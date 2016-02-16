//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.PharmacyDetailV4;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class PharmacyDetailResultV4   
{

    private Result resultField;
    private PharmacyDetailV4[] pharmacyDetailArrayField = new PharmacyDetailV4[]();
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
    public PharmacyDetailV4[] getpharmacyDetailArray() throws Exception {
        return this.pharmacyDetailArrayField;
    }

    public void setpharmacyDetailArray(PharmacyDetailV4[] value) throws Exception {
        this.pharmacyDetailArrayField = value;
    }

}


