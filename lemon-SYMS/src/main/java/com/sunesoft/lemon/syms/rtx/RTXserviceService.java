
package com.sunesoft.lemon.syms.rtx;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "RTXserviceService", targetNamespace = "http://webService.rtxservice.petrochina.com", wsdlLocation = "http://10.72.247.160:9080/RTXService/services/RTXservice/wsdl/RTXservice.wsdl")
public class RTXserviceService
    extends Service
{

    private final static URL RTXSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException RTXSERVICESERVICE_EXCEPTION;
    private final static QName RTXSERVICESERVICE_QNAME = new QName("http://webService.rtxservice.petrochina.com", "RTXserviceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://10.72.247.160:9080/RTXService/services/RTXservice/wsdl/RTXservice.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        RTXSERVICESERVICE_WSDL_LOCATION = url;
        RTXSERVICESERVICE_EXCEPTION = e;
    }

    public RTXserviceService() {
        super(__getWsdlLocation(), RTXSERVICESERVICE_QNAME);
    }

    public RTXserviceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), RTXSERVICESERVICE_QNAME, features);
    }

    public RTXserviceService(URL wsdlLocation) {
        super(wsdlLocation, RTXSERVICESERVICE_QNAME);
    }

    public RTXserviceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, RTXSERVICESERVICE_QNAME, features);
    }

    public RTXserviceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RTXserviceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns RTXservice
     */
    @WebEndpoint(name = "RTXservice")
    public RTXservice getRTXservice() {
        return super.getPort(new QName("http://webService.rtxservice.petrochina.com", "RTXservice"), RTXservice.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RTXservice
     */
    @WebEndpoint(name = "RTXservice")
    public RTXservice getRTXservice(WebServiceFeature... features) {
        return super.getPort(new QName("http://webService.rtxservice.petrochina.com", "RTXservice"), RTXservice.class, features);
    }

    private static URL __getWsdlLocation() {
        if (RTXSERVICESERVICE_EXCEPTION!= null) {
            throw RTXSERVICESERVICE_EXCEPTION;
        }
        return RTXSERVICESERVICE_WSDL_LOCATION;
    }

}