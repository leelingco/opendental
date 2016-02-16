//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Allergies;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.CDSIntervention;
import OpenDentBusiness.Cvx;
import OpenDentBusiness.Db;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.EhrLab;
import OpenDentBusiness.EhrLabResult;
import OpenDentBusiness.EhrLabs;
import OpenDentBusiness.EhrTrigger;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.Icd10s;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.MatchCardinality;
import OpenDentBusiness.Medication;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.RxNorm;
import OpenDentBusiness.RxNorms;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import OpenDentBusiness.Vitalsign;
import OpenDentBusiness.Vitalsigns;

/**
* 
*/
public class EhrTriggers   
{
    //If this table type will exist as cached data, uncomment the CachePattern region below and edit.
    /*
    		#region CachePattern
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all AutoTriggers.</summary>
    		private static List<AutoTrigger> listt;
    		///<summary>A list of all AutoTriggers.</summary>
    		public static List<AutoTrigger> Listt{
    			get {
    				if(listt==null) {
    					RefreshCache();
    				}
    				return listt;
    			}
    			set {
    				listt=value;
    			}
    		}
    		///<summary></summary>
    		public static DataTable RefreshCache(){
    			//No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
    			string command="SELECT * FROM autotrigger ORDER BY ItemOrder";//stub query probably needs to be changed
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="AutoTrigger";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.AutoTriggerCrud.TableToList(table);
    		}
    		#endregion
    		*/
    public static List<EhrTrigger> getAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrTrigger>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM ehrtrigger";
        return Crud.EhrTriggerCrud.SelectMany(command);
    }

    /**
    * This is the first step of automation, this checks to see if the new object matches one of the trigger conditions. 
    *  @param triggerObject Can be DiseaseDef, ICD9, Icd10, Snomed, Medication, RxNorm, Cvx, AllerfyDef, EHRLabResult, Patient, or VitalSign.
    *  @param PatCur Triggers and intervention are currently always dependant on current patient. 
    *  @return Returns a dictionary keyed on triggers and a list of all the objects that the trigger matched on. Should be used to generate CDS intervention message and later be passed to FormInfobutton for knowledge request.
    */
    public static List<CDSIntervention> triggerMatch(Object triggerObject, Patient PatCur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<CDSIntervention>>GetObject(MethodBase.GetCurrentMethod(), triggerObject, PatCur);
        }
         
        //Dictionary<string,List<object>> retVal=new Dictionary<string,List<object>>();
        List<CDSIntervention> retVal = new List<CDSIntervention>();
        //Define objects to be used in matching triggers.
        DiseaseDef diseaseDef;
        ICD9 icd9;
        Icd10 icd10;
        Snomed snomed;
        Medication medication;
        RxNorm rxNorm;
        Cvx cvx;
        AllergyDef allergyDef;
        EhrLabResult ehrLabResult;
        Patient pat;
        Vitalsign vitalsign;
        String triggerObjectMessage = "";
        String command = "";
        Name __dummyScrutVar0 = triggerObject.GetType().Name;
        if (__dummyScrutVar0.equals("DiseaseDef"))
        {
            diseaseDef = (DiseaseDef)triggerObject;
            command = "SELECT * FROM ehrtrigger" + " WHERE ProblemDefNumList LIKE '% " + POut.String(diseaseDef.DiseaseDefNum.ToString()) + " %'";
            // '% <code> %' so that we can get exact matches.
            if (!StringSupport.equals(diseaseDef.ICD9Code, ""))
            {
                command += " OR ProblemIcd9List LIKE '% " + POut.string(diseaseDef.ICD9Code) + " %'";
                triggerObjectMessage += "  -" + diseaseDef.ICD9Code + "(Icd9)  " + ICD9s.getByCode(diseaseDef.ICD9Code).Description + "\r\n";
            }
             
            if (!StringSupport.equals(diseaseDef.Icd10Code, ""))
            {
                command += " OR ProblemIcd10List LIKE '% " + POut.string(diseaseDef.Icd10Code) + " %'";
                triggerObjectMessage += "  -" + diseaseDef.Icd10Code + "(Icd10)  " + Icd10s.getByCode(diseaseDef.Icd10Code).Description + "\r\n";
            }
             
            if (!StringSupport.equals(diseaseDef.SnomedCode, ""))
            {
                command += " OR ProblemSnomedList LIKE '% " + POut.string(diseaseDef.SnomedCode) + " %'";
                triggerObjectMessage += "  -" + diseaseDef.SnomedCode + "(Snomed)  " + Snomeds.getByCode(diseaseDef.SnomedCode).Description + "\r\n";
            }
             
        }
        else if (__dummyScrutVar0.equals("ICD9"))
        {
            icd9 = (ICD9)triggerObject;
            //TODO: TriggerObjectMessage
            command = "SELECT * FROM ehrtrigger" + " WHERE Icd9List LIKE '% " + POut.string(icd9.ICD9Code) + " %'";
        }
        else // '% <code> %' so that we can get exact matches.
        if (__dummyScrutVar0.equals("Icd10"))
        {
            icd10 = (Icd10)triggerObject;
            //TODO: TriggerObjectMessage
            command = "SELECT * FROM ehrtrigger" + " WHERE Icd10List LIKE '% " + POut.string(icd10.Icd10Code) + " %'";
        }
        else // '% <code> %' so that we can get exact matches.
        if (__dummyScrutVar0.equals("Snomed"))
        {
            snomed = (Snomed)triggerObject;
            //TODO: TriggerObjectMessage
            command = "SELECT * FROM ehrtrigger" + " WHERE SnomedList LIKE '% " + POut.string(snomed.SnomedCode) + " %'";
        }
        else // '% <code> %' so that we can get exact matches.
        if (__dummyScrutVar0.equals("Medication"))
        {
            medication = (Medication)triggerObject;
            triggerObjectMessage = "  - " + medication.MedName + (medication.RxCui == 0 ? "" : " (RxCui:" + RxNorms.GetByRxCUI(medication.RxCui.ToString()).RxCui + ")") + "\r\n";
            command = "SELECT * FROM ehrtrigger" + " WHERE MedicationNumList LIKE '% " + POut.String(medication.MedicationNum.ToString()) + " %'";
            // '% <code> %' so that we can get exact matches.
            if (medication.RxCui != 0)
            {
                command += " OR RxCuiList LIKE '% " + POut.String(medication.RxCui.ToString()) + " %'";
            }
             
        }
        else // '% <code> %' so that we can get exact matches.
        if (__dummyScrutVar0.equals("RxNorm"))
        {
            rxNorm = (RxNorm)triggerObject;
            triggerObjectMessage = "  - " + rxNorm.Description + "(RxCui:" + rxNorm.RxCui + ")\r\n";
            command = "SELECT * FROM ehrtrigger" + " WHERE RxCuiList LIKE '% " + POut.string(rxNorm.RxCui) + " %'";
        }
        else // '% <code> %' so that we can get exact matches.
        if (__dummyScrutVar0.equals("Cvx"))
        {
            cvx = (Cvx)triggerObject;
            //TODO: TriggerObjectMessage
            command = "SELECT * FROM ehrtrigger" + " WHERE CvxList LIKE '% " + POut.string(cvx.CvxCode) + " %'";
        }
        else // '% <code> %' so that we can get exact matches.
        if (__dummyScrutVar0.equals("AllergyDef"))
        {
            allergyDef = (AllergyDef)triggerObject;
            //TODO: TriggerObjectMessage
            command = "SELECT * FROM ehrtrigger" + " WHERE AllergyDefNumList LIKE '% " + POut.String(allergyDef.AllergyDefNum.ToString()) + " %'";
        }
        else // '% <code> %' so that we can get exact matches.
        if (__dummyScrutVar0.equals("EhrLabResult"))
        {
            //match loinc only, no longer
            ehrLabResult = (EhrLabResult)triggerObject;
            //TODO: TriggerObjectMessage
            //LOINC may be in one of two fields
            command = "SELECT * FROM ehrtrigger WHERE " + "(LabLoincList LIKE '% " + ehrLabResult.ObservationIdentifierID + " %'" + "OR LabLoincList LIKE '% " + ehrLabResult.ObservationIdentifierIDAlt + " %')";
        }
        else //LOINC may be in one of two fields
        if (__dummyScrutVar0.equals("Patient"))
        {
            pat = (Patient)triggerObject;
            List<String> triggerNums = new List<String>();
            //TODO: TriggerObjectMessage
            command = "SELECT * FROM ehrtrigger WHERE DemographicsList !=''";
            List<EhrTrigger> triggers = Crud.EhrTriggerCrud.SelectMany(command);
            for (int i = 0;i < triggers.Count;i++)
            {
                String[] arrayDemoItems = triggers[i].DemographicsList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
                for (int j = 0;j < arrayDemoItems.Length;j++)
                {
                    System.Array<System.String>.INDEXER.APPLY.INDEXER __dummyScrutVar1 = arrayDemoItems[j].Split(',')[0];
                    if (__dummyScrutVar1.equals("age"))
                    {
                        int val = PIn.Int(Regex.Match(arrayDemoItems[j], "\\d+").Value);
                        if (arrayDemoItems[j].Contains("="))
                        {
                            //=, >=, or <=
                            if (val == pat.getAge())
                            {
                                triggerNums.Add(triggers[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                        if (arrayDemoItems[j].Contains("<"))
                        {
                            if (pat.getAge() < val)
                            {
                                triggerNums.Add(triggers[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                        if (arrayDemoItems[j].Contains(">"))
                        {
                            if (pat.getAge() > val)
                            {
                                triggerNums.Add(triggers[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                    }
                    else //should never happen, age element didn't contain a comparator
                    if (__dummyScrutVar1.equals("gender"))
                    {
                        if (arrayDemoItems[j].Split(',')[0].StartsWith(pat.Gender.ToString()))
                        {
                            triggerNums.Add(triggers[i].EhrTriggerNum.ToString());
                        }
                         
                    }
                    else
                    {
                    }  
                }
            }
            //should never happen
            triggerNums.Add("-1");
            //to ensure the querry is valid.
            command = "SELECT * FROM ehrTrigger WHERE EhrTriggerNum IN (" + String.Join(",", triggerNums) + ")";
        }
        else if (__dummyScrutVar0.equals("Vitalsign"))
        {
            List<String> trigNums = new List<String>();
            vitalsign = (Vitalsign)triggerObject;
            command = "SELECT * FROM ehrtrigger WHERE VitalLoincList !=''";
            List<EhrTrigger> triggersVit = Crud.EhrTriggerCrud.SelectMany(command);
            for (int i = 0;i < triggersVit.Count;i++)
            {
                String[] arrayVitalItems = triggersVit[i].VitalLoincList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
                for (int j = 0;j < arrayVitalItems.Length;j++)
                {
                    double val = PIn.Double(Regex.Match(arrayVitalItems[j], "\\d+(.(\\d+))*").Value);
                    //decimal value w or w/o decimal.
                    System.Array<System.String>.INDEXER.APPLY.INDEXER __dummyScrutVar2 = arrayVitalItems[j].Split(',')[0];
                    if (__dummyScrutVar2.equals("height"))
                    {
                        if (arrayVitalItems[j].Contains("="))
                        {
                            //=, >=, or <=
                            if (vitalsign.Height == val)
                            {
                                trigNums.Add(triggersVit[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                        if (arrayVitalItems[j].Contains("<"))
                        {
                            if (vitalsign.Height < val)
                            {
                                trigNums.Add(triggersVit[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                        if (arrayVitalItems[j].Contains(">"))
                        {
                            if (vitalsign.Height > val)
                            {
                                trigNums.Add(triggersVit[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                    }
                    else //should never happen, Height element didn't contain a comparator
                    if (__dummyScrutVar2.equals("weight"))
                    {
                        if (arrayVitalItems[j].Contains("="))
                        {
                            //=, >=, or <=
                            if (vitalsign.Weight == val)
                            {
                                trigNums.Add(triggersVit[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                        if (arrayVitalItems[j].Contains("<"))
                        {
                            if (vitalsign.Weight < val)
                            {
                                trigNums.Add(triggersVit[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                        if (arrayVitalItems[j].Contains(">"))
                        {
                            if (vitalsign.Weight > val)
                            {
                                trigNums.Add(triggersVit[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                    }
                    else if (__dummyScrutVar2.equals("BMI"))
                    {
                        float BMI = Vitalsigns.calcBMI(vitalsign.Weight,vitalsign.Height);
                        if (arrayVitalItems[j].Contains("="))
                        {
                            //=, >=, or <=
                            if (BMI == val)
                            {
                                trigNums.Add(triggersVit[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                        if (arrayVitalItems[j].Contains("<"))
                        {
                            if (BMI < val)
                            {
                                trigNums.Add(triggersVit[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                        if (arrayVitalItems[j].Contains(">"))
                        {
                            if (BMI > val)
                            {
                                trigNums.Add(triggersVit[i].EhrTriggerNum.ToString());
                                break;
                            }
                             
                        }
                         
                    }
                    else if (__dummyScrutVar2.equals("BP"))
                    {
                    }
                        
                }
            }
            //TODO
            //end switch
            //End Triggers Vit
            trigNums.Add("-1");
            //to ensure the querry is valid.
            command = "SELECT * FROM ehrTrigger WHERE EhrTriggerNum IN (" + String.Join(",", trigNums) + ")";
        }
        else
        {
            return null;
        }           
        //command="SELECT * FROM ehrtrigger WHERE false";//should not return any results.
        //break;
        List<EhrTrigger> listEhrTriggers = Crud.EhrTriggerCrud.SelectMany(command);
        if (listEhrTriggers.Count == 0)
        {
            return null;
        }
         
        for (int i = 0;i < listEhrTriggers.Count;i++)
        {
            //no triggers matched.
            //Check for MatchCardinality.One type triggers.----------------------------------------------------------------------------
            if (listEhrTriggers[i].Cardinality != MatchCardinality.One)
            {
                continue;
            }
             
            String triggerMessage = listEhrTriggers[i].Description + ":\r\n";
            //Example:"Patient over 55:\r\n"
            triggerMessage += triggerObjectMessage;
            //Example:"  -Patient Age 67\r\n"
            List<Object> ListObjectMatches = new List<Object>();
            ListObjectMatches.Add(triggerObject);
            CDSIntervention cdsi = new CDSIntervention();
            cdsi.EhrTrigger = listEhrTriggers[i];
            cdsi.InterventionMessage = triggerMessage;
            cdsi.TriggerObjects = ListObjectMatches;
            retVal.Add(cdsi);
        }
        //Fill object lists to be checked-------------------------------------------------------------------------------------------------
        List<Allergy> ListAllergy = Allergies.getAll(PatCur.PatNum,false);
        List<Disease> ListDisease = Diseases.refresh(PatCur.PatNum,true);
        List<DiseaseDef> ListDiseaseDef = new List<DiseaseDef>();
        List<EhrLab> ListEhrLab = EhrLabs.getAllForPat(PatCur.PatNum);
        //List<EhrLabResult> ListEhrLabResults=null;//Lab results are stored in a list in the EhrLab object.
        List<MedicationPat> ListMedicationPat = MedicationPats.refresh(PatCur.PatNum,false);
        List<AllergyDef> ListAllergyDef = new List<AllergyDef>();
        for (int i = 0;i < ListAllergy.Count;i++)
        {
            ListAllergyDef.Add(AllergyDefs.GetOne(ListAllergy[i].AllergyDefNum));
        }
        for (int i = 0;i < ListDisease.Count;i++)
        {
            ListDiseaseDef.Add(DiseaseDefs.GetItem(ListDisease[i].DiseaseDefNum));
        }
        for (int i = 0;i < listEhrTriggers.Count;i++)
        {
            if (listEhrTriggers[i].Cardinality == MatchCardinality.One)
            {
                continue;
            }
             
            //we handled these above.
            String triggerMessage = listEhrTriggers[i].Description + ":\r\n";
            triggerMessage += triggerObjectMessage;
            List<Object> ListObjectMatches = new List<Object>();
            //Allergy, Disease, LabPanels, MedicationPat, Patient, VaccinePat
            ListObjectMatches.Add(triggerObject);
            //Allergy-----------------------------------------------------------------------------------------------------------------------
            //allergy.snomedreaction
            //allergy.AllergyDefNum>>AllergyDef.SnomedType
            //allergy.AllergyDefNum>>AllergyDef.SnomedAllergyTo
            //allergy.AllergyDefNum>>AllergyDef.MedicationNum>>Medication.RxCui
            //Disease-----------------------------------------------------------------------------------------------------------------------
            //Disease.DiseaseDefNum>>DiseaseDef.ICD9Code
            //Disease.DiseaseDefNum>>DiseaseDef.SnomedCode
            //Disease.DiseaseDefNum>>DiseaseDef.Icd10Code
            //LabPanels---------------------------------------------------------------------------------------------------------------------
            //LabPanel.LabPanelNum<<LabResult.TestId (Loinc)
            //LabPanel.LabPanelNum<<LabResult.ObsValue (Loinc)
            //LabPanel.LabPanelNum<<LabResult.ObsRange (Loinc)
            //MedicationPat-----------------------------------------------------------------------------------------------------------------
            //MedicationPat.RxCui
            //MedicationPat.MedicationNum>>Medication.RxCui
            //Patient>>Demographics---------------------------------------------------------------------------------------------------------
            //Patient.Gender
            //Patient.Birthdate (Loinc age?)
            //Patient.SmokingSnoMed
            //RxPat-------------------------------------------------------------------------------------------------------------------------
            //Do not check RxPat. It is useless.
            //VaccinePat--------------------------------------------------------------------------------------------------------------------
            //VaccinePat.VaccineDefNum>>VaccineDef.CVXCode
            //VitalSign---------------------------------------------------------------------------------------------------------------------
            //VitalSign.Height (Loinc)
            //VitalSign.Weight (Loinc)
            //VitalSign.BpSystolic (Loinc)
            //VitalSign.BpDiastolic (Loinc)
            //VitalSign.WeightCode (Snomed)
            //VitalSign.PregDiseaseNum (Snomed)
            //Use object matches to check if required conditions are met-------------------------------------------------------------------------------
            Cardinality __dummyScrutVar3 = listEhrTriggers[i].Cardinality;
            if (__dummyScrutVar3.equals(MatchCardinality.One))
            {
                continue;
            }
            else //should never get here, handled above.
            //falls through to two or more, but then branches at the end of the case statement.
            if (__dummyScrutVar3.equals(MatchCardinality.OneOfEachCategory) || __dummyScrutVar3.equals(MatchCardinality.TwoOrMore))
            {
                for (int m = 0;m < ListMedicationPat.Count;m++)
                {
                    //if(ListObjectMatches.Count<2) {
                    //	continue;//Must match at least two objects for this category.
                    //}
                    //Medication
                    if (listEhrTriggers[i].MedicationNumList.Contains(" " + ListMedicationPat[m].MedicationNum + " "))
                    {
                        ListObjectMatches.Add(ListMedicationPat[m]);
                        continue;
                    }
                     
                    if (ListMedicationPat[m].RxCui != 0 && listEhrTriggers[i].RxCuiList.Contains(" " + ListMedicationPat[m].RxCui + " "))
                    {
                        ListObjectMatches.Add(ListMedicationPat[m]);
                        continue;
                    }
                     
                }
                for (int a = 0;a < ListAllergy.Count;a++)
                {
                    //Allergy
                    if (listEhrTriggers[i].AllergyDefNumList.Contains(" " + ListAllergy[a].AllergyDefNum + " "))
                    {
                        ListObjectMatches.Add(AllergyDefs.GetOne(ListAllergy[a].AllergyDefNum));
                        triggerMessage += "  -(Allergy) " + AllergyDefs.GetOne(ListAllergy[a].AllergyDefNum).Description + "\r\n";
                        continue;
                    }
                     
                }
                for (int d = 0;d < ListDiseaseDef.Count;d++)
                {
                    //Problem
                    if (!StringSupport.equals(ListDiseaseDef[d].ICD9Code, "") && listEhrTriggers[i].ProblemIcd9List.Contains(" " + ListDiseaseDef[d].ICD9Code + " "))
                    {
                        ListObjectMatches.Add(ListDiseaseDef[d]);
                        triggerMessage += "  -(ICD9) " + ICD9s.GetByCode(ListDiseaseDef[d].ICD9Code).Description + "\r\n";
                        continue;
                    }
                     
                    if (!StringSupport.equals(ListDiseaseDef[d].Icd10Code, "") && listEhrTriggers[i].ProblemIcd10List.Contains(" " + ListDiseaseDef[d].Icd10Code + " "))
                    {
                        ListObjectMatches.Add(ListDiseaseDef[d]);
                        triggerMessage += "  -(Icd10) " + Icd10s.GetByCode(ListDiseaseDef[d].Icd10Code).Description + "\r\n";
                        continue;
                    }
                     
                    if (!StringSupport.equals(ListDiseaseDef[d].SnomedCode, "") && listEhrTriggers[i].ProblemSnomedList.Contains(" " + ListDiseaseDef[d].SnomedCode + " "))
                    {
                        ListObjectMatches.Add(ListDiseaseDef[d]);
                        triggerMessage += "  -(Snomed) " + Snomeds.GetByCode(ListDiseaseDef[d].SnomedCode).Description + "\r\n";
                        continue;
                    }
                     
                    if (listEhrTriggers[i].ProblemDefNumList.Contains(" " + ListDiseaseDef[d].DiseaseDefNum + " "))
                    {
                        ListObjectMatches.Add(ListDiseaseDef[d]);
                        triggerMessage += "  -(Problem Def) " + ListDiseaseDef[d].DiseaseName + "\r\n";
                        continue;
                    }
                     
                }
                for (int l = 0;l < ListEhrLab.Count;l++)
                {
                    for (int r = 0;r < ListEhrLab[l].ListEhrLabResults.Count;r++)
                    {
                        //Vital
                        //TODO
                        //Age
                        //TODO
                        //Gender
                        //TODO
                        //Lab Result
                        if (listEhrTriggers[i].LabLoincList.Contains(" " + ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierID + " ") || listEhrTriggers[i].LabLoincList.Contains(" " + ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierIDAlt + " "))
                        {
                            ListObjectMatches.Add(ListEhrLab[l].ListEhrLabResults[r]);
                            if (!StringSupport.equals(ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierID, ""))
                            {
                                //should almost always be the case.
                                triggerMessage += "  -(LOINC) " + Loincs.GetByCode(ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierID).NameShort + "\r\n";
                            }
                            else if (!StringSupport.equals(ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierID, ""))
                            {
                                triggerMessage += "  -(LOINC) " + Loincs.GetByCode(ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierIDAlt).NameShort + "\r\n";
                            }
                            else if (!StringSupport.equals(ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierText, ""))
                            {
                                triggerMessage += "  -(LOINC) " + ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierText + "\r\n";
                            }
                            else if (!StringSupport.equals(ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierTextAlt, ""))
                            {
                                triggerMessage += "  -(LOINC) " + ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierTextAlt + "\r\n";
                            }
                            else if (!StringSupport.equals(ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierID, ""))
                            {
                                triggerMessage += "  -(LOINC) " + ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierID + "\r\n";
                            }
                            else if (!StringSupport.equals(ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierIDAlt, ""))
                            {
                                triggerMessage += "  -(LOINC) " + ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierIDAlt + "\r\n";
                            }
                            else if (!StringSupport.equals(ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierTextOriginal, ""))
                            {
                                triggerMessage += "  -(LOINC) " + ListEhrLab[l].ListEhrLabResults[r].ObservationIdentifierTextOriginal + "\r\n";
                            }
                            else
                            {
                                triggerMessage += "  -(LOINC) Unknown code.\r\n";
                            }       
                            continue;
                        }
                         
                    }
                }
                //should never happen.
                ListObjectMatches = RemoveDuplicateObjectsHelper(ListObjectMatches);
                if (listEhrTriggers[i].Cardinality == MatchCardinality.TwoOrMore && ListObjectMatches.Count < 2)
                {
                    continue;
                }
                 
                //next trigger, do not add to retVal
                if (listEhrTriggers[i].Cardinality == MatchCardinality.OneOfEachCategory && !OneOfEachCategoryHelper(listEhrTriggers[i], ListObjectMatches))
                {
                    continue;
                }
                 
            }
            else if (__dummyScrutVar3.equals(MatchCardinality.All))
            {
                boolean allConditionsMet = true;
                List<String> MatchedCodes = getCodesFromListHelper(ListObjectMatches);
                //new List<string>();
                //Match all Icd9Codes-------------------------------------------------------------------------------------------------------------------------------------------------
                String[] arrayIcd9Codes = listEhrTriggers[i].ProblemIcd9List.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
                for (int c = 0;c < arrayIcd9Codes.Length;c++)
                {
                    if (MatchedCodes.Contains(arrayIcd9Codes[i]))
                    {
                        continue;
                    }
                     
                    //found required code
                    //required code not found, set allConditionsMet to false and continue to next trigger
                    allConditionsMet = false;
                    break;
                }
                if (!allConditionsMet)
                {
                    continue;
                }
                 
                //next trigger
                //Match all Icd10Codes------------------------------------------------------------------------------------------------------------------------------------------------
                String[] arrayIcd10Codes = listEhrTriggers[i].ProblemIcd10List.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
                for (int c = 0;c < arrayIcd10Codes.Length;c++)
                {
                    if (MatchedCodes.Contains(arrayIcd10Codes[i]))
                    {
                        continue;
                    }
                     
                    //found required code
                    //required code not found, set allConditionsMet to false and continue to next trigger
                    allConditionsMet = false;
                    break;
                }
                if (!allConditionsMet)
                {
                    continue;
                }
                 
                //next trigger
                //Match all SnomedCodes-----------------------------------------------------------------------------------------------------------------------------------------------
                String[] arraySnomedCodes = listEhrTriggers[i].ProblemSnomedList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
                for (int c = 0;c < arraySnomedCodes.Length;c++)
                {
                    if (MatchedCodes.Contains(arraySnomedCodes[i]))
                    {
                        continue;
                    }
                     
                    //found required code
                    //required code not found, set allConditionsMet to false and continue to next trigger
                    allConditionsMet = false;
                    break;
                }
                if (!allConditionsMet)
                {
                    continue;
                }
                 
                //next trigger
                //Match all CvxCodes--------------------------------------------------------------------------------------------------------------------------------------------------
                String[] arrayCvxCodes = listEhrTriggers[i].CvxList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
                for (int c = 0;c < arrayCvxCodes.Length;c++)
                {
                    if (MatchedCodes.Contains(arrayCvxCodes[i]))
                    {
                        continue;
                    }
                     
                    //found required code
                    //required code not found, set allConditionsMet to false and continue to next trigger
                    allConditionsMet = false;
                    break;
                }
                if (!allConditionsMet)
                {
                    continue;
                }
                 
                //next trigger, do not add to retval
                //Match all LoincCodes------------------------------------------------------------------------------------------------------------------------------------------------
                String[] arrayLoincCodes = listEhrTriggers[i].LabLoincList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
                for (int c = 0;c < arrayLoincCodes.Length;c++)
                {
                    if (MatchedCodes.Contains(arrayLoincCodes[i]))
                    {
                        continue;
                    }
                     
                    //found required code
                    //required code not found, set allConditionsMet to false and continue to next trigger
                    allConditionsMet = false;
                    break;
                }
                if (!allConditionsMet)
                {
                    continue;
                }
                 
            }
               
            //next trigger, do not add to retval
            //TODO:with values
            //Match all Vitals----------------------------------------------------------------------------------------------------------------------------------------------------
            //TODO:with values
            //Match all Demographics---------------------------------------------------------------------------------------------------------------------------------------------
            //if(listEhrTriggers[i].DemographicAgeGreaterThan!=0){
            //	if(PatCur.Birthdate.Year>1880 && PatCur.Birthdate.AddYears(listEhrTriggers[i].DemographicAgeGreaterThan)>DateTime.Now){//patient too young
            //		continue;//next trigger
            //	}
            //}
            //if(listEhrTriggers[i].DemographicAgeLessThan!=0){
            //	if(PatCur.Birthdate.Year>1880 && PatCur.Birthdate.AddYears(listEhrTriggers[i].DemographicAgeGreaterThan)<DateTime.Now){//patient too old
            //		continue;//next trigger
            //	}
            //}
            //if(listEhrTriggers[i].DemographicGender!=""){
            //	if(!listEhrTriggers[i].DemographicGender.Contains(PatCur.Gender.ToString())){//Patient Gender not in gender list of trigger
            //		continue;//next trigger
            //	}
            //}
            //TODO: construct trigger message using all the codes in the trigger.
            //end switch trigger cardinality
            //retVal.Add(triggerMessage,ListObjectMatches);
            CDSIntervention cdsi = new CDSIntervention();
            cdsi.EhrTrigger = listEhrTriggers[i];
            cdsi.InterventionMessage = triggerMessage;
            cdsi.TriggerObjects = ListObjectMatches;
            retVal.Add(cdsi);
        }
        return retVal;
    }

    //end triggers
    private static List<String> getCodesFromListHelper(List<Object> ListObjectMatches) throws Exception {
        List<String> retVal = new List<String>();
        for (int i = 0;i < ListObjectMatches.Count;i++)
        {
            Name __dummyScrutVar4 = ListObjectMatches[i].GetType().Name;
            if (__dummyScrutVar4.equals("DiseaseDef"))
            {
                retVal.Add(((DiseaseDef)ListObjectMatches[i]).DiseaseDefNum.ToString());
                retVal.Add(((DiseaseDef)ListObjectMatches[i]).ICD9Code);
                retVal.Add(((DiseaseDef)ListObjectMatches[i]).Icd10Code);
                retVal.Add(((DiseaseDef)ListObjectMatches[i]).SnomedCode);
            }
            else if (__dummyScrutVar4.equals("ICD9"))
            {
                retVal.Add(((ICD9)ListObjectMatches[i]).ICD9Code);
            }
            else if (__dummyScrutVar4.equals("Icd10"))
            {
                retVal.Add(((Icd10)ListObjectMatches[i]).Icd10Code);
            }
            else if (__dummyScrutVar4.equals("Snomed"))
            {
                retVal.Add(((Snomed)ListObjectMatches[i]).SnomedCode);
            }
            else if (__dummyScrutVar4.equals("Medication"))
            {
                retVal.Add(((Medication)ListObjectMatches[i]).MedicationNum.ToString());
                retVal.Add(((Medication)ListObjectMatches[i]).RxCui.ToString());
            }
            else if (__dummyScrutVar4.equals("RxNorm"))
            {
                retVal.Add(((RxNorm)ListObjectMatches[i]).RxCui);
            }
            else if (__dummyScrutVar4.equals("Cvx"))
            {
                retVal.Add(((Cvx)ListObjectMatches[i]).CvxCode);
            }
            else if (__dummyScrutVar4.equals("AllergyDef"))
            {
                retVal.Add(((AllergyDef)ListObjectMatches[i]).AllergyDefNum.ToString());
            }
            else if (__dummyScrutVar4.equals("EHRLabResult"))
            {
                //match loinc only
                retVal.Add(((EhrLabResult)ListObjectMatches[i]).ObservationIdentifierID);
                retVal.Add(((EhrLabResult)ListObjectMatches[i]).ObservationIdentifierIDAlt);
            }
            else if (__dummyScrutVar4.equals("Patient"))
            {
                retVal.Add(((Patient)ListObjectMatches[i]).Gender.ToString());
            }
            else //Maybe more here?
            if (__dummyScrutVar4.equals("VitalSign"))
            {
            }
            else
            {
            }           
        }
        return retVal;
    }

    //retVal.Add(((Vitalsign)ListObjectMatches[i]).???);
    /**
    * Returns true if ListObjectMatches satisfies trigger conditions.
    */
    private static boolean oneOfEachCategoryHelper(EhrTrigger ehrTrigger, List<Object> ListObjectMatches) throws Exception {
        //problems
        if (!StringSupport.equals(ehrTrigger.ProblemDefNumList.Trim(), "") || !StringSupport.equals(ehrTrigger.ProblemIcd9List.Trim(), "") || !StringSupport.equals(ehrTrigger.ProblemIcd10List.Trim(), "") || !StringSupport.equals(ehrTrigger.ProblemSnomedList.Trim(), ""))
        {
            for (int i = 0;i < ListObjectMatches.Count;i++)
            {
                //problem condition exists
                if (StringSupport.equals(ListObjectMatches[i].GetType().Name, "DiseaseDef"))
                {
                    break;
                }
                 
                //satisfied problem category, move on to next category
                if (i == ListObjectMatches.Count - 1)
                {
                    return false;
                }
                 
            }
        }
         
        //made it to end of list and did not find a problem.
        //end list matches
        //end problem
        //medication
        if (!StringSupport.equals(ehrTrigger.MedicationNumList.Trim(), "") || !StringSupport.equals(ehrTrigger.RxCuiList.Trim(), "") || !StringSupport.equals(ehrTrigger.CvxList.Trim(), ""))
        {
            for (int i = 0;i < ListObjectMatches.Count;i++)
            {
                //Medication condition exists
                if (StringSupport.equals(ListObjectMatches[i].GetType().Name, "Medication") || StringSupport.equals(ListObjectMatches[i].GetType().Name, "VaccineDef"))
                {
                    break;
                }
                 
                //satisfied medication category, move on to next category
                if (i == ListObjectMatches.Count - 1)
                {
                    return false;
                }
                 
            }
        }
         
        //made it to end of list and did not find a problem.
        //end list matches
        //end medication
        //allergy
        if (!StringSupport.equals(ehrTrigger.AllergyDefNumList.Trim(), ""))
        {
            for (int i = 0;i < ListObjectMatches.Count;i++)
            {
                //Allergy condition exists
                if (StringSupport.equals(ListObjectMatches[i].GetType().Name, "AllergyDef"))
                {
                    break;
                }
                 
                //satisfied Allergy category, move on to next category
                if (i == ListObjectMatches.Count - 1)
                {
                    return false;
                }
                 
            }
        }
         
        return true;
    }

    //made it to end of list and did not find a problem.
    //end list matches
    //end allergy
    //lab-todo
    //vitals-todo
    //demographics-todo
    private static List<Object> removeDuplicateObjectsHelper(List<Object> ListObjectMatches) throws Exception {
        List<Object> retVal = new List<Object>();
        for (int i = 0;i < ListObjectMatches.Count;i++)
        {
            boolean IsNew = true;
            for (int j = 0;j < retVal.Count;j++)
            {
                if (retVal[j].GetType() != ListObjectMatches[i].GetType())
                {
                    continue;
                }
                 
                Name __dummyScrutVar5 = retVal[j].GetType().Name;
                if (__dummyScrutVar5.equals("AllergyDef"))
                {
                    if (((AllergyDef)ListObjectMatches[i]).AllergyDefNum == ((AllergyDef)retVal[j]).AllergyDefNum)
                    {
                        IsNew = false;
                    }
                     
                }
                else
                {
                } 
            }
            //handle other cases as needed.
            if (IsNew)
            {
                retVal.Add(ListObjectMatches[i]);
            }
             
        }
        return retVal;
    }

    //next input object
    /**
    * 
    */
    public static long insert(EhrTrigger ehrTrigger) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrTrigger.EhrTriggerNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrTrigger);
            return ehrTrigger.EhrTriggerNum;
        }
         
        return Crud.EhrTriggerCrud.Insert(ehrTrigger);
    }

    /**
    * 
    */
    public static void update(EhrTrigger ehrTrigger) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrTrigger);
            return ;
        }
         
        Crud.EhrTriggerCrud.Update(ehrTrigger);
    }

    /**
    * 
    */
    public static void delete(long ehrTriggerNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrTriggerNum);
            return ;
        }
         
        String command = "DELETE FROM ehrtrigger WHERE EhrTriggerNum = " + POut.long(ehrTriggerNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<AutoTrigger> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<AutoTrigger>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM autotrigger WHERE PatNum = "+POut.Long(patNum);
			return Crud.AutoTriggerCrud.SelectMany(command);
		}
		///<summary>Gets one AutoTrigger from the db.</summary>
		public static AutoTrigger GetOne(long automationTriggerNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<AutoTrigger>(MethodBase.GetCurrentMethod(),automationTriggerNum);
			}
			return Crud.AutoTriggerCrud.SelectOne(automationTriggerNum);
		}
		///<summary></summary>
		public static long Insert(AutoTrigger autoTrigger){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				autoTrigger.AutomationTriggerNum=Meth.GetLong(MethodBase.GetCurrentMethod(),autoTrigger);
				return autoTrigger.AutomationTriggerNum;
			}
			return Crud.AutoTriggerCrud.Insert(autoTrigger);
		}
		///<summary></summary>
		public static void Update(AutoTrigger autoTrigger){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),autoTrigger);
				return;
			}
			Crud.AutoTriggerCrud.Update(autoTrigger);
		}
		*/