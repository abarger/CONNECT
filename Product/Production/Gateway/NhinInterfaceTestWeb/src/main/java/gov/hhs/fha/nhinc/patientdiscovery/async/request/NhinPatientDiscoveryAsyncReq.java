/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.patientdiscovery.async.request;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;

/**
 *
 * @author JHOPPESC
 */
@WebService(serviceName = "RespondingGatewayAsyncReq_Service", portName = "RespondingGatewayAsyncReq_Port", endpointInterface = "ihe.iti.xcpd._2009.RespondingGatewayAsyncReqPortType", targetNamespace = "urn:ihe:iti:xcpd:2009", wsdlLocation = "WEB-INF/wsdl/NhinPatientDiscoveryAsyncReq/NhinPatientDiscoveryAsyncReq.wsdl")
@BindingType(value = "http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class NhinPatientDiscoveryAsyncReq {
    @Resource
    private WebServiceContext context;

    public org.hl7.v3.MCCIIN000002UV01 respondingGatewayPRPAIN201305UV02(org.hl7.v3.PRPAIN201305UV02 body) {
        return new NhinPatientDiscoveryAsyncReqImpl().respondingGatewayPRPAIN201305UV02(body, context);
    }

}