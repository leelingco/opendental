//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:27 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;

public class CreditCardUtils   
{
    public static String getType(String ccNum) throws Exception {
        if (ccNum == null || StringSupport.equals(ccNum, ""))
        {
            return "";
        }
         
        ccNum = stripNonDigits(ccNum);
        if (ccNum.StartsWith("4"))
        {
            return "VISA";
        }
         
        if (ccNum.StartsWith("5"))
        {
            return "MASTERCARD";
        }
         
        if (ccNum.StartsWith("34") || ccNum.StartsWith("37"))
        {
            return "AMEX";
        }
         
        if (ccNum.StartsWith("30") || ccNum.StartsWith("36") || ccNum.StartsWith("38"))
        {
            return "DINERS";
        }
         
        if (ccNum.StartsWith("6011"))
        {
            return "DISCOVER";
        }
         
        return "";
    }

    private static boolean isValidVisaNumber(String ccNum) throws Exception {
        char[] number = ccNum.ToCharArray();
        int len = number.Length;
        if (len != 16 && len != 13)
        {
            return false;
        }
         
        if (number[0] != '4')
        {
            return false;
        }
         
        return checkMOD10(ccNum);
    }

    private static boolean isValidMasterCardNumber(String ccNum) throws Exception {
        char[] number = ccNum.ToCharArray();
        int len = number.Length;
        if (len != 16)
        {
            return false;
        }
         
        if (number[0] != '5' || number[1] == '0' || number[1] > '5')
        {
            return false;
        }
         
        return checkMOD10(ccNum);
    }

    private static boolean isValidAmexNumber(String ccNum) throws Exception {
        char[] number = ccNum.ToCharArray();
        int len = number.Length;
        if (len != 15)
        {
            return false;
        }
         
        if (number[0] != '3' || (number[1] != '4' && number[1] != '7'))
        {
            return false;
        }
         
        return checkMOD10(ccNum);
    }

    private static boolean isValidDinersNumber(String ccNum) throws Exception {
        char[] number = ccNum.ToCharArray();
        int len = number.Length;
        if (len != 14)
        {
            return false;
        }
         
        if (number[0] != '3' || (number[1] != '0' && number[1] != '6' && number[1] != '8') || number[1] == '0' && number[2] > '5')
        {
            return false;
        }
         
        return checkMOD10(ccNum);
    }

    private static boolean isValidDiscoverNumber(String ccNum) throws Exception {
        char[] number = ccNum.ToCharArray();
        int len = number.Length;
        if (len != 16)
        {
            return false;
        }
         
        if (number[0] != '6' || number[1] != '0' || number[2] != '1' || number[3] != '1')
        {
            return false;
        }
         
        return checkMOD10(ccNum);
    }

    /**
    * Strips non-digit characters from a string. Returns the modified string, or null if 's' is null.
    */
    public static String stripNonDigits(String s) throws Exception {
        return StripNonDigits(s, new char[]{  });
    }

    /**
    * Strips non-digit characters from a string. The variable s is the string to strip. The allowed array must contain characters that should not be stripped. Returns the modified string, or null if 's' is null.
    */
    public static String stripNonDigits(String s, char[] allowed) throws Exception {
        if (s == null)
        {
            return null;
        }
         
        StringBuilder buff = new StringBuilder(s);
        StripNonDigits(buff, allowed);
        return buff.ToString();
    }

    /**
    * Strips non-digit characters from a string. The variable s is the string to strip.
    */
    public static void stripNonDigits(StringBuilder s) throws Exception {
        StripNonDigits(s, new char[]{  });
    }

    /**
    * Strips non-digit characters from a string. The variable s is the string to strip. The allowed array must contain the characters that should not be stripped.
    */
    public static void stripNonDigits(StringBuilder s, char[] allowed) throws Exception {
        for (int i = 0;i < s.Length;i++)
        {
            if (!Char.IsDigit(s[i]) && !ContainsCharacter(s[i], allowed))
            {
                s.Remove(i, 1);
                i--;
            }
             
        }
    }

    /**
    * Searches a character array for the presence of the given character. Variable c is the character to search for. The search array is the array to search in. Returns true if the character is present in the array.  false otherwise.
    */
    public static boolean containsCharacter(char c, char[] search) throws Exception {
        for (Object __dummyForeachVar0 : search)
        {
            char x = (Character)__dummyForeachVar0;
            if (c == x)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Performs a MOD10 check against a string. This is the check that is used to validate credit card numbers, but can be used on other numbers, as well. The algorithm is: Starting with the least significant digit, sum all odd-numbered digits; separately, sum two times each even-numbered digit (if this is greater than 10, bring it into single-digit range by subtracting 9). Then add both totals and divide by 10. If there is no remainder then the value passes the check. The variable value is the value to check, which must be all digits. Returns true iff the value passes the MOD10 check.
    */
    public static boolean checkMOD10(String value) throws Exception {
        if (value == null)
        {
            throw new NullReferenceException("Value is null.");
        }
         
        value = stripNonDigits(value);
        int sum = 0;
        int count = 0;
        for (int i = value.Length - 1;i >= 0;i--)
        {
            count++;
            int digit = int.Parse(value.Substring(i, 1));
            if ((count % 2) == 1)
            {
                sum += digit;
            }
            else
            {
                int tmp = digit * 2;
                if (tmp >= 10)
                {
                    tmp -= 9;
                }
                 
                sum += tmp;
            } 
        }
        return ((sum % 10) == 0);
    }

    /**
    * Return bool if value passed in is numeric only
    */
    public static boolean isNumeric(String str) throws Exception {
        if (str == null)
        {
            return false;
        }
         
        Regex objNotWholePattern = new Regex("[^0-9]");
        return !objNotWholePattern.IsMatch(str);
    }

}


