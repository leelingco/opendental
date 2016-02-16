//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SigButDef;
import OpenDentBusiness.SigButDefElement;
import OpenDentBusiness.SigButDefElements;
import OpenDentBusiness.SigElementDefs;
import OpenDentBusiness.SignalElementType;

/**
* 
*/
public class SigButDefs   
{
    private static SigButDef[] listt = new SigButDef[]();
    /**
    * A list of all SigButDefs.
    */
    //No need to check RemotingRole; no call to db.
    public static SigButDef[] getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(SigButDef[] value) throws Exception {
        listt = value;
    }

    /**
    * Gets a list of all SigButDefs when program first opens.  Also refreshes SigButDefElements and attaches all elements to the appropriate buttons.
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM sigbutdef ORDER BY ButtonIndex";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "SigButDef";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.SigButDefCrud.TableToList(table).ToArray();
        for (int i = 0;i < listt.Length;i++)
        {
            listt[i].ElementList = SigButDefElements.GetForButton(listt[i].SigButDefNum);
        }
    }

    /**
    * 
    */
    public static void update(SigButDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        Crud.SigButDefCrud.Update(def);
    }

    /**
    * 
    */
    public static long insert(SigButDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            def.SigButDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), def);
            return def.SigButDefNum;
        }
         
        return Crud.SigButDefCrud.Insert(def);
    }

    /**
    * No need to surround with try/catch, because all deletions are allowed.
    */
    public static void delete(SigButDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        String command = "DELETE FROM sigbutdefelement WHERE SigButDefNum=" + POut.long(def.SigButDefNum);
        Db.nonQ(command);
        command = "DELETE FROM sigbutdef WHERE SigButDefNum =" + POut.long(def.SigButDefNum);
        Db.nonQ(command);
    }

    /**
    * Used in the Button edit dialog.
    */
    public static void deleteElements(SigButDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        String command = "DELETE FROM sigbutdefelement WHERE SigButDefNum=" + POut.long(def.SigButDefNum);
        Db.nonQ(command);
    }

    /**
    * Loops through the element list and pulls out one element of a specific type. Used in the button edit window.
    */
    public static SigButDefElement getElement(SigButDef def, SignalElementType elementType) throws Exception {
        for (int i = 0;i < def.ElementList.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (SigElementDefs.GetElement(def.ElementList[i].SigElementDefNum).SigElementType == elementType)
            {
                return def.ElementList[i].Copy();
            }
             
        }
        return null;
    }

    /**
    * Used in Setup.  The returned list also includes defaults if not overridden by one with a computername.  The supplied computer name can be blank for the default setup.
    */
    public static SigButDef[] getByComputer(String computerName) throws Exception {
        //No need to check RemotingRole; no call to db.
        //first, get a default list, because we will always need that
        ArrayList AL = new ArrayList();
        for (int i = 0;i < getListt().Length;i++)
        {
            if (StringSupport.equals(getListt()[i].ComputerName, ""))
            {
                AL.Add(getListt()[i]);
            }
             
        }
        SigButDef[] defaultList = new SigButDef[AL.Count];
        AL.CopyTo(defaultList);
        if (StringSupport.equals(computerName, ""))
        {
            return defaultList;
        }
         
        //if all we are interested in is the default list, then done.
        //for any other computer:
        List<SigButDef> listSigButDefs = new List<SigButDef>();
        for (int i = 0;i < getListt().Length;i++)
        {
            if (StringSupport.equals(computerName, getListt()[i].ComputerName))
            {
                listSigButDefs.Add(getListt()[i]);
            }
             
        }
        //but we are still missing some defaults
        SigButDef matchingBut;
        for (int i = 0;i < defaultList.Length;i++)
        {
            matchingBut = GetByIndex(defaultList[i].ButtonIndex, listSigButDefs);
            //retVal);
            if (matchingBut != null)
            {
                continue;
            }
             
            //There is a button for this computer which overrides the default, so don't add the default.
            //AL.Add(defaultList[i]);
            listSigButDefs.Add(defaultList[i]);
        }
        listSigButDefs.Sort(CompareButtonsByIndex);
        SigButDef[] retVal = new SigButDef[listSigButDefs.Count];
        listSigButDefs.CopyTo(retVal);
        return retVal;
    }

    private static int compareButtonsByIndex(SigButDef x, SigButDef y) throws Exception {
        return x.ButtonIndex.CompareTo(y.ButtonIndex);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Moves the selected item up in the supplied sub list.
    */
    public static void moveUp(SigButDef selected, SigButDef[] subList) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (selected.ButtonIndex == 0)
        {
            return ;
        }
         
        //already at top
        SigButDef occupied = null;
        for (int i = 0;i < subList.Length;i++)
        {
            //if not the selected object
            if (subList[i].SigButDefNum != selected.SigButDefNum && subList[i].ButtonIndex == selected.ButtonIndex - 1)
            {
                //and position occupied
                occupied = subList[i].Copy();
            }
             
        }
        if (occupied != null)
        {
            occupied.ButtonIndex++;
            update(occupied);
        }
         
        selected.ButtonIndex--;
        update(selected);
    }

    /**
    * 
    */
    public static void moveDown(SigButDef selected, SigButDef[] subList) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (selected.ButtonIndex == 20)
        {
            throw new ApplicationException(Lans.g("SigButDefs","Max 20 buttons."));
        }
         
        SigButDef occupied = null;
        for (int i = 0;i < subList.Length;i++)
        {
            //if not the selected object
            if (subList[i].SigButDefNum != selected.SigButDefNum && subList[i].ButtonIndex == selected.ButtonIndex + 1)
            {
                //and position occupied
                occupied = subList[i].Copy();
            }
             
        }
        if (occupied != null)
        {
            occupied.ButtonIndex--;
            update(occupied);
        }
         
        selected.ButtonIndex++;
        update(selected);
    }

    /**
    * Returns the SigButDef with the specified buttonIndex.  Used from the setup page.  The supplied list will already have been filtered by computername.  Supply buttonIndex in 0-based format.
    */
    public static SigButDef getByIndex(int buttonIndex, List<SigButDef> subList) throws Exception {
        for (int i = 0;i < subList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (subList[i].ButtonIndex == buttonIndex)
            {
                return subList[i].Copy();
            }
             
        }
        return null;
    }

    /**
    * Returns the SigButDef with the specified buttonIndex.  Used from the setup page.  The supplied list will already have been filtered by computername.  Supply buttonIndex in 0-based format.
    */
    public static SigButDef getByIndex(int buttonIndex, SigButDef[] subList) throws Exception {
        for (int i = 0;i < subList.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (subList[i].ButtonIndex == buttonIndex)
            {
                return subList[i].Copy();
            }
             
        }
        return null;
    }

}


