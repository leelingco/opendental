//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* A company that provides supplies for the office, typically dental supplies.
*/
public class Supplier  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SupplierNum = new long();
    /**
    * .
    */
    public String Name = new String();
    /**
    * .
    */
    public String Phone = new String();
    /**
    * The customer ID that this office uses for transactions with the supplier
    */
    public String CustomerId = new String();
    /**
    * Full address to website.  We might make it clickable.
    */
    public String Website = new String();
    /**
    * The username used to log in to the supplier website.
    */
    public String UserName = new String();
    /**
    * The password to log in to the supplier website.  Not encrypted or hidden in any way.
    */
    public String Password = new String();
    /**
    * Any note regarding supplier.  Could hold address, CC info, etc.
    */
    public String Note = new String();
}


