//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemsGeneratorInterface;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_ODSQLPullStrings_LedgerItem;

/*=============================================================================================================
Open Dental is a dental practice management program.
Copyright (C) 2003,2004,2005,2006,2007  Jordan Sparks, DMD.  http://www.open-dent.com,  http://www.docsparks.com
This program is free software; you can redistribute it and/or modify it under the terms of the
GNU Db Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.
This program is distributed in the hope that it will be useful, but without any warranty. See the GNU Db Public License
for more details, available at http://www.opensource.org/licenses/gpl-license.php
Any changes to this program must follow the guidelines of the GPL license if a modified version is to be
redistributed.
 * 
 * Original Author of this file is Daniel W. Krueger, DDS
===============================================================================================================*/
/**
* A place to consolidate the queries for pulling data from opendental.
* A collection of classes that provide different sements of the SQL querry that pulls the different
* opendental tables for the data we need to reconstruct a Ledger for a specific guarantor.
* 
* Call  LedgerItemsRaw(guarantor, IsPulled) to return a datatable with Structure Defined in Columns
*/
public class LedgerItemsGenerator   implements LedgerItemsGeneratorInterface
{
    public static final int IndexofODItemNum = 5;
    public static final int IndexofODTableSource = 6;
    /**
    * The columns in the data structor
    */
    public static final String[] Columns = { "LedgerItemType", "Guarantor", "PatientNum", "ProviderNum", "ItemDate", "Amount", "ODItemNum", "TableSource" };
    // TablesSource is TableUtility.AvailableTables
    private String _MySqlConnectionString = "";
    public enum ColumnTypesPulledFromOD
    {
        //private string _MyOracleConnectionString = ""; // Not implemented yet
        Guarantor,
        PatientNum,
        ProviderNum,
        ItemDate,
        Amount,
        ODItemNum,
        TableSource
    }
    public enum ODTablesPulled
    {
        /**
        * Used to distiquish what is the basic payment type.
        * 
        * The tables from which data is pulled.  Put into an enumaration to minimize misspelling
        */
        ProcedureLog,
        Adjustment,
        ClaimProc,
        PaySplit
    }
    public LedgerItemsGenerator(String MySqlConnectionString1) throws Exception {
        this._MySqlConnectionString = MySqlConnectionString1;
    }

    public LedgerItemsGenerator() throws Exception {
    }

    /**
    * Provides a string that will pull ledger items for a specific guarantor.  If Guarantor is 0 it
    * will attempt to pull all items for all guarantors.
    * 
    *  @param Guarantor 
    *  @param IsPulledFlag 
    *  @return
    */
    public String oDPullString(int Guarantor, boolean IsPulledFlag) throws Exception {
        throw new Exception("Method not Implemented Yet");
    }

    //return ""; // just used to override
    public DataTable ledgerItemsRaw(int Guarantor, boolean IsPulledStatus) throws Exception {
        DataTable rValdtWorking = new DataTable();
        MySqlConnection con = new MySqlConnection(this._MySqlConnectionString);
        try
        {
            con.Open();
            MySqlDataAdapter da = new MySqlDataAdapter(PP_ODSQLPullStrings_LedgerItem.PullAll_FromOD(Guarantor, IsPulledStatus), con);
            da.Fill(rValdtWorking);
        }
        catch (Exception exc)
        {
            exc.ToString();
        }
        finally
        {
            if (con.State == ConnectionState.Open)
                con.Close();
             
        }
        for (int i = 0;i < Columns.Length;i++)
            // If exception is thrown here I want to know it because it means my columns array is not right.
            rValdtWorking.Columns[i].ColumnName = Columns[i];
        return rValdtWorking;
    }

    /**
    * Returns the Number of Guarantors
    * 
    *  @param Guarantor 
    *  @param IsPulledStatus 
    *  @return
    */
    public int ledgerItemsRawCount(int Guarantor, boolean IsPulledStatus) throws Exception {
        DataTable rValdtWorking = new DataTable();
        int ItemsInRawQuery = -1;
        MySqlConnection con = new MySqlConnection(this._MySqlConnectionString);
        try
        {
            con.Open();
            String command = PP_ODSQLPullStrings_LedgerItem.PullAll_FromOD(Guarantor, IsPulledStatus);
            command = "SELECT Count(Distinct (tb1.Guarantor)) FROM ( " + command + ") as tb1";
            MySqlDataAdapter da = new MySqlDataAdapter(command, con);
            da.Fill(rValdtWorking);
            if (rValdtWorking != null && rValdtWorking.Rows.Count != 0 && rValdtWorking.Columns.Count != 0)
                if (rValdtWorking.Rows[0][0] != null)
                    ItemsInRawQuery = Int32.Parse(rValdtWorking.Rows[0][0].ToString());
                 
             
        }
        catch (Exception exc)
        {
            exc.ToString();
        }
        finally
        {
            if (con.State == ConnectionState.Open)
                con.Close();
             
        }
        return ItemsInRawQuery;
    }

    /**
    * The IsPulled Condtion was used to minimze the ammount of data that needed to be
    * accessed for reallocation by only dealling with new entries.  Currently We are not
    * using it but put it here in case I need it when I migrate it over to Opendental.
    * 
    *  @param IsPulled 
    *  @return
    */
    protected String sQL_IsPulledCondition(ODTablesPulled table, boolean IsPulled) throws Exception {
        String rVal = "";
        if (IsPulled)
            throw new Exception("Method Not Implemented Yet");
         
        return rVal;
    }

}


//Below is modified
//rVal += String.Format("\n    && {0}.{1} = {2}", table.ToString().ToLower(), TableUtility.ColumnName_IsPulledToDansLedger, (IsPulled ? 1 : 0));
//old version
//if (IsPulledFlag)
//    rVal += "\n    && adjustment." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//else
//    rVal += "\n    && adjustment." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";