//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package xCrudGenerator;

import OpenDentBusiness.DbSchemaCol;
import OpenDentBusiness.OdDbType;
import OpenDentBusiness.TextSizeMySqlOracle;
import xCrudGenerator.CrudGenHelper;
import xCrudGenerator.CrudQueries;
import xCrudGenerator.CrudSchemaRaw;
import xCrudGenerator.SchemaTable;

/**
* See UnitTests.SchemaT.cs.  This class generates the SchemaCrudTest file.
*/
public class CrudSchemaForUnitTest   
{
    public static String create() throws Exception {
        StringBuilder strb = new StringBuilder();
        //This is a stub that is to be replaced with some good code generation:
        strb.Append("using System;\r\n" + 
        "using System.Collections.Generic;\r\n" + 
        "using System.Text;\r\n" + 
        "\r\n" + 
        "namespace OpenDentBusiness {\r\n" + 
        "\t///<summary>Please ignore this class.  It\'s used only for testing.</summary>\r\n" + 
        "\tpublic class SchemaCrudTest {\r\n" + 
        "\t\t///<summary>Example only</summary>\r\n" + 
        "\t\tpublic static void AddTableTempcore() {\r\n" + 
        "\t\t\tstring command=\"\";");
        Type typeClass = SchemaTable.class;
        FieldInfo[] fields = typeClass.GetFields();
        FieldInfo priKey = CrudGenHelper.GetPriKey(fields, typeClass.Name);
        List<FieldInfo> fieldsExceptPri = CrudGenHelper.GetFieldsExceptPriKey(fields, priKey);
        List<DbSchemaCol> cols = CrudQueries.GetListColumns(priKey.Name, null, fieldsExceptPri, false);
        strb.Append("\r\n" + CrudSchemaRaw.AddTable("tempcore", cols, 3, false));
        strb.Append("\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t///<summary>Example only</summary>\r\n" + 
        "\t\tpublic static void AddColumnEndClob() {\r\n" + 
        "\t\t\tstring command=\"\";");
        DbSchemaCol col = new DbSchemaCol("ColEndClob",OdDbType.Text,TextSizeMySqlOracle.Medium);
        strb.Append("\r\n" + CrudSchemaRaw.addColumnEnd("tempcore",col,3));
        strb.Append("\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t///<summary>Example only</summary>\r\n" + 
        "\t\tpublic static void AddColumnEndInt() {\r\n" + 
        "\t\t\tstring command=\"\";");
        col = new DbSchemaCol("ColEndInt",OdDbType.Int);
        strb.Append("\r\n" + CrudSchemaRaw.addColumnEnd("tempcore",col,3));
        strb.Append("\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t///<summary>Example only</summary>\r\n" + 
        "\t\tpublic static void AddColumnEndTimeStamp() {\r\n" + 
        "\t\t\tstring command=\"\";");
        col = new DbSchemaCol("ColEndTimeStamp",OdDbType.DateTimeStamp);
        strb.Append("\r\n" + CrudSchemaRaw.addColumnEnd("tempcore",col,3));
        strb.Append("\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t///<summary>Example only</summary>\r\n" + 
        "\t\tpublic static void AddIndex() {\r\n" + 
        "\t\t\tstring command=\"\";");
        strb.Append("\r\n" + CrudSchemaRaw.addIndex("tempcore","ColEndInt",3));
        strb.Append("\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t///<summary>Example only</summary>\r\n" + 
        "\t\tpublic static void DropColumn() {\r\n" + 
        "\t\t\tstring command=\"\";");
        strb.Append("\r\n" + CrudSchemaRaw.dropColumn("tempcore","TextLargeTest",3));
        strb.Append("\r\n" + 
        "\t\t}\r\n" + 
        "\r\n" + 
        "\t\t//AddColumnAfter\r\n" + 
        "\t\t//DropColumnTimeStamp\r\n" + 
        "\t\t//DropIndex\r\n" + 
        "\t\t//etc.\r\n" + 
        "\r\n" + 
        "\r\n" + 
        "\t}\r\n" + 
        "}");
        return strb.ToString();
    }

}


