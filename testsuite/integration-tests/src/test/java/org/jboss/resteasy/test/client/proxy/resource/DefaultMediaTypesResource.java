package org.jboss.resteasy.test.client.proxy.resource;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.test.client.proxy.DefaultMediaTypesTest;

@Path("foo")
public class DefaultMediaTypesResource implements DefaultMediaTypesTest.Foo {
    @Context
    HttpRequest request;

    @Override
    public String getFoo() {
        return request.getHttpHeaders().getAcceptableMediaTypes().toString();
    }

    @Override
    public String setFoo(String value) {
        return request.getHttpHeaders().getMediaType().toString();
    }
}
