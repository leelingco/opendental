//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.GroupPermission;
import OpenDentBusiness.GroupPermissionC;
import OpenDentBusiness.GroupPermissions;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class GroupPermissions   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM grouppermission";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "GroupPermission";
        fillCache(table);
        return table;
    }

    private static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        GroupPermissionC.setList(Crud.GroupPermissionCrud.TableToList(table).ToArray());
    }

    /**
    * 
    */
    public static void update(GroupPermission gp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), gp);
            return ;
        }
         
        if (gp.NewerDate.Year > 1880 && gp.NewerDays > 0)
        {
            throw new Exception(Lans.g("GroupPermissions","Date or days can be set, but not both."));
        }
         
        if (!GroupPermissions.permTakesDates(gp.PermType))
        {
            if (gp.NewerDate.Year > 1880 || gp.NewerDays > 0)
            {
                throw new Exception(Lans.g("GroupPermissions","This type of permission may not have a date or days set."));
            }
             
        }
         
        Crud.GroupPermissionCrud.Update(gp);
    }

    /**
    * 
    */
    public static long insert(GroupPermission gp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            gp.GroupPermNum = Meth.GetLong(MethodBase.GetCurrentMethod(), gp);
            return gp.GroupPermNum;
        }
         
        if (gp.NewerDate.Year > 1880 && gp.NewerDays > 0)
        {
            throw new Exception(Lans.g("GroupPermissions","Date or days can be set, but not both."));
        }
         
        if (!GroupPermissions.permTakesDates(gp.PermType))
        {
            if (gp.NewerDate.Year > 1880 || gp.NewerDays > 0)
            {
                throw new Exception(Lans.g("GroupPermissions","This type of permission may not have a date or days set."));
            }
             
        }
         
        return Crud.GroupPermissionCrud.Insert(gp);
    }

    /**
    * 
    */
    public static void removePermission(long groupNum, OpenDentBusiness.Permissions permType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), groupNum, permType);
            return ;
        }
         
        String command = new String();
        if (permType == OpenDentBusiness.Permissions.SecurityAdmin)
        {
            //need to make sure that at least one other user has this permission
            command = "SELECT COUNT(*) FROM grouppermission WHERE PermType='" + POut.Long(((Enum)permType).ordinal()) + "'";
            DataTable table = Db.getTable(command);
            if (StringSupport.equals(table.Rows[0][0].ToString(), "1"))
            {
                throw new Exception(Lans.g("FormSecurity","At least one group must have Security Admin permission."));
            }
             
        }
         
        //only one, so this would delete the last one.
        command = "DELETE from grouppermission WHERE UserGroupNum='" + POut.long(groupNum) + "' " + "AND PermType='" + POut.Long(((Enum)permType).ordinal()) + "'";
        Db.nonQ(command);
    }

    /**
    * Gets a GroupPermission based on the supplied userGroupNum and permType.  If not found, then it returns null.  Used in FormSecurity when double clicking on a dated permission or when clicking the all button.
    */
    public static GroupPermission getPerm(long userGroupNum, OpenDentBusiness.Permissions permType) throws Exception {
        for (int i = 0;i < GroupPermissionC.getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (GroupPermissionC.getList()[i].UserGroupNum == userGroupNum && GroupPermissionC.getList()[i].PermType == permType)
            {
                return GroupPermissionC.getList()[i].Copy();
            }
             
        }
        return null;
    }

    /**
    * Used in Security.IsAuthorized
    */
    public static boolean hasPermission(long userGroupNum, OpenDentBusiness.Permissions permType) throws Exception {
        for (int i = 0;i < GroupPermissionC.getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (GroupPermissionC.getList()[i].UserGroupNum != userGroupNum || GroupPermissionC.getList()[i].PermType != permType)
            {
                continue;
            }
             
            return true;
        }
        return false;
    }

    /**
    * Gets the description for the specified permisssion.  Already translated.
    */
    public static String getDesc(OpenDentBusiness.Permissions perm) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(perm)
        {
            case Accounting: 
                return Lans.g("enumPermissions","Accounting");
            case AccountingCreate: 
                return Lans.g("enumPermissions","Accounting Create Entry");
            case AccountingEdit: 
                return Lans.g("enumPermissions","Accounting Edit Entry");
            case AccountModule: 
                return Lans.g("enumPermissions","Account Module");
            case AdjustmentCreate: 
                return Lans.g("enumPermissions","Adjustment Create");
            case AdjustmentEdit: 
                return Lans.g("enumPermissions","Adjustment Edit");
            case AdjustmentEditZero: 
                return Lans.g("enumPermissions","Adjustment Edit Zero Amount");
            case AnesthesiaIntakeMeds: 
                return Lans.g("enumPermissions","Intake Anesthetic Medications into Inventory");
            case AnesthesiaControlMeds: 
                return Lans.g("enumPermissions","Edit Anesthetic Records; Edit/Adjust Inventory Counts");
            case AppointmentCreate: 
                return Lans.g("enumPermissions","Appointment Create");
            case AppointmentEdit: 
                return Lans.g("enumPermissions","Appointment Edit");
            case AppointmentMove: 
                return Lans.g("enumPermissions","Appointment Move");
            case AppointmentsModule: 
                return Lans.g("enumPermissions","Appointments Module");
            case AutoNoteQuickNoteEdit: 
                return Lans.g("enumPermissions","Auto/Quick Note Edit");
            case EcwAppointmentRevise: 
                return Lans.g("enumPermissions","eCW Appointment Revise");
            case Backup: 
                return Lans.g("enumPermissions","Backup");
            case Billing: 
                return Lans.g("enumPermissions","Billing");
            case Blockouts: 
                return Lans.g("enumPermissions","Blockouts");
            case ChartModule: 
                return Lans.g("enumPermissions","Chart Module");
            case CarrierCreate: 
                return Lans.g("enumPermissions","Carrier Create");
            case ChooseDatabase: 
                return Lans.g("enumPermissions","Choose Database");
            case ClaimSentEdit: 
                return Lans.g("enumPermissions","Claim Sent Edit");
            case CommlogEdit: 
                return Lans.g("enumPermissions","Commlog Edit");
            case DepositSlips: 
                return Lans.g("enumPermissions","Deposit Slips");
            case EhrEmergencyAccess: 
                return Lans.g("enumPermissions","EHR Emergency Access");
            case EquipmentDelete: 
                return Lans.g("enumPermissions","Equipment Delete");
            case EquipmentSetup: 
                return Lans.g("enumPermissions","Equipment Setup");
            case FamilyModule: 
                return Lans.g("enumPermissions","Family Module");
            case ImageDelete: 
                return Lans.g("enumPermissions","Image Delete");
            case ImagesModule: 
                return Lans.g("enumPermissions","Images Module");
            case InsPayCreate: 
                return Lans.g("enumPermissions","Insurance Payment Create");
            case InsPayEdit: 
                return Lans.g("enumPermissions","Insurance Payment Edit");
            case InsPlanChangeSubsc: 
                return Lans.g("enumPermissions","Insurance Plan Change Subscriber");
            case ManageModule: 
                return Lans.g("enumPermissions","Manage Module");
            case None: 
                return "";
            case PaymentCreate: 
                return Lans.g("enumPermissions","Payment Create");
            case PaymentEdit: 
                return Lans.g("enumPermissions","Payment Edit");
            case PerioEdit: 
                return Lans.g("enumPermissions","Perio Chart Edit");
            case ProblemEdit: 
                return Lans.g("enumPermissions","Problem Edit");
            case ProcComplCreate: 
                return Lans.g("enumPermissions","Create Completed Procedure (or set complete)");
            case ProcedureNote: 
                return Lans.g("enumPermissions","Procedure Note");
            case ProcDelete: 
                return Lans.g("enumPermissions","Delete Procedure");
            case ProcComplEdit: 
                return Lans.g("enumPermissions","Edit Completed Procedure");
            case ProcEditShowFee: 
                return Lans.g("enumPermissions","Show Procedure Fee");
            case Providers: 
                return Lans.g("enumPermissions","Providers");
            case Reports: 
                return Lans.g("enumPermissions","Reports");
            case RefAttachAdd: 
                return Lans.g("enumPermissions","Referral, Add to Patient");
            case RefAttachDelete: 
                return Lans.g("enumPermissions","Referral, Delete from Patient");
            case ReferralAdd: 
                return Lans.g("enumPermissions","Referral Add");
            case RxCreate: 
                return Lans.g("enumPermissions","Rx Create");
            case Schedules: 
                return Lans.g("enumPermissions","Schedules - Practice and Provider");
            case SecurityAdmin: 
                return Lans.g("enumPermissions","Security Admin");
            case Setup: 
                return Lans.g("enumPermissions","Setup - Covers a wide variety of setup functions");
            case SheetEdit: 
                return Lans.g("enumPermissions","Sheet Edit");
            case TimecardDeleteEntry: 
                return Lans.g("enumPermissions","Timecard Delete Entry");
            case TimecardsEditAll: 
                return Lans.g("enumPermissions","Edit All Timecards");
            case TPModule: 
                return Lans.g("enumPermissions","TreatmentPlan Module");
            case TreatPlanEdit: 
                return Lans.g("enumPermissions","Edit Treatment Plan");
            case UserQuery: 
                return Lans.g("enumPermissions","User Query");
            case ReportProdInc: 
                return Lans.g("enumPermissions","Reports - Production and Income, Aging");
            case ReportDashboard: 
                return Lans.g("enumPermissions","Reports - Dashboard");
            case TaskEdit: 
                return Lans.g("enumPermissions","Task Edit");
            case WikiListSetup: 
                return Lans.g("enumPermissions","Wiki List Setup");
        
        }
        return "";
    }

    //case Permissions.EhrInfoButton:
    //	return Lans.g("enumPermissions","EHR Access Info Button");
    //case Permissions.EhrShowCDS:
    //	return Lans.g("enumPermissions","EHR Show Clinical Decision Support");
    //should never happen
    /**
    * 
    */
    public static boolean permTakesDates(OpenDentBusiness.Permissions permType) throws Exception {
        //No need to check RemotingRole; no call to db.
        //prevents backdating
        //prevents backdating
        if (permType == OpenDentBusiness.Permissions.AdjustmentEdit || permType == OpenDentBusiness.Permissions.PaymentEdit || permType == OpenDentBusiness.Permissions.ProcComplEdit || permType == OpenDentBusiness.Permissions.ProcDelete || permType == OpenDentBusiness.Permissions.InsPayEdit || permType == OpenDentBusiness.Permissions.ClaimSentEdit || permType == OpenDentBusiness.Permissions.AccountingEdit || permType == OpenDentBusiness.Permissions.AccountingCreate || permType == OpenDentBusiness.Permissions.DepositSlips || permType == OpenDentBusiness.Permissions.TreatPlanEdit || permType == OpenDentBusiness.Permissions.TimecardDeleteEntry || permType == OpenDentBusiness.Permissions.EquipmentDelete || permType == OpenDentBusiness.Permissions.CommlogEdit || permType == OpenDentBusiness.Permissions.SheetEdit || permType == OpenDentBusiness.Permissions.PerioEdit || permType == OpenDentBusiness.Permissions.ImageDelete)
        {
            return true;
        }
         
        return false;
    }

}


