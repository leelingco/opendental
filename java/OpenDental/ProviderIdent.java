//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package OpenDental;

import OpenDental.POut;
import OpenDentBusiness.ProviderSupplementalID;

/**
* Some insurance companies require special provider ID #s, and this table holds them.
*/
public class ProviderIdent   
{
    /**
    * Primary key.
    */
    public int ProviderIdentNum = new int();
    /**
    * FK to provider.ProvNum.  An ID only applies to one provider.
    */
    public int ProvNum = new int();
    /**
    * FK to carrier.ElectID  aka Electronic ID. An ID only applies to one insurance carrier.
    */
    public String PayorID = new String();
    /**
    * Enum:ProviderSupplementalID
    */
    public ProviderSupplementalID SuppIDType = ProviderSupplementalID.BlueCross;
    /**
    * The number assigned by the ins carrier.
    */
    public String IDNumber = new String();
    /**
    * 
    */
    public void update() throws Exception {
        String command = "UPDATE providerident SET " + "ProvNum = '" + POut.pInt(ProvNum) + "'" + ",PayorID = '" + POut.pString(PayorID) + "'" + ",SuppIDType = '" + POut.pInt(((Enum)SuppIDType).ordinal()) + "'" + ",IDNumber = '" + POut.pString(IDNumber) + "'" + " WHERE ProviderIdentNum = '" + POut.pInt(ProviderIdentNum) + "'";
        General.NonQ(command);
    }

    /**
    * 
    */
    public void insert() throws Exception {
        String command = "INSERT INTO providerident (ProvNum,PayorID,SuppIDType,IDNumber" + ") VALUES (" + "'" + POut.pInt(ProvNum) + "', " + "'" + POut.pString(PayorID) + "', " + "'" + POut.pInt(((Enum)SuppIDType).ordinal()) + "', " + "'" + POut.pString(IDNumber) + "')";
        //MessageBox.Show(command);
        General.NonQ(command);
    }

    /**
    * 
    */
    public void delete() throws Exception {
        String command = "DELETE FROM providerident " + "WHERE ProviderIdentNum = " + POut.pInt(ProviderIdentNum);
        General.NonQ(command);
    }

}


