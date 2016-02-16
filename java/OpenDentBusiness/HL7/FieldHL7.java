//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import OpenDentBusiness.HL7.ComponentHL7;
import OpenDentBusiness.HL7.FieldHL7;

public class FieldHL7   
{
    /**
    * 
    */
    private String fullText = new String();
    /**
    * Not often used. Some HL7 fields are allowed to "repeat" multiple times. For example, in immunization messaging export (VXU messages), PID-3 repeats twice, once for patient ID and once for SSN.
    */
    private List<FieldHL7> _listRepeatFields = new List<FieldHL7>();
    public List<ComponentHL7> Components = new List<ComponentHL7>();
    /**
    * Only use this constructor when generating a message instead of parsing a message.
    */
    public FieldHL7() throws Exception {
        fullText = "";
        Components = new List<ComponentHL7>();
        ComponentHL7 component;
        component = new ComponentHL7("");
        Components.Add(component);
    }

    //add more components later if needed.
    public FieldHL7(String fieldText) throws Exception {
        setFullText(fieldText);
    }

    public String toString() {
        try
        {
            return fullText;
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

    /**
    * Setting the FullText resets all the child components to the values passed in here.
    */
    public String getFullText() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.Append(fullText);
        for (int i = 0;i < _listRepeatFields.Count;i++)
        {
            sb.Append("~");
            //Field repitition separator.  Always before each repeat field, even if fullText is blank.
            sb.Append(_listRepeatFields[i].FullText);
        }
        return sb.ToString();
    }

    public void setFullText(String value) throws Exception {
        fullText = value;
        Components = new List<ComponentHL7>();
        //In the future, we could first split by ~ to get the repeating fields as needed, then split each field instance by the ^ character.
        String[] components = fullText.Split(new String[]{ "^" }, StringSplitOptions.None);
        //leave empty entries in place
        ComponentHL7 component;
        for (int i = 0;i < components.Length;i++)
        {
            component = new ComponentHL7(components[i]);
            Components.Add(component);
        }
    }

    /**
    * 
    */
    public String getComponentVal(int indexPos) throws Exception {
        if (indexPos > Components.Count - 1)
        {
            return "";
        }
         
        return Components[indexPos].ComponentVal;
    }

    /**
    * This also resets the number of components.  And it sets fullText.
    */
    public void setVals(String... values) throws Exception {
        if (values.Length == 1)
        {
            setFullText(values[0]);
            return ;
        }
         
        //this allows us to pass in all components for the field as one long string: comp1^comp2^comp3
        fullText = "";
        Components = new List<ComponentHL7>();
        ComponentHL7 component;
        for (int i = 0;i < values.Length;i++)
        {
            component = new ComponentHL7(values[i]);
            Components.Add(component);
            fullText += values[i];
            if (i < values.Length - 1)
            {
                fullText += "^";
            }
             
        }
    }

    /**
    * Not often used. Some HL7 fields are allowed to "repeat" multiple times. For example, in immunization messaging export (VXU messages), PID-3 repeats twice, once for patient ID and once for SSN.
    */
    public void repeatVals(String... values) throws Exception {
        FieldHL7 field = new FieldHL7();
        field.SetVals(values);
        _listRepeatFields.Add(field);
    }

}


