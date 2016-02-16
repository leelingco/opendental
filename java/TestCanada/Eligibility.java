//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:36 PM
//

package TestCanada;

import CS2JNet.System.StringSupport;
import OpenDental.Eclaims.CanadianOutput;
import OpenDental.Eclaims.CCDFieldInputter;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransMessageTexts;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.ProviderC;
import TestCanada.PatientTC;

public class Eligibility   
{
    public static String runOne(boolean showForms) throws Exception {
        String retVal = "";
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist #1
        Patient pat = Patients.getPat(PatientTC.PatNum1);
        //patient#1
        if (pat.PriProv != provNum)
        {
            Patient oldPat = pat.copy();
            pat.PriProv = provNum;
            //this script uses the primary provider for the patient
            Patients.update(pat,oldPat);
        }
         
        PatPlan patplan = PatPlans.getPatPlan(pat.PatNum,1);
        InsSub sub = InsSubs.getOne(patplan.InsSubNum);
        InsPlan plan = InsPlans.GetPlan(sub.PlanNum, new List<InsPlan>());
        //the UI would block this due to carrier not supporting this transaction type.
        long etransNum = CanadianOutput.sendElegibility(pat.PatNum,plan,new DateTime(1999, 1, 1),patplan.Relationship,patplan.PatID,showForms,sub);
        Etrans etrans = Etranss.getEtrans(etransNum);
        String message = EtransMessageTexts.getMessageText(etrans.EtransMessageTextNum);
        CCDFieldInputter formData = new CCDFieldInputter(message);
        String responseStatus = formData.getValue("G05");
        if (!StringSupport.equals(responseStatus, "R"))
        {
            throw new Exception("Should be R");
        }
         
        retVal += "Eligibility #1 successful.\r\n";
        return retVal;
    }

    public static String runTwo(boolean showForms) throws Exception {
        String retVal = "";
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist #1
        Patient pat = Patients.getPat(PatientTC.PatNum7);
        //patient#7
        if (pat.PriProv != provNum)
        {
            Patient oldPat = pat.copy();
            pat.PriProv = provNum;
            //this script uses the primary provider for the patient
            Patients.update(pat,oldPat);
        }
         
        PatPlan patplan = PatPlans.getPatPlan(pat.PatNum,1);
        InsSub sub = InsSubs.getOne(patplan.InsSubNum);
        InsPlan plan = InsPlans.GetPlan(sub.PlanNum, new List<InsPlan>());
        long etransNum = CanadianOutput.sendElegibility(pat.PatNum,plan,new DateTime(1999, 1, 1),patplan.Relationship,patplan.PatID,showForms,sub);
        //should print Eligibility response on Dentaide Form
        Etrans etrans = Etranss.getEtrans(etransNum);
        String message = EtransMessageTexts.getMessageText(etrans.EtransMessageTextNum);
        CCDFieldInputter formData = new CCDFieldInputter(message);
        String responseStatus = formData.getValue("G05");
        if (!StringSupport.equals(responseStatus, "E"))
        {
            throw new Exception("Should be E");
        }
         
        //no errors
        retVal += "Eligibility #2 successful.\r\n";
        return retVal;
    }

    public static String runThree(boolean showForms) throws Exception {
        String retVal = "";
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist #1
        Patient pat = Patients.getPat(PatientTC.PatNum6);
        //patient#6
        if (pat.PriProv != provNum)
        {
            Patient oldPat = pat.copy();
            pat.PriProv = provNum;
            //this script uses the primary provider for the patient
            Patients.update(pat,oldPat);
        }
         
        PatPlan patplan = PatPlans.getPatPlan(pat.PatNum,2);
        InsSub sub = InsSubs.getOne(patplan.InsSubNum);
        InsPlan plan = InsPlans.GetPlan(sub.PlanNum, new List<InsPlan>());
        long etransNum = CanadianOutput.sendElegibility(pat.PatNum,plan,new DateTime(1999, 1, 1),patplan.Relationship,patplan.PatID,showForms,sub);
        Etrans etrans = Etranss.getEtrans(etransNum);
        String message = EtransMessageTexts.getMessageText(etrans.EtransMessageTextNum);
        CCDFieldInputter formData = new CCDFieldInputter(message);
        String responseStatus = formData.getValue("G05");
        if (!StringSupport.equals(responseStatus, "R"))
        {
            throw new Exception("Should be R");
        }
         
        retVal += "Eligibility #3 successful.\r\n";
        return retVal;
    }

    public static String runFour(boolean showForms) throws Exception {
        String retVal = "";
        long provNum = ProviderC.getListShort()[1].ProvNum;
        //dentist #2
        Patient pat = Patients.getPat(PatientTC.PatNum6);
        //patient#6
        if (pat.PriProv != provNum)
        {
            Patient oldPat = pat.copy();
            pat.PriProv = provNum;
            //this script uses the primary provider for the patient
            Patients.update(pat,oldPat);
        }
         
        PatPlan patplan = PatPlans.getPatPlan(pat.PatNum,1);
        InsSub sub = InsSubs.getOne(patplan.InsSubNum);
        InsPlan plan = InsPlans.GetPlan(sub.PlanNum, new List<InsPlan>());
        long etransNum = CanadianOutput.sendElegibility(pat.PatNum,plan,new DateTime(1999, 1, 1),patplan.Relationship,patplan.PatID,showForms,sub);
        Etrans etrans = Etranss.getEtrans(etransNum);
        String message = EtransMessageTexts.getMessageText(etrans.EtransMessageTextNum);
        CCDFieldInputter formData = new CCDFieldInputter(message);
        String responseStatus = formData.getValue("G05");
        if (!StringSupport.equals(responseStatus, "M"))
        {
            throw new Exception("Should be M");
        }
         
        retVal += "Eligibility #4 successful.\r\n";
        return retVal;
    }

    public static String runFive(boolean showForms) throws Exception {
        String retVal = "";
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist #1
        Patient pat = Patients.getPat(PatientTC.PatNum5);
        //patient#5
        if (pat.PriProv != provNum)
        {
            Patient oldPat = pat.copy();
            pat.PriProv = provNum;
            //this script uses the primary provider for the patient
            Patients.update(pat,oldPat);
        }
         
        PatPlan patplan = PatPlans.getPatPlan(pat.PatNum,1);
        InsSub sub = InsSubs.getOne(patplan.InsSubNum);
        InsPlan plan = InsPlans.GetPlan(sub.PlanNum, new List<InsPlan>());
        long etransNum = CanadianOutput.sendElegibility(pat.PatNum,plan,new DateTime(1999, 1, 1),patplan.Relationship,patplan.PatID,showForms,sub);
        Etrans etrans = Etranss.getEtrans(etransNum);
        String message = EtransMessageTexts.getMessageText(etrans.EtransMessageTextNum);
        CCDFieldInputter formData = new CCDFieldInputter(message);
        String responseStatus = formData.getValue("G05");
        if (!StringSupport.equals(responseStatus, "E"))
        {
            throw new Exception("Should be E");
        }
         
        retVal += "Eligibility #5 successful.\r\n";
        return retVal;
    }

    public static String runSix(boolean showForms) throws Exception {
        String retVal = "";
        long provNum = ProviderC.getListShort()[0].ProvNum;
        //dentist #1
        Patient pat = Patients.getPat(PatientTC.PatNum9);
        //patient#9
        if (pat.PriProv != provNum)
        {
            Patient oldPat = pat.copy();
            pat.PriProv = provNum;
            //this script uses the primary provider for the patient
            Patients.update(pat,oldPat);
        }
         
        PatPlan patplan = PatPlans.getPatPlan(pat.PatNum,1);
        InsSub sub = InsSubs.getOne(patplan.InsSubNum);
        InsPlan plan = InsPlans.GetPlan(sub.PlanNum, new List<InsPlan>());
        long etransNum = CanadianOutput.sendElegibility(pat.PatNum,plan,new DateTime(1999, 1, 1),patplan.Relationship,patplan.PatID,showForms,sub);
        Etrans etrans = Etranss.getEtrans(etransNum);
        String message = EtransMessageTexts.getMessageText(etrans.EtransMessageTextNum);
        CCDFieldInputter formData = new CCDFieldInputter(message);
        String responseStatus = formData.getValue("G05");
        if (!StringSupport.equals(responseStatus, "E"))
        {
            throw new Exception("Should be E");
        }
         
        retVal += "Eligibility #6 successful.\r\n";
        return retVal;
    }

}


//public static string RunEligibility(long provNum,Patient pat,string expectedStatus,int scriptNum,bool showForms) {
//  string retVal="";
//  if(pat.PriProv!=provNum) {
//    Patient oldPat=pat.Copy();
//    pat.PriProv=provNum;//this script uses the primary provider for the patient
//    Patients.Update(pat,oldPat);
//  }
//  PatPlan patplan=PatPlans.GetPatPlan(pat.PatNum,1);
//  InsSub sub=InsSubs.GetOne(patplan.InsSubNum);
//  InsPlan plan=InsPlans.GetPlan(sub.PlanNum,new List<InsPlan>());
//  long etransNum=CanadianOutput.SendElegibility(pat.PatNum,plan,new DateTime(1999,1,1),patplan.Relationship,patplan.PatID,showForms,sub);
//  Etrans etrans=Etranss.GetEtrans(etransNum);
//  string message=EtransMessageTexts.GetMessageText(etrans.EtransMessageTextNum);
//  CCDFieldInputter formData=new CCDFieldInputter(message);
//  string responseStatus=formData.GetValue("G05");
//  if(responseStatus!=expectedStatus) {
//    throw new Exception("Should be "+expectedStatus);
//  }
//  retVal+="Eligibility #"+scriptNum+" successful.\r\n";
//  return retVal;
//}