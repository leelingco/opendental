//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchPrescriptionsCompletedEventHandler;
import OpenDental.MobileWeb.SynchPrescriptionsCompletedEventHandler;

/**
* 
*/
public class __MultiSynchPrescriptionsCompletedEventHandler   implements SynchPrescriptionsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchPrescriptionsCompletedEventHandler> copy = new IList<SynchPrescriptionsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchPrescriptionsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchPrescriptionsCompletedEventHandler d = (SynchPrescriptionsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchPrescriptionsCompletedEventHandler> _invocationList = new ArrayList<SynchPrescriptionsCompletedEventHandler>();
    public static SynchPrescriptionsCompletedEventHandler combine(SynchPrescriptionsCompletedEventHandler a, SynchPrescriptionsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchPrescriptionsCompletedEventHandler ret = new __MultiSynchPrescriptionsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchPrescriptionsCompletedEventHandler remove(SynchPrescriptionsCompletedEventHandler a, SynchPrescriptionsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchPrescriptionsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchPrescriptionsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchPrescriptionsCompletedEventHandler ret = new __MultiSynchPrescriptionsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchPrescriptionsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


