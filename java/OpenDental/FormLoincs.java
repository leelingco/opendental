//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormLoincs;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormLoincs  extends Form 
{

    public boolean IsSelectionMode = new boolean();
    public Loinc SelectedLoinc;
    private List<Loinc> listLoincSearch = new List<Loinc>();
    public Loinc LoincCur;
    public FormLoincs() throws Exception {
        initializeComponent();
    }

    private void formLoincPicker_Load(Object sender, EventArgs e) throws Exception {
        listLoincSearch = new List<Loinc>();
        ActiveControl = textCode;
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Loinc Code",80);
        //,HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Status",80);
        //,HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Long Name",500);
        //,HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("UCUM Units",100);
        //,HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Order or Observation",100);
        //,HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        listLoincSearch = Loincs.GetBySearchString(textCode.Text);
        for (int i = 0;i < listLoincSearch.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listLoincSearch[i].LoincCode);
            row.getCells().Add(listLoincSearch[i].StatusOfCode);
            row.getCells().Add(listLoincSearch[i].NameLongCommon);
            row.getCells().Add(listLoincSearch[i].UnitsUCUM);
            row.getCells().Add(listLoincSearch[i].OrderObs);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            SelectedLoinc = listLoincSearch[e.getRow()];
            DialogResult = DialogResult.OK;
        }
         
    }

    //Nothing to do if not selection mode
    //private void butLoincFill_Click_Old(object sender,EventArgs e) {
    //  //string loincFilePath=@"C:\Users\Ryan\Desktop\LoincDB.txt";
    //  OpenFileDialog ofd=new OpenFileDialog();
    //  ofd.Title=Lan.g(this,"Please select the LoincDB.TXT file that contains the Loinc Codes.");
    //  ofd.Multiselect=false;
    //  ofd.ShowDialog();
    //  //Validate selected file---------------------------------------------------------------------------------------------------------------
    //  if(!ofd.FileName.ToLower().EndsWith(".txt")) {
    //    MsgBox.Show(this,"You must select a text file.");
    //  }
    //  try {
    //    System.IO.StreamReader sr=new System.IO.StreamReader(ofd.FileName);
    //    sr.Peek();
    //  }
    //  catch (Exception ex){
    //    MessageBox.Show(this,Lan.g(this,"Error reading file")+":\r\n"+ex.Message);
    //  }
    //  Cursor=Cursors.WaitCursor;
    //  //This code is useful for debugging and should be identical to the code found in convertDB3.
    //  //string command="DROP TABLE IF EXISTS loinc";
    //  //DataCore.NonQ(command);
    //  //command=@"CREATE TABLE loinc (
    //  //			LoincNum BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    //  //			LoincCode VARCHAR(255) NOT NULL,
    //  //			UCUMUnits VARCHAR(255) NOT NULL,
    //  //			LongName VARCHAR(255) NOT NULL,
    //  //			ShortName VARCHAR(255) NOT NULL,
    //  //			OrderObs VARCHAR(255) NOT NULL
    //  //			) DEFAULT CHARSET=utf8";
    //  //DataCore.NonQ(command);
    //  try {
    //    System.IO.StreamReader sr=new System.IO.StreamReader(ofd.FileName);
    //    while(!sr.ReadLine().StartsWith("<----Clip Here for Data----->")) {//fast forward through the header text to the data table.
    //      if(sr.EndOfStream) {//prevent infinite loop in case we read through the whole file.
    //        throw new Exception(Lan.g(this,"Reached end of file before finding data."));
    //        //break;
    //      }
    //    }
    //    string[] headers=sr.ReadLine().Split('\t');
    //    if(headers.Length!=48) {
    //      throw new Exception(Lan.g(this,"File contains unexpected number of columns."));
    //    }
    //    if(headers[0].Trim('\"') !="Loinc_NUM"
    //      || headers[39].Trim('\"')!="EXAMPLE_UCUM_UNITS"
    //      || headers[34].Trim('\"')!="LONG_COMMON_NAME"
    //      || headers[28].Trim('\"')!="SHORTNAME"
    //      || headers[29].Trim('\"')!="ORDER_OBS")
    //    {
    //      throw new Exception(Lan.g(this,"Column names mismatch."));
    //    }
    //    //Import Loinc Codes----------------------------------------------------------------------------------------------------------
    //    string[] arrayLoinc;
    //    Loinc loincTemp=new Loinc();
    //    while(!sr.EndOfStream) {//each loop should read exactly one line of code. and each line of code should be a unique Loinc code
    //      arrayLoinc=sr.ReadLine().Split('\t');
    //      loincTemp.LoincCode		=arrayLoinc[0].Trim('\"');
    //      //loincTemp.UCUMUnits		=arrayLoinc[39].Trim('\"');
    //      //loincTemp.LongName		=arrayLoinc[34].Trim('\"');
    //      //loincTemp.ShortName		=arrayLoinc[28].Trim('\"');
    //      loincTemp.OrderObs		=arrayLoinc[29].Trim('\"');
    //      Loincs.Insert(loincTemp);
    //    }
    //  }
    //  catch(Exception ex) {
    //    MessageBox.Show(this,Lan.g(this,"Error importing Loinc codes:")+"\r\n"+ex.Message);
    //  }
    //  Cursor=Cursors.Default;
    //}
    //		private void butLoincFill_Click(object sender,EventArgs e) {
    //			//Maybe this should access our servers and then download the file directly from us.
    //			MsgBox.Show(this,"This can take several minutes to import the Loinc table.");
    //			OpenFileDialog ofd=new OpenFileDialog();
    //			ofd.Title=Lan.g(this,"Please select the LoincDB.TXT file that contains the Loinc Codes.");
    //			ofd.Multiselect=false;
    //			if(ofd.ShowDialog()!=DialogResult.OK) {
    //				return;
    //			}
    //			//Validate selected file---------------------------------------------------------------------------------------------------------------
    //			if(!ofd.FileName.ToLower().EndsWith(".txt")) {
    //				MsgBox.Show(this,"You must select a text file.");
    //			}
    //			try {
    //				System.IO.StreamReader sr=new System.IO.StreamReader(ofd.FileName);
    //				sr.Peek();
    //			}
    //			catch(Exception ex) {
    //				MessageBox.Show(this,Lan.g(this,"Error reading file")+":\r\n"+ex.Message);
    //			}
    //			Cursor=Cursors.WaitCursor;
    //#if DEBUG
    //			string command="DROP TABLE IF EXISTS loinc";
    //			DataCore.NonQ(command);
    //			command=@"CREATE TABLE loinc (
    //						LoincNum bigint NOT NULL auto_increment PRIMARY KEY,
    //						LoincCode varchar(255) NOT NULL,
    //						Component varchar(255) NOT NULL,
    //						PropertyObserved varchar(255) NOT NULL,
    //						TimeAspct varchar(255) NOT NULL,
    //						SystemMeasured varchar(255) NOT NULL,
    //						ScaleType varchar(255) NOT NULL,
    //						MethodType varchar(255) NOT NULL,
    //						StatusOfCode varchar(255) NOT NULL,
    //						NameShort varchar(255) NOT NULL,
    //						ClassType int NOT NULL,
    //						UnitsRequired tinyint NOT NULL,
    //						OrderObs varchar(255) NOT NULL,
    //						HL7FieldSubfieldID varchar(255) NOT NULL,
    //						ExternalCopyrightNotice text NOT NULL,
    //						NameLongCommon varchar(255) NOT NULL,
    //						UnitsUCUM varchar(255) NOT NULL,
    //						RankCommonTests int NOT NULL,
    //						RankCommonOrders int NOT NULL
    //						) DEFAULT CHARSET=utf8";
    //			DataCore.NonQ(command);
    //#endif
    //			try {
    //				System.IO.StreamReader sr=new System.IO.StreamReader(ofd.FileName);
    //				while(!sr.ReadLine().StartsWith("<----Clip Here for Data----->")) {//fast forward through the header text to the data table.
    //					if(sr.EndOfStream) {//prevent infinite loop in case we read through the whole file.
    //						throw new Exception(Lan.g(this,"Reached end of file before finding data."));
    //						//break;
    //					}
    //				}
    //				string[] headers=sr.ReadLine().Split('\t');
    //				if(headers.Length!=48) {
    //					throw new Exception(Lan.g(this,"File contains unexpected number of columns."));
    //				}
    //				if(headers[0].Trim('\"') !="Loinc_NUM"
    //					|| headers[39].Trim('\"')!="EXAMPLE_UCUM_UNITS"
    //					|| headers[34].Trim('\"')!="LONG_COMMON_NAME"
    //					|| headers[28].Trim('\"')!="SHORTNAME"
    //					|| headers[29].Trim('\"')!="ORDER_OBS") {
    //					//TODO: expand this to check all columns, though this should be sufficient.
    //					throw new Exception(Lan.g(this,"Column names mismatch."));
    //				}
    //				//Import Loinc Codes----------------------------------------------------------------------------------------------------------
    //				string[] arrayLoinc;
    //				Loinc loincTemp=new Loinc();
    //				while(!sr.EndOfStream) {//each loop should read exactly one line of code. and each line of code should be a unique Loinc code
    //					arrayLoinc=sr.ReadLine().Split('\t');
    //					loincTemp.LoincCode								=arrayLoinc[0].Trim('\"');
    //					loincTemp.Component								=arrayLoinc[1].Trim('\"');
    //					loincTemp.PropertyObserved				=arrayLoinc[2].Trim('\"');
    //					loincTemp.TimeAspct								=arrayLoinc[3].Trim('\"');
    //					loincTemp.SystemMeasured					=arrayLoinc[4].Trim('\"');
    //					loincTemp.ScaleType								=arrayLoinc[5].Trim('\"');
    //					loincTemp.MethodType							=arrayLoinc[6].Trim('\"');
    //					loincTemp.StatusOfCode						=arrayLoinc[12].Trim('\"');
    //					//loincTemp.ClassType								=PIn.Int(arrayLoinc[15].Trim('\"'));
    //					loincTemp.UnitsRequired						=arrayLoinc[25].Trim('\"')=="Y";
    //					loincTemp.NameShort								=arrayLoinc[28].Trim('\"');
    //					loincTemp.OrderObs								=arrayLoinc[29].Trim('\"');
    //					loincTemp.HL7FieldSubfieldID			=arrayLoinc[31].Trim('\"');
    //					loincTemp.ExternalCopyrightNotice	=arrayLoinc[32].Trim('\"');
    //					loincTemp.NameLongCommon					=arrayLoinc[34].Trim('\"');
    //					loincTemp.UnitsUCUM								=arrayLoinc[39].Trim('\"');
    //					loincTemp.RankCommonOrders				=PIn.Int(arrayLoinc[44].Trim('\"'));
    //					loincTemp.RankCommonTests					=PIn.Int(arrayLoinc[45].Trim('\"'));
    //					Loincs.Insert(loincTemp);
    //				}
    //			}
    //			catch(Exception ex) {
    //				MessageBox.Show(this,Lan.g(this,"Error importing Loinc codes:")+"\r\n"+ex.Message);
    //			}
    //			Cursor=Cursors.Default;
    //			fillGrid();
    //		}
    private void butSearch_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a Loinc code from the list.");
            return ;
        }
         
        if (IsSelectionMode)
        {
            SelectedLoinc = listLoincSearch[gridMain.getSelectedIndex()];
        }
         
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLoincs.class);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label1 = new System.Windows.Forms.Label();
        this.textCode = new System.Windows.Forms.TextBox();
        this.butSearch = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.setAllowSortingByColumn(true);
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 41);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(901, 612);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("LOINC Codes");
        this.gridMain.setTranslationName("FormLoincCodes");
        this.gridMain.setWrapText(false);
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
        // label1
        //
        this.label1.Location = new System.Drawing.Point(15, 17);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(172, 16);
        this.label1.TabIndex = 20;
        this.label1.Text = "Code or Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCode
        //
        this.textCode.Location = new System.Drawing.Point(190, 14);
        this.textCode.Name = "textCode";
        this.textCode.Size = new System.Drawing.Size(158, 20);
        this.textCode.TabIndex = 19;
        //
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.Location = new System.Drawing.Point(354, 11);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 24);
        this.butSearch.TabIndex = 23;
        this.butSearch.Text = "Search";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(919, 599);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 25;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(919, 629);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 26;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormLoincs
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(1006, 665);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butSearch);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textCode);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormLoincs";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "LOINC";
        this.Load += new System.EventHandler(this.FormLoincPicker_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCode = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butSearch;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
}


