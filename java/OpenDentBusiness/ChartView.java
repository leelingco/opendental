//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ChartView;
import OpenDentBusiness.ChartViewDates;
import OpenDentBusiness.ChartViewObjs;
import OpenDentBusiness.ChartViewProcStat;
import OpenDentBusiness.OrionStatus;
import OpenDentBusiness.TableBase;

/**
* Enables viewing a variety of views in chart module.
*/
public class ChartView  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ChartViewNum = new long();
    /**
    * Description of this view.  Gets displayed at top of Progress Notes grid.
    */
    public String Description = new String();
    /**
    * 0-based order to display in lists.
    */
    public int ItemOrder = new int();
    /**
    * Enum:ChartViewProcStat None=0,TP=1,Complete=2,Existing Cur Prov=4,Existing Other Prov=8,Referred=16,Deleted=32,Condition=64,All=127.
    */
    public ChartViewProcStat ProcStatuses = ChartViewProcStat.None;
    /**
    * Enum:ChartViewObjs None=0,Appointments=1,Comm Log=2,Comm Log Family=4,Tasks=8,Email=16,LabCases=32,Rx=64,Sheets=128,All=255.
    */
    public ChartViewObjs ObjectTypes = ChartViewObjs.None;
    /**
    * Set true to show procedure notes.
    */
    public boolean ShowProcNotes = new boolean();
    /**
    * Set true to enable audit mode.
    */
    public boolean IsAudit = new boolean();
    /**
    * Set true to only show information regarding the selected teeth.
    */
    public boolean SelectedTeethOnly = new boolean();
    /**
    * Enum:OrionStatus Which orion statuses to show. Will be zero if not orion.
    */
    public OrionStatus OrionStatusFlags = OrionStatus.None;
    /**
    * Enum:ChartViewDates
    */
    public ChartViewDates DatesShowing = ChartViewDates.All;
    public ChartView copy() throws Exception {
        return (ChartView)this.MemberwiseClone();
    }

}


