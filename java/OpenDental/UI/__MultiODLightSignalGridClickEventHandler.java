//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.UI.__MultiODLightSignalGridClickEventHandler;

/**
* 
*/
public class __MultiODLightSignalGridClickEventHandler   implements OpenDental.UI.ODLightSignalGridClickEventHandler
{
    public void invoke(Object sender, OpenDental.UI.ODLightSignalGridClickEventArgs e) throws Exception {
        IList<OpenDental.UI.ODLightSignalGridClickEventHandler> copy = new IList<OpenDental.UI.ODLightSignalGridClickEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<OpenDental.UI.ODLightSignalGridClickEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            OpenDental.UI.ODLightSignalGridClickEventHandler d = (OpenDental.UI.ODLightSignalGridClickEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<OpenDental.UI.ODLightSignalGridClickEventHandler> _invocationList = new ArrayList<OpenDental.UI.ODLightSignalGridClickEventHandler>();
    public static OpenDental.UI.ODLightSignalGridClickEventHandler combine(OpenDental.UI.ODLightSignalGridClickEventHandler a, OpenDental.UI.ODLightSignalGridClickEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiODLightSignalGridClickEventHandler ret = new __MultiODLightSignalGridClickEventHandler();
        //private bool IsUpdating;
        //<summary>collection of SignalButtonStates</summary>
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    /**
    * 
    */
    public static OpenDental.UI.ODLightSignalGridClickEventHandler remove(OpenDental.UI.ODLightSignalGridClickEventHandler a, OpenDental.UI.ODLightSignalGridClickEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        System.Collections.Generic.IList<OpenDental.UI.ODLightSignalGridClickEventHandler> aInvList = a.getInvocationList();
        /**
        * 
        */
        //base.OnPaintBackground (pea);
        //don't paint background.  This reduces flickering when using double buffering.
        System.Collections.Generic.IList<OpenDental.UI.ODLightSignalGridClickEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        /**
        * 
        */
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiODLightSignalGridClickEventHandler ret = new __MultiODLightSignalGridClickEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<OpenDental.UI.ODLightSignalGridClickEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


