//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.SigElement;
import OpenDentBusiness.Signal;
import OpenDentBusiness.SignalType;
import OpenDentBusiness.TableBase;

/**
* An actual signal that gets sent out as part of the messaging functionality.
*/
public class Signal  extends TableBase implements IComparable
{
    /**
    * Primary key.
    */
    public long SignalNum = new long();
    /**
    * Text version of 'user' this message was sent from, which can actually be any description of a group or individual.
    */
    public String FromUser = new String();
    /**
    * Enum:InvalidType List of InvalidType long values separated by commas.  Can be empty.  When Date or Tasks are used, they are used all alone with no other flags present.
    */
    public String ITypes = new String();
    /**
    * If IType=Date, then this is the affected date in the Appointments module.
    */
    public DateTime DateViewing = new DateTime();
    /**
    * Enum:SignalType  Button, or Invalid.
    */
    public SignalType SigType = SignalType.Button;
    /**
    * This is only used if the type is button, and the user types in some text.  This is the typed portion and does not include any of the text that was on the buttons.  These types of signals are displayed in their own separate list in addition to any light and sound that they may cause.
    */
    public String SigText = new String();
    /**
    * The exact server time when this signal was entered into db.  This does not need to be set by sender since it's handled automatically.
    */
    public DateTime SigDateTime = new DateTime();
    /**
    * Text version of 'user' this message was sent to, which can actually be any description of a group or individual.
    */
    public String ToUser = new String();
    /**
    * If this signal has been acknowledged, then this will contain the date and time.  This is how lights get turned off also.
    */
    public DateTime AckTime = new DateTime();
    /**
    * FK to task.TaskNum.  If IType=Tasks, then this is the taskNum that was added.
    */
    public long TaskNum = new long();
    /**
    * Not a database field.  The sounds and lights attached to the signal.
    */
    public SigElement[] ElementList = new SigElement[]();
    /**
    * IComparable.CompareTo implementation.  This is used to order signals.  This is needed because ordering signals is too complex to do with a query.
    */
    public int compareTo(Object obj) throws Exception {
        if (!(obj instanceof Signal))
        {
            throw new ArgumentException("object is not a Signal");
        }
         
        Signal sig = (Signal)obj;
        DateTime date1 = new DateTime();
        DateTime date2 = new DateTime();
        if (AckTime.Year < 1880)
        {
            //if not acknowledged
            date1 = SigDateTime;
        }
        else
        {
            date1 = AckTime;
        } 
        if (sig.AckTime.Year < 1880)
        {
            //if not acknowledged
            date2 = sig.SigDateTime;
        }
        else
        {
            date2 = sig.AckTime;
        } 
        return date1.CompareTo(date2);
    }

    /**
    * 
    */
    public Signal copy() throws Exception {
        Signal s = (Signal)this.MemberwiseClone();
        s.ElementList = new SigElement[ElementList.Length];
        ElementList.CopyTo(s.ElementList, 0);
        return s;
    }

}


