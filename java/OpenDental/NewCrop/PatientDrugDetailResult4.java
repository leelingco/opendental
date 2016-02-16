//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.PatientDrugDetail4;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class PatientDrugDetailResult4   
{

    private Result resultField;
    private PatientDrugDetail4[] patientDrugDetailField = new PatientDrugDetail4[]();
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
    public PatientDrugDetail4[] getpatientDrugDetail() throws Exception {
        return this.patientDrugDetailField;
    }

    public void setpatientDrugDetail(PatientDrugDetail4[] value) throws Exception {
        this.patientDrugDetailField = value;
    }

}


