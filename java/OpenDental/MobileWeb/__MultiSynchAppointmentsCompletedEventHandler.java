//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchAppointmentsCompletedEventHandler;
import OpenDental.MobileWeb.SynchAppointmentsCompletedEventHandler;

/**
* 
*/
public class __MultiSynchAppointmentsCompletedEventHandler   implements SynchAppointmentsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchAppointmentsCompletedEventHandler> copy = new IList<SynchAppointmentsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchAppointmentsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchAppointmentsCompletedEventHandler d = (SynchAppointmentsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchAppointmentsCompletedEventHandler> _invocationList = new ArrayList<SynchAppointmentsCompletedEventHandler>();
    public static SynchAppointmentsCompletedEventHandler combine(SynchAppointmentsCompletedEventHandler a, SynchAppointmentsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchAppointmentsCompletedEventHandler ret = new __MultiSynchAppointmentsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchAppointmentsCompletedEventHandler remove(SynchAppointmentsCompletedEventHandler a, SynchAppointmentsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchAppointmentsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchAppointmentsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchAppointmentsCompletedEventHandler ret = new __MultiSynchAppointmentsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchAppointmentsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


