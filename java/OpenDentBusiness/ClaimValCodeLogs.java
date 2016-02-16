//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ClaimValCodeLog;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class ClaimValCodeLogs   
{
    public static double getValAmountTotal(long claimNum, String valCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<double>GetObject(MethodBase.GetCurrentMethod(), claimNum, valCode);
        }
         
        //double total = 0;
        String command = "SELECT SUM(ValAmount) FROM claimvalcodelog WHERE ClaimNum=" + POut.long(claimNum) + " AND ValCode='" + POut.string(valCode) + "'";
        return PIn.double(Db.getScalar(command));
    }

    //DataTable table=Db.GetTable(command);
    //for(int i=0;i<table.Rows.Count;i++){
    //	total+=PIn.Double(table.Rows[i][4].ToString());
    //}
    //return total;
    public static List<ClaimValCodeLog> getForClaim(long claimNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimValCodeLog>>GetObject(MethodBase.GetCurrentMethod(), claimNum);
        }
         
        String command = "SELECT * FROM claimvalcodelog WHERE ClaimNum=" + POut.long(claimNum);
        return Crud.ClaimValCodeLogCrud.SelectMany(command);
    }

    public static void updateList(List<ClaimValCodeLog> vCodes) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), vCodes);
            return ;
        }
         
        for (int i = 0;i < vCodes.Count;i++)
        {
            ClaimValCodeLog vc = vCodes[i];
            if (vc.ClaimValCodeLogNum == 0)
            {
                Crud.ClaimValCodeLogCrud.Insert(vc);
            }
            else
            {
                Crud.ClaimValCodeLogCrud.Update(vc);
            } 
        }
    }

}


