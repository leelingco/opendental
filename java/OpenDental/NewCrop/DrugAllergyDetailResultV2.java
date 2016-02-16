//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.DrugAllergyDetailV2;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class DrugAllergyDetailResultV2   
{

    private Result resultField;
    private DrugAllergyDetailV2[] drugAllergyDetailArrayV2Field = new DrugAllergyDetailV2[]();
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
    public DrugAllergyDetailV2[] getdrugAllergyDetailArrayV2() throws Exception {
        return this.drugAllergyDetailArrayV2Field;
    }

    public void setdrugAllergyDetailArrayV2(DrugAllergyDetailV2[] value) throws Exception {
        this.drugAllergyDetailArrayV2Field = value;
    }

}


