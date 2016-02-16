//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.DrugFormularyFavoriteDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class DrugFormularyFavoriteDetailResult   
{

    private Result resultField;
    private DrugFormularyFavoriteDetail[] drugFormularyFavoriteDetailField = new DrugFormularyFavoriteDetail[]();
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
    public DrugFormularyFavoriteDetail[] getdrugFormularyFavoriteDetail() throws Exception {
        return this.drugFormularyFavoriteDetailField;
    }

    public void setdrugFormularyFavoriteDetail(DrugFormularyFavoriteDetail[] value) throws Exception {
        this.drugFormularyFavoriteDetailField = value;
    }

}


