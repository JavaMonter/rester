package org.jboss.resteasy.test.client.exception.resource;

import javax.ws.rs.WebApplicationException;

public class ClientExceptionsCustomException extends WebApplicationException {

    public ClientExceptionsCustomException(final String message) {
        super(message);
    }
}
