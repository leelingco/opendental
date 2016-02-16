//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Db;
import OpenDentBusiness.ElectronicClaimFormat;
import OpenDentBusiness.EnumClaimMedType;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Clearinghouses   
{
    /**
    * List of all clearinghouses.
    */
    private static Clearinghouse[] listt = new Clearinghouse[]();
    /**
    * Key=PayorID. Value=ClearingHouseNum.
    */
    private static Hashtable HList = new Hashtable();
    //No need to check RemotingRole; no call to db.
    public static Clearinghouse[] getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(Clearinghouse[] value) throws Exception {
        listt = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM clearinghouse";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Clearinghouse";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.ClearinghouseCrud.TableToList(table).ToArray();
        HList = new Hashtable();
        String[] payors = new String[]();
        for (int i = 0;i < listt.Length;i++)
        {
            payors = listt[i].Payors.Split(',');
            for (int j = 0;j < payors.Length;j++)
            {
                if (!HList.ContainsKey(payors[j]))
                {
                    HList.Add(payors[j], listt[i].ClearinghouseNum);
                }
                 
            }
        }
    }

    /**
    * Inserts this clearinghouse into database.
    */
    public static long insert(Clearinghouse clearhouse) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            clearhouse.ClearinghouseNum = Meth.GetLong(MethodBase.GetCurrentMethod(), clearhouse);
            return clearhouse.ClearinghouseNum;
        }
         
        return Crud.ClearinghouseCrud.Insert(clearhouse);
    }

    /**
    * 
    */
    public static void update(Clearinghouse clearhouse) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), clearhouse);
            return ;
        }
         
        Crud.ClearinghouseCrud.Update(clearhouse);
    }

    /**
    * 
    */
    public static void delete(Clearinghouse clearhouse) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), clearhouse);
            return ;
        }
         
        String command = "DELETE FROM clearinghouse WHERE ClearinghouseNum = '" + POut.long(clearhouse.ClearinghouseNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Gets the last batch number for this clearinghouse and increments it by one.  Saves the new value, then returns it.  So even if the new value is not used for some reason, it will have already been incremented. Remember that LastBatchNumber is never accurate with local data in memory.
    */
    public static int getNextBatchNumber(Clearinghouse clearhouse) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), clearhouse);
        }
         
        //get last batch number
        String command = "SELECT LastBatchNumber FROM clearinghouse " + "WHERE ClearinghouseNum = " + POut.long(clearhouse.ClearinghouseNum);
        DataTable table = Db.getTable(command);
        int batchNum = PIn.Int(table.Rows[0][0].ToString());
        //and increment it by one
        if (clearhouse.Eformat == ElectronicClaimFormat.Canadian)
        {
            if (batchNum == 999999)
            {
                batchNum = 1;
            }
            else
            {
                batchNum++;
            } 
        }
        else
        {
            if (batchNum == 999)
            {
                batchNum = 1;
            }
            else
            {
                batchNum++;
            } 
        } 
        //save the new batch number. Even if user cancels, it will have incremented.
        command = "UPDATE clearinghouse SET LastBatchNumber=" + batchNum.ToString() + " WHERE ClearinghouseNum = " + POut.long(clearhouse.ClearinghouseNum);
        Db.nonQ(command);
        return batchNum;
    }

    /**
    * Returns the clearinghouseNum for claims for the supplied payorID.  If the payorID was not entered or if no default was set, then 0 is returned.
    */
    public static long automateClearinghouseSelection(String payorID, EnumClaimMedType medType) throws Exception {
        //No need to check RemotingRole; no call to db.
        //payorID can be blank.  For example, Renaissance does not require payorID.
        if (HList == null)
        {
            refreshCache();
        }
         
        Clearinghouse clearinghouse = null;
        if (medType == EnumClaimMedType.Dental)
        {
            if (PrefC.getLong(PrefName.ClearinghouseDefaultDent) == 0)
            {
                return 0;
            }
             
            clearinghouse = getClearinghouse(PrefC.getLong(PrefName.ClearinghouseDefaultDent));
        }
         
        if (medType == EnumClaimMedType.Medical || medType == EnumClaimMedType.Institutional)
        {
            if (PrefC.getLong(PrefName.ClearinghouseDefaultMed) == 0)
            {
                return 0;
            }
             
            clearinghouse = getClearinghouse(PrefC.getLong(PrefName.ClearinghouseDefaultMed));
        }
         
        if (clearinghouse == null)
        {
            return 0;
        }
         
        //we couldn't find a default clearinghouse for that medType.  Needs to always be a default.
        if (!StringSupport.equals(payorID, "") && HList.ContainsKey(payorID))
        {
            //an override exists for this payorID
            Clearinghouse ch = getClearinghouse((long)HList[payorID]);
            if (ch.Eformat == ElectronicClaimFormat.x837D_4010 || ch.Eformat == ElectronicClaimFormat.x837D_5010_dental || ch.Eformat == ElectronicClaimFormat.Canadian)
            {
                //all dental formats
                if (medType == EnumClaimMedType.Dental)
                {
                    return ch.ClearinghouseNum;
                }
                 
            }
             
            //med type matches
            if (ch.Eformat == ElectronicClaimFormat.x837_5010_med_inst)
            {
                if (medType == EnumClaimMedType.Medical || medType == EnumClaimMedType.Institutional)
                {
                    return ch.ClearinghouseNum;
                }
                 
            }
             
        }
         
        return clearinghouse.ClearinghouseNum;
    }

    //med type matches
    //no override, so just return the default.
    /**
    * Returns the default clearinghouse. If no default present, returns null.
    */
    public static Clearinghouse getDefaultDental() throws Exception {
        return getClearinghouse(PrefC.getLong(PrefName.ClearinghouseDefaultDent));
    }

    //No need to check RemotingRole; no call to db.
    /**
    * Gets a clearinghouse from cache.  Will return null if invalid.
    */
    public static Clearinghouse getClearinghouse(long clearinghouseNum) throws Exception {
        for (int i = 0;i < getListt().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (clearinghouseNum == getListt()[i].ClearinghouseNum)
            {
                return getListt()[i];
            }
             
        }
        return null;
    }

}


