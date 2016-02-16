//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
* 
*/
public class CounselingMessageDetail   
{

    private String displayOrderField = new String();
    private String professionalMessageField = new String();
    private String patientMessageField = new String();
    /**
    * 
    */
    public String getdisplayOrder() throws Exception {
        return this.displayOrderField;
    }

    public void setdisplayOrder(String value) throws Exception {
        this.displayOrderField = value;
    }

    /**
    * 
    */
    public String getprofessionalMessage() throws Exception {
        return this.professionalMessageField;
    }

    public void setprofessionalMessage(String value) throws Exception {
        this.professionalMessageField = value;
    }

    /**
    * 
    */
    public String getpatientMessage() throws Exception {
        return this.patientMessageField;
    }

    public void setpatientMessage(String value) throws Exception {
        this.patientMessageField = value;
    }

}


