//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.FormQuery;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.XChargeTransaction;
import OpenDentBusiness.XChargeTransactions;

public class FormXChargeReconcile  extends Form 
{

    public FormXChargeReconcile() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void butImport_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        OpenFileDialog Dlg = new OpenFileDialog();
        if (Directory.Exists("C:\\X-Charge\\"))
        {
            Dlg.InitialDirectory = "C:\\X-Charge\\";
        }
        else if (Directory.Exists("C:\\"))
        {
            Dlg.InitialDirectory = "C:\\";
        }
          
        if (Dlg.ShowDialog() != DialogResult.OK)
        {
            Cursor = Cursors.Default;
            return ;
        }
         
        if (!File.Exists(Dlg.FileName))
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"File not found");
            return ;
        }
         
        XChargeTransaction trans = new XChargeTransaction();
        String[] fields = new String[]();
        XChargeTransaction transCheck;
        StreamReader sr = new StreamReader(Dlg.FileName);
        try
        {
            {
                Cursor = Cursors.WaitCursor;
                String line = sr.ReadLine();
                while (line != null)
                {
                    fields = line.Split(new String[]{ "," }, StringSplitOptions.None);
                    if (fields.Length < 16)
                    {
                        continue;
                    }
                     
                    trans.TransType = fields[0];
                    fields[1] = fields[1].Replace("$", "");
                    if (fields[1].Contains("("))
                    {
                        fields[1] = fields[1].TrimStart('(');
                        fields[1] = fields[1].TrimEnd(')');
                        fields[1] = fields[1].Insert(0, "-");
                    }
                     
                    trans.Amount = PIn.Double(fields[1]);
                    trans.CCEntry = fields[2];
                    trans.PatNum = PIn.Long(fields[3].Substring(3));
                    //remove "PAT" from the beginning of the string
                    trans.Result = fields[4];
                    trans.ClerkID = fields[5];
                    trans.ResultCode = fields[7];
                    trans.Expiration = fields[8];
                    trans.CCType = fields[9];
                    trans.CreditCardNum = fields[10];
                    trans.BatchNum = fields[11];
                    trans.ItemNum = fields[12];
                    trans.ApprCode = fields[13];
                    trans.TransactionDateTime = PIn.Date(fields[14]).AddHours(PIn.Double(fields[15].Substring(0, 2))).AddMinutes(PIn.Double(fields[15].Substring(3, 2)));
                    if (StringSupport.equals(trans.BatchNum, "") || StringSupport.equals(trans.ItemNum, ""))
                    {
                        line = sr.ReadLine();
                        continue;
                    }
                     
                    transCheck = XChargeTransactions.checkByBatchItem(trans.BatchNum,trans.ItemNum);
                    if (transCheck == trans)
                    {
                        XChargeTransactions.delete(transCheck.XChargeTransactionNum);
                        XChargeTransactions.insert(trans);
                    }
                    else
                    {
                        XChargeTransactions.insert(trans);
                    } 
                    line = sr.ReadLine();
                }
            }
        }
        finally
        {
            if (sr != null)
                Disposable.mkDisposable(sr).dispose();
             
        }
        Cursor = Cursors.Default;
        MsgBox.show(this,"Done.");
    }

    private void butViewImported_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = "SELECT TransactionDateTime,ClerkID,BatchNum,ItemNum,PatNum,CCType,CreditCardNum,Expiration,Result,Amount FROM xchargetransaction " + "WHERE DATE(TransactionDateTime) BETWEEN " + POut.Date(date1.SelectionStart) + " AND " + POut.Date(date2.SelectionStart);
        FormQuery FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        FormQuery2.submitReportQuery();
        report.Title = "XCharge Transactions From " + date1.SelectionStart.ToShortDateString() + " To " + date2.SelectionStart.ToShortDateString();
        report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
        report.setColumn(this,0,"Transaction Date/Time",170);
        report.setColumn(this,1,"Clerk ID",80);
        report.setColumn(this,2,"Batch#",50);
        report.setColumn(this,3,"Item#",50);
        report.setColumn(this,4,"PatNum",50);
        report.setColumn(this,5,"CC Type",55);
        report.setColumn(this,6,"Credit Card Num",140);
        report.setColumn(this,7,"Exp",50);
        report.setColumn(this,8,"Result",50);
        report.SetColumn(this, 9, "Amount", 60, HorizontalAlignment.Right);
        Cursor = Cursors.Default;
        FormQuery2.ShowDialog();
    }

    private void butPayments_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        String programNum = ProgramProperties.getPropVal(Programs.getCur(ProgramName.Xcharge).ProgramNum,"PaymentType");
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = "SET @pos=0; " + "SELECT @pos:=@pos+1 as 'Count', patient.PatNum, LName, FName, DateEntry,PayDate, PayNote,PayAmt " + "FROM patient INNER JOIN payment ON payment.PatNum=patient.PatNum " + "WHERE PayType=" + programNum + " AND DateEntry BETWEEN " + POut.Date(date1.SelectionStart) + " AND " + POut.Date(date2.SelectionStart) + "ORDER BY PayDate ASC, patient.LName";
        FormQuery FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        FormQuery2.submitReportQuery();
        report.Title = "Payments From " + date1.SelectionStart.ToShortDateString() + " To " + date2.SelectionStart.ToShortDateString();
        report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
        report.setColumn(this,0,"Count",50);
        report.setColumn(this,1,"PatNum",50);
        report.setColumn(this,2,"LName",100);
        report.setColumn(this,3,"FName",100);
        report.setColumn(this,4,"DateEntry",100);
        report.setColumn(this,5,"PayDate",100);
        report.setColumn(this,6,"PayNote",150);
        report.SetColumn(this, 7, "PayAmt", 70, HorizontalAlignment.Right);
        Cursor = Cursors.Default;
        FormQuery2.ShowDialog();
    }

    private void butMissing_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        String programNum = ProgramProperties.getPropVal(Programs.getCur(ProgramName.Xcharge).ProgramNum,"PaymentType");
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = "SELECT TransactionDateTime,ClerkID,BatchNum,ItemNum,xchargetransaction.PatNum,CCType,CreditCardNum,Expiration,Result,Amount " + " FROM xchargetransaction LEFT JOIN (" + " SELECT patient.PatNum,LName,FName,DateEntry,PayDate,PayAmt,PayNote" + " FROM patient INNER JOIN payment ON payment.PatNum=patient.PatNum" + " WHERE PayType=" + programNum + " AND DateEntry BETWEEN " + POut.Date(date1.SelectionStart) + " AND " + POut.Date(date2.SelectionStart) + " ) AS P ON xchargetransaction.PatNum=P.PatNum AND DATE(xchargetransaction.TransactionDateTime)=P.DateEntry AND xchargetransaction.Amount=P.PayAmt " + " WHERE DATE(TransactionDateTime) BETWEEN " + POut.Date(date1.SelectionStart) + " AND " + POut.Date(date2.SelectionStart) + " AND P.PatNum IS NULL;";
        FormQuery FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        FormQuery2.submitReportQuery();
        report.Title = "XCharge Transactions From " + date1.SelectionStart.ToShortDateString() + " To " + date2.SelectionStart.ToShortDateString();
        report.SubTitle.Add("No Matching Transaction Found in Open Dental");
        report.setColumn(this,0,"Transaction Date/Time",170);
        report.setColumn(this,1,"Clerk ID",80);
        report.setColumn(this,2,"Batch#",50);
        report.setColumn(this,3,"Item#",50);
        report.setColumn(this,4,"PatNum",50);
        report.setColumn(this,5,"CC Type",55);
        report.setColumn(this,6,"Credit Card Num",140);
        report.setColumn(this,7,"Exp",50);
        report.setColumn(this,8,"Result",50);
        report.SetColumn(this, 9, "Amount", 60, HorizontalAlignment.Right);
        Cursor = Cursors.Default;
        FormQuery2.ShowDialog();
    }

    private void butExtra_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        String programNum = ProgramProperties.getPropVal(Programs.getCur(ProgramName.Xcharge).ProgramNum,"PaymentType");
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = "SELECT payment.PatNum, LName, FName, payment.DateEntry,payment.PayDate, payment.PayNote,payment.PayAmt " + "FROM patient INNER JOIN payment ON payment.PatNum=patient.PatNum " + "LEFT JOIN (SELECT TransactionDateTime,ClerkID,BatchNum,ItemNum,PatNum,CCType,CreditCardNum,Expiration,Result,Amount FROM xchargetransaction " + "WHERE DATE(TransactionDateTime) BETWEEN " + POut.Date(date1.SelectionStart) + " AND " + POut.Date(date2.SelectionStart) + ") AS X " + "ON X.PatNum=payment.PatNum AND DATE(X.TransactionDateTime)=payment.DateEntry AND X.Amount=payment.PayAmt " + "WHERE PayType=" + programNum + " AND DateEntry BETWEEN " + POut.Date(date1.SelectionStart) + " AND " + POut.Date(date2.SelectionStart) + " " + "AND X.TransactionDateTime IS NULL " + "ORDER BY PayDate ASC, patient.LName";
        FormQuery FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        FormQuery2.submitReportQuery();
        report.Title = "Payments From " + date1.SelectionStart.ToShortDateString() + " To " + date2.SelectionStart.ToShortDateString();
        report.SubTitle.Add("No Matching X-Charge Transactions for these Payments");
        report.setColumn(this,0,"PatNum",50);
        report.setColumn(this,1,"LName",100);
        report.setColumn(this,2,"FName",100);
        report.setColumn(this,3,"DateEntry",100);
        report.setColumn(this,4,"PayDate",100);
        report.setColumn(this,5,"PayNote",150);
        report.SetColumn(this, 6, "PayAmt", 70, HorizontalAlignment.Right);
        Cursor = Cursors.Default;
        FormQuery2.ShowDialog();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        this.label5 = new System.Windows.Forms.Label();
        this.date2 = new System.Windows.Forms.MonthCalendar();
        this.date1 = new System.Windows.Forms.MonthCalendar();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.butExtra = new OpenDental.UI.Button();
        this.butMissing = new OpenDental.UI.Button();
        this.butPayments = new OpenDental.UI.Button();
        this.butViewImported = new OpenDental.UI.Button();
        this.butImport = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label5
        //
        this.label5.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.label5.Location = new System.Drawing.Point(105, 35);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(244, 20);
        this.label5.TabIndex = 14;
        this.label5.Text = "Import X-Charge transactions from text.";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // date2
        //
        this.date2.Location = new System.Drawing.Point(282, 94);
        this.date2.Name = "date2";
        this.date2.TabIndex = 24;
        //
        // date1
        //
        this.date1.Location = new System.Drawing.Point(18, 94);
        this.date1.Name = "date1";
        this.date1.TabIndex = 23;
        //
        // label2
        //
        this.label2.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.label2.Location = new System.Drawing.Point(105, 344);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(296, 20);
        this.label2.TabIndex = 14;
        this.label2.Text = "View X-Charge transactions with no payment in OD.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label3
        //
        this.label3.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.label3.Location = new System.Drawing.Point(105, 372);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(272, 20);
        this.label3.TabIndex = 14;
        this.label3.Text = "View payments in OD with no X-Charge transaction.";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label4
        //
        this.label4.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.label4.Location = new System.Drawing.Point(105, 270);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(244, 20);
        this.label4.TabIndex = 14;
        this.label4.Text = "View imported X-Charge transactions.";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label7
        //
        this.label7.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.label7.Location = new System.Drawing.Point(105, 300);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(296, 20);
        this.label7.TabIndex = 14;
        this.label7.Text = "View credit card payments in Open Dental.";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butExtra
        //
        this.butExtra.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExtra.setAutosize(true);
        this.butExtra.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExtra.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExtra.setCornerRadius(4F);
        this.butExtra.Location = new System.Drawing.Point(18, 370);
        this.butExtra.Name = "butExtra";
        this.butExtra.Size = new System.Drawing.Size(81, 24);
        this.butExtra.TabIndex = 3;
        this.butExtra.Text = "Extra";
        this.butExtra.Click += new System.EventHandler(this.butExtra_Click);
        //
        // butMissing
        //
        this.butMissing.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMissing.setAutosize(true);
        this.butMissing.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMissing.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMissing.setCornerRadius(4F);
        this.butMissing.Location = new System.Drawing.Point(18, 342);
        this.butMissing.Name = "butMissing";
        this.butMissing.Size = new System.Drawing.Size(81, 24);
        this.butMissing.TabIndex = 3;
        this.butMissing.Text = "Missing";
        this.butMissing.Click += new System.EventHandler(this.butMissing_Click);
        //
        // butPayments
        //
        this.butPayments.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPayments.setAutosize(true);
        this.butPayments.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPayments.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPayments.setCornerRadius(4F);
        this.butPayments.Location = new System.Drawing.Point(18, 298);
        this.butPayments.Name = "butPayments";
        this.butPayments.Size = new System.Drawing.Size(81, 24);
        this.butPayments.TabIndex = 3;
        this.butPayments.Text = "Payments";
        this.butPayments.Click += new System.EventHandler(this.butPayments_Click);
        //
        // butViewImported
        //
        this.butViewImported.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butViewImported.setAutosize(true);
        this.butViewImported.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butViewImported.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butViewImported.setCornerRadius(4F);
        this.butViewImported.Location = new System.Drawing.Point(18, 268);
        this.butViewImported.Name = "butViewImported";
        this.butViewImported.Size = new System.Drawing.Size(81, 24);
        this.butViewImported.TabIndex = 3;
        this.butViewImported.Text = "View Imported";
        this.butViewImported.Click += new System.EventHandler(this.butViewImported_Click);
        //
        // butImport
        //
        this.butImport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImport.setAutosize(true);
        this.butImport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImport.setCornerRadius(4F);
        this.butImport.Location = new System.Drawing.Point(18, 33);
        this.butImport.Name = "butImport";
        this.butImport.Size = new System.Drawing.Size(81, 24);
        this.butImport.TabIndex = 3;
        this.butImport.Text = "Import";
        this.butImport.Click += new System.EventHandler(this.butImport_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(414, 378);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormXChargeReconcile
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(527, 432);
        this.Controls.Add(this.date2);
        this.Controls.Add(this.date1);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.butExtra);
        this.Controls.Add(this.butMissing);
        this.Controls.Add(this.butPayments);
        this.Controls.Add(this.butViewImported);
        this.Controls.Add(this.butImport);
        this.Controls.Add(this.butClose);
        this.Name = "FormXChargeReconcile";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "X-Charge Reconcile";
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butImport;
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butViewImported;
    private System.Windows.Forms.MonthCalendar date2 = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.MonthCalendar date1 = new System.Windows.Forms.MonthCalendar();
    private OpenDental.UI.Button butMissing;
    private OpenDental.UI.Button butExtra;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butPayments;
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
}


