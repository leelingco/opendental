//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.RxPat;

/**
* 
*/
public class RxPats   
{
    /**
    * Used in Ehr.  Excludes controlled substances.
    */
    public static List<RxPat> getPermissableForDateRange(long patNum, DateTime dateStart, DateTime dateStop) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<RxPat>>GetObject(MethodBase.GetCurrentMethod(), patNum, dateStart, dateStop);
        }
         
        String command = "SELECT * FROM rxpat WHERE PatNum=" + POut.long(patNum) + " " + "AND RxDate >= " + POut.date(dateStart) + " " + "AND RxDate <= " + POut.date(dateStop) + " " + "AND IsControlled = 0";
        return Crud.RxPatCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static RxPat getRx(long rxNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<RxPat>GetObject(MethodBase.GetCurrentMethod(), rxNum);
        }
         
        return Crud.RxPatCrud.SelectOne(rxNum);
    }

    /**
    * 
    */
    public static void update(RxPat rx) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), rx);
            return ;
        }
         
        Crud.RxPatCrud.Update(rx);
    }

    /**
    * 
    */
    public static long insert(RxPat rx) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            rx.RxNum = Meth.GetLong(MethodBase.GetCurrentMethod(), rx);
            return rx.RxNum;
        }
         
        return Crud.RxPatCrud.Insert(rx);
    }

    /**
    * 
    */
    public static void delete(long rxNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), rxNum);
            return ;
        }
         
        String command = "DELETE FROM rxpat WHERE RxNum = '" + POut.long(rxNum) + "'";
        Db.nonQ(command);
    }

    public static List<long> getChangedSinceRxNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT RxNum FROM rxpat WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> rxnums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            rxnums.Add(PIn.Long(dt.Rows[i]["RxNum"].ToString()));
        }
        return rxnums;
    }

    /**
    * Used along with GetChangedSinceRxNums
    */
    public static List<RxPat> getMultRxPats(List<long> rxNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<RxPat>>GetObject(MethodBase.GetCurrentMethod(), rxNums);
        }
         
        String strRxNums = "";
        DataTable table = new DataTable();
        if (rxNums.Count > 0)
        {
            for (int i = 0;i < rxNums.Count;i++)
            {
                if (i > 0)
                {
                    strRxNums += "OR ";
                }
                 
                strRxNums += "RxNum='" + rxNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM rxpat WHERE " + strRxNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        RxPat[] multRxs = Crud.RxPatCrud.TableToList(table).ToArray();
        List<RxPat> rxList = new List<RxPat>(multRxs);
        return rxList;
    }

    /**
    * Used in FormRxSend to fill electronic queue.
    */
    public static List<RxPat> getQueue() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<RxPat>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM rxpat WHERE SendStatus=1";
        return Crud.RxPatCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static RxPat getRxNewCrop(String newCropGuid) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<RxPat>GetObject(MethodBase.GetCurrentMethod(), newCropGuid);
        }
         
        String command = "SELECT * FROM rxpat WHERE NewCropGuid='" + POut.string(newCropGuid) + "'";
        List<RxPat> rxNewCrop = Crud.RxPatCrud.SelectMany(command);
        if (rxNewCrop.Count == 0)
        {
            return null;
        }
         
        return rxNewCrop[0];
    }

}


