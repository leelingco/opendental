//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetAllRenewalRequestsDetailV4CompletedEventHandler;
import OpenDental.NewCrop.GetAllRenewalRequestsDetailV4CompletedEventArgs;
import OpenDental.NewCrop.GetAllRenewalRequestsDetailV4CompletedEventHandler;

/**
* 
*/
public class __MultiGetAllRenewalRequestsDetailV4CompletedEventHandler   implements GetAllRenewalRequestsDetailV4CompletedEventHandler
{
    public void invoke(Object sender, GetAllRenewalRequestsDetailV4CompletedEventArgs e) throws Exception {
        IList<GetAllRenewalRequestsDetailV4CompletedEventHandler> copy = new IList<GetAllRenewalRequestsDetailV4CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetAllRenewalRequestsDetailV4CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetAllRenewalRequestsDetailV4CompletedEventHandler d = (GetAllRenewalRequestsDetailV4CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetAllRenewalRequestsDetailV4CompletedEventHandler> _invocationList = new ArrayList<GetAllRenewalRequestsDetailV4CompletedEventHandler>();
    public static GetAllRenewalRequestsDetailV4CompletedEventHandler combine(GetAllRenewalRequestsDetailV4CompletedEventHandler a, GetAllRenewalRequestsDetailV4CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetAllRenewalRequestsDetailV4CompletedEventHandler ret = new __MultiGetAllRenewalRequestsDetailV4CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetAllRenewalRequestsDetailV4CompletedEventHandler remove(GetAllRenewalRequestsDetailV4CompletedEventHandler a, GetAllRenewalRequestsDetailV4CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetAllRenewalRequestsDetailV4CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetAllRenewalRequestsDetailV4CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetAllRenewalRequestsDetailV4CompletedEventHandler ret = new __MultiGetAllRenewalRequestsDetailV4CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetAllRenewalRequestsDetailV4CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


