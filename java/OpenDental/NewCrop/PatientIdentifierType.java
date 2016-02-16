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
public class PatientIdentifierType   
{

    private String patientIDField = new String();
    private String patientIDTypeField = new String();
    /**
    * 
    */
    public String getpatientID() throws Exception {
        return this.patientIDField;
    }

    public void setpatientID(String value) throws Exception {
        this.patientIDField = value;
    }

    /**
    * 
    */
    public String getpatientIDType() throws Exception {
        return this.patientIDTypeField;
    }

    public void setpatientIDType(String value) throws Exception {
        this.patientIDTypeField = value;
    }

}


