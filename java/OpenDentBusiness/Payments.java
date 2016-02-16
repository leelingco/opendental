//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.AccountType;
import OpenDentBusiness.Db;
import OpenDentBusiness.JournalEntries;
import OpenDentBusiness.JournalEntry;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Payment;
import OpenDentBusiness.PaySplit;
import OpenDentBusiness.PIn;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Security;
import OpenDentBusiness.Transaction;
import OpenDentBusiness.Transactions;

/**
* 
*/
public class Payments   
{
    /**
    * Gets all payments for the specified patient. This has NOTHING to do with pay splits.  Must use pay splits for accounting.  This is only for display in Account module.
    */
    public static List<Payment> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Payment>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * from payment" + " WHERE PatNum=" + patNum.ToString();
        return Crud.PaymentCrud.SelectMany(command);
    }

    /**
    * Get one specific payment from db.
    */
    public static Payment getPayment(long payNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Payment>GetObject(MethodBase.GetCurrentMethod(), payNum);
        }
         
        String command = "SELECT * from payment" + " WHERE PayNum = '" + payNum + "'";
        return Crud.PaymentCrud.SelectOne(command);
    }

    /**
    * Get all specified payments.
    */
    public static List<Payment> getPayments(List<long> payNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Payment>>GetObject(MethodBase.GetCurrentMethod(), payNums);
        }
         
        if (payNums.Count == 0)
        {
            return new List<Payment>();
        }
         
        String command = "SELECT * from payment" + " WHERE";
        for (int i = 0;i < payNums.Count;i++)
        {
            if (i > 0)
            {
                command += " OR";
            }
             
            command += " PayNum=" + payNums[i].ToString();
        }
        return Crud.PaymentCrud.SelectMany(command);
    }

    /**
    * Gets all payments attached to a single deposit.
    */
    public static List<Payment> getForDeposit(long depositNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Payment>>GetObject(MethodBase.GetCurrentMethod(), depositNum);
        }
         
        String command = "SELECT * FROM payment " + "WHERE DepositNum = " + POut.long(depositNum) + " " + "ORDER BY PayDate";
        return Crud.PaymentCrud.SelectMany(command);
    }

    /**
    * Gets all unattached payments for a new deposit slip.  Excludes payments before dateStart.  There is a chance payTypes might be of length 1 or even 0.
    */
    public static List<Payment> getForDeposit(DateTime dateStart, long clinicNum, List<long> payTypes) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Payment>>GetObject(MethodBase.GetCurrentMethod(), dateStart, clinicNum, payTypes);
        }
         
        String command = "SELECT * FROM payment " + "WHERE DepositNum = 0 " + "AND PayDate >= " + POut.date(dateStart) + " ";
        if (clinicNum != 0)
        {
            command += "AND ClinicNum=" + POut.long(clinicNum);
        }
         
        for (int i = 0;i < payTypes.Count;i++)
        {
            if (i == 0)
            {
                command += " AND (";
            }
            else
            {
                command += " OR ";
            } 
            command += "PayType=" + POut.Long(payTypes[i]);
            if (i == payTypes.Count - 1)
            {
                command += ")";
            }
             
        }
        command += " ORDER BY PayDate";
        Object[] parameters;
        Plugins.hookAddCode(null,"Payments.GetForDeposit_beforequeryrun",parameters);
        command = (String)parameters[0];
        return Crud.PaymentCrud.SelectMany(command);
    }

    /**
    * Updates this payment.  Must make sure to update the datePay of all attached paysplits so that they are always in synch.  Also need to manually set IsSplit before here.  Will throw an exception if bad date, so surround by try-catch.  Set excludeDepositNum to true from FormPayment to prevent collision from another worksation that just deleted a deposit.
    */
    public static void update(Payment pay, boolean excludeDepositNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pay, excludeDepositNum);
            return ;
        }
         
        if (pay.PayDate.Date > DateTime.Today)
        {
            throw new ApplicationException(Lans.g("Payments","Date must not be a future date."));
        }
         
        if (pay.PayDate.Year < 1880)
        {
            throw new ApplicationException(Lans.g("Payments","Invalid date"));
        }
         
        //the functionality below needs to be taken care of before calling the function:
        /*string command="SELECT DepositNum,PayAmt FROM payment "
        					+"WHERE PayNum="+POut.PInt(PayNum);
        			DataConnection dcon=new DataConnection();
        			DataTable table=Db.GetTable(command);
        			if(table.Rows.Count==0) {
        				return;
        			}
        			if(table.Rows[0][0].ToString()!="0"//if payment is already attached to a deposit
        					&& PIn.PDouble(table.Rows[0][1].ToString())!=PayAmt) {//and PayAmt changes
        				throw new ApplicationException(Lans.g("Payments","Not allowed to change the amount on payments attached to deposits."));
        			}*/
        Crud.PaymentCrud.Update(pay);
        if (!excludeDepositNum)
        {
            String command = "UPDATE payment SET DepositNum=" + POut.long(pay.DepositNum) + " WHERE payNum = " + POut.long(pay.PayNum);
            Db.nonQ(command);
        }
         
    }

    /**
    * There's only one place in the program where this is called from.  Date is today, so no need to validate the date.
    */
    public static long insert(Payment pay) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            pay.PayNum = Meth.GetLong(MethodBase.GetCurrentMethod(), pay);
            return pay.PayNum;
        }
         
        return Crud.PaymentCrud.Insert(pay);
    }

    /**
    * There's only one place in the program where this is called from.  Date is today, so no need to validate the date.
    */
    public static long insert(Payment pay, boolean useExistingPK) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            pay.PayNum = Meth.GetLong(MethodBase.GetCurrentMethod(), pay, useExistingPK);
            return pay.PayNum;
        }
         
        return Crud.PaymentCrud.Insert(pay, useExistingPK);
    }

    /**
    * Deletes the payment as well as all splits.  Surround by try catch, because it will throw an exception if trying to delete a payment attached to a deposit.
    */
    public static void delete(Payment pay) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pay);
            return ;
        }
         
        String command = "SELECT DepositNum,PayAmt FROM payment WHERE PayNum=" + POut.long(pay.PayNum);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return ;
        }
         
        //if payment is already attached to a deposit
        if (!StringSupport.equals(table.Rows[0]["DepositNum"].ToString(), "0") && PIn.Double(table.Rows[0]["PayAmt"].ToString()) != 0)
        {
            throw new ApplicationException(Lans.g("Payments","Not allowed to delete a payment attached to a deposit."));
        }
         
        //and it's not new
        command = "DELETE from payment WHERE payNum = '" + pay.PayNum.ToString() + "'";
        Db.nonQ(command);
        //this needs to be improved to handle EstBal
        command = "DELETE from paysplit WHERE payNum = '" + pay.PayNum.ToString() + "'";
        Db.nonQ(command);
    }

    //PaySplit[] splitList=PaySplits.RefreshPaymentList(PayNum);
    //for(int i=0;i<splitList.Length;i++){
    //	splitList[i].Delete();
    //}
    /**
    * Called just before Allocate in FormPayment.butOK click.  If true, then it will prompt the user before allocating.
    */
    public static boolean allocationRequired(double payAmt, long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), payAmt, patNum);
        }
         
        String command = "SELECT EstBalance FROM patient " + "WHERE PatNum = " + POut.long(patNum);
        DataTable table = Db.getTable(command);
        double estBal = 0;
        if (table.Rows.Count > 0)
        {
            estBal = PIn.Double(table.Rows[0][0].ToString());
        }
         
        if (!PrefC.getBool(PrefName.BalancesDontSubtractIns))
        {
            command = "SELECT SUM(InsPayEst)+SUM(Writeoff) \r\n" + 
            "\t\t\t\t\tFROM claimproc\r\n" + 
            "\t\t\t\t\tWHERE PatNum=" + POut.long(patNum) + " " + "AND Status=0";
            //NotReceived
            table = Db.getTable(command);
            if (table.Rows.Count > 0)
            {
                estBal -= PIn.Double(table.Rows[0][0].ToString());
            }
             
        }
         
        if (payAmt > estBal)
        {
            return true;
        }
         
        return false;
    }

    /**
    * Only Called only from FormPayment.butOK click.  Only called if the user did not enter any splits.  Usually just adds one split for the current patient.  But if that would take the balance negative, then it loops through all other family members and creates splits for them.  It might still take the current patient negative once all other family members are zeroed out.
    */
    public static List<PaySplit> allocate(Payment pay) throws Exception {
        //double amtTot,int patNum,Payment payNum){
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PaySplit>>GetObject(MethodBase.GetCurrentMethod(), pay);
        }
         
        String command = "SELECT Guarantor FROM patient " + "WHERE PatNum = " + POut.long(pay.PatNum);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return new List<PaySplit>();
        }
         
        //NotReceived
        command = "SELECT patient.PatNum,EstBalance,PriProv,SUM(InsPayEst)+SUM(Writeoff) insEst_ " + "FROM patient " + "LEFT JOIN claimproc ON patient.PatNum=claimproc.PatNum " + "AND Status=0 " + "WHERE Guarantor = " + table.Rows[0][0].ToString() + " " + "GROUP BY  patient.PatNum,EstBalance,PriProv";
        //+" ORDER BY PatNum!="+POut.PInt(pay.PatNum);//puts current patient in position 0 //Oracle does not allow
        table = Db.getTable(command);
        List<Patient> pats = new List<Patient>();
        Patient pat;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //first, put the current patient at position 0.
            if (table.Rows[i]["PatNum"].ToString() == pay.PatNum.ToString())
            {
                pat = new Patient();
                pat.PatNum = PIn.Long(table.Rows[i][0].ToString());
                pat.EstBalance = PIn.Double(table.Rows[i][1].ToString());
                if (!PrefC.getBool(PrefName.BalancesDontSubtractIns))
                {
                    pat.EstBalance -= PIn.Double(table.Rows[i]["insEst_"].ToString());
                }
                 
                pat.PriProv = PIn.Long(table.Rows[i][2].ToString());
                pats.Add(pat.copy());
            }
             
        }
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //then, do all the rest of the patients.
            if (table.Rows[i]["PatNum"].ToString() == pay.PatNum.ToString())
            {
                continue;
            }
             
            pat = new Patient();
            pat.PatNum = PIn.Long(table.Rows[i][0].ToString());
            pat.EstBalance = PIn.Double(table.Rows[i][1].ToString());
            if (!PrefC.getBool(PrefName.BalancesDontSubtractIns))
            {
                pat.EstBalance -= PIn.Double(table.Rows[i]["insEst_"].ToString());
            }
             
            pat.PriProv = PIn.Long(table.Rows[i][2].ToString());
            pats.Add(pat.copy());
        }
        //first calculate all the amounts
        double amtRemain = pay.PayAmt;
        //start off with the full amount
        double[] amtSplits = new double[pats.Count];
        for (int i = 0;i < pats.Count;i++)
        {
            //loop through each family member, starting with current
            if (pats[i].EstBalance == 0 || pats[i].EstBalance < 0)
            {
                continue;
            }
             
            //don't apply paysplits to anyone with a negative balance
            if (amtRemain < pats[i].EstBalance)
            {
                //entire remainder can be allocated to this patient
                amtSplits[i] = amtRemain;
                amtRemain = 0;
                break;
            }
            else
            {
                //amount remaining is more than or equal to the estBal for this family member
                amtSplits[i] = pats[i].EstBalance;
                amtRemain -= pats[i].EstBalance;
            } 
        }
        //add any remainder to the split for this patient
        amtSplits[0] += amtRemain;
        //now create a split for each non-zero amount
        PaySplit PaySplitCur;
        List<PaySplit> retVal = new List<PaySplit>();
        for (int i = 0;i < pats.Count;i++)
        {
            if (amtSplits[i] == 0)
            {
                continue;
            }
             
            PaySplitCur = new PaySplit();
            PaySplitCur.PatNum = pats[i].PatNum;
            PaySplitCur.PayNum = pay.PayNum;
            PaySplitCur.ProcDate = pay.PayDate;
            PaySplitCur.DatePay = pay.PayDate;
            PaySplitCur.ClinicNum = pay.ClinicNum;
            PaySplitCur.ProvNum = Patients.GetProvNum(pats[i]);
            PaySplitCur.SplitAmt = Math.Round(amtSplits[i], CultureInfo.CurrentCulture.NumberFormat.CurrencyDecimalDigits);
            //PaySplitCur.InsertOrUpdate(true);
            retVal.Add(PaySplitCur);
        }
        return retVal;
    }

    //finally, adjust each EstBalance, but no need to do current patient
    //This no longer works here.  Must do it when closing payment window somehow
    /*for(int i=1;i<pats.Length;i++){
    				if(amtSplits[i]==0){
    					continue;
    				}
    				command="UPDATE patient SET EstBalance=EstBalance-"+POut.PDouble(amtSplits[i])
    					+" WHERE PatNum="+POut.PInt(pats[i].PatNum);
    				Db.NonQ(command);
    			}*/
    /**
    * This does all the validation before calling AlterLinkedEntries.  It had to be separated like this because of the complexity of saving a payment.  Surround with try-catch.  Will throw an exception if user is trying to change, but not allowed.  Will return false if no synch with accounting is needed.  Use -1 for newAcct to indicate no change.
    */
    public static boolean validateLinkedEntries(double oldAmt, double newAmt, boolean isNew, long payNum, long newAcct) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (!Accounts.paymentsLinked())
        {
            return false;
        }
         
        //user has not even set up accounting links, so no need to check any of this.
        boolean amtChanged = false;
        if (oldAmt != newAmt)
        {
            amtChanged = true;
        }
         
        Transaction trans = Transactions.getAttachedToPayment(payNum);
        //this gives us the oldAcctNum
        if (trans == null && (newAcct == 0 || newAcct == -1))
        {
            return false;
        }
         
        //if there was no previous link, and there is no attempt to create a link
        //no synch needed
        if (trans == null)
        {
            return true;
        }
         
        //no previous link, but user is trying to create one. newAcct>0.
        //new transaction will be required
        //at this point, we have established that there is a previous transaction.
        //If payment is attached to a transaction which is more than 48 hours old, then not allowed to change.
        if (amtChanged && trans.DateTimeEntry < MiscData.getNowDateTime().AddDays(-2))
        {
            throw new ApplicationException(Lans.g("Payments","Not allowed to change amount that is more than 48 hours old.  This payment is already attached to an accounting transaction.  You will need to detach it from within the accounting section of the program."));
        }
         
        if (amtChanged && Transactions.isReconciled(trans))
        {
            throw new ApplicationException(Lans.g("Payments","Not allowed to change amount.  This payment is attached to an accounting transaction that has been reconciled.  You will need to detach it from within the accounting section of the program."));
        }
         
        List<JournalEntry> jeL = JournalEntries.getForTrans(trans.TransactionNum);
        long oldAcct = 0;
        JournalEntry jeDebit = null;
        JournalEntry jeCredit = null;
        double absOld = oldAmt;
        //the absolute value of the old amount
        if (oldAmt < 0)
        {
            absOld = -oldAmt;
        }
         
        for (int i = 0;i < jeL.Count;i++)
        {
            //we make sure down below that this count is exactly 2.
            if (Accounts.GetAccount(jeL[i].AccountNum).AcctType == AccountType.Asset)
            {
                oldAcct = jeL[i].AccountNum;
            }
             
            if (jeL[i].DebitAmt == absOld)
            {
                jeDebit = jeL[i];
            }
             
            //old credit entry
            if (jeL[i].CreditAmt == absOld)
            {
                jeCredit = jeL[i];
            }
             
        }
        if (jeCredit == null || jeDebit == null)
        {
            throw new ApplicationException(Lans.g("Payments","Not able to automatically make changes in the accounting section to match the change made here.  You will need to detach it from within the accounting section."));
        }
         
        if (oldAcct == 0)
        {
            throw new ApplicationException(Lans.g("Payments","Could not locate linked transaction.  You will need to detach it manually from within the accounting section of the program."));
        }
         
        //something must have gone wrong.  But this should never happen
        if (newAcct == 0)
        {
            return true;
        }
         
        //detaching it from a linked transaction.
        //We will delete the transaction
        boolean acctChanged = false;
        if (newAcct != -1 && oldAcct != newAcct)
        {
            acctChanged = true;
        }
         
        //changing linked acctNum
        if (!amtChanged && !acctChanged)
        {
            return false;
        }
         
        //no changes being made to amount or account, so no synch required.
        if (jeL.Count != 2)
        {
            throw new ApplicationException(Lans.g("Payments","Not able to automatically change the amount in the accounting section to match the change made here.  You will need to detach it from within the accounting section."));
        }
         
        return true;
    }

    //Amount or account changed on an existing linked transaction.
    /**
    * Only called once from FormPayment when trying to change an amount or an account on a payment that's already linked to the Accounting section or when trying to create a new link.  This automates updating the Accounting section.  Do not surround with try-catch, because it was already validated in ValidateLinkedEntries above.  Use -1 for newAcct to indicate no changed. The name is required to give descriptions to new entries.
    */
    public static void alterLinkedEntries(double oldAmt, double newAmt, boolean isNew, long payNum, long newAcct, DateTime payDate, String patName) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (!Accounts.paymentsLinked())
        {
            return ;
        }
         
        //user has not even set up accounting links.
        boolean amtChanged = false;
        if (oldAmt != newAmt)
        {
            amtChanged = true;
        }
         
        Transaction trans = Transactions.getAttachedToPayment(payNum);
        //this gives us the oldAcctNum
        double absNew = newAmt;
        //absolute value of the new amount
        if (newAmt < 0)
        {
            absNew = -newAmt;
        }
         
        //if(trans==null && (newAcct==0 || newAcct==-1)) {//then this method will not even be called
        if (trans == null)
        {
            //no previous link, but user is trying to create one.
            //this is the only case where a new trans is required.
            trans = new Transaction();
            trans.PayNum = payNum;
            trans.UserNum = Security.getCurUser().UserNum;
            Transactions.insert(trans);
            //sets entry date
            //first the deposit entry
            JournalEntry je = new JournalEntry();
            je.AccountNum = newAcct;
            //DepositAccounts[comboDepositAccount.SelectedIndex];
            je.CheckNumber = Lans.g("Payments","DEP");
            je.DateDisplayed = payDate;
            //it would be nice to add security here.
            if (absNew == newAmt)
            {
                //amount is positive
                je.DebitAmt = newAmt;
            }
            else
            {
                je.CreditAmt = absNew;
            } 
            je.Memo = Lans.g("Payments","Payment -") + " " + patName;
            je.Splits = Accounts.getDescript(PrefC.getLong(PrefName.AccountingCashIncomeAccount));
            je.TransactionNum = trans.TransactionNum;
            JournalEntries.insert(je);
            //then, the income entry
            je = new JournalEntry();
            je.AccountNum = PrefC.getLong(PrefName.AccountingCashIncomeAccount);
            //je.CheckNumber=;
            je.DateDisplayed = payDate;
            //it would be nice to add security here.
            if (absNew == newAmt)
            {
                //amount is positive
                je.CreditAmt = newAmt;
            }
            else
            {
                je.DebitAmt = absNew;
            } 
            je.Memo = Lans.g("Payments","Payment -") + " " + patName;
            je.Splits = Accounts.getDescript(newAcct);
            je.TransactionNum = trans.TransactionNum;
            JournalEntries.insert(je);
            return ;
        }
         
        //at this point, we have established that there is a previous transaction.
        List<JournalEntry> jeL = JournalEntries.getForTrans(trans.TransactionNum);
        long oldAcct = 0;
        JournalEntry jeDebit = null;
        JournalEntry jeCredit = null;
        boolean signChanged = false;
        double absOld = oldAmt;
        //the absolute value of the old amount
        if (oldAmt < 0)
        {
            absOld = -oldAmt;
        }
         
        if (oldAmt < 0 && newAmt > 0)
        {
            signChanged = true;
        }
         
        if (oldAmt > 0 && newAmt < 0)
        {
            signChanged = true;
        }
         
        for (int i = 0;i < 2;i++)
        {
            if (Accounts.GetAccount(jeL[i].AccountNum).AcctType == AccountType.Asset)
            {
                oldAcct = jeL[i].AccountNum;
            }
             
            if (jeL[i].DebitAmt == absOld)
            {
                jeDebit = jeL[i];
            }
             
            //old credit entry
            if (jeL[i].CreditAmt == absOld)
            {
                jeCredit = jeL[i];
            }
             
        }
        //Already validated that both je's are not null, and that oldAcct is not 0.
        if (newAcct == 0)
        {
            //detaching it from a linked transaction. We will delete the transaction
            //we don't care about the amount
            Transactions.delete(trans);
            return ;
        }
         
        //we need to make sure this doesn't throw any exceptions by carefully checking all
        //possibilities in the validation routine above.
        //Either the amount or the account changed on an existing linked transaction.
        boolean acctChanged = false;
        if (newAcct != -1 && oldAcct != newAcct)
        {
            acctChanged = true;
        }
         
        //changing linked acctNum
        if (amtChanged)
        {
            if (signChanged)
            {
                jeDebit.DebitAmt = 0;
                jeDebit.CreditAmt = absNew;
                jeCredit.DebitAmt = absNew;
                jeCredit.CreditAmt = 0;
            }
            else
            {
                jeDebit.DebitAmt = absNew;
                jeCredit.CreditAmt = absNew;
            } 
        }
         
        if (acctChanged)
        {
            if (jeDebit.AccountNum == oldAcct)
            {
                jeDebit.AccountNum = newAcct;
            }
             
            if (jeCredit.AccountNum == oldAcct)
            {
                jeCredit.AccountNum = newAcct;
            }
             
        }
         
        JournalEntries.update(jeDebit);
        JournalEntries.update(jeCredit);
    }

    /**
    * Used for display in ProcEdit. List MUST include the requested payment. Use GetPayments to get the list.
    */
    public static Payment getFromList(long payNum, List<Payment> List) throws Exception {
        for (int i = 0;i < List.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (List[i].PayNum == payNum)
            {
                return List[i];
            }
             
        }
        return null;
    }

}


//should never happen
/*
		///<summary></summary>
		public static string GetInfo(int payNum){
			string retStr;
			Payment Cur=GetPayment(payNum);
			retStr=DefB.GetName(DefCat.PaymentTypes,Cur.PayType);
			if(Cur.IsSplit) retStr=retStr
				+"  "+Cur.PayAmt.ToString("c")
				+"  "+Cur.PayDate.ToString("d")
				+" "+Lans.g("Payments","split between patients");
			return retStr;
		}*/