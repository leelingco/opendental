//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.FeeSched;
import OpenDentBusiness.FeeScheduleType;
import OpenDentBusiness.TableBase;

/**
* Fee schedule names used to be in the definition table, but now they have their own table.  We are about to have many many more fee schedules as we start automating allowed fees.
*/
public class FeeSched  extends TableBase 
{
    /**
    * Primary key.
    */
    public long FeeSchedNum = new long();
    /**
    * The name of the fee schedule.
    */
    public String Description = new String();
    /**
    * Enum:FeeScheduleType
    */
    public FeeScheduleType FeeSchedType = FeeScheduleType.Normal;
    /**
    * Unlike with the old definition table, this ItemOrder is not as critical in the caching of data.  The item order is only for fee schedules of the same type.
    */
    public int ItemOrder = new int();
    /**
    * True if the fee schedule is hidden.  Can't delete fee schedules or change their type once created.
    */
    public boolean IsHidden = new boolean();
    public FeeSched copy() throws Exception {
        return (FeeSched)this.MemberwiseClone();
    }

}


