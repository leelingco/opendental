//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPatientFreeFormAllergyHistoryCompletedEventHandler;
import OpenDental.NewCrop.GetPatientFreeFormAllergyHistoryCompletedEventArgs;
import OpenDental.NewCrop.GetPatientFreeFormAllergyHistoryCompletedEventHandler;

/**
* 
*/
public class __MultiGetPatientFreeFormAllergyHistoryCompletedEventHandler   implements GetPatientFreeFormAllergyHistoryCompletedEventHandler
{
    public void invoke(Object sender, GetPatientFreeFormAllergyHistoryCompletedEventArgs e) throws Exception {
        IList<GetPatientFreeFormAllergyHistoryCompletedEventHandler> copy = new IList<GetPatientFreeFormAllergyHistoryCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPatientFreeFormAllergyHistoryCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPatientFreeFormAllergyHistoryCompletedEventHandler d = (GetPatientFreeFormAllergyHistoryCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPatientFreeFormAllergyHistoryCompletedEventHandler> _invocationList = new ArrayList<GetPatientFreeFormAllergyHistoryCompletedEventHandler>();
    public static GetPatientFreeFormAllergyHistoryCompletedEventHandler combine(GetPatientFreeFormAllergyHistoryCompletedEventHandler a, GetPatientFreeFormAllergyHistoryCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPatientFreeFormAllergyHistoryCompletedEventHandler ret = new __MultiGetPatientFreeFormAllergyHistoryCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPatientFreeFormAllergyHistoryCompletedEventHandler remove(GetPatientFreeFormAllergyHistoryCompletedEventHandler a, GetPatientFreeFormAllergyHistoryCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPatientFreeFormAllergyHistoryCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPatientFreeFormAllergyHistoryCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPatientFreeFormAllergyHistoryCompletedEventHandler ret = new __MultiGetPatientFreeFormAllergyHistoryCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPatientFreeFormAllergyHistoryCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


