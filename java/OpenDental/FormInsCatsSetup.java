//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:15 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormInsCatEdit;
import OpenDental.FormInsCatsSetup;
import OpenDental.FormInsSpanEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CovCat;
import OpenDentBusiness.CovCatC;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.CovSpan;
import OpenDentBusiness.CovSpans;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.InvalidType;

/**
* 
*/
public class FormInsCatsSetup  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butAddSpan;
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butAddCat;
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.ODGrid gridMain;
    private GroupBox groupBox1 = new GroupBox();
    private TextBox textBox1 = new TextBox();
    private OpenDental.UI.Button butDefaultsReset;
    private Label label1 = new Label();
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormInsCatsSetup() throws Exception {
        initializeComponent();
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsCatsSetup.class);
        this.butOK = new OpenDental.UI.Button();
        this.butAddSpan = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butAddCat = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.butDefaultsReset = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butOK.Location = new System.Drawing.Point(613, 619);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(85, 24);
        this.butOK.TabIndex = 6;
        this.butOK.Text = "&Close";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butAddSpan
        //
        this.butAddSpan.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddSpan.setAutosize(true);
        this.butAddSpan.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddSpan.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddSpan.setCornerRadius(4F);
        this.butAddSpan.Image = Resources.getAdd();
        this.butAddSpan.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddSpan.Location = new System.Drawing.Point(512, 286);
        this.butAddSpan.Name = "butAddSpan";
        this.butAddSpan.Size = new System.Drawing.Size(86, 24);
        this.butAddSpan.TabIndex = 9;
        this.butAddSpan.Text = "Add Span";
        this.butAddSpan.Click += new System.EventHandler(this.butAddSpan_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(8, 19);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(86, 24);
        this.butUp.TabIndex = 12;
        this.butUp.Text = "Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butAddCat
        //
        this.butAddCat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddCat.setAutosize(true);
        this.butAddCat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddCat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddCat.setCornerRadius(4F);
        this.butAddCat.Image = Resources.getAdd();
        this.butAddCat.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddCat.Location = new System.Drawing.Point(8, 61);
        this.butAddCat.Name = "butAddCat";
        this.butAddCat.Size = new System.Drawing.Size(86, 24);
        this.butAddCat.TabIndex = 11;
        this.butAddCat.Text = "A&dd";
        this.butAddCat.Click += new System.EventHandler(this.butAddCat_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(100, 19);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(86, 24);
        this.butDown.TabIndex = 13;
        this.butDown.Text = "Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 68);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(476, 578);
        this.gridMain.TabIndex = 14;
        this.gridMain.setTitle("Coverage Spans");
        this.gridMain.setTranslationName("TableCovSpans");
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
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butUp);
        this.groupBox1.Controls.Add(this.butDown);
        this.groupBox1.Controls.Add(this.butAddCat);
        this.groupBox1.Location = new System.Drawing.Point(504, 68);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(194, 94);
        this.groupBox1.TabIndex = 15;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Categories";
        //
        // textBox1
        //
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Location = new System.Drawing.Point(12, 12);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.ReadOnly = true;
        this.textBox1.Size = new System.Drawing.Size(543, 53);
        this.textBox1.TabIndex = 16;
        this.textBox1.Text = resources.GetString("textBox1.Text");
        //
        // butDefaultsReset
        //
        this.butDefaultsReset.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDefaultsReset.setAutosize(true);
        this.butDefaultsReset.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDefaultsReset.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDefaultsReset.setCornerRadius(4F);
        this.butDefaultsReset.Location = new System.Drawing.Point(512, 430);
        this.butDefaultsReset.Name = "butDefaultsReset";
        this.butDefaultsReset.Size = new System.Drawing.Size(86, 24);
        this.butDefaultsReset.TabIndex = 18;
        this.butDefaultsReset.Text = "Set to Defaults";
        this.butDefaultsReset.Click += new System.EventHandler(this.butDefaultsReset_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(511, 458);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(148, 51);
        this.label1.TabIndex = 20;
        this.label1.Text = "This safely fixes the orders and spans.";
        //
        // FormInsCatsSetup
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butOK;
        this.ClientSize = new System.Drawing.Size(713, 660);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butDefaultsReset);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.butAddSpan);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormInsCatsSetup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Setup Insurance Categories";
        this.Load += new System.EventHandler(this.FormInsCatsSetup_Load);
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormInsCatsSetup_Closing);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formInsCatsSetup_Load(Object sender, System.EventArgs e) throws Exception {
        fillSpans();
    }

    private void fillSpans() throws Exception {
        CovCats.refreshCache();
        CovSpans.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Category",90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("From ADA",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("To ADA",70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Hidden",45);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("E-Benefit Category",100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        CovSpan[] spansForCat = new CovSpan[]();
        for (int i = 0;i < CovCatC.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.setTag(CovCatC.getListt()[i].Copy());
            row.setColorBackG(Color.FromArgb(225, 225, 225));
            if (i != 0)
            {
                gridMain.getRows()[gridMain.getRows().Count - 1].ColorLborder = Color.Black;
            }
             
            row.getCells().Add(CovCatC.getListt()[i].Description);
            row.getCells().add("");
            row.getCells().add("");
            if (CovCatC.getListt()[i].IsHidden)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            if (CovCatC.getListt()[i].EbenefitCat == EbenefitCategory.None)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(CovCatC.getListt()[i].EbenefitCat.ToString());
            } 
            gridMain.getRows().add(row);
            spansForCat = CovSpans.GetForCat(CovCatC.getListt()[i].CovCatNum);
            for (int j = 0;j < spansForCat.Length;j++)
            {
                row = new OpenDental.UI.ODGridRow();
                row.setTag(spansForCat[j].Copy());
                row.getCells().add("");
                row.getCells().Add(spansForCat[j].FromCode);
                row.getCells().Add(spansForCat[j].ToCode);
                row.getCells().add("");
                row.getCells().add("");
                gridMain.getRows().add(row);
            }
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        boolean isCat = false;
        long selectedKey = 0;
        if (gridMain.getRows().get___idx(e.getRow()).getTag().GetType() == CovCat.class)
        {
            isCat = true;
            selectedKey = ((CovCat)gridMain.getRows().get___idx(e.getRow()).getTag()).CovCatNum;
            FormInsCatEdit FormE = new FormInsCatEdit((CovCat)gridMain.getRows().get___idx(e.getRow()).getTag());
            FormE.ShowDialog();
            if (FormE.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        }
        else
        {
            //covSpan
            selectedKey = ((CovSpan)gridMain.getRows().get___idx(e.getRow()).getTag()).CovSpanNum;
            FormInsSpanEdit FormE = new FormInsSpanEdit((CovSpan)gridMain.getRows().get___idx(e.getRow()).getTag());
            FormE.ShowDialog();
            if (FormE.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        } 
        changed = true;
        fillSpans();
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (isCat && gridMain.getRows().get___idx(i).getTag().GetType() == CovCat.class && selectedKey == ((CovCat)gridMain.getRows().get___idx(i).getTag()).CovCatNum)
            {
                gridMain.setSelected(i,true);
            }
             
            if (!isCat && gridMain.getRows().get___idx(i).getTag().GetType() == CovSpan.class && selectedKey == ((CovSpan)gridMain.getRows().get___idx(i).getTag()).CovSpanNum)
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void butAddSpan_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length < 1)
        {
            MsgBox.show(this,"Please select a category first.");
            return ;
        }
         
        if (gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag.GetType() != CovCat.class)
        {
            MsgBox.show(this,"Please select a category first.");
            return ;
        }
         
        CovSpan covspan = new CovSpan();
        covspan.CovCatNum = ((CovCat)gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag).CovCatNum;
        FormInsSpanEdit FormE = new FormInsSpanEdit(covspan);
        FormE.IsNew = true;
        FormE.ShowDialog();
        if (FormE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        changed = true;
        fillSpans();
    }

    private void butUp_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length < 1)
        {
            MsgBox.show(this,"Please select a category first.");
            return ;
        }
         
        if (gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag.GetType() != CovCat.class)
        {
            MsgBox.show(this,"Please select a category first.");
            return ;
        }
         
        long catNum = ((CovCat)gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag).CovCatNum;
        CovCats.moveUp((CovCat)gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag);
        changed = true;
        fillSpans();
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (gridMain.getRows().get___idx(i).getTag().GetType() == CovCat.class && catNum == ((CovCat)gridMain.getRows().get___idx(i).getTag()).CovCatNum)
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void butDown_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length < 1)
        {
            MsgBox.show(this,"Please select a category first.");
            return ;
        }
         
        if (gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag.GetType() != CovCat.class)
        {
            MsgBox.show(this,"Please select a category first.");
            return ;
        }
         
        long catNum = ((CovCat)gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag).CovCatNum;
        CovCats.moveDown((CovCat)gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag);
        changed = true;
        fillSpans();
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (gridMain.getRows().get___idx(i).getTag().GetType() == CovCat.class && catNum == ((CovCat)gridMain.getRows().get___idx(i).getTag()).CovCatNum)
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void butAddCat_Click(Object sender, System.EventArgs e) throws Exception {
        CovCat covcat = new CovCat();
        covcat.CovOrder = (byte)CovCatC.getListt().Count;
        covcat.DefaultPercent = -1;
        FormInsCatEdit FormE = new FormInsCatEdit(covcat);
        FormE.IsNew = true;
        FormE.ShowDialog();
        if (FormE.DialogResult == DialogResult.OK)
        {
            changed = true;
            fillSpans();
        }
         
    }

    /*
    		private void butDefaultsCheck_Click(object sender,EventArgs e) {
    			string retVal=CheckDefaults();
    			if(retVal=="") {
    				MsgBox.Show(this,"Categories are set up correctly.  Spans have not been checked.  Push the Reset button to automatically reset the spans to default.");
    			}
    			else {
    				MessageBox.Show(retVal);
    			}
    		}*/
    private String checkDefaults() throws Exception {
        //There needs to be at least 14 categories, each with an etype and no duplicates.
        String retVal = "";
        int count = new int();
        for (int i = 1;i < 15;i++)
        {
            //starts with 1 because we don't care about None category
            count = CovCats.countForEbenCat(EbenefitCategory.values()[i]);
            if (count > 1)
            {
                retVal += "Duplicate category: " + (EbenefitCategory.values()[i]).ToString() + "\r\n";
            }
             
            if (count == 0)
            {
                retVal += "Missing category: " + (EbenefitCategory.values()[i]).ToString() + "\r\n";
            }
             
        }
        if (!StringSupport.equals(retVal, ""))
        {
            retVal = "The following errors must be fixed manually:\r\n\r\n" + retVal + "\r\n" + "Remember that any changes you make affect all current patients who are using those categories.";
        }
         
        return retVal;
    }

    private void butDefaultsReset_Click(Object sender, EventArgs e) throws Exception {
        String retVal = checkDefaults();
        if (!StringSupport.equals(retVal, ""))
        {
            MessageBox.Show(retVal);
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Reset orders and spans to default?"))
        {
            return ;
        }
         
        CovCats.setOrdersToDefault();
        CovCats.setSpansToDefault();
        fillSpans();
        MsgBox.show(this,"Done.");
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void formInsCatsSetup_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.InsCats);
        }
         
    }

}


