//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

/**
* 
*/
public class __MultiODGridClickEventHandler   implements OpenDental.UI.ODGridClickEventHandler
{
    public void invoke(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        IList<OpenDental.UI.ODGridClickEventHandler> copy = new IList<OpenDental.UI.ODGridClickEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<OpenDental.UI.ODGridClickEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            OpenDental.UI.ODGridClickEventHandler d = (OpenDental.UI.ODGridClickEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<OpenDental.UI.ODGridClickEventHandler> _invocationList = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
    public static OpenDental.UI.ODGridClickEventHandler combine(OpenDental.UI.ODGridClickEventHandler a, OpenDental.UI.ODGridClickEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiODGridClickEventHandler ret = new __MultiODGridClickEventHandler();
        ret._invocationList = a.getInvocationList();
        /**
        * A new and improved grid control to replace the inherited ContrTable that is used so extensively in the program.
        */
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    /**
    * Required designer variable.
    */
    public static OpenDental.UI.ODGridClickEventHandler remove(OpenDental.UI.ODGridClickEventHandler a, OpenDental.UI.ODGridClickEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        /**
        * 
        */
        System.Collections.Generic.IList<OpenDental.UI.ODGridClickEventHandler> aInvList = a.getInvocationList();
        /**
        * 
        */
        System.Collections.Generic.IList<OpenDental.UI.ODGridClickEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            /**
            * 
            */
            __MultiODGridClickEventHandler ret = new __MultiODGridClickEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    //private Font titleFont=new Font(FontFamily.GenericSansSerif,10,FontStyle.Bold);
    //private Font headerFont=new Font(FontFamily.GenericSansSerif,8.5f,FontStyle.Bold);
    //private Font cellFont=new Font(FontFamily.GenericSansSerif,8.5f);
    public System.Collections.Generic.IList<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


