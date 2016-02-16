//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:52 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;

/**
* Please ignore this class.  It's used only for testing.
*/
public class SchemaCrudProposedTest   
{
    /**
    * Example only
    */
    public static void addTableTempcore() throws Exception {
        String command = "";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "DROP TABLE IF EXISTS tempcore";
            Db.nonQ(command);
            command = "CREATE TABLE tempcore (\r\n" + 
            "\t\t\t\t\tTempCoreNum bigint NOT NULL auto_increment PRIMARY KEY,\r\n" + 
            "\t\t\t\t\tTimeOfDayTest time NOT NULL DEFAULT \'00:00:00\',\r\n" + 
            "\t\t\t\t\tTimeStampTest timestamp,\r\n" + 
            "\t\t\t\t\tDateTest date NOT NULL DEFAULT \'0001-01-01\',\r\n" + 
            "\t\t\t\t\tDateTimeTest datetime NOT NULL DEFAULT \'0001-01-01 00:00:00\',\r\n" + 
            "\t\t\t\t\tTimeSpanTest time NOT NULL DEFAULT \'00:00:00\',\r\n" + 
            "\t\t\t\t\tCurrencyTest double NOT NULL,\r\n" + 
            "\t\t\t\t\tBoolTest tinyint NOT NULL,\r\n" + 
            "\t\t\t\t\tTextSmallTest varchar(255) NOT NULL,\r\n" + 
            "\t\t\t\t\tTextMediumTest text NOT NULL,\r\n" + 
            "\t\t\t\t\tTextLargeTest text NOT NULL,\r\n" + 
            "\t\t\t\t\tVarCharTest varchar(255) NOT NULL\r\n" + 
            "\t\t\t\t\t) DEFAULT CHARSET=utf8";
            Db.nonQ(command);
        }
        else
        {
            //oracle
            command = "BEGIN EXECUTE IMMEDIATE 'DROP TABLE tempcore'; EXCEPTION WHEN OTHERS THEN NULL; END;";
            Db.nonQ(command);
            command = "CREATE TABLE tempcore (\r\n" + 
            "\t\t\t\t\tTempCoreNum number(20) NOT NULL,\r\n" + 
            "\t\t\t\t\tTimeOfDayTest date DEFAULT TO_DATE(\'0001-01-01\',\'YYYY-MM-DD\') NOT NULL,\r\n" + 
            "\t\t\t\t\tTimeStampTest timestamp,\r\n" + 
            "\t\t\t\t\tDateTest date DEFAULT TO_DATE(\'0001-01-01\',\'YYYY-MM-DD\') NOT NULL,\r\n" + 
            "\t\t\t\t\tDateTimeTest date DEFAULT TO_DATE(\'0001-01-01\',\'YYYY-MM-DD\') NOT NULL,\r\n" + 
            "\t\t\t\t\tTimeSpanTest varchar2(255),\r\n" + 
            "\t\t\t\t\tCurrencyTest number(38,8) NOT NULL,\r\n" + 
            "\t\t\t\t\tBoolTest number(3) NOT NULL,\r\n" + 
            "\t\t\t\t\tTextSmallTest varchar2(255),\r\n" + 
            "\t\t\t\t\tTextMediumTest clob,\r\n" + 
            "\t\t\t\t\tTextLargeTest clob,\r\n" + 
            "\t\t\t\t\tVarCharTest varchar2(255),\r\n" + 
            "\t\t\t\t\tCONSTRAINT TempCoreNum PRIMARY KEY (TempCoreNum)\r\n" + 
            "\t\t\t\t\t)";
            Db.nonQ(command);
        } 
    }

    /**
    * Example only
    */
    public static void addColumnEndClob() throws Exception {
        String command = "";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "ALTER TABLE tempcore ADD ColEndClob text NOT NULL";
            Db.nonQ(command);
        }
        else
        {
            //oracle
            command = "ALTER TABLE tempcore ADD ColEndClob clob";
            Db.nonQ(command);
        } 
    }

    /**
    * Example only
    */
    public static void addColumnEndInt() throws Exception {
        String command = "";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "ALTER TABLE tempcore ADD ColEndInt int NOT NULL";
            Db.nonQ(command);
        }
        else
        {
            //oracle
            command = "ALTER TABLE tempcore ADD ColEndInt number(11)";
            Db.nonQ(command);
            command = "UPDATE tempcore SET ColEndInt = 0 WHERE ColEndInt IS NULL";
            Db.nonQ(command);
            command = "ALTER TABLE tempcore MODIFY ColEndInt NOT NULL";
            Db.nonQ(command);
        } 
    }

    /**
    * Example only
    */
    public static void addColumnEndTimeStamp() throws Exception {
        String command = "";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "ALTER TABLE tempcore ADD ColEndTimeStamp timestamp";
            Db.nonQ(command);
            command = "UPDATE tempcore SET ColEndTimeStamp = NOW()";
            Db.nonQ(command);
        }
        else
        {
            //oracle
            command = "ALTER TABLE tempcore ADD ColEndTimeStamp timestamp";
            Db.nonQ(command);
            command = "UPDATE tempcore SET ColEndTimeStamp = SYSTIMESTAMP";
            Db.nonQ(command);
        } 
    }

    /**
    * Example only
    */
    public static void addIndex() throws Exception {
        String command = "";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "ALTER TABLE tempcore ADD INDEX(ColEndInt)";
            Db.nonQ(command);
        }
        else
        {
            //oracle
            command = "CREATE INDEX ColEndInt ON tempcore (ColEndInt)";
            Db.nonQ(command);
        } 
    }

    /**
    * Example only
    */
    public static void dropColumn() throws Exception {
        String command = "";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "ALTER TABLE tempcore DROP COLUMN TextLargeTest";
            Db.nonQ(command);
        }
        else
        {
            //oracle
            command = "ALTER TABLE tempcore DROP COLUMN TextLargeTest";
            Db.nonQ(command);
        } 
    }

}


//AddColumnAfter
//DropColumnTimeStamp
//DropIndex
//etc.