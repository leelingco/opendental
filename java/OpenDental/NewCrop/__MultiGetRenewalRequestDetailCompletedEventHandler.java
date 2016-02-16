//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetRenewalRequestDetailCompletedEventHandler;
import OpenDental.NewCrop.GetRenewalRequestDetailCompletedEventArgs;
import OpenDental.NewCrop.GetRenewalRequestDetailCompletedEventHandler;

/**
* 
*/
public class __MultiGetRenewalRequestDetailCompletedEventHandler   implements GetRenewalRequestDetailCompletedEventHandler
{
    public void invoke(Object sender, GetRenewalRequestDetailCompletedEventArgs e) throws Exception {
        IList<GetRenewalRequestDetailCompletedEventHandler> copy = new IList<GetRenewalRequestDetailCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetRenewalRequestDetailCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetRenewalRequestDetailCompletedEventHandler d = (GetRenewalRequestDetailCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetRenewalRequestDetailCompletedEventHandler> _invocationList = new ArrayList<GetRenewalRequestDetailCompletedEventHandler>();
    public static GetRenewalRequestDetailCompletedEventHandler combine(GetRenewalRequestDetailCompletedEventHandler a, GetRenewalRequestDetailCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetRenewalRequestDetailCompletedEventHandler ret = new __MultiGetRenewalRequestDetailCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetRenewalRequestDetailCompletedEventHandler remove(GetRenewalRequestDetailCompletedEventHandler a, GetRenewalRequestDetailCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetRenewalRequestDetailCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetRenewalRequestDetailCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetRenewalRequestDetailCompletedEventHandler ret = new __MultiGetRenewalRequestDetailCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetRenewalRequestDetailCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


