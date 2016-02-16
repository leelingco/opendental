//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiGenerateTestRenewalRequestCompletedEventHandler;
import OpenDental.NewCrop.GenerateTestRenewalRequestCompletedEventArgs;
import OpenDental.NewCrop.GenerateTestRenewalRequestCompletedEventHandler;

/**
* 
*/
public class __MultiGenerateTestRenewalRequestCompletedEventHandler   implements GenerateTestRenewalRequestCompletedEventHandler
{
    public void invoke(Object sender, GenerateTestRenewalRequestCompletedEventArgs e) throws Exception {
        IList<GenerateTestRenewalRequestCompletedEventHandler> copy = new IList<GenerateTestRenewalRequestCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<GenerateTestRenewalRequestCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            GenerateTestRenewalRequestCompletedEventHandler d = (GenerateTestRenewalRequestCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<GenerateTestRenewalRequestCompletedEventHandler> _invocationList = new ArrayList<GenerateTestRenewalRequestCompletedEventHandler>();
    public static GenerateTestRenewalRequestCompletedEventHandler combine(GenerateTestRenewalRequestCompletedEventHandler a, GenerateTestRenewalRequestCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiGenerateTestRenewalRequestCompletedEventHandler ret = new __MultiGenerateTestRenewalRequestCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static GenerateTestRenewalRequestCompletedEventHandler remove(GenerateTestRenewalRequestCompletedEventHandler a, GenerateTestRenewalRequestCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<GenerateTestRenewalRequestCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<GenerateTestRenewalRequestCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiGenerateTestRenewalRequestCompletedEventHandler ret = new __MultiGenerateTestRenewalRequestCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<GenerateTestRenewalRequestCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


