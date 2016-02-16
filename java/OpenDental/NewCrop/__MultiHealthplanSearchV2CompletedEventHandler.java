//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiHealthplanSearchV2CompletedEventHandler;
import OpenDental.NewCrop.HealthplanSearchV2CompletedEventArgs;
import OpenDental.NewCrop.HealthplanSearchV2CompletedEventHandler;

/**
* 
*/
public class __MultiHealthplanSearchV2CompletedEventHandler   implements HealthplanSearchV2CompletedEventHandler
{
    public void invoke(Object sender, HealthplanSearchV2CompletedEventArgs e) throws Exception {
        IList<HealthplanSearchV2CompletedEventHandler> copy = new IList<HealthplanSearchV2CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<HealthplanSearchV2CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            HealthplanSearchV2CompletedEventHandler d = (HealthplanSearchV2CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<HealthplanSearchV2CompletedEventHandler> _invocationList = new ArrayList<HealthplanSearchV2CompletedEventHandler>();
    public static HealthplanSearchV2CompletedEventHandler combine(HealthplanSearchV2CompletedEventHandler a, HealthplanSearchV2CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiHealthplanSearchV2CompletedEventHandler ret = new __MultiHealthplanSearchV2CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static HealthplanSearchV2CompletedEventHandler remove(HealthplanSearchV2CompletedEventHandler a, HealthplanSearchV2CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<HealthplanSearchV2CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<HealthplanSearchV2CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiHealthplanSearchV2CompletedEventHandler ret = new __MultiHealthplanSearchV2CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<HealthplanSearchV2CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


