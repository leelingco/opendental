//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;


public class ChartModuleComponentsToLoad   
{
    public boolean ShowAppointments = new boolean();
    public boolean ShowCommLog = new boolean();
    public boolean ShowCompleted = new boolean();
    public boolean ShowConditions = new boolean();
    public boolean ShowEmail = new boolean();
    public boolean ShowExisting = new boolean();
    public boolean ShowFamilyCommLog = new boolean();
    public boolean ShowFormPat = new boolean();
    public boolean ShowLabCases = new boolean();
    public boolean ShowProcNotes = new boolean();
    public boolean ShowReferred = new boolean();
    public boolean ShowRX = new boolean();
    public boolean ShowSheets = new boolean();
    public boolean ShowTasks = new boolean();
    public boolean ShowTreatPlan = new boolean();
    /**
    * All showComponents are set to true.
    */
    public ChartModuleComponentsToLoad() throws Exception {
        ShowAppointments = true;
        ShowCommLog = true;
        ShowCompleted = true;
        ShowConditions = true;
        ShowEmail = true;
        ShowExisting = true;
        ShowFamilyCommLog = true;
        ShowFormPat = true;
        ShowLabCases = true;
        ShowProcNotes = true;
        ShowReferred = true;
        ShowRX = true;
        ShowSheets = true;
        ShowTasks = true;
        ShowTreatPlan = true;
    }

    /**
    * 
    */
    public ChartModuleComponentsToLoad(boolean showAppointments, boolean showCommLog, boolean showCompleted, boolean showConditions, boolean showEmail, boolean showExisting, boolean showFamilyCommLog, boolean showFormPat, boolean showLabCases, boolean showProcNotes, boolean showReferred, boolean showRX, boolean showSheets, boolean showTasks, boolean showTreatPlan) throws Exception {
        ShowAppointments = showAppointments;
        ShowCommLog = showCommLog;
        ShowCompleted = showCompleted;
        ShowConditions = showConditions;
        ShowEmail = showEmail;
        ShowExisting = showExisting;
        ShowFamilyCommLog = showFamilyCommLog;
        ShowFormPat = showFormPat;
        ShowLabCases = showLabCases;
        ShowProcNotes = showProcNotes;
        ShowReferred = showReferred;
        ShowRX = showRX;
        ShowSheets = showSheets;
        ShowTasks = showTasks;
        ShowTreatPlan = showTreatPlan;
    }

}


//public class DtoChartModuleGetAll:DtoQueryBase {
//	public int PatNum;
//	public bool IsAuditMode;
//}