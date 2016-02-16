//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.FormCommItem;
import OpenDental.FormSheetFillEdit;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.RefAttaches;
import OpenDental.SheetFiller;
import OpenDental.SheetUtil;
import OpenDentBusiness.Allergies;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.AutoCondComparison;
import OpenDentBusiness.AutoCondField;
import OpenDentBusiness.AutomationAction;
import OpenDentBusiness.AutomationCondition;
import OpenDentBusiness.AutomationConditions;
import OpenDentBusiness.Automations;
import OpenDentBusiness.AutomationTrigger;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.LabResult;
import OpenDentBusiness.LabResults;
import OpenDentBusiness.Medication;
import OpenDentBusiness.Medications;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Security;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.Sheets;

public class AutomationL   
{
    /**
    * ProcCodes will be null unless trigger is CompleteProcedure.  This routine will generally fail silently.  Will return true if a trigger happened.
    */
    public static boolean trigger(AutomationTrigger trigger, List<String> procCodes, long patNum) throws Exception {
        if (patNum == 0)
        {
            return false;
        }
         
        //Could happen for OpenPatient trigger
        boolean automationHappened = false;
        for (int i = 0;i < Automations.getListt().Count;i++)
        {
            if (Automations.getListt()[i].Autotrigger != trigger)
            {
                continue;
            }
             
            if (trigger == AutomationTrigger.CompleteProcedure)
            {
                if (procCodes == null)
                {
                    continue;
                }
                 
                //fail silently
                boolean codeFound = false;
                String[] arrayCodes = Automations.getListt()[i].ProcCodes.Split(',');
                for (int p = 0;p < procCodes.Count;p++)
                {
                    for (int a = 0;a < arrayCodes.Length;a++)
                    {
                        if (arrayCodes[a] == procCodes[p])
                        {
                            codeFound = true;
                            break;
                        }
                         
                    }
                }
                if (!codeFound)
                {
                    continue;
                }
                 
            }
             
            //matching automation item has been found
            //Get possible list of conditions that exist for this automation item
            List<AutomationCondition> autoConditionsList = AutomationConditions.GetListByAutomationNum(Automations.getListt()[i].AutomationNum);
            if (Automations.getListt()[i].AutoAction == AutomationAction.CreateCommlog)
            {
                if (autoConditionsList.Count > 0)
                {
                    if (!CheckAutomationConditions(autoConditionsList, patNum))
                    {
                        continue;
                    }
                     
                }
                 
                Commlog CommlogCur = new Commlog();
                CommlogCur.PatNum = patNum;
                CommlogCur.CommDateTime = DateTime.Now;
                CommlogCur.CommType = Automations.getListt()[i].CommType;
                CommlogCur.Note = Automations.getListt()[i].MessageContent;
                CommlogCur.Mode_ = CommItemMode.None;
                CommlogCur.UserNum = Security.getCurUser().UserNum;
                FormCommItem FormCI = new FormCommItem(CommlogCur);
                FormCI.IsNew = true;
                FormCI.ShowDialog();
                automationHappened = true;
            }
            else if (Automations.getListt()[i].AutoAction == AutomationAction.PopUp)
            {
                if (autoConditionsList.Count > 0)
                {
                    if (!CheckAutomationConditions(autoConditionsList, patNum))
                    {
                        continue;
                    }
                     
                }
                 
                MessageBox.Show(Automations.getListt()[i].MessageContent);
                automationHappened = true;
            }
            else if (Automations.getListt()[i].AutoAction == AutomationAction.PrintPatientLetter)
            {
                if (autoConditionsList.Count > 0)
                {
                    if (!CheckAutomationConditions(autoConditionsList, patNum))
                    {
                        continue;
                    }
                     
                }
                 
                SheetDef sheetDef = SheetDefs.GetSheetDef(Automations.getListt()[i].SheetDefNum);
                Sheet sheet = SheetUtil.createSheet(sheetDef,patNum);
                SheetParameter.setParameter(sheet,"PatNum",patNum);
                //SheetParameter.SetParameter(sheet,"ReferralNum",referral.ReferralNum);
                SheetFiller.fillFields(sheet);
                Bitmap bmp = new Bitmap(100, 100);
                try
                {
                    {
                        //a dummy bitmap for the graphics object
                        Graphics g = Graphics.FromImage(bmp);
                        try
                        {
                            {
                                SheetUtil.calculateHeights(sheet,g);
                            }
                        }
                        finally
                        {
                            if (g != null)
                                Disposable.mkDisposable(g).dispose();
                             
                        }
                    }
                }
                finally
                {
                    if (bmp != null)
                        Disposable.mkDisposable(bmp).dispose();
                     
                }
                FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet);
                FormSF.ShowDialog();
                automationHappened = true;
            }
            else if (Automations.getListt()[i].AutoAction == AutomationAction.PrintReferralLetter)
            {
                if (autoConditionsList.Count > 0)
                {
                    if (!CheckAutomationConditions(autoConditionsList, patNum))
                    {
                        continue;
                    }
                     
                }
                 
                long referralNum = RefAttaches.GetReferralNum(patNum);
                if (referralNum == 0)
                {
                    MsgBox.show("Automations","This patient has no referral source entered.");
                    automationHappened = true;
                    continue;
                }
                 
                SheetDef sheetDef = SheetDefs.GetSheetDef(Automations.getListt()[i].SheetDefNum);
                Sheet sheet = SheetUtil.createSheet(sheetDef,patNum);
                SheetParameter.setParameter(sheet,"PatNum",patNum);
                SheetParameter.setParameter(sheet,"ReferralNum",referralNum);
                SheetFiller.fillFields(sheet);
                Bitmap bmp = new Bitmap(100, 100);
                try
                {
                    {
                        //a dummy bitmap for the graphics object
                        Graphics g = Graphics.FromImage(bmp);
                        try
                        {
                            {
                                SheetUtil.calculateHeights(sheet,g);
                            }
                        }
                        finally
                        {
                            if (g != null)
                                Disposable.mkDisposable(g).dispose();
                             
                        }
                    }
                }
                finally
                {
                    if (bmp != null)
                        Disposable.mkDisposable(bmp).dispose();
                     
                }
                FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet);
                FormSF.ShowDialog();
                automationHappened = true;
            }
            else if (Automations.getListt()[i].AutoAction == AutomationAction.ShowExamSheet)
            {
                if (autoConditionsList.Count > 0)
                {
                    if (!CheckAutomationConditions(autoConditionsList, patNum))
                    {
                        continue;
                    }
                     
                }
                 
                SheetDef sheetDef = SheetDefs.GetSheetDef(Automations.getListt()[i].SheetDefNum);
                Sheet sheet = SheetUtil.createSheet(sheetDef,patNum);
                SheetParameter.setParameter(sheet,"PatNum",patNum);
                SheetFiller.fillFields(sheet);
                Bitmap bmp = new Bitmap(100, 100);
                try
                {
                    {
                        //a dummy bitmap for the graphics object
                        Graphics g = Graphics.FromImage(bmp);
                        try
                        {
                            {
                                SheetUtil.calculateHeights(sheet,g);
                            }
                        }
                        finally
                        {
                            if (g != null)
                                Disposable.mkDisposable(g).dispose();
                             
                        }
                    }
                }
                finally
                {
                    if (bmp != null)
                        Disposable.mkDisposable(bmp).dispose();
                     
                }
                FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet);
                FormSF.ShowDialog();
                automationHappened = true;
            }
                 
        }
        return automationHappened;
    }

    private static boolean checkAutomationConditions(List<AutomationCondition> autoConditionsList, long patNum) throws Exception {
        for (int i = 0;i < autoConditionsList.Count;i++)
        {
            //Make sure every condition returns true
            CompareField __dummyScrutVar0 = autoConditionsList[i].CompareField;
            if (__dummyScrutVar0.equals(AutoCondField.SheetNotCompletedTodayWithName))
            {
                if (SheetNotCompletedTodayWithName(autoConditionsList[i], patNum))
                {
                    return false;
                }
                 
            }
            else if (__dummyScrutVar0.equals(AutoCondField.Problem))
            {
                if (!ProblemComparison(autoConditionsList[i], patNum))
                {
                    return false;
                }
                 
            }
            else if (__dummyScrutVar0.equals(AutoCondField.Medication))
            {
                if (!MedicationComparison(autoConditionsList[i], patNum))
                {
                    return false;
                }
                 
            }
            else if (__dummyScrutVar0.equals(AutoCondField.Allergy))
            {
                if (!AllergyComparison(autoConditionsList[i], patNum))
                {
                    return false;
                }
                 
            }
            else if (__dummyScrutVar0.equals(AutoCondField.Age))
            {
                if (!AgeComparison(autoConditionsList[i], patNum))
                {
                    return false;
                }
                 
            }
            else if (__dummyScrutVar0.equals(AutoCondField.Gender))
            {
                if (!GenderComparison(autoConditionsList[i], patNum))
                {
                    return false;
                }
                 
            }
            else if (__dummyScrutVar0.equals(AutoCondField.Labresult))
            {
                if (!LabresultComparison(autoConditionsList[i], patNum))
                {
                    return false;
                }
                 
            }
                   
        }
        return true;
    }

    private static boolean sheetNotCompletedTodayWithName(AutomationCondition autoCond, long patNum) throws Exception {
        List<Sheet> sheetList = Sheets.getForPatientForToday(patNum);
        switch(autoCond.Comparison)
        {
            case Equals: 
                for (int i = 0;i < sheetList.Count;i++)
                {
                    //Find out what operand to use.
                    //Loop through every sheet to find one that matches the condition.
                    if (StringSupport.equals(sheetList[i].Description, autoCond.CompareString))
                    {
                        return true;
                    }
                     
                }
                break;
            case Contains: 
                for (int i = 0;i < sheetList.Count;i++)
                {
                    //Operand based on AutoCondComparison.
                    if (sheetList[i].Description.ToLower().Contains(autoCond.CompareString.ToLower()))
                    {
                        return true;
                    }
                     
                }
                break;
        
        }
        return false;
    }

    private static boolean problemComparison(AutomationCondition autoCond, long patNum) throws Exception {
        List<Disease> problemList = Diseases.refresh(patNum);
        switch(autoCond.Comparison)
        {
            case Equals: 
                for (int i = 0;i < problemList.Count;i++)
                {
                    //Find out what operand to use.
                    //Includes hidden
                    if (StringSupport.equals(DiseaseDefs.GetName(problemList[i].DiseaseDefNum), autoCond.CompareString))
                    {
                        return true;
                    }
                     
                }
                break;
            case Contains: 
                for (int i = 0;i < problemList.Count;i++)
                {
                    if (DiseaseDefs.GetName(problemList[i].DiseaseDefNum).ToLower().Contains(autoCond.CompareString.ToLower()))
                    {
                        return true;
                    }
                     
                }
                break;
        
        }
        return false;
    }

    private static boolean medicationComparison(AutomationCondition autoCond, long patNum) throws Exception {
        List<Medication> medList = Medications.getMedicationsByPat(patNum);
        switch(autoCond.Comparison)
        {
            case Equals: 
                for (int i = 0;i < medList.Count;i++)
                {
                    if (StringSupport.equals(medList[i].MedName, autoCond.CompareString))
                    {
                        return true;
                    }
                     
                }
                break;
            case Contains: 
                for (int i = 0;i < medList.Count;i++)
                {
                    if (medList[i].MedName.ToLower().Contains(autoCond.CompareString.ToLower()))
                    {
                        return true;
                    }
                     
                }
                break;
        
        }
        return false;
    }

    private static boolean allergyComparison(AutomationCondition autoCond, long patNum) throws Exception {
        List<Allergy> allergyList = Allergies.getAll(patNum,false);
        switch(autoCond.Comparison)
        {
            case Equals: 
                for (int i = 0;i < allergyList.Count;i++)
                {
                    if (StringSupport.equals(AllergyDefs.GetOne(allergyList[i].AllergyDefNum).Description, autoCond.CompareString))
                    {
                        return true;
                    }
                     
                }
                break;
            case Contains: 
                for (int i = 0;i < allergyList.Count;i++)
                {
                    if (AllergyDefs.GetOne(allergyList[i].AllergyDefNum).Description.ToLower().Contains(autoCond.CompareString.ToLower()))
                    {
                        return true;
                    }
                     
                }
                break;
        
        }
        return false;
    }

    private static boolean ageComparison(AutomationCondition autoCond, long patNum) throws Exception {
        Patient pat = Patients.getPat(patNum);
        int age = pat.getAge();
        switch(autoCond.Comparison)
        {
            case Equals: 
                return (age == PIn.Int(autoCond.CompareString));
            case Contains: 
                return (age.ToString().Contains(autoCond.CompareString));
            case GreaterThan: 
                return (age > PIn.Int(autoCond.CompareString));
            case LessThan: 
                return (age < PIn.Int(autoCond.CompareString));
            default: 
                return false;
        
        }
    }

    private static boolean genderComparison(AutomationCondition autoCond, long patNum) throws Exception {
        Patient pat = Patients.getPat(patNum);
        switch(autoCond.Comparison)
        {
            case Equals: 
                return (pat.Gender.ToString().Substring(0, 1).ToLower() == autoCond.CompareString.ToLower());
            case Contains: 
                return (pat.Gender.ToString().Substring(0, 1).ToLower().Contains(autoCond.CompareString.ToLower()));
            default: 
                return false;
        
        }
    }

    private static boolean labresultComparison(AutomationCondition autoCond, long patNum) throws Exception {
        List<LabResult> listResults = LabResults.getAllForPatient(patNum);
        switch(autoCond.Comparison)
        {
            case Equals: 
                for (int i = 0;i < listResults.Count;i++)
                {
                    if (StringSupport.equals(listResults[i].TestName, autoCond.CompareString))
                    {
                        return true;
                    }
                     
                }
                break;
            case Contains: 
                for (int i = 0;i < listResults.Count;i++)
                {
                    if (listResults[i].TestName.ToLower().Contains(autoCond.CompareString.ToLower()))
                    {
                        return true;
                    }
                     
                }
                break;
        
        }
        return false;
    }

}


