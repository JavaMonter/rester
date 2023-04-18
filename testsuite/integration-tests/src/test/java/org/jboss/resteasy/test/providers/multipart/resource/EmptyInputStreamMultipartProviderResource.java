package org.jboss.resteasy.test.providers.multipart.resource;

import java.io.ByteArrayInputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/rest/zba")
public class EmptyInputStreamMultipartProviderResource {
    @GET
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @MultipartForm
    public EmptyInputStreamMultipartProviderMyBean get() {
        EmptyInputStreamMultipartProviderMyBean myBean = new EmptyInputStreamMultipartProviderMyBean();
        myBean.setSomeBinary(new ByteArrayInputStream(new byte[0]));
        return myBean;
    }
}
