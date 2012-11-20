/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services. 
 * All rights reserved. 
 *
 * Copyright (c) 2011, Conemaugh Valley Memorial Hospital
 * This source is subject to the Conemaugh public license.  Please see the
 * license.txt file for more information.
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
package gov.hhs.fha.nhinc.adapter.commondatalayer.mappers;

import gov.hhs.fha.nhinc.mpi.adapter.component.hl7parsers.HL7Parser201305;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.FindPatientsPRPAIN201305UV02RequestType;
import org.hl7.v3.FindPatientsPRPAMT201310UV02ResponseType;
import org.hl7.v3.PRPAIN201305UV02MCCIMT000100UV01Message;
import org.hl7.v3.PRPAMT201306UV02ParameterList;
import gov.hhs.fha.nhinc.adapter.commondatalayer.mappers.constants.AdapterCommonDataLayerConstants;

/**
 * 
 * @author kim
 */
public class StaticFindPatientsQuery {

    private static final Log LOG = LogFactory.getLog(StaticFindPatientsQuery.class);
    private static final String DEBUG_LINE_BREAK = "= = = = = = = = = = = = = = = = = = ="
       + " = = = = = = = = = = = = = = = =";

    public static FindPatientsPRPAMT201310UV02ResponseType createFindPatientsResponse(
        FindPatientsPRPAIN201305UV02RequestType request) {
        FindPatientsPRPAMT201310UV02ResponseType response = new FindPatientsPRPAMT201310UV02ResponseType();

        LOG.debug("Creating Static Find Patients Data...");

        // check properties file for test/live data mode
        if (AdapterCommonDataLayerConstants.FIND_PATIENTS_TEST.equalsIgnoreCase("Y")) {

            // Get Provider OID from request
            String receiverOID = request.getReceiverOID();

            //Get Patient Last Name, First Name, Gender, and DOB from request
            String ptLastName = null;
            String ptFirstName = null;
            String ptGender = null;
            String ptDOB = null;

            PRPAIN201305UV02MCCIMT000100UV01Message query = request.getQuery();
            if (query != null) {
                PRPAMT201306UV02ParameterList paramList
                    = query.getControlActProcess().getQueryByParameter().getValue().getParameterList();

                // get patient's first name
                ptLastName = HL7Parser201305.ExtractPersonName(paramList).getLastName();

                // get patient's first name
                ptFirstName = HL7Parser201305.ExtractPersonName(paramList).getFirstName();

                // get patient's gender
                ptGender = HL7Parser201305.ExtractGender(paramList);

                // get patient's date of birth (if any)
                ptDOB = HL7Parser201305.ExtractBirthdate(paramList);

            }

            response = StaticUtil.createFindPatientsResponse(receiverOID, ptLastName, ptFirstName, ptGender, ptDOB);

        } else {
            LOG.debug(DEBUG_LINE_BREAK);
            LOG.debug(DEBUG_LINE_BREAK);
            LOG.debug(" Insert Adapter Agency specific dynamic document data accessors here ");
            LOG.debug(DEBUG_LINE_BREAK);
            LOG.debug(DEBUG_LINE_BREAK);
        }

        return response;
    }
}
