//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.PatientFreeFormAllergyExtendedDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class PatientFreeFormAllergyExtendedDetailResult   
{

    private Result resultField;
    private PatientFreeFormAllergyExtendedDetail[] patientFreeFormAllergyExtendedDetailField = new PatientFreeFormAllergyExtendedDetail[]();
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
    public PatientFreeFormAllergyExtendedDetail[] getpatientFreeFormAllergyExtendedDetail() throws Exception {
        return this.patientFreeFormAllergyExtendedDetailField;
    }

    public void setpatientFreeFormAllergyExtendedDetail(PatientFreeFormAllergyExtendedDetail[] value) throws Exception {
        this.patientFreeFormAllergyExtendedDetailField = value;
    }

}


