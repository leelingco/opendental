//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:20 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.DialogExprEditor;
import fyiReporting.RdlDesign.DrillParameter;
import fyiReporting.RdlDesign.DrillParametersDialog;
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
* Summary description for StyleCtl.
*/
public class InteractivityCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private List<XmlNode> _ReportItems = new List<XmlNode>();
    private DesignXmlDraw _Draw;
    private List<DrillParameter> _DrillParameters = new List<DrillParameter>();
    // flags for controlling whether syntax changed for a particular property
    private boolean fBookmark = new boolean(), fAction = new boolean(), fHidden = new boolean(), fToggle = new boolean();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox grpBoxVisibility = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbBookmark = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.RadioButton rbHyperlink = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton rbBookmarkLink = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton rbDrillthrough = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.TextBox tbHyperlink = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox tbBookmarkLink = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox tbDrillthrough = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button bParameters = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbHidden = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ComboBox cbToggle = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.RadioButton rbNoAction = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Button bDrillthrough = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bHidden = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bBookmarkLink = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bHyperlink = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bBookmark = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public InteractivityCtl(DesignXmlDraw dxDraw, List<XmlNode> reportItems) throws Exception {
        _ReportItems = reportItems;
        _Draw = dxDraw;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        InitValues(_ReportItems[0]);
    }

    private void initValues(XmlNode node) throws Exception {
        tbBookmark.Text = _Draw.getElementValue(node,"Bookmark","");
        // Handle Action definition
        XmlNode aNode = _Draw.getNamedChildNode(node,"Action");
        if (aNode == null)
            rbNoAction.Checked = true;
        else
        {
            XmlNode vLink = _Draw.getNamedChildNode(aNode,"Hyperlink");
            if (vLink != null)
            {
                // Hyperlink specified
                rbHyperlink.Checked = true;
                tbHyperlink.Text = vLink.InnerText;
            }
            else
            {
                vLink = _Draw.getNamedChildNode(aNode,"Drillthrough");
                if (vLink != null)
                {
                    // Drillthrough specified
                    rbDrillthrough.Checked = true;
                    tbDrillthrough.Text = _Draw.getElementValue(vLink,"ReportName","");
                    _DrillParameters = new List<DrillParameter>();
                    XmlNode pNodes = _Draw.getNamedChildNode(vLink,"Parameters");
                    if (pNodes != null)
                    {
                        for (Object __dummyForeachVar0 : pNodes.ChildNodes)
                        {
                            XmlNode pNode = (XmlNode)__dummyForeachVar0;
                            if (!StringSupport.equals(pNode.Name, "Parameter"))
                                continue;
                             
                            String name = _Draw.getElementAttribute(pNode,"Name","");
                            String pvalue = _Draw.getElementValue(pNode,"Value","");
                            String omit = _Draw.getElementValue(pNode,"Omit","false");
                            DrillParameter dp = new DrillParameter(name,pvalue,omit);
                            _DrillParameters.Add(dp);
                        }
                    }
                     
                }
                else
                {
                    vLink = _Draw.getNamedChildNode(aNode,"BookmarkLink");
                    if (vLink != null)
                    {
                        // BookmarkLink specified
                        rbBookmarkLink.Checked = true;
                        this.tbBookmarkLink.Text = vLink.InnerText;
                    }
                     
                } 
            } 
        } 
        // Handle Visiblity definition
        XmlNode visNode = _Draw.getNamedChildNode(node,"Visibility");
        if (visNode != null)
        {
            XmlNode hNode = _Draw.getNamedChildNode(node,"Visibility");
            this.tbHidden.Text = _Draw.getElementValue(visNode,"Hidden","");
            this.cbToggle.Text = _Draw.getElementValue(visNode,"ToggleItem","");
        }
         
        IEnumerable list = _Draw.getReportItems("//Textbox");
        if (list != null)
        {
            for (Object __dummyForeachVar1 : list)
            {
                XmlNode tNode = (XmlNode)__dummyForeachVar1;
                XmlAttribute name = tNode.Attributes["Name"];
                if (name != null && name.Value != null && name.Value.Length > 0)
                    cbToggle.Items.Add(name.Value);
                 
            }
        }
         
        // nothing has changed now
        fBookmark = fAction = fHidden = fToggle = false;
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
        this.grpBoxVisibility = new System.Windows.Forms.GroupBox();
        this.bHidden = new System.Windows.Forms.Button();
        this.cbToggle = new System.Windows.Forms.ComboBox();
        this.tbHidden = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.bBookmarkLink = new System.Windows.Forms.Button();
        this.bHyperlink = new System.Windows.Forms.Button();
        this.rbNoAction = new System.Windows.Forms.RadioButton();
        this.bParameters = new System.Windows.Forms.Button();
        this.bDrillthrough = new System.Windows.Forms.Button();
        this.tbDrillthrough = new System.Windows.Forms.TextBox();
        this.tbBookmarkLink = new System.Windows.Forms.TextBox();
        this.tbHyperlink = new System.Windows.Forms.TextBox();
        this.rbDrillthrough = new System.Windows.Forms.RadioButton();
        this.rbBookmarkLink = new System.Windows.Forms.RadioButton();
        this.rbHyperlink = new System.Windows.Forms.RadioButton();
        this.label1 = new System.Windows.Forms.Label();
        this.tbBookmark = new System.Windows.Forms.TextBox();
        this.bBookmark = new System.Windows.Forms.Button();
        this.grpBoxVisibility.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // grpBoxVisibility
        //
        this.grpBoxVisibility.Controls.Add(this.bHidden);
        this.grpBoxVisibility.Controls.Add(this.cbToggle);
        this.grpBoxVisibility.Controls.Add(this.tbHidden);
        this.grpBoxVisibility.Controls.Add(this.label3);
        this.grpBoxVisibility.Controls.Add(this.label2);
        this.grpBoxVisibility.Location = new System.Drawing.Point(8, 152);
        this.grpBoxVisibility.Name = "grpBoxVisibility";
        this.grpBoxVisibility.Size = new System.Drawing.Size(432, 80);
        this.grpBoxVisibility.TabIndex = 1;
        this.grpBoxVisibility.TabStop = false;
        this.grpBoxVisibility.Text = "Visibility";
        //
        // bHidden
        //
        this.bHidden.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bHidden.Location = new System.Drawing.Point(400, 26);
        this.bHidden.Name = "bHidden";
        this.bHidden.Size = new System.Drawing.Size(22, 16);
        this.bHidden.TabIndex = 1;
        this.bHidden.Tag = "visibility";
        this.bHidden.Text = "fx";
        this.bHidden.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bHidden.Click += new System.EventHandler(this.bExpr_Click);
        //
        // cbToggle
        //
        this.cbToggle.Location = new System.Drawing.Point(168, 48);
        this.cbToggle.Name = "cbToggle";
        this.cbToggle.Size = new System.Drawing.Size(152, 21);
        this.cbToggle.TabIndex = 2;
        this.cbToggle.TextChanged += new System.EventHandler(this.cbToggle_SelectedIndexChanged);
        this.cbToggle.SelectedIndexChanged += new System.EventHandler(this.cbToggle_SelectedIndexChanged);
        //
        // tbHidden
        //
        this.tbHidden.Location = new System.Drawing.Point(168, 24);
        this.tbHidden.Name = "tbHidden";
        this.tbHidden.Size = new System.Drawing.Size(224, 20);
        this.tbHidden.TabIndex = 0;
        this.tbHidden.Text = "";
        this.tbHidden.TextChanged += new System.EventHandler(this.tbHidden_TextChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(16, 48);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(152, 23);
        this.label3.TabIndex = 1;
        this.label3.Text = "Toggle Item (Textbox name)";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(16, 24);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(120, 23);
        this.label2.TabIndex = 0;
        this.label2.Text = "Hidden (initial visibility)";
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.bBookmarkLink);
        this.groupBox1.Controls.Add(this.bHyperlink);
        this.groupBox1.Controls.Add(this.rbNoAction);
        this.groupBox1.Controls.Add(this.bParameters);
        this.groupBox1.Controls.Add(this.bDrillthrough);
        this.groupBox1.Controls.Add(this.tbDrillthrough);
        this.groupBox1.Controls.Add(this.tbBookmarkLink);
        this.groupBox1.Controls.Add(this.tbHyperlink);
        this.groupBox1.Controls.Add(this.rbDrillthrough);
        this.groupBox1.Controls.Add(this.rbBookmarkLink);
        this.groupBox1.Controls.Add(this.rbHyperlink);
        this.groupBox1.Location = new System.Drawing.Point(8, 8);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(432, 136);
        this.groupBox1.TabIndex = 0;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Action";
        //
        // bBookmarkLink
        //
        this.bBookmarkLink.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bBookmarkLink.Location = new System.Drawing.Point(400, 80);
        this.bBookmarkLink.Name = "bBookmarkLink";
        this.bBookmarkLink.Size = new System.Drawing.Size(22, 16);
        this.bBookmarkLink.TabIndex = 3;
        this.bBookmarkLink.Tag = "bookmarklink";
        this.bBookmarkLink.Text = "fx";
        this.bBookmarkLink.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bBookmarkLink.Click += new System.EventHandler(this.bExpr_Click);
        //
        // bHyperlink
        //
        this.bHyperlink.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bHyperlink.Location = new System.Drawing.Point(400, 50);
        this.bHyperlink.Name = "bHyperlink";
        this.bHyperlink.Size = new System.Drawing.Size(22, 16);
        this.bHyperlink.TabIndex = 1;
        this.bHyperlink.Tag = "hyperlink";
        this.bHyperlink.Text = "fx";
        this.bHyperlink.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bHyperlink.Click += new System.EventHandler(this.bExpr_Click);
        //
        // rbNoAction
        //
        this.rbNoAction.Location = new System.Drawing.Point(16, 16);
        this.rbNoAction.Name = "rbNoAction";
        this.rbNoAction.TabIndex = 5;
        this.rbNoAction.Text = "None";
        this.rbNoAction.CheckedChanged += new System.EventHandler(this.rbAction_CheckedChanged);
        //
        // bParameters
        //
        this.bParameters.Location = new System.Drawing.Point(344, 104);
        this.bParameters.Name = "bParameters";
        this.bParameters.Size = new System.Drawing.Size(80, 23);
        this.bParameters.TabIndex = 6;
        this.bParameters.Text = "Parameters...";
        this.bParameters.Click += new System.EventHandler(this.bParameters_Click);
        //
        // bDrillthrough
        //
        this.bDrillthrough.Location = new System.Drawing.Point(312, 104);
        this.bDrillthrough.Name = "bDrillthrough";
        this.bDrillthrough.Size = new System.Drawing.Size(24, 23);
        this.bDrillthrough.TabIndex = 5;
        this.bDrillthrough.Text = "...";
        this.bDrillthrough.Click += new System.EventHandler(this.bDrillthrough_Click);
        //
        // tbDrillthrough
        //
        this.tbDrillthrough.Location = new System.Drawing.Point(128, 104);
        this.tbDrillthrough.Name = "tbDrillthrough";
        this.tbDrillthrough.Size = new System.Drawing.Size(176, 20);
        this.tbDrillthrough.TabIndex = 4;
        this.tbDrillthrough.Text = "";
        this.tbDrillthrough.TextChanged += new System.EventHandler(this.tbAction_TextChanged);
        //
        // tbBookmarkLink
        //
        this.tbBookmarkLink.Location = new System.Drawing.Point(128, 76);
        this.tbBookmarkLink.Name = "tbBookmarkLink";
        this.tbBookmarkLink.Size = new System.Drawing.Size(264, 20);
        this.tbBookmarkLink.TabIndex = 2;
        this.tbBookmarkLink.Text = "";
        this.tbBookmarkLink.TextChanged += new System.EventHandler(this.tbAction_TextChanged);
        //
        // tbHyperlink
        //
        this.tbHyperlink.Location = new System.Drawing.Point(128, 47);
        this.tbHyperlink.Name = "tbHyperlink";
        this.tbHyperlink.Size = new System.Drawing.Size(264, 20);
        this.tbHyperlink.TabIndex = 0;
        this.tbHyperlink.Text = "";
        this.tbHyperlink.TextChanged += new System.EventHandler(this.tbAction_TextChanged);
        //
        // rbDrillthrough
        //
        this.rbDrillthrough.Location = new System.Drawing.Point(16, 102);
        this.rbDrillthrough.Name = "rbDrillthrough";
        this.rbDrillthrough.TabIndex = 2;
        this.rbDrillthrough.Text = "Drill Through";
        this.rbDrillthrough.CheckedChanged += new System.EventHandler(this.rbAction_CheckedChanged);
        //
        // rbBookmarkLink
        //
        this.rbBookmarkLink.Location = new System.Drawing.Point(16, 74);
        this.rbBookmarkLink.Name = "rbBookmarkLink";
        this.rbBookmarkLink.TabIndex = 1;
        this.rbBookmarkLink.Text = "Bookmark Link";
        this.rbBookmarkLink.CheckedChanged += new System.EventHandler(this.rbAction_CheckedChanged);
        //
        // rbHyperlink
        //
        this.rbHyperlink.Location = new System.Drawing.Point(16, 45);
        this.rbHyperlink.Name = "rbHyperlink";
        this.rbHyperlink.TabIndex = 0;
        this.rbHyperlink.Text = "Hyperlink";
        this.rbHyperlink.CheckedChanged += new System.EventHandler(this.rbAction_CheckedChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 256);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(56, 16);
        this.label1.TabIndex = 2;
        this.label1.Text = "Bookmark";
        //
        // tbBookmark
        //
        this.tbBookmark.Location = new System.Drawing.Point(88, 254);
        this.tbBookmark.Name = "tbBookmark";
        this.tbBookmark.Size = new System.Drawing.Size(312, 20);
        this.tbBookmark.TabIndex = 3;
        this.tbBookmark.Text = "";
        //
        // bBookmark
        //
        this.bBookmark.Font = new System.Drawing.Font("Arial", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
        this.bBookmark.Location = new System.Drawing.Point(408, 258);
        this.bBookmark.Name = "bBookmark";
        this.bBookmark.Size = new System.Drawing.Size(22, 16);
        this.bBookmark.TabIndex = 4;
        this.bBookmark.Tag = "bookmark";
        this.bBookmark.Text = "fx";
        this.bBookmark.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.bBookmark.Click += new System.EventHandler(this.bExpr_Click);
        //
        // InteractivityCtl
        //
        this.Controls.Add(this.bBookmark);
        this.Controls.Add(this.tbBookmark);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.grpBoxVisibility);
        this.Name = "InteractivityCtl";
        this.Size = new System.Drawing.Size(472, 288);
        this.grpBoxVisibility.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        return true;
    }

    public void apply() throws Exception {
        for (Object __dummyForeachVar2 : this._ReportItems)
        {
            // take information in control and apply to all the style nodes
            //  Only change information that has been marked as modified;
            //   this way when group is selected it is possible to change just
            //   the items you want and keep the rest the same.
            XmlNode riNode = (XmlNode)__dummyForeachVar2;
            applyChanges(riNode);
        }
        // nothing has changed now
        fBookmark = fAction = fHidden = fToggle = false;
    }

    private void applyChanges(XmlNode rNode) throws Exception {
        if (fBookmark)
            _Draw.SetElement(rNode, "Bookmark", tbBookmark.Text);
         
        if (fHidden || fToggle)
        {
            XmlNode visNode = _Draw.setElement(rNode,"Visibility",null);
            if (fHidden)
                _Draw.SetElement(visNode, "Hidden", tbHidden.Text);
             
            if (fToggle)
                _Draw.SetElement(visNode, "ToggleItem", this.cbToggle.Text);
             
        }
         
        if (fAction)
        {
            XmlNode aNode = new XmlNode();
            if (this.rbHyperlink.Checked)
            {
                aNode = _Draw.setElement(rNode,"Action",null);
                _Draw.removeElement(aNode,"Drillthrough");
                _Draw.removeElement(aNode,"BookmarkLink");
                _Draw.SetElement(aNode, "Hyperlink", tbHyperlink.Text);
            }
            else if (this.rbDrillthrough.Checked)
            {
                aNode = _Draw.setElement(rNode,"Action",null);
                _Draw.removeElement(aNode,"Hyperlink");
                _Draw.removeElement(aNode,"BookmarkLink");
                XmlNode dNode = _Draw.setElement(aNode,"Drillthrough",null);
                _Draw.SetElement(dNode, "ReportName", tbDrillthrough.Text);
                // Now do parameters
                _Draw.removeElement(dNode,"Parameters");
                // Get rid of prior values
                if (this._DrillParameters != null && _DrillParameters.Count > 0)
                {
                    XmlNode pNodes = _Draw.setElement(dNode,"Parameters",null);
                    for (Object __dummyForeachVar3 : _DrillParameters)
                    {
                        DrillParameter dp = (DrillParameter)__dummyForeachVar3;
                        XmlNode p = _Draw.setElement(pNodes,"Parameter",null);
                        _Draw.setElementAttribute(p,"Name",dp.ParameterName);
                        _Draw.setElement(p,"Value",dp.ParameterValue);
                        if (dp.ParameterOmit != null && dp.ParameterOmit.Length > 0)
                            _Draw.setElement(p,"Omit",dp.ParameterOmit);
                         
                    }
                }
                 
            }
            else if (this.rbBookmarkLink.Checked)
            {
                aNode = _Draw.setElement(rNode,"Action",null);
                _Draw.removeElement(aNode,"Drillthrough");
                _Draw.removeElement(aNode,"Hyperlink");
                _Draw.SetElement(aNode, "BookmarkLink", tbBookmarkLink.Text);
            }
            else
            {
                // assume no action
                _Draw.removeElement(rNode,"Action");
            }   
        }
         
    }

    private void tbBookmark_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fBookmark = true;
    }

    private void rbAction_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        if (this.rbHyperlink.Checked)
        {
            tbHyperlink.Enabled = bHyperlink.Enabled = true;
            tbBookmarkLink.Enabled = bBookmarkLink.Enabled = false;
            tbDrillthrough.Enabled = false;
            bDrillthrough.Enabled = false;
            bParameters.Enabled = false;
        }
        else if (this.rbDrillthrough.Checked)
        {
            tbHyperlink.Enabled = bHyperlink.Enabled = false;
            tbBookmarkLink.Enabled = bBookmarkLink.Enabled = false;
            tbDrillthrough.Enabled = true;
            bDrillthrough.Enabled = true;
            bParameters.Enabled = true;
        }
        else if (this.rbBookmarkLink.Checked)
        {
            tbHyperlink.Enabled = bHyperlink.Enabled = false;
            tbBookmarkLink.Enabled = bBookmarkLink.Enabled = true;
            tbDrillthrough.Enabled = false;
            bDrillthrough.Enabled = false;
            bParameters.Enabled = false;
        }
        else
        {
            // assume no action
            tbHyperlink.Enabled = bHyperlink.Enabled = false;
            tbBookmarkLink.Enabled = bBookmarkLink.Enabled = false;
            tbDrillthrough.Enabled = false;
            bDrillthrough.Enabled = false;
            bParameters.Enabled = false;
        }   
        fAction = true;
    }

    private void tbAction_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fAction = true;
    }

    private void tbHidden_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fHidden = true;
    }

    private void cbToggle_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        fToggle = true;
    }

    private void bDrillthrough_Click(Object sender, System.EventArgs e) throws Exception {
        OpenFileDialog ofd = new OpenFileDialog();
        ofd.Filter = "Report files (*.rdl)|*.rdl" + "All files (*.*)|*.*|";
        ofd.FilterIndex = 1;
        ofd.FileName = "*.rdl";
        ofd.Title = "Specify Report File Name";
        ofd.DefaultExt = "rdl";
        ofd.AddExtension = true;
        if (ofd.ShowDialog() == DialogResult.OK)
        {
            String file = Path.GetFileNameWithoutExtension(ofd.FileName);
            tbDrillthrough.Text = file;
        }
         
    }

    private void bParameters_Click(Object sender, System.EventArgs e) throws Exception {
        DrillParametersDialog dpd = new DrillParametersDialog(this.tbDrillthrough.Text, _DrillParameters);
        if (dpd.ShowDialog(this) != DialogResult.OK)
            return ;
         
        tbDrillthrough.Text = dpd.getDrillthroughReport();
        _DrillParameters = dpd.getDrillParameters();
        fAction = true;
    }

    private void bExpr_Click(Object sender, System.EventArgs e) throws Exception {
        Button b = sender instanceof Button ? (Button)sender : (Button)null;
        if (b == null)
            return ;
         
        Control c = null;
        System.String __dummyScrutVar0 = b.Tag instanceof String ? (String)b.Tag : (String)null;
        if (__dummyScrutVar0.equals("bookmark"))
        {
            c = tbBookmark;
        }
        else if (__dummyScrutVar0.equals("bookmarklink"))
        {
            c = tbBookmarkLink;
        }
        else if (__dummyScrutVar0.equals("hyperlink"))
        {
            c = tbHyperlink;
        }
        else if (__dummyScrutVar0.equals("visibility"))
        {
            c = tbHidden;
        }
            
        if (c == null)
            return ;
         
        XmlNode sNode = _ReportItems[0];
        DialogExprEditor ee = new DialogExprEditor(_Draw, c.Text, sNode);
        DialogResult dr = ee.ShowDialog();
        if (dr == DialogResult.OK)
            c.Text = ee.getExpression();
         
        return ;
    }

}


