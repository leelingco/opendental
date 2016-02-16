//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package WebHostSynch;

import WebHostSynch.Properties.Settings;

public class DbInit   
{
    static {
        try
        {
            /**
            * being a static constructor this is only called when Init() is called the first time. Further calls to Init() does not call this constructor.
            */
            String connectStr = Settings.getDefault().getDBMobileWeb();
            OpenDentBusiness.DataConnection dc = new OpenDentBusiness.DataConnection();
            dc.setDb(connectStr,"",OpenDentBusiness.DatabaseType.MySql,true);
        }
        catch (Exception __dummyStaticConstructorCatchVar0)
        {
            throw new ExceptionInInitializerError(__dummyStaticConstructorCatchVar0);
        }
    
    }

    /**
    * Empty method with the sole purpose of making sure that that the constructor of this class is called
    */
    public static void init() throws Exception {
    }

}


