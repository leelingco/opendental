//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package OpenDental;

import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.UserQuery;

/*=========================================================================================
		=================================== class UserQueries ==========================================*/
/**
* 
*/
public class UserQueries   
{
    /**
    * 
    */
    public static UserQuery[] List = new UserQuery[]();
    /**
    * 
    */
    public static UserQuery Cur = new UserQuery();
    /**
    * 
    */
    public static boolean IsSelected = new boolean();
    /**
    * 
    */
    public static void refresh() throws Exception {
        String command = "SELECT querynum,description,filename,querytext" + " FROM userquery" + " ORDER BY description";
        //+" WHERE hidden != '1'";
        DataTable table = General.GetTable(command);
        List = new UserQuery[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            List[i].QueryNum = PIn.PInt(table.Rows[i][0].ToString());
            List[i].Description = PIn.PString(table.Rows[i][1].ToString());
            List[i].FileName = PIn.PString(table.Rows[i][2].ToString());
            List[i].QueryText = PIn.PString(table.Rows[i][3].ToString());
        }
    }

    /**
    * 
    */
    public static void insertCur() throws Exception {
        String command = "INSERT INTO userquery (description,filename,querytext) VALUES(" + "'" + POut.pString(Cur.Description) + "', " + "'" + POut.pString(Cur.FileName) + "', " + "'" + POut.pString(Cur.QueryText) + "')";
        //MessageBox.Show(command);
        General.NonQ(command);
    }

    /**
    * 
    */
    public static void deleteCur() throws Exception {
        String command = "DELETE from userquery WHERE querynum = '" + POut.pInt(Cur.QueryNum) + "'";
        General.NonQ(command);
    }

    /**
    * 
    */
    public static void updateCur() throws Exception {
        String command = "UPDATE userquery SET " + "description = '" + POut.pString(Cur.Description) + "'" + ",filename = '" + POut.pString(Cur.FileName) + "'" + ",querytext = '" + POut.pString(Cur.QueryText) + "'" + " WHERE querynum = '" + POut.pInt(Cur.QueryNum) + "'";
        General.NonQ(command);
    }

}


