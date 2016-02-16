//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RDL;


public class CustomReportItemEntry   
{
    public String Type = new String();
    public Assembly CodeModule = new Assembly();
    public String ClassName = new String();
    public String ErrorMsg = new String();
    public CustomReportItemEntry(String type, String cname, Assembly codemodule, String msg) throws Exception {
        Type = type;
        CodeModule = codemodule;
        ClassName = cname;
        ErrorMsg = msg;
    }

}


