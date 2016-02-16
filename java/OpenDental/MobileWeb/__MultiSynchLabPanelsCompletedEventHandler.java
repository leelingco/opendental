//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchLabPanelsCompletedEventHandler;
import OpenDental.MobileWeb.SynchLabPanelsCompletedEventHandler;

/**
* 
*/
public class __MultiSynchLabPanelsCompletedEventHandler   implements SynchLabPanelsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchLabPanelsCompletedEventHandler> copy = new IList<SynchLabPanelsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchLabPanelsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchLabPanelsCompletedEventHandler d = (SynchLabPanelsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchLabPanelsCompletedEventHandler> _invocationList = new ArrayList<SynchLabPanelsCompletedEventHandler>();
    public static SynchLabPanelsCompletedEventHandler combine(SynchLabPanelsCompletedEventHandler a, SynchLabPanelsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchLabPanelsCompletedEventHandler ret = new __MultiSynchLabPanelsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchLabPanelsCompletedEventHandler remove(SynchLabPanelsCompletedEventHandler a, SynchLabPanelsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchLabPanelsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchLabPanelsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchLabPanelsCompletedEventHandler ret = new __MultiSynchLabPanelsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchLabPanelsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


