//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.EmdeonITSTest;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.EmdeonITSTest.__MultiAuthenticateCompletedEventHandler;
import OpenDental.EmdeonITSTest.AuthenticateCompletedEventArgs;
import OpenDental.EmdeonITSTest.AuthenticateCompletedEventHandler;

/**
* 
*/
public class __MultiAuthenticateCompletedEventHandler   implements AuthenticateCompletedEventHandler
{
    public void invoke(Object sender, AuthenticateCompletedEventArgs e) throws Exception {
        IList<AuthenticateCompletedEventHandler> copy = new IList<AuthenticateCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<AuthenticateCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            AuthenticateCompletedEventHandler d = (AuthenticateCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<AuthenticateCompletedEventHandler> _invocationList = new ArrayList<AuthenticateCompletedEventHandler>();
    public static AuthenticateCompletedEventHandler combine(AuthenticateCompletedEventHandler a, AuthenticateCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiAuthenticateCompletedEventHandler ret = new __MultiAuthenticateCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static AuthenticateCompletedEventHandler remove(AuthenticateCompletedEventHandler a, AuthenticateCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<AuthenticateCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<AuthenticateCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiAuthenticateCompletedEventHandler ret = new __MultiAuthenticateCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<AuthenticateCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


