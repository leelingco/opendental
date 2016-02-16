//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchLabResultsCompletedEventHandler;
import OpenDental.MobileWeb.SynchLabResultsCompletedEventHandler;

/**
* 
*/
public class __MultiSynchLabResultsCompletedEventHandler   implements SynchLabResultsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchLabResultsCompletedEventHandler> copy = new IList<SynchLabResultsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchLabResultsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchLabResultsCompletedEventHandler d = (SynchLabResultsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchLabResultsCompletedEventHandler> _invocationList = new ArrayList<SynchLabResultsCompletedEventHandler>();
    public static SynchLabResultsCompletedEventHandler combine(SynchLabResultsCompletedEventHandler a, SynchLabResultsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchLabResultsCompletedEventHandler ret = new __MultiSynchLabResultsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchLabResultsCompletedEventHandler remove(SynchLabResultsCompletedEventHandler a, SynchLabResultsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchLabResultsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchLabResultsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchLabResultsCompletedEventHandler ret = new __MultiSynchLabResultsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchLabResultsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


