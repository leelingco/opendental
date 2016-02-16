//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;

/**
* 
*/
public class __MultiODToolBarButtonClickEventHandler   implements OpenDental.UI.ODToolBarButtonClickEventHandler
{
    public void invoke(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        IList<OpenDental.UI.ODToolBarButtonClickEventHandler> copy = new IList<OpenDental.UI.ODToolBarButtonClickEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<OpenDental.UI.ODToolBarButtonClickEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            OpenDental.UI.ODToolBarButtonClickEventHandler d = (OpenDental.UI.ODToolBarButtonClickEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<OpenDental.UI.ODToolBarButtonClickEventHandler> _invocationList = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
    public static OpenDental.UI.ODToolBarButtonClickEventHandler combine(OpenDental.UI.ODToolBarButtonClickEventHandler a, OpenDental.UI.ODToolBarButtonClickEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        /**
        * Open Dental Toolbar.  This is very custom.
        */
        __MultiODToolBarButtonClickEventHandler ret = new __MultiODToolBarButtonClickEventHandler();
        /**
        * Required designer variable.
        */
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    /**
    * A hot button is either: 1.The button that the mouseDown happened on, regardless of the current position of the mouse, or 2.If the mouse is not down, the button in State.Hover. Keeping track of which one is hot allows faster painting during mouse events.
    */
    public static OpenDental.UI.ODToolBarButtonClickEventHandler remove(OpenDental.UI.ODToolBarButtonClickEventHandler a, OpenDental.UI.ODToolBarButtonClickEventHandler b) throws Exception {
        /**
        * 
        */
        if (a == null || b == null)
            return a;
         
        /**
        * 
        */
        System.Collections.Generic.IList<OpenDental.UI.ODToolBarButtonClickEventHandler> aInvList = a.getInvocationList();
        // This call is required by the Windows.Forms Form Designer.
        /**
        * Clean up any resources being used.
        */
        System.Collections.Generic.IList<OpenDental.UI.ODToolBarButtonClickEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiODToolBarButtonClickEventHandler ret = new __MultiODToolBarButtonClickEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    //
    // ODToolBar
    //
    public System.Collections.Generic.IList<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


