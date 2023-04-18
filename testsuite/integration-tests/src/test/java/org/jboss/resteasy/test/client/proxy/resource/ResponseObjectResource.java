package org.jboss.resteasy.test.client.proxy.resource;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("test")
public class ResponseObjectResource {

    @GET
    @Produces("text/plain")
    public String get() {
        return "ABC";
    }

    @GET
    @Path("/link-header")
    public Response getWithHeader(@Context UriInfo uri) {
        URI subUri = uri.getAbsolutePathBuilder().path("next-link").build();
        Link link = Link.fromUri(subUri).rel("nextLink").build();
        return Response.noContent().header("Link", link.toString()).build();
    }

    @GET
    @Produces("text/plain")
    @Path("/link-header/next-link")
    public String getHeaderForward() {
        return "forwarded";
    }
}
