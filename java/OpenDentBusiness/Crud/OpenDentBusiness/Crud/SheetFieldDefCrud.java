//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:07 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class SheetFieldDefCrud   
{
    /**
    * Gets one SheetFieldDef object from the database using the primary key.  Returns null if not found.
    */
    public static SheetFieldDef selectOne(long sheetFieldDefNum) throws Exception {
        String command = "SELECT * FROM sheetfielddef " + "WHERE SheetFieldDefNum = " + POut.Long(sheetFieldDefNum);
        List<SheetFieldDef> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one SheetFieldDef object from the database using a query.
    */
    public static SheetFieldDef selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<SheetFieldDef> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of SheetFieldDef objects from the database using a query.
    */
    public static List<SheetFieldDef> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<SheetFieldDef> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<SheetFieldDef> tableToList(DataTable table) throws Exception {
        List<SheetFieldDef> retVal = new List<SheetFieldDef>();
        SheetFieldDef sheetFieldDef = new SheetFieldDef();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            sheetFieldDef = new SheetFieldDef();
            sheetFieldDef.SheetFieldDefNum = PIn.Long(table.Rows[i]["SheetFieldDefNum"].ToString());
            sheetFieldDef.SheetDefNum = PIn.Long(table.Rows[i]["SheetDefNum"].ToString());
            sheetFieldDef.FieldType = (SheetFieldType)PIn.Int(table.Rows[i]["FieldType"].ToString());
            sheetFieldDef.FieldName = PIn.String(table.Rows[i]["FieldName"].ToString());
            sheetFieldDef.FieldValue = PIn.String(table.Rows[i]["FieldValue"].ToString());
            sheetFieldDef.FontSize = PIn.Float(table.Rows[i]["FontSize"].ToString());
            sheetFieldDef.FontName = PIn.String(table.Rows[i]["FontName"].ToString());
            sheetFieldDef.FontIsBold = PIn.Bool(table.Rows[i]["FontIsBold"].ToString());
            sheetFieldDef.XPos = PIn.Int(table.Rows[i]["XPos"].ToString());
            sheetFieldDef.YPos = PIn.Int(table.Rows[i]["YPos"].ToString());
            sheetFieldDef.Width = PIn.Int(table.Rows[i]["Width"].ToString());
            sheetFieldDef.Height = PIn.Int(table.Rows[i]["Height"].ToString());
            sheetFieldDef.GrowthBehavior = (GrowthBehaviorEnum)PIn.Int(table.Rows[i]["GrowthBehavior"].ToString());
            sheetFieldDef.RadioButtonValue = PIn.String(table.Rows[i]["RadioButtonValue"].ToString());
            sheetFieldDef.RadioButtonGroup = PIn.String(table.Rows[i]["RadioButtonGroup"].ToString());
            sheetFieldDef.IsRequired = PIn.Bool(table.Rows[i]["IsRequired"].ToString());
            sheetFieldDef.TabOrder = PIn.Int(table.Rows[i]["TabOrder"].ToString());
            sheetFieldDef.ReportableName = PIn.String(table.Rows[i]["ReportableName"].ToString());
            retVal.Add(sheetFieldDef);
        }
        return retVal;
    }

    /**
    * Inserts one SheetFieldDef into the database.  Returns the new priKey.
    */
    public static long insert(SheetFieldDef sheetFieldDef) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            sheetFieldDef.SheetFieldDefNum = DbHelper.GetNextOracleKey("sheetfielddef", "SheetFieldDefNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(sheetFieldDef,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        sheetFieldDef.SheetFieldDefNum++;
                        loopcount++;
                    }
                    else
                    {
                        throw ex;
                    } 
                }
            
            }
            throw new ApplicationException("Insert failed.  Could not generate primary key.");
        }
        else
        {
            return insert(sheetFieldDef,false);
        } 
    }

    /**
    * Inserts one SheetFieldDef into the database.  Provides option to use the existing priKey.
    */
    public static long insert(SheetFieldDef sheetFieldDef, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            sheetFieldDef.SheetFieldDefNum = ReplicationServers.GetKey("sheetfielddef", "SheetFieldDefNum");
        }
         
        String command = "INSERT INTO sheetfielddef (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "SheetFieldDefNum,";
        }
         
        command += "SheetDefNum,FieldType,FieldName,FieldValue,FontSize,FontName,FontIsBold,XPos,YPos,Width,Height,GrowthBehavior,RadioButtonValue,RadioButtonGroup,IsRequired,TabOrder,ReportableName) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(sheetFieldDef.SheetFieldDefNum) + ",";
        }
         
        command += POut.Long(sheetFieldDef.SheetDefNum) + "," + POut.Int((int)sheetFieldDef.FieldType) + "," + "'" + POut.String(sheetFieldDef.FieldName) + "'," + DbHelper.ParamChar + "paramFieldValue," + POut.Float(sheetFieldDef.FontSize) + "," + "'" + POut.String(sheetFieldDef.FontName) + "'," + POut.Bool(sheetFieldDef.FontIsBold) + "," + POut.Int(sheetFieldDef.XPos) + "," + POut.Int(sheetFieldDef.YPos) + "," + POut.Int(sheetFieldDef.Width) + "," + POut.Int(sheetFieldDef.Height) + "," + POut.Int((int)sheetFieldDef.GrowthBehavior) + "," + "'" + POut.String(sheetFieldDef.RadioButtonValue) + "'," + "'" + POut.String(sheetFieldDef.RadioButtonGroup) + "'," + POut.Bool(sheetFieldDef.IsRequired) + "," + POut.Int(sheetFieldDef.TabOrder) + "," + "'" + POut.String(sheetFieldDef.ReportableName) + "')";
        if (sheetFieldDef.FieldValue == null)
        {
            sheetFieldDef.FieldValue = "";
        }
         
        OdSqlParameter paramFieldValue = new OdSqlParameter("paramFieldValue", OdDbType.Text, sheetFieldDef.FieldValue);
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command, paramFieldValue);
        }
        else
        {
            sheetFieldDef.SheetFieldDefNum = Db.NonQ(command, true, paramFieldValue);
        } 
        return sheetFieldDef.SheetFieldDefNum;
    }

    /**
    * Updates one SheetFieldDef in the database.
    */
    public static void update(SheetFieldDef sheetFieldDef) throws Exception {
        String command = "UPDATE sheetfielddef SET " + "SheetDefNum     =  " + POut.Long(sheetFieldDef.SheetDefNum) + ", " + "FieldType       =  " + POut.Int((int)sheetFieldDef.FieldType) + ", " + "FieldName       = '" + POut.String(sheetFieldDef.FieldName) + "', " + "FieldValue      =  " + DbHelper.ParamChar + "paramFieldValue, " + "FontSize        =  " + POut.Float(sheetFieldDef.FontSize) + ", " + "FontName        = '" + POut.String(sheetFieldDef.FontName) + "', " + "FontIsBold      =  " + POut.Bool(sheetFieldDef.FontIsBold) + ", " + "XPos            =  " + POut.Int(sheetFieldDef.XPos) + ", " + "YPos            =  " + POut.Int(sheetFieldDef.YPos) + ", " + "Width           =  " + POut.Int(sheetFieldDef.Width) + ", " + "Height          =  " + POut.Int(sheetFieldDef.Height) + ", " + "GrowthBehavior  =  " + POut.Int((int)sheetFieldDef.GrowthBehavior) + ", " + "RadioButtonValue= '" + POut.String(sheetFieldDef.RadioButtonValue) + "', " + "RadioButtonGroup= '" + POut.String(sheetFieldDef.RadioButtonGroup) + "', " + "IsRequired      =  " + POut.Bool(sheetFieldDef.IsRequired) + ", " + "TabOrder        =  " + POut.Int(sheetFieldDef.TabOrder) + ", " + "ReportableName  = '" + POut.String(sheetFieldDef.ReportableName) + "' " + "WHERE SheetFieldDefNum = " + POut.Long(sheetFieldDef.SheetFieldDefNum);
        if (sheetFieldDef.FieldValue == null)
        {
            sheetFieldDef.FieldValue = "";
        }
         
        OdSqlParameter paramFieldValue = new OdSqlParameter("paramFieldValue", OdDbType.Text, sheetFieldDef.FieldValue);
        Db.NonQ(command, paramFieldValue);
    }

    /**
    * Updates one SheetFieldDef in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(SheetFieldDef sheetFieldDef, SheetFieldDef oldSheetFieldDef) throws Exception {
        String command = "";
        if (sheetFieldDef.SheetDefNum != oldSheetFieldDef.SheetDefNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SheetDefNum = " + POut.Long(sheetFieldDef.SheetDefNum) + "";
        }
         
        if (sheetFieldDef.FieldType != oldSheetFieldDef.FieldType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldType = " + POut.Int((int)sheetFieldDef.FieldType) + "";
        }
         
        if (sheetFieldDef.FieldName != oldSheetFieldDef.FieldName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldName = '" + POut.String(sheetFieldDef.FieldName) + "'";
        }
         
        if (sheetFieldDef.FieldValue != oldSheetFieldDef.FieldValue)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldValue = " + DbHelper.ParamChar + "paramFieldValue";
        }
         
        if (sheetFieldDef.FontSize != oldSheetFieldDef.FontSize)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FontSize = " + POut.Float(sheetFieldDef.FontSize) + "";
        }
         
        if (sheetFieldDef.FontName != oldSheetFieldDef.FontName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FontName = '" + POut.String(sheetFieldDef.FontName) + "'";
        }
         
        if (sheetFieldDef.FontIsBold != oldSheetFieldDef.FontIsBold)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FontIsBold = " + POut.Bool(sheetFieldDef.FontIsBold) + "";
        }
         
        if (sheetFieldDef.XPos != oldSheetFieldDef.XPos)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "XPos = " + POut.Int(sheetFieldDef.XPos) + "";
        }
         
        if (sheetFieldDef.YPos != oldSheetFieldDef.YPos)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "YPos = " + POut.Int(sheetFieldDef.YPos) + "";
        }
         
        if (sheetFieldDef.Width != oldSheetFieldDef.Width)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Width = " + POut.Int(sheetFieldDef.Width) + "";
        }
         
        if (sheetFieldDef.Height != oldSheetFieldDef.Height)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Height = " + POut.Int(sheetFieldDef.Height) + "";
        }
         
        if (sheetFieldDef.GrowthBehavior != oldSheetFieldDef.GrowthBehavior)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "GrowthBehavior = " + POut.Int((int)sheetFieldDef.GrowthBehavior) + "";
        }
         
        if (sheetFieldDef.RadioButtonValue != oldSheetFieldDef.RadioButtonValue)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RadioButtonValue = '" + POut.String(sheetFieldDef.RadioButtonValue) + "'";
        }
         
        if (sheetFieldDef.RadioButtonGroup != oldSheetFieldDef.RadioButtonGroup)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RadioButtonGroup = '" + POut.String(sheetFieldDef.RadioButtonGroup) + "'";
        }
         
        if (sheetFieldDef.IsRequired != oldSheetFieldDef.IsRequired)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsRequired = " + POut.Bool(sheetFieldDef.IsRequired) + "";
        }
         
        if (sheetFieldDef.TabOrder != oldSheetFieldDef.TabOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TabOrder = " + POut.Int(sheetFieldDef.TabOrder) + "";
        }
         
        if (sheetFieldDef.ReportableName != oldSheetFieldDef.ReportableName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ReportableName = '" + POut.String(sheetFieldDef.ReportableName) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (sheetFieldDef.FieldValue == null)
        {
            sheetFieldDef.FieldValue = "";
        }
         
        OdSqlParameter paramFieldValue = new OdSqlParameter("paramFieldValue", OdDbType.Text, sheetFieldDef.FieldValue);
        command = "UPDATE sheetfielddef SET " + command + " WHERE SheetFieldDefNum = " + POut.Long(sheetFieldDef.SheetFieldDefNum);
        Db.NonQ(command, paramFieldValue);
    }

    /**
    * Deletes one SheetFieldDef from the database.
    */
    public static void delete(long sheetFieldDefNum) throws Exception {
        String command = "DELETE FROM sheetfielddef " + "WHERE SheetFieldDefNum = " + POut.Long(sheetFieldDefNum);
        Db.NonQ(command);
    }

}


