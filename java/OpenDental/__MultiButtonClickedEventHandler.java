//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.__MultiButtonClickedEventHandler;
import OpenDental.ButtonClickedEventHandler;

/**
* 
*/
public class __MultiButtonClickedEventHandler   implements ButtonClickedEventHandler
{
    public void invoke(Object sender, OpenDental.ButtonClicked_EventArgs e) throws Exception {
        IList<ButtonClickedEventHandler> copy = new IList<ButtonClickedEventHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<ButtonClickedEventHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            ButtonClickedEventHandler d = (ButtonClickedEventHandler)__dummyForeachVar0;
            d.invoke(sender, e);
        }
    }

    private System.Collections.Generic.IList<ButtonClickedEventHandler> _invocationList = new ArrayList<ButtonClickedEventHandler>();
    public static ButtonClickedEventHandler combine(ButtonClickedEventHandler a, ButtonClickedEventHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        /**
        * 
        */
        __MultiButtonClickedEventHandler ret = new __MultiButtonClickedEventHandler();
        /**
        * Required designer variable.
        */
        ret._invocationList = a.getInvocationList();
        /**
        * 
        */
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    /**
    * 
    */
    public static ButtonClickedEventHandler remove(ButtonClickedEventHandler a, ButtonClickedEventHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        /**
        * 
        */
        System.Collections.Generic.IList<ButtonClickedEventHandler> aInvList = a.getInvocationList();
        /**
        * Used when click event is cancelled.
        */
        // This call is required by the Windows.Forms Form Designer.
        System.Collections.Generic.IList<ButtonClickedEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiButtonClickedEventHandler ret = new __MultiButtonClickedEventHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public System.Collections.Generic.IList<ButtonClickedEventHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


