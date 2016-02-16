//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Allergies;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.Db;
import OpenDentBusiness.Disease;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.EhrCriterion;
import OpenDentBusiness.LabResult;
import OpenDentBusiness.LabResults;
import OpenDentBusiness.Medication;
import OpenDentBusiness.Medications;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.POut;
import OpenDentBusiness.ReminderRule;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ReminderRules   
{
    /**
    * 
    */
    public static long insert(ReminderRule reminderRule) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            reminderRule.ReminderRuleNum = Meth.GetLong(MethodBase.GetCurrentMethod(), reminderRule);
            return reminderRule.ReminderRuleNum;
        }
         
        return Crud.ReminderRuleCrud.Insert(reminderRule);
    }

    /**
    * 
    */
    public static void update(ReminderRule reminderRule) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), reminderRule);
            return ;
        }
         
        Crud.ReminderRuleCrud.Update(reminderRule);
    }

    /**
    * 
    */
    public static void delete(long reminderRuleNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), reminderRuleNum);
            return ;
        }
         
        String command = "DELETE FROM reminderrule WHERE ReminderRuleNum = " + POut.long(reminderRuleNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static List<ReminderRule> selectAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ReminderRule>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM reminderrule";
        return Crud.ReminderRuleCrud.SelectMany(command);
    }

    public static List<ReminderRule> getRemindersForPatient(Patient PatCur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ReminderRule>>GetObject(MethodBase.GetCurrentMethod(), PatCur);
        }
         
        //Problem,Medication,Allergy,Age,Gender,LabResult
        List<ReminderRule> fullListReminders = Crud.ReminderRuleCrud.SelectMany("SELECT * FROM reminderrule");
        List<ReminderRule> retVal = new List<ReminderRule>();
        List<Disease> listProblems = Diseases.refresh(PatCur.PatNum);
        List<Medication> listMedications = Medications.getMedicationsByPat(PatCur.PatNum);
        List<Allergy> listAllergies = Allergies.refresh(PatCur.PatNum);
        List<LabResult> listLabResults = LabResults.getAllForPatient(PatCur.PatNum);
        for (int i = 0;i < fullListReminders.Count;i++)
        {
            ReminderCriterion __dummyScrutVar0 = fullListReminders[i].ReminderCriterion;
            if (__dummyScrutVar0.equals(EhrCriterion.Problem))
            {
                for (int j = 0;j < listProblems.Count;j++)
                {
                    if (fullListReminders[i].CriterionFK == listProblems[j].DiseaseDefNum)
                    {
                        retVal.Add(fullListReminders[i]);
                        break;
                    }
                     
                }
            }
            else if (__dummyScrutVar0.equals(EhrCriterion.Medication))
            {
                for (int j = 0;j < listMedications.Count;j++)
                {
                    if (fullListReminders[i].CriterionFK == listMedications[j].MedicationNum)
                    {
                        retVal.Add(fullListReminders[i]);
                        break;
                    }
                     
                }
            }
            else if (__dummyScrutVar0.equals(EhrCriterion.Allergy))
            {
                for (int j = 0;j < listAllergies.Count;j++)
                {
                    if (fullListReminders[i].CriterionFK == listAllergies[j].AllergyDefNum)
                    {
                        retVal.Add(fullListReminders[i]);
                        break;
                    }
                     
                }
            }
            else if (__dummyScrutVar0.equals(EhrCriterion.Age))
            {
                if (fullListReminders[i].CriterionValue[0] == '<')
                {
                    if (PatCur.getAge() < int.Parse(fullListReminders[i].CriterionValue.Substring(1, fullListReminders[i].CriterionValue.Length - 1)))
                    {
                        retVal.Add(fullListReminders[i]);
                    }
                     
                }
                else if (fullListReminders[i].CriterionValue[0] == '>')
                {
                    if (PatCur.getAge() > int.Parse(fullListReminders[i].CriterionValue.Substring(1, fullListReminders[i].CriterionValue.Length - 1)))
                    {
                        retVal.Add(fullListReminders[i]);
                    }
                     
                }
                else
                {
                }  
            }
            else //This section should never be reached
            if (__dummyScrutVar0.equals(EhrCriterion.Gender))
            {
                if (PatCur.Gender.ToString().ToLower() == fullListReminders[i].CriterionValue.ToLower())
                {
                    retVal.Add(fullListReminders[i]);
                }
                 
            }
            else if (__dummyScrutVar0.equals(EhrCriterion.LabResult))
            {
                for (int j = 0;j < listLabResults.Count;j++)
                {
                    if (listLabResults[j].TestName.ToLower().Contains(fullListReminders[i].CriterionValue.ToLower()))
                    {
                        retVal.Add(fullListReminders[i]);
                        break;
                    }
                     
                }
            }
                  
        }
        return retVal;
    }

}


//case EhrCriterion.ICD9:
//  for(int j=0;j<listProblems.Count;j++) {
//    if(fullListReminders[i].CriterionFK==listProblems[j].DiseaseDefNum) {
//      retVal.Add(fullListReminders[i]);
//      break;
//    }
//  }
//  break;
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<ReminderRule> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<ReminderRule>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM reminderrule WHERE PatNum = "+POut.Long(patNum);
			return Crud.ReminderRuleCrud.SelectMany(command);
		}
		///<summary>Gets one ReminderRule from the db.</summary>
		public static ReminderRule GetOne(long reminderRuleNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<ReminderRule>(MethodBase.GetCurrentMethod(),reminderRuleNum);
			}
			return Crud.ReminderRuleCrud.SelectOne(reminderRuleNum);
		}
		*/