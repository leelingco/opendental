//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormEHR;
import OpenDental.FormEhrQualityMeasureEdit2014;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.QualityMeasure;
import OpenDentBusiness.QualityMeasures;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEhrQualityMeasures2014;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrQualityMeasures2014  extends Form 
{

    private List<QualityMeasure> listQ = new List<QualityMeasure>();
    private List<Provider> listProvsKeyed = new List<Provider>();
    private long _provNum = new long();
    private DateTime _dateStart = new DateTime();
    private DateTime _dateEnd = new DateTime();
    public long selectedPatNum = new long();
    public FormEhrQualityMeasures2014() throws Exception {
        initializeComponent();
    }

    private void formQualityMeasures_Load(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        listProvsKeyed = new List<Provider>();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (FormEHR.ProvKeyIsValid(ProviderC.getListShort()[i].LName, ProviderC.getListShort()[i].FName, true, ProviderC.getListShort()[i].EhrKey))
            {
                listProvsKeyed.Add(ProviderC.getListShort()[i]);
            }
             
        }
        if (listProvsKeyed.Count == 0)
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"No providers found with ehr keys.");
            return ;
        }
         
        for (int i = 0;i < listProvsKeyed.Count;i++)
        {
            comboProv.Items.Add(listProvsKeyed[i].GetLongDesc());
            if (Security.getCurUser().ProvNum == listProvsKeyed[i].ProvNum)
            {
                comboProv.SelectedIndex = i;
            }
             
        }
        textDateStart.Text = (new DateTime(DateTime.Now.Year, 1, 1)).ToShortDateString();
        textDateEnd.Text = (new DateTime(DateTime.Now.Year, 12, 31)).ToShortDateString();
        fillGrid();
        Cursor = Cursors.Default;
    }

    private void fillGrid() throws Exception {
        if (comboProv.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a provider first.");
            return ;
        }
         
        DateTime dateStart = PIn.Date(textDateStart.Text);
        DateTime dateEnd = PIn.Date(textDateEnd.Text);
        if (dateStart == DateTime.MinValue || dateEnd == DateTime.MinValue)
        {
            MsgBox.show(this,"Fix date format and try again.");
            return ;
        }
         
        _dateStart = dateStart;
        _dateEnd = dateEnd;
        _provNum = listProvsKeyed[comboProv.SelectedIndex].ProvNum;
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Id",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Description",200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Denominator", 75, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Numerator", 65, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Exclusion", 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Exception", 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("NotMet", 50, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("PerformanceRate", 100, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        listQ = QualityMeasures.getAll2014(dateStart,dateEnd,_provNum);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listQ.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listQ[i].Id);
            row.getCells().Add(listQ[i].Descript);
            row.getCells().Add(listQ[i].Denominator.ToString());
            row.getCells().Add(listQ[i].Numerator.ToString());
            row.getCells().Add(listQ[i].Exclusions.ToString());
            row.getCells().Add(listQ[i].Exceptions.ToString());
            row.getCells().Add(listQ[i].NotMet.ToString());
            row.getCells().Add(listQ[i].Numerator.ToString() + "/" + (listQ[i].Numerator + listQ[i].NotMet).ToString() + "  = " + listQ[i].PerformanceRate.ToString() + "%");
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    /**
    * Launches edit window for double clicked item.
    */
    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrQualityMeasureEdit2014 FormQME = new FormEhrQualityMeasureEdit2014();
        FormQME.MeasureCur = listQ[e.getRow()];
        FormQME.ShowDialog();
        if (FormQME.DialogResult == DialogResult.OK && FormQME.selectedPatNum != 0)
        {
            selectedPatNum = FormQME.selectedPatNum;
            DialogResult = DialogResult.OK;
            Close();
            return ;
        }
         
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        fillGrid();
        Cursor = Cursors.Default;
    }

    private void butCreateQRDAs_Click(Object sender, EventArgs e) throws Exception {
        if (comboProv.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a provider first.");
            return ;
        }
         
        if (listQ == null)
        {
            MsgBox.show(this,"Click Refresh first.");
            return ;
        }
         
        long provSelected = listProvsKeyed[comboProv.SelectedIndex].ProvNum;
        if (_provNum != provSelected)
        {
            MsgBox.show(this,"The values in the grid do not apply to the provider selected.  Click Refresh first.");
            return ;
        }
         
        FolderBrowserDialog fbd = new FolderBrowserDialog();
        if (fbd.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        String folderPath = fbd.SelectedPath + "\\" + "CQMs_" + DateTime.Today.ToString("MM_dd_yyyy");
        if (System.IO.Directory.Exists(folderPath))
        {
            //if the folder already exists
            //find a unique folder name
            int uniqueID = 1;
            String originalPath = folderPath;
            do
            {
                folderPath = originalPath + "_" + uniqueID.ToString();
                uniqueID++;
            }
            while (System.IO.Directory.Exists(folderPath));
        }
         
        try
        {
            System.IO.Directory.CreateDirectory(folderPath);
            for (int i = 0;i < listQ.Count;i++)
            {
                if (System.IO.Directory.Exists(folderPath + "\\Measure_" + listQ[i].eMeasureNum))
                {
                    continue;
                }
                 
                System.IO.Directory.CreateDirectory(folderPath + "\\Measure_" + listQ[i].eMeasureNum);
            }
        }
        catch (Exception ex)
        {
            MessageBox.Show("Folder was not created: " + ex.Message);
            return ;
        }

        Cursor = Cursors.WaitCursor;
        try
        {
            QualityMeasures.generateQRDA(listQ,_provNum,_dateStart,_dateEnd,folderPath);
        }
        catch (Exception ex)
        {
            //folderPath is a new directory created within the chosen directory
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        Cursor = Cursors.Default;
        MsgBox.show(this,"QRDA files have been created within the selected directory.");
    }

    private void butSubmit_Click(Object sender, EventArgs e) throws Exception {
        if (listQ == null)
        {
            MsgBox.show(this,"Click Refresh first.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        try
        {
        }
        catch (Exception ex)
        {
            //EmailMessages.SendTestUnsecure("QRDA","qrda.xml",GenerateQRDA());
            //code to export will need to include the cda.xsl style sheet as well as the cda.xsd
            //FolderBrowserDialog dlg=new FolderBrowserDialog();
            //dlg.SelectedPath=ImageStore.GetPatientFolder(PatCur,ImageStore.GetPreferredAtoZpath());//Default to patient image folder.
            //DialogResult result=dlg.ShowDialog();
            //if(result!=DialogResult.OK) {
            //	return;
            //}
            //if(File.Exists(Path.Combine(dlg.SelectedPath,"ccd.xml"))) {
            //	if(MessageBox.Show("Overwrite existing ccd.xml?","",MessageBoxButtons.OKCancel)!=DialogResult.OK) {
            //		return;
            //	}
            //}
            //File.WriteAllText(Path.Combine(dlg.SelectedPath,"ccd.xml"),ccd);
            //File.WriteAllText(Path.Combine(dlg.SelectedPath,"ccd.xsl"),FormEHR.GetEhrResource("CCD"));
            //EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
            //newMeasureEvent.DateTEvent = DateTime.Now;
            //newMeasureEvent.EventType = EhrMeasureEventType.ClinicalSummaryProvidedToPt;
            //newMeasureEvent.PatNum = PatCur.PatNum;
            //EhrMeasureEvents.Insert(newMeasureEvent);
            //FillGridEHRMeasureEvents();
            //MessageBox.Show("Exported");
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        Cursor = Cursors.Default;
        MsgBox.show(this,"Sent");
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        this.Close();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrQualityMeasures2014.class);
        this.butClose = new System.Windows.Forms.Button();
        this.butRefresh = new System.Windows.Forms.Button();
        this.textDateEnd = new System.Windows.Forms.TextBox();
        this.textDateStart = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butCreateQRDAs = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(658, 408);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 1;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butRefresh
        //
        this.butRefresh.Location = new System.Drawing.Point(633, 8);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(75, 23);
        this.butRefresh.TabIndex = 21;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.UseVisualStyleBackColor = true;
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // textDateEnd
        //
        this.textDateEnd.Location = new System.Drawing.Point(176, 8);
        this.textDateEnd.Name = "textDateEnd";
        this.textDateEnd.Size = new System.Drawing.Size(100, 20);
        this.textDateEnd.TabIndex = 19;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(50, 8);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(100, 20);
        this.textDateStart.TabIndex = 18;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(137, 12);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(53, 13);
        this.label1.TabIndex = 20;
        this.label1.Text = "to";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(-13, 13);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(60, 13);
        this.label5.TabIndex = 17;
        this.label5.Text = "Period";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboProv
        //
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.FormattingEnabled = true;
        this.comboProv.Location = new System.Drawing.Point(395, 8);
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(215, 21);
        this.comboProv.TabIndex = 24;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(273, 12);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(118, 13);
        this.label2.TabIndex = 23;
        this.label2.Text = "Provider";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butCreateQRDAs
        //
        this.butCreateQRDAs.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butCreateQRDAs.Location = new System.Drawing.Point(183, 408);
        this.butCreateQRDAs.Name = "butCreateQRDAs";
        this.butCreateQRDAs.Size = new System.Drawing.Size(93, 23);
        this.butCreateQRDAs.TabIndex = 25;
        this.butCreateQRDAs.Text = "Create QRDAs";
        this.butCreateQRDAs.UseVisualStyleBackColor = true;
        this.butCreateQRDAs.Click += new System.EventHandler(this.butCreateQRDAs_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 36);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(721, 366);
        this.gridMain.TabIndex = 0;
        this.gridMain.setTitle("Clinical Quality Measures 2014");
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
        // FormEhrQualityMeasures2014
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(745, 438);
        this.Controls.Add(this.butCreateQRDAs);
        this.Controls.Add(this.textDateEnd);
        this.Controls.Add(this.comboProv);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.textDateStart);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEhrQualityMeasures2014";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Clinical Quality Measures 2014";
        this.Load += new System.EventHandler(this.FormQualityMeasures_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butRefresh = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textDateEnd = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDateStart = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboProv = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butCreateQRDAs = new System.Windows.Forms.Button();
}


