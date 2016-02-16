//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.PatientDrugDetail5;
import OpenDental.NewCrop.TransmissionDetail;


/**
* 
*/
public class TransmissionSummary   
{

    private String externalIdField = new String();
    private System.Guid prescriptionGuidField = new System.Guid();
    private String prescriptionStatusField = new String();
    private String prescriptionSubStatusField = new String();
    private String prescriptionArchiveField = new String();
    private String summaryMessageField = new String();
    private String summaryXmlResponseField = new String();
    private int transmissionDetailCountField = new int();
    private PatientDrugDetail5 drugDetailField;
    private TransmissionDetail[] transmissionDetailArrayField = new TransmissionDetail[]();
    /**
    * 
    */
    public String getExternalId() throws Exception {
        return this.externalIdField;
    }

    public void setExternalId(String value) throws Exception {
        this.externalIdField = value;
    }

    /**
    * 
    */
    public System.Guid getPrescriptionGuid() throws Exception {
        return this.prescriptionGuidField;
    }

    public void setPrescriptionGuid(System.Guid value) throws Exception {
        this.prescriptionGuidField = value;
    }

    /**
    * 
    */
    public String getPrescriptionStatus() throws Exception {
        return this.prescriptionStatusField;
    }

    public void setPrescriptionStatus(String value) throws Exception {
        this.prescriptionStatusField = value;
    }

    /**
    * 
    */
    public String getPrescriptionSubStatus() throws Exception {
        return this.prescriptionSubStatusField;
    }

    public void setPrescriptionSubStatus(String value) throws Exception {
        this.prescriptionSubStatusField = value;
    }

    /**
    * 
    */
    public String getPrescriptionArchive() throws Exception {
        return this.prescriptionArchiveField;
    }

    public void setPrescriptionArchive(String value) throws Exception {
        this.prescriptionArchiveField = value;
    }

    /**
    * 
    */
    public String getSummaryMessage() throws Exception {
        return this.summaryMessageField;
    }

    public void setSummaryMessage(String value) throws Exception {
        this.summaryMessageField = value;
    }

    /**
    * 
    */
    public String getSummaryXmlResponse() throws Exception {
        return this.summaryXmlResponseField;
    }

    public void setSummaryXmlResponse(String value) throws Exception {
        this.summaryXmlResponseField = value;
    }

    /**
    * 
    */
    public int getTransmissionDetailCount() throws Exception {
        return this.transmissionDetailCountField;
    }

    public void setTransmissionDetailCount(int value) throws Exception {
        this.transmissionDetailCountField = value;
    }

    /**
    * 
    */
    public PatientDrugDetail5 getdrugDetail() throws Exception {
        return this.drugDetailField;
    }

    public void setdrugDetail(PatientDrugDetail5 value) throws Exception {
        this.drugDetailField = value;
    }

    /**
    * 
    */
    public TransmissionDetail[] gettransmissionDetailArray() throws Exception {
        return this.transmissionDetailArrayField;
    }

    public void settransmissionDetailArray(TransmissionDetail[] value) throws Exception {
        this.transmissionDetailArrayField = value;
    }

}


