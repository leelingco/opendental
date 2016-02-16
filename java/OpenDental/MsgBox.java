//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBoxButtons;

/**
* This is a more efficient version of the MS MessageBox.
*/
public class MsgBox   
{
    /**
    * This is a more efficient version of the MS MessageBox. It also automates the language translation. Do NOT use if the text is variable in any way, because it will mess up the translation feature.
    */
    public static void show(System.Object sender, String text) throws Exception {
        MessageBox.Show(Lan.g(sender.GetType().Name,text));
    }

    /**
    * This is a more efficient version of the MS MessageBox. It also automates the language translation. Returns true if result is OK or Yes.
    */
    public static boolean show(System.Object sender, MsgBoxButtons buttons, String question) throws Exception {
        if (buttons == MsgBoxButtons.OKCancel)
        {
            if (MessageBox.Show(Lan.g(sender.GetType().Name,question), "", MessageBoxButtons.OKCancel) == DialogResult.OK)
            {
                return true;
            }
            else
            {
                return false;
            } 
        }
        else if (buttons == MsgBoxButtons.YesNo)
        {
            if (MessageBox.Show(Lan.g(sender.GetType().Name,question), "", MessageBoxButtons.YesNo) == DialogResult.Yes)
            {
                return true;
            }
            else
            {
                return false;
            } 
        }
          
        return false;
    }

    /**
    * deprecated
    */
    public static boolean show(System.Object sender, boolean okCancel, String question) throws Exception {
        return show(sender,MsgBoxButtons.OKCancel,question);
    }

}


