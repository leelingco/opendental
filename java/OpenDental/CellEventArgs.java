//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//if (mouseDownPosition.Y>this.rowPos[i]) myRow=i;
//end class ContrTable
/**
* 
*/
public class CellEventArgs  extends EventArgs 
{
    private int col = new int();
    private int row = new int();
    /**
    * 
    */
    public CellEventArgs(int col, int row) throws Exception {
        super();
        this.row = row;
        this.col = col;
    }

    /**
    * 
    */
    public int getCol() throws Exception {
        return col;
    }

    /**
    * 
    */
    public int getRow() throws Exception {
        return row;
    }

}


