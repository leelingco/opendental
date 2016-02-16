//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import OpenDentBusiness.ClockEvent;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.RxPat;
import OpenDentBusiness.TimeAdjust;

/**
* This sorts a variety objects based on their dates and times.
*/
public class ObjectDateComparer  extends IComparer 
{
    /**
    * This sorts a variety objects based on their dates and times: Procedure, RxPat, Commlog, ClockEvent, TimeAdjust
    */
    int iComparer___Compare(Object x, Object y) throws Exception {
        DateTime datex = new DateTime();
        DateTime datey = new DateTime();
        Type typex = x.GetType();
        Type typey = y.GetType();
        if (typex == Procedure.class)
        {
            datex = ((Procedure)x).ProcDate;
        }
        else if (typex == RxPat.class)
        {
            datex = ((RxPat)x).RxDate;
        }
        else if (typex == Commlog.class)
        {
            datex = ((Commlog)x).CommDateTime;
        }
        else if (typex == ClockEvent.class)
        {
            datex = ((ClockEvent)x).TimeDisplayed1;
        }
        else if (typex == TimeAdjust.class)
        {
            datex = ((TimeAdjust)x).TimeEntry;
        }
        else
        {
            throw new Exception("Types don't match");
        }     
        //only for debugging.
        if (typey == Procedure.class)
        {
            datey = ((Procedure)y).ProcDate;
        }
        else if (typey == RxPat.class)
        {
            datey = ((RxPat)y).RxDate;
        }
        else if (typey == Commlog.class)
        {
            datey = ((Commlog)y).CommDateTime;
        }
        else if (typey == ClockEvent.class)
        {
            datey = ((ClockEvent)y).TimeDisplayed1;
        }
        else if (typey == TimeAdjust.class)
        {
            datey = ((TimeAdjust)y).TimeEntry;
        }
        else
        {
            throw new Exception("Types don't match");
        }     
        return datex.CompareTo(datey);
    }

}


//only for debugging.