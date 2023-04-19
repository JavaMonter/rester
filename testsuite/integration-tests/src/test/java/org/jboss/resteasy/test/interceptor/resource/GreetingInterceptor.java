package org.jboss.resteasy.test.interceptor.resource;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

public class GreetingInterceptor implements WriterInterceptor {

    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        String entity = (String) context.getEntity();
        if (entity != null) {
            context.setEntity("Hello " + entity + " !");
        }
        context.proceed();
    }

}
