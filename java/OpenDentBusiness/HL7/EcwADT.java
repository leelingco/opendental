//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.HL7.EcwSegmentPID;
import OpenDentBusiness.HL7.MessageHL7;
import OpenDentBusiness.HL7.SegmentHL7;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PIn;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.SegmentNameHL7;

/**
* ADT messages are known as Patient Administration messages.  There are around 60 different kinds of ADT messages.  ADT messages are the most common message type, and I always think of them as "demographics" messages.  Not sure what ADT stands for; probably "admit/discharge/transfer" since many of the kinds of ADTs have to do with handling incoming and outgoing patients.
*/
public class EcwADT   
{
    public static void processMessage(MessageHL7 message, boolean isStandalone, boolean isVerboseLogging) throws Exception {
        /*string triggerevent=message.Segments[0].GetFieldComponent(8,1);
        			switch(triggerevent) {
        				case "A01"://Admit/Visit Information
        					break;
        				case "A04"://New Patient Information
        					ProcessNewPatient(message);
        					break;
        				case "A08"://Update Patient Information
        					break;
        				case "A28"://Add Patient Information
        					break;
        				case "A31"://Update Patient Information
        					break;
        			}*/
        //MSH-Ignore
        //EVN-Ignore
        //PID-------------------------------------
        SegmentHL7 seg = message.GetSegment(SegmentNameHL7.PID, true);
        long patNum = PIn.long(seg.getFieldFullText(2));
        Patient pat = null;
        if (isStandalone)
        {
            pat = Patients.GetPatByChartNumber(patNum.ToString());
            if (pat == null)
            {
                //try to find the patient in question by using name and birthdate
                String lName = seg.getFieldComponent(5,0);
                String fName = seg.getFieldComponent(5,1);
                DateTime birthdate = EcwSegmentPID.dateParse(seg.getFieldFullText(7));
                long patNumByName = Patients.getPatNumByNameAndBirthday(lName,fName,birthdate);
                if (patNumByName == 0)
                {
                }
                else
                {
                    //patient does not exist in OD
                    //so pat will still be null, triggering creation of new patient further down.
                    pat = Patients.getPat(patNumByName);
                    pat.ChartNumber = patNum.ToString();
                } 
            }
             
        }
        else
        {
            //from now on, we will be able to find pat by chartNumber
            pat = Patients.getPat(patNum);
        } 
        Patient patOld = null;
        boolean isNewPat = pat == null;
        if (isNewPat)
        {
            pat = new Patient();
            if (isStandalone)
            {
                pat.ChartNumber = patNum.ToString();
            }
            else
            {
                //this line does not work if isStandalone, so moved to end
                //pat.Guarantor=patNum;
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
        EcwSegmentPID.ProcessPID(pat, seg, isStandalone);
        //PV1-patient visit---------------------------
        //seg=message.GetSegment(SegmentName.PV1,false);
        //if(seg!=null) {//this seg is optional
        //	SegmentPID.ProcessPV1(pat,seg);
        //}
        //PD1-additional patient demographics------------
        //seg=message.GetSegment(SegmentName.PD1,false);
        //if(seg!=null) {//this seg is optional
        //	ProcessPD1(pat,seg);
        //}
        //GT1-Guarantor-------------------------------------
        seg = message.GetSegment(SegmentNameHL7.GT1, true);
        EcwSegmentPID.ProcessGT1(pat, seg, isStandalone);
        //IN1-Insurance-------------------------------------
        //List<SegmentHL7> segments=message.GetSegments(SegmentName.IN1);
        //for(int i=0;i<segments.Count;i++) {
        //	ProcessIN1(pat,seg);
        //}
        if (StringSupport.equals(pat.FName, "") || StringSupport.equals(pat.LName, ""))
        {
            EventLog.WriteEntry("OpenDentHL7", "Patient demographics not processed due to missing first or last name. PatNum:" + pat.PatNum.ToString(), EventLogEntryType.Information);
            return ;
        }
         
        if (isNewPat)
        {
            if (isVerboseLogging)
            {
                EventLog.WriteEntry("OpenDentHL7", "Inserted patient: " + pat.FName + " " + pat.LName, EventLogEntryType.Information);
            }
             
            Patients.insert(pat,true);
            if (pat.Guarantor == 0)
            {
                patOld = pat.copy();
                pat.Guarantor = pat.PatNum;
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
    }

    public static void processPD1(Patient pat, SegmentHL7 seg) throws Exception {
        long provNum = EcwSegmentPID.provProcess(seg.getField(4));
        //don't know why both
        if (provNum != 0)
        {
            pat.PriProv = provNum;
        }
         
    }

    public static void processIN1(Patient pat, SegmentHL7 seg) throws Exception {
        //as a general strategy, if certain things are the same, like subscriber and carrier,
        //then we change the existing plan.
        //However, if basics change at all, then we drop the old plan and create a new one
        int ordinal = PIn.int(seg.getFieldFullText(1));
        PatPlan oldPatPlan = PatPlans.getPatPlan(pat.PatNum,ordinal);
        if (oldPatPlan == null)
        {
        }
         
    }

}


//create a new plan and a new patplan
//InsPlan oldPlan=InsPlans.g
//we'll have to get back to this.  This is lower priority than appointments.