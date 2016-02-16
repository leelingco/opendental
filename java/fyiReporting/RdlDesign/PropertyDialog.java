//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:21 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.BodyCtl;
import fyiReporting.RdlDesign.ChartAxisCtl;
import fyiReporting.RdlDesign.ChartCtl;
import fyiReporting.RdlDesign.ChartLegendCtl;
import fyiReporting.RdlDesign.CodeCtl;
import fyiReporting.RdlDesign.CustomReportItemCtl;
import fyiReporting.RdlDesign.DataSetRowsCtl;
import fyiReporting.RdlDesign.DataSetsCtl;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.FiltersCtl;
import fyiReporting.RdlDesign.GroupingCtl;
import fyiReporting.RdlDesign.ImageCtl;
import fyiReporting.RdlDesign.InteractivityCtl;
import fyiReporting.RdlDesign.IProperty;
import fyiReporting.RdlDesign.ListCtl;
import fyiReporting.RdlDesign.MatrixCtl;
import fyiReporting.RdlDesign.ModulesClassesCtl;
import fyiReporting.RdlDesign.PositionCtl;
import fyiReporting.RdlDesign.PropertyTypeEnum;
import fyiReporting.RdlDesign.QueryParametersCtl;
import fyiReporting.RdlDesign.ReportCtl;
import fyiReporting.RdlDesign.ReportParameterCtl;
import fyiReporting.RdlDesign.ReportXmlCtl;
import fyiReporting.RdlDesign.SortingCtl;
import fyiReporting.RdlDesign.StyleBorderCtl;
import fyiReporting.RdlDesign.StyleCtl;
import fyiReporting.RdlDesign.StyleTextCtl;
import fyiReporting.RdlDesign.SubreportCtl;
import fyiReporting.RdlDesign.TableColumnCtl;
import fyiReporting.RdlDesign.TableCtl;
import fyiReporting.RdlDesign.TableRowCtl;

/**
* Summary description for PropertyDialog.
*/
public class PropertyDialog  extends System.Windows.Forms.Form 
{
    private DesignXmlDraw _Draw;
    // design draw
    private List<XmlNode> _Nodes = new List<XmlNode>();
    // selected nodes
    private PropertyTypeEnum _Type = PropertyTypeEnum.Report;
    private boolean _Changed = false;
    private boolean _Delete = false;
    private XmlNode _TableColumn = null;
    // when table this is the current table column
    private XmlNode _TableRow = null;
    // when table this is the current table row
    private List<UserControl> _TabPanels = new List<UserControl>();
    // list of IProperty controls
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Button bCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bApply = new System.Windows.Forms.Button();
    private System.Windows.Forms.TabControl tcProps = new System.Windows.Forms.TabControl();
    private System.Windows.Forms.Button bDelete = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public PropertyDialog(DesignXmlDraw dxDraw, List<XmlNode> sNodes, PropertyTypeEnum type) throws Exception {
        this(dxDraw, sNodes, type, null, null);
    }

    public PropertyDialog(DesignXmlDraw dxDraw, List<XmlNode> sNodes, PropertyTypeEnum type, XmlNode tcNode, XmlNode trNode) throws Exception {
        this._Draw = dxDraw;
        this._Nodes = sNodes;
        this._Type = type;
        _TableColumn = tcNode;
        _TableRow = trNode;
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        //   Add the controls for the selected ReportItems
        switch(_Type)
        {
            case Report: 
                buildReportTabs();
                break;
            case DataSets: 
                buildDataSetsTabs();
                break;
            case Grouping: 
                buildGroupingTabs();
                break;
            case ChartLegend: 
                buildChartLegendTabs();
                break;
            case CategoryAxis: 
            case ValueAxis: 
                buildChartAxisTabs(type);
                break;
            case ChartTitle: 
            case CategoryAxisTitle: 
            case ValueAxisTitle: 
                buildTitle(type);
                break;
            case ReportItems: 
            default: 
                buildReportItemTabs();
                break;
        
        }
    }

    public boolean getChanged() throws Exception {
        return _Changed;
    }

    public boolean getDelete() throws Exception {
        return _Delete;
    }

    private void buildReportTabs() throws Exception {
        this.Text = "Report Properties";
        ReportCtl rc = new ReportCtl(_Draw);
        AddTab("Report", rc);
        ReportParameterCtl pc = new ReportParameterCtl(_Draw);
        AddTab("Parameters", pc);
        ReportXmlCtl xc = new ReportXmlCtl(_Draw);
        AddTab("XML Rendering", xc);
        BodyCtl bc = new BodyCtl(_Draw);
        AddTab("Body", bc);
        CodeCtl cc = new CodeCtl(_Draw);
        AddTab("Code", cc);
        ModulesClassesCtl mc = new ModulesClassesCtl(_Draw);
        AddTab("Modules/Classes", mc);
        return ;
    }

    private void buildDataSetsTabs() throws Exception {
        bDelete.Visible = true;
        this.Text = "DataSet";
        XmlNode aNode = new XmlNode();
        if (_Nodes != null && _Nodes.Count > 0)
            aNode = _Nodes[0];
        else
            aNode = null; 
        DataSetsCtl dsc = new DataSetsCtl(_Draw,aNode);
        AddTab("DataSet", dsc);
        QueryParametersCtl qp = new QueryParametersCtl(_Draw,dsc.getDSV());
        AddTab("Query Parameters", qp);
        FiltersCtl fc = new FiltersCtl(_Draw,aNode);
        AddTab("Filters", fc);
        DataSetRowsCtl dsrc = new DataSetRowsCtl(_Draw,aNode,dsc.getDSV());
        AddTab("Data", dsrc);
        return ;
    }

    private void buildGroupingTabs() throws Exception {
        XmlNode aNode = _Nodes[0];
        if (StringSupport.equals(aNode.Name, "DynamicSeries"))
        {
            this.Text = "Series Grouping";
        }
        else if (StringSupport.equals(aNode.Name, "DynamicCategories"))
        {
            this.Text = "Category Grouping";
        }
        else
        {
            this.Text = "Grouping and Sorting";
        }  
        GroupingCtl gc = new GroupingCtl(_Draw,aNode);
        AddTab("Grouping", gc);
        SortingCtl sc = new SortingCtl(_Draw,aNode);
        AddTab("Sorting", sc);
        // We have to create a grouping here but will need to kill it if no definition follows it
        XmlNode gNode = _Draw.getCreateNamedChildNode(aNode,"Grouping");
        FiltersCtl fc = new FiltersCtl(_Draw,gNode);
        AddTab("Filters", fc);
        return ;
    }

    private void buildReportItemTabs() throws Exception {
        XmlNode aNode = _Nodes[0];
        // Determine if all nodes are the same type
        String type = aNode.Name;
        if (StringSupport.equals(type, "CustomReportItem"))
        {
            // For customReportItems we use the type that is a parameter
            String t = _Draw.getElementValue(aNode,"Type","");
            if (t.Length > 0)
                type = t;
             
        }
         
        for (Object __dummyForeachVar0 : this._Nodes)
        {
            XmlNode pNode = (XmlNode)__dummyForeachVar0;
            // For customReportItems we use the type that is a parameter
            String t = pNode.Name;
            if (StringSupport.equals(t, "CustomReportItem"))
            {
                t = _Draw.getElementValue(aNode,"Type","");
                if (t.Length == 0)
                    // Shouldn't happen
                    t = pNode.Name;
                 
            }
             
            if (!StringSupport.equals(t, type))
                type = "";
             
        }
        // Not all nodes have the same type
        ensureStyle();
        // Make sure we have Style nodes for all the report items
        if (_Nodes.Count > 1)
            this.Text = "Group Selection Properties";
        else
        {
            String name = _Draw.getElementAttribute(aNode,"Name","");
            this.Text = String.Format("{0} {1} Properties", type, name);
        } 
        // Create all the tabs
        if (StringSupport.equals(type, "Textbox"))
        {
            StyleTextCtl stc = new StyleTextCtl(_Draw,this._Nodes);
            AddTab("Text", stc);
        }
        else if (StringSupport.equals(type, "List"))
        {
            ListCtl lc = new ListCtl(_Draw,this._Nodes);
            AddTab("List", lc);
            if (_Nodes.Count == 1)
            {
                XmlNode l = _Nodes[0];
                FiltersCtl fc = new FiltersCtl(_Draw,l);
                AddTab("Filters", fc);
                SortingCtl srtc = new SortingCtl(_Draw,l);
                AddTab("Sorting", srtc);
            }
             
        }
        else if (StringSupport.equals(type, "Chart"))
        {
            ChartCtl cc = new ChartCtl(_Draw,this._Nodes);
            AddTab("Chart", cc);
            if (_Nodes.Count == 1)
            {
                FiltersCtl fc = new FiltersCtl(_Draw, _Nodes[0]);
                AddTab("Filters", fc);
            }
             
        }
        else if (StringSupport.equals(type, "Image"))
        {
            ImageCtl imgc = new ImageCtl(_Draw,this._Nodes);
            AddTab("Image", imgc);
        }
        else if (StringSupport.equals(type, "Table"))
        {
            XmlNode table = _Nodes[0];
            TableCtl tc = new TableCtl(_Draw,this._Nodes);
            AddTab("Table", tc);
            FiltersCtl fc = new FiltersCtl(_Draw,table);
            AddTab("Filters", fc);
            XmlNode details = _Draw.getNamedChildNode(table,"Details");
            if (details != null)
            {
                // if no details then we don't need details sorting
                GroupingCtl grpc = new GroupingCtl(_Draw,details);
                AddTab("Grouping", grpc);
                SortingCtl srtc = new SortingCtl(_Draw,details);
                AddTab("Sorting", srtc);
            }
             
            if (_TableColumn != null)
            {
                TableColumnCtl tcc = new TableColumnCtl(_Draw,_TableColumn);
                AddTab("Table Column", tcc);
            }
             
            if (_TableRow != null)
            {
                TableRowCtl trc = new TableRowCtl(_Draw,_TableRow);
                AddTab("Table Row", trc);
            }
             
        }
        else if (StringSupport.equals(type, "Matrix"))
        {
            XmlNode matrix = _Nodes[0];
            MatrixCtl mc = new MatrixCtl(_Draw,this._Nodes);
            AddTab("Matrix", mc);
            FiltersCtl fc = new FiltersCtl(_Draw,matrix);
            AddTab("Filters", fc);
        }
        else if (StringSupport.equals(type, "Subreport") && _Nodes.Count == 1)
        {
            XmlNode subreport = _Nodes[0];
            SubreportCtl src = new SubreportCtl(_Draw,subreport);
            AddTab("Subreport", src);
        }
        else if (StringSupport.equals(aNode.Name, "CustomReportItem"))
        {
            XmlNode cri = _Nodes[0];
            CustomReportItemCtl cric = new CustomReportItemCtl(_Draw,_Nodes);
            AddTab(type, cric);
        }
                
        // Position tab
        PositionCtl pc = new PositionCtl(_Draw,this._Nodes);
        AddTab("Name/Position", pc);
        // Border tab
        StyleBorderCtl bc = new StyleBorderCtl(_Draw,this._Nodes);
        AddTab("Border", bc);
        if (!(StringSupport.equals(type, "Line") || StringSupport.equals(type, "Subreport")))
        {
            // Style tab
            StyleCtl sc = new StyleCtl(_Draw,this._Nodes);
            AddTab("Style", sc);
            // Interactivity tab
            InteractivityCtl ic = new InteractivityCtl(_Draw,this._Nodes);
            AddTab("Interactivity", ic);
        }
         
    }

    private void buildChartAxisTabs(PropertyTypeEnum type) throws Exception {
        String propName = new String();
        if (type == PropertyTypeEnum.CategoryAxis)
        {
            this.Text = "Chart Category (X) Axis";
            propName = "CategoryAxis";
        }
        else
        {
            this.Text = "Chart Value (Y) Axis";
            propName = "ValueAxis";
        } 
        XmlNode cNode = _Nodes[0];
        XmlNode aNode = _Draw.getCreateNamedChildNode(cNode,propName);
        XmlNode axNode = _Draw.getCreateNamedChildNode(aNode,"Axis");
        // Now we replace the node array with a new one containing only the legend
        _Nodes = new List<XmlNode>();
        _Nodes.Add(axNode);
        ensureStyle();
        // Make sure we have Style nodes
        // Chart Axis
        ChartAxisCtl cac = new ChartAxisCtl(_Draw,this._Nodes);
        AddTab("Axis", cac);
        // Style Text
        StyleTextCtl stc = new StyleTextCtl(_Draw,this._Nodes);
        AddTab("Text", stc);
        // Border tab
        StyleBorderCtl bc = new StyleBorderCtl(_Draw,this._Nodes);
        AddTab("Border", bc);
        // Style tab
        StyleCtl sc = new StyleCtl(_Draw,this._Nodes);
        AddTab("Style", sc);
    }

    private void buildChartLegendTabs() throws Exception {
        this.Text = "Chart Legend Properties";
        XmlNode cNode = _Nodes[0];
        XmlNode lNode = _Draw.getCreateNamedChildNode(cNode,"Legend");
        // Now we replace the node array with a new one containing only the legend
        _Nodes = new List<XmlNode>();
        _Nodes.Add(lNode);
        ensureStyle();
        // Make sure we have Style nodes
        // Chart Legend
        ChartLegendCtl clc = new ChartLegendCtl(_Draw,this._Nodes);
        AddTab("Legend", clc);
        // Style Text
        StyleTextCtl stc = new StyleTextCtl(_Draw,this._Nodes);
        AddTab("Text", stc);
        // Border tab
        StyleBorderCtl bc = new StyleBorderCtl(_Draw,this._Nodes);
        AddTab("Border", bc);
        // Style tab
        StyleCtl sc = new StyleCtl(_Draw,this._Nodes);
        AddTab("Style", sc);
    }

    private void buildTitle(PropertyTypeEnum type) throws Exception {
        XmlNode cNode = _Nodes[0];
        _Nodes = new List<XmlNode>();
        // replace with a new one
        if (type == PropertyTypeEnum.ChartTitle)
        {
            this.Text = "Chart Title";
            XmlNode lNode = _Draw.getCreateNamedChildNode(cNode,"Title");
            _Nodes.Add(lNode);
        }
        else // Working on the title
        if (type == PropertyTypeEnum.CategoryAxisTitle)
        {
            this.Text = "Category (X) Axis Title";
            XmlNode caNode = _Draw.getCreateNamedChildNode(cNode,"CategoryAxis");
            XmlNode aNode = _Draw.getCreateNamedChildNode(caNode,"Axis");
            XmlNode tNode = _Draw.getCreateNamedChildNode(aNode,"Title");
            _Nodes.Add(tNode);
        }
        else
        {
            // Working on the title
            this.Text = "Value (Y) Axis Title";
            XmlNode caNode = _Draw.getCreateNamedChildNode(cNode,"ValueAxis");
            XmlNode aNode = _Draw.getCreateNamedChildNode(caNode,"Axis");
            XmlNode tNode = _Draw.getCreateNamedChildNode(aNode,"Title");
            _Nodes.Add(tNode);
        }  
        // Working on the title
        ensureStyle();
        // Make sure we have Style nodes
        // Style Text
        StyleTextCtl stc = new StyleTextCtl(_Draw,this._Nodes);
        AddTab("Text", stc);
        // Border tab
        StyleBorderCtl bc = new StyleBorderCtl(_Draw,this._Nodes);
        AddTab("Border", bc);
        // Style tab
        StyleCtl sc = new StyleCtl(_Draw,this._Nodes);
        AddTab("Style", sc);
    }

    private void ensureStyle() throws Exception {
        for (Object __dummyForeachVar1 : this._Nodes)
        {
            // Make sure we have Style nodes for all the nodes
            XmlNode pNode = (XmlNode)__dummyForeachVar1;
            XmlNode stNode = _Draw.getCreateNamedChildNode(pNode,"Style");
        }
        return ;
    }

    private void addTab(String name, UserControl uc) throws Exception {
        // Style tab
        TabPage tp = new TabPage();
        tp.Location = new System.Drawing.Point(4, 22);
        tp.Name = name + "1";
        tp.Size = new System.Drawing.Size(552, 284);
        tp.TabIndex = 1;
        tp.Text = name;
        _TabPanels.Add(uc);
        tp.Controls.Add(uc);
        uc.Dock = System.Windows.Forms.DockStyle.Fill;
        uc.Location = new System.Drawing.Point(0, 0);
        uc.Name = name + "1";
        uc.Size = new System.Drawing.Size(552, 284);
        uc.TabIndex = 0;
        tcProps.Controls.Add(tp);
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
        this.panel1 = new System.Windows.Forms.Panel();
        this.bDelete = new System.Windows.Forms.Button();
        this.bApply = new System.Windows.Forms.Button();
        this.bOK = new System.Windows.Forms.Button();
        this.bCancel = new System.Windows.Forms.Button();
        this.tcProps = new System.Windows.Forms.TabControl();
        this.panel1.SuspendLayout();
        this.SuspendLayout();
        //
        // panel1
        //
        this.panel1.CausesValidation = false;
        this.panel1.Controls.Add(this.bDelete);
        this.panel1.Controls.Add(this.bApply);
        this.panel1.Controls.Add(this.bOK);
        this.panel1.Controls.Add(this.bCancel);
        this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.panel1.Location = new System.Drawing.Point(0, 326);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(458, 40);
        this.panel1.TabIndex = 1;
        //
        // bDelete
        //
        this.bDelete.Location = new System.Drawing.Point(8, 8);
        this.bDelete.Name = "bDelete";
        this.bDelete.TabIndex = 3;
        this.bDelete.Text = "Delete";
        this.bDelete.Visible = false;
        this.bDelete.Click += new System.EventHandler(this.bDelete_Click);
        //
        // bApply
        //
        this.bApply.Location = new System.Drawing.Point(376, 8);
        this.bApply.Name = "bApply";
        this.bApply.TabIndex = 2;
        this.bApply.Text = "Apply";
        this.bApply.Click += new System.EventHandler(this.bApply_Click);
        //
        // bOK
        //
        this.bOK.Location = new System.Drawing.Point(216, 8);
        this.bOK.Name = "bOK";
        this.bOK.TabIndex = 0;
        this.bOK.Text = "OK";
        this.bOK.Click += new System.EventHandler(this.bOK_Click);
        //
        // bCancel
        //
        this.bCancel.CausesValidation = false;
        this.bCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.bCancel.Location = new System.Drawing.Point(296, 8);
        this.bCancel.Name = "bCancel";
        this.bCancel.TabIndex = 1;
        this.bCancel.Text = "Cancel";
        //
        // tcProps
        //
        this.tcProps.Dock = System.Windows.Forms.DockStyle.Fill;
        this.tcProps.Location = new System.Drawing.Point(0, 0);
        this.tcProps.Multiline = true;
        this.tcProps.Name = "tcProps";
        this.tcProps.SelectedIndex = 0;
        this.tcProps.Size = new System.Drawing.Size(458, 326);
        this.tcProps.TabIndex = 0;
        //
        // PropertyDialog
        //
        this.AcceptButton = this.bOK;
        this.AutoScaleMode = AutoScaleMode.None;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.bCancel;
        this.ClientSize = new System.Drawing.Size(458, 366);
        this.Controls.Add(this.tcProps);
        this.Controls.Add(this.panel1);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "PropertyDialog";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "Properties";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.PropertyDialog_Closing);
        this.panel1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void bApply_Click(Object sender, System.EventArgs e) throws Exception {
        if (!isValid())
            return ;
         
        this._Changed = true;
        for (Object __dummyForeachVar2 : _TabPanels)
        {
            IProperty ip = (IProperty)__dummyForeachVar2;
            ip.apply();
        }
        this._Draw.Invalidate();
    }

    // Force screen to redraw
    private void bOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!isValid())
            return ;
         
        bApply_Click(sender,e);
        // Apply does all the work
        this.DialogResult = DialogResult.OK;
    }

    private boolean isValid() throws Exception {
        int index = 0;
        for (Object __dummyForeachVar3 : _TabPanels)
        {
            IProperty ip = (IProperty)__dummyForeachVar3;
            if (!ip.isValid())
            {
                tcProps.SelectedIndex = index;
                return false;
            }
             
            index++;
        }
        return true;
    }

    private void propertyDialog_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (_Type == PropertyTypeEnum.Grouping)
        {
            // Need to check if grouping value is still required
            XmlNode aNode = _Nodes[0];
            // We have to create a grouping here but will need to kill it if no definition follows it
            XmlNode gNode = _Draw.getNamedChildNode(aNode,"Grouping");
            if (gNode != null && _Draw.getNamedChildNode(gNode,"GroupExpressions") == null)
            {
                // Not a valid group if no GroupExpressions
                aNode.RemoveChild(gNode);
            }
             
        }
         
    }

    private void bDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (MessageBox.Show(this, "Are you sure you want to delete this dataset?", "DataSet", MessageBoxButtons.YesNo) == DialogResult.Yes)
        {
            _Delete = true;
            this.DialogResult = DialogResult.OK;
        }
         
    }

}


