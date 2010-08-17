/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.docretrieve.adapter.deferred.request.error;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;

/**
 *
 * @author JHOPPESC
 */
@WebService(serviceName = "AdapterDocRetrieveDeferredRequestErrorService", portName = "AdapterDocRetrieveRequestErrorPortSoap", endpointInterface = "gov.hhs.fha.nhinc.adapterdocretrievedeferredrequesterror.AdapterDocRetrieveDeferredRequestErrorPortType", targetNamespace = "urn:gov:hhs:fha:nhinc:adapterdocretrievedeferredrequesterror", wsdlLocation = "WEB-INF/wsdl/AdapterDocRetrieveDeferredRequestErrorUnsecured/AdapterDocRetrieveDeferredReqError.wsdl")
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class AdapterDocRetrieveDeferredRequestErrorUnsecured {
    @Resource
    private WebServiceContext context;

    public gov.hhs.healthit.nhin.DocRetrieveAcknowledgementType crossGatewayRetrieveRequestError(gov.hhs.fha.nhinc.common.nhinccommonadapter.AdapterDocumentRetrieveDeferredRequestErrorType body) {
        return new AdapterDocRetrieveDeferredRequestErrorImpl().crossGatewayRetrieveRequestError(body, context);
    }

}