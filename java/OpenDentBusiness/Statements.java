//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CreditCards;
import OpenDentBusiness.Db;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Statement;
import OpenDentBusiness.StatementMode;
import OpenDentBusiness.Statements;

/**
* 
*/
public class Statements   
{
    /**
    * Gets one statement from the database.
    */
    public static Statement createObject(long statementNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Statement>GetObject(MethodBase.GetCurrentMethod(), statementNum);
        }
         
        return Crud.StatementCrud.SelectOne(statementNum);
    }

    /**
    * 
    */
    public static long insert(Statement statement) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            statement.StatementNum = Meth.GetLong(MethodBase.GetCurrentMethod(), statement);
            return statement.StatementNum;
        }
         
        return Crud.StatementCrud.Insert(statement);
    }

    /**
    * 
    */
    public static void update(Statement statement) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), statement);
            return ;
        }
         
        Crud.StatementCrud.Update(statement);
    }

    /**
    * 
    */
    public static void deleteObject(Statement statement) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), statement);
            return ;
        }
         
        //validate that not already in use.
        Crud.StatementCrud.Delete(statement.StatementNum);
    }

    public static void deleteObject(long statementNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), statementNum);
            return ;
        }
         
        Crud.StatementCrud.Delete(statementNum);
    }

    /**
    * For deleting a statement when user clicks Cancel.  No need to make entry in DeletedObject table.
    */
    public static void delete(long statementNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), statementNum);
            return ;
        }
         
        Crud.StatementCrud.Delete(statementNum);
    }

    /**
    * Queries the database to determine if there are any unsent statements.
    */
    public static boolean unsentStatementsExist() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT COUNT(*) FROM statement WHERE IsSent=0";
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * Queries the database to determine if there are any unsent statements for a particular clinic.
    */
    public static boolean unsentClinicStatementsExist(long clinicNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), clinicNum);
        }
         
        if (clinicNum == 0)
        {
            return unsentStatementsExist();
        }
         
        //All clinics.
        String command = "SELECT COUNT(*) FROM statement \r\n" + 
        "\t\t\t\tLEFT JOIN patient ON statement.PatNum=patient.PatNum\r\n" + 
        "\t\t\t\tWHERE statement.IsSent=0\r\n" + 
        "\t\t\t\tAND patient.ClinicNum=" + clinicNum;
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    public static void markSent(long statementNum, DateTime dateSent) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), statementNum, dateSent);
            return ;
        }
         
        String command = "UPDATE statement SET DateSent=" + POut.date(dateSent) + ", " + "IsSent=1 WHERE StatementNum=" + POut.long(statementNum);
        Db.nonQ(command);
    }

    public static void attachDoc(long statementNum, long docNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), statementNum, docNum);
            return ;
        }
         
        String command = "UPDATE statement SET DocNum=" + POut.long(docNum) + " WHERE StatementNum=" + POut.long(statementNum);
        Db.nonQ(command);
    }

    /**
    * For orderBy, use 0 for BillingType and 1 for PatientName.
    */
    public static DataTable getBilling(boolean isSent, int orderBy, DateTime dateFrom, DateTime dateTo, long clinicNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), isSent, orderBy, dateFrom, dateTo, clinicNum);
        }
         
        DataTable table = new DataTable();
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("amountDue");
        table.Columns.Add("balTotal");
        table.Columns.Add("billingType");
        table.Columns.Add("insEst");
        table.Columns.Add("IsSent");
        table.Columns.Add("lastStatement");
        table.Columns.Add("mode");
        table.Columns.Add("name");
        table.Columns.Add("PatNum");
        table.Columns.Add("payPlanDue");
        table.Columns.Add("StatementNum");
        List<DataRow> rows = new List<DataRow>();
        String command = "SELECT BalTotal,BillingType,FName,InsEst,statement.IsSent," + "IFNULL(MAX(s2.DateSent)," + POut.Date(DateTime.MinValue) + ") LastStatement," + "LName,MiddleI,statement.Mode_,PayPlanDue,Preferred," + "statement.PatNum,statement.StatementNum " + "FROM statement " + "LEFT JOIN patient ON statement.PatNum=patient.PatNum " + "LEFT JOIN statement s2 ON s2.PatNum=patient.PatNum " + "AND s2.IsSent=1 ";
        if (PrefC.getBool(PrefName.BillingIgnoreInPerson))
        {
            command += "AND s2.Mode_ !=1 ";
        }
         
        if (orderBy == 0)
        {
            //BillingType
            command += "LEFT JOIN definition ON patient.BillingType=definition.DefNum ";
        }
         
        command += "WHERE statement.IsSent=" + POut.bool(isSent) + " ";
        //if(dateFrom.Year>1800){
        command += "AND statement.DateSent>=" + POut.date(dateFrom) + " ";
        //greater than midnight this morning
        //}
        //if(dateFrom.Year>1800){
        command += "AND statement.DateSent<" + POut.Date(dateTo.AddDays(1)) + " ";
        //less than midnight tonight
        //}
        if (clinicNum > 0)
        {
            command += "AND patient.ClinicNum=" + clinicNum + " ";
        }
         
        command += "GROUP BY BalTotal,BillingType,FName,InsEst,statement.IsSent," + "LName,MiddleI,statement.Mode_,PayPlanDue,Preferred," + "statement.PatNum,statement.StatementNum ";
        if (orderBy == 0)
        {
            //BillingType
            command += "ORDER BY definition.ItemOrder,LName,FName,MiddleI,PayPlanDue";
        }
        else
        {
            command += "ORDER BY LName,FName";
        } 
        DataTable rawTable = Db.getTable(command);
        Patient pat;
        StatementMode mode = StatementMode.Mail;
        double balTotal = new double();
        double insEst = new double();
        double payPlanDue = new double();
        DateTime lastStatement = new DateTime();
        for (int i = 0;i < rawTable.Rows.Count;i++)
        {
            row = table.NewRow();
            balTotal = PIn.Double(rawTable.Rows[i]["BalTotal"].ToString());
            insEst = PIn.Double(rawTable.Rows[i]["InsEst"].ToString());
            payPlanDue = PIn.Double(rawTable.Rows[i]["PayPlanDue"].ToString());
            row["amountDue"] = (balTotal - insEst).ToString("F");
            row["balTotal"] = balTotal.ToString("F");
                ;
            row["billingType"] = DefC.GetName(DefCat.BillingTypes, PIn.Long(rawTable.Rows[i]["BillingType"].ToString()));
            if (insEst == 0)
            {
                row["insEst"] = "";
            }
            else
            {
                row["insEst"] = insEst.ToString("F");
            } 
            row["IsSent"] = rawTable.Rows[i]["IsSent"].ToString();
            lastStatement = PIn.Date(rawTable.Rows[i]["LastStatement"].ToString());
            if (lastStatement.Year < 1880)
            {
                row["lastStatement"] = "";
            }
            else
            {
                row["lastStatement"] = lastStatement.ToShortDateString();
            } 
            mode = (StatementMode)PIn.Long(rawTable.Rows[i]["Mode_"].ToString());
            row["mode"] = Lans.g("enumStatementMode", mode.ToString());
            pat = new Patient();
            pat.LName = rawTable.Rows[i]["LName"].ToString();
            pat.FName = rawTable.Rows[i]["FName"].ToString();
            pat.Preferred = rawTable.Rows[i]["Preferred"].ToString();
            pat.MiddleI = rawTable.Rows[i]["MiddleI"].ToString();
            row["name"] = pat.getNameLF();
            row["PatNum"] = rawTable.Rows[i]["PatNum"].ToString();
            if (payPlanDue == 0)
            {
                row["payPlanDue"] = "";
            }
            else
            {
                row["payPlanDue"] = payPlanDue.ToString("F");
            } 
            row["StatementNum"] = rawTable.Rows[i]["StatementNum"].ToString();
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * This query is flawed.
    */
    public static DataTable getStatementNotesPracticeWeb(long PatientID) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), PatientID);
        }
         
        String command = "SELECT Note FROM Statement Where Patnum=" + PatientID;
        return Db.getTable(command);
    }

    /**
    * This query is flawed.
    */
    public static Statement getStatementInfoPracticeWeb(long PatientID) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Statement>GetObject(MethodBase.GetCurrentMethod(), PatientID);
        }
         
        String command = "Select SinglePatient,DateRangeFrom,DateRangeTo,Intermingled\r\n" + 
        "                        FROM statement WHERE PatNum = " + PatientID;
        return Crud.StatementCrud.SelectOne(command);
    }

    /**
    * Fetches StatementNums restricted by the DateTStamp, PatNums and a limit of records per patient. If limitPerPatient is zero all StatementNums of a patient are fetched
    */
    public static List<long> getChangedSinceStatementNums(DateTime changedSince, List<long> eligibleForUploadPatNumList, int limitPerPatient) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince, eligibleForUploadPatNumList);
        }
         
        List<long> statementnums = new List<long>();
        String limitStr = "";
        if (limitPerPatient > 0)
        {
            limitStr = "LIMIT " + limitPerPatient;
        }
         
        DataTable table = new DataTable();
        // there are possibly more efficient ways to implement this using a single sql statement but readability of the sql can be compromised
        if (eligibleForUploadPatNumList.Count > 0)
        {
            for (int i = 0;i < eligibleForUploadPatNumList.Count;i++)
            {
                String command = "SELECT StatementNum FROM statement WHERE DateTStamp > " + POut.dateT(changedSince) + " AND PatNum='" + eligibleForUploadPatNumList[i].ToString() + "' ORDER BY DateSent DESC, StatementNum DESC " + limitStr;
                table = Db.getTable(command);
                for (int j = 0;j < table.Rows.Count;j++)
                {
                    statementnums.Add(PIn.Long(table.Rows[j]["StatementNum"].ToString()));
                }
            }
        }
         
        return statementnums;
    }

    /**
    * Used along with GetChangedSinceStatementNums
    */
    public static List<Statement> getMultStatements(List<long> statementNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Statement>>GetObject(MethodBase.GetCurrentMethod(), statementNums);
        }
         
        String strStatementNums = "";
        DataTable table = new DataTable();
        if (statementNums.Count > 0)
        {
            for (int i = 0;i < statementNums.Count;i++)
            {
                if (i > 0)
                {
                    strStatementNums += "OR ";
                }
                 
                strStatementNums += "StatementNum='" + statementNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM statement WHERE " + strStatementNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        Statement[] multStatements = Crud.StatementCrud.TableToList(table).ToArray();
        List<Statement> statementList = new List<Statement>(multStatements);
        return statementList;
    }

    /**
    * Changes the value of the DateTStamp column to the current time stamp for all statements of a patient
    */
    public static void resetTimeStamps(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        String command = "UPDATE statement SET DateTStamp = CURRENT_TIMESTAMP WHERE PatNum =" + POut.long(patNum);
        Db.nonQ(command);
    }

    /**
    * Returns an email message for the patient based on the statement passed in.
    */
    public static EmailMessage getEmailMessageForStatement(Statement stmt, Patient pat) throws Exception {
        //No need to check RemotingRole; no call to db.
        EmailMessage message = new EmailMessage();
        message.PatNum = pat.PatNum;
        message.ToAddress = pat.Email;
        message.FromAddress = EmailAddresses.getByClinic(pat.ClinicNum).SenderAddress;
        String str = new String();
        if (stmt.EmailSubject != null && !StringSupport.equals(stmt.EmailSubject, ""))
        {
            str = stmt.EmailSubject;
        }
        else
        {
            //Set str to the email subject if one was already set.
            //Subject was not set.  Set str to the default billing email subject.
            str = PrefC.getString(PrefName.BillingEmailSubject);
        } 
        message.Subject = Statements.replaceVarsForEmail(str,pat);
        if (stmt.EmailBody != null && !StringSupport.equals(stmt.EmailBody, ""))
        {
            str = stmt.EmailBody;
        }
        else
        {
            //Set str to the email body if one was already set.
            //Body was not set.  Set str to the default billing email body text.
            str = PrefC.getString(PrefName.BillingEmailBodyText);
        } 
        message.BodyText = Statements.replaceVarsForEmail(str,pat);
        return message;
    }

    /**
    * Email statements allow variables to be present in the message body and subject, this method replaces those variables with the information from the patient passed in.  Simply pass in the string for the subject or body and the corresponding patient.
    */
    private static String replaceVarsForEmail(String str, Patient pat) throws Exception {
        //No need to check RemotingRole; no call to db.
        str = str.Replace("[monthlyCardsOnFile]", CreditCards.getMonthlyCardsOnFile(pat.PatNum));
        str = str.Replace("[nameF]", pat.getNameFirst());
        str = str.Replace("[nameFL]", pat.getNameFL());
        str = str.Replace("[nameFLnoPref]", pat.getNameFLnoPref());
        str = str.Replace("[namePref]", pat.Preferred);
        str = str.Replace("[PatNum]", pat.PatNum.ToString());
        str = str.Replace("[currentMonth]", DateTime.Now.ToString("MMMM"));
        return str;
    }

}


