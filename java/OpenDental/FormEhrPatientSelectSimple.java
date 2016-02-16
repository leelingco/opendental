//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrPatientSelectSimple  extends Form 
{

    public long SelectedPatNum = new long();
    private DataTable table = new DataTable();
    public String FName = new String();
    public String LName = new String();
    public FormEhrPatientSelectSimple() throws Exception {
        initializeComponent();
    }

    private void formPatientSelectSimple_Load(Object sender, EventArgs e) throws Exception {
        textFName.Text = FName;
        textLName.Text = LName;
        fillGrid();
    }

    private void fillGrid() throws Exception {
        table = Patients.GetPtDataTable(false, textLName.Text, textFName.Text, "", "", false, "", "", "", "", "", 0, false, false, Security.getCurUser().ClinicNum, DateTime.MinValue, 0, "", "");
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("PatNum",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("LName",120);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("FName",120);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["PatNum"].ToString());
            row.getCells().Add(table.Rows[i]["LName"].ToString());
            row.getCells().Add(table.Rows[i]["FName"].ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        patSelected();
    }

    private void butSearch_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void patSelected() throws Exception {
        SelectedPatNum = PIn.Long(table.Rows[gridMain.getSelectedIndex()]["PatNum"].ToString());
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MessageBox.Show("Please select a patient first.");
            return ;
        }
         
        patSelected();
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
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textFName = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textLName = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butSearch = new System.Windows.Forms.Button();
        this.butOK = new System.Windows.Forms.Button();
        this.butCancel = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(2, 2);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(559, 527);
        this.gridMain.TabIndex = 10;
        this.gridMain.setTitle("Select Patient");
        this.gridMain.setTranslationName("FormPatientSelect");
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
        // textFName
        //
        this.textFName.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textFName.Location = new System.Drawing.Point(646, 135);
        this.textFName.Name = "textFName";
        this.textFName.Size = new System.Drawing.Size(124, 20);
        this.textFName.TabIndex = 12;
        //
        // label3
        //
        this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label3.Location = new System.Drawing.Point(566, 139);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(79, 12);
        this.label3.TabIndex = 14;
        this.label3.Text = "First Name";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textLName
        //
        this.textLName.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textLName.Location = new System.Drawing.Point(646, 115);
        this.textLName.Name = "textLName";
        this.textLName.Size = new System.Drawing.Size(124, 20);
        this.textLName.TabIndex = 11;
        //
        // label1
        //
        this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label1.Location = new System.Drawing.Point(563, 118);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(82, 12);
        this.label1.TabIndex = 13;
        this.label1.Text = "Last Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butSearch
        //
        this.butSearch.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butSearch.Location = new System.Drawing.Point(686, 200);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 23);
        this.butSearch.TabIndex = 15;
        this.butSearch.Text = "Search";
        this.butSearch.UseVisualStyleBackColor = true;
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(605, 495);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 16;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(686, 495);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 17;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormPatientSelectSimple
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(773, 530);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butSearch);
        this.Controls.Add(this.textFName);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textLName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Name = "FormPatientSelectSimple";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Select Patient";
        this.Load += new System.EventHandler(this.FormPatientSelectSimple_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.TextBox textFName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textLName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butSearch = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
}


