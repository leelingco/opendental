//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:45 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormScreenGroupEdit;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.ScreenGroup;
import OpenDental.ScreenGroups;
import OpenDental.Screens;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;

/**
* 
*/
public class FormScreenGroups  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.TextBox textDateFrom = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butRefresh;
    private System.Windows.Forms.TextBox textDateTo = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butAdd;
    private IContainer components = new IContainer();
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private MainMenu mainMenu = new MainMenu();
    private List<ScreenGroup> ScreenGroupList = new List<ScreenGroup>();
    private OpenDental.UI.Button butLeft;
    private OpenDental.UI.Button butRight;
    private OpenDental.UI.Button butToday;
    private Label label1 = new Label();
    public ScreenGroup ScreenGroupCur = new ScreenGroup();
    private DateTime dateCur = new DateTime();
    /**
    * 
    */
    public FormScreenGroups() throws Exception {
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
        this.textDateFrom = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textDateTo = new System.Windows.Forms.TextBox();
        this.butRefresh = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.mainMenu = new System.Windows.Forms.MainMenu(this.components);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butLeft = new OpenDental.UI.Button();
        this.butRight = new OpenDental.UI.Button();
        this.butToday = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(150, 52);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(69, 20);
        this.textDateFrom.TabIndex = 74;
        this.textDateFrom.Validating += new System.ComponentModel.CancelEventHandler(this.textDateFrom_Validating);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(218, 56);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(25, 13);
        this.label2.TabIndex = 77;
        this.label2.Text = "To";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(243, 52);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(75, 20);
        this.textDateTo.TabIndex = 76;
        this.textDateTo.Validating += new System.ComponentModel.CancelEventHandler(this.textDateTo_Validating);
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(326, 51);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(55, 21);
        this.butRefresh.TabIndex = 78;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(13, 502);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(70, 24);
        this.butAdd.TabIndex = 79;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butClose.Location = new System.Drawing.Point(441, 502);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(70, 24);
        this.butClose.TabIndex = 79;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(13, 82);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(499, 402);
        this.gridMain.TabIndex = 80;
        this.gridMain.setTitle("Screening Groups");
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
        // butLeft
        //
        this.butLeft.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLeft.setAutosize(true);
        this.butLeft.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLeft.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLeft.setCornerRadius(4F);
        this.butLeft.Image = Resources.getLeft();
        this.butLeft.Location = new System.Drawing.Point(167, 13);
        this.butLeft.Name = "butLeft";
        this.butLeft.Size = new System.Drawing.Size(39, 24);
        this.butLeft.TabIndex = 78;
        this.butLeft.Click += new System.EventHandler(this.butLeft_Click);
        //
        // butRight
        //
        this.butRight.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRight.setAutosize(true);
        this.butRight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRight.setCornerRadius(4F);
        this.butRight.Image = Resources.getRight();
        this.butRight.Location = new System.Drawing.Point(307, 13);
        this.butRight.Name = "butRight";
        this.butRight.Size = new System.Drawing.Size(39, 24);
        this.butRight.TabIndex = 78;
        this.butRight.Click += new System.EventHandler(this.butRight_Click);
        //
        // butToday
        //
        this.butToday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToday.setAutosize(true);
        this.butToday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToday.setCornerRadius(4F);
        this.butToday.Location = new System.Drawing.Point(215, 13);
        this.butToday.Name = "butToday";
        this.butToday.Size = new System.Drawing.Size(83, 24);
        this.butToday.TabIndex = 78;
        this.butToday.Text = "Today";
        this.butToday.Click += new System.EventHandler(this.butToday_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(92, 56);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(57, 13);
        this.label1.TabIndex = 77;
        this.label1.Text = "From";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormScreenGroups
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(524, 541);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.textDateFrom);
        this.Controls.Add(this.textDateTo);
        this.Controls.Add(this.butRight);
        this.Controls.Add(this.butLeft);
        this.Controls.Add(this.butToday);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label2);
        this.Menu = this.mainMenu;
        this.Name = "FormScreenGroups";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "Screening Groups";
        this.Load += new System.EventHandler(this.FormScreenings_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formScreenings_Load(Object sender, System.EventArgs e) throws Exception {
        dateCur = DateTime.Today;
        textDateFrom.Text = DateTime.Today.ToShortDateString();
        textDateTo.Text = DateTime.Today.ToShortDateString();
        fillGrid();
    }

    private void fillGrid() throws Exception {
        ScreenGroupList = ScreenGroups.Refresh(PIn.Date(textDateFrom.Text), PIn.Date(textDateTo.Text));
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g(this,"Date"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Description"),140);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        ListViewItem[] items = new ListViewItem[ScreenGroupList.Count];
        for (int i = 0;i < items.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ScreenGroupList[i].SGDate.ToShortDateString());
            row.getCells().Add(ScreenGroupList[i].Description);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormScreenGroupEdit FormSG = new FormScreenGroupEdit();
        FormSG.ScreenGroupCur = ScreenGroupList[gridMain.getSelectedIndex()];
        FormSG.ShowDialog();
        fillGrid();
    }

    private void textDateFrom_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (StringSupport.equals(textDateFrom.Text, ""))
            return ;
         
        try
        {
            DateTime.Parse(textDateFrom.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show("Date invalid");
            e.Cancel = true;
        }
    
    }

    private void textDateTo_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (StringSupport.equals(textDateTo.Text, ""))
            return ;
         
        try
        {
            DateTime.Parse(textDateTo.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show("Date invalid");
            e.Cancel = true;
        }
    
    }

    private void butRefresh_Click(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    /*
    		private void menuItemSetup_Click(object sender,EventArgs e) {
    			FormScreenSetup FormSS=new FormScreenSetup();
    			FormSS.ShowDialog();
    		}
    		*/
    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormScreenGroupEdit FormSG = new FormScreenGroupEdit();
        FormSG.IsNew = true;
        //ScreenGroups.Cur=new ScreenGroup();
        if (ScreenGroupList.Count == 0)
        {
            FormSG.ScreenGroupCur = new ScreenGroup();
        }
        else
        {
            FormSG.ScreenGroupCur = ScreenGroupList[ScreenGroupList.Count - 1];
        } 
        //'remembers' the last entry
        FormSG.ScreenGroupCur.SGDate = DateTime.Today;
        //except date will be today
        FormSG.ShowDialog();
        //if(FormSG.DialogResult!=DialogResult.OK){
        //	return;
        //}
        fillGrid();
    }

    private void butToday_Click(Object sender, EventArgs e) throws Exception {
        dateCur = DateTime.Today;
        textDateFrom.Text = DateTime.Today.ToShortDateString();
        textDateTo.Text = DateTime.Today.ToShortDateString();
    }

    private void butLeft_Click(Object sender, EventArgs e) throws Exception {
        dateCur = dateCur.AddDays(-1);
        textDateFrom.Text = dateCur.ToShortDateString();
        textDateTo.Text = dateCur.ToShortDateString();
    }

    private void butRight_Click(Object sender, EventArgs e) throws Exception {
        dateCur = dateCur.AddDays(1);
        textDateFrom.Text = dateCur.ToShortDateString();
        textDateTo.Text = dateCur.ToShortDateString();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length != 1)
        {
            MessageBox.Show("Please select one item first.");
            return ;
        }
         
        ScreenGroupCur = ScreenGroupList[gridMain.getSelectedIndex()];
        OpenDentBusiness.Screen[] screenList = Screens.Refresh(ScreenGroupCur.ScreenGroupNum);
        if (screenList.Length > 0)
        {
            MessageBox.Show("Not allowed to delete a screening group with items in it.");
            return ;
        }
         
        ScreenGroups.Delete(ScreenGroupCur);
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


