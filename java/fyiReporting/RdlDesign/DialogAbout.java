//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:17 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.DialogAbout;

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
* Summary description for DialogAbout.
*/
public class DialogAbout  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Button bOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox tbLicense = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.LinkLabel linkLabel3 = new System.Windows.Forms.LinkLabel();
    private System.Windows.Forms.LinkLabel linkLabel4 = new System.Windows.Forms.LinkLabel();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.PictureBox pictureBox2 = new System.Windows.Forms.PictureBox();
    private System.Windows.Forms.Label lVersion = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label lVMVersion = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public DialogAbout() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        tbLicense.Text = "RDL Designer creates reports defined using the Report Definition Language Specification.\r\n" + 
        "Copyright (C) 2004-2006  fyiReporting Software, LLC\r\n" + 
        "\r\n" + 
        "This program is distributed in the hope that it will be useful,\r\n" + 
        "but WITHOUT ANY WARRANTY; without even the implied warranty of\r\n" + 
        "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.";
        lVersion.Text = "Version " + Assembly.GetExecutingAssembly().GetName().Version.ToString();
        this.lVMVersion.Text = ".NET " + Environment.Version.ToString();
        return ;
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
        System.Resources.ResourceManager resources = new System.Resources.ResourceManager(DialogAbout.class);
        this.bOK = new System.Windows.Forms.Button();
        this.tbLicense = new System.Windows.Forms.TextBox();
        this.linkLabel3 = new System.Windows.Forms.LinkLabel();
        this.linkLabel4 = new System.Windows.Forms.LinkLabel();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.lVersion = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.pictureBox2 = new System.Windows.Forms.PictureBox();
        this.lVMVersion = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // bOK
        //
        this.bOK.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.bOK.Location = new System.Drawing.Point(200, 272);
        this.bOK.Name = "bOK";
        this.bOK.TabIndex = 0;
        this.bOK.Text = "OK";
        //
        // tbLicense
        //
        this.tbLicense.Location = new System.Drawing.Point(8, 120);
        this.tbLicense.Multiline = true;
        this.tbLicense.Name = "tbLicense";
        this.tbLicense.ReadOnly = true;
        this.tbLicense.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        this.tbLicense.Size = new System.Drawing.Size(448, 136);
        this.tbLicense.TabIndex = 9;
        this.tbLicense.Text = "";
        //
        // linkLabel3
        //
        this.linkLabel3.Location = new System.Drawing.Point(280, 88);
        this.linkLabel3.Name = "linkLabel3";
        this.linkLabel3.Size = new System.Drawing.Size(152, 16);
        this.linkLabel3.TabIndex = 15;
        this.linkLabel3.TabStop = true;
        this.linkLabel3.Tag = "mailto:comments@fyireporting.com";
        this.linkLabel3.Text = "comments@fyireporting.com";
        this.linkLabel3.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.lnk_LinkClicked);
        //
        // linkLabel4
        //
        this.linkLabel4.Location = new System.Drawing.Point(72, 88);
        this.linkLabel4.Name = "linkLabel4";
        this.linkLabel4.Size = new System.Drawing.Size(144, 16);
        this.linkLabel4.TabIndex = 14;
        this.linkLabel4.TabStop = true;
        this.linkLabel4.Tag = "http://www.fyireporting.com";
        this.linkLabel4.Text = "http://www.fyireporting.com";
        this.linkLabel4.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.lnk_LinkClicked);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(240, 88);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(40, 23);
        this.label5.TabIndex = 13;
        this.label5.Text = "E-mail:";
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(16, 88);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(56, 23);
        this.label6.TabIndex = 12;
        this.label6.Text = "Website:";
        //
        // lVersion
        //
        this.lVersion.Location = new System.Drawing.Point(264, 40);
        this.lVersion.Name = "lVersion";
        this.lVersion.Size = new System.Drawing.Size(136, 16);
        this.lVersion.TabIndex = 11;
        this.lVersion.Text = "Version x.x.x";
        this.lVersion.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // label8
        //
        this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.label8.Location = new System.Drawing.Point(240, 16);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(184, 24);
        this.label8.TabIndex = 10;
        this.label8.Text = "fyiReporting Designer";
        //
        // pictureBox2
        //
        this.pictureBox2.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox2.Image")));
        this.pictureBox2.Location = new System.Drawing.Point(8, 8);
        this.pictureBox2.Name = "pictureBox2";
        this.pictureBox2.Size = new System.Drawing.Size(212, 72);
        this.pictureBox2.SizeMode = System.Windows.Forms.PictureBoxSizeMode.AutoSize;
        this.pictureBox2.TabIndex = 16;
        this.pictureBox2.TabStop = false;
        //
        // lVMVersion
        //
        this.lVMVersion.Location = new System.Drawing.Point(256, 64);
        this.lVMVersion.Name = "lVMVersion";
        this.lVMVersion.Size = new System.Drawing.Size(144, 16);
        this.lVMVersion.TabIndex = 17;
        this.lVMVersion.Text = ".NET x.x.xxxx.xxxx";
        this.lVMVersion.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // DialogAbout
        //
        this.AcceptButton = this.bOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.bOK;
        this.ClientSize = new System.Drawing.Size(466, 304);
        this.Controls.Add(this.lVMVersion);
        this.Controls.Add(this.linkLabel3);
        this.Controls.Add(this.linkLabel4);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.lVersion);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.pictureBox2);
        this.Controls.Add(this.tbLicense);
        this.Controls.Add(this.bOK);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "DialogAbout";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "About";
        this.ResumeLayout(false);
    }

    private void lnk_LinkClicked(Object sender, LinkLabelLinkClickedEventArgs ea) throws Exception {
        LinkLabel lnk = (LinkLabel)sender;
        lnk.Links[lnk.Links.IndexOf(ea.Link)].Visited = true;
        System.Diagnostics.Process.Start(lnk.Tag.ToString());
    }

}


