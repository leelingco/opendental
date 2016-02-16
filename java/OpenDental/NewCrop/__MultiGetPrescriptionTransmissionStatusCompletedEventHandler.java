//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPrescriptionTransmissionStatusCompletedEventHandler;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusCompletedEventArgs;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusCompletedEventHandler;

/**
* 
*/
public class __MultiGetPrescriptionTransmissionStatusCompletedEventHandler   implements GetPrescriptionTransmissionStatusCompletedEventHandler
{
    public void invoke(Object sender, GetPrescriptionTransmissionStatusCompletedEventArgs e) throws Exception {
        IList<GetPrescriptionTransmissionStatusCompletedEventHandler> copy = new IList<GetPrescriptionTransmissionStatusCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPrescriptionTransmissionStatusCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPrescriptionTransmissionStatusCompletedEventHandler d = (GetPrescriptionTransmissionStatusCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPrescriptionTransmissionStatusCompletedEventHandler> _invocationList = new ArrayList<GetPrescriptionTransmissionStatusCompletedEventHandler>();
    public static GetPrescriptionTransmissionStatusCompletedEventHandler combine(GetPrescriptionTransmissionStatusCompletedEventHandler a, GetPrescriptionTransmissionStatusCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPrescriptionTransmissionStatusCompletedEventHandler ret = new __MultiGetPrescriptionTransmissionStatusCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPrescriptionTransmissionStatusCompletedEventHandler remove(GetPrescriptionTransmissionStatusCompletedEventHandler a, GetPrescriptionTransmissionStatusCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPrescriptionTransmissionStatusCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPrescriptionTransmissionStatusCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPrescriptionTransmissionStatusCompletedEventHandler ret = new __MultiGetPrescriptionTransmissionStatusCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPrescriptionTransmissionStatusCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


