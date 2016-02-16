//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPBMDrugHistoryV2CompletedEventHandler;
import OpenDental.NewCrop.GetPBMDrugHistoryV2CompletedEventArgs;
import OpenDental.NewCrop.GetPBMDrugHistoryV2CompletedEventHandler;

/**
* 
*/
public class __MultiGetPBMDrugHistoryV2CompletedEventHandler   implements GetPBMDrugHistoryV2CompletedEventHandler
{
    public void invoke(Object sender, GetPBMDrugHistoryV2CompletedEventArgs e) throws Exception {
        IList<GetPBMDrugHistoryV2CompletedEventHandler> copy = new IList<GetPBMDrugHistoryV2CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPBMDrugHistoryV2CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPBMDrugHistoryV2CompletedEventHandler d = (GetPBMDrugHistoryV2CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPBMDrugHistoryV2CompletedEventHandler> _invocationList = new ArrayList<GetPBMDrugHistoryV2CompletedEventHandler>();
    public static GetPBMDrugHistoryV2CompletedEventHandler combine(GetPBMDrugHistoryV2CompletedEventHandler a, GetPBMDrugHistoryV2CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPBMDrugHistoryV2CompletedEventHandler ret = new __MultiGetPBMDrugHistoryV2CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPBMDrugHistoryV2CompletedEventHandler remove(GetPBMDrugHistoryV2CompletedEventHandler a, GetPBMDrugHistoryV2CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPBMDrugHistoryV2CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPBMDrugHistoryV2CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPBMDrugHistoryV2CompletedEventHandler ret = new __MultiGetPBMDrugHistoryV2CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPBMDrugHistoryV2CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


