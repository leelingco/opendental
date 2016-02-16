//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.PrinterL;
import OpenDental.UI.GridSortingStrategy;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.EhrPatListElement2014;
import OpenDentBusiness.EhrPatListElements;
import OpenDentBusiness.EhrRestrictionType;
import OpenDentBusiness.PrintSituation;

public class FormPatListResultsEHR2014  extends Form 
{

    private List<EhrPatListElement2014> elementList = new List<EhrPatListElement2014>();
    private DataTable table = new DataTable();
    private boolean headingPrinted = new boolean();
    private int pagesPrinted = new int();
    public FormPatListResultsEHR2014(List<EhrPatListElement2014> ElementList) throws Exception {
        initializeComponent();
        elementList = ElementList;
    }

    private void formPatListResults_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        table = EhrPatListElements.getListOrderBy2014(elementList);
        int colWidth = 0;
        Graphics g = CreateGraphics();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("PatNum", 60, HorizontalAlignment.Center);
        col.setSortingStrategy(GridSortingStrategy.AmountParse);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Full Name",200);
        col.setSortingStrategy(GridSortingStrategy.StringCompare);
        gridMain.getColumns().add(col);
        for (int i = 0;i < elementList.Count;i++)
        {
            Restriction __dummyScrutVar0 = elementList[i].Restriction;
            if (__dummyScrutVar0.equals(EhrRestrictionType.Birthdate))
            {
                col = new ODGridColumn("Birthdate", 80, HorizontalAlignment.Center);
                col.setSortingStrategy(GridSortingStrategy.DateParse);
                gridMain.getColumns().add(col);
                col = new ODGridColumn("Age", 80, HorizontalAlignment.Center);
                col.setSortingStrategy(GridSortingStrategy.AmountParse);
                gridMain.getColumns().add(col);
            }
            else if (__dummyScrutVar0.equals(EhrRestrictionType.Gender))
            {
                col = new ODGridColumn("Gender", 80, HorizontalAlignment.Center);
                gridMain.getColumns().add(col);
            }
            else if (__dummyScrutVar0.equals(EhrRestrictionType.LabResult))
            {
                colWidth = System.Convert.ToInt32(g.MeasureString("Lab Value: " + elementList[i].CompareString, this.Font).Width);
                col.setSortingStrategy(GridSortingStrategy.AmountParse);
                colWidth = colWidth + (colWidth / 10);
                //Add 10%
                col = new ODGridColumn("Lab Value: " + elementList[i].CompareString, colWidth, HorizontalAlignment.Center);
                gridMain.getColumns().add(col);
                colWidth = System.Convert.ToInt32(g.MeasureString("Lab Date: " + elementList[i].CompareString, this.Font).Width);
                colWidth = colWidth + (colWidth / 10);
                //Add 10%
                col = new ODGridColumn("Lab Date: " + elementList[i].CompareString, colWidth, HorizontalAlignment.Center);
                col.setSortingStrategy(GridSortingStrategy.DateParse);
                gridMain.getColumns().add(col);
            }
            else if (__dummyScrutVar0.equals(EhrRestrictionType.Medication))
            {
                colWidth = System.Convert.ToInt32(g.MeasureString("Prescription Date: " + elementList[i].CompareString, this.Font).Width);
                colWidth = colWidth + (colWidth / 10);
                //Add 10%
                col = new ODGridColumn("Prescription Date: " + elementList[i].CompareString, colWidth, HorizontalAlignment.Center);
                col.setSortingStrategy(GridSortingStrategy.DateParse);
                gridMain.getColumns().add(col);
            }
            else if (__dummyScrutVar0.equals(EhrRestrictionType.Problem))
            {
                colWidth = System.Convert.ToInt32(g.MeasureString("Date Diagnosed: " + DiseaseDefs.GetNameByCode(elementList[i].CompareString), this.Font).Width);
                colWidth = colWidth + (colWidth / 10);
                //Add 10%
                col = new ODGridColumn("Date Diagnosed: " + DiseaseDefs.GetNameByCode(elementList[i].CompareString), colWidth, HorizontalAlignment.Center);
                col.setSortingStrategy(GridSortingStrategy.DateParse);
                gridMain.getColumns().add(col);
            }
            else if (__dummyScrutVar0.equals(EhrRestrictionType.Allergy))
            {
                colWidth = System.Convert.ToInt32(g.MeasureString("Date Alergic Reaction: " + elementList[i].CompareString, this.Font).Width);
                colWidth = colWidth + (colWidth / 10);
                //Add 10%
                col = new ODGridColumn("Date Alergic Reaction: " + elementList[i].CompareString, colWidth, HorizontalAlignment.Center);
                col.setSortingStrategy(GridSortingStrategy.DateParse);
                gridMain.getColumns().add(col);
            }
            else if (__dummyScrutVar0.equals(EhrRestrictionType.CommPref))
            {
                col = new ODGridColumn("Communication Preference", 180, HorizontalAlignment.Center);
                gridMain.getColumns().add(col);
            }
                   
        }
        //  colWidth=System.Convert.ToInt32(g.MeasureString(elementList[i].CompareString,this.Font).Width);
        //  colWidth=colWidth+(colWidth/10);//Add 10%
        //  if(colWidth<90) {
        //    colWidth=90;//Minimum of 90 width.
        //  }
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["PatNum"].ToString());
            row.getCells().Add(table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString());
            //Add 3 to j to compensate for PatNum, LName and FName.
            int k = 0;
            for (int j = 0;j < elementList.Count;j++)
            {
                //added to j to iterate through the table columns as j itterates through the elementList
                //sometimes one element might pull two columns, Lab Results for instance.//<elementList.Count;j++) {
                Restriction __dummyScrutVar1 = elementList[j].Restriction;
                if (__dummyScrutVar1.equals(EhrRestrictionType.Medication) || __dummyScrutVar1.equals(EhrRestrictionType.Problem) || __dummyScrutVar1.equals(EhrRestrictionType.Allergy))
                {
                    row.getCells().Add(table.Rows[i][j + k + 3].ToString().Replace(" 12:00:00 AM", ""));
                }
                else //safely remove irrelevant time entries.//dates
                if (__dummyScrutVar1.equals(EhrRestrictionType.Birthdate))
                {
                    row.getCells().Add(table.Rows[i][j + k + 3].ToString().Replace(" 12:00:00 AM", ""));
                    //safely remove irrelevant time entries.//date
                    row.getCells().Add(table.Rows[i][j + k + 4].ToString());
                    //age
                    k++;
                }
                else //to keep the count correct.
                if (__dummyScrutVar1.equals(EhrRestrictionType.LabResult))
                {
                    row.getCells().Add(table.Rows[i][j + k + 3].ToString());
                    //obsVal
                    row.getCells().Add(table.Rows[i][j + k + 4].ToString().Replace(" 12:00:00 AM", ""));
                    //safely remove irrelevant time entries.//date
                    k++;
                }
                else //to keep the count correct.
                if (__dummyScrutVar1.equals(EhrRestrictionType.Gender))
                {
                    Rows.INDEXER.INDEXER.APPLY __dummyScrutVar2 = table.Rows[i][j + k + 3].ToString();
                    if (__dummyScrutVar2.equals("0"))
                    {
                        //Male
                        row.getCells().add("Male");
                    }
                    else if (__dummyScrutVar2.equals("1"))
                    {
                        //Female
                        row.getCells().add("Female");
                    }
                    else
                    {
                        //Unknown
                        row.getCells().add("Unknown");
                    }  
                }
                else if (__dummyScrutVar1.equals(EhrRestrictionType.CommPref))
                {
                    Rows.INDEXER.INDEXER.APPLY __dummyScrutVar3 = table.Rows[i][j + k + 3].ToString();
                    if (__dummyScrutVar3.equals("0"))
                    {
                        //None
                        row.getCells().add("None");
                    }
                    else if (__dummyScrutVar3.equals("1"))
                    {
                        //DoNotCall
                        row.getCells().add("Do Not Call");
                    }
                    else if (__dummyScrutVar3.equals("2"))
                    {
                        //HmPhone
                        row.getCells().add("Home Phone");
                    }
                    else if (__dummyScrutVar3.equals("3"))
                    {
                        //WkPhone
                        row.getCells().add("Work Phone");
                    }
                    else if (__dummyScrutVar3.equals("4"))
                    {
                        //WirelessPh
                        row.getCells().add("Wireless Phone");
                    }
                    else if (__dummyScrutVar3.equals("5"))
                    {
                        //Email
                        row.getCells().add("Email");
                    }
                    else if (__dummyScrutVar3.equals("6"))
                    {
                        //SeeNotes
                        row.getCells().add("See Notes");
                    }
                    else if (__dummyScrutVar3.equals("7"))
                    {
                        //Mail
                        row.getCells().add("Mail");
                    }
                    else if (__dummyScrutVar3.equals("8"))
                    {
                        //TextMessage
                        row.getCells().add("TextMessage");
                    }
                             
                }
                     
            }
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        g.Dispose();
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(25, 25, 40, 40);
        //pd.OriginAtMargins=true;
        if (pd.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        if (!PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Printed patient list from EHR"))
        {
            return ;
        }
         
        try
        {
            pd.Print();
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = e.MarginBounds;
        //new Rectangle(50,40,800,1035);//Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = "Patient List";
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            headingPrinted = true;
        }
         
        //headingPrintH=yPos;
        yPos = gridMain.printPage(g,pagesPrinted,bounds,yPos);
        pagesPrinted++;
        if (yPos == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
        } 
        g.Dispose();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.butClose = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butPrint = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(799, 335);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 17;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.setAllowSortingByColumn(true);
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(2, 2);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(781, 367);
        this.gridMain.TabIndex = 10;
        this.gridMain.setTitle("Results");
        this.gridMain.setTranslationName("FormPatientListResults");
        //
        // butPrint
        //
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.Location = new System.Drawing.Point(799, 279);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 23);
        this.butPrint.TabIndex = 20;
        this.butPrint.Text = "Print";
        this.butPrint.UseVisualStyleBackColor = true;
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // FormPatListResultsEHR2014
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(886, 370);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMain);
        this.Name = "FormPatListResultsEHR2014";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Patient List Results";
        this.Load += new System.EventHandler(this.FormPatListResults_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butPrint = new System.Windows.Forms.Button();
}


