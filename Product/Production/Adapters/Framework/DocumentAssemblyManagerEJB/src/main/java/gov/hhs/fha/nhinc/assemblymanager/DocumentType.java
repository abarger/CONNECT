/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services. 
 * All rights reserved. 
 * Copyright (c) 2011, Conemaugh Valley Memorial Hospital
 * This source is subject to the Conemaugh public license.  Please see the
 * license.txt file for more information.
 * All other rights reserved.

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
package gov.hhs.fha.nhinc.assemblymanager;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author rhaslam - reworked to avoid use of DB
 */
public class DocumentType {

    private String typeId;
    private String displayName;
    private boolean active;
    private String version;
    private String comments;
    private String codeSystemOid;
    private String codeSystemName;
    private static DocumentType c32Document;
    private static DocumentType c62Document;
    private static DocumentType c62RadiologyDocument;
    private static List<String> allDocumentClassCodes;

    static {
        c32Document = new DocumentType();
        c32Document.setActive(true);
        c32Document.setCodeSystemName(CDAConstants.LOINC_CODE_SYS_NAME);
        c32Document.setCodeSystemOid(CDAConstants.LOINC_CODE_SYS_OID);
        c32Document.setComments("");
        c32Document.setDisplayName(AssemblyConstants.C32_DISPLAY_NAME);
        c32Document.setTypeId(AssemblyConstants.C32_CLASS_CODE);
        c32Document.setVersion(AssemblyConstants.C32_VERSION);

        c62Document = new DocumentType();
        c62Document.setActive(true);
        c62Document.setCodeSystemName(CDAConstants.LOINC_CODE_SYS_NAME);
        c62Document.setCodeSystemOid(CDAConstants.LOINC_CODE_SYS_OID);
        c62Document.setComments("");
        c62Document.setDisplayName(AssemblyConstants.C62_DISPLAY_NAME);
        c62Document.setTypeId(AssemblyConstants.C62_CLASS_CODE);
        c62Document.setVersion(AssemblyConstants.C62_VERSION);

        //radiology docs
        c62RadiologyDocument = new DocumentType();
        c62RadiologyDocument.setActive(true);
        c62RadiologyDocument.setCodeSystemName(CDAConstants.LOINC_CODE_SYS_NAME);
        c62RadiologyDocument.setCodeSystemOid(CDAConstants.LOINC_CODE_SYS_OID);
        c62RadiologyDocument.setComments("");
        c62RadiologyDocument.setDisplayName(AssemblyConstants.C62_RR_DISPLAY_NAME);
        c62RadiologyDocument.setTypeId(AssemblyConstants.C62_RR_CLASS_CODE);
        c62RadiologyDocument.setVersion(AssemblyConstants.C62_VERSION);

        allDocumentClassCodes = new ArrayList();
        allDocumentClassCodes.add(AssemblyConstants.C32_CLASS_CODE);
        allDocumentClassCodes.add(AssemblyConstants.C62_CLASS_CODE);
        allDocumentClassCodes.add(AssemblyConstants.C62_RR_CLASS_CODE);
    }

    /**
     *
     * @param type String
     * @return retBool
     */
    public static boolean isValidDocumentType(String type) {
        
        boolean retBool = false;

        if (allDocumentClassCodes.contains(type)) {
            return true;
        }

        return retBool;
    }

    /**
     *
     * @return allDocumentClassCodes
     */
    public static List<String> getSupportedTypesList() {
        return allDocumentClassCodes;
    }

    /**
     *
     * @param documentClassCode String
     * @return DocumentType
     */
    public static DocumentType getDocument(String documentClassCode) {
        if (documentClassCode.equals(AssemblyConstants.C32_CLASS_CODE)) {
            return c32Document;
        }
        if (documentClassCode.equals(AssemblyConstants.C62_CLASS_CODE)) {
            return c62Document;
        }
        if (documentClassCode.equals(AssemblyConstants.C62_RR_CLASS_CODE)) {
            return c62RadiologyDocument;
        }
        return null;
    }

    /**
     *
     */
    public DocumentType() {

        //empty constructor
    }

    /**
     *
     * @return typeId
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     *
     * @param typeId String
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     *
     * @return displayName
     */
    public String getDisplayName() {
        return displayName != null ? displayName : "";
    }

    /**
     *
     * @param displayName String
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     *
     * @return active
     */
    public boolean getActive() {
        return active;
    }

    /**
     *
     * @param active boolean
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version String
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     *
     * @return comments
     */
    public String getComments() {
        return comments;
    }

    /**
     *
     * @param comments String
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     *
     * @return codeSystemOid
     */
    public String getCodeSystemOid() {
        return codeSystemOid != null ? codeSystemOid : "";
    }

    /**
     *
     * @param codeSystemOid String
     */
    public void setCodeSystemOid(String codeSystemOid) {
        this.codeSystemOid = codeSystemOid;
    }

    /**
     *
     * @return codeSystemName
     */
    public String getCodeSystemName() {
        return codeSystemName;
    }

    /**
     *
     * @param codeSystem String
     */
    public void setCodeSystemName(String codeSystem) {
        this.codeSystemName = codeSystem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeId != null ? typeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentType)) {
            return false;
        }
        DocumentType other = (DocumentType) object;
        if ((this.typeId == null && other.typeId != null) || (this.typeId != null
            && !this.typeId.equals(other.typeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();

        String lineSeperator = System.getProperty("line.separator");

        String str1 = "DocumentType[";
        String str2 = "\ttypeId=";
        String str3 = "\tdisplayName=";
        String str4 = "\tactive=";
        String str5 = "\tcodeSystemOid=";
        String str6 = ",codeSystem=";
        String str7 = "]";

        str.append(str1);
        str.append(lineSeperator);
        str.append(str2);
        str.append(typeId);
        str.append(lineSeperator);
        str.append(str3);
        str.append(displayName);
        str.append(lineSeperator);
        str.append(str4);
        str.append(active);
        str.append(lineSeperator);
        str.append(str5);
        str.append(codeSystemOid);
        str.append(str6);
        str.append(codeSystemName);
        str.append(str7);

        return str.toString();
    }
}
