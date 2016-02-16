//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed;

public class ODTablesUsed   
{
    public enum ODTablesPulled
    {
        /**
        * The tables from which data is pulled to go into DansLedger
        */
        ProcedureLog,
        Adjustment,
        ClaimProcPayment,
        ClaimProcWriteOff,
        PaySplit,
        Payment
    }
    private static final List<ODTablesUsed> _TablesUsed = new List<ODTablesUsed>();
    private static String[] _ApprovedOD_Versions = { "5.1.0.0" };
    /**
    * Throws Exception.  Usefull for unimplemented methods.  Would like to add reflection.
    */
    void throwException() throws Exception {
        throw new Exception("Method Not Implemented Yet");
    }

    void throwException(String message) throws Exception {
        throw new Exception(message);
    }

    /**
    * Returns true if table is in the expected state to use with this dll.
    */
    public boolean tableStateValid(ODTablesUsed table) throws Exception {
        boolean rVal = false;
        throwException();
        return rVal;
    }

    /**
    * Checks for the state of all tables in the _TablesUsed
    * and the dll is access from _ApprovedOD_Versions.  Just to make sure
    * dll is safe to run.  Put this here because of the very dynamic
    * state of opendental that if columns and tables get moved arround
    * this dll will become unstable creating bad things.
    */
    public boolean dllSafeToRun() throws Exception {
        boolean rVal = false;
        // check tables
        // check versions
        throwException();
        return rVal;
    }

    /**
    * This is the main point where the tables used will be be built.
    */
    private static void buildStaticTableList() throws Exception {
        String name = "ProcedureLog";
        String[] columns;
        Type[] types1;
        ODTablesUsed table1 = new ODTablesUsed(name, columns, types1);
    }

    //public enum ODTablesPulled { ProcedureLog, Adjustment, ClaimProc, PaySplit }
    private String _TableName = new String();
    private String[] _ColumnsUsed = null;
    private Type[] _ColumnTypes = null;
    /// <summary>
    /// <param name="name">the OpenDental Table name that is used</param>
    /// <param name="columns">the array of column names that are used</param>
    /// <param name="types">the types of the columns listed in columns</param>
    public ODTablesUsed(String name, String[] columns, Type[] types1) throws Exception {
        if (columns.Length != types1.Length)
            throwException("column count does not match type count in ODTablesUsed Constructor");
         
        if (columns == null || types1 == null)
            throwException("null refernces not allowed in ODTablesUsed Constructor");
         
        _TableName = name;
        _ColumnsUsed = columns;
        _ColumnTypes = types1;
    }

}


