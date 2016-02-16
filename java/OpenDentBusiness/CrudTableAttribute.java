//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;


public class CrudTableAttribute  extends Attribute 
{
    public CrudTableAttribute() throws Exception {
        this.tableName = "";
        this.isDeleteForbidden = false;
        this.isMissingInGeneral = false;
        this.isMobile = false;
    }

    private String tableName = new String();
    /**
    * If tablename is different than the lowercase class name.
    */
    public String getTableName() throws Exception {
        return tableName;
    }

    public void setTableName(String value) throws Exception {
        tableName = value;
    }

    private boolean isDeleteForbidden = new boolean();
    /**
    * Set to true for tables where rows are not deleted.
    */
    public boolean getIsDeleteForbidden() throws Exception {
        return isDeleteForbidden;
    }

    public void setIsDeleteForbidden(boolean value) throws Exception {
        isDeleteForbidden = value;
    }

    private boolean isMissingInGeneral = new boolean();
    /**
    * Set to true for tables that are part of internal tools and not found in the general release.  The Crud generator will gracefully skip these tables if missing from the database that it's running against.  It also won't try to generate a dataInterface s class.
    */
    public boolean getIsMissingInGeneral() throws Exception {
        return isMissingInGeneral;
    }

    public void setIsMissingInGeneral(boolean value) throws Exception {
        isMissingInGeneral = value;
    }

    private boolean isMobile = new boolean();
    /**
    * Set to true for tables that are used on server for mobile services.  These are 'lite' versions of the main tables, and end with m.  A composite primary key will be expected.  The Crud generator will generate these crud files in a different place than the other crud files.  It will also generate the dataInterface 'ms' class to a different location.  It also won't validate that the table exists in the test database.
    */
    public boolean getIsMobile() throws Exception {
        return isMobile;
    }

    public void setIsMobile(boolean value) throws Exception {
        isMobile = value;
    }

}


