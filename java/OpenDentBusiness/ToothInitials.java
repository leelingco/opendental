//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothInitials;
import OpenDentBusiness.ToothInitialType;

/**
* 
*/
public class ToothInitials   
{
    /**
    * Gets all toothinitial entries for the current patient.
    */
    public static List<ToothInitial> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ToothInitial>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM toothinitial" + " WHERE PatNum = " + POut.long(patNum);
        return Crud.ToothInitialCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(ToothInitial init) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            init.ToothInitialNum = Meth.GetLong(MethodBase.GetCurrentMethod(), init);
            return init.ToothInitialNum;
        }
         
        if (PrefC.getRandomKeys())
        {
            init.ToothInitialNum = ReplicationServers.getKey("toothinitial","ToothInitialNum");
        }
         
        return Crud.ToothInitialCrud.Insert(init);
    }

    /**
    * 
    */
    public static void update(ToothInitial init) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), init);
            return ;
        }
         
        Crud.ToothInitialCrud.Update(init);
    }

    /**
    * 
    */
    public static void delete(ToothInitial init) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), init);
            return ;
        }
         
        String command = "DELETE FROM toothinitial WHERE ToothInitialNum = '" + init.ToothInitialNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * Sets teeth missing, or sets primary, or sets movement values.  It first clears the value from the database, then adds a new row to represent that value.  Movements require an amount.  If movement amt is 0, then no row gets added.
    */
    public static void setValue(long patNum, String tooth_id, ToothInitialType initialType) throws Exception {
        //No need to check RemotingRole; no call to db.
        SetValue(patNum, tooth_id, initialType, 0);
    }

    /**
    * Sets teeth missing, or sets primary, or sets movement values.  It first clears the value from the database, then adds a new row to represent that value.  Movements require an amount.  If movement amt is 0, then no row gets added.
    */
    public static void setValue(long patNum, String tooth_id, ToothInitialType initialType, float moveAmt) throws Exception {
        //No need to check RemotingRole; no call to db.
        clearValue(patNum,tooth_id,initialType);
        ToothInitial ti = new ToothInitial();
        ti.PatNum = patNum;
        ti.ToothNum = tooth_id;
        ti.InitialType = initialType;
        ti.Movement = moveAmt;
        ToothInitials.insert(ti);
    }

    /**
    * Same as SetValue, but does not clear any values first.  Only use this if you have first run ClearAllValuesForType.
    */
    public static void setValueQuick(long patNum, String tooth_id, ToothInitialType initialType, float moveAmt) throws Exception {
        //No need to check RemotingRole; no call to db.
        ToothInitial ti = new ToothInitial();
        ti.PatNum = patNum;
        ti.ToothNum = tooth_id;
        ti.InitialType = initialType;
        ti.Movement = moveAmt;
        ToothInitials.insert(ti);
    }

    /**
    * Only used for incremental tooth movements.  Automatically adds a movement to any existing movement.  Supply a list of all toothInitials for the patient.
    */
    public static void addMovement(List<ToothInitial> initialList, long patNum, String tooth_id, ToothInitialType initialType, float moveAmt) throws Exception {
        //No need to check RemotingRole; no call to db.
        ToothInitial ti = null;
        for (int i = 0;i < initialList.Count;i++)
        {
            if (StringSupport.equals(initialList[i].ToothNum, tooth_id) && initialList[i].InitialType == initialType)
            {
                ti = initialList[i].Copy();
            }
             
        }
        if (ti == null)
        {
            ti = new ToothInitial();
            ti.PatNum = patNum;
            ti.ToothNum = tooth_id;
            ti.InitialType = initialType;
            ti.Movement = moveAmt;
            ToothInitials.insert(ti);
            return ;
        }
         
        ti.Movement += moveAmt;
        ToothInitials.update(ti);
    }

    /**
    * Sets teeth not missing, or sets to perm, or clears movement values.
    */
    public static void clearValue(long patNum, String tooth_id, ToothInitialType initialType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum, tooth_id, initialType);
            return ;
        }
         
        String command = "DELETE FROM toothinitial WHERE PatNum=" + POut.long(patNum) + " AND ToothNum='" + POut.string(tooth_id) + "' AND InitialType=" + POut.Long(((Enum)initialType).ordinal());
        Db.nonQ(command);
    }

    /**
    * Sets teeth not missing, or sets to perm, or clears movement values.  Clears all the values of one type for all teeth in the mouth.
    */
    public static void clearAllValuesForType(long patNum, ToothInitialType initialType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum, initialType);
            return ;
        }
         
        String command = "DELETE FROM toothinitial WHERE PatNum=" + POut.long(patNum) + " AND InitialType=" + POut.Long(((Enum)initialType).ordinal());
        Db.nonQ(command);
    }

    /**
    * Gets a list of missing teeth as strings. Includes "1"-"32", and "A"-"Z".
    */
    public static List<String> getMissingOrHiddenTeeth(List<ToothInitial> initialList) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<String> missing = new List<String>();
        for (int i = 0;i < initialList.Count;i++)
        {
            if ((initialList[i].InitialType == ToothInitialType.Missing || initialList[i].InitialType == ToothInitialType.Hidden) && Tooth.IsValidDB(initialList[i].ToothNum) && !Tooth.IsSuperNum(initialList[i].ToothNum) && !missing.Contains(initialList[i].ToothNum))
            {
                missing.Add(initialList[i].ToothNum);
            }
             
        }
        return missing;
    }

    /**
    * Gets a list of primary teeth as strings. Includes "1"-"32".
    */
    public static ArrayList getPriTeeth(List<ToothInitial> initialList) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList pri = new ArrayList();
        for (int i = 0;i < initialList.Count;i++)
        {
            if (initialList[i].InitialType == ToothInitialType.Primary && Tooth.IsValidDB(initialList[i].ToothNum) && !Tooth.IsPrimary(initialList[i].ToothNum) && !Tooth.IsSuperNum(initialList[i].ToothNum))
            {
                pri.Add(initialList[i].ToothNum);
            }
             
        }
        return pri;
    }

    /**
    * Loops through supplied initial list to see if the specified tooth is already marked as missing or hidden.
    */
    public static boolean toothIsMissingOrHidden(List<ToothInitial> initialList, String toothNum) throws Exception {
        for (int i = 0;i < initialList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (initialList[i].InitialType != ToothInitialType.Missing && initialList[i].InitialType != ToothInitialType.Hidden)
            {
                continue;
            }
             
            if (!StringSupport.equals(initialList[i].ToothNum, toothNum))
            {
                continue;
            }
             
            return true;
        }
        return false;
    }

    /**
    * Gets the current movement value for a single tooth by looping through the supplied list.
    */
    public static float getMovement(List<ToothInitial> initialList, String toothNum, ToothInitialType initialType) throws Exception {
        for (int i = 0;i < initialList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (initialList[i].InitialType == initialType && StringSupport.equals(initialList[i].ToothNum, toothNum))
            {
                return initialList[i].Movement;
            }
             
        }
        return 0;
    }

    /**
    * Gets a list of the hidden teeth as strings. Includes "1"-"32", and "A"-"Z".
    */
    public static ArrayList getHiddenTeeth(List<ToothInitial> initialList) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList hidden = new ArrayList();
        for (int i = 0;i < initialList.Count;i++)
        {
            if (initialList[i].InitialType == ToothInitialType.Hidden && Tooth.IsValidDB(initialList[i].ToothNum) && !Tooth.IsSuperNum(initialList[i].ToothNum))
            {
                hidden.Add(initialList[i].ToothNum);
            }
             
        }
        return hidden;
    }

}


