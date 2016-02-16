//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.HL7.FieldHL7;
import OpenDentBusiness.SegmentNameHL7;

/**
* A 'row' in the message.  Composed of fields
*/
public class SegmentHL7   
{
    public List<FieldHL7> Fields = new List<FieldHL7>();
    /**
    * The name
    */
    public SegmentNameHL7 Name = SegmentNameHL7.AIG;
    /**
    * The original full text of the segment.
    */
    private String fullText = new String();
    /**
    * Only use this constructor when generating a message instead of parsing a message.
    */
    public SegmentHL7(SegmentNameHL7 name) throws Exception {
        fullText = "";
        Name = name;
        Fields = new List<FieldHL7>();
        //remember that the "field quantity" is one more than the last index, because 0-based.
        //All fields are initially added with just one component
        //This can all probably be removed now since we add fields dynamically as needed:
        if (name == SegmentNameHL7.MSA)
        {
            addFields(3);
        }
         
        if (name == SegmentNameHL7.MSH)
        {
            addFields(12);
        }
         
        if (name == SegmentNameHL7.EVN)
        {
            addFields(4);
        }
         
        if (name == SegmentNameHL7.PID)
        {
            addFields(23);
        }
         
        if (name == SegmentNameHL7.PV1)
        {
            addFields(51);
        }
         
        if (name == SegmentNameHL7.FT1)
        {
            addFields(27);
        }
         
        if (name == SegmentNameHL7.DG1)
        {
            addFields(5);
        }
         
        if (name == SegmentNameHL7.ZX1)
        {
            addFields(6);
        }
         
    }

    private void addFields(int quantity) throws Exception {
        FieldHL7 field;
        for (int i = 0;i < quantity;i++)
        {
            field = new FieldHL7();
            Fields.Add(field);
        }
    }

    /**
    * Use this constructor when we have a message to parse.
    */
    public SegmentHL7(String rowtext) throws Exception {
        setFullText(rowtext);
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
    * Setting the FullText resets all the child fields.
    */
    public String getFullText() throws Exception {
        return fullText;
    }

    public void setFullText(String value) throws Exception {
        fullText = value;
        Fields = new List<FieldHL7>();
        String[] fields = fullText.Split(new String[]{ "|" }, StringSplitOptions.None);
        FieldHL7 field;
        for (int i = 0;i < fields.Length;i++)
        {
            field = new FieldHL7(fields[i]);
            Fields.Add(field);
        }
        try
        {
            Name = (SegmentNameHL7)Enum.Parse(SegmentNameHL7.class, Fields[0].FullText);
        }
        catch (Exception __dummyCatchVar1)
        {
            Name = SegmentNameHL7.Unknown;
        }
    
    }

    /**
    * 
    */
    public String getFieldFullText(int indexPos) throws Exception {
        if (indexPos > Fields.Count - 1)
        {
            return "";
        }
         
        return Fields[indexPos].FullText;
    }

    /**
    * Really just a handy shortcut.  Identical to getting segment 0 or to GetFieldFullText.
    */
    public String getFieldComponent(int fieldIndex) throws Exception {
        return getFieldFullText(fieldIndex);
    }

    public String getFieldComponent(int fieldIndex, int segIndex) throws Exception {
        if (fieldIndex > Fields.Count - 1)
        {
            return "";
        }
         
        return Fields[fieldIndex].GetComponentVal(segIndex);
    }

    /**
    * 
    */
    public FieldHL7 getField(int indexPos) throws Exception {
        if (indexPos > Fields.Count - 1)
        {
            return null;
        }
         
        return Fields[indexPos];
    }

    /**
    * Pass in one val to set the whole field.  Pass in multiple vals to set multiple components.  It also sets the fullText of the segment.
    */
    public void setField(int fieldIndex, String... vals) throws Exception {
        if (fieldIndex > Fields.Count - 1)
        {
            addFields(fieldIndex - (Fields.Count - 1));
        }
         
        Fields[fieldIndex].SetVals(vals);
        //kind of repetitive to recalc the whole segment again here, but it goes very fast.
        refreshFullText();
    }

    private void refreshFullText() throws Exception {
        fullText = "";
        for (int i = 0;i < Fields.Count;i++)
        {
            if (i > 0)
            {
                fullText += "|";
            }
             
            fullText += Fields[i].FullText;
        }
    }

    /**
    * Not often used.  If RepeatField() is called before SetField(), then the first field instance will be blank.
    * Some HL7 fields are allowed to "repeat" multiple times.
    * For example, in immunization messaging export (VXU messages), PID-3 repeats twice, once for patient ID and once for SSN.
    */
    public void repeatField(int fieldIndex, String... vals) throws Exception {
        if (fieldIndex > Fields.Count - 1)
        {
            //The field does not exist yet.
            addFields(fieldIndex - (Fields.Count - 1));
        }
         
        //The field exists and has already been set.  Repeat the field with the new values.
        Fields[fieldIndex].RepeatVals(vals);
        //kind of repetitive to recalc the whole segment again here, but it goes very fast.
        refreshFullText();
    }

    /**
    * Sets the field if not yet set, otherwise repeats the field with the new values.
    * Not often used.  Some HL7 fields are allowed to "repeat" multiple times.
    * For example, in immunization messaging export (VXU messages), PID-3 repeats twice, once for patient ID and once for SSN.
    */
    public void setOrRepeatField(int fieldIndex, String... vals) throws Exception {
        if (fieldIndex > Fields.Count - 1)
        {
            //The field does not exist yet.
            addFields(fieldIndex - (Fields.Count - 1));
        }
         
        if (StringSupport.equals(Fields[fieldIndex].FullText, ""))
        {
            //The field exists and has not been set yet.
            SetField(fieldIndex, vals);
            return ;
        }
         
        //The field exists and has already been set.  Repeat the field with the new values.
        Fields[fieldIndex].RepeatVals(vals);
        //kind of repetitive to recalc the whole segment again here, but it goes very fast.
        refreshFullText();
    }

    /**
    * yyyyMMdd[HHmm[ss]].  If not in that format, it returns minVal.
    */
    public DateTime getDateTime(int fieldIndex) throws Exception {
        if (fieldIndex > Fields.Count - 1)
        {
            return DateTime.MinValue;
        }
         
        String str = Fields[fieldIndex].FullText.Trim();
        try
        {
            //trailing space was causing problems.
            if (str.Length == 8)
            {
                return DateTime.ParseExact(str, "yyyyMMdd", DateTimeFormatInfo.InvariantInfo);
            }
             
            if (str.Length == 12)
            {
                return DateTime.ParseExact(str, "yyyyMMddHHmm", DateTimeFormatInfo.InvariantInfo);
            }
             
            if (str.Length == 14)
            {
                return DateTime.ParseExact(str, "yyyyMMddHHmmss", DateTimeFormatInfo.InvariantInfo);
            }
             
        }
        catch (Exception __dummyCatchVar2)
        {
            return DateTime.MinValue;
        }

        return DateTime.MinValue;
    }

}


