//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.customerUpdates;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.customerUpdates.__MultiFeatureRequestDiscussSubmitCompletedEventHandler;
import OpenDental.customerUpdates.FeatureRequestDiscussSubmitCompletedEventArgs;
import OpenDental.customerUpdates.FeatureRequestDiscussSubmitCompletedEventHandler;

/**
* 
*/
public class __MultiFeatureRequestDiscussSubmitCompletedEventHandler   implements FeatureRequestDiscussSubmitCompletedEventHandler
{
    public void invoke(Object sender, FeatureRequestDiscussSubmitCompletedEventArgs e) throws Exception {
        IList<FeatureRequestDiscussSubmitCompletedEventHandler> copy = new IList<FeatureRequestDiscussSubmitCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<FeatureRequestDiscussSubmitCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            FeatureRequestDiscussSubmitCompletedEventHandler d = (FeatureRequestDiscussSubmitCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<FeatureRequestDiscussSubmitCompletedEventHandler> _invocationList = new ArrayList<FeatureRequestDiscussSubmitCompletedEventHandler>();
    public static FeatureRequestDiscussSubmitCompletedEventHandler combine(FeatureRequestDiscussSubmitCompletedEventHandler a, FeatureRequestDiscussSubmitCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiFeatureRequestDiscussSubmitCompletedEventHandler ret = new __MultiFeatureRequestDiscussSubmitCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static FeatureRequestDiscussSubmitCompletedEventHandler remove(FeatureRequestDiscussSubmitCompletedEventHandler a, FeatureRequestDiscussSubmitCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<FeatureRequestDiscussSubmitCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<FeatureRequestDiscussSubmitCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiFeatureRequestDiscussSubmitCompletedEventHandler ret = new __MultiFeatureRequestDiscussSubmitCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<FeatureRequestDiscussSubmitCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


