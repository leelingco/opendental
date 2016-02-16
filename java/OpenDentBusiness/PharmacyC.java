//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Pharmacies;
import OpenDentBusiness.Pharmacy;

public class PharmacyC   
{
    /**
    * A list of all pharmacies.
    */
    private static List<Pharmacy> listt = new List<Pharmacy>();
    public static List<Pharmacy> getListt() throws Exception {
        if (listt == null)
        {
            Pharmacies.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<Pharmacy> value) throws Exception {
        listt = value;
    }

}


