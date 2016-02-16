//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Guardian;
import OpenDentBusiness.GuardianRelationship;
import OpenDentBusiness.TableBase;

/**
* Links patient to patient in a many to many database relationship.  The two PatNums need not be in the same family, but will usually be.
* The two PatNums could be in different families if the relationship was entered, then one of the patients in the relationship is moved to another family.
* This table can also be used for other relationship types besides guardians.  The table name is guardian because we only supported guardian relationships in the past,
* and we did not want to risk breaking queries by changing the table or column names. User can specify any relationship as a guardian or not a guardian.
* For example, a retired person might specify their brother or child as their guardian, or the user may want to record the brother of a patient as a non-guardian.
*/
public class Guardian  extends TableBase 
{
    /**
    * Primary key.
    */
    public long GuardianNum = new long();
    /**
    * FK to patient.PatNum.  If Relationship is "Mother", then this PatNum is the child of the mother.
    */
    public long PatNumChild = new long();
    /**
    * FK to patient.PatNum.  If Relationship is "Mother", then this is the PatNum of the mother.
    */
    public long PatNumGuardian = new long();
    /**
    * Enum:GuardianRelationship .
    */
    public GuardianRelationship Relationship = GuardianRelationship.Father;
    /**
    * True if this specifies a guardian relationship, or false if any other relationship.
    * When this flag is true, the relationship will show in the "Guardians" appointment view field and in the family module "Guardians" display field for the patient.
    */
    public boolean IsGuardian = new boolean();
    /**
    * 
    */
    public Guardian clone() {
        try
        {
            return (Guardian)this.MemberwiseClone();
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


