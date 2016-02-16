//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.PatientAllergyExtendedDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class PatientAllergyExtendedDetailResult   
{

    private Result resultField;
    private PatientAllergyExtendedDetail[] patientAllergyExtendedDetailField = new PatientAllergyExtendedDetail[]();
    /**
    * 
    */
    public Result getresult() throws Exception {
        return this.resultField;
    }

    public void setresult(Result value) throws Exception {
        this.resultField = value;
    }

    /**
    * 
    */
    public PatientAllergyExtendedDetail[] getpatientAllergyExtendedDetail() throws Exception {
        return this.patientAllergyExtendedDetailField;
    }

    public void setpatientAllergyExtendedDetail(PatientAllergyExtendedDetail[] value) throws Exception {
        this.patientAllergyExtendedDetailField = value;
    }

}


