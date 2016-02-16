//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:44 PM
//

package xCrudGenerator;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.OdDbType;
import OpenDentBusiness.OdSqlParameter;
import OpenDentBusiness.TableBase;
import xCrudGenerator.CrudGenDataInterface;
import xCrudGenerator.CrudGenHelper;
import xCrudGenerator.CrudQueries;
import xCrudGenerator.CrudSchemaForUnitTest;
import xCrudGenerator.SnippetType;
import xCrudGenerator.Form1;

public class Form1  extends Form 
{

    private String crudDir = new String();
    private String crudmDir = new String();
    private String convertDbFile = new String();
    private String convertDbFilem = new String();
    private static final String rn = "\r\n";
    private static final String t = "\t";
    private static final String t2 = "\t\t";
    private static final String t3 = "\t\t\t";
    private static final String t4 = "\t\t\t\t";
    private static final String t5 = "\t\t\t\t\t";
    private List<Type> tableTypes = new List<Type>();
    private List<Type> tableTypesAll = new List<Type>();
    private List<Type> tableTypesM = new List<Type>();
    public Form1() throws Exception {
        initializeComponent();
    }

    private void form1_Load(Object sender, EventArgs e) throws Exception {
        crudDir = "..\\..\\..\\OpenDentBusiness\\Crud";
        crudmDir = "..\\..\\..\\OpenDentBusiness\\Mobile\\Crud";
        convertDbFile = "..\\..\\..\\OpenDentBusiness\\Misc\\ConvertDatabases3.cs";
        convertDbFilem = "..\\..\\..\\OpenDentBusiness\\Mobile\\ConvertDatabasem.cs";
        if (!Directory.Exists(crudDir))
        {
            MessageBox.Show(crudDir + " is an invalid path.");
            Application.Exit();
        }
         
        if (!Directory.Exists(crudmDir))
        {
            MessageBox.Show(crudmDir + " is an invalid path.");
            Application.Exit();
        }
         
        tableTypes = new List<Type>();
        tableTypesAll = new List<Type>();
        tableTypesM = new List<Type>();
        Type typeTableBase = TableBase.class;
        Assembly assembly = Assembly.GetAssembly(typeTableBase);
        for (Object __dummyForeachVar0 : assembly.GetTypes())
        {
            Type typeClass = (Type)__dummyForeachVar0;
            if (typeClass.BaseType == typeTableBase)
            {
                if (CrudGenHelper.isMobile(typeClass))
                {
                    tableTypesM.Add(typeClass);
                }
                else
                {
                    tableTypes.Add(typeClass);
                } 
                tableTypesAll.Add(typeClass);
            }
             
        }
        tableTypesAll.Sort(CompareTypesByName);
        tableTypes.Sort(CompareTypesByName);
        tableTypesM.Sort(CompareTypesByName);
        for (int i = 0;i < tableTypes.Count;i++)
        {
            listClass.Items.Add(tableTypesAll[i].Name);
        }
        for (int i = 0;i < Enum.GetNames(SnippetType.class).Length;i++)
        {
            comboType.Items.Add(Enum.GetNames(SnippetType.class)[i].ToString());
        }
        comboType.SelectedIndex = ((Enum)SnippetType.EntireSclass).ordinal();
    }

    private static int compareTypesByName(Type x, Type y) throws Exception {
        return x.Name.CompareTo(y.Name);
    }

    private void butRun_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        String[] files = new String[]();
        StringBuilder strb = new StringBuilder();
        String className = new String();
        if (checkRun.Checked)
        {
            files = Directory.GetFiles(crudDir);
            CrudGenHelper.ConnectToDatabase(textDb.Text);
            for (int i = 0;i < tableTypes.Count;i++)
            {
                className = tableTypes[i].Name + "Crud";
                strb = new StringBuilder();
                CrudGenHelper.ValidateTypes(tableTypes[i], textDb.Text);
                WriteAll(strb, className, tableTypes[i], false);
                File.WriteAllText(Path.Combine(crudDir, className + ".cs"), strb.ToString());
                CrudQueries.Write(convertDbFile, tableTypes[i], textDb.Text, false);
                CrudGenDataInterface.Create(convertDbFile, tableTypes[i], textDb.Text, false);
                //CrudGenDataInterfaceWebService.Create(tableTypes[i]);
                Application.DoEvents();
            }
        }
         
        if (checkRunM.Checked)
        {
            files = Directory.GetFiles(crudmDir);
            CrudGenHelper.ConnectToDatabaseM(textDbM.Text);
            for (int i = 0;i < tableTypesM.Count;i++)
            {
                className = tableTypesM[i].Name + "Crud";
                strb = new StringBuilder();
                CrudGenHelper.ValidateTypes(tableTypesM[i], textDbM.Text);
                WriteAll(strb, className, tableTypesM[i], true);
                File.WriteAllText(Path.Combine(crudmDir, className + ".cs"), strb.ToString());
                CrudQueries.Write(convertDbFilem, tableTypesM[i], textDbM.Text, true);
                CrudGenDataInterface.Create(convertDbFilem, tableTypesM[i], textDbM.Text, true);
            }
        }
         
        if (checkRunSchema.Checked)
        {
            File.WriteAllText("..\\..\\..\\OpenDentBusiness\\Db\\SchemaCrudTest.cs", CrudSchemaForUnitTest.create());
        }
         
        Cursor = Cursors.Default;
        MessageBox.Show("Done");
    }

    //Application.Exit();
    /**
    * Example of className is 'AccountCrud' or 'PatientmCrud'.
    */
    private void writeAll(StringBuilder strb, String className, Type typeClass, boolean isMobile) throws Exception {
        FieldInfo[] fields = typeClass.GetFields();
        //We can't assume they are in the correct order.
        FieldInfo priKey = null;
        FieldInfo priKey1 = null;
        FieldInfo priKey2 = null;
        if (isMobile)
        {
            priKey1 = CrudGenHelper.GetPriKeyMobile1(fields, typeClass.Name);
            priKey2 = CrudGenHelper.GetPriKeyMobile2(fields, typeClass.Name);
        }
        else
        {
            priKey = CrudGenHelper.GetPriKey(fields, typeClass.Name);
        } 
        String tablename = CrudGenHelper.getTableName(typeClass);
        //in lowercase now.
        String priKeyParam = null;
        String priKeyParam1 = null;
        String priKeyParam2 = null;
        if (isMobile)
        {
            priKeyParam1 = priKey1.Name.Substring(0, 1).ToLower() + priKey1.Name.Substring(1);
            //lowercase initial letter.  Example customerNum
            priKeyParam2 = priKey2.Name.Substring(0, 1).ToLower() + priKey2.Name.Substring(1);
        }
        else
        {
            //lowercase initial letter.  Example patNum
            priKeyParam = priKey.Name.Substring(0, 1).ToLower() + priKey.Name.Substring(1);
        } 
        //lowercase initial letter.  Example patNum
        String obj = typeClass.Name.Substring(0, 1).ToLower() + typeClass.Name.Substring(1);
        //lowercase initial letter.  Example feeSched
        String oldObj = "old" + typeClass.Name;
        //used in the second update overload.  Example oldFeeSched
        strb.Append("//This file is automatically generated." + rn + "//Do not attempt to make changes to this file because the changes will be erased and overwritten." + rn + "using System;\r\n" + 
        "using System.Collections;\r\n" + 
        "using System.Collections.Generic;\r\n" + 
        "using System.Data;\r\n" + 
        "using System.Drawing;" + rn);
        if (className.StartsWith("EhrLab"))
        {
            strb.Append("using EhrLaboratories;" + rn);
        }
         
        if (isMobile)
        {
            strb.Append(rn + "namespace OpenDentBusiness.Mobile.Crud{");
        }
        else
        {
            strb.Append(rn + "namespace OpenDentBusiness.Crud{");
        } 
        strb.Append(rn + t + "public class " + className + " {");
        //SelectOne------------------------------------------------------------------------------------------
        if (isMobile)
        {
            strb.Append(rn + t2 + "///<summary>Gets one " + typeClass.Name + " object from the database using primaryKey1(CustomerNum) and primaryKey2.  Returns null if not found.</summary>");
            strb.Append(rn + t2 + "public static " + typeClass.Name + " SelectOne(long " + priKeyParam1 + ",long " + priKeyParam2 + "){");
            strb.Append(rn + t3 + "string command=\"SELECT * FROM " + tablename + " \"");
            strb.Append(rn + t4 + "+\"WHERE " + priKey1.Name + " = \"+POut.Long(" + priKeyParam1 + ")+\" AND " + priKey2.Name + " = \"+POut.Long(" + priKeyParam2 + ");");
        }
        else
        {
            strb.Append(rn + t2 + "///<summary>Gets one " + typeClass.Name + " object from the database using the primary key.  Returns null if not found.</summary>");
            strb.Append(rn + t2 + "public static " + typeClass.Name + " SelectOne(long " + priKeyParam + "){");
            strb.Append(rn + t3 + "string command=\"SELECT * FROM " + tablename + " \"");
            strb.Append(rn + t4 + "+\"WHERE " + priKey.Name + " = \"+POut.Long(" + priKeyParam + ");");
        } 
        strb.Append(rn + t3 + "List<" + typeClass.Name + "> list=TableToList(Db.GetTable(command));");
        strb.Append(rn + t3 + "if(list.Count==0) {");
        strb.Append(rn + t4 + "return null;");
        strb.Append(rn + t3 + "}");
        strb.Append(rn + t3 + "return list[0];");
        strb.Append(rn + t2 + "}");
        //SelectOne(string command)--------------------------------------------------------------------------
        strb.Append(rn + rn + t2 + "///<summary>Gets one " + typeClass.Name + " object from the database using a query.</summary>");
        strb.Append(rn + t2 + "public static " + typeClass.Name + " SelectOne(string command){");
        strb.Append(rn + t3 + "if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {\r\n" + 
        "\t\t\t\tthrow new ApplicationException(\"Not allowed to send sql directly.  Rewrite the calling class to not use this query:\\r\\n\"+command);\r\n" + 
        "\t\t\t}");
        strb.Append(rn + t3 + "List<" + typeClass.Name + "> list=TableToList(Db.GetTable(command));");
        strb.Append(rn + t3 + "if(list.Count==0) {");
        strb.Append(rn + t4 + "return null;");
        strb.Append(rn + t3 + "}");
        strb.Append(rn + t3 + "return list[0];");
        strb.Append(rn + t2 + "}");
        //SelectMany-----------------------------------------------------------------------------------------
        strb.Append(rn + rn + t2 + "///<summary>Gets a list of " + typeClass.Name + " objects from the database using a query.</summary>");
        strb.Append(rn + t2 + "public static List<" + typeClass.Name + "> SelectMany(string command){");
        strb.Append(rn + t3 + "if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {\r\n" + 
        "\t\t\t\tthrow new ApplicationException(\"Not allowed to send sql directly.  Rewrite the calling class to not use this query:\\r\\n\"+command);\r\n" + 
        "\t\t\t}");
        strb.Append(rn + t3 + "List<" + typeClass.Name + "> list=TableToList(Db.GetTable(command));");
        strb.Append(rn + t3 + "return list;");
        strb.Append(rn + t2 + "}");
        //TableToList----------------------------------------------------------------------------------------
        strb.Append(rn + rn + t2 + "///<summary>Converts a DataTable to a list of objects.</summary>");
        strb.Append(rn + t2 + "public static List<" + typeClass.Name + "> TableToList(DataTable table){");
        strb.Append(rn + t3 + "List<" + typeClass.Name + "> retVal=new List<" + typeClass.Name + ">();");
        strb.Append(rn + t3 + typeClass.Name + " " + obj + ";");
        strb.Append(rn + t3 + "for(int i=0;i<table.Rows.Count;i++) {");
        strb.Append(rn + t4 + obj + "=new " + typeClass.Name + "();");
        List<FieldInfo> fieldsInDb = CrudGenHelper.GetFieldsExceptNotDb(fields);
        //get the longest fieldname for alignment purposes
        int longestField = 0;
        for (int f = 0;f < fieldsInDb.Count;f++)
        {
            if (fieldsInDb[f].Name.Length > longestField)
            {
                longestField = fieldsInDb[f].Name.Length;
            }
             
        }
        CrudSpecialColType specialType = CrudSpecialColType.None;
        for (int f = 0;f < fieldsInDb.Count;f++)
        {
            //Fields are not guaranteed to be in any particular order.
            specialType = CrudGenHelper.GetSpecialType(fieldsInDb[f]);
            if (specialType == CrudSpecialColType.EnumAsString)
            {
                String fieldLower = fieldsInDb[f].Name.Substring(0, 1).ToLower() + fieldsInDb[f].Name.Substring(1);
                //lowercase initial letter.  Example clockStatus
                strb.Append(rn + t4 + "string " + fieldLower + "=table.Rows[i][\"" + fieldsInDb[f].Name + "\"].ToString();");
                strb.Append(rn + t4 + "if(" + fieldLower + "==\"\"){");
                strb.Append(rn + t5 + obj + "." + fieldsInDb[f].Name.PadRight(longestField - 2, ' ') + "=" + "(" + fieldsInDb[f].FieldType.Name + ")0;");
                strb.Append(rn + t4 + "}");
                strb.Append(rn + t4 + "else try{");
                strb.Append(rn + t5 + obj + "." + fieldsInDb[f].Name.PadRight(longestField - 2, ' ') + "=" + "(" + fieldsInDb[f].FieldType.Name + ")Enum.Parse(typeof(" + fieldsInDb[f].FieldType.Name + ")," + fieldLower + ");");
                strb.Append(rn + t4 + "}");
                strb.Append(rn + t4 + "catch{");
                strb.Append(rn + t5 + obj + "." + fieldsInDb[f].Name.PadRight(longestField - 2, ' ') + "=" + "(" + fieldsInDb[f].FieldType.Name + ")0;");
                strb.Append(rn + t4 + "}");
                continue;
            }
             
            strb.Append(rn + t4 + obj + "." + fieldsInDb[f].Name.PadRight(longestField, ' ') + "= ");
            if (specialType == CrudSpecialColType.DateT || specialType == CrudSpecialColType.TimeStamp || specialType == CrudSpecialColType.DateTEntry || specialType == CrudSpecialColType.DateTEntryEditable)
            {
                //specialTypes.DateEntry and DateEntryEditable is handled fine by the normal DateTime (date) below.
                strb.Append("PIn.DateT (");
            }
            else //else if(specialType==CrudSpecialColType.EnumAsString) {//moved up
            if (specialType == CrudSpecialColType.TimeSpanNeg)
            {
                strb.Append("PIn.TSpan (");
            }
            else //no special treatment for specialType clob
            if (fieldsInDb[f].FieldType.IsEnum)
            {
                strb.Append("(" + fieldsInDb[f].FieldType.Name + ")PIn.Int(");
            }
            else
            {
                Name __dummyScrutVar0 = fieldsInDb[f].FieldType.Name;
                if (__dummyScrutVar0.equals("Bitmap"))
                {
                    strb.Append("PIn.Bitmap(");
                }
                else if (__dummyScrutVar0.equals("Boolean"))
                {
                    strb.Append("PIn.Bool  (");
                }
                else if (__dummyScrutVar0.equals("Byte"))
                {
                    strb.Append("PIn.Byte  (");
                }
                else if (__dummyScrutVar0.equals("Color"))
                {
                    strb.Append("Color.FromArgb(PIn.Int(");
                }
                else if (__dummyScrutVar0.equals("DateTime"))
                {
                    //This ONLY handles date, not dateT which is a special type.
                    strb.Append("PIn.Date  (");
                }
                else if (__dummyScrutVar0.equals("Double"))
                {
                    strb.Append("PIn.Double(");
                }
                else if (__dummyScrutVar0.equals("Interval"))
                {
                    strb.Append("new Interval(PIn.Int(");
                }
                else if (__dummyScrutVar0.equals("Int64"))
                {
                    strb.Append("PIn.Long  (");
                }
                else if (__dummyScrutVar0.equals("Int32"))
                {
                    strb.Append("PIn.Int   (");
                }
                else if (__dummyScrutVar0.equals("Single"))
                {
                    strb.Append("PIn.Float (");
                }
                else if (__dummyScrutVar0.equals("String"))
                {
                    strb.Append("PIn.String(");
                }
                else if (__dummyScrutVar0.equals("TimeSpan"))
                {
                    strb.Append("PIn.Time(");
                }
                else
                {
                    throw new ApplicationException("Type not yet supported: " + fieldsInDb[f].FieldType.Name);
                }            
            }   
            strb.Append("table.Rows[i][\"" + fieldsInDb[f].Name + "\"].ToString())");
            if (StringSupport.equals(fieldsInDb[f].FieldType.Name, "Color") || StringSupport.equals(fieldsInDb[f].FieldType.Name, "Interval"))
            {
                strb.Append(")");
            }
             
            strb.Append(";");
        }
        strb.Append(rn + t4 + "retVal.Add(" + obj + ");");
        strb.Append(rn + t3 + "}");
        strb.Append(rn + t3 + "return retVal;");
        strb.Append(rn + t2 + "}");
        //Insert---------------------------------------------------------------------------------------------
        List<FieldInfo> fieldsExceptPri = null;
        if (isMobile)
        {
            fieldsExceptPri = CrudGenHelper.GetFieldsExceptPriKey(fields, priKey2);
            //first override not used for mobile.
            //second override
            strb.Append(rn + rn + t2 + "///<summary>Usually set useExistingPK=true.  Inserts one " + typeClass.Name + " into the database.</summary>");
            strb.Append(rn + t2 + "public static long Insert(" + typeClass.Name + " " + obj + ",bool useExistingPK){");
            strb.Append(rn + t3 + "if(!useExistingPK) {");
            // && PrefC.RandomKeys) {");PrefC.RandomKeys is always true for mobile, since autoincr is just not possible.
            //Todo: ReplicationServers.GetKey() needs to work for mobile.  Not needed until we start inserting records from mobile.
            strb.Append(rn + t4 + obj + "." + priKey2.Name + "=ReplicationServers.GetKey(\"" + tablename + "\",\"" + priKey2.Name + "\");");
            strb.Append(rn + t3 + "}");
            strb.Append(rn + t3 + "string command=\"INSERT INTO " + tablename + " (\";");
            //strb.Append(rn+t3+"if(useExistingPK || PrefC.RandomKeys) {");//PrefC.RandomKeys is always true
            strb.Append(rn + t3 + "command+=\"" + priKey2.Name + ",\";");
            //strb.Append(rn+t3+"}");
            strb.Append(rn + t3 + "command+=\"");
            for (int f = 0;f < fieldsExceptPri.Count;f++)
            {
                //all fields except PK2.
                if (CrudGenHelper.GetSpecialType(fieldsExceptPri[f]) == CrudSpecialColType.TimeStamp)
                {
                    continue;
                }
                 
                if (f > 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append(fieldsExceptPri[f].Name);
            }
            strb.Append(") VALUES(\";");
            //strb.Append(rn+t3+"if(useExistingPK || PrefC.RandomKeys) {");//PrefC.RandomKeys is always true
            strb.Append(rn + t3 + "command+=POut.Long(" + obj + "." + priKey2.Name + ")+\",\";");
            //strb.Append(rn+t3+"}");
            strb.Append(rn + t3 + "command+=");
        }
        else
        {
            fieldsExceptPri = CrudGenHelper.GetFieldsExceptPriKey(fields, priKey);
            strb.Append(rn + rn + t2 + "///<summary>Inserts one " + typeClass.Name + " into the database.  Returns the new priKey.</summary>");
            strb.Append(rn + t2 + "public static long Insert(" + typeClass.Name + " " + obj + "){");
            strb.Append(rn + t3 + "if(DataConnection.DBtype==DatabaseType.Oracle) {");
            strb.Append(rn + t4 + obj + "." + priKey.Name + "=DbHelper.GetNextOracleKey(\"" + tablename + "\",\"" + priKey.Name + "\");");
            strb.Append(rn + t4 + "int loopcount=0;");
            strb.Append(rn + t4 + "while(loopcount<100){");
            strb.Append(rn + t5 + "try {");
            strb.Append(rn + t5 + t + "return Insert(" + obj + ",true);");
            strb.Append(rn + t5 + "}");
            strb.Append(rn + t5 + "catch(Oracle.DataAccess.Client.OracleException ex){");
            strb.Append(rn + t5 + t + "if(ex.Number==1 && ex.Message.ToLower().Contains(\"unique constraint\") && ex.Message.ToLower().Contains(\"violated\")){");
            strb.Append(rn + t5 + t2 + obj + "." + priKey.Name + "++;");
            strb.Append(rn + t5 + t2 + "loopcount++;");
            strb.Append(rn + t5 + t + "}");
            strb.Append(rn + t5 + t + "else{");
            strb.Append(rn + t5 + t2 + "throw ex;");
            strb.Append(rn + t5 + t + "}");
            strb.Append(rn + t5 + "}");
            strb.Append(rn + t4 + "}");
            strb.Append(rn + t4 + "throw new ApplicationException(\"Insert failed.  Could not generate primary key.\");");
            strb.Append(rn + t3 + "}");
            strb.Append(rn + t3 + "else {");
            strb.Append(rn + t4 + "return Insert(" + obj + ",false);");
            strb.Append(rn + t3 + "}");
            strb.Append(rn + t2 + "}");
            //second override
            strb.Append(rn + rn + t2 + "///<summary>Inserts one " + typeClass.Name + " into the database.  Provides option to use the existing priKey.</summary>");
            strb.Append(rn + t2 + "public static long Insert(" + typeClass.Name + " " + obj + ",bool useExistingPK){");
            strb.Append(rn + t3 + "if(!useExistingPK && PrefC.RandomKeys) {");
            strb.Append(rn + t4 + obj + "." + priKey.Name + "=ReplicationServers.GetKey(\"" + tablename + "\",\"" + priKey.Name + "\");");
            strb.Append(rn + t3 + "}");
            strb.Append(rn + t3 + "string command=\"INSERT INTO " + tablename + " (\";");
            strb.Append(rn + t3 + "if(useExistingPK || PrefC.RandomKeys) {");
            strb.Append(rn + t4 + "command+=\"" + priKey.Name + ",\";");
            strb.Append(rn + t3 + "}");
            strb.Append(rn + t3 + "command+=\"");
            for (int f = 0;f < fieldsExceptPri.Count;f++)
            {
                if (CrudGenHelper.GetSpecialType(fieldsExceptPri[f]) == CrudSpecialColType.TimeStamp)
                {
                    continue;
                }
                 
                if (f > 0)
                {
                    strb.Append(",");
                }
                 
                strb.Append(fieldsExceptPri[f].Name);
            }
            strb.Append(") VALUES(\";");
            strb.Append(rn + t3 + "if(useExistingPK || PrefC.RandomKeys) {");
            strb.Append(rn + t4 + "command+=POut.Long(" + obj + "." + priKey.Name + ")+\",\";");
            strb.Append(rn + t3 + "}");
            strb.Append(rn + t3 + "command+=");
        } 
        //a quick and dirty temporary list that just helps keep track of which columns take parameters
        List<OdSqlParameter> paramList = new List<OdSqlParameter>();
        for (int f = 0;f < fieldsExceptPri.Count;f++)
        {
            strb.Append(rn + t4);
            specialType = CrudGenHelper.GetSpecialType(fieldsExceptPri[f]);
            if (specialType == CrudSpecialColType.TimeStamp)
            {
                strb.Append("//" + fieldsExceptPri[f].Name + " can only be set by MySQL");
                continue;
            }
             
            if (f == 0)
            {
                strb.Append(" ");
            }
            else
            {
                strb.Append("+");
            } 
            if (specialType == CrudSpecialColType.DateEntry || specialType == CrudSpecialColType.DateEntryEditable || specialType == CrudSpecialColType.DateTEntry || specialType == CrudSpecialColType.DateTEntryEditable)
            {
                strb.Append("    DbHelper.Now()+\"");
            }
            else if (specialType == CrudSpecialColType.DateT)
            {
                strb.Append("    POut.DateT (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
            }
            else if (specialType == CrudSpecialColType.EnumAsString)
            {
                strb.Append("\"'\"+POut.String(" + obj + "." + fieldsExceptPri[f].Name + ".ToString())+\"'");
            }
            else if (specialType == CrudSpecialColType.TimeSpanNeg)
            {
                strb.Append("\"'\"+POut.TSpan (" + obj + "." + fieldsExceptPri[f].Name + ")+\"'");
            }
            else if (specialType == CrudSpecialColType.TextIsClob)
            {
                strb.Append("DbHelper.ParamChar+\"param" + fieldsExceptPri[f].Name);
                paramList.Add(new OdSqlParameter(fieldsExceptPri[f].Name, OdDbType.Text, null));
            }
            else if (fieldsExceptPri[f].FieldType.IsEnum)
            {
                strb.Append("    POut.Int   ((int)" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
            }
            else
            {
                Name __dummyScrutVar1 = fieldsExceptPri[f].FieldType.Name;
                if (__dummyScrutVar1.equals("Bitmap"))
                {
                    strb.Append("    POut.Bitmap(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar1.equals("Boolean"))
                {
                    strb.Append("    POut.Bool  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar1.equals("Byte"))
                {
                    strb.Append("    POut.Byte  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar1.equals("Color"))
                {
                    strb.Append("    POut.Int   (" + obj + "." + fieldsExceptPri[f].Name + ".ToArgb())+\"");
                }
                else if (__dummyScrutVar1.equals("DateTime"))
                {
                    //This is only for date, not dateT.
                    strb.Append("    POut.Date  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar1.equals("Double"))
                {
                    strb.Append("\"'\"+POut.Double(" + obj + "." + fieldsExceptPri[f].Name + ")+\"'");
                }
                else if (__dummyScrutVar1.equals("Interval"))
                {
                    strb.Append("    POut.Int   (" + obj + "." + fieldsExceptPri[f].Name + ".ToInt())+\"");
                }
                else if (__dummyScrutVar1.equals("Int64"))
                {
                    strb.Append("    POut.Long  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar1.equals("Int32"))
                {
                    strb.Append("    POut.Int   (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar1.equals("Single"))
                {
                    strb.Append("    POut.Float (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar1.equals("String"))
                {
                    strb.Append("\"'\"+POut.String(" + obj + "." + fieldsExceptPri[f].Name + ")+\"'");
                }
                else if (__dummyScrutVar1.equals("TimeSpan"))
                {
                    strb.Append("    POut.Time  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else
                {
                    throw new ApplicationException("Type not yet supported: " + fieldsExceptPri[f].FieldType.Name);
                }            
            }      
            if (f == fieldsExceptPri.Count - 2 && CrudGenHelper.GetSpecialType(fieldsExceptPri[f + 1]) == CrudSpecialColType.TimeStamp)
            {
                //in case the last field is a timestamp
                strb.Append(")\";");
            }
            else if (f < fieldsExceptPri.Count - 1)
            {
                strb.Append(",\"");
            }
            else
            {
                strb.Append(")\";");
            }  
        }
        for (int i = 0;i < paramList.Count;i++)
        {
            strb.Append(rn + t3 + "if(" + obj + "." + paramList[i].ParameterName + "==null) {");
            strb.Append(rn + t4 + "" + obj + "." + paramList[i].ParameterName + "=\"\";");
            strb.Append(rn + t3 + "}");
            //example: OdSqlParameter paramNote=new OdSqlParameter("paramNote",
            //           OdDbType.Text,procNote.Note);
            strb.Append(rn + t3 + "OdSqlParameter param" + paramList[i].ParameterName + "=new OdSqlParameter(\"param" + paramList[i].ParameterName + "\"," + "OdDbType.Text," + obj + "." + paramList[i].ParameterName + ");");
        }
        String paramsString = "";
        for (int i = 0;i < paramList.Count;i++)
        {
            //example: ,paramNote,paramAltNote
            paramsString += ",param" + paramList[i].ParameterName;
        }
        if (isMobile)
        {
            strb.Append(rn + t3 + "Db.NonQ(command" + paramsString + ");//There is no autoincrement in the mobile server.");
            strb.Append(rn + t3 + "return " + obj + "." + priKey2.Name + ";");
            strb.Append(rn + t2 + "}");
        }
        else
        {
            strb.Append(rn + t3 + "if(useExistingPK || PrefC.RandomKeys) {");
            strb.Append(rn + t4 + "Db.NonQ(command" + paramsString + ");");
            strb.Append(rn + t3 + "}");
            strb.Append(rn + t3 + "else {");
            strb.Append(rn + t4 + obj + "." + priKey.Name + "=Db.NonQ(command,true" + paramsString + ");");
            strb.Append(rn + t3 + "}");
            strb.Append(rn + t3 + "return " + obj + "." + priKey.Name + ";");
            strb.Append(rn + t2 + "}");
        } 
        //Update---------------------------------------------------------------------------------------------
        strb.Append(rn + rn + t2 + "///<summary>Updates one " + typeClass.Name + " in the database.</summary>");
        strb.Append(rn + t2 + "public static void Update(" + typeClass.Name + " " + obj + "){");
        strb.Append(rn + t3 + "string command=\"UPDATE " + tablename + " SET \"");
        for (int f = 0;f < fieldsExceptPri.Count;f++)
        {
            if (isMobile && fieldsExceptPri[f] == priKey1)
            {
                continue;
            }
             
            //2 already skipped
            specialType = CrudGenHelper.GetSpecialType(fieldsExceptPri[f]);
            if (specialType == CrudSpecialColType.DateEntry)
            {
                strb.Append(rn + t4 + "//" + fieldsExceptPri[f].Name + " not allowed to change");
                continue;
            }
             
            if (specialType == CrudSpecialColType.DateTEntry)
            {
                strb.Append(rn + t4 + "//" + fieldsExceptPri[f].Name + " not allowed to change");
                continue;
            }
             
            if (specialType == CrudSpecialColType.TimeStamp)
            {
                strb.Append(rn + t4 + "//" + fieldsExceptPri[f].Name + " can only be set by MySQL");
                continue;
            }
             
            if (specialType == CrudSpecialColType.ExcludeFromUpdate)
            {
                strb.Append(rn + t4 + "//" + fieldsExceptPri[f].Name + " excluded from update");
                continue;
            }
             
            strb.Append(rn + t4 + "+\"" + fieldsExceptPri[f].Name.PadRight(longestField, ' ') + "= ");
            if (specialType == CrudSpecialColType.DateT)
            {
                strb.Append(" \"+POut.DateT (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
            }
            else if (specialType == CrudSpecialColType.DateEntryEditable)
            {
                strb.Append(" \"+POut.Date  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
            }
            else if (specialType == CrudSpecialColType.DateTEntryEditable)
            {
                strb.Append(" \"+POut.DateT (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
            }
            else if (specialType == CrudSpecialColType.EnumAsString)
            {
                strb.Append("'\"+POut.String(" + obj + "." + fieldsExceptPri[f].Name + ".ToString())+\"'");
            }
            else if (specialType == CrudSpecialColType.TimeSpanNeg)
            {
                strb.Append("'\"+POut.TSpan (" + obj + "." + fieldsExceptPri[f].Name + ")+\"'");
            }
            else if (specialType == CrudSpecialColType.TextIsClob)
            {
                strb.Append(" \"+DbHelper.ParamChar+\"param" + fieldsExceptPri[f].Name);
            }
            else //paramList is already set above
            if (fieldsExceptPri[f].FieldType.IsEnum)
            {
                strb.Append(" \"+POut.Int   ((int)" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
            }
            else
            {
                Name __dummyScrutVar2 = fieldsExceptPri[f].FieldType.Name;
                if (__dummyScrutVar2.equals("Bitmap"))
                {
                    strb.Append(" \"+POut.Bitmap(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar2.equals("Boolean"))
                {
                    strb.Append(" \"+POut.Bool  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar2.equals("Byte"))
                {
                    strb.Append(" \"+POut.Byte  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar2.equals("Color"))
                {
                    strb.Append(" \"+POut.Int   (" + obj + "." + fieldsExceptPri[f].Name + ".ToArgb())+\"");
                }
                else if (__dummyScrutVar2.equals("DateTime"))
                {
                    //This is only for date, not dateT
                    strb.Append(" \"+POut.Date  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar2.equals("Double"))
                {
                    strb.Append("'\"+POut.Double(" + obj + "." + fieldsExceptPri[f].Name + ")+\"'");
                }
                else if (__dummyScrutVar2.equals("Interval"))
                {
                    strb.Append(" \"+POut.Int   (" + obj + "." + fieldsExceptPri[f].Name + ".ToInt())+\"");
                }
                else if (__dummyScrutVar2.equals("Int64"))
                {
                    strb.Append(" \"+POut.Long  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar2.equals("Int32"))
                {
                    strb.Append(" \"+POut.Int   (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar2.equals("Single"))
                {
                    strb.Append(" \"+POut.Float (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (__dummyScrutVar2.equals("String"))
                {
                    strb.Append("'\"+POut.String(" + obj + "." + fieldsExceptPri[f].Name + ")+\"'");
                }
                else if (__dummyScrutVar2.equals("TimeSpan"))
                {
                    strb.Append(" \"+POut.Time  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else
                {
                    throw new ApplicationException("Type not yet supported: " + fieldsExceptPri[f].FieldType.Name);
                }            
            }       
            if (f == fieldsExceptPri.Count - 2 && CrudGenHelper.GetSpecialType(fieldsExceptPri[f + 1]) == CrudSpecialColType.TimeStamp)
            {
            }
            else //in case the last field is a timestamp
            //strb.Append(" \"");
            if (f < fieldsExceptPri.Count - 1)
            {
                strb.Append(",");
            }
              
            strb.Append(" \"");
        }
        if (isMobile)
        {
            strb.Append(rn + t4 + "+\"WHERE " + priKey1.Name + " = \"+POut.Long(" + obj + "." + priKey1.Name + ")+\" AND " + priKey2.Name + " = \"+POut.Long(" + obj + "." + priKey2.Name + ");");
        }
        else
        {
            strb.Append(rn + t4 + "+\"WHERE " + priKey.Name + " = \"+POut.Long(" + obj + "." + priKey.Name + ");");
        } 
        for (int i = 0;i < paramList.Count;i++)
        {
            strb.Append(rn + t3 + "if(" + obj + "." + paramList[i].ParameterName + "==null) {");
            strb.Append(rn + t4 + "" + obj + "." + paramList[i].ParameterName + "=\"\";");
            strb.Append(rn + t3 + "}");
            strb.Append(rn + t3 + "OdSqlParameter param" + paramList[i].ParameterName + "=new OdSqlParameter(\"param" + paramList[i].ParameterName + "\"," + "OdDbType.Text," + obj + "." + paramList[i].ParameterName + ");");
        }
        strb.Append(rn + t3 + "Db.NonQ(command" + paramsString + ");");
        strb.Append(rn + t2 + "}");
        //Update, 2nd override-------------------------------------------------------------------------------
        if (!isMobile)
        {
            strb.Append(rn + rn + t2 + "///<summary>Updates one " + typeClass.Name + " in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.</summary>");
            strb.Append(rn + t2 + "public static void Update(" + typeClass.Name + " " + obj + "," + typeClass.Name + " " + oldObj + "){");
            strb.Append(rn + t3 + "string command=\"\";");
            for (int f = 0;f < fieldsExceptPri.Count;f++)
            {
                //if(isMobile && fieldsExceptPri[f]==priKey1) {//2 already skipped
                //	continue;
                //}
                specialType = CrudGenHelper.GetSpecialType(fieldsExceptPri[f]);
                if (specialType == CrudSpecialColType.DateEntry)
                {
                    strb.Append(rn + t3 + "//" + fieldsExceptPri[f].Name + " not allowed to change");
                    continue;
                }
                 
                if (specialType == CrudSpecialColType.DateTEntry)
                {
                    strb.Append(rn + t3 + "//" + fieldsExceptPri[f].Name + " not allowed to change");
                    continue;
                }
                 
                if (specialType == CrudSpecialColType.TimeStamp)
                {
                    strb.Append(rn + t3 + "//" + fieldsExceptPri[f].Name + " can only be set by MySQL");
                    continue;
                }
                 
                if (specialType == CrudSpecialColType.ExcludeFromUpdate)
                {
                    strb.Append(rn + t3 + "//" + fieldsExceptPri[f].Name + " excluded from update");
                    continue;
                }
                 
                strb.Append(rn + t3 + "if(" + obj + "." + fieldsExceptPri[f].Name + " != " + oldObj + "." + fieldsExceptPri[f].Name + ") {");
                strb.Append(rn + t4 + "if(command!=\"\"){ command+=\",\";}");
                strb.Append(rn + t4 + "command+=\"" + fieldsExceptPri[f].Name + " = ");
                if (specialType == CrudSpecialColType.DateT)
                {
                    strb.Append("\"+POut.DateT(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (specialType == CrudSpecialColType.DateEntryEditable)
                {
                    strb.Append("\"+POut.Date(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (specialType == CrudSpecialColType.DateTEntryEditable)
                {
                    strb.Append("\"+POut.DateT(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else if (specialType == CrudSpecialColType.EnumAsString)
                {
                    strb.Append("'\"+POut.String(" + obj + "." + fieldsExceptPri[f].Name + ".ToString())+\"'");
                }
                else if (specialType == CrudSpecialColType.TimeSpanNeg)
                {
                    strb.Append("'\"+POut.TSpan (" + obj + "." + fieldsExceptPri[f].Name + ")+\"'");
                }
                else if (specialType == CrudSpecialColType.TextIsClob)
                {
                    strb.Append("\"+DbHelper.ParamChar+\"param" + fieldsExceptPri[f].Name);
                }
                else //paramList is already set above
                if (fieldsExceptPri[f].FieldType.IsEnum)
                {
                    strb.Append("\"+POut.Int   ((int)" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                }
                else
                {
                    Name __dummyScrutVar3 = fieldsExceptPri[f].FieldType.Name;
                    if (__dummyScrutVar3.equals("Boolean"))
                    {
                        strb.Append("\"+POut.Bool(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                    }
                    else if (__dummyScrutVar3.equals("Bitmap"))
                    {
                        strb.Append("\"+POut.Bitmap(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                    }
                    else if (__dummyScrutVar3.equals("Byte"))
                    {
                        strb.Append("\"+POut.Byte(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                    }
                    else if (__dummyScrutVar3.equals("Color"))
                    {
                        strb.Append("\"+POut.Int(" + obj + "." + fieldsExceptPri[f].Name + ".ToArgb())+\"");
                    }
                    else if (__dummyScrutVar3.equals("DateTime"))
                    {
                        //This is only for date, not dateT.
                        strb.Append("\"+POut.Date(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                    }
                    else if (__dummyScrutVar3.equals("Double"))
                    {
                        strb.Append("'\"+POut.Double(" + obj + "." + fieldsExceptPri[f].Name + ")+\"'");
                    }
                    else if (__dummyScrutVar3.equals("Interval"))
                    {
                        strb.Append("\"+POut.Int(" + obj + "." + fieldsExceptPri[f].Name + ".ToInt())+\"");
                    }
                    else if (__dummyScrutVar3.equals("Int64"))
                    {
                        strb.Append("\"+POut.Long(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                    }
                    else if (__dummyScrutVar3.equals("Int32"))
                    {
                        strb.Append("\"+POut.Int(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                    }
                    else if (__dummyScrutVar3.equals("Single"))
                    {
                        strb.Append("\"+POut.Float(" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                    }
                    else if (__dummyScrutVar3.equals("String"))
                    {
                        strb.Append("'\"+POut.String(" + obj + "." + fieldsExceptPri[f].Name + ")+\"'");
                    }
                    else if (__dummyScrutVar3.equals("TimeSpan"))
                    {
                        strb.Append("\"+POut.Time  (" + obj + "." + fieldsExceptPri[f].Name + ")+\"");
                    }
                    else
                    {
                        throw new ApplicationException("Type not yet supported: " + fieldsExceptPri[f].FieldType.Name);
                    }            
                }       
                strb.Append("\";");
                strb.Append(rn + t3 + "}");
            }
            strb.Append(rn + t3 + "if(command==\"\"){");
            strb.Append(rn + t4 + "return;");
            strb.Append(rn + t3 + "}");
            for (int i = 0;i < paramList.Count;i++)
            {
                strb.Append(rn + t3 + "if(" + obj + "." + paramList[i].ParameterName + "==null) {");
                strb.Append(rn + t4 + "" + obj + "." + paramList[i].ParameterName + "=\"\";");
                strb.Append(rn + t3 + "}");
                strb.Append(rn + t3 + "OdSqlParameter param" + paramList[i].ParameterName + "=new OdSqlParameter(\"param" + paramList[i].ParameterName + "\"," + "OdDbType.Text," + obj + "." + paramList[i].ParameterName + ");");
            }
            strb.Append(rn + t3 + "command=\"UPDATE " + tablename + " SET \"+command");
            strb.Append(rn + t4 + "+\" WHERE " + priKey.Name + " = \"+POut.Long(" + obj + "." + priKey.Name + ");");
            strb.Append(rn + t3 + "Db.NonQ(command" + paramsString + ");");
            strb.Append(rn + t2 + "}");
        }
         
        //Delete---------------------------------------------------------------------------------------------
        if (CrudGenHelper.isDeleteForbidden(typeClass))
        {
            strb.Append(rn + rn + t2 + "//Delete not allowed for this table");
            strb.Append(rn + t2 + "//public static void Delete(long " + priKeyParam + "){");
            strb.Append(rn + t2 + "//");
            strb.Append(rn + t2 + "//}");
        }
        else
        {
            strb.Append(rn + rn + t2 + "///<summary>Deletes one " + typeClass.Name + " from the database.</summary>");
            if (isMobile)
            {
                strb.Append(rn + t2 + "public static void Delete(long " + priKeyParam1 + ",long " + priKeyParam2 + "){");
                strb.Append(rn + t3 + "string command=\"DELETE FROM " + tablename + " \"");
                strb.Append(rn + t4 + "+\"WHERE " + priKey1.Name + " = \"+POut.Long(" + priKeyParam1 + ")+\" AND " + priKey2.Name + " = \"+POut.Long(" + priKeyParam2 + ");");
            }
            else
            {
                strb.Append(rn + t2 + "public static void Delete(long " + priKeyParam + "){");
                strb.Append(rn + t3 + "string command=\"DELETE FROM " + tablename + " \"");
                strb.Append(rn + t4 + "+\"WHERE " + priKey.Name + " = \"+POut.Long(" + priKeyParam + ");");
            } 
            strb.Append(rn + t3 + "Db.NonQ(command);");
            strb.Append(rn + t2 + "}");
        } 
        if (isMobile)
        {
            //ConvertToM------------------------------------------------------------------------------------------
            Type typeClassReg = CrudGenHelper.GetTypeFromMType(typeClass.Name, tableTypes);
            //gets the non-mobile type
            if (typeClassReg == null)
            {
                strb.Append(rn + rn + t2 + "//ConvertToM not applicable.");
            }
            else
            {
                String tablenameReg = CrudGenHelper.getTableName(typeClassReg);
                //in lowercase now.
                String objReg = typeClassReg.Name.Substring(0, 1).ToLower() + typeClassReg.Name.Substring(1);
                //lowercase initial letter.  Example feeSched
                FieldInfo[] fieldsReg = typeClassReg.GetFields();
                //We can't assume they are in the correct order.
                List<FieldInfo> fieldsInDbReg = CrudGenHelper.GetFieldsExceptNotDb(fieldsReg);
                strb.Append(rn + rn + t2 + "///<summary>Converts one " + typeClassReg.Name + " object to its mobile equivalent.  Warning! CustomerNum will always be 0.</summary>");
                strb.Append(rn + t2 + "public static " + typeClass.Name + " ConvertToM(" + typeClassReg.Name + " " + objReg + "){");
                strb.Append(rn + t3 + typeClass.Name + " " + obj + "=new " + typeClass.Name + "();");
                for (int f = 0;f < fieldsInDb.Count;f++)
                {
                    if (StringSupport.equals(fieldsInDb[f].Name, "CustomerNum"))
                    {
                        strb.Append(rn + t3 + "//CustomerNum cannot be set.  Remains 0.");
                        continue;
                    }
                     
                    boolean matchfound = false;
                    for (int r = 0;r < fieldsInDbReg.Count;r++)
                    {
                        if (fieldsInDb[f].Name == fieldsInDbReg[r].Name)
                        {
                            strb.Append(rn + t3 + obj + "." + fieldsInDb[f].Name.PadRight(longestField, ' ') + "=" + objReg + "." + fieldsInDbReg[r].Name + ";");
                            matchfound = true;
                        }
                         
                    }
                    if (!matchfound)
                    {
                        throw new ApplicationException("Match not found.");
                    }
                     
                }
                strb.Append(rn + t3 + "return " + obj + ";");
                strb.Append(rn + t2 + "}");
            } 
        }
         
        //Footer
        strb.Append(rn);
        strb.Append("\r\n" + 
        "\t}\r\n" + 
        "}");
    }

    private void butSnippet_Click(Object sender, EventArgs e) throws Exception {
        if (listClass.SelectedIndex == -1)
        {
            MessageBox.Show("Please select a class.");
            return ;
        }
         
        //if(comboType.SelectedIndex==-1){
        //	MessageBox.Show("Please select a type.");
        //	return;
        //}
        Type typeClass = tableTypesAll[listClass.SelectedIndex];
        SnippetType snipType = (SnippetType)comboType.SelectedIndex;
        boolean isMobile = CrudGenHelper.isMobile(typeClass);
        String snippet = CrudGenDataInterface.getSnippet(typeClass,snipType,isMobile);
        textSnippet.Text = snippet;
        Clipboard.SetText(snippet);
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(Form1.class);
        this.label1 = new System.Windows.Forms.Label();
        this.butRun = new System.Windows.Forms.Button();
        this.textDb = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.comboType = new System.Windows.Forms.ComboBox();
        this.butSnippet = new System.Windows.Forms.Button();
        this.textSnippet = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.listClass = new System.Windows.Forms.ListBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textDbM = new System.Windows.Forms.TextBox();
        this.checkRun = new System.Windows.Forms.CheckBox();
        this.checkRunM = new System.Windows.Forms.CheckBox();
        this.label8 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.checkRunSchema = new System.Windows.Forms.CheckBox();
        this.label10 = new System.Windows.Forms.Label();
        this.label11 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(406, 11);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(421, 54);
        this.label1.TabIndex = 0;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // butRun
        //
        this.butRun.Location = new System.Drawing.Point(868, 14);
        this.butRun.Name = "butRun";
        this.butRun.Size = new System.Drawing.Size(75, 23);
        this.butRun.TabIndex = 1;
        this.butRun.Text = "Run";
        this.butRun.UseVisualStyleBackColor = true;
        this.butRun.Click += new System.EventHandler(this.butRun_Click);
        //
        // textDb
        //
        this.textDb.Location = new System.Drawing.Point(105, 24);
        this.textDb.Name = "textDb";
        this.textDb.Size = new System.Drawing.Size(127, 20);
        this.textDb.TabIndex = 5;
        this.textDb.Text = "development133";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(17, 25);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(87, 17);
        this.label3.TabIndex = 7;
        this.label3.Text = "Database";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label2.Location = new System.Drawing.Point(160, 104);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(163, 17);
        this.label2.TabIndex = 9;
        this.label2.Text = "Snippet Generator";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(9, 227);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(125, 17);
        this.label4.TabIndex = 10;
        this.label4.Text = "Class";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(9, 182);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(125, 17);
        this.label5.TabIndex = 12;
        this.label5.Text = "Type";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // comboType
        //
        this.comboType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboType.FormattingEnabled = true;
        this.comboType.Location = new System.Drawing.Point(11, 202);
        this.comboType.MaxDropDownItems = 100;
        this.comboType.Name = "comboType";
        this.comboType.Size = new System.Drawing.Size(144, 21);
        this.comboType.TabIndex = 11;
        //
        // butSnippet
        //
        this.butSnippet.Location = new System.Drawing.Point(59, 156);
        this.butSnippet.Name = "butSnippet";
        this.butSnippet.Size = new System.Drawing.Size(96, 23);
        this.butSnippet.TabIndex = 13;
        this.butSnippet.Text = "Create Snippet";
        this.butSnippet.UseVisualStyleBackColor = true;
        this.butSnippet.Click += new System.EventHandler(this.butSnippet_Click);
        //
        // textSnippet
        //
        this.textSnippet.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textSnippet.Location = new System.Drawing.Point(161, 124);
        this.textSnippet.Multiline = true;
        this.textSnippet.Name = "textSnippet";
        this.textSnippet.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        this.textSnippet.Size = new System.Drawing.Size(988, 529);
        this.textSnippet.TabIndex = 14;
        this.textSnippet.WordWrap = false;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(1, 106);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(157, 43);
        this.label6.TabIndex = 15;
        this.label6.Text = "A copy of the snippet will automatically be placed on the clipboard";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listClass
        //
        this.listClass.FormattingEnabled = true;
        this.listClass.Location = new System.Drawing.Point(11, 247);
        this.listClass.Name = "listClass";
        this.listClass.Size = new System.Drawing.Size(144, 407);
        this.listClass.TabIndex = 16;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(-1, 49);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(105, 17);
        this.label7.TabIndex = 18;
        this.label7.Text = "Mobile on .196";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDbM
        //
        this.textDbM.Location = new System.Drawing.Point(105, 48);
        this.textDbM.Name = "textDbM";
        this.textDbM.Size = new System.Drawing.Size(127, 20);
        this.textDbM.TabIndex = 17;
        this.textDbM.Text = "mobile_dev";
        //
        // checkRun
        //
        this.checkRun.Checked = true;
        this.checkRun.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkRun.Location = new System.Drawing.Point(239, 26);
        this.checkRun.Name = "checkRun";
        this.checkRun.Size = new System.Drawing.Size(39, 17);
        this.checkRun.TabIndex = 19;
        this.checkRun.UseVisualStyleBackColor = true;
        //
        // checkRunM
        //
        this.checkRunM.Enabled = false;
        this.checkRunM.Location = new System.Drawing.Point(239, 51);
        this.checkRunM.Name = "checkRunM";
        this.checkRunM.Size = new System.Drawing.Size(39, 17);
        this.checkRunM.TabIndex = 20;
        this.checkRunM.UseVisualStyleBackColor = true;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(223, 4);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(48, 17);
        this.label8.TabIndex = 21;
        this.label8.Text = "Run";
        this.label8.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(59, 73);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(176, 17);
        this.label9.TabIndex = 22;
        this.label9.Text = "Schema (no db needed)";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkRunSchema
        //
        this.checkRunSchema.Location = new System.Drawing.Point(239, 74);
        this.checkRunSchema.Name = "checkRunSchema";
        this.checkRunSchema.Size = new System.Drawing.Size(39, 17);
        this.checkRunSchema.TabIndex = 23;
        this.checkRunSchema.UseVisualStyleBackColor = true;
        //
        // label10
        //
        this.label10.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label10.Location = new System.Drawing.Point(406, 73);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(421, 33);
        this.label10.TabIndex = 24;
        this.label10.Text = "This CrudGenerator project MUST be set to compile each time it\'s run even if chan" + "ges were only to OpenDentBusiness.";
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(255, 50);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(145, 17);
        this.label11.TabIndex = 25;
        this.label11.Text = "see DiseasemCrud, L114";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // Form1
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(1161, 910);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.checkRunSchema);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.checkRunM);
        this.Controls.Add(this.checkRun);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.textDbM);
        this.Controls.Add(this.listClass);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textSnippet);
        this.Controls.Add(this.butSnippet);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.comboType);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textDb);
        this.Controls.Add(this.butRun);
        this.Controls.Add(this.label1);
        this.Name = "Form1";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Crud Generator";
        this.Load += new System.EventHandler(this.Form1_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butRun = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textDb = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Button butSnippet = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textSnippet = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listClass = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDbM = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkRun = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkRunM = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkRunSchema = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
}


