//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.QuestionDef;
import OpenDentBusiness.QuestionDefs;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class QuestionDefs   
{
    /**
    * Gets a list of all QuestionDefs.
    */
    public static QuestionDef[] refresh() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<QuestionDef[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM questiondef ORDER BY ItemOrder";
        return Crud.QuestionDefCrud.SelectMany(command).ToArray();
    }

    /**
    * 
    */
    public static void update(QuestionDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        Crud.QuestionDefCrud.Update(def);
    }

    /**
    * 
    */
    public static long insert(QuestionDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            def.QuestionDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), def);
            return def.QuestionDefNum;
        }
         
        return Crud.QuestionDefCrud.Insert(def);
    }

    /**
    * Ok to delete whenever, because no patients are tied to this table by any dependencies.
    */
    public static void delete(QuestionDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        String command = "DELETE FROM questiondef WHERE QuestionDefNum =" + POut.long(def.QuestionDefNum);
        Db.nonQ(command);
    }

    /**
    * Moves the selected item up in the list.
    */
    public static void moveUp(int selected, QuestionDef[] List) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (selected < 0)
        {
            throw new ApplicationException(Lans.g("QuestionDefs","Please select an item first."));
        }
         
        if (selected == 0)
        {
            return ;
        }
         
        //already at top
        if (selected > List.Length - 1)
        {
            throw new ApplicationException(Lans.g("QuestionDefs","Invalid selection."));
        }
         
        SetOrder(selected - 1, List[selected].ItemOrder, List);
        SetOrder(selected, List[selected].ItemOrder - 1, List);
    }

    /**
    * 
    */
    public static void moveDown(int selected, QuestionDef[] List) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (selected < 0)
        {
            throw new ApplicationException(Lans.g("QuestionDefs","Please select an item first."));
        }
         
        if (selected == List.Length - 1)
        {
            return ;
        }
         
        //already at bottom
        if (selected > List.Length - 1)
        {
            throw new ApplicationException(Lans.g("QuestionDefs","Invalid selection."));
        }
         
        SetOrder(selected + 1, List[selected].ItemOrder, List);
        SetOrder(selected, List[selected].ItemOrder + 1, List);
    }

    //selected+=1;
    /**
    * Used by MoveUp and MoveDown.
    */
    private static void setOrder(int mySelNum, int myItemOrder, QuestionDef[] List) throws Exception {
        //No need to check RemotingRole; no call to db.
        QuestionDef temp = List[mySelNum];
        temp.ItemOrder = myItemOrder;
        QuestionDefs.update(temp);
    }

}


