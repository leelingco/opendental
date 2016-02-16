//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEhrQuarterlyKeyEdit;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrQuarterlyKey;
import OpenDentBusiness.EhrQuarterlyKeys;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEhrQuarterlyKeys;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrQuarterlyKeys  extends Form 
{

    private List<EhrQuarterlyKey> listKeys = new List<EhrQuarterlyKey>();
    public FormEhrQuarterlyKeys() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEhrQuarterlyKeys_Load(Object sender, EventArgs e) throws Exception {
        textPracticeTitle.Text = PrefC.getString(PrefName.PracticeTitle);
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Year",50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Quarter",50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Key",100);
        gridMain.getColumns().add(col);
        listKeys = EhrQuarterlyKeys.Refresh(0);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listKeys.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listKeys[i].YearValue.ToString());
            row.getCells().Add(listKeys[i].QuarterValue.ToString());
            row.getCells().Add(listKeys[i].KeyValue);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrQuarterlyKeyEdit formE = new FormEhrQuarterlyKeyEdit();
        EhrQuarterlyKey keycur = listKeys[e.getRow()];
        keycur.setIsNew(false);
        formE.KeyCur = keycur;
        formE.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormEhrQuarterlyKeyEdit formE = new FormEhrQuarterlyKeyEdit();
        EhrQuarterlyKey keycur = new EhrQuarterlyKey();
        keycur.setIsNew(true);
        formE.KeyCur = keycur;
        formE.ShowDialog();
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrQuarterlyKeys.class);
        this.butClose = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textPracticeTitle = new System.Windows.Forms.TextBox();
        this.butAdd = new OpenDental.UI.Button();
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
        this.butClose.Location = new System.Drawing.Point(397, 417);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(41, 144);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(311, 297);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Keys");
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
        // label1
        //
        this.label1.Location = new System.Drawing.Point(38, 19);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(415, 72);
        this.label1.TabIndex = 5;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(38, 108);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(83, 17);
        this.label2.TabIndex = 6;
        this.label2.Text = "Practice Title";
        //
        // textPracticeTitle
        //
        this.textPracticeTitle.Location = new System.Drawing.Point(115, 105);
        this.textPracticeTitle.Name = "textPracticeTitle";
        this.textPracticeTitle.ReadOnly = true;
        this.textPracticeTitle.Size = new System.Drawing.Size(338, 20);
        this.textPracticeTitle.TabIndex = 7;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(378, 144);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 8;
        this.butAdd.Text = "Add";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormEhrQuarterlyKeys
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(497, 468);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.textPracticeTitle);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Name = "FormEhrQuarterlyKeys";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Ehr Quarterly Keys";
        this.Load += new System.EventHandler(this.FormEhrQuarterlyKeys_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPracticeTitle = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butAdd;
}


