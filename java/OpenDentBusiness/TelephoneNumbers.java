//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:59 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;

public class TelephoneNumbers   
{
    /**
    * Used in the tool that loops through the database fixing telephone numbers.  Also used in the patient import from XML tool, carrier edit window, and PT Dental bridge.
    */
    public static String reFormat(String phoneNum) throws Exception {
        if (!StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") && !CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            return phoneNum;
        }
         
        //Not USA or Canadian. en-US or en-CA or fr-CA
        Regex regex = new Regex();
        regex = new Regex("^\\d{10}");
        //eg. 5033635432
        if (regex.IsMatch(phoneNum))
        {
            return "(" + phoneNum.Substring(0, 3) + ")" + phoneNum.Substring(3, 3) + "-" + phoneNum.Substring(6);
        }
         
        regex = new Regex("^\\d{3}-\\d{3}-\\d{4}");
        //eg. 503-363-5432
        if (regex.IsMatch(phoneNum))
        {
            return "(" + phoneNum.Substring(0, 3) + ")" + phoneNum.Substring(4);
        }
         
        regex = new Regex("^\\d-\\d{3}-\\d{3}-\\d{4}");
        //eg. 1-503-363-5432 to 1(503)363-5432
        if (regex.IsMatch(phoneNum))
        {
            return phoneNum.Substring(0, 1) + "(" + phoneNum.Substring(2, 3) + ")" + phoneNum.Substring(6);
        }
         
        regex = new Regex("^\\d{3} \\d{3}-\\d{4}");
        //eg 503 363-5432
        if (regex.IsMatch(phoneNum))
        {
            return "(" + phoneNum.Substring(0, 3) + ")" + phoneNum.Substring(4);
        }
         
        regex = new Regex("^\\d{3} \\d{3} \\d{4}");
        //eg 916 363 5432
        if (regex.IsMatch(phoneNum))
        {
            return "(" + phoneNum.Substring(0, 3) + ")" + phoneNum.Substring(4, 3) + "-" + phoneNum.Substring(8);
        }
         
        regex = new Regex("^\\(\\d{3}\\) \\d{3} \\d{4}");
        //eg (916) 363 5432
        if (regex.IsMatch(phoneNum))
        {
            return "(" + phoneNum.Substring(1, 3) + ")" + phoneNum.Substring(6, 3) + "-" + phoneNum.Substring(10);
        }
         
        regex = new Regex("^\\(\\d{3}\\) \\d{3}-\\d{4}");
        //eg (916) 363-5432
        if (regex.IsMatch(phoneNum))
        {
            return "(" + phoneNum.Substring(1, 3) + ")" + phoneNum.Substring(6, 3) + "-" + phoneNum.Substring(10);
        }
         
        regex = new Regex("^\\d{7}");
        //eg 3635432
        if (regex.IsMatch(phoneNum))
        {
            return (phoneNum.Substring(0, 3) + "-" + phoneNum.Substring(3));
        }
         
        //this must be run after the d{10} match up above.
        regex = new Regex("^\\(\\d{3}-\\d{3}-\\d{4}");
        //eg (916-363-5432
        if (regex.IsMatch(phoneNum))
        {
            return "(" + phoneNum.Substring(1, 3) + ")" + phoneNum.Substring(5, 3) + "-" + phoneNum.Substring(9);
        }
         
        return phoneNum;
    }

    /**
    * reformats initial entry with each keystroke
    */
    public static String autoFormat(String phoneNum) throws Exception {
        if (!StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") && !CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            return phoneNum;
        }
         
        //Not Canadian. en-CA or fr-CA
        if (Regex.IsMatch(phoneNum, "^[2-9]$"))
        {
            return "(" + phoneNum;
        }
         
        if (Regex.IsMatch(phoneNum, "^1\\d$"))
        {
            return "1(" + phoneNum.Substring(1);
        }
         
        if (Regex.IsMatch(phoneNum, "^\\(\\d\\d\\d\\d$"))
        {
            return (phoneNum.Substring(0, 4) + ")" + phoneNum.Substring(4));
        }
         
        if (Regex.IsMatch(phoneNum, "^1\\(\\d\\d\\d\\d$"))
        {
            return (phoneNum.Substring(0, 5) + ")" + phoneNum.Substring(5));
        }
         
        if (Regex.IsMatch(phoneNum, "^\\(\\d\\d\\d\\)\\d\\d\\d\\d$"))
        {
            return (phoneNum.Substring(0, 8) + "-" + phoneNum.Substring(8));
        }
         
        if (Regex.IsMatch(phoneNum, "^1\\(\\d\\d\\d\\)\\d\\d\\d\\d$"))
        {
            return (phoneNum.Substring(0, 9) + "-" + phoneNum.Substring(9));
        }
         
        return phoneNum;
    }

    /**
    * Also truncates if more than two non-numbers in a row.  This is to avoid the notes that can follow phone numbers.
    */
    public static String formatNumbersOnly(String phoneStr) throws Exception {
        String retVal = "";
        int nonnumcount = 0;
        for (int i = 0;i < phoneStr.Length;i++)
        {
            if (nonnumcount == 2)
            {
                return retVal;
            }
             
            if (Char.IsNumber(phoneStr, i))
            {
                retVal += phoneStr.Substring(i, 1);
                nonnumcount = 0;
            }
            else
            {
                nonnumcount++;
            } 
        }
        return retVal;
    }

    /**
    * 
    */
    public static String formatNumbersExactTen(String phoneNum) throws Exception {
        String retVal = "";
        for (int i = 0;i < phoneNum.Length;i++)
        {
            if (Char.IsNumber(phoneNum, i))
            {
                if (StringSupport.equals(retVal, "") && StringSupport.equals(phoneNum.Substring(i, 1), "1"))
                {
                    continue;
                }
                 
                //skip leading 1.
                retVal += phoneNum.Substring(i, 1);
            }
             
            if (retVal.Length == 10)
            {
                return retVal;
            }
             
        }
        return "";
    }

}


//never made it to 10