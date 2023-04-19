package org.jboss.resteasy.test.warning.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by rsearls on 9/5/17.
 */
@Path("test")
public class TestResource1 {

    @GET
    @Path("x")
    @Produces("text/plain")
    public Response method() {
        return Response.ok("ok").build();
    }
}
