//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.DrugHistoryDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class DrugHistoryDetailResult   
{

    private Result resultField;
    private DrugHistoryDetail[] drugHistoryDetailArrayField = new DrugHistoryDetail[]();
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
    public DrugHistoryDetail[] getdrugHistoryDetailArray() throws Exception {
        return this.drugHistoryDetailArrayField;
    }

    public void setdrugHistoryDetailArray(DrugHistoryDetail[] value) throws Exception {
        this.drugHistoryDetailArrayField = value;
    }

}


