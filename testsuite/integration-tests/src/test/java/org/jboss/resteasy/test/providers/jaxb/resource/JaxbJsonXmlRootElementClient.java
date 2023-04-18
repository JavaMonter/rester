package org.jboss.resteasy.test.providers.jaxb.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Consumes("application/json")
@Produces("application/json")
public interface JaxbJsonXmlRootElementClient {

    @GET
    @Path("/{name}")
    Parent getParent(@PathParam("name") String name);

    @GET
    @Path("/{name}")
    String getParentString(@PathParam("name") String name);

    @POST
    Parent postParent(Parent parent);

}
