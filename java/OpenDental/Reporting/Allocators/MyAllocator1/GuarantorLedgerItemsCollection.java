//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:23 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import CS2JNet.System.StringSupport;
import OpenDental.Reporting.Allocators.MyAllocator1.Guarantors;
import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemTypes;
import OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_LedgerItem;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_ODSQLPullStrings_LedgerItem;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_PaymentItem;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_PaySplitItem;
import OpenDental.Reporting.Allocators.MyAllocator1.PU;
import OpenDentBusiness.Db;

public class GuarantorLedgerItemsCollection   
{
    private Guarantors m_Guarantors = null;
    private long m_GuarantorNumber = new long();
    private List<PP_LedgerItem> m_FullLedgerList = new List<PP_LedgerItem>();
    private List<PP_LedgerItem> m_ChargesAndRefundsList = new List<PP_LedgerItem>();
    private List<PP_PaymentItem> m_PaymentsAndAdjustList = new List<PP_PaymentItem>();
    private List<PP_LedgerItem> m_MarkedForDeletion = new List<PP_LedgerItem>();
    private List<PP_PaymentItem> m_PaymentItemsList = new List<PP_PaymentItem>();
    //private List<PP_PaySplitItem> m_PaymentItems_AfterSplit = null;//new List<PP_PaymentItem>();
    private boolean m_isEqualized = false;
    private boolean m_isFilled = false;
    public GuarantorLedgerItemsCollection(Guarantors Guarantor) throws Exception {
        this.m_Guarantors = Guarantor;
        this.m_GuarantorNumber = this.m_Guarantors.getNumber();
    }

    public GuarantorLedgerItemsCollection(long GuarantorNumber) throws Exception {
        this.m_GuarantorNumber = GuarantorNumber;
    }

    /**
    * Throws exception if Guarantor_Object was not set. ie using uint Guarantor Numbers instead
    */
    public Guarantors getGUARANTOR_OBJECT() throws Exception {
        if (this.m_Guarantors == null)
            throw new Exception("Gurarantor Object is null.\nObject was instantated with different contstructor.\n" + " Consider Calling Property GuarantorNumber instead.");
         
        return this.m_Guarantors;
    }

    /**
    * Can be used if you don't want to use the whole DansTables.Guarantors class. To save memory.
    */
    public long getGUARANTOR_NUMBER() throws Exception {
        return this.m_GuarantorNumber;
    }

    /**
    * Includes all LedgerItems.  Readonly
    */
    // Consider returning the combined results of Payments and Charges.
    // My concern is that the order of things may be affected
    public List<PP_LedgerItem> getFullLedger() throws Exception {
        return this.m_FullLedgerList;
    }

    /**
    * Includes all Payments and Adjustments on account. Readonly
    */
    public List<PP_PaymentItem> getPayments() throws Exception {
        return this.m_PaymentsAndAdjustList;
    }

    /**
    * Includes all Charges and Refunds on account. Readonly
    */
    public List<PP_LedgerItem> getCharges() throws Exception {
        return this.m_ChargesAndRefundsList;
    }

    /**
    * Looks thru entire ledger and returns a list of providers.  This is iterative so may not
    * want to use it if you are already cycling thru the ledger.
    */
    public int[] getProviders() throws Exception {
        List<int> provs = new List<int>();
        if (m_FullLedgerList != null && m_FullLedgerList.Count != 0)
            for (Object __dummyForeachVar0 : m_FullLedgerList)
            {
                PP_LedgerItem pli = (PP_LedgerItem)__dummyForeachVar0;
                if (!provs.Contains(pli.getPROVNUM()))
                    provs.Add(pli.getPROVNUM());
                 
            }
         
        return provs.ToArray();
    }

    public boolean getIS_EQUALIZED() throws Exception {
        return m_isEqualized;
    }

    public boolean getIS_FILLED() throws Exception {
        return m_isFilled;
    }

    /**
    * Just makes a datatable of the Full Ledger list of items
    * 
    *  @return
    */
    public DataTable viewLedgerObject(LedgerItemTypes[] lit) throws Exception {
        DataTable dtLookAtLedger = new DataTable();
        dtLookAtLedger.Columns.Add("Guarantor");
        dtLookAtLedger.Columns.Add("IsAllocated");
        dtLookAtLedger.Columns.Add("ProvNum");
        dtLookAtLedger.Columns.Add("ItemDate");
        dtLookAtLedger.Columns.Add("PatNum");
        dtLookAtLedger.Columns.Add("ProcNum");
        dtLookAtLedger.Columns.Add("ODTableSource");
        dtLookAtLedger.Columns.Add("ItemNum");
        dtLookAtLedger.Columns.Add("ItemType");
        dtLookAtLedger.Columns.Add("Amount");
        for (Object __dummyForeachVar1 : this.getFullLedger())
        {
            //dtLookAtLedger.Columns.Add("
            //dtLookAtLedger.Columns.Add("
            PP_LedgerItem pp1 = (PP_LedgerItem)__dummyForeachVar1;
            boolean ShowItem = false;
            for (int i = 0;i < lit.Length;i++)
                if (pp1.getITEMTYPE() == lit[i])
                {
                    ShowItem = true;
                    i = lit.Length;
                }
                 
            if (ShowItem)
            {
                DataRow newRow = dtLookAtLedger.NewRow();
                newRow["Guarantor"] = pp1.getGUARANTOR().ToString();
                newRow["IsAllocated"] = pp1.getIS_ALLOCATED().ToString();
                newRow["ProvNum"] = pp1.getPROVNUM().ToString();
                newRow["ItemDate"] = pp1.getITEMDATE().ToString();
                newRow["PatNum"] = pp1.getPATNUM().ToString();
                newRow["ProcNum"] = pp1.getPROCNUM().ToString();
                newRow["ODTableSource"] = pp1.getOD_TABLESOURCE().ToString();
                newRow["ItemNum"] = pp1.getITEMNUM().ToString();
                newRow["ItemType"] = pp1.getITEMTYPE().ToString();
                newRow["Amount"] = pp1.getAMMOUNT();
                dtLookAtLedger.Rows.Add(newRow);
                if (pp1 instanceof PP_PaymentItem)
                {
                    PP_PaymentItem pyI = (PP_PaymentItem)pp1;
                    if (pyI.getPAYMENT_SPLITS() != null)
                    {
                        for (int j = 0;j < pyI.getPAYMENT_SPLITS().Count;j++)
                        {
                            DataRow newRow2 = dtLookAtLedger.NewRow();
                            newRow2["Guarantor"] = "\"";
                            newRow2["IsAllocated"] = "\"";
                            newRow2["ProvNum"] = pyI.getPAYMENT_SPLITS()[j].PROVNUM.ToString();
                            newRow2["ItemDate"] = "\"";
                            newRow2["PatNum"] = "\"";
                            newRow2["ProcNum"] = pyI.getPAYMENT_SPLITS()[j].ALLOCATED_FROM_NUM.ToString();
                            newRow2["ODTableSource"] = pyI.getPAYMENT_SPLITS()[j].ALLOCATED_FROM_TABLE.ToString();
                            newRow2["ItemNum"] = "";
                            newRow2["ItemType"] = "";
                            newRow2["Amount"] = pyI.getPAYMENT_SPLITS()[j].AMMOUNT.ToString();
                            dtLookAtLedger.Rows.Add(newRow2);
                        }
                    }
                     
                }
                 
            }
             
        }
        return dtLookAtLedger;
    }

    //end if (showitem)
    public DataTable viewLedgerObject(boolean ShowAll) throws Exception {
        return ViewLedgerObject(new LedgerItemTypes[]{ LedgerItemTypes.Charge, LedgerItemTypes.NegAdjustment, LedgerItemTypes.Payment, LedgerItemTypes.PosAdjustment });
    }

    /**
    * Fill the Ledger Object with data from the database.
    * Flushes old data in this object first
    * If you are running lots of data you may want to control the GC
    * to collect when you want.
    * 
    * Put in two methods for organization
    */
    public boolean fill(boolean Suppress_GC_Collection) throws Exception {
        return _Fill(Suppress_GC_Collection);
    }

    /**
    * The method that will equalize the payments for this Ledger's data.
    */
    public void equalizePaymentsV2() throws Exception {
        _EqualizePayments();
    }

    /**
    * Was orginally used to update the database with any changed values
    * 
    * Roadmap:
    * 1.  Create Variables to hold Splits that have actuall changed
    * i.	SplitsToDrop		Contain splits that are in the allocation_provider table that have been
    * determined to be old/outdated/incorrect or need changed.
    * ii.	SplitsToWrite		Contain the splits that need to be written to allocation_provider table
    * 2.	Grab all entries from allocation_provider
    * i.	dt2_AllocTbl		a datatable of the split items as they exist in the allocation_provider table
    * ii.	htCurrentRecord		a hashtable that is used to determine if the recorded data matches the data in this objects payment ledger items
    * Also used to determine if entries exist that are no longer a part of opendental. (meaning they have been deleted)
    * 3.	Cycle through this.Payments compare to entries of allocation_provider
    * i.		Add to SplitsToDrop and SplitsToWrite as appropriate
    * a.  Checks split count to match
    * b.	Checks provider count to match
    * c.	Calcuates provider balances to match (for the split entries only)
    * d.  If different drop and write (as indicated in 3.i. ) else leave alone
    * ii.		Marke htCurrentRecord items as "used" if there is an ODEntry corresponding to the record.
    * 4.	Delete entries not used in htCurrentRecord (ie data that has been deleted from OpenDental)
    * i.		makes use of the key values in htCurrentRecord
    * ii.		key = [tablenum]-[tableitemnum]
    * 5.	Delete and add entries that are in the SplitsToDrop,SplitsToWrite lists
    */
    private boolean update2() throws Exception {
        boolean rVal_Successful = true;
        1.ToString();
        // Just to break and inspect
        //PU.MB = "Method not implemented. Not Saving Provider Paysplit Data\n\n" + PU.Method;
        List<PP_PaymentItem> SplitsToDrop = new List<PP_PaymentItem>();
        // Drop entries that will be needing updating
        List<PP_PaymentItem> SplitsToWrite = new List<PP_PaymentItem>();
        // Write all newly allocated splits and any changed entries.
        /**
        * ////////////////////////////////////////////////////////////////////////////
        */
        //  Find all the payments in the split table currently
        /**
        * ////////////////////////////////////////////////////////////////////////////
        */
        String cmd2 = "SELECT PayTableSource, PaySourceNum, IsFullyAllocated, Amount, ProvNum FROM " + OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.TABLENAME + "\nWHERE Guarantor = " + this.getGUARANTOR_NUMBER();
        DataTable dt2_AllocTbl = Db.getTableOld(cmd2);
        System.Collections.Hashtable htCurrentRecord = new System.Collections.Hashtable();
        for (int i = 0;i < dt2_AllocTbl.Rows.Count;i++)
        {
            // key = [tablesource as int]-[SourceNum]
            String key = dt2_AllocTbl.Rows[i][0].ToString() + "-" + dt2_AllocTbl.Rows[i][1].ToString();
            //if (key == "5-6160")
            //    1.ToString();
            if (!htCurrentRecord.ContainsKey(key))
                htCurrentRecord[key] = "notused";
             
        }
        for (int i = 0;i < this.getPayments().Count;i++)
        {
            // Roadmap -- Check to see if payment key matches pulled string.
            // See if splits are recorded
            // Add splits to SPlitsToWrite
            // What will be the distinguishing factor between unrecorded and recorded.
            /**
            * 1. Existence of TableSource-SourceNum.  If exists then it has been written.  If not then has not.
            * 2. If Recorded ProvNum = 0 then split is considered unallocated.
            * 3. Need to check if any of the items in payment list have splits = 0;
            * 
            * Or
            * Recreate Payment Items
            * Check every one for differences.
            * Write every one.
            * 
            * I think the final decision is
            * 1. Write non existant entries
            * 2. Only update entries that have provNum =0
            * 3. Make the de-allocator delete the entries or update to ProvNum = 0;
            * -- Equalizer is not supposed to change existing allocations.
            */
            //	if (htCurrentRecord.Count != 0)
            //	DataTable dtv1 = ViewLedgerObject(new LedgerItemTypes[] { LedgerItemTypes.Payment });
            //if (this.Payments[i].PROCNUM == 161218)
            //    1.ToString();
            String key = ((int)this.getPayments()[i].OD_TABLESOURCE).ToString() + "-" + this.getPayments()[i].PROCNUM;
            if (!htCurrentRecord.ContainsKey(key))
            {
                // if key does not exist then has not been written.
                SplitsToWrite.Add(this.getPayments()[i]);
            }
            else
            {
                // Key does exist--meaning a record is recorded in the alloctable for this table and item num
                htCurrentRecord[key] = "used";
                // see if any of these records have provider = 0
                String subsetCondition = "PayTableSource=" + ((int)this.getPayments()[i].OD_TABLESOURCE).ToString() + " AND PaySourceNum=" + this.getPayments()[i].PROCNUM.ToString();
                DataRow[] draSubSet = dt2_AllocTbl.Select(subsetCondition);
                // Find split that matches subset item
                // does subset have ProvNum = 0?
                // do we delete all subset entries =0 && AllocateAs in this.Payments?
                // What signifies difference
                //	1. Different number of split entries
                //	2. If some any of entries have different ProvNum, ItemNum, Amount
                // Draw back of this approach is that any payment that is partially allocated will
                //		be droped and reallocated.  Could make a difference.  But getting around this
                //		is another programing logical issue.
                //		Accept the compromise:
                //	Another Question am I using the IsFullyAllocated Flag effectively?  Wouldn't ProvNum != 0
                //		Mean the same thing?  Or ProvNum =0 give contion to find Flag.  Probably so but logically
                //		it seems to fit in the puzzle so I am leaving it for now.  Plus for the most part
                //		the Equailizer should equalize the payments equally for the most part.
                if (draSubSet.Length == getPayments()[i].PAYMENT_SPLITS.Count)
                {
                    //same number of splits
                    // check for matches.
                    // Does Provider Count Match?
                    List<int> ProvsInPayments = new List<int>();
                    List<double> ProvsInPayments_Balances = new List<double>();
                    List<int> ProvsInSubSet = new List<int>();
                    List<double> ProvsInSubSet_Balances = new List<double>();
                    for (int j = 0;j < getPayments()[i].PAYMENT_SPLITS.Count;j++)
                    {
                        // Find the Provider Balances in Payments[i] add to ProvsInPayments_Balances
                        int Provider1 = getPayments()[i].PAYMENT_SPLITS[j].PROVNUM;
                        if (ProvsInPayments.Contains(Provider1))
                            ProvsInPayments_Balances[ProvsInPayments.IndexOf(Provider1)] += getPayments()[i].PAYMENT_SPLITS[j].AMMOUNT;
                        else
                        {
                            ProvsInPayments.Add(Provider1);
                            ProvsInPayments_Balances.Add(0);
                            ProvsInPayments_Balances[ProvsInPayments.IndexOf(Provider1)] += getPayments()[i].PAYMENT_SPLITS[j].AMMOUNT;
                        } 
                    }
                    for (int k = 0;k < draSubSet.Length;k++)
                    {
                        // Find the Provider Balances Recorde in table associated with single payment entry
                        int provider2 = (int)draSubSet[k][4];
                        double amount = (double)draSubSet[k][3];
                        if (ProvsInSubSet.Contains(provider2))
                            ProvsInSubSet_Balances[ProvsInSubSet.IndexOf(provider2)] += amount;
                        else
                        {
                            ProvsInSubSet.Add(provider2);
                            ProvsInSubSet_Balances.Add(0);
                            ProvsInSubSet_Balances[ProvsInSubSet.IndexOf(provider2)] += amount;
                        } 
                    }
                    // Determine if they are an exact match
                    //    1. Split Count
                    //	  2. Provider balances are the same
                    boolean ExactMatch = ProvsInPayments.Count == ProvsInSubSet.Count;
                    // (don't add Payment to drop/write list)
                    if (ExactMatch)
                    {
                        for (int k = 0;k < ProvsInPayments.Count;k++)
                        {
                            int providerInQuestion = ProvsInPayments[k];
                            if (ProvsInSubSet.Contains(providerInQuestion) && ProvsInPayments.Contains(providerInQuestion))
                                ExactMatch = ExactMatch && ProvsInPayments_Balances[ProvsInPayments.IndexOf(providerInQuestion)] == ProvsInSubSet_Balances[ProvsInSubSet.IndexOf(providerInQuestion)];
                            else
                                ExactMatch = false; 
                            if (!ExactMatch)
                                k = ProvsInPayments.Count;
                             
                        }
                    }
                     
                    if (!ExactMatch)
                    {
                        SplitsToDrop.Add(this.getPayments()[i]);
                        SplitsToWrite.Add(this.getPayments()[i]);
                    }
                     
                }
                else
                {
                    // Note if it is an exact match then don't add the payment to the write list.  Just keep what
                    // is in the database.
                    // end if (draSubSet.Length == Payments[i].PAYMENT_SPLITS.Count)
                    // split length does not match
                    SplitsToDrop.Add(this.getPayments()[i]);
                    SplitsToWrite.Add(this.getPayments()[i]);
                } 
            } 
        }
        // end else
        // end for (int i = 0; i < this.Payments.Count; i++)
        // Find all the unused items in htCurrentRecord.  These indicate that OD had no data for
        // the items that were in the allocation provider table.
        List<String> keys2Delete = new List<String>();
        for (Object __dummyForeachVar2 : htCurrentRecord)
        {
            System.Collections.DictionaryEntry de = (System.Collections.DictionaryEntry)__dummyForeachVar2;
            String key2 = de.Key.ToString();
            if (StringSupport.equals(de.Value.ToString(), "notused"))
                keys2Delete.Add(key2);
             
        }
        try
        {
            //for (int i = 0; i < htCurrentRecord.Keys.Count; i++)
            //{
            //    string key2 = htCurrentRecord[ htCurrentRecord.Keys.i]];
            //    if (htCurrentRecord[key2].ToString() == "notused")
            //        keys2Delete.Add(key2);
            //}
            if (keys2Delete.Count != 0)
            {
                String dropUnusedCommand = "DELETE FROM " + OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.TABLENAME + " WHERE ";
                for (int i = 0;i < keys2Delete.Count;i++)
                {
                    String[] splitme = keys2Delete[i].Split(new char[]{ '-' });
                    LedgerItemTypes TableSource = (LedgerItemTypes)Int32.Parse(splitme[0]);
                    ulong SourceNum = ulong.Parse(splitme[1]);
                    dropUnusedCommand += "( PayTableSource = " + TableSource + " AND " + " PaySourceNum = " + SourceNum + ")";
                    if (i < keys2Delete.Count - 1)
                        dropUnusedCommand += "\nOR ";
                     
                }
                Db.nonQOld(dropUnusedCommand);
            }
             
        }
        catch (Exception exc)
        {
            String s2 = exc.ToString();
        }

        try
        {
            //Write the subset lists to DataBase
            String cmd = "";
            // Do Required Drops First.  These are drops where the allocation has physically changed.
            if (SplitsToDrop.Count != 0)
            {
                cmd = "DELETE FROM " + OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.TABLENAME + " WHERE \n";
                for (int i = 0;i < SplitsToDrop.Count;i++)
                {
                    cmd += "( PayTableSource = " + ((int)SplitsToDrop[i].OD_TABLESOURCE).ToString() + " AND " + " PaySourceNum = " + SplitsToDrop[i].PROCNUM + ")";
                    if (i < SplitsToDrop.Count - 1)
                        cmd += "\nOR ";
                     
                }
                Db.nonQOld(cmd);
            }
             
            // Add Spilt to AllocationTable
            if (SplitsToWrite.Count != 0)
            {
                cmd = OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.valueStringHeader() + "\n";
                for (int i = 0;i < SplitsToWrite.Count;i++)
                {
                    for (int j = 0;j < SplitsToWrite[i].PAYMENT_SPLITS.Count;j++)
                    {
                        // Header
                        //    (AllocType, Guarantor, ProvNum, "
                        //+ "PayTableSource, PaySourceNum, AllocToTableSource, AllocToSourceNum, Amount, IsFullyAllocated)
                        //if (SplitsToWrite[i].PROCNUM == 169218)
                        //    1.ToString();
                        // AllocType
                        // Guarantor
                        // ProvNum
                        // PayTableSource
                        // PaySourceNum
                        //AllocToTableSource
                        //AllocToSourceNum
                        //Amount
                        cmd += OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.ValueString(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.AllocationTypeID, SplitsToWrite[i].GUARANTOR, SplitsToWrite[i].PAYMENT_SPLITS[j].PROVNUM, ((int)SplitsToWrite[i].OD_TABLESOURCE), SplitsToWrite[i].PROCNUM, ((int)SplitsToWrite[i].PAYMENT_SPLITS[j].ALLOCATED_FROM_TABLE), SplitsToWrite[i].PAYMENT_SPLITS[j].ALLOCATED_FROM_NUM, SplitsToWrite[i].PAYMENT_SPLITS[j].AMMOUNT, SplitsToWrite[i].IS_ALLOCATED);
                        //IsFullyAllocated
                        if (j < SplitsToWrite[i].PAYMENT_SPLITS.Count - 1)
                            cmd += " , \n  ";
                         
                    }
                    if (i < SplitsToWrite.Count - 1 && SplitsToWrite[i].PAYMENT_SPLITS.Count != 0)
                        cmd += " , \n  ";
                     
                }
                Db.nonQOld(cmd);
            }
             
        }
        catch (Exception exc)
        {
            PU.setMB("Error in ProviderAllocation\nMessage: " + exc.Message + "\nMethod: " + PU.getMethod());
            rVal_Successful = false;
        }

        return rVal_Successful;
    }

    /**
    * Called by the Exposed Public Method with the similar name.  Just put
    * it here for organization.
    */
    private void _EqualizePayments() throws Exception {
        sortAllLists();
        OpenDental.Reporting.Allocators.MyAllocator1.GuarantorLedgerItemsCollection Ledger = this;
        List<PP_PaySplitItem> PaySplits = new List<PP_PaySplitItem>();
        List<PP_LedgerItem> ChargesAndRefundsList = Ledger.getCharges();
        List<PP_PaymentItem> PaymentsAndAdjustList = Ledger.getPayments();
        for (int j = 0;j < PaymentsAndAdjustList.Count;j++)
        {
            //List<PP_PaySplitItem> Splits = new List<PP_PaySplitItem>();
            // I just wanted to check to make sure my ducks are in a row.  I may take this out in the future
            //for (int j = 0; j < PaymentsAndAdjustList.Count; j++)
            //    if (PaymentsAndAdjustList[j].AmtUnallocated > 0)
            //    {
            //        if (PaymentsAndAdjustList[j].PATNUM != 3859)  //Stacia SchluttenHofer had a  -ve check
            //        throw new Exception("Figure Out Why Payment is +ve.");
            //    }
            //for (int j = 0; j < ChargesAndRefundsList.Count; j++)
            //    if (ChargesAndRefundsList[j].AmtUnallocated < 0)
            //        throw new Exception("Figure Out Why Charge is -ve.");
            // Allocate Adjustments First then Payments
            if (PaymentsAndAdjustList[j].ITEMTYPE != LedgerItemTypes.Payment && !PaymentsAndAdjustList[j].IS_ALLOCATED)
                for (int k = 0;k < ChargesAndRefundsList.Count;k++)
                {
                    //Should only be LedgerItemTypes.NegAdjustment
                    // Check to see if Providers are the same for the adjustment and the charge
                    // And that there is enough to allocate
                    if (ChargesAndRefundsList[k].AmtUnallocated > 0 && ChargesAndRefundsList[k].PROVNUM == PaymentsAndAdjustList[j].PROVNUM)
                    {
                        // Allocate amount
                        double a = ChargesAndRefundsList[k].AmtUnallocated;
                        double b = -PaymentsAndAdjustList[j].AmtUnallocated;
                        double AmmountToAllocate = (a - b > 0 ? b : a);
                        if (AmmountToAllocate == 0)
                            1.ToString();
                         
                        // 100 - +50 = +50 --- Allocate -50
                        // 100 - +60 = +40 --- Allocate -60
                        // 100 - +110 =  -10  --- Allocate -100
                        ChargesAndRefundsList[k].ALLOCATED_AMMOUNT += AmmountToAllocate;
                        PaymentsAndAdjustList[j].ALLOCATED_AMMOUNT -= AmmountToAllocate;
                        PP_PaySplitItem paysplit = new PP_PaySplitItem(-AmmountToAllocate, ChargesAndRefundsList[k].PROVNUM, ChargesAndRefundsList[k].PROCNUM, ChargesAndRefundsList[k].OD_TABLESOURCE);
                        //, PaymentsAndAdjustList[j].ITEMNUM);
                        PaymentsAndAdjustList[j].PAYMENT_SPLITS.Add(paysplit);
                        //Splits.Add(paysplit);
                        if (PaymentsAndAdjustList[j].AmtUnallocated == 0)
                            k = ChargesAndRefundsList.Count;
                         
                    }
                     
                }
             
        }
        // end charge lookup and get another payment
        // end if ChargeList has unallocated Ammount and Providers are the same
        //end for chargeList loop
        //if (PaymentsAndAdjustList[j].AmtUnallocated > 0)
        //{
        //		Only put code here if you want to have a split assigned if no carges for that provider
        //		are present.
        //}
        // end for PaymentListLoop
        int CurChargeIndex = 0;
        // use this so that you don't have to loop through each charge each time.
        //  Now just index past the item when done.
        boolean chargesLeft = CurChargeIndex < ChargesAndRefundsList.Count;
        for (int j = 0;j < PaymentsAndAdjustList.Count;j++)
        {
            //DataTable dtv1 = ViewLedgerObject(new LedgerItemTypes[] { LedgerItemTypes.Payment });
            int OverFlowCounter = 0;
            while (!PaymentsAndAdjustList[j].IS_ALLOCATED && chargesLeft == true)
            {
                OverFlowCounter++;
                if (ChargesAndRefundsList[CurChargeIndex].AmtUnallocated > 0)
                {
                    // Allocate amount
                    double a = ChargesAndRefundsList[CurChargeIndex].AmtUnallocated;
                    double b = -PaymentsAndAdjustList[j].AmtUnallocated;
                    double AmmountToAllocate = (a - b > 0 ? b : a);
                    if (AmmountToAllocate == 0)
                        2.ToString();
                     
                    // 100 + -50 = +50 --- Allocate -50
                    // 100 + -60 = +40 --- Allocate -60
                    // 100 + -110 =  -10  --- Allocate -100
                    //if (this.Payments[4] == PaymentsAndAdjustList[j])
                    //    1.ToString(); // to debug
                    ChargesAndRefundsList[CurChargeIndex].ALLOCATED_AMMOUNT += AmmountToAllocate;
                    PaymentsAndAdjustList[j].ALLOCATED_AMMOUNT -= AmmountToAllocate;
                    if (AmmountToAllocate != 0)
                    {
                        PP_PaySplitItem paysplit = new PP_PaySplitItem(-AmmountToAllocate, ChargesAndRefundsList[CurChargeIndex].PROVNUM, ChargesAndRefundsList[CurChargeIndex].PROCNUM, ChargesAndRefundsList[CurChargeIndex].OD_TABLESOURCE);
                        //, PaymentsAndAdjustList[j].ITEMNUM);
                        PaymentsAndAdjustList[j].PAYMENT_SPLITS.Add(paysplit);
                    }
                     
                }
                 
                //	Splits.Add(paysplit);
                if (ChargesAndRefundsList[CurChargeIndex].AmtUnallocated == 0)
                    CurChargeIndex++;
                 
                chargesLeft = CurChargeIndex < ChargesAndRefundsList.Count;
                if (OverFlowCounter > 100000)
                    throw new Exception("Problems with while loop in GuarantorLedgerItemColection.EqualizePaymentsV2()\n\n" + PU.getMethod());
                 
            }
            //end while(PaymentsAndAdjustList[j].AmtUnallocated > 0 && chargesLeft == true)
            // if there are no charges left to allocate and Payment has unallocated ammount then
            // make a paysplit entry for allocated amount.  Assign Split to Provider = 0.
            if (!PaymentsAndAdjustList[j].IS_ALLOCATED)
            {
                PP_PaySplitItem paysplit = new PP_PaySplitItem(PaymentsAndAdjustList[j].AmtUnallocated, 0, 0, 0);
                //, PaymentsAndAdjustList[j].ITEMNUM);
                PaymentsAndAdjustList[j].PAYMENT_SPLITS.Add(paysplit);
            }
             
        }
        //Splits.Add(paysplit);
        // end for
        // It is possible for a LedgerItem = 0 and I don't want to have to
        // Recycle throught these again so I am making these items as
        // flagged to indicate that they are considered allocated.
        //Indicate which items are fully allocated.
        //for (int i = 0; i < Ledger.FullLedger.Count; i++)
        //    if (Ledger.FullLedger[i].AmtUnallocated == 0)
        //        if (!Ledger.FullLedger[i].IS_ALLOCATED)
        //        {
        //            Ledger.FullLedger[i].IS_ALLOCATED = true; // Does it get here? It does if Ammount =0.  I think ALLOCATED_AMMOUNT should take care of them
        //        }
        //PU.MB = "Did not implent DansPaySplitTable.WritePaySplits(Splits); Splits will not be saved in database\n\n"+PU.Method;
        //DansPaySplitTable.WritePaySplits(Splits);
        //	DataTable dtv1 = ViewLedgerObject(true);
        //	DataTable dtv2 = ViewLedgerObject(new LedgerItemTypes[] { LedgerItemTypes.Payment });
        Ledger.update2();
        m_isEqualized = true;
    }

    /**
    * Used in EqualizePayments. Recursive Method
    */
    private double allocateToCharge(List<PP_LedgerItem> dliChargeRefundList, PP_LedgerItem dliPaymentItem) throws Exception {
        /**
        * /////////-----------> Recursive Method !!!
        */
        if (dliPaymentItem.getAmtUnallocated() == 0)
            return 0;
         
        double AmtAllocated = 0;
        PP_LedgerItem dliNext = NextUnallocatedItem(dliChargeRefundList);
        if (dliNext != null)
        {
            AmtAllocated = dliNext.addAllocation(dliPaymentItem.getITEMNUM(),dliPaymentItem.getAmtUnallocated());
            if (AmtAllocated != 0)
            {
                //    dliPaymentItem.PROVNUM = dliNext.PROVNUM;
                dliPaymentItem.AddAllocation(dliNext.getITEMNUM(), AmtAllocated, dliNext.getPROVNUM());
            }
            else
                dliNext.setIsAllocated_OLD(true); 
            // if nothing to allocate
            if (dliNext.getIsAllocated_OLD() == true)
                dliChargeRefundList.Remove(dliNext);
             
            if (dliPaymentItem.getAmtUnallocated() != 0)
            {
                // will loop infinately if no allocation occurs and Chargeitems are still left
                AmtAllocated += AllocateToCharge(dliChargeRefundList, dliPaymentItem);
            }
             
        }
         
        return AmtAllocated;
    }

    //recursion
    /**
    * used in AllocateToCharge
    * call stack EqualizePayments-->AllocateToCharge-->NextUnallocatedItem
    */
    private PP_LedgerItem nextUnallocatedItem(List<PP_LedgerItem> dliList) throws Exception {
        PP_LedgerItem rVal = null;
        for (int i = 0;i < dliList.Count;i++)
        {
            if (dliList[i].AmtUnallocated != 0)
            {
                // find first item that has some unallocated amount
                rVal = dliList[i];
                i = dliList.Count;
            }
             
        }
        return rVal;
    }

    /**
    * Adds to the full list
    * adds to chargesandrefunds list or paymentsandadjustments lists as
    * apporpriate.
    * 
    *  @param dli
    */
    private void addToCollection(PP_LedgerItem dli, PP_PaySplitItem[] splits) throws Exception {
        PP_LedgerItem item_to_add = dli;
        if (dli.getITEMTYPE() == LedgerItemTypes.Charge)
            m_ChargesAndRefundsList.Add(dli);
        else if (dli.getITEMTYPE() == LedgerItemTypes.Payment)
        {
            PP_PaymentItem ppi = dli.createPaymentItem();
            if (splits != null)
                ppi.getPAYMENT_SPLITS().AddRange(splits);
             
            if (ppi.getALLOCATED_AMMOUNT() != 0)
                1.ToString();
             
            for (int i = 0;i < ppi.getPAYMENT_SPLITS().Count;i++)
            {
                ppi.setALLOCATED_AMMOUNT(ppi.getALLOCATED_AMMOUNT() + ppi.getPAYMENT_SPLITS()[i].AMMOUNT);
            }
            item_to_add = ppi;
            m_PaymentsAndAdjustList.Add(ppi);
        }
        else if (dli.getITEMTYPE() == LedgerItemTypes.NegAdjustment)
            if (dli.getAMMOUNT() < 0)
            {
                PP_PaymentItem ppi = dli.createPaymentItem();
                item_to_add = ppi;
                m_PaymentsAndAdjustList.Add(ppi);
            }
            else
                m_ChargesAndRefundsList.Add(dli); 
        else //ie +ve adjusment reflects a refund
        if (dli.getITEMTYPE() == LedgerItemTypes.PosAdjustment)
            m_ChargesAndRefundsList.Add(dli);
            
        // ie treat positive adjustment just like a charge
        m_FullLedgerList.Add(item_to_add);
        this.m_isEqualized = false;
    }

    /**
    * Empties all the PP_LedgerItem lists
    */
    private void flush(boolean Suppress_GC_Collection) throws Exception {
        m_FullLedgerList.Clear();
        m_ChargesAndRefundsList.Clear();
        m_PaymentsAndAdjustList.Clear();
        m_MarkedForDeletion.Clear();
        m_PaymentItemsList.Clear();
        if (!Suppress_GC_Collection)
            GC.Collect();
         
    }

    /**
    * Fill the Ledger Object with data from the database.
    * Flushes old data in this object first
    * If you are running lots of data you may want to control the GC
    * to collect when you want.
    * 
    * 
    * 
    * Method Roadmap:
    * 
    * 1. Generate 2 Tables
    * i.		Table1:		dt_ODItems			Contains all the items of Ledger Interest from OpenDental Tables
    * ii.		Table2:		dt2_AllocTbl_Items	Contains all the Allocation Data that exists in the allocation_provider table
    * 2. Look For Differences
    * i.		Build a Ledger Item from the dt_ODItems (ie from the opendental tables)
    * ii.		Find the Splits in dt2_AllocTbl_Items that are associated with the dt_ODItems that are payments
    * a.		For Payment items (in dt_ODItems)Find he dt2_AllocTbl_Items that have the same PayTableSource and PaySourceNumber
    * b.		Make a split [] out of the dt2_AllocTbl_Items that were found
    * iii.	Add LI and Splits to MasterList  (this) with call to AddToCollection(LI,splits);
    * a.  The method will determine if LI is a payment and if splits are not null create a PP_PaymentItem and attach the splits to this PP_PaymentItem and then add the PP_PaymentItem to the master list
    * b.  Adding PaySplits needs to calculate the allocated ammount
    * 
    * Need to determine what to do if an item exists in the allocation_provider (ie dt2_AllocTble_Items) table but not in the
    * opendental data (ie dt_ODItems) // use Update2() to Clear these entries from data that
    * has been deleted from OD but not from allocation_provider.
    */
    private boolean _Fill(boolean Suppress_GC_Collection) throws Exception {
        //PU.Ex = "Not Implemented Yet Flag 6";
        flush(Suppress_GC_Collection);
        boolean rValSuccess = true;
        try
        {
            //	QueryResult qr = QueryResult.RunQuery(PP_ODSQLPullStrings_LedgerItem.PullAll_FromOD(m_GuarantorNumber, false));
            DataTable dt_ODItems = Db.getTableOld(PP_ODSQLPullStrings_LedgerItem.pullAll_FromOD(m_GuarantorNumber,false));
            //0 ,1
            // 2, 3, 4
            // 5, 6
            String cmd2 = "SELECT PayTableSource, PaySourceNum, " + " IsFullyAllocated, Amount, ProvNum, " + " AllocToTableSource, AllocToSourceNum  FROM " + OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.TABLENAME + "\nWHERE Guarantor = " + this.getGUARANTOR_NUMBER();
            DataTable dt2_AllocTbl_Items = Db.getTableOld(cmd2);
            if (dt_ODItems.Rows.Count != 0 && dt_ODItems.Columns.Count != 0)
            {
                for (int i = 0;i < dt_ODItems.Rows.Count;i++)
                {
                    //public enum HeaderOfQueryEnum
                    //    { LedgerItemType, Guarantor, PatNum,
                    //      ProvNum, ItemDate, Ammount,
                    //      ItemNum, TableSource	};
                    DataRow dr = dt_ODItems.Rows[i];
                    LedgerItemTypes type = (LedgerItemTypes)Int32.Parse(dr[0].ToString());
                    uint uiGuarantor = uint.Parse(dr[1].ToString());
                    uint uiPatnum = uint.Parse(dr[2].ToString());
                        ;
                    ushort usProvNum = ushort.Parse(dr[3].ToString());
                    DateTime ItemDate = (DateTime)dr[4];
                    // need to make sure this doesn't crash here.
                    Decimal dAmmount = Decimal.Parse(dr[5].ToString());
                    uint uiItemNum = uint.Parse(dr[6].ToString());
                        ;
                    OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled iTableEnum = (OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled)int.Parse(dr[7].ToString());
                    PP_LedgerItem LI = new PP_LedgerItem(type, uiGuarantor, uiPatnum, usProvNum, uiItemNum, ItemDate, dAmmount, 0, iTableEnum);
                    //if (uiItemNum == 95693)
                    //    1.ToString();
                    /**
                    * ////////////////////////////////////////////////////////////////
                    */
                    //  Before make an Item Load the Allocation Status for Payments from Allocation_Provider Table.
                    //		Remember All payments get fully split
                    //		If there is no provider to allocate split to ProvNum is
                    //			assigned to zero.
                    /**
                    * ////////////////////////////////////////////////////////////////
                    */
                    PP_PaySplitItem[] splits = null;
                    if (type == LedgerItemTypes.Payment && dt2_AllocTbl_Items.Rows.Count != 0)
                    {
                        //if (i == 66)
                        //    1.ToString();
                        String s1 = "PayTableSource = " + (((Enum)iTableEnum).ordinal()).ToString() + " AND PaySourceNum = " + uiItemNum;
                        DataRow[] rows = dt2_AllocTbl_Items.Select(s1);
                        //DataTable dtTest = new DataTable();
                        //for (int jj =0; jj < dt2_AllocTbl_Items.Columns.Count; jj++)
                        //{
                        //    dtTest.Columns.Add(new DataColumn(dt2_AllocTbl_Items.Columns[jj].ColumnName, typeof(string))   );
                        //}
                        //for (int jj = 0; jj < rows.Length; jj++)
                        //{
                        //    DataRow drkk = dtTest.NewRow();
                        //    for (int kk = 0; kk < dtTest.Columns.Count; kk++)
                        //    {
                        //        drkk[kk] = rows[jj][kk].ToString();
                        //    }
                        //    dtTest.Rows.Add(drkk);
                        //}
                        boolean fullyAllocated = false;
                        if (rows != null && rows.Length != 0)
                        {
                            splits = new PP_PaySplitItem[rows.Length];
                            fullyAllocated = true;
                            for (int j = 0;j < rows.Length;j++)
                            {
                                double amount2 = double.Parse(rows[j][3].ToString());
                                int prov2 = int.Parse(rows[j][4].ToString());
                                OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled AllocatedToTable = (OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled)int.Parse(rows[j][5].ToString());
                                ulong AllocatedToNum = ulong.Parse(rows[j][6].ToString());
                                splits[j] = new PP_PaySplitItem(amount2, prov2, AllocatedToNum, AllocatedToTable);
                                fullyAllocated = fullyAllocated && (prov2 != 0);
                            }
                        }
                         
                    }
                     
                    // If one item == 0 then it has not been allocated.
                    //LI.IS_ALLOCATED = fullyAllocated;
                    //if (fullyAllocated)
                    //    LI.ALLOCATED_AMMOUNT = sum; // if Allocated_ammount = amount the fully allocated = true (property of LI)
                    /**
                    * ////////////////////////////////////////////////////////////////
                    */
                    //  Now add item to collection
                    /**
                    * ////////////////////////////////////////////////////////////////
                    */
                    AddToCollection(LI, splits);
                }
            }
             
        }
        catch (Exception exc)
        {
            // Just Makes a PaymentItem out of the LI and Splits then adds the PaymentItem to the list
            // What about the case of an Item being in the Allocation_Provider Table but not in the
            // ODTable data? Let Update2() take care of these
            PU.setMB("Error in method:  " + PU.getMethod() + "\n\n" + exc.Message);
            rValSuccess = false;
        }

        if (rValSuccess)
            this.m_isFilled = true;
         
        return rValSuccess;
    }

    //DataTable dtv1 = ViewLedgerObject(true);
    //DataTable dtv1 = ViewLedgerObject(new LedgerItemTypes[] { LedgerItemTypes.Payment });
    private void sortAllLists() throws Exception {
        if (m_FullLedgerList != null)
            m_FullLedgerList.Sort();
         
        if (m_ChargesAndRefundsList != null)
            m_ChargesAndRefundsList.Sort();
         
        if (m_PaymentsAndAdjustList != null)
            m_PaymentsAndAdjustList.Sort();
         
        if (m_MarkedForDeletion != null)
            m_MarkedForDeletion.Sort();
         
        if (m_PaymentItemsList != null)
            m_PaymentItemsList.Sort();
         
    }

}


