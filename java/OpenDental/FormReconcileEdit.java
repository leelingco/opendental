//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:39 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormReconcileEdit;
import OpenDental.Lan;
import OpenDental.MigraDocHelper;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.JournalEntries;
import OpenDentBusiness.JournalEntry;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Reconcile;
import OpenDentBusiness.Reconciles;

/**
* Summary description for FormBasicTemplate.
*/
public class FormReconcileEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.ValidDate textDate;
    private Label label1 = new Label();
    private Label label2 = new Label();
    private OpenDental.ValidDouble textStart;
    private OpenDental.ValidDouble textEnd;
    private Label label3 = new Label();
    private TextBox textTarget = new TextBox();
    private Label label4 = new Label();
    private OpenDental.UI.ODGrid gridMain;
    private Label label5 = new Label();
    private TextBox textSum = new TextBox();
    private CheckBox checkLocked = new CheckBox();
    private Reconcile ReconcileCur;
    private List<JournalEntry> JournalList = new List<JournalEntry>();
    private OpenDental.UI.Button butDelete;
    private TextBox textFindAmount = new TextBox();
    private Label label6 = new Label();
    private OpenDental.UI.Button butPrint;
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    /**
    * 
    */
    public FormReconcileEdit(Reconcile reconcileCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        ReconcileCur = reconcileCur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReconcileEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textTarget = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.textSum = new System.Windows.Forms.TextBox();
        this.checkLocked = new System.Windows.Forms.CheckBox();
        this.textFindAmount = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.butPrint = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textEnd = new OpenDental.ValidDouble();
        this.textStart = new OpenDental.ValidDouble();
        this.textDate = new OpenDental.ValidDate();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(370, 4);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(96, 20);
        this.label1.TabIndex = 3;
        this.label1.Text = "Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(342, 47);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(124, 20);
        this.label2.TabIndex = 5;
        this.label2.Text = "Starting Balance";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(342, 68);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(124, 20);
        this.label3.TabIndex = 7;
        this.label3.Text = "Ending Balance";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTarget
        //
        this.textTarget.Location = new System.Drawing.Point(467, 90);
        this.textTarget.Name = "textTarget";
        this.textTarget.ReadOnly = true;
        this.textTarget.Size = new System.Drawing.Size(100, 20);
        this.textTarget.TabIndex = 9;
        this.textTarget.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(353, 89);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(113, 20);
        this.label4.TabIndex = 10;
        this.label4.Text = "Target Change";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(353, 110);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(113, 20);
        this.label5.TabIndex = 13;
        this.label5.Text = "Sum of Transactions";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSum
        //
        this.textSum.Location = new System.Drawing.Point(467, 111);
        this.textSum.Name = "textSum";
        this.textSum.ReadOnly = true;
        this.textSum.Size = new System.Drawing.Size(100, 20);
        this.textSum.TabIndex = 12;
        this.textSum.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // checkLocked
        //
        this.checkLocked.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkLocked.Location = new System.Drawing.Point(374, 28);
        this.checkLocked.Name = "checkLocked";
        this.checkLocked.Size = new System.Drawing.Size(108, 18);
        this.checkLocked.TabIndex = 14;
        this.checkLocked.Text = "Locked";
        this.checkLocked.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkLocked.UseVisualStyleBackColor = true;
        this.checkLocked.Click += new System.EventHandler(this.checkLocked_Click);
        //
        // textFindAmount
        //
        this.textFindAmount.Location = new System.Drawing.Point(467, 186);
        this.textFindAmount.Name = "textFindAmount";
        this.textFindAmount.Size = new System.Drawing.Size(100, 20);
        this.textFindAmount.TabIndex = 16;
        this.textFindAmount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        this.textFindAmount.TextChanged += new System.EventHandler(this.textFindAmount_TextChanged);
        this.textFindAmount.Leave += new System.EventHandler(this.textFindAmount_Leave);
        //
        // label6
        //
        this.label6.AutoSize = true;
        this.label6.Location = new System.Drawing.Point(399, 190);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(66, 13);
        this.label6.TabIndex = 17;
        this.label6.Text = "Find Amount";
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(413, 616);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 26);
        this.butPrint.TabIndex = 19;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(4, 648);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(84, 26);
        this.butDelete.TabIndex = 15;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(4, 5);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(338, 637);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("Transactions");
        this.gridMain.setTranslationName("TableJournal");
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
        // textEnd
        //
        this.textEnd.Location = new System.Drawing.Point(467, 69);
        this.textEnd.Name = "textEnd";
        this.textEnd.Size = new System.Drawing.Size(100, 20);
        this.textEnd.TabIndex = 8;
        this.textEnd.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        this.textEnd.TextChanged += new System.EventHandler(this.textEnd_TextChanged);
        //
        // textStart
        //
        this.textStart.Location = new System.Drawing.Point(467, 48);
        this.textStart.Name = "textStart";
        this.textStart.Size = new System.Drawing.Size(100, 20);
        this.textStart.TabIndex = 6;
        this.textStart.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        this.textStart.TextChanged += new System.EventHandler(this.textStart_TextChanged);
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(467, 5);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(100, 20);
        this.textDate.TabIndex = 2;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(494, 616);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(494, 648);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormReconcileEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(587, 677);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textFindAmount);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.checkLocked);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textSum);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textTarget);
        this.Controls.Add(this.textEnd);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textStart);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormReconcileEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Reconcile";
        this.Load += new System.EventHandler(this.FormReconcileEdit_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormReconcileEdit_FormClosing);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formReconcileEdit_Load(Object sender, EventArgs e) throws Exception {
        textDate.Text = ReconcileCur.DateReconcile.ToShortDateString();
        checkLocked.Checked = ReconcileCur.IsLocked;
        textStart.Text = ReconcileCur.StartingBal.ToString("n");
        textEnd.Text = ReconcileCur.EndingBal.ToString("n");
        textTarget.Text = (ReconcileCur.EndingBal - ReconcileCur.StartingBal).ToString("n");
        boolean includeUncleared = !ReconcileCur.IsLocked;
        JournalList = JournalEntries.getForReconcile(ReconcileCur.AccountNum,includeUncleared,ReconcileCur.ReconcileNum);
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableJournal","Chk #"), 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableJournal","Date"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableJournal","Deposits"), 70, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableJournal","Withdrawals"), 70, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("X", 30, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        double sum = 0;
        for (int i = 0;i < JournalList.Count;i++)
        {
            //to avoid cumulative errors.
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(JournalList[i].CheckNumber);
            row.getCells().Add(JournalList[i].DateDisplayed.ToShortDateString());
            //row.Cells.Add(JournalList[i].Memo);
            //row.Cells.Add(JournalList[i].Splits);
            if (JournalList[i].DebitAmt == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(JournalList[i].DebitAmt.ToString("n"));
                if (JournalList[i].ReconcileNum != 0)
                {
                    sum += (double)JournalList[i].DebitAmt;
                }
                 
            } 
            if (JournalList[i].CreditAmt == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(JournalList[i].CreditAmt.ToString("n"));
                if (JournalList[i].ReconcileNum != 0)
                {
                    sum -= (double)JournalList[i].CreditAmt;
                }
                 
            } 
            if (JournalList[i].ReconcileNum == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add("X");
            } 
            if (this.textFindAmount.Text.Length > 0)
            {
                try
                {
                    double famt = Convert.ToDouble(textFindAmount.Text);
                    if (famt == JournalList[i].CreditAmt || famt == JournalList[i].DebitAmt)
                    {
                        //row.ColorText=System.Drawing.Color.DarkRed;
                        row.setColorBackG(System.Drawing.Color.Yellow);
                    }
                     
                }
                catch (Exception __dummyCatchVar0)
                {
                }
            
            }
             
            //Just a format error in the amount probably, who cares.
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        textSum.Text = sum.ToString("n");
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (e.getCol() != 4)
        {
            return ;
        }
         
        if (checkLocked.Checked)
        {
            return ;
        }
         
        if (JournalList[e.getRow()].ReconcileNum == 0)
        {
            JournalList[e.getRow()].ReconcileNum = ReconcileCur.ReconcileNum;
        }
        else
        {
            JournalList[e.getRow()].ReconcileNum = 0;
        } 
        int rowClicked = e.getRow();
        fillGrid();
        gridMain.setSelected(rowClicked,true);
    }

    private void textStart_TextChanged(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textStart.errorProvider1.GetError(textStart), "") || !StringSupport.equals(textEnd.errorProvider1.GetError(textEnd), ""))
        {
            return ;
        }
         
        textTarget.Text = (PIn.Double(textEnd.Text) - PIn.Double(textStart.Text)).ToString("n");
    }

    private void textEnd_TextChanged(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textStart.errorProvider1.GetError(textStart), "") || !StringSupport.equals(textEnd.errorProvider1.GetError(textEnd), ""))
        {
            return ;
        }
         
        textTarget.Text = (PIn.Double(textEnd.Text) - PIn.Double(textStart.Text)).ToString("n");
    }

    private void checkLocked_Click(Object sender, EventArgs e) throws Exception {
        if (checkLocked.Checked)
        {
            if (textTarget.Text != textSum.Text)
            {
                MsgBox.show(this,"Target change must match sum of transactions.");
                checkLocked.Checked = false;
                return ;
            }
             
        }
        else
        {
        } 
        //unchecking
        //need to check permissions here.
        saveList();
        boolean includeUncleared = !checkLocked.Checked;
        JournalList = JournalEntries.getForReconcile(ReconcileCur.AccountNum,includeUncleared,ReconcileCur.ReconcileNum);
        fillGrid();
    }

    /**
    * Saves all changes to JournalList to database.  Can only be called once when closing form.
    */
    private void saveList() throws Exception {
        JournalEntries.saveList(JournalList,ReconcileCur.ReconcileNum);
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        try
        {
            Reconciles.delete(ReconcileCur);
            DialogResult = DialogResult.OK;
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), "") || !StringSupport.equals(textStart.errorProvider1.GetError(textStart), "") || !StringSupport.equals(textEnd.errorProvider1.GetError(textEnd), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        ReconcileCur.DateReconcile = PIn.Date(textDate.Text);
        ReconcileCur.StartingBal = PIn.Double(textStart.Text);
        ReconcileCur.EndingBal = PIn.Double(textEnd.Text);
        ReconcileCur.IsLocked = checkLocked.Checked;
        Reconciles.update(ReconcileCur);
        saveList();
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formReconcileEdit_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (IsNew)
        {
            for (int i = 0;i < JournalList.Count;i++)
            {
                JournalList[i].ReconcileNum = 0;
            }
            saveList();
            //detaches all journal entries.
            Reconciles.delete(ReconcileCur);
        }
         
    }

    private void textFindAmount_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void textFindAmount_Leave(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(this.textFindAmount.Text, "") && !Regex.IsMatch(this.textFindAmount.Text, "[0-9]+(\\.[0-9]+)?"))
        {
            MessageBox.Show("Invalid amount format in text search amount field.");
        }
         
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        MigraDoc.DocumentObjectModel.Document doc = createPrintDocument();
        MigraDoc.Rendering.Printing.MigraDocPrintDocument printdoc = new MigraDoc.Rendering.Printing.MigraDocPrintDocument();
        MigraDoc.Rendering.DocumentRenderer renderer = new MigraDoc.Rendering.DocumentRenderer(doc);
        renderer.PrepareDocument();
        printdoc.Renderer = renderer;
        //Always prints to the Windows default printer.
        if (PrinterL.SetPrinter(pd2, PrintSituation.Default, 0, "Reconcile list printed"))
        {
            printdoc.Print();
        }
         
    }

    private MigraDoc.DocumentObjectModel.Document createPrintDocument() throws Exception {
        String text = new String();
        MigraDoc.DocumentObjectModel.Document doc = new MigraDoc.DocumentObjectModel.Document();
        doc.DefaultPageSetup.PageWidth = Unit.FromInch(8.5);
        doc.DefaultPageSetup.PageHeight = Unit.FromInch(11);
        doc.DefaultPageSetup.TopMargin = Unit.FromInch(.5);
        doc.DefaultPageSetup.LeftMargin = Unit.FromInch(.5);
        doc.DefaultPageSetup.RightMargin = Unit.FromInch(.5);
        MigraDoc.DocumentObjectModel.Section section = doc.AddSection();
        MigraDoc.DocumentObjectModel.Font headingFont = MigraDocHelper.CreateFont(13, true);
        MigraDoc.DocumentObjectModel.Font bodyFontx = MigraDocHelper.CreateFont(9, false);
        MigraDoc.DocumentObjectModel.Font nameFontx = MigraDocHelper.CreateFont(9, true);
        MigraDoc.DocumentObjectModel.Font totalFontx = MigraDocHelper.CreateFont(9, true);
        Paragraph par = section.AddParagraph();
        ParagraphFormat parformat = new ParagraphFormat();
        parformat.Alignment = ParagraphAlignment.Center;
        parformat.Font = MigraDocHelper.CreateFont(14, true);
        par.Format = parformat;
        //Render the reconcile grid.
        par = section.AddParagraph();
        par.Format.Alignment = ParagraphAlignment.Center;
        par.AddFormattedText(Lan.g(this,"RECONCILE"), totalFontx);
        par.AddLineBreak();
        text = Accounts.getAccount(ReconcileCur.AccountNum).Description.ToUpper();
        par.AddFormattedText(text, totalFontx);
        par.AddLineBreak();
        text = PrefC.getString(PrefName.PracticeTitle);
        par.AddText(text);
        par.AddLineBreak();
        text = PrefC.getString(PrefName.PracticePhone);
        if (text.Length == 10 && StringSupport.equals(Application.CurrentCulture.Name, "en-US"))
        {
            text = "(" + text.Substring(0, 3) + ")" + text.Substring(3, 3) + "-" + text.Substring(6);
        }
         
        par.AddText(text);
        MigraDocHelper.InsertSpacer(section, 10);
        MigraDocHelper.DrawGrid(section, gridMain);
        return doc;
    }

}


