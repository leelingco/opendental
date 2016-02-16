//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import EhrLaboratories.HL70200;
import EhrLaboratories.HL70203;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EhrLabResultsCopyTo;
import OpenDentBusiness.TableBase;

/**
* For EHR module, copy results to... that contains all required fields for HL7 Lab Reporting Interface (LRI).
*/
public class EhrLabResultsCopyTo  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrLabResultsCopyToNum = new long();
    /**
    * FK to EhrLab.EhrLabNum.
    */
    public long EhrLabNum = new long();
    /**
    * May be provnum or NPI num or any other num, when combined with CopyToIdAssigningAuthority should uniquely identify the provider.  OBR.28.1
    */
    public String CopyToID = new String();
    /**
    * OBR.28.2
    */
    public String CopyToLName = new String();
    /**
    * OBR.28.3
    */
    public String CopyToFName = new String();
    /**
    * Middle names or initials therof.  OBR.28.4
    */
    public String CopyToMiddleNames = new String();
    /**
    * Example: JR or III.  OBR.28.5
    */
    public String CopyToSuffix = new String();
    /**
    * Example: DR, Not MD, MD would be stored in an optional field that was not implemented called CopyToDegree.  OBR.28.6
    */
    public String CopyToPrefix = new String();
    /**
    * Usually empty, "The value of [this field] reflects a local code that represents the combination of [the next two fields]."  OBR.28.9.1
    */
    public String CopyToAssigningAuthorityNamespaceID = new String();
    /**
    * ISO compliant OID that represents the organization that assigned the unique provider ID.  OBR.28.9.2
    */
    public String CopyToAssigningAuthorityUniversalID = new String();
    /**
    * Always "ISO", unless importing from outside source.  OBR.28.9.3
    */
    public String CopyToAssigningAuthorityIDType = new String();
    /**
    * Describes the type of name used.  OBR.28.10
    */
    public HL70200 CopyToNameTypeCode = HL70200.C;
    /**
    * Must be value from HL70203 code set, see note at bottom of EhrLab.cs for usage.  OBR.28.13
    */
    public HL70203 CopyToIdentifierTypeCode = HL70203.AN;
    /**
    * 
    */
    public EhrLabResultsCopyTo copy() throws Exception {
        return (EhrLabResultsCopyTo)MemberwiseClone();
    }

}


