//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormProgress;
import OpenDental.Lan;
import OpenDental.MobileWeb.Mobile;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PassProgressDelegate;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.DeletedObject;
import OpenDentBusiness.DeletedObjects;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.MiscData;
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
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.RxPat;
import OpenDentBusiness.RxPats;
import OpenDentBusiness.Statement;
import OpenDentBusiness.Statements;

public class FormMobile  extends Form 
{

    private static Mobile mb = new Mobile();
    private static int BatchSize = 100;
    /**
    * All statements of a patient are not uploaded. The limit is defined by the recent [statementLimitPerPatient] records
    */
    private static int statementLimitPerPatient = 5;
    /**
    * This variable prevents the synching methods from being called when a previous synch is in progress.
    */
    private static boolean IsSynching = new boolean();
    /**
    * This variable prevents multiple error message boxes from popping up if mobile synch server is not available.
    */
    private static boolean IsServerAvail = true;
    /**
    * True if a pref was saved and the other workstations need to have their cache refreshed when this form closes.
    */
    private boolean changed = new boolean();
    /**
    * If this variable is true then records are uploaded one at a time so that an error in uploading can be traced down to a single record
    */
    private static boolean IsTroubleshootMode = false;
    private static FormProgress FormP;
    private enum SynchEntity
    {
        patient,
        appointment,
        prescription,
        provider,
        pharmacy,
        labpanel,
        labresult,
        medication,
        medicationpat,
        allergy,
        allergydef,
        disease,
        diseasedef,
        icd9,
        statement,
        document,
        recall,
        deletedobject,
        patientdel
    }
    public FormMobile() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formMobileSetup_Load(Object sender, EventArgs e) throws Exception {
        textMobileSyncServerURL.Text = PrefC.getString(PrefName.MobileSyncServerURL);
        textSynchMinutes.Text = PrefC.getInt(PrefName.MobileSyncIntervalMinutes).ToString();
        textDateBefore.Text = PrefC.getDate(PrefName.MobileExcludeApptsBeforeDate).ToShortDateString();
        textMobileSynchWorkStation.Text = PrefC.getString(PrefName.MobileSyncWorkstationName);
        textMobileUserName.Text = PrefC.getString(PrefName.MobileUserName);
        textMobilePassword.Text = "";
        //not stored locally, and not pulled from web server
        DateTime lastRun = PrefC.getDateT(PrefName.MobileSyncDateTimeLastRun);
        if (lastRun.Year > 1880)
        {
            textDateTimeLastRun.Text = lastRun.ToShortDateString() + " " + lastRun.ToShortTimeString();
        }
         
    }

    //Web server is not contacted when loading this form.  That would be too slow.
    //CreateAppointments(5);
    private void butCurrentWorkstation_Click(Object sender, EventArgs e) throws Exception {
        textMobileSynchWorkStation.Text = System.Environment.MachineName.ToUpper();
    }

    private void butSave_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        if (!savePrefs())
        {
            Cursor = Cursors.Default;
            return ;
        }
         
        Cursor = Cursors.Default;
        MsgBox.show(this,"Done");
    }

    /**
    * Returns false if validation failed.  This also makes sure the web service exists, the customer is paid, and the registration key is correct.
    */
    private boolean savePrefs() throws Exception {
        //validation
        if (!StringSupport.equals(textSynchMinutes.errorProvider1.GetError(textSynchMinutes), "") || !StringSupport.equals(textDateBefore.errorProvider1.GetError(textDateBefore), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return false;
        }
         
        //yes, workstation is allowed to be blank.  That's one way for user to turn off auto synch.
        //if(textMobileSynchWorkStation.Text=="") {
        //	MsgBox.Show(this,"WorkStation cannot be empty");
        //	return false;
        //}
        // the text field is read because the keyed in values have not been saved yet
        //if(textMobileSyncServerURL.Text.Contains("192.168.0.196") || textMobileSyncServerURL.Text.Contains("localhost")) {
        if (textMobileSyncServerURL.Text.Contains("10.10.1.196") || textMobileSyncServerURL.Text.Contains("localhost"))
        {
            ignoreCertificateErrors();
        }
         
        // done so that TestWebServiceExists() does not thow an error.
        // if this is not done then an old non-functional url prevents any new url from being saved.
        Prefs.UpdateString(PrefName.MobileSyncServerURL, textMobileSyncServerURL.Text);
        if (!testWebServiceExists())
        {
            MsgBox.show(this,"Web service not found.");
            return false;
        }
         
        if (mb.getCustomerNum(PrefC.getString(PrefName.RegistrationKey)) == 0)
        {
            MsgBox.show(this,"Registration key is incorrect.");
            return false;
        }
         
        if (!verifyPaidCustomer())
        {
            return false;
        }
         
        //Minimum 10 char.  Must contain uppercase, lowercase, numbers, and symbols. Valid symbols are: !@#$%^&+=
        //The set of symbols checked was far too small, not even including periods, commas, and parentheses.
        //So I rewrote it all.  New error messages say exactly what's wrong with it.
        if (!StringSupport.equals(textMobileUserName.Text, ""))
        {
            //allowed to be blank
            if (textMobileUserName.Text.Length < 10)
            {
                MsgBox.show(this,"User Name must be at least 10 characters long.");
                return false;
            }
             
            if (!Regex.IsMatch(textMobileUserName.Text, "[A-Z]+"))
            {
                MsgBox.show(this,"User Name must contain an uppercase letter.");
                return false;
            }
             
            if (!Regex.IsMatch(textMobileUserName.Text, "[a-z]+"))
            {
                MsgBox.show(this,"User Name must contain an lowercase letter.");
                return false;
            }
             
            if (!Regex.IsMatch(textMobileUserName.Text, "[0-9]+"))
            {
                MsgBox.show(this,"User Name must contain a number.");
                return false;
            }
             
            if (!Regex.IsMatch(textMobileUserName.Text, "[^0-9a-zA-Z]+"))
            {
                //absolutely anything except number, lower or upper.
                MsgBox.show(this,"User Name must contain punctuation or symbols.");
                return false;
            }
             
        }
         
        if (StringSupport.equals(textDateBefore.Text, ""))
        {
            //default to one year if empty
            textDateBefore.Text = DateTime.Today.AddYears(-1).ToShortDateString();
        }
         
        //not going to bother informing user.  They can see it.
        //save to db------------------------------------------------------------------------------------
        //blank entry allowed
        //blank
        if (Prefs.UpdateString(PrefName.MobileSyncServerURL, textMobileSyncServerURL.Text) | Prefs.UpdateInt(PrefName.MobileSyncIntervalMinutes, PIn.Int(textSynchMinutes.Text)) | Prefs.UpdateString(PrefName.MobileExcludeApptsBeforeDate, POut.Date(PIn.Date(textDateBefore.Text), false)) | Prefs.UpdateString(PrefName.MobileSyncWorkstationName, textMobileSynchWorkStation.Text) | Prefs.UpdateString(PrefName.MobileUserName, textMobileUserName.Text))
        {
            changed = true;
            Prefs.refreshCache();
        }
         
        //Username and password-----------------------------------------------------------------------------
        mb.SetMobileWebUserPassword(PrefC.getString(PrefName.RegistrationKey), textMobileUserName.Text.Trim(), textMobilePassword.Text.Trim());
        return true;
    }

    /**
    * Uploads Preferences to the Patient Portal /Mobile Web.
    */
    public static void uploadPreference(PrefName prefname) throws Exception {
        if (StringSupport.equals(PrefC.getString(PrefName.RegistrationKey), ""))
        {
            return ;
        }
         
        try
        {
            //Prevents a bug when using the trial version with no registration key.  Practice edit, OK, was giving error.
            if (testWebServiceExists())
            {
                Prefm prefm = Prefms.GetPrefm(prefname.ToString());
                mb.setPreference(PrefC.getString(PrefName.RegistrationKey),prefm);
            }
             
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    //may not show if called from a thread but that does not matter - the failing of this method should not stop the  the code from proceeding.
    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!savePrefs())
        {
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete all your data from our server?  This happens automatically before a full synch."))
        {
            return ;
        }
         
        mb.deleteAllRecords(PrefC.getString(PrefName.RegistrationKey));
        MsgBox.show(this,"Done");
    }

    private void butFullSync_Click(Object sender, EventArgs e) throws Exception {
        if (!savePrefs())
        {
            return ;
        }
         
        if (IsSynching)
        {
            MsgBox.show(this,"A Synch is in progress at the moment. Please try again later.");
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"This will be time consuming. Continue anyway?"))
        {
            return ;
        }
         
        //for full synch, delete all records then repopulate.
        mb.deleteAllRecords(PrefC.getString(PrefName.RegistrationKey));
        ShowProgressForm(DateTime.MinValue);
    }

    private void butSync_Click(Object sender, EventArgs e) throws Exception {
        if (!savePrefs())
        {
            return ;
        }
         
        if (IsSynching)
        {
            MsgBox.show(this,"A Synch is in progress at the moment. Please try again later.");
            return ;
        }
         
        if (PrefC.getDate(PrefName.MobileExcludeApptsBeforeDate).Year < 1880)
        {
            MsgBox.show(this,"Full synch has never been run before.");
            return ;
        }
         
        DateTime changedSince = PrefC.getDateT(PrefName.MobileSyncDateTimeLastRun);
        showProgressForm(changedSince);
    }

    private void showProgressForm(DateTime changedSince) throws Exception {
        if (checkTroubleshooting.Checked)
        {
            IsTroubleshootMode = true;
        }
        else
        {
            IsTroubleshootMode = false;
        } 
        DateTime timeSynchStarted = MiscData.getNowDateTime();
        FormP = new FormProgress();
        FormP.MaxVal = 100;
        //to keep the form from closing until the real MaxVal is set.
        FormP.NumberMultiplication = 1;
        FormP.DisplayText = "Preparing records for upload.";
        FormP.NumberFormat = "F0";
        ThreadStart uploadDelegate;
        Thread workerThread = new Thread(uploadDelegate);
        workerThread.Start();
        //display the progress dialog to the user:
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.Cancel)
        {
            workerThread.Abort();
        }
         
        changed = true;
        textDateTimeLastRun.Text = PrefC.getDateT(PrefName.MobileSyncDateTimeLastRun).ToShortDateString() + " " + PrefC.getDateT(PrefName.MobileSyncDateTimeLastRun).ToShortTimeString();
    }

    /**
    * This is the function that the worker thread uses to actually perform the upload.  Can also call this method in the ordinary way if the data to be transferred is small.  The timeSynchStarted must be passed in to ensure that no records are skipped due to small time differences.
    */
    private static void uploadWorker(DateTime changedSince, DateTime timeSynchStarted) throws Exception {
        int totalCount = 100;
        try
        {
            //Dennis: try catch may not work: Does not work in threads, not sure about this. Note that all methods inside this try catch block are without exception handling. This is done on purpose so that when an exception does occur it does not update PrefName.MobileSyncDateTimeLastRun
            //The handling of PrefName.MobileSynchNewTables79 should never be removed in future versions
            DateTime changedProv = changedSince;
            DateTime changedDeleted = changedSince;
            DateTime changedPat = changedSince;
            DateTime changedStatement = changedSince;
            DateTime changedDocument = changedSince;
            DateTime changedRecall = changedSince;
            if (!PrefC.getBoolSilent(PrefName.MobileSynchNewTables79Done,false))
            {
                changedProv = DateTime.MinValue;
                changedDeleted = DateTime.MinValue;
            }
             
            if (!PrefC.getBoolSilent(PrefName.MobileSynchNewTables112Done,false))
            {
                changedPat = DateTime.MinValue;
                changedStatement = DateTime.MinValue;
                changedDocument = DateTime.MinValue;
            }
             
            if (!PrefC.getBoolSilent(PrefName.MobileSynchNewTables121Done,false))
            {
                changedRecall = DateTime.MinValue;
                uploadPreference(PrefName.PracticeTitle);
            }
             
            //done again because the previous upload did not include the prefnum
            boolean synchDelPat = true;
            if (PrefC.getDateT(PrefName.MobileSyncDateTimeLastRun).Hour == timeSynchStarted.Hour)
            {
                synchDelPat = false;
            }
             
            // synching delPatNumList is timeconsuming (15 seconds) for a dental office with around 5000 patients and it's mostly the same records that have to be deleted every time a synch happens. So it's done only once hourly.
            //MobileWeb
            List<long> patNumList = Patientms.getChangedSincePatNums(changedPat);
            List<long> aptNumList = Appointmentms.getChangedSinceAptNums(changedSince,PrefC.getDate(PrefName.MobileExcludeApptsBeforeDate));
            List<long> rxNumList = RxPatms.getChangedSinceRxNums(changedSince);
            List<long> provNumList = Providerms.getChangedSinceProvNums(changedProv);
            List<long> pharNumList = Pharmacyms.getChangedSincePharmacyNums(changedSince);
            List<long> allergyDefNumList = AllergyDefms.getChangedSinceAllergyDefNums(changedSince);
            List<long> allergyNumList = Allergyms.getChangedSinceAllergyNums(changedSince);
            //exclusively Patient Portal
            /*
            				List<long> eligibleForUploadPatNumList=Patientms.GetPatNumsEligibleForSynch();
            				List<long> labPanelNumList=LabPanelms.GetChangedSinceLabPanelNums(changedSince,eligibleForUploadPatNumList);
            				List<long> labResultNumList=LabResultms.GetChangedSinceLabResultNums(changedSince);
            				List<long> medicationNumList=Medicationms.GetChangedSinceMedicationNums(changedSince);
            				List<long> medicationPatNumList=MedicationPatms.GetChangedSinceMedicationPatNums(changedSince,eligibleForUploadPatNumList);
            				List<long> diseaseDefNumList=DiseaseDefms.GetChangedSinceDiseaseDefNums(changedSince);
            				List<long> diseaseNumList=Diseasems.GetChangedSinceDiseaseNums(changedSince,eligibleForUploadPatNumList);
            				List<long> icd9NumList=ICD9ms.GetChangedSinceICD9Nums(changedSince);
            				List<long> statementNumList=Statementms.GetChangedSinceStatementNums(changedStatement,eligibleForUploadPatNumList,statementLimitPerPatient);
            				List<long> documentNumList=Documentms.GetChangedSinceDocumentNums(changedDocument,statementNumList);
            				List<long> recallNumList=Recallms.GetChangedSinceRecallNums(changedRecall);*/
            List<long> delPatNumList = Patientms.getPatNumsForDeletion();
            //List<DeletedObject> dO=DeletedObjects.GetDeletedSince(changedDeleted);dennis: delete this line later
            List<long> deletedObjectNumList = DeletedObjects.getChangedSinceDeletedObjectNums(changedDeleted);
            //to delete appointments from mobile
            //+labPanelNumList.Count+labResultNumList.Count+medicationNumList.Count+medicationPatNumList.Count
            //+allergyDefNumList.Count//+allergyNumList.Count+diseaseDefNumList.Count+diseaseNumList.Count+icd9NumList.Count
            //+statementNumList.Count+documentNumList.Count+recallNumList.Count
            totalCount = patNumList.Count + aptNumList.Count + rxNumList.Count + provNumList.Count + pharNumList.Count + deletedObjectNumList.Count;
            if (synchDelPat)
            {
                totalCount += delPatNumList.Count;
            }
             
            double currentVal = 0;
            if (Application.OpenForms["FormProgress"] != null)
            {
                FormP.Invoke(new PassProgressDelegate() 
                  { 
                    // without this line the following error is thrown: "Invoke or BeginInvoke cannot be called on a control until the window handle has been created." or a null pointer exception is thrown when an automatic synch is done by the system.
                    public System.Void invoke(System.Double newCurVal, System.String newDisplayText, System.Double newMaxVal, System.String errorMessage) throws Exception {
                        passProgressToDialog(newCurVal, newDisplayText, newMaxVal, errorMessage);
                    }

                    public List<PassProgressDelegate> getInvocationList() throws Exception {
                        List<PassProgressDelegate> ret = new ArrayList<PassProgressDelegate>();
                        ret.add(this);
                        return ret;
                    }
                
                  }, new Object[]{ currentVal, "?currentVal of ?maxVal records uploaded", totalCount, "" });
            }
             
            IsSynching = true;
            RefSupport<double> refVar___0 = new RefSupport<double>(currentVal);
            SynchGeneric(patNumList, SynchEntity.patient, totalCount, refVar___0);
            currentVal = refVar___0.getValue();
            RefSupport<double> refVar___1 = new RefSupport<double>(currentVal);
            SynchGeneric(aptNumList, SynchEntity.appointment, totalCount, refVar___1);
            currentVal = refVar___1.getValue();
            RefSupport<double> refVar___2 = new RefSupport<double>(currentVal);
            SynchGeneric(rxNumList, SynchEntity.prescription, totalCount, refVar___2);
            currentVal = refVar___2.getValue();
            RefSupport<double> refVar___3 = new RefSupport<double>(currentVal);
            SynchGeneric(provNumList, SynchEntity.provider, totalCount, refVar___3);
            currentVal = refVar___3.getValue();
            RefSupport<double> refVar___4 = new RefSupport<double>(currentVal);
            SynchGeneric(pharNumList, SynchEntity.pharmacy, totalCount, refVar___4);
            currentVal = refVar___4.getValue();
            //pat portal
            /*
            				SynchGeneric(labPanelNumList,SynchEntity.labpanel,totalCount,ref currentVal);
            				SynchGeneric(labResultNumList,SynchEntity.labresult,totalCount,ref currentVal);
            				SynchGeneric(medicationNumList,SynchEntity.medication,totalCount,ref currentVal);
            				SynchGeneric(medicationPatNumList,SynchEntity.medicationpat,totalCount,ref currentVal);
            				SynchGeneric(allergyDefNumList,SynchEntity.allergydef,totalCount,ref currentVal);
            				SynchGeneric(allergyNumList,SynchEntity.allergy,totalCount,ref currentVal);
            				SynchGeneric(diseaseDefNumList,SynchEntity.diseasedef,totalCount,ref currentVal);
            				SynchGeneric(diseaseNumList,SynchEntity.disease,totalCount,ref currentVal);
            				SynchGeneric(icd9NumList,SynchEntity.icd9,totalCount,ref currentVal);
            				SynchGeneric(statementNumList,SynchEntity.statement,totalCount,ref currentVal);
            				SynchGeneric(documentNumList,SynchEntity.document,totalCount,ref currentVal);
            				SynchGeneric(recallNumList,SynchEntity.recall,totalCount,ref currentVal);*/
            if (synchDelPat)
            {
                RefSupport<double> refVar___5 = new RefSupport<double>(currentVal);
                SynchGeneric(delPatNumList, SynchEntity.patientdel, totalCount, refVar___5);
                currentVal = refVar___5.getValue();
            }
             
            //DeleteObjects(dO,totalCount,ref currentVal);// this has to be done at this end because objects may have been created and deleted between synchs. If this function is place above then the such a deleted object will not be deleted from the server.
            RefSupport<double> refVar___6 = new RefSupport<double>(currentVal);
            SynchGeneric(deletedObjectNumList, SynchEntity.deletedobject, totalCount, refVar___6);
            currentVal = refVar___6.getValue();
            // this has to be done at this end because objects may have been created and deleted between synchs. If this function is place above then the such a deleted object will not be deleted from the server.
            if (!PrefC.getBoolSilent(PrefName.MobileSynchNewTables79Done,true))
            {
                Prefs.updateBool(PrefName.MobileSynchNewTables79Done,true);
            }
             
            if (!PrefC.getBoolSilent(PrefName.MobileSynchNewTables112Done,true))
            {
                Prefs.updateBool(PrefName.MobileSynchNewTables112Done,true);
            }
             
            if (!PrefC.getBoolSilent(PrefName.MobileSynchNewTables121Done,true))
            {
                Prefs.updateBool(PrefName.MobileSynchNewTables121Done,true);
            }
             
            Prefs.updateDateT(PrefName.MobileSyncDateTimeLastRun,timeSynchStarted);
            IsSynching = false;
        }
        catch (Exception e)
        {
            IsSynching = false;
            // this will ensure that the synch can start again. If this variable remains true due to an exception then a synch will never take place automatically.
            if (Application.OpenForms["FormProgress"] != null)
            {
                FormP.Invoke(new PassProgressDelegate() 
                  { 
                    // without this line the following error is thrown: "Invoke or BeginInvoke cannot be called on a control until the window handle has been created." or a null pointer exception is thrown when an automatic synch is done by the system.
                    public System.Void invoke(System.Double newCurVal, System.String newDisplayText, System.Double newMaxVal, System.String errorMessage) throws Exception {
                        passProgressToDialog(newCurVal, newDisplayText, newMaxVal, errorMessage);
                    }

                    public List<PassProgressDelegate> getInvocationList() throws Exception {
                        List<PassProgressDelegate> ret = new ArrayList<PassProgressDelegate>();
                        ret.add(this);
                        return ret;
                    }
                
                  }, new Object[]{ 0, "?currentVal of ?maxVal records uploaded", totalCount, e.Message });
            }
             
        }
    
    }

    /**
    * a general function to reduce the amount of code for uploading
    */
    private static void synchGeneric(List<long> PKNumList, SynchEntity entity, double totalCount, RefSupport<double> currentVal) throws Exception {
        //Dennis: a try catch block here has been avoid on purpose.
        List<long> BlockPKNumList = null;
        int localBatchSize = BatchSize;
        if (IsTroubleshootMode)
        {
            localBatchSize = 1;
        }
         
        String AtoZpath = ImageStore.getPreferredAtoZpath();
        for (int start = 0;start < PKNumList.Count;start += localBatchSize)
        {
            if ((start + localBatchSize) > PKNumList.Count)
            {
                localBatchSize = PKNumList.Count - start;
            }
             
            try
            {
                BlockPKNumList = PKNumList.GetRange(start, localBatchSize);
                switch(entity)
                {
                    case patient: 
                        List<Patientm> changedPatientmList = Patientms.GetMultPats(BlockPKNumList);
                        mb.SynchPatients(PrefC.getString(PrefName.RegistrationKey), changedPatientmList.ToArray());
                        break;
                    case appointment: 
                        List<Appointmentm> changedAppointmentmList = Appointmentms.GetMultApts(BlockPKNumList);
                        mb.SynchAppointments(PrefC.getString(PrefName.RegistrationKey), changedAppointmentmList.ToArray());
                        break;
                    case prescription: 
                        List<RxPatm> changedRxList = RxPatms.GetMultRxPats(BlockPKNumList);
                        mb.SynchPrescriptions(PrefC.getString(PrefName.RegistrationKey), changedRxList.ToArray());
                        break;
                    case provider: 
                        List<Providerm> changedProvList = Providerms.GetMultProviderms(BlockPKNumList);
                        mb.SynchProviders(PrefC.getString(PrefName.RegistrationKey), changedProvList.ToArray());
                        break;
                    case pharmacy: 
                        List<Pharmacym> changedPharmacyList = Pharmacyms.GetMultPharmacyms(BlockPKNumList);
                        mb.SynchPharmacies(PrefC.getString(PrefName.RegistrationKey), changedPharmacyList.ToArray());
                        break;
                    case labpanel: 
                        List<LabPanelm> ChangedLabPanelList = LabPanelms.GetMultLabPanelms(BlockPKNumList);
                        mb.SynchLabPanels(PrefC.getString(PrefName.RegistrationKey), ChangedLabPanelList.ToArray());
                        break;
                    case labresult: 
                        List<LabResultm> ChangedLabResultList = LabResultms.GetMultLabResultms(BlockPKNumList);
                        mb.SynchLabResults(PrefC.getString(PrefName.RegistrationKey), ChangedLabResultList.ToArray());
                        break;
                    case medication: 
                        List<Medicationm> ChangedMedicationList = Medicationms.GetMultMedicationms(BlockPKNumList);
                        mb.SynchMedications(PrefC.getString(PrefName.RegistrationKey), ChangedMedicationList.ToArray());
                        break;
                    case medicationpat: 
                        List<MedicationPatm> ChangedMedicationPatList = MedicationPatms.GetMultMedicationPatms(BlockPKNumList);
                        mb.SynchMedicationPats(PrefC.getString(PrefName.RegistrationKey), ChangedMedicationPatList.ToArray());
                        break;
                    case allergy: 
                        List<Allergym> ChangedAllergyList = Allergyms.GetMultAllergyms(BlockPKNumList);
                        mb.SynchAllergies(PrefC.getString(PrefName.RegistrationKey), ChangedAllergyList.ToArray());
                        break;
                    case allergydef: 
                        List<AllergyDefm> ChangedAllergyDefList = AllergyDefms.GetMultAllergyDefms(BlockPKNumList);
                        mb.SynchAllergyDefs(PrefC.getString(PrefName.RegistrationKey), ChangedAllergyDefList.ToArray());
                        break;
                    case disease: 
                        List<Diseasem> ChangedDiseaseList = Diseasems.GetMultDiseasems(BlockPKNumList);
                        mb.SynchDiseases(PrefC.getString(PrefName.RegistrationKey), ChangedDiseaseList.ToArray());
                        break;
                    case diseasedef: 
                        List<DiseaseDefm> ChangedDiseaseDefList = DiseaseDefms.GetMultDiseaseDefms(BlockPKNumList);
                        mb.SynchDiseaseDefs(PrefC.getString(PrefName.RegistrationKey), ChangedDiseaseDefList.ToArray());
                        break;
                    case icd9: 
                        List<ICD9m> ChangedICD9List = ICD9ms.GetMultICD9ms(BlockPKNumList);
                        mb.SynchICD9s(PrefC.getString(PrefName.RegistrationKey), ChangedICD9List.ToArray());
                        break;
                    case statement: 
                        List<Statementm> ChangedStatementList = Statementms.GetMultStatementms(BlockPKNumList);
                        mb.SynchStatements(PrefC.getString(PrefName.RegistrationKey), ChangedStatementList.ToArray());
                        break;
                    case document: 
                        List<Documentm> ChangedDocumentList = Documentms.GetMultDocumentms(BlockPKNumList, AtoZpath);
                        mb.SynchDocuments(PrefC.getString(PrefName.RegistrationKey), ChangedDocumentList.ToArray());
                        break;
                    case recall: 
                        List<Recallm> ChangedRecallList = Recallms.GetMultRecallms(BlockPKNumList);
                        mb.SynchRecalls(PrefC.getString(PrefName.RegistrationKey), ChangedRecallList.ToArray());
                        break;
                    case deletedobject: 
                        List<DeletedObject> ChangedDeleteObjectList = DeletedObjects.GetMultDeletedObjects(BlockPKNumList);
                        mb.DeleteObjects(PrefC.getString(PrefName.RegistrationKey), ChangedDeleteObjectList.ToArray());
                        break;
                    case patientdel: 
                        mb.DeletePatientsRecords(PrefC.getString(PrefName.RegistrationKey), BlockPKNumList.ToArray());
                        break;
                
                }
                //progressIndicator.CurrentVal+=LocalBatchSize;//not allowed
                currentVal.setValue(currentVal.getValue() + localBatchSize);
                if (Application.OpenForms["FormProgress"] != null)
                {
                    FormP.Invoke(new PassProgressDelegate() 
                      { 
                        // without this line the following error is thrown: "Invoke or BeginInvoke cannot be called on a control until the window handle has been created." or a null pointer exception is thrown when an automatic synch is done by the system.
                        public System.Void invoke(System.Double newCurVal, System.String newDisplayText, System.Double newMaxVal, System.String errorMessage) throws Exception {
                            passProgressToDialog(newCurVal, newDisplayText, newMaxVal, errorMessage);
                        }

                        public List<PassProgressDelegate> getInvocationList() throws Exception {
                            List<PassProgressDelegate> ret = new ArrayList<PassProgressDelegate>();
                            ret.add(this);
                            return ret;
                        }
                    
                      }, new Object[]{ currentVal.getValue(), "?currentVal of ?maxVal records uploaded", totalCount, "" });
                }
                 
            }
            catch (Exception e)
            {
                if (IsTroubleshootMode)
                {
                    String errorMessage = entity + " with Primary Key = " + BlockPKNumList.First() + " failed to synch. " + "\n" + e.Message;
                    throw new Exception(errorMessage);
                }
                else
                {
                    throw e;
                } 
            }
        
        }
    }

    //for loop ends here
    /**
    * This method gets invoked from the worker thread.
    */
    private static void passProgressToDialog(double currentVal, String displayText, double maxVal, String errorMessage) throws Exception {
        FormP.CurrentVal = currentVal;
        FormP.DisplayText = displayText;
        FormP.MaxVal = maxVal;
        FormP.ErrorMessage = errorMessage;
    }

    /*
    		private static void DeleteObjects(List<DeletedObject> dO,double totalCount,ref double currentVal) {
    			int LocalBatchSize=BatchSize;
    			if(IsTroubleshootMode) {
    				LocalBatchSize=1;
    			}
    			for(int start=0;start<dO.Count;start+=LocalBatchSize) {
    				try {
    				if((start+LocalBatchSize)>dO.Count) {
    					mb.DeleteObjects(PrefC.GetString(PrefName.RegistrationKey),dO.ToArray()); //dennis check this - why is it not done in batches.
    					LocalBatchSize=dO.Count-start;
    				}
    				currentVal+=BatchSize;
    				if(Application.OpenForms["FormProgress"]!=null) {// without this line the following error is thrown: "Invoke or BeginInvoke cannot be called on a control until the window handle has been created." or a null pointer exception is thrown when an automatic synch is done by the system.
    					FormP.Invoke(new PassProgressDelegate(PassProgressToDialog),
    						new object[] {currentVal,"?currentVal of ?maxVal records uploaded",totalCount,"" });
    				}
    								}
    				catch(Exception e) {
    					if(IsTroubleshootMode) {
    						//string errorMessage="DeleteObjects with Primary Key = "+BlockPKNumList.First() + " failed to synch. " +  "\n" + e.Message;
    						//throw new Exception(errorMessage);
    					}
    					else {
    						throw e;
    					}
    				}
    			}//for loop ends here
    			
    		}
    		*/
    /**
    * An empty method to test if the webservice is up and running. This was made with the intention of testing the correctness of the webservice URL. If an incorrect webservice URL is used in a background thread the exception cannot be handled easily to a point where even a correct URL cannot be keyed in by the user. Because an exception in a background thread closes the Form which spawned it.
    */
    private static boolean testWebServiceExists() throws Exception {
        try
        {
            mb.setUrl(PrefC.getString(PrefName.MobileSyncServerURL));
            if (mb.serviceExists())
            {
                return true;
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            return false;
        }

        return false;
    }

    private boolean verifyPaidCustomer() throws Exception {
        //if(textMobileSyncServerURL.Text.Contains("192.168.0.196") || textMobileSyncServerURL.Text.Contains("localhost")) {
        if (textMobileSyncServerURL.Text.Contains("10.10.1.196") || textMobileSyncServerURL.Text.Contains("localhost"))
        {
            ignoreCertificateErrors();
        }
         
        boolean isPaidCustomer = mb.isPaidCustomer(PrefC.getString(PrefName.RegistrationKey));
        if (!isPaidCustomer)
        {
            textSynchMinutes.Text = "0";
            Prefs.updateInt(PrefName.MobileSyncIntervalMinutes,0);
            changed = true;
            MsgBox.show(this,"This feature requires a separate monthly payment.  Please call customer support.");
            return false;
        }
         
        return true;
    }

    /**
    * Called from FormOpenDental and from FormEhrOnlineAccess.  doForce is set to false to follow regular synching interval.
    */
    public static void synchFromMain(boolean doForce) throws Exception {
        if (Application.OpenForms["FormMobile"] != null)
        {
            return ;
        }
         
        //tested.  This prevents main synch whenever this form is open.
        if (IsSynching)
        {
            return ;
        }
         
        DateTime timeSynchStarted = MiscData.getNowDateTime();
        if (!doForce)
        {
            //if doForce, we skip checking the interval
            if (timeSynchStarted < PrefC.getDateT(PrefName.MobileSyncDateTimeLastRun).AddMinutes(PrefC.getInt(PrefName.MobileSyncIntervalMinutes)))
            {
                return ;
            }
             
        }
         
        //if(PrefC.GetString(PrefName.MobileSyncServerURL).Contains("192.168.0.196") || PrefC.GetString(PrefName.MobileSyncServerURL).Contains("localhost")) {
        if (PrefC.getString(PrefName.MobileSyncServerURL).Contains("10.10.1.196") || PrefC.getString(PrefName.MobileSyncServerURL).Contains("localhost"))
        {
            ignoreCertificateErrors();
        }
         
        if (!testWebServiceExists())
        {
            if (!doForce)
            {
                //if being used from FormOpenDental as part of timer
                if (IsServerAvail)
                {
                    //this will only happen the first time to prevent multiple windows.
                    IsServerAvail = false;
                    DialogResult res = MessageBox.Show("Mobile synch server not available.  Synch failed.  Turn off synch?", "", MessageBoxButtons.YesNo);
                    if (res == DialogResult.Yes)
                    {
                        Prefs.updateInt(PrefName.MobileSyncIntervalMinutes,0);
                    }
                     
                }
                 
            }
             
            return ;
        }
        else
        {
            IsServerAvail = true;
        } 
        DateTime changedSince = PrefC.getDateT(PrefName.MobileSyncDateTimeLastRun);
        ThreadStart uploadDelegate;
        Thread workerThread = new Thread(uploadDelegate);
        workerThread.Start();
    }

    /**
    * This allows the code to continue by not throwing an exception even if there is a problem with the security certificate.
    */
    private static void ignoreCertificateErrors() throws Exception {
        System.Net.ServicePointManager.ServerCertificateValidationCallback += ;
    }

    /**
    * For testing only
    */
    private static void createPatients(int PatientCount) throws Exception {
        for (int i = 0;i < PatientCount;i++)
        {
            Patient newPat = new Patient();
            newPat.LName = "Mathew" + i;
            newPat.FName = "Dennis" + i;
            newPat.Address = "Address Line 1.Address Line 1___" + i;
            newPat.Address2 = "Address Line 2. Address Line 2__" + i;
            newPat.AddrNote = "Lives off in far off Siberia Lives off in far off Siberia" + i;
            newPat.AdmitDate = (new DateTime(1985, 3, 3)).AddDays(i);
            newPat.ApptModNote = "Flies from Siberia on specially chartered flight piloted by goblins:)" + i;
            newPat.AskToArriveEarly = 1555;
            newPat.BillingType = 3;
            newPat.ChartNumber = "111111" + i;
            newPat.City = "NL";
            newPat.ClinicNum = i;
            newPat.CreditType = "A";
            newPat.DateFirstVisit = (new DateTime(1985, 3, 3)).AddDays(i);
            newPat.Email = "dennis.mathew________________seb@siberiacrawlmail.com";
            newPat.HmPhone = "416-222-5678";
            newPat.WkPhone = "416-222-5678";
            newPat.Zip = "M3L 2L9";
            newPat.WirelessPhone = "416-222-5678";
            newPat.Birthdate = (new DateTime(1970, 3, 3)).AddDays(i);
            Patients.insert(newPat,false);
            //set Guarantor field the same as PatNum
            Patient patOld = newPat.copy();
            newPat.Guarantor = newPat.PatNum;
            Patients.update(newPat,patOld);
        }
    }

    /**
    * For testing only
    */
    private static void createAppointments(int AppointmentCount) throws Exception {
        long[] patNumArray = Patients.getAllPatNums();
        DateTime appdate = DateTime.Now;
        for (int i = 0;i < patNumArray.Length;i++)
        {
            appdate = appdate.AddMinutes(20);
            for (int j = 0;j < AppointmentCount;j++)
            {
                Appointment apt = new Appointment();
                appdate = appdate.AddMinutes(20);
                apt.PatNum = patNumArray[i];
                apt.DateTimeArrived = appdate;
                apt.DateTimeAskedToArrive = appdate;
                apt.DateTimeDismissed = appdate;
                apt.DateTimeSeated = appdate;
                apt.AptDateTime = appdate;
                apt.Note = "some notenote noten otenotenot enotenot enote" + j;
                apt.IsNewPatient = true;
                apt.ProvNum = 3;
                apt.AptStatus = ApptStatus.Scheduled;
                apt.AptDateTime = appdate;
                apt.Op = 2;
                apt.Pattern = "//XX//////";
                apt.ProcDescript = "4-BWX";
                apt.ProcsColored = "<span color=\"-16777216\">4-BWX</span>";
                Appointments.insert(apt);
            }
        }
    }

    /**
    * For testing only
    */
    private static void createPrescriptions(int PrescriptionCount) throws Exception {
        long[] patNumArray = Patients.getAllPatNums();
        for (int i = 0;i < patNumArray.Length;i++)
        {
            for (int j = 0;j < PrescriptionCount;j++)
            {
                RxPat rxpat = new RxPat();
                rxpat.Drug = "VicodinA VicodinB VicodinC" + j;
                rxpat.Disp = "50.50";
                rxpat.IsControlled = true;
                rxpat.PatNum = patNumArray[i];
                rxpat.RxDate = new DateTime(2010, 12, 1, 11, 0, 0);
                RxPats.insert(rxpat);
            }
        }
    }

    private static void createStatements(int StatementCount) throws Exception {
        long[] patNumArray = Patients.getAllPatNums();
        for (int i = 0;i < patNumArray.Length;i++)
        {
            for (int j = 0;j < StatementCount;j++)
            {
                Statement st = new Statement();
                st.DateSent = (new DateTime(2010, 12, 1, 11, 0, 0)).AddDays(1 + j);
                st.DocNum = i + j;
                st.PatNum = patNumArray[i];
                Statements.insert(st);
            }
        }
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

    private void formMobile_FormClosed(Object sender, FormClosedEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.textMobileSyncServerURL = new System.Windows.Forms.TextBox();
        this.labelMobileSynchURL = new System.Windows.Forms.Label();
        this.labelMinutesBetweenSynch = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.groupPreferences = new System.Windows.Forms.GroupBox();
        this.label8 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.textMobileUserName = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butCurrentWorkstation = new OpenDental.UI.Button();
        this.textMobilePassword = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.textMobileSynchWorkStation = new System.Windows.Forms.TextBox();
        this.textSynchMinutes = new OpenDental.ValidNumber();
        this.butSave = new OpenDental.UI.Button();
        this.textDateBefore = new OpenDental.ValidDate();
        this.textDateTimeLastRun = new System.Windows.Forms.Label();
        this.butFullSync = new OpenDental.UI.Button();
        this.butSync = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.service11 = new OpenDental.localhost.Service1();
        this.butDelete = new OpenDental.UI.Button();
        this.checkTroubleshooting = new System.Windows.Forms.CheckBox();
        this.groupPreferences.SuspendLayout();
        this.SuspendLayout();
        //
        // textMobileSyncServerURL
        //
        this.textMobileSyncServerURL.Location = new System.Drawing.Point(177, 19);
        this.textMobileSyncServerURL.Name = "textMobileSyncServerURL";
        this.textMobileSyncServerURL.Size = new System.Drawing.Size(445, 20);
        this.textMobileSyncServerURL.TabIndex = 75;
        //
        // labelMobileSynchURL
        //
        this.labelMobileSynchURL.Location = new System.Drawing.Point(6, 20);
        this.labelMobileSynchURL.Name = "labelMobileSynchURL";
        this.labelMobileSynchURL.Size = new System.Drawing.Size(169, 19);
        this.labelMobileSynchURL.TabIndex = 76;
        this.labelMobileSynchURL.Text = "Host Server Address";
        this.labelMobileSynchURL.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelMinutesBetweenSynch
        //
        this.labelMinutesBetweenSynch.Location = new System.Drawing.Point(6, 48);
        this.labelMinutesBetweenSynch.Name = "labelMinutesBetweenSynch";
        this.labelMinutesBetweenSynch.Size = new System.Drawing.Size(169, 19);
        this.labelMinutesBetweenSynch.TabIndex = 79;
        this.labelMinutesBetweenSynch.Text = "Minutes Between Synch";
        this.labelMinutesBetweenSynch.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(26, 239);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(167, 18);
        this.label3.TabIndex = 87;
        this.label3.Text = "Date/time of last sync";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(5, 76);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(170, 18);
        this.label2.TabIndex = 85;
        this.label2.Text = "Exclude Appointments Before";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupPreferences
        //
        this.groupPreferences.Controls.Add(this.label8);
        this.groupPreferences.Controls.Add(this.label7);
        this.groupPreferences.Controls.Add(this.textMobileUserName);
        this.groupPreferences.Controls.Add(this.label4);
        this.groupPreferences.Controls.Add(this.butCurrentWorkstation);
        this.groupPreferences.Controls.Add(this.textMobilePassword);
        this.groupPreferences.Controls.Add(this.label6);
        this.groupPreferences.Controls.Add(this.label5);
        this.groupPreferences.Controls.Add(this.textMobileSynchWorkStation);
        this.groupPreferences.Controls.Add(this.textSynchMinutes);
        this.groupPreferences.Controls.Add(this.label2);
        this.groupPreferences.Controls.Add(this.butSave);
        this.groupPreferences.Controls.Add(this.textDateBefore);
        this.groupPreferences.Controls.Add(this.labelMobileSynchURL);
        this.groupPreferences.Controls.Add(this.textMobileSyncServerURL);
        this.groupPreferences.Controls.Add(this.labelMinutesBetweenSynch);
        this.groupPreferences.Location = new System.Drawing.Point(18, 12);
        this.groupPreferences.Name = "groupPreferences";
        this.groupPreferences.Size = new System.Drawing.Size(665, 212);
        this.groupPreferences.TabIndex = 239;
        this.groupPreferences.TabStop = false;
        this.groupPreferences.Text = "Preferences";
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(8, 183);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(575, 19);
        this.label8.TabIndex = 246;
        this.label8.Text = "To change your password, enter a new one in the box and Save.  To keep the old pa" + "ssword, leave the box empty.";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(222, 48);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(343, 18);
        this.label7.TabIndex = 244;
        this.label7.Text = "Set to 0 to stop automatic Synchronization";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textMobileUserName
        //
        this.textMobileUserName.Location = new System.Drawing.Point(177, 131);
        this.textMobileUserName.Name = "textMobileUserName";
        this.textMobileUserName.Size = new System.Drawing.Size(247, 20);
        this.textMobileUserName.TabIndex = 242;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(5, 132);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(169, 19);
        this.label4.TabIndex = 243;
        this.label4.Text = "User Name";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butCurrentWorkstation
        //
        this.butCurrentWorkstation.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCurrentWorkstation.setAutosize(true);
        this.butCurrentWorkstation.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCurrentWorkstation.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCurrentWorkstation.setCornerRadius(4F);
        this.butCurrentWorkstation.Location = new System.Drawing.Point(430, 101);
        this.butCurrentWorkstation.Name = "butCurrentWorkstation";
        this.butCurrentWorkstation.Size = new System.Drawing.Size(115, 24);
        this.butCurrentWorkstation.TabIndex = 247;
        this.butCurrentWorkstation.Text = "Current Workstation";
        this.butCurrentWorkstation.Click += new System.EventHandler(this.butCurrentWorkstation_Click);
        //
        // textMobilePassword
        //
        this.textMobilePassword.Location = new System.Drawing.Point(177, 159);
        this.textMobilePassword.Name = "textMobilePassword";
        this.textMobilePassword.PasswordChar = '*';
        this.textMobilePassword.Size = new System.Drawing.Size(247, 20);
        this.textMobilePassword.TabIndex = 243;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(4, 105);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(170, 18);
        this.label6.TabIndex = 246;
        this.label6.Text = "Workstation for Synching";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(5, 160);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(169, 19);
        this.label5.TabIndex = 244;
        this.label5.Text = "Password";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMobileSynchWorkStation
        //
        this.textMobileSynchWorkStation.Location = new System.Drawing.Point(177, 103);
        this.textMobileSynchWorkStation.Name = "textMobileSynchWorkStation";
        this.textMobileSynchWorkStation.Size = new System.Drawing.Size(247, 20);
        this.textMobileSynchWorkStation.TabIndex = 245;
        //
        // textSynchMinutes
        //
        this.textSynchMinutes.Location = new System.Drawing.Point(177, 47);
        this.textSynchMinutes.setMaxVal(255);
        this.textSynchMinutes.setMinVal(0);
        this.textSynchMinutes.Name = "textSynchMinutes";
        this.textSynchMinutes.Size = new System.Drawing.Size(39, 20);
        this.textSynchMinutes.TabIndex = 241;
        //
        // butSave
        //
        this.butSave.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSave.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSave.setAutosize(true);
        this.butSave.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSave.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSave.setCornerRadius(4F);
        this.butSave.Location = new System.Drawing.Point(598, 182);
        this.butSave.Name = "butSave";
        this.butSave.Size = new System.Drawing.Size(61, 24);
        this.butSave.TabIndex = 240;
        this.butSave.Text = "Save";
        this.butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // textDateBefore
        //
        this.textDateBefore.Location = new System.Drawing.Point(177, 75);
        this.textDateBefore.Name = "textDateBefore";
        this.textDateBefore.Size = new System.Drawing.Size(100, 20);
        this.textDateBefore.TabIndex = 84;
        //
        // textDateTimeLastRun
        //
        this.textDateTimeLastRun.Location = new System.Drawing.Point(196, 239);
        this.textDateTimeLastRun.Name = "textDateTimeLastRun";
        this.textDateTimeLastRun.Size = new System.Drawing.Size(207, 18);
        this.textDateTimeLastRun.TabIndex = 243;
        this.textDateTimeLastRun.Text = "3/4/2011 4:15 PM";
        this.textDateTimeLastRun.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butFullSync
        //
        this.butFullSync.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butFullSync.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butFullSync.setAutosize(true);
        this.butFullSync.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFullSync.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFullSync.setCornerRadius(4F);
        this.butFullSync.Location = new System.Drawing.Point(269, 288);
        this.butFullSync.Name = "butFullSync";
        this.butFullSync.Size = new System.Drawing.Size(68, 24);
        this.butFullSync.TabIndex = 83;
        this.butFullSync.Text = "Full Synch";
        this.butFullSync.Click += new System.EventHandler(this.butFullSync_Click);
        //
        // butSync
        //
        this.butSync.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSync.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSync.setAutosize(true);
        this.butSync.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSync.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSync.setCornerRadius(4F);
        this.butSync.Location = new System.Drawing.Point(343, 288);
        this.butSync.Name = "butSync";
        this.butSync.Size = new System.Drawing.Size(68, 24);
        this.butSync.TabIndex = 82;
        this.butSync.Text = "Synch";
        this.butSync.Click += new System.EventHandler(this.butSync_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(611, 288);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 81;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // service11
        //
        this.service11.setUrl("http://localhost:3824/Service1.asmx");
        this.service11.setUseDefaultCredentials(true);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Location = new System.Drawing.Point(195, 288);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(68, 24);
        this.butDelete.TabIndex = 245;
        this.butDelete.Text = "Delete All";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // checkTroubleshooting
        //
        this.checkTroubleshooting.Location = new System.Drawing.Point(327, 239);
        this.checkTroubleshooting.Name = "checkTroubleshooting";
        this.checkTroubleshooting.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkTroubleshooting.Size = new System.Drawing.Size(184, 24);
        this.checkTroubleshooting.TabIndex = 246;
        this.checkTroubleshooting.Text = "Synch Troubleshooting Mode";
        this.checkTroubleshooting.UseVisualStyleBackColor = true;
        //
        // FormMobile
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(714, 325);
        this.Controls.Add(this.checkTroubleshooting);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textDateTimeLastRun);
        this.Controls.Add(this.groupPreferences);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.butFullSync);
        this.Controls.Add(this.butSync);
        this.Controls.Add(this.butClose);
        this.Name = "FormMobile";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Mobile Synch";
        this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.FormMobile_FormClosed);
        this.Load += new System.EventHandler(this.FormMobileSetup_Load);
        this.groupPreferences.ResumeLayout(false);
        this.groupPreferences.PerformLayout();
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.TextBox textMobileSyncServerURL = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelMobileSynchURL = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelMinutesBetweenSynch = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateBefore;
    private OpenDental.UI.Button butFullSync;
    private OpenDental.UI.Button butSync;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.GroupBox groupPreferences = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butSave;
    private OpenDental.ValidNumber textSynchMinutes;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textMobileUserName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textMobilePassword = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textMobileSynchWorkStation = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butCurrentWorkstation;
    private System.Windows.Forms.Label textDateTimeLastRun = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private OpenDental.localhost.Service1 service11;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.CheckBox checkTroubleshooting = new System.Windows.Forms.CheckBox();
}


