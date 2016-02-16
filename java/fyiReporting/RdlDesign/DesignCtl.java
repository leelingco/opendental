//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:16 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.ICustomReportItem;
import fyiReporting.RDL.RdlEngineConfig;
import fyiReporting.RDL.Rectangle;
import fyiReporting.RDL.StyleInfo;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.DialogNewChart;
import fyiReporting.RdlDesign.DialogNewMatrix;
import fyiReporting.RdlDesign.DialogNewTable;
import fyiReporting.RdlDesign.HeightEventArgs;
import fyiReporting.RdlDesign.HitLocation;
import fyiReporting.RdlDesign.HitLocationEnum;
import fyiReporting.RdlDesign.PropertyDialog;
import fyiReporting.RdlDesign.PropertyTypeEnum;
import fyiReporting.RdlDesign.SubReportEventArgs;
import fyiReporting.RdlDesign.Undo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
* DesignCtl is a designer view of an RDL report
*/
public class DesignCtl  extends System.Windows.Forms.Control 
{
    public static class __MultiOpenSubreportEventHandler   implements OpenSubreportEventHandler
    {
        public void invoke(Object source, SubReportEventArgs e) throws Exception {
            IList<OpenSubreportEventHandler> copy = new IList<OpenSubreportEventHandler>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<OpenSubreportEventHandler>(members);
            }
            for (Object __dummyForeachVar0 : copy)
            {
                OpenSubreportEventHandler d = (OpenSubreportEventHandler)__dummyForeachVar0;
                d.invoke(source, e);
            }
        }

        private System.Collections.Generic.IList<OpenSubreportEventHandler> _invocationList = new ArrayList<OpenSubreportEventHandler>();
        public static OpenSubreportEventHandler combine(OpenSubreportEventHandler a, OpenSubreportEventHandler b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiOpenSubreportEventHandler ret = new __MultiOpenSubreportEventHandler();
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static OpenSubreportEventHandler remove(OpenSubreportEventHandler a, OpenSubreportEventHandler b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<OpenSubreportEventHandler> aInvList = a.getInvocationList();
            // to prevent recursively invoking paint
            // Scrollbars
            System.Collections.Generic.IList<OpenSubreportEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                // the xml document we're editting
                //  the undo object tied to the _ReportDoc;
                __MultiOpenSubreportEventHandler ret = new __MultiOpenSubreportEventHandler();
                // current object to insert; null if none
                // Mouse control
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        // XmlNode affected by the mouse down event
        // hit location affected by the mouse down event
        // position of the mouse
        public System.Collections.Generic.IList<OpenSubreportEventHandler> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    public static interface OpenSubreportEventHandler   
    {
        void invoke(Object source, SubReportEventArgs e) throws Exception ;

        System.Collections.Generic.IList<OpenSubreportEventHandler> getInvocationList() throws Exception ;
    
    }

    public static class __MultiHeightEventHandler   implements HeightEventHandler
    {
        public void invoke(Object source, HeightEventArgs e) throws Exception {
            IList<HeightEventHandler> copy = new IList<HeightEventHandler>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<HeightEventHandler>(members);
            }
            for (Object __dummyForeachVar1 : copy)
            {
                HeightEventHandler d = (HeightEventHandler)__dummyForeachVar1;
                d.invoke(source, e);
            }
        }

        private System.Collections.Generic.IList<HeightEventHandler> _invocationList = new ArrayList<HeightEventHandler>();
        public static HeightEventHandler combine(HeightEventHandler a, HeightEventHandler b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiHeightEventHandler ret = new __MultiHeightEventHandler();
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static HeightEventHandler remove(HeightEventHandler a, HeightEventHandler b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<HeightEventHandler> aInvList = a.getInvocationList();
            System.Collections.Generic.IList<HeightEventHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                __MultiHeightEventHandler ret = new __MultiHeightEventHandler();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        public System.Collections.Generic.IList<HeightEventHandler> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    public static interface HeightEventHandler   
    {
        void invoke(Object source, HeightEventArgs e) throws Exception ;

        System.Collections.Generic.IList<HeightEventHandler> getInvocationList() throws Exception ;
    
    }

    public System.EventHandler ReportChanged = new System.EventHandler();
    public System.EventHandler SelectionChanged = new System.EventHandler();
    public System.EventHandler SelectionMoved = new System.EventHandler();
    public System.EventHandler ReportItemInserted = new System.EventHandler();
    public OpenSubreportEventHandler OpenSubreport;
    public HeightEventHandler HeightChanged;
    boolean _InPaint = new boolean();
    private VScrollBar _vScroll = new VScrollBar();
    private HScrollBar _hScroll = new HScrollBar();
    private float _DpiX = new float();
    private float _DpiY = new float();
    private XmlDocument _ReportDoc = new XmlDocument();
    private Undo _Undo;
    private String _CurrentInsert = new String();
    private XmlNode _MouseDownNode = new XmlNode();
    private HitLocationEnum _MouseDownLoc = HitLocationEnum.Inside;
    private Point _MousePosition = new Point();
    private Point _ptRBOriginal = new Point();
    // starting position of the mouse (rubber banding)
    private Point _ptRBLast = new Point();
    //   last position of mouse (rubber banding)
    private boolean _bHaveMouse = new boolean();
    // flag indicates we're rubber banding
    private boolean _AdjustScroll = false;
    // when adjusting band height we may need to adjust scroll bars
    private DesignXmlDraw _DrawPanel;
    // the main drawing panel
    // Context menus
    MenuItem menuCopy = new MenuItem();
    MenuItem menuPaste = new MenuItem();
    MenuItem menuDelete = new MenuItem();
    MenuItem menuFSep1 = new MenuItem();
    MenuItem menuSelectAll = new MenuItem();
    MenuItem menuFSep2 = new MenuItem();
    MenuItem menuInsert = new MenuItem();
    MenuItem menuProperties = new MenuItem();
    ContextMenu menuContext = new ContextMenu();
    MenuItem menuPropertiesLegend = new MenuItem();
    MenuItem menuPropertiesCategoryAxis = new MenuItem();
    MenuItem menuPropertiesValueAxis = new MenuItem();
    MenuItem menuPropertiesCategoryAxisTitle = new MenuItem();
    MenuItem menuPropertiesValueAxisTitle = new MenuItem();
    MenuItem menuPropertiesChartTitle = new MenuItem();
    public DesignCtl() throws Exception {
        // Get our graphics DPI
        Graphics g = null;
        try
        {
            g = this.CreateGraphics();
            _DpiX = g.DpiX;
            _DpiY = g.DpiY;
        }
        catch (Exception __dummyCatchVar0)
        {
            _DpiX = _DpiY = 96;
        }
        finally
        {
            if (g != null)
                g.Dispose();
             
        }
        // Handle the controls
        _vScroll = new VScrollBar();
        _vScroll.Scroll += new ScrollEventHandler(this.VerticalScroll);
        _vScroll.Enabled = false;
        _hScroll = new HScrollBar();
        _hScroll.Scroll += new ScrollEventHandler(this.HorizontalScroll);
        _hScroll.Enabled = false;
        _DrawPanel = new DesignXmlDraw();
        _DrawPanel.Paint += new PaintEventHandler(this.DrawPanelPaint);
        _DrawPanel.MouseUp += new MouseEventHandler(this.DrawPanelMouseUp);
        _DrawPanel.MouseDown += new MouseEventHandler(this.DrawPanelMouseDown);
        _DrawPanel.Resize += new EventHandler(this.DrawPanelResize);
        _DrawPanel.MouseWheel += new MouseEventHandler(DrawPanelMouseWheel);
        _DrawPanel.KeyDown += new KeyEventHandler(DrawPanelKeyDown);
        _DrawPanel.MouseMove += new MouseEventHandler(DrawPanelMouseMove);
        _DrawPanel.DoubleClick += new EventHandler(DrawPanelDoubleClick);
        this.Layout += new LayoutEventHandler(DesignCtl_Layout);
        this.SuspendLayout();
        // Must be added in this order for DockStyle to work correctly
        this.Controls.Add(_DrawPanel);
        this.Controls.Add(_vScroll);
        this.Controls.Add(_hScroll);
        this.ResumeLayout(false);
        buildContextMenus();
    }

    public String getCurrentInsert() throws Exception {
        return _CurrentInsert;
    }

    public void setCurrentInsert(String value) throws Exception {
        _CurrentInsert = value;
    }

    public XmlDocument getReportDocument() throws Exception {
        return _ReportDoc;
    }

    public void setReportDocument(XmlDocument value) throws Exception {
        _ReportDoc = value;
        if (_ReportDoc != null)
        {
            _Undo = new Undo(_ReportDoc,300);
            _Undo.setGroupsOnly(true);
        }
         
        // don't record changes that we don't group.
        int selCount = _DrawPanel.getSelectedCount();
        this._DrawPanel.setReportDocument(_ReportDoc);
        if (selCount > 0)
            // changing report document forces change to selection
            SelectionChanged(this, new EventArgs());
         
    }

    public DesignXmlDraw getDrawCtl() throws Exception {
        return _DrawPanel;
    }

    public String getReportSource() throws Exception {
        if (_ReportDoc == null)
            return null;
         
        String result = "";
        try
        {
            // Convert the document into a string
            StringWriter sw = new StringWriter();
            XmlTextWriter xtw = new XmlTextWriter(sw);
            xtw.IndentChar = ' ';
            xtw.Indentation = 2;
            xtw.Formatting = Formatting.Indented;
            _ReportDoc.WriteContentTo(xtw);
            xtw.Close();
            sw.Close();
            result = sw.ToString();
            result = result.Replace("xmlns=\"\"", "");
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message, "Unable to create RDL syntax");
        }

        return result;
    }

    public void setReportSource(String value) throws Exception {
        if (value == null || StringSupport.equals(value, ""))
        {
            setReportDocument(null);
            return ;
        }
         
        XmlDocument xDoc = new XmlDocument();
        xDoc.PreserveWhitespace = false;
        xDoc.LoadXml(value);
        // this will throw an exception if invalid XML
        setReportDocument(xDoc);
    }

    public void clearUndo() throws Exception {
        _Undo.reset();
    }

    public void undo() throws Exception {
        _Undo.undo();
        _DrawPanel.setReportNames(null);
        // may not be required; but if reportitem deleted/inserted it must be
        // determine if any of the selected nodes has been affected
        boolean clearSelect = false;
        for (Object __dummyForeachVar2 : _DrawPanel.getSelectedList())
        {
            XmlNode n = (XmlNode)__dummyForeachVar2;
            // this is an imperfect test but it shows if the node has been unchained.
            if (n.ParentNode == null)
            {
                clearSelect = true;
                break;
            }
             
        }
        if (clearSelect)
            _DrawPanel.getSelectedList().Clear();
         
        _DrawPanel.Invalidate();
    }

    public boolean getShowReportItemOutline() throws Exception {
        return _DrawPanel.getShowReportItemOutline();
    }

    public void setShowReportItemOutline(boolean value) throws Exception {
        _DrawPanel.setShowReportItemOutline(value);
    }

    public void redo() throws Exception {
    }

    public String getUndoDescription() throws Exception {
        return _Undo.getDescription();
    }

    public boolean getCanUndo() throws Exception {
        return _Undo.getCanUndo();
    }

    public void startUndoGroup(String description) throws Exception {
        _Undo.StartUndoGroup(description);
    }

    public void endUndoGroup(boolean keepChanges) throws Exception {
        _Undo.endUndoGroup(keepChanges);
    }

    public void alignLefts() throws Exception {
        XmlNode model = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        XmlNode l = _DrawPanel.getNamedChildNode(model,"Left");
        String left = l == null ? "0pt" : l.InnerText;
        _Undo.StartUndoGroup("Align");
        for (Object __dummyForeachVar3 : _DrawPanel.getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar3;
            // we even reset the first one; in case the attribute wasn't specified
            _DrawPanel.setElement(xNode,"Left",left);
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    public void alignRights() throws Exception {
        XmlNode model = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        RectangleF mrect = _DrawPanel.getReportItemRect(model);
        // size attributes in points
        if (mrect.Width == float.MinValue)
            return ;
         
        // model doesn't have width specified
        float mright = mrect.Left + mrect.Width;
        // the right side of the model
        _Undo.StartUndoGroup("Align");
        for (Object __dummyForeachVar4 : _DrawPanel.getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar4;
            if (xNode == model)
                continue;
             
            RectangleF nrect = _DrawPanel.getReportItemRect(xNode);
            if (nrect.Width == float.MinValue)
                continue;
             
            float nleft = mright - nrect.Width;
            if (nleft < 0)
                nleft = 0;
             
            String left = String.Format("{0:0.00}pt", nleft);
            _DrawPanel.setElement(xNode,"Left",left);
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    public void alignCenters() throws Exception {
        XmlNode model = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        RectangleF mrect = _DrawPanel.getReportItemRect(model);
        // size attributes in points
        if (mrect.Width == float.MinValue)
            return ;
         
        // model doesn't have width specified
        float mc = mrect.Left + mrect.Width / 2;
        // the middle of the model
        _Undo.StartUndoGroup("Align");
        for (Object __dummyForeachVar5 : _DrawPanel.getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar5;
            if (xNode == model)
                continue;
             
            RectangleF nrect = _DrawPanel.getReportItemRect(xNode);
            if (nrect.Width == float.MinValue)
                continue;
             
            float nleft = (mc - (nrect.Left + nrect.Width / 2));
            nleft += nrect.Left;
            if (nleft < 0)
                nleft = 0;
             
            String left = String.Format("{0:0.00}pt", nleft);
            _DrawPanel.setElement(xNode,"Left",left);
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    public void alignTops() throws Exception {
        XmlNode model = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        XmlNode t = _DrawPanel.getNamedChildNode(model,"Top");
        String top = t == null ? "0pt" : t.InnerText;
        _Undo.StartUndoGroup("Align");
        for (Object __dummyForeachVar6 : _DrawPanel.getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar6;
            // we even reset the first one; in case the attribute wasn't specified
            _DrawPanel.setElement(xNode,"Top",top);
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    public void alignBottoms() throws Exception {
        XmlNode model = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        RectangleF mrect = _DrawPanel.getReportItemRect(model);
        // size attributes in points
        if (mrect.Height == float.MinValue)
            return ;
         
        // model doesn't have height specified
        float mbottom = mrect.Top + mrect.Height;
        // the bottom side of the model
        _Undo.StartUndoGroup("Align");
        for (Object __dummyForeachVar7 : _DrawPanel.getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar7;
            if (xNode == model)
                continue;
             
            RectangleF nrect = _DrawPanel.getReportItemRect(xNode);
            if (nrect.Height == float.MinValue)
                continue;
             
            float ntop = mbottom - nrect.Height;
            if (ntop < 0)
                ntop = 0;
             
            String top = String.Format("{0:0.00}pt", ntop);
            _DrawPanel.setElement(xNode,"Top",top);
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    public void alignMiddles() throws Exception {
        XmlNode model = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        RectangleF mrect = _DrawPanel.getReportItemRect(model);
        // size attributes in points
        if (mrect.Height == float.MinValue)
            return ;
         
        // model doesn't have height specified
        float mc = mrect.Top + mrect.Height / 2;
        // the middle of the model
        _Undo.StartUndoGroup("Align");
        for (Object __dummyForeachVar8 : _DrawPanel.getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar8;
            if (xNode == model)
                continue;
             
            RectangleF nrect = _DrawPanel.getReportItemRect(xNode);
            if (nrect.Height == float.MinValue)
                continue;
             
            float ntop = (mc - (nrect.Top + nrect.Height / 2));
            ntop += nrect.Top;
            if (ntop < 0)
                ntop = 0;
             
            String top = String.Format("{0:0.00}pt", ntop);
            _DrawPanel.setElement(xNode,"Top",top);
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    public void sizeHeights() throws Exception {
        XmlNode model = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        XmlNode h = _DrawPanel.getNamedChildNode(model,"Height");
        if (h == null)
            return ;
         
        String height = h.InnerText;
        _Undo.StartUndoGroup("Size");
        for (Object __dummyForeachVar9 : _DrawPanel.getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar9;
            // we even reset the first one; in case the attribute wasn't specified
            _DrawPanel.setElement(xNode,"Height",height);
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    public void sizeWidths() throws Exception {
        XmlNode model = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        XmlNode w = _DrawPanel.getNamedChildNode(model,"Width");
        if (w == null)
            return ;
         
        String width = w.InnerText;
        _Undo.StartUndoGroup("Size");
        for (Object __dummyForeachVar10 : _DrawPanel.getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar10;
            // we even reset the first one; in case the attribute wasn't specified
            _DrawPanel.setElement(xNode,"Width",width);
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    public void sizeBoth() throws Exception {
        XmlNode model = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        XmlNode w = _DrawPanel.getNamedChildNode(model,"Width");
        if (w == null)
            return ;
         
        String width = w.InnerText;
        XmlNode h = _DrawPanel.getNamedChildNode(model,"Height");
        if (h == null)
            return ;
         
        String height = h.InnerText;
        _Undo.StartUndoGroup("Size");
        for (Object __dummyForeachVar11 : _DrawPanel.getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar11;
            // we even reset the first one; in case the attribute wasn't specified
            _DrawPanel.setElement(xNode,"Height",height);
            _DrawPanel.setElement(xNode,"Width",width);
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    void horzSpacing(float diff) throws Exception {
        XmlNode model = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        RectangleF rectm = _DrawPanel.getReportItemRect(model);
        if (rectm.Width == float.MinValue)
            return ;
         
        _Undo.StartUndoGroup("Spacing");
        float x = rectm.Left + rectm.Width + diff;
        for (Object __dummyForeachVar12 : _DrawPanel.getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar12;
            if (xNode == model)
                continue;
             
            String left = String.Format("{0:0.00}pt", x);
            _DrawPanel.setElement(xNode,"Left",left);
            RectangleF rectn = _DrawPanel.getReportItemRect(xNode);
            if (rectn.Width == float.MinValue)
                rectn.Width = 77;
             
            x += (rectn.Width + diff);
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    float horzSpacingDiff() throws Exception {
        float diff = 0;
        if (_DrawPanel.getSelectedList().Count < 2)
            return diff;
         
        XmlNode m1 = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        RectangleF r1 = _DrawPanel.getReportItemRect(m1);
        if (r1.Width == float.MinValue)
            return diff;
         
        XmlNode m2 = _DrawPanel.getSelectedList()[1] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[1] : (XmlNode)null;
        RectangleF r2 = _DrawPanel.getReportItemRect(m2);
        diff = r2.Left - (r1.Left + r1.Width);
        if (diff < 0)
            diff = 0;
         
        return diff;
    }

    public void horzSpacingMakeEqual() throws Exception {
        if (_DrawPanel.getSelectedList().Count < 2)
            return ;
         
        horzSpacing(horzSpacingDiff());
    }

    public void horzSpacingIncrease() throws Exception {
        float diff = horzSpacingDiff() + 8;
        horzSpacing(diff);
    }

    public void horzSpacingDecrease() throws Exception {
        float diff = horzSpacingDiff() - 8;
        if (diff < 0)
            diff = 0;
         
        horzSpacing(diff);
    }

    public void horzSpacingMakeZero() throws Exception {
        HorzSpacing(0);
    }

    void vertSpacing(float diff) throws Exception {
        XmlNode model = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        RectangleF rectm = _DrawPanel.getReportItemRect(model);
        if (rectm.Height == float.MinValue)
            return ;
         
        _Undo.StartUndoGroup("Spacing");
        float y = rectm.Top + rectm.Height + diff;
        for (Object __dummyForeachVar13 : _DrawPanel.getSelectedList())
        {
            XmlNode xNode = (XmlNode)__dummyForeachVar13;
            if (xNode == model)
                continue;
             
            String top = String.Format("{0:0.00}pt", y);
            _DrawPanel.setElement(xNode,"Top",top);
            RectangleF rectn = _DrawPanel.getReportItemRect(xNode);
            if (rectn.Height == float.MinValue)
                rectn.Height = 16;
             
            y += (rectn.Height + diff);
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    float vertSpacingDiff() throws Exception {
        float diff = 0;
        if (_DrawPanel.getSelectedList().Count < 2)
            return diff;
         
        XmlNode m1 = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        RectangleF r1 = _DrawPanel.getReportItemRect(m1);
        if (r1.Height == float.MinValue)
            return diff;
         
        XmlNode m2 = _DrawPanel.getSelectedList()[1] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[1] : (XmlNode)null;
        RectangleF r2 = _DrawPanel.getReportItemRect(m2);
        diff = r2.Top - (r1.Top + r1.Height);
        if (diff < 0)
            diff = 0;
         
        return diff;
    }

    public void vertSpacingMakeEqual() throws Exception {
        if (_DrawPanel.getSelectedList().Count < 2)
            return ;
         
        vertSpacing(vertSpacingDiff());
    }

    public void vertSpacingIncrease() throws Exception {
        float diff = vertSpacingDiff() + 8;
        vertSpacing(diff);
    }

    public void vertSpacingDecrease() throws Exception {
        float diff = vertSpacingDiff() - 8;
        if (diff < 0)
            diff = 0;
         
        vertSpacing(diff);
    }

    public void vertSpacingMakeZero() throws Exception {
        VertSpacing(0);
    }

    public void setPadding(String name, int diff) throws Exception {
        if (_DrawPanel.getSelectedList().Count < 1)
            return ;
         
        _Undo.StartUndoGroup("Padding");
        for (Object __dummyForeachVar14 : _DrawPanel.getSelectedList())
        {
            XmlNode n = (XmlNode)__dummyForeachVar14;
            XmlNode sNode = this._DrawPanel.getCreateNamedChildNode(n,"Style");
            if (diff == 0)
                _DrawPanel.setElement(sNode,name,"0pt");
            else
            {
                float pns = _DrawPanel.getSize(sNode,name);
                pns += diff;
                if (pns < 0)
                    pns = 0;
                 
                String pad = String.Format("{0:0.00}pt", pns);
                _DrawPanel.setElement(sNode,name,pad);
            } 
        }
        _Undo.endUndoGroup();
        ReportChanged(this, new EventArgs());
        _DrawPanel.Invalidate();
    }

    public void cut() throws Exception {
        if (_DrawPanel.getSelectedCount() <= 0)
            return ;
         
        Clipboard.SetDataObject(getCopy(), true);
        _Undo.StartUndoGroup("Cut");
        _DrawPanel.deleteSelected();
        _Undo.endUndoGroup();
        SelectionChanged(this, new EventArgs());
    }

    public void copy() throws Exception {
        Clipboard.SetDataObject(getCopy(), true);
    }

    public void clear() throws Exception {
        return ;
    }

    public void delete() throws Exception {
        if (_DrawPanel.getSelectedCount() > 0)
        {
            _Undo.StartUndoGroup("Delete");
            _DrawPanel.deleteSelected();
            _Undo.endUndoGroup();
            ReportChanged(this, new EventArgs());
            SelectionChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
         
    }

    public void paste() throws Exception {
        doPaste(null,new PointF(0, 0));
    }

    public void selectAll() throws Exception {
        doSelectAll();
    }

    public int getSelectionCount() throws Exception {
        return this._DrawPanel.getSelectedCount();
    }

    public String getSelectedText() throws Exception {
        if (_DrawPanel.getSelectedCount() == 0)
            return "";
         
        return getCopy();
    }

    public String getSelectionName() throws Exception {
        if (_DrawPanel.getSelectedCount() == 0)
            return "";
         
        if (_DrawPanel.getSelectedCount() > 1)
            return "Group Selection";
         
        XmlNode xNode = _DrawPanel.getSelectedList()[0];
        if (StringSupport.equals(xNode.Name, "TableColumn") || StringSupport.equals(xNode.Name, "TableRow"))
            return "";
         
        XmlAttribute xAttr = xNode.Attributes["Name"];
        if (xAttr == null)
            return "*Unnamed*";
         
        XmlNode cNode = _DrawPanel.getReportItemContainer(xNode);
        if (cNode == null)
            return xAttr.Value;
         
        XmlAttribute cAttr = cNode.Attributes["Name"];
        if (cAttr == null)
            return xAttr.Value + " in " + cNode.Name;
         
        String title = xAttr.Value + " in " + cNode.Name + " " + cAttr.Value;
        if (!StringSupport.equals(cNode.Name, "Table"))
            return title;
         
        XmlNode pNode = xNode.ParentNode.ParentNode;
        if (!StringSupport.equals(pNode.Name, "TableCell"))
            return title;
         
        XmlNode trNode = pNode.ParentNode.ParentNode;
        // should be TableRow
        if (!StringSupport.equals(trNode.Name, "TableRow"))
            return title;
         
        // Find the number of the TableRow -- e.g. 1st, 2nd, 3rd, ...
        int trNumber = 1;
        for (Object __dummyForeachVar15 : trNode.ParentNode.ChildNodes)
        {
            XmlNode n = (XmlNode)__dummyForeachVar15;
            if (n == trNode)
                break;
             
            trNumber++;
        }
        pNode = trNode.ParentNode.ParentNode;
        // Details, Header or Footer
        String rowTitle = trNumber > 1 ? String.Format("{0}({1})", pNode.Name, trNumber) : pNode.Name;
        if (StringSupport.equals(pNode.Name, "Details"))
            return title + " " + rowTitle;
         
        // We've got a Header or a Footer; could be a group header/footer
        pNode = pNode.ParentNode;
        if (!StringSupport.equals(pNode.Name, "TableGroup"))
            return title + " " + rowTitle;
         
        // We're in a group; find out the group name
        XmlNode gNode = this._DrawPanel.getNamedChildNode(pNode,"Grouping");
        if (gNode == null)
            return title + " " + rowTitle;
         
        XmlAttribute gAttr = gNode.Attributes["Name"];
        if (gAttr == null)
            return title + " " + rowTitle;
         
        return title + ", Group " + gAttr.Value + " " + rowTitle;
    }

    public PointF getSelectionPosition() throws Exception {
        if (_DrawPanel.getSelectedCount() == 0)
            return new PointF(float.MinValue, float.MinValue);
         
        XmlNode xNode = _DrawPanel.getSelectedList()[0];
        return _DrawPanel.selectionPosition(xNode);
    }

    public SizeF getSelectionSize() throws Exception {
        if (_DrawPanel.getSelectedCount() == 0)
            return new SizeF(float.MinValue, float.MinValue);
         
        XmlNode xNode = _DrawPanel.getSelectedList()[0];
        return _DrawPanel.selectionSize(xNode);
    }

    public StyleInfo getSelectedStyle() throws Exception {
        if (_DrawPanel.getSelectedCount() == 0)
            return null;
         
        XmlNode xNode = _DrawPanel.getSelectedList()[0];
        return _DrawPanel.getStyleInfo(xNode);
    }

    public void applyStyleToSelected(String name, String v) throws Exception {
        if (_DrawPanel.getSelectedCount() == 0)
            return ;
         
        _Undo.StartUndoGroup("Style");
        _DrawPanel.applyStyleToSelected(name,v);
        _Undo.endUndoGroup(true);
        ReportChanged(this, new EventArgs());
    }

    public void setSelectedText(String v) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode tn = _DrawPanel.getSelectedList()[0] instanceof XmlNode ? (XmlNode)_DrawPanel.getSelectedList()[0] : (XmlNode)null;
        if (tn == null || !StringSupport.equals(tn.Name, "Textbox"))
            return ;
         
        _Undo.StartUndoGroup("Textbox Value");
        _DrawPanel.setElement(tn,"Value",v);
        _Undo.endUndoGroup(true);
        _DrawPanel.Invalidate();
        // force a repaint
        ReportChanged(this, new EventArgs());
    }

    private void buildContextMenus() throws Exception {
        // EDIT MENU
        menuCopy = new MenuItem("&Copy", new EventHandler(this.menuCopy_Click));
        menuPaste = new MenuItem("Paste", new EventHandler(this.menuPaste_Click));
        menuDelete = new MenuItem("&Delete", new EventHandler(this.menuDelete_Click));
        menuFSep1 = new MenuItem("-");
        menuSelectAll = new MenuItem("Select &All", new EventHandler(this.menuSelectAll_Click));
        menuFSep2 = new MenuItem("-");
        List<MenuItem> insertItems = new List<MenuItem>();
        insertItems.Add(new MenuItem("&Chart...", new EventHandler(this.menuInsertChart_Click)));
        insertItems.Add(new MenuItem("&Image", new EventHandler(this.menuInsertImage_Click)));
        insertItems.Add(new MenuItem("&Line", new EventHandler(this.menuInsertLine_Click)));
        insertItems.Add(new MenuItem("&List", new EventHandler(this.menuInsertList_Click)));
        insertItems.Add(new MenuItem("&Matrix...", new EventHandler(this.menuInsertMatrix_Click)));
        insertItems.Add(new MenuItem("&Rectangle", new EventHandler(this.menuInsertRectangle_Click)));
        insertItems.Add(new MenuItem("&Subreport", new EventHandler(this.menuInsertSubreport_Click)));
        insertItems.Add(new MenuItem("Ta&ble...", new EventHandler(this.menuInsertTable_Click)));
        insertItems.Add(new MenuItem("&Textbox", new EventHandler(this.menuInsertTextbox_Click)));
        // Now add any CustomReportItems
        BuildContextMenusCustom(insertItems);
        menuInsert = new MenuItem("&Insert");
        menuInsert.MenuItems.AddRange(insertItems.ToArray());
        menuProperties = new MenuItem("&Properties...", new EventHandler(this.menuProperties_Click));
        //
        // Create chart context menu and add array of sub-menu items
        menuPropertiesLegend = new MenuItem("Legend...", new EventHandler(this.menuPropertiesLegend_Click));
        menuPropertiesCategoryAxis = new MenuItem("Category (X) Axis...", new EventHandler(this.menuPropertiesCategoryAxis_Click));
        menuPropertiesValueAxis = new MenuItem("Value (Y) Axis...", new EventHandler(this.menuPropertiesValueAxis_Click));
        menuPropertiesCategoryAxisTitle = new MenuItem("Category (X) Axis Title...", new EventHandler(this.menuPropertiesCategoryAxisTitle_Click));
        menuPropertiesValueAxisTitle = new MenuItem("Value (Y) Axis Title...", new EventHandler(this.menuPropertiesValueAxisTitle_Click));
        menuPropertiesChartTitle = new MenuItem("Title...", new EventHandler(this.menuPropertiesChartTitle_Click));
    }

    private void buildContextMenusCustom(List<MenuItem> items) throws Exception {
        try
        {
            String[] sa = RdlEngineConfig.getCustomReportTypes();
            if (sa == null || sa.Length == 0)
                return ;
             
            items.Add(new MenuItem("-"));
            for (Object __dummyForeachVar16 : sa)
            {
                // put a separator
                // Add the custom report items to the insert menu
                String m = (String)__dummyForeachVar16;
                MenuItem mi = new MenuItem(m + "...", new EventHandler(this.menuInsertCustomReportItem_Click));
                mi.Tag = m;
                items.Add(mi);
            }
        }
        catch (Exception ex)
        {
            MessageBox.Show(String.Format("Error building CustomReportItem menus: {0}", ex.Message), "Insert", MessageBoxButtons.OK);
        }
    
    }

    private void drawPanelPaint(Object sender, System.Windows.Forms.PaintEventArgs e) throws Exception {
        synchronized (this)
        {
            {
                // Only handle one paint at a time
                if (_InPaint)
                    return ;
                 
                _InPaint = true;
            }
        }
        Graphics g = e.Graphics;
        try
        {
            // never want to die in here
            if (this._ReportDoc == null)
                // if no report force the simplest one
                createEmptyReportDoc();
             
            _DrawPanel.Draw(g, PointsX(_hScroll.Value), PointsY(_vScroll.Value), e.ClipRectangle);
        }
        catch (Exception ex)
        {
            // don't want to kill process if we die -- put up some kind of error message
            StringFormat format = new StringFormat();
            String msg = String.Format("Error drawing report.  Likely error in syntax.  Switch to syntax and correct report syntax.{0}{1}{0}{2}", Environment.NewLine, ex.Message, ex.StackTrace);
            g.DrawString(msg, this.Font, Brushes.Black, new Rectangle(2, 2, this.Width, this.Height), format);
        }

        synchronized (this)
        {
            {
                _InPaint = false;
            }
        }
    }

    private void createEmptyReportDoc() throws Exception {
        XmlDocument xDoc = new XmlDocument();
        xDoc.PreserveWhitespace = false;
        xDoc.LoadXml("<Report><Body><Height>0pt</Height></Body></Report>");
        setReportDocument(xDoc);
    }

    private void drawPanelResize(Object sender, EventArgs e) throws Exception {
        _DrawPanel.Refresh();
        setScrollControls();
    }

    private float pointsX(float x) throws Exception {
        return x * DesignXmlDraw.POINTSIZED / _DpiX;
    }

    // pixels to points
    private float pointsY(float y) throws Exception {
        return y * DesignXmlDraw.POINTSIZED / _DpiY;
    }

    private int pixelsX(float x) throws Exception {
        return (int)(x * _DpiX / DesignXmlDraw.POINTSIZED);
    }

    // points to pixels
    private int pixelsY(float y) throws Exception {
        return (int)(y * _DpiY / DesignXmlDraw.POINTSIZED);
    }

    public void setScrollControls() throws Exception {
        if (_ReportDoc == null)
        {
            // nothing loaded; nothing to do
            _vScroll.Enabled = _hScroll.Enabled = false;
            _vScroll.Value = _hScroll.Value = 0;
            return ;
        }
         
        setScrollControlsV();
        setScrollControlsH();
    }

    private void setScrollControlsV() throws Exception {
        // calculate the vertical scroll needed
        int h = pixelsY(_DrawPanel.getVerticalMax());
        // size we need to show
        int sh = this.Height - _hScroll.Height;
        if (sh > h || sh < 0)
        {
            _vScroll.Enabled = false;
            if (_vScroll.Value != 0)
            {
                _vScroll.Value = 0;
                _DrawPanel.Invalidate();
            }
             
            return ;
        }
         
        // force a repaint
        _vScroll.Minimum = 0;
        _vScroll.Maximum = h;
        int sValue = Math.Min(_vScroll.Value, _vScroll.Maximum);
        if (_vScroll.Value != sValue)
        {
            _vScroll.Value = sValue;
            _DrawPanel.Invalidate();
        }
         
        // force a repaint
        _vScroll.LargeChange = sh;
        _vScroll.SmallChange = _vScroll.LargeChange / 5;
        _vScroll.Enabled = true;
        return ;
    }

    private void setScrollControlsH() throws Exception {
        int w = pixelsX(_DrawPanel.getHorizontalMax());
        int sw = this.Width - _vScroll.Width;
        if (sw > w)
        {
            _hScroll.Enabled = false;
            if (_hScroll.Value != 0)
            {
                _hScroll.Value = 0;
                _DrawPanel.Invalidate();
            }
             
            return ;
        }
         
        _hScroll.Maximum = w;
        _hScroll.Minimum = 0;
        int sValue = Math.Min(_hScroll.Value, _hScroll.Maximum);
        if (_hScroll.Value != sValue)
        {
            _hScroll.Value = sValue;
            _DrawPanel.Invalidate();
        }
         
        if (sw < 0)
            sw = 0;
         
        _hScroll.LargeChange = sw;
        _hScroll.SmallChange = _hScroll.LargeChange / 5;
        _hScroll.Enabled = true;
        return ;
    }

    private void horizontalScroll(Object sender, System.Windows.Forms.ScrollEventArgs e) throws Exception {
        if (e.NewValue == _hScroll.Value)
            return ;
         
        // don't need to scroll if already there
        _DrawPanel.Invalidate();
    }

    private void verticalScroll(Object sender, System.Windows.Forms.ScrollEventArgs e) throws Exception {
        if (e.NewValue == _vScroll.Value)
            return ;
         
        // don't need to scroll if already there
        _DrawPanel.Invalidate();
    }

    private void drawPanelMouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button == MouseButtons.Left)
            _Undo.endUndoGroup(true);
         
        if (_MouseDownNode != null && StringSupport.equals(_MouseDownNode.Name, "Height"))
            HeightChanged.invoke(this,new HeightEventArgs(null));
         
        // reset any mousemove
        _MouseDownNode = null;
        if (this._bHaveMouse)
        {
            // Handle the end of the rubber banding
            _bHaveMouse = false;
            // remove last rectangle if necessary
            if (this._ptRBLast.X != -1)
            {
                this.drawPanelRubberBand(this._ptRBOriginal,this._ptRBLast);
                // Process the rectangle
                Rectangle r = drawPanelRectFromPoints(this._ptRBOriginal,this._ptRBLast);
                if ((Control.ModifierKeys & Keys.Control) != Keys.Control)
                    // we allow addition to selection
                    _DrawPanel.clearSelected();
                 
                _DrawPanel.SelectInRectangle(r, PointsX(_hScroll.Value), PointsY(_vScroll.Value));
                SelectionChanged(this, new EventArgs());
            }
             
            // clear out the points for the next time
            _ptRBOriginal.X = _ptRBOriginal.Y = _ptRBLast.X = _ptRBLast.Y = -1;
        }
        else if (e.Button == MouseButtons.Right)
        {
            drawPanelContextMenu(new Point(e.X, e.Y));
        }
          
        if (_AdjustScroll)
        {
            this.setScrollControls();
            _AdjustScroll = false;
        }
         
    }

    private void drawPanelContextMenu(Point p) throws Exception {
        if (_DrawPanel.getSelectedCount() == 1)
        {
            XmlNode cNode = _DrawPanel.getSelectedList()[0];
            if (StringSupport.equals(cNode.Name, "Chart"))
                drawPanelContextMenuChart(p,cNode);
            else if (StringSupport.equals(cNode.Name, "Subreport"))
                drawPanelContextMenuSubreport(p,cNode);
            else if (_DrawPanel.inTable(cNode))
                drawPanelContextMenuTable(p,cNode);
            else if (_DrawPanel.inMatrix(cNode))
                drawPanelContextMenuMatrix(p,cNode);
            else
                drawPanelContextMenuDefault(p);    
        }
        else
            drawPanelContextMenuDefault(p); 
    }

    private void drawPanelContextMenuChart(Point p, XmlNode riNode) throws Exception {
        menuContext = new ContextMenu();
        menuContext.Popup += new EventHandler(this.menuContext_Popup);
        // Get the Category Groupings
        Object[] catGroupNames = _DrawPanel.getChartCategoryGroupNames(riNode);
        MenuItem menuChartInsertCategoryGrouping = new MenuItem("Insert Category Grouping...", new EventHandler(this.menuChartInsertCategoryGrouping_Click));
        MenuItem menuChartEditCategoryGrouping = new MenuItem("Edit Category Grouping");
        MenuItem menuChartDeleteCategoryGrouping = new MenuItem("Delete Category Grouping");
        if (catGroupNames != null)
        {
            for (Object __dummyForeachVar17 : catGroupNames)
            {
                String gname = (String)__dummyForeachVar17;
                menuChartEditCategoryGrouping.MenuItems.Add(new MenuItem(gname, new EventHandler(this.menuChartEditGrouping_Click)));
                menuChartDeleteCategoryGrouping.MenuItems.Add(new MenuItem(gname, new EventHandler(this.menuChartDeleteGrouping_Click)));
            }
        }
        else
        {
            menuChartEditCategoryGrouping.Enabled = false;
            menuChartDeleteCategoryGrouping.Enabled = false;
        } 
        // Get the Series Groupings
        Object[] serGroupNames = _DrawPanel.getChartSeriesGroupNames(riNode);
        MenuItem menuChartInsertSeriesGrouping = new MenuItem("Insert Series Grouping...", new EventHandler(this.menuChartInsertSeriesGrouping_Click));
        MenuItem menuChartEditSeriesGrouping = new MenuItem("Edit Series Grouping");
        MenuItem menuChartDeleteSeriesGrouping = new MenuItem("Delete Series Grouping");
        if (serGroupNames != null)
        {
            for (Object __dummyForeachVar18 : serGroupNames)
            {
                String gname = (String)__dummyForeachVar18;
                menuChartEditSeriesGrouping.MenuItems.Add(new MenuItem(gname, new EventHandler(this.menuChartEditGrouping_Click)));
                menuChartDeleteSeriesGrouping.MenuItems.Add(new MenuItem(gname, new EventHandler(this.menuChartDeleteGrouping_Click)));
            }
        }
        else
        {
            menuChartEditSeriesGrouping.Enabled = false;
            menuChartDeleteSeriesGrouping.Enabled = false;
        } 
        menuContext.MenuItems.AddRange(new MenuItem[]{ menuProperties, menuPropertiesLegend, menuPropertiesChartTitle, new MenuItem("-"), menuChartInsertCategoryGrouping, menuChartEditCategoryGrouping, menuChartDeleteCategoryGrouping, new MenuItem("-"), menuPropertiesCategoryAxis, menuPropertiesCategoryAxisTitle, new MenuItem("-"), menuChartInsertSeriesGrouping, menuChartEditSeriesGrouping, menuChartDeleteSeriesGrouping, new MenuItem("-"), menuPropertiesValueAxis, menuPropertiesValueAxisTitle, new MenuItem("-"), menuCopy, menuPaste, menuDelete, new MenuItem("-"), menuSelectAll });
        menuContext.Show(this, p);
    }

    private void drawPanelContextMenuDefault(Point p) throws Exception {
        menuContext = new ContextMenu();
        menuContext.Popup += new EventHandler(this.menuContext_Popup);
        menuContext.MenuItems.AddRange(new MenuItem[]{ menuProperties, new MenuItem("-"), menuCopy, menuPaste, menuDelete, menuFSep1, menuSelectAll, menuFSep2, menuInsert });
        menuContext.Show(this, p);
    }

    private void drawPanelContextMenuMatrix(Point p, XmlNode riNode) throws Exception {
        menuContext = new ContextMenu();
        menuContext.Popup += new EventHandler(this.menuContext_Popup);
        // matrix menus
        MenuItem menuMatrixDelete = new MenuItem("Delete Matrix", new EventHandler(this.menuMatrixDelete_Click));
        MenuItem menuMatrixProperties = new MenuItem("Matrix Properties...", new EventHandler(this.menuMatrixProperties_Click));
        // Get the column groupings
        MenuItem[] cmenu = new MenuItem[]();
        // the ultimate context menu items
        Object[] colGroupNames = _DrawPanel.getMatrixColumnGroupNames(riNode);
        Object[] rowGroupNames = _DrawPanel.getMatrixRowGroupNames(riNode);
        MenuItem menuMatrixInsertColumnGroup = new MenuItem("Insert Column Group...", new EventHandler(this.menuMatrixInsertColumnGroup_Click));
        MenuItem menuMatrixEditColumnGroup = new MenuItem("Edit Column Group");
        MenuItem menuMatrixDeleteColumnGroup = new MenuItem("Delete Column Group");
        if (colGroupNames != null)
        {
            for (Object __dummyForeachVar19 : colGroupNames)
            {
                String gname = (String)__dummyForeachVar19;
                menuMatrixEditColumnGroup.MenuItems.Add(new MenuItem(gname, new EventHandler(this.menuMatrixEditGroup_Click)));
                menuMatrixDeleteColumnGroup.MenuItems.Add(new MenuItem(gname, new EventHandler(this.menuMatrixDeleteGroup_Click)));
            }
        }
        else
        {
            menuMatrixEditColumnGroup.Enabled = false;
            menuMatrixDeleteColumnGroup.Enabled = false;
        } 
        MenuItem menuMatrixInsertRowGroup = new MenuItem("Insert Row Group...", new EventHandler(this.menuMatrixInsertRowGroup_Click));
        MenuItem menuMatrixEditRowGroup = new MenuItem("Edit Row Group");
        MenuItem menuMatrixDeleteRowGroup = new MenuItem("Delete Row Group");
        if (rowGroupNames != null)
        {
            for (Object __dummyForeachVar20 : rowGroupNames)
            {
                String gname = (String)__dummyForeachVar20;
                menuMatrixEditRowGroup.MenuItems.Add(new MenuItem(gname, new EventHandler(this.menuMatrixEditGroup_Click)));
                menuMatrixDeleteRowGroup.MenuItems.Add(new MenuItem(gname, new EventHandler(this.menuMatrixDeleteGroup_Click)));
            }
        }
        else
        {
            menuMatrixEditRowGroup.Enabled = false;
            menuMatrixDeleteRowGroup.Enabled = false;
        } 
        cmenu = new MenuItem[]{ menuProperties, menuMatrixProperties, new MenuItem("-"), menuMatrixInsertColumnGroup, menuMatrixEditColumnGroup, menuMatrixDeleteColumnGroup, new MenuItem("-"), menuMatrixInsertRowGroup, menuMatrixEditRowGroup, menuMatrixDeleteRowGroup, new MenuItem("-"), menuMatrixDelete, new MenuItem("-"), menuCopy, menuPaste, menuDelete, new MenuItem("-"), menuSelectAll };
        menuContext.MenuItems.AddRange(cmenu);
        menuContext.Show(this, p);
    }

    private void drawPanelContextMenuSubreport(Point p, XmlNode sr) throws Exception {
        menuContext = new ContextMenu();
        menuContext.Popup += new EventHandler(this.menuContext_Popup);
        // get the subreport name
        String name = _DrawPanel.getElementValue(sr,"ReportName","");
        if (name == null || name.Length == 0)
        {
            // No name; no way to open the subreport
            menuContext.MenuItems.AddRange(new MenuItem[]{ menuProperties, new MenuItem("-"), menuCopy, menuPaste, menuDelete, new MenuItem("-"), menuSelectAll, menuFSep2, menuInsert });
        }
        else
        {
            String srmi = "Open " + name;
            menuContext.MenuItems.AddRange(new MenuItem[]{ menuProperties, new MenuItem(srmi, new EventHandler(this.menuOpenSubreport_Click)), new MenuItem("-"), menuCopy, menuPaste, menuDelete, new MenuItem("-"), menuSelectAll, menuFSep2, menuInsert });
        } 
        menuContext.Show(this, p);
    }

    private void drawPanelContextMenuTable(Point p, XmlNode riNode) throws Exception {
        menuContext = new ContextMenu();
        menuContext.Popup += new EventHandler(this.menuContext_Popup);
        // table menus
        MenuItem menuTableInsertRowBefore = new MenuItem("Insert Row Before", new EventHandler(this.menuTableInsertRowBefore_Click));
        MenuItem menuTableInsertRowAfter = new MenuItem("Insert Row After", new EventHandler(this.menuTableInsertRowAfter_Click));
        MenuItem menuTableDeleteRow = new MenuItem("Delete Table Row", new EventHandler(this.menuTableDeleteRow_Click));
        MenuItem menuTableInsertColumnBefore = new MenuItem("Insert Column Before", new EventHandler(this.menuTableInsertColumnBefore_Click));
        MenuItem menuTableInsertColumnAfter = new MenuItem("Insert Column After", new EventHandler(this.menuTableInsertColumnAfter_Click));
        MenuItem menuTableInsertTableGroup = new MenuItem("Insert Table Group...", new EventHandler(this.menuTableInsertGroup_Click));
        MenuItem menuTableDeleteColumn = new MenuItem("Delete Table Column", new EventHandler(this.menuTableDeleteColumn_Click));
        MenuItem menuTableDelete = new MenuItem("Delete Table", new EventHandler(this.menuTableDelete_Click));
        MenuItem menuTableProperties = new MenuItem("Table Properties...", new EventHandler(this.menuTableProperties_Click));
        // the replace items
        MenuItem menuReplTextbox = new MenuItem("&Textbox", new EventHandler(this.menuInsertTextbox_Click));
        MenuItem menuReplRectangle = new MenuItem("&Rectangle", new EventHandler(this.menuInsertRectangle_Click));
        MenuItem menuReplImage = new MenuItem("&Image", new EventHandler(this.menuInsertImage_Click));
        MenuItem menuReplSubreport = new MenuItem("&Subreport", new EventHandler(this.menuInsertSubreport_Click));
        MenuItem menuReplList = new MenuItem("&List", new EventHandler(this.menuInsertList_Click));
        MenuItem menuReplMatrix = new MenuItem("&Matrix...", new EventHandler(this.menuInsertMatrix_Click));
        MenuItem menuReplTable = new MenuItem("Ta&ble...", new EventHandler(this.menuInsertTable_Click));
        MenuItem menuReplChart = new MenuItem("&Chart...", new EventHandler(this.menuInsertChart_Click));
        MenuItem menuRepl = new MenuItem("&Replace Cell with");
        menuRepl.MenuItems.AddRange(new MenuItem[]{ menuReplChart, menuReplImage, menuReplList, menuReplMatrix, menuReplRectangle, menuReplSubreport, menuReplTable, menuReplTextbox });
        // If there are any TableGroups then we need menu items to manipulate them
        MenuItem[] cmenu = new MenuItem[]();
        // the ultimate context menu items
        Object[] tblGroupNames = _DrawPanel.getTableGroupNames(riNode);
        if (tblGroupNames == null)
        {
            // Don't need menus for manipulating existing TableGroups
            cmenu = new MenuItem[]{ menuProperties, menuTableProperties, menuRepl, new MenuItem("-"), menuTableInsertColumnBefore, menuTableInsertColumnAfter, new MenuItem("-"), menuTableInsertRowBefore, menuTableInsertRowAfter, new MenuItem("-"), menuTableInsertTableGroup, new MenuItem("-"), menuTableDeleteColumn, menuTableDeleteRow, menuTableDelete, new MenuItem("-"), menuCopy, menuPaste, menuDelete, new MenuItem("-"), menuSelectAll };
        }
        else
        {
            MenuItem menuTableEditGroup = new MenuItem("Edit Group");
            MenuItem menuTableDeleteGroup = new MenuItem("Delete Group");
            for (Object __dummyForeachVar21 : tblGroupNames)
            {
                String gname = (String)__dummyForeachVar21;
                menuTableEditGroup.MenuItems.Add(new MenuItem(gname, new EventHandler(this.menuTableEditGroup_Click)));
                menuTableDeleteGroup.MenuItems.Add(new MenuItem(gname, new EventHandler(this.menuTableDeleteGroup_Click)));
            }
            cmenu = new MenuItem[]{ menuProperties, menuTableProperties, menuRepl, new MenuItem("-"), menuTableInsertColumnBefore, menuTableInsertColumnAfter, new MenuItem("-"), menuTableInsertRowBefore, menuTableInsertRowAfter, new MenuItem("-"), menuTableInsertTableGroup, menuTableEditGroup, menuTableDeleteGroup, new MenuItem("-"), menuTableDeleteColumn, menuTableDeleteRow, menuTableDelete, new MenuItem("-"), menuCopy, menuPaste, menuDelete, new MenuItem("-"), menuSelectAll };
        } 
        menuContext.MenuItems.AddRange(cmenu);
        menuContext.Show(this, p);
    }

    private void drawPanelMouseMove(Object sender, MouseEventArgs e) throws Exception {
        XmlNode b = null;
        HitLocationEnum hle = HitLocationEnum.Inside;
        Point newMousePosition = new Point(e.X, e.Y);
        if (_bHaveMouse)
        {
            // we're rubber banding
            // If we drew previously; we'll draw again to remove old rectangle
            if (this._ptRBLast.X != -1)
            {
                this.drawPanelRubberBand(this._ptRBOriginal,_ptRBLast);
            }
             
            _MousePosition = newMousePosition;
            // Update last point;  but don't rubber band outside our client area
            if (newMousePosition.X < 0)
                newMousePosition.X = 0;
             
            if (newMousePosition.X > _DrawPanel.Width)
                newMousePosition.X = _DrawPanel.Width;
             
            if (newMousePosition.Y < 0)
                newMousePosition.Y = 0;
             
            if (newMousePosition.Y > _DrawPanel.Height)
                newMousePosition.Y = _DrawPanel.Height;
             
            _ptRBLast = newMousePosition;
            if (_ptRBLast.X < 0)
                _ptRBLast.X = 0;
             
            if (_ptRBLast.Y < 0)
                _ptRBLast.Y = 0;
             
            // Draw new lines.
            this.drawPanelRubberBand(_ptRBOriginal,newMousePosition);
            Cursor.Current = Cursors.Hand;
            return ;
        }
        else // use cross hair to indicate drawing
        if (_MouseDownNode != null)
        {
            if (e.Button != MouseButtons.Left)
                b = _MouseDownNode;
            else
            {
                b = _MouseDownNode;
                Name __dummyScrutVar0 = _MouseDownNode.Name;
                if (__dummyScrutVar0.equals("TableColumn") || __dummyScrutVar0.equals("RowGrouping") || __dummyScrutVar0.equals("MatrixColumn"))
                {
                    hle = HitLocationEnum.TableColumnResize;
                    if (e.X == _MousePosition.X)
                        break;
                     
                    if (_DrawPanel.TableColumnResize(_MouseDownNode, e.X - _MousePosition.X))
                    {
                        SelectionMoved(this, new EventArgs());
                        ReportChanged(this, new EventArgs());
                        _AdjustScroll = true;
                        _DrawPanel.Invalidate();
                    }
                    else
                    {
                        // trying to drag into invalid area; disallow
                        Cursor.Position = this.PointToScreen(_MousePosition);
                        newMousePosition = this.PointToClient(Cursor.Position);
                    } 
                }
                else if (__dummyScrutVar0.equals("TableRow") || __dummyScrutVar0.equals("ColumnGrouping") || __dummyScrutVar0.equals("MatrixRow"))
                {
                    hle = HitLocationEnum.TableRowResize;
                    if (e.Y == _MousePosition.Y)
                        break;
                     
                    if (_DrawPanel.TableRowResize(_MouseDownNode, e.Y - _MousePosition.Y))
                    {
                        SelectionMoved(this, new EventArgs());
                        ReportChanged(this, new EventArgs());
                        _DrawPanel.Invalidate();
                    }
                    else
                    {
                        // trying to drag into invalid area; disallow
                        Cursor.Position = this.PointToScreen(_MousePosition);
                        newMousePosition = this.PointToClient(Cursor.Position);
                    } 
                }
                else if (__dummyScrutVar0.equals("Height"))
                {
                    if (e.Y == _MousePosition.Y)
                        break;
                     
                    if (_DrawPanel.ChangeHeight(_MouseDownNode, e.Y - _MousePosition.Y, 0))
                    {
                        ReportChanged(this, new EventArgs());
                        HeightChanged.invoke(this,new HeightEventArgs(b.InnerText));
                        _DrawPanel.Invalidate();
                        _AdjustScroll = true;
                    }
                    else
                    {
                        // this will force scroll bars to be adjusted on MouseUp
                        // trying to drag into invalid area; disallow
                        Cursor.Position = this.PointToScreen(_MousePosition);
                        newMousePosition = this.PointToClient(Cursor.Position);
                    } 
                }
                else // Force scroll when off end of page
                //if (e.Y > _DrawPanel.Height)
                //{
                //    int hs = _vScroll.Value + _vScroll.SmallChange;
                //    _vScroll.Value = Math.Min(_vScroll.Maximum, hs);
                //    _DrawPanel.Refresh();
                //}
                if (__dummyScrutVar0.equals("Textbox") || __dummyScrutVar0.equals("Image") || __dummyScrutVar0.equals("Rectangle") || __dummyScrutVar0.equals("List") || __dummyScrutVar0.equals("Table") || __dummyScrutVar0.equals("Matrix") || __dummyScrutVar0.equals("Chart") || __dummyScrutVar0.equals("Subreport") || __dummyScrutVar0.equals("Line") || __dummyScrutVar0.equals("CustomReportItem"))
                {
                    hle = this._MouseDownLoc;
                    if (e.Y == _MousePosition.Y && e.X == _MousePosition.X)
                        break;
                     
                    if (_DrawPanel.MoveSelectedItems(e.X - _MousePosition.X, e.Y - _MousePosition.Y, this._MouseDownLoc))
                    {
                        SelectionMoved(this, new EventArgs());
                        ReportChanged(this, new EventArgs());
                        _DrawPanel.Invalidate();
                        _AdjustScroll = true;
                    }
                    else
                    {
                        // trying to drag into invalid area; disallow
                        Cursor.Position = this.PointToScreen(_MousePosition);
                        newMousePosition = this.PointToClient(Cursor.Position);
                    } 
                }
                    
            } 
        }
        else
        {
            HitLocation hl = _DrawPanel.HitNode(newMousePosition, PointsX(_hScroll.Value), PointsY(_vScroll.Value));
            if (hl != null)
            {
                b = hl.HitNode;
                hle = hl.HitSpot;
            }
             
        }  
        _MousePosition = newMousePosition;
        drawPanelSetCursor(b,hle);
    }

    private void drawPanelMouseDown(Object sender, MouseEventArgs e) throws Exception {
        _MousePosition = new Point(e.X, e.Y);
        HitLocation hl = _DrawPanel.HitNode(_MousePosition, PointsX(_hScroll.Value), PointsY(_vScroll.Value));
        _MouseDownNode = hl == null ? null : hl.HitNode;
        _MouseDownLoc = hl == null ? HitLocationEnum.Inside : hl.HitSpot;
        if (drawPanelMouseDownInsert(hl,sender,e))
            return ;
         
        // Handle ReportItem insertion
        if (e.Button == MouseButtons.Left)
            _Undo.StartUndoGroup("Move/Size");
         
        if (drawPanelMouseDownRubberBand(sender,e))
            return ;
         
        // Handle rubber banding
        if (drawPanelMouseDownTableColumnResize(sender,e))
            return ;
         
        // Handle column resize
        if (drawPanelMouseDownTableRowResize(sender,e))
            return ;
         
        // Handle row resize
        if (StringSupport.equals(_MouseDownNode.Name, "Height"))
        {
            _DrawPanel.clearSelected();
            SelectionChanged(this, new EventArgs());
            HeightChanged.invoke(this,new HeightEventArgs(_MouseDownNode.InnerText));
        }
        else // Set the height
        if ((Control.ModifierKeys & Keys.Control) == Keys.Control)
        {
            _DrawPanel.addRemoveSelection(_MouseDownNode);
            SelectionChanged(this, new EventArgs());
        }
        else
        {
            _DrawPanel.setSelection(_MouseDownNode);
            SelectionChanged(this, new EventArgs());
        }  
        drawPanelSetCursor(_MouseDownNode,hl.HitSpot);
    }

    private boolean drawPanelMouseDownRubberBand(Object sender, MouseEventArgs e) throws Exception {
        if (_MouseDownLoc != HitLocationEnum.Inside)
            return false;
         
        // must hit inside a region
        // Now determine if object hit allows for rubber banding
        boolean bRubber = false;
        boolean bDeselect = true;
        if (_MouseDownNode == null)
            bRubber = true;
        else if (StringSupport.equals(_MouseDownNode.Name, "List") || StringSupport.equals(_MouseDownNode.Name, "Rectangle"))
        {
            if (_DrawPanel.getSelectedCount() == 1 && _DrawPanel.isNodeSelected(_MouseDownNode))
            {
                bRubber = true;
                bDeselect = false;
            }
             
        }
        else if (StringSupport.equals(_MouseDownNode.Name, "Body") || StringSupport.equals(_MouseDownNode.Name, "PageHeader") || StringSupport.equals(_MouseDownNode.Name, "PageFooter"))
            bRubber = true;
           
        if (!bRubber)
            return false;
         
        // We have a rubber band operation
        if (e.Button != MouseButtons.Left)
        {
            if (bDeselect)
            {
                _DrawPanel.clearSelected();
                SelectionChanged(this, new EventArgs());
            }
             
            return true;
        }
         
        // well no rubber band but it's been handled
        if ((Control.ModifierKeys & Keys.Control) != Keys.Control)
        {
            // we allow addition to selection
            if (bDeselect)
            {
                _DrawPanel.clearSelected();
                SelectionChanged(this, new EventArgs());
            }
             
        }
         
        _bHaveMouse = true;
        // keep the starting point of the rectangular rubber band
        this._ptRBOriginal.X = e.X;
        this._ptRBOriginal.Y = e.Y;
        // -1 indicates no previous rubber band
        this._ptRBLast.X = this._ptRBLast.Y = -1;
        Cursor.Current = Cursors.Hand;
        return true;
    }

    // use cross hair to indicate drawing: TODO: Cross looks like IBeam
    private boolean drawPanelMouseDownTableColumnResize(Object sender, MouseEventArgs e) throws Exception {
        if (_MouseDownNode == null || _MouseDownLoc != HitLocationEnum.TableColumnResize)
            return false;
         
        Cursor.Current = Cursors.VSplit;
        return true;
    }

    private boolean drawPanelMouseDownTableRowResize(Object sender, MouseEventArgs e) throws Exception {
        if (_MouseDownNode == null || _MouseDownLoc != HitLocationEnum.TableRowResize)
            return false;
         
        Cursor.Current = Cursors.HSplit;
        return true;
    }

    private boolean drawPanelMouseDownInsert(HitLocation hl, Object sender, MouseEventArgs e) throws Exception {
        // should we be inserting?
        if (!(_CurrentInsert != null && _MouseDownNode != null && (StringSupport.equals(_MouseDownNode.Name, "List") || StringSupport.equals(_MouseDownNode.Name, "Rectangle") || StringSupport.equals(_MouseDownNode.Name, "Body") || StringSupport.equals(_MouseDownNode.Name, "PageHeader") || StringSupport.equals(_MouseDownNode.Name, "PageFooter"))))
        {
            if (_CurrentInsert == null || StringSupport.equals(_CurrentInsert, "Line") || hl == null || hl.HitContainer == null || !StringSupport.equals(hl.HitContainer.Name, "Table"))
                return false;
             
            if (MessageBox.Show("Do you want to replace contents of TableCell?", "Insert", MessageBoxButtons.YesNo) != DialogResult.Yes)
                return false;
             
        }
         
        System.String __dummyScrutVar1 = _CurrentInsert;
        if (__dummyScrutVar1.equals("Textbox"))
        {
            menuInsertTextbox_Click(sender, e);
        }
        else if (__dummyScrutVar1.equals("Chart"))
        {
            menuInsertChart_Click(sender, e);
        }
        else if (__dummyScrutVar1.equals("Rectangle"))
        {
            menuInsertRectangle_Click(sender, e);
        }
        else if (__dummyScrutVar1.equals("Table"))
        {
            menuInsertTable_Click(sender, e);
        }
        else if (__dummyScrutVar1.equals("Matrix"))
        {
            menuInsertMatrix_Click(sender, e);
        }
        else if (__dummyScrutVar1.equals("List"))
        {
            menuInsertList_Click(sender, e);
        }
        else if (__dummyScrutVar1.equals("Line"))
        {
            menuInsertLine_Click(sender, e);
        }
        else if (__dummyScrutVar1.equals("Image"))
        {
            menuInsertImage_Click(sender, e);
        }
        else if (__dummyScrutVar1.equals("Subreport"))
        {
            menuInsertSubreport_Click(sender, e);
        }
        else
        {
        }         
        return true;
    }

    private void drawPanelDoubleClick(Object sender, EventArgs e) throws Exception {
        menuProperties_Click();
    }

    // treat double click like a property menu click
    private void drawPanelRubberBand(Point p1, Point p2) throws Exception {
        // Convert the points to screen coordinates
        p1 = PointToScreen(p1);
        p2 = PointToScreen(p2);
        // Get a rectangle from the two points
        Rectangle rc = drawPanelRectFromPoints(p1,p2);
        // Draw reversibleFrame
        ControlPaint.DrawReversibleFrame(rc, Color.Red, FrameStyle.Dashed);
        return ;
    }

    private Rectangle drawPanelRectFromPoints(Point p1, Point p2) throws Exception {
        Rectangle r = new Rectangle();
        // set the width and x of rectangle
        if (p1.X < p2.X)
        {
            r.X = p1.X;
            r.setWidth(p2.X - p1.X);
        }
        else
        {
            r.X = p2.X;
            r.setWidth(p1.X - p2.X);
        } 
        // set the height and y of rectangle
        if (p1.Y < p2.Y)
        {
            r.Y = p1.Y;
            r.setHeight(p2.Y - p1.Y);
        }
        else
        {
            r.Y = p2.Y;
            r.setHeight(p1.Y - p2.Y);
        } 
        return r;
    }

    private void drawPanelSetCursor(XmlNode node, HitLocationEnum hle) throws Exception {
        Cursor c = new Cursor();
        if (node == null)
            c = Cursors.Arrow;
        else if (StringSupport.equals(node.Name, "Height"))
            c = Cursors.SizeNS;
        else if (hle == HitLocationEnum.TableColumnResize)
            // doesn't need to be selected
            c = Cursors.VSplit;
        else if (hle == HitLocationEnum.TableRowResize)
            // doesn't need to be selected
            c = Cursors.HSplit;
        else if (this._DrawPanel.isNodeSelected(node))
        {
            switch(hle)
            {
                case BottomLeft: 
                case TopRight: 
                    c = Cursors.SizeNESW;
                    break;
                case LeftMiddle: 
                case RightMiddle: 
                    c = Cursors.SizeWE;
                    break;
                case BottomRight: 
                case TopLeft: 
                    c = Cursors.SizeNWSE;
                    break;
                case TopMiddle: 
                case BottomMiddle: 
                    c = Cursors.SizeNS;
                    break;
                case Move: 
                    c = Cursors.SizeAll;
                    break;
                case TableColumnResize: 
                    c = Cursors.VSplit;
                    break;
                case TableRowResize: 
                    c = Cursors.HSplit;
                    break;
                case LineLeft: 
                case LineRight: 
                    //		c = Cursors.Cross;			bug in C# runtime? Cross doesn't work!
                    c = Cursors.Hand;
                    break;
                case Inside: 
                default: 
                    c = Cursors.Arrow;
                    break;
            
            }
        }
        else
            c = Cursors.Arrow;     
        if (c != Cursor.Current)
            Cursor.Current = c;
         
    }

    private void drawPanelMouseWheel(Object sender, MouseEventArgs e) throws Exception {
        int wvalue = new int();
        if (e.Delta < 0)
        {
            if (_vScroll.Value < _vScroll.Maximum)
            {
                wvalue = _vScroll.Value + _vScroll.SmallChange;
                _vScroll.Value = Math.Min(_vScroll.Maximum, wvalue);
                _DrawPanel.Refresh();
            }
             
        }
        else
        {
            if (_vScroll.Value > _vScroll.Minimum)
            {
                wvalue = _vScroll.Value - _vScroll.SmallChange;
                _vScroll.Value = Math.Max(_vScroll.Minimum, wvalue);
                _DrawPanel.Refresh();
            }
             
        } 
    }

    private void drawPanelKeyDown(Object sender, KeyEventArgs e) throws Exception {
        int incX = 0;
        int incY = 0;
        int vScroll = _vScroll.Value;
        int hScroll = _hScroll.Value;
        // Force scroll up and down
        if (e.KeyCode == Keys.Down)
        {
            incY = 1;
        }
        else if (e.KeyCode == Keys.Up)
        {
            incY = -1;
        }
        else if (e.KeyCode == Keys.Left)
        {
            incX = -1;
        }
        else if (e.KeyCode == Keys.Right)
        {
            incX = 1;
        }
        else if (e.KeyCode == Keys.PageDown)
        {
            vScroll = Math.Min(_vScroll.Value + _vScroll.LargeChange, _vScroll.Maximum);
        }
        else if (e.KeyCode == Keys.PageUp)
        {
            vScroll = Math.Max(_vScroll.Value - _vScroll.LargeChange, 0);
        }
        else if (e.KeyCode == Keys.Enter)
        {
            e.Handled = true;
            menuProperties_Click();
            return ;
        }
        else if (e.KeyCode == Keys.Tab)
        {
            if (_DrawPanel.selectNext((Control.ModifierKeys & Keys.Shift) == Keys.Shift))
            {
                RectangleF r = _DrawPanel.GetRectangle(_DrawPanel.getSelectedList()[0]);
                Rectangle nr = new Rectangle(PixelsX(r.X), PixelsY(r.Y), PixelsX(r.Width), PixelsY(r.Height));
                if (nr.Right > _hScroll.Value + Width - _vScroll.Width || nr.getLeft() < _hScroll.Value - _vScroll.Width)
                    hScroll = Math.Min(nr.getLeft(), _hScroll.Maximum);
                 
                if (nr.Bottom > _vScroll.Value + Height - _hScroll.Height || nr.getTop() < _vScroll.Value - _hScroll.Height)
                    vScroll = Math.Min(nr.getTop(), _vScroll.Maximum);
                 
                this.SelectionChanged(this, new EventArgs());
                _DrawPanel.Invalidate();
            }
             
            e.Handled = true;
        }
        else if (e.KeyCode == Keys.Delete)
        {
            this.delete();
            e.Handled = true;
        }
                 
        boolean bRefresh = false;
        if (vScroll != _vScroll.Value)
        {
            _vScroll.Value = Math.Max(vScroll, 0);
            bRefresh = true;
            e.Handled = true;
        }
         
        if (hScroll != _hScroll.Value)
        {
            _hScroll.Value = Math.Max(hScroll, 0);
            bRefresh = true;
            e.Handled = true;
        }
         
        if (incX != 0 || incY != 0)
        {
            HitLocationEnum hle = HitLocationEnum.Move;
            if ((Control.ModifierKeys & Keys.Shift) == Keys.Shift)
            {
                // if shift key on resize
                hle = incX != 0 ? HitLocationEnum.RightMiddle : HitLocationEnum.BottomMiddle;
            }
             
            _Undo.StartUndoGroup("Move");
            if (_DrawPanel.moveSelectedItems(incX,incY,hle))
            {
                _Undo.endUndoGroup(true);
                SelectionMoved(this, new EventArgs());
                ReportChanged(this, new EventArgs());
                _DrawPanel.Invalidate();
            }
            else
                _Undo.endUndoGroup(false); 
            e.Handled = true;
        }
         
        if (bRefresh)
            _DrawPanel.Refresh();
         
    }

    private void designCtl_Layout(Object sender, LayoutEventArgs e) throws Exception {
        _DrawPanel.Location = new Point(0, 0);
        _DrawPanel.Width = this.Width - _vScroll.Width;
        _DrawPanel.Height = this.Height - _hScroll.Height;
        _hScroll.Location = new Point(0, this.Height - _hScroll.Height);
        _hScroll.Width = _DrawPanel.Width;
        _vScroll.Location = new Point(this.Width - _vScroll.Width, _DrawPanel.Location.Y);
        _vScroll.Height = _DrawPanel.Height;
    }

    private void menuCopy_Click(Object sender, EventArgs e) throws Exception {
        Clipboard.SetDataObject(getCopy(), true);
    }

    private String getCopy() throws Exception {
        StringBuilder sb = new StringBuilder();
        // Build XML representing the selected objects
        sb.Append("<ReportItems>");
        for (Object __dummyForeachVar22 : _DrawPanel.getSelectedList())
        {
            // surround by ReportItems element
            XmlNode riNode = (XmlNode)__dummyForeachVar22;
            sb.Append(riNode.OuterXml);
        }
        sb.Append("</ReportItems>");
        return sb.ToString();
    }

    private void menuPaste_Click(Object sender, EventArgs e) throws Exception {
        HitLocation hl = _DrawPanel.HitNode(_MousePosition, PointsX(_hScroll.Value), PointsY(_vScroll.Value));
        XmlNode lNode = hl == null ? null : hl.HitNode;
        if (lNode == null)
            return ;
         
        if (!(StringSupport.equals(lNode.Name, "List") || StringSupport.equals(lNode.Name, "Body") || StringSupport.equals(lNode.Name, "Rectangle") || StringSupport.equals(lNode.Name, "PageHeader") || StringSupport.equals(lNode.Name, "PageFooter")))
        {
            if (hl.HitContainer != null && StringSupport.equals(hl.HitContainer.Name, "Table"))
            {
                //	When table we need to replace the tablecell contents; ask first
                if (MessageBox.Show("Do you want to replace contents of TableCell?", "Paste", MessageBoxButtons.YesNo) != DialogResult.Yes)
                    return ;
                 
                XmlNode repItems = lNode.ParentNode;
                if (!StringSupport.equals(repItems.Name, "ReportItems"))
                    return ;
                 
                XmlNode p = repItems.ParentNode;
                p.RemoveChild(repItems);
                doPaste(p,hl.HitRelative);
            }
             
            return ;
        }
         
        doPaste(lNode,hl.HitRelative);
    }

    private void doPaste(XmlNode lNode, PointF p) throws Exception {
        IDataObject iData = Clipboard.GetDataObject();
        if (iData == null)
            return ;
         
        if (!(iData.GetDataPresent(DataFormats.Text) || iData.GetDataPresent(DataFormats.Bitmap)))
            return ;
         
        if (lNode == null)
        {
            lNode = _DrawPanel.getBody();
            if (lNode == null)
                return ;
             
        }
         
        _Undo.StartUndoGroup("Paste");
        if (iData.GetDataPresent(DataFormats.Text))
        {
            // Build the xml string in case it is a straight pasting of text
            String t = (String)iData.GetData(DataFormats.Text);
            if (!(t.Length >= 27 && StringSupport.equals(t.Substring(0, 13), "<ReportItems>")))
            {
                t = t.Replace("&", "&amp;");
                t = t.Replace("<", "&lt;");
                t = String.Format("<ReportItems><Textbox><Height>12pt</Height><Width>1in</Width><Value>{0}</Value></Textbox></ReportItems>", t);
            }
             
            try
            {
                // PasteReportItems throws exception when you try to paste an illegal object
                _DrawPanel.pasteReportItems(lNode,t,p);
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message, "Paste");
            }
        
        }
        else
        {
            // Build the xml string for an image; but we also need to put an
            //   embedded image into the report.
            System.Drawing.Bitmap bo = (System.Drawing.Bitmap)iData.GetData(DataFormats.Bitmap);
            _DrawPanel.pasteImage(lNode,bo,p);
        } 
        _Undo.endUndoGroup();
        _DrawPanel.Invalidate();
        ReportChanged(this, new EventArgs());
        SelectionChanged(this, new EventArgs());
    }

    private void menuDelete_Click(Object sender, EventArgs e) throws Exception {
        delete();
    }

    private void menuInsertChart_Click(Object sender, EventArgs e) throws Exception {
        HitLocation hl = _DrawPanel.HitContainer(_MousePosition, PointsX(_hScroll.Value), PointsY(_vScroll.Value));
        if (hl == null || hl.HitContainer == null)
            return ;
         
        // Charts aren't allowed in PageHeader or PageFooter
        if (_DrawPanel.inPageHeaderOrFooter(hl.HitContainer))
        {
            MessageBox.Show("Charts can only be inserted in the body of the report.", "Insert");
            return ;
        }
         
        _Undo.StartUndoGroup("Insert Chart");
        DialogNewChart dnc = new DialogNewChart(this._DrawPanel,hl.HitContainer);
        DialogResult dr = dnc.ShowDialog(this);
        if (dr != DialogResult.OK)
        {
            _Undo.endUndoGroup(false);
            return ;
        }
         
        XmlNode chart = new XmlNode();
        if (StringSupport.equals(hl.HitContainer.Name, "Table"))
        {
            chart = _DrawPanel.replaceTableMatrixOrChart(hl,dnc.getChartXml());
        }
        else
            chart = _DrawPanel.pasteTableMatrixOrChart(hl.HitContainer,dnc.getChartXml(),hl.HitRelative); 
        if (chart == null)
        {
            _Undo.endUndoGroup(false);
            return ;
        }
         
        _Undo.endUndoGroup(true);
        ReportChanged(this, new EventArgs());
        SelectionChanged(this, new EventArgs());
        ReportItemInserted(this, new EventArgs());
        _DrawPanel.Invalidate();
        // Now bring up the property dialog
        List<XmlNode> ar = new List<XmlNode>();
        ar.Add(chart);
        _Undo.StartUndoGroup("Dialog");
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.ReportItems);
        dr = pd.ShowDialog(this);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
            _Undo.endUndoGroup(false); 
        setFocus();
    }

    private void menuInsertCustomReportItem_Click(Object sender, EventArgs e) throws Exception {
        String ri = new String();
        ICustomReportItem cri = null;
        try
        {
            MenuItem mi = sender instanceof MenuItem ? (MenuItem)sender : (MenuItem)null;
            cri = RdlEngineConfig.createCustomReportItem((String)mi.Tag);
            ri = "<ReportItems>" + String.Format(cri.getCustomReportItemXml(), mi.Tag) + "</ReportItems>";
        }
        catch (Exception ex)
        {
            // substitute the type of custom report item
            MessageBox.Show(String.Format("Exception building CustomReportItem insert: {0}", ex.Message), "Insert");
            return ;
        }
        finally
        {
            if (cri != null)
                cri.Dispose();
             
        }
        menuInsertReportItem(sender,e,ri);
    }

    private void menuInsertLine_Click(Object sender, EventArgs e) throws Exception {
        String ri = "<ReportItems><Line><Height>0in</Height><Width>1in</Width><Style><BorderStyle><Default>Solid</Default></BorderStyle></Style></Line></ReportItems>";
        menuInsertReportItem(sender,e,ri);
    }

    private void menuInsertList_Click(Object sender, EventArgs e) throws Exception {
        String ri = "<ReportItems><List><Height>1.5in</Height><Width>1.5in</Width></List></ReportItems>";
        // Lists aren't allowed in PageHeader or PageFooter
        HitLocation hl = _DrawPanel.HitContainer(_MousePosition, PointsX(_hScroll.Value), PointsY(_vScroll.Value));
        if (hl == null || hl.HitContainer == null)
            return ;
         
        if (_DrawPanel.inPageHeaderOrFooter(hl.HitContainer))
        {
            MessageBox.Show("Lists can only be inserted in the body of the report.", "Insert");
            return ;
        }
         
        menuInsertReportItem(hl,ri);
    }

    private void menuInsertImage_Click(Object sender, EventArgs e) throws Exception {
        String ri = "<ReportItems><Image><Height>1.5in</Height><Width>1.5in</Width></Image></ReportItems>";
        menuInsertReportItem(sender,e,ri);
    }

    private void menuInsertMatrix_Click(Object sender, EventArgs e) throws Exception {
        HitLocation hl = _DrawPanel.HitContainer(_MousePosition, PointsX(_hScroll.Value), PointsY(_vScroll.Value));
        if (hl == null || hl.HitContainer == null)
            return ;
         
        // Matrixs aren't allowed in PageHeader or PageFooter
        if (_DrawPanel.inPageHeaderOrFooter(hl.HitContainer))
        {
            MessageBox.Show("Matrixs can only be inserted in the body of the report.", "Insert");
            return ;
        }
         
        _Undo.StartUndoGroup("Insert Matrix");
        DialogNewMatrix dnm = new DialogNewMatrix(this._DrawPanel,hl.HitContainer);
        DialogResult dr = dnm.ShowDialog(this);
        if (dr != DialogResult.OK)
        {
            _Undo.endUndoGroup(false);
            return ;
        }
         
        XmlNode matrix = new XmlNode();
        if (StringSupport.equals(hl.HitContainer.Name, "Table"))
            matrix = _DrawPanel.replaceTableMatrixOrChart(hl,dnm.getMatrixXml());
        else
            matrix = _DrawPanel.pasteTableMatrixOrChart(hl.HitContainer,dnm.getMatrixXml(),hl.HitRelative); 
        if (matrix == null)
        {
            _Undo.endUndoGroup(false);
            return ;
        }
         
        _Undo.endUndoGroup(true);
        ReportChanged(this, new EventArgs());
        SelectionChanged(this, new EventArgs());
        ReportItemInserted(this, new EventArgs());
        _DrawPanel.Invalidate();
        // Now bring up the property dialog
        List<XmlNode> ar = new List<XmlNode>();
        ar.Add(matrix);
        _Undo.StartUndoGroup("Dialog");
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.ReportItems);
        dr = pd.ShowDialog(this);
        _Undo.endUndoGroup(pd.getChanged() || dr == DialogResult.OK);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
         
        setFocus();
    }

    private void menuInsertRectangle_Click(Object sender, EventArgs e) throws Exception {
        String ri = "<ReportItems><Rectangle><Height>1.5in</Height><Width>1.5in</Width></Rectangle></ReportItems>";
        menuInsertReportItem(sender,e,ri);
    }

    private void menuInsertReportItem(Object sender, EventArgs e, String reportItem) throws Exception {
        HitLocation hl = _DrawPanel.HitContainer(_MousePosition, PointsX(_hScroll.Value), PointsY(_vScroll.Value));
        if (hl == null || hl.HitContainer == null)
            return ;
         
        menuInsertReportItem(hl,reportItem);
    }

    private void menuInsertReportItem(HitLocation hl, String reportItem) throws Exception {
        _Undo.StartUndoGroup("Insert");
        try
        {
            if (StringSupport.equals(hl.HitContainer.Name, "Table"))
            {
                _DrawPanel.replaceReportItems(hl,reportItem);
            }
            else
                _DrawPanel.pasteReportItems(hl.HitContainer,reportItem,hl.HitRelative); 
        }
        catch (Exception ex)
        {
            MessageBox.Show("Internal error: illegal insert syntax:" + Environment.NewLine + reportItem + Environment.NewLine + ex.Message);
            return ;
        }
        finally
        {
            _Undo.endUndoGroup(true);
        }
        _DrawPanel.Invalidate();
        ReportChanged(this, new EventArgs());
        SelectionChanged(this, new EventArgs());
        ReportItemInserted(this, new EventArgs());
        menuProperties_Click();
    }

    private void menuInsertSubreport_Click(Object sender, EventArgs e) throws Exception {
        // Subreports aren't allowed in PageHeader or PageFooter
        HitLocation hl = _DrawPanel.HitContainer(_MousePosition, PointsX(_hScroll.Value), PointsY(_vScroll.Value));
        if (hl == null || hl.HitContainer == null)
            return ;
         
        if (_DrawPanel.inPageHeaderOrFooter(hl.HitContainer))
        {
            MessageBox.Show("Subreports can only be inserted in the body of the report.", "Insert");
            return ;
        }
         
        String ri = "<ReportItems><Subreport><Height>1.5in</Height><Width>1.5in</Width></Subreport></ReportItems>";
        menuInsertReportItem(hl,ri);
    }

    private void menuInsertTable_Click(Object sender, EventArgs e) throws Exception {
        HitLocation hl = _DrawPanel.HitContainer(_MousePosition, PointsX(_hScroll.Value), PointsY(_vScroll.Value));
        if (hl == null || hl.HitContainer == null)
            return ;
         
        // Tables aren't allowed in PageHeader or PageFooter
        if (_DrawPanel.inPageHeaderOrFooter(hl.HitContainer))
        {
            MessageBox.Show("Tables can only be inserted in the body of the report.", "Insert");
            return ;
        }
         
        _Undo.StartUndoGroup("Insert Table");
        DialogNewTable dnt = new DialogNewTable(this._DrawPanel,hl.HitContainer);
        DialogResult dr = dnt.ShowDialog(this);
        if (dr != DialogResult.OK)
        {
            _Undo.endUndoGroup(false);
            return ;
        }
         
        XmlNode table = new XmlNode();
        if (StringSupport.equals(hl.HitContainer.Name, "Table"))
            table = _DrawPanel.replaceTableMatrixOrChart(hl,dnt.getTableXml());
        else
            table = _DrawPanel.pasteTableMatrixOrChart(hl.HitContainer,dnt.getTableXml(),hl.HitRelative); 
        if (table == null)
        {
            _Undo.endUndoGroup(false);
            return ;
        }
         
        _Undo.endUndoGroup(true);
        ReportChanged(this, new EventArgs());
        SelectionChanged(this, new EventArgs());
        ReportItemInserted(this, new EventArgs());
        _DrawPanel.Invalidate();
        // Now bring up the property dialog
        List<XmlNode> ar = new List<XmlNode>();
        ar.Add(table);
        _Undo.StartUndoGroup("Dialog");
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.ReportItems);
        dr = pd.ShowDialog(this);
        _Undo.endUndoGroup(pd.getChanged() || dr == DialogResult.OK);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
         
        setFocus();
    }

    private void menuInsertTextbox_Click(Object sender, EventArgs e) throws Exception {
        String ri = "<ReportItems><Textbox><Height>12pt</Height><Width>1in</Width><Value>Text</Value></Textbox></ReportItems>";
        menuInsertReportItem(sender,e,ri);
    }

    private void menuOpenSubreport_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedList().Count == 0)
            return ;
         
        XmlNode sr = _DrawPanel.getSelectedList()[0];
        if (!StringSupport.equals(sr.Name, "Subreport"))
            return ;
         
        String name = _DrawPanel.getElementValue(sr,"ReportName","");
        if (name == null || name.Length == 0)
            return ;
         
        if (OpenSubreport != null)
            OpenSubreport.invoke(this,new SubReportEventArgs(name));
         
    }

    private void menuSelectAll_Click(Object sender, EventArgs e) throws Exception {
        doSelectAll();
    }

    private void doSelectAll() throws Exception {
        IEnumerable list = _DrawPanel.getReportItems(null);
        // get all the report items
        if (list == null)
            return ;
         
        _DrawPanel.clearSelected();
        for (Object __dummyForeachVar23 : list)
        {
            XmlNode riNode = (XmlNode)__dummyForeachVar23;
            if (StringSupport.equals(riNode.Name, "Table") || StringSupport.equals(riNode.Name, "List") || StringSupport.equals(riNode.Name, "Rectangle"))
                continue;
             
            // we'll just select all the sub objects in these containers
            _DrawPanel.addSelection(riNode);
            SelectionChanged(this, new EventArgs());
        }
        _DrawPanel.Invalidate();
        return ;
    }

    public void menuProperties_Click(Object sender, EventArgs e) throws Exception {
        menuProperties_Click();
    }

    public void menuProperties_Click() throws Exception {
        if (_DrawPanel.getSelectedCount() > 0)
        {
            doPropertyDialog(PropertyTypeEnum.ReportItems);
        }
        else
        {
            // Put up the Report Property sheets
            doPropertyDialog(PropertyTypeEnum.Report);
        } 
        SelectionChanged(this, new EventArgs());
    }

    private void menuChartDeleteGrouping_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        MenuItem menu = sender instanceof MenuItem ? (MenuItem)sender : (MenuItem)null;
        if (menu == null)
            return ;
         
        String gname = menu.Text;
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _DrawPanel.clearSelected();
        this.SelectionChanged(this, new EventArgs());
        _Undo.StartUndoGroup("Delete Grouping");
        boolean bSuccess = false;
        if (_DrawPanel.deleteChartGrouping(cNode,gname))
        {
            bSuccess = true;
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
         
        _Undo.endUndoGroup(bSuccess);
    }

    private void menuChartEditGrouping_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        MenuItem menu = sender instanceof MenuItem ? (MenuItem)sender : (MenuItem)null;
        if (menu == null)
            return ;
         
        String gname = menu.Text;
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        XmlNode group = _DrawPanel.getChartGrouping(cNode,gname);
        List<XmlNode> ar = new List<XmlNode>();
        // need to put this is a list for dialog to handle
        ar.Add(group.ParentNode);
        _Undo.StartUndoGroup("Dialog Grouping");
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.Grouping);
        DialogResult dr = pd.ShowDialog(this);
        _Undo.endUndoGroup(pd.getChanged() || dr == DialogResult.OK);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
         
    }

    private void menuChartInsertCategoryGrouping_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        _Undo.StartUndoGroup("Insert Category Grouping");
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        XmlNode colGroup = _DrawPanel.insertChartCategoryGrouping(cNode);
        if (colGroup == null)
        {
            _Undo.endUndoGroup(false);
            return ;
        }
         
        List<XmlNode> ar = new List<XmlNode>();
        // need to put this is a list for dialog to handle
        ar.Add(colGroup);
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.Grouping);
        DialogResult dr = pd.ShowDialog(this);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
        {
            _DrawPanel.deleteChartGrouping(colGroup);
            _Undo.endUndoGroup(false);
        } 
    }

    private void menuChartInsertSeriesGrouping_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        _Undo.StartUndoGroup("Insert Series Grouping");
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        XmlNode colGroup = _DrawPanel.insertChartSeriesGrouping(cNode);
        if (colGroup == null)
        {
            _Undo.endUndoGroup(false);
            return ;
        }
         
        List<XmlNode> ar = new List<XmlNode>();
        // need to put this is a list for dialog to handle
        ar.Add(colGroup);
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.Grouping);
        DialogResult dr = pd.ShowDialog(this);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
        {
            _DrawPanel.deleteChartGrouping(colGroup);
            _Undo.endUndoGroup(false);
        } 
    }

    private void menuPropertiesLegend_Click(Object sender, EventArgs e) throws Exception {
        doPropertyDialog(PropertyTypeEnum.ChartLegend);
    }

    private void menuPropertiesCategoryAxis_Click(Object sender, EventArgs e) throws Exception {
        doPropertyDialog(PropertyTypeEnum.CategoryAxis);
    }

    private void menuPropertiesValueAxis_Click(Object sender, EventArgs e) throws Exception {
        doPropertyDialog(PropertyTypeEnum.ValueAxis);
    }

    private void menuPropertiesCategoryAxisTitle_Click(Object sender, EventArgs e) throws Exception {
        doPropertyDialog(PropertyTypeEnum.CategoryAxisTitle);
    }

    private void menuPropertiesValueAxisTitle_Click(Object sender, EventArgs e) throws Exception {
        doPropertyDialog(PropertyTypeEnum.ValueAxisTitle);
    }

    private void menuPropertiesChartTitle_Click(Object sender, EventArgs e) throws Exception {
        doPropertyDialog(PropertyTypeEnum.ChartTitle);
    }

    private void menuMatrixProperties_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode riNode = _DrawPanel.getSelectedList()[0];
        XmlNode table = _DrawPanel.getMatrixFromReportItem(riNode);
        if (table == null)
            return ;
         
        List<XmlNode> ar = new List<XmlNode>();
        // need to put this is a list for dialog to handle
        ar.Add(table);
        _Undo.StartUndoGroup("Matrix Dialog");
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.ReportItems);
        DialogResult dr = pd.ShowDialog(this);
        _Undo.endUndoGroup(pd.getChanged() || dr == DialogResult.OK);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
         
    }

    private void menuMatrixDelete_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        _Undo.StartUndoGroup("Matrix Delete");
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _DrawPanel.clearSelected();
        this.SelectionChanged(this, new EventArgs());
        if (_DrawPanel.deleteMatrix(cNode))
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
            _Undo.endUndoGroup(false); 
    }

    private void menuMatrixDeleteGroup_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        MenuItem menu = sender instanceof MenuItem ? (MenuItem)sender : (MenuItem)null;
        if (menu == null)
            return ;
         
        _Undo.StartUndoGroup("Matrix Delete Group");
        String gname = menu.Text;
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _DrawPanel.clearSelected();
        this.SelectionChanged(this, new EventArgs());
        if (_DrawPanel.deleteMatrixGroup(cNode,gname))
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
            _Undo.endUndoGroup(false); 
    }

    private void menuMatrixEditGroup_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        MenuItem menu = sender instanceof MenuItem ? (MenuItem)sender : (MenuItem)null;
        if (menu == null)
            return ;
         
        String gname = menu.Text;
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        XmlNode group = _DrawPanel.getMatrixGroup(cNode,gname);
        List<XmlNode> ar = new List<XmlNode>();
        // need to put this is a list for dialog to handle
        ar.Add(group.ParentNode);
        _Undo.StartUndoGroup("Matrix Edit");
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.Grouping);
        DialogResult dr = pd.ShowDialog(this);
        _Undo.endUndoGroup(pd.getChanged() || dr == DialogResult.OK);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
         
    }

    private void menuMatrixInsertColumnGroup_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        XmlNode colGroup = _DrawPanel.insertMatrixColumnGroup(cNode);
        if (colGroup == null)
            return ;
         
        List<XmlNode> ar = new List<XmlNode>();
        // need to put this is a list for dialog to handle
        ar.Add(colGroup);
        _Undo.StartUndoGroup("Matrix Insert Column Group");
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.Grouping);
        DialogResult dr = pd.ShowDialog(this);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
        {
            _DrawPanel.deleteMatrixGroup(colGroup);
            _Undo.endUndoGroup(false);
        } 
    }

    private void menuMatrixInsertRowGroup_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        XmlNode rowGroup = _DrawPanel.insertMatrixRowGroup(cNode);
        if (rowGroup == null)
            return ;
         
        List<XmlNode> ar = new List<XmlNode>();
        // need to put this is a list for dialog to handle
        ar.Add(rowGroup);
        _Undo.StartUndoGroup("Matrix Insert Row Group");
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.Grouping);
        DialogResult dr = pd.ShowDialog(this);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
        {
            _DrawPanel.deleteMatrixGroup(rowGroup);
            _Undo.endUndoGroup(false);
        } 
    }

    private void menuTableDeleteColumn_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _Undo.StartUndoGroup("Delete Table Column");
        _DrawPanel.clearSelected();
        this.SelectionChanged(this, new EventArgs());
        if (_DrawPanel.deleteTableColumn(cNode))
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
            _Undo.endUndoGroup(false); 
    }

    private void menuTableDelete_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _Undo.StartUndoGroup("Delete Table");
        _DrawPanel.clearSelected();
        this.SelectionChanged(this, new EventArgs());
        if (_DrawPanel.deleteTable(cNode))
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
            _Undo.endUndoGroup(false); 
    }

    private void menuTableDeleteRow_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _Undo.StartUndoGroup("Delete Table Row");
        _DrawPanel.clearSelected();
        this.SelectionChanged(this, new EventArgs());
        if (_DrawPanel.deleteTableRow(cNode))
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
            _Undo.endUndoGroup(false); 
    }

    private void menuTableDeleteGroup_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        MenuItem menu = sender instanceof MenuItem ? (MenuItem)sender : (MenuItem)null;
        if (menu == null)
            return ;
         
        String gname = menu.Text;
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _Undo.StartUndoGroup("Delete Table Group");
        _DrawPanel.clearSelected();
        this.SelectionChanged(this, new EventArgs());
        if (_DrawPanel.deleteTableGroup(cNode,gname))
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
            _Undo.endUndoGroup(false); 
    }

    private void menuTableEditGroup_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        MenuItem menu = sender instanceof MenuItem ? (MenuItem)sender : (MenuItem)null;
        if (menu == null)
            return ;
         
        String gname = menu.Text;
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _Undo.StartUndoGroup("Dialog Table Group Edit");
        XmlNode tblGroup = _DrawPanel.getTableGroup(cNode,gname);
        List<XmlNode> ar = new List<XmlNode>();
        // need to put this is a list for dialog to handle
        ar.Add(tblGroup);
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.Grouping);
        DialogResult dr = pd.ShowDialog(this);
        _Undo.endUndoGroup(pd.getChanged() || dr == DialogResult.OK);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
         
    }

    private void menuTableInsertColumnBefore_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _Undo.StartUndoGroup("Insert Table Column");
        if (_DrawPanel.insertTableColumn(cNode,true))
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
            _Undo.endUndoGroup(false); 
    }

    private void menuTableInsertColumnAfter_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _Undo.StartUndoGroup("Insert Table Column");
        if (_DrawPanel.insertTableColumn(cNode,false))
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
            _Undo.endUndoGroup(false); 
    }

    private void menuTableInsertGroup_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _Undo.StartUndoGroup("Insert Table Group");
        XmlNode tblGroup = _DrawPanel.insertTableGroup(cNode);
        if (tblGroup == null)
        {
            _Undo.endUndoGroup(false);
            return ;
        }
         
        List<XmlNode> ar = new List<XmlNode>();
        // need to put this is a list for dialog to handle
        ar.Add(tblGroup);
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.Grouping);
        DialogResult dr = pd.ShowDialog(this);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
        {
            _DrawPanel.deleteTableGroup(tblGroup);
            _Undo.endUndoGroup(false);
        } 
    }

    private void menuTableInsertRowBefore_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _Undo.StartUndoGroup("Insert Table Row");
        if (_DrawPanel.insertTableRow(cNode,true))
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
            _Undo.endUndoGroup(false); 
    }

    private void menuTableInsertRowAfter_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode cNode = _DrawPanel.getSelectedList()[0];
        _Undo.StartUndoGroup("Insert Table Row");
        if (_DrawPanel.insertTableRow(cNode,false))
        {
            _Undo.endUndoGroup(true);
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
        else
            _Undo.endUndoGroup(false); 
    }

    private void menuTableProperties_Click(Object sender, EventArgs e) throws Exception {
        if (_DrawPanel.getSelectedCount() != 1)
            return ;
         
        XmlNode riNode = _DrawPanel.getSelectedList()[0];
        XmlNode table = _DrawPanel.getTableFromReportItem(riNode);
        if (table == null)
            return ;
         
        XmlNode tc = _DrawPanel.getTableColumn(riNode);
        XmlNode tr = _DrawPanel.getTableRow(riNode);
        List<XmlNode> ar = new List<XmlNode>();
        // need to put this is a list for dialog to handle
        ar.Add(table);
        _Undo.StartUndoGroup("Table Dialog");
        PropertyDialog pd = new PropertyDialog(_DrawPanel, ar, PropertyTypeEnum.ReportItems, tc, tr);
        DialogResult dr = pd.ShowDialog(this);
        _Undo.endUndoGroup(pd.getChanged() || dr == DialogResult.OK);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
         
    }

    private void doPropertyDialog(PropertyTypeEnum type) throws Exception {
        this.startUndoGroup("Dialog");
        PropertyDialog pd = new PropertyDialog(_DrawPanel,_DrawPanel.getSelectedList(),type);
        DialogResult dr = pd.ShowDialog(this);
        this.endUndoGroup(pd.getChanged() || dr == DialogResult.OK);
        if (pd.getChanged() || dr == DialogResult.OK)
        {
            ReportChanged(this, new EventArgs());
            _DrawPanel.Invalidate();
        }
         
        setFocus();
    }

    public void setFocus() throws Exception {
        this._DrawPanel.Focus();
    }

    private void menuContext_Popup(Object sender, EventArgs e) throws Exception {
        boolean bEnable = _DrawPanel.getSelectedCount() <= 0 ? false : true;
        menuCopy.Enabled = bEnable;
        menuDelete.Enabled = bEnable;
        List<XmlNode> al = new List<XmlNode>();
        IDataObject iData = Clipboard.GetDataObject();
        if (iData == null)
            bEnable = false;
        else if (iData.GetDataPresent(al.GetType()))
            bEnable = true;
        else if (iData.GetDataPresent(DataFormats.Text))
            bEnable = true;
        else if (iData.GetDataPresent(DataFormats.Bitmap))
            bEnable = true;
        else
            bEnable = false;    
        menuPaste.Enabled = bEnable;
        return ;
    }

}


