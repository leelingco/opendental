//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:23 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.LedgerItemTypes;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_LedgerItem;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_PaymentItem;
import OpenDental.Reporting.Allocators.MyAllocator1.PP_PaySplitItem;
import OpenDental.Reporting.Allocators.MyAllocator1.ProviderBalance;
import OpenDental.Reporting.Allocators.MyAllocator1.ProviderPayment;
import OpenDental.Reporting.Allocators.MyAllocator1.PU;

/**
* Instantiate an Object of EqualizeProviderPayments.
* Set Connection String to QuerryResult.SetConnectionString(string)
* Call Instance.EqualizeGuarantorPayments(guarantor)
* use returned DataTable for your information.
*/
public class EqualizeProviderPayments   
{
    /**
    * This does Several Things
    * 3)  Calls EqualizeLedger
    * -For the guarantors passed it Performs a FIFO Provider accounting of
    * charges, Adjustments and Payments.  It will change the payment
    * to be multiple payments if Provider Payments require it
    * to be so.
    */
    private static int InstanceCounter = 0;
    OpenDental.Reporting.Allocators.MyAllocator1.GuarantorLedgerItemsCollection Ledger = null;
    // private bool m_DansLedgerTableExists = false;
    public EqualizeProviderPayments(BackgroundWorker bw) throws Exception {
        GC.Collect();
        // Found out that the object may not be collected.  If it is not then the finzlizer won't be in the que to call
        GC.WaitForPendingFinalizers();
        // Note this can make a serious performance hit.
        if (InstanceCounter > 0)
            throw new Exception("Not supposed to make more than one instance of this object! Check it out");
         
        InstanceCounter++;
    }

    protected void finalize() throws Throwable {
        try
        {
            InstanceCounter--;
        }
        finally
        {
            super.finalize();
        }
    }

    /**
    * In the future this will check to make sure OD Tables are compliant with
    * the setup that this was intended to run with.
    */
    private static boolean checkTables() throws Exception {
        return true;
    }

    /**
    * The method that actually calls the Equalizer.  Currently the equalizer just equalizes this instances
    * data so that we can see the data via the returned DataTable
    * 
    * Note this Erases the old ledger item and replaces it with a new one for the Specified Guarantor
    * 
    *  @param uGuarantor 
    *  @return
    */
    public DataTable equalizeGuarantorPayments(int iGuarantor) throws Exception {
        this.Ledger = new OpenDental.Reporting.Allocators.MyAllocator1.GuarantorLedgerItemsCollection(iGuarantor);
        Ledger.fill(false);
        // Old call need to keep to figure out what I was thinking before
        //Ledger.FillFromDansLedgerOriginalTable(openConnection);
        Ledger.equalizePaymentsV2();
        Ledger.getFullLedger().Sort();
        //	int c2 = Ledger.FullLedger[0].CompareTo(Ledger.FullLedger[1]);
        DataTable dt = new DataTable();
        String[] cols;
        for (Object __dummyForeachVar0 : cols)
        {
            String s = (String)__dummyForeachVar0;
            dt.Columns.Add(new DataColumn(s));
        }
        //GuarantorLedgerItemsCollection MixedCollection = new GuarantorLedgerItemsCollection(
        double runningBalance = 0;
        double runningCharges = 0;
        double runningPayments = 0;
        DateTime CurDate = DateTime.MinValue;
        for (Object __dummyForeachVar2 : Ledger.getFullLedger())
        {
            PP_LedgerItem li = (PP_LedgerItem)__dummyForeachVar2;
            if (!(li instanceof PP_PaymentItem))
            {
                runningBalance += li.getAMMOUNT();
                runningCharges += li.getAMMOUNT();
                DataRow dr = dt.NewRow();
                //string s3 = li.ITEMDATE.ToShortDateString();
                if (li.getITEMDATE() > CurDate)
                {
                    CurDate = li.getITEMDATE();
                    dr[0] = li.getITEMDATE().ToShortDateString();
                    if (dt.Rows.Count > 0)
                        dt.Rows[dt.Rows.Count - 1][7] = runningBalance.ToString();
                     
                }
                else
                    dr[0] = ""; 
                dr[1] = li.getPATNUM().ToString();
                dr[2] = li.getGUARANTOR().ToString();
                dr[3] = ((LedgerItemTypes)li.getITEMTYPE()).ToString();
                dr[4] = li.getPROVNUM().ToString();
                dr[5] = li.getAMMOUNT().ToString();
                dr[6] = "";
                //Payment Column
                dt.Rows.Add(dr);
            }
            else
            {
                PP_PaymentItem payitem = (PP_PaymentItem)li;
                for (Object __dummyForeachVar1 : payitem.getPAYMENT_SPLITS())
                {
                    PP_PaySplitItem psi = (PP_PaySplitItem)__dummyForeachVar1;
                    runningBalance += psi.getAMMOUNT();
                    runningPayments += psi.getAMMOUNT();
                    DataRow dr = dt.NewRow();
                    if (li.getITEMDATE() > CurDate)
                    {
                        CurDate = payitem.getITEMDATE();
                        dr[0] = payitem.getITEMDATE().ToShortDateString();
                        if (dt.Rows.Count > 0)
                            dt.Rows[dt.Rows.Count - 1][7] = runningBalance.ToString();
                         
                    }
                    else
                        dr[0] = ""; 
                    dr[1] = payitem.getPATNUM().ToString();
                    dr[2] = payitem.getGUARANTOR().ToString();
                    dr[3] = ((LedgerItemTypes)payitem.getITEMTYPE()).ToString();
                    dr[4] = psi.getPROVNUM().ToString();
                    dr[5] = "";
                    // Charge Column
                    dr[6] = psi.getAMMOUNT().ToString();
                    dt.Rows.Add(dr);
                }
            } 
        }
        if (dt.Rows.Count > 0)
            dt.Rows[dt.Rows.Count - 1][7] = runningBalance.ToString();
         
        {
            dt.Rows.Add(dt.NewRow());
            // blank line
            DataRow dr = dt.NewRow();
            dr[0] = CurDate.ToShortDateString();
            dr[4] = "Totals";
            dr[5] = runningCharges.ToString();
            dr[6] = runningPayments.ToString();
            dr[7] = runningBalance.ToString();
            dt.Rows.Add(dr);
        }
        return dt;
    }

    /**
    * Provides a data table that gives a running balance for each provider.
    * 
    * Will run the EqualizeGuarantorPayments(guarantor) first if this instance has not
    * done so yet.
    * 
    * // Setup DataTable
    * 
    * Date				   Provider #							 	      Provider #
    * Charges  Payments  Adjustments  Balances	    Charges  Payments  Adjustments Balances
    * [ 0   ][1][   2   ][   3    ][     4     ][    5   ][6][    7  ][    8   ][  etc......
    * 
    *  @param uGuarantor 
    *  @return
    */
    public DataTable providerBalancesDetail() throws Exception {
        //uint uGuarantor)
        DataTable dt = new DataTable();
        String[] cols;
        String[] subcols;
        if (Ledger == null)
            PU.setEx("Guarantor not set in " + PU.getMethod());
         
        if (!Ledger.getIS_FILLED())
            Ledger.fill(false);
         
        if (!Ledger.getIS_EQUALIZED())
            Ledger.equalizePaymentsV2();
         
        if (!Ledger.getIS_FILLED() || !Ledger.getIS_EQUALIZED())
        {
            dt.Columns.Add("ERROR");
            dt.Rows.Add(dt.NewRow()[0] = "Error Getting table filled");
            return dt;
        }
         
        Ledger.getFullLedger().Sort();
        // Generate List of Providers
        List<int> ProviderNums = new List<int>();
        for (Object __dummyForeachVar4 : Ledger.getFullLedger())
        {
            PP_LedgerItem li = (PP_LedgerItem)__dummyForeachVar4;
            if (li instanceof PP_PaymentItem)
            {
                for (Object __dummyForeachVar3 : ((PP_PaymentItem)li).getPAYMENT_SPLITS())
                {
                    PP_PaySplitItem psi = (PP_PaySplitItem)__dummyForeachVar3;
                    if (!ProviderNums.Contains(psi.getPROVNUM()))
                        ProviderNums.Add(psi.getPROVNUM());
                     
                }
            }
            else if (!ProviderNums.Contains(li.getPROVNUM()))
                ProviderNums.Add(li.getPROVNUM());
              
        }
        // Setup DataTable
        /**
        * Date				   Provider #							 	      Provider #
        * Charges  Payments  Adjustments  Balances	    Charges  Payments  Adjustments Balances
        * [ 0   ][1][   2   ][   3    ][     4     ][    5   ][6][    7  ][    8   ][  etc......
        */
        dt.Columns.Add(new DataColumn("Date"));
        System.Collections.Hashtable ht_dtProvNumOffsets = new System.Collections.Hashtable();
        for (int i = 0;i < ProviderNums.Count;i++)
        {
            dt.Columns.Add(new DataColumn(""));
            // Blank Column
            dt.Columns.Add(new DataColumn(""));
            dt.Columns.Add(new DataColumn(""));
            dt.Columns.Add(new DataColumn());
            dt.Columns.Add(new DataColumn(""));
        }
        // Add Header Rows
        DataRow dr1 = dt.NewRow();
        DataRow dr2 = dt.NewRow();
        dr2[0] = "Date";
        for (int i = 0;i < ProviderNums.Count;i++)
        {
            dr1[3 + 5 * i] = "Provider";
            dr1[4 + 5 * i] = "# " + ProviderNums[i].ToString();
            for (int j = 0;j < subcols.Length;j++)
                dr2[2 + 5 * i + j] = subcols[j];
        }
        // Charges, Payments, Adjustments, Balances
        dt.Rows.Add(dr1);
        dt.Rows.Add(dr2);
        // Generate Provider Balances
        System.Collections.Hashtable htProvBalance = new System.Collections.Hashtable();
        System.Collections.Hashtable htProvBalance_Cummulative = new System.Collections.Hashtable();
        DateTime curDate = DateTime.MinValue;
        if (Ledger.getFullLedger().Count != 0)
            curDate = Ledger.getFullLedger()[0].ITEMDATE;
         
        for (Object __dummyForeachVar5 : ProviderNums)
        {
            int ProvNum = (Integer)__dummyForeachVar5;
            htProvBalance[ProvNum] = new ProviderBalance(ProvNum,curDate);
            htProvBalance_Cummulative[ProvNum] = new ProviderBalance(ProvNum,curDate);
        }
        for (int i = 0;i < Ledger.getFullLedger().Count;i++)
        {
            PP_LedgerItem li = Ledger.getFullLedger()[i];
            if (curDate < li.getITEMDATE() || i == Ledger.getFullLedger().Count - 1)
            {
                DataRow dr3 = this.MakeProviderBalanceDataRow(curDate, dt, htProvBalance, htProvBalance_Cummulative, subcols, ProviderNums);
                dt.Rows.Add(dr3);
                for (Object __dummyForeachVar6 : ProviderNums)
                {
                    int ProvNum = (Integer)__dummyForeachVar6;
                    htProvBalance[ProvNum] = new ProviderBalance(ProvNum,li.getITEMDATE());
                    ((ProviderBalance)htProvBalance_Cummulative[ProvNum]).Date_of_Balance = li.getITEMDATE();
                }
                curDate = li.getITEMDATE();
            }
             
            if (li instanceof PP_PaymentItem)
            {
                for (Object __dummyForeachVar7 : ((PP_PaymentItem)li).getPAYMENT_SPLITS())
                {
                    PP_PaySplitItem psi = (PP_PaySplitItem)__dummyForeachVar7;
                    ProviderBalance pb = (ProviderBalance)htProvBalance[psi.getPROVNUM()];
                    ProviderBalance pb_Cumulative = (ProviderBalance)htProvBalance_Cummulative[psi.getPROVNUM()];
                    pb.Ammounts[((Enum)li.getITEMTYPE()).ordinal()] += psi.getAMMOUNT();
                    pb_Cumulative.Ammounts[((Enum)li.getITEMTYPE()).ordinal()] += psi.getAMMOUNT();
                }
            }
            else
            {
                ProviderBalance pb = (ProviderBalance)htProvBalance[li.getPROVNUM()];
                ProviderBalance pb_Cumulative = (ProviderBalance)htProvBalance_Cummulative[li.getPROVNUM()];
                pb.Ammounts[((Enum)li.getITEMTYPE()).ordinal()] += li.getAMMOUNT();
                pb_Cumulative.Ammounts[((Enum)li.getITEMTYPE()).ordinal()] += li.getAMMOUNT();
            } 
        }
        {
            DataRow dr3 = this.MakeProviderBalanceDataRow(curDate, dt, htProvBalance, htProvBalance_Cummulative, subcols, ProviderNums);
            dt.Rows.Add(dr3);
        }
        return dt;
    }

    /**
    * 2 Tables. Will return null if error getting table filled.
    * Columns:  Provider, Ammount
    */
    public DataTable providerBalancesOnly() throws Exception {
        //uint uGuarantor)
        DataTable rVal = null;
        if (Ledger == null)
            PU.setEx("Did not assign Guarantor in " + PU.getMethod());
         
        if (!Ledger.getIS_FILLED())
            Ledger.fill(false);
         
        if (!Ledger.getIS_EQUALIZED())
            Ledger.equalizePaymentsV2();
         
        if (!Ledger.getIS_FILLED() || !Ledger.getIS_EQUALIZED())
        {
            return null;
        }
         
        try
        {
            //dt.Columns.Add("ERROR");
            //dt.Rows.Add(dt.NewRow()[0] = "Error Getting table filled");
            rVal = new DataTable();
            Ledger.getFullLedger().Sort();
            System.Collections.Hashtable htProvBalance = new System.Collections.Hashtable();
            int[] providers = Ledger.getProviders();
            double initalbalance = 0;
            for (Object __dummyForeachVar8 : providers)
            {
                int p = (Integer)__dummyForeachVar8;
                htProvBalance[p] = initalbalance;
            }
            for (Object __dummyForeachVar9 : Ledger.getCharges())
            {
                PP_LedgerItem pli = (PP_LedgerItem)__dummyForeachVar9;
                htProvBalance[pli.getPROVNUM()] = ((double)htProvBalance[pli.getPROVNUM()]) + pli.getAMMOUNT();
            }
            for (Object __dummyForeachVar11 : Ledger.getPayments())
            {
                PP_PaymentItem pi = (PP_PaymentItem)__dummyForeachVar11;
                if (pi.getPAYMENT_SPLITS() != null)
                {
                    for (Object __dummyForeachVar10 : pi.getPAYMENT_SPLITS())
                    {
                        PP_PaySplitItem psi = (PP_PaySplitItem)__dummyForeachVar10;
                        htProvBalance[psi.getPROVNUM()] = ((double)htProvBalance[psi.getPROVNUM()]) + psi.getAMMOUNT();
                    }
                }
                else
                    htProvBalance[pi.getPROVNUM()] = ((double)htProvBalance[pi.getPROVNUM()]) + pi.getAMMOUNT(); 
            }
            DataColumn dc1 = new DataColumn("Provider", uint.class);
            DataColumn dc2 = new DataColumn("Ammount", double.class);
            rVal.Columns.Add(dc1);
            rVal.Columns.Add(dc2);
            for (Object __dummyForeachVar12 : providers)
            {
                //for (int i = 0; i < htProvBalance.Count; i++)
                int p = (Integer)__dummyForeachVar12;
                DataRow dr = rVal.NewRow();
                dr[0] = p;
                dr[1] = (double)htProvBalance[p];
                rVal.Rows.Add(dr);
            }
        }
        catch (Exception exc)
        {
            exc.ToString();
        }

        return rVal;
    }

    // just to elimnate compiler warning
    /**
    * Takes the ammount to allocate then provides a recommendation of where to allocate the
    * charges to.  Returns a DataTable with the following Collumns:
    * Provider, Ammount
    * 
    * The idea being that the calling method will have the payment in their hand and will
    * use the recommendation to generate the appropriate PROVIDER paysplit info.  So the calling
    * method will have the Date of Payment, Paytable source etc.
    * 
    *  @param AmountToAllocate Should be -ve because it is a payment.
    *  @return
    */
    public DataTable nextPaySplitRecommendation(double AmountToAllocate) throws Exception {
        //(uint uGuarantor, decimal AmountToAllocate)
        DataTable rVal = new DataTable();
        if (!Ledger.getIS_FILLED())
            Ledger.fill(false);
         
        if (!Ledger.getIS_EQUALIZED())
            Ledger.equalizePaymentsV2();
         
        if (!Ledger.getIS_FILLED() || !Ledger.getIS_EQUALIZED())
        {
            return null;
        }
         
        //dt.Columns.Add("ERROR");
        //dt.Rows.Add(dt.NewRow()[0] = "Error Getting table filled");
        List<ProviderPayment> psiList = new List<ProviderPayment>();
        ProviderPayment curPayItem = new ProviderPayment();
        curPayItem.Provider = -1;
        double remainingAmmount = AmountToAllocate;
        for (int i = 0;i < Ledger.getCharges().Count;i++)
        {
            if (Ledger.getCharges()[i].AmtUnallocated > 0)
            {
                // if first time thru create curPayItem
                if (curPayItem.Provider == -1)
                {
                    curPayItem = new ProviderPayment();
                    curPayItem.Provider = Ledger.getCharges()[i].PROVNUM;
                }
                 
                // if 2+ thru and ProvNums don't match create a new curPayItem
                if (curPayItem.Provider != Ledger.getCharges()[i].PROVNUM)
                {
                    psiList.Add(curPayItem);
                    curPayItem = new ProviderPayment();
                    curPayItem.Provider = Ledger.getCharges()[i].PROVNUM;
                }
                 
                // Determin what ammount to add
                if (Ledger.getCharges()[i].AmtUnallocated <= remainingAmmount)
                {
                    curPayItem.Ammount += Ledger.getCharges()[i].AmtUnallocated;
                    remainingAmmount -= Ledger.getCharges()[i].AmtUnallocated;
                }
                else
                {
                    curPayItem.Ammount += remainingAmmount;
                    remainingAmmount = 0;
                } 
                if (remainingAmmount == 0)
                    i = Ledger.getCharges().Count;
                 
            }
             
        }
        // end loop
        /**
        * Scenarios:  1:  Partial Ammount was allocated.  But curPayItem not recorded
        * 2:	No Ammount was allocated so a curPayItem needs recorded
        * for 1:  Need to recored  curPayItem and Generate a new CurPayItem with no provider for the remaining
        */
        if (remainingAmmount != AmountToAllocate)
        {
            // allocation occurred but not saved
            psiList.Add(curPayItem);
            // any remaining ammount?
            if (remainingAmmount != 0)
            {
                curPayItem = new ProviderPayment();
                curPayItem.Provider = -1;
                curPayItem.Ammount = remainingAmmount;
                remainingAmmount = 0;
                psiList.Add(curPayItem);
            }
             
        }
        else
        {
            // No allocation occurred
            curPayItem = new ProviderPayment();
            curPayItem.Provider = -1;
            curPayItem.Ammount = remainingAmmount;
            remainingAmmount = 0;
            psiList.Add(curPayItem);
        } 
        // last item created was not added.
        /**
        * Generate the returning table.
        */
        DataColumn dc1 = new DataColumn("Provider", uint.class);
        DataColumn dc2 = new DataColumn("Ammount", double.class);
        rVal.Columns.Add(dc1);
        rVal.Columns.Add(dc2);
        for (Object __dummyForeachVar13 : psiList)
        {
            ProviderPayment pi = (ProviderPayment)__dummyForeachVar13;
            DataRow dr = rVal.NewRow();
            if (pi.Provider < 0)
                dr[0] = 0;
            else
                dr[0] = pi.Provider; 
            dr[1] = pi.Ammount;
            rVal.Rows.Add(dr);
        }
        return rVal;
    }

    private DataRow makeProviderBalanceDataRow(DateTime date, DataTable SourceSchema, System.Collections.Hashtable htProvBalance, System.Collections.Hashtable htCumulativeBalance, String[] subcols, List<int> ProviderNums) throws Exception {
        //MakeDTRow;
        DataRow dr3 = SourceSchema.NewRow();
        dr3[0] = date.ToShortDateString();
        for (int j = 0;j < ProviderNums.Count;j++)
        {
            Object o1 = htProvBalance["3"];
            ProviderBalance pb = (ProviderBalance)htProvBalance[ProviderNums[j]];
            ProviderBalance pb_Cumulative = (ProviderBalance)htCumulativeBalance[ProviderNums[j]];
            for (int k = 0;k < subcols.Length - 1;k++)
                if (pb.Ammounts[k] != 0)
                    dr3[2 + j * 5 + k] = pb.Ammounts[k];
                 
            dr3[2 + j * 5 + subcols.Length - 1] = pb_Cumulative.getBALANCE();
        }
        return dr3;
    }

}


