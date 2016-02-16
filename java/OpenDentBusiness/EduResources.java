//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Disease;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.EduResource;
import OpenDentBusiness.EhrLabResult;
import OpenDentBusiness.EhrLabResults;
import OpenDentBusiness.LabResult;
import OpenDentBusiness.LabResults;
import OpenDentBusiness.Medication;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Medications;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EduResources   
{
    /**
    * 
    */
    public static List<EduResource> generateForPatient(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EduResource>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        List<Disease> diseaseList = Diseases.refresh(patNum);
        List<MedicationPat> medicationPatList = MedicationPats.refresh(patNum,false);
        List<LabResult> labResultList = LabResults.getAllForPatient(patNum);
        List<EhrLabResult> listEhrLabResults = EhrLabResults.getAllForPatient(patNum);
        List<EduResource> eduResourceListAll = Crud.EduResourceCrud.SelectMany("SELECT * FROM eduresource");
        List<EduResource> retVal = new List<EduResource>();
        for (int i = 0;i < eduResourceListAll.Count;i++)
        {
            if (eduResourceListAll[i].DiseaseDefNum != 0)
            {
                for (int j = 0;j < diseaseList.Count;j++)
                {
                    if (eduResourceListAll[i].DiseaseDefNum == diseaseList[j].DiseaseDefNum)
                    {
                        retVal.Add(eduResourceListAll[i]);
                    }
                     
                }
            }
            else if (eduResourceListAll[i].DiseaseDefNum != 0)
            {
                for (int j = 0;j < diseaseList.Count;j++)
                {
                    //checks against same list as Diseases/Problems
                    if (eduResourceListAll[i].DiseaseDefNum == diseaseList[j].DiseaseDefNum)
                    {
                        retVal.Add(eduResourceListAll[i]);
                    }
                     
                }
            }
            else if (eduResourceListAll[i].MedicationNum != 0)
            {
                Medication med = Medications.GetMedication(eduResourceListAll[i].MedicationNum);
                for (int j = 0;j < medicationPatList.Count;j++)
                {
                    if (eduResourceListAll[i].MedicationNum == medicationPatList[j].MedicationNum || (medicationPatList[j].MedicationNum == 0 && medicationPatList[j].RxCui == med.RxCui))
                    {
                        retVal.Add(eduResourceListAll[i]);
                    }
                     
                }
            }
            else if (!StringSupport.equals(eduResourceListAll[i].LabResultID, ""))
            {
                for (int j = 0;j < labResultList.Count;j++)
                {
                    if (eduResourceListAll[i].LabResultID != labResultList[j].TestID)
                    {
                        continue;
                    }
                     
                    if (eduResourceListAll[i].LabResultCompare.StartsWith("<"))
                    {
                        try
                        {
                            //PIn.Int not used because blank not allowed.
                            if (int.Parse(labResultList[j].ObsValue) < int.Parse(eduResourceListAll[i].LabResultCompare.Substring(1)))
                            {
                                retVal.Add(eduResourceListAll[i]);
                            }
                             
                        }
                        catch (Exception __dummyCatchVar0)
                        {
                        }
                    
                    }
                    else //This could only happen if the validation in either input didn't work.
                    if (eduResourceListAll[i].LabResultCompare.StartsWith(">"))
                    {
                        try
                        {
                            if (int.Parse(labResultList[j].ObsValue) > int.Parse(eduResourceListAll[i].LabResultCompare.Substring(1)))
                            {
                                retVal.Add(eduResourceListAll[i]);
                            }
                             
                        }
                        catch (Exception __dummyCatchVar1)
                        {
                        }
                    
                    }
                      
                }
                for (int j = 0;j < listEhrLabResults.Count;j++)
                {
                    //This could only happen if the validation in either input didn't work.
                    //end LabResultList
                    //matches loinc only.
                    if (listEhrLabResults[j].ObservationIdentifierID != eduResourceListAll[i].LabResultID)
                    {
                        continue;
                    }
                     
                    if (retVal.Contains(eduResourceListAll[i]))
                    {
                        continue;
                    }
                     
                    //already added from loop above.
                    retVal.Add(eduResourceListAll[i]);
                }
            }
                
        }
        return retVal;
    }

    //end EhrLabResults
    /**
    * 
    */
    public static List<EduResource> selectAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EduResource>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM eduresource";
        return Crud.EduResourceCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static void delete(long eduResourceNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), eduResourceNum);
            return ;
        }
         
        String command = "DELETE FROM eduresource WHERE EduResourceNum = " + POut.long(eduResourceNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long insert(EduResource eduResource) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            eduResource.EduResourceNum = Meth.GetLong(MethodBase.GetCurrentMethod(), eduResource);
            return eduResource.EduResourceNum;
        }
         
        return Crud.EduResourceCrud.Insert(eduResource);
    }

    /**
    * 
    */
    public static void update(EduResource eduResource) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), eduResource);
            return ;
        }
         
        Crud.EduResourceCrud.Update(eduResource);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<EduResource> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<EduResource>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM eduresource WHERE PatNum = "+POut.Long(patNum);
			return Crud.EduResourceCrud.SelectMany(command);
		}
		///<summary>Gets one EduResource from the db.</summary>
		public static EduResource GetOne(long eduResourceNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EduResource>(MethodBase.GetCurrentMethod(),eduResourceNum);
			}
			return Crud.EduResourceCrud.SelectOne(eduResourceNum);
		}
		*/