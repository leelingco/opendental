//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGetPBMEligibilityV3CompletedEventHandler;
import OpenDental.NewCrop.GetPBMEligibilityV3CompletedEventArgs;
import OpenDental.NewCrop.GetPBMEligibilityV3CompletedEventHandler;

/**
* 
*/
public class __MultiGetPBMEligibilityV3CompletedEventHandler   implements GetPBMEligibilityV3CompletedEventHandler
{
    public void invoke(Object sender, GetPBMEligibilityV3CompletedEventArgs e) throws Exception {
        IList<GetPBMEligibilityV3CompletedEventHandler> copy = new IList<GetPBMEligibilityV3CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPBMEligibilityV3CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPBMEligibilityV3CompletedEventHandler d = (GetPBMEligibilityV3CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPBMEligibilityV3CompletedEventHandler> _invocationList = new ArrayList<GetPBMEligibilityV3CompletedEventHandler>();
    public static GetPBMEligibilityV3CompletedEventHandler combine(GetPBMEligibilityV3CompletedEventHandler a, GetPBMEligibilityV3CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPBMEligibilityV3CompletedEventHandler ret = new __MultiGetPBMEligibilityV3CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPBMEligibilityV3CompletedEventHandler remove(GetPBMEligibilityV3CompletedEventHandler a, GetPBMEligibilityV3CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPBMEligibilityV3CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPBMEligibilityV3CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPBMEligibilityV3CompletedEventHandler ret = new __MultiGetPBMEligibilityV3CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPBMEligibilityV3CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


