//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:59 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;

public class PatientLogic   
{
    /**
    * Returns a formatted name, Last, First.
    */
    public static String getNameLF(String LName, String FName, String Preferred, String MiddleI) throws Exception {
        if (StringSupport.equals(LName, ""))
        {
            return "";
        }
         
        if (StringSupport.equals(Preferred, ""))
            return LName + ", " + FName + " " + MiddleI;
        else
            return LName + ", '" + Preferred + "' " + FName + " " + MiddleI; 
    }

    /**
    * Converts a date to an age.  Blank if over 115.  Only used where it's important to show the month, too.  Month will only show if less than 18yo.
    */
    public static String dateToAgeString(DateTime dateBirth) throws Exception {
        return DateToAgeString(dateBirth, DateTime.Now);
    }

    /**
    * Converts a date to an age.  Blank if over 115.  Only used where it's important to show the month, too.  Month will only show if less than 18yo.
    * Use dateTo=DateTime.Now for current age.
    */
    public static String dateToAgeString(DateTime dateBirth, DateTime dateTimeTo) throws Exception {
        if (dateBirth.Year < 1880)
            return "";
         
        if (dateTimeTo.Year < 1880)
        {
            dateTimeTo = DateTime.Now;
        }
         
        if (dateTimeTo < dateBirth)
        {
            return "";
        }
         
        int years = 0;
        int months = 0;
        if (dateBirth.Month < dateTimeTo.Month)
        {
            //birthday was recently in a previous month
            years = dateTimeTo.Year - dateBirth.Year;
            if (dateBirth.Day < dateTimeTo.Day)
            {
                //birthday earlier in the month
                months = (dateTimeTo.Month - dateBirth.Month);
            }
            else if (dateBirth.Day == dateTimeTo.Day)
            {
                //birthday day of month same as today
                months = (dateTimeTo.Month - dateBirth.Month);
            }
            else
            {
                //day of month later than today
                months = (dateTimeTo.Month - dateBirth.Month) - 1;
            }  
        }
        else if (dateBirth.Month == dateTimeTo.Month)
        {
            //birthday this month
            if (dateBirth.Day < dateTimeTo.Day)
            {
                //birthday earlier in this month
                years = dateTimeTo.Year - dateBirth.Year;
                months = 0;
            }
            else if (dateBirth.Month == dateTimeTo.Month && dateBirth.Day == dateTimeTo.Day)
            {
                //today
                years = dateTimeTo.Year - dateBirth.Year;
                months = 0;
            }
            else
            {
                //later this month
                years = dateTimeTo.Year - dateBirth.Year - 1;
                months = 11;
            }  
        }
        else
        {
            //Hasn't had birthday yet this year.  It will be in a future month.
            years = dateTimeTo.Year - dateBirth.Year - 1;
            if (dateBirth.Day < dateTimeTo.Day)
            {
                //birthday earlier in the month
                months = 12 - (dateBirth.Month - dateTimeTo.Month);
            }
            else if (dateBirth.Day == dateTimeTo.Day)
            {
                //birthday day of month same as today
                months = 12 - (dateBirth.Month - dateTimeTo.Month);
            }
            else
            {
                //day of month later than today
                months = 12 - (dateBirth.Month - dateTimeTo.Month) - 1;
            }  
        }  
        if (years < 18)
        {
            return years.ToString() + "y " + months.ToString() + "m";
        }
        else
        {
            return years.ToString();
        } 
    }

}


//return AgeToString(DateToAge(date));