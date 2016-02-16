//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.PIn;
import OpenDentBusiness.WikiListHeaderWidths;

/**
* This class contains methods used to generate database independent SQL.
*/
public class DbHelper   
{
    /**
    * Use when you already have a WHERE clause in the query. Uses AND RowNum... for Oracle.
    */
    public static String limitAnd(int n) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "AND RowNum <= " + n;
        }
        else
        {
            return "LIMIT " + n;
        } 
    }

    /**
    * Use when you do not otherwise have a WHERE clause in the query. Uses WHERE RowNum... for Oracle.
    */
    public static String limitWhere(int n) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "WHERE RowNum <= " + n;
        }
        else
        {
            return "LIMIT " + n;
        } 
    }

    /**
    * Use when there is an ORDER BY clause in the query. Uses RowNum... for Oracle.
    */
    public static String limitOrderBy(String query, int n) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "SELECT * FROM (" + query + ") WHERE RowNum <= " + n;
        }
        else
        {
            return query + " LIMIT " + n;
        } 
    }

    /**
    * Concatenates the fields and/or literals passed as params for Oracle or MySQL. If passing in a literal, surround with single quotes.
    */
    public static String concat(String... values) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            String result = "(";
            for (int i = 0;i < values.Length;i++)
            {
                if (i != 0)
                {
                    result += " || ";
                }
                 
                result += values[i];
            }
            result += ")";
            return result;
        }
        else
        {
            String result = "CONCAT(";
            for (int i = 0;i < values.Length;i++)
            {
                if (i != 0)
                {
                    result += ",";
                }
                 
                result += values[i];
            }
            result += ")";
            return result;
        } 
    }

    /**
    * Specify column for equivalent of "GROUP_CONCAT(column)" in MySQL.
    */
    public static String groupConcat(String column) throws Exception {
        return groupConcat(column,false);
    }

    /**
    * Specify column for equivalent of "GROUP_CONCAT(column)" in MySQL. Adds DISTINCT in MySQL if specified.
    */
    public static String groupConcat(String column, boolean distinct) throws Exception {
        return groupConcat(column,distinct,false);
    }

    /**
    * Specify column for equivalent of "GROUP_CONCAT(column)" in MySQL. Adds DISTINCT (MySQL only) and ORDERBY as specified.
    */
    public static String groupConcat(String column, boolean distinct, boolean orderby) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            if (orderby)
            {
                return "RTRIM(REPLACE(REPLACE(XMLAgg(XMLElement(\"x\"," + column + ") ORDER BY " + column + "),'<x>'),'</x>',','),',')";
            }
            else
            {
                return "RTRIM(REPLACE(REPLACE(XMLAgg(XMLElement(\"x\"," + column + ")),'<x>'),'</x>',','),',')";
            } 
        }
        else
        {
            //Distinct ignored for Oracle case.
            if (distinct && orderby)
            {
                return "GROUP_CONCAT(DISTINCT " + column + " ORDER BY " + column + ")";
            }
             
            if (distinct && !orderby)
            {
                return "GROUP_CONCAT(DISTINCT " + column + ")";
            }
             
            if (!distinct && orderby)
            {
                return "GROUP_CONCAT(" + column + " ORDER BY " + column + ")";
            }
            else
            {
                return "GROUP_CONCAT(" + column + ")";
            } 
        } 
    }

    /**
    * In Oracle, union order by combos can only use ordinals and not column names. Values for ordinal start at 1.
    */
    public static String unionOrderBy(String colName, int ordinal) throws Exception {
        //Using POut doesn't name sense for column names or ordinal numbers because they are not values they are part of the query.
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return ordinal.ToString();
        }
         
        return colName;
    }

    public static String dateAddDay(String date, String days) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return date + " +" + days;
        }
         
        return "ADDDATE(" + date + "," + days + ")";
    }

    //Can handle negatives even with '+' hardcoded.
    public static String dateAddMonth(String date, String months) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "ADD_MONTHS(" + date + "," + months + ")";
        }
         
        return "ADDDATE(" + date + ",INTERVAL " + months + " MONTH)";
    }

    public static String dateAddYear(String date, String years) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "ADD_MONTHS(" + date + "," + years + "*12)";
        }
         
        return "ADDDATE(" + date + ",INTERVAL " + years + " YEAR)";
    }

    /**
    * TO_DATE() for datetime columns where we only want the date.
    */
    public static String dateColumn(String colName) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "TO_DATE(" + colName + ")";
        }
         
        return "DATE(" + colName + ")";
    }

    /**
    * The format must be the MySQL format. The following formats are currently acceptable as input: %c/%d/%Y , %m/%d/%Y
    */
    public static String dateFormatColumn(String colName, String format) throws Exception {
        //MySQL DATE_FORMAT() reference: http://dev.mysql.com/doc/refman/5.0/en/date-and-time-functions.html#function_date-format
        //Oracle TO_CHAR() reference: http://download.oracle.com/docs/cd/B19306_01/server.102/b14200/sql_elements004.htm#i34510
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            if (StringSupport.equals(format, "%c/%d/%Y"))
            {
                return "TO_CHAR(" + colName + ",'MM/DD/YYYY')";
            }
            else //Sadly, not exactly the same but closest option.
            if (StringSupport.equals(format, "%m/%d/%Y"))
            {
                return "TO_CHAR(" + colName + ",'MM/DD/YYYY')";
            }
              
            throw new Exception("Unrecognized date format string.");
        }
         
        //Sadly, not exactly the same but closest option.
        //MySQL-----------------------------------------------------------------------------
        if (System.Globalization.CultureInfo.CurrentCulture.Name.EndsWith("US"))
        {
            return "DATE_FORMAT(" + colName + ",'" + format + "')";
        }
         
        //foreign, assume d/m/y
        if (StringSupport.equals(format, "%c/%d/%Y"))
        {
            return "DATE_FORMAT(" + colName + ",'%d/%c/%Y')";
        }
        else if (StringSupport.equals(format, "%m/%d/%Y"))
        {
            return "DATE_FORMAT(" + colName + ",'%d/%m/%Y')";
        }
          
        throw new Exception("Unrecognized date format string.");
    }

    /**
    * The format must be the MySQL format.  The following formats are currently acceptable as input: %c/%d/%Y %H:%i:%s and %m/%d/%Y %H:%i:%s.
    */
    public static String dateTFormatColumn(String colName, String format) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            if (StringSupport.equals(format, "%c/%d/%Y %H:%i:%s"))
            {
                return "TO_CHAR(" + colName + ",'MM/DD/YYYY %HH24:%MI:%SS')";
            }
            else //Sadly, not exactly the same but closest option.
            if (StringSupport.equals(format, "%m/%d/%Y %H:%i:%s"))
            {
                return "TO_CHAR(" + colName + ",'MM/DD/YYYY %HH24:%MI:%SS')";
            }
              
            throw new Exception("Unrecognized datetime format string.");
        }
         
        //Sadly, not exactly the same but closest option.
        //MySQL-----------------------------------------------------------------------------
        if (System.Globalization.CultureInfo.CurrentCulture.Name.EndsWith("US"))
        {
            return "DATE_FORMAT(" + colName + ",'" + format + "')";
        }
         
        //foreign, assume d/m/y
        if (StringSupport.equals(format, "%c/%d/%Y %H:%i:%s"))
        {
            return "DATE_FORMAT(" + colName + ",'%d/%c/%Y %H:%i:%s')";
        }
        else if (StringSupport.equals(format, "%m/%d/%Y %H:%i:%s"))
        {
            return "DATE_FORMAT(" + colName + ",'%d/%m/%Y %H:%i:%s')";
        }
          
        throw new Exception("Unrecognized datetime format string.");
    }

    /* Not used
    		///<summary>Helper for Oracle that will return equivalent of MySql CURTIME().</summary>
    		public static string Curtime() {
    			if(DataConnection.DBtype==DatabaseType.Oracle) {
    				return "SYSDATE";
    			}
    			return "CURTIME()";
    		}*/
    /**
    * Helper for Oracle that will return equivalent of MySql CURDATE()
    */
    public static String curdate() throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "SYSDATE";
        }
         
        return "CURDATE()";
    }

    //return "(SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD') FROM DUAL)";
    /**
    * Helper for Oracle that will return equivalent of MySql NOW()
    */
    public static String now() throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "SYSDATE";
        }
         
        return "NOW()";
    }

    //return "(SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') FROM DUAL)";
    /**
    * Helper for Oracle that will return equivalent of MySql YEAR()
    */
    public static String year(String date) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "CAST(TO_CHAR(" + date + ",'YYYY') AS NUMBER)";
        }
         
        return "YEAR(" + date + ")";
    }

    /**
    * Helper for Oracle that will return equivalent of MySql "input REGEXP 'pattern'". Also changes pattern:[0-9] to [:digit:] for Oracle.
    */
    public static String regexp(String input, String pattern) throws Exception {
        return regexp(input,pattern,true);
    }

    /**
    * Helper for Oracle that will return equivalent of MySql "input REGEXP 'pattern'". Also changes pattern:[0-9] to [:digit:] for Oracle. Takes matches param for "does [not] match this regexp."
    */
    public static String regexp(String input, String pattern, boolean matches) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            pattern.Replace("[0-9]", "[[:digit:]]");
            return (matches ? "" : "NOT ") + "REGEXP_LIKE(" + input + ",'" + pattern + "')";
        }
         
        return input + (matches ? "" : " NOT") + " REGEXP '" + pattern + "'";
    }

    /**
    * Gets the database specific character used for parameters.  For example, : or @.
    */
    public static String getParamChar() throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return ":";
        }
         
        return "@";
    }

    /**
    * Gets the maximum value for the specified field within the specified table. This key will always be the MAX(field)+1 and will usually be the correct key to use for new inserts, but not always.
    */
    public static long getNextOracleKey(String tablename, String field) throws Exception {
        //When inserting a new record with the key value returned by this function, these are some possible errors that can occur.
        //The actual error text starts after the ... on each line. Note especially the duplicate key exception, as this exception
        //must be considered by the insertion algorithm:
        //DUPLICATE PRIMARY KEY....ORA-00001: unique constraint (DEV77.PRIMARY_87) violated
        //MISSING WHOLE TABLE......ORA-00942: table or view does not exist
        //MISSING TABLE COLUMN.....ORA-00904: "ITEMORDER": invalid identifier
        //MISSING OPENING PAREND...ORA-00926: missing VALUES keyword
        //CONNECTION LOST..........ORA-03113: end-of-file on communication channel
        String command = "SELECT MAX(" + field + ")+1 FROM " + tablename;
        long retval = PIn.long(Db.getCount(command));
        if (retval == 0)
        {
            return 1;
        }
         
        return retval;
    }

    //Happens when the table has no records
    public static String castToChar(String expr) throws Exception {
        String result = "CAST(";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            result += expr + " AS VARCHAR2(4000))";
        }
        else
        {
            result += expr + " AS CHAR(4000))";
        } 
        return result;
    }

    /**
    * Returns true if the input string is a reserved word in MySQL 5.5.31.
    */
    public static boolean isMySQLReservedWord(String input) throws Exception {
        boolean retval = new boolean();
        //using a switch statement makes this method run in constant time (faster).
        System.String.APPLY __dummyScrutVar0 = input.ToUpper();
        //New as of mysql 5.5
        if (__dummyScrutVar0.equals("ACCESSIBLE") || __dummyScrutVar0.equals("ADD") || __dummyScrutVar0.equals("ALL") || __dummyScrutVar0.equals("ALTER") || __dummyScrutVar0.equals("ANALYZE") || __dummyScrutVar0.equals("AND") || __dummyScrutVar0.equals("AS") || __dummyScrutVar0.equals("ASC") || __dummyScrutVar0.equals("ASENSITIVE") || __dummyScrutVar0.equals("BEFORE") || __dummyScrutVar0.equals("BETWEEN") || __dummyScrutVar0.equals("BIGINT") || __dummyScrutVar0.equals("BINARY") || __dummyScrutVar0.equals("BLOB") || __dummyScrutVar0.equals("BOTH") || __dummyScrutVar0.equals("BY") || __dummyScrutVar0.equals("CALL") || __dummyScrutVar0.equals("CASCADE") || __dummyScrutVar0.equals("CHANGE") || __dummyScrutVar0.equals("CHAR") || __dummyScrutVar0.equals("CHARACTER") || __dummyScrutVar0.equals("CHECK") || __dummyScrutVar0.equals("COLLATE") || __dummyScrutVar0.equals("COLUMN") || __dummyScrutVar0.equals("CONDITION") || __dummyScrutVar0.equals("CONSTRAINT") || __dummyScrutVar0.equals("CONTINUE") || __dummyScrutVar0.equals("CONVERT") || __dummyScrutVar0.equals("CREATE") || __dummyScrutVar0.equals("CROSS") || __dummyScrutVar0.equals("CURRENT_DATE") || __dummyScrutVar0.equals("CURRENT_TIME") || __dummyScrutVar0.equals("CURRENT_TIMESTAMP") || __dummyScrutVar0.equals("CURRENT_USER") || __dummyScrutVar0.equals("CURSOR") || __dummyScrutVar0.equals("DATABASE") || __dummyScrutVar0.equals("DATABASES") || __dummyScrutVar0.equals("DAY_HOUR") || __dummyScrutVar0.equals("DAY_MICROSECOND") || __dummyScrutVar0.equals("DAY_MINUTE") || __dummyScrutVar0.equals("DAY_SECOND") || __dummyScrutVar0.equals("DEC") || __dummyScrutVar0.equals("DECIMAL") || __dummyScrutVar0.equals("DECLARE") || __dummyScrutVar0.equals("DEFAULT") || __dummyScrutVar0.equals("DELAYED") || __dummyScrutVar0.equals("DELETE") || __dummyScrutVar0.equals("DESC") || __dummyScrutVar0.equals("DESCRIBE") || __dummyScrutVar0.equals("DETERMINISTIC") || __dummyScrutVar0.equals("DISTINCT") || __dummyScrutVar0.equals("DISTINCTROW") || __dummyScrutVar0.equals("DIV") || __dummyScrutVar0.equals("DOUBLE") || __dummyScrutVar0.equals("DROP") || __dummyScrutVar0.equals("DUAL") || __dummyScrutVar0.equals("EACH") || __dummyScrutVar0.equals("ELSE") || __dummyScrutVar0.equals("ELSEIF") || __dummyScrutVar0.equals("ENCLOSED") || __dummyScrutVar0.equals("ESCAPED") || __dummyScrutVar0.equals("EXISTS") || __dummyScrutVar0.equals("EXIT") || __dummyScrutVar0.equals("EXPLAIN") || __dummyScrutVar0.equals("FALSE") || __dummyScrutVar0.equals("FETCH") || __dummyScrutVar0.equals("FLOAT") || __dummyScrutVar0.equals("FLOAT4") || __dummyScrutVar0.equals("FLOAT8") || __dummyScrutVar0.equals("FOR") || __dummyScrutVar0.equals("FORCE") || __dummyScrutVar0.equals("FOREIGN") || __dummyScrutVar0.equals("FROM") || __dummyScrutVar0.equals("FULLTEXT") || __dummyScrutVar0.equals("GRANT") || __dummyScrutVar0.equals("GROUP") || __dummyScrutVar0.equals("HAVING") || __dummyScrutVar0.equals("HIGH_PRIORITY") || __dummyScrutVar0.equals("HOUR_MICROSECOND") || __dummyScrutVar0.equals("HOUR_MINUTE") || __dummyScrutVar0.equals("HOUR_SECOND") || __dummyScrutVar0.equals("IF") || __dummyScrutVar0.equals("IGNORE") || __dummyScrutVar0.equals("IN") || __dummyScrutVar0.equals("INDEX") || __dummyScrutVar0.equals("INFILE") || __dummyScrutVar0.equals("INNER") || __dummyScrutVar0.equals("INOUT") || __dummyScrutVar0.equals("INSENSITIVE") || __dummyScrutVar0.equals("INSERT") || __dummyScrutVar0.equals("INT") || __dummyScrutVar0.equals("INT1") || __dummyScrutVar0.equals("INT2") || __dummyScrutVar0.equals("INT3") || __dummyScrutVar0.equals("INT4") || __dummyScrutVar0.equals("INT8") || __dummyScrutVar0.equals("INTEGER") || __dummyScrutVar0.equals("INTERVAL") || __dummyScrutVar0.equals("INTO") || __dummyScrutVar0.equals("IS") || __dummyScrutVar0.equals("ITERATE") || __dummyScrutVar0.equals("JOIN") || __dummyScrutVar0.equals("KEY") || __dummyScrutVar0.equals("KEYS") || __dummyScrutVar0.equals("KILL") || __dummyScrutVar0.equals("LEADING") || __dummyScrutVar0.equals("LEAVE") || __dummyScrutVar0.equals("LEFT") || __dummyScrutVar0.equals("LIKE") || __dummyScrutVar0.equals("LIMIT") || __dummyScrutVar0.equals("LINEAR") || __dummyScrutVar0.equals("LINES") || __dummyScrutVar0.equals("LOAD") || __dummyScrutVar0.equals("LOCALTIME") || __dummyScrutVar0.equals("LOCALTIMESTAMP") || __dummyScrutVar0.equals("LOCK") || __dummyScrutVar0.equals("LONG") || __dummyScrutVar0.equals("LONGBLOB") || __dummyScrutVar0.equals("LONGTEXT") || __dummyScrutVar0.equals("LOOP") || __dummyScrutVar0.equals("LOW_PRIORITY") || __dummyScrutVar0.equals("MASTER_SSL_VERIFY_SERVER_CERT") || __dummyScrutVar0.equals("MATCH") || __dummyScrutVar0.equals("MAXVALUE") || __dummyScrutVar0.equals("MEDIUMBLOB") || __dummyScrutVar0.equals("MEDIUMINT") || __dummyScrutVar0.equals("MEDIUMTEXT") || __dummyScrutVar0.equals("MIDDLEINT") || __dummyScrutVar0.equals("MINUTE_MICROSECOND") || __dummyScrutVar0.equals("MINUTE_SECOND") || __dummyScrutVar0.equals("MOD") || __dummyScrutVar0.equals("MODIFIES") || __dummyScrutVar0.equals("NATURAL") || __dummyScrutVar0.equals("NOT") || __dummyScrutVar0.equals("NO_WRITE_TO_BINLOG") || __dummyScrutVar0.equals("NULL") || __dummyScrutVar0.equals("NUMERIC") || __dummyScrutVar0.equals("ON") || __dummyScrutVar0.equals("OPTIMIZE") || __dummyScrutVar0.equals("OPTION") || __dummyScrutVar0.equals("OPTIONALLY") || __dummyScrutVar0.equals("OR") || __dummyScrutVar0.equals("ORDER") || __dummyScrutVar0.equals("OUT") || __dummyScrutVar0.equals("OUTER") || __dummyScrutVar0.equals("OUTFILE") || __dummyScrutVar0.equals("PRECISION") || __dummyScrutVar0.equals("PRIMARY") || __dummyScrutVar0.equals("PROCEDURE") || __dummyScrutVar0.equals("PURGE") || __dummyScrutVar0.equals("RANGE") || __dummyScrutVar0.equals("READ") || __dummyScrutVar0.equals("READS") || __dummyScrutVar0.equals("READ_WRITE") || __dummyScrutVar0.equals("REAL") || __dummyScrutVar0.equals("REFERENCES") || __dummyScrutVar0.equals("REGEXP") || __dummyScrutVar0.equals("RELEASE") || __dummyScrutVar0.equals("RENAME") || __dummyScrutVar0.equals("REPEAT") || __dummyScrutVar0.equals("REPLACE") || __dummyScrutVar0.equals("REQUIRE") || __dummyScrutVar0.equals("RESIGNAL") || __dummyScrutVar0.equals("RESTRICT") || __dummyScrutVar0.equals("RETURN") || __dummyScrutVar0.equals("REVOKE") || __dummyScrutVar0.equals("RIGHT") || __dummyScrutVar0.equals("RLIKE") || __dummyScrutVar0.equals("SCHEMA") || __dummyScrutVar0.equals("SCHEMAS") || __dummyScrutVar0.equals("SECOND_MICROSECOND") || __dummyScrutVar0.equals("SELECT") || __dummyScrutVar0.equals("SENSITIVE") || __dummyScrutVar0.equals("SEPARATOR") || __dummyScrutVar0.equals("SET") || __dummyScrutVar0.equals("SHOW") || __dummyScrutVar0.equals("SIGNAL") || __dummyScrutVar0.equals("SMALLINT") || __dummyScrutVar0.equals("SPATIAL") || __dummyScrutVar0.equals("SPECIFIC") || __dummyScrutVar0.equals("SQL") || __dummyScrutVar0.equals("SQLEXCEPTION") || __dummyScrutVar0.equals("SQLSTATE") || __dummyScrutVar0.equals("SQLWARNING") || __dummyScrutVar0.equals("SQL_BIG_RESULT") || __dummyScrutVar0.equals("SQL_CALC_FOUND_ROWS") || __dummyScrutVar0.equals("SQL_SMALL_RESULT") || __dummyScrutVar0.equals("SSL") || __dummyScrutVar0.equals("STARTING") || __dummyScrutVar0.equals("STRAIGHT_JOIN") || __dummyScrutVar0.equals("TABLE") || __dummyScrutVar0.equals("TERMINATED") || __dummyScrutVar0.equals("THEN") || __dummyScrutVar0.equals("TINYBLOB") || __dummyScrutVar0.equals("TINYINT") || __dummyScrutVar0.equals("TINYTEXT") || __dummyScrutVar0.equals("TO") || __dummyScrutVar0.equals("TRAILING") || __dummyScrutVar0.equals("TRIGGER") || __dummyScrutVar0.equals("TRUE") || __dummyScrutVar0.equals("UNDO") || __dummyScrutVar0.equals("UNION") || __dummyScrutVar0.equals("UNIQUE") || __dummyScrutVar0.equals("UNLOCK") || __dummyScrutVar0.equals("UNSIGNED") || __dummyScrutVar0.equals("UPDATE") || __dummyScrutVar0.equals("USAGE") || __dummyScrutVar0.equals("USE") || __dummyScrutVar0.equals("USING") || __dummyScrutVar0.equals("UTC_DATE") || __dummyScrutVar0.equals("UTC_TIME") || __dummyScrutVar0.equals("UTC_TIMESTAMP") || __dummyScrutVar0.equals("VALUES") || __dummyScrutVar0.equals("VARBINARY") || __dummyScrutVar0.equals("VARCHAR") || __dummyScrutVar0.equals("VARCHARACTER") || __dummyScrutVar0.equals("VARYING") || __dummyScrutVar0.equals("WHEN") || __dummyScrutVar0.equals("WHERE") || __dummyScrutVar0.equals("WHILE") || __dummyScrutVar0.equals("WITH") || __dummyScrutVar0.equals("WRITE") || __dummyScrutVar0.equals("XOR") || __dummyScrutVar0.equals("YEAR_MONTH") || __dummyScrutVar0.equals("ZEROFILL") || __dummyScrutVar0.equals("GENERAL") || __dummyScrutVar0.equals("IGNORE_SERVER_IDS") || __dummyScrutVar0.equals("MASTER_HEARTBEAT_PERIOD") || __dummyScrutVar0.equals("SLOW"))
        {
            retval = true;
        }
        else
        {
            retval = false;
        } 
        if (Regex.IsMatch(input, WikiListHeaderWidths.dummyColName))
        {
            retval = true;
        }
         
        return retval;
    }

    public static String ifNull(String expr, String valWhenNull) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            return "CASE WHEN (" + expr + ") IS NULL THEN " + expr + " ELSE '" + valWhenNull + "' END";
        }
         
        return "IFNULL(" + expr + ",'" + valWhenNull + "')";
    }

}


