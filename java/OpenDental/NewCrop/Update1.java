//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.AccountRequest;
import OpenDental.NewCrop.CounselingMessageDetailResult;
import OpenDental.NewCrop.Credentials;
import OpenDental.NewCrop.DoseCheckCompletedEventArgs;
import OpenDental.NewCrop.DoseCheckCompletedEventHandler;
import OpenDental.NewCrop.DownloadDetailResult;
import OpenDental.NewCrop.DrugAllergyDetailResultV2;
import OpenDental.NewCrop.DrugAllergyInteractionV2CompletedEventArgs;
import OpenDental.NewCrop.DrugAllergyInteractionV2CompletedEventHandler;
import OpenDental.NewCrop.DrugDiseaseDetailResult;
import OpenDental.NewCrop.DrugDiseaseInteractionCompletedEventArgs;
import OpenDental.NewCrop.DrugDiseaseInteractionCompletedEventHandler;
import OpenDental.NewCrop.DrugFormularyDetailResult;
import OpenDental.NewCrop.DrugFormularyFavoriteDetailResult;
import OpenDental.NewCrop.DrugHistoryDetailResult;
import OpenDental.NewCrop.DrugSearchWithFormularyWithFavoritesV2CompletedEventArgs;
import OpenDental.NewCrop.DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler;
import OpenDental.NewCrop.DrugSearchWithFormularyWithFavoritesV3CompletedEventArgs;
import OpenDental.NewCrop.DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler;
import OpenDental.NewCrop.EligibilityDetailResult;
import OpenDental.NewCrop.EligibilityDetailResultV3;
import OpenDental.NewCrop.FormularyAlternativesWithDrugInfo2CompletedEventArgs;
import OpenDental.NewCrop.FormularyAlternativesWithDrugInfo2CompletedEventHandler;
import OpenDental.NewCrop.FormularyCoverageDetailResultV3;
import OpenDental.NewCrop.FormularyCoverageV3CompletedEventArgs;
import OpenDental.NewCrop.FormularyCoverageV3CompletedEventHandler;
import OpenDental.NewCrop.GenerateTestRenewalRequestCompletedEventArgs;
import OpenDental.NewCrop.GenerateTestRenewalRequestCompletedEventHandler;
import OpenDental.NewCrop.GetAccountStatusDetailCompletedEventArgs;
import OpenDental.NewCrop.GetAccountStatusDetailCompletedEventHandler;
import OpenDental.NewCrop.GetAllRenewalRequestsCompletedEventArgs;
import OpenDental.NewCrop.GetAllRenewalRequestsCompletedEventHandler;
import OpenDental.NewCrop.GetAllRenewalRequestsDetailV4CompletedEventArgs;
import OpenDental.NewCrop.GetAllRenewalRequestsDetailV4CompletedEventHandler;
import OpenDental.NewCrop.GetAllRenewalRequestsSummaryV4CompletedEventArgs;
import OpenDental.NewCrop.GetAllRenewalRequestsSummaryV4CompletedEventHandler;
import OpenDental.NewCrop.GetAllRenewalRequestsV2CompletedEventArgs;
import OpenDental.NewCrop.GetAllRenewalRequestsV2CompletedEventHandler;
import OpenDental.NewCrop.GetAllRenewalRequestsV3CompletedEventArgs;
import OpenDental.NewCrop.GetAllRenewalRequestsV3CompletedEventHandler;
import OpenDental.NewCrop.GetCompleteMedicationHistoryCompletedEventArgs;
import OpenDental.NewCrop.GetCompleteMedicationHistoryCompletedEventHandler;
import OpenDental.NewCrop.GetCounselingMessagesCompletedEventArgs;
import OpenDental.NewCrop.GetCounselingMessagesCompletedEventHandler;
import OpenDental.NewCrop.GetDailyScriptReportCompletedEventArgs;
import OpenDental.NewCrop.GetDailyScriptReportCompletedEventHandler;
import OpenDental.NewCrop.GetDailyScriptReportV2CompletedEventArgs;
import OpenDental.NewCrop.GetDailyScriptReportV2CompletedEventHandler;
import OpenDental.NewCrop.GetDailyScriptReportV3CompletedEventArgs;
import OpenDental.NewCrop.GetDailyScriptReportV3CompletedEventHandler;
import OpenDental.NewCrop.GetMeaningfulUsePatientEncounterInfoCompletedEventArgs;
import OpenDental.NewCrop.GetMeaningfulUsePatientEncounterInfoCompletedEventHandler;
import OpenDental.NewCrop.GetMostRecentDownloadUrlCompletedEventArgs;
import OpenDental.NewCrop.GetMostRecentDownloadUrlCompletedEventHandler;
import OpenDental.NewCrop.GetPatientAllergyHistory2CompletedEventArgs;
import OpenDental.NewCrop.GetPatientAllergyHistory2CompletedEventHandler;
import OpenDental.NewCrop.GetPatientAllergyHistoryV3CompletedEventArgs;
import OpenDental.NewCrop.GetPatientAllergyHistoryV3CompletedEventHandler;
import OpenDental.NewCrop.GetPatientFreeFormAllergyHistoryCompletedEventArgs;
import OpenDental.NewCrop.GetPatientFreeFormAllergyHistoryCompletedEventHandler;
import OpenDental.NewCrop.GetPatientFullMedicationHistory4CompletedEventArgs;
import OpenDental.NewCrop.GetPatientFullMedicationHistory4CompletedEventHandler;
import OpenDental.NewCrop.GetPatientFullMedicationHistory5CompletedEventArgs;
import OpenDental.NewCrop.GetPatientFullMedicationHistory5CompletedEventHandler;
import OpenDental.NewCrop.GetPatientFullMedicationHistory6CompletedEventArgs;
import OpenDental.NewCrop.GetPatientFullMedicationHistory6CompletedEventHandler;
import OpenDental.NewCrop.GetPBMDrugHistoryV2CompletedEventArgs;
import OpenDental.NewCrop.GetPBMDrugHistoryV2CompletedEventHandler;
import OpenDental.NewCrop.GetPBMEligibilityV2CompletedEventArgs;
import OpenDental.NewCrop.GetPBMEligibilityV2CompletedEventHandler;
import OpenDental.NewCrop.GetPBMEligibilityV3CompletedEventArgs;
import OpenDental.NewCrop.GetPBMEligibilityV3CompletedEventHandler;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusByPatientCompletedEventArgs;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusByPatientCompletedEventHandler;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusCompletedEventArgs;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusCompletedEventHandler;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusV2CompletedEventArgs;
import OpenDental.NewCrop.GetPrescriptionTransmissionStatusV2CompletedEventHandler;
import OpenDental.NewCrop.GetRenewalRequestDetailCompletedEventArgs;
import OpenDental.NewCrop.GetRenewalRequestDetailCompletedEventHandler;
import OpenDental.NewCrop.GetRenewalRequestDetailV4CompletedEventArgs;
import OpenDental.NewCrop.GetRenewalRequestDetailV4CompletedEventHandler;
import OpenDental.NewCrop.GetRenewalResponseStatusCompletedEventArgs;
import OpenDental.NewCrop.GetRenewalResponseStatusCompletedEventHandler;
import OpenDental.NewCrop.GetRenewalResponseTransmissionStatusCompletedEventArgs;
import OpenDental.NewCrop.GetRenewalResponseTransmissionStatusCompletedEventHandler;
import OpenDental.NewCrop.GetSideEffectsCompletedEventArgs;
import OpenDental.NewCrop.GetSideEffectsCompletedEventHandler;
import OpenDental.NewCrop.GetSubmittedMessageTransactionStatusCompletedEventArgs;
import OpenDental.NewCrop.GetSubmittedMessageTransactionStatusCompletedEventHandler;
import OpenDental.NewCrop.HealthplanDetailResult;
import OpenDental.NewCrop.HealthplanSearchV2CompletedEventArgs;
import OpenDental.NewCrop.HealthplanSearchV2CompletedEventHandler;
import OpenDental.NewCrop.MessageTransactionStatusResult;
import OpenDental.NewCrop.PatientAllergyExtendedDetailResult;
import OpenDental.NewCrop.PatientDrugDetailResult4;
import OpenDental.NewCrop.PatientDrugDetailResult5;
import OpenDental.NewCrop.PatientFreeFormAllergyExtendedDetailResult;
import OpenDental.NewCrop.PatientInformationRequester;
import OpenDental.NewCrop.PatientRequest;
import OpenDental.NewCrop.PharmacyDetailResultV2;
import OpenDental.NewCrop.PharmacyDetailResultV4;
import OpenDental.NewCrop.PharmacySearchV3CompletedEventArgs;
import OpenDental.NewCrop.PharmacySearchV3CompletedEventHandler;
import OpenDental.NewCrop.PharmacySearchV4CompletedEventArgs;
import OpenDental.NewCrop.PharmacySearchV4CompletedEventHandler;
import OpenDental.NewCrop.PrescriptionHistoryRequest;
import OpenDental.NewCrop.PrescriptionTransmissionQueryType;
import OpenDental.NewCrop.ProcessRenewalRequestCompletedEventArgs;
import OpenDental.NewCrop.ProcessRenewalRequestCompletedEventHandler;
import OpenDental.NewCrop.RenewalDetailResult;
import OpenDental.NewCrop.RenewalDetailResultV4;
import OpenDental.NewCrop.RenewalListDetailResultV4;
import OpenDental.NewCrop.RenewalListSummaryResultV4;
import OpenDental.NewCrop.RenewalResponseDetailResult;
import OpenDental.NewCrop.RenewalSummaryResult;
import OpenDental.NewCrop.RenewalSummaryResultV2;
import OpenDental.NewCrop.ReportPrescribingCountCompletedEventArgs;
import OpenDental.NewCrop.ReportPrescribingCountCompletedEventHandler;
import OpenDental.NewCrop.ResolveFailedPrescriptionTransmissionCompletedEventArgs;
import OpenDental.NewCrop.ResolveFailedPrescriptionTransmissionCompletedEventHandler;
import OpenDental.NewCrop.Result;
import OpenDental.NewCrop.SendMissingHealthplanInformationCompletedEventArgs;
import OpenDental.NewCrop.SendMissingHealthplanInformationCompletedEventHandler;
import OpenDental.NewCrop.TransmissionSummaryResult;
import OpenDental.Properties.Settings;


//------------------------------------------------------------------------------// <auto-generated>//     This code was generated by a tool.//     Runtime Version:4.0.30319.296////     Changes to this file may cause incorrect behavior and will be lost if//     the code is regenerated.// </auto-generated>//------------------------------------------------------------------------------//// This source code was auto-generated by Microsoft.VSDesigner, Version 4.0.30319.296.///**
* 
*/
public class Update1  extends System.Web.Services.Protocols.SoapHttpClientProtocol 
{

    private System.Threading.SendOrPostCallback GetDailyScriptReportOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetDailyScriptReportV2OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetDailyScriptReportV3OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetCompleteMedicationHistoryOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPatientFullMedicationHistory4OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPatientFullMedicationHistory5OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPatientFullMedicationHistory6OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPatientAllergyHistory2OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPatientAllergyHistoryV3OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPatientFreeFormAllergyHistoryOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPrescriptionTransmissionStatusOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPrescriptionTransmissionStatusByPatientOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GenerateTestRenewalRequestOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetAllRenewalRequestsOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetAllRenewalRequestsV2OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetAllRenewalRequestsV3OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetAllRenewalRequestsDetailV4OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetAllRenewalRequestsSummaryV4OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetRenewalRequestDetailOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetRenewalRequestDetailV4OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback ProcessRenewalRequestOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetRenewalResponseTransmissionStatusOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetRenewalResponseStatusOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback FormularyAlternativesWithDrugInfo2OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback SendMissingHealthplanInformationOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPBMDrugHistoryV2OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetMostRecentDownloadUrlOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback DrugDiseaseInteractionOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback PharmacySearchV3OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback PharmacySearchV4OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback DrugSearchWithFormularyWithFavoritesV2OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback DrugSearchWithFormularyWithFavoritesV3OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback HealthplanSearchV2OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback FormularyCoverageV3OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback ReportPrescribingCountOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetCounselingMessagesOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetSideEffectsOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetAccountStatusDetailOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPBMEligibilityV2OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPBMEligibilityV3OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetPrescriptionTransmissionStatusV2OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetSubmittedMessageTransactionStatusOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback DrugAllergyInteractionV2OperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback ResolveFailedPrescriptionTransmissionOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback GetMeaningfulUsePatientEncounterInfoOperationCompleted = new System.Threading.SendOrPostCallback();
    private System.Threading.SendOrPostCallback DoseCheckOperationCompleted = new System.Threading.SendOrPostCallback();
    private boolean useDefaultCredentialsSetExplicitly = new boolean();
    /**
    * 
    */
    public Update1() throws Exception {
        this.setUrl(Settings.getDefault().getOpenDental_NewCrop_Update1());
        if ((this.isLocalFileSystemWebService(this.getUrl()) == true))
        {
            this.setUseDefaultCredentials(true);
            this.useDefaultCredentialsSetExplicitly = false;
        }
        else
        {
            this.useDefaultCredentialsSetExplicitly = true;
        } 
    }

    public String getUrl() throws Exception {
        return super.Url;
    }

    public void setUrl(String value) throws Exception {
        if ((((this.IsLocalFileSystemWebService(super.Url) == true) && (this.useDefaultCredentialsSetExplicitly == false)) && (this.isLocalFileSystemWebService(value) == false)))
        {
            super.UseDefaultCredentials = false;
        }
         
        super.Url = value;
    }

    public boolean getUseDefaultCredentials() throws Exception {
        return super.UseDefaultCredentials;
    }

    public void setUseDefaultCredentials(boolean value) throws Exception {
        super.UseDefaultCredentials = value;
        this.useDefaultCredentialsSetExplicitly = true;
    }

    /**
    * 
    */
    public GetDailyScriptReportCompletedEventHandler GetDailyScriptReportCompleted;
    /**
    * 
    */
    public GetDailyScriptReportV2CompletedEventHandler GetDailyScriptReportV2Completed;
    /**
    * 
    */
    public GetDailyScriptReportV3CompletedEventHandler GetDailyScriptReportV3Completed;
    /**
    * 
    */
    public GetCompleteMedicationHistoryCompletedEventHandler GetCompleteMedicationHistoryCompleted;
    /**
    * 
    */
    public GetPatientFullMedicationHistory4CompletedEventHandler GetPatientFullMedicationHistory4Completed;
    /**
    * 
    */
    public GetPatientFullMedicationHistory5CompletedEventHandler GetPatientFullMedicationHistory5Completed;
    /**
    * 
    */
    public GetPatientFullMedicationHistory6CompletedEventHandler GetPatientFullMedicationHistory6Completed;
    /**
    * 
    */
    public GetPatientAllergyHistory2CompletedEventHandler GetPatientAllergyHistory2Completed;
    /**
    * 
    */
    public GetPatientAllergyHistoryV3CompletedEventHandler GetPatientAllergyHistoryV3Completed;
    /**
    * 
    */
    public GetPatientFreeFormAllergyHistoryCompletedEventHandler GetPatientFreeFormAllergyHistoryCompleted;
    /**
    * 
    */
    public GetPrescriptionTransmissionStatusCompletedEventHandler GetPrescriptionTransmissionStatusCompleted;
    /**
    * 
    */
    public GetPrescriptionTransmissionStatusByPatientCompletedEventHandler GetPrescriptionTransmissionStatusByPatientCompleted;
    /**
    * 
    */
    public GenerateTestRenewalRequestCompletedEventHandler GenerateTestRenewalRequestCompleted;
    /**
    * 
    */
    public GetAllRenewalRequestsCompletedEventHandler GetAllRenewalRequestsCompleted;
    /**
    * 
    */
    public GetAllRenewalRequestsV2CompletedEventHandler GetAllRenewalRequestsV2Completed;
    /**
    * 
    */
    public GetAllRenewalRequestsV3CompletedEventHandler GetAllRenewalRequestsV3Completed;
    /**
    * 
    */
    public GetAllRenewalRequestsDetailV4CompletedEventHandler GetAllRenewalRequestsDetailV4Completed;
    /**
    * 
    */
    public GetAllRenewalRequestsSummaryV4CompletedEventHandler GetAllRenewalRequestsSummaryV4Completed;
    /**
    * 
    */
    public GetRenewalRequestDetailCompletedEventHandler GetRenewalRequestDetailCompleted;
    /**
    * 
    */
    public GetRenewalRequestDetailV4CompletedEventHandler GetRenewalRequestDetailV4Completed;
    /**
    * 
    */
    public ProcessRenewalRequestCompletedEventHandler ProcessRenewalRequestCompleted;
    /**
    * 
    */
    public GetRenewalResponseTransmissionStatusCompletedEventHandler GetRenewalResponseTransmissionStatusCompleted;
    /**
    * 
    */
    public GetRenewalResponseStatusCompletedEventHandler GetRenewalResponseStatusCompleted;
    /**
    * 
    */
    public FormularyAlternativesWithDrugInfo2CompletedEventHandler FormularyAlternativesWithDrugInfo2Completed;
    /**
    * 
    */
    public SendMissingHealthplanInformationCompletedEventHandler SendMissingHealthplanInformationCompleted;
    /**
    * 
    */
    public GetPBMDrugHistoryV2CompletedEventHandler GetPBMDrugHistoryV2Completed;
    /**
    * 
    */
    public GetMostRecentDownloadUrlCompletedEventHandler GetMostRecentDownloadUrlCompleted;
    /**
    * 
    */
    public DrugDiseaseInteractionCompletedEventHandler DrugDiseaseInteractionCompleted;
    /**
    * 
    */
    public PharmacySearchV3CompletedEventHandler PharmacySearchV3Completed;
    /**
    * 
    */
    public PharmacySearchV4CompletedEventHandler PharmacySearchV4Completed;
    /**
    * 
    */
    public DrugSearchWithFormularyWithFavoritesV2CompletedEventHandler DrugSearchWithFormularyWithFavoritesV2Completed;
    /**
    * 
    */
    public DrugSearchWithFormularyWithFavoritesV3CompletedEventHandler DrugSearchWithFormularyWithFavoritesV3Completed;
    /**
    * 
    */
    public HealthplanSearchV2CompletedEventHandler HealthplanSearchV2Completed;
    /**
    * 
    */
    public FormularyCoverageV3CompletedEventHandler FormularyCoverageV3Completed;
    /**
    * 
    */
    public ReportPrescribingCountCompletedEventHandler ReportPrescribingCountCompleted;
    /**
    * 
    */
    public GetCounselingMessagesCompletedEventHandler GetCounselingMessagesCompleted;
    /**
    * 
    */
    public GetSideEffectsCompletedEventHandler GetSideEffectsCompleted;
    /**
    * 
    */
    public GetAccountStatusDetailCompletedEventHandler GetAccountStatusDetailCompleted;
    /**
    * 
    */
    public GetPBMEligibilityV2CompletedEventHandler GetPBMEligibilityV2Completed;
    /**
    * 
    */
    public GetPBMEligibilityV3CompletedEventHandler GetPBMEligibilityV3Completed;
    /**
    * 
    */
    public GetPrescriptionTransmissionStatusV2CompletedEventHandler GetPrescriptionTransmissionStatusV2Completed;
    /**
    * 
    */
    public GetSubmittedMessageTransactionStatusCompletedEventHandler GetSubmittedMessageTransactionStatusCompleted;
    /**
    * 
    */
    public DrugAllergyInteractionV2CompletedEventHandler DrugAllergyInteractionV2Completed;
    /**
    * 
    */
    public ResolveFailedPrescriptionTransmissionCompletedEventHandler ResolveFailedPrescriptionTransmissionCompleted;
    /**
    * 
    */
    public GetMeaningfulUsePatientEncounterInfoCompletedEventHandler GetMeaningfulUsePatientEncounterInfoCompleted;
    /**
    * 
    */
    public DoseCheckCompletedEventHandler DoseCheckCompleted;
    /**
    * 
    */
    public Result getDailyScriptReport(Credentials credentials, AccountRequest accountRequest, String reportDateCCYYMMDD, String includeSchema, String sortOrder) throws Exception {
        Object[] results = this.Invoke("GetDailyScriptReport", new Object[]{ credentials, accountRequest, reportDateCCYYMMDD, includeSchema, sortOrder });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void getDailyScriptReportAsync(Credentials credentials, AccountRequest accountRequest, String reportDateCCYYMMDD, String includeSchema, String sortOrder) throws Exception {
        this.getDailyScriptReportAsync(credentials,accountRequest,reportDateCCYYMMDD,includeSchema,sortOrder,null);
    }

    /**
    * 
    */
    public void getDailyScriptReportAsync(Credentials credentials, AccountRequest accountRequest, String reportDateCCYYMMDD, String includeSchema, String sortOrder, Object userState) throws Exception {
        if ((this.GetDailyScriptReportOperationCompleted == null))
        {
            this.GetDailyScriptReportOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetDailyScriptReportOperationCompleted);
        }
         
        this.InvokeAsync("GetDailyScriptReport", new Object[]{ credentials, accountRequest, reportDateCCYYMMDD, includeSchema, sortOrder }, this.GetDailyScriptReportOperationCompleted, userState);
    }

    private void onGetDailyScriptReportOperationCompleted(Object arg) throws Exception {
        if ((this.GetDailyScriptReportCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetDailyScriptReportCompleted.invoke(this,new GetDailyScriptReportCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result getDailyScriptReportV2(Credentials credentials, AccountRequest accountRequest, String reportDateCCYYMMDD, int startHour, int endHour, String status, String transmissionType, String includeSchema) throws Exception {
        Object[] results = this.Invoke("GetDailyScriptReportV2", new Object[]{ credentials, accountRequest, reportDateCCYYMMDD, startHour, endHour, status, transmissionType, includeSchema });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void getDailyScriptReportV2Async(Credentials credentials, AccountRequest accountRequest, String reportDateCCYYMMDD, int startHour, int endHour, String status, String transmissionType, String includeSchema) throws Exception {
        this.getDailyScriptReportV2Async(credentials,accountRequest,reportDateCCYYMMDD,startHour,endHour,status,transmissionType,includeSchema,null);
    }

    /**
    * 
    */
    public void getDailyScriptReportV2Async(Credentials credentials, AccountRequest accountRequest, String reportDateCCYYMMDD, int startHour, int endHour, String status, String transmissionType, String includeSchema, Object userState) throws Exception {
        if ((this.GetDailyScriptReportV2OperationCompleted == null))
        {
            this.GetDailyScriptReportV2OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetDailyScriptReportV2OperationCompleted);
        }
         
        this.InvokeAsync("GetDailyScriptReportV2", new Object[]{ credentials, accountRequest, reportDateCCYYMMDD, startHour, endHour, status, transmissionType, includeSchema }, this.GetDailyScriptReportV2OperationCompleted, userState);
    }

    private void onGetDailyScriptReportV2OperationCompleted(Object arg) throws Exception {
        if ((this.GetDailyScriptReportV2Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetDailyScriptReportV2Completed.invoke(this,new GetDailyScriptReportV2CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result getDailyScriptReportV3(Credentials credentials, AccountRequest accountRequest, String reportDateCCYYMMDD, int startHour, int endHour, String status, String transmissionType, String prescriptionType, String prescriptionSubType, String includeSchema) throws Exception {
        Object[] results = this.Invoke("GetDailyScriptReportV3", new Object[]{ credentials, accountRequest, reportDateCCYYMMDD, startHour, endHour, status, transmissionType, prescriptionType, prescriptionSubType, includeSchema });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void getDailyScriptReportV3Async(Credentials credentials, AccountRequest accountRequest, String reportDateCCYYMMDD, int startHour, int endHour, String status, String transmissionType, String prescriptionType, String prescriptionSubType, String includeSchema) throws Exception {
        this.getDailyScriptReportV3Async(credentials,accountRequest,reportDateCCYYMMDD,startHour,endHour,status,transmissionType,prescriptionType,prescriptionSubType,includeSchema,null);
    }

    /**
    * 
    */
    public void getDailyScriptReportV3Async(Credentials credentials, AccountRequest accountRequest, String reportDateCCYYMMDD, int startHour, int endHour, String status, String transmissionType, String prescriptionType, String prescriptionSubType, String includeSchema, Object userState) throws Exception {
        if ((this.GetDailyScriptReportV3OperationCompleted == null))
        {
            this.GetDailyScriptReportV3OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetDailyScriptReportV3OperationCompleted);
        }
         
        this.InvokeAsync("GetDailyScriptReportV3", new Object[]{ credentials, accountRequest, reportDateCCYYMMDD, startHour, endHour, status, transmissionType, prescriptionType, prescriptionSubType, includeSchema }, this.GetDailyScriptReportV3OperationCompleted, userState);
    }

    private void onGetDailyScriptReportV3OperationCompleted(Object arg) throws Exception {
        if ((this.GetDailyScriptReportV3Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetDailyScriptReportV3Completed.invoke(this,new GetDailyScriptReportV3CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result getCompleteMedicationHistory(Credentials credentials, AccountRequest accountRequest, String reportDateStartCCYYMMDD, String reportDateEndCCYYMMDD, String includeSchema, String sortOrder) throws Exception {
        Object[] results = this.Invoke("GetCompleteMedicationHistory", new Object[]{ credentials, accountRequest, reportDateStartCCYYMMDD, reportDateEndCCYYMMDD, includeSchema, sortOrder });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void getCompleteMedicationHistoryAsync(Credentials credentials, AccountRequest accountRequest, String reportDateStartCCYYMMDD, String reportDateEndCCYYMMDD, String includeSchema, String sortOrder) throws Exception {
        this.getCompleteMedicationHistoryAsync(credentials,accountRequest,reportDateStartCCYYMMDD,reportDateEndCCYYMMDD,includeSchema,sortOrder,null);
    }

    /**
    * 
    */
    public void getCompleteMedicationHistoryAsync(Credentials credentials, AccountRequest accountRequest, String reportDateStartCCYYMMDD, String reportDateEndCCYYMMDD, String includeSchema, String sortOrder, Object userState) throws Exception {
        if ((this.GetCompleteMedicationHistoryOperationCompleted == null))
        {
            this.GetCompleteMedicationHistoryOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetCompleteMedicationHistoryOperationCompleted);
        }
         
        this.InvokeAsync("GetCompleteMedicationHistory", new Object[]{ credentials, accountRequest, reportDateStartCCYYMMDD, reportDateEndCCYYMMDD, includeSchema, sortOrder }, this.GetCompleteMedicationHistoryOperationCompleted, userState);
    }

    private void onGetCompleteMedicationHistoryOperationCompleted(Object arg) throws Exception {
        if ((this.GetCompleteMedicationHistoryCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetCompleteMedicationHistoryCompleted.invoke(this,new GetCompleteMedicationHistoryCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public PatientDrugDetailResult4 getPatientFullMedicationHistory4(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PrescriptionHistoryRequest prescriptionHistoryRequest, PatientInformationRequester patientInformationRequester, String patientIdType) throws Exception {
        Object[] results = this.Invoke("GetPatientFullMedicationHistory4", new Object[]{ credentials, accountRequest, patientRequest, prescriptionHistoryRequest, patientInformationRequester, patientIdType });
        return ((PatientDrugDetailResult4)(results[0]));
    }

    /**
    * 
    */
    public void getPatientFullMedicationHistory4Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PrescriptionHistoryRequest prescriptionHistoryRequest, PatientInformationRequester patientInformationRequester, String patientIdType) throws Exception {
        this.getPatientFullMedicationHistory4Async(credentials,accountRequest,patientRequest,prescriptionHistoryRequest,patientInformationRequester,patientIdType,null);
    }

    /**
    * 
    */
    public void getPatientFullMedicationHistory4Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PrescriptionHistoryRequest prescriptionHistoryRequest, PatientInformationRequester patientInformationRequester, String patientIdType, Object userState) throws Exception {
        if ((this.GetPatientFullMedicationHistory4OperationCompleted == null))
        {
            this.GetPatientFullMedicationHistory4OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPatientFullMedicationHistory4OperationCompleted);
        }
         
        this.InvokeAsync("GetPatientFullMedicationHistory4", new Object[]{ credentials, accountRequest, patientRequest, prescriptionHistoryRequest, patientInformationRequester, patientIdType }, this.GetPatientFullMedicationHistory4OperationCompleted, userState);
    }

    private void onGetPatientFullMedicationHistory4OperationCompleted(Object arg) throws Exception {
        if ((this.GetPatientFullMedicationHistory4Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPatientFullMedicationHistory4Completed.invoke(this,new GetPatientFullMedicationHistory4CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public PatientDrugDetailResult5 getPatientFullMedicationHistory5(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PrescriptionHistoryRequest prescriptionHistoryRequest, PatientInformationRequester patientInformationRequester, String patientIdType) throws Exception {
        Object[] results = this.Invoke("GetPatientFullMedicationHistory5", new Object[]{ credentials, accountRequest, patientRequest, prescriptionHistoryRequest, patientInformationRequester, patientIdType });
        return ((PatientDrugDetailResult5)(results[0]));
    }

    /**
    * 
    */
    public void getPatientFullMedicationHistory5Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PrescriptionHistoryRequest prescriptionHistoryRequest, PatientInformationRequester patientInformationRequester, String patientIdType) throws Exception {
        this.getPatientFullMedicationHistory5Async(credentials,accountRequest,patientRequest,prescriptionHistoryRequest,patientInformationRequester,patientIdType,null);
    }

    /**
    * 
    */
    public void getPatientFullMedicationHistory5Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PrescriptionHistoryRequest prescriptionHistoryRequest, PatientInformationRequester patientInformationRequester, String patientIdType, Object userState) throws Exception {
        if ((this.GetPatientFullMedicationHistory5OperationCompleted == null))
        {
            this.GetPatientFullMedicationHistory5OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPatientFullMedicationHistory5OperationCompleted);
        }
         
        this.InvokeAsync("GetPatientFullMedicationHistory5", new Object[]{ credentials, accountRequest, patientRequest, prescriptionHistoryRequest, patientInformationRequester, patientIdType }, this.GetPatientFullMedicationHistory5OperationCompleted, userState);
    }

    private void onGetPatientFullMedicationHistory5OperationCompleted(Object arg) throws Exception {
        if ((this.GetPatientFullMedicationHistory5Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPatientFullMedicationHistory5Completed.invoke(this,new GetPatientFullMedicationHistory5CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result getPatientFullMedicationHistory6(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PrescriptionHistoryRequest prescriptionHistoryRequest, PatientInformationRequester patientInformationRequester, String patientIdType, String includeSchema) throws Exception {
        Object[] results = this.Invoke("GetPatientFullMedicationHistory6", new Object[]{ credentials, accountRequest, patientRequest, prescriptionHistoryRequest, patientInformationRequester, patientIdType, includeSchema });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void getPatientFullMedicationHistory6Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PrescriptionHistoryRequest prescriptionHistoryRequest, PatientInformationRequester patientInformationRequester, String patientIdType, String includeSchema) throws Exception {
        this.getPatientFullMedicationHistory6Async(credentials,accountRequest,patientRequest,prescriptionHistoryRequest,patientInformationRequester,patientIdType,includeSchema,null);
    }

    /**
    * 
    */
    public void getPatientFullMedicationHistory6Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PrescriptionHistoryRequest prescriptionHistoryRequest, PatientInformationRequester patientInformationRequester, String patientIdType, String includeSchema, Object userState) throws Exception {
        if ((this.GetPatientFullMedicationHistory6OperationCompleted == null))
        {
            this.GetPatientFullMedicationHistory6OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPatientFullMedicationHistory6OperationCompleted);
        }
         
        this.InvokeAsync("GetPatientFullMedicationHistory6", new Object[]{ credentials, accountRequest, patientRequest, prescriptionHistoryRequest, patientInformationRequester, patientIdType, includeSchema }, this.GetPatientFullMedicationHistory6OperationCompleted, userState);
    }

    private void onGetPatientFullMedicationHistory6OperationCompleted(Object arg) throws Exception {
        if ((this.GetPatientFullMedicationHistory6Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPatientFullMedicationHistory6Completed.invoke(this,new GetPatientFullMedicationHistory6CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public PatientAllergyExtendedDetailResult getPatientAllergyHistory2(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester) throws Exception {
        Object[] results = this.Invoke("GetPatientAllergyHistory2", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester });
        return ((PatientAllergyExtendedDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void getPatientAllergyHistory2Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester) throws Exception {
        this.getPatientAllergyHistory2Async(credentials,accountRequest,patientRequest,patientInformationRequester,null);
    }

    /**
    * 
    */
    public void getPatientAllergyHistory2Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, Object userState) throws Exception {
        if ((this.GetPatientAllergyHistory2OperationCompleted == null))
        {
            this.GetPatientAllergyHistory2OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPatientAllergyHistory2OperationCompleted);
        }
         
        this.InvokeAsync("GetPatientAllergyHistory2", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester }, this.GetPatientAllergyHistory2OperationCompleted, userState);
    }

    private void onGetPatientAllergyHistory2OperationCompleted(Object arg) throws Exception {
        if ((this.GetPatientAllergyHistory2Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPatientAllergyHistory2Completed.invoke(this,new GetPatientAllergyHistory2CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result getPatientAllergyHistoryV3(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester) throws Exception {
        Object[] results = this.Invoke("GetPatientAllergyHistoryV3", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void getPatientAllergyHistoryV3Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester) throws Exception {
        this.getPatientAllergyHistoryV3Async(credentials,accountRequest,patientRequest,patientInformationRequester,null);
    }

    /**
    * 
    */
    public void getPatientAllergyHistoryV3Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, Object userState) throws Exception {
        if ((this.GetPatientAllergyHistoryV3OperationCompleted == null))
        {
            this.GetPatientAllergyHistoryV3OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPatientAllergyHistoryV3OperationCompleted);
        }
         
        this.InvokeAsync("GetPatientAllergyHistoryV3", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester }, this.GetPatientAllergyHistoryV3OperationCompleted, userState);
    }

    private void onGetPatientAllergyHistoryV3OperationCompleted(Object arg) throws Exception {
        if ((this.GetPatientAllergyHistoryV3Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPatientAllergyHistoryV3Completed.invoke(this,new GetPatientAllergyHistoryV3CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public PatientFreeFormAllergyExtendedDetailResult getPatientFreeFormAllergyHistory(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester) throws Exception {
        Object[] results = this.Invoke("GetPatientFreeFormAllergyHistory", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester });
        return ((PatientFreeFormAllergyExtendedDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void getPatientFreeFormAllergyHistoryAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester) throws Exception {
        this.getPatientFreeFormAllergyHistoryAsync(credentials,accountRequest,patientRequest,patientInformationRequester,null);
    }

    /**
    * 
    */
    public void getPatientFreeFormAllergyHistoryAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, Object userState) throws Exception {
        if ((this.GetPatientFreeFormAllergyHistoryOperationCompleted == null))
        {
            this.GetPatientFreeFormAllergyHistoryOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPatientFreeFormAllergyHistoryOperationCompleted);
        }
         
        this.InvokeAsync("GetPatientFreeFormAllergyHistory", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester }, this.GetPatientFreeFormAllergyHistoryOperationCompleted, userState);
    }

    private void onGetPatientFreeFormAllergyHistoryOperationCompleted(Object arg) throws Exception {
        if ((this.GetPatientFreeFormAllergyHistoryCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPatientFreeFormAllergyHistoryCompleted.invoke(this,new GetPatientFreeFormAllergyHistoryCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public TransmissionSummaryResult getPrescriptionTransmissionStatus(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String prescriptionIds) throws Exception {
        Object[] results = this.Invoke("GetPrescriptionTransmissionStatus", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, prescriptionIds });
        return ((TransmissionSummaryResult)(results[0]));
    }

    /**
    * 
    */
    public void getPrescriptionTransmissionStatusAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String prescriptionIds) throws Exception {
        this.getPrescriptionTransmissionStatusAsync(credentials,accountRequest,patientRequest,patientInformationRequester,prescriptionIds,null);
    }

    /**
    * 
    */
    public void getPrescriptionTransmissionStatusAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String prescriptionIds, Object userState) throws Exception {
        if ((this.GetPrescriptionTransmissionStatusOperationCompleted == null))
        {
            this.GetPrescriptionTransmissionStatusOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPrescriptionTransmissionStatusOperationCompleted);
        }
         
        this.InvokeAsync("GetPrescriptionTransmissionStatus", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, prescriptionIds }, this.GetPrescriptionTransmissionStatusOperationCompleted, userState);
    }

    private void onGetPrescriptionTransmissionStatusOperationCompleted(Object arg) throws Exception {
        if ((this.GetPrescriptionTransmissionStatusCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPrescriptionTransmissionStatusCompleted.invoke(this,new GetPrescriptionTransmissionStatusCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public TransmissionSummaryResult getPrescriptionTransmissionStatusByPatient(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String queryMode, String status, String subStatus, String archiveStatus) throws Exception {
        Object[] results = this.Invoke("GetPrescriptionTransmissionStatusByPatient", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, queryMode, status, subStatus, archiveStatus });
        return ((TransmissionSummaryResult)(results[0]));
    }

    /**
    * 
    */
    public void getPrescriptionTransmissionStatusByPatientAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String queryMode, String status, String subStatus, String archiveStatus) throws Exception {
        this.getPrescriptionTransmissionStatusByPatientAsync(credentials,accountRequest,patientRequest,patientInformationRequester,queryMode,status,subStatus,archiveStatus,null);
    }

    /**
    * 
    */
    public void getPrescriptionTransmissionStatusByPatientAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String queryMode, String status, String subStatus, String archiveStatus, Object userState) throws Exception {
        if ((this.GetPrescriptionTransmissionStatusByPatientOperationCompleted == null))
        {
            this.GetPrescriptionTransmissionStatusByPatientOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPrescriptionTransmissionStatusByPatientOperationCompleted);
        }
         
        this.InvokeAsync("GetPrescriptionTransmissionStatusByPatient", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, queryMode, status, subStatus, archiveStatus }, this.GetPrescriptionTransmissionStatusByPatientOperationCompleted, userState);
    }

    private void onGetPrescriptionTransmissionStatusByPatientOperationCompleted(Object arg) throws Exception {
        if ((this.GetPrescriptionTransmissionStatusByPatientCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPrescriptionTransmissionStatusByPatientCompleted.invoke(this,new GetPrescriptionTransmissionStatusByPatientCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result generateTestRenewalRequest(Credentials credentials, AccountRequest accountRequest, String xmlIn, boolean createCurrentMedFromRxInfo, String originalPrescriptionFillDate) throws Exception {
        Object[] results = this.Invoke("GenerateTestRenewalRequest", new Object[]{ credentials, accountRequest, xmlIn, createCurrentMedFromRxInfo, originalPrescriptionFillDate });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void generateTestRenewalRequestAsync(Credentials credentials, AccountRequest accountRequest, String xmlIn, boolean createCurrentMedFromRxInfo, String originalPrescriptionFillDate) throws Exception {
        this.generateTestRenewalRequestAsync(credentials,accountRequest,xmlIn,createCurrentMedFromRxInfo,originalPrescriptionFillDate,null);
    }

    /**
    * 
    */
    public void generateTestRenewalRequestAsync(Credentials credentials, AccountRequest accountRequest, String xmlIn, boolean createCurrentMedFromRxInfo, String originalPrescriptionFillDate, Object userState) throws Exception {
        if ((this.GenerateTestRenewalRequestOperationCompleted == null))
        {
            this.GenerateTestRenewalRequestOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGenerateTestRenewalRequestOperationCompleted);
        }
         
        this.InvokeAsync("GenerateTestRenewalRequest", new Object[]{ credentials, accountRequest, xmlIn, createCurrentMedFromRxInfo, originalPrescriptionFillDate }, this.GenerateTestRenewalRequestOperationCompleted, userState);
    }

    private void onGenerateTestRenewalRequestOperationCompleted(Object arg) throws Exception {
        if ((this.GenerateTestRenewalRequestCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GenerateTestRenewalRequestCompleted.invoke(this,new GenerateTestRenewalRequestCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public RenewalSummaryResult getAllRenewalRequests(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId) throws Exception {
        Object[] results = this.Invoke("GetAllRenewalRequests", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId });
        return ((RenewalSummaryResult)(results[0]));
    }

    /**
    * 
    */
    public void getAllRenewalRequestsAsync(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId) throws Exception {
        this.getAllRenewalRequestsAsync(credentials,accountRequest,locationId,licensedPrescriberId,null);
    }

    /**
    * 
    */
    public void getAllRenewalRequestsAsync(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, Object userState) throws Exception {
        if ((this.GetAllRenewalRequestsOperationCompleted == null))
        {
            this.GetAllRenewalRequestsOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetAllRenewalRequestsOperationCompleted);
        }
         
        this.InvokeAsync("GetAllRenewalRequests", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId }, this.GetAllRenewalRequestsOperationCompleted, userState);
    }

    private void onGetAllRenewalRequestsOperationCompleted(Object arg) throws Exception {
        if ((this.GetAllRenewalRequestsCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetAllRenewalRequestsCompleted.invoke(this,new GetAllRenewalRequestsCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public RenewalSummaryResultV2 getAllRenewalRequestsV2(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId) throws Exception {
        Object[] results = this.Invoke("GetAllRenewalRequestsV2", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId });
        return ((RenewalSummaryResultV2)(results[0]));
    }

    /**
    * 
    */
    public void getAllRenewalRequestsV2Async(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId) throws Exception {
        this.getAllRenewalRequestsV2Async(credentials,accountRequest,locationId,licensedPrescriberId,null);
    }

    /**
    * 
    */
    public void getAllRenewalRequestsV2Async(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, Object userState) throws Exception {
        if ((this.GetAllRenewalRequestsV2OperationCompleted == null))
        {
            this.GetAllRenewalRequestsV2OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetAllRenewalRequestsV2OperationCompleted);
        }
         
        this.InvokeAsync("GetAllRenewalRequestsV2", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId }, this.GetAllRenewalRequestsV2OperationCompleted, userState);
    }

    private void onGetAllRenewalRequestsV2OperationCompleted(Object arg) throws Exception {
        if ((this.GetAllRenewalRequestsV2Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetAllRenewalRequestsV2Completed.invoke(this,new GetAllRenewalRequestsV2CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public RenewalSummaryResultV2 getAllRenewalRequestsV3(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String renewalRequestDate) throws Exception {
        Object[] results = this.Invoke("GetAllRenewalRequestsV3", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId, renewalRequestDate });
        return ((RenewalSummaryResultV2)(results[0]));
    }

    /**
    * 
    */
    public void getAllRenewalRequestsV3Async(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String renewalRequestDate) throws Exception {
        this.getAllRenewalRequestsV3Async(credentials,accountRequest,locationId,licensedPrescriberId,renewalRequestDate,null);
    }

    /**
    * 
    */
    public void getAllRenewalRequestsV3Async(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String renewalRequestDate, Object userState) throws Exception {
        if ((this.GetAllRenewalRequestsV3OperationCompleted == null))
        {
            this.GetAllRenewalRequestsV3OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetAllRenewalRequestsV3OperationCompleted);
        }
         
        this.InvokeAsync("GetAllRenewalRequestsV3", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId, renewalRequestDate }, this.GetAllRenewalRequestsV3OperationCompleted, userState);
    }

    private void onGetAllRenewalRequestsV3OperationCompleted(Object arg) throws Exception {
        if ((this.GetAllRenewalRequestsV3Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetAllRenewalRequestsV3Completed.invoke(this,new GetAllRenewalRequestsV3CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public RenewalListDetailResultV4 getAllRenewalRequestsDetailV4(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String renewalRequestDate) throws Exception {
        Object[] results = this.Invoke("GetAllRenewalRequestsDetailV4", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId, renewalRequestDate });
        return ((RenewalListDetailResultV4)(results[0]));
    }

    /**
    * 
    */
    public void getAllRenewalRequestsDetailV4Async(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String renewalRequestDate) throws Exception {
        this.getAllRenewalRequestsDetailV4Async(credentials,accountRequest,locationId,licensedPrescriberId,renewalRequestDate,null);
    }

    /**
    * 
    */
    public void getAllRenewalRequestsDetailV4Async(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String renewalRequestDate, Object userState) throws Exception {
        if ((this.GetAllRenewalRequestsDetailV4OperationCompleted == null))
        {
            this.GetAllRenewalRequestsDetailV4OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetAllRenewalRequestsDetailV4OperationCompleted);
        }
         
        this.InvokeAsync("GetAllRenewalRequestsDetailV4", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId, renewalRequestDate }, this.GetAllRenewalRequestsDetailV4OperationCompleted, userState);
    }

    private void onGetAllRenewalRequestsDetailV4OperationCompleted(Object arg) throws Exception {
        if ((this.GetAllRenewalRequestsDetailV4Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetAllRenewalRequestsDetailV4Completed.invoke(this,new GetAllRenewalRequestsDetailV4CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public RenewalListSummaryResultV4 getAllRenewalRequestsSummaryV4(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String renewalRequestDate) throws Exception {
        Object[] results = this.Invoke("GetAllRenewalRequestsSummaryV4", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId, renewalRequestDate });
        return ((RenewalListSummaryResultV4)(results[0]));
    }

    /**
    * 
    */
    public void getAllRenewalRequestsSummaryV4Async(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String renewalRequestDate) throws Exception {
        this.getAllRenewalRequestsSummaryV4Async(credentials,accountRequest,locationId,licensedPrescriberId,renewalRequestDate,null);
    }

    /**
    * 
    */
    public void getAllRenewalRequestsSummaryV4Async(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String renewalRequestDate, Object userState) throws Exception {
        if ((this.GetAllRenewalRequestsSummaryV4OperationCompleted == null))
        {
            this.GetAllRenewalRequestsSummaryV4OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetAllRenewalRequestsSummaryV4OperationCompleted);
        }
         
        this.InvokeAsync("GetAllRenewalRequestsSummaryV4", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId, renewalRequestDate }, this.GetAllRenewalRequestsSummaryV4OperationCompleted, userState);
    }

    private void onGetAllRenewalRequestsSummaryV4OperationCompleted(Object arg) throws Exception {
        if ((this.GetAllRenewalRequestsSummaryV4Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetAllRenewalRequestsSummaryV4Completed.invoke(this,new GetAllRenewalRequestsSummaryV4CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public RenewalDetailResult getRenewalRequestDetail(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier) throws Exception {
        Object[] results = this.Invoke("GetRenewalRequestDetail", new Object[]{ credentials, accountRequest, renewalRequestIdentifier });
        return ((RenewalDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void getRenewalRequestDetailAsync(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier) throws Exception {
        this.getRenewalRequestDetailAsync(credentials,accountRequest,renewalRequestIdentifier,null);
    }

    /**
    * 
    */
    public void getRenewalRequestDetailAsync(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier, Object userState) throws Exception {
        if ((this.GetRenewalRequestDetailOperationCompleted == null))
        {
            this.GetRenewalRequestDetailOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetRenewalRequestDetailOperationCompleted);
        }
         
        this.InvokeAsync("GetRenewalRequestDetail", new Object[]{ credentials, accountRequest, renewalRequestIdentifier }, this.GetRenewalRequestDetailOperationCompleted, userState);
    }

    private void onGetRenewalRequestDetailOperationCompleted(Object arg) throws Exception {
        if ((this.GetRenewalRequestDetailCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetRenewalRequestDetailCompleted.invoke(this,new GetRenewalRequestDetailCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public RenewalDetailResultV4 getRenewalRequestDetailV4(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier, String returnEncodedMessage) throws Exception {
        Object[] results = this.Invoke("GetRenewalRequestDetailV4", new Object[]{ credentials, accountRequest, renewalRequestIdentifier, returnEncodedMessage });
        return ((RenewalDetailResultV4)(results[0]));
    }

    /**
    * 
    */
    public void getRenewalRequestDetailV4Async(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier, String returnEncodedMessage) throws Exception {
        this.getRenewalRequestDetailV4Async(credentials,accountRequest,renewalRequestIdentifier,returnEncodedMessage,null);
    }

    /**
    * 
    */
    public void getRenewalRequestDetailV4Async(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier, String returnEncodedMessage, Object userState) throws Exception {
        if ((this.GetRenewalRequestDetailV4OperationCompleted == null))
        {
            this.GetRenewalRequestDetailV4OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetRenewalRequestDetailV4OperationCompleted);
        }
         
        this.InvokeAsync("GetRenewalRequestDetailV4", new Object[]{ credentials, accountRequest, renewalRequestIdentifier, returnEncodedMessage }, this.GetRenewalRequestDetailV4OperationCompleted, userState);
    }

    private void onGetRenewalRequestDetailV4OperationCompleted(Object arg) throws Exception {
        if ((this.GetRenewalRequestDetailV4Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetRenewalRequestDetailV4Completed.invoke(this,new GetRenewalRequestDetailV4CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result processRenewalRequest(Credentials credentials, AccountRequest accountRequest, String xmlIn) throws Exception {
        Object[] results = this.Invoke("ProcessRenewalRequest", new Object[]{ credentials, accountRequest, xmlIn });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void processRenewalRequestAsync(Credentials credentials, AccountRequest accountRequest, String xmlIn) throws Exception {
        this.processRenewalRequestAsync(credentials,accountRequest,xmlIn,null);
    }

    /**
    * 
    */
    public void processRenewalRequestAsync(Credentials credentials, AccountRequest accountRequest, String xmlIn, Object userState) throws Exception {
        if ((this.ProcessRenewalRequestOperationCompleted == null))
        {
            this.ProcessRenewalRequestOperationCompleted = new System.Threading.SendOrPostCallback(this.OnProcessRenewalRequestOperationCompleted);
        }
         
        this.InvokeAsync("ProcessRenewalRequest", new Object[]{ credentials, accountRequest, xmlIn }, this.ProcessRenewalRequestOperationCompleted, userState);
    }

    private void onProcessRenewalRequestOperationCompleted(Object arg) throws Exception {
        if ((this.ProcessRenewalRequestCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.ProcessRenewalRequestCompleted.invoke(this,new ProcessRenewalRequestCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public RenewalResponseDetailResult getRenewalResponseTransmissionStatus(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier) throws Exception {
        Object[] results = this.Invoke("GetRenewalResponseTransmissionStatus", new Object[]{ credentials, accountRequest, renewalRequestIdentifier });
        return ((RenewalResponseDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void getRenewalResponseTransmissionStatusAsync(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier) throws Exception {
        this.getRenewalResponseTransmissionStatusAsync(credentials,accountRequest,renewalRequestIdentifier,null);
    }

    /**
    * 
    */
    public void getRenewalResponseTransmissionStatusAsync(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier, Object userState) throws Exception {
        if ((this.GetRenewalResponseTransmissionStatusOperationCompleted == null))
        {
            this.GetRenewalResponseTransmissionStatusOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetRenewalResponseTransmissionStatusOperationCompleted);
        }
         
        this.InvokeAsync("GetRenewalResponseTransmissionStatus", new Object[]{ credentials, accountRequest, renewalRequestIdentifier }, this.GetRenewalResponseTransmissionStatusOperationCompleted, userState);
    }

    private void onGetRenewalResponseTransmissionStatusOperationCompleted(Object arg) throws Exception {
        if ((this.GetRenewalResponseTransmissionStatusCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetRenewalResponseTransmissionStatusCompleted.invoke(this,new GetRenewalResponseTransmissionStatusCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result getRenewalResponseStatus(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier, String includeSchema) throws Exception {
        Object[] results = this.Invoke("GetRenewalResponseStatus", new Object[]{ credentials, accountRequest, renewalRequestIdentifier, includeSchema });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void getRenewalResponseStatusAsync(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier, String includeSchema) throws Exception {
        this.getRenewalResponseStatusAsync(credentials,accountRequest,renewalRequestIdentifier,includeSchema,null);
    }

    /**
    * 
    */
    public void getRenewalResponseStatusAsync(Credentials credentials, AccountRequest accountRequest, String renewalRequestIdentifier, String includeSchema, Object userState) throws Exception {
        if ((this.GetRenewalResponseStatusOperationCompleted == null))
        {
            this.GetRenewalResponseStatusOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetRenewalResponseStatusOperationCompleted);
        }
         
        this.InvokeAsync("GetRenewalResponseStatus", new Object[]{ credentials, accountRequest, renewalRequestIdentifier, includeSchema }, this.GetRenewalResponseStatusOperationCompleted, userState);
    }

    private void onGetRenewalResponseStatusOperationCompleted(Object arg) throws Exception {
        if ((this.GetRenewalResponseStatusCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetRenewalResponseStatusCompleted.invoke(this,new GetRenewalResponseStatusCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public DrugFormularyDetailResult formularyAlternativesWithDrugInfo2(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanID, String healthplanTypeID, String eligibilityIndex, String drugConcept, String drugConceptType) throws Exception {
        Object[] results = this.Invoke("FormularyAlternativesWithDrugInfo2", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, healthplanID, healthplanTypeID, eligibilityIndex, drugConcept, drugConceptType });
        return ((DrugFormularyDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void formularyAlternativesWithDrugInfo2Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanID, String healthplanTypeID, String eligibilityIndex, String drugConcept, String drugConceptType) throws Exception {
        this.formularyAlternativesWithDrugInfo2Async(credentials,accountRequest,patientRequest,patientInformationRequester,healthplanID,healthplanTypeID,eligibilityIndex,drugConcept,drugConceptType,null);
    }

    /**
    * 
    */
    public void formularyAlternativesWithDrugInfo2Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanID, String healthplanTypeID, String eligibilityIndex, String drugConcept, String drugConceptType, Object userState) throws Exception {
        if ((this.FormularyAlternativesWithDrugInfo2OperationCompleted == null))
        {
            this.FormularyAlternativesWithDrugInfo2OperationCompleted = new System.Threading.SendOrPostCallback(this.OnFormularyAlternativesWithDrugInfo2OperationCompleted);
        }
         
        this.InvokeAsync("FormularyAlternativesWithDrugInfo2", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, healthplanID, healthplanTypeID, eligibilityIndex, drugConcept, drugConceptType }, this.FormularyAlternativesWithDrugInfo2OperationCompleted, userState);
    }

    private void onFormularyAlternativesWithDrugInfo2OperationCompleted(Object arg) throws Exception {
        if ((this.FormularyAlternativesWithDrugInfo2Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.FormularyAlternativesWithDrugInfo2Completed.invoke(this,new FormularyAlternativesWithDrugInfo2CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result sendMissingHealthplanInformation(Credentials credentials, AccountRequest accountRequest, String healthplanName, String healthplanId, String healthplanAddress1, String healthplanAddress2, String healthplanCity, String healthplanStateCode, String healthplanZip, String healthplanZip4, String healthplanPhoneNumber) throws Exception {
        Object[] results = this.Invoke("SendMissingHealthplanInformation", new Object[]{ credentials, accountRequest, healthplanName, healthplanId, healthplanAddress1, healthplanAddress2, healthplanCity, healthplanStateCode, healthplanZip, healthplanZip4, healthplanPhoneNumber });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void sendMissingHealthplanInformationAsync(Credentials credentials, AccountRequest accountRequest, String healthplanName, String healthplanId, String healthplanAddress1, String healthplanAddress2, String healthplanCity, String healthplanStateCode, String healthplanZip, String healthplanZip4, String healthplanPhoneNumber) throws Exception {
        this.sendMissingHealthplanInformationAsync(credentials,accountRequest,healthplanName,healthplanId,healthplanAddress1,healthplanAddress2,healthplanCity,healthplanStateCode,healthplanZip,healthplanZip4,healthplanPhoneNumber,null);
    }

    /**
    * 
    */
    public void sendMissingHealthplanInformationAsync(Credentials credentials, AccountRequest accountRequest, String healthplanName, String healthplanId, String healthplanAddress1, String healthplanAddress2, String healthplanCity, String healthplanStateCode, String healthplanZip, String healthplanZip4, String healthplanPhoneNumber, Object userState) throws Exception {
        if ((this.SendMissingHealthplanInformationOperationCompleted == null))
        {
            this.SendMissingHealthplanInformationOperationCompleted = new System.Threading.SendOrPostCallback(this.OnSendMissingHealthplanInformationOperationCompleted);
        }
         
        this.InvokeAsync("SendMissingHealthplanInformation", new Object[]{ credentials, accountRequest, healthplanName, healthplanId, healthplanAddress1, healthplanAddress2, healthplanCity, healthplanStateCode, healthplanZip, healthplanZip4, healthplanPhoneNumber }, this.SendMissingHealthplanInformationOperationCompleted, userState);
    }

    private void onSendMissingHealthplanInformationOperationCompleted(Object arg) throws Exception {
        if ((this.SendMissingHealthplanInformationCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.SendMissingHealthplanInformationCompleted.invoke(this,new SendMissingHealthplanInformationCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public DrugHistoryDetailResult getPBMDrugHistoryV2(Credentials credentials, AccountRequest accountRequest, String eligibilityTransactionId, int monthsPrior, String includeSchema) throws Exception {
        Object[] results = this.Invoke("GetPBMDrugHistoryV2", new Object[]{ credentials, accountRequest, eligibilityTransactionId, monthsPrior, includeSchema });
        return ((DrugHistoryDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void getPBMDrugHistoryV2Async(Credentials credentials, AccountRequest accountRequest, String eligibilityTransactionId, int monthsPrior, String includeSchema) throws Exception {
        this.getPBMDrugHistoryV2Async(credentials,accountRequest,eligibilityTransactionId,monthsPrior,includeSchema,null);
    }

    /**
    * 
    */
    public void getPBMDrugHistoryV2Async(Credentials credentials, AccountRequest accountRequest, String eligibilityTransactionId, int monthsPrior, String includeSchema, Object userState) throws Exception {
        if ((this.GetPBMDrugHistoryV2OperationCompleted == null))
        {
            this.GetPBMDrugHistoryV2OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPBMDrugHistoryV2OperationCompleted);
        }
         
        this.InvokeAsync("GetPBMDrugHistoryV2", new Object[]{ credentials, accountRequest, eligibilityTransactionId, monthsPrior, includeSchema }, this.GetPBMDrugHistoryV2OperationCompleted, userState);
    }

    private void onGetPBMDrugHistoryV2OperationCompleted(Object arg) throws Exception {
        if ((this.GetPBMDrugHistoryV2Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPBMDrugHistoryV2Completed.invoke(this,new GetPBMDrugHistoryV2CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public DownloadDetailResult getMostRecentDownloadUrl(Credentials credentials, AccountRequest accountRequest, int desiredData) throws Exception {
        Object[] results = this.Invoke("GetMostRecentDownloadUrl", new Object[]{ credentials, accountRequest, desiredData });
        return ((DownloadDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void getMostRecentDownloadUrlAsync(Credentials credentials, AccountRequest accountRequest, int desiredData) throws Exception {
        this.getMostRecentDownloadUrlAsync(credentials,accountRequest,desiredData,null);
    }

    /**
    * 
    */
    public void getMostRecentDownloadUrlAsync(Credentials credentials, AccountRequest accountRequest, int desiredData, Object userState) throws Exception {
        if ((this.GetMostRecentDownloadUrlOperationCompleted == null))
        {
            this.GetMostRecentDownloadUrlOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetMostRecentDownloadUrlOperationCompleted);
        }
         
        this.InvokeAsync("GetMostRecentDownloadUrl", new Object[]{ credentials, accountRequest, desiredData }, this.GetMostRecentDownloadUrlOperationCompleted, userState);
    }

    private void onGetMostRecentDownloadUrlOperationCompleted(Object arg) throws Exception {
        if ((this.GetMostRecentDownloadUrlCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetMostRecentDownloadUrlCompleted.invoke(this,new GetMostRecentDownloadUrlCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public DrugDiseaseDetailResult drugDiseaseInteraction(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String[] diseaseList, String diseaseCodeType, String[] proposedMedications, String drugStandardType) throws Exception {
        Object[] results = this.Invoke("DrugDiseaseInteraction", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, diseaseList, diseaseCodeType, proposedMedications, drugStandardType });
        return ((DrugDiseaseDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void drugDiseaseInteractionAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String[] diseaseList, String diseaseCodeType, String[] proposedMedications, String drugStandardType) throws Exception {
        this.DrugDiseaseInteractionAsync(credentials, accountRequest, patientRequest, patientInformationRequester, diseaseList, diseaseCodeType, proposedMedications, drugStandardType, null);
    }

    /**
    * 
    */
    public void drugDiseaseInteractionAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String[] diseaseList, String diseaseCodeType, String[] proposedMedications, String drugStandardType, Object userState) throws Exception {
        if ((this.DrugDiseaseInteractionOperationCompleted == null))
        {
            this.DrugDiseaseInteractionOperationCompleted = new System.Threading.SendOrPostCallback(this.OnDrugDiseaseInteractionOperationCompleted);
        }
         
        this.InvokeAsync("DrugDiseaseInteraction", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, diseaseList, diseaseCodeType, proposedMedications, drugStandardType }, this.DrugDiseaseInteractionOperationCompleted, userState);
    }

    private void onDrugDiseaseInteractionOperationCompleted(Object arg) throws Exception {
        if ((this.DrugDiseaseInteractionCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.DrugDiseaseInteractionCompleted.invoke(this,new DrugDiseaseInteractionCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public PharmacyDetailResultV2 pharmacySearchV3(Credentials credentials, AccountRequest accountRequest, String postalCode, String phoneNumber, String streetName, String pharmacyName, String city, String state, String ncpdpID, String healthplanID, String healthplanTypeID, String eligibilityIndex) throws Exception {
        Object[] results = this.Invoke("PharmacySearchV3", new Object[]{ credentials, accountRequest, postalCode, phoneNumber, streetName, pharmacyName, city, state, ncpdpID, healthplanID, healthplanTypeID, eligibilityIndex });
        return ((PharmacyDetailResultV2)(results[0]));
    }

    /**
    * 
    */
    public void pharmacySearchV3Async(Credentials credentials, AccountRequest accountRequest, String postalCode, String phoneNumber, String streetName, String pharmacyName, String city, String state, String ncpdpID, String healthplanID, String healthplanTypeID, String eligibilityIndex) throws Exception {
        this.pharmacySearchV3Async(credentials,accountRequest,postalCode,phoneNumber,streetName,pharmacyName,city,state,ncpdpID,healthplanID,healthplanTypeID,eligibilityIndex,null);
    }

    /**
    * 
    */
    public void pharmacySearchV3Async(Credentials credentials, AccountRequest accountRequest, String postalCode, String phoneNumber, String streetName, String pharmacyName, String city, String state, String ncpdpID, String healthplanID, String healthplanTypeID, String eligibilityIndex, Object userState) throws Exception {
        if ((this.PharmacySearchV3OperationCompleted == null))
        {
            this.PharmacySearchV3OperationCompleted = new System.Threading.SendOrPostCallback(this.OnPharmacySearchV3OperationCompleted);
        }
         
        this.InvokeAsync("PharmacySearchV3", new Object[]{ credentials, accountRequest, postalCode, phoneNumber, streetName, pharmacyName, city, state, ncpdpID, healthplanID, healthplanTypeID, eligibilityIndex }, this.PharmacySearchV3OperationCompleted, userState);
    }

    private void onPharmacySearchV3OperationCompleted(Object arg) throws Exception {
        if ((this.PharmacySearchV3Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.PharmacySearchV3Completed.invoke(this,new PharmacySearchV3CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public PharmacyDetailResultV4 pharmacySearchV4(Credentials credentials, AccountRequest accountRequest, String postalCode, String phoneNumber, String streetName, String pharmacyName, String city, String state, String ncpdpID, String healthplanID, String healthplanTypeID, String eligibilityIndex, String pharmacyType, String open24Hours, String specialtyID, String extraQuery1, String extraQuery2, String extraQuery3, String extraQuery4) throws Exception {
        Object[] results = this.Invoke("PharmacySearchV4", new Object[]{ credentials, accountRequest, postalCode, phoneNumber, streetName, pharmacyName, city, state, ncpdpID, healthplanID, healthplanTypeID, eligibilityIndex, pharmacyType, open24Hours, specialtyID, extraQuery1, extraQuery2, extraQuery3, extraQuery4 });
        return ((PharmacyDetailResultV4)(results[0]));
    }

    /**
    * 
    */
    public void pharmacySearchV4Async(Credentials credentials, AccountRequest accountRequest, String postalCode, String phoneNumber, String streetName, String pharmacyName, String city, String state, String ncpdpID, String healthplanID, String healthplanTypeID, String eligibilityIndex, String pharmacyType, String open24Hours, String specialtyID, String extraQuery1, String extraQuery2, String extraQuery3, String extraQuery4) throws Exception {
        this.pharmacySearchV4Async(credentials,accountRequest,postalCode,phoneNumber,streetName,pharmacyName,city,state,ncpdpID,healthplanID,healthplanTypeID,eligibilityIndex,pharmacyType,open24Hours,specialtyID,extraQuery1,extraQuery2,extraQuery3,extraQuery4,null);
    }

    /**
    * 
    */
    public void pharmacySearchV4Async(Credentials credentials, AccountRequest accountRequest, String postalCode, String phoneNumber, String streetName, String pharmacyName, String city, String state, String ncpdpID, String healthplanID, String healthplanTypeID, String eligibilityIndex, String pharmacyType, String open24Hours, String specialtyID, String extraQuery1, String extraQuery2, String extraQuery3, String extraQuery4, Object userState) throws Exception {
        if ((this.PharmacySearchV4OperationCompleted == null))
        {
            this.PharmacySearchV4OperationCompleted = new System.Threading.SendOrPostCallback(this.OnPharmacySearchV4OperationCompleted);
        }
         
        this.InvokeAsync("PharmacySearchV4", new Object[]{ credentials, accountRequest, postalCode, phoneNumber, streetName, pharmacyName, city, state, ncpdpID, healthplanID, healthplanTypeID, eligibilityIndex, pharmacyType, open24Hours, specialtyID, extraQuery1, extraQuery2, extraQuery3, extraQuery4 }, this.PharmacySearchV4OperationCompleted, userState);
    }

    private void onPharmacySearchV4OperationCompleted(Object arg) throws Exception {
        if ((this.PharmacySearchV4Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.PharmacySearchV4Completed.invoke(this,new PharmacySearchV4CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public DrugFormularyFavoriteDetailResult drugSearchWithFormularyWithFavoritesV2(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanID, String healthplanTypeID, String eligibilityIndex, String drugName, String includeObsolete, String searchBrandGeneric, String searchRxOTC, String searchDrugSupply, String locationId, String providerId) throws Exception {
        Object[] results = this.Invoke("DrugSearchWithFormularyWithFavoritesV2", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, healthplanID, healthplanTypeID, eligibilityIndex, drugName, includeObsolete, searchBrandGeneric, searchRxOTC, searchDrugSupply, locationId, providerId });
        return ((DrugFormularyFavoriteDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void drugSearchWithFormularyWithFavoritesV2Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanID, String healthplanTypeID, String eligibilityIndex, String drugName, String includeObsolete, String searchBrandGeneric, String searchRxOTC, String searchDrugSupply, String locationId, String providerId) throws Exception {
        this.drugSearchWithFormularyWithFavoritesV2Async(credentials,accountRequest,patientRequest,patientInformationRequester,healthplanID,healthplanTypeID,eligibilityIndex,drugName,includeObsolete,searchBrandGeneric,searchRxOTC,searchDrugSupply,locationId,providerId,null);
    }

    /**
    * 
    */
    public void drugSearchWithFormularyWithFavoritesV2Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanID, String healthplanTypeID, String eligibilityIndex, String drugName, String includeObsolete, String searchBrandGeneric, String searchRxOTC, String searchDrugSupply, String locationId, String providerId, Object userState) throws Exception {
        if ((this.DrugSearchWithFormularyWithFavoritesV2OperationCompleted == null))
        {
            this.DrugSearchWithFormularyWithFavoritesV2OperationCompleted = new System.Threading.SendOrPostCallback(this.OnDrugSearchWithFormularyWithFavoritesV2OperationCompleted);
        }
         
        this.InvokeAsync("DrugSearchWithFormularyWithFavoritesV2", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, healthplanID, healthplanTypeID, eligibilityIndex, drugName, includeObsolete, searchBrandGeneric, searchRxOTC, searchDrugSupply, locationId, providerId }, this.DrugSearchWithFormularyWithFavoritesV2OperationCompleted, userState);
    }

    private void onDrugSearchWithFormularyWithFavoritesV2OperationCompleted(Object arg) throws Exception {
        if ((this.DrugSearchWithFormularyWithFavoritesV2Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.DrugSearchWithFormularyWithFavoritesV2Completed.invoke(this,new DrugSearchWithFormularyWithFavoritesV2CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result drugSearchWithFormularyWithFavoritesV3(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanId, String healthplanTypeId, String eligibilityIndex, String drugName, String drugTypeId, String includeObsolete, String searchBrandGeneric, String searchRxOTC, String searchDrugSupply, String locationId, String providerId, String includeCopay, String includeSchema) throws Exception {
        Object[] results = this.Invoke("DrugSearchWithFormularyWithFavoritesV3", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, healthplanId, healthplanTypeId, eligibilityIndex, drugName, drugTypeId, includeObsolete, searchBrandGeneric, searchRxOTC, searchDrugSupply, locationId, providerId, includeCopay, includeSchema });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void drugSearchWithFormularyWithFavoritesV3Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanId, String healthplanTypeId, String eligibilityIndex, String drugName, String drugTypeId, String includeObsolete, String searchBrandGeneric, String searchRxOTC, String searchDrugSupply, String locationId, String providerId, String includeCopay, String includeSchema) throws Exception {
        this.drugSearchWithFormularyWithFavoritesV3Async(credentials,accountRequest,patientRequest,patientInformationRequester,healthplanId,healthplanTypeId,eligibilityIndex,drugName,drugTypeId,includeObsolete,searchBrandGeneric,searchRxOTC,searchDrugSupply,locationId,providerId,includeCopay,includeSchema,null);
    }

    /**
    * 
    */
    public void drugSearchWithFormularyWithFavoritesV3Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanId, String healthplanTypeId, String eligibilityIndex, String drugName, String drugTypeId, String includeObsolete, String searchBrandGeneric, String searchRxOTC, String searchDrugSupply, String locationId, String providerId, String includeCopay, String includeSchema, Object userState) throws Exception {
        if ((this.DrugSearchWithFormularyWithFavoritesV3OperationCompleted == null))
        {
            this.DrugSearchWithFormularyWithFavoritesV3OperationCompleted = new System.Threading.SendOrPostCallback(this.OnDrugSearchWithFormularyWithFavoritesV3OperationCompleted);
        }
         
        this.InvokeAsync("DrugSearchWithFormularyWithFavoritesV3", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, healthplanId, healthplanTypeId, eligibilityIndex, drugName, drugTypeId, includeObsolete, searchBrandGeneric, searchRxOTC, searchDrugSupply, locationId, providerId, includeCopay, includeSchema }, this.DrugSearchWithFormularyWithFavoritesV3OperationCompleted, userState);
    }

    private void onDrugSearchWithFormularyWithFavoritesV3OperationCompleted(Object arg) throws Exception {
        if ((this.DrugSearchWithFormularyWithFavoritesV3Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.DrugSearchWithFormularyWithFavoritesV3Completed.invoke(this,new DrugSearchWithFormularyWithFavoritesV3CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public HealthplanDetailResult healthplanSearchV2(Credentials credentials, AccountRequest accountRequest, String healthplan, String state, String searchType, String resultOrder) throws Exception {
        Object[] results = this.Invoke("HealthplanSearchV2", new Object[]{ credentials, accountRequest, healthplan, state, searchType, resultOrder });
        return ((HealthplanDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void healthplanSearchV2Async(Credentials credentials, AccountRequest accountRequest, String healthplan, String state, String searchType, String resultOrder) throws Exception {
        this.healthplanSearchV2Async(credentials,accountRequest,healthplan,state,searchType,resultOrder,null);
    }

    /**
    * 
    */
    public void healthplanSearchV2Async(Credentials credentials, AccountRequest accountRequest, String healthplan, String state, String searchType, String resultOrder, Object userState) throws Exception {
        if ((this.HealthplanSearchV2OperationCompleted == null))
        {
            this.HealthplanSearchV2OperationCompleted = new System.Threading.SendOrPostCallback(this.OnHealthplanSearchV2OperationCompleted);
        }
         
        this.InvokeAsync("HealthplanSearchV2", new Object[]{ credentials, accountRequest, healthplan, state, searchType, resultOrder }, this.HealthplanSearchV2OperationCompleted, userState);
    }

    private void onHealthplanSearchV2OperationCompleted(Object arg) throws Exception {
        if ((this.HealthplanSearchV2Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.HealthplanSearchV2Completed.invoke(this,new HealthplanSearchV2CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public FormularyCoverageDetailResultV3 formularyCoverageV3(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanID, String healthplanTypeID, String eligibilityIndex, String[] drugConcept, String drugConceptType) throws Exception {
        Object[] results = this.Invoke("FormularyCoverageV3", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, healthplanID, healthplanTypeID, eligibilityIndex, drugConcept, drugConceptType });
        return ((FormularyCoverageDetailResultV3)(results[0]));
    }

    /**
    * 
    */
    public void formularyCoverageV3Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanID, String healthplanTypeID, String eligibilityIndex, String[] drugConcept, String drugConceptType) throws Exception {
        this.FormularyCoverageV3Async(credentials, accountRequest, patientRequest, patientInformationRequester, healthplanID, healthplanTypeID, eligibilityIndex, drugConcept, drugConceptType, null);
    }

    /**
    * 
    */
    public void formularyCoverageV3Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String healthplanID, String healthplanTypeID, String eligibilityIndex, String[] drugConcept, String drugConceptType, Object userState) throws Exception {
        if ((this.FormularyCoverageV3OperationCompleted == null))
        {
            this.FormularyCoverageV3OperationCompleted = new System.Threading.SendOrPostCallback(this.OnFormularyCoverageV3OperationCompleted);
        }
         
        this.InvokeAsync("FormularyCoverageV3", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, healthplanID, healthplanTypeID, eligibilityIndex, drugConcept, drugConceptType }, this.FormularyCoverageV3OperationCompleted, userState);
    }

    private void onFormularyCoverageV3OperationCompleted(Object arg) throws Exception {
        if ((this.FormularyCoverageV3Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.FormularyCoverageV3Completed.invoke(this,new FormularyCoverageV3CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result reportPrescribingCount(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String reportType, String reportStartDate, String reportEndDate, String prescriptionType, String prescriptionCount) throws Exception {
        Object[] results = this.Invoke("ReportPrescribingCount", new Object[]{ credentials, accountRequest, patientInformationRequester, reportType, reportStartDate, reportEndDate, prescriptionType, prescriptionCount });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void reportPrescribingCountAsync(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String reportType, String reportStartDate, String reportEndDate, String prescriptionType, String prescriptionCount) throws Exception {
        this.reportPrescribingCountAsync(credentials,accountRequest,patientInformationRequester,reportType,reportStartDate,reportEndDate,prescriptionType,prescriptionCount,null);
    }

    /**
    * 
    */
    public void reportPrescribingCountAsync(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String reportType, String reportStartDate, String reportEndDate, String prescriptionType, String prescriptionCount, Object userState) throws Exception {
        if ((this.ReportPrescribingCountOperationCompleted == null))
        {
            this.ReportPrescribingCountOperationCompleted = new System.Threading.SendOrPostCallback(this.OnReportPrescribingCountOperationCompleted);
        }
         
        this.InvokeAsync("ReportPrescribingCount", new Object[]{ credentials, accountRequest, patientInformationRequester, reportType, reportStartDate, reportEndDate, prescriptionType, prescriptionCount }, this.ReportPrescribingCountOperationCompleted, userState);
    }

    private void onReportPrescribingCountOperationCompleted(Object arg) throws Exception {
        if ((this.ReportPrescribingCountCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.ReportPrescribingCountCompleted.invoke(this,new ReportPrescribingCountCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public CounselingMessageDetailResult getCounselingMessages(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String drugConcept, String drugStandardType) throws Exception {
        Object[] results = this.Invoke("GetCounselingMessages", new Object[]{ credentials, accountRequest, patientInformationRequester, drugConcept, drugStandardType });
        return ((CounselingMessageDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void getCounselingMessagesAsync(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String drugConcept, String drugStandardType) throws Exception {
        this.getCounselingMessagesAsync(credentials,accountRequest,patientInformationRequester,drugConcept,drugStandardType,null);
    }

    /**
    * 
    */
    public void getCounselingMessagesAsync(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String drugConcept, String drugStandardType, Object userState) throws Exception {
        if ((this.GetCounselingMessagesOperationCompleted == null))
        {
            this.GetCounselingMessagesOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetCounselingMessagesOperationCompleted);
        }
         
        this.InvokeAsync("GetCounselingMessages", new Object[]{ credentials, accountRequest, patientInformationRequester, drugConcept, drugStandardType }, this.GetCounselingMessagesOperationCompleted, userState);
    }

    private void onGetCounselingMessagesOperationCompleted(Object arg) throws Exception {
        if ((this.GetCounselingMessagesCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetCounselingMessagesCompleted.invoke(this,new GetCounselingMessagesCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result getSideEffects(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String drugConcept, String drugStandardType, String diseaseDescriptionType, String includeSchema, String sortOrder) throws Exception {
        Object[] results = this.Invoke("GetSideEffects", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, drugConcept, drugStandardType, diseaseDescriptionType, includeSchema, sortOrder });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void getSideEffectsAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String drugConcept, String drugStandardType, String diseaseDescriptionType, String includeSchema, String sortOrder) throws Exception {
        this.getSideEffectsAsync(credentials,accountRequest,patientRequest,patientInformationRequester,drugConcept,drugStandardType,diseaseDescriptionType,includeSchema,sortOrder,null);
    }

    /**
    * 
    */
    public void getSideEffectsAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String drugConcept, String drugStandardType, String diseaseDescriptionType, String includeSchema, String sortOrder, Object userState) throws Exception {
        if ((this.GetSideEffectsOperationCompleted == null))
        {
            this.GetSideEffectsOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetSideEffectsOperationCompleted);
        }
         
        this.InvokeAsync("GetSideEffects", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, drugConcept, drugStandardType, diseaseDescriptionType, includeSchema, sortOrder }, this.GetSideEffectsOperationCompleted, userState);
    }

    private void onGetSideEffectsOperationCompleted(Object arg) throws Exception {
        if ((this.GetSideEffectsCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetSideEffectsCompleted.invoke(this,new GetSideEffectsCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result getAccountStatusDetail(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String statusSectionType, String includeSchema, String sortOrder) throws Exception {
        Object[] results = this.Invoke("GetAccountStatusDetail", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId, statusSectionType, includeSchema, sortOrder });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void getAccountStatusDetailAsync(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String statusSectionType, String includeSchema, String sortOrder) throws Exception {
        this.getAccountStatusDetailAsync(credentials,accountRequest,locationId,licensedPrescriberId,statusSectionType,includeSchema,sortOrder,null);
    }

    /**
    * 
    */
    public void getAccountStatusDetailAsync(Credentials credentials, AccountRequest accountRequest, String locationId, String licensedPrescriberId, String statusSectionType, String includeSchema, String sortOrder, Object userState) throws Exception {
        if ((this.GetAccountStatusDetailOperationCompleted == null))
        {
            this.GetAccountStatusDetailOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetAccountStatusDetailOperationCompleted);
        }
         
        this.InvokeAsync("GetAccountStatusDetail", new Object[]{ credentials, accountRequest, locationId, licensedPrescriberId, statusSectionType, includeSchema, sortOrder }, this.GetAccountStatusDetailOperationCompleted, userState);
    }

    private void onGetAccountStatusDetailOperationCompleted(Object arg) throws Exception {
        if ((this.GetAccountStatusDetailCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetAccountStatusDetailCompleted.invoke(this,new GetAccountStatusDetailCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public EligibilityDetailResult getPBMEligibilityV2(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String xmlIn, String includeSchema) throws Exception {
        Object[] results = this.Invoke("GetPBMEligibilityV2", new Object[]{ credentials, accountRequest, patientInformationRequester, xmlIn, includeSchema });
        return ((EligibilityDetailResult)(results[0]));
    }

    /**
    * 
    */
    public void getPBMEligibilityV2Async(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String xmlIn, String includeSchema) throws Exception {
        this.getPBMEligibilityV2Async(credentials,accountRequest,patientInformationRequester,xmlIn,includeSchema,null);
    }

    /**
    * 
    */
    public void getPBMEligibilityV2Async(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String xmlIn, String includeSchema, Object userState) throws Exception {
        if ((this.GetPBMEligibilityV2OperationCompleted == null))
        {
            this.GetPBMEligibilityV2OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPBMEligibilityV2OperationCompleted);
        }
         
        this.InvokeAsync("GetPBMEligibilityV2", new Object[]{ credentials, accountRequest, patientInformationRequester, xmlIn, includeSchema }, this.GetPBMEligibilityV2OperationCompleted, userState);
    }

    private void onGetPBMEligibilityV2OperationCompleted(Object arg) throws Exception {
        if ((this.GetPBMEligibilityV2Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPBMEligibilityV2Completed.invoke(this,new GetPBMEligibilityV2CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public EligibilityDetailResultV3 getPBMEligibilityV3(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String xmlIn, String includeSchema) throws Exception {
        Object[] results = this.Invoke("GetPBMEligibilityV3", new Object[]{ credentials, accountRequest, patientInformationRequester, xmlIn, includeSchema });
        return ((EligibilityDetailResultV3)(results[0]));
    }

    /**
    * 
    */
    public void getPBMEligibilityV3Async(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String xmlIn, String includeSchema) throws Exception {
        this.getPBMEligibilityV3Async(credentials,accountRequest,patientInformationRequester,xmlIn,includeSchema,null);
    }

    /**
    * 
    */
    public void getPBMEligibilityV3Async(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String xmlIn, String includeSchema, Object userState) throws Exception {
        if ((this.GetPBMEligibilityV3OperationCompleted == null))
        {
            this.GetPBMEligibilityV3OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPBMEligibilityV3OperationCompleted);
        }
         
        this.InvokeAsync("GetPBMEligibilityV3", new Object[]{ credentials, accountRequest, patientInformationRequester, xmlIn, includeSchema }, this.GetPBMEligibilityV3OperationCompleted, userState);
    }

    private void onGetPBMEligibilityV3OperationCompleted(Object arg) throws Exception {
        if ((this.GetPBMEligibilityV3Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPBMEligibilityV3Completed.invoke(this,new GetPBMEligibilityV3CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public TransmissionSummaryResult getPrescriptionTransmissionStatusV2(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, PrescriptionTransmissionQueryType queryType, String queryId) throws Exception {
        Object[] results = this.Invoke("GetPrescriptionTransmissionStatusV2", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, queryType, queryId });
        return ((TransmissionSummaryResult)(results[0]));
    }

    /**
    * 
    */
    public void getPrescriptionTransmissionStatusV2Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, PrescriptionTransmissionQueryType queryType, String queryId) throws Exception {
        this.getPrescriptionTransmissionStatusV2Async(credentials,accountRequest,patientRequest,patientInformationRequester,queryType,queryId,null);
    }

    /**
    * 
    */
    public void getPrescriptionTransmissionStatusV2Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, PrescriptionTransmissionQueryType queryType, String queryId, Object userState) throws Exception {
        if ((this.GetPrescriptionTransmissionStatusV2OperationCompleted == null))
        {
            this.GetPrescriptionTransmissionStatusV2OperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetPrescriptionTransmissionStatusV2OperationCompleted);
        }
         
        this.InvokeAsync("GetPrescriptionTransmissionStatusV2", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, queryType, queryId }, this.GetPrescriptionTransmissionStatusV2OperationCompleted, userState);
    }

    private void onGetPrescriptionTransmissionStatusV2OperationCompleted(Object arg) throws Exception {
        if ((this.GetPrescriptionTransmissionStatusV2Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetPrescriptionTransmissionStatusV2Completed.invoke(this,new GetPrescriptionTransmissionStatusV2CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public MessageTransactionStatusResult getSubmittedMessageTransactionStatus(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String messageTransactionId, String messageTransactionSource) throws Exception {
        Object[] results = this.Invoke("GetSubmittedMessageTransactionStatus", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, messageTransactionId, messageTransactionSource });
        return ((MessageTransactionStatusResult)(results[0]));
    }

    /**
    * 
    */
    public void getSubmittedMessageTransactionStatusAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String messageTransactionId, String messageTransactionSource) throws Exception {
        this.getSubmittedMessageTransactionStatusAsync(credentials,accountRequest,patientRequest,patientInformationRequester,messageTransactionId,messageTransactionSource,null);
    }

    /**
    * 
    */
    public void getSubmittedMessageTransactionStatusAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String messageTransactionId, String messageTransactionSource, Object userState) throws Exception {
        if ((this.GetSubmittedMessageTransactionStatusOperationCompleted == null))
        {
            this.GetSubmittedMessageTransactionStatusOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetSubmittedMessageTransactionStatusOperationCompleted);
        }
         
        this.InvokeAsync("GetSubmittedMessageTransactionStatus", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, messageTransactionId, messageTransactionSource }, this.GetSubmittedMessageTransactionStatusOperationCompleted, userState);
    }

    private void onGetSubmittedMessageTransactionStatusOperationCompleted(Object arg) throws Exception {
        if ((this.GetSubmittedMessageTransactionStatusCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetSubmittedMessageTransactionStatusCompleted.invoke(this,new GetSubmittedMessageTransactionStatusCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public DrugAllergyDetailResultV2 drugAllergyInteractionV2(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String[] allergies, String[] proposedMedications, String drugStandardType) throws Exception {
        Object[] results = this.Invoke("DrugAllergyInteractionV2", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, allergies, proposedMedications, drugStandardType });
        return ((DrugAllergyDetailResultV2)(results[0]));
    }

    /**
    * 
    */
    public void drugAllergyInteractionV2Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String[] allergies, String[] proposedMedications, String drugStandardType) throws Exception {
        this.DrugAllergyInteractionV2Async(credentials, accountRequest, patientRequest, patientInformationRequester, allergies, proposedMedications, drugStandardType, null);
    }

    /**
    * 
    */
    public void drugAllergyInteractionV2Async(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String[] allergies, String[] proposedMedications, String drugStandardType, Object userState) throws Exception {
        if ((this.DrugAllergyInteractionV2OperationCompleted == null))
        {
            this.DrugAllergyInteractionV2OperationCompleted = new System.Threading.SendOrPostCallback(this.OnDrugAllergyInteractionV2OperationCompleted);
        }
         
        this.InvokeAsync("DrugAllergyInteractionV2", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, allergies, proposedMedications, drugStandardType }, this.DrugAllergyInteractionV2OperationCompleted, userState);
    }

    private void onDrugAllergyInteractionV2OperationCompleted(Object arg) throws Exception {
        if ((this.DrugAllergyInteractionV2Completed != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.DrugAllergyInteractionV2Completed.invoke(this,new DrugAllergyInteractionV2CompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result resolveFailedPrescriptionTransmission(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String prescriptionType, String transactionId) throws Exception {
        Object[] results = this.Invoke("ResolveFailedPrescriptionTransmission", new Object[]{ credentials, accountRequest, patientInformationRequester, prescriptionType, transactionId });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void resolveFailedPrescriptionTransmissionAsync(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String prescriptionType, String transactionId) throws Exception {
        this.resolveFailedPrescriptionTransmissionAsync(credentials,accountRequest,patientInformationRequester,prescriptionType,transactionId,null);
    }

    /**
    * 
    */
    public void resolveFailedPrescriptionTransmissionAsync(Credentials credentials, AccountRequest accountRequest, PatientInformationRequester patientInformationRequester, String prescriptionType, String transactionId, Object userState) throws Exception {
        if ((this.ResolveFailedPrescriptionTransmissionOperationCompleted == null))
        {
            this.ResolveFailedPrescriptionTransmissionOperationCompleted = new System.Threading.SendOrPostCallback(this.OnResolveFailedPrescriptionTransmissionOperationCompleted);
        }
         
        this.InvokeAsync("ResolveFailedPrescriptionTransmission", new Object[]{ credentials, accountRequest, patientInformationRequester, prescriptionType, transactionId }, this.ResolveFailedPrescriptionTransmissionOperationCompleted, userState);
    }

    private void onResolveFailedPrescriptionTransmissionOperationCompleted(Object arg) throws Exception {
        if ((this.ResolveFailedPrescriptionTransmissionCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.ResolveFailedPrescriptionTransmissionCompleted.invoke(this,new ResolveFailedPrescriptionTransmissionCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result getMeaningfulUsePatientEncounterInfo(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String encounterId, String includeSchema) throws Exception {
        Object[] results = this.Invoke("GetMeaningfulUsePatientEncounterInfo", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, encounterId, includeSchema });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void getMeaningfulUsePatientEncounterInfoAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String encounterId, String includeSchema) throws Exception {
        this.getMeaningfulUsePatientEncounterInfoAsync(credentials,accountRequest,patientRequest,patientInformationRequester,encounterId,includeSchema,null);
    }

    /**
    * 
    */
    public void getMeaningfulUsePatientEncounterInfoAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String encounterId, String includeSchema, Object userState) throws Exception {
        if ((this.GetMeaningfulUsePatientEncounterInfoOperationCompleted == null))
        {
            this.GetMeaningfulUsePatientEncounterInfoOperationCompleted = new System.Threading.SendOrPostCallback(this.OnGetMeaningfulUsePatientEncounterInfoOperationCompleted);
        }
         
        this.InvokeAsync("GetMeaningfulUsePatientEncounterInfo", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, encounterId, includeSchema }, this.GetMeaningfulUsePatientEncounterInfoOperationCompleted, userState);
    }

    private void onGetMeaningfulUsePatientEncounterInfoOperationCompleted(Object arg) throws Exception {
        if ((this.GetMeaningfulUsePatientEncounterInfoCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.GetMeaningfulUsePatientEncounterInfoCompleted.invoke(this,new GetMeaningfulUsePatientEncounterInfoCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public Result doseCheck(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String drugId, String drugTypeId, String birthDateCCYYMMDD, String gender, String diagnosis, String doseType, String dose, String doseUOM, String weightInKg, String includeSchema) throws Exception {
        Object[] results = this.Invoke("DoseCheck", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, drugId, drugTypeId, birthDateCCYYMMDD, gender, diagnosis, doseType, dose, doseUOM, weightInKg, includeSchema });
        return ((Result)(results[0]));
    }

    /**
    * 
    */
    public void doseCheckAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String drugId, String drugTypeId, String birthDateCCYYMMDD, String gender, String diagnosis, String doseType, String dose, String doseUOM, String weightInKg, String includeSchema) throws Exception {
        this.doseCheckAsync(credentials,accountRequest,patientRequest,patientInformationRequester,drugId,drugTypeId,birthDateCCYYMMDD,gender,diagnosis,doseType,dose,doseUOM,weightInKg,includeSchema,null);
    }

    /**
    * 
    */
    public void doseCheckAsync(Credentials credentials, AccountRequest accountRequest, PatientRequest patientRequest, PatientInformationRequester patientInformationRequester, String drugId, String drugTypeId, String birthDateCCYYMMDD, String gender, String diagnosis, String doseType, String dose, String doseUOM, String weightInKg, String includeSchema, Object userState) throws Exception {
        if ((this.DoseCheckOperationCompleted == null))
        {
            this.DoseCheckOperationCompleted = new System.Threading.SendOrPostCallback(this.OnDoseCheckOperationCompleted);
        }
         
        this.InvokeAsync("DoseCheck", new Object[]{ credentials, accountRequest, patientRequest, patientInformationRequester, drugId, drugTypeId, birthDateCCYYMMDD, gender, diagnosis, doseType, dose, doseUOM, weightInKg, includeSchema }, this.DoseCheckOperationCompleted, userState);
    }

    private void onDoseCheckOperationCompleted(Object arg) throws Exception {
        if ((this.DoseCheckCompleted != null))
        {
            System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
            this.DoseCheckCompleted.invoke(this,new DoseCheckCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
        }
         
    }

    /**
    * 
    */
    public void cancelAsync(Object userState) throws Exception {
        super.CancelAsync(userState);
    }

    private boolean isLocalFileSystemWebService(String url) throws Exception {
        if (((url == null) || (StringSupport.equals(url, String.Empty))))
        {
            return false;
        }
         
        System.Uri wsUri = new System.Uri(url);
        if (((wsUri.Port >= 1024) && (String.Compare(wsUri.Host, "localHost", System.StringComparison.OrdinalIgnoreCase) == 0)))
        {
            return true;
        }
         
        return false;
    }

}


