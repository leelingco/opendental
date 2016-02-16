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
* DialogListOfStrings: puts up a dialog that lets a user enter a list of strings
*/
public class DialogListOfStrings  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Button bOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbStrings = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public DialogListOfStrings(List<String> list) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        if (list == null || list.Count == 0)
            return ;
         
        // Populate textbox with the list of strings
        String[] sa = new String[list.Count];
        int l = 0;
        for (Object __dummyForeachVar0 : list)
        {
            String v = (String)__dummyForeachVar0;
            sa[l++] = v;
        }
        tbStrings.Lines = sa;
        return ;
    }

    public List<String> getListOfStrings() throws Exception {
        if (this.tbStrings.Text.Length == 0)
            return null;
         
        List<String> l = new List<String>();
        for (Object __dummyForeachVar1 : tbStrings.Lines)
        {
            String v = (String)__dummyForeachVar1;
            if (v.Length > 0)
                l.Add(v);
             
        }
        return l;
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
        this.bOK = new System.Windows.Forms.Button();
        this.tbStrings = new System.Windows.Forms.TextBox();
        this.bCancel = new System.Windows.Forms.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // bOK
        //
        this.bOK.DialogResult = System.Windows.Forms.DialogResult.OK;
        this.bOK.Location = new System.Drawing.Point(96, 192);
        this.bOK.Name = "bOK";
        this.bOK.TabIndex = 0;
        this.bOK.Text = "OK";
        //
        // tbStrings
        //
        this.tbStrings.Location = new System.Drawing.Point(8, 40);
        this.tbStrings.Multiline = true;
        this.tbStrings.Name = "tbStrings";
        this.tbStrings.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        this.tbStrings.Size = new System.Drawing.Size(256, 144);
        this.tbStrings.TabIndex = 9;
        this.tbStrings.Text = "";
        //
        // bCancel
        //
        this.bCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.bCancel.Location = new System.Drawing.Point(192, 192);
        this.bCancel.Name = "bCancel";
        this.bCancel.TabIndex = 10;
        this.bCancel.Text = "Cancel";
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 8);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(240, 23);
        this.label1.TabIndex = 11;
        this.label1.Text = "Enter separate values on multiple lines below";
        //
        // DialogListOfStrings
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.bCancel;
        this.ClientSize = new System.Drawing.Size(282, 224);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.bCancel);
        this.Controls.Add(this.tbStrings);
        this.Controls.Add(this.bOK);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "DialogListOfStrings";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.ResumeLayout(false);
    }

    private void lnk_LinkClicked(Object sender, LinkLabelLinkClickedEventArgs ea) throws Exception {
        LinkLabel lnk = (LinkLabel)sender;
        lnk.Links[lnk.Links.IndexOf(ea.Link)].Visited = true;
        System.Diagnostics.Process.Start(lnk.Tag.ToString());
    }

}


