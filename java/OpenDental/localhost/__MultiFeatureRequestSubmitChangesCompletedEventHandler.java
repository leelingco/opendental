//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.__MultiFeatureRequestSubmitChangesCompletedEventHandler;
import OpenDental.localhost.FeatureRequestSubmitChangesCompletedEventArgs;
import OpenDental.localhost.FeatureRequestSubmitChangesCompletedEventHandler;

/**
* 
*/
public class __MultiFeatureRequestSubmitChangesCompletedEventHandler   implements FeatureRequestSubmitChangesCompletedEventHandler
{
    public void invoke(Object sender, FeatureRequestSubmitChangesCompletedEventArgs e) throws Exception {
        IList<FeatureRequestSubmitChangesCompletedEventHandler> copy = new IList<FeatureRequestSubmitChangesCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<FeatureRequestSubmitChangesCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            FeatureRequestSubmitChangesCompletedEventHandler d = (FeatureRequestSubmitChangesCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<FeatureRequestSubmitChangesCompletedEventHandler> _invocationList = new ArrayList<FeatureRequestSubmitChangesCompletedEventHandler>();
    public static FeatureRequestSubmitChangesCompletedEventHandler combine(FeatureRequestSubmitChangesCompletedEventHandler a, FeatureRequestSubmitChangesCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiFeatureRequestSubmitChangesCompletedEventHandler ret = new __MultiFeatureRequestSubmitChangesCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static FeatureRequestSubmitChangesCompletedEventHandler remove(FeatureRequestSubmitChangesCompletedEventHandler a, FeatureRequestSubmitChangesCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<FeatureRequestSubmitChangesCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<FeatureRequestSubmitChangesCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiFeatureRequestSubmitChangesCompletedEventHandler ret = new __MultiFeatureRequestSubmitChangesCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<FeatureRequestSubmitChangesCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


