//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiProcessRenewalRequestCompletedEventHandler;
import OpenDental.NewCrop.ProcessRenewalRequestCompletedEventArgs;
import OpenDental.NewCrop.ProcessRenewalRequestCompletedEventHandler;

/**
* 
*/
public class __MultiProcessRenewalRequestCompletedEventHandler   implements ProcessRenewalRequestCompletedEventHandler
{
    public void invoke(Object sender, ProcessRenewalRequestCompletedEventArgs e) throws Exception {
        IList<ProcessRenewalRequestCompletedEventHandler> copy = new IList<ProcessRenewalRequestCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<ProcessRenewalRequestCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            ProcessRenewalRequestCompletedEventHandler d = (ProcessRenewalRequestCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<ProcessRenewalRequestCompletedEventHandler> _invocationList = new ArrayList<ProcessRenewalRequestCompletedEventHandler>();
    public static ProcessRenewalRequestCompletedEventHandler combine(ProcessRenewalRequestCompletedEventHandler a, ProcessRenewalRequestCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiProcessRenewalRequestCompletedEventHandler ret = new __MultiProcessRenewalRequestCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static ProcessRenewalRequestCompletedEventHandler remove(ProcessRenewalRequestCompletedEventHandler a, ProcessRenewalRequestCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<ProcessRenewalRequestCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<ProcessRenewalRequestCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiProcessRenewalRequestCompletedEventHandler ret = new __MultiProcessRenewalRequestCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<ProcessRenewalRequestCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


