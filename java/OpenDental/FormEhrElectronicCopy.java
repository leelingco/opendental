//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import OpenDental.FormEHR;
import OpenDental.FormEhrSummaryOfCare;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrCCD;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Patient;

public class FormEhrElectronicCopy  extends Form 
{

    public Patient PatCur;
    private List<EhrMeasureEvent> listHistory = new List<EhrMeasureEvent>();
    public FormEhrElectronicCopy() throws Exception {
        initializeComponent();
    }

    private void formElectronicCopy_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("DateTime",140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Type",600);
        gridMain.getColumns().add(col);
        listHistory = EhrMeasureEvents.refreshByType(PatCur.PatNum,EhrMeasureEventType.ElectronicCopyRequested,EhrMeasureEventType.ElectronicCopyProvidedToPt);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listHistory.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listHistory[i].DateTEvent.ToString());
            EventType __dummyScrutVar0 = listHistory[i].EventType;
            if (__dummyScrutVar0.equals(EhrMeasureEventType.ElectronicCopyRequested))
            {
                row.getCells().add("Requested by patient");
            }
            else if (__dummyScrutVar0.equals(EhrMeasureEventType.ElectronicCopyProvidedToPt))
            {
                row.getCells().add("Provided to patient");
            }
              
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butRequest_Click(Object sender, EventArgs e) throws Exception {
        EhrMeasureEvent measureEvent = new EhrMeasureEvent();
        measureEvent.DateTEvent = DateTime.Now.AddMinutes(-1);
        measureEvent.EventType = EhrMeasureEventType.ElectronicCopyRequested;
        measureEvent.PatNum = PatCur.PatNum;
        measureEvent.MoreInfo = "";
        EhrMeasureEvents.insert(measureEvent);
        fillGrid();
    }

    /**
    * When sent by email or exported, this records the event.  It also recordes a request if needed.
    */
    private void recordRequestAndProvide() throws Exception {
        //If there's not an event for a request within the last 5 days, automatically add one.
        boolean requestExists = false;
        for (int i = 0;i < listHistory.Count;i++)
        {
            if (listHistory[i].EventType != EhrMeasureEventType.ElectronicCopyRequested)
            {
                continue;
            }
             
            if (listHistory[i].DateTEvent.Date >= DateTime.Today.AddDays(-5))
            {
                requestExists = true;
                break;
            }
             
        }
        EhrMeasureEvent measureEvent;
        if (!requestExists)
        {
            measureEvent = new EhrMeasureEvent();
            measureEvent.DateTEvent = DateTime.Now.AddMinutes(-1);
            measureEvent.EventType = EhrMeasureEventType.ElectronicCopyRequested;
            measureEvent.PatNum = PatCur.PatNum;
            measureEvent.MoreInfo = "";
            EhrMeasureEvents.insert(measureEvent);
        }
         
        //Always add an event for providing the electronic copy
        measureEvent = new EhrMeasureEvent();
        measureEvent.DateTEvent = DateTime.Now;
        measureEvent.EventType = EhrMeasureEventType.ElectronicCopyProvidedToPt;
        measureEvent.PatNum = PatCur.PatNum;
        measureEvent.MoreInfo = "";
        EhrMeasureEvents.insert(measureEvent);
        fillGrid();
    }

    private void butExport_Click(Object sender, EventArgs e) throws Exception {
        String ccd = "";
        try
        {
            ccd = EhrCCD.generateElectronicCopy(PatCur);
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
        recordRequestAndProvide();
        MessageBox.Show("Exported");
    }

    private void butSendEmail_Click(Object sender, EventArgs e) throws Exception {
        MsgBox.show(this,"Electronic copies cannot be emailed to patients due to security concerns.\r\n" + "Instruct the patient to access their information in the patient portal.\r\n" + "If you are trying to send the patient information directly to another provider, then go to Chart | EHR | Send/Receive summary of care.");
    }

    private void butShowXhtml_Click(Object sender, EventArgs e) throws Exception {
        String ccd = "";
        try
        {
            ccd = EhrCCD.generateElectronicCopy(PatCur);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        FormEhrSummaryOfCare.displayCCD(ccd);
    }

    private void butShowXml_Click(Object sender, EventArgs e) throws Exception {
        String ccd = "";
        try
        {
            ccd = EhrCCD.generateElectronicCopy(PatCur);
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
        if (gridMain.getSelectedIndices().Length < 1)
        {
            MessageBox.Show("Please select at least one record to delete.");
            return ;
        }
         
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            EhrMeasureEvents.Delete(listHistory[gridMain.getSelectedIndices()[i]].EhrMeasureEventNum);
        }
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
        this.butClose = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.butRequest = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butShowXhtml = new System.Windows.Forms.Button();
        this.butShowXml = new System.Windows.Forms.Button();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butExport = new System.Windows.Forms.Button();
        this.butSendEmail = new System.Windows.Forms.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(331, 448);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 7;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(27, 448);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 15;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butRequest
        //
        this.butRequest.Location = new System.Drawing.Point(29, 12);
        this.butRequest.Name = "butRequest";
        this.butRequest.Size = new System.Drawing.Size(103, 23);
        this.butRequest.TabIndex = 17;
        this.butRequest.Text = "Requested";
        this.butRequest.UseVisualStyleBackColor = true;
        this.butRequest.Click += new System.EventHandler(this.butRequest_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(28, 149);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(378, 283);
        this.gridMain.TabIndex = 16;
        this.gridMain.setTitle("History");
        this.gridMain.setTranslationName(null);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(135, 14);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(74, 18);
        this.label1.TabIndex = 18;
        this.label1.Text = "(optional)";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butShowXhtml);
        this.groupBox1.Controls.Add(this.butShowXml);
        this.groupBox1.Location = new System.Drawing.Point(258, 48);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(103, 77);
        this.groupBox1.TabIndex = 21;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Show";
        //
        // butShowXhtml
        //
        this.butShowXhtml.Location = new System.Drawing.Point(10, 19);
        this.butShowXhtml.Name = "butShowXhtml";
        this.butShowXhtml.Size = new System.Drawing.Size(84, 23);
        this.butShowXhtml.TabIndex = 18;
        this.butShowXhtml.Text = "Show xhtml";
        this.butShowXhtml.UseVisualStyleBackColor = true;
        this.butShowXhtml.Click += new System.EventHandler(this.butShowXhtml_Click);
        //
        // butShowXml
        //
        this.butShowXml.Location = new System.Drawing.Point(10, 46);
        this.butShowXml.Name = "butShowXml";
        this.butShowXml.Size = new System.Drawing.Size(84, 23);
        this.butShowXml.TabIndex = 19;
        this.butShowXml.Text = "Show xml";
        this.butShowXml.UseVisualStyleBackColor = true;
        this.butShowXml.Click += new System.EventHandler(this.butShowXml_Click);
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.label2);
        this.groupBox3.Controls.Add(this.butExport);
        this.groupBox3.Controls.Add(this.butSendEmail);
        this.groupBox3.Location = new System.Drawing.Point(28, 48);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(201, 91);
        this.groupBox3.TabIndex = 24;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Provide Electronic Copy to Patient";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 45);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(192, 43);
        this.label2.TabIndex = 23;
        this.label2.Text = "includes 2 files:\r\nccd.xml - the data\r\nccd.xsl - for human readable viewing";
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
        // FormElectronicCopy
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(437, 483);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butRequest);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butClose);
        this.Name = "FormElectronicCopy";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Electronic Copy for Patient";
        this.Load += new System.EventHandler(this.FormElectronicCopy_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox3.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butRequest = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butShowXhtml = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butShowXml = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butExport = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butSendEmail = new System.Windows.Forms.Button();
}


