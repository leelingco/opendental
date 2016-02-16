//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPBMEligibilityV2CompletedEventHandler;
import OpenDental.NewCrop.GetPBMEligibilityV2CompletedEventArgs;
import OpenDental.NewCrop.GetPBMEligibilityV2CompletedEventHandler;

/**
* 
*/
public class __MultiGetPBMEligibilityV2CompletedEventHandler   implements GetPBMEligibilityV2CompletedEventHandler
{
    public void invoke(Object sender, GetPBMEligibilityV2CompletedEventArgs e) throws Exception {
        IList<GetPBMEligibilityV2CompletedEventHandler> copy = new IList<GetPBMEligibilityV2CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPBMEligibilityV2CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPBMEligibilityV2CompletedEventHandler d = (GetPBMEligibilityV2CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPBMEligibilityV2CompletedEventHandler> _invocationList = new ArrayList<GetPBMEligibilityV2CompletedEventHandler>();
    public static GetPBMEligibilityV2CompletedEventHandler combine(GetPBMEligibilityV2CompletedEventHandler a, GetPBMEligibilityV2CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPBMEligibilityV2CompletedEventHandler ret = new __MultiGetPBMEligibilityV2CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPBMEligibilityV2CompletedEventHandler remove(GetPBMEligibilityV2CompletedEventHandler a, GetPBMEligibilityV2CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPBMEligibilityV2CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPBMEligibilityV2CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPBMEligibilityV2CompletedEventHandler ret = new __MultiGetPBMEligibilityV2CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPBMEligibilityV2CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


