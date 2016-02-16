//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetSideEffectsCompletedEventHandler;
import OpenDental.NewCrop.GetSideEffectsCompletedEventArgs;
import OpenDental.NewCrop.GetSideEffectsCompletedEventHandler;

/**
* 
*/
public class __MultiGetSideEffectsCompletedEventHandler   implements GetSideEffectsCompletedEventHandler
{
    public void invoke(Object sender, GetSideEffectsCompletedEventArgs e) throws Exception {
        IList<GetSideEffectsCompletedEventHandler> copy = new IList<GetSideEffectsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetSideEffectsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetSideEffectsCompletedEventHandler d = (GetSideEffectsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetSideEffectsCompletedEventHandler> _invocationList = new ArrayList<GetSideEffectsCompletedEventHandler>();
    public static GetSideEffectsCompletedEventHandler combine(GetSideEffectsCompletedEventHandler a, GetSideEffectsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetSideEffectsCompletedEventHandler ret = new __MultiGetSideEffectsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetSideEffectsCompletedEventHandler remove(GetSideEffectsCompletedEventHandler a, GetSideEffectsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetSideEffectsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetSideEffectsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetSideEffectsCompletedEventHandler ret = new __MultiGetSideEffectsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetSideEffectsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


