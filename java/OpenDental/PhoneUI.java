//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:11 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormOpenDental;
import OpenDental.FormPhoneEmpDefaultEdit;
import OpenDental.FormPhoneNumbersManage;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PhoneTile;
import OpenDentBusiness.AsteriskRingGroups;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.ClockStatusEnum;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PhoneAsterisks;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.PhoneEmpStatusOverride;
import OpenDentBusiness.PhoneNumber;
import OpenDentBusiness.PhoneNumbers;
import OpenDentBusiness.Phones;
import OpenDentBusiness.Security;
import OpenDentBusiness.TimeClockStatus;
import OpenDentBusiness.Userod;
import OpenDentBusiness.Userods;

public class PhoneUI   
{
    private static String langThis = "Phone";
    public static String PathPhoneMsg = "\\\\10.10.1.197\\Voicemail\\default\\998\\INBOX";
    public static void manage(PhoneTile tile) throws Exception {
        //if(selectedTile.PhoneCur==null) {//already validated
        long patNum = tile.getPhoneCur().PatNum;
        if (patNum == 0)
        {
            MsgBox.show(langThis,"Please attach this number to a patient first.");
            return ;
        }
         
        FormPhoneNumbersManage FormM = new FormPhoneNumbersManage();
        FormM.PatNum = patNum;
        FormM.ShowDialog();
    }

    public static void add(PhoneTile tile) throws Exception {
        //if(selectedTile.PhoneCur==null) {//already validated
        if (StringSupport.equals(tile.getPhoneCur().CustomerNumber, ""))
        {
            MsgBox.show(langThis,"No phone number present.");
            return ;
        }
         
        long patNum = tile.getPhoneCur().PatNum;
        if (FormOpenDental.CurPatNum == 0)
        {
            MsgBox.show(langThis,"Please select a patient in the main window first.");
            return ;
        }
         
        if (patNum != 0)
        {
            MsgBox.show(langThis,"The current number is already attached to a different customer.");
            return ;
        }
        else
        {
            //if(!MsgBox.Show(langThis,MsgBoxButtons.OKCancel,"The current number is already attached to a patient. Attach it to this patient instead?")) {
            //	return;
            //}
            //This crashes because we don't actually know what the number is.  Enhance later by storing actual number in phone grid.
            //PhoneNumber ph=PhoneNumbers.GetByVal(tile.PhoneCur.CustomerNumber);
            //ph.PatNum=FormOpenDental.CurPatNum;
            //PhoneNumbers.Update(ph);
            String patName = Patients.getLim(FormOpenDental.CurPatNum).getNameLF();
            if (MessageBox.Show("Attach this phone number to " + patName + "?", "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
            PhoneNumber ph = new PhoneNumber();
            ph.PatNum = FormOpenDental.CurPatNum;
            ph.PhoneNumberVal = tile.getPhoneCur().CustomerNumber;
            PhoneNumbers.insert(ph);
        } 
        //tell the phone server to refresh this row with the patient name and patnum
        DataValid.setInvalid(InvalidType.PhoneNumbers);
    }

    /**
    * If this is Security.CurUser's tile then ClockIn. If it is someone else's tile then allow the single case of switching from NeedsHelp to Available.
    */
    public static void available(PhoneTile tile) throws Exception {
        long employeeNum = Security.getCurUser().EmployeeNum;
        if (Security.getCurUser().EmployeeNum != tile.getPhoneCur().EmployeeNum)
        {
            //We are on someone else's tile. So Let's do some checks before we assume we can take over this extension.
            if (tile.getPhoneCur().ClockStatus == ClockStatusEnum.NeedsHelp)
            {
                //Allow the specific state where we are changing their status back from NeedsHelp to Available.
                //This does not require any security permissions as any tech in can perform this action on behalf of any other tech.
                Phones.setPhoneStatus(ClockStatusEnum.Available,tile.getPhoneCur().Extension,tile.getPhoneCur().EmployeeNum);
                return ;
            }
             
            //green
            //We are on a tile that is not our own
            //If another employee is occupying this extension then assume we are trying to change that employee's status back to available.
            if (ClockEvents.isClockedIn(tile.getPhoneCur().EmployeeNum))
            {
                //This tile is taken by an employee who is clocked in.
                //Transition the employee back to available.
                changeTileStatus(tile,ClockStatusEnum.Available);
                return ;
            }
             
            //The other person is still actively using this extension.
            if (tile.getPhoneCur().ClockStatus != ClockStatusEnum.None && tile.getPhoneCur().ClockStatus != ClockStatusEnum.Home)
            {
                MsgBox.show(langThis,"Cannot take over this extension as it is currently occuppied by someone who is likely on Break or Lunch.");
                return ;
            }
             
            //If another employee is NOT occupying this extension then assume we are trying clock in at this extension.
            if (ClockEvents.isClockedIn(employeeNum))
            {
                //We are already clocked in at a different extension.
                MsgBox.show(langThis,"You are already clocked in at a different extension.  You must clock out of the current extension you are logged into before moving to another extension.");
                return ;
            }
             
        }
         
        //We got this far so fall through and allow user to clock in.
        //We go here so all of our checks passed and we may login at this extension
        if (!clockIn(tile))
        {
            return ;
        }
         
        //Clock in on behalf of yourself
        //Update the Phone tables accordingly.
        PhoneEmpDefaults.setAvailable(tile.getPhoneCur().Extension,employeeNum);
        PhoneAsterisks.setToDefaultRingGroups(tile.getPhoneCur().Extension,employeeNum);
        Phones.setPhoneStatus(ClockStatusEnum.Available,tile.getPhoneCur().Extension,employeeNum);
    }

    //green
    public static void training(PhoneTile tile) throws Exception {
        changeTileStatus(tile,ClockStatusEnum.Training);
    }

    public static void teamAssist(PhoneTile tile) throws Exception {
        changeTileStatus(tile,ClockStatusEnum.TeamAssist);
    }

    public static void needsHelp(PhoneTile tile) throws Exception {
        changeTileStatus(tile,ClockStatusEnum.NeedsHelp);
    }

    public static void wrapUp(PhoneTile tile) throws Exception {
        //this is usually an automatic status
        changeTileStatus(tile,ClockStatusEnum.WrapUp);
    }

    public static void offlineAssist(PhoneTile tile) throws Exception {
        changeTileStatus(tile,ClockStatusEnum.OfflineAssist);
    }

    public static void unavailable(PhoneTile tile) throws Exception {
        if (!ClockEvents.isClockedIn(Security.getCurUser().EmployeeNum))
        {
            //Employee performing the action must be clocked in.
            MsgBox.show("PhoneUI","You must clock in before completing this action.");
            return ;
        }
         
        if (!ClockEvents.isClockedIn(tile.getPhoneCur().EmployeeNum))
        {
            //Employee having action performed must be clocked in.
            MessageBox.Show(Lan.g("PhoneUI","Target employee must be clocked in before setting this status: ") + tile.getPhoneCur().EmployeeName);
            return ;
        }
         
        if (!checkUserCanChangeStatus(tile))
        {
            return ;
        }
         
        int extension = tile.getPhoneCur().Extension;
        long employeeNum = tile.getPhoneCur().EmployeeNum;
        PhoneEmpDefault ped = PhoneEmpDefaults.getByExtAndEmp(extension,employeeNum);
        if (ped == null)
        {
            MessageBox.Show("PhoneEmpDefault (employee setting row) not found for Extension " + extension.ToString() + " and EmployeeNum " + employeeNum.ToString());
            return ;
        }
         
        FormPhoneEmpDefaultEdit formPED = new FormPhoneEmpDefaultEdit();
        formPED.PedCur = ped;
        formPED.PedCur.StatusOverride = PhoneEmpStatusOverride.Unavailable;
        if (formPED.ShowDialog() == DialogResult.OK && formPED.PedCur.StatusOverride == PhoneEmpStatusOverride.Unavailable)
        {
            //This phone status update can get skipped from within the editor if the employee is not clocked in.
            //This would be the case when you are setting an employee other than yourself to Unavailable.
            //So we will set it here. This keeps the phone table and phone panel in sync.
            Phones.setPhoneStatus(ClockStatusEnum.Unavailable,formPED.PedCur.PhoneExt,formPED.PedCur.EmployeeNum);
        }
         
    }

    /**
    * As per Nathan, changing status should set your ring group to None (not Backup as you might think). We are abandoning the Backup ring group for now.
    */
    public static void backup(PhoneTile tile) throws Exception {
        changeTileStatus(tile,ClockStatusEnum.Backup);
        PhoneAsterisks.setRingGroups(tile.getPhoneCur().Extension,AsteriskRingGroups.None);
    }

    //RingGroups---------------------------------------------------
    public static void ringgroupAll(PhoneTile tile) throws Exception {
        if (!checkUserCanChangeStatus(tile))
        {
            return ;
        }
         
        PhoneAsterisks.setRingGroups(tile.getPhoneCur().Extension,AsteriskRingGroups.All);
    }

    public static void ringgroupNone(PhoneTile tile) throws Exception {
        if (!checkUserCanChangeStatus(tile))
        {
            return ;
        }
         
        PhoneAsterisks.setRingGroups(tile.getPhoneCur().Extension,AsteriskRingGroups.None);
    }

    public static void ringgroupsDefault(PhoneTile tile) throws Exception {
        if (!checkUserCanChangeStatus(tile))
        {
            return ;
        }
         
        PhoneAsterisks.setToDefaultRingGroups(tile.getPhoneCur().Extension,tile.getPhoneCur().EmployeeNum);
    }

    /**
    * As of 10/9/13 Nathan wants backup ringgroup to go to 'None'. We may go back to using 'Backup' at some point, but for now it is not necessary so just set them to None.
    */
    public static void ringgroupsBackup(PhoneTile tile) throws Exception {
        if (!checkUserCanChangeStatus(tile))
        {
            return ;
        }
         
        PhoneAsterisks.setRingGroups(tile.getPhoneCur().Extension,AsteriskRingGroups.Backup);
    }

    //Timecard---------------------------------------------------
    public static void lunch(PhoneTile tile) throws Exception {
        //verify that employee is logged in as user
        int extension = tile.getPhoneCur().Extension;
        long employeeNum = tile.getPhoneCur().EmployeeNum;
        if (!checkUserCanChangeStatus(tile))
        {
            return ;
        }
         
        try
        {
            ClockEvents.clockOut(employeeNum,TimeClockStatus.Lunch);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        //This message will tell user that they are already clocked out.
        PhoneEmpDefaults.setAvailable(extension,employeeNum);
        Employee EmpCur = Employees.getEmp(employeeNum);
        EmpCur.ClockStatus = Lan.g("enumTimeClockStatus", TimeClockStatus.Lunch.ToString());
        Employees.update(EmpCur);
        Phones.setPhoneStatus(ClockStatusEnum.Lunch,extension);
    }

    public static void home(PhoneTile tile) throws Exception {
        //verify that employee is logged in as user
        int extension = tile.getPhoneCur().Extension;
        long employeeNum = tile.getPhoneCur().EmployeeNum;
        if (!checkUserCanChangeStatus(tile))
        {
            return ;
        }
         
        try
        {
            //Update the clock event, phone (HQ only), and phone emp default (HQ only).
            ClockEvents.clockOut(employeeNum,TimeClockStatus.Home);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        //This message will tell user that they are already clocked out.
        Employee EmpCur = Employees.getEmp(employeeNum);
        EmpCur.ClockStatus = Lan.g("enumTimeClockStatus", TimeClockStatus.Home.ToString());
        Employees.update(EmpCur);
    }

    public static void break(PhoneTile tile) throws Exception {
        //verify that employee is logged in as user
        int extension = tile.getPhoneCur().Extension;
        long employeeNum = tile.getPhoneCur().EmployeeNum;
        if (!checkUserCanChangeStatus(tile))
        {
            return ;
        }
         
        try
        {
            ClockEvents.clockOut(employeeNum,TimeClockStatus.Break);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        //This message will tell user that they are already clocked out.
        PhoneEmpDefaults.setAvailable(extension,employeeNum);
        Employee EmpCur = Employees.getEmp(employeeNum);
        EmpCur.ClockStatus = Lan.g("enumTimeClockStatus", TimeClockStatus.Break.ToString());
        Employees.update(EmpCur);
        Phones.setPhoneStatus(ClockStatusEnum.Break,extension);
    }

    /**
    * If already clocked in, this does nothing.  Returns false if not able to clock in due to security, or true if successful.
    */
    private static boolean clockIn(PhoneTile tile) throws Exception {
        long employeeNum = Security.getCurUser().EmployeeNum;
        //tile.PhoneCur.EmployeeNum;
        if (employeeNum == 0)
        {
            //Can happen if logged in as 'admin' user (employeeNum==0). Otherwise should not happen, means the employee trying to clock doesn't exist in the employee table.
            MsgBox.show(langThis,"Inavlid OD User: " + Security.getCurUser().UserName);
            return false;
        }
         
        if (ClockEvents.isClockedIn(employeeNum))
        {
            return true;
        }
         
        try
        {
            //We no longer need to check passwords here because the user HAS to be logged in and physically sitting at the computer.
            /*if(Security.CurUser.EmployeeNum!=employeeNum) {
            				if(!Security.IsAuthorized(Permissions.TimecardsEditAll,true)) {
            					if(!CheckSelectedUserPassword(employeeNum)) {
            						return false;
            					}
            				}
            			}*/
            ClockEvents.clockIn(employeeNum);
        }
        catch (Exception __dummyCatchVar0)
        {
            return true;
        }

        //This should never happen.  Fail silently.
        Employee EmpCur = Employees.getEmp(employeeNum);
        EmpCur.ClockStatus = "Working";
        Employees.update(EmpCur);
        return true;
    }

    /**
    * Will ask for password if the current user logged in isn't the user status being manipulated.
    */
    private static boolean checkSelectedUserPassword(long employeeNum) throws Exception {
        if (Security.getCurUser().EmployeeNum == employeeNum)
        {
            return true;
        }
         
        Userod selectedUser = Userods.getUserByEmployeeNum(employeeNum);
        InputBox inputPass = new InputBox("Please enter password:");
        inputPass.textResult.PasswordChar = '*';
        inputPass.ShowDialog();
        if (inputPass.DialogResult != DialogResult.OK)
        {
            return false;
        }
         
        if (!Userods.CheckTypedPassword(inputPass.textResult.Text, selectedUser.Password))
        {
            MsgBox.show("PhoneUI","Wrong password.");
            return false;
        }
         
        return true;
    }

    /**
    * Verify... 1) Security.CurUser is clocked in. 2) Target status change employee is clocked in. 3) Secruity.CurUser has TimecardsEditAll permission.
    */
    private static boolean changeTileStatus(PhoneTile tile, ClockStatusEnum newClockStatus) throws Exception {
        if (!ClockEvents.isClockedIn(Security.getCurUser().EmployeeNum))
        {
            //Employee performing the action must be clocked in.
            MsgBox.show(langThis,"You must clock in before completing this action.");
            return false;
        }
         
        if (!ClockEvents.isClockedIn(tile.getPhoneCur().EmployeeNum))
        {
            //Employee having action performed must be clocked in.
            MessageBox.Show(Lan.g(langThis,"Target employee must be clocked in before setting this status: ") + tile.getPhoneCur().EmployeeName);
            return false;
        }
         
        if (!checkUserCanChangeStatus(tile))
        {
            return false;
        }
         
        PhoneEmpDefaults.setAvailable(tile.getPhoneCur().Extension,tile.getPhoneCur().EmployeeNum);
        PhoneAsterisks.setToDefaultRingGroups(tile.getPhoneCur().Extension,tile.getPhoneCur().EmployeeNum);
        Phones.setPhoneStatus(newClockStatus,tile.getPhoneCur().Extension);
        return true;
    }

    /**
    * Verify Security.CurUser is allowed to change this tile's status.
    */
    private static boolean checkUserCanChangeStatus(PhoneTile tile) throws Exception {
        if (Security.getCurUser().EmployeeNum == tile.getPhoneCur().EmployeeNum)
        {
            return true;
        }
         
        //User is changing their own tile. This is always allowed.
        if (Security.isAuthorized(Permissions.TimecardsEditAll,true))
        {
            return true;
        }
         
        return checkSelectedUserPassword(tile.getPhoneCur().EmployeeNum);
    }

}


//User has time card edit permission so allow it.
//User must enter target tile's password correctly.