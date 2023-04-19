package org.jboss.resteasy.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.resteasy_jaxrs.i18n.Messages;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.spi.ValueInjector;
import org.jboss.resteasy.util.Encode;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class FormParamInjector extends StringParameterInjector implements ValueInjector {
    private boolean encode;

    public FormParamInjector(final Class type, final Type genericType, final AccessibleObject target, final String header,
            final String defaultValue, final boolean encode, final Annotation[] annotations,
            final ResteasyProviderFactory factory) {
        super(type, genericType, header, FormParam.class, defaultValue, target, annotations, factory);
        this.encode = encode;
    }

    @Override
    public Object inject(HttpRequest request, HttpResponse response, boolean unwrapAsync) {
        List<String> list = request.getDecodedFormParameters().get(paramName);
        if (list != null && encode) {
            List<String> encodedList = new ArrayList<String>();
            for (String s : list) {
                encodedList.add(Encode.encodeString(s));
            }
            list = encodedList;
        }
        return extractValues(list);
    }

    @Override
    public Object inject(boolean unwrapAsync) {
        throw new RuntimeException(Messages.MESSAGES.illegalToInjectFormParam());
    }
}
