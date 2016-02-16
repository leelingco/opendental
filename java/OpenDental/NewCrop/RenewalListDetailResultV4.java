//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.RenewalDetailV4;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class RenewalListDetailResultV4   
{

    private Result resultField;
    private RenewalDetailV4[] renewalListDetailArrayField = new RenewalDetailV4[]();
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
    public RenewalDetailV4[] getrenewalListDetailArray() throws Exception {
        return this.renewalListDetailArrayField;
    }

    public void setrenewalListDetailArray(RenewalDetailV4[] value) throws Exception {
        this.renewalListDetailArrayField = value;
    }

}


