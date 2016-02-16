//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SupplyOrderItem;

/**
* 
*/
public class SupplyOrderItems   
{
    /**
    * Items in the table are not ItemOrderObjects
    */
    public static DataTable getItemsForOrder(long orderNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), orderNum);
        }
         
        String command = "SELECT CatalogNumber,Descript,Qty,supplyorderitem.Price,SupplyOrderItemNum,supplyorderitem.SupplyNum " + "FROM supplyorderitem,definition,supply " + "WHERE definition.DefNum=supply.Category " + "AND supply.SupplyNum=supplyorderitem.SupplyNum " + "AND supplyorderitem.SupplyOrderNum=" + POut.long(orderNum) + " " + "ORDER BY definition.ItemOrder,supply.ItemOrder";
        return Db.getTable(command);
    }

    public static SupplyOrderItem createObject(long supplyOrderItemNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<SupplyOrderItem>GetObject(MethodBase.GetCurrentMethod(), supplyOrderItemNum);
        }
         
        String command = "SELECT * FROM supplyorderitem WHERE SupplyOrderItemNum=" + POut.long(supplyOrderItemNum);
        return Crud.SupplyOrderItemCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static long insert(SupplyOrderItem supp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            supp.SupplyOrderItemNum = Meth.GetLong(MethodBase.GetCurrentMethod(), supp);
            return supp.SupplyOrderItemNum;
        }
         
        return Crud.SupplyOrderItemCrud.Insert(supp);
    }

    /**
    * 
    */
    public static void update(SupplyOrderItem supp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), supp);
            return ;
        }
         
        Crud.SupplyOrderItemCrud.Update(supp);
    }

    /**
    * Surround with try-catch.
    */
    public static void deleteObject(SupplyOrderItem supp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), supp);
            return ;
        }
         
        //validate that not already in use.
        Crud.SupplyOrderItemCrud.Delete(supp.SupplyOrderItemNum);
    }

}


