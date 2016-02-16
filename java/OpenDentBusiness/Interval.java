//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Interval;

/**
* Currently used in recall interval. Uses all four values together to establish an interval between two dates, letting the user have total control.  Will later be used for such things as lab cases, appointment scheduling, etc.  Includes a way to combine all four values into one number to be stored in the database (as an int32).  Each value has a max of 255, except years has a max of 127.
*/
public class Interval   
{
    public Interval() {
    }

    /**
    * 
    */
    public int Years = new int();
    /**
    * 
    */
    public int Months = new int();
    /**
    * 
    */
    public int Weeks = new int();
    /**
    * 
    */
    public int Days = new int();
    /**
    * 
    */
    public Interval(int combinedValue) throws Exception {
        BitVector32 bitVector = new BitVector32(combinedValue);
        BitVector32.Section sectionDays = BitVector32.CreateSection(255);
        BitVector32.Section sectionWeeks = BitVector32.CreateSection(255, sectionDays);
        BitVector32.Section sectionMonths = BitVector32.CreateSection(255, sectionWeeks);
        BitVector32.Section sectionYears = BitVector32.CreateSection(255, sectionMonths);
        Days = bitVector[sectionDays];
        Weeks = bitVector[sectionWeeks];
        Months = bitVector[sectionMonths];
        Years = bitVector[sectionYears];
    }

    /**
    * 
    */
    public Interval(int days, int weeks, int months, int years) throws Exception {
        Days = days;
        Weeks = weeks;
        Months = months;
        Years = years;
    }

    /**
    * Define the == operator.
    */

    /**
    * Define the != operator.
    */

    /**
    * Required to override Equals since we defined == and !=
    */
    public boolean equals(Object o) {
        try
        {
            try
            {
                return (boolean)(this == (Interval)o);
            }
            catch (Exception __dummyCatchVar0)
            {
                return false;
            }
        
        }
        catch (RuntimeException __dummyCatchVar1)
        {
            throw __dummyCatchVar1;
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new RuntimeException(__dummyCatchVar1);
        }
    
    }

    /**
    * Required to override since we defined == and !=
    */
    public int hashCode() {
        try
        {
            return toInt();
        }
        catch (RuntimeException __dummyCatchVar2)
        {
            throw __dummyCatchVar2;
        }
        catch (Exception __dummyCatchVar2)
        {
            throw new RuntimeException(__dummyCatchVar2);
        }
    
    }

    /**
    * Specify a date and an interval to return a new date based on adding the interval to the original date.
    */

    /**
    * 
    */
    public int toInt() throws Exception {
        BitVector32 bitVector = new BitVector32(0);
        BitVector32.Section sectionDays = BitVector32.CreateSection(255);
        BitVector32.Section sectionWeeks = BitVector32.CreateSection(255, sectionDays);
        BitVector32.Section sectionMonths = BitVector32.CreateSection(255, sectionWeeks);
        BitVector32.Section sectionYears = BitVector32.CreateSection(255, sectionMonths);
        bitVector[sectionDays] = Days;
        bitVector[sectionWeeks] = Weeks;
        bitVector[sectionMonths] = Months;
        bitVector[sectionYears] = Years;
        return bitVector.Data;
    }

    /**
    * Example: 1y3m1w1d
    */
    public String toString() {
        try
        {
            String retVal = "";
            if (Years > 0)
            {
                retVal += Years.ToString() + "y";
            }
             
            if (Months > 0)
            {
                retVal += Months.ToString() + "m";
            }
             
            if (Weeks > 0)
            {
                retVal += Weeks.ToString() + "w";
            }
             
            if (Days > 0)
            {
                retVal += Days.ToString() + "d";
            }
             
            return retVal;
        }
        catch (RuntimeException __dummyCatchVar3)
        {
            throw __dummyCatchVar3;
        }
        catch (Exception __dummyCatchVar3)
        {
            throw new RuntimeException(__dummyCatchVar3);
        }
    
    }

}


