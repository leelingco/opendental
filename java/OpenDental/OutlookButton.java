//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* 
*/
public class OutlookButton   
{
    public OutlookButton() {
    }

    //this should be a class if I was going to ever reuse this control
    //private string caption;
    //private int imageIndex;
    /**
    * 
    */
    public OutlookButton(String caption, int imageIndex) throws Exception {
        Caption = caption;
        ImageIndex = imageIndex;
        Bounds = new Rectangle(0, 0, 0, 0);
        Visible = true;
    }

    /**
    * 
    */
    public String Caption = new String();
    /**
    * 
    */
    public int ImageIndex = new int();
    /**
    * 
    */
    public Rectangle Bounds = new Rectangle();
    /**
    * 
    */
    public boolean Visible = new boolean();
}


