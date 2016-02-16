//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RdlReader;


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
* Summary description for ZoomTo.
*/
public class ZoomTo  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbMagnify = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Button bOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bCancel = new System.Windows.Forms.Button();
    private fyiReporting.RdlViewer.RdlViewer.RdlViewer _Viewer = new fyiReporting.RdlViewer.RdlViewer.RdlViewer();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public ZoomTo(fyiReporting.RdlViewer.RdlViewer.RdlViewer viewer) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        _Viewer = viewer;
        // set the intial value for magnification
        if (_Viewer.ZoomMode == fyiReporting.RdlViewer.ZoomEnum.FitPage)
            cbMagnify.Text = "Fit Page";
        else if (_Viewer.ZoomMode == fyiReporting.RdlViewer.ZoomEnum.FitWidth)
            cbMagnify.Text = "Fit Width";
        else if (_Viewer.Zoom == 1)
            cbMagnify.Text = "Actual Size";
        else
        {
            String formatted = String.Format("{0:#0.##}", _Viewer.Zoom * 100);
            if (formatted[formatted.Length - 1] == '.')
                formatted = formatted.Substring(0, formatted.Length - 2);
             
            formatted = formatted + "%";
            cbMagnify.Text = formatted;
        }   
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
        this.label1 = new System.Windows.Forms.Label();
        this.cbMagnify = new System.Windows.Forms.ComboBox();
        this.bOK = new System.Windows.Forms.Button();
        this.bCancel = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(16, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(72, 23);
        this.label1.TabIndex = 3;
        this.label1.Text = "Magnification";
        //
        // cbMagnify
        //
        this.cbMagnify.Items.AddRange(new Object[]{ "800%", "400%", "200%", "150%", "125%", "100%", "50%", "25%", "Fit Page", "Actual Size", "Fit Width" });
        this.cbMagnify.Location = new System.Drawing.Point(96, 16);
        this.cbMagnify.Name = "cbMagnify";
        this.cbMagnify.Size = new System.Drawing.Size(120, 21);
        this.cbMagnify.TabIndex = 2;
        //
        // bOK
        //
        this.bOK.DialogResult = System.Windows.Forms.DialogResult.OK;
        this.bOK.Location = new System.Drawing.Point(24, 64);
        this.bOK.Name = "bOK";
        this.bOK.TabIndex = 1;
        this.bOK.Text = "OK";
        this.bOK.Click += new System.EventHandler(this.bOK_Click);
        //
        // bCancel
        //
        this.bCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.bCancel.Location = new System.Drawing.Point(136, 64);
        this.bCancel.Name = "bCancel";
        this.bCancel.TabIndex = 0;
        this.bCancel.Text = "Cancel";
        this.bCancel.Click += new System.EventHandler(this.bCancel_Click);
        //
        // ZoomTo
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(248, 102);
        this.Controls.Add(this.bCancel);
        this.Controls.Add(this.bOK);
        this.Controls.Add(this.cbMagnify);
        this.Controls.Add(this.label1);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "ZoomTo";
        this.ShowInTaskbar = false;
        this.Text = "Zoom To";
        this.ResumeLayout(false);
    }

    private void bOK_Click(Object sender, System.EventArgs e) throws Exception {
        Text __dummyScrutVar0 = cbMagnify.Text;
        if (__dummyScrutVar0.equals("Fit Page"))
        {
            _Viewer.ZoomMode = fyiReporting.RdlViewer.ZoomEnum.FitPage;
        }
        else if (__dummyScrutVar0.equals("Actual Size"))
        {
            _Viewer.Zoom = 1;
        }
        else if (__dummyScrutVar0.equals("Fit Width"))
        {
            _Viewer.ZoomMode = fyiReporting.RdlViewer.ZoomEnum.FitWidth;
        }
        else
        {
            String z = cbMagnify.Text.Replace("%", "");
            try
            {
                float zfactor = (float)(Convert.ToSingle(z) / 100.0);
                _Viewer.Zoom = zfactor;
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show(this, "Magnification level is an invalid number.");
                return ;
            }
        
        }   
        DialogResult = DialogResult.OK;
    }

    private void bCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


