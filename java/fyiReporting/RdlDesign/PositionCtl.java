//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:21 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.DesignerUtility;
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
* Summary description for ReportCtl.
*/
public class PositionCtl  extends System.Windows.Forms.UserControl implements IProperty
{
    private List<XmlNode> _ReportItems = new List<XmlNode>();
    private DesignXmlDraw _Draw;
    boolean fName = new boolean(), fLeft = new boolean(), fTop = new boolean(), fWidth = new boolean(), fHeight = new boolean(), fZIndex = new boolean(), fColSpan = new boolean();
    boolean fCanGrow = new boolean(), fCanShrink = new boolean(), fHideDuplicates = new boolean(), fToggleImage = new boolean(), fDataElementStyle = new boolean();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbWidth = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox tbTop = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox tbLeft = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.NumericUpDown tbZIndex = new System.Windows.Forms.NumericUpDown();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox tbName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox tbHeight = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox gbPosition = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label lblColSpan = new System.Windows.Forms.Label();
    private System.Windows.Forms.NumericUpDown tbColSpan = new System.Windows.Forms.NumericUpDown();
    private System.Windows.Forms.GroupBox gbText = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox chkCanGrow = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox chkCanShrink = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbHideDuplicates = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbDataElementStyle = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbToggleImage = new System.Windows.Forms.ComboBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public PositionCtl(DesignXmlDraw dxDraw, List<XmlNode> ris) throws Exception {
        _ReportItems = ris;
        _Draw = dxDraw;
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        // Initialize form using the style node values
        initValues();
    }

    private void initValues() throws Exception {
        XmlNode riNode = _ReportItems[0];
        XmlNode tcell = null;
        if (_ReportItems.Count > 1)
        {
            tbName.Text = "Group Selected";
            tbName.Enabled = false;
            lblColSpan.Visible = tbColSpan.Visible = false;
        }
        else
        {
            XmlAttribute xa = riNode.Attributes["Name"];
            tbName.Text = xa == null ? "" : xa.Value;
            XmlNode ris = riNode.ParentNode;
            tcell = ris.ParentNode;
            if (!StringSupport.equals(tcell.Name, "TableCell"))
                tcell = null;
             
        } 
        this.tbZIndex.Value = Convert.ToInt32(_Draw.getElementValue(riNode,"ZIndex","0"));
        if (tcell != null)
        {
            gbPosition.Visible = false;
            this.gbText.Location = gbPosition.Location;
            String colspan = _Draw.getElementValue(tcell,"ColSpan","1");
            tbColSpan.Value = Convert.ToDecimal(colspan);
        }
        else
        {
            lblColSpan.Visible = tbColSpan.Visible = false;
            tbLeft.Text = _Draw.getElementValue(riNode,"Left","0pt");
            tbTop.Text = _Draw.getElementValue(riNode,"Top","0pt");
            tbWidth.Text = _Draw.getElementValue(riNode,"Width","");
            tbHeight.Text = _Draw.getElementValue(riNode,"Height","");
        } 
        if (StringSupport.equals(riNode.Name, "Textbox"))
        {
            this.cbDataElementStyle.Text = _Draw.getElementValue(riNode,"DataElementStyle","Auto");
            cbHideDuplicates.Items.Add("");
            Object[] dsn = _Draw.getDataSetNames();
            if (dsn != null)
                cbHideDuplicates.Items.AddRange(dsn);
             
            Object[] grps = _Draw.getGroupingNames();
            if (grps != null)
                cbHideDuplicates.Items.AddRange(grps);
             
            this.cbHideDuplicates.Text = _Draw.getElementValue(riNode,"HideDuplicates","");
            this.chkCanGrow.Checked = StringSupport.equals(_Draw.getElementValue(riNode,"CanGrow","false").ToLower(), "true");
            this.chkCanShrink.Checked = StringSupport.equals(_Draw.getElementValue(riNode,"CanShrink","false").ToLower(), "true");
            XmlNode initstate = DesignXmlDraw.findNextInHierarchy(riNode,"ToggleImage","InitialState");
            this.cbToggleImage.Text = initstate == null ? "" : initstate.InnerText;
        }
        else
        {
            this.gbText.Visible = false;
        } 
        fName = fLeft = fTop = fWidth = fHeight = fZIndex = fColSpan = fCanGrow = fCanShrink = fHideDuplicates = fToggleImage = fDataElementStyle = false;
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
        this.gbPosition = new System.Windows.Forms.GroupBox();
        this.tbHeight = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.tbWidth = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.tbTop = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.tbLeft = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.tbZIndex = new System.Windows.Forms.NumericUpDown();
        this.label1 = new System.Windows.Forms.Label();
        this.tbName = new System.Windows.Forms.TextBox();
        this.lblColSpan = new System.Windows.Forms.Label();
        this.tbColSpan = new System.Windows.Forms.NumericUpDown();
        this.gbText = new System.Windows.Forms.GroupBox();
        this.cbToggleImage = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.cbDataElementStyle = new System.Windows.Forms.ComboBox();
        this.label3 = new System.Windows.Forms.Label();
        this.cbHideDuplicates = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.chkCanShrink = new System.Windows.Forms.CheckBox();
        this.chkCanGrow = new System.Windows.Forms.CheckBox();
        this.gbPosition.SuspendLayout();
        ((System.ComponentModel.ISupportInitialize)(this.tbZIndex)).BeginInit();
        ((System.ComponentModel.ISupportInitialize)(this.tbColSpan)).BeginInit();
        this.gbText.SuspendLayout();
        this.SuspendLayout();
        //
        // gbPosition
        //
        this.gbPosition.Controls.Add(this.tbHeight);
        this.gbPosition.Controls.Add(this.label7);
        this.gbPosition.Controls.Add(this.tbWidth);
        this.gbPosition.Controls.Add(this.label8);
        this.gbPosition.Controls.Add(this.tbTop);
        this.gbPosition.Controls.Add(this.label6);
        this.gbPosition.Controls.Add(this.tbLeft);
        this.gbPosition.Controls.Add(this.label5);
        this.gbPosition.Controls.Add(this.label9);
        this.gbPosition.Controls.Add(this.tbZIndex);
        this.gbPosition.Location = new System.Drawing.Point(24, 48);
        this.gbPosition.Name = "gbPosition";
        this.gbPosition.Size = new System.Drawing.Size(384, 112);
        this.gbPosition.TabIndex = 14;
        this.gbPosition.TabStop = false;
        this.gbPosition.Text = "Position";
        //
        // tbHeight
        //
        this.tbHeight.Location = new System.Drawing.Point(224, 48);
        this.tbHeight.Name = "tbHeight";
        this.tbHeight.Size = new System.Drawing.Size(72, 20);
        this.tbHeight.TabIndex = 5;
        this.tbHeight.Text = "";
        this.tbHeight.TextChanged += new System.EventHandler(this.tbHeight_TextChanged);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(168, 48);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(48, 16);
        this.label7.TabIndex = 4;
        this.label7.Text = "Height";
        //
        // tbWidth
        //
        this.tbWidth.Location = new System.Drawing.Point(72, 48);
        this.tbWidth.Name = "tbWidth";
        this.tbWidth.Size = new System.Drawing.Size(72, 20);
        this.tbWidth.TabIndex = 3;
        this.tbWidth.Text = "";
        this.tbWidth.TextChanged += new System.EventHandler(this.tbWidth_TextChanged);
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(16, 48);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(48, 16);
        this.label8.TabIndex = 4;
        this.label8.Text = "Width";
        //
        // tbTop
        //
        this.tbTop.Location = new System.Drawing.Point(224, 16);
        this.tbTop.Name = "tbTop";
        this.tbTop.Size = new System.Drawing.Size(72, 20);
        this.tbTop.TabIndex = 2;
        this.tbTop.Text = "";
        this.tbTop.TextChanged += new System.EventHandler(this.tbTop_TextChanged);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(168, 16);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(48, 16);
        this.label6.TabIndex = 1;
        this.label6.Text = "Top";
        //
        // tbLeft
        //
        this.tbLeft.Location = new System.Drawing.Point(72, 16);
        this.tbLeft.Name = "tbLeft";
        this.tbLeft.Size = new System.Drawing.Size(72, 20);
        this.tbLeft.TabIndex = 0;
        this.tbLeft.Text = "";
        this.tbLeft.TextChanged += new System.EventHandler(this.tbLeft_TextChanged);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(16, 16);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(48, 16);
        this.label5.TabIndex = 0;
        this.label5.Text = "Left";
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(16, 80);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(48, 23);
        this.label9.TabIndex = 15;
        this.label9.Text = "Z Index";
        //
        // tbZIndex
        //
        this.tbZIndex.Location = new System.Drawing.Point(72, 80);
        this.tbZIndex.Name = "tbZIndex";
        this.tbZIndex.Size = new System.Drawing.Size(72, 20);
        this.tbZIndex.TabIndex = 6;
        this.tbZIndex.ValueChanged += new System.EventHandler(this.tbZIndex_ValueChanged);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(24, 15);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(48, 23);
        this.label1.TabIndex = 15;
        this.label1.Text = "Name";
        //
        // tbName
        //
        this.tbName.Location = new System.Drawing.Point(80, 16);
        this.tbName.Name = "tbName";
        this.tbName.Size = new System.Drawing.Size(128, 20);
        this.tbName.TabIndex = 0;
        this.tbName.Text = "textBox1";
        this.tbName.Validating += new System.ComponentModel.CancelEventHandler(this.tbName_Validating);
        this.tbName.TextChanged += new System.EventHandler(this.tbName_TextChanged);
        //
        // lblColSpan
        //
        this.lblColSpan.Location = new System.Drawing.Point(232, 16);
        this.lblColSpan.Name = "lblColSpan";
        this.lblColSpan.Size = new System.Drawing.Size(112, 23);
        this.lblColSpan.TabIndex = 16;
        this.lblColSpan.Text = "Span Table Columns";
        //
        // tbColSpan
        //
        this.tbColSpan.Location = new System.Drawing.Point(360, 16);
        this.tbColSpan.Maximum = new System.Decimal(new int[]{ 1000, 0, 0, 0 });
        this.tbColSpan.Minimum = new System.Decimal(new int[]{ 1, 0, 0, 0 });
        this.tbColSpan.Name = "tbColSpan";
        this.tbColSpan.Size = new System.Drawing.Size(48, 20);
        this.tbColSpan.TabIndex = 1;
        this.tbColSpan.Value = new System.Decimal(new int[]{ 1, 0, 0, 0 });
        this.tbColSpan.ValueChanged += new System.EventHandler(this.tbColSpan_ValueChanged);
        //
        // gbText
        //
        this.gbText.Controls.Add(this.cbToggleImage);
        this.gbText.Controls.Add(this.label4);
        this.gbText.Controls.Add(this.cbDataElementStyle);
        this.gbText.Controls.Add(this.label3);
        this.gbText.Controls.Add(this.cbHideDuplicates);
        this.gbText.Controls.Add(this.label2);
        this.gbText.Controls.Add(this.chkCanShrink);
        this.gbText.Controls.Add(this.chkCanGrow);
        this.gbText.Location = new System.Drawing.Point(24, 168);
        this.gbText.Name = "gbText";
        this.gbText.Size = new System.Drawing.Size(384, 112);
        this.gbText.TabIndex = 18;
        this.gbText.TabStop = false;
        this.gbText.Text = "Text Processing";
        //
        // cbToggleImage
        //
        this.cbToggleImage.Items.AddRange(new Object[]{ "", "true", "false" });
        this.cbToggleImage.Location = new System.Drawing.Point(120, 80);
        this.cbToggleImage.Name = "cbToggleImage";
        this.cbToggleImage.Size = new System.Drawing.Size(256, 21);
        this.cbToggleImage.TabIndex = 7;
        this.cbToggleImage.TextChanged += new System.EventHandler(this.cbToggleImage_Changed);
        this.cbToggleImage.SelectedIndexChanged += new System.EventHandler(this.cbToggleImage_Changed);
        //
        // label4
        //
        this.label4.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.label4.Location = new System.Drawing.Point(16, 80);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(104, 23);
        this.label4.TabIndex = 6;
        this.label4.Text = "Toggle Image";
        //
        // cbDataElementStyle
        //
        this.cbDataElementStyle.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbDataElementStyle.Items.AddRange(new Object[]{ "Auto", "AttributeNormal", "ElementNormal" });
        this.cbDataElementStyle.Location = new System.Drawing.Point(120, 48);
        this.cbDataElementStyle.Name = "cbDataElementStyle";
        this.cbDataElementStyle.Size = new System.Drawing.Size(120, 21);
        this.cbDataElementStyle.TabIndex = 5;
        this.cbDataElementStyle.SelectedIndexChanged += new System.EventHandler(this.cbDataElementStyle_SelectedIndexChanged);
        //
        // label3
        //
        this.label3.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.label3.Location = new System.Drawing.Point(16, 48);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(104, 23);
        this.label3.TabIndex = 4;
        this.label3.Text = "XML Element Style";
        //
        // cbHideDuplicates
        //
        this.cbHideDuplicates.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbHideDuplicates.Location = new System.Drawing.Point(264, 16);
        this.cbHideDuplicates.Name = "cbHideDuplicates";
        this.cbHideDuplicates.Size = new System.Drawing.Size(112, 21);
        this.cbHideDuplicates.TabIndex = 3;
        this.cbHideDuplicates.SelectedIndexChanged += new System.EventHandler(this.cbHideDuplicates_SelectedIndexChanged);
        //
        // label2
        //
        this.label2.ImageAlign = System.Drawing.ContentAlignment.BottomCenter;
        this.label2.Location = new System.Drawing.Point(184, 18);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(88, 22);
        this.label2.TabIndex = 2;
        this.label2.Text = "Hide Duplicates";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // chkCanShrink
        //
        this.chkCanShrink.Location = new System.Drawing.Point(96, 16);
        this.chkCanShrink.Name = "chkCanShrink";
        this.chkCanShrink.TabIndex = 1;
        this.chkCanShrink.Text = "Can Shrink";
        this.chkCanShrink.CheckedChanged += new System.EventHandler(this.chkCanShrink_CheckedChanged);
        //
        // chkCanGrow
        //
        this.chkCanGrow.Location = new System.Drawing.Point(16, 16);
        this.chkCanGrow.Name = "chkCanGrow";
        this.chkCanGrow.Size = new System.Drawing.Size(80, 24);
        this.chkCanGrow.TabIndex = 0;
        this.chkCanGrow.Text = "Can Grow";
        this.chkCanGrow.CheckedChanged += new System.EventHandler(this.chkCanGrow_CheckedChanged);
        //
        // PositionCtl
        //
        this.Controls.Add(this.gbText);
        this.Controls.Add(this.tbColSpan);
        this.Controls.Add(this.lblColSpan);
        this.Controls.Add(this.tbName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gbPosition);
        this.Name = "PositionCtl";
        this.Size = new System.Drawing.Size(472, 288);
        this.gbPosition.ResumeLayout(false);
        ((System.ComponentModel.ISupportInitialize)(this.tbZIndex)).EndInit();
        ((System.ComponentModel.ISupportInitialize)(this.tbColSpan)).EndInit();
        this.gbText.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    public boolean isValid() throws Exception {
        XmlNode ri = this._ReportItems[0] instanceof XmlNode ? (XmlNode)this._ReportItems[0] : (XmlNode)null;
        if (tbName.Enabled && fName)
        {
            String nerr = _Draw.NameError(ri, this.tbName.Text);
            if (nerr != null)
            {
                MessageBox.Show(nerr, "Name");
                return false;
            }
             
        }
         
        String name = "";
        try
        {
            // allow minus if Line and only one item selected
            boolean bMinus = StringSupport.equals(ri.Name, "Line") && tbName.Enabled ? true : false;
            if (fLeft)
            {
                name = "Left";
                DesignerUtility.ValidateSize(this.tbLeft.Text, true, false);
            }
             
            if (fTop)
            {
                name = "Top";
                DesignerUtility.ValidateSize(this.tbTop.Text, true, false);
            }
             
            if (fWidth)
            {
                name = "Width";
                DesignerUtility.ValidateSize(this.tbWidth.Text, true, bMinus);
            }
             
            if (fHeight)
            {
                name = "Height";
                DesignerUtility.ValidateSize(this.tbHeight.Text, true, bMinus);
            }
             
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message, name + " Size is Invalid");
            return false;
        }

        return true;
    }

    public void apply() throws Exception {
        for (Object __dummyForeachVar0 : this._ReportItems)
        {
            // take information in control and apply to all the style nodes
            //  Only change information that has been marked as modified;
            //   this way when group is selected it is possible to change just
            //   the items you want and keep the rest the same.
            XmlNode riNode = (XmlNode)__dummyForeachVar0;
            applyChanges(riNode);
        }
        // No more changes
        fName = fLeft = fTop = fWidth = fHeight = fZIndex = fColSpan = fCanGrow = fCanShrink = fHideDuplicates = fToggleImage = fDataElementStyle = false;
    }

    public void applyChanges(XmlNode node) throws Exception {
        if (tbName.Enabled && fName)
        {
            _Draw.SetName(node, tbName.Text.Trim());
        }
         
        if (fLeft)
            _Draw.SetElement(node, "Left", DesignerUtility.MakeValidSize(tbLeft.Text, true));
         
        if (fTop)
            _Draw.SetElement(node, "Top", DesignerUtility.MakeValidSize(tbTop.Text, true));
         
        boolean bLine = StringSupport.equals(node.Name, "Line");
        if (fWidth)
            _Draw.SetElement(node, "Width", DesignerUtility.MakeValidSize(tbWidth.Text, bLine, bLine));
         
        if (fHeight)
            _Draw.SetElement(node, "Height", DesignerUtility.MakeValidSize(tbHeight.Text, bLine, bLine));
         
        if (fZIndex)
            _Draw.SetElement(node, "ZIndex", tbZIndex.Text);
         
        if (fColSpan)
        {
            XmlNode ris = node.ParentNode;
            XmlNode tcell = ris.ParentNode;
            if (StringSupport.equals(tcell.Name, "TableCell"))
            {
                // SetTableCellColSpan does all the heavy lifting;
                //    ie making sure the # of columns continue to match
                _Draw.SetTableCellColSpan(tcell, tbColSpan.Value.ToString());
            }
             
        }
         
        if (fDataElementStyle)
            _Draw.SetElement(node, "DataElementStyle", this.cbDataElementStyle.Text);
         
        if (fHideDuplicates)
            _Draw.SetElement(node, "HideDuplicates", this.cbHideDuplicates.Text);
         
        if (fCanGrow)
            _Draw.setElement(node,"CanGrow",this.chkCanGrow.Checked ? "true" : "false");
         
        if (fCanShrink)
            _Draw.setElement(node,"CanShrink",this.chkCanShrink.Checked ? "true" : "false");
         
        if (fDataElementStyle)
            _Draw.SetElement(node, "DataElementStyle", this.cbDataElementStyle.Text);
         
        if (fToggleImage)
        {
            if (cbToggleImage.Text.Length <= 0)
            {
                _Draw.removeElement(node,"ToggleImage");
            }
            else
            {
                XmlNode ti = _Draw.setElement(node,"ToggleImage",null);
                _Draw.SetElement(ti, "InitialState", cbToggleImage.Text);
            } 
        }
         
    }

    private void tbName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fName = true;
    }

    private void tbLeft_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fLeft = true;
    }

    private void tbTop_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fTop = true;
    }

    private void tbWidth_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fWidth = true;
    }

    private void tbHeight_TextChanged(Object sender, System.EventArgs e) throws Exception {
        fHeight = true;
    }

    private void tbZIndex_ValueChanged(Object sender, System.EventArgs e) throws Exception {
        fZIndex = true;
    }

    private void tbName_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        XmlNode xNode = this._ReportItems[0];
        String err = _Draw.NameError(xNode, tbName.Text.Trim());
        if (err != null)
        {
            e.Cancel = true;
            MessageBox.Show(String.Format("{0} is invalid.  {1}", tbName.Text, err), "Name");
            return ;
        }
         
    }

    private void tbColSpan_ValueChanged(Object sender, System.EventArgs e) throws Exception {
        fColSpan = true;
    }

    private void cbToggleImage_Changed(Object sender, System.EventArgs e) throws Exception {
        fToggleImage = true;
    }

    private void cbDataElementStyle_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        fDataElementStyle = true;
    }

    private void cbHideDuplicates_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        fHideDuplicates = true;
    }

    private void chkCanShrink_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        fCanShrink = true;
    }

    private void chkCanGrow_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        fCanGrow = true;
    }

}


