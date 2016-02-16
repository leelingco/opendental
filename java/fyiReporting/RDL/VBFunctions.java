//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;


/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General public License for more details.
    You should have received a copy of the GNU Lesser General public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* The VBFunctions class holds a number of static functions for support VB functions.
*/
final public class VBFunctions   
{
    /**
    * Obtains the year
    * 
    *  @param dt 
    *  @return int year
    */
    static public int year(DateTime dt) throws Exception {
        return dt.Year;
    }

    /**
    * Returns the integer day of week: 1=Sunday, 2=Monday, ..., 7=Saturday
    * 
    *  @param dt 
    *  @return
    */
    static public int weekday(DateTime dt) throws Exception {
        int dow = new int();
        DayOfWeek __dummyScrutVar0 = dt.DayOfWeek;
        if (__dummyScrutVar0.equals(DayOfWeek.Sunday))
        {
            dow = 1;
        }
        else if (__dummyScrutVar0.equals(DayOfWeek.Monday))
        {
            dow = 2;
        }
        else if (__dummyScrutVar0.equals(DayOfWeek.Tuesday))
        {
            dow = 3;
        }
        else if (__dummyScrutVar0.equals(DayOfWeek.Wednesday))
        {
            dow = 4;
        }
        else if (__dummyScrutVar0.equals(DayOfWeek.Thursday))
        {
            dow = 5;
        }
        else if (__dummyScrutVar0.equals(DayOfWeek.Friday))
        {
            dow = 6;
        }
        else if (__dummyScrutVar0.equals(DayOfWeek.Saturday))
        {
            dow = 7;
        }
        else
        {
            // should never happen
            dow = 1;
        }       
        return dow;
    }

    /**
    * Returns the name of the day of week
    * 
    *  @param d 
    *  @return
    */
    static public String weekdayName(int d) throws Exception {
        return weekdayName(d,false);
    }

    /**
    * Returns the name of the day of week
    * 
    *  @param d 
    *  @param bAbbreviation true for abbreviated name
    *  @return
    */
    static public String weekdayName(int d, boolean bAbbreviation) throws Exception {
        DateTime dt = new DateTime(2005, 5, d);
        // May 1, 2005 is a Sunday
        String wdn = bAbbreviation ? String.Format("{0:ddd}", dt) : String.Format("{0:dddd}", dt);
        return wdn;
    }

    /**
    * Get the day of the month.
    * 
    *  @param dt 
    *  @return
    */
    static public int day(DateTime dt) throws Exception {
        return dt.Day;
    }

    /**
    * Gets the integer month
    * 
    *  @param dt 
    *  @return
    */
    static public int month(DateTime dt) throws Exception {
        return dt.Month;
    }

    /**
    * Get the month name
    * 
    *  @param m 
    *  @return
    */
    static public String monthName(int m) throws Exception {
        return monthName(m,false);
    }

    /**
    * Gets the month name; optionally abbreviated
    * 
    *  @param m 
    *  @param bAbbreviation 
    *  @return
    */
    static public String monthName(int m, boolean bAbbreviation) throws Exception {
        DateTime dt = new DateTime(2005, m, 1);
        String mdn = bAbbreviation ? String.Format("{0:MMM}", dt) : String.Format("{0:MMMM}", dt);
        return mdn;
    }

    /**
    * Gets the hour
    * 
    *  @param dt 
    *  @return
    */
    static public int hour(DateTime dt) throws Exception {
        return dt.Hour;
    }

    /**
    * Get the minute
    * 
    *  @param dt 
    *  @return
    */
    static public int minute(DateTime dt) throws Exception {
        return dt.Minute;
    }

    /**
    * Get the second
    * 
    *  @param dt 
    *  @return
    */
    static public int second(DateTime dt) throws Exception {
        return dt.Second;
    }

    /**
    * Gets the current local date/time on this computer
    * 
    *  @return
    */
    static public DateTime today() throws Exception {
        return DateTime.Now;
    }

    /**
    * Converts the first letter in a string to ANSI code
    * 
    *  @param o 
    *  @return
    */
    static public int asc(String o) throws Exception {
        if (o == null || o.Length == 0)
            return 0;
         
        return Convert.ToInt32(o[0]);
    }

    /**
    * Converts an expression to Boolean
    * 
    *  @param o 
    *  @return
    */
    static public boolean cBool(Object o) throws Exception {
        return Convert.ToBoolean(o);
    }

    /**
    * Converts an expression to type Byte
    * 
    *  @param o 
    *  @return
    */
    static public Byte cByte(String o) throws Exception {
        return Convert.ToByte(o);
    }

    /**
    * Converts an expression to type Currency - really Decimal
    * 
    *  @param o 
    *  @return
    */
    static public double cCur(String o) throws Exception {
        return Convert.ToDecimal(o);
    }

    /**
    * Converts an expression to type Date
    * 
    *  @param o 
    *  @return
    */
    static public DateTime cDate(String o) throws Exception {
        return Convert.ToDateTime(o);
    }

    /**
    * Converts the specified ANSI code to a character
    * 
    *  @param o 
    *  @return
    */
    static public char chr(int o) throws Exception {
        return Convert.ToChar(o);
    }

    /**
    * Converts the expression to integer
    * 
    *  @param o 
    *  @return
    */
    static public int cInt(Object o) throws Exception {
        return Convert.ToInt32(o);
    }

    /**
    * Converts the expression to long
    * 
    *  @param o 
    *  @return
    */
    static public long cLng(Object o) throws Exception {
        return Convert.ToInt64(o);
    }

    /**
    * Converts the expression to Single
    * 
    *  @param o 
    *  @return
    */
    static public Single cSng(Object o) throws Exception {
        return Convert.ToSingle(o);
    }

    /**
    * Converts the expression to String
    * 
    *  @param o 
    *  @return
    */
    static public String cStr(Object o) throws Exception {
        return Convert.ToString(o);
    }

    /**
    * Returns the hexadecimal value of a specified number
    * 
    *  @param o 
    *  @return
    */
    static public String hex(long o) throws Exception {
        return Convert.ToString(o, 16);
    }

    /**
    * Returns the octal value of a specified number
    * 
    *  @param o 
    *  @return
    */
    static public String oct(long o) throws Exception {
        return Convert.ToString(o, 8);
    }

    /**
    * Converts the passed parameter to double
    * 
    *  @param o 
    *  @return
    */
    static public double cDbl(Object o) throws Exception {
        return Convert.ToDouble(o);
    }

    /**
    * 1 based offset of string2 in string1
    * 
    *  @param string1 
    *  @param string2 
    *  @return
    */
    static public int inStr(String string1, String string2) throws Exception {
        return inStr(1,string1,string2,0);
    }

    /**
    * 1 based offset of string2 in string1
    * 
    *  @param start 
    *  @param string1 
    *  @param string2 
    *  @return
    */
    static public int inStr(int start, String string1, String string2) throws Exception {
        return inStr(start,string1,string2,0);
    }

    /**
    * 1 based offset of string2 in string1; optionally case insensitive
    * 
    *  @param string1 
    *  @param string2 
    *  @param compare 1 if you want case insensitive compare
    *  @return
    */
    static public int inStr(String string1, String string2, int compare) throws Exception {
        return inStr(1,string1,string2,compare);
    }

    /**
    * 1 based offset of string2 in string1; optionally case insensitive
    * 
    *  @param start 
    *  @param string1 
    *  @param string2 
    *  @param compare 
    *  @return
    */
    static public int inStr(int start, String string1, String string2, int compare) throws Exception {
        if (string1 == null || string2 == null || string1.Length == 0 || start > string1.Length || start < 1)
            return 0;
         
        if (string2.Length == 0)
            return start;
         
        // Make start zero based
        start--;
        if (start < 0)
            start = 0;
         
        if (compare == 1)
        {
            // Make case insensitive comparison?
            // yes; just make both strings lower case
            string1 = string1.ToLower();
            string2 = string2.ToLower();
        }
         
        int i = string1.IndexOf(string2, start);
        return i + 1;
    }

    // result is 1 based
    /**
    * 1 based offset of string2 in string1 starting from end of string
    * 
    *  @param string1 
    *  @param string2 
    *  @return
    */
    static public int inStrRev(String string1, String string2) throws Exception {
        return inStrRev(string1,string2,-1,0);
    }

    /**
    * 1 based offset of string2 in string1 starting from end of string - start
    * 
    *  @param string1 
    *  @param string2 
    *  @param start 
    *  @return
    */
    static public int inStrRev(String string1, String string2, int start) throws Exception {
        return inStrRev(string1,string2,start,0);
    }

    /**
    * 1 based offset of string2 in string1 starting from end of string - start optionally case insensitive
    * 
    *  @param string1 
    *  @param string2 
    *  @param start 
    *  @param compare 1 for case insensitive comparison
    *  @return
    */
    static public int inStrRev(String string1, String string2, int start, int compare) throws Exception {
        if (string1 == null || string2 == null || string1.Length == 0 || string2.Length > string1.Length)
            return 0;
         
        // TODO this is the brute force method of searching; should use better algorithm
        boolean bCaseSensitive = compare == 1;
        int inc = start == -1 ? string1.Length : start;
        if (inc > string1.Length)
            inc = string1.Length;
         
        while (inc >= string2.Length)
        {
            // go thru the string backwards; but string still needs to long enough to hold find string
            int i = string2.Length - 1;
            for (;i >= 0;i--)
            {
                // match the find string backwards as well
                if (bCaseSensitive)
                {
                    if (Char.ToLower(string1[inc - string2.Length + i]) != string2[i])
                        break;
                     
                }
                else
                {
                    if (string1[inc - string2.Length + i] != string2[i])
                        break;
                     
                } 
            }
            if (i < 0)
                return inc + 1 - string2.Length;
             
            // We got a match
            inc--;
        }
        return 0;
    }

    // No match try next character
    /**
    * Returns the lower case of the passed string
    * 
    *  @param str 
    *  @return
    */
    static public String lCase(String str) throws Exception {
        return str == null ? null : str.ToLower();
    }

    /**
    * Returns the left n characters from the string
    * 
    *  @param str 
    *  @param count 
    *  @return
    */
    static public String left(String str, int count) throws Exception {
        if (str == null || count >= str.Length)
            return str;
        else
            return str.Substring(0, count); 
    }

    /**
    * Returns the length of the string
    * 
    *  @param str 
    *  @return
    */
    static public int len(String str) throws Exception {
        return str == null ? 0 : str.Length;
    }

    /**
    * Removes leading blanks from string
    * 
    *  @param str 
    *  @return
    */
    static public String lTrim(String str) throws Exception {
        if (str == null || str.Length == 0)
            return str;
         
        return str.TrimStart(' ');
    }

    /**
    * Returns the portion of the string denoted by the start and length.
    * 
    *  @param str 
    *  @param start 1 based starting position
    *  @param length length to extract
    *  @return
    */
    static public String mid(String str, int start, int length) throws Exception {
        if (str == null)
            return null;
         
        if (start > str.Length)
            return "";
         
        return str.Substring(start - 1, length);
    }

    //Replace(string,find,replacewith[,start[,count[,compare]]])
    /**
    * Returns string replacing all instances of the searched for text with the replace text.
    * 
    *  @param str 
    *  @param find 
    *  @param replacewith 
    *  @return
    */
    static public String replace(String str, String find, String replacewith) throws Exception {
        return replace(str,find,replacewith,1,-1,0);
    }

    /**
    * Returns string replacing all instances of the searched for text starting at position
    * start with the replace text.
    * 
    *  @param str 
    *  @param find 
    *  @param replacewith 
    *  @param start 
    *  @return
    */
    static public String replace(String str, String find, String replacewith, int start) throws Exception {
        return replace(str,find,replacewith,start,-1,0);
    }

    /**
    * Returns string replacing 'count' instances of the searched for text starting at position
    * start with the replace text.
    * 
    *  @param str 
    *  @param find 
    *  @param replacewith 
    *  @param start 
    *  @param count 
    *  @return
    */
    static public String replace(String str, String find, String replacewith, int start, int count) throws Exception {
        return replace(str,find,replacewith,start,count,0);
    }

    /**
    * Returns string replacing 'count' instances of the searched for text (optionally
    * case insensitive) starting at position start with the replace text.
    * 
    *  @param str 
    *  @param find 
    *  @param replacewith 
    *  @param start 
    *  @param count 
    *  @param compare 1 for case insensitive search
    *  @return
    */
    static public String replace(String str, String find, String replacewith, int start, int count, int compare) throws Exception {
        if (str == null || find == null || find.Length == 0 || count == 0)
            return str;
         
        if (count == -1)
            // user want all changed?
            count = int.MaxValue;
         
        StringBuilder sb = new StringBuilder(str);
        boolean bCaseSensitive = compare != 0;
        // determine if case sensitive; compare = 0 for case sensitive
        if (bCaseSensitive)
            find = find.ToLower();
         
        int inc = 0;
        boolean bReplace = (replacewith != null && replacewith.Length > 0);
        while (inc <= sb.Length - find.Length)
        {
            // TODO this is the brute force method of searching; should use better algorithm
            int i = 0;
            for (;i < find.Length;i++)
            {
                if (bCaseSensitive)
                {
                    if (Char.ToLower(sb[inc + i]) != find[i])
                        break;
                     
                }
                else
                {
                    if (sb[inc + i] != find[i])
                        break;
                     
                } 
            }
            if (i == find.Length)
            {
                // We got a match
                // replace the found string with the replacement string
                sb.Remove(inc, find.Length);
                if (bReplace)
                {
                    sb.Insert(inc, replacewith);
                    inc += (replacewith.Length + 1);
                }
                 
                count--;
                if (count == 0)
                    return sb.ToString();
                 
            }
            else
                // have we done as many replaces as requested?
                // yes, return
                inc++; 
        }
        return sb.ToString();
    }

    // No match try next character
    /**
    * Returns the rightmost length of string.
    * 
    *  @param str 
    *  @param length 
    *  @return
    */
    static public String right(String str, int length) throws Exception {
        if (str == null || str.Length <= length)
            return str;
         
        if (length <= 0)
            return "";
         
        return str.Substring(str.Length - length);
    }

    /**
    * Removes trailing blanks from string.
    * 
    *  @param str 
    *  @return
    */
    static public String rTrim(String str) throws Exception {
        if (str == null || str.Length == 0)
            return str;
         
        return str.TrimEnd(' ');
    }

    /**
    * Returns blank string of the specified length
    * 
    *  @param length 
    *  @return
    */
    static public String space(int length) throws Exception {
        return string(length,' ');
    }

    //StrComp(string1,string2[,compare])
    /**
    * Compares the strings. When string1 < string2: -1, string1 = string2: 0, string1 > string2: 1
    * 
    *  @param string1 
    *  @param string2 
    *  @return
    */
    static public int strComp(String string1, String string2) throws Exception {
        return strComp(string1,string2,0);
    }

    /**
    * Compares the strings; optionally with case insensitivity. When string1 < string2: -1, string1 = string2: 0, string1 > string2: 1
    * 
    *  @param string1 
    *  @param string2 
    *  @param compare 1 for case insensitive comparison
    *  @return
    */
    static public int strComp(String string1, String string2, int compare) throws Exception {
        if (string1 == null || string2 == null)
            return 0;
         
        return compare == 0 ? string1.CompareTo(string2) : string1.ToLower().CompareTo(string2.ToLower());
    }

    // not technically correct; should return null
    /**
    * Return string with the character repeated for the length
    * 
    *  @param length 
    *  @param c 
    *  @return
    */
    static public String string(int length, char c) throws Exception {
        if (length <= 0)
            return "";
         
        StringBuilder sb = new StringBuilder(length, length);
        for (int i = 0;i < length;i++)
            sb.Append(c);
        return sb.ToString();
    }

    /**
    * Returns a string with the characters reversed.
    * 
    *  @param str 
    *  @return
    */
    static public String strReverse(String str) throws Exception {
        if (str == null || str.Length < 2)
            return str;
         
        StringBuilder sb = new StringBuilder(str, str.Length);
        int i = str.Length - 1;
        for (Object __dummyForeachVar0 : str)
        {
            char c = (Character)__dummyForeachVar0;
            sb[i--] = c;
        }
        return sb.ToString();
    }

    /**
    * Removes whitespace from beginning and end of string.
    * 
    *  @param str 
    *  @return
    */
    static public String trim(String str) throws Exception {
        if (str == null || str.Length == 0)
            return str;
         
        return str.Trim(' ');
    }

    /**
    * Returns the uppercase version of the string
    * 
    *  @param str 
    *  @return
    */
    static public String uCase(String str) throws Exception {
        return str == null ? null : str.ToUpper();
    }

}


