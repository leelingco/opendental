//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.FeeSched;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.FeeScheduleType;
import OpenDentBusiness.HL7.FieldHL7;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.PatientRaceOld;
import OpenDentBusiness.PIn;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;

/**
* Parses a single incoming HL7 field.
*/
public class FieldParser   
{
    //HL7 has very specific data types.  Each data type that we use will have a corresponding parser method here.
    //Data types are listed in 2.15.
    /**
    * yyyyMMddHHmmss.  Can have more precision than seconds and won't break.  If less than 8 digits, returns MinVal.
    */
    public static DateTime dateTimeParse(String str) throws Exception {
        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;
        if (str.Length < 8)
        {
            return DateTime.MinValue;
        }
         
        year = PIn.Int(str.Substring(0, 4));
        month = PIn.Int(str.Substring(4, 2));
        day = PIn.Int(str.Substring(6, 2));
        if (str.Length >= 10)
        {
            hour = PIn.Int(str.Substring(8, 2));
        }
         
        if (str.Length >= 12)
        {
            minute = PIn.Int(str.Substring(10, 2));
        }
         
        //skip seconds and any trailing numbers
        DateTime retVal = new DateTime(year, month, day, hour, minute, 0);
        return retVal;
    }

    /**
    * M,F,U
    */
    public static PatientGender genderParse(String str) throws Exception {
        if (StringSupport.equals(str.ToLower(), "m") || StringSupport.equals(str.ToLower(), "male"))
        {
            return PatientGender.Male;
        }
        else if (StringSupport.equals(str.ToLower(), "f") || StringSupport.equals(str.ToLower(), "female"))
        {
            return PatientGender.Female;
        }
        else
        {
            return PatientGender.Unknown;
        }  
    }

    public static PatientPosition maritalStatusParse(String str) throws Exception {
        System.String __dummyScrutVar0 = str;
        if (__dummyScrutVar0.equals("Single"))
        {
            return PatientPosition.Single;
        }
        else if (__dummyScrutVar0.equals("Married"))
        {
            return PatientPosition.Married;
        }
        else if (__dummyScrutVar0.equals("Divorced"))
        {
            return PatientPosition.Divorced;
        }
        else if (__dummyScrutVar0.equals("Widowed"))
        {
            return PatientPosition.Widowed;
        }
        else if (__dummyScrutVar0.equals("Legally Separated"))
        {
            return PatientPosition.Married;
        }
        else if (__dummyScrutVar0.equals("Unknown"))
        {
            return PatientPosition.Single;
        }
        else if (__dummyScrutVar0.equals("Partner"))
        {
            return PatientPosition.Single;
        }
        else
        {
            return PatientPosition.Single;
        }       
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
    public static String processPattern(DateTime startTime, DateTime stopTime) throws Exception {
        int minutes = (int)((stopTime - startTime).TotalMinutes);
        if (minutes <= 0)
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
    * Supply in format UPIN^LastName^FirstName^MI (PV1) or UPIN^LastName, FirstName MI (AIG).  If UPIN(abbr) does not exist, provider gets created.  If name has changed, provider gets updated.  ProvNum is returned.  If blank, then returns 0.  If field is NULL, returns 0. For PV1, the provider.LName field will hold "LastName, FirstName MI". They can manually change later.
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
         
        if (field.Components.Count == 4)
        {
            //PV1 segment in format UPIN^LastName^FirstName^MI
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
             
        }
        else if (field.Components.Count == 2)
        {
            //AIG segment in format UPIN^LastName, FirstName MI
            String[] components = field.getComponentVal(1).Split(' ');
            if (components.Length > 0)
            {
                components[0] = components[0].TrimEnd(',');
                if (!StringSupport.equals(prov.LName, components[0]))
                {
                    provChanged = true;
                    prov.LName = components[0];
                }
                 
            }
             
            if (components.Length > 1 && !StringSupport.equals(prov.FName, components[1]))
            {
                provChanged = true;
                prov.FName = components[1];
            }
             
            if (components.Length > 2 && !StringSupport.equals(prov.MI, components[2]))
            {
                provChanged = true;
                prov.MI = components[2];
            }
             
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
    * Returns the depricated PatientRaceOld enum.  It gets converted to new patient race entries where it's called.
    */
    public static PatientRaceOld raceParse(String str) throws Exception {
        System.String __dummyScrutVar1 = str;
        if (__dummyScrutVar1.equals("American Indian Or Alaska Native"))
        {
            return PatientRaceOld.AmericanIndian;
        }
        else if (__dummyScrutVar1.equals("Asian"))
        {
            return PatientRaceOld.Asian;
        }
        else if (__dummyScrutVar1.equals("Native Hawaiian or Other Pacific"))
        {
            return PatientRaceOld.HawaiiOrPacIsland;
        }
        else if (__dummyScrutVar1.equals("Black or African American"))
        {
            return PatientRaceOld.AfricanAmerican;
        }
        else if (__dummyScrutVar1.equals("White"))
        {
            return PatientRaceOld.White;
        }
        else if (__dummyScrutVar1.equals("Hispanic"))
        {
            return PatientRaceOld.HispanicLatino;
        }
        else if (__dummyScrutVar1.equals("Other Race"))
        {
            return PatientRaceOld.Other;
        }
        else
        {
            return PatientRaceOld.Other;
        }       
    }

    public static double secondsToMinutes(String secs) throws Exception {
        double retVal = new double();
        try
        {
            retVal = double.Parse(secs);
        }
        catch (Exception __dummyCatchVar0)
        {
            return 0;
        }

        return retVal / 60;
    }

    //couldn't parse the value to a double so just return 0
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


