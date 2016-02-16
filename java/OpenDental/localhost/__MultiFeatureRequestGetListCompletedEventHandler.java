//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.__MultiFeatureRequestGetListCompletedEventHandler;
import OpenDental.localhost.FeatureRequestGetListCompletedEventArgs;
import OpenDental.localhost.FeatureRequestGetListCompletedEventHandler;

/**
* 
*/
public class __MultiFeatureRequestGetListCompletedEventHandler   implements FeatureRequestGetListCompletedEventHandler
{
    public void invoke(Object sender, FeatureRequestGetListCompletedEventArgs e) throws Exception {
        IList<FeatureRequestGetListCompletedEventHandler> copy = new IList<FeatureRequestGetListCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<FeatureRequestGetListCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            FeatureRequestGetListCompletedEventHandler d = (FeatureRequestGetListCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<FeatureRequestGetListCompletedEventHandler> _invocationList = new ArrayList<FeatureRequestGetListCompletedEventHandler>();
    public static FeatureRequestGetListCompletedEventHandler combine(FeatureRequestGetListCompletedEventHandler a, FeatureRequestGetListCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiFeatureRequestGetListCompletedEventHandler ret = new __MultiFeatureRequestGetListCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static FeatureRequestGetListCompletedEventHandler remove(FeatureRequestGetListCompletedEventHandler a, FeatureRequestGetListCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<FeatureRequestGetListCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<FeatureRequestGetListCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiFeatureRequestGetListCompletedEventHandler ret = new __MultiFeatureRequestGetListCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<FeatureRequestGetListCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


