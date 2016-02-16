//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.FeeSched;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.FeeScheduleType;
import OpenDentBusiness.HL7.EcwSegmentPID;
import OpenDentBusiness.HL7.FieldHL7;
import OpenDentBusiness.HL7.SegmentHL7;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.PatientRaceOld;
import OpenDentBusiness.PatientRaces;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PIn;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;

/**
* (and GT1 and PV1)
*/
public class EcwSegmentPID   
{
    /**
    * PatNum will not be altered here.  The pat passed in must either have PatNum=0, or must have a PatNum matching the segment.  The reason that isStandalone is passed in is because if using tight integration mode (isStandalone=false), then we need to store the "alternate patient id" aka Account No. that comes in on PID.4 in the ChartNumber field so we can pass it back in PID.2 of the DFT charge message.  However, if not using tight integration (isStandalone=true), the ChartNumber field is already occupied by the eCW patient ID, and we do not want to overwrite it.
    */
    public static void processPID(Patient pat, SegmentHL7 seg, boolean isStandalone) throws Exception {
        long patNum = PIn.long(seg.getFieldFullText(2));
        //if(pat.PatNum==0) {
        //	pat.PatNum=patNum;
        //}
        //else
        //in standalone, the patnums won't match, so don't check
        if (!isStandalone && pat.PatNum != 0 && pat.PatNum != patNum)
        {
            throw new ApplicationException("Invalid patNum");
        }
         
        if (!isStandalone)
        {
            //when in tight integration mode
            pat.ChartNumber = seg.getFieldFullText(4);
        }
         
        pat.LName = seg.getFieldComponent(5,0);
        pat.FName = seg.getFieldComponent(5,1);
        pat.MiddleI = seg.getFieldComponent(5,2);
        pat.Birthdate = dateParse(seg.getFieldFullText(7));
        pat.Gender = genderParse(seg.getFieldFullText(8));
        PatientRaceOld patientRaceOld = raceParse(seg.getFieldFullText(10));
        //Converts PatientRaceOld to new Patient Races, and adds them o the PatientRace table.
        PatientRaces.reconcile(pat.PatNum,PatientRaces.getPatRacesFromPatientRaceOld(patientRaceOld));
        pat.Address = seg.getFieldComponent(11,0);
        pat.Address2 = seg.getFieldComponent(11,1);
        pat.City = seg.getFieldComponent(11,2);
        pat.State = seg.getFieldComponent(11,3);
        pat.Zip = seg.getFieldComponent(11,4);
        pat.HmPhone = phoneParse(seg.getFieldFullText(13));
        pat.WkPhone = phoneParse(seg.getFieldFullText(14));
        pat.Position = maritalStatusParse(seg.getFieldFullText(16));
        //pat.ChartNumber=seg.GetFieldFullText(18);//this is wrong.  Would also break standalone mode
        pat.SSN = seg.getFieldFullText(19);
        if (StringSupport.equals(ProgramProperties.getPropVal(ProgramName.eClinicalWorks,"FeeSchedulesSetManually"), "0"))
        {
            //if !FeeSchedulesSetManually
            pat.FeeSched = feeScheduleParse(seg.getFieldFullText(22));
        }
         
    }

    /**
    * If relationship is self, this loop does nothing.  A new pat will later change guarantor to be same as patnum.
    */
    public static void processGT1(Patient pat, SegmentHL7 seg, boolean useChartNumber) throws Exception {
        long guarNum = PIn.long(seg.getFieldFullText(2));
        if (guarNum == 0)
        {
            return ;
        }
         
        //because we have an example where they sent us this (position 2 is empty): GT1|1||^^||^^^^||||||||
        if (StringSupport.equals(seg.getFieldFullText(11), "1"))
        {
            return ;
        }
         
        //if relationship is self (according to some of their documentation)
        //lname
        if (StringSupport.equals(seg.getFieldComponent(3,0), "") || StringSupport.equals(seg.getFieldComponent(3,1), ""))
        {
            //fname
            EventLog.WriteEntry("OpenDentHL7", "Guarantor not processed due to missing first or last name. PatNum of patient:" + pat.PatNum.ToString(), EventLogEntryType.Information);
            return ;
        }
         
        Patient guar = null;
        Patient guarOld = null;
        //So guarantor is someone else
        if (useChartNumber)
        {
            //try to find guarantor by using chartNumber
            guar = Patients.GetPatByChartNumber(guarNum.ToString());
            if (guar == null)
            {
                //try to find the guarantor by using name and birthdate
                String lName = seg.getFieldComponent(3,0);
                String fName = seg.getFieldComponent(3,1);
                DateTime birthdate = EcwSegmentPID.dateParse(seg.getFieldFullText(8));
                long guarNumByName = Patients.getPatNumByNameAndBirthday(lName,fName,birthdate);
                if (guarNumByName == 0)
                {
                }
                else
                {
                    //guarantor does not exist in OD
                    //so guar will still be null, triggering creation of new guarantor further down.
                    guar = Patients.getPat(guarNumByName);
                    guar.ChartNumber = guarNum.ToString();
                } 
            }
             
        }
        else
        {
            //from now on, we will be able to find guar by chartNumber
            guar = Patients.getPat(guarNum);
        } 
        //we can't necessarily set pat.Guarantor yet, because in Standalone mode, we might not know it yet.
        boolean isNewGuar = guar == null;
        if (isNewGuar)
        {
            //then we need to add guarantor to db
            guar = new Patient();
            if (useChartNumber)
            {
                guar.ChartNumber = guarNum.ToString();
            }
            else
            {
                guar.PatNum = guarNum;
            } 
            guar.PriProv = PrefC.getLong(PrefName.PracticeDefaultProv);
            guar.BillingType = PrefC.getLong(PrefName.PracticeDefaultBillType);
        }
        else
        {
            guarOld = guar.copy();
        } 
        //guar.Guarantor=guarNum;
        guar.LName = seg.getFieldComponent(3,0);
        guar.FName = seg.getFieldComponent(3,1);
        guar.MiddleI = seg.getFieldComponent(3,2);
        guar.Address = seg.getFieldComponent(5,0);
        guar.Address2 = seg.getFieldComponent(5,1);
        guar.City = seg.getFieldComponent(5,2);
        guar.State = seg.getFieldComponent(5,3);
        guar.Zip = seg.getFieldComponent(5,4);
        guar.HmPhone = phoneParse(seg.getFieldFullText(6));
        guar.WkPhone = phoneParse(seg.getFieldFullText(7));
        guar.Birthdate = dateParse(seg.getFieldFullText(8));
        guar.Gender = genderParse(seg.getFieldFullText(9));
        //11. Guarantor relationship to patient.  We can't really do anything with this value
        guar.SSN = seg.getFieldFullText(12);
        if (isNewGuar)
        {
            Patients.insert(guar,!useChartNumber);
            //if using chartnumber (standalone mode), then can't insert using existing PK
            guarOld = guar.copy();
            guar.Guarantor = guar.PatNum;
            Patients.update(guar,guarOld);
        }
        else
        {
            Patients.update(guar,guarOld);
        } 
        pat.Guarantor = guar.PatNum;
    }

    //public static void ProcessPV1(Patient pat,SegmentHL7 seg) {
    //	long provNum=ProvProcess(seg.GetField(7));
    //	if(provNum!=0) {
    //		pat.PriProv=provNum;
    //	}
    //}
    /**
    * yyyyMMdd.  If not in that format, it returns minVal.
    */
    public static DateTime dateParse(String str) throws Exception {
        if (str.Length != 8)
        {
            return DateTime.MinValue;
        }
         
        int year = PIn.Int(str.Substring(0, 4));
        int month = PIn.Int(str.Substring(4, 2));
        int day = PIn.Int(str.Substring(6));
        DateTime retVal = new DateTime(year, month, day);
        return retVal;
    }

    /**
    * If it's exactly 10 digits, it will be formatted like this: (###)###-####.  Otherwise, no change.
    */
    public static String phoneParse(String str) throws Exception {
        if (str.Length != 10)
        {
            return str;
        }
         
        return "(" + str.Substring(0, 3) + ")" + str.Substring(3, 3) + "-" + str.Substring(6);
    }

    //no change
    /**
    * M,F,U
    */
    public static PatientGender genderParse(String str) throws Exception {
        if (StringSupport.equals(str, "M") || StringSupport.equals(str.ToLower(), "male"))
        {
            return PatientGender.Male;
        }
        else if (StringSupport.equals(str, "F") || StringSupport.equals(str.ToLower(), "female"))
        {
            return PatientGender.Female;
        }
        else
        {
            return PatientGender.Unknown;
        }  
    }

    /**
    * Returns the depricated PatientRaceOld enum.  Converting the old enum to patientrace entries needs to happen after this function is called.
    */
    public static PatientRaceOld raceParse(String str) throws Exception {
        System.String __dummyScrutVar0 = str;
        if (__dummyScrutVar0.equals("American Indian Or Alaska Native"))
        {
            return PatientRaceOld.AmericanIndian;
        }
        else if (__dummyScrutVar0.equals("Asian"))
        {
            return PatientRaceOld.Asian;
        }
        else if (__dummyScrutVar0.equals("Native Hawaiian or Other Pacific"))
        {
            return PatientRaceOld.HawaiiOrPacIsland;
        }
        else if (__dummyScrutVar0.equals("Black or African American"))
        {
            return PatientRaceOld.AfricanAmerican;
        }
        else if (__dummyScrutVar0.equals("White"))
        {
            return PatientRaceOld.White;
        }
        else if (__dummyScrutVar0.equals("Hispanic"))
        {
            return PatientRaceOld.HispanicLatino;
        }
        else if (__dummyScrutVar0.equals("Other Race"))
        {
            return PatientRaceOld.Other;
        }
        else
        {
            return PatientRaceOld.Other;
        }       
    }

    public static PatientPosition maritalStatusParse(String str) throws Exception {
        System.String __dummyScrutVar1 = str;
        if (__dummyScrutVar1.equals("Single"))
        {
            return PatientPosition.Single;
        }
        else if (__dummyScrutVar1.equals("Married"))
        {
            return PatientPosition.Married;
        }
        else if (__dummyScrutVar1.equals("Divorced"))
        {
            return PatientPosition.Divorced;
        }
        else if (__dummyScrutVar1.equals("Widowed"))
        {
            return PatientPosition.Widowed;
        }
        else if (__dummyScrutVar1.equals("Legally Separated"))
        {
            return PatientPosition.Married;
        }
        else if (__dummyScrutVar1.equals("Unknown"))
        {
            return PatientPosition.Single;
        }
        else if (__dummyScrutVar1.equals("Partner"))
        {
            return PatientPosition.Single;
        }
        else
        {
            return PatientPosition.Single;
        }       
    }

    /**
    * Supply in format UPIN^LastName^FirstName^MI.  If UPIN(abbr) does not exist, provider gets created.  If name has changed, provider gets updated.  ProvNum is returned.  If blank, then returns 0.  If field is NULL, returns 0.
    */
    public static long provProcess(FieldHL7 field) throws Exception {
        if (field == null)
        {
            return 0;
        }
         
        String eID = field.getComponentVal(0);
        eID = eID.Trim();
        if (StringSupport.equals(eID, ""))
        {
            return 0;
        }
         
        Provider prov = Providers.getProvByEcwID(eID);
        boolean isNewProv = false;
        boolean provChanged = false;
        if (prov == null)
        {
            isNewProv = true;
            prov = new Provider();
            prov.Abbr = eID;
            //They can manually change this later.
            prov.EcwID = eID;
            prov.FeeSched = FeeSchedC.getListShort()[0].FeeSchedNum;
        }
         
        if (!StringSupport.equals(prov.LName, field.getComponentVal(1)))
        {
            provChanged = true;
            prov.LName = field.getComponentVal(1);
        }
         
        if (!StringSupport.equals(prov.FName, field.getComponentVal(2)))
        {
            provChanged = true;
            prov.FName = field.getComponentVal(2);
        }
         
        if (!StringSupport.equals(prov.MI, field.getComponentVal(3)))
        {
            provChanged = true;
            prov.MI = field.getComponentVal(3);
        }
         
        if (isNewProv)
        {
            Providers.insert(prov);
            Providers.refreshCache();
        }
        else if (provChanged)
        {
            Providers.update(prov);
            Providers.refreshCache();
        }
          
        return prov.ProvNum;
    }

    /**
    * Will return 0 if string cannot be parsed to a number.  Will return 0 if the fee schedule passed in does not exactly match the description of a regular fee schedule.
    */
    public static long feeScheduleParse(String str) throws Exception {
        if (StringSupport.equals(str, ""))
        {
            return 0;
        }
         
        FeeSched feeSched = FeeScheds.getByExactName(str,FeeScheduleType.Normal);
        if (feeSched == null)
        {
            return 0;
        }
         
        return feeSched.FeeSchedNum;
    }

}


