//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPrescriptionTransmissionStatusByPatientCompletedEventHandler;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusByPatientCompletedEventArgs;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusByPatientCompletedEventHandler;

/**
* 
*/
public class __MultiGetPrescriptionTransmissionStatusByPatientCompletedEventHandler   implements GetPrescriptionTransmissionStatusByPatientCompletedEventHandler
{
    public void invoke(Object sender, GetPrescriptionTransmissionStatusByPatientCompletedEventArgs e) throws Exception {
        IList<GetPrescriptionTransmissionStatusByPatientCompletedEventHandler> copy = new IList<GetPrescriptionTransmissionStatusByPatientCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPrescriptionTransmissionStatusByPatientCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPrescriptionTransmissionStatusByPatientCompletedEventHandler d = (GetPrescriptionTransmissionStatusByPatientCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPrescriptionTransmissionStatusByPatientCompletedEventHandler> _invocationList = new ArrayList<GetPrescriptionTransmissionStatusByPatientCompletedEventHandler>();
    public static GetPrescriptionTransmissionStatusByPatientCompletedEventHandler combine(GetPrescriptionTransmissionStatusByPatientCompletedEventHandler a, GetPrescriptionTransmissionStatusByPatientCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPrescriptionTransmissionStatusByPatientCompletedEventHandler ret = new __MultiGetPrescriptionTransmissionStatusByPatientCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPrescriptionTransmissionStatusByPatientCompletedEventHandler remove(GetPrescriptionTransmissionStatusByPatientCompletedEventHandler a, GetPrescriptionTransmissionStatusByPatientCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPrescriptionTransmissionStatusByPatientCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPrescriptionTransmissionStatusByPatientCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPrescriptionTransmissionStatusByPatientCompletedEventHandler ret = new __MultiGetPrescriptionTransmissionStatusByPatientCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPrescriptionTransmissionStatusByPatientCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


