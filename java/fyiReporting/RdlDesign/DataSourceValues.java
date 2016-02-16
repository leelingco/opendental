//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:18 PM
//

package fyiReporting.RdlDesign;


public class DataSourceValues   
{
    String _Name = new String();
    boolean _bDataSourceReference = new boolean();
    String _DataSourceReference = new String();
    String _DataProvider = new String();
    String _ConnectionString = new String();
    boolean _IntegratedSecurity = new boolean();
    String _Prompt = new String();
    XmlNode _Node = new XmlNode();
    public DataSourceValues(String name) throws Exception {
        _Name = name;
    }

    public String getName() throws Exception {
        return _Name;
    }

    public void setName(String value) throws Exception {
        _Name = value;
    }

    public boolean getbDataSourceReference() throws Exception {
        return _bDataSourceReference;
    }

    public void setbDataSourceReference(boolean value) throws Exception {
        _bDataSourceReference = value;
    }

    public String getDataSourceReference() throws Exception {
        return _DataSourceReference;
    }

    public void setDataSourceReference(String value) throws Exception {
        _DataSourceReference = value;
    }

    public String getDataProvider() throws Exception {
        return _DataProvider;
    }

    public void setDataProvider(String value) throws Exception {
        _DataProvider = value;
    }

    public String getConnectionString() throws Exception {
        return _ConnectionString;
    }

    public void setConnectionString(String value) throws Exception {
        _ConnectionString = value;
    }

    public boolean getIntegratedSecurity() throws Exception {
        return _IntegratedSecurity;
    }

    public void setIntegratedSecurity(boolean value) throws Exception {
        _IntegratedSecurity = value;
    }

    public String getPrompt() throws Exception {
        return _Prompt;
    }

    public void setPrompt(String value) throws Exception {
        _Prompt = value;
    }

    public XmlNode getNode() throws Exception {
        return _Node;
    }

    public void setNode(XmlNode value) throws Exception {
        _Node = value;
    }

    public String toString() {
        try
        {
            return _Name;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


