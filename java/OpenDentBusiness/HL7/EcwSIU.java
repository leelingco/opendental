//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.HL7.EcwSegmentPID;
import OpenDentBusiness.HL7.MessageHL7;
import OpenDentBusiness.HL7.SegmentHL7;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PIn;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.SegmentNameHL7;

public class EcwSIU   
{
    public static void processMessage(MessageHL7 message, boolean isVerboseLogging) throws Exception {
        SegmentHL7 seg = message.GetSegment(SegmentNameHL7.PID, true);
        long patNum = PIn.long(seg.getFieldFullText(2));
        Patient pat = Patients.getPat(patNum);
        Patient patOld = null;
        boolean isNewPat = pat == null;
        if (isNewPat)
        {
            pat = new Patient();
            pat.PatNum = patNum;
            pat.Guarantor = patNum;
            pat.PriProv = PrefC.getLong(PrefName.PracticeDefaultProv);
            pat.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
        }
        else
        {
            patOld = pat.copy();
        } 
        EcwSegmentPID.ProcessPID(pat, seg, false);
        //IsStandalone=false because should never make it this far.
        //PV1-patient visit---------------------------
        //seg=message.GetSegment(SegmentName.PV1,false);
        //if(seg!=null) {
        //	SegmentPID.ProcessPV1(pat,seg);
        //}
        //SCH- Schedule Activity Information
        seg = message.GetSegment(SegmentNameHL7.SCH, true);
        //The documentation is wrong.  SCH.01 is not the appointment ID, but is instead a sequence# (always 1)
        long aptNum = PIn.long(seg.getFieldFullText(2));
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
            EventLog.WriteEntry("OpenDentHL7", "Appointment does not match patient: " + pat.FName + " " + pat.LName + ", apt.PatNum:" + apt.PatNum.ToString() + ", pat.PatNum:" + pat.PatNum.ToString(), EventLogEntryType.Error);
            return ;
        }
         
        //we can't process this message because wrong patnum.
        apt.Note = seg.getFieldFullText(7);
        //apt.Pattern=ProcessDuration(seg.GetFieldFullText(9));
        //9 and 10 are not actually available, in spite of the documentation.
        //11-We need start time and stop time
        apt.AptDateTime = dateTimeParse(seg.getFieldComponent(11,3));
        DateTime stopTime = dateTimeParse(seg.getFieldComponent(11,4));
        apt.Pattern = processPattern(apt.AptDateTime,stopTime);
        apt.ProvNum = pat.PriProv;
        //just in case there's not AIG segment.
        //AIG is optional, but looks like the only way to get provider for the appt-----------
        //PV1 seems to frequently be sent instead of AIG.
        SegmentHL7 segAIG = message.GetSegment(SegmentNameHL7.AIG, false);
        SegmentHL7 segPV = message.GetSegment(SegmentNameHL7.PV1, false);
        if (segAIG != null)
        {
            long provNum = EcwSegmentPID.provProcess(segAIG.getField(3));
            if (provNum != 0)
            {
                apt.ProvNum = provNum;
                pat.PriProv = provNum;
            }
             
        }
        else if (segPV != null)
        {
            long provNum = EcwSegmentPID.provProcess(segPV.getField(7));
            if (provNum != 0)
            {
                apt.ProvNum = provNum;
                pat.PriProv = provNum;
            }
             
        }
          
        //AIL,AIP seem to be optional, and I'm going to ignore them for now.
        if (StringSupport.equals(pat.FName, "") || StringSupport.equals(pat.LName, ""))
        {
            EventLog.WriteEntry("OpenDentHL7", "Appointment not processed due to missing patient first or last name. PatNum:" + pat.PatNum.ToString(), EventLogEntryType.Information);
            return ;
        }
         
        //this will also skip the appt insert.
        if (isNewPat)
        {
            if (isVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "Inserted patient: " + pat.FName + " " + pat.LName + ", PatNum:" + pat.PatNum.ToString(), EventLogEntryType.Information);
            }
             
            Patients.insert(pat,true);
        }
        else
        {
            if (isVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "Updated patient: " + pat.FName + " " + pat.LName, EventLogEntryType.Information);
            }
             
            Patients.update(pat,patOld);
        } 
        if (isNewApt)
        {
            if (isVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "Inserted appointment for: " + pat.FName + " " + pat.LName, EventLogEntryType.Information);
            }
             
            Appointments.insertIncludeAptNum(apt,true);
        }
        else
        {
            if (isVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "Updated appointment for: " + pat.FName + " " + pat.LName, EventLogEntryType.Information);
            }
             
            Appointments.update(apt,aptOld);
        } 
    }

    private static String processPattern(DateTime startTime, DateTime stopTime) throws Exception {
        int minutes = (int)((stopTime - startTime).TotalMinutes);
        if (minutes == 0)
        {
            return "//";
        }
         
        //we don't want it to be zero minutes
        int increments5 = minutes / 5;
        StringBuilder pattern = new StringBuilder();
        for (int i = 0;i < increments5;i++)
        {
            pattern.Append("X");
        }
        return pattern.ToString();
    }

    //make it all provider time, I guess.
    /**
    * yyyyMMddHHmmss.  If not in that format, it returns minVal.
    */
    public static DateTime dateTimeParse(String str) throws Exception {
        if (str.Length != 14)
        {
            return DateTime.MinValue;
        }
         
        int year = PIn.Int(str.Substring(0, 4));
        int month = PIn.Int(str.Substring(4, 2));
        int day = PIn.Int(str.Substring(6, 2));
        int hour = PIn.Int(str.Substring(8, 2));
        int minute = PIn.Int(str.Substring(10, 2));
        //skip seconds
        DateTime retVal = new DateTime(year, month, day, hour, minute, 0);
        return retVal;
    }

}


