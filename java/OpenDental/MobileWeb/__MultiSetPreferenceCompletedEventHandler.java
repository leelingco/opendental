//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSetPreferenceCompletedEventHandler;
import OpenDental.MobileWeb.SetPreferenceCompletedEventHandler;

/**
* 
*/
public class __MultiSetPreferenceCompletedEventHandler   implements SetPreferenceCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SetPreferenceCompletedEventHandler> copy = new IList<SetPreferenceCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SetPreferenceCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SetPreferenceCompletedEventHandler d = (SetPreferenceCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SetPreferenceCompletedEventHandler> _invocationList = new ArrayList<SetPreferenceCompletedEventHandler>();
    public static SetPreferenceCompletedEventHandler combine(SetPreferenceCompletedEventHandler a, SetPreferenceCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSetPreferenceCompletedEventHandler ret = new __MultiSetPreferenceCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SetPreferenceCompletedEventHandler remove(SetPreferenceCompletedEventHandler a, SetPreferenceCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SetPreferenceCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SetPreferenceCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSetPreferenceCompletedEventHandler ret = new __MultiSetPreferenceCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SetPreferenceCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


