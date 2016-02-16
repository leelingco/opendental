//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.PharmacyDetail;
import OpenDental.NewCrop.TransmissionMethodType;


/**
* 
*/
public class TransmissionDetail   
{

    private TransmissionMethodType transmissionMethodField = TransmissionMethodType.Print;
    private String transmissionNetworkField = new String();
    private System.Guid transactionGuidField = new System.Guid();
    private System.Guid transactionDetailGuidField = new System.Guid();
    private System.DateTime timestampField = new System.DateTime();
    private String userIdField = new String();
    private String statusMessageField = new String();
    private String detailMessageField = new String();
    private String detailXmlResponseField = new String();
    private PharmacyDetail pharmacyDetailField;
    /**
    * 
    */
    public TransmissionMethodType getTransmissionMethod() throws Exception {
        return this.transmissionMethodField;
    }

    public void setTransmissionMethod(TransmissionMethodType value) throws Exception {
        this.transmissionMethodField = value;
    }

    /**
    * 
    */
    public String getTransmissionNetwork() throws Exception {
        return this.transmissionNetworkField;
    }

    public void setTransmissionNetwork(String value) throws Exception {
        this.transmissionNetworkField = value;
    }

    /**
    * 
    */
    public System.Guid getTransactionGuid() throws Exception {
        return this.transactionGuidField;
    }

    public void setTransactionGuid(System.Guid value) throws Exception {
        this.transactionGuidField = value;
    }

    /**
    * 
    */
    public System.Guid getTransactionDetailGuid() throws Exception {
        return this.transactionDetailGuidField;
    }

    public void setTransactionDetailGuid(System.Guid value) throws Exception {
        this.transactionDetailGuidField = value;
    }

    /**
    * 
    */
    public System.DateTime getTimestamp() throws Exception {
        return this.timestampField;
    }

    public void setTimestamp(System.DateTime value) throws Exception {
        this.timestampField = value;
    }

    /**
    * 
    */
    public String getUserId() throws Exception {
        return this.userIdField;
    }

    public void setUserId(String value) throws Exception {
        this.userIdField = value;
    }

    /**
    * 
    */
    public String getStatusMessage() throws Exception {
        return this.statusMessageField;
    }

    public void setStatusMessage(String value) throws Exception {
        this.statusMessageField = value;
    }

    /**
    * 
    */
    public String getDetailMessage() throws Exception {
        return this.detailMessageField;
    }

    public void setDetailMessage(String value) throws Exception {
        this.detailMessageField = value;
    }

    /**
    * 
    */
    public String getDetailXmlResponse() throws Exception {
        return this.detailXmlResponseField;
    }

    public void setDetailXmlResponse(String value) throws Exception {
        this.detailXmlResponseField = value;
    }

    /**
    * 
    */
    public PharmacyDetail getpharmacyDetail() throws Exception {
        return this.pharmacyDetailField;
    }

    public void setpharmacyDetail(PharmacyDetail value) throws Exception {
        this.pharmacyDetailField = value;
    }

}


