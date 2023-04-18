package org.jboss.resteasy.test.exception.resource;

import javax.ws.rs.core.Response;

public class ClosedResponseHandlingPleaseMapException extends RuntimeException {

    final Response response;

    public ClosedResponseHandlingPleaseMapException(final Response response) {
        this.response = response;
    }
}
