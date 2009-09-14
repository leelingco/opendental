using System;
using System.Collections;
using System.Data;
using System.Reflection;

namespace OpenDentBusiness{
	///<summary></summary>
	public class ClockEvents {
		///<summary>isBreaks is ignored if getAll is true.</summary>
		public static ClockEvent[] Refresh(long empNum,DateTime fromDate,DateTime toDate,bool getAll,bool isBreaks) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<ClockEvent[]>(MethodBase.GetCurrentMethod(),empNum,fromDate,toDate,getAll,isBreaks);
			}
			string command=
				"SELECT * from clockevent WHERE"
				+" EmployeeNum = '"+POut.PLong(empNum)+"'"
				+" AND TimeDisplayed >= "+POut.PDate(fromDate)
				//adding a day takes it to midnight of the specified toDate
				+" AND TimeDisplayed <= "+POut.PDate(toDate.AddDays(1));
			if(!getAll) {
				if(isBreaks)
					command+=" AND ClockStatus = '2'";
				else
					command+=" AND (ClockStatus = '0' OR ClockStatus = '1')";
			}
			command+=" ORDER BY TimeDisplayed";
			DataTable table=Db.GetTable(command);
			ClockEvent[] List=new ClockEvent[table.Rows.Count];
			for(int i=0;i<List.Length;i++) {
				List[i]=new ClockEvent();
				List[i].ClockEventNum = PIn.PLong(table.Rows[i][0].ToString());
				List[i].EmployeeNum   = PIn.PLong(table.Rows[i][1].ToString());
				List[i].TimeEntered   = PIn.PDateT(table.Rows[i][2].ToString());
				List[i].TimeDisplayed = PIn.PDateT(table.Rows[i][3].ToString());
				List[i].ClockIn       = PIn.PBool(table.Rows[i][4].ToString());
				List[i].ClockStatus   =(TimeClockStatus)PIn.PLong(table.Rows[i][5].ToString());
				List[i].Note          = PIn.PString(table.Rows[i][6].ToString());
			}
			return List;
		}

		///<summary></summary>
		public static long Insert(ClockEvent ce) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				ce.ClockEventNum=Meth.GetInt(MethodBase.GetCurrentMethod(),ce);
				return ce.ClockEventNum;
			}
			DateTime serverTime=MiscData.GetNowDateTime();
			if(PrefC.RandomKeys) {
				ce.ClockEventNum=ReplicationServers.GetKey("clockevent","ClockEventNum");
			}
			string command="INSERT INTO clockevent (";
			if(PrefC.RandomKeys) {
				command+="ClockEventNum,";
			}
			command+="EmployeeNum,TimeEntered,TimeDisplayed,ClockIn"
				+",ClockStatus,Note) VALUES(";
			if(PrefC.RandomKeys) {
				command+="'"+POut.PLong(ce.ClockEventNum)+"', ";
			}
			command+=
				 "'"+POut.PLong   (ce.EmployeeNum)+"', "
				+POut.PDateT (serverTime)+", "
				+POut.PDateT (serverTime)+", "
				+"'"+POut.PBool  (ce.ClockIn)+"', "
				+"'"+POut.PLong   ((int)ce.ClockStatus)+"', "
				+"'"+POut.PString(ce.Note)+"')";
			if(PrefC.RandomKeys) {
				Db.NonQ(command);
			}
			else {
				ce.ClockEventNum=Db.NonQ(command,true);
			}
			return ce.ClockEventNum;
		}

		///<summary></summary>
		public static void Update(ClockEvent ce) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ce);
				return;
			}
			string command= "UPDATE clockevent SET "
				+"EmployeeNum = '"    +POut.PLong   (ce.EmployeeNum)+"' "
				+",TimeEntered = "   +POut.PDateT (ce.TimeEntered)+" "
				+",TimeDisplayed = " +POut.PDateT (ce.TimeDisplayed)+" "
				+",ClockIn = '"       +POut.PBool  (ce.ClockIn)+"' "
				+",ClockStatus = '"   +POut.PLong   ((int)ce.ClockStatus)+"' "
				+",Note = '"          +POut.PString(ce.Note)+"' "
				+"WHERE ClockEventNum = '"+POut.PLong(ce.ClockEventNum)+"'";
			Db.NonQ(command);
		}

		///<summary></summary>
		public static void Delete(ClockEvent ce) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ce);
				return;
			}
			string command= "DELETE FROM clockevent WHERE ClockEventNum = "+POut.PLong(ce.ClockEventNum);
			Db.NonQ(command);
		}

		///<summary>Gets directly from the database.  Returns true if the last time clock entry for this employee was a clockin.</summary>
		public static bool IsClockedIn(long employeeNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetBool(MethodBase.GetCurrentMethod(),employeeNum);
			}
			string command="SELECT ClockIn FROM clockevent WHERE EmployeeNum="+POut.PLong(employeeNum)
				+" ORDER BY TimeDisplayed DESC ";
			if(DataConnection.DBtype==DatabaseType.Oracle){
				command="SELECT * FROM ("+command+") WHERE ROWNUM<=1";
			}else{//Assume MySQL
				command+=" LIMIT 1";
			}
			DataTable table=Db.GetTable(command);
			if(table.Rows.Count==0)//if this employee has never clocked in or out.
				return false;
			if(PIn.PBool(table.Rows[0][0].ToString())){//if the last clockevent was a clockin
				return true;
			}
			return false;
		}

		///<summary>Gets info directly from database.  If the employee is clocked out, this gets the status for clockin is based on how they last clocked out.  Also used to determine how to initially display timecard.</summary>
		public static TimeClockStatus GetLastStatus(long employeeNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<TimeClockStatus>(MethodBase.GetCurrentMethod(),employeeNum);
			}
			string command="SELECT ClockStatus FROM clockevent WHERE EmployeeNum="+POut.PLong(employeeNum)
				+" ORDER BY TimeDisplayed DESC ";
			if(DataConnection.DBtype==DatabaseType.Oracle){
				command="SELECT * FROM ("+command+") WHERE ROWNUM<=1";
			}else{//Assum MySQL
				command+="LIMIT 1";
			}
			DataTable table=Db.GetTable(command);
			if(table.Rows.Count==0)//if this employee has never clocked in or out.
				return TimeClockStatus.Home;
			return (TimeClockStatus)PIn.PLong(table.Rows[0][0].ToString());
		}

		///<summary></summary>
		public static DateTime GetServerTime(){
			//No need to check RemotingRole; no call to db.
			return MiscData.GetNowDateTime();
		}

		///<summary>Used in the timecard to track hours worked per week when the week started in a previous time period.  This gets all the hours of the first week before the date listed.  Also adds in any adjustments for that week.</summary>
		public static TimeSpan GetWeekTotal(long empNum,DateTime date) {
			//No need to check RemotingRole; no call to db.
			ClockEvent[] events=Refresh(empNum,date.AddDays(-6),date.AddDays(-1),false,false);
			//eg, if this is Thursday, then we are getting last Friday through this Wed.
			TimeSpan retVal=new TimeSpan(0);
			for(int i=0;i<events.Length;i++){
				if(events[i].TimeDisplayed.DayOfWeek > date.DayOfWeek){//eg, Friday > Thursday, so ignore
					continue;
				}
				if(i>0 && !events[i].ClockIn){
					retVal+=events[i].TimeDisplayed-events[i-1].TimeDisplayed;
				}
			}
			//now, adjustments
			TimeAdjust[] TimeAdjustList=TimeAdjusts.Refresh(empNum,date.AddDays(-6),date.AddDays(-1));
			for(int i=0;i<TimeAdjustList.Length;i++) {
				if(TimeAdjustList[i].TimeEntry.DayOfWeek > date.DayOfWeek) {//eg, Friday > Thursday, so ignore
					continue;
				}
				retVal+=TimeAdjustList[i].RegHours;
			}
			return retVal;
		}




	}

	
}




