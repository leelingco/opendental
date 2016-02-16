//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:40 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormAudit;
import OpenDental.FormPatientSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.GridSortingStrategy;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.SecurityLog;
import OpenDentBusiness.SecurityLogHashes;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Userod;
import OpenDentBusiness.UserodC;
import OpenDentBusiness.Userods;

/**
* Summary description for FormBasicTemplate.
*/
public class FormAudit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.ODGrid grid;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateFrom;
    private OpenDental.ValidDate textDateTo;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butRefresh;
    private ComboBox comboPermission = new ComboBox();
    private Label label1 = new Label();
    private Label label4 = new Label();
    private Label label5 = new Label();
    private ComboBox comboUser = new ComboBox();
    private TextBox textPatient = new TextBox();
    private OpenDental.UI.Button butFind;
    private OpenDental.UI.Button butAll;
    /**
    * The selected patNum.  Can be 0 to include all.
    */
    private long PatNum = new long();
    private OpenDental.UI.Button butCurrent;
    private OpenDental.UI.Button butPrint;
    /**
    * This gets set externally beforehand.  Lets user quickly select audit trail for current patient.
    */
    public long CurPatNum = new long();
    private PrintDocument pd = new PrintDocument();
    /**
    * This alphabetizes the permissions, except for "none", which is always first.  If using a foreign language, the order will be according to the English version, not the foreign translated text.
    */
    private List<String> permissionsAlphabetic = new List<String>();
    private boolean headingPrinted = new boolean();
    private int pagesPrinted = new int();
    private int headingPrintH = new int();
    /**
    * 
    */
    public FormAudit() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        String[] enumArray = Enum.GetNames(Permissions.class);
        permissionsAlphabetic = new List<String>();
        for (int i = 1;i < enumArray.Length;i++)
        {
            permissionsAlphabetic.Add(enumArray[i]);
        }
        permissionsAlphabetic.Sort();
        permissionsAlphabetic.Insert(0, Permissions.None.ToString());
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAudit.class);
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.comboPermission = new System.Windows.Forms.ComboBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.comboUser = new System.Windows.Forms.ComboBox();
        this.textPatient = new System.Windows.Forms.TextBox();
        this.butCurrent = new OpenDental.UI.Button();
        this.butAll = new OpenDental.UI.Button();
        this.butFind = new OpenDental.UI.Button();
        this.butRefresh = new OpenDental.UI.Button();
        this.textDateFrom = new OpenDental.ValidDate();
        this.textDateTo = new OpenDental.ValidDate();
        this.grid = new OpenDental.UI.ODGrid();
        this.butPrint = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(0, 8);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(75, 14);
        this.label2.TabIndex = 45;
        this.label2.Text = "From Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(9, 30);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(64, 13);
        this.label3.TabIndex = 46;
        this.label3.Text = "To Date";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboPermission
        //
        this.comboPermission.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPermission.FormattingEnabled = true;
        this.comboPermission.Location = new System.Drawing.Point(262, 4);
        this.comboPermission.MaxDropDownItems = 40;
        this.comboPermission.Name = "comboPermission";
        this.comboPermission.Size = new System.Drawing.Size(163, 21);
        this.comboPermission.TabIndex = 50;
        this.comboPermission.SelectionChangeCommitted += new System.EventHandler(this.comboPermission_SelectionChangeCommitted);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(179, 8);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(82, 13);
        this.label1.TabIndex = 51;
        this.label1.Text = "Permission";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(426, 8);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(82, 13);
        this.label4.TabIndex = 52;
        this.label4.Text = "Patient";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(179, 29);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(82, 13);
        this.label5.TabIndex = 55;
        this.label5.Text = "User";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboUser
        //
        this.comboUser.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboUser.FormattingEnabled = true;
        this.comboUser.Location = new System.Drawing.Point(262, 25);
        this.comboUser.MaxDropDownItems = 40;
        this.comboUser.Name = "comboUser";
        this.comboUser.Size = new System.Drawing.Size(163, 21);
        this.comboUser.TabIndex = 54;
        this.comboUser.SelectionChangeCommitted += new System.EventHandler(this.comboUser_SelectionChangeCommitted);
        //
        // textPatient
        //
        this.textPatient.Location = new System.Drawing.Point(509, 4);
        this.textPatient.Name = "textPatient";
        this.textPatient.Size = new System.Drawing.Size(216, 20);
        this.textPatient.TabIndex = 56;
        //
        // butCurrent
        //
        this.butCurrent.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCurrent.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCurrent.setAutosize(true);
        this.butCurrent.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCurrent.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCurrent.setCornerRadius(4F);
        this.butCurrent.Location = new System.Drawing.Point(509, 24);
        this.butCurrent.Name = "butCurrent";
        this.butCurrent.Size = new System.Drawing.Size(63, 24);
        this.butCurrent.TabIndex = 59;
        this.butCurrent.Text = "Current";
        this.butCurrent.Click += new System.EventHandler(this.butCurrent_Click);
        //
        // butAll
        //
        this.butAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAll.setAutosize(true);
        this.butAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAll.setCornerRadius(4F);
        this.butAll.Location = new System.Drawing.Point(662, 24);
        this.butAll.Name = "butAll";
        this.butAll.Size = new System.Drawing.Size(63, 24);
        this.butAll.TabIndex = 58;
        this.butAll.Text = "All";
        this.butAll.Click += new System.EventHandler(this.butAll_Click);
        //
        // butFind
        //
        this.butFind.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butFind.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butFind.setAutosize(true);
        this.butFind.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFind.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFind.setCornerRadius(4F);
        this.butFind.Location = new System.Drawing.Point(585, 24);
        this.butFind.Name = "butFind";
        this.butFind.Size = new System.Drawing.Size(63, 24);
        this.butFind.TabIndex = 57;
        this.butFind.Text = "Find";
        this.butFind.Click += new System.EventHandler(this.butFind_Click);
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(814, 1);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(82, 24);
        this.butRefresh.TabIndex = 49;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(79, 5);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(90, 20);
        this.textDateFrom.TabIndex = 47;
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(79, 26);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(90, 20);
        this.textDateTo.TabIndex = 48;
        //
        // grid
        //
        this.grid.setAllowSortingByColumn(true);
        this.grid.setHScrollVisible(false);
        this.grid.Location = new System.Drawing.Point(8, 54);
        this.grid.Name = "grid";
        this.grid.setScrollValue(0);
        this.grid.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.grid.Size = new System.Drawing.Size(889, 578);
        this.grid.TabIndex = 2;
        this.grid.setTitle("Audit Trail");
        this.grid.setTranslationName("TableAudit");
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
        this.butPrint.Location = new System.Drawing.Point(814, 27);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(82, 24);
        this.butPrint.TabIndex = 60;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // FormAudit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(905, 634);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butCurrent);
        this.Controls.Add(this.butAll);
        this.Controls.Add(this.butFind);
        this.Controls.Add(this.textPatient);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.comboUser);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.comboPermission);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.textDateFrom);
        this.Controls.Add(this.textDateTo);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.grid);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.KeyPreview = true;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAudit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Audit Trail";
        this.Load += new System.EventHandler(this.FormAudit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formAudit_Load(Object sender, System.EventArgs e) throws Exception {
        textDateFrom.Text = DateTime.Today.AddDays(-10).ToShortDateString();
        textDateTo.Text = DateTime.Today.ToShortDateString();
        for (int i = 0;i < permissionsAlphabetic.Count;i++)
        {
            if (i == 0)
            {
                comboPermission.Items.Add(Lan.g(this,"All"));
            }
            else
            {
                //None
                comboPermission.Items.Add(Lan.g("enumPermissions", permissionsAlphabetic[i]));
            } 
        }
        comboPermission.SelectedIndex = 0;
        comboUser.Items.Add(Lan.g(this,"All"));
        comboUser.SelectedIndex = 0;
        for (int i = 0;i < UserodC.getListt().Count;i++)
        {
            comboUser.Items.Add(UserodC.getListt()[i].UserName);
        }
        PatNum = 0;
        fillGrid();
    }

    private void comboUser_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void comboPermission_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butCurrent_Click(Object sender, EventArgs e) throws Exception {
        PatNum = CurPatNum;
        if (PatNum == 0)
        {
            textPatient.Text = "";
        }
        else
        {
            textPatient.Text = Patients.getLim(PatNum).getNameLF();
        } 
        fillGrid();
    }

    private void butFind_Click(Object sender, EventArgs e) throws Exception {
        FormPatientSelect FormP = new FormPatientSelect();
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        PatNum = FormP.SelectedPatNum;
        textPatient.Text = Patients.getLim(PatNum).getNameLF();
        fillGrid();
    }

    private void butAll_Click(Object sender, EventArgs e) throws Exception {
        PatNum = 0;
        textPatient.Text = "";
        fillGrid();
    }

    private void fillGrid() throws Exception {
        long userNum = 0;
        if (comboUser.SelectedIndex > 0)
        {
            userNum = UserodC.getListt()[comboUser.SelectedIndex - 1].UserNum;
        }
         
        SecurityLog[] logList = null;
        if (comboPermission.SelectedIndex == 0)
        {
            logList = SecurityLogs.Refresh(PIn.Date(textDateFrom.Text), PIn.Date(textDateTo.Text), Permissions.None, PatNum, userNum);
        }
        else
        {
            logList = SecurityLogs.Refresh(PIn.Date(textDateFrom.Text), PIn.Date(textDateTo.Text), (Permissions)Enum.Parse(Permissions.class, comboPermission.SelectedItem.ToString()), PatNum, userNum);
        } 
        grid.beginUpdate();
        grid.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableAudit","Date"),70);
        col.setSortingStrategy(GridSortingStrategy.DateParse);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableAudit","Time"),60);
        col.setSortingStrategy(GridSortingStrategy.DateParse);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableAudit","Patient"),100);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableAudit","User"),70);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableAudit","Permission"),110);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableAudit","Computer"),70);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableAudit","Log Text"),560);
        grid.getColumns().add(col);
        grid.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        Userod user;
        for (int i = 0;i < logList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(logList[i].LogDateTime.ToShortDateString());
            row.getCells().Add(logList[i].LogDateTime.ToShortTimeString());
            row.getCells().Add(logList[i].PatientName);
            user = Userods.GetUser(logList[i].UserNum);
            //user might be null due to old bugs.
            if (user == null)
            {
                row.getCells().add("unknown");
            }
            else
            {
                row.getCells().Add(Userods.GetUser(logList[i].UserNum).UserName);
            } 
            if (logList[i].PermType == Permissions.ChartModule)
            {
                row.getCells().add("ChartModuleViewed");
            }
            else if (logList[i].PermType == Permissions.FamilyModule)
            {
                row.getCells().add("FamilyModuleViewed");
            }
            else if (logList[i].PermType == Permissions.AccountModule)
            {
                row.getCells().add("AccountModuleViewed");
            }
            else if (logList[i].PermType == Permissions.ImagesModule)
            {
                row.getCells().add("ImagesModuleViewed");
            }
            else if (logList[i].PermType == Permissions.TPModule)
            {
                row.getCells().add("TreatmentPlanModuleViewed");
            }
            else
            {
                row.getCells().Add(logList[i].PermType.ToString());
            }     
            row.getCells().Add(logList[i].CompName);
            row.getCells().Add(logList[i].LogText);
            //Get the hash for the audit log entry from the database and rehash to compare
            String newHash = SecurityLogHashes.GetHashString(logList[i]);
            if (!StringSupport.equals(logList[i].LogHash, newHash))
            {
                row.setColorText(Color.Red);
            }
             
            //Bad hash or no hash entry at all.  This prevents users from deleting the entire hash table to make the audit trail look valid and encrypted.
            //historical entries will show as red.
            grid.getRows().add(row);
        }
        grid.endUpdate();
        grid.scrollToEnd();
    }

    private void butRefresh_Click(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(textDateFrom.Text, "") || StringSupport.equals(textDateTo.Text, "") || !StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        fillGrid();
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        pagesPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        //pd.OriginAtMargins=true;
        pd.DefaultPageSettings.Landscape = true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        headingPrinted = false;
        try
        {
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Audit trail printed"))
            {
                pd.Print();
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    /**
    * Raised for each page to be printed.
    */
    private void pd_PrintPage(Object sender, PrintPageEventArgs e) throws Exception {
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
            text = Lan.g(this,"Audit Trail");
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            text = textDateFrom.Text + " " + Lan.g(this,"to") + " " + textDateTo.Text;
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += 20;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = grid.printPage(g,pagesPrinted,bounds,headingPrintH);
        pagesPrinted++;
        if (yPos == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
            pagesPrinted = 0;
        } 
        g.Dispose();
    }

}


