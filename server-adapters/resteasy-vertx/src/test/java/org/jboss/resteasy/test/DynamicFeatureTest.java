package org.jboss.resteasy.test;

import static org.jboss.resteasy.test.TestPortProvider.generateURL;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.jboss.resteasy.plugins.server.vertx.VertxContainer;
import org.jboss.resteasy.plugins.server.vertx.VertxResteasyDeployment;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test that dynamic feature doesn't add to all resource methods
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class DynamicFeatureTest {

    public abstract static class AbstractAddFilter implements ContainerResponseFilter {

        protected int amount;

        public AbstractAddFilter(final int amount) {
            this.amount = amount;
        }

        @Override
        public void filter(ContainerRequestContext requestContext,
                ContainerResponseContext responseContext) throws IOException {
            int status = responseContext.getStatus();
            if (status != 500) {
                String entity = (String) responseContext.getEntity();
                Integer i = Integer.valueOf(entity);
                entity = String.valueOf(i + amount);
                responseContext.setEntity(entity, (Annotation[]) null,
                        MediaType.WILDCARD_TYPE);
            }
        }

    }

    public abstract static class AbstractAddInterceptor
            implements ReaderInterceptor, WriterInterceptor {

        private int amount;

        public AbstractAddInterceptor(final int amount) {
            this.amount = amount;
        }

        @Override
        public void aroundWriteTo(WriterInterceptorContext context)
                throws IOException, WebApplicationException {
            String entity = (String) context.getEntity();
            if (entity != null) { // when unhandled exception, status 500
                // does not have entity
                Integer i = Integer.parseInt(entity);
                entity = String.valueOf(i + amount);
                context.setEntity(entity);
            }
            context.proceed();
        }

        @Override
        public Object aroundReadFrom(ReaderInterceptorContext context)
                throws IOException, WebApplicationException {
            InputStream inputStream = context.getInputStream();
            String entity = readFromStream(inputStream);
            if (entity != null) {
                Integer i = Integer.parseInt(entity);
                entity = String.valueOf(i + amount);
                context.setInputStream(new ByteArrayInputStream(entity.getBytes()));
            }
            return context.proceed();
        }

    }

    public static class AddTenFilter extends AbstractAddFilter {

        public AddTenFilter() {
            super(10);
        }

        @Override
        public void filter(ContainerRequestContext requestContext,
                ContainerResponseContext responseContext) throws IOException {
            super.filter(requestContext, responseContext);
        }

    }

    public static class AddOneInterceptor extends AbstractAddInterceptor {

        public AddOneInterceptor() {
            super(1);
        }
    }

    @Path("resource")
    public static class Resource {

        @POST
        @Path("dynamic")
        public String echo(String echo) {
            return echo;
        }

        @POST
        @Path("nobinding")
        public String noBindingEcho(String echo) {
            return echo;
        }
    }

    public static final//
    String readFromStream(InputStream stream) throws IOException {
        InputStreamReader isr = new InputStreamReader(stream);
        return readFromReader(isr);
    }

    public static final//
    String readFromReader(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        String entity = br.readLine();
        br.close();
        return entity;
    }

    @Provider
    public static class AddDynamicFeature implements DynamicFeature {

        @Override
        public void configure(ResourceInfo resourceInfo, FeatureContext context) {
            if (resourceInfo.getResourceMethod().getName().equals("echo")) {
                context.register(AddTenFilter.class);
                context.register(AddOneInterceptor.class);
            }
        }

    }

    static Client client;

    @BeforeClass
    public static void setup() throws Exception {
        VertxResteasyDeployment deployment = new VertxResteasyDeployment();
        deployment.getActualProviderClasses().add(AddDynamicFeature.class);
        deployment.getActualResourceClasses().add(Resource.class);
        VertxContainer.start(deployment);
        client = ClientBuilder.newClient();
    }

    @AfterClass
    public static void end() throws Exception {
        try {
            client.close();
        } catch (Exception e) {

        }
        VertxContainer.stop();
    }

    @Test
    public void testBasic() throws Exception {
        WebTarget target = client.target(generateURL("/resource/nobinding"));
        String val = target.request().post(Entity.text("0"), String.class);
        Assert.assertEquals("0", val);

    }
}
