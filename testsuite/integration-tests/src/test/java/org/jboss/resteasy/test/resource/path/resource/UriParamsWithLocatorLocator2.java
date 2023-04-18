package org.jboss.resteasy.test.resource.path.resource;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.PathSegment;

import org.junit.Assert;

@Path("/")
public class UriParamsWithLocatorLocator2 {
    @Path("/{id}")
    public UriParamsWithLocatorResource2 get(@PathParam("id") PathSegment id) {
        Assert.assertEquals("1", id.getPath());
        return new UriParamsWithLocatorResource2();
    }
}
