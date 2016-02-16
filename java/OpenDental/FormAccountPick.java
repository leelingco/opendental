//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:34 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.Bridges.QuickBooks;
import OpenDental.FormAccountPick;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;
import OpenDentBusiness.Account;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.AccountType;

/**
* Summary description for FormBasicTemplate.
*/
public class FormAccountPick  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.ODGrid gridMain;
    private IContainer components = new IContainer();
    private CheckBox checkInactive = new CheckBox();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private ImageList imageListMain = new ImageList();
    /**
    * Upon closing with OK, this will be the selected account.
    */
    public Account SelectedAccount;
    public boolean IsQuickBooks = new boolean();
    public List<String> SelectedAccountsQB = new List<String>();
    /**
    * 
    */
    public FormAccountPick() throws Exception {
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAccountPick.class);
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.checkInactive = new System.Windows.Forms.CheckBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "Add.gif");
        this.imageListMain.Images.SetKeyName(1, "editPencil.gif");
        //
        // checkInactive
        //
        this.checkInactive.AutoSize = true;
        this.checkInactive.Location = new System.Drawing.Point(20, 525);
        this.checkInactive.Name = "checkInactive";
        this.checkInactive.Size = new System.Drawing.Size(150, 17);
        this.checkInactive.TabIndex = 2;
        this.checkInactive.Text = "Include Inactive Accounts";
        this.checkInactive.UseVisualStyleBackColor = true;
        this.checkInactive.Click += new System.EventHandler(this.checkInactive_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(8, 14);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(475, 505);
        this.gridMain.TabIndex = 1;
        this.gridMain.setTitle("Accounts");
        this.gridMain.setTranslationName("TableChartOfAccounts");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(408, 546);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 11;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(315, 546);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 10;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormAccountPick
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(492, 584);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.checkInactive);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAccountPick";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Pick Account";
        this.Load += new System.EventHandler(this.FormAccountPick_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formAccountPick_Load(Object sender, EventArgs e) throws Exception {
        if (IsQuickBooks)
        {
            SelectedAccountsQB = new List<String>();
            checkInactive.Visible = false;
            fillGridQB();
            gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        }
        else
        {
            fillGrid();
        } 
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableChartOfAccounts","Type"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableChartOfAccounts","Description"),170);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableChartOfAccounts","Balance"), 65, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableChartOfAccounts","Bank Number"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableChartOfAccounts","Inactive"),70);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < Accounts.getListLong().Length;i++)
        {
            if (!checkInactive.Checked && Accounts.getListLong()[i].Inactive)
            {
                continue;
            }
             
            row = new ODGridRow();
            row.getCells().Add(Lan.g("enumAccountType", Accounts.getListLong()[i].AcctType.ToString()));
            row.getCells().Add(Accounts.getListLong()[i].Description);
            if (Accounts.getListLong()[i].AcctType == AccountType.Asset)
            {
                row.getCells().Add(Accounts.GetBalance(Accounts.getListLong()[i].AccountNum, Accounts.getListLong()[i].AcctType).ToString("n"));
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(Accounts.getListLong()[i].BankNumber);
            if (Accounts.getListLong()[i].Inactive)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            //if not the last row
            if (i < Accounts.getListLong().Length - 1 && Accounts.getListLong()[i].AcctType != Accounts.getListLong()[i + 1].AcctType)
            {
                row.setColorLborder(Color.Black);
            }
             
            row.setTag(Accounts.getListLong()[i].Clone());
            row.setColorBackG(Accounts.getListLong()[i].AccountColor);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void fillGridQB() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableChartOfAccountsQB","Description"),200);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ODGridRow row;
        //Get the list of accounts from QuickBooks.
        Cursor.Current = Cursors.WaitCursor;
        List<String> accountList = new List<String>();
        try
        {
            accountList = QuickBooks.getListOfAccounts();
        }
        catch (Exception e)
        {
            MessageBox.Show(e.Message);
        }

        Cursor.Current = Cursors.Default;
        for (int i = 0;i < accountList.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(accountList[i]);
            row.setTag(accountList[i]);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        if (IsQuickBooks)
        {
            SelectedAccountsQB.Add((String)gridMain.getRows().get___idx(e.getRow()).getTag());
        }
        else
        {
            SelectedAccount = ((Account)gridMain.getRows().get___idx(e.getRow()).getTag()).clone();
        } 
        DialogResult = DialogResult.OK;
    }

    private void checkInactive_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an account first.");
            return ;
        }
         
        if (IsQuickBooks)
        {
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                SelectedAccountsQB.Add((String)(gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag));
            }
        }
        else
        {
            SelectedAccount = ((Account)gridMain.getRows().get___idx(gridMain.getSelectedIndex()).getTag()).clone();
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


