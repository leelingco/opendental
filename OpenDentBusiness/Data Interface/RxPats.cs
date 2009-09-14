using System;
using System.Collections;
using System.Data;
using System.Reflection;

namespace OpenDentBusiness{
	///<summary></summary>
	public class RxPats {
		/*
		///<summary></summary>
		public static RxPat[] Refresh(int patNum) {
			string command="SELECT * FROM rxpat"
				+" WHERE PatNum = '"+POut.PInt(patNum)+"'"
				+" ORDER BY RxDate";
			DataTable table=Db.GetTable(command);
			RxPat[] List=new RxPat[table.Rows.Count];
			for(int i=0;i<table.Rows.Count;i++) {
				List[i]=new RxPat();
				List[i].RxNum      = PIn.PInt(table.Rows[i][0].ToString());
				List[i].PatNum     = PIn.PInt(table.Rows[i][1].ToString());
				List[i].RxDate     = PIn.PDate(table.Rows[i][2].ToString());
				List[i].Drug       = PIn.PString(table.Rows[i][3].ToString());
				List[i].Sig        = PIn.PString(table.Rows[i][4].ToString());
				List[i].Disp       = PIn.PString(table.Rows[i][5].ToString());
				List[i].Refills    = PIn.PString(table.Rows[i][6].ToString());
				List[i].ProvNum    = PIn.PInt(table.Rows[i][7].ToString());
				List[i].Notes      = PIn.PString(table.Rows[i][8].ToString());
			}
			return List;
		}*/

		///<summary></summary>
		public static RxPat GetRx(long rxNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<RxPat>(MethodBase.GetCurrentMethod(),rxNum);
			}
			string command="SELECT * FROM rxpat"
				+" WHERE RxNum = "+POut.PLong(rxNum);
			DataTable table=Db.GetTable(command);
			RxPat rx=new RxPat();
			rx.RxNum       = PIn.PLong(table.Rows[0][0].ToString());
			rx.PatNum      = PIn.PLong(table.Rows[0][1].ToString());
			rx.RxDate      = PIn.PDate(table.Rows[0][2].ToString());
			rx.Drug        = PIn.PString(table.Rows[0][3].ToString());
			rx.Sig         = PIn.PString(table.Rows[0][4].ToString());
			rx.Disp        = PIn.PString(table.Rows[0][5].ToString());
			rx.Refills     = PIn.PString(table.Rows[0][6].ToString());
			rx.ProvNum     = PIn.PLong(table.Rows[0][7].ToString());
			rx.Notes       = PIn.PString(table.Rows[0][8].ToString());
			rx.PharmacyNum = PIn.PLong   (table.Rows[0][9].ToString());
			rx.IsControlled= PIn.PBool  (table.Rows[0][10].ToString());
			return rx;
		}

		///<summary></summary>
		public static void Update(RxPat rx) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),rx);
				return;
			}
			string command= "UPDATE rxpat SET " 
				+ "PatNum = '"      +POut.PLong   (rx.PatNum)+"'"
				+ ",RxDate = "      +POut.PDate  (rx.RxDate)
				+ ",Drug = '"       +POut.PString(rx.Drug)+"'"
				+ ",Sig = '"        +POut.PString(rx.Sig)+"'"
				+ ",Disp = '"       +POut.PString(rx.Disp)+"'"
				+ ",Refills = '"    +POut.PString(rx.Refills)+"'"
				+ ",ProvNum = '"    +POut.PLong   (rx.ProvNum)+"'"
				+ ",Notes = '"      +POut.PString(rx.Notes)+"'"
				+ ",PharmacyNum = '"+POut.PLong   (rx.PharmacyNum)+"'"
				+ ",IsControlled='" +POut.PBool  (rx.IsControlled)+"'"
				+" WHERE RxNum = '" +POut.PLong   (rx.RxNum)+"'";
			Db.NonQ(command);
		}

		///<summary></summary>
		public static long Insert(RxPat rx) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				rx.RxNum=Meth.GetInt(MethodBase.GetCurrentMethod(),rx);
				return rx.RxNum;
			}
			if(PrefC.RandomKeys) {
				rx.RxNum=ReplicationServers.GetKey("rxpat","RxNum");
			}
			string command="INSERT INTO rxpat (";
			if(PrefC.RandomKeys) {
				command+="RxNum,";
			}
			command+="PatNum,RxDate,Drug,Sig,Disp,Refills,ProvNum,Notes,PharmacyNum,IsControlled) VALUES(";
			if(PrefC.RandomKeys) {
				command+="'"+POut.PLong(rx.RxNum)+"', ";
			}
			command+=
				 "'"+POut.PLong   (rx.PatNum)+"', "
				+POut.PDate  (rx.RxDate)+", "
				+"'"+POut.PString(rx.Drug)+"', "
				+"'"+POut.PString(rx.Sig)+"', "
				+"'"+POut.PString(rx.Disp)+"', "
				+"'"+POut.PString(rx.Refills)+"', "
				+"'"+POut.PLong   (rx.ProvNum)+"', "
				+"'"+POut.PString(rx.Notes)+"', "
				+"'"+POut.PLong   (rx.PharmacyNum)+"', "
				+"'"+POut.PBool  (rx.IsControlled)+"')";
			if(PrefC.RandomKeys) {
				Db.NonQ(command);
			}
			else{
				rx.RxNum=Db.NonQ(command,true);
			}
			return rx.RxNum;
		}

		///<summary></summary>
		public static void Delete(long rxNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),rxNum);
				return;
			}
			string command= "DELETE FROM rxpat WHERE RxNum = '"+POut.PLong(rxNum)+"'";
			Db.NonQ(command);
		}



	
	

		
	}

	


}













