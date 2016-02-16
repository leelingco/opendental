//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;

import ODR.DataConnection;
import OpenDentBusiness.Defs;

/**
* 
*/
public class MakeReadableDefNum   
{
    //private Hashtable hash;
    /**
    * Constructor
    */
    public MakeReadableDefNum() throws Exception {
        //hash=new Hashtable();
        String command = "SELECT * FROM definition ORDER BY Category,ItemOrder";
        DataConnection dcon = new DataConnection();
        DataTable table = dcon.getTable(command);
        Defs.fillCache(table);
    }

    public String getPaymentType(String defNum) throws Exception {
        return "any";
    }

}


//return DefB.GetName(DefCat.PaymentTypes,PIn.PInt(defNum));