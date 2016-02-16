//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum Permissions
{
    /**
    * A hard-coded list of permissions which may be granted to usergroups.0
    */
    None,
    /**
    * 1
    */
    AppointmentsModule,
    /**
    * 2
    */
    FamilyModule,
    /**
    * 3
    */
    AccountModule,
    /**
    * 4
    */
    TPModule,
    /**
    * 5
    */
    ChartModule,
    /**
    * 6
    */
    ImagesModule,
    /**
    * 7
    */
    ManageModule,
    /**
    * 8. Currently covers a wide variety of setup functions.
    */
    Setup,
    /**
    * 9
    */
    RxCreate,
    /**
    * 10. Uses date restrictions.  Covers editing AND deleting of completed procs.  Deleting non-completed procs is covered by ProcDelete.
    */
    ProcComplEdit,
    /**
    * 11
    */
    ChooseDatabase,
    /**
    * 12
    */
    Schedules,
    /**
    * 13
    */
    Blockouts,
    /**
    * 14. Uses date restrictions.
    */
    ClaimSentEdit,
    /**
    * 15
    */
    PaymentCreate,
    /**
    * 16. Uses date restrictions.
    */
    PaymentEdit,
    /**
    * 17
    */
    AdjustmentCreate,
    /**
    * 18. Uses date restrictions.
    */
    AdjustmentEdit,
    /**
    * 19
    */
    UserQuery,
    /**
    * 20.  Not used anymore.
    */
    StartupSingleUserOld,
    /**
    * 21 Not used anymore.
    */
    StartupMultiUserOld,
    /**
    * 22
    */
    Reports,
    /**
    * 23. Includes setting procedures complete.
    */
    ProcComplCreate,
    /**
    * 24. At least one user must have this permission.
    */
    SecurityAdmin,
    /**
    * 25.
    */
    AppointmentCreate,
    /**
    * 26
    */
    AppointmentMove,
    /**
    * 27
    */
    AppointmentEdit,
    /**
    * 28
    */
    Backup,
    /**
    * 29
    */
    TimecardsEditAll,
    /**
    * 30
    */
    DepositSlips,
    /**
    * 31. Uses date restrictions.
    */
    AccountingEdit,
    /**
    * 32. Uses date restrictions.
    */
    AccountingCreate,
    /**
    * 33
    */
    Accounting,
    /**
    * 34
    */
    AnesthesiaIntakeMeds,
    /**
    * 35
    */
    AnesthesiaControlMeds,
    /**
    * 36
    */
    InsPayCreate,
    /**
    * 37. Uses date restrictions. Edit Batch Insurance Payment.
    */
    InsPayEdit,
    /**
    * 38. Uses date restrictions.
    */
    TreatPlanEdit,
    /**
    * 39
    */
    ReportProdInc,
    /**
    * 40. Uses date restrictions.
    */
    TimecardDeleteEntry,
    /**
    * 41. Uses date restrictions. All other equipment functions are covered by .Setup.
    */
    EquipmentDelete,
    /**
    * 42. Uses date restrictions. Also used in audit trail to log web form importing.
    */
    SheetEdit,
    /**
    * 43. Uses date restrictions.
    */
    CommlogEdit,
    /**
    * 44. Uses date restrictions.
    */
    ImageDelete,
    /**
    * 45. Uses date restrictions.
    */
    PerioEdit,
    /**
    * 46. Shows the fee textbox in the proc edit window.
    */
    ProcEditShowFee,
    /**
    * 47
    */
    AdjustmentEditZero,
    /**
    * 48
    */
    EhrEmergencyAccess,
    /**
    * 49. Uses date restrictions.  This only applies to non-completed procs.  Deletion of completed procs is covered by ProcComplEdit.
    */
    ProcDelete,
    /**
    * 50 - Only used at OD HQ.  No user interface.
    */
    EhrKeyAdd,
    /**
    * 51
    */
    Providers,
    /**
    * 52
    */
    EcwAppointmentRevise,
    /**
    * 53
    */
    ProcedureNote,
    /**
    * 54
    */
    ReferralAdd,
    /**
    * 55
    */
    InsPlanChangeSubsc,
    /**
    * 56
    */
    RefAttachAdd,
    /**
    * 57
    */
    RefAttachDelete,
    /**
    * 58
    */
    CarrierCreate,
    /**
    * 59
    */
    ReportDashboard,
    /**
    * 60
    */
    AutoNoteQuickNoteEdit,
    /**
    * 61
    */
    EquipmentSetup,
    /**
    * 62
    */
    Billing,
    /**
    * 63
    */
    ProblemEdit,
    /**
    * 64- There is no user interface in the security window for this permission.  It is only used for tracking.  FK to CodeNum.
    */
    ProcFeeEdit,
    /**
    * 65- There is no user interface in the security window for this permission.  It is only used for tracking.  Only tracks changes to carriername, not any other carrier info.  FK to PlanNum for tracking.
    */
    InsPlanChangeCarrierName,
    /**
    * 66- When editing an existing task: delete the task, edit original description, or double click on note rows.  Even if you don't have the permission, you can still edit your own task description (but not the notes) as long as it's in your inbox and as long as nobody but you has added any notes.
    */
    TaskEdit,
    /**
    * 67- Add or delete lists and list columns..
    */
    WikiListSetup,
    /**
    * 68- There is no user interface in the security window for this permission.  It is only used for tracking.  Tracks copying of patient information.  Required by EHR.
    */
    Copy,
    /**
    * 69- There is no user interface in the security window for this permission.  It is only used for tracking.  Tracks printing of patient information.  Required by EHR.
    */
    Printing,
    /**
    * 70- There is no user interface in the security window for this permission.  It is only used for tracking.  Tracks viewing of patient medical information.
    */
    MedicalInfoViewed,
    /**
    * 71- There is no user interface in the security window for this permission.  It is only used for tracking.  Tracks creation and editing of patient problems.
    */
    PatProblemListEdit,
    /**
    * 72- There is no user interface in the security window for this permission.  It is only used for tracking.  Tracks creation and edting of patient medications.
    */
    PatMedicationListEdit,
    /**
    * 73- There is no user interface in the security window for this permission.  It is only used for tracking.  Tracks creation and editing of patient allergies.
    */
    PatAllergyListEdit,
    /**
    * 74- There is no user interface in the security window for this permission.  It is only used for tracking.  Tracks creation and editing of patient family health history.
    */
    PatFamilyHealthEdit,
    /**
    * //75- TODO description and verify name.  This is more like a user level preference than a permission.
    */
    //EhrShowCDS,
    /**
    * //76- TODO description and verify name.  This is more like a user level preference than a permission.
    */
    //EhrInfoButton,
    /**
    * 75- There is no user interface in the security window for this permission.  It is only used for tracking.  Patient Portal access of patient information.  Required by EHR.
    */
    PatientPortal
}

