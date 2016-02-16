//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchMedicationsCompletedEventHandler;
import OpenDental.MobileWeb.SynchMedicationsCompletedEventHandler;

/**
* 
*/
public class __MultiSynchMedicationsCompletedEventHandler   implements SynchMedicationsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchMedicationsCompletedEventHandler> copy = new IList<SynchMedicationsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchMedicationsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchMedicationsCompletedEventHandler d = (SynchMedicationsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchMedicationsCompletedEventHandler> _invocationList = new ArrayList<SynchMedicationsCompletedEventHandler>();
    public static SynchMedicationsCompletedEventHandler combine(SynchMedicationsCompletedEventHandler a, SynchMedicationsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchMedicationsCompletedEventHandler ret = new __MultiSynchMedicationsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchMedicationsCompletedEventHandler remove(SynchMedicationsCompletedEventHandler a, SynchMedicationsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchMedicationsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchMedicationsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchMedicationsCompletedEventHandler ret = new __MultiSynchMedicationsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchMedicationsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


