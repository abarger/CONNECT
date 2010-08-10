package gov.hhs.fha.nhinc.policyengine.adapter.pep.proxy;

import gov.hhs.fha.nhinc.adapterpep.AdapterPEPPortType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.CheckPolicyRequestType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.CheckPolicyResponseType;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerException;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is the concrete implementation for the Web based call to the
 * AdapterPEP.
 */
public class AdapterPEPWebServiceProxy implements AdapterPEPProxy{

    private Log log = null;
    private static Service cachedService = null;
    private static final String NAMESPACE_URI = "urn:gov:hhs:fha:nhinc:adapterpep";
    private static final String SERVICE_LOCAL_PART = "AdapterPEP";
    private static final String PORT_LOCAL_PART = "AdapterPEPPortSoap";
    private static final String WSDL_FILE = "AdapterPEP.wsdl";
    private static final String WS_ADDRESSING_ACTION = "urn:gov:hhs:fha:nhinc:adapterpep:CheckPolicyRequest";

    private WebServiceProxyHelper oProxyHelper = null;

    public AdapterPEPWebServiceProxy()
    {
        log = createLogger();
        oProxyHelper = createWebServiceProxyHelper();
    }

    protected Log createLogger()
    {
        return LogFactory.getLog(getClass());
    }

    protected WebServiceProxyHelper createWebServiceProxyHelper()
    {
        return new WebServiceProxyHelper();
    }

    protected String invokeConnectionManager(String serviceName) throws ConnectionManagerException
    {
        return ConnectionManagerCache.getLocalEndpointURLByServiceName(serviceName);
    }

    protected String getEndpointURL()
    {
        String endpointURL = null;
        String serviceName = NhincConstants.ADAPTER_PEP_SERVICE_NAME;
        try
        {
            endpointURL = invokeConnectionManager(serviceName);
            log.debug("Retrieved endpoint URL for service " + serviceName + ": " + endpointURL);
        }
        catch (ConnectionManagerException ex)
        {
            log.error("Error getting url for " + serviceName + " from the connection manager. Error: " + ex.getMessage(), ex);
        }

        return endpointURL;
    }
    
    /**
     * This method retrieves and initializes the port.
     *
     * @param url The URL for the web service.
     * @param wsAddressingAction The action assigned to the input parameter for the web service operation.
     * @param assertion The assertion information for the web service
     * @return The port object for the web service.
     */
    protected AdapterPEPPortType getPort(String url, String wsAddressingAction, AssertionType assertion)
    {
        AdapterPEPPortType port = null;
        Service service = getService();
        if (service != null)
        {
            log.debug("Obtained service - creating port.");

            port = service.getPort(new QName(NAMESPACE_URI, PORT_LOCAL_PART), AdapterPEPPortType.class);
            oProxyHelper.initializeUnsecurePort((javax.xml.ws.BindingProvider) port, url, wsAddressingAction, assertion);
        }
        else
        {
            log.error("Unable to obtain serivce - no port created.");
        }
        return port;
    }

    /**
     * Retrieve the service class for this web service.
     *
     * @return The service class for this web service.
     */
    protected Service getService()
    {
        if (cachedService == null)
        {
            try
            {
                cachedService = oProxyHelper.createService(WSDL_FILE, NAMESPACE_URI, SERVICE_LOCAL_PART);
            }
            catch (Throwable t)
            {
                log.error("Error creating service: " + t.getMessage(), t);
            }
        }
        return cachedService;
    }

    /**
     * Given a request to check the access policy, this service will interface
     * with the PDP to determine if access is to be granted or denied.
     * @param request The xacml request to check defined policy
     * @return The xacml response which contains the access decision
     */
    public CheckPolicyResponseType checkPolicy(CheckPolicyRequestType request) {

        CheckPolicyResponseType checkPolicyResponse = new CheckPolicyResponseType();

        try
        {
            String url = getEndpointURL();
            AssertionType assertion = request.getAssertion();
            AdapterPEPPortType pepPort = getPort(url, WS_ADDRESSING_ACTION, assertion);
            checkPolicyResponse = pepPort.checkPolicy(request);
        }
        catch (Exception ex)
        {
            String message = "Error occurred calling AdapterPEPJavaProxy.checkPolicy.  Error: " +
                                   ex.getMessage();
            log.error(message, ex);
            throw new RuntimeException(message, ex);
        }

        return checkPolicyResponse;
    }

}