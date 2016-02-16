//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:19 PM
//

package fyiReporting.RdlDesign;


public class EmbeddedImageValues   
{
    String _Name = new String();
    String _ImageData = new String();
    // the embedded image value
    String _MIMEType = new String();
    public EmbeddedImageValues(String name) throws Exception {
        _Name = name;
    }

    public String getName() throws Exception {
        return _Name;
    }

    public void setName(String value) throws Exception {
        _Name = value;
    }

    public String getImageData() throws Exception {
        return _ImageData;
    }

    public void setImageData(String value) throws Exception {
        _ImageData = value;
    }

    public String getMIMEType() throws Exception {
        return _MIMEType;
    }

    public void setMIMEType(String value) throws Exception {
        _MIMEType = value;
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


