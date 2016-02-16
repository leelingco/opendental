//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchICD9sCompletedEventHandler;
import OpenDental.MobileWeb.SynchICD9sCompletedEventHandler;

/**
* 
*/
public class __MultiSynchICD9sCompletedEventHandler   implements SynchICD9sCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchICD9sCompletedEventHandler> copy = new IList<SynchICD9sCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchICD9sCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchICD9sCompletedEventHandler d = (SynchICD9sCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchICD9sCompletedEventHandler> _invocationList = new ArrayList<SynchICD9sCompletedEventHandler>();
    public static SynchICD9sCompletedEventHandler combine(SynchICD9sCompletedEventHandler a, SynchICD9sCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchICD9sCompletedEventHandler ret = new __MultiSynchICD9sCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchICD9sCompletedEventHandler remove(SynchICD9sCompletedEventHandler a, SynchICD9sCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchICD9sCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchICD9sCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchICD9sCompletedEventHandler ret = new __MultiSynchICD9sCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchICD9sCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


