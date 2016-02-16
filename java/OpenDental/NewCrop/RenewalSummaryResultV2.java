//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.RenewalSummaryV2;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class RenewalSummaryResultV2   
{

    private Result resultField;
    private RenewalSummaryV2[] renewalSummaryArrayField = new RenewalSummaryV2[]();
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
    public RenewalSummaryV2[] getrenewalSummaryArray() throws Exception {
        return this.renewalSummaryArrayField;
    }

    public void setrenewalSummaryArray(RenewalSummaryV2[] value) throws Exception {
        this.renewalSummaryArrayField = value;
    }

}


