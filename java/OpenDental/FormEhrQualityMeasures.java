//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.System.LCC.Disposable;
import OpenDental.FormEHR;
import OpenDental.FormEhrQualityMeasureEdit;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.QualityMeasure;
import OpenDentBusiness.QualityMeasures;
import OpenDentBusiness.QualityType;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormEhrQualityMeasures;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrQualityMeasures  extends Form 
{

    private List<QualityMeasure> listQ = new List<QualityMeasure>();
    private List<Provider> listProvsKeyed = new List<Provider>();
    public FormEhrQualityMeasures() throws Exception {
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
            MessageBox.Show("No providers found with ehr keys.");
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
            return ;
        }
         
        try
        {
            DateTime.Parse(textDateStart.Text);
            DateTime.Parse(textDateEnd.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            return ;
        }

        DateTime dateStart = PIn.Date(textDateStart.Text);
        DateTime dateEnd = PIn.Date(textDateEnd.Text);
        long provNum = listProvsKeyed[comboProv.SelectedIndex].ProvNum;
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Id",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Description",200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Denom", 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Numerator", 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Exclusion", 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("NotMet", 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("PerformanceRate", 110, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        listQ = QualityMeasures.getAll(dateStart,dateEnd,provNum);
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
            row.getCells().Add(listQ[i].NotMet.ToString());
            row.getCells().Add(listQ[i].Numerator.ToString() + "/" + (listQ[i].Numerator + listQ[i].NotMet).ToString() + "  = " + listQ[i].PerformanceRate.ToString() + "%");
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    public String generatePQRS_xml() throws Exception {
        //provider and dates already validated at button push
        Provider prov = listProvsKeyed[comboProv.SelectedIndex];
        DateTime dateStart = PIn.Date(textDateStart.Text);
        DateTime dateEnd = PIn.Date(textDateEnd.Text);
        List<QualityType> typesToReport = new List<QualityType>();
        typesToReport.Add(QualityType.WeightOver65);
        typesToReport.Add(QualityType.Hypertension);
        typesToReport.Add(QualityType.TobaccoUse);
        typesToReport.Add(QualityType.InfluenzaAdult);
        typesToReport.Add(QualityType.WeightChild_1_1);
        typesToReport.Add(QualityType.ImmunizeChild_1);
        typesToReport.Add(QualityType.Pneumonia);
        typesToReport.Add(QualityType.DiabetesBloodPressure);
        typesToReport.Add(QualityType.BloodPressureManage);
        XmlWriterSettings xmlSettings = new XmlWriterSettings();
        xmlSettings.Encoding = Encoding.UTF8;
        xmlSettings.OmitXmlDeclaration = true;
        xmlSettings.Indent = true;
        xmlSettings.IndentChars = "   ";
        StringBuilder strBuilder = new StringBuilder();
        XmlWriter writer = XmlWriter.Create(strBuilder, xmlSettings);
        try
        {
            {
                writer.WriteRaw("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
                writer.WriteStartElement("submission");
                writer.WriteAttributeString("type", "PQRI-REGISTRY");
                writer.WriteAttributeString("option", "PAYMENT");
                writer.WriteAttributeString("version", "2.0");
                writer.WriteAttributeString("xmlns", "xsi", null, "http://www.w3.org/2001/XMLSchema-instance");
                writer.WriteAttributeString("xsi", "noNamespaceSchemaLocation", null, "Registry_Payment.xsd");
                writer.WriteStartElement("file-audit-data");
                writer.WriteElementString("create-date", DateTime.Today.ToString("MM-dd-yyyy"));
                writer.WriteElementString("create-time", DateTime.Now.ToString("HH:mm"));
                //military time
                writer.WriteElementString("create-by", "RegistryA");
                //many values are hard-coded for test because they don't explain them
                writer.WriteElementString("version", "1.0");
                writer.WriteElementString("file-number", "1");
                writer.WriteElementString("number-of-files", "1");
                writer.WriteEndElement();
                //file-audit-data
                writer.WriteStartElement("registry");
                writer.WriteElementString("registry-name", "Model Registry");
                writer.WriteElementString("registry-id", "125789123");
                writer.WriteElementString("submission-method", "A");
                writer.WriteEndElement();
                //registry
                writer.WriteStartElement("measure-group");
                writer.WriteAttributeString("ID", "X");
                writer.WriteStartElement("provider");
                writer.WriteElementString("npi", prov.NationalProvID);
                writer.WriteElementString("tin", prov.SSN);
                writer.WriteElementString("waiver-signed", "Y");
                writer.WriteElementString("encounter-from-date", dateStart.ToString("MM-dd-yyyy"));
                writer.WriteElementString("encounter-to-date", dateEnd.ToString("MM-dd-yyyy"));
                for (int i = 0;i < listQ.Count;i++)
                {
                    //measure-group-stat must be omitted because measure-group ID is X
                    if (!typesToReport.Contains(listQ[i].Type))
                    {
                        continue;
                    }
                     
                    writer.WriteStartElement("pqri-measure");
                    writer.WriteElementString("pqri-measure-number", QualityMeasures.GetPQRIMeasureNumber(listQ[i].Type));
                    writer.WriteElementString("eligible-instances", listQ[i].Denominator.ToString());
                    writer.WriteElementString("meets-performance-instances", listQ[i].Numerator.ToString());
                    writer.WriteElementString("performance-exclusion-instances", listQ[i].Exclusions.ToString());
                    writer.WriteElementString("performance-not-met-instances", listQ[i].NotMet.ToString());
                    writer.WriteElementString("reporting-rate", listQ[i].ReportingRate.ToString("f2"));
                    if (listQ[i].Denominator == 0)
                    {
                        //rate is null
                        writer.WriteStartElement("performance-rate");
                        writer.WriteAttributeString("xsi", "nil", System.Xml.Schema.XmlSchema.InstanceNamespace, "true");
                        writer.WriteEndElement();
                    }
                    else
                    {
                        writer.WriteElementString("performance-rate", listQ[i].PerformanceRate.ToString("f2"));
                    } 
                    writer.WriteEndElement();
                }
                //pqri-measure
                writer.WriteEndElement();
                //provider
                writer.WriteEndElement();
                //measure-group
                writer.WriteEndElement();
            }
        }
        finally
        {
            if (writer != null)
                Disposable.mkDisposable(writer).dispose();
             
        }
        return strBuilder.ToString();
    }

    //submission
    /**
    * Launches edit window for double clicked item.
    */
    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        try
        {
            DateTime.Parse(textDateStart.Text);
            DateTime.Parse(textDateEnd.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show("Please fix dates first.");
            return ;
        }

        FormEhrQualityMeasureEdit formQe = new FormEhrQualityMeasureEdit();
        formQe.DateStart = PIn.Date(textDateStart.Text);
        formQe.DateEnd = PIn.Date(textDateEnd.Text);
        formQe.ProvNum = listProvsKeyed[comboProv.SelectedIndex].ProvNum;
        formQe.Qcur = listQ[e.getRow()];
        formQe.ShowDialog();
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butShow_Click(Object sender, EventArgs e) throws Exception {
        if (comboProv.SelectedIndex == -1)
        {
            MessageBox.Show("Please select a provider first.");
            return ;
        }
         
        try
        {
            DateTime.Parse(textDateStart.Text);
            DateTime.Parse(textDateEnd.Text);
        }
        catch (Exception __dummyCatchVar2)
        {
            MessageBox.Show("Invalid dates.");
            return ;
        }

        if (listQ == null)
        {
            MessageBox.Show("Click Refresh first.");
            return ;
        }
         
        MsgBoxCopyPaste MsgBoxCP = new MsgBoxCopyPaste(generatePQRS_xml());
        MsgBoxCP.ShowDialog();
    }

    private void butSubmit_Click(Object sender, EventArgs e) throws Exception {
        if (comboProv.SelectedIndex == -1)
        {
            MessageBox.Show("Please select a provider first.");
            return ;
        }
         
        try
        {
            DateTime.Parse(textDateStart.Text);
            DateTime.Parse(textDateEnd.Text);
        }
        catch (Exception __dummyCatchVar3)
        {
            MessageBox.Show("Invalid dates.");
            return ;
        }

        if (listQ == null)
        {
            MessageBox.Show("Click Refresh first.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        try
        {
            EmailMessages.sendTestUnsecure("PQRI","pqri.xml",generatePQRS_xml());
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        Cursor = Cursors.Default;
        MessageBox.Show("Sent");
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrQualityMeasures.class);
        this.butClose = new System.Windows.Forms.Button();
        this.butRefresh = new System.Windows.Forms.Button();
        this.textDateEnd = new System.Windows.Forms.TextBox();
        this.textDateStart = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.butSubmit = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butShow = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(633, 635);
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
        // butSubmit
        //
        this.butSubmit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSubmit.Location = new System.Drawing.Point(246, 635);
        this.butSubmit.Name = "butSubmit";
        this.butSubmit.Size = new System.Drawing.Size(84, 23);
        this.butSubmit.TabIndex = 22;
        this.butSubmit.Text = "Submit PQRI";
        this.butSubmit.UseVisualStyleBackColor = true;
        this.butSubmit.Click += new System.EventHandler(this.butSubmit_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 36);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(696, 593);
        this.gridMain.TabIndex = 0;
        this.gridMain.setTitle("Clinical Quality Measures");
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
        // butShow
        //
        this.butShow.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butShow.Location = new System.Drawing.Point(140, 635);
        this.butShow.Name = "butShow";
        this.butShow.Size = new System.Drawing.Size(84, 23);
        this.butShow.TabIndex = 25;
        this.butShow.Text = "Show PQRI";
        this.butShow.UseVisualStyleBackColor = true;
        this.butShow.Click += new System.EventHandler(this.butShow_Click);
        //
        // FormEhrQualityMeasures
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(720, 665);
        this.Controls.Add(this.butShow);
        this.Controls.Add(this.textDateEnd);
        this.Controls.Add(this.comboProv);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butSubmit);
        this.Controls.Add(this.butRefresh);
        this.Controls.Add(this.textDateStart);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEhrQualityMeasures";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Clinical Quality Measures";
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
    private System.Windows.Forms.Button butSubmit = new System.Windows.Forms.Button();
    private System.Windows.Forms.ComboBox comboProv = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butShow = new System.Windows.Forms.Button();
}


