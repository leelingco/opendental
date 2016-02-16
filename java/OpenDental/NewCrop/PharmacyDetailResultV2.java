//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.PharmacyDetailV2;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class PharmacyDetailResultV2   
{

    private Result resultField;
    private PharmacyDetailV2[] pharmacyDetailArrayField = new PharmacyDetailV2[]();
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
    public PharmacyDetailV2[] getpharmacyDetailArray() throws Exception {
        return this.pharmacyDetailArrayField;
    }

    public void setpharmacyDetailArray(PharmacyDetailV2[] value) throws Exception {
        this.pharmacyDetailArrayField = value;
    }

}


