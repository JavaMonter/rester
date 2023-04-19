package org.jboss.resteasy.test.interceptor.resource;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

import org.jboss.resteasy.test.interceptor.PriorityExecutionTest;

@Priority(Integer.MIN_VALUE)
public class PriorityExecutionClientResponseFilterMin implements ClientResponseFilter {
    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        PriorityExecutionTest.logger.info(this);
        PriorityExecutionTest.interceptors.add("PriorityExecutionClientResponseFilterMin");
    }
}
