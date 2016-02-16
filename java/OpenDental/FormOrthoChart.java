//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormOrthoChartAddDate;
import OpenDental.FormPatFieldCheckEdit;
import OpenDental.FormPatFieldCurrencyEdit;
import OpenDental.FormPatFieldDateEdit;
import OpenDental.FormPatFieldEdit;
import OpenDental.FormPatFieldPickEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFieldCategory;
import OpenDentBusiness.DisplayFields;
import OpenDentBusiness.OrthoChart;
import OpenDentBusiness.OrthoCharts;
import OpenDentBusiness.PatField;
import OpenDentBusiness.PatFieldDefs;
import OpenDentBusiness.PatFields;
import OpenDentBusiness.PatFieldType;
import OpenDentBusiness.Patient;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormOrthoChart  extends Form 
{

    private PatField[] listPatientFields = new PatField[]();
    private List<DisplayField> listOrthDisplayFields = new List<DisplayField>();
    private List<OrthoChart> listOrthoCharts = new List<OrthoChart>();
    public Patient PatCur;
    /**
    * Each row in this table has a date as the first cell.  There will be additional rows that are not yet in the db.  Each blank cell will be an empty string.  It will also store changes made by the user prior to closing the form.  When the form is closed, this table will be compared with the original listOrthoCharts and a synch process will take place to save to db.  An empty string in a cell will result in no db row or a deletion of existing db row.
    */
    DataTable table = new DataTable();
    public FormOrthoChart(Patient patCur) throws Exception {
        PatCur = patCur;
        initializeComponent();
        Lan.F(this);
    }

    private void formOrthoChart_Load(Object sender, EventArgs e) throws Exception {
        //define the table----------------------------------------------------------------------------------------------------------
        table = new DataTable("OrthoChartForPatient");
        //define columns----------------------------------------------------------------------------------------------------------
        table.Columns.Add("Date", DateTime.class);
        listOrthDisplayFields = DisplayFields.getForCategory(DisplayFieldCategory.OrthoChart);
        for (int i = 0;i < listOrthDisplayFields.Count;i++)
        {
            table.Columns.Add((i + 1).ToString());
        }
        //named by number, but probably refer to by index
        //define rows------------------------------------------------------------------------------------------------------------
        listOrthoCharts = OrthoCharts.getAllForPatient(PatCur.PatNum);
        List<DateTime> datesShowing = new List<DateTime>();
        List<String> listDisplayFieldNames = new List<String>();
        for (int i = 0;i < listOrthDisplayFields.Count;i++)
        {
            //fill listDisplayFieldNames to be used in comparison
            listDisplayFieldNames.Add(listOrthDisplayFields[i].Description);
        }
        //start adding dates starting with today's date
        datesShowing.Add(DateTime.Today);
        for (int i = 0;i < listOrthoCharts.Count;i++)
        {
            if (!listDisplayFieldNames.Contains(listOrthoCharts[i].FieldName))
            {
                continue;
            }
             
            //skip rows not in display fields
            if (!datesShowing.Contains(listOrthoCharts[i].DateService))
            {
                //add dates not already in date list
                datesShowing.Add(listOrthoCharts[i].DateService);
            }
             
        }
        datesShowing.Sort();
        //We now have a list of dates.
        //add all blank cells to each row except for the date.
        DataRow row = new DataRow();
        for (int i = 0;i < datesShowing.Count;i++)
        {
            //create and add row for each date in date showing
            row = table.NewRow();
            row["Date"] = datesShowing[i];
            for (int j = 0;j < listOrthDisplayFields.Count;j++)
            {
                row[j + 1] = "";
            }
            //j+1 because first row is date field.
            table.Rows.Add(row);
        }
        for (int i = 0;i < listOrthoCharts.Count;i++)
        {
            //We now have a table with all empty strings in cells except dates.
            //Fill with data as necessary.
            //loop
            if (!datesShowing.Contains(listOrthoCharts[i].DateService))
            {
                continue;
            }
             
            if (!listDisplayFieldNames.Contains(listOrthoCharts[i].FieldName))
            {
                continue;
            }
             
            for (int j = 0;j < table.Rows.Count;j++)
            {
                if (listOrthoCharts[i].DateService == (DateTime)table.Rows[j]["Date"])
                {
                    table.Rows[j][listDisplayFieldNames.IndexOf(listOrthoCharts[i].FieldName) + 1] = listOrthoCharts[i].FieldValue;
                }
                 
            }
        }
        fillGrid();
        fillGridPat();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Date",70);
        gridMain.getColumns().add(col);
        for (int i = 0;i < listOrthDisplayFields.Count;i++)
        {
            col = new ODGridColumn(listOrthDisplayFields[i].Description, listOrthDisplayFields[i].ColumnWidth, true);
            gridMain.getColumns().add(col);
        }
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            DateTime tempDate = (DateTime)table.Rows[i]["Date"];
            row.getCells().Add(tempDate.ToShortDateString());
            row.setTag(tempDate);
            for (int j = 0;j < listOrthDisplayFields.Count;j++)
            {
                row.getCells().Add(table.Rows[i][j + 1].ToString());
            }
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void fillGridPat() throws Exception {
        gridPat.beginUpdate();
        gridPat.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Field",150);
        gridPat.getColumns().add(col);
        col = new ODGridColumn("Value",200);
        gridPat.getColumns().add(col);
        gridPat.getRows().Clear();
        listPatientFields = PatFields.refresh(PatCur.PatNum);
        PatFieldDefs.refreshCache();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < PatFieldDefs.getList().Length;i++)
        {
            //define and fill rows in grid at the same time.
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(PatFieldDefs.getList()[i].FieldName);
            for (int j = 0;j <= listPatientFields.Length;j++)
            {
                if (j == listPatientFields.Length)
                {
                    //no matches in the list
                    row.getCells().add("");
                    break;
                }
                 
                if (listPatientFields[j].FieldName == PatFieldDefs.getList()[i].FieldName)
                {
                    if (PatFieldDefs.getList()[i].FieldType == PatFieldType.Checkbox)
                    {
                        row.getCells().add("X");
                    }
                    else
                    {
                        row.getCells().Add(listPatientFields[j].FieldValue);
                    } 
                    break;
                }
                 
            }
            gridPat.getRows().add(row);
        }
        gridPat.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
    }

    /*
    			if(e.Col==0){//cannot edit a date
    				FormOrthoChartAddDate FormOCAD = new FormOrthoChartAddDate();
    				FormOCAD.ShowDialog();
    				if(FormOCAD.DialogResult!=DialogResult.OK) {
    					return;
    				}
    				for(int i=0;i<gridMain.Rows.Count;i++) {
    					if(FormOCAD.SelectedDate.ToShortDateString()==gridMain.Rows[i].Cells[0].Text) {
    						MsgBox.Show(this,"That date already exists.");
    						return;
    					}
    				}
    				listDatesAdditional.Add(FormOCAD.SelectedDate);
    				FillGrid();
    				return;
    			}
    			////create an orthoChart that has this date and this type
    			//FormOrthoChartEdit FormOCE = new FormOrthoChartEdit();
    			//if(gridMain.Rows[e.Row].Cells[e.Col].Text==" ") {//new ortho chart
    			//  FormOCE.OrthoCur.DateService = DateTime.Parse(gridMain.Rows[e.Row].Cells[0].Text);
    			//  FormOCE.OrthoCur.FieldName = gridMain.Columns[e.Col].Heading;
    			//  FormOCE.IsNew=true;
    			//}
    			//else {//existing ortho chart
    			//  for(int i=0;i<listOrthoCharts.Count;i++) {
    			//    if(listOrthoCharts[i].DateService.ToShortDateString()==gridMain.Rows[e.Row].Cells[0].Text
    			//    && listOrthoCharts[i].FieldName==gridMain.Columns[e.Col].Heading) {
    			//      FormOCE.OrthoCur=listOrthoCharts[i];
    			//      break;
    			//    }
    			//  }
    			//}
    			//FormOCE.ShowDialog();
    			//if(FormOCE.DialogResult!=DialogResult.OK) {
    			//  return;
    			//}
    			//if(FormOCE.IsNew) {
    			//  OrthoCharts.Insert(FormOCE.OrthoCur);
    			//}
    			//else {
    			//  OrthoCharts.Update(FormOCE.OrthoCur);
    			//}*/
    //FillGrid();
    private void gridPat_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        PatField field = PatFields.GetByName(PatFieldDefs.getList()[e.getRow()].FieldName, listPatientFields);
        if (field == null)
        {
            field = new PatField();
            field.PatNum = PatCur.PatNum;
            field.FieldName = PatFieldDefs.getList()[e.getRow()].FieldName;
            if (PatFieldDefs.getList()[e.getRow()].FieldType == PatFieldType.Text)
            {
                FormPatFieldEdit FormPF = new FormPatFieldEdit(field);
                FormPF.IsNew = true;
                FormPF.ShowDialog();
            }
             
            if (PatFieldDefs.getList()[e.getRow()].FieldType == PatFieldType.PickList)
            {
                FormPatFieldPickEdit FormPF = new FormPatFieldPickEdit(field);
                FormPF.IsNew = true;
                FormPF.ShowDialog();
            }
             
            if (PatFieldDefs.getList()[e.getRow()].FieldType == PatFieldType.Date)
            {
                FormPatFieldDateEdit FormPF = new FormPatFieldDateEdit(field);
                FormPF.IsNew = true;
                FormPF.ShowDialog();
            }
             
            if (PatFieldDefs.getList()[e.getRow()].FieldType == PatFieldType.Checkbox)
            {
                FormPatFieldCheckEdit FormPF = new FormPatFieldCheckEdit(field);
                FormPF.IsNew = true;
                FormPF.ShowDialog();
            }
             
            if (PatFieldDefs.getList()[e.getRow()].FieldType == PatFieldType.Currency)
            {
                FormPatFieldCurrencyEdit FormPF = new FormPatFieldCurrencyEdit(field);
                FormPF.IsNew = true;
                FormPF.ShowDialog();
            }
             
        }
        else
        {
            if (PatFieldDefs.getList()[e.getRow()].FieldType == PatFieldType.Text)
            {
                FormPatFieldEdit FormPF = new FormPatFieldEdit(field);
                FormPF.ShowDialog();
            }
             
            if (PatFieldDefs.getList()[e.getRow()].FieldType == PatFieldType.PickList)
            {
                FormPatFieldPickEdit FormPF = new FormPatFieldPickEdit(field);
                FormPF.ShowDialog();
            }
             
            if (PatFieldDefs.getList()[e.getRow()].FieldType == PatFieldType.Date)
            {
                FormPatFieldDateEdit FormPF = new FormPatFieldDateEdit(field);
                FormPF.ShowDialog();
            }
             
            if (PatFieldDefs.getList()[e.getRow()].FieldType == PatFieldType.Checkbox)
            {
                FormPatFieldCheckEdit FormPF = new FormPatFieldCheckEdit(field);
                FormPF.ShowDialog();
            }
             
            if (PatFieldDefs.getList()[e.getRow()].FieldType == PatFieldType.Currency)
            {
                FormPatFieldCurrencyEdit FormPF = new FormPatFieldCurrencyEdit(field);
                FormPF.ShowDialog();
            }
             
        } 
        fillGridPat();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormOrthoChartAddDate FormOCAD = new FormOrthoChartAddDate();
        FormOCAD.ShowDialog();
        if (FormOCAD.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (FormOCAD.SelectedDate == (DateTime)table.Rows[i]["Date"])
            {
                MsgBox.show(this,"That date already exists.");
                return ;
            }
             
        }
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            //listDatesAdditional.Add(FormOCAD.SelectedDate);
            //Move data from grid to table, add new date row to datatable, then fill grid from table.
            table.Rows[i]["Date"] = gridMain.getRows().get___idx(i).getTag();
            for (int j = 0;j < listOrthDisplayFields.Count;j++)
            {
                //store date
                table.Rows[i][j + 1] = gridMain.getRows().get___idx(i).getCells()[j + 1].Text;
            }
        }
        DataRow row = new DataRow();
        row = table.NewRow();
        row["Date"] = FormOCAD.SelectedDate;
        for (int i = 0;i < listOrthDisplayFields.Count;i++)
        {
            row[i + 1] = "";
        }
        for (int i = 0;i <= table.Rows.Count;i++)
        {
            //j+1 because first row is date field.
            //insert new row in proper ascending datetime order to dataTable
            if (i == table.Rows.Count)
            {
                table.Rows.InsertAt(row, i);
                break;
            }
             
            if ((DateTime)row["Date"] > (DateTime)table.Rows[i]["Date"])
            {
                continue;
            }
             
            table.Rows.InsertAt(row, i);
            break;
        }
        fillGrid();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

    private void formOrthoChart_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            //Save data from grid to table
            table.Rows[i]["Date"] = gridMain.getRows().get___idx(i).getTag();
            for (int j = 0;j < listOrthDisplayFields.Count;j++)
            {
                //store date
                table.Rows[i][j + 1] = gridMain.getRows().get___idx(i).getCells()[j + 1].Text;
            }
        }
        List<OrthoChart> tempOrthoChartsFromDB = OrthoCharts.getAllForPatient(PatCur.PatNum);
        List<OrthoChart> tempOrthoChartsFromTable = new List<OrthoChart>();
        for (int r = 0;r < table.Rows.Count;r++)
        {
            for (int c = 1;c < table.Columns.Count;c++)
            {
                //skip col 0
                OrthoChart tempChart = new OrthoChart();
                tempChart.DateService = (DateTime)table.Rows[r]["Date"];
                tempChart.FieldName = listOrthDisplayFields[c - 1].Description;
                tempChart.FieldValue = table.Rows[r][c].ToString();
                tempChart.PatNum = PatCur.PatNum;
                tempOrthoChartsFromTable.Add(tempChart);
            }
        }
        for (int i = 0;i < tempOrthoChartsFromTable.Count;i++)
        {
            //Check table list vs DB list for inserts, updates, and deletes.
            //Either delete an existing record from the DB or ignore this non-entry.
            if (StringSupport.equals(tempOrthoChartsFromTable[i].FieldValue, ""))
            {
                for (int j = 0;j < tempOrthoChartsFromDB.Count;j++)
                {
                    if (tempOrthoChartsFromDB[j].DateService == tempOrthoChartsFromTable[i].DateService && tempOrthoChartsFromDB[j].FieldName == tempOrthoChartsFromTable[i].FieldName)
                    {
                        OrthoCharts.Delete(tempOrthoChartsFromDB[j].OrthoChartNum);
                        break;
                    }
                     
                }
                continue;
            }
             
            for (int j = 0;j <= tempOrthoChartsFromDB.Count;j++)
            {
                //i loop
                //Update the Record if it already exists or Insert if it's new.
                //Insert if you've made it through the whole list.
                if (j == tempOrthoChartsFromDB.Count)
                {
                    OrthoCharts.Insert(tempOrthoChartsFromTable[i]);
                    break;
                }
                 
                //Update if type and date match
                if (tempOrthoChartsFromDB[j].DateService == tempOrthoChartsFromTable[i].DateService && tempOrthoChartsFromDB[j].FieldName == tempOrthoChartsFromTable[i].FieldName)
                {
                    tempOrthoChartsFromTable[i].OrthoChartNum = tempOrthoChartsFromDB[j].OrthoChartNum;
                    OrthoCharts.Update(tempOrthoChartsFromTable[i]);
                    break;
                }
                 
            }
        }
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
        this.gridPat = new OpenDental.UI.ODGrid();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.label6 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // gridPat
        //
        this.gridPat.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridPat.setHScrollVisible(false);
        this.gridPat.Location = new System.Drawing.Point(10, 9);
        this.gridPat.Name = "gridPat";
        this.gridPat.setScrollValue(0);
        this.gridPat.Size = new System.Drawing.Size(916, 194);
        this.gridPat.TabIndex = 6;
        this.gridPat.setTitle("Patient Fields");
        this.gridPat.setTranslationName(null);
        this.gridPat.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridPat.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPat_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(10, 209);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.OneCell);
        this.gridMain.Size = new System.Drawing.Size(916, 418);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle("Ortho Chart");
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
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Location = new System.Drawing.Point(10, 634);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 23);
        this.butAdd.TabIndex = 9;
        this.butAdd.Text = "Add Date";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(851, 634);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 7;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // label6
        //
        this.label6.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.label6.Location = new System.Drawing.Point(722, 630);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(123, 27);
        this.label6.TabIndex = 55;
        this.label6.Text = "(All info is always saved automatically)";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // FormOrthoChart
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(938, 665);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridPat);
        this.Controls.Add(this.gridMain);
        this.Name = "FormOrthoChart";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Ortho Chart";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormOrthoChart_FormClosing);
        this.Load += new System.EventHandler(this.FormOrthoChart_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridPat;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
}


