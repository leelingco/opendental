//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package xCrudGenerator;

import CS2JNet.System.StringSupport;
import xCrudGenerator.CrudGenHelper;
import xCrudGenerator.SnippetType;

public class CrudGenDataInterface   
{
    private static final String rn = "\r\n";
    private static final String t = "\t";
    private static final String t2 = "\t\t";
    private static final String t3 = "\t\t\t";
    private static final String t4 = "\t\t\t\t";
    private static final String t5 = "\t\t\t\t\t";
    public static String getSnippet(Type typeClass, SnippetType snipType, boolean isMobile) throws Exception {
        //bool isMobile=CrudGenHelper.IsMobile(typeClass);
        String Sname = GetSname(typeClass.Name);
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
        String tablename = CrudGenHelper.getTableName(typeClass);
        //in lowercase now.  Example feesched
        List<FieldInfo> fieldsExceptPri = null;
        if (isMobile)
        {
            fieldsExceptPri = CrudGenHelper.GetFieldsExceptPriKey(fields, priKey2);
        }
        else
        {
            //for mobile, only excludes PK2
            fieldsExceptPri = CrudGenHelper.GetFieldsExceptPriKey(fields, priKey);
        } 
        switch(snipType)
        {
            default: 
                return "snippet type not found.";
            case Insert: 
                if (isMobile)
                {
                    return GetInsert(typeClass.Name, obj, null, true);
                }
                else
                {
                    return GetInsert(typeClass.Name, obj, priKey.Name, false);
                } 
            case Update: 
                return GetUpdate(typeClass.Name, obj, isMobile);
            case EntireSclass: 
                if (isMobile)
                {
                    return GetEntireSclassMobile(typeClass.Name, obj, priKey1.Name, priKey2.Name, Sname, tablename, priKeyParam1, priKeyParam2);
                }
                else
                {
                    return GetEntireSclass(typeClass.Name, obj, priKey.Name, Sname, tablename, priKeyParam);
                } 
            case CreateTable: 
                return "Currently unavailable.";
        
        }
    }

    /*
    					if(isMobile) {
    						return GetCreateTable(tablename,priKey1.Name,priKey2.Name,fieldsExceptPri);
    					}
    					else {
    						return GetCreateTable(tablename,priKey.Name,null,fieldsExceptPri);
    					}*/
    private static String getSname(String typeClassName) throws Exception {
        String Sname = typeClassName;
        if (StringSupport.equals(typeClassName, "Etrans"))
        {
            return "Etranss";
        }
         
        if (StringSupport.equals(typeClassName, "SecurityLogHash"))
        {
            return "SecurityLogHashes";
        }
         
        //if(typeClassName=="RegistrationKey") {
        //	return "RegistrationKeys";
        //}
        if (StringSupport.equals(typeClassName, "Language"))
        {
            return "Lans";
        }
         
        if (Sname.EndsWith("s"))
        {
            Sname = Sname + "es";
        }
        else if (Sname.EndsWith("ch"))
        {
            Sname = Sname + "es";
        }
        else if (Sname.EndsWith("ay"))
        {
            Sname = Sname + "s";
        }
        else if (Sname.EndsWith("ey"))
        {
            //eg key
            Sname = Sname + "s";
        }
        else if (Sname.EndsWith("y"))
        {
            Sname = Sname.TrimEnd('y') + "ies";
        }
        else
        {
            Sname = Sname + "s";
        }     
        return Sname;
    }

    /**
    * Creates the Data Interface "s" classes for new tables, complete with typical stubs.  Asks user first.
    */
    public static void create(String convertDbFile, Type typeClass, String dbName, boolean isMobile) throws Exception {
        String Sname = GetSname(typeClass.Name);
        String fileName = null;
        if (isMobile)
        {
            fileName = "..\\..\\..\\OpenDentBusiness\\Mobile\\Data Interface\\" + Sname + ".cs";
        }
        else
        {
            fileName = "..\\..\\..\\OpenDentBusiness\\Data Interface\\" + Sname + ".cs";
        } 
        if (File.Exists(fileName))
        {
            return ;
        }
         
        if (CrudGenHelper.isMissingInGeneral(typeClass))
        {
            return ;
        }
         
        if (MessageBox.Show("Create stub for " + fileName + "?", "", MessageBoxButtons.YesNo) != DialogResult.Yes)
        {
            return ;
        }
         
        String snippet = getSnippet(typeClass,SnippetType.EntireSclass,isMobile);
        File.WriteAllText(fileName, snippet);
        //Process.Start(fileName);
        MessageBox.Show(fileName + " has been created.  Be sure to add it to the project and to SVN");
    }

    /**
    * priKeyName will be null if mobile.
    */
    private static String getInsert(String typeClassName, String obj, String priKeyName, boolean isMobile) throws Exception {
        String retVal = null;
        if (isMobile)
        {
            retVal = "\t\t///<summary></summary>\r\n" + 
            "\t\tpublic static long Insert(" + typeClassName + " " + obj + "){\r\n" + 
            "\t\t\treturn Crud." + typeClassName + "Crud.Insert(" + obj + ",true);\r\n" + 
            "\t\t}";
        }
        else
        {
            retVal = "\t\t///<summary></summary>\r\n" + 
            "\t\tpublic static long Insert(" + typeClassName + " " + obj + "){\r\n" + 
            "\t\t\tif(RemotingClient.RemotingRole==RemotingRole.ClientWeb){\r\n" + 
            "\t\t\t\t" + obj + "." + priKeyName + "=Meth.GetLong(MethodBase.GetCurrentMethod()," + obj + ");\r\n" + 
            "\t\t\t\treturn " + obj + "." + priKeyName + ";\r\n" + 
            "\t\t\t}\r\n" + 
            "\t\t\treturn Crud." + typeClassName + "Crud.Insert(" + obj + ");\r\n" + 
            "\t\t}";
        } 
        return retVal;
    }

    private static String getUpdate(String typeClassName, String obj, boolean isMobile) throws Exception {
        String retVal = null;
        if (isMobile)
        {
            retVal = "\t\t///<summary></summary>\r\n" + 
            "\t\tpublic static void Update(" + typeClassName + " " + obj + "){\r\n" + 
            "\t\t\tCrud." + typeClassName + "Crud.Update(" + obj + ");\r\n" + 
            "\t\t}";
        }
        else
        {
            retVal = "\t\t///<summary></summary>\r\n" + 
            "\t\tpublic static void Update(" + typeClassName + " " + obj + "){\r\n" + 
            "\t\t\tif(RemotingClient.RemotingRole==RemotingRole.ClientWeb){\r\n" + 
            "\t\t\t\tMeth.GetVoid(MethodBase.GetCurrentMethod()," + obj + ");\r\n" + 
            "\t\t\t\treturn;\r\n" + 
            "\t\t\t}\r\n" + 
            "\t\t\tCrud." + typeClassName + "Crud.Update(" + obj + ");\r\n" + 
            "\t\t}";
        } 
        return retVal;
    }

    private static String getEntireSclass(String typeClassName, String obj, String priKeyName, String Sname, String tablename, String priKeyParam) throws Exception {
        String str = "using System;\r\n" + 
        "using System.Collections.Generic;\r\n" + 
        "using System.Data;\r\n" + 
        "using System.Reflection;\r\n" + 
        "using System.Text;\r\n" + 
        "\r\n" + 
        "namespace OpenDentBusiness{\r\n" + 
        "\t///<summary></summary>\r\n" + 
        "\tpublic class " + Sname + "{\r\n" + 
        "\t\t//If this table type will exist as cached data, uncomment the CachePattern region below and edit.\r\n" + 
        "\t\t/*\r\n" + 
        "\t\t#region CachePattern\r\n" + 
        "\t\t//This region can be eliminated if this is not a table type with cached data.\r\n" + 
        "\t\t//If leaving this region in place, be sure to add RefreshCache and FillCache \r\n" + 
        "\t\t//to the Cache.cs file with all the other Cache types.\r\n" + 
        "\r\n" + 
        "\t\t///<summary>A list of all " + Sname + ".</summary>\r\n" + 
        "\t\tprivate static List<" + typeClassName + "> listt;\r\n" + 
        "\r\n" + 
        "\t\t///<summary>A list of all " + Sname + ".</summary>\r\n" + 
        "\t\tpublic static List<" + typeClassName + "> Listt{\r\n" + 
        "\t\t\tget {\r\n" + 
        "\t\t\t\tif(listt==null) {\r\n" + 
        "\t\t\t\t\tRefreshCache();\r\n" + 
        "\t\t\t\t}\r\n" + 
        "\t\t\t\treturn listt;\r\n" + 
        "\t\t\t}\r\n" + 
        "\t\t\tset {\r\n" + 
        "\t\t\t\tlistt=value;\r\n" + 
        "\t\t\t}\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t///<summary></summary>\r\n" + 
        "\t\tpublic static DataTable RefreshCache(){\r\n" + 
        "\t\t\t//No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().\r\n" + 
        "\t\t\tstring command=\"SELECT * FROM " + tablename + " ORDER BY ItemOrder\";//stub query probably needs to be changed\r\n" + 
        "\t\t\tDataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);\r\n" + 
        "\t\t\ttable.TableName=\"" + typeClassName + "\";\r\n" + 
        "\t\t\tFillCache(table);\r\n" + 
        "\t\t\treturn table;\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t///<summary></summary>\r\n" + 
        "\t\tpublic static void FillCache(DataTable table){\r\n" + 
        "\t\t\t//No need to check RemotingRole; no call to db.\r\n" + 
        "\t\t\tlistt=Crud." + typeClassName + "Crud.TableToList(table);\r\n" + 
        "\t\t}\r\n" + 
        "\t\t#endregion\r\n" + 
        "\t\t*/\r\n" + 
        "\t\t/*\r\n" + 
        "\t\tOnly pull out the methods below as you need them.  Otherwise, leave them commented out.\r\n" + 
        "\r\n" + 
        "\t\t///<summary></summary>\r\n" + 
        "\t\tpublic static List<" + typeClassName + "> Refresh(long patNum){\r\n" + 
        "\t\t\tif(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {\r\n" + 
        "\t\t\t\treturn Meth.GetObject<List<" + typeClassName + ">>(MethodBase.GetCurrentMethod(),patNum);\r\n" + 
        "\t\t\t}\r\n" + 
        "\t\t\tstring command=\"SELECT * FROM " + tablename + " WHERE PatNum = \"+POut.Long(patNum);\r\n" + 
        "\t\t\treturn Crud." + typeClassName + "Crud.SelectMany(command);\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t///<summary>Gets one " + typeClassName + " from the db.</summary>\r\n" + 
        "\t\tpublic static " + typeClassName + " GetOne(long " + priKeyParam + "){\r\n" + 
        "\t\t\tif(RemotingClient.RemotingRole==RemotingRole.ClientWeb){\r\n" + 
        "\t\t\t\treturn Meth.GetObject<" + typeClassName + ">(MethodBase.GetCurrentMethod()," + priKeyParam + ");\r\n" + 
        "\t\t\t}\r\n" + 
        "\t\t\treturn Crud." + typeClassName + "Crud.SelectOne(" + priKeyParam + ");\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + getInsert(typeClassName,obj,priKeyName,false) + "\r\n" + 
        "\r\n" + getUpdate(typeClassName,obj,false) + "\r\n" + 
        "\r\n" + 
        "\t\t///<summary></summary>\r\n" + 
        "\t\tpublic static void Delete(long " + priKeyParam + ") {\r\n" + 
        "\t\t\tif(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {\r\n" + 
        "\t\t\t\tMeth.GetVoid(MethodBase.GetCurrentMethod()," + priKeyParam + ");\r\n" + 
        "\t\t\t\treturn;\r\n" + 
        "\t\t\t}\r\n" + 
        "\t\t\tstring command= \"DELETE FROM " + tablename + " WHERE " + priKeyName + " = \"+POut.Long(" + priKeyParam + ");\r\n" + 
        "\t\t\tDb.NonQ(command);\r\n" + 
        "\t\t}\r\n" + 
        "\t\t*/\r\n" + 
        "\r\n" + 
        "\r\n" + 
        "\r\n" + 
        "\t}\r\n" + 
        "}";
        return str;
    }

    /**
    * priKeyParam1 is CustomerNum for now.
    */
    private static String getEntireSclassMobile(String typeClassName, String obj, String priKeyName1, String priKeyName2, String Sname, String tablename, String priKeyParam1, String priKeyParam2) throws Exception {
        String str = "using System;\r\n" + 
        "using System.Collections.Generic;\r\n" + 
        "using System.Data;\r\n" + 
        "using System.Reflection;\r\n" + 
        "using System.Text;\r\n" + 
        "\r\n" + 
        "namespace OpenDentBusiness.Mobile{\r\n" + 
        "\t///<summary></summary>\r\n" + 
        "\tpublic class " + Sname + "{\r\n" + 
        "\t\t\r\n" + 
        "\t\t/*\r\n" + 
        "\t\tOnly pull out the methods below as you need them.  Otherwise, leave them commented out.\r\n" + 
        "\r\n" + 
        "\t\t///<summary></summary>\r\n" + 
        "\t\tpublic static List<" + typeClassName + "> Refresh(long patNum){\r\n" + 
        "\t\t\tstring command=\"SELECT * FROM " + tablename + " WHERE PatNum = \"+POut.Long(patNum);\r\n" + 
        "\t\t\treturn Crud." + typeClassName + "Crud.SelectMany(command);\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t///<summary>Gets one " + typeClassName + " from the db.</summary>\r\n" + 
        "\t\tpublic static " + typeClassName + " GetOne(long " + priKeyParam1 + ",long " + priKeyParam2 + "){\r\n" + 
        "\t\t\treturn Crud." + typeClassName + "Crud.SelectOne(" + priKeyParam1 + "," + priKeyParam2 + ");\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + getInsert(typeClassName,obj,null,true) + "\r\n" + 
        "\r\n" + getUpdate(typeClassName,obj,true) + "\r\n" + 
        "\r\n" + 
        "\t\t///<summary></summary>\r\n" + 
        "\t\tpublic static void Delete(long " + priKeyParam1 + ",long " + priKeyParam2 + ") {\r\n" + 
        "\t\t\tstring command= \"DELETE FROM " + tablename + " WHERE " + priKeyName1 + " = \"+POut.Long(" + priKeyParam1 + ")+\" AND " + priKeyName2 + " = \"+POut.Long(" + priKeyParam2 + ");\r\n" + 
        "\t\t\tDb.NonQ(command);\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t///<summary>First use GetChangedSince.  Then, use this to convert the list a list of \'m\' objects.</summary>\r\n" + 
        "\t\tpublic static List<" + typeClassName + "> ConvertListToM(List<" + typeClassName.Substring(0, typeClassName.Length - 1) + "> list) {\r\n" + 
        "\t\t\tList<" + typeClassName + "> retVal=new List<" + typeClassName + ">();\r\n" + 
        "\t\t\tfor(int i=0;i<list.Count;i++){\r\n" + 
        "\t\t\t\tretVal.Add(Crud." + typeClassName + "Crud.ConvertToM(list[i]));\r\n" + 
        "\t\t\t}\r\n" + 
        "\t\t\treturn retVal;\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t///<summary>Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().</summary>\r\n" + 
        "\t\tpublic static void UpdateFromChangeList(List<" + typeClassName + "> list,long " + priKeyParam1 + ") {\r\n" + 
        "\t\t\tfor(int i=0;i<list.Count;i++){\r\n" + 
        "\t\t\t\tlist[i].CustomerNum=" + priKeyParam1 + ";\r\n" + 
        "\t\t\t\t" + typeClassName + " " + obj + "=Crud." + typeClassName + "Crud.SelectOne(" + priKeyParam1 + ",list[i]." + priKeyName2 + ");\r\n" + 
        "\t\t\t\tif(" + obj + "==null){//not in db\r\n" + 
        "\t\t\t\t\tCrud." + typeClassName + "Crud.Insert(list[i],true);\r\n" + 
        "\t\t\t\t}\r\n" + 
        "\t\t\t\telse{\r\n" + 
        "\t\t\t\t\tCrud." + typeClassName + "Crud.Update(list[i]);\r\n" + 
        "\t\t\t\t}\r\n" + 
        "\t\t\t}\r\n" + 
        "\t\t}\r\n" + 
        "\t\t*/\r\n" + 
        "\r\n" + 
        "\r\n" + 
        "\r\n" + 
        "\t}\r\n" + 
        "}";
        return str;
    }

}


/*
		///<summary>priKeyName2 will be null if not mobile.</summary>
		private static string GetCreateTable(string tablename,string priKeyName1,string priKeyName2,List<FieldInfo> fieldsExceptPri) {
			StringBuilder strb=new StringBuilder();
			CrudQueries.GetCreateTable(strb,tablename,priKeyName1,priKeyName2,fieldsExceptPri);
			return strb.ToString();
		}*/