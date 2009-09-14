using System;
using System.Collections;
using System.Data;
using System.Reflection;

namespace OpenDentBusiness{
	///<summary></summary>
	public class RxDefs {

		///<summary></summary>
		public static RxDef[] Refresh() {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<RxDef[]>(MethodBase.GetCurrentMethod());
			}
			string command="SELECT * FROM rxdef ORDER BY Drug";
			DataTable table=Db.GetTable(command);
			RxDef[] List=new RxDef[table.Rows.Count];
			for(int i=0;i<table.Rows.Count;i++) {
				List[i]=new RxDef();
				List[i].RxDefNum    = PIn.PLong(table.Rows[i][0].ToString());
				List[i].Drug        = PIn.PString(table.Rows[i][1].ToString());
				List[i].Sig         = PIn.PString(table.Rows[i][2].ToString());
				List[i].Disp        = PIn.PString(table.Rows[i][3].ToString());
				List[i].Refills     = PIn.PString(table.Rows[i][4].ToString());
				List[i].Notes       = PIn.PString(table.Rows[i][5].ToString());
				List[i].IsControlled= PIn.PBool  (table.Rows[i][6].ToString());
			}
			return List;
		}

		///<summary></summary>
		public static void Update(RxDef def) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),def);
				return;
			}
			string command= "UPDATE rxdef SET " 
				+"Drug = '"       +POut.PString(def.Drug)+"'"
				+",Sig = '"       +POut.PString(def.Sig)+"'"
				+",Disp = '"      +POut.PString(def.Disp)+"'"
				+",Refills = '"   +POut.PString(def.Refills)+"'"
				+",Notes = '"     +POut.PString(def.Notes)+"'"
				+",IsControlled='"+POut.PBool  (def.IsControlled)+"'"
				+" WHERE RxDefNum = '" +POut.PLong(def.RxDefNum)+"'";
			Db.NonQ(command);
		}

		///<summary></summary>
		public static long Insert(RxDef def) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				def.RxDefNum=Meth.GetInt(MethodBase.GetCurrentMethod(),def);
				return def.RxDefNum;
			}
			if(PrefC.RandomKeys) {
				def.RxDefNum=ReplicationServers.GetKey("rxdef","RxDefNum");
			}
			string command="INSERT INTO rxdef (";
			if(PrefC.RandomKeys) {
				command+="RxDefNum,";
			}
			command+="Drug,Sig,Disp,Refills,Notes,IsControlled) VALUES(";
			if(PrefC.RandomKeys) {
				command+=POut.PLong(def.RxDefNum)+", ";
			}
			command+=
				 "'"+POut.PString(def.Drug)+"', "
				+"'"+POut.PString(def.Sig)+"', "
				+"'"+POut.PString(def.Disp)+"', "
				+"'"+POut.PString(def.Refills)+"', "
				+"'"+POut.PString(def.Notes)+"', "
				+"'"+POut.PBool(def.IsControlled)+"')";
			if(PrefC.RandomKeys) {
				Db.NonQ(command);
			}
			else{
				def.RxDefNum=Db.NonQ(command,true);
			}
			return def.RxDefNum;
		}

		///<summary>Also deletes all RxAlerts that were attached.</summary>
		public static void Delete(RxDef def) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),def);
				return;
			}
			string command="DELETE FROM rxalert WHERE RxDefNum="+POut.PLong(def.RxDefNum);
			Db.NonQ(command);
			command= "DELETE FROM rxdef WHERE RxDefNum = "+POut.PLong(def.RxDefNum);
			Db.NonQ(command);
		}




	
	
	
	}

	

	


}













