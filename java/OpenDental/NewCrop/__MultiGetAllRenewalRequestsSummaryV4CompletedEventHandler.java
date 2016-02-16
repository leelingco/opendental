//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetAllRenewalRequestsSummaryV4CompletedEventHandler;
import OpenDental.NewCrop.GetAllRenewalRequestsSummaryV4CompletedEventArgs;
import OpenDental.NewCrop.GetAllRenewalRequestsSummaryV4CompletedEventHandler;

/**
* 
*/
public class __MultiGetAllRenewalRequestsSummaryV4CompletedEventHandler   implements GetAllRenewalRequestsSummaryV4CompletedEventHandler
{
    public void invoke(Object sender, GetAllRenewalRequestsSummaryV4CompletedEventArgs e) throws Exception {
        IList<GetAllRenewalRequestsSummaryV4CompletedEventHandler> copy = new IList<GetAllRenewalRequestsSummaryV4CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetAllRenewalRequestsSummaryV4CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetAllRenewalRequestsSummaryV4CompletedEventHandler d = (GetAllRenewalRequestsSummaryV4CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetAllRenewalRequestsSummaryV4CompletedEventHandler> _invocationList = new ArrayList<GetAllRenewalRequestsSummaryV4CompletedEventHandler>();
    public static GetAllRenewalRequestsSummaryV4CompletedEventHandler combine(GetAllRenewalRequestsSummaryV4CompletedEventHandler a, GetAllRenewalRequestsSummaryV4CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetAllRenewalRequestsSummaryV4CompletedEventHandler ret = new __MultiGetAllRenewalRequestsSummaryV4CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetAllRenewalRequestsSummaryV4CompletedEventHandler remove(GetAllRenewalRequestsSummaryV4CompletedEventHandler a, GetAllRenewalRequestsSummaryV4CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetAllRenewalRequestsSummaryV4CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetAllRenewalRequestsSummaryV4CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetAllRenewalRequestsSummaryV4CompletedEventHandler ret = new __MultiGetAllRenewalRequestsSummaryV4CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetAllRenewalRequestsSummaryV4CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


