//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:42 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.EhrQuarterlyKey;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrQuarterlyKeys   
{
    /**
    * Pass in a guarantor of 0 when not using from OD tech station.
    */
    public static List<EhrQuarterlyKey> refresh(long guarantor) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrQuarterlyKey>>GetObject(MethodBase.GetCurrentMethod(), guarantor);
        }
         
        String command = new String();
        if (guarantor == 0)
        {
            //customer looking at their own quarterly keys
            command = "SELECT * FROM ehrquarterlykey WHERE PatNum=0";
        }
        else
        {
            //
            command = "SELECT ehrquarterlykey.* FROM ehrquarterlykey,patient " + "WHERE ehrquarterlykey.PatNum=patient.PatNum " + "AND patient.Guarantor=" + POut.long(guarantor) + " " + "GROUP BY ehrquarterlykey.EhrQuarterlyKeyNum " + "ORDER BY ehrquarterlykey.YearValue,ehrquarterlykey.QuarterValue";
        } 
        return Crud.EhrQuarterlyKeyCrud.SelectMany(command);
    }

    public static EhrQuarterlyKey getKeyThisQuarter() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<EhrQuarterlyKey>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = new String();
        int quarter = MonthToQuarter(DateTime.Today.Month);
        command = "SELECT * FROM ehrquarterlykey WHERE YearValue=" + (DateTime.Today.Year - 2000).ToString() + " " + "AND QuarterValue=" + quarter.ToString() + " " + "AND PatNum=0";
        return Crud.EhrQuarterlyKeyCrud.SelectOne(command);
    }

    //we don't care about practice title in the query
    /**
    * 
    */
    public static List<EhrQuarterlyKey> getAllKeys() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrQuarterlyKey>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = new String();
        command = "SELECT * FROM ehrquarterlykey WHERE PatNum=0 ORDER BY YearValue,QuarterValue";
        List<EhrQuarterlyKey> ehrKeys = Crud.EhrQuarterlyKeyCrud.SelectMany(command);
        return ehrKeys;
    }

    /**
    * Returns all keys in the given years.
    */
    public static List<EhrQuarterlyKey> getAllKeys(DateTime startDate, DateTime endDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrQuarterlyKey>>GetObject(MethodBase.GetCurrentMethod(), startDate, endDate);
        }
         
        int startYear = startDate.Year - 2000;
        int endYear = endDate.Year - 2000;
        String command = new String();
        command = "SELECT * FROM ehrquarterlykey WHERE (YearValue BETWEEN " + startYear + " " + "AND " + endYear + ") " + "AND PatNum=0 ORDER BY YearValue,QuarterValue";
        List<EhrQuarterlyKey> ehrKeys = Crud.EhrQuarterlyKeyCrud.SelectMany(command);
        return ehrKeys;
    }

    /**
    * We want to find the first day of the oldest quarter less than or equal to one year old from the latest entered valid key.  validKeys must be sorted ascending.
    */
    public static DateTime findStartDate(List<EhrQuarterlyKey> validKeys) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (validKeys.Count < 1)
        {
            return new DateTime(DateTime.Today.Year, 1, 1);
        }
         
        EhrQuarterlyKey ehrKey = validKeys[validKeys.Count - 1];
        DateTime latestReportDate = getLastDayOfQuarter(ehrKey);
        DateTime earliestStartDate = latestReportDate.AddYears(-1).AddDays(1);
        for (int i = validKeys.Count - 1;i > -1;i--)
        {
            ehrKey = validKeys[i];
            if (i == 0)
            {
                break;
            }
             
            int expectedPrevQuarter = validKeys[i].QuarterValue - 1;
            if (validKeys[i].QuarterValue == 1)
            {
                expectedPrevQuarter = 4;
            }
             
            int prevQuarter = validKeys[i - 1].QuarterValue;
            int expectedYear = validKeys[i].YearValue;
            if (validKeys[i].QuarterValue == 1)
            {
                expectedYear = validKeys[i].YearValue - 1;
            }
             
            int prevQuarter_Year = validKeys[i - 1].YearValue;
            if (expectedPrevQuarter != prevQuarter || expectedYear != prevQuarter_Year)
            {
                break;
            }
             
            //There is an quarterly key gap, so we ignore any older quarterly keys.
            DateTime prevQuarterFirstDay = GetFirstDayOfQuarter(validKeys[i - 1]);
            if (prevQuarterFirstDay < earliestStartDate)
            {
                break;
            }
             
        }
        return getFirstDayOfQuarter(ehrKey);
    }

    public static DateTime getFirstDayOfQuarter(DateTime date) throws Exception {
        return new DateTime(date.Year, 3 * (MonthToQuarter(date.Month) - 1) + 1, 1);
    }

    //No need to check RemotingRole; no call to db.
    public static DateTime getFirstDayOfQuarter(EhrQuarterlyKey ehrKey) throws Exception {
        return getFirstDayOfQuarter(new DateTime(ehrKey.YearValue + 2000, ehrKey.QuarterValue * 3, 1));
    }

    //No need to check RemotingRole; no call to db.
    public static DateTime getLastDayOfQuarter(DateTime date) throws Exception {
        return (new DateTime(date.Year, 1, 1)).AddMonths(3 * MonthToQuarter(date.Month)).AddDays(-1);
    }

    //No need to check RemotingRole; no call to db.
    public static DateTime getLastDayOfQuarter(EhrQuarterlyKey ehrKey) throws Exception {
        return getLastDayOfQuarter(new DateTime(ehrKey.YearValue + 2000, ehrKey.QuarterValue * 3, 1));
    }

    //No need to check RemotingRole; no call to db.
    public static String validateDateRange(List<EhrQuarterlyKey> validKeys, DateTime startDate, DateTime endDate) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (validKeys.Count == 0)
        {
            return "No Valid Quarterly Keys";
        }
         
        DateTime startOfFirstQuarter = getFirstDayOfQuarter(startDate);
        DateTime endOfLastQuarter = getLastDayOfQuarter(endDate);
        String explanation = "";
        int msgCount = 0;
        int numberOfQuarters = calculateQuarters(startDate,endDate);
        for (int j = 0;j < numberOfQuarters;j++)
        {
            boolean isValid = false;
            if (startOfFirstQuarter > endOfLastQuarter)
            {
                break;
            }
             
            for (int i = 0;i < validKeys.Count;i++)
            {
                if (MonthToQuarter(startOfFirstQuarter.Month) == validKeys[i].QuarterValue && startOfFirstQuarter.Year - 2000 == validKeys[i].YearValue)
                {
                    isValid = true;
                    break;
                }
                 
            }
            if (!isValid)
            {
                if (StringSupport.equals(explanation, ""))
                {
                    explanation = "Selected date range is invalid. You are missing these quarterly keys: \r\n";
                }
                 
                explanation += startOfFirstQuarter.Year + "-Q" + MonthToQuarter(startOfFirstQuarter.Month) + "\r\n";
                msgCount++;
                if (msgCount > 8)
                {
                    return explanation;
                }
                 
            }
             
            startOfFirstQuarter = startOfFirstQuarter.AddMonths(3);
        }
        return explanation;
    }

    /**
    * Gets the count of quarters between the dates inclusive.
    */
    private static int calculateQuarters(DateTime startDate, DateTime endDate) throws Exception {
        //No need to check RemotingRole; no call to db.
        int startQuarter = MonthToQuarter(startDate.Month);
        int endQuarter = MonthToQuarter(endDate.Month);
        return 4 * (endDate.Year - startDate.Year) + (endQuarter - startQuarter + 1);
    }

    public static int monthToQuarter(int month) throws Exception {
        //No need to check RemotingRole; no call to db.
        int quarter = 1;
        if (month >= 4 && month <= 6)
        {
            quarter = 2;
        }
         
        if (month >= 7 && month <= 9)
        {
            quarter = 3;
        }
         
        if (month >= 10)
        {
            quarter = 4;
        }
         
        return quarter;
    }

    /**
    * 
    */
    public static long insert(EhrQuarterlyKey ehrQuarterlyKey) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrQuarterlyKey.EhrQuarterlyKeyNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrQuarterlyKey);
            return ehrQuarterlyKey.EhrQuarterlyKeyNum;
        }
         
        return Crud.EhrQuarterlyKeyCrud.Insert(ehrQuarterlyKey);
    }

    /**
    * 
    */
    public static void update(EhrQuarterlyKey ehrQuarterlyKey) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrQuarterlyKey);
            return ;
        }
         
        Crud.EhrQuarterlyKeyCrud.Update(ehrQuarterlyKey);
    }

    /**
    * 
    */
    public static void delete(long ehrQuarterlyKeyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrQuarterlyKeyNum);
            return ;
        }
         
        String command = "DELETE FROM ehrquarterlykey WHERE EhrQuarterlyKeyNum = " + POut.long(ehrQuarterlyKeyNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		
		///<summary>Gets one EhrQuarterlyKey from the db.</summary>
		public static EhrQuarterlyKey GetOne(long ehrQuarterlyKeyNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrQuarterlyKey>(MethodBase.GetCurrentMethod(),ehrQuarterlyKeyNum);
			}
			return Crud.EhrQuarterlyKeyCrud.SelectOne(ehrQuarterlyKeyNum);
		}
		
		*/