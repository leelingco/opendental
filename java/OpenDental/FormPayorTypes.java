//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormPayorTypeEdit;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PayorType;
import OpenDentBusiness.PayorTypes;
import OpenDentBusiness.Sops;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormPayorTypes;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormPayorTypes  extends Form 
{

    private List<PayorType> ListPayorTypes = new List<PayorType>();
    public Patient PatCur;
    public FormPayorTypes() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formPayorTypes_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Date Start",70);
        col.setTextAlign(HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Date End",70);
        col.setTextAlign(HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("SOP Code",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Description",250);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Note",100);
        gridMain.getColumns().add(col);
        ListPayorTypes = PayorTypes.refresh(PatCur.PatNum);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListPayorTypes.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListPayorTypes[i].DateStart.ToShortDateString());
            if (i == ListPayorTypes.Count - 1)
            {
                row.getCells().add("Current");
            }
            else
            {
                row.getCells().Add(ListPayorTypes[i + 1].DateStart.ToShortDateString());
            } 
            row.getCells().Add(ListPayorTypes[i].SopCode);
            row.getCells().Add(Sops.GetOneDescription(ListPayorTypes[i].SopCode));
            row.getCells().Add(ListPayorTypes[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        PayorType payorType = ListPayorTypes[e.getRow()];
        FormPayorTypeEdit FormPTE = new FormPayorTypeEdit(payorType);
        FormPTE.ShowDialog();
        if (FormPTE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        PayorType payorType = new PayorType();
        payorType.PatNum = PatCur.PatNum;
        payorType.DateStart = DateTime.Today;
        FormPayorTypeEdit FormPTE = new FormPayorTypeEdit(payorType);
        FormPTE.IsNew = true;
        FormPTE.ShowDialog();
        if (FormPTE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPayorTypes.class);
        this.label7 = new System.Windows.Forms.Label();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(21, 9);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(473, 19);
        this.label7.TabIndex = 137;
        this.label7.Text = "This shows the history of the patient\'s payor types and the date ranges that each" + " were used.";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(621, 31);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 32;
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
        this.butClose.Location = new System.Drawing.Point(621, 235);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 31;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(24, 31);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(585, 228);
        this.gridMain.TabIndex = 30;
        this.gridMain.setTitle("History");
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
        // FormPayorTypes
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(702, 271);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormPayorTypes";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Payor Types";
        this.Load += new System.EventHandler(this.FormPayorTypes_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
}


