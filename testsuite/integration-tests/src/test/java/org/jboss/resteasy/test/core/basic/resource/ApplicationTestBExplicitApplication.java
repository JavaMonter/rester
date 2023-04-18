package org.jboss.resteasy.test.core.basic.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("b/explicit")
public class ApplicationTestBExplicitApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> set = new HashSet<Class<?>>();
        set.add(ApplicationTestResourceB.class);
        return set;
    }

    @Override
    public Set<Object> getSingletons() {
        HashSet<Object> set = new HashSet<>();
        set.add(new ApplicationTestSingletonB());
        return set;
    }
}
