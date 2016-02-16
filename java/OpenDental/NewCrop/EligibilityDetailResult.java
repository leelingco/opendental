//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.EligibilityDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class EligibilityDetailResult   
{

    private Result resultField;
    private String eligibilityGuidField = new String();
    private EligibilityDetail[] eligibilityDetailArrayField = new EligibilityDetail[]();
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
    public String geteligibilityGuid() throws Exception {
        return this.eligibilityGuidField;
    }

    public void seteligibilityGuid(String value) throws Exception {
        this.eligibilityGuidField = value;
    }

    /**
    * 
    */
    public EligibilityDetail[] geteligibilityDetailArray() throws Exception {
        return this.eligibilityDetailArrayField;
    }

    public void seteligibilityDetailArray(EligibilityDetail[] value) throws Exception {
        this.eligibilityDetailArrayField = value;
    }

}


