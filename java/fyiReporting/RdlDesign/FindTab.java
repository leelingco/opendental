//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:20 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.RdlEditPreview;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    The RDL project is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* Summary description for FindTab.
*/
public class FindTab  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.TabPage tabFind = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox txtFind = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.RadioButton radioUp = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioDown = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox chkCase = new System.Windows.Forms.CheckBox();
    public System.Windows.Forms.TabPage tabGoTo = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox txtLine = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button btnNext = new System.Windows.Forms.Button();
    private RdlEditPreview rdlEdit;
    private System.Windows.Forms.Button btnGoto = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button btnCancel = new System.Windows.Forms.Button();
    public System.Windows.Forms.TabPage tabReplace = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.Button btnFindNext = new System.Windows.Forms.Button();
    private System.Windows.Forms.CheckBox chkMatchCase = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Button btnReplaceAll = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button btnReplace = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox txtFindR = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox txtReplace = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button bCloseReplace = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bCloseGoto = new System.Windows.Forms.Button();
    public System.Windows.Forms.TabControl tcFRG = new System.Windows.Forms.TabControl();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public FindTab() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        this.AcceptButton = btnNext;
        this.CancelButton = btnCancel;
        txtFind.Focus();
    }

    public FindTab(RdlEditPreview pad) throws Exception {
        rdlEdit = pad;
        initializeComponent();
        this.AcceptButton = btnNext;
        this.CancelButton = btnCancel;
        txtFind.Focus();
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
        this.tcFRG = new System.Windows.Forms.TabControl();
        this.tabFind = new System.Windows.Forms.TabPage();
        this.btnCancel = new System.Windows.Forms.Button();
        this.btnNext = new System.Windows.Forms.Button();
        this.chkCase = new System.Windows.Forms.CheckBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.radioUp = new System.Windows.Forms.RadioButton();
        this.radioDown = new System.Windows.Forms.RadioButton();
        this.label1 = new System.Windows.Forms.Label();
        this.txtFind = new System.Windows.Forms.TextBox();
        this.tabReplace = new System.Windows.Forms.TabPage();
        this.bCloseReplace = new System.Windows.Forms.Button();
        this.btnFindNext = new System.Windows.Forms.Button();
        this.chkMatchCase = new System.Windows.Forms.CheckBox();
        this.btnReplaceAll = new System.Windows.Forms.Button();
        this.btnReplace = new System.Windows.Forms.Button();
        this.txtFindR = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.txtReplace = new System.Windows.Forms.TextBox();
        this.tabGoTo = new System.Windows.Forms.TabPage();
        this.bCloseGoto = new System.Windows.Forms.Button();
        this.txtLine = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.btnGoto = new System.Windows.Forms.Button();
        this.tcFRG.SuspendLayout();
        this.tabFind.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.tabReplace.SuspendLayout();
        this.tabGoTo.SuspendLayout();
        this.SuspendLayout();
        //
        // tcFRG
        //
        this.tcFRG.Controls.Add(this.tabFind);
        this.tcFRG.Controls.Add(this.tabReplace);
        this.tcFRG.Controls.Add(this.tabGoTo);
        this.tcFRG.Location = new System.Drawing.Point(8, 8);
        this.tcFRG.Name = "tcFRG";
        this.tcFRG.SelectedIndex = 0;
        this.tcFRG.Size = new System.Drawing.Size(432, 192);
        this.tcFRG.TabIndex = 0;
        this.tcFRG.Enter += new System.EventHandler(this.tcFRG_Enter);
        this.tcFRG.SelectedIndexChanged += new System.EventHandler(this.tcFRG_SelectedIndexChanged);
        //
        // tabFind
        //
        this.tabFind.Controls.Add(this.btnCancel);
        this.tabFind.Controls.Add(this.btnNext);
        this.tabFind.Controls.Add(this.chkCase);
        this.tabFind.Controls.Add(this.groupBox1);
        this.tabFind.Controls.Add(this.label1);
        this.tabFind.Controls.Add(this.txtFind);
        this.tabFind.Location = new System.Drawing.Point(4, 22);
        this.tabFind.Name = "tabFind";
        this.tabFind.Size = new System.Drawing.Size(424, 166);
        this.tabFind.TabIndex = 0;
        this.tabFind.Tag = "find";
        this.tabFind.Text = "Find";
        //
        // btnCancel
        //
        this.btnCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.btnCancel.Location = new System.Drawing.Point(344, 128);
        this.btnCancel.Name = "btnCancel";
        this.btnCancel.TabIndex = 3;
        this.btnCancel.Text = "Close";
        this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
        //
        // btnNext
        //
        this.btnNext.Enabled = false;
        this.btnNext.Location = new System.Drawing.Point(344, 16);
        this.btnNext.Name = "btnNext";
        this.btnNext.TabIndex = 1;
        this.btnNext.Text = "Find Next";
        this.btnNext.Click += new System.EventHandler(this.btnNext_Click);
        //
        // chkCase
        //
        this.chkCase.Location = new System.Drawing.Point(208, 72);
        this.chkCase.Name = "chkCase";
        this.chkCase.Size = new System.Drawing.Size(88, 24);
        this.chkCase.TabIndex = 2;
        this.chkCase.Text = "Match Case";
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.radioUp);
        this.groupBox1.Controls.Add(this.radioDown);
        this.groupBox1.Location = new System.Drawing.Point(16, 48);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(176, 64);
        this.groupBox1.TabIndex = 2;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Search Direction";
        //
        // radioUp
        //
        this.radioUp.Location = new System.Drawing.Point(16, 24);
        this.radioUp.Name = "radioUp";
        this.radioUp.Size = new System.Drawing.Size(56, 24);
        this.radioUp.TabIndex = 0;
        this.radioUp.Text = "Up";
        this.radioUp.CheckedChanged += new System.EventHandler(this.radioUp_CheckedChanged);
        //
        // radioDown
        //
        this.radioDown.Location = new System.Drawing.Point(104, 24);
        this.radioDown.Name = "radioDown";
        this.radioDown.Size = new System.Drawing.Size(56, 24);
        this.radioDown.TabIndex = 1;
        this.radioDown.Text = "Down";
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(76, 23);
        this.label1.TabIndex = 0;
        this.label1.Text = "Find";
        //
        // txtFind
        //
        this.txtFind.Location = new System.Drawing.Point(96, 16);
        this.txtFind.Name = "txtFind";
        this.txtFind.Size = new System.Drawing.Size(216, 20);
        this.txtFind.TabIndex = 0;
        this.txtFind.Text = "";
        this.txtFind.TextChanged += new System.EventHandler(this.txtFind_TextChanged);
        //
        // tabReplace
        //
        this.tabReplace.Controls.Add(this.bCloseReplace);
        this.tabReplace.Controls.Add(this.btnFindNext);
        this.tabReplace.Controls.Add(this.chkMatchCase);
        this.tabReplace.Controls.Add(this.btnReplaceAll);
        this.tabReplace.Controls.Add(this.btnReplace);
        this.tabReplace.Controls.Add(this.txtFindR);
        this.tabReplace.Controls.Add(this.label3);
        this.tabReplace.Controls.Add(this.label2);
        this.tabReplace.Controls.Add(this.txtReplace);
        this.tabReplace.Location = new System.Drawing.Point(4, 22);
        this.tabReplace.Name = "tabReplace";
        this.tabReplace.Size = new System.Drawing.Size(424, 166);
        this.tabReplace.TabIndex = 1;
        this.tabReplace.Tag = "replace";
        this.tabReplace.Text = "Replace";
        //
        // bCloseReplace
        //
        this.bCloseReplace.Location = new System.Drawing.Point(344, 128);
        this.bCloseReplace.Name = "bCloseReplace";
        this.bCloseReplace.TabIndex = 6;
        this.bCloseReplace.Text = "Close";
        this.bCloseReplace.Click += new System.EventHandler(this.btnCancel_Click);
        //
        // btnFindNext
        //
        this.btnFindNext.Enabled = false;
        this.btnFindNext.Location = new System.Drawing.Point(344, 16);
        this.btnFindNext.Name = "btnFindNext";
        this.btnFindNext.TabIndex = 3;
        this.btnFindNext.Text = "FindNext";
        this.btnFindNext.Click += new System.EventHandler(this.btnFindNext_Click);
        //
        // chkMatchCase
        //
        this.chkMatchCase.Location = new System.Drawing.Point(8, 88);
        this.chkMatchCase.Name = "chkMatchCase";
        this.chkMatchCase.TabIndex = 2;
        this.chkMatchCase.Text = "Match Case";
        //
        // btnReplaceAll
        //
        this.btnReplaceAll.Enabled = false;
        this.btnReplaceAll.Location = new System.Drawing.Point(344, 80);
        this.btnReplaceAll.Name = "btnReplaceAll";
        this.btnReplaceAll.TabIndex = 5;
        this.btnReplaceAll.Text = "ReplaceAll";
        this.btnReplaceAll.Click += new System.EventHandler(this.btnReplaceAll_Click);
        //
        // btnReplace
        //
        this.btnReplace.Enabled = false;
        this.btnReplace.Location = new System.Drawing.Point(344, 48);
        this.btnReplace.Name = "btnReplace";
        this.btnReplace.TabIndex = 4;
        this.btnReplace.Text = "Replace";
        this.btnReplace.Click += new System.EventHandler(this.btnReplace_Click);
        //
        // txtFindR
        //
        this.txtFindR.Location = new System.Drawing.Point(96, 16);
        this.txtFindR.Name = "txtFindR";
        this.txtFindR.Size = new System.Drawing.Size(224, 20);
        this.txtFindR.TabIndex = 0;
        this.txtFindR.Text = "";
        this.txtFindR.TextChanged += new System.EventHandler(this.txtFindR_TextChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(14, 16);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(76, 23);
        this.label3.TabIndex = 0;
        this.label3.Text = "Find";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(14, 56);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(76, 23);
        this.label2.TabIndex = 5;
        this.label2.Text = "Replace With";
        //
        // txtReplace
        //
        this.txtReplace.Location = new System.Drawing.Point(96, 56);
        this.txtReplace.Name = "txtReplace";
        this.txtReplace.Size = new System.Drawing.Size(224, 20);
        this.txtReplace.TabIndex = 1;
        this.txtReplace.Text = "";
        //
        // tabGoTo
        //
        this.tabGoTo.Controls.Add(this.bCloseGoto);
        this.tabGoTo.Controls.Add(this.txtLine);
        this.tabGoTo.Controls.Add(this.label4);
        this.tabGoTo.Controls.Add(this.btnGoto);
        this.tabGoTo.Location = new System.Drawing.Point(4, 22);
        this.tabGoTo.Name = "tabGoTo";
        this.tabGoTo.Size = new System.Drawing.Size(424, 166);
        this.tabGoTo.TabIndex = 2;
        this.tabGoTo.Tag = "goto";
        this.tabGoTo.Text = "GoTo";
        //
        // bCloseGoto
        //
        this.bCloseGoto.Location = new System.Drawing.Point(344, 128);
        this.bCloseGoto.Name = "bCloseGoto";
        this.bCloseGoto.TabIndex = 2;
        this.bCloseGoto.Text = "Close";
        this.bCloseGoto.Click += new System.EventHandler(this.btnCancel_Click);
        //
        // txtLine
        //
        this.txtLine.Location = new System.Drawing.Point(96, 16);
        this.txtLine.Name = "txtLine";
        this.txtLine.TabIndex = 0;
        this.txtLine.Text = "";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(16, 16);
        this.label4.Name = "label4";
        this.label4.TabIndex = 2;
        this.label4.Text = "Line Number";
        //
        // btnGoto
        //
        this.btnGoto.Location = new System.Drawing.Point(344, 16);
        this.btnGoto.Name = "btnGoto";
        this.btnGoto.TabIndex = 1;
        this.btnGoto.Text = "GoTo";
        this.btnGoto.Click += new System.EventHandler(this.btnGoto_Click);
        //
        // FindTab
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.btnCancel;
        this.ClientSize = new System.Drawing.Size(448, 206);
        this.Controls.Add(this.tcFRG);
        this.Name = "FindTab";
        this.Text = "Find";
        this.TopMost = true;
        this.tcFRG.ResumeLayout(false);
        this.tabFind.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.tabReplace.ResumeLayout(false);
        this.tabGoTo.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void radioUp_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
    }

    private void btnNext_Click(Object sender, System.EventArgs e) throws Exception {
        rdlEdit.FindNext(this, txtFind.Text, chkCase.Checked);
    }

    private void txtFind_TextChanged(Object sender, System.EventArgs e) throws Exception {
        if (StringSupport.equals(txtFind.Text, ""))
            btnNext.Enabled = false;
        else
            btnNext.Enabled = true; 
    }

    private void btnFindNext_Click(Object sender, System.EventArgs e) throws Exception {
        rdlEdit.FindNext(this, txtFindR.Text, chkCase.Checked);
        txtFind.Focus();
    }

    private void btnReplace_Click(Object sender, System.EventArgs e) throws Exception {
        rdlEdit.FindNext(this, txtFindR.Text, chkCase.Checked);
        rdlEdit.ReplaceNext(this, txtFindR.Text, txtReplace.Text, chkCase.Checked);
        txtFindR.Focus();
    }

    private void txtFindR_TextChanged(Object sender, System.EventArgs e) throws Exception {
        boolean bEnable = (StringSupport.equals(txtFindR.Text, "")) ? false : true;
        btnFindNext.Enabled = bEnable;
        btnReplace.Enabled = bEnable;
        btnReplaceAll.Enabled = bEnable;
    }

    private void btnReplaceAll_Click(Object sender, System.EventArgs e) throws Exception {
        rdlEdit.ReplaceAll(this, txtFindR.Text, txtReplace.Text, chkCase.Checked);
        txtFindR.Focus();
    }

    private void btnGoto_Click(Object sender, System.EventArgs e) throws Exception {
        try
        {
            try
            {
                int nLine = Int32.Parse(txtLine.Text);
                rdlEdit.Goto(this, nLine);
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, ex.Message, "Invalid Line Number");
            }

            txtLine.Focus();
        }
        catch (Exception er)
        {
            er.ToString();
        }
    
    }

    private void btnCancel_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void tcFRG_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        TabControl tc = (TabControl)sender;
        String tag = (String)tc.TabPages[tc.SelectedIndex].Tag;
        System.String __dummyScrutVar0 = tag;
        if (__dummyScrutVar0.equals("find"))
        {
            this.AcceptButton = btnNext;
            this.CancelButton = btnCancel;
            txtFind.Focus();
        }
        else if (__dummyScrutVar0.equals("replace"))
        {
            this.AcceptButton = this.btnFindNext;
            this.CancelButton = this.bCloseReplace;
            txtFindR.Focus();
        }
        else if (__dummyScrutVar0.equals("goto"))
        {
            this.AcceptButton = btnGoto;
            this.CancelButton = this.bCloseGoto;
            txtLine.Focus();
        }
           
    }

    private void tcFRG_Enter(Object sender, System.EventArgs e) throws Exception {
        tcFRG_SelectedIndexChanged(this.tcFRG, new EventArgs());
    }

}


