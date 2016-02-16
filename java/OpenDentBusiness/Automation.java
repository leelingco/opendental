//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Automation;
import OpenDentBusiness.AutomationAction;
import OpenDentBusiness.AutomationTrigger;
import OpenDentBusiness.TableBase;

/**
* A trigger event causes one or more actions.
*/
public class Automation  extends TableBase 
{
    /**
    * Primary key.
    */
    public long AutomationNum = new long();
    /**
    * .
    */
    public String Description = new String();
    /**
    * Enum:AutomationTrigger What triggers this automation
    */
    public AutomationTrigger Autotrigger = AutomationTrigger.CompleteProcedure;
    /**
    * If this has a CompleteProcedure trigger, this is a comma-delimited list of codes that will trigger the action.
    */
    public String ProcCodes = new String();
    /**
    * Enum:AutomationAction The action taken as a result of the trigger.  To get more than one action, create multiple automation entries.
    */
    public AutomationAction AutoAction = AutomationAction.PrintPatientLetter;
    /**
    * FK to sheetdef.SheetDefNum.  If the action is to print a sheet, then this tells which sheet to print.  So it must be a custom sheet.  Also, not that this organization does not allow passing parameters to the sheet such as which procedures were completed, or which appt was broken.
    */
    public long SheetDefNum = new long();
    /**
    * FK to definition.DefNum. Only used if action is CreateCommlog.
    */
    public long CommType = new long();
    /**
    * If a commlog action, then this is the text that goes in the commlog.  If this is a ShowStatementNoteBold action, then this is the NoteBold. Might later be expanded to work with email or to use variables.
    */
    public String MessageContent = new String();
    public Automation copy() throws Exception {
        return (Automation)MemberwiseClone();
    }

}


