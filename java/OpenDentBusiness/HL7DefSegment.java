//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.DataTypeHL7;
import OpenDentBusiness.FieldNameAndType;
import OpenDentBusiness.HL7DefField;
import OpenDentBusiness.HL7DefSegment;
import OpenDentBusiness.SegmentNameHL7;
import OpenDentBusiness.TableBase;

/**
* multiple segments per message
*/
public class HL7DefSegment  extends TableBase 
{
    /**
    * Primary key.
    */
    public long HL7DefSegmentNum = new long();
    /**
    * FK to HL7DefMessage.HL7DefMessageNum
    */
    public long HL7DefMessageNum = new long();
    /**
    * Since we don't enforce or automate, it can be 1-based or 0-based.  For outgoing, this affects the message structure.  For incoming, this is just for convenience and organization in the HL7 Def windows.
    */
    public int ItemOrder = new int();
    /**
    * For example, a DFT can have multiple FT1 segments.  This turns out to be a completely useless field, since we already know which ones can repeat.
    */
    public boolean CanRepeat = new boolean();
    /**
    * If this is false, and an incoming message is missing this segment, then it gets logged as an error/failure.  If this is true, then it will gracefully skip a missing incoming segment.  Not used for outgoing.
    */
    public boolean IsOptional = new boolean();
    /**
    * Stored in db as string, but used in OD as enum SegmentNameHL7. Example: PID.
    */
    public SegmentNameHL7 SegmentName = SegmentNameHL7.AIG;
    /**
    * .
    */
    public String Note = new String();
    /**
    * List of segments associated with this hierarchical definition.  Use items in this list to get to items lower in the hierarchy.
    */
    public List<HL7DefField> hl7DefFields = new List<HL7DefField>();
    /**
    * 
    */
    public HL7DefSegment clone() {
        try
        {
            return (HL7DefSegment)this.MemberwiseClone();
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

    public void addField(int ordinalPos, String tableId, DataTypeHL7 dataType, String fieldName, String fixedText) throws Exception {
        if (hl7DefFields == null)
        {
            hl7DefFields = new List<HL7DefField>();
        }
         
        HL7DefField field = new HL7DefField();
        field.OrdinalPos = ordinalPos;
        field.DataType = dataType;
        field.TableId = tableId;
        field.FieldName = fieldName;
        field.FixedText = fixedText;
        this.hl7DefFields.Add(field);
    }

    public void addField(int ordinalPos, String fieldName) throws Exception {
        DataTypeHL7 dataType = FieldNameAndType.getTypeFromName(fieldName);
        String tableNum = FieldNameAndType.getTableNumFromName(fieldName);
        addField(ordinalPos,tableNum,dataType,fieldName,"");
    }

    /**
    * 
    */
    public void addFieldFixed(int ordinalPos, DataTypeHL7 dataType, String fixedText) throws Exception {
        addField(ordinalPos,"",dataType,"",fixedText);
    }

}


