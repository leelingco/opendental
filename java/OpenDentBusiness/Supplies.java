//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Supply;

/**
* 
*/
public class Supplies   
{
    /**
    * //Gets all Supplies, ordered by category and itemOrder.  Optionally hides those marked IsHidden.  FindText must only include alphanumeric characters.
    */
    //public static List<Supply> CreateObjects(bool includeHidden,long supplierNum,string findText) {
    //	if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    //		return Meth.GetObject<List<Supply>>(MethodBase.GetCurrentMethod(),includeHidden,supplierNum,findText);
    //	}
    //	string command="SELECT supply.* FROM supply,definition "
    //		+"WHERE definition.DefNum=supply.Category "
    //		+"AND supply.SupplierNum="+POut.Long(supplierNum)+" ";
    //	if(findText!=""){
    //		command+="AND ("+DbHelper.Regexp("supply.CatalogNumber",POut.String(findText))+" OR "+DbHelper.Regexp("supply.Descript",POut.String(findText))+") ";
    //	}
    //	if(!includeHidden){
    //		command+="AND supply.IsHidden=0 ";
    //	}
    //	command+="ORDER BY definition.ItemOrder,supply.ItemOrder";
    //	return Crud.SupplyCrud.SelectMany(command);
    //}
    /**
    * Gets all Supplies, ordered by category and itemOrder.
    */
    public static List<Supply> getAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Supply>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT supply.* FROM supply, definition " + "WHERE definition.DefNum=supply.Category " + "ORDER BY definition.ItemOrder,supply.ItemOrder";
        return Crud.SupplyCrud.SelectMany(command);
    }

    /**
    * Gets one supply from the database.  Used for display in SupplyOrderItemEdit window.
    */
    public static Supply getSupply(long supplyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Supply>GetObject(MethodBase.GetCurrentMethod(), supplyNum);
        }
         
        return Crud.SupplyCrud.SelectOne(supplyNum);
    }

    /**
    * Insert new item at bottom of category.
    */
    public static long insert(Supply supp) throws Exception {
        return Insert(supp, int.MaxValue);
    }

    /**
    * Inserts item at corresponding itemOrder. If item order is out of range, item will be placed at beginning or end of category.
    */
    public static long insert(Supply supp, int itemOrder) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            supp.SupplyNum = Meth.GetLong(MethodBase.GetCurrentMethod(), supp, itemOrder);
            return supp.SupplyNum;
        }
         
        String command = "";
        if (itemOrder < 0)
        {
            itemOrder = 0;
        }
        else
        {
            command = "SELECT MAX(ItemOrder) FROM supply WHERE Category=" + POut.long(supp.Category);
            int itemOrderMax = PIn.int(Db.getScalar(command));
            if (itemOrder > itemOrderMax)
            {
                itemOrder = itemOrderMax + 1;
            }
             
        } 
        //Set new item order.
        supp.ItemOrder = itemOrder;
        //move other items
        command = "UPDATE supply SET ItemOrder=(ItemOrder+1) WHERE Category=" + POut.long(supp.Category) + " AND ItemOrder>=" + POut.int(supp.ItemOrder);
        Db.nonQ(command);
        return Crud.SupplyCrud.Insert(supp);
    }

    //insert and return new supply
    /**
    * Standard update logic.
    */
    public static void update(Supply supp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), supp);
            return ;
        }
         
        Crud.SupplyCrud.Update(supp);
    }

    /**
    * Updates and also fixes item order issues that may arise from changing categories.
    */
    public static void update(Supply suppOld, Supply suppNew) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), suppOld, suppNew);
            return ;
        }
         
        String command = "";
        if (suppNew.ItemOrder < 0 || suppNew.ItemOrder == int.MaxValue)
        {
            command = "SELECT MAX(ItemOrder) FROM supply WHERE Category=" + POut.long(suppNew.Category);
            int itemOrderMax = PIn.int(Db.getScalar(command));
            if (suppNew.ItemOrder > itemOrderMax)
            {
                suppNew.ItemOrder = itemOrderMax + 1;
            }
             
        }
         
        //move other items
        if (suppNew.Category == suppOld.Category)
        {
            //move within same category
            command = "UPDATE supply SET ItemOrder=(ItemOrder+1)" + " WHERE Category=" + POut.long(suppNew.Category) + " AND ItemOrder>=" + POut.int(suppNew.ItemOrder) + " AND ItemOrder<=" + POut.int(suppOld.ItemOrder);
            Db.nonQ(command);
        }
        else
        {
            //may have overlapping item orders until suppNew is updated at the end of this function.
            //move between categories
            //Move all item orders down one form the old list
            command = "UPDATE supply SET ItemOrder=(ItemOrder-1)" + " WHERE Category=" + POut.long(suppOld.Category) + " AND ItemOrder>=" + POut.int(suppOld.ItemOrder);
            Db.nonQ(command);
            //Assuming item order is correct adjust new list item orders
            command = "UPDATE supply SET ItemOrder=(ItemOrder+1)" + " WHERE Category=" + POut.long(suppNew.Category) + " AND ItemOrder>=" + POut.int(suppNew.ItemOrder);
            Db.nonQ(command);
        } 
        Crud.SupplyCrud.Update(suppNew);
    }

    /**
    * Updates item orders only.  One query is run per supply item. If supplyOriginal is null will insert supplyNew.
    */
    public static void updateOrInsertIfNeeded(Supply supplyOriginal, Supply supplyNew) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), supplyOriginal, supplyNew);
            return ;
        }
         
        //if for some reason the SupplyNum didn't get set.
        if (supplyNew.getIsNew() || supplyNew.SupplyNum == 0 || supplyOriginal == null)
        {
            Crud.SupplyCrud.Insert(supplyNew);
        }
        else //probably wont happen but I'm not sure.
        if (!StringSupport.equals(supplyOriginal.CatalogNumber, supplyNew.CatalogNumber) || supplyOriginal.Category != supplyNew.Category || !StringSupport.equals(supplyOriginal.Descript, supplyNew.Descript) || supplyOriginal.IsHidden != supplyNew.IsHidden || supplyOriginal.ItemOrder != supplyNew.ItemOrder || supplyOriginal.LevelDesired != supplyNew.LevelDesired || supplyOriginal.Price != supplyNew.Price || supplyOriginal.SupplierNum != supplyNew.SupplierNum)
        {
            Crud.SupplyCrud.Update(supplyNew);
        }
          
    }

    //No update or insert needed.
    /**
    * Removes gaps and overlaps in item orders with a single call to the DB. Example: 1,2,5,5,13 becomes 0,1,2,3,4; the overlaps are sorted arbitrarily.
    */
    //		public static void NormalizeItemOrders() {
    //			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    //				Meth.GetVoid(MethodBase.GetCurrentMethod());
    //				return;
    //			}
    //			string command="SET @newOrder=0";
    //			Db.NonQ(command);
    //			command=@"SELECT @newOrder:=@newOrder+1 AS newOrder, t.supplynum, t.*
    //								FROM
    //									(SELECT supply.*
    //									FROM supply
    //									LEFT JOIN definition ON supply.category=definition.defnum
    //									ORDER BY definition.itemorder,supply.itemorder) t";
    //			Db.NonQ(command);
    //		}
    /**
    * Surround with try-catch.
    */
    public static void deleteObject(Supply supp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), supp);
            return ;
        }
         
        //validate that not already in use.
        String command = "SELECT COUNT(*) FROM supplyorderitem WHERE SupplyNum=" + POut.long(supp.SupplyNum);
        int count = PIn.int(Db.getCount(command));
        if (count > 0)
        {
            throw new ApplicationException(Lans.g("Supplies","Supply is already in use on an order. Not allowed to delete."));
        }
         
        Crud.SupplyCrud.Delete(supp.SupplyNum);
        command = "UPDATE supply SET ItemOrder=(ItemOrder-1) WHERE Category=" + POut.long(supp.Category) + " AND ItemOrder>=" + POut.int(supp.ItemOrder);
        Db.nonQ(command);
    }

    /**
    * Loops through the supplied list and verifies that the ItemOrders are not corrupted.  If they are, then it fixes them.  Returns true if fix was required.  It makes a few assumptions about the quality of the list supplied.  Specifically, that there are no missing items, and that categories are grouped and sorted.
    */
    public static boolean cleanupItemOrders(List<Supply> listSupply) throws Exception {
        //No need to check RemotingRole; no call to db.
        long catCur = -1;
        int previousOrder = -1;
        boolean retVal = false;
        for (int i = 0;i < listSupply.Count;i++)
        {
            if (listSupply[i].Category != catCur)
            {
                //start of new category
                catCur = listSupply[i].Category;
                previousOrder = -1;
            }
             
            if (listSupply[i].ItemOrder != previousOrder + 1)
            {
                listSupply[i].ItemOrder = previousOrder + 1;
                Update(listSupply[i]);
                retVal = true;
            }
             
            previousOrder++;
        }
        return retVal;
    }

    /**
    * Gets from the database the next available itemOrder for the specified category.  Returns 0 if no items found in category.
    */
    public static int getNextItemOrder(long catNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), catNum);
        }
         
        String command = "SELECT MAX(ItemOrder+1) FROM supply WHERE Category=" + POut.long(catNum);
        try
        {
            return PIn.int(Db.getScalar(command));
        }
        catch (Exception ex)
        {
            return 0;
        }
    
    }

}


/**
* //Deprecated.  Gets from the database the last itemOrder for the specified category.  Used to send un unhidden supply to the end of the list.
*/
//public static int GetLastItemOrder(long supplierNum,long catNum) {
//	if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
//		return Meth.GetInt(MethodBase.GetCurrentMethod(),supplierNum,catNum);
//	}
//	string command="SELECT MAX(ItemOrder) FROM supply WHERE SupplierNum="+POut.Long(supplierNum)
//		+" AND Category="+POut.Long(catNum);// +" AND IsHidden=0";
//	DataTable table=Db.GetTable(command);
//	if(table.Rows.Count==0){
//		return -1;
//	}
//	return PIn.Int(table.Rows[0][0].ToString());
//}