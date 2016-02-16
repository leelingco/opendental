//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.ProviderIdent;
import OpenDentBusiness.ProviderSupplementalID;

/*=========================================================================================
	=================================== class ProviderIdents ======================================*/
/**
* Refreshed with local data.
*/
public class ProviderIdents   
{
    /**
    * This is the list of all id's for all providers. They are extracted as needed.
    */
    private static ProviderIdent[] List = new ProviderIdent[]();
    /**
    * 
    */
    public static void refresh() throws Exception {
        String command = "SELECT * from providerident";
        DataTable table = General.GetTable(command);
        List = new ProviderIdent[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            List[i] = new ProviderIdent();
            List[i].ProviderIdentNum = PIn.PInt(table.Rows[i][0].ToString());
            List[i].ProvNum = PIn.PInt(table.Rows[i][1].ToString());
            List[i].PayorID = PIn.PString(table.Rows[i][2].ToString());
            List[i].SuppIDType = (ProviderSupplementalID)PIn.PInt(table.Rows[i][3].ToString());
            List[i].IDNumber = PIn.PString(table.Rows[i][4].ToString());
        }
    }

    /**
    * Gets all supplemental identifiers that have been attached to this provider. Used in the provider edit window.
    */
    public static ProviderIdent[] getForProv(int provNum) throws Exception {
        ArrayList arrayL = new ArrayList();
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].ProvNum == provNum)
            {
                arrayL.Add(List[i]);
            }
             
        }
        ProviderIdent[] ForProv = new ProviderIdent[arrayL.Count];
        for (int i = 0;i < arrayL.Count;i++)
        {
            ForProv[i] = (ProviderIdent)arrayL[i];
        }
        return ForProv;
    }

    /**
    * Gets all supplemental identifiers that have been attached to this provider and for this particular payorID.  Called from X12 when creating a claim file.  Also used now on printed claims.
    */
    public static ProviderIdent[] getForPayor(int provNum, String payorID) throws Exception {
        ArrayList arrayL = new ArrayList();
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].ProvNum == provNum && StringSupport.equals(List[i].PayorID, payorID))
            {
                arrayL.Add(List[i]);
            }
             
        }
        ProviderIdent[] ForPayor = new ProviderIdent[arrayL.Count];
        for (int i = 0;i < arrayL.Count;i++)
        {
            ForPayor[i] = (ProviderIdent)arrayL[i];
        }
        return ForPayor;
    }

    /**
    * Called from FormProvEdit if cancel on a new provider.
    */
    public static void deleteAllForProv(int provNum) throws Exception {
        String command = "DELETE from providerident WHERE provnum = '" + POut.pInt(provNum) + "'";
        General.NonQ(command);
    }

    /**
    * 
    */
    public static boolean identExists(ProviderSupplementalID type, int provNum, String payorID) throws Exception {
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].ProvNum == provNum && List[i].SuppIDType == type && StringSupport.equals(List[i].PayorID, payorID))
            {
                return true;
            }
             
        }
        return false;
    }

}


