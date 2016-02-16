//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchPharmaciesCompletedEventHandler;
import OpenDental.MobileWeb.SynchPharmaciesCompletedEventHandler;

/**
* 
*/
public class __MultiSynchPharmaciesCompletedEventHandler   implements SynchPharmaciesCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchPharmaciesCompletedEventHandler> copy = new IList<SynchPharmaciesCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchPharmaciesCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchPharmaciesCompletedEventHandler d = (SynchPharmaciesCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchPharmaciesCompletedEventHandler> _invocationList = new ArrayList<SynchPharmaciesCompletedEventHandler>();
    public static SynchPharmaciesCompletedEventHandler combine(SynchPharmaciesCompletedEventHandler a, SynchPharmaciesCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchPharmaciesCompletedEventHandler ret = new __MultiSynchPharmaciesCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchPharmaciesCompletedEventHandler remove(SynchPharmaciesCompletedEventHandler a, SynchPharmaciesCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchPharmaciesCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchPharmaciesCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchPharmaciesCompletedEventHandler ret = new __MultiSynchPharmaciesCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchPharmaciesCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


