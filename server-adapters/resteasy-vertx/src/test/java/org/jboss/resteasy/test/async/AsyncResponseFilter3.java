package org.jboss.resteasy.test.async;

import jakarta.annotation.Priority;
import jakarta.ws.rs.ext.Provider;

@Priority(3)
@Provider
public class AsyncResponseFilter3 extends AsyncResponseFilter {

    public AsyncResponseFilter3() {
        super("ResponseFilter3");
    }
}
