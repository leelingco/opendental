//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ScreenPat;

/**
* 
*/
public class ScreenPats   
{
    /**
    * 
    */
    public static long insert(ScreenPat screenPat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            screenPat.ScreenPatNum = Meth.GetLong(MethodBase.GetCurrentMethod(), screenPat);
            return screenPat.ScreenPatNum;
        }
         
        return Crud.ScreenPatCrud.Insert(screenPat);
    }

    /**
    * 
    */
    public static List<ScreenPat> getForScreenGroup(long screenGroupNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ScreenPat>>GetObject(MethodBase.GetCurrentMethod(), screenGroupNum);
        }
         
        String command = "SELECT * FROM screenpat WHERE ScreenPatNum IN (SELECT ScreenPatNum FROM screengroup WHERE ScreenGroupNum=" + POut.long(screenGroupNum) + ")";
        return Crud.ScreenPatCrud.SelectMany(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<ScreenPat> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<ScreenPat>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM screenpat WHERE PatNum = "+POut.Long(patNum);
			return Crud.ScreenPatCrud.SelectMany(command);
		}
		///<summary>Gets one ScreenPat from the db.</summary>
		public static ScreenPat GetOne(long screenPatNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<ScreenPat>(MethodBase.GetCurrentMethod(),screenPatNum);
			}
			return Crud.ScreenPatCrud.SelectOne(screenPatNum);
		}
		///<summary></summary>
		public static void Update(ScreenPat screenPat){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),screenPat);
				return;
			}
			Crud.ScreenPatCrud.Update(screenPat);
		}
		///<summary></summary>
		public static void Delete(long screenPatNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),screenPatNum);
				return;
			}
			string command= "DELETE FROM screenpat WHERE ScreenPatNum = "+POut.Long(screenPatNum);
			Db.NonQ(command);
		}
		*/