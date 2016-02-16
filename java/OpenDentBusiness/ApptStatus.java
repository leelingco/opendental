//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum ApptStatus
{
    /**
    * Appointment status.0- No appointment should ever have this status.
    */
    None,
    /**
    * 1- Shows as a regularly scheduled appointment.
    */
    Scheduled,
    /**
    * 2- Shows greyed out.
    */
    Complete,
    /**
    * 3- Only shows on unscheduled list.
    */
    UnschedList,
    /**
    * 4- Functions almost the same as Scheduled, but also causes the appointment to show on the ASAP list.
    */
    ASAP,
    /**
    * 5- Shows with a big X on it.
    */
    Broken,
    /**
    * 6- Planned appointment.  Only shows in Chart module. User not allowed to change this status, and it does not display as one of the options.
    */
    Planned,
    /**
    * 7- Patient "post-it" note on the schedule. Shows light yellow. Shows on day scheduled just like appt, as well as in prog notes, etc.
    */
    PtNote,
    /**
    * 8- Patient "post-it" note completed
    */
    PtNoteCompleted
}

