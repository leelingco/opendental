//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchStatementsCompletedEventHandler;
import OpenDental.MobileWeb.SynchStatementsCompletedEventHandler;

/**
* 
*/
public class __MultiSynchStatementsCompletedEventHandler   implements SynchStatementsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchStatementsCompletedEventHandler> copy = new IList<SynchStatementsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchStatementsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchStatementsCompletedEventHandler d = (SynchStatementsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchStatementsCompletedEventHandler> _invocationList = new ArrayList<SynchStatementsCompletedEventHandler>();
    public static SynchStatementsCompletedEventHandler combine(SynchStatementsCompletedEventHandler a, SynchStatementsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchStatementsCompletedEventHandler ret = new __MultiSynchStatementsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchStatementsCompletedEventHandler remove(SynchStatementsCompletedEventHandler a, SynchStatementsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchStatementsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchStatementsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchStatementsCompletedEventHandler ret = new __MultiSynchStatementsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchStatementsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


