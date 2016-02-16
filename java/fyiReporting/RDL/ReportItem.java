//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Action;
import fyiReporting.RDL.Body;
import fyiReporting.RDL.Chart;
import fyiReporting.RDL.Custom;
import fyiReporting.RDL.DataElementOutputEnum;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Line;
import fyiReporting.RDL.List;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.MatrixCellEntry;
import fyiReporting.RDL.MatrixRow;
import fyiReporting.RDL.Name;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.PageFooter;
import fyiReporting.RDL.PageHeader;
import fyiReporting.RDL.PageItem;
import fyiReporting.RDL.PageLine;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.Rectangle;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportItems;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.Style;
import fyiReporting.RDL.StyleInfo;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.TableCell;
import fyiReporting.RDL.TableColumn;
import fyiReporting.RDL.TableRow;
import fyiReporting.RDL.Textbox;
import fyiReporting.RDL.Visibility;
import fyiReporting.RDL.XmlUtil;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
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
* Base class of all display items in a report.  e.g. Textbox, Matrix, Table, ...
*/
public class ReportItem  extends ReportLink implements IComparable
{
    Name _Name;
    // Name of the report item
    Style _Style;
    // Style information for the element
    Action _Action;
    // An action (e.g. a hyperlink) associated with
    // the ReportItem
    RSize _Top;
    // The distance of the item from the top of the
    // containing object.
    // Defaults to 0 if omitted.
    RSize _Left;
    // The distance of the item from the left of the
    // containing object.  Defaults to 0 if omitted.
    RSize _Height;
    // Height of the item. Negative sizes allowed
    // only for lines (The height/width gives the
    // offset of the endpoint of the line from the start
    // point).
    //Defaults to the height of the containing object
    //minus Top if omitted.
    RSize _Width;
    // Width of the item. Negative sizes allowed
    // only for lines.
    // Defaults to the width of the containing object
    // minus Left if omitted.
    int _ZIndex = new int();
    // Drawing order of the report item within the
    // containing object. Items with lower indices
    // are drawn first (appearing behind items with
    // higher indices). Items with equal indices
    // have an unspecified order.
    // Default: 0 Min: 0 Max: 2147483647
    Visibility _Visibility;
    // Indicates if the item should be hidden.
    Expression _ToolTip;
    // (string) A textual label for the report item. Used for
    // such things as including TITLE and ALT
    // attributes in HTML reports.
    Expression _Label;
    // A label to identify an instance of a report item
    // (Variant) within the client UI (to provide a user-friendly
    // label for searching)
    //Hierarchical listing of report item and group
    //labels within the UI (the Document Map)
    //should reflect the object containment
    //hierarchy in the report definition. Peer items
    //should be listed in left-to-right top-to-bottom
    //order.
    //If the expression returns null, no item is added
    //to the Document Map. Not used for report
    //items in the page header or footer.
    String _LinkToChild = new String();
    // The name of a report item contained directly
    //within this report item that is the target
    //location for the Document Map label (if any).
    //Ignored if Label is not present. Used only for
    //Rectangle.
    Expression _Bookmark;
    // (string)A bookmark that can be linked to via a
    // Bookmark action
    String _RepeatWith = new String();
    // The name of a data region that this report item
    // should be repeated with if that data region
    // spans multiple pages.
    //The data region must be in the same
    //ReportItems collection as this ReportItem
    //(Since data regions are not allowed in page
    //headers/footers, this means RepeatWith will
    //be unusable in page headers/footers).
    //Not allowed if this report item is a data
    //region, subreport or rectangle that contains a
    //data region or subreport.
    Custom _Custom;
    // Custom information to be handed to a report
    //  output component.
    String _DataElementName = new String();
    //The name to use for the data element/attribute
    // for this report item.
    // Default: Name of the report item
    DataElementOutputEnum _DataElementOutput = DataElementOutputEnum.Output;
    // should item appear in data rendering?
    TableCell _TC;
    // TableCell- if part of a Table
    List<ReportItem> _YParents = new List<ReportItem>();
    // calculated: when calculating the y position these are the items above it
    boolean _InMatrix = new boolean();
    // true if reportitem is in a matrix
    public ReportItem(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Name = null;
        _Style = null;
        _Action = null;
        _Top = null;
        _Left = null;
        _Height = null;
        _Width = null;
        _ZIndex = 0;
        _Visibility = null;
        _ToolTip = null;
        _Label = null;
        _LinkToChild = null;
        _Bookmark = null;
        _RepeatWith = null;
        _Custom = null;
        _DataElementName = null;
        _DataElementOutput = DataElementOutputEnum.Auto;
        for (Object __dummyForeachVar0 : xNode.Attributes)
        {
            // Run thru the attributes
            XmlAttribute xAttr = (XmlAttribute)__dummyForeachVar0;
            Name __dummyScrutVar0 = xAttr.Name;
            if (__dummyScrutVar0.equals("Name"))
            {
                _Name = new Name(xAttr.Value);
            }
             
        }
    }

    public boolean reportItemElement(XmlNode xNodeLoop) throws Exception {
        Name __dummyScrutVar1 = xNodeLoop.Name;
        if (__dummyScrutVar1.equals("Style"))
        {
            _Style = new Style(OwnerReport,this,xNodeLoop);
        }
        else if (__dummyScrutVar1.equals("Action"))
        {
            _Action = new Action(OwnerReport,this,xNodeLoop);
        }
        else if (__dummyScrutVar1.equals("Top"))
        {
            _Top = new RSize(OwnerReport,xNodeLoop);
        }
        else if (__dummyScrutVar1.equals("Left"))
        {
            _Left = new RSize(OwnerReport,xNodeLoop);
        }
        else if (__dummyScrutVar1.equals("Height"))
        {
            _Height = new RSize(OwnerReport,xNodeLoop);
        }
        else if (__dummyScrutVar1.equals("Width"))
        {
            _Width = new RSize(OwnerReport,xNodeLoop);
        }
        else if (__dummyScrutVar1.equals("ZIndex"))
        {
            _ZIndex = XmlUtil.Integer(xNodeLoop.InnerText);
        }
        else if (__dummyScrutVar1.equals("Visibility"))
        {
            _Visibility = new Visibility(OwnerReport,this,xNodeLoop);
        }
        else if (__dummyScrutVar1.equals("ToolTip"))
        {
            _ToolTip = new Expression(OwnerReport,this,xNodeLoop,ExpressionType.String);
        }
        else if (__dummyScrutVar1.equals("Label"))
        {
            _Label = new Expression(OwnerReport,this,xNodeLoop,ExpressionType.Variant);
        }
        else if (__dummyScrutVar1.equals("LinkToChild"))
        {
            _LinkToChild = xNodeLoop.InnerText;
        }
        else if (__dummyScrutVar1.equals("Bookmark"))
        {
            _Bookmark = new Expression(OwnerReport,this,xNodeLoop,ExpressionType.String);
        }
        else if (__dummyScrutVar1.equals("RepeatWith"))
        {
            _RepeatWith = xNodeLoop.InnerText;
        }
        else if (__dummyScrutVar1.equals("Custom"))
        {
            _Custom = new Custom(OwnerReport,this,xNodeLoop);
        }
        else if (__dummyScrutVar1.equals("DataElementName"))
        {
            _DataElementName = xNodeLoop.InnerText;
        }
        else if (__dummyScrutVar1.equals("DataElementOutput"))
        {
            _DataElementOutput = fyiReporting.RDL.DataElementOutput.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
        }
        else
        {
            return false;
        }                
        return true;
    }

    // Not a report item element
    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Style != null)
            _Style.finalPass();
         
        if (_Action != null)
            _Action.finalPass();
         
        if (_Visibility != null)
            _Visibility.finalPass();
         
        if (_ToolTip != null)
            _ToolTip.finalPass();
         
        if (_Label != null)
            _Label.finalPass();
         
        if (_Bookmark != null)
            _Bookmark.finalPass();
         
        if (_Custom != null)
            _Custom.finalPass();
         
        if (Parent.Parent instanceof TableCell)
        {
            // This is part of a table
            _TC = Parent.Parent instanceof TableCell ? (TableCell)Parent.Parent : (TableCell)null;
        }
        else
        {
            _TC = null;
        } 
        // Determine if ReportItem is defined inside of a Matrix
        _InMatrix = false;
        for (ReportLink rl = this.Parent;rl != null;rl = rl.Parent)
        {
            if (rl instanceof Matrix)
            {
                _InMatrix = true;
                break;
            }
             
            if (rl instanceof Table || rl instanceof List || rl instanceof Chart)
                break;
             
        }
        return ;
    }

    public void positioningFinalPass(int i, List<ReportItem> items) throws Exception {
        if (items.Count == 1 || i == 0)
            return ;
         
        // Nothing to do if only one item in list or 1st item in list
        int x = this.getLeft() == null ? 0 : this.getLeft().getSize();
        int w = positioningWidth(this);
        int right = x + w;
        int y = (this.getTop() == null ? 0 : this.getTop().getSize());
        if (this instanceof Line)
        {
            // normalize the width
            if (w < 0)
            {
                x -= w;
                w = -w;
            }
             
        }
         
        this._YParents = new List<ReportItem>();
        int maxParents = 100;
        for (int ti = i - 1;ti >= 0 && maxParents > 0;ti--)
        {
            // heuristic to limit size of parents; otherwise processing in
            //   extreme cases can blow up
            ReportItem ri = items[ti];
            int xw = ri.getLeft() == null ? 0 : ri.getLeft().getSize();
            int w2 = positioningWidth(ri);
            if (ri instanceof Line)
            {
                // normalize the width
                if (w2 < 0)
                {
                    xw -= w2;
                    w2 = -w2;
                }
                 
            }
             
            if (ri.getHeight() == null || ri.getTop() == null)
                continue;
             
            // if position/height not specified don't use to reposition
            if (y < ri.getTop().getSize() + ri.getHeight().getSize())
                continue;
             
            _YParents.Add(ri);
            // X coordinate overlap
            maxParents--;
            // if item above completely covers the report item then it will be pushed down first
            if (xw <= x && xw + w2 >= x + w && maxParents > 30)
                //   and we haven't already set the maxParents.
                maxParents = 30;
             
        }
        //   just add a few more if necessary
        //foreach (ReportItem ri in items)
        //{
        //    if (ri == this)
        //        break;
        //    int xw = ri.Left == null ? 0 : ri.Left.Size;
        //    int w2 = PositioningWidth(ri);
        //    if (ri is Line)
        //    {   // normalize the width
        //        if (w2 < 0)
        //        {
        //            xw -= w2;
        //            w2 = -w2;
        //        }
        //    }
        //    //if (xw > right || x > xw + w2)                    // this allows items to be repositioned only based on what's above them
        //    //    continue;
        //    if (ri.Height == null || ri.Top == null)          // if position/height not specified don't use to reposition
        //        continue;
        //    if (y < ri.Top.Size + ri.Height.Size)
        //        continue;
        //    _YParents.Add(ri);		// X coordinate overlap
        //}
        // Reduce the overhead
        if (this._YParents.Count == 0)
            this._YParents = null;
        else
            this._YParents.TrimExcess(); 
        return ;
    }

    int positioningWidth(ReportItem ri) throws Exception {
        int w = new int();
        if (ri.getWidth() == null)
        {
            if (ri instanceof Table)
            {
                Table t = ri instanceof Table ? (Table)ri : (Table)null;
                w = t.getWidthInUnits();
            }
            else
                w = int.MaxValue / 2; 
        }
        else
            // MaxValue/2 is just meant to be a large number (but won't overflow when adding in the x)
            w = ri.getWidth().getSize(); 
        return w;
    }

    public void run(IPresent ip, Row row) throws Exception {
        return ;
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        return ;
    }

    public boolean isTableOrMatrixCell(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getWC(rpt);
        return (_TC != null || wc.MC != null || this._InMatrix);
    }

    public Name getName() throws Exception {
        return _Name;
    }

    public void setName(Name value) throws Exception {
        _Name = value;
    }

    public Style getStyle() throws Exception {
        return _Style;
    }

    public void setStyle(Style value) throws Exception {
        _Style = value;
    }

    public Action getAction() throws Exception {
        return _Action;
    }

    public void setAction(Action value) throws Exception {
        _Action = value;
    }

    public RSize getTop() throws Exception {
        return _Top;
    }

    public void setTop(RSize value) throws Exception {
        _Top = value;
    }

    public RSize getLeft() throws Exception {
        return _Left;
    }

    public void setLeft(RSize value) throws Exception {
        _Left = value;
    }

    public float leftCalc(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getWC(rpt);
        if (_TC != null || wc.MC != null || _Left == null)
            return 0;
        else
            return _Left.getPoints(); 
    }

    public float getOffsetCalc(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getWC(rpt);
        float x = new float();
        if (this._TC != null)
        {
            // must be part of a table
            Table t = _TC.getOwnerTable();
            int colindex = _TC.getColIndex();
            TableColumn tc;
            tc = (TableColumn)(t.getTableColumns().getItems()[colindex]);
            x = tc.getXPosition(rpt);
        }
        else if (wc.MC != null)
        {
            // must be part of a matrix
            x = wc.MC.getXPosition();
        }
        else
        {
            ReportItems ris = this.Parent instanceof ReportItems ? (ReportItems)this.Parent : (ReportItems)null;
            x = ris.getXOffset(rpt);
        }  
        return x;
    }

    public RSize getHeight() throws Exception {
        return _Height;
    }

    public void setHeight(RSize value) throws Exception {
        _Height = value;
    }

    // routine returns the height; If not specified go up the owner chain
    //   to find an appropriate containing object
    public float getHeightOrOwnerHeight() throws Exception {
        if (_Height != null)
            return _Height.getPoints();
         
        float yloc = this.getTop() == null ? 0 : this.getTop().getPoints();
        for (ReportLink rl = this.Parent;rl != null;rl = rl.Parent)
        {
            if (rl instanceof ReportItem)
            {
                ReportItem ri = rl instanceof ReportItem ? (ReportItem)rl : (ReportItem)null;
                if (ri.getHeight() != null)
                    return ri.getHeight().getPoints() - yloc;
                 
                continue;
            }
             
            if (rl instanceof PageHeader)
            {
                PageHeader ph = rl instanceof PageHeader ? (PageHeader)rl : (PageHeader)null;
                if (ph.getHeight() != null)
                    return ph.getHeight().getPoints() - yloc;
                 
                continue;
            }
             
            if (rl instanceof PageFooter)
            {
                PageFooter pf = rl instanceof PageFooter ? (PageFooter)rl : (PageFooter)null;
                if (pf.getHeight() != null)
                    return pf.getHeight().getPoints() - yloc;
                 
                continue;
            }
             
            if (rl instanceof TableRow)
            {
                TableRow tr = rl instanceof TableRow ? (TableRow)rl : (TableRow)null;
                if (tr.getHeight() != null)
                    return tr.getHeight().getPoints() - yloc;
                 
                continue;
            }
             
            if (rl instanceof MatrixRow)
            {
                MatrixRow mr = rl instanceof MatrixRow ? (MatrixRow)rl : (MatrixRow)null;
                if (mr.getHeight() != null)
                    return mr.getHeight().getPoints() - yloc;
                 
                continue;
            }
             
            if (rl instanceof Body)
            {
                Body b = rl instanceof Body ? (Body)rl : (Body)null;
                if (b.getHeight() != null)
                    return b.getHeight().getPoints() - yloc;
                 
                continue;
            }
             
        }
        return OwnerReport.getPageHeight().getPoints();
    }

    public boolean isHidden(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (this._Visibility == null)
            return false;
         
        return _Visibility.isHidden(rpt,r);
    }

    public void setPageLeft(fyiReporting.RDL.Report rpt) throws Exception {
        if (this._TC != null)
        {
            // must be part of a table
            Table t = _TC.getOwnerTable();
            int colindex = _TC.getColIndex();
            TableColumn tc = (TableColumn)(t.getTableColumns().getItems()[colindex]);
            setLeft(new RSize(OwnerReport, tc.getXPosition(rpt).ToString() + "pt"));
        }
        else if (getLeft() == null)
            setLeft(new RSize(OwnerReport,"0pt"));
          
    }

    public void setPagePositionAndStyle(fyiReporting.RDL.Report rpt, PageItem pi, Row row) throws Exception {
        WorkClass wc = getWC(rpt);
        pi.setX(getOffsetCalc(rpt) + leftCalc(rpt));
        if (this._TC != null)
        {
            // must be part of a table
            Table t = _TC.getOwnerTable();
            int colindex = _TC.getColIndex();
            // Calculate width: add up all columns within the column span
            float width = 0;
            TableColumn tc;
            for (int ci = colindex;ci < colindex + _TC.getColSpan();ci++)
            {
                tc = (TableColumn)(t.getTableColumns().getItems()[ci]);
                width += tc.getWidth().getPoints();
            }
            pi.setW(width);
            pi.setY(0);
            TableRow tr = (TableRow)(_TC.Parent.Parent);
            pi.setH(tr.heightCalc(rpt));
        }
        else // this is a cached item; note tr.HeightOfRow must already be called on row
        if (wc.MC != null)
        {
            // must be part of a matrix
            pi.setW(wc.MC.getWidth());
            pi.setY(0);
            pi.setH(wc.MC.getHeight());
        }
        else if (pi instanceof PageLine)
        {
            // don't really handle if line is part of table???  TODO
            PageLine pl = (PageLine)pi;
            if (getTop() != null)
                pl.setY(this.gap(rpt));
             
            //  y will get adjusted when pageitem added to page
            float y2 = pl.getY();
            if (getHeight() != null)
                y2 += getHeight().getPoints();
             
            pl.setY2(y2);
            pl.setX2(pl.getX());
            if (getWidth() != null)
                pl.setX2(pl.getX2() + getWidth().getPoints());
             
        }
        else
        {
            // not part of a table or matrix
            if (getTop() != null)
                pi.setY(this.gap(rpt));
             
            //  y will get adjusted when pageitem added to page
            if (getHeight() != null)
                pi.setH(getHeight().getPoints());
            else
                pi.setH(this.getHeightOrOwnerHeight()); 
            if (getWidth() != null)
                pi.setW(getWidth().getPoints());
            else
                pi.setW(this.widthOrOwnerWidth(rpt)); 
        }   
        if (getStyle() != null)
            pi.setSI(getStyle().getStyleInfo(rpt,row));
        else
            pi.setSI(new StyleInfo()); 
        // this will just default everything
        pi.setZIndex(this.getZIndex());
        // retain the zindex of the object
        // Catch any action needed
        if (this._Action != null)
        {
            pi.setBookmarkLink(_Action.bookmarkLinkValue(rpt,row));
            pi.setHyperLink(_Action.hyperLinkValue(rpt,row));
        }
         
        if (this._ToolTip != null)
            pi.setTooltip(_ToolTip.evaluateString(rpt,row));
         
    }

    public MatrixCellEntry getMC(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getWC(rpt);
        return wc.MC;
    }

    public void setMC(fyiReporting.RDL.Report rpt, MatrixCellEntry mce) throws Exception {
        WorkClass wc = getWC(rpt);
        wc.MC = mce;
    }

    public RSize getWidth() throws Exception {
        return _Width;
    }

    public void setWidth(RSize value) throws Exception {
        _Width = value;
    }

    public float widthOrOwnerWidth(fyiReporting.RDL.Report rpt) throws Exception {
        if (_Width != null)
            return _Width.getPoints();
         
        float xloc = this.leftCalc(rpt);
        for (ReportLink rl = this.Parent;rl != null;rl = rl.Parent)
        {
            if (rl instanceof ReportItem)
            {
                ReportItem ri = rl instanceof ReportItem ? (ReportItem)rl : (ReportItem)null;
                if (ri.getWidth() != null)
                    return ri.getWidth().getPoints() - xloc;
                 
                continue;
            }
             
            if (rl instanceof PageHeader || rl instanceof PageFooter || rl instanceof Body)
            {
                return OwnerReport.getWidth().getPoints() - xloc;
            }
             
        }
        return OwnerReport.getWidth().getPoints() - xloc;
    }

    public int widthCalc(fyiReporting.RDL.Report rpt, Graphics g) throws Exception {
        WorkClass wc = getWC(rpt);
        int width = new int();
        if (this._TC != null)
        {
            // must be part of a table
            Table t = _TC.getOwnerTable();
            int colindex = _TC.getColIndex();
            // Calculate width: add up all columns within the column span
            width = 0;
            TableColumn tc;
            for (int ci = colindex;ci < colindex + _TC.getColSpan();ci++)
            {
                tc = (TableColumn)(t.getTableColumns().getItems()[ci]);
                width += tc.getWidth().getPixelsX();
            }
        }
        else if (wc.MC != null)
        {
            // must be part of a matrix
            width = g == null ? RSize.pixelsFromPoints(wc.MC.getWidth()) : RSize.pixelsFromPoints(g,wc.MC.getWidth());
        }
        else
        {
            // not part of a table or matrix
            if (getWidth() != null)
                width = getWidth().getPixelsX();
            else
                width = RSize.pixelsFromPoints(widthOrOwnerWidth(rpt)); 
        }  
        return width;
    }

    public Page runPageNew(Pages pgs, Page p) throws Exception {
        if (p.isEmpty())
            return p;
         
        // if the page is empty it won't help to create another one
        // Do we need a new page or have should we fill out more body columns
        Body b = OwnerReport.getBody();
        int ccol = b.incrCurrentColumn(pgs.getReport());
        // bump to next column
        float top = OwnerReport.getTopOfPage();
        // calc top of page
        if (ccol < b.getColumns())
        {
            // Stay on same page but move to new column
            p.setXOffset(((OwnerReport.getWidth().getPoints() + b.getColumnSpacing().getPoints()) * ccol));
            p.setYOffset(top);
            p.setEmpty();
        }
        else
        {
            // consider this page empty
            // Go to new page
            b.setCurrentColumn(pgs.getReport(),0);
            pgs.nextOrNew();
            p = pgs.getCurrentPage();
            p.setYOffset(top);
            p.setXOffset(0);
        } 
        return p;
    }

    /**
    * Updates the current page and location based on the ReportItems
    * that are above it in the report.
    * 
    *  @param pgs
    */
    public void setPagePositionBegin(Pages pgs) throws Exception {
        // Update the current page
        if (this._YParents != null)
        {
            ReportItem saveri = getReportItemAbove(pgs.getReport());
            if (saveri != null)
            {
                WorkClass wc = saveri.getWC(pgs.getReport());
                pgs.setCurrentPage(wc.CurrentPage);
                pgs.getCurrentPage().setYOffset(wc.BottomPosition);
            }
             
        }
        else if (this.Parent.Parent instanceof PageHeader)
        {
            pgs.getCurrentPage().setYOffset(OwnerReport.getTopMargin().getPoints());
        }
        else if (this.Parent.Parent instanceof PageFooter)
        {
            pgs.getCurrentPage().setYOffset(OwnerReport.getPageHeight().getPoints() - OwnerReport.getBottomMargin().getPoints() - OwnerReport.getPageFooter().getHeight().getPoints());
        }
        else if (!(this.Parent.Parent instanceof Body))
        {
        }
        else // if not body then we don't need to do anything
        if (this.OwnerReport.getSubreport() != null)
        {
        }
        else
        {
            //				pgs.CurrentPage = this.OwnerReport.Subreport.FirstPage;
            //				pgs.CurrentPage.YOffset = top;
            pgs.setCurrentPage(pgs.getFirstPage());
            // if nothing above it (in body) then it goes on first page
            pgs.getCurrentPage().setYOffset(OwnerReport.getTopOfPage());
        }     
        return ;
    }

    public void setPagePositionEnd(Pages pgs, float pos) throws Exception {
        if (_TC != null || _InMatrix)
            return ;
         
        // don't mess with page if part of a table or in a matrix
        WorkClass wc = getWC(pgs.getReport());
        wc.CurrentPage = pgs.getCurrentPage();
        wc.BottomPosition = pos;
    }

    /**
    * Calculates the runtime y position of the object based on the height of objects
    * above it vertically.
    */
    public float gap(fyiReporting.RDL.Report rpt) throws Exception {
        float top = _Top == null ? 0 : _Top.getPoints();
        ReportItem saveri = getReportItemAbove(rpt);
        if (saveri == null)
            return top;
         
        float gap = top;
        float s_top = saveri.getTop() == null ? 0 : saveri.getTop().getPoints();
        float s_h = saveri.getHeight() == null ? 0 : saveri.getHeight().getPoints();
        gap -= saveri.getTop().getPoints();
        if (top < s_top + s_h)
            // do we have an overlap;
            gap = top - (s_top + s_h);
        else
            // yes; force overlap even when moving report item down
            gap -= saveri.getHeight().getPoints(); 
        return gap;
    }

    // no; move report item down just the gap between the items
    /**
    * Calculates the runtime y position of the object based on the height of objects
    * above it vertically.
    */
    public float relativeY(fyiReporting.RDL.Report rpt) throws Exception {
        float top = _Top == null ? 0 : _Top.getPoints();
        ReportItem saveri = getReportItemAbove(rpt);
        if (saveri == null)
            return top;
         
        float gap = top;
        if (saveri.getTop() != null)
            gap -= saveri.getTop().getPoints();
         
        if (saveri.getHeight() != null)
            gap -= saveri.getHeight().getPoints();
         
        return gap;
    }

    private ReportItem getReportItemAbove(fyiReporting.RDL.Report rpt) throws Exception {
        if (this._YParents == null)
            return null;
         
        float maxy = float.MinValue;
        ReportItem saveri = null;
        int pgno = 0;
        for (Object __dummyForeachVar1 : this._YParents)
        {
            ReportItem ri = (ReportItem)__dummyForeachVar1;
            WorkClass wc = ri.getWC(rpt);
            if (wc.BottomPosition.CompareTo(float.NaN) == 0 || wc.CurrentPage == null || pgno > wc.CurrentPage.getPageNumber())
                continue;
             
            if (maxy < wc.BottomPosition || pgno < wc.CurrentPage.getPageNumber())
            {
                pgno = wc.CurrentPage.getPageNumber();
                maxy = wc.BottomPosition;
                saveri = ri;
            }
             
        }
        return saveri;
    }

    public int getZIndex() throws Exception {
        return _ZIndex;
    }

    public void setZIndex(int value) throws Exception {
        _ZIndex = value;
    }

    public Visibility getVisibility() throws Exception {
        return _Visibility;
    }

    public void setVisibility(Visibility value) throws Exception {
        _Visibility = value;
    }

    public Expression getToolTip() throws Exception {
        return _ToolTip;
    }

    public void setToolTip(Expression value) throws Exception {
        _ToolTip = value;
    }

    public String toolTipValue(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_ToolTip == null)
            return null;
         
        return _ToolTip.evaluateString(rpt,r);
    }

    public Expression getLabel() throws Exception {
        return _Label;
    }

    public void setLabel(Expression value) throws Exception {
        _Label = value;
    }

    public String getLinkToChild() throws Exception {
        return _LinkToChild;
    }

    public void setLinkToChild(String value) throws Exception {
        _LinkToChild = value;
    }

    public Expression getBookmark() throws Exception {
        return _Bookmark;
    }

    public void setBookmark(Expression value) throws Exception {
        _Bookmark = value;
    }

    public String bookmarkValue(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Bookmark == null)
            return null;
         
        return _Bookmark.evaluateString(rpt,r);
    }

    public TableCell getTC() throws Exception {
        return _TC;
    }

    public String getRepeatWith() throws Exception {
        return _RepeatWith;
    }

    public void setRepeatWith(String value) throws Exception {
        _RepeatWith = value;
    }

    public Custom getCustom() throws Exception {
        return _Custom;
    }

    public void setCustom(Custom value) throws Exception {
        _Custom = value;
    }

    public String getDataElementName() throws Exception {
        if (_DataElementName != null)
            return _DataElementName;
        else if (_Name != null)
            return _Name.getNm();
        else
            return null;  
    }

    public void setDataElementName(String value) throws Exception {
        _DataElementName = value;
    }

    public List<ReportItem> getYParents() throws Exception {
        return this._YParents;
    }

    public DataElementOutputEnum getDataElementOutput() throws Exception {
        if (_DataElementOutput == DataElementOutputEnum.Auto)
        {
            if (this instanceof Textbox)
            {
                Textbox tb = this instanceof Textbox ? (Textbox)this : (Textbox)null;
                if (tb.getValue().isConstant())
                    return DataElementOutputEnum.NoOutput;
                else
                    return DataElementOutputEnum.Output; 
            }
             
            if (this instanceof Rectangle)
                return DataElementOutputEnum.ContentsOnly;
             
            return DataElementOutputEnum.Output;
        }
        else
            return _DataElementOutput; 
    }

    public void setDataElementOutput(DataElementOutputEnum value) throws Exception {
        _DataElementOutput = value;
    }

    public boolean getIsInBody() throws Exception {
        return this.Parent.Parent instanceof Body;
    }

    private WorkClass getWC(fyiReporting.RDL.Report rpt) throws Exception {
        if (rpt == null)
            return new WorkClass();
         
        WorkClass wc = rpt.getCache().get(this,"riwc") instanceof WorkClass ? (WorkClass)rpt.getCache().get(this,"riwc") : (WorkClass)null;
        if (wc == null)
        {
            wc = new WorkClass();
            rpt.getCache().add(this,"riwc",wc);
        }
         
        return wc;
    }

    private void removeWC(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(this,"riwc");
    }

    static class WorkClass   
    {
        public MatrixCellEntry MC;
        // matrix cell entry
        public float BottomPosition = new float();
        // used when calculating position of objects below this one.
        // this must be initialized by the inheriting class.
        public Page CurrentPage;
        // the page this reportitem was last put on;
        public WorkClass() throws Exception {
            MC = null;
            BottomPosition = float.NaN;
            CurrentPage = null;
        }
    
    }

    // Sort report items based on top down, left to right
    public int compareTo(Object obj) throws Exception {
        ReportItem ri = obj instanceof ReportItem ? (ReportItem)obj : (ReportItem)null;
        int t1 = this.getTop() == null ? 0 : this.getTop().getSize();
        int t2 = ri.getTop() == null ? 0 : ri.getTop().getSize();
        int rc = t1 - t2;
        if (rc != 0)
            return rc;
         
        int l1 = this.getLeft() == null ? 0 : this.getLeft().getSize();
        int l2 = ri.getLeft() == null ? 0 : ri.getLeft().getSize();
        return l1 - l2;
    }

}


