//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSynchDocumentsCompletedEventHandler;
import OpenDental.MobileWeb.SynchDocumentsCompletedEventHandler;

/**
* 
*/
public class __MultiSynchDocumentsCompletedEventHandler   implements SynchDocumentsCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SynchDocumentsCompletedEventHandler> copy = new IList<SynchDocumentsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SynchDocumentsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SynchDocumentsCompletedEventHandler d = (SynchDocumentsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SynchDocumentsCompletedEventHandler> _invocationList = new ArrayList<SynchDocumentsCompletedEventHandler>();
    public static SynchDocumentsCompletedEventHandler combine(SynchDocumentsCompletedEventHandler a, SynchDocumentsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSynchDocumentsCompletedEventHandler ret = new __MultiSynchDocumentsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SynchDocumentsCompletedEventHandler remove(SynchDocumentsCompletedEventHandler a, SynchDocumentsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SynchDocumentsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SynchDocumentsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSynchDocumentsCompletedEventHandler ret = new __MultiSynchDocumentsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SynchDocumentsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


