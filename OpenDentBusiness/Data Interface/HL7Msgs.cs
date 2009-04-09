﻿using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using OpenDentBusiness.DataAccess;

namespace OpenDentBusiness{
	///<summary></summary>
	public class HL7Msgs{
		/*
		///<summary></summary>
		public static DataTable RefreshCache(){
			string c="SELECT * from HL7Msg ORDER BY Description";
			DataTable table=General.GetTable(c);
			table.TableName="HL7Msg";
			FillCache(table);
			return table;
		}

		public static void FillCache(DataTable table){
			HL7MsgC.List=new HL7Msg[table.Rows.Count];
			for(int i=0;i<HL7MsgC.List.Length;i++){
				HL7MsgC.List[i]=new HL7Msg();
				HL7MsgC.List[i].IsNew=false;
				HL7MsgC.List[i].HL7MsgNum    = PIn.PInt   (table.Rows[i][0].ToString());
				HL7MsgC.List[i].Description= PIn.PString(table.Rows[i][1].ToString());
				HL7MsgC.List[i].Note       = PIn.PString(table.Rows[i][2].ToString());
			}
		}

		///<Summary>Gets one HL7Msg from the database.</Summary>
		public static HL7Msg CreateObject(int HL7MsgNum){
			return DataObjectFactory<HL7Msg>.CreateObject(HL7MsgNum);
		}*/

		public static List<HL7Msg> GetAllPending(out string diagnosticMsg){
			//diagnosticMsg=DataConnection.GetCurrentConnectionString();
			string command="SELECT * FROM hl7msg WHERE HL7Status="+POut.PInt((int)HL7MessageStatus.OutPending);
			//diagnosticMsg+=".   "+command;
			diagnosticMsg="";
			Collection<HL7Msg> collection=DataObjectFactory<HL7Msg>.CreateObjects(command);
			return new List<HL7Msg>(collection);		
		}

		///<summary></summary>
		public static void WriteObject(HL7Msg hL7Msg){
			DataObjectFactory<HL7Msg>.WriteObject(hL7Msg);
		}

		///<summary></summary>
		public static bool MessageWasSent(int aptNum) {
			string command="SELECT COUNT(*) FROM hl7msg WHERE AptNum="+POut.PInt(aptNum);
			if(General.GetCount(command)=="0") {
				return false;
			}
			return true;
		}

		/*
		///<summary></summary>
		public static void DeleteObject(int HL7MsgNum){
			//validate that not already in use.
			string command="SELECT LName,FName FROM patient WHERE HL7MsgNum="+POut.PInt(HL7MsgNum);
			DataTable table=General.GetTable(command);
			//int count=PIn.PInt(General.GetCount(command));
			string pats="";
			for(int i=0;i<table.Rows.Count;i++){
				if(i>0){
					pats+=", ";
				}
				pats+=table.Rows[i]["FName"].ToString()+" "+table.Rows[i]["LName"].ToString();
			}
			if(table.Rows.Count>0){
				throw new ApplicationException(Lan.g("HL7Msgs","HL7Msg is already in use by patient(s). Not allowed to delete. ")+pats);
			}
			DataObjectFactory<HL7Msg>.DeleteObject(HL7MsgNum);
		}

		//public static void DeleteObject(int HL7MsgNum){
		//	DataObjectFactory<HL7Msg>.DeleteObject(HL7MsgNum);
		//}

		public static string GetDescription(int HL7MsgNum){
			if(HL7MsgNum==0){
				return "";
			}
			for(int i=0;i<HL7MsgC.List.Length;i++){
				if(HL7MsgC.List[i].HL7MsgNum==HL7MsgNum){
					return HL7MsgC.List[i].Description;
				}
			}
			return "";
		}

		public static List<HL7Msg> GetListFiltered(string snippet) {
			List<HL7Msg> retVal=new List<HL7Msg>();
			if(snippet=="") {
				return retVal;
			}
			for(int i=0;i<HL7MsgC.List.Length;i++) {
				if(HL7MsgC.List[i].Description.ToLower().Contains(snippet.ToLower())) {
					retVal.Add(HL7MsgC.List[i]);
				}
			}
			return retVal;
		}

		///<summary>Will return -1 if no match.</summary>
		public static int FindMatchHL7MsgNum(string description) {
			if(description=="") {
				return 0;
			}
			for(int i=0;i<HL7MsgC.List.Length;i++) {
				if(HL7MsgC.List[i].Description.ToLower()==description.ToLower()) {
					return HL7MsgC.List[i].HL7MsgNum;
				}
			}
			return -1;
		}
		*/

	}
}