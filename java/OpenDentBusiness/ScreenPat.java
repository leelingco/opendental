//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ScreenPat;
import OpenDentBusiness.TableBase;

/**
* This allows users to set up a list of students prior to actually going to the school.  It also serves to attach the exam sheet to the screening.
*/
public class ScreenPat  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ScreenPatNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * FK to screengroup.ScreenGroupNum. Every screening is attached to a group (classroom)
    */
    public long ScreenGroupNum = new long();
    /**
    * FK to sheet.SheetNum. Starts out 0 to indicate a potential screening. Gets linked to an exam sheet once the screening is done.
    */
    public long SheetNum = new long();
    /**
    * 
    */
    public ScreenPat clone() {
        try
        {
            return (ScreenPat)this.MemberwiseClone();
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


