package org.jboss.resteasy.test.resource.path.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.PathSegment;

import org.junit.Assert;

public class UriParamsWithLocatorResource2 {
    @GET
    @Path("/{id}")
    public String get(@PathParam("id") PathSegment id) {
        Assert.assertEquals("2", id.getPath());
        return id.getPath();
    }
}
