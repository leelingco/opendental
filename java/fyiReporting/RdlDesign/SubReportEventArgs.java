//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:16 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SubReportEventArgs  extends EventArgs 
{
    String _name = new String();
    // name of subreport user has requested be opened
    public SubReportEventArgs(String name) throws Exception {
        super();
        _name = name;
    }

    public String getSubReportName() throws Exception {
        return _name;
    }

}


