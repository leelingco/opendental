//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.UI.GridSortingStrategy;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.EhrPatListElement;
import OpenDentBusiness.EhrPatListElements;
import OpenDentBusiness.EhrRestrictionType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.SecurityLogs;

public class FormEhrPatListResults  extends Form 
{

    private List<EhrPatListElement> elementList = new List<EhrPatListElement>();
    private DataTable table = new DataTable();
    private boolean headingPrinted = new boolean();
    private int pagesPrinted = new int();
    /**
    * Used to sort and keep the ASC/DESC UI intact, mostly useless.
    */
    private int orderByColumn = -1;
    public FormEhrPatListResults(List<EhrPatListElement> ElementList) throws Exception {
        initializeComponent();
        elementList = ElementList;
    }

    private void formPatListResults_Load(Object sender, EventArgs e) throws Exception {
        fillGrid(true);
    }

    /**
    * Deprecated.  We no longer need to pass in a bool.
    */
    private void fillGrid(boolean isAsc) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        table = EhrPatListElements.getListOrderBy2014Retro(elementList);
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
            if (orderByColumn == -1 && elementList[i].OrderBy)
            {
                //There can be 0 to 1 elements that have OrderBy set to true.
                //we will use this to determine how to use the ASC/DESC buttons.
                //some elements add one column, others add two, this selects the column that is to be added next.
                orderByColumn = gridMain.getColumns().Count;
            }
             
            Restriction __dummyScrutVar0 = elementList[i].Restriction;
            if (__dummyScrutVar0.equals(EhrRestrictionType.Birthdate))
            {
                col = new ODGridColumn("Birthdate", 80, HorizontalAlignment.Center);
                col.setSortingStrategy(GridSortingStrategy.DateParse);
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
            }
            else if (__dummyScrutVar0.equals(EhrRestrictionType.Medication))
            {
                col = new ODGridColumn("Medication", 90, HorizontalAlignment.Center);
                col.setSortingStrategy(GridSortingStrategy.StringCompare);
                gridMain.getColumns().add(col);
            }
            else if (__dummyScrutVar0.equals(EhrRestrictionType.Problem))
            {
                col = new ODGridColumn("Disease", 160, HorizontalAlignment.Center);
                col.setSortingStrategy(GridSortingStrategy.StringCompare);
                gridMain.getColumns().add(col);
            }
            else
            {
            }     
        }
        //should not happen.
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["PatNum"].ToString());
            row.getCells().Add(table.Rows[i]["LName"].ToString() + ", " + table.Rows[i]["FName"].ToString());
            for (int j = 0;j < elementList.Count;j++)
            {
                //Add 3 to j to compensate for PatNum, LName and FName.
                //sometimes one element might pull two columns, Lab Results for instance.//<elementList.Count;j++) {
                Restriction __dummyScrutVar1 = elementList[j].Restriction;
                if (__dummyScrutVar1.equals(EhrRestrictionType.Medication) || __dummyScrutVar1.equals(EhrRestrictionType.Problem))
                {
                    row.getCells().Add(table.Rows[i][j + 3].ToString());
                }
                else if (__dummyScrutVar1.equals(EhrRestrictionType.Birthdate))
                {
                    row.getCells().Add(table.Rows[i][j + 3].ToString().Replace(" 12:00:00 AM", ""));
                }
                else if (__dummyScrutVar1.equals(EhrRestrictionType.LabResult))
                {
                    row.getCells().Add(table.Rows[i][j + 3].ToString());
                }
                else //obsVal
                if (__dummyScrutVar1.equals(EhrRestrictionType.Gender))
                {
                    Rows.INDEXER.INDEXER.APPLY __dummyScrutVar2 = table.Rows[i][j + 3].ToString();
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
                    
            }
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        g.Dispose();
    }

    ////private void FillGrid(bool isAsc) {
    ////	table=EhrPatListElements.GetListOrderBy(elementList,isAsc);
    ////	int colWidth=0;
    ////	Graphics g=CreateGraphics();
    ////	gridMain.BeginUpdate();
    ////	gridMain.Columns.Clear();
    ////	ODGridColumn col;
    ////	col=new ODGridColumn("PatNum",60,HorizontalAlignment.Center);
    ////	gridMain.Columns.Add(col);
    ////	col=new ODGridColumn("Full Name",110);
    ////	gridMain.Columns.Add(col);
    ////	for(int i=0;i<elementList.Count;i++) {
    ////		if(elementList[i].Restriction==EhrRestrictionType.Birthdate) {
    ////			col=new ODGridColumn("Birthdate",80,HorizontalAlignment.Center);
    ////			gridMain.Columns.Add(col);
    ////		}
    ////		else if(elementList[i].Restriction==EhrRestrictionType.Gender) {
    ////			col=new ODGridColumn("Gender",70,HorizontalAlignment.Center);
    ////			gridMain.Columns.Add(col);
    ////		}
    ////		else if(elementList[i].Restriction==EhrRestrictionType.Problem) {
    ////			col=new ODGridColumn("Disease",160,HorizontalAlignment.Center);
    ////			gridMain.Columns.Add(col);
    ////		}
    ////		else {
    ////			colWidth=System.Convert.ToInt32(g.MeasureString(elementList[i].CompareString,this.Font).Width);
    ////			colWidth=colWidth+(colWidth/10);//Add 10%
    ////			if(colWidth<90) {
    ////				colWidth=90;//Minimum of 90 width.
    ////			}
    ////			col=new ODGridColumn(elementList[i].CompareString,colWidth,HorizontalAlignment.Center);
    ////			gridMain.Columns.Add(col);
    ////		}
    ////	}
    ////	gridMain.Rows.Clear();
    ////	ODGridRow row;
    ////	string icd9Desc;
    ////	for(int i=0;i<table.Rows.Count;i++) {
    ////		row=new ODGridRow();
    ////		row.Cells.Add(table.Rows[i]["PatNum"].ToString());
    ////		row.Cells.Add(table.Rows[i]["LName"].ToString()+", "+table.Rows[i]["FName"].ToString());
    ////		//Add 3 to j to compensate for PatNum, LName and FName.
    ////		for(int j=0;j<elementList.Count;j++) {
    ////			if(elementList[j].Restriction==EhrRestrictionType.Problem) {
    ////				ICD9 icd;
    ////				try {
    ////					icd=ICD9s.GetOne(PIn.Long(table.Rows[i][j+3].ToString()));
    ////					icd9Desc="("+icd.ICD9Code+")-"+icd.Description;
    ////					row.Cells.Add(icd9Desc);
    ////				}
    ////				catch {//Graceful fail just in case.
    ////					row.Cells.Add("X");
    ////				}
    ////				continue;
    ////			}
    ////			if(elementList[j].Restriction==EhrRestrictionType.Medication)	{
    ////				row.Cells.Add("X");
    ////				continue;
    ////			}
    ////			if(elementList[j].Restriction==EhrRestrictionType.Birthdate) {
    ////				row.Cells.Add(PIn.Date(table.Rows[i][j+3].ToString()).ToShortDateString());
    ////				continue;
    ////			}
    ////			if(elementList[j].Restriction==EhrRestrictionType.Gender)	{
    ////				switch(table.Rows[i][j+3].ToString()) {
    ////					case "0":
    ////						row.Cells.Add("Male");
    ////						break;
    ////					case "1":
    ////						row.Cells.Add("Female");
    ////						break;
    ////					default:
    ////						row.Cells.Add("Unknown");
    ////						break;
    ////				}
    ////				continue;
    ////			}
    ////			row.Cells.Add(table.Rows[i][j+3].ToString());
    ////		}
    ////		gridMain.Rows.Add(row);
    ////	}
    ////	gridMain.EndUpdate();
    ////	g.Dispose();
    ////}
    //private void radioAsc_CheckedChanged(object sender,EventArgs e) {
    //	if(orderByColumn==-1 || orderByColumn>gridMain.Columns.Count-1){
    //		return;//no order by collumn set, or index out of bounds.
    //	}
    //	gridMain.SortForced(orderByColumn,true);
    //}
    //private void radioDesc_CheckedChanged(object sender,EventArgs e) {
    //	if(orderByColumn==-1 || orderByColumn>gridMain.Columns.Count-1) {
    //		return;//no order by collumn set, or index out of bounds.
    //	}
    //	gridMain.SortForced(orderByColumn,false);
    //}
    private void radioOrderBy_CheckedChanged(Object sender, EventArgs e) throws Exception {
        if (orderByColumn == -1 || orderByColumn > gridMain.getColumns().Count - 1)
        {
            return ;
        }
         
        //no order by collumn set, or index out of bounds.
        gridMain.SortForced(orderByColumn, radioAsc.Checked);
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
         
        PrintDialog dialog = new PrintDialog();
        dialog.UseEXDialog = true;
        DialogResult result = dialog.ShowDialog();
        if (result == DialogResult.OK)
        {
            try
            {
                pd.PrinterSettings = dialog.PrinterSettings;
                pd.Print();
                //Create audit log entry for printing.  PatNum can be 0.
                SecurityLogs.MakeLogEntry(Permissions.Printing, 0, "Patient List Printed");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        
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
        this.groupOrderBy = new System.Windows.Forms.GroupBox();
        this.radioDesc = new System.Windows.Forms.RadioButton();
        this.radioAsc = new System.Windows.Forms.RadioButton();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butPrint = new System.Windows.Forms.Button();
        this.groupOrderBy.SuspendLayout();
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
        // groupOrderBy
        //
        this.groupOrderBy.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.groupOrderBy.Controls.Add(this.radioDesc);
        this.groupOrderBy.Controls.Add(this.radioAsc);
        this.groupOrderBy.Location = new System.Drawing.Point(799, 154);
        this.groupOrderBy.Name = "groupOrderBy";
        this.groupOrderBy.Size = new System.Drawing.Size(75, 68);
        this.groupOrderBy.TabIndex = 18;
        this.groupOrderBy.TabStop = false;
        this.groupOrderBy.Text = "Order By";
        //
        // radioDesc
        //
        this.radioDesc.AutoSize = true;
        this.radioDesc.Location = new System.Drawing.Point(11, 39);
        this.radioDesc.Name = "radioDesc";
        this.radioDesc.Size = new System.Drawing.Size(50, 17);
        this.radioDesc.TabIndex = 20;
        this.radioDesc.Text = "Desc";
        this.radioDesc.UseVisualStyleBackColor = true;
        //
        // radioAsc
        //
        this.radioAsc.AutoSize = true;
        this.radioAsc.Location = new System.Drawing.Point(11, 19);
        this.radioAsc.Name = "radioAsc";
        this.radioAsc.Size = new System.Drawing.Size(43, 17);
        this.radioAsc.TabIndex = 19;
        this.radioAsc.Text = "Asc";
        this.radioAsc.UseVisualStyleBackColor = true;
        this.radioAsc.CheckedChanged += new System.EventHandler(this.radioOrderBy_CheckedChanged);
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
        // FormEhrPatListResults
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(886, 370);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.groupOrderBy);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMain);
        this.Name = "FormEhrPatListResults";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Patient List Results";
        this.Load += new System.EventHandler(this.FormPatListResults_Load);
        this.groupOrderBy.ResumeLayout(false);
        this.groupOrderBy.PerformLayout();
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupOrderBy = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioDesc = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioAsc = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Button butPrint = new System.Windows.Forms.Button();
}


