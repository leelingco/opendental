//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:19 PM
//

package fyiReporting.RdlDesign;


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
* Summary description for DialogNew.
*/
public class DialogNew  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.ListView listNewChoices = new System.Windows.Forms.ListView();
    private System.Windows.Forms.Button btnOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button btnCancel = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private String _resultType = new String();
    public DialogNew() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        //
        // TODO: Add any constructor code after InitializeComponent call
        //
        listNewChoices.Clear();
        listNewChoices.BeginUpdate();
        ListViewItem lvi = new ListViewItem("Blank");
        listNewChoices.Items.Add(lvi);
        lvi = new ListViewItem("Data Base");
        listNewChoices.Items.Add(lvi);
        listNewChoices.LabelWrap = true;
        listNewChoices.Select();
        listNewChoices.EndUpdate();
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
        this.listNewChoices = new System.Windows.Forms.ListView();
        this.btnOK = new System.Windows.Forms.Button();
        this.btnCancel = new System.Windows.Forms.Button();
        this.panel1 = new System.Windows.Forms.Panel();
        this.panel1.SuspendLayout();
        this.SuspendLayout();
        //
        // listNewChoices
        //
        this.listNewChoices.Dock = System.Windows.Forms.DockStyle.Fill;
        this.listNewChoices.HideSelection = false;
        this.listNewChoices.Location = new System.Drawing.Point(0, 0);
        this.listNewChoices.MultiSelect = false;
        this.listNewChoices.Name = "listNewChoices";
        this.listNewChoices.Size = new System.Drawing.Size(448, 366);
        this.listNewChoices.TabIndex = 0;
        this.listNewChoices.ItemActivate += new System.EventHandler(this.listNewChoices_ItemActivate);
        this.listNewChoices.SelectedIndexChanged += new System.EventHandler(this.listNewChoices_SelectedIndexChanged);
        //
        // btnOK
        //
        this.btnOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.btnOK.Enabled = false;
        this.btnOK.Location = new System.Drawing.Point(280, 4);
        this.btnOK.Name = "btnOK";
        this.btnOK.TabIndex = 1;
        this.btnOK.Text = "OK";
        this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
        //
        // btnCancel
        //
        this.btnCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.btnCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.btnCancel.Location = new System.Drawing.Point(368, 4);
        this.btnCancel.Name = "btnCancel";
        this.btnCancel.TabIndex = 2;
        this.btnCancel.Text = "Cancel";
        //
        // panel1
        //
        this.panel1.Controls.Add(this.btnOK);
        this.panel1.Controls.Add(this.btnCancel);
        this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.panel1.Location = new System.Drawing.Point(0, 334);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(448, 32);
        this.panel1.TabIndex = 3;
        //
        // DialogNew
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(448, 366);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.listNewChoices);
        this.Name = "DialogNew";
        this.Text = "New";
        this.panel1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void listNewChoices_ItemActivate(Object sender, System.EventArgs e) throws Exception {
        for (Object __dummyForeachVar0 : listNewChoices.SelectedItems)
        {
            ListViewItem lvi = (ListViewItem)__dummyForeachVar0;
            _resultType = lvi.Text;
            break;
        }
        DialogResult = DialogResult.OK;
        this.Close();
    }

    private void btnOK_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
        this.Close();
    }

    private void listNewChoices_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        for (Object __dummyForeachVar1 : listNewChoices.SelectedItems)
        {
            ListViewItem lvi = (ListViewItem)__dummyForeachVar1;
            _resultType = lvi.Text;
            break;
        }
        btnOK.Enabled = true;
    }

    public String getResultType() throws Exception {
        return _resultType;
    }

}


