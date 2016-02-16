//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;

public class FormProvidersMultiPick  extends Form 
{

    public List<Provider> SelectedProviders = new List<Provider>();
    public FormProvidersMultiPick() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formProvidersMultiPick_Load(Object sender, EventArgs e) throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableProviders","Abbrev"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProviders","Last Name"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProviders","First Name"),90);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ProviderC.getListShort()[i].Abbr);
            row.getCells().Add(ProviderC.getListShort()[i].LName);
            row.getCells().Add(ProviderC.getListShort()[i].FName);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (SelectedProviders.Contains(ProviderC.getListShort()[i]))
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void butProvDentist_Click(Object sender, EventArgs e) throws Exception {
        SelectedProviders = new List<Provider>();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (!ProviderC.getListShort()[i].IsSecondary)
            {
                SelectedProviders.Add(ProviderC.getListShort()[i]);
                gridMain.setSelected(i,true);
            }
            else
            {
                gridMain.setSelected(i,false);
            } 
        }
    }

    private void butProvHygenist_Click(Object sender, EventArgs e) throws Exception {
        SelectedProviders = new List<Provider>();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (ProviderC.getListShort()[i].IsSecondary)
            {
                SelectedProviders.Add(ProviderC.getListShort()[i]);
                gridMain.setSelected(i,true);
            }
            else
            {
                gridMain.setSelected(i,false);
            } 
        }
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        SelectedProviders = new List<Provider>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            SelectedProviders.Add(ProviderC.getListShort()[gridMain.getSelectedIndices()[i]]);
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butProvHygenist = new OpenDental.UI.Button();
        this.butProvDentist = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(410, 589);
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
        this.butCancel.Location = new System.Drawing.Point(410, 630);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(362, 642);
        this.gridMain.TabIndex = 14;
        this.gridMain.setTitle("Providers");
        this.gridMain.setTranslationName(null);
        //
        // butProvHygenist
        //
        this.butProvHygenist.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProvHygenist.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butProvHygenist.setAutosize(true);
        this.butProvHygenist.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProvHygenist.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProvHygenist.setCornerRadius(4F);
        this.butProvHygenist.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butProvHygenist.Location = new System.Drawing.Point(410, 149);
        this.butProvHygenist.Name = "butProvHygenist";
        this.butProvHygenist.Size = new System.Drawing.Size(75, 24);
        this.butProvHygenist.TabIndex = 90;
        this.butProvHygenist.Text = "Hygienists";
        this.butProvHygenist.Click += new System.EventHandler(this.butProvHygenist_Click);
        //
        // butProvDentist
        //
        this.butProvDentist.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProvDentist.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butProvDentist.setAutosize(true);
        this.butProvDentist.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProvDentist.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProvDentist.setCornerRadius(4F);
        this.butProvDentist.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butProvDentist.Location = new System.Drawing.Point(410, 108);
        this.butProvDentist.Name = "butProvDentist";
        this.butProvDentist.Size = new System.Drawing.Size(75, 24);
        this.butProvDentist.TabIndex = 89;
        this.butProvDentist.Text = "Dentists";
        this.butProvDentist.Click += new System.EventHandler(this.butProvDentist_Click);
        //
        // FormProvidersMultiPick
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(514, 670);
        this.Controls.Add(this.butProvHygenist);
        this.Controls.Add(this.butProvDentist);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.KeyPreview = true;
        this.Name = "FormProvidersMultiPick";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Providers";
        this.Load += new System.EventHandler(this.FormProvidersMultiPick_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butProvHygenist;
    private OpenDental.UI.Button butProvDentist;
}


