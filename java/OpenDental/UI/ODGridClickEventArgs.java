//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* 
*/
public class ODGridClickEventArgs   
{
    private int col = new int();
    private int row = new int();
    private MouseButtons button = new MouseButtons();
    /**
    * 
    */
    public ODGridClickEventArgs(int col, int row, MouseButtons button) throws Exception {
        this.col = col;
        this.row = row;
        this.button = button;
    }

    /**
    * 
    */
    public int getRow() throws Exception {
        return row;
    }

    /**
    * 
    */
    public int getCol() throws Exception {
        return col;
    }

    /**
    * Gets which mouse button was pressed.
    */
    public MouseButtons getButton() throws Exception {
        return button;
    }

}


