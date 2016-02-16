//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetAllRenewalRequestsCompletedEventHandler;
import OpenDental.NewCrop.GetAllRenewalRequestsCompletedEventArgs;
import OpenDental.NewCrop.GetAllRenewalRequestsCompletedEventHandler;

/**
* 
*/
public class __MultiGetAllRenewalRequestsCompletedEventHandler   implements GetAllRenewalRequestsCompletedEventHandler
{
    public void invoke(Object sender, GetAllRenewalRequestsCompletedEventArgs e) throws Exception {
        IList<GetAllRenewalRequestsCompletedEventHandler> copy = new IList<GetAllRenewalRequestsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetAllRenewalRequestsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetAllRenewalRequestsCompletedEventHandler d = (GetAllRenewalRequestsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetAllRenewalRequestsCompletedEventHandler> _invocationList = new ArrayList<GetAllRenewalRequestsCompletedEventHandler>();
    public static GetAllRenewalRequestsCompletedEventHandler combine(GetAllRenewalRequestsCompletedEventHandler a, GetAllRenewalRequestsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetAllRenewalRequestsCompletedEventHandler ret = new __MultiGetAllRenewalRequestsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetAllRenewalRequestsCompletedEventHandler remove(GetAllRenewalRequestsCompletedEventHandler a, GetAllRenewalRequestsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetAllRenewalRequestsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetAllRenewalRequestsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetAllRenewalRequestsCompletedEventHandler ret = new __MultiGetAllRenewalRequestsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetAllRenewalRequestsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


