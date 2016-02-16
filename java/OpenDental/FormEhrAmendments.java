//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEhrAmendmentEdit;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrAmendment;
import OpenDentBusiness.EhrAmendments;
import OpenDentBusiness.Patient;
import OpenDentBusiness.YN;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrAmendments  extends Form 
{

    private List<EhrAmendment> ListAmendments = new List<EhrAmendment>();
    public Patient PatCur;
    public FormEhrAmendments() throws Exception {
        initializeComponent();
    }

    private void formEhrAmendments_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Entry Date",70);
        col.setTextAlign(HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Status",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Source",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Description",170);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Scanned",25);
        col.setTextAlign(HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        ListAmendments = EhrAmendments.refresh(PatCur.PatNum);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListAmendments.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListAmendments[i].DateTRequest.ToShortDateString());
            if (ListAmendments[i].IsAccepted == YN.Yes)
            {
                row.getCells().add("Accepted");
            }
            else if (ListAmendments[i].IsAccepted == YN.No)
            {
                row.getCells().add("Denied");
            }
            else
            {
                row.getCells().add("Requested");
            }  
            row.getCells().Add(ListAmendments[i].Source.ToString());
            row.getCells().Add(ListAmendments[i].Description);
            if (!StringSupport.equals(ListAmendments[i].FileName, ""))
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        EhrAmendment ehrAmd = ListAmendments[e.getRow()];
        FormEhrAmendmentEdit FormEAE = new FormEhrAmendmentEdit(ehrAmd);
        FormEAE.ShowDialog();
        fillGrid();
    }

    //Always have to refresh grid due to using the images module to update the db.
    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        EhrAmendment ehrAmd = new EhrAmendment();
        ehrAmd.PatNum = PatCur.PatNum;
        ehrAmd.setIsNew(true);
        EhrAmendments.insert(ehrAmd);
        FormEhrAmendmentEdit FormEAE = new FormEhrAmendmentEdit(ehrAmd);
        FormEAE.ShowDialog();
        fillGrid();
    }

    //Always have to refresh grid due to using the images module to update the db.
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
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(18, 48);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(470, 222);
        this.gridMain.TabIndex = 25;
        this.gridMain.setTitle("Amendments");
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
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(501, 246);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 28;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
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
        this.butAdd.Location = new System.Drawing.Point(501, 48);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 29;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(15, 9);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(473, 33);
        this.label3.TabIndex = 130;
        this.label3.Text = "Patients can bring in documents and request that they be appended to their record" + ".  They can then be scanned or the hardcopy filed.";
        //
        // FormEhrAmendments
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(588, 282);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMain);
        this.Name = "FormEhrAmendments";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "Amendments";
        this.Load += new System.EventHandler(this.FormEhrAmendments_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
}


