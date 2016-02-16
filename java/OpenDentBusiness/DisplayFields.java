//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:39 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFieldC;
import OpenDentBusiness.DisplayFieldCategory;
import OpenDentBusiness.DisplayFields;
import OpenDentBusiness.Meth;
import OpenDentBusiness.OrthoChart;
import OpenDentBusiness.OrthoCharts;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Programs;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class DisplayFields   
{
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM displayfield ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "DisplayField";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        DisplayFieldC.setListt(Crud.DisplayFieldCrud.TableToList(table));
    }

    /**
    * 
    */
    public static long insert(DisplayField field) throws Exception {
        return Crud.DisplayFieldCrud.Insert(field);
    }

    /*
    		///<summary></summary>
    		public static void Update(DisplayField field) {			
    			string command="UPDATE displayfield SET "
    			+"DisplayFieldName = '"+POut.PString(DisplayField.DisplayFieldName)+"', "
    			+"ControlsToInc = '"+POut.PString(DisplayField.ControlsToInc)+"' "
    			+"WHERE DisplayFieldNum = '"+POut.PInt(DisplayField.DisplayFieldNum)+"'";
    			Db.NonQ(command);
    		}
    		*/
    /**
    * 
    */
    public static void deleteForChartView(long chartViewNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), chartViewNum);
            return ;
        }
         
        String command = "DELETE FROM displayfield WHERE ChartViewNum = " + POut.long(chartViewNum);
        Db.nonQ(command);
    }

    /**
    * Returns an ordered list for just one category.  Do not use with None, or it will malfunction.  These are display fields that the user has entered, which are stored in the db, and then are pulled into the cache.
    */
    public static List<DisplayField> getForCategory(DisplayFieldCategory category) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<DisplayField> retVal = new List<DisplayField>();
        for (int i = 0;i < DisplayFieldC.getListt().Count;i++)
        {
            if (DisplayFieldC.getListt()[i].Category == category)
            {
                retVal.Add(DisplayFieldC.getListt()[i].Copy());
            }
             
        }
        if (retVal.Count == 0)
        {
            return DisplayFields.getDefaultList(category);
        }
         
        return retVal;
    }

    //default
    /**
    * Returns an ordered list for just one chart view
    */
    public static List<DisplayField> getForChartView(long ChartViewNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<DisplayField> retVal = new List<DisplayField>();
        for (int i = 0;i < DisplayFieldC.getListt().Count;i++)
        {
            if (DisplayFieldC.getListt()[i].ChartViewNum == ChartViewNum && DisplayFieldC.getListt()[i].Category == DisplayFieldCategory.None)
            {
                retVal.Add(DisplayFieldC.getListt()[i].Copy());
            }
             
        }
        if (retVal.Count == 0)
        {
            return DisplayFields.getDefaultList(DisplayFieldCategory.None);
        }
         
        return retVal;
    }

    //default
    public static List<DisplayField> getDefaultList(DisplayFieldCategory category) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<DisplayField> list = new List<DisplayField>();
        if (category == DisplayFieldCategory.None)
        {
            list.Add(new DisplayField("Date",67,category));
            //list.Add(new DisplayField("Time",40));
            list.Add(new DisplayField("Th",27,category));
            list.Add(new DisplayField("Surf",40,category));
            list.Add(new DisplayField("Dx",28,category));
            list.Add(new DisplayField("Description",218,category));
            list.Add(new DisplayField("Stat",25,category));
            list.Add(new DisplayField("Prov",42,category));
            list.Add(new DisplayField("Amount",48,category));
            list.Add(new DisplayField("ADA Code",62,category));
            list.Add(new DisplayField("User",62,category));
            list.Add(new DisplayField("Signed",55,category));
        }
        else //list.Add(new DisplayField("Priority",65,category));
        //list.Add(new DisplayField("Date TP",67,category));
        //list.Add(new DisplayField("Date Entry",67,category));
        //list.Add(new DisplayField("Prognosis",60,category));
        //list.Add(new DisplayField("Length",40,category));
        //list.Add(new DisplayField("Abbr",50,category));
        //list.Add(new DisplayField("Locked",50,category));
        //if(Programs.UsingOrion){
        //list.Add(new DisplayField("DPC",33,category));
        //list.Add(new DisplayField("Schedule By",72,category));
        //list.Add(new DisplayField("Stop Clock",67,category));
        //list.Add(new DisplayField("Stat 2",36,category));
        //list.Add(new DisplayField("On Call",45,category));
        //list.Add(new DisplayField("Effective Comm",90,category));
        //list.Add(new DisplayField("End Time",56,category));
        //list.Add(new DisplayField("Quadrant",55,category));
        //list.Add(new DisplayField("DPCpost",52,category));
        //}
        if (category == DisplayFieldCategory.PatientSelect)
        {
            list.Add(new DisplayField("LastName",75,category));
            list.Add(new DisplayField("First Name",75,category));
            //list.Add(new DisplayField("MI",25,category));
            list.Add(new DisplayField("Pref Name",60,category));
            list.Add(new DisplayField("Age",30,category));
            list.Add(new DisplayField("SSN",65,category));
            list.Add(new DisplayField("Hm Phone",90,category));
            list.Add(new DisplayField("Wk Phone",90,category));
            if (PrefC.getBool(PrefName.DistributorKey))
            {
            }
             
            //if for OD HQ
            //list.Add(new DisplayField("OtherPhone",90,category));
            list.Add(new DisplayField("PatNum",80,category));
            //list.Add(new DisplayField("ChartNum",60,category));
            list.Add(new DisplayField("Address",100,category));
            list.Add(new DisplayField("Status",65,category));
        }
        else //list.Add(new DisplayField("Bill Type",90,category));
        //list.Add(new DisplayField("City",80,category));
        //list.Add(new DisplayField("State",55,category));
        //list.Add(new DisplayField("Pri Prov",85,category));
        //list.Add(new DisplayField("Birthdate",70,category));
        //list.Add(new DisplayField("Site",90,category));
        //list.Add(new DisplayField("Email",90,category));
        if (category == DisplayFieldCategory.PatientInformation)
        {
            list.Add(new DisplayField("Last",0,category));
            list.Add(new DisplayField("First",0,category));
            list.Add(new DisplayField("Middle",0,category));
            list.Add(new DisplayField("Preferred",0,category));
            list.Add(new DisplayField("Title",0,category));
            list.Add(new DisplayField("Salutation",0,category));
            list.Add(new DisplayField("Status",0,category));
            list.Add(new DisplayField("Gender",0,category));
            list.Add(new DisplayField("Position",0,category));
            list.Add(new DisplayField("Birthdate",0,category));
            list.Add(new DisplayField("Age",0,category));
            list.Add(new DisplayField("SS#",0,category));
            list.Add(new DisplayField("Address",0,category));
            list.Add(new DisplayField("Address2",0,category));
            list.Add(new DisplayField("City",0,category));
            list.Add(new DisplayField("State",0,category));
            if (PrefC.getBool(PrefName.DockPhonePanelShow))
            {
                list.Add(new DisplayField("Country",0,category));
            }
             
            list.Add(new DisplayField("Zip",0,category));
            list.Add(new DisplayField("Hm Phone",0,category));
            list.Add(new DisplayField("Wk Phone",0,category));
            list.Add(new DisplayField("Wireless Ph",0,category));
            list.Add(new DisplayField("E-mail",0,category));
            list.Add(new DisplayField("Contact Method",0,category));
            list.Add(new DisplayField("ABC0",0,category));
            //list.Add(new DisplayField("Chart Num",0,category));
            list.Add(new DisplayField("Billing Type",0,category));
            //list.Add(new DisplayField("Ward",0,category));
            //list.Add(new DisplayField("AdmitDate",0,category));
            list.Add(new DisplayField("Primary Provider",0,category));
            list.Add(new DisplayField("Sec. Provider",0,category));
            list.Add(new DisplayField("Payor Types",0,category));
            list.Add(new DisplayField("Language",0,category));
            //list.Add(new DisplayField("Clinic",0,category));
            //list.Add(new DisplayField("ResponsParty",0,category));
            list.Add(new DisplayField("Referrals",0,category));
            list.Add(new DisplayField("Addr/Ph Note",0,category));
            list.Add(new DisplayField("PatFields",0,category));
            //list.Add(new DisplayField("Guardians",0,category));
            //list.Add(new DisplayField("Arrive Early",0,category));
            //list.Add(new DisplayField("Super Head",0,category));
            if (PrefC.getBool(PrefName.DistributorKey))
            {
                list.Add(new DisplayField("References",0,category));
            }
             
        }
        else if (category == DisplayFieldCategory.AccountModule)
        {
            list.Add(new DisplayField("Date",65,category));
            list.Add(new DisplayField("Patient",100,category));
            list.Add(new DisplayField("Prov",40,category));
            //list.Add(new DisplayField("Clinic",50,category));
            list.Add(new DisplayField("Code",46,category));
            list.Add(new DisplayField("Tth",26,category));
            list.Add(new DisplayField("Description",270,category));
            list.Add(new DisplayField("Charges",60,category));
            list.Add(new DisplayField("Credits",60,category));
            list.Add(new DisplayField("Balance",60,category));
        }
        else if (category == DisplayFieldCategory.RecallList)
        {
            list.Add(new DisplayField("Due Date",75,category));
            list.Add(new DisplayField("Patient",120,category));
            list.Add(new DisplayField("Age",30,category));
            list.Add(new DisplayField("Type",60,category));
            list.Add(new DisplayField("Interval",50,category));
            list.Add(new DisplayField("#Remind",55,category));
            list.Add(new DisplayField("LastRemind",75,category));
            list.Add(new DisplayField("Contact",120,category));
            list.Add(new DisplayField("Status",130,category));
            list.Add(new DisplayField("Note",215,category));
        }
        else //list.Add(new DisplayField("BillingType",100,category));
        if (category == DisplayFieldCategory.ChartPatientInformation)
        {
            list.Add(new DisplayField("Age",0,category));
            list.Add(new DisplayField("ABC0",0,category));
            list.Add(new DisplayField("Billing Type",0,category));
            list.Add(new DisplayField("Referred From",0,category));
            list.Add(new DisplayField("Date First Visit",0,category));
            list.Add(new DisplayField("Prov. (Pri, Sec)",0,category));
            list.Add(new DisplayField("Pri Ins",0,category));
            list.Add(new DisplayField("Sec Ins",0,category));
            list.Add(new DisplayField("Payor Types",0,category));
            if (PrefC.getBool(PrefName.DistributorKey))
            {
                list.Add(new DisplayField("Registration Keys",0,category));
                list.Add(new DisplayField("Ehr Provider Keys",0,category));
                list.Add(new DisplayField("References",0,category));
            }
             
            //different default list for eCW:
            if (!Programs.usingEcwTightOrFullMode())
            {
                list.Add(new DisplayField("Premedicate",0,category));
                list.Add(new DisplayField("Problems",0,category));
                list.Add(new DisplayField("Med Urgent",0,category));
                list.Add(new DisplayField("Medical Summary",0,category));
                list.Add(new DisplayField("Service Notes",0,category));
                list.Add(new DisplayField("Medications",0,category));
                list.Add(new DisplayField("Allergies",0,category));
            }
             
        }
        else //list.Add(new DisplayField("PatFields",0,category));
        //list.Add(new DisplayField("Birthdate",0,category));
        //list.Add(new DisplayField("City",0,category));
        //list.Add(new DisplayField("AskToArriveEarly",0,category));
        //list.Add(new DisplayField("Super Head",0,category));
        //list.Add(new DisplayField("Patient Portal",0,category));
        if (category == DisplayFieldCategory.ProcedureGroupNote)
        {
            list.Add(new DisplayField("Date",67,category));
            list.Add(new DisplayField("Th",27,category));
            list.Add(new DisplayField("Surf",40,category));
            list.Add(new DisplayField("Description",203,category));
            list.Add(new DisplayField("Stat",25,category));
            list.Add(new DisplayField("Prov",42,category));
            list.Add(new DisplayField("Amount",48,category));
            list.Add(new DisplayField("ADA Code",62,category));
        }
        else //if(Programs.UsingOrion){
        //  list.Add(new DisplayField("Stat 2",36,category));
        //  list.Add(new DisplayField("On Call",45,category));
        //  list.Add(new DisplayField("Effective Comm",90,category));
        //  list.Add(new DisplayField("Repair",45,category));
        //	list.Add(new DisplayField("DPCpost",52,category));
        //}
        if (category == DisplayFieldCategory.TreatmentPlanModule)
        {
            list.Add(new DisplayField("Done",50,category));
            list.Add(new DisplayField("Priority",50,category));
            list.Add(new DisplayField("Tth",40,category));
            list.Add(new DisplayField("Surf",45,category));
            list.Add(new DisplayField("Code",50,category));
            list.Add(new DisplayField("Description",235,category));
            list.Add(new DisplayField("Fee",50,category));
            list.Add(new DisplayField("Pri Ins",50,category));
            list.Add(new DisplayField("Sec Ins",50,category));
            list.Add(new DisplayField("Discount",55,category));
            list.Add(new DisplayField("Pat",50,category));
        }
        else //list.Add(new DisplayField("Prognosis",60,category));
        //list.Add(new DisplayField("Dx",28,category));
        if (category == DisplayFieldCategory.OrthoChart)
        {
        }
                 
        return list;
    }

    //Ortho chart has no default columns.  User must explicitly set up columns.
    public static List<DisplayField> getAllAvailableList(DisplayFieldCategory category) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<DisplayField> list = new List<DisplayField>();
        if (category == DisplayFieldCategory.None)
        {
            //Currently only used for ChartViews
            list.Add(new DisplayField("Date",67,category));
            list.Add(new DisplayField("Time",40,category));
            list.Add(new DisplayField("Th",27,category));
            list.Add(new DisplayField("Surf",40,category));
            list.Add(new DisplayField("Dx",28,category));
            list.Add(new DisplayField("Description",218,category));
            list.Add(new DisplayField("Stat",25,category));
            list.Add(new DisplayField("Prov",42,category));
            list.Add(new DisplayField("Amount",48,category));
            list.Add(new DisplayField("ADA Code",62,category));
            list.Add(new DisplayField("User",62,category));
            list.Add(new DisplayField("Signed",55,category));
            list.Add(new DisplayField("Priority",44,category));
            list.Add(new DisplayField("Date TP",67,category));
            list.Add(new DisplayField("Date Entry",67,category));
            list.Add(new DisplayField("Prognosis",60,category));
            list.Add(new DisplayField("Length",40,category));
            list.Add(new DisplayField("Abbr",50,category));
            list.Add(new DisplayField("Locked",50,category));
            if (Programs.getUsingOrion())
            {
                list.Add(new DisplayField("DPC",33,category));
                list.Add(new DisplayField("Schedule By",72,category));
                list.Add(new DisplayField("Stop Clock",67,category));
                list.Add(new DisplayField("Stat 2",36,category));
                list.Add(new DisplayField("On Call",45,category));
                list.Add(new DisplayField("Effective Comm",90,category));
                list.Add(new DisplayField("End Time",56,category));
                //not visible unless orion
                list.Add(new DisplayField("Quadrant",55,category));
                //behavior is specific to orion
                list.Add(new DisplayField("DPCpost",52,category));
            }
             
        }
        else if (category == DisplayFieldCategory.PatientSelect)
        {
            list.Add(new DisplayField("LastName",75,category));
            list.Add(new DisplayField("First Name",75,category));
            list.Add(new DisplayField("MI",25,category));
            list.Add(new DisplayField("Pref Name",60,category));
            list.Add(new DisplayField("Age",30,category));
            list.Add(new DisplayField("SSN",65,category));
            list.Add(new DisplayField("Hm Phone",90,category));
            list.Add(new DisplayField("Wk Phone",90,category));
            if (PrefC.getBool(PrefName.DistributorKey))
            {
                //if for OD HQ
                list.Add(new DisplayField("OtherPhone",90,category));
            }
             
            list.Add(new DisplayField("PatNum",80,category));
            list.Add(new DisplayField("ChartNum",60,category));
            list.Add(new DisplayField("Address",100,category));
            list.Add(new DisplayField("Status",65,category));
            list.Add(new DisplayField("Bill Type",90,category));
            list.Add(new DisplayField("City",80,category));
            list.Add(new DisplayField("State",55,category));
            list.Add(new DisplayField("Pri Prov",85,category));
            list.Add(new DisplayField("Birthdate",70,category));
            list.Add(new DisplayField("Site",90,category));
            list.Add(new DisplayField("Email",90,category));
        }
        else if (category == DisplayFieldCategory.PatientInformation)
        {
            list.Add(new DisplayField("Last",0,category));
            list.Add(new DisplayField("First",0,category));
            list.Add(new DisplayField("Middle",0,category));
            list.Add(new DisplayField("Preferred",0,category));
            list.Add(new DisplayField("Title",0,category));
            list.Add(new DisplayField("Salutation",0,category));
            list.Add(new DisplayField("Status",0,category));
            list.Add(new DisplayField("Gender",0,category));
            list.Add(new DisplayField("Position",0,category));
            list.Add(new DisplayField("Birthdate",0,category));
            list.Add(new DisplayField("Age",0,category));
            list.Add(new DisplayField("SS#",0,category));
            list.Add(new DisplayField("Address",0,category));
            list.Add(new DisplayField("Address2",0,category));
            list.Add(new DisplayField("City",0,category));
            list.Add(new DisplayField("State",0,category));
            if (PrefC.getBool(PrefName.DockPhonePanelShow))
            {
                list.Add(new DisplayField("Country",0,category));
            }
             
            list.Add(new DisplayField("Zip",0,category));
            list.Add(new DisplayField("Hm Phone",0,category));
            list.Add(new DisplayField("Wk Phone",0,category));
            list.Add(new DisplayField("Wireless Ph",0,category));
            list.Add(new DisplayField("E-mail",0,category));
            list.Add(new DisplayField("Contact Method",0,category));
            list.Add(new DisplayField("ABC0",0,category));
            list.Add(new DisplayField("Chart Num",0,category));
            list.Add(new DisplayField("Billing Type",0,category));
            list.Add(new DisplayField("Ward",0,category));
            list.Add(new DisplayField("AdmitDate",0,category));
            list.Add(new DisplayField("Primary Provider",0,category));
            list.Add(new DisplayField("Sec. Provider",0,category));
            list.Add(new DisplayField("Payor Types",0,category));
            list.Add(new DisplayField("Language",0,category));
            list.Add(new DisplayField("Clinic",0,category));
            list.Add(new DisplayField("ResponsParty",0,category));
            list.Add(new DisplayField("Referrals",0,category));
            list.Add(new DisplayField("Addr/Ph Note",0,category));
            list.Add(new DisplayField("PatFields",0,category));
            list.Add(new DisplayField("Guardians",0,category));
            list.Add(new DisplayField("Arrive Early",0,category));
            list.Add(new DisplayField("Super Head",0,category));
            if (PrefC.getBool(PrefName.DistributorKey))
            {
                list.Add(new DisplayField("References",0,category));
            }
             
        }
        else if (category == DisplayFieldCategory.AccountModule)
        {
            list.Add(new DisplayField("Date",65,category));
            list.Add(new DisplayField("Patient",100,category));
            list.Add(new DisplayField("Prov",40,category));
            list.Add(new DisplayField("Clinic",50,category));
            list.Add(new DisplayField("Code",46,category));
            list.Add(new DisplayField("Tth",26,category));
            list.Add(new DisplayField("Description",270,category));
            list.Add(new DisplayField("Charges",60,category));
            list.Add(new DisplayField("Credits",60,category));
            list.Add(new DisplayField("Balance",60,category));
        }
        else if (category == DisplayFieldCategory.RecallList)
        {
            list.Add(new DisplayField("Due Date",75,category));
            list.Add(new DisplayField("Patient",120,category));
            list.Add(new DisplayField("Age",30,category));
            list.Add(new DisplayField("Type",60,category));
            list.Add(new DisplayField("Interval",50,category));
            list.Add(new DisplayField("#Remind",55,category));
            list.Add(new DisplayField("LastRemind",75,category));
            list.Add(new DisplayField("Contact",120,category));
            list.Add(new DisplayField("Status",130,category));
            list.Add(new DisplayField("Note",215,category));
            list.Add(new DisplayField("BillingType",100,category));
        }
        else if (category == DisplayFieldCategory.ChartPatientInformation)
        {
            list.Add(new DisplayField("Age",0,category));
            list.Add(new DisplayField("ABC0",0,category));
            list.Add(new DisplayField("Billing Type",0,category));
            list.Add(new DisplayField("Referred From",0,category));
            list.Add(new DisplayField("Date First Visit",0,category));
            list.Add(new DisplayField("Prov. (Pri, Sec)",0,category));
            list.Add(new DisplayField("Pri Ins",0,category));
            list.Add(new DisplayField("Sec Ins",0,category));
            list.Add(new DisplayField("Payor Types",0,category));
            if (PrefC.getBool(PrefName.DistributorKey))
            {
                list.Add(new DisplayField("Registration Keys",0,category));
                list.Add(new DisplayField("Ehr Provider Keys",0,category));
                list.Add(new DisplayField("References",0,category));
            }
             
            list.Add(new DisplayField("Premedicate",0,category));
            list.Add(new DisplayField("Problems",0,category));
            list.Add(new DisplayField("Med Urgent",0,category));
            list.Add(new DisplayField("Medical Summary",0,category));
            list.Add(new DisplayField("Service Notes",0,category));
            list.Add(new DisplayField("Medications",0,category));
            list.Add(new DisplayField("Allergies",0,category));
            list.Add(new DisplayField("PatFields",0,category));
            list.Add(new DisplayField("Birthdate",0,category));
            list.Add(new DisplayField("City",0,category));
            list.Add(new DisplayField("AskToArriveEarly",0,category));
            list.Add(new DisplayField("Super Head",0,category));
            list.Add(new DisplayField("Patient Portal",0,category));
        }
        else if (category == DisplayFieldCategory.ProcedureGroupNote)
        {
            list.Add(new DisplayField("Date",67,category));
            list.Add(new DisplayField("Th",27,category));
            list.Add(new DisplayField("Surf",40,category));
            list.Add(new DisplayField("Description",218,category));
            list.Add(new DisplayField("Stat",25,category));
            list.Add(new DisplayField("Prov",42,category));
            list.Add(new DisplayField("Amount",48,category));
            list.Add(new DisplayField("ADA Code",62,category));
            if (Programs.getUsingOrion())
            {
                list.Add(new DisplayField("Stat 2",36,category));
                list.Add(new DisplayField("On Call",45,category));
                list.Add(new DisplayField("Effective Comm",90,category));
                list.Add(new DisplayField("Repair",45,category));
                list.Add(new DisplayField("DPCpost",52,category));
            }
             
        }
        else if (category == DisplayFieldCategory.TreatmentPlanModule)
        {
            list.Add(new DisplayField("Done",50,category));
            list.Add(new DisplayField("Priority",50,category));
            list.Add(new DisplayField("Tth",40,category));
            list.Add(new DisplayField("Surf",45,category));
            list.Add(new DisplayField("Code",50,category));
            list.Add(new DisplayField("Description",235,category));
            list.Add(new DisplayField("Fee",50,category));
            list.Add(new DisplayField("Pri Ins",50,category));
            list.Add(new DisplayField("Sec Ins",50,category));
            list.Add(new DisplayField("Discount",55,category));
            list.Add(new DisplayField("Pat",50,category));
            list.Add(new DisplayField("Prognosis",60,category));
            list.Add(new DisplayField("Dx",28,category));
        }
        else if (category == DisplayFieldCategory.OrthoChart)
        {
            list = getForCategory(DisplayFieldCategory.OrthoChart);
            //The display fields that the user has already saved
            List<OrthoChart> listDistinctOrthoCharts = OrthoCharts.getByDistinctFieldNames();
            for (int i = 0;i < listDistinctOrthoCharts.Count;i++)
            {
                boolean addToList = true;
                for (int j = 0;j < list.Count;j++)
                {
                    if (list[j].Description == listDistinctOrthoCharts[i].FieldName)
                    {
                        addToList = false;
                    }
                     
                }
                if (addToList)
                {
                    DisplayField df = new DisplayField("",20,DisplayFieldCategory.OrthoChart);
                    df.Description = listDistinctOrthoCharts[i].FieldName;
                    list.Add(df);
                }
                 
            }
        }
                 
        return list;
    }

    public static void saveListForCategory(List<DisplayField> ListShowing, DisplayFieldCategory category) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ListShowing, category);
            return ;
        }
         
        boolean isDefault = true;
        List<DisplayField> defaultList = getDefaultList(category);
        if (ListShowing.Count != defaultList.Count)
        {
            isDefault = false;
        }
        else
        {
            for (int i = 0;i < ListShowing.Count;i++)
            {
                if (!StringSupport.equals(ListShowing[i].Description, ""))
                {
                    isDefault = false;
                    break;
                }
                 
                if (ListShowing[i].InternalName != defaultList[i].InternalName)
                {
                    isDefault = false;
                    break;
                }
                 
                if (ListShowing[i].ColumnWidth != defaultList[i].ColumnWidth)
                {
                    isDefault = false;
                    break;
                }
                 
            }
        } 
        String command = "DELETE FROM displayfield WHERE Category=" + POut.Long(((Enum)category).ordinal());
        Db.nonQ(command);
        if (isDefault)
        {
            return ;
        }
         
        for (int i = 0;i < ListShowing.Count;i++)
        {
            ListShowing[i].ItemOrder = i;
            Insert(ListShowing[i]);
        }
    }

    /**
    * This is for use with saving Ortho Chart display fields ONLY.
    */
    public static void saveListForOrthoChart(List<DisplayField> ListShowing) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ListShowing);
            return ;
        }
         
        String command = "DELETE FROM displayfield WHERE Category=" + POut.Long(((Enum)DisplayFieldCategory.OrthoChart).ordinal());
        Db.nonQ(command);
        for (int i = 0;i < ListShowing.Count;i++)
        {
            ListShowing[i].ItemOrder = i;
            Insert(ListShowing[i]);
        }
    }

    public static void saveListForChartView(List<DisplayField> ListShowing, long ChartViewNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ListShowing, ChartViewNum);
            return ;
        }
         
        //Odds are, that if they are creating a custom view, that the fields are not default. If they are default, this code still works.
        String command = "DELETE FROM displayfield WHERE ChartViewNum=" + POut.long((long)ChartViewNum);
        Db.nonQ(command);
        for (int i = 0;i < ListShowing.Count;i++)
        {
            ListShowing[i].ItemOrder = i;
            ListShowing[i].ChartViewNum = ChartViewNum;
            Insert(ListShowing[i]);
        }
    }

}


