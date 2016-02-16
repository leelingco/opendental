//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.MobileWeb;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.MobileWeb.__MultiGetPatientPortalAddressCompletedEventHandler;
import OpenDental.MobileWeb.GetPatientPortalAddressCompletedEventArgs;
import OpenDental.MobileWeb.GetPatientPortalAddressCompletedEventHandler;

/**
* 
*/
public class __MultiGetPatientPortalAddressCompletedEventHandler   implements GetPatientPortalAddressCompletedEventHandler
{
    public void invoke(Object sender, GetPatientPortalAddressCompletedEventArgs e) throws Exception {
        IList<GetPatientPortalAddressCompletedEventHandler> copy = new IList<GetPatientPortalAddressCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GetPatientPortalAddressCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GetPatientPortalAddressCompletedEventHandler d = (GetPatientPortalAddressCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GetPatientPortalAddressCompletedEventHandler> _invocationList = new ArrayList<GetPatientPortalAddressCompletedEventHandler>();
    public static GetPatientPortalAddressCompletedEventHandler combine(GetPatientPortalAddressCompletedEventHandler a, GetPatientPortalAddressCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGetPatientPortalAddressCompletedEventHandler ret = new __MultiGetPatientPortalAddressCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GetPatientPortalAddressCompletedEventHandler remove(GetPatientPortalAddressCompletedEventHandler a, GetPatientPortalAddressCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GetPatientPortalAddressCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GetPatientPortalAddressCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGetPatientPortalAddressCompletedEventHandler ret = new __MultiGetPatientPortalAddressCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GetPatientPortalAddressCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


