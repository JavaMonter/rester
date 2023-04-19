package org.jboss.resteasy.test.resource.param.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.test.resource.param.HeaderDelegateTest;

@Path("/last")
public class HeaderDelegateResource {
    @GET
    @Produces("text/plain")
    public Response last() {
        return Response.ok().lastModified(HeaderDelegateTest.RIGHT_AFTER_BIG_BANG).build();
    }
}
