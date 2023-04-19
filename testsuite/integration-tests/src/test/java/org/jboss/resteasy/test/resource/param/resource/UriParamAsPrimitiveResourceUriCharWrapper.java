package org.jboss.resteasy.test.resource.param.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jboss.resteasy.test.resource.param.UriParamAsPrimitiveTest;
import org.junit.Assert;

@Path("/char/wrapper/{arg}")
public class UriParamAsPrimitiveResourceUriCharWrapper {
    @GET
    public String doGet(@PathParam("arg") Character v) {
        Assert.assertEquals(UriParamAsPrimitiveTest.ERROR_CODE, 'a', v.charValue());
        return "content";
    }
}
