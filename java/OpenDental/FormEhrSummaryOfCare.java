//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.System.StringSupport;
import OpenDental.FormEHR;
import OpenDental.FormEhrSummaryCcdEdit;
import OpenDental.FormEmailInbox;
import OpenDental.FormEmailMessageEdit;
import OpenDental.FormReferralsPatient;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrCCD;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.EhrSummaryCcd;
import OpenDentBusiness.EhrSummaryCcds;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.EmailSentOrReceived;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Patient;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrSummaryOfCare  extends Form 
{

    public Patient PatCur;
    private List<EhrMeasureEvent> listHistorySent = new List<EhrMeasureEvent>();
    private List<EhrSummaryCcd> listCcdRec = new List<EhrSummaryCcd>();
    public FormEhrSummaryOfCare() throws Exception {
        initializeComponent();
    }

    private void formSummaryOfCare_Load(Object sender, EventArgs e) throws Exception {
        fillGridSent();
        fillGridRec();
    }

    private void fillGridSent() throws Exception {
        gridSent.beginUpdate();
        gridSent.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("DateTime", 140, HorizontalAlignment.Center);
        gridSent.getColumns().add(col);
        listHistorySent = EhrMeasureEvents.refreshByType(PatCur.PatNum,EhrMeasureEventType.SummaryOfCareProvidedToDr);
        gridSent.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listHistorySent.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listHistorySent[i].DateTEvent.ToString());
            gridSent.getRows().add(row);
        }
        gridSent.endUpdate();
    }

    private void fillGridRec() throws Exception {
        gridRec.beginUpdate();
        gridRec.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("DateTime", 140, HorizontalAlignment.Center);
        gridRec.getColumns().add(col);
        listCcdRec = EhrSummaryCcds.refresh(PatCur.PatNum);
        gridRec.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listCcdRec.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listCcdRec[i].DateSummary.ToShortDateString());
            gridRec.getRows().add(row);
        }
        gridRec.endUpdate();
    }

    private void gridRec_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        String xmltext = listCcdRec[gridRec.getSelectedIndex()].ContentSummary;
        displayCCD(xmltext,PatCur);
    }

    /**
    * Returns true if user performed a print job on the CCD.  Cannot be moved to OpenDentBusiness/Misc/EhrCCD.cs, because this function uses windows UI components.
    * Strictly used for displaying CCD messages.  Will not allow user to reconcile meds, problems, or allergies into the patient's account.
    */
    public static boolean displayCCD(String strXmlCCD) throws Exception {
        return displayCCD(strXmlCCD,null);
    }

    public static boolean displayCCD(String strXmlCCD, Patient patCur) throws Exception {
        return displayCCD(strXmlCCD,patCur,"");
    }

    /**
    * Returns true if user performed a print job on the CCD.  Cannot be moved to OpenDentBusiness/Misc/EhrCCD.cs, because this function uses windows UI components.
    * Pass in a valid patient if the CCD is being displayed to reconcile (incoporate into patient record) medications and/or problems and/or allergies.
    * patCur can be null, or patCur.PatNum can be 0, to hide the three reconcile buttons.
    * strXmlCCD is the actual text of the CCD.
    * strAlterateFilPathXslCCD is a full file path to an alternative style sheet.
    * This file will only be used in the case where the EHR dll version of the stylesheet couldn not be loaded.
    * If neither method works for attaining the stylesheet then an excption will be thrown.
    */
    public static boolean displayCCD(String strXmlCCD, Patient patCur, String strAlterateFilPathXslCCD) throws Exception {
        //string xmltext=GetSampleMissingStylesheet();
        XmlDocument doc = new XmlDocument();
        try
        {
            doc.LoadXml(strXmlCCD);
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new XmlException("Invalid XML");
        }

        String xmlFileName = "";
        String xslFileName = "";
        String xslContents = "";
        if (StringSupport.equals(doc.DocumentElement.Name.ToLower(), "clinicaldocument"))
        {
            //CCD, CCDA, and C32.
            xmlFileName = "ccd.xml";
            xslFileName = "ccd.xsl";
            xslContents = FormEHR.getEhrResource("CCD");
            if (StringSupport.equals(xslContents, ""))
            {
                //XSL load from EHR dll failed so see if caller provided an alternative
                if (!StringSupport.equals(strAlterateFilPathXslCCD, ""))
                {
                    //alternative XSL file was provided so use that for our stylesheet
                    xslContents = File.ReadAllText(strAlterateFilPathXslCCD);
                }
                 
            }
             
            if (StringSupport.equals(xslContents, ""))
            {
                throw new Exception("No stylesheet found");
            }
             
        }
        else //one last check to see if we succeeded in finding a stylesheet
        if (StringSupport.equals(doc.DocumentElement.Name.ToLower(), "continuityofcarerecord") || StringSupport.equals(doc.DocumentElement.Name.ToLower(), "ccr:continuityofcarerecord"))
        {
            //CCR
            xmlFileName = "ccr.xml";
            xslFileName = "ccr.xsl";
            xslContents = FormEHR.getEhrResource("CCR");
        }
        else
        {
            MessageBox.Show("This is not a valid CCD, CCDA, CCR, or C32 message.  Only the raw text will be shown");
            MessageBox.Show(strXmlCCD);
            return false;
        }  
        XmlNode node = doc.SelectSingleNode("/processing-instruction(\"xml-stylesheet\")");
        if (node == null)
        {
            //document does not contain any stylesheet instruction, so add one
            XmlProcessingInstruction pi = doc.CreateProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"" + xslFileName + "\"");
            doc.InsertAfter(pi, doc.ChildNodes[0]);
        }
        else
        {
            //alter the existing instruction
            XmlProcessingInstruction pi = (XmlProcessingInstruction)node;
            pi.Value = "type=\"text/xsl\" href=\"" + xslFileName + "\"";
        } 
        File.WriteAllText(Path.Combine(Path.GetTempPath(), xmlFileName), doc.InnerXml.ToString());
        File.WriteAllText(Path.Combine(Path.GetTempPath(), xslFileName), xslContents);
        FormEhrSummaryCcdEdit formESCD = new FormEhrSummaryCcdEdit(Path.Combine(Path.GetTempPath(), xmlFileName), patCur);
        formESCD.ShowDialog();
        return formESCD.DidPrint;
    }

    private void butExport_Click(Object sender, EventArgs e) throws Exception {
        FormReferralsPatient FormRP = new FormReferralsPatient();
        FormRP.PatNum = PatCur.PatNum;
        FormRP.IsSelectionMode = true;
        if (FormRP.ShowDialog() == DialogResult.Cancel)
        {
            MessageBox.Show("Summary of Care not exported.");
            return ;
        }
         
        String ccd = "";
        try
        {
            ccd = EhrCCD.generateSummaryOfCare(PatCur);
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
        newMeasureEvent.EventType = EhrMeasureEventType.SummaryOfCareProvidedToDr;
        newMeasureEvent.PatNum = PatCur.PatNum;
        newMeasureEvent.FKey = FormRP.RefAttachNum;
        //Can be 0 if user didn't pick a referral for some reason.
        long fkey = EhrMeasureEvents.insert(newMeasureEvent);
        newMeasureEvent = new EhrMeasureEvent();
        newMeasureEvent.DateTEvent = DateTime.Now;
        newMeasureEvent.FKey = fkey;
        newMeasureEvent.EventType = EhrMeasureEventType.SummaryOfCareProvidedToDrElectronic;
        newMeasureEvent.PatNum = PatCur.PatNum;
        newMeasureEvent.FKey = FormRP.RefAttachNum;
        //Can be 0 if user didn't pick a referral for some reason.
        EhrMeasureEvents.insert(newMeasureEvent);
        fillGridSent();
        MessageBox.Show("Exported");
    }

    private void butSendEmail_Click(Object sender, EventArgs e) throws Exception {
        FormReferralsPatient FormRP = new FormReferralsPatient();
        FormRP.PatNum = PatCur.PatNum;
        FormRP.IsSelectionMode = true;
        if (FormRP.ShowDialog() == DialogResult.Cancel)
        {
            MessageBox.Show("Summary of Care not exported.");
            return ;
        }
         
        String ccd = "";
        try
        {
            ccd = EhrCCD.generateSummaryOfCare(PatCur);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        Cursor = Cursors.WaitCursor;
        EmailAddress emailAddressFrom = EmailAddresses.GetByClinic(0);
        //Default for clinic/practice.
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.PatNum = PatCur.PatNum;
        emailMessage.MsgDateTime = DateTime.Now;
        emailMessage.SentOrReceived = EmailSentOrReceived.Neither;
        //To force FormEmailMessageEdit into "compose" mode.
        emailMessage.FromAddress = emailAddressFrom.EmailUsername;
        //Cannot be emailAddressFrom.SenderAddress, because it would cause encryption to fail.
        emailMessage.ToAddress = "";
        //User must set inside of FormEmailMessageEdit
        emailMessage.Subject = "Summary of Care";
        emailMessage.BodyText = "Summary of Care";
        try
        {
            EmailMessages.createAttachmentFromText(emailMessage,ccd,"ccd.xml");
            EmailMessages.createAttachmentFromText(emailMessage,FormEHR.getEhrResource("CCD"),"ccd.xsl");
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        EmailMessages.insert(emailMessage);
        FormEmailMessageEdit formE = new FormEmailMessageEdit(emailMessage);
        if (formE.ShowDialog() == DialogResult.OK)
        {
            EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
            newMeasureEvent.DateTEvent = DateTime.Now;
            newMeasureEvent.EventType = EhrMeasureEventType.SummaryOfCareProvidedToDr;
            newMeasureEvent.PatNum = PatCur.PatNum;
            long fkey = EhrMeasureEvents.insert(newMeasureEvent);
            newMeasureEvent = new EhrMeasureEvent();
            newMeasureEvent.DateTEvent = DateTime.Now;
            newMeasureEvent.FKey = fkey;
            newMeasureEvent.EventType = EhrMeasureEventType.SummaryOfCareProvidedToDrElectronic;
            newMeasureEvent.PatNum = PatCur.PatNum;
            newMeasureEvent.FKey = FormRP.RefAttachNum;
            //Can be 0 if user didn't pick a referral for some reason.
            EhrMeasureEvents.insert(newMeasureEvent);
            fillGridSent();
        }
         
        Cursor = Cursors.Default;
    }

    private void butShowXhtml_Click(Object sender, EventArgs e) throws Exception {
        String ccd = "";
        try
        {
            ccd = EhrCCD.generateSummaryOfCare(PatCur);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        boolean didPrint = displayCCD(ccd);
        if (didPrint)
        {
            //we are printing a ccd so add new measure event.
            EhrMeasureEvent measureEvent = new EhrMeasureEvent();
            measureEvent.DateTEvent = DateTime.Now;
            measureEvent.EventType = EhrMeasureEventType.SummaryOfCareProvidedToDr;
            measureEvent.PatNum = PatCur.PatNum;
            EhrMeasureEvents.insert(measureEvent);
            fillGridSent();
        }
         
    }

    private void butShowXml_Click(Object sender, EventArgs e) throws Exception {
        String ccd = "";
        try
        {
            ccd = EhrCCD.generateSummaryOfCare(PatCur);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(ccd);
        msgbox.ShowDialog();
    }

    private void butRecEmail_Click(Object sender, EventArgs e) throws Exception {
        FormEmailInbox form = new FormEmailInbox();
        form.ShowDialog();
    }

    private void butRecFile_Click(Object sender, EventArgs e) throws Exception {
        OpenFileDialog dlg = new OpenFileDialog();
        DialogResult result = dlg.ShowDialog();
        if (result != DialogResult.OK)
        {
            return ;
        }
         
        String text = File.ReadAllText(dlg.FileName);
        EhrSummaryCcd ehrSummaryCcd = new EhrSummaryCcd();
        ehrSummaryCcd.ContentSummary = text;
        ehrSummaryCcd.DateSummary = DateTime.Today;
        ehrSummaryCcd.PatNum = PatCur.PatNum;
        EhrSummaryCcds.insert(ehrSummaryCcd);
        fillGridRec();
        displayCCD(text,PatCur);
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridSent.getSelectedIndices().Length < 1)
        {
            MessageBox.Show("Please select at least one record to delete.");
            return ;
        }
         
        for (int i = 0;i < gridSent.getSelectedIndices().Length;i++)
        {
            EhrMeasureEvents.Delete(listHistorySent[gridSent.getSelectedIndices()[i]].EhrMeasureEventNum);
        }
        fillGridSent();
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
        this.butRecEmail = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.butShowXhtml = new System.Windows.Forms.Button();
        this.butShowXml = new System.Windows.Forms.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.gridRec = new OpenDental.UI.ODGrid();
        this.gridSent = new OpenDental.UI.ODGrid();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.butRecFile = new System.Windows.Forms.Button();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butExport = new System.Windows.Forms.Button();
        this.butSendEmail = new System.Windows.Forms.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(343, 401);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 7;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butRecEmail
        //
        this.butRecEmail.Location = new System.Drawing.Point(103, 19);
        this.butRecEmail.Name = "butRecEmail";
        this.butRecEmail.Size = new System.Drawing.Size(82, 23);
        this.butRecEmail.TabIndex = 10;
        this.butRecEmail.Text = "Email";
        this.butRecEmail.UseVisualStyleBackColor = true;
        this.butRecEmail.Click += new System.EventHandler(this.butRecEmail_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(27, 401);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 15;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
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
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butShowXhtml);
        this.groupBox1.Controls.Add(this.butShowXml);
        this.groupBox1.Location = new System.Drawing.Point(20, 100);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(201, 50);
        this.groupBox1.TabIndex = 20;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Show";
        //
        // gridRec
        //
        this.gridRec.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridRec.setHScrollVisible(false);
        this.gridRec.Location = new System.Drawing.Point(240, 65);
        this.gridRec.Name = "gridRec";
        this.gridRec.setScrollValue(0);
        this.gridRec.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridRec.Size = new System.Drawing.Size(178, 323);
        this.gridRec.TabIndex = 17;
        this.gridRec.setTitle("Received");
        this.gridRec.setTranslationName(null);
        this.gridRec.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridRec.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridRec_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridSent
        //
        this.gridSent.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridSent.setHScrollVisible(false);
        this.gridSent.Location = new System.Drawing.Point(28, 154);
        this.gridSent.Name = "gridSent";
        this.gridSent.setScrollValue(0);
        this.gridSent.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridSent.Size = new System.Drawing.Size(185, 234);
        this.gridSent.TabIndex = 16;
        this.gridSent.setTitle("Sent");
        this.gridSent.setTranslationName(null);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.butRecFile);
        this.groupBox2.Controls.Add(this.butRecEmail);
        this.groupBox2.Location = new System.Drawing.Point(233, 7);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(192, 52);
        this.groupBox2.TabIndex = 21;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Receive by";
        //
        // butRecFile
        //
        this.butRecFile.Location = new System.Drawing.Point(7, 19);
        this.butRecFile.Name = "butRecFile";
        this.butRecFile.Size = new System.Drawing.Size(82, 23);
        this.butRecFile.TabIndex = 11;
        this.butRecFile.Text = "File Import";
        this.butRecFile.UseVisualStyleBackColor = true;
        this.butRecFile.Click += new System.EventHandler(this.butRecFile_Click);
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.label1);
        this.groupBox3.Controls.Add(this.butExport);
        this.groupBox3.Controls.Add(this.butSendEmail);
        this.groupBox3.Location = new System.Drawing.Point(20, 7);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(201, 91);
        this.groupBox3.TabIndex = 23;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Send Summary of Care to Doctor";
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(6, 45);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(192, 43);
        this.label1.TabIndex = 23;
        this.label1.Text = "includes 2 files:\r\nccd.xml - the data\r\nccd.xsl - for human readable viewing";
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
        // FormSummaryOfCare
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(441, 434);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridRec);
        this.Controls.Add(this.gridSent);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butClose);
        this.Name = "FormSummaryOfCare";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Summary of Care";
        this.Load += new System.EventHandler(this.FormSummaryOfCare_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox3.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butRecEmail = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridSent;
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridRec;
    private System.Windows.Forms.Button butShowXhtml = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butShowXml = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butRecFile = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butExport = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butSendEmail = new System.Windows.Forms.Button();
}
/*
		public static StringBuilder GenerateCCD(Patient pat) {
			XmlWriterSettings xmlSettings=new XmlWriterSettings();
			xmlSettings.Encoding=Encoding.UTF8;
			xmlSettings.OmitXmlDeclaration=true;
			xmlSettings.Indent=true;
			xmlSettings.IndentChars="   ";
			StringBuilder strBuilder=new StringBuilder();
			using(XmlWriter writer=XmlWriter.Create(strBuilder,xmlSettings)){
				//Begin Clinical Document
				writer.WriteRaw("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
				writer.WriteStartElement("ClinicalDocument","urn:hl7-org:v3");
					// Asserting use of the HL7 Guide for CDA R2 - CCD
					writer.WriteStartElement("typeId");
						writer.WriteAttributeString("extension","POCD_HD0000040");
						writer.WriteAttributeString("root","2.16.840.1.113883.1.3");
				  writer.WriteEndElement();
				//Michael:Do I need this here?
					//writer.WriteStartElement("templateId");
					//  writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1");
					//writer.WriteEndElement();
					// Header---------------------------------------------------------------------------------------------
					// 2.1 CCR Unique Identifier
				  writer.WriteStartElement("id");
				  writer.WriteEndElement();
					writer.WriteStartElement("code");
						writer.WriteAttributeString("code","34133-9");
						writer.WriteAttributeString("codeSystemName","LOINC");
						writer.WriteAttributeString("codeSystem","2.16.840.1.113883.6.1");
						writer.WriteAttributeString("displayName","Summary of episode note");
				  writer.WriteEndElement();
					writer.WriteStartElement("documentationOf");
						writer.WriteStartElement("serviceEvent");
							writer.WriteAttributeString("classCode","PCPR");
							string effectiveTimeHigh=DateTime.Now.ToString("yyyyMMddHHmmsszzz").Replace(":","");
							string effectiveTimeLow=pat.Birthdate.ToString("yyyyMMddHHmmsszzz").Replace(":","");
							writer.WriteStartElement("effectiveTime");
								writer.WriteStartElement("high");
									writer.WriteAttributeString("value",effectiveTimeHigh);
								writer.WriteEndElement();//End high
								writer.WriteStartElement("low");
									writer.WriteAttributeString("value",effectiveTimeLow);
								writer.WriteEndElement();//End low
							writer.WriteEndElement();//End effectiveTime
						writer.WriteEndElement();
					writer.WriteEndElement();//End documentationOf
					// 2.2 Language
					writer.WriteStartElement("languageCode");
						writer.WriteAttributeString("value","en-US");
					writer.WriteEndElement();
					// 2.3 Version
				  writer.WriteStartElement("templateId");
				    writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1");
				  writer.WriteEndElement();
					// 2.4 CCR Creation Date/Time
					writer.WriteStartElement("effectiveTime");
						string ccrCreationDateTime=DateTime.Now.ToString("yyyyMMddHHmmsszzz").Replace(":","");
						writer.WriteAttributeString("value",ccrCreationDateTime);
					writer.WriteEndElement();
					// 2.5 Patient
					writer.WriteStartElement("recordTarget");
						writer.WriteStartElement("patientRole");
							writer.WriteStartElement("id");
								writer.WriteAttributeString("value",pat.PatNum.ToString());
							writer.WriteEndElement();
							writer.WriteStartElement("addr");
								writer.WriteAttributeString("use","HP");
								writer.WriteStartElement("streetAddressLine");
									writer.WriteString(pat.Address.ToString());
								writer.WriteEndElement();
								writer.WriteStartElement("streetAddressLine");
									writer.WriteString(pat.Address2.ToString());
								writer.WriteEndElement();
								writer.WriteStartElement("city");
									writer.WriteString(pat.City.ToString());
								writer.WriteEndElement();
								writer.WriteStartElement("state");
									writer.WriteString(pat.State.ToString());
								writer.WriteEndElement();
								writer.WriteStartElement("country");
									writer.WriteString("");
								writer.WriteEndElement();
							writer.WriteEndElement();//End addr
							writer.WriteStartElement("patient");
								writer.WriteStartElement("name");
									writer.WriteAttributeString("use","L");
									writer.WriteStartElement("given");
										writer.WriteString(pat.FName.ToString());
									writer.WriteEndElement();
									writer.WriteStartElement("given");
										writer.WriteString(pat.MiddleI.ToString());
									writer.WriteEndElement();
									writer.WriteStartElement("family");
										writer.WriteString(pat.LName.ToString());
									writer.WriteEndElement();
									writer.WriteStartElement("suffix");
										writer.WriteAttributeString("qualifier","TITLE");
										writer.WriteString(pat.Title.ToString());
									writer.WriteEndElement();//End suffix
								writer.WriteEndElement();//End name
							writer.WriteEndElement();//End patient
						writer.WriteEndElement();//End patientRole
						writer.WriteStartElement("text");//The following text will be parsed as html with a style sheet to be human readable.
							writer.WriteStartElement("table");
								writer.WriteAttributeString("width","100%");
								writer.WriteAttributeString("border","1");
								writer.WriteStartElement("thead");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("th");
										  writer.WriteString("Name");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Date of Birth");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Gender");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Identification Number");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Identification Number Type");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Address/Phone");
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End thead
								writer.WriteStartElement("tbody");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("td");//Name
											writer.WriteString(pat.LName+", "+pat.FName+" "+pat.MiddleI);
										writer.WriteEndElement();
										writer.WriteStartElement("td");//DoB
											writer.WriteString(pat.Birthdate.ToShortDateString());
										writer.WriteEndElement();
										writer.WriteStartElement("td");//Gender
											writer.WriteString(pat.Gender.ToString());
										writer.WriteEndElement();
										writer.WriteStartElement("td");//PatNum
											writer.WriteString(pat.PatNum.ToString());
										writer.WriteEndElement();
										writer.WriteStartElement("td");//"Open Dental PatNum"
											writer.WriteString("Open Dental PatNum");
										writer.WriteEndElement();
										writer.WriteStartElement("td");//Address/Phone
											writer.WriteString(pat.Address+" "+pat.Address2+"\r\n"+pat.City+", "
												+pat.State+"\r\n"+pat.Zip+"\r\n"+pat.HmPhone);
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End tbody
							writer.WriteEndElement();//End table
						writer.WriteEndElement();//End text
					writer.WriteEndElement();//End recordTarget
					// 2.6 From
					writer.WriteStartElement("author");
						writer.WriteStartElement("assignedAuthor");
							writer.WriteStartElement("assignedPerson");
								writer.WriteElementString("name","Auto Generated");
							writer.WriteEndElement();//End assignedPerson
						writer.WriteEndElement();//End assignedAuthor
					writer.WriteEndElement();//End author
					// Body--------------------------------------------------------------------------------------------
					// 3.5 Problems
					List<Disease> listProblem=Diseases.Refresh(pat.PatNum);
					int condID=1;//used to set the CondID-# in the problem list of the html table in the text section of this document.
					ICD9 icd9;
					writer.WriteStartElement("component");
						writer.WriteComment("Problems");
						writer.WriteStartElement("section");
							writer.WriteStartElement("templateId");
								writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1.11");
								writer.WriteAttributeString("assigningAuthorityName","HL7 CCD");
							writer.WriteEndElement();
							writer.WriteComment("Problems section template");
							writer.WriteStartElement("code");
								writer.WriteAttributeString("code","11450-4");
								writer.WriteAttributeString("codeSystemName","LOINC");
								writer.WriteAttributeString("codeSystem","2.16.840.1.113883.6.1");
								writer.WriteAttributeString("displayName","Problem list");
						writer.WriteEndElement();
						writer.WriteStartElement("title");
							writer.WriteString("Problems");
						writer.WriteEndElement();
						writer.WriteStartElement("text");//The following text will be parsed as html with a style sheet to be human readable.
							writer.WriteStartElement("table");
								writer.WriteAttributeString("width","100%");
								writer.WriteAttributeString("border","1");
								writer.WriteStartElement("thead");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("th");
										  writer.WriteString("ICD-9 Code");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Patient Problem");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Date Diagnosed");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Status");
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End thead
								writer.WriteStartElement("tbody");
									for(int i=0;i<listProblem.Count;i++){
										if(listProblem[i].ICD9Num!=0){
											icd9=ICD9s.GetOne(listProblem[i].ICD9Num);
											writer.WriteStartElement("tr");
												writer.WriteAttributeString("ID","CondID-"+condID);
												writer.WriteStartElement("td");
												  writer.WriteString(icd9.ICD9Code.Substring(0,3)+"."+icd9.ICD9Code.Substring(3));
												writer.WriteEndElement();
												writer.WriteStartElement("td");
													writer.WriteString(icd9.Description);
												writer.WriteEndElement();
												writer.WriteStartElement("td");
													writer.WriteString(listProblem[i].DateStart.ToShortDateString());
												writer.WriteEndElement();
												writer.WriteStartElement("td");
													writer.WriteString(listProblem[i].ProbStatus.ToString());
												writer.WriteEndElement();
											writer.WriteEndElement();//End tr
										}
									}
								writer.WriteEndElement();//End tbody
							writer.WriteEndElement();//End table
						writer.WriteEndElement();//End text
					writer.WriteEndElement();//End Problem component
					// 3.8 Alerts (for Allergies)
					List<Allergy> listAllergy=Allergies.Refresh(pat.PatNum);
					AllergyDef allergyDef;
					Medication med;
					writer.WriteStartElement("component");
						writer.WriteComment("Alerts");
						writer.WriteStartElement("section");
							writer.WriteStartElement("templateId");
								writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1.2");
								writer.WriteAttributeString("assigningAuthorityName","HL7 CCD");
							writer.WriteEndElement();
							writer.WriteComment("Alerts section template");
							writer.WriteStartElement("code");
								writer.WriteAttributeString("code","48765-2");
								writer.WriteAttributeString("codeSystemName","LOINC");
								writer.WriteAttributeString("codeSystem","2.16.840.1.113883.6.1");
								writer.WriteAttributeString("displayName","Allergies, adverse reactions, alerts");
						writer.WriteEndElement();
						writer.WriteStartElement("title");
							writer.WriteString("Allergies and Adverse Reactions");
						writer.WriteEndElement();
						writer.WriteStartElement("text");//The following text will be parsed as html with a style sheet to be human readable.
							writer.WriteStartElement("table");
								writer.WriteAttributeString("width","100%");
								writer.WriteAttributeString("border","1");
								writer.WriteStartElement("thead");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("th");
										  writer.WriteString("SNOMED Allergy Type Code");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Medication/Agent Allergy");
										writer.WriteEndElement();//
										writer.WriteStartElement("th");
											writer.WriteString("Reaction");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Adverse Event Date");
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End thead
								writer.WriteStartElement("tbody");
									for(int i=0;i<listAllergy.Count;i++){
										allergyDef=AllergyDefs.GetOne(listAllergy[i].AllergyDefNum);
										writer.WriteStartElement("tr");
											writer.WriteStartElement("td");
											  writer.WriteString(AllergyDefs.GetSnomedAllergyDesc(allergyDef.Snomed));
											writer.WriteEndElement();
											writer.WriteStartElement("td");
												med=Medications.GetMedication(allergyDef.MedicationNum);
											  writer.WriteString(med.RxCui.ToString()+" - "+med.MedName);
											writer.WriteEndElement();
											writer.WriteStartElement("td");
												writer.WriteString(listAllergy[i].Reaction);
											writer.WriteEndElement();
											writer.WriteStartElement("td");
												writer.WriteString(listAllergy[i].DateAdverseReaction.ToShortDateString());
											writer.WriteEndElement();
										writer.WriteEndElement();//End tr
									}
								writer.WriteEndElement();//End tbody
							writer.WriteEndElement();//End table
						writer.WriteEndElement();//End text
					writer.WriteEndElement();//End Alerts component
					// 3.9 Medications
					List<MedicationPat> listMedPat=MedicationPats.Refresh(pat.PatNum,true);
					writer.WriteStartElement("component");
						writer.WriteComment("Medications");
						writer.WriteStartElement("section");
							writer.WriteStartElement("templateId");
								writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1.8");
								writer.WriteAttributeString("assigningAuthorityName","HL7 CCD");
							writer.WriteEndElement();
							writer.WriteComment("Medications section template");
							writer.WriteStartElement("code");
								writer.WriteAttributeString("code","10160-0");
								writer.WriteAttributeString("codeSystemName","LOINC");
								writer.WriteAttributeString("codeSystem","2.16.840.1.113883.6.1");
								writer.WriteAttributeString("displayName","History of medication use");
						writer.WriteEndElement();
						writer.WriteStartElement("title");
							writer.WriteString("Medications");
						writer.WriteEndElement();
						writer.WriteStartElement("text");//The following text will be parsed as html with a style sheet to be human readable.
							writer.WriteStartElement("table");
								writer.WriteAttributeString("width","100%");
								writer.WriteAttributeString("border","1");
								writer.WriteStartElement("thead");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("th");
										  writer.WriteString("RxNorm Code");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
										  writer.WriteString("Product");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
										  writer.WriteString("Generic Name");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Brand Name");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Instructions");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Date Started");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Status");
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End thead
								writer.WriteStartElement("tbody");
									for(int i=0;i<listMedPat.Count;i++){
										med=Medications.GetMedication(listMedPat[i].MedicationNum);
										writer.WriteStartElement("tr");
											writer.WriteStartElement("td");
											  writer.WriteString(med.RxCui.ToString());//RxNorm Code
											writer.WriteEndElement();
											writer.WriteStartElement("td");
											  writer.WriteString("Medication");//Product
											writer.WriteEndElement();
											writer.WriteStartElement("td");
											  writer.WriteString(Medications.GetGenericName(med.GenericNum));//Generic Name
											writer.WriteEndElement();
											writer.WriteStartElement("td");
											  writer.WriteString(med.MedName);//Brand Name
											writer.WriteEndElement();
											writer.WriteStartElement("td");
												writer.WriteString(listMedPat[i].PatNote);//Instruction
											writer.WriteEndElement();
											writer.WriteStartElement("td");
												writer.WriteString(listMedPat[i].DateStart.ToShortDateString());//Date Started
											writer.WriteEndElement();
											writer.WriteStartElement("td");
										writer.WriteString(MedicationPat.IsMedActive(listMedPat[i])?"Active":"Inactive");//Status
											writer.WriteEndElement();
										writer.WriteEndElement();//End tr
									}
								writer.WriteEndElement();//End tbody
							writer.WriteEndElement();//End table
						writer.WriteEndElement();//End text
					writer.WriteEndElement();//End Medication component
					// 3.13 Results
					List<LabResult> listLabResult=LabResults.GetAllForPatient(pat.PatNum);
					writer.WriteStartElement("component");
						writer.WriteComment("Results");
						writer.WriteStartElement("section");
							writer.WriteStartElement("templateId");
								writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1.14");
								writer.WriteAttributeString("assigningAuthorityName","HL7 CCD");
							writer.WriteEndElement();
							writer.WriteComment("Relevant diagnostic tests and/or labratory data");
							writer.WriteStartElement("code");
								writer.WriteAttributeString("code","30954-2");
								writer.WriteAttributeString("codeSystemName","LOINC");
								writer.WriteAttributeString("codeSystem","2.16.840.1.113883.6.1");
								writer.WriteAttributeString("displayName","Allergies, adverse reactions, alerts");
						writer.WriteEndElement();
						writer.WriteStartElement("title");
							writer.WriteString("Results");
						writer.WriteEndElement();
						writer.WriteStartElement("text");//The following text will be parsed as html with a style sheet to be human readable.
							writer.WriteStartElement("table");
								writer.WriteAttributeString("width","100%");
								writer.WriteAttributeString("border","1");
								writer.WriteStartElement("thead");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("th");
										  writer.WriteString("LOINC Code");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Test");
										writer.WriteEndElement();//
										writer.WriteStartElement("th");
											writer.WriteString("Result");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Abnormal Flag");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Date Performed");
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End thead
								writer.WriteStartElement("tbody");
									for(int i=0;i<listLabResult.Count;i++){
										writer.WriteStartElement("tr");
										writer.WriteStartElement("td");
										writer.WriteString(listLabResult[i].TestID.ToString());
										writer.WriteEndElement();
										writer.WriteStartElement("td");
										writer.WriteString(listLabResult[i].TestName);
										writer.WriteEndElement();
										writer.WriteStartElement("td");
										writer.WriteString(listLabResult[i].AbnormalFlag.ToString());
										writer.WriteEndElement();
										writer.WriteStartElement("td");
										writer.WriteString(listLabResult[i].DateTimeTest.ToShortDateString());
										writer.WriteEndElement();
										writer.WriteEndElement();//End tr
									}
								writer.WriteEndElement();//End tbody
							writer.WriteEndElement();//End table
						writer.WriteEndElement();//End text
					writer.WriteEndElement();//End Diagnostic Test Results component
				writer.WriteEndElement();
				return strBuilder;
			}
		}*/

/*
		public static StringBuilder GenerateCCD(Patient pat) {
			XmlWriterSettings xmlSettings=new XmlWriterSettings();
			xmlSettings.Encoding=Encoding.UTF8;
			xmlSettings.OmitXmlDeclaration=true;
			xmlSettings.Indent=true;
			xmlSettings.IndentChars="   ";
			StringBuilder strBuilder=new StringBuilder();
			using(XmlWriter writer=XmlWriter.Create(strBuilder,xmlSettings)){
				//Begin Clinical Document
				writer.WriteRaw("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
				writer.WriteStartElement("ClinicalDocument","urn:hl7-org:v3");
					// Asserting use of the HL7 Guide for CDA R2 - CCD
					writer.WriteStartElement("typeId");
						writer.WriteAttributeString("extension","POCD_HD0000040");
						writer.WriteAttributeString("root","2.16.840.1.113883.1.3");
				  writer.WriteEndElement();
				//Michael:Do I need this here?
					//writer.WriteStartElement("templateId");
					//  writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1");
					//writer.WriteEndElement();
					// Header---------------------------------------------------------------------------------------------
					// 2.1 CCR Unique Identifier
				  writer.WriteStartElement("id");
				  writer.WriteEndElement();
					writer.WriteStartElement("code");
						writer.WriteAttributeString("code","34133-9");
						writer.WriteAttributeString("codeSystemName","LOINC");
						writer.WriteAttributeString("codeSystem","2.16.840.1.113883.6.1");
						writer.WriteAttributeString("displayName","Summary of episode note");
				  writer.WriteEndElement();
					writer.WriteStartElement("documentationOf");
						writer.WriteStartElement("serviceEvent");
							writer.WriteAttributeString("classCode","PCPR");
							string effectiveTimeHigh=DateTime.Now.ToString("yyyyMMddHHmmsszzz").Replace(":","");
							string effectiveTimeLow=pat.Birthdate.ToString("yyyyMMddHHmmsszzz").Replace(":","");
							writer.WriteStartElement("effectiveTime");
								writer.WriteStartElement("high");
									writer.WriteAttributeString("value",effectiveTimeHigh);
								writer.WriteEndElement();//End high
								writer.WriteStartElement("low");
									writer.WriteAttributeString("value",effectiveTimeLow);
								writer.WriteEndElement();//End low
							writer.WriteEndElement();//End effectiveTime
						writer.WriteEndElement();
					writer.WriteEndElement();//End documentationOf
					// 2.2 Language
					writer.WriteStartElement("languageCode");
						writer.WriteAttributeString("value","en-US");
					writer.WriteEndElement();
					// 2.3 Version
				  writer.WriteStartElement("templateId");
				    writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1");
				  writer.WriteEndElement();
					// 2.4 CCR Creation Date/Time
					writer.WriteStartElement("effectiveTime");
						string ccrCreationDateTime=DateTime.Now.ToString("yyyyMMddHHmmsszzz").Replace(":","");
						writer.WriteAttributeString("value",ccrCreationDateTime);
					writer.WriteEndElement();
					// 2.5 Patient
					writer.WriteStartElement("recordTarget");
						writer.WriteStartElement("patientRole");
							writer.WriteStartElement("id");
								writer.WriteAttributeString("value",pat.PatNum.ToString());
							writer.WriteEndElement();
							writer.WriteStartElement("addr");
								writer.WriteAttributeString("use","HP");
								writer.WriteStartElement("streetAddressLine");
									writer.WriteString(pat.Address.ToString());
								writer.WriteEndElement();
								writer.WriteStartElement("streetAddressLine");
									writer.WriteString(pat.Address2.ToString());
								writer.WriteEndElement();
								writer.WriteStartElement("city");
									writer.WriteString(pat.City.ToString());
								writer.WriteEndElement();
								writer.WriteStartElement("state");
									writer.WriteString(pat.State.ToString());
								writer.WriteEndElement();
								writer.WriteStartElement("country");
									writer.WriteString("");
								writer.WriteEndElement();
							writer.WriteEndElement();//End addr
							writer.WriteStartElement("patient");
								writer.WriteStartElement("name");
									writer.WriteAttributeString("use","L");
									writer.WriteStartElement("given");
										writer.WriteString(pat.FName.ToString());
									writer.WriteEndElement();
									writer.WriteStartElement("given");
										writer.WriteString(pat.MiddleI.ToString());
									writer.WriteEndElement();
									writer.WriteStartElement("family");
										writer.WriteString(pat.LName.ToString());
									writer.WriteEndElement();
									writer.WriteStartElement("suffix");
										writer.WriteAttributeString("qualifier","TITLE");
										writer.WriteString(pat.Title.ToString());
									writer.WriteEndElement();//End suffix
								writer.WriteEndElement();//End name
							writer.WriteEndElement();//End patient
						writer.WriteEndElement();//End patientRole
						writer.WriteStartElement("text");//The following text will be parsed as html with a style sheet to be human readable.
							writer.WriteStartElement("table");
								writer.WriteAttributeString("width","100%");
								writer.WriteAttributeString("border","1");
								writer.WriteStartElement("thead");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("th");
										  writer.WriteString("Name");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Date of Birth");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Gender");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Identification Number");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Identification Number Type");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Address/Phone");
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End thead
								writer.WriteStartElement("tbody");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("td");//Name
											writer.WriteString(pat.LName+", "+pat.FName+" "+pat.MiddleI);
										writer.WriteEndElement();
										writer.WriteStartElement("td");//DoB
											writer.WriteString(pat.Birthdate.ToShortDateString());
										writer.WriteEndElement();
										writer.WriteStartElement("td");//Gender
											writer.WriteString(pat.Gender.ToString());
										writer.WriteEndElement();
										writer.WriteStartElement("td");//PatNum
											writer.WriteString(pat.PatNum.ToString());
										writer.WriteEndElement();
										writer.WriteStartElement("td");//"Open Dental PatNum"
											writer.WriteString("Open Dental PatNum");
										writer.WriteEndElement();
										writer.WriteStartElement("td");//Address/Phone
											writer.WriteString(pat.Address+" "+pat.Address2+"\r\n"+pat.City+", "
												+pat.State+"\r\n"+pat.Zip+"\r\n"+pat.HmPhone);
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End tbody
							writer.WriteEndElement();//End table
						writer.WriteEndElement();//End text
					writer.WriteEndElement();//End recordTarget
					// 2.6 From
					writer.WriteStartElement("author");
						writer.WriteStartElement("assignedAuthor");
							writer.WriteStartElement("assignedPerson");
								writer.WriteElementString("name","Auto Generated");
							writer.WriteEndElement();//End assignedPerson
						writer.WriteEndElement();//End assignedAuthor
					writer.WriteEndElement();//End author
					// Body--------------------------------------------------------------------------------------------
					// 3.5 Problems
					List<Disease> listProblem=Diseases.Refresh(pat.PatNum);
					int condID=1;//used to set the CondID-# in the problem list of the html table in the text section of this document.
					ICD9 icd9;
					writer.WriteStartElement("component");
						writer.WriteComment("Problems");
						writer.WriteStartElement("section");
							writer.WriteStartElement("templateId");
								writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1.11");
								writer.WriteAttributeString("assigningAuthorityName","HL7 CCD");
							writer.WriteEndElement();
							writer.WriteComment("Problems section template");
							writer.WriteStartElement("code");
								writer.WriteAttributeString("code","11450-4");
								writer.WriteAttributeString("codeSystemName","LOINC");
								writer.WriteAttributeString("codeSystem","2.16.840.1.113883.6.1");
								writer.WriteAttributeString("displayName","Problem list");
						writer.WriteEndElement();
						writer.WriteStartElement("title");
							writer.WriteString("Problems");
						writer.WriteEndElement();
						writer.WriteStartElement("text");//The following text will be parsed as html with a style sheet to be human readable.
							writer.WriteStartElement("table");
								writer.WriteAttributeString("width","100%");
								writer.WriteAttributeString("border","1");
								writer.WriteStartElement("thead");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("th");
										  writer.WriteString("ICD-9 Code");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Patient Problem");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Date Diagnosed");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Status");
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End thead
								writer.WriteStartElement("tbody");
									for(int i=0;i<listProblem.Count;i++){
										if(listProblem[i].ICD9Num!=0){
											icd9=ICD9s.GetOne(listProblem[i].ICD9Num);
											writer.WriteStartElement("tr");
												writer.WriteAttributeString("ID","CondID-"+condID);
												writer.WriteStartElement("td");
												  writer.WriteString(icd9.ICD9Code.Substring(0,3)+"."+icd9.ICD9Code.Substring(3));
												writer.WriteEndElement();
												writer.WriteStartElement("td");
													writer.WriteString(icd9.Description);
												writer.WriteEndElement();
												writer.WriteStartElement("td");
													writer.WriteString(listProblem[i].DateStart.ToShortDateString());
												writer.WriteEndElement();
												writer.WriteStartElement("td");
													writer.WriteString(listProblem[i].ProbStatus.ToString());
												writer.WriteEndElement();
											writer.WriteEndElement();//End tr
										}
									}
								writer.WriteEndElement();//End tbody
							writer.WriteEndElement();//End table
						writer.WriteEndElement();//End text
					writer.WriteEndElement();//End Problem component
					// 3.8 Alerts (for Allergies)
					List<Allergy> listAllergy=Allergies.Refresh(pat.PatNum);
					AllergyDef allergyDef;
					Medication med;
					writer.WriteStartElement("component");
						writer.WriteComment("Alerts");
						writer.WriteStartElement("section");
							writer.WriteStartElement("templateId");
								writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1.2");
								writer.WriteAttributeString("assigningAuthorityName","HL7 CCD");
							writer.WriteEndElement();
							writer.WriteComment("Alerts section template");
							writer.WriteStartElement("code");
								writer.WriteAttributeString("code","48765-2");
								writer.WriteAttributeString("codeSystemName","LOINC");
								writer.WriteAttributeString("codeSystem","2.16.840.1.113883.6.1");
								writer.WriteAttributeString("displayName","Allergies, adverse reactions, alerts");
						writer.WriteEndElement();
						writer.WriteStartElement("title");
							writer.WriteString("Allergies and Adverse Reactions");
						writer.WriteEndElement();
						writer.WriteStartElement("text");//The following text will be parsed as html with a style sheet to be human readable.
							writer.WriteStartElement("table");
								writer.WriteAttributeString("width","100%");
								writer.WriteAttributeString("border","1");
								writer.WriteStartElement("thead");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("th");
										  writer.WriteString("SNOMED Allergy Type Code");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Medication/Agent Allergy");
										writer.WriteEndElement();//
										writer.WriteStartElement("th");
											writer.WriteString("Reaction");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Adverse Event Date");
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End thead
								writer.WriteStartElement("tbody");
									for(int i=0;i<listAllergy.Count;i++){
										allergyDef=AllergyDefs.GetOne(listAllergy[i].AllergyDefNum);
										writer.WriteStartElement("tr");
											writer.WriteStartElement("td");
											  writer.WriteString(AllergyDefs.GetSnomedAllergyDesc(allergyDef.Snomed));
											writer.WriteEndElement();
											writer.WriteStartElement("td");
												med=Medications.GetMedication(allergyDef.MedicationNum);
											  writer.WriteString(med.RxCui.ToString()+" - "+med.MedName);
											writer.WriteEndElement();
											writer.WriteStartElement("td");
												writer.WriteString(listAllergy[i].Reaction);
											writer.WriteEndElement();
											writer.WriteStartElement("td");
												writer.WriteString(listAllergy[i].DateAdverseReaction.ToShortDateString());
											writer.WriteEndElement();
										writer.WriteEndElement();//End tr
									}
								writer.WriteEndElement();//End tbody
							writer.WriteEndElement();//End table
						writer.WriteEndElement();//End text
					writer.WriteEndElement();//End Alerts component
					// 3.9 Medications
					List<MedicationPat> listMedPat=MedicationPats.Refresh(pat.PatNum,true);
					writer.WriteStartElement("component");
						writer.WriteComment("Medications");
						writer.WriteStartElement("section");
							writer.WriteStartElement("templateId");
								writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1.8");
								writer.WriteAttributeString("assigningAuthorityName","HL7 CCD");
							writer.WriteEndElement();
							writer.WriteComment("Medications section template");
							writer.WriteStartElement("code");
								writer.WriteAttributeString("code","10160-0");
								writer.WriteAttributeString("codeSystemName","LOINC");
								writer.WriteAttributeString("codeSystem","2.16.840.1.113883.6.1");
								writer.WriteAttributeString("displayName","History of medication use");
						writer.WriteEndElement();
						writer.WriteStartElement("title");
							writer.WriteString("Medications");
						writer.WriteEndElement();
						writer.WriteStartElement("text");//The following text will be parsed as html with a style sheet to be human readable.
							writer.WriteStartElement("table");
								writer.WriteAttributeString("width","100%");
								writer.WriteAttributeString("border","1");
								writer.WriteStartElement("thead");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("th");
										  writer.WriteString("RxNorm Code");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
										  writer.WriteString("Product");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
										  writer.WriteString("Generic Name");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Brand Name");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Instructions");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Date Started");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Status");
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End thead
								writer.WriteStartElement("tbody");
									for(int i=0;i<listMedPat.Count;i++){
										med=Medications.GetMedication(listMedPat[i].MedicationNum);
										writer.WriteStartElement("tr");
											writer.WriteStartElement("td");
											  writer.WriteString(med.RxCui.ToString());//RxNorm Code
											writer.WriteEndElement();
											writer.WriteStartElement("td");
											  writer.WriteString("Medication");//Product
											writer.WriteEndElement();
											writer.WriteStartElement("td");
											  writer.WriteString(Medications.GetGenericName(med.GenericNum));//Generic Name
											writer.WriteEndElement();
											writer.WriteStartElement("td");
											  writer.WriteString(med.MedName);//Brand Name
											writer.WriteEndElement();
											writer.WriteStartElement("td");
												writer.WriteString(listMedPat[i].PatNote);//Instruction
											writer.WriteEndElement();
											writer.WriteStartElement("td");
												writer.WriteString(listMedPat[i].DateStart.ToShortDateString());//Date Started
											writer.WriteEndElement();
											writer.WriteStartElement("td");
										writer.WriteString(MedicationPat.IsMedActive(listMedPat[i])?"Active":"Inactive");//Status
											writer.WriteEndElement();
										writer.WriteEndElement();//End tr
									}
								writer.WriteEndElement();//End tbody
							writer.WriteEndElement();//End table
						writer.WriteEndElement();//End text
					writer.WriteEndElement();//End Medication component
					// 3.13 Results
					List<LabResult> listLabResult=LabResults.GetAllForPatient(pat.PatNum);
					writer.WriteStartElement("component");
						writer.WriteComment("Results");
						writer.WriteStartElement("section");
							writer.WriteStartElement("templateId");
								writer.WriteAttributeString("root","2.16.840.1.113883.10.20.1.14");
								writer.WriteAttributeString("assigningAuthorityName","HL7 CCD");
							writer.WriteEndElement();
							writer.WriteComment("Relevant diagnostic tests and/or labratory data");
							writer.WriteStartElement("code");
								writer.WriteAttributeString("code","30954-2");
								writer.WriteAttributeString("codeSystemName","LOINC");
								writer.WriteAttributeString("codeSystem","2.16.840.1.113883.6.1");
								writer.WriteAttributeString("displayName","Allergies, adverse reactions, alerts");
						writer.WriteEndElement();
						writer.WriteStartElement("title");
							writer.WriteString("Results");
						writer.WriteEndElement();
						writer.WriteStartElement("text");//The following text will be parsed as html with a style sheet to be human readable.
							writer.WriteStartElement("table");
								writer.WriteAttributeString("width","100%");
								writer.WriteAttributeString("border","1");
								writer.WriteStartElement("thead");
									writer.WriteStartElement("tr");
										writer.WriteStartElement("th");
										  writer.WriteString("LOINC Code");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Test");
										writer.WriteEndElement();//
										writer.WriteStartElement("th");
											writer.WriteString("Result");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Abnormal Flag");
										writer.WriteEndElement();
										writer.WriteStartElement("th");
											writer.WriteString("Date Performed");
										writer.WriteEndElement();
									writer.WriteEndElement();//End tr
								writer.WriteEndElement();//End thead
								writer.WriteStartElement("tbody");
									for(int i=0;i<listLabResult.Count;i++){
										writer.WriteStartElement("tr");
										writer.WriteStartElement("td");
										writer.WriteString(listLabResult[i].TestID.ToString());
										writer.WriteEndElement();
										writer.WriteStartElement("td");
										writer.WriteString(listLabResult[i].TestName);
										writer.WriteEndElement();
										writer.WriteStartElement("td");
										writer.WriteString(listLabResult[i].AbnormalFlag.ToString());
										writer.WriteEndElement();
										writer.WriteStartElement("td");
										writer.WriteString(listLabResult[i].DateTimeTest.ToShortDateString());
										writer.WriteEndElement();
										writer.WriteEndElement();//End tr
									}
								writer.WriteEndElement();//End tbody
							writer.WriteEndElement();//End table
						writer.WriteEndElement();//End text
					writer.WriteEndElement();//End Diagnostic Test Results component
				writer.WriteEndElement();
				return strBuilder;
			}
		}*/