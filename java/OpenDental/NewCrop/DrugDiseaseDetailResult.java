//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.DrugDiseaseDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class DrugDiseaseDetailResult   
{

    private Result resultField;
    private DrugDiseaseDetail[] drugDiseaseDetailArrayField = new DrugDiseaseDetail[]();
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
    public DrugDiseaseDetail[] getdrugDiseaseDetailArray() throws Exception {
        return this.drugDiseaseDetailArrayField;
    }

    public void setdrugDiseaseDetailArray(DrugDiseaseDetail[] value) throws Exception {
        this.drugDiseaseDetailArrayField = value;
    }

}


