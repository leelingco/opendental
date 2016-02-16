//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* 
*/
public class Shared   
{
    /**
    * 
    */
    public Shared() throws Exception {
    }

    /**
    * Converts numbers to ordinals.  For example, 120 to 120th, 73 to 73rd.  Probably doesn't work too well with foreign language translations.  Used in the Birthday postcards.
    */
    public static String numberToOrdinal(int number) throws Exception {
        if (number == 11)
        {
            return "11th";
        }
         
        if (number == 12)
        {
            return "12th";
        }
         
        if (number == 13)
        {
            return "13th";
        }
         
        String str = number.ToString();
        String last = str.Substring(str.Length - 1);
        System.String __dummyScrutVar0 = last;
        if (__dummyScrutVar0.equals("0") || __dummyScrutVar0.equals("4") || __dummyScrutVar0.equals("5") || __dummyScrutVar0.equals("6") || __dummyScrutVar0.equals("7") || __dummyScrutVar0.equals("8") || __dummyScrutVar0.equals("9"))
        {
            return str + "th";
        }
        else if (__dummyScrutVar0.equals("1"))
        {
            return str + "st";
        }
        else if (__dummyScrutVar0.equals("2"))
        {
            return str + "nd";
        }
        else if (__dummyScrutVar0.equals("3"))
        {
            return str + "rd";
        }
            
        return "";
    }

}


