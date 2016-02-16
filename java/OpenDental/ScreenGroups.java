//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package OpenDental;

import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.ScreenGroup;
import OpenDentBusiness.MiscData;

/*=========================================================================================
		=================================== class ScreenGroups ===========================================*/
/**
* 
*/
public class ScreenGroups   
{
    /**
    * 
    */
    public static ScreenGroup Cur = new ScreenGroup();
    /**
    * 
    */
    public static ScreenGroup[] List = new ScreenGroup[]();
    /**
    * 
    */
    public static void refresh(DateTime fromDate, DateTime toDate) throws Exception {
        String command = "SELECT * from screengroup " + "WHERE SGDate >= '" + POut.pDateT(fromDate) + "' " + "&& SGDate <= '" + POut.PDateT(toDate.AddDays(1)) + "' " + "ORDER BY SGDate,ScreenGroupNum";
        //added one day since it's calculated based on midnight.
        DataTable table = General.GetTable(command);
        List = new ScreenGroup[table.Rows.Count];
        for (int i = 0;i < List.Length;i++)
        {
            List[i].ScreenGroupNum = PIn.PInt(table.Rows[i][0].ToString());
            List[i].Description = PIn.PString(table.Rows[i][1].ToString());
            List[i].SGDate = PIn.PDate(table.Rows[i][2].ToString());
        }
    }

    /**
    * 
    */
    public static void insertCur() throws Exception {
        if (PrefB.RandomKeys)
        {
            Cur.ScreenGroupNum = MiscData.GetKey("screengroup", "ScreenGroupNum");
        }
         
        String command = "INSERT INTO screengroup (";
        if (PrefB.RandomKeys)
        {
            command += "ScreenGroupNum,";
        }
         
        command += "Description,SGDate) VALUES(";
        if (PrefB.RandomKeys)
        {
            command += "'" + POut.pInt(Cur.ScreenGroupNum) + "', ";
        }
         
        command += "'" + POut.pString(Cur.Description) + "', " + "'" + POut.pDate(Cur.SGDate) + "')";
        if (PrefB.RandomKeys)
        {
            General.NonQ(command);
        }
        else
        {
            Cur.ScreenGroupNum = General.NonQ(command, true);
        } 
    }

    /**
    * 
    */
    public static void updateCur() throws Exception {
        String command = "UPDATE screengroup SET " + "Description ='" + POut.pString(Cur.Description) + "'" + ",SGDate ='" + POut.pDate(Cur.SGDate) + "'" + " WHERE ScreenGroupNum = '" + POut.pInt(Cur.ScreenGroupNum) + "'";
        General.NonQ(command);
    }

    /**
    * This will also delete all screen items, so may need to ask user first.
    */
    public static void deleteCur() throws Exception {
        String command = "DELETE from screen WHERE ScreenGroupNum ='" + POut.pInt(Cur.ScreenGroupNum) + "'";
        General.NonQ(command);
        command = "DELETE from screengroup WHERE ScreenGroupNum ='" + POut.pInt(Cur.ScreenGroupNum) + "'";
        General.NonQ(command);
    }

}


