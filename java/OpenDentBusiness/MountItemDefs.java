//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class MountItemDefs   
{
    /*
    		///<summary>A list of all MountItemDefs.</summary>
    		public static List<MountItemDef> Listt;
    		///<summary>Gets a list of all MountItemDefs when program first opens.</summary>
    		public static void Refresh() {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod());
    				return;
    			}
    			string command="SELECT * FROM mountitemdef";
    			DataTable table=Db.GetTable(command);
    			Listt=new List<MountItemDef>();
    			MountItemDef mount;
    			for(int i=0;i<table.Rows.Count;i++) {
    				mount=new MountItemDef();
    				mount.MountItemDefNum=PIn.PInt   (table.Rows[i][0].ToString());
    				mount.MountDefNum    =PIn.PInt   (table.Rows[i][1].ToString());
    				mount.Xpos           =PIn.PInt   (table.Rows[i][2].ToString());
    				mount.Ypos           =PIn.PInt   (table.Rows[i][3].ToString());
    				mount.Width          =PIn.PInt   (table.Rows[i][4].ToString());
    				mount.Height         =PIn.PInt   (table.Rows[i][5].ToString());
    				Listt.Add(mount);
    			}
    		}*/
    /*
    		///<summary></summary>
    		public static void Update(MountItemDef def) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),def);
    			}
    			Crud.MountItemDefCrud.Update(def);
    		}
    		///<summary></summary>
    		public static long Insert(MountItemDef def) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				def.MountItemDefNum=Meth.GetLong(MethodBase.GetCurrentMethod(),def);
    				return def.MountItemDefNum;
    			}
    			return Crud.MountItemDefCrud.Insert(def);
    		}*/
    /**
    * No need to surround with try/catch, because all deletions are allowed.
    */
    public static void delete(long mountItemDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), mountItemDefNum);
            return ;
        }
         
        String command = "DELETE FROM mountitemdef WHERE MountItemDefNum=" + POut.long(mountItemDefNum);
        Db.nonQ(command);
    }

}


