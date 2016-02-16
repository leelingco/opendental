//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:52 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.ContrTable.__MultiCellEventHandler;
import OpenDental.DataValid;
import OpenDental.FormClearinghouseEdit;
import OpenDental.FormClearinghouses;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clearinghouses;
import OpenDentBusiness.ElectronicClaimFormat;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormClearinghouses  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    private OpenDental.TableClearinghouses gridMain;
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butAdd;
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butDefaultMedical;
    private OpenDental.UI.Button butDefaultDental;
    private boolean listHasChanged = new boolean();
    /**
    * 
    */
    public FormClearinghouses() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.C(this, new System.Windows.Forms.Control[]{ textBox1 });
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClearinghouses.class);
        this.butClose = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.TableClearinghouses();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.butAdd = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butDefaultDental = new OpenDental.UI.Button();
        this.butDefaultMedical = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
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
        this.butClose.Location = new System.Drawing.Point(807, 465);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.BackColor = System.Drawing.SystemColors.Window;
        this.gridMain.Location = new System.Drawing.Point(6, 61);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(1);
        this.gridMain.setSelectedIndices(new int[0]);
        this.gridMain.setSelectionMode(System.Windows.Forms.SelectionMode.One);
        this.gridMain.Size = new System.Drawing.Size(879, 318);
        this.gridMain.TabIndex = 2;
        this.gridMain.CellDoubleClicked = __MultiCellEventHandler.combine(this.gridMain.CellDoubleClicked,new OpenDental.ContrTable.CellEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.CellEventArgs e) throws Exception {
                this.gridMain_CellDoubleClicked(sender, e);
            }

            public List<OpenDental.ContrTable.CellEventHandler> getInvocationList() throws Exception {
                List<OpenDental.ContrTable.CellEventHandler> ret = new ArrayList<OpenDental.ContrTable.CellEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // textBox1
        //
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Location = new System.Drawing.Point(10, 8);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.ReadOnly = true;
        this.textBox1.Size = new System.Drawing.Size(597, 50);
        this.textBox1.TabIndex = 3;
        this.textBox1.Text = resources.GetString("textBox1.Text");
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
        this.butAdd.Location = new System.Drawing.Point(805, 385);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 24);
        this.butAdd.TabIndex = 8;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butDefaultMedical);
        this.groupBox1.Controls.Add(this.butDefaultDental);
        this.groupBox1.Location = new System.Drawing.Point(6, 387);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(97, 86);
        this.groupBox1.TabIndex = 9;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Set Default";
        //
        // butDefaultDental
        //
        this.butDefaultDental.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDefaultDental.setAutosize(true);
        this.butDefaultDental.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDefaultDental.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDefaultDental.setCornerRadius(4F);
        this.butDefaultDental.Location = new System.Drawing.Point(15, 19);
        this.butDefaultDental.Name = "butDefaultDental";
        this.butDefaultDental.Size = new System.Drawing.Size(75, 24);
        this.butDefaultDental.TabIndex = 1;
        this.butDefaultDental.Text = "Dental";
        this.butDefaultDental.Click += new System.EventHandler(this.butDefaultDental_Click);
        //
        // butDefaultMedical
        //
        this.butDefaultMedical.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDefaultMedical.setAutosize(true);
        this.butDefaultMedical.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDefaultMedical.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDefaultMedical.setCornerRadius(4F);
        this.butDefaultMedical.Location = new System.Drawing.Point(15, 49);
        this.butDefaultMedical.Name = "butDefaultMedical";
        this.butDefaultMedical.Size = new System.Drawing.Size(75, 24);
        this.butDefaultMedical.TabIndex = 2;
        this.butDefaultMedical.Text = "Medical";
        this.butDefaultMedical.Click += new System.EventHandler(this.butDefaultMedical_Click);
        //
        // FormClearinghouses
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(891, 503);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClearinghouses";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "E-Claims";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormClearinghouses_Closing);
        this.Load += new System.EventHandler(this.FormClearinghouses_Load);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formClearinghouses_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        Clearinghouses.refreshCache();
        gridMain.ResetRows(Clearinghouses.getListt().Length);
        gridMain.SetGridColor(Color.Gray);
        gridMain.SetBackGColor(Color.White);
        for (int i = 0;i < Clearinghouses.getListt().Length;i++)
        {
            gridMain.Cell[0, i] = Clearinghouses.getListt()[i].Description;
            gridMain.Cell[1, i] = Clearinghouses.getListt()[i].ExportPath;
            gridMain.Cell[2, i] = Clearinghouses.getListt()[i].Eformat.ToString();
            String s = "";
            if (PrefC.getLong(PrefName.ClearinghouseDefaultDent) == Clearinghouses.getListt()[i].ClearinghouseNum)
            {
                s += "Dent";
            }
             
            if (PrefC.getLong(PrefName.ClearinghouseDefaultMed) == Clearinghouses.getListt()[i].ClearinghouseNum)
            {
                if (!StringSupport.equals(s, ""))
                {
                    s += ",";
                }
                 
                s += "Med";
            }
             
            gridMain.Cell[3, i] = s;
            gridMain.Cell[4, i] = Clearinghouses.getListt()[i].Payors;
        }
        gridMain.layoutTables();
    }

    private void gridMain_CellDoubleClicked(Object sender, OpenDental.CellEventArgs e) throws Exception {
        FormClearinghouseEdit FormCE = new FormClearinghouseEdit();
        FormCE.ClearinghouseCur = Clearinghouses.getListt()[e.getRow()];
        FormCE.ShowDialog();
        if (FormCE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        listHasChanged = true;
        fillGrid();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormClearinghouseEdit FormCE = new FormClearinghouseEdit();
        FormCE.ClearinghouseCur = new Clearinghouse();
        FormCE.IsNew = true;
        FormCE.ShowDialog();
        if (FormCE.DialogResult != DialogResult.OK)
            return ;
         
        listHasChanged = true;
        fillGrid();
    }

    private void butDefaultDental_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.SelectedRow == -1)
        {
            MsgBox.show(this,"Please select a row first.");
            return ;
        }
         
        Clearinghouse ch = Clearinghouses.getListt()[gridMain.SelectedRow];
        if (ch.Eformat == ElectronicClaimFormat.x837_5010_med_inst)
        {
            //med/inst clearinghouse
            MsgBox.show(this,"The selected clearinghouse must first be set to a dental e-claim format.");
            return ;
        }
         
        Prefs.updateLong(PrefName.ClearinghouseDefaultDent,ch.ClearinghouseNum);
        fillGrid();
        DataValid.setInvalid(InvalidType.Prefs);
    }

    private void butDefaultMedical_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.SelectedRow == -1)
        {
            MsgBox.show(this,"Please select a row first.");
            return ;
        }
         
        Clearinghouse ch = Clearinghouses.getListt()[gridMain.SelectedRow];
        if (ch.Eformat != ElectronicClaimFormat.x837_5010_med_inst)
        {
            //anything except the med/inst format
            MsgBox.show(this,"The selected clearinghouse must first be set to the med/inst e-claim format.");
            return ;
        }
         
        Prefs.UpdateLong(PrefName.ClearinghouseDefaultMed, Clearinghouses.getListt()[gridMain.SelectedRow].ClearinghouseNum);
        fillGrid();
        DataValid.setInvalid(InvalidType.Prefs);
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formClearinghouses_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (PrefC.getLong(PrefName.ClearinghouseDefaultDent) == 0)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"A default clearinghouse should be set. Continue anyway?"))
            {
                e.Cancel = true;
                return ;
            }
             
        }
         
        //validate that the default dental clearinghouse is not type mismatched.
        Clearinghouse chDent = Clearinghouses.getClearinghouse(PrefC.getLong(PrefName.ClearinghouseDefaultDent));
        if (chDent != null)
        {
            if (chDent.Eformat == ElectronicClaimFormat.x837_5010_med_inst)
            {
                //mismatch
                if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"The default dental clearinghouse should be set to a dental e-claim format.  Continue anyway?"))
                {
                    e.Cancel = true;
                    return ;
                }
                 
            }
             
        }
         
        //validate medical clearinghouse
        Clearinghouse chMed = Clearinghouses.getClearinghouse(PrefC.getLong(PrefName.ClearinghouseDefaultMed));
        if (chMed != null)
        {
            if (chMed.Eformat != ElectronicClaimFormat.x837_5010_med_inst)
            {
                //mismatch
                if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"The default medical clearinghouse should be set to a med/inst e-claim format.  Continue anyway?"))
                {
                    e.Cancel = true;
                    return ;
                }
                 
            }
             
        }
         
        if (listHasChanged)
        {
            //update all computers including this one:
            DataValid.setInvalid(InvalidType.ClearHouses);
        }
         
    }

}


