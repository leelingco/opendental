using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.Drawing;
using System.Reflection;

namespace OpenDentBusiness{
	///<summary></summary>
	public class Employees{
		private static Employee[] listLong;
		private static Employee[] listShort;

		public static Employee[] ListLong {
			//No need to check RemotingRole; no call to db.
			get {
				if(listLong==null) {
					RefreshCache();
				}
				return listLong;
			}
			set {
				listLong=value;
			}
		}

		///<summary>Does not include hidden employees</summary>
		public static Employee[] ListShort {
			//No need to check RemotingRole; no call to db.
			get {
				if(listShort==null) {
					RefreshCache();
				}
				return listShort;
			}
			set {
				listShort=value;
			}
		}

		public static DataTable RefreshCache() {
			//No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
			string command="SELECT * FROM employee ORDER BY IsHidden,FName,LName";
			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
			table.TableName="Employee";
			FillCache(table);
			return table;
		}

		///<summary></summary>
		public static void FillCache(DataTable table) {
			//No need to check RemotingRole; no call to db.
			ListLong=new Employee[table.Rows.Count];
			ArrayList tempList=new ArrayList();
			//Employee temp;
			for(int i=0;i<table.Rows.Count;i++) {
				ListLong[i]=new Employee();
				ListLong[i].EmployeeNum=PIn.PInt(table.Rows[i][0].ToString());
				ListLong[i].LName=PIn.PString(table.Rows[i][1].ToString());
				ListLong[i].FName=PIn.PString(table.Rows[i][2].ToString());
				ListLong[i].MiddleI=PIn.PString(table.Rows[i][3].ToString());
				ListLong[i].IsHidden=PIn.PBool(table.Rows[i][4].ToString());
				ListLong[i].ClockStatus=PIn.PString(table.Rows[i][5].ToString());
				ListLong[i].PhoneExt=PIn.PInt(table.Rows[i][6].ToString());
				if(!ListLong[i].IsHidden) {
					tempList.Add(ListLong[i]);
				}
			}
			ListShort=new Employee[tempList.Count];
			for(int i=0;i<tempList.Count;i++) {
				ListShort[i]=(Employee)tempList[i];
			}
		}

		/*public static Employee[] GetListByExtension(){
			if(ListShort==null){
				return new Employee[0];
			}
			Employee[] arrayCopy=new Employee[ListShort.Length];
			ListShort.CopyTo(arrayCopy,0);
			int[] arrayKeys=new int[ListShort.Length];
			for(int i=0;i<ListShort.Length;i++){
				arrayKeys[i]=ListShort[i].PhoneExt;
			}
			Array.Sort(arrayKeys,arrayCopy);
			//List<Employee> retVal=new List<Employee>(ListShort);
			//retVal.Sort(
			return arrayCopy;
		}*/

		///<summary></summary>
		public static void Update(Employee Cur){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),Cur);
				return;
			}
			if(Cur.LName=="" && Cur.FName=="") {
				throw new ApplicationException(Lans.g("FormEmployeeEdit","Must include either first name or last name"));
			}
			string command="UPDATE employee SET " 
				+ "lname = '"       +POut.PString(Cur.LName)+"' "
				+ ",fname = '"      +POut.PString(Cur.FName)+"' "
				+ ",middlei = '"    +POut.PString(Cur.MiddleI)+"' "
				+ ",ishidden = '"   +POut.PBool  (Cur.IsHidden)+"' "
				+ ",ClockStatus = '"+POut.PString(Cur.ClockStatus)+"' "
				+ ",PhoneExt = '"   +POut.PInt   (Cur.PhoneExt)+"' "
				+"WHERE EmployeeNum = '"+POut.PInt(Cur.EmployeeNum)+"'";
			//MessageBox.Show(string command);
			Db.NonQ(command);
		}

		///<summary></summary>
		public static long Insert(Employee Cur){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Cur.EmployeeNum=Meth.GetInt(MethodBase.GetCurrentMethod(),Cur);
				return Cur.EmployeeNum;
			}
			string command;
			if(Cur.LName=="" && Cur.FName=="") {
				throw new ApplicationException(Lans.g("FormEmployeeEdit","Must include either first name or last name"));
			}
			command= "INSERT INTO employee (lname,fname,middlei,ishidden"
				+",ClockStatus,PhoneExt) "
				+"VALUES("
				+"'"+POut.PString(Cur.LName)+"', "
				+"'"+POut.PString(Cur.FName)+"', "
				+"'"+POut.PString(Cur.MiddleI)+"', "
				+"'"+POut.PBool  (Cur.IsHidden)+"', "
				+"'"+POut.PString(Cur.ClockStatus)+"', "
				+"'"+POut.PInt   (Cur.PhoneExt)+"')";
			Cur.EmployeeNum=Db.NonQ(command,true);
			return Cur.EmployeeNum;
		}

		///<summary>Surround with try-catch</summary>
		public static void Delete(long employeeNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),employeeNum);
				return;
			}
			//appointment.Assistant will not block deletion
			//schedule.EmployeeNum will not block deletion
			string command="SELECT COUNT(*) FROM clockevent WHERE EmployeeNum="+POut.PInt(employeeNum);
			if(Db.GetCount(command)!="0"){
				throw new ApplicationException(Lans.g("FormEmployeeSelect",
					"Not allowed to delete employee because of attached clock events."));
			}
			command="SELECT COUNT(*) FROM timeadjust WHERE EmployeeNum="+POut.PInt(employeeNum);
			if(Db.GetCount(command)!="0") {
				throw new ApplicationException(Lans.g("FormEmployeeSelect",
					"Not allowed to delete employee because of attached time adjustments."));
			}
			command="SELECT COUNT(*) FROM userod WHERE EmployeeNum="+POut.PInt(employeeNum);
			if(Db.GetCount(command)!="0") {
				throw new ApplicationException(Lans.g("FormEmployeeSelect",
					"Not allowed to delete employee because of attached user."));
			}
			command="UPDATE appointment SET Assistant=0 WHERE Assistant="+POut.PInt(employeeNum);
			Db.NonQ(command);
			command="DELETE FROM schedule WHERE EmployeeNum="+POut.PInt(employeeNum);
			Db.NonQ(command);
			command= "DELETE FROM employee WHERE EmployeeNum ="+POut.PInt(employeeNum);
			Db.NonQ(command);
		}

		/*
		///<summary>Returns LName,FName MiddleI for the provided employee.</summary>
		public static string GetNameLF(Employee emp){
			return(emp.LName+", "+emp.FName+" "+emp.MiddleI);
		}

		///<summary>Loops through List to find matching employee, and returns LName,FName MiddleI.</summary>
		public static string GetNameLF(int employeeNum){
			for(int i=0;i<ListLong.Length;i++){
				if(ListLong[i].EmployeeNum==employeeNum){
					return GetNameLF(ListLong[i]);
				}
			}
			return "";
		}*/

		///<summary>Returns FName MiddleI LName for the provided employee.</summary>
		public static string GetNameFL(Employee emp) {
			//No need to check RemotingRole; no call to db.
			return (emp.FName+" "+emp.MiddleI+" "+emp.LName);
		}

		///<summary>Loops through List to find matching employee, and returns FName MiddleI LName.</summary>
		public static string GetNameFL(long employeeNum) {
			//No need to check RemotingRole; no call to db.
			for(int i=0;i<ListLong.Length;i++) {
				if(ListLong[i].EmployeeNum==employeeNum) {
					return GetNameFL(ListLong[i]);
				}
			}
			return "";
		}

		///<summary>Loops through List to find matching employee, and returns first 2 letters of first name.  Will later be improved with abbr field.</summary>
		public static string GetAbbr(long employeeNum) {
			//No need to check RemotingRole; no call to db.
			string retVal="";
			for(int i=0;i<ListLong.Length;i++){
				if(ListLong[i].EmployeeNum==employeeNum){
					retVal=ListLong[i].FName;
					if(retVal.Length>2)
						retVal=retVal.Substring(0,2);
					return retVal;
				}
			}
			return "";
		}

		public static Employee GetEmp(long employeeNum) {
			//No need to check RemotingRole; no call to db.
			for(int i=0;i<ListLong.Length;i++) {
				if(ListLong[i].EmployeeNum==employeeNum) {
					return ListLong[i];
				}
			}
			return null;
		}

		/// <summary> Returns -1 if employeeNum is not found.  0 if not hidden and 1 if hidden </summary>		
		public static int IsHidden(long employeeNum) {
			//No need to check RemotingRole; no call to db.
			int rValue = -1;
			if (ListLong != null){
				for (int i = 0; i < ListLong.Length; i++){
					if (ListLong[i].EmployeeNum == employeeNum){
						rValue = (ListLong[i].IsHidden ? 1 : 0);
						i = ListLong.Length;
					}
				}
			}
			return rValue;
		}

		public static DataTable GetPhoneTable(){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetTable(MethodBase.GetCurrentMethod());
			}
			string command="SELECT * FROM phone";
			try{
				return Db.GetTable(command);
			}
			catch{
				return new DataTable();
			}
		}

		public static void SetPhoneStatus(string clockStatus,int extens){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),clockStatus,extens);
				return;
			}
			//this code is similar to code in the phone tracking server.
			//But here, it ONLY changes clockStatus and ColorBar.
			string command=@"SELECT EmployeeNum,Description,
				IFNULL(IsAvailable,1) isAvail, COUNT(IsAvailable) overridden
				FROM phone
				LEFT JOIN phoneoverride ON phone.Extension=phoneoverride.Extension
				WHERE phone.Extension="+POut.PInt(extens)
				+" GROUP BY phone.Extension";
			DataTable tablePhone=Db.GetTable(command);
			if(tablePhone.Rows.Count==0){
				return;
			}
			long empNum=PIn.PInt(tablePhone.Rows[0]["EmployeeNum"].ToString());
			bool isAvailable=PIn.PBool(tablePhone.Rows[0]["isAvail"].ToString());
			bool overridden=PIn.PBool(tablePhone.Rows[0]["overridden"].ToString());
			bool isInUse=false;
			if(tablePhone.Rows[0]["Description"].ToString()=="In use"){
				isInUse=true;
			}
			Color colorBar=GetColorBar(clockStatus,overridden,isAvailable,empNum,isInUse);
			command="UPDATE phone SET ClockStatus='"+POut.PString(clockStatus)+"', "
				+"ColorBar="+colorBar.ToArgb().ToString()+" "
				+"WHERE Extension="+extens;
			Db.NonQ(command);
		}

		///<summary>Used when clocking in and out, but not through the phone grid.  Keeps the phone grid current. Handles situations where employee is listed on two different extensions.</summary>
		public static void SetPhoneClockStatus(long employeeNum,string clockStatus){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),employeeNum,clockStatus);
				return;
			}
			string command="SELECT Extension,ClockStatus FROM phone WHERE employeeNum="+POut.PInt(employeeNum);
			DataTable table=Db.GetTable(command);
			int extension;
			string curClockStatus;
			for(int i=0;i<table.Rows.Count;i++){
				extension=PIn.PInt32(table.Rows[i]["Extension"].ToString());
				curClockStatus=table.Rows[i]["ClockStatus"].ToString();
				if(curClockStatus=="Unavailable"){
					continue;//don't change "Unavailable" to anything else.
				}
				SetPhoneStatus(clockStatus,extension);
			}
		}

		private static Color GetColorBar(string clockStatus,bool overridden,bool isAvailable,long empNum,bool isInUse){
			//No need to check RemotingRole; no call to db.
			//there is an exact duplicate of this function in the phone server.
			Color colorBar=Color.White;
			if(overridden && !isAvailable){
				//no colors
			}
			else if(!overridden
				&& (empNum==4//amber
				|| empNum==21//natalie
				|| empNum==20//britt
				|| empNum==22//jordan
				|| empNum==15//derek
				|| empNum==18//james
				))
			{
				//no colors
			}
			//else if(!overridden
			//	&& (empNum==15//derek
			//	|| empNum==18))//james
			//{
				//this prevents green bar from showing.
			//	if(isInUse){
			//		colorBar=Color.Salmon;
			//	}
			//}
			else if(isInUse){
				colorBar=Color.Salmon;
			}
			//the rest are for idle:
			else if(clockStatus=="Available"){
				colorBar=Color.FromArgb(153,220,153);//green
			}
			//"Unavailable" already handled above
			else if(clockStatus=="off"){
				//colorText=Color.Gray;
				//no colorBar
			}
			else if(clockStatus=="Break"){
				//no color
			}
			else if(clockStatus=="Training"
				|| clockStatus=="TeamAssist"
				|| clockStatus=="WrapUp"
				|| clockStatus=="OfflineAssist")
			{
				colorBar=Color.FromArgb(255,255,145);//yellow
			}
			return colorBar;
		}




	}

	

	
	

}













