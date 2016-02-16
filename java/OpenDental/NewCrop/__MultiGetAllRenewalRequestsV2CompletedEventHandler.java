//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetAllRenewalRequestsV2CompletedEventHandler;
import OpenDental.NewCrop.GetAllRenewalRequestsV2CompletedEventArgs;
import OpenDental.NewCrop.GetAllRenewalRequestsV2CompletedEventHandler;

/**
* 
*/
public class __MultiGetAllRenewalRequestsV2CompletedEventHandler   implements GetAllRenewalRequestsV2CompletedEventHandler
{
    public void invoke(Object sender, GetAllRenewalRequestsV2CompletedEventArgs e) throws Exception {
        IList<GetAllRenewalRequestsV2CompletedEventHandler> copy = new IList<GetAllRenewalRequestsV2CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetAllRenewalRequestsV2CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetAllRenewalRequestsV2CompletedEventHandler d = (GetAllRenewalRequestsV2CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetAllRenewalRequestsV2CompletedEventHandler> _invocationList = new ArrayList<GetAllRenewalRequestsV2CompletedEventHandler>();
    public static GetAllRenewalRequestsV2CompletedEventHandler combine(GetAllRenewalRequestsV2CompletedEventHandler a, GetAllRenewalRequestsV2CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetAllRenewalRequestsV2CompletedEventHandler ret = new __MultiGetAllRenewalRequestsV2CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetAllRenewalRequestsV2CompletedEventHandler remove(GetAllRenewalRequestsV2CompletedEventHandler a, GetAllRenewalRequestsV2CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetAllRenewalRequestsV2CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetAllRenewalRequestsV2CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetAllRenewalRequestsV2CompletedEventHandler ret = new __MultiGetAllRenewalRequestsV2CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetAllRenewalRequestsV2CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


