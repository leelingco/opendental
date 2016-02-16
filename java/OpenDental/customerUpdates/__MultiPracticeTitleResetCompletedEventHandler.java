//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.customerUpdates;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.customerUpdates.__MultiPracticeTitleResetCompletedEventHandler;
import OpenDental.customerUpdates.PracticeTitleResetCompletedEventHandler;

/**
* 
*/
public class __MultiPracticeTitleResetCompletedEventHandler   implements PracticeTitleResetCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<PracticeTitleResetCompletedEventHandler> copy = new IList<PracticeTitleResetCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<PracticeTitleResetCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            PracticeTitleResetCompletedEventHandler d = (PracticeTitleResetCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<PracticeTitleResetCompletedEventHandler> _invocationList = new ArrayList<PracticeTitleResetCompletedEventHandler>();
    public static PracticeTitleResetCompletedEventHandler combine(PracticeTitleResetCompletedEventHandler a, PracticeTitleResetCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiPracticeTitleResetCompletedEventHandler ret = new __MultiPracticeTitleResetCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static PracticeTitleResetCompletedEventHandler remove(PracticeTitleResetCompletedEventHandler a, PracticeTitleResetCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<PracticeTitleResetCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<PracticeTitleResetCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiPracticeTitleResetCompletedEventHandler ret = new __MultiPracticeTitleResetCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<PracticeTitleResetCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


