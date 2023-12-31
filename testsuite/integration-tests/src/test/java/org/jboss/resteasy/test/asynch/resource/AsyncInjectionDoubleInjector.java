package org.jboss.resteasy.test.asynch.resource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import jakarta.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.ContextInjector;

@Provider
public class AsyncInjectionDoubleInjector implements ContextInjector<CompletionStage<Double>, Double> {

    @Override
    public CompletionStage<Double> resolve(
            Class<? extends CompletionStage<Double>> rawType, Type genericType, Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == AsyncInjectionPrimitiveInjectorSpecifier.class) {
                AsyncInjectionPrimitiveInjectorSpecifier.Type value = ((AsyncInjectionPrimitiveInjectorSpecifier) annotation)
                        .value();
                switch (value) {
                    case NO_RESULT:
                        return null;
                    case NULL:
                        return CompletableFuture.completedFuture(null);
                    case VALUE:
                        return CompletableFuture.completedFuture(4.2);
                }
                break;
            }
        }
        return CompletableFuture.completedFuture(4.2);
    }

}
