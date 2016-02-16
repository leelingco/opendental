//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchDiseasesCompletedEventHandler;
import OpenDental.MobileWeb.SynchDiseasesCompletedEventHandler;

/**
* 
*/
public class __MultiSynchDiseasesCompletedEventHandler   implements SynchDiseasesCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchDiseasesCompletedEventHandler> copy = new IList<SynchDiseasesCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchDiseasesCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchDiseasesCompletedEventHandler d = (SynchDiseasesCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchDiseasesCompletedEventHandler> _invocationList = new ArrayList<SynchDiseasesCompletedEventHandler>();
    public static SynchDiseasesCompletedEventHandler combine(SynchDiseasesCompletedEventHandler a, SynchDiseasesCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchDiseasesCompletedEventHandler ret = new __MultiSynchDiseasesCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchDiseasesCompletedEventHandler remove(SynchDiseasesCompletedEventHandler a, SynchDiseasesCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchDiseasesCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchDiseasesCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchDiseasesCompletedEventHandler ret = new __MultiSynchDiseasesCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchDiseasesCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


