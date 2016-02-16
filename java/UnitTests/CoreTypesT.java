//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:38 PM
//

package UnitTests;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.OdDbType;
import OpenDentBusiness.OdSqlParameter;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import UnitTests.DatabaseTools;

public class CoreTypesT   
{
    /**
    * 
    */
    public static String createTempTable(boolean isOracle) throws Exception {
        String retVal = "";
        DatabaseTools.setDbConnection("unittest",isOracle);
        String command = new String();
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "DROP TABLE IF EXISTS tempcore";
            DataCore.nonQ(command);
            command = "CREATE TABLE tempcore (\r\n" + 
            "\t\t\t\t\tTempCoreNum bigint NOT NULL,\r\n" + 
            "\t\t\t\t\tTimeOfDayTest time NOT NULL DEFAULT \'00:00:00\',\r\n" + 
            "\t\t\t\t\tTimeStampTest timestamp,\r\n" + 
            "\t\t\t\t\tDateTest date NOT NULL DEFAULT \'0001-01-01\',\r\n" + 
            "\t\t\t\t\tDateTimeTest datetime NOT NULL DEFAULT \'0001-01-01 00:00:00\',\r\n" + 
            "\t\t\t\t\tTimeSpanTest time NOT NULL DEFAULT \'00:00:00\',\r\n" + 
            "\t\t\t\t\tCurrencyTest double NOT NULL,\r\n" + 
            "\t\t\t\t\tBoolTest tinyint NOT NULL,\r\n" + 
            "\t\t\t\t\tTextTinyTest varchar(255) NOT NULL, \r\n" + 
            "\t\t\t\t\tTextSmallTest text NOT NULL,\r\n" + 
            "\t\t\t\t\tTextMediumTest text NOT NULL,\r\n" + 
            "\t\t\t\t\tTextLargeTest mediumtext NOT NULL,\r\n" + 
            "\t\t\t\t\tVarCharTest varchar(255) NOT NULL\r\n" + 
            "\t\t\t\t\t) DEFAULT CHARSET=utf8";
            DataCore.nonQ(command);
        }
        else
        {
            //oracle
            command = "BEGIN EXECUTE IMMEDIATE 'DROP TABLE tempcore'; EXCEPTION WHEN OTHERS THEN NULL; END;";
            DataCore.nonQ(command);
            command = "CREATE TABLE tempcore (\r\n" + 
            "\t\t\t\t\tTempCoreNum number(20),\r\n" + 
            "\t\t\t\t\tTimeOfDayTest date DEFAULT TO_DATE(\'0001-01-01\',\'YYYY-MM-DD\'),\r\n" + 
            "\t\t\t\t\tTimeStampTest timestamp DEFAULT TO_DATE(\'0001-01-01\',\'YYYY-MM-DD\'),\r\n" + 
            "\t\t\t\t\tDateTest date DEFAULT TO_DATE(\'0001-01-01\',\'YYYY-MM-DD\'),\r\n" + 
            "\t\t\t\t\tDateTimeTest date DEFAULT TO_DATE(\'0001-01-01\',\'YYYY-MM-DD\'),\r\n" + 
            "\t\t\t\t\tTimeSpanTest varchar2(255),\r\n" + 
            "\t\t\t\t\tCurrencyTest number(38,8),\r\n" + 
            "\t\t\t\t\tBoolTest number(3),\r\n" + 
            "\t\t\t\t\tTextTinyTest varchar2(255),\r\n" + 
            "\t\t\t\t\tTextSmallTest varchar2(4000),\r\n" + 
            "\t\t\t\t\tTextMediumTest clob,\r\n" + 
            "\t\t\t\t\tTextLargeTest clob,\r\n" + 
            "\t\t\t\t\tVarCharTest varchar2(255)\r\n" + 
            "\t\t\t\t\t)";
            DataCore.nonQ(command);
        } 
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "DROP TABLE IF EXISTS tempgroupconcat";
            DataCore.nonQ(command);
            command = "CREATE TABLE tempgroupconcat (\r\n" + 
            "\t\t\t\t\tNames varchar(255)\r\n" + 
            "\t\t\t\t\t) DEFAULT CHARSET=utf8";
            DataCore.nonQ(command);
        }
        else
        {
            //oracle
            command = "BEGIN EXECUTE IMMEDIATE 'DROP TABLE tempgroupconcat'; EXCEPTION WHEN OTHERS THEN NULL; END;";
            DataCore.nonQ(command);
            command = "CREATE TABLE tempgroupconcat (\r\n" + 
            "\t\t\t\t\tNames varchar2(255)\r\n" + 
            "\t\t\t\t\t)";
            DataCore.nonQ(command);
        } 
        retVal += "Temp tables created.\r\n";
        return retVal;
    }

    //retVal+="Temp tables cannot yet be created.\r\n";
    /**
    * 
    */
    public static String runAll() throws Exception {
        String retVal = "";
        //Things that we might later add to this series of tests:
        //Special column types such as timestamp
        //Computer set to other region, affecting string parsing of types such dates and decimals
        //Test types without casting back and forth to strings.
        //Retrieval using a variety of techniques, such as getting a table, scalar, and reading a row.
        //Blobs
        String command = "";
        DataTable table = new DataTable();
        TimeSpan timespan = new TimeSpan();
        TimeSpan timespan2 = new TimeSpan();
        String varchar1 = new String();
        String varchar2 = new String();
        //timespan(timeOfDay)----------------------------------------------------------------------------------------------
        timespan = new TimeSpan(1, 2, 3);
        //1hr,2min,3sec
        command = "INSERT INTO tempcore (TimeOfDayTest) VALUES (" + POut.time(timespan) + ")";
        DataCore.nonQ(command);
        command = "SELECT TimeOfDayTest FROM tempcore";
        table = DataCore.getTable(command);
        timespan2 = PIn.Time(table.Rows[0]["TimeOfDayTest"].ToString());
        if (timespan != timespan2)
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "TimeSpan (time of day): Passed.\r\n";
        //timespan, negative------------------------------------------------------------------------------------
        timespan = new TimeSpan(0, -36, 0);
        //This particular timespan value was found to fail in mysql with the old connector.
        //Don't know what's so special about this one value.  There are probably other values failing as well, but it doesn't matter.
        //Oracle does not seem to like negative values.
        command = "INSERT INTO tempcore (TimeSpanTest) VALUES ('" + POut.tSpan(timespan) + "')";
        DataCore.nonQ(command);
        command = "SELECT TimeSpanTest FROM tempcore";
        table = DataCore.getTable(command);
        String tempVal = table.Rows[0]["TimeSpanTest"].ToString();
        timespan2 = PIn.tSpan(tempVal);
        if (timespan != timespan2)
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "TimeSpan, negative: Passed.\r\n";
        //timespan, over 24 hours-----------------------------------------------------------------------------
        timespan = new TimeSpan(432, 5, 17);
        command = "INSERT INTO tempcore (TimeSpanTest) VALUES ('" + POut.tSpan(timespan) + "')";
        DataCore.nonQ(command);
        command = "SELECT TimeSpanTest FROM tempcore";
        table = DataCore.getTable(command);
        timespan2 = PIn.TSpan(table.Rows[0]["TimeSpanTest"].ToString());
        if (timespan != timespan2)
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "TimeSpan, large: Passed.\r\n";
        //date----------------------------------------------------------------------------------------------
        DateTime date1 = new DateTime();
        DateTime date2 = new DateTime();
        date1 = new DateTime(2003, 5, 23);
        command = "INSERT INTO tempcore (DateTest) VALUES (" + POut.date(date1) + ")";
        DataCore.nonQ(command);
        command = "SELECT DateTest FROM tempcore";
        table = DataCore.getTable(command);
        date2 = PIn.Date(table.Rows[0]["DateTest"].ToString());
        if (date1 != date2)
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "Date: Passed.\r\n";
        //datetime------------------------------------------------------------------------------------------
        DateTime datet1 = new DateTime();
        DateTime datet2 = new DateTime();
        datet1 = new DateTime(2003, 5, 23, 10, 18, 0);
        command = "INSERT INTO tempcore (DateTimeTest) VALUES (" + POut.dateT(datet1) + ")";
        DataCore.nonQ(command);
        command = "SELECT DateTimeTest FROM tempcore";
        table = DataCore.getTable(command);
        datet2 = PIn.DateT(table.Rows[0]["DateTimeTest"].ToString());
        if (datet1 != datet2)
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "Date/Time: Passed.\r\n";
        //currency------------------------------------------------------------------------------------------
        double double1 = new double();
        double double2 = new double();
        double1 = 12.34d;
        command = "INSERT INTO tempcore (CurrencyTest) VALUES (" + POut.double(double1) + ")";
        DataCore.nonQ(command);
        command = "SELECT CurrencyTest FROM tempcore";
        table = DataCore.getTable(command);
        double2 = PIn.Double(table.Rows[0]["CurrencyTest"].ToString());
        if (double1 != double2)
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "Currency: Passed.\r\n";
        //group_concat------------------------------------------------------------------------------------
        command = "INSERT INTO tempgroupconcat VALUES ('name1')";
        DataCore.nonQ(command);
        command = "INSERT INTO tempgroupconcat VALUES ('name2')";
        DataCore.nonQ(command);
        command = "SELECT " + DbHelper.groupConcat("Names") + " allnames FROM tempgroupconcat";
        table = DataCore.getTable(command);
        String allnames = PIn.byteArray(table.Rows[0]["allnames"].ToString());
        //if(DataConnection.DBtype==DatabaseType.Oracle) {
        //	allnames=allnames.TrimEnd(',');//known issue.  Should already be fixed:
        //Use RTRIM(REPLACE(REPLACE(XMLAgg(XMLElement("x",column_name)),'<x>'),'</x>',','))
        //}
        if (!StringSupport.equals(allnames, "name1,name2"))
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempgroupconcat";
        DataCore.nonQ(command);
        retVal += "Group_concat: Passed.\r\n";
        //bool,pos------------------------------------------------------------------------------------
        boolean bool1 = new boolean();
        boolean bool2 = new boolean();
        bool1 = true;
        command = "INSERT INTO tempcore (BoolTest) VALUES (" + POut.bool(bool1) + ")";
        DataCore.nonQ(command);
        command = "SELECT BoolTest FROM tempcore";
        table = DataCore.getTable(command);
        bool2 = PIn.Bool(table.Rows[0]["BoolTest"].ToString());
        if (bool1 != bool2)
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "Bool, true: Passed.\r\n";
        //bool,neg------------------------------------------------------------------------------------
        bool1 = false;
        command = "INSERT INTO tempcore (BoolTest) VALUES (" + POut.bool(bool1) + ")";
        DataCore.nonQ(command);
        command = "SELECT BoolTest FROM tempcore";
        table = DataCore.getTable(command);
        bool2 = PIn.Bool(table.Rows[0]["BoolTest"].ToString());
        if (bool1 != bool2)
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "Bool, false: Passed.\r\n";
        //varchar255 Nonstandard Characters-----------------------------------------------------------
        varchar1 = "\'!@#$%^&*()-+[{]}\\`~,<.>/?\'\";:=_" + "\r\n\t";
        varchar2 = "";
        command = "INSERT INTO tempcore (TextTinyTest) VALUES ('" + POut.string(varchar1) + "')";
        DataCore.nonQ(command);
        command = "SELECT TextTinyTest FROM tempcore";
        table = DataCore.getTable(command);
        varchar2 = PIn.String(table.Rows[0]["TextTinyTest"].ToString());
        if (!StringSupport.equals(varchar1, varchar2))
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "VarChar(255): Passed.\r\n";
        //VARCHAR2(4000)------------------------------------------------------------------------------
        varchar1 = createRandomAlphaNumericString(4000);
        //Tested 4001 and it was too large as expected.
        command = "INSERT INTO tempcore (TextSmallTest) VALUES ('" + POut.string(varchar1) + "')";
        DataCore.nonQ(command);
        command = "SELECT TextSmallTest FROM tempcore";
        table = DataCore.getTable(command);
        varchar2 = PIn.String(table.Rows[0]["TextSmallTest"].ToString());
        if (!StringSupport.equals(varchar1, varchar2))
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "VarChar2(4000): Passed.\r\n";
        //clob:-----------------------------------------------------------------------------------------
        //tested up to 20MB in oracle.  (50MB however was failing: Chunk size error)
        //mysql mediumtext maxes out at about 16MB.
        String clobstring1 = createRandomAlphaNumericString(10485760);
        //10MB should be larger than anything we store.
        String clobstring2 = "";
        OdSqlParameter param = new OdSqlParameter("param1", OdDbType.Text, clobstring1);
        command = "INSERT INTO tempcore (TextLargeTest) VALUES (" + DbHelper.getParamChar() + "param1)";
        DataCore.nonQ(command,param);
        command = "SELECT TextLargeTest FROM tempcore";
        table = DataCore.getTable(command);
        clobstring2 = PIn.String(table.Rows[0]["TextLargeTest"].ToString());
        if (!StringSupport.equals(clobstring1, clobstring2))
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "Clob, Alpha-Numeric 10MB: Passed.\r\n";
        //clob:non-standard----------------------------------------------------------------------------------
        clobstring1 = createRandomNonStandardString(8000000);
        //8MB is the max because many chars takes 2 bytes, and mysql maxes out at 16MB
        clobstring2 = "";
        param = new OdSqlParameter("param1", OdDbType.Text, clobstring1);
        command = "INSERT INTO tempcore (TextLargeTest) VALUES (" + DbHelper.getParamChar() + "param1)";
        DataCore.nonQ(command,param);
        command = "SELECT TextLargeTest FROM tempcore";
        table = DataCore.getTable(command);
        clobstring2 = PIn.String(table.Rows[0]["TextLargeTest"].ToString());
        if (!StringSupport.equals(clobstring1, clobstring2))
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "Clob, Symbols and Chinese: Passed.\r\n";
        //clob:Rick Roller----------------------------------------------------------------------------------
        clobstring1 = rickRoller(10485760);
        //10MB should be larger than anything we store.
        clobstring2 = "";
        param = new OdSqlParameter("param1", OdDbType.Text, clobstring1);
        command = "INSERT INTO tempcore (TextLargeTest) VALUES (" + DbHelper.getParamChar() + "param1)";
        DataCore.nonQ(command,param);
        command = "SELECT TextLargeTest FROM tempcore";
        table = DataCore.getTable(command);
        clobstring2 = PIn.String(table.Rows[0]["TextLargeTest"].ToString());
        if (!StringSupport.equals(clobstring1, clobstring2))
        {
            throw new Exception();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "Clob, Rick Roller: Passed.\r\n";
        //SHOW CREATE TABLE -----------------------------------------------------------------------
        //This command is needed in order to perform a backup.
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "SHOW CREATE TABLE account";
            table = DataCore.getTable(command);
            String createResult = PIn.byteArray(table.Rows[0][1]);
            if (!createResult.StartsWith("CREATE TABLE"))
            {
                throw new Exception();
            }
             
            retVal += "SHOW CREATE TABLE: Passed.\r\n";
        }
        else
        {
            retVal += "SHOW CREATE TABLE: Not applicable to Oracle.\r\n";
        } 
        //Single Command Split-------------------------------------------------------------------------
        varchar1 = "';\"";
        varchar2 = ";'';;;;\"\"\"\"'asdfsadsdaf'";
        command = "INSERT INTO tempcore (TextTinyTest,TextSmallTest) VALUES ('" + POut.string(varchar1) + "','" + POut.string(varchar2) + "');";
        DataCore.nonQ(command);
        //Test the split function.
        command = "SELECT TextTinyTest,TextSmallTest FROM tempcore";
        table = DataCore.getTable(command);
        if (!StringSupport.equals(PIn.String(table.Rows[0]["TextTinyTest"].ToString()), varchar1) || !StringSupport.equals(PIn.String(table.Rows[0]["TextSmallTest"].ToString()), varchar2))
        {
            throw new ApplicationException();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "Single Command Split: Passed.";
        //Run multiple non-queries in one transaction--------------------------------------------------
        varchar1 = "A";
        varchar2 = "B";
        command = "INSERT INTO tempcore (TextTinyTest) VALUES ('" + POut.string(varchar1) + "'); DELETE FROM tempcore; INSERT INTO tempcore (TextTinyTest) VALUES ('" + POut.string(varchar2) + "')";
        DataCore.nonQ(command);
        command = "SELECT TextTinyTest FROM tempcore";
        table = DataCore.getTable(command);
        if (!StringSupport.equals(PIn.String(table.Rows[0][0].ToString()), varchar2))
        {
            throw new ApplicationException();
        }
         
        command = "DELETE FROM tempcore";
        DataCore.nonQ(command);
        retVal += "Multi-Non-Queries: Passed.\r\n";
        //Cleanup---------------------------------------------------------------------------------------
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "DROP TABLE IF EXISTS tempcore";
            DataCore.nonQ(command);
            command = "DROP TABLE IF EXISTS tempgroupconcat";
            DataCore.nonQ(command);
        }
        else
        {
            command = "BEGIN EXECUTE IMMEDIATE 'DROP TABLE tempcore'; EXCEPTION WHEN OTHERS THEN NULL; END;";
            DataCore.nonQ(command);
            command = "BEGIN EXECUTE IMMEDIATE 'DROP TABLE tempgroupconcat'; EXCEPTION WHEN OTHERS THEN NULL; END;";
            DataCore.nonQ(command);
        } 
        retVal += "CoreTypes test done.\r\n";
        return retVal;
    }

    public static String createRandomAlphaNumericString(int length) throws Exception {
        StringBuilder result = new StringBuilder(length);
        Random rand = new Random();
        String randChrs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0;i < length;i++)
        {
            result.Append(randChrs[rand.Next(0, randChrs.Length - 1)]);
        }
        return result.ToString();
    }

    public static String createRandomNonStandardString(int length) throws Exception {
        StringBuilder result = new StringBuilder(length);
        Random rand = new Random();
        String randChrs = "'!@#$%^&*()-+[{]}\\`~,<.>/?'\";:=_是像电子和质子这样的亚原子粒子之间的产生排斥力和吸引";
        for (int i = 0;i < length;i++)
        {
            result.Append(randChrs[rand.Next(0, randChrs.Length - 1)]);
        }
        return result.ToString();
    }

    public static String rickRoller(int length) throws Exception {
        StringBuilder result = new StringBuilder(length);
        Random rand = new Random();
        String randChrs = "I just couldn't take it anymore.  Kept getting the d--- song stuck in my head.";
        for (int i = 0;i < length;i++)
        {
            result.Append(randChrs[i % randChrs.Length]);
        }
        return result.ToString();
    }

}


