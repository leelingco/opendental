//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.RxNorm;
import OpenDentBusiness.RxNorms;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormRxNorms  extends Form 
{

    private List<RxNorm> rxList = new List<RxNorm>();
    /**
    * When this window is used for selecting an RxNorm (medication.RxCui), then use must click OK, None, or double click in grid.  In those cases, this field will have a value.  If None was clicked, it will be a new RxNorm with an RxCui of zero.
    */
    public RxNorm SelectedRxNorm;
    public List<RxNorm> ListSelectedRxNorms = new List<RxNorm>();
    public boolean IsSelectionMode = new boolean();
    public boolean IsMultiSelectMode = new boolean();
    public FormRxNorms() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formRxNorms_Load(Object sender, EventArgs e) throws Exception {
        if (!IsSelectionMode && !IsMultiSelectMode)
        {
            butNone.Visible = false;
            butOK.Visible = false;
            butCancel.Text = "Close";
        }
         
        if (IsMultiSelectMode)
        {
            gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        }
         
        checkIgnore.Checked = true;
    }

    private void formRxNorms_Shown(Object sender, EventArgs e) throws Exception {
        if (RxNorms.isRxNormTableEmpty())
        {
            MessageBox.Show("The RxNorm table in the database is empty.  If you intend to use RxNorm codes, use the code system importer tool in the Setup>EHR menu.  It will add about 10 MB to your database size.");
        }
         
    }

    private void butSearch_Click(Object sender, EventArgs e) throws Exception {
        fillGrid(false);
    }

    private void butExact_Click(Object sender, EventArgs e) throws Exception {
        fillGrid(true);
    }

    private void fillGrid(boolean isExact) throws Exception {
        Cursor = Cursors.WaitCursor;
        rxList = RxNorms.GetListByCodeOrDesc(textCode.Text, isExact, checkIgnore.Checked);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("FormRxNorms","Code"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormRxNorms","Description"),110);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < rxList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(rxList[i].RxCui);
            row.getCells().Add(rxList[i].Description);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setScrollValue(0);
        Cursor = Cursors.Default;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!IsSelectionMode)
        {
            return ;
        }
         
        SelectedRxNorm = rxList[e.getRow()];
        ListSelectedRxNorms = new List<RxNorm>();
        ListSelectedRxNorms.Add(rxList[e.getRow()]);
        DialogResult = DialogResult.OK;
    }

    //private void butRxNorm_Click(object sender,EventArgs e) {
    //	//if(!MsgBox.Show(this,MsgBoxButtons.OKCancel,"This will ")) {
    //	//	return;
    //	//}
    //	Cursor=Cursors.WaitCursor;
    //	RxNorms.CreateFreshRxNormTableFromZip();
    //	Cursor=Cursors.Default;
    //	MsgBox.Show(this,"done");
    //	//just making sure it worked:
    //	/*
    //	RxNorm rxNorm=RxNorms.GetOne(1);
    //	MsgBox.Show(this,rxNorm.RxNormNum+" "+rxNorm.RxCui+" "+rxNorm.MmslCode+" "+rxNorm.Description);
    //	MsgBox.Show(this,RxNorms.GetMmslCodeByRxCui("1000005")+" <-- should be 26420");
    //	MsgBox.Show(this,RxNorms.GetMmslCodeByRxCui("1000002")+" <-- should be blank");*/
    //}
    private void butNone_Click(Object sender, EventArgs e) throws Exception {
        SelectedRxNorm = new RxNorm();
        ListSelectedRxNorms = new List<RxNorm>();
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() < 0)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        SelectedRxNorm = rxList[gridMain.getSelectedIndex()];
        ListSelectedRxNorms = new List<RxNorm>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            ListSelectedRxNorms.Add(rxList[gridMain.getSelectedIndices()[i]]);
        }
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
        this.labelCodeOrDesc = new System.Windows.Forms.Label();
        this.textCode = new System.Windows.Forms.TextBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butExact = new OpenDental.UI.Button();
        this.butSearch = new OpenDental.UI.Button();
        this.butNone = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.checkIgnore = new System.Windows.Forms.CheckBox();
        this.SuspendLayout();
        //
        // labelCodeOrDesc
        //
        this.labelCodeOrDesc.Location = new System.Drawing.Point(3, 10);
        this.labelCodeOrDesc.Name = "labelCodeOrDesc";
        this.labelCodeOrDesc.Size = new System.Drawing.Size(172, 16);
        this.labelCodeOrDesc.TabIndex = 21;
        this.labelCodeOrDesc.Text = "Code or Description";
        this.labelCodeOrDesc.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCode
        //
        this.textCode.Location = new System.Drawing.Point(178, 7);
        this.textCode.Name = "textCode";
        this.textCode.Size = new System.Drawing.Size(100, 20);
        this.textCode.TabIndex = 20;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(26, 34);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(642, 599);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("RxNorm Codes");
        this.gridMain.setTranslationName(null);
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
        // butExact
        //
        this.butExact.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExact.setAutosize(true);
        this.butExact.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExact.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExact.setCornerRadius(4F);
        this.butExact.Location = new System.Drawing.Point(365, 5);
        this.butExact.Name = "butExact";
        this.butExact.Size = new System.Drawing.Size(75, 24);
        this.butExact.TabIndex = 24;
        this.butExact.Text = "Exact";
        this.butExact.Click += new System.EventHandler(this.butExact_Click);
        //
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.Location = new System.Drawing.Point(284, 5);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 24);
        this.butSearch.TabIndex = 22;
        this.butSearch.Text = "Similar";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // butNone
        //
        this.butNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNone.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butNone.setAutosize(true);
        this.butNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNone.setCornerRadius(4F);
        this.butNone.Location = new System.Drawing.Point(26, 660);
        this.butNone.Name = "butNone";
        this.butNone.Size = new System.Drawing.Size(75, 24);
        this.butNone.TabIndex = 3;
        this.butNone.Text = "&None";
        this.butNone.Click += new System.EventHandler(this.butNone_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(512, 660);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
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
        this.butCancel.Location = new System.Drawing.Point(593, 660);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // checkIgnore
        //
        this.checkIgnore.AutoSize = true;
        this.checkIgnore.Location = new System.Drawing.Point(486, 9);
        this.checkIgnore.Name = "checkIgnore";
        this.checkIgnore.Size = new System.Drawing.Size(101, 17);
        this.checkIgnore.TabIndex = 25;
        this.checkIgnore.Text = "Ignore Numbers";
        this.checkIgnore.UseVisualStyleBackColor = true;
        //
        // FormRxNorms
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(693, 702);
        this.Controls.Add(this.checkIgnore);
        this.Controls.Add(this.butExact);
        this.Controls.Add(this.butSearch);
        this.Controls.Add(this.labelCodeOrDesc);
        this.Controls.Add(this.textCode);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butNone);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormRxNorms";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "RxNorms";
        this.Load += new System.EventHandler(this.FormRxNorms_Load);
        this.Shown += new System.EventHandler(this.FormRxNorms_Shown);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butNone;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butSearch;
    private System.Windows.Forms.Label labelCodeOrDesc = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCode = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butExact;
    private System.Windows.Forms.CheckBox checkIgnore = new System.Windows.Forms.CheckBox();
}


