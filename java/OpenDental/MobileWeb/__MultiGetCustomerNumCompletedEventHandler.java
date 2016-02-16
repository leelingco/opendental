//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiGetCustomerNumCompletedEventHandler;
import OpenDental.MobileWeb.GetCustomerNumCompletedEventArgs;
import OpenDental.MobileWeb.GetCustomerNumCompletedEventHandler;

/**
* 
*/
public class __MultiGetCustomerNumCompletedEventHandler   implements GetCustomerNumCompletedEventHandler
{
    public void invoke(Object sender, GetCustomerNumCompletedEventArgs e) throws Exception {
        IList<GetCustomerNumCompletedEventHandler> copy = new IList<GetCustomerNumCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetCustomerNumCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetCustomerNumCompletedEventHandler d = (GetCustomerNumCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetCustomerNumCompletedEventHandler> _invocationList = new ArrayList<GetCustomerNumCompletedEventHandler>();
    public static GetCustomerNumCompletedEventHandler combine(GetCustomerNumCompletedEventHandler a, GetCustomerNumCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetCustomerNumCompletedEventHandler ret = new __MultiGetCustomerNumCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetCustomerNumCompletedEventHandler remove(GetCustomerNumCompletedEventHandler a, GetCustomerNumCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetCustomerNumCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetCustomerNumCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetCustomerNumCompletedEventHandler ret = new __MultiGetCustomerNumCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetCustomerNumCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


