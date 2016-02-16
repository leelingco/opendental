//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.DentalSpecialty;
import OpenDentBusiness.Provider;

public class X12Generator   
{
    /**
    * If clearhouse.SenderTIN is blank, then 810624427 will be used to indicate Open Dental.
    */
    public static String getISA06(Clearinghouse clearhouse) throws Exception {
        if (StringSupport.equals(clearhouse.SenderTIN, ""))
        {
            return sout("810624427",15,15);
        }
        else
        {
            return sout(clearhouse.SenderTIN,15,15);
        } 
    }

    //TIN of OD.
    //already validated to be length at least 2.
    /**
    * Sometimes SenderTIN, sometimes OD's TIN.
    */
    public static String getGS02(Clearinghouse clearhouse) throws Exception {
        if (StringSupport.equals(clearhouse.SenderTIN, ""))
        {
            return sout("810624427",15,2);
        }
        else
        {
            return sout(clearhouse.SenderTIN,15,2);
        } 
    }

    //already validated to be length at least 2.
    /**
    * Returns the Provider Taxonomy code for the given specialty. Always 10 characters, validated.
    */
    public static String getTaxonomy(Provider provider) throws Exception {
        if (!StringSupport.equals(provider.TaxonomyCodeOverride, ""))
        {
            return provider.TaxonomyCodeOverride;
        }
         
        String spec = "1223G0001X";
        //general
        switch(provider.Specialty)
        {
            case General: 
                spec = "1223G0001X";
                break;
            case Hygienist: 
                spec = "124Q00000X";
                break;
            case PublicHealth: 
                spec = "1223D0001X";
                break;
            case Endodontics: 
                spec = "1223E0200X";
                break;
            case Pathology: 
                spec = "1223P0106X";
                break;
            case Radiology: 
                spec = "1223X0008X";
                break;
            case Surgery: 
                spec = "1223S0112X";
                break;
            case Ortho: 
                spec = "1223X0400X";
                break;
            case Pediatric: 
                spec = "1223P0221X";
                break;
            case Perio: 
                spec = "1223P0300X";
                break;
            case Prosth: 
                spec = "1223P0700X";
                break;
            case Denturist: 
                spec = "122400000X";
                break;
            case Assistant: 
                spec = "126800000X";
                break;
            case LabTech: 
                spec = "126900000X";
                break;
        
        }
        return spec;
    }

    /**
    * Converts any string to an acceptable format for X12. Converts to all caps and strips off all invalid characters. Optionally shortens the string to the specified length and/or makes sure the string is long enough by padding with spaces.
    */
    public static String sout(String inputStr, int maxL, int minL) throws Exception {
        String retStr = inputStr.ToUpper();
        //Debug.Write(retStr+",");
        retStr = Regex.Replace(retStr, "[^\\w!\"&'\\(\\)\\+,-\\./;\\?= #]", "");
        //replaces characters in this input string
        //Allowed: !"&'()+,-./;?=(space)#   # is actually part of extended character set
        //[](any single char)^(that is not)\w(A-Z or 0-9) or one of the above chars.
        retStr = Regex.Replace(retStr, "[_]", "");
        //replaces _
        if (maxL != -1)
        {
            if (retStr.Length > maxL)
            {
                retStr = retStr.Substring(0, maxL);
            }
             
        }
         
        if (minL != -1)
        {
            if (retStr.Length < minL)
            {
                retStr = retStr.PadRight(minL, ' ');
            }
             
        }
         
        return retStr;
    }

}


//Debug.WriteLine(retStr);