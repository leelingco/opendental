//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchAllergiesCompletedEventHandler;
import OpenDental.MobileWeb.SynchAllergiesCompletedEventHandler;

/**
* 
*/
public class __MultiSynchAllergiesCompletedEventHandler   implements SynchAllergiesCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchAllergiesCompletedEventHandler> copy = new IList<SynchAllergiesCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchAllergiesCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchAllergiesCompletedEventHandler d = (SynchAllergiesCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchAllergiesCompletedEventHandler> _invocationList = new ArrayList<SynchAllergiesCompletedEventHandler>();
    public static SynchAllergiesCompletedEventHandler combine(SynchAllergiesCompletedEventHandler a, SynchAllergiesCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchAllergiesCompletedEventHandler ret = new __MultiSynchAllergiesCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchAllergiesCompletedEventHandler remove(SynchAllergiesCompletedEventHandler a, SynchAllergiesCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchAllergiesCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchAllergiesCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchAllergiesCompletedEventHandler ret = new __MultiSynchAllergiesCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchAllergiesCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


