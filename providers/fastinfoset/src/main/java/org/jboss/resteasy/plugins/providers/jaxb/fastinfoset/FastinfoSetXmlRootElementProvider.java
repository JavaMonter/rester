package org.jboss.resteasy.plugins.providers.jaxb.fastinfoset;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.providers.jaxb.JAXBXmlRootElementProvider;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@Provider
@Consumes({ "application/fastinfoset", "application/*+fastinfoset" })
@Produces({ "application/fastinfoset", "application/*+fastinfoset" })
public class FastinfoSetXmlRootElementProvider extends JAXBXmlRootElementProvider {
    @Override
    protected boolean needsSecurity() {
        return false;
    }
}
