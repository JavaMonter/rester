package org.jboss.resteasy.test.client.resource;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

@ConstrainedTo(RuntimeType.SERVER)
public class ClientDynamicFeaturesServerFeature2 implements DynamicFeature {
    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
    }
}
