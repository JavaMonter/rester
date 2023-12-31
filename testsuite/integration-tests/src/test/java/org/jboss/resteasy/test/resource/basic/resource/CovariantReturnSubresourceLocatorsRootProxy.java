package org.jboss.resteasy.test.resource.basic.resource;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

public interface CovariantReturnSubresourceLocatorsRootProxy {
    @Path("sub/{path}")
    CovariantReturnSubresourceLocatorsSubProxy getSub(@PathParam("path") String path);
}
