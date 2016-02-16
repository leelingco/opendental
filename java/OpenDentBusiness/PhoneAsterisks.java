//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.AsteriskRingGroups;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.Signalods;

/**
* This entire class is only used at Open Dental, Inc HQ.  So for that special environment, many things are hard-coded.
*/
public class PhoneAsterisks   
{
    private static final String ipAddressAsterisk = "10.10.1.197";
    //was 192.168.0.197
    public static void setToDefaultRingGroups(int extension, long employeeNum) throws Exception {
        //First, figure out what the defaults are for this employee
        AsteriskRingGroups ringGroups = PhoneEmpDefaults.getRingGroup(employeeNum);
        /*if(ringGroup==AsteriskRingGroups.All) {
        				SetToAllRingGroups(extension,employeeNum);
        			}
        			if(ringGroup==AsteriskRingGroups.None) {
        				RemoveFromRingGroups(extension,employeeNum);
        			}
        			if(ringGroup==AsteriskRingGroups.Backup) {
        				SetToBackupRingGroupOnly(extension,employeeNum);
        			}*/
        setRingGroups(extension,ringGroups);
    }

    public static void setRingGroups(int extension, AsteriskRingGroups ringGroups) throws Exception {
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection(ipAddressAsterisk,"asterisk","opendental","secret",OpenDentBusiness.DatabaseType.MySql);
        String command = "SELECT grpnum,grplist FROM ringgroups WHERE grpnum = '601' OR grpnum = '609'";
        DataTable table = null;
        try
        {
            table = dcon.getTable(command);
        }
        catch (Exception __dummyCatchVar0)
        {
            return ;
        }

        //if remotely connecting from home
        String rawExtensions601 = "";
        String rawExtensions609 = "";
        String[] arrayExtensions601 = new String[0];
        String[] arrayExtensions609 = new String[0];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (StringSupport.equals(table.Rows[i]["grpnum"].ToString(), "601"))
            {
                //there should always be exactly one
                rawExtensions601 = table.Rows[i]["grplist"].ToString();
                arrayExtensions601 = rawExtensions601.Split(new char[]{ '-' }, StringSplitOptions.RemoveEmptyEntries);
            }
             
            if (StringSupport.equals(table.Rows[i]["grpnum"].ToString(), "609"))
            {
                //there should always be exactly one
                rawExtensions609 = table.Rows[i]["grplist"].ToString();
                arrayExtensions609 = rawExtensions609.Split(new char[]{ '-' }, StringSplitOptions.RemoveEmptyEntries);
            }
             
        }
        List<String> listExtension601 = new List<String>();
        boolean isIn601 = false;
        for (int i = 0;i < arrayExtensions601.Length;i++)
        {
            //we won't test to make sure each item is a pure number.
            listExtension601.Add(arrayExtensions601[i]);
            if (arrayExtensions601[i] == extension.ToString())
            {
                isIn601 = true;
            }
             
        }
        List<String> listExtension609 = new List<String>();
        boolean isIn609 = false;
        for (int i = 0;i < arrayExtensions609.Length;i++)
        {
            //we won't test to make sure each item is a pure number.
            listExtension609.Add(arrayExtensions609[i]);
            if (arrayExtensions609[i] == extension.ToString())
            {
                isIn609 = true;
            }
             
        }
        if (ringGroups == AsteriskRingGroups.All)
        {
            if (!isIn601)
            {
                AddToRingGroup("601", extension.ToString(), rawExtensions601);
            }
             
            if (!isIn609)
            {
                AddToRingGroup("609", extension.ToString(), rawExtensions609);
            }
             
        }
         
        if (ringGroups == AsteriskRingGroups.None)
        {
            if (isIn601)
            {
                RemoveFromRingGroup("601", extension.ToString(), listExtension601, rawExtensions601);
            }
             
            if (isIn609)
            {
                RemoveFromRingGroup("609", extension.ToString(), listExtension609, rawExtensions609);
            }
             
        }
         
        if (ringGroups == AsteriskRingGroups.Backup)
        {
            if (isIn601)
            {
                RemoveFromRingGroup("601", extension.ToString(), listExtension601, rawExtensions601);
            }
             
            if (!isIn609)
            {
                AddToRingGroup("609", extension.ToString(), rawExtensions609);
            }
             
        }
         
        Signalods.setInvalid(InvalidType.PhoneAsteriskReload);
    }

    private static void addToRingGroup(String ringGroup, String extension, String rawExtensions) throws Exception {
        String newExtensions = rawExtensions + "-" + extension;
        String command = "UPDATE ringgroups SET grplist='" + POut.string(newExtensions) + "' " + "WHERE grpnum='" + ringGroup + "' " + "AND grplist = '" + POut.string(rawExtensions) + "'";
        //this ensures it hasn't changed since we checked it.  If it has, then this silently fails.
        //A transaction would be better, but no time.
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection(ipAddressAsterisk,"asterisk","opendental","secret",OpenDentBusiness.DatabaseType.MySql);
        dcon.nonQ(command);
    }

    private static void removeFromRingGroup(String ringGroup, String extension, List<String> listExtensions, String rawExtensions) throws Exception {
        String newExtensions = "";
        for (int i = 0;i < listExtensions.Count;i++)
        {
            if (StringSupport.equals(listExtensions[i], extension))
            {
                continue;
            }
             
            //skip this extension
            if (!StringSupport.equals(newExtensions, ""))
            {
                newExtensions = newExtensions + "-";
            }
             
            newExtensions = newExtensions + listExtensions[i];
        }
        String command = "UPDATE ringgroups SET grplist='" + POut.string(newExtensions) + "' " + "WHERE grpnum='" + ringGroup + "' " + "AND grplist = '" + POut.string(rawExtensions) + "'";
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection(ipAddressAsterisk,"asterisk","opendental","secret",OpenDentBusiness.DatabaseType.MySql);
        dcon.nonQ(command);
    }

    /**
    * For a given date, gets a list of dateTimes of missed calls.  Gets directly from the Asterisk database, hard-coded.
    */
    public static List<DateTime> getMissedCalls(DateTime date) throws Exception {
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection(ipAddressAsterisk,"asteriskcdrdb","opendental","secret",OpenDentBusiness.DatabaseType.MySql);
        String command = "SELECT calldate FROM cdr WHERE " + DbHelper.dateColumn("calldate") + " = " + POut.date(date) + " " + "AND (dcontext='ext-group' OR dcontext='ext-local') AND dst='vmu998'";
        List<DateTime> retVal = new List<DateTime>();
        DataTable table = dcon.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(PIn.DateT(table.Rows[i][0].ToString()));
        }
        return retVal;
    }

}


