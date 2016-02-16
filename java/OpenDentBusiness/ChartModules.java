//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:53 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.ChartModuleComponentsToLoad;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.OrionDPC;
import OpenDentBusiness.OrionStatus;
import OpenDentBusiness.Pharmacies;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.ProcedureLogic;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.TaskNote;
import OpenDentBusiness.TaskNotes;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.TreatmentArea;
import OpenDentBusiness.Userods;

public class ChartModules   
{
    private static DataTable rawApt = new DataTable();
    public static DataSet getAll(long patNum, boolean isAuditMode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetDS(MethodBase.GetCurrentMethod(), patNum, isAuditMode);
        }
         
        DataSet retVal = new DataSet();
        retVal.Tables.Add(getProgNotes(patNum,isAuditMode,new ChartModuleComponentsToLoad()));
        retVal.Tables.Add(getPlannedApt(patNum));
        return retVal;
    }

    public static DataSet getAll(long patNum, boolean isAuditMode, ChartModuleComponentsToLoad componentsToLoad) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetDS(MethodBase.GetCurrentMethod(), patNum, isAuditMode, componentsToLoad);
        }
         
        DataSet retVal = new DataSet();
        retVal.Tables.Add(getProgNotes(patNum,isAuditMode,componentsToLoad));
        retVal.Tables.Add(getPlannedApt(patNum));
        return retVal;
    }

    public static DataTable getProgNotes(long patNum, boolean isAuditMode, ChartModuleComponentsToLoad componentsToLoad) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), componentsToLoad);
        }
         
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = new DataTable("ProgNotes");
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("aptDateTime", DateTime.class);
        table.Columns.Add("AbbrDesc");
        table.Columns.Add("AptNum");
        table.Columns.Add("clinic");
        table.Columns.Add("CodeNum");
        table.Columns.Add("colorBackG");
        table.Columns.Add("colorText");
        table.Columns.Add("CommlogNum");
        table.Columns.Add("dateEntryC");
        table.Columns.Add("dateTP");
        table.Columns.Add("description");
        table.Columns.Add("dx");
        table.Columns.Add("Dx");
        table.Columns.Add("EmailMessageNum");
        table.Columns.Add("FormPatNum");
        table.Columns.Add("HideGraphics");
        table.Columns.Add("isLocked");
        table.Columns.Add("length");
        table.Columns.Add("LabCaseNum");
        table.Columns.Add("note");
        table.Columns.Add("orionDateScheduleBy");
        table.Columns.Add("orionDateStopClock");
        table.Columns.Add("orionDPC");
        table.Columns.Add("orionDPCpost");
        table.Columns.Add("orionIsEffectiveComm");
        table.Columns.Add("orionIsOnCall");
        table.Columns.Add("orionStatus2");
        table.Columns.Add("PatNum");
        //only used for Commlog and Task
        table.Columns.Add("Priority");
        //for sorting
        table.Columns.Add("priority");
        table.Columns.Add("ProcCode");
        table.Columns.Add("procDate");
        table.Columns.Add("ProcDate", DateTime.class);
        table.Columns.Add("procFee");
        table.Columns.Add("ProcNum");
        table.Columns.Add("ProcNumLab");
        table.Columns.Add("procStatus");
        table.Columns.Add("ProcStatus");
        table.Columns.Add("procTime");
        table.Columns.Add("procTimeEnd");
        table.Columns.Add("prognosis");
        table.Columns.Add("prov");
        table.Columns.Add("quadrant");
        table.Columns.Add("RxNum");
        table.Columns.Add("SheetNum");
        table.Columns.Add("signature");
        table.Columns.Add("Surf");
        table.Columns.Add("surf");
        table.Columns.Add("TaskNum");
        table.Columns.Add("toothNum");
        table.Columns.Add("ToothNum");
        table.Columns.Add("ToothRange");
        table.Columns.Add("user");
        //table.Columns.Add("");
        //but we won't actually fill this table with rows until the very end.  It's more useful to use a List<> for now.
        List<DataRow> rows = new List<DataRow>();
        String command = new String();
        DateTime dateT = new DateTime();
        String txt = new String();
        List<DataRow> labRows = new List<DataRow>();
        //Canadian lab procs, which must be added in a loop at the very end.
        if (componentsToLoad.ShowTreatPlan || componentsToLoad.ShowCompleted || componentsToLoad.ShowExisting || componentsToLoad.ShowReferred || componentsToLoad.ShowConditions)
        {
            command = "SELECT provider.Abbr,procedurecode.AbbrDesc,appointment.AptDateTime,procedurelog.BaseUnits,procedurelog.ClinicNum," + "procedurelog.CodeNum,procedurelog.DateEntryC,orionproc.DateScheduleBy,orionproc.DateStopClock,procedurelog.DateTP," + "procedurecode.Descript,orionproc.DPC,orionproc.DPCpost,Dx,HideGraphics,orionproc.IsEffectiveComm,IsLocked,orionproc.IsOnCall," + "LaymanTerm,Priority,procedurecode.ProcCode,ProcDate,ProcFee,procedurelog.ProcNum,ProcNumLab,procedurelog.ProcTime," + "procedurelog.ProcTimeEnd,procedurelog.Prognosis,ProcStatus,orionproc.Status2,Surf,ToothNum,ToothRange,UnitQty " + "FROM procedurelog " + "LEFT JOIN procedurecode ON procedurecode.CodeNum=procedurelog.CodeNum " + "LEFT JOIN provider ON provider.ProvNum=procedurelog.ProvNum " + "LEFT JOIN orionproc ON procedurelog.ProcNum=orionproc.ProcNum " + "LEFT JOIN appointment ON appointment.AptNum=procedurelog.AptNum " + "AND (appointment.AptStatus=" + POut.Long(((Enum)ApptStatus.Scheduled).ordinal()) + " OR appointment.AptStatus=" + POut.Long(((Enum)ApptStatus.ASAP).ordinal()) + " OR appointment.AptStatus=" + POut.Long(((Enum)ApptStatus.Broken).ordinal()) + " OR appointment.AptStatus=" + POut.Long(((Enum)ApptStatus.Complete).ordinal()) + ") WHERE procedurelog.PatNum=" + POut.long(patNum);
            if (!isAuditMode)
            {
                //regular mode
                command += " AND (ProcStatus !=6" + " OR IsLocked=1)";
            }
             
            //not deleted
            //Any locked proc should show.  This forces invalidated (deleted locked) procs to show.
            command += " ORDER BY ProcDate";
            //we'll just have to reorder it anyway
            DataTable rawProcs = dcon.getTable(command);
            command = "SELECT ProcNum,EntryDateTime,UserNum,Note," + "CASE WHEN Signature!='' THEN 1 ELSE 0 END AS SigPresent " + "FROM procnote WHERE PatNum=" + POut.long(patNum) + " ORDER BY EntryDateTime";
            // but this helps when looping for notes
            DataTable rawNotes = dcon.getTable(command);
            for (int i = 0;i < rawProcs.Rows.Count;i++)
            {
                row = table.NewRow();
                row["AbbrDesc"] = rawProcs.Rows[i]["AbbrDesc"].ToString();
                row["aptDateTime"] = PIn.DateT(rawProcs.Rows[i]["AptDateTime"].ToString());
                row["AptNum"] = 0;
                row["clinic"] = Clinics.GetDesc(PIn.Long(rawProcs.Rows[i]["ClinicNum"].ToString()));
                row["CodeNum"] = rawProcs.Rows[i]["CodeNum"].ToString();
                row["colorBackG"] = Color.White.ToArgb();
                if (((DateTime)row["aptDateTime"]).Date == DateTime.Today)
                {
                    row["colorBackG"] = DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][6].ItemColor.ToArgb().ToString();
                }
                 
                switch((OpenDentBusiness.ProcStat)PIn.Long(rawProcs.Rows[i]["ProcStatus"].ToString()))
                {
                    case TP: 
                        row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][0].ItemColor.ToArgb().ToString();
                        break;
                    case C: 
                        row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][1].ItemColor.ToArgb().ToString();
                        break;
                    case EC: 
                        row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][2].ItemColor.ToArgb().ToString();
                        break;
                    case EO: 
                        row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][3].ItemColor.ToArgb().ToString();
                        break;
                    case R: 
                        row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][4].ItemColor.ToArgb().ToString();
                        break;
                    case D: 
                        row["colorText"] = Color.Black.ToArgb().ToString();
                        break;
                    case Cn: 
                        row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][22].ItemColor.ToArgb().ToString();
                        break;
                
                }
                row["CommlogNum"] = 0;
                dateT = PIn.DateT(rawProcs.Rows[i]["DateEntryC"].ToString());
                if (dateT.Year < 1880)
                {
                    row["dateEntryC"] = "";
                }
                else
                {
                    row["dateEntryC"] = dateT.ToString(Lans.getShortDateTimeFormat());
                } 
                dateT = PIn.DateT(rawProcs.Rows[i]["DateTP"].ToString());
                if (dateT.Year < 1880)
                {
                    row["dateTP"] = "";
                }
                else
                {
                    row["dateTP"] = dateT.ToString(Lans.getShortDateTimeFormat());
                } 
                if (StringSupport.equals(rawProcs.Rows[i]["LaymanTerm"].ToString(), ""))
                {
                    row["description"] = rawProcs.Rows[i]["Descript"].ToString();
                }
                else
                {
                    row["description"] = rawProcs.Rows[i]["LaymanTerm"].ToString();
                } 
                if (!StringSupport.equals(rawProcs.Rows[i]["ToothRange"].ToString(), ""))
                {
                    row["description"] += " #" + Tooth.FormatRangeForDisplay(rawProcs.Rows[i]["ToothRange"].ToString());
                }
                 
                row["dx"] = DefC.GetValue(DefCat.Diagnosis, PIn.Long(rawProcs.Rows[i]["Dx"].ToString()));
                row["Dx"] = rawProcs.Rows[i]["Dx"].ToString();
                row["EmailMessageNum"] = 0;
                row["FormPatNum"] = 0;
                row["HideGraphics"] = rawProcs.Rows[i]["HideGraphics"].ToString();
                row["isLocked"] = PIn.Bool(rawProcs.Rows[i]["isLocked"].ToString()) ? "X" : "";
                row["LabCaseNum"] = 0;
                row["length"] = "";
                row["signature"] = "";
                row["user"] = "";
                if (componentsToLoad.ShowProcNotes)
                {
                    row["note"] = "";
                    dateT = PIn.DateT(rawProcs.Rows[i]["DateScheduleBy"].ToString());
                    if (dateT.Year < 1880)
                    {
                        row["orionDateScheduleBy"] = "";
                    }
                    else
                    {
                        row["orionDateScheduleBy"] = dateT.ToString(Lans.getShortDateTimeFormat());
                    } 
                    dateT = PIn.DateT(rawProcs.Rows[i]["DateStopClock"].ToString());
                    if (dateT.Year < 1880)
                    {
                        row["orionDateStopClock"] = "";
                    }
                    else
                    {
                        row["orionDateStopClock"] = dateT.ToString(Lans.getShortDateTimeFormat());
                    } 
                    if (StringSupport.equals(((OrionDPC)PIn.Int(rawProcs.Rows[i]["DPC"].ToString())).ToString(), "NotSpecified"))
                    {
                        row["orionDPC"] = "";
                    }
                    else
                    {
                        row["orionDPC"] = ((OrionDPC)PIn.Int(rawProcs.Rows[i]["DPC"].ToString())).ToString();
                    } 
                    if (StringSupport.equals(((OrionDPC)PIn.Int(rawProcs.Rows[i]["DPCpost"].ToString())).ToString(), "NotSpecified"))
                    {
                        row["orionDPCpost"] = "";
                    }
                    else
                    {
                        row["orionDPCpost"] = ((OrionDPC)PIn.Int(rawProcs.Rows[i]["DPCpost"].ToString())).ToString();
                    } 
                    row["orionIsEffectiveComm"] = "";
                    if (StringSupport.equals(rawProcs.Rows[i]["IsEffectiveComm"].ToString(), "1"))
                    {
                        row["orionIsEffectiveComm"] = "Y";
                    }
                    else if (StringSupport.equals(rawProcs.Rows[i]["IsEffectiveComm"].ToString(), "0"))
                    {
                        row["orionIsEffectiveComm"] = "";
                    }
                      
                    row["orionIsOnCall"] = "";
                    if (StringSupport.equals(rawProcs.Rows[i]["IsOnCall"].ToString(), "1"))
                    {
                        row["orionIsOnCall"] = "Y";
                    }
                    else if (StringSupport.equals(rawProcs.Rows[i]["IsOnCall"].ToString(), "0"))
                    {
                        row["orionIsOnCall"] = "";
                    }
                      
                    row["orionStatus2"] = ((OrionStatus)PIn.Int(rawProcs.Rows[i]["Status2"].ToString())).ToString();
                    if (isAuditMode)
                    {
                        for (int n = 0;n < rawNotes.Rows.Count;n++)
                        {
                            //we will include all notes for each proc.  We will concat and make readable.
                            //loop through each note
                            if (rawProcs.Rows[i]["ProcNum"].ToString() != rawNotes.Rows[n]["ProcNum"].ToString())
                            {
                                continue;
                            }
                             
                            if (!StringSupport.equals(row["note"].ToString(), ""))
                            {
                                //if there is an existing note
                                row["note"] += "\r\n------------------------------------------------------\r\n";
                            }
                             
                            //start a new line
                            row["note"] += PIn.DateT(rawNotes.Rows[n]["EntryDateTime"].ToString()).ToString();
                            row["note"] += "  " + Userods.GetName(PIn.Long(rawNotes.Rows[n]["UserNum"].ToString()));
                            if (StringSupport.equals(rawNotes.Rows[n]["SigPresent"].ToString(), "1"))
                            {
                                row["note"] += "  " + Lans.g("ChartModule","(signed)");
                            }
                             
                            row["note"] += "\r\n" + rawNotes.Rows[n]["Note"].ToString();
                        }
                    }
                    else
                    {
                        for (int n = rawNotes.Rows.Count - 1;n >= 0;n--)
                        {
                            //Not audit mode.  We just want the most recent note
                            //loop through each note, backwards.
                            if (rawProcs.Rows[i]["ProcNum"].ToString() != rawNotes.Rows[n]["ProcNum"].ToString())
                            {
                                continue;
                            }
                             
                            row["note"] = rawNotes.Rows[n]["Note"].ToString();
                            break;
                        }
                    } 
                }
                 
                //out of note loop.
                //This section is closely related to notes, but must be filled for all procedures regardless of whether showing the actual note.
                if (!isAuditMode)
                {
                    for (int n = rawNotes.Rows.Count - 1;n >= 0;n--)
                    {
                        //Audit mode is handled above by putting this info into the note section itself.
                        //Loop through each note; backwards to get most recent note.
                        if (rawProcs.Rows[i]["ProcNum"].ToString() != rawNotes.Rows[n]["ProcNum"].ToString())
                        {
                            continue;
                        }
                         
                        row["user"] = Userods.GetName(PIn.Long(rawNotes.Rows[n]["UserNum"].ToString()));
                        if (StringSupport.equals(rawNotes.Rows[n]["SigPresent"].ToString(), "1"))
                        {
                            row["signature"] = Lans.g("ChartModule","Signed");
                        }
                        else
                        {
                            row["signature"] = "";
                        } 
                        break;
                    }
                }
                 
                row["PatNum"] = "";
                row["Priority"] = rawProcs.Rows[i]["Priority"].ToString();
                row["priority"] = DefC.GetName(DefCat.TxPriorities, PIn.Long(rawProcs.Rows[i]["Priority"].ToString()));
                row["ProcCode"] = rawProcs.Rows[i]["ProcCode"].ToString();
                dateT = PIn.DateT(rawProcs.Rows[i]["ProcDate"].ToString());
                if (dateT.Year < 1880)
                {
                    row["procDate"] = "";
                }
                else
                {
                    row["procDate"] = dateT.ToString(Lans.getShortDateTimeFormat());
                } 
                row["ProcDate"] = dateT;
                double amt = PIn.Double(rawProcs.Rows[i]["ProcFee"].ToString());
                int qty = PIn.Int(rawProcs.Rows[i]["UnitQty"].ToString()) + PIn.Int(rawProcs.Rows[i]["BaseUnits"].ToString());
                if (qty > 0)
                {
                    amt *= qty;
                }
                 
                row["procFee"] = amt.ToString("F");
                row["ProcNum"] = rawProcs.Rows[i]["ProcNum"].ToString();
                row["ProcNumLab"] = rawProcs.Rows[i]["ProcNumLab"].ToString();
                row["procStatus"] = Lans.g("enumProcStat", ((OpenDentBusiness.ProcStat)PIn.Long(rawProcs.Rows[i]["ProcStatus"].ToString())).ToString());
                if (StringSupport.equals(row["procStatus"].ToString(), "D"))
                {
                    if (StringSupport.equals(row["isLocked"].ToString(), "X"))
                    {
                        row["procStatus"] = "I";
                        row["description"] = Lans.g("ChartModule","-invalid-") + " " + row["description"].ToString();
                    }
                     
                }
                 
                row["ProcStatus"] = rawProcs.Rows[i]["ProcStatus"].ToString();
                row["procTime"] = "";
                dateT = PIn.DateT(rawProcs.Rows[i]["ProcTime"].ToString());
                if (dateT.TimeOfDay != TimeSpan.Zero)
                {
                    row["procTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
                }
                 
                row["procTimeEnd"] = "";
                dateT = PIn.DateT(rawProcs.Rows[i]["ProcTimeEnd"].ToString());
                if (dateT.TimeOfDay != TimeSpan.Zero)
                {
                    row["procTimeEnd"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
                }
                 
                row["prognosis"] = DefC.GetName(DefCat.Prognosis, PIn.Long(rawProcs.Rows[i]["Prognosis"].ToString()));
                row["prov"] = rawProcs.Rows[i]["Abbr"].ToString();
                row["quadrant"] = "";
                if (ProcedureCodes.GetProcCode(PIn.Long(row["CodeNum"].ToString())).TreatArea == TreatmentArea.Tooth)
                {
                    row["quadrant"] = Tooth.GetQuadrant(rawProcs.Rows[i]["ToothNum"].ToString());
                }
                else if (ProcedureCodes.GetProcCode(PIn.Long(row["CodeNum"].ToString())).TreatArea == TreatmentArea.Surf)
                {
                    row["quadrant"] = Tooth.GetQuadrant(rawProcs.Rows[i]["ToothNum"].ToString());
                }
                else if (ProcedureCodes.GetProcCode(PIn.Long(row["CodeNum"].ToString())).TreatArea == TreatmentArea.Quad)
                {
                    row["quadrant"] = rawProcs.Rows[i]["Surf"].ToString();
                }
                else if (ProcedureCodes.GetProcCode(PIn.Long(row["CodeNum"].ToString())).TreatArea == TreatmentArea.ToothRange)
                {
                    String[] toothNum = rawProcs.Rows[i]["ToothRange"].ToString().Split(',');
                    boolean sameQuad = false;
                    for (int n = 0;n < toothNum.Length;n++)
                    {
                        //Don't want true if length==0.
                        //But want true if length==1 (check index 0 against itself).
                        if (Tooth.GetQuadrant(toothNum[n]) == Tooth.GetQuadrant(toothNum[0]))
                        {
                            sameQuad = true;
                        }
                        else
                        {
                            sameQuad = false;
                            break;
                        } 
                    }
                    if (sameQuad)
                    {
                        row["quadrant"] = Tooth.GetQuadrant(toothNum[0]);
                    }
                     
                }
                    
                row["RxNum"] = 0;
                row["SheetNum"] = 0;
                row["Surf"] = rawProcs.Rows[i]["Surf"].ToString();
                if (ProcedureCodes.GetProcCode(PIn.Long(row["CodeNum"].ToString())).TreatArea == TreatmentArea.Surf)
                {
                    row["surf"] = Tooth.SurfTidyFromDbToDisplay(rawProcs.Rows[i]["Surf"].ToString(), rawProcs.Rows[i]["ToothNum"].ToString());
                }
                else
                {
                    row["surf"] = rawProcs.Rows[i]["Surf"].ToString();
                } 
                row["TaskNum"] = 0;
                row["toothNum"] = Tooth.GetToothLabel(rawProcs.Rows[i]["ToothNum"].ToString());
                row["ToothNum"] = rawProcs.Rows[i]["ToothNum"].ToString();
                row["ToothRange"] = rawProcs.Rows[i]["ToothRange"].ToString();
                if (StringSupport.equals(rawProcs.Rows[i]["ProcNumLab"].ToString(), "0"))
                {
                    //normal proc
                    rows.Add(row);
                }
                else
                {
                    row["description"] = "^ ^ " + row["description"].ToString();
                    labRows.Add(row);
                } 
            }
        }
         
        //these will be added in the loop at the end
        if (componentsToLoad.ShowCommLog)
        {
            //TODO: refine to use show Family
            command = "SELECT CommlogNum,CommDateTime,commlog.DateTimeEnd,CommType,Note,commlog.PatNum,UserNum,p1.FName," + "CASE WHEN Signature!='' THEN 1 ELSE 0 END SigPresent " + "FROM patient p1,patient p2,commlog " + "WHERE commlog.PatNum=p1.PatNum " + "AND p1.Guarantor=p2.Guarantor " + "AND p2.PatNum=" + POut.long(patNum) + " ORDER BY CommDateTime";
            DataTable rawComm = dcon.getTable(command);
            for (int i = 0;i < rawComm.Rows.Count;i++)
            {
                row = table.NewRow();
                row["AbbrDesc"] = "";
                row["aptDateTime"] = DateTime.MinValue;
                row["AptNum"] = 0;
                row["clinic"] = "";
                row["CodeNum"] = "";
                row["colorBackG"] = Color.White.ToArgb();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][6].ItemColor.ToArgb().ToString();
                row["CommlogNum"] = rawComm.Rows[i]["CommlogNum"].ToString();
                row["dateEntryC"] = "";
                row["dateTP"] = "";
                if (rawComm.Rows[i]["PatNum"].ToString() == patNum.ToString())
                {
                    txt = "";
                }
                else
                {
                    txt = "(" + rawComm.Rows[i]["FName"].ToString() + ") ";
                } 
                row["description"] = txt + Lans.g("ChartModule","Comm - ") + DefC.GetName(DefCat.CommLogTypes, PIn.Long(rawComm.Rows[i]["CommType"].ToString()));
                row["dx"] = "";
                row["Dx"] = "";
                row["EmailMessageNum"] = 0;
                row["FormPatNum"] = 0;
                row["HideGraphics"] = "";
                row["isLocked"] = "";
                row["LabCaseNum"] = 0;
                row["length"] = "";
                if (PIn.DateT(rawComm.Rows[i]["DateTimeEnd"].ToString()).Year > 1880)
                {
                    DateTime startTime = PIn.DateT(rawComm.Rows[i]["CommDateTime"].ToString());
                    DateTime endTime = PIn.DateT(rawComm.Rows[i]["DateTimeEnd"].ToString());
                    row["length"] = (endTime - startTime).ToStringHmm();
                }
                 
                row["note"] = rawComm.Rows[i]["Note"].ToString();
                row["orionDateScheduleBy"] = "";
                row["orionDateStopClock"] = "";
                row["orionDPC"] = "";
                row["orionDPCpost"] = "";
                row["orionIsEffectiveComm"] = "";
                row["orionIsOnCall"] = "";
                row["orionStatus2"] = "";
                row["PatNum"] = rawComm.Rows[i]["PatNum"].ToString();
                row["Priority"] = "";
                row["priority"] = "";
                row["ProcCode"] = "";
                dateT = PIn.DateT(rawComm.Rows[i]["CommDateTime"].ToString());
                if (dateT.Year < 1880)
                {
                    row["procDate"] = "";
                }
                else
                {
                    row["procDate"] = dateT.ToString(Lans.getShortDateTimeFormat());
                } 
                row["ProcDate"] = dateT;
                row["procTime"] = "";
                if (dateT.TimeOfDay != TimeSpan.Zero)
                {
                    row["procTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
                }
                 
                row["procTimeEnd"] = "";
                row["procFee"] = "";
                row["ProcNum"] = 0;
                row["ProcNumLab"] = "";
                row["procStatus"] = "";
                row["ProcStatus"] = "";
                row["prov"] = "";
                row["quadrant"] = "";
                row["RxNum"] = 0;
                row["SheetNum"] = 0;
                row["signature"] = "";
                if (StringSupport.equals(rawComm.Rows[i]["SigPresent"].ToString(), "1"))
                {
                    row["signature"] = Lans.g("ChartModule","Signed");
                }
                 
                row["Surf"] = "";
                row["TaskNum"] = 0;
                row["toothNum"] = "";
                row["ToothNum"] = "";
                row["ToothRange"] = "";
                row["user"] = Userods.GetName(PIn.Long(rawComm.Rows[i]["UserNum"].ToString()));
                rows.Add(row);
            }
        }
         
        if (componentsToLoad.ShowFormPat)
        {
            command = "SELECT FormDateTime,FormPatNum " + "FROM formpat WHERE PatNum ='" + POut.long(patNum) + "' ORDER BY FormDateTime";
            DataTable rawForm = dcon.getTable(command);
            for (int i = 0;i < rawForm.Rows.Count;i++)
            {
                row = table.NewRow();
                row["AbbrDesc"] = "";
                row["aptDateTime"] = DateTime.MinValue;
                row["AptNum"] = 0;
                row["clinic"] = "";
                row["CodeNum"] = "";
                row["colorBackG"] = Color.White.ToArgb();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][6].ItemColor.ToArgb().ToString();
                row["CommlogNum"] = 0;
                row["dateEntryC"] = "";
                row["dateTP"] = "";
                row["description"] = Lans.g("ChartModule","Questionnaire");
                row["dx"] = "";
                row["Dx"] = "";
                row["EmailMessageNum"] = 0;
                row["FormPatNum"] = rawForm.Rows[i]["FormPatNum"].ToString();
                row["HideGraphics"] = "";
                row["isLocked"] = "";
                row["LabCaseNum"] = 0;
                row["length"] = "";
                row["note"] = "";
                row["orionDateScheduleBy"] = "";
                row["orionDateStopClock"] = "";
                row["orionDPC"] = "";
                row["orionDPCpost"] = "";
                row["orionIsEffectiveComm"] = "";
                row["orionIsOnCall"] = "";
                row["orionStatus2"] = "";
                row["PatNum"] = "";
                row["Priority"] = "";
                row["priority"] = "";
                row["ProcCode"] = "";
                dateT = PIn.DateT(rawForm.Rows[i]["FormDateTime"].ToString());
                row["ProcDate"] = dateT.ToShortDateString();
                if (dateT.TimeOfDay != TimeSpan.Zero)
                {
                    row["procTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
                }
                 
                if (dateT.Year < 1880)
                {
                    row["procDate"] = "";
                }
                else
                {
                    row["procDate"] = dateT.ToString(Lans.getShortDateTimeFormat());
                } 
                if (dateT.TimeOfDay != TimeSpan.Zero)
                {
                    row["procTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
                }
                 
                row["procTimeEnd"] = "";
                row["procFee"] = "";
                row["ProcNum"] = 0;
                row["ProcNumLab"] = "";
                row["procStatus"] = "";
                row["ProcStatus"] = "";
                row["prov"] = "";
                row["quadrant"] = "";
                row["RxNum"] = 0;
                row["SheetNum"] = 0;
                row["signature"] = "";
                row["Surf"] = "";
                row["TaskNum"] = 0;
                row["toothNum"] = "";
                row["ToothNum"] = "";
                row["ToothRange"] = "";
                row["user"] = "";
                /*commlog code
                					dateT = PIn.PDateT(rawForm.Rows[i]["FormDateTime"].ToString());
                					row["CommDateTime"] = dateT;
                					row["commDate"] = dateT.ToShortDateString();
                					if (dateT.TimeOfDay != TimeSpan.Zero)
                					{
                							row["commTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
                					}
                					row["CommlogNum"] = "0";
                					row["commType"] = Lans.g("AccountModule", "Questionnaire");
                					row["EmailMessageNum"] = "0";
                					row["FormPatNum"] = rawForm.Rows[i]["FormPatNum"].ToString();
                					row["mode"] = "";
                					row["Note"] = "";
                					row["patName"] = "";
                					row["SheetNum"] = "0";
                					//row["sentOrReceived"]="";
                					*/
                rows.Add(row);
            }
        }
         
        if (componentsToLoad.ShowRX)
        {
            command = "SELECT RxNum,RxDate,Drug,Disp,ProvNum,Notes,PharmacyNum FROM rxpat WHERE PatNum=" + POut.long(patNum) + " ORDER BY RxDate";
            DataTable rawRx = dcon.getTable(command);
            for (int i = 0;i < rawRx.Rows.Count;i++)
            {
                row = table.NewRow();
                row["AbbrDesc"] = "";
                row["aptDateTime"] = DateTime.MinValue;
                row["AptNum"] = 0;
                row["clinic"] = "";
                row["CodeNum"] = "";
                row["colorBackG"] = Color.White.ToArgb();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][5].ItemColor.ToArgb().ToString();
                row["CommlogNum"] = 0;
                row["dateEntryC"] = "";
                row["dateTP"] = "";
                row["description"] = Lans.g("ChartModule","Rx - ") + rawRx.Rows[i]["Drug"].ToString() + " - #" + rawRx.Rows[i]["Disp"].ToString();
                if (!StringSupport.equals(rawRx.Rows[i]["PharmacyNum"].ToString(), "0"))
                {
                    row["description"] += "\r\n" + Pharmacies.GetDescription(PIn.Long(rawRx.Rows[i]["PharmacyNum"].ToString()));
                }
                 
                row["dx"] = "";
                row["Dx"] = "";
                row["EmailMessageNum"] = 0;
                row["FormPatNum"] = 0;
                row["HideGraphics"] = "";
                row["isLocked"] = "";
                row["LabCaseNum"] = 0;
                row["length"] = "";
                row["note"] = rawRx.Rows[i]["Notes"].ToString();
                row["orionDateScheduleBy"] = "";
                row["orionDateStopClock"] = "";
                row["orionDPC"] = "";
                row["orionDPCpost"] = "";
                row["orionIsEffectiveComm"] = "";
                row["orionIsOnCall"] = "";
                row["orionStatus2"] = "";
                row["PatNum"] = "";
                row["Priority"] = "";
                row["priority"] = "";
                row["ProcCode"] = "";
                dateT = PIn.Date(rawRx.Rows[i]["RxDate"].ToString());
                if (dateT.Year < 1880)
                {
                    row["procDate"] = "";
                }
                else
                {
                    row["procDate"] = dateT.ToString(Lans.getShortDateTimeFormat());
                } 
                row["ProcDate"] = dateT;
                row["procFee"] = "";
                row["ProcNum"] = 0;
                row["ProcNumLab"] = "";
                row["procStatus"] = "";
                row["ProcStatus"] = "";
                row["procTime"] = "";
                row["procTimeEnd"] = "";
                row["prov"] = Providers.GetAbbr(PIn.Long(rawRx.Rows[i]["ProvNum"].ToString()));
                row["quadrant"] = "";
                row["RxNum"] = rawRx.Rows[i]["RxNum"].ToString();
                row["SheetNum"] = 0;
                row["signature"] = "";
                row["Surf"] = "";
                row["TaskNum"] = 0;
                row["toothNum"] = "";
                row["ToothNum"] = "";
                row["ToothRange"] = "";
                row["user"] = "";
                rows.Add(row);
            }
        }
         
        if (componentsToLoad.ShowLabCases)
        {
            command = "SELECT labcase.*,Description,Phone FROM labcase,laboratory " + "WHERE labcase.LaboratoryNum=laboratory.LaboratoryNum " + "AND PatNum=" + POut.long(patNum) + " ORDER BY DateTimeCreated";
            DataTable rawLab = dcon.getTable(command);
            DateTime duedate = new DateTime();
            for (int i = 0;i < rawLab.Rows.Count;i++)
            {
                row = table.NewRow();
                row["AbbrDesc"] = "";
                row["aptDateTime"] = DateTime.MinValue;
                row["AptNum"] = 0;
                row["clinic"] = "";
                row["CodeNum"] = "";
                row["colorBackG"] = Color.White.ToArgb();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][7].ItemColor.ToArgb().ToString();
                row["CommlogNum"] = 0;
                row["dateEntryC"] = "";
                row["dateTP"] = "";
                row["description"] = Lans.g("ChartModule","LabCase - ") + rawLab.Rows[i]["Description"].ToString() + " " + rawLab.Rows[i]["Phone"].ToString();
                if (PIn.Date(rawLab.Rows[i]["DateTimeDue"].ToString()).Year > 1880)
                {
                    duedate = PIn.DateT(rawLab.Rows[i]["DateTimeDue"].ToString());
                    row["description"] += "\r\n" + Lans.g("ChartModule","Due") + " " + duedate.ToString("ddd") + " " + duedate.ToShortDateString() + " " + duedate.ToShortTimeString();
                }
                 
                if (PIn.Date(rawLab.Rows[i]["DateTimeChecked"].ToString()).Year > 1880)
                {
                    row["description"] += "\r\n" + Lans.g("ChartModule","Quality Checked");
                }
                else if (PIn.Date(rawLab.Rows[i]["DateTimeRecd"].ToString()).Year > 1880)
                {
                    row["description"] += "\r\n" + Lans.g("ChartModule","Received");
                }
                else if (PIn.Date(rawLab.Rows[i]["DateTimeSent"].ToString()).Year > 1880)
                {
                    row["description"] += "\r\n" + Lans.g("ChartModule","Sent");
                }
                   
                row["dx"] = "";
                row["Dx"] = "";
                row["EmailMessageNum"] = 0;
                row["FormPatNum"] = 0;
                row["HideGraphics"] = "";
                row["isLocked"] = "";
                row["LabCaseNum"] = rawLab.Rows[i]["LabCaseNum"].ToString();
                row["length"] = "";
                row["note"] = rawLab.Rows[i]["Instructions"].ToString();
                row["orionDateScheduleBy"] = "";
                row["orionDateStopClock"] = "";
                row["orionDPC"] = "";
                row["orionDPCpost"] = "";
                row["orionIsEffectiveComm"] = "";
                row["orionIsOnCall"] = "";
                row["orionStatus2"] = "";
                row["PatNum"] = "";
                row["Priority"] = "";
                row["priority"] = "";
                row["ProcCode"] = "";
                dateT = PIn.DateT(rawLab.Rows[i]["DateTimeCreated"].ToString());
                if (dateT.Year < 1880)
                {
                    row["procDate"] = "";
                }
                else
                {
                    row["procDate"] = dateT.ToString(Lans.getShortDateTimeFormat());
                } 
                row["procTime"] = "";
                if (dateT.TimeOfDay != TimeSpan.Zero)
                {
                    row["procTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
                }
                 
                row["ProcDate"] = dateT;
                row["procTimeEnd"] = "";
                row["procFee"] = "";
                row["ProcNum"] = 0;
                row["ProcNumLab"] = "";
                row["procStatus"] = "";
                row["ProcStatus"] = "";
                row["prov"] = "";
                row["quadrant"] = "";
                row["RxNum"] = 0;
                row["SheetNum"] = 0;
                row["signature"] = "";
                row["Surf"] = "";
                row["TaskNum"] = 0;
                row["toothNum"] = "";
                row["ToothNum"] = "";
                row["ToothRange"] = "";
                row["user"] = "";
                rows.Add(row);
            }
        }
         
        if (componentsToLoad.ShowTasks)
        {
            command = "SELECT task.*,tasklist.Descript ListDisc,p1.FName " + "FROM patient p1,patient p2, task,tasklist " + "WHERE task.KeyNum=p1.PatNum " + "AND task.TaskListNum=tasklist.TaskListNum " + "AND p1.Guarantor=p2.Guarantor " + "AND p2.PatNum=" + POut.long(patNum) + " AND task.ObjectType=1 " + "ORDER BY DateTimeEntry";
            DataTable rawTask = dcon.getTable(command);
            List<long> taskNums = new List<long>();
            for (int i = 0;i < rawTask.Rows.Count;i++)
            {
                taskNums.Add(PIn.Long(rawTask.Rows[i]["TaskNum"].ToString()));
            }
            List<TaskNote> TaskNoteList = TaskNotes.RefreshForTasks(taskNums);
            for (int i = 0;i < rawTask.Rows.Count;i++)
            {
                row = table.NewRow();
                row["AbbrDesc"] = "";
                row["aptDateTime"] = DateTime.MinValue;
                row["AptNum"] = 0;
                row["clinic"] = "";
                row["CodeNum"] = "";
                //colors the same as notes
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][18].ItemColor.ToArgb().ToString();
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][19].ItemColor.ToArgb().ToString();
                //row["colorText"] = DefC.Long[(int)DefCat.ProgNoteColors][6].ItemColor.ToArgb().ToString();//same as commlog
                row["CommlogNum"] = 0;
                row["dateEntryC"] = "";
                row["dateTP"] = "";
                if (rawTask.Rows[i]["KeyNum"].ToString() == patNum.ToString())
                {
                    txt = "";
                }
                else
                {
                    txt = "(" + rawTask.Rows[i]["FName"].ToString() + ") ";
                } 
                if (StringSupport.equals(rawTask.Rows[i]["TaskStatus"].ToString(), "2"))
                {
                    //completed
                    txt += Lans.g("ChartModule","Completed ");
                    row["colorBackG"] = Color.White.ToArgb();
                    //use same as note colors for completed tasks
                    row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][20].ItemColor.ToArgb().ToString();
                    row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][21].ItemColor.ToArgb().ToString();
                }
                 
                row["description"] = txt + Lans.g("ChartModule","Task - In List: ") + rawTask.Rows[i]["ListDisc"].ToString();
                row["dx"] = "";
                row["Dx"] = "";
                row["EmailMessageNum"] = 0;
                row["FormPatNum"] = 0;
                row["HideGraphics"] = "";
                row["isLocked"] = "";
                row["LabCaseNum"] = 0;
                row["length"] = "";
                txt = "";
                if (!rawTask.Rows[i]["Descript"].ToString().StartsWith("==") && !StringSupport.equals(rawTask.Rows[i]["UserNum"].ToString(), ""))
                {
                    txt += Userods.GetName(PIn.Long(rawTask.Rows[i]["UserNum"].ToString())) + " - ";
                }
                 
                txt += rawTask.Rows[i]["Descript"].ToString();
                long taskNum = PIn.Long(rawTask.Rows[i]["TaskNum"].ToString());
                for (int n = 0;n < TaskNoteList.Count;n++)
                {
                    if (TaskNoteList[n].TaskNum != taskNum)
                    {
                        continue;
                    }
                     
                    //even on the first loop
                    txt += "\r\n" + "==" + Userods.GetName(TaskNoteList[n].UserNum) + " - " + TaskNoteList[n].DateTimeNote.ToShortDateString() + " " + TaskNoteList[n].DateTimeNote.ToShortTimeString() + " - " + TaskNoteList[n].Note;
                }
                row["note"] = txt;
                row["orionDateScheduleBy"] = "";
                row["orionDateStopClock"] = "";
                row["orionDPC"] = "";
                row["orionDPCpost"] = "";
                row["orionIsEffectiveComm"] = "";
                row["orionIsOnCall"] = "";
                row["orionStatus2"] = "";
                row["PatNum"] = rawTask.Rows[i]["KeyNum"].ToString();
                row["Priority"] = "";
                row["priority"] = "";
                row["ProcCode"] = "";
                dateT = PIn.DateT(rawTask.Rows[i]["DateTask"].ToString());
                row["procTime"] = "";
                if (dateT.Year < 1880)
                {
                    //check if due date set for task or note
                    dateT = PIn.DateT(rawTask.Rows[i]["DateTimeEntry"].ToString());
                    if (dateT.Year < 1880)
                    {
                        //since dateT was just redefined, check it now
                        row["procDate"] = "";
                    }
                    else
                    {
                        row["procDate"] = dateT.ToShortDateString();
                    } 
                    if (dateT.TimeOfDay != TimeSpan.Zero)
                    {
                        row["procTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
                    }
                     
                    row["ProcDate"] = dateT;
                }
                else
                {
                    row["procDate"] = dateT.ToString(Lans.getShortDateTimeFormat());
                    if (dateT.TimeOfDay != TimeSpan.Zero)
                    {
                        row["procTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
                    }
                     
                    row["ProcDate"] = dateT;
                } 
                //row["Surf"] = "DUE";
                row["procTimeEnd"] = "";
                row["procFee"] = "";
                row["ProcNum"] = 0;
                row["ProcNumLab"] = "";
                row["procStatus"] = "";
                row["ProcStatus"] = "";
                row["prov"] = "";
                row["quadrant"] = "";
                row["RxNum"] = 0;
                row["SheetNum"] = 0;
                row["signature"] = "";
                row["Surf"] = "";
                row["TaskNum"] = taskNum;
                row["toothNum"] = "";
                row["ToothNum"] = "";
                row["ToothRange"] = "";
                row["user"] = "";
                rows.Add(row);
            }
        }
         
        command = "SELECT * FROM appointment WHERE PatNum=" + POut.long(patNum);
        if (componentsToLoad.ShowAppointments)
        {
        }
        else
        {
            //we will need this table later for planned appts, so always need to get.
            //get all appts
            //only include planned appts.  We will need those later, but not in this grid.
            command += " AND AptStatus = " + POut.int(((Enum)ApptStatus.Planned).ordinal());
        } 
        command += " ORDER BY AptDateTime";
        rawApt = dcon.getTable(command);
        long apptStatus = new long();
        for (int i = 0;i < rawApt.Rows.Count;i++)
        {
            row = table.NewRow();
            row["AbbrDesc"] = "";
            row["aptDateTime"] = DateTime.MinValue;
            row["AptNum"] = rawApt.Rows[i]["AptNum"].ToString();
            row["clinic"] = "";
            row["colorBackG"] = Color.White.ToArgb();
            dateT = PIn.DateT(rawApt.Rows[i]["AptDateTime"].ToString());
            apptStatus = PIn.Long(rawApt.Rows[i]["AptStatus"].ToString());
            row["colorBackG"] = "";
            row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][8].ItemColor.ToArgb().ToString();
            row["CommlogNum"] = 0;
            row["dateEntryC"] = "";
            row["dateTP"] = "";
            row["description"] = Lans.g("ChartModule","Appointment - ") + dateT.ToShortTimeString() + "\r\n" + rawApt.Rows[i]["ProcDescript"].ToString();
            if (dateT.Date.Date == DateTime.Today.Date)
            {
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][9].ItemColor.ToArgb().ToString();
                //deliniates nicely between old appts
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][8].ItemColor.ToArgb().ToString();
            }
            else if (dateT.Date < DateTime.Today)
            {
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][11].ItemColor.ToArgb().ToString();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][10].ItemColor.ToArgb().ToString();
            }
            else if (dateT.Date > DateTime.Today)
            {
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][13].ItemColor.ToArgb().ToString();
                //at a glace, you see green...the pt is good to go as they have a future appt scheduled
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][12].ItemColor.ToArgb().ToString();
            }
               
            if (apptStatus == ((Enum)ApptStatus.Broken).ordinal())
            {
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][14].ItemColor.ToArgb().ToString();
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][15].ItemColor.ToArgb().ToString();
                row["description"] = Lans.g("ChartModule","BROKEN Appointment - ") + dateT.ToShortTimeString() + "\r\n" + rawApt.Rows[i]["ProcDescript"].ToString();
            }
            else if (apptStatus == ((Enum)ApptStatus.UnschedList).ordinal())
            {
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][14].ItemColor.ToArgb().ToString();
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][15].ItemColor.ToArgb().ToString();
                row["description"] = Lans.g("ChartModule","UNSCHEDULED Appointment - ") + dateT.ToShortTimeString() + "\r\n" + rawApt.Rows[i]["ProcDescript"].ToString();
            }
            else if (apptStatus == ((Enum)ApptStatus.Planned).ordinal())
            {
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][16].ItemColor.ToArgb().ToString();
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][17].ItemColor.ToArgb().ToString();
                row["description"] = Lans.g("ChartModule","PLANNED Appointment") + "\r\n" + rawApt.Rows[i]["ProcDescript"].ToString();
            }
            else if (apptStatus == ((Enum)ApptStatus.PtNote).ordinal())
            {
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][18].ItemColor.ToArgb().ToString();
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][19].ItemColor.ToArgb().ToString();
                row["description"] = Lans.g("ChartModule","*** Patient NOTE  *** - ") + dateT.ToShortTimeString();
            }
            else if (apptStatus == ((Enum)ApptStatus.PtNoteCompleted).ordinal())
            {
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][20].ItemColor.ToArgb().ToString();
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][21].ItemColor.ToArgb().ToString();
                row["description"] = Lans.g("ChartModule","** Complete Patient NOTE ** - ") + dateT.ToShortTimeString();
            }
                 
            row["dx"] = "";
            row["Dx"] = "";
            row["EmailMessageNum"] = 0;
            row["FormPatNum"] = 0;
            row["HideGraphics"] = "";
            row["isLocked"] = "";
            row["LabCaseNum"] = 0;
            row["length"] = "";
            if (!StringSupport.equals(rawApt.Rows[i]["Pattern"].ToString(), ""))
            {
                row["length"] = (new TimeSpan(0, rawApt.Rows[i]["Pattern"].ToString().Length * 5, 0)).ToStringHmm();
            }
             
            row["note"] = rawApt.Rows[i]["Note"].ToString();
            row["orionDateScheduleBy"] = "";
            row["orionDateStopClock"] = "";
            row["orionDPC"] = "";
            row["orionDPCpost"] = "";
            row["orionIsEffectiveComm"] = "";
            row["orionIsOnCall"] = "";
            row["orionStatus2"] = "";
            row["PatNum"] = "";
            row["Priority"] = "";
            row["priority"] = "";
            row["ProcCode"] = "";
            if (dateT.Year < 1880)
            {
                row["procDate"] = "";
            }
            else
            {
                row["procDate"] = dateT.ToString(Lans.getShortDateTimeFormat());
            } 
            row["procTime"] = "";
            if (dateT.TimeOfDay != TimeSpan.Zero)
            {
                row["procTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
            }
             
            row["ProcDate"] = dateT;
            row["procTimeEnd"] = "";
            row["procFee"] = "";
            row["ProcNum"] = 0;
            row["ProcNumLab"] = "";
            row["procStatus"] = "";
            row["ProcStatus"] = "";
            row["prov"] = "";
            row["quadrant"] = "";
            row["RxNum"] = 0;
            row["SheetNum"] = 0;
            row["signature"] = "";
            row["Surf"] = "";
            row["TaskNum"] = 0;
            row["toothNum"] = "";
            row["ToothNum"] = "";
            row["ToothRange"] = "";
            row["user"] = "";
            rows.Add(row);
        }
        if (componentsToLoad.ShowEmail)
        {
            command = "SELECT EmailMessageNum,MsgDateTime,Subject,BodyText,PatNum,SentOrReceived " + "FROM emailmessage " + "WHERE PatNum=" + POut.long(patNum) + " AND SentOrReceived NOT IN (12,13) " + "ORDER BY MsgDateTime";
            //Do not show Direct message acknowledgements in Chart progress notes
            DataTable rawEmail = dcon.getTable(command);
            for (int i = 0;i < rawEmail.Rows.Count;i++)
            {
                row = table.NewRow();
                row["AbbrDesc"] = "";
                row["aptDateTime"] = DateTime.MinValue;
                row["AptNum"] = 0;
                row["clinic"] = "";
                row["CodeNum"] = "";
                row["colorBackG"] = Color.White.ToArgb();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][6].ItemColor.ToArgb().ToString();
                //needs to change
                row["CommlogNum"] = 0;
                row["dateEntryC"] = "";
                row["dateTP"] = "";
                txt = "";
                if (StringSupport.equals(rawEmail.Rows[i]["SentOrReceived"].ToString(), "0"))
                {
                    txt = Lans.g("ChartModule","(unsent) ");
                }
                 
                row["description"] = Lans.g("ChartModule","Email - ") + txt + rawEmail.Rows[i]["Subject"].ToString();
                row["dx"] = "";
                row["Dx"] = "";
                row["EmailMessageNum"] = rawEmail.Rows[i]["EmailMessageNum"].ToString();
                row["FormPatNum"] = 0;
                row["HideGraphics"] = "";
                row["isLocked"] = "";
                row["LabCaseNum"] = 0;
                row["length"] = "";
                row["note"] = rawEmail.Rows[i]["BodyText"].ToString();
                row["orionDateScheduleBy"] = "";
                row["orionDateStopClock"] = "";
                row["orionDPC"] = "";
                row["orionDPCpost"] = "";
                row["orionIsEffectiveComm"] = "";
                row["orionIsOnCall"] = "";
                row["orionStatus2"] = "";
                row["PatNum"] = "";
                row["Priority"] = "";
                row["priority"] = "";
                row["ProcCode"] = "";
                //row["PatNum"]=rawEmail.Rows[i]["PatNum"].ToString();
                dateT = PIn.DateT(rawEmail.Rows[i]["msgDateTime"].ToString());
                if (dateT.Year < 1880)
                {
                    row["procDate"] = "";
                }
                else
                {
                    row["procDate"] = dateT.ToString(Lans.getShortDateTimeFormat());
                } 
                row["ProcDate"] = dateT;
                row["procTime"] = "";
                if (dateT.TimeOfDay != TimeSpan.Zero)
                {
                    row["procTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
                }
                 
                row["procTimeEnd"] = "";
                row["procFee"] = "";
                row["ProcNum"] = 0;
                row["ProcNumLab"] = "";
                row["procStatus"] = "";
                row["ProcStatus"] = "";
                row["prov"] = "";
                row["quadrant"] = "";
                row["RxNum"] = 0;
                row["SheetNum"] = 0;
                row["signature"] = "";
                row["Surf"] = "";
                row["TaskNum"] = 0;
                row["toothNum"] = "";
                row["ToothNum"] = "";
                row["ToothRange"] = "";
                row["user"] = "";
                rows.Add(row);
            }
        }
         
        if (componentsToLoad.ShowSheets)
        {
            //rx are only accesssible from within Rx edit window.
            command = "SELECT Description,SheetNum,DateTimeSheet,SheetType " + "FROM sheet " + "WHERE PatNum=" + POut.long(patNum) + " AND SheetType!=" + POut.Long(((Enum)SheetTypeEnum.Rx).ordinal()) + " AND SheetType!=" + POut.Long(((Enum)SheetTypeEnum.LabSlip).ordinal()) + " ORDER BY DateTimeSheet";
            //labslips are only accesssible from within the labslip edit window.
            DataTable rawSheet = dcon.getTable(command);
            for (int i = 0;i < rawSheet.Rows.Count;i++)
            {
                //SheetTypeEnum sheetType;
                row = table.NewRow();
                row["AbbrDesc"] = "";
                row["aptDateTime"] = DateTime.MinValue;
                row["AptNum"] = 0;
                row["clinic"] = "";
                row["CodeNum"] = "";
                row["colorBackG"] = Color.White.ToArgb();
                row["colorText"] = Color.Black.ToArgb();
                //DefC.Long[(int)DefCat.ProgNoteColors][6].ItemColor.ToArgb().ToString();//needs to change
                row["CommlogNum"] = 0;
                dateT = PIn.DateT(rawSheet.Rows[i]["DateTimeSheet"].ToString());
                if (dateT.Year < 1880)
                {
                    row["dateEntryC"] = "";
                    row["dateTP"] = "";
                }
                else
                {
                    row["dateEntryC"] = dateT.ToString(Lans.getShortDateTimeFormat());
                    row["dateTP"] = dateT.ToString(Lans.getShortDateTimeFormat());
                } 
                //sheetType=(SheetTypeEnum)PIn.PLong(rawSheet.Rows[i]["SheetType"].ToString());
                row["description"] = rawSheet.Rows[i]["Description"].ToString();
                row["dx"] = "";
                row["Dx"] = "";
                row["EmailMessageNum"] = 0;
                row["FormPatNum"] = 0;
                row["HideGraphics"] = "";
                row["isLocked"] = "";
                row["LabCaseNum"] = 0;
                row["length"] = "";
                row["note"] = "";
                row["orionDateScheduleBy"] = "";
                row["orionDateStopClock"] = "";
                row["orionDPC"] = "";
                row["orionDPCpost"] = "";
                row["orionIsEffectiveComm"] = "";
                row["orionIsOnCall"] = "";
                row["orionStatus2"] = "";
                row["PatNum"] = "";
                row["Priority"] = "";
                row["priority"] = "";
                row["ProcCode"] = "";
                if (dateT.Year < 1880)
                {
                    row["procDate"] = "";
                }
                else
                {
                    row["procDate"] = dateT.ToString(Lans.getShortDateTimeFormat());
                } 
                row["ProcDate"] = dateT;
                row["procTime"] = "";
                if (dateT.TimeOfDay != TimeSpan.Zero)
                {
                    row["procTime"] = dateT.ToString("h:mm") + dateT.ToString("%t").ToLower();
                }
                 
                row["procTimeEnd"] = "";
                row["procFee"] = "";
                row["ProcNum"] = 0;
                row["ProcNumLab"] = "";
                row["procStatus"] = "";
                row["ProcStatus"] = "";
                row["prov"] = "";
                row["quadrant"] = "";
                row["RxNum"] = 0;
                row["SheetNum"] = rawSheet.Rows[i]["SheetNum"].ToString();
                row["signature"] = "";
                row["Surf"] = "";
                row["TaskNum"] = 0;
                row["toothNum"] = "";
                row["ToothNum"] = "";
                row["ToothRange"] = "";
                row["user"] = "";
                rows.Add(row);
            }
        }
         
        rows.Sort(CompareChartRows);
        for (int i = 0;i < labRows.Count;i++)
        {
            for (int r = 0;r < rows.Count;r++)
            {
                //Canadian lab procedures need to come immediately after their corresponding proc---------------------------------
                if (rows[r]["ProcNum"].ToString() == labRows[i]["ProcNumLab"].ToString())
                {
                    rows.Insert(r + 1, labRows[i]);
                    break;
                }
                 
            }
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    public static DataTable getPlannedApt(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), patNum);
        }
         
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = new DataTable("Planned");
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("AptNum");
        table.Columns.Add("colorBackG");
        table.Columns.Add("colorText");
        table.Columns.Add("dateSched");
        table.Columns.Add("ItemOrder");
        table.Columns.Add("minutes");
        table.Columns.Add("Note");
        table.Columns.Add("ProcDescript");
        table.Columns.Add("PlannedApptNum");
        //but we won't actually fill this table with rows until the very end.  It's more useful to use a List<> for now.
        List<DataRow> rows = new List<DataRow>();
        //The query below was causing a max join error for big offices.  It's fixed now,
        //but a better option for next time would be to put SET SQL_BIG_SELECTS=1; before the query.
        //COUNT(procedurelog.ProcNum) someAreComplete "//The count won't be accurate, but it will tell us if not zero.
        //+"LEFT JOIN procedurelog ON procedurelog.PlannedAptNum=plannedappt.AptNum "//grab all attached completed procs
        //+"AND procedurelog.ProcStatus=2 "
        String command = "SELECT plannedappt.AptNum,ItemOrder,PlannedApptNum,appointment.AptDateTime," + "appointment.Pattern,appointment.AptStatus," + "(SELECT COUNT(*) FROM procedurelog WHERE procedurelog.PlannedAptNum=plannedappt.AptNum AND procedurelog.ProcStatus=2) someAreComplete " + "FROM plannedappt " + "LEFT JOIN appointment ON appointment.NextAptNum=plannedappt.AptNum " + "WHERE plannedappt.PatNum=" + POut.long(patNum) + " " + "GROUP BY plannedappt.AptNum,ItemOrder,PlannedApptNum,appointment.AptDateTime," + "appointment.Pattern,appointment.AptStatus " + "ORDER BY ItemOrder";
        //plannedappt.AptNum does refer to the planned appt, but the other fields in the result are for the linked scheduled appt.
        DataTable rawPlannedAppts = dcon.getTable(command);
        DataRow aptRow = new DataRow();
        int itemOrder = 1;
        DateTime dateSched = new DateTime();
        ApptStatus aptStatus = ApptStatus.None;
        for (int i = 0;i < rawPlannedAppts.Rows.Count;i++)
        {
            aptRow = null;
            for (int a = 0;a < rawApt.Rows.Count;a++)
            {
                if (rawApt.Rows[a]["AptNum"].ToString() == rawPlannedAppts.Rows[i]["AptNum"].ToString())
                {
                    aptRow = rawApt.Rows[a];
                    break;
                }
                 
            }
            if (aptRow == null)
            {
                continue;
            }
             
            //this will have to be fixed in dbmaint.
            //repair any item orders here rather than in dbmaint. It's really fast.
            if (itemOrder.ToString() != rawPlannedAppts.Rows[i]["ItemOrder"].ToString())
            {
                command = "UPDATE plannedappt SET ItemOrder=" + POut.Long(itemOrder) + " WHERE PlannedApptNum=" + rawPlannedAppts.Rows[i]["PlannedApptNum"].ToString();
                dcon.nonQ(command);
            }
             
            //end of repair
            row = table.NewRow();
            row["AptNum"] = aptRow["AptNum"].ToString();
            dateSched = PIn.Date(rawPlannedAppts.Rows[i]["AptDateTime"].ToString());
            //Colors----------------------------------------------------------------------------
            aptStatus = (ApptStatus)PIn.Long(rawPlannedAppts.Rows[i]["AptStatus"].ToString());
            //change color if completed, broken, or unscheduled no matter the date
            if (aptStatus == ApptStatus.Broken || aptStatus == ApptStatus.UnschedList)
            {
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][15].ItemColor.ToArgb().ToString();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][14].ItemColor.ToArgb().ToString();
            }
            else if (aptStatus == ApptStatus.Complete)
            {
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][11].ItemColor.ToArgb().ToString();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][10].ItemColor.ToArgb().ToString();
            }
            else if (aptStatus == ApptStatus.Scheduled && dateSched.Date != DateTime.Today.Date)
            {
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][13].ItemColor.ToArgb().ToString();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][12].ItemColor.ToArgb().ToString();
            }
            else if (dateSched.Date < DateTime.Today && dateSched != DateTime.MinValue)
            {
                //Past
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][11].ItemColor.ToArgb().ToString();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][10].ItemColor.ToArgb().ToString();
            }
            else if (dateSched.Date == DateTime.Today.Date)
            {
                //Today
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][9].ItemColor.ToArgb().ToString();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][8].ItemColor.ToArgb().ToString();
            }
            else if (dateSched.Date > DateTime.Today)
            {
                //Future
                row["colorBackG"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][13].ItemColor.ToArgb().ToString();
                row["colorText"] = DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][12].ItemColor.ToArgb().ToString();
            }
            else
            {
                row["colorBackG"] = Color.White.ToArgb().ToString();
                row["colorText"] = Color.Black.ToArgb().ToString();
            }      
            //end of colors------------------------------------------------------------------------------
            if (dateSched.Year < 1880)
            {
                row["dateSched"] = "";
            }
            else
            {
                row["dateSched"] = dateSched.ToShortDateString();
            } 
            row["ItemOrder"] = itemOrder.ToString();
            row["minutes"] = (aptRow["Pattern"].ToString().Length * 5).ToString();
            row["Note"] = aptRow["Note"].ToString();
            row["PlannedApptNum"] = rawPlannedAppts.Rows[i]["PlannedApptNum"].ToString();
            row["ProcDescript"] = aptRow["ProcDescript"].ToString();
            if (aptStatus == ApptStatus.Complete)
            {
                row["ProcDescript"] = Lans.g("ContrChart","(Completed) ") + row["ProcDescript"];
            }
            else if (dateSched == DateTime.Today.Date)
            {
                row["ProcDescript"] = Lans.g("ContrChart","(Today's) ") + row["ProcDescript"];
            }
            else if (!StringSupport.equals(rawPlannedAppts.Rows[i]["someAreComplete"].ToString(), "0"))
            {
                row["ProcDescript"] = Lans.g("ContrChart","(Some procs complete) ") + row["ProcDescript"];
            }
               
            rows.Add(row);
            itemOrder++;
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * The supplied DataRows must include the following columns: ProcNum,ProcDate,Priority,ToothRange,ToothNum,ProcCode. This sorts all objects in Chart module based on their dates, times, priority, and toothnum.  For time comparisons, procs are not included.  But if other types such as comm have a time component in ProcDate, then they will be sorted by time as well.
    */
    public static int compareChartRows(DataRow x, DataRow y) throws Exception {
        //if dates are different, then sort by date
        if (((DateTime)x["ProcDate"]).Date != ((DateTime)y["ProcDate"]).Date)
        {
            return ((DateTime)x["ProcDate"]).Date.CompareTo(((DateTime)y["ProcDate"]).Date);
        }
         
        //Sort by Type. Types are: Appointments, Procedures, CommLog, Tasks, Email, Lab Cases, Rx, Sheets.----------------------------------------------------
        int xInd = 0;
        if (!StringSupport.equals(x["AptNum"].ToString(), "0"))
        {
            xInd = 0;
        }
        else if (!StringSupport.equals(x["ProcNum"].ToString(), "0"))
        {
            xInd = 1;
        }
        else if (!StringSupport.equals(x["CommlogNum"].ToString(), "0"))
        {
            xInd = 2;
        }
        else //commlogs and tasks are intermingled and sorted by time
        if (!StringSupport.equals(x["TaskNum"].ToString(), "0"))
        {
            xInd = 2;
        }
        else //commlogs and tasks are intermingled and sorted by time
        if (!StringSupport.equals(x["EmailMessageNum"].ToString(), "0"))
        {
            xInd = 3;
        }
        else if (!StringSupport.equals(x["LabCaseNum"].ToString(), "0"))
        {
            xInd = 4;
        }
        else if (!StringSupport.equals(x["RxNum"].ToString(), "0"))
        {
            xInd = 5;
        }
        else if (!StringSupport.equals(x["SheetNum"].ToString(), "0"))
        {
            xInd = 6;
        }
                
        int yInd = 0;
        if (!StringSupport.equals(y["AptNum"].ToString(), "0"))
        {
            yInd = 0;
        }
        else if (!StringSupport.equals(y["ProcNum"].ToString(), "0"))
        {
            yInd = 1;
        }
        else if (!StringSupport.equals(y["CommlogNum"].ToString(), "0"))
        {
            yInd = 2;
        }
        else //commlogs and tasks are intermingled and sorted by time
        if (!StringSupport.equals(y["TaskNum"].ToString(), "0"))
        {
            yInd = 2;
        }
        else //commlogs and tasks are intermingled and sorted by time
        if (!StringSupport.equals(y["EmailMessageNum"].ToString(), "0"))
        {
            yInd = 3;
        }
        else if (!StringSupport.equals(y["LabCaseNum"].ToString(), "0"))
        {
            yInd = 4;
        }
        else if (!StringSupport.equals(y["RxNum"].ToString(), "0"))
        {
            yInd = 5;
        }
        else if (!StringSupport.equals(y["SheetNum"].ToString(), "0"))
        {
            yInd = 6;
        }
                
        if (xInd != yInd)
        {
            return xInd.CompareTo(yInd);
        }
         
        //End sort by type------------------------------------------------------------------------------------------------------------------------------------
        //Sort procedures by status, priority, tooth region/num, proc code
        if (!StringSupport.equals(x["ProcNum"].ToString(), "0") && !StringSupport.equals(y["ProcNum"].ToString(), "0"))
        {
            return ProcedureLogic.compareProcedures(x,y);
        }
         
        //if both are procedures
        //nothing below this point can be a procedure.
        //dates are guaranteed to match at this point.
        //they are also guaranteed to be the same type (commlogs and tasks intermingled).
        //Sort other types by time-----------------------------------------------------------------------------------------------------------------------------
        if (((DateTime)x["ProcDate"]) != ((DateTime)y["ProcDate"]))
        {
            return ((DateTime)x["ProcDate"]).CompareTo(((DateTime)y["ProcDate"]));
        }
         
        return 0;
    }

}


