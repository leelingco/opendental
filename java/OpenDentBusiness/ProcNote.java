//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* A procedure note for one procedure.  User does not have any direct control over this table at all.  It's handled automatically.  When user "edits" a procedure note, the program actually just adds another note.  No note can EVER be edited or deleted.
*/
public class ProcNote  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ProcNoteNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * FK to procedurelog.ProcNum
    */
    public long ProcNum = new long();
    /**
    * The server time that this note was entered. Essentially a timestamp.
    */
    public DateTime EntryDateTime = new DateTime();
    /**
    * FK to userod.UserNum.
    */
    public long UserNum = new long();
    /**
    * The actual note.
    */
    public String Note = new String();
    /**
    * There are two kinds of signatures.  Topaz signatures use hardware manufactured by that company, and the signature is created by their library.  OD signatures work exactly the same way, but are only for on-screen signing.
    */
    public boolean SigIsTopaz = new boolean();
    /**
    * The encrypted signature.  A signature starts as a collection of vectors.  The Topaz .sig file format is proprietary.  The OD signature format looks like this: 45,68;48,70;49,72;0,0;55,88;etc.  It's simply a sequence of points, separated by semicolons.  0,0 represents pen up.  Then, a hash is created from the Note, concatenated directly with the userNum.  For example, "This is a note3" gets turned into a hash of 2849283940385391 (16 bytes).  The hash is used to encrypt the signature data string using symmetric encryption.  Therefore, the actual signature cannot be retrieved from the database by ordinary means.  Also, the signature info cannot even be retrieved by Open Dental at all unless it supplies the same hash as before, proving that the data has not changed since signed.  If OD supplies the correct hash, then it will be able to extract the sequence of vectors which it will then use to display the signature.  The OD sigs are not compressed, and the Topaz sigs are.  But there is very little difference in their sizes.  It would be very rare for a signature to be larger than 1000 bytes.
    */
    public String Signature = new String();
}


