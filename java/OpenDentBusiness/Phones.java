//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.ClockStatusEnum;
import OpenDentBusiness.Db;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Phone;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.Phones;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Task;

/**
* 
*/
public class Phones   
{
    /**
    * Define a color scheme for a phone. FormMapHQ uses DUAL while PhoneTile uses SINGLE.
    */
    public static class PhoneColorScheme   
    {
        private static Color COLOR_DUAL_FontHere = Color.Black;
        private static Color COLOR_DUAL_FontAway = Color.FromArgb(186, 186, 186);
        private static Color COLOR_DUAL_InnerHome = Color.FromArgb(245, 245, 245);
        private static Color COLOR_DUAL_OuterHome = Color.FromArgb(191, 191, 191);
        private static Color COLOR_DUAL_InnerNeedsHelp = Color.FromArgb(249, 233, 249);
        private static Color COLOR_DUAL_OuterNeedsHelp = Color.Orchid;
        private static Color COLOR_DUAL_InnerNoColor = Color.FromArgb(245, 245, 245);
        private static Color COLOR_DUAL_OuterNoColor = Color.FromArgb(100, 100, 100);
        private static Color COLOR_DUAL_InnerUnavailable = Color.FromArgb(245, 245, 245);
        private static Color COLOR_DUAL_OuterUnavailable = Color.FromArgb(100, 100, 100);
        private static Color COLOR_DUAL_InnerTriageAway = Color.White;
        public static Color COLOR_DUAL_InnerTriageHere = Color.LightCyan;
        public static Color COLOR_DUAL_OuterTriage = Color.Blue;
        private static Color COLOR_DUAL_InnerOnPhone = Color.FromArgb(254, 235, 233);
        private static Color COLOR_DUAL_OuterOnPhone = Color.Red;
        private static Color COLOR_DUAL_InnerLunchBreak = Color.White;
        private static Color COLOR_DUAL_OuterLunchBreak = Color.LimeGreen;
        private static Color COLOR_DUAL_InnerAvailable = Color.FromArgb(217, 255, 217);
        private static Color COLOR_DUAL_OuterAvailable = Color.LimeGreen;
        private static Color COLOR_DUAL_InnerTrainingAssistWrap = Color.FromArgb(255, 255, 145);
        private static Color COLOR_DUAL_OuterTrainingAssistWrap = Color.LimeGreen;
        private static Color COLOR_DUAL_InnerBackup = Color.FromArgb(236, 255, 236);
        private static Color COLOR_DUAL_OuterBackup = Color.FromArgb(100, 100, 100);
        private static Color COLOR_SINGLE_Unavailable = Color.FromArgb(191, 191, 191);
        private static Color COLOR_SINGLE_TriageAway = Color.White;
        private static Color COLOR_SINGLE_TriageHere = Color.SkyBlue;
        private static Color COLOR_SINGLE_OnPhone = Color.Salmon;
        private static Color COLOR_SINGLE_LunchBreak = Color.White;
        private static Color COLOR_SINGLE_Available = Color.FromArgb(153, 220, 153);
        private static Color COLOR_SINGLE_TrainingAssistWrap = Color.FromArgb(255, 255, 145);
        private static Color COLOR_SINGLE_Backup = Color.FromArgb(217, 255, 217);
        private boolean _forDualColor = new boolean();
        public boolean getForDualColor() throws Exception {
            return _forDualColor;
        }

        public void setForDualColor(boolean value) throws Exception {
            _forDualColor = value;
            setColorScheme(_forDualColor);
        }

        public Color ColorFontHere = new Color();
        public Color ColorFontAway = new Color();
        public Color ColorInnerUnavailable = new Color();
        public Color ColorOuterUnavailable = new Color();
        public Color ColorInnerHome = new Color();
        public Color ColorOuterHome = new Color();
        public Color ColorInnerNeedsHelp = new Color();
        public Color ColorOuterNeedsHelp = new Color();
        public Color ColorInnerNoColor = new Color();
        public Color ColorOuterNoColor = new Color();
        public Color ColorInnerTriageAway = new Color();
        public Color ColorInnerTriageHere = new Color();
        public Color ColorOuterTriage = new Color();
        public Color ColorInnerOnPhone = new Color();
        public Color ColorOuterOnPhone = new Color();
        public Color ColorInnerLunchBreak = new Color();
        public Color ColorOuterLunchBreak = new Color();
        public Color ColorInnerAvailable = new Color();
        public Color ColorOuterAvailable = new Color();
        public Color ColorInnerTrainingAssistWrap = new Color();
        public Color ColorOuterTrainingAssistWrap = new Color();
        public Color ColorInnerBackup = new Color();
        public Color ColorOuterBackup = new Color();
        /**
        * Switch between single and dual color schemes
        */
        public PhoneColorScheme(boolean forDualColor) throws Exception {
            setForDualColor(forDualColor);
        }

        /**
        * Set public available colors according to user preference
        */
        private void setColorScheme(boolean forDualColor) throws Exception {
            //default is dual color scheme
            ColorFontHere = COLOR_DUAL_FontHere;
            ColorFontAway = COLOR_DUAL_FontAway;
            ColorInnerUnavailable = COLOR_DUAL_InnerUnavailable;
            ColorOuterUnavailable = COLOR_DUAL_OuterUnavailable;
            ColorInnerHome = COLOR_DUAL_InnerHome;
            ColorOuterHome = COLOR_DUAL_OuterHome;
            ColorInnerNeedsHelp = COLOR_DUAL_InnerNeedsHelp;
            ColorOuterNeedsHelp = COLOR_DUAL_OuterNeedsHelp;
            ColorInnerNoColor = COLOR_DUAL_InnerNoColor;
            ColorOuterNoColor = COLOR_DUAL_OuterNoColor;
            ColorInnerTriageAway = COLOR_DUAL_InnerTriageAway;
            ColorInnerTriageHere = COLOR_DUAL_InnerTriageHere;
            ColorOuterTriage = COLOR_DUAL_OuterTriage;
            ColorInnerOnPhone = COLOR_DUAL_InnerOnPhone;
            ColorOuterOnPhone = COLOR_DUAL_OuterOnPhone;
            ColorInnerLunchBreak = COLOR_DUAL_InnerLunchBreak;
            ColorOuterLunchBreak = COLOR_DUAL_OuterLunchBreak;
            ColorInnerAvailable = COLOR_DUAL_InnerAvailable;
            ColorOuterAvailable = COLOR_DUAL_OuterAvailable;
            ColorInnerTrainingAssistWrap = COLOR_DUAL_InnerTrainingAssistWrap;
            ColorOuterTrainingAssistWrap = COLOR_DUAL_OuterTrainingAssistWrap;
            ColorInnerBackup = COLOR_DUAL_InnerBackup;
            ColorOuterBackup = COLOR_DUAL_OuterBackup;
            //make any changes necessary for single color scheme
            if (!forDualColor)
            {
                ColorInnerUnavailable = COLOR_SINGLE_Unavailable;
                ColorOuterUnavailable = COLOR_SINGLE_Unavailable;
                ColorInnerTriageAway = COLOR_SINGLE_TriageAway;
                ColorInnerTriageHere = COLOR_SINGLE_TriageHere;
                ColorOuterTriage = COLOR_SINGLE_TriageHere;
                ColorInnerOnPhone = COLOR_SINGLE_OnPhone;
                ColorOuterOnPhone = COLOR_SINGLE_OnPhone;
                ColorInnerLunchBreak = COLOR_SINGLE_LunchBreak;
                ColorOuterLunchBreak = COLOR_SINGLE_LunchBreak;
                ColorInnerAvailable = COLOR_SINGLE_Available;
                ColorOuterAvailable = COLOR_SINGLE_Available;
                ColorInnerTrainingAssistWrap = COLOR_SINGLE_TrainingAssistWrap;
                ColorOuterTrainingAssistWrap = COLOR_SINGLE_TrainingAssistWrap;
                ColorInnerBackup = COLOR_SINGLE_Backup;
                ColorOuterBackup = COLOR_SINGLE_Backup;
            }
             
        }
    
    }

    public static void insert(Phone phone) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), phone);
            return ;
        }
         
        Crud.PhoneCrud.Insert(phone);
    }

    //db sets the PK
    public static List<Phone> getPhoneList() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Phone>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM phone ORDER BY Extension";
        try
        {
            return Crud.PhoneCrud.SelectMany(command);
        }
        catch (Exception __dummyCatchVar0)
        {
            return new List<Phone>();
        }
    
    }

    /**
    * Converts from string to enum and also handles conversion of Working to Available
    */
    public static ClockStatusEnum getClockStatusFromEmp(String empClockStatus) throws Exception {
        //No need to check RemotingRole; no call to db.
        System.String __dummyScrutVar0 = empClockStatus;
        if (__dummyScrutVar0.equals("Home"))
        {
            return ClockStatusEnum.Home;
        }
        else if (__dummyScrutVar0.equals("Lunch"))
        {
            return ClockStatusEnum.Lunch;
        }
        else if (__dummyScrutVar0.equals("Break"))
        {
            return ClockStatusEnum.Break;
        }
        else if (__dummyScrutVar0.equals("Working"))
        {
            return ClockStatusEnum.Available;
        }
        else
        {
            return ClockStatusEnum.None;
        }    
    }

    /**
    * this code is similar to code in the phone tracking server.  But here, we frequently only change clockStatus and ColorBar by setting employeeNum=-1.  If employeeNum is not -1, then EmployeeName also gets set.
    */
    public static void setPhoneStatus(ClockStatusEnum clockStatus, int extens) throws Exception {
        //No need to check RemotingRole; no call to db.
        SetPhoneStatus(clockStatus, extens, -1);
    }

    /**
    * this code is similar to code in the phone tracking server.  But here, we frequently only change clockStatus and ColorBar by setting employeeNum=-1.  If employeeNum is not -1, then EmployeeName also gets set.  If employeeNum==0, then clears employee from that row.
    */
    public static void setPhoneStatus(ClockStatusEnum clockStatus, int extens, long employeeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), clockStatus, extens, employeeNum);
            return ;
        }
         
        String command = "SELECT phoneempdefault.EmployeeNum,phoneempdefault.IsTriageOperator,Description,phoneempdefault.EmpName,HasColor,phone.ClockStatus " + "FROM phone " + "LEFT JOIN phoneempdefault ON phone.Extension=phoneempdefault.PhoneExt " + "WHERE phone.Extension=" + POut.Long(extens);
        DataTable tablePhone = Db.getTable(command);
        if (tablePhone.Rows.Count == 0)
        {
            return ;
        }
         
        //It would be nice if we could create a phone row for this extension.
        long empNum = PIn.Long(tablePhone.Rows[0]["EmployeeNum"].ToString());
        boolean isTriageOperator = PIn.Bool(tablePhone.Rows[0]["IsTriageOperator"].ToString());
        String empName = PIn.String(tablePhone.Rows[0]["EmpName"].ToString());
        String clockStatusDb = PIn.String(tablePhone.Rows[0]["ClockStatus"].ToString());
        Employee emp = Employees.getEmp(employeeNum);
        if (emp != null)
        {
            //A new employee is going to take over this extension.
            empName = emp.FName;
            empNum = emp.EmployeeNum;
        }
        else if (employeeNum == 0)
        {
            //Clear the employee from that row.
            empName = "";
            empNum = 0;
        }
          
        //if these values are null because of missing phoneempdefault row, they will default to false
        //PhoneEmpStatusOverride statusOverride=(PhoneEmpStatusOverride)PIn.Int(tablePhone.Rows[0]["StatusOverride"].ToString());
        boolean hasColor = PIn.Bool(tablePhone.Rows[0]["HasColor"].ToString());
        //When a user shows up as a color on the phone panel, we want a timer to be constantly going to show how long they've been off the phone.
        String dateTimeStart = "";
        //It's possible that a new user has never clocked in before, therefore their clockStatus will be empty.  Simply set it to the status that they are trying to go to.
        if (StringSupport.equals(clockStatusDb, ""))
        {
            clockStatusDb = clockStatus.ToString();
        }
         
        if (clockStatus == ClockStatusEnum.Break || clockStatus == ClockStatusEnum.Lunch)
        {
            //The user is going on Lunch or Break.  Start the DateTimeStart counter so we know how long they have been gone.
            dateTimeStart = "DateTimeStart=NOW(), ";
        }
        else if (clockStatus == ClockStatusEnum.Home)
        {
            //User is going Home.  Always clear the DateTimeStart column no matter what.
            dateTimeStart = "DateTimeStart='0001-01-01', ";
        }
        else
        {
            //User shows as a color on big phones and is not going to a status of Home, Lunch, or Break.  Example: Available, Training etc.
            //Get the current clock status from the database.
            ClockStatusEnum clockStatusCur = (ClockStatusEnum)Enum.Parse(ClockStatusEnum.class, clockStatusDb);
            //Start the clock if the user is going from a break status to any other non-break status.
            if (clockStatusCur == ClockStatusEnum.Home || clockStatusCur == ClockStatusEnum.Lunch || clockStatusCur == ClockStatusEnum.Break)
            {
                //The user is clocking in from home, lunch, or break.  Start the timer up.
                if (hasColor)
                {
                    //Only start up the timer when someone with color clocks in.
                    dateTimeStart = "DateTimeStart=NOW(), ";
                }
                else
                {
                    //Someone with no color then reset the timer. They are back from break, that's all we need to know.
                    dateTimeStart = "DateTimeStart='0001-01-01', ";
                } 
            }
             
        }  
        //Update the phone row to reflect the new clock status of the user.
        String clockStatusNew = clockStatus.ToString();
        if (clockStatus == ClockStatusEnum.None)
        {
            clockStatusNew = "";
        }
         
        //+"ColorBar=-1, " //ColorBar is now determined at runtime by OD using Phones.GetPhoneColor.
        command = "UPDATE phone SET ClockStatus='" + POut.string(clockStatusNew) + "', " + dateTimeStart + "EmployeeNum=" + POut.long(empNum) + ", " + "EmployeeName='" + POut.string(empName) + "' " + "WHERE Extension=" + extens;
        Db.nonQ(command);
        if (PrefC.getBool(PrefName.DockPhonePanelShow))
        {
            //hq only
            //Zero out any duplicate phone table rows for this employee.
            //This is possible if a user logged off and another employee logs into their computer. This would cause duplicate entries in the big phones window.
            updatePhoneToEmpty(employeeNum,extens);
        }
         
    }

    /**
    * Zero out any duplicate phone table rows for this employee. This is possible if a user logged off and another employee logs into their computer. This would cause duplicate entries in the big phones window. If ignoreExtension less than 1 (inavlid) then zero out all entries for this employeeNum.
    */
    public static void updatePhoneToEmpty(long employeeNum, int ignoreExtension) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), employeeNum, ignoreExtension);
            return ;
        }
         
        String command = "UPDATE phone SET " + "EmployeeName='', " + "ClockStatus='', " + "Description='', " + "EmployeeNum=0 " + "WHERE EmployeeNum=" + POut.long(employeeNum) + " " + "AND Extension!=" + POut.int(ignoreExtension);
        Db.nonQ(command);
    }

    /**
    * Consider all scenarios for a employee/phone/cubicle and return color and triage information
    */
    public static void getPhoneColor(Phone phone, PhoneEmpDefault phoneEmpDefault, boolean forDualColorScheme, RefSupport<Color> outerColor, RefSupport<Color> innerColor, RefSupport<Color> fontColor, RefSupport<boolean> isTriageOperatorOnTheClock) throws Exception {
        PhoneColorScheme colorScheme = new PhoneColorScheme(forDualColorScheme);
        isTriageOperatorOnTheClock.setValue(false);
        //first set the font color
        if (phone == null || phoneEmpDefault == null || phone.ClockStatus == ClockStatusEnum.Home || phone.ClockStatus == ClockStatusEnum.None || phone.ClockStatus == ClockStatusEnum.Off)
        {
            fontColor.setValue(colorScheme.ColorFontAway);
        }
        else
        {
            fontColor.setValue(colorScheme.ColorFontHere);
        } 
        if (!forDualColorScheme && !phoneEmpDefault.HasColor)
        {
            //smaller color boxes need special colors
            innerColor.setValue(Color.Black);
            outerColor.setValue(Color.White);
            return ;
        }
         
        //now cover all scenarios and set the inner and out color
        if (phone.ClockStatus == ClockStatusEnum.Home || phone.ClockStatus == ClockStatusEnum.None || phone.ClockStatus == ClockStatusEnum.Off)
        {
            //No color if employee is not currently working. Trumps all.
            outerColor.setValue(colorScheme.ColorOuterHome);
            innerColor.setValue(colorScheme.ColorInnerHome);
            return ;
        }
         
        if (phone.ClockStatus == ClockStatusEnum.NeedsHelp)
        {
            //get this person help now!
            outerColor.setValue(colorScheme.ColorOuterNeedsHelp);
            innerColor.setValue(colorScheme.ColorInnerNeedsHelp);
            return ;
        }
         
        if (!phoneEmpDefault.HasColor)
        {
            //not colored (generally an engineer or admin)
            outerColor.setValue(colorScheme.ColorOuterNoColor);
            innerColor.setValue(colorScheme.ColorInnerNoColor);
            return ;
        }
         
        if (phone.ClockStatus == ClockStatusEnum.Unavailable)
        {
            //Unavailable is very rare and must be approved by management. Make them look like admin/engineer.
            outerColor.setValue(colorScheme.ColorOuterUnavailable);
            innerColor.setValue(colorScheme.ColorInnerUnavailable);
            return ;
        }
         
        //If we get this far then the person is a tech who is working today.
        if (phoneEmpDefault.IsTriageOperator)
        {
            outerColor.setValue(colorScheme.ColorOuterTriage);
            if (phone.ClockStatus == ClockStatusEnum.Break || phone.ClockStatus == ClockStatusEnum.Lunch)
            {
                //triage op is working today but currently on break/lunch
                innerColor.setValue(colorScheme.ColorInnerTriageAway);
                if (!forDualColorScheme)
                {
                    //smaller color boxes need special colors
                    outerColor.setValue(colorScheme.ColorInnerTriageAway);
                }
                 
            }
            else
            {
                //this is a triage operator who is currently here and on the clock
                isTriageOperatorOnTheClock.setValue(true);
                innerColor.setValue(colorScheme.ColorInnerTriageHere);
            } 
            return ;
        }
         
        if (!StringSupport.equals(phone.Description, ""))
        {
            //Description field only has 'in use' when person is on the phone. That is the only time the field is not empty.
            outerColor.setValue(colorScheme.ColorOuterOnPhone);
            innerColor.setValue(colorScheme.ColorInnerOnPhone);
            return ;
        }
         
        //We get this far so we are dealing with a tech who is not on a phone call. Handle each state.
        switch(phone.ClockStatus)
        {
            case Lunch: 
            case Break: 
                outerColor.setValue(colorScheme.ColorOuterLunchBreak);
                innerColor.setValue(colorScheme.ColorInnerLunchBreak);
                return ;
            case Available: 
                outerColor.setValue(colorScheme.ColorOuterAvailable);
                innerColor.setValue(colorScheme.ColorInnerAvailable);
                return ;
            case WrapUp: 
            case Training: 
            case TeamAssist: 
            case OfflineAssist: 
                //these all look the same. they mean the tech is here and available but they are currently on a different assigment.
                outerColor.setValue(colorScheme.ColorOuterTrainingAssistWrap);
                innerColor.setValue(colorScheme.ColorInnerTrainingAssistWrap);
                return ;
            case Backup: 
                outerColor.setValue(colorScheme.ColorOuterBackup);
                innerColor.setValue(colorScheme.ColorInnerBackup);
                return ;
            default: 
                break;
        
        }
        throw new Exception("FormMapHQ.GetPhoneColor has a state that is currently unsupported!");
    }

    public static Phone getPhoneForExtension(List<Phone> phoneList, int extens) throws Exception {
        for (int i = 0;i < phoneList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (phoneList[i].Extension == extens)
            {
                return phoneList[i];
            }
             
        }
        return null;
    }

    /**
    * Gets the extension for the employee.  Returns 0 if employee cannot be found.
    */
    public static int getExtensionForEmp(long employeeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), employeeNum);
        }
         
        String command = "SELECT Extension FROM phone WHERE EmployeeNum=" + POut.long(employeeNum);
        DataTable table = Db.getTable(command);
        int extension = 0;
        if (table.Rows.Count > 0)
        {
            extension = PIn.Int(table.Rows[0]["Extension"].ToString());
        }
         
        return extension;
    }

    /*
    		///<summary>Gets the phoneNum which is the primary key, not the phone number.</summary>
    		public static long GetPhoneNum(int extension){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetLong(MethodBase.GetCurrentMethod(),extension);
    			}
    			string command="SELECT PhoneNum FROM phone WHERE Extension ="+POut.Long(extension);
    			string result= Db.GetScalar(command);
    			return PIn.Long(result);
    		}*/
    /**
    * Sets the employeeName and employeeNum for when someone else logs into another persons computer.
    */
    public static void setPhoneForEmp(long empNum, String empName, long extension) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), empNum, empName, extension);
            return ;
        }
         
        if (extension == 0)
        {
            return ;
        }
         
        String command = "UPDATE phone SET " + "EmployeeName   = '" + POut.string(empName) + "', " + "EmployeeNum   = " + POut.long(empNum) + " " + "WHERE Extension = " + POut.long(extension);
        Db.nonQ(command);
    }

    /**
    * Bitmap can be null.  Computername will override ipAddress.
    */
    public static void setWebCamImage(String ipAddress, Bitmap bitmap, String computerName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ipAddress, bitmap, computerName);
            return ;
        }
         
        //if(ipAddress=="") {
        //	return;
        //}
        String command = "SELECT * FROM phoneempdefault WHERE ComputerName='" + POut.string(computerName) + "'";
        PhoneEmpDefault ped = Crud.PhoneEmpDefaultCrud.SelectOne(command);
        if (ped != null)
        {
            //we found that computername entered as an override
            //handles null
            command = "UPDATE phone SET " + "WebCamImage   = '" + POut.Bitmap(bitmap, ImageFormat.Png) + "' " + "WHERE Extension = " + POut.Long(ped.PhoneExt);
            Db.nonQ(command);
            return ;
        }
         
        //there is no computername override entered by staff, so figure out what the extension should be
        int extension = 0;
        if (ipAddress.Contains("10.10.1.2"))
        {
            extension = PIn.Int(ipAddress.ToString().Substring(8)) - 100;
        }
         
        //eg 205-100=105
        if (extension == 0)
        {
            return ;
        }
         
        //we don't have a good extension
        //handles null
        //Example: this is computer .204, and ext 104 has a computername override. Don't update ext 104.
        command = "UPDATE phone SET " + "WebCamImage   = '" + POut.Bitmap(bitmap, ImageFormat.Png) + "' " + "WHERE Extension = " + POut.Long(extension) + " " + "AND NOT EXISTS(SELECT * FROM phoneempdefault WHERE PhoneExt= " + POut.Long(extension) + " " + "AND ComputerName!='')";
        //there exists a computername override for the extension
        Db.nonQ(command);
    }

    public static int getPhoneExtension(String ipAddress, String computerName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), ipAddress, computerName);
        }
         
        String command = "SELECT * FROM phoneempdefault WHERE ComputerName='" + POut.string(computerName) + "'";
        PhoneEmpDefault ped = Crud.PhoneEmpDefaultCrud.SelectOne(command);
        if (ped != null)
        {
            return ped.PhoneExt;
        }
         
        //there is no computername override entered by staff, so figure out what the extension should be
        int extension = 0;
        if (ipAddress.Contains("10.10.1.2"))
        {
            return PIn.Int(ipAddress.ToString().Substring(8)) - 100;
        }
         
        return 0;
    }

    //eg 205-100=105
    //couldn't find good extension
    public static void setScreenshot(int extension, String path, Bitmap bitmap) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), extension);
            return ;
        }
         
        //handles null
        String command = "UPDATE phone SET " + "ScreenshotPath = '" + POut.string(path) + "', " + "ScreenshotImage   = '" + POut.Bitmap(bitmap, ImageFormat.Png) + "' " + "WHERE Extension = " + POut.Long(extension);
        Db.nonQ(command);
    }

    /**
    * Checks the phone.ClockStatus to determine if screenshot should be saved.  Returns extension if true and zero if false.
    */
    public static int isOnClock(String ipAddress, String computerName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), ipAddress, computerName);
        }
         
        String command = "SELECT * FROM phoneempdefault WHERE ComputerName='" + POut.string(computerName) + "'";
        PhoneEmpDefault ped = Crud.PhoneEmpDefaultCrud.SelectOne(command);
        if (ped != null)
        {
            //we found that computername entered as an override
            if (ped.IsPrivateScreen)
            {
                Phones.setScreenshot(ped.PhoneExt,"",null);
                return 0;
            }
             
            command = "SELECT ClockStatus FROM phone " + "WHERE Extension = " + POut.Long(ped.PhoneExt);
            try
            {
                ClockStatusEnum status = (ClockStatusEnum)Enum.Parse(ClockStatusEnum.class, PIn.string(Db.getScalar(command)));
                if (status == ClockStatusEnum.Available || status == ClockStatusEnum.Backup || status == ClockStatusEnum.OfflineAssist || status == ClockStatusEnum.TeamAssist || status == ClockStatusEnum.Training || status == ClockStatusEnum.WrapUp || status == ClockStatusEnum.NeedsHelp)
                {
                    return ped.PhoneExt;
                }
                 
            }
            catch (Exception __dummyCatchVar1)
            {
                return 0;
            }

            return 0;
        }
         
        //on break or clocked out
        //there is no computername override entered by staff, so figure out what the extension should be
        int extension = 0;
        if (ipAddress.Contains("10.10.1.2"))
        {
            extension = PIn.Int(ipAddress.ToString().Substring(8)) - 100;
        }
         
        //eg 205-100=105
        if (extension == 0)
        {
            return 0;
        }
         
        //we don't have a good extension
        //make sure the extension isn't overridden with a computername
        //Example: this is computer .204, and ext 104 has a computername override. This computer should not save screenshot on behalf of 104.
        //command="SELECT COUNT(*) FROM phoneempdefault WHERE PhoneExt= "+POut.Long(extension)+" "
        //	+"AND ComputerName!=''";//there exists a computername override for the extension
        //if(Db.GetScalar(command)!="0") {
        //	return 0;
        //}
        command = "SELECT * FROM phoneempdefault WHERE PhoneExt= " + POut.Long(extension);
        ped = Crud.PhoneEmpDefaultCrud.SelectOne(command);
        if (ped != null && ped.IsPrivateScreen)
        {
            Phones.setScreenshot(ped.PhoneExt,"",null);
            return 0;
        }
         
        command = "SELECT ClockStatus FROM phone " + "WHERE Extension = " + POut.Long(extension);
        try
        {
            ClockStatusEnum status2 = (ClockStatusEnum)Enum.Parse(ClockStatusEnum.class, PIn.string(Db.getScalar(command)));
            if (status2 == ClockStatusEnum.Available || status2 == ClockStatusEnum.Backup || status2 == ClockStatusEnum.OfflineAssist || status2 == ClockStatusEnum.TeamAssist || status2 == ClockStatusEnum.Training || status2 == ClockStatusEnum.WrapUp || status2 == ClockStatusEnum.NeedsHelp)
            {
                return extension;
            }
             
        }
        catch (Exception __dummyCatchVar2)
        {
            return 0;
        }

        return 0;
    }

    //on break or clocked out
    public static void clearImages() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "UPDATE phone SET WebCamImage=''";
        Db.nonQ(command);
    }

    /**
    * Gets list of TaskNums for new and viewed tasks within the Triage task list.
    */
    public static List<long> getTriageTaskNums() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        List<long> taskNums = new List<long>();
        String command = "SELECT * FROM task " + "WHERE TaskListNum=1697 " + "AND TaskStatus<>2";
        //Triage task list.
        //Not done (new or viewed).
        List<Task> triageList = Crud.TaskCrud.SelectMany(command);
        for (int i = 0;i < triageList.Count;i++)
        {
            taskNums.Add(triageList[i].TaskNum);
        }
        return taskNums;
    }

    /**
    * Returns the time of the oldest task within the Triage task list.  Returns 0 if there is no tasks in the list.
    */
    public static DateTime getTriageTime() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<DateTime>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT IFNULL(MIN(DateTimeEntry),0) AS triageTime " + "FROM task " + "WHERE TaskListNum=1697 " + "AND TaskStatus<>2 " + "AND TaskNum NOT IN (SELECT TaskNum FROM tasknote) " + "LIMIT 1";
        return PIn.dateT(Db.getScalar(command));
    }

    //Triage task list.
    //Not done (new or viewed).
    //Not waiting a call back.
    /**
    * Get triage metrics to be displayed in phone panels
    */
    public static DataTable getTriageMetrics() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<DataTable>GetObject(MethodBase.GetCurrentMethod());
        }
         
        //-- get all phone metrics as a collection of sub-selects
        String command = "SELECT \r\n" + 
        "\t\t\t\t-- count triage tasks with no notes\t\t\t\t\r\n" + 
        "\t\t\t\t(SELECT COUNT(TaskNum) \r\n" + 
        "\t\t\t\t\tFROM task  \r\n" + 
        "\t\t\t\t\tWHERE  \r\n" + 
        "\t\t\t\t\t\tTaskListNum=1697   -- Triage task list.\r\n" + 
        "\t\t\t\t\t\tAND TaskStatus<>2   -- Not done (new or viewed).\r\n" + 
        "\t\t\t\t\t\tAND TaskNum NOT IN (SELECT tn.TaskNum FROM tasknote tn WHERE tn.TaskNum = task.TaskNum)  -- no notes yet\r\n" + 
        "\t\t\t\t\t) AS CountTasksWithoutNotes \r\n" + 
        "\t\t\t\t-- count triage tasks with notes\r\n" + 
        "\t\t\t\t,(SELECT COUNT(TaskNum)  \r\n" + 
        "\t\t\t\t\tFROM task \r\n" + 
        "\t\t\t\t\tWHERE  \r\n" + 
        "\t\t\t\t\t\tTaskListNum=1697  -- Triage task list.\r\n" + 
        "\t\t\t\t\t\tAND TaskStatus<>2  -- Not done (new or viewed).\r\n" + 
        "\t\t\t\t\t\tAND TaskNum IN (SELECT tn.TaskNum FROM tasknote tn WHERE tn.TaskNum = task.TaskNum)  -- no notes yet\r\n" + 
        "\t\t\t\t\t) AS CountTasksWithNotes \r\n" + 
        "\t\t\t\t-- count urgent triage tasks (does not matter if it has notes or not)\r\n" + 
        "\t\t\t\t,(SELECT COUNT(TaskNum)  \r\n" + 
        "\t\t\t\t\tFROM task \r\n" + 
        "\t\t\t\t\tWHERE  \r\n" + 
        "\t\t\t\t\t\tTaskListNum=1697  -- Triage task list.\r\n" + 
        "\t\t\t\t\t\tAND TaskStatus<>2  -- Not done (new or viewed).\r\n" + 
        "\t\t\t\t\t\tAND  \r\n" + 
        "\t\t\t\t\t\t(  -- COLLATE utf8_bin means case-sesitive search\r\n" + 
        "\t\t\t\t\t\t\tDescript COLLATE utf8_bin LIKE \'%CUSTOMER%\' \r\n" + 
        "\t\t\t\t\t\t\tOR Descript COLLATE utf8_bin LIKE \'%DOWN%\' \r\n" + 
        "\t\t\t\t\t\t\tOR Descript COLLATE utf8_bin LIKE \'%URGENT%\' \r\n" + 
        "\t\t\t\t\t\t\tOR Descript COLLATE utf8_bin LIKE \'%CONFERENCE%\' \r\n" + 
        "\t\t\t\t\t\t\tOR Descript COLLATE utf8_bin LIKE \'%!!%\' \r\n" + 
        "\t\t\t\t\t\t) \r\n" + 
        "\t\t\t\t\t) AS CountUrgentTasks\t \r\n" + 
        "\t\t\t\t-- time of oldest triage task which does not already have notes \r\n" + 
        "\t\t\t\t,(SELECT IFNULL(MIN(DateTimeEntry),\'0001-01-01\') \r\n" + 
        "\t\t\t\t\tFROM task \r\n" + 
        "\t\t\t\t\tWHERE TaskListNum=1697  -- Triage task list.\r\n" + 
        "\t\t\t\t\t\tAND TaskStatus<>2  -- Not done (new or viewed).\r\n" + 
        "\t\t\t\t\t\tAND TaskNum NOT IN (SELECT tn.TaskNum FROM tasknote tn)  -- no notes yet \r\n" + 
        "\t\t\t\t\tLIMIT 1 \r\n" + 
        "\t\t\t\t\t) AS TimeOfOldestTaskWithoutNotes \r\n" + 
        "\t\t\t\t-- time of oldest urgent task or the oldest tasknote if one exists\r\n" + 
        "\t\t\t\t,(SELECT MIN(DateTimeMax) \r\n" + 
        "\t\t\t\t\tFROM\r\n" + 
        "\t\t\t\t\t\t(SELECT GREATEST(IFNULL(task.DateTimeEntry,\'0001-01-01\'), IFNULL((SELECT MAX(DateTimeNote) FROM tasknote WHERE tasknote.tasknum=task.tasknum),\'0001-01-01\')) AS DateTimeMax\r\n" + 
        "\t\t\t\t\t\tFROM task\r\n" + 
        "\t\t\t\t\t\tWHERE TaskListNum=1697 /*Triage task list*/\r\n" + 
        "\t\t\t\t\t\t\tAND TaskStatus<>2 /*Not done (new or viewed)*/\r\n" + 
        "\t\t\t\t\t\t\tAND  \r\n" + 
        "\t\t\t\t\t\t\t( Descript LIKE BINARY \'%CUSTOMER%\'\r\n" + 
        "\t\t\t\t\t\t\tOR Descript LIKE BINARY \'%DOWN%\'\r\n" + 
        "\t\t\t\t\t\t\tOR Descript LIKE BINARY \'%URGENT%\'\r\n" + 
        "\t\t\t\t\t\t\tOR Descript LIKE BINARY \'%CONFERENCE%\'\r\n" + 
        "\t\t\t\t\t\t\tOR Descript LIKE BINARY \'%!!%\'\r\n" + 
        "\t\t\t\t\t\t\t)\r\n" + 
        "\t\t\t\t\t\t) temp\r\n" + 
        "\t\t\t\t\t) AS TimeOfOldestUrgentTaskNote;";
        return Db.getTable(command);
    }

    /**
    * sorting class used to sort Phone in various ways
    */
    public static class PhoneComparer  extends IComparer<Phone> 
    {
        private OpenDentBusiness.Phones.PhoneComparer.SortBy SortOn = OpenDentBusiness.Phones.PhoneComparer.SortBy.name;
        public PhoneComparer(OpenDentBusiness.Phones.PhoneComparer.SortBy sortBy) throws Exception {
            SortOn = sortBy;
        }

        public int compare(Phone x, Phone y) throws Exception {
            int retVal = 0;
            switch(SortOn)
            {
                case empNum: 
                    retVal = x.EmployeeNum.CompareTo(y.EmployeeNum);
                    break;
                case ext: 
                    retVal = x.Extension.CompareTo(y.Extension);
                    break;
                case name: 
                default: 
                    retVal = x.EmployeeName.CompareTo(y.EmployeeName);
                    break;
            
            }
            if (retVal == 0)
            {
                //last name is primary tie breaker
                retVal = x.EmployeeName.CompareTo(y.EmployeeName);
                if (retVal == 0)
                {
                    //extension is seconary tie breaker
                    retVal = x.Extension.CompareTo(y.Extension);
                }
                 
            }
             
            return retVal;
        }

        //we got here so our sort was successful
        public enum SortBy
        {
            /**
            * 0 - By Extension.
            */
            ext,
            /**
            * 1 - By EmployeeNum.
            */
            empNum,
            /**
            * 2 - By Name.
            */
            name
        }
    }

}


