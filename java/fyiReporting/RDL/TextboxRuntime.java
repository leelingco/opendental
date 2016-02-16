//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Page;
import fyiReporting.RDL.Textbox;
import fyiReporting.RDL.TextboxRuntime;

public class TextboxRuntime   
{
    public int RunCount = 0;
    // number of times TextBox is rendered at runtime;
    //    used to generate unique names for toggling visibility
    public float RunHeight = 0;
    // the runtime height (in points)
    public String PreviousText = null;
    // previous text displayed
    public Page PreviousPage = null;
    //  page previous text was shown on
    public Object LastObject = null;
    // last object calculated
    static public TextboxRuntime getTextboxRuntime(fyiReporting.RDL.Report rpt, Textbox tb) throws Exception {
        TextboxRuntime tbr = rpt.getCache().get(tb,"txtbox") instanceof TextboxRuntime ? (TextboxRuntime)rpt.getCache().get(tb,"txtbox") : (TextboxRuntime)null;
        if (tbr != null)
            return tbr;
         
        tbr = new TextboxRuntime();
        rpt.getCache().add(tb,"txtbox",tbr);
        return tbr;
    }

}


