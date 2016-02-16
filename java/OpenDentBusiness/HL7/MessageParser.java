//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.HL7.FieldParser;
import OpenDentBusiness.HL7.MessageHL7;
import OpenDentBusiness.HL7.SegmentHL7;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.HL7DefMessage;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.HL7DefSegment;
import OpenDentBusiness.HL7MessageStatus;
import OpenDentBusiness.HL7Msg;
import OpenDentBusiness.HL7Msgs;
import OpenDentBusiness.InOutHL7;
import OpenDentBusiness.MessageTypeHL7;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientRaceOld;
import OpenDentBusiness.PatientRaces;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PIn;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.SegmentNameHL7;

/**
* This is the engine that will parse our incoming HL7 messages.
*/
public class MessageParser   
{
    private static boolean IsNewPat = new boolean();
    private static boolean IsVerboseLogging = new boolean();
    private static HL7Msg HL7MsgCur;
    //Open \\SERVERFILES\storage\OPEN DENTAL\Programmers Documents\Standards (X12, ADA, etc)\HL7\Version2.6\V26_CH02_Control_M4_JAN2007.doc
    //At the top of page 33, there are rules for the recipient.
    //Basically, they state that parsing should not fail just because there are extra unexpected items.
    //And parsing should also not fail if expected items are missing.
    public static void process(MessageHL7 msg, boolean isVerboseLogging) throws Exception {
        HL7MsgCur = new HL7Msg();
        HL7MsgCur.HL7Status = HL7MessageStatus.InFailed;
        //it will be marked InProcessed once data is inserted.
        HL7MsgCur.MsgText = msg.toString();
        HL7MsgCur.PatNum = 0;
        HL7MsgCur.AptNum = 0;
        List<HL7Msg> hl7Existing = HL7Msgs.GetOneExisting(HL7MsgCur);
        if (hl7Existing.Count > 0)
        {
            //This message is already in the db
            HL7MsgCur.HL7MsgNum = hl7Existing[0].HL7MsgNum;
            HL7Msgs.UpdateDateTStamp(HL7MsgCur);
            msg.ControlId = HL7Msgs.GetControlId(HL7MsgCur);
            return ;
        }
        else
        {
            //Insert as InFailed until processing is complete.  Update once complete, PatNum will have correct value, AptNum will have correct value if SIU message or 0 if ADT, and status changed to InProcessed
            HL7Msgs.Insert(HL7MsgCur);
        } 
        IsVerboseLogging = isVerboseLogging;
        IsNewPat = false;
        HL7Def def = HL7Defs.getOneDeepEnabled();
        if (def == null)
        {
            HL7MsgCur.Note = "Could not process HL7 message.  No HL7 definition is enabled.";
            HL7Msgs.Update(HL7MsgCur);
            throw new Exception("Could not process HL7 message.  No HL7 definition is enabled.");
        }
         
        HL7DefMessage hl7defmsg = null;
        for (int i = 0;i < def.hl7DefMessages.Count;i++)
        {
            if (def.hl7DefMessages[i].MessageType == msg.MsgType)
            {
                //&& def.hl7DefMessages[i].EventType==msg.EventType) { //Ignoring event type for now, we will treat all ADT's and SIU's the same
                hl7defmsg = def.hl7DefMessages[i];
            }
             
        }
        if (hl7defmsg == null)
        {
            //No message definition matches this message's MessageType and EventType
            HL7MsgCur.Note = "Could not process HL7 message.  No definition for this type of message in the enabled HL7Def.";
            HL7Msgs.Update(HL7MsgCur);
            throw new Exception("Could not process HL7 message.  No definition for this type of message in the enabled HL7Def.");
        }
         
        String chartNum = null;
        long patNum = 0;
        DateTime birthdate = DateTime.MinValue;
        String patLName = null;
        String patFName = null;
        boolean isExistingPID = false;
        for (int s = 0;s < hl7defmsg.hl7DefSegments.Count;s++)
        {
            //Needed to add note to hl7msg table if there isn't a PID segment in the message.
            //Get patient in question, incoming messages must have a PID segment so use that to find the pat in question
            if (hl7defmsg.hl7DefSegments[s].SegmentName != SegmentNameHL7.PID)
            {
                continue;
            }
             
            int pidOrder = hl7defmsg.hl7DefSegments[s].ItemOrder;
            //we found the PID segment in the def, make sure it exists in the msg
            //If the number of segments in the message is less than the item order of the PID segment
            if (msg.Segments.Count <= pidOrder || !StringSupport.equals(msg.Segments[pidOrder].GetField(0).ToString(), "PID"))
            {
                break;
            }
             
            //Or if the segment we expect to be the PID segment is not the PID segment
            isExistingPID = true;
            for (int f = 0;f < hl7defmsg.hl7DefSegments[s].hl7DefFields.Count;f++)
            {
                //Go through fields of PID segment and get patnum, chartnum, patient name, and/or birthdate to locate patient
                if (StringSupport.equals(hl7defmsg.hl7DefSegments[s].hl7DefFields[f].FieldName, "pat.ChartNumber"))
                {
                    int chartNumOrdinal = hl7defmsg.hl7DefSegments[s].hl7DefFields[f].OrdinalPos;
                    chartNum = msg.Segments[pidOrder].Fields[chartNumOrdinal].ToString();
                }
                else if (StringSupport.equals(hl7defmsg.hl7DefSegments[s].hl7DefFields[f].FieldName, "pat.PatNum"))
                {
                    int patNumOrdinal = hl7defmsg.hl7DefSegments[s].hl7DefFields[f].OrdinalPos;
                    patNum = PIn.Long(msg.Segments[pidOrder].Fields[patNumOrdinal].ToString());
                }
                else if (StringSupport.equals(hl7defmsg.hl7DefSegments[s].hl7DefFields[f].FieldName, "pat.birthdateTime"))
                {
                    int patBdayOrdinal = hl7defmsg.hl7DefSegments[s].hl7DefFields[f].OrdinalPos;
                    birthdate = FieldParser.DateTimeParse(msg.Segments[pidOrder].Fields[patBdayOrdinal].ToString());
                }
                else if (StringSupport.equals(hl7defmsg.hl7DefSegments[s].hl7DefFields[f].FieldName, "pat.nameLFM"))
                {
                    int patNameOrdinal = hl7defmsg.hl7DefSegments[s].hl7DefFields[f].OrdinalPos;
                    patLName = msg.Segments[pidOrder].GetFieldComponent(patNameOrdinal, 0);
                    patFName = msg.Segments[pidOrder].GetFieldComponent(patNameOrdinal, 1);
                }
                    
            }
        }
        if (!isExistingPID)
        {
            HL7MsgCur.Note = "Could not process the HL7 message due to missing PID segment.";
            HL7Msgs.Update(HL7MsgCur);
            throw new Exception("Could not process HL7 message.  Could not process the HL7 message due to missing PID segment.");
        }
         
        //We now have patnum, chartnum, patname, and/or birthdate so locate pat
        Patient pat = null;
        Patient patOld = null;
        if (patNum != 0)
        {
            pat = Patients.getPat(patNum);
        }
         
        if (!StringSupport.equals(def.InternalType, "eCWStandalone") && pat == null)
        {
            IsNewPat = true;
        }
         
        //In eCWstandalone integration, if we couldn't locate patient by patNum or patNum was 0 then pat will still be null so try to locate by chartNum if chartNum is not null
        if (StringSupport.equals(def.InternalType, "eCWStandalone") && chartNum != null)
        {
            pat = Patients.getPatByChartNumber(chartNum);
        }
         
        //In eCWstandalone integration, if pat is still null we need to try to locate patient by name and birthdate
        if (StringSupport.equals(def.InternalType, "eCWStandalone") && pat == null)
        {
            long patNumByName = Patients.getPatNumByNameAndBirthday(patLName,patFName,birthdate);
            //If patNumByName is 0 we couldn't locate by patNum, chartNum or name and birthdate so this message must be for a new patient
            if (patNumByName == 0)
            {
                IsNewPat = true;
            }
            else
            {
                pat = Patients.getPat(patNumByName);
                patOld = pat.copy();
                pat.ChartNumber = chartNum;
                //from now on, we will be able to find pat by chartNumber
                Patients.update(pat,patOld);
            } 
        }
         
        if (IsNewPat)
        {
            pat = new Patient();
            if (chartNum != null)
            {
                pat.ChartNumber = chartNum;
            }
             
            if (patNum != 0)
            {
                pat.PatNum = patNum;
                pat.Guarantor = patNum;
            }
             
            pat.PriProv = PrefC.getLong(PrefName.PracticeDefaultProv);
            pat.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
        }
        else
        {
            patOld = pat.copy();
        } 
        //Update hl7msg table with correct PatNum for this message
        HL7MsgCur.PatNum = pat.PatNum;
        HL7Msgs.Update(HL7MsgCur);
        //If this is a message that contains an SCH segment, loop through to find the AptNum.  Pass it to the other segments that will need it.
        long aptNum = 0;
        for (int s = 0;s < hl7defmsg.hl7DefSegments.Count;s++)
        {
            if (hl7defmsg.hl7DefSegments[s].SegmentName != SegmentNameHL7.SCH)
            {
                continue;
            }
             
            //we found the SCH segment
            int schOrder = hl7defmsg.hl7DefSegments[s].ItemOrder;
            for (int f = 0;f < hl7defmsg.hl7DefSegments[s].hl7DefFields.Count;f++)
            {
                //Go through fields of SCH segment and get AptNum
                if (StringSupport.equals(hl7defmsg.hl7DefSegments[s].hl7DefFields[f].FieldName, "apt.AptNum"))
                {
                    int aptNumOrdinal = hl7defmsg.hl7DefSegments[s].hl7DefFields[f].OrdinalPos;
                    aptNum = PIn.Long(msg.Segments[schOrder].Fields[aptNumOrdinal].ToString());
                }
                 
            }
        }
        //We now have a patient object , either loaded from the db or new, and aptNum so process this message for this patient
        //We need to insert the pat to get a patnum so we can compare to guar patnum to see if relationship to guar is self
        if (IsNewPat)
        {
            if (isVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "Inserted patient: " + pat.FName + " " + pat.LName, EventLogEntryType.Information);
            }
             
            if (pat.PatNum == 0)
            {
                pat.PatNum = Patients.insert(pat,false);
            }
            else
            {
                pat.PatNum = Patients.insert(pat,true);
            } 
            patOld = pat.copy();
        }
         
        for (int i = 0;i < hl7defmsg.hl7DefSegments.Count;i++)
        {
            try
            {
                SegmentHL7 seg = msg.GetSegment(hl7defmsg.hl7DefSegments[i].SegmentName, !hl7defmsg.hl7DefSegments[i].IsOptional);
                if (seg != null)
                {
                    //null if segment was not found but is optional
                    ProcessSeg(pat, aptNum, hl7defmsg.hl7DefSegments[i], seg, msg);
                }
                 
            }
            catch (ApplicationException ex)
            {
                //Required segment was missing, or other error.
                HL7MsgCur.Note = "Could not process this HL7 message.  " + ex;
                HL7Msgs.Update(HL7MsgCur);
                throw new Exception("Could not process HL7 message.  " + ex);
            }
        
        }
        //We have processed the message so now update or insert the patient
        if (StringSupport.equals(pat.FName, "") || StringSupport.equals(pat.LName, ""))
        {
            EventLog.WriteEntry("OpenDentHL7", "Patient demographics not processed due to missing first or last name. PatNum:" + pat.PatNum.ToString(), EventLogEntryType.Information);
            HL7MsgCur.Note = "Patient demographics not processed due to missing first or last name. PatNum:" + pat.PatNum.ToString();
            HL7Msgs.Update(HL7MsgCur);
            return ;
        }
         
        if (IsNewPat)
        {
            if (pat.Guarantor == 0)
            {
                pat.Guarantor = pat.PatNum;
                Patients.update(pat,patOld);
            }
            else
            {
                Patients.update(pat,patOld);
            } 
        }
        else
        {
            if (isVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "Updated patient: " + pat.FName + " " + pat.LName, EventLogEntryType.Information);
            }
             
            Patients.update(pat,patOld);
        } 
        HL7MsgCur.HL7Status = HL7MessageStatus.InProcessed;
        HL7Msgs.Update(HL7MsgCur);
    }

    public static void processAck(MessageHL7 msg, boolean isVerboseLogging) throws Exception {
        IsVerboseLogging = isVerboseLogging;
        HL7Def def = HL7Defs.getOneDeepEnabled();
        if (def == null)
        {
            throw new Exception("Could not process ACK.  No HL7 definition is enabled.");
        }
         
        HL7DefMessage hl7defmsg = null;
        for (int i = 0;i < def.hl7DefMessages.Count;i++)
        {
            if (def.hl7DefMessages[i].MessageType == MessageTypeHL7.ACK && def.hl7DefMessages[i].InOrOut == InOutHL7.Incoming)
            {
                hl7defmsg = def.hl7DefMessages[i];
                break;
            }
             
        }
        if (hl7defmsg == null)
        {
            throw new Exception("Could not process HL7 ACK message.  No definition for this type of message in the enabled HL7Def.");
        }
         
        for (int i = 0;i < hl7defmsg.hl7DefSegments.Count;i++)
        {
            //No incoming ACK defined, do nothing with it
            try
            {
                SegmentHL7 seg = msg.GetSegment(hl7defmsg.hl7DefSegments[i].SegmentName, !hl7defmsg.hl7DefSegments[i].IsOptional);
                if (seg != null)
                {
                    //null if segment was not found but is optional
                    ProcessSeg(null, 0, hl7defmsg.hl7DefSegments[i], seg, msg);
                }
                 
            }
            catch (ApplicationException ex)
            {
                throw new Exception("Could not process HL7 message.  " + ex);
            }
        
        }
    }

    //Required segment was missing, or other error.
    public static void processSeg(Patient pat, long aptNum, HL7DefSegment segDef, SegmentHL7 seg, MessageHL7 msg) throws Exception {
        switch(segDef.SegmentName)
        {
            case AIG: 
                ProcessAIG(pat, aptNum, segDef, seg);
                return ;
            case GT1: 
                ProcessGT1(pat, segDef, seg);
                return ;
            case IN1: 
                return ;
            case MSA: 
                //ProcessIN1();
                ProcessMSA(segDef, seg, msg);
                return ;
            case MSH: 
                ProcessMSH(segDef, seg, msg);
                return ;
            case PD1: 
                return ;
            case PID: 
                //ProcessPD1();
                ProcessPID(pat, segDef, seg);
                return ;
            case PV1: 
                ProcessPV1(pat, aptNum, segDef, seg);
                return ;
            case SCH: 
                ProcessSCH(pat, segDef, seg);
                return ;
            default: 
                return ;
        
        }
    }

    public static void processAIG(Patient pat, long aptNum, HL7DefSegment segDef, SegmentHL7 seg) throws Exception {
        long provNum = new long();
        int provNumOrder = 0;
        for (int i = 0;i < segDef.hl7DefFields.Count;i++)
        {
            if (StringSupport.equals(segDef.hl7DefFields[i].FieldName, "prov.provIdNameLFM") || StringSupport.equals(segDef.hl7DefFields[i].FieldName, "prov.provIdName"))
            {
                provNumOrder = segDef.hl7DefFields[i].OrdinalPos;
                break;
            }
             
        }
        if (provNumOrder == 0)
        {
            return ;
        }
         
        //No provIdNameLFM or provIdName field in this segment definition so do nothing with it
        provNum = FieldParser.ProvProcess(seg.Fields[provNumOrder]);
        if (provNum == 0)
        {
            return ;
        }
         
        //This segment didn't have a valid provider id in it to locate the provider (must have been blank)
        Appointment apt = Appointments.getOneApt(aptNum);
        //SCH segment was found and aptNum was retrieved, if no SCH segment for this message then 0
        if (apt == null)
        {
            return ;
        }
         
        //Just in case we can't find an apt with the aptNum from SCH segment
        Appointment aptOld = apt.clone();
        Patient patOld = pat.copy();
        apt.ProvNum = provNum;
        pat.PriProv = provNum;
        Appointments.update(apt,aptOld);
        Patients.update(pat,patOld);
        return ;
    }

    /**
    * If relationship is self, this method does nothing.  A new pat will later change guarantor to be same as patnum.
    */
    public static void processGT1(Patient pat, HL7DefSegment segDef, SegmentHL7 seg) throws Exception {
        //Find the position of the guarNum, guarChartNum, guarName, and guarBirthdate in this HL7 segment based on the definition of a GT1
        int guarPatNumOrdinal = -1;
        int guarChartNumOrdinal = -1;
        int guarNameOrdinal = -1;
        int guarBirthdateOrdinal = -1;
        for (int i = 0;i < segDef.hl7DefFields.Count;i++)
        {
            if (StringSupport.equals(segDef.hl7DefFields[i].FieldName, "guar.PatNum"))
            {
                guarPatNumOrdinal = segDef.hl7DefFields[i].OrdinalPos;
            }
             
            if (StringSupport.equals(segDef.hl7DefFields[i].FieldName, "guar.ChartNumber"))
            {
                guarChartNumOrdinal = segDef.hl7DefFields[i].OrdinalPos;
            }
             
            if (StringSupport.equals(segDef.hl7DefFields[i].FieldName, "guar.nameLFM"))
            {
                guarNameOrdinal = segDef.hl7DefFields[i].OrdinalPos;
            }
             
            if (StringSupport.equals(segDef.hl7DefFields[i].FieldName, "guar.birthdateTime"))
            {
                guarBirthdateOrdinal = segDef.hl7DefFields[i].OrdinalPos;
            }
             
        }
        //If neither guar.PatNum nor guar.ChartNumber are included in this GT1 definition log a message in the event log and return
        if (guarPatNumOrdinal == -1 && guarChartNumOrdinal == -1)
        {
            HL7MsgCur.Note = "Guarantor not processed.  guar.PatNum or guar.ChartNumber must be included in the GT1 definition.  PatNum of patient:" + pat.PatNum.ToString();
            HL7Msgs.Update(HL7MsgCur);
            EventLog.WriteEntry("OpenDentHL7", "Guarantor not processed.  guar.PatNum or guar.ChartNumber must be included in the GT1 definition.  PatNum of patient:" + pat.PatNum.ToString(), EventLogEntryType.Information);
            return ;
        }
         
        //If guar.nameLFM is not included in this GT1 definition log a message in the event log and return
        if (guarNameOrdinal == -1)
        {
            HL7MsgCur.Note = "Guarantor not processed due to guar.nameLFM not included in the GT1 definition.  Patnum of patient:" + pat.PatNum.ToString();
            HL7Msgs.Update(HL7MsgCur);
            EventLog.WriteEntry("OpenDentHL7", "Guarantor not processed due to guar.nameLFM not included in the GT1 definition.  Patnum of patient:" + pat.PatNum.ToString(), EventLogEntryType.Information);
            return ;
        }
         
        //If the first or last name are not included in this GT1 segment, log a message in the event log and return
        if (StringSupport.equals(seg.getFieldComponent(guarNameOrdinal,0), "") || StringSupport.equals(seg.getFieldComponent(guarNameOrdinal,1), ""))
        {
            HL7MsgCur.Note = "Guarantor not processed due to missing first or last name.  PatNum of patient:" + pat.PatNum.ToString();
            HL7Msgs.Update(HL7MsgCur);
            EventLog.WriteEntry("OpenDentHL7", "Guarantor not processed due to missing first or last name.  PatNum of patient:" + pat.PatNum.ToString(), EventLogEntryType.Information);
            return ;
        }
         
        //Only process GT1 if either guar.PatNum or guar.ChartNumber is included and both guar.LName and guar.FName are included
        long guarPatNum = 0;
        String guarChartNum = "";
        if (guarPatNumOrdinal != -1)
        {
            guarPatNum = PIn.long(seg.getFieldFullText(guarPatNumOrdinal));
        }
         
        if (guarChartNumOrdinal != -1)
        {
            guarChartNum = seg.getFieldComponent(guarChartNumOrdinal);
        }
         
        if (guarPatNum == 0 && StringSupport.equals(guarChartNum, ""))
        {
            //because we have an example where they sent us this (position 2 (guarPatNumOrder or guarChartNumOrder for eCW) is empty): GT1|1||^^||^^^^||||||||
            HL7MsgCur.Note = "Guarantor not processed due to missing both guar.PatNum and guar.ChartNumber.  One of those numbers must be included.  PatNum of patient:" + pat.PatNum.ToString();
            HL7Msgs.Update(HL7MsgCur);
            EventLog.WriteEntry("OpenDentHL7", "Guarantor not processed due to missing both guar.PatNum and guar.ChartNumber.  One of those numbers must be included.  PatNum of patient:" + pat.PatNum.ToString(), EventLogEntryType.Information);
            return ;
        }
         
        if (guarPatNum == pat.PatNum || StringSupport.equals(guarChartNum, pat.ChartNumber))
        {
            return ;
        }
         
        //if relationship is self
        //Guar must be someone else
        Patient guar = null;
        Patient guarOld = null;
        //Find guarantor by guar.PatNum if defined and in this segment
        if (guarPatNum != 0)
        {
            guar = Patients.getPat(guarPatNum);
        }
        else
        {
            //guarPatNum was 0 so try to get guar by guar.ChartNumber or name and birthdate
            //try to find guarantor using chartNumber
            guar = Patients.getPatByChartNumber(guarChartNum);
            if (guar == null)
            {
                //try to find the guarantor by using name and birthdate
                String guarLName = seg.getFieldComponent(guarNameOrdinal,0);
                String guarFName = seg.getFieldComponent(guarNameOrdinal,1);
                DateTime guarBirthdate = FieldParser.dateTimeParse(seg.getFieldFullText(guarBirthdateOrdinal));
                long guarNumByName = Patients.getPatNumByNameAndBirthday(guarLName,guarFName,guarBirthdate);
                if (guarNumByName == 0)
                {
                }
                else
                {
                    //guarantor does not exist in OD
                    //so guar will still be null, triggering creation of new guarantor further down.
                    guar = Patients.getPat(guarNumByName);
                    guarOld = guar.copy();
                    guar.ChartNumber = guarChartNum;
                    //from now on, we will be able to find guar by chartNumber
                    Patients.update(guar,guarOld);
                } 
            }
             
        } 
        //At this point we have a guarantor located in OD or guar=null so guar is new patient
        boolean isNewGuar = guar == null;
        if (isNewGuar)
        {
            //then we need to add guarantor to db
            guar = new Patient();
            if (guarPatNum != 0)
            {
                guar.PatNum = guarPatNum;
            }
            else
            {
                guar.ChartNumber = guarChartNum;
            } 
            guar.PriProv = PrefC.getLong(PrefName.PracticeDefaultProv);
            guar.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
        }
        else
        {
            guarOld = guar.copy();
        } 
        for (int i = 0;i < segDef.hl7DefFields.Count;i++)
        {
            //Now that we have our guarantor, process the GT1 segment
            int itemOrder = segDef.hl7DefFields[i].OrdinalPos;
            FieldName __dummyScrutVar1 = segDef.hl7DefFields[i].FieldName;
            if (__dummyScrutVar1.equals("guar.addressCityStateZip"))
            {
                guar.Address = seg.getFieldComponent(itemOrder,0);
                guar.Address2 = seg.getFieldComponent(itemOrder,1);
                guar.City = seg.getFieldComponent(itemOrder,2);
                guar.State = seg.getFieldComponent(itemOrder,3);
                guar.Zip = seg.getFieldComponent(itemOrder,4);
                continue;
            }
            else if (__dummyScrutVar1.equals("guar.birthdateTime"))
            {
                guar.Birthdate = FieldParser.dateTimeParse(seg.getFieldComponent(itemOrder));
                continue;
            }
            else if (__dummyScrutVar1.equals("guar.ChartNumber"))
            {
                guar.ChartNumber = seg.getFieldComponent(itemOrder);
                continue;
            }
            else if (__dummyScrutVar1.equals("guar.Gender"))
            {
                guar.Gender = FieldParser.genderParse(seg.getFieldComponent(itemOrder));
                continue;
            }
            else if (__dummyScrutVar1.equals("guar.HmPhone"))
            {
                guar.HmPhone = FieldParser.phoneParse(seg.getFieldComponent(itemOrder));
                continue;
            }
            else if (__dummyScrutVar1.equals("guar.nameLFM"))
            {
                guar.LName = seg.getFieldComponent(itemOrder,0);
                guar.FName = seg.getFieldComponent(itemOrder,1);
                guar.MiddleI = seg.getFieldComponent(itemOrder,2);
                continue;
            }
            else //case "guar.PatNum": Maybe do nothing??
            if (__dummyScrutVar1.equals("guar.SSN"))
            {
                guar.SSN = seg.getFieldComponent(itemOrder);
                continue;
            }
            else if (__dummyScrutVar1.equals("guar.WkPhone"))
            {
                guar.WkPhone = FieldParser.phoneParse(seg.getFieldComponent(itemOrder));
                continue;
            }
            else
            {
                continue;
            }        
        }
        if (isNewGuar)
        {
            guarOld = guar.copy();
            if (guar.PatNum == 0)
            {
                guar.PatNum = Patients.insert(guar,false);
            }
            else
            {
                guar.PatNum = Patients.insert(guar,true);
            } 
            guar.Guarantor = guar.PatNum;
            Patients.update(guar,guarOld);
        }
        else
        {
            Patients.update(guar,guarOld);
        } 
        pat.Guarantor = guar.PatNum;
        return ;
    }

    //public static void ProcessIN1() {
    //	return;
    //}
    public static void processMSA(HL7DefSegment segDef, SegmentHL7 seg, MessageHL7 msg) throws Exception {
        int ackCodeOrder = 0;
        int msgControlIdOrder = 0;
        for (int i = 0;i < segDef.hl7DefFields.Count;i++)
        {
            //find position of AckCode in segDef for MSA seg
            if (StringSupport.equals(segDef.hl7DefFields[i].FieldName, "ackCode"))
            {
                ackCodeOrder = segDef.hl7DefFields[i].OrdinalPos;
            }
             
            if (StringSupport.equals(segDef.hl7DefFields[i].FieldName, "messageControlId"))
            {
                msgControlIdOrder = segDef.hl7DefFields[i].OrdinalPos;
            }
             
        }
        if (ackCodeOrder == 0)
        {
            return ;
        }
         
        //no ackCode defined for this def of MSA, do nothing with it?
        if (msgControlIdOrder == 0)
        {
            return ;
        }
         
        //no messageControlId defined for this def of MSA, do nothing with it?
        //set msg.AckCode to value in position located in def of ackcode in seg
        msg.AckCode = seg.Fields[ackCodeOrder].ToString();
        msg.ControlId = seg.Fields[msgControlIdOrder].ToString();
    }

    public static void processMSH(HL7DefSegment segDef, SegmentHL7 seg, MessageHL7 msg) throws Exception {
        int msgControlIdOrder = 0;
        for (int i = 0;i < segDef.hl7DefFields.Count;i++)
        {
            //find position of messageControlId in segDef for MSH seg
            if (StringSupport.equals(segDef.hl7DefFields[i].FieldName, "messageControlId"))
            {
                msgControlIdOrder = segDef.hl7DefFields[i].OrdinalPos;
                break;
            }
             
        }
        if (msgControlIdOrder == 0)
        {
            return ;
        }
         
        msg.ControlId = seg.Fields[msgControlIdOrder].ToString();
    }

    //public static void ProcessPD1() {
    //	return;
    //}
    public static void processPID(Patient pat, HL7DefSegment segDef, SegmentHL7 seg) throws Exception {
        for (int i = 0;i < segDef.hl7DefFields.Count;i++)
        {
            int itemOrder = segDef.hl7DefFields[i].OrdinalPos;
            FieldName __dummyScrutVar2 = segDef.hl7DefFields[i].FieldName;
            if (__dummyScrutVar2.equals("pat.addressCityStateZip"))
            {
                pat.Address = seg.getFieldComponent(itemOrder,0);
                pat.Address2 = seg.getFieldComponent(itemOrder,1);
                pat.City = seg.getFieldComponent(itemOrder,2);
                pat.State = seg.getFieldComponent(itemOrder,3);
                pat.Zip = seg.getFieldComponent(itemOrder,4);
                continue;
            }
            else if (__dummyScrutVar2.equals("pat.birthdateTime"))
            {
                pat.Birthdate = FieldParser.dateTimeParse(seg.getFieldComponent(itemOrder));
                continue;
            }
            else if (__dummyScrutVar2.equals("pat.ChartNumber"))
            {
                pat.ChartNumber = seg.getFieldComponent(itemOrder);
                continue;
            }
            else if (__dummyScrutVar2.equals("pat.Gender"))
            {
                pat.Gender = FieldParser.genderParse(seg.getFieldComponent(itemOrder));
                continue;
            }
            else if (__dummyScrutVar2.equals("pat.HmPhone"))
            {
                pat.HmPhone = FieldParser.phoneParse(seg.getFieldComponent(itemOrder));
                continue;
            }
            else if (__dummyScrutVar2.equals("pat.nameLFM"))
            {
                pat.LName = seg.getFieldComponent(itemOrder,0);
                pat.FName = seg.getFieldComponent(itemOrder,1);
                pat.MiddleI = seg.getFieldComponent(itemOrder,2);
                continue;
            }
            else if (__dummyScrutVar2.equals("pat.PatNum"))
            {
                if (pat.PatNum != 0 && pat.PatNum != PIn.long(seg.getFieldComponent(itemOrder)))
                {
                    throw new Exception("Invalid PatNum");
                }
                 
                continue;
            }
            else if (__dummyScrutVar2.equals("pat.Position"))
            {
                pat.Position = FieldParser.maritalStatusParse(seg.getFieldComponent(itemOrder));
                continue;
            }
            else if (__dummyScrutVar2.equals("pat.Race"))
            {
                PatientRaceOld patientRaceOld = FieldParser.raceParse(seg.getFieldComponent(itemOrder));
                //Converts deprecated PatientRaceOld enum to list of PatRaces, and adds them to the PatientRaces table for the patient.
                PatientRaces.reconcile(pat.PatNum,PatientRaces.getPatRacesFromPatientRaceOld(patientRaceOld));
                continue;
            }
            else if (__dummyScrutVar2.equals("pat.SSN"))
            {
                pat.SSN = seg.getFieldComponent(itemOrder);
                continue;
            }
            else if (__dummyScrutVar2.equals("pat.WkPhone"))
            {
                pat.WkPhone = FieldParser.phoneParse(seg.getFieldComponent(itemOrder));
                continue;
            }
            else if (__dummyScrutVar2.equals("pat.FeeSched"))
            {
                if (Programs.isEnabled(ProgramName.eClinicalWorks) && StringSupport.equals(ProgramProperties.getPropVal(ProgramName.eClinicalWorks,"FeeSchedulesSetManually"), "1"))
                {
                    continue;
                }
                else
                {
                    //if using eCW and FeeSchedulesSetManually
                    //do not process fee sched field, manually set by user
                    pat.FeeSched = FieldParser.feeScheduleParse(seg.getFieldComponent(itemOrder));
                } 
                continue;
            }
            else
            {
                continue;
            }            
        }
        return ;
    }

    public static void processPV1(Patient pat, long aptNum, HL7DefSegment segDef, SegmentHL7 seg) throws Exception {
        long provNum = new long();
        int provNumOrder = 0;
        for (int i = 0;i < segDef.hl7DefFields.Count;i++)
        {
            if (StringSupport.equals(segDef.hl7DefFields[i].FieldName, "prov.provIdName") || StringSupport.equals(segDef.hl7DefFields[i].FieldName, "prov.provIdNameLFM"))
            {
                provNumOrder = segDef.hl7DefFields[i].OrdinalPos;
                break;
            }
             
        }
        if (provNumOrder == 0)
        {
            return ;
        }
         
        //No provIdName or provIdNameLFM field in this segment definition so do nothing with it
        provNum = FieldParser.ProvProcess(seg.Fields[provNumOrder]);
        if (provNum == 0)
        {
            return ;
        }
         
        //This segment didn't have a valid provider id in it to locate the provider (must have been blank) so do nothing
        Appointment apt = Appointments.getOneApt(aptNum);
        //SCH segment was found and aptNum was retrieved, if no SCH segment for this message then 0
        if (apt == null)
        {
            return ;
        }
         
        //Just in case we can't find an apt with the aptNum from SCH segment
        Appointment aptOld = apt.clone();
        Patient patOld = pat.copy();
        apt.ProvNum = provNum;
        pat.PriProv = provNum;
        Appointments.update(apt,aptOld);
        Patients.update(pat,patOld);
        return ;
    }

    /**
    * Returns AptNum of the incoming appointment.
    */
    public static long processSCH(Patient pat, HL7DefSegment segDef, SegmentHL7 seg) throws Exception {
        String aptNote = "";
        double aptLength = 0;
        long aptNum = 0;
        DateTime aptStart = DateTime.MinValue;
        DateTime aptStop = DateTime.MinValue;
        for (int i = 0;i < segDef.hl7DefFields.Count;i++)
        {
            int ordinalPos = segDef.hl7DefFields[i].OrdinalPos;
            FieldName __dummyScrutVar3 = segDef.hl7DefFields[i].FieldName;
            if (__dummyScrutVar3.equals("apt.AptNum"))
            {
                aptNum = PIn.long(seg.getFieldComponent(ordinalPos));
                continue;
            }
            else if (__dummyScrutVar3.equals("apt.lengthStartEnd"))
            {
                aptLength = FieldParser.secondsToMinutes(seg.getFieldComponent(ordinalPos,2));
                aptStart = FieldParser.dateTimeParse(seg.getFieldComponent(ordinalPos,3));
                aptStop = FieldParser.dateTimeParse(seg.getFieldComponent(ordinalPos,4));
                continue;
            }
            else if (__dummyScrutVar3.equals("apt.Note"))
            {
                aptNote = seg.getFieldComponent(ordinalPos);
                continue;
            }
            else
            {
                continue;
            }   
        }
        Appointment apt = Appointments.getOneApt(aptNum);
        Appointment aptOld = null;
        boolean isNewApt = apt == null;
        if (isNewApt)
        {
            apt = new Appointment();
            apt.AptNum = aptNum;
            apt.PatNum = pat.PatNum;
            apt.AptStatus = ApptStatus.Scheduled;
        }
        else
        {
            aptOld = apt.clone();
        } 
        if (apt.PatNum != pat.PatNum)
        {
            throw new Exception("Appointment does not match patient: " + pat.FName + " " + pat.LName + ", apt.PatNum: " + apt.PatNum.ToString() + ", pat.PatNum: " + pat.PatNum.ToString());
        }
         
        //we can't process this message because wrong patnum.
        apt.Note = aptNote;
        String pattern = new String();
        //If aptStop is MinValue we know that stop time was not sent or was not in the correct format so try to use the duration field.
        if (aptStop == DateTime.MinValue && aptLength != 0)
        {
            //Stop time is optional.  If not included we will use the duration field to calculate pattern.
            pattern = FieldParser.ProcessPattern(aptStart, aptStart.AddMinutes(aptLength));
        }
        else
        {
            //We received a good stop time or stop time is MinValue but we don't have a good aptLength so ProcessPattern will return the apt length or the default 5 minutes
            pattern = FieldParser.processPattern(aptStart,aptStop);
        } 
        apt.AptDateTime = aptStart;
        apt.Pattern = pattern;
        apt.ProvNum = pat.PriProv;
        //Set apt.ProvNum to the patient's primary provider.  This may change after processing the AIG or PV1 segments, but set here in case those segs are missing.
        if (StringSupport.equals(pat.FName, "") || StringSupport.equals(pat.LName, ""))
        {
            throw new Exception("Appointment not processed due to missing patient first or last name. PatNum:" + pat.PatNum.ToString());
        }
         
        if (isNewApt)
        {
            if (IsVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "Inserted appointment for: " + pat.FName + " " + pat.LName, EventLogEntryType.Information);
            }
             
            Appointments.insertIncludeAptNum(apt,true);
        }
        else
        {
            if (IsVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "Updated appointment for: " + pat.FName + " " + pat.LName, EventLogEntryType.Information);
            }
             
            Appointments.update(apt,aptOld);
        } 
        HL7MsgCur.AptNum = apt.AptNum;
        HL7Msgs.Update(HL7MsgCur);
        return aptNum;
    }

}


