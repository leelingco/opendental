//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.FormularyCoverageDetailV3;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class FormularyCoverageDetailResultV3   
{

    private Result resultField;
    private FormularyCoverageDetailV3[] formularyCoverageDetailV3ArrayField = new FormularyCoverageDetailV3[]();
    private FormularyCoverageDetailV3[] formularyCoverageAlternativesDetailV3ArrayField = new FormularyCoverageDetailV3[]();
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
    public FormularyCoverageDetailV3[] getformularyCoverageDetailV3Array() throws Exception {
        return this.formularyCoverageDetailV3ArrayField;
    }

    public void setformularyCoverageDetailV3Array(FormularyCoverageDetailV3[] value) throws Exception {
        this.formularyCoverageDetailV3ArrayField = value;
    }

    /**
    * 
    */
    public FormularyCoverageDetailV3[] getformularyCoverageAlternativesDetailV3Array() throws Exception {
        return this.formularyCoverageAlternativesDetailV3ArrayField;
    }

    public void setformularyCoverageAlternativesDetailV3Array(FormularyCoverageDetailV3[] value) throws Exception {
        this.formularyCoverageAlternativesDetailV3ArrayField = value;
    }

}


