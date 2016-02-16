//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPatientAllergyHistoryV3CompletedEventHandler;
import OpenDental.NewCrop.GetPatientAllergyHistoryV3CompletedEventArgs;
import OpenDental.NewCrop.GetPatientAllergyHistoryV3CompletedEventHandler;

/**
* 
*/
public class __MultiGetPatientAllergyHistoryV3CompletedEventHandler   implements GetPatientAllergyHistoryV3CompletedEventHandler
{
    public void invoke(Object sender, GetPatientAllergyHistoryV3CompletedEventArgs e) throws Exception {
        IList<GetPatientAllergyHistoryV3CompletedEventHandler> copy = new IList<GetPatientAllergyHistoryV3CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPatientAllergyHistoryV3CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPatientAllergyHistoryV3CompletedEventHandler d = (GetPatientAllergyHistoryV3CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPatientAllergyHistoryV3CompletedEventHandler> _invocationList = new ArrayList<GetPatientAllergyHistoryV3CompletedEventHandler>();
    public static GetPatientAllergyHistoryV3CompletedEventHandler combine(GetPatientAllergyHistoryV3CompletedEventHandler a, GetPatientAllergyHistoryV3CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPatientAllergyHistoryV3CompletedEventHandler ret = new __MultiGetPatientAllergyHistoryV3CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPatientAllergyHistoryV3CompletedEventHandler remove(GetPatientAllergyHistoryV3CompletedEventHandler a, GetPatientAllergyHistoryV3CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPatientAllergyHistoryV3CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPatientAllergyHistoryV3CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPatientAllergyHistoryV3CompletedEventHandler ret = new __MultiGetPatientAllergyHistoryV3CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPatientAllergyHistoryV3CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


