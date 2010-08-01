package gov.hhs.fha.nhinc.docretrievedeferred.entity.proxy.response;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunitiesType;
import gov.hhs.fha.nhinc.common.nhinccommonentity.RespondingGatewayCrossGatewayRetrieveResponseType;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;
import gov.hhs.fha.nhinc.entitydocretrieve.EntityDocRetrieveDeferredResponse;
import gov.hhs.fha.nhinc.entitydocretrieve.EntityDocRetrieveDeferredResponsePortType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import gov.hhs.healthit.nhin.DocRetrieveAcknowledgementType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Sai Valluripalli
 */
public class EntityDocRetrieveDeferredRespUnsecuredWebServiceImpl implements EntityDocRetrieveDeferredRespProxy {

    private Log log = null;
    private boolean debugEnabled = false;
    private static EntityDocRetrieveDeferredResponse service = null;

    /**
     * default constructor
     */
    public EntityDocRetrieveDeferredRespUnsecuredWebServiceImpl() {
        log = createLogger();
        debugEnabled = log.isDebugEnabled();
        service = getWebService();
    }

    /**
     * 
     * @return EntityDocRetrieveDeferredResponse
     */
    private EntityDocRetrieveDeferredResponse getWebService() {
        return new EntityDocRetrieveDeferredResponse();
    }

    /**
     * creates logger instance
     * @return Log
     */
    private Log createLogger() {
        return (log != null) ? log : LogFactory.getLog(this.getClass());
    }

    /**
     * 
     * @param response
     * @param assertion
     * @param target
     * @return DocRetrieveAcknowledgementType
     */
    public DocRetrieveAcknowledgementType crossGatewayRetrieveResponse(RetrieveDocumentSetResponseType response, AssertionType assertion, NhinTargetCommunitiesType target) {
        if (debugEnabled) {
            log.debug("-- Begin EntityDocRetrieveDeferredRespUnsecuredWebServiceImpl.crossGatewayRetrieveResponse(...) --");
        }
        DocRetrieveAcknowledgementType ack = null;
        String url = getUrl();
        if (NullChecker.isNotNullish(url)) {
            EntityDocRetrieveDeferredResponsePortType port = getPort(url);
            RespondingGatewayCrossGatewayRetrieveResponseType resp = new RespondingGatewayCrossGatewayRetrieveResponseType();
            resp.setAssertion(assertion);
            resp.setNhinTargetCommunities(target);
            resp.setRetrieveDocumentSetResponse(response);
            ack = port.crossGatewayRetrieveResponse(resp);
        }
        if (debugEnabled) {
            log.debug("-- End EntityDocRetrieveDeferredRespUnsecuredWebServiceImpl.crossGatewayRetrieveResponse(...) --");
        }
        return ack;
    }

    /**
     * 
     * @return String
     */
    private String getUrl() {
        String url = null;
        try {
            url = ConnectionManagerCache.getLocalEndpointURLByServiceName(NhincConstants.ENTITY_DOCRETRIEVE_DEFERRED_UNSECURED_RESPONSE);
        } catch (Exception e) {
            log.error("Error: Failed to retrieve url for service: '" + NhincConstants.ENTITY_DOCRETRIEVE_DEFERRED_UNSECURED_RESPONSE + "'");
            log.error(e.getMessage());
        }
        return url;
    }

    /**
     *
     * @param url
     * @return EntityDocRetrieveDeferredResponseSecuredPortType
     */
    private EntityDocRetrieveDeferredResponsePortType getPort(String url) {
        EntityDocRetrieveDeferredResponsePortType port = service.getEntityDocRetrieveDeferredResponsePortSoap();
        ((javax.xml.ws.BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        return port;
    }
}