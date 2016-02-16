//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Encounters;
import OpenDentBusiness.Fees;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PIn;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProcCodeNotes;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.ProcedureComparer;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcNote;
import OpenDentBusiness.ProcNotes;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.ToothInitials;
import OpenDentBusiness.ToothInitialType;
import OpenDentBusiness.ToothPaintingType;
import OpenDentBusiness.TreatmentArea;

public class Procedures   
{
    /**
    * Gets all procedures for a single patient, without notes.  Does not include deleted procedures.
    */
    public static List<Procedure> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Procedure>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM procedurelog WHERE PatNum=" + POut.long(patNum) + " AND ProcStatus !=6" + " ORDER BY ProcDate";
        return Crud.ProcedureCrud.SelectMany(command);
    }

    //don't include deleted
    /**
    * It shows all completed procs for the family that are not already attached to a provkey. No notes.
    */
    public static List<Procedure> getForProvKey(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Procedure>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT procedurelog.* FROM procedurelog,patient " + "WHERE procedurelog.PatNum=patient.PatNum " + "AND patient.Guarantor=(SELECT patkey.Guarantor FROM patient patkey WHERE patkey.PatNum=" + POut.long(patNum) + ") " + "AND ProcStatus = " + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "AND NOT EXISTS (SELECT * FROM ehrprovkey WHERE ehrprovkey.ProcNum=procedurelog.ProcNum) " + "GROUP BY procedurelog.ProcNum " + "ORDER BY ProcDate";
        return Crud.ProcedureCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(Procedure procedure) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            procedure.ProcNum = Meth.GetLong(MethodBase.GetCurrentMethod(), procedure);
            return procedure.ProcNum;
        }
         
        Crud.ProcedureCrud.Insert(procedure);
        if (!StringSupport.equals(procedure.Note, ""))
        {
            ProcNote note = new ProcNote();
            note.PatNum = procedure.PatNum;
            note.ProcNum = procedure.ProcNum;
            note.UserNum = procedure.UserNum;
            note.Note = procedure.Note;
            ProcNotes.insert(note);
        }
         
        return procedure.ProcNum;
    }

    /**
    * Updates only the changed columns.
    */
    public static void update(Procedure procedure, Procedure oldProcedure) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procedure, oldProcedure);
            return ;
        }
         
        Crud.ProcedureCrud.Update(procedure, oldProcedure);
        if (!StringSupport.equals(procedure.Note, oldProcedure.Note) || procedure.UserNum != oldProcedure.UserNum || procedure.SigIsTopaz != oldProcedure.SigIsTopaz || !StringSupport.equals(procedure.Signature, oldProcedure.Signature))
        {
            ProcNote note = new ProcNote();
            note.PatNum = procedure.PatNum;
            note.ProcNum = procedure.ProcNum;
            note.UserNum = procedure.UserNum;
            note.Note = procedure.Note;
            note.SigIsTopaz = procedure.SigIsTopaz;
            note.Signature = procedure.Signature;
            ProcNotes.insert(note);
        }
         
    }

    /**
    * If not allowed to delete, then it throws an exception, so surround it with a try catch.  Also deletes any claimProcs and adjustments.  This does not actually delete the procedure, but just changes the status to deleted.
    */
    public static void delete(long procNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procNum);
            return ;
        }
         
        //Test to see if any payment at all has been received for this proc
        String command = "SELECT COUNT(*) FROM claimproc WHERE ProcNum=" + POut.long(procNum) + " AND InsPayAmt > 0 AND Status != " + POut.Long(((Enum)OpenDentBusiness.ClaimProcStatus.Preauth).ordinal());
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new Exception(Lans.g("Procedures","Not allowed to delete a procedure that is attached to an insurance payment."));
        }
         
        //Test to see if any referrals exist for this proc
        command = "SELECT COUNT(*) FROM refattach WHERE ProcNum=" + POut.long(procNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new Exception(Lans.g("Procedures","Not allowed to delete a procedure with referrals attached."));
        }
         
        //delete adjustments
        command = "DELETE FROM adjustment WHERE ProcNum='" + POut.long(procNum) + "'";
        Db.nonQ(command);
        //delete claimprocs
        command = "DELETE from claimproc WHERE ProcNum = '" + POut.long(procNum) + "'";
        Db.nonQ(command);
        //detach procedure labs
        command = "UPDATE procedurelog SET ProcNumLab=0 WHERE ProcNumLab='" + POut.long(procNum) + "'";
        Db.nonQ(command);
        //resynch appointment description-------------------------------------------------------------------------------------
        command = "SELECT AptNum,PlannedAptNum FROM procedurelog WHERE ProcNum = " + POut.long(procNum);
        DataTable table = Db.getTable(command);
        String aptnum = table.Rows[0][0].ToString();
        String plannedaptnum = table.Rows[0][1].ToString();
        String procdescript = new String();
        if (!StringSupport.equals(aptnum, "0"))
        {
            command = "SELECT AbbrDesc FROM procedurecode,procedurelog\r\n" + 
            "\t\t\t\t\tWHERE procedurecode.CodeNum=procedurelog.CodeNum\r\n" + 
            "\t\t\t\t\tAND ProcNum != " + POut.long(procNum) + " AND procedurelog.AptNum=" + aptnum;
            table = Db.getTable(command);
            procdescript = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (i > 0)
                    procdescript += ", ";
                 
                procdescript += table.Rows[i]["AbbrDesc"].ToString();
            }
            command = "UPDATE appointment SET ProcDescript='" + POut.string(procdescript) + "' " + "WHERE AptNum=" + aptnum;
            Db.nonQ(command);
        }
         
        if (!StringSupport.equals(plannedaptnum, "0"))
        {
            command = "SELECT AbbrDesc FROM procedurecode,procedurelog\r\n" + 
            "\t\t\t\t\tWHERE procedurecode.CodeNum=procedurelog.CodeNum\r\n" + 
            "\t\t\t\t\tAND ProcNum != " + POut.long(procNum) + " AND procedurelog.PlannedAptNum=" + plannedaptnum;
            table = Db.getTable(command);
            procdescript = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (i > 0)
                    procdescript += ", ";
                 
                procdescript += table.Rows[i]["AbbrDesc"].ToString();
            }
            command = "UPDATE appointment SET ProcDescript='" + POut.string(procdescript) + "' " + "WHERE NextAptNum=" + plannedaptnum;
            Db.nonQ(command);
        }
         
        //set the procedure deleted-----------------------------------------------------------------------------------------
        command = "UPDATE procedurelog SET ProcStatus = " + POut.Long(((Enum)OpenDentBusiness.ProcStat.D).ordinal()) + ", " + "AptNum=0, " + "PlannedAptNum=0 " + "WHERE ProcNum = '" + POut.long(procNum) + "'";
        Db.nonQ(command);
    }

    public static void updateAptNum(long procNum, long newAptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procNum, newAptNum);
            return ;
        }
         
        String command = "UPDATE procedurelog SET AptNum = " + POut.long(newAptNum) + " WHERE ProcNum = " + POut.long(procNum);
        Db.nonQ(command);
    }

    public static void updatePlannedAptNum(long procNum, long newPlannedAptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procNum, newPlannedAptNum);
            return ;
        }
         
        String command = "UPDATE procedurelog SET PlannedAptNum = " + POut.long(newPlannedAptNum) + " WHERE ProcNum = " + POut.long(procNum);
        Db.nonQ(command);
    }

    public static void updatePriority(long procNum, long newPriority) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procNum, newPriority);
            return ;
        }
         
        String command = "UPDATE procedurelog SET Priority = " + POut.long(newPriority) + " WHERE ProcNum = " + POut.long(procNum);
        Db.nonQ(command);
    }

    public static void updateFee(long procNum, double newFee) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procNum, newFee);
            return ;
        }
         
        String command = "UPDATE procedurelog SET ProcFee = " + POut.double(newFee) + " WHERE ProcNum = " + POut.long(procNum);
        Db.nonQ(command);
    }

    /**
    * Gets one procedure directly from the db.  Option to include the note.
    */
    public static Procedure getOneProc(long procNum, boolean includeNote) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Procedure>GetObject(MethodBase.GetCurrentMethod(), procNum, includeNote);
        }
         
        String command = "SELECT * FROM procedurelog " + "WHERE ProcNum=" + procNum.ToString();
        Procedure proc = Crud.ProcedureCrud.SelectOne(procNum);
        if (proc == null)
        {
            return new Procedure();
        }
         
        command = "SELECT * FROM procnote WHERE ProcNum=" + POut.long(procNum) + " ORDER BY EntryDateTime DESC";
        DbHelper.limitOrderBy(command,1);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return proc;
        }
         
        proc.UserNum = PIn.Long(table.Rows[0]["UserNum"].ToString());
        proc.Note = PIn.String(table.Rows[0]["Note"].ToString());
        proc.SigIsTopaz = PIn.Bool(table.Rows[0]["SigIsTopaz"].ToString());
        proc.Signature = PIn.String(table.Rows[0]["Signature"].ToString());
        return proc;
    }

    /**
    * Gets Procedures for a single appointment directly from the database
    */
    public static List<Procedure> getProcsForSingle(long aptNum, boolean isPlanned) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Procedure>>GetObject(MethodBase.GetCurrentMethod(), aptNum, isPlanned);
        }
         
        String command = new String();
        if (isPlanned)
        {
            command = "SELECT * from procedurelog WHERE PlannedAptNum = '" + POut.long(aptNum) + "'";
        }
        else
        {
            command = "SELECT * from procedurelog WHERE AptNum = '" + POut.long(aptNum) + "'";
        } 
        return Crud.ProcedureCrud.SelectMany(command);
    }

    /**
    * Gets all Procedures for a single date for the specified patient directly from the database
    */
    public static List<Procedure> getProcsForPatByDate(long patNum, DateTime date) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Procedure>>GetObject(MethodBase.GetCurrentMethod(), patNum, date);
        }
         
        String command = "SELECT * FROM procedurelog " + "WHERE PatNum='" + POut.long(patNum) + "' AND (ProcDate=" + POut.date(date) + " OR DateEntryC=" + POut.date(date) + ")";
        List<Procedure> result = Crud.ProcedureCrud.SelectMany(command);
        for (int i = 0;i < result.Count;i++)
        {
            command = "SELECT * FROM procnote WHERE ProcNum=" + POut.Long(result[i].ProcNum) + " ORDER BY EntryDateTime DESC";
            command = DbHelper.limitOrderBy(command,1);
            DataTable table = Db.getTable(command);
            if (table.Rows.Count == 0)
            {
                continue;
            }
             
            result[i].UserNum = PIn.Long(table.Rows[0]["UserNum"].ToString());
            result[i].Note = PIn.String(table.Rows[0]["Note"].ToString());
            result[i].SigIsTopaz = PIn.Bool(table.Rows[0]["SigIsTopaz"].ToString());
            result[i].Signature = PIn.String(table.Rows[0]["Signature"].ToString());
        }
        return result;
    }

    /**
    * Gets all procedures associated with corresponding claimprocs. Returns empty procedure list if an empty list was passed in.
    */
    public static List<Procedure> getProcsFromClaimProcs(List<ClaimProc> listClaimProc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Procedure>>GetObject(MethodBase.GetCurrentMethod(), listClaimProc);
        }
         
        if (listClaimProc.Count == 0)
        {
            return new List<Procedure>();
        }
         
        String command = "SELECT * FROM procedurelog WHERE ProcNum IN (";
        for (int i = 0;i < listClaimProc.Count;i++)
        {
            if (i > 0)
            {
                command += ",";
            }
             
            command += listClaimProc[i].ProcNum;
        }
        command += ")";
        return Crud.ProcedureCrud.SelectMany(command);
    }

    /**
    * Gets a string in M/yy format for the most recent completed procedure in the specified code range.  Gets directly from the database.
    */
    public static String getRecentProcDateString(long patNum, DateTime aptDate, String procCodeRange) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), patNum, aptDate, procCodeRange);
        }
         
        if (aptDate.Year < 1880)
        {
            aptDate = DateTime.Today;
        }
         
        String code1 = new String();
        String code2 = new String();
        if (procCodeRange.Contains("-"))
        {
            String[] codeSplit = procCodeRange.Split('-');
            code1 = codeSplit[0].Trim();
            code2 = codeSplit[1].Trim();
        }
        else
        {
            code1 = procCodeRange.Trim();
            code2 = procCodeRange.Trim();
        } 
        //+"AND CodeNum="+POut.Long(codeNum)+" "
        String command = "SELECT ProcDate FROM procedurelog " + "LEFT JOIN procedurecode ON procedurecode.CodeNum=procedurelog.CodeNum " + "WHERE PatNum=" + POut.long(patNum) + " " + "AND ProcDate < " + POut.date(aptDate) + " " + "AND (ProcStatus =" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "OR ProcStatus =" + POut.int(((Enum)OpenDentBusiness.ProcStat.EC).ordinal()) + " " + "OR ProcStatus =" + POut.int(((Enum)OpenDentBusiness.ProcStat.EO).ordinal()) + ") " + "AND procedurecode.ProcCode >= '" + POut.string(code1) + "' " + "AND procedurecode.ProcCode <= '" + POut.string(code2) + "' " + "ORDER BY ProcDate DESC";
        command = DbHelper.limitOrderBy(command,1);
        DateTime date = PIn.date(Db.getScalar(command));
        if (date.Year < 1880)
        {
            return "";
        }
         
        return date.ToString("M/yy");
    }

    /**
    * Gets the first completed procedure within the family.  Used to determine the earliest date the family became a customer.
    */
    public static Procedure getFirstCompletedProcForFamily(long guarantor) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Procedure>GetObject(MethodBase.GetCurrentMethod(), guarantor);
        }
         
        String command = "SELECT procedurelog.* FROM procedurelog " + "LEFT JOIN patient ON procedurelog.PatNum=patient.PatNum AND patient.Guarantor=" + POut.long(guarantor) + " " + "WHERE " + DbHelper.year("procedurelog.ProcDate") + ">1 " + "AND procedurelog.ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "ORDER BY procedurelog.ProcDate";
        command = DbHelper.limitOrderBy(command,1);
        return Crud.ProcedureCrud.SelectOne(command);
    }

    /**
    * Gets a list (procsMultApts is a struct of type ProcDesc(aptNum, string[], and production) of all the procedures attached to the specified appointments.  Then, use GetProcsOneApt to pull procedures for one appointment from this list.  This process requires only one call to the database. "myAptNums" is the list of appointments to get procedures for.
    */
    public static List<Procedure> getProcsMultApts(List<long> myAptNums) throws Exception {
        return GetProcsMultApts(myAptNums, false);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Gets a list (procsMultApts is a struct of type ProcDesc(aptNum, string[], and production) of all the procedures attached to the specified appointments.  Then, use GetProcsOneApt to pull procedures for one appointment from this list or GetProductionOneApt.  This process requires only one call to the database.  "myAptNums" is the list of appointments to get procedures for.  isForNext gets procedures for a list of next appointments rather than regular appointments.
    */
    public static List<Procedure> getProcsMultApts(List<long> myAptNums, boolean isForPlanned) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Procedure>>GetObject(MethodBase.GetCurrentMethod(), myAptNums, isForPlanned);
        }
         
        if (myAptNums.Count == 0)
        {
            return new List<Procedure>();
        }
         
        String strAptNums = "";
        for (int i = 0;i < myAptNums.Count;i++)
        {
            if (i > 0)
            {
                strAptNums += " OR";
            }
             
            if (isForPlanned)
            {
                strAptNums += " PlannedAptNum='" + POut.Long(myAptNums[i]) + "'";
            }
            else
            {
                strAptNums += " AptNum='" + POut.Long(myAptNums[i]) + "'";
            } 
        }
        String command = "SELECT * FROM procedurelog WHERE" + strAptNums;
        return Crud.ProcedureCrud.SelectMany(command);
    }

    /**
    * Gets procedures for one appointment by looping through the procsMultApts which was filled previously from GetProcsMultApts.
    */
    public static Procedure[] getProcsOneApt(long myAptNum, List<Procedure> procsMultApts) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList AL = new ArrayList();
        for (int i = 0;i < procsMultApts.Count;i++)
        {
            if (procsMultApts[i].AptNum == myAptNum)
            {
                AL.Add(procsMultApts[i].Copy());
            }
             
        }
        Procedure[] retVal = new Procedure[AL.Count];
        AL.CopyTo(retVal);
        return retVal;
    }

    /**
    * Gets the production for one appointment by looping through the procsMultApts which was filled previously from GetProcsMultApts.
    */
    public static double getProductionOneApt(long myAptNum, Procedure[] procsMultApts, boolean isPlanned) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < procsMultApts.Length;i++)
        {
            if (isPlanned && procsMultApts[i].PlannedAptNum == myAptNum)
            {
                retVal += procsMultApts[i].ProcFee;
            }
             
            if (!isPlanned && procsMultApts[i].AptNum == myAptNum)
            {
                retVal += procsMultApts[i].ProcFee;
            }
             
        }
        return retVal;
    }

    /**
    * Used in FormClaimEdit,FormClaimPrint,FormClaimPayTotal,ContrAccount etc to get description of procedure. Procedure list needs to include the procedure we are looking for.
    */
    public static Procedure getProcFromList(List<Procedure> list, long procNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (procNum == list[i].ProcNum)
            {
                return list[i];
            }
             
        }
        return new Procedure();
    }

    //MessageBox.Show("Error. Procedure not found");
    /**
    * Sets the patient.DateFirstVisit if necessary. A visitDate is required to be passed in because it may not be today's date. This is triggered by:
    * 1. When any procedure is inserted regardless of status. From Chart or appointment. If no C procs and date blank, changes date.
    * 2. When updating a procedure to status C. If no C procs, update visit date. Ask user first?
    * #2 was recently changed to only happen if date is blank or less than 7 days old.
    * 3. When an appointment is deleted. If no C procs, clear visit date.
    * #3 was recently changed to not occur at all unless appt is of type IsNewPatient
    * 4. Changing an appt date of type IsNewPatient. If no C procs, change visit date.
    * Old: when setting a procedure complete in the Chart module or the ProcEdit window.  Also when saving an appointment that is marked IsNewPat.
    */
    public static void setDateFirstVisit(DateTime visitDate, int situation, Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), visitDate, situation, pat);
            return ;
        }
         
        if (situation == 1)
        {
            if (pat.DateFirstVisit.Year > 1880)
            {
                return ;
            }
             
        }
         
        //a date has already been set.
        if (situation == 2)
        {
            if (pat.DateFirstVisit.Year > 1880 && pat.DateFirstVisit < DateTime.Now.AddDays(-7))
            {
                return ;
            }
             
        }
         
        //a date has already been set.
        String command = "SELECT Count(*) from procedurelog WHERE " + "PatNum = '" + POut.long(pat.PatNum) + "' " + "AND ProcStatus = '2'";
        DataTable table = Db.getTable(command);
        if (PIn.Long(table.Rows[0][0].ToString()) > 0)
        {
            return ;
        }
         
        //there are already completed procs (for all situations)
        if (situation == 2)
        {
        }
         
        //ask user first?
        if (situation == 3)
        {
            command = "UPDATE patient SET DateFirstVisit =" + POut.date(new DateTime(0001, 01, 01)) + " WHERE PatNum ='" + POut.long(pat.PatNum) + "'";
        }
        else
        {
            command = "UPDATE patient SET DateFirstVisit =" + POut.date(visitDate) + " WHERE PatNum ='" + POut.long(pat.PatNum) + "'";
        } 
        //MessageBox.Show(cmd.CommandText);
        //dcon.NonQ(command);
        Db.nonQ(command);
    }

    /**
    * Called from FormApptsOther when creating a new appointment.  Returns true if there are any procedures marked complete for this patient.  The result is that the NewPt box on the appointment won't be checked.
    */
    public static boolean areAnyComplete(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT COUNT(*) FROM procedurelog " + "WHERE PatNum=" + patNum.ToString() + " AND ProcStatus=2";
        DataTable table = Db.getTable(command);
        if (StringSupport.equals(table.Rows[0][0].ToString(), "0"))
        {
            return false;
        }
        else
            return true; 
    }

    /**
    * Called from AutoCodeItems.  Makes a call to the database to determine whether the specified tooth has been extracted or will be extracted. This could then trigger a pontic code.
    */
    public static boolean willBeMissing(String toothNum, long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), toothNum, patNum);
        }
         
        //first, check for missing teeth
        String command = "SELECT COUNT(*) FROM toothinitial " + "WHERE ToothNum='" + toothNum + "' " + "AND PatNum=" + POut.long(patNum) + " AND InitialType=0";
        //missing
        DataTable table = Db.getTable(command);
        if (!StringSupport.equals(table.Rows[0][0].ToString(), "0"))
        {
            return true;
        }
         
        //then, check for a planned extraction
        command = "SELECT COUNT(*) FROM procedurelog,procedurecode " + "WHERE procedurelog.CodeNum=procedurecode.CodeNum " + "AND procedurelog.ToothNum='" + toothNum + "' " + "AND procedurelog.PatNum=" + patNum.ToString() + " " + "AND procedurelog.ProcStatus <> " + POut.int(((Enum)OpenDentBusiness.ProcStat.D).ordinal()) + " " + "AND procedurecode.PaintType=1";
        //Not deleted procedures
        //extraction
        table = Db.getTable(command);
        if (!StringSupport.equals(table.Rows[0][0].ToString(), "0"))
        {
            return true;
        }
         
        return false;
    }

    public static void attachToApt(long procNum, long aptNum, boolean isPlanned) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<long> procNums = new List<long>();
        procNums.Add(procNum);
        AttachToApt(procNums, aptNum, isPlanned);
    }

    public static void attachToApt(List<long> procNums, long aptNum, boolean isPlanned) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procNums, aptNum, isPlanned);
            return ;
        }
         
        if (procNums.Count == 0)
        {
            return ;
        }
         
        String command = "UPDATE procedurelog SET ";
        if (isPlanned)
        {
            command += "PlannedAptNum";
        }
        else
        {
            command += "AptNum";
        } 
        command += "=" + POut.long(aptNum) + " WHERE ";
        for (int i = 0;i < procNums.Count;i++)
        {
            if (i > 0)
            {
                command += " OR ";
            }
             
            command += "ProcNum=" + POut.Long(procNums[i]);
        }
        Db.nonQ(command);
    }

    public static void detachFromApt(List<long> procNums, boolean isPlanned) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procNums, isPlanned);
            return ;
        }
         
        if (procNums.Count == 0)
        {
            return ;
        }
         
        String command = "UPDATE procedurelog SET ";
        if (isPlanned)
        {
            command += "PlannedAptNum";
        }
        else
        {
            command += "AptNum";
        } 
        command += "=0 WHERE ";
        for (int i = 0;i < procNums.Count;i++)
        {
            if (i > 0)
            {
                command += " OR ";
            }
             
            command += "ProcNum=" + POut.Long(procNums[i]);
        }
        Db.nonQ(command);
    }

    public static void detachFromInvoice(long statementNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), statementNum);
            return ;
        }
         
        String command = "UPDATE procedurelog SET StatementNum=0 WHERE StatementNum='" + POut.long(statementNum) + "'";
        Db.nonQ(command);
    }

    //--------------------Taken from Procedure class--------------------------------------------------
    /*
    		///<summary>Gets allowedOverride for this procedure based on supplied claimprocs. Includes all claimproc types.  Only used in main TP module when calculating PPOs. The claimProc array typically includes all claimProcs for the patient, but must at least include all claimprocs for this proc.</summary>
    		public static double GetAllowedOverride(Procedure proc,ClaimProc[] claimProcs,int priPlanNum) {
    			//double retVal=0;
    			for(int i=0;i<claimProcs.Length;i++) {
    				if(claimProcs[i].ProcNum==proc.ProcNum && claimProcs[i].PlanNum==priPlanNum) {
    					return claimProcs[i].AllowedOverride;
    					//retVal+=claimProcs[i].WriteOff;
    				}
    			}
    			return 0;//retVal;
    		}*/
    /*
    		///<summary>Gets total writeoff for this procedure based on supplied claimprocs. Includes all claimproc types.  Only used in main TP module. The claimProc array typically includes all claimProcs for the patient, but must at least include all claimprocs for this proc.</summary>
    		public static double GetWriteOff(Procedure proc,List<ClaimProc> claimProcs) {
    			//No need to check RemotingRole; no call to db.
    			double retVal=0;
    			for(int i=0;i<claimProcs.Count;i++) {
    				if(claimProcs[i].ProcNum==proc.ProcNum) {
    					retVal+=claimProcs[i].WriteOff;
    				}
    			}
    			return retVal;
    		}*/
    /**
    * WriteOff'Complete'. Only used in main Account module. Gets writeoff for this procedure based on supplied claimprocs. Only includes claimprocs with status of CapComplete,CapClaim,NotReceived,Received,or Supplemental. Used to ONLY include Writeoffs not attached to claims, because those would display on the claim line, but now they show on each procedure instead.  /*In practice, this means only writeoffs with CapComplete status get returned because they are to be subtracted from the patient portion on the proc line*/. The claimProc array typically includes all claimProcs for the patient, but must at least include all claimprocs for this proc.
    */
    public static double getWriteOffC(Procedure proc, ClaimProc[] claimProcs) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < claimProcs.Length;i++)
        {
            if (claimProcs[i].ProcNum != proc.ProcNum)
            {
                continue;
            }
             
            //if(claimProcs[i].ClaimNum>0) {
            //	continue;
            //}
            //adj skipped
            //capEstimate would never happen because procedure is C.
            //estimate means not attached to claim, so don't count
            //|| claimProcs[i].Status==ClaimProcStatus.NotReceived//see below
            //preAuth -no
            if (claimProcs[i].Status == OpenDentBusiness.ClaimProcStatus.CapClaim || claimProcs[i].Status == OpenDentBusiness.ClaimProcStatus.CapComplete || claimProcs[i].Status == OpenDentBusiness.ClaimProcStatus.Received || claimProcs[i].Status == OpenDentBusiness.ClaimProcStatus.Supplemental)
            {
                retVal += claimProcs[i].WriteOff;
            }
             
            //this is the typical situation
            if (!PrefC.getBool(PrefName.BalancesDontSubtractIns) && claimProcs[i].Status == OpenDentBusiness.ClaimProcStatus.NotReceived)
            {
                //so, if user IS using "balances don't subtract ins", and a proc as been sent but not received,
                //then we do not subtract the writeoff because it's considered part of the estimate.
                retVal += claimProcs[i].WriteOff;
            }
             
        }
        return retVal;
    }

    /**
    * Used in deciding how to display procedures in Account. The claimProcList can be all claimProcs for the patient or only those attached to this proc. Will be true if any claimProcs at all are attached to this procedure.
    */
    public static boolean isCoveredIns(Procedure proc, ClaimProc[] List) throws Exception {
        for (int i = 0;i < List.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (List[i].ProcNum == proc.ProcNum)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Used in deciding how to display procedures in Account. The claimProcList can be all claimProcs for the patient or only those attached to this proc. Will be true if any claimProcs attached to this procedure are set NoBillIns.
    */
    public static boolean noBillIns(Procedure proc, List<ClaimProc> claimProcList) throws Exception {
        for (int i = 0;i < claimProcList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (claimProcList[i].ProcNum == proc.ProcNum && claimProcList[i].NoBillIns)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Used in ContrAccount.CreateClaim when validating selected procedures. Returns true if there is any claimproc for this procedure and plan which is marked NoBillIns.  The claimProcList can be all claimProcs for the patient or only those attached to this proc. Will be true if any claimProcs attached to this procedure are set NoBillIns.
    */
    public static boolean noBillIns(Procedure proc, List<ClaimProc> claimProcList, long planNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (proc == null)
        {
            return false;
        }
         
        for (int i = 0;i < claimProcList.Count;i++)
        {
            if (claimProcList[i].ProcNum == proc.ProcNum && claimProcList[i].PlanNum == planNum && claimProcList[i].NoBillIns)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Used in deciding how to display procedures in Account. The claimProcList can be all claimProcs for the patient or only those attached to this proc. Will be true if any claimProcs attached to this procedure are status estimate, which means they haven't been attached to a claim because their status would have been changed to NotReceived.  And if the patient doesn't have ins, then the estimates would have been deleted.
    */
    public static boolean isUnsent(Procedure proc, ClaimProc[] List) throws Exception {
        for (int i = 0;i < List.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            //unsent if no claimprocs with claimNums
            if (List[i].ProcNum == proc.ProcNum && List[i].Status == OpenDentBusiness.ClaimProcStatus.Estimate)
            {
                return true;
            }
             
        }
        return false;
    }

    //&& List[i].ClaimNum>0
    //&& List[i].Status!=ClaimProcStatus.Preauth
    /**
    * Called from FormProcEdit to signal when to disable much of the editing in that form. If the procedure is 'AttachedToClaim' then user should not change it very much.  Also prevents user from Invalidating a locked procedure if attached to a claim.  The claimProcList can be all claimProcs for the patient or only those attached to this proc.
    */
    public static boolean isAttachedToClaim(Procedure proc, List<ClaimProc> claimProcList) throws Exception {
        for (int i = 0;i < claimProcList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (claimProcList[i].ProcNum == proc.ProcNum && claimProcList[i].ClaimNum > 0 && (claimProcList[i].Status == OpenDentBusiness.ClaimProcStatus.CapClaim || claimProcList[i].Status == OpenDentBusiness.ClaimProcStatus.NotReceived || claimProcList[i].Status == OpenDentBusiness.ClaimProcStatus.Preauth || claimProcList[i].Status == OpenDentBusiness.ClaimProcStatus.Received || claimProcList[i].Status == OpenDentBusiness.ClaimProcStatus.Supplemental))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Only called from FormProcEdit.  When attached  to a claim and user clicks Edit Anyway, we need to know the oldest claim date for security reasons.  The claimProcsForProc should only be claimprocs for this procedure.
    */
    public static DateTime getOldestClaimDate(long procNum, List<ClaimProc> claimProcsForProc) throws Exception {
        //No need to check RemotingRole; no call to db.
        Claim claim;
        DateTime retVal = DateTime.Today;
        for (int i = 0;i < claimProcsForProc.Count;i++)
        {
            if (claimProcsForProc[i].ClaimNum == 0)
            {
                continue;
            }
             
            if (claimProcsForProc[i].Status == OpenDentBusiness.ClaimProcStatus.CapClaim || claimProcsForProc[i].Status == OpenDentBusiness.ClaimProcStatus.NotReceived || claimProcsForProc[i].Status == OpenDentBusiness.ClaimProcStatus.Preauth || claimProcsForProc[i].Status == OpenDentBusiness.ClaimProcStatus.Received || claimProcsForProc[i].Status == OpenDentBusiness.ClaimProcStatus.Supplemental)
            {
                claim = Claims.GetClaim(claimProcsForProc[i].ClaimNum);
                if (claim.DateSent < retVal)
                {
                    retVal = claim.DateSent;
                }
                 
            }
             
        }
        return retVal;
    }

    /**
    * Only called from FormProcEditAll to signal when to disable much of the editing in that form. If the procedure is 'AttachedToClaim' then user should not change it very much.  The claimProcList can be all claimProcs for the patient or only those attached to this proc.
    */
    public static boolean isAttachedToClaim(List<Procedure> procList, List<ClaimProc> claimprocList) throws Exception {
        for (int j = 0;j < procList.Count;j++)
        {
            //No need to check RemotingRole; no call to db.
            if (IsAttachedToClaim(procList[j], claimprocList))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Queries the database to determine if this procedure is attached to a claim already.
    */
    public static boolean isAttachedToClaim(long procNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), procNum);
        }
         
        String command = "SELECT COUNT(*) FROM claimproc " + "WHERE ProcNum=" + POut.long(procNum) + " " + "AND ClaimNum>0";
        DataTable table = Db.getTable(command);
        if (StringSupport.equals(table.Rows[0][0].ToString(), "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * Used in ContrAccount.CreateClaim to validate that procedure is not already attached to a claim for this specific insPlan.  The claimProcList can be all claimProcs for the patient or only those attached to this proc.
    */
    public static boolean isAlreadyAttachedToClaim(Procedure proc, List<ClaimProc> claimProcList, long insSubNum) throws Exception {
        for (int i = 0;i < claimProcList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (claimProcList[i].ProcNum == proc.ProcNum && claimProcList[i].InsSubNum == insSubNum && claimProcList[i].ClaimNum > 0 && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.Preauth)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Only used in ContrAccount.OnInsClick to automate selection of procedures.  Returns true if this procedure should be selected.  This happens if there is at least one claimproc attached for this inssub that is an estimate, and it is not set to NoBillIns.  The list can be all ClaimProcs for patient, or just those for this procedure. The plan is the primary plan.
    */
    public static boolean needsSent(long procNum, long insSubNum, List<ClaimProc> claimProcList) throws Exception {
        for (int i = 0;i < claimProcList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (claimProcList[i].ProcNum == procNum && !claimProcList[i].NoBillIns && claimProcList[i].InsSubNum == insSubNum && claimProcList[i].Status == OpenDentBusiness.ClaimProcStatus.Estimate)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Only used in ContrAccount.CreateClaim and FormRepeatChargeUpdate.CreateClaim to decide whether a given procedure has an estimate that can be used to attach to a claim for the specified plan.  Returns a valid claimProc if this procedure has an estimate attached that is not set to NoBillIns.  The list can be all ClaimProcs for patient, or just those for this procedure. Returns null if there are no claimprocs that would work.
    */
    public static ClaimProc getClaimProcEstimate(long procNum, List<ClaimProc> claimProcList, InsPlan plan, long insSubNum) throws Exception {
        for (int i = 0;i < claimProcList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            //bool matchOfWrongType=false;
            if (claimProcList[i].ProcNum == procNum && !claimProcList[i].NoBillIns && claimProcList[i].PlanNum == plan.PlanNum && claimProcList[i].InsSubNum == insSubNum)
            {
                if (StringSupport.equals(plan.PlanType, "c"))
                {
                    if (claimProcList[i].Status == OpenDentBusiness.ClaimProcStatus.CapComplete)
                    {
                        return claimProcList[i];
                    }
                     
                }
                else
                {
                    //any type except capitation
                    if (claimProcList[i].Status == OpenDentBusiness.ClaimProcStatus.Estimate)
                    {
                        return claimProcList[i];
                    }
                     
                } 
            }
             
        }
        return null;
    }

    /**
    * Used by GetProcsForSingle and GetProcsMultApts to generate a short string description of a procedure.
    */
    public static String convertProcToString(long codeNum, String surf, String toothNum, boolean forAccount) throws Exception {
        //No need to check RemotingRole; no call to db.
        String strLine = "";
        ProcedureCode code = ProcedureCodes.getProcCode(codeNum);
        switch(code.TreatArea)
        {
            case Surf: 
                if (!forAccount)
                {
                    strLine += "#" + Tooth.toInternat(toothNum) + "-";
                }
                 
                //"#12-"
                strLine += Tooth.surfTidyFromDbToDisplay(surf,toothNum);
                break;
            case Tooth: 
                //"MOD-"
                if (!forAccount)
                {
                    strLine += "#" + Tooth.toInternat(toothNum) + "-";
                }
                 
                break;
            default: 
                break;
            case Quad: 
                //"#12-"
                //area 3 or 0 (mouth)
                strLine += surf + "-";
                break;
            case Sextant: 
                //"UL-"
                strLine += "S" + surf + "-";
                break;
            case Arch: 
                //"S2-"
                strLine += surf + "-";
                break;
            case ToothRange: 
                break;
        
        }
        //"U-"
        //strLine+=table.Rows[j][13].ToString()+" ";//don't show range
        //end switch
        if (!forAccount)
        {
            strLine += " " + code.AbbrDesc;
        }
        else if (!StringSupport.equals(code.LaymanTerm, ""))
        {
            strLine += " " + code.LaymanTerm;
        }
        else
        {
            strLine += " " + code.Descript;
        }  
        return strLine;
    }

    /**
    * Used to display procedure descriptions on appointments. The returned string also includes surf and toothNum.
    */
    public static String getDescription(Procedure proc) throws Exception {
        return convertProcToString(proc.CodeNum,proc.Surf,proc.ToothNum,false);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Supply the list of procedures attached to the appointment.  It will loop through each and assign the correct provider.  Also sets clinic.  Also sets procDate for TP procs.  js 7/24/12 This is not supposed to be called if the appointment is complete.
    */
    public static void setProvidersInAppointment(Appointment apt, List<Procedure> procList) throws Exception {
        //No need to check RemotingRole; no call to db.
        ProcedureCode procCode;
        Procedure changedProc;
        for (int i = 0;i < procList.Count;i++)
        {
            changedProc = procList[i].Copy();
            procCode = ProcedureCodes.GetProcCode(procList[i].CodeNum);
            if (apt.ProvHyg != 0)
            {
                //if the appointment has a hygiene provider
                if (procCode.IsHygiene)
                {
                    //hygiene proc
                    changedProc.ProvNum = apt.ProvHyg;
                }
                else
                {
                    //dentist proc
                    changedProc.ProvNum = apt.ProvNum;
                } 
            }
            else
            {
                //same provider for every procedure
                changedProc.ProvNum = apt.ProvNum;
            } 
            if (procCode.ProvNumDefault != 0)
            {
                changedProc.ProvNum = procCode.ProvNumDefault;
            }
             
            //Override ProvNum if there is a default provider for procCode
            changedProc.ClinicNum = apt.ClinicNum;
            if (procList[i].ProcStatus == OpenDentBusiness.ProcStat.TP)
            {
                changedProc.ProcDate = apt.AptDateTime;
            }
             
            Procedures.Update(changedProc, procList[i]);
        }
    }

    //won't go to db unless a field has changed.
    /**
    * Gets a list of procedures representing extracted teeth.  Status of C,EC,orEO. Includes procs with toothNum "1"-"32".  Will not include procs with procdate before 1880.  Used for Canadian e-claims instead of the usual ToothInitials.GetMissingOrHiddenTeeth, because Canada requires dates on the extracted teeth.  Supply all procedures for the patient.
    */
    public static List<Procedure> getCanadianExtractedTeeth(List<Procedure> procList) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Procedure> extracted = new List<Procedure>();
        ProcedureCode procCode;
        for (int i = 0;i < procList.Count;i++)
        {
            if (procList[i].ProcStatus != OpenDentBusiness.ProcStat.C && procList[i].ProcStatus != OpenDentBusiness.ProcStat.EC && procList[i].ProcStatus != OpenDentBusiness.ProcStat.EO)
            {
                continue;
            }
             
            if (!Tooth.IsValidDB(procList[i].ToothNum))
            {
                continue;
            }
             
            if (Tooth.IsSuperNum(procList[i].ToothNum))
            {
                continue;
            }
             
            if (Tooth.IsPrimary(procList[i].ToothNum))
            {
                continue;
            }
             
            if (procList[i].ProcDate.Year < 1880)
            {
                continue;
            }
             
            procCode = ProcedureCodes.GetProcCode(procList[i].CodeNum);
            if (procCode.TreatArea != TreatmentArea.Tooth)
            {
                continue;
            }
             
            if (procCode.PaintType != ToothPaintingType.Extraction)
            {
                continue;
            }
             
        }
        return extracted;
    }

    /**
    * Takes the list of all procedures for the patient, and finds any that are attached as lab procs to that proc.
    */
    public static List<Procedure> getCanadianLabFees(long procNumLab, List<Procedure> procList) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Procedure> retVal = new List<Procedure>();
        if (procNumLab == 0)
        {
            return retVal;
        }
         
        for (int i = 0;i < procList.Count;i++)
        {
            //Ignore regular procedures.
            if (procList[i].ProcNumLab == procNumLab)
            {
                retVal.Add(procList[i]);
            }
             
        }
        return retVal;
    }

    /**
    * Pulls the lab fees for the given procnum directly from the database.
    */
    public static List<Procedure> getCanadianLabFees(long procNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Procedure>>GetObject(MethodBase.GetCurrentMethod(), procNum);
        }
         
        String command = "SELECT * FROM procedurelog WHERE ProcStatus<>" + POut.int(((Enum)OpenDentBusiness.ProcStat.D).ordinal()) + " AND ProcNumLab=" + POut.long(procNum);
        return ProcedureCrud.SelectMany(command);
    }

    /*
    		///<summary>InsEstTotal or override is retrieved from supplied claimprocs. Includes annual max and deductible.  The claimProc array typically includes all claimProcs for the patient, but must at least include the claimprocs for this proc that we need.  Will always return a meaningful value rather than -1.</summary>
    		public static double GetEst(Procedure proc,List<ClaimProc> claimProcs,int planNum) {
    			//No need to check RemotingRole; no call to db.
    			for(int i=0;i<claimProcs.Count;i++) {
    				//adjustments automatically ignored since no ProcNum
    				if(claimProcs[i].Status==ClaimProcStatus.CapClaim
    					||claimProcs[i].Status==ClaimProcStatus.Preauth
    					||claimProcs[i].Status==ClaimProcStatus.Supplemental) {
    					continue;
    				}
    				if(claimProcs[i].ProcNum!=proc.ProcNum) {
    					continue;
    				}
    				if(claimProcs[i].PlanNum!=planNum) {
    					continue;
    				}
    				if(claimProcs[i].InsEstTotalOverride != -1){
    					return claimProcs[i].InsEstTotalOverride;
    				}
    				return claimProcs[i].InsEstTotal;
    			}
    			return 0;
    		}*/
    /**
    * Only fees, not estimates.  Returns number of fees changed.
    */
    public static long globalUpdateFees() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT procedurecode.CodeNum,ProcNum,patient.PatNum,procedurelog.PatNum,\r\n" + 
        "\t\t\t\tinsplan.FeeSched AS PlanFeeSched,patient.FeeSched AS PatFeeSched,patient.PriProv,\r\n" + 
        "\t\t\t\tprocedurelog.ProcFee,insplan.PlanType\r\n" + 
        "\t\t\t\tFROM procedurelog\r\n" + 
        "\t\t\t\tLEFT JOIN patient ON patient.PatNum=procedurelog.PatNum\r\n" + 
        "\t\t\t\tLEFT JOIN patplan ON patplan.PatNum=procedurelog.PatNum\r\n" + 
        "\t\t\t\tAND patplan.Ordinal=1\r\n" + 
        "\t\t\t\tLEFT JOIN procedurecode ON procedurecode.CodeNum=procedurelog.CodeNum\r\n" + 
        "\t\t\t\tLEFT JOIN inssub ON inssub.InsSubNum=patplan.InsSubNum\r\n" + 
        "\t\t\t\tLEFT JOIN insplan ON insplan.PlanNum=inssub.PlanNum\r\n" + 
        "\t\t\t\tWHERE procedurelog.ProcStatus=1";
        /*@"SELECT procedurelog.ProcCode,insplan.FeeSched AS PlanFeeSched,patient.FeeSched AS PatFeeSched,
        							patient.PriProv,ProcNum
        							FROM procedurelog,patient
        							LEFT JOIN patplan ON patplan.PatNum=procedurelog.PatNum
        							AND patplan.Ordinal=1
        							LEFT JOIN insplan ON insplan.PlanNum=patplan.PlanNum
        							WHERE procedurelog.ProcStatus=1
        							AND patient.PatNum=procedurelog.PatNum
        						";*/
        DataTable table = Db.getTable(command);
        long priPlanFeeSched = new long();
        //int feeSchedNum;
        long patFeeSched = new long();
        long patProv = new long();
        String planType = new String();
        double insfee = new double();
        double standardfee = new double();
        double newFee = new double();
        double oldFee = new double();
        long rowsChanged = 0;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            priPlanFeeSched = PIn.Long(table.Rows[i]["PlanFeeSched"].ToString());
            patFeeSched = PIn.Long(table.Rows[i]["PatFeeSched"].ToString());
            patProv = PIn.Long(table.Rows[i]["PriProv"].ToString());
            planType = PIn.String(table.Rows[i]["PlanType"].ToString());
            insfee = Fees.GetAmount0(PIn.Long(table.Rows[i]["CodeNum"].ToString()), Fees.getFeeSched(priPlanFeeSched,patFeeSched,patProv));
            if (StringSupport.equals(planType, "p"))
            {
                //PPO
                standardfee = Fees.GetAmount0(PIn.Long(table.Rows[i]["CodeNum"].ToString()), Providers.getProv(patProv).FeeSched);
                if (standardfee > insfee)
                {
                    newFee = standardfee;
                }
                else
                {
                    newFee = insfee;
                } 
            }
            else
            {
                newFee = insfee;
            } 
            oldFee = PIn.Double(table.Rows[i]["ProcFee"].ToString());
            if (newFee == oldFee)
            {
                continue;
            }
             
            command = "UPDATE procedurelog SET ProcFee='" + POut.double(newFee) + "' " + "WHERE ProcNum=" + table.Rows[i]["ProcNum"].ToString();
            rowsChanged += Db.nonQ(command);
        }
        return rowsChanged;
    }

    /**
    * Used from TP to get a list of all TP procs, ordered by priority, toothnum.
    */
    public static Procedure[] getListTP(List<Procedure> procList) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList AL = new ArrayList();
        for (int i = 0;i < procList.Count;i++)
        {
            if (procList[i].ProcStatus == OpenDentBusiness.ProcStat.TP)
            {
                AL.Add(procList[i]);
            }
             
        }
        IComparer myComparer = new ProcedureComparer();
        AL.Sort(myComparer);
        Procedure[] retVal = new Procedure[AL.Count];
        AL.CopyTo(retVal);
        return retVal;
    }

    public static void computeEstimates(Procedure proc, long patNum, List<ClaimProc> claimProcs, boolean isInitialEntry, List<InsPlan> PlanList, List<PatPlan> patPlans, List<Benefit> benefitList, int patientAge, List<InsSub> subList) throws Exception {
        //This is a stub that needs revision.
        RefSupport<List<ClaimProc>> refVar___0 = new RefSupport<List<ClaimProc>>(claimProcs);
        ComputeEstimates(proc, patNum, refVar___0, isInitialEntry, PlanList, patPlans, benefitList, null, null, true, patientAge, subList);
        claimProcs = refVar___0.getValue();
    }

    /**
    * Used whenever a procedure changes or a plan changes.  All estimates for a given procedure must be updated. This frequently includes adding claimprocs, but can also just edit the appropriate existing claimprocs. Skips status=Adjustment,CapClaim,Preauth,Supplemental.  Also fixes date,status,and provnum if appropriate.  The claimProc list only needs to include claimprocs for this proc, although it can include more.  Only set isInitialEntry true from Chart module; it is for cap procs.  loopList only contains information about procedures that come before this one in a list such as TP or claim.
    */
    public static void computeEstimates(Procedure proc, long patNum, RefSupport<List<ClaimProc>> claimProcs, boolean isInitialEntry, List<InsPlan> PlanList, List<PatPlan> patPlans, List<Benefit> benefitList, List<ClaimProcHist> histList, List<ClaimProcHist> loopList, boolean saveToDb, int patientAge, List<InsSub> subList) throws Exception {
        //No need to check RemotingRole; no call to db.
        boolean isHistorical = false;
        if (proc.ProcDate < DateTime.Today && proc.ProcStatus == OpenDentBusiness.ProcStat.C)
        {
            isHistorical = true;
            for (int i = 0;i < PlanList.Count;i++)
            {
                //Don't automatically create an estimate for completed procedures, especially if they are older than today.  Very important after a conversion from another software.
                //Special logic in place only for capitation plans:
                if (!StringSupport.equals(PlanList[i].PlanType, "c"))
                {
                    continue;
                }
                 
                //11/19/2012 js We had a specific complaint where changing plan type to capitation automatically added WOs to historical procs.
                //04/02/2013 Jason- To relax this filter for offices that enter treatment a few days after it's done, we will loop through all the claimprocs and see if any capitation statuses exist.
                boolean hasCapClaimProcs = false;
                for (int j = 0;j < claimProcs.getValue().Count;j++)
                {
                    if (claimProcs.getValue()[j].ProcNum != proc.ProcNum)
                    {
                        continue;
                    }
                     
                    //Check if this claimproc is capitation.
                    if (claimProcs.getValue()[j].Status == OpenDentBusiness.ClaimProcStatus.CapClaim || claimProcs.getValue()[j].Status == OpenDentBusiness.ClaimProcStatus.CapComplete || claimProcs.getValue()[j].Status == OpenDentBusiness.ClaimProcStatus.CapEstimate)
                    {
                        hasCapClaimProcs = true;
                        break;
                    }
                     
                }
                if (!hasCapClaimProcs)
                {
                    return ;
                }
                 
            }
        }
         
        for (int i = 0;i < claimProcs.getValue().Count;i++)
        {
            //There are no capitation claimprocs for this procedure, therefore we don't want to touch/damage this proc.
            //first test to see if each estimate matches an existing patPlan (current coverage),
            //delete any other estimates
            if (claimProcs.getValue()[i].ProcNum != proc.ProcNum)
            {
                continue;
            }
             
            if (claimProcs.getValue()[i].PlanNum == 0)
            {
                continue;
            }
             
            if (claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.CapClaim || claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.Preauth || claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.Supplemental)
            {
                continue;
            }
             
            //ignored: adjustment
            //included: capComplete,CapEstimate,Estimate,NotReceived,Received
            if (claimProcs.getValue()[i].Status != OpenDentBusiness.ClaimProcStatus.Estimate && claimProcs.getValue()[i].Status != OpenDentBusiness.ClaimProcStatus.CapEstimate)
            {
                continue;
            }
             
            boolean planIsCurrent = false;
            for (int p = 0;p < patPlans.Count;p++)
            {
                if (patPlans[p].InsSubNum == claimProcs.getValue()[i].InsSubNum && InsSubs.GetSub(patPlans[p].InsSubNum, subList).PlanNum == claimProcs.getValue()[i].PlanNum)
                {
                    planIsCurrent = true;
                    break;
                }
                 
            }
            //If claimProc estimate is for a plan that is not current, delete it
            if (!planIsCurrent)
            {
                if (saveToDb)
                {
                    ClaimProcs.Delete(claimProcs.getValue()[i]);
                }
                else
                {
                    claimProcs.getValue()[i].DoDelete = true;
                } 
            }
             
        }
        InsPlan PlanCur;
        InsSub SubCur;
        boolean estExists = new boolean();
        boolean cpAdded = false;
        for (int p = 0;p < patPlans.Count;p++)
        {
            //loop through all patPlans (current coverage), and add any missing estimates
            //typically, loop will only have length of 1 or 2
            if (isHistorical)
            {
                break;
            }
             
            //test to see if estimate exists
            estExists = false;
            for (int i = 0;i < claimProcs.getValue().Count;i++)
            {
                if (claimProcs.getValue()[i].ProcNum != proc.ProcNum)
                {
                    continue;
                }
                 
                if (claimProcs.getValue()[i].PlanNum == 0)
                {
                    continue;
                }
                 
                if (claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.CapClaim || claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.Preauth || claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.Supplemental)
                {
                    continue;
                }
                 
                //ignored: adjustment
                //included: capComplete,CapEstimate,Estimate,NotReceived,Received
                //if(patPlans[p].PlanNum!=claimProcs[i].PlanNum) {
                if (patPlans[p].InsSubNum != claimProcs.getValue()[i].InsSubNum)
                {
                    continue;
                }
                 
                estExists = true;
                break;
            }
            if (estExists)
            {
                continue;
            }
             
            //estimate is missing, so add it.
            ClaimProc cp = new ClaimProc();
            cp.ProcNum = proc.ProcNum;
            cp.PatNum = patNum;
            cp.ProvNum = proc.ProvNum;
            SubCur = InsSubs.GetSub(patPlans[p].InsSubNum, subList);
            PlanCur = InsPlans.GetPlan(SubCur.PlanNum, PlanList);
            if (PlanCur == null || SubCur == null)
            {
                continue;
            }
             
            //??
            //??
            if (StringSupport.equals(PlanCur.PlanType, "c"))
            {
                if (proc.ProcStatus == OpenDentBusiness.ProcStat.C)
                {
                    cp.Status = OpenDentBusiness.ClaimProcStatus.CapComplete;
                }
                else
                {
                    cp.Status = OpenDentBusiness.ClaimProcStatus.CapEstimate;
                } 
            }
            else
            {
                //this may be changed below
                cp.Status = OpenDentBusiness.ClaimProcStatus.Estimate;
            } 
            cp.PlanNum = PlanCur.PlanNum;
            cp.InsSubNum = SubCur.InsSubNum;
            cp.DateCP = proc.ProcDate;
            cp.AllowedOverride = -1;
            cp.PercentOverride = -1;
            cp.NoBillIns = ProcedureCodes.getProcCode(proc.CodeNum).NoBillIns;
            cp.PaidOtherIns = -1;
            cp.CopayOverride = -1;
            cp.ProcDate = proc.ProcDate;
            cp.BaseEst = 0;
            cp.InsEstTotal = 0;
            cp.InsEstTotalOverride = -1;
            cp.DedEst = -1;
            cp.DedEstOverride = -1;
            cp.PaidOtherInsOverride = -1;
            cp.WriteOffEst = -1;
            cp.WriteOffEstOverride = -1;
            //ComputeBaseEst will fill AllowedOverride,Percentage,CopayAmt,BaseEst
            if (saveToDb)
            {
                ClaimProcs.insert(cp);
            }
            else
            {
                claimProcs.getValue().Add(cp);
            } 
            //this newly added cp has not ClaimProcNum and is not yet in the db.
            cpAdded = true;
        }
        //if any were added, refresh the list
        if (cpAdded && saveToDb)
        {
            //no need to refresh the list if !saveToDb, because list already made current.
            claimProcs.setValue(ClaimProcs.refresh(patNum));
        }
         
        double paidOtherInsEstTotal = 0;
        double paidOtherInsBaseEst = 0;
        double writeOffEstOtherIns = 0;
        //because secondary claimproc might come before primary claimproc in the list, we cannot simply loop through the claimprocs
        RefSupport<double> refVar___1 = new RefSupport<double>(paidOtherInsEstTotal);
        RefSupport<double> refVar___2 = new RefSupport<double>(paidOtherInsBaseEst);
        RefSupport<double> refVar___3 = new RefSupport<double>(writeOffEstOtherIns);
        ComputeForOrdinal(1, claimProcs.getValue(), proc, PlanList, isInitialEntry, refVar___1, refVar___2, refVar___3, patPlans, benefitList, histList, loopList, saveToDb, patientAge);
        paidOtherInsEstTotal = refVar___1.getValue();
        paidOtherInsBaseEst = refVar___2.getValue();
        writeOffEstOtherIns = refVar___3.getValue();
        RefSupport<double> refVar___4 = new RefSupport<double>(paidOtherInsEstTotal);
        RefSupport<double> refVar___5 = new RefSupport<double>(paidOtherInsBaseEst);
        RefSupport<double> refVar___6 = new RefSupport<double>(writeOffEstOtherIns);
        ComputeForOrdinal(2, claimProcs.getValue(), proc, PlanList, isInitialEntry, refVar___4, refVar___5, refVar___6, patPlans, benefitList, histList, loopList, saveToDb, patientAge);
        paidOtherInsEstTotal = refVar___4.getValue();
        paidOtherInsBaseEst = refVar___5.getValue();
        writeOffEstOtherIns = refVar___6.getValue();
        RefSupport<double> refVar___7 = new RefSupport<double>(paidOtherInsEstTotal);
        RefSupport<double> refVar___8 = new RefSupport<double>(paidOtherInsBaseEst);
        RefSupport<double> refVar___9 = new RefSupport<double>(writeOffEstOtherIns);
        ComputeForOrdinal(3, claimProcs.getValue(), proc, PlanList, isInitialEntry, refVar___7, refVar___8, refVar___9, patPlans, benefitList, histList, loopList, saveToDb, patientAge);
        paidOtherInsEstTotal = refVar___7.getValue();
        paidOtherInsBaseEst = refVar___8.getValue();
        writeOffEstOtherIns = refVar___9.getValue();
        RefSupport<double> refVar___10 = new RefSupport<double>(paidOtherInsEstTotal);
        RefSupport<double> refVar___11 = new RefSupport<double>(paidOtherInsBaseEst);
        RefSupport<double> refVar___12 = new RefSupport<double>(writeOffEstOtherIns);
        ComputeForOrdinal(4, claimProcs.getValue(), proc, PlanList, isInitialEntry, refVar___10, refVar___11, refVar___12, patPlans, benefitList, histList, loopList, saveToDb, patientAge);
        paidOtherInsEstTotal = refVar___10.getValue();
        paidOtherInsBaseEst = refVar___11.getValue();
        writeOffEstOtherIns = refVar___12.getValue();
        //At this point, for a PPO with secondary, the sum of all estimates plus primary writeoff might be greater than fee.
        if (patPlans.Count > 1)
        {
            SubCur = InsSubs.GetSub(patPlans[0].InsSubNum, subList);
            PlanCur = InsPlans.GetPlan(SubCur.PlanNum, PlanList);
            if (StringSupport.equals(PlanCur.PlanType, "p"))
            {
                //claimProcs=ClaimProcs.Refresh(patNum);
                //ClaimProc priClaimProc=null;
                int priClaimProcIdx = -1;
                double sumPay = 0;
                for (int i = 0;i < claimProcs.getValue().Count;i++)
                {
                    //Either actual or estimate
                    if (claimProcs.getValue()[i].ProcNum != proc.ProcNum)
                    {
                        continue;
                    }
                     
                    if (claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.Adjustment || claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.CapClaim || claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.CapComplete || claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.CapEstimate || claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.Preauth)
                    {
                        continue;
                    }
                     
                    if (claimProcs.getValue()[i].PlanNum == PlanCur.PlanNum && claimProcs.getValue()[i].WriteOffEst > 0)
                    {
                        priClaimProcIdx = i;
                    }
                     
                    if (claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.Received || claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.Supplemental)
                    {
                        sumPay += claimProcs.getValue()[i].InsPayAmt;
                    }
                     
                    if (claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.Estimate)
                    {
                        if (claimProcs.getValue()[i].InsEstTotalOverride != -1)
                        {
                            sumPay += claimProcs.getValue()[i].InsEstTotalOverride;
                        }
                        else
                        {
                            sumPay += claimProcs.getValue()[i].InsEstTotal;
                        } 
                    }
                     
                    if (claimProcs.getValue()[i].Status == OpenDentBusiness.ClaimProcStatus.NotReceived)
                    {
                        sumPay += claimProcs.getValue()[i].InsPayEst;
                    }
                     
                }
                //Alter primary WO if needed.
                if (priClaimProcIdx != -1)
                {
                    if (sumPay + claimProcs.getValue()[priClaimProcIdx].WriteOffEst > proc.ProcFee)
                    {
                        double writeOffEst = proc.ProcFee - sumPay;
                        if (writeOffEst < 0)
                        {
                            writeOffEst = 0;
                        }
                         
                        claimProcs.getValue()[priClaimProcIdx].WriteOffEst = writeOffEst;
                        if (saveToDb)
                        {
                            ClaimProcs.Update(claimProcs.getValue()[priClaimProcIdx]);
                        }
                         
                    }
                     
                }
                 
            }
             
        }
         
    }

    /**
    * Passing in 4 will compute for 4 as well as any other situation such as dropped plan.
    */
    private static void computeForOrdinal(int ordinal, List<ClaimProc> claimProcs, Procedure proc, List<InsPlan> PlanList, boolean isInitialEntry, RefSupport<double> paidOtherInsEstTotal, RefSupport<double> paidOtherInsBaseEst, RefSupport<double> writeOffEstOtherIns, List<PatPlan> patPlans, List<Benefit> benefitList, List<ClaimProcHist> histList, List<ClaimProcHist> loopList, boolean saveToDb, int patientAge) throws Exception {
        //No need to check RemotingRole; no call to db.
        InsPlan PlanCur;
        PatPlan patplan;
        for (int i = 0;i < claimProcs.Count;i++)
        {
            if (claimProcs[i].ProcNum != proc.ProcNum)
            {
                continue;
            }
             
            PlanCur = InsPlans.GetPlan(claimProcs[i].PlanNum, PlanList);
            if (PlanCur == null)
            {
                continue;
            }
             
            //in older versions it still did a couple of small things even if plan was null, but don't know why
            //example:cap estimate changed to cap complete, and if estimate, then provnum set
            //but I don't see how PlanCur could ever be null
            patplan = PatPlans.GetFromList(patPlans, claimProcs[i].InsSubNum);
            //capitation estimates are always forced to follow the status of the procedure
            if (StringSupport.equals(PlanCur.PlanType, "c") && (claimProcs[i].Status == OpenDentBusiness.ClaimProcStatus.CapComplete || claimProcs[i].Status == OpenDentBusiness.ClaimProcStatus.CapEstimate))
            {
                if (isInitialEntry)
                {
                    //this will be switched to CapComplete further down if applicable.
                    //This makes ComputeBaseEst work properly on new cap procs w status Complete
                    claimProcs[i].Status = OpenDentBusiness.ClaimProcStatus.CapEstimate;
                }
                else if (proc.ProcStatus == OpenDentBusiness.ProcStat.C)
                {
                    claimProcs[i].Status = OpenDentBusiness.ClaimProcStatus.CapComplete;
                }
                else
                {
                    claimProcs[i].Status = OpenDentBusiness.ClaimProcStatus.CapEstimate;
                }  
            }
             
            //ignored: adjustment
            //ComputeBaseEst automatically skips: capComplete,Preauth,capClaim,Supplemental
            //does recalc est on: CapEstimate,Estimate,NotReceived,Received
            //the cp is altered within ComputeBaseEst, but not saved.
            if (patplan == null)
            {
                //the plan for this claimproc was dropped
                if (ordinal != 4)
                {
                    continue;
                }
                 
                //only process on the fourth round
                ClaimProcs.ComputeBaseEst(claimProcs[i], proc.ProcFee, proc.ToothNum, proc.CodeNum, PlanCur, 0, benefitList, histList, loopList, patPlans, 0, 0, patientAge, 0);
            }
            else if (patplan.Ordinal == 1)
            {
                if (ordinal != 1)
                {
                    continue;
                }
                 
                ClaimProcs.ComputeBaseEst(claimProcs[i], proc.ProcFee, proc.ToothNum, proc.CodeNum, PlanCur, patplan.PatPlanNum, benefitList, histList, loopList, patPlans, paidOtherInsEstTotal.getValue(), paidOtherInsBaseEst.getValue(), patientAge, writeOffEstOtherIns.getValue());
                paidOtherInsEstTotal.setValue(paidOtherInsEstTotal.getValue() + claimProcs[i].InsEstTotal);
                paidOtherInsBaseEst.setValue(paidOtherInsBaseEst.getValue() + claimProcs[i].BaseEst);
                writeOffEstOtherIns.setValue(writeOffEstOtherIns.getValue() + ClaimProcs.GetWriteOffEstimate(claimProcs[i]));
            }
            else if (patplan.Ordinal == 2)
            {
                if (ordinal != 2)
                {
                    continue;
                }
                 
                ClaimProcs.ComputeBaseEst(claimProcs[i], proc.ProcFee, proc.ToothNum, proc.CodeNum, PlanCur, patplan.PatPlanNum, benefitList, histList, loopList, patPlans, paidOtherInsEstTotal.getValue(), paidOtherInsBaseEst.getValue(), patientAge, writeOffEstOtherIns.getValue());
                paidOtherInsEstTotal.setValue(paidOtherInsEstTotal.getValue() + claimProcs[i].InsEstTotal);
                paidOtherInsBaseEst.setValue(paidOtherInsBaseEst.getValue() + claimProcs[i].BaseEst);
                writeOffEstOtherIns.setValue(writeOffEstOtherIns.getValue() + ClaimProcs.GetWriteOffEstimate(claimProcs[i]));
            }
            else if (patplan.Ordinal == 3)
            {
                if (ordinal != 3)
                {
                    continue;
                }
                 
                ClaimProcs.ComputeBaseEst(claimProcs[i], proc.ProcFee, proc.ToothNum, proc.CodeNum, PlanCur, patplan.PatPlanNum, benefitList, histList, loopList, patPlans, paidOtherInsEstTotal.getValue(), paidOtherInsBaseEst.getValue(), patientAge, writeOffEstOtherIns.getValue());
                paidOtherInsEstTotal.setValue(paidOtherInsEstTotal.getValue() + claimProcs[i].InsEstTotal);
                paidOtherInsBaseEst.setValue(paidOtherInsBaseEst.getValue() + claimProcs[i].BaseEst);
                writeOffEstOtherIns.setValue(writeOffEstOtherIns.getValue() + ClaimProcs.GetWriteOffEstimate(claimProcs[i]));
            }
            else
            {
                //patplan.Ordinal is 4 or greater.  Estimate won't be accurate if more than 4 insurances.
                if (ordinal != 4)
                {
                    continue;
                }
                 
                ClaimProcs.ComputeBaseEst(claimProcs[i], proc.ProcFee, proc.ToothNum, proc.CodeNum, PlanCur, patplan.PatPlanNum, benefitList, histList, loopList, patPlans, paidOtherInsEstTotal.getValue(), paidOtherInsBaseEst.getValue(), patientAge, writeOffEstOtherIns.getValue());
            }    
            //This was a longstanding bug. I hope there are not other consequences for commenting it out.
            //claimProcs[i].DateCP=proc.ProcDate;
            claimProcs[i].ProcDate = proc.ProcDate;
            claimProcs[i].ClinicNum = proc.ClinicNum;
            //Wish we could do this, but it might change history.  It's needed when changing a completed proc to a different provider.
            //Can't do it here, though, because some people intentionally set provider different on claimprocs.
            //claimProcs[i].ProvNum=proc.ProvNum;
            if (isInitialEntry && claimProcs[i].Status == OpenDentBusiness.ClaimProcStatus.CapEstimate && proc.ProcStatus == OpenDentBusiness.ProcStat.C)
            {
                claimProcs[i].Status = OpenDentBusiness.ClaimProcStatus.CapComplete;
            }
             
            //prov only updated if still an estimate
            if (claimProcs[i].Status == OpenDentBusiness.ClaimProcStatus.Estimate || claimProcs[i].Status == OpenDentBusiness.ClaimProcStatus.CapEstimate)
            {
                claimProcs[i].ProvNum = proc.ProvNum;
            }
             
            if (saveToDb)
            {
                ClaimProcs.Update(claimProcs[i]);
            }
             
        }
    }

    /**
    * After changing important coverage plan info, this is called to recompute estimates for all procedures for this patient.
    */
    public static void computeEstimatesForAll(long patNum, List<ClaimProc> claimProcs, List<Procedure> procs, List<InsPlan> PlanList, List<PatPlan> patPlans, List<Benefit> benefitList, int patientAge, List<InsSub> subList) throws Exception {
        for (int i = 0;i < procs.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            ComputeEstimates(procs[i], patNum, claimProcs, false, PlanList, patPlans, benefitList, patientAge, subList);
        }
    }

    /**
    * Loops through each proc. Does not add notes to a procedure that already has notes. Used three times, security checked in all three places before calling this.  Also sets provider for each proc and claimproc.
    */
    public static void setCompleteInAppt(Appointment apt, List<InsPlan> PlanList, List<PatPlan> patPlans, long siteNum, int patientAge, List<Procedure> procsInAppt, List<InsSub> subList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), apt, PlanList, patPlans, siteNum, patientAge, procsInAppt, subList);
            return ;
        }
         
        List<Procedure> ProcList = Procedures.refresh(apt.PatNum);
        List<ClaimProc> ClaimProcList = ClaimProcs.refresh(apt.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        //this query could be improved slightly to only get notes of interest.
        String command = "SELECT * FROM procnote WHERE PatNum=" + POut.long(apt.PatNum) + " ORDER BY EntryDateTime";
        DataTable rawNotes = Db.getTable(command);
        //CovPats.Refresh(PlanList,patPlans);
        //bool doResetRecallStatus=false;
        ProcedureCode procCode;
        Procedure oldProc;
        //int siteNum=0;
        //if(!PrefC.GetBool(PrefName.EasyHidePublicHealth")){
        //	siteNum=Patients.GetPat(apt.PatNum).SiteNum;
        //}
        List<long> encounterProvNums = new List<long>();
        for (int i = 0;i < ProcList.Count;i++)
        {
            //for auto-inserting default encounters
            if (ProcList[i].AptNum != apt.AptNum)
            {
                continue;
            }
             
            for (int n = rawNotes.Rows.Count - 1;n >= 0;n--)
            {
                //if(ProcList[i].ProcStatus==ProcStat.C) {//if the procedure is already complete, don't touch it.
                //too severe
                //}
                //attach the note, if it exists.
                //loop through each note, backwards.
                if (ProcList[i].ProcNum.ToString() != rawNotes.Rows[n]["ProcNum"].ToString())
                {
                    continue;
                }
                 
                ProcList[i].UserNum = PIn.Long(rawNotes.Rows[n]["UserNum"].ToString());
                ProcList[i].Note = PIn.String(rawNotes.Rows[n]["Note"].ToString());
                ProcList[i].SigIsTopaz = PIn.Bool(rawNotes.Rows[n]["SigIsTopaz"].ToString());
                ProcList[i].Signature = PIn.String(rawNotes.Rows[n]["Signature"].ToString());
                break;
            }
            //out of note loop.
            oldProc = ProcList[i].Copy();
            procCode = ProcedureCodes.GetProcCode(ProcList[i].CodeNum);
            if (procCode.PaintType == ToothPaintingType.Extraction)
            {
                //if an extraction, then mark previous procs hidden
                //SetHideGraphical(ProcList[i]);//might not matter anymore
                ToothInitials.SetValue(apt.PatNum, ProcList[i].ToothNum, ToothInitialType.Missing);
            }
             
            ProcList[i].ProcStatus = OpenDentBusiness.ProcStat.C;
            if (oldProc.ProcStatus != OpenDentBusiness.ProcStat.C)
            {
                ProcList[i].ProcDate = apt.AptDateTime.Date;
                //only change date to match appt if not already complete.
                ProcList[i].DateEntryC = DateTime.Now;
                //this triggers it to set to server time NOW().
                if (StringSupport.equals(ProcList[i].DiagnosticCode, ""))
                {
                    ProcList[i].DiagnosticCode = PrefC.getString(PrefName.ICD9DefaultForNewProcs);
                }
                 
            }
             
            ProcList[i].PlaceService = (PlaceOfService)PrefC.getLong(PrefName.DefaultProcedurePlaceService);
            ProcList[i].ClinicNum = apt.ClinicNum;
            ProcList[i].SiteNum = siteNum;
            ProcList[i].PlaceService = Clinics.getPlaceService(apt.ClinicNum);
            if (apt.ProvHyg != 0)
            {
                //if the appointment has a hygiene provider
                if (procCode.IsHygiene)
                {
                    //hyg proc
                    ProcList[i].ProvNum = apt.ProvHyg;
                }
                else
                {
                    //regular proc
                    ProcList[i].ProvNum = apt.ProvNum;
                } 
            }
            else
            {
                //same provider for every procedure
                ProcList[i].ProvNum = apt.ProvNum;
            } 
            if (procCode.ProvNumDefault != 0)
            {
                //Override provider for procedures with a default provider
                ProcList[i].ProvNum = procCode.ProvNumDefault;
            }
             
            //if procedure was already complete, then don't add more notes.
            if (oldProc.ProcStatus != OpenDentBusiness.ProcStat.C)
            {
                ProcList[i].Note += ProcCodeNotes.GetNote(ProcList[i].ProvNum, ProcList[i].CodeNum);
            }
             
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canada
                Procedures.SetCanadianLabFeesCompleteForProc(ProcList[i]);
            }
             
            if (RemotingClient.RemotingRole == RemotingRole.ClientDirect)
            {
                //this is a temporary safe fix
                Plugins.hookAddCode(null,"Procedures.SetCompleteInAppt_procLoop",ProcList[i],oldProc);
            }
             
            Procedures.Update(ProcList[i], oldProc);
            Procedures.ComputeEstimates(ProcList[i], apt.PatNum, ClaimProcList, false, PlanList, patPlans, benefitList, patientAge, subList);
            ClaimProcs.SetProvForProc(ProcList[i], ClaimProcList);
            //Add provnum to list to create an encounter later. Done to limit calls to DB from Encounters.InsertDefaultEncounter().
            if (oldProc.ProcStatus != OpenDentBusiness.ProcStat.C && !encounterProvNums.Contains(ProcList[i].ProvNum))
            {
                encounterProvNums.Add(ProcList[i].ProvNum);
            }
             
        }
        for (int j = 0;j < encounterProvNums.Count;j++)
        {
            //Auto-insert default encounters for the providers that did work on this appointment
            Encounters.InsertDefaultEncounter(apt.PatNum, encounterProvNums[j], apt.AptDateTime);
        }
        //if(doResetRecallStatus){
        //	Recalls.Reset(apt.PatNum);//this also synchs recall
        //}
        Recalls.synch(apt.PatNum);
    }

    //Patient pt=Patients.GetPat(apt.PatNum);
    //jsparks-See notes within this method:
    //Reporting.Allocators.AllocatorCollection.CallAll_Allocators(pt.Guarantor);
    /**
    * 
    */
    public static long getClinicNum(long procNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), procNum);
        }
         
        String command = "SELECT ClinicNum FROM procedurelog WHERE ProcNum=" + POut.long(procNum);
        return PIn.long(Db.getScalar(command));
    }

    public static boolean isUsingCode(long codeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), codeNum);
        }
         
        String command = "SELECT COUNT(*) FROM procedurelog WHERE CodeNum=" + POut.long(codeNum);
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    public static void setCanadianLabFeesCompleteForProc(Procedure proc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), proc);
            return ;
        }
         
        //If this gets run on a lab fee itself, nothing will happen because result will be zero procs.
        String command = "SELECT * FROM procedurelog WHERE ProcNumLab=" + proc.ProcNum;
        List<Procedure> labFeesForProc = Crud.ProcedureCrud.SelectMany(command);
        for (int i = 0;i < labFeesForProc.Count;i++)
        {
            Procedure labFeeNew = labFeesForProc[i];
            Procedure labFeeOld = labFeeNew.copy();
            labFeeNew.AptNum = proc.AptNum;
            labFeeNew.CanadianTypeCodes = proc.CanadianTypeCodes;
            labFeeNew.ClinicNum = proc.ClinicNum;
            labFeeNew.DateEntryC = proc.DateEntryC;
            labFeeNew.PlaceService = proc.PlaceService;
            labFeeNew.ProcDate = proc.ProcDate;
            labFeeNew.ProcStatus = OpenDentBusiness.ProcStat.C;
            labFeeNew.ProvNum = proc.ProvNum;
            labFeeNew.SiteNum = proc.SiteNum;
            labFeeNew.UserNum = proc.UserNum;
            Procedures.update(labFeeNew,labFeeOld);
        }
    }

    /**
    * Gets the number of procedures attached to a claim.
    */
    public static int getCountForClaim(long claimNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), claimNum);
        }
         
        String command = "SELECT COUNT(*) FROM procedurelog " + "WHERE ProcNum IN " + "(SELECT claimproc.ProcNum FROM claimproc " + " WHERE ClaimNum=" + claimNum + ")";
        return PIn.int(Db.getCount(command));
    }

    /**
    * Gets a list of procedures for
    */
    public static DataTable getReferred(DateTime dateFrom, DateTime dateTo, boolean complete) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), dateFrom, dateTo, complete);
        }
         
        String command = "SELECT procedurelog.CodeNum,procedurelog.PatNum,LName,FName,MName,RefDate,DateProcComplete,refattach.Note,RefToStatus " + "FROM procedurelog " + "JOIN refattach ON procedurelog.ProcNum=refattach.ProcNum " + "JOIN referral ON refattach.ReferralNum=referral.ReferralNum " + "WHERE RefDate>=" + POut.date(dateFrom) + " " + "AND RefDate<=" + POut.date(dateTo) + " ";
        if (!complete)
        {
            command += "AND DateProcComplete=" + POut.Date(DateTime.MinValue) + " ";
        }
         
        command += "ORDER BY RefDate";
        return Db.getTable(command);
    }

    /**
    * 
    */
    public static void lock(DateTime date1, DateTime date2) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), date1, date2);
            return ;
        }
         
        //completed
        //or group note
        String command = "UPDATE procedurelog SET IsLocked=1 " + "WHERE (ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "OR CodeNum=" + POut.long(ProcedureCodes.getCodeNum(ProcedureCodes.GroupProcCode)) + ") " + "AND ProcDate >= " + POut.date(date1) + " " + "AND ProcDate <= " + POut.date(date2);
        Db.nonQ(command);
    }

}


