//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.DocumentMisc;
import OpenDentBusiness.DocumentMiscType;
import OpenDentBusiness.TableBase;

/**
* For storing docs/images in database.  This table is for the various miscellaneous documents that are not in the normal patient subfolders.
*/
public class DocumentMisc  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DocMiscNum = new long();
    /**
    * Date created.
    */
    public DateTime DateCreated = new DateTime();
    /**
    * The name the file would have if it was not in the database. Does not include any directory info.
    */
    public String FileName = new String();
    /**
    * Enum:DocumentMiscType Corresponds to the same subfolder within AtoZ folder. eg. UpdateFiles
    */
    public DocumentMiscType DocMiscType = DocumentMiscType.UpdateFiles;
    /**
    * The raw file data encoded as base64.
    */
    public String RawBase64 = new String();
    /**
    * Returns a copy of this DocumentMisc.
    */
    public DocumentMisc copy() throws Exception {
        return (DocumentMisc)this.MemberwiseClone();
    }

}


