//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchPatientsCompletedEventHandler;
import OpenDental.MobileWeb.SynchPatientsCompletedEventHandler;

/**
* 
*/
public class __MultiSynchPatientsCompletedEventHandler   implements SynchPatientsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchPatientsCompletedEventHandler> copy = new IList<SynchPatientsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchPatientsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchPatientsCompletedEventHandler d = (SynchPatientsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchPatientsCompletedEventHandler> _invocationList = new ArrayList<SynchPatientsCompletedEventHandler>();
    public static SynchPatientsCompletedEventHandler combine(SynchPatientsCompletedEventHandler a, SynchPatientsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchPatientsCompletedEventHandler ret = new __MultiSynchPatientsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchPatientsCompletedEventHandler remove(SynchPatientsCompletedEventHandler a, SynchPatientsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchPatientsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchPatientsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchPatientsCompletedEventHandler ret = new __MultiSynchPatientsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchPatientsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


