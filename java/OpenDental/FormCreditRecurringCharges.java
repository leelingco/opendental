//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.DateTimeOD;
import OpenDental.FormXchargeSetup;
import OpenDental.GotoModule;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.PrinterL;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CreditCards;
import OpenDentBusiness.Ledgers;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Payment;
import OpenDentBusiness.Payments;
import OpenDentBusiness.PaySplit;
import OpenDentBusiness.PaySplits;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormCreditRecurringCharges  extends Form 
{

    private DataTable table = new DataTable();
    private PrintDocument pd = new PrintDocument();
    private int pagesPrinted = new int();
    private int headingPrintH = new int();
    private boolean headingPrinted = new boolean();
    private boolean insertPayment = new boolean();
    private Program prog;
    private DateTime nowDateTime = new DateTime();
    private String xPath = new String();
    /**
    * Only works for XCharge so far.
    */
    public FormCreditRecurringCharges() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formRecurringCharges_Load(Object sender, EventArgs e) throws Exception {
        nowDateTime = MiscData.getNowDateTime();
        prog = Programs.getCur(ProgramName.Xcharge);
        xPath = Programs.getProgramPath(prog);
        labelCharged.Text = Lan.g(this,"Charged=") + "0";
        labelFailed.Text = Lan.g(this,"Failed=") + "0";
        fillGrid();
        gridMain.setSelected(true);
        labelSelected.Text = Lan.g(this,"Selected=") + gridMain.getSelectedIndices().Length.ToString();
    }

    private void fillGrid() throws Exception {
        //Currently only working for X-Charge. If more added then move this check out of FillGrid.
        if (prog == null)
        {
            MsgBox.show(this,"X-Charge entry is missing from the database.");
            return ;
        }
         
        //should never happen
        if (!prog.Enabled)
        {
            if (Security.isAuthorized(Permissions.Setup))
            {
                FormXchargeSetup FormX = new FormXchargeSetup();
                FormX.ShowDialog();
            }
             
            return ;
        }
         
        if (!File.Exists(xPath))
        {
            MsgBox.show(this,"Path is not valid.");
            if (Security.isAuthorized(Permissions.Setup))
            {
                FormXchargeSetup FormX = new FormXchargeSetup();
                FormX.ShowDialog();
            }
             
            return ;
        }
         
        table = CreditCards.getRecurringChargeList();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableRecurring","PatNum"),110);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecurring","Name"),250);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecurring","Total Bal"), 90, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRecurring","ChargeAmt"), 100, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            Double famBalTotal = PIn.Double(table.Rows[i]["FamBalTotal"].ToString());
            Double chargeAmt = PIn.Double(table.Rows[i]["ChargeAmt"].ToString());
            row.getCells().Add(table.Rows[i]["PatNum"].ToString());
            row.getCells().Add(table.Rows[i]["PatName"].ToString());
            row.getCells().Add(famBalTotal.ToString("c"));
            row.getCells().Add(chargeAmt.ToString("c"));
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        labelTotal.Text = Lan.g(this,"Total=") + table.Rows.Count.ToString();
        labelSelected.Text = Lan.g(this,"Selected=") + gridMain.getSelectedIndices().Length.ToString();
    }

    /**
    * Returns a valid DateTime for the payment's PayDate.  Contains logic if payment should be for the previous or the current month.
    */
    private DateTime getPayDate(DateTime latestPayment, DateTime dateStart) throws Exception {
        //Most common, current day >= dateStart so we use current month and year with the dateStart day.  Will always be a legal DateTime.
        if (nowDateTime.Day >= dateStart.Day)
        {
            return new DateTime(nowDateTime.Year, nowDateTime.Month, dateStart.Day);
        }
         
        //If not enough days in current month to match the dateStart see if on the last day in the month.
        //Example: dateStart=08/31/2009 and month is February 28th so we need the PayDate to be today not for last day on the last month, which would happen below.
        int daysInMonth = DateTime.DaysInMonth(nowDateTime.Year, nowDateTime.Month);
        if (daysInMonth <= dateStart.Day && daysInMonth == nowDateTime.Day)
        {
            return nowDateTime;
        }
         
        //Today is last day of the month so return today as the PayDate.
        //PayDate needs to be for the previous month so we need to determine if using the dateStart day would be a legal DateTime.
        DateTime nowMinusOneMonth = nowDateTime.AddMonths(-1);
        daysInMonth = DateTime.DaysInMonth(nowMinusOneMonth.Year, nowMinusOneMonth.Month);
        if (daysInMonth <= dateStart.Day)
        {
            return new DateTime(nowMinusOneMonth.Year, nowMinusOneMonth.Month, daysInMonth);
        }
         
        return new DateTime(nowMinusOneMonth.Year, nowMinusOneMonth.Month, dateStart.Day);
    }

    //Returns the last day of the previous month.
    //Previous month contains a legal date using dateStart's day.
    /**
    * Tests the selected indicies with newly calculated pay dates.  If there's a date violation, a warning shows and false is returned.
    */
    private boolean paymentsWithinLockDate() throws Exception {
        //Check if user has the payment create permission in the first place to save time.
        if (!Security.IsAuthorized(Permissions.PaymentCreate, nowDateTime.Date))
        {
            return false;
        }
         
        List<String> warnings = new List<String>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            //Calculate what the new pay date will be.
            DateTime newPayDate = GetPayDate(PIn.Date(table.Rows[gridMain.getSelectedIndices()[i]]["LatestPayment"].ToString()), PIn.Date(table.Rows[gridMain.getSelectedIndices()[i]]["DateStart"].ToString()));
            //Test if the user can create a payment with the new pay date.
            if (!Security.isAuthorized(Permissions.PaymentCreate,newPayDate,true))
            {
                if (warnings.Count == 0)
                {
                    warnings.Add("Lock date limitation is preventing the recurring charges from running:");
                }
                 
                warnings.Add(newPayDate.ToShortDateString() + " - " + table.Rows[i]["PatNum"].ToString() + ": " + table.Rows[i]["PatName"].ToString() + " - " + PIn.Double(table.Rows[i]["FamBalTotal"].ToString()).ToString("c") + " - " + PIn.Double(table.Rows[i]["ChargeAmt"].ToString()).ToString("c"));
            }
             
        }
        if (warnings.Count > 0)
        {
            String msg = "";
            for (int i = 0;i < warnings.Count;i++)
            {
                if (i > 0)
                {
                    msg += "\r\n";
                }
                 
                msg += warnings[i];
            }
            //Show the warning message.  This allows the user the ability to unhighlight rows or go change the date limitation.
            MessageBox.Show(msg);
            return false;
        }
         
        return true;
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        labelSelected.Text = Lan.g(this,"Selected=") + gridMain.getSelectedIndices().Length.ToString();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.AccountModule))
        {
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Must select at least one recurring charge.");
            return ;
        }
         
        long patNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[0]]["PatNum"].ToString());
        GotoModule.gotoAccount(patNum);
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
        labelCharged.Text = Lan.g(this,"Charged=") + "0";
        labelFailed.Text = Lan.g(this,"Failed=") + "0";
    }

    private void butPrintList_Click(Object sender, EventArgs e) throws Exception {
        pagesPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        pd.DefaultPageSettings.Landscape = true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        try
        {
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "CreditCard recurring charges list printed"))
            {
                pd.Print();
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = e.MarginBounds;
        //new Rectangle(50,40,800,1035);//Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = Lan.g(this,"Recurring Charges");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            yPos += 20;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = gridMain.printPage(g,pagesPrinted,bounds,headingPrintH);
        pagesPrinted++;
        if (yPos == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
        } 
        g.Dispose();
    }

    private void butAll_Click(Object sender, EventArgs e) throws Exception {
        gridMain.setSelected(true);
        labelSelected.Text = Lan.g(this,"Selected=") + gridMain.getSelectedIndices().Length.ToString();
    }

    private void butNone_Click(Object sender, EventArgs e) throws Exception {
        gridMain.setSelected(false);
        labelSelected.Text = Lan.g(this,"Selected=") + gridMain.getSelectedIndices().Length.ToString();
    }

    private void butSend_Click(Object sender, EventArgs e) throws Exception {
        //Assuming the use of XCharge.  If adding another vendor (PayConnect for example)
        //make sure to move XCharge validation in FillGrid() to here.
        if (prog == null)
        {
            return ;
        }
         
        //Gets filled in FillGrid()
        if (gridMain.getSelectedIndices().Length < 1)
        {
            MsgBox.show(this,"Must select at least one recurring charge.");
            return ;
        }
         
        if (!paymentsWithinLockDate())
        {
            return ;
        }
         
        String recurringResultFile = "Recurring charge results for " + DateTime.Now.ToShortDateString() + " ran at " + DateTime.Now.ToShortTimeString() + "\r\n\r\n";
        int failed = 0;
        int success = 0;
        String user = ProgramProperties.getPropVal(prog.ProgramNum,"Username");
        String password = ProgramProperties.getPropVal(prog.ProgramNum,"Password");
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            if (!StringSupport.equals(table.Rows[gridMain.getSelectedIndices()[i]]["XChargeToken"].ToString(), "") && CreditCards.IsDuplicateXChargeToken(table.Rows[gridMain.getSelectedIndices()[i]]["XChargeToken"].ToString()))
            {
                MessageBox.Show(Lan.g(this,"A duplicate token was found, the card cannot be charged for customer: ") + table.Rows[i]["PatName"].ToString());
                continue;
            }
             
            insertPayment = false;
            ProcessStartInfo info = new ProcessStartInfo(xPath);
            long patNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["PatNum"].ToString());
            String resultfile = Path.Combine(Path.GetDirectoryName(xPath), "XResult.txt");
            File.Delete(resultfile);
            //delete the old result file.
            info.Arguments = "";
            double amt = PIn.Double(table.Rows[gridMain.getSelectedIndices()[i]]["ChargeAmt"].ToString());
            DateTime exp = PIn.Date(table.Rows[gridMain.getSelectedIndices()[i]]["CCExpiration"].ToString());
            String address = PIn.String(table.Rows[gridMain.getSelectedIndices()[i]]["Address"].ToString());
            String addressPat = PIn.String(table.Rows[gridMain.getSelectedIndices()[i]]["AddressPat"].ToString());
            String zip = PIn.String(table.Rows[gridMain.getSelectedIndices()[i]]["Zip"].ToString());
            String zipPat = PIn.String(table.Rows[gridMain.getSelectedIndices()[i]]["ZipPat"].ToString());
            info.Arguments += "/AMOUNT:" + amt.ToString("F2") + " /LOCKAMOUNT ";
            info.Arguments += "/TRANSACTIONTYPE:PURCHASE /LOCKTRANTYPE ";
            if (!StringSupport.equals(table.Rows[gridMain.getSelectedIndices()[i]]["XChargeToken"].ToString(), ""))
            {
                info.Arguments += "/XCACCOUNTID:" + table.Rows[gridMain.getSelectedIndices()[i]]["XChargeToken"].ToString() + " ";
                info.Arguments += "/RECURRING ";
            }
            else
            {
                info.Arguments += "/ACCOUNT:" + table.Rows[gridMain.getSelectedIndices()[i]]["CCNumberMasked"].ToString() + " ";
            } 
            if (exp.Year > 1880)
            {
                info.Arguments += "/EXP:" + exp.ToString("MMyy") + " ";
            }
             
            if (!StringSupport.equals(address, ""))
            {
                info.Arguments += "\"/ADDRESS:" + address + "\" ";
            }
            else if (!StringSupport.equals(addressPat, ""))
            {
                info.Arguments += "\"/ADDRESS:" + addressPat + "\" ";
            }
              
            if (!StringSupport.equals(zip, ""))
            {
                info.Arguments += "\"/ZIP:" + zip + "\" ";
            }
            else if (!StringSupport.equals(zipPat, ""))
            {
                info.Arguments += "\"/ZIP:" + zipPat + "\" ";
            }
              
            info.Arguments += "/RECEIPT:Pat" + patNum + " ";
            //aka invoice#
            info.Arguments += "\"/CLERK:" + Security.getCurUser().UserName + " R\" /LOCKCLERK ";
            info.Arguments += "/RESULTFILE:\"" + resultfile + "\" ";
            info.Arguments += "/USERID:" + user + " ";
            info.Arguments += "/PASSWORD:" + password + " ";
            info.Arguments += "/HIDEMAINWINDOW ";
            info.Arguments += "/AUTOPROCESS ";
            info.Arguments += "/SMALLWINDOW ";
            info.Arguments += "/AUTOCLOSE ";
            info.Arguments += "/NORESULTDIALOG ";
            Cursor = Cursors.WaitCursor;
            Process process = new Process();
            process.StartInfo = info;
            process.EnableRaisingEvents = true;
            process.Start();
            while (!process.HasExited)
            {
                Application.DoEvents();
            }
            Thread.Sleep(200);
            //Wait 2/10 second to give time for file to be created.
            Cursor = Cursors.Default;
            String line = "";
            String resultText = "";
            recurringResultFile += "PatNum: " + patNum + " Name: " + table.Rows[i]["PatName"].ToString() + "\r\n";
            TextReader reader = new StreamReader(resultfile);
            try
            {
                {
                    line = reader.ReadLine();
                    while (line != null)
                    {
                        if (!StringSupport.equals(resultText, ""))
                        {
                            resultText += "\r\n";
                        }
                         
                        resultText += line;
                        if (line.StartsWith("RESULT="))
                        {
                            if (StringSupport.equals(line, "RESULT=SUCCESS"))
                            {
                                success++;
                                labelCharged.Text = Lan.g(this,"Charged=") + success;
                                insertPayment = true;
                            }
                            else
                            {
                                failed++;
                                labelFailed.Text = Lan.g(this,"Failed=") + failed;
                            } 
                        }
                         
                        line = reader.ReadLine();
                    }
                    recurringResultFile += resultText + "\r\n\r\n";
                }
            }
            finally
            {
                if (reader != null)
                    Disposable.mkDisposable(reader).dispose();
                 
            }
            if (insertPayment)
            {
                Patient patCur = Patients.getPat(patNum);
                Payment paymentCur = new Payment();
                paymentCur.DateEntry = nowDateTime.Date;
                paymentCur.PayDate = GetPayDate(PIn.Date(table.Rows[gridMain.getSelectedIndices()[i]]["LatestPayment"].ToString()), PIn.Date(table.Rows[gridMain.getSelectedIndices()[i]]["DateStart"].ToString()));
                paymentCur.PatNum = patCur.PatNum;
                paymentCur.ClinicNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["ClinicNum"].ToString());
                paymentCur.PayType = PIn.Int(ProgramProperties.getPropVal(prog.ProgramNum,"PaymentType"));
                paymentCur.PayAmt = amt;
                paymentCur.PayNote = resultText;
                paymentCur.IsRecurringCC = true;
                Payments.insert(paymentCur);
                long provNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["ProvNum"].ToString());
                //for payment plans only
                if (provNum == 0)
                {
                    //Regular payments need to apply to the provider that the family owes the most money to.
                    DataTable dt = Patients.getPaymentStartingBalances(patCur.Guarantor,paymentCur.PayNum);
                    double highestAmt = 0;
                    for (int j = 0;j < dt.Rows.Count;j++)
                    {
                        double afterIns = PIn.Double(dt.Rows[j]["AfterIns"].ToString());
                        if (highestAmt >= afterIns)
                        {
                            continue;
                        }
                         
                        highestAmt = afterIns;
                        provNum = PIn.Long(dt.Rows[j]["ProvNum"].ToString());
                    }
                }
                 
                PaySplit split = new PaySplit();
                split.PatNum = paymentCur.PatNum;
                split.ClinicNum = paymentCur.ClinicNum;
                split.PayNum = paymentCur.PayNum;
                split.ProcDate = paymentCur.PayDate;
                split.DatePay = paymentCur.PayDate;
                split.ProvNum = provNum;
                split.SplitAmt = paymentCur.PayAmt;
                split.PayPlanNum = PIn.Long(table.Rows[gridMain.getSelectedIndices()[i]]["PayPlanNum"].ToString());
                PaySplits.insert(split);
                if (PrefC.getBool(PrefName.AgingCalculatedMonthlyInsteadOfDaily))
                {
                    Ledgers.computeAging(patCur.Guarantor,PrefC.getDate(PrefName.DateLastAging),false);
                }
                else
                {
                    Ledgers.computeAging(patCur.Guarantor,DateTimeOD.getToday(),false);
                    if (PrefC.getDate(PrefName.DateLastAging) != DateTime.Today)
                    {
                        Prefs.UpdateString(PrefName.DateLastAging, POut.Date(DateTime.Today, false));
                    }
                     
                } 
            }
             
        }
        try
        {
            //Since this is always called from UI, the above line works fine to keep the prefs cache current.
            File.WriteAllText(Path.Combine(Path.GetDirectoryName(xPath), "RecurringChargeResult.txt"), recurringResultFile);
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        //Do nothing cause this is just for internal use.
        fillGrid();
        labelCharged.Text = Lan.g(this,"Charged=") + success;
        labelFailed.Text = Lan.g(this,"Failed=") + failed;
        MsgBox.show(this,"Done charging cards.\r\nIf there are any patients remaining in list, print the list and handle each one manually.");
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.groupCounts = new System.Windows.Forms.GroupBox();
        this.labelFailed = new System.Windows.Forms.Label();
        this.labelCharged = new System.Windows.Forms.Label();
        this.labelSelected = new System.Windows.Forms.Label();
        this.labelTotal = new System.Windows.Forms.Label();
        this.butNone = new OpenDental.UI.Button();
        this.butAll = new OpenDental.UI.Button();
        this.butRefresh = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butSend = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butPrintList = new OpenDental.UI.Button();
        this.groupCounts.SuspendLayout();
        this.SuspendLayout();
        //
        // groupCounts
        //
        this.groupCounts.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupCounts.Controls.Add(this.labelFailed);
        this.groupCounts.Controls.Add(this.labelCharged);
        this.groupCounts.Controls.Add(this.labelSelected);
        this.groupCounts.Controls.Add(this.labelTotal);
        this.groupCounts.Location = new System.Drawing.Point(617, 196);
        this.groupCounts.Name = "groupCounts";
        this.groupCounts.Size = new System.Drawing.Size(96, 103);
        this.groupCounts.TabIndex = 34;
        this.groupCounts.TabStop = false;
        this.groupCounts.Text = "Counts";
        //
        // labelFailed
        //
        this.labelFailed.Location = new System.Drawing.Point(4, 76);
        this.labelFailed.Name = "labelFailed";
        this.labelFailed.Size = new System.Drawing.Size(89, 16);
        this.labelFailed.TabIndex = 32;
        this.labelFailed.Text = "Failed=0";
        this.labelFailed.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelCharged
        //
        this.labelCharged.Location = new System.Drawing.Point(4, 57);
        this.labelCharged.Name = "labelCharged";
        this.labelCharged.Size = new System.Drawing.Size(89, 16);
        this.labelCharged.TabIndex = 31;
        this.labelCharged.Text = "Charged=0";
        this.labelCharged.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelSelected
        //
        this.labelSelected.Location = new System.Drawing.Point(4, 38);
        this.labelSelected.Name = "labelSelected";
        this.labelSelected.Size = new System.Drawing.Size(89, 16);
        this.labelSelected.TabIndex = 30;
        this.labelSelected.Text = "Selected=0";
        this.labelSelected.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelTotal
        //
        this.labelTotal.Location = new System.Drawing.Point(4, 19);
        this.labelTotal.Name = "labelTotal";
        this.labelTotal.Size = new System.Drawing.Size(89, 16);
        this.labelTotal.TabIndex = 29;
        this.labelTotal.Text = "Total=0";
        this.labelTotal.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butNone
        //
        this.butNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNone.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butNone.setAutosize(true);
        this.butNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNone.setCornerRadius(4F);
        this.butNone.Location = new System.Drawing.Point(102, 498);
        this.butNone.Name = "butNone";
        this.butNone.Size = new System.Drawing.Size(75, 24);
        this.butNone.TabIndex = 42;
        this.butNone.Text = "&None";
        this.butNone.Click += new System.EventHandler(this.butNone_Click);
        //
        // butAll
        //
        this.butAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAll.setAutosize(true);
        this.butAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAll.setCornerRadius(4F);
        this.butAll.Location = new System.Drawing.Point(12, 498);
        this.butAll.Name = "butAll";
        this.butAll.Size = new System.Drawing.Size(75, 24);
        this.butAll.TabIndex = 41;
        this.butAll.Text = "&All";
        this.butAll.Click += new System.EventHandler(this.butAll_Click);
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(627, 12);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(75, 24);
        this.butRefresh.TabIndex = 40;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.AutoScroll = true;
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(592, 474);
        this.gridMain.TabIndex = 29;
        this.gridMain.setTitle("Recurring Charges");
        this.gridMain.setTranslationName("TableRecurring");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butSend
        //
        this.butSend.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSend.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSend.setAutosize(true);
        this.butSend.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSend.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSend.setCornerRadius(4F);
        this.butSend.Location = new System.Drawing.Point(627, 462);
        this.butSend.Name = "butSend";
        this.butSend.Size = new System.Drawing.Size(75, 24);
        this.butSend.TabIndex = 3;
        this.butSend.Text = "&Send";
        this.butSend.Click += new System.EventHandler(this.butSend_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(627, 498);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butPrintList
        //
        this.butPrintList.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrintList.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrintList.setAutosize(true);
        this.butPrintList.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrintList.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrintList.setCornerRadius(4F);
        this.butPrintList.Image = Resources.getbutPrint();
        this.butPrintList.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrintList.Location = new System.Drawing.Point(318, 498);
        this.butPrintList.Name = "butPrintList";
        this.butPrintList.Size = new System.Drawing.Size(88, 24);
        this.butPrintList.TabIndex = 43;
        this.butPrintList.Text = "Print List";
        this.butPrintList.Click += new System.EventHandler(this.butPrintList_Click);
        //
        // FormCreditRecurringCharges
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(725, 534);
        this.Controls.Add(this.butPrintList);
        this.Controls.Add(this.butNone);
        this.Controls.Add(this.butAll);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.groupCounts);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butSend);
        this.Controls.Add(this.butCancel);
        this.Name = "FormCreditRecurringCharges";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Credit Card Recurring Charges";
        this.Load += new System.EventHandler(this.FormRecurringCharges_Load);
        this.groupCounts.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butSend;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.GroupBox groupCounts = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label labelFailed = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelCharged = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelSelected = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelTotal = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butRefresh;
    private OpenDental.UI.Button butNone;
    private OpenDental.UI.Button butAll;
    private OpenDental.UI.Button butPrintList;
}


