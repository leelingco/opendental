//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiIsPaidCustomerCompletedEventHandler;
import OpenDental.MobileWeb.IsPaidCustomerCompletedEventArgs;
import OpenDental.MobileWeb.IsPaidCustomerCompletedEventHandler;

/**
* 
*/
public class __MultiIsPaidCustomerCompletedEventHandler   implements IsPaidCustomerCompletedEventHandler
{
    public void invoke(Object sender, IsPaidCustomerCompletedEventArgs e) throws Exception {
        IList<IsPaidCustomerCompletedEventHandler> copy = new IList<IsPaidCustomerCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<IsPaidCustomerCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            IsPaidCustomerCompletedEventHandler d = (IsPaidCustomerCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<IsPaidCustomerCompletedEventHandler> _invocationList = new ArrayList<IsPaidCustomerCompletedEventHandler>();
    public static IsPaidCustomerCompletedEventHandler combine(IsPaidCustomerCompletedEventHandler a, IsPaidCustomerCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiIsPaidCustomerCompletedEventHandler ret = new __MultiIsPaidCustomerCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static IsPaidCustomerCompletedEventHandler remove(IsPaidCustomerCompletedEventHandler a, IsPaidCustomerCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<IsPaidCustomerCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<IsPaidCustomerCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiIsPaidCustomerCompletedEventHandler ret = new __MultiIsPaidCustomerCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<IsPaidCustomerCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


