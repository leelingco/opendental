//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPatientFullMedicationHistory4CompletedEventHandler;
import OpenDental.NewCrop.GetPatientFullMedicationHistory4CompletedEventArgs;
import OpenDental.NewCrop.GetPatientFullMedicationHistory4CompletedEventHandler;

/**
* 
*/
public class __MultiGetPatientFullMedicationHistory4CompletedEventHandler   implements GetPatientFullMedicationHistory4CompletedEventHandler
{
    public void invoke(Object sender, GetPatientFullMedicationHistory4CompletedEventArgs e) throws Exception {
        IList<GetPatientFullMedicationHistory4CompletedEventHandler> copy = new IList<GetPatientFullMedicationHistory4CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPatientFullMedicationHistory4CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPatientFullMedicationHistory4CompletedEventHandler d = (GetPatientFullMedicationHistory4CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPatientFullMedicationHistory4CompletedEventHandler> _invocationList = new ArrayList<GetPatientFullMedicationHistory4CompletedEventHandler>();
    public static GetPatientFullMedicationHistory4CompletedEventHandler combine(GetPatientFullMedicationHistory4CompletedEventHandler a, GetPatientFullMedicationHistory4CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPatientFullMedicationHistory4CompletedEventHandler ret = new __MultiGetPatientFullMedicationHistory4CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPatientFullMedicationHistory4CompletedEventHandler remove(GetPatientFullMedicationHistory4CompletedEventHandler a, GetPatientFullMedicationHistory4CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPatientFullMedicationHistory4CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPatientFullMedicationHistory4CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPatientFullMedicationHistory4CompletedEventHandler ret = new __MultiGetPatientFullMedicationHistory4CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPatientFullMedicationHistory4CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


