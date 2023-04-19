package org.jboss.resteasy.test.providers.custom.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public interface ReaderWriterClient {
    @Path("/implicit")
    @DELETE
    Response deleteCustomer();

    @Path("/complex")
    @DELETE
    Response deleteComplex();
}
