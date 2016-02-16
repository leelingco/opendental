//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DtoObject;

/**
* Packages any object with a TypeName so that it can be serialized and deserialized better.
*/
public class DtoObject  extends IXmlSerializable 
{
    /**
    * Fully qualified name, including the namespace but not the assembly.  Examples: System.Int32, OpenDentBusiness.Patient, OpenDentBusiness.Patient[], List<OpenDentBusiness.Patient>.  When the xml element is created for the Obj, the namespace is not included.  So this field properly stores it.
    */
    public String TypeName = new String();
    /**
    * The actual object.
    */
    public Object Obj = new Object();
    /**
    * Empty constructor as required by IXmlSerializable
    */
    public DtoObject() throws Exception {
    }

    /**
    * This is the constructor that should be used normally because it automatically creates the TypeName.
    */
    public DtoObject(Object obj, Type objType) throws Exception {
        Obj = obj;
        //Type type=obj.GetType();
        //This will eventually become much more complex:
        //Arrays automatically become "ArrayOf..." and serialize just fine, with TypeName=...[]
        //Lists:
        if (objType.IsGenericType)
        {
            Type listType = objType.GetGenericArguments()[0];
            TypeName = "List<" + listType.FullName + ">";
        }
        else
        {
            TypeName = objType.FullName;
        } 
    }

    /**
    * This is not explicitly called by our code.  It's required for the IXmlSerializable interface, which we have defined for this class.  So C# will call this when we call Serialize().
    */
    public void writeXml(XmlWriter writer) throws Exception {
        /* we want the result to look like this:
        			<TypeName>Patient</TypeName>
        			<Obj>
        				<Patient>
        					<LName>Smith</LName>
        					<PatNum>22</PatNum>
        					<IsGuar>True</IsGuar>
        				</Patient>
        			</Obj>
        			*/
        writer.WriteStartElement("TypeName");
        writer.WriteString(TypeName);
        writer.WriteEndElement();
        //TypeName
        writer.WriteStartElement("Obj");
        if (StringSupport.equals(TypeName, "System.Drawing.Color"))
        {
            XmlSerializer serializer = new XmlSerializer(int.class);
            serializer.Serialize(writer, ((Color)Obj).ToArgb());
        }
        else
        {
            String assemb = Assembly.GetAssembly(Db.class).FullName;
            Type type = convertNameToType(TypeName,assemb);
            XmlSerializer serializer = new XmlSerializer(type);
            serializer.Serialize(writer, Obj);
        } 
        writer.WriteEndElement();
    }

    //Obj
    public void readXml(XmlReader reader) throws Exception {
        reader.ReadToFollowing("TypeName");
        reader.ReadStartElement("TypeName");
        TypeName = reader.ReadString();
        reader.ReadEndElement();
        while (reader.NodeType != XmlNodeType.Element)
        {
            //TypeName
            reader.Read();
        }
        //gets rid of whitespace if in debug mode.
        reader.ReadStartElement("Obj");
        while (reader.NodeType != XmlNodeType.Element)
        {
            reader.Read();
        }
        String strObj = reader.ReadOuterXml();
        while (reader.NodeType != XmlNodeType.EndElement)
        {
            //now get the reader to the correct location
            reader.Read();
        }
        reader.ReadEndElement();
        while (reader.NodeType != XmlNodeType.EndElement)
        {
            //Obj
            reader.Read();
        }
        reader.ReadEndElement();
        //DtoObject
        Type type = null;
        if (TypeName.StartsWith("List<"))
        {
            Type typeGen = Type.GetType(TypeName.Substring(5, TypeName.Length - 6));
            Type typeList = .class;
            type = typeList.MakeGenericType(typeGen);
        }
        else if (StringSupport.equals(TypeName, "System.Drawing.Color"))
        {
            type = int.class;
        }
        else
        {
            //This works fine for non-system types as well without specifying the assembly,
            //because we are already in the OpenDentBusiness assembly.
            type = Type.GetType(TypeName);
        }  
        XmlSerializer serializer = new XmlSerializer(type);
        //XmlReader reader2=XmlReader.Create(new StringReader(strObj));
        XmlTextReader reader2 = new XmlTextReader(new StringReader(strObj));
        if (StringSupport.equals(TypeName, "System.Drawing.Color"))
        {
            Obj = Color.FromArgb((int)serializer.Deserialize(reader2));
        }
        else
        {
            Obj = serializer.Deserialize(reader2);
        } 
    }

    //Convert.ChangeType(serializer.Deserialize(reader2),type);
    /**
    * Required by IXmlSerializable
    */
    public XmlSchema getSchema() throws Exception {
        return (null);
    }

    /**
    * We must pass in a matching array of types for situations where nulls are used in parameters.  Otherwise, we won't know the parameter type.
    */
    public static DtoObject[] constructArray(Object[] objArray, Type[] objTypes) throws Exception {
        DtoObject[] retVal = new DtoObject[objArray.Length];
        for (int i = 0;i < objArray.Length;i++)
        {
            retVal[i] = new DtoObject(objArray[i], objTypes[i]);
        }
        return retVal;
    }

    public static Object[] generateObjects(DtoObject[] parameters) throws Exception {
        Object[] retVal = new Object[parameters.Length];
        for (int i = 0;i < parameters.Length;i++)
        {
            retVal[i] = parameters[i].Obj;
        }
        return retVal;
    }

    public static Type[] generateTypes(DtoObject[] parameters, String assemb) throws Exception {
        Type[] retVal = new Type[parameters.Length];
        for (int i = 0;i < parameters.Length;i++)
        {
            retVal[i] = ConvertNameToType(parameters[i].TypeName, assemb);
        }
        return retVal;
    }

    private static Type convertNameToType(String FullName, String assemb) throws Exception {
        Type typeObj = null;
        if (FullName.StartsWith("List<"))
        {
            String strTypeGenName = FullName.Substring(5, FullName.Length - 6);
            //strips off the List<>
            Type typeGen = null;
            if (strTypeGenName.StartsWith("OpenDentBusiness"))
            {
                typeGen = Type.GetType(strTypeGenName + "," + assemb);
            }
            else
            {
                //system types
                typeGen = Type.GetType(strTypeGenName);
            } 
            Type typeList = .class;
            typeObj = typeList.MakeGenericType(typeGen);
        }
        else if (FullName.StartsWith("OpenDentBusiness"))
        {
            typeObj = Type.GetType(FullName + "," + assemb);
        }
        else if (StringSupport.equals(FullName, "System.Drawing.Color"))
        {
            typeObj = Color.class;
        }
        else
        {
            //system types
            typeObj = Type.GetType(FullName);
        }   
        return typeObj;
    }

}


