//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package WebHostSynch;

import OpenDentBusiness.DeletedObject;
import OpenDentBusiness.DeletedObjects;
import OpenDentBusiness.Mobile.AllergyDefm;
import OpenDentBusiness.Mobile.AllergyDefms;
import OpenDentBusiness.Mobile.Allergym;
import OpenDentBusiness.Mobile.Allergyms;
import OpenDentBusiness.Mobile.Appointmentm;
import OpenDentBusiness.Mobile.Appointmentms;
import OpenDentBusiness.Mobile.DiseaseDefm;
import OpenDentBusiness.Mobile.DiseaseDefms;
import OpenDentBusiness.Mobile.Diseasem;
import OpenDentBusiness.Mobile.Diseasems;
import OpenDentBusiness.Mobile.Documentm;
import OpenDentBusiness.Mobile.Documentms;
import OpenDentBusiness.Mobile.ICD9m;
import OpenDentBusiness.Mobile.ICD9ms;
import OpenDentBusiness.Mobile.LabPanelm;
import OpenDentBusiness.Mobile.LabPanelms;
import OpenDentBusiness.Mobile.LabResultm;
import OpenDentBusiness.Mobile.LabResultms;
import OpenDentBusiness.Mobile.Medicationm;
import OpenDentBusiness.Mobile.Medicationms;
import OpenDentBusiness.Mobile.MedicationPatm;
import OpenDentBusiness.Mobile.MedicationPatms;
import OpenDentBusiness.Mobile.Patientm;
import OpenDentBusiness.Mobile.Patientms;
import OpenDentBusiness.Mobile.Pharmacym;
import OpenDentBusiness.Mobile.Pharmacyms;
import OpenDentBusiness.Mobile.Prefm;
import OpenDentBusiness.Mobile.Prefms;
import OpenDentBusiness.Mobile.Providerm;
import OpenDentBusiness.Mobile.Providerms;
import OpenDentBusiness.Mobile.Recallm;
import OpenDentBusiness.Mobile.Recallms;
import OpenDentBusiness.Mobile.RxPatm;
import OpenDentBusiness.Mobile.RxPatms;
import OpenDentBusiness.Mobile.Statementm;
import OpenDentBusiness.Mobile.Statementms;
import WebForms.Logger;
import WebHostSynch.Properties.Settings;

/**
* Summary description for Mobile
*/
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line.
// [System.Web.Script.Services.ScriptService]
public class Mobile  extends System.Web.Services.WebService 
{
    private WebHostSynch.Util util = new WebHostSynch.Util();
    private long customerNum = 0;
    /**
    * An empty method to test if the webservice is up and running. this was made with the intention of testing the correctness of the webservice URL on an Open Dental Installation. If an incorrect webservice URL is used in a background thread of OD the exception cannot be handled easily.
    */
    public boolean serviceExists() throws Exception {
        try
        {
            util.setMobileDbConnection();
            Logger.information("in ServiceExists()");
            return true;
        }
        catch (Exception ex)
        {
            Logger.logError(ex);
            return false;
        }
    
    }

    public long getCustomerNum(String RegistrationKeyFromDentalOffice) throws Exception {
        return util.getDentalOfficeID(RegistrationKeyFromDentalOffice);
    }

    public boolean isPaidCustomer(String RegistrationKey) throws Exception {
        boolean IsPaidCustomer = false;
        try
        {
            Logger.information("In IsPaidCustomer");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum != 0)
            {
                IsPaidCustomer = util.isPaidCustomer(customerNum);
            }
             
            return IsPaidCustomer;
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            return IsPaidCustomer;
        }
    
    }

    public void deleteObjects(String RegistrationKey, List<DeletedObject> dOList) throws Exception {
        try
        {
            Logger.information("In DeleteObjects");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            DeletedObjects.DeleteForMobile(dOList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in DeleteObjects");
        }
    
    }

    public void deleteAllRecords(String RegistrationKey) throws Exception {
        try
        {
            Logger.information("In DeleteAllRecords");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            //mobile web
            Patientms.deleteAll(customerNum);
            Appointmentms.deleteAll(customerNum);
            RxPatms.deleteAll(customerNum);
            Providerms.deleteAll(customerNum);
            Pharmacyms.deleteAll(customerNum);
            Recallms.deleteAll(customerNum);
            //pat portal
            LabPanelms.deleteAll(customerNum);
            LabResultms.deleteAll(customerNum);
            Medicationms.deleteAll(customerNum);
            MedicationPatms.deleteAll(customerNum);
            AllergyDefms.deleteAll(customerNum);
            Allergyms.deleteAll(customerNum);
            DiseaseDefms.deleteAll(customerNum);
            Diseasems.deleteAll(customerNum);
            ICD9ms.deleteAll(customerNum);
            Statementms.deleteAll(customerNum);
            Documentms.deleteAll(customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in DeleteAllRecords");
        }
    
    }

    public void synchPatients(String RegistrationKey, List<Patientm> patientmList) throws Exception {
        try
        {
            Logger.information("In SynchPatients");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            Patientms.UpdateFromChangeList(patientmList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchPatients");
        }
    
    }

    public void synchAppointments(String RegistrationKey, List<Appointmentm> appointmentList) throws Exception {
        try
        {
            Logger.information("In SynchAppointments");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            Appointmentms.UpdateFromChangeList(appointmentList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchAppointments");
        }
    
    }

    public void synchPrescriptions(String RegistrationKey, List<RxPatm> rxList) throws Exception {
        try
        {
            Logger.information("In SynchPrescriptions");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            RxPatms.UpdateFromChangeList(rxList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchPrescriptions");
        }
    
    }

    public void synchProviders(String RegistrationKey, List<Providerm> providerList) throws Exception {
        try
        {
            Logger.information("In SynchProviders");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            Providerms.UpdateFromChangeList(providerList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchProviders");
        }
    
    }

    public void synchPharmacies(String RegistrationKey, List<Pharmacym> pharmacyList) throws Exception {
        try
        {
            Logger.information("In SynchPharmacies");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            Pharmacyms.UpdateFromChangeList(pharmacyList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchPharmacies");
        }
    
    }

    public String getUserName(String RegistrationKey) throws Exception {
        String UserName = "";
        try
        {
            Logger.information("In GetUserName");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum != 0)
            {
                UserName = util.getMobileWebUserName(customerNum);
            }
             
            return UserName;
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            return UserName;
        }
    
    }

    public void setMobileWebUserPassword(String RegistrationKey, String UserName, String Password) throws Exception {
        try
        {
            Logger.information("In SetMobileWebPassword");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
            else
            {
                util.SetMobileWebUserPassword(customerNum, UserName, Password);
            } 
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SetMobileWebUserPassword");
        }
    
    }

    public void setPracticeTitle(String RegistrationKey, String PracticeTitle) throws Exception {
        try
        {
            Logger.information("In SetPracticeTitle");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
            else
            {
            } 
        }
        catch (Exception ex)
        {
            // we do nothing here because the preferencem table now has a PrefNum column and the  older code did not take into account this column
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SetPracticeTitle");
        }
    
    }

    public void setPreference(String RegistrationKey, Prefm prefm) throws Exception {
        try
        {
            Logger.information("In SetPreference");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
            else
            {
                prefm.CustomerNum = customerNum;
                Prefms.updatePreference(prefm);
            } 
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SetPreference");
        }
    
    }

    public String getPatientPortalAddress(String RegistrationKey) throws Exception {
        long DentalOfficeID = util.getDentalOfficeID(RegistrationKey);
        if (DentalOfficeID == 0)
        {
            return "";
        }
         
        String PatientPortalAddress = "";
        try
        {
            PatientPortalAddress = Settings.getDefault().getPatientPortalAddress();
        }
        catch (Exception ex)
        {
            Logger.logError(ex);
        }

        Logger.information("In GetPatientPortalAddress PatientPortalAddress=" + PatientPortalAddress);
        return PatientPortalAddress;
    }

    public void synchLabPanels(String RegistrationKey, List<LabPanelm> labPanelmList) throws Exception {
        try
        {
            Logger.information("In SynchLabPanels");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            LabPanelms.UpdateFromChangeList(labPanelmList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchLabPanels");
        }
    
    }

    public void synchLabResults(String RegistrationKey, List<LabResultm> labResultmList) throws Exception {
        try
        {
            Logger.information("In SynchLabResults");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            LabResultms.UpdateFromChangeList(labResultmList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchLabResults");
        }
    
    }

    public void synchMedications(String RegistrationKey, List<Medicationm> medicationmList) throws Exception {
        try
        {
            Logger.information("In SynchMedications");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            Medicationms.UpdateFromChangeList(medicationmList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchMedications");
        }
    
    }

    public void synchMedicationPats(String RegistrationKey, List<MedicationPatm> medicationPatList) throws Exception {
        try
        {
            Logger.information("In SynchMedicationPats");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            MedicationPatms.UpdateFromChangeList(medicationPatList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchMedicationPats");
        }
    
    }

    public void synchAllergies(String RegistrationKey, List<Allergym> allergyList) throws Exception {
        try
        {
            Logger.information("In SynchAllergies");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            Allergyms.UpdateFromChangeList(allergyList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchAllergies");
        }
    
    }

    public void synchAllergyDefs(String RegistrationKey, List<AllergyDefm> allergyDefList) throws Exception {
        try
        {
            Logger.information("In SynchAllergyDefs");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            AllergyDefms.UpdateFromChangeList(allergyDefList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchAllergyDefs");
        }
    
    }

    public void synchDiseases(String RegistrationKey, List<Diseasem> diseaseList) throws Exception {
        try
        {
            Logger.information("In SynchDiseases");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            Diseasems.UpdateFromChangeList(diseaseList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchDiseases");
        }
    
    }

    public void synchDiseaseDefs(String RegistrationKey, List<DiseaseDefm> diseaseDefList) throws Exception {
        try
        {
            Logger.information("In SynchDiseaseDefs");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            DiseaseDefms.UpdateFromChangeList(diseaseDefList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchDiseaseDefs");
        }
    
    }

    public void synchICD9s(String RegistrationKey, List<ICD9m> icd9List) throws Exception {
        try
        {
            Logger.information("In SynchICD9s");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            ICD9ms.UpdateFromChangeList(icd9List, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchICD9s");
        }
    
    }

    public void synchStatements(String RegistrationKey, List<Statementm> statementList) throws Exception {
        try
        {
            Logger.information("In SynchStatements");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            Statementms.UpdateFromChangeList(statementList, customerNum);
            //now delete some statements to restrict the number of statements per patient.
            int limitPerPatient = 5;
            List<long> patList = statementList.Select(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(sl) => {
                return sl.PatNum;
            }" */).Distinct().ToList();
            //select distint patients from the list.
            Statementms.LimitStatementmsPerPatient(patList, customerNum, limitPerPatient);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchStatements");
        }
    
    }

    public void synchDocuments(String RegistrationKey, List<Documentm> documentList) throws Exception {
        try
        {
            Logger.information("In SynchDocuments");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            Documentms.UpdateFromChangeList(documentList, customerNum);
            //now delete documents whoes DocNums are not found in the statements table
            Documentms.limitDocumentmsPerPatient(customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchDocuments");
        }
    
    }

    public void synchRecalls(String RegistrationKey, List<Recallm> recallList) throws Exception {
        try
        {
            Logger.information("In SynchRecalls");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            Recallms.UpdateFromChangeList(recallList, customerNum);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in SynchRecalls");
        }
    
    }

    /**
    * Deletes records associated with the PatNums
    */
    public void deletePatientsRecords(String RegistrationKey, List<long> patNumList) throws Exception {
        try
        {
            Logger.information("In DeletePatientsRecords");
            customerNum = util.getDentalOfficeID(RegistrationKey);
            if (customerNum == 0)
            {
                return ;
            }
             
            for (int i = 0;i < patNumList.Count;i++)
            {
                //Dennis: an inefficient loop but will work fine for the small number of records and will use existing default methods of the ms class
                /* On OD if a labpanel is deleted the corresponding labresults are also deleted. This will ensure that on the webserver labresults are deleted via 	the DeleteObjects function
                						 * a similar situation would be true for  medications, allergydefs and disease defs.
                						 * If however the patient password is set to blank then the corresponding deletes of labresults, medications, allergydefs and disease defs will not occur causing some unnecessary records to be present on the webserver. 
                						 * Given the current level of coding it's important to leave these unnecessary records on the webserver because the moment a patient password is not blank they will be needed again.
                						*/
                LabPanelms.Delete(customerNum, patNumList[i]);
                MedicationPatms.Delete(customerNum, patNumList[i]);
                Allergyms.Delete(customerNum, patNumList[i]);
                Diseasems.Delete(customerNum, patNumList[i]);
                Statementms.Delete(customerNum, patNumList[i]);
                Documentms.Delete(customerNum, patNumList[i]);
            }
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + customerNum,ex);
            throw new Exception("Exception in DeletePatientsRecords");
        }
    
    }

}


