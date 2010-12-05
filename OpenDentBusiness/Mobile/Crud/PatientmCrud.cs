//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.Drawing;

namespace OpenDentBusiness.Mobile.Crud{
	internal class PatientmCrud {
		///<summary>Gets one Patientm object from the database using primaryKey1(CustomerNum) and primaryKey2.  Returns null if not found.</summary>
		internal static Patientm SelectOne(long customerNum,long patNum){
			string command="SELECT * FROM patientm "
				+"WHERE CustomerNum = "+POut.Long(customerNum)+" AND PatNum = "+POut.Long(patNum)+" LIMIT 1";
			List<Patientm> list=TableToList(Db.GetTable(command));
			if(list.Count==0) {
				return null;
			}
			return list[0];
		}

		///<summary>Gets one Patientm object from the database using a query.</summary>
		internal static Patientm SelectOne(string command){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n"+command);
			}
			List<Patientm> list=TableToList(Db.GetTable(command));
			if(list.Count==0) {
				return null;
			}
			return list[0];
		}

		///<summary>Gets a list of Patientm objects from the database using a query.</summary>
		internal static List<Patientm> SelectMany(string command){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n"+command);
			}
			List<Patientm> list=TableToList(Db.GetTable(command));
			return list;
		}

		///<summary>Converts a DataTable to a list of objects.</summary>
		internal static List<Patientm> TableToList(DataTable table){
			List<Patientm> retVal=new List<Patientm>();
			Patientm patientm;
			for(int i=0;i<table.Rows.Count;i++) {
				patientm=new Patientm();
				patientm.CustomerNum        = PIn.Long  (table.Rows[i]["CustomerNum"].ToString());
				patientm.PatNum             = PIn.Long  (table.Rows[i]["PatNum"].ToString());
				patientm.LName              = PIn.String(table.Rows[i]["LName"].ToString());
				patientm.FName              = PIn.String(table.Rows[i]["FName"].ToString());
				patientm.MiddleI            = PIn.String(table.Rows[i]["MiddleI"].ToString());
				patientm.Preferred          = PIn.String(table.Rows[i]["Preferred"].ToString());
				patientm.PatStatus          = (PatientStatus)PIn.Int(table.Rows[i]["PatStatus"].ToString());
				patientm.Gender             = (PatientGender)PIn.Int(table.Rows[i]["Gender"].ToString());
				patientm.Position           = (PatientPosition)PIn.Int(table.Rows[i]["Position"].ToString());
				patientm.Birthdate          = PIn.Date  (table.Rows[i]["Birthdate"].ToString());
				patientm.Address            = PIn.String(table.Rows[i]["Address"].ToString());
				patientm.Address2           = PIn.String(table.Rows[i]["Address2"].ToString());
				patientm.City               = PIn.String(table.Rows[i]["City"].ToString());
				patientm.State              = PIn.String(table.Rows[i]["State"].ToString());
				patientm.Zip                = PIn.String(table.Rows[i]["Zip"].ToString());
				patientm.HmPhone            = PIn.String(table.Rows[i]["HmPhone"].ToString());
				patientm.WkPhone            = PIn.String(table.Rows[i]["WkPhone"].ToString());
				patientm.WirelessPhone      = PIn.String(table.Rows[i]["WirelessPhone"].ToString());
				patientm.Guarantor          = PIn.Long  (table.Rows[i]["Guarantor"].ToString());
				patientm.Email              = PIn.String(table.Rows[i]["Email"].ToString());
				patientm.AddrNote           = PIn.String(table.Rows[i]["AddrNote"].ToString());
				patientm.ClinicNum          = PIn.Long  (table.Rows[i]["ClinicNum"].ToString());
				patientm.PreferContactMethod= (ContactMethod)PIn.Int(table.Rows[i]["PreferContactMethod"].ToString());
				retVal.Add(patientm);
			}
			return retVal;
		}

		///<summary>Usually set useExistingPK=true.  Inserts one Patientm into the database.</summary>
		internal static long Insert(Patientm patientm,bool useExistingPK){
			if(!useExistingPK) {
				patientm.PatNum=ReplicationServers.GetKey("patientm","PatNum");
			}
			string command="INSERT INTO patientm (";
			command+="PatNum,";
			command+="CustomerNum,LName,FName,MiddleI,Preferred,PatStatus,Gender,Position,Birthdate,Address,Address2,City,State,Zip,HmPhone,WkPhone,WirelessPhone,Guarantor,Email,AddrNote,ClinicNum,PreferContactMethod) VALUES(";
			command+=POut.Long(patientm.PatNum)+",";
			command+=
				     POut.Long  (patientm.CustomerNum)+","
				+"'"+POut.String(patientm.LName)+"',"
				+"'"+POut.String(patientm.FName)+"',"
				+"'"+POut.String(patientm.MiddleI)+"',"
				+"'"+POut.String(patientm.Preferred)+"',"
				+    POut.Int   ((int)patientm.PatStatus)+","
				+    POut.Int   ((int)patientm.Gender)+","
				+    POut.Int   ((int)patientm.Position)+","
				+    POut.Date  (patientm.Birthdate)+","
				+"'"+POut.String(patientm.Address)+"',"
				+"'"+POut.String(patientm.Address2)+"',"
				+"'"+POut.String(patientm.City)+"',"
				+"'"+POut.String(patientm.State)+"',"
				+"'"+POut.String(patientm.Zip)+"',"
				+"'"+POut.String(patientm.HmPhone)+"',"
				+"'"+POut.String(patientm.WkPhone)+"',"
				+"'"+POut.String(patientm.WirelessPhone)+"',"
				+    POut.Long  (patientm.Guarantor)+","
				+"'"+POut.String(patientm.Email)+"',"
				+"'"+POut.String(patientm.AddrNote)+"',"
				+    POut.Long  (patientm.ClinicNum)+","
				+    POut.Int   ((int)patientm.PreferContactMethod)+")";
			Db.NonQ(command);//There is no autoincrement in the mobile server.
			return patientm.PatNum;
		}

		///<summary>Updates one Patientm in the database.</summary>
		internal static void Update(Patientm patientm){
			string command="UPDATE patientm SET "
				+"LName              = '"+POut.String(patientm.LName)+"', "
				+"FName              = '"+POut.String(patientm.FName)+"', "
				+"MiddleI            = '"+POut.String(patientm.MiddleI)+"', "
				+"Preferred          = '"+POut.String(patientm.Preferred)+"', "
				+"PatStatus          =  "+POut.Int   ((int)patientm.PatStatus)+", "
				+"Gender             =  "+POut.Int   ((int)patientm.Gender)+", "
				+"Position           =  "+POut.Int   ((int)patientm.Position)+", "
				+"Birthdate          =  "+POut.Date  (patientm.Birthdate)+", "
				+"Address            = '"+POut.String(patientm.Address)+"', "
				+"Address2           = '"+POut.String(patientm.Address2)+"', "
				+"City               = '"+POut.String(patientm.City)+"', "
				+"State              = '"+POut.String(patientm.State)+"', "
				+"Zip                = '"+POut.String(patientm.Zip)+"', "
				+"HmPhone            = '"+POut.String(patientm.HmPhone)+"', "
				+"WkPhone            = '"+POut.String(patientm.WkPhone)+"', "
				+"WirelessPhone      = '"+POut.String(patientm.WirelessPhone)+"', "
				+"Guarantor          =  "+POut.Long  (patientm.Guarantor)+", "
				+"Email              = '"+POut.String(patientm.Email)+"', "
				+"AddrNote           = '"+POut.String(patientm.AddrNote)+"', "
				+"ClinicNum          =  "+POut.Long  (patientm.ClinicNum)+", "
				+"PreferContactMethod=  "+POut.Int   ((int)patientm.PreferContactMethod)+" "
				+"WHERE CustomerNum = "+POut.Long(patientm.CustomerNum)+" AND PatNum = "+POut.Long(patientm.PatNum)+" LIMIT 1";
			Db.NonQ(command);
		}

		///<summary>Deletes one Patientm from the database.</summary>
		internal static void Delete(long customerNum,long patNum){
			string command="DELETE FROM patientm "
				+"WHERE CustomerNum = "+POut.Long(customerNum)+" AND PatNum = "+POut.Long(patNum)+" LIMIT 1";
			Db.NonQ(command);
		}

	}
}