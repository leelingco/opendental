//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EhrLabNote;
import OpenDentBusiness.TableBase;

/**
* For EHR module, May either be a note attached to an EhrLab or an EhrLabResult.  NTE.*
*/
public class EhrLabNote  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrLabNoteNum = new long();
    /**
    * FK to EhrLab.EhrLabNum.  Should never be zero.
    */
    public long EhrLabNum = new long();
    /**
    * FK to EhrLabResult.EhrLabResult.  May be 0 if this is a Lab Note, will be valued if this is an Ehr Lab Result Note.
    */
    public long EhrLabResultNum = new long();
    ///<summary>Carret delimited list of comments.  Comments must be formatted text and cannot contain the following 6 characters |^&~\#  NTE.*.*</summary>
    public String Comments = new String();
    /**
    * 
    */
    public EhrLabNote copy() throws Exception {
        return (EhrLabNote)MemberwiseClone();
    }

    public EhrLabNote() throws Exception {
        Comments = "";
    }

}


