//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:36 PM
//

package TestCanada;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;

public class ProviderTC   
{
    public static String setInitialProviders() throws Exception {
        //Dr. A---------------------------------
        Provider priProv = createProvider("A.","Dentist","530123401","1234","DocA");
        //Dr. B---------------------------------
        createProvider("B.","Dentist","035678900","1234","DocB");
        //The billing provider for both is Dr. A.
        Prefs.updateLong(PrefName.PracticeDefaultProv,priProv.ProvNum);
        Prefs.updateLong(PrefName.InsBillingProv,priProv.ProvNum);
        //We create a fake test address for the practice, so that forms looks correct when printed, even though no such test address is provided by CDANet.
        Prefs.updateString(PrefName.PracticeAddress,"123 Test Ave");
        Prefs.updateString(PrefName.PracticeAddress2,"Suite 100");
        Prefs.updateString(PrefName.PracticeCity,"East Westchester");
        Prefs.updateString(PrefName.PracticeST,"ON");
        Prefs.updateString(PrefName.PracticeZip,"M7F2J9");
        Prefs.updateString(PrefName.PracticePhone,"123-456-7890");
        Prefs.refreshCache();
        return "Dentist objects set.\r\n";
    }

    private static Provider createProvider(String fName, String lName, String npi, String officeNum, String abbr) throws Exception {
        Provider prov = null;
        int maxItemOrder = 0;
        for (int i = 0;i < ProviderC.getListLong().Count;i++)
        {
            if (StringSupport.equals(ProviderC.getListLong()[i].NationalProvID, "") || StringSupport.equals(ProviderC.getListLong()[i].NationalProvID, npi))
            {
                prov = ProviderC.getListLong()[i];
            }
             
            if (ProviderC.getListLong()[i].ItemOrder > maxItemOrder)
            {
                maxItemOrder = ProviderC.getListLong()[i].ItemOrder;
            }
             
        }
        boolean updateProv = (prov != null);
        if (prov == null)
        {
            prov = new Provider();
        }
         
        prov.IsHidden = false;
        prov.IsCDAnet = true;
        prov.FName = fName;
        prov.LName = lName;
        prov.NationalProvID = npi;
        prov.CanadianOfficeNum = officeNum;
        prov.Abbr = abbr;
        prov.FeeSched = 53;
        prov.ItemOrder = maxItemOrder + 1;
        if (updateProv)
        {
            Providers.update(prov);
        }
        else
        {
            Providers.insert(prov);
        } 
        Providers.refreshCache();
        return prov;
    }

}


