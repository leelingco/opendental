//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;


// we don't have paging turned on for html
public class CssCacheEntry2   
{
    String _Css = new String();
    // css
    String _Name = new String();
    // name of entry
    public CssCacheEntry2(String css, String name) throws Exception {
        _Css = css;
        _Name = name;
    }

    public String getCss() throws Exception {
        return _Css;
    }

    public void setCss(String value) throws Exception {
        _Css = value;
    }

    public String getName() throws Exception {
        return _Name;
    }

    public void setName(String value) throws Exception {
        _Name = value;
    }

}


