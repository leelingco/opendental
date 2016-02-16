//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchMedicationPatsCompletedEventHandler;
import OpenDental.MobileWeb.SynchMedicationPatsCompletedEventHandler;

/**
* 
*/
public class __MultiSynchMedicationPatsCompletedEventHandler   implements SynchMedicationPatsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchMedicationPatsCompletedEventHandler> copy = new IList<SynchMedicationPatsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchMedicationPatsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchMedicationPatsCompletedEventHandler d = (SynchMedicationPatsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchMedicationPatsCompletedEventHandler> _invocationList = new ArrayList<SynchMedicationPatsCompletedEventHandler>();
    public static SynchMedicationPatsCompletedEventHandler combine(SynchMedicationPatsCompletedEventHandler a, SynchMedicationPatsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchMedicationPatsCompletedEventHandler ret = new __MultiSynchMedicationPatsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchMedicationPatsCompletedEventHandler remove(SynchMedicationPatsCompletedEventHandler a, SynchMedicationPatsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchMedicationPatsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchMedicationPatsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchMedicationPatsCompletedEventHandler ret = new __MultiSynchMedicationPatsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchMedicationPatsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


