//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPatientFullMedicationHistory5CompletedEventHandler;
import OpenDental.NewCrop.GetPatientFullMedicationHistory5CompletedEventArgs;
import OpenDental.NewCrop.GetPatientFullMedicationHistory5CompletedEventHandler;

/**
* 
*/
public class __MultiGetPatientFullMedicationHistory5CompletedEventHandler   implements GetPatientFullMedicationHistory5CompletedEventHandler
{
    public void invoke(Object sender, GetPatientFullMedicationHistory5CompletedEventArgs e) throws Exception {
        IList<GetPatientFullMedicationHistory5CompletedEventHandler> copy = new IList<GetPatientFullMedicationHistory5CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPatientFullMedicationHistory5CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPatientFullMedicationHistory5CompletedEventHandler d = (GetPatientFullMedicationHistory5CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPatientFullMedicationHistory5CompletedEventHandler> _invocationList = new ArrayList<GetPatientFullMedicationHistory5CompletedEventHandler>();
    public static GetPatientFullMedicationHistory5CompletedEventHandler combine(GetPatientFullMedicationHistory5CompletedEventHandler a, GetPatientFullMedicationHistory5CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPatientFullMedicationHistory5CompletedEventHandler ret = new __MultiGetPatientFullMedicationHistory5CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPatientFullMedicationHistory5CompletedEventHandler remove(GetPatientFullMedicationHistory5CompletedEventHandler a, GetPatientFullMedicationHistory5CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPatientFullMedicationHistory5CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPatientFullMedicationHistory5CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPatientFullMedicationHistory5CompletedEventHandler ret = new __MultiGetPatientFullMedicationHistory5CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPatientFullMedicationHistory5CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


