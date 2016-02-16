//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:07 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patients;

//using mshtml;
/**
* 
*/
public class FormEligibilityResponseDisplay  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private Label LblPatientName = new Label();
    private DataGridView dataGridView1 = new DataGridView();
    private System.ComponentModel.Container components = null;
    long PatID = new long();
    XmlDocument doc = new XmlDocument();
    DataSet DsEligResponse = new DataSet();
    private System.Drawing.Printing.PrintDocument MyPrintDocument = new System.Drawing.Printing.PrintDocument();
    private OpenDental.UI.Button btnPrintPreview;
    DataTable DtResponse = new DataTable("EligibilityResponse");
    DataGridViewPrinter MyDataGridViewPrinter;
    /**
    * 
    */
    public FormEligibilityResponseDisplay(XmlDocument Request, long PatientID) throws Exception {
        doc = Request;
        PatID = PatientID;
        initializeComponent();
    }

    /**
    * 
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    private void initializeComponent() throws Exception {
        System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
        System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
        System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle3 = new System.Windows.Forms.DataGridViewCellStyle();
        System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle4 = new System.Windows.Forms.DataGridViewCellStyle();
        this.butClose = new OpenDental.UI.Button();
        this.LblPatientName = new System.Windows.Forms.Label();
        this.dataGridView1 = new System.Windows.Forms.DataGridView();
        this.MyPrintDocument = new System.Drawing.Printing.PrintDocument();
        this.btnPrintPreview = new OpenDental.UI.Button();
        ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.OK;
        this.butClose.Location = new System.Drawing.Point(385, 404);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // LblPatientName
        //
        this.LblPatientName.AutoSize = true;
        this.LblPatientName.Font = new System.Drawing.Font("Microsoft Sans Serif", 13F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.LblPatientName.ForeColor = System.Drawing.Color.Blue;
        this.LblPatientName.Location = new System.Drawing.Point(2, 8);
        this.LblPatientName.Name = "LblPatientName";
        this.LblPatientName.Size = new System.Drawing.Size(0, 22);
        this.LblPatientName.TabIndex = 12;
        //
        // dataGridView1
        //
        this.dataGridView1.AllowUserToAddRows = false;
        this.dataGridView1.AllowUserToDeleteRows = false;
        this.dataGridView1.AllowUserToOrderColumns = true;
        this.dataGridView1.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.AllCells;
        this.dataGridView1.BackgroundColor = System.Drawing.SystemColors.Window;
        this.dataGridView1.CausesValidation = false;
        dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
        dataGridViewCellStyle1.BackColor = System.Drawing.SystemColors.Control;
        dataGridViewCellStyle1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        dataGridViewCellStyle1.ForeColor = System.Drawing.SystemColors.WindowText;
        dataGridViewCellStyle1.SelectionBackColor = System.Drawing.SystemColors.Highlight;
        dataGridViewCellStyle1.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
        this.dataGridView1.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle1;
        dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
        dataGridViewCellStyle2.BackColor = System.Drawing.SystemColors.Window;
        dataGridViewCellStyle2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        dataGridViewCellStyle2.ForeColor = System.Drawing.SystemColors.ControlText;
        dataGridViewCellStyle2.SelectionBackColor = System.Drawing.SystemColors.Highlight;
        dataGridViewCellStyle2.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
        dataGridViewCellStyle2.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
        this.dataGridView1.DefaultCellStyle = dataGridViewCellStyle2;
        this.dataGridView1.EditMode = System.Windows.Forms.DataGridViewEditMode.EditProgrammatically;
        this.dataGridView1.Location = new System.Drawing.Point(4, 33);
        this.dataGridView1.Name = "dataGridView1";
        dataGridViewCellStyle3.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
        dataGridViewCellStyle3.BackColor = System.Drawing.SystemColors.Control;
        dataGridViewCellStyle3.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        dataGridViewCellStyle3.ForeColor = System.Drawing.SystemColors.WindowText;
        dataGridViewCellStyle3.SelectionBackColor = System.Drawing.SystemColors.Highlight;
        dataGridViewCellStyle3.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
        dataGridViewCellStyle3.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
        this.dataGridView1.RowHeadersDefaultCellStyle = dataGridViewCellStyle3;
        this.dataGridView1.RowHeadersWidth = 22;
        dataGridViewCellStyle4.Alignment = System.Windows.Forms.DataGridViewContentAlignment.TopLeft;
        dataGridViewCellStyle4.NullValue = null;
        dataGridViewCellStyle4.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
        this.dataGridView1.RowsDefaultCellStyle = dataGridViewCellStyle4;
        this.dataGridView1.RowTemplate.ReadOnly = true;
        this.dataGridView1.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
        this.dataGridView1.Size = new System.Drawing.Size(807, 368);
        this.dataGridView1.TabIndex = 11;
        //
        // MyPrintDocument
        //
        this.MyPrintDocument.PrintPage += new System.Drawing.Printing.PrintPageEventHandler(this.MyPrintDocument_PrintPage);
        //
        // btnPrintPreview
        //
        this.btnPrintPreview.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.btnPrintPreview.setAutosize(true);
        this.btnPrintPreview.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.btnPrintPreview.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.btnPrintPreview.setCornerRadius(4F);
        this.btnPrintPreview.Location = new System.Drawing.Point(290, 404);
        this.btnPrintPreview.Name = "btnPrintPreview";
        this.btnPrintPreview.Size = new System.Drawing.Size(93, 23);
        this.btnPrintPreview.TabIndex = 14;
        this.btnPrintPreview.Text = "&Print Preview";
        this.btnPrintPreview.UseVisualStyleBackColor = true;
        this.btnPrintPreview.Click += new System.EventHandler(this.btnPrintPreview_Click);
        //
        // FormEligibilityResponseDisplay
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(814, 462);
        this.Controls.Add(this.btnPrintPreview);
        this.Controls.Add(this.LblPatientName);
        this.Controls.Add(this.dataGridView1);
        this.Controls.Add(this.butClose);
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormEligibilityResponseDisplay";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Patient Eligibility";
        this.Load += new System.EventHandler(this.FormDisplayEligibilityResponse_Load);
        ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formDisplayEligibilityResponse_Load(Object sender, System.EventArgs e) throws Exception {
        displayPatientName();
        prepareDataSetAndTable();
        populateDataTable();
        setDataColumn();
        displayData();
    }

    private void setDataColumn() throws Exception {
        // Setting the style of the DataGridView control
        this.dataGridView1.ColumnHeadersDefaultCellStyle.Font = new Font("Tahoma", 9, FontStyle.Bold, GraphicsUnit.Point);
        this.dataGridView1.ColumnHeadersDefaultCellStyle.BackColor = SystemColors.ControlDark;
        this.dataGridView1.ColumnHeadersBorderStyle = DataGridViewHeaderBorderStyle.Single;
        this.dataGridView1.ColumnHeadersDefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
        this.dataGridView1.DefaultCellStyle.Font = new Font("Tahoma", 8, FontStyle.Regular, GraphicsUnit.Point);
        this.dataGridView1.DefaultCellStyle.BackColor = Color.Empty;
        this.dataGridView1.AlternatingRowsDefaultCellStyle.BackColor = SystemColors.ControlLight;
        this.dataGridView1.CellBorderStyle = DataGridViewCellBorderStyle.Single;
        this.dataGridView1.GridColor = SystemColors.ControlDarkDark;
    }

    private void displayPatientName() throws Exception {
        this.LblPatientName.Text = Patients.getEligibilityDisplayName(PatID);
    }

    private void prepareDataSetAndTable() throws Exception {
        // Define DataSet and DataTable
        // Define DataColumns
        DataColumn colBenefitInfo = new DataColumn("Benefit Info", System.Type.GetType("System.String"));
        DataColumn colCoverageLevelCode = new DataColumn("Coverage Level", System.Type.GetType("System.String"));
        DataColumn colPlanCoverageDesc = new DataColumn("Plan Coverage", System.Type.GetType("System.String"));
        DataColumn colTimeQualifier = new DataColumn("Time Qualifier", System.Type.GetType("System.String"));
        DataColumn colBenefitAmount = new DataColumn("Benefit Amount", System.Type.GetType("System.String"));
        DataColumn colMessage = new DataColumn("Message", System.Type.GetType("System.String"));
        DataColumn colPercent = new DataColumn("Percent", System.Type.GetType("System.String"));
        DataColumn colProcCode = new DataColumn("ProcCode", System.Type.GetType("System.String"));
        DataColumn colDeliveryPattern = new DataColumn("Delivery Pattern", System.Type.GetType("System.String"));
        // Add Columns to Data Table
        DtResponse.Columns.Add(colBenefitInfo);
        DtResponse.Columns.Add(colCoverageLevelCode);
        DtResponse.Columns.Add(colPlanCoverageDesc);
        DtResponse.Columns.Add(colTimeQualifier);
        DtResponse.Columns.Add(colBenefitAmount);
        DtResponse.Columns.Add(colMessage);
        DtResponse.Columns.Add(colPercent);
        DtResponse.Columns.Add(colProcCode);
        DtResponse.Columns.Add(colDeliveryPattern);
        // Add Tabl to DataSet
        DsEligResponse.Tables.Add(DtResponse);
    }

    private void populateDataTable() throws Exception {
        XmlNodeList PlanBenefits = new XmlNodeList();
        XmlNodeList ChildNodes = new XmlNodeList();
        // Declare Variable for each table column
        String BenefitInfo = new String();
        String CoverageLevelCode = new String();
        String PlanCoverageDesc = new String();
        String TimeQualifier = new String();
        String BenefitAmount = new String();
        String Message = new String();
        String Percent = new String();
        String ProcCode = new String();
        String DeliveryPattern = new String();
        // select all Plan Benefit Nodes
        PlanBenefits = doc.SelectNodes("EligBenefitResponse/BenefitResponse/PlanBenefit");
        for (Object __dummyForeachVar1 : PlanBenefits)
        {
            XmlNode Node = (XmlNode)__dummyForeachVar1;
            ChildNodes = Node.ChildNodes;
            // reset All Prior Column Values
            BenefitInfo = String.Empty;
            CoverageLevelCode = String.Empty;
            PlanCoverageDesc = String.Empty;
            TimeQualifier = String.Empty;
            BenefitAmount = String.Empty;
            Message = String.Empty;
            Percent = String.Empty;
            ProcCode = String.Empty;
            DeliveryPattern = String.Empty;
            for (Object __dummyForeachVar0 : ChildNodes)
            {
                XmlNode ChildNode = (XmlNode)__dummyForeachVar0;
                // Process Each Child Node and Get the values back
                Name __dummyScrutVar0 = ChildNode.Name;
                if (__dummyScrutVar0.equals("BenefitInfo"))
                {
                    BenefitInfo = ChildNode.InnerText;
                }
                else if (__dummyScrutVar0.equals("CoverageLevelCode"))
                {
                    CoverageLevelCode = ChildNode.InnerText;
                }
                else if (__dummyScrutVar0.equals("PlanCoverageDesc"))
                {
                    PlanCoverageDesc = ChildNode.InnerText;
                }
                else if (__dummyScrutVar0.equals("TimeQualifier"))
                {
                    TimeQualifier = ChildNode.InnerText;
                }
                else if (__dummyScrutVar0.equals("BenefitAmount"))
                {
                    BenefitAmount = "$" + ChildNode.InnerText;
                }
                else if (__dummyScrutVar0.equals("Message"))
                {
                    if (!StringSupport.equals(ChildNode.InnerText.Substring(0, 3), "URL"))
                        Message = ChildNode.InnerText;
                     
                }
                else if (__dummyScrutVar0.equals("Percent"))
                {
                    try
                    {
                        Percent = System.Convert.ToString(System.Convert.ToDecimal(ChildNode.InnerText) * 100) + "%";
                    }
                    catch (Exception __dummyCatchVar0)
                    {
                        Percent = "??";
                    }
                
                }
                else if (__dummyScrutVar0.equals("Procedure"))
                {
                    ProcCode = ChildNode.ChildNodes.Item(1).InnerText;
                }
                else if (__dummyScrutVar0.equals("DeliveryPattern"))
                {
                    DeliveryPattern = ChildNode.ChildNodes.Item(0).InnerText + "-" + ChildNode.ChildNodes.Item(1).InnerText;
                }
                         
            }
            // Insert Record Into DataTable
            DataRow DrResponse = DtResponse.NewRow();
            DrResponse["Benefit Info"] = BenefitInfo;
            DrResponse["Coverage Level"] = CoverageLevelCode;
            DrResponse["Plan Coverage"] = PlanCoverageDesc;
            DrResponse["Time Qualifier"] = TimeQualifier;
            DrResponse["Benefit Amount"] = BenefitAmount;
            DrResponse["Message"] = Message;
            DrResponse["Percent"] = Percent;
            DrResponse["ProcCode"] = ProcCode;
            DrResponse["Delivery Pattern"] = DeliveryPattern;
            DtResponse.Rows.Add(DrResponse);
        }
    }

    private void displayData() throws Exception {
        this.dataGridView1.DataSource = DsEligResponse;
        this.dataGridView1.DataMember = "EligibilityResponse";
        // Changing the last column alignment to be in the Right alignment
        this.dataGridView1.Columns[this.dataGridView1.Columns.Count - 1].DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
        // Adjusting each column to be fit as the content of all its cells, including the header cell
        this.dataGridView1.AutoResizeColumns(DataGridViewAutoSizeColumnsMode.AllCells);
    }

    //this.dataGridView1.AutoResizeColumns();
    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private boolean setupThePrinting() throws Exception {
        PrintDialog MyPrintDialog = new PrintDialog();
        MyPrintDialog.AllowCurrentPage = false;
        MyPrintDialog.AllowPrintToFile = false;
        MyPrintDialog.AllowSelection = false;
        MyPrintDialog.AllowSomePages = false;
        MyPrintDialog.PrintToFile = false;
        MyPrintDialog.ShowHelp = false;
        MyPrintDialog.ShowNetwork = false;
        MyPrintDialog.UseEXDialog = true;
        if (MyPrintDialog.ShowDialog() != DialogResult.OK)
        {
            return false;
        }
         
        MyPrintDocument.DocumentName = "Patient Eligibility Response";
        MyPrintDocument.PrinterSettings = MyPrintDialog.PrinterSettings;
        MyPrintDocument.DefaultPageSettings = MyPrintDialog.PrinterSettings.DefaultPageSettings;
        MyPrintDocument.DefaultPageSettings.Margins = new Margins(40, 40, 40, 40);
        MyDataGridViewPrinter = new DataGridViewPrinter(this.dataGridView1, MyPrintDocument, true, true, this.LblPatientName.Text, new Font("Tahoma", 18, FontStyle.Bold, GraphicsUnit.Point), Color.Black, true);
        return true;
    }

    private void btnPrintPreview_Click(Object sender, EventArgs e) throws Exception {
        if (setupThePrinting())
        {
            PrintPreviewDialog MyPrintPreviewDialog = new PrintPreviewDialog();
            MyPrintPreviewDialog.Document = MyPrintDocument;
            MyPrintPreviewDialog.PrintPreviewControl.Zoom = 1.0;
            ((Form)MyPrintPreviewDialog).WindowState = FormWindowState.Maximized;
            MyPrintPreviewDialog.ShowDialog();
        }
         
    }

    private void myPrintDocument_PrintPage(Object sender, PrintPageEventArgs e) throws Exception {
        boolean more = MyDataGridViewPrinter.drawDataGridView(e.Graphics);
        if (more == true)
        {
            e.HasMorePages = true;
        }
         
    }

}


