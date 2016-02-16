//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrAptObs;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrAptObses   
{
    /**
    * 
    */
    public static List<EhrAptObs> refresh(long aptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrAptObs>>GetObject(MethodBase.GetCurrentMethod(), aptNum);
        }
         
        String command = "SELECT * FROM ehraptobs WHERE AptNum = " + POut.long(aptNum);
        return Crud.EhrAptObsCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(EhrAptObs ehrAptObs) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrAptObs.EhrAptObsNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrAptObs);
            return ehrAptObs.EhrAptObsNum;
        }
         
        return Crud.EhrAptObsCrud.Insert(ehrAptObs);
    }

    /**
    * 
    */
    public static void update(EhrAptObs ehrAptObs) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrAptObs);
            return ;
        }
         
        Crud.EhrAptObsCrud.Update(ehrAptObs);
    }

    /**
    * 
    */
    public static void delete(long ehrAptObsNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrAptObsNum);
            return ;
        }
         
        String command = "DELETE FROM ehraptobs WHERE EhrAptObsNum = " + POut.long(ehrAptObsNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary>Gets one EhrAptObs from the db.</summary>
		public static EhrAptObs GetOne(long ehrAptObsNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrAptObs>(MethodBase.GetCurrentMethod(),ehrAptObsNum);
			}
			return Crud.EhrAptObsCrud.SelectOne(ehrAptObsNum);
		}
		
		*/