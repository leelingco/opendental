//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.HealthplanDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class HealthplanDetailResult   
{

    private Result resultField;
    private HealthplanDetail[] healthplanDetailField = new HealthplanDetail[]();
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
    public HealthplanDetail[] gethealthplanDetail() throws Exception {
        return this.healthplanDetailField;
    }

    public void sethealthplanDetail(HealthplanDetail[] value) throws Exception {
        this.healthplanDetailField = value;
    }

}


