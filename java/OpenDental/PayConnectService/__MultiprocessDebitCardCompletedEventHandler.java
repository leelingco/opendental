//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.__MultiprocessDebitCardCompletedEventHandler;
import OpenDental.PayConnectService.processDebitCardCompletedEventArgs;
import OpenDental.PayConnectService.processDebitCardCompletedEventHandler;

/**
* 
*/
public class __MultiprocessDebitCardCompletedEventHandler   implements processDebitCardCompletedEventHandler
{
    public void invoke(Object sender, processDebitCardCompletedEventArgs e) throws Exception {
        IList<processDebitCardCompletedEventHandler> copy = new IList<processDebitCardCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<processDebitCardCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            processDebitCardCompletedEventHandler d = (processDebitCardCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<processDebitCardCompletedEventHandler> _invocationList = new ArrayList<processDebitCardCompletedEventHandler>();
    public static processDebitCardCompletedEventHandler combine(processDebitCardCompletedEventHandler a, processDebitCardCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiprocessDebitCardCompletedEventHandler ret = new __MultiprocessDebitCardCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static processDebitCardCompletedEventHandler remove(processDebitCardCompletedEventHandler a, processDebitCardCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<processDebitCardCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<processDebitCardCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiprocessDebitCardCompletedEventHandler ret = new __MultiprocessDebitCardCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<processDebitCardCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


