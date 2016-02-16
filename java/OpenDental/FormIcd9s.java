//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;

public class FormIcd9s  extends Form 
{

    public boolean IsSelectionMode = new boolean();
    public ICD9 SelectedIcd9;
    private List<ICD9> icd9List = new List<ICD9>();
    private boolean changed = new boolean();
    public FormIcd9s() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formIcd9s_Load(Object sender, EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            butClose.Text = Lan.g(this,"Cancel");
        }
        else
        {
            butOK.Visible = false;
        } 
    }

    private void butSearch_Click(Object sender, EventArgs e) throws Exception {
        //if(textCode.Text.Length<3) {
        //	MsgBox.Show(this,"Please enter at least 3 characters before searching.");
        //	return;
        //}
        //forget about the above.  Allow general browsing by entering no search parameters.
        fillGrid();
    }

    private void fillGrid() throws Exception {
        Cursor = Cursors.WaitCursor;
        icd9List = ICD9s.GetByCodeOrDescription(textCode.Text);
        listMain.Items.Clear();
        for (int i = 0;i < icd9List.Count;i++)
        {
            listMain.Items.Add(icd9List[i].ICD9Code + " - " + icd9List[i].Description);
        }
        Cursor = Cursors.Default;
    }

    private void listMain_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listMain.SelectedIndex == -1)
        {
            return ;
        }
         
        if (IsSelectionMode)
        {
            SelectedIcd9 = icd9List[listMain.SelectedIndex];
            DialogResult = DialogResult.OK;
            return ;
        }
         
    }

    /* Commented to prevent Icd9 edit window from being shown
    			changed=true;
    			FormIcd9Edit FormI=new FormIcd9Edit(icd9List[listMain.SelectedIndex]);
    			FormI.ShowDialog();
    			if(FormI.DialogResult!=DialogResult.OK) {
    				return;
    			}
    			FillGrid();
    			*/
    /* Deprecated. This is populated from a list and follows a standard so we do not allow custom ICD-9 Codes.
    		private void butAdd_Click(object sender,EventArgs e) {
    			changed=true;
    			ICD9 icd9=new ICD9();
    			FormIcd9Edit FormI=new FormIcd9Edit(icd9);
    			FormI.IsNew=true;
    			FormI.ShowDialog();
    			FillGrid();
    		}
    		*/
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //not even visible unless IsSelectionMode
        if (listMain.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        SelectedIcd9 = icd9List[listMain.SelectedIndex];
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
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
        this.listMain = new System.Windows.Forms.ListBox();
        this.butOK = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.textCode = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butSearch = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listMain
        //
        this.listMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.listMain.IntegralHeight = false;
        this.listMain.Location = new System.Drawing.Point(20, 36);
        this.listMain.Name = "listMain";
        this.listMain.Size = new System.Drawing.Size(501, 642);
        this.listMain.TabIndex = 15;
        this.listMain.DoubleClick += new System.EventHandler(this.listMain_DoubleClick);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(549, 598);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(549, 639);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textCode
        //
        this.textCode.Location = new System.Drawing.Point(180, 10);
        this.textCode.Name = "textCode";
        this.textCode.Size = new System.Drawing.Size(100, 20);
        this.textCode.TabIndex = 17;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(5, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(172, 16);
        this.label1.TabIndex = 18;
        this.label1.Text = "Code or Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.Location = new System.Drawing.Point(286, 8);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 24);
        this.butSearch.TabIndex = 19;
        this.butSearch.Text = "Search";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // FormIcd9s
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(649, 690);
        this.Controls.Add(this.butSearch);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textCode);
        this.Controls.Add(this.listMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butClose);
        this.Name = "FormIcd9s";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "ICD9s";
        this.Load += new System.EventHandler(this.FormIcd9s_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.ListBox listMain = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.TextBox textCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSearch;
}


