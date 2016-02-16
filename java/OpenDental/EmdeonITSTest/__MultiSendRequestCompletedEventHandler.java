//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.EmdeonITSTest;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.EmdeonITSTest.__MultiSendRequestCompletedEventHandler;
import OpenDental.EmdeonITSTest.SendRequestCompletedEventArgs;
import OpenDental.EmdeonITSTest.SendRequestCompletedEventHandler;

/**
* 
*/
public class __MultiSendRequestCompletedEventHandler   implements SendRequestCompletedEventHandler
{
    public void invoke(Object sender, SendRequestCompletedEventArgs e) throws Exception {
        IList<SendRequestCompletedEventHandler> copy = new IList<SendRequestCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SendRequestCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SendRequestCompletedEventHandler d = (SendRequestCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SendRequestCompletedEventHandler> _invocationList = new ArrayList<SendRequestCompletedEventHandler>();
    public static SendRequestCompletedEventHandler combine(SendRequestCompletedEventHandler a, SendRequestCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSendRequestCompletedEventHandler ret = new __MultiSendRequestCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SendRequestCompletedEventHandler remove(SendRequestCompletedEventHandler a, SendRequestCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SendRequestCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SendRequestCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSendRequestCompletedEventHandler ret = new __MultiSendRequestCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SendRequestCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


