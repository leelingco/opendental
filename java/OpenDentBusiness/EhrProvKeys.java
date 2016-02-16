//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:42 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrProvKey;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrProvKeys   
{
    /**
    * 
    */
    public static List<EhrProvKey> refreshForFam(long guarantor) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrProvKey>>GetObject(MethodBase.GetCurrentMethod(), guarantor);
        }
         
        String command = "SELECT ehrprovkey.* FROM ehrprovkey,patient " + "WHERE ehrprovkey.PatNum=patient.PatNum " + "AND patient.Guarantor=" + POut.long(guarantor) + " " + "GROUP BY ehrprovkey.EhrProvKeyNum " + "ORDER BY ehrprovkey.LName,ehrprovkey.FName";
        return Crud.EhrProvKeyCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(EhrProvKey ehrProvKey) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrProvKey.EhrProvKeyNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrProvKey);
            return ehrProvKey.EhrProvKeyNum;
        }
         
        return Crud.EhrProvKeyCrud.Insert(ehrProvKey);
    }

    /**
    * 
    */
    public static void update(EhrProvKey ehrProvKey) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrProvKey);
            return ;
        }
         
        Crud.EhrProvKeyCrud.Update(ehrProvKey);
    }

    /**
    * 
    */
    public static void delete(long ehrProvKeyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrProvKeyNum);
            return ;
        }
         
        String command = "DELETE FROM ehrprovkey WHERE EhrProvKeyNum = " + POut.long(ehrProvKeyNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		
		///<summary>Gets one EhrProvKey from the db.</summary>
		public static EhrProvKey GetOne(long ehrProvKeyNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrProvKey>(MethodBase.GetCurrentMethod(),ehrProvKeyNum);
			}
			return Crud.EhrProvKeyCrud.SelectOne(ehrProvKeyNum);
		}
		
		*/