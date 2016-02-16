//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ContactMethod;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormCommPrefPicker  extends Form 
{

    public ContactMethod ContMethCur = ContactMethod.None;
    public FormCommPrefPicker() throws Exception {
        initializeComponent();
    }

    private void formCommPrefPicker_Load(Object sender, EventArgs e) throws Exception {
        if (ContMethCur == null)
        {
            ContMethCur = new ContactMethod();
        }
         
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Contact Preference", 120, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn("ICD9 Code",80);
        //gridMain.Columns.Add(col);
        //col=new ODGridColumn("SNOMED Code",80);
        //gridMain.Columns.Add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < Enum.GetNames(ContactMethod.class).Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Enum.GetNames(ContactMethod.class)[i]);
            //row.Cells.Add(DiseaseDefs.ListLong[i].ICD9Code);
            //row.Cells.Add(DiseaseDefs.ListLong[i].SnomedCode);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        ContMethCur = ContactMethod.values()[e.getRow()];
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a communication preference first.");
            return ;
        }
         
        ContMethCur = ContactMethod.values()[gridMain.getSelectedIndex()];
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
        this.butCancel = new System.Windows.Forms.Button();
        this.butOK = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(435, 605);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 7;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(354, 605);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(498, 587);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("Comm Pref");
        this.gridMain.setTranslationName("FormMedicationPicker");
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
        // FormCommPrefPicker
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(522, 640);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormCommPrefPicker";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Pick Communication Preference";
        this.Load += new System.EventHandler(this.FormCommPrefPicker_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridMain;
}


