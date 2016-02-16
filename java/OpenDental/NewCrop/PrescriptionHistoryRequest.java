//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
* 
*/
public class PrescriptionHistoryRequest   
{

    private System.DateTime startHistoryField = new System.DateTime();
    private System.DateTime endHistoryField = new System.DateTime();
    private String prescriptionStatusField = new String();
    private String prescriptionSubStatusField = new String();
    private String prescriptionArchiveStatusField = new String();
    /**
    * 
    */
    public System.DateTime getStartHistory() throws Exception {
        return this.startHistoryField;
    }

    public void setStartHistory(System.DateTime value) throws Exception {
        this.startHistoryField = value;
    }

    /**
    * 
    */
    public System.DateTime getEndHistory() throws Exception {
        return this.endHistoryField;
    }

    public void setEndHistory(System.DateTime value) throws Exception {
        this.endHistoryField = value;
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
    public String getPrescriptionArchiveStatus() throws Exception {
        return this.prescriptionArchiveStatusField;
    }

    public void setPrescriptionArchiveStatus(String value) throws Exception {
        this.prescriptionArchiveStatusField = value;
    }

}


