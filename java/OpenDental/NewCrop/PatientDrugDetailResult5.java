//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.PatientDrugDetail5;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class PatientDrugDetailResult5   
{

    private Result resultField;
    private PatientDrugDetail5[] patientDrugDetailField = new PatientDrugDetail5[]();
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
    public PatientDrugDetail5[] getpatientDrugDetail() throws Exception {
        return this.patientDrugDetailField;
    }

    public void setpatientDrugDetail(PatientDrugDetail5[] value) throws Exception {
        this.patientDrugDetailField = value;
    }

}


