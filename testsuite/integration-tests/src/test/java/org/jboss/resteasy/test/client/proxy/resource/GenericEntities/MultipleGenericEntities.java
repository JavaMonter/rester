package org.jboss.resteasy.test.client.proxy.resource.GenericEntities;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
public interface MultipleGenericEntities<K, V> {

    @GET
    @Path("hashMap")
    @Produces(MediaType.APPLICATION_JSON)
    HashMap<K, V> findHashMap();

}
