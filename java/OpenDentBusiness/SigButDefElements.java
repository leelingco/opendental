//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SigButDefElement;

/**
* 
*/
public class SigButDefElements   
{
    /**
    * A list of all elements for all buttons
    */
    private static SigButDefElement[] list = new SigButDefElement[]();
    //No need to check RemotingRole; no call to db.
    public static SigButDefElement[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(SigButDefElement[] value) throws Exception {
        list = value;
    }

    /**
    * Gets all SigButDefElements for all buttons, ordered by type: user,extras, message.
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM sigbutdefelement";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "SigButDefElement";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.SigButDefElementCrud.TableToList(table).ToArray();
    }

    /*
    		///<summary>This will never happen</summary>
    		public void Update(){
    			string command= "UPDATE SigButDefElement SET " 
    				+"FromUser = '"    +POut.PString(FromUser)+"'"
    				+",ITypes = '"     +POut.PInt   ((int)ITypes)+"'"
    				+",DateViewing = '"+POut.PDate  (DateViewing)+"'"
    				+",SigType = '"    +POut.PInt   ((int)SigType)+"'"
    				+" WHERE SigButDefElementNum = '"+POut.PInt(SigButDefElementNum)+"'";
    			DataConnection dcon=new DataConnection();
     			Db.NonQ(command);
    		}*/
    /**
    * 
    */
    public static long insert(SigButDefElement element) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            element.ElementNum = Meth.GetLong(MethodBase.GetCurrentMethod(), element);
            return element.ElementNum;
        }
         
        return Crud.SigButDefElementCrud.Insert(element);
    }

    /**
    * 
    */
    public static void delete(SigButDefElement element) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), element);
            return ;
        }
         
        String command = "DELETE from sigbutdefelement WHERE ElementNum = '" + POut.long(element.ElementNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Loops through the SigButDefElement list and pulls out all elements for one specific button.
    */
    public static SigButDefElement[] getForButton(long sigButDefNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList AL = new ArrayList();
        for (int i = 0;i < getList().Length;i++)
        {
            if (getList()[i].SigButDefNum == sigButDefNum)
            {
                AL.Add(getList()[i].Copy());
            }
             
        }
        SigButDefElement[] retVal = new SigButDefElement[AL.Count];
        AL.CopyTo(retVal);
        return retVal;
    }

}


//already ordered because List is ordered.