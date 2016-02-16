//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.EmdeonITS;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.EmdeonITS.__MultiPutFileExtCompletedEventHandler;
import OpenDental.EmdeonITS.PutFileExtCompletedEventArgs;
import OpenDental.EmdeonITS.PutFileExtCompletedEventHandler;

/**
* 
*/
public class __MultiPutFileExtCompletedEventHandler   implements PutFileExtCompletedEventHandler
{
    public void invoke(Object sender, PutFileExtCompletedEventArgs e) throws Exception {
        IList<PutFileExtCompletedEventHandler> copy = new IList<PutFileExtCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<PutFileExtCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            PutFileExtCompletedEventHandler d = (PutFileExtCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<PutFileExtCompletedEventHandler> _invocationList = new ArrayList<PutFileExtCompletedEventHandler>();
    public static PutFileExtCompletedEventHandler combine(PutFileExtCompletedEventHandler a, PutFileExtCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiPutFileExtCompletedEventHandler ret = new __MultiPutFileExtCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static PutFileExtCompletedEventHandler remove(PutFileExtCompletedEventHandler a, PutFileExtCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<PutFileExtCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<PutFileExtCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiPutFileExtCompletedEventHandler ret = new __MultiPutFileExtCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<PutFileExtCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


