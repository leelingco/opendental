//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:36 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ApptField;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ApptFields   
{
    /**
    * Gets one ApptField from the db.
    */
    public static ApptField getOne(long apptFieldNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ApptField>GetObject(MethodBase.GetCurrentMethod(), apptFieldNum);
        }
         
        return Crud.ApptFieldCrud.SelectOne(apptFieldNum);
    }

    /**
    * 
    */
    public static long insert(ApptField apptField) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            apptField.ApptFieldNum = Meth.GetLong(MethodBase.GetCurrentMethod(), apptField);
            return apptField.ApptFieldNum;
        }
         
        return Crud.ApptFieldCrud.Insert(apptField);
    }

    /**
    * 
    */
    public static void update(ApptField apptField) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), apptField);
            return ;
        }
         
        Crud.ApptFieldCrud.Update(apptField);
    }

    /**
    * 
    */
    public static void delete(long apptFieldNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), apptFieldNum);
            return ;
        }
         
        String command = "DELETE FROM apptfield WHERE ApptFieldNum = " + POut.long(apptFieldNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<ApptField> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<ApptField>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM apptfield WHERE PatNum = "+POut.Long(patNum);
			return Crud.ApptFieldCrud.SelectMany(command);
		}
		
		*/