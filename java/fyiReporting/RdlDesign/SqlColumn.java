//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:16 PM
//

package fyiReporting.RdlDesign;


public class SqlColumn   
{
    String _Name = new String();
    Type _DataType = new Type();
    public String toString() {
        try
        {
            return _Name;
        }
        catch (RuntimeException __dummyCatchVar4)
        {
            throw __dummyCatchVar4;
        }
        catch (Exception __dummyCatchVar4)
        {
            throw new RuntimeException(__dummyCatchVar4);
        }
    
    }

    public String getName() throws Exception {
        return _Name;
    }

    public void setName(String value) throws Exception {
        _Name = value;
    }

    public Type getDataType() throws Exception {
        return _DataType;
    }

    public void setDataType(Type value) throws Exception {
        _DataType = value;
    }

}


