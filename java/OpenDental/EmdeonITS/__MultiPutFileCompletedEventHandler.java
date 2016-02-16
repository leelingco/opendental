//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.EmdeonITS;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.EmdeonITS.__MultiPutFileCompletedEventHandler;
import OpenDental.EmdeonITS.PutFileCompletedEventArgs;
import OpenDental.EmdeonITS.PutFileCompletedEventHandler;

/**
* 
*/
public class __MultiPutFileCompletedEventHandler   implements PutFileCompletedEventHandler
{
    public void invoke(Object sender, PutFileCompletedEventArgs e) throws Exception {
        IList<PutFileCompletedEventHandler> copy = new IList<PutFileCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<PutFileCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            PutFileCompletedEventHandler d = (PutFileCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<PutFileCompletedEventHandler> _invocationList = new ArrayList<PutFileCompletedEventHandler>();
    public static PutFileCompletedEventHandler combine(PutFileCompletedEventHandler a, PutFileCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiPutFileCompletedEventHandler ret = new __MultiPutFileCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static PutFileCompletedEventHandler remove(PutFileCompletedEventHandler a, PutFileCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<PutFileCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<PutFileCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiPutFileCompletedEventHandler ret = new __MultiPutFileCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<PutFileCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


