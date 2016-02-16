//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:56 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Credentials;
import OpenDentBusiness.Db;
import OpenDentBusiness.DtoGetObject;
import OpenDentBusiness.DtoObject;
import OpenDentBusiness.GroupPermissionC;
import OpenDentBusiness.GroupPermissions;
import OpenDentBusiness.Lans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Security;
import OpenDentBusiness.Userod;
import OpenDentBusiness.Userods;

/**
* 
*/
public class Security   
{
    /**
    * The current user.  Might be null when first starting the program.  Otherwise, must contain valid user.
    */
    private static Userod curUser;
    /**
    * Remember the password that the user typed in.  Do not store it in the database.  We will need it when connecting to the web service.  Probably blank if not connected to the web service.  If eCW, then this is already encrypted.
    */
    public static String PasswordTyped = new String();
    public static Userod getCurUser() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ServerWeb)
        {
            throw new ApplicationException("Security.Userod not accessible from RemotingRole.ServerWeb.");
        }
         
        return curUser;
    }

    public static void setCurUser(Userod value) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ServerWeb)
        {
            throw new ApplicationException("Security.Userod not accessible from RemotingRole.ServerWeb.");
        }
         
        curUser = value;
    }

    /**
    * 
    */
    public Security() throws Exception {
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Checks to see if current user is authorized.  It also checks any date restrictions.  If not authorized, it gives a Message box saying so and returns false.
    */
    public static boolean isAuthorized(OpenDentBusiness.Permissions perm) throws Exception {
        return IsAuthorized(perm, DateTime.MinValue, false);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Checks to see if current user is authorized.  It also checks any date restrictions.  If not authorized, it gives a Message box saying so and returns false.
    */
    public static boolean isAuthorized(OpenDentBusiness.Permissions perm, DateTime date) throws Exception {
        return isAuthorized(perm,date,false);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Checks to see if current user is authorized.  It also checks any date restrictions.  If not authorized, it gives a Message box saying so and returns false.
    */
    public static boolean isAuthorized(OpenDentBusiness.Permissions perm, boolean suppressMessage) throws Exception {
        return IsAuthorized(perm, DateTime.MinValue, suppressMessage);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Checks to see if current user is authorized.  It also checks any date restrictions.  If not authorized, it gives a Message box saying so and returns false.
    */
    public static boolean isAuthorized(OpenDentBusiness.Permissions perm, DateTime date, boolean suppressMessage) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (Security.getCurUser() == null)
        {
            if (!suppressMessage)
            {
                MessageBox.Show(Lans.g("Security","Not authorized for") + "\r\n" + GroupPermissions.getDesc(perm));
            }
             
            return false;
        }
         
        try
        {
            return isAuthorized(perm,date,suppressMessage,curUser.UserGroupNum);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return false;
        }
    
    }

    /**
    * Will throw an error if not authorized and message not suppressed.
    */
    public static boolean isAuthorized(OpenDentBusiness.Permissions perm, DateTime date, boolean suppressMessage, long userGroupNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (!GroupPermissions.hasPermission(userGroupNum,perm))
        {
            if (!suppressMessage)
            {
                throw new Exception(Lans.g("Security","Not authorized for") + "\r\n" + GroupPermissions.getDesc(perm));
            }
             
            return false;
        }
         
        if (perm == OpenDentBusiness.Permissions.AccountingCreate || perm == OpenDentBusiness.Permissions.AccountingEdit)
        {
            if (date <= PrefC.getDate(PrefName.AccountingLockDate))
            {
                if (!suppressMessage)
                {
                    throw new Exception(Lans.g("Security","Locked by Administrator."));
                }
                 
                return false;
            }
             
        }
         
        //Check the global security lock------------------------------------------------------------------------------------
        //the list below is NOT the list of permissions that take dates. See GroupPermissions.PermTakesDates().
        //|| perm==Permissions.ImageDelete
        if (perm == OpenDentBusiness.Permissions.AdjustmentCreate || perm == OpenDentBusiness.Permissions.AdjustmentEdit || perm == OpenDentBusiness.Permissions.PaymentCreate || perm == OpenDentBusiness.Permissions.PaymentEdit || perm == OpenDentBusiness.Permissions.ProcComplCreate || perm == OpenDentBusiness.Permissions.ProcComplEdit || perm == OpenDentBusiness.Permissions.InsPayCreate || perm == OpenDentBusiness.Permissions.InsPayEdit || perm == OpenDentBusiness.Permissions.SheetEdit || perm == OpenDentBusiness.Permissions.CommlogEdit)
        {
            //If the global lock is date-based:
            //if a valid date was passed in
            if (date.Year > 1 && date <= PrefC.getDate(PrefName.SecurityLockDate))
            {
                //and that date is earlier than the lock
                //if admins are locked out too
                if (PrefC.getBool(PrefName.SecurityLockIncludesAdmin) || !GroupPermissions.hasPermission(userGroupNum,OpenDentBusiness.Permissions.SecurityAdmin))
                {
                    //or is not an admin
                    if (!suppressMessage)
                    {
                        throw new Exception(Lans.g("Security","Locked by Administrator before ") + PrefC.getDate(PrefName.SecurityLockDate).ToShortDateString());
                    }
                     
                    return false;
                }
                 
            }
             
            //If the global lock is days-based:
            //if a valid date was passed in
            if (date.Year > 1 && PrefC.getInt(PrefName.SecurityLockDays) > 0 && date <= DateTime.Today.AddDays(-PrefC.getInt(PrefName.SecurityLockDays)))
            {
                //and that date is earlier than the lock
                //if admins are locked out too
                if (PrefC.getBool(PrefName.SecurityLockIncludesAdmin) || !GroupPermissions.hasPermission(userGroupNum,OpenDentBusiness.Permissions.SecurityAdmin))
                {
                    //or is not an admin
                    if (!suppressMessage)
                    {
                        throw new Exception(Lans.g("Security","Locked by Administrator before ") + PrefC.getInt(PrefName.SecurityLockDays).ToString() + " days.");
                    }
                     
                    return false;
                }
                 
            }
             
        }
         
        //Check date/days limits on individual permission----------------------------------------------------------------
        if (!GroupPermissions.permTakesDates(perm))
        {
            return true;
        }
         
        DateTime dateLimit = getDateLimit(perm,userGroupNum);
        if (date > dateLimit)
        {
            return true;
        }
         
        //authorized
        //Prevents certain bugs when 1/1/1 dates are passed in and compared----------------------------------------------
        //Handling of min dates.  There might be others, but we have to handle them individually to avoid introduction of bugs.
        //no date sent was entered before setting claim received
        //a completed procedure with a min date.
        //a claim payment with no date.
        //usually from a conversion
        if (perm == OpenDentBusiness.Permissions.ClaimSentEdit || perm == OpenDentBusiness.Permissions.ProcComplEdit || perm == OpenDentBusiness.Permissions.InsPayEdit || perm == OpenDentBusiness.Permissions.TreatPlanEdit || perm == OpenDentBusiness.Permissions.AdjustmentEdit || perm == OpenDentBusiness.Permissions.CommlogEdit || perm == OpenDentBusiness.Permissions.ProcDelete)
        {
            //because older versions did not set the DateEntryC.
            if (date.Year < 1880 && dateLimit.Year < 1880)
            {
                return true;
            }
             
        }
         
        if (!suppressMessage)
        {
            throw new Exception(Lans.g("Security","Not authorized for") + "\r\n" + GroupPermissions.getDesc(perm) + "\r\n" + Lans.g("Security","Date limitation"));
        }
         
        return false;
    }

    private static DateTime getDateLimit(OpenDentBusiness.Permissions permType, long userGroupNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        DateTime nowDate = OpenDentBusiness.MiscData.getNowDateTime().Date;
        DateTime retVal = DateTime.MinValue;
        for (int i = 0;i < GroupPermissionC.getList().Length;i++)
        {
            if (GroupPermissionC.getList()[i].UserGroupNum != userGroupNum || GroupPermissionC.getList()[i].PermType != permType)
            {
                continue;
            }
             
            //this should only happen once.  One match.
            if (GroupPermissionC.getList()[i].NewerDate.Year > 1880)
            {
                retVal = GroupPermissionC.getList()[i].NewerDate;
            }
             
            if (GroupPermissionC.getList()[i].NewerDays == 0)
            {
            }
            else //do not restrict by days
            //do not change retVal
            if (nowDate.AddDays(-GroupPermissionC.getList()[i].NewerDays) > retVal)
            {
                retVal = nowDate.AddDays(-GroupPermissionC.getList()[i].NewerDays);
            }
              
        }
        return retVal;
    }

    /**
    * Gets a module that the user has permission to use.  Tries the suggestedI first.  If a -1 is supplied, it tries to find any authorized module.  If no authorization for any module, it returns a -1, causing no module to be selected.
    */
    public static int getModule(int suggestI) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (suggestI != -1 && IsAuthorized(permofModule(suggestI), DateTime.MinValue, true))
        {
            return suggestI;
        }
         
        for (int i = 0;i < 7;i++)
        {
            if (IsAuthorized(permofModule(i), DateTime.MinValue, true))
            {
                return i;
            }
             
        }
        return -1;
    }

    private static OpenDentBusiness.Permissions permofModule(int i) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(i)
        {
            case 0: 
                return OpenDentBusiness.Permissions.AppointmentsModule;
            case 1: 
                return OpenDentBusiness.Permissions.FamilyModule;
            case 2: 
                return OpenDentBusiness.Permissions.AccountModule;
            case 3: 
                return OpenDentBusiness.Permissions.TPModule;
            case 4: 
                return OpenDentBusiness.Permissions.ChartModule;
            case 5: 
                return OpenDentBusiness.Permissions.ImagesModule;
            case 6: 
                return OpenDentBusiness.Permissions.ManageModule;
        
        }
        return OpenDentBusiness.Permissions.None;
    }

    //<summary>Obsolete</summary>
    //public static void ResetPassword(){
    //FIXME:UPDATE-MULTIPLE-TABLES
    /*string command="UPDATE userod,grouppermissions SET userod.Password='' "
    				+"WHERE grouppermissions.UserGroupNum=userod.UserGroupNum "
    				+"AND grouppermissions.PermType=24";
     			Db.NonQ(command);
    			 */
    //Code updated to be compatible with Oracle as well as MySQL.
    /*
    			string command="SELECT userod.UserNum FROM userod,grouppermissions "
    				+"WHERE grouppermissions.UserGroupNum=userod.UserGroupNum "
    				+"AND grouppermissions.PermType=24";
    			DataTable table=Db.GetTable(command); 
    			if(table.Rows.Count==0){
    				throw new ApplicationException("No admin exists.");
    			}
    			command="UPDATE userod SET Password='' WHERE UserNum="+POut.PString(table.Rows[0][0].ToString());
    			Db.NonQ(command);
    		}*/
    /**
    * RemotingRole has not yet been set to ClientWeb, but it will if this succeeds.  Will throw an exception if server cannot validate username and password.  configPath will be empty from a workstation and filled from the server.  If Ecw, odpass will actually be the hash.
    */
    public static Userod logInWeb(String oduser, String odpass, String configPath, String clientVersionStr, boolean usingEcw) throws Exception {
        //Very unusual method.  Remoting role can't be checked, but is implied by the presence of a value in configPath.
        if (!StringSupport.equals(configPath, ""))
        {
            //RemotingRole.ServerWeb
            Userods.loadDatabaseInfoFromFile(CodeBase.ODFileUtils.combinePaths(configPath,"OpenDentalServerConfig.xml"));
            //ODFileUtils.CombinePaths(
            //  ,"OpenDentalServerConfig.xml"));
            //Path.GetDirectoryName(Application.ExecutablePath),"OpenDentalServerConfig.xml"));
            //Application.StartupPath,"OpenDentalServerConfig.xml"));
            //Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location),"OpenDentalServerConfig.xml"));
            //Environment.CurrentDirectory,"OpenDentalServerConfig.xml"));
            //Then, check username and password
            Userod user = Userods.checkUserAndPassword(oduser,odpass,usingEcw);
            if (user == null)
            {
                throw new Exception("Invalid username or password.");
            }
             
            String command = "SELECT ValueString FROM preference WHERE PrefName='ProgramVersion'";
            String dbVersionStr = Db.getScalar(command);
            String serverVersionStr = Assembly.GetAssembly(Db.class).GetName().Version.ToString(4);
            if (!StringSupport.equals(dbVersionStr, serverVersionStr))
            {
                throw new Exception("Version mismatch.  Server:" + serverVersionStr + "  Database:" + dbVersionStr);
            }
             
            Version clientVersion = new Version(clientVersionStr);
            Version serverVersion = new Version(serverVersionStr);
            if (clientVersion > serverVersion)
            {
                throw new Exception("Version mismatch.  Client:" + clientVersionStr + "  Server:" + serverVersionStr);
            }
             
            return user;
        }
        else
        {
            //if clientVersion == serverVersion, than we need do nothing.
            //if clientVersion < serverVersion, than an update will later be triggered.
            //Security.CurUser=user;//we're on the server, so this is meaningless
            //return 0;//meaningless
            //Because RemotingRole has not been set, and because CurUser has not been set,
            //this particular method is more verbose than most and does not use Meth.
            //It's not a good example of the standard way of doing things.
            DtoGetObject dto = new DtoGetObject();
            dto.Credentials = new Credentials();
            dto.Credentials.Username = oduser;
            dto.Credentials.Password = odpass;
            //Userods.EncryptPassword(password);
            dto.MethodName = "Security.LogInWeb";
            dto.ObjectType = Userod.class.FullName;
            Object[] parameters = new Object[]{ oduser, odpass, configPath, clientVersionStr, usingEcw };
            Type[] objTypes = new Type[]{ String.class, String.class, String.class, String.class, boolean.class };
            dto.Params = DtoObject.ConstructArray(parameters, objTypes);
            return RemotingClient.processGetObject(dto);
        } 
    }

}


//can throw exception