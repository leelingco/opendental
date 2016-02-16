//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ReplicationServer;
import OpenDentBusiness.TableBase;

/**
* If replication is being used, then this stores information about each server.  Each row is one server.
*/
public class ReplicationServer  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ReplicationServerNum = new long();
    /**
    * The description or name of the server.  Optional.
    */
    public String Descript = new String();
    /**
    * Db admin sets this server_id server variable on each replication server.  Allows us to know what server each workstation is connected to.  In display, it's ordered by this value.  Users are always forced to enter a value here.
    */
    public int ServerId = new int();
    /**
    * The start of the key range for this server.  0 if no value entered yet.
    */
    public long RangeStart = new long();
    /**
    * The end of the key range for this server.  0 if no value entered yet.
    */
    public long RangeEnd = new long();
    /**
    * The AtoZpath for this server. Optional.
    */
    public String AtoZpath = new String();
    /**
    * If true, then this server cannot initiate an update.  Typical for satellite servers.
    */
    public boolean UpdateBlocked = new boolean();
    /**
    * The description or name of the comptuer that will monitor replication for this server.
    */
    public String SlaveMonitor = new String();
    public ReplicationServer copy() throws Exception {
        return (ReplicationServer)this.MemberwiseClone();
    }

}


