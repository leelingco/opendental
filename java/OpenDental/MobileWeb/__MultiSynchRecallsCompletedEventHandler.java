//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchRecallsCompletedEventHandler;
import OpenDental.MobileWeb.SynchRecallsCompletedEventHandler;

/**
* 
*/
public class __MultiSynchRecallsCompletedEventHandler   implements SynchRecallsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchRecallsCompletedEventHandler> copy = new IList<SynchRecallsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchRecallsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchRecallsCompletedEventHandler d = (SynchRecallsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchRecallsCompletedEventHandler> _invocationList = new ArrayList<SynchRecallsCompletedEventHandler>();
    public static SynchRecallsCompletedEventHandler combine(SynchRecallsCompletedEventHandler a, SynchRecallsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchRecallsCompletedEventHandler ret = new __MultiSynchRecallsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchRecallsCompletedEventHandler remove(SynchRecallsCompletedEventHandler a, SynchRecallsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchRecallsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchRecallsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchRecallsCompletedEventHandler ret = new __MultiSynchRecallsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchRecallsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


