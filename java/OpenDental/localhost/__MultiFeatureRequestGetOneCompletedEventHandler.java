//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.__MultiFeatureRequestGetOneCompletedEventHandler;
import OpenDental.localhost.FeatureRequestGetOneCompletedEventArgs;
import OpenDental.localhost.FeatureRequestGetOneCompletedEventHandler;

/**
* 
*/
public class __MultiFeatureRequestGetOneCompletedEventHandler   implements FeatureRequestGetOneCompletedEventHandler
{
    public void invoke(Object sender, FeatureRequestGetOneCompletedEventArgs e) throws Exception {
        IList<FeatureRequestGetOneCompletedEventHandler> copy = new IList<FeatureRequestGetOneCompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<FeatureRequestGetOneCompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            FeatureRequestGetOneCompletedEventHandler d = (FeatureRequestGetOneCompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<FeatureRequestGetOneCompletedEventHandler> _invocationList = new ArrayList<FeatureRequestGetOneCompletedEventHandler>();
    public static FeatureRequestGetOneCompletedEventHandler combine(FeatureRequestGetOneCompletedEventHandler a, FeatureRequestGetOneCompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiFeatureRequestGetOneCompletedEventHandler ret = new __MultiFeatureRequestGetOneCompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static FeatureRequestGetOneCompletedEventHandler remove(FeatureRequestGetOneCompletedEventHandler a, FeatureRequestGetOneCompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<FeatureRequestGetOneCompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<FeatureRequestGetOneCompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiFeatureRequestGetOneCompletedEventHandler ret = new __MultiFeatureRequestGetOneCompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<FeatureRequestGetOneCompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


