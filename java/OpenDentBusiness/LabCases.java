//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.Db;
import OpenDentBusiness.LabCase;
import OpenDentBusiness.LabCaseComparer;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PatientLogic;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetTypeEnum;

/**
* 
*/
public class LabCases   
{
    /**
    * Gets a filtered list of all labcases.
    */
    public static DataTable refresh(DateTime aptStartDate, DateTime aptEndDate, boolean showCompleted, boolean ShowUnattached) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), aptStartDate, aptEndDate, showCompleted, ShowUnattached);
        }
         
        DataTable table = new DataTable();
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("AptDateTime", DateTime.class);
        table.Columns.Add("aptDateTime");
        table.Columns.Add("AptNum");
        table.Columns.Add("lab");
        table.Columns.Add("LabCaseNum");
        table.Columns.Add("patient");
        table.Columns.Add("phone");
        table.Columns.Add("ProcDescript");
        table.Columns.Add("status");
        table.Columns.Add("Instructions");
        List<DataRow> rows = new List<DataRow>();
        //the first query only gets labcases that are attached to scheduled appointments
        String command = "SELECT AptDateTime,appointment.AptNum,DateTimeChecked,DateTimeRecd,DateTimeSent," + "LabCaseNum,laboratory.Description,LName,FName,Preferred,MiddleI,Phone,ProcDescript,Instructions " + "FROM labcase " + "LEFT JOIN appointment ON labcase.AptNum=appointment.AptNum " + "LEFT JOIN patient ON labcase.PatNum=patient.PatNum " + "LEFT JOIN laboratory ON labcase.LaboratoryNum=laboratory.LaboratoryNum " + "WHERE AptDateTime > " + POut.date(aptStartDate) + " " + "AND AptDateTime < " + POut.Date(aptEndDate.AddDays(1)) + " ";
        if (!showCompleted)
        {
            command += "AND (AptStatus=" + POut.Long(((Enum)ApptStatus.ASAP).ordinal()) + " OR AptStatus=" + POut.Long(((Enum)ApptStatus.Broken).ordinal()) + " OR AptStatus=" + POut.Long(((Enum)ApptStatus.None).ordinal()) + " OR AptStatus=" + POut.Long(((Enum)ApptStatus.Scheduled).ordinal()) + " OR AptStatus=" + POut.Long(((Enum)ApptStatus.UnschedList).ordinal()) + ") ";
        }
         
        DataTable raw = Db.getTable(command);
        DateTime AptDateTime = new DateTime();
        DateTime date = new DateTime();
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            row = table.NewRow();
            AptDateTime = PIn.DateT(raw.Rows[i]["AptDateTime"].ToString());
            row["AptDateTime"] = AptDateTime;
            row["aptDateTime"] = AptDateTime.ToShortDateString() + " " + AptDateTime.ToShortTimeString();
            row["AptNum"] = raw.Rows[i]["AptNum"].ToString();
            row["lab"] = raw.Rows[i]["Description"].ToString();
            row["LabCaseNum"] = raw.Rows[i]["LabCaseNum"].ToString();
            row["patient"] = PatientLogic.GetNameLF(raw.Rows[i]["LName"].ToString(), raw.Rows[i]["FName"].ToString(), raw.Rows[i]["Preferred"].ToString(), raw.Rows[i]["MiddleI"].ToString());
            row["phone"] = raw.Rows[i]["Phone"].ToString();
            row["ProcDescript"] = raw.Rows[i]["ProcDescript"].ToString();
            row["Instructions"] = raw.Rows[i]["Instructions"].ToString();
            date = PIn.DateT(raw.Rows[i]["DateTimeChecked"].ToString());
            if (date.Year > 1880)
            {
                row["status"] = Lans.g("FormLabCases","Quality Checked");
            }
            else
            {
                date = PIn.DateT(raw.Rows[i]["DateTimeRecd"].ToString());
                if (date.Year > 1880)
                {
                    row["status"] = Lans.g("FormLabCases","Received");
                }
                else
                {
                    date = PIn.DateT(raw.Rows[i]["DateTimeSent"].ToString());
                    if (date.Year > 1880)
                    {
                        row["status"] = Lans.g("FormLabCases","Sent");
                    }
                    else
                    {
                        //sent but not received
                        row["status"] = Lans.g("FormLabCases","Not Sent");
                    } 
                } 
            } 
            rows.Add(row);
        }
        if (ShowUnattached)
        {
            //Then, this second query gets labcases not attached to appointments.  No date filter.  No date displayed.
            command = "SELECT DateTimeChecked,DateTimeRecd,DateTimeSent," + "LabCaseNum,laboratory.Description,LName,FName,Preferred,MiddleI,Phone,Instructions " + "FROM labcase " + "LEFT JOIN patient ON labcase.PatNum=patient.PatNum " + "LEFT JOIN laboratory ON labcase.LaboratoryNum=laboratory.LaboratoryNum " + "WHERE AptNum=0";
            raw = Db.getTable(command);
            for (int i = 0;i < raw.Rows.Count;i++)
            {
                row = table.NewRow();
                row["AptDateTime"] = DateTime.MinValue;
                row["aptDateTime"] = "";
                row["AptNum"] = 0;
                row["lab"] = raw.Rows[i]["Description"].ToString();
                row["LabCaseNum"] = raw.Rows[i]["LabCaseNum"].ToString();
                row["patient"] = PatientLogic.GetNameLF(raw.Rows[i]["LName"].ToString(), raw.Rows[i]["FName"].ToString(), raw.Rows[i]["Preferred"].ToString(), raw.Rows[i]["MiddleI"].ToString());
                row["phone"] = raw.Rows[i]["Phone"].ToString();
                row["ProcDescript"] = "";
                row["status"] = "";
                row["Instructions"] = raw.Rows[i]["Instructions"].ToString();
                date = PIn.DateT(raw.Rows[i]["DateTimeChecked"].ToString());
                if (date.Year > 1880)
                {
                    row["status"] = Lans.g("FormLabCases","Quality Checked");
                }
                else
                {
                    date = PIn.DateT(raw.Rows[i]["DateTimeRecd"].ToString());
                    if (date.Year > 1880)
                    {
                        row["status"] = Lans.g("FormLabCases","Received");
                    }
                    else
                    {
                        date = PIn.DateT(raw.Rows[i]["DateTimeSent"].ToString());
                        if (date.Year > 1880)
                        {
                            row["status"] = Lans.g("FormLabCases","Sent");
                        }
                        else
                        {
                            //sent but not received
                            row["status"] = Lans.g("FormLabCases","Not Sent");
                        } 
                    } 
                } 
                rows.Add(row);
            }
        }
         
        LabCaseComparer comparer = new LabCaseComparer();
        rows.Sort(comparer);
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * Used when drawing the appointments for a day.
    */
    public static List<LabCase> getForPeriod(DateTime startDate, DateTime endDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<LabCase>>GetObject(MethodBase.GetCurrentMethod(), startDate, endDate);
        }
         
        //scheduled,complete,or ASAP
        String command = "SELECT labcase.* FROM labcase,appointment " + "WHERE labcase.AptNum=appointment.AptNum " + "AND (appointment.AptStatus=1 OR appointment.AptStatus=2 OR appointment.AptStatus=4) " + "AND AptDateTime >= " + POut.date(startDate) + " AND AptDateTime < " + POut.Date(endDate.AddDays(1));
        return Crud.LabCaseCrud.SelectMany(command);
    }

    //midnight of the next morning.
    /**
    * Used when drawing the planned appointment.
    */
    public static LabCase getForPlanned(long aptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<LabCase>GetObject(MethodBase.GetCurrentMethod(), aptNum);
        }
         
        String command = "SELECT * FROM labcase " + "WHERE labcase.PlannedAptNum=" + POut.long(aptNum);
        return Crud.LabCaseCrud.SelectOne(command);
    }

    /**
    * Gets one labcase from database.
    */
    public static LabCase getOne(long labCaseNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<LabCase>GetObject(MethodBase.GetCurrentMethod(), labCaseNum);
        }
         
        String command = "SELECT * FROM labcase WHERE LabCaseNum=" + POut.long(labCaseNum);
        return Crud.LabCaseCrud.SelectOne(command);
    }

    /**
    * Gets all labcases for a patient which have not been attached to an appointment.  Usually one or none.  Only used when attaching a labcase from within an appointment.
    */
    public static List<LabCase> getForPat(long patNum, boolean isPlanned) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<LabCase>>GetObject(MethodBase.GetCurrentMethod(), patNum, isPlanned);
        }
         
        String command = "SELECT * FROM labcase WHERE PatNum=" + POut.long(patNum) + " AND ";
        if (isPlanned)
        {
            command += "PlannedAptNum=0 AND AptNum=0";
        }
        else
        {
            //We only show lab cases that have not been attached to any kind of appt.
            command += "AptNum=0";
        } 
        return Crud.LabCaseCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(LabCase labCase) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            labCase.LabCaseNum = Meth.GetLong(MethodBase.GetCurrentMethod(), labCase);
            return labCase.LabCaseNum;
        }
         
        return Crud.LabCaseCrud.Insert(labCase);
    }

    /**
    * 
    */
    public static void update(LabCase labCase) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), labCase);
            return ;
        }
         
        Crud.LabCaseCrud.Update(labCase);
    }

    /**
    * Surround with try/catch.  Checks dependencies first.  Throws exception if can't delete.
    */
    public static void delete(long labCaseNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), labCaseNum);
            return ;
        }
         
        //check for dependencies
        String command = "SELECT count(*) FROM sheet,sheetfield " + "WHERE sheet.SheetNum=sheetfield.SheetNum" + " AND sheet.PatNum= (SELECT patnum FROM labcase WHERE labcase.LabCaseNum=" + POut.long(labCaseNum) + ")" + " AND sheet.SheetType=" + POut.Long(((Enum)SheetTypeEnum.LabSlip).ordinal()) + " AND sheetfield.FieldType=" + POut.Long(((Enum)SheetFieldType.Parameter).ordinal()) + " AND sheetfield.FieldName='LabCaseNum' " + "AND sheetfield.FieldValue='" + POut.long(labCaseNum) + "'";
        if (PIn.int(Db.getCount(command)) != 0)
        {
            throw new Exception(Lans.g("LabCases","Cannot delete LabCase because lab slip is still attached."));
        }
         
        //delete
        command = "DELETE FROM labcase WHERE LabCaseNum = " + POut.long(labCaseNum);
        Db.nonQ(command);
    }

    /**
    * Attaches a labcase to an appointment.
    */
    public static void attachToAppt(long labCaseNum, long aptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), labCaseNum, aptNum);
            return ;
        }
         
        String command = "UPDATE labcase SET AptNum=" + POut.long(aptNum) + " WHERE LabCaseNum=" + POut.long(labCaseNum);
        Db.nonQ(command);
    }

    /**
    * Attaches a labcase to a planned appointment.
    */
    public static void attachToPlannedAppt(long labCaseNum, long plannedAptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), labCaseNum, plannedAptNum);
            return ;
        }
         
        String command = "UPDATE labcase SET PlannedAptNum=" + POut.long(plannedAptNum) + " WHERE LabCaseNum=" + POut.long(labCaseNum);
        Db.nonQ(command);
    }

    /**
    * Frequently returns null.
    */
    public static LabCase getOneFromList(List<LabCase> labCaseList, long aptNum) throws Exception {
        for (int i = 0;i < labCaseList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (labCaseList[i].AptNum == aptNum)
            {
                return labCaseList[i];
            }
             
        }
        return null;
    }

}


