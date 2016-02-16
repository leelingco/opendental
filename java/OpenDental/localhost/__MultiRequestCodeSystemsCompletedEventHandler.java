//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.__MultiRequestCodeSystemsCompletedEventHandler;
import OpenDental.localhost.RequestCodeSystemsCompletedEventArgs;
import OpenDental.localhost.RequestCodeSystemsCompletedEventHandler;

/**
* 
*/
public class __MultiRequestCodeSystemsCompletedEventHandler   implements RequestCodeSystemsCompletedEventHandler
{
    public void invoke(Object sender, RequestCodeSystemsCompletedEventArgs e) throws Exception {
        IList<RequestCodeSystemsCompletedEventHandler> copy = new IList<RequestCodeSystemsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<RequestCodeSystemsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            RequestCodeSystemsCompletedEventHandler d = (RequestCodeSystemsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<RequestCodeSystemsCompletedEventHandler> _invocationList = new ArrayList<RequestCodeSystemsCompletedEventHandler>();
    public static RequestCodeSystemsCompletedEventHandler combine(RequestCodeSystemsCompletedEventHandler a, RequestCodeSystemsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiRequestCodeSystemsCompletedEventHandler ret = new __MultiRequestCodeSystemsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static RequestCodeSystemsCompletedEventHandler remove(RequestCodeSystemsCompletedEventHandler a, RequestCodeSystemsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<RequestCodeSystemsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<RequestCodeSystemsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiRequestCodeSystemsCompletedEventHandler ret = new __MultiRequestCodeSystemsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<RequestCodeSystemsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


