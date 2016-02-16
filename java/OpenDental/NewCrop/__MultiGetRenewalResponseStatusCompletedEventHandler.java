//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetRenewalResponseStatusCompletedEventHandler;
import OpenDental.NewCrop.GetRenewalResponseStatusCompletedEventArgs;
import OpenDental.NewCrop.GetRenewalResponseStatusCompletedEventHandler;

/**
* 
*/
public class __MultiGetRenewalResponseStatusCompletedEventHandler   implements GetRenewalResponseStatusCompletedEventHandler
{
    public void invoke(Object sender, GetRenewalResponseStatusCompletedEventArgs e) throws Exception {
        IList<GetRenewalResponseStatusCompletedEventHandler> copy = new IList<GetRenewalResponseStatusCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetRenewalResponseStatusCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetRenewalResponseStatusCompletedEventHandler d = (GetRenewalResponseStatusCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetRenewalResponseStatusCompletedEventHandler> _invocationList = new ArrayList<GetRenewalResponseStatusCompletedEventHandler>();
    public static GetRenewalResponseStatusCompletedEventHandler combine(GetRenewalResponseStatusCompletedEventHandler a, GetRenewalResponseStatusCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetRenewalResponseStatusCompletedEventHandler ret = new __MultiGetRenewalResponseStatusCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetRenewalResponseStatusCompletedEventHandler remove(GetRenewalResponseStatusCompletedEventHandler a, GetRenewalResponseStatusCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetRenewalResponseStatusCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetRenewalResponseStatusCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetRenewalResponseStatusCompletedEventHandler ret = new __MultiGetRenewalResponseStatusCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetRenewalResponseStatusCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


