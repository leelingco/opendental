//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.customerUpdates;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.customerUpdates.__MultiValidatePatientPortalOfficeCompletedEventHandler;
import OpenDental.customerUpdates.ValidatePatientPortalOfficeCompletedEventArgs;
import OpenDental.customerUpdates.ValidatePatientPortalOfficeCompletedEventHandler;

/**
* 
*/
public class __MultiValidatePatientPortalOfficeCompletedEventHandler   implements ValidatePatientPortalOfficeCompletedEventHandler
{
    public void invoke(Object sender, ValidatePatientPortalOfficeCompletedEventArgs e) throws Exception {
        IList<ValidatePatientPortalOfficeCompletedEventHandler> copy = new IList<ValidatePatientPortalOfficeCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<ValidatePatientPortalOfficeCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            ValidatePatientPortalOfficeCompletedEventHandler d = (ValidatePatientPortalOfficeCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<ValidatePatientPortalOfficeCompletedEventHandler> _invocationList = new ArrayList<ValidatePatientPortalOfficeCompletedEventHandler>();
    public static ValidatePatientPortalOfficeCompletedEventHandler combine(ValidatePatientPortalOfficeCompletedEventHandler a, ValidatePatientPortalOfficeCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiValidatePatientPortalOfficeCompletedEventHandler ret = new __MultiValidatePatientPortalOfficeCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static ValidatePatientPortalOfficeCompletedEventHandler remove(ValidatePatientPortalOfficeCompletedEventHandler a, ValidatePatientPortalOfficeCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<ValidatePatientPortalOfficeCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<ValidatePatientPortalOfficeCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiValidatePatientPortalOfficeCompletedEventHandler ret = new __MultiValidatePatientPortalOfficeCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<ValidatePatientPortalOfficeCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


