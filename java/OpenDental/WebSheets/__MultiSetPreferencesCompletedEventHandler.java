//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiSetPreferencesCompletedEventHandler;
import OpenDental.WebSheets.SetPreferencesCompletedEventArgs;
import OpenDental.WebSheets.SetPreferencesCompletedEventHandler;

/**
* 
*/
public class __MultiSetPreferencesCompletedEventHandler   implements SetPreferencesCompletedEventHandler
{
    public void invoke(Object sender, SetPreferencesCompletedEventArgs e) throws Exception {
        IList<SetPreferencesCompletedEventHandler> copy = new IList<SetPreferencesCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SetPreferencesCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SetPreferencesCompletedEventHandler d = (SetPreferencesCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SetPreferencesCompletedEventHandler> _invocationList = new ArrayList<SetPreferencesCompletedEventHandler>();
    public static SetPreferencesCompletedEventHandler combine(SetPreferencesCompletedEventHandler a, SetPreferencesCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSetPreferencesCompletedEventHandler ret = new __MultiSetPreferencesCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SetPreferencesCompletedEventHandler remove(SetPreferencesCompletedEventHandler a, SetPreferencesCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SetPreferencesCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SetPreferencesCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSetPreferencesCompletedEventHandler ret = new __MultiSetPreferencesCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SetPreferencesCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


