//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchAllergyDefsCompletedEventHandler;
import OpenDental.MobileWeb.SynchAllergyDefsCompletedEventHandler;

/**
* 
*/
public class __MultiSynchAllergyDefsCompletedEventHandler   implements SynchAllergyDefsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchAllergyDefsCompletedEventHandler> copy = new IList<SynchAllergyDefsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchAllergyDefsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchAllergyDefsCompletedEventHandler d = (SynchAllergyDefsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchAllergyDefsCompletedEventHandler> _invocationList = new ArrayList<SynchAllergyDefsCompletedEventHandler>();
    public static SynchAllergyDefsCompletedEventHandler combine(SynchAllergyDefsCompletedEventHandler a, SynchAllergyDefsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchAllergyDefsCompletedEventHandler ret = new __MultiSynchAllergyDefsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchAllergyDefsCompletedEventHandler remove(SynchAllergyDefsCompletedEventHandler a, SynchAllergyDefsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchAllergyDefsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchAllergyDefsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchAllergyDefsCompletedEventHandler ret = new __MultiSynchAllergyDefsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchAllergyDefsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


