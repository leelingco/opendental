//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:36 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.DeletedObjects;
import OpenDentBusiness.DeletedObjectType;
import OpenDentBusiness.Employees;
import OpenDentBusiness.GuardianRelationship;
import OpenDentBusiness.Guardians;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientLogic;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PIn;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProcCodeNotes;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.ProcedureLogic;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Properties.Resources;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Sites;
import OpenDentBusiness.Tooth;

public class Appointments   
{
    /**
    * Gets a list of appointments for a period of time in the schedule, whether hidden or not.
    */
    public static Appointment[] getForPeriod(DateTime startDate, DateTime endDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Appointment[]>GetObject(MethodBase.GetCurrentMethod(), startDate, endDate);
        }
         
        //DateSelected = thisDay;
        String command = "SELECT * from appointment " + "WHERE AptDateTime BETWEEN " + POut.date(startDate) + " AND " + POut.Date(endDate.AddDays(1)) + " " + "AND aptstatus != '" + ((Enum)ApptStatus.UnschedList).ordinal() + "' " + "AND aptstatus != '" + ((Enum)ApptStatus.Planned).ordinal() + "'";
        return Crud.AppointmentCrud.SelectMany(command).ToArray();
    }

    /**
    * Gets a List<Appointment> of appointments for a period of time in the schedule, whether hidden or not.
    */
    public static List<Appointment> getForPeriodList(DateTime startDate, DateTime endDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Appointment>>GetObject(MethodBase.GetCurrentMethod(), startDate, endDate);
        }
         
        //DateSelected = thisDay;
        String command = "SELECT * from appointment " + "WHERE AptDateTime BETWEEN " + POut.date(startDate) + " AND " + POut.Date(endDate.AddDays(1)) + " " + "AND aptstatus != '" + ((Enum)ApptStatus.UnschedList).ordinal() + "' " + "AND aptstatus != '" + ((Enum)ApptStatus.Planned).ordinal() + "'";
        return Crud.AppointmentCrud.SelectMany(command);
    }

    /**
    * Gets list of unscheduled appointments.  Allowed orderby: status, alph, date
    */
    public static Appointment[] refreshUnsched(String orderby, long provNum, long siteNum, boolean includeBrokenAppts, long clinicNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Appointment[]>GetObject(MethodBase.GetCurrentMethod(), orderby, provNum, siteNum, includeBrokenAppts, clinicNum);
        }
         
        String command = "SELECT * FROM appointment " + "LEFT JOIN patient ON patient.PatNum=appointment.PatNum " + "WHERE ";
        if (includeBrokenAppts)
        {
            command += "(AptStatus = " + POut.Long(((Enum)ApptStatus.UnschedList).ordinal()) + " OR AptStatus = " + POut.Long(((Enum)ApptStatus.Broken).ordinal()) + ") ";
        }
        else
        {
            command += "AptStatus = " + POut.Long(((Enum)ApptStatus.UnschedList).ordinal()) + " ";
        } 
        if (provNum > 0)
        {
            command += "AND (appointment.ProvNum=" + POut.long(provNum) + " OR appointment.ProvHyg=" + POut.long(provNum) + ") ";
        }
         
        if (siteNum > 0)
        {
            command += "AND patient.SiteNum=" + POut.long(siteNum) + " ";
        }
         
        if (clinicNum > 0)
        {
            command += "AND appointment.ClinicNum=" + POut.long(clinicNum) + " ";
        }
         
        command += "HAVING patient.PatStatus= " + POut.Long(((Enum)PatientStatus.Patient).ordinal()) + " " + " OR patient.PatStatus= " + POut.Long(((Enum)PatientStatus.Prospective).ordinal()) + " ";
        if (StringSupport.equals(orderby, "status"))
        {
            command += "ORDER BY UnschedStatus,AptDateTime";
        }
        else if (StringSupport.equals(orderby, "alph"))
        {
            command += "ORDER BY LName,FName";
        }
        else
        {
            //if(orderby=="date"){
            command += "ORDER BY AptDateTime";
        }  
        return Crud.AppointmentCrud.SelectMany(command).ToArray();
    }

    /**
    * Gets list of asap appointments.
    */
    public static List<Appointment> refreshASAP(long provNum, long siteNum, long clinicNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Appointment>>GetObject(MethodBase.GetCurrentMethod(), provNum, siteNum, clinicNum);
        }
         
        String command = "SELECT * FROM appointment ";
        //if(orderby=="alph" || siteNum>0) {
        //command+="LEFT JOIN patient ON patient.PatNum=appointment.PatNum ";
        //}
        if (siteNum > 0)
        {
            command += "LEFT JOIN patient ON patient.PatNum=appointment.PatNum ";
        }
         
        command += "WHERE AptStatus = " + POut.Long(((Enum)ApptStatus.ASAP).ordinal()) + " ";
        if (provNum > 0)
        {
            command += "AND (appointment.ProvNum=" + POut.long(provNum) + " OR appointment.ProvHyg=" + POut.long(provNum) + ") ";
        }
         
        if (siteNum > 0)
        {
            command += "AND patient.SiteNum=" + POut.long(siteNum) + " ";
        }
         
        if (clinicNum > 0)
        {
            command += "AND appointment.ClinicNum=" + POut.long(clinicNum) + " ";
        }
         
        /*if(orderby=="status") {
        				command+="ORDER BY UnschedStatus,AptDateTime";
        			}
        			else if(orderby=="alph") {
        				command+="ORDER BY LName,FName";
        			}
        			else { //if(orderby=="date"){
        				command+="ORDER BY AptDateTime";
        			}*/
        command += "ORDER BY AptDateTime";
        return Crud.AppointmentCrud.SelectMany(command);
    }

    /**
    * Allowed orderby: status, alph, date
    */
    public static List<Appointment> refreshPlannedTracker(String orderby, long provNum, long siteNum, long clinicNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Appointment>>GetObject(MethodBase.GetCurrentMethod(), orderby, provNum, siteNum, clinicNum);
        }
         
        //We create a in-memory temporary table by joining the appointment and patient
        //tables to get a list of planned appointments for active paients, then we
        //perform a left join on that temporary table against the appointment table
        //to exclude any appointments in the temporary table which are already refereced
        //by the NextAptNum column by any other appointment within the appointment table.
        //Using an in-memory temporary table reduces the number of row comparisons performed for
        //this query overall as compared to left joining the appointment table onto itself,
        //because the in-memory temporary table has many fewer rows than the appointment table
        //on average.
        String command = "SELECT tplanned.*,tregular.AptNum " + "FROM (SELECT a.* FROM appointment a,patient p " + "WHERE a.AptStatus=" + POut.Long(((Enum)ApptStatus.Planned).ordinal()) + " AND p.PatStatus=" + POut.Long(((Enum)PatientStatus.Patient).ordinal()) + " AND a.PatNum=p.PatNum ";
        if (provNum > 0)
        {
            command += "AND (a.ProvNum=" + POut.long(provNum) + " OR a.ProvHyg=" + POut.long(provNum) + ") ";
        }
         
        if (siteNum > 0)
        {
            command += "AND p.SiteNum=" + POut.long(siteNum) + " ";
        }
         
        if (clinicNum > 0)
        {
            command += "AND a.ClinicNum=" + POut.long(clinicNum) + " ";
        }
         
        if (StringSupport.equals(orderby, "status"))
        {
            command += "ORDER BY a.UnschedStatus,a.AptDateTime";
        }
        else if (StringSupport.equals(orderby, "alph"))
        {
            command += "ORDER BY p.LName,p.FName";
        }
        else
        {
            //if(orderby=="date"){
            command += "ORDER BY a.AptDateTime";
        }  
        command += ") tplanned " + "LEFT JOIN appointment tregular ON tplanned.AptNum=tregular.NextAptNum " + "WHERE tregular.AptNum IS NULL";
        return Crud.AppointmentCrud.SelectMany(command);
    }

    /**
    * Returns all appointments for the given patient, ordered from earliest to latest.  Used in statements, appt cards, OtherAppts window, etc.
    */
    public static Appointment[] getForPat(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Appointment[]>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM appointment " + "WHERE PatNum = '" + POut.long(patNum) + "' " + "AND NOT (AptDateTime < " + POut.date(new DateTime(1880, 1, 1)) + " AND AptStatus=" + POut.int(((Enum)ApptStatus.UnschedList).ordinal()) + ") " + "ORDER BY AptDateTime";
        return Crud.AppointmentCrud.SelectMany(command).ToArray();
    }

    //AND NOT (on the pinboard)
    public static List<Appointment> getListForPat(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Appointment>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM appointment " + "WHERE patnum = '" + patNum.ToString() + "' " + "ORDER BY AptDateTime";
        return Crud.AppointmentCrud.SelectMany(command);
    }

    /**
    * Gets one appointment from db.  Returns null if not found.
    */
    public static Appointment getOneApt(long aptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Appointment>GetObject(MethodBase.GetCurrentMethod(), aptNum);
        }
         
        if (aptNum == 0)
        {
            return null;
        }
         
        String command = "SELECT * FROM appointment " + "WHERE AptNum = '" + POut.long(aptNum) + "'";
        return Crud.AppointmentCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static Appointment getScheduledPlannedApt(long nextAptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Appointment>GetObject(MethodBase.GetCurrentMethod(), nextAptNum);
        }
         
        if (nextAptNum == 0)
        {
            return null;
        }
         
        String command = "SELECT * FROM appointment " + "WHERE NextAptNum = '" + POut.long(nextAptNum) + "'";
        return Crud.AppointmentCrud.SelectOne(command);
    }

    /**
    * Gets a list of all future appointments which are either sched or ASAP.  Ordered by dateTime
    */
    public static List<Appointment> getFutureSchedApts(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Appointment>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM appointment " + "WHERE PatNum = " + POut.long(patNum) + " " + "AND AptDateTime > " + DbHelper.now() + " " + "AND (aptstatus = " + ((Enum)ApptStatus.Scheduled).ordinal() + " " + "OR aptstatus = " + ((Enum)ApptStatus.ASAP).ordinal() + ") " + "ORDER BY AptDateTime";
        return Crud.AppointmentCrud.SelectMany(command);
    }

    /**
    * Gets a list of aptNums for one day in the schedule for a given set of providers.
    */
    public static List<long> getRouting(DateTime date, List<long> provNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), date, provNums);
        }
         
        String command = "SELECT AptNum FROM appointment " + "WHERE AptDateTime LIKE '" + POut.date(date,false) + "%' " + "AND aptstatus != '" + ((Enum)ApptStatus.UnschedList).ordinal() + "' " + "AND aptstatus != '" + ((Enum)ApptStatus.Planned).ordinal() + "' " + "AND (";
        for (int i = 0;i < provNums.Count;i++)
        {
            if (i > 0)
            {
                command += " OR";
            }
             
            command += " ProvNum=" + POut.Long(provNums[i]) + " OR ProvHyg=" + POut.Long(provNums[i]);
        }
        command += ") ORDER BY AptDateTime";
        DataTable table = Db.getTable(command);
        List<long> retVal = new List<long>();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(PIn.Long(table.Rows[i][0].ToString()));
        }
        return retVal;
    }

    //return TableToObjects(table).ToArray();
    public static List<Appointment> getChangedSince(DateTime changedSince, DateTime excludeOlderThan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Appointment>>GetObject(MethodBase.GetCurrentMethod(), changedSince, excludeOlderThan);
        }
         
        String command = "SELECT * FROM appointment WHERE DateTStamp > " + POut.dateT(changedSince) + " AND AptDateTime > " + POut.dateT(excludeOlderThan);
        return Crud.AppointmentCrud.SelectMany(command);
    }

    /**
    * Used if the number of records are very large, in which case using GetChangedSince is not the preffered route due to memory problems caused by large recordsets.
    */
    public static List<long> getChangedSinceAptNums(DateTime changedSince, DateTime excludeOlderThan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT AptNum FROM appointment WHERE DateTStamp > " + POut.dateT(changedSince) + " AND AptDateTime > " + POut.dateT(excludeOlderThan);
        DataTable dt = Db.getTable(command);
        List<long> aptnums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            aptnums.Add(PIn.Long(dt.Rows[i]["AptNum"].ToString()));
        }
        return aptnums;
    }

    /**
    * Used along with GetChangedSinceAptNums
    */
    public static List<Appointment> getMultApts(List<long> aptNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Appointment>>GetObject(MethodBase.GetCurrentMethod(), aptNums);
        }
         
        //MessageBox.Show(patNums.Length.ToString());
        String strAptNums = "";
        DataTable table = new DataTable();
        if (aptNums.Count > 0)
        {
            for (int i = 0;i < aptNums.Count;i++)
            {
                if (i > 0)
                {
                    strAptNums += "OR ";
                }
                 
                strAptNums += "AptNum='" + aptNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM appointment WHERE " + strAptNums;
            //MessageBox.Show(string command);
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        Appointment[] multApts = Crud.AppointmentCrud.TableToList(table).ToArray();
        List<Appointment> aptList = new List<Appointment>(multApts);
        return aptList;
    }

    /**
    * A list of strings.  Each string corresponds to one appointment in the supplied list.  Each string is a comma delimited list of codenums of the procedures attached to the appointment.
    */
    public static List<String> getUAppointProcs(List<Appointment> appts) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod(), appts);
        }
         
        List<String> retVal = new List<String>();
        if (appts.Count == 0)
        {
            return retVal;
        }
         
        String command = "SELECT AptNum,CodeNum FROM procedurelog WHERE AptNum IN(";
        for (int i = 0;i < appts.Count;i++)
        {
            if (i > 0)
            {
                command += ",";
            }
             
            command += POut.Long(appts[i].AptNum);
        }
        command += ")";
        DataTable table = Db.getTable(command);
        String str = new String();
        for (int i = 0;i < appts.Count;i++)
        {
            str = "";
            for (int p = 0;p < table.Rows.Count;p++)
            {
                if (table.Rows[p]["AptNum"].ToString() == appts[i].AptNum.ToString())
                {
                    if (!StringSupport.equals(str, ""))
                    {
                        str += ",";
                    }
                     
                    str += table.Rows[p]["CodeNum"].ToString();
                }
                 
            }
            retVal.Add(str);
        }
        return retVal;
    }

    public static void insert(Appointment appt) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            insertIncludeAptNum(appt,false);
        }
        else
        {
            //Oracle must always have a valid PK.
            appt.AptNum = DbHelper.getNextOracleKey("appointment","AptNum");
            insertIncludeAptNum(appt,true);
        } 
    }

    /**
    * Set includeAptNum to true only in rare situations.  Like when we are inserting for eCW.
    */
    public static long insertIncludeAptNum(Appointment appt, boolean useExistingPK) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            appt.AptNum = Meth.GetLong(MethodBase.GetCurrentMethod(), appt, useExistingPK);
            return appt.AptNum;
        }
         
        //make sure all fields are properly filled:
        if (appt.Confirmed == 0)
        {
            appt.Confirmed = DefC.getList(DefCat.ApptConfirmed)[0].DefNum;
        }
         
        if (appt.ProvNum == 0)
        {
            appt.ProvNum = ProviderC.getListShort()[0].ProvNum;
        }
         
        return Crud.AppointmentCrud.Insert(appt, useExistingPK);
    }

    /**
    * Updates only the changed columns and returns the number of rows affected.  Supply an oldApt for comparison.
    */
    public static void update(Appointment appointment, Appointment oldAppointment) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), appointment, oldAppointment);
            return ;
        }
         
        Crud.AppointmentCrud.Update(appointment, oldAppointment);
    }

    /**
    * Used in Chart module to test whether a procedure is attached to an appointment with today's date. The procedure might have a different date if still TP status.  ApptList should include all appointments for this patient. Does not make a call to db.
    */
    public static boolean procIsToday(Appointment[] apptList, Procedure proc) throws Exception {
        for (int i = 0;i < apptList.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (apptList[i].AptDateTime.Date == DateTime.Today && apptList[i].AptNum == proc.AptNum && (apptList[i].AptStatus == ApptStatus.Scheduled || apptList[i].AptStatus == ApptStatus.ASAP || apptList[i].AptStatus == ApptStatus.Broken || apptList[i].AptStatus == ApptStatus.Complete))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Used in FormConfirmList.  The assumption is made that showRecall and showNonRecall will not both be false.
    */
    public static DataTable getConfirmList(DateTime dateFrom, DateTime dateTo, long provNum, long clinicNum, boolean showRecall, boolean showNonRecall) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), dateFrom, dateTo, provNum, clinicNum, showRecall, showNonRecall);
        }
         
        DataTable table = new DataTable();
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("AddrNote");
        table.Columns.Add("AptNum");
        table.Columns.Add("age");
        table.Columns.Add("AptDateTime", DateTime.class);
        //This will actually be DateTimeAskedToArrive
        table.Columns.Add("aptDateTime");
        //This will actually be DateTimeAskedToArrive
        table.Columns.Add("ClinicNum");
        //patient.ClinicNum
        table.Columns.Add("confirmed");
        table.Columns.Add("contactMethod");
        table.Columns.Add("email");
        //could be patient or guarantor email.
        table.Columns.Add("Guarantor");
        table.Columns.Add("medNotes");
        table.Columns.Add("nameF");
        //or preferred.
        table.Columns.Add("nameFL");
        table.Columns.Add("Note");
        table.Columns.Add("patientName");
        table.Columns.Add("PatNum");
        table.Columns.Add("PreferConfirmMethod");
        table.Columns.Add("ProcDescript");
        table.Columns.Add("TxtMsgOk");
        table.Columns.Add("WirelessPhone");
        List<DataRow> rows = new List<DataRow>();
        String command = "SELECT patient.PatNum," + "patient.LName," + "patient.FName,patient.Preferred,patient.LName, " + "patient.Guarantor,AptDateTime,patient.Birthdate,patient.ClinicNum,patient.HmPhone,patient.TxtMsgOk," + "patient.WkPhone,patient.WirelessPhone,ProcDescript,Confirmed,Note," + "patient.AddrNote,AptNum,patient.MedUrgNote,patient.PreferConfirmMethod," + "guar.Email guarEmail,patient.Email,patient.Premed,DateTimeAskedToArrive " + "FROM patient,appointment,patient guar " + "WHERE patient.PatNum=appointment.PatNum " + "AND patient.Guarantor=guar.PatNum " + "AND AptDateTime > " + POut.date(dateFrom) + " " + "AND AptDateTime < " + POut.Date(dateTo.AddDays(1)) + " " + "AND (AptStatus=1 " + "OR AptStatus=4) ";
        //scheduled
        //ASAP
        if (provNum > 0)
        {
            //only include doc if it's not a hyg appt
            //"AND (appointment.ProvNum="+POut.Long(provNum)
            //+" OR appointment.ProvHyg="+POut.Long(provNum)+") ";
            command += "AND ((appointment.ProvNum=" + POut.long(provNum) + " AND appointment.IsHygiene=0) " + " OR (appointment.ProvHyg=" + POut.long(provNum) + " AND appointment.IsHygiene=1)) ";
        }
         
        //only include hygienists if it's a hygiene appt
        if (clinicNum > 0)
        {
            command += "AND appointment.ClinicNum=" + POut.long(clinicNum) + " ";
        }
         
        if (showRecall && !showNonRecall)
        {
            command += "AND AptNum IN (" + "SELECT AptNum FROM procedurelog " + "INNER JOIN procedurecode ON procedurelog.CodeNum=procedurecode.CodeNum " + "AND procedurecode.IsHygiene=1) ";
        }
        else if (!showRecall && showNonRecall)
        {
            command += "AND AptNum NOT IN (" + "SELECT AptNum FROM procedurelog " + "INNER JOIN procedurecode ON procedurelog.CodeNum=procedurecode.CodeNum " + "AND procedurecode.IsHygiene=1) ";
        }
          
        command += "ORDER BY AptDateTime";
        DataTable rawtable = Db.getTable(command);
        DateTime dateT = new DateTime();
        DateTime timeAskedToArrive = new DateTime();
        Patient pat;
        ContactMethod contmeth = ContactMethod.None;
        for (int i = 0;i < rawtable.Rows.Count;i++)
        {
            row = table.NewRow();
            row["AddrNote"] = rawtable.Rows[i]["AddrNote"].ToString();
            row["AptNum"] = rawtable.Rows[i]["AptNum"].ToString();
            row["age"] = Patients.DateToAge(PIn.Date(rawtable.Rows[i]["Birthdate"].ToString())).ToString();
            //we don't care about m/y.
            dateT = PIn.DateT(rawtable.Rows[i]["AptDateTime"].ToString());
            timeAskedToArrive = PIn.DateT(rawtable.Rows[i]["DateTimeAskedToArrive"].ToString());
            if (timeAskedToArrive.Year > 1880)
            {
                dateT = timeAskedToArrive;
            }
             
            row["AptDateTime"] = dateT;
            row["aptDateTime"] = dateT.ToShortDateString() + "\r\n" + dateT.ToShortTimeString();
            row["ClinicNum"] = rawtable.Rows[i]["ClinicNum"].ToString();
            row["confirmed"] = DefC.GetName(DefCat.ApptConfirmed, PIn.Long(rawtable.Rows[i]["Confirmed"].ToString()));
            contmeth = (ContactMethod)PIn.Int(rawtable.Rows[i]["PreferConfirmMethod"].ToString());
            if (contmeth == ContactMethod.None || contmeth == ContactMethod.HmPhone)
            {
                row["contactMethod"] = Lans.g("FormConfirmList","Hm:") + rawtable.Rows[i]["HmPhone"].ToString();
            }
             
            if (contmeth == ContactMethod.WkPhone)
            {
                row["contactMethod"] = Lans.g("FormConfirmList","Wk:") + rawtable.Rows[i]["WkPhone"].ToString();
            }
             
            if (contmeth == ContactMethod.WirelessPh)
            {
                row["contactMethod"] = Lans.g("FormConfirmList","Cell:") + rawtable.Rows[i]["WirelessPhone"].ToString();
            }
             
            if (contmeth == ContactMethod.TextMessage)
            {
                row["contactMethod"] = Lans.g("FormConfirmList","Text:") + rawtable.Rows[i]["WirelessPhone"].ToString();
            }
             
            if (contmeth == ContactMethod.Email)
            {
                row["contactMethod"] = rawtable.Rows[i]["Email"].ToString();
            }
             
            if (contmeth == ContactMethod.DoNotCall || contmeth == ContactMethod.SeeNotes)
            {
                row["contactMethod"] = Lans.g("enumContactMethod", contmeth.ToString());
            }
             
            if (StringSupport.equals(rawtable.Rows[i]["Email"].ToString(), "") && !StringSupport.equals(rawtable.Rows[i]["guarEmail"].ToString(), ""))
            {
                row["email"] = rawtable.Rows[i]["guarEmail"].ToString();
            }
            else
            {
                row["email"] = rawtable.Rows[i]["Email"].ToString();
            } 
            row["Guarantor"] = rawtable.Rows[i]["Guarantor"].ToString();
            row["medNotes"] = "";
            if (StringSupport.equals(rawtable.Rows[i]["Premed"].ToString(), "1"))
            {
                row["medNotes"] = Lans.g("FormConfirmList","Premedicate");
            }
             
            if (!StringSupport.equals(rawtable.Rows[i]["MedUrgNote"].ToString(), ""))
            {
                if (!StringSupport.equals(row["medNotes"].ToString(), ""))
                {
                    row["medNotes"] += "\r\n";
                }
                 
                row["medNotes"] += rawtable.Rows[i]["MedUrgNote"].ToString();
            }
             
            pat = new Patient();
            pat.LName = rawtable.Rows[i]["LName"].ToString();
            pat.FName = rawtable.Rows[i]["FName"].ToString();
            pat.Preferred = rawtable.Rows[i]["Preferred"].ToString();
            row["nameF"] = pat.getNameFirstOrPreferred();
            row["nameFL"] = pat.getNameFirstOrPrefL();
            row["Note"] = rawtable.Rows[i]["Note"].ToString();
            row["patientName"] = pat.LName + "\r\n";
            if (!StringSupport.equals(pat.Preferred, ""))
            {
                row["patientName"] += "'" + pat.Preferred + "'";
            }
            else
            {
                row["patientName"] += pat.FName;
            } 
            row["PatNum"] = rawtable.Rows[i]["PatNum"].ToString();
            row["PreferConfirmMethod"] = rawtable.Rows[i]["PreferConfirmMethod"].ToString();
            row["ProcDescript"] = rawtable.Rows[i]["ProcDescript"].ToString();
            row["TxtMsgOk"] = rawtable.Rows[i]["TxtMsgOk"].ToString();
            row["WirelessPhone"] = rawtable.Rows[i]["WirelessPhone"].ToString();
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            //Array.Sort(orderDate,RecallList);
            //return RecallList;
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * Used in Confirm list to just get addresses.
    */
    public static DataTable getAddrTable(List<long> aptNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), aptNums);
        }
         
        String command = "SELECT patient.LName,patient.FName,patient.MiddleI,patient.Preferred," + "patient.Address,patient.Address2,patient.City,patient.State,patient.Zip,appointment.AptDateTime,appointment.ClinicNum " + "FROM patient,appointment " + "WHERE patient.PatNum=appointment.PatNum " + "AND (";
        for (int i = 0;i < aptNums.Count;i++)
        {
            if (i > 0)
            {
                command += " OR ";
            }
             
            command += "appointment.AptNum=" + aptNums[i].ToString();
        }
        command += ") ORDER BY appointment.AptDateTime";
        return Db.getTable(command);
    }

    /**
    * The newStatus will be a DefNum or 0.
    */
    public static void setConfirmed(long aptNum, long newStatus) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), aptNum, newStatus);
            return ;
        }
         
        String command = "UPDATE appointment SET Confirmed=" + POut.long(newStatus);
        if (PrefC.getLong(PrefName.AppointmentTimeArrivedTrigger) == newStatus)
        {
            command += ",DateTimeArrived=" + DbHelper.now();
        }
        else if (PrefC.getLong(PrefName.AppointmentTimeSeatedTrigger) == newStatus)
        {
            command += ",DateTimeSeated=" + DbHelper.now();
        }
        else if (PrefC.getLong(PrefName.AppointmentTimeDismissedTrigger) == newStatus)
        {
            command += ",DateTimeDismissed=" + DbHelper.now();
        }
           
        command += " WHERE AptNum=" + POut.long(aptNum);
        Db.nonQ(command);
        Plugins.hookAddCode(null,"Appointments.SetConfirmed_end",aptNum,newStatus);
    }

    /**
    * Sets the new pattern for an appointment.  This is how resizing is done.  Must contain only / and X, with each char representing 5 minutes.
    */
    public static void setPattern(long aptNum, String newPattern) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), aptNum, newPattern);
            return ;
        }
         
        String command = "UPDATE appointment SET Pattern='" + POut.string(newPattern) + "' WHERE AptNum=" + POut.long(aptNum);
        Db.nonQ(command);
    }

    /**
    * Use to send to unscheduled list, to set broken, etc.  Do not use to set complete.
    */
    public static void setAptStatus(long aptNum, ApptStatus newStatus) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), aptNum, newStatus);
            return ;
        }
         
        String command = "UPDATE appointment SET AptStatus=" + POut.Long(((Enum)newStatus).ordinal()) + " WHERE AptNum=" + POut.long(aptNum);
        Db.nonQ(command);
    }

    /**
    * The plan nums that are passed in are simply saved in columns in the appt.  Those two fields are used by approximately one office right now.
    */
    public static void setAptStatusComplete(long aptNum, long planNum1, long planNum2) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), aptNum, planNum1, planNum2);
            return ;
        }
         
        String command = "UPDATE appointment SET " + "AptStatus=" + POut.Long(((Enum)ApptStatus.Complete).ordinal()) + ", " + "InsPlan1=" + POut.long(planNum1) + ", " + "InsPlan2=" + POut.long(planNum2) + " " + "WHERE AptNum=" + POut.long(aptNum);
        Db.nonQ(command);
    }

    public static void setAptTimeLocked() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "UPDATE appointment SET TimeLocked=" + POut.bool(true);
        Db.nonQ(command);
    }

    public static Appointment tableToObject(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (table.Rows.Count == 0)
        {
            return null;
        }
         
        return Crud.AppointmentCrud.TableToList(table)[0];
    }

    /**
    * 
    */
    public static DataSet refreshPeriod(DateTime dateStart, DateTime dateEnd) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetDS(MethodBase.GetCurrentMethod(), dateStart, dateEnd);
        }
         
        DataSet retVal = new DataSet();
        DataTable tableAppt = GetPeriodApptsTable(dateStart, dateEnd, 0, false);
        retVal.Tables.Add(tableAppt);
        //parameters[0],parameters[1],"0","0"));
        retVal.Tables.Add(getPeriodEmployeeSchedTable(dateStart,dateEnd));
        retVal.Tables.Add(getPeriodWaitingRoomTable());
        retVal.Tables.Add(getPeriodSchedule(dateStart,dateEnd));
        retVal.Tables.Add(getApptFields(tableAppt));
        retVal.Tables.Add(getPatFields(tableAppt));
        return retVal;
    }

    /**
    * 
    */
    public static DataSet refreshOneApt(long aptNum, boolean isPlanned) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetDS(MethodBase.GetCurrentMethod(), aptNum, isPlanned);
        }
         
        DataSet retVal = new DataSet();
        retVal.Tables.Add(GetPeriodApptsTable(DateTime.MinValue, DateTime.MinValue, aptNum, isPlanned));
        return retVal;
    }

    /**
    * If aptnum is specified, then the dates are ignored.  If getting data for one planned appt, then pass isPlanned=1.  This changes which procedures are retrieved.
    */
    public static DataTable getPeriodApptsTable(DateTime dateStart, DateTime dateEnd, long aptNum, boolean isPlanned) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), dateStart, dateEnd, aptNum, isPlanned);
        }
         
        //DateTime dateStart=PIn.PDate(strDateStart);
        //DateTime dateEnd=PIn.PDate(strDateEnd);
        //int aptNum=PIn.PInt(strAptNum);
        //bool isPlanned=PIn.PBool(strIsPlanned);
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = new DataTable("Appointments");
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("age");
        table.Columns.Add("address");
        table.Columns.Add("addrNote");
        table.Columns.Add("apptModNote");
        table.Columns.Add("aptDate");
        table.Columns.Add("aptDay");
        table.Columns.Add("aptLength");
        table.Columns.Add("aptTime");
        table.Columns.Add("AptDateTime");
        table.Columns.Add("AptNum");
        table.Columns.Add("AptStatus");
        table.Columns.Add("Assistant");
        table.Columns.Add("assistantAbbr");
        table.Columns.Add("billingType");
        table.Columns.Add("chartNumber");
        table.Columns.Add("chartNumAndName");
        table.Columns.Add("ColorOverride");
        table.Columns.Add("confirmed");
        table.Columns.Add("Confirmed");
        table.Columns.Add("contactMethods");
        //table.Columns.Add("creditIns");
        table.Columns.Add("CreditType");
        table.Columns.Add("famFinUrgNote");
        table.Columns.Add("guardians");
        table.Columns.Add("hasIns[I]");
        table.Columns.Add("hmPhone");
        table.Columns.Add("ImageFolder");
        table.Columns.Add("insurance");
        table.Columns.Add("insToSend[!]");
        table.Columns.Add("IsHygiene");
        table.Columns.Add("lab");
        table.Columns.Add("medOrPremed[+]");
        table.Columns.Add("MedUrgNote");
        table.Columns.Add("Note");
        table.Columns.Add("Op");
        table.Columns.Add("patientName");
        table.Columns.Add("patientNameF");
        table.Columns.Add("PatNum");
        table.Columns.Add("patNum");
        table.Columns.Add("GuarNum");
        table.Columns.Add("patNumAndName");
        table.Columns.Add("Pattern");
        table.Columns.Add("preMedFlag");
        table.Columns.Add("procs");
        table.Columns.Add("procsColored");
        table.Columns.Add("production");
        table.Columns.Add("productionVal");
        table.Columns.Add("provider");
        table.Columns.Add("ProvHyg");
        table.Columns.Add("ProvNum");
        table.Columns.Add("timeAskedToArrive");
        table.Columns.Add("wkPhone");
        table.Columns.Add("wirelessPhone");
        table.Columns.Add("writeoffPPO");
        String command = "SELECT p1.Abbr ProvAbbr,p2.Abbr HygAbbr,patient.Address,patient.Address2,patient.AddrNote," + "patient.ApptModNote,AptDateTime,appointment.AptNum,AptStatus,Assistant," + "patient.BillingType,patient.BirthDate,patient.DateTimeDeceased," + "carrier1.CarrierName carrierName1,carrier2.CarrierName carrierName2," + "patient.ChartNumber,patient.City,appointment.ColorOverride,Confirmed,patient.CreditType,DateTimeChecked," + "DateTimeDue,DateTimeRecd,DateTimeSent,DateTimeAskedToArrive," + "guar.FamFinUrgNote,patient.FName,patient.Guarantor," + "COUNT(AllergyNum) hasAllergy," + "COUNT(DiseaseNum) hasDisease," + "patient.HmPhone,patient.ImageFolder,IsHygiene,IsNewPatient," + "LabCaseNum,patient.LName,patient.MedUrgNote,patient.MiddleI,Note,Op,appointment.PatNum," + "Pattern,COUNT(patplan.InsSubNum) hasIns,patient.PreferConfirmMethod,patient.PreferContactMethod,patient.Preferred," + "patient.PreferRecallMethod,patient.Premed," + "ProcDescript,ProcsColored,ProvHyg,appointment.ProvNum," + "patient.State,patient.WirelessPhone,patient.WkPhone,patient.Zip " + "FROM appointment " + "LEFT JOIN patient ON patient.PatNum=appointment.PatNum " + "LEFT JOIN provider p1 ON p1.ProvNum=appointment.ProvNum " + "LEFT JOIN provider p2 ON p2.ProvNum=appointment.ProvHyg ";
        if (isPlanned)
        {
            command += "LEFT JOIN labcase ON labcase.PlannedAptNum=appointment.AptNum AND labcase.PlannedAptNum!=0 ";
        }
        else
        {
            command += "LEFT JOIN labcase ON labcase.AptNum=appointment.AptNum AND labcase.AptNum!=0 ";
        } 
        //these four lines are very rarely made use of. They depend on the appointment.InsPlan1/2 being filled, which is unreliable.
        command += "LEFT JOIN patient guar ON guar.PatNum=patient.Guarantor " + "LEFT JOIN patplan ON patplan.PatNum=patient.PatNum AND patplan.Ordinal=1 " + "LEFT JOIN insplan plan1 ON InsPlan1=plan1.PlanNum " + "LEFT JOIN insplan plan2 ON InsPlan2=plan2.PlanNum " + "LEFT JOIN carrier carrier1 ON plan1.CarrierNum=carrier1.CarrierNum " + "LEFT JOIN carrier carrier2 ON plan2.CarrierNum=carrier2.CarrierNum " + "LEFT JOIN disease ON patient.PatNum=disease.PatNum AND disease.DiseaseDefNum <> " + POut.long(PrefC.getLong(PrefName.ProblemsIndicateNone)) + " " + "LEFT JOIN allergy ON patient.PatNum=allergy.PatNum AND allergy.AllergyDefNum <> " + POut.long(PrefC.getLong(PrefName.AllergiesIndicateNone)) + " ";
        if (aptNum == 0)
        {
            command += "WHERE AptDateTime >= " + POut.date(dateStart) + " " + "AND AptDateTime < " + POut.Date(dateEnd.AddDays(1)) + " " + "AND AptStatus IN (1, 2, 4, 5, 7, 8) ";
        }
        else
        {
            command += "WHERE appointment.AptNum=" + POut.long(aptNum);
        } 
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command += " GROUP BY appointment.AptNum";
        }
        else
        {
            //Oracle
            command += " GROUP BY p1.Abbr,p2.Abbr,patient.Address,patient.Address2,patient.AddrNote," + "patient.ApptModNote,AptDateTime,appointment.AptNum,AptStatus,Assistant," + "patient.BillingType,patient.BirthDate," + "carrier1.CarrierName,carrier2.CarrierName," + "patient.ChartNumber,patient.City,appointment.ColorOverride,Confirmed,patient.CreditType," + "DateTimeChecked,DateTimeDue,DateTimeRecd,DateTimeSent,DateTimeAskedToArrive," + "guar.FamFinUrgNote,patient.FName,patient.Guarantor,patient.HmPhone,patient.ImageFolder,IsHygiene,IsNewPatient," + "LabCaseNum,patient.LName,patient.MedUrgNote,patient.MiddleI,Note,Op,appointment.PatNum," + "Pattern,patient.PreferConfirmMethod,patient.PreferContactMethod,patient.Preferred," + "patient.PreferRecallMethod,patient.Premed," + "ProcDescript,ProcsColored,ProvHyg,appointment.ProvNum," + "patient.State,patient.WirelessPhone,patient.WkPhone,patient.Zip ";
        } 
        DataTable raw = dcon.getTable(command);
        //rawProc table was historically used for other purposes.  It is currently only used for production--------------------------
        //rawProcLab table is only used for Canada and goes hand in hand with the rawProc table, also only used for production.
        DataTable rawProc = new DataTable();
        DataTable rawProcLab = null;
        if (raw.Rows.Count == 0)
        {
            rawProc = new DataTable();
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canadian. en-CA or fr-CA
                rawProcLab = new DataTable();
            }
             
        }
        else
        {
            command = "SELECT AptNum,PlannedAptNum," + "ProcFee,";
            //AbbrDesc,procedurecode.CodeNum
            if (dateStart.Date < DateTime.Now.Date)
            {
                //Use the actual writeoff if looking at a date in the past, otherwise writeoff estimates will be used.
                command += "SUM(WriteOff) writeoffPPO,";
            }
            else
            {
                command += "SUM(CASE WHEN WriteOffEstOverride!=-1 THEN WriteOffEstOverride ELSE WriteOffEst END) writeoffPPO,";
            } 
            command += "procedurelog.ProcNum " + "FROM procedurelog " + "LEFT JOIN claimproc ON claimproc.ProcNum=procedurelog.ProcNum " + "AND (claimproc.WriteOffEst != -1 " + "OR claimproc.WriteOffEstOverride != -1) " + "WHERE ProcNumLab=0 AND ";
            //+"Surf,ToothNum,TreatArea  "
            //+"LEFT JOIN procedurecode ON procedurelog.CodeNum=procedurecode.CodeNum "
            if (isPlanned)
            {
                command += "PlannedAptNum!=0 AND PlannedAptNum ";
            }
            else
            {
                command += "AptNum!=0 AND AptNum ";
            } 
            command += "IN(";
            //this was far too slow:SELECT a.AptNum FROM appointment a WHERE ";
            if (aptNum == 0)
            {
                for (int a = 0;a < raw.Rows.Count;a++)
                {
                    if (a > 0)
                    {
                        command += ",";
                    }
                     
                    command += raw.Rows[a]["AptNum"].ToString();
                }
            }
            else
            {
                //command+="a.AptDateTime >= "+POut.PDate(dateStart)+" "
                //	+"AND a.AptDateTime < "+POut.PDate(dateEnd.AddDays(1));
                command += POut.long(aptNum);
            } 
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
            {
                command += ") GROUP BY procedurelog.ProcNum";
            }
            else
            {
                //Oracle
                command += ") GROUP BY procedurelog.ProcNum,AptNum,PlannedAptNum,ProcFee";
            } 
            rawProc = dcon.getTable(command);
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA") && rawProc.Rows.Count > 0)
            {
                //Canadian. en-CA or fr-CA
                command = "SELECT procedurelog.ProcNum,ProcNumLab,ProcFee,SUM(CASE WHEN WriteOffEstOverride!=-1 THEN WriteOffEstOverride ELSE WriteOffEst END) writeoffPPO " + "FROM procedurelog " + "LEFT JOIN claimproc ON claimproc.ProcNum=procedurelog.ProcNum " + "AND (claimproc.WriteOffEst != -1 " + "OR claimproc.WriteOffEstOverride != -1) " + "WHERE ProcNumLab IN (";
                for (int i = 0;i < rawProc.Rows.Count;i++)
                {
                    if (i > 0)
                    {
                        command += ",";
                    }
                     
                    command += rawProc.Rows[i]["ProcNum"].ToString();
                }
                if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
                {
                    command += ") GROUP BY procedurelog.ProcNum";
                }
                else
                {
                    //Oracle
                    command += ") GROUP BY procedurelog.ProcNum,ProcNumLab,ProcFee";
                } 
                rawProcLab = dcon.getTable(command);
            }
             
        } 
        //rawInsProc table is usually skipped. Too slow------------------------------------------------------------------------------
        DataTable rawInsProc = null;
        if (PrefC.getBool(PrefName.ApptExclamationShowForUnsentIns))
        {
            //procs for flag, InsNotSent
            //estimate
            //I'm sure this is the slow part.  Should be easy to make faster with less range
            command = "SELECT patient.PatNum, patient.Guarantor " + "FROM patient,procedurecode,procedurelog,claimproc " + "WHERE claimproc.procnum=procedurelog.procnum " + "AND patient.PatNum=procedurelog.PatNum " + "AND procedurelog.CodeNum=procedurecode.CodeNum " + "AND claimproc.NoBillIns=0 " + "AND procedurelog.ProcFee>0 " + "AND claimproc.Status=6 " + "AND ((CASE WHEN claimproc.InsEstTotalOverride>-1 THEN claimproc.InsEstTotalOverride ELSE claimproc.InsEstTotal END) > 0) " + "AND procedurelog.procstatus=2 " + "AND procedurelog.ProcDate >= " + POut.Date(DateTime.Now.AddYears(-1)) + " " + "AND procedurelog.ProcDate <= " + POut.Date(DateTime.Now) + " " + "GROUP BY patient.PatNum, patient.Guarantor";
            rawInsProc = dcon.getTable(command);
        }
         
        //Guardians-------------------------------------------------------------------------------------------------------------------
        command = "SELECT PatNumChild,PatNumGuardian,Relationship,patient.FName,patient.Preferred " + "FROM guardian " + "LEFT JOIN patient ON patient.PatNum=guardian.PatNumGuardian " + "WHERE IsGuardian<>0 AND PatNumChild IN (";
        if (raw.Rows.Count == 0)
        {
            command += "0";
        }
        else
            for (int i = 0;i < raw.Rows.Count;i++)
            {
                if (i > 0)
                {
                    command += ",";
                }
                 
                command += raw.Rows[i]["PatNum"].ToString();
            } 
        command += ") ORDER BY Relationship";
        DataTable rawGuardians = dcon.getTable(command);
        DateTime aptDate = new DateTime();
        TimeSpan span = new TimeSpan();
        int hours = new int();
        int minutes = new int();
        DateTime labDate = new DateTime();
        DateTime labDueDate = new DateTime();
        DateTime birthdate = new DateTime();
        DateTime timeAskedToArrive = new DateTime();
        double production = new double();
        double writeoffPPO = new double();
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            row = table.NewRow();
            row["address"] = Patients.GetAddressFull(raw.Rows[i]["Address"].ToString(), raw.Rows[i]["Address2"].ToString(), raw.Rows[i]["City"].ToString(), raw.Rows[i]["State"].ToString(), raw.Rows[i]["Zip"].ToString());
            row["addrNote"] = "";
            if (!StringSupport.equals(raw.Rows[i]["AddrNote"].ToString(), ""))
            {
                row["addrNote"] = Lans.g("Appointments","AddrNote: ") + raw.Rows[i]["AddrNote"].ToString();
            }
             
            aptDate = PIn.DateT(raw.Rows[i]["AptDateTime"].ToString());
            row["AptDateTime"] = aptDate;
            birthdate = PIn.Date(raw.Rows[i]["Birthdate"].ToString());
            DateTime dateTimeDeceased = PIn.Date(raw.Rows[i]["DateTimeDeceased"].ToString());
            DateTime dateTimeTo = DateTime.Now;
            if (dateTimeDeceased.Year > 1880)
            {
                dateTimeTo = dateTimeDeceased;
            }
             
            row["age"] = "";
            if (birthdate.AddYears(18) < dateTimeTo)
            {
                row["age"] = Lans.g("Appointments","Age: ");
            }
             
            //only show if older than 18
            if (birthdate.Year > 1880)
            {
                row["age"] += PatientLogic.dateToAgeString(birthdate,dateTimeTo);
            }
            else
            {
                row["age"] += "?";
            } 
            row["apptModNote"] = "";
            if (!StringSupport.equals(raw.Rows[i]["ApptModNote"].ToString(), ""))
            {
                row["apptModNote"] = Lans.g("Appointments","ApptModNote: ") + raw.Rows[i]["ApptModNote"].ToString();
            }
             
            row["aptDate"] = aptDate.ToShortDateString();
            row["aptDay"] = aptDate.ToString("dddd");
            span = TimeSpan.FromMinutes(raw.Rows[i]["Pattern"].ToString().Length * 5);
            hours = span.Hours;
            minutes = span.Minutes;
            if (hours == 0)
            {
                row["aptLength"] = minutes.ToString() + Lans.g("Appointments"," Min");
            }
            else if (hours == 1)
            {
                row["aptLength"] = hours.ToString() + Lans.g("Appointments"," Hr, ") + minutes.ToString() + Lans.g("Appointments"," Min");
            }
            else
            {
                row["aptLength"] = hours.ToString() + Lans.g("Appointments"," Hrs, ") + minutes.ToString() + Lans.g("Appointments"," Min");
            }  
            row["aptTime"] = aptDate.ToShortTimeString();
            row["AptNum"] = raw.Rows[i]["AptNum"].ToString();
            row["AptStatus"] = raw.Rows[i]["AptStatus"].ToString();
            row["Assistant"] = raw.Rows[i]["Assistant"].ToString();
            row["assistantAbbr"] = "";
            if (!StringSupport.equals(row["Assistant"].ToString(), "0"))
            {
                row["assistantAbbr"] = Employees.GetAbbr(PIn.Long(row["Assistant"].ToString()));
            }
             
            row["billingType"] = DefC.GetName(DefCat.BillingTypes, PIn.Long(raw.Rows[i]["BillingType"].ToString()));
            row["chartNumber"] = raw.Rows[i]["ChartNumber"].ToString();
            row["chartNumAndName"] = "";
            if (StringSupport.equals(raw.Rows[i]["IsNewPatient"].ToString(), "1"))
            {
                row["chartNumAndName"] = "NP-";
            }
             
            row["chartNumAndName"] += raw.Rows[i]["ChartNumber"].ToString() + " " + PatientLogic.GetNameLF(raw.Rows[i]["LName"].ToString(), raw.Rows[i]["FName"].ToString(), raw.Rows[i]["Preferred"].ToString(), raw.Rows[i]["MiddleI"].ToString());
            row["ColorOverride"] = raw.Rows[i]["ColorOverride"].ToString();
            row["confirmed"] = DefC.GetName(DefCat.ApptConfirmed, PIn.Long(raw.Rows[i]["Confirmed"].ToString()));
            row["Confirmed"] = raw.Rows[i]["Confirmed"].ToString();
            row["contactMethods"] = "";
            if (!StringSupport.equals(raw.Rows[i]["PreferConfirmMethod"].ToString(), "0"))
            {
                row["contactMethods"] += Lans.g("Appointments","Confirm Method: ") + ((ContactMethod)PIn.Long(raw.Rows[i]["PreferConfirmMethod"].ToString())).ToString();
            }
             
            if (!StringSupport.equals(raw.Rows[i]["PreferContactMethod"].ToString(), "0"))
            {
                if (!StringSupport.equals(row["contactMethods"].ToString(), ""))
                {
                    row["contactMethods"] += "\r\n";
                }
                 
                row["contactMethods"] += Lans.g("Appointments","Contact Method: ") + ((ContactMethod)PIn.Long(raw.Rows[i]["PreferContactMethod"].ToString())).ToString();
            }
             
            if (!StringSupport.equals(raw.Rows[i]["PreferRecallMethod"].ToString(), "0"))
            {
                if (!StringSupport.equals(row["contactMethods"].ToString(), ""))
                {
                    row["contactMethods"] += "\r\n";
                }
                 
                row["contactMethods"] += Lans.g("Appointments","Recall Method: ") + ((ContactMethod)PIn.Long(raw.Rows[i]["PreferRecallMethod"].ToString())).ToString();
            }
             
            boolean InsToSend = false;
            if (rawInsProc != null)
            {
                for (int j = 0;j < rawInsProc.Rows.Count;j++)
                {
                    //figure out if pt's family has ins claims that need to be created
                    if (!StringSupport.equals(raw.Rows[i]["hasIns"].ToString(), "0"))
                    {
                        if (raw.Rows[i]["Guarantor"].ToString() == rawInsProc.Rows[j]["Guarantor"].ToString() || raw.Rows[i]["Guarantor"].ToString() == rawInsProc.Rows[j]["PatNum"].ToString())
                        {
                            InsToSend = true;
                        }
                         
                    }
                     
                }
            }
             
            row["CreditType"] = raw.Rows[i]["CreditType"].ToString();
            row["famFinUrgNote"] = "";
            if (!StringSupport.equals(raw.Rows[i]["FamFinUrgNote"].ToString(), ""))
            {
                row["famFinUrgNote"] = Lans.g("Appointments","FamFinUrgNote: ") + raw.Rows[i]["FamFinUrgNote"].ToString();
            }
             
            row["guardians"] = "";
            GuardianRelationship guardRelat = GuardianRelationship.Father;
            for (int g = 0;g < rawGuardians.Rows.Count;g++)
            {
                if (raw.Rows[i]["PatNum"].ToString() == rawGuardians.Rows[g]["PatNumChild"].ToString())
                {
                    if (!StringSupport.equals(row["guardians"].ToString(), ""))
                    {
                        row["guardians"] += ",";
                    }
                     
                    guardRelat = (GuardianRelationship)PIn.Int(rawGuardians.Rows[g]["Relationship"].ToString());
                    row["guardians"] += Patients.GetNameFirstOrPreferred(rawGuardians.Rows[g]["FName"].ToString(), rawGuardians.Rows[g]["Preferred"].ToString()) + Guardians.getGuardianRelationshipStr(guardRelat);
                }
                 
            }
            row["hasIns[I]"] = "";
            if (!StringSupport.equals(raw.Rows[i]["hasIns"].ToString(), "0"))
            {
                row["hasIns[I]"] += "I";
            }
             
            row["hmPhone"] = Lans.g("Appointments","Hm: ") + raw.Rows[i]["HmPhone"].ToString();
            row["ImageFolder"] = raw.Rows[i]["ImageFolder"].ToString();
            row["insurance"] = "";
            if (!StringSupport.equals(raw.Rows[i]["carrierName1"].ToString(), ""))
            {
                row["insurance"] += raw.Rows[i]["carrierName1"].ToString();
                if (!StringSupport.equals(raw.Rows[i]["carrierName2"].ToString(), ""))
                {
                    //if(row["insurance"].ToString()!="") {
                    row["insurance"] += "\r\n";
                    //}
                    row["insurance"] += raw.Rows[i]["carrierName2"].ToString();
                }
                 
            }
            else if (!StringSupport.equals(raw.Rows[i]["hasIns"].ToString(), "0"))
            {
                row["insurance"] = Lans.g("Appointments","Insured");
            }
              
            row["insToSend[!]"] = "";
            if (InsToSend)
            {
                row["insToSend[!]"] = "!";
            }
             
            row["IsHygiene"] = raw.Rows[i]["IsHygiene"].ToString();
            row["lab"] = "";
            if (!StringSupport.equals(raw.Rows[i]["LabCaseNum"].ToString(), ""))
            {
                labDate = PIn.DateT(raw.Rows[i]["DateTimeChecked"].ToString());
                if (labDate.Year > 1880)
                {
                    row["lab"] = Lans.g("Appointments","Lab Quality Checked");
                }
                else
                {
                    labDate = PIn.DateT(raw.Rows[i]["DateTimeRecd"].ToString());
                    if (labDate.Year > 1880)
                    {
                        row["lab"] = Lans.g("Appointments","Lab Received");
                    }
                    else
                    {
                        labDate = PIn.DateT(raw.Rows[i]["DateTimeSent"].ToString());
                        if (labDate.Year > 1880)
                        {
                            row["lab"] = Lans.g("Appointments","Lab Sent");
                        }
                        else
                        {
                            //sent but not received
                            row["lab"] = Lans.g("Appointments","Lab Not Sent");
                        } 
                        labDueDate = PIn.DateT(raw.Rows[i]["DateTimeDue"].ToString());
                        if (labDueDate.Year > 1880)
                        {
                            //+dateDue.ToString("ddd")+" "
                            row["lab"] += ", " + Lans.g("Appointments","Due: ") + labDueDate.ToShortDateString();
                        }
                         
                    } 
                } 
            }
             
            //+" "+dateDue.ToShortTimeString();
            row["medOrPremed[+]"] = "";
            if (!StringSupport.equals(raw.Rows[i]["MedUrgNote"].ToString(), "") || StringSupport.equals(raw.Rows[i]["Premed"].ToString(), "1") || !StringSupport.equals(raw.Rows[i]["hasDisease"].ToString(), "0") || !StringSupport.equals(raw.Rows[i]["hasAllergy"].ToString(), "0"))
            {
                row["medOrPremed[+]"] = "+";
            }
             
            row["MedUrgNote"] = raw.Rows[i]["MedUrgNote"].ToString();
            row["Note"] = raw.Rows[i]["Note"].ToString();
            row["Op"] = raw.Rows[i]["Op"].ToString();
            if (StringSupport.equals(raw.Rows[i]["IsNewPatient"].ToString(), "1"))
            {
                row["patientName"] = "NP-";
            }
             
            row["patientName"] += PatientLogic.GetNameLF(raw.Rows[i]["LName"].ToString(), raw.Rows[i]["FName"].ToString(), raw.Rows[i]["Preferred"].ToString(), raw.Rows[i]["MiddleI"].ToString());
            row["patientNameF"] = raw.Rows[i]["FName"].ToString();
            row["PatNum"] = raw.Rows[i]["PatNum"].ToString();
            row["patNum"] = "PatNum: " + raw.Rows[i]["PatNum"].ToString();
            row["GuarNum"] = raw.Rows[i]["Guarantor"].ToString();
            row["patNumAndName"] = "";
            if (StringSupport.equals(raw.Rows[i]["IsNewPatient"].ToString(), "1"))
            {
                row["patNumAndName"] = "NP-";
            }
             
            row["patNumAndName"] += raw.Rows[i]["PatNum"].ToString() + " " + PatientLogic.GetNameLF(raw.Rows[i]["LName"].ToString(), raw.Rows[i]["FName"].ToString(), raw.Rows[i]["Preferred"].ToString(), raw.Rows[i]["MiddleI"].ToString());
            row["Pattern"] = raw.Rows[i]["Pattern"].ToString();
            row["preMedFlag"] = "";
            if (StringSupport.equals(raw.Rows[i]["Premed"].ToString(), "1"))
            {
                row["preMedFlag"] = Lans.g("Appointments","Premedicate");
            }
             
            row["procs"] = raw.Rows[i]["ProcDescript"].ToString();
            row["procsColored"] += raw.Rows[i]["ProcsColored"].ToString();
            production = 0;
            writeoffPPO = 0;
            if (rawProc != null)
            {
                for (int p = 0;p < rawProc.Rows.Count;p++)
                {
                    if (isPlanned && raw.Rows[i]["AptNum"].ToString() != rawProc.Rows[p]["PlannedAptNum"].ToString())
                    {
                        continue;
                    }
                    else if (!isPlanned && raw.Rows[i]["AptNum"].ToString() != rawProc.Rows[p]["AptNum"].ToString())
                    {
                        continue;
                    }
                      
                    production += PIn.Decimal(rawProc.Rows[p]["ProcFee"].ToString());
                    //WriteOffEst -1 and WriteOffEstOverride -1 already excluded
                    //production-=
                    writeoffPPO += PIn.Decimal(rawProc.Rows[p]["writeoffPPO"].ToString());
                    //frequently zero
                    if (rawProcLab != null)
                    {
                        for (int a = 0;a < rawProcLab.Rows.Count;a++)
                        {
                            //Will be null if not Canada.
                            if (rawProcLab.Rows[a]["ProcNumLab"].ToString() == rawProc.Rows[p]["ProcNum"].ToString())
                            {
                                production += PIn.Decimal(rawProcLab.Rows[a]["ProcFee"].ToString());
                                writeoffPPO += PIn.Decimal(rawProcLab.Rows[a]["writeoffPPO"].ToString());
                            }
                             
                        }
                    }
                     
                }
            }
             
            //frequently zero
            row["production"] = production.ToString("c");
            //PIn.Double(raw.Rows[i]["Production"].ToString()).ToString("c");
            row["productionVal"] = production.ToString();
            //raw.Rows[i]["Production"].ToString();
            if (StringSupport.equals(raw.Rows[i]["IsHygiene"].ToString(), "1"))
            {
                row["provider"] = raw.Rows[i]["HygAbbr"].ToString();
                if (!StringSupport.equals(raw.Rows[i]["ProvAbbr"].ToString(), ""))
                {
                    row["provider"] += " (" + raw.Rows[i]["ProvAbbr"].ToString() + ")";
                }
                 
            }
            else
            {
                row["provider"] = raw.Rows[i]["ProvAbbr"].ToString();
                if (!StringSupport.equals(raw.Rows[i]["HygAbbr"].ToString(), ""))
                {
                    row["provider"] += " (" + raw.Rows[i]["HygAbbr"].ToString() + ")";
                }
                 
            } 
            row["ProvNum"] = raw.Rows[i]["ProvNum"].ToString();
            row["ProvHyg"] = raw.Rows[i]["ProvHyg"].ToString();
            row["timeAskedToArrive"] = "";
            timeAskedToArrive = PIn.DateT(raw.Rows[i]["DateTimeAskedToArrive"].ToString());
            if (timeAskedToArrive.Year > 1880)
            {
                row["timeAskedToArrive"] = timeAskedToArrive.ToString("H:mm");
            }
             
            row["wirelessPhone"] = Lans.g("Appointments","Cell: ") + raw.Rows[i]["WirelessPhone"].ToString();
            row["wkPhone"] = Lans.g("Appointments","Wk: ") + raw.Rows[i]["WkPhone"].ToString();
            row["writeoffPPO"] = writeoffPPO.ToString();
            table.Rows.Add(row);
        }
        return table;
    }

    /**
    * Pass in the appointments table so that we can search based on appointments.
    */
    public static DataTable getApptFields(DataTable tableAppts) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<long> aptNums = new List<long>();
        for (int i = 0;i < tableAppts.Rows.Count;i++)
        {
            aptNums.Add(PIn.Long(tableAppts.Rows[i]["AptNum"].ToString()));
        }
        return GetApptFieldsByApptNums(aptNums);
    }

    /**
    * Only called from above method, but must be public for remoting.
    */
    public static DataTable getApptFieldsByApptNums(List<long> aptNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), aptNums);
        }
         
        String command = "SELECT AptNum,FieldName,FieldValue " + "FROM apptfield " + "WHERE AptNum IN (";
        if (aptNums.Count == 0)
        {
            command += "0";
        }
        else
            for (int i = 0;i < aptNums.Count;i++)
            {
                if (i > 0)
                {
                    command += ",";
                }
                 
                command += POut.Long(aptNums[i]);
            } 
        command += ")";
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = dcon.getTable(command);
        table.TableName = "ApptFields";
        return table;
    }

    /**
    * Pass in the appointments table so that we can search based on appointments.
    */
    public static DataTable getPatFields(DataTable tableAppts) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), tableAppts);
        }
         
        String command = "SELECT PatNum,FieldName,FieldValue " + "FROM patfield " + "WHERE PatNum IN (";
        if (tableAppts.Rows.Count == 0)
        {
            command += "0";
        }
        else
        {
            for (int i = 0;i < tableAppts.Rows.Count;i++)
            {
                if (i > 0)
                {
                    command += ",";
                }
                 
                command += tableAppts.Rows[i]["PatNum"].ToString();
            }
        } 
        command += ")";
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = dcon.getTable(command);
        table.TableName = "PatFields";
        return table;
    }

    /**
    * Pass in one aptNum
    */
    public static DataTable getApptFields(long aptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), aptNum);
        }
         
        String command = "SELECT ApptFieldNum,apptfielddef.FieldName,FieldValue " + "FROM apptfielddef " + "LEFT JOIN apptfield ON apptfielddef.FieldName=apptfield.FieldName " + "AND AptNum = " + POut.long(aptNum) + " " + "ORDER BY apptfielddef.FieldName";
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = dcon.getTable(command);
        table.TableName = "ApptFields";
        return table;
    }

    public static DataTable getPeriodEmployeeSchedTable(DateTime dateStart, DateTime dateEnd) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), dateStart, dateEnd);
        }
         
        //DateTime dateStart=PIn.PDate(strDateStart);
        //DateTime dateEnd=PIn.PDate(strDateEnd);
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = new DataTable("EmpSched");
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("empName");
        table.Columns.Add("schedule");
        table.Columns.Add("Note");
        if (dateStart != dateEnd)
        {
            return table;
        }
         
        //employee
        String command = "SELECT StartTime,StopTime,FName,employee.EmployeeNum,Note " + "FROM employee,schedule " + "WHERE schedule.EmployeeNum=employee.EmployeeNum " + "AND SchedType=3 " + "AND SchedDate = " + POut.date(dateStart) + " " + "ORDER BY FName";
        DataTable raw = dcon.getTable(command);
        DateTime startTime = new DateTime();
        DateTime stopTime = new DateTime();
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            row = table.NewRow();
            if (i == 0 || raw.Rows[i]["EmployeeNum"].ToString() != raw.Rows[i - 1]["EmployeeNum"].ToString())
            {
                row["empName"] = raw.Rows[i]["FName"].ToString();
            }
            else
            {
                row["empName"] = "";
            } 
            if (!StringSupport.equals(row["schedule"].ToString(), ""))
            {
                row["schedule"] += ",";
            }
             
            startTime = PIn.DateT(raw.Rows[i]["StartTime"].ToString());
            stopTime = PIn.DateT(raw.Rows[i]["StopTime"].ToString());
            row["schedule"] += startTime.ToString("h:mm") + "-" + stopTime.ToString("h:mm");
            row["Note"] = raw.Rows[i]["Note"].ToString();
            table.Rows.Add(row);
        }
        return table;
    }

    public static DataTable getPeriodWaitingRoomTable() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        //DateTime dateStart=PIn.PDate(strDateStart);
        //DateTime dateEnd=PIn.PDate(strDateEnd);
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = new DataTable("WaitingRoom");
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("patName");
        table.Columns.Add("waitTime");
        //midnight earlier today
        String command = "SELECT DateTimeArrived,DateTimeSeated,LName,FName,Preferred," + DbHelper.now() + " dateTimeNow " + "FROM appointment " + "JOIN patient ON appointment.PatNum=patient.PatNum " + "WHERE " + DbHelper.dateColumn("AptDateTime") + " = " + POut.Date(DateTime.Now) + " " + "AND DateTimeArrived > " + POut.Date(DateTime.Now) + " " + "AND DateTimeArrived < " + DbHelper.now() + " " + "AND " + DbHelper.dateColumn("DateTimeArrived") + "=" + DbHelper.dateColumn("AptDateTime") + " ";
        //prevents people from getting "stuck" in waiting room.
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            command += "AND TO_NUMBER(TO_CHAR(DateTimeSeated,'SSSSS')) = 0 ";
        }
        else
        {
            command += "AND TIME(DateTimeSeated) = 0 ";
        } 
        command += "AND AptStatus IN (" + POut.int(((Enum)ApptStatus.Complete).ordinal()) + "," + POut.int(((Enum)ApptStatus.Scheduled).ordinal()) + "," + POut.int(((Enum)ApptStatus.ASAP).ordinal()) + ") " + "ORDER BY AptDateTime";
        //None of the other statuses
        DataTable raw = dcon.getTable(command);
        TimeSpan timeArrived = new TimeSpan();
        //DateTime timeSeated;
        DateTime waitTime = new DateTime();
        Patient pat;
        DateTime dateTimeNow = new DateTime();
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            //int minutes;
            row = table.NewRow();
            pat = new Patient();
            pat.LName = raw.Rows[i]["LName"].ToString();
            pat.FName = raw.Rows[i]["FName"].ToString();
            pat.Preferred = raw.Rows[i]["Preferred"].ToString();
            row["patName"] = pat.getNameLF();
            dateTimeNow = PIn.DateT(raw.Rows[i]["dateTimeNow"].ToString());
            timeArrived = (PIn.DateT(raw.Rows[i]["DateTimeArrived"].ToString())).TimeOfDay;
            waitTime = dateTimeNow - timeArrived;
            row["waitTime"] = waitTime.ToString("H:mm:ss");
            //minutes=waitTime.Minutes;
            //if(waitTime.Hours>0){
            //	row["waitTime"]+=waitTime.Hours.ToString()+"h ";
            //minutes-=60*waitTime.Hours;
            //}
            //row["waitTime"]+=waitTime.Minutes.ToString()+"m";
            table.Rows.Add(row);
        }
        return table;
    }

    public static DataTable getPeriodSchedule(DateTime dateStart, DateTime dateEnd) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), dateStart, dateEnd);
        }
         
        DataTable table = new DataTable("Schedule");
        table.Columns.Add("ScheduleNum");
        table.Columns.Add("SchedDate");
        table.Columns.Add("StartTime");
        table.Columns.Add("StopTime");
        table.Columns.Add("SchedType");
        table.Columns.Add("ProvNum");
        table.Columns.Add("BlockoutType");
        table.Columns.Add("Note");
        table.Columns.Add("Status");
        table.Columns.Add("ops");
        table.Columns.Add("EmployeeNum");
        table.Columns.Add("DateTStamp");
        String command = "SELECT schedule.ScheduleNum,SchedDate,StartTime,StopTime,SchedType,ProvNum,BlockoutType,Note,Status,EmployeeNum,DateTStamp," + DbHelper.groupConcat("scheduleop.OperatoryNum") + " ops_ " + "FROM schedule " + "LEFT JOIN scheduleop ON schedule.ScheduleNum=scheduleop.ScheduleNum " + "WHERE SchedDate >= " + POut.date(dateStart) + " " + "AND SchedDate <= " + POut.date(dateEnd) + " ";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command += "GROUP BY schedule.ScheduleNum ";
        }
        else
        {
            //Oracle
            command += "GROUP BY schedule.ScheduleNum,SchedDate,StartTime,StopTime,SchedType,ProvNum,BlockoutType,Note,Status,EmployeeNum,DateTStamp ";
        } 
        command += "ORDER BY StartTime";
        DataTable raw = Db.getTable(command);
        DataRow row = new DataRow();
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            row = table.NewRow();
            row["ScheduleNum"] = raw.Rows[i]["ScheduleNum"].ToString();
            row["SchedDate"] = POut.Date(PIn.Date(raw.Rows[i]["SchedDate"].ToString()), false);
            row["StartTime"] = POut.Time(PIn.Time(raw.Rows[i]["StartTime"].ToString()), false);
            row["StopTime"] = POut.Time(PIn.Time(raw.Rows[i]["StopTime"].ToString()), false);
            row["SchedType"] = raw.Rows[i]["SchedType"].ToString();
            row["ProvNum"] = raw.Rows[i]["ProvNum"].ToString();
            row["BlockoutType"] = raw.Rows[i]["BlockoutType"].ToString();
            row["Note"] = raw.Rows[i]["Note"].ToString();
            row["Status"] = raw.Rows[i]["Status"].ToString();
            row["ops"] = PIn.byteArray(raw.Rows[i]["ops_"]);
            row["EmployeeNum"] = raw.Rows[i]["EmployeeNum"].ToString();
            row["DateTStamp"] = POut.Date(PIn.Date(raw.Rows[i]["DateTStamp"].ToString()), false);
            table.Rows.Add(row);
        }
        return table;
    }

    //Get DS for one appointment in Edit window--------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------------------------
    /**
    * 
    */
    public static DataSet getApptEdit(long aptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetDS(MethodBase.GetCurrentMethod(), aptNum);
        }
         
        DataSet retVal = new DataSet();
        retVal.Tables.Add(getApptTable(aptNum));
        retVal.Tables.Add(GetPatTable(retVal.Tables["Appointment"].Rows[0]["PatNum"].ToString()));
        retVal.Tables.Add(GetProcTable(retVal.Tables["Appointment"].Rows[0]["PatNum"].ToString(), aptNum.ToString(), retVal.Tables["Appointment"].Rows[0]["AptStatus"].ToString(), retVal.Tables["Appointment"].Rows[0]["AptDateTime"].ToString()));
        retVal.Tables.Add(GetCommTable(retVal.Tables["Appointment"].Rows[0]["PatNum"].ToString()));
        boolean isPlanned = false;
        if (StringSupport.equals(retVal.Tables["Appointment"].Rows[0]["AptStatus"].ToString(), "6"))
        {
            isPlanned = true;
        }
         
        retVal.Tables.Add(GetMiscTable(aptNum.ToString(), isPlanned));
        retVal.Tables.Add(getApptFields(aptNum));
        return retVal;
    }

    public static DataTable getApptTable(long aptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), aptNum);
        }
         
        String command = "SELECT * FROM appointment WHERE AptNum=" + aptNum.ToString();
        DataTable table = Db.getTable(command);
        table.TableName = "Appointment";
        return table;
    }

    public static DataTable getPatTable(String patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), patNum);
        }
         
        DataTable table = new DataTable("Patient");
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("field");
        table.Columns.Add("value");
        String command = "SELECT * FROM patient WHERE PatNum=" + patNum;
        DataTable rawPat = Db.getTable(command);
        DataRow row = new DataRow();
        //Patient Name--------------------------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Name");
        row["value"] = PatientLogic.GetNameLF(rawPat.Rows[0]["LName"].ToString(), rawPat.Rows[0]["FName"].ToString(), rawPat.Rows[0]["Preferred"].ToString(), rawPat.Rows[0]["MiddleI"].ToString());
        table.Rows.Add(row);
        //Patient First Name--------------------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","First Name");
        row["value"] = rawPat.Rows[0]["FName"];
        table.Rows.Add(row);
        //Patient Last name---------------------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Last Name");
        row["value"] = rawPat.Rows[0]["LName"];
        table.Rows.Add(row);
        //Patient middle initial----------------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Middle Initial");
        row["value"] = rawPat.Rows[0]["MiddleI"];
        table.Rows.Add(row);
        //Patient birthdate----------------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Birthdate");
        row["value"] = PIn.Date(rawPat.Rows[0]["Birthdate"].ToString()).ToShortDateString();
        table.Rows.Add(row);
        //Patient home phone--------------------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Home Phone");
        row["value"] = rawPat.Rows[0]["HmPhone"];
        table.Rows.Add(row);
        //Patient work phone--------------------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Work Phone");
        row["value"] = rawPat.Rows[0]["WkPhone"];
        table.Rows.Add(row);
        //Patient wireless phone----------------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Wireless Phone");
        row["value"] = rawPat.Rows[0]["WirelessPhone"];
        table.Rows.Add(row);
        //Patient credit type-------------------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Credit Type");
        row["value"] = rawPat.Rows[0]["CreditType"];
        table.Rows.Add(row);
        //Patient billing type------------------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Billing Type");
        row["value"] = DefC.GetName(DefCat.BillingTypes, PIn.Long(rawPat.Rows[0]["BillingType"].ToString()));
        table.Rows.Add(row);
        //Patient total balance-----------------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Total Balance");
        double totalBalance = PIn.Double(rawPat.Rows[0]["EstBalance"].ToString());
        row["value"] = totalBalance.ToString("F");
        table.Rows.Add(row);
        //Patient address and phone notes-------------------------------------------------------
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Address and Phone Notes");
        row["value"] = rawPat.Rows[0]["AddrNote"];
        table.Rows.Add(row);
        //Patient family balance----------------------------------------------------------------
        command = "SELECT BalTotal,InsEst FROM patient WHERE Guarantor='" + rawPat.Rows[0]["Guarantor"].ToString() + "'";
        DataTable familyBalance = Db.getTable(command);
        row = table.NewRow();
        row["field"] = Lans.g("FormApptEdit","Family Balance");
        double balance = PIn.Double(familyBalance.Rows[0]["BalTotal"].ToString()) - PIn.Double(familyBalance.Rows[0]["InsEst"].ToString());
        row["value"] = balance.ToString("F");
        table.Rows.Add(row);
        //Site----------------------------------------------------------------------------------
        if (!PrefC.getBool(PrefName.EasyHidePublicHealth))
        {
            row = table.NewRow();
            row["field"] = Lans.g("FormApptEdit","Site");
            row["value"] = Sites.GetDescription(PIn.Long(rawPat.Rows[0]["SiteNum"].ToString()));
            table.Rows.Add(row);
        }
         
        return table;
    }

    public static DataTable getProcTable(String patNum, String aptNum, String apptStatus, String aptDateTime) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), patNum, aptNum, apptStatus, aptDateTime);
        }
         
        DataTable table = new DataTable("Procedure");
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("AbbrDesc");
        table.Columns.Add("attached");
        //0 or 1
        table.Columns.Add("CodeNum");
        table.Columns.Add("descript");
        table.Columns.Add("fee");
        table.Columns.Add("priority");
        table.Columns.Add("Priority");
        table.Columns.Add("ProcCode");
        table.Columns.Add("ProcDate");
        table.Columns.Add("ProcNum");
        table.Columns.Add("ProcStatus");
        table.Columns.Add("ProvNum");
        table.Columns.Add("status");
        table.Columns.Add("Surf");
        table.Columns.Add("toothNum");
        table.Columns.Add("ToothNum");
        table.Columns.Add("ToothRange");
        table.Columns.Add("TreatArea");
        //but we won't actually fill this table with rows until the very end.  It's more useful to use a List<> for now.
        List<DataRow> rows = new List<DataRow>();
        String command = "SELECT AbbrDesc,procedurecode.ProcCode,AptNum,LaymanTerm," + "PlannedAptNum,Priority,ProcFee,ProcNum,ProcStatus, " + "procedurecode.Descript,procedurelog.CodeNum,ProcDate,procedurelog.ProvNum,Surf,ToothNum,ToothRange,TreatArea " + "FROM procedurelog LEFT JOIN procedurecode ON procedurelog.CodeNum=procedurecode.CodeNum " + "WHERE PatNum=" + patNum + " AND (ProcStatus=1 OR ";
        //sort later
        //1. All TP procs
        //tp
        //2. All attached procs
        //+" AND ";
        if (StringSupport.equals(apptStatus, "6"))
        {
            //planned
            command += "PlannedAptNum=" + aptNum;
        }
        else
        {
            command += "AptNum=" + aptNum;
        } 
        //exclude procs attached to other appts.
        //3. All unattached completed procs with same date as appt.
        //but only if one of these types
        if (StringSupport.equals(apptStatus, "1") || StringSupport.equals(apptStatus, "2") || StringSupport.equals(apptStatus, "4") || StringSupport.equals(apptStatus, "5"))
        {
            //sched,C,ASAP,broken
            DateTime aptDate = PIn.dateT(aptDateTime);
            //unattached
            //complete
            command += " OR (AptNum=0 " + "AND ProcStatus=2 " + "AND " + DbHelper.dateColumn("ProcDate") + "=" + POut.date(aptDate) + ")";
        }
         
        //same date
        command += ") " + "AND ProcStatus<>6 " + "AND IsCanadianLab=0";
        //Not deleted.
        DataTable rawProc = Db.getTable(command);
        for (int i = 0;i < rawProc.Rows.Count;i++)
        {
            row = table.NewRow();
            row["AbbrDesc"] = rawProc.Rows[i]["AbbrDesc"].ToString();
            if (StringSupport.equals(apptStatus, "6"))
            {
                //planned
                row["attached"] = (StringSupport.equals(rawProc.Rows[i]["PlannedAptNum"].ToString(), aptNum)) ? "1" : "0";
            }
            else
            {
                row["attached"] = (StringSupport.equals(rawProc.Rows[i]["AptNum"].ToString(), aptNum)) ? "1" : "0";
            } 
            row["CodeNum"] = rawProc.Rows[i]["CodeNum"].ToString();
            row["descript"] = "";
            if (StringSupport.equals(apptStatus, "6"))
            {
                //planned
                if (!StringSupport.equals(rawProc.Rows[i]["PlannedAptNum"].ToString(), "0") && !StringSupport.equals(rawProc.Rows[i]["PlannedAptNum"].ToString(), aptNum))
                {
                    row["descript"] = Lans.g("FormApptEdit","(other appt)");
                }
                 
            }
            else
            {
                if (!StringSupport.equals(rawProc.Rows[i]["AptNum"].ToString(), "0") && !StringSupport.equals(rawProc.Rows[i]["AptNum"].ToString(), aptNum))
                {
                    row["descript"] = Lans.g("FormApptEdit","(other appt)");
                }
                 
            } 
            if (StringSupport.equals(rawProc.Rows[i]["LaymanTerm"].ToString(), ""))
            {
                row["descript"] += rawProc.Rows[i]["Descript"].ToString();
            }
            else
            {
                row["descript"] += rawProc.Rows[i]["LaymanTerm"].ToString();
            } 
            if (!StringSupport.equals(rawProc.Rows[i]["ToothRange"].ToString(), ""))
            {
                row["descript"] += " #" + Tooth.FormatRangeForDisplay(rawProc.Rows[i]["ToothRange"].ToString());
            }
             
            row["fee"] = PIn.Double(rawProc.Rows[i]["ProcFee"].ToString()).ToString("F");
            row["priority"] = DefC.GetName(DefCat.TxPriorities, PIn.Long(rawProc.Rows[i]["Priority"].ToString()));
            row["Priority"] = rawProc.Rows[i]["Priority"].ToString();
            row["ProcCode"] = rawProc.Rows[i]["ProcCode"].ToString();
            row["ProcDate"] = rawProc.Rows[i]["ProcDate"].ToString();
            //eg 2012-02-19
            row["ProcNum"] = rawProc.Rows[i]["ProcNum"].ToString();
            row["ProcStatus"] = rawProc.Rows[i]["ProcStatus"].ToString();
            row["ProvNum"] = rawProc.Rows[i]["ProvNum"].ToString();
            row["status"] = ((OpenDentBusiness.ProcStat)PIn.Long(rawProc.Rows[i]["ProcStatus"].ToString())).ToString();
            row["Surf"] = rawProc.Rows[i]["Surf"].ToString();
            row["toothNum"] = Tooth.GetToothLabel(rawProc.Rows[i]["ToothNum"].ToString());
            row["ToothNum"] = rawProc.Rows[i]["ToothNum"].ToString();
            row["ToothRange"] = rawProc.Rows[i]["ToothRange"].ToString();
            row["TreatArea"] = rawProc.Rows[i]["TreatArea"].ToString();
            rows.Add(row);
        }
        //Sorting
        rows.Sort(CompareRows);
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * The supplied DataRows must include the following columns: attached,Priority,ToothRange,ToothNum,ProcCode. This sorts all objects in Chart module based on their dates, times, priority, and toothnum.  For time comparisons, procs are not included.  But if other types such as comm have a time component in ProcDate, then they will be sorted by time as well.
    */
    public static int compareRows(DataRow x, DataRow y) throws Exception {
        return ProcedureLogic.compareProcedures(x,y);
    }

    //No need to check RemotingRole; no call to db.
    /*if(x["attached"].ToString()!=y["attached"].ToString()){//if one is attached and the other is not
    				if(x["attached"].ToString()=="1"){
    					return -1;
    				}
    				else{
    					return 1;
    				}
    			}*/
    //sort by priority, toothnum, procCode
    public static DataTable getCommTable(String patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), patNum);
        }
         
        DataTable table = new DataTable("Comm");
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("commDateTime");
        table.Columns.Add("CommlogNum");
        table.Columns.Add("CommType");
        table.Columns.Add("Note");
        String command = "SELECT * FROM commlog WHERE PatNum=" + patNum + " ORDER BY CommDateTime";
        //+" AND IsStatementSent=0 "//don't include StatementSent
        DataTable rawComm = Db.getTable(command);
        for (int i = 0;i < rawComm.Rows.Count;i++)
        {
            row = table.NewRow();
            row["commDateTime"] = PIn.DateT(rawComm.Rows[i]["commDateTime"].ToString()).ToShortDateString();
            row["CommlogNum"] = rawComm.Rows[i]["CommlogNum"].ToString();
            row["CommType"] = rawComm.Rows[i]["CommType"].ToString();
            row["Note"] = rawComm.Rows[i]["Note"].ToString();
            table.Rows.Add(row);
        }
        return table;
    }

    public static DataTable getMiscTable(String aptNum, boolean isPlanned) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), aptNum, isPlanned);
        }
         
        DataTable table = new DataTable("Misc");
        DataRow row = new DataRow();
        table.Columns.Add("LabCaseNum");
        table.Columns.Add("labDescript");
        table.Columns.Add("requirements");
        String command = "SELECT LabCaseNum,DateTimeDue,DateTimeChecked,DateTimeRecd,DateTimeSent," + "laboratory.Description FROM labcase,laboratory " + "WHERE labcase.LaboratoryNum=laboratory.LaboratoryNum AND ";
        if (isPlanned)
        {
            command += "labcase.PlannedAptNum=" + aptNum;
        }
        else
        {
            command += "labcase.AptNum=" + aptNum;
        } 
        DataTable raw = Db.getTable(command);
        DateTime date = new DateTime();
        DateTime dateDue = new DateTime();
        //for(int i=0;i<raw.Rows.Count;i++) {//always return one row:
        row = table.NewRow();
        row["LabCaseNum"] = "0";
        row["labDescript"] = "";
        if (raw.Rows.Count > 0)
        {
            row["LabCaseNum"] = raw.Rows[0]["LabCaseNum"].ToString();
            row["labDescript"] = raw.Rows[0]["Description"].ToString();
            date = PIn.DateT(raw.Rows[0]["DateTimeChecked"].ToString());
            if (date.Year > 1880)
            {
                row["labDescript"] += ", " + Lans.g("FormApptEdit","Quality Checked");
            }
            else
            {
                date = PIn.DateT(raw.Rows[0]["DateTimeRecd"].ToString());
                if (date.Year > 1880)
                {
                    row["labDescript"] += ", " + Lans.g("FormApptEdit","Received");
                }
                else
                {
                    date = PIn.DateT(raw.Rows[0]["DateTimeSent"].ToString());
                    if (date.Year > 1880)
                    {
                        row["labDescript"] += ", " + Lans.g("FormApptEdit","Sent");
                    }
                    else
                    {
                        //sent but not received
                        row["labDescript"] += ", " + Lans.g("FormApptEdit","Not Sent");
                    } 
                    dateDue = PIn.DateT(raw.Rows[0]["DateTimeDue"].ToString());
                    if (dateDue.Year > 1880)
                    {
                        row["labDescript"] += ", " + Lans.g("FormAppEdit","Due: ") + dateDue.ToString("ddd") + " " + dateDue.ToShortDateString() + " " + dateDue.ToShortTimeString();
                    }
                     
                } 
            } 
        }
         
        //requirements-------------------------------------------------------------------------------------------
        //schoolcourse "
        command = "SELECT " + "reqstudent.Descript,LName,FName " + "FROM reqstudent,provider " + "WHERE reqstudent.ProvNum=provider.ProvNum " + "AND reqstudent.AptNum=" + aptNum;
        raw = Db.getTable(command);
        row["requirements"] = "";
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            if (i != 0)
            {
                row["requirements"] += "\r\n";
            }
             
            row["requirements"] += raw.Rows[i]["LName"].ToString() + ", " + raw.Rows[i]["FName"].ToString() + ": " + raw.Rows[i]["Descript"].ToString();
        }
        table.Rows.Add(row);
        return table;
    }

    //private static DataRow GetRowFromTable(
    /**
    * 
    */
    public static void delete(long aptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), aptNum);
            return ;
        }
         
        String command = new String();
        command = "SELECT PatNum,IsNewPatient,AptStatus FROM appointment WHERE AptNum=" + POut.long(aptNum);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count < 1)
        {
            return ;
        }
         
        //Already deleted or did not exist.
        Patient pat = Patients.GetPat(PIn.Long(table.Rows[0]["PatNum"].ToString()));
        if (StringSupport.equals(table.Rows[0]["IsNewPatient"].ToString(), "1"))
        {
            Procedures.SetDateFirstVisit(DateTime.MinValue, 3, pat);
        }
         
        //procs
        if (StringSupport.equals(table.Rows[0]["AptStatus"].ToString(), "6"))
        {
            //planned
            command = "UPDATE procedurelog SET PlannedAptNum =0 WHERE PlannedAptNum = " + POut.long(aptNum);
        }
        else
        {
            command = "UPDATE procedurelog SET AptNum =0 WHERE AptNum = " + POut.long(aptNum);
        } 
        Db.nonQ(command);
        //labcases
        if (StringSupport.equals(table.Rows[0]["AptStatus"].ToString(), "6"))
        {
            //planned
            command = "UPDATE labcase SET PlannedAptNum =0 WHERE PlannedAptNum = " + POut.long(aptNum);
        }
        else
        {
            command = "UPDATE labcase SET AptNum =0 WHERE AptNum = " + POut.long(aptNum);
        } 
        Db.nonQ(command);
        //plannedappt
        command = "DELETE FROM plannedappt WHERE AptNum=" + POut.long(aptNum);
        Db.nonQ(command);
        //we will not reset item orders here
        command = "DELETE FROM appointment WHERE AptNum = " + POut.long(aptNum);
        Db.nonQ(command);
        //apptfield
        command = "DELETE FROM apptfield WHERE AptNum = " + POut.long(aptNum);
        Db.nonQ(command);
        DeletedObjects.setDeleted(DeletedObjectType.Appointment,aptNum);
    }

    /**
    * If make5minute is false, then result will be in 10 or 15 minutes blocks and will need a later conversion step before going to db.
    */
    public static String calculatePattern(long provDent, long provHyg, List<long> codeNums, boolean make5minute) throws Exception {
        StringBuilder strBTime = new StringBuilder("");
        String procTime = "";
        ProcedureCode procCode;
        if (codeNums.Count == 1)
        {
            procCode = ProcedureCodes.GetProcCode(codeNums[0]);
            if (procCode.IsHygiene)
            {
                //hygiene proc
                procTime = ProcCodeNotes.GetTimePattern(provHyg, codeNums[0]);
            }
            else
            {
                //dentist proc
                procTime = ProcCodeNotes.GetTimePattern(provDent, codeNums[0]);
            } 
            strBTime.Append(procTime);
        }
        else
        {
            for (int i = 0;i < codeNums.Count;i++)
            {
                //multiple procs or no procs
                procCode = ProcedureCodes.GetProcCode(codeNums[i]);
                if (procCode.IsHygiene)
                {
                    //hygiene proc
                    procTime = ProcCodeNotes.GetTimePattern(provHyg, codeNums[i]);
                }
                else
                {
                    //dentist proc
                    procTime = ProcCodeNotes.GetTimePattern(provDent, codeNums[i]);
                } 
                if (procTime.Length < 2)
                {
                    continue;
                }
                 
                for (int n = 1;n < procTime.Length - 1;n++)
                {
                    if (StringSupport.equals(procTime.Substring(n, 1), "/"))
                    {
                        strBTime.Append("/");
                    }
                    else
                    {
                        strBTime.Insert(0, "X");
                    } 
                }
            }
        } 
        if (codeNums.Count > 1)
        {
            //multiple procs
            strBTime.Insert(0, "/");
            strBTime.Append("/");
        }
        else if (codeNums.Count == 0)
        {
            //0 procs
            strBTime.Append("/");
        }
          
        if (strBTime.Length > 39)
        {
            strBTime.Remove(39, strBTime.Length - 39);
        }
         
        Plugins.hookAddCode(null,"Appointments.CalculatePattern_end",strBTime,provDent,provHyg,codeNums);
        String pattern = strBTime.ToString();
        if (make5minute)
        {
            return convertPatternTo5(pattern);
        }
         
        return pattern;
    }

    public static String convertPatternTo5(String pattern) throws Exception {
        StringBuilder savePattern = new StringBuilder();
        for (int i = 0;i < pattern.Length;i++)
        {
            savePattern.Append(pattern.Substring(i, 1));
            if (PrefC.getLong(PrefName.AppointmentTimeIncrement) == 10)
            {
                savePattern.Append(pattern.Substring(i, 1));
            }
             
            if (PrefC.getLong(PrefName.AppointmentTimeIncrement) == 15)
            {
                savePattern.Append(pattern.Substring(i, 1));
                savePattern.Append(pattern.Substring(i, 1));
            }
             
        }
        if (savePattern.Length == 0)
        {
            savePattern = new StringBuilder("/");
        }
         
        return savePattern.ToString();
    }

    /**
    * Only called from the mobile server, not from any workstation.  Pass in an apptViewNum of 0 for now.  We might use that parameter later.
    */
    public static String getMobileBitmap(DateTime date, long apptViewNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), date, apptViewNum);
        }
         
        return POut.Bitmap(Resources.getApptBackTest(), ImageFormat.Gif);
    }

    //For testing pass a resource image.
    /**
    * Returns a list of appointments that are scheduled between start date and end date. This takes into account the length of the appointments.
    */
    public static List<Appointment> getAppointmentsForPeriod(DateTime start, DateTime end) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Appointment>>GetObject(MethodBase.GetCurrentMethod(), start, end);
        }
         
        String command = "SELECT * FROM appointment WHERE ";
        command += "AptDateTime >= " + POut.DateT(start.Date);
        //to check to see if an appointment scheduled beforehand overlaps this time segment
        command += "AND AptDateTime <= " + POut.dateT(end);
        List<Appointment> retVal = Crud.AppointmentCrud.TableToList(Db.getTable(command));
        for (int i = retVal.Count - 1;i >= 0;i--)
        {
            if (retVal[i].AptDateTime.AddMinutes(retVal[i].Pattern.Length * PrefC.getInt(PrefName.AppointmentTimeIncrement)) > start)
            {
                retVal.RemoveAt(i);
            }
             
        }
        return retVal;
    }

    /**
    * Returns true if the patient has any broken appointments, future appointments, unscheduled appointments, or unsched planned appointments.  This adds intelligence when user attempts to schedule an appointment by only showing the appointments for the patient when needed rather than always.
    */
    public static boolean hasPlannedEtc(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), patNum);
        }
         
        //future scheduled
        //planned appts that are already scheduled will also show because they are caught on the line above rather then on the next line
        //planned, not sched
        String command = "SELECT COUNT(*) FROM appointment " + "WHERE PatNum='" + POut.long(patNum) + "' " + "AND (AptStatus='" + POut.Long(((Enum)ApptStatus.Broken).ordinal()) + "' " + "OR AptStatus='" + POut.Long(((Enum)ApptStatus.UnschedList).ordinal()) + "' " + "OR (AptStatus='" + POut.Long(((Enum)ApptStatus.Scheduled).ordinal()) + "' AND AptDateTime > " + DbHelper.curdate() + " ) " + "OR (AptStatus='" + POut.Long(((Enum)ApptStatus.Planned).ordinal()) + "' " + "AND NOT EXISTS(SELECT * FROM appointment a2 WHERE a2.PatNum='" + POut.long(patNum) + "' AND a2.NextAptNum=appointment.AptNum)) " + ")";
        if (StringSupport.equals(Db.getScalar(command), "0"))
        {
            return false;
        }
         
        return true;
    }

}


