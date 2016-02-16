//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import OpenDental.FormEHR;
import OpenDental.FormEhrExportCCD;
import OpenDental.FormEhrSummaryOfCare;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Patient;

public class FormEhrClinicalSummary  extends Form 
{

    public Patient PatCur;
    private List<EhrMeasureEvent> summariesSentList = new List<EhrMeasureEvent>();
    public FormEhrClinicalSummary() throws Exception {
        initializeComponent();
    }

    private void formClinicalSummary_Load(Object sender, EventArgs e) throws Exception {
        fillGridEHRMeasureEvents();
    }

    private void fillGridEHRMeasureEvents() throws Exception {
        gridEHRMeasureEvents.beginUpdate();
        gridEHRMeasureEvents.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("DateTime",140);
        gridEHRMeasureEvents.getColumns().add(col);
        //col = new ODGridColumn("Details",600);
        //gridEHRMeasureEvents.Columns.Add(col);
        summariesSentList = EhrMeasureEvents.refreshByType(PatCur.PatNum,EhrMeasureEventType.ClinicalSummaryProvidedToPt);
        gridEHRMeasureEvents.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < summariesSentList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(summariesSentList[i].DateTEvent.ToString());
            //row.Cells.Add(summariesSentList[i].EventType.ToString());
            gridEHRMeasureEvents.getRows().add(row);
        }
        gridEHRMeasureEvents.endUpdate();
    }

    private void butExport_Click(Object sender, EventArgs e) throws Exception {
        String ccd = "";
        try
        {
            FormEhrExportCCD FormEEC = new FormEhrExportCCD(PatCur);
            FormEEC.ShowDialog();
            if (FormEEC.DialogResult == DialogResult.OK)
            {
                ccd = FormEEC.getCCD();
            }
            else
            {
                return ;
            } 
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        FolderBrowserDialog dlg = new FolderBrowserDialog();
        dlg.SelectedPath = ImageStore.getPatientFolder(PatCur,ImageStore.getPreferredAtoZpath());
        //Default to patient image folder.
        DialogResult result = dlg.ShowDialog();
        if (result != DialogResult.OK)
        {
            return ;
        }
         
        if (File.Exists(Path.Combine(dlg.SelectedPath, "ccd.xml")))
        {
            if (MessageBox.Show("Overwrite existing ccd.xml?", "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
        }
         
        File.WriteAllText(Path.Combine(dlg.SelectedPath, "ccd.xml"), ccd);
        File.WriteAllText(Path.Combine(dlg.SelectedPath, "ccd.xsl"), FormEHR.getEhrResource("CCD"));
        EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
        newMeasureEvent.DateTEvent = DateTime.Now;
        newMeasureEvent.EventType = EhrMeasureEventType.ClinicalSummaryProvidedToPt;
        newMeasureEvent.PatNum = PatCur.PatNum;
        EhrMeasureEvents.insert(newMeasureEvent);
        fillGridEHRMeasureEvents();
        MessageBox.Show("Exported");
    }

    private void butSendEmail_Click(Object sender, EventArgs e) throws Exception {
        MsgBox.show(this,"Clinical summaries cannot be emailed to patients due to security concerns.\r\n" + "Instruct the patient to access their information in the patient portal.\r\n" + "If you are trying to send the patient information directly to another provider, then go to Chart | EHR | Send/Receive summary of care.");
    }

    private void butShowXhtml_Click(Object sender, EventArgs e) throws Exception {
        String ccd = "";
        try
        {
            FormEhrExportCCD FormEEC = new FormEhrExportCCD(PatCur);
            FormEEC.ShowDialog();
            if (FormEEC.DialogResult == DialogResult.OK)
            {
                ccd = FormEEC.getCCD();
            }
            else
            {
                return ;
            } 
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        boolean didPrint = FormEhrSummaryOfCare.displayCCD(ccd);
        if (didPrint)
        {
            //we are printing a ccd so add new measure event.
            EhrMeasureEvent measureEvent = new EhrMeasureEvent();
            measureEvent.DateTEvent = DateTime.Now;
            measureEvent.EventType = EhrMeasureEventType.ClinicalSummaryProvidedToPt;
            measureEvent.PatNum = PatCur.PatNum;
            EhrMeasureEvents.insert(measureEvent);
            fillGridEHRMeasureEvents();
        }
         
    }

    private void butShowXml_Click(Object sender, EventArgs e) throws Exception {
        String ccd = "";
        try
        {
            FormEhrExportCCD FormEEC = new FormEhrExportCCD(PatCur);
            FormEEC.ShowDialog();
            if (FormEEC.DialogResult == DialogResult.OK)
            {
                ccd = FormEEC.getCCD();
            }
            else
            {
                return ;
            } 
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(ccd);
        msgbox.ShowDialog();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridEHRMeasureEvents.getSelectedIndices().Length < 1)
        {
            MessageBox.Show("Please select at least one record to delete.");
            return ;
        }
         
        for (int i = 0;i < gridEHRMeasureEvents.getSelectedIndices().Length;i++)
        {
            EhrMeasureEvents.Delete(summariesSentList[gridEHRMeasureEvents.getSelectedIndices()[i]].EhrMeasureEventNum);
        }
        fillGridEHRMeasureEvents();
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
        this.butClose = new System.Windows.Forms.Button();
        this.butSendEmail = new System.Windows.Forms.Button();
        this.gridEHRMeasureEvents = new OpenDental.UI.ODGrid();
        this.butDelete = new System.Windows.Forms.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butShowXhtml = new System.Windows.Forms.Button();
        this.butShowXml = new System.Windows.Forms.Button();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.butExport = new System.Windows.Forms.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(180, 440);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 7;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butSendEmail
        //
        this.butSendEmail.Location = new System.Drawing.Point(109, 19);
        this.butSendEmail.Name = "butSendEmail";
        this.butSendEmail.Size = new System.Drawing.Size(84, 23);
        this.butSendEmail.TabIndex = 11;
        this.butSendEmail.Text = "by E-mail";
        this.butSendEmail.UseVisualStyleBackColor = true;
        this.butSendEmail.Click += new System.EventHandler(this.butSendEmail_Click);
        //
        // gridEHRMeasureEvents
        //
        this.gridEHRMeasureEvents.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridEHRMeasureEvents.setHScrollVisible(false);
        this.gridEHRMeasureEvents.Location = new System.Drawing.Point(15, 166);
        this.gridEHRMeasureEvents.Name = "gridEHRMeasureEvents";
        this.gridEHRMeasureEvents.setScrollValue(0);
        this.gridEHRMeasureEvents.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridEHRMeasureEvents.Size = new System.Drawing.Size(240, 261);
        this.gridEHRMeasureEvents.TabIndex = 13;
        this.gridEHRMeasureEvents.setTitle("Clinical Summaries Sent to Patient");
        this.gridEHRMeasureEvents.setTranslationName(null);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(15, 440);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 12;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butShowXhtml);
        this.groupBox1.Controls.Add(this.butShowXml);
        this.groupBox1.Location = new System.Drawing.Point(39, 108);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(201, 50);
        this.groupBox1.TabIndex = 21;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Show";
        //
        // butShowXhtml
        //
        this.butShowXhtml.Location = new System.Drawing.Point(8, 19);
        this.butShowXhtml.Name = "butShowXhtml";
        this.butShowXhtml.Size = new System.Drawing.Size(84, 23);
        this.butShowXhtml.TabIndex = 18;
        this.butShowXhtml.Text = "Show xhtml";
        this.butShowXhtml.UseVisualStyleBackColor = true;
        this.butShowXhtml.Click += new System.EventHandler(this.butShowXhtml_Click);
        //
        // butShowXml
        //
        this.butShowXml.Location = new System.Drawing.Point(109, 19);
        this.butShowXml.Name = "butShowXml";
        this.butShowXml.Size = new System.Drawing.Size(84, 23);
        this.butShowXml.TabIndex = 19;
        this.butShowXml.Text = "Show xml";
        this.butShowXml.UseVisualStyleBackColor = true;
        this.butShowXml.Click += new System.EventHandler(this.butShowXml_Click);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.label1);
        this.groupBox2.Controls.Add(this.butExport);
        this.groupBox2.Controls.Add(this.butSendEmail);
        this.groupBox2.Location = new System.Drawing.Point(39, 10);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(201, 91);
        this.groupBox2.TabIndex = 22;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Send Clinical Summary to Patient";
        //
        // butExport
        //
        this.butExport.Location = new System.Drawing.Point(8, 19);
        this.butExport.Name = "butExport";
        this.butExport.Size = new System.Drawing.Size(84, 23);
        this.butExport.TabIndex = 12;
        this.butExport.Text = "Export";
        this.butExport.UseVisualStyleBackColor = true;
        this.butExport.Click += new System.EventHandler(this.butExport_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(6, 45);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(192, 43);
        this.label1.TabIndex = 23;
        this.label1.Text = "includes 2 files:\r\nccd.xml - the data\r\nccd.xsl - for human readable viewing";
        //
        // FormClinicalSummary
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(273, 475);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridEHRMeasureEvents);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butClose);
        this.Name = "FormClinicalSummary";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Clinical Summary";
        this.Load += new System.EventHandler(this.FormClinicalSummary_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butSendEmail = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridEHRMeasureEvents;
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butShowXhtml = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butShowXml = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butExport = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
}


