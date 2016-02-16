//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RDL;


public class SqlConfigEntry   
{
    public String Provider = new String();
    public Assembly CodeModule = new Assembly();
    public String ClassName = new String();
    public String TableSelect = new String();
    public String ErrorMsg = new String();
    public boolean ReplaceParameters = new boolean();
    public SqlConfigEntry(String provider, String cname, Assembly codemodule, String tselect, String msg) throws Exception {
        Provider = provider;
        CodeModule = codemodule;
        ClassName = cname;
        TableSelect = tselect;
        ErrorMsg = msg;
        ReplaceParameters = false;
    }

}


