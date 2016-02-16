//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiSetPracticeTitleCompletedEventHandler;
import OpenDental.MobileWeb.SetPracticeTitleCompletedEventHandler;

/**
* 
*/
public class __MultiSetPracticeTitleCompletedEventHandler   implements SetPracticeTitleCompletedEventHandler
{
    public void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception {
        IList<SetPracticeTitleCompletedEventHandler> copy = new IList<SetPracticeTitleCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<SetPracticeTitleCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            SetPracticeTitleCompletedEventHandler d = (SetPracticeTitleCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<SetPracticeTitleCompletedEventHandler> _invocationList = new ArrayList<SetPracticeTitleCompletedEventHandler>();
    public static SetPracticeTitleCompletedEventHandler combine(SetPracticeTitleCompletedEventHandler a, SetPracticeTitleCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiSetPracticeTitleCompletedEventHandler ret = new __MultiSetPracticeTitleCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static SetPracticeTitleCompletedEventHandler remove(SetPracticeTitleCompletedEventHandler a, SetPracticeTitleCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<SetPracticeTitleCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<SetPracticeTitleCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiSetPracticeTitleCompletedEventHandler ret = new __MultiSetPracticeTitleCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<SetPracticeTitleCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


