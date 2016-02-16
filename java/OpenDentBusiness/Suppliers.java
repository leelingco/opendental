//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Supplier;

/**
* 
*/
public class Suppliers   
{
    /**
    * Gets all Suppliers.
    */
    public static List<Supplier> createObjects() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Supplier>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM supplier ORDER BY Name";
        return Crud.SupplierCrud.SelectMany(command);
    }

    /**
    * Gets one Supplier by num.
    */
    public static Supplier getOne(long supplierNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Supplier>GetObject(MethodBase.GetCurrentMethod());
        }
         
        return Crud.SupplierCrud.SelectOne(supplierNum);
    }

    /**
    * 
    */
    public static long insert(Supplier supp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            supp.SupplierNum = Meth.GetLong(MethodBase.GetCurrentMethod(), supp);
            return supp.SupplierNum;
        }
         
        return Crud.SupplierCrud.Insert(supp);
    }

    /**
    * 
    */
    public static void update(Supplier supp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), supp);
            return ;
        }
         
        Crud.SupplierCrud.Update(supp);
    }

    /**
    * Surround with try-catch.
    */
    public static void deleteObject(Supplier supp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), supp);
            return ;
        }
         
        //validate that not already in use.
        String command = "SELECT COUNT(*) FROM supplyorder WHERE SupplierNum=" + POut.long(supp.SupplierNum);
        int count = PIn.int(Db.getCount(command));
        if (count > 0)
        {
            throw new ApplicationException(Lans.g("Supplies","Supplier is already in use on an order. Not allowed to delete."));
        }
         
        command = "SELECT COUNT(*) FROM supply WHERE SupplierNum=" + POut.long(supp.SupplierNum);
        count = PIn.int(Db.getCount(command));
        if (count > 0)
        {
            throw new ApplicationException(Lans.g("Supplies","Supplier is already in use on a supply. Not allowed to delete."));
        }
         
        Crud.SupplierCrud.Delete(supp.SupplierNum);
    }

    public static String getName(List<Supplier> listSupplier, long supplierNum) throws Exception {
        for (int i = 0;i < listSupplier.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (listSupplier[i].SupplierNum == supplierNum)
            {
                return listSupplier[i].Name;
            }
             
        }
        return "";
    }

}


