package org.jboss.resteasy.test.response.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/test")
public class ResponseHeaderResource {
    @GET
    @Produces("text/plain")
    public String get() {
        throw new ResponseHeaderExceptionMapperRuntimeException();
    }
}
