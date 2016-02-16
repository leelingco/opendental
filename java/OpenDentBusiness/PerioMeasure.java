//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PerioMeasure;
import OpenDentBusiness.PerioSequenceType;
import OpenDentBusiness.TableBase;

/**
* One row can hold up to six measurements for one tooth, all of the same type.  Always attached to a perioexam.
*/
public class PerioMeasure  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PerioMeasureNum = new long();
    /**
    * FK to perioexam.PerioExamNum.
    */
    public long PerioExamNum = new long();
    /**
    * Enum:PerioSequenceType  eg probing, mobility, recession, etc.
    */
    public PerioSequenceType SequenceType = PerioSequenceType.Mobility;
    /**
    * Valid values are 1-32. Every measurement must be associated with a tooth.
    */
    public int IntTooth = new int();
    /**
    * This is used when the measurement does not apply to a surface(mobility and skiptooth).  Valid values for all surfaces are 0 through 19, or -1 to represent no measurement taken.
    */
    public int ToothValue = new int();
    /**
    * -1 represents no measurement. Values of 100+ represent negative values (only used for Gingival Margins). e.g. To use a value of 105, subtract it from 100. (100 - 105 = -5)
    */
    public int MBvalue = new int();
    /**
    * .
    */
    public int Bvalue = new int();
    /**
    * .
    */
    public int DBvalue = new int();
    /**
    * .
    */
    public int MLvalue = new int();
    /**
    * .
    */
    public int Lvalue = new int();
    /**
    * .
    */
    public int DLvalue = new int();
    public PerioMeasure copy() throws Exception {
        return (PerioMeasure)this.MemberwiseClone();
    }

}


