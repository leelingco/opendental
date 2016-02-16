//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:17 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.BackgroundGradientTypeEnum;
import fyiReporting.RDL.BorderStyleEnum;
import fyiReporting.RDL.CalendarEnum;
import fyiReporting.RDL.DirectionEnum;
import fyiReporting.RDL.FontStyleEnum;
import fyiReporting.RDL.FontWeightEnum;
import fyiReporting.RDL.ICustomReportItem;
import fyiReporting.RDL.Image;
import fyiReporting.RDL.ImageSizing;
import fyiReporting.RDL.ImageSizingEnum;
import fyiReporting.RDL.RdlEngineConfig;
import fyiReporting.RDL.Rectangle;
import fyiReporting.RDL.StyleInfo;
import fyiReporting.RDL.TextAlignEnum;
import fyiReporting.RDL.TextDecorationEnum;
import fyiReporting.RDL.UnicodeBiDirectionalEnum;
import fyiReporting.RDL.VerticalAlignEnum;
import fyiReporting.RDL.WritingModeEnum;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.DialogValidateRdl;
import fyiReporting.RdlDesign.HitLocation;
import fyiReporting.RdlDesign.HitLocationEnum;
import fyiReporting.RdlDesign.MatrixItem;
import fyiReporting.RdlDesign.MatrixView;
import fyiReporting.RdlDesign.ReportItemSorter;
import fyiReporting.RdlDesign.ReportNames;

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
* Control for providing a designer image of RDL.   Works directly off the RDL XML.
*/
public class DesignXmlDraw  extends UserControl 
{
    static public final float POINTSIZED = 72.27f;
    static public final double POINTSIZEM = 72.27m;
    static final float RADIUS = 2.5f;
    static final int BANDHEIGHT = 12;
    static final float LEFTGAP = 12f;
    // keep a gap on the left size of the screen
    // Various page measurements that we keep
    float rWidth = new float(), pHeight = new float(), pWidth = new float();
    float lMargin = new float(), rMargin = new float(), tMargin = new float(), bMargin = new float();
    XmlNode bodyNode = new XmlNode();
    XmlNode phNode = new XmlNode();
    XmlNode pfNode = new XmlNode();
    private XmlDocument rDoc = new XmlDocument();
    // the reporting XML document
    private List<XmlNode> _SelectedReportItems = new List<XmlNode>();
    private ReportNames _ReportNames;
    // holds the names of the report items
    float DpiX = new float();
    float DpiY = new float();
    // During drawing these are set
    Graphics g = new Graphics();
    float _vScroll = new float();
    float _hScroll = new float();
    RectangleF _clip = new RectangleF();
    // During hit testing
    PointF _HitPoint = new PointF();
    RectangleF _HitRect = new RectangleF();
    // Durning GetRectangle
    XmlNode _RectNode = new XmlNode();
    RectangleF _GetRect = new RectangleF();
    boolean _ShowReportItemOutline = false;
    public DesignXmlDraw() throws Exception {
        super();
        // Get our graphics DPI
        Graphics ga = null;
        try
        {
            ga = this.CreateGraphics();
            DpiX = ga.DpiX;
            DpiY = ga.DpiY;
        }
        catch (Exception __dummyCatchVar0)
        {
            DpiX = DpiY = 96;
        }
        finally
        {
            if (ga != null)
                ga.Dispose();
             
        }
        // force to double buffering for smoother drawing
        this.SetStyle(ControlStyles.DoubleBuffer | ControlStyles.UserPaint | ControlStyles.AllPaintingInWmPaint, true);
    }

    /**
    * Need to override otherwise don't get key events for up/down/left/right
    * 
    *  @param keyData 
    *  @return
    */
    protected boolean isInputKey(Keys keyData) throws Exception {
        return true;
    }

    public boolean getShowReportItemOutline() throws Exception {
        return _ShowReportItemOutline;
    }

    public void setShowReportItemOutline(boolean value) throws Exception {
        if (value != _ShowReportItemOutline)
            this.Invalidate();
         
        _ShowReportItemOutline = value;
    }

    public ReportNames getReportNames() throws Exception {
        if (_ReportNames == null && getReportDocument() != null)
            _ReportNames = new ReportNames(rDoc);
         
        return _ReportNames;
    }

    // rebuild report names on demand
    public void setReportNames(ReportNames value) throws Exception {
        _ReportNames = value;
    }

    public XmlDocument getReportDocument() throws Exception {
        return rDoc;
    }

    public void setReportDocument(XmlDocument value) throws Exception {
        rDoc = value;
        if (rDoc != null)
        {
            setReportNames(null);
            // this needs to get rebuilt
            ProcessReport(rDoc.LastChild);
            this.clearSelected();
        }
        else
        {
            this._SelectedReportItems.Clear();
            setReportNames(null);
            this.clearSelected();
        } 
    }

    public XmlNode getBody() throws Exception {
        return bodyNode;
    }

    public RectangleF getRectangle(XmlNode xNode) throws Exception {
        _RectNode = xNode;
        // this is the rectangle we're trying to find;
        float yLoc = 0;
        _GetRect = RectangleF.Empty;
        try
        {
            yLoc += getRectReportPrimaryRegions(phNode,LEFTGAP,yLoc);
            yLoc += getRectReportPrimaryRegions(bodyNode,LEFTGAP,yLoc);
            yLoc += getRectReportPrimaryRegions(pfNode,LEFTGAP,yLoc);
        }
        catch (Exception e)
        {
            // this is the normal exit; we throw exception when node is found
            if (StringSupport.equals(e.Message, "found it!"))
                return _GetRect;
             
        }

        return RectangleF.Empty;
    }

    private float getRectReportPrimaryRegions(XmlNode xNode, float xLoc, float yLoc) throws Exception {
        if (xNode == null)
            return yLoc;
         
        XmlNode items = null;
        float height = 0;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Height"))
            {
                height = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("ReportItems"))
            {
                items = xNodeLoop;
            }
              
        }
        RectangleF b = new RectangleF(xLoc, yLoc, int.MaxValue, height);
        getRectReportItems(items,b);
        return height + BANDHEIGHT;
    }

    // now draw the report items
    private void getRectReportItems(XmlNode xNode, RectangleF r) throws Exception {
        if (xNode == null)
            return ;
         
        for (Object __dummyForeachVar1 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar1;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            RectangleF rir = RectangleF.Empty;
            Name __dummyScrutVar1 = xNodeLoop.Name;
            if (__dummyScrutVar1.equals("Textbox") || __dummyScrutVar1.equals("Image") || __dummyScrutVar1.equals("Subreport") || __dummyScrutVar1.equals("Chart") || __dummyScrutVar1.equals("Line") || __dummyScrutVar1.equals("CustomReportItem"))
            {
                rir = getRectRI(xNodeLoop,r);
            }
            else if (__dummyScrutVar1.equals("Table"))
            {
                rir = getRectTable(xNodeLoop,r);
            }
            else if (__dummyScrutVar1.equals("Rectangle") || __dummyScrutVar1.equals("List"))
            {
                rir = getRectListRectangle(xNodeLoop,r);
            }
            else if (__dummyScrutVar1.equals("Matrix"))
            {
                rir = getRectMatrix(xNodeLoop,r);
            }
                
            if (xNodeLoop == this._RectNode)
            {
                this._GetRect = rir;
                throw new Exception("found it!");
            }
             
        }
    }

    private RectangleF getRectRI(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF ir = getReportItemRect(xNode,r);
        return ir;
    }

    private RectangleF getRectListRectangle(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF listR = getReportItemRect(xNode,r);
        XmlNode items = this.getNamedChildNode(xNode,"ReportItems");
        if (items != null)
            getRectReportItems(items,listR);
         
        return listR;
    }

    private RectangleF getRectMatrix(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF mr = getReportItemRect(xNode,r);
        return mr;
    }

    // get the matrix rectangle
    private RectangleF getRectTable(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF tr = getReportItemRect(xNode,r);
        // get the table rectangle
        // For Table width is really defined by the table columns
        float[] colWidths = new float[]();
        colWidths = getTableColumnWidths(getNamedChildNode(xNode,"TableColumns"));
        // calc the total width
        float w = 0;
        for (Object __dummyForeachVar2 : colWidths)
        {
            float cw = (Float)__dummyForeachVar2;
            w += cw;
        }
        tr.Width = w;
        // For Table height is really defined the sum of the RowHeights
        List<XmlNode> trs = getTableRows(xNode);
        tr.Height = GetTableRowsHeight(trs);
        // Loop thru the TableRows and the columns in each of them to get at the
        //  individual cell
        float yPos = tr.Y;
        for (Object __dummyForeachVar4 : trs)
        {
            XmlNode trow = (XmlNode)__dummyForeachVar4;
            XmlNode tcells = getNamedChildNode(trow,"TableCells");
            float h = GetSize(getNamedChildNode(trow,"Height").InnerText);
            float xPos = tr.X;
            int col = 0;
            for (Object __dummyForeachVar3 : tcells)
            {
                XmlNode tcell = (XmlNode)__dummyForeachVar3;
                if (!StringSupport.equals(tcell.Name, "TableCell"))
                    continue;
                 
                // Calculate width based on cell span
                float width = 0;
                int colSpan = Convert.ToInt32(getElementValue(tcell,"ColSpan","1"));
                for (int i = 0;i < colSpan && col + i < colWidths.Length;i++)
                {
                    width += colWidths[col + i];
                }
                RectangleF cellR = new RectangleF(xPos, yPos, width, h);
                getRectReportItems(getNamedChildNode(tcell,"ReportItems"),cellR);
                xPos += width;
                col += colSpan;
            }
            yPos += h;
        }
        return tr;
    }

    public XmlNode getReportItemContainer(XmlNode xNode) throws Exception {
        for (XmlNode lNode = xNode.ParentNode;lNode != null;lNode = lNode.ParentNode)
        {
            Name __dummyScrutVar2 = lNode.Name;
            if (__dummyScrutVar2.equals("List") || __dummyScrutVar2.equals("Body") || __dummyScrutVar2.equals("PageHeader") || __dummyScrutVar2.equals("PageFooter") || __dummyScrutVar2.equals("Rectangle") || __dummyScrutVar2.equals("Table") || __dummyScrutVar2.equals("Matrix"))
            {
                return lNode;
            }
             
        }
        return null;
    }

    public XmlNode getReportItemDataRegionContainer(XmlNode xNode) throws Exception {
        for (XmlNode cont = getReportItemContainer(xNode);cont != null;cont = getReportItemContainer(cont))
        {
            if (isDataRegion(cont))
                return cont;
             
        }
        return null;
    }

    public boolean isDataRegion(XmlNode node) throws Exception {
        if (node == null)
            return false;
         
        Name __dummyScrutVar3 = node.Name;
        if (__dummyScrutVar3.equals("List") || __dummyScrutVar3.equals("Table") || __dummyScrutVar3.equals("Matrix") || __dummyScrutVar3.equals("Chart"))
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

    public String[] getReportItemDataRegionFields(XmlNode xNode, boolean bExpression) throws Exception {
        XmlNode cNode = getReportItemContainer(xNode);
        if (cNode == null || StringSupport.equals(cNode.Name, "Body") || StringSupport.equals(cNode.Name, "PageHeader") || StringSupport.equals(cNode.Name, "PageFooter"))
            return null;
         
        String dsname = getDataSetNameValue(cNode);
        String[] f = null;
        if (dsname != null)
            // found it
            f = getFields(dsname,bExpression);
         
        return f;
    }

    public void applyStyleToSelected(String name, String v) throws Exception {
        for (Object __dummyForeachVar5 : _SelectedReportItems)
        {
            XmlNode n = (XmlNode)__dummyForeachVar5;
            XmlNode sNode = this.getCreateNamedChildNode(n,"Style");
            this.setElement(sNode,name,v);
        }
        this.Invalidate();
    }

    /**
    * Returns a collection of the DataSetNames
    */
    public Object[] getDataSetNames() throws Exception {
        return getReportNames().getDataSetNames();
    }

    /**
    * Returns a collection of the DataSourceNames
    */
    public Object[] getDataSourceNames() throws Exception {
        return getReportNames().getDataSourceNames();
    }

    public XmlNode dataSourceName(String dsn) throws Exception {
        return getReportNames().dataSourceName(dsn);
    }

    /**
    * Returns a collection of the Groupings
    */
    public Object[] getGroupingNames() throws Exception {
        return getReportNames().getGroupingNames();
    }

    public String[] getFields(String dataSetName, boolean asExpression) throws Exception {
        return getReportNames().getFields(dataSetName,asExpression);
    }

    public String[] getReportParameters(boolean asExpression) throws Exception {
        return getReportNames().getReportParameters(asExpression);
    }

    public PointF selectionPosition(XmlNode xNode) throws Exception {
        RectangleF r = this.getReportItemRect(xNode);
        return new PointF(r.X, r.Y);
    }

    public SizeF selectionSize(XmlNode xNode) throws Exception {
        SizeF rs = new SizeF(float.MinValue, float.MinValue);
        if (this.inTable(xNode))
        {
            XmlNode tcol = this.getTableColumn(xNode);
            XmlNode tcell = this.getTableCell(xNode);
            if (tcol != null && tcell != null)
            {
                int colSpan = Convert.ToInt32(getElementValue(tcell,"ColSpan","1"));
                float width = 0;
                while (colSpan > 0 && tcol != null)
                {
                    XmlNode w = this.getNamedChildNode(tcol,"Width");
                    if (w != null)
                        width += GetSize(w.InnerText);
                     
                    colSpan--;
                    tcol = tcol.NextSibling;
                }
                if (width > 0)
                    rs.Width = width;
                 
            }
             
            XmlNode tr = this.getTableRow(xNode);
            if (tr != null)
            {
                XmlNode h = this.getNamedChildNode(tr,"Height");
                if (h != null)
                    rs.Height = GetSize(h.InnerText);
                 
            }
             
        }
        else
        {
            RectangleF r = this.getReportItemRect(xNode);
            rs.Width = r.Width;
            rs.Height = r.Height;
        } 
        // we want both values or neither
        if (rs.Width == float.MinValue || rs.Height == float.MinValue)
            rs.Width = rs.Height = float.MinValue;
         
        return rs;
    }

    /**
    * Adds the node to the selection unless the node is already there in which case it removes it
    * 
    *  @param node
    */
    public void addRemoveSelection(XmlNode node) throws Exception {
        if (_SelectedReportItems.IndexOf(node) >= 0)
            _SelectedReportItems.Remove(node);
        else
            // remove from list if already in list
            _SelectedReportItems.Add(node); 
        // add to list otherwise
        this.Invalidate();
    }

    public void addSelection(XmlNode node) throws Exception {
        if (_SelectedReportItems.IndexOf(node) < 0)
        {
            _SelectedReportItems.Add(node);
        }
         
    }

    // add to list otherwise
    public void clearSelected() throws Exception {
        if (_SelectedReportItems.Count > 0)
        {
            _SelectedReportItems.Clear();
            this.Invalidate();
        }
         
    }

    public void deleteSelected() throws Exception {
        if (_SelectedReportItems.Count <= 0)
            return ;
         
        for (Object __dummyForeachVar6 : _SelectedReportItems)
        {
            XmlNode n = (XmlNode)__dummyForeachVar6;
            deleteReportItem(n);
        }
        _SelectedReportItems.Clear();
        this.Invalidate();
    }

    public boolean isNodeSelected(XmlNode node) throws Exception {
        boolean bSelected = false;
        for (Object __dummyForeachVar7 : this._SelectedReportItems)
        {
            XmlNode lNode = (XmlNode)__dummyForeachVar7;
            if (lNode == node)
            {
                bSelected = true;
                break;
            }
             
        }
        return bSelected;
    }

    public void removeSelection(XmlNode node) throws Exception {
        _SelectedReportItems.Remove(node);
        this.Invalidate();
    }

    public boolean selectNext(boolean bReverse) throws Exception {
        XmlNode sNode = new XmlNode();
        if (_SelectedReportItems.Count > 0)
            sNode = _SelectedReportItems[0];
        else
            sNode = null; 
        XmlNode nNode = bReverse ? getReportNames().findPrior(sNode) : getReportNames().findNext(sNode);
        if (nNode == null)
            return false;
         
        this.clearSelected();
        this.addSelection(nNode);
        return true;
    }

    static public int countChildren(XmlNode node, String... names) throws Exception {
        return CountChildren(node, names, 0);
    }

    static private int countChildren(XmlNode node, String[] names, int index) throws Exception {
        int count = 0;
        for (Object __dummyForeachVar8 : node.ChildNodes)
        {
            XmlNode c = (XmlNode)__dummyForeachVar8;
            if (c.Name != names[index])
                continue;
             
            if (names.Length - 1 == index)
                count++;
            else
                count += CountChildren(c, names, index + 1); 
        }
        return count;
    }

    static public XmlNode findNextInHierarchy(XmlNode xNode, String... names) throws Exception {
        XmlNode rNode = xNode;
        for (Object __dummyForeachVar10 : names)
        {
            String name = (String)__dummyForeachVar10;
            XmlNode node = null;
            for (Object __dummyForeachVar9 : rNode.ChildNodes)
            {
                XmlNode cNode = (XmlNode)__dummyForeachVar9;
                if (cNode.NodeType == XmlNodeType.Element && StringSupport.equals(cNode.Name, name))
                {
                    node = cNode;
                    break;
                }
                 
            }
            rNode = node;
            if (rNode == null)
                break;
             
        }
        return rNode;
    }

    public boolean getAllowGroupOperationOnSelected() throws Exception {
        if (_SelectedReportItems.Count <= 1)
            return false;
         
        for (Object __dummyForeachVar11 : getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar11;
            if (inMatrix(xNode) || inTable(xNode))
                return false;
             
        }
        return true;
    }

    public int getSelectedCount() throws Exception {
        return _SelectedReportItems.Count;
    }

    public void setSelection(XmlNode node) throws Exception {
        if (_SelectedReportItems.IndexOf(node) >= 0)
            return ;
         
        // already part of selection
        _SelectedReportItems.Clear();
        // clear out all selected
        _SelectedReportItems.Add(node);
        //   and add in the new one
        this.Invalidate();
    }

    public List<XmlNode> getSelectedList() throws Exception {
        return _SelectedReportItems;
    }

    public float getVerticalMax() throws Exception {
        return getSize(bodyNode,"Height") + getSize(phNode,"Height") + getSize(pfNode,"Height") + BANDHEIGHT * 3 + 3 * 10;
    }

    // plus about 3 lines
    public float getHorizontalMax() throws Exception {
        float hm = Math.Max(pWidth, rWidth);
        return Math.Max(hm, RightMost(rDoc.LastChild) + 90);
    }

    // 90: just to give a little extra room on right
    /**
    * Find the Right most (largest x) position of a report item
    * 
    *  @param xNode Should be the "Report" node
    *  @return x + width of rightmost object
    */
    private float rightMost(XmlNode xNode) throws Exception {
        float rm = 0;
        for (Object __dummyForeachVar12 : xNode.ChildNodes)
        {
            // current rightmost position
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar12;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar4 = xNodeLoop.Name;
            if (__dummyScrutVar4.equals("Body") || __dummyScrutVar4.equals("PageHeader") || __dummyScrutVar4.equals("PageFooter"))
            {
                rm = Math.Max(rm, rightMostRI(getNamedChildNode(xNodeLoop,"ReportItems")));
            }
             
        }
        return rm;
    }

    private float rightMostRI(XmlNode xNode) throws Exception {
        if (xNode == null)
            return 0;
         
        float rm = 0;
        for (Object __dummyForeachVar14 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar14;
            RectangleF r = getReportItemRect(xNodeLoop);
            // get the ReportItem rectangle
            Name __dummyScrutVar5 = xNodeLoop.Name;
            if (__dummyScrutVar5.equals("Table"))
            {
                // Table width is really defined by the table columns
                float[] colWidths = new float[]();
                colWidths = getTableColumnWidths(getNamedChildNode(xNodeLoop,"TableColumns"));
                // calc the total width
                float w = 0;
                for (Object __dummyForeachVar13 : colWidths)
                {
                    float cw = (Float)__dummyForeachVar13;
                    w += cw;
                }
                rm = Math.Max(rm, r.Left + w);
            }
            else if (__dummyScrutVar5.equals("Matrix"))
            {
                MatrixView matrix = new MatrixView(this,xNodeLoop);
                rm = Math.Max(rm, r.Left + matrix.getWidth());
            }
            else
            {
                rm = Math.Max(rm, r.Right);
            }  
        }
        return rm;
    }

    /**
    * Delete the matrix that contains the passed node
    * 
    *  @param node 
    *  @return true if table is deleted
    */
    public boolean deleteMatrix(XmlNode node) throws Exception {
        // Get the table
        XmlNode matrix = this.getMatrixFromReportItem(node);
        if (matrix == null)
            return false;
         
        return deleteReportItem(matrix);
    }

    /**
    * Deletes the specified ReportItem node but ensures that the report remains syntactically correct.
    * e.g. TableCells must contain a ReportItems which must contain a ReportItem
    * e.g. The parent ReportsItems node must be deleted if this is the only node.
    * 
    *  @param node 
    *  @return true when deleted; false when node is changed into Textbox with value = ""
    */
    public boolean deleteReportItem(XmlNode node) throws Exception {
        boolean rc = true;
        boolean bRebuildNames = false;
        if (StringSupport.equals(node.Name, "Table") || StringSupport.equals(node.Name, "List") || StringSupport.equals(node.Name, "Matrix") || StringSupport.equals(node.Name, "Rectangle"))
            bRebuildNames = true;
         
        XmlNode reportItemsNode = node.ParentNode;
        if (reportItemsNode == null)
            return false;
         
        // can't delete this; it is already deleted
        XmlNode pReportItems = reportItemsNode.ParentNode;
        if (StringSupport.equals(pReportItems.Name, "TableCell"))
        {
            // Report item is part of a table; just convert it to an Textbox with no text
            rc = false;
            XmlNode styleNode = getNamedChildNode(node,"Style");
            // want to retain style if possible
            if (styleNode != null)
                styleNode = styleNode.CloneNode(true);
             
            reportItemsNode.RemoveChild(node);
            getReportNames().removeName(node);
            XmlElement tbnode = this.createElement(reportItemsNode,"Textbox",null);
            getReportNames().GenerateName(tbnode);
            XmlElement vnode = this.CreateElement(tbnode, "Value", "");
            if (styleNode != null)
                tbnode.AppendChild(styleNode);
             
        }
        else
        {
            reportItemsNode.RemoveChild(node);
            getReportNames().removeName(node);
            if (!reportItemsNode.HasChildNodes)
            {
                // ReportItems now has no nodes and needs to be removed
                pReportItems.RemoveChild(reportItemsNode);
            }
             
        } 
        if (bRebuildNames)
            setReportNames(null);
         
        return rc;
    }

    // this will force a rebuild when next needed
    /**
    * Delete the table that contains the passed node
    * 
    *  @param node 
    *  @return true if table is deleted
    */
    public boolean deleteTable(XmlNode node) throws Exception {
        // Get the table
        XmlNode table = this.getTableFromReportItem(node);
        if (table == null)
            return false;
         
        return deleteReportItem(table);
    }

    /**
    * Draw the report definition
    * 
    *  @param g 
    *  @param hScroll Horizontal scroll position
    *  @param vScroll Vertical scroll position
    *  @param clipRectangle
    */
    public void draw(Graphics ag, float hScroll, float vScroll, System.Drawing.Rectangle clipRectangle) throws Exception {
        g = ag;
        _hScroll = hScroll;
        _vScroll = vScroll;
        g.PageUnit = GraphicsUnit.Point;
        g.ScaleTransform(1, 1);
        _clip = new RectangleF(PointsX(clipRectangle.X) + _hScroll, PointsY(clipRectangle.Y) + _vScroll, PointsX(clipRectangle.Width), PointsY(clipRectangle.Height));
        XmlNode xNode = rDoc.LastChild;
        if (xNode == null || !StringSupport.equals(xNode.Name, "Report"))
        {
            throw new Exception("RDL doesn't contain a report element.");
        }
         
        processReport(xNode);
        // Render the report
        drawMargins();
        float yLoc = 0;
        yLoc += drawReportPrimaryRegions(phNode,LEFTGAP,yLoc,"Page Header \x2191");
        yLoc += drawReportPrimaryRegions(bodyNode,LEFTGAP,yLoc,"Body \x2191");
        yLoc += drawReportPrimaryRegions(pfNode,LEFTGAP,yLoc,"Page Footer \x2191");
    }

    // Process the report
    private void processReport(XmlNode xNode) throws Exception {
        bodyNode = null;
        phNode = null;
        pfNode = null;
        rWidth = pHeight = pWidth = lMargin = rMargin = tMargin = bMargin = 0;
        for (Object __dummyForeachVar15 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar15;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar6 = xNodeLoop.Name;
            if (__dummyScrutVar6.equals("Body"))
            {
                bodyNode = xNodeLoop;
            }
            else if (__dummyScrutVar6.equals("PageHeader"))
            {
                phNode = xNodeLoop;
            }
            else if (__dummyScrutVar6.equals("PageFooter"))
            {
                pfNode = xNodeLoop;
            }
            else if (__dummyScrutVar6.equals("Width"))
            {
                rWidth = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar6.equals("PageHeight"))
            {
                pHeight = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar6.equals("PageWidth"))
            {
                pWidth = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar6.equals("LeftMargin"))
            {
                lMargin = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar6.equals("RightMargin"))
            {
                rMargin = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar6.equals("TopMargin"))
            {
                tMargin = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar6.equals("BottomMargin"))
            {
                bMargin = GetSize(xNodeLoop.InnerText);
            }
                      
        }
        // Set the default sizes (if not specified)
        if (pWidth == 0)
            pWidth = getSize("8.5in");
         
        if (pHeight == 0)
            pHeight = getSize("11in");
         
        if (rWidth == 0)
            rWidth = pWidth;
         
        if (phNode == null)
            phNode = createPrimaryRegion("PageHeader");
         
        if (pfNode == null)
            phNode = createPrimaryRegion("PageFooter");
         
        if (bodyNode == null)
            bodyNode = createPrimaryRegion("Body");
         
        return ;
    }

    private XmlNode createPrimaryRegion(String name) throws Exception {
        // Create a primary region: e.g. Page Header, Body, Page Footer
        XmlNode xNode = rDoc.CreateElement(name);
        // Add in the height element
        XmlNode hNode = rDoc.CreateElement("Height");
        hNode.InnerText = "0pt";
        xNode.AppendChild(hNode);
        // Now link it under the Report element
        XmlNode rNode = rDoc.LastChild;
        rNode.AppendChild(xNode);
        return xNode;
    }

    private float drawReportPrimaryRegions(XmlNode xNode, float xLoc, float yLoc, String title) throws Exception {
        if (xNode == null)
            return yLoc;
         
        XmlNode items = null;
        float height = float.MinValue;
        for (Object __dummyForeachVar16 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar16;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar7 = xNodeLoop.Name;
            if (__dummyScrutVar7.equals("Height"))
            {
                height = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar7.equals("ReportItems"))
            {
                items = xNodeLoop;
            }
              
        }
        if (height == float.MinValue)
        {
            // Shouldn't happen with correctly defined report; so create a Height element for the region
            this.createElement(xNode,"Height","0pt");
            height = 0;
        }
         
        StyleInfo si = new StyleInfo();
        System.String __dummyScrutVar8 = title;
        if (__dummyScrutVar8.equals("Body"))
        {
            si.BackgroundColor = Color.Red;
        }
        else if (__dummyScrutVar8.equals("Page Header"))
        {
            si.BackgroundColor = Color.Blue;
        }
        else if (__dummyScrutVar8.equals("Page Footer"))
        {
            si.BackgroundColor = Color.Green;
        }
           
        RectangleF b = new RectangleF(xLoc, yLoc, PointsX(Width) + _hScroll, height);
        DrawBackground(b, si);
        RectangleF bm = new RectangleF(_hScroll, yLoc + height, PointsX(Width) + _hScroll, BANDHEIGHT);
        si.BackgroundColor = Color.LightGray;
        si.FontFamily = "Arial";
        si.FontSize = 8;
        si.FontWeight = FontWeightEnum.Bold;
        drawString(title,si,bm);
        drawReportItems(items,b);
        return height + BANDHEIGHT;
    }

    // now draw the report items
    private void drawReportItems(XmlNode xNode, RectangleF r) throws Exception {
        if (xNode == null)
            return ;
         
        IEnumerable olist = new IEnumerable();
        if (xNode.ChildNodes.Count > 1)
            olist = drawReportItemsOrdered(xNode);
        else
            // Get list with ordered report items
            olist = xNode.ChildNodes; 
        for (Object __dummyForeachVar17 : olist)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar17;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            RectangleF rir = RectangleF.Empty;
            Name __dummyScrutVar9 = xNodeLoop.Name;
            if (__dummyScrutVar9.equals("Textbox"))
            {
                rir = drawTextbox(xNodeLoop,r);
            }
            else if (__dummyScrutVar9.equals("Table"))
            {
                rir = drawTable(xNodeLoop,r);
            }
            else if (__dummyScrutVar9.equals("Image"))
            {
                rir = drawImage(xNodeLoop,r);
            }
            else if (__dummyScrutVar9.equals("CustomReportItem"))
            {
                rir = drawCustomReportItem(xNodeLoop,r);
            }
            else if (__dummyScrutVar9.equals("Rectangle"))
            {
                rir = drawRectangle(xNodeLoop,r);
            }
            else if (__dummyScrutVar9.equals("List"))
            {
                rir = drawList(xNodeLoop,r);
            }
            else if (__dummyScrutVar9.equals("Matrix"))
            {
                rir = drawMatrix(xNodeLoop,r);
            }
            else if (__dummyScrutVar9.equals("Subreport"))
            {
                rir = drawSubreport(xNodeLoop,r);
            }
            else if (__dummyScrutVar9.equals("Chart"))
            {
                rir = drawChart(xNodeLoop,r);
            }
            else if (__dummyScrutVar9.equals("Line"))
            {
                rir = drawLine(xNodeLoop,r);
            }
                      
            if (!rir.IsEmpty)
            {
                if (this._SelectedReportItems.IndexOf(xNodeLoop) >= 0)
                    drawSelected(xNodeLoop,rir);
                 
            }
             
        }
    }

    private List<XmlNode> drawReportItemsOrdered(XmlNode xNode) throws Exception {
        // build the array
        List<XmlNode> al = new List<XmlNode>(xNode.ChildNodes.Count);
        for (Object __dummyForeachVar18 : xNode.ChildNodes)
        {
            XmlNode n = (XmlNode)__dummyForeachVar18;
            al.Add(n);
        }
        al.Sort(new ReportItemSorter(this));
        return al;
    }

    private RectangleF getReportItemRect(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF rir = getReportItemRect(xNode);
        if (rir.Width == float.MinValue)
            rir.Width = r.Width - rir.Left;
         
        if (rir.Height == float.MinValue)
            rir.Height = r.Height - rir.Top;
         
        rir = new RectangleF(rir.Left + r.Left, rir.Top + r.Top, rir.Width, rir.Height);
        rir.Intersect(r);
        return rir;
    }

    /**
    * Return the rectangle as specified by Left, Top, Height, Width elements
    * 
    *  @param xNode 
    *  @return
    */
    public RectangleF getReportItemRect(XmlNode xNode) throws Exception {
        float t = 0;
        float l = 0;
        float w = float.MinValue;
        float h = float.MinValue;
        for (Object __dummyForeachVar19 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar19;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar10 = xNodeLoop.Name;
            if (__dummyScrutVar10.equals("Top"))
            {
                t = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar10.equals("Left"))
            {
                l = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar10.equals("Height"))
            {
                h = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar10.equals("Width"))
            {
                w = GetSize(xNodeLoop.InnerText);
            }
                
        }
        RectangleF rir = new RectangleF(l, t, w, h);
        return rir;
    }

    private void getLineEnds(XmlNode xNode, RectangleF r, RefSupport<PointF> l1, RefSupport<PointF> l2) throws Exception {
        float x = 0;
        float y = 0;
        float w = 0;
        float h = 0;
        for (Object __dummyForeachVar20 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar20;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar11 = xNodeLoop.Name;
            if (__dummyScrutVar11.equals("Top"))
            {
                y = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar11.equals("Left"))
            {
                x = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar11.equals("Height"))
            {
                h = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar11.equals("Width"))
            {
                w = GetSize(xNodeLoop.InnerText);
            }
                
        }
        l1.setValue(new PointF(r.Left + x, r.Top + y));
        l2.setValue(new PointF(l1.getValue().X + w, l1.getValue().Y + h));
        return ;
    }

    private void setReportItemHeightWidth(XmlNode xNode, float height, float width) throws Exception {
        this.SetElement(xNode, "Height", String.Format(NumberFormatInfo.InvariantInfo, "{0:0.##}pt", height));
        this.SetElement(xNode, "Width", String.Format(NumberFormatInfo.InvariantInfo, "{0:0.##}pt", width));
    }

    private void setReportItemXY(XmlNode xNode, float x, float y) throws Exception {
        this.SetElement(xNode, "Left", String.Format(NumberFormatInfo.InvariantInfo, "{0:0.##}pt", x));
        this.SetElement(xNode, "Top", String.Format(NumberFormatInfo.InvariantInfo, "{0:0.##}pt", y));
    }

    private void removeReportItemLTHW(XmlNode ri) throws Exception {
        XmlNode w = this.getNamedChildNode(ri,"Left");
        if (w != null)
            ri.RemoveChild(w);
         
        w = this.getNamedChildNode(ri,"Top");
        if (w != null)
            ri.RemoveChild(w);
         
        w = this.getNamedChildNode(ri,"Height");
        if (w != null)
            ri.RemoveChild(w);
         
        w = this.getNamedChildNode(ri,"Width");
        if (w != null)
            ri.RemoveChild(w);
         
    }

    private RectangleF drawChart(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF ir = getReportItemRect(xNode,r);
        XmlNode title = this.getNamedChildNode(xNode,"Title");
        StyleInfo csi = getStyleInfo(xNode);
        csi.TextAlign = TextAlignEnum.Left;
        if (title != null)
        {
            drawString("",csi,ir);
            // for the chart background
            String caption = this.getElementValue(title,"Caption","");
            if (StringSupport.equals(caption, ""))
                caption = "Chart";
            else
                caption = "Chart: " + caption; 
            // Blend the styles of the chart and the title;
            StyleInfo tsi = getStyleInfo(title);
            csi.FontFamily = tsi.FontFamily;
            csi.FontSize = tsi.FontSize;
            csi.FontStyle = tsi.FontStyle;
            csi.FontWeight = tsi.FontWeight;
            csi.Color = tsi.Color;
            csi.TextAlign = TextAlignEnum.Left;
            drawString(caption,csi,ir,false);
        }
        else
            DrawString(xNode.Name, csi, ir, false); 
        return ir;
    }

    private RectangleF drawCustomReportItem(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF ir = getReportItemRect(xNode,r);
        if (!ir.IntersectsWith(_clip))
            return ir;
         
        StyleInfo si = getStyleInfo(xNode);
        XmlNode tNode = this.getNamedChildNode(xNode,"Type");
        if (tNode == null)
        {
            // shouldn't really ever happen
            drawString("CustomReportItem requires type.",si,ir);
            return ir;
        }
         
        String type = tNode.InnerText;
        ICustomReportItem cri = null;
        Bitmap bm = null;
        try
        {
            cri = RdlEngineConfig.createCustomReportItem(type);
            int width = (int)PixelsX(ir.Width - (si.PaddingLeft + si.PaddingRight));
            int height = (int)PixelsY(ir.Height - (si.PaddingTop + si.PaddingBottom));
            if (width <= 0)
                width = 1;
             
            if (height <= 0)
                height = 1;
             
            bm = new Bitmap(width, height);
            cri.DrawDesignerImage(bm);
            DrawImageSized(xNode, ImageSizingEnum.Clip, bm, si, ir);
            drawBorder(si,ir);
        }
        catch (Exception __dummyCatchVar1)
        {
            drawString("CustomReportItem type is unknown.",si,ir);
        }
        finally
        {
            if (cri != null)
                cri.Dispose();
             
            if (bm != null)
                bm.Dispose();
             
        }
        return ir;
    }

    private RectangleF drawImage(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF ir = getReportItemRect(xNode,r);
        if (!ir.IntersectsWith(_clip))
            return ir;
         
        StyleInfo si = getStyleInfo(xNode);
        XmlNode sNode = this.getNamedChildNode(xNode,"Source");
        XmlNode vNode = this.getNamedChildNode(xNode,"Value");
        if (sNode == null || vNode == null)
        {
            // shouldn't really ever happen
            drawString("Image with invalid source or value.",si,ir);
            return ir;
        }
         
        InnerText __dummyScrutVar12 = sNode.InnerText;
        if (__dummyScrutVar12.equals("External"))
        {
            if (drawImageExternal(xNode,sNode,vNode,si,ir))
                ir = getReportItemRect(xNode,r);
             
            drawBorder(si,ir);
        }
        else if (__dummyScrutVar12.equals("Embedded"))
        {
            if (drawImageEmbedded(xNode,sNode,vNode,si,ir))
                ir = getReportItemRect(xNode,r);
             
            drawBorder(si,ir);
        }
        else if (__dummyScrutVar12.equals("Database"))
        {
            DrawString(String.Format("Database Image: {0}.", vNode.InnerText), si, ir);
        }
        else
        {
            DrawString(String.Format("Image, invalid source={0}.", sNode.InnerText), si, ir);
        }   
        return ir;
    }

    private boolean drawImageEmbedded(XmlNode iNode, XmlNode sNode, XmlNode vNode, StyleInfo si, RectangleF r) throws Exception {
        // First we need to find the embedded image list
        XmlNode emNode = this.GetNamedChildNode(rDoc.LastChild, "EmbeddedImages");
        if (emNode == null)
        {
            DrawString(String.Format("Image: embedded image {0} requested but there are no embedded images defined.", vNode.InnerText), si, r);
            return false;
        }
         
        // Next find the requested embedded image by name
        XmlNode eiNode = null;
        for (Object __dummyForeachVar21 : emNode.ChildNodes)
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar21;
            if (xNode.NodeType != XmlNodeType.Element || !StringSupport.equals(xNode.Name, "EmbeddedImage"))
                continue;
             
            System.Xml.XmlAttribute na = xNode.Attributes["Name"];
            if (na.Value == vNode.InnerText)
            {
                eiNode = xNode;
                break;
            }
             
        }
        if (eiNode == null)
        {
            DrawString(String.Format("Image: embedded image {0} not found.", vNode.InnerText), si, r);
            return false;
        }
         
        // Get the image data out
        XmlNode id = this.getNamedChildNode(eiNode,"ImageData");
        byte[] ba = Convert.FromBase64String(id.InnerText);
        Stream strm = null;
        System.Drawing.Image im = null;
        boolean bResize = false;
        try
        {
            strm = new MemoryStream(ba);
            im = System.Drawing.Image.FromStream(strm);
            // Draw based on sizing options
            bResize = DrawImageSized(iNode, im, si, r);
        }
        catch (Exception e)
        {
            DrawString(String.Format("Image: {0}", e.Message), si, r);
        }
        finally
        {
            if (strm != null)
                strm.Close();
             
            if (im != null)
                im.Dispose();
             
        }
        return bResize;
    }

    private boolean drawImageExternal(XmlNode iNode, XmlNode sNode, XmlNode vNode, StyleInfo si, RectangleF r) throws Exception {
        Stream strm = null;
        System.Drawing.Image im = null;
        boolean bResize = false;
        try
        {
            if (vNode.InnerText[0] == '=')
            {
                // Image is an expression; can't calculate at design time
                DrawString(String.Format("Image: {0}", vNode.InnerText), si, r);
            }
            else
            {
                // TODO: should probably put this into cached memory: instead of reading all the time
                String fname = vNode.InnerText;
                if (fname.StartsWith("http:") || fname.StartsWith("file:") || fname.StartsWith("https:"))
                {
                    WebRequest wreq = WebRequest.Create(fname);
                    WebResponse wres = wreq.GetResponse();
                    strm = wres.GetResponseStream();
                }
                else
                    strm = new FileStream(fname, FileMode.Open, FileAccess.Read, FileShare.Read); 
                im = System.Drawing.Image.FromStream(strm);
                // Draw based on sizing options
                bResize = DrawImageSized(iNode, im, si, r);
            } 
        }
        catch (Exception e)
        {
            DrawString(String.Format("Image: {0}", e.Message), si, r);
        }
        finally
        {
            if (strm != null)
                strm.Close();
             
            if (im != null)
                im.Dispose();
             
        }
        return bResize;
    }

    ImageSizingEnum getSizing(XmlNode iNode) throws Exception {
        XmlNode szNode = this.getNamedChildNode(iNode,"Sizing");
        ImageSizingEnum ise = szNode == null ? ImageSizingEnum.AutoSize : ImageSizing.GetStyle(szNode.InnerText);
        return ise;
    }

    private boolean drawImageSized(XmlNode iNode, Image im, StyleInfo si, RectangleF r) throws Exception {
        return drawImageSized(iNode,getSizing(iNode),im,si,r);
    }

    private boolean drawImageSized(XmlNode iNode, ImageSizingEnum ise, Image im, StyleInfo si, RectangleF r) throws Exception {
        // calculate new rectangle based on padding and scroll
        RectangleF r2 = new RectangleF(r.Left + si.PaddingLeft - _hScroll, r.Top + si.PaddingTop - _vScroll, r.Width - si.PaddingLeft - si.PaddingRight, r.Height - si.PaddingTop - si.PaddingBottom);
        boolean bResize = false;
        float height = new float(), width = new float();
        // some work variables
        Rectangle ir;
        // int work rectangle
        GraphicsUnit gu = new GraphicsUnit();
        switch(ise)
        {
            case AutoSize: 
                // Note: GDI+ will stretch an image when you only provide
                //  the left/top coordinates.  This seems pretty stupid since
                //  it results in the image being out of focus even though
                //  you don't want the image resized.
                //					g.DrawImage(im, r2.Left, r2.Top);
                // correct the height and width of the image: to match size of image
                width = PointsX(im.getWidth()) + si.PaddingLeft + si.PaddingRight;
                height = PointsY(im.getHeight()) + si.PaddingTop + si.PaddingBottom;
                this.setReportItemHeightWidth(iNode,height,width);
                gu = g.PageUnit;
                g.PageUnit = GraphicsUnit.Pixel;
                ir = new Rectangle(PixelsX(r2.Left), PixelsY(r2.Top), im.getWidth(), im.getHeight());
                g.DrawImage(im, ir);
                g.PageUnit = gu;
                bResize = true;
                break;
            case Clip: 
                Region saveRegion = g.Clip;
                Region clipRegion = new Region(g.Clip.GetRegionData());
                //RectangleF r3 = new RectangleF(PointsX(r2.Left), PointsY(r2.Top), PointsX(r2.Width), PointsY(r2.Height));
                clipRegion.Intersect(r2);
                g.Clip = clipRegion;
                gu = g.PageUnit;
                g.PageUnit = GraphicsUnit.Pixel;
                ir = new Rectangle(PixelsX(r2.Left), PixelsY(r2.Top), im.getWidth(), im.getHeight());
                g.DrawImage(im, ir);
                g.PageUnit = gu;
                g.Clip = saveRegion;
                break;
            case FitProportional: 
                float ratioIm = (float)im.getHeight() / (float)im.getWidth();
                float ratioR = r2.Height / r2.Width;
                height = r2.Height;
                width = r2.Width;
                if (ratioIm > ratioR)
                {
                    // this means the rectangle width must be corrected
                    width = height * (1 / ratioIm);
                }
                else if (ratioIm < ratioR)
                {
                    // this means the ractangle height must be corrected
                    height = width * ratioIm;
                }
                  
                r2 = new RectangleF(r2.X, r2.Y, width, height);
                g.DrawImage(im, r2);
                break;
            case Fit: 
            default: 
                g.DrawImage(im, r2);
                break;
        
        }
        return bResize;
    }

    private RectangleF drawList(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF listR = getReportItemRect(xNode,r);
        StyleInfo si = getStyleInfo(xNode);
        DrawBackground(listR, si);
        drawBorder(si,listR);
        XmlNode items = this.getNamedChildNode(xNode,"ReportItems");
        if (items != null)
            drawReportItems(items,listR);
         
        return listR;
    }

    private RectangleF drawLine(XmlNode xNode, RectangleF r) throws Exception {
        PointF l1 = new PointF();
        PointF l2 = new PointF();
        RefSupport<PointF> refVar___0 = new RefSupport<PointF>();
        RefSupport<PointF> refVar___1 = new RefSupport<PointF>();
        getLineEnds(xNode,r,refVar___0,refVar___1);
        l1 = refVar___0.getValue();
        l2 = refVar___1.getValue();
        StyleInfo si = getStyleInfo(xNode);
        BorderStyleEnum ls = si.BStyleLeft;
        if (!(ls == BorderStyleEnum.Solid || ls == BorderStyleEnum.Dashed || ls == BorderStyleEnum.Dotted))
            ls = BorderStyleEnum.Solid;
         
        DrawLine(si.BColorLeft, ls, si.BWidthLeft, l1.X, l1.Y, l2.X, l2.Y);
        return r;
    }

    private RectangleF drawMatrix(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF mr = getReportItemRect(xNode,r);
        // get the matrix rectangle
        if (mr.IsEmpty)
            return mr;
         
        MatrixView matrix = new MatrixView(this,xNode);
        mr.Height = matrix.getHeight();
        mr.Width = matrix.getWidth();
        float ypos = mr.Top;
        for (int row = 0;row < matrix.getRows();row++)
        {
            float xpos = mr.Left;
            for (int col = 0;col < matrix.getColumns();col++)
            {
                MatrixItem mi = matrix.get___idx(row,col);
                if (mi.getReportItem() != null)
                {
                    RectangleF cr = new RectangleF(xpos, ypos, mi.getWidth(), mi.getHeight());
                    this.drawReportItems(mi.getReportItem(),cr);
                }
                 
                float width = matrix.get___idx(1,col).getWidth();
                xpos += width;
            }
            ypos += matrix.get___idx(row,1).getHeight();
        }
        return mr;
    }

    private RectangleF drawRectangle(XmlNode xNode, RectangleF r) throws Exception {
        StyleInfo si = getStyleInfo(xNode);
        RectangleF ri = getReportItemRect(xNode,r);
        DrawBackground(ri, si);
        drawBorder(si,ri);
        XmlNode items = this.getNamedChildNode(xNode,"ReportItems");
        if (items != null)
            drawReportItems(items,ri);
         
        return ri;
    }

    private void drawSelected(XmlNode xNode, RectangleF r) throws Exception {
        if (StringSupport.equals(xNode.Name, "Line"))
        {
            drawSelectedLine(xNode,r);
            return ;
        }
         
        StyleInfo si = new StyleInfo();
        si.BStyleBottom = si.BStyleLeft = si.BStyleTop = si.BStyleRight = BorderStyleEnum.Solid;
        si.BWidthBottom = si.BWidthLeft = si.BWidthRight = si.BWidthTop = 1;
        si.BColorBottom = si.BColorLeft = si.BColorRight = si.BColorTop = Color.LightGray;
        drawBorder(si,r);
        DrawCircle(Color.Black, BorderStyleEnum.Solid, 1, r.X - RADIUS, r.Y - RADIUS, RADIUS * 2, true);
        // top left
        DrawCircle(Color.Black, BorderStyleEnum.Solid, 1, r.X + r.Width - RADIUS, r.Y - RADIUS, RADIUS * 2, true);
        // top right
        DrawCircle(Color.Black, BorderStyleEnum.Solid, 1, r.X - RADIUS, r.Y + r.Height - RADIUS, RADIUS * 2, true);
        // bottom left
        DrawCircle(Color.Black, BorderStyleEnum.Solid, 1, r.X + r.Width - RADIUS, r.Y + r.Height - RADIUS, RADIUS * 2, true);
    }

    // bottom right
    private void drawSelectedLine(XmlNode xNode, RectangleF r) throws Exception {
        PointF p1 = new PointF();
        PointF p2 = new PointF();
        RefSupport<PointF> refVar___2 = new RefSupport<PointF>();
        RefSupport<PointF> refVar___3 = new RefSupport<PointF>();
        this.getLineEnds(xNode,r,refVar___2,refVar___3);
        p1 = refVar___2.getValue();
        p2 = refVar___3.getValue();
        DrawCircle(Color.Black, BorderStyleEnum.Solid, 1, p1.X - RADIUS, p1.Y - RADIUS, RADIUS * 2, true);
        DrawCircle(Color.Black, BorderStyleEnum.Solid, 1, p2.X + -RADIUS, p2.Y - RADIUS, RADIUS * 2, true);
    }

    private RectangleF drawSubreport(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF tr = getReportItemRect(xNode,r);
        String subreport = this.getElementValue(xNode,"ReportName","<not specified>");
        String title = String.Format("Subreport: {0}", subreport);
        drawString(title,getStyleInfo(xNode),tr,false);
        return tr;
    }

    private RectangleF drawTable(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF tr = getReportItemRect(xNode,r);
        // get the table rectangle
        if (tr.IsEmpty)
            return tr;
         
        // For Table width is really defined by the table columns
        float[] colWidths = new float[]();
        colWidths = getTableColumnWidths(getNamedChildNode(xNode,"TableColumns"));
        // calc the total width
        float w = 0;
        for (Object __dummyForeachVar22 : colWidths)
        {
            float cw = (Float)__dummyForeachVar22;
            w += cw;
        }
        tr.Width = w;
        // For Table height is really defined the sum of the RowHeights
        List<XmlNode> trs = getTableRows(xNode);
        tr.Height = GetTableRowsHeight(trs);
        DrawBackground(tr, getStyleInfo(xNode));
        // Loop thru the TableRows and the columns in each of them to get at the
        //  individual cell
        float yPos = tr.Y;
        for (Object __dummyForeachVar24 : trs)
        {
            XmlNode trow = (XmlNode)__dummyForeachVar24;
            XmlNode tcells = getNamedChildNode(trow,"TableCells");
            float h = GetSize(getNamedChildNode(trow,"Height").InnerText);
            float xPos = tr.X;
            int col = 0;
            for (Object __dummyForeachVar23 : tcells)
            {
                XmlNode tcell = (XmlNode)__dummyForeachVar23;
                if (!StringSupport.equals(tcell.Name, "TableCell"))
                    continue;
                 
                // Calculate width based on cell span
                float width = 0;
                int colSpan = Convert.ToInt32(getElementValue(tcell,"ColSpan","1"));
                for (int i = 0;i < colSpan && col + i < colWidths.Length;i++)
                {
                    width += colWidths[col + i];
                }
                RectangleF cellR = new RectangleF(xPos, yPos, width, h);
                drawReportItems(getNamedChildNode(tcell,"ReportItems"),cellR);
                xPos += width;
                col += colSpan;
            }
            yPos += h;
        }
        return tr;
    }

    private int getTableColumnCount(XmlNode tblNode) throws Exception {
        XmlNode tblCols = this.getNamedChildNode(tblNode,"TableColumns");
        if (tblCols == null)
            return 0;
         
        int cols = 0;
        for (Object __dummyForeachVar25 : tblCols.ChildNodes)
        {
            XmlNode cNode = (XmlNode)__dummyForeachVar25;
            if (StringSupport.equals(cNode.Name, "TableColumn"))
                cols++;
             
        }
        return cols;
    }

    private List<XmlNode> getTableRows(XmlNode xNode) throws Exception {
        List<XmlNode> trs = new List<XmlNode>();
        XmlNode tblGroups = null, header = null, footer = null, details = null;
        for (Object __dummyForeachVar26 : xNode)
        {
            // Find the major groups that have TableRows
            XmlNode cNode = (XmlNode)__dummyForeachVar26;
            if (cNode.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar14 = cNode.Name;
            if (__dummyScrutVar14.equals("Header"))
            {
                header = cNode;
            }
            else if (__dummyScrutVar14.equals("Details"))
            {
                details = cNode;
            }
            else if (__dummyScrutVar14.equals("Footer"))
            {
                footer = cNode;
            }
            else if (__dummyScrutVar14.equals("TableGroups"))
            {
                tblGroups = cNode;
            }
                
        }
        GetTableRowsAdd(getNamedChildNode(header,"TableRows"), trs);
        GetTableGroupsRows(tblGroups, trs, "Header");
        GetTableRowsAdd(getNamedChildNode(details,"TableRows"), trs);
        GetTableGroupsRows(tblGroups, trs, "Footer");
        GetTableRowsAdd(getNamedChildNode(footer,"TableRows"), trs);
        return trs;
    }

    private void getTableGroupsRows(XmlNode xNode, List<XmlNode> trs, String name) throws Exception {
        if (xNode == null)
            return ;
         
        for (Object __dummyForeachVar27 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar27;
            if (xNodeLoop.NodeType == XmlNodeType.Element && StringSupport.equals(xNodeLoop.Name, "TableGroup"))
            {
                XmlNode n = getNamedChildNode(xNodeLoop,name);
                if (n == null)
                    continue;
                 
                n = getNamedChildNode(n,"TableRows");
                if (n == null)
                    continue;
                 
                GetTableRowsAdd(n, trs);
            }
             
        }
    }

    private void getTableRowsAdd(XmlNode xNode, List<XmlNode> trs) throws Exception {
        if (xNode == null)
            return ;
         
        for (Object __dummyForeachVar28 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar28;
            if (xNodeLoop.NodeType == XmlNodeType.Element && StringSupport.equals(xNodeLoop.Name, "TableRow"))
            {
                trs.Add(xNodeLoop);
            }
             
        }
    }

    private float getTableRowsHeight(List<XmlNode> trs) throws Exception {
        float h = 0;
        for (Object __dummyForeachVar29 : trs)
        {
            XmlNode tr = (XmlNode)__dummyForeachVar29;
            XmlNode cNode = getNamedChildNode(tr,"Height");
            if (cNode != null)
                h += GetSize(cNode.InnerText);
             
        }
        return h;
    }

    private float[] getTableColumnWidths(XmlNode xNode) throws Exception {
        if (xNode == null)
            return new float[0];
         
        List<float> cl = new List<float>();
        for (Object __dummyForeachVar30 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar30;
            if (xNodeLoop.NodeType == XmlNodeType.Element && StringSupport.equals(xNodeLoop.Name, "TableColumn"))
            {
                XmlNode cNode = getNamedChildNode(xNodeLoop,"Width");
                if (cNode != null)
                    cl.Add(GetSize(cNode.InnerText));
                 
            }
             
        }
        float[] r = cl.ToArray();
        return r;
    }

    private RectangleF drawTextbox(XmlNode xNode, RectangleF r) throws Exception {
        StyleInfo si = getStyleInfo(xNode);
        if (si.Color == Color.Empty)
            si.Color = Color.Black;
         
        XmlNode v = getNamedChildNode(xNode,"Value");
        String t = v == null ? "" : v.InnerText;
        RectangleF ir = getReportItemRect(xNode,r);
        DrawString(t, si, ir, !t.StartsWith("="));
        return ir;
    }

    private void drawMargins() throws Exception {
        // left margin
        RectangleF m = new RectangleF(0, 0, LEFTGAP, getTotalPageHeight());
        StyleInfo si = new StyleInfo();
        si.BackgroundColor = Color.BlanchedAlmond;
        DrawBackground(m, si);
        // right margin
        m = new RectangleF(pWidth - rMargin, 0, PointsX(Width), getTotalPageHeight());
        DrawBackground(m, si);
    }

    private float getTotalPageHeight() throws Exception {
        return pHeight;
    }

    // eventually we'll need to add in the sizes of the separating bars
    public XmlNode getNamedChildNode(XmlNode xNode, String name) throws Exception {
        if (xNode == null)
            return null;
         
        for (Object __dummyForeachVar31 : xNode.ChildNodes)
        {
            XmlNode cNode = (XmlNode)__dummyForeachVar31;
            if (cNode.NodeType == XmlNodeType.Element && StringSupport.equals(cNode.Name, name))
                return cNode;
             
        }
        return null;
    }

    /**
    * Returns the named child node; if not there it is created
    * 
    *  @param xNode 
    *  @param name 
    *  @return
    */
    public XmlNode getCreateNamedChildNode(XmlNode xNode, String name) throws Exception {
        if (xNode == null)
            return null;
         
        // Must have parent to create
        XmlNode node = getNamedChildNode(xNode,name);
        if (node == null)
            node = createElement(xNode,name,null);
         
        return node;
    }

    /**
    * Returns the named child node; if not there it is created and the default is applied
    * 
    *  @param xNode 
    *  @param name 
    *  @param def 
    *  @return
    */
    public XmlNode getCreateNamedChildNode(XmlNode xNode, String name, String def) throws Exception {
        if (xNode == null)
            return null;
         
        // Must have parent to create
        XmlNode node = getNamedChildNode(xNode,name);
        if (node == null)
        {
            node = createElement(xNode,name,null);
            if (def != null)
                node.InnerText = def;
             
        }
         
        return node;
    }

    public StyleInfo getStyleInfo(XmlNode xNode) throws Exception {
        StyleInfo si = new StyleInfo();
        XmlNode sNode = this.getNamedChildNode(xNode,"Style");
        if (sNode == null)
            return si;
         
        for (Object __dummyForeachVar32 : sNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar32;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar15 = xNodeLoop.Name;
            if (__dummyScrutVar15.equals("BorderColor"))
            {
                getStyleInfoBorderColor(xNodeLoop,si);
            }
            else if (__dummyScrutVar15.equals("BorderStyle"))
            {
                getStyleInfoBorderStyle(xNodeLoop,si);
            }
            else if (__dummyScrutVar15.equals("BorderWidth"))
            {
                getStyleInfoBorderWidth(xNodeLoop,si);
            }
            else if (__dummyScrutVar15.equals("BackgroundColor"))
            {
                si.BackgroundColor = GetStyleColor(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar15.equals("BackgroundGradientType"))
            {
                si.BackgroundGradientType = StyleInfo.GetBackgroundGradientType(xNodeLoop.InnerText, BackgroundGradientTypeEnum.None);
            }
            else if (__dummyScrutVar15.equals("BackgroundGradientEndColor"))
            {
                si.BackgroundGradientEndColor = GetStyleColor(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar15.equals("BackgroundImage"))
            {
                getStyleInfoBackgroundImage(xNodeLoop,si);
            }
            else if (__dummyScrutVar15.equals("FontStyle"))
            {
                si.FontStyle = StyleInfo.GetFontStyle(xNodeLoop.InnerText, FontStyleEnum.Normal);
            }
            else if (__dummyScrutVar15.equals("FontFamily"))
            {
                si.FontFamily = xNodeLoop.InnerText[0] == '=' ? "Arial" : xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar15.equals("FontSize"))
            {
                si.FontSize = xNodeLoop.InnerText[0] == '=' ? 10 : GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar15.equals("FontWeight"))
            {
                si.FontWeight = StyleInfo.GetFontWeight(xNodeLoop.InnerText, FontWeightEnum.Normal);
            }
            else if (__dummyScrutVar15.equals("Format"))
            {
            }
            else if (__dummyScrutVar15.equals("TextDecoration"))
            {
                si.TextDecoration = StyleInfo.GetTextDecoration(xNodeLoop.InnerText, TextDecorationEnum.None);
            }
            else if (__dummyScrutVar15.equals("TextAlign"))
            {
                si.TextAlign = StyleInfo.GetTextAlign(xNodeLoop.InnerText, TextAlignEnum.Left);
            }
            else if (__dummyScrutVar15.equals("VerticalAlign"))
            {
                si.VerticalAlign = StyleInfo.GetVerticalAlign(xNodeLoop.InnerText, VerticalAlignEnum.Middle);
            }
            else if (__dummyScrutVar15.equals("Color"))
            {
                si.Color = GetStyleColor(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar15.equals("PaddingLeft"))
            {
                si.PaddingLeft = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar15.equals("PaddingRight"))
            {
                si.PaddingRight = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar15.equals("PaddingTop"))
            {
                si.PaddingTop = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar15.equals("PaddingBottom"))
            {
                si.PaddingBottom = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar15.equals("LineHeight"))
            {
                si.LineHeight = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar15.equals("Direction"))
            {
                si.Direction = StyleInfo.GetDirection(xNodeLoop.InnerText, DirectionEnum.LTR);
            }
            else if (__dummyScrutVar15.equals("WritingMode"))
            {
                si.WritingMode = StyleInfo.GetWritingMode(xNodeLoop.InnerText, WritingModeEnum.lr_tb);
            }
            else if (__dummyScrutVar15.equals("Language"))
            {
                si.Language = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar15.equals("UnicodeBiDi"))
            {
                si.UnicodeBiDirectional = StyleInfo.GetUnicodeBiDirectional(xNodeLoop.InnerText, UnicodeBiDirectionalEnum.Normal);
            }
            else if (__dummyScrutVar15.equals("Calendar"))
            {
                si.Calendar = StyleInfo.GetCalendar(xNodeLoop.InnerText, CalendarEnum.Gregorian);
            }
            else if (__dummyScrutVar15.equals("NumeralLanguage"))
            {
            }
            else if (__dummyScrutVar15.equals("NumeralVariant"))
            {
            }
                                        
        }
        return si;
    }

    private Color getStyleColor(String c) throws Exception {
        if (c == null || c.Length < 1 || c[0] == '=')
            return Color.Empty;
         
        Color clr = new Color();
        try
        {
            clr = ColorTranslator.FromHtml(c);
        }
        catch (Exception __dummyCatchVar2)
        {
            clr = Color.White;
        }

        return clr;
    }

    private void getStyleInfoBorderColor(XmlNode xNode, StyleInfo si) throws Exception {
        Color dColor = Color.Black;
        si.BColorLeft = si.BColorRight = si.BColorTop = si.BColorBottom = Color.Empty;
        for (Object __dummyForeachVar33 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar33;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar16 = xNodeLoop.Name;
            if (__dummyScrutVar16.equals("Default"))
            {
                dColor = GetStyleColor(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar16.equals("Left"))
            {
                si.BColorLeft = GetStyleColor(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar16.equals("Right"))
            {
                si.BColorRight = GetStyleColor(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar16.equals("Top"))
            {
                si.BColorTop = GetStyleColor(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar16.equals("Bottom"))
            {
                si.BColorBottom = GetStyleColor(xNodeLoop.InnerText);
            }
                 
        }
        if (si.BColorLeft == Color.Empty)
            si.BColorLeft = dColor;
         
        if (si.BColorRight == Color.Empty)
            si.BColorRight = dColor;
         
        if (si.BColorTop == Color.Empty)
            si.BColorTop = dColor;
         
        if (si.BColorBottom == Color.Empty)
            si.BColorBottom = dColor;
         
    }

    private void getStyleInfoBorderStyle(XmlNode xNode, StyleInfo si) throws Exception {
        BorderStyleEnum def = BorderStyleEnum.None;
        String l = null;
        String r = null;
        String t = null;
        String b = null;
        for (Object __dummyForeachVar34 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar34;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar17 = xNodeLoop.Name;
            if (__dummyScrutVar17.equals("Default"))
            {
                def = GetBorderStyle(xNodeLoop.InnerText, BorderStyleEnum.None);
            }
            else if (__dummyScrutVar17.equals("Left"))
            {
                l = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar17.equals("Right"))
            {
                r = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar17.equals("Top"))
            {
                t = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar17.equals("Bottom"))
            {
                b = xNodeLoop.InnerText;
            }
                 
        }
        si.BStyleLeft = l == null ? def : getBorderStyle(l,def);
        si.BStyleRight = r == null ? def : getBorderStyle(r,def);
        si.BStyleBottom = b == null ? def : getBorderStyle(b,def);
        si.BStyleTop = t == null ? def : getBorderStyle(t,def);
    }

    // return the BorderStyleEnum given a particular string value
    BorderStyleEnum getBorderStyle(String v, BorderStyleEnum def) throws Exception {
        BorderStyleEnum bs = BorderStyleEnum.None;
        System.String __dummyScrutVar18 = v;
        if (__dummyScrutVar18.equals("None"))
        {
            bs = BorderStyleEnum.None;
        }
        else if (__dummyScrutVar18.equals("Dotted"))
        {
            bs = BorderStyleEnum.Dotted;
        }
        else if (__dummyScrutVar18.equals("Dashed"))
        {
            bs = BorderStyleEnum.Dashed;
        }
        else if (__dummyScrutVar18.equals("Solid"))
        {
            bs = BorderStyleEnum.Solid;
        }
        else if (__dummyScrutVar18.equals("Double"))
        {
            bs = BorderStyleEnum.Double;
        }
        else if (__dummyScrutVar18.equals("Groove"))
        {
            bs = BorderStyleEnum.Groove;
        }
        else if (__dummyScrutVar18.equals("Ridge"))
        {
            bs = BorderStyleEnum.Ridge;
        }
        else if (__dummyScrutVar18.equals("Inset"))
        {
            bs = BorderStyleEnum.Inset;
        }
        else if (__dummyScrutVar18.equals("WindowInset"))
        {
            bs = BorderStyleEnum.WindowInset;
        }
        else if (__dummyScrutVar18.equals("Outset"))
        {
            bs = BorderStyleEnum.Outset;
        }
        else
        {
            bs = def;
        }          
        return bs;
    }

    private void getStyleInfoBorderWidth(XmlNode xNode, StyleInfo si) throws Exception {
        String l = null;
        String r = null;
        String t = null;
        String b = null;
        float def = getSize("1pt");
        for (Object __dummyForeachVar35 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar35;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar19 = xNodeLoop.Name;
            if (__dummyScrutVar19.equals("Default"))
            {
                def = GetSize(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar19.equals("Left"))
            {
                l = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar19.equals("Right"))
            {
                r = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar19.equals("Top"))
            {
                t = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar19.equals("Bottom"))
            {
                b = xNodeLoop.InnerText;
            }
                 
        }
        si.BWidthTop = t == null ? def : getSize(t);
        si.BWidthBottom = b == null ? def : getSize(b);
        si.BWidthLeft = l == null ? def : getSize(l);
        si.BWidthRight = r == null ? def : getSize(r);
    }

    private void getStyleInfoBackgroundImage(XmlNode xNode, StyleInfo si) throws Exception {
        for (Object __dummyForeachVar36 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar36;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar20 = xNodeLoop.Name;
            // TODO
            if (__dummyScrutVar20.equals("Source"))
            {
            }
            else if (__dummyScrutVar20.equals("Value"))
            {
            }
            else if (__dummyScrutVar20.equals("MIMEType"))
            {
            }
            else if (__dummyScrutVar20.equals("BackgroundRepeat"))
            {
            }
                
        }
    }

    public float getSize(XmlNode pNode, String name) throws Exception {
        XmlNode xNode = this.getNamedChildNode(pNode,name);
        if (xNode == null)
            return 0;
         
        return GetSize(xNode.InnerText);
    }

    static public float getSize(String t) throws Exception {
        if (t == null || t.Length == 0 || t[0] == '=')
            return 0;
         
        // Size is specified in CSS Length Units
        // format is <decimal number nnn.nnn><optional space><unit>
        // in -> inches (1 inch = 2.54 cm)
        // cm -> centimeters (.01 meters)
        // mm -> millimeters (.001 meters)
        // pt -> points (1 point = 1/72 inches)
        // pc -> Picas (1 pica = 12 points)
        t = t.Trim();
        int space = t.LastIndexOf(' ');
        String n = "";
        // number string
        String u = "in";
        // unit string
        double d = new double();
        try
        {
            // initial number
            // Convert.ToDecimal can be very picky
            if (space != -1)
            {
                // any spaces
                n = t.Substring(0, space).Trim();
                // number string
                u = t.Substring(space).Trim();
            }
            else // unit string
            if (t.Length >= 3)
            {
                n = t.Substring(0, t.Length - 2);
                u = t.Substring(t.Length - 2);
            }
            else
            {
                return 0;
            }  
            // Illegal unit
            d = Convert.ToDecimal(n, NumberFormatInfo.InvariantInfo);
        }
        catch (Exception __dummyCatchVar3)
        {
            return 0;
        }

        int size = new int();
        System.String __dummyScrutVar21 = u;
        // convert to millimeters
        if (__dummyScrutVar21.equals("in"))
        {
            size = (int)(d * 2540m);
        }
        else if (__dummyScrutVar21.equals("cm"))
        {
            size = (int)(d * 1000m);
        }
        else if (__dummyScrutVar21.equals("mm"))
        {
            size = (int)(d * 100m);
        }
        else if (__dummyScrutVar21.equals("pt"))
        {
            return Convert.ToSingle(d);
        }
        else if (__dummyScrutVar21.equals("pc"))
        {
            size = (int)(d * (2540m / POINTSIZEM * 12m));
        }
        else
        {
            // Illegal unit
            size = (int)(d * 2540m);
        }     
        return (float)((double)size / 2540.0 * POINTSIZED);
    }

    // and return as points
    private void drawBackground(System.Drawing.RectangleF rect, StyleInfo si) throws Exception {
        if (!rect.IntersectsWith(_clip))
            return ;
         
        RectangleF dr = new RectangleF(rect.X - _hScroll, rect.Y - _vScroll, rect.Width, rect.Height);
        LinearGradientBrush linGrBrush = null;
        SolidBrush sb = null;
        try
        {
            if (si.BackgroundGradientType != BackgroundGradientTypeEnum.None && !si.BackgroundGradientEndColor.IsEmpty && !si.BackgroundColor.IsEmpty)
            {
                Color c = si.BackgroundColor;
                Color ec = si.BackgroundGradientEndColor;
                switch(si.BackgroundGradientType)
                {
                    case LeftRight: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Horizontal);
                        break;
                    case TopBottom: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Vertical);
                        break;
                    case Center: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Horizontal);
                        break;
                    case DiagonalLeft: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.ForwardDiagonal);
                        break;
                    case DiagonalRight: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.BackwardDiagonal);
                        break;
                    case HorizontalCenter: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Horizontal);
                        break;
                    case VerticalCenter: 
                        linGrBrush = new LinearGradientBrush(rect, c, ec, LinearGradientMode.Vertical);
                        break;
                    default: 
                        break;
                
                }
            }
             
            if (linGrBrush != null)
            {
                g.FillRectangle(linGrBrush, dr);
                linGrBrush.Dispose();
            }
            else if (!si.BackgroundColor.IsEmpty)
            {
                sb = new SolidBrush(si.BackgroundColor);
                g.FillRectangle(sb, dr);
                sb.Dispose();
            }
              
        }
        finally
        {
            if (linGrBrush != null)
                linGrBrush.Dispose();
             
            if (sb != null)
                sb.Dispose();
             
        }
        return ;
    }

    private void drawBorder(StyleInfo si, RectangleF r) throws Exception {
        if (!r.IntersectsWith(_clip))
            return ;
         
        if (r.Height <= 0 || r.Width <= 0)
            return ;
         
        // no bounding box to use
        DrawLine(si.BColorTop, si.BStyleTop, si.BWidthTop, r.X, r.Y, r.Right, r.Y);
        DrawLine(si.BColorRight, si.BStyleRight, si.BWidthRight, r.Right, r.Y, r.Right, r.Bottom);
        DrawLine(si.BColorLeft, si.BStyleLeft, si.BWidthLeft, r.X, r.Y, r.X, r.Bottom);
        DrawLine(si.BColorBottom, si.BStyleBottom, si.BWidthBottom, r.X, r.Bottom, r.Right, r.Bottom);
        return ;
    }

    private void drawCircle(Color c, BorderStyleEnum bs, float penWidth, float x, float y, float d, boolean bFill) throws Exception {
        if (bs == BorderStyleEnum.None || c.IsEmpty || d <= 0)
            return ;
         
        // nothing to draw
        // adjust coordinates for scrolling
        x -= _hScroll;
        y -= _vScroll;
        Pen p = null;
        try
        {
            p = new Pen(c, penWidth);
            switch(bs)
            {
                case Dashed: 
                    p.DashStyle = DashStyle.Dash;
                    break;
                case Dotted: 
                    p.DashStyle = DashStyle.Dot;
                    break;
                case Double: 
                case Groove: 
                case Inset: 
                case Solid: 
                case Outset: 
                case Ridge: 
                case WindowInset: 
                default: 
                    p.DashStyle = DashStyle.Solid;
                    break;
            
            }
            if (bFill)
                g.FillEllipse(Brushes.Black, x, y, d, d);
            else
                g.DrawEllipse(p, x, y, d, d); 
        }
        finally
        {
            if (p != null)
                p.Dispose();
             
        }
    }

    private void drawLine(Color c, BorderStyleEnum bs, float w, float x, float y, float x2, float y2) throws Exception {
        Color lc = c;
        if (this.getShowReportItemOutline())
        {
            // force an outline
            lc = (bs == BorderStyleEnum.None || c.IsEmpty) ? Color.LightGray : c;
            if (w <= 0)
                w = 1;
             
        }
        else if (bs == BorderStyleEnum.None || c.IsEmpty || w <= 0)
        {
            return ;
        }
          
        // nothing to draw
        // adjust coordinates for scrolling
        x -= _hScroll;
        y -= _vScroll;
        x2 -= _hScroll;
        y2 -= _vScroll;
        Pen p = null;
        try
        {
            p = new Pen(lc, w);
            switch(bs)
            {
                case Dashed: 
                    p.DashStyle = DashStyle.Dash;
                    break;
                case Dotted: 
                    p.DashStyle = DashStyle.Dot;
                    break;
                case Double: 
                case Groove: 
                case Inset: 
                case Solid: 
                case Outset: 
                case Ridge: 
                case WindowInset: 
                default: 
                    p.DashStyle = DashStyle.Solid;
                    break;
            
            }
            g.DrawLine(p, x, y, x2, y2);
        }
        finally
        {
            if (p != null)
                p.Dispose();
             
        }
    }

    private void drawString(String text, StyleInfo si, RectangleF r) throws Exception {
        drawString(text,si,r,true);
    }

    private void drawString(String text, StyleInfo si, RectangleF r, boolean bWrap) throws Exception {
        if (!r.IntersectsWith(_clip))
            return ;
         
        Font drawFont = null;
        StringFormat drawFormat = null;
        Brush drawBrush = null;
        try
        {
            // STYLE
            System.Drawing.FontStyle fs = 0;
            if (si.FontStyle == FontStyleEnum.Italic)
                fs |= System.Drawing.FontStyle.Italic;
             
            switch(si.TextDecoration)
            {
                case Underline: 
                    fs |= System.Drawing.FontStyle.Underline;
                    break;
                case LineThrough: 
                    fs |= System.Drawing.FontStyle.Strikeout;
                    break;
                case Overline: 
                case None: 
                    break;
            
            }
            // WEIGHT
            switch(si.FontWeight)
            {
                case Bold: 
                case Bolder: 
                case W500: 
                case W600: 
                case W700: 
                case W800: 
                case W900: 
                    fs |= System.Drawing.FontStyle.Bold;
                    break;
                default: 
                    break;
            
            }
            if (si.FontSize <= 0)
                // can't have zero length font; force to default
                si.FontSize = 10;
             
            drawFont = new Font(si.FontFamily, si.FontSize, fs);
            // si.FontSize already in points
            // ALIGNMENT
            drawFormat = new StringFormat();
            if (!bWrap)
                drawFormat.FormatFlags |= StringFormatFlags.NoWrap;
             
            switch(si.TextAlign)
            {
                case Right: 
                    drawFormat.Alignment = StringAlignment.Far;
                    break;
                case Center: 
                    drawFormat.Alignment = StringAlignment.Center;
                    break;
                case Left: 
                default: 
                    drawFormat.Alignment = StringAlignment.Near;
                    break;
            
            }
            if (si.WritingMode == WritingModeEnum.tb_rl)
            {
                drawFormat.FormatFlags |= StringFormatFlags.DirectionRightToLeft;
                drawFormat.FormatFlags |= StringFormatFlags.DirectionVertical;
            }
             
            switch(si.VerticalAlign)
            {
                case Bottom: 
                    drawFormat.LineAlignment = StringAlignment.Far;
                    break;
                case Middle: 
                    drawFormat.LineAlignment = StringAlignment.Center;
                    break;
                case Top: 
                default: 
                    drawFormat.LineAlignment = StringAlignment.Near;
                    break;
            
            }
            // draw the background
            DrawBackground(r, si);
            // adjust drawing rectangle based on padding and adjusted for scrolling
            RectangleF r2 = new RectangleF(r.Left + si.PaddingLeft - _hScroll, r.Top + si.PaddingTop - _vScroll, r.Width - si.PaddingLeft - si.PaddingRight, r.Height - si.PaddingTop - si.PaddingBottom);
            drawBrush = new SolidBrush(si.Color);
            g.DrawString(text, drawFont, drawBrush, r2, drawFormat);
        }
        finally
        {
            if (drawFont != null)
                drawFont.Dispose();
             
            if (drawFormat != null)
                drawFont.Dispose();
             
            if (drawBrush != null)
                drawBrush.Dispose();
             
        }
        drawBorder(si,r);
    }

    // Draw the border if needed
    public void pasteImage(XmlNode parent, System.Drawing.Bitmap img, PointF p) throws Exception {
        String t = String.Format(NumberFormatInfo.InvariantInfo, "<ReportItems><Image><Source>Embedded</Source><Height>{0:0.00}in</Height><Width>{1:0.00}in</Width><Sizing>FitProportional</Sizing></Image></ReportItems>", PointsY(img.Height) / POINTSIZED, PointsX(img.Width) / POINTSIZED);
        pasteReportItems(parent,t,p);
        if (_SelectedReportItems.Count != 1)
            return ;
         
        // Paste must have failed
        XmlNode iNode = this._SelectedReportItems[0];
        // Get the just pasted image
        // Get the name; we'll use that as the embedded image name as well
        XmlAttribute xAttr = iNode.Attributes["Name"];
        if (xAttr == null)
            return ;
         
        String name = xAttr.Value;
        this.setElement(iNode,"Value",name);
        XmlNode rNode = this.getReportNode();
        XmlNode eimages = this.getCreateNamedChildNode(rNode,"EmbeddedImages");
        XmlNode image = this.createElement(eimages,"EmbeddedImage",null);
        this.setElementAttribute(image,"Name",name);
        this.createElement(image,"MIMEType","image/png");
        // always store in PNG format
        String imagedata = null;
        try
        {
            MemoryStream ostrm = new MemoryStream();
            ImageFormat imf = ImageFormat.Png;
            img.Save(ostrm, imf);
            byte[] ba = ostrm.ToArray();
            ostrm.Close();
            imagedata = Convert.ToBase64String(ba);
        }
        catch (Exception __dummyCatchVar4)
        {
            imagedata = null;
        }
        finally
        {
            if (img != null)
                img.Dispose();
             
        }
        if (imagedata != null)
            this.createElement(image,"ImageData",imagedata);
         
        return ;
    }

    /**
    * Normally used to replace the contents of a cell in a table
    * 
    *  @param hl 
    *  @param rItems
    */
    public void replaceReportItems(HitLocation hl, String rItems) throws Exception {
        if (hl.HitNode == null)
            return ;
         
        XmlNode repItems = hl.HitNode.ParentNode;
        if (!StringSupport.equals(repItems.Name, "ReportItems"))
            return ;
         
        XmlNode p = repItems.ParentNode;
        p.RemoveChild(repItems);
        pasteReportItems(p,rItems,hl.HitRelative);
    }

    public void pasteReportItems(XmlNode parent, String rItems, PointF p) throws Exception {
        boolean bTableCell = StringSupport.equals(parent.Name, "TableCell");
        // inside of table no size should be specified
        XmlDocumentFragment fDoc = rDoc.CreateDocumentFragment();
        fDoc.InnerXml = rItems;
        XmlNode priNode = GetNamedChildNode(fDoc, "ReportItems");
        if (priNode == null)
            return ;
         
        pasteValidate(parent,priNode);
        // will throw exception if problem
        // Get the ReportItems node we need to paste into
        XmlNode riNode = this.getCreateNamedChildNode(parent,"ReportItems");
        if (priNode.ChildNodes.Count > 1 && bTableCell)
        {
            // if we're trying to paste multiple items in a
            //   cell we need to put a rectangle over it
            XmlNode rectNode = this.createElement(riNode,"Rectangle",null);
            getReportNames().generateName(rectNode);
            // generate a new name
            riNode = this.createElement(rectNode,"ReportItems",null);
        }
         
        // We need to normalize the positions in the reportitems. Simple as 1, 2, 3
        // 1) Find the left most object and the top most object
        // 2) Adjust all objects positions
        // 3) Move the nodes into the proper ReportItems collection
        // 1) Find the left most and top most objects
        float left = float.MaxValue;
        float top = float.MaxValue;
        for (Object __dummyForeachVar37 : priNode)
        {
            XmlNode ri = (XmlNode)__dummyForeachVar37;
            RectangleF rf = this.getReportItemRect(ri);
            if (left > rf.Left)
                left = rf.Left;
             
            if (top > rf.Top)
                top = rf.Top;
             
        }
        for (Object __dummyForeachVar38 : priNode)
        {
            // 2) Adjust all objects positions
            XmlNode ri = (XmlNode)__dummyForeachVar38;
            if (bTableCell)
            {
                removeReportItemLTHW(ri);
            }
            else
            {
                RectangleF rf = this.getReportItemRect(ri);
                SetReportItemXY(ri, rf.Left - left + p.X, rf.Top - top + p.Y);
            } 
        }
        // 3) Move the nodes into the proper ReportItems collection
        // Loop thru and put all the report items into the main document
        // This loop is a little strange because when a node is appended to
        //   the main document it is removed from the fragment.   Thus you
        //   must continually grab the first child until all the children have
        //   been removed.
        this.clearSelected();
        for (XmlNode ri = priNode.FirstChild;ri != null;ri = priNode.FirstChild)
        {
            // the new nodes end up selected
            riNode.AppendChild(ri);
            pasteNewNames(ri);
            this.addSelection(ri);
        }
    }

    void pasteValidate(XmlNode parent, XmlNode pitems) throws Exception {
        // check restriction on placing DataRegion or Subreport in pageheader or footer
        if (!this.inPageHeaderOrFooter(parent))
            return ;
         
        pasteValidateRecurse(pitems);
    }

    void pasteValidateRecurse(XmlNode pitems) throws Exception {
        if (!pitems.HasChildNodes)
            return ;
         
        for (Object __dummyForeachVar39 : pitems.ChildNodes)
        {
            XmlNode item = (XmlNode)__dummyForeachVar39;
            if (this.isDataRegion(item))
                throw new Exception("You can't paste a DataRegion into a page header or footer");
             
            if (StringSupport.equals(item.Name, "Subreport"))
                throw new Exception("You can't paste a Subreport into a page header or footer");
             
            XmlNode ritems = this.getNamedChildNode(item,"ReportItems");
            if (ritems != null)
                pasteValidateRecurse(ritems);
             
        }
        return ;
    }

    /**
    * Normally used to replace the contents of a cell in a table with a Table, Matrix or Chart
    * 
    *  @param hl 
    *  @param rItems
    */
    public XmlNode replaceTableMatrixOrChart(HitLocation hl, String rTable) throws Exception {
        if (hl.HitNode == null)
            return null;
         
        XmlNode repItems = hl.HitNode.ParentNode;
        if (!StringSupport.equals(repItems.Name, "ReportItems"))
            return null;
         
        XmlNode p = repItems.ParentNode;
        p.RemoveChild(repItems);
        return pasteTableMatrixOrChart(p,rTable,hl.HitRelative);
    }

    public XmlNode pasteTableMatrixOrChart(XmlNode parent, String sTable, PointF p) throws Exception {
        XmlDocumentFragment fDoc = rDoc.CreateDocumentFragment();
        try
        {
            fDoc.InnerXml = sTable;
        }
        catch (Exception e)
        {
            MessageBox.Show(e.Message, "XML is Invalid");
            return null;
        }

        String type = new String();
        if (StringSupport.equals(sTable.Substring(0, 6), "<Table"))
            type = "Table";
        else if (StringSupport.equals(sTable.Substring(0, 7), "<Matrix"))
            type = "Matrix";
        else if (StringSupport.equals(sTable.Substring(0, 6), "<Chart"))
            type = "Chart";
        else
            return null;   
        XmlNode riNode = this.getNamedChildNode(parent,"ReportItems");
        if (riNode == null)
            // Node has to have ReportItems in order to do paste
            riNode = this.createElement(parent,"ReportItems",null);
         
        XmlNode tNode = GetNamedChildNode(fDoc, type);
        if (tNode == null)
            return null;
         
        boolean bTableCell = StringSupport.equals(parent.Name, "TableCell");
        // inside of table no size should be specified
        if (bTableCell)
            removeReportItemLTHW(tNode);
        else
            SetReportItemXY(tNode, p.X, p.Y); 
        riNode.AppendChild(tNode);
        // move table into ReportItems collection
        this.clearSelected();
        // the new nodes end up selected
        // Need to go thru entire node regenerating all the ReportItem names in the table/matrix
        pasteNewNames(tNode);
        return tNode;
    }

    private void pasteNewNames(XmlNode node) throws Exception {
        if (node == null)
            return ;
         
        Name __dummyScrutVar29 = node.Name;
        if (__dummyScrutVar29.equals("Textbox") || __dummyScrutVar29.equals("Image") || __dummyScrutVar29.equals("Subreport") || __dummyScrutVar29.equals("Line") || __dummyScrutVar29.equals("CustomReportItem"))
        {
            getReportNames().generateName(node);
            return ;
        }
        else // generate a new name
        if (__dummyScrutVar29.equals("Chart") || __dummyScrutVar29.equals("Rectangle") || __dummyScrutVar29.equals("Table") || __dummyScrutVar29.equals("Matrix") || __dummyScrutVar29.equals("List"))
        {
            getReportNames().generateName(node);
        }
        else // generate a new name
        // just to limit some of the dead ends we might hit
        if (__dummyScrutVar29.equals("Style") || __dummyScrutVar29.equals("Filters"))
        {
            return ;
        }
        else if (__dummyScrutVar29.equals("Grouping"))
        {
            // need to assign name to groups too
            getReportNames().generateGroupingName(node);
            return ;
        }
            
        for (Object __dummyForeachVar40 : node.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar40;
            if (xNodeLoop.NodeType == XmlNodeType.Element)
                pasteNewNames(xNodeLoop);
             
        }
        return ;
    }

    /**
    * Every reportitem intersecting with the rectangle is added to the selection
    * 
    *  @param r Rectangle used to test
    *  @param hScroll current horizontal scroll
    *  @param vScroll current vertical scroll
    */
    public void selectInRectangle(Rectangle r, float hScroll, float vScroll) throws Exception {
        if (rDoc == null)
            return ;
         
        _hScroll = hScroll;
        _vScroll = vScroll;
        ProcessReport(rDoc.LastChild);
        float bh = 0, hh = 0, fh = 0;
        _HitRect = new RectangleF(PointsX(r.X) + _hScroll, PointsY(r.Y) + _vScroll, PointsX(r.getWidth()), PointsY(r.getHeight()));
        // If selected count changes then we need to repaint
        int selectedCount = this._SelectedReportItems.Count;
        // Check for hit in the header
        XmlNode hn = getNamedChildNode(phNode,"Height");
        if (hn != null)
        {
            hh = GetSize(hn.InnerText);
            RectangleF rf = new RectangleF(LEFTGAP, 0, PointsX(Width) + _hScroll, hh);
            if (rf.IntersectsWith(_HitRect))
            {
                XmlNode ri = getNamedChildNode(phNode,"ReportItems");
                selectInReportItems(ri,rf);
            }
             
        }
         
        // Check for hit in the body
        XmlNode bn = getNamedChildNode(bodyNode,"Height");
        if (bn != null)
        {
            bh = GetSize(bn.InnerText);
            RectangleF rf = new RectangleF(LEFTGAP, hh + BANDHEIGHT, PointsX(Width) + _hScroll, bh);
            if (rf.IntersectsWith(_HitRect))
            {
                XmlNode ri = getNamedChildNode(bodyNode,"ReportItems");
                selectInReportItems(ri,rf);
            }
             
        }
         
        // Check for hit in the footer
        XmlNode fn = getNamedChildNode(pfNode,"Height");
        if (fn != null)
        {
            fh = GetSize(fn.InnerText);
            RectangleF rf = new RectangleF(LEFTGAP, hh + BANDHEIGHT + bh + BANDHEIGHT, PointsX(Width) + _hScroll, fh);
            if (rf.IntersectsWith(_HitRect))
            {
                XmlNode ri = getNamedChildNode(pfNode,"ReportItems");
                selectInReportItems(ri,rf);
            }
             
        }
         
        if (selectedCount != this._SelectedReportItems.Count)
            this.Invalidate();
         
        return ;
    }

    private void selectInReportItems(XmlNode xNode, RectangleF r) throws Exception {
        if (xNode == null)
            return ;
         
        for (Object __dummyForeachVar41 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar41;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar30 = xNodeLoop.Name;
            if (__dummyScrutVar30.equals("Textbox") || __dummyScrutVar30.equals("Chart") || __dummyScrutVar30.equals("Image") || __dummyScrutVar30.equals("Subreport") || __dummyScrutVar30.equals("CustomReportItem"))
            {
                RectangleF rif = getReportItemRect(xNodeLoop,r);
                if (rif.IntersectsWith(_HitRect))
                    this.addSelection(xNodeLoop);
                 
            }
            else if (__dummyScrutVar30.equals("Line"))
            {
                // for line a simplification is to require endpoints to be in rectangle
                //  TODO more sophisticated line segment crosses rectangle but endpoints not in rect
                PointF p1 = new PointF();
                PointF p2 = new PointF();
                RefSupport<PointF> refVar___4 = new RefSupport<PointF>();
                RefSupport<PointF> refVar___5 = new RefSupport<PointF>();
                this.getLineEnds(xNodeLoop,r,refVar___4,refVar___5);
                p1 = refVar___4.getValue();
                p2 = refVar___5.getValue();
                if (_HitRect.Contains(p1) || _HitRect.Contains(p2))
                    this.addSelection(xNodeLoop);
                 
            }
            else if (__dummyScrutVar30.equals("Rectangle"))
            {
                selectInRectangle(xNodeLoop,r);
            }
            else if (__dummyScrutVar30.equals("Table"))
            {
                selectInTable(xNodeLoop,r);
            }
            else if (__dummyScrutVar30.equals("Matrix"))
            {
                selectInMatrix(xNodeLoop,r);
            }
            else if (__dummyScrutVar30.equals("List"))
            {
                selectInList(xNodeLoop,r);
            }
                  
        }
        return ;
    }

    private void selectInList(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF rif = getReportItemRect(xNode,r);
        if (!rif.IntersectsWith(_HitRect))
            return ;
         
        XmlNode ri = getNamedChildNode(xNode,"ReportItems");
        if (ri == null)
            return ;
         
        selectInReportItems(ri,rif);
        if (this.getSelectedCount() == 0)
            // if nothing inside selected select List itself
            this.addSelection(xNode);
         
    }

    private void selectInMatrix(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF mr = getReportItemRect(xNode,r);
        // get the matrix rectangle
        MatrixView matrix = new MatrixView(this,xNode);
        mr.Height = matrix.getHeight();
        mr.Width = matrix.getWidth();
        if (!mr.IntersectsWith(_HitRect))
            return ;
         
        float ypos = mr.Top;
        for (int row = 0;row < matrix.getRows();row++)
        {
            float xpos = mr.Left;
            for (int col = 0;col < matrix.getColumns();col++)
            {
                MatrixItem mi = matrix.get___idx(row,col);
                if (mi.getReportItem() != null)
                {
                    RectangleF cr = new RectangleF(xpos, ypos, mi.getWidth(), mi.getHeight());
                    selectInReportItems(mi.getReportItem(),cr);
                }
                 
                float width = matrix.get___idx(1,col).getWidth();
                xpos += width;
            }
            ypos += matrix.get___idx(row,1).getHeight();
        }
        return ;
    }

    private void selectInRectangle(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF rif = getReportItemRect(xNode,r);
        if (!rif.IntersectsWith(_HitRect))
            return ;
         
        XmlNode ri = getNamedChildNode(xNode,"ReportItems");
        if (ri == null)
            return ;
         
        selectInReportItems(ri,rif);
        if (this.getSelectedCount() == 0)
            // if nothing inside selected select Rectangle itself
            this.addSelection(xNode);
         
    }

    private void selectInTable(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF tr = getReportItemRect(xNode,r);
        // get the table rectangle
        // For Table width is really defined by the table columns
        float[] colWidths = new float[]();
        colWidths = getTableColumnWidths(getNamedChildNode(xNode,"TableColumns"));
        // calc the total width
        float w = 0;
        for (Object __dummyForeachVar42 : colWidths)
        {
            float cw = (Float)__dummyForeachVar42;
            w += cw;
        }
        tr.Width = w;
        // For Table height is really defined the sum of the RowHeights
        List<XmlNode> trs = getTableRows(xNode);
        tr.Height = GetTableRowsHeight(trs);
        if (!tr.IntersectsWith(_HitRect))
            return ;
         
        // Loop thru the TableRows and the columns in each of them to get at the
        //  individual cell
        float yPos = tr.Y;
        for (Object __dummyForeachVar44 : trs)
        {
            XmlNode trow = (XmlNode)__dummyForeachVar44;
            XmlNode tcells = getNamedChildNode(trow,"TableCells");
            float h = GetSize(getNamedChildNode(trow,"Height").InnerText);
            float xPos = tr.X;
            int col = 0;
            for (Object __dummyForeachVar43 : tcells)
            {
                XmlNode tcell = (XmlNode)__dummyForeachVar43;
                if (!StringSupport.equals(tcell.Name, "TableCell"))
                    continue;
                 
                // Calculate width based on cell span
                float width = 0;
                int colSpan = Convert.ToInt32(getElementValue(tcell,"ColSpan","1"));
                for (int i = 0;i < colSpan && col + i < colWidths.Length;i++)
                {
                    width += colWidths[col + i];
                }
                RectangleF cellR = new RectangleF(xPos, yPos, width, h);
                if (cellR.IntersectsWith(_HitRect))
                    this.selectInReportItems(getNamedChildNode(tcell,"ReportItems"),cellR);
                 
                xPos += width;
                col += colSpan;
            }
            yPos += h;
        }
        return ;
    }

    public boolean tableColumnResize(XmlNode tcNode, int xInc) throws Exception {
        if (tcNode == null || xInc == 0)
            return false;
         
        if (!(StringSupport.equals(tcNode.Name, "TableColumn") || StringSupport.equals(tcNode.Name, "RowGrouping") || StringSupport.equals(tcNode.Name, "MatrixColumn")))
            return false;
         
        XmlNode w = this.getCreateNamedChildNode(tcNode,"Width","0pt");
        return this.changeWidth(w,xInc,RADIUS);
    }

    public boolean tableRowResize(XmlNode trNode, int yInc) throws Exception {
        if (trNode == null || yInc == 0)
            return false;
         
        if (!(StringSupport.equals(trNode.Name, "TableRow") || StringSupport.equals(trNode.Name, "ColumnGrouping") || StringSupport.equals(trNode.Name, "MatrixRow")))
            return false;
         
        XmlNode h = this.getCreateNamedChildNode(trNode,"Height","0pt");
        return this.changeHeight(h,yInc,RADIUS);
    }

    /**
    * Returns the XmlNode of the report container that has the point: PageHeader, Body, List, PageFooter
    * 
    *  @param p Location to look for in pixels
    *  @param hScroll Horizontal scroll position in points
    *  @param vScroll Vertical scroll position in points
    */
    public HitLocation hitContainer(Point p, float hScroll, float vScroll) throws Exception {
        return hitNode(p,hScroll,vScroll);
    }

    /**
    * Returns the XmlNode of the object hit
    * 
    *  @param p Location to look for in pixels
    *  @param hScroll Horizontal scroll position in points
    *  @param vScroll Vertical scroll position in points
    */
    public HitLocation hitNode(Point p, float hScroll, float vScroll) throws Exception {
        if (rDoc == null)
            return null;
         
        _hScroll = hScroll;
        _vScroll = vScroll;
        ProcessReport(rDoc.LastChild);
        float bh = 0, hh = 0, fh = 0;
        _HitPoint = new PointF(PointsX(p.X) + _hScroll, PointsY(p.Y) + _vScroll);
        // Check for hit in the header
        XmlNode hn = getNamedChildNode(phNode,"Height");
        if (hn != null)
        {
            hh = GetSize(hn.InnerText);
            RectangleF rf = new RectangleF(LEFTGAP, hh, PointsX(Width) + _hScroll, BANDHEIGHT);
            if (rf.Contains(_HitPoint))
                return new HitLocation(hn,phNode,HitLocationEnum.Inside,new PointF(0, 0));
             
            if (_HitPoint.Y <= hh)
            {
                rf.Y = 0;
                rf.Height = hh;
                if (rf.Contains(_HitPoint))
                {
                    XmlNode ri = getNamedChildNode(phNode,"ReportItems");
                    HitLocation ril = hitReportItems(ri,rf);
                    if (ril == null)
                        return new HitLocation(phNode,phNode,HitLocationEnum.Inside,new PointF(_HitPoint.X - rf.X, _HitPoint.Y - rf.Y));
                    else
                        return ril; 
                }
                else
                    return null; 
            }
             
        }
         
        // Check for hit in the body
        XmlNode bn = getNamedChildNode(bodyNode,"Height");
        if (bn != null)
        {
            bh = GetSize(bn.InnerText);
            RectangleF rf = new RectangleF(LEFTGAP, hh + BANDHEIGHT + bh, PointsX(Width) + _hScroll, BANDHEIGHT);
            if (rf.Contains(_HitPoint))
                return new HitLocation(bn,bodyNode,HitLocationEnum.Inside,new PointF(0, 0));
             
            if (_HitPoint.Y <= hh + BANDHEIGHT + bh)
            {
                rf.Y = hh + BANDHEIGHT;
                rf.Height = bh;
                if (rf.Contains(_HitPoint))
                {
                    XmlNode ri = getNamedChildNode(bodyNode,"ReportItems");
                    HitLocation ril = hitReportItems(ri,rf);
                    if (ril == null)
                        return new HitLocation(bodyNode,bodyNode,HitLocationEnum.Inside,new PointF(_HitPoint.X - rf.X, _HitPoint.Y - rf.Y));
                    else
                        return ril; 
                }
                else
                    return null; 
            }
             
        }
         
        // Check for hit in the footer
        XmlNode fn = getNamedChildNode(pfNode,"Height");
        if (fn != null)
        {
            fh = GetSize(fn.InnerText);
            RectangleF rf = new RectangleF(LEFTGAP, hh + BANDHEIGHT + bh + BANDHEIGHT + fh, PointsX(Width) + _hScroll, BANDHEIGHT);
            if (rf.Contains(_HitPoint))
                return new HitLocation(fn,pfNode,HitLocationEnum.Inside,new PointF(0, 0));
             
            if (_HitPoint.Y <= hh + BANDHEIGHT + bh + BANDHEIGHT + fh)
            {
                rf.Y = hh + BANDHEIGHT + bh + BANDHEIGHT;
                rf.Height = fh;
                if (rf.Contains(_HitPoint))
                {
                    XmlNode ri = getNamedChildNode(pfNode,"ReportItems");
                    HitLocation ril = hitReportItems(ri,rf);
                    if (ril == null)
                        return new HitLocation(pfNode,pfNode,HitLocationEnum.Inside,new PointF(_HitPoint.X - rf.X, _HitPoint.Y - rf.Y));
                    else
                        return ril; 
                }
                else
                    return null; 
            }
             
        }
         
        return null;
    }

    /**
    * Changes the height of the node (if possible) using the specified increment
    * 
    *  @param b The XmlNode that is the height property.
    *  @param inc The amount to increment in pixels
    *  @return true if height was changed else false
    */
    public boolean changeHeight(XmlNode b, int inc, float minValue) throws Exception {
        if (b == null || inc == 0 || !StringSupport.equals(b.Name, "Height"))
            return false;
         
        float h = GetSize(b.InnerText);
        h += PointsY(inc);
        if (h < minValue)
            h = minValue;
         
        String nv = String.Format(NumberFormatInfo.InvariantInfo, "{0:0.0}pt", h);
        if (!StringSupport.equals(b.InnerText, nv))
        {
            b.InnerText = nv;
            return true;
        }
        else
            return false; 
    }

    /**
    * Changes the width of the node (if possible) using the specified increment
    * 
    *  @param b The XmlNode that is the width property.
    *  @param inc The amount to increment in pixels
    *  @return true if height was changed else false
    */
    public boolean changeWidth(XmlNode b, int inc, float minValue) throws Exception {
        if (b == null || inc == 0 || !StringSupport.equals(b.Name, "Width"))
            return false;
         
        float w = GetSize(b.InnerText);
        w += PointsX(inc);
        if (w < minValue)
            w = minValue;
         
        String nv = String.Format(NumberFormatInfo.InvariantInfo, "{0:0.0}pt", w);
        if (!StringSupport.equals(b.InnerText, nv))
        {
            b.InnerText = nv;
            return true;
        }
        else
            return false; 
    }

    private HitLocation hitReportItems(XmlNode xNode, RectangleF r) throws Exception {
        if (xNode == null)
            return null;
         
        HitLocation hnl = null;
        for (Object __dummyForeachVar45 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar45;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar31 = xNodeLoop.Name;
            if (__dummyScrutVar31.equals("Textbox") || __dummyScrutVar31.equals("Chart") || __dummyScrutVar31.equals("Image") || __dummyScrutVar31.equals("Subreport") || __dummyScrutVar31.equals("CustomReportItem"))
            {
                hnl = hitReportItem(xNodeLoop,r);
            }
            else if (__dummyScrutVar31.equals("Rectangle"))
            {
                hnl = hitRectangle(xNodeLoop,r);
            }
            else if (__dummyScrutVar31.equals("Table"))
            {
                hnl = hitTable(xNodeLoop,r);
            }
            else if (__dummyScrutVar31.equals("Matrix"))
            {
                hnl = hitMatrix(xNodeLoop,r);
            }
            else if (__dummyScrutVar31.equals("List"))
            {
                hnl = hitList(xNodeLoop,r);
            }
            else if (__dummyScrutVar31.equals("Line"))
            {
                hnl = hitLine(xNodeLoop,r);
            }
                  
            if (hnl != null)
                return hnl;
             
        }
        return null;
    }

    private HitLocation hitList(XmlNode xNode, RectangleF r) throws Exception {
        return hitRectangle(xNode,r);
    }

    // Same logic as Rectangle
    private HitLocation hitLine(XmlNode xNode, RectangleF r) throws Exception {
        PointF p1 = new PointF();
        PointF p2 = new PointF();
        RefSupport<PointF> refVar___6 = new RefSupport<PointF>();
        RefSupport<PointF> refVar___7 = new RefSupport<PointF>();
        this.getLineEnds(xNode,r,refVar___6,refVar___7);
        p1 = refVar___6.getValue();
        p2 = refVar___7.getValue();
        RectangleF rifLoc = new RectangleF();
        if (this.getSelectedCount() == 1 && this._SelectedReportItems[0] == xNode)
        {
            // When selected node; we do special testing to determine if the location
            //   hits one of the special sizing locations
            rifLoc = new RectangleF(p1.X - RADIUS, p1.Y - RADIUS, 2 * RADIUS, 2 * RADIUS);
            if (rifLoc.Contains(_HitPoint))
                return new HitLocation(xNode,null,HitLocationEnum.LineLeft,new PointF(0, 0));
             
            rifLoc = new RectangleF(p2.X - RADIUS, p2.Y - RADIUS, 2 * RADIUS, 2 * RADIUS);
            if (rifLoc.Contains(_HitPoint))
                return new HitLocation(xNode,null,HitLocationEnum.LineRight,new PointF(0, 0));
             
        }
         
        // Construct a ploygon that surrounds the line
        PointF[] pg = new PointF[5];
        if (p1.X <= p2.X)
        {
            pg[0] = pg[4] = new PointF(p1.X - RADIUS, p1.Y - RADIUS);
            pg[1] = new PointF(p2.X + RADIUS, p2.Y - RADIUS);
            pg[2] = new PointF(p2.X + RADIUS, p2.Y + RADIUS);
            pg[3] = new PointF(p1.X - RADIUS, p1.Y + RADIUS);
        }
        else
        {
            pg[0] = pg[4] = new PointF(p2.X - RADIUS, p2.Y - RADIUS);
            pg[1] = new PointF(p1.X + RADIUS, p1.Y - RADIUS);
            pg[2] = new PointF(p1.X + RADIUS, p1.Y + RADIUS);
            pg[3] = new PointF(p2.X - RADIUS, p2.Y + RADIUS);
        } 
        if (InsidePolygon(pg, _HitPoint))
        {
            boolean bSelected = isNodeSelected(xNode);
            HitLocation hl = new HitLocation(xNode,null,bSelected ? HitLocationEnum.Move : HitLocationEnum.Inside,new PointF(0, 0));
            return hl;
        }
        else
            return null; 
    }

    private boolean insidePolygon(PointF[] points, PointF p) throws Exception {
        for (int i = 0;i < points.Length - 1;i++)
        {
            // see http://astronomy.swin.edu.au/~pbourke/geometry/insidepoly/ Solution 3
            if ((p.Y - points[i].Y) * (points[i + 1].X - points[i].X) - (p.X - points[i].X) * (points[i + 1].Y - points[i].Y) < 0)
                return false;
             
        }
        return true;
    }

    private HitLocation hitMatrix(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF mr = getReportItemRect(xNode,r);
        // get the matrix rectangle
        MatrixView matrix = new MatrixView(this,xNode);
        mr.Height = matrix.getHeight();
        mr.Width = matrix.getWidth();
        if (!mr.Contains(_HitPoint))
            return null;
         
        // Check to see if column resize location
        HitLocation hl = hitMatrixColumnResize(xNode,matrix,mr);
        if (hl != null)
            return hl;
         
        // Check to see if Row resize location
        hl = hitMatrixRowResize(xNode,matrix,mr);
        if (hl != null)
            return hl;
         
        float ypos = mr.Top;
        HitLocation result = null;
        for (int row = 0;row < matrix.getRows() && result == null;row++)
        {
            float xpos = mr.Left;
            for (int col = 0;col < matrix.getColumns() && result == null;col++)
            {
                MatrixItem mi = matrix.get___idx(row,col);
                if (mi.getReportItem() != null)
                {
                    RectangleF cr = new RectangleF(xpos, ypos, mi.getWidth(), mi.getHeight());
                    result = hitReportItems(mi.getReportItem(),cr);
                }
                 
                float width = matrix.get___idx(1,col).getWidth();
                xpos += width;
            }
            ypos += matrix.get___idx(row,1).getHeight();
        }
        if (result == null)
            result = new HitLocation(xNode,xNode,HitLocationEnum.Inside,new PointF(0, 0));
        else
            result.HitContainer = xNode; 
        return result;
    }

    private HitLocation hitMatrixColumnResize(XmlNode xNode, MatrixView matrix, RectangleF r) throws Exception {
        // Loop thru the matrix columns to see if point is over the column resize area
        int i = new int();
        float xPos = r.Right - RADIUS;
        for (i = matrix.getColumns() - 1;i >= 0;i--)
        {
            // Need to loop backwards thru the columns; so we can resize 0 length columns
            RectangleF cr = new RectangleF(xPos, r.Top, RADIUS * 2, r.Height);
            if (cr.Contains(_HitPoint))
                break;
             
            xPos -= matrix.get___idx(1,i).getWidth();
        }
        if (i < 0)
            return null;
         
        XmlNode hNode = null;
        for (hNode = matrix.get___idx(matrix.getHeaderRows(),i).getReportItem();hNode != null;hNode = hNode.ParentNode)
        {
            if (StringSupport.equals(hNode.Name, "RowGrouping"))
                break;
             
        }
        HitLocation result = null;
        if (hNode != null)
            result = new HitLocation(hNode,xNode,HitLocationEnum.TableColumnResize,new PointF(0, 0));
        else
        {
            // find out which column it is.
            // 1) Get the report item found
            XmlNode ri = matrix.get___idx(matrix.getHeaderRows(),i).getReportItem();
            // 2) Find its relative location in the MatrixCells
            XmlNode mcells = DesignXmlDraw.findNextInHierarchy(xNode,"MatrixRows","MatrixRow","MatrixCells");
            if (mcells == null)
                return null;
             
            int offsetColumn = 0;
            for (Object __dummyForeachVar46 : mcells.ChildNodes)
            {
                XmlNode mcell = (XmlNode)__dummyForeachVar46;
                if (!StringSupport.equals(mcell.Name, "MatrixCell"))
                    continue;
                 
                XmlNode cri = DesignXmlDraw.findNextInHierarchy(mcell,"ReportItems");
                if (ri == cri)
                    break;
                 
                offsetColumn++;
            }
            // 3) Now find the same relative location in MatrixColumns
            XmlNode mcols = DesignXmlDraw.findNextInHierarchy(xNode,"MatrixColumns");
            if (mcols == null)
                return null;
             
            XmlNode mc = null;
            for (Object __dummyForeachVar47 : mcols.ChildNodes)
            {
                XmlNode mcol = (XmlNode)__dummyForeachVar47;
                if (offsetColumn == 0)
                {
                    mc = mcol;
                    break;
                }
                 
                offsetColumn--;
            }
            if (mc == null)
                // Not found; just use the first one
                mc = DesignXmlDraw.findNextInHierarchy(xNode,"MatrixColumns","MatrixColumn");
             
            if (mc != null)
                result = new HitLocation(mc,xNode,HitLocationEnum.TableColumnResize,new PointF(0, 0));
             
        } 
        return result;
    }

    private HitLocation hitMatrixRowResize(XmlNode xNode, MatrixView matrix, RectangleF r) throws Exception {
        // Loop thru the matrix rows to see if point is over the row resize area
        float yPos = r.Bottom - RADIUS;
        int row = new int();
        for (row = matrix.getRows() - 1;row >= 0;row--)
        {
            RectangleF mr = new RectangleF(r.Left, yPos, r.Width, RADIUS * 2);
            if (mr.Contains(_HitPoint))
                break;
             
            yPos -= matrix.get___idx(row,0).getHeight();
        }
        if (row < 0)
            return null;
         
        XmlNode hNode = null;
        for (hNode = matrix.get___idx(row,matrix.getHeaderColumns()).getReportItem();hNode != null;hNode = hNode.ParentNode)
        {
            if (StringSupport.equals(hNode.Name, "ColumnGrouping"))
                break;
             
        }
        HitLocation result = null;
        if (hNode != null)
            result = new HitLocation(hNode,xNode,HitLocationEnum.TableRowResize,new PointF(0, 0));
        else
        {
            // find out which row it is.
            // 1) Get the report item found
            XmlNode ri = matrix.get___idx(row,matrix.getHeaderColumns()).getReportItem();
            // 2) Find its location in the MatrixRows
            XmlNode mrows = DesignXmlDraw.findNextInHierarchy(xNode,"MatrixRows");
            if (mrows == null)
                return null;
             
            XmlNode mr = null;
            for (Object __dummyForeachVar49 : mrows.ChildNodes)
            {
                XmlNode mrow = (XmlNode)__dummyForeachVar49;
                if (!StringSupport.equals(mrow.Name, "MatrixRow"))
                    continue;
                 
                // find the report item
                XmlNode mcells = DesignXmlDraw.findNextInHierarchy(mrow,"MatrixCells");
                if (mcells == null)
                    return null;
                 
                for (Object __dummyForeachVar48 : mcells.ChildNodes)
                {
                    XmlNode mcell = (XmlNode)__dummyForeachVar48;
                    if (!StringSupport.equals(mcell.Name, "MatrixCell"))
                        continue;
                     
                    XmlNode cri = DesignXmlDraw.findNextInHierarchy(mcell,"ReportItems");
                    if (ri == cri)
                    {
                        mr = mrow;
                        break;
                    }
                     
                }
                if (mr != null)
                    break;
                 
            }
            if (mr == null)
            {
                // Not found; just use the first one
                mr = DesignXmlDraw.findNextInHierarchy(xNode,"MatrixRows","MatrixRow");
            }
             
            if (mr != null)
                result = new HitLocation(mr,xNode,HitLocationEnum.TableRowResize,new PointF(0, 0));
             
        } 
        return result;
    }

    private HitLocation hitRectangle(XmlNode xNode, RectangleF r) throws Exception {
        HitLocation hl = hitReportItem(xNode,r);
        if (hl == null)
            return null;
         
        if (hl.HitSpot != HitLocationEnum.Inside)
            return hl;
         
        // if it didn't hit the inside
        //   we just return the node (so it can be resized)
        hl.HitContainer = xNode;
        // Rectangle is its own container
        RectangleF rif = getReportItemRect(xNode,r);
        hl.HitRelative = new PointF(_HitPoint.X - rif.X, _HitPoint.Y - rif.Y);
        XmlNode ri = getNamedChildNode(xNode,"ReportItems");
        if (ri == null)
            return hl;
         
        HitLocation hl2 = hitReportItems(ri,getReportItemRect(xNode,r));
        return hl2 != null ? hl2 : hl;
    }

    // return either the rectangle or the embedded reportitem
    private HitLocation hitReportItem(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF rif = getReportItemRect(xNode,r);
        if (this.getSelectedCount() == 1 && this._SelectedReportItems[0] == xNode)
        {
            // When selected node; we do special testing to determine if the location
            //   hits one of the special sizing locations
            // Try it in a big rectangle around all the sizing rectangles
            RectangleF rifLoc = new RectangleF(rif.X - RADIUS, rif.Y - RADIUS, rif.Width + 2 * RADIUS, rif.Height + 2 * RADIUS);
            if (!rifLoc.Contains(_HitPoint))
                return null;
             
            // Top Left sizing
            rifLoc = new RectangleF(rif.X - RADIUS, rif.Y - RADIUS, 2 * RADIUS, 2 * RADIUS);
            if (rifLoc.Contains(_HitPoint))
                return new HitLocation(xNode,null,HitLocationEnum.TopLeft,new PointF(0, 0));
             
            // Top Right sizing
            rifLoc = new RectangleF(rif.X + rif.Width - RADIUS, rif.Y - RADIUS, 2 * RADIUS, 2 * RADIUS);
            if (rifLoc.Contains(_HitPoint))
                return new HitLocation(xNode,null,HitLocationEnum.TopRight,new PointF(0, 0));
             
            // Bottom Left sizing
            rifLoc = new RectangleF(rif.X - RADIUS, rif.Y + rif.Height - RADIUS, 2 * RADIUS, 2 * RADIUS);
            if (rifLoc.Contains(_HitPoint))
                return new HitLocation(xNode,null,HitLocationEnum.BottomLeft,new PointF(0, 0));
             
            // Bottom Right sizing
            rifLoc = new RectangleF(rif.X + rif.Width - RADIUS, rif.Y + rif.Height - RADIUS, 2 * RADIUS, 2 * RADIUS);
            if (rifLoc.Contains(_HitPoint))
                return new HitLocation(xNode,null,HitLocationEnum.BottomRight,new PointF(0, 0));
             
        }
         
        // ok try the point in the rectangle now
        if (rif.Contains(_HitPoint))
        {
            boolean bSelected = isNodeSelected(xNode);
            HitLocation hl = new HitLocation(xNode,null,bSelected ? HitLocationEnum.Move : HitLocationEnum.Inside,new PointF(0, 0));
            // Correct hit location (move or inside) for reportitems that contain moveable items
            if (bSelected && (StringSupport.equals(xNode.Name, "List") || StringSupport.equals(xNode.Name, "Rectangle")))
            {
                RectangleF innerRect = new RectangleF(rif.Left + RADIUS, rif.Top + RADIUS, rif.Width - RADIUS * 2, rif.Height - RADIUS * 2);
                if (innerRect.Contains(_HitPoint))
                    hl.HitSpot = HitLocationEnum.Inside;
                 
            }
             
            return hl;
        }
        else
            return null; 
    }

    private HitLocation hitTable(XmlNode xNode, RectangleF r) throws Exception {
        RectangleF tr = getReportItemRect(xNode,r);
        // get the table rectangle
        // For Table width is really defined by the table columns
        float[] colWidths = new float[]();
        colWidths = getTableColumnWidths(getNamedChildNode(xNode,"TableColumns"));
        // calc the total width
        float w = 0;
        for (Object __dummyForeachVar50 : colWidths)
        {
            float cw = (Float)__dummyForeachVar50;
            w += cw;
        }
        tr.Width = w;
        // For Table height is really defined the sum of the RowHeights
        List<XmlNode> trs = getTableRows(xNode);
        tr.Height = GetTableRowsHeight(trs);
        // If not in the bigger rectangle; its not in any smaller rectangles
        if (!tr.Contains(_HitPoint))
            return null;
         
        // Check to see if column resize location
        HitLocation hl = HitTableColumnResize(xNode, tr, colWidths);
        if (hl != null)
            return hl;
         
        // Check to see if Row resize location
        hl = HitTableRowResize(xNode, tr, trs);
        if (hl != null)
            return hl;
         
        // Loop thru the TableRows and the columns in each of them to get at the
        //  individual cell
        float yPos = tr.Y;
        for (Object __dummyForeachVar52 : trs)
        {
            XmlNode trow = (XmlNode)__dummyForeachVar52;
            XmlNode tcells = getNamedChildNode(trow,"TableCells");
            float h = GetSize(getNamedChildNode(trow,"Height").InnerText);
            float xPos = tr.X;
            int col = 0;
            for (Object __dummyForeachVar51 : tcells)
            {
                XmlNode tcell = (XmlNode)__dummyForeachVar51;
                if (!StringSupport.equals(tcell.Name, "TableCell"))
                    continue;
                 
                // Calculate width based on cell span
                float width = 0;
                int colSpan = Convert.ToInt32(getElementValue(tcell,"ColSpan","1"));
                for (int i = 0;i < colSpan && col + i < colWidths.Length;i++)
                {
                    width += colWidths[col + i];
                }
                RectangleF cellR = new RectangleF(xPos, yPos, width, h);
                if (cellR.Contains(_HitPoint))
                {
                    HitLocation hnl = hitReportItems(getNamedChildNode(tcell,"ReportItems"),cellR);
                    if (hnl != null)
                    {
                        if (hnl.HitContainer == null)
                            hnl.HitContainer = xNode;
                         
                        return hnl;
                    }
                    else
                        return new HitLocation(tcell,null,HitLocationEnum.Inside,new PointF(0, 0)); 
                }
                 
                // if not already in container; put in table
                xPos += width;
                col += colSpan;
            }
            yPos += h;
        }
        return null;
    }

    private HitLocation hitTableColumnResize(XmlNode xNode, RectangleF r, float[] colWidths) throws Exception {
        XmlNode tcols = this.getNamedChildNode(xNode,"TableColumns");
        if (tcols == null)
            return null;
         
        // Loop thru the table columns to see if point is over the column resize area
        int i = new int();
        XmlNode cn = null;
        float xPos = r.Right - RADIUS;
        for (i = colWidths.Length - 1;i >= 0;i--)
        {
            // Need to loop backwards thru the columns; so we can resize 0 length columns
            RectangleF cr = new RectangleF(xPos, r.Top, RADIUS * 2, r.Height);
            if (cr.Contains(_HitPoint))
                break;
             
            xPos -= colWidths[i];
        }
        if (i < 0)
            return null;
         
        // Now find the node that matches this column
        int ci = 0;
        for (Object __dummyForeachVar53 : tcols.ChildNodes)
        {
            XmlNode tcn = (XmlNode)__dummyForeachVar53;
            if (!StringSupport.equals(tcn.Name, "TableColumn"))
                continue;
             
            if (ci++ == i)
            {
                cn = tcn;
                break;
            }
             
        }
        if (cn == null)
            return null;
         
        // really shouldn't happen
        HitLocation hl = new HitLocation(cn,xNode,HitLocationEnum.TableColumnResize,new PointF(0, 0));
        return hl;
    }

    private HitLocation hitTableRowResize(XmlNode xNode, RectangleF r, List<XmlNode> trs) throws Exception {
        // Loop thru the table rows to see if point is over the row resize area
        XmlNode rn = null;
        float yPos = r.Bottom - RADIUS;
        List<XmlNode> reverse = new List<XmlNode>(trs);
        reverse.Reverse();
        for (Object __dummyForeachVar54 : reverse)
        {
            XmlNode trn = (XmlNode)__dummyForeachVar54;
            RectangleF tr = new RectangleF(r.Left, yPos, r.Width, RADIUS * 2);
            if (tr.Contains(_HitPoint))
            {
                rn = trn;
                break;
            }
             
            yPos -= GetSize(getNamedChildNode(trn,"Height").InnerText);
        }
        if (rn == null)
            return null;
         
        HitLocation hl = new HitLocation(rn,xNode,HitLocationEnum.TableRowResize,new PointF(0, 0));
        return hl;
    }

    public boolean moveReportItem(XmlNode b, int xInc, int yInc) throws Exception {
        if (b == null)
            return false;
         
        if (xInc == 0 && yInc == 0)
            return false;
         
        if (inTable(b))
        {
            while (b != null && !StringSupport.equals(b.Name, "Table"))
            {
                b = b.ParentNode;
            }
            if (b == null)
                return false;
             
        }
        else if (inMatrix(b))
        {
            while (b != null && !StringSupport.equals(b.Name, "Matrix"))
            {
                b = b.ParentNode;
            }
            if (b == null)
                return false;
             
        }
          
        XmlNode lNode = this.getNamedChildNode(b,"Left");
        if (lNode == null)
            lNode = createElement(b,"Left","0pt");
         
        XmlNode tNode = this.getNamedChildNode(b,"Top");
        if (tNode == null)
            tNode = createElement(b,"Top","0pt");
         
        // Assume nothing changed
        boolean changed = false;
        // handle Left
        float l = GetSize(lNode.InnerText);
        l += PointsX(xInc);
        if (l < 0)
            l = 0;
         
        String lv = String.Format(NumberFormatInfo.InvariantInfo, "{0:0.0}pt", l);
        if (!StringSupport.equals(lNode.InnerText, lv))
        {
            lNode.InnerText = lv;
            changed = true;
        }
         
        // handle right
        float t = GetSize(tNode.InnerText);
        t += PointsY(yInc);
        if (t < 0)
            t = 0;
         
        String tv = String.Format(NumberFormatInfo.InvariantInfo, "{0:0.0}pt", t);
        if (!StringSupport.equals(tNode.InnerText, tv))
        {
            tNode.InnerText = tv;
            changed = true;
        }
         
        return changed;
    }

    public boolean moveSelectedItems(int xInc, int yInc, HitLocationEnum hle) throws Exception {
        boolean rc = false;
        // Are we just moving the selected items
        if (hle == HitLocationEnum.Move)
        {
            List<XmlNode> ar = null;
            for (Object __dummyForeachVar55 : this._SelectedReportItems)
            {
                XmlNode ri = (XmlNode)__dummyForeachVar55;
                // Ensure we don't move table or matrixes multiple times depending on selection
                XmlNode tm = tMParent(ri);
                if (tm == null)
                {
                    rc |= moveReportItem(ri,xInc,yInc);
                    continue;
                }
                 
                if (ar == null)
                    ar = new List<XmlNode>();
                else if (ar.Contains(tm))
                    continue;
                  
                ar.Add(tm);
                rc |= moveReportItem(tm,xInc,yInc);
            }
            return rc;
        }
         
        if (_SelectedReportItems.Count <= 0)
            return false;
         
        // Ok we need to resize the nodes
        rc = false;
        for (Object __dummyForeachVar56 : _SelectedReportItems)
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar56;
            rc |= resizeReportItem(xNode,xInc,yInc,hle);
        }
        return rc;
    }

    public boolean resizeReportItem(XmlNode xNode, int xInc, int yInc, HitLocationEnum hle) throws Exception {
        if (xInc == 0 && yInc == 0)
            return false;
         
        if (inTable(xNode) || inMatrix(xNode))
            return false;
         
        boolean rc = false;
        float xIncPt = PointsX(xInc);
        float yIncPt = PointsY(yInc);
        XmlNode lNode = this.getCreateNamedChildNode(xNode,"Left","0pt");
        float l = GetSize(lNode.InnerText);
        XmlNode tNode = this.getCreateNamedChildNode(xNode,"Top","0pt");
        float t = GetSize(tNode.InnerText);
        XmlNode wNode = this.getNamedChildNode(xNode,"Width");
        float w = wNode == null ? float.MinValue : GetSize(wNode.InnerText);
        XmlNode hNode = this.getNamedChildNode(xNode,"Height");
        float h = hNode == null ? float.MinValue : GetSize(hNode.InnerText);
        switch(hle)
        {
            case BottomLeft: 
                // need to adjust x/y position and height/width
                if (wNode == null || hNode == null)
                    return false;
                 
                l += xIncPt;
                w -= xIncPt;
                h += yIncPt;
                break;
            case BottomMiddle: 
                // need to adjust height
                if (hNode == null)
                    return false;
                 
                h += yIncPt;
                break;
            case BottomRight: 
                // need to adjust width and height
                if (wNode == null || hNode == null)
                    return false;
                 
                w += xIncPt;
                h += yIncPt;
                break;
            case LeftMiddle: 
                // need to adjust x position and width
                if (wNode == null)
                    return false;
                 
                l += xIncPt;
                w -= xIncPt;
                break;
            case RightMiddle: 
                // need to adjust width
                if (wNode == null)
                    return false;
                 
                w += xIncPt;
                break;
            case TopLeft: 
                // need to adjust x,y position and height
                if (wNode == null || hNode == null)
                    return false;
                 
                l += xIncPt;
                w -= xIncPt;
                t += yIncPt;
                h -= yIncPt;
                break;
            case TopMiddle: 
                // need to adjust y position and height
                if (hNode == null)
                    return false;
                 
                t += yIncPt;
                h -= yIncPt;
                break;
            case TopRight: 
                // need to adjust y position, width and height
                if (wNode == null || hNode == null)
                    return false;
                 
                w += xIncPt;
                t += yIncPt;
                h -= yIncPt;
                break;
            case LineLeft: 
                if (l + xIncPt < 0)
                {
                    xIncPt = -l;
                    l = 0;
                }
                else
                    l += xIncPt; 
                if (t + yIncPt < 0)
                {
                    yIncPt = -t;
                    t = 0;
                }
                else
                    t += yIncPt; 
                w -= xIncPt;
                h -= yIncPt;
                break;
            case LineRight: 
                w += xIncPt;
                h += yIncPt;
                break;
        
        }
        // Normalize results
        if (l < 0)
        {
            w -= l;
            l = 0;
        }
         
        if (t < 0)
        {
            h -= t;
            t = 0;
        }
         
        if (StringSupport.equals(xNode.Name, "Line"))
        {
            // Line height and widths allowed to go negative but overall position can't be < 0
            if (t + h < 0)
                h = -t;
             
            if (l + w < 0)
                w = -l;
             
        }
        else
        {
            // Don't let height and width go too small
            if (h < .1)
                h = .1f;
             
            if (w < .1)
                w = .1f;
             
        } 
        String tv = String.Format(NumberFormatInfo.InvariantInfo, "{0:0.00}pt", t);
        if (!StringSupport.equals(tNode.InnerText, tv))
        {
            tNode.InnerText = tv;
            rc = true;
        }
         
        String lv = String.Format(NumberFormatInfo.InvariantInfo, "{0:0.00}pt", l);
        if (!StringSupport.equals(lNode.InnerText, lv))
        {
            lNode.InnerText = lv;
            rc = true;
        }
         
        String wv = String.Format(NumberFormatInfo.InvariantInfo, "{0:0.00}pt", w);
        if (wNode != null && !StringSupport.equals(wNode.InnerText, wv))
        {
            wNode.InnerText = wv;
            rc = true;
        }
         
        String hv = String.Format(NumberFormatInfo.InvariantInfo, "{0:0.00}pt", h);
        if (hNode != null && !StringSupport.equals(hNode.InnerText, hv))
        {
            hNode.InnerText = hv;
            rc = true;
        }
         
        return rc;
    }

    public XmlElement createElement(XmlNode parent, String name, String val) throws Exception {
        XmlElement node = new XmlElement();
        if (name.StartsWith("rd:"))
        {
            String nms = rDoc.DocumentElement.GetNamespaceOfPrefix("rd");
            if (nms == null || nms.Length == 0)
                nms = DialogValidateRdl.MSDESIGNERSCHEMA;
             
            node = rDoc.CreateElement(name, nms);
        }
        else if (name.StartsWith("fyi:"))
        {
            String nms = rDoc.DocumentElement.GetNamespaceOfPrefix("fyi");
            if (nms == null || nms.Length == 0)
                nms = DialogValidateRdl.DESIGNERSCHEMA;
             
            node = rDoc.CreateElement(name, nms);
        }
        else
            node = rDoc.CreateElement(name);  
        if (val != null)
            node.InnerText = val;
         
        parent.AppendChild(node);
        return node;
    }

    /**
    * Use for testing a name for validity.  If null is return name is valid.
    * Otherwise a string describing the error is returned.
    * 
    *  @param xNode 
    *  @param name 
    *  @return
    */
    public String nameError(XmlNode xNode, String name) throws Exception {
        return getReportNames().nameError(xNode,name);
    }

    public String groupingNameCheck(XmlNode xNode, String name) throws Exception {
        return getReportNames().groupingNameCheck(xNode,name);
    }

    public boolean setName(XmlNode xNode, String name) throws Exception {
        return getReportNames().changeName(xNode,name);
    }

    public boolean setGroupName(XmlNode xNode, String name) throws Exception {
        return getReportNames().changeGroupName(xNode,name);
    }

    public void setElementAttribute(XmlNode parent, String name, String val) throws Exception {
        XmlAttribute attr = parent.Attributes[name];
        if (attr != null)
        {
            attr.Value = val;
        }
        else
        {
            attr = rDoc.CreateAttribute(name);
            attr.Value = val;
            parent.Attributes.Append(attr);
        } 
        return ;
    }

    public void removeElement(XmlNode parent, String name) throws Exception {
        XmlNode node = this.getNamedChildNode(parent,name);
        if (node != null)
        {
            parent.RemoveChild(node);
        }
         
        return ;
    }

    public String getDataSetNameValue(XmlNode dataRegion) throws Exception {
        XmlNode dr = getReportItemDataRegionContainer(dataRegion);
        if (dr != null)
            return getDataSetNameValue(dr);
         
        // dataRegion is embedded in a dataregion if not null
        XmlNode node = this.getNamedChildNode(dataRegion,"DataSetName");
        if (node != null)
            return node.InnerText;
         
        // Need to find the single data set specified in report
        XmlNode rNode = this.getReportNode();
        node = getNamedChildNode(rNode,"DataSets");
        if (node == null)
            return "";
         
        node = getNamedChildNode(node,"DataSet");
        if (node == null)
            return "";
         
        XmlAttribute xAttr = node.Attributes["Name"];
        if (xAttr == null)
            return "";
         
        return xAttr.Value;
    }

    public String getElementAttribute(XmlNode parent, String name, String def) throws Exception {
        XmlAttribute attr = parent.Attributes[name];
        if (attr == null)
            return def;
        else
            return attr.Value; 
    }

    public String getElementValue(XmlNode parent, String name, String defaultV) throws Exception {
        XmlNode node = this.getNamedChildNode(parent,name);
        if (node == null)
            return defaultV;
        else
            return node.InnerText; 
    }

    public XmlNode setElement(XmlNode parent, String name, String val) throws Exception {
        XmlNode node = this.getNamedChildNode(parent,name);
        if (node == null)
            node = createElement(parent,name,val);
        else if (val != null)
            node.InnerText = val;
          
        return node;
    }

    public void setTableCellColSpan(XmlNode tcell, String val) throws Exception {
        String oldval = this.getElementValue(tcell,"ColSpan","1");
        if (StringSupport.equals(oldval, val))
            return ;
         
        // if they're the same we're all done
        int iold = Convert.ToInt32(oldval);
        int inew = Convert.ToInt32(val);
        if (inew <= 0)
            inew = 1;
         
        // find out the maximum column span
        int maxcolspan = 0;
        for (XmlNode n = tcell;n != null;n = n.NextSibling)
        {
            if (StringSupport.equals(n.Name, "TableCell"))
            {
                String span = this.getElementValue(n,"ColSpan","1");
                maxcolspan += Convert.ToInt32(span);
            }
             
        }
        if (inew > maxcolspan)
            inew = maxcolspan;
         
        int dif = iold - inew;
        if (dif == 0)
            return ;
         
        // string comparison isn't always correct; especially after correction
        XmlNode tcells = tcell.ParentNode;
        if (dif < 0)
        {
            for (XmlNode n = tcell.NextSibling;n != null && dif < 0;n = tcell.NextSibling)
            {
                // we need to remove "dif" number of TableCell entries after this one
                if (StringSupport.equals(n.Name, "TableCell"))
                {
                    tcells.RemoveChild(n);
                    dif++;
                }
                 
            }
        }
        else
        {
            while (dif > 0)
            {
                // dif > 0
                // we need to create "dif" number of TableCell entries after this one
                insertTableColumn(tcells,tcell,false);
                dif--;
            }
        } 
        // Finally set the new ColSpan value
        this.SetElement(tcell, "ColSpan", inew.ToString());
        return ;
    }

    public XmlNode getReportNode() throws Exception {
        return rDoc.DocumentElement;
    }

    public IEnumerable getReportItems(String query) throws Exception {
        XmlNodeList nodeList = new XmlNodeList();
        XmlElement root = this.rDoc.DocumentElement;
        if (root.NamespaceURI != String.Empty)
        {
            if (query == null)
                query = "//Textbox|//Rectangle|//Image|//Subreport|//Chart|//Table|//List|//Line|//Matrix|" + "//rdl:Textbox|//rdl:Rectangle|//rdl:Image|//rdl:Subreport|//rdl:Chart|//rdl:Table|//rdl:List|//rdl:Line|//rdl:Matrix";
            else
            {
                // need to search in the namespace as well
                String mquery = query.Replace("//", "//rdl:");
                query = query + "|" + mquery;
            } 
            XmlNamespaceManager nsmgr = new XmlNamespaceManager(this.rDoc.NameTable);
            nsmgr.AddNamespace("rdl", root.NamespaceURI);
            //default namespace
            nodeList = root.SelectNodes(query, nsmgr);
        }
        else
        {
            if (query == null)
                query = "//Textbox|//Rectangle|//Image|//Subreport|//Chart|//Table|//List|//Line|//Matrix";
             
            nodeList = root.SelectNodes(query);
        } 
        return nodeList;
    }

    public boolean deleteChartGrouping(XmlNode dynamic) throws Exception {
        if (dynamic == null)
            return false;
         
        XmlNode seriesOrCategory = dynamic.ParentNode;
        XmlNode groupings = seriesOrCategory.ParentNode;
        groupings.RemoveChild(seriesOrCategory);
        // Remove the Grouping from Groups
        if (!groupings.HasChildNodes)
        {
            // If Groups has no children
            //   remove the SeriesGroupings or CategoryGroupings from Chart
            XmlNode pgroupings = groupings.ParentNode;
            pgroupings.RemoveChild(groupings);
            if (!pgroupings.HasChildNodes)
            {
                pgroupings.ParentNode.RemoveChild(pgroupings);
            }
             
        }
         
        return true;
    }

    public boolean deleteChartGrouping(XmlNode chart, String gname) throws Exception {
        if (chart == null || !StringSupport.equals(chart.Name, "Chart"))
            return false;
         
        XmlNode group = new XmlNode();
        group = getChartGroupName(chart,gname,"SeriesGroupings","SeriesGrouping","DynamicSeries");
        if (group == null)
            // if not there try the row groupings
            group = getChartGroupName(chart,gname,"CategoryGroupings","CategoryGrouping","DynamicCategories");
         
        return deleteChartGrouping(group);
    }

    public String[] getChartCategoryGroupNames(XmlNode chart) throws Exception {
        if (chart == null || !StringSupport.equals(chart.Name, "Chart"))
            return null;
         
        XmlNode catGroups = this.getNamedChildNode(chart,"CategoryGroupings");
        if (catGroups == null)
            return null;
         
        List<String> ar = new List<String>();
        for (Object __dummyForeachVar57 : catGroups.ChildNodes)
        {
            XmlNode cgroup = (XmlNode)__dummyForeachVar57;
            if (!StringSupport.equals(cgroup.Name, "CategoryGrouping"))
                continue;
             
            XmlNode group = DesignXmlDraw.findNextInHierarchy(cgroup,"DynamicCategories","Grouping");
            if (group == null)
                continue;
             
            String name = this.getElementAttribute(group,"Name",null);
            if (name != null)
                ar.Add(name);
             
        }
        if (ar.Count <= 0)
            return null;
         
        return ar.ToArray();
    }

    public String[] getChartSeriesGroupNames(XmlNode chart) throws Exception {
        if (chart == null || !StringSupport.equals(chart.Name, "Chart"))
            return null;
         
        XmlNode serGroups = this.getNamedChildNode(chart,"SeriesGroupings");
        if (serGroups == null)
            return null;
         
        List<String> ar = new List<String>();
        for (Object __dummyForeachVar58 : serGroups.ChildNodes)
        {
            XmlNode sgroup = (XmlNode)__dummyForeachVar58;
            if (!StringSupport.equals(sgroup.Name, "SeriesGrouping"))
                continue;
             
            XmlNode group = DesignXmlDraw.findNextInHierarchy(sgroup,"DynamicSeries","Grouping");
            if (group == null)
                continue;
             
            String name = this.getElementAttribute(group,"Name",null);
            if (name != null)
                ar.Add(name);
             
        }
        if (ar.Count <= 0)
            return null;
         
        return ar.ToArray();
    }

    public XmlNode getChartGrouping(XmlNode chart, String gname) throws Exception {
        if (chart == null || !StringSupport.equals(chart.Name, "Chart"))
            return null;
         
        // Try the series first
        XmlNode group = new XmlNode();
        group = getChartGroupName(chart,gname,"SeriesGroupings","SeriesGrouping","DynamicSeries");
        if (group == null)
            // if not there try the row groupings
            group = getChartGroupName(chart,gname,"CategoryGroupings","CategoryGrouping","DynamicCategories");
         
        return group;
    }

    XmlNode getChartGroupName(XmlNode chart, String gname, String search1, String search2, String search3) throws Exception {
        XmlNode serGroups = this.getNamedChildNode(chart,search1);
        if (serGroups == null)
            return null;
         
        for (Object __dummyForeachVar59 : serGroups.ChildNodes)
        {
            XmlNode sgroup = (XmlNode)__dummyForeachVar59;
            if (!StringSupport.equals(sgroup.Name, search2))
                continue;
             
            XmlNode group = DesignXmlDraw.findNextInHierarchy(sgroup,search3,"Grouping");
            if (group == null)
                continue;
             
            String name = this.getElementAttribute(group,"Name",null);
            if (StringSupport.equals(name, gname))
                return group;
             
        }
        return null;
    }

    public XmlNode insertChartCategoryGrouping(XmlNode chart) throws Exception {
        if (chart == null || !StringSupport.equals(chart.Name, "Chart"))
            return null;
         
        XmlNode catGroups = this.getCreateNamedChildNode(chart,"CategoryGroupings");
        XmlElement cgrp = rDoc.CreateElement("CategoryGrouping");
        catGroups.AppendChild(cgrp);
        XmlElement dyncats = rDoc.CreateElement("DynamicCategories");
        cgrp.AppendChild(dyncats);
        XmlElement grp = rDoc.CreateElement("Grouping");
        dyncats.AppendChild(grp);
        return dyncats;
    }

    public XmlNode insertChartSeriesGrouping(XmlNode chart) throws Exception {
        if (chart == null || !StringSupport.equals(chart.Name, "Chart"))
            return null;
         
        XmlNode serGroups = this.getCreateNamedChildNode(chart,"SeriesGroupings");
        XmlElement sgrp = rDoc.CreateElement("SeriesGrouping");
        serGroups.AppendChild(sgrp);
        XmlElement dynsers = rDoc.CreateElement("DynamicSeries");
        sgrp.AppendChild(dynsers);
        XmlElement grp = rDoc.CreateElement("Grouping");
        dynsers.AppendChild(grp);
        return dynsers;
    }

    public boolean inMatrix(XmlNode node) throws Exception {
        XmlNode pNode = node.ParentNode;
        if (pNode == null || !StringSupport.equals(pNode.Name, "ReportItems"))
            return false;
         
        pNode = pNode.ParentNode;
        if (pNode == null)
            return false;
         
        Name __dummyScrutVar33 = pNode.Name;
        if (__dummyScrutVar33.equals("MatrixCell") || __dummyScrutVar33.equals("Subtotal") || __dummyScrutVar33.equals("DynamicRows") || __dummyScrutVar33.equals("DynamicColumns") || __dummyScrutVar33.equals("StaticRows") || __dummyScrutVar33.equals("StaticColumns") || __dummyScrutVar33.equals("Corner"))
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

    public XmlNode getMatrixFromReportItem(XmlNode riNode) throws Exception {
        XmlNode matrix = new XmlNode();
        for (matrix = riNode.ParentNode;matrix != null;matrix = matrix.ParentNode)
        {
            if (StringSupport.equals(matrix.Name, "Matrix"))
                break;
             
        }
        return matrix;
    }

    /**
    * Get the list of column group names given a ReportItem in a matrix
    * 
    *  @param riNode 
    *  @return
    */
    public String[] getMatrixColumnGroupNames(XmlNode riNode) throws Exception {
        XmlNode matrix = getMatrixFromReportItem(riNode);
        if (matrix == null)
            return null;
         
        XmlNode colGroups = this.getNamedChildNode(matrix,"ColumnGroupings");
        if (colGroups == null)
            return null;
         
        List<String> ar = new List<String>();
        for (Object __dummyForeachVar60 : colGroups.ChildNodes)
        {
            XmlNode cgroup = (XmlNode)__dummyForeachVar60;
            if (!StringSupport.equals(cgroup.Name, "ColumnGrouping"))
                continue;
             
            XmlNode group = DesignXmlDraw.findNextInHierarchy(cgroup,"DynamicColumns","Grouping");
            if (group == null)
                continue;
             
            String name = this.getElementAttribute(group,"Name",null);
            if (name != null)
                ar.Add(name);
             
        }
        if (ar.Count <= 0)
            return null;
         
        return ar.ToArray();
    }

    /**
    * Get the list of row group names given a ReportItem in a matrix
    * 
    *  @param riNode 
    *  @return
    */
    public String[] getMatrixRowGroupNames(XmlNode riNode) throws Exception {
        XmlNode matrix = getMatrixFromReportItem(riNode);
        if (matrix == null)
            return null;
         
        XmlNode colGroups = this.getNamedChildNode(matrix,"RowGroupings");
        if (colGroups == null)
            return null;
         
        List<String> ar = new List<String>();
        for (Object __dummyForeachVar61 : colGroups.ChildNodes)
        {
            XmlNode cgroup = (XmlNode)__dummyForeachVar61;
            if (!StringSupport.equals(cgroup.Name, "RowGrouping"))
                continue;
             
            XmlNode group = DesignXmlDraw.findNextInHierarchy(cgroup,"DynamicRows","Grouping");
            if (group == null)
                continue;
             
            String name = this.getElementAttribute(group,"Name",null);
            if (name != null)
                ar.Add(name);
             
        }
        if (ar.Count <= 0)
            return null;
         
        return ar.ToArray();
    }

    public XmlNode insertMatrixColumnGroup(XmlNode node) throws Exception {
        XmlNode matrix = this.getMatrixFromReportItem(node);
        if (matrix == null)
            return null;
         
        XmlNode colGroups = this.getCreateNamedChildNode(matrix,"ColumnGroupings");
        // ColumnGrouping in ColumnGroupings
        XmlElement cgrp = rDoc.CreateElement("ColumnGrouping");
        colGroups.AppendChild(cgrp);
        this.SetElement(cgrp, "Height", ".25in");
        XmlElement dyncols = rDoc.CreateElement("DynamicColumns");
        cgrp.AppendChild(dyncols);
        // Grouping in DynamicColumns
        XmlElement grp = rDoc.CreateElement("Grouping");
        dyncols.AppendChild(grp);
        return dyncols;
    }

    public XmlNode insertMatrixRowGroup(XmlNode node) throws Exception {
        XmlNode matrix = this.getMatrixFromReportItem(node);
        if (matrix == null)
            return null;
         
        XmlNode rowGroups = this.getCreateNamedChildNode(matrix,"RowGroupings");
        // ColumnGrouping in ColumnGroupings
        XmlElement rgrp = rDoc.CreateElement("RowGrouping");
        rowGroups.AppendChild(rgrp);
        this.SetElement(rgrp, "Width", "1in");
        XmlElement dynrows = rDoc.CreateElement("DynamicRows");
        rgrp.AppendChild(dynrows);
        // Grouping in DynamicRows
        XmlElement grp = rDoc.CreateElement("Grouping");
        dynrows.AppendChild(grp);
        return dynrows;
    }

    public boolean deleteMatrixGroup(XmlNode dynamic) throws Exception {
        XmlNode columnOrRow = dynamic.ParentNode;
        XmlNode groupings = columnOrRow.ParentNode;
        groupings.RemoveChild(columnOrRow);
        // Remove the Grouping from Groups
        if (!groupings.HasChildNodes)
        {
            // If Groups has no children
            //   remove the ColumnGroupings from Matrix
            groupings.ParentNode.RemoveChild(groupings);
        }
         
        return true;
    }

    /**
    * Delete the matrix group (ColumnGrouping or RowGrouping) given a ReportItem in a matrix and the name of the group
    * 
    *  @param riNode 
    *  @return true if the Group is deleted
    */
    public boolean deleteMatrixGroup(XmlNode riNode, String gname) throws Exception {
        XmlNode matrix = this.getMatrixFromReportItem(riNode);
        if (matrix == null)
            return false;
         
        // Try the column groupings first
        XmlNode group = new XmlNode();
        group = getMatrixColumnGroupFromName(matrix,gname);
        if (group == null)
            group = getMatrixRowGroupFromName(matrix,gname);
         
        if (group == null)
            return false;
         
        XmlNode dynamic = group.ParentNode;
        XmlNode columnOrRow = dynamic.ParentNode;
        XmlNode groupings = columnOrRow.ParentNode;
        groupings.RemoveChild(columnOrRow);
        // Remove the Grouping from Groups
        if (!groupings.HasChildNodes)
        {
            // If Groups has no children
            //   remove the TableGroups from Table
            matrix.RemoveChild(groupings);
        }
         
        return true;
    }

    public XmlNode getMatrixGroup(XmlNode riNode, String gname) throws Exception {
        XmlNode matrix = this.getMatrixFromReportItem(riNode);
        if (matrix == null)
            return null;
         
        // Try the column groupings first
        XmlNode group = new XmlNode();
        group = getMatrixColumnGroupFromName(matrix,gname);
        if (group == null)
            // if not there try the row groupings
            group = getMatrixRowGroupFromName(matrix,gname);
         
        return group;
    }

    public XmlNode getMatrixColumnGroupFromName(XmlNode matrix, String gname) throws Exception {
        XmlNode colGroups = this.getNamedChildNode(matrix,"ColumnGroupings");
        if (colGroups == null)
            return null;
         
        for (Object __dummyForeachVar62 : colGroups.ChildNodes)
        {
            XmlNode cgroup = (XmlNode)__dummyForeachVar62;
            if (!StringSupport.equals(cgroup.Name, "ColumnGrouping"))
                continue;
             
            XmlNode group = DesignXmlDraw.findNextInHierarchy(cgroup,"DynamicColumns","Grouping");
            if (group == null)
                continue;
             
            String name = this.getElementAttribute(group,"Name",null);
            if (StringSupport.equals(name, gname))
                return group;
             
        }
        return null;
    }

    public XmlNode getMatrixRowGroupFromName(XmlNode matrix, String gname) throws Exception {
        XmlNode rowGroups = this.getNamedChildNode(matrix,"RowGroupings");
        if (rowGroups == null)
            return null;
         
        for (Object __dummyForeachVar63 : rowGroups.ChildNodes)
        {
            XmlNode cgroup = (XmlNode)__dummyForeachVar63;
            if (!StringSupport.equals(cgroup.Name, "RowGrouping"))
                continue;
             
            XmlNode group = DesignXmlDraw.findNextInHierarchy(cgroup,"DynamicRows","Grouping");
            if (group == null)
                continue;
             
            String name = this.getElementAttribute(group,"Name",null);
            if (StringSupport.equals(name, gname))
                return group;
             
        }
        return null;
    }

    public XmlNode getTableFromReportItem(XmlNode riNode) throws Exception {
        XmlNode table = new XmlNode();
        for (table = riNode.ParentNode;table != null;table = table.ParentNode)
        {
            if (StringSupport.equals(table.Name, "Table"))
                break;
             
        }
        return table;
    }

    /**
    * Return TableCell that contains the specified reportitem
    * 
    *  @param node ReportItem in a table row
    *  @return null if not found
    */
    public XmlNode getTableCell(XmlNode node) throws Exception {
        // find the table cell
        XmlNode tcNode = new XmlNode();
        for (tcNode = node.ParentNode;tcNode != null;tcNode = tcNode.ParentNode)
        {
            if (StringSupport.equals(tcNode.Name, "TableCell"))
                break;
             
        }
        return tcNode;
    }

    /**
    * Return Table column that contains the specified reportitem
    * 
    *  @param node ReportItem in a table row
    *  @return null if not found
    */
    public XmlNode getTableColumn(XmlNode node) throws Exception {
        // find the table cell
        XmlNode tcNode = getTableCell(node);
        if (tcNode == null)
            return null;
         
        // Get the table
        XmlNode table = new XmlNode();
        for (table = tcNode.ParentNode;table != null;table = table.ParentNode)
        {
            if (StringSupport.equals(table.Name, "Table"))
                break;
             
        }
        if (table == null)
            return null;
         
        int col = getTableColumnNumber(tcNode);
        XmlNode tcs = this.getNamedChildNode(table,"TableColumns");
        if (tcs == null)
            return null;
         
        XmlNode savetc = null;
        for (Object __dummyForeachVar64 : tcs.ChildNodes)
        {
            XmlNode tc = (XmlNode)__dummyForeachVar64;
            if (!StringSupport.equals(tc.Name, "TableColumn"))
                continue;
             
            if (col < 1)
            {
                savetc = tc;
                break;
            }
             
            col--;
        }
        return savetc;
    }

    /**
    * Return TableRow that contains the specified reportitem
    * 
    *  @param node ReportItem in a table row
    *  @return null if not found
    */
    public XmlNode getTableRow(XmlNode node) throws Exception {
        // find the tablerow
        XmlNode trNode = new XmlNode();
        for (trNode = node.ParentNode;trNode != null;trNode = trNode.ParentNode)
        {
            if (StringSupport.equals(trNode.Name, "TableRow"))
                break;
             
        }
        return trNode;
    }

    /**
    * Delete the Table column that contains the specified reportitem
    * 
    *  @param node ReportItem in a table row
    *  @return true if deleted
    */
    public boolean deleteTableColumn(XmlNode node) throws Exception {
        // find the table cell
        XmlNode tcNode = new XmlNode();
        for (tcNode = node.ParentNode;tcNode != null;tcNode = tcNode.ParentNode)
        {
            if (StringSupport.equals(tcNode.Name, "TableCell"))
                break;
             
        }
        if (tcNode == null)
            return false;
         
        // Get the table
        XmlNode table = new XmlNode();
        for (table = tcNode.ParentNode;table != null;table = table.ParentNode)
        {
            if (StringSupport.equals(table.Name, "Table"))
                break;
             
        }
        if (table == null)
            return false;
         
        if (getTableColumnCount(table) <= 1)
        {
            // We're deleting the last column?
            this.deleteReportItem(table);
            return true;
        }
         
        // yes; just get rid of the table
        // calculate the column number of this node
        int col = getTableColumnNumber(tcNode);
        deleteTableColumn(this.getNamedChildNode(table,"TableColumns"),col);
        deleteTableColumn(DesignXmlDraw.findNextInHierarchy(table,"Header","TableRows"),col);
        deleteTableColumn(DesignXmlDraw.findNextInHierarchy(table,"Details","TableRows"),col);
        deleteTableColumn(DesignXmlDraw.findNextInHierarchy(table,"Footer","TableRows"),col);
        XmlNode tGroups = this.getNamedChildNode(table,"TableGroups");
        if (tGroups == null)
            return true;
         
        for (Object __dummyForeachVar65 : tGroups.ChildNodes)
        {
            // run thru the table groups
            XmlNode tgrp = (XmlNode)__dummyForeachVar65;
            if (!StringSupport.equals(tgrp.Name, "TableGroup"))
                continue;
             
            deleteTableColumn(DesignXmlDraw.findNextInHierarchy(tgrp,"Header","TableRows"),col);
            deleteTableColumn(DesignXmlDraw.findNextInHierarchy(tgrp,"Footer","TableRows"),col);
        }
        return true;
    }

    private void deleteTableColumn(XmlNode sNode, int col) throws Exception {
        if (sNode == null)
            return ;
         
        if (StringSupport.equals(sNode.Name, "TableRows"))
        {
            for (Object __dummyForeachVar66 : sNode.ChildNodes)
            {
                // Loop thru all the tablerows to get at the TableCells
                XmlNode tr = (XmlNode)__dummyForeachVar66;
                if (StringSupport.equals(tr.Name, "TableRow"))
                    deleteTableColumn(this.getNamedChildNode(tr,"TableCells"),col);
                 
            }
            return ;
        }
         
        // We have either TableCells or TableColumns
        String name = "TableCell";
        if (StringSupport.equals(sNode.Name, "TableColumns"))
            name = "TableColumn";
         
        XmlNode del = null;
        for (Object __dummyForeachVar67 : sNode.ChildNodes)
        {
            XmlNode cNode = (XmlNode)__dummyForeachVar67;
            if (!StringSupport.equals(cNode.Name, name))
                continue;
             
            int colSpan = 1;
            if (StringSupport.equals(name, "TableCell"))
                colSpan = Convert.ToInt32(getElementValue(cNode,"ColSpan","1"));
             
            if (col - colSpan < 0)
            {
                if (colSpan == 1)
                    del = cNode;
                else
                    this.SetElement(cNode, "ColSpan", (colSpan - 1).ToString()); 
                break;
            }
             
            col -= colSpan;
        }
        if (del == null)
            return ;
         
        sNode.RemoveChild(del);
        return ;
    }

    private int getTableColumnNumber(XmlNode tcell) throws Exception {
        int col = 0;
        XmlNode tcells = tcell.ParentNode;
        for (Object __dummyForeachVar68 : tcells.ChildNodes)
        {
            XmlNode cell = (XmlNode)__dummyForeachVar68;
            if (!StringSupport.equals(cell.Name, "TableCell"))
                continue;
             
            if (cell == tcell)
                break;
             
            int colSpan = Convert.ToInt32(getElementValue(cell,"ColSpan","1"));
            col += colSpan;
        }
        return col;
    }

    /**
    * Delete the TableRow that contains the specified reportitem
    * 
    *  @param node ReportItem in a table row
    *  @return true if deleted
    */
    public boolean deleteTableRow(XmlNode node) throws Exception {
        XmlNode trNode = new XmlNode();
        for (trNode = node.ParentNode;trNode != null;trNode = trNode.ParentNode)
        {
            if (StringSupport.equals(trNode.Name, "TableRow"))
                break;
             
        }
        if (trNode == null)
            return false;
         
        XmlNode trows = trNode.ParentNode;
        trows.RemoveChild(trNode);
        // If that was the last TableRow in TableRows we need to delete TableRows as well
        if (this.getNamedChildNode(trows,"TableRow") != null)
            return true;
         
        // we have another tablerow in this section
        XmlNode section = trows.ParentNode;
        //  get the TableRows parent
        Name __dummyScrutVar34 = section.Name;
        if (__dummyScrutVar34.equals("Footer") || __dummyScrutVar34.equals("Details") || __dummyScrutVar34.equals("Header"))
        {
        }
        else
        {
            return true;
        } 
        // anything other than footer, details, header doesn't require tablerows
        XmlNode table = section.ParentNode;
        // get the parent
        table.RemoveChild(section);
        if (this.getNamedChildNode(table,"Details") != null || this.getNamedChildNode(table,"Footer") != null || this.getNamedChildNode(table,"Header") != null)
            return true;
         
        // Also need to delete the table since no header, footer or detail
        this.deleteReportItem(table);
        return true;
    }

    /**
    * Delete the table group given a ReportItem in a table and the name of the group
    * 
    *  @param riNode 
    *  @return true if the TableGroup is deleted
    */
    public boolean deleteTableGroup(XmlNode riNode, String gname) throws Exception {
        XmlNode table = this.getTableFromReportItem(riNode);
        if (table == null)
            return false;
         
        XmlNode tblGroups = this.getNamedChildNode(table,"TableGroups");
        if (tblGroups == null)
            return false;
         
        XmlNode tblGroup = null;
        for (Object __dummyForeachVar69 : tblGroups.ChildNodes)
        {
            XmlNode tgroup = (XmlNode)__dummyForeachVar69;
            if (!StringSupport.equals(tgroup.Name, "TableGroup"))
                continue;
             
            XmlNode group = this.getNamedChildNode(tgroup,"Grouping");
            if (group == null)
                continue;
             
            String name = this.getElementAttribute(group,"Name",null);
            if (StringSupport.equals(name, gname))
            {
                tblGroup = tgroup;
                break;
            }
             
        }
        if (tblGroup == null)
            return false;
         
        tblGroups.RemoveChild(tblGroup);
        // Remove the TableGroup from TableGroups
        if (!tblGroups.HasChildNodes)
        {
            // If TableGroups has no children
            //   remove the TableGroups from Table
            table.RemoveChild(tblGroups);
        }
         
        return true;
    }

    /**
    * Get the XmlNode of the table group given a ReportItem in a table and the name of the group
    * 
    *  @param riNode 
    *  @return
    */
    public XmlNode getTableGroup(XmlNode riNode, String gname) throws Exception {
        XmlNode table = new XmlNode();
        for (table = riNode.ParentNode;table != null;table = table.ParentNode)
        {
            if (StringSupport.equals(table.Name, "Table"))
                break;
             
        }
        if (table == null)
            return null;
         
        XmlNode tblGroups = this.getNamedChildNode(table,"TableGroups");
        if (tblGroups == null)
            return null;
         
        for (Object __dummyForeachVar70 : tblGroups.ChildNodes)
        {
            XmlNode tgroup = (XmlNode)__dummyForeachVar70;
            if (!StringSupport.equals(tgroup.Name, "TableGroup"))
                continue;
             
            XmlNode group = this.getNamedChildNode(tgroup,"Grouping");
            if (group == null)
                continue;
             
            String name = this.getElementAttribute(group,"Name",null);
            if (StringSupport.equals(name, gname))
                return tgroup;
             
        }
        return null;
    }

    /**
    * Get the list of table group names given a ReportItem in a table
    * 
    *  @param riNode 
    *  @return
    */
    public String[] getTableGroupNames(XmlNode riNode) throws Exception {
        XmlNode table = new XmlNode();
        for (table = riNode.ParentNode;table != null;table = table.ParentNode)
        {
            if (StringSupport.equals(table.Name, "Table"))
                break;
             
        }
        if (table == null)
            return null;
         
        XmlNode tblGroups = this.getNamedChildNode(table,"TableGroups");
        if (tblGroups == null)
            return null;
         
        List<String> ar = new List<String>();
        for (Object __dummyForeachVar71 : tblGroups.ChildNodes)
        {
            XmlNode tgroup = (XmlNode)__dummyForeachVar71;
            if (!StringSupport.equals(tgroup.Name, "TableGroup"))
                continue;
             
            XmlNode group = this.getNamedChildNode(tgroup,"Grouping");
            if (group == null)
                continue;
             
            String name = this.getElementAttribute(group,"Name",null);
            if (name != null)
                ar.Add(name);
             
        }
        if (ar.Count <= 0)
            return null;
         
        return ar.ToArray();
    }

    /**
    * Insert a table column before or after the column containing the specified ReportItem
    * 
    *  @param node The reportitem node
    *  @param before If true row is inserted before this column otherwise it will go after.
    *  @return true if the column was inserted
    */
    public boolean insertTableColumn(XmlNode node, boolean before) throws Exception {
        // find the table cell
        XmlNode tcNode = new XmlNode();
        for (tcNode = node.ParentNode;tcNode != null;tcNode = tcNode.ParentNode)
        {
            if (StringSupport.equals(tcNode.Name, "TableCell"))
                break;
             
        }
        if (tcNode == null)
            return false;
         
        // Get the table
        XmlNode table = new XmlNode();
        for (table = tcNode.ParentNode;table != null;table = table.ParentNode)
        {
            if (StringSupport.equals(table.Name, "Table"))
                break;
             
        }
        if (table == null)
            return false;
         
        // Get the table column
        XmlNode refCol = null;
        int col = getTableColumnNumber(tcNode);
        int ci = 0;
        XmlNode tableColumns = this.getNamedChildNode(table,"TableColumns");
        for (Object __dummyForeachVar72 : tableColumns.ChildNodes)
        {
            XmlNode tbCol = (XmlNode)__dummyForeachVar72;
            if (!StringSupport.equals(tbCol.Name, "TableColumn"))
                continue;
             
            if (ci == col)
            {
                refCol = tbCol;
                break;
            }
             
            ci++;
        }
        if (refCol == null)
            return false;
         
        // insert the tablecolumn
        XmlElement newcol = rDoc.CreateElement("TableColumn");
        if (before)
            tableColumns.InsertBefore(newcol, refCol);
        else
            tableColumns.InsertAfter(newcol, refCol); 
        this.SetElement(newcol, "Width", this.getElementValue(refCol,"Width","1in"));
        insertTableColumn(DesignXmlDraw.findNextInHierarchy(table,"Header","TableRows"),col,before);
        insertTableColumn(DesignXmlDraw.findNextInHierarchy(table,"Details","TableRows"),col,before);
        insertTableColumn(DesignXmlDraw.findNextInHierarchy(table,"Footer","TableRows"),col,before);
        XmlNode tGroups = this.getNamedChildNode(table,"TableGroups");
        if (tGroups == null)
            return true;
         
        for (Object __dummyForeachVar73 : tGroups.ChildNodes)
        {
            // run thru the table groups
            XmlNode tgrp = (XmlNode)__dummyForeachVar73;
            if (!StringSupport.equals(tgrp.Name, "TableGroup"))
                continue;
             
            insertTableColumn(DesignXmlDraw.findNextInHierarchy(tgrp,"Header","TableRows"),col,before);
            insertTableColumn(DesignXmlDraw.findNextInHierarchy(tgrp,"Footer","TableRows"),col,before);
        }
        return true;
    }

    private void insertTableColumn(XmlNode tcells, int col, boolean before) throws Exception {
        if (tcells == null)
            return ;
         
        if (StringSupport.equals(tcells.Name, "TableRows"))
        {
            for (Object __dummyForeachVar74 : tcells.ChildNodes)
            {
                // Loop thru all the tablerows to get at the TableCells
                XmlNode tr = (XmlNode)__dummyForeachVar74;
                if (StringSupport.equals(tr.Name, "TableRow"))
                    insertTableColumn(this.getNamedChildNode(tr,"TableCells"),col,before);
                 
            }
            return ;
        }
         
        // We have TableCells
        XmlNode refCell = null;
        for (Object __dummyForeachVar75 : tcells.ChildNodes)
        {
            XmlNode cNode = (XmlNode)__dummyForeachVar75;
            if (!StringSupport.equals(cNode.Name, "TableCell"))
                continue;
             
            int colSpan = Convert.ToInt32(getElementValue(cNode,"ColSpan","1"));
            if (col - colSpan < 0)
            {
                if (colSpan == 1 || (col == 0 && before))
                    // insert new column if very first and before
                    refCell = cNode;
                else
                    //   or if no colSpan has been requested
                    this.SetElement(cNode, "ColSpan", (colSpan + 1).ToString()); 
                break;
            }
             
            col -= colSpan;
        }
        if (refCell == null)
            return ;
         
        insertTableColumn(tcells,refCell,before);
    }

    private void insertTableColumn(XmlNode tcells, XmlNode refCell, boolean before) throws Exception {
        // insert the TableCell
        XmlElement ntcell = rDoc.CreateElement("TableCell");
        if (before)
            tcells.InsertBefore(ntcell, refCell);
        else
            tcells.InsertAfter(ntcell, refCell); 
        // ReportItems in TableCell
        XmlElement ris = rDoc.CreateElement("ReportItems");
        ntcell.AppendChild(ris);
        // TextBox in ReportItems
        XmlElement tbox = rDoc.CreateElement("Textbox");
        getReportNames().GenerateName(tbox);
        ris.AppendChild(tbox);
        XmlElement vnode = rDoc.CreateElement("Value");
        vnode.InnerText = "";
        tbox.AppendChild(vnode);
        // Copy style info if refCell contains a Textbox
        XmlNode styleNode = DesignXmlDraw.findNextInHierarchy(refCell,"ReportItems","Textbox","Style");
        if (styleNode != null)
            tbox.AppendChild(styleNode.CloneNode(true));
         
        return ;
    }

    /**
    * Creates a new TableGroup and put it on the end of the chain
    * 
    *  @param node ReportItem this is contained in the table
    *  @return XmlNode of the new TableGroup
    */
    public XmlNode insertTableGroup(XmlNode node) throws Exception {
        XmlNode table = this.getTableFromReportItem(node);
        if (table == null)
            return null;
         
        XmlNode tblGroups = this.getCreateNamedChildNode(table,"TableGroups");
        // TableGroup in TableGroups
        XmlElement tgrp = rDoc.CreateElement("TableGroup");
        tblGroups.AppendChild(tgrp);
        // Grouping in TableGroup
        XmlElement grp = rDoc.CreateElement("Grouping");
        tgrp.AppendChild(grp);
        return tgrp;
    }

    /**
    * Deletes the passed TableGroup; if last TableGroup in TableGroups then TableGroups also deleted
    * 
    *  @param node XmlNode of TableGroup
    */
    public void deleteTableGroup(XmlNode tgrp) throws Exception {
        if (tgrp == null || !StringSupport.equals(tgrp.Name, "TableGroup"))
            return ;
         
        // make sure we have valid arguments
        XmlNode tblGroups = tgrp.ParentNode;
        tblGroups.RemoveChild(tgrp);
        if (this.getNamedChildNode(tblGroups,"TableGroup") == null)
        {
            // this was the last tablegroup
            XmlNode table = tblGroups.ParentNode;
            table.RemoveChild(tblGroups);
        }
         
    }

    /**
    * Insert a table row before or after the TableRow containing the specified ReportItem
    * 
    *  @param node The reportitem node
    *  @param before If true row is inserted before this TableRow otherwise it will go after.
    *  @return true if the TableRow was inserted
    */
    public boolean insertTableRow(XmlNode node, boolean before) throws Exception {
        XmlNode trNode = new XmlNode();
        for (trNode = node.ParentNode;trNode != null;trNode = trNode.ParentNode)
        {
            if (StringSupport.equals(trNode.Name, "TableRow"))
                break;
             
        }
        if (trNode == null)
            return false;
         
        XmlNode tcells = this.getNamedChildNode(trNode,"TableCells");
        if (tcells == null)
            return false;
         
        XmlNode trows = trNode.ParentNode;
        XmlElement newrow = rDoc.CreateElement("TableRow");
        if (before)
            trows.InsertBefore(newrow, trNode);
        else
            trows.InsertAfter(newrow, trNode); 
        XmlElement height = rDoc.CreateElement("Height");
        // use same height as reference row if possible
        XmlNode cheight = this.getNamedChildNode(trNode,"Height");
        if (cheight != null)
            height.InnerText = cheight.InnerText;
        else
            height.InnerText = ".25in"; 
        newrow.AppendChild(height);
        XmlElement tablecells = rDoc.CreateElement("TableCells");
        newrow.AppendChild(tablecells);
        for (Object __dummyForeachVar76 : tcells.ChildNodes)
        {
            // loop thru the TableCell children of the reference tablerow;
            //  create a textbox for each column
            XmlNode tcell = (XmlNode)__dummyForeachVar76;
            if (!StringSupport.equals(tcell.Name, "TableCell"))
                continue;
             
            XmlElement ntcell = rDoc.CreateElement("TableCell");
            tablecells.AppendChild(ntcell);
            XmlElement ris = rDoc.CreateElement("ReportItems");
            ntcell.AppendChild(ris);
            // Need to create a column for each spaned column
            int colSpan = Convert.ToInt32(getElementValue(tcell,"ColSpan","1"));
            XmlNode styleNode = DesignXmlDraw.findNextInHierarchy(tcell,"ReportItems","Textbox","Style");
            for (int ci = 0;ci < colSpan;ci++)
            {
                XmlElement tbox = rDoc.CreateElement("Textbox");
                getReportNames().GenerateName(tbox);
                ris.AppendChild(tbox);
                XmlElement vnode = rDoc.CreateElement("Value");
                vnode.InnerText = "";
                tbox.AppendChild(vnode);
                if (styleNode != null)
                    tbox.AppendChild(styleNode.CloneNode(true));
                 
            }
        }
        return true;
    }

    /**
    * Insert a table row given a TableRows; uses TableColumns to create proper number of rows
    * 
    *  @param node The reportitem node
    *  @return true if the TableRow was inserted
    */
    public boolean insertTableRow(XmlNode tblRows) throws Exception {
        if (tblRows == null)
            return false;
         
        XmlNode table = new XmlNode();
        for (table = tblRows.ParentNode;table != null;table = table.ParentNode)
        {
            if (StringSupport.equals(table.Name, "Table"))
                break;
             
        }
        if (table == null)
            return false;
         
        XmlNode columns = this.getNamedChildNode(table,"TableColumns");
        if (columns == null)
            return false;
         
        XmlElement newrow = this.createElement(tblRows,"TableRow",null);
        this.CreateElement(newrow, "Height", ".2in");
        XmlElement tablecells = this.CreateElement(newrow, "TableCells", null);
        for (Object __dummyForeachVar77 : columns.ChildNodes)
        {
            // loop thru the TableColumns children
            //  create a textbox for each column
            XmlNode col = (XmlNode)__dummyForeachVar77;
            if (!StringSupport.equals(col.Name, "TableColumn"))
                continue;
             
            XmlElement ntcell = this.CreateElement(tablecells, "TableCell", null);
            XmlElement ris = this.CreateElement(ntcell, "ReportItems", null);
            XmlElement tbox = this.CreateElement(ris, "Textbox", null);
            getReportNames().GenerateName(tbox);
            XmlElement style = this.CreateElement(tbox, "Style", null);
            XmlElement bstyle = this.CreateElement(style, "BorderStyle", null);
            this.SetElement(bstyle, "Default", "Solid");
            this.SetElement(tbox, "Value", "");
        }
        return true;
    }

    public boolean inPageHeaderOrFooter(XmlNode node) throws Exception {
        for (XmlNode p = node;p != null;p = p.ParentNode)
        {
            if (StringSupport.equals(node.Name, "PageHeader") || StringSupport.equals(node.Name, "PageFooter"))
                return true;
             
        }
        return false;
    }

    /**
    * Checks to see if node is part of a table
    * 
    *  @param node Usually a ReportItem
    *  @return true if part of a table
    */
    public boolean inTable(XmlNode node) throws Exception {
        XmlNode pNode = node.ParentNode;
        if (pNode == null || !StringSupport.equals(pNode.Name, "ReportItems"))
            return false;
         
        pNode = pNode.ParentNode;
        if (pNode == null || !StringSupport.equals(pNode.Name, "TableCell"))
            return false;
        else
            return true; 
    }

    /**
    * Returns the node of the parent table (or matrix) or null
    * 
    *  @param node 
    *  @return
    */
    private XmlNode tMParent(XmlNode node) throws Exception {
        for (XmlNode pNode = node.ParentNode;pNode != null;pNode = pNode.ParentNode)
        {
            if (StringSupport.equals(pNode.Name, "Table") || StringSupport.equals(pNode.Name, "Matrix"))
                return pNode;
             
        }
        return null;
    }

    private int pixelsX(float x) throws Exception {
        return (int)(x * DpiX / POINTSIZED);
    }

    // points to pixels
    private int pixelsY(float y) throws Exception {
        return (int)(y * DpiY / POINTSIZED);
    }

    private float pointsX(float x) throws Exception {
        return x * POINTSIZED / DpiX;
    }

    // pixels to points
    private float pointsY(float y) throws Exception {
        return y * POINTSIZED / DpiY;
    }

}


