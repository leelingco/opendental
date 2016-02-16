//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormAutoNoteControlEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AutoNoteControl;
import OpenDentBusiness.AutoNoteControls;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormAutoNoteControls;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormAutoNoteControls  extends Form 
{

    /**
    * If OK, then this is the control that the user selected.
    */
    public long SelectedControlNum = new long();
    public FormAutoNoteControls() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.F(this);
    }

    private void formAutoNoteControls_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        AutoNoteControls.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("FormAutoNoteControls","Description"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormAutoNoteControls","Type"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormAutoNoteControls","Prompt Text"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormAutoNoteControls","Options"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < AutoNoteControls.Listt.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(AutoNoteControls.Listt[i].Descript);
            row.getCells().Add(AutoNoteControls.Listt[i].ControlType);
            row.getCells().Add(AutoNoteControls.Listt[i].ControlLabel);
            row.getCells().Add(AutoNoteControls.Listt[i].ControlOptions);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
    }

    //do nothing
    private void butEdit_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        FormAutoNoteControlEdit FormA = new FormAutoNoteControlEdit();
        FormA.ControlCur = AutoNoteControls.Listt[gridMain.getSelectedIndex()];
        FormA.ShowDialog();
        if (FormA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormAutoNoteControlEdit FormA = new FormAutoNoteControlEdit();
        FormA.IsNew = true;
        FormA.ControlCur = new AutoNoteControl();
        FormA.ShowDialog();
        if (FormA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        SelectedControlNum = AutoNoteControls.Listt[gridMain.getSelectedIndex()].AutoNoteControlNum;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAutoNoteControls.class);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butEdit = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(6, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(842, 597);
        this.gridMain.TabIndex = 106;
        this.gridMain.setTitle("Controls");
        this.gridMain.setTranslationName("FormAutoNoteEdit");
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
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(489, 619);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(78, 24);
        this.butAdd.TabIndex = 105;
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
        this.butOK.Location = new System.Drawing.Point(686, 619);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(78, 24);
        this.butOK.TabIndex = 5;
        this.butOK.Text = "OK";
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
        this.butCancel.Location = new System.Drawing.Point(770, 619);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(78, 24);
        this.butCancel.TabIndex = 6;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butEdit
        //
        this.butEdit.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butEdit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butEdit.setAutosize(true);
        this.butEdit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEdit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEdit.setCornerRadius(4F);
        this.butEdit.Image = Resources.geteditPencil();
        this.butEdit.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEdit.Location = new System.Drawing.Point(342, 619);
        this.butEdit.Name = "butEdit";
        this.butEdit.Size = new System.Drawing.Size(78, 24);
        this.butEdit.TabIndex = 107;
        this.butEdit.Text = "Edit";
        this.butEdit.Click += new System.EventHandler(this.butEdit_Click);
        //
        // FormAutoNoteControls
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(867, 656);
        this.Controls.Add(this.butEdit);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormAutoNoteControls";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Auto Note Controls";
        this.Load += new System.EventHandler(this.FormAutoNoteControls_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butEdit;
}


