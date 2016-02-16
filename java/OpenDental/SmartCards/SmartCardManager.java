//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;

import OpenDental.SmartCards.ISmartCardManager;
import OpenDental.SmartCards.WindowsSmartCardManager;

public class SmartCardManager   
{
    /**
    * Gets the 
    *  {@link #ISmartCardManager}
    *  for the current operation system.
    * 
    *  @return 
    * A 
    *  {@link #ISmartCardManager}
    *  if the current operation system is supported, else, 
    *  {@link #}
    * .
    */
    public static ISmartCardManager load() throws Exception {
        if (Environment.OSVersion.Platform == PlatformID.Win32NT)
        {
            return new WindowsSmartCardManager();
        }
        else
        {
            return null;
        } 
    }

}


