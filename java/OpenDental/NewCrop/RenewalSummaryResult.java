//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.RenewalSummary;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class RenewalSummaryResult   
{

    private Result resultField;
    private RenewalSummary[] renewalSummaryArrayField = new RenewalSummary[]();
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
    public RenewalSummary[] getrenewalSummaryArray() throws Exception {
        return this.renewalSummaryArrayField;
    }

    public void setrenewalSummaryArray(RenewalSummary[] value) throws Exception {
        this.renewalSummaryArrayField = value;
    }

}


