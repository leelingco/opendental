//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.DeletedObject;
import OpenDentBusiness.DeletedObjectType;
import OpenDentBusiness.TableBase;

/**
* When some objects are deleted, we sometimes need a way to track them for synching purposes.  Other objects already have fields for IsHidden or PatStatus which track deletions just fine.  Those types of objects will not use this table.
*/
public class DeletedObject  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DeletedObjectNum = new long();
    /**
    * Foreign key to a number of different tables, depending on which type it is.
    */
    public long ObjectNum = new long();
    /**
    * Enum:DeletedObjectType
    */
    public DeletedObjectType ObjectType = DeletedObjectType.Appointment;
    /**
    * Updated any time the row is altered in any way.
    */
    public DateTime DateTStamp = new DateTime();
    public DeletedObject clone() {
        try
        {
            return (DeletedObject)this.MemberwiseClone();
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


