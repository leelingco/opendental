//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.__MultiModuleEventHandler;
import OpenDental.ModuleEventArgs;
import OpenDental.ModuleEventHandler;

/**
* This is used for our global module events.
*/
public class __MultiModuleEventHandler   implements ModuleEventHandler
{
    public void invoke(ModuleEventArgs e) throws Exception {
        IList<ModuleEventHandler> copy = new IList<ModuleEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<ModuleEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            ModuleEventHandler d = (ModuleEventHandler)__dummyForeachVar0;
            d.invoke(e);
        }
    }

    private System.Collections.Generic.IList<ModuleEventHandler> _invocationList = new ArrayList<ModuleEventHandler>();
    public static ModuleEventHandler combine(ModuleEventHandler a, ModuleEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiModuleEventHandler ret = new __MultiModuleEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static ModuleEventHandler remove(ModuleEventHandler a, ModuleEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<ModuleEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<ModuleEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiModuleEventHandler ret = new __MultiModuleEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<ModuleEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


