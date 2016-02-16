//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.__MultiRequestFeeSchedCompletedEventHandler;
import OpenDental.localhost.RequestFeeSchedCompletedEventArgs;
import OpenDental.localhost.RequestFeeSchedCompletedEventHandler;

/**
* 
*/
public class __MultiRequestFeeSchedCompletedEventHandler   implements RequestFeeSchedCompletedEventHandler
{
    public void invoke(Object sender, RequestFeeSchedCompletedEventArgs e) throws Exception {
        IList<RequestFeeSchedCompletedEventHandler> copy = new IList<RequestFeeSchedCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<RequestFeeSchedCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            RequestFeeSchedCompletedEventHandler d = (RequestFeeSchedCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<RequestFeeSchedCompletedEventHandler> _invocationList = new ArrayList<RequestFeeSchedCompletedEventHandler>();
    public static RequestFeeSchedCompletedEventHandler combine(RequestFeeSchedCompletedEventHandler a, RequestFeeSchedCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiRequestFeeSchedCompletedEventHandler ret = new __MultiRequestFeeSchedCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static RequestFeeSchedCompletedEventHandler remove(RequestFeeSchedCompletedEventHandler a, RequestFeeSchedCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<RequestFeeSchedCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<RequestFeeSchedCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiRequestFeeSchedCompletedEventHandler ret = new __MultiRequestFeeSchedCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<RequestFeeSchedCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


