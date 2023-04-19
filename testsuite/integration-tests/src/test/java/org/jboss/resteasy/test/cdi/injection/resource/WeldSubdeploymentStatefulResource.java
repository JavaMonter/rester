package org.jboss.resteasy.test.cdi.injection.resource;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.test.cdi.injection.WeldSubdeploymentTest;
import org.junit.Assert;

@Path("/stateful")
@Stateful
public class WeldSubdeploymentStatefulResource {

    @Inject
    private WeldSubdeploymentCdiJpaInjectingBean bean;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public void getMethod() {
        Assert.assertNotNull(WeldSubdeploymentTest.ERROR_MESSAGE, bean.entityManagerFactory());
    }

}
