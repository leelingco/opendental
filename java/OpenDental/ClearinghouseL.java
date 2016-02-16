//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clearinghouses;

/**
* 
*/
public class ClearinghouseL   
{
    /**
    * Returns the clearinghouse specified by the given num.
    */
    public static Clearinghouse getClearinghouse(long clearinghouseNum) throws Exception {
        return getClearinghouse(clearinghouseNum,false);
    }

    /**
    * Returns the clearinghouse specified by the given num.
    */
    public static Clearinghouse getClearinghouse(long clearinghouseNum, boolean suppressError) throws Exception {
        for (int i = 0;i < Clearinghouses.getListt().Length;i++)
        {
            if (Clearinghouses.getListt()[i].ClearinghouseNum == clearinghouseNum)
            {
                return Clearinghouses.getListt()[i];
            }
             
        }
        if (!suppressError)
        {
            MessageBox.Show("Error. Could not locate Clearinghouse.");
        }
         
        return null;
    }

    /**
    * Gets the index of this clearinghouse within List
    */
    public static int getIndex(long clearinghouseNum) throws Exception {
        for (int i = 0;i < Clearinghouses.getListt().Length;i++)
        {
            if (Clearinghouses.getListt()[i].ClearinghouseNum == clearinghouseNum)
            {
                return i;
            }
             
        }
        MessageBox.Show("Clearinghouses.GetIndex failed.");
        return -1;
    }

    /**
    * 
    */
    public static String getDescript(long clearinghouseNum) throws Exception {
        if (clearinghouseNum == 0)
        {
            return "";
        }
         
        return getClearinghouse(clearinghouseNum).Description;
    }

}


