package org.jboss.resteasy.test.providers.jaxb.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.logging.Logger;

@Path("/")
public class AbstractJaxbClassResource {

    private static Logger logger = Logger.getLogger(AbstractJaxbClassResource.class.getName());

    @POST
    public void post(AbstractJaxbClassPerson person) {
        logger.info(person.getName() + " " + person.getId());
    }

    @POST
    @Path("customer")
    public void postKunde(AbstractJaxbClassCustomer customer) {
        logger.info(customer.getNachname());
    }

}
