//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental.Bridges;

import OpenDental.Bridges.TrophyFolder;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormTrophyNamePick  extends Form 
{

    public List<TrophyFolder> ListMatches = new List<TrophyFolder>();
    /**
    * If dialogResult is OK, then this will contain the picked folder name.  If blank, then the calling class will need to generate a new folder name.
    */
    public String PickedName = new String();
    public FormTrophyNamePick() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formTrophyNamePick_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("FormTrophyNamePick","FolderName"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormTrophyNamePick","Last Name"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormTrophyNamePick","First Name"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormTrophyNamePick","Birthdate"),80);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < ListMatches.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(ListMatches[i].FolderName);
            row.getCells().Add(ListMatches[i].LName);
            row.getCells().Add(ListMatches[i].FName);
            row.getCells().Add(ListMatches[i].BirthDate.ToShortDateString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butNew_Click(Object sender, EventArgs e) throws Exception {
        PickedName = "";
        DialogResult = DialogResult.OK;
    }

    private void gridMain_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        PickedName = ListMatches[e.getRow()].FolderName;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an item from the list first.");
            return ;
        }
         
        PickedName = ListMatches[gridMain.getSelectedIndex()].FolderName;
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
        this.label1 = new System.Windows.Forms.Label();
        this.butNew = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(340, 36);
        this.label1.TabIndex = 4;
        this.label1.Text = "The following unpaired folders were found.  Please pick one from the list to assi" + "gn to this patient, or click New Patient at the bottom.";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butNew
        //
        this.butNew.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNew.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butNew.setAutosize(true);
        this.butNew.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNew.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNew.setCornerRadius(4F);
        this.butNew.Location = new System.Drawing.Point(497, 399);
        this.butNew.Name = "butNew";
        this.butNew.Size = new System.Drawing.Size(84, 24);
        this.butNew.TabIndex = 141;
        this.butNew.Text = "New Patient";
        this.butNew.Click += new System.EventHandler(this.butNew_Click);
        //
        // gridMain
        //
        this.gridMain.setAllowSelection(false);
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(15, 58);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(439, 449);
        this.gridMain.TabIndex = 140;
        this.gridMain.setTitle("Unpaired Folders");
        this.gridMain.setTranslationName("FormTrophyNamePick");
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
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(497, 442);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(84, 24);
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
        this.butCancel.Location = new System.Drawing.Point(497, 483);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(84, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormTrophyNamePick
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(606, 534);
        this.Controls.Add(this.butNew);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormTrophyNamePick";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Folder";
        this.Load += new System.EventHandler(this.FormTrophyNamePick_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butNew;
}


