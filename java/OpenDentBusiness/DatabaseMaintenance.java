//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:04 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Account;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.AccountType;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.AutoCode;
import OpenDentBusiness.CanSupTransTypes;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.DatabaseMaintenance;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Laboratory;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Payment;
import OpenDentBusiness.Payments;
import OpenDentBusiness.PIn;
import OpenDentBusiness.PlannedAppt;
import OpenDentBusiness.PlannedAppts;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RecallTypes;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SchedStatus;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.Schedules;
import OpenDentBusiness.Signalods;
import OpenDentBusiness.Tooth;

public class DatabaseMaintenance   
{
    private static DataTable table = new DataTable();
    private static String command = new String();
    private static boolean success = false;
    public static boolean getSuccess() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod());
        }
         
        return DatabaseMaintenance.success;
    }

    public static String mySQLTables(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        success = true;
        if (OpenDentBusiness.DataConnection.DBtype != OpenDentBusiness.DatabaseType.MySql)
        {
            return "";
        }
         
        command = "DROP TABLE IF EXISTS `signal`";
        //Signal is keyword for MySQL 5.5.  Was renamed to signalod so drop if exists.
        Db.nonQ(command);
        command = "SHOW TABLES";
        table = Db.getTable(command);
        String[] tableNames = new String[table.Rows.Count];
        int lastRow = new int();
        boolean allOK = true;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            tableNames[i] = table.Rows[i][0].ToString();
        }
        for (int i = 0;i < tableNames.Length;i++)
        {
            command = "CHECK TABLE " + tableNames[i];
            table = Db.getTable(command);
            lastRow = table.Rows.Count - 1;
            String status = PIn.byteArray(table.Rows[lastRow][3]);
            if (!StringSupport.equals(status, "OK"))
            {
                log += Lans.g("FormDatabaseMaintenance","Corrupt file found for table") + " " + tableNames[i] + "\r\n";
                allOK = false;
            }
             
        }
        if (allOK)
        {
            if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","No corrupted tables.") + "\r\n";
            }
             
        }
        else
        {
            success = false;
            //no other checks should be done until we can successfully get past this.
            log += Lans.g("FormDatabaseMaintenance","Corrupted files found.  Run the optimize tool to repair them, then click check to be sure they were really fixed.") + "\r\n" + Lans.g("FormDatabaseMaintenance","Done.");
        } 
        return log;
    }

    /**
    * If using MySQL, tries to make a backup of the database and then optimizes and repairs each table.  Returns true if a backup was made.
    * We have to backup the database before running the repair commands because it has a tendency to delete data it cannot understand which is problematic.
    * Currently called whenever MySQL is upgraded and when users click Optimize in database maintenance.
    */
    public static void backupRepairAndOptimize() throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype != OpenDentBusiness.DatabaseType.MySql)
        {
            return ;
        }
         
        OpenDentBusiness.MiscData.makeABackup();
        command = "SHOW TABLES";
        table = Db.getTable(command);
        String[] tableNames = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            tableNames[i] = table.Rows[i][0].ToString();
        }
        for (int i = 0;i < tableNames.Length;i++)
        {
            command = "OPTIMIZE TABLE `" + tableNames[i] + "`";
            Db.nonQ(command);
        }
        for (int i = 0;i < tableNames.Length;i++)
        {
            command = "REPAIR TABLE `" + tableNames[i] + "`";
            Db.nonQ(command);
        }
    }

    public static String datesNoZeros(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "";
        }
         
        //This check is not valid for Oracle, because each of the following fields are defined as non-null,
        //and 0000-00-00 is not a valid Oracle date.
        if (isCheck)
        {
            String[] commands = new String[]{ "SELECT COUNT(*) FROM adjustment WHERE AdjDate='0000-00-00'", "SELECT COUNT(*) FROM adjustment WHERE DateEntry<'1980'", "SELECT COUNT(*) FROM adjustment WHERE ProcDate='0000-00-00'", "SELECT COUNT(*) FROM appointment WHERE AptDateTime LIKE '0000-00-00%'", "SELECT COUNT(*) FROM appointment WHERE DateTimeArrived LIKE '0000-00-00%'", "SELECT COUNT(*) FROM appointment WHERE DateTimeAskedToArrive LIKE '0000-00-00%'", "SELECT COUNT(*) FROM appointment WHERE DateTimeSeated LIKE '0000-00-00%'", "SELECT COUNT(*) FROM appointment WHERE DateTimeDismissed LIKE '0000-00-00%'", "SELECT COUNT(*) FROM appointment WHERE DateTStamp='0000-00-00 00:00:00'", "SELECT COUNT(*) FROM claim WHERE DateService='0000-00-00'", "SELECT COUNT(*) FROM claim WHERE DateSent='0000-00-00'", "SELECT COUNT(*) FROM claim WHERE DateReceived='0000-00-00'", "SELECT COUNT(*) FROM claim WHERE PriorDate='0000-00-00'", "SELECT COUNT(*) FROM claim WHERE AccidentDate='0000-00-00'", "SELECT COUNT(*) FROM claim WHERE OrthoDate='0000-00-00'", "SELECT COUNT(*) FROM claimpayment WHERE CheckDate='0000-00-00'", "SELECT COUNT(*) FROM claimproc WHERE DateCP='0000-00-00'", "SELECT COUNT(*) FROM claimproc WHERE ProcDate='0000-00-00'", "SELECT COUNT(*) FROM inssub WHERE DateEffective='0000-00-00'", "SELECT COUNT(*) FROM inssub WHERE DateTerm='0000-00-00'", "SELECT COUNT(*) FROM patient WHERE Birthdate='0000-00-00'", "SELECT COUNT(*) FROM patient WHERE DateFirstVisit='0000-00-00'", "SELECT COUNT(*) FROM procedurelog WHERE ProcDate LIKE '0000-00-00%'", "SELECT COUNT(*) FROM procedurelog WHERE DateOriginalProsth='0000-00-00'", "SELECT COUNT(*) FROM procedurelog WHERE DateEntryC='0000-00-00'", "SELECT COUNT(*) FROM procedurelog WHERE DateTP='0000-00-00'", "SELECT COUNT(*) FROM recall WHERE DateDueCalc='0000-00-00'", "SELECT COUNT(*) FROM recall WHERE DateDue='0000-00-00'", "SELECT COUNT(*) FROM recall WHERE DatePrevious='0000-00-00'", "SELECT COUNT(*) FROM recall WHERE DateScheduled='0000-00-00'", "SELECT COUNT(*) FROM recall WHERE DisableUntilDate='0000-00-00'", "SELECT COUNT(*) FROM schedule WHERE SchedDate='0000-00-00'", "SELECT COUNT(*) FROM signalod WHERE DateViewing='0000-00-00'", "SELECT COUNT(*) FROM signalod WHERE SigDateTime LIKE '0000-00-00%'", "SELECT COUNT(*) FROM signalod WHERE AckTime LIKE '0000-00-00%'" };
            int numInvalidDates = 0;
            for (int i = 0;i < commands.Length;i++)
            {
                numInvalidDates += PIn.Int(Db.GetCount(commands[i]));
            }
            if (numInvalidDates > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Dates invalid:") + " " + numInvalidDates + "\r\n";
            }
             
        }
        else
        {
            String[] commands = new String[]{ "UPDATE adjustment SET AdjDate='0001-01-01' WHERE AdjDate='0000-00-00'", "UPDATE adjustment SET DateEntry='1980-01-01' WHERE DateEntry<'1980'", "UPDATE adjustment SET ProcDate='0001-01-01' WHERE ProcDate='0000-00-00'", "UPDATE appointment SET AptDateTime='0001-01-01 00:00:00' WHERE AptDateTime LIKE '0000-00-00%'", "UPDATE appointment SET DateTimeArrived='0001-01-01 00:00:00' WHERE DateTimeArrived LIKE '0000-00-00%'", "UPDATE appointment SET DateTimeAskedToArrive='0001-01-01 00:00:00' WHERE DateTimeAskedToArrive LIKE '0000-00-00%'", "UPDATE appointment SET DateTimeSeated='0001-01-01 00:00:00' WHERE DateTimeSeated LIKE '0000-00-00%'", "UPDATE appointment SET DateTimeDismissed='0001-01-01 00:00:00' WHERE DateTimeDismissed LIKE '0000-00-00%'", "UPDATE appointment SET DateTStamp='2009-08-24 00:00:00' WHERE DateTStamp='0000-00-00 00:00:00'", "UPDATE claim SET DateService='0001-01-01' WHERE DateService='0000-00-00'", "UPDATE claim SET DateSent='0001-01-01' WHERE DateSent='0000-00-00'", "UPDATE claim SET DateReceived='0001-01-01' WHERE DateReceived='0000-00-00'", "UPDATE claim SET PriorDate='0001-01-01' WHERE PriorDate='0000-00-00'", "UPDATE claim SET AccidentDate='0001-01-01' WHERE AccidentDate='0000-00-00'", "UPDATE claim SET OrthoDate='0001-01-01' WHERE OrthoDate='0000-00-00'", "UPDATE claimpayment SET CheckDate='0001-01-01' WHERE CheckDate='0000-00-00'", "UPDATE claimproc SET DateCP='0001-01-01' WHERE DateCP='0000-00-00'", "UPDATE claimproc SET ProcDate='0001-01-01' WHERE ProcDate='0000-00-00'", "UPDATE inssub SET DateEffective='0001-01-01' WHERE DateEffective='0000-00-00'", "UPDATE inssub SET DateTerm='0001-01-01' WHERE DateTerm='0000-00-00'", "UPDATE patient SET Birthdate='0001-01-01' WHERE Birthdate='0000-00-00'", "UPDATE patient SET DateFirstVisit='0001-01-01' WHERE DateFirstVisit='0000-00-00'", "UPDATE procedurelog SET ProcDate='0001-01-01 00:00:00' WHERE ProcDate LIKE '0000-00-00%'", "UPDATE procedurelog SET DateOriginalProsth='0001-01-01' WHERE DateOriginalProsth='0000-00-00'", "UPDATE procedurelog SET DateEntryC='0001-01-01' WHERE DateEntryC='0000-00-00'", "UPDATE procedurelog SET DateTP='0001-01-01' WHERE DateTP='0000-00-00'", "UPDATE recall SET DateDueCalc='0001-01-01' WHERE DateDueCalc='0000-00-00'", "UPDATE recall SET DateDue='0001-01-01' WHERE DateDue='0000-00-00'", "UPDATE recall SET DatePrevious='0001-01-01' WHERE DatePrevious='0000-00-00'", "UPDATE recall SET DateScheduled='0001-01-01' WHERE DateScheduled='0000-00-00'", "UPDATE recall SET DisableUntilDate='0001-01-01' WHERE DisableUntilDate='0000-00-00'", "UPDATE schedule SET SchedDate='0001-01-01' WHERE SchedDate='0000-00-00'", "UPDATE signalod SET DateViewing='0001-01-01' WHERE DateViewing='0000-00-00'", "UPDATE signalod SET SigDateTime='0001-01-01 00:00:00' WHERE SigDateTime LIKE '0000-00-00%'", "UPDATE signalod SET AckTime='0001-01-01 00:00:00' WHERE AckTime LIKE '0000-00-00%'" };
            long rowsChanged = 0;
            for (int i = 0;i < commands.Length;i++)
            {
                rowsChanged += Db.NonQ(commands[i]);
            }
            if (rowsChanged != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Dates fixed. Rows changed:") + " " + rowsChanged.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String decimalValues(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        return log;
    }

    //This specific fix is no longer needed, since we are using ROUND(EstBalance,2) in the aging calculation now.
    //However, it is still a problem in many columns that we will eventually need to deal with.
    //Maybe add this back when users can control which fixes they make.
    //One problem is the foreign users do not necessarily use 2 decimal places (Kuwait uses 3).
    /**
    * /Holds columns to be checked. Strings are in pairs in the following order: table-name,column-name
    */
    //string[] decimalCols=new string[] {
    //  "patient","EstBalance"
    //};
    //int decimalPlacessToRoundTo=8;
    //long numberFixed=0;
    //for(int i=0;i<decimalCols.Length;i+=2) {
    //  string tablename=decimalCols[i];
    //  string colname=decimalCols[i+1];
    //  string command="UPDATE "+tablename+" SET "+colname+"=ROUND("+colname+","+decimalPlacessToRoundTo
    //    +") WHERE "+colname+"!=ROUND("+colname+","+decimalPlacessToRoundTo+")";
    //  numberFixed+=Db.NonQ(command);
    //}
    //if(numberFixed>0 || verbose) {
    //  log+=Lans.g("FormDatabaseMaintenance","Decimal values fixed: ")+numberFixed.ToString()+"\r\n";
    //}
    //also checks patient.AddrNote
    public static String specialCharactersInNotes(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        //this will run for fix or check, but will only fix if the special char button is used
        //Fix code is in a dedicated button "Spec Char"
        command = "SELECT * FROM appointment WHERE (ProcDescript REGEXP '[^[:alnum:]^[:space:]^[:punct:]]+') OR (Note REGEXP '[^[:alnum:]^[:space:]^[:punct:]]+')";
        List<Appointment> apts = Crud.AppointmentCrud.SelectMany(command);
        List<char> specialCharsFound = new List<char>();
        int specialCharCount = 0;
        int intC = 0;
        for (Object __dummyForeachVar2 : apts)
        {
            Appointment apt = (Appointment)__dummyForeachVar2;
            for (Object __dummyForeachVar0 : apt.Note)
            {
                char c = (Character)__dummyForeachVar0;
                intC = (int)c;
                //31 - 126 are all safe.
                //"Horizontal Tabulation"
                //Line Feed
                if ((intC < 126 && intC > 31) || intC == 9 || intC == 10 || intC == 13)
                {
                    continue;
                }
                 
                //carriage return
                specialCharCount++;
                if (specialCharsFound.Contains(c))
                {
                    continue;
                }
                 
                specialCharsFound.Add(c);
            }
            for (Object __dummyForeachVar1 : apt.ProcDescript)
            {
                char c = (Character)__dummyForeachVar1;
                //search every character in ProcDescript
                intC = (int)c;
                //31 - 126 are all safe.
                //"Horizontal Tabulation"
                //Line Feed
                if ((intC < 126 && intC > 31) || intC == 9 || intC == 10 || intC == 13)
                {
                    continue;
                }
                 
                //carriage return
                specialCharCount++;
                if (specialCharsFound.Contains(c))
                {
                    continue;
                }
                 
                specialCharsFound.Add(c);
            }
        }
        command = "SELECT * FROM patient WHERE AddrNote REGEXP '[^[:alnum:]^[:space:]]+'";
        List<Patient> pats = OpenDentBusiness.Crud.PatientCrud.SelectMany(command);
        intC = 0;
        for (Object __dummyForeachVar4 : pats)
        {
            Patient pat = (Patient)__dummyForeachVar4;
            for (Object __dummyForeachVar3 : pat.AddrNote)
            {
                char c = (Character)__dummyForeachVar3;
                intC = (int)c;
                //31 - 126 are all safe.
                //"Horizontal Tabulation"
                //Line Feed
                if ((intC < 126 && intC > 31) || intC == 9 || intC == 10 || intC == 13)
                {
                    continue;
                }
                 
                //carriage return
                specialCharCount++;
                if (specialCharsFound.Contains(c))
                {
                    continue;
                }
                 
                specialCharsFound.Add(c);
            }
        }
        for (Object __dummyForeachVar5 : specialCharsFound)
        {
            char c = (Character)__dummyForeachVar5;
            log += c.ToString() + " doesn't work.\r\n";
        }
        command = "SELECT COUNT(*) FROM adjustment WHERE AdjNote LIKE '%" + POut.string("\0") + "%'";
        specialCharCount += PIn.int(Db.getCount(command));
        command = "SELECT COUNT(*) FROM definition WHERE ItemName LIKE '%" + POut.string("\0") + "%'";
        specialCharCount += PIn.int(Db.getCount(command));
        command = "SELECT COUNT(*) FROM payment WHERE PayNote LIKE '%" + POut.string("\0") + "%'";
        specialCharCount += PIn.int(Db.getCount(command));
        if (specialCharCount != 0 || verbose)
        {
            log += specialCharCount.ToString() + " " + Lans.g("FormDatabaseMaintenance","total special characters found.  The Spec Char button below will remove these characters.") + "\r\n";
        }
         
        return log;
    }

    //Methods that apply to specific tables----------------------------------------------------------------------------------
    public static String appointmentCompleteWithTpAttached(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT DISTINCT appointment.PatNum, " + DbHelper.concat("LName","\", \"","FName") + " AS PatName, AptDateTime " + "FROM appointment " + "INNER JOIN patient ON patient.PatNum=appointment.PatNum " + "INNER JOIN procedurelog ON procedurelog.AptNum=appointment.AptNum " + "WHERE AptStatus=" + POut.int(((Enum)ApptStatus.Complete).ordinal()) + " " + "AND procedurelog.ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.TP).ordinal()) + " " + "ORDER BY PatName";
        table = Db.getTable(command);
        //Both check and fix need to alert the user to fix manually.
        if (table.Rows.Count > 0 || verbose)
        {
            log += Lans.g("FormDatabaseMaintenance","Completed appointments with treatment planned procedures attached") + ": " + table.Rows.Count + ", " + Lans.g("FormDatabaseMaintenance","including") + ":\r\n";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (i > 10)
                {
                    break;
                }
                 
                log += "   " + table.Rows[i]["PatNum"].ToString() + "-" + table.Rows[i]["PatName"].ToString() + "  Appt Date:" + PIn.DateT(table.Rows[i]["AptDateTime"].ToString()).ToShortDateString();
                log += "\r\n";
            }
            if (table.Rows.Count > 0)
            {
                log += Lans.g("FormDatabaseMaintenance","   They need to be fixed manually.") + "\r\n";
            }
             
        }
         
        return log;
    }

    public static String appointmentsNoPattern(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT AptNum FROM appointment WHERE Pattern=\'\'";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Appointments found with zero length: ") + table.Rows.Count.ToString() + "\r\n";
            }
             
        }
        else
        {
            if (table.Rows.Count > 0)
            {
                //detach all procedures
                if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
                {
                    command = "UPDATE procedurelog P SET P.AptNum = 0 WHERE (SELECT A.Pattern FROM appointment A WHERE A.AptNum=P.AptNum AND ROWNUM<=1)=''";
                }
                else
                {
                    command = "UPDATE procedurelog P, appointment A SET P.AptNum = 0 WHERE P.AptNum = A.AptNum AND A.Pattern = ''";
                } 
                Db.nonQ(command);
                if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
                {
                    command = "UPDATE procedurelog P SET P.PlannedAptNum = 0 WHERE (SELECT A.Pattern FROM appointment A WHERE A.AptNum=P.PlannedAptNum AND ROWNUM<=1)=''";
                }
                else
                {
                    command = "UPDATE procedurelog P, appointment A SET P.PlannedAptNum = 0 WHERE P.PlannedAptNum = A.AptNum AND A.Pattern = ''";
                } 
                Db.nonQ(command);
                command = "DELETE FROM appointment WHERE Pattern = ''";
                Db.nonQ(command);
            }
             
            int numberFixed = table.Rows.Count;
            if (numberFixed != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Appointments deleted with zero length: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String appointmentsNoDateOrProcs(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            //scheduled
            command = "SELECT COUNT(*) FROM appointment " + "WHERE AptStatus=1 " + "AND " + DbHelper.year("AptDateTime") + "<1880 " + "AND NOT EXISTS(SELECT * FROM procedurelog WHERE procedurelog.AptNum=appointment.AptNum)";
            //scheduled but no date
            //and no procs
            int numFound = PIn.int(Db.getCount(command));
            if (numFound != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Appointments found with no date and no procs: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //scheduled
            command = "DELETE FROM appointment " + "WHERE AptStatus=1 " + "AND " + DbHelper.year("AptDateTime") + "<1880 " + "AND NOT EXISTS(SELECT * FROM procedurelog WHERE procedurelog.AptNum=appointment.AptNum)";
            //scheduled but no date
            //and no procs
            long numberFixed = Db.nonQ(command);
            if (numberFixed != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Appointments deleted due to no date and no procs: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String appointmentsNoPatients(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT Count(*) FROM appointment WHERE PatNum NOT IN(SELECT PatNum FROM patient)";
        int count = PIn.int(Db.getCount(command));
        if (isCheck)
        {
            if (count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Appointments found abandoned: ") + count.ToString() + "\r\n";
            }
             
        }
        else
        {
            //Fix is safe because we are not deleting data, we are just attaching abandoned appointments to a dummy patient.
            long numfixed = 0;
            if (count != 0)
            {
                Patient dummyPatient = new Patient();
                dummyPatient.FName = "MISSING";
                dummyPatient.LName = "PATIENT";
                dummyPatient.AddrNote = "Appointments with missing patients were assigned to this patient on " + DateTime.Now.ToShortDateString() + " while doing database maintenance.";
                dummyPatient.Birthdate = DateTime.MinValue;
                dummyPatient.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
                dummyPatient.PatStatus = PatientStatus.Archived;
                dummyPatient.PriProv = PrefC.getLong(PrefName.PracticeDefaultProv);
                long dummyPatNum = Patients.insert(dummyPatient,false);
                Patient oldDummyPatient = dummyPatient.copy();
                dummyPatient.Guarantor = dummyPatNum;
                Patients.update(dummyPatient,oldDummyPatient);
                command = "UPDATE appointment SET PatNum=" + POut.long(dummyPatNum) + " WHERE PatNum NOT IN(SELECT PatNum FROM patient)";
                numfixed = Db.nonQ(command);
            }
             
            if (numfixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Appointments altered due to no patient: ") + count.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String appoitmentNoteTooManyNewLines(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        StringBuilder tooManyNewLines = new StringBuilder();
        for (int i = 0;i < 30;i++)
        {
            tooManyNewLines.Append("\r\n");
        }
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM appointment WHERE Note LIKE '%" + POut.String(tooManyNewLines.ToString()) + "%'";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Appointment notes found with too many new lines: ") + numFound.ToString() + "\r\n";
            }
             
        }
        else
        {
            command = "SELECT * FROM appointment WHERE Note LIKE '%" + POut.String(tooManyNewLines.ToString()) + "%'";
            DataTable tableAppts = Db.getTable(command);
            long numfixed = 0;
            if (tableAppts.Rows.Count > 0 || verbose)
            {
                for (int i = 0;i < tableAppts.Rows.Count;i++)
                {
                    long aptNum = PIn.Long(tableAppts.Rows[i]["AptNum"].ToString());
                    Appointment oldApt = Appointments.getOneApt(aptNum);
                    Appointment apt = oldApt.clone();
                    apt.Note = Regex.Replace(oldApt.Note, POut.String(tooManyNewLines.ToString()) + "[\r\n]*", "\r\n");
                    Appointments.update(apt,oldApt);
                    numfixed++;
                }
                log += Lans.g("FormDatabaseMaintenance","Appoinment notes with too many new lines fixed: ") + numfixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String appointmentPlannedNoPlannedApt(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM appointment WHERE AptStatus=6 AND AptNum NOT IN (SELECT AptNum FROM plannedappt)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Appointments with status set to planned without Planned Appointment: ") + numFound.ToString() + "\r\n";
            }
             
        }
        else
        {
            command = "SELECT * FROM appointment WHERE AptStatus=6 AND AptNum NOT IN (SELECT AptNum FROM plannedappt)";
            DataTable appts = Db.getTable(command);
            if (appts.Rows.Count > 0 || verbose)
            {
                PlannedAppt plannedAppt;
                for (int i = 0;i < appts.Rows.Count;i++)
                {
                    plannedAppt = new PlannedAppt();
                    plannedAppt.PatNum = PIn.Long(appts.Rows[i]["PatNum"].ToString());
                    plannedAppt.AptNum = PIn.Long(appts.Rows[i]["AptNum"].ToString());
                    plannedAppt.ItemOrder = 1;
                    PlannedAppts.insert(plannedAppt);
                }
                log += Lans.g("FormDatabaseMaintenance","Planned Appointments created for Appointments with status set to planned and no Planned Appointment: ") + appts.Rows.Count + "\r\n";
            }
             
        } 
        return log;
    }

    public static String appointmentScheduledWithCompletedProcs(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT DISTINCT appointment.PatNum, " + DbHelper.concat("LName","\', \'","FName") + " AS PatName, appointment.AptDateTime " + "FROM appointment " + "INNER JOIN patient ON patient.PatNum=appointment.PatNum " + "INNER JOIN procedurelog ON procedurelog.AptNum=appointment.AptNum " + "WHERE AptStatus=" + POut.int(((Enum)ApptStatus.Scheduled).ordinal()) + " " + "AND procedurelog.ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "ORDER BY PatName";
        table = Db.getTable(command);
        //Both check and fix need to alert the user to fix manually.
        if (table.Rows.Count > 0 || verbose)
        {
            log += Lans.g("FormDatabaseMaintenance","Scheduled appointments with completed procedures attached") + ": " + table.Rows.Count + ", " + Lans.g("FormDatabaseMaintenance","including") + ":\r\n";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (i > 10)
                {
                    break;
                }
                 
                log += "   " + table.Rows[i]["PatNum"].ToString() + "-" + table.Rows[i]["PatName"].ToString() + "  Appt Date:" + PIn.DateT(table.Rows[i]["AptDateTime"].ToString()).ToShortDateString();
                log += "\r\n";
            }
            if (table.Rows.Count > 0)
            {
                log += Lans.g("FormDatabaseMaintenance","   They need to be fixed manually.") + "\r\n";
            }
             
        }
         
        return log;
    }

    public static String autoCodeItemsWithNoAutoCode(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT DISTINCT AutoCodeNum FROM autocodeitem WHERE NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT * FROM autocode WHERE autocodeitem.AutoCodeNum=autocode.AutoCodeNum)";
            table = Db.getTable(command);
            int numFound = table.Rows.Count;
            if (numFound != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Auto codes missing due to invalid auto code items") + ": " + numFound.ToString() + "\r\n";
            }
             
        }
        else
        {
            command = "SELECT DISTINCT AutoCodeNum FROM autocodeitem WHERE NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT * FROM autocode WHERE autocodeitem.AutoCodeNum=autocode.AutoCodeNum)";
            table = Db.getTable(command);
            int numFixed = table.Rows.Count;
            for (int i = 0;i < table.Rows.Count;i++)
            {
                AutoCode autoCode = new AutoCode();
                autoCode.AutoCodeNum = PIn.Long(table.Rows[i]["AutoCodeNum"].ToString());
                autoCode.Description = "UNKNOWN";
                Crud.AutoCodeCrud.Insert(autoCode, true);
            }
            if (numFixed > 0)
            {
                Signalods.setInvalid(InvalidType.AutoCodes);
            }
             
            if (numFixed != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Auto codes created due to invalid auto code items") + ": " + numFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String autoCodesDeleteWithNoItems(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM autocode WHERE NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT * FROM autocodeitem WHERE autocodeitem.AutoCodeNum=autocode.AutoCodeNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Autocodes found with no items: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM autocode WHERE NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT * FROM autocodeitem WHERE autocodeitem.AutoCodeNum=autocode.AutoCodeNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0)
            {
                Signalods.setInvalid(InvalidType.AutoCodes);
            }
             
            if (numberFixed != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Autocodes deleted due to no items: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String automationTriggersWithNoSheetDefs(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM automation WHERE automation.SheetDefNum!=0 AND NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT SheetDefNum FROM sheetdef WHERE automation.SheetDefNum=sheetdef.SheetDefNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Automation triggers found with no sheet defs: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM automation WHERE automation.SheetDefNum!=0 AND NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT SheetDefNum FROM sheetdef WHERE automation.SheetDefNum=sheetdef.SheetDefNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Automation triggers deleted due to no sheet defs: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String billingTypesInvalid(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            //Make sure preference of default billing type is set.
            command = "SELECT ValueString FROM preference WHERE PrefName = 'PracticeDefaultBillType'";
            long billingType = PIn.long(Db.getScalar(command));
            command = "SELECT COUNT(*) FROM definition WHERE Category=4 AND definition.DefNum=" + billingType;
            int prefExists = PIn.int(Db.getCount(command));
            if (prefExists != 1)
            {
                log += Lans.g("FormDatabaseMaintenance","No default billing type set.") + "\r\n";
            }
            else
            {
                if (verbose)
                {
                    log += Lans.g("FormDatabaseMaintenance","Default practice billing type verified.") + "\r\n";
                }
                 
            } 
            //Check for any patients with invalid billingtype.
            command = "SELECT COUNT(*) FROM patient WHERE NOT EXISTS(SELECT * FROM definition WHERE Category=4 AND patient.BillingType=definition.DefNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Patients with invalid billing type: ") + numFound.ToString() + "\r\n";
            }
             
        }
        else
        {
            //Fix for default billingtype not being set.
            command = "SELECT ValueString FROM preference WHERE PrefName = 'PracticeDefaultBillType'";
            long billingType = PIn.long(Db.getScalar(command));
            command = "SELECT COUNT(*) FROM definition WHERE Category=4 AND definition.DefNum=" + billingType;
            int prefExists = PIn.int(Db.getCount(command));
            if (prefExists != 1)
            {
                //invalid billing type
                command = "SELECT DefNum FROM definition WHERE Category = 4 AND IsHidden = 0 ORDER BY ItemOrder";
                table = Db.getTable(command);
                if (table.Rows.Count == 0)
                {
                    //if all billing types are hidden
                    command = "SELECT DefNum FROM definition WHERE Category = 4 ORDER BY ItemOrder";
                    table = Db.getTable(command);
                }
                 
                command = "UPDATE preference SET ValueString='" + table.Rows[0][0].ToString() + "' WHERE PrefName='PracticeDefaultBillType'";
                Db.nonQ(command);
                log += Lans.g("FormDatabaseMaintenance","Default billing type preference was set due to being invalid.") + "\r\n";
                Prefs.refreshCache();
            }
             
            //for the next line.
            //Fix for patients with invalid billingtype.
            command = "UPDATE patient SET patient.BillingType=" + POut.long(PrefC.getLong(PrefName.PracticeDefaultBillType));
            command += " WHERE NOT EXISTS(SELECT * FROM definition WHERE Category=4 AND patient.BillingType=definition.DefNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Patients billing type set to default due to being invalid: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String canadaCarriersCdaMissingInfo(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        if (!CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            return "";
        }
         
        //Canadian. en-CA or fr-CA
        command = "SELECT CanadianNetworkNum FROM canadiannetwork WHERE Abbrev='TELUS B' LIMIT 1";
        long canadianNetworkNumTelusB = PIn.long(Db.getScalar(command));
        command = "SELECT CanadianNetworkNum FROM canadiannetwork WHERE Abbrev='CSI' LIMIT 1";
        long canadianNetworkNumCSI = PIn.long(Db.getScalar(command));
        command = "SELECT CanadianNetworkNum FROM canadiannetwork WHERE Abbrev='CDCS' LIMIT 1";
        long canadianNetworkNumCDCS = PIn.long(Db.getScalar(command));
        command = "SELECT CanadianNetworkNum FROM canadiannetwork WHERE Abbrev='TELUS A' LIMIT 1";
        long canadianNetworkNumTelusA = PIn.long(Db.getScalar(command));
        command = "SELECT CanadianNetworkNum FROM canadiannetwork WHERE Abbrev='MBC' LIMIT 1";
        long canadianNetworkNumMBC = PIn.long(Db.getScalar(command));
        command = "SELECT CanadianNetworkNum FROM canadiannetwork WHERE Abbrev='PBC' LIMIT 1";
        long canadianNetworkNumPBC = PIn.long(Db.getScalar(command));
        command = "SELECT CanadianNetworkNum FROM canadiannetwork WHERE Abbrev='ABC' LIMIT 1";
        long canadianNetworkNumABC = PIn.long(Db.getScalar(command));
        CanSupTransTypes claimTypes = CanSupTransTypes.ClaimAckEmbedded_11e | CanSupTransTypes.ClaimEobEmbedded_21e;
        //Claim 01, claim ack 11, and claim eob 21 are implied.
        CanSupTransTypes reversalTypes = CanSupTransTypes.ClaimReversal_02 | CanSupTransTypes.ClaimReversalResponse_12;
        CanSupTransTypes predeterminationTypes = CanSupTransTypes.PredeterminationAck_13 | CanSupTransTypes.PredeterminationAckEmbedded_13e | CanSupTransTypes.PredeterminationMultiPage_03 | CanSupTransTypes.PredeterminationSinglePage_03;
        CanSupTransTypes rotTypes = CanSupTransTypes.RequestForOutstandingTrans_04;
        CanSupTransTypes cobTypes = CanSupTransTypes.CobClaimTransaction_07;
        CanSupTransTypes eligibilityTypes = CanSupTransTypes.EligibilityTransaction_08 | CanSupTransTypes.EligibilityResponse_18;
        CanSupTransTypes rprTypes = CanSupTransTypes.RequestForPaymentReconciliation_06;
        //Column order: ElectID,CanadianEncryptionMethod,CDAnetVersion,CanadianSupportedTypes,CanadianNetworkNum
        //accerta
        //adsc
        //aga
        //appq
        //alberta blue cross. Usually sent through ClaimStream instead of ITRANS.
        //assumption life
        //autoben
        //benecaid health benefit solutions
        //benefits trust
        //beneplan
        //boilermakers' national benefit plan
        //canadian benefit providers
        //capitale
        //cdcs
        //claimsecure
        //ccq
        //co-operators
        //coughlin & associates
        //cowan wright beauchamps
        //desjardins financial security
        //empire life insurance company
        //equitable life
        //esorse corporation
        //fas administrators
        //great west life assurance company
        //green sheild canada
        //group medical services
        //group medical services saskatchewan
        //groupsource
        //industrial alliance
        //industrial alliance pacific insuarnce and financial
        //internationale campagnie d'assurance vie
        //johnson inc.
        //johnston group
        //lee-power & associates
        //local 1030 health benefity plan
        //manion wilkins
        //manitoba blue cross
        //manitoba cleft palate program
        //manitoba health
        //manufacturers life insurance company
        //manulife financial
        //maritime life assurance company
        //maritime pro
        //mdm
        //medavie blue cross
        //nexgenrx
        //nihb
        //nova scotia community services
        //nova scotia medical services insurance
        //nunatsiavut government department of health
        //pacific blue cross
        //quickcard
        //pbas
        //rwam insurance
        //saskatchewan blue cross
        //ses benefits
        //sheet metal workers local 30 benefit plan
        //ssq societe d'assurance-vie inc.
        //standard life assurance company
        //sun life of canada
        //survivance
        //syndicat des fonctionnaires municipaux mtl
        //the building union of canada health beneift plan
        //u.a. local 46 dental plan
        //u.a. local 787 health trust fund dental plan
        //wawanesa
        Object[] carrierInfo = new Object[]{ "311140", 1, "04", claimTypes | reversalTypes, canadianNetworkNumTelusB, "000105", 1, "04", claimTypes | reversalTypes | predeterminationTypes | rotTypes | cobTypes | eligibilityTypes, canadianNetworkNumCSI, "610226", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusB, "628112", 1, "02", claimTypes | reversalTypes | predeterminationTypes | cobTypes, canadianNetworkNumTelusB, "000090", 1, "04", claimTypes | reversalTypes | predeterminationTypes | rotTypes | cobTypes, canadianNetworkNumABC, "610191", 1, "04", claimTypes, canadianNetworkNumTelusB, "628151", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusB, "610708", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumCSI, "610146", 1, "02", claimTypes | predeterminationTypes, canadianNetworkNumTelusB, "400008", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusB, "000116", 1, "04", claimTypes | predeterminationTypes, canadianNetworkNumCSI, "610202", 1, "04", claimTypes | reversalTypes | predeterminationTypes | cobTypes, canadianNetworkNumTelusB, "600502", 1, "04", claimTypes, canadianNetworkNumTelusB, "610129", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumCDCS, "610099", 1, "04", claimTypes | eligibilityTypes, canadianNetworkNumTelusB, "000036", 1, "02", claimTypes | reversalTypes, canadianNetworkNumTelusB, "606258", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumCSI, "610105", 1, "04", claimTypes | reversalTypes | predeterminationTypes | rotTypes, canadianNetworkNumTelusB, "610153", 1, "04", claimTypes | reversalTypes, canadianNetworkNumCSI, "000051", 1, "04", claimTypes | predeterminationTypes, canadianNetworkNumCSI, "000033", 1, "04", claimTypes | predeterminationTypes, canadianNetworkNumTelusB, "000029", 1, "02", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusA, "610650", 1, "04", claimTypes | reversalTypes | predeterminationTypes | rprTypes | cobTypes, canadianNetworkNumTelusB, "610614", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusB, "000011", 1, "02", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusA, "000102", 1, "04", claimTypes | reversalTypes | predeterminationTypes | cobTypes, canadianNetworkNumTelusB, "610217", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumCSI, "610218", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumCSI, "605064", 1, "04", claimTypes | reversalTypes | eligibilityTypes, canadianNetworkNumCSI, "000060", 1, "02", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusA, "000024", 1, "02", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusA, "610643", 1, "04", claimTypes | reversalTypes, canadianNetworkNumCSI, "627265", 1, "04", claimTypes, canadianNetworkNumTelusB, "627223", 1, "02", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusA, "627585", 1, "02", claimTypes, canadianNetworkNumTelusB, "000118", 1, "04", claimTypes | predeterminationTypes, canadianNetworkNumCSI, "610158", 1, "04", claimTypes | reversalTypes, canadianNetworkNumTelusB, "000094", 1, "04", claimTypes | reversalTypes | predeterminationTypes | rotTypes, canadianNetworkNumMBC, "000114", 1, "04", claimTypes | predeterminationTypes | rotTypes, canadianNetworkNumCSI, "000113", 1, "04", claimTypes | rotTypes, canadianNetworkNumCSI, "000034", 1, "02", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusB, "610059", 1, "02", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusB, "311113", 1, "02", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusB, "610070", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusB, "601052", 1, "02", claimTypes | reversalTypes | predeterminationTypes | eligibilityTypes, canadianNetworkNumTelusB, "610047", 1, "02", claimTypes | predeterminationTypes, canadianNetworkNumTelusB, "610634", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumCSI, "610124", 1, "04", claimTypes | reversalTypes, canadianNetworkNumCSI, "000109", 1, "04", claimTypes | reversalTypes | predeterminationTypes | rotTypes | cobTypes | eligibilityTypes, canadianNetworkNumCSI, "000108", 1, "04", claimTypes | reversalTypes | predeterminationTypes | rotTypes | cobTypes | eligibilityTypes, canadianNetworkNumCSI, "610172", 1, "04", claimTypes | reversalTypes, canadianNetworkNumCSI, "000064", 1, "04", claimTypes | predeterminationTypes | rotTypes, canadianNetworkNumPBC, "000103", 1, "04", claimTypes | reversalTypes | predeterminationTypes | rotTypes | cobTypes | eligibilityTypes, canadianNetworkNumCSI, "610256", 1, "04", claimTypes | predeterminationTypes | rotTypes, canadianNetworkNumCSI, "610616", 1, "04", claimTypes | reversalTypes, canadianNetworkNumTelusB, "000096", 1, "04", claimTypes, canadianNetworkNumTelusB, "610196", 1, "04", claimTypes | reversalTypes, canadianNetworkNumTelusB, "000119", 1, "04", claimTypes | predeterminationTypes, canadianNetworkNumCSI, "000079", 1, "04", claimTypes, canadianNetworkNumCSI, "000020", 1, "04", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusB, "000016", 1, "02", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusA, "000080", 1, "04", claimTypes, canadianNetworkNumCSI, "610677", 1, "04", claimTypes | reversalTypes, canadianNetworkNumCSI, "000120", 1, "04", claimTypes | predeterminationTypes, canadianNetworkNumCSI, "000115", 1, "04", claimTypes | predeterminationTypes, canadianNetworkNumCSI, "000110", 1, "04", claimTypes | predeterminationTypes, canadianNetworkNumCSI, "311109", 1, "02", claimTypes | reversalTypes | predeterminationTypes, canadianNetworkNumTelusB };
        String log = "";
        if (isCheck)
        {
            int numFound = 0;
            for (int i = 0;i < carrierInfo.Length;i += 5)
            {
                command = "SELECT COUNT(*) " + "FROM carrier " + "WHERE IsCDA<>0 AND ElectID='" + POut.string((String)carrierInfo[i]) + "' AND " + "(CanadianEncryptionMethod<>" + POut.int((int)carrierInfo[i + 1]) + " OR " + "CDAnetVersion<>'" + POut.string((String)carrierInfo[i + 2]) + "' OR " + "CanadianSupportedTypes<>" + POut.int((int)carrierInfo[i + 3]) + " OR " + "CanadianNetworkNum<>" + POut.long((long)carrierInfo[i + 4]) + ")";
                numFound += PIn.int(Db.getCount(command));
            }
            if (numFound != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","CDANet carriers with incorrect network, encryption method or version, based on carrier identification number: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            long numberFixed = 0;
            for (int i = 0;i < carrierInfo.Length;i += 5)
            {
                command = "UPDATE carrier SET " + "CanadianEncryptionMethod=" + POut.int((int)carrierInfo[i + 1]) + "," + "CDAnetVersion='" + POut.string((String)carrierInfo[i + 2]) + "'," + "CanadianSupportedTypes=" + POut.int((int)carrierInfo[i + 3]) + "," + "CanadianNetworkNum=" + POut.long((long)carrierInfo[i + 4]) + " " + "WHERE IsCDA<>0 AND ElectID='" + POut.string((String)carrierInfo[i]) + "' AND " + "(CanadianEncryptionMethod<>" + POut.int((int)carrierInfo[i + 1]) + " OR " + "CDAnetVersion<>'" + POut.string((String)carrierInfo[i + 2]) + "' OR " + "CanadianSupportedTypes<>" + POut.int((int)carrierInfo[i + 3]) + " OR " + "CanadianNetworkNum<>" + POut.long((long)carrierInfo[i + 4]) + ")";
                numberFixed += Db.nonQ(command);
            }
            if (numberFixed != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","CDANet carriers fixed based on carrier identification number: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimDeleteWithNoClaimProcs(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claim WHERE NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT * FROM claimproc WHERE claim.ClaimNum=claimproc.ClaimNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claims found with no procedures: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //Orphaned claims do not show in the account module (tested) so we need to delete them because no other way.
            command = "DELETE FROM claim WHERE NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT * FROM claimproc WHERE claim.ClaimNum=claimproc.ClaimNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claims deleted due to no procedures: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimWithInvalidInsSubNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            //check 2 situations:
            //1. claim.PlanNum=0 and inssub.PlanNum=0
            command = "SELECT COUNT(*) FROM claim,inssub WHERE claim.InsSubNum=inssub.InsSubNum AND claim.PlanNum=0 AND inssub.PlanNum=0 ";
            int planCount = PIn.int(Db.getCount(command));
            //2. claim.PlanNum=0 and inssub does not exist
            command = "SELECT COUNT(*) FROM claim WHERE PlanNum=0 AND InsSubNum NOT IN (SELECT InsSubNum FROM inssub WHERE inssub.InsSubNum=claim.InsSubNum)";
            int existCount = PIn.int(Db.getCount(command));
            int numFound = planCount + existCount;
            if (numFound != 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claims with invalid InsSubNum: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //situation where PlanNum and InsSubNum are both invalid and not zero is handled in InsSubNumMismatchPlanNum
            command = "SELECT claim.ClaimNum,claim.PatNum FROM claim,inssub WHERE claim.InsSubNum=inssub.InsSubNum AND claim.PlanNum=0 AND inssub.PlanNum=0 " + "UNION " + "SELECT ClaimNum,PatNum FROM claim WHERE PlanNum=0 AND InsSubNum NOT IN (SELECT InsSubNum FROM inssub WHERE inssub.InsSubNum=claim.InsSubNum)";
            table = Db.getTable(command);
            long numberFixed = table.Rows.Count;
            InsPlan plan = null;
            InsSub sub = null;
            if (numberFixed > 0)
            {
                log += Lans.g("FormDatabaseMaintenance","List of patients who will need insurance information reentered:") + "\r\n";
            }
             
            for (int i = 0;i < numberFixed;i++)
            {
                plan = new InsPlan();
                //Create a dummy plan and carrier to attach claims and claim procs to.
                plan.IsHidden = true;
                plan.CarrierNum = Carriers.getByNameAndPhone("UNKNOWN CARRIER","").CarrierNum;
                InsPlans.insert(plan);
                long claimNum = PIn.Long(table.Rows[i]["ClaimNum"].ToString());
                long patNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
                sub = new InsSub();
                //Create inssubs and attach claim and procs to both plan and inssub.
                sub.PlanNum = plan.PlanNum;
                sub.Subscriber = PIn.Long(table.Rows[i]["PatNum"].ToString());
                sub.SubscriberID = "unknown";
                InsSubs.insert(sub);
                command = "UPDATE claim SET PlanNum=" + plan.PlanNum + ",InsSubNum=" + sub.InsSubNum + " WHERE ClaimNum=" + claimNum;
                Db.nonQ(command);
                command = "UPDATE claimproc SET PlanNum=" + plan.PlanNum + ",InsSubNum=" + sub.InsSubNum + " WHERE ClaimNum=" + claimNum;
                Db.nonQ(command);
                Patient pat = Patients.getLim(patNum);
                log += "PatNum: " + pat.PatNum + " - " + Patients.getNameFL(pat.LName,pat.FName,pat.Preferred,pat.MiddleI) + "\r\n";
            }
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claims with invalid InsSubNum fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    /**
    * Also fixes situations where PatNum=0
    */
    public static String claimWithInvalidPatNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT claim.ClaimNum, procedurelog.PatNum patNumCorrect\r\n" + 
        "\t\t\t\tFROM claim, claimproc, procedurelog\r\n" + 
        "\t\t\t\tWHERE claim.PatNum NOT IN (SELECT PatNum FROM patient)\r\n" + 
        "\t\t\t\tAND claim.ClaimNum=claimproc.ClaimNum\r\n" + 
        "\t\t\t\tAND claimproc.ProcNum=procedurelog.ProcNum\r\n" + 
        "\t\t\t\tAND procedurelog.PatNum!=0\r\n" + 
        "\t\t\t\tGROUP BY claim.ClaimNum, procedurelog.PatNum";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claims found with invalid patients attached: ") + table.Rows.Count.ToString() + "\r\n";
            }
             
        }
        else
        {
            for (int i = 0;i < table.Rows.Count;i++)
            {
                command = "UPDATE claim SET PatNum='" + POut.Long(PIn.Long(table.Rows[i]["patNumCorrect"].ToString())) + "' " + "WHERE ClaimNum=" + POut.Long(PIn.Long(table.Rows[i]["ClaimNum"].ToString()));
                Db.nonQ(command);
            }
            int numberFixed = table.Rows.Count;
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claim with invalid PatNums fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimWriteoffSum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "";
        }
         
        String log = "";
        //Sums for each claim---------------------------------------------------------------------
        command = "SELECT claim.ClaimNum,SUM(claimproc.WriteOff) sumwo,claim.WriteOff\r\n" + 
        "\t\t\t\tFROM claim,claimproc\r\n" + 
        "\t\t\t\tWHERE claim.ClaimNum=claimproc.ClaimNum\r\n" + 
        "\t\t\t\tGROUP BY claim.ClaimNum,claim.WriteOff\r\n" + 
        "\t\t\t\tHAVING sumwo-claim.WriteOff > .01\r\n" + 
        "\t\t\t\tOR sumwo-claim.WriteOff < -.01";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claim writeoff sums found incorrect: ") + table.Rows.Count.ToString() + "\r\n";
            }
             
        }
        else
        {
            for (int i = 0;i < table.Rows.Count;i++)
            {
                command = "UPDATE claim SET WriteOff='" + POut.Double(PIn.Double(table.Rows[i]["sumwo"].ToString())) + "' " + "WHERE ClaimNum=" + table.Rows[i]["ClaimNum"].ToString();
                Db.nonQ(command);
            }
            int numberFixed = table.Rows.Count;
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claim writeoff sums fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    /**
    * also fixes resulting deposit misbalances.
    */
    public static String claimPaymentCheckAmt(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "";
        }
         
        String log = "";
        //because of the way this is grouped, it will just get one of many patients for each
        command = "SELECT claimproc.ClaimPaymentNum,ROUND(SUM(InsPayAmt),2) _sumpay,ROUND(CheckAmt,2) _checkamt,claimproc.PatNum\r\n" + 
        "\t\t\t\t\tFROM claimpayment,claimproc\r\n" + 
        "\t\t\t\t\tWHERE claimpayment.ClaimPaymentNum=claimproc.ClaimPaymentNum\r\n" + 
        "\t\t\t\t\tGROUP BY claimproc.ClaimPaymentNum,CheckAmt\r\n" + 
        "\t\t\t\t\tHAVING _sumpay!=_checkamt";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claim payment sums found incorrect: ") + table.Rows.Count.ToString() + "\r\n";
            }
             
        }
        else
        {
            //Changing the claim payment sums automatically is dangerous so give the user enough information to investigate themselves.
            if (table.Rows.Count > 1)
            {
                log = Lans.g("FormDatabaseMaintenance","The following claim payment sums are incorrect") + ":\r\n";
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    Patient pat = Patients.GetPat(PIn.Long(table.Rows[i]["PatNum"].ToString()));
                    command = "SELECT CheckDate,CheckAmt,IsPartial FROM claimpayment WHERE ClaimPaymentNum=" + table.Rows[i]["ClaimPaymentNum"].ToString();
                    DataTable claimPayTable = Db.getTable(command);
                    if (pat == null)
                    {
                        //insert pat
                        Patient dummyPatient = new Patient();
                        dummyPatient.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
                        dummyPatient.Guarantor = dummyPatient.PatNum;
                        dummyPatient.FName = "MISSING";
                        dummyPatient.LName = "PATIENT";
                        dummyPatient.AddrNote = "This patient was inserted due to claimprocs with invalid PatNum on " + DateTime.Now.ToShortDateString() + " while doing database maintenance.";
                        dummyPatient.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
                        dummyPatient.PatStatus = PatientStatus.Archived;
                        dummyPatient.PriProv = PrefC.getLong(PrefName.PracticeDefaultProv);
                        long dummyPatNum = Patients.insert(dummyPatient,true);
                        pat = Patients.getPat(dummyPatient.PatNum);
                    }
                     
                    log += "   Patient: #" + table.Rows[i]["PatNum"].ToString() + ":" + pat.getNameFirstOrPrefL() + " Date: " + PIn.Date(claimPayTable.Rows[0]["CheckDate"].ToString()).ToShortDateString() + " Amount: " + PIn.Double(claimPayTable.Rows[0]["CheckAmt"].ToString()).ToString("F");
                    if (!PIn.Bool(claimPayTable.Rows[0]["IsPartial"].ToString()))
                    {
                        command = "UPDATE claimpayment SET IsPartial=1 WHERE ClaimPaymentNum=" + PIn.Long(table.Rows[i]["ClaimPaymentNum"].ToString()).ToString();
                        Db.nonQ(command);
                        log += " (row has been unlocked and marked as partial)";
                    }
                     
                    log += "\r\n";
                }
                log += Lans.g("FormDatabaseMaintenance","   They need to be fixed manually.") + "\r\n";
            }
             
        } 
        /*
        				for(int i=0;i<table.Rows.Count;i++) {
        					command="UPDATE claimpayment SET CheckAmt='"+POut.Double(PIn.Double(table.Rows[i]["_sumpay"].ToString()))+"' "
        				    +"WHERE ClaimPaymentNum="+table.Rows[i]["ClaimPaymentNum"].ToString();
        					Db.NonQ(command);
        				}
        				int numberFixed=table.Rows.Count;
        				if(numberFixed>0||verbose) {
        					log+=Lans.g("FormDatabaseMaintenance","Claim payment sums fixed: ")+numberFixed.ToString()+"\r\n";
        				}*/
        //now deposits which were affected by the changes above--------------------------------------------------
        command = "SELECT DepositNum,deposit.Amount,DateDeposit,\r\n" + 
        "\t\t\t\tIFNULL((SELECT SUM(CheckAmt) FROM claimpayment WHERE claimpayment.DepositNum=deposit.DepositNum GROUP BY deposit.DepositNum),0)\r\n" + 
        "\t\t\t\t+IFNULL((SELECT SUM(PayAmt) FROM payment WHERE payment.DepositNum=deposit.DepositNum GROUP BY deposit.DepositNum),0) _sum\r\n" + 
        "\t\t\t\tFROM deposit\r\n" + 
        "\t\t\t\tHAVING ROUND(_sum,2) != ROUND(deposit.Amount,2)";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Deposit sums found incorrect: ") + table.Rows.Count.ToString() + "\r\n";
            }
             
        }
        else
        {
        } 
        return log;
    }

    /*
    				for(int i=0;i<table.Rows.Count;i++) {
    					if(i==0) {
    						log+=Lans.g("FormDatabaseMaintenance","PRINT THIS FOR REFERENCE. Deposit sums recalculated:")+"\r\n";
    					}
    					DateTime date=PIn.Date(table.Rows[i]["DateDeposit"].ToString());
    					Double oldval=PIn.Double(table.Rows[i]["Amount"].ToString());
    					Double newval=PIn.Double(table.Rows[i]["_sum"].ToString());
    					log+=date.ToShortDateString()+" "+Lans.g("FormDatabaseMaintenance","OldSum:")+oldval.ToString("c")
    				    +", "+Lans.g("FormDatabaseMaintenance","NewSum:")+newval.ToString("c")+"\r\n";
    					command="UPDATE deposit SET Amount='"+POut.Double(PIn.Double(table.Rows[i]["_sum"].ToString()))+"' "
    				    +"WHERE DepositNum="+table.Rows[i]["DepositNum"].ToString();
    					Db.NonQ(command);
    				}
    				int numberFixed=table.Rows.Count;
    				if(numberFixed>0||verbose) {
    					log+=Lans.g("FormDatabaseMaintenance","Deposit sums fixed: ")+numberFixed.ToString()+"\r\n";
    				}*/
    public static String claimPaymentDetachMissingDeposit(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimpayment " + "WHERE DepositNum != 0 " + "AND NOT EXISTS(SELECT * FROM deposit WHERE deposit.DepositNum=claimpayment.DepositNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claim payments attached to deposits that no longer exist: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE claimpayment SET DepositNum=0 " + "WHERE DepositNum != 0 " + "AND NOT EXISTS(SELECT * FROM deposit WHERE deposit.DepositNum=claimpayment.DepositNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claim payments detached from deposits that no longer exist: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcDateNotMatchCapComplete(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE Status=7 AND DateCP != ProcDate";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Capitation procs with mismatched dates found: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //js ok
            command = "UPDATE claimproc SET DateCP=ProcDate WHERE Status=7 AND DateCP != ProcDate";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Capitation procs with mismatched dates fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcDateNotMatchPayment(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT claimproc.ClaimProcNum,claimpayment.CheckDate FROM claimproc,claimpayment " + "WHERE claimproc.ClaimPaymentNum=claimpayment.ClaimPaymentNum " + "AND claimproc.DateCP!=claimpayment.CheckDate";
            table = Db.getTable(command);
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claim payments with mismatched dates found: ") + table.Rows.Count.ToString() + "\r\n";
            }
             
        }
        else
        {
            //This is a very strict relationship that has been enforced rigorously for many years.
            //If there is an error, it is a fairly new error.  All errors must be repaired.
            //It won't change amounts of history, just dates.  The changes will typically be only a few days or weeks.
            //Various reports assume this enforcement and the reports will malfunction if this is not fixed.
            //Let's list out each change.  Patient name, procedure desc, date of service, old dateCP, new dateCP (check date).
            command = "SELECT patient.LName,patient.FName,patient.MiddleI,claimproc.CodeSent,claim.DateService,claimproc.DateCP,claimpayment.CheckDate,claimproc.ClaimProcNum " + "FROM claimproc,patient,claim,claimpayment " + "WHERE claimproc.PatNum=patient.PatNum " + "AND claimproc.ClaimNum=claim.ClaimNum " + "AND claimproc.ClaimPaymentNum=claimpayment.ClaimPaymentNum " + "AND claimproc.DateCP!=claimpayment.CheckDate";
            table = Db.getTable(command);
            String patientName = new String();
            String codeSent = new String();
            DateTime dateService = new DateTime();
            DateTime oldDateCP = new DateTime();
            DateTime newDateCP = new DateTime();
            if (table.Rows.Count > 0 || verbose)
            {
                log += "Claim payments with mismatched dates (Patient Name, Code Sent, Date of Service, Old Date, New Date):\r\n";
            }
             
            for (int i = 0;i < table.Rows.Count;i++)
            {
                patientName = table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString() + " " + table.Rows[i]["MiddleI"].ToString();
                patientName = patientName.Trim();
                //Looks better when middle initial is not present.//Doesn't work though
                codeSent = table.Rows[i]["CodeSent"].ToString();
                dateService = PIn.Date(table.Rows[i]["DateService"].ToString());
                oldDateCP = PIn.Date(table.Rows[i]["DateCP"].ToString());
                newDateCP = PIn.Date(table.Rows[i]["CheckDate"].ToString());
                command = "UPDATE claimproc SET DateCP=" + POut.date(newDateCP) + " WHERE ClaimProcNum=" + table.Rows[i]["ClaimProcNum"].ToString();
                Db.nonQ(command);
                log += patientName + ", " + codeSent + ", " + dateService.ToShortDateString() + ", " + oldDateCP.ToShortDateString() + ", " + newDateCP.ToShortDateString() + "\r\n";
            }
            int numberFixed = table.Rows.Count;
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claim payments with mismatched dates fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcWithInvalidClaimNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE claimproc.ClaimNum!=0 " + "AND NOT EXISTS(SELECT * FROM claim WHERE claim.ClaimNum=claimproc.ClaimNum) " + "AND (claimproc.InsPayAmt!=0 OR claimproc.WriteOff!=0)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claimprocs found with invalid ClaimNum: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //fix
            //We can't touch those claimprocs because it would mess up the accounting.
            //We will create dummy claims for all claimprocs with invalid ClaimNums if those claimprocs have amounts entered in the InsPayAmt or Writeoff columns, otherwise you could not delete the procedure or create a new claim
            command = "SELECT * FROM claimproc WHERE claimproc.ClaimNum!=0 " + "AND NOT EXISTS(SELECT * FROM claim WHERE claim.ClaimNum=claimproc.ClaimNum) " + "AND (claimproc.InsPayAmt!=0 OR claimproc.WriteOff!=0) " + "GROUP BY claimproc.ClaimNum";
            table = Db.getTable(command);
            List<ClaimProc> cpList = Crud.ClaimProcCrud.TableToList(table);
            Claim claim;
            for (int i = 0;i < cpList.Count;i++)
            {
                claim = new Claim();
                claim.ClaimNum = cpList[i].ClaimNum;
                claim.PatNum = cpList[i].PatNum;
                claim.ClinicNum = cpList[i].ClinicNum;
                if (cpList[i].Status == OpenDentBusiness.ClaimProcStatus.Received)
                {
                    claim.ClaimStatus = "R";
                }
                else
                {
                    //Status received because we know it's been paid on and the claimproc status is received
                    claim.ClaimStatus = "W";
                } 
                claim.PlanNum = cpList[i].PlanNum;
                claim.InsSubNum = cpList[i].InsSubNum;
                claim.ProvTreat = cpList[i].ProvNum;
                Crud.ClaimCrud.Insert(claim, true);
                //Allows us to use a primary key that was "used".
                Patient pat = Patients.getLim(claim.PatNum);
                log += Lans.g("FormDatabaseMaintenance","Claim created due to claimprocs with invalid ClaimNums for patient: ") + pat.PatNum + " - " + Patients.getNameFL(pat.LName,pat.FName,pat.Preferred,pat.MiddleI) + "\r\n";
            }
        } 
        return log;
    }

    public static String claimProcDeleteDuplicateEstimateForSameInsPlan(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            //Get all the claimproc estimates that already have a claimproc marked as received from the same InsPlan.
            //The same insurance plan
            //for the same subscriber
            //for the same procedure.
            command = "SELECT cp.ClaimProcNum FROM claimproc cp USE KEY(PRIMARY)" + " INNER JOIN claimproc cp2 ON cp2.PatNum=cp.PatNum" + " AND cp2.PlanNum=cp.PlanNum" + " AND cp2.InsSubNum=cp.InsSubNum" + " AND cp2.ProcNum=cp.ProcNum" + " AND cp2.Status=" + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.Received).ordinal()) + " WHERE cp.Status=" + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.Estimate).ordinal()) + " AND cp.ClaimNum=0";
        }
        else
        {
            //Make sure the estimate is not already attached to a claim somehow.
            //oracle
            //Get all the claimproc estimates that already have a claimproc marked as received from the same InsPlan.
            //The same insurance plan
            //for the same subscriber
            //for the same procedure.
            command = "SELECT cp.ClaimProcNum FROM claimproc cp" + " INNER JOIN claimproc cp2 ON cp2.PatNum=cp.PatNum" + " AND cp2.PlanNum=cp.PlanNum" + " AND cp2.InsSubNum=cp.InsSubNum" + " AND cp2.ProcNum=cp.ProcNum" + " AND cp2.Status=" + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.Received).ordinal()) + " WHERE cp.Status=" + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.Estimate).ordinal()) + " AND cp.ClaimNum=0";
        } 
        //Make sure the estimate is not already attached to a claim somehow.
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Duplicate ClaimProc estimates for the same InsPlan found: ") + table.Rows.Count + "\r\n";
            }
             
        }
        else
        {
            if (table.Rows.Count > 0)
            {
                command = "DELETE FROM claimproc WHERE ClaimProcNum IN (";
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    if (i > 0)
                    {
                        command += ",";
                    }
                     
                    command += table.Rows[i]["ClaimProcNum"].ToString();
                }
                command += ")";
                Db.nonQ(command);
            }
             
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Duplicate ClaimProc estimates for the same InsPlan deleted: ") + table.Rows.Count + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcDeleteWithInvalidClaimNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE claimproc.ClaimNum!=0 " + "AND NOT EXISTS(SELECT * FROM claim WHERE claim.ClaimNum=claimproc.ClaimNum) " + "AND claimproc.InsPayAmt=0 AND claimproc.WriteOff=0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claimprocs found with invalid ClaimNum: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM claimproc WHERE claimproc.ClaimNum!=0 " + "AND NOT EXISTS(SELECT * FROM claim WHERE claim.ClaimNum=claimproc.ClaimNum) " + "AND claimproc.InsPayAmt=0 AND claimproc.WriteOff=0";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claimprocs deleted due to invalid ClaimNum: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcDeleteMismatchPatNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            //claimproc.PatNum != procedurelog.PatNum
            command = "SELECT COUNT(*) FROM claimproc " + "WHERE ProcNum > 0 " + "AND claimproc.PatNum!=(SELECT procedurelog.PatNum FROM procedurelog WHERE claimproc.ProcNum=procedurelog.ProcNum) " + "AND claimproc.InsPayAmt=0 AND claimproc.WriteOff=0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claimprocs found with PatNum that doesn't match the procedure PatNum: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM claimproc " + "WHERE ProcNum > 0 " + "AND claimproc.PatNum!=(SELECT procedurelog.PatNum FROM procedurelog WHERE claimproc.ProcNum=procedurelog.ProcNum) " + "AND claimproc.InsPayAmt=0 AND claimproc.WriteOff=0";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claimprocs deleted due to PatNum not matching the procedure PatNum: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcDeleteEstimateWithInvalidProcNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE ProcNum>0 " + "AND Status=" + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.Estimate).ordinal()) + " " + "AND NOT EXISTS(SELECT * FROM procedurelog " + "WHERE claimproc.ProcNum=procedurelog.ProcNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Estimates found for procedures that no longer exist: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //These seem to pop up quite regularly due to the program forgetting to delete them
            command = "DELETE FROM claimproc WHERE ProcNum>0 " + "AND Status=" + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.Estimate).ordinal()) + " " + "AND NOT EXISTS(SELECT * FROM procedurelog " + "WHERE claimproc.ProcNum=procedurelog.ProcNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Estimates deleted for procedures that no longer exist: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcDeleteCapEstimateWithProcComplete(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE ProcNum>0 " + "AND Status=" + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.CapEstimate).ordinal()) + " " + "AND EXISTS(" + "SELECT * FROM procedurelog " + "WHERE claimproc.ProcNum=procedurelog.ProcNum " + "AND procedurelog.ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + ")";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Capitation estimates found for completed procedures: ") + numFound.ToString() + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM claimproc WHERE ProcNum>0 " + "AND Status=" + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.CapEstimate).ordinal()) + " " + "AND EXISTS(" + "SELECT * FROM procedurelog " + "WHERE claimproc.ProcNum=procedurelog.ProcNum " + "AND procedurelog.ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + ")";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Capitation estimates deleted for completed procedures: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcEstNoBillIns(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE NoBillIns=1 AND InsPayEst !=0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claimprocs found with non-zero estimates marked NoBillIns: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //This is just estimate info, regardless of the claimproc status, so totally safe.
            command = "UPDATE claimproc SET InsPayEst=0 WHERE NoBillIns=1 AND InsPayEst !=0";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claimproc estimates set to zero because marked NoBillIns: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcEstWithInsPaidAmt(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE InsPayAmt > 0 AND ClaimNum=0 AND Status=6";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ClaimProc estimates with InsPaidAmt > 0 found: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //The InsPayAmt is already being ignored due to the status of the claimproc.  So changing its value is harmless.
            command = "UPDATE claimproc SET InsPayAmt=0 WHERE InsPayAmt > 0 AND ClaimNum=0 AND Status=6";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ClaimProc estimates with InsPaidAmt > 0 fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcPatNumMissing(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE PatNum=0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ClaimProcs with missing patnums found: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM claimproc WHERE PatNum=0 AND InsPayAmt=0 AND WriteOff=0";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ClaimProcs with missing patnums fixed: ") + numberFixed + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcProvNumMissing(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE ProvNum=0 AND Status!=3";
            //Status 3 is adjustment which does not require a provider.
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ClaimProcs with missing provnums found: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //If estimate, set to default prov (doesn't affect finances)
            command = "UPDATE claimproc SET ProvNum=" + PrefC.getString(PrefName.PracticeDefaultProv) + " WHERE ProvNum=0 AND Status=" + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.Estimate).ordinal());
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ClaimProcs with missing provnums fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    //create a dummy provider (using helper function in Providers.cs)
    //change provnum to the dummy prov (something like Providers.GetDummy())
    //Provider dummy=new Provider();
    //dummy.Abbr="Dummy";
    //dummy.FName="Dummy";
    //dummy.LName="Provider";
    //Will get to this soon.
    //01-17-2011 No fix yet. This has not caused issues except for notifying users.
    public static String claimProcPreauthNotMatchClaim(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT claimproc.ClaimProcNum \r\n" + 
        "\t\t\t\tFROM claimproc,claim \r\n" + 
        "\t\t\t\tWHERE claimproc.ClaimNum=claim.ClaimNum\r\n" + 
        "\t\t\t\tAND claim.ClaimType=\'PreAuth\'\r\n" + 
        "\t\t\t\tAND claimproc.Status!=2";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ClaimProcs for preauths with status not preauth fixed: ") + table.Rows.Count.ToString() + "\r\n";
            }
             
        }
        else
        {
        } 
        return log;
    }

    //Take no action.  Use descriptive explanation.
    public static String claimProcStatusNotMatchClaim(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "";
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc,claim\r\n" + 
            "\t\t\t\t\tWHERE claimproc.ClaimNum=claim.ClaimNum\r\n" + 
            "\t\t\t\t\tAND claim.ClaimStatus=\'R\'\r\n" + 
            "\t\t\t\t\tAND claimproc.Status=" + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.NotReceived).ordinal());
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ClaimProcs with status not matching claim found: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //Take no action.  Use descriptive explanation.
            command = "SELECT claim.PatNum,claim.DateService,claimproc.ProcDate,claimproc.CodeSent,claimproc.FeeBilled\r\n" + 
            "\t\t\t\t\tFROM claimproc,claim\r\n" + 
            "\t\t\t\t\tWHERE claimproc.ClaimNum=claim.ClaimNum\r\n" + 
            "\t\t\t\t\tAND claim.ClaimStatus=\'R\'\r\n" + 
            "\t\t\t\t\tAND claimproc.Status=" + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.NotReceived).ordinal());
            table = Db.getTable(command);
            for (int i = 0;i < table.Rows.Count;i++)
            {
                Patient pat = Patients.GetPat(PIn.Long(table.Rows[i]["PatNum"].ToString()));
                if (i == 0)
                {
                    log += Lans.g("FormDatabaseMaintenance","The following ClaimProc statuses do not match the claim") + ":\r\n";
                }
                 
                log += "   Patient: #" + pat.PatNum.ToString() + ":" + pat.getNameFirstOrPrefL() + " ClaimDate: " + PIn.Date(table.Rows[i]["DateService"].ToString()).ToShortDateString() + " ProcDate: " + PIn.Date(table.Rows[i]["ProcDate"].ToString()).ToShortDateString() + " Code: " + table.Rows[i]["CodeSent"].ToString() + " FeeBilled: " + PIn.Double(table.Rows[i]["FeeBilled"].ToString()).ToString("F") + "\r\n";
            }
            if (table.Rows.Count > 0)
            {
                log += Lans.g("FormDatabaseMaintenance","   They need to be fixed manually.") + "\r\n";
            }
             
        } 
        return log;
    }

    public static String claimProcWithInvalidClaimPaymentNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE claimpaymentnum !=0 AND NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT * FROM claimpayment WHERE claimpayment.ClaimPaymentNum=claimproc.ClaimPaymentNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ClaimProcs with with invalid ClaimPaymentNumber found: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //slightly dangerous.  User will have to create ins check again.  But does not alter financials.
            command = "UPDATE claimproc SET ClaimPaymentNum=0 WHERE claimpaymentnum !=0 AND NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT * FROM claimpayment WHERE claimpayment.ClaimPaymentNum=claimproc.ClaimPaymentNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ClaimProcs with with invalid ClaimPaymentNumber fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    //Tell user what items to create ins checks for?
    public static String claimProcWriteOffNegative(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE WriteOff < 0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Negative writeoffs found: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //Take no action.  Use descriptive explanation.
            command = "SELECT patient.LName,patient.FName,patient.MiddleI,claimproc.CodeSent,procedurelog.ProcFee,procedurelog.ProcDate,claimproc.WriteOff\r\n" + 
            "\t\t\t\t\tFROM claimproc \r\n" + 
            "\t\t\t\t\tLEFT JOIN patient ON claimproc.PatNum=patient.PatNum\r\n" + 
            "\t\t\t\t\tLEFT JOIN procedurelog ON claimproc.ProcNum=procedurelog.ProcNum \r\n" + 
            "\t\t\t\t\tWHERE WriteOff<0";
            table = Db.getTable(command);
            String patientName = new String();
            String codeSent = new String();
            double writeOff = new double();
            double procFee = new double();
            DateTime procDate = new DateTime();
            if (table.Rows.Count > 0)
            {
                log += Lans.g("FormDatabaseMaintenance","List of patients with procedures that have negative writeoffs:\r\n");
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    patientName = table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString() + " " + table.Rows[i]["MiddleI"].ToString();
                    codeSent = table.Rows[i]["CodeSent"].ToString();
                    procDate = PIn.Date(table.Rows[i]["ProcDate"].ToString());
                    writeOff = PIn.Decimal(table.Rows[i]["WriteOff"].ToString());
                    procFee = PIn.Decimal(table.Rows[i]["ProcFee"].ToString());
                    log += patientName + " " + codeSent + " fee:" + procFee.ToString("c") + " date:" + procDate.ToShortDateString() + " writeoff:" + writeOff.ToString("c") + "\r\n";
                }
                log += Lans.g("FormDatabaseMaintenance","Go to the patients listed above and manually correct the writeoffs.\r\n");
            }
             
        } 
        return log;
    }

    public static String clockEventInFuture(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "";
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM clockevent WHERE TimeDisplayed1 > " + DbHelper.now() + "+INTERVAL 15 MINUTE";
            int numFound = PIn.int(Db.getCount(command));
            command = "SELECT COUNT(*) FROM clockevent WHERE TimeDisplayed2 > " + DbHelper.now() + "+INTERVAL 15 MINUTE";
            numFound += PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Timecard entries invalid: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE clockevent SET TimeDisplayed1=" + DbHelper.now() + " WHERE TimeDisplayed1 > " + DbHelper.now() + "+INTERVAL 15 MINUTE";
            long numberFixed = Db.nonQ(command);
            command = "UPDATE clockevent SET TimeDisplayed2=" + DbHelper.now() + " WHERE TimeDisplayed2 > " + DbHelper.now() + "+INTERVAL 15 MINUTE";
            numberFixed += Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Future timecard entry times fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String documentWithNoCategory(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT DocNum FROM document WHERE DocCategory=0";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Images with no category found: ") + table.Rows.Count + "\r\n";
            }
             
        }
        else
        {
            for (int i = 0;i < table.Rows.Count;i++)
            {
                command = "UPDATE document SET DocCategory=" + POut.Long(DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][0].DefNum) + " WHERE DocNum=" + table.Rows[i][0].ToString();
                Db.nonQ(command);
            }
            int numberFixed = table.Rows.Count;
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Images with no category fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String eduResourceInvalidDiseaseDefNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT EduResourceNum FROM eduresource WHERE DiseaseDefNum NOT IN (SELECT DiseaseDefNum FROM diseasedef)";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","EHR Educational Resources with invalid problem found: ") + table.Rows.Count + "\r\n";
            }
             
        }
        else
        {
            command = "SELECT DiseaseDefNum FROM diseasedef WHERE ItemOrder=(SELECT MIN(ItemOrder) FROM diseasedef WHERE IsHidden=0)";
            long defNum = PIn.long(Db.getScalar(command));
            for (int i = 0;i < table.Rows.Count;i++)
            {
                command = "UPDATE eduresource SET DiseaseDefNum='" + defNum + "' WHERE EduResourceNum='" + table.Rows[i][0].ToString() + "'";
                Db.nonQ(command);
            }
            int numberFixed = table.Rows.Count;
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","EHR Educational Resources with invalid problem fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String feeDeleteDuplicates(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        command = "SELECT FeeNum,FeeSched,CodeNum,Amount FROM fee GROUP BY FeeSched,CodeNum HAVING COUNT(CodeNum)>1";
        table = Db.getTable(command);
        int count = table.Rows.Count;
        String log = "";
        if (isCheck)
        {
            if (count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedure codes with duplicate fee entries: ") + count + "\r\n";
            }
             
        }
        else
        {
            //fix
            long numberFixed = 0;
            for (int i = 0;i < count;i++)
            {
                if (i == 0)
                {
                    log += Lans.g("FormDatabaseMaintenance","The following procedure codes had duplicate fee entries.  Verify that the following amounts are correct:") + "\r\n";
                }
                 
                //Make an entry in the log so that the user knows to verify the amount for this fee.
                //No call to db.
                //No call to db.
                log += "Fee Schedule: " + FeeScheds.GetDescription(PIn.Long(table.Rows[i]["FeeSched"].ToString())) + " - Code: " + ProcedureCodes.GetStringProcCode(PIn.Long(table.Rows[i]["CodeNum"].ToString())) + " - Amount: " + PIn.Double(table.Rows[i]["Amount"].ToString()).ToString("n") + "\r\n";
                //At least one fee needs to stay.  Each row in table is a random fee, so we'll just keep that one and delete the rest.
                command = "DELETE FROM fee WHERE FeeSched=" + table.Rows[i]["FeeSched"].ToString() + " AND CodeNum=" + table.Rows[i]["CodeNum"].ToString() + " AND FeeNum!=" + table.Rows[i]["FeeNum"].ToString();
                //This is the random fee we will keep.
                numberFixed += Db.nonQ(command);
            }
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Duplicate fees deleted: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String groupNoteWithInvalidAptNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM procedurelog " + "INNER JOIN procedurecode ON procedurelog.CodeNum=procedurecode.CodeNum " + "WHERE procedurelog.AptNum!=0 AND procedurecode.ProcCode='~GRP~'";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Group notes attached to appointments: ") + numFound.ToString() + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE procedurelog SET AptNum=0 " + "WHERE AptNum!=0 AND CodeNum IN(SELECT CodeNum FROM procedurecode WHERE procedurecode.ProcCode='~GRP~')";
            long numfixed = Db.nonQ(command);
            if (numfixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Group notes attached to appointments fixed: ") + numfixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String groupNoteWithInvalidProcStatus(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM procedurelog " + "INNER JOIN procedurecode ON procedurelog.CodeNum=procedurecode.CodeNum " + "WHERE procedurelog.ProcStatus NOT IN(" + POut.int(((Enum)OpenDentBusiness.ProcStat.D).ordinal()) + "," + POut.int(((Enum)OpenDentBusiness.ProcStat.EC).ordinal()) + ") " + "AND procedurecode.ProcCode='~GRP~'";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Group notes with invalid status: ") + numFound.ToString() + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE procedurelog SET ProcStatus=" + POut.Long(((Enum)OpenDentBusiness.ProcStat.EC).ordinal()) + " " + "WHERE ProcStatus NOT IN(" + POut.int(((Enum)OpenDentBusiness.ProcStat.D).ordinal()) + "," + POut.int(((Enum)OpenDentBusiness.ProcStat.EC).ordinal()) + ") " + "AND CodeNum IN(SELECT CodeNum FROM procedurecode WHERE procedurecode.ProcCode='~GRP~')";
            long numfixed = Db.nonQ(command);
            if (numfixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Group notes statuses fixed: ") + numfixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String insPlanInvalidCarrier(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        //Gets a list of insurance plans that do not have a carrier attached. The list should be blank. If not, then you need to go to the plan listed and add a carrier. Missing carriers will cause the send claims function to give an error.
        command = "SELECT PlanNum FROM insplan WHERE CarrierNum NOT IN (SELECT CarrierNum FROM carrier)";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Ins plans with carrier missing found: ") + table.Rows.Count + "\r\n";
            }
             
        }
        else
        {
            if (table.Rows.Count > 0)
            {
                Carrier carrier = Carriers.getByNameAndPhone("UNKNOWN CARRIER","");
                command = "UPDATE insplan SET CarrierNum=" + POut.long(carrier.CarrierNum) + " WHERE CarrierNum NOT IN (SELECT CarrierNum FROM carrier)";
                //set this new carrier for all insplans
                //which have invalid carriernums
                Db.nonQ(command);
            }
             
            int numberFixed = table.Rows.Count;
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Ins plans with carrier missing fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String insPlanInvalidNum(boolean verbose, boolean isCheck) throws Exception {
        //Many sections removed because they are now fixed in InsSubNumMismatchPlanNum.
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM appointment WHERE appointment.InsPlan1 != 0 AND NOT EXISTS(SELECT * FROM insplan WHERE insplan.PlanNum=appointment.InsPlan1)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Invalid appointment InsPlan1 values: ") + numFound + "\r\n";
            }
             
            command = "SELECT COUNT(*) FROM appointment WHERE appointment.InsPlan2 != 0 AND NOT EXISTS(SELECT * FROM insplan WHERE insplan.PlanNum=appointment.InsPlan2)";
            numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Invalid appointment InsPlan2 values: ") + numFound + "\r\n";
            }
             
            command = "SELECT COUNT(*) FROM benefit WHERE PlanNum !=0 AND NOT EXISTS(SELECT * FROM insplan WHERE insplan.PlanNum=benefit.PlanNum)";
            numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Invalid benefit PlanNums: ") + numFound + "\r\n";
            }
             
            command = "SELECT COUNT(*) FROM inssub WHERE NOT EXISTS(SELECT * FROM insplan WHERE insplan.PlanNum=inssub.PlanNum)";
            numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Invalid inssub PlanNums: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //fix
            //One option will sometimes be to create a dummy plan to attach these things to, be we have not had to implement that yet.
            //We need databases with actual problems to test these fixes against.
            //appointment.InsPlan1-----------------------------------------------------------------------------------------------
            command = "UPDATE appointment SET InsPlan1=0 WHERE InsPlan1 != 0 AND NOT EXISTS(SELECT * FROM insplan WHERE insplan.PlanNum=appointment.InsPlan1)";
            long numFixed = Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Invalid appointment InsPlan1 values fixed: ") + numFixed + "\r\n";
            }
             
            //appointment.InsPlan2-----------------------------------------------------------------------------------------------
            command = "UPDATE appointment SET InsPlan2=0 WHERE InsPlan2 != 0 AND NOT EXISTS(SELECT * FROM insplan WHERE insplan.PlanNum=appointment.InsPlan2)";
            numFixed = Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Invalid appointment InsPlan2 values fixed: ") + numFixed + "\r\n";
            }
             
            //benefit.PlanNum----------------------------------------------------------------------------------------------------
            command = "DELETE FROM benefit WHERE PlanNum !=0 AND NOT EXISTS(SELECT * FROM insplan WHERE insplan.PlanNum=benefit.PlanNum)";
            numFixed = Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Invalid benefit PlanNums fixed: ") + numFixed + "\r\n";
            }
             
            //inssub.PlanNum------------------------------------------------------------------------------------------------------
            numFixed = 0;
            //1: PlanNum=0
            command = "SELECT InsSubNum FROM inssub WHERE PlanNum=0";
            table = Db.getTable(command);
            for (int i = 0;i < table.Rows.Count;i++)
            {
                long insSubNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
                command = "SELECT COUNT(*) FROM claim WHERE InsSubNum=" + POut.long(insSubNum);
                int countUsed = PIn.int(Db.getCount(command));
                command = "SELECT COUNT(*) FROM claimproc WHERE InsSubNum=" + POut.long(insSubNum) + " AND (ClaimNum<>0 OR (Status<>6 AND Status<>3))";
                //attached to a claim or (not an estimate or adjustment)
                countUsed += PIn.int(Db.getCount(command));
                command = "SELECT COUNT(*) FROM etrans WHERE InsSubNum=" + POut.long(insSubNum);
                countUsed += PIn.int(Db.getCount(command));
                //command="SELECT COUNT(*) FROM patplan WHERE InsSubNum="+POut.Long(insSubNum);
                //countUsed+=PIn.Int(Db.GetCount(command));
                command = "SELECT COUNT(*) FROM payplan WHERE InsSubNum=" + POut.long(insSubNum);
                countUsed += PIn.int(Db.getCount(command));
                if (countUsed == 0)
                {
                    command = "DELETE FROM claimproc WHERE InsSubNum=" + POut.long(insSubNum) + " AND ClaimNum=0 AND (Status=6 OR Status=3)";
                    //ok to delete because no claim and just an estimate or adjustment
                    Db.nonQ(command);
                    command = "DELETE FROM inssub WHERE InsSubNum=" + POut.long(insSubNum);
                    Db.nonQ(command);
                    command = "DELETE FROM patplan WHERE InsSubNum=" + POut.long(insSubNum);
                    //It's very safe to "drop coverage" for a patient.
                    Db.nonQ(command);
                    numFixed++;
                    continue;
                }
                 
            }
            //2: PlanNum invalid
            command = "SELECT InsSubNum,PlanNum FROM inssub WHERE NOT EXISTS(SELECT * FROM insplan WHERE insplan.PlanNum=inssub.PlanNum)";
            table = Db.getTable(command);
            for (int i = 0;i < table.Rows.Count;i++)
            {
                long planNum = PIn.Long(table.Rows[i]["PlanNum"].ToString());
                long insSubNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
                command = "SELECT COUNT(*) FROM claim WHERE InsSubNum=" + POut.long(insSubNum);
                int countUsed = PIn.int(Db.getCount(command));
                command = "SELECT COUNT(*) FROM claimproc WHERE InsSubNum=" + POut.long(insSubNum);
                countUsed += PIn.int(Db.getCount(command));
                command = "SELECT COUNT(*) FROM etrans WHERE InsSubNum=" + POut.long(insSubNum);
                countUsed += PIn.int(Db.getCount(command));
                command = "SELECT COUNT(*) FROM patplan WHERE InsSubNum=" + POut.long(insSubNum);
                countUsed += PIn.int(Db.getCount(command));
                command = "SELECT COUNT(*) FROM payplan WHERE InsSubNum=" + POut.long(insSubNum);
                countUsed += PIn.int(Db.getCount(command));
                //planNum
                command = "SELECT COUNT(*) FROM benefit WHERE PlanNum=" + POut.long(planNum);
                countUsed += PIn.int(Db.getCount(command));
                command = "SELECT COUNT(*) FROM claim WHERE PlanNum=" + POut.long(planNum);
                countUsed += PIn.int(Db.getCount(command));
                command = "SELECT COUNT(*) FROM claimproc WHERE PlanNum=" + POut.long(planNum);
                countUsed += PIn.int(Db.getCount(command));
                if (countUsed == 0)
                {
                    //There are no other pointers to this invalid plannum or this inssub, delete this inssub
                    command = "DELETE FROM inssub WHERE InsSubNum=" + POut.long(insSubNum);
                    Db.nonQ(command);
                    numFixed++;
                    continue;
                }
                else
                {
                    //There are objects referencing this inssub or this insplan.  Insert a dummy plan linked to a dummy carrier with CarrierName=Unknown
                    InsPlan insplan = new InsPlan();
                    insplan.IsHidden = true;
                    insplan.CarrierNum = Carriers.getByNameAndPhone("UNKNOWN CARRIER","").CarrierNum;
                    long insPlanNum = InsPlans.insert(insplan);
                    command = "UPDATE inssub SET PlanNum=" + POut.long(insPlanNum) + " WHERE InsSubNum=" + POut.long(insSubNum);
                    Db.nonQ(command);
                    numFixed++;
                    continue;
                } 
            }
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Invalid inssub PlanNums fixed: ") + numFixed + "\r\n";
            }
             
        } 
        return log;
    }

    public static String insPlanNoClaimForm(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM insplan WHERE ClaimFormNum=0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Insplan claimforms missing: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE insplan SET ClaimFormNum=" + POut.long(PrefC.getLong(PrefName.DefaultClaimForm)) + " WHERE ClaimFormNum=0";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Insplan claimforms set if missing: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String insSubInvalidSubscriber(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT Subscriber FROM inssub WHERE Subscriber NOT IN (SELECT PatNum FROM patient) AND Subscriber != 0 GROUP BY Subscriber";
        table = Db.getTable(command);
        if (isCheck)
        {
            int numFound = table.Rows.Count;
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","InsSub subscribers missing: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //Fix
            //Create dummy patients using the FKs that the Subscriber column is expecting.
            long priProv = PrefC.getLong(PrefName.PracticeDefaultProv);
            long billType = PrefC.getLong(PrefName.PracticeDefaultBillType);
            for (int i = 0;i < table.Rows.Count;i++)
            {
                Patient pat = new Patient();
                pat.PatNum = PIn.Long(table.Rows[i]["Subscriber"].ToString());
                pat.LName = "UNKNOWN";
                pat.FName = "Unknown";
                pat.Guarantor = pat.PatNum;
                pat.PriProv = priProv;
                pat.BillingType = billType;
                Patients.insert(pat,true);
            }
            int numberFixed = table.Rows.Count;
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","InsSub subscribers fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String insSubNumMismatchPlanNum(boolean verbose, boolean isCheck) throws Exception {
        //Checks for situations where there are valid InsSubNums, but mismatched PlanNums.
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            int numFound = 0;
            //Can't do the following because no inssubnum: appointmentx2, benefit.
            //Can't do inssub because that's what we're comparing against.  That's the one that's assumed to be correct.
            //claim.PlanNum -----------------------------------------------------------------------------------------------------
            command = "SELECT COUNT(*) FROM claim " + "WHERE PlanNum NOT IN (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=claim.InsSubNum) ";
            numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Mismatched claim InsSubNum/PlanNum values: ") + numFound + "\r\n";
            }
             
            //claim.PlanNum2---------------------------------------------------------------------------------------------------
            command = "SELECT COUNT(*) FROM claim WHERE PlanNum2 != 0 " + "AND PlanNum2 NOT IN (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=claim.InsSubNum2)";
            //not really necessary; just a reminder
            numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Mismatched claim InsSubNum2/PlanNum2 values: ") + numFound + "\r\n";
            }
             
            //claimproc---------------------------------------------------------------------------------------------------
            command = "SELECT COUNT(*) FROM claimproc " + "WHERE PlanNum NOT IN (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=claimproc.InsSubNum)";
            numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Mismatched claimproc InsSubNum/PlanNum values: ") + numFound + "\r\n";
            }
             
            //etrans---------------------------------------------------------------------------------------------------
            command = "SELECT COUNT(*) FROM etrans " + "WHERE PlanNum!=0 AND PlanNum NOT IN (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=etrans.InsSubNum)";
            numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Mismatched etrans InsSubNum/PlanNum values: ") + numFound + "\r\n";
            }
             
            //payplan---------------------------------------------------------------------------------------------------
            command = "SELECT COUNT(*) FROM payplan " + "WHERE EXISTS (SELECT PlanNum FROM inssub WHERE inssub.InsSubNum=payplan.InsSubNum AND inssub.PlanNum!=payplan.PlanNum)";
            numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Mismatched payplan InsSubNum/PlanNum values: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //fix
            long numFixed = 0;
            //claim.PlanNum (1/4) Mismatch------------------------------------------------------------------------------------------------------
            command = "UPDATE claim SET PlanNum = (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=claim.InsSubNum) " + "WHERE PlanNum != (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=claim.InsSubNum)";
            numFixed = Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Mismatched claim InsSubNum/PlanNum fixed: ") + numFixed.ToString() + "\r\n";
            }
             
            numFixed = 0;
            //claim.PlanNum (2/4) PlanNum zero, invalid InsSubNum--------------------------------------------------------------------------------
            //Will leave orphaned claimprocs. No finanicals to check.
            command = "DELETE FROM claim WHERE PlanNum=0 AND ClaimStatus IN ('PreAuth','W','U') AND NOT EXISTS(SELECT * FROM inssub WHERE inssub.InsSubNum=claim.InsSubNum)";
            numFixed = Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claims deleted with invalid InsSubNum and PlanNum=0: ") + numFixed.ToString() + "\r\n";
            }
             
            numFixed = 0;
            //claim.PlanNum (3/4) PlanNum invalid, and claim.InsSubNum invalid-------------------------------------------------------------------
            command = "SELECT claim.PatNum,claim.PlanNum,claim.InsSubNum FROM claim " + "WHERE PlanNum NOT IN (SELECT insplan.PlanNum FROM insplan) " + "AND InsSubNum NOT IN (SELECT inssub.InsSubNum FROM inssub) ";
            table = Db.getTable(command);
            if (table.Rows.Count > 0)
            {
                log += Lans.g("FormDatabaseMaintenance","List of patients who will need insurance information reentered:") + "\r\n";
            }
             
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //Create simple InsPlans and InsSubs for each claim to replace the missing ones.
                //make sure a plan does not exist from a previous insert in this loop
                command = "SELECT COUNT(*) FROM insplan WHERE PlanNum = " + table.Rows[i]["PlanNum"].ToString();
                if (StringSupport.equals(Db.getCount(command), "0"))
                {
                    InsPlan plan = new InsPlan();
                    plan.PlanNum = PIn.Long(table.Rows[i]["PlanNum"].ToString());
                    //reuse the existing FK
                    plan.IsHidden = true;
                    plan.CarrierNum = Carriers.getByNameAndPhone("UNKNOWN CARRIER","").CarrierNum;
                    InsPlans.insert(plan,true);
                }
                 
                long patNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
                //make sure an inssub does not exist from a previous insert in this loop
                command = "SELECT COUNT(*) FROM inssub WHERE InsSubNum = " + table.Rows[i]["InsSubNum"].ToString();
                if (StringSupport.equals(Db.getCount(command), "0"))
                {
                    InsSub sub = new InsSub();
                    sub.InsSubNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
                    //reuse the existing FK
                    sub.PlanNum = PIn.Long(table.Rows[i]["PlanNum"].ToString());
                    sub.Subscriber = patNum;
                    //if this sub was created on a previous loop, this may be some other patient.
                    sub.SubscriberID = "unknown";
                    InsSubs.insert(sub,true);
                }
                 
                Patient pat = Patients.getLim(patNum);
                log += "PatNum: " + pat.PatNum + " - " + Patients.getNameFL(pat.LName,pat.FName,pat.Preferred,pat.MiddleI) + "\r\n";
            }
            numFixed = table.Rows.Count;
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claims with invalid PlanNums and invalid InsSubNums fixed: ") + numFixed.ToString() + "\r\n";
            }
             
            numFixed = 0;
            //claim.PlanNum (4/4) PlanNum valid, but claim.InsSubNum invalid-------------------------------------------------------------------
            command = "SELECT PatNum,PlanNum,InsSubNum FROM claim " + "WHERE PlanNum IN (SELECT insplan.PlanNum FROM insplan) " + "AND InsSubNum NOT IN (SELECT inssub.InsSubNum FROM inssub) GROUP BY InsSubNum";
            table = Db.getTable(command);
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //Create a dummy inssub and link it to the valid plan.
                InsSub sub = new InsSub();
                sub.InsSubNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
                sub.PlanNum = PIn.Long(table.Rows[i]["PlanNum"].ToString());
                sub.Subscriber = PIn.Long(table.Rows[i]["PatNum"].ToString());
                sub.SubscriberID = "unknown";
                InsSubs.insert(sub,true);
            }
            numFixed = table.Rows.Count;
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claims with invalid InsSubNums and invalid PlanNums fixed: ") + numFixed.ToString() + "\r\n";
            }
             
            numFixed = 0;
            //claim.PlanNum2---------------------------------------------------------------------------------------------------
            command = "UPDATE claim SET PlanNum2 = (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=claim.InsSubNum2) " + "WHERE PlanNum2 != 0 " + "AND PlanNum2 NOT IN (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=claim.InsSubNum2)";
            //if InsSubNum2 was completely invalid, then PlanNum2 gets set to zero here.
            numFixed = Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Mismatched claim InsSubNum2/PlanNum2 fixed: ") + numFixed.ToString() + "\r\n";
            }
             
            numFixed = 0;
            //claimproc (1/2) If planNum is valid but InsSubNum does not exist, then add a dummy inssub----------------------------------------
            command = "SELECT PatNum,PlanNum,InsSubNum FROM claimproc " + "WHERE PlanNum IN (SELECT insplan.PlanNum FROM insplan) " + "AND InsSubNum NOT IN (SELECT inssub.InsSubNum FROM inssub) GROUP BY InsSubNum";
            table = Db.getTable(command);
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //Create a dummy inssub and link it to the valid plan.
                InsSub sub = new InsSub();
                sub.InsSubNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
                sub.PlanNum = PIn.Long(table.Rows[i]["PlanNum"].ToString());
                sub.Subscriber = PIn.Long(table.Rows[i]["PatNum"].ToString());
                sub.SubscriberID = "unknown";
                InsSubs.insert(sub,true);
            }
            numFixed = table.Rows.Count;
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claims with valid PlanNums and invalid InsSubNums fixed: ") + numFixed.ToString() + "\r\n";
            }
             
            numFixed = 0;
            //claimproc (2/2) Mismatch, but InsSubNum is valid
            command = "UPDATE claimproc SET PlanNum = (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=claimproc.InsSubNum) " + "WHERE PlanNum != (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=claimproc.InsSubNum)";
            numFixed = Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Mismatched claimproc InsSubNum/PlanNum fixed: ") + numFixed.ToString() + "\r\n";
            }
             
            numFixed = 0;
            //claimproc.PlanNum zero, invalid InsSubNum--------------------------------------------------------------------------------
            command = "DELETE FROM claimproc WHERE PlanNum=0 AND NOT EXISTS(SELECT * FROM inssub WHERE inssub.InsSubNum=claimproc.InsSubNum)" + " AND InsPayAmt=0 AND WriteOff=0" + " AND Status IN (6,2)";
            //Make sure this deletion will not affect financials.
            //OK to delete because no claim and just an estimate (6) or preauth (2) claimproc
            numFixed = Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Claimprocs deleted with invalid InsSubNum and PlanNum=0: ") + numFixed.ToString() + "\r\n";
            }
             
            numFixed = 0;
            //etrans---------------------------------------------------------------------------------------------------
            command = "UPDATE etrans SET PlanNum = (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=etrans.InsSubNum) " + "WHERE PlanNum!=0 AND PlanNum != (SELECT inssub.PlanNum FROM inssub WHERE inssub.InsSubNum=etrans.InsSubNum)";
            numFixed = Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Mismatched etrans InsSubNum/PlanNum fixed: ") + numFixed.ToString() + "\r\n";
            }
             
            numFixed = 0;
            //payplan--------------------------------------------------------------------------------------------------
            command = "UPDATE payplan SET PlanNum=(SELECT PlanNum FROM inssub WHERE inssub.InsSubNum=payplan.InsSubNum) " + "WHERE EXISTS (SELECT PlanNum FROM inssub WHERE inssub.InsSubNum=payplan.InsSubNum AND inssub.PlanNum!=payplan.PlanNum)";
            numFixed = Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Mismatched payplan InsSubNum/PlanNum fixed: ") + numFixed.ToString() + "\r\n";
            }
             
            numFixed = 0;
        } 
        return log;
    }

    public static String journalEntryInvalidAccountNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT COUNT(*) FROM journalentry WHERE AccountNum NOT IN(SELECT AccountNum FROM account)";
        int numFound = PIn.int(Db.getCount(command));
        if (isCheck)
        {
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Transactions found attached to an invalid account") + ": " + numFound + "\r\n";
            }
             
        }
        else
        {
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Transactions found attached to an invalid account") + ": " + numFound + "\r\n";
            }
             
            if (numFound > 0)
            {
                //Check to see if there is already an active account called UNKNOWN.
                command = "SELECT AccountNum FROM account WHERE Description='UNKNOWN' AND Inactive=0";
                long accountNum = PIn.long(Db.getScalar(command));
                if (accountNum == 0)
                {
                    //Create a new Account called UNKNOWN.
                    Account account = new Account();
                    account.Description = "UNKNOWN";
                    account.Inactive = false;
                    //Just in case.
                    account.AcctType = AccountType.Asset;
                    //Default account type.  This DBM check was added to fix orphaned automatic payment journal entries, which should have been associated to an income account.
                    accountNum = Accounts.insert(account);
                }
                 
                //Update the journalentry table.
                command = "UPDATE journalentry SET AccountNum=" + POut.long(accountNum) + " WHERE AccountNum NOT IN(SELECT AccountNum FROM account)";
                Db.nonQ(command);
                log += Lans.g("FormDatabaseMaintenance","   All invalid transactions have been attached to the account called UNKNOWN.") + "\r\n";
                log += Lans.g("FormDatabaseMaintenance","   They need to be fixed manually.") + "\r\n";
            }
             
        } 
        return log;
    }

    public static String labCaseWithInvalidLaboratory(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM labcase WHERE laboratoryNum NOT IN(SELECT laboratoryNum FROM laboratory)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Lab cases found with invalid laboratories") + ": " + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "SELECT COUNT(*) FROM labcase WHERE laboratoryNum NOT IN(SELECT laboratoryNum FROM laboratory)";
            long numberFixed = long.Parse(Db.getCount(command));
            command = "SELECT * FROM labcase WHERE laboratoryNum NOT IN(SELECT laboratoryNum FROM laboratory) GROUP BY LaboratoryNum";
            table = Db.getTable(command);
            long labnum = new long();
            for (int i = 0;i < table.Rows.Count;i++)
            {
                Laboratory lab = new Laboratory();
                lab.LaboratoryNum = PIn.Long(table.Rows[i]["LaboratoryNum"].ToString());
                lab.Description = "Laboratory " + table.Rows[i]["LaboratoryNum"].ToString();
                //laboratoryNum is not allowed to be zero
                labnum = Crud.LaboratoryCrud.Insert(lab);
                command = "UPDATE labcase SET LaboratoryNum=" + POut.long(labnum) + " WHERE LaboratoryNum=" + table.Rows[i]["LaboratoryNum"].ToString();
                Db.nonQ(command);
            }
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Lab cases fixed with invalid laboratories") + ": " + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String laboratoryWithInvalidSlip(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM laboratory WHERE Slip NOT IN(SELECT SheetDefNum FROM sheetdef) AND Slip != 0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Laboratories found with invalid lab slips") + ": " + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE laboratory SET Slip=0 WHERE Slip NOT IN(SELECT SheetDefNum FROM sheetdef) AND Slip != 0";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Laboratories fixed with invalid lab slips") + ": " + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String medicationPatDeleteWithInvalidMedNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM medicationpat WHERE (medicationpat.MedicationNum=0 AND medicationpat.NewCropGuid='') OR " + "(medicationpat.MedicationNum<>0 AND NOT EXISTS(SELECT * FROM medication WHERE medication.MedicationNum=medicationpat.MedicationNum))";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Medications found where no defition exists for them: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM medicationpat WHERE (medicationpat.MedicationNum=0 AND medicationpat.NewCropGuid='') OR " + "(medicationpat.MedicationNum<>0 AND NOT EXISTS(SELECT * FROM medication WHERE medication.MedicationNum=medicationpat.MedicationNum))";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Medications deleted because no definition exists for them: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String messageButtonDuplicateButtonIndex(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        String queryStr = "SELECT COUNT(*) NumFound,SigButDefNum,ButtonIndex,ComputerName FROM sigbutdef GROUP BY ComputerName,ButtonIndex HAVING COUNT(*) > 1";
        table = Db.getTable(queryStr);
        int numFound = 0;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            numFound += PIn.Int(table.Rows[i]["NumFound"].ToString()) - 1;
        }
        //Gets the actual number of rows that will be altered.
        if (isCheck)
        {
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Messaging buttons found with invalid button orders: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            do
            {
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    //fix
                    //Loop through the messaging buttons and increment the duplicate button index by the max plus one.
                    command = "SELECT MAX(ButtonIndex) FROM sigbutdef WHERE ComputerName='" + table.Rows[i]["ComputerName"].ToString() + "'";
                    int newIndex = PIn.int(Db.getScalar(command)) + 1;
                    command = "UPDATE sigbutdef SET ButtonIndex=" + newIndex.ToString() + " WHERE SigButDefNum=" + table.Rows[i]["SigButDefNum"].ToString();
                    Db.nonQ(command);
                }
                //It's possible we need to loop through several more times depending on how many items shared the same button index.
                table = Db.getTable(queryStr);
            }
            while (table.Rows.Count > 0);
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Messaging buttons with invalid button orders fixed: ") + numFound.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String patFieldsDeleteDuplicates(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "";
        }
         
        String log = "";
        //This code is only needed for older db's. New DB's created after 12.2.30 and 12.3.2 shouldn't need this.
        String command = "DROP TABLE IF EXISTS tempduplicatepatfields";
        Db.nonQ(command);
        String tableName = "tempduplicatepatfields" + CodeBase.MiscUtils.createRandomAlphaNumericString(8);
        //max size for a table name in oracle is 30 chars.
        //This query run very fast on a db with no corruption.
        command = "CREATE TABLE " + tableName + "\r\n" + 
        "\t\t\t\t\t\t\t\tSELECT *\r\n" + 
        "\t\t\t\t\t\t\t\tFROM patfield\r\n" + 
        "\t\t\t\t\t\t\t\tGROUP BY PatNum,FieldName\r\n" + 
        "\t\t\t\t\t\t\t\tHAVING COUNT(*)>1";
        Db.nonQ(command);
        command = "SELECT patient.PatNum,LName,FName\r\n" + 
        "\t\t\t\t\t\t\t\tFROM patient \r\n" + 
        "\t\t\t\t\t\t\t\tINNER JOIN " + tableName + " t ON t.PatNum=patient.PatNum\r\n" + 
        "\t\t\t\t\t\t\t\tGROUP BY patient.PatNum";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Patients with duplicate field entries found: ") + table.Rows.Count + ".\r\n";
            }
             
        }
        else
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","The following patients had corrupt Patient Fields. Please verify the Patient Fields of these patients:") + "\r\n";
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    log += "#" + table.Rows[i]["PatNum"].ToString() + " " + table.Rows[i]["LName"] + ", " + table.Rows[i]["FName"] + ".\r\n";
                }
                //Without this index the delete process takes too long.
                command = "ALTER TABLE " + tableName + " ADD INDEX(PatNum)";
                Db.nonQ(command);
                command = "ALTER TABLE " + tableName + " ADD INDEX(FieldName)";
                Db.nonQ(command);
                command = "DELETE FROM patfield WHERE ((PatNum, FieldName) IN (SELECT PatNum, FieldName FROM " + tableName + "));";
                Db.nonQ(command);
                command = "INSERT INTO patfield SELECT * FROM " + tableName + ";";
                Db.nonQ(command);
                log += Lans.g("FormDatabaseMaintenance","Patients with duplicate field entries removed: ") + table.Rows.Count + ".\r\n";
            }
             
        } 
        command = "DROP TABLE IF EXISTS " + tableName;
        Db.nonQ(command);
        return log;
    }

    public static String patientBadGuarantor(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT p.PatNum FROM patient p LEFT JOIN patient p2 ON p.Guarantor = p2.PatNum WHERE p2.PatNum IS NULL";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Patients with invalid Guarantors found: ") + table.Rows.Count.ToString() + "\r\n";
            }
             
        }
        else
        {
            for (int i = 0;i < table.Rows.Count;i++)
            {
                command = "UPDATE patient SET Guarantor=PatNum WHERE PatNum=" + table.Rows[i][0].ToString();
                Db.nonQ(command);
            }
            int numberFixed = table.Rows.Count;
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Patients with invalid Guarantors fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String patientBadGuarantorWithAnotherGuarantor(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT p.PatNum,p2.Guarantor FROM patient p LEFT JOIN patient p2 ON p.Guarantor=p2.PatNum WHERE p2.PatNum!=p2.Guarantor";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Patients with a guarantor who has another guarantor found: ") + table.Rows.Count.ToString() + "\r\n";
            }
             
        }
        else
        {
            for (int i = 0;i < table.Rows.Count;i++)
            {
                command = "UPDATE patient SET Guarantor=" + table.Rows[i]["Guarantor"].ToString() + " WHERE PatNum=" + table.Rows[i]["PatNum"].ToString();
                Db.nonQ(command);
            }
            int numberFixed = table.Rows.Count;
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Patients with a guarantor who has another guarantor fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String patientDeletedWithClinicNumSet(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM patient WHERE ClinicNum!=0 AND PatStatus=" + POut.int(((Enum)PatientStatus.Deleted).ordinal());
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Deleted patients with a clinic still set: ") + numFound.ToString() + "\r\n";
            }
             
        }
        else
        {
            //fix
            command = "UPDATE patient SET ClinicNum=0 WHERE ClinicNum!=0 AND PatStatus=" + POut.int(((Enum)PatientStatus.Deleted).ordinal());
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Deleted patients with clinics cleared: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String patientsNoClinicSet(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        //same behavior whether check or fix
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            return log;
        }
         
        //Get the number of patients not assigned to a clinic:
        command = "SELECT COUNT(*) FROM patient WHERE ClinicNum=0 AND PatStatus!=" + POut.int(((Enum)PatientStatus.Deleted).ordinal());
        int count = PIn.int(Db.getCount(command));
        command = "SELECT PatNum,LName,FName FROM patient WHERE ClinicNum=0 AND PatStatus!=" + POut.int(((Enum)PatientStatus.Deleted).ordinal()) + " LIMIT 30";
        table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return log;
        }
         
        log += Lans.g("FormDatabaseMaintenance","Patients with no Clinic assigned: ") + count.ToString() + Lans.g("FormDatabaseMaintenance",", including: ");
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //Start a new line and indent every three patients for printing purposes.
            if (i % 3 == 0)
            {
                log += "\r\n   ";
            }
             
            log += table.Rows[i]["PatNum"].ToString() + "-" + table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString() + "; ";
        }
        log += "\r\n";
        return log;
    }

    public static String patientPriProvHidden(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT ProvNum,Abbr FROM provider WHERE ProvNum IN (SELECT PriProv FROM patient WHERE patient.PriProv=provider.ProvNum) AND IsHidden=1";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Hidden providers with patients: ") + table.Rows.Count + "\r\n";
                DataTable patTable = new DataTable();
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    log += "     " + table.Rows[i]["Abbr"].ToString() + ": ";
                    command = "SELECT PatNum,LName,FName FROM patient WHERE PriProv=(SELECT ProvNum FROM provider WHERE ProvNum=" + table.Rows[i]["ProvNum"].ToString() + " AND IsHidden=1) LIMIT 10";
                    patTable = Db.getTable(command);
                    for (int j = 0;j < patTable.Rows.Count;j++)
                    {
                        if (j > 0)
                        {
                            log += ", ";
                        }
                         
                        log += patTable.Rows[j]["PatNum"].ToString() + "-" + patTable.Rows[j]["FName"].ToString() + " " + patTable.Rows[j]["LName"].ToString();
                    }
                    log += "\r\n";
                }
            }
             
        }
        else
        {
        } 
        return log;
    }

    //Currently no fix.
    //Proposed fix is to add a tool to Lists>Providers and allow quick reassigning of patients and their providers.
    public static String patientPriProvMissing(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM patient WHERE PriProv=0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Patient pri provs not set: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //previous versions of the program just dealt gracefully with missing provnum.
            //From now on, we can assum priprov is not missing, making coding easier.
            command = "UPDATE patient SET PriProv=" + PrefC.getString(PrefName.PracticeDefaultProv) + " WHERE PriProv=0";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Patient pri provs fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String patientUnDeleteWithBalance(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT PatNum FROM patient	WHERE PatStatus=4 " + "AND (Bal_0_30 !=0	OR Bal_31_60 !=0 OR Bal_61_90 !=0	OR BalOver90 !=0 OR InsEst !=0 OR BalTotal !=0)";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Patients found who are marked deleted with non-zero balances: ") + table.Rows.Count + "\r\n";
            }
             
        }
        else
        {
            if (table.Rows.Count == 0 && verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","No balances found for deleted patients.") + "\r\n";
                return log;
            }
             
            Patient pat;
            Patient old;
            for (int i = 0;i < table.Rows.Count;i++)
            {
                pat = Patients.GetPat(PIn.Long(table.Rows[i][0].ToString()));
                old = pat.copy();
                pat.LName = pat.LName + Lans.g("FormDatabaseMaintenance","DELETED");
                pat.PatStatus = PatientStatus.Archived;
                Patients.update(pat,old);
                log += Lans.g("FormDatabaseMaintenance","Warning!  Patient:") + " " + old.getNameFL() + " " + Lans.g("FormDatabaseMaintenance","was previously marked as deleted, but was found to have a balance. Patient has been changed to Archive status.  The account will need to be cleared, and the patient deleted again.") + "\r\n";
            }
        } 
        return log;
    }

    public static String patPlanDeleteWithInvalidInsSubNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM patplan WHERE InsSubNum NOT IN (SELECT InsSubNum FROM inssub)";
            String countStr = Db.getCount(command);
            if (!StringSupport.equals(countStr, "0") || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Pat plans found with invalid InsSubNums: ") + countStr + "\r\n";
            }
             
        }
        else
        {
            //fix
            command = "DELETE FROM patplan WHERE InsSubNum NOT IN (SELECT InsSubNum FROM inssub)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Pat plans with invalid InsSubNums deleted: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String patPlanDeleteWithInvalidPatNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM patplan WHERE PatNum NOT IN (SELECT PatNum FROM patient)";
            String countStr = Db.getCount(command);
            if (!StringSupport.equals(countStr, "0") || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Pat plans found with invalid PatNums: ") + countStr + "\r\n";
            }
             
        }
        else
        {
            //fix
            command = "DELETE FROM patplan WHERE PatNum NOT IN (SELECT PatNum FROM patient)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Pat plans with invalid PatNums deleted: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String patPlanOrdinalDuplicates(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT patient.PatNum,patient.LName,patient.FName,COUNT(*) " + "FROM patplan " + "INNER JOIN patient ON patient.PatNum=patplan.PatNum " + "GROUP BY patplan.PatNum,patplan.Ordinal " + "HAVING COUNT(*)>1";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","PatPlan duplicate ordinals: ") + table.Rows.Count + "\r\n";
            }
             
            for (int i = 0;i < table.Rows.Count;i++)
            {
                log += Lans.g("FormDatabaseMaintenance","PatPlan duplicate ordinals for patient must be manually fixed: ") + PIn.String(table.Rows[i]["FName"].ToString()) + " " + PIn.String(table.Rows[i]["LName"].ToString()) + "\r\n";
            }
        }
        else
        {
        } 
        return log;
    }

    //No fix. User needs to fix manually.
    public static String patPlanOrdinalZeroToOne(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT PatPlanNum,PatNum FROM patplan WHERE Ordinal=0";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","PatPlan ordinals currently zero: ") + table.Rows.Count + "\r\n";
            }
             
        }
        else
        {
            int numberFixed = 0;
            for (int i = 0;i < table.Rows.Count;i++)
            {
                PatPlan patPlan = PatPlans.GetPatPlan(PIn.Long(table.Rows[i][1].ToString()), 0);
                if (patPlan != null)
                {
                    //Unlikely but possible if plan gets deleted by a user during this check.
                    PatPlans.setOrdinal(patPlan.PatPlanNum,1);
                    numberFixed++;
                }
                 
            }
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","PatPlan ordinals changed from 0 to 1: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String patPlanOrdinalTwoToOne(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT PatPlanNum,PatNum FROM patplan patplan1 WHERE Ordinal=2 AND NOT EXISTS(" + "SELECT * FROM patplan patplan2 WHERE patplan1.PatNum=patplan2.PatNum AND patplan2.Ordinal=1)";
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","PatPlans for secondary found where no primary ins: ") + table.Rows.Count + "\r\n";
            }
             
        }
        else
        {
            int numberFixed = 0;
            for (int i = 0;i < table.Rows.Count;i++)
            {
                PatPlan patPlan = PatPlans.GetPatPlan(PIn.Int(table.Rows[i][1].ToString()), 2);
                if (patPlan != null)
                {
                    //Unlikely but possible if plan gets deleted by a user during this check.
                    PatPlans.setOrdinal(patPlan.PatPlanNum,1);
                    numberFixed++;
                }
                 
            }
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","PatPlan ordinals changed from 2 to 1 if no primary ins: ") + numberFixed + "\r\n";
            }
             
        } 
        return log;
    }

    public static String paymentDetachMissingDeposit(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM payment " + "WHERE DepositNum != 0 " + "AND NOT EXISTS(SELECT * FROM deposit WHERE deposit.DepositNum=payment.DepositNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Payments attached to deposits that no longer exist: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE payment SET DepositNum=0 " + "WHERE DepositNum != 0 " + "AND NOT EXISTS(SELECT * FROM deposit WHERE deposit.DepositNum=payment.DepositNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Payments detached from deposits that no longer exist: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String paymentMissingPaySplit(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM payment " + "WHERE PayNum NOT IN (SELECT PayNum FROM paysplit) " + "AND ((DepositNum=0) " + "OR (DepositNum!=0 AND PayAmt=0))";
            //Payments with no split that are
            //not attached to a deposit
            //or attached to a deposit with no amount.
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Payments with no attached paysplit: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM payment " + "WHERE PayNum NOT IN (SELECT PayNum FROM paysplit) " + "AND ((DepositNum=0) " + "OR (DepositNum!=0 AND PayAmt=0))";
            //Payments with no split that are
            //not attached to a deposit
            //or attached to a deposit with no amount.
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Payments with no attached paysplit fixed: ") + numberFixed + "\r\n";
            }
             
        } 
        return log;
    }

    public static String payPlanChargeGuarantorMatch(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "";
        }
         
        String log = "";
        if (isCheck)
        {
            int numFound = 0;
            command = "SELECT COUNT(*) FROM payplancharge,payplan " + "WHERE payplan.PayPlanNum=payplancharge.PayPlanNum " + "AND payplancharge.Guarantor != payplan.Guarantor";
            numFound += PIn.int(Db.getCount(command));
            command = "SELECT COUNT(*) FROM payplancharge,payplan " + "WHERE payplan.PayPlanNum=payplancharge.PayPlanNum " + "AND payplancharge.PatNum != payplan.PatNum";
            numFound += PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","PayPlanCharge guarantors and pats not matching payplan guarantors and pats: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //Fix the cases where payplan.Guarantor and payplan.PatNum are not zero.
            command = "UPDATE payplan,payplancharge " + "SET payplancharge.Guarantor=payplan.Guarantor " + "WHERE payplan.PayPlanNum=payplancharge.PayPlanNum " + "AND payplancharge.Guarantor != payplan.Guarantor " + "AND payplan.Guarantor != 0";
            long numFixed = Db.nonQ(command);
            command = "UPDATE payplan,payplancharge " + "SET payplancharge.PatNum=payplan.PatNum " + "WHERE payplan.PayPlanNum=payplancharge.PayPlanNum " + "AND payplancharge.PatNum != payplan.PatNum " + "AND payplan.PatNum != 0";
            numFixed += Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","PayPlanCharge guarantors and pats fixed to match payplan: ") + numFixed + "\r\n";
            }
             
        } 
        return log;
    }

    //No fix yet if payplan.Guarantor or payplan.PatNum are zero but there are good values in PayPlanCharge.
    public static String payPlanChargeProvNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM payplancharge WHERE ProvNum=0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Pay plan charge providers missing: ") + numFound + "\r\n";
            }
             
        }
        else
        {
        } 
        return log;
    }

    //Take no action.  Use descriptive explanation.
    public static String payPlanSetGuarantorToPatForIns(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM payplan WHERE PlanNum>0 AND Guarantor != PatNum";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","PayPlan Guarantors not equal to PatNum where used for insurance tracking: ") + numFound + "\r\n";
            }
             
        }
        else
        {
        } 
        return log;
    }

    //Too dangerous to do anything at all.  Just have a very descriptive explanation in the check.
    public static String paySplitAttachedToPayPlan(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT SplitNum,payplan.Guarantor FROM paysplit,payplan " + "WHERE paysplit.PayPlanNum=payplan.PayPlanNum " + "AND paysplit.PatNum!=payplan.Guarantor";
        DataTable table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Paysplits found with patnum not matching payplan guarantor: ") + table.Rows.Count + "\r\n";
            }
             
        }
        else
        {
        } 
        return log;
    }

    //Too dangerous to do anything at all.  Just have a very descriptive explanation in the check.
    public static String paySplitWithInvalidPayNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM paysplit WHERE NOT EXISTS(SELECT * FROM payment WHERE paysplit.PayNum=payment.PayNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Paysplits found with invalid PayNum: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                return "";
            }
             
            command = "SELECT *,SUM(SplitAmt) SplitAmt_ FROM paysplit WHERE NOT EXISTS(SELECT * FROM payment WHERE paysplit.PayNum=payment.PayNum) GROUP BY PayNum";
            DataTable table = Db.getTable(command);
            if (table.Rows.Count > 0 || verbose)
            {
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    /**
                    * There's only one place in the program where this is called from.  Date is today, so no need to validate the date.
                    */
                    Payment payment = new Payment();
                    payment.PayType = DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][0].DefNum;
                    payment.DateEntry = PIn.Date(table.Rows[i]["DateEntry"].ToString());
                    payment.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
                    payment.PayDate = PIn.Date(table.Rows[i]["DatePay"].ToString());
                    payment.PayAmt = PIn.Double(table.Rows[i]["SplitAmt_"].ToString());
                    payment.PayNote = "Dummy payment. Original payment entry missing from the database.";
                    payment.PayNum = PIn.Long(table.Rows[i]["PayNum"].ToString());
                    Payments.insert(payment,true);
                }
                log += Lans.g("FormDatabaseMaintenance","Paysplits found with invalid PayNum fixed: ") + table.Rows.Count + "\r\n";
            }
             
        } 
        return log;
    }

    public static String paySplitWithInvalidPayPlanNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM paysplit WHERE paysplit.PayPlanNum!=0 AND paysplit.PayPlanNum NOT IN(SELECT payplan.PayPlanNum FROM payplan)";
            int numFound = PIn.int(Db.getScalar(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Paysplits found with invalid PayPlanNum: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                return "";
            }
             
            command = "UPDATE paysplit SET paysplit.PayPlanNum=0 WHERE paysplit.PayPlanNum!=0 AND paysplit.PayPlanNum NOT IN(SELECT payplan.PayPlanNum FROM payplan)";
            long numFixed = Db.nonQ(command);
            if (numFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Paysplits with invalid PayPlanNums fixed: ") + numFixed + "\r\n";
            }
             
        } 
        return log;
    }

    public static String perioMeasureWithInvalidIntTooth(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM periomeasure WHERE IntTooth > 32 OR IntTooth < 1";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","PerioMeasures found with invalid tooth number: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM periomeasure WHERE IntTooth > 32 OR IntTooth < 1";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","PerioMeasures deleted due to invalid tooth number: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String preferenceAllergiesIndicateNone(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM allergydef where AllergyDefNum=" + POut.long(PrefC.getLong(PrefName.AllergiesIndicateNone));
            if (PIn.int(Db.getCount(command)) == 0 && !StringSupport.equals(PrefC.getString(PrefName.AllergiesIndicateNone), ""))
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"AllergyIndicatesNone\" is an invalid value.") + "\r\n";
            }
            else if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"AllergyIndicatesNone\" checked.") + "\r\n";
            }
              
        }
        else
        {
            command = "SELECT COUNT(*) FROM allergydef where AllergyDefNum=" + POut.long(PrefC.getLong(PrefName.AllergiesIndicateNone));
            if (PIn.int(Db.getCount(command)) == 0 && !StringSupport.equals(PrefC.getString(PrefName.AllergiesIndicateNone), ""))
            {
                Prefs.updateString(PrefName.AllergiesIndicateNone,"");
                Signalods.setInvalid(InvalidType.Prefs);
                log += Lans.g("FormDatabaseMaintenance","Preference \"AllergyIndicatesNone\" set to blank due to an invalid value.") + "\r\n";
            }
            else if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"AllergyIndicatesNone\" checked.") + "\r\n";
            }
              
        } 
        return log;
    }

    public static String preferenceDateDepositsStarted(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            DateTime date = PrefC.getDate(PrefName.DateDepositsStarted);
            if (date < DateTime.Now.AddMonths(-1))
            {
                log += Lans.g("FormDatabaseMaintenance","Deposit start date needs to be reset.") + "\r\n";
            }
            else if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Deposit start date checked.") + "\r\n";
            }
              
        }
        else
        {
            //If the program locks up when trying to create a deposit slip, it's because someone removed the start date from the deposit edit window. Run this query to get back in.
            DateTime date = PrefC.getDate(PrefName.DateDepositsStarted);
            if (date < DateTime.Now.AddMonths(-1))
            {
                command = "UPDATE preference SET ValueString=" + POut.Date(DateTime.Today.AddDays(-21)) + " WHERE PrefName='DateDepositsStarted'";
                Db.nonQ(command);
                Signalods.setInvalid(InvalidType.Prefs);
                log += Lans.g("FormDatabaseMaintenance","Deposit start date reset.") + "\r\n";
            }
            else if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Deposit start date checked.") + "\r\n";
            }
              
        } 
        return log;
    }

    public static String preferenceMedicationsIndicateNone(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM medication where MedicationNum=" + POut.long(PrefC.getLong(PrefName.MedicationsIndicateNone));
            if (PIn.int(Db.getCount(command)) == 0 && !StringSupport.equals(PrefC.getString(PrefName.MedicationsIndicateNone), ""))
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"MedicationsIndicateNone\" is an invalid value.") + "\r\n";
            }
            else if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"MedicationsIndicateNone\" checked.") + "\r\n";
            }
              
        }
        else
        {
            command = "SELECT COUNT(*) FROM medication where MedicationNum=" + POut.long(PrefC.getLong(PrefName.MedicationsIndicateNone));
            if (PIn.int(Db.getCount(command)) == 0 && !StringSupport.equals(PrefC.getString(PrefName.MedicationsIndicateNone), ""))
            {
                Prefs.updateString(PrefName.MedicationsIndicateNone,"");
                Signalods.setInvalid(InvalidType.Prefs);
                log += Lans.g("FormDatabaseMaintenance","Preference \"MedicationsIndicateNone\" set to blank due to an invalid value.") + "\r\n";
            }
            else if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"MedicationsIndicateNone\" checked.") + "\r\n";
            }
              
        } 
        return log;
    }

    public static String preferenceProblemsIndicateNone(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM diseasedef where DiseaseDefNum=" + POut.long(PrefC.getLong(PrefName.ProblemsIndicateNone));
            if (PIn.int(Db.getCount(command)) == 0 && !StringSupport.equals(PrefC.getString(PrefName.ProblemsIndicateNone), ""))
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"ProblemsIndicateNone\" is an invalid value.") + "\r\n";
            }
            else if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"ProblemsIndicateNone\" checked.") + "\r\n";
            }
              
        }
        else
        {
            command = "SELECT COUNT(*) FROM diseasedef where DiseaseDefNum=" + POut.long(PrefC.getLong(PrefName.ProblemsIndicateNone));
            if (PIn.int(Db.getCount(command)) == 0 && !StringSupport.equals(PrefC.getString(PrefName.ProblemsIndicateNone), ""))
            {
                Prefs.updateString(PrefName.ProblemsIndicateNone,"");
                Signalods.setInvalid(InvalidType.Prefs);
                log += Lans.g("FormDatabaseMaintenance","Preference \"ProblemsIndicateNone\" set to blank due to an invalid value.") + "\r\n";
            }
            else if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"ProblemsIndicateNone\" checked.") + "\r\n";
            }
              
        } 
        return log;
    }

    public static String preferenceTimeCardOvertimeFirstDayOfWeek(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            if (PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek) < 0 || PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek) > 6)
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"TimeCardOvertimeFirstDayOfWeek\" is an invalid value.") + "\r\n";
            }
            else if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"TimeCardOvertimeFirstDayOfWeek\" checked.") + "\r\n";
            }
              
        }
        else
        {
            if (PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek) < 0 || PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek) > 6)
            {
                Prefs.updateInt(PrefName.TimeCardOvertimeFirstDayOfWeek,0);
                //0==Sunday
                Signalods.setInvalid(InvalidType.Prefs);
                log += Lans.g("FormDatabaseMaintenance","Preference \"TimeCardOvertimeFirstDayOfWeek\" set to Sunday due to an invalid value.") + "\r\n";
            }
            else if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Preference \"TimeCardOvertimeFirstDayOfWeek\" checked.") + "\r\n";
            }
              
        } 
        return log;
    }

    public static String preferencePracticeProv(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT valuestring FROM preference WHERE prefname = 'PracticeDefaultProv'";
        table = Db.getTable(command);
        if (!StringSupport.equals(table.Rows[0][0].ToString(), ""))
        {
            if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Default practice provider verified.") + "\r\n";
            }
             
        }
        else
        {
            log += Lans.g("FormDatabaseMaintenance","No default provider set.") + "\r\n";
            if (!isCheck)
            {
                if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
                {
                    command = "SELECT ProvNum FROM provider WHERE IsHidden=0 ORDER BY itemorder";
                }
                else
                {
                    //MySQL
                    command = "SELECT provnum FROM provider WHERE IsHidden=0 ORDER BY itemorder LIMIT 1";
                } 
                table = Db.getTable(command);
                command = "UPDATE preference SET valuestring = '" + table.Rows[0][0].ToString() + "' WHERE prefname = 'PracticeDefaultProv'";
                Db.nonQ(command);
                log += "  " + Lans.g("FormDatabaseMaintenance","Fixed.") + "\r\n";
            }
             
        } 
        return log;
    }

    public static String procButtonItemsDeleteWithInvalidAutoCode(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM procbuttonitem WHERE CodeNum=0 AND NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT * FROM autocode WHERE autocode.AutoCodeNum=procbuttonitem.AutoCodeNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ProcButtonItems found with invalid autocode: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM procbuttonitem WHERE CodeNum=0 AND NOT EXISTS(\r\n" + 
            "\t\t\t\t\tSELECT * FROM autocode WHERE autocode.AutoCodeNum=procbuttonitem.AutoCodeNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0)
            {
                Signalods.setInvalid(InvalidType.ProcButtons);
            }
             
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","ProcButtonItems deleted due to invalid autocode: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String procedurecodeCategoryNotSet(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM procedurecode WHERE procedurecode.ProcCat=0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                if (DefC.getShort()[((Enum)DefCat.ProcCodeCats).ordinal()].Length == 0)
                {
                    log += Lans.g("FormDatabaseMaintenance","Procedure codes with no categories found but cannot be fixed because there are no visible proc code categories.") + "\r\n";
                    return log;
                }
                 
                log += Lans.g("FormDatabaseMaintenance","Procedure codes with no category found") + ": " + numFound + "\r\n";
            }
             
        }
        else
        {
            //fix
            if (DefC.getShort()[((Enum)DefCat.ProcCodeCats).ordinal()].Length == 0)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedure codes with no categories cannot be fixed because there are no visible proc code categories.") + "\r\n";
                return log;
            }
             
            command = "UPDATE procedurecode SET procedurecode.ProcCat=" + POut.Long(DefC.getShort()[((Enum)DefCat.ProcCodeCats).ordinal()][0].DefNum) + " WHERE procedurecode.ProcCat=0";
            long numberfixed = Db.nonQ(command);
            if (numberfixed > 0)
            {
                Signalods.setInvalid(InvalidType.ProcCodes);
            }
             
            if (numberfixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedure codes with no category fixed") + ": " + numberfixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String procedurelogAttachedToApptWithProcStatusDeleted(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM procedurelog " + "WHERE ProcStatus=6 AND (AptNum!=0 OR PlannedAptNum!=0)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Deleted procedures still attached to appointments: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE procedurelog SET AptNum=0,PlannedAptNum=0 " + "WHERE ProcStatus=6 " + "AND (AptNum!=0 OR PlannedAptNum!=0)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Deleted procedures detached from appointments: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String procedurelogAttachedToWrongAppts(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                command = "SELECT COUNT(*) FROM procedurelog p " + "WHERE (SELECT COUNT(*) FROM appointment a WHERE p.AptNum=a.AptNum AND p.PatNum!=a.PatNum AND ROWNUM<=1)>0";
            }
            else
            {
                command = "SELECT COUNT(*) FROM appointment,procedurelog " + "WHERE procedurelog.AptNum=appointment.AptNum AND procedurelog.PatNum != appointment.PatNum";
            } 
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedures attached to appointments with incorrect patient: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                command = "UPDATE procedurelog p SET p.AptNum=0 " + "WHERE (SELECT COUNT(*) FROM appointment a WHERE p.AptNum=a.AptNum AND p.PatNum!=a.PatNum AND ROWNUM<=1)>0";
            }
            else
            {
                command = "UPDATE appointment,procedurelog SET procedurelog.AptNum=0 " + "WHERE procedurelog.AptNum=appointment.AptNum AND procedurelog.PatNum != appointment.PatNum";
            } 
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedures detached from appointments: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String procedurelogAttachedToWrongApptDate(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                command = "SELECT COUNT(*) FROM procedurelog p\r\n" + 
                "\t\t\t\t\t\tWHERE p.ProcStatus=2 AND \r\n" + 
                "\t\t\t\t\t\t(SELECT COUNT(*) FROM appointment a WHERE a.AptNum=p.AptNum AND TO_DATE(p.ProcDate)!=TO_DATE(a.AptDateTime) AND ROWNUM<=1)>0";
            }
            else
            {
                command = "SELECT COUNT(*) FROM procedurelog,appointment\r\n" + 
                "\t\t\t\t\t\tWHERE procedurelog.AptNum = appointment.AptNum\r\n" + 
                "\t\t\t\t\t\tAND DATE(procedurelog.ProcDate) != DATE(appointment.AptDateTime)\r\n" + 
                "\t\t\t\t\t\tAND procedurelog.ProcStatus = 2";
            } 
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedures which are attached to appointments with mismatched dates: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                command = "UPDATE procedurelog p\r\n" + 
                "\t\t\t\t\t\tSET p.AptNum=0\r\n" + 
                "\t\t\t\t\t\tWHERE p.ProcStatus=2 AND \r\n" + 
                "\t\t\t\t\t\t(SELECT COUNT(*) FROM appointment a WHERE a.AptNum=p.AptNum AND TO_DATE(p.ProcDate)!=TO_DATE(a.AptDateTime) AND ROWNUM<=1)>0";
            }
            else
            {
                command = "UPDATE procedurelog,appointment\r\n" + 
                "\t\t\t\t\t\tSET procedurelog.AptNum=0\r\n" + 
                "\t\t\t\t\t\tWHERE procedurelog.AptNum = appointment.AptNum\r\n" + 
                "\t\t\t\t\t\tAND DATE(procedurelog.ProcDate) != DATE(appointment.AptDateTime)\r\n" + 
                "\t\t\t\t\t\tAND procedurelog.ProcStatus = 2";
            } 
            //only detach completed procs
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedures detached from appointments due to mismatched dates: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String procedurelogBaseUnitsZero(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            //zero--------------------------------------------------------------------------------------
            command = "SELECT COUNT(*) FROM procedurelog \r\n" + 
            "\t\t\t\t\tWHERE baseunits != (SELECT procedurecode.BaseUnits FROM procedurecode WHERE procedurecode.CodeNum=procedurelog.CodeNum)\r\n" + 
            "\t\t\t\t\tAND baseunits = 0";
            //we do not want to change this automatically.  Do not fix these!
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedure BaseUnits are zero and are not matching procedurecode BaseUnits: ") + numFound + "\r\n";
            }
             
            //not zero----------------------------------------------------------------------------------
            command = "SELECT COUNT(*)\r\n" + 
            "\t\t\t\t\tFROM procedurelog\r\n" + 
            "\t\t\t\t\tWHERE BaseUnits!=0\r\n" + 
            "\t\t\t\t\tAND (SELECT procedurecode.BaseUnits FROM procedurecode WHERE procedurecode.CodeNum=procedurelog.CodeNum)=0";
            //very safe to change them back to zero.
            numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedure BaseUnits not zero, but procedurecode BaseUnits are zero: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //first situation: don't fix.
            //second situation:
            //Writing the query this way allows it to work with Oracle.
            command = "UPDATE procedurelog\r\n" + 
            "\t\t\t\t\tSET BaseUnits=0\r\n" + 
            "\t\t\t\t\tWHERE BaseUnits!=0 \r\n" + 
            "\t\t\t\t\tAND (SELECT procedurecode.BaseUnits FROM procedurecode WHERE procedurecode.CodeNum=procedurelog.CodeNum)=0";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedure BaseUnits set to zero because procedurecode BaseUnits are zero: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String procedurelogCodeNumInvalid(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM procedurelog WHERE NOT EXISTS (SELECT * FROM procedurecode WHERE procedurecode.CodeNum=procedurelog.CodeNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedures found with invalid CodeNum") + ": " + numFound + "\r\n";
            }
             
        }
        else
        {
            long badCodeNum = 0;
            if (!ProcedureCodes.isValidCode("~BAD~"))
            {
                ProcedureCode badCode = new ProcedureCode();
                badCode.ProcCode = "~BAD~";
                badCode.Descript = "Invalid procedure";
                badCode.AbbrDesc = "Invalid procedure";
                badCode.ProcCat = DefC.getByExactNameNeverZero(DefCat.ProcCodeCats,"Never Used");
                ProcedureCodes.insert(badCode);
                badCodeNum = badCode.CodeNum;
            }
            else
            {
                badCodeNum = ProcedureCodes.getCodeNum("~BAD~");
            } 
            command = "UPDATE procedurelog SET CodeNum=" + POut.long(badCodeNum) + " WHERE NOT EXISTS (SELECT * FROM procedurecode WHERE procedurecode.CodeNum=procedurelog.CodeNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedures fixed with invalid CodeNum") + ": " + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String procedurelogLabAttachedToDeletedProc(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM procedurelog " + "WHERE ProcStatus=2 AND ProcNumLab IN(SELECT ProcNum FROM procedurelog WHERE ProcStatus=6)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Completed procedure labs attached to deleted procedures: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "SELECT patient.PatNum,patient.FName,patient.LName FROM procedurelog " + "LEFT JOIN patient ON procedurelog.PatNum=patient.PatNum " + "WHERE ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " AND ProcNumLab IN(SELECT ProcNum FROM procedurelog WHERE ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.D).ordinal()) + ") ";
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
            {
                command += "GROUP BY patient.PatNum";
            }
            else
            {
                //Oracle
                command += "GROUP BY patient.PatNum,patient.FName,patient.LName";
            } 
            table = Db.getTable(command);
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
            {
                command = "UPDATE procedurelog plab,procedurelog p " + "SET plab.ProcNumLab=0 " + "WHERE plab.ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " AND plab.ProcNumLab=p.ProcNum AND p.ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.D).ordinal());
            }
            else
            {
                //Oracle
                command = "UPDATE procedurelog plab SET plab.ProcNumLab=0 " + "WHERE plab.ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "AND plab.ProcNumLab IN (SELECT p.ProcNum FROM procedurelog p WHERE p.ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.D).ordinal()) + ") ";
            } 
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Patients with completed lab procedures detached from deleted procedures: ") + numberFixed.ToString() + "\r\n";
                String patNames = "";
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    if (i > 15)
                    {
                        break;
                    }
                     
                    if (i > 0)
                    {
                        patNames += ", ";
                    }
                     
                    patNames += table.Rows[i]["PatNum"].ToString() + ":" + table.Rows[i]["FName"].ToString() + " " + table.Rows[i]["LName"].ToString();
                }
                log += Lans.g("FormDatabaseMaintenance","Including: ") + patNames + "\r\n";
            }
             
        } 
        return log;
    }

    public static String procedurelogProvNumMissing(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM procedurelog WHERE ProvNum=0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedures with missing provnums found: ") + numFound + "\r\n";
            }
             
        }
        else
        {
        } 
        return log;
    }

    //Create a new provider and attach procedures.
    //command="UPDATE procedurelog SET ProvNum="+PrefC.GetString(PrefName.PracticeDefaultProv)+" WHERE ProvNum=0";
    //long numberFixed=Db.NonQ(command);
    //if(numberFixed>0 || verbose) {
    //  log+=Lans.g("FormDatabaseMaintenance","Procedures with missing provnums fixed: ")+numberFixed.ToString()+"\r\n";
    //}
    public static String procedurelogToothNums(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT procnum,toothnum,patnum FROM procedurelog";
        table = Db.getTable(command);
        Patient Lim = null;
        String toothNum = new String();
        int numberFixed = 0;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            toothNum = table.Rows[i][1].ToString();
            if (StringSupport.equals(toothNum, ""))
                continue;
             
            if (Tooth.isValidDB(toothNum))
            {
                continue;
            }
             
            if (verbose)
            {
                Lim = Patients.GetLim(Convert.ToInt32(table.Rows[i][2].ToString()));
            }
             
            if (String.CompareOrdinal(toothNum, "a") >= 0 && String.CompareOrdinal(toothNum, "t") <= 0)
            {
                if (!isCheck)
                {
                    command = "UPDATE procedurelog SET ToothNum = '" + toothNum.ToUpper() + "' WHERE ProcNum = " + table.Rows[i][0].ToString();
                    Db.nonQ(command);
                }
                 
                if (verbose)
                {
                    log += Lim.getNameLF() + " " + toothNum + " - " + toothNum.ToUpper() + "\r\n";
                }
                 
                numberFixed++;
            }
            else
            {
                if (!isCheck)
                {
                    command = "UPDATE procedurelog SET ToothNum = '1' WHERE ProcNum = " + table.Rows[i][0].ToString();
                    Db.nonQ(command);
                }
                 
                if (verbose)
                {
                    log += Lim.getNameLF() + " " + toothNum + " - 1\r\n";
                }
                 
                numberFixed++;
            } 
        }
        if (numberFixed != 0 || verbose)
        {
            log += Lans.g("FormDatabaseMaintenance","Check for invalid tooth numbers complete.  Records checked: ") + table.Rows.Count.ToString() + ". " + Lans.g("FormDatabaseMaintenance","Records invalid: ") + numberFixed.ToString() + "\r\n";
        }
         
        return log;
    }

    public static String procedurelogTpAttachedToClaim(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT procedurelog.ProcNum,claim.ClaimNum,claim.DateService,patient.PatNum,patient.LName,patient.FName,procedurecode.ProcCode " + "FROM procedurelog,claim,claimproc,patient,procedurecode " + "WHERE procedurelog.ProcNum=claimproc.ProcNum " + "AND claim.ClaimNum=claimproc.ClaimNum " + "AND claim.PatNum=patient.PatNum " + "AND procedurelog.CodeNum=procedurecode.CodeNum " + "AND procedurelog.ProcStatus!=" + POut.Long(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "AND (claim.ClaimStatus='W' OR claim.ClaimStatus='S' OR claim.ClaimStatus='R') " + "AND (claim.ClaimType='P' OR claim.ClaimType='S' OR claim.ClaimType='Other')";
        //procedure not complete
        //waiting, sent, or received
        //pri, sec, or other.  Eliminates preauths.
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedures attached to claims, but with status of TP: ") + table.Rows.Count + "\r\n";
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    log += Lans.g("FormDatabaseMaintenance","Patient") + " " + table.Rows[i]["FName"].ToString() + " " + table.Rows[i]["LName"].ToString() + " #" + table.Rows[i]["PatNum"].ToString() + ", for claim service date " + PIn.Date(table.Rows[i]["DateService"].ToString()).ToShortDateString() + ", procedure code " + table.Rows[i]["ProcCode"].ToString() + "\r\n";
                }
            }
             
        }
        else
        {
        } 
        return log;
    }

    //Detach claimproc(s) from claim.
    //for(int i=0;i<table.Rows.Count;i++) {
    //  command="UPDATE procedurelog SET ProcStatus=2 WHERE ProcNum="+table.Rows[i][0].ToString();
    //  Db.NonQ(command);
    //}
    //int numberFixed=table.Rows.Count;
    //if(numberFixed>0 || verbose) {
    //  log+=Lans.g("FormDatabaseMaintenance","Procedures attached to claims, but with status of TP.  Status changed back to C: ")
    //    +numberFixed.ToString()+"\r\n";
    //}
    public static String procedurelogTpAttachedToCompleteLabFeesCanada(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        if (!CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            return "";
        }
         
        //Canadian. en-CA or fr-CA
        String log = "";
        command = "SELECT pc.ProcCode ProcCode,pclab.ProcCode ProcCodeLab,proc.PatNum,proc.ProcDate " + "FROM procedurelog proc " + "INNER JOIN procedurecode pc ON pc.CodeNum=proc.CodeNum " + "INNER JOIN procedurelog lab ON proc.ProcNum=lab.ProcNumLab AND lab.ProcStatus=" + POut.Long(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "INNER JOIN procedurecode pclab ON pclab.CodeNum=lab.CodeNum " + "WHERE proc.ProcStatus=" + POut.Long(((Enum)OpenDentBusiness.ProcStat.TP).ordinal());
        table = Db.getTable(command);
        if (isCheck)
        {
            if (table.Rows.Count > 0 || verbose)
            {
                for (int i = 0;i < table.Rows.Count;i++)
                {
                    log += Lans.g("FormDatabaseMaintenance","Completed lab fee") + " " + table.Rows[i]["ProcCodeLab"].ToString() + " " + Lans.g("FormDatabaseMaintenance","is attached to TP procedure") + " " + table.Rows[i]["ProcCode"].ToString() + " " + Lans.g("FormDatabaseMaintenance","on date") + " " + PIn.Date(table.Rows[i]["ProcDate"].ToString()).ToShortDateString() + ". " + Lans.g("FormDatabaseMaintenance","PatNum: ") + table.Rows[i]["PatNum"].ToString() + " " + Lans.g("FormDatabaseMaintenance","Fix manually from within the Chart module.") + "\r\n";
                }
            }
             
        }
        else
        {
        } 
        return log;
    }

    //User must fix manually.
    public static String procedurelogUnitQtyZero(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM procedurelog WHERE UnitQty=0";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedures with UnitQty=0 found: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE procedurelog        \r\n" + 
            "\t\t\t\t\tSET UnitQty=1\r\n" + 
            "\t\t\t\t\tWHERE UnitQty=0";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Procedures changed from UnitQty=0 to UnitQty=1: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String providerHiddenWithClaimPayments(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        command = "SELECT MAX(claimproc.ProcDate) ProcDate,provider.ProvNum\r\n" + 
        "\t\t\t\tFROM claimproc,provider\r\n" + 
        "\t\t\t\tWHERE claimproc.ProvNum=provider.ProvNum\r\n" + 
        "\t\t\t\tAND provider.IsHidden=1\r\n" + 
        "\t\t\t\tAND claimproc.InsPayAmt>0\r\n" + 
        "\t\t\t\tGROUP BY provider.ProvNum";
        table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Hidden providers checked for claim payments.") + "\r\n";
            }
             
            return log;
        }
         
        if (isCheck)
        {
            Provider prov;
            for (int i = 0;i < table.Rows.Count;i++)
            {
                prov = Providers.GetProv(PIn.Long(table.Rows[i][1].ToString()));
                log += Lans.g("FormDatabaseMaintenance","Warning!  Hidden provider ") + " " + prov.Abbr + " " + Lans.g("FormDatabaseMaintenance","has claim payments entered as recently as ") + PIn.Date(table.Rows[i][0].ToString()).ToShortDateString() + Lans.g("FormDatabaseMaintenance",".  This data will be missing on income reports.") + "\r\n";
            }
        }
        else
        {
        } 
        return log;
    }

    //No fix implemented.
    public static String providerWithInvalidFeeSched(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM provider WHERE FeeSched NOT IN (SELECT FeeSchedNum FROM feesched)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Providers found with invalid FeeSched: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE provider SET FeeSched=" + POut.Long(FeeSchedC.getListShort()[0].FeeSchedNum) + " " + "WHERE FeeSched NOT IN (SELECT FeeSchedNum FROM feesched)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Providers whose FeeSched has been changed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String recallDuplicatesWarn(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "";
        }
         
        String log = "";
        if (RecallTypes.getPerioType() < 1 || RecallTypes.getProphyType() < 1)
        {
            log += Lans.g("FormDatabaseMaintenance","Warning!  Recall types not set up properly.  There must be at least one of each type: perio and prophy.") + "\r\n";
            return log;
        }
         
        command = "SELECT FName,LName,COUNT(*) countDups FROM patient LEFT JOIN recall ON recall.PatNum=patient.PatNum " + "AND (recall.RecallTypeNum=" + POut.long(RecallTypes.getPerioType()) + " " + "OR recall.RecallTypeNum=" + POut.long(RecallTypes.getProphyType()) + ") " + "GROUP BY FName,LName,patient.PatNum HAVING countDups>1";
        table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            if (verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Recalls checked for duplicates.") + "\r\n";
            }
             
            return log;
        }
         
        if (isCheck)
        {
            String patNames = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (i > 15)
                {
                    break;
                }
                 
                if (i > 0)
                {
                    patNames += ", ";
                }
                 
                patNames += table.Rows[i][0].ToString() + " " + table.Rows[i][1].ToString();
            }
            log += Lans.g("FormDatabaseMaintenance","Warning!  Number of patients with duplicate recalls: ") + table.Rows.Count.ToString() + ".  " + Lans.g("FormDatabaseMaintenance","including: ") + patNames + "\r\n";
        }
        else
        {
        } 
        return log;
    }

    //No fix implemented.
    public static String recallTriggerDeleteBadCodeNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM recalltrigger WHERE NOT EXISTS (SELECT * FROM procedurecode WHERE procedurecode.CodeNum=recalltrigger.CodeNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Recall triggers found with bad codenum: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM recalltrigger\r\n" + 
            "\t\t\t\t\tWHERE NOT EXISTS (SELECT * FROM procedurecode WHERE procedurecode.CodeNum=recalltrigger.CodeNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0)
            {
                Signalods.setInvalid(InvalidType.RecallTypes);
            }
             
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Recall triggers deleted due to bad codenum: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String refAttachDeleteWithInvalidReferral(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM refattach WHERE ReferralNum NOT IN (SELECT ReferralNum FROM referral)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Ref attachments found with invalid referrals: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //fix
            command = "DELETE FROM refattach WHERE ReferralNum NOT IN (SELECT ReferralNum FROM referral)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Ref attachments with invalid referrals deleted: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String schedulesDeleteHiddenProviders(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM provider WHERE IsHidden=1 AND ProvNum IN (SELECT ProvNum FROM schedule WHERE SchedDate > " + DbHelper.now() + " GROUP BY ProvNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Hidden providers found on future schedules: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //Fix
            command = "SELECT ProvNum FROM provider WHERE IsHidden=1 AND ProvNum IN (SELECT ProvNum FROM schedule WHERE SchedDate > " + DbHelper.now() + " GROUP BY ProvNum)";
            table = Db.getTable(command);
            List<long> provNums = new List<long>();
            for (int i = 0;i < table.Rows.Count;i++)
            {
                provNums.Add(PIn.Long(table.Rows[i]["ProvNum"].ToString()));
            }
            Providers.RemoveProvsFromFutureSchedule(provNums);
            //Deletes future schedules for providers.
            if (provNums.Count > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Hidden providers found on future schedules fixed: ") + provNums.Count.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String schedulesDeleteShort(boolean verbose, boolean isCheck) throws Exception {
        //No need to check RemotingRole; no call to db.
        String log = "";
        if (isCheck)
        {
            int numFound = 0;
            Schedule[] schedList = Schedules.refreshAll();
            for (int i = 0;i < schedList.Length;i++)
            {
                if (schedList[i].Status != SchedStatus.Open)
                {
                    continue;
                }
                 
                //closed and holiday statuses do not use starttime and stoptime
                if (schedList[i].StopTime - schedList[i].StartTime < new TimeSpan(0, 5, 0))
                {
                    //Schedule items less than five minutes won't show up.
                    //But we don't want to count provider notes, employee notes, or pratice notes.
                    if (StringSupport.equals(schedList[i].Note, ""))
                    {
                        numFound++;
                    }
                     
                }
                 
            }
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Schedule blocks invalid: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            int numberFixed = 0;
            Schedule[] schedList = Schedules.refreshAll();
            for (int i = 0;i < schedList.Length;i++)
            {
                if (schedList[i].Status != SchedStatus.Open)
                {
                    continue;
                }
                 
                //closed and holiday statuses do not use starttime and stoptime
                if (schedList[i].StopTime - schedList[i].StartTime < new TimeSpan(0, 5, 0))
                {
                    //Schedule items less than five minutes won't show up. Remove them.
                    //But we don't want to remove provider notes, employee notes, or pratice notes.
                    if (StringSupport.equals(schedList[i].Note, ""))
                    {
                        Schedules.Delete(schedList[i]);
                        numberFixed++;
                    }
                     
                }
                 
            }
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Schedule blocks fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String schedulesDeleteProvClosed(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM schedule WHERE SchedType=1 AND Status=1";
            //type=prov,status=closed
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Schedules found which are causing printing issues: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM schedule WHERE SchedType=1 AND Status=1";
            //type=prov,status=closed
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Schedules deleted that were causing printing issues: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String signalInFuture(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM signalod WHERE SigDateTime > NOW() OR AckTime > NOW()";
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                String nowDateTime = POut.dateT(OpenDentBusiness.MiscData.getNowDateTime());
                command = "SELECT COUNT(*) FROM signalod WHERE SigDateTime > " + nowDateTime + " OR AckTime > " + nowDateTime;
            }
             
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Signalod entries with future time: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM signalod WHERE SigDateTime > NOW() OR AckTime > NOW()";
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                String nowDateTime = POut.dateT(OpenDentBusiness.MiscData.getNowDateTime());
                command = "DELETE FROM signalod WHERE SigDateTime > " + nowDateTime + " OR AckTime > " + nowDateTime;
            }
             
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Signalod entries deleted: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String statementDateRangeMax(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM statement WHERE DateRangeTo='9999-12-31'";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Statement DateRangeTo max found: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE statement SET DateRangeTo='2200-01-01' WHERE DateRangeTo='9999-12-31'";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Statement DateRangeTo max fixed: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String taskSubscriptionsInvalid(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM tasksubscription " + "WHERE NOT EXISTS(SELECT * FROM tasklist WHERE tasksubscription.TaskListNum=tasklist.TaskListNum)";
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Task subscriptions invalid: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "DELETE FROM tasksubscription " + "WHERE NOT EXISTS(SELECT * FROM tasklist WHERE tasksubscription.TaskListNum=tasklist.TaskListNum)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Task subscriptions deleted: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static String timeCardRuleEmployeeNumInvalid(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT COUNT(*) FROM timecardrule " + "WHERE timecardrule.EmployeeNum!=0 " + "AND timecardrule.EmployeeNum NOT IN(SELECT employee.EmployeeNum FROM employee)";
            //0 is all employees, so it is a 'valid' employee number
            int numFound = PIn.int(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Timecard rules found with invalid employee number: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            command = "UPDATE timecardrule " + "SET timecardrule.EmployeeNum=0 " + "WHERE timecardrule.EmployeeNum!=0 " + "AND timecardrule.EmployeeNum NOT IN(SELECT employee.EmployeeNum FROM employee)";
            //don't set to 0 if already 0
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Timecard rules applied to All Employees due to invalid employee number: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    /**
    * Only one user of a given UserName may be unhidden at a time. Warn the user and instruct them to hide extras.
    */
    public static String userodDuplicateUser(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        //check and fix are currently identical
        if (isCheck)
        {
            //Give them a warning to hide all but one of these users.
            command = "SELECT UserName FROM userod WHERE IsHidden=0 GROUP BY UserName HAVING Count(*)>1;";
            DataTable table = Db.getTable(command);
            for (int i = 0;i < table.Rows.Count;i++)
            {
                log += Lans.g("FormDatabaseMaintenance","Warning! User ") + table.Rows[i]["UserName"].ToString() + " has duplicates. Please go to Setup | Security and hide all but one of these users.\r\n";
            }
        }
        else
        {
            //Give them a warning to hide all but one of these users.
            command = "SELECT UserName FROM userod WHERE IsHidden=0 GROUP BY UserName HAVING Count(*)>1;";
            DataTable table = Db.getTable(command);
            for (int i = 0;i < table.Rows.Count;i++)
            {
                log += Lans.g("FormDatabaseMaintenance","Warning! User ") + table.Rows[i]["UserName"].ToString() + " has duplicates. Please go to Setup | Security and hide all but one of these users.\r\n";
            }
        } 
        return log;
    }

    public static String userodInvalidClinicNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT Count(*) FROM userod WHERE ClinicNum<>0 AND ClinicNum NOT IN (SELECT ClinicNum FROM clinic)";
            long numFound = PIn.long(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Users found with invalid ClinicNum: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //Fix
            command = "UPDATE userod SET ClinicNum=0 WHERE ClinicNum<>0 AND ClinicNum NOT IN (SELECT ClinicNum FROM clinic)";
            long numberFixed = Db.nonQ(command);
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Users fixed with invalid ClinicNum: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    /**
    * userod has an invalid FK to usergroup
    */
    public static String userodInvalidUserGroupNum(boolean verbose, boolean isCheck) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), verbose, isCheck);
        }
         
        String log = "";
        if (isCheck)
        {
            command = "SELECT Count(*) FROM userod WHERE UserGroupNum NOT IN (SELECT UserGroupNum FROM usergroup) ";
            long numFound = PIn.long(Db.getCount(command));
            if (numFound > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Users found with invalid UserGroupNum: ") + numFound + "\r\n";
            }
             
        }
        else
        {
            //isFix
            command = "SELECT * FROM userod WHERE UserGroupNum NOT IN (SELECT UserGroupNum FROM usergroup) ";
            table = Db.getTable(command);
            long userNum = new long();
            String userName = new String();
            long userGroupNum = new long();
            long numberFixed = 0;
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //Create a usergroup with the same name as the userod+"Group"
                userNum = PIn.Long(table.Rows[i]["UserNum"].ToString());
                userName = PIn.String(table.Rows[i]["UserName"].ToString());
                command = "INSERT INTO usergroup (Description) VALUES('" + POut.string(userName + " Group") + "')";
                userGroupNum = Db.nonQ(command,true);
                command = "UPDATE userod SET UserGroupNum=" + POut.long(userGroupNum) + " WHERE UserNum=" + POut.long(userNum);
                Db.nonQ(command);
                numberFixed++;
            }
            if (numberFixed > 0 || verbose)
            {
                log += Lans.g("FormDatabaseMaintenance","Users fixed with invalid UserGroupNum: ") + numberFixed.ToString() + "\r\n";
            }
             
        } 
        return log;
    }

    public static List<String> getDatabaseNames() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        List<String> retVal = new List<String>();
        command = "SHOW DATABASES";
        //if this next step fails, table will simply have 0 rows
        DataTable table = Db.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(table.Rows[i][0].ToString());
        }
        return retVal;
    }

    /**
    * Will return empty string if no problems.
    */
    public static String getDuplicateClaimProcs() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        String retVal = "";
        command = "SELECT LName,FName,patient.PatNum,ClaimNum,FeeBilled,Status,ProcNum,ProcDate,ClaimProcNum,InsPayAmt,LineNumber, COUNT(*) cnt\r\n" + 
        "FROM claimproc\r\n" + 
        "LEFT JOIN patient ON patient.PatNum=claimproc.PatNum\r\n" + 
        "WHERE ClaimNum > 0\r\n" + 
        "AND ProcNum>0\r\n" + 
        "AND Status!=4/*exclude supplemental*/\r\n" + 
        "GROUP BY LName,FName,patient.PatNum,ClaimNum,FeeBilled,Status,ProcNum,ProcDate,ClaimProcNum,InsPayAmt,LineNumber \r\n" + 
        "HAVING cnt>1";
        table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return "";
        }
         
        retVal += "Duplicate claim payments found:\r\n";
        DateTime date = new DateTime();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (i > 0)
            {
                //check for duplicate rows.  We only want to report each claim once.
                if (table.Rows[i]["ClaimNum"].ToString() == table.Rows[i - 1]["ClaimNum"].ToString())
                {
                    continue;
                }
                 
            }
             
            date = PIn.Date(table.Rows[i]["ProcDate"].ToString());
            retVal += table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString() + " " + "(" + table.Rows[i]["PatNum"].ToString() + "), " + date.ToShortDateString() + "\r\n";
        }
        retVal += "\r\n";
        return retVal;
    }

    /**
    * Will return empty string if no problems.
    */
    public static String getDuplicateSupplementalPayments() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        String retVal = "";
        command = "SELECT LName,FName,patient.PatNum,ClaimNum,FeeBilled,Status,ProcNum,ProcDate,ClaimProcNum,InsPayAmt,LineNumber, COUNT(*) cnt\r\n" + 
        "FROM claimproc\r\n" + 
        "LEFT JOIN patient ON patient.PatNum=claimproc.PatNum\r\n" + 
        "WHERE ClaimNum > 0\r\n" + 
        "AND ProcNum>0\r\n" + 
        "AND Status=4/*only supplemental*/\r\n" + 
        "GROUP BY LName,FName,patient.PatNum,ClaimNum,FeeBilled,Status,ProcNum,ProcDate,ClaimProcNum,InsPayAmt,LineNumber\r\n" + 
        "HAVING cnt>1";
        table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return "";
        }
         
        retVal += "Duplicate supplemental payments found (may be false positives):\r\n";
        DateTime date = new DateTime();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (i > 0)
            {
                if (table.Rows[i]["ClaimNum"].ToString() == table.Rows[i - 1]["ClaimNum"].ToString())
                {
                    continue;
                }
                 
            }
             
            date = PIn.Date(table.Rows[i]["ProcDate"].ToString());
            retVal += table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString() + " " + "(" + table.Rows[i]["PatNum"].ToString() + "), " + date.ToShortDateString() + "\r\n";
        }
        retVal += "\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String getMissingClaimProcs(String olddb) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), olddb);
        }
         
        String retVal = "";
        command = "SELECT LName,FName,patient.PatNum,ClaimNum,FeeBilled,Status,ProcNum,ProcDate,ClaimProcNum,InsPayAmt,LineNumber " + "FROM " + olddb + ".claimproc " + "LEFT JOIN " + olddb + ".patient ON " + olddb + ".patient.PatNum=" + olddb + ".claimproc.PatNum " + "WHERE NOT EXISTS(SELECT * FROM claimproc WHERE claimproc.ClaimProcNum=" + olddb + ".claimproc.ClaimProcNum) " + "AND ClaimNum > 0 AND ProcNum>0";
        table = Db.getTable(command);
        double insPayAmt = new double();
        double feeBilled = new double();
        int count = 0;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            insPayAmt = PIn.Double(table.Rows[i]["InsPayAmt"].ToString());
            feeBilled = PIn.Double(table.Rows[i]["FeeBilled"].ToString());
            command = "SELECT COUNT(*) FROM " + olddb + ".claimproc " + "WHERE ClaimNum= " + table.Rows[i]["ClaimNum"].ToString() + " " + "AND ProcNum= " + table.Rows[i]["ProcNum"].ToString() + " " + "AND Status= " + table.Rows[i]["Status"].ToString() + " " + "AND InsPayAmt= '" + POut.double(insPayAmt) + "' " + "AND FeeBilled= '" + POut.double(feeBilled) + "' " + "AND LineNumber= " + table.Rows[i]["LineNumber"].ToString();
            String result = Db.getCount(command);
            if (!StringSupport.equals(result, "1"))
            {
                //only include in result if there are duplicates in old db.
                count++;
            }
             
        }
        command = "SELECT ClaimPaymentNum " + "FROM " + olddb + ".claimpayment " + "WHERE NOT EXISTS(SELECT * FROM claimpayment WHERE claimpayment.ClaimPaymentNum=" + olddb + ".claimpayment.ClaimPaymentNum) ";
        DataTable table2 = Db.getTable(command);
        if (count == 0 && table2.Rows.Count == 0)
        {
            return "";
        }
         
        retVal += "Missing claim payments found: " + count.ToString() + "\r\n";
        retVal += "Missing claim checks found (probably false positives): " + table2.Rows.Count.ToString() + "\r\n";
        return retVal;
    }

    //public static bool DatabaseIsOlderThanMarchSeventeenth(string olddb){
    //  if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    //    return Meth.GetBool(MethodBase.GetCurrentMethod(),olddb);
    //  }
    //  command="SELECT COUNT(*) FROM "+olddb+".claimproc WHERE DateEntry > '2010-03-16'";
    //  if(Db.GetCount(command)=="0"){
    //    return true;
    //  }
    //  return false;
    //}
    /**
    * 
    */
    public static String fixClaimProcDeleteDuplicates() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        String log = "";
        return log;
    }

    //command=@"SELECT LName,FName,patient.PatNum,ClaimNum,FeeBilled,Status,ProcNum,ProcDate,ClaimProcNum,InsPayAmt,LineNumber, COUNT(*) cnt
    //	FROM claimproc
    //	LEFT JOIN patient ON patient.PatNum=claimproc.PatNum
    //	WHERE ClaimNum > 0
    //	AND ProcNum>0
    //	AND Status!=4/*exclude supplemental*/
    //	GROUP BY ClaimNum,ProcNum,Status,InsPayAmt,FeeBilled,LineNumber
    //	HAVING cnt>1";
    //table=Db.GetTable(command);
    //long numberFixed=0;
    //double insPayAmt;
    //double feeBilled;
    //for(int i=0;i<table.Rows.Count;i++) {
    //  insPayAmt=PIn.Double(table.Rows[i]["InsPayAmt"].ToString());
    //  feeBilled=PIn.Double(table.Rows[i]["FeeBilled"].ToString());
    //  command="DELETE FROM claimproc "
    //    +"WHERE ClaimNum= "+table.Rows[i]["ClaimNum"].ToString()+" "
    //    +"AND ProcNum= "+table.Rows[i]["ProcNum"].ToString()+" "
    //    +"AND Status= "+table.Rows[i]["Status"].ToString()+" "
    //    +"AND InsPayAmt= '"+POut.Double(insPayAmt)+"' "
    //    +"AND FeeBilled= '"+POut.Double(feeBilled)+"' "
    //    +"AND LineNumber= "+table.Rows[i]["LineNumber"].ToString()+" "
    //    +"AND ClaimProcNum != "+table.Rows[i]["ClaimProcNum"].ToString();
    //  numberFixed+=Db.NonQ(command);
    //}
    //log+="Claimprocs deleted due duplicate entries: "+numberFixed.ToString()+".\r\n";
    /**
    * 
    */
    public static String fixMissingClaimProcs(String olddb) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        String log = "";
        return log;
    }

    //command="SELECT LName,FName,patient.PatNum,ClaimNum,FeeBilled,Status,ProcNum,ProcDate,ClaimProcNum,InsPayAmt,LineNumber "
    //  +"FROM "+olddb+".claimproc "
    //  +"LEFT JOIN "+olddb+".patient ON "+olddb+".patient.PatNum="+olddb+".claimproc.PatNum "
    //  +"WHERE NOT EXISTS(SELECT * FROM claimproc WHERE claimproc.ClaimProcNum="+olddb+".claimproc.ClaimProcNum) "
    //  +"AND ClaimNum > 0 AND ProcNum>0";
    //table=Db.GetTable(command);
    //long numberFixed=0;
    //command="SELECT ValueString FROM "+olddb+".preference WHERE PrefName = 'DataBaseVersion'";
    //string oldVersString=Db.GetScalar(command);
    //Version oldVersion=new Version(oldVersString);
    //if(oldVersion < new Version("6.7.1.0")) {
    //  return "Version of old database is too old to use with the automated tool: "+oldVersString;
    //}
    //double insPayAmt;
    //double feeBilled;
    //for(int i=0;i<table.Rows.Count;i++) {
    //  insPayAmt=PIn.Double(table.Rows[i]["InsPayAmt"].ToString());
    //  feeBilled=PIn.Double(table.Rows[i]["FeeBilled"].ToString());
    //  command="SELECT COUNT(*) FROM "+olddb+".claimproc "
    //    +"WHERE ClaimNum= "+table.Rows[i]["ClaimNum"].ToString()+" "
    //    +"AND ProcNum= "+table.Rows[i]["ProcNum"].ToString()+" "
    //    +"AND Status= "+table.Rows[i]["Status"].ToString()+" "
    //    +"AND InsPayAmt= '"+POut.Double(insPayAmt)+"' "
    //    +"AND FeeBilled= '"+POut.Double(feeBilled)+"' "
    //    +"AND LineNumber= "+table.Rows[i]["LineNumber"].ToString();
    //  string result=Db.GetCount(command);
    //  if(result=="1"){//only include in result if there are duplicates in old db.
    //    continue;
    //  }
    //  command="INSERT INTO claimproc SELECT *";
    //  if(oldVersion < new Version("6.8.1.0")) {
    //    command+=",-1,-1,0";
    //  }
    //  else if(oldVersion < new Version("6.9.1.0")) {
    //    command+=",0";
    //  }
    //  command+=" FROM "+olddb+".claimproc "
    //    +"WHERE "+olddb+".claimproc.ClaimProcNum="+table.Rows[i]["ClaimProcNum"].ToString();
    //  numberFixed+=Db.NonQ(command);
    //}
    //command="SELECT ClaimPaymentNum "
    //  +"FROM "+olddb+".claimpayment "
    //  +"WHERE NOT EXISTS(SELECT * FROM claimpayment WHERE claimpayment.ClaimPaymentNum="+olddb+".claimpayment.ClaimPaymentNum) ";
    //table=Db.GetTable(command);
    //long numberFixed2=0;
    //for(int i=0;i<table.Rows.Count;i++) {
    //  command="INSERT INTO claimpayment SELECT * FROM "+olddb+".claimpayment "
    //    +"WHERE "+olddb+".claimpayment.ClaimPaymentNum="+table.Rows[i]["ClaimPaymentNum"].ToString();
    //  numberFixed2+=Db.NonQ(command);
    //}
    //log+="Missing claimprocs added back: "+numberFixed.ToString()+".\r\n";
    //log+="Missing claimpayments added back: "+numberFixed2.ToString()+".\r\n";
    /**
    * Removes unsupported unicode characters from appointment.ProcDescript, appointment.Note, and patient.AddrNote.  Also removes mysql null character ("\0" or CHAR(0)) from adjustment.AdjNote, payment.PayNote, and definition.ItemName.  These null characters were causing the middle tier deserialization to fail as they are not UTF-16 supported characters.  They are, however, allowed in UTF-8.
    */
    public static void fixSpecialCharacters() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "SELECT * FROM appointment WHERE (ProcDescript REGEXP '[^[:alnum:]^[:space:]^[:punct:]]+') OR (Note REGEXP '[^[:alnum:]^[:space:]^[:punct:]]+')";
        List<Appointment> apts = OpenDentBusiness.Crud.AppointmentCrud.SelectMany(command);
        List<char> specialCharsFound = new List<char>();
        int specialCharCount = 0;
        int intC = 0;
        if (apts.Count != 0)
        {
            for (Object __dummyForeachVar8 : apts)
            {
                Appointment apt = (Appointment)__dummyForeachVar8;
                for (Object __dummyForeachVar6 : apt.Note)
                {
                    char c = (Character)__dummyForeachVar6;
                    intC = (int)c;
                    //31 - 126 are all safe.
                    //"Horizontal Tabulation"
                    //Line Feed
                    if ((intC < 126 && intC > 31) || intC == 9 || intC == 10 || intC == 13)
                    {
                        continue;
                    }
                     
                    //carriage return
                    specialCharCount++;
                    if (specialCharsFound.Contains(c))
                    {
                        continue;
                    }
                     
                    specialCharsFound.Add(c);
                }
                for (Object __dummyForeachVar7 : apt.ProcDescript)
                {
                    char c = (Character)__dummyForeachVar7;
                    //search every character in ProcDescript
                    intC = (int)c;
                    //31 - 126 are all safe.
                    //"Horizontal Tabulation"
                    //Line Feed
                    if ((intC < 126 && intC > 31) || intC == 9 || intC == 10 || intC == 13)
                    {
                        continue;
                    }
                     
                    //carriage return
                    specialCharCount++;
                    if (specialCharsFound.Contains(c))
                    {
                        continue;
                    }
                     
                    specialCharsFound.Add(c);
                }
            }
            for (Object __dummyForeachVar9 : specialCharsFound)
            {
                char c = (Character)__dummyForeachVar9;
                command = "UPDATE appointment SET Note = REPLACE(Note,'" + POut.String(c.ToString()) + "',''), ProcDescript = REPLACE(ProcDescript,'" + POut.String(c.ToString()) + "','')";
                Db.nonQ(command);
            }
        }
         
        command = "SELECT * FROM patient WHERE AddrNote REGEXP '[^[:alnum:]^[:space:]]+'";
        List<Patient> pats = OpenDentBusiness.Crud.PatientCrud.SelectMany(command);
        specialCharsFound = new List<char>();
        specialCharCount = 0;
        intC = 0;
        if (pats.Count > 0)
        {
            for (Object __dummyForeachVar11 : pats)
            {
                Patient pat = (Patient)__dummyForeachVar11;
                for (Object __dummyForeachVar10 : pat.AddrNote)
                {
                    char c = (Character)__dummyForeachVar10;
                    intC = (int)c;
                    //31 - 126 are all safe.
                    //"Horizontal Tabulation"
                    //Line Feed
                    if ((intC < 126 && intC > 31) || intC == 9 || intC == 10 || intC == 13)
                    {
                        continue;
                    }
                     
                    //carriage return
                    specialCharCount++;
                    if (specialCharsFound.Contains(c))
                    {
                        continue;
                    }
                     
                    specialCharsFound.Add(c);
                }
            }
            for (Object __dummyForeachVar12 : specialCharsFound)
            {
                char c = (Character)__dummyForeachVar12;
                command = "UPDATE patient SET AddrNote=REPLACE(AddrNote,'" + POut.String(c.ToString()) + "','')";
                Db.nonQ(command);
            }
        }
         
        command = "UPDATE adjustment SET AdjNote=REPLACE(AdjNote,CHAR(0),'') WHERE AdjNote LIKE '%" + POut.string("\0") + "%'";
        Db.nonQ(command);
        command = "UPDATE payment SET PayNote=REPLACE(PayNote,CHAR(0),'') WHERE PayNote LIKE '%" + POut.string("\0") + "%'";
        Db.nonQ(command);
        command = "UPDATE definition SET ItemName=REPLACE(ItemName,CHAR(0),'') WHERE ItemName LIKE '%" + POut.string("\0") + "%'";
        Db.nonQ(command);
        return ;
    }

    /**
    * Replaces null strings with empty strings and returns the number of rows changed.
    */
    public static long removeNullStrings() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT table_name,column_name \r\n" + 
        "\t\t\t\tFROM information_schema.columns \r\n" + 
        "\t\t\t\tWHERE table_schema=(SELECT DATABASE()) \r\n" + 
        "\t\t\t\tAND (data_type=\'char\' \r\n" + 
        "\t\t\t\t\tOR data_type=\'longtext\' \r\n" + 
        "\t\t\t\t\tOR data_type=\'mediumtext\' \r\n" + 
        "\t\t\t\t\tOR data_type=\'text\' \r\n" + 
        "\t\t\t\t\tOR data_type=\'varchar\') \r\n" + 
        "\t\t\t\tAND is_nullable=\'YES\'";
        DataTable table = Db.getTable(command);
        long changeCount = 0;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            command = "UPDATE " + POut.String(table.Rows[i]["table_name"].ToString()) + " SET " + POut.String(table.Rows[i]["column_name"].ToString()) + "='' WHERE " + POut.String(table.Rows[i]["column_name"].ToString()) + " IS NULL";
            changeCount += Db.nonQ(command);
        }
        return changeCount;
    }

    /**
    * Return values look like 'MyISAM' or 'InnoDB'. Will return empty string on error.
    */
    public static String getStorageEngineDefaultName() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        String command = "SHOW GLOBAL VARIABLES LIKE 'storage_engine'";
        DataTable dtEngine = Db.getTable(command);
        if (dtEngine.Rows.Count > 0)
        {
            try
            {
                return PIn.String(dtEngine.Rows[0]["Value"].ToString());
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
         
        return "";
    }

    /**
    * Gets the names of tables in InnoDB format, comma delimited (excluding the 'phone' table).  Returns empty string if none.
    */
    public static String getInnodbTableNames() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        //Using COUNT(*) with INFORMATION_SCHEMA is buggy.  It can return "1" even if no results.
        String command = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.tables " + "WHERE TABLE_SCHEMA='" + POut.string(OpenDentBusiness.DataConnection.getDatabaseName()) + "' " + "AND TABLE_NAME!='phone' " + "AND ENGINE NOT LIKE 'MyISAM'";
        //this table is used internally at OD HQ, and is always innodb.
        DataTable table = Db.getTable(command);
        String tableNames = "";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (!StringSupport.equals(tableNames, ""))
            {
                tableNames += ",";
            }
             
            tableNames += PIn.String(table.Rows[i][0].ToString());
        }
        return tableNames;
    }

    /**
    * Gets the number of tables in MyISAM format.
    */
    public static int getMyisamTableCount() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.tables " + "WHERE TABLE_SCHEMA='" + POut.string(OpenDentBusiness.DataConnection.getDatabaseName()) + "' " + "AND ENGINE LIKE 'MyISAM'";
        return Db.getTable(command).Rows.Count;
    }

    /**
    * Returns true if the conversion was successfull or no conversion was necessary. The goal is to convert InnoDB tables (excluding the 'phone' table) to MyISAM format when there are a mixture of InnoDB and MyISAM tables but no conversion will be performed when all of the tables are already in the same format.
    */
    public static boolean convertTablesToMyisam() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod());
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return true;
        }
         
        //Does not apply to Oracle.
        command = "SELECT TABLE_NAME,ENGINE FROM INFORMATION_SCHEMA.tables " + "WHERE TABLE_SCHEMA='" + POut.string(OpenDentBusiness.DataConnection.getDatabaseName()) + "' " + "AND TABLE_NAME!='phone'";
        //this table is used internally at OD HQ, and is always innodb.
        DataTable dtTableTypes = Db.getTable(command);
        int numInnodb = 0;
        //Or possibly some other format.
        int numMyisam = 0;
        for (int i = 0;i < dtTableTypes.Rows.Count;i++)
        {
            if (StringSupport.equals(PIn.String(dtTableTypes.Rows[i]["ENGINE"].ToString()).ToUpper(), "MYISAM"))
            {
                numMyisam++;
            }
            else
            {
                numInnodb++;
            } 
        }
        if (numInnodb > 0 && numMyisam > 0)
        {
            for (int i = 0;i < dtTableTypes.Rows.Count;i++)
            {
                //Fix tables by converting them to MyISAM when there is a mixture of different table types.
                if (StringSupport.equals(PIn.String(dtTableTypes.Rows[i]["ENGINE"].ToString()).ToUpper(), "MYISAM"))
                {
                    continue;
                }
                 
                String tableName = PIn.String(dtTableTypes.Rows[i]["TABLE_NAME"].ToString());
                command = "ALTER TABLE " + POut.string(tableName) + " ENGINE='MyISAM'";
                try
                {
                    Db.nonQ(command);
                }
                catch (Exception __dummyCatchVar1)
                {
                    return false;
                }
            
            }
            command = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.tables " + "WHERE TABLE_SCHEMA='" + POut.string(OpenDentBusiness.DataConnection.getDatabaseName()) + "' " + "AND TABLE_NAME!='phone' " + "AND ENGINE NOT LIKE 'MyISAM'";
            if (Db.getTable(command).Rows.Count != 0)
            {
                return false;
            }
             
        }
         
        return true;
    }

}


//If any tables are still InnoDB.