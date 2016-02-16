//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.ModuleEventArgs;
import OpenDental.ModuleEventHandler;

/*=================================Class GotoModule==================================================
	===========================================================================================*/
/**
* Used to trigger a global event to jump between modules and perform actions in other modules.  PatNum is optional.  If 0, then no effect.
*/
public class GotoModule   
{
    /**
    * 
    */
    public static ModuleEventHandler ModuleSelected;
    /*
    		///<summary>This triggers a global event which the main form responds to by going directly to a module.</summary>
    		public static void GoNow(DateTime dateSelected,Appointment pinAppt,int selectedAptNum,int iModule,int claimNum) {
    			OnModuleSelected(new ModuleEventArgs(dateSelected,pinAppt,selectedAptNum,iModule,claimNum));
    		}*/
    /**
    * Goes directly to an existing appointment.
    */
    public static void gotoAppointment(DateTime dateSelected, long selectedAptNum) throws Exception {
        onModuleSelected(new ModuleEventArgs(dateSelected, new List<long>(), selectedAptNum, 0, 0, 0, 0));
    }

    /**
    * Goes directly to a claim in someone's Account.
    */
    public static void gotoClaim(long claimNum) throws Exception {
        onModuleSelected(new ModuleEventArgs(DateTime.MinValue, new List<long>(), 0, 2, claimNum, 0, 0));
    }

    /**
    * Goes directly to an Account.  Sometimes, patient is selected some other way instead of being passed in here, so OK to pass in a patNum of zero.
    */
    public static void gotoAccount(long patNum) throws Exception {
        onModuleSelected(new ModuleEventArgs(DateTime.MinValue, new List<long>(), 0, 2, 0, patNum, 0));
    }

    /**
    * Goes directly to Family module.  Sometimes, patient is selected some other way instead of being passed in here, so OK to pass in a patNum of zero.
    */
    public static void gotoFamily(long patNum) throws Exception {
        onModuleSelected(new ModuleEventArgs(DateTime.MinValue, new List<long>(), 0, 1, 0, patNum, 0));
    }

    /**
    * Goes directly to TP module.  Sometimes, patient is selected some other way instead of being passed in here, so OK to pass in a patNum of zero.
    */
    public static void gotoTreatmentPlan(long patNum) throws Exception {
        onModuleSelected(new ModuleEventArgs(DateTime.MinValue, new List<long>(), 0, 3, 0, patNum, 0));
    }

    public static void gotoChart(long patNum) throws Exception {
        onModuleSelected(new ModuleEventArgs(DateTime.MinValue, new List<long>(), 0, 4, 0, patNum, 0));
    }

    public static void gotoManage(long patNum) throws Exception {
        onModuleSelected(new ModuleEventArgs(DateTime.MinValue, new List<long>(), 0, 6, 0, patNum, 0));
    }

    /**
    * Puts appointment on pinboard, then jumps to Appointments module.  Sometimes, patient is selected some other way instead of being passed in here, so OK to pass in a patNum of zero.
    */
    public static void pinToAppt(List<long> pinAptNums, long patNum) throws Exception {
        onModuleSelected(new ModuleEventArgs(DateTime.Today, pinAptNums, 0, 0, 0, patNum, 0));
    }

    /**
    * Jumps to Images module and pulls up the specified image.
    */
    public static void gotoImage(long patNum, long docNum) throws Exception {
        onModuleSelected(new ModuleEventArgs(DateTime.MinValue, new List<long>(), 0, 5, 0, patNum, docNum));
    }

    /**
    * 
    */
    protected static void onModuleSelected(ModuleEventArgs e) throws Exception {
        if (ModuleSelected != null)
        {
            ModuleSelected.invoke(e);
        }
         
    }

}


