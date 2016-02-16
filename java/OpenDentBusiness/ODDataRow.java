//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PIn;

public class ODDataRow  extends SortedList<String, String> 
{
    //Dictionary<string,string>{
    public String get___idx(int index) throws Exception {
        return this.Values[index];
    }

    public Object toObject(Type objectType) throws Exception {
        ConstructorInfo constructor = objectType.GetConstructor(System.Type.EmptyTypes);
        Object obj = constructor.Invoke(null);
        FieldInfo[] fieldInfo = objectType.GetFields();
        for (int f = 0;f < fieldInfo.Length;f++)
        {
            if (fieldInfo[f].FieldType == int.class)
            {
                fieldInfo[f].SetValue(obj, PIn.long(this.get___idx(f)));
            }
            else if (fieldInfo[f].FieldType == boolean.class)
            {
                fieldInfo[f].SetValue(obj, PIn.bool(this.get___idx(f)));
            }
            else if (fieldInfo[f].FieldType == String.class)
            {
                fieldInfo[f].SetValue(obj, PIn.string(this.get___idx(f)));
            }
            else if (fieldInfo[f].FieldType.IsEnum)
            {
                Object val = ((Object[])Enum.GetValues(fieldInfo[f].FieldType))[PIn.long(this.get___idx(f))];
                fieldInfo[f].SetValue(obj, val);
            }
            else
            {
                throw new System.NotImplementedException();
            }    
        }
        return obj;
    }

}


