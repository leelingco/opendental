//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiPharmacySearchV4CompletedEventHandler;
import OpenDental.NewCrop.PharmacySearchV4CompletedEventArgs;
import OpenDental.NewCrop.PharmacySearchV4CompletedEventHandler;

/**
* 
*/
public class __MultiPharmacySearchV4CompletedEventHandler   implements PharmacySearchV4CompletedEventHandler
{
    public void invoke(Object sender, PharmacySearchV4CompletedEventArgs e) throws Exception {
        IList<PharmacySearchV4CompletedEventHandler> copy = new IList<PharmacySearchV4CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<PharmacySearchV4CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            PharmacySearchV4CompletedEventHandler d = (PharmacySearchV4CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<PharmacySearchV4CompletedEventHandler> _invocationList = new ArrayList<PharmacySearchV4CompletedEventHandler>();
    public static PharmacySearchV4CompletedEventHandler combine(PharmacySearchV4CompletedEventHandler a, PharmacySearchV4CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiPharmacySearchV4CompletedEventHandler ret = new __MultiPharmacySearchV4CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static PharmacySearchV4CompletedEventHandler remove(PharmacySearchV4CompletedEventHandler a, PharmacySearchV4CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<PharmacySearchV4CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<PharmacySearchV4CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiPharmacySearchV4CompletedEventHandler ret = new __MultiPharmacySearchV4CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<PharmacySearchV4CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


