//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:15 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormInsFilingCodeEdit;
import OpenDental.FormInsFilingCodes;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.InsFilingCode;
import OpenDentBusiness.InsFilingCodeC;
import OpenDentBusiness.InsFilingCodes;
import OpenDentBusiness.InvalidType;

/**
* Summary description for FormBasicTemplate.
*/
public class FormInsFilingCodes  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butNone;
    private OpenDental.UI.Button butOK;
    private boolean changed = new boolean();
    public boolean IsSelectionMode = new boolean();
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butDown;
    /**
    * Only used if IsSelectionMode.  On OK, contains selected InsFilingCodeNum.  Can be 0.  Can also be set ahead of time externally.
    */
    public long SelectedInsFilingCodeNum = new long();
    /**
    * 
    */
    public FormInsFilingCodes() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    /**
    * Clean up any resources being used.
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsFilingCodes.class);
        this.butNone = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butNone
        //
        this.butNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNone.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butNone.setAutosize(true);
        this.butNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNone.setCornerRadius(4F);
        this.butNone.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butNone.Location = new System.Drawing.Point(412, 349);
        this.butNone.Name = "butNone";
        this.butNone.Size = new System.Drawing.Size(80, 24);
        this.butNone.TabIndex = 16;
        this.butNone.Text = "None";
        this.butNone.Click += new System.EventHandler(this.butNone_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(417, 570);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 15;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(17, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(367, 612);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("Insurance Filing Codes");
        this.gridMain.setTranslationName("TableInsFilingCodes");
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
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(412, 12);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 24);
        this.butAdd.TabIndex = 10;
        this.butAdd.Text = "&Add";
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
        this.butClose.Location = new System.Drawing.Point(417, 600);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUp.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(412, 185);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(75, 24);
        this.butUp.TabIndex = 17;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(412, 215);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(75, 24);
        this.butDown.TabIndex = 18;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // FormInsFilingCodes
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(520, 640);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.butNone);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormInsFilingCodes";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Insurance Filing Codes";
        this.Load += new System.EventHandler(this.FormInsFilingCodes_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormInsFilingCodes_FormClosing);
        this.ResumeLayout(false);
    }

    private void formInsFilingCodes_Load(Object sender, EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            butClose.Text = Lan.g(this,"Cancel");
        }
        else
        {
            butOK.Visible = false;
            butNone.Visible = false;
        } 
        for (int i = 0;i < InsFilingCodeC.getListt().Count;i++)
        {
            //synch the itemorders just in case
            if (InsFilingCodeC.getListt()[i].ItemOrder != i)
            {
                InsFilingCodeC.getListt()[i].ItemOrder = i;
                InsFilingCodes.Update(InsFilingCodeC.getListt()[i]);
                changed = true;
            }
             
        }
        fillGrid();
        if (SelectedInsFilingCodeNum != 0)
        {
            for (int i = 0;i < InsFilingCodeC.getListt().Count;i++)
            {
                if (InsFilingCodeC.getListt()[i].InsFilingCodeNum == SelectedInsFilingCodeNum)
                {
                    gridMain.setSelected(i,true);
                    break;
                }
                 
            }
        }
         
    }

    private void fillGrid() throws Exception {
        InsFilingCodes.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableInsFilingCodes","Description"),250);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableInsFilingCodes","EclaimCode"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < InsFilingCodeC.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(InsFilingCodeC.getListt()[i].Descript);
            row.getCells().Add(InsFilingCodeC.getListt()[i].EclaimCode);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormInsFilingCodeEdit FormIE = new FormInsFilingCodeEdit();
        FormIE.InsFilingCodeCur = new InsFilingCode();
        FormIE.InsFilingCodeCur.ItemOrder = InsFilingCodeC.getListt().Count;
        FormIE.InsFilingCodeCur.setIsNew(true);
        FormIE.ShowDialog();
        fillGrid();
        changed = true;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            SelectedInsFilingCodeNum = InsFilingCodeC.getListt()[e.getRow()].InsFilingCodeNum;
            DialogResult = DialogResult.OK;
            return ;
        }
        else
        {
            FormInsFilingCodeEdit FormI = new FormInsFilingCodeEdit();
            FormI.InsFilingCodeCur = InsFilingCodeC.getListt()[e.getRow()];
            FormI.ShowDialog();
            fillGrid();
            changed = true;
        } 
    }

    private void butNone_Click(Object sender, EventArgs e) throws Exception {
        //not even visible unless is selection mode
        SelectedInsFilingCodeNum = 0;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //not even visible unless is selection mode
        if (gridMain.getSelectedIndex() == -1)
        {
            SelectedInsFilingCodeNum = 0;
        }
        else
        {
            SelectedInsFilingCodeNum = InsFilingCodeC.getListt()[gridMain.getSelectedIndex()].InsFilingCodeNum;
        } 
        DialogResult = DialogResult.OK;
    }

    private void butUp_Click(Object sender, EventArgs e) throws Exception {
        int idx = gridMain.getSelectedIndex();
        if (idx == -1)
        {
            MsgBox.show(this,"Please select an insurance filing code first.");
            return ;
        }
         
        if (idx == 0)
        {
            return ;
        }
         
        //swap the orders.
        int order1 = InsFilingCodeC.getListt()[idx - 1].ItemOrder;
        int order2 = InsFilingCodeC.getListt()[idx].ItemOrder;
        InsFilingCodeC.getListt()[idx - 1].ItemOrder = order2;
        InsFilingCodes.Update(InsFilingCodeC.getListt()[idx - 1]);
        InsFilingCodeC.getListt()[idx].ItemOrder = order1;
        InsFilingCodes.Update(InsFilingCodeC.getListt()[idx]);
        changed = true;
        fillGrid();
        gridMain.setSelected(idx - 1,true);
    }

    private void butDown_Click(Object sender, EventArgs e) throws Exception {
        int idx = gridMain.getSelectedIndex();
        if (idx == -1)
        {
            MsgBox.show(this,"Please select an insurance filing code first.");
            return ;
        }
         
        if (idx == InsFilingCodeC.getListt().Count - 1)
        {
            return ;
        }
         
        int order1 = InsFilingCodeC.getListt()[idx].ItemOrder;
        int order2 = InsFilingCodeC.getListt()[idx + 1].ItemOrder;
        InsFilingCodeC.getListt()[idx].ItemOrder = order2;
        InsFilingCodes.Update(InsFilingCodeC.getListt()[idx]);
        InsFilingCodeC.getListt()[idx + 1].ItemOrder = order1;
        InsFilingCodes.Update(InsFilingCodeC.getListt()[idx + 1]);
        changed = true;
        fillGrid();
        gridMain.setSelected(idx + 1,true);
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formInsFilingCodes_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.InsFilingCodes);
        }
         
    }

}


