//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness.OpenDentalServer;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDentBusiness.OpenDentalServer.__MultiProcessRequestCompletedEventHandler;
import OpenDentBusiness.OpenDentalServer.ProcessRequestCompletedEventArgs;
import OpenDentBusiness.OpenDentalServer.ProcessRequestCompletedEventHandler;

/**
* 
*/
public class __MultiProcessRequestCompletedEventHandler   implements ProcessRequestCompletedEventHandler
{
    public void invoke(Object sender, ProcessRequestCompletedEventArgs e) throws Exception {
        IList<ProcessRequestCompletedEventHandler> copy = new IList<ProcessRequestCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<ProcessRequestCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            ProcessRequestCompletedEventHandler d = (ProcessRequestCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<ProcessRequestCompletedEventHandler> _invocationList = new ArrayList<ProcessRequestCompletedEventHandler>();
    public static ProcessRequestCompletedEventHandler combine(ProcessRequestCompletedEventHandler a, ProcessRequestCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiProcessRequestCompletedEventHandler ret = new __MultiProcessRequestCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static ProcessRequestCompletedEventHandler remove(ProcessRequestCompletedEventHandler a, ProcessRequestCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<ProcessRequestCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<ProcessRequestCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiProcessRequestCompletedEventHandler ret = new __MultiProcessRequestCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<ProcessRequestCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


