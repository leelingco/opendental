//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CodeSystem;
import OpenDentBusiness.TableBase;

/**
* Used for tracking code systems imported to OD. HL7OID used for sending messages.  This must be a database table in order to keep track of VersionCur between sessions.
*/
public class CodeSystem  extends TableBase 
{
    /**
    * Primary key. Not currently referenced anywhere.
    */
    public long CodeSystemNum = new long();
    /**
    * .
    */
    public String CodeSystemName = new String();
    /**
    * Only used for display, not actually interpreted. Updated by Code System importer.  Examples: 2013 or 1
    */
    public String VersionCur = new String();
    /**
    * Only used for display, not actually interpreted. Updated by Convert DB script.
    */
    public String VersionAvail = new String();
    /**
    * Example: 2.16.840.1.113883.6.13
    */
    public String HL7OID = new String();
    /**
    * Notes to display to user. Examples: "CDT codes distributed via program updates.", "CPT codes require purchase and download from www.ama.com
    */
    public String Note = new String();
    /**
    * 
    */
    public CodeSystem clone() {
        try
        {
            return (CodeSystem)this.MemberwiseClone();
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
    * 
    */
    public CodeSystem() throws Exception {
    }

    /**
    * Used to generate version specific list.
    */
    public CodeSystem(String name) throws Exception {
        CodeSystemName = name;
    }

}


