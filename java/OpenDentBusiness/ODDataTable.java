//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ODDataRow;
import OpenDentBusiness.PIn;

public class ODDataTable   
{
    public String Name = new String();
    public List<ODDataRow> Rows = new List<ODDataRow>();
    public ODDataTable() throws Exception {
        Rows = new List<ODDataRow>();
        Name = "";
    }

    public ODDataTable(String xmlData) throws Exception {
        XmlDocument doc = new XmlDocument();
        doc.LoadXml(xmlData);
        Rows = new List<ODDataRow>();
        Name = "";
        ODDataRow currentRow;
        XmlNodeList nodesRows = doc.DocumentElement.ChildNodes;
        for (int i = 0;i < nodesRows.Count;i++)
        {
            currentRow = new ODDataRow();
            if (StringSupport.equals(Name, ""))
            {
                Name = nodesRows[i].Name;
            }
             
            for (Object __dummyForeachVar0 : nodesRows[i].ChildNodes)
            {
                XmlNode nodeCell = (XmlNode)__dummyForeachVar0;
                currentRow.Add(nodeCell.Name.ToString(), nodeCell.InnerXml);
            }
            Rows.Add(currentRow);
        }
    }

    public <T>List<T> toList() throws Exception {
        Type tp = T.class;
        //List<object> list=new List<object>();
        List<T> list = new List<T>();
        FieldInfo[] fieldInfo = tp.GetFields();
        Object obj = /* [UNSUPPORTED] default expressions are not yet supported "default T" */;
        for (int i = 0;i < Rows.Count;i++)
        {
            ConstructorInfo constructor = tp.GetConstructor(System.Type.EmptyTypes);
            obj = constructor.Invoke(null);
            for (int f = 0;f < fieldInfo.Length;f++)
            {
                if (fieldInfo[f].FieldType == int.class)
                {
                    fieldInfo[f].SetValue(obj, PIn.Long(Rows[i][f]));
                }
                else if (fieldInfo[f].FieldType == boolean.class)
                {
                    fieldInfo[f].SetValue(obj, PIn.Bool(Rows[i][f]));
                }
                else if (fieldInfo[f].FieldType == String.class)
                {
                    fieldInfo[f].SetValue(obj, PIn.String(Rows[i][f]));
                }
                else if (fieldInfo[f].FieldType.IsEnum)
                {
                    Object val = ((Object[])Enum.GetValues(fieldInfo[f].FieldType))[PIn.Long(Rows[i][f])];
                    fieldInfo[f].SetValue(obj, val);
                }
                    
            }
            list.Add((T)obj);
        }
        return list;
    }

}


//Collection
//(List<T>)list.Cast<T>();
//public List ToList(){
//public T Field;
//	return null;
//}