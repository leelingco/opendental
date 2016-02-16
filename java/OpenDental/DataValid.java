//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Security;
import OpenDentBusiness.TaskUnreads;

//will never happen
/*=================================Class DataValid=========================================
===========================================================================================*/
/**
* Handles a global event to keep local data synchronized.
*/
public class DataValid   
{
    /**
    * 
    */
    public static OpenDental.ValidEventHandler BecameInvalid;
    /*
    		///<summary>Triggers an event that causes a signal to be sent to all other computers telling them what kind of locally stored data needs to be updated.  Either supply a set of flags for the types, or supply a date if the appointment screen needs to be refreshed.  Yes, this does immediately refresh the local data, too.  The AllLocal override does all types except appointment date for the local computer only, such as when starting up.</summary>
    		public static void SetInvalid(List<int> itypes){
    			OnBecameInvalid(new OpenDental.ValidEventArgs(DateTime.MinValue,itypes,false,0));
    		}*/
    /**
    * Triggers an event that causes a signal to be sent to all other computers telling them what kind of locally stored data needs to be updated.  Either supply a set of flags for the types, or supply a date if the appointment screen needs to be refreshed.  Yes, this does immediately refresh the local data, too.  The AllLocal override does all types except appointment date for the local computer only, such as when starting up.
    */
    public static void setInvalid(InvalidType... itypes) throws Exception {
        List<int> itypeList = new List<int>();
        for (int i = 0;i < itypes.Length;i++)
        {
            itypeList.Add((int)itypes[i]);
        }
        onBecameInvalid(new OpenDental.ValidEventArgs(DateTime.MinValue, itypeList, false, 0));
    }

    /**
    * Triggers an event that causes a signal to be sent to all other computers telling them what kind of locally stored data needs to be updated.  Either supply a set of flags for the types, or supply a date if the appointment screen needs to be refreshed.  Yes, this does immediately refresh the local data, too, except Appointments.  The AllLocal override does all types except appointment date for the local computer only, such as when starting up.
    */
    public static void setInvalid(DateTime date) throws Exception {
        List<int> itypeList = new List<int>();
        itypeList.Add(((Enum)InvalidType.Date).ordinal());
        onBecameInvalid(new OpenDental.ValidEventArgs(date, itypeList, false, 0));
    }

    /**
    * Triggers an event that causes a signal to be sent to all other computers telling them what kind of locally stored data needs to be updated.  Either supply a set of flags for the types, or supply a date if the appointment screen needs to be refreshed.  Yes, this does immediately refresh the local data, too.  The AllLocal override does all types except appointment date for the local computer only, such as when starting up.
    */
    public static void setInvalid(boolean onlyLocal) throws Exception {
        List<int> itypeList = new List<int>();
        itypeList.Add(((Enum)InvalidType.AllLocal).ordinal());
        onBecameInvalid(new OpenDental.ValidEventArgs(DateTime.MinValue, itypeList, true, 0));
    }

    public static void setInvalidTask(long taskNum, boolean isPopup) throws Exception {
        List<int> itypeList = new List<int>();
        if (isPopup)
        {
            itypeList.Add(((Enum)InvalidType.TaskPopup).ordinal());
            //we also need to tell the database about all the users with unread tasks
            TaskUnreads.addUnreads(taskNum,Security.getCurUser().UserNum);
        }
        else
        {
            itypeList.Add(((Enum)InvalidType.Task).ordinal());
        } 
        onBecameInvalid(new OpenDental.ValidEventArgs(DateTime.MinValue, itypeList, false, taskNum));
    }

    /**
    * 
    */
    protected static void onBecameInvalid(OpenDental.ValidEventArgs e) throws Exception {
        if (BecameInvalid != null)
        {
            BecameInvalid.invoke(e);
        }
         
    }

}


