//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiCheckRegistrationKeyCompletedEventHandler;
import OpenDental.WebSheets.CheckRegistrationKeyCompletedEventArgs;
import OpenDental.WebSheets.CheckRegistrationKeyCompletedEventHandler;

/**
* 
*/
public class __MultiCheckRegistrationKeyCompletedEventHandler   implements CheckRegistrationKeyCompletedEventHandler
{
    public void invoke(Object sender, CheckRegistrationKeyCompletedEventArgs e) throws Exception {
        IList<CheckRegistrationKeyCompletedEventHandler> copy = new IList<CheckRegistrationKeyCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<CheckRegistrationKeyCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            CheckRegistrationKeyCompletedEventHandler d = (CheckRegistrationKeyCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<CheckRegistrationKeyCompletedEventHandler> _invocationList = new ArrayList<CheckRegistrationKeyCompletedEventHandler>();
    public static CheckRegistrationKeyCompletedEventHandler combine(CheckRegistrationKeyCompletedEventHandler a, CheckRegistrationKeyCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiCheckRegistrationKeyCompletedEventHandler ret = new __MultiCheckRegistrationKeyCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static CheckRegistrationKeyCompletedEventHandler remove(CheckRegistrationKeyCompletedEventHandler a, CheckRegistrationKeyCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<CheckRegistrationKeyCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<CheckRegistrationKeyCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiCheckRegistrationKeyCompletedEventHandler ret = new __MultiCheckRegistrationKeyCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<CheckRegistrationKeyCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


