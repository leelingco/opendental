//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.HL7.MessageHL7;
import OpenDentBusiness.HL7.SegmentHL7;
import OpenDentBusiness.MessageTypeHL7;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.PatientRaceOld;
import OpenDentBusiness.PatientRaces;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.SegmentNameHL7;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.TreatmentArea;

/**
* A DFT message is a Charge Specification.  There are different kinds.  The kind we have implemented passes information about completed procedures and their charges to external programs for billing purposes.
*/
public class EcwDFT   
{
    private MessageHL7 msg;
    private SegmentHL7 seg;
    /**
    * 
    */
    public EcwDFT() throws Exception {
    }

    /**
    * Creates the Message object and fills it with data.
    */
    public void initializeEcw(long aptNum, long provNum, Patient pat, String pdfDataAsBase64, String pdfDescription, boolean justPDF) throws Exception {
        msg = new MessageHL7(MessageTypeHL7.DFT);
        mSH();
        eVN();
        pID(pat);
        pV1(aptNum,provNum);
        fT1(aptNum,justPDF);
        dG1();
        zX1(pdfDataAsBase64,pdfDescription);
    }

    /**
    * Message Header Segment
    */
    private void mSH() throws Exception {
        seg = new SegmentHL7("MSH|^~\\&|OD||ECW||" + DateTime.Now.ToString("yyyyMMddHHmmss") + "||DFT^P03||P|2.3");
        msg.Segments.Add(seg);
    }

    /**
    * Event type segment.
    */
    private void eVN() throws Exception {
        seg = new SegmentHL7("EVN|P03|" + DateTime.Now.ToString("yyyyMMddHHmmss") + "|");
        msg.Segments.Add(seg);
    }

    /**
    * Patient identification.
    */
    private void pID(Patient pat) throws Exception {
        seg = new SegmentHL7(SegmentNameHL7.PID);
        seg.setField(0,"PID");
        seg.setField(1,"1");
        seg.setField(2,pat.ChartNumber);
        //Account number.  eCW requires this to be the same # as came in on PID.4.
        seg.SetField(3, pat.PatNum.ToString());
        //??what is this MRN?
        seg.setField(5,pat.LName,pat.FName,pat.MiddleI);
        //we assume that dob is always valid because eCW should always pass us a dob.
        seg.SetField(7, pat.Birthdate.ToString("yyyyMMdd"));
        seg.setField(8,convertGender(pat.Gender));
        seg.setField(10,convertRace(PatientRaces.getPatientRaceOldFromPatientRaces(pat.PatNum)));
        //Passes in the deprecated PatientRaceOld enum retrieved from the PatientRace table.
        seg.setField(11,pat.Address,pat.Address2,pat.City,pat.State,pat.Zip);
        seg.setField(13,convertPhone(pat.HmPhone));
        seg.setField(14,convertPhone(pat.WkPhone));
        seg.setField(16,convertMaritalStatus(pat.Position));
        seg.setField(19,pat.SSN);
        msg.Segments.Add(seg);
    }

    /**
    * Patient visit.
    */
    private void pV1(long aptNum, long provNum) throws Exception {
        seg = new SegmentHL7(SegmentNameHL7.PV1);
        seg.setField(0,"PV1");
        Provider prov = Providers.getProv(provNum);
        seg.setField(7,prov.EcwID,prov.LName,prov.FName,prov.MI);
        seg.SetField(19, aptNum.ToString());
        msg.Segments.Add(seg);
    }

    /**
    * Financial transaction segment.
    */
    private void fT1(long aptNum, boolean justPDF) throws Exception {
        if (justPDF)
        {
            return ;
        }
         
        //FT1 segment is not necessary when sending only a PDF.
        List<Procedure> procs = Procedures.getProcsForSingle(aptNum,false);
        ProcedureCode procCode;
        for (int i = 0;i < procs.Count;i++)
        {
            seg = new SegmentHL7(SegmentNameHL7.FT1);
            seg.setField(0,"FT1");
            seg.SetField(1, (i + 1).ToString());
            seg.SetField(4, procs[i].ProcDate.ToString("yyyyMMddHHmmss"));
            seg.SetField(5, procs[i].ProcDate.ToString("yyyyMMddHHmmss"));
            seg.setField(6,"CG");
            seg.setField(10,"1.0");
            seg.setField(16,"");
            //location code and description???
            seg.SetField(19, procs[i].DiagnosticCode);
            Provider prov = Providers.GetProv(procs[i].ProvNum);
            seg.setField(20,prov.EcwID,prov.LName,prov.FName,prov.MI);
            //performed by provider.
            seg.setField(21,prov.EcwID,prov.LName,prov.FName,prov.MI);
            //ordering provider.
            seg.SetField(22, procs[i].ProcFee.ToString("F2"));
            procCode = ProcedureCodes.GetProcCode(procs[i].CodeNum);
            if (procCode.ProcCode.Length > 5 && procCode.ProcCode.StartsWith("D"))
            {
                seg.SetField(25, procCode.ProcCode.Substring(0, 5));
            }
            else
            {
                //Remove suffix from all D codes.
                seg.setField(25,procCode.ProcCode);
            } 
            if (procCode.TreatArea == TreatmentArea.ToothRange)
            {
                seg.SetField(26, procs[i].ToothRange, "");
            }
            else if (procCode.TreatArea == TreatmentArea.Surf)
            {
                //probably not necessary
                seg.SetField(26, Tooth.ToInternat(procs[i].ToothNum), Tooth.SurfTidyForClaims(procs[i].Surf, procs[i].ToothNum));
            }
            else
            {
                seg.SetField(26, Tooth.ToInternat(procs[i].ToothNum), procs[i].Surf);
            }  
            msg.Segments.Add(seg);
        }
    }

    /**
    * Diagnosis segment. Optional.
    */
    private void dG1() throws Exception {
    }

    //DG1 optional, so we'll skip for now---------------------------------
    //seg=new SegmentHL7(SegmentName.DG1);
    //msg.Segments.Add(seg);
    /**
    * PDF data segment.
    */
    private void zX1(String pdfDataAsBase64, String pdfDescription) throws Exception {
        seg = new SegmentHL7(SegmentNameHL7.ZX1);
        seg.setField(0,"ZX1");
        seg.setField(1,"6");
        seg.setField(2,"PDF");
        seg.setField(3,"PATHOLOGY^Pathology Report^L");
        seg.setField(4,pdfDescription);
        seg.setField(5,pdfDataAsBase64);
        msg.Segments.Add(seg);
    }

    public String generateMessage() throws Exception {
        return msg.toString();
    }

    private String convertGender(PatientGender gender) throws Exception {
        if (gender == PatientGender.Female)
        {
            return "F";
        }
         
        if (gender == PatientGender.Male)
        {
            return "M";
        }
         
        return "U";
    }

    //=======================================================================================================================================
    //DO NOT ALTER any of these Convert... methods for use with any other HL7 bridge.
    //Each bridge tends to have slightly different implementation.
    //No bridge can share any of these.
    //Instead, copy them into other classes.
    //This set of methods is ONLY for ECW, and will have to be renamed and grouped if any other DFT bridge is built.
    //=======================================================================================================================================
    /**
    * Convert the patient race entries to the deprecated PatientRaceOld enum before calling this method.
    */
    private String convertRace(PatientRaceOld race) throws Exception {
        switch(race)
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

    private String convertPhone(String phone) throws Exception {
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
             
            if (retVal.Length == 10)
            {
                return retVal;
            }
             
        }
        return "";
    }

    //never made it to 10
    private String convertMaritalStatus(PatientPosition patpos) throws Exception {
        switch(patpos)
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

}


