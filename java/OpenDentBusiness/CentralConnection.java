//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CentralConnection;
import OpenDentBusiness.TableBase;

/**
* Used by the Central Manager.  Stores the information needed to establish a connection to a remote database.
*/
public class CentralConnection  extends TableBase 
{
    /**
    * Primary key.
    */
    public long CentralConnectionNum = new long();
    /**
    * If direct db connection.  Can be ip address.
    */
    public String ServerName = new String();
    /**
    * If direct db connection.
    */
    public String DatabaseName = new String();
    /**
    * If direct db connection.
    */
    public String MySqlUser = new String();
    /**
    * If direct db connection.  Symmetrically encrypted.
    */
    public String MySqlPassword = new String();
    /**
    * If connecting to the web service. Can be on VPN, or can be over https.
    */
    public String ServiceURI = new String();
    /**
    * If connecting to the web service.
    */
    public String OdUser = new String();
    /**
    * If connecting to the web service.  Symmetrically encrypted.
    */
    public String OdPassword = new String();
    /**
    * .
    */
    public String Note = new String();
    /**
    * 0-based.
    */
    public int ItemOrder = new int();
    /**
    * If set to true, the password hash is calculated differently.
    */
    public boolean WebServiceIsEcw = new boolean();
    /**
    * Returns a copy.
    */
    public CentralConnection copy() throws Exception {
        return (CentralConnection)this.MemberwiseClone();
    }

}


