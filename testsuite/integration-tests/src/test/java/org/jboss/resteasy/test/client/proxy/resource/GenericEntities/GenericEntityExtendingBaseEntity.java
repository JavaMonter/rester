package org.jboss.resteasy.test.client.proxy.resource.GenericEntities;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public interface GenericEntityExtendingBaseEntity<T extends BaseEntity> {

    @GET
    @Path("one")
    @Produces(MediaType.APPLICATION_JSON)
    T findOne();

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    List<T> findAll();

}
