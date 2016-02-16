//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormReferenceEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CustReference;
import OpenDentBusiness.CustReferences;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormReference  extends Form 
{

    private DataTable RefTable = new DataTable();
    public List<CustReference> SelectedCustRefs = new List<CustReference>();
    public long GotoPatNum = new long();
    public FormReference() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formReference_Load(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()].Length;i++)
        {
            listBillingType.Items.Add(DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][i].ItemName);
            listBillingType.SetSelected(i, true);
        }
        listBillingType.TopIndex = 0;
        listBillingType.SelectedIndexChanged += new EventHandler(listBillingType_SelectedIndexChanged);
        fillMain(true);
    }

    private void fillMain(boolean limit) throws Exception {
        int age = 0;
        try
        {
            age = PIn.Int(textAge.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        int superFam = 0;
        try
        {
            superFam = PIn.Int(textSuperFamily.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        long[] billingTypes = new long[listBillingType.SelectedIndices.Count];
        if (listBillingType.SelectedIndices.Count != 0)
        {
            for (int i = 0;i < listBillingType.SelectedIndices.Count;i++)
            {
                billingTypes[i] = DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][listBillingType.SelectedIndices[i]].DefNum;
            }
        }
         
        RefTable = CustReferences.GetReferenceTable(limit, billingTypes, checkBadRefs.Checked, checkUsedRefs.Checked, checkGuarOnly.Checked, textCity.Text, textState.Text, textZip.Text, textAreaCode.Text, textSpecialty.Text, superFam, textLName.Text, textFName.Text, textPatNum.Text, age);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("PatNum",50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("First Name",75);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Last Name",75);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("HmPhone",90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("State",45);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("City",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Zip Code",60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Specialty",90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Age",40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Super",50);
        col.setTextAlign(HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Last Used",70);
        col.setTextAlign(HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Times Used",70);
        col.setTextAlign(HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        if (checkBadRefs.Checked)
        {
            col = new ODGridColumn("Bad",50);
            col.setTextAlign(HorizontalAlignment.Center);
            gridMain.getColumns().add(col);
        }
         
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < RefTable.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(RefTable.Rows[i]["PatNum"].ToString());
            row.getCells().Add(RefTable.Rows[i]["FName"].ToString());
            row.getCells().Add(RefTable.Rows[i]["LName"].ToString());
            row.getCells().Add(RefTable.Rows[i]["HmPhone"].ToString());
            row.getCells().Add(RefTable.Rows[i]["State"].ToString());
            row.getCells().Add(RefTable.Rows[i]["City"].ToString());
            row.getCells().Add(RefTable.Rows[i]["Zip"].ToString());
            row.getCells().Add(RefTable.Rows[i]["Specialty"].ToString());
            row.getCells().Add(RefTable.Rows[i]["age"].ToString());
            row.getCells().Add(RefTable.Rows[i]["SuperFamily"].ToString());
            row.getCells().Add(RefTable.Rows[i]["DateMostRecent"].ToString());
            row.getCells().Add(RefTable.Rows[i]["TimesUsed"].ToString());
            if (checkBadRefs.Checked)
            {
                row.getCells().Add(RefTable.Rows[i]["IsBadRef"].ToString());
            }
             
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setSelected(0,true);
    }

    private void onDataEntered() throws Exception {
        if (checkRefresh.Checked)
        {
            fillMain(true);
        }
         
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        CustReference refCur = CustReferences.GetOne(PIn.Long(RefTable.Rows[e.getRow()]["CustReferenceNum"].ToString()));
        FormReferenceEdit FormRE = new FormReferenceEdit(refCur);
        FormRE.ShowDialog();
        fillMain(true);
    }

    private void gridMain_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button != MouseButtons.Right)
        {
            return ;
        }
         
        if (gridMain.getSelectedIndices().Length != 1)
        {
            return ;
        }
         
        menuRightReferences.Show(gridMain, new Point(e.X, e.Y));
    }

    private void goToFamilyToolStripMenuItem_Click(Object sender, EventArgs e) throws Exception {
        GotoPatNum = PIn.Long(RefTable.Rows[gridMain.getSelectedIndex()]["PatNum"].ToString());
        Close();
    }

    private void listBillingType_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void checkUsedRefs_Click(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void checkBadRefs_Click(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void butSearch_Click(Object sender, EventArgs e) throws Exception {
        fillMain(true);
    }

    private void butGetAll_Click(Object sender, EventArgs e) throws Exception {
        Cursor.Current = Cursors.WaitCursor;
        fillMain(false);
        Cursor.Current = Cursors.Default;
    }

    private void textCity_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textState_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textZip_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textAreaCode_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textSpecialty_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textSuperFamily_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textLName_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textFName_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textPatNum_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textAge_TextChanged(Object sender, EventArgs e) throws Exception {
        onDataEntered();
    }

    private void textCity_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textState_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textZip_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textAreaCode_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textSpecialty_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textSuperFamily_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textLName_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textFName_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textPatNum_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void textAge_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Up || e.KeyCode == Keys.Down)
        {
            gridMain_KeyDown(sender,e);
            gridMain.Invalidate();
            e.Handled = true;
        }
         
    }

    private void gridMain_KeyDown(Object sender, KeyEventArgs e) throws Exception {
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndices().Length < 1)
        {
            MsgBox.show(this,"Select at least one reference.");
            return ;
        }
         
        SelectedCustRefs = new List<CustReference>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            CustReference custRef = CustReferences.GetOne(PIn.Long(RefTable.Rows[gridMain.getSelectedIndices()[i]]["CustReferenceNum"].ToString()));
            custRef.DateMostRecent = DateTime.Now;
            CustReferences.update(custRef);
            SelectedCustRefs.Add(custRef);
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
        this.components = new System.ComponentModel.Container();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textSuperFamily = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textSpecialty = new System.Windows.Forms.TextBox();
        this.textAge = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textAreaCode = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textLName = new System.Windows.Forms.TextBox();
        this.textZip = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.textFName = new System.Windows.Forms.TextBox();
        this.textPatNum = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.textState = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textCity = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.checkBadRefs = new System.Windows.Forms.CheckBox();
        this.groupFilter = new System.Windows.Forms.GroupBox();
        this.listBillingType = new System.Windows.Forms.ListBox();
        this.label10 = new System.Windows.Forms.Label();
        this.checkUsedRefs = new System.Windows.Forms.CheckBox();
        this.checkGuarOnly = new System.Windows.Forms.CheckBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.checkRefresh = new System.Windows.Forms.CheckBox();
        this.butGetAll = new OpenDental.UI.Button();
        this.butSearch = new OpenDental.UI.Button();
        this.menuRightReferences = new System.Windows.Forms.ContextMenuStrip(this.components);
        this.goToAccountToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        this.label11 = new System.Windows.Forms.Label();
        this.groupBox2.SuspendLayout();
        this.groupFilter.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.menuRightReferences.SuspendLayout();
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
        this.butOK.Location = new System.Drawing.Point(983, 633);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
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
        this.butCancel.Location = new System.Drawing.Point(1064, 633);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(847, 642);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("Select  References");
        this.gridMain.setTranslationName("FormPatientSelect");
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
        this.gridMain.KeyDown += new System.Windows.Forms.KeyEventHandler(this.gridMain_KeyDown);
        this.gridMain.MouseUp += new System.Windows.Forms.MouseEventHandler(this.gridMain_MouseUp);
        //
        // groupBox2
        //
        this.groupBox2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox2.Controls.Add(this.label6);
        this.groupBox2.Controls.Add(this.textSuperFamily);
        this.groupBox2.Controls.Add(this.label5);
        this.groupBox2.Controls.Add(this.textSpecialty);
        this.groupBox2.Controls.Add(this.textAge);
        this.groupBox2.Controls.Add(this.label2);
        this.groupBox2.Controls.Add(this.textAreaCode);
        this.groupBox2.Controls.Add(this.label4);
        this.groupBox2.Controls.Add(this.label1);
        this.groupBox2.Controls.Add(this.textLName);
        this.groupBox2.Controls.Add(this.textZip);
        this.groupBox2.Controls.Add(this.label3);
        this.groupBox2.Controls.Add(this.label12);
        this.groupBox2.Controls.Add(this.textFName);
        this.groupBox2.Controls.Add(this.textPatNum);
        this.groupBox2.Controls.Add(this.label9);
        this.groupBox2.Controls.Add(this.textState);
        this.groupBox2.Controls.Add(this.label8);
        this.groupBox2.Controls.Add(this.textCity);
        this.groupBox2.Controls.Add(this.label7);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(870, 5);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(269, 220);
        this.groupBox2.TabIndex = 10;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Search by:";
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(8, 113);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(154, 20);
        this.label6.TabIndex = 31;
        this.label6.Text = "# (or more) in Super Family";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSuperFamily
        //
        this.textSuperFamily.Location = new System.Drawing.Point(163, 113);
        this.textSuperFamily.Name = "textSuperFamily";
        this.textSuperFamily.Size = new System.Drawing.Size(90, 20);
        this.textSuperFamily.TabIndex = 6;
        this.textSuperFamily.TextChanged += new System.EventHandler(this.textSuperFamily_TextChanged);
        this.textSuperFamily.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textSuperFamily_KeyDown);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(59, 93);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(103, 20);
        this.label5.TabIndex = 29;
        this.label5.Text = "Specialty";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSpecialty
        //
        this.textSpecialty.Location = new System.Drawing.Point(163, 93);
        this.textSpecialty.Name = "textSpecialty";
        this.textSpecialty.Size = new System.Drawing.Size(90, 20);
        this.textSpecialty.TabIndex = 5;
        this.textSpecialty.TextChanged += new System.EventHandler(this.textSpecialty_TextChanged);
        this.textSpecialty.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textSpecialty_KeyDown);
        //
        // textAge
        //
        this.textAge.Location = new System.Drawing.Point(163, 193);
        this.textAge.Name = "textAge";
        this.textAge.Size = new System.Drawing.Size(90, 20);
        this.textAge.TabIndex = 10;
        this.textAge.TextChanged += new System.EventHandler(this.textAge_TextChanged);
        this.textAge.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textAge_KeyDown);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(65, 193);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(99, 20);
        this.label2.TabIndex = 27;
        this.label2.Text = "Age At Least";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAreaCode
        //
        this.textAreaCode.Location = new System.Drawing.Point(163, 73);
        this.textAreaCode.Name = "textAreaCode";
        this.textAreaCode.Size = new System.Drawing.Size(90, 20);
        this.textAreaCode.TabIndex = 4;
        this.textAreaCode.TextChanged += new System.EventHandler(this.textAreaCode_TextChanged);
        this.textAreaCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textAreaCode_KeyDown);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(34, 73);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(129, 20);
        this.label4.TabIndex = 7;
        this.label4.Text = "Area Code";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(59, 133);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(103, 20);
        this.label1.TabIndex = 3;
        this.label1.Text = "Last Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textLName
        //
        this.textLName.Location = new System.Drawing.Point(163, 133);
        this.textLName.Name = "textLName";
        this.textLName.Size = new System.Drawing.Size(90, 20);
        this.textLName.TabIndex = 7;
        this.textLName.TextChanged += new System.EventHandler(this.textLName_TextChanged);
        this.textLName.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textLName_KeyDown);
        //
        // textZip
        //
        this.textZip.Location = new System.Drawing.Point(163, 53);
        this.textZip.Name = "textZip";
        this.textZip.Size = new System.Drawing.Size(90, 20);
        this.textZip.TabIndex = 3;
        this.textZip.TextChanged += new System.EventHandler(this.textZip_TextChanged);
        this.textZip.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textZip_KeyDown);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(62, 153);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 20);
        this.label3.TabIndex = 5;
        this.label3.Text = "First Name";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(62, 53);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(101, 20);
        this.label12.TabIndex = 24;
        this.label12.Text = "Zip";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFName
        //
        this.textFName.Location = new System.Drawing.Point(163, 153);
        this.textFName.Name = "textFName";
        this.textFName.Size = new System.Drawing.Size(90, 20);
        this.textFName.TabIndex = 8;
        this.textFName.TextChanged += new System.EventHandler(this.textFName_TextChanged);
        this.textFName.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textFName_KeyDown);
        //
        // textPatNum
        //
        this.textPatNum.Location = new System.Drawing.Point(163, 173);
        this.textPatNum.Name = "textPatNum";
        this.textPatNum.Size = new System.Drawing.Size(90, 20);
        this.textPatNum.TabIndex = 9;
        this.textPatNum.TextChanged += new System.EventHandler(this.textPatNum_TextChanged);
        this.textPatNum.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textPatNum_KeyDown);
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(63, 173);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(101, 20);
        this.label9.TabIndex = 18;
        this.label9.Text = "Patient Number";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textState
        //
        this.textState.Location = new System.Drawing.Point(163, 33);
        this.textState.Name = "textState";
        this.textState.Size = new System.Drawing.Size(90, 20);
        this.textState.TabIndex = 2;
        this.textState.TextChanged += new System.EventHandler(this.textState_TextChanged);
        this.textState.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textState_KeyDown);
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(62, 33);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(100, 20);
        this.label8.TabIndex = 16;
        this.label8.Text = "State";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCity
        //
        this.textCity.Location = new System.Drawing.Point(163, 13);
        this.textCity.Name = "textCity";
        this.textCity.Size = new System.Drawing.Size(90, 20);
        this.textCity.TabIndex = 1;
        this.textCity.TextChanged += new System.EventHandler(this.textCity_TextChanged);
        this.textCity.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textCity_KeyDown);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(62, 13);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(98, 20);
        this.label7.TabIndex = 14;
        this.label7.Text = "City";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkBadRefs
        //
        this.checkBadRefs.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkBadRefs.Location = new System.Drawing.Point(11, 243);
        this.checkBadRefs.Name = "checkBadRefs";
        this.checkBadRefs.Size = new System.Drawing.Size(187, 17);
        this.checkBadRefs.TabIndex = 12;
        this.checkBadRefs.Text = "Include bad references";
        this.checkBadRefs.Click += new System.EventHandler(this.checkBadRefs_Click);
        //
        // groupFilter
        //
        this.groupFilter.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupFilter.Controls.Add(this.listBillingType);
        this.groupFilter.Controls.Add(this.label10);
        this.groupFilter.Controls.Add(this.checkUsedRefs);
        this.groupFilter.Controls.Add(this.checkGuarOnly);
        this.groupFilter.Controls.Add(this.checkBadRefs);
        this.groupFilter.Location = new System.Drawing.Point(870, 227);
        this.groupFilter.Name = "groupFilter";
        this.groupFilter.Size = new System.Drawing.Size(269, 288);
        this.groupFilter.TabIndex = 45;
        this.groupFilter.TabStop = false;
        this.groupFilter.Text = "Filters:";
        //
        // listBillingType
        //
        this.listBillingType.FormattingEnabled = true;
        this.listBillingType.Location = new System.Drawing.Point(11, 32);
        this.listBillingType.Name = "listBillingType";
        this.listBillingType.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listBillingType.Size = new System.Drawing.Size(252, 186);
        this.listBillingType.TabIndex = 60;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(8, 15);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(121, 16);
        this.label10.TabIndex = 59;
        this.label10.Text = "Billing Type";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // checkUsedRefs
        //
        this.checkUsedRefs.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkUsedRefs.Location = new System.Drawing.Point(11, 224);
        this.checkUsedRefs.Name = "checkUsedRefs";
        this.checkUsedRefs.Size = new System.Drawing.Size(187, 17);
        this.checkUsedRefs.TabIndex = 11;
        this.checkUsedRefs.Text = "Used references only";
        this.checkUsedRefs.Click += new System.EventHandler(this.checkUsedRefs_Click);
        //
        // checkGuarOnly
        //
        this.checkGuarOnly.Checked = true;
        this.checkGuarOnly.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkGuarOnly.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkGuarOnly.Location = new System.Drawing.Point(11, 262);
        this.checkGuarOnly.Name = "checkGuarOnly";
        this.checkGuarOnly.Size = new System.Drawing.Size(187, 17);
        this.checkGuarOnly.TabIndex = 12;
        this.checkGuarOnly.Text = "Guarantors only";
        this.checkGuarOnly.Click += new System.EventHandler(this.checkBadRefs_Click);
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox1.Controls.Add(this.checkRefresh);
        this.groupBox1.Controls.Add(this.butGetAll);
        this.groupBox1.Controls.Add(this.butSearch);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(870, 516);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(269, 69);
        this.groupBox1.TabIndex = 47;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Search";
        //
        // checkRefresh
        //
        this.checkRefresh.Checked = true;
        this.checkRefresh.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkRefresh.Location = new System.Drawing.Point(43, 47);
        this.checkRefresh.Name = "checkRefresh";
        this.checkRefresh.Size = new System.Drawing.Size(195, 18);
        this.checkRefresh.TabIndex = 11;
        this.checkRefresh.Text = "Refresh while typing";
        this.checkRefresh.UseVisualStyleBackColor = true;
        //
        // butGetAll
        //
        this.butGetAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGetAll.setAutosize(true);
        this.butGetAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGetAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGetAll.setCornerRadius(4F);
        this.butGetAll.Location = new System.Drawing.Point(163, 18);
        this.butGetAll.Name = "butGetAll";
        this.butGetAll.Size = new System.Drawing.Size(75, 23);
        this.butGetAll.TabIndex = 10;
        this.butGetAll.Text = "Get All";
        this.butGetAll.Click += new System.EventHandler(this.butGetAll_Click);
        //
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.Location = new System.Drawing.Point(43, 18);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 23);
        this.butSearch.TabIndex = 7;
        this.butSearch.Text = "&Search";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // menuRightReferences
        //
        this.menuRightReferences.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.goToAccountToolStripMenuItem });
        this.menuRightReferences.Name = "menuRightReferences";
        this.menuRightReferences.Size = new System.Drawing.Size(142, 26);
        //
        // goToAccountToolStripMenuItem
        //
        this.goToAccountToolStripMenuItem.Name = "goToAccountToolStripMenuItem";
        this.goToAccountToolStripMenuItem.Size = new System.Drawing.Size(141, 22);
        this.goToAccountToolStripMenuItem.Text = "Go to Family";
        this.goToAccountToolStripMenuItem.Click += new System.EventHandler(this.goToFamilyToolStripMenuItem_Click);
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(913, 598);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(146, 39);
        this.label11.TabIndex = 32;
        this.label11.Text = "(use selected as reference for this customer)";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormReference
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(1151, 666);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.groupFilter);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.butCancel);
        this.Name = "FormReference";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = " Reference Select";
        this.Load += new System.EventHandler(this.FormReference_Load);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.groupFilter.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.menuRightReferences.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textAge = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textZip = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textState = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCity = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAreaCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textLName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkBadRefs = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSuperFamily = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSpecialty = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupFilter = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox checkUsedRefs = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox checkRefresh = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butGetAll;
    private OpenDental.UI.Button butSearch;
    private System.Windows.Forms.ContextMenuStrip menuRightReferences = new System.Windows.Forms.ContextMenuStrip();
    private System.Windows.Forms.ToolStripMenuItem goToAccountToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
    private System.Windows.Forms.CheckBox checkGuarOnly = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listBillingType = new System.Windows.Forms.ListBox();
}


