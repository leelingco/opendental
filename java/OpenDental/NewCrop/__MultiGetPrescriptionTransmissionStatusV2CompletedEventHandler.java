//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPrescriptionTransmissionStatusV2CompletedEventHandler;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusV2CompletedEventArgs;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusV2CompletedEventHandler;

/**
* 
*/
public class __MultiGetPrescriptionTransmissionStatusV2CompletedEventHandler   implements GetPrescriptionTransmissionStatusV2CompletedEventHandler
{
    public void invoke(Object sender, GetPrescriptionTransmissionStatusV2CompletedEventArgs e) throws Exception {
        IList<GetPrescriptionTransmissionStatusV2CompletedEventHandler> copy = new IList<GetPrescriptionTransmissionStatusV2CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPrescriptionTransmissionStatusV2CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPrescriptionTransmissionStatusV2CompletedEventHandler d = (GetPrescriptionTransmissionStatusV2CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPrescriptionTransmissionStatusV2CompletedEventHandler> _invocationList = new ArrayList<GetPrescriptionTransmissionStatusV2CompletedEventHandler>();
    public static GetPrescriptionTransmissionStatusV2CompletedEventHandler combine(GetPrescriptionTransmissionStatusV2CompletedEventHandler a, GetPrescriptionTransmissionStatusV2CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPrescriptionTransmissionStatusV2CompletedEventHandler ret = new __MultiGetPrescriptionTransmissionStatusV2CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPrescriptionTransmissionStatusV2CompletedEventHandler remove(GetPrescriptionTransmissionStatusV2CompletedEventHandler a, GetPrescriptionTransmissionStatusV2CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPrescriptionTransmissionStatusV2CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPrescriptionTransmissionStatusV2CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPrescriptionTransmissionStatusV2CompletedEventHandler ret = new __MultiGetPrescriptionTransmissionStatusV2CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPrescriptionTransmissionStatusV2CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


