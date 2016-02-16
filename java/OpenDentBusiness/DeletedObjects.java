//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:39 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.DeletedObject;
import OpenDentBusiness.DeletedObjectType;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Mobile.Crud.AppointmentmCrud;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class DeletedObjects   
{
    /**
    * 
    */
    public static void setDeleted(DeletedObjectType objType, long objectNum) throws Exception {
        DeletedObject delObj = new DeletedObject();
        delObj.ObjectNum = objectNum;
        delObj.ObjectType = objType;
        Crud.DeletedObjectCrud.Insert(delObj);
    }

    public static List<DeletedObject> getDeletedSince(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<DeletedObject>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT * FROM deletedobject WHERE DateTStamp > " + POut.dateT(changedSince);
        return Crud.DeletedObjectCrud.SelectMany(command);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceDeletedObjectNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT DeletedObjectNum FROM deletedobject WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> deletedObjectnums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            deletedObjectnums.Add(PIn.Long(dt.Rows[i]["DeletedObjectNum"].ToString()));
        }
        return deletedObjectnums;
    }

    public static List<DeletedObject> getMultDeletedObjects(List<long> deletedObjectNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<DeletedObject>>GetObject(MethodBase.GetCurrentMethod(), deletedObjectNums);
        }
         
        String strDeletedObjectNums = "";
        DataTable table = new DataTable();
        if (deletedObjectNums.Count > 0)
        {
            for (int i = 0;i < deletedObjectNums.Count;i++)
            {
                if (i > 0)
                {
                    strDeletedObjectNums += "OR ";
                }
                 
                strDeletedObjectNums += "DeletedObjectNum='" + deletedObjectNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM deletedobject WHERE " + strDeletedObjectNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        DeletedObject[] multDeletedObjects = Crud.DeletedObjectCrud.TableToList(table).ToArray();
        List<DeletedObject> deletedObjectList = new List<DeletedObject>(multDeletedObjects);
        return deletedObjectList;
    }

    /**
    * This is only run at the server for the mobile db. Deleted patients are not handled here because patients never get deleted.
    */
    public static void deleteForMobile(List<DeletedObject> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            //mobile
            if (list[i].ObjectType == DeletedObjectType.Appointment)
            {
                AppointmentmCrud.Delete(customerNum, list[i].ObjectNum);
            }
             
        }
    }

}


