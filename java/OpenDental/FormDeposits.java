//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:56 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormDepositEdit;
import OpenDental.FormDeposits;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Deposit;
import OpenDentBusiness.Deposits;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

/**
* Summary description for FormBasicTemplate.
*/
public class FormDeposits  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid grid;
    private Deposit[] DList = new Deposit[]();
    private OpenDental.UI.Button butOK;
    /**
    * Use this from Transaction screen when attaching a source document.
    */
    public boolean IsSelectionMode = new boolean();
    /**
    * In selection mode, when closing form with OK, this contains selected deposit.
    */
    public Deposit SelectedDeposit;
    /**
    * 
    */
    public FormDeposits() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDeposits.class);
        this.butClose = new OpenDental.UI.Button();
        this.grid = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(382, 576);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // grid
        //
        this.grid.setHScrollVisible(false);
        this.grid.Location = new System.Drawing.Point(18, 13);
        this.grid.Name = "grid";
        this.grid.setScrollValue(0);
        this.grid.Size = new System.Drawing.Size(189, 588);
        this.grid.TabIndex = 1;
        this.grid.setTitle("Deposit Slips");
        this.grid.setTranslationName("TableDepositSlips");
        this.grid.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.grid.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.grid_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(223, 576);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(77, 26);
        this.butAdd.TabIndex = 98;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(382, 534);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 99;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormDeposits
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(486, 613);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.grid);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAdd);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormDeposits";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Deposit Slips";
        this.Load += new System.EventHandler(this.FormDeposits_Load);
        this.ResumeLayout(false);
    }

    private void formDeposits_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            butAdd.Visible = false;
        }
        else
        {
            butOK.Visible = false;
        } 
        //comboProv.Items.Add(Lan.g(this,"All"));
        //comboProv.SelectedIndex=0;
        //for(int i=0;i<ProviderC.List.Length;i++) {
        //	comboProv.Items.Add(ProviderC.List[i].GetLongDesc());
        //}
        fillGrid();
    }

    //private void listProv_Click(object sender,EventArgs e) {
    //	FillGrid();
    //}
    private void fillGrid() throws Exception {
        //int provNum=0;
        //if(comboProv.SelectedIndex!=0) {
        //	provNum=ProviderC.List[comboProv.SelectedIndex-1].ProvNum;
        //}
        if (IsSelectionMode)
        {
            DList = Deposits.getUnattached();
        }
        else
        {
            DList = Deposits.refresh();
        } 
        grid.beginUpdate();
        grid.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableDepositSlips","Date"),80);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableDepositSlips","Amount"),90);
        grid.getColumns().add(col);
        grid.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < DList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(DList[i].DateDeposit.ToShortDateString());
            row.getCells().Add(DList[i].Amount.ToString("F"));
            grid.getRows().add(row);
        }
        grid.endUpdate();
        grid.scrollToEnd();
    }

    private void grid_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            SelectedDeposit = DList[e.getRow()];
            DialogResult = DialogResult.OK;
            return ;
        }
         
        //not selection mode.
        FormDepositEdit FormD = new FormDepositEdit(DList[e.getRow()]);
        FormD.ShowDialog();
        if (FormD.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillGrid();
    }

    /**
    * Not available in selection mode.
    */
    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        Deposit deposit = new Deposit();
        deposit.DateDeposit = DateTime.Today;
        deposit.BankAccountInfo = PrefC.getString(PrefName.PracticeBankNumber);
        FormDepositEdit FormD = new FormDepositEdit(deposit);
        FormD.IsNew = true;
        FormD.ShowDialog();
        if (FormD.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillGrid();
    }

    /**
    * Only available in selection mode.
    */
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (grid.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a deposit first.");
            return ;
        }
         
        SelectedDeposit = DList[grid.getSelectedIndex()];
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


