//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.ZipCode;
import OpenDentBusiness.MiscData;

/*=========================================================================================
		=================================== class ZipCodes ===========================================*/
/**
* 
*/
public class ZipCodes   
{
    /**
    * 
    */
    public static ZipCode Cur = new ZipCode();
    /**
    * 
    */
    public static ZipCode[] List = new ZipCode[]();
    /**
    * 
    */
    public static ArrayList ALFrequent = new ArrayList();
    /**
    * 
    */
    public static ArrayList ALMatches = new ArrayList();
    //public static Hashtable HList;
    /**
    * Refresh done on startup and then whenever a change is made.
    */
    public static void refresh() throws Exception {
        String command = "SELECT * from zipcode ORDER BY zipcodedigits";
        DataTable table = General.GetTable(command);
        //HList=new Hashtable();
        ALFrequent = new ArrayList();
        List = new ZipCode[table.Rows.Count];
        for (int i = 0;i < List.Length;i++)
        {
            List[i].ZipCodeNum = PIn.PInt(table.Rows[i][0].ToString());
            List[i].ZipCodeDigits = PIn.PString(table.Rows[i][1].ToString());
            List[i].City = PIn.PString(table.Rows[i][2].ToString());
            List[i].State = PIn.PString(table.Rows[i][3].ToString());
            List[i].IsFrequent = PIn.PBool(table.Rows[i][4].ToString());
            if (List[i].IsFrequent)
            {
                ALFrequent.Add(List[i]);
            }
             
        }
    }

    //HList.Add(List[i].ZipCodeNum,List[i]);
    /**
    * 
    */
    public static void insertCur() throws Exception {
        if (PrefB.RandomKeys)
        {
            Cur.ZipCodeNum = MiscData.GetKey("zipcode", "ZipCodeNum");
        }
         
        String command = "INSERT INTO zipcode (";
        if (PrefB.RandomKeys)
        {
            command += "ZipCodeNum,";
        }
         
        command += "zipcodedigits,city,state,isfrequent) VALUES(";
        if (PrefB.RandomKeys)
        {
            command += "'" + POut.pInt(Cur.ZipCodeNum) + "', ";
        }
         
        command += "'" + POut.pString(Cur.ZipCodeDigits) + "', " + "'" + POut.pString(Cur.City) + "', " + "'" + POut.pString(Cur.State) + "', " + "'" + POut.pBool(Cur.IsFrequent) + "')";
        if (PrefB.RandomKeys)
        {
            General.NonQ(command);
        }
        else
        {
            Cur.ZipCodeNum = General.NonQ(command, true);
        } 
    }

    /**
    * 
    */
    public static void updateCur() throws Exception {
        String command = "UPDATE zipcode SET " + "zipcodedigits ='" + POut.pString(Cur.ZipCodeDigits) + "'" + ",city ='" + POut.pString(Cur.City) + "'" + ",state ='" + POut.pString(Cur.State) + "'" + ",isfrequent ='" + POut.pBool(Cur.IsFrequent) + "'" + " WHERE zipcodenum = '" + POut.pInt(Cur.ZipCodeNum) + "'";
        General.NonQ(command);
    }

    /**
    * 
    */
    public static void deleteCur() throws Exception {
        String command = "DELETE from zipcode WHERE zipcodenum = '" + POut.pInt(Cur.ZipCodeNum) + "'";
        General.NonQ(command);
    }

    /**
    * 
    */
    public static void getALMatches(String zipCodeDigits) throws Exception {
        ALMatches = new ArrayList();
        for (int i = 0;i < List.Length;i++)
        {
            if (StringSupport.equals(List[i].ZipCodeDigits, zipCodeDigits))
            {
                ALMatches.Add(List[i]);
            }
             
        }
    }

}


