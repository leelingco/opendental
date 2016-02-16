//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:16 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HeightEventArgs  extends EventArgs 
{
    String _height = new String();
    // current height
    public HeightEventArgs(String height) throws Exception {
        super();
        _height = height;
    }

    public String getHeight() throws Exception {
        return _height;
    }

}


