//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchProvidersCompletedEventHandler;
import OpenDental.MobileWeb.SynchProvidersCompletedEventHandler;

/**
* 
*/
public class __MultiSynchProvidersCompletedEventHandler   implements SynchProvidersCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchProvidersCompletedEventHandler> copy = new IList<SynchProvidersCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchProvidersCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchProvidersCompletedEventHandler d = (SynchProvidersCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchProvidersCompletedEventHandler> _invocationList = new ArrayList<SynchProvidersCompletedEventHandler>();
    public static SynchProvidersCompletedEventHandler combine(SynchProvidersCompletedEventHandler a, SynchProvidersCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchProvidersCompletedEventHandler ret = new __MultiSynchProvidersCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchProvidersCompletedEventHandler remove(SynchProvidersCompletedEventHandler a, SynchProvidersCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchProvidersCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchProvidersCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchProvidersCompletedEventHandler ret = new __MultiSynchProvidersCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchProvidersCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


