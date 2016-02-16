//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:17 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormJournal;
import OpenDental.FormReconciles;
import OpenDental.FormRpPrintPreview;
import OpenDental.FormTransactionEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Account;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.AccountType;
import OpenDentBusiness.JournalEntries;
import OpenDentBusiness.JournalEntry;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Security;
import OpenDentBusiness.Transaction;
import OpenDentBusiness.Transactions;

/**
* Summary description for FormBasicTemplate.
*/
public class FormJournal  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.ODToolBar ToolBarMain;
    private OpenDental.UI.ODGrid gridMain;
    private IContainer components = new IContainer();
    private Account AccountCur;
    private ImageList imageListMain = new ImageList();
    private List<JournalEntry> JournalList = new List<JournalEntry>();
    private PrintDocument pd2 = new PrintDocument();
    private boolean headingPrinted = new boolean();
    private int pagesPrinted = new int();
    private int headingPrintH = new int();
    private Label label1 = new Label();
    private OpenDental.ValidDate textDateFrom;
    private OpenDental.ValidDate textDateTo;
    private Label label2 = new Label();
    private OpenDental.UI.Button butRefresh;
    private MonthCalendar calendarFrom = new MonthCalendar();
    private OpenDental.UI.Button butDropFrom;
    private OpenDental.UI.Button butDropTo;
    private MonthCalendar calendarTo = new MonthCalendar();
    private boolean isPrinting = new boolean();
    private OpenDental.ValidDouble textAmt;
    private Label label3 = new Label();
    private Label label4 = new Label();
    private TextBox textFindText = new TextBox();
    //set this externally so that the ending balances will match what's showing in the Chart of Accounts.
    public DateTime InitialAsOfDate = new DateTime();
    /**
    * 
    */
    public FormJournal(Account accountCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        AccountCur = accountCur;
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormJournal.class);
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.label1 = new System.Windows.Forms.Label();
        this.textDateFrom = new OpenDental.ValidDate();
        this.textDateTo = new OpenDental.ValidDate();
        this.label2 = new System.Windows.Forms.Label();
        this.butRefresh = new OpenDental.UI.Button();
        this.calendarFrom = new System.Windows.Forms.MonthCalendar();
        this.butDropFrom = new OpenDental.UI.Button();
        this.butDropTo = new OpenDental.UI.Button();
        this.calendarTo = new System.Windows.Forms.MonthCalendar();
        this.textAmt = new OpenDental.ValidDouble();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.textFindText = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "Add.gif");
        this.imageListMain.Images.SetKeyName(1, "print.gif");
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(0, 56);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(844, 615);
        this.gridMain.TabIndex = 1;
        this.gridMain.setTitle(null);
        this.gridMain.setTranslationName("TableJournal");
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
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(844, 29);
        this.ToolBarMain.TabIndex = 0;
        this.ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(2, 31);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(75, 18);
        this.label1.TabIndex = 2;
        this.label1.Text = "From";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(78, 32);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(81, 20);
        this.textDateFrom.TabIndex = 3;
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(268, 32);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(81, 20);
        this.textDateTo.TabIndex = 5;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(195, 31);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(72, 18);
        this.label2.TabIndex = 4;
        this.label2.Text = "To";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(711, 31);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(75, 23);
        this.butRefresh.TabIndex = 6;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.UseVisualStyleBackColor = true;
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // calendarFrom
        //
        this.calendarFrom.Location = new System.Drawing.Point(5, 56);
        this.calendarFrom.MaxSelectionCount = 1;
        this.calendarFrom.Name = "calendarFrom";
        this.calendarFrom.TabIndex = 7;
        this.calendarFrom.Visible = false;
        this.calendarFrom.DateSelected += new System.Windows.Forms.DateRangeEventHandler(this.calendarFrom_DateSelected);
        //
        // butDropFrom
        //
        this.butDropFrom.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDropFrom.setAutosize(true);
        this.butDropFrom.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDropFrom.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDropFrom.setCornerRadius(4F);
        this.butDropFrom.Location = new System.Drawing.Point(161, 30);
        this.butDropFrom.Name = "butDropFrom";
        this.butDropFrom.Size = new System.Drawing.Size(22, 23);
        this.butDropFrom.TabIndex = 8;
        this.butDropFrom.Text = "V";
        this.butDropFrom.UseVisualStyleBackColor = true;
        this.butDropFrom.Click += new System.EventHandler(this.butDropFrom_Click);
        //
        // butDropTo
        //
        this.butDropTo.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDropTo.setAutosize(true);
        this.butDropTo.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDropTo.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDropTo.setCornerRadius(4F);
        this.butDropTo.Location = new System.Drawing.Point(351, 30);
        this.butDropTo.Name = "butDropTo";
        this.butDropTo.Size = new System.Drawing.Size(22, 23);
        this.butDropTo.TabIndex = 9;
        this.butDropTo.Text = "V";
        this.butDropTo.UseVisualStyleBackColor = true;
        this.butDropTo.Click += new System.EventHandler(this.butDropTo_Click);
        //
        // calendarTo
        //
        this.calendarTo.Location = new System.Drawing.Point(195, 56);
        this.calendarTo.MaxSelectionCount = 1;
        this.calendarTo.Name = "calendarTo";
        this.calendarTo.TabIndex = 10;
        this.calendarTo.Visible = false;
        this.calendarTo.DateSelected += new System.Windows.Forms.DateRangeEventHandler(this.calendarTo_DateSelected);
        //
        // textAmt
        //
        this.textAmt.ForeColor = System.Drawing.SystemColors.WindowText;
        this.textAmt.Location = new System.Drawing.Point(450, 32);
        this.textAmt.Name = "textAmt";
        this.textAmt.Size = new System.Drawing.Size(81, 20);
        this.textAmt.TabIndex = 11;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(387, 32);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(63, 18);
        this.label3.TabIndex = 12;
        this.label3.Text = "Find Amt";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(537, 32);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(68, 18);
        this.label4.TabIndex = 13;
        this.label4.Text = "Find Text";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // textFindText
        //
        this.textFindText.Location = new System.Drawing.Point(605, 32);
        this.textFindText.Name = "textFindText";
        this.textFindText.Size = new System.Drawing.Size(78, 20);
        this.textFindText.TabIndex = 14;
        //
        // FormJournal
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(844, 671);
        this.Controls.Add(this.textFindText);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textAmt);
        this.Controls.Add(this.calendarTo);
        this.Controls.Add(this.butDropTo);
        this.Controls.Add(this.butDropFrom);
        this.Controls.Add(this.calendarFrom);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.textDateTo);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textDateFrom);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.ToolBarMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormJournal";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Transaction History";
        this.Load += new System.EventHandler(this.FormJournal_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formJournal_Load(Object sender, EventArgs e) throws Exception {
        DateTime firstofYear = new DateTime(InitialAsOfDate.Year, 1, 1);
        textDateTo.Text = InitialAsOfDate.ToShortDateString();
        if (AccountCur.AcctType == AccountType.Income || AccountCur.AcctType == AccountType.Expense)
        {
            textDateFrom.Text = firstofYear.ToShortDateString();
        }
         
        layoutToolBar();
        fillGrid();
        gridMain.scrollToEnd();
    }

    /**
    * Causes the toolbar to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Add Entry"), 0, "", "Add"));
        if (AccountCur.AcctType == AccountType.Asset)
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Reconcile"), -1, "", "Reconcile"));
        }
         
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Print"), 1, "", "Print"));
        //ToolBarMain.Buttons.Add(new ODToolBarButton(ODToolBarButtonStyle.Separator));
        //ToolBarMain.Buttons.Add(new ODToolBarButton(Lan.g(this,"Edit"),-1,Lan.g(this,"Edit Selected Account"),"Edit"));
        //ODToolBarButton button=new ODToolBarButton("",-1,"","PageNum");
        //button.Style=ODToolBarButtonStyle.Label;
        //ToolBarMain.Buttons.Add(button);
        //ToolBarMain.Buttons.Add(new ODToolBarButton("",2,"Go Forward One Page","Fwd"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Close"), -1, "Close This Window", "Close"));
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
        if (__dummyScrutVar0.equals("Add"))
        {
            add_Click();
        }
        else if (__dummyScrutVar0.equals("Reconcile"))
        {
            reconcile_Click();
        }
        else if (__dummyScrutVar0.equals("Print"))
        {
            print_Click();
        }
        else if (__dummyScrutVar0.equals("Close"))
        {
            this.Close();
        }
            
    }

    private void fillGrid() throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            return ;
        }
         
        DateTime dateFrom = PIn.Date(textDateFrom.Text);
        DateTime dateTo = new DateTime();
        if (StringSupport.equals(textDateTo.Text, ""))
        {
            dateTo = DateTime.MaxValue;
        }
        else
        {
            dateTo = PIn.Date(textDateTo.Text);
        } 
        double filterAmt = 0;
        if (StringSupport.equals(textAmt.errorProvider1.GetError(textAmt), ""))
        {
            filterAmt = PIn.Double(textAmt.Text);
        }
         
        JournalList = JournalEntries.getForAccount(AccountCur.AccountNum);
        int scroll = gridMain.getScrollValue();
        gridMain.beginUpdate();
        gridMain.setTitle(AccountCur.Description + " (" + Lan.g("enumAccountType", AccountCur.AcctType.ToString()) + ")");
        gridMain.getColumns().Clear();
        String str = "";
        ODGridColumn col = new ODGridColumn(Lan.g("TableJournal","Chk #"), 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableJournal","Date"),80);
        gridMain.getColumns().add(col);
        if (isPrinting)
        {
            col = new ODGridColumn(Lan.g("TableJournal","Memo"),200);
        }
        else
        {
            col = new ODGridColumn(Lan.g("TableJournal","Memo"),220);
        } 
        gridMain.getColumns().add(col);
        if (isPrinting)
        {
            col = new ODGridColumn(Lan.g("TableJournal","Splits"),200);
        }
        else
        {
            col = new ODGridColumn(Lan.g("TableJournal","Splits"),220);
        } 
        gridMain.getColumns().add(col);
        str = Lan.g("TableJournal","Debit");
        if (Accounts.debitIsPos(AccountCur.AcctType))
        {
            str += Lan.g("TableJournal","(+)");
        }
        else
        {
            str += Lan.g("TableJournal","(-)");
        } 
        col = new ODGridColumn(str, 65, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        str = Lan.g("TableJournal","Credit");
        if (Accounts.debitIsPos(AccountCur.AcctType))
        {
            str += Lan.g("TableJournal","(-)");
        }
        else
        {
            str += Lan.g("TableJournal","(+)");
        } 
        col = new ODGridColumn(str, 65, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableJournal","Balance"), 65, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableJournal","Clear"), 55, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        double bal = 0;
        for (int i = 0;i < JournalList.Count;i++)
        {
            if (JournalList[i].DateDisplayed > dateTo)
            {
                break;
            }
             
            if (AccountCur.AcctType == AccountType.Income || AccountCur.AcctType == AccountType.Expense)
            {
                if (JournalList[i].DateDisplayed < dateFrom)
                {
                    continue;
                }
                 
            }
             
            //for income and expense accounts, previous balances are not included. Only the current timespan.
            if (JournalList[i].DebitAmt != 0)
            {
                if (Accounts.debitIsPos(AccountCur.AcctType))
                {
                    //this one is used for checking account
                    bal += (Decimal)JournalList[i].DebitAmt;
                }
                else
                {
                    bal -= (Decimal)JournalList[i].DebitAmt;
                } 
            }
             
            if (JournalList[i].CreditAmt != 0)
            {
                if (Accounts.debitIsPos(AccountCur.AcctType))
                {
                    //this one is used for checking account
                    bal -= (Decimal)JournalList[i].CreditAmt;
                }
                else
                {
                    bal += (Decimal)JournalList[i].CreditAmt;
                } 
            }
             
            if (AccountCur.AcctType == AccountType.Asset || AccountCur.AcctType == AccountType.Liability || AccountCur.AcctType == AccountType.Equity)
            {
                if (JournalList[i].DateDisplayed < dateFrom)
                {
                    continue;
                }
                 
            }
             
            //for asset, liability, and equity accounts, older entries do affect the current balance.
            if (filterAmt != 0 && filterAmt != JournalList[i].CreditAmt && filterAmt != JournalList[i].DebitAmt)
            {
                continue;
            }
             
            if (!StringSupport.equals(textFindText.Text, "") && !JournalList[i].Memo.ToUpper().Contains(textFindText.Text.ToUpper()) && !JournalList[i].CheckNumber.ToUpper().Contains(textFindText.Text.ToUpper()) && !JournalList[i].Splits.ToUpper().Contains(textFindText.Text.ToUpper()))
            {
                continue;
            }
             
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(JournalList[i].CheckNumber);
            row.getCells().Add(JournalList[i].DateDisplayed.ToShortDateString());
            row.getCells().Add(JournalList[i].Memo);
            row.getCells().Add(JournalList[i].Splits);
            if (JournalList[i].DebitAmt == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(JournalList[i].DebitAmt.ToString("n"));
            } 
            if (JournalList[i].CreditAmt == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(JournalList[i].CreditAmt.ToString("n"));
            } 
            row.getCells().Add(bal.ToString("n"));
            if (JournalList[i].ReconcileNum == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add("X");
            } 
            row.setTag(JournalList[i].Copy());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.scrollToEnd();
    }

    private void add_Click() throws Exception {
        Transaction trans = new Transaction();
        trans.UserNum = Security.getCurUser().UserNum;
        Transactions.insert(trans);
        //we now have a TransactionNum, and datetimeEntry has been set
        FormTransactionEdit FormT = new FormTransactionEdit(trans.TransactionNum,AccountCur.AccountNum);
        FormT.IsNew = true;
        FormT.ShowDialog();
        if (FormT.DialogResult == DialogResult.Cancel)
        {
            //no need to try-catch, since no journal entries were saved.
            Transactions.delete(trans);
        }
         
        fillGrid();
    }

    private void reconcile_Click() throws Exception {
        int selectedRow = gridMain.getSelectedIndex();
        int scrollValue = gridMain.getScrollValue();
        FormReconciles FormR = new FormReconciles(AccountCur.AccountNum);
        FormR.ShowDialog();
        fillGrid();
        gridMain.setSelected(selectedRow,true);
        gridMain.setScrollValue(scrollValue);
    }

    private void print_Click() throws Exception {
        pagesPrinted = 0;
        headingPrinted = false;
        printReport(false);
    }

    /**
    * Preview is only used for debugging.
    */
    public void printReport(boolean justPreview) throws Exception {
        pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        pd2.OriginAtMargins = true;
        if (pd2.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd2.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        isPrinting = true;
        fillGrid();
        try
        {
            if (justPreview)
            {
                FormRpPrintPreview pView = new FormRpPrintPreview();
                pView.printPreviewControl2.Document = pd2;
                pView.ShowDialog();
            }
            else
            {
                if (PrinterL.SetPrinter(pd2, PrintSituation.Default, 0, "Accounting transaction history for " + AccountCur.Description + " printed"))
                {
                    pd2.Print();
                }
                 
            } 
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }

        isPrinting = false;
        fillGrid();
    }

    private void pd2_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = new Rectangle(50, 40, 800, 1035);
        //Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = AccountCur.Description + " (" + Lan.g("enumAccountType", AccountCur.AcctType.ToString()) + ")";
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            text = DateTime.Today.ToShortDateString();
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
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

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        int selectedRow = e.getRow();
        int scrollValue = gridMain.getScrollValue();
        FormTransactionEdit FormT = new FormTransactionEdit(((JournalEntry)gridMain.getRows().get___idx(e.getRow()).getTag()).TransactionNum,AccountCur.AccountNum);
        FormT.ShowDialog();
        if (FormT.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillGrid();
        gridMain.setSelected(selectedRow,true);
        gridMain.setScrollValue(scrollValue);
    }

    private void butDropFrom_Click(Object sender, EventArgs e) throws Exception {
        toggleCalendars();
    }

    private void butDropTo_Click(Object sender, EventArgs e) throws Exception {
        toggleCalendars();
    }

    private void toggleCalendars() throws Exception {
        if (calendarFrom.Visible)
        {
            //hide the calendars
            calendarFrom.Visible = false;
            calendarTo.Visible = false;
        }
        else
        {
            //set the date on the calendars to match what's showing in the boxes
            if (StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") && StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
            {
                //if no date errors
                if (StringSupport.equals(textDateFrom.Text, ""))
                {
                    calendarFrom.SetDate(DateTime.Today);
                }
                else
                {
                    calendarFrom.SetDate(PIn.Date(textDateFrom.Text));
                } 
                if (StringSupport.equals(textDateTo.Text, ""))
                {
                    calendarTo.SetDate(DateTime.Today);
                }
                else
                {
                    calendarTo.SetDate(PIn.Date(textDateTo.Text));
                } 
            }
             
            //show the calendars
            calendarFrom.Visible = true;
            calendarTo.Visible = true;
        } 
    }

    private void calendarFrom_DateSelected(Object sender, DateRangeEventArgs e) throws Exception {
        textDateFrom.Text = calendarFrom.SelectionStart.ToShortDateString();
    }

    private void calendarTo_DateSelected(Object sender, DateRangeEventArgs e) throws Exception {
        textDateTo.Text = calendarTo.SelectionStart.ToShortDateString();
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), "") || !StringSupport.equals(textAmt.errorProvider1.GetError(textAmt), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        calendarFrom.Visible = false;
        calendarTo.Visible = false;
        fillGrid();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


