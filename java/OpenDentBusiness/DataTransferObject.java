//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Credentials;
import OpenDentBusiness.DataTransferObject;
import OpenDentBusiness.DtoObject;

/**
* Provides a base class for DTO classes.  A DTO class is a simple data storage object.  A DTO is the only format accepted by OpenDentBusiness.dll.
*/
public abstract class DataTransferObject   
{
    /**
    * Always passed with new web service.  Never null.
    */
    public Credentials Credentials;
    /**
    * This is the name of the method that we need to call.  "Class.Method" format.  Not used with GetTableLow.
    */
    public String MethodName = new String();
    /**
    * This is a list of parameters that we are passing.  They can be various kinds of objects.
    */
    public DtoObject[] Params = new DtoObject[]();
    public String serialize() throws Exception {
        StringBuilder strBuild = new StringBuilder();
        XmlWriter writer = XmlWriter.Create(strBuild);
        XmlSerializer serializer = new XmlSerializer(this.GetType());
        serializer.Serialize(writer, this);
        writer.Close();
        return strBuild.ToString();
    }

    public static DataTransferObject deserialize(String data) throws Exception {
        StringReader strReader = new StringReader(data);
        //XmlReader reader=XmlReader.Create(strReader);
        XmlTextReader reader = new XmlTextReader(strReader);
        String strNodeName = "";
        while (reader.Read())
        {
            if (reader.NodeType != XmlNodeType.Element)
            {
                continue;
            }
             
            strNodeName = reader.Name;
            break;
        }
        //strReader.Close();
        //reader.Close();
        Type type = Type.GetType("OpenDentBusiness." + strNodeName);
        //StringReader strReader2=new StringReader(data);
        //XmlReader reader2=XmlReader.Create(strReader2);
        XmlSerializer serializer = new XmlSerializer(type);
        DataTransferObject retVal = (DataTransferObject)serializer.Deserialize(reader);
        strReader.Close();
        reader.Close();
        return retVal;
    }

}


