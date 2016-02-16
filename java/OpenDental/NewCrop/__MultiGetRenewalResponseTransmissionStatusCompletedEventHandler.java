//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetRenewalResponseTransmissionStatusCompletedEventHandler;
import OpenDental.NewCrop.GetRenewalResponseTransmissionStatusCompletedEventArgs;
import OpenDental.NewCrop.GetRenewalResponseTransmissionStatusCompletedEventHandler;

/**
* 
*/
public class __MultiGetRenewalResponseTransmissionStatusCompletedEventHandler   implements GetRenewalResponseTransmissionStatusCompletedEventHandler
{
    public void invoke(Object sender, GetRenewalResponseTransmissionStatusCompletedEventArgs e) throws Exception {
        IList<GetRenewalResponseTransmissionStatusCompletedEventHandler> copy = new IList<GetRenewalResponseTransmissionStatusCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetRenewalResponseTransmissionStatusCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetRenewalResponseTransmissionStatusCompletedEventHandler d = (GetRenewalResponseTransmissionStatusCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetRenewalResponseTransmissionStatusCompletedEventHandler> _invocationList = new ArrayList<GetRenewalResponseTransmissionStatusCompletedEventHandler>();
    public static GetRenewalResponseTransmissionStatusCompletedEventHandler combine(GetRenewalResponseTransmissionStatusCompletedEventHandler a, GetRenewalResponseTransmissionStatusCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetRenewalResponseTransmissionStatusCompletedEventHandler ret = new __MultiGetRenewalResponseTransmissionStatusCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetRenewalResponseTransmissionStatusCompletedEventHandler remove(GetRenewalResponseTransmissionStatusCompletedEventHandler a, GetRenewalResponseTransmissionStatusCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetRenewalResponseTransmissionStatusCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetRenewalResponseTransmissionStatusCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetRenewalResponseTransmissionStatusCompletedEventHandler ret = new __MultiGetRenewalResponseTransmissionStatusCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetRenewalResponseTransmissionStatusCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


