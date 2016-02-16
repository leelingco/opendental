//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed;

public class PP_PaySplitItem   
{
    //  private ulong m_SplitNum;
    private double m_ItemAmt = new double();
    private int m_ProvNum = new int();
    //private ulong m_DansLedgerItemNum_Payment;
    private ulong m_AllocFromNum = new ulong();
    private OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled m_AllocFromTbl = OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled.ProcedureLog;
    // 	public bool IsAllocated = false; // never was used
    /**
    * The PP_PaySplitItem will be a paysplit that matches a certain charge.  The identity of the
    * original charge is contained in the table indicated by AllocFromTable and procedure number in this
    * table (as indicated by AllocFromProcNum).
    */
    public PP_PaySplitItem(double Ammount, int Provider, ulong AllocFromProcNum, OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled AllocFromTable) throws Exception {
        m_ItemAmt = Ammount;
        m_ProvNum = Provider;
        m_AllocFromTbl = AllocFromTable;
        m_AllocFromNum = AllocFromProcNum;
    }

    public PP_PaySplitItem() throws Exception {
    }

    //     public ulong SPLITNUM { get { return this.m_SplitNum; } }
    //		public ulong DL_NUM { get { return this.m_DansLedgerItemNum_Payment; } }
    public int getPROVNUM() throws Exception {
        return this.m_ProvNum;
    }

    public double getAMMOUNT() throws Exception {
        return this.m_ItemAmt;
    }

    public void setAMMOUNT(double value) throws Exception {
        this.m_ItemAmt = value;
    }

    /**
    * The PP_PaySplit is matches a payment to a procedure or charge.  This contains a value that indicates what the table is.
    */
    public OpenDental.Reporting.Allocators.MyAllocator1.ODTablesUsed.ODTablesPulled getALLOCATED_FROM_TABLE() throws Exception {
        return this.m_AllocFromTbl;
    }

    /**
    * The PP_PaySplit is matches a payment to a procedure or charge. This is the ProcNum or AdjNum or some other index Number that matches the  table indicated by ALLOCATED_FROM_TABLE
    */
    public ulong getALLOCATED_FROM_NUM() throws Exception {
        return this.m_AllocFromNum;
    }

}


// end class