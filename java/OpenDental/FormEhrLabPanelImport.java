//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEhrPatientSelectSimple;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.HL7.MessageHL7;
import OpenDentBusiness.HL7.SegmentHL7;
import OpenDentBusiness.LabPanel;
import OpenDentBusiness.LabPanels;
import OpenDentBusiness.LabResult;
import OpenDentBusiness.LabResults;
import OpenDentBusiness.MedicalOrder;
import OpenDentBusiness.MedicalOrders;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.SegmentNameHL7;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrLabPanelImport  extends Form 
{

    private List<MedicalOrder> listLabOrders = new List<MedicalOrder>();
    private long patNum = new long();
    private String fName = new String();
    private String lName = new String();
    public FormEhrLabPanelImport() throws Exception {
        initializeComponent();
    }

    private void formLabPanelImport_Load(Object sender, EventArgs e) throws Exception {
    }

    private void butReceive_Click(Object sender, EventArgs e) throws Exception {
        /*string sample=@"MSH|^~\&|KAM|DGI|Y|OU|20100920093000||ORU^R01^ORU_R01|20100920093000|P|2.3.1
        PID||405979410 |405979410^^^&2.16.840.1.113883.19.3.2.1&ISO^MR||Lewis^Frank ||19500101|M||2106-3^White^HL70005|622 Chestnut^^Springfield^Tennessee^37026^^M||^^^^^615^3826396|||||405979410 ||||N^Not Hispanic or Latino^HL70189
        OBR|1|OrderNum-1001|FillOrder-1001|24331-1^Lipid Panel^LN||20100920083000|20100920083000|20100920083000|||||Hemolyzed ||LNA&Arterial Catheter&Hl70070| ProviderIDNum-100^Crow^Tom^Black^III^Dr.||||Aloha Laboratories 575 Luau Street Honolulu Hawaii 96813 ||||CH|F|
        OBX|1|NM|14647-2^Total cholesterol^LN |134465|162|mg/dl |<200| N|||F|||20100920083000 
        OBX|2|NM|14646-4^HDL cholesterol^LN|333123|43|mg/dl|>=40| N|||F|||20100920083000
        OBX|3|NM|2089-1^LDL cholesterol^LN|333123|84|mg/dl|<100| N|||F|||20100920083000
        OBX|4|NM|14927-8^Triglycerides^LN|333123|127|mg/dl|<150| N|||F|||20100920083000";*/
        String sample = "";
        Cursor = Cursors.WaitCursor;
        try
        {
            sample = EmailMessages.receiveOneForEhrTest();
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        Cursor = Cursors.Default;
        textHL7Raw.Text = sample;
    }

    private void textHL7Raw_TextChanged(Object sender, EventArgs e) throws Exception {
        textPatName.Text = "";
        textPatIDNum.Text = "";
        textPatAccountNum.Text = "";
        textDateTimeTest.Text = "";
        textServiceID.Text = "";
        textServiceName.Text = "";
        try
        {
            //ORU-R01
            MessageHL7 msg = new MessageHL7(textHL7Raw.Text);
            SegmentHL7 segPID = msg.GetSegment(SegmentNameHL7.PID, false);
            patNum = 0;
            if (segPID != null)
            {
                fName = segPID.getFieldComponent(5,1);
                lName = segPID.getFieldComponent(5,0);
                //F M L ?
                textPatName.Text = segPID.getFieldComponent(5,1) + " " + segPID.getFieldComponent(5,2) + " " + segPID.getFieldComponent(5,0);
                textPatIDNum.Text = segPID.getFieldFullText(2);
                patNum = Patients.getPatNumByName(lName,fName);
                //could be 0
                //patNum=PIn.Long(segPID.GetFieldFullText(2));
                textPatAccountNum.Text = segPID.getFieldFullText(18);
            }
             
            SegmentHL7 segOBR = msg.GetSegment(SegmentNameHL7.OBR, false);
            if (segOBR != null)
            {
                textServiceID.Text = segOBR.getFieldComponent(4,0);
                textServiceName.Text = segOBR.getFieldComponent(4,1);
            }
             
            SegmentHL7 segOBX = msg.GetSegment(SegmentNameHL7.OBX, false);
            if (segOBX != null)
            {
                DateTime dt = segOBX.getDateTime(14);
                if (dt.Year > 1880)
                {
                    textDateTimeTest.Text = dt.ToShortDateString();
                }
                 
            }
             
            // +" "+dt.ToShortTimeString();
            fillPatAndGrid();
        }
        catch (Exception __dummyCatchVar0)
        {
            patNum = 0;
            fillPatAndGrid();
            MessageBox.Show("Error parsing HL7.");
        }
    
    }

    private void fillPatAndGrid() throws Exception {
        Patient pat = Patients.getLim(patNum);
        textPatName2.Text = pat.getNameFLnoPref();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Date",85);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Order",190);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Results Attached", 150, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        listLabOrders = MedicalOrders.getAllLabs(patNum);
        //this works for 0
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listLabOrders.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listLabOrders[i].DateTimeOrder.ToShortDateString());
            row.getCells().Add(listLabOrders[i].Description);
            boolean hasResultsAttached = MedicalOrders.LabHasResultsAttached(listLabOrders[i].MedicalOrderNum);
            if (hasResultsAttached)
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
        //if(!CreateLabPanel()) {
        //	return;
        //}
        createLabPanel();
        DialogResult = DialogResult.OK;
    }

    private void butChangePat_Click(Object sender, EventArgs e) throws Exception {
        Patient pat = Patients.getLim(patNum);
        //doesn't return null
        FormEhrPatientSelectSimple FormP = new FormEhrPatientSelectSimple();
        if (String.IsNullOrEmpty(pat.LName) && String.IsNullOrEmpty(pat.FName))
        {
            //patient could not be located by patnum, so use parsed name
            FormP.LName = lName;
            FormP.FName = fName;
        }
        else
        {
            FormP.LName = pat.LName;
            FormP.FName = pat.FName;
        } 
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        patNum = FormP.SelectedPatNum;
        fillPatAndGrid();
    }

    private void createLabPanel() throws Exception {
        MedicalOrder order = listLabOrders[gridMain.getSelectedIndex()];
        MessageHL7 msg = new MessageHL7(textHL7Raw.Text);
        //SegmentHL7 segOBR=null;
        //SegmentHL7 segOBX=null;
        //int idxPanel=0;
        //int idxResult=0;
        LabPanel panel = null;
        LabResult result = null;
        for (int i = 0;i < msg.Segments.Count;i++)
        {
            //loop through all message segments.
            if (msg.Segments[i].Name == SegmentNameHL7.OBR)
            {
                //if this is the start of a new panel
                panel = new LabPanel();
                panel.PatNum = order.PatNum;
                panel.MedicalOrderNum = order.MedicalOrderNum;
                panel.RawMessage = textHL7Raw.Text;
                panel.LabNameAddress = msg.Segments[i].GetFieldFullText(20);
                panel.SpecimenSource = msg.Segments[i].GetFieldFullText(15);
                panel.SpecimenCondition = msg.Segments[i].GetFieldFullText(13);
                panel.ServiceId = msg.Segments[i].GetFieldComponent(4, 0);
                panel.ServiceName = msg.Segments[i].GetFieldComponent(4, 1);
                LabPanels.insert(panel);
            }
             
            if (msg.Segments[i].Name == SegmentNameHL7.OBX)
            {
                //if this is a result within a panel
                result = new LabResult();
                result.LabPanelNum = panel.LabPanelNum;
                result.DateTimeTest = msg.Segments[i].GetDateTime(14);
                result.TestID = msg.Segments[i].GetFieldComponent(3, 0);
                result.TestName = msg.Segments[i].GetFieldComponent(3, 1);
                result.ObsValue = msg.Segments[i].GetFieldFullText(5);
                result.ObsUnits = msg.Segments[i].GetFieldFullText(6);
                result.ObsRange = msg.Segments[i].GetFieldFullText(7);
                LabResults.insert(result);
            }
             
        }
    }

    //any other kind of segment, continue.
    //order.IsLabPending=false;
    //MedicalOrders.Update(order);
    //return true;//I guess it's always true?
    private void butOk_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MessageBox.Show("Please select a lab order first.");
            return ;
        }
         
        //if(!CreateLabPanel()) {
        //	return;
        //}
        createLabPanel();
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
        this.textHL7Raw = new System.Windows.Forms.TextBox();
        this.textPatName = new System.Windows.Forms.TextBox();
        this.textPatIDNum = new System.Windows.Forms.TextBox();
        this.textPatAccountNum = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.butCancel = new System.Windows.Forms.Button();
        this.butOk = new System.Windows.Forms.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textDateTimeTest = new System.Windows.Forms.TextBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.textServiceID = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textServiceName = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textPatName2 = new System.Windows.Forms.TextBox();
        this.butChangePat = new System.Windows.Forms.Button();
        this.butReceive = new System.Windows.Forms.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // textHL7Raw
        //
        this.textHL7Raw.AcceptsReturn = true;
        this.textHL7Raw.AcceptsTab = true;
        this.textHL7Raw.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textHL7Raw.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textHL7Raw.Location = new System.Drawing.Point(136, 41);
        this.textHL7Raw.Multiline = true;
        this.textHL7Raw.Name = "textHL7Raw";
        this.textHL7Raw.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        this.textHL7Raw.Size = new System.Drawing.Size(747, 205);
        this.textHL7Raw.TabIndex = 1;
        this.textHL7Raw.WordWrap = false;
        this.textHL7Raw.TextChanged += new System.EventHandler(this.textHL7Raw_TextChanged);
        //
        // textPatName
        //
        this.textPatName.Location = new System.Drawing.Point(124, 24);
        this.textPatName.Name = "textPatName";
        this.textPatName.ReadOnly = true;
        this.textPatName.Size = new System.Drawing.Size(301, 20);
        this.textPatName.TabIndex = 1;
        //
        // textPatIDNum
        //
        this.textPatIDNum.Location = new System.Drawing.Point(124, 50);
        this.textPatIDNum.Name = "textPatIDNum";
        this.textPatIDNum.ReadOnly = true;
        this.textPatIDNum.Size = new System.Drawing.Size(79, 20);
        this.textPatIDNum.TabIndex = 2;
        //
        // textPatAccountNum
        //
        this.textPatAccountNum.Location = new System.Drawing.Point(124, 76);
        this.textPatAccountNum.Name = "textPatAccountNum";
        this.textPatAccountNum.ReadOnly = true;
        this.textPatAccountNum.Size = new System.Drawing.Size(110, 20);
        this.textPatAccountNum.TabIndex = 3;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(17, 42);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(118, 17);
        this.label1.TabIndex = 4;
        this.label1.Text = "Raw HL7 Message";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(5, 25);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(118, 17);
        this.label2.TabIndex = 5;
        this.label2.Text = "Patient Name";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(5, 51);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(118, 17);
        this.label3.TabIndex = 6;
        this.label3.Text = "Patient ID";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(5, 77);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(118, 17);
        this.label4.TabIndex = 7;
        this.label4.Text = "Patient Account";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(807, 501);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 8;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOk
        //
        this.butOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOk.Location = new System.Drawing.Point(726, 501);
        this.butOk.Name = "butOk";
        this.butOk.Size = new System.Drawing.Size(75, 23);
        this.butOk.TabIndex = 0;
        this.butOk.Text = "Ok";
        this.butOk.UseVisualStyleBackColor = true;
        this.butOk.Click += new System.EventHandler(this.butOk_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.label8);
        this.groupBox1.Controls.Add(this.textDateTimeTest);
        this.groupBox1.Controls.Add(this.groupBox2);
        this.groupBox1.Controls.Add(this.textPatName);
        this.groupBox1.Controls.Add(this.textPatIDNum);
        this.groupBox1.Controls.Add(this.textPatAccountNum);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Location = new System.Drawing.Point(12, 262);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(435, 228);
        this.groupBox1.TabIndex = 13;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Info From Incoming Message";
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(55, 104);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(67, 17);
        this.label8.TabIndex = 10;
        this.label8.Text = "Date";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateTimeTest
        //
        this.textDateTimeTest.Location = new System.Drawing.Point(124, 102);
        this.textDateTimeTest.Name = "textDateTimeTest";
        this.textDateTimeTest.ReadOnly = true;
        this.textDateTimeTest.Size = new System.Drawing.Size(106, 20);
        this.textDateTimeTest.TabIndex = 9;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.textServiceID);
        this.groupBox2.Controls.Add(this.label5);
        this.groupBox2.Controls.Add(this.textServiceName);
        this.groupBox2.Controls.Add(this.label7);
        this.groupBox2.Location = new System.Drawing.Point(44, 133);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(381, 80);
        this.groupBox2.TabIndex = 8;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Service";
        //
        // textServiceID
        //
        this.textServiceID.Location = new System.Drawing.Point(80, 22);
        this.textServiceID.Name = "textServiceID";
        this.textServiceID.ReadOnly = true;
        this.textServiceID.Size = new System.Drawing.Size(106, 20);
        this.textServiceID.TabIndex = 0;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(8, 24);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(70, 17);
        this.label5.TabIndex = 2;
        this.label5.Text = "LOINC";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textServiceName
        //
        this.textServiceName.Location = new System.Drawing.Point(80, 48);
        this.textServiceName.Name = "textServiceName";
        this.textServiceName.ReadOnly = true;
        this.textServiceName.Size = new System.Drawing.Size(284, 20);
        this.textServiceName.TabIndex = 1;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(8, 50);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(70, 17);
        this.label7.TabIndex = 3;
        this.label7.Text = "Name";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(467, 257);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(334, 32);
        this.label6.TabIndex = 14;
        this.label6.Text = "Attach these lab results to a lab order by selecting a patient first, and then se" + "lecting one lab order from the list.";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(470, 319);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(412, 171);
        this.gridMain.TabIndex = 10;
        this.gridMain.setTitle("Lab Orders for the Patient Above");
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
        // textPatName2
        //
        this.textPatName2.Location = new System.Drawing.Point(470, 292);
        this.textPatName2.Name = "textPatName2";
        this.textPatName2.ReadOnly = true;
        this.textPatName2.Size = new System.Drawing.Size(349, 20);
        this.textPatName2.TabIndex = 15;
        //
        // butChangePat
        //
        this.butChangePat.Location = new System.Drawing.Point(823, 290);
        this.butChangePat.Name = "butChangePat";
        this.butChangePat.Size = new System.Drawing.Size(59, 23);
        this.butChangePat.TabIndex = 16;
        this.butChangePat.Text = "Change";
        this.butChangePat.UseVisualStyleBackColor = true;
        this.butChangePat.Click += new System.EventHandler(this.butChangePat_Click);
        //
        // butReceive
        //
        this.butReceive.Location = new System.Drawing.Point(136, 12);
        this.butReceive.Name = "butReceive";
        this.butReceive.Size = new System.Drawing.Size(94, 23);
        this.butReceive.TabIndex = 17;
        this.butReceive.Text = "Receive";
        this.butReceive.UseVisualStyleBackColor = true;
        this.butReceive.Click += new System.EventHandler(this.butReceive_Click);
        //
        // FormLabPanelImport
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(895, 536);
        this.Controls.Add(this.butReceive);
        this.Controls.Add(this.butChangePat);
        this.Controls.Add(this.textPatName2);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOk);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textHL7Raw);
        this.Name = "FormLabPanelImport";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Import Lab Results";
        this.Load += new System.EventHandler(this.FormLabPanelImport_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textHL7Raw = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPatName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPatIDNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPatAccountNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOk = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textServiceID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textServiceName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDateTimeTest = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPatName2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butChangePat = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butReceive = new System.Windows.Forms.Button();
}


