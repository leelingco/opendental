//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:22 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormOperatories;
import OpenDental.FormOperatoryEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Operatories;
import OpenDentBusiness.Operatory;
import OpenDentBusiness.OperatoryC;
import OpenDentBusiness.Providers;

/**
* Summary description for FormBasicTemplate.
*/
public class FormOperatories  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.Button butUp;
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormOperatories() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormOperatories.class);
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.label1 = new System.Windows.Forms.Label();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(542, 445);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(19, 445);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 26);
        this.butAdd.TabIndex = 10;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(21, 31);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(595, 385);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("Operatories");
        this.gridMain.setTranslationName("TableOperatories");
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
        this.label1.Location = new System.Drawing.Point(20, 7);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(588, 20);
        this.label1.TabIndex = 12;
        this.label1.Text = "(Also, see the appointment views section)";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(224, 445);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(79, 26);
        this.butDown.TabIndex = 14;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUp.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(122, 445);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(79, 26);
        this.butUp.TabIndex = 13;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // FormOperatories
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(649, 494);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormOperatories";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Operatories";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormOperatories_Closing);
        this.Load += new System.EventHandler(this.FormOperatories_Load);
        this.ResumeLayout(false);
    }

    private void formOperatories_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        Cache.refresh(InvalidType.Operatories);
        boolean neededFixing = false;
        for (int i = 0;i < OperatoryC.getListt().Count;i++)
        {
            if (OperatoryC.getListt()[i].ItemOrder != i)
            {
                OperatoryC.getListt()[i].ItemOrder = i;
                Operatories.Update(OperatoryC.getListt()[i]);
                neededFixing = true;
            }
             
        }
        if (neededFixing)
        {
            DataValid.setInvalid(InvalidType.Operatories);
        }
         
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableOperatories","Op Name"),150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableOperatories","Abbrev"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableOperatories","IsHidden"), 64, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableOperatories","Clinic"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableOperatories","Dentist"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableOperatories","Hygienist"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableOperatories","IsHygiene"), 72, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < OperatoryC.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(OperatoryC.getListt()[i].OpName);
            row.getCells().Add(OperatoryC.getListt()[i].Abbrev);
            if (OperatoryC.getListt()[i].IsHidden)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(Clinics.GetDesc(OperatoryC.getListt()[i].ClinicNum));
            row.getCells().Add(Providers.GetAbbr(OperatoryC.getListt()[i].ProvDentist));
            row.getCells().Add(Providers.GetAbbr(OperatoryC.getListt()[i].ProvHygienist));
            if (OperatoryC.getListt()[i].IsHygiene)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormOperatoryEdit FormE = new FormOperatoryEdit(OperatoryC.getListt()[e.getRow()]);
        FormE.ShowDialog();
        fillGrid();
        changed = true;
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        Operatory opCur = new Operatory();
        if (gridMain.getSelectedIndices().Length > 0)
        {
            //a row is selected
            opCur.ItemOrder = gridMain.getSelectedIndices()[0];
        }
        else
        {
            opCur.ItemOrder = OperatoryC.getListt().Count;
        } 
        //goes at end of list
        FormOperatoryEdit FormE = new FormOperatoryEdit(opCur);
        FormE.IsNew = true;
        FormE.ShowDialog();
        if (FormE.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length > 0)
        {
            for (int i = gridMain.getSelectedIndices()[0];i < OperatoryC.getListt().Count;i++)
            {
                //fix the itemOrder of every Operatory following this one
                OperatoryC.getListt()[i].ItemOrder++;
                Operatories.Update(OperatoryC.getListt()[i]);
            }
        }
         
        fillGrid();
        changed = true;
    }

    private void butUp_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"You must first select a row.");
            return ;
        }
         
        int selected = gridMain.getSelectedIndices()[0];
        if (selected == 0)
        {
            return ;
        }
         
        //already at the top
        //move selected item up
        OperatoryC.getListt()[selected].ItemOrder--;
        Operatories.Update(OperatoryC.getListt()[selected]);
        //move the one above it down
        OperatoryC.getListt()[selected - 1].ItemOrder++;
        Operatories.Update(OperatoryC.getListt()[selected - 1]);
        fillGrid();
        gridMain.setSelected(selected - 1,true);
        changed = true;
    }

    private void butDown_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"You must first select a row.");
            return ;
        }
         
        int selected = gridMain.getSelectedIndices()[0];
        if (selected == OperatoryC.getListt().Count - 1)
        {
            return ;
        }
         
        //already at the bottom
        //move selected item down
        OperatoryC.getListt()[selected].ItemOrder++;
        Operatories.Update(OperatoryC.getListt()[selected]);
        //move the one below it up
        OperatoryC.getListt()[selected + 1].ItemOrder--;
        Operatories.Update(OperatoryC.getListt()[selected + 1]);
        fillGrid();
        gridMain.setSelected(selected + 1,true);
        changed = true;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formOperatories_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Operatories);
        }
         
    }

}


