//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:29 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormPharmacies;
import OpenDental.FormPharmacyEdit;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Pharmacies;
import OpenDentBusiness.Pharmacy;
import OpenDentBusiness.PharmacyC;

/**
* Summary description for FormBasicTemplate.
*/
public class FormPharmacies  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butNone;
    private OpenDental.UI.Button butOK;
    private boolean changed = new boolean();
    public boolean IsSelectionMode = new boolean();
    /**
    * Only used if IsSelectionMode.  On OK, contains selected pharmacyNum.  Can be 0.  Can also be set ahead of time externally.
    */
    public long SelectedPharmacyNum = new long();
    /**
    * 
    */
    public FormPharmacies() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPharmacies.class);
        this.butNone = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butNone
        //
        this.butNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNone.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butNone.setAutosize(true);
        this.butNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNone.setCornerRadius(4F);
        this.butNone.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butNone.Location = new System.Drawing.Point(478, 590);
        this.butNone.Name = "butNone";
        this.butNone.Size = new System.Drawing.Size(68, 24);
        this.butNone.TabIndex = 16;
        this.butNone.Text = "None";
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
        this.butOK.Location = new System.Drawing.Point(626, 590);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 15;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(17, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(765, 565);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("Pharmacies");
        this.gridMain.setTranslationName("TablePharmacies");
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
        this.butAdd.Location = new System.Drawing.Point(17, 590);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 24);
        this.butAdd.TabIndex = 10;
        this.butAdd.Text = "&Add";
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
        this.butClose.Location = new System.Drawing.Point(707, 590);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormPharmacies
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(810, 630);
        this.Controls.Add(this.butNone);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPharmacies";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Pharmacies";
        this.Load += new System.EventHandler(this.FormPharmacies_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormPharmacies_FormClosing);
        this.ResumeLayout(false);
    }

    private void formPharmacies_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            butClose.Text = Lan.g(this,"Cancel");
        }
        else
        {
            butOK.Visible = false;
            butNone.Visible = false;
        } 
        fillGrid();
        if (SelectedPharmacyNum != 0)
        {
            for (int i = 0;i < PharmacyC.getListt().Count;i++)
            {
                if (PharmacyC.getListt()[i].PharmacyNum == SelectedPharmacyNum)
                {
                    gridMain.setSelected(i,true);
                    break;
                }
                 
            }
        }
         
    }

    private void fillGrid() throws Exception {
        Pharmacies.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TablePharmacies","Store Name"),130);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePharmacies","Phone"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePharmacies","Fax"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePharmacies","Address"),120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePharmacies","City"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePharmacies","Note"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        String txt = new String();
        for (int i = 0;i < PharmacyC.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(PharmacyC.getListt()[i].StoreName);
            row.getCells().Add(PharmacyC.getListt()[i].Phone);
            row.getCells().Add(PharmacyC.getListt()[i].Fax);
            txt = PharmacyC.getListt()[i].Address;
            if (!StringSupport.equals(PharmacyC.getListt()[i].Address2, ""))
            {
                txt += "\r\n" + PharmacyC.getListt()[i].Address2;
            }
             
            row.getCells().add(txt);
            row.getCells().Add(PharmacyC.getListt()[i].City);
            row.getCells().Add(PharmacyC.getListt()[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormPharmacyEdit FormPE = new FormPharmacyEdit();
        FormPE.PharmCur = new Pharmacy();
        FormPE.PharmCur.setIsNew(true);
        FormPE.ShowDialog();
        fillGrid();
        changed = true;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            SelectedPharmacyNum = PharmacyC.getListt()[e.getRow()].PharmacyNum;
            DialogResult = DialogResult.OK;
            return ;
        }
        else
        {
            FormPharmacyEdit FormP = new FormPharmacyEdit();
            FormP.PharmCur = PharmacyC.getListt()[e.getRow()];
            FormP.ShowDialog();
            fillGrid();
            changed = true;
        } 
    }

    private void butNone_Click(Object sender, EventArgs e) throws Exception {
        //not even visible unless is selection mode
        SelectedPharmacyNum = 0;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //not even visible unless is selection mode
        if (gridMain.getSelectedIndex() == -1)
        {
            //	MsgBox.Show(this,"Please select an item first.");
            //	return;
            SelectedPharmacyNum = 0;
        }
        else
        {
            SelectedPharmacyNum = PharmacyC.getListt()[gridMain.getSelectedIndex()].PharmacyNum;
        } 
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formPharmacies_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Pharmacies);
        }
         
    }

}


