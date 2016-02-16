//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:16 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RDL.ICustomReportItem;
import fyiReporting.RDL.RdlEngineConfig;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.DialogExprEditor;
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
* CustomReportItemCtl provides property values for a CustomReportItem
*/
public class CustomReportItemCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private List<XmlNode> _ReportItems = new List<XmlNode>();
    private DesignXmlDraw _Draw;
    private String _Type = new String();
    private PropertyGrid pgProps = new PropertyGrid();
    private Button bExpr = new Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public CustomReportItemCtl(DesignXmlDraw dxDraw, List<XmlNode> reportItems) throws Exception {
        _Draw = dxDraw;
        this._ReportItems = reportItems;
        _Type = _Draw.GetElementValue(_ReportItems[0], "Type", "");
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        ICustomReportItem cri = null;
        try
        {
            cri = RdlEngineConfig.createCustomReportItem(_Type);
            Object props = cri.GetPropertiesInstance(_Draw.GetNamedChildNode(_ReportItems[0], "CustomProperties"));
            pgProps.SelectedObject = props;
        }
        catch (Exception __dummyCatchVar0)
        {
            return ;
        }
        finally
        {
            if (cri != null)
                cri.Dispose();
             
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
        this.pgProps = new System.Windows.Forms.PropertyGrid();
        this.bExpr = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // pgProps
        //
        this.pgProps.Location = new System.Drawing.Point(13, 17);
        this.pgProps.Name = "pgProps";
        this.pgProps.Size = new System.Drawing.Size(406, 260);
        this.pgProps.TabIndex = 3;
        //
        // bExpr
        //
        this.bExpr.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.bExpr.Location = new System.Drawing.Point(422, 57);
        this.bExpr.Name = "bExpr";
        this.bExpr.Size = new System.Drawing.Size(22, 16);
        this.bExpr.TabIndex = 4;
        this.bExpr.Tag = "sd";
        this.bExpr.Text = "fx";
        this.bExpr.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bExpr.Click += new System.EventHandler(this.bExpr_Click);
        //
        // CustomReportItemCtl
        //
        this.Controls.Add(this.bExpr);
        this.Controls.Add(this.pgProps);
        this.Name = "CustomReportItemCtl";
        this.Size = new System.Drawing.Size(464, 304);
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        return true;
    }

    public void apply() throws Exception {
        ICustomReportItem cri = null;
        try
        {
            cri = RdlEngineConfig.createCustomReportItem(_Type);
            for (Object __dummyForeachVar0 : _ReportItems)
            {
                XmlNode node = (XmlNode)__dummyForeachVar0;
                cri.setPropertiesInstance(_Draw.getNamedChildNode(node,"CustomProperties"),pgProps.SelectedObject);
            }
        }
        catch (Exception __dummyCatchVar1)
        {
            return ;
        }
        finally
        {
            if (cri != null)
                cri.Dispose();
             
        }
        return ;
    }

    private void bExpr_Click(Object sender, EventArgs e) throws Exception {
        GridItem gi = this.pgProps.SelectedGridItem;
        XmlNode sNode = _ReportItems[0];
        DialogExprEditor ee = new DialogExprEditor(_Draw, gi.Value.ToString(), sNode, false);
        DialogResult dr = ee.ShowDialog();
        if (dr == DialogResult.OK)
        {
            // There's probably a better way without reflection but this works fine.
            String nm = gi.Label;
            Object sel = pgProps.SelectedObject;
            Type t = sel.GetType();
            PropertyInfo pi = t.GetProperty(nm);
            MethodInfo mi = pi.GetSetMethod();
            Object[] oa = new Object[1];
            oa[0] = ee.getExpression();
            mi.Invoke(sel, oa);
            gi.Select();
        }
         
    }

}


