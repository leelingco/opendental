//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.Prefm;
import OpenDentBusiness.Mobile.PrefmC;
import OpenDentBusiness.Mobile.PrefmName;
import OpenDentBusiness.Mobile.Prefms;

public class PrefmC   
{
    public Dictionary<String, Prefm> Dict = new Dictionary<String, Prefm>();
    // cannot have a static variable here because we want something unique for each patient.
    /**
    * Gets a pref of type string.
    */
    public static String getString(PrefmName prefmName) throws Exception {
        try
        {
            PrefmC prefmC = Prefms.loadPreferences();
            if (!prefmC.Dict.ContainsKey(prefmName.ToString()))
            {
                throw new Exception(prefmName + " is an invalid pref name.");
            }
             
            return prefmC.Dict[prefmName.ToString()].ValueString;
        }
        catch (Exception ex)
        {
            return "";
        }
    
    }

}


