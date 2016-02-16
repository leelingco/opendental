//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile.Crud;

import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.RxPatm;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;
import OpenDentBusiness.RxPat;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class RxPatmCrud   
{
    /**
    * Gets one RxPatm object from the database using primaryKey1(CustomerNum) and primaryKey2.  Returns null if not found.
    */
    public static RxPatm selectOne(long customerNum, long rxNum) throws Exception {
        String command = "SELECT * FROM rxpatm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND RxNum = " + POut.long(rxNum);
        List<RxPatm> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one RxPatm object from the database using a query.
    */
    public static RxPatm selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<RxPatm> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of RxPatm objects from the database using a query.
    */
    public static List<RxPatm> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<RxPatm> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<RxPatm> tableToList(DataTable table) throws Exception {
        List<RxPatm> retVal = new List<RxPatm>();
        RxPatm rxPatm;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            rxPatm = new RxPatm();
            rxPatm.CustomerNum = PIn.Long(table.Rows[i]["CustomerNum"].ToString());
            rxPatm.RxNum = PIn.Long(table.Rows[i]["RxNum"].ToString());
            rxPatm.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            rxPatm.RxDate = PIn.Date(table.Rows[i]["RxDate"].ToString());
            rxPatm.Drug = PIn.String(table.Rows[i]["Drug"].ToString());
            rxPatm.Sig = PIn.String(table.Rows[i]["Sig"].ToString());
            rxPatm.Disp = PIn.String(table.Rows[i]["Disp"].ToString());
            rxPatm.Refills = PIn.String(table.Rows[i]["Refills"].ToString());
            rxPatm.ProvNum = PIn.Long(table.Rows[i]["ProvNum"].ToString());
            retVal.Add(rxPatm);
        }
        return retVal;
    }

    /**
    * Usually set useExistingPK=true.  Inserts one RxPatm into the database.
    */
    public static long insert(RxPatm rxPatm, boolean useExistingPK) throws Exception {
        if (!useExistingPK)
        {
            rxPatm.RxNum = ReplicationServers.getKey("rxpatm","RxNum");
        }
         
        String command = "INSERT INTO rxpatm (";
        command += "RxNum,";
        command += "CustomerNum,PatNum,RxDate,Drug,Sig,Disp,Refills,ProvNum) VALUES(";
        command += POut.long(rxPatm.RxNum) + ",";
        command += POut.long(rxPatm.CustomerNum) + "," + POut.long(rxPatm.PatNum) + "," + POut.date(rxPatm.RxDate) + "," + "'" + POut.string(rxPatm.Drug) + "'," + "'" + POut.string(rxPatm.Sig) + "'," + "'" + POut.string(rxPatm.Disp) + "'," + "'" + POut.string(rxPatm.Refills) + "'," + POut.long(rxPatm.ProvNum) + ")";
        Db.nonQ(command);
        return rxPatm.RxNum;
    }

    //There is no autoincrement in the mobile server.
    /**
    * Updates one RxPatm in the database.
    */
    public static void update(RxPatm rxPatm) throws Exception {
        String command = "UPDATE rxpatm SET " + "PatNum     =  " + POut.long(rxPatm.PatNum) + ", " + "RxDate     =  " + POut.date(rxPatm.RxDate) + ", " + "Drug       = '" + POut.string(rxPatm.Drug) + "', " + "Sig        = '" + POut.string(rxPatm.Sig) + "', " + "Disp       = '" + POut.string(rxPatm.Disp) + "', " + "Refills    = '" + POut.string(rxPatm.Refills) + "', " + "ProvNum    =  " + POut.long(rxPatm.ProvNum) + " " + "WHERE CustomerNum = " + POut.long(rxPatm.CustomerNum) + " AND RxNum = " + POut.long(rxPatm.RxNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one RxPatm from the database.
    */
    public static void delete(long customerNum, long rxNum) throws Exception {
        String command = "DELETE FROM rxpatm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND RxNum = " + POut.long(rxNum);
        Db.nonQ(command);
    }

    /**
    * Converts one RxPat object to its mobile equivalent.  Warning! CustomerNum will always be 0.
    */
    public static RxPatm convertToM(RxPat rxPat) throws Exception {
        RxPatm rxPatm = new RxPatm();
        //CustomerNum cannot be set.  Remains 0.
        rxPatm.RxNum = rxPat.RxNum;
        rxPatm.PatNum = rxPat.PatNum;
        rxPatm.RxDate = rxPat.RxDate;
        rxPatm.Drug = rxPat.Drug;
        rxPatm.Sig = rxPat.Sig;
        rxPatm.Disp = rxPat.Disp;
        rxPatm.Refills = rxPat.Refills;
        rxPatm.ProvNum = rxPat.ProvNum;
        return rxPatm;
    }

}


