//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiDoseCheckCompletedEventHandler;
import OpenDental.NewCrop.DoseCheckCompletedEventArgs;
import OpenDental.NewCrop.DoseCheckCompletedEventHandler;

/**
* 
*/
public class __MultiDoseCheckCompletedEventHandler   implements DoseCheckCompletedEventHandler
{
    public void invoke(Object sender, DoseCheckCompletedEventArgs e) throws Exception {
        IList<DoseCheckCompletedEventHandler> copy = new IList<DoseCheckCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DoseCheckCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DoseCheckCompletedEventHandler d = (DoseCheckCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<DoseCheckCompletedEventHandler> _invocationList = new ArrayList<DoseCheckCompletedEventHandler>();
    public static DoseCheckCompletedEventHandler combine(DoseCheckCompletedEventHandler a, DoseCheckCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDoseCheckCompletedEventHandler ret = new __MultiDoseCheckCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DoseCheckCompletedEventHandler remove(DoseCheckCompletedEventHandler a, DoseCheckCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<DoseCheckCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<DoseCheckCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDoseCheckCompletedEventHandler ret = new __MultiDoseCheckCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<DoseCheckCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


