//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.__MultiValidEventHandler;

/**
* 
*/
public class __MultiValidEventHandler   implements OpenDental.ValidEventHandler
{
    public void invoke(OpenDental.ValidEventArgs e) throws Exception {
        IList<OpenDental.ValidEventHandler> copy = new IList<OpenDental.ValidEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<OpenDental.ValidEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            OpenDental.ValidEventHandler d = (OpenDental.ValidEventHandler)__dummyForeachVar0;
            d.invoke(e);
        }
    }

    private System.Collections.Generic.IList<OpenDental.ValidEventHandler> _invocationList = new ArrayList<OpenDental.ValidEventHandler>();
    public static OpenDental.ValidEventHandler combine(OpenDental.ValidEventHandler a, OpenDental.ValidEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiValidEventHandler ret = new __MultiValidEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static OpenDental.ValidEventHandler remove(OpenDental.ValidEventHandler a, OpenDental.ValidEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<OpenDental.ValidEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<OpenDental.ValidEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiValidEventHandler ret = new __MultiValidEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<OpenDental.ValidEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


