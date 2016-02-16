//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.User_Controls;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* 
*/
public class KeyboardClickEventArgs   
{
    private String txt = new String();
    private Keys keyData = new Keys();
    public KeyboardClickEventArgs(String myTxt, Keys myKeyData) throws Exception {
        txt = myTxt;
        keyData = myKeyData;
    }

    /**
    * 
    */
    public String getTxt() throws Exception {
        return txt;
    }

    /**
    * 
    */
    public Keys getKeyData() throws Exception {
        return keyData;
    }

}


