//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PIn;

public class X12Parse   
{
    public static DateTime toDate(String element) throws Exception {
        if (element.Length != 8)
        {
            return DateTime.MinValue;
        }
         
        int year = PIn.Int(element.Substring(0, 4));
        int month = PIn.Int(element.Substring(4, 2));
        int day = PIn.Int(element.Substring(6, 2));
        DateTime dt = new DateTime(year, month, day);
        return dt;
    }

    public static String urlDecode(String t) throws Exception {
        t = t.Replace("%3A", ":");
        t = t.Replace("%26", "&");
        t = t.Replace("%2F", "/");
        t = t.Replace("%3D", "=");
        t = t.Replace("%3F", "?");
        return t;
    }

}


//there are more we could do later.