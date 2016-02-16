//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:07 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class SheetFieldCrud   
{
    /**
    * Gets one SheetField object from the database using the primary key.  Returns null if not found.
    */
    public static SheetField selectOne(long sheetFieldNum) throws Exception {
        String command = "SELECT * FROM sheetfield " + "WHERE SheetFieldNum = " + POut.Long(sheetFieldNum);
        List<SheetField> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one SheetField object from the database using a query.
    */
    public static SheetField selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<SheetField> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of SheetField objects from the database using a query.
    */
    public static List<SheetField> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<SheetField> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<SheetField> tableToList(DataTable table) throws Exception {
        List<SheetField> retVal = new List<SheetField>();
        SheetField sheetField = new SheetField();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            sheetField = new SheetField();
            sheetField.SheetFieldNum = PIn.Long(table.Rows[i]["SheetFieldNum"].ToString());
            sheetField.SheetNum = PIn.Long(table.Rows[i]["SheetNum"].ToString());
            sheetField.FieldType = (SheetFieldType)PIn.Int(table.Rows[i]["FieldType"].ToString());
            sheetField.FieldName = PIn.String(table.Rows[i]["FieldName"].ToString());
            sheetField.FieldValue = PIn.String(table.Rows[i]["FieldValue"].ToString());
            sheetField.FontSize = PIn.Float(table.Rows[i]["FontSize"].ToString());
            sheetField.FontName = PIn.String(table.Rows[i]["FontName"].ToString());
            sheetField.FontIsBold = PIn.Bool(table.Rows[i]["FontIsBold"].ToString());
            sheetField.XPos = PIn.Int(table.Rows[i]["XPos"].ToString());
            sheetField.YPos = PIn.Int(table.Rows[i]["YPos"].ToString());
            sheetField.Width = PIn.Int(table.Rows[i]["Width"].ToString());
            sheetField.Height = PIn.Int(table.Rows[i]["Height"].ToString());
            sheetField.GrowthBehavior = (GrowthBehaviorEnum)PIn.Int(table.Rows[i]["GrowthBehavior"].ToString());
            sheetField.RadioButtonValue = PIn.String(table.Rows[i]["RadioButtonValue"].ToString());
            sheetField.RadioButtonGroup = PIn.String(table.Rows[i]["RadioButtonGroup"].ToString());
            sheetField.IsRequired = PIn.Bool(table.Rows[i]["IsRequired"].ToString());
            sheetField.TabOrder = PIn.Int(table.Rows[i]["TabOrder"].ToString());
            sheetField.ReportableName = PIn.String(table.Rows[i]["ReportableName"].ToString());
            retVal.Add(sheetField);
        }
        return retVal;
    }

    /**
    * Inserts one SheetField into the database.  Returns the new priKey.
    */
    public static long insert(SheetField sheetField) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            sheetField.SheetFieldNum = DbHelper.GetNextOracleKey("sheetfield", "SheetFieldNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(sheetField,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        sheetField.SheetFieldNum++;
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
            return insert(sheetField,false);
        } 
    }

    /**
    * Inserts one SheetField into the database.  Provides option to use the existing priKey.
    */
    public static long insert(SheetField sheetField, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            sheetField.SheetFieldNum = ReplicationServers.GetKey("sheetfield", "SheetFieldNum");
        }
         
        String command = "INSERT INTO sheetfield (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "SheetFieldNum,";
        }
         
        command += "SheetNum,FieldType,FieldName,FieldValue,FontSize,FontName,FontIsBold,XPos,YPos,Width,Height,GrowthBehavior,RadioButtonValue,RadioButtonGroup,IsRequired,TabOrder,ReportableName) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(sheetField.SheetFieldNum) + ",";
        }
         
        command += POut.Long(sheetField.SheetNum) + "," + POut.Int((int)sheetField.FieldType) + "," + "'" + POut.String(sheetField.FieldName) + "'," + DbHelper.ParamChar + "paramFieldValue," + POut.Float(sheetField.FontSize) + "," + "'" + POut.String(sheetField.FontName) + "'," + POut.Bool(sheetField.FontIsBold) + "," + POut.Int(sheetField.XPos) + "," + POut.Int(sheetField.YPos) + "," + POut.Int(sheetField.Width) + "," + POut.Int(sheetField.Height) + "," + POut.Int((int)sheetField.GrowthBehavior) + "," + "'" + POut.String(sheetField.RadioButtonValue) + "'," + "'" + POut.String(sheetField.RadioButtonGroup) + "'," + POut.Bool(sheetField.IsRequired) + "," + POut.Int(sheetField.TabOrder) + "," + "'" + POut.String(sheetField.ReportableName) + "')";
        if (sheetField.FieldValue == null)
        {
            sheetField.FieldValue = "";
        }
         
        OdSqlParameter paramFieldValue = new OdSqlParameter("paramFieldValue", OdDbType.Text, sheetField.FieldValue);
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command, paramFieldValue);
        }
        else
        {
            sheetField.SheetFieldNum = Db.NonQ(command, true, paramFieldValue);
        } 
        return sheetField.SheetFieldNum;
    }

    /**
    * Updates one SheetField in the database.
    */
    public static void update(SheetField sheetField) throws Exception {
        String command = "UPDATE sheetfield SET " + "SheetNum        =  " + POut.Long(sheetField.SheetNum) + ", " + "FieldType       =  " + POut.Int((int)sheetField.FieldType) + ", " + "FieldName       = '" + POut.String(sheetField.FieldName) + "', " + "FieldValue      =  " + DbHelper.ParamChar + "paramFieldValue, " + "FontSize        =  " + POut.Float(sheetField.FontSize) + ", " + "FontName        = '" + POut.String(sheetField.FontName) + "', " + "FontIsBold      =  " + POut.Bool(sheetField.FontIsBold) + ", " + "XPos            =  " + POut.Int(sheetField.XPos) + ", " + "YPos            =  " + POut.Int(sheetField.YPos) + ", " + "Width           =  " + POut.Int(sheetField.Width) + ", " + "Height          =  " + POut.Int(sheetField.Height) + ", " + "GrowthBehavior  =  " + POut.Int((int)sheetField.GrowthBehavior) + ", " + "RadioButtonValue= '" + POut.String(sheetField.RadioButtonValue) + "', " + "RadioButtonGroup= '" + POut.String(sheetField.RadioButtonGroup) + "', " + "IsRequired      =  " + POut.Bool(sheetField.IsRequired) + ", " + "TabOrder        =  " + POut.Int(sheetField.TabOrder) + ", " + "ReportableName  = '" + POut.String(sheetField.ReportableName) + "' " + "WHERE SheetFieldNum = " + POut.Long(sheetField.SheetFieldNum);
        if (sheetField.FieldValue == null)
        {
            sheetField.FieldValue = "";
        }
         
        OdSqlParameter paramFieldValue = new OdSqlParameter("paramFieldValue", OdDbType.Text, sheetField.FieldValue);
        Db.NonQ(command, paramFieldValue);
    }

    /**
    * Updates one SheetField in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(SheetField sheetField, SheetField oldSheetField) throws Exception {
        String command = "";
        if (sheetField.SheetNum != oldSheetField.SheetNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SheetNum = " + POut.Long(sheetField.SheetNum) + "";
        }
         
        if (sheetField.FieldType != oldSheetField.FieldType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldType = " + POut.Int((int)sheetField.FieldType) + "";
        }
         
        if (sheetField.FieldName != oldSheetField.FieldName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldName = '" + POut.String(sheetField.FieldName) + "'";
        }
         
        if (sheetField.FieldValue != oldSheetField.FieldValue)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldValue = " + DbHelper.ParamChar + "paramFieldValue";
        }
         
        if (sheetField.FontSize != oldSheetField.FontSize)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FontSize = " + POut.Float(sheetField.FontSize) + "";
        }
         
        if (sheetField.FontName != oldSheetField.FontName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FontName = '" + POut.String(sheetField.FontName) + "'";
        }
         
        if (sheetField.FontIsBold != oldSheetField.FontIsBold)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FontIsBold = " + POut.Bool(sheetField.FontIsBold) + "";
        }
         
        if (sheetField.XPos != oldSheetField.XPos)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "XPos = " + POut.Int(sheetField.XPos) + "";
        }
         
        if (sheetField.YPos != oldSheetField.YPos)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "YPos = " + POut.Int(sheetField.YPos) + "";
        }
         
        if (sheetField.Width != oldSheetField.Width)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Width = " + POut.Int(sheetField.Width) + "";
        }
         
        if (sheetField.Height != oldSheetField.Height)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Height = " + POut.Int(sheetField.Height) + "";
        }
         
        if (sheetField.GrowthBehavior != oldSheetField.GrowthBehavior)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "GrowthBehavior = " + POut.Int((int)sheetField.GrowthBehavior) + "";
        }
         
        if (sheetField.RadioButtonValue != oldSheetField.RadioButtonValue)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RadioButtonValue = '" + POut.String(sheetField.RadioButtonValue) + "'";
        }
         
        if (sheetField.RadioButtonGroup != oldSheetField.RadioButtonGroup)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RadioButtonGroup = '" + POut.String(sheetField.RadioButtonGroup) + "'";
        }
         
        if (sheetField.IsRequired != oldSheetField.IsRequired)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsRequired = " + POut.Bool(sheetField.IsRequired) + "";
        }
         
        if (sheetField.TabOrder != oldSheetField.TabOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TabOrder = " + POut.Int(sheetField.TabOrder) + "";
        }
         
        if (sheetField.ReportableName != oldSheetField.ReportableName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ReportableName = '" + POut.String(sheetField.ReportableName) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (sheetField.FieldValue == null)
        {
            sheetField.FieldValue = "";
        }
         
        OdSqlParameter paramFieldValue = new OdSqlParameter("paramFieldValue", OdDbType.Text, sheetField.FieldValue);
        command = "UPDATE sheetfield SET " + command + " WHERE SheetFieldNum = " + POut.Long(sheetField.SheetFieldNum);
        Db.NonQ(command, paramFieldValue);
    }

    /**
    * Deletes one SheetField from the database.
    */
    public static void delete(long sheetFieldNum) throws Exception {
        String command = "DELETE FROM sheetfield " + "WHERE SheetFieldNum = " + POut.Long(sheetFieldNum);
        Db.NonQ(command);
    }

}


