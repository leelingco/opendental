//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Credentials;
import OpenDentBusiness.Db;
import OpenDentBusiness.GroupPermissionC;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Userod;
import OpenDentBusiness.UserodC;

/**
* (Users OD)
*/
public class Userods   
{
    //private static bool webServerConfigHasLoadedd=false;
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM userod ORDER BY UserName";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Userod";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        UserodC.setListt(Crud.UserodCrud.TableToList(table));
    }

    /**
    * 
    */
    public static Userod getUser(long userNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (UserodC.getListt() == null)
        {
            refreshCache();
        }
         
        for (int i = 0;i < UserodC.getListt().Count;i++)
        {
            if (UserodC.getListt()[i].UserNum == userNum)
            {
                return UserodC.getListt()[i];
            }
             
        }
        return null;
    }

    /**
    * Returns null if not found.  isEcwTight is not case sensitive.
    */
    public static Userod getUserByName(String userName, boolean isEcwTight) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (UserodC.getListt() == null)
        {
            refreshCache();
        }
         
        for (int i = 0;i < UserodC.getListt().Count;i++)
        {
            if (UserodC.getListt()[i].IsHidden)
            {
                continue;
            }
             
            if (isEcwTight)
            {
                if (UserodC.getListt()[i].UserName.ToLower() == userName.ToLower())
                {
                    return UserodC.getListt()[i];
                }
                 
            }
            else
            {
                if (StringSupport.equals(UserodC.getListt()[i].UserName, userName))
                {
                    return UserodC.getListt()[i];
                }
                 
            } 
        }
        return null;
    }

    //exact
    /**
    * Returns null if not found.
    */
    public static Userod getUserByEmployeeNum(long employeeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (UserodC.getListt() == null)
        {
            refreshCache();
        }
         
        for (int i = 0;i < UserodC.getListt().Count;i++)
        {
            if (UserodC.getListt()[i].EmployeeNum == employeeNum)
            {
                return UserodC.getListt()[i];
            }
             
        }
        return null;
    }

    /**
    * This handles situations where we have a usernum, but not a user.  And it handles usernum of zero.
    */
    public static String getName(long userNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (userNum == 0)
        {
            return "";
        }
         
        Userod user = getUser(userNum);
        if (user == null)
        {
            return "";
        }
         
        return user.UserName;
    }

    /**
    * Only used in one place on the server when first attempting to log on.  The password will be hashed and checked against the one in the database.  Password is required, so empty string will return null.  Returns a user object if user and password are valid.  Otherwise, returns null.  If usingEcw, password will actually be the hash.  If usingEcw, then the username is not case sensitive.
    */
    public static Userod checkUserAndPassword(String username, String password, boolean usingEcw) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(password, ""))
        {
            return null;
        }
         
        refreshCache();
        Userod user = getUserByName(username,usingEcw);
        if (user == null)
        {
            return null;
        }
         
        if (usingEcw)
        {
            if (StringSupport.equals(user.Password, password))
            {
                return user;
            }
             
        }
        else if (StringSupport.equals(user.Password, encryptPassword(password)))
        {
            return user;
        }
          
        return null;
    }

    /**
    * Used by Server.  Throws exception if bad username or passhash or if either are blank.  It uses cached user list, refreshing it if null.  This is used everywhere except in the log on screen.
    */
    public static void checkCredentials(Credentials cred) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(cred.Username, "") || StringSupport.equals(cred.Password, ""))
        {
            throw new ApplicationException("Invalid username or password.");
        }
         
        Userod userod = null;
        for (int i = 0;i < UserodC.getListt().Count;i++)
        {
            if (StringSupport.equals(UserodC.getListt()[i].UserName, cred.Username))
            {
                userod = UserodC.getListt()[i];
                break;
            }
             
        }
        if (userod == null)
        {
            throw new ApplicationException("Invalid username or password.");
        }
         
        boolean useEcwAlgorithm = Programs.isEnabled(ProgramName.eClinicalWorks);
        if (useEcwAlgorithm)
        {
            if (!StringSupport.equals(userod.Password, cred.Password))
            {
                throw new ApplicationException("Invalid username or password.");
            }
             
        }
        else if (!StringSupport.equals(userod.Password, encryptPassword(cred.Password)))
        {
            throw new ApplicationException("Invalid username or password.");
        }
          
    }

    /**
    * Will throw an exception if it fails for any reason.  This will directly access the config file on the disk, read the values, and set the DataConnection to the new database.  This is only triggered when someone tries to log on.
    */
    public static void loadDatabaseInfoFromFile(String configFilePath) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (!File.Exists(configFilePath))
        {
            throw new Exception("Could not find " + configFilePath + " on the web server.");
        }
         
        XmlDocument doc = new XmlDocument();
        try
        {
            doc.Load(configFilePath);
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new Exception("Web server " + configFilePath + " could not be opened or is in an invalid format.");
        }

        XPathNavigator Navigator = doc.CreateNavigator();
        //always picks the first database entry in the file:
        XPathNavigator navConn = Navigator.SelectSingleNode("//DatabaseConnection");
        //[Database='"+database+"']");
        if (navConn == null)
        {
            throw new Exception(configFilePath + " does not contain a valid database entry.");
        }
         
        //database+" is not an allowed database.");
        String connString = "", server = "", database = "", mysqlUser = "", mysqlPassword = "", mysqlUserLow = "", mysqlPasswordLow = "";
        XPathNavigator navConString = navConn.SelectSingleNode("ConnectionString");
        if (navConString != null)
        {
            //If there is a connection string then use it.
            connString = navConString.Value;
        }
        else
        {
            //return navOne.SelectSingleNode("summary").Value;
            //now, get the values for this connection
            server = navConn.SelectSingleNode("ComputerName").Value;
            database = navConn.SelectSingleNode("Database").Value;
            mysqlUser = navConn.SelectSingleNode("User").Value;
            mysqlPassword = navConn.SelectSingleNode("Password").Value;
            mysqlUserLow = navConn.SelectSingleNode("UserLow").Value;
            mysqlPasswordLow = navConn.SelectSingleNode("PasswordLow").Value;
        } 
        XPathNavigator dbTypeNav = navConn.SelectSingleNode("DatabaseType");
        OpenDentBusiness.DatabaseType dbtype = OpenDentBusiness.DatabaseType.MySql;
        if (dbTypeNav != null)
        {
            if (StringSupport.equals(dbTypeNav.Value, "Oracle"))
            {
                dbtype = OpenDentBusiness.DatabaseType.Oracle;
            }
             
        }
         
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        if (!StringSupport.equals(connString, ""))
        {
            try
            {
                dcon.setDb(connString,"",dbtype);
            }
            catch (Exception e)
            {
                throw new Exception(e.Message + "\r\n" + "Connection to database failed.  Check the values in the config file on the web server " + configFilePath);
            }
        
        }
        else
        {
            try
            {
                dcon.setDb(server,database,mysqlUser,mysqlPassword,mysqlUserLow,mysqlPasswordLow,dbtype);
            }
            catch (Exception e)
            {
                throw new Exception(e.Message + "\r\n" + "Connection to database failed.  Check the values in the config file on the web server " + configFilePath);
            }
        
        } 
    }

    //todo?: make sure no users have blank passwords.
    /*
    		///<summary>Used by the SL logon window to validate credentials.  Send in the password unhashed.  If invalid, it will always throw an exception of some type.  If it is valid, then it will return the hashed password.  This is the only place where the config file is read, and it's only read on startup.  So the web service needs to be restarted if the config file changes.</summary>
    		public static string CheckDbUserPassword(string configFilePath,string username,string password){
    			//for some reason, this static variable was remaining true even if the webservice was restarted.
    			//So we're not going to use it anymore.  Always load from file.
    			//if(!webServerConfigHasLoadedd){
    				LoadDatabaseInfoFromFile(configFilePath);
    			//	webServerConfigHasLoadedd=true;
    			//}
    			DataConnection dcon=new DataConnection();
    			//Then, check username and password
    			string passhash="";
    			string command="SELECT Password FROM userod WHERE UserName='"+POut.PString(username)+"'";
    			DataTable table=dcon.GetTable(command);
    			if(table.Rows.Count!=0){
    				passhash=table.Rows[0][0].ToString();
    			}
    			if(passhash=="" || passhash!=EncryptPassword(password)){
    				throw new Exception("Invalid username or password.");
    			}
    			return passhash;
    		}*/
    /**
    * 
    */
    public static String encryptPassword(String inputPass) throws Exception {
        //No need to check RemotingRole; no call to db.
        boolean useEcwAlgorithm = Programs.isEnabled(ProgramName.eClinicalWorks);
        return encryptPassword(inputPass,useEcwAlgorithm);
    }

    /**
    * Creates a hash.
    */
    public static String encryptPassword(String inputPass, boolean useEcwAlgorithm) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(inputPass, ""))
        {
            return "";
        }
         
        HashAlgorithm algorithm = HashAlgorithm.Create("MD5");
        if (useEcwAlgorithm)
        {
            // && Programs.IsEnabled("eClinicalWorks")) {
            byte[] asciiBytes = Encoding.ASCII.GetBytes(inputPass);
            byte[] hashbytes = algorithm.ComputeHash(asciiBytes);
            //length=16
            byte digit1 = new byte();
            byte digit2 = new byte();
            String char1 = new String();
            String char2 = new String();
            StringBuilder strbuild = new StringBuilder();
            for (int i = 0;i < hashbytes.Length;i++)
            {
                if (hashbytes[i] == 0)
                {
                    digit1 = 0;
                    digit2 = 0;
                }
                else
                {
                    digit1 = (byte)Math.Floor((double)hashbytes[i] / 16d);
                    //double remainder=Math.IEEERemainder((double)hashbytes[i],16d);
                    digit2 = (byte)(hashbytes[i] - (byte)(16 * digit1));
                } 
                char1 = ByteToStr(digit1);
                char2 = ByteToStr(digit2);
                strbuild.Append(char1);
                strbuild.Append(char2);
            }
            return strbuild.ToString();
        }
        else
        {
            //typical
            byte[] unicodeBytes = Encoding.Unicode.GetBytes(inputPass);
            byte[] hashbytes2 = algorithm.ComputeHash(unicodeBytes);
            return Convert.ToBase64String(hashbytes2);
        } 
    }

    /**
    * The only valid input is a value between 0 and 15.  Text returned will be 1-9 or a-f.
    */
    private static String byteToStr(Byte byteVal) throws Exception {
        //No need to check RemotingRole; no call to db.
        Byte __dummyScrutVar0 = byteVal;
        if (__dummyScrutVar0.equals(10))
        {
            return "a";
        }
        else if (__dummyScrutVar0.equals(11))
        {
            return "b";
        }
        else if (__dummyScrutVar0.equals(12))
        {
            return "c";
        }
        else if (__dummyScrutVar0.equals(13))
        {
            return "d";
        }
        else if (__dummyScrutVar0.equals(14))
        {
            return "e";
        }
        else if (__dummyScrutVar0.equals(15))
        {
            return "f";
        }
        else
        {
            return byteVal.ToString();
        }      
    }

    /**
    * Used from log on screen, phoneUI, and when logging in via command line.
    */
    public static boolean checkTypedPassword(String inputPass, String hashedPass) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(hashedPass, ""))
        {
            return StringSupport.equals(inputPass, "");
        }
         
        String hashedInput = encryptPassword(inputPass);
        return StringSupport.equals(hashedInput, hashedPass);
    }

    //MessageBox.Show(
    //Debug.WriteLine(hashedInput+","+hashedPass);
    /**
    * usertype can be 'all', 'prov', 'emp', or 'other'.
    */
    public static List<Userod> refreshSecurity(String usertype, long schoolClassNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Userod>>GetObject(MethodBase.GetCurrentMethod(), usertype, schoolClassNum);
        }
         
        String command = new String();
        if (StringSupport.equals(usertype, "prov") && schoolClassNum > 0)
        {
            command = "SELECT userod.* FROM userod,provider " + "WHERE userod.ProvNum=provider.ProvNum " + "AND SchoolClassNum=" + POut.long(schoolClassNum) + " ORDER BY UserName";
            return Crud.UserodCrud.SelectMany(command);
        }
         
        command = "SELECT * FROM userod ";
        if (StringSupport.equals(usertype, "emp"))
        {
            command += "WHERE EmployeeNum!=0 ";
        }
        else if (StringSupport.equals(usertype, "prov"))
        {
            //and all schoolclassnums
            command += "WHERE ProvNum!=0 ";
        }
        else if (StringSupport.equals(usertype, "all"))
        {
        }
        else //command+="";
        if (StringSupport.equals(usertype, "other"))
        {
            command += "WHERE ProvNum=0 AND EmployeeNum=0 ";
        }
            
        command += "ORDER BY UserName";
        return Crud.UserodCrud.SelectMany(command);
    }

    /**
    * Surround with try/catch because it can throw exceptions.
    */
    public static void update(Userod userod) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), userod);
            return ;
        }
         
        validate(false,userod,false);
        Crud.UserodCrud.Update(userod);
    }

    /**
    * Surround with try/catch because it can throw exceptions.  Only used from FormOpenDental.menuItemPassword_Click().  Same as Update(), only the Validate call skips checking duplicate names for hidden users.
    */
    public static void updatePassword(Userod userod) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), userod);
            return ;
        }
         
        validate(false,userod,true);
        Crud.UserodCrud.Update(userod);
    }

    /**
    * Surround with try/catch because it can throw exceptions.
    */
    public static long insert(Userod userod) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            userod.UserNum = Meth.GetLong(MethodBase.GetCurrentMethod(), userod);
            return userod.UserNum;
        }
         
        validate(true,userod,false);
        return Crud.UserodCrud.Insert(userod);
    }

    /**
    * Surround with try/catch because it can throw exceptions.  We don't really need to make this public, but it's required in order to follow the RemotingRole pattern.
    */
    public static void validate(boolean isNew, Userod user, boolean excludeHiddenUsers) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), isNew, user, excludeHiddenUsers);
            return ;
        }
         
        //should add a check that employeenum and provnum are not both set.
        //make sure username is not already taken
        String command = new String();
        long excludeUserNum = new long();
        if (isNew)
        {
            excludeUserNum = 0;
        }
        else
        {
            excludeUserNum = user.UserNum;
        } 
        //it's ok if the name matches the current username
        //It doesn't matter if the UserName is already in use if the user being updated is going to be hidden.  This check will block them from unhiding duplicate users.
        if (!user.IsHidden)
        {
            //if the user is now not hidden
            if (!isUserNameUnique(user.UserName,excludeUserNum,excludeHiddenUsers))
            {
                throw new Exception(Lans.g("Userods","UserName already in use."));
            }
             
        }
         
        //make sure that there would still be at least one user with security admin permissions
        if (!isNew)
        {
            command = "SELECT COUNT(*) FROM grouppermission " + "WHERE PermType='" + POut.Long(((Enum)OpenDentBusiness.Permissions.SecurityAdmin).ordinal()) + "' " + "AND UserGroupNum=" + POut.long(user.UserGroupNum);
            if (StringSupport.equals(Db.getCount(command), "0"))
            {
                //if this user would not have admin
                //make sure someone else has admin
                command = "SELECT COUNT(*) FROM userod,grouppermission " + "WHERE grouppermission.PermType='" + POut.Long(((Enum)OpenDentBusiness.Permissions.SecurityAdmin).ordinal()) + "'" + " AND userod.UserGroupNum=grouppermission.UserGroupNum" + " AND userod.IsHidden =0" + " AND userod.UserNum != " + POut.long(user.UserNum);
                if (StringSupport.equals(Db.getCount(command), "0"))
                {
                    throw new Exception(Lans.g("Users","At least one user must have Security Admin permission."));
                }
                 
            }
             
        }
         
        //there are no other users with this permission
        //an admin user can never be hidden
        command = "SELECT COUNT(*) FROM grouppermission " + "WHERE PermType='" + POut.Long(((Enum)OpenDentBusiness.Permissions.SecurityAdmin).ordinal()) + "' " + "AND UserGroupNum=" + POut.long(user.UserGroupNum);
        //if this user is admin
        if (!StringSupport.equals(Db.getCount(command), "0") && user.IsHidden)
        {
            throw new Exception(Lans.g("Userods","Admins cannot be hidden."));
        }
         
    }

    //and hidden
    /**
    * Supply 0 or -1 for the excludeUserNum to not exclude any.
    */
    public static boolean isUserNameUnique(String username, long excludeUserNum, boolean excludeHiddenUsers) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), username, excludeUserNum, excludeHiddenUsers);
        }
         
        if (StringSupport.equals(username, ""))
        {
            return false;
        }
         
        String command = "SELECT COUNT(*) FROM userod WHERE ";
        //if(Programs.UsingEcwTight()){
        //	command+="BINARY ";//allows different usernames based on capitalization.//we no longer allow this
        //Does not need to be tested under Oracle because eCW users do not use Oracle.
        //}
        command += "UserName='" + POut.string(username) + "' " + "AND UserNum !=" + POut.long(excludeUserNum) + " ";
        if (excludeHiddenUsers)
        {
            command += "AND IsHidden=0";
        }
         
        //not hidden
        DataTable table = Db.getTable(command);
        if (StringSupport.equals(table.Rows[0][0].ToString(), "0"))
        {
            return true;
        }
         
        return false;
    }

    /**
    * Used in FormSecurity.FillTreeUsers
    */
    public static List<Userod> getForGroup(long userGroupNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        //ArrayList al=new ArrayList();
        List<Userod> retVal = new List<Userod>();
        for (int i = 0;i < UserodC.getListt().Count;i++)
        {
            if (UserodC.getListt()[i].UserGroupNum == userGroupNum)
            {
                retVal.Add(UserodC.getListt()[i]);
            }
             
        }
        return retVal;
    }

    //User[] retVal=new User[al.Count];
    //al.CopyTo(retVal);
    /**
    * This always returns one admin user.  There must be one and there is rarely more than one.  Only used on startup to determine if security is being used.
    */
    public static Userod getAdminUser() throws Exception {
        for (int i = 0;i < GroupPermissionC.getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            //just find any permission for security admin.  There has to be one.
            if (GroupPermissionC.getList()[i].PermType != OpenDentBusiness.Permissions.SecurityAdmin)
            {
                continue;
            }
             
            for (int j = 0;j < UserodC.getListt().Count;j++)
            {
                if (UserodC.getListt()[j].UserGroupNum == GroupPermissionC.getList()[i].UserGroupNum)
                {
                    return UserodC.getListt()[j];
                }
                 
            }
        }
        return null;
    }

    //will never happen
    /**
    * Will return 0 if no inbox found for user.
    */
    public static long getInbox(long userNum) throws Exception {
        for (int i = 0;i < UserodC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (UserodC.getListt()[i].UserNum == userNum)
            {
                return UserodC.getListt()[i].TaskListInBox;
            }
             
        }
        return 0;
    }

    /**
    * 
    */
    public static List<Userod> getNotHidden() throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Userod> retVal = new List<Userod>();
        for (int i = 0;i < UserodC.getListt().Count;i++)
        {
            if (!UserodC.getListt()[i].IsHidden)
            {
                retVal.Add(UserodC.getListt()[i].Copy());
            }
             
        }
        return retVal;
    }

    //retVal.Sort(//in a hurry, so skipping
    //Return 3, which is non-admin provider type
    public static long getAnesthProvType(long anesthProvType) throws Exception {
        for (int i = 0;i < UserodC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (UserodC.getListt()[i].AnesthProvType == anesthProvType)
            {
                return UserodC.getListt()[i].AnesthProvType;
            }
             
        }
        return 3;
    }

    /**
    * Returns empty string if password is strong enough.  Otherwise, returns explanation of why it's not strong enough.
    */
    public static String isPasswordStrong(String pass) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(pass, ""))
        {
            return Lans.g("FormUserPassword","Password may not be blank when the strong password feature is turned on.");
        }
         
        if (pass.Length < 8)
        {
            return Lans.g("FormUserPassword","Password must be at least eight characters long when the strong password feature is turned on.");
        }
         
        boolean containsCap = false;
        for (int i = 0;i < pass.Length;i++)
        {
            if (Char.IsUpper(pass[i]))
            {
                containsCap = true;
            }
             
        }
        if (!containsCap)
        {
            return Lans.g("FormUserPassword","Password must contain at least one capital letter when the strong password feature is turned on.");
        }
         
        /*
        			bool containsPunct=false;
        			for(int i=0;i<pass.Length;i++) {
        				if(!Char.IsLetterOrDigit(pass[i])) {
        					containsPunct=true;
        				}
        			}
        			if(!containsPunct) {
        				return Lans.g("FormUserPassword","Password must contain at least one punctuation or symbol character when the strong password feature is turned on.");
        			}*/
        boolean containsNum = false;
        for (int i = 0;i < pass.Length;i++)
        {
            if (Char.IsNumber(pass[i]))
            {
                containsNum = true;
            }
             
        }
        if (!containsNum)
        {
            return Lans.g("FormUserPassword","Password must contain at least one number when the strong password feature is turned on.");
        }
         
        return "";
    }

    /**
    * This resets the strong password flag on all users after an admin turns off pref PasswordsMustBeStrong.  If strong passwords are again turned on later, then each user will have to edit their password in order set the strong password flag again.
    */
    public static void resetStrongPasswordFlags() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "UPDATE userod SET PasswordIsStrong=0";
        Db.nonQ(command);
    }

}


