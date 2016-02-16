//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SupplyOrder;
import OpenDentBusiness.SupplyOrders;

/**
* 
*/
public class SupplyOrders   
{
    /**
    * Gets all SupplyOrders for one supplier, ordered by date.
    */
    public static List<SupplyOrder> createObjects(long supplierNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<SupplyOrder>>GetObject(MethodBase.GetCurrentMethod(), supplierNum);
        }
         
        String command = "SELECT * FROM supplyorder " + "WHERE SupplierNum=" + POut.long(supplierNum) + " ORDER BY DatePlaced";
        return Crud.SupplyOrderCrud.SelectMany(command);
    }

    /**
    * Gets all SupplyOrders, ordered by date.
    */
    public static List<SupplyOrder> getAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<SupplyOrder>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM supplyorder ORDER BY DatePlaced";
        return Crud.SupplyOrderCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(SupplyOrder order) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            order.SupplyOrderNum = Meth.GetLong(MethodBase.GetCurrentMethod(), order);
            return order.SupplyOrderNum;
        }
         
        return Crud.SupplyOrderCrud.Insert(order);
    }

    /**
    * 
    */
    public static void update(SupplyOrder order) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), order);
            return ;
        }
         
        Crud.SupplyOrderCrud.Update(order);
    }

    /**
    * No need to surround with try-catch.
    */
    public static void deleteObject(SupplyOrder order) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), order);
            return ;
        }
         
        //validate that not already in use-no
        //delete associated orderItems
        String command = "DELETE FROM supplyorderitem WHERE SupplyOrderNum=" + POut.long(order.SupplyOrderNum);
        Db.nonQ(command);
        Crud.SupplyOrderCrud.Delete(order.SupplyOrderNum);
    }

    //Retotals all items attached to order and updates AmountTotal.
    public static void updateOrderPrice(long orderNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), orderNum);
            return ;
        }
         
        String command = "SELECT SUM(Qty*Price) FROM supplyorderitem WHERE SupplyOrderNum=" + orderNum;
        double amountTotal = PIn.double(Db.getScalar(command));
        command = "SELECT * FROM supplyorder WHERE SupplyOrderNum=" + orderNum;
        SupplyOrder so = Crud.SupplyOrderCrud.SelectOne(command);
        so.AmountTotal = amountTotal;
        SupplyOrders.update(so);
    }

}


