//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.DrugFormularyDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class DrugFormularyDetailResult   
{

    private Result resultField;
    private DrugFormularyDetail[] drugFormularyDetailArrayField = new DrugFormularyDetail[]();
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
    public DrugFormularyDetail[] getdrugFormularyDetailArray() throws Exception {
        return this.drugFormularyDetailArrayField;
    }

    public void setdrugFormularyDetailArray(DrugFormularyDetail[] value) throws Exception {
        this.drugFormularyDetailArrayField = value;
    }

}


