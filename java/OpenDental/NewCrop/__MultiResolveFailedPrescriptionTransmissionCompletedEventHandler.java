//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiResolveFailedPrescriptionTransmissionCompletedEventHandler;
import OpenDental.NewCrop.ResolveFailedPrescriptionTransmissionCompletedEventArgs;
import OpenDental.NewCrop.ResolveFailedPrescriptionTransmissionCompletedEventHandler;

/**
* 
*/
public class __MultiResolveFailedPrescriptionTransmissionCompletedEventHandler   implements ResolveFailedPrescriptionTransmissionCompletedEventHandler
{
    public void invoke(Object sender, ResolveFailedPrescriptionTransmissionCompletedEventArgs e) throws Exception {
        IList<ResolveFailedPrescriptionTransmissionCompletedEventHandler> copy = new IList<ResolveFailedPrescriptionTransmissionCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<ResolveFailedPrescriptionTransmissionCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            ResolveFailedPrescriptionTransmissionCompletedEventHandler d = (ResolveFailedPrescriptionTransmissionCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<ResolveFailedPrescriptionTransmissionCompletedEventHandler> _invocationList = new ArrayList<ResolveFailedPrescriptionTransmissionCompletedEventHandler>();
    public static ResolveFailedPrescriptionTransmissionCompletedEventHandler combine(ResolveFailedPrescriptionTransmissionCompletedEventHandler a, ResolveFailedPrescriptionTransmissionCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiResolveFailedPrescriptionTransmissionCompletedEventHandler ret = new __MultiResolveFailedPrescriptionTransmissionCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static ResolveFailedPrescriptionTransmissionCompletedEventHandler remove(ResolveFailedPrescriptionTransmissionCompletedEventHandler a, ResolveFailedPrescriptionTransmissionCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<ResolveFailedPrescriptionTransmissionCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<ResolveFailedPrescriptionTransmissionCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiResolveFailedPrescriptionTransmissionCompletedEventHandler ret = new __MultiResolveFailedPrescriptionTransmissionCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<ResolveFailedPrescriptionTransmissionCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


