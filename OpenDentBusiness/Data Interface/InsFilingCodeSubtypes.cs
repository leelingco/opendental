﻿using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Reflection;
using OpenDentBusiness.DataAccess;

namespace OpenDentBusiness {
	///<summary></summary>
	public class InsFilingCodeSubtypes {
		///<summary></summary>
		public static DataTable RefreshCache() {
			//No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
			string c="SELECT * FROM insfilingcodesubtype ORDER BY Descript";
			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),c);
			table.TableName="InsFilingCodeSubtype";
			FillCache(table);
			return table;
		}

		public static void FillCache(DataTable table) {
			//No need to check RemotingRole; no call to db.
			InsFilingCodeSubtypeC.Listt=new List<InsFilingCodeSubtype>();
			InsFilingCodeSubtype insFilingCodeSubtype;
			for(int i=0;i<table.Rows.Count;i++) {
				insFilingCodeSubtype=new InsFilingCodeSubtype();
				insFilingCodeSubtype.IsNew=false;
				insFilingCodeSubtype.InsFilingCodeSubtypeNum=PIn.PInt(table.Rows[i][0].ToString());
				insFilingCodeSubtype.InsFilingCodeNum=PIn.PInt(table.Rows[i][1].ToString());
				insFilingCodeSubtype.Descript=PIn.PString(table.Rows[i][2].ToString());
				InsFilingCodeSubtypeC.Listt.Add(insFilingCodeSubtype);
			}
		}

		///<Summary>Gets one InsFilingCodeSubtype from the database.</Summary>
		public static InsFilingCodeSubtype CreateObject(long insFilingCodeSubtypeNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<InsFilingCodeSubtype>(MethodBase.GetCurrentMethod(),insFilingCodeSubtypeNum);
			}
			return DataObjectFactory<InsFilingCodeSubtype>.CreateObject(insFilingCodeSubtypeNum);
		}

		///<summary></summary>
		public static long WriteObject(InsFilingCodeSubtype insFilingCodeSubtype) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				insFilingCodeSubtype.InsFilingCodeSubtypeNum=Meth.GetInt(MethodBase.GetCurrentMethod(),insFilingCodeSubtype);
				return insFilingCodeSubtype.InsFilingCodeSubtypeNum;
			}
			DataObjectFactory<InsFilingCodeSubtype>.WriteObject(insFilingCodeSubtype);
			return insFilingCodeSubtype.InsFilingCodeSubtypeNum;
		}

		///<summary>Surround with try/catch</summary>
		public static void Delete(long insFilingCodeSubtypeNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),insFilingCodeSubtypeNum);
				return;
			}
			string command="SELECT COUNT(*) FROM insplan WHERE FilingCodeSubtype="+POut.PInt(insFilingCodeSubtypeNum);
			if(Db.GetScalar(command) != "0") {
				throw new ApplicationException(Lans.g("InsFilingCodeSubtype","Already in use by insplans."));
			}
			DataObjectFactory<InsFilingCodeSubtype>.DeleteObject(insFilingCodeSubtypeNum);
		}

		public static List<InsFilingCodeSubtype> GetForInsFilingCode(long insFilingCodeNum) {
			List <InsFilingCodeSubtype> insFilingCodeSubtypes=new List<InsFilingCodeSubtype>();
			for(int i=0;i<InsFilingCodeSubtypeC.Listt.Count;i++){
				if(InsFilingCodeSubtypeC.Listt[i].InsFilingCodeNum==insFilingCodeNum){
					insFilingCodeSubtypes.Add(InsFilingCodeSubtypeC.Listt[i]);
				}
			}
			return insFilingCodeSubtypes;
		}

		public static void DeleteForInsFilingCode(long insFilingCodeNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),insFilingCodeNum);
				return;
			}
			string command="DELETE FROM insfilingcodesubtype "+
				"WHERE InsFilingCodeNum="+POut.PInt(insFilingCodeNum);
			Db.NonQ(command);
		}

	}
}