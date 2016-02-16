//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;

import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PatientStatus;

/**
* 
*/
public class MakeReadable   
{
    /**
    * 
    */
    public static String patStatus(String patStatus) throws Exception {
        return Enum.GetName(PatientStatus.class, Convert.ToInt32(patStatus));
    }

    //public static string DefNumPaymentType(string defNum){
    //	return DefB.GetName(DefCat.PaymentTypes,PIn.PInt(defNum));
    //}
    public static String query(String query, String parameters) throws Exception {
        MessageBox.Show("query: " + query);
        MessageBox.Show("parameter info: " + parameters);
        return "SELECT * FROM patient " + DbHelper.limitWhere(10);
    }

}


