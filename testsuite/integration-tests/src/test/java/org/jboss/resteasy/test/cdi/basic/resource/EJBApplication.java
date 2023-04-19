package org.jboss.resteasy.test.cdi.basic.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Application;

@Singleton
@ApplicationScoped
public class EJBApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(EJBBookReaderImpl.class);
        classes.add(EJBBookWriterImpl.class);
        classes.add(EJBBookResource.class);
        return classes;
    }
}
