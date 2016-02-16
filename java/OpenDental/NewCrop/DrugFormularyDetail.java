//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.DrugDetail;


/**
* 
*/
public class DrugFormularyDetail   
{

    private DrugDetail drugDetailField;
    private String formularyCoverageField = new String();
    /**
    * 
    */
    public DrugDetail getdrugDetail() throws Exception {
        return this.drugDetailField;
    }

    public void setdrugDetail(DrugDetail value) throws Exception {
        this.drugDetailField = value;
    }

    /**
    * 
    */
    public String getformularyCoverage() throws Exception {
        return this.formularyCoverageField;
    }

    public void setformularyCoverage(String value) throws Exception {
        this.formularyCoverageField = value;
    }

}


