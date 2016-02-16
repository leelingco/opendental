//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.__MultiprocessCheckCompletedEventHandler;
import OpenDental.PayConnectService.processCheckCompletedEventArgs;
import OpenDental.PayConnectService.processCheckCompletedEventHandler;

/**
* 
*/
public class __MultiprocessCheckCompletedEventHandler   implements processCheckCompletedEventHandler
{
    public void invoke(Object sender, processCheckCompletedEventArgs e) throws Exception {
        IList<processCheckCompletedEventHandler> copy = new IList<processCheckCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<processCheckCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            processCheckCompletedEventHandler d = (processCheckCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<processCheckCompletedEventHandler> _invocationList = new ArrayList<processCheckCompletedEventHandler>();
    public static processCheckCompletedEventHandler combine(processCheckCompletedEventHandler a, processCheckCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiprocessCheckCompletedEventHandler ret = new __MultiprocessCheckCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static processCheckCompletedEventHandler remove(processCheckCompletedEventHandler a, processCheckCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<processCheckCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<processCheckCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiprocessCheckCompletedEventHandler ret = new __MultiprocessCheckCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<processCheckCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


