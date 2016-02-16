//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.RenewalDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class RenewalDetailResult   
{

    private Result resultField;
    private RenewalDetail renewalDetailField;
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
    public RenewalDetail getrenewalDetail() throws Exception {
        return this.renewalDetailField;
    }

    public void setrenewalDetail(RenewalDetail value) throws Exception {
        this.renewalDetailField = value;
    }

}


