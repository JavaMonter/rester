package org.jboss.resteasy.test.resource.basic.resource;

import javax.ws.rs.Path;

@Path("/path")
public class CovariantReturnSubresourceLocatorsSubProxyRootImpl implements CovariantReturnSubresourceLocatorsRootProxy {
    @Override
    public CovariantReturnSubresourceLocatorsSubProxySubImpl getSub(String path) {
        return new CovariantReturnSubresourceLocatorsSubProxySubImpl(path);
    }
}
