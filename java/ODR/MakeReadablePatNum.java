//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;

import CS2JNet.System.StringSupport;
import ODR.DataConnection;

/**
* 
*/
public class MakeReadablePatNum   
{
    private Hashtable hash = new Hashtable();
    /**
    * Constructor
    */
    public MakeReadablePatNum() throws Exception {
        hash = new Hashtable();
        String command = "SELECT PatNum,LName,FName,Preferred,MiddleI FROM patient";
        DataConnection dcon = new DataConnection();
        DataTable table = dcon.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (StringSupport.equals(table.Rows[i][3].ToString(), ""))
            {
                //no preferred
                //key is patNum, in string format
                //LName
                //FName
                hash.Add(table.Rows[i][0].ToString(), table.Rows[i][1].ToString() + ", " + table.Rows[i][2].ToString() + " " + table.Rows[i][4].ToString());
            }
            else
            {
                //MiddleI
                //key is patNum, in string format
                //LName
                //Preferred
                //FName
                hash.Add(table.Rows[i][0].ToString(), table.Rows[i][1].ToString() + ", '" + table.Rows[i][3].ToString() + "' " + table.Rows[i][2].ToString() + " " + table.Rows[i][4].ToString());
            } 
        }
    }

    //MiddleI
    public String get(String patNum) throws Exception {
        if (hash.ContainsKey(patNum))
        {
            return (String)hash[patNum];
        }
         
        return "";
    }

}


/*if(patNum=="1"){
				return "one1";
			}
			else if(patNum=="2"){
				return "two2";
			}
			return "other3";*/