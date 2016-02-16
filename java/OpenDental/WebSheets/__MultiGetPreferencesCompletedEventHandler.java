//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiGetPreferencesCompletedEventHandler;
import OpenDental.WebSheets.GetPreferencesCompletedEventArgs;
import OpenDental.WebSheets.GetPreferencesCompletedEventHandler;

/**
* 
*/
public class __MultiGetPreferencesCompletedEventHandler   implements GetPreferencesCompletedEventHandler
{
    public void invoke(Object sender, GetPreferencesCompletedEventArgs e) throws Exception {
        IList<GetPreferencesCompletedEventHandler> copy = new IList<GetPreferencesCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPreferencesCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPreferencesCompletedEventHandler d = (GetPreferencesCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPreferencesCompletedEventHandler> _invocationList = new ArrayList<GetPreferencesCompletedEventHandler>();
    public static GetPreferencesCompletedEventHandler combine(GetPreferencesCompletedEventHandler a, GetPreferencesCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPreferencesCompletedEventHandler ret = new __MultiGetPreferencesCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPreferencesCompletedEventHandler remove(GetPreferencesCompletedEventHandler a, GetPreferencesCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPreferencesCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPreferencesCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPreferencesCompletedEventHandler ret = new __MultiGetPreferencesCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPreferencesCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


