//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:33 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DateTimeOD;
import OpenDental.FormAccountEdit;
import OpenDental.FormAccounting;
import OpenDental.FormAccountingLock;
import OpenDental.FormAccountingSetup;
import OpenDental.FormJournal;
import OpenDental.FormRpAccountingBalanceSheet;
import OpenDental.FormRpAccountingGenLedg;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;
import OpenDental.UI.ODToolBarButton;
import OpenDental.UI.ODToolBarButtonClickEventArgs;
import OpenDentBusiness.Account;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.AccountType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormAccounting  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.ODToolBar ToolBarMain;
    private OpenDental.UI.ODGrid gridMain;
    private IContainer components = new IContainer();
    private CheckBox checkInactive = new CheckBox();
    private MainMenu mainMenu1 = new MainMenu();
    private MenuItem menuItemSetup = new MenuItem();
    private MenuItem menuItem1 = new MenuItem();
    private MenuItem menuItemGL = new MenuItem();
    private MenuItem menuItemBalSheet = new MenuItem();
    private ImageList imageListMain = new ImageList();
    private OpenDental.UI.Button butRefresh;
    private OpenDental.ValidDate textDate;
    private Label label2 = new Label();
    private OpenDental.UI.Button butToday;
    private MenuItem menuItemLock = new MenuItem();
    //private Account[] AccountList;
    private DataTable table = new DataTable();
    /**
    * 
    */
    public FormAccounting() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAccounting.class);
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.checkInactive = new System.Windows.Forms.CheckBox();
        this.mainMenu1 = new System.Windows.Forms.MainMenu(this.components);
        this.menuItemSetup = new System.Windows.Forms.MenuItem();
        this.menuItemLock = new System.Windows.Forms.MenuItem();
        this.menuItem1 = new System.Windows.Forms.MenuItem();
        this.menuItemGL = new System.Windows.Forms.MenuItem();
        this.menuItemBalSheet = new System.Windows.Forms.MenuItem();
        this.label2 = new System.Windows.Forms.Label();
        this.butToday = new OpenDental.UI.Button();
        this.butRefresh = new OpenDental.UI.Button();
        this.textDate = new OpenDental.ValidDate();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
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
        this.checkInactive.Location = new System.Drawing.Point(313, 34);
        this.checkInactive.Name = "checkInactive";
        this.checkInactive.Size = new System.Drawing.Size(150, 17);
        this.checkInactive.TabIndex = 2;
        this.checkInactive.Text = "Include Inactive Accounts";
        this.checkInactive.UseVisualStyleBackColor = true;
        this.checkInactive.Click += new System.EventHandler(this.checkInactive_Click);
        //
        // mainMenu1
        //
        this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemSetup, this.menuItemLock, this.menuItem1 });
        //
        // menuItemSetup
        //
        this.menuItemSetup.Index = 0;
        this.menuItemSetup.Text = "Setup";
        this.menuItemSetup.Click += new System.EventHandler(this.menuItemSetup_Click);
        //
        // menuItemLock
        //
        this.menuItemLock.Index = 1;
        this.menuItemLock.Text = "Lock";
        this.menuItemLock.Click += new System.EventHandler(this.menuItemLock_Click);
        //
        // menuItem1
        //
        this.menuItem1.Index = 2;
        this.menuItem1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemGL, this.menuItemBalSheet });
        this.menuItem1.Text = "Reports";
        //
        // menuItemGL
        //
        this.menuItemGL.Index = 0;
        this.menuItemGL.Text = "General Ledger Detail";
        this.menuItemGL.Click += new System.EventHandler(this.menuItemGL_Click);
        //
        // menuItemBalSheet
        //
        this.menuItemBalSheet.Index = 1;
        this.menuItemBalSheet.Text = "Balance Sheet";
        this.menuItemBalSheet.Click += new System.EventHandler(this.menuItemBalSheet_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(3, 33);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(72, 18);
        this.label2.TabIndex = 7;
        this.label2.Text = "As of Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // butToday
        //
        this.butToday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToday.setAutosize(true);
        this.butToday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToday.setCornerRadius(4F);
        this.butToday.Location = new System.Drawing.Point(238, 32);
        this.butToday.Name = "butToday";
        this.butToday.Size = new System.Drawing.Size(70, 23);
        this.butToday.TabIndex = 10;
        this.butToday.Text = "Today";
        this.butToday.UseVisualStyleBackColor = true;
        this.butToday.Click += new System.EventHandler(this.butToday_Click);
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(163, 32);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(70, 23);
        this.butRefresh.TabIndex = 9;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.UseVisualStyleBackColor = true;
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(76, 34);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(81, 20);
        this.textDate.TabIndex = 8;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(0, 57);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(492, 450);
        this.gridMain.TabIndex = 1;
        this.gridMain.setTitle("Chart of Accounts");
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
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(492, 25);
        this.ToolBarMain.TabIndex = 0;
        this.ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormAccounting
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(492, 507);
        this.Controls.Add(this.butToday);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.checkInactive);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.ToolBarMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.Menu = this.mainMenu1;
        this.MinimizeBox = false;
        this.Name = "FormAccounting";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Accounting";
        this.Load += new System.EventHandler(this.FormAccounting_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formAccounting_Load(Object sender, EventArgs e) throws Exception {
        layoutToolBar();
        textDate.Text = DateTime.Today.ToShortDateString();
        fillGrid();
    }

    /**
    * Causes the toolbar to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        ToolBarMain.getButtons().add(new ODToolBarButton(Lan.g(this,"Add"), 0, "", "Add"));
        //ToolBarMain.Buttons.Add(new ODToolBarButton(ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new ODToolBarButton(Lan.g(this,"Edit"), 1, Lan.g(this,"Edit Selected Account"), "Edit"));
        /*ODToolBarButton button=new ODToolBarButton("",-1,"","PageNum");
        			button.Style=ODToolBarButtonStyle.Label;
        			ToolBarMain.Buttons.Add(button);
        			ToolBarMain.Buttons.Add(new ODToolBarButton("",2,"Go Forward One Page","Fwd"));
        			ToolBarMain.Buttons.Add(new ODToolBarButton(ODToolBarButtonStyle.Separator));*/
        ToolBarMain.getButtons().add(new ODToolBarButton(Lan.g(this,"Close"), -1, "Close This Window", "Close"));
    }

    private void menuItemSetup_Click(Object sender, EventArgs e) throws Exception {
        FormAccountingSetup FormA = new FormAccountingSetup();
        FormA.ShowDialog();
    }

    private void menuItemLock_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.SecurityAdmin))
        {
            return ;
        }
         
        FormAccountingLock FormA = new FormAccountingLock();
        FormA.ShowDialog();
        if (FormA.DialogResult == DialogResult.OK)
        {
            SecurityLogs.MakeLogEntry(Permissions.SecurityAdmin, 0, "Accounting Lock Changed");
        }
         
    }

    private void menuItemGL_Click(Object sender, EventArgs e) throws Exception {
        FormRpAccountingGenLedg FormA = new FormRpAccountingGenLedg();
        FormA.ShowDialog();
    }

    private void menuItemBalSheet_Click(Object sender, EventArgs e) throws Exception {
        FormRpAccountingBalanceSheet FormA = new FormRpAccountingBalanceSheet();
        FormA.ShowDialog();
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
        if (__dummyScrutVar0.equals("Add"))
        {
            add_Click();
        }
        else if (__dummyScrutVar0.equals("Edit"))
        {
            edit_Click();
        }
        else if (__dummyScrutVar0.equals("Close"))
        {
            Close();
        }
           
    }

    /*	case "Fwd":
    					OnFwd_Click();
    					break;
    				
    			}*/
    private void fillGrid() throws Exception {
        Accounts.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableChartOfAccounts","Type"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableChartOfAccounts","Description"),170);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableChartOfAccounts","Balance"), 80, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableChartOfAccounts","Bank Number"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableChartOfAccounts","Inactive"), 70, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ODGridRow row;
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            //error
            table = Accounts.GetFullList(DateTimeOD.getToday(), checkInactive.Checked);
        }
        else
        {
            table = Accounts.GetFullList(PIn.Date(textDate.Text), checkInactive.Checked);
        } 
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(table.Rows[i]["type"].ToString());
            row.getCells().Add(table.Rows[i]["Description"].ToString());
            row.getCells().Add(table.Rows[i]["balance"].ToString());
            row.getCells().Add(table.Rows[i]["BankNumber"].ToString());
            row.getCells().Add(table.Rows[i]["inactive"].ToString());
            //if not the last row
            if (i < table.Rows.Count - 1 && table.Rows[i]["type"].ToString() != table.Rows[i + 1]["type"].ToString())
            {
                row.setColorLborder(Color.Black);
            }
             
            row.setColorBackG(Color.FromArgb(PIn.Int(table.Rows[i]["color"].ToString())));
            gridMain.getRows().add(row);
        }
        /*for(int i=0;i<Accounts.ListLong.Length;i++){
        				if(!checkInactive.Checked && Accounts.ListLong[i].Inactive){
        					continue;
        				}
        				row=new ODGridRow();
        				row.Cells.Add(Lan.g("enumAccountType",Accounts.ListLong[i].AcctType.ToString()));
        				row.Cells.Add(Accounts.ListLong[i].Description);
        				if(Accounts.ListLong[i].AcctType==AccountType.Asset){
        					row.Cells.Add(Accounts.GetBalance(Accounts.ListLong[i].AccountNum,Accounts.ListLong[i].AcctType).ToString("n"));
        				}
        				else{
        					row.Cells.Add("");
        				}
        				row.Cells.Add(Accounts.ListLong[i].BankNumber);
        				if(Accounts.ListLong[i].Inactive){
        					row.Cells.Add("X");
        				}
        				else{
        					row.Cells.Add("");
        				}
        				if(i<Accounts.ListLong.Length-1//if not the last row
        					&& Accounts.ListLong[i].AcctType != Accounts.ListLong[i+1].AcctType){
        					row.ColorLborder=Color.Black;
        				}
        				row.Tag=Accounts.ListLong[i].Clone();
        				row.ColorBackG=Accounts.ListLong[i].AccountColor;
        				gridMain.Rows.Add(row);
        			}*/
        gridMain.endUpdate();
    }

    private void add_Click() throws Exception {
        Account acct = new Account();
        acct.AcctType = AccountType.Asset;
        acct.AccountColor = Color.White;
        FormAccountEdit FormA = new FormAccountEdit(acct);
        FormA.IsNew = true;
        FormA.ShowDialog();
        fillGrid();
    }

    private void edit_Click() throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please pick an account first.");
            return ;
        }
         
        long acctNum = PIn.Long(table.Rows[gridMain.getSelectedIndex()]["AccountNum"].ToString());
        if (acctNum == 0)
        {
            MsgBox.show(this,"This account is generated automatically, and cannot be edited.");
            return ;
        }
         
        Account acct = Accounts.getAccount(acctNum);
        FormAccountEdit FormA = new FormAccountEdit(acct);
        FormA.ShowDialog();
        fillGrid();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (table.Rows[i]["AccountNum"].ToString() == acctNum.ToString())
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void gridMain_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        long acctNum = PIn.Long(table.Rows[gridMain.getSelectedIndex()]["AccountNum"].ToString());
        if (acctNum == 0)
        {
            MsgBox.show(this,"This account is generated automatically, and there is currently no way to view the detail.  It is the sum of all income minus all expenses for all previous years.");
            return ;
        }
         
        DateTime asofDate = new DateTime();
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            //error
            asofDate = DateTime.Today;
        }
        else
        {
            asofDate = PIn.Date(textDate.Text);
        } 
        Account acct = Accounts.getAccount(acctNum);
        FormJournal FormJ = new FormJournal(acct);
        FormJ.InitialAsOfDate = asofDate;
        FormJ.ShowDialog();
        fillGrid();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (table.Rows[i]["AccountNum"].ToString() == acctNum.ToString())
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void checkInactive_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butToday_Click(Object sender, EventArgs e) throws Exception {
        textDate.Text = DateTime.Today.ToShortDateString();
        fillGrid();
    }

}


