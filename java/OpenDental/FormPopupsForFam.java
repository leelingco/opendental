//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormPopupEdit;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EnumPopupLevel;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Popup;
import OpenDentBusiness.Popups;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormPopupsForFam  extends Form 
{

    public Patient PatCur;
    private List<Popup> PopupList = new List<Popup>();
    public FormPopupsForFam() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formPopupsForFam_Load(Object sender, EventArgs e) throws Exception {
        gridMain.setAllowSortingByColumn(true);
        fillGrid();
    }

    private void fillGrid() throws Exception {
        if (checkDeleted.Checked)
        {
            PopupList = Popups.getDeletedForFamily(PatCur);
        }
        else
        {
            PopupList = Popups.getForFamily(PatCur);
        } 
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TablePopupsForFamily","Patient"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePopupsForFamily","Level"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePopupsForFamily","Disabled"), 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        if (checkDeleted.Checked)
        {
            col = new ODGridColumn(Lan.g("TablePopupsForFamily","Deleted"), 60, HorizontalAlignment.Center);
            gridMain.getColumns().add(col);
        }
         
        col = new ODGridColumn(Lan.g("TablePopupsForFamily","Popup Message"),120);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < PopupList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Patients.GetPat(PopupList[i].PatNum).GetNameLF());
            row.getCells().Add(Lan.g("enumEnumPopupLevel", PopupList[i].PopupLevel.ToString()));
            row.getCells().add(PopupList[i].IsDisabled ? "X" : "");
            if (checkDeleted.Checked)
            {
                row.getCells().add(PopupList[i].IsArchived ? "X" : "");
            }
             
            row.getCells().Add(PopupList[i].Description);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormPopupEdit FormPE = new FormPopupEdit();
        FormPE.PopupCur = PopupList[e.getRow()];
        FormPE.ShowDialog();
        if (FormPE.DialogResult == DialogResult.OK)
        {
            fillGrid();
        }
         
    }

    private void checkDeleted_CheckedChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormPopupEdit FormPE = new FormPopupEdit();
        Popup popup = new Popup();
        popup.PatNum = PatCur.PatNum;
        popup.PopupLevel = EnumPopupLevel.Patient;
        popup.setIsNew(true);
        FormPE.PopupCur = popup;
        FormPE.ShowDialog();
        if (FormPE.DialogResult == DialogResult.OK)
        {
            fillGrid();
        }
         
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
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
        this.butClose = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.checkDeleted = new System.Windows.Forms.CheckBox();
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
        this.butClose.Location = new System.Drawing.Point(500, 354);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(24, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(551, 325);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Popups");
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
        this.butAdd.Location = new System.Drawing.Point(24, 354);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 2;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // checkDeleted
        //
        this.checkDeleted.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkDeleted.Location = new System.Drawing.Point(302, 357);
        this.checkDeleted.Name = "checkDeleted";
        this.checkDeleted.Size = new System.Drawing.Size(192, 20);
        this.checkDeleted.TabIndex = 21;
        this.checkDeleted.Text = "Show Deleted";
        this.checkDeleted.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkDeleted.UseVisualStyleBackColor = true;
        this.checkDeleted.CheckedChanged += new System.EventHandler(this.checkDeleted_CheckedChanged);
        //
        // FormPopupsForFam
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(600, 391);
        this.Controls.Add(this.checkDeleted);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Name = "FormPopupsForFam";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Popups for Family";
        this.Load += new System.EventHandler(this.FormPopupsForFam_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.CheckBox checkDeleted = new System.Windows.Forms.CheckBox();
}


