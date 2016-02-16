//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.POut;

public class InnoDb   
{
    /**
    * Returns the default storage engine.
    */
    public static String getDefaultEngine() throws Exception {
        String command = "SELECT @@default_storage_engine";
        String defaultengine = Db.getScalar(command).ToString();
        return defaultengine;
    }

    public static boolean isInnodbAvail() throws Exception {
        String command = "SELECT @@have_innodb";
        String innoDbOn = Db.getScalar(command).ToString();
        return StringSupport.equals(innoDbOn, "YES");
    }

    /**
    * Returns the number of MyISAM tables and the number of InnoDB tables in the current database.
    */
    public static String getEngineCount() throws Exception {
        String command = "SELECT SUM(CASE WHEN information_schema.tables.engine=\'MyISAM\' THEN 1 ELSE 0 END) AS \'myisam\',\r\n" + 
        "\t\t\t\tSUM(CASE WHEN information_schema.tables.engine=\'InnoDB\' THEN 1 ELSE 0 END) AS \'innodb\'\r\n" + 
        "\t\t\t\tFROM information_schema.tables\r\n" + 
        "\t\t\t\tWHERE table_schema=(SELECT DATABASE())";
        DataTable results = Db.getTable(command);
        String retval = Lans.g("FormInnoDb","Number of MyISAM tables: ");
        retval += Lans.g("FormInnoDb", results.Rows[0]["myisam"].ToString()) + "\r\n";
        retval += Lans.g("FormInnoDb","Number of InnoDB tables: ");
        retval += Lans.g("FormInnoDb", results.Rows[0]["innodb"].ToString()) + "\r\n";
        return retval;
    }

    /**
    * The only allowed parameters are "InnoDB" or "MyISAM".  Converts tables to toEngine type and returns the number of tables converted.
    */
    public static int convertTables(String fromEngine, String toEngine) throws Exception {
        int numtables = 0;
        String command = "SELECT DATABASE()";
        String database = Db.getScalar(command);
        command = "SELECT table_name\r\n" + 
        "\t\t\t\tFROM information_schema.tables\r\n" + 
        "\t\t\t\tWHERE table_schema=\'" + database + "' AND information_schema.tables.engine='" + fromEngine + "'";
        DataTable results = Db.getTable(command);
        command = "";
        if (results.Rows.Count == 0)
        {
            return numtables;
        }
         
        for (int i = 0;i < results.Rows.Count;i++)
        {
            command += "ALTER TABLE " + database + "." + POut.String(results.Rows[i]["table_name"].ToString()) + " ENGINE='" + toEngine + "'; ";
            numtables++;
        }
        Db.nonQ(command);
        return numtables;
    }

}


