//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cpt;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Cpts   
{
    public static List<Cpt> getBySearchText(String searchText) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Cpt>>GetObject(MethodBase.GetCurrentMethod(), searchText);
        }
         
        String[] searchTokens = searchText.Split(' ');
        String command = "SELECT * FROM cpt ";
        for (int i = 0;i < searchTokens.Length;i++)
        {
            command += (i == 0 ? "WHERE " : "AND ") + "(CptCode LIKE '%" + POut.String(searchTokens[i]) + "%' OR Description LIKE '%" + POut.String(searchTokens[i]) + "%') ";
        }
        return Crud.CptCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(Cpt cpt) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            cpt.CptNum = Meth.GetLong(MethodBase.GetCurrentMethod(), cpt);
            return cpt.CptNum;
        }
         
        return Crud.CptCrud.Insert(cpt);
    }

    public static List<String> getAllCodes() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        List<String> retVal = new List<String>();
        String command = "SELECT CptCode FROM cpt";
        DataTable table = DataCore.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(table.Rows[i][0].ToString());
        }
        return retVal;
    }

    /**
    * Gets one Cpt object directly from the database by CptCode.  If code does not exist, returns null.
    */
    public static Cpt getByCode(String cptCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Cpt>GetObject(MethodBase.GetCurrentMethod(), cptCode);
        }
         
        String command = "SELECT * FROM cpt WHERE CptCode='" + POut.string(cptCode) + "'";
        return Crud.CptCrud.SelectOne(command);
    }

    /**
    * Directly from db.
    */
    public static boolean codeExists(String cptCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), cptCode);
        }
         
        String command = "SELECT COUNT(*) FROM cpt WHERE CptCode = '" + POut.string(cptCode) + "'";
        String count = Db.getCount(command);
        if (StringSupport.equals(count, "0"))
        {
            return false;
        }
         
        return true;
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Cpt> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<Cpt>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM cpt WHERE PatNum = "+POut.Long(patNum);
			return Crud.CptCrud.SelectMany(command);
		}
		///<summary>Gets one Cpt from the db.</summary>
		public static Cpt GetOne(long cptNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<Cpt>(MethodBase.GetCurrentMethod(),cptNum);
			}
			return Crud.CptCrud.SelectOne(cptNum);
		}
		///<summary></summary>
		public static void Update(Cpt cpt){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),cpt);
				return;
			}
			Crud.CptCrud.Update(cpt);
		}
		///<summary></summary>
		public static void Delete(long cptNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),cptNum);
				return;
			}
			string command= "DELETE FROM cpt WHERE CptNum = "+POut.Long(cptNum);
			Db.NonQ(command);
		}
		*/