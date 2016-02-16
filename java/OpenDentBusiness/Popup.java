//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EnumPopupLevel;
import OpenDentBusiness.Popup;
import OpenDentBusiness.TableBase;

/**
* If an existing popup message gets changed, then an archive first gets created that's a copy of the original.  This is so that we can track historical changes.  When a new one gets created, all the archived popups will get automatically repointed to the new one.  If you "delete" a popup, it actually archives that popup.  All the other archives of that popup still point to the newly archived popup, but now there is no popup in that group with the IsArchived flag not set.
*/
public class Popup  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PopupNum = new long();
    /**
    * FK to patient.PatNum.  If PopupLevel is Family/SuperFamily then this must be a guarantor/super family head.
    */
    public long PatNum = new long();
    /**
    * The text of the popup.
    */
    public String Description = new String();
    /**
    * If true, then the popup won't automatically show when a patient is selected.  Kind of useless except for offices that want to still show historical popups.
    */
    public boolean IsDisabled = new boolean();
    /**
    * Enum:EnumPopupLevel 0=Patient, 1=Family, 2=Superfamily. If Family, then this Popup will apply to the entire family and PatNum will the Guarantor PatNum.  If Superfamily, then this popup will apply to the entire superfamily and PatNum will be the head of the superfamily. This column will need to be synched for all family actions where the guarantor changes.
    */
    public EnumPopupLevel PopupLevel = EnumPopupLevel.Patient;
    //rename to PopupLevel
    /**
    * FK to userod.UserNum.
    */
    public long UserNum = new long();
    /**
    * The server time that this note was entered.  Cannot be changed by user.  Does not get changed automatically when level or isDisabled gets changed.  If note itself changes, then a new popup is created along with a new DateTimeEntry. Current popup's edit date gets set to the previous entry's DateTimeEntry
    */
    public DateTime DateTimeEntry = new DateTime();
    /**
    * Indicates that this is not the most current popup and that it is an archive.  True for any archived or "deleted" popups.
    */
    public boolean IsArchived = new boolean();
    /**
    * This will be zero for current popups that show when a patient is selected.  Archived popups will have a value which is the FK to its parent Popup.  The parent popup could be the most recent popup or another archived popup.  Will be zero for current and "deleted" popups.
    */
    public long PopupNumArchive = new long();
    //We will consider later adding Guarantor and SuperFamily FK's to speed up queries.  The disadvantage is that popups would then have to be synched every time guarantors or sf heads changed.
    public Popup copy() throws Exception {
        return (Popup)this.MemberwiseClone();
    }

}


