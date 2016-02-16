//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.__MultiRequestUpdateCompletedEventHandler;
import OpenDental.localhost.RequestUpdateCompletedEventArgs;
import OpenDental.localhost.RequestUpdateCompletedEventHandler;

/**
* 
*/
public class __MultiRequestUpdateCompletedEventHandler   implements RequestUpdateCompletedEventHandler
{
    public void invoke(Object sender, RequestUpdateCompletedEventArgs e) throws Exception {
        IList<RequestUpdateCompletedEventHandler> copy = new IList<RequestUpdateCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<RequestUpdateCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            RequestUpdateCompletedEventHandler d = (RequestUpdateCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<RequestUpdateCompletedEventHandler> _invocationList = new ArrayList<RequestUpdateCompletedEventHandler>();
    public static RequestUpdateCompletedEventHandler combine(RequestUpdateCompletedEventHandler a, RequestUpdateCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiRequestUpdateCompletedEventHandler ret = new __MultiRequestUpdateCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static RequestUpdateCompletedEventHandler remove(RequestUpdateCompletedEventHandler a, RequestUpdateCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<RequestUpdateCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<RequestUpdateCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiRequestUpdateCompletedEventHandler ret = new __MultiRequestUpdateCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<RequestUpdateCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


