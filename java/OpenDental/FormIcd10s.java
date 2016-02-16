//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.Icd10s;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormIcd10s;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormIcd10s  extends Form 
{

    public boolean IsSelectionMode = new boolean();
    public Icd10 SelectedIcd10;
    private List<Icd10> listIcd10s = new List<Icd10>();
    private boolean changed = new boolean();
    public FormIcd10s() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formIcd10s_Load(Object sender, EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            butClose.Text = Lan.g(this,"Cancel");
        }
        else
        {
            butOK.Visible = false;
        } 
        ActiveControl = textCode;
    }

    private void butSearch_Click(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Icd10 Code",100);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn("Deprecated",75,HorizontalAlignment.Center);
        //gridMain.Columns.Add(col);
        col = new ODGridColumn("Description",500);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn("Used By CQM's",75);
        //gridMain.Columns.Add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        listIcd10s = Icd10s.GetBySearchText(textCode.Text);
        for (int i = 0;i < listIcd10s.Count;i++)
        {
            //List<ODGridRow> listAll=new List<ODGridRow>();//for sorting grid after it has been filled.
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listIcd10s[i].Icd10Code);
            row.getCells().Add(listIcd10s[i].Description);
            //row.Cells.Add(EhrCodes.GetMeasureIdsForCode(listCpts[i].SnomedCode,"SNOMEDCT"));
            row.setTag(listIcd10s[i]);
                ;
            //listAll.Add(row);
            gridMain.getRows().add(row);
        }
        //listAll.Sort(SortMeasuresMet);
        //for(int i=0;i<listAll.Count;i++) {
        //	gridMain.Rows.Add(listAll[i]);
        //}
        gridMain.endUpdate();
    }

    /**
    * Sort function to put the codes that apply to the most number of CQM's at the top so the user can see which codes they should select.
    */
    //private int SortMeasuresMet(ODGridRow row1,ODGridRow row2) {
    //	//First sort by the number of measures the codes apply to in a comma delimited list
    //	int diff=row2.Cells[2].Text.Split(new string[] { "," },StringSplitOptions.RemoveEmptyEntries).Length-row1.Cells[2].Text.Split(new string[] { "," },StringSplitOptions.RemoveEmptyEntries).Length;
    //	if(diff!=0) {
    //		return diff;
    //	}
    //	try {
    //		//if the codes apply to the same number of CQMs, order by the code values
    //		return PIn.Long(row1.Cells[0].Text).CompareTo(PIn.Long(row2.Cells[0].Text));
    //	}
    //	catch(Exception ex) {
    //		return 0;
    //	}
    //}
    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            SelectedIcd10 = (Icd10)gridMain.getRows().get___idx(e.getRow()).getTag();
            DialogResult = DialogResult.OK;
            return ;
        }
         
    }

    //changed=true;
    //FormSnomedEdit FormSE=new FormSnomedEdit((Snomed)gridMain.Rows[e.Row].Tag);
    //FormSE.ShowDialog();
    //if(FormSE.DialogResult!=DialogResult.OK) {
    //	return;
    //}
    //FillGrid();
    /*private void butAdd_Click(object sender,EventArgs e) {
    			//TODO: Either change to adding a snomed code instead of an ICD9 or don't allow users to add SNOMED codes other than importing.
    			changed=true;
    			Snomed snomed=new Snomed();
    			FormSnomedEdit FormI=new FormSnomedEdit(snomed);
    			FormI.IsNew=true;
    			FormI.ShowDialog();
    			FillGrid();
    		}*/
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //not even visible unless IsSelectionMode
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        SelectedIcd10 = (Icd10)gridMain.getRows().get___idx(gridMain.getSelectedIndex()).getTag();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormIcd10s.class);
        this.textCode = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butSearch = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textCode
        //
        this.textCode.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textCode.Location = new System.Drawing.Point(246, 10);
        this.textCode.Name = "textCode";
        this.textCode.Size = new System.Drawing.Size(399, 20);
        this.textCode.TabIndex = 17;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(73, 13);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(172, 16);
        this.label1.TabIndex = 18;
        this.label1.Text = "Code(s) or Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(20, 38);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(851, 641);
        this.gridMain.TabIndex = 20;
        this.gridMain.setTitle("ICD10 Codes");
        this.gridMain.setTranslationName("FormIcd10Codes");
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
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.Location = new System.Drawing.Point(651, 7);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 24);
        this.butSearch.TabIndex = 19;
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
        this.butOK.Location = new System.Drawing.Point(877, 625);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(877, 655);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormIcd10s
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(961, 691);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butSearch);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textCode);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormIcd10s";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "ICD10";
        this.Load += new System.EventHandler(this.FormIcd10s_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.TextBox textCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSearch;
    private OpenDental.UI.ODGrid gridMain;
}


