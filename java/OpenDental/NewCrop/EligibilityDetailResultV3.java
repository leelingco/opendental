//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.EligibilityDetailV3;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class EligibilityDetailResultV3   
{

    private Result resultField;
    private String eligibilityGuidField = new String();
    private EligibilityDetailV3[] eligibilityDetailArrayField = new EligibilityDetailV3[]();
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
    public EligibilityDetailV3[] geteligibilityDetailArray() throws Exception {
        return this.eligibilityDetailArrayField;
    }

    public void seteligibilityDetailArray(EligibilityDetailV3[] value) throws Exception {
        this.eligibilityDetailArrayField = value;
    }

}


