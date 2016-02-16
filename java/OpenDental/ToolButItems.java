//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package OpenDental;

import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.ToolButItem;
import OpenDentBusiness.Programs;
import OpenDentBusiness.ToolBarsAvail;

//later include ComputerName.  If blank, then show on all computers.
//also later, include an image.
/*=========================================================================================
		=================================== class ToolButItems ===========================================*/
/**
* 
*/
public class ToolButItems   
{
    /**
    * 
    */
    public static ToolButItem Cur = new ToolButItem();
    /**
    * 
    */
    public static ToolButItem[] List = new ToolButItem[]();
    /**
    * 
    */
    public static ArrayList ForProgram = new ArrayList();
    /**
    * 
    */
    public static void refresh() throws Exception {
        String command = "SELECT * from toolbutitem";
        DataTable table = General.GetTable(command);
        List = new ToolButItem[table.Rows.Count];
        for (int i = 0;i < List.Length;i++)
        {
            List[i].ToolButItemNum = PIn.PInt(table.Rows[i][0].ToString());
            List[i].ProgramNum = PIn.PInt(table.Rows[i][1].ToString());
            List[i].ToolBar = (ToolBarsAvail)PIn.PInt(table.Rows[i][2].ToString());
            List[i].ButtonText = PIn.PString(table.Rows[i][3].ToString());
        }
    }

    /**
    * 
    */
    public static void insertCur() throws Exception {
        String command = "INSERT INTO toolbutitem (ProgramNum,ToolBar,ButtonText) " + "VALUES (" + "'" + POut.pInt(Cur.ProgramNum) + "', " + "'" + POut.pInt(((Enum)Cur.ToolBar).ordinal()) + "', " + "'" + POut.pString(Cur.ButtonText) + "')";
        //MessageBox.Show(command);
        General.NonQ(command);
    }

    //Cur.=InsertID;
    /**
    * This in not currently being used.
    */
    public static void updateCur() throws Exception {
        String command = "UPDATE toolbutitem SET " + "ProgramNum ='" + POut.pInt(Cur.ProgramNum) + "'" + ",ToolBar ='" + POut.pInt(((Enum)Cur.ToolBar).ordinal()) + "'" + ",ButtonText ='" + POut.pString(Cur.ButtonText) + "'" + " WHERE ToolButItemNum = '" + POut.pInt(Cur.ToolButItemNum) + "'";
        General.NonQ(command);
    }

    /**
    * This is not currently being used.
    */
    public static void deleteCur() throws Exception {
        String command = "DELETE from toolbutitem WHERE ToolButItemNum = '" + POut.pInt(Cur.ToolButItemNum) + "'";
        General.NonQ(command);
    }

    /**
    * Deletes all ToolButItems for the Programs.Cur.  This is used regularly when saving a Program link because of the way the user interface works.
    */
    public static void deleteAllForProgram(int programNum) throws Exception {
        String command = "DELETE from toolbutitem WHERE ProgramNum = '" + POut.pInt(programNum) + "'";
        General.NonQ(command);
    }

    /**
    * Fills ForProgram with toolbutitems attached to the Programs.Cur
    */
    public static void getForProgram(int programNum) throws Exception {
        ForProgram = new ArrayList();
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].ProgramNum == programNum)
            {
                ForProgram.Add(List[i]);
            }
             
        }
    }

    /**
    * Returns a list of toolbutitems for the specified toolbar. Used when laying out toolbars.
    */
    public static ArrayList getForToolBar(ToolBarsAvail toolbar) throws Exception {
        ArrayList retVal = new ArrayList();
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].ToolBar == toolbar && Programs.IsEnabled(List[i].ProgramNum))
            {
                retVal.Add(List[i]);
            }
             
        }
        return retVal;
    }

}


