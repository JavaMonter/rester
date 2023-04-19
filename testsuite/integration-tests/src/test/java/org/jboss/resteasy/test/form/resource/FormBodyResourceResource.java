package org.jboss.resteasy.test.form.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.Form;

@Path("body")
public class FormBodyResourceResource {
    @PUT
    @Consumes("text/plain")
    @Produces("text/plain")
    public String put(@Form FormBodyResourceForm form) {
        return form.body + ".gotIt";
    }
}
