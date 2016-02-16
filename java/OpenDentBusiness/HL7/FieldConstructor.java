//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.EventTypeHL7;
import OpenDentBusiness.HL7Def;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.PatientRaceOld;
import OpenDentBusiness.PatientRaces;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.TreatmentArea;

/**
* This is the engine that will construct our outgoing HL7 message fields.
*/
public class FieldConstructor   
{
    /**
    * apt, guar, proc, prov and pdfDataString can be null and will return an empty string if a field requires that object
    */
    public static String generateDFT(HL7Def def, String fieldName, Patient pat, Provider prov, Procedure proc, Patient guar, Appointment apt, int sequenceNum, EventTypeHL7 eventType, String pdfDescription, String pdfDataString) throws Exception {
        //big long list of fieldnames that we support
        System.String __dummyScrutVar0 = fieldName;
        if (__dummyScrutVar0.equals("apt.AptNum"))
        {
            if (apt == null)
            {
                return "";
            }
            else
            {
                return apt.AptNum.ToString();
            } 
        }
        else if (__dummyScrutVar0.equals("dateTime.Now"))
        {
            return gDTM(DateTime.Now, 14);
        }
        else if (__dummyScrutVar0.equals("eventType"))
        {
            return eventType.ToString();
        }
        else if (__dummyScrutVar0.equals("guar.addressCityStateZip"))
        {
            if (guar == null)
            {
                return "";
            }
            else
            {
                return gConcat(def.ComponentSeparator,guar.Address,guar.Address2,guar.City,guar.State,guar.Zip);
            } 
        }
        else if (__dummyScrutVar0.equals("guar.birthdateTime"))
        {
            if (guar == null)
            {
                return "";
            }
            else
            {
                return gDTM(guar.Birthdate,8);
            } 
        }
        else if (__dummyScrutVar0.equals("guar.Gender"))
        {
            if (guar == null)
            {
                return "";
            }
            else
            {
                return gIS(guar);
            } 
        }
        else if (__dummyScrutVar0.equals("guar.HmPhone"))
        {
            if (guar == null)
            {
                return "";
            }
            else
            {
                return gXTN(guar.HmPhone,10);
            } 
        }
        else if (__dummyScrutVar0.equals("guar.nameLFM"))
        {
            if (guar == null)
            {
                return "";
            }
            else
            {
                return gConcat(def.ComponentSeparator,guar.LName,guar.FName,guar.MiddleI);
            } 
        }
        else if (__dummyScrutVar0.equals("guar.PatNum"))
        {
            if (guar == null)
            {
                return "";
            }
            else
            {
                return guar.PatNum.ToString();
            } 
        }
        else if (__dummyScrutVar0.equals("guar.SSN"))
        {
            if (guar == null)
            {
                return "";
            }
            else
            {
                return guar.SSN;
            } 
        }
        else if (__dummyScrutVar0.equals("guar.WkPhone"))
        {
            if (guar == null)
            {
                return "";
            }
            else
            {
                return gXTN(guar.WkPhone,10);
            } 
        }
        else if (__dummyScrutVar0.equals("messageControlId"))
        {
            return Guid.NewGuid().ToString("N");
        }
        else if (__dummyScrutVar0.equals("messageType"))
        {
            return gConcat(def.ComponentSeparator, "DFT", eventType.ToString());
        }
        else if (__dummyScrutVar0.equals("pat.addressCityStateZip"))
        {
            return gConcat(def.ComponentSeparator,pat.Address,pat.Address2,pat.City,pat.State,pat.Zip);
        }
        else if (__dummyScrutVar0.equals("pat.birthdateTime"))
        {
            return gDTM(pat.Birthdate,8);
        }
        else if (__dummyScrutVar0.equals("pat.ChartNumber"))
        {
            return pat.ChartNumber;
        }
        else if (__dummyScrutVar0.equals("pat.Gender"))
        {
            return gIS(pat);
        }
        else if (__dummyScrutVar0.equals("pat.HmPhone"))
        {
            return gXTN(pat.HmPhone,10);
        }
        else if (__dummyScrutVar0.equals("pat.nameLFM"))
        {
            return gConcat(def.ComponentSeparator,pat.LName,pat.FName,pat.MiddleI);
        }
        else if (__dummyScrutVar0.equals("pat.PatNum"))
        {
            return pat.PatNum.ToString();
        }
        else if (__dummyScrutVar0.equals("pat.Position"))
        {
            return gPos(pat);
        }
        else if (__dummyScrutVar0.equals("pat.Race"))
        {
            return gRace(pat);
        }
        else if (__dummyScrutVar0.equals("pat.SSN"))
        {
            return pat.SSN;
        }
        else if (__dummyScrutVar0.equals("pat.WkPhone"))
        {
            return gXTN(pat.WkPhone,10);
        }
        else if (__dummyScrutVar0.equals("pdfDescription"))
        {
            return pdfDescription;
        }
        else if (__dummyScrutVar0.equals("pdfDataAsBase64"))
        {
            if (pdfDataString == null)
            {
                return "";
            }
            else
            {
                return pdfDataString;
            } 
        }
        else if (__dummyScrutVar0.equals("proc.DiagnosticCode"))
        {
            if (proc == null)
            {
                return "";
            }
             
            if (proc.DiagnosticCode == null)
            {
                return "";
            }
            else
            {
                return proc.DiagnosticCode;
            } 
        }
        else if (__dummyScrutVar0.equals("proc.procDateTime"))
        {
            if (proc == null)
            {
                return "";
            }
            else
            {
                return gDTM(proc.ProcDate,14);
            } 
        }
        else if (__dummyScrutVar0.equals("proc.ProcFee"))
        {
            if (proc == null)
            {
                return "";
            }
            else
            {
                return proc.ProcFee.ToString("F2");
            } 
        }
        else if (__dummyScrutVar0.equals("proc.ProcNum"))
        {
            if (proc == null)
            {
                return "";
            }
            else
            {
                return proc.ProcNum.ToString();
            } 
        }
        else if (__dummyScrutVar0.equals("proc.toothSurfRange"))
        {
            if (proc == null)
            {
                return "";
            }
            else
            {
                return gTreatArea(def.ComponentSeparator, proc);
            } 
        }
        else if (__dummyScrutVar0.equals("proccode.ProcCode"))
        {
            if (proc == null)
            {
                return "";
            }
            else
            {
                return gProcCode(proc);
            } 
        }
        else if (__dummyScrutVar0.equals("prov.provIdNameLFM"))
        {
            if (prov == null)
            {
                return "";
            }
            else
            {
                return gConcat(def.ComponentSeparator,prov.EcwID,prov.LName,prov.FName,prov.MI);
            } 
        }
        else if (__dummyScrutVar0.equals("separators^~\\&"))
        {
            return gSep(def);
        }
        else if (__dummyScrutVar0.equals("sequenceNum"))
        {
            return sequenceNum.ToString();
        }
        else
        {
            return "";
        }                                   
    }

    public static String generateACK(HL7Def def, String fieldName, String controlId, boolean isAck, String ackEvent) throws Exception {
        //big long list of fieldnames that we support
        System.String __dummyScrutVar1 = fieldName;
        if (__dummyScrutVar1.equals("ackCode"))
        {
            return gAck(isAck);
        }
        else if (__dummyScrutVar1.equals("dateTime.Now"))
        {
            return gDTM(DateTime.Now, 14);
        }
        else if (__dummyScrutVar1.equals("messageControlId"))
        {
            return controlId;
        }
        else if (__dummyScrutVar1.equals("messageType"))
        {
            return gConcat(def.ComponentSeparator,"ACK",ackEvent);
        }
        else if (__dummyScrutVar1.equals("separators^~\\&"))
        {
            return gSep(def);
        }
        else
        {
            return "";
        }     
    }

    private static String gAck(boolean isAck) throws Exception {
        if (isAck)
        {
            return "AA";
        }
        else
        {
            return "AE";
        } 
    }

    //Acknowledgment accept
    //Acknowledgment error
    //Ack reject is a third possible response that we don't currently support
    //Send in component separator for this def and the values in the order they should be in the message.
    private static String gConcat(String componentSep, String... vals) throws Exception {
        String retVal = "";
        if (vals.Length == 1)
        {
            return retVal = vals[0];
        }
         
        for (int i = 0;i < vals.Length;i++)
        {
            //this allows us to pass in all components for the field as one long string: comp1^comp2^comp3
            if (i > 0)
            {
                retVal += componentSep;
            }
             
            retVal += vals[i];
        }
        return retVal;
    }

    private static String gSep(HL7Def def) throws Exception {
        return def.ComponentSeparator + def.RepetitionSeparator + def.EscapeCharacter + def.SubcomponentSeparator;
    }

    private static String gDTM(DateTime dt, int precisionDigits) throws Exception {
        switch(precisionDigits)
        {
            case 8: 
                return dt.ToString("yyyyMMdd");
            case 14: 
                return dt.ToString("yyyyMMddHHmmss");
            default: 
                return "";
        
        }
    }

    private static String gIS(Patient pat) throws Exception {
        if (pat.Gender == PatientGender.Female)
        {
            return "F";
        }
         
        if (pat.Gender == PatientGender.Male)
        {
            return "M";
        }
         
        return "U";
    }

    private static String gPos(Patient pat) throws Exception {
        switch(pat.Position)
        {
            case Single: 
                return "Single";
            case Married: 
                return "Married";
            case Divorced: 
                return "Divorced";
            case Widowed: 
                return "Widowed";
            case Child: 
                return "Single";
            default: 
                return "Single";
        
        }
    }

    private static String gProcCode(Procedure proc) throws Exception {
        String retVal = "";
        ProcedureCode procCode = ProcedureCodes.getProcCode(proc.CodeNum);
        if (procCode.ProcCode.Length > 5 && procCode.ProcCode.StartsWith("D"))
        {
            retVal = procCode.ProcCode.Substring(0, 5);
        }
        else
        {
            //Remove suffix from all D codes.
            retVal = procCode.ProcCode;
        } 
        return retVal;
    }

    private static String gRace(Patient pat) throws Exception {
        switch(PatientRaces.getPatientRaceOldFromPatientRaces(pat.PatNum))
        {
            case AmericanIndian: 
                return "American Indian Or Alaska Native";
            case Asian: 
                return "Asian";
            case HawaiiOrPacIsland: 
                return "Native Hawaiian or Other Pacific";
            case AfricanAmerican: 
                return "Black or African American";
            case White: 
                return "White";
            case HispanicLatino: 
                return "Hispanic";
            case Other: 
                return "Other Race";
            default: 
                return "Other Race";
        
        }
    }

    //Uses the deprecated PatientRaceOld enum converted from PatientRaces.GetPatientRaceOldFromPatientRaces()
    private static String gTreatArea(String componentSep, Procedure proc) throws Exception {
        String retVal = "";
        ProcedureCode procCode = ProcedureCodes.getProcCode(proc.CodeNum);
        if (procCode.TreatArea == TreatmentArea.ToothRange)
        {
            retVal = proc.ToothRange;
        }
        else if (procCode.TreatArea == TreatmentArea.Surf)
        {
            //probably not necessary
            retVal = gConcat(componentSep,Tooth.toInternat(proc.ToothNum),Tooth.surfTidyForClaims(proc.Surf,proc.ToothNum));
        }
        else
        {
            retVal = gConcat(componentSep,Tooth.toInternat(proc.ToothNum),proc.Surf);
        }  
        return retVal;
    }

    /**
    * XTN is a phone number.
    */
    private static String gXTN(String phone, int numDigits) throws Exception {
        String retVal = "";
        for (int i = 0;i < phone.Length;i++)
        {
            if (Char.IsNumber(phone, i))
            {
                if (StringSupport.equals(retVal, "") && StringSupport.equals(phone.Substring(i, 1), "1"))
                {
                    continue;
                }
                 
                //skip leading 1.
                retVal += phone.Substring(i, 1);
            }
             
            if (retVal.Length == numDigits)
            {
                return retVal;
            }
             
        }
        return "";
    }

}


//never made it to 10