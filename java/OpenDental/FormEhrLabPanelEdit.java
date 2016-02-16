//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEhrLabResultEdit;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.LabPanel;
import OpenDentBusiness.LabPanels;
import OpenDentBusiness.LabResult;
import OpenDentBusiness.LabResults;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrLabPanelEdit  extends Form 
{

    public List<LabResult> listResults = new List<LabResult>();
    public LabPanel PanelCur;
    public boolean IsNew = new boolean();
    public FormEhrLabPanelEdit() throws Exception {
        initializeComponent();
    }

    private void formLabPanelEdit_Load(Object sender, EventArgs e) throws Exception {
        textRawMsg.Text = PanelCur.RawMessage;
        Patient pat = Patients.getLim(PanelCur.PatNum);
        textName.Text = pat.getNameFL();
        textServiceID.Text = PanelCur.ServiceId;
        textServiceName.Text = PanelCur.ServiceName;
        textLabName.Text = PanelCur.LabNameAddress;
        textSpecimenCondition.Text = PanelCur.SpecimenCondition;
        if (!StringSupport.equals(PanelCur.SpecimenSource, ""))
        {
            String[] components = PanelCur.SpecimenSource.Split('&');
            if (components.Length == 3)
            {
                textSpecimenSourceCode.Text = components[0];
                textSpecimenLocation.Text = components[1];
            }
             
        }
         
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Test Date",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("LOINC",75);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Test Performed",130);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("ResultVal",60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Units",45);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Range",55);
        gridMain.getColumns().add(col);
        listResults = LabResults.getForPanel(PanelCur.LabPanelNum);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listResults.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listResults[i].DateTimeTest.ToShortDateString());
            row.getCells().Add(listResults[i].TestID);
            row.getCells().Add(listResults[i].TestName);
            row.getCells().Add(listResults[i].ObsValue);
            row.getCells().Add(listResults[i].ObsUnits);
            row.getCells().Add(listResults[i].ObsRange);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrLabResultEdit FormLRE = new FormEhrLabResultEdit();
        FormLRE.LabCur = listResults[e.getRow()];
        FormLRE.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            MessageBox.Show("Lab results can only be added to saved or existing lab panels.");
            return ;
        }
         
        FormEhrLabResultEdit FormLRE = new FormEhrLabResultEdit();
        FormLRE.IsNew = true;
        FormLRE.LabCur = new LabResult();
        FormLRE.LabCur.LabPanelNum = PanelCur.LabPanelNum;
        FormLRE.LabCur.DateTimeTest = DateTime.Now;
        FormLRE.ShowDialog();
        fillGrid();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (MessageBox.Show("Delete?", "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        LabPanels.delete(PanelCur.LabPanelNum);
        LabResults.deleteForPanel(PanelCur.LabPanelNum);
        DialogResult = DialogResult.OK;
    }

    private void butOk_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textSpecimenSourceCode.Text, "") && StringSupport.equals(textSpecimenLocation.Text, ""))
        {
            MessageBox.Show("If specimen code is entered, then specimen location must be entered.");
            return ;
        }
         
        if (StringSupport.equals(textSpecimenSourceCode.Text, "") && !StringSupport.equals(textSpecimenLocation.Text, ""))
        {
            MessageBox.Show("If specimen location is entered, then specimen code must be entered.");
            return ;
        }
         
        PanelCur.ServiceId = textServiceID.Text;
        PanelCur.ServiceName = textServiceName.Text;
        PanelCur.LabNameAddress = textLabName.Text;
        PanelCur.SpecimenCondition = textSpecimenCondition.Text;
        if (StringSupport.equals(textSpecimenSourceCode.Text, ""))
        {
            PanelCur.SpecimenSource = "";
        }
        else
        {
            PanelCur.SpecimenSource = textSpecimenSourceCode.Text + "&" + textSpecimenLocation.Text + "&HL70070";
        } 
        if (IsNew)
        {
            LabPanels.insert(PanelCur);
        }
        else
        {
            LabPanels.update(PanelCur);
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
        this.butCancel = new System.Windows.Forms.Button();
        this.butOk = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.textRawMsg = new System.Windows.Forms.TextBox();
        this.textLabName = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textSpecimenCondition = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textSpecimenSourceCode = new System.Windows.Forms.TextBox();
        this.butAdd = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label5 = new System.Windows.Forms.Label();
        this.textName = new System.Windows.Forms.TextBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textSpecimenLocation = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.textServiceID = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textServiceName = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(485, 560);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 1;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOk
        //
        this.butOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOk.Location = new System.Drawing.Point(404, 560);
        this.butOk.Name = "butOk";
        this.butOk.Size = new System.Drawing.Size(75, 24);
        this.butOk.TabIndex = 2;
        this.butOk.Text = "OK";
        this.butOk.UseVisualStyleBackColor = true;
        this.butOk.Click += new System.EventHandler(this.butOk_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(12, 560);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 3;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textRawMsg
        //
        this.textRawMsg.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textRawMsg.Location = new System.Drawing.Point(16, 22);
        this.textRawMsg.Multiline = true;
        this.textRawMsg.Name = "textRawMsg";
        this.textRawMsg.ReadOnly = true;
        this.textRawMsg.ScrollBars = System.Windows.Forms.ScrollBars.Horizontal;
        this.textRawMsg.Size = new System.Drawing.Size(544, 148);
        this.textRawMsg.TabIndex = 4;
        this.textRawMsg.WordWrap = false;
        //
        // textLabName
        //
        this.textLabName.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textLabName.Location = new System.Drawing.Point(130, 266);
        this.textLabName.Multiline = true;
        this.textLabName.Name = "textLabName";
        this.textLabName.Size = new System.Drawing.Size(430, 36);
        this.textLabName.TabIndex = 5;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(14, 1);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(114, 17);
        this.label1.TabIndex = 6;
        this.label1.Text = "Raw HL7 Message";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(7, 267);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(120, 18);
        this.label2.TabIndex = 7;
        this.label2.Text = "Lab Name / Address";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(2, 304);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(125, 17);
        this.label3.TabIndex = 9;
        this.label3.Text = "Specimen Condition";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSpecimenCondition
        //
        this.textSpecimenCondition.Location = new System.Drawing.Point(130, 302);
        this.textSpecimenCondition.Name = "textSpecimenCondition";
        this.textSpecimenCondition.Size = new System.Drawing.Size(158, 20);
        this.textSpecimenCondition.TabIndex = 8;
        this.textSpecimenCondition.WordWrap = false;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(24, 17);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(88, 17);
        this.label4.TabIndex = 11;
        this.label4.Text = "Code";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSpecimenSourceCode
        //
        this.textSpecimenSourceCode.Location = new System.Drawing.Point(113, 14);
        this.textSpecimenSourceCode.Name = "textSpecimenSourceCode";
        this.textSpecimenSourceCode.Size = new System.Drawing.Size(64, 20);
        this.textSpecimenSourceCode.TabIndex = 10;
        this.textSpecimenSourceCode.WordWrap = false;
        //
        // butAdd
        //
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.Location = new System.Drawing.Point(207, 560);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(81, 24);
        this.butAdd.TabIndex = 13;
        this.butAdd.Text = "Add Result";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 395);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(548, 156);
        this.gridMain.TabIndex = 0;
        this.gridMain.setTitle("Lab Results");
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
        // label5
        //
        this.label5.Location = new System.Drawing.Point(5, 176);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(122, 17);
        this.label5.TabIndex = 15;
        this.label5.Text = "Patient Name";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textName
        //
        this.textName.Location = new System.Drawing.Point(130, 174);
        this.textName.Name = "textName";
        this.textName.ReadOnly = true;
        this.textName.Size = new System.Drawing.Size(312, 20);
        this.textName.TabIndex = 14;
        this.textName.WordWrap = false;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.label7);
        this.groupBox1.Controls.Add(this.textSpecimenLocation);
        this.groupBox1.Controls.Add(this.label6);
        this.groupBox1.Controls.Add(this.textSpecimenSourceCode);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Location = new System.Drawing.Point(17, 328);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(425, 61);
        this.groupBox1.TabIndex = 16;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Specimen Source";
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(183, 17);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(202, 17);
        this.label7.TabIndex = 14;
        this.label7.Text = "HL7 0070 Format";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textSpecimenLocation
        //
        this.textSpecimenLocation.Location = new System.Drawing.Point(113, 34);
        this.textSpecimenLocation.Name = "textSpecimenLocation";
        this.textSpecimenLocation.Size = new System.Drawing.Size(302, 20);
        this.textSpecimenLocation.TabIndex = 12;
        this.textSpecimenLocation.WordWrap = false;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(24, 37);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(88, 17);
        this.label6.TabIndex = 13;
        this.label6.Text = "Location";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.textServiceID);
        this.groupBox2.Controls.Add(this.label8);
        this.groupBox2.Controls.Add(this.textServiceName);
        this.groupBox2.Controls.Add(this.label9);
        this.groupBox2.Location = new System.Drawing.Point(17, 198);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(425, 61);
        this.groupBox2.TabIndex = 17;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Service";
        //
        // textServiceID
        //
        this.textServiceID.Location = new System.Drawing.Point(113, 14);
        this.textServiceID.Name = "textServiceID";
        this.textServiceID.Size = new System.Drawing.Size(106, 20);
        this.textServiceID.TabIndex = 0;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(41, 16);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(70, 17);
        this.label8.TabIndex = 2;
        this.label8.Text = "LOINC";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textServiceName
        //
        this.textServiceName.Location = new System.Drawing.Point(113, 34);
        this.textServiceName.Name = "textServiceName";
        this.textServiceName.Size = new System.Drawing.Size(302, 20);
        this.textServiceName.TabIndex = 1;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(41, 36);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(70, 17);
        this.label9.TabIndex = 3;
        this.label9.Text = "Name";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormLabPanelEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(572, 595);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textName);
        this.Controls.Add(this.textSpecimenCondition);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textLabName);
        this.Controls.Add(this.textRawMsg);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOk);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.gridMain);
        this.Name = "FormLabPanelEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Lab Panel";
        this.Load += new System.EventHandler(this.FormLabPanelEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOk = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textRawMsg = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textLabName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSpecimenCondition = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSpecimenSourceCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSpecimenLocation = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textServiceID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textServiceName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
}


