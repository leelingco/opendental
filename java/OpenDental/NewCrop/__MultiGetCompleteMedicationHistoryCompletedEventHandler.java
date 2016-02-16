//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetCompleteMedicationHistoryCompletedEventHandler;
import OpenDental.NewCrop.GetCompleteMedicationHistoryCompletedEventArgs;
import OpenDental.NewCrop.GetCompleteMedicationHistoryCompletedEventHandler;

/**
* 
*/
public class __MultiGetCompleteMedicationHistoryCompletedEventHandler   implements GetCompleteMedicationHistoryCompletedEventHandler
{
    public void invoke(Object sender, GetCompleteMedicationHistoryCompletedEventArgs e) throws Exception {
        IList<GetCompleteMedicationHistoryCompletedEventHandler> copy = new IList<GetCompleteMedicationHistoryCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetCompleteMedicationHistoryCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetCompleteMedicationHistoryCompletedEventHandler d = (GetCompleteMedicationHistoryCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetCompleteMedicationHistoryCompletedEventHandler> _invocationList = new ArrayList<GetCompleteMedicationHistoryCompletedEventHandler>();
    public static GetCompleteMedicationHistoryCompletedEventHandler combine(GetCompleteMedicationHistoryCompletedEventHandler a, GetCompleteMedicationHistoryCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetCompleteMedicationHistoryCompletedEventHandler ret = new __MultiGetCompleteMedicationHistoryCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetCompleteMedicationHistoryCompletedEventHandler remove(GetCompleteMedicationHistoryCompletedEventHandler a, GetCompleteMedicationHistoryCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetCompleteMedicationHistoryCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetCompleteMedicationHistoryCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetCompleteMedicationHistoryCompletedEventHandler ret = new __MultiGetCompleteMedicationHistoryCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetCompleteMedicationHistoryCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


