package org.jboss.resteasy.test.resource.basic.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

public interface MultiInterfaceResLocatorIntf2 {
    @GET
    @Produces("text/plain")
    @Path("hello2")
    String resourceMethod2();
}
