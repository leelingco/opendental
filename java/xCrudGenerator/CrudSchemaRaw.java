//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package xCrudGenerator;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DbSchemaCol;
import OpenDentBusiness.OdDbType;
import OpenDentBusiness.TextSizeMySqlOracle;

/**
* This is the class that actually generates snippets of raw schema code.
*/
public class CrudSchemaRaw   
{
    private static final String rn = "\r\n";
    private static final String t1 = "\t";
    private static final String t2 = "\t\t";
    private static final String t3 = "\t\t\t";
    private static final String t4 = "\t\t\t\t";
    private static final String t5 = "\t\t\t\t\t";
    private static String tb = "";
    /**
    * Generates C# code to add a table.
    */
    public static String addTable(String tableName, List<DbSchemaCol> cols, int tabInset, boolean isMobile) throws Exception {
        StringBuilder strb = new StringBuilder();
        List<DbSchemaCol> indexes = new List<DbSchemaCol>();
        tb = "";
        for (int i = 0;i < tabInset;i++)
        {
            //must reset tabs each time method is called
            //defines the base tabs to be added to all lines
            tb += "\t";
        }
        strb.Append(tb + "if(DataConnection.DBtype==DatabaseType.MySql) {");
        strb.Append(rn + tb + t1 + "command=\"DROP TABLE IF EXISTS " + tableName + "\";");
        strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        strb.Append(rn + tb + t1 + "command=@\"CREATE TABLE " + tableName + " (");
        for (int i = 0;i < cols.Count;i++)
        {
            strb.Append(rn + tb + t2 + cols[i].ColumnName + " " + GetMySqlType(cols[i]));
            if (!StringSupport.equals(GetMySqlType(cols[i]), "timestamp"))
            {
                strb.Append(" NOT NULL");
            }
             
            if (!StringSupport.equals(GetMySqlBlankData(cols[i]), "\"\"") && !StringSupport.equals(GetMySqlBlankData(cols[i]), "0") && !StringSupport.equals(GetMySqlType(cols[i]), "timestamp"))
            {
                strb.Append(" DEFAULT " + GetMySqlBlankData(cols[i]));
            }
             
            if (i == 0 && !isMobile)
            {
                strb.Append(" auto_increment PRIMARY KEY");
            }
            else //indexes.Add(cols[i]);//oracle needs to be changed to handle the primary key
            if (cols[i].DataType == OdDbType.Long)
            {
                //All bigints are assumed to be either keys or foreign keys.
                indexes.Add(cols[i]);
            }
              
            //for oracle
            if (i < cols.Count - 1)
            {
                strb.Append(",");
            }
             
        }
        for (int i = 0;i < indexes.Count;i++)
        {
            strb.Append(",");
            //There will always be a column defined before this, so we need to add a comma before we add an index.
            strb.Append(rn + tb + t2 + "INDEX(" + indexes[i].ColumnName + ")");
        }
        strb.Append(rn + tb + t2 + ") DEFAULT CHARSET=utf8\";");
        strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        //for(int i=0;i<indexes.Count;i++) {
        //	strb.Append(rn+tb+t1+"command=@\"ALTER TABLE "+tableName+" ADD INDEX ("+indexes[i].ColumnName+")\";");
        //	strb.Append(rn+tb+t1+"Db.NonQ(command);");
        //}
        strb.Append(rn + tb + "}");
        if (isMobile)
        {
            return strb.ToString();
        }
         
        //no oracle
        strb.Append(rn + tb + "else {//oracle");
        strb.Append(rn + tb + t1 + "command=\"BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + tableName + "'; EXCEPTION WHEN OTHERS THEN NULL; END;\";");
        //Equivalent to "drop table if exists <table>"
        strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        strb.Append(rn + tb + t1 + "command=@\"CREATE TABLE " + tableName + " (");
        for (int i = 0;i < cols.Count;i++)
        {
            String tempData = GetOracleBlankData(cols[i]);
            //to save calls to the function, and shorten the following line of code.
            strb.Append(rn + tb + t2 + cols[i].ColumnName + " " + GetOracleType(cols[i]) + (tempData == null ? "" : (StringSupport.equals(tempData, "0") ? " NOT NULL" : " DEFAULT " + tempData + " NOT NULL")) + ",");
        }
        //        Columns are added to index from the MySQL section above. Technically the same columns should have indexes in MySql and Oracle.
        //				if(cols[i].DataType==OdDbType.Long) {//all OdDbType.Long columns are assumed to be either keys or foreign keys.
        //					indexes.Add(cols[i]);
        //				}
        String constraint = tableName + "_" + cols[0].ColumnName;
        strb.Append(rn + tb + t2 + "CONSTRAINT " + constraint.Substring(0, Math.Min(30, constraint.Length)) + " PRIMARY KEY (" + cols[0].ColumnName + ")");
        strb.Append(rn + tb + t2 + ")\";");
        strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        for (int i = 0;i < indexes.Count;i++)
        {
            /**
            * /Generate timestamp triggers if they need to be created.
            */
            //for(int i=0;i<cols.Count;i++) {//check for timestamp columns
            //  if(cols[i].DataType == OdDbType.DateTimeStamp) {
            //    strb.Append(rn+tb+t1+"command=@\"CREATE OR REPLACE TRIGGER "+tableName+"_timestamp");
            //    strb.Append(rn+tb+t1+"           BEFORE UPDATE ON "+tableName);
            //    strb.Append(rn+tb+t1+"           FOR EACH ROW");
            //    strb.Append(rn+tb+t1+"           BEGIN");
            //    for(int j=0;j<cols.Count;j++) {//Each column in the table must be set up to change timestamp when changed
            //      strb.Append(rn+tb+t2+"           IF :OLD."+cols[j].ColumnName+" <> :NEW."+cols[j].ColumnName+" THEN");
            //      strb.Append(rn+tb+t2+"           :NEW."+cols[i].ColumnName+" := SYSDATE;");
            //      strb.Append(rn+tb+t2+"           END IF");
            //    }
            //    strb.Append(rn+tb+t1+"           END "+tableName+"_timestamp;\";");
            //    strb.Append(rn+tb+t1+"Db.NonQ(command);");
            //  }
            //}
            //Generate Triggers if need be
            String indexName = tableName + "_" + indexes[i].ColumnName;
            strb.Append(rn + tb + t1 + "command=@\"CREATE INDEX " + indexName.Substring(0, Math.Min(30, indexName.Length)) + " ON " + tableName + " (" + indexes[i].ColumnName + ")\";");
            strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        }
        strb.Append(rn + tb + "}");
        return strb.ToString();
    }

    /**
    * Generates C# code to Add Column to table. List of DbSchemaCol cols should not contain the new column to be added.
    */
    public static String addColumnEnd(String tableName, DbSchemaCol col, int tabInset) throws Exception {
        StringBuilder strb = new StringBuilder();
        tb = "";
        for (int i = 0;i < tabInset;i++)
        {
            //must reset tabs each time method is called
            //defines the base tabs to be added to all lines
            tb += "\t";
        }
        strb.Append(tb + "if(DataConnection.DBtype==DatabaseType.MySql) {");
        strb.Append(rn + tb + t1 + "command=\"ALTER TABLE " + tableName + " ADD " + col.ColumnName + " " + getMySqlType(col) + (col.DataType == OdDbType.DateTimeStamp ? "" : " NOT NULL") + (col.DataType == OdDbType.Date ? " DEFAULT '0001-01-01')" : "") + "\";");
        //			strb.Append(rn+tb+t1+"//If ColEnd might be over 65k characters, use mediumtext");
        strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        if (col.DataType == OdDbType.DateTimeStamp)
        {
            //set value of new timestamp column to now()
            strb.Append(rn + tb + t1 + "command=\"UPDATE " + tableName + " SET " + col.ColumnName + " = NOW()\";");
            strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        }
         
        if (col.DataType == OdDbType.Long)
        {
            //key or foreign key
            strb.Append(rn + tb + t1 + "command=\"ALTER TABLE " + tableName + " ADD INDEX (" + col.ColumnName + ")\";");
            strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        }
         
        strb.Append(rn + tb + "}");
        strb.Append(rn + tb + "else {//oracle");
        strb.Append(rn + tb + t1 + "command=\"ALTER TABLE " + tableName + " ADD " + col.ColumnName + " " + getOracleType(col) + "\";");
        strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        if (col.DataType == OdDbType.DateTimeStamp)
        {
            //set value of new timestamp column to SYSTIMESTAMP
            strb.Append(rn + tb + t1 + "command=\"UPDATE " + tableName + " SET " + col.ColumnName + " = SYSTIMESTAMP\";");
            strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        }
         
        if (getOracleBlankData(col) != null)
        {
            //Do not add NOT NULL constraint because empty strings are stored as NULL in Oracle
            //Non string types must be filled with "blank" data and set to NOT NULL
            strb.Append(rn + tb + t1 + "command=\"UPDATE " + tableName + " SET " + col.ColumnName + " = " + getOracleBlankData(col) + " WHERE " + col.ColumnName + " IS NULL\";");
            strb.Append(rn + tb + t1 + "Db.NonQ(command);");
            strb.Append(rn + tb + t1 + "command=\"ALTER TABLE " + tableName + " MODIFY " + col.ColumnName + " NOT NULL\";");
            strb.Append(rn + tb + t1 + "Db.NonQ(command);");
            if (col.DataType == OdDbType.Long)
            {
                //key or foreign key
                String indexName = tableName + "_" + col.ColumnName;
                strb.Append(rn + tb + t1 + "command=@\"CREATE INDEX " + indexName.Substring(0, Math.Min(30, indexName.Length)) + " ON " + tableName + " (" + col.ColumnName + ")\";");
                strb.Append(rn + tb + t1 + "Db.NonQ(command);");
            }
             
        }
         
        //if(cols != null) {//this should be removed once the nulls have been removed from the function calls.
        //  cols.Add(col);
        //  for(int i=0;i<cols.Count;i++) {//check for timestamp columns
        //    if(cols[i].DataType == OdDbType.DateTimeStamp) {
        //      strb.Append(rn+tb+t1+"command=@\"CREATE OR REPLACE TRIGGER "+tableName+"_timestamp");
        //      strb.Append(rn+tb+t1+"           BEFORE UPDATE ON "+tableName);
        //      strb.Append(rn+tb+t1+"           FOR EACH ROW");
        //      strb.Append(rn+tb+t1+"           BEGIN");
        //      for(int j=0;j<cols.Count;j++) {//Each column in the table must be set up to change timestamp when changed
        //        strb.Append(rn+tb+t2+"           IF :OLD."+cols[j].ColumnName+" <> :NEW."+cols[j].ColumnName+" THEN");
        //        strb.Append(rn+tb+t2+"           :NEW."+cols[i].ColumnName+" := SYSDATE;");
        //        strb.Append(rn+tb+t2+"           END IF");
        //      }
        //      strb.Append(rn+tb+t1+"           END "+tableName+"_timestamp;\";");
        //      strb.Append(rn+tb+t1+"Db.NonQ(command);");
        //    }
        //  }
        //}
        strb.Append(rn + tb + "}");
        return strb.ToString();
    }

    /**
    * 
    */
    public static String addIndex(String tableName, String colName, int tabInset) throws Exception {
        StringBuilder strb = new StringBuilder();
        tb = "";
        for (int i = 0;i < tabInset;i++)
        {
            //must reset tabs each time method is called
            //defines the base tabs to be added to all lines
            tb += "\t";
        }
        strb.Append(tb + "if(DataConnection.DBtype==DatabaseType.MySql) {");
        strb.Append(rn + tb + t1 + "command=\"ALTER TABLE " + tableName + " ADD INDEX(" + colName + ")\";");
        strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        strb.Append(rn + tb + "}");
        strb.Append(rn + tb + "else {//oracle");
        String indexName = tableName + "_" + colName;
        strb.Append(rn + tb + t1 + "command=\"CREATE INDEX " + indexName.Substring(0, Math.Min(30, indexName.Length)) + " ON " + tableName + " (" + colName + ")\";");
        strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        strb.Append(rn + tb + "}");
        return strb.ToString();
    }

    /**
    * Does not work for Timestamp because of Oracle triggers.
    */
    public static String dropColumn(String tableName, String colName, int tabInset) throws Exception {
        StringBuilder strb = new StringBuilder();
        tb = "";
        for (int i = 0;i < tabInset;i++)
        {
            //must reset tabs each time method is called
            //defines the base tabs to be added to all lines
            tb += "\t";
        }
        strb.Append(tb + "if(DataConnection.DBtype==DatabaseType.MySql) {");
        strb.Append(rn + tb + t1 + "command=\"ALTER TABLE " + tableName + " DROP COLUMN " + colName + "\";");
        strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        strb.Append(rn + tb + "}");
        strb.Append(rn + tb + "else {//oracle");
        strb.Append(rn + tb + t1 + "command=\"ALTER TABLE " + tableName + " DROP COLUMN " + colName + "\";");
        strb.Append(rn + tb + t1 + "Db.NonQ(command);");
        strb.Append(rn + tb + "}");
        return strb.ToString();
    }

    /**
    * For example, might return "bigint".
    */
    private static String getMySqlType(DbSchemaCol col) throws Exception {
        switch(col.DataType)
        {
            case Bool: 
                return "tinyint";
            case Byte: 
                return "tinyint unsigned";
            case Currency: 
                return "double";
            case Date: 
                return "date";
            case DateTime: 
                return "datetime";
            case DateTimeStamp: 
                return "timestamp";
            case Float: 
                return "float";
            case Enum: 
                return "tinyint";
            case Int: 
                if (col.IntUseSmallInt)
                {
                    return "smallint";
                }
                else
                {
                    return "int";
                } 
            case Long: 
                return "bigint";
            case Text: 
                if (col.TextSize == TextSizeMySqlOracle.Small || col.TextSize == TextSizeMySqlOracle.Medium)
                {
                    return "text";
                }
                else
                {
                    return "mediumtext";
                } 
            case TimeOfDay: 
                return "time";
            case TimeSpan: 
                return "time";
            case VarChar255: 
                return "varchar(255)";
            default: 
                throw new ApplicationException("type not found");
        
        }
    }

    //textSize==TextSizeMySqlOracle.large
    /**
    * For example, might returns "0", "", or "01-01-0001" for cols with types OdDbType.Byte, OdDbType.Text, and OdDbType.DateTime respectively.
    */
    private static String getMySqlBlankData(DbSchemaCol col) throws Exception {
        switch(col.DataType)
        {
            case Bool: 
            case Byte: 
            case Currency: 
            case Enum: 
            case Float: 
            case Int: 
            case Long: 
                return "0";
            case Date: 
                return "'0001-01-01'";
            case DateTimeStamp: 
                return "NOW()";
            case DateTime: 
                return "'0001-01-01 00:00:00'";
            case TimeOfDay: 
            case TimeSpan: 
                return "'00:00:00'";
            case Text: 
            case VarChar255: 
                return "\"\"";
            default: 
                throw new ApplicationException("type not found");
        
        }
    }

    //sets date to 01 JAN 2001, 00:00:00
    //sets to empty string
    /**
    * For example, might return "NUMBER(11) NOT NULL".
    */
    private static String getOracleType(DbSchemaCol col) throws Exception {
        switch(col.DataType)
        {
            case Bool: 
                return "number(3)";
            case Byte: 
                return "number(3)";
            case Currency: 
                return "number(38,8)";
            case Date: 
                return "date";
            case DateTime: 
                return "date";
            case DateTimeStamp: 
                return "timestamp";
            case Float: 
                return "number(38,8)";
            case Enum: 
                return "number(3)";
            case Int: 
                return "number(11)";
            case Long: 
                return "number(20)";
            case Text: 
                //also requires trigger, trigger code is automatically created above.
                if (col.TextSize == TextSizeMySqlOracle.Small)
                {
                    return "varchar2(4000)";
                }
                else
                {
                    return "clob";
                } 
            case TimeOfDay: 
                return "date";
            case TimeSpan: 
                return "varchar2(255)";
            case VarChar255: 
                return "varchar2(255)";
            default: 
                throw new ApplicationException("type not found");
        
        }
    }

    //textSize == medium or large
    /**
    * For example, might returns "0", "", or "01-JAN-0001" for cols with types OdDbType.Byte, OdDbType.Text, and OdDbType.DateTime respectively.
    */
    private static String getOracleBlankData(DbSchemaCol col) throws Exception {
        switch(col.DataType)
        {
            case Bool: 
            case Byte: 
            case Currency: 
            case Float: 
            case Enum: 
            case Int: 
            case Long: 
                return "0";
            case Date: 
            case DateTime: 
            case TimeOfDay: 
                return "TO_DATE('0001-01-01','YYYY-MM-DD')";
            case DateTimeStamp: 
                return null;
            case Text: 
            case TimeSpan: 
            case VarChar255: 
                return null;
            default: 
                throw new ApplicationException("type not found");
        
        }
    }

}


//timestamp is stored as a date and trigger combination
//stored as NULL,
/**
* //Rebuilds timestamp triggers for Oracle timestamps.
*/
//private static string TimeStampTriggerBuilderOracle(string tableName,List<DbSchemaCol> cols,int tabInset) {
//  StringBuilder strb = new StringBuilder();
//  tb="";//must reset tabs each time method is called
//  for(int i=0;i<tabInset;i++) {//defines the base tabs to be added to all lines
//    tb+="\t";
//  }
//  if(DataConnection.DBtype==DatabaseType.Oracle) {
//    for(int i=0;i<cols.Count;i++) {//check for timestamp columns
//      if(cols[i].DataType == OdDbType.DateTimeStamp) {
//        strb.Append(rn+tb+t1+"command=@\"CREATE OR REPLACE TRIGGER "+tableName+"_timestamp");
//        strb.Append(rn+tb+t1+"           BEFORE UPDATE ON "+tableName);
//        strb.Append(rn+tb+t1+"           FOR EACH ROW");
//        strb.Append(rn+tb+t1+"           BEGIN");
//        for(int j=0;j<cols.Count;j++) {//Each column in the table must be set up to change timestamp when changed
//          strb.Append(rn+tb+t2+"           IF :OLD."+cols[j].ColumnName+" <> :NEW."+cols[j].ColumnName+" THEN");
//          strb.Append(rn+tb+t2+"           :NEW."+cols[i].ColumnName+" := SYSDATE;");
//          strb.Append(rn+tb+t2+"           END IF");
//        }
//        strb.Append(rn+tb+t1+"           END "+tableName+"_timestamp;\";");
//        strb.Append(rn+tb+t1+"Db.NonQ(command);");
//      }
//    }
//  }
//  return strb.ToString();
//}