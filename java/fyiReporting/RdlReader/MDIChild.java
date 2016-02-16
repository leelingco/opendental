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
* RdlReader is a application for displaying reports based on RDL.
*/
public class MDIChild  extends Form 
{
    private fyiReporting.RdlViewer.RdlViewer rdlViewer1;
    public MDIChild(int width, int height) throws Exception {
        this.rdlViewer1 = new fyiReporting.RdlViewer.RdlViewer();
        this.SuspendLayout();
        //
        // rdlViewer1
        //
        this.rdlViewer1.Dock = System.Windows.Forms.DockStyle.Fill;
        this.rdlViewer1.Location = new System.Drawing.Point(0, 0);
        this.rdlViewer1.Name = "rdlViewer1";
        this.rdlViewer1.Size = new System.Drawing.Size(width, height);
        this.rdlViewer1.TabIndex = 0;
        //
        // RdlReader
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(width, height);
        this.Controls.Add(this.rdlViewer1);
        this.Name = "";
        this.Text = "";
        this.ResumeLayout(false);
    }

    /**
    * The RDL file that should be displayed.
    */
    public String getSourceFile() throws Exception {
        return this.rdlViewer1.getSourceFile();
    }

    public void setSourceFile(String value) throws Exception {
        this.rdlViewer1.setSourceFile(value);
        this.rdlViewer1.Refresh();
    }

    // force the repaint
    public fyiReporting.RdlViewer.RdlViewer.RdlViewer getViewer() throws Exception {
        return this.rdlViewer1;
    }

}


