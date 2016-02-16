//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//

package OpenDentBusiness;


public class TimeSpanExtension   
{
    /**
    * -H:mm.  If zero, then returns empty string.  Hours can be greater than 24.
    */
    public static String toStringHmm(/* parameter modifiers are not yet supported this */TimeSpan tspan) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (tspan == TimeSpan.Zero)
        {
            return "";
        }
         
        String retVal = "";
        if (tspan < TimeSpan.Zero)
        {
            retVal += "-";
            tspan = tspan.Duration();
        }
         
        //It has to be done this way to support hours greater than 24.
        int hours = (tspan.Days * 24) + tspan.Hours;
        retVal += hours.ToString() + ":" + tspan.Minutes.ToString().PadLeft(2, '0');
        return retVal;
    }

    /**
    * -H:mm:ss.  If zero, then returns empty string.
    */
    public static String toStringHmmss(/* parameter modifiers are not yet supported this */TimeSpan tspan) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (tspan == TimeSpan.Zero)
        {
            return "";
        }
         
        String retVal = "";
        if (tspan < TimeSpan.Zero)
        {
            retVal += "-";
            tspan = tspan.Duration();
        }
         
        int hours = (tspan.Days * 24) + tspan.Hours;
        retVal += hours.ToString() + ":" + tspan.Minutes.ToString().PadLeft(2, '0') + ":" + tspan.Seconds.ToString().PadLeft(2, '0');
        return retVal;
    }

    /**
    * -mm:ss.  If zero, then returns empty string.
    */
    public static String toStringmmss(/* parameter modifiers are not yet supported this */TimeSpan tspan) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (tspan == TimeSpan.Zero)
        {
            return "";
        }
         
        String retVal = "";
        if (tspan < TimeSpan.Zero)
        {
            retVal += "-";
            tspan = tspan.Duration();
        }
         
        retVal += ((int)tspan.TotalMinutes).ToString().PadLeft(2, '0') + ":" + tspan.Seconds.ToString().PadLeft(2, '0');
        return retVal;
    }

    /**
    * Does not work well with negative values.
    */
    public static String toString(/* parameter modifiers are not yet supported this */TimeSpan tspan, String format) throws Exception {
        //No need to check RemotingRole; no call to db.
        DateTime dt = DateTime.Today;
        dt = dt + tspan;
        return dt.ToString(format);
    }

    /**
    * Does not work well with negative values.
    */
    public static String toShortTimeString(/* parameter modifiers are not yet supported this */TimeSpan tspan) throws Exception {
        //No need to check RemotingRole; no call to db.
        DateTime dt = DateTime.Today;
        dt = dt + tspan;
        return dt.ToShortTimeString();
    }

}


