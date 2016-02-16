//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;


/**
* 
*/
public class ToothGridCols   
{
}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<ToothGridCol> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<ToothGridCol>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM toothgridcol WHERE PatNum = "+POut.Long(patNum);
			return Crud.ToothGridColCrud.SelectMany(command);
		}
		///<summary>Gets one ToothGridCol from the db.</summary>
		public static ToothGridCol GetOne(long toothGridColNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<ToothGridCol>(MethodBase.GetCurrentMethod(),toothGridColNum);
			}
			return Crud.ToothGridColCrud.SelectOne(toothGridColNum);
		}
		///<summary></summary>
		public static long Insert(ToothGridCol toothGridCol){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				toothGridCol.ToothGridColNum=Meth.GetLong(MethodBase.GetCurrentMethod(),toothGridCol);
				return toothGridCol.ToothGridColNum;
			}
			return Crud.ToothGridColCrud.Insert(toothGridCol);
		}
		///<summary></summary>
		public static void Update(ToothGridCol toothGridCol){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),toothGridCol);
				return;
			}
			Crud.ToothGridColCrud.Update(toothGridCol);
		}
		///<summary></summary>
		public static void Delete(long toothGridColNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),toothGridColNum);
				return;
			}
			string command= "DELETE FROM toothgridcol WHERE ToothGridColNum = "+POut.Long(toothGridColNum);
			Db.NonQ(command);
		}
		*/