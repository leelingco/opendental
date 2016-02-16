//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Hcpcs;
import OpenDentBusiness.Hcpcses;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormHcpcs;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormHcpcs  extends Form 
{

    public boolean IsSelectionMode = new boolean();
    public Hcpcs SelectedHcpcs;
    private List<Hcpcs> listHcpcses = new List<Hcpcs>();
    private boolean changed = new boolean();
    public FormHcpcs() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formHcpcs_Load(Object sender, EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            butClose.Text = Lan.g(this,"Cancel");
        }
        else
        {
            butOK.Visible = false;
        } 
        ActiveControl = textCode;
    }

    private void butSearch_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("HCPCS Code",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Description",500);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        listHcpcses = Hcpcses.GetBySearchText(textCode.Text);
        for (int i = 0;i < listHcpcses.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listHcpcses[i].HcpcsCode);
            row.getCells().Add(listHcpcses[i].DescriptionShort);
            row.setTag(listHcpcses[i]);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            SelectedHcpcs = (Hcpcs)gridMain.getRows().get___idx(e.getRow()).getTag();
            DialogResult = DialogResult.OK;
            return ;
        }
         
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //not even visible unless IsSelectionMode
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        SelectedHcpcs = (Hcpcs)gridMain.getRows().get___idx(gridMain.getSelectedIndex()).getTag();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormHcpcs.class);
        this.textCode = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butSearch = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textCode
        //
        this.textCode.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textCode.Location = new System.Drawing.Point(246, 10);
        this.textCode.Name = "textCode";
        this.textCode.Size = new System.Drawing.Size(399, 20);
        this.textCode.TabIndex = 17;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(73, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(172, 16);
        this.label1.TabIndex = 18;
        this.label1.Text = "Code(s) or Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(20, 38);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(851, 641);
        this.gridMain.TabIndex = 20;
        this.gridMain.setTitle("HCPCS Codes");
        this.gridMain.setTranslationName("FormHcpcsCodes");
        this.gridMain.setWrapText(false);
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
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.Location = new System.Drawing.Point(651, 7);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 24);
        this.butSearch.TabIndex = 19;
        this.butSearch.Text = "Search";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(877, 625);
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
        this.butClose.Location = new System.Drawing.Point(877, 655);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormHcpcs
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(961, 691);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butSearch);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textCode);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormHcpcs";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "HCPCS";
        this.Load += new System.EventHandler(this.FormHcpcs_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.TextBox textCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSearch;
    private OpenDental.UI.ODGrid gridMain;
}


