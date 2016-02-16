//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.__MultiRequestCustomerIDCompletedEventHandler;
import OpenDental.localhost.RequestCustomerIDCompletedEventArgs;
import OpenDental.localhost.RequestCustomerIDCompletedEventHandler;

/**
* 
*/
public class __MultiRequestCustomerIDCompletedEventHandler   implements RequestCustomerIDCompletedEventHandler
{
    public void invoke(Object sender, RequestCustomerIDCompletedEventArgs e) throws Exception {
        IList<RequestCustomerIDCompletedEventHandler> copy = new IList<RequestCustomerIDCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<RequestCustomerIDCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            RequestCustomerIDCompletedEventHandler d = (RequestCustomerIDCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<RequestCustomerIDCompletedEventHandler> _invocationList = new ArrayList<RequestCustomerIDCompletedEventHandler>();
    public static RequestCustomerIDCompletedEventHandler combine(RequestCustomerIDCompletedEventHandler a, RequestCustomerIDCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiRequestCustomerIDCompletedEventHandler ret = new __MultiRequestCustomerIDCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static RequestCustomerIDCompletedEventHandler remove(RequestCustomerIDCompletedEventHandler a, RequestCustomerIDCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<RequestCustomerIDCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<RequestCustomerIDCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiRequestCustomerIDCompletedEventHandler ret = new __MultiRequestCustomerIDCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<RequestCustomerIDCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


