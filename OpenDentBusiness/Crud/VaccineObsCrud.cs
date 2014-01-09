//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.Drawing;

namespace OpenDentBusiness.Crud{
	public class VaccineObsCrud {
		///<summary>Gets one VaccineObs object from the database using the primary key.  Returns null if not found.</summary>
		public static VaccineObs SelectOne(long vaccineObsNum){
			string command="SELECT * FROM vaccineobs "
				+"WHERE VaccineObsNum = "+POut.Long(vaccineObsNum);
			List<VaccineObs> list=TableToList(Db.GetTable(command));
			if(list.Count==0) {
				return null;
			}
			return list[0];
		}

		///<summary>Gets one VaccineObs object from the database using a query.</summary>
		public static VaccineObs SelectOne(string command){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n"+command);
			}
			List<VaccineObs> list=TableToList(Db.GetTable(command));
			if(list.Count==0) {
				return null;
			}
			return list[0];
		}

		///<summary>Gets a list of VaccineObs objects from the database using a query.</summary>
		public static List<VaccineObs> SelectMany(string command){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n"+command);
			}
			List<VaccineObs> list=TableToList(Db.GetTable(command));
			return list;
		}

		///<summary>Converts a DataTable to a list of objects.</summary>
		public static List<VaccineObs> TableToList(DataTable table){
			List<VaccineObs> retVal=new List<VaccineObs>();
			VaccineObs vaccineObs;
			for(int i=0;i<table.Rows.Count;i++) {
				vaccineObs=new VaccineObs();
				vaccineObs.VaccineObsNum     = PIn.Long  (table.Rows[i]["VaccineObsNum"].ToString());
				vaccineObs.VaccinePatNum     = PIn.Long  (table.Rows[i]["VaccinePatNum"].ToString());
				vaccineObs.ValType           = (VaccineObsType)PIn.Int(table.Rows[i]["ValType"].ToString());
				vaccineObs.IdentifyingCode   = (VaccineObsIdentifier)PIn.Int(table.Rows[i]["IdentifyingCode"].ToString());
				vaccineObs.ValReported       = PIn.String(table.Rows[i]["ValReported"].ToString());
				vaccineObs.ValCodeSystem     = (VaccineObsValCodeSystem)PIn.Int(table.Rows[i]["ValCodeSystem"].ToString());
				vaccineObs.VaccineObsNumGroup= PIn.Long  (table.Rows[i]["VaccineObsNumGroup"].ToString());
				vaccineObs.ValUnit           = PIn.String(table.Rows[i]["ValUnit"].ToString());
				vaccineObs.DateObs           = PIn.Date  (table.Rows[i]["DateObs"].ToString());
				vaccineObs.MethodCode        = PIn.String(table.Rows[i]["MethodCode"].ToString());
				retVal.Add(vaccineObs);
			}
			return retVal;
		}

		///<summary>Inserts one VaccineObs into the database.  Returns the new priKey.</summary>
		public static long Insert(VaccineObs vaccineObs){
			if(DataConnection.DBtype==DatabaseType.Oracle) {
				vaccineObs.VaccineObsNum=DbHelper.GetNextOracleKey("vaccineobs","VaccineObsNum");
				int loopcount=0;
				while(loopcount<100){
					try {
						return Insert(vaccineObs,true);
					}
					catch(Oracle.DataAccess.Client.OracleException ex){
						if(ex.Number==1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated")){
							vaccineObs.VaccineObsNum++;
							loopcount++;
						}
						else{
							throw ex;
						}
					}
				}
				throw new ApplicationException("Insert failed.  Could not generate primary key.");
			}
			else {
				return Insert(vaccineObs,false);
			}
		}

		///<summary>Inserts one VaccineObs into the database.  Provides option to use the existing priKey.</summary>
		public static long Insert(VaccineObs vaccineObs,bool useExistingPK){
			if(!useExistingPK && PrefC.RandomKeys) {
				vaccineObs.VaccineObsNum=ReplicationServers.GetKey("vaccineobs","VaccineObsNum");
			}
			string command="INSERT INTO vaccineobs (";
			if(useExistingPK || PrefC.RandomKeys) {
				command+="VaccineObsNum,";
			}
			command+="VaccinePatNum,ValType,IdentifyingCode,ValReported,ValCodeSystem,VaccineObsNumGroup,ValUnit,DateObs,MethodCode) VALUES(";
			if(useExistingPK || PrefC.RandomKeys) {
				command+=POut.Long(vaccineObs.VaccineObsNum)+",";
			}
			command+=
				     POut.Long  (vaccineObs.VaccinePatNum)+","
				+    POut.Int   ((int)vaccineObs.ValType)+","
				+    POut.Int   ((int)vaccineObs.IdentifyingCode)+","
				+"'"+POut.String(vaccineObs.ValReported)+"',"
				+    POut.Int   ((int)vaccineObs.ValCodeSystem)+","
				+    POut.Long  (vaccineObs.VaccineObsNumGroup)+","
				+"'"+POut.String(vaccineObs.ValUnit)+"',"
				+    POut.Date  (vaccineObs.DateObs)+","
				+"'"+POut.String(vaccineObs.MethodCode)+"')";
			if(useExistingPK || PrefC.RandomKeys) {
				Db.NonQ(command);
			}
			else {
				vaccineObs.VaccineObsNum=Db.NonQ(command,true);
			}
			return vaccineObs.VaccineObsNum;
		}

		///<summary>Updates one VaccineObs in the database.</summary>
		public static void Update(VaccineObs vaccineObs){
			string command="UPDATE vaccineobs SET "
				+"VaccinePatNum     =  "+POut.Long  (vaccineObs.VaccinePatNum)+", "
				+"ValType           =  "+POut.Int   ((int)vaccineObs.ValType)+", "
				+"IdentifyingCode   =  "+POut.Int   ((int)vaccineObs.IdentifyingCode)+", "
				+"ValReported       = '"+POut.String(vaccineObs.ValReported)+"', "
				+"ValCodeSystem     =  "+POut.Int   ((int)vaccineObs.ValCodeSystem)+", "
				+"VaccineObsNumGroup=  "+POut.Long  (vaccineObs.VaccineObsNumGroup)+", "
				+"ValUnit           = '"+POut.String(vaccineObs.ValUnit)+"', "
				+"DateObs           =  "+POut.Date  (vaccineObs.DateObs)+", "
				+"MethodCode        = '"+POut.String(vaccineObs.MethodCode)+"' "
				+"WHERE VaccineObsNum = "+POut.Long(vaccineObs.VaccineObsNum);
			Db.NonQ(command);
		}

		///<summary>Updates one VaccineObs in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.</summary>
		public static void Update(VaccineObs vaccineObs,VaccineObs oldVaccineObs){
			string command="";
			if(vaccineObs.VaccinePatNum != oldVaccineObs.VaccinePatNum) {
				if(command!=""){ command+=",";}
				command+="VaccinePatNum = "+POut.Long(vaccineObs.VaccinePatNum)+"";
			}
			if(vaccineObs.ValType != oldVaccineObs.ValType) {
				if(command!=""){ command+=",";}
				command+="ValType = "+POut.Int   ((int)vaccineObs.ValType)+"";
			}
			if(vaccineObs.IdentifyingCode != oldVaccineObs.IdentifyingCode) {
				if(command!=""){ command+=",";}
				command+="IdentifyingCode = "+POut.Int   ((int)vaccineObs.IdentifyingCode)+"";
			}
			if(vaccineObs.ValReported != oldVaccineObs.ValReported) {
				if(command!=""){ command+=",";}
				command+="ValReported = '"+POut.String(vaccineObs.ValReported)+"'";
			}
			if(vaccineObs.ValCodeSystem != oldVaccineObs.ValCodeSystem) {
				if(command!=""){ command+=",";}
				command+="ValCodeSystem = "+POut.Int   ((int)vaccineObs.ValCodeSystem)+"";
			}
			if(vaccineObs.VaccineObsNumGroup != oldVaccineObs.VaccineObsNumGroup) {
				if(command!=""){ command+=",";}
				command+="VaccineObsNumGroup = "+POut.Long(vaccineObs.VaccineObsNumGroup)+"";
			}
			if(vaccineObs.ValUnit != oldVaccineObs.ValUnit) {
				if(command!=""){ command+=",";}
				command+="ValUnit = '"+POut.String(vaccineObs.ValUnit)+"'";
			}
			if(vaccineObs.DateObs != oldVaccineObs.DateObs) {
				if(command!=""){ command+=",";}
				command+="DateObs = "+POut.Date(vaccineObs.DateObs)+"";
			}
			if(vaccineObs.MethodCode != oldVaccineObs.MethodCode) {
				if(command!=""){ command+=",";}
				command+="MethodCode = '"+POut.String(vaccineObs.MethodCode)+"'";
			}
			if(command==""){
				return;
			}
			command="UPDATE vaccineobs SET "+command
				+" WHERE VaccineObsNum = "+POut.Long(vaccineObs.VaccineObsNum);
			Db.NonQ(command);
		}

		///<summary>Deletes one VaccineObs from the database.</summary>
		public static void Delete(long vaccineObsNum){
			string command="DELETE FROM vaccineobs "
				+"WHERE VaccineObsNum = "+POut.Long(vaccineObsNum);
			Db.NonQ(command);
		}

	}
}