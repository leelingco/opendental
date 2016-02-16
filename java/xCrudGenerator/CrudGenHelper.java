//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package xCrudGenerator;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CrudColumnAttribute;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.CrudTableAttribute;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class CrudGenHelper   
{
    /**
    * Will throw exception if no primary key attribute defined.
    */
    public static FieldInfo getPriKey(FieldInfo[] fields, String tableName) throws Exception {
        for (int i = 0;i < fields.Length;i++)
        {
            Object[] attributes = fields[i].GetCustomAttributes(CrudColumnAttribute.class, true);
            if (attributes.Length != 1)
            {
                continue;
            }
             
            if (((CrudColumnAttribute)attributes[0]).getIsPriKey())
            {
                return fields[i];
            }
             
        }
        throw new ApplicationException("No primary key defined for " + tableName);
    }

    /**
    * Will throw exception if no primary key attribute defined.
    */
    public static FieldInfo getPriKeyMobile1(FieldInfo[] fields, String tableName) throws Exception {
        for (int i = 0;i < fields.Length;i++)
        {
            Object[] attributes = fields[i].GetCustomAttributes(CrudColumnAttribute.class, true);
            if (attributes.Length != 1)
            {
                continue;
            }
             
            if (((CrudColumnAttribute)attributes[0]).getIsPriKeyMobile1())
            {
                return fields[i];
            }
             
        }
        throw new ApplicationException("No primary key 1 defined for " + tableName);
    }

    /**
    * Will throw exception if no primary key attribute defined.
    */
    public static FieldInfo getPriKeyMobile2(FieldInfo[] fields, String tableName) throws Exception {
        for (int i = 0;i < fields.Length;i++)
        {
            Object[] attributes = fields[i].GetCustomAttributes(CrudColumnAttribute.class, true);
            if (attributes.Length != 1)
            {
                continue;
            }
             
            if (((CrudColumnAttribute)attributes[0]).getIsPriKeyMobile2())
            {
                return fields[i];
            }
             
        }
        throw new ApplicationException("No primary key 2 defined for " + tableName);
    }

    /**
    * The name of the table in the database.  By default, the lowercase name of the class type.
    */
    public static String getTableName(Type typeClass) throws Exception {
        Object[] attributes = typeClass.GetCustomAttributes(CrudTableAttribute.class, true);
        if (attributes.Length == 0)
        {
            return typeClass.Name.ToLower();
        }
         
        for (int i = 0;i < attributes.Length;i++)
        {
            if (attributes[i].GetType() != CrudTableAttribute.class)
            {
                continue;
            }
             
            if (!StringSupport.equals(((CrudTableAttribute)attributes[i]).getTableName(), ""))
            {
                return ((CrudTableAttribute)attributes[i]).getTableName();
            }
             
        }
        return typeClass.Name.ToLower();
    }

    //couldn't find any override.
    /**
    * 
    */
    public static boolean isDeleteForbidden(Type typeClass) throws Exception {
        Object[] attributes = typeClass.GetCustomAttributes(CrudTableAttribute.class, true);
        if (attributes.Length == 0)
        {
            return false;
        }
         
        for (int i = 0;i < attributes.Length;i++)
        {
            if (attributes[i].GetType() != CrudTableAttribute.class)
            {
                continue;
            }
             
            if (((CrudTableAttribute)attributes[i]).getIsDeleteForbidden())
            {
                return true;
            }
             
        }
        return false;
    }

    //couldn't find any.
    /**
    * 
    */
    public static boolean isMissingInGeneral(Type typeClass) throws Exception {
        Object[] attributes = typeClass.GetCustomAttributes(CrudTableAttribute.class, true);
        if (attributes.Length == 0)
        {
            return false;
        }
         
        for (int i = 0;i < attributes.Length;i++)
        {
            if (attributes[i].GetType() != CrudTableAttribute.class)
            {
                continue;
            }
             
            if (((CrudTableAttribute)attributes[i]).getIsMissingInGeneral())
            {
                return true;
            }
             
        }
        return false;
    }

    //couldn't find any.
    /**
    * 
    */
    public static boolean isMobile(Type typeClass) throws Exception {
        Object[] attributes = typeClass.GetCustomAttributes(CrudTableAttribute.class, true);
        if (attributes.Length == 0)
        {
            return false;
        }
         
        for (int i = 0;i < attributes.Length;i++)
        {
            if (attributes[i].GetType() != CrudTableAttribute.class)
            {
                continue;
            }
             
            if (((CrudTableAttribute)attributes[i]).getIsMobile())
            {
                return true;
            }
             
        }
        return false;
    }

    //couldn't find any.
    /**
    * For Mobile, this only excludes PK2; result includes PK1, the CustomerNum.  Always excludes fields that are not in the database, like patient.Age.
    */
    public static List<FieldInfo> getFieldsExceptPriKey(FieldInfo[] fields, FieldInfo priKey) throws Exception {
        List<FieldInfo> retVal = new List<FieldInfo>();
        for (int i = 0;i < fields.Length;i++)
        {
            if (fields[i].Name == priKey.Name)
            {
                continue;
            }
             
            if (IsNotDbColumn(fields[i]))
            {
                continue;
            }
             
            retVal.Add(fields[i]);
        }
        return retVal;
    }

    /**
    * This only excludes fields that are not in the database, like patient.Age.
    */
    public static List<FieldInfo> getFieldsExceptNotDb(FieldInfo[] fields) throws Exception {
        List<FieldInfo> retVal = new List<FieldInfo>();
        for (int i = 0;i < fields.Length;i++)
        {
            if (IsNotDbColumn(fields[i]))
            {
                continue;
            }
             
            retVal.Add(fields[i]);
        }
        return retVal;
    }

    /**
    * This gets all new fields which are found in the table definition but not in the database.  Result will be empty if the table itself is not in the database.
    */
    public static List<FieldInfo> getNewFields(FieldInfo[] fields, Type typeClass, String dbName) throws Exception {
        String tablename = getTableName(typeClass);
        String command = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + dbName + "' AND table_name = '" + tablename + "'";
        if (!StringSupport.equals(DataCore.getScalar(command), "1"))
        {
            return new List<FieldInfo>();
        }
         
        command = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS " + "WHERE table_name = '" + tablename + "' AND table_schema = '" + dbName + "'";
        DataTable table = DataCore.getTable(command);
        List<FieldInfo> retVal = new List<FieldInfo>();
        for (int i = 0;i < fields.Length;i++)
        {
            if (IsNotDbColumn(fields[i]))
            {
                continue;
            }
             
            boolean found = false;
                ;
            for (int t = 0;t < table.Rows.Count;t++)
            {
                if (table.Rows[t]["COLUMN_NAME"].ToString().ToLower() == fields[i].Name.ToLower())
                {
                    found = true;
                }
                 
            }
            if (!found)
            {
                retVal.Add(fields[i]);
            }
             
        }
        return retVal;
    }

    /**
    * Pass in fields processed by GetFieldsExceptPriKey.  This quick method returns the bigint fields so that indexes can possibly be added.  For mobile, pass in the priKeyName2 so that it can be excluded.  If not mobile, then set it to null.
    */
    public static List<FieldInfo> getBigIntFields(List<FieldInfo> fieldsExceptPri, String priKeyName2) throws Exception {
        List<FieldInfo> retVal = new List<FieldInfo>();
        for (int i = 0;i < fieldsExceptPri.Count;i++)
        {
            if (priKeyName2 != null)
            {
                //mobile
                if (StringSupport.equals(fieldsExceptPri[i].Name, priKeyName2))
                {
                    continue;
                }
                 
            }
             
            if (StringSupport.equals(fieldsExceptPri[i].FieldType.Name, "Int64"))
            {
                retVal.Add(fieldsExceptPri[i]);
            }
             
        }
        return retVal;
    }

    public static CrudSpecialColType getSpecialType(FieldInfo field) throws Exception {
        Object[] attributes = field.GetCustomAttributes(CrudColumnAttribute.class, true);
        if (attributes.Length == 0)
        {
            return CrudSpecialColType.None;
        }
         
        return ((CrudColumnAttribute)attributes[0]).getSpecialType();
    }

    /**
    * Normally false
    */
    public static boolean isNotDbColumn(FieldInfo field) throws Exception {
        Object[] attributes = field.GetCustomAttributes(CrudColumnAttribute.class, true);
        if (attributes.Length == 0)
        {
            return false;
        }
         
        return ((CrudColumnAttribute)attributes[0]).getIsNotDbColumn();
    }

    public static void connectToDatabase(String dbName) throws Exception {
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        dcon.setDb("localhost",dbName,"root","","","",OpenDentBusiness.DatabaseType.MySql);
        RemotingClient.RemotingRole = RemotingRole.ClientDirect;
    }

    public static void connectToDatabaseM(String dbName) throws Exception {
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        dcon.setDb("10.10.1.196",dbName,"root","","","",OpenDentBusiness.DatabaseType.MySql);
        RemotingClient.RemotingRole = RemotingRole.ClientDirect;
    }

    /**
    * Gets the regular non-mobile type by stripping the m off the end of the mobile type.  Quicker than formalizing the type with an attribute on the m table.
    */
    public static Type getTypeFromMType(String typeNameMobile, List<Type> typesReg) throws Exception {
        if (StringSupport.equals(typeNameMobile, "Userm"))
        {
            return null;
        }
         
        String typeNameReg = typeNameMobile.Substring(0, typeNameMobile.Length - 1);
        for (int i = 0;i < typesReg.Count;i++)
        {
            if (StringSupport.equals(typesReg[i].Name, typeNameReg))
            {
                return typesReg[i];
            }
             
        }
        throw new ApplicationException("Type not found.");
    }

    /**
    * Makes sure the tablename is valid.  Goes through each column and makes sure that the column is present and that the type in the database is a supported type for this C# data type.  Throws exception if it fails.
    */
    public static void validateTypes(Type typeClass, String dbName) throws Exception {
        String tablename = getTableName(typeClass);
        String command = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + dbName + "' AND table_name = '" + tablename + "'";
        if (!StringSupport.equals(DataCore.getScalar(command), "1"))
        {
            return ;
        }
         
        //can't validate
        command = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS " + "WHERE table_name = '" + tablename + "' AND table_schema = '" + dbName + "'";
        DataTable table = DataCore.getTable(command);
        FieldInfo[] fields = typeClass.GetFields();
        for (int i = 0;i < fields.Length;i++)
        {
            if (IsNotDbColumn(fields[i]))
            {
                continue;
            }
             
            ValidateColumn(dbName, tablename, fields[i], table);
        }
    }

    public static void validateColumn(String dbName, String tablename, FieldInfo field, DataTable table) throws Exception {
        //make sure the column exists
        String dataTypeInDb = "";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (table.Rows[i]["COLUMN_NAME"].ToString().ToLower() == field.Name.ToLower())
            {
                dataTypeInDb = table.Rows[i]["DATA_TYPE"].ToString();
            }
             
        }
        if (StringSupport.equals(dataTypeInDb, ""))
        {
            return ;
        }
         
        //can't validate
        CrudSpecialColType specialColType = getSpecialType(field);
        String dataTypeExpected = "";
        String dataTypeExpected2 = "";
        //if an alternate datatype is allowed
        String dataTypeExpected3 = "";
        String dataTypeExpected4 = "";
        String dataTypeExpected5 = "";
        if (specialColType == CrudSpecialColType.TimeStamp)
        {
            dataTypeExpected = "timestamp";
        }
        else if (specialColType == CrudSpecialColType.DateEntry)
        {
            dataTypeExpected = "date";
        }
        else if (specialColType == CrudSpecialColType.DateEntryEditable)
        {
            dataTypeExpected = "date";
        }
        else if (specialColType == CrudSpecialColType.DateT)
        {
            dataTypeExpected = "datetime";
        }
        else if (specialColType == CrudSpecialColType.DateTEntry)
        {
            dataTypeExpected = "datetime";
        }
        else if (specialColType == CrudSpecialColType.DateTEntryEditable)
        {
            dataTypeExpected = "datetime";
        }
        else if (specialColType == CrudSpecialColType.TinyIntSigned)
        {
            dataTypeExpected = "tinyint";
        }
        else if (specialColType == CrudSpecialColType.EnumAsString)
        {
            dataTypeExpected = "varchar";
        }
        else if (field.FieldType.IsEnum)
        {
            dataTypeExpected = "tinyint";
            dataTypeExpected2 = "int";
            dataTypeExpected3 = "smallint";
        }
        else
        {
            Name __dummyScrutVar0 = field.FieldType.Name;
            if (__dummyScrutVar0.equals("Bitmap"))
            {
                dataTypeExpected = "mediumtext";
                dataTypeExpected2 = "text";
            }
            else //only for very small images
            if (__dummyScrutVar0.equals("Boolean"))
            {
                dataTypeExpected = "tinyint";
            }
            else if (__dummyScrutVar0.equals("Byte"))
            {
                dataTypeExpected = "tinyint";
            }
            else if (__dummyScrutVar0.equals("Color"))
            {
                dataTypeExpected = "int";
            }
            else if (__dummyScrutVar0.equals("DateTime"))
            {
                dataTypeExpected = "date";
            }
            else //If the mysql field is datetime, then the C# field should have an [attribute] describing the type.
            if (__dummyScrutVar0.equals("Double"))
            {
                dataTypeExpected = "double";
            }
            else if (__dummyScrutVar0.equals("Interval"))
            {
                dataTypeExpected = "int";
            }
            else if (__dummyScrutVar0.equals("Int64"))
            {
                dataTypeExpected = "bigint";
            }
            else if (__dummyScrutVar0.equals("Int32"))
            {
                //use C# int for ItemOrder style fields.  We know they will not use random keys.
                dataTypeExpected = "int";
                dataTypeExpected2 = "smallint";
            }
            else //ok as long as the coding is careful.  Less than ideal.
            //tinyint not allowed.  Possibly change C# type to byte if values can be between 0 and 255 with no negatives.
            //We might some day use SByte for values that can be -127 to 127.  Example, perio depths, percentages that allow -1, etc.  For now, those are smallint.
            if (__dummyScrutVar0.equals("Single"))
            {
                dataTypeExpected = "float";
                //not 1:1, but we never use the full range anyway.
                dataTypeExpected2 = "float unsigned";
            }
            else if (__dummyScrutVar0.equals("String"))
            {
                dataTypeExpected = "varchar";
                dataTypeExpected2 = "text";
                dataTypeExpected3 = "char";
                dataTypeExpected4 = "mediumtext";
                dataTypeExpected5 = "longtext";
            }
            else if (__dummyScrutVar0.equals("TimeSpan"))
            {
                dataTypeExpected = "time";
            }
            else
            {
                throw new ApplicationException("Type not yet supported: " + field.FieldType.Name);
            }            
        }         
        if (!StringSupport.equals(dataTypeInDb, dataTypeExpected) && !StringSupport.equals(dataTypeInDb, dataTypeExpected2) && !StringSupport.equals(dataTypeInDb, dataTypeExpected3) && !StringSupport.equals(dataTypeInDb, dataTypeExpected4) && !StringSupport.equals(dataTypeInDb, dataTypeExpected5))
        {
            throw new Exception(tablename + "." + field.Name + " type mismatch.  Look in the lines of code above for case \"" + field.FieldType.Name + "\".  The types listed are what is allowed in the mysql database.  " + dataTypeInDb + " is not one of the allowed mysql types.");
        }
         
    }

}


