//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package OpenDental;

import OpenDental.POut;
import OpenDental.RefAttach;

/**
* Attaches a referral to a patient.
*/
public class RefAttach   
{
    /**
    * Primary key.
    */
    public int RefAttachNum = new int();
    /**
    * FK to referral.ReferralNum.
    */
    public int ReferralNum = new int();
    /**
    * FK to patient.PatNum.
    */
    public int PatNum = new int();
    /**
    * Order to display in patient info. Will be automated more in future.
    */
    public int ItemOrder = new int();
    /**
    * Date of referral.
    */
    public DateTime RefDate = new DateTime();
    //
    /**
    * true=from, false=to
    */
    public boolean IsFrom = new boolean();
    /**
    * Returns a copy of this RefAttach.
    */
    public RefAttach copy() throws Exception {
        RefAttach r = new RefAttach();
        r.RefAttachNum = RefAttachNum;
        r.ReferralNum = ReferralNum;
        r.PatNum = PatNum;
        r.ItemOrder = ItemOrder;
        r.RefDate = RefDate;
        r.IsFrom = IsFrom;
        return r;
    }

    /**
    * 
    */
    public void update() throws Exception {
        String command = "UPDATE refattach SET " + "referralnum = '" + POut.pInt(ReferralNum) + "'" + ",patnum = '" + POut.pInt(PatNum) + "'" + ",itemorder = '" + POut.pInt(ItemOrder) + "'" + ",refdate = '" + POut.pDate(RefDate) + "'" + ",isfrom = '" + POut.pBool(IsFrom) + "'" + " WHERE RefAttachNum = '" + POut.pInt(RefAttachNum) + "'";
        //MessageBox.Show(command);
        General.NonQ(command);
    }

    /**
    * 
    */
    public void insert() throws Exception {
        String command = "INSERT INTO refattach (referralnum,patnum," + "itemorder,refdate,IsFrom) VALUES(" + "'" + POut.pInt(ReferralNum) + "', " + "'" + POut.pInt(PatNum) + "', " + "'" + POut.pInt(ItemOrder) + "', " + "'" + POut.pDate(RefDate) + "', " + "'" + POut.pBool(IsFrom) + "')";
        RefAttachNum = General.NonQ(command, true);
    }

    /**
    * 
    */
    public void delete() throws Exception {
        String command = "DELETE FROM refattach " + "WHERE refattachnum = '" + RefAttachNum + "'";
        General.NonQ(command);
    }

}


