//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.LetterMergeField;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class LetterMergeFields   
{
    /**
    * List of all lettermergeFields.
    */
    private static LetterMergeField[] list = new LetterMergeField[]();
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM lettermergefield " + "ORDER BY FieldName";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "LetterMergeField";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.LetterMergeFieldCrud.TableToList(table).ToArray();
    }

    /**
    * Inserts this lettermergefield into database.
    */
    public static long insert(LetterMergeField lmf) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            lmf.FieldNum = Meth.GetLong(MethodBase.GetCurrentMethod(), lmf);
            return lmf.FieldNum;
        }
         
        return Crud.LetterMergeFieldCrud.Insert(lmf);
    }

    /*
    		///<summary></summary>
    		public void Update(){
    			string command="UPDATE lettermergefield SET "
    				+"LetterMergeNum = '"+POut.PInt   (LetterMergeNum)+"' "
    				+",FieldName = '"    +POut.PString(FieldName)+"' "
    				+"WHERE FieldNum = '"+POut.PInt(FieldNum)+"'";
    			DataConnection dcon=new DataConnection();
     			Db.NonQ(command);
    		}*/
    /*
    		///<summary></summary>
    		public void Delete(){
    			string command="DELETE FROM lettermergefield "
    				+"WHERE FieldNum = "+POut.PInt(FieldNum);
    			DataConnection dcon=new DataConnection();
    			Db.NonQ(command);
    		}*/
    /**
    * Called from LetterMerge.Refresh() to get all field names for a given letter.  The result is a collection of strings representing field names.
    */
    public static List<String> getForLetter(long letterMergeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (list == null)
        {
            refreshCache();
        }
         
        List<String> retVal = new List<String>();
        for (int i = 0;i < list.Length;i++)
        {
            if (list[i].LetterMergeNum == letterMergeNum)
            {
                retVal.Add(list[i].FieldName);
            }
             
        }
        return retVal;
    }

    /**
    * Deletes all lettermergefields for the given letter.  This is then followed by adding them all back, which is simpler than just updating.
    */
    public static void deleteForLetter(long letterMergeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), letterMergeNum);
            return ;
        }
         
        String command = "DELETE FROM lettermergefield " + "WHERE LetterMergeNum = " + POut.long(letterMergeNum);
        Db.nonQ(command);
    }

}


