//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetRenewalRequestDetailV4CompletedEventHandler;
import OpenDental.NewCrop.GetRenewalRequestDetailV4CompletedEventArgs;
import OpenDental.NewCrop.GetRenewalRequestDetailV4CompletedEventHandler;

/**
* 
*/
public class __MultiGetRenewalRequestDetailV4CompletedEventHandler   implements GetRenewalRequestDetailV4CompletedEventHandler
{
    public void invoke(Object sender, GetRenewalRequestDetailV4CompletedEventArgs e) throws Exception {
        IList<GetRenewalRequestDetailV4CompletedEventHandler> copy = new IList<GetRenewalRequestDetailV4CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetRenewalRequestDetailV4CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetRenewalRequestDetailV4CompletedEventHandler d = (GetRenewalRequestDetailV4CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetRenewalRequestDetailV4CompletedEventHandler> _invocationList = new ArrayList<GetRenewalRequestDetailV4CompletedEventHandler>();
    public static GetRenewalRequestDetailV4CompletedEventHandler combine(GetRenewalRequestDetailV4CompletedEventHandler a, GetRenewalRequestDetailV4CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetRenewalRequestDetailV4CompletedEventHandler ret = new __MultiGetRenewalRequestDetailV4CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetRenewalRequestDetailV4CompletedEventHandler remove(GetRenewalRequestDetailV4CompletedEventHandler a, GetRenewalRequestDetailV4CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetRenewalRequestDetailV4CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetRenewalRequestDetailV4CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetRenewalRequestDetailV4CompletedEventHandler ret = new __MultiGetRenewalRequestDetailV4CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetRenewalRequestDetailV4CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


