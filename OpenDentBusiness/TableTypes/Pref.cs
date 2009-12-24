using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;

namespace OpenDentBusiness {
	///<summary>Stores small bits of data for a wide variety of purposes.  Any data that's too small to warrant its own table will usually end up here.</summary>
	public class Pref {
		///<summary>Primary key.</summary>
		public string PrefName;//
		///<summary>The stored value.</summary>
		public string ValueString;
		///<summary>Documentation on usage and values of each pref.</summary>
		public string Comments;
	}

	///<summary>Because this enum is stored in the database as strings rather than as numbers, we can do the order alphabetically and we can change it whenever we want.</summary>
	public enum PrefName {
		///<summary></summary>
		AccountingCashIncomeAccount,
		///<summary></summary>
		AccountingDepositAccounts,
		///<summary></summary>
		AccountingIncomeAccount,
		///<summary></summary>
		AccountingLockDate,
		///<summary></summary>
		ADAComplianceDateTime,
		///<summary></summary>
		ADAdescriptionsReset,
		AgingCalculatedMonthlyInsteadOfDaily,
		AllowedFeeSchedsAutomate,
		AllowSettingProcsComplete,
		AppointmentBubblesDisabled,
		AppointmentTimeArrivedTrigger,
		AppointmentTimeDismissedTrigger,
		AppointmentTimeIncrement,
		AppointmentTimeSeatedTrigger,
		ApptBubbleDelay,
		ApptExclamationShowForUnsentIns,
		ApptModuleRefreshesEveryMinute,
		AtoZfolderNotRequired,
		AutoResetTPEntryStatus,
		BackupExcludeImageFolder,
		BackupFromPath,
		BackupReminderLastDateRun,
		BackupRestoreAtoZToPath,
		BackupRestoreFromPath,
		BackupRestoreToPath,
		BackupToPath,
		BalancesDontSubtractIns,
		BankAddress,
		BankRouting,
		BillingAgeOfAccount,
		BillingChargeAdjustmentType,
		BillingChargeAmount,
		BillingChargeLastRun,
		///<summary>Value is a string, either Billing or Finance.</summary>
		BillingChargeOrFinanceIsDefault,
		BillingDefaultsIntermingle,
		BillingDefaultsLastDays,
		BillingDefaultsNote,
		BillingElectClientAcctNumber,
		BillingElectCreditCardChoices,
		BillingElectPassword,
		BillingElectUserName,
		BillingElectVendorId,
		BillingElectVendorPMSCode,
		BillingExcludeBadAddresses,
		BillingExcludeIfUnsentProcs,
		BillingExcludeInactive,
		BillingExcludeInsPending,
		BillingExcludeLessThan,
		BillingExcludeNegative,
		BillingIgnoreInPerson,
		BillingIncludeChanged,
		BillingSelectBillingTypes,
		BillingUseElectronic,
		BirthdayPostcardMsg,
		BrokenAppointmentAdjustmentType,
		BrokenApptCommLogNotAdjustment,
		ChartQuickAddHideAmalgam,
		ClaimAttachExportPath,
		ClaimFormTreatDentSaysSigOnFile,
		ClaimsValidateACN,
		ConfirmEmailMessage,
		ConfirmEmailSubject,
		ConfirmPostcardMessage,
		///<summary>FK to definition.DefNum.  Initially 0.</summary>
		ConfirmStatusEmailed,
		CoPay_FeeSchedule_BlankLikeZero,
		CorruptedDatabase,
		CropDelta,
		CustomizedForPracticeWeb,
		DatabaseConvertedForMySql41,
		DataBaseVersion,
		DateDepositsStarted,
		DateLastAging,
		DefaultClaimForm,
		DefaultProcedurePlaceService,
		DistributorKey,
		DockPhonePanelShow,
		DocPath,
		EasyBasicModules,
		EasyHideAdvancedIns,
		EasyHideCapitation,
		EasyHideClinical,
		EasyHideDentalSchools,
		EasyHideHospitals,
		EasyHideInsurance,
		EasyHideMedicaid,
		EasyHidePrinters,
		EasyHidePublicHealth,
		EasyHideRepeatCharges,
		EasyNoClinics,
		EclaimsSeparateTreatProv,
		EmailPassword,
		EmailPort,
		EmailSenderAddress,
		EmailSMTPserver,
		EmailUsername,
		EmailUseSSL,
		EnableAnesthMod,
		ExportPath,
		FinanceChargeAdjustmentType,
		FinanceChargeAPR,
		FinanceChargeLastRun,
		FuchsListSelectionColor,
		FuchsOptionsOn,
		GenericEClaimsForm,
		HL7FolderOut,
		HL7FolderIn,
		ImageStoreIsDatabase,
		ImageWindowingMax,
		ImageWindowingMin,
		InsBillingProv,
		InsDefaultPPOpercent,
		InsDefaultShowUCRonClaims,
		InsurancePlansShared,
		IntermingleFamilyDefault,
		LabelPatientDefaultSheetDefNum,
		LanguagesUsedByPatients,
		LetterMergePath,
		MainWindowTitle,
		MedicalEclaimsEnabled,
		MobileSyncDateTimeLastRun,
		MobileSyncLastFileNumber,
		MobileSyncPath,
		OpenDentalVendor,
		OracleInsertId,
		PatientSelectUsesSearchButton,
		PayPlansBillInAdvanceDays,
		PerioColorCAL,
		PerioColorFurcations,
		PerioColorFurcationsRed,
		PerioColorGM,
		PerioColorMGJ,
		PerioColorProbing,
		PerioColorProbingRed,
		PerioRedCAL,
		PerioRedFurc,
		PerioRedGing,
		PerioRedMGJ,
		PerioRedMob,
		PerioRedProb,
		PlannedApptTreatedAsRegularAppt,
		PracticeAddress,
		PracticeAddress2,
		PracticeBankNumber,
		PracticeBillingAddress,
		PracticeBillingAddress2,
		PracticeBillingCity,
		PracticeBillingST,
		PracticeBillingZip,
		PracticeCity,
		PracticeDefaultBillType,
		PracticeDefaultProv,
		PracticePhone,
		PracticeST,
		PracticeTitle,
		PracticeZip,
		ProcessSigsIntervalInSecs,
		ProgramVersion,
		ProviderIncomeTransferShows,
		RandomPrimaryKeys,
		RecallAdjustDown,
		RecallAdjustRight,
		RecallCardsShowReturnAdd,
		///<summary>-1 indicates min for all dates</summary>
		RecallDaysFuture,
		///<summary>-1 indicates min for all dates</summary>
		RecallDaysPast,
		RecallEmailFamMsg,
		RecallEmailFamMsg2,
		RecallEmailFamMsg3,
		RecallEmailMessage,
		RecallEmailMessage2,
		RecallEmailMessage3,
		RecallEmailSubject,
		RecallEmailSubject2,
		RecallEmailSubject3,
		RecallGroupByFamily,
		RecallMaxNumberReminders,
		RecallPostcardFamMsg,
		RecallPostcardFamMsg2,
		RecallPostcardFamMsg3,
		RecallPostcardMessage,
		RecallPostcardMessage2,
		RecallPostcardMessage3,
		RecallPostcardsPerSheet,
		RecallShowIfDaysFirstReminder,
		RecallShowIfDaysSecondReminder,
		RecallStatusEmailed,
		RecallStatusMailed,
		RecallTypeSpecialChildProphy,
		RecallTypeSpecialPerio,
		RecallTypeSpecialProphy,
		///<summary>Comma-delimited list. FK to recalltype.RecallTypeNum.</summary>
		RecallTypesShowingInList,
		RegistrationKey,
		RegistrationKeyIsDisabled,
		RegistrationNumberClaim,
		RenaissanceLastBatchNumber,
		ReportFolderName,
		SalesTaxPercentage,
		ScannerCompression,
		ScannerCompressionPhotos,
		ScannerCompressionRadiographs,
		ScheduleProvUnassigned,
		SecurityLockDate,
		SecurityLockIncludesAdmin,
		ShowAccountFamilyCommEntries,
		ShowFeatureMedicalInsurance,
		ShowIDinTitleBar,
		ShowProgressNotesInsteadofCommLog,
		ShowUrgFinNoteInProgressNotes,
		SolidBlockouts,
		StatementAccountsUseChartNumber,
		StatementsCalcDueDate,
		StatementShowCreditCard,
		StatementShowNotes,
		StatementShowProcBreakdown,
		StatementShowReturnAddress,
		StatementSummaryShowInsInfo,
		StoreCCnumbers,
		TaskAncestorsAllSetInVersion55,
		TaskListAlwaysShowsAtBottom,
		TasksCheckOnStartup,
		TerminalClosePassword,
		TimecardSecurityEnabled,
		TimecardUsersDontEditOwnCard,
		TitleBarShowSite,
		TreatmentPlanNote,
		TreatPlanPriorityForDeclined,
		TreatPlanShowCompleted,
		TreatPlanShowGraphics,
		TreatPlanShowIns,
		TrojanExpressCollectBillingType,
		TrojanExpressCollectPassword,
		TrojanExpressCollectPath,
		TrojanExpressCollectPreviousFileNumber,
		UpdateCode,
		UpdateInProgressOnComputerName,
		UpdateMultipleDatabases,
		UpdateServerAddress,
		UpdateWebProxyAddress,
		UpdateWebProxyPassword,
		UpdateWebProxyUserName,
		UpdateWebsitePath,
		UpdateWindowShowsClassicView,
		UseBillingAddressOnClaims,
		///<summary>Enum:ToothNumberingNomenclature 0=Universal(American), 1=FDI, 2=Haderup, 3=Palmer</summary>
		UseInternationalToothNumbers,
		WebServiceServerName,
		WordProcessorPath,
		XRayExposureLevel
	}

	



}
