//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.__MultiFeatureRequestDiscussGetListCompletedEventHandler;
import OpenDental.localhost.FeatureRequestDiscussGetListCompletedEventArgs;
import OpenDental.localhost.FeatureRequestDiscussGetListCompletedEventHandler;

/**
* 
*/
public class __MultiFeatureRequestDiscussGetListCompletedEventHandler   implements FeatureRequestDiscussGetListCompletedEventHandler
{
    public void invoke(Object sender, FeatureRequestDiscussGetListCompletedEventArgs e) throws Exception {
        IList<FeatureRequestDiscussGetListCompletedEventHandler> copy = new IList<FeatureRequestDiscussGetListCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<FeatureRequestDiscussGetListCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            FeatureRequestDiscussGetListCompletedEventHandler d = (FeatureRequestDiscussGetListCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<FeatureRequestDiscussGetListCompletedEventHandler> _invocationList = new ArrayList<FeatureRequestDiscussGetListCompletedEventHandler>();
    public static FeatureRequestDiscussGetListCompletedEventHandler combine(FeatureRequestDiscussGetListCompletedEventHandler a, FeatureRequestDiscussGetListCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiFeatureRequestDiscussGetListCompletedEventHandler ret = new __MultiFeatureRequestDiscussGetListCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static FeatureRequestDiscussGetListCompletedEventHandler remove(FeatureRequestDiscussGetListCompletedEventHandler a, FeatureRequestDiscussGetListCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<FeatureRequestDiscussGetListCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<FeatureRequestDiscussGetListCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiFeatureRequestDiscussGetListCompletedEventHandler ret = new __MultiFeatureRequestDiscussGetListCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<FeatureRequestDiscussGetListCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


