//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;

public class ProgramPropertyC   
{
    private static List<ProgramProperty> listt = new List<ProgramProperty>();
    /**
    * A list of all program (link) properties.
    */
    public static List<ProgramProperty> getListt() throws Exception {
        if (listt == null)
        {
            ProgramProperties.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<ProgramProperty> value) throws Exception {
        listt = value;
    }

}


