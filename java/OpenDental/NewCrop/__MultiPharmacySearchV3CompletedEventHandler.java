//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiPharmacySearchV3CompletedEventHandler;
import OpenDental.NewCrop.PharmacySearchV3CompletedEventArgs;
import OpenDental.NewCrop.PharmacySearchV3CompletedEventHandler;

/**
* 
*/
public class __MultiPharmacySearchV3CompletedEventHandler   implements PharmacySearchV3CompletedEventHandler
{
    public void invoke(Object sender, PharmacySearchV3CompletedEventArgs e) throws Exception {
        IList<PharmacySearchV3CompletedEventHandler> copy = new IList<PharmacySearchV3CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<PharmacySearchV3CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            PharmacySearchV3CompletedEventHandler d = (PharmacySearchV3CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<PharmacySearchV3CompletedEventHandler> _invocationList = new ArrayList<PharmacySearchV3CompletedEventHandler>();
    public static PharmacySearchV3CompletedEventHandler combine(PharmacySearchV3CompletedEventHandler a, PharmacySearchV3CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiPharmacySearchV3CompletedEventHandler ret = new __MultiPharmacySearchV3CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static PharmacySearchV3CompletedEventHandler remove(PharmacySearchV3CompletedEventHandler a, PharmacySearchV3CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<PharmacySearchV3CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<PharmacySearchV3CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiPharmacySearchV3CompletedEventHandler ret = new __MultiPharmacySearchV3CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<PharmacySearchV3CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


