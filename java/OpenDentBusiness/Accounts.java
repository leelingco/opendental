//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Account;
import OpenDentBusiness.AccountingAutoPays;
import OpenDentBusiness.AccountingSoftware;
import OpenDentBusiness.AccountType;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class Accounts   
{
    private static Account[] listLong = new Account[]();
    private static Account[] listShort = new Account[]();
    /**
    * 
    */
    public static Account[] getListLong() throws Exception {
        if (listLong == null)
        {
            refreshCache();
        }
         
        return listLong;
    }

    public static void setListLong(Account[] value) throws Exception {
        listLong = value;
    }

    /**
    * Used for display. Does not include inactive
    */
    public static Account[] getListShort() throws Exception {
        if (listShort == null)
        {
            refreshCache();
        }
         
        return listShort;
    }

    public static void setListShort(Account[] value) throws Exception {
        listShort = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM account " + " ORDER BY AcctType,Description";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Account";
        fillCache(table);
        return table;
    }

    //on the server side
    private static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listLong = Crud.AccountCrud.TableToList(table).ToArray();
        List<Account> list = new List<Account>();
        for (int i = 0;i < listLong.Length;i++)
        {
            if (!listLong[i].Inactive)
            {
                list.Add(listLong[i].Clone());
            }
             
        }
        listShort = list.ToArray();
    }

    /**
    * 
    */
    public static long insert(Account acct) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            acct.AccountNum = Meth.GetLong(MethodBase.GetCurrentMethod(), acct);
            return acct.AccountNum;
        }
         
        return Crud.AccountCrud.Insert(acct);
    }

    /**
    * 
    */
    public static void update(Account acct) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), acct);
            return ;
        }
         
        Crud.AccountCrud.Update(acct);
    }

    /**
    * Loops through listLong to find a description for the specified account.  0 returns an empty string.
    */
    public static String getDescript(long accountNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (accountNum == 0)
        {
            return "";
        }
         
        for (int i = 0;i < getListLong().Length;i++)
        {
            if (getListLong()[i].AccountNum == accountNum)
            {
                return getListLong()[i].Description;
            }
             
        }
        return "";
    }

    /**
    * Loops through listLong to find an account.  Will return null if accountNum is 0.
    */
    public static Account getAccount(long accountNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (accountNum == 0)
        {
            return null;
        }
         
        for (int i = 0;i < getListLong().Length;i++)
        {
            if (getListLong()[i].AccountNum == accountNum)
            {
                return getListLong()[i].Clone();
            }
             
        }
        return null;
    }

    //just in case
    /**
    * Throws exception if account is in use.
    */
    public static void delete(Account acct) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), acct);
            return ;
        }
         
        //check to see if account has any journal entries
        String command = "SELECT COUNT(*) FROM journalentry WHERE AccountNum=" + POut.long(acct.AccountNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("FormAccountEdit","Not allowed to delete an account with existing journal entries."));
        }
         
        //Check various preference entries
        command = "SELECT ValueString FROM preference WHERE PrefName='AccountingDepositAccounts'";
        String result = Db.getCount(command);
        String[] strArray = result.Split(new char[]{ ',' });
        for (int i = 0;i < strArray.Length;i++)
        {
            if (strArray[i] == acct.AccountNum.ToString())
            {
                throw new ApplicationException(Lans.g("FormAccountEdit","Account is in use in the setup section."));
            }
             
        }
        command = "SELECT ValueString FROM preference WHERE PrefName='AccountingIncomeAccount'";
        result = Db.getCount(command);
        if (StringSupport.equals(result, acct.AccountNum.ToString()))
        {
            throw new ApplicationException(Lans.g("FormAccountEdit","Account is in use in the setup section."));
        }
         
        command = "SELECT ValueString FROM preference WHERE PrefName='AccountingCashIncomeAccount'";
        result = Db.getCount(command);
        if (StringSupport.equals(result, acct.AccountNum.ToString()))
        {
            throw new ApplicationException(Lans.g("FormAccountEdit","Account is in use in the setup section."));
        }
         
        for (int i = 0;i < AccountingAutoPays.getListt().Count;i++)
        {
            //check AccountingAutoPay entries
            strArray = AccountingAutoPays.getListt()[i].PickList.Split(new char[]{ ',' });
            for (int s = 0;s < strArray.Length;s++)
            {
                if (strArray[s] == acct.AccountNum.ToString())
                {
                    throw new ApplicationException(Lans.g("FormAccountEdit","Account is in use in the setup section."));
                }
                 
            }
        }
        command = "DELETE FROM account WHERE AccountNum = " + POut.long(acct.AccountNum);
        Db.nonQ(command);
    }

    /**
    * Used to test the sign on debits and credits for the five different account types
    */
    public static boolean debitIsPos(AccountType type) throws Exception {
        switch(type)
        {
            case Asset: 
            case Expense: 
                return true;
            case Liability: 
            case Equity: 
            case Income: 
                return false;
        
        }
        return true;
    }

    //No need to check RemotingRole; no call to db.
    //because liabilities and equity are treated the same
    //will never happen
    /**
    * Gets the balance of an account directly from the database.
    */
    public static double getBalance(long accountNum, AccountType acctType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<double>GetObject(MethodBase.GetCurrentMethod(), accountNum, acctType);
        }
         
        String command = "SELECT SUM(DebitAmt),SUM(CreditAmt) FROM journalentry " + "WHERE AccountNum=" + POut.long(accountNum) + " GROUP BY AccountNum";
        DataTable table = Db.getTable(command);
        double debit = 0;
        double credit = 0;
        if (table.Rows.Count > 0)
        {
            debit = PIn.Double(table.Rows[0][0].ToString());
            credit = PIn.Double(table.Rows[0][1].ToString());
        }
         
        if (debitIsPos(acctType))
        {
            return debit - credit;
        }
        else
        {
            return credit - debit;
        } 
    }

    /*}
    			catch {
    				Debug.WriteLine(command);
    				MessageBox.Show(command);
    			}
    			return 0;*/
    /**
    * Checks the loaded prefs to see if user has setup deposit linking.  Returns true if so.
    */
    public static boolean depositsLinked() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (PrefC.getInt(PrefName.AccountingSoftware) == ((Enum)AccountingSoftware.QuickBooks).ordinal())
        {
            if (StringSupport.equals(PrefC.getString(PrefName.QuickBooksDepositAccounts), ""))
            {
                return false;
            }
             
            if (StringSupport.equals(PrefC.getString(PrefName.QuickBooksIncomeAccount), ""))
            {
                return false;
            }
             
        }
        else
        {
            if (StringSupport.equals(PrefC.getString(PrefName.AccountingDepositAccounts), ""))
            {
                return false;
            }
             
            if (PrefC.getLong(PrefName.AccountingIncomeAccount) == 0)
            {
                return false;
            }
             
        } 
        return true;
    }

    //might add a few more checks later.
    /**
    * Checks the loaded prefs and accountingAutoPays to see if user has setup auto pay linking.  Returns true if so.
    */
    public static boolean paymentsLinked() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (AccountingAutoPays.getListt().Count == 0)
        {
            return false;
        }
         
        if (PrefC.getLong(PrefName.AccountingCashIncomeAccount) == 0)
        {
            return false;
        }
         
        return true;
    }

    //might add a few more checks later.
    /**
    * 
    */
    public static long[] getDepositAccounts() throws Exception {
        //No need to check RemotingRole; no call to db.
        String depStr = PrefC.getString(PrefName.AccountingDepositAccounts);
        String[] depStrArray = depStr.Split(new char[]{ ',' });
        ArrayList depAL = new ArrayList();
        for (int i = 0;i < depStrArray.Length;i++)
        {
            if (StringSupport.equals(depStrArray[i], ""))
            {
                continue;
            }
             
            depAL.Add(PIn.Long(depStrArray[i]));
        }
        long[] retVal = new long[depAL.Count];
        depAL.CopyTo(retVal);
        return retVal;
    }

    /**
    * 
    */
    public static List<String> getDepositAccountsQB() throws Exception {
        //No need to check RemotingRole; no call to db.
        String depStr = PrefC.getString(PrefName.QuickBooksDepositAccounts);
        String[] depStrArray = depStr.Split(new char[]{ ',' });
        List<String> retVal = new List<String>();
        for (int i = 0;i < depStrArray.Length;i++)
        {
            if (StringSupport.equals(depStrArray[i], ""))
            {
                continue;
            }
             
            retVal.Add(depStrArray[i]);
        }
        return retVal;
    }

    /**
    * 
    */
    public static List<String> getIncomeAccountsQB() throws Exception {
        //No need to check RemotingRole; no call to db.
        String incomeStr = PrefC.getString(PrefName.QuickBooksIncomeAccount);
        String[] incomeStrArray = incomeStr.Split(new char[]{ ',' });
        List<String> retVal = new List<String>();
        for (int i = 0;i < incomeStrArray.Length;i++)
        {
            if (StringSupport.equals(incomeStrArray[i], ""))
            {
                continue;
            }
             
            retVal.Add(incomeStrArray[i]);
        }
        return retVal;
    }

    /**
    * Gets the full list to display in the Chart of Accounts, including balances.
    */
    public static DataTable getFullList(DateTime asOfDate, boolean showInactive) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), asOfDate, showInactive);
        }
         
        DataTable table = new DataTable("Accounts");
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("type");
        table.Columns.Add("Description");
        table.Columns.Add("balance");
        table.Columns.Add("BankNumber");
        table.Columns.Add("inactive");
        table.Columns.Add("color");
        table.Columns.Add("AccountNum");
        //but we won't actually fill this table with rows until the very end.  It's more useful to use a List<> for now.
        List<DataRow> rows = new List<DataRow>();
        //first, the entire history for the asset, liability, and equity accounts (except Retained Earnings)-----------
        String command = "SELECT account.AcctType, account.Description, account.AccountNum, " + "SUM(DebitAmt) AS SumDebit, SUM(CreditAmt) AS SumCredit, account.BankNumber, account.Inactive, account.AccountColor " + "FROM account " + "LEFT JOIN journalentry ON journalentry.AccountNum=account.AccountNum AND " + "DateDisplayed <= " + POut.date(asOfDate) + " WHERE AcctType<=2 ";
        if (!showInactive)
        {
            command += "AND Inactive=0 ";
        }
         
        command += "GROUP BY account.AccountNum, account.AcctType, account.Description, account.BankNumber," + "account.Inactive, account.AccountColor ORDER BY AcctType, Description";
        DataTable rawTable = Db.getTable(command);
        AccountType aType = AccountType.Asset;
        double debit = 0;
        double credit = 0;
        for (int i = 0;i < rawTable.Rows.Count;i++)
        {
            row = table.NewRow();
            aType = (AccountType)PIn.Long(rawTable.Rows[i]["AcctType"].ToString());
            row["type"] = Lans.g("enumAccountType", aType.ToString());
            row["Description"] = rawTable.Rows[i]["Description"].ToString();
            debit = PIn.Decimal(rawTable.Rows[i]["SumDebit"].ToString());
            credit = PIn.Decimal(rawTable.Rows[i]["SumCredit"].ToString());
            if (debitIsPos(aType))
            {
                row["balance"] = (debit - credit).ToString("N");
            }
            else
            {
                row["balance"] = (credit - debit).ToString("N");
            } 
            row["BankNumber"] = rawTable.Rows[i]["BankNumber"].ToString();
            if (StringSupport.equals(rawTable.Rows[i]["Inactive"].ToString(), "0"))
            {
                row["inactive"] = "";
            }
            else
            {
                row["inactive"] = "X";
            } 
            row["color"] = rawTable.Rows[i]["AccountColor"].ToString();
            //it will be an unsigned int at this point.
            row["AccountNum"] = rawTable.Rows[i]["AccountNum"].ToString();
            rows.Add(row);
        }
        //now, the Retained Earnings (auto) account-----------------------------------------------------------------
        DateTime firstofYear = new DateTime(asOfDate.Year, 1, 1);
        command = "SELECT AcctType, SUM(DebitAmt) AS SumDebit, SUM(CreditAmt) AS SumCredit " + "FROM account,journalentry " + "WHERE journalentry.AccountNum=account.AccountNum " + "AND DateDisplayed < " + POut.date(firstofYear) + " AND (AcctType=3 OR AcctType=4) " + "GROUP BY AcctType ORDER BY AcctType";
        //all from previous years
        //income or expenses
        //income first, but could return zero rows.
        rawTable = Db.getTable(command);
        double balance = 0;
        for (int i = 0;i < rawTable.Rows.Count;i++)
        {
            aType = (AccountType)PIn.Long(rawTable.Rows[i]["AcctType"].ToString());
            debit = PIn.Decimal(rawTable.Rows[i]["SumDebit"].ToString());
            credit = PIn.Decimal(rawTable.Rows[i]["SumCredit"].ToString());
            //this works for both income and expenses, because we are subracting expenses, so signs cancel
            balance += credit - debit;
        }
        row = table.NewRow();
        row["type"] = Lans.g("enumAccountType", AccountType.Equity.ToString());
        row["Description"] = Lans.g("Accounts","Retained Earnings (auto)");
        row["balance"] = balance.ToString("N");
        row["BankNumber"] = "";
        row["color"] = Color.White.ToArgb();
        row["AccountNum"] = "0";
        rows.Add(row);
        //finally, income and expenses------------------------------------------------------------------------------
        command = "SELECT account.AcctType, account.Description, account.AccountNum, " + "SUM(DebitAmt) AS SumDebit, SUM(CreditAmt) AS SumCredit, account.BankNumber, account.Inactive, account.AccountColor " + "FROM account " + "LEFT JOIN journalentry ON journalentry.AccountNum=account.AccountNum " + "AND DateDisplayed <= " + POut.date(asOfDate) + " AND DateDisplayed >= " + POut.date(firstofYear) + " WHERE (AcctType=3 OR AcctType=4) ";
        //only for this year
        if (!showInactive)
        {
            command += "AND Inactive=0 ";
        }
         
        command += "GROUP BY account.AccountNum, account.AcctType, account.Description, account.BankNumber," + "account.Inactive, account.AccountColor ORDER BY AcctType, Description";
        rawTable = Db.getTable(command);
        for (int i = 0;i < rawTable.Rows.Count;i++)
        {
            row = table.NewRow();
            aType = (AccountType)PIn.Long(rawTable.Rows[i]["AcctType"].ToString());
            row["type"] = Lans.g("enumAccountType", aType.ToString());
            row["Description"] = rawTable.Rows[i]["Description"].ToString();
            debit = PIn.Decimal(rawTable.Rows[i]["SumDebit"].ToString());
            credit = PIn.Decimal(rawTable.Rows[i]["SumCredit"].ToString());
            if (debitIsPos(aType))
            {
                row["balance"] = (debit - credit).ToString("N");
            }
            else
            {
                row["balance"] = (credit - debit).ToString("N");
            } 
            row["BankNumber"] = rawTable.Rows[i]["BankNumber"].ToString();
            if (StringSupport.equals(rawTable.Rows[i]["Inactive"].ToString(), "0"))
            {
                row["inactive"] = "";
            }
            else
            {
                row["inactive"] = "X";
            } 
            row["color"] = rawTable.Rows[i]["AccountColor"].ToString();
            //it will be an unsigned int at this point.
            row["AccountNum"] = rawTable.Rows[i]["AccountNum"].ToString();
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

}


