//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.__MultiSetPreferencesV2CompletedEventHandler;
import OpenDental.WebSheets.SetPreferencesV2CompletedEventArgs;
import OpenDental.WebSheets.SetPreferencesV2CompletedEventHandler;

/**
* 
*/
public class __MultiSetPreferencesV2CompletedEventHandler   implements SetPreferencesV2CompletedEventHandler
{
    public void invoke(Object sender, SetPreferencesV2CompletedEventArgs e) throws Exception {
        IList<SetPreferencesV2CompletedEventHandler> copy = new IList<SetPreferencesV2CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SetPreferencesV2CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SetPreferencesV2CompletedEventHandler d = (SetPreferencesV2CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SetPreferencesV2CompletedEventHandler> _invocationList = new ArrayList<SetPreferencesV2CompletedEventHandler>();
    public static SetPreferencesV2CompletedEventHandler combine(SetPreferencesV2CompletedEventHandler a, SetPreferencesV2CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSetPreferencesV2CompletedEventHandler ret = new __MultiSetPreferencesV2CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SetPreferencesV2CompletedEventHandler remove(SetPreferencesV2CompletedEventHandler a, SetPreferencesV2CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SetPreferencesV2CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SetPreferencesV2CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSetPreferencesV2CompletedEventHandler ret = new __MultiSetPreferencesV2CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SetPreferencesV2CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


