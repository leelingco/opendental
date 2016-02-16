//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package UnitTests;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

public class ToothT   
{
    public static String formatRanges() throws Exception {
        //PrefC.DictRef=new Dictionary<string,Pref>();
        //Pref pref=new Pref();
        //pref.PrefName="UseInternationalToothNumbers";
        //pref.ValueString="0";
        //PrefC.DictRef.Add(pref.PrefName,pref);
        Prefs.refreshCache();
        if (Prefs.updateInt(PrefName.UseInternationalToothNumbers,0))
        {
            Prefs.refreshCache();
        }
         
        //for display----------------------------------------------------------------
        String inputrange = "1,2,3,4,5,7,8,9,11,12,15,16,17,18,21,22,23";
        String result = OpenDentBusiness.Tooth.formatRangeForDisplay(inputrange);
        String desired = "1-5,7-9,11,12,15,16,17,18,21-23";
        if (!StringSupport.equals(result, desired))
        {
            throw new ApplicationException("ToothFormatRangeForDisplay failed.  Desired: " + desired + " Result: " + result + "\r\n");
        }
         
        inputrange = "2,4,5,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,25";
        result = OpenDentBusiness.Tooth.formatRangeForDisplay(inputrange);
        desired = "2,4,5,7-9,11-16,17-23,25";
        if (!StringSupport.equals(result, desired))
        {
            throw new ApplicationException("ToothFormatRangeForDisplay failed.  Desired: " + desired + " Result: " + result + "\r\n");
        }
         
        inputrange = "4,5,2, 7,8,9,11 ,13,12,14,15,16,17,18 ,20, 21,22,23,19,25";
        result = OpenDentBusiness.Tooth.formatRangeForDisplay(inputrange);
        desired = "2,4,5,7-9,11-16,17-23,25";
        if (!StringSupport.equals(result, desired))
        {
            throw new ApplicationException("ToothFormatRangeForDisplay failed.  Desired: " + desired + " Result: " + result + "\r\n");
        }
         
        //for database------------------------------------------------------------------
        inputrange = "1-5,7-9,11,12,15,16,17,18,21-23";
        result = OpenDentBusiness.Tooth.formatRangeForDb(inputrange);
        desired = "1,2,3,4,5,7,8,9,11,12,15,16,17,18,21,22,23";
        if (!StringSupport.equals(result, desired))
        {
            throw new ApplicationException("ToothFormatRangeForDb failed.  Desired: " + desired + " Result: " + result + "\r\n");
        }
         
        inputrange = "2,4,5,7-9,11-16,17-23,25";
        result = OpenDentBusiness.Tooth.formatRangeForDb(inputrange);
        desired = "2,4,5,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,25";
        if (!StringSupport.equals(result, desired))
        {
            throw new ApplicationException("ToothFormatRangeForDb failed.  Desired: " + desired + " Result: " + result + "\r\n");
        }
         
        inputrange = "4,2,5,7-9 ,11-16,25 ,  17-23";
        result = OpenDentBusiness.Tooth.formatRangeForDb(inputrange);
        desired = "2,4,5,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,25";
        if (!StringSupport.equals(result, desired))
        {
            throw new ApplicationException("ToothFormatRangeForDb failed.  Desired: " + desired + " Result: " + result + "\r\n");
        }
         
        return "Tooth FormatRanges passed.\r\n";
    }

}


//we still haven't tested really bad input.