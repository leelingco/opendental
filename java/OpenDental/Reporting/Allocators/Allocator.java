//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:23 PM
//

package OpenDental.Reporting.Allocators;

import CS2JNet.System.StringSupport;
import OpenDental.Reporting.Allocators.IAllocate;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;

/**
* The class that is to be inherited by anyone creating an allocator.
*/
abstract public class Allocator   implements IAllocate
{
    protected String Description = "Explanation of what your allocator does";
    protected String Name = "Name of your Allocator";
    /**
    * The text that provides the help documentation for the user to see.
    * Pass it thru the Lan.F before you want to display it.
    */
    protected String HelpDoc = "No Helpdoc Available";
    /**
    * This is the name given to the table that will be used to hold the allocated
    * data.  Set by programer in inherited member.
    */
    protected String DbaseStorageTable = "";
    /**
    * The column names used for the DbaseStorageTable
    */
    protected String[] DbaseTableColumns = null;
    public Allocator(String TableName, String[] Columns) throws Exception {
        SetDbaseTable_and_Columns(TableName, Columns);
    }

    public Allocator() throws Exception {
    }

    public abstract boolean allocate(int iGuarantor) throws Exception ;

    //{
    //    throw new Exception("The method or operation is not implemented.");
    //}
    public abstract boolean deAllocate(int iGuarantor) throws Exception ;

    //{
    //    throw new Exception("The method or operation is not implemented.");
    //}
    /**
    * Designed to be called by the constructor to set the tableName and
    * column definitions of the allocator.  I really wanted this member
    * to be protected not public.
    */
    private void setDbaseTable_and_Columns(String tableName, String[] Columns) throws Exception {
        this.DbaseStorageTable = tableName;
        this.DbaseTableColumns = Columns;
    }

    /**
    * Tries to select 1 row of elements with each of the columns stated in
    * DbaseTableColumns.  Code was faster than "DESCRIBE Table";
    * Plus I'm not checking field types.
    * 
    *  @return false if querry throws an exception.
    */
    boolean checkDbase() throws Exception {
        boolean rVal = true;
        String cmd = "SELECT ";
        for (int i = 0;i < DbaseTableColumns.Length;i++)
        {
            cmd += DbaseTableColumns[i];
            if (i < DbaseTableColumns.Length - 1)
                cmd += ", ";
             
        }
        cmd += " FROM " + DbaseStorageTable + " " + DbHelper.limitWhere(1);
        try
        {
            Db.getTableOld(cmd);
        }
        catch (Exception __dummyCatchVar0)
        {
            rVal = false;
        }

        return rVal;
    }

    /**
    * Uses "SHOW TABLES" to see if table exists.
    * 
    *  @return
    */
    boolean tableExists() throws Exception {
        boolean rVal = false;
        String cmd = "SHOW TABLES";
        String thistable = this.DbaseStorageTable.ToLower();
        try
        {
            DataTable dt = Db.getTableOld(cmd);
            if (dt.Rows.Count != 0)
            {
                for (int i = 0;i < dt.Rows.Count;i++)
                {
                    String tblname = dt.Rows[i][0].ToString().ToLower();
                    if (StringSupport.equals(thistable, tblname))
                    {
                        i = dt.Rows.Count;
                        rVal = true;
                    }
                     
                }
            }
             
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        return rVal;
    }

}


