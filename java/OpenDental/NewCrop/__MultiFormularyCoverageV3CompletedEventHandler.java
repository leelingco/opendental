//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.__MultiFormularyCoverageV3CompletedEventHandler;
import OpenDental.NewCrop.FormularyCoverageV3CompletedEventArgs;
import OpenDental.NewCrop.FormularyCoverageV3CompletedEventHandler;

/**
* 
*/
public class __MultiFormularyCoverageV3CompletedEventHandler   implements FormularyCoverageV3CompletedEventHandler
{
    public void invoke(Object sender, FormularyCoverageV3CompletedEventArgs e) throws Exception {
        IList<FormularyCoverageV3CompletedEventHandler> copy = new IList<FormularyCoverageV3CompletedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<FormularyCoverageV3CompletedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            FormularyCoverageV3CompletedEventHandler d = (FormularyCoverageV3CompletedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<FormularyCoverageV3CompletedEventHandler> _invocationList = new ArrayList<FormularyCoverageV3CompletedEventHandler>();
    public static FormularyCoverageV3CompletedEventHandler combine(FormularyCoverageV3CompletedEventHandler a, FormularyCoverageV3CompletedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiFormularyCoverageV3CompletedEventHandler ret = new __MultiFormularyCoverageV3CompletedEventHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static FormularyCoverageV3CompletedEventHandler remove(FormularyCoverageV3CompletedEventHandler a, FormularyCoverageV3CompletedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<FormularyCoverageV3CompletedEventHandler> aInvList = a.getInvocationList();
        System.Collections.Generic.IList<FormularyCoverageV3CompletedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiFormularyCoverageV3CompletedEventHandler ret = new __MultiFormularyCoverageV3CompletedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<FormularyCoverageV3CompletedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


