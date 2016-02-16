//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.customerUpdates;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.customerUpdates.__MultiValidateResellerCredentialsCompletedEventHandler;
import OpenDental.customerUpdates.ValidateResellerCredentialsCompletedEventArgs;
import OpenDental.customerUpdates.ValidateResellerCredentialsCompletedEventHandler;

/**
* 
*/
public class __MultiValidateResellerCredentialsCompletedEventHandler   implements ValidateResellerCredentialsCompletedEventHandler
{
    public void invoke(Object sender, ValidateResellerCredentialsCompletedEventArgs e) throws Exception {
        IList<ValidateResellerCredentialsCompletedEventHandler> copy = new IList<ValidateResellerCredentialsCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<ValidateResellerCredentialsCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            ValidateResellerCredentialsCompletedEventHandler d = (ValidateResellerCredentialsCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<ValidateResellerCredentialsCompletedEventHandler> _invocationList = new ArrayList<ValidateResellerCredentialsCompletedEventHandler>();
    public static ValidateResellerCredentialsCompletedEventHandler combine(ValidateResellerCredentialsCompletedEventHandler a, ValidateResellerCredentialsCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiValidateResellerCredentialsCompletedEventHandler ret = new __MultiValidateResellerCredentialsCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static ValidateResellerCredentialsCompletedEventHandler remove(ValidateResellerCredentialsCompletedEventHandler a, ValidateResellerCredentialsCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<ValidateResellerCredentialsCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<ValidateResellerCredentialsCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiValidateResellerCredentialsCompletedEventHandler ret = new __MultiValidateResellerCredentialsCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<ValidateResellerCredentialsCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


