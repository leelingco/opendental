//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEhrMedicalOrderLabEdit;
import OpenDental.FormEhrMedicalOrderRadEdit;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.MedicalOrder;
import OpenDentBusiness.MedicalOrders;
import OpenDentBusiness.MedicalOrderType;
import OpenDentBusiness.Patient;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrMedicalOrders  extends Form 
{

    public Patient _patCur;
    private DataTable table = new DataTable();
    /**
    * If this is true after exiting, then launch MedicationPat dialog.
    */
    public boolean LaunchMedicationPat = new boolean();
    /**
    * If LaunchMedicationPat is true after exiting, then this specifies which MedicationPat to open.  Will never be zero.
    */
    public long LaunchMedicationPatNum = new long();
    public FormEhrMedicalOrders() throws Exception {
        initializeComponent();
    }

    private void formMedicalOrders_Load(Object sender, EventArgs e) throws Exception {
        fillGridMedOrders();
    }

    private void fillGridMedOrders() throws Exception {
        gridMedOrders.beginUpdate();
        gridMedOrders.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Date",70);
        gridMedOrders.getColumns().add(col);
        col = new ODGridColumn("Type",80);
        gridMedOrders.getColumns().add(col);
        col = new ODGridColumn("Prov",70);
        gridMedOrders.getColumns().add(col);
        col = new ODGridColumn("Instructions",280);
        gridMedOrders.getColumns().add(col);
        col = new ODGridColumn("Status",100);
        gridMedOrders.getColumns().add(col);
        table = MedicalOrders.GetOrderTable(_patCur.PatNum, checkBoxShowDiscontinued.Checked);
        gridMedOrders.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["date"].ToString());
            row.getCells().Add(table.Rows[i]["type"].ToString());
            row.getCells().Add(table.Rows[i]["prov"].ToString());
            row.getCells().Add(table.Rows[i]["description"].ToString());
            row.getCells().Add(table.Rows[i]["status"].ToString());
            gridMedOrders.getRows().add(row);
        }
        gridMedOrders.endUpdate();
    }

    private void gridMedOrders_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        long medicalOrderNum = PIn.Long(table.Rows[e.getRow()]["MedicalOrderNum"].ToString());
        MedicalOrder ord = MedicalOrders.getOne(medicalOrderNum);
        if (ord.MedOrderType == MedicalOrderType.Laboratory)
        {
            FormEhrMedicalOrderLabEdit FormMlab = new FormEhrMedicalOrderLabEdit();
            FormMlab.MedOrderCur = ord;
            FormMlab.ShowDialog();
        }
        else
        {
            //Rad
            FormEhrMedicalOrderRadEdit FormMrad = new FormEhrMedicalOrderRadEdit();
            FormMrad.MedOrderCur = ord;
            FormMrad.ShowDialog();
        } 
        fillGridMedOrders();
    }

    private void checkBoxShowDiscontinued_Click(Object sender, EventArgs e) throws Exception {
        fillGridMedOrders();
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
        this.butClose = new System.Windows.Forms.Button();
        this.checkBoxShowDiscontinued = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.gridMedOrders = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(634, 285);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 9;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // checkBoxShowDiscontinued
        //
        this.checkBoxShowDiscontinued.Location = new System.Drawing.Point(12, 12);
        this.checkBoxShowDiscontinued.Name = "checkBoxShowDiscontinued";
        this.checkBoxShowDiscontinued.Size = new System.Drawing.Size(532, 17);
        this.checkBoxShowDiscontinued.TabIndex = 10;
        this.checkBoxShowDiscontinued.Text = "Show discontinued orders";
        this.checkBoxShowDiscontinued.UseVisualStyleBackColor = true;
        this.checkBoxShowDiscontinued.Click += new System.EventHandler(this.checkBoxShowDiscontinued_Click);
        //
        // label1
        //
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F);
        this.label1.ForeColor = System.Drawing.Color.Black;
        this.label1.Location = new System.Drawing.Point(9, 285);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(619, 28);
        this.label1.TabIndex = 34;
        this.label1.Text = "The grid above shows lab and radiology orders entered while using 2011 EHR.  \r\nLa" + "bs and radiology orders are now entered via the EHR window.";
        //
        // gridMedOrders
        //
        this.gridMedOrders.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMedOrders.setHScrollVisible(false);
        this.gridMedOrders.Location = new System.Drawing.Point(12, 35);
        this.gridMedOrders.Name = "gridMedOrders";
        this.gridMedOrders.setScrollValue(0);
        this.gridMedOrders.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMedOrders.Size = new System.Drawing.Size(697, 244);
        this.gridMedOrders.TabIndex = 5;
        this.gridMedOrders.setTitle("Lab and Radiology Orders");
        this.gridMedOrders.setTranslationName(null);
        this.gridMedOrders.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMedOrders.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMedOrders_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormEhrMedicalOrders
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(721, 320);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkBoxShowDiscontinued);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMedOrders);
        this.Name = "FormEhrMedicalOrders";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "2011 Lab and Radiology Orders";
        this.Load += new System.EventHandler(this.FormMedicalOrders_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMedOrders;
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.CheckBox checkBoxShowDiscontinued = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


