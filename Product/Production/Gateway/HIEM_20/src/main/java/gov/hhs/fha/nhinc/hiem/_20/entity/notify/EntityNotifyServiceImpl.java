/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the United States Government nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.hhs.fha.nhinc.hiem._20.entity.notify;

import gov.hhs.fha.nhinc.common.nhinccommon.AcknowledgementType;
import gov.hhs.fha.nhinc.common.nhinccommonentity.NotifyRequestType;
import gov.hhs.fha.nhinc.hiem.dte.SoapUtil;
import gov.hhs.fha.nhinc.notify.entity.EntityNotifyOrchImpl;
import gov.hhs.fha.nhinc.saml.extraction.SamlTokenExtractor;

import javax.xml.ws.WebServiceContext;

import org.oasis_open.docs.wsn.b_2.Notify;

/**
 *
 *
 * @author Neil Webb
 */
public class EntityNotifyServiceImpl {

    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
            .getLog(EntityNotifyServiceImpl.class);

    public AcknowledgementType notify(NotifyRequestType notifyRequest, WebServiceContext context) {
        log.debug("EntityNotifyServiceImpl.notify");
        AcknowledgementType ack = new AcknowledgementType();

        try {
            String rawNotifyXml = new SoapUtil().extractSoapMessage(context, "notifySoapMessage");

            EntityNotifyOrchImpl processor = new EntityNotifyOrchImpl();
            processor.processNotify(notifyRequest.getNotify(), notifyRequest.getAssertion(), rawNotifyXml);
        } catch (Throwable t) {
            log.error("Exception encountered processing notify message: " + t.getMessage(), t);
        }

        return ack;
    }

    public AcknowledgementType notify(Notify notifyRequest, WebServiceContext context) {
        log.debug("EntityNotifyServiceImpl.notify");
        AcknowledgementType ack = new AcknowledgementType();

        try {
            String rawNotifyXml = new SoapUtil().extractSoapMessage(context, "notifySoapMessage");
            EntityNotifyOrchImpl processor = new EntityNotifyOrchImpl();
            processor.processNotify(notifyRequest, SamlTokenExtractor.GetAssertion(context), rawNotifyXml);
        } catch (Throwable t) {
            log.error("Exception encountered processing notify message: " + t.getMessage(), t);
        }

        return ack;
    }

}
