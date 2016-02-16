//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import CS2JNet.System.StringSupport;
import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemTypes;
import OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_LedgerItem;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_PaymentItem;

/**
* // 
* // Class holding the definitions for the DansLedger Table
* //
*/
//class PP_Ledger
//{
//    public enum ODTablesPulled { ProcedureLog, Adjustment, ClaimProc, PaySplit };
//    public enum Columns
//    {
//        LedgerItemNum, LedgerITemType, Guarantor, PatNum, PRovNum,
//        ProcNum, ItemDate, ItemAmt, AmtAllocated, IsAllocated,
//        AllocatedString, TableSource
//    };
//    #region Public Static Fields
//    public static readonly string Name = "DansLedger";
//    public static readonly string OrignalTableName = "DansLedgerOriginalData";
//    #region Commented out Code
//    //public static string CreateTableString = "CREATE TABLE " + PaymentProcessor_Ledger.Name
//    //               + " (LedgerItemNum bigint unsigned not null auto_increment primary key, "
//    //               + " LedgerItemType smallint unsigned," // the type as defined in SQLReportGenerator.LedgerItemTypes
//    //               + " Guarantor int unsigned, "  //The Person who is the Guarantor of this procedure
//    //               + " PatNum int unsigned, " // The person the charge item is associated with, 0 for ItemTypes != Charge
//    //               + " ProvNum smallint unsigned, " //The provider Num the item is assigned to.
//    //           + " ProcNum int unsigned, "    //The Procedure Number in the ProcedureLog Table
//    //           + " ItemDate DATE, " // The date the Item is posted to the ledger
//    //           + " ItemAmt decimal(10,2), " //Amount of charge, payment, or adjustment
//    //           + " AmtAllocated decimal(10,2) DEFAULT 0, " //amount of ledgeritem that has been allocated
//    //           + " IsAllocated bool DEFAULT 0, " // indicates wether the full amount of item has been allocated
//    //            + " AllocationString text DEFAULT '', "
//    //            + " TableSource smallint DEFAULT -1)"; // A debuging field that indicates what payments/refund were allocated to this item
//    //public static string CreateTableStringByName(string tableName)
//    //{
//    //    return "CREATE TABLE " + tableName
//    //                 + " (LedgerItemNum bigint unsigned not null auto_increment primary key, "
//    //                 + " LedgerItemType smallint unsigned," // the type as defined in SQLReportGenerator.LedgerItemTypes
//    //                 + " Guarantor int unsigned, "  //The Person who is the Guarantor of this procedure
//    //                 + " PatNum int unsigned, " // The person the charge item is associated with, 0 for ItemTypes != Charge
//    //                 + " ProvNum smallint unsigned, " //The provider Num the item is assigned to.
//    //             + " ProcNum int unsigned, "    //The Procedure Number in the ProcedureLog Table
//    //             + " ItemDate DATE, " // The date the Item is posted to the ledger
//    //             + " ItemAmt decimal(10,2), " //Amount of charge, payment, or adjustment
//    //             + " AmtAllocated decimal(10,2) DEFAULT 0, " //amount of ledgeritem that has been allocated
//    //             + " IsAllocated bool DEFAULT 0, " // indicates wether the full amount of item has been allocated
//    //              + " AllocationString text, "
//    //             + " TableSource smallint DEFAULT -1)"; // A debuging field that indicates what payments/refund were allocated to this item
//    //}
//    #endregion
//    #endregion
//    /// <summary>
//    /// Copies all the items from OD tables to DansOriginalTable if they do not have the IsPulledToDans flag
//    ///
//    /// </summary>
//    public static bool CopyNonPulledItemsToOriginal(object sender, DoWorkEventArgs e)
//    { throw new Exception("Non Implemented Yet. Flag1"); return false; }
//    #region Commented Out
//    //public static bool CopyNonPulledItemsToOriginal(object sender, DoWorkEventArgs e)
//    //{
//    //    if (sender != null)
//    //        if (!(sender is BackgroundWorker))
//    //            System.Windows.Forms.MessageBox.Show("sender is not a BackgroundWorker. Set Breakpoint\n"
//    //                 + "in DansTables.DansLedger.MakeOriginal(object sender, DoWorkEventArgs e) to debug.");
//    //    BackgroundWorker worker = null;
//    //    if (sender != null)
//    //        worker = (BackgroundWorker)sender;
//    //    bool rSuccess = true;
//    //    /// Make sure The ODTables in Question have an IsPulled Flag
//    //    TableUtility tu = new TableUtility();
//    //    bool ODTablesHaveFlags = tu.CheckODTables(true);
//    //    tu = null;
//    //    System.Data.DataTable dtWorking = null;
//    //    DateTime StartTime = DateTime.Now;
//    //    MySqlConnection con = new MySqlConnection(MasterConnectionData.GetConnectionString());
//    //    try
//    //    {
//    //        // Find All Guarantors
//    //        dtWorking = new System.Data.DataTable();
//    //        //   MySqlDataAdapter da = new MySqlDataAdapter("SELECT Distinct(Guarantor) FROM Patient", con);
//    //        // MySqlCommand cmd = new MySqlCommand(LedgerItems.GuarantorsWNonPulledItems(), con);
//    //        MySqlDataAdapter da = new MySqlDataAdapter(LedgerItems.GuarantorsWNonPulledItems(), con);
//    //        if (con.State != System.Data.ConnectionState.Open)
//    //            con.Open();
//    //        da.Fill(dtWorking);
//    //        bool ExitEarly = false;
//    //        int progress = 0;
//    //        if (worker != null)
//    //        {
//    //            worker.ReportProgress(progress, "**SCROLL****LOG**Preparing to Clone Data from OpenDental to  "
//    //                + PaymentProcessor_Ledger.OrignalTableName + ".");
//    //            worker.ReportProgress(progress, "**SCROLL****LOG** ");
//    //        }
//    //        for (int i = 0; i < dtWorking.Rows.Count; i++)
//    //        {
//    //            progress = 100 * i / dtWorking.Rows.Count;
//    //            uint Guarantor = uint.Parse(dtWorking.Rows[i][0].ToString());
//    //            //uint Guarantor = 8;
//    //            if (Guarantor == 3858)
//    //                3858.ToString();
//    //            // Fill OriginalTable -- //Skip Guarantors in the Excluded List
//    //            if (Guarantor != 0 && !Guarantors.GuarantorIsInExcludedList(Guarantor)) // See comments on what Guarantor = 0 does
//    //                ExitEarly = !TableUtility.CopyNonPulledItemsTo_DanLedgerOriginal(Guarantor);
//    //            if (ExitEarly)
//    //                1.ToString();
//    //            if (worker != null)
//    //            {
//    //                worker.ReportProgress(progress, "Copied Guarantor data " + Guarantor
//    //                     + " into " + PaymentProcessor_Ledger.OrignalTableName + ".");
//    //                if (ExitEarly)
//    //                    worker.ReportProgress(0, "**SCROLL****LOG**Problem with TableUtility.CopyNonPulldItemsTo_DansLedgerOrigional\n"
//    //                             + " Called From DansLedger.CopyNonPulledItemsToOriginal look for 'ExitEarly' Flag   ");
//    //                if (worker.CancellationPending) // Problem arros
//    //                {
//    //                    rSuccess = false;
//    //                    e.Cancel = true;
//    //                    i = dtWorking.Rows.Count;
//    //                    return false;
//    //                }
//    //            }
//    //        }
//    //    }
//    //    catch (Exception exc)
//    //    {
//    //        string s = exc.Message;
//    //        System.Windows.Forms.MessageBox.Show("Error creating original table.  Did you drop it first?\n" + s);
//    //        rSuccess = false;
//    //    }
//    //    finally
//    //    {
//    //        if (con.State == System.Data.ConnectionState.Open)
//    //            con.Close();
//    //    }
//    //    DateTime EndTime = DateTime.Now;
//    //    TimeSpan ts = new TimeSpan(EndTime.Ticks - StartTime.Ticks);
//    //    return rSuccess;
//    //}
//    #endregion
//} // end class PP_Ledger
/**
* PP is for Payment Proccessor
*/
public class PP_LedgerItem  extends IComparable 
{
    private LedgerItemTypes m_LedgerItemType = LedgerItemTypes.Charge;
    private uint m_Guarantor = new uint();
    private uint m_PatNum = new uint();
    private int m_ProvNum = new int();
    private uint m_ProcNum = new uint();
    private DateTime m_ItemDate = new DateTime();
    private double m_ItemAmt = new double();
    private ulong m_LedgerItemNum = new ulong();
    private OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled m_TableSource = OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled.ProcedureLog;
    /**
    * Indicates wether LedgerItem Needs Updated.
    * Currently the only fields affected are:
    * IsAllocated
    * m_DebugString
    * AmtAllocated
    */
    private boolean m_IsChanged = false;
    /**
    * Indicates wether the Charge or Refund Item is fully allocated
    * to a payment or adjustment.
    */
    //  private bool IsAllocated = false;
    private boolean m_IsAllocated = false;
    /**
    * Provides backward compatibility to GuarantorLedgerItemsCollection.  Not a PROPERTY but
    * and exposed Variable.  Doesn't flag IsChanged.
    * 
    * 
    * Watch this method because it may change m_IsAllocated when you do not want it to.
    */
    public boolean getIsAllocated_OLD() throws Exception {
        return this.m_IsAllocated;
    }

    public void setIsAllocated_OLD(boolean value) throws Exception {
        this.m_IsAllocated = value;
        this.m_IsChanged = true;
    }

    /**
    * Works with Property ALLOCATED_AMMOUNT
    */
    private double m_AmtAllocated = new double();
    private String m_DebugString = "";
    /**
    * Used for creating LedgerItems that can be sorted in a FIFO Manner
    * Input values are similar to the Table Columns.  Readonly properties provided.
    * IComparable Interface Provided for sorting
    * 
    * Strips time off of cDate
    * 
    * Set cLedgerItemNumber =0 if unknown.
    */
    public PP_LedgerItem(LedgerItemTypes cLedgerItemType, uint cGuarantor, uint cPatNum, int cProvNum, uint cProcNum, DateTime cDate, double cItemAmt, ulong cLedgerItemNumber, OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled cSourceTable) throws Exception {
        //this.m_LedgerItemType = cLedgerItemType;
        //this.m_Guarantor = cGuarantor;
        //this.m_PatNum = cPatNum;
        //this.m_ProvNum = cProvNum;
        //this.m_ProcNum = cProcNum;
        //this.m_ItemDate = new DateTime(cDate.Year, cDate.Month, cDate.Day);
        //this.m_ItemAmt = cItemAmt;
        //this.m_LedgerItemNum = cLedgerItemNumber;
        SetConstructorValues(cLedgerItemType, cGuarantor, cPatNum, cProvNum, cProcNum, cDate, cItemAmt, cLedgerItemNumber, cSourceTable);
    }

    /**
    * 2nd OverLoad has values IsAllocated, DebugString, AmtAllocated
    */
    public PP_LedgerItem(LedgerItemTypes cLedgerItemType, uint cGuarantor, uint cPatNum, ushort cProvNum, uint cProcNum, DateTime cDate, double cItemAmt, ulong cLedgerItemNumber, OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled cSourceTable, boolean IsAllocated1, double AmtAllocated1, String DebugString1) throws Exception {
        SetConstructorValues(cLedgerItemType, cGuarantor, cPatNum, cProvNum, cProcNum, cDate, cItemAmt, cLedgerItemNumber, cSourceTable, IsAllocated1, DebugString1, AmtAllocated1);
    }

    public PP_LedgerItem(PP_LedgerItem DLI) throws Exception {
        PP_LedgerItem CloneCopy = (PP_LedgerItem)DLI.MemberwiseClone();
        setConstructorValues(CloneCopy.m_LedgerItemType,CloneCopy.m_Guarantor,CloneCopy.m_PatNum,CloneCopy.m_ProvNum,CloneCopy.m_ProcNum,CloneCopy.m_ItemDate,CloneCopy.m_ItemAmt,CloneCopy.m_LedgerItemNum,CloneCopy.m_TableSource);
    }

    private void setConstructorValues(LedgerItemTypes cLedgerItemType, uint cGuarantor, uint cPatNum, int cProvNum, uint cProcNum, DateTime cDate, double cItemAmt, ulong cLedgerItemNumber, OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled cSourceTable) throws Exception {
        this.m_LedgerItemType = cLedgerItemType;
        this.m_Guarantor = cGuarantor;
        this.m_PatNum = cPatNum;
        this.m_ProvNum = cProvNum;
        this.m_ProcNum = cProcNum;
        this.m_ItemDate = new DateTime(cDate.Year, cDate.Month, cDate.Day);
        this.m_ItemAmt = cItemAmt;
        this.m_LedgerItemNum = cLedgerItemNumber;
        this.m_TableSource = cSourceTable;
    }

    /**
    * Last 3 parameters are different.  Used if want to set IsAllocated, DebugString,or AmtAllocated
    */
    private void setConstructorValues(LedgerItemTypes cLedgerItemType, uint cGuarantor, uint cPatNum, ushort cProvNum, uint cProcNum, DateTime cDate, double cItemAmt, ulong cLedgerItemNumber, OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled cSourceTable, boolean IsAllocated1, String DebugString1, double AmtAllocated1) throws Exception {
        SetConstructorValues(cLedgerItemType, cGuarantor, cPatNum, cProvNum, cProcNum, cDate, cItemAmt, cLedgerItemNumber, cSourceTable);
        this.m_IsAllocated = IsAllocated1;
        this.m_DebugString = DebugString1;
        this.m_AmtAllocated = AmtAllocated1;
    }

    public LedgerItemTypes getITEMTYPE() throws Exception {
        return this.m_LedgerItemType;
    }

    public uint getGUARANTOR() throws Exception {
        return this.m_Guarantor;
    }

    public uint getPATNUM() throws Exception {
        return this.m_PatNum;
    }

    public int getPROVNUM() throws Exception {
        return this.m_ProvNum;
    }

    public uint getPROCNUM() throws Exception {
        return this.m_ProcNum;
    }

    public DateTime getITEMDATE() throws Exception {
        return this.m_ItemDate;
    }

    public boolean getIS_CHANGED() throws Exception {
        return this.m_IsChanged;
    }

    public ulong getITEMNUM() throws Exception {
        return this.m_LedgerItemNum;
    }

    public double getAMMOUNT() throws Exception {
        return this.m_ItemAmt;
    }

    public void setAMMOUNT(double value) throws Exception {
        this.m_ItemAmt = value;
    }

    /**
    * The unique number from the database assigned to this transaction
    * 
    * A string that is usefull to see how things are allocated.  More used
    * in debugging problems
    * As allocation occurs string will be xxx,yyyy; xxxx2, yyyy2; xxx3,yyyy3
    * where xxx is the LedgerItemNumber and yyyy is the amount alocated from this
    * ledger item number
    */
    public String getALLOCATION_STRING() throws Exception {
        return this.m_DebugString;
    }

    public void setALLOCATION_STRING(String value) throws Exception {
        this.m_DebugString = value;
        this.m_IsChanged = true;
    }

    /**
    * Gets or sets the amount of this ledger items that is allocated
    * When this value is set IS_CHANGED becomes true.
    */
    public double getALLOCATED_AMMOUNT() throws Exception {
        return this.m_AmtAllocated;
    }

    public void setALLOCATED_AMMOUNT(double value) throws Exception {
        this.m_AmtAllocated = value;
        this.m_IsChanged = true;
        if (this.getAmtUnallocated() == 0)
            this.setIS_ALLOCATED(true);
         
    }

    /**
    * Holds the
    */
    public OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled getOD_TABLESOURCE() throws Exception {
        return this.m_TableSource;
    }

    public void setOD_TABLESOURCE(OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled value) throws Exception {
        // make sure value matches ODTableTypes
        this.m_TableSource = value;
    }

    /**
    * Expects caller to be manipulating AmtAllocated Field.
    * So this just returns this.AMMOUNT - this.AmtAllocated
    */
    public double getAmtUnallocated() throws Exception {
        return this.getAMMOUNT() - this.m_AmtAllocated;
    }

    /**
    * Returns true if Item has been allocated.  Also set IS_CHANGED = true;
    */
    public boolean getIS_ALLOCATED() throws Exception {
        return this.m_IsAllocated;
    }

    public void setIS_ALLOCATED(boolean value) throws Exception {
        this.m_IsAllocated = value;
        this.m_IsChanged = true;
    }

    public PP_PaymentItem createPaymentItem() throws Exception {
        return new PP_PaymentItem(this.m_LedgerItemType,this.m_Guarantor,m_PatNum,m_ProvNum,m_ProcNum,m_ItemDate,m_ItemAmt,m_LedgerItemNum,m_TableSource);
    }

    //public string GetInsertString()
    //{
    //    return "INSERT INTO " + DansTables.DansLedger.Name
    //    + " ( LedgerItemType, Guarantor, PatNum, ProvNum, ProcNum, ItemDate, ItemAmt, "
    //    + "  AmtAllocated, IsAllocated,AllocationString) "
    //    + " Values ( "
    //    + ((int)this.ITEMTYPE).ToString() + ","
    //    + this.GUARANTOR.ToString() + ","
    //    + this.PATNUM.ToString() + ","
    //    + this.PROVNUM.ToString() + ","
    //    + this.PROCNUM.ToString() + ","
    //    + String.Format("'{0}-{1}-{2}',", this.ITEMDATE.Year, this.ITEMDATE.Month, this.ITEMDATE.Day)
    //    + this.AMMOUNT.ToString() + ","
    //    + this.m_AmtAllocated.ToString() + ","
    //    + ((int)(this.m_IsAllocated ? 1 : 0)).ToString() + ","
    //    + "'" + this.m_DebugString + "' "
    //    + ")";
    //}
    /**
    * Only to be used if you don't want to change the provider number
    * assigned to this LedgerItem but you want
    * the ProviderNumber to Appear in the allocation string
    * 
    *  @param LedgerItemNumber 
    *  @param allocatedAmount 
    *  @param ProvNum 
    *  @return
    */
    public double addAllocation(ulong LedgerItemNumber, double allocatedAmount, int ProvNum) throws Exception {
        double rVal = this.AddAllocation(LedgerItemNumber, allocatedAmount);
        if (!StringSupport.equals(this.m_DebugString, ""))
            this.m_DebugString += ";";
         
        this.m_DebugString += ProvNum + "," + rVal.ToString();
        return rVal;
    }

    /**
    * Need a LedgerItemNumber to correlate allocation to
    * ---Returns amount actually allocated.
    * 
    *  @param LedgerItemNumber 
    *  @param allocatedAmount 
    *  @return The amount actually allocated
    */
    public double addAllocation(ulong LedgerItemNumber, double allocatedAmount) throws Exception {
        /**
        * Note this.AmtUnallocated and allocatedAmount should normally have opposite signs
        * because you are allocating a payment (-ve) to a charge (+ve) or vice versa
        * If a different situation is found you need to figure out why
        */
        if (this.getAmtUnallocated() * allocatedAmount > 0)
            2.ToString();
         
        // set breakpoint here
        double MaxAmount = new double();
        // Want MaxAmmount oposite sinage of this.Ammount
        if (this.getAMMOUNT() > 0)
            MaxAmount = (this.getAmtUnallocated() >= -allocatedAmount ? -allocatedAmount : this.getAmtUnallocated());
        else
            MaxAmount = (-this.getAmtUnallocated() >= allocatedAmount ? -allocatedAmount : this.getAmtUnallocated()); 
        this.setALLOCATED_AMMOUNT(this.m_AmtAllocated + MaxAmount);
        if (this.getAmtUnallocated() == 0)
            this.m_IsAllocated = true;
         
        return MaxAmount;
    }

    /*
    		/// <summary>
    		/// Splits the current ledger item up into an array of items with different 
    		/// payments and providers.  Each string in parameters should be of the form
    		/// xxx,yyyy
    		/// xxx= provider num
    		/// yyyy = amount
    		/// Throws exception if 'this' is not a payment or writeoff type.
    		/// </summary>
    		/// <param name="Parameters"></param>
    		/// <returns></returns>
    		public PP_LedgerItem[] SplitPaymentWriteOffItem(string[] Parameters)
    		{
    			//   bool b1 = this.m_LedgerItemType != LedgerItemTypes.NegAdjustment;
    			//    bool b2 = this.m_LedgerItemType != LedgerItemTypes.Payment;
    			//    bool b3 = b1 || b2;
    			if (!(this.m_LedgerItemType != LedgerItemTypes.NegAdjustment || this.m_LedgerItemType != LedgerItemTypes.Payment))
    				throw new Exception("SplitPaymentWriteoffItem in DansLedger called \nfrom an object not of Payment or writeoff type!");
    			if (Parameters[0] == "")
    				return null;
    			List<PP_LedgerItem> rList = new List<PP_LedgerItem>();
    			PP_LedgerItem temp = null;
    			ushort CurrentProvider = UInt16.Parse(Parameters[0].Split(',')[0]);
    			//decimal runningBalance = 0;
    			for (int i = 0; i < Parameters.Length; i++)
    			{
    				string[] ProvAmount = Parameters[i].Split(',');
    				ushort Prov = UInt16.Parse(ProvAmount[0]);
    				decimal ammount = Decimal.Parse(ProvAmount[1]);
    				if (temp == null)
    				{
    					temp = new PP_LedgerItem(this.ITEMTYPE, this.GUARANTOR, this.PATNUM,
    						  Prov, this.PROCNUM, this.ITEMDATE, 0, 0);
    					rList.Add(temp);
    				}
    				if (temp.PROVNUM == Prov)
    				{
    					temp.AMMOUNT += ammount;
    					temp.ALLOCATED_AMMOUNT += ammount;
    				}
    				if (Prov != temp.PROVNUM)
    				{
    					temp.ALLOCATION_STRING = temp.PROVNUM.ToString() + "," + temp.AMMOUNT;
    					temp.m_IsAllocated = true;
    					temp.ALLOCATED_AMMOUNT = temp.m_AmtAllocated;
    					temp = new PP_LedgerItem(this.ITEMTYPE, this.GUARANTOR, this.PATNUM,
    						  Prov, this.PROCNUM, this.ITEMDATE, ammount, 0);
    					rList.Add(temp);
    				}
    				temp.ALLOCATION_STRING = temp.PROVNUM.ToString() + "," + temp.AMMOUNT;
    				temp.m_IsAllocated = true;
    				// temp.AmtAllocated += -37;
    				//   rList.Add(temp);
    			}
    			return rList.ToArray();
    		}
    		*/
    /**
    * Returns -1 if Date of this is sooner than obj, 1 if later
    * If Dates are the same then returns -1,0,1 by giving -1
    * to the first items in this order Charge->Adjustment->Payment
    * if ItemTypes are the same 0 is returned
    */
    public int compareTo(Object obj) throws Exception {
        /* elected to elimiate this item and let the system throw the exception
        			 * because this method will get called a lot and 
        			 * I want to reduce overhead.
        			if (!(obj is DansLedgerItem))
        				throw new Exception("Obj passed to DansLedgerItem.CompareTo() is not a DansLedgerItem");
        			*/
        PP_LedgerItem item2 = (PP_LedgerItem)obj;
        int rValue = this.getITEMDATE().CompareTo(item2.getITEMDATE());
        if (rValue == 0)
        {
            switch(this.getITEMTYPE())
            {
                case PosAdjustment: 
                    if (item2.getITEMTYPE() == LedgerItemTypes.PosAdjustment)
                        return 0;
                    else if (item2.getITEMTYPE() == LedgerItemTypes.Charge)
                        return 1;
                    else
                        return -1;  
                case Charge: 
                    //break;
                    if (item2.getITEMTYPE() == LedgerItemTypes.Charge)
                        rValue = 0;
                    else
                        rValue = 1; 
                    break;
                case Payment: 
                    // Charge should always come first. So compared object should compare bigger.
                    if (item2.getITEMTYPE() == LedgerItemTypes.Payment)
                        rValue = 0;
                    else
                        rValue = 1; 
                    break;
                case NegAdjustment: 
                    // Payments always last
                    if (item2.getITEMTYPE() == LedgerItemTypes.NegAdjustment)
                        rValue = 0;
                    else if (item2.getITEMTYPE() == LedgerItemTypes.Charge || item2.getITEMTYPE() == LedgerItemTypes.PosAdjustment)
                        rValue = 1;
                    else
                        //ie this Should appear later then item2
                        // item2.ITEMTYPE == LedgerItemType.Payment
                        rValue = -1;  
                    break;
                default: 
                    break;
            
            }
        }
         
        return rValue;
    }

}


// if (rValue == 0)