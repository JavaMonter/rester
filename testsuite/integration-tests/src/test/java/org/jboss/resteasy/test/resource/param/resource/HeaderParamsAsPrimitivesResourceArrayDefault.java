package org.jboss.resteasy.test.resource.param.resource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.test.resource.param.HeaderParamsAsPrimitivesTest;
import org.junit.Assert;

@Path("/array/default")
public class HeaderParamsAsPrimitivesResourceArrayDefault {
    @GET
    @Produces("application/boolean")
    public String doGetBoolean(@HeaderParam("boolean") @DefaultValue("true") boolean[] v) {
        Assert.assertEquals(HeaderParamsAsPrimitivesTest.ERROR_MESSAGE, true, v[0]);
        return "content";
    }

    @GET
    @Produces("application/short")
    public String doGetShort(@HeaderParam("short") @DefaultValue("32767") short[] v) {
        Assert.assertTrue(HeaderParamsAsPrimitivesTest.ERROR_MESSAGE, 32767 == v[0]);
        return "content";
    }
}
