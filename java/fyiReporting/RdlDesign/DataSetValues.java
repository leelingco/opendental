//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:16 PM
//

package fyiReporting.RdlDesign;


public class DataSetValues   
{
    String _Name = new String();
    String _DataSourceName = new String();
    String _CommandText = new String();
    int _Timeout = new int();
    DataTable _QueryParameters = new DataTable();
    // of type DSQueryParameter
    DataTable _Fields = new DataTable();
    XmlNode _Node = new XmlNode();
    public DataSetValues(String name) throws Exception {
        _Name = name;
    }

    public String getName() throws Exception {
        return _Name;
    }

    public void setName(String value) throws Exception {
        _Name = value;
    }

    public String getDataSourceName() throws Exception {
        return _DataSourceName;
    }

    public void setDataSourceName(String value) throws Exception {
        _DataSourceName = value;
    }

    public String getCommandText() throws Exception {
        return _CommandText;
    }

    public void setCommandText(String value) throws Exception {
        _CommandText = value;
    }

    public int getTimeout() throws Exception {
        return _Timeout;
    }

    public void setTimeout(int value) throws Exception {
        _Timeout = value;
    }

    public DataTable getQueryParameters() throws Exception {
        return _QueryParameters;
    }

    public void setQueryParameters(DataTable value) throws Exception {
        _QueryParameters = value;
    }

    public XmlNode getNode() throws Exception {
        return _Node;
    }

    public void setNode(XmlNode value) throws Exception {
        _Node = value;
    }

    public DataTable getFields() throws Exception {
        return _Fields;
    }

    public void setFields(DataTable value) throws Exception {
        _Fields = value;
    }

    public String toString() {
        try
        {
            return _Name;
        }
        catch (RuntimeException __dummyCatchVar1)
        {
            throw __dummyCatchVar1;
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new RuntimeException(__dummyCatchVar1);
        }
    
    }

}


