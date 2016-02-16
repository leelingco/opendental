//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum InvalidType
{
    /**
    * When the autorefresh message is sent to the other computers, this is the type.0
    */
    None,
    /**
    * 1 Not used with any other flags
    */
    Date,
    /**
    * 2 Deprecated.  Inefficient.  All flags combined except Date and Tasks.
    */
    AllLocal,
    /**
    * 3 Not used with any other flags.  Used to just indicate added tasks, but now it indicates any change at all except those where a popup is needed.  If we also want a popup, then use TaskPopup.
    */
    Task,
    /**
    * 4
    */
    ProcCodes,
    /**
    * 5
    */
    Prefs,
    /**
    * 6 ApptViews, ApptViewItems, AppointmentRules, ProcApptColors.
    */
    Views,
    /**
    * 7
    */
    AutoCodes,
    /**
    * 8
    */
    Carriers,
    /**
    * 9
    */
    ClearHouses,
    /**
    * 10
    */
    Computers,
    /**
    * 11
    */
    InsCats,
    /**
    * 12- Also includes payperiods.
    */
    Employees,
    /**
    * 13- Deprecated.
    */
    StartupOld,
    /**
    * 14
    */
    Defs,
    /**
    * 15- Both templates and addresses.
    */
    Email,
    /**
    * 16
    */
    Fees,
    /**
    * 17
    */
    Letters,
    /**
    * 18
    */
    QuickPaste,
    /**
    * 19- Userod and UserGroup
    */
    Security,
    /**
    * 20
    */
    Programs,
    /**
    * 21- Also includes Image Mounts
    */
    ToolBut,
    /**
    * 22- Also includes clinics.
    */
    Providers,
    /**
    * 23
    */
    ClaimForms,
    /**
    * 24
    */
    ZipCodes,
    /**
    * 25
    */
    LetterMerge,
    /**
    * 26
    */
    DentalSchools,
    /**
    * 27
    */
    Operatories,
    /**
    * 28
    */
    TaskPopup,
    /**
    * 29
    */
    Sites,
    /**
    * 30
    */
    Pharmacies,
    /**
    * 31
    */
    Sheets,
    /**
    * 32
    */
    RecallTypes,
    /**
    * 33
    */
    FeeScheds,
    /**
    * 34. This is used internally by OD, Inc with the phonenumber table and the phone server.
    */
    PhoneNumbers,
    /**
    * 35. Signal/message defs
    */
    Signals,
    /**
    * 36
    */
    DisplayFields,
    /**
    * 37. And ApptFields.
    */
    PatFields,
    /**
    * 38
    */
    AccountingAutoPays,
    /**
    * 39
    */
    ProcButtons,
    /**
    * 40.  Includes ICD9s.
    */
    Diseases,
    /**
    * 41
    */
    Languages,
    /**
    * 42
    */
    AutoNotes,
    /**
    * 43
    */
    ElectIDs,
    /**
    * 44
    */
    Employers,
    /**
    * 45
    */
    ProviderIdents,
    /**
    * 46
    */
    ShutDownNow,
    /**
    * 47
    */
    InsFilingCodes,
    /**
    * 48
    */
    ReplicationServers,
    /**
    * 49
    */
    Automation,
    /**
    * 50. This is used internally by OD, Inc with the phone server to trigger the phone system to reload after changing which call groups users are in.
    */
    PhoneAsteriskReload,
    /**
    * 51
    */
    TimeCardRules,
    /**
    * 52. Includes DrugManufacturers and DrugUnits.
    */
    Vaccines,
    /**
    * 53. Includes all 4 HL7Def tables.
    */
    HL7Defs,
    /**
    * 54
    */
    DictCustoms,
    /**
    * 55. Caches the wiki master page and the wikiListHeaderWidths
    */
    Wiki,
    /**
    * 56. SourceOfPayment
    */
    Sops
}

