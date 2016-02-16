//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.FamilyHealth;
import OpenDentBusiness.FamilyRelationship;
import OpenDentBusiness.TableBase;

/**
* For EHR, this lets us record medical problems for family members.  These family members will usually not be in our database, and they are just recorded by relationship.
*/
public class FamilyHealth  extends TableBase 
{
    /**
    * Primary key.
    */
    public long FamilyHealthNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Enum:FamilyRelationship
    */
    public FamilyRelationship Relationship = FamilyRelationship.Parent;
    /**
    * FK to diseasedef.DiseaseDefNum, which will have a SnoMed associated with it.
    */
    public long DiseaseDefNum = new long();
    /**
    * Name of the family member.
    */
    public String PersonName = new String();
    /**
    * 
    */
    public FamilyHealth clone() {
        try
        {
            return (FamilyHealth)this.MemberwiseClone();
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


