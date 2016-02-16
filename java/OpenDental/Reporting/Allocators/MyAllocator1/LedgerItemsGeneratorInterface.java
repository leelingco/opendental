//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;


/**
* Single required interface item - ODPullString(....)
*/
public interface LedgerItemsGeneratorInterface   
{
    String oDPullString(int Guarantor, boolean IsPulledFlag) throws Exception ;

}


//class L1_NegativeAdjustments :LedgerItemsGenerator
//{
//    #region LedgerItemsGeneratorInterface Members
//    /// <summary>
//    /// Provides the String to select NegativeAdjustments from OD Adjustment Table
//    /// </summary>
//    /// <param name="Guarantor"></param>
//    /// <param name="IsPulledFlag">Which state of flag do you want to pull</param>
//    /// <returns></returns>
//    public string ODPullString(uint Guarantor, bool IsPulledFlag)
//    { // string NegAdjustmentUnion -- From GuarantorLedgerItem
//        string rVal = "SELECT "
//                + "\n '" + (int)LedgerItemTypes.NegAdjustment + "', " //2
//                + "\n      patient.Guarantor, "
//                + "\n      patient.PatNum, "
//                + "\n      adjustment.ProvNum, "
//                + "\n      adjustment.ADjDate, " // AS 'ItemDate', " --was a problem if not first in union
//                + "\n      adjustment.AdjAmt, "
//                + "\n       Adjustment.AdjNum," // ProcNum not used For this Union
//                + "\n       " + (int)ODTablesPulled.Adjustment
//                + "\n FROM adjustment, patient \n WHERE "
//                + "\n      adjustment.PatNum = Patient.PatNum "
//                + "\n      && adjustment.AdjAmt <= 0";
//        if (Guarantor != 0)
//            rVal += "\n      && Patient.Guarantor = " + Guarantor;
//        if (IsPulledFlag)
//            rVal += SQL_IsPulledCondition(ODTablesPulled.Adjustment, IsPulledFlag);
//        //if (IsPulledFlag)
//        //    rVal += "\n    && adjustment." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//        //else
//        //    rVal += "\n    && adjustment." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";
//        return rVal;
//    }
//    #endregion
//}
//class L2_PositiveAdjustments : LedgerItemsGenerator
//{
//    public override string ODPullString(uint Guarantor, bool IsPulledFlag)
//    {
//        //PosAdjustmentUnion
//        string rVal = "SELECT "
//                    + "\n '" + (int)LedgerItemTypes.PosAdjustment + "', " //2
//                    + "\n      patient.Guarantor, "
//                    + "\n      patient.PatNum, "
//                    + "\n      adjustment.ProvNum, "
//                    + "\n      adjustment.ADjDate, "
//                    + "\n      adjustment.AdjAmt, "
//                    + "\n       adjustment.AdjNum," //
//                + "\n       " + (int)ODTablesPulled.Adjustment
//                    + "\n FROM adjustment, patient \n WHERE "
//                    + "\n      adjustment.PatNum = Patient.PatNum "
//                    + "\n      && adjustment.AdjAmt > 0";
//        if (Guarantor != 0)
//            rVal += "\n      && Patient.Guarantor = " + Guarantor;
//        if (IsPulledFlag)
//            rVal += SQL_IsPulledCondition(ODTablesPulled.Adjustment, IsPulledFlag);
//        //if (IsPulledFlag)
//        //    rVal += "\n    && adjustment." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//        //else
//        //    rVal += "\n    && adjustment." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";
//        return rVal;
//    }
//}
//class L3_ClaimWriteOffAdjustments : LedgerItemsGenerator
//{
//    public override string ODPullString(uint Guarantor, bool IsPulledFlag)
//    { //ClaimWriteOFFUnion
//        string rVal = "SELECT "
//                + "\n      '" + (int)LedgerItemTypes.NegAdjustment + "', " //2
//                + "\n      patient.Guarantor, "
//                + "\n      patient.PatNum, "
//                + "\n      claimproc.ProvNum, " // Need to Check this to make sure includes 2ndary Provider
//                + "\n      claimpayment.CheckDate, "
//                + "\n      -(claimproc.WriteOff), "
//                + "\n       claimproc.ClaimProcNum," //
//                + "\n       " + (int)ODTablesPulled.ClaimProc
//                + "\n FROM claimpayment,claimproc, patient \n WHERE "
//                + "\n      claimproc.PatNum = Patient.PatNum "
//                + "\n      && claimproc.ClaimPaymentNum = claimpayment.ClaimPaymentNum "
//                + "\n      && (claimproc.Status = 1 OR Claimproc.Status = 4)";
//        if (Guarantor != 0)
//            rVal += "\n      && Patient.Guarantor = " + Guarantor;
//        if (IsPulledFlag)
//            rVal += SQL_IsPulledCondition(ODTablesPulled.ClaimProc, IsPulledFlag);
//        //if (IsPulledFlag)
//        //    rVal += "\n    && claimproc." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//        //else
//        //    rVal += "\n    && claimproc." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";
//        return rVal;
//    }
//}
//class L4_PaymentsFromPaySplit : LedgerItemsGenerator
//{
//    public override string ODPullString(uint Guarantor, bool IsPulledFlag)
//    { //Payments1Union
//        string rVal = "SELECT "
//                + "\n      '" + (int)LedgerItemTypes.Payment + "', " //1
//                + "\n      patient.Guarantor, "
//                + "\n      patient.PatNum, "
//                + "\n      0, " //Assums there is no provider #0
//                + "\n      paysplit.DatePay, "
//                + "\n      -paysplit.SplitAmt, "
//                + "\n       paysplit.SplitNum," //
//                + "\n       " + (int)ODTablesPulled.PaySplit
//                + "\n FROM paysplit,patient \n WHERE "
//                + "\n      paysplit.PatNum = Patient.PatNum ";
//        if (Guarantor != 0)
//            rVal += "\n      && Patient.Guarantor = " + Guarantor;
//        if (IsPulledFlag)
//            rVal += SQL_IsPulledCondition(ODTablesPulled.PaySplit, IsPulledFlag);
//        //if (IsPulledFlag)
//        //    rVal += "\n    && paysplit." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//        //else
//        //    rVal += "\n    && paysplit." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";
//        return rVal;
//    }
//}
//class L5_PaymentsFromInsurance : LedgerItemsGenerator
//{
//    public override string ODPullString(uint Guarantor, bool IsPulledFlag)
//    {//Payments2InsuranceUnion
//        string rVal = "SELECT "
//                + "\n      '" + (int)LedgerItemTypes.Payment + "', " //1
//                + "\n      patient.Guarantor, "
//                + "\n      patient.PatNum, "
//                + "\n      0, " //Assums there is no provider #0
//                + "\n      claimpayment.CheckDate, "
//                + "\n      -claimproc.InsPayAmt, "
//                + "\n       claimproc.ClaimProcNum," //
//                + "\n       " + (int)ODTablesPulled.ClaimProc
//                + "\n FROM claimproc,patient,claimpayment \n WHERE "
//                + "\n      claimproc.PatNum = Patient.PatNum "
//                + "\n      && claimpayment.ClaimPaymentNum = claimproc.ClaimPaymentNum ";
//        if (Guarantor != 0)
//            rVal += "\n      && Patient.Guarantor = " + Guarantor;
//        if (IsPulledFlag)
//            rVal += SQL_IsPulledCondition(ODTablesPulled.ClaimProc, IsPulledFlag);
//        //if (IsPulledFlag)
//        //    rVal += "\n    && claimproc." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//        //else
//        //    rVal += "\n    && claimproc." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";
//        return rVal;
//    }
//}
//class L6_Charges : LedgerItemsGenerator
//{
//    public override string ODPullString(uint Guarantor, bool IsPulledFlag)
//    { //ChargesCommandUnion
//        string rVal =
//               "SELECT "
//           + "\n    " + (int)LedgerItemTypes.Charge + ","
//           + "\n    Patient.Guarantor,"
//           + "\n    Patient.PatNum,"
//           + "\n    Procedurelog.ProvNum,  "
//           + "\n    Procedurelog.ProcDate ,  "
//           + "\n    Procedurelog.ProcFee, "
//           + "\n    Procedurelog.ProcNum ," //
//                + "\n       " + (int)ODTablesPulled.ProcedureLog
//           + "\nFROM "
//           + "\n     Procedurelog, Patient"
//           + "\nWHERE "
//           + "\n     Patient.PatNum = Procedurelog.Patnum "
//           + "\n     && Procedurelog.ProcStatus = " + ((int)OpenDentBusiness.ProcStat.C).ToString(); ;
//        if (Guarantor != 0)
//            rVal += "\n      && Patient.Guarantor = " + Guarantor;
//        if (IsPulledFlag)
//            rVal += SQL_IsPulledCondition(ODTablesPulled.ProcedureLog, IsPulledFlag);
//        //if (IsPulledFlag)
//        //    rVal += "\n    && Procedurelog." + TableUtility.ColumnName_IsPulledToDansLedger + " = 1";
//        //else
//        //    rVal += "\n    && Procedurelog." + TableUtility.ColumnName_IsPulledToDansLedger + " = 0";
//        return rVal;
//    }
//}