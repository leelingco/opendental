//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.RenewalSummaryV4;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class RenewalListSummaryResultV4   
{

    private Result resultField;
    private RenewalSummaryV4[] renewalListDetailArrayField = new RenewalSummaryV4[]();
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
    public RenewalSummaryV4[] getrenewalListDetailArray() throws Exception {
        return this.renewalListDetailArrayField;
    }

    public void setrenewalListDetailArray(RenewalSummaryV4[] value) throws Exception {
        this.renewalListDetailArrayField = value;
    }

}


