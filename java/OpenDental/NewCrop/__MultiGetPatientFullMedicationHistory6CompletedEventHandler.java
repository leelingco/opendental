//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPatientFullMedicationHistory6CompletedEventHandler;
import OpenDental.NewCrop.GetPatientFullMedicationHistory6CompletedEventArgs;
import OpenDental.NewCrop.GetPatientFullMedicationHistory6CompletedEventHandler;

/**
* 
*/
public class __MultiGetPatientFullMedicationHistory6CompletedEventHandler   implements GetPatientFullMedicationHistory6CompletedEventHandler
{
    public void invoke(Object sender, GetPatientFullMedicationHistory6CompletedEventArgs e) throws Exception {
        IList<GetPatientFullMedicationHistory6CompletedEventHandler> copy = new IList<GetPatientFullMedicationHistory6CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPatientFullMedicationHistory6CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPatientFullMedicationHistory6CompletedEventHandler d = (GetPatientFullMedicationHistory6CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPatientFullMedicationHistory6CompletedEventHandler> _invocationList = new ArrayList<GetPatientFullMedicationHistory6CompletedEventHandler>();
    public static GetPatientFullMedicationHistory6CompletedEventHandler combine(GetPatientFullMedicationHistory6CompletedEventHandler a, GetPatientFullMedicationHistory6CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPatientFullMedicationHistory6CompletedEventHandler ret = new __MultiGetPatientFullMedicationHistory6CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPatientFullMedicationHistory6CompletedEventHandler remove(GetPatientFullMedicationHistory6CompletedEventHandler a, GetPatientFullMedicationHistory6CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPatientFullMedicationHistory6CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPatientFullMedicationHistory6CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPatientFullMedicationHistory6CompletedEventHandler ret = new __MultiGetPatientFullMedicationHistory6CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPatientFullMedicationHistory6CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


