//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RdlViewer;

import fyiReporting.RdlViewer.DialogMessages;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
    You should have received a copy of the GNU Lesser General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* DialogMessage is used in place of a message box when the text can be large
*/
public class DialogMessages  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Button bOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox tbMessages = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public DialogMessages(IList msgs) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        String[] lines = new String[msgs.Count];
        int l = 0;
        for (Object __dummyForeachVar0 : msgs)
        {
            String msg = (String)__dummyForeachVar0;
            lines[l++] = msg;
        }
        tbMessages.Lines = lines;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(DialogMessages.class);
        this.bOK = new System.Windows.Forms.Button();
        this.tbMessages = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // bOK
        //
        this.bOK.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.bOK.Location = new System.Drawing.Point(200, 208);
        this.bOK.Name = "bOK";
        this.bOK.Size = new System.Drawing.Size(75, 23);
        this.bOK.TabIndex = 0;
        this.bOK.Text = "OK";
        //
        // tbMessages
        //
        this.tbMessages.Location = new System.Drawing.Point(16, 16);
        this.tbMessages.Multiline = true;
        this.tbMessages.Name = "tbMessages";
        this.tbMessages.ReadOnly = true;
        this.tbMessages.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        this.tbMessages.Size = new System.Drawing.Size(448, 176);
        this.tbMessages.TabIndex = 9;
        //
        // DialogMessages
        //
        this.AcceptButton = this.bOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.bOK;
        this.ClientSize = new System.Drawing.Size(482, 240);
        this.Controls.Add(this.tbMessages);
        this.Controls.Add(this.bOK);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "DialogMessages";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "Report Warnings";
        this.ResumeLayout(false);
        this.PerformLayout();
    }

}


