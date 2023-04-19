package org.jboss.resteasy.test.resource.basic.resource;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public interface ParameterSubResRoot {
    @Path("sub/{path}")
    ParameterSubResSub getSub(@PathParam("path") String path);

    @Path("subclass")
    Class<ParameterSubResClassSub> getSubClass();
}
