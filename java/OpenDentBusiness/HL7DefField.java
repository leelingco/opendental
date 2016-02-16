//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.DataTypeHL7;
import OpenDentBusiness.HL7DefField;
import OpenDentBusiness.TableBase;

/**
* Multiple fields per segment.
*/
public class HL7DefField  extends TableBase 
{
    /**
    * Primary key.
    */
    public long HL7DefFieldNum = new long();
    /**
    * FK to HL7DefSegment.HL7DefSegmentNum
    */
    public long HL7DefSegmentNum = new long();
    /**
    * Position within the segment.
    */
    public int OrdinalPos = new int();
    /**
    * HL7 table Id, if applicable. Example: 0234. Example: 1234/2345.  DataType will be ID.
    */
    public String TableId = new String();
    /**
    * The DataTypeHL7 enum will be unlinked from the db by storing as string in db. As it's loaded into OD, it will become an enum.
    */
    public DataTypeHL7 DataType = DataTypeHL7.CNE;
    /**
    * User will get to pick from a list of fields that we will maintain. Example: guar.nameLFM, prov.provIdName, or pat.addressCityStateZip.  See below for the full list.  This will be blank if this is a fixed text field.
    */
    public String FieldName = new String();
    /**
    * User will need to insert fixed text for some fields.  Either FixedText or FieldName will have a value, not both.
    */
    public String FixedText = new String();
    /**
    * 
    */
    public HL7DefField clone() {
        try
        {
            return (HL7DefField)this.MemberwiseClone();
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


