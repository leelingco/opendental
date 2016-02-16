//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:15 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.IProperty;

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
* Summary description for BodyCtl.
*/
public class BodyCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private DesignXmlDraw _Draw;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbHeight = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox tbColumns = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox tbColumnSpacing = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public BodyCtl(DesignXmlDraw dxDraw) throws Exception {
        _Draw = dxDraw;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        XmlNode rNode = _Draw.getReportNode();
        XmlNode bNode = _Draw.getNamedChildNode(rNode,"Body");
        tbHeight.Text = _Draw.getElementValue(bNode,"Height","");
        tbColumns.Text = _Draw.getElementValue(bNode,"Columns","1");
        tbColumnSpacing.Text = _Draw.getElementValue(bNode,"ColumnSpacing","");
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
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.tbHeight = new System.Windows.Forms.TextBox();
        this.tbColumns = new System.Windows.Forms.TextBox();
        this.tbColumnSpacing = new System.Windows.Forms.TextBox();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(40, 24);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(56, 23);
        this.label1.TabIndex = 0;
        this.label1.Text = "Height";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(40, 60);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(56, 23);
        this.label2.TabIndex = 1;
        this.label2.Text = "Columns";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(8, 96);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(88, 23);
        this.label3.TabIndex = 2;
        this.label3.Text = "Column Spacing";
        //
        // tbHeight
        //
        this.tbHeight.Location = new System.Drawing.Point(104, 25);
        this.tbHeight.Name = "tbHeight";
        this.tbHeight.TabIndex = 0;
        this.tbHeight.Text = "";
        //
        // tbColumns
        //
        this.tbColumns.Location = new System.Drawing.Point(104, 61);
        this.tbColumns.Name = "tbColumns";
        this.tbColumns.TabIndex = 1;
        this.tbColumns.Text = "";
        //
        // tbColumnSpacing
        //
        this.tbColumnSpacing.Location = new System.Drawing.Point(104, 96);
        this.tbColumnSpacing.Name = "tbColumnSpacing";
        this.tbColumnSpacing.TabIndex = 2;
        this.tbColumnSpacing.Text = "";
        //
        // BodyCtl
        //
        this.Controls.Add(this.tbColumnSpacing);
        this.Controls.Add(this.tbColumns);
        this.Controls.Add(this.tbHeight);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Name = "BodyCtl";
        this.Size = new System.Drawing.Size(472, 288);
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        return true;
    }

    public void apply() throws Exception {
        XmlNode rNode = _Draw.getReportNode();
        XmlNode bNode = _Draw.getNamedChildNode(rNode,"Body");
        _Draw.SetElement(bNode, "Height", tbHeight.Text);
        _Draw.SetElement(bNode, "Columns", tbColumns.Text);
        if (tbColumnSpacing.Text.Length > 0)
            _Draw.SetElement(bNode, "ColumnSpacing", tbColumnSpacing.Text);
        else
            _Draw.removeElement(bNode,"ColumnSpacing"); 
    }

}


