//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.com.dentalxchange.webservices.__MultiupdateTerminalCompletedEventHandler;
import OpenDental.com.dentalxchange.webservices.updateTerminalCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.updateTerminalCompletedEventHandler;

/**
* 
*/
public class __MultiupdateTerminalCompletedEventHandler   implements updateTerminalCompletedEventHandler
{
    public void invoke(Object sender, updateTerminalCompletedEventArgs e) throws Exception {
        IList<updateTerminalCompletedEventHandler> copy = new IList<updateTerminalCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<updateTerminalCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            updateTerminalCompletedEventHandler d = (updateTerminalCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<updateTerminalCompletedEventHandler> _invocationList = new ArrayList<updateTerminalCompletedEventHandler>();
    public static updateTerminalCompletedEventHandler combine(updateTerminalCompletedEventHandler a, updateTerminalCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiupdateTerminalCompletedEventHandler ret = new __MultiupdateTerminalCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static updateTerminalCompletedEventHandler remove(updateTerminalCompletedEventHandler a, updateTerminalCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<updateTerminalCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<updateTerminalCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiupdateTerminalCompletedEventHandler ret = new __MultiupdateTerminalCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<updateTerminalCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


