//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package OpenDental;

import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.School;

/*=========================================================================================
		=================================== class Schools ===========================================*/
/**
* 
*/
public class Schools   
{
    /**
    * 
    */
    public static School Cur = new School();
    /**
    * This list is only refreshed as needed rather than being part of the local data.
    */
    public static School[] List = new School[]();
    /**
    * Used in screening window. Simpler interface.
    */
    public static String[] ListNames = new String[]();
    /**
    * Refreshes List as needed directly from the database.  List only includes items that will show in dropdown list.
    */
    public static void refresh(String name) throws Exception {
        String command = "SELECT * from school " + "WHERE SchoolName LIKE '" + name + "%' " + "ORDER BY SchoolName";
        DataTable table = General.GetTable(command);
        List = new School[table.Rows.Count];
        for (int i = 0;i < List.Length;i++)
        {
            List[i].SchoolName = PIn.PString(table.Rows[i][0].ToString());
            List[i].SchoolCode = PIn.PString(table.Rows[i][1].ToString());
            List[i].OldSchoolName = PIn.PString(table.Rows[i][0].ToString());
        }
    }

    /**
    * 
    */
    public static void refresh() throws Exception {
        refresh("");
    }

    /**
    * Gets an array of strings containing all the schools in alphabetical order.  Used for the screening interface which must be simpler than the usual interface.
    */
    public static void getListNames() throws Exception {
        String command = "SELECT SchoolName from school " + "ORDER BY SchoolName";
        DataTable table = General.GetTable(command);
        ListNames = new String[table.Rows.Count];
        for (int i = 0;i < ListNames.Length;i++)
        {
            ListNames[i] = PIn.PString(table.Rows[i][0].ToString());
        }
    }

    /**
    * Need to make sure schoolname not already in db.
    */
    public static void insertCur() throws Exception {
        String command = "INSERT INTO school (SchoolName,SchoolCode) " + "VALUES (" + "'" + POut.pString(Cur.SchoolName) + "', " + "'" + POut.pString(Cur.SchoolCode) + "')";
        //MessageBox.Show(command);
        General.NonQ(command);
    }

    /**
    * Updates the schoolname and code in the school table, and also updates all patients that were using the oldschool name.
    */
    public static void updateCur() throws Exception {
        String command = "UPDATE school SET " + "SchoolName ='" + POut.pString(Cur.SchoolName) + "'" + ",SchoolCode ='" + POut.pString(Cur.SchoolCode) + "'" + " WHERE SchoolName = '" + POut.pString(Cur.OldSchoolName) + "'";
        General.NonQ(command);
        //then, update all patients using that school
        command = "UPDATE patient SET " + "GradeSchool ='" + POut.pString(Cur.SchoolName) + "'" + " WHERE GradeSchool = '" + POut.pString(Cur.OldSchoolName) + "'";
        General.NonQ(command);
    }

    /**
    * Must run UsedBy before running this.
    */
    public static void deleteCur() throws Exception {
        String command = "DELETE from school WHERE SchoolName = '" + POut.pString(Cur.SchoolName) + "'";
        General.NonQ(command);
    }

    /**
    * Use before DeleteCur to determine if this school name is in use. Returns a formatted string that can be used to quickly display the names of all patients using the schoolname.
    */
    public static String usedBy(String schoolName) throws Exception {
        String command = "SELECT LName,FName from patient " + "WHERE GradeSchool = '" + POut.pString(schoolName) + "' ";
        DataTable table = General.GetTable(command);
        if (table.Rows.Count == 0)
            return "";
         
        String retVal = "";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal += PIn.PString(table.Rows[i][0].ToString()) + ", " + PIn.PString(table.Rows[i][1].ToString());
            if (i < table.Rows.Count - 1)
            {
                //if not the last row
                retVal += "\r";
            }
             
        }
        return retVal;
    }

    /**
    * Use before InsertCur to determine if this school name already exists. Also used when closing patient edit window to validate that the schoolname exists.
    */
    public static boolean doesExist(String schoolName) throws Exception {
        String command = "SELECT * from school " + "WHERE SchoolName = '" + POut.pString(schoolName) + "' ";
        DataTable table = General.GetTable(command);
        if (table.Rows.Count == 0)
            return false;
        else
            return true; 
    }

}


