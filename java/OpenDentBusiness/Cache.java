//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.AccountingAutoPays;
import OpenDentBusiness.AppointmentRules;
import OpenDentBusiness.ApptFieldDefs;
import OpenDentBusiness.ApptViewItems;
import OpenDentBusiness.ApptViews;
import OpenDentBusiness.AutoCodeConds;
import OpenDentBusiness.AutoCodeItems;
import OpenDentBusiness.AutoCodes;
import OpenDentBusiness.Automations;
import OpenDentBusiness.AutoNoteControls;
import OpenDentBusiness.AutoNotes;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.ClaimFormItems;
import OpenDentBusiness.ClaimForms;
import OpenDentBusiness.Clearinghouses;
import OpenDentBusiness.Computers;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.CovSpans;
import OpenDentBusiness.Db;
import OpenDentBusiness.Defs;
import OpenDentBusiness.DictCustoms;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.DisplayFields;
import OpenDentBusiness.DrugManufacturers;
import OpenDentBusiness.DrugUnits;
import OpenDentBusiness.ElectIDs;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailTemplates;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Employers;
import OpenDentBusiness.Fees;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.HL7DefFields;
import OpenDentBusiness.HL7DefMessages;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.HL7DefSegments;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.InsFilingCodes;
import OpenDentBusiness.InsFilingCodeSubtypes;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Lans;
import OpenDentBusiness.LetterMergeFields;
import OpenDentBusiness.LetterMerges;
import OpenDentBusiness.Letters;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Operatories;
import OpenDentBusiness.PatFieldDefs;
import OpenDentBusiness.PayPeriods;
import OpenDentBusiness.Pharmacies;
import OpenDentBusiness.PIn;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Printers;
import OpenDentBusiness.ProcApptColors;
import OpenDentBusiness.ProcButtonItems;
import OpenDentBusiness.ProcButtons;
import OpenDentBusiness.ProcCodeNotes;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.ProviderIdents;
import OpenDentBusiness.Providers;
import OpenDentBusiness.QuickPasteCats;
import OpenDentBusiness.QuickPasteNotes;
import OpenDentBusiness.RecallTriggers;
import OpenDentBusiness.RecallTypes;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;
import OpenDentBusiness.SchoolClasses;
import OpenDentBusiness.SchoolCourses;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetFieldDefs;
import OpenDentBusiness.SigButDefs;
import OpenDentBusiness.SigElementDefs;
import OpenDentBusiness.Sites;
import OpenDentBusiness.Sops;
import OpenDentBusiness.TimeCardRules;
import OpenDentBusiness.ToolButItems;
import OpenDentBusiness.UserGroups;
import OpenDentBusiness.Userods;
import OpenDentBusiness.VaccineDefs;
import OpenDentBusiness.WikiListHeaderWidths;
import OpenDentBusiness.WikiPages;
import OpenDentBusiness.ZipCodes;

public class Cache   
{
    /**
    * This is only used in the RefreshCache methods.  Used instead of Meth.  The command is only used if not ClientWeb.
    */
    public static DataTable getTableRemotelyIfNeeded(MethodBase methodBase, String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.getTable(methodBase);
        }
        else
        {
            return Db.getTable(command);
        } 
    }

    /**
    * This is only called from the UI.  Its purpose is to refresh the cache for one type on both the workstation and server.  This can be used instead of DataValid.SetInvalid() when it is unnecessary to tell all the other workstations to refresh their cache.
    */
    public static void refresh(InvalidType itype) throws Exception {
        int intItype = ((Enum)itype).ordinal();
        RefreshCache(intItype.ToString());
    }

    /**
    * itypesStr= comma-delimited list of int.  Called directly from UI in one spot.  Called from above repeatedly.  The end result is that both server and client have been properly refreshed.
    */
    public static void refreshCache(String itypesStr) throws Exception {
        DataSet ds = getCacheDs(itypesStr);
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            //Because otherwise it was handled just fine as part of the GetCacheDs
            fillCache(ds,itypesStr);
        }
         
    }

    /**
    * This is an incomplete stub and should not be used very much yet.  This will get used when switching databases.  Switching databases is allowed from ClientWeb in the sense that the user can connect to a different server from the ChooseDatabase window.
    */
    public static void clearAllCache() throws Exception {
        //AccountingAutoPays
        AccountingAutoPays.clearCache();
        //AutoCodes
        AutoCodes.clearCache();
        AutoCodeItems.clearCache();
        AutoCodeConds.clearCache();
        //etc...
        Prefs.clearCache();
    }

    //etc...
    /**
    * If ClientWeb, then this method is instead run on the server, and the result passed back to the client.  And since it's ClientWeb, FillCache will be run on the client.
    */
    public static DataSet getCacheDs(String itypesStr) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetDS(MethodBase.GetCurrentMethod(), itypesStr);
        }
         
        //so this part below only happens if direct or server------------------------------------------------
        List<int> itypes = new List<int>();
        String[] strArray = itypesStr.Split(',');
        for (int i = 0;i < strArray.Length;i++)
        {
            itypes.Add(PIn.Int(strArray[i]));
        }
        boolean isAll = false;
        if (itypes.Contains(((Enum)InvalidType.AllLocal).ordinal()))
        {
            isAll = true;
        }
         
        DataSet ds = new DataSet();
        if (itypes.Contains(((Enum)InvalidType.AccountingAutoPays).ordinal()) || isAll)
        {
            ds.Tables.Add(AccountingAutoPays.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.AutoCodes).ordinal()) || isAll)
        {
            ds.Tables.Add(AutoCodes.refreshCache());
            ds.Tables.Add(AutoCodeItems.refreshCache());
            ds.Tables.Add(AutoCodeConds.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Automation).ordinal()) || isAll)
        {
            ds.Tables.Add(Automations.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.AutoNotes).ordinal()) || isAll)
        {
            ds.Tables.Add(AutoNotes.refreshCache());
            ds.Tables.Add(AutoNoteControls.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Carriers).ordinal()) || isAll)
        {
            ds.Tables.Add(Carriers.refreshCache());
        }
         
        //run on startup, after telephone reformat, after list edit.
        if (itypes.Contains(((Enum)InvalidType.ClaimForms).ordinal()) || isAll)
        {
            ds.Tables.Add(ClaimFormItems.refreshCache());
            ds.Tables.Add(ClaimForms.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.ClearHouses).ordinal()) || isAll)
        {
            ds.Tables.Add(Clearinghouses.refreshCache());
        }
         
        //kh wants to add an EasyHideClearHouses to disable this
        if (itypes.Contains(((Enum)InvalidType.Computers).ordinal()) || isAll)
        {
            ds.Tables.Add(Computers.refreshCache());
            ds.Tables.Add(Printers.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Defs).ordinal()) || isAll)
        {
            ds.Tables.Add(Defs.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.DentalSchools).ordinal()) || isAll)
        {
            ds.Tables.Add(SchoolClasses.refreshCache());
            ds.Tables.Add(SchoolCourses.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.DictCustoms).ordinal()) || isAll)
        {
            ds.Tables.Add(DictCustoms.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Diseases).ordinal()) || isAll)
        {
            ds.Tables.Add(DiseaseDefs.refreshCache());
            ds.Tables.Add(ICD9s.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.DisplayFields).ordinal()) || isAll)
        {
            ds.Tables.Add(DisplayFields.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.ElectIDs).ordinal()) || isAll)
        {
            ds.Tables.Add(ElectIDs.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Email).ordinal()) || isAll)
        {
            ds.Tables.Add(EmailAddresses.refreshCache());
            ds.Tables.Add(EmailTemplates.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Employees).ordinal()) || isAll)
        {
            ds.Tables.Add(Employees.refreshCache());
            ds.Tables.Add(PayPeriods.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Employers).ordinal()) || isAll)
        {
            ds.Tables.Add(Employers.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Fees).ordinal()) || isAll)
        {
            ds.Tables.Add(Fees.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.FeeScheds).ordinal()) || isAll)
        {
            ds.Tables.Add(FeeScheds.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.HL7Defs).ordinal()) || isAll)
        {
            ds.Tables.Add(HL7Defs.refreshCache());
            ds.Tables.Add(HL7DefMessages.refreshCache());
            ds.Tables.Add(HL7DefSegments.refreshCache());
            ds.Tables.Add(HL7DefFields.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.InsCats).ordinal()) || isAll)
        {
            ds.Tables.Add(CovCats.refreshCache());
            ds.Tables.Add(CovSpans.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.InsFilingCodes).ordinal()) || isAll)
        {
            ds.Tables.Add(InsFilingCodes.refreshCache());
            ds.Tables.Add(InsFilingCodeSubtypes.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Languages).ordinal()) || isAll)
        {
            if (!StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
            {
                ds.Tables.Add(Lans.refreshCache());
            }
             
        }
         
        if (itypes.Contains(((Enum)InvalidType.Letters).ordinal()) || isAll)
        {
            ds.Tables.Add(Letters.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.LetterMerge).ordinal()) || isAll)
        {
            ds.Tables.Add(LetterMergeFields.refreshCache());
            ds.Tables.Add(LetterMerges.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Operatories).ordinal()) || isAll)
        {
            ds.Tables.Add(Operatories.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.PatFields).ordinal()) || isAll)
        {
            ds.Tables.Add(PatFieldDefs.refreshCache());
            ds.Tables.Add(ApptFieldDefs.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Pharmacies).ordinal()) || isAll)
        {
            ds.Tables.Add(Pharmacies.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Prefs).ordinal()) || isAll)
        {
            ds.Tables.Add(Prefs.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.ProcButtons).ordinal()) || isAll)
        {
            ds.Tables.Add(ProcButtons.refreshCache());
            ds.Tables.Add(ProcButtonItems.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.ProcCodes).ordinal()) || isAll)
        {
            ds.Tables.Add(ProcedureCodes.refreshCache());
            ds.Tables.Add(ProcCodeNotes.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Programs).ordinal()) || isAll)
        {
            ds.Tables.Add(Programs.refreshCache());
            ds.Tables.Add(ProgramProperties.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.ProviderIdents).ordinal()) || isAll)
        {
            ds.Tables.Add(ProviderIdents.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Providers).ordinal()) || isAll)
        {
            ds.Tables.Add(Providers.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.QuickPaste).ordinal()) || isAll)
        {
            ds.Tables.Add(QuickPasteNotes.refreshCache());
            ds.Tables.Add(QuickPasteCats.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.RecallTypes).ordinal()) || isAll)
        {
            ds.Tables.Add(RecallTypes.refreshCache());
            ds.Tables.Add(RecallTriggers.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.ReplicationServers).ordinal()) || isAll)
        {
            ds.Tables.Add(ReplicationServers.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Security).ordinal()) || isAll)
        {
            ds.Tables.Add(Userods.refreshCache());
            ds.Tables.Add(UserGroups.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Sheets).ordinal()) || isAll)
        {
            ds.Tables.Add(SheetDefs.refreshCache());
            ds.Tables.Add(SheetFieldDefs.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Signals).ordinal()) || isAll)
        {
            ds.Tables.Add(SigElementDefs.refreshCache());
            ds.Tables.Add(SigButDefs.refreshCache());
        }
         
        //includes SigButDefElements.Refresh()
        if (itypes.Contains(((Enum)InvalidType.Sites).ordinal()) || isAll)
        {
            ds.Tables.Add(Sites.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Sops).ordinal()) || isAll)
        {
            ds.Tables.Add(Sops.refreshCache());
        }
         
        //InvalidTypes.Tasks not handled here.
        if (itypes.Contains(((Enum)InvalidType.TimeCardRules).ordinal()) || isAll)
        {
            ds.Tables.Add(TimeCardRules.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.ToolBut).ordinal()) || isAll)
        {
            ds.Tables.Add(ToolButItems.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Vaccines).ordinal()) || isAll)
        {
            ds.Tables.Add(VaccineDefs.refreshCache());
            ds.Tables.Add(DrugManufacturers.refreshCache());
            ds.Tables.Add(DrugUnits.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Views).ordinal()) || isAll)
        {
            ds.Tables.Add(ApptViews.refreshCache());
            ds.Tables.Add(ApptViewItems.refreshCache());
            ds.Tables.Add(AppointmentRules.refreshCache());
            ds.Tables.Add(ProcApptColors.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.Wiki).ordinal()) || isAll)
        {
            ds.Tables.Add(WikiListHeaderWidths.refreshCache());
            ds.Tables.Add(WikiPages.refreshCache());
        }
         
        if (itypes.Contains(((Enum)InvalidType.ZipCodes).ordinal()) || isAll)
        {
            ds.Tables.Add(ZipCodes.refreshCache());
        }
         
        return ds;
    }

    /**
    * only if ClientWeb
    */
    public static void fillCache(DataSet ds, String itypesStr) throws Exception {
        List<int> itypes = new List<int>();
        String[] strArray = itypesStr.Split(',');
        for (int i = 0;i < strArray.Length;i++)
        {
            itypes.Add(PIn.Int(strArray[i]));
        }
        boolean isAll = false;
        if (itypes.Contains(((Enum)InvalidType.AllLocal).ordinal()))
        {
            isAll = true;
        }
         
        if (itypes.Contains(((Enum)InvalidType.AccountingAutoPays).ordinal()) || isAll)
        {
            AccountingAutoPays.FillCache(ds.Tables["AccountingAutoPay"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.AutoCodes).ordinal()) || isAll)
        {
            AutoCodes.FillCache(ds.Tables["AutoCode"]);
            AutoCodeItems.FillCache(ds.Tables["AutoCodeItem"]);
            AutoCodeConds.FillCache(ds.Tables["AutoCodeCond"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Automation).ordinal()) || isAll)
        {
            Automations.FillCache(ds.Tables["Automation"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.AutoNotes).ordinal()) || isAll)
        {
            AutoNotes.FillCache(ds.Tables["AutoNote"]);
            AutoNoteControls.FillCache(ds.Tables["AutoNoteControl"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Carriers).ordinal()) || isAll)
        {
            Carriers.FillCache(ds.Tables["Carrier"]);
        }
         
        //run on startup, after telephone reformat, after list edit.
        if (itypes.Contains(((Enum)InvalidType.ClaimForms).ordinal()) || isAll)
        {
            ClaimFormItems.FillCache(ds.Tables["ClaimFormItem"]);
            ClaimForms.FillCache(ds.Tables["ClaimForm"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.ClearHouses).ordinal()) || isAll)
        {
            Clearinghouses.FillCache(ds.Tables["Clearinghouse"]);
        }
         
        //kh wants to add an EasyHideClearHouses to disable this
        if (itypes.Contains(((Enum)InvalidType.Computers).ordinal()) || isAll)
        {
            Computers.FillCache(ds.Tables["Computer"]);
            Printers.FillCache(ds.Tables["Printer"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Defs).ordinal()) || isAll)
        {
            Defs.FillCache(ds.Tables["Def"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.DentalSchools).ordinal()) || isAll)
        {
            SchoolClasses.FillCache(ds.Tables["SchoolClass"]);
            SchoolCourses.FillCache(ds.Tables["SchoolCourse"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.DictCustoms).ordinal()) || isAll)
        {
            DictCustoms.FillCache(ds.Tables["DictCustom"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Diseases).ordinal()) || isAll)
        {
            DiseaseDefs.FillCache(ds.Tables["DiseaseDef"]);
            ICD9s.FillCache(ds.Tables["ICD9"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.DisplayFields).ordinal()) || isAll)
        {
            DisplayFields.FillCache(ds.Tables["DisplayField"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.ElectIDs).ordinal()) || isAll)
        {
            ElectIDs.FillCache(ds.Tables["ElectID"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Email).ordinal()) || isAll)
        {
            EmailAddresses.FillCache(ds.Tables["EmailAddress"]);
            EmailTemplates.FillCache(ds.Tables["EmailTemplate"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Employees).ordinal()) || isAll)
        {
            Employees.FillCache(ds.Tables["Employee"]);
            PayPeriods.FillCache(ds.Tables["PayPeriod"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Employers).ordinal()) || isAll)
        {
            Employers.FillCache(ds.Tables["Employer"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Fees).ordinal()) || isAll)
        {
            Fees.FillCache(ds.Tables["Fee"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.FeeScheds).ordinal()) || isAll)
        {
            FeeScheds.FillCache(ds.Tables["FeeSched"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.HL7Defs).ordinal()) || isAll)
        {
            HL7Defs.FillCache(ds.Tables["HL7Def"]);
            HL7DefMessages.FillCache(ds.Tables["HL7DefMessage"]);
            HL7DefSegments.FillCache(ds.Tables["HL7DefSegment"]);
            HL7DefFields.FillCache(ds.Tables["HL7DefField"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.InsCats).ordinal()) || isAll)
        {
            CovCats.FillCache(ds.Tables["CovCat"]);
            CovSpans.FillCache(ds.Tables["CovSpan"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.InsFilingCodes).ordinal()) || isAll)
        {
            InsFilingCodes.FillCache(ds.Tables["InsFilingCode"]);
            InsFilingCodeSubtypes.FillCache(ds.Tables["InsFilingCodeSubtype"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Languages).ordinal()) || isAll)
        {
            Lans.FillCache(ds.Tables["Language"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Letters).ordinal()) || isAll)
        {
            Letters.FillCache(ds.Tables["Letter"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.LetterMerge).ordinal()) || isAll)
        {
            LetterMergeFields.FillCache(ds.Tables["LetterMergeField"]);
            LetterMerges.FillCache(ds.Tables["LetterMerge"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Operatories).ordinal()) || isAll)
        {
            Operatories.FillCache(ds.Tables["Operatory"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.PatFields).ordinal()) || isAll)
        {
            PatFieldDefs.FillCache(ds.Tables["PatFieldDef"]);
            ApptFieldDefs.FillCache(ds.Tables["ApptFieldDef"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Pharmacies).ordinal()) || isAll)
        {
            Pharmacies.FillCache(ds.Tables["Pharmacy"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Prefs).ordinal()) || isAll)
        {
            Prefs.FillCache(ds.Tables["Pref"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.ProcButtons).ordinal()) || isAll)
        {
            ProcButtons.FillCache(ds.Tables["ProcButton"]);
            ProcButtonItems.FillCache(ds.Tables["ProcButtonItem"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.ProcCodes).ordinal()) || isAll)
        {
            ProcedureCodes.FillCache(ds.Tables["ProcedureCode"]);
            ProcCodeNotes.FillCache(ds.Tables["ProcCodeNote"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Programs).ordinal()) || isAll)
        {
            Programs.FillCache(ds.Tables["Program"]);
            ProgramProperties.FillCache(ds.Tables["ProgramProperty"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.ProviderIdents).ordinal()) || isAll)
        {
            ProviderIdents.FillCache(ds.Tables["ProviderIdent"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Providers).ordinal()) || isAll)
        {
            Providers.FillCache(ds.Tables["Provider"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.QuickPaste).ordinal()) || isAll)
        {
            QuickPasteNotes.FillCache(ds.Tables["QuickPasteNote"]);
            QuickPasteCats.FillCache(ds.Tables["QuickPasteCat"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.RecallTypes).ordinal()) || isAll)
        {
            RecallTypes.FillCache(ds.Tables["RecallType"]);
            RecallTriggers.FillCache(ds.Tables["RecallTrigger"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.ReplicationServers).ordinal()) || isAll)
        {
            ReplicationServers.FillCache(ds.Tables["ReplicationServer"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Security).ordinal()) || isAll)
        {
            Userods.FillCache(ds.Tables["Userod"]);
            UserGroups.FillCache(ds.Tables["UserGroup"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Sheets).ordinal()) || isAll)
        {
            SheetDefs.FillCache(ds.Tables["SheetDef"]);
            SheetFieldDefs.FillCache(ds.Tables["SheetFieldDef"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Signals).ordinal()) || isAll)
        {
            SigElementDefs.FillCache(ds.Tables["SigElementDef"]);
            SigButDefs.FillCache(ds.Tables["SigButDef"]);
        }
         
        //includes SigButDefElements.Refresh()
        if (itypes.Contains(((Enum)InvalidType.Sites).ordinal()) || isAll)
        {
            Sites.FillCache(ds.Tables["Site"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Sops).ordinal()) || isAll)
        {
            Sops.FillCache(ds.Tables["Sop"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.TimeCardRules).ordinal()) || isAll)
        {
            TimeCardRules.FillCache(ds.Tables["TimeCardRule"]);
        }
         
        //InvalidTypes.Tasks not handled here.
        if (itypes.Contains(((Enum)InvalidType.ToolBut).ordinal()) || isAll)
        {
            ToolButItems.FillCache(ds.Tables["ToolButItem"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Vaccines).ordinal()) || isAll)
        {
            VaccineDefs.FillCache(ds.Tables["VaccineDef"]);
            DrugManufacturers.FillCache(ds.Tables["DrugManufacturer"]);
            DrugUnits.FillCache(ds.Tables["DrugUnit"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Views).ordinal()) || isAll)
        {
            ApptViews.FillCache(ds.Tables["ApptView"]);
            ApptViewItems.FillCache(ds.Tables["ApptViewItem"]);
            AppointmentRules.FillCache(ds.Tables["AppointmentRule"]);
            ProcApptColors.FillCache(ds.Tables["ProcApptColor"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.Wiki).ordinal()) || isAll)
        {
            WikiListHeaderWidths.FillCache(ds.Tables["WikiListHeaderWidth"]);
            WikiPages.FillCache(ds.Tables["WikiPage"]);
        }
         
        if (itypes.Contains(((Enum)InvalidType.ZipCodes).ordinal()) || isAll)
        {
            ZipCodes.FillCache(ds.Tables["ZipCode"]);
        }
         
    }

}


