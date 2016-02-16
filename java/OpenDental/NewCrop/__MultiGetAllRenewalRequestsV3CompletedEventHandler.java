//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetAllRenewalRequestsV3CompletedEventHandler;
import OpenDental.NewCrop.GetAllRenewalRequestsV3CompletedEventArgs;
import OpenDental.NewCrop.GetAllRenewalRequestsV3CompletedEventHandler;

/**
* 
*/
public class __MultiGetAllRenewalRequestsV3CompletedEventHandler   implements GetAllRenewalRequestsV3CompletedEventHandler
{
    public void invoke(Object sender, GetAllRenewalRequestsV3CompletedEventArgs e) throws Exception {
        IList<GetAllRenewalRequestsV3CompletedEventHandler> copy = new IList<GetAllRenewalRequestsV3CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetAllRenewalRequestsV3CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetAllRenewalRequestsV3CompletedEventHandler d = (GetAllRenewalRequestsV3CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetAllRenewalRequestsV3CompletedEventHandler> _invocationList = new ArrayList<GetAllRenewalRequestsV3CompletedEventHandler>();
    public static GetAllRenewalRequestsV3CompletedEventHandler combine(GetAllRenewalRequestsV3CompletedEventHandler a, GetAllRenewalRequestsV3CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetAllRenewalRequestsV3CompletedEventHandler ret = new __MultiGetAllRenewalRequestsV3CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetAllRenewalRequestsV3CompletedEventHandler remove(GetAllRenewalRequestsV3CompletedEventHandler a, GetAllRenewalRequestsV3CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetAllRenewalRequestsV3CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetAllRenewalRequestsV3CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetAllRenewalRequestsV3CompletedEventHandler ret = new __MultiGetAllRenewalRequestsV3CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetAllRenewalRequestsV3CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


