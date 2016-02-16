//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:23 PM
//

package OpenDental.Reporting.Allocators;

import OpenDental.Reporting.Allocators.Allocator;
import OpenDentBusiness.Db;

/**
* @see IAllocator
* Here is the model.
* Payments arrive.  When they arrive an allocation event is to occur. The payment
* can then be allocated by the set of rules given by the allocator which a programmer
* will create.  The Allocator created must conform to the method rules called by the
* allocator interface which is really simple.
* 
* The Allocators in the AllocatorCollection will be called.  The allocators must be created by
* a programmer and added to the static list that is creted in the private method CreateAllocators.
*/
public class AllocatorCollection   
{
    private static List<Allocator> _AllocatorList = null;
    /**
    * jsparks-NOT GETTING CALLED.
    * The only place that it was being used was in ProcedureL.SetCompleteInAppt().  But once that method was moved into the business layer,
    * it was no longer possible to make a call into OpenDental UI layer.
    * So for this to work, it will all need to be moved into the business layer.
    * Calls all of the allocators created for this patient.
    * Determines the guarantor form this patient first
    * 
    * 
    * Points of Entry Identified in OD
    * 1)  ContrAccount.ToolBarMain_ButtonClick(...)  Added code to run allocator after user is finished with clicked tasks
    * 2)	ContrAccount.gridAccount_CellDoubleClick(...)  Double Click means that an edit was potentialy occuring run allocator.
    * 3)  ContrAccount.gridRepeat_CellDoubleClick(...)  I am not familiar with Payment Plans or Repeating Charges need to check against tool
    * 4)  ContrAccount.gridPayPlan_CellDoubleClick(...) I am not familiar with Payment Plans or Repeating Charges need to check against tool
    * 5)  ContrChart.gridProg_CellDoubleClick(...)  Indicates Procedure was potentially changed. Runs allocator if any of the dialogs returned DialogResult.OK
    * 6)	ContrChart.EnterTypedCode(...)
    * 7)  ContrChart.ProcButtonClicked(...)
    * 8)  Procedures.SetCompleteInAppt(...) // Called from Set Complete Checkmark (butComplete) in the appointment module.
    * 
    * Points of Entry That Need Atttention to
    */
    public static void callAll_Allocators(int iPatient) throws Exception {
        // Find Guarantor first
        int iGuarantor = getGuarantor(iPatient);
        if (_AllocatorList == null)
        {
            createAllocators();
        }
         
        // <--- Add your allocator to this list.
        if (iGuarantor != 0)
            for (Object __dummyForeachVar0 : _AllocatorList)
            {
                // cannot allocate for no patient
                Allocator alc = (Allocator)__dummyForeachVar0;
                //<--- if your allocator is not based on guarantor then you will need to make sure it is not part of this list that is called but make your own.
                alc.allocate(iGuarantor);
            }
         
    }

    public static void callAll_UnAllocators(int iPatient) throws Exception {
        // Find Guarantor first
        int iGuarantor = getGuarantor(iPatient);
        if (_AllocatorList == null)
        {
            createAllocators();
        }
         
        if (iGuarantor != 0)
            for (Object __dummyForeachVar1 : _AllocatorList)
            {
                // cannot allocate for no patient
                Allocator alc = (Allocator)__dummyForeachVar1;
                alc.deAllocate(iGuarantor);
            }
         
    }

    /**
    * Only calle from with in AllocatorCollection so
    * don't have to worry about opendental calling this.
    */
    private static void createAllocators() throws Exception {
        _AllocatorList = new List<Allocator>();
        _AllocatorList.Add(new OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment());
    }

    private static int getGuarantor(int Patient) throws Exception {
        int rVal = 0;
        try
        {
            rVal = int.Parse(Db.getTableOld("SELECT guarantor FROM patient WHERE patnum= " + Patient.ToString()).Rows[0][0].ToString());
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        return rVal;
    }

}


