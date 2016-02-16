//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DashboardAR;
import OpenDentBusiness.DashboardARs;
import OpenDentBusiness.Db;
import OpenDentBusiness.Ledgers;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

//using System.Windows.Controls;//need a reference for this dll, or get msgbox into UI layer.
public class DashboardQueries   
{
    public static DataTable getProvList(DateTime dt) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), dt);
        }
         
        String command = new String();
        command = "DROP TABLE IF EXISTS tempdash;";
        Db.nonQ(command);
        command = "CREATE TABLE tempdash (\r\n" + 
        "\t\t\t\tProvNum bigint NOT NULL PRIMARY KEY,\r\n" + 
        "\t\t\t\tproduction decimal NOT NULL,\r\n" + 
        "\t\t\t\tincome decimal NOT NULL\r\n" + 
        "\t\t\t\t) DEFAULT CHARSET=utf8";
        Db.nonQ(command);
        //providers
        command = "INSERT INTO tempdash (ProvNum)\r\n" + 
        "\t\t\t\tSELECT ProvNum\r\n" + 
        "\t\t\t\tFROM provider WHERE IsHidden=0\r\n" + 
        "\t\t\t\tORDER BY ItemOrder";
        Db.nonQ(command);
        //production--------------------------------------------------------------------
        //procs
        command = "UPDATE tempdash \r\n" + 
        "\t\t\t\tSET production=(SELECT SUM(ProcFee*(UnitQty+BaseUnits)) FROM procedurelog \r\n" + 
        "\t\t\t\tWHERE procedurelog.ProvNum=tempdash.ProvNum\r\n" + 
        "\t\t\t\tAND procedurelog.ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + "\r\n" + 
        "\t\t\t\tAND ProcDate=" + POut.date(dt) + ")";
        Db.nonQ(command);
        //capcomplete writeoffs were skipped
        //adjustments
        command = "UPDATE tempdash \r\n" + 
        "\t\t\t\tSET production=production+(SELECT IFNULL(SUM(AdjAmt),0) FROM adjustment \r\n" + 
        "\t\t\t\tWHERE adjustment.ProvNum=tempdash.ProvNum\r\n" + 
        "\t\t\t\tAND AdjDate=" + POut.date(dt) + ")";
        Db.nonQ(command);
        //insurance writeoffs
        if (PrefC.getBool(PrefName.ReportsPPOwriteoffDefaultToProcDate))
        {
            //use procdate
            command = "UPDATE tempdash \r\n" + 
            "\t\t\t\t\tSET production=production-(SELECT IFNULL(SUM(WriteOff),0) FROM claimproc \r\n" + 
            "\t\t\t\t\tWHERE claimproc.ProvNum=tempdash.ProvNum\r\n" + 
            "\t\t\t\t\tAND ProcDate=" + POut.date(dt) + " \r\n" + 
            "\t\t\t\t\tAND (claimproc.Status=1 OR claimproc.Status=4 OR claimproc.Status=0) )";
        }
        else
        {
            //received or supplemental or notreceived
            command = "UPDATE tempdash \r\n" + 
            "\t\t\t\t\tSET production=production-(SELECT IFNULL(SUM(WriteOff),0) FROM claimproc \r\n" + 
            "\t\t\t\t\tWHERE claimproc.ProvNum=tempdash.ProvNum\r\n" + 
            "\t\t\t\t\tAND DateCP=" + POut.date(dt) + " \r\n" + 
            "\t\t\t\t\tAND (claimproc.Status=1 OR claimproc.Status=4) )";
        } 
        //received or supplemental
        Db.nonQ(command);
        //income------------------------------------------------------------------------
        //patient income
        command = "UPDATE tempdash \r\n" + 
        "\t\t\t\tSET income=(SELECT SUM(SplitAmt) FROM paysplit \r\n" + 
        "\t\t\t\tWHERE paysplit.ProvNum=tempdash.ProvNum\r\n" + 
        "\t\t\t\tAND DatePay=" + POut.date(dt) + ")";
        Db.nonQ(command);
        //ins income
        command = "UPDATE tempdash \r\n" + 
        "\t\t\t\tSET income=income+(SELECT IFNULL(SUM(InsPayAmt),0) FROM claimproc \r\n" + 
        "\t\t\t\tWHERE claimproc.ProvNum=tempdash.ProvNum\r\n" + 
        "\t\t\t\tAND DateCP=" + POut.date(dt) + ")";
        Db.nonQ(command);
        //final queries
        command = "SELECT * FROM tempdash";
        DataTable table = Db.getTable(command);
        command = "DROP TABLE IF EXISTS tempdash;";
        Db.nonQ(command);
        return table;
    }

    public static List<System.Windows.Media.Color> getProdProvColors() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<System.Windows.Media.Color>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT ProvColor\r\n" + 
        "\t\t\t\tFROM provider WHERE IsHidden=0\r\n" + 
        "\t\t\t\tORDER BY ItemOrder";
        DataTable table = Db.getTable(command);
        List<System.Windows.Media.Color> retVal = new List<System.Windows.Media.Color>();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            System.Drawing.Color dColor = System.Drawing.Color.FromArgb(PIn.Int(table.Rows[i]["ProvColor"].ToString()));
            System.Windows.Media.Color mColor = System.Windows.Media.Color.FromArgb(dColor.A, dColor.R, dColor.G, dColor.B);
            retVal.Add(mColor);
        }
        return retVal;
    }

    public static List<List<int>> getProdProvs(DateTime dateFrom, DateTime dateTo) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<List<int>>>GetObject(MethodBase.GetCurrentMethod(), dateFrom, dateTo);
        }
         
        String command = new String();
        command = "DROP TABLE IF EXISTS tempdash;";
        Db.nonQ(command);
        //this table will contain approx 12x3xProv rows if there was production for each prov in each month.
        command = "CREATE TABLE tempdash (\r\n" + 
        "\t\t\t\tDatePeriod date NOT NULL,\r\n" + 
        "\t\t\t\tProvNum bigint NOT NULL,\r\n" + 
        "\t\t\t\tproduction decimal NOT NULL\r\n" + 
        "\t\t\t\t) DEFAULT CHARSET=utf8";
        Db.nonQ(command);
        //procs. Inserts approx 12xProv rows
        command = "INSERT INTO tempdash\r\n" + 
        "\t\t\t\tSELECT procedurelog.ProcDate,procedurelog.ProvNum,\r\n" + 
        "\t\t\t\tSUM(procedurelog.ProcFee*(procedurelog.UnitQty+procedurelog.BaseUnits))-IFNULL(SUM(claimproc.WriteOff),0)\r\n" + 
        "\t\t\t\tFROM procedurelog\r\n" + 
        "\t\t\t\tLEFT JOIN claimproc ON procedurelog.ProcNum=claimproc.ProcNum\r\n" + 
        "\t\t\t\tAND claimproc.Status=\'7\' /*only CapComplete writeoffs are subtracted here*/\r\n" + 
        "\t\t\t\tWHERE procedurelog.ProcStatus = \'2\'\r\n" + 
        "\t\t\t\tAND procedurelog.ProcDate >= " + POut.date(dateFrom) + "\r\n" + 
        "\t\t\t\tAND procedurelog.ProcDate <= " + POut.date(dateTo) + "\r\n" + 
        "\t\t\t\tGROUP BY procedurelog.ProvNum,MONTH(procedurelog.ProcDate)";
        Db.nonQ(command);
        //todo 2 more tables
        //get all the data as 12xProv rows
        command = "SELECT DatePeriod,ProvNum,SUM(production) prod\r\n" + 
        "\t\t\t\tFROM tempdash \r\n" + 
        "\t\t\t\tGROUP BY ProvNum,MONTH(DatePeriod)";
        //this fails with date issue
        DataTable tableProd = Db.getTable(command);
        command = "DROP TABLE IF EXISTS tempdash;";
        Db.nonQ(command);
        command = "SELECT ProvNum\r\n" + 
        "\t\t\t\tFROM provider WHERE IsHidden=0\r\n" + 
        "\t\t\t\tORDER BY ItemOrder";
        DataTable tableProv = Db.getTable(command);
        List<List<int>> retVal = new List<List<int>>();
        for (int p = 0;p < tableProv.Rows.Count;p++)
        {
            //loop through each provider
            long provNum = PIn.Long(tableProv.Rows[p]["ProvNum"].ToString());
            List<int> listInt = new List<int>();
            for (int i = 0;i < 12;i++)
            {
                //12 items
                double prod = 0;
                DateTime datePeriod = dateFrom.AddMonths(i);
                for (int j = 0;j < tableProd.Rows.Count;j++)
                {
                    //only the month and year are important
                    if (datePeriod.Year == PIn.Date(tableProd.Rows[j]["DatePeriod"].ToString()).Year && datePeriod.Month == PIn.Date(tableProd.Rows[j]["DatePeriod"].ToString()).Month && provNum == PIn.Long(tableProd.Rows[j]["ProvNum"].ToString()))
                    {
                        prod = PIn.Decimal(tableProd.Rows[j]["prod"].ToString());
                        break;
                    }
                     
                }
                listInt.Add((int)(prod));
            }
            retVal.Add(listInt);
        }
        return retVal;
    }

    /**
    * Only one dimension to the list for now.
    */
    public static List<List<int>> getAR(DateTime dateFrom, DateTime dateTo, List<DashboardAR> listDashAR) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<List<int>>>GetObject(MethodBase.GetCurrentMethod(), dateFrom, dateTo, listDashAR);
        }
         
        //assumes that dateFrom is the first of the month and that there are 12 periods
        //listDashAR may be empty, in which case, this routine will take about 18 seconds, but the user was warned.
        //listDashAR may also me incomplete, especially the most recent month(s).
        String command = new String();
        List<int> listInt = new List<int>();
        listInt = new List<int>();
        boolean agingWasRun = false;
        for (int i = 0;i < 12;i++)
        {
            DateTime dateLastOfMonth = dateFrom.AddMonths(i + 1).AddDays(-1);
            DashboardAR dash = null;
            for (int d = 0;d < listDashAR.Count;d++)
            {
                if (listDashAR[d].DateCalc != dateLastOfMonth)
                {
                    continue;
                }
                 
                dash = listDashAR[d];
            }
            if (dash != null)
            {
                //we found a DashboardAR object from the database for this month, so use it.
                listInt.Add((int)dash.BalTotal);
                continue;
            }
             
            agingWasRun = true;
            //run historical aging on all patients based on the date entered.
            Ledgers.ComputeAging(0, dateLastOfMonth, true);
            command = "SELECT SUM(Bal_0_30+Bal_31_60+Bal_61_90+BalOver90),SUM(InsEst) FROM patient";
            DataTable table = Db.getTable(command);
            dash = new DashboardAR();
            dash.DateCalc = dateLastOfMonth;
            dash.BalTotal = PIn.Double(table.Rows[0][0].ToString());
            dash.InsEst = PIn.Double(table.Rows[0][1].ToString());
            DashboardARs.insert(dash);
            //save it to the db for later.
            listInt.Add((int)dash.BalTotal);
        }
        //and also use it now.
        if (agingWasRun)
        {
            Ledgers.runAging();
        }
         
        //set aging back to normal
        List<List<int>> retVal = new List<List<int>>();
        retVal.Add(listInt);
        return retVal;
    }

    public static List<List<int>> getProdInc(DateTime dateFrom, DateTime dateTo) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<List<int>>>GetObject(MethodBase.GetCurrentMethod(), dateFrom, dateTo);
        }
         
        String command = new String();
        command = "SELECT procedurelog.ProcDate,\r\n" + 
        "\t\t\t\tSUM(procedurelog.ProcFee*(procedurelog.UnitQty+procedurelog.BaseUnits))-IFNULL(SUM(claimproc.WriteOff),0)\r\n" + 
        "\t\t\t\tFROM procedurelog\r\n" + 
        "\t\t\t\tLEFT JOIN claimproc ON procedurelog.ProcNum=claimproc.ProcNum\r\n" + 
        "\t\t\t\tAND claimproc.Status=\'7\' /*only CapComplete writeoffs are subtracted here*/\r\n" + 
        "\t\t\t\tWHERE procedurelog.ProcStatus = \'2\'\r\n" + 
        "\t\t\t\tAND procedurelog.ProcDate >= " + POut.date(dateFrom) + "\r\n" + 
        "\t\t\t\tAND procedurelog.ProcDate <= " + POut.date(dateTo) + "\r\n" + 
        "\t\t\t\tGROUP BY MONTH(procedurelog.ProcDate)";
        DataTable tableProduction = Db.getTable(command);
        command = "SELECT AdjDate,\r\n" + 
        "\t\t\t\tSUM(AdjAmt)\r\n" + 
        "\t\t\t\tFROM adjustment\r\n" + 
        "\t\t\t\tWHERE AdjDate >= " + POut.date(dateFrom) + "\r\n" + 
        "\t\t\t\tAND AdjDate <= " + POut.date(dateTo) + "\r\n" + 
        "\t\t\t\tGROUP BY MONTH(AdjDate)";
        DataTable tableAdj = Db.getTable(command);
        if (PrefC.getBool(PrefName.ReportsPPOwriteoffDefaultToProcDate))
        {
            //use procdate
            command = "SELECT " + "claimproc.ProcDate," + "SUM(claimproc.WriteOff) " + "FROM claimproc " + "WHERE claimproc.ProcDate >= " + POut.date(dateFrom) + " " + "AND claimproc.ProcDate <= " + POut.date(dateTo) + " " + "AND (claimproc.Status=1 OR claimproc.Status=4 OR claimproc.Status=0) " + "GROUP BY MONTH(claimproc.ProcDate)";
        }
        else
        {
            //received or supplemental or notreceived
            command = "SELECT " + "claimproc.DateCP," + "SUM(claimproc.WriteOff) " + "FROM claimproc " + "WHERE claimproc.DateCP >= " + POut.date(dateFrom) + " " + "AND claimproc.DateCP <= " + POut.date(dateTo) + " " + "AND (claimproc.Status=1 OR claimproc.Status=4) " + "GROUP BY MONTH(claimproc.DateCP)";
        } 
        //Received or supplemental
        DataTable tableWriteoff = Db.getTable(command);
        command = "SELECT " + "paysplit.DatePay," + "SUM(paysplit.SplitAmt) " + "FROM paysplit " + "WHERE paysplit.IsDiscount=0 " + "AND paysplit.DatePay >= " + POut.date(dateFrom) + " " + "AND paysplit.DatePay <= " + POut.date(dateTo) + " " + "GROUP BY MONTH(paysplit.DatePay)";
        DataTable tablePay = Db.getTable(command);
        command = "SELECT claimpayment.CheckDate,SUM(claimproc.InsPayamt) " + "FROM claimpayment,claimproc WHERE " + "claimproc.ClaimPaymentNum = claimpayment.ClaimPaymentNum " + "AND claimpayment.CheckDate >= " + POut.date(dateFrom) + " " + "AND claimpayment.CheckDate <= " + POut.date(dateTo) + " " + " GROUP BY claimpayment.CheckDate ORDER BY checkdate";
        DataTable tableIns = Db.getTable(command);
        //production--------------------------------------------------------------------
        List<int> listInt = new List<int>();
        listInt = new List<int>();
        for (int i = 0;i < 12;i++)
        {
            double prod = 0;
            double adjust = 0;
            double inswriteoff = 0;
            DateTime datePeriod = dateFrom.AddMonths(i);
            for (int j = 0;j < tableProduction.Rows.Count;j++)
            {
                //only the month and year are important
                if (datePeriod.Year == PIn.Date(tableProduction.Rows[j][0].ToString()).Year && datePeriod.Month == PIn.Date(tableProduction.Rows[j][0].ToString()).Month)
                {
                    prod += PIn.Decimal(tableProduction.Rows[j][1].ToString());
                }
                 
            }
            for (int j = 0;j < tableAdj.Rows.Count;j++)
            {
                if (datePeriod.Year == PIn.Date(tableAdj.Rows[j][0].ToString()).Year && datePeriod.Month == PIn.Date(tableAdj.Rows[j][0].ToString()).Month)
                {
                    adjust += PIn.Decimal(tableAdj.Rows[j][1].ToString());
                }
                 
            }
            for (int j = 0;j < tableWriteoff.Rows.Count;j++)
            {
                if (datePeriod.Year == PIn.Date(tableWriteoff.Rows[j][0].ToString()).Year && datePeriod.Month == PIn.Date(tableWriteoff.Rows[j][0].ToString()).Month)
                {
                    inswriteoff += PIn.Decimal(tableWriteoff.Rows[j][1].ToString());
                }
                 
            }
            listInt.Add((int)(prod + adjust - inswriteoff));
        }
        List<List<int>> retVal = new List<List<int>>();
        retVal.Add(listInt);
        //income----------------------------------------------------------------------
        listInt = new List<int>();
        for (int i = 0;i < 12;i++)
        {
            double ptincome = 0;
            double insincome = 0;
            DateTime datePeriod = dateFrom.AddMonths(i);
            for (int j = 0;j < tablePay.Rows.Count;j++)
            {
                //only the month and year are important
                if (datePeriod.Year == PIn.Date(tablePay.Rows[j][0].ToString()).Year && datePeriod.Month == PIn.Date(tablePay.Rows[j][0].ToString()).Month)
                {
                    ptincome += PIn.Decimal(tablePay.Rows[j][1].ToString());
                }
                 
            }
            for (int j = 0;j < tableIns.Rows.Count;j++)
            {
                //
                if (datePeriod.Year == PIn.Date(tableIns.Rows[j][0].ToString()).Year && datePeriod.Month == PIn.Date(tableIns.Rows[j][0].ToString()).Month)
                {
                    insincome += PIn.Decimal(tableIns.Rows[j][1].ToString());
                }
                 
            }
            listInt.Add((int)(ptincome + insincome));
        }
        retVal.Add(listInt);
        return retVal;
    }

    public static List<List<int>> getNewPatients(DateTime dateFrom, DateTime dateTo) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<List<int>>>GetObject(MethodBase.GetCurrentMethod(), dateFrom, dateTo);
        }
         
        String command = new String();
        command = "DROP TABLE IF EXISTS tempdash;";
        Db.nonQ(command);
        command = "CREATE TABLE tempdash (\r\n" + 
        "\t\t\t\tPatNum bigint NOT NULL PRIMARY KEY,\r\n" + 
        "\t\t\t\tdateFirstProc datetime NOT NULL\r\n" + 
        "\t\t\t\t) DEFAULT CHARSET=utf8";
        Db.nonQ(command);
        //table full of individual patients and their dateFirstProcs.
        command = "INSERT INTO tempdash \r\n" + 
        "\t\t\t\tSELECT PatNum, MIN(ProcDate) dateFirstProc FROM procedurelog\r\n" + 
        "\t\t\t\tWHERE ProcStatus=2 GROUP BY PatNum\r\n" + 
        "\t\t\t\tHAVING dateFirstProc >= " + POut.date(dateFrom) + " " + "AND dateFirstProc <= " + POut.date(dateTo);
        Db.nonQ(command);
        command = "SELECT dateFirstProc,COUNT(*) " + "FROM tempdash " + "GROUP BY MONTH(dateFirstProc)";
        DataTable tableCounts = Db.getTable(command);
        List<int> listInt = new List<int>();
        for (int i = 0;i < 12;i++)
        {
            int ptcount = 0;
            DateTime datePeriod = dateFrom.AddMonths(i);
            for (int j = 0;j < tableCounts.Rows.Count;j++)
            {
                //only the month and year are important
                if (datePeriod.Year == PIn.Date(tableCounts.Rows[j][0].ToString()).Year && datePeriod.Month == PIn.Date(tableCounts.Rows[j][0].ToString()).Month)
                {
                    ptcount += PIn.Int(tableCounts.Rows[j][1].ToString());
                }
                 
            }
            listInt.Add(ptcount);
        }
        List<List<int>> retVal = new List<List<int>>();
        retVal.Add(listInt);
        return retVal;
    }

}


