package org.jboss.resteasy.test.interception.resource;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

@Priority(100)
public class PriorityClientRequestFilter1 implements ClientRequestFilter {
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {

    }
}
