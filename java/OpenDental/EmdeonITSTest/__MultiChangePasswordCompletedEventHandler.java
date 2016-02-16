//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.EmdeonITSTest;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.EmdeonITSTest.__MultiChangePasswordCompletedEventHandler;
import OpenDental.EmdeonITSTest.ChangePasswordCompletedEventArgs;
import OpenDental.EmdeonITSTest.ChangePasswordCompletedEventHandler;

/**
* 
*/
public class __MultiChangePasswordCompletedEventHandler   implements ChangePasswordCompletedEventHandler
{
    public void invoke(Object sender, ChangePasswordCompletedEventArgs e) throws Exception {
        IList<ChangePasswordCompletedEventHandler> copy = new IList<ChangePasswordCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<ChangePasswordCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            ChangePasswordCompletedEventHandler d = (ChangePasswordCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<ChangePasswordCompletedEventHandler> _invocationList = new ArrayList<ChangePasswordCompletedEventHandler>();
    public static ChangePasswordCompletedEventHandler combine(ChangePasswordCompletedEventHandler a, ChangePasswordCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiChangePasswordCompletedEventHandler ret = new __MultiChangePasswordCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static ChangePasswordCompletedEventHandler remove(ChangePasswordCompletedEventHandler a, ChangePasswordCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<ChangePasswordCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<ChangePasswordCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiChangePasswordCompletedEventHandler ret = new __MultiChangePasswordCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<ChangePasswordCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


