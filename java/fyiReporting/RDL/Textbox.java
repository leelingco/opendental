//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.DataElementStyleEnum;
import fyiReporting.RDL.DataSetDefn;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.FunctionField;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.PageText;
import fyiReporting.RDL.PageTextHtml;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.TextboxRuntime;
import fyiReporting.RDL.ToggleImage;
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
* The Textbox definition.  Inherits from ReportItem.
*/
public class Textbox  extends ReportItem 
{
    Expression _Value;
    // (Variant) An expression, the value of which is
    // displayed in the text-box.
    // This can be a constant expression for constant labels.
    boolean _CanGrow = new boolean();
    // Indicates the Textbox size can
    // increase to accommodate the contents
    boolean _CanShrink = new boolean();
    // Indicates the Textbox size can
    // decrease to match the contents
    String _HideDuplicates = new String();
    // Indicates the item should be hidden
    //when the value of the expression
    //associated with the report item is the
    //same as the preceding instance. The
    //value of HideDuplicates is the name
    //of a grouping or data set over which
    //to apply the hiding. Each time a
    //new instance of that group is
    //encountered, the first instance of
    //this report item will not be hidden.
    //Rows on a previous page are
    //ignored for the purposes of hiding
    //duplicates. If the textbox is in a
    //table or matrix cell, only the text
    //will be hidden. The textbox will
    //remain to provide background and
    //border for the cell.
    //Ignored in matrix subtotals.
    ToggleImage _ToggleImage;
    // Indicates the initial state of a
    // toggling image should one be
    // displayed as a part of the textbox.
    DataElementStyleEnum _DataElementStyle = DataElementStyleEnum.Auto;
    // Indicates whether textbox value
    // should render as an element or attribute: Auto (Default)
    // Auto uses the setting on the Report element.
    boolean _IsToggle = new boolean();
    // Textbox is used to toggle a detail row
    List<String> _ExprReferences = new List<String>();
    // array of names of expressions that reference this Textbox;
    //  only needed for page header/footer references
    public Textbox(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p, xNode);
        _Value = null;
        _CanGrow = false;
        _CanShrink = false;
        _HideDuplicates = null;
        _ToggleImage = null;
        _DataElementStyle = DataElementStyleEnum.Auto;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Value"))
            {
                _Value = new Expression(r,this,xNodeLoop,ExpressionType.Variant);
            }
            else if (__dummyScrutVar0.equals("CanGrow"))
            {
                _CanGrow = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("CanShrink"))
            {
                _CanShrink = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("HideDuplicates"))
            {
                _HideDuplicates = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("ToggleImage"))
            {
                _ToggleImage = new ToggleImage(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("DataElementStyle"))
            {
                _DataElementStyle = fyiReporting.RDL.DataElementStyle.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                if (reportItemElement(xNodeLoop))
                    break;
                 
                // try at ReportItem level
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Textbox element '" + xNodeLoop.Name + "' ignored.");
            }      
        }
        if (_Value == null)
            OwnerReport.rl.logError(8,"Textbox value not specified for " + (this.getName() == null ? "'name not specified'" : this.getName().getNm()));
         
        if (this.getName() != null)
        {
            try
            {
                OwnerReport.getLUReportItems().Add(this.getName().getNm(), this);
            }
            catch (Exception __dummyCatchVar0)
            {
                // add to referenceable TextBoxes
                // Duplicate name
                OwnerReport.rl.logError(4,"Duplicate Textbox name '" + this.getName().getNm() + "' ignored.");
            }
        
        }
         
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        super.finalPass();
        _Value.finalPass();
        if (this.getDataElementName() == null && this.getName() == null)
        {
            // no name or dataelementname; try using expression
            FunctionField ff = _Value.getExpr() instanceof FunctionField ? (FunctionField)_Value.getExpr() : (FunctionField)null;
            if (ff != null && ff.getFld() != null)
            {
                this.setDataElementName(ff.getFld().getDataField());
            }
             
        }
         
        if (_ToggleImage != null)
            _ToggleImage.finalPass();
         
        if (_HideDuplicates != null)
        {
            Object o = OwnerReport.getLUAggrScope()[_HideDuplicates];
            if (o == null)
            {
                OwnerReport.rl.logError(4,"HideDuplicate '" + _HideDuplicates + "' is not a Group or DataSet name.   It will be ignored.");
                _HideDuplicates = null;
            }
            else if (o instanceof Grouping)
            {
                Grouping g = o instanceof Grouping ? (Grouping)o : (Grouping)null;
                g.addHideDuplicates(this);
            }
            else if (o instanceof DataSetDefn)
            {
                DataSetDefn ds = o instanceof DataSetDefn ? (DataSetDefn)o : (DataSetDefn)null;
                ds.addHideDuplicates(this);
            }
               
        }
         
        return ;
    }

    public void addExpressionReference(String name) throws Exception {
        if (_ExprReferences == null)
            _ExprReferences = new List<String>();
         
        _ExprReferences.Add(name);
    }

    public void recordPageReference(fyiReporting.RDL.Report rpt, Page p, Row r) throws Exception {
        if (_ExprReferences == null)
            return ;
         
        for (Object __dummyForeachVar1 : _ExprReferences)
        {
            String refr = (String)__dummyForeachVar1;
            p.addPageExpressionRow(rpt,refr,r);
        }
    }

    public void resetPrevious(fyiReporting.RDL.Report rpt) throws Exception {
        TextboxRuntime tbr = TextboxRuntime.getTextboxRuntime(rpt,this);
        resetPrevious(tbr);
    }

    void resetPrevious(TextboxRuntime tbr) throws Exception {
        tbr.PreviousText = null;
        tbr.PreviousPage = null;
    }

    public void run(IPresent ip, Row row) throws Exception {
        fyiReporting.RDL.Report rpt = ip.report();
        super.run(ip,row);
        TextboxRuntime tbr = TextboxRuntime.getTextboxRuntime(rpt,this);
        tbr.RunCount++;
        // Increment the run count
        String t = runText(rpt,row);
        boolean bDup = runTextIsDuplicate(tbr,t,null);
        if (bDup)
        {
            if (!(this.isTableOrMatrixCell(rpt)))
                return ;
             
            // don't put out anything if not in Table or Matrix
            t = "";
        }
         
        // still need to put out the cell
        ip.textbox(this,t,row);
        if (!bDup)
            tbr.PreviousText = t;
         
    }

    // set for next time
    public void runPage(Pages pgs, Row row) throws Exception {
        fyiReporting.RDL.Report r = pgs.getReport();
        TextboxRuntime tbr = TextboxRuntime.getTextboxRuntime(pgs.getReport(),this);
        tbr.RunCount++;
        // Increment the run count
        if (isHidden(pgs.getReport(),row))
            return ;
         
        setPagePositionBegin(pgs);
        String t = runText(r,row);
        // get the text
        boolean bDup = runTextIsDuplicate(tbr,t,pgs.getCurrentPage());
        if (bDup)
        {
            if (!(this.isTableOrMatrixCell(r)))
                return ;
             
            // don't put out anything if not in Table or Matrix
            t = "";
        }
         
        // still need to put out the cell
        PageText pt;
        PageTextHtml pth = null;
        if (isHtml(r,row))
            pt = pth = new PageTextHtml(t);
        else
            pt = new PageText(t); 
        setPagePositionAndStyle(r,pt,row);
        if (this.getCanGrow() && tbr.RunHeight == 0)
        {
            // when textbox is in a DataRegion this will already be called
            this.runTextCalcHeight(r,pgs.getG(),row,pt instanceof PageTextHtml ? (pt instanceof PageTextHtml ? (PageTextHtml)pt : (PageTextHtml)null) : null);
        }
         
        pt.setH(Math.Max(pt.getH(), tbr.RunHeight));
        // reset height
        if (pt.getSI().BackgroundImage != null)
            pt.getSI().BackgroundImage.setH(pt.getH());
         
        //   and in the background image
        pt.setCanGrow(this.getCanGrow());
        // Force page break if it doesn't fit on a page
        // Only force page when object directly in body
        // running off end of page
        if (this.getIsInBody() && pgs.getCurrentPage().getYOffset() + pt.getY() + pt.getH() >= pgs.getBottomOfPage() && !pgs.getCurrentPage().isEmpty())
        {
            // if page is already empty don't force new
            // force page break if it doesn't fit on the page
            pgs.nextOrNew();
            pgs.getCurrentPage().setYOffset(OwnerReport.getTopOfPage());
            if (this.getYParents() != null)
                pt.setY(0);
             
        }
         
        Page p = pgs.getCurrentPage();
        recordPageReference(r,p,row);
        // save information for late page header/footer references
        p.addObject(pt);
        if (!bDup)
        {
            tbr.PreviousText = t;
            // previous text displayed
            tbr.PreviousPage = p;
        }
         
        //  page previous text was shown on
        setPagePositionEnd(pgs,pt.getY() + pt.getH());
        if (pth != null)
            pth.reset();
         
        if (this.getCanGrow() && !getValue().isConstant())
        {
            tbr.RunHeight = 0;
        }
         
    }

    // need to recalculate
    // routine to determine if text is considered to be a duplicate;
    //  ie: same as previous text and on same page
    private boolean runTextIsDuplicate(TextboxRuntime tbr, String t, Page p) throws Exception {
        if (this._HideDuplicates == null)
            return false;
         
        if (StringSupport.equals(t, tbr.PreviousText) && p == tbr.PreviousPage)
            return true;
         
        return false;
    }

    public String runText(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object o = _Value.evaluate(rpt,row);
        String t = getStyle().GetFormatedString(rpt, this.getStyle(), row, o, _Value.getTypeCode());
        return t;
    }

    public float runTextCalcHeight(fyiReporting.RDL.Report rpt, Graphics g, Row row) throws Exception {
        return runTextCalcHeight(rpt,g,row,null);
    }

    public float runTextCalcHeight(fyiReporting.RDL.Report rpt, Graphics g, Row row, PageTextHtml pth) throws Exception {
        // normally only called when CanGrow is true
        Size s = Size.Empty;
        if (isHidden(rpt,row))
            return 0;
         
        Object o = _Value.evaluate(rpt,row);
        TypeCode tc = _Value.getTypeCode();
        int width = this.widthCalc(rpt,g);
        if (this.getStyle() != null)
        {
            width -= (getStyle().evalPaddingLeftPx(rpt,row) + getStyle().evalPaddingRightPx(rpt,row));
            if (this.isHtml(rpt,row))
            {
                if (pth == null)
                {
                    pth = new PageTextHtml(o == null ? "" : o.ToString());
                    setPagePositionAndStyle(rpt,pth,row);
                }
                 
                pth.build(g);
                s.Height = RSize.pixelsFromPoints(pth.getTotalHeight());
            }
            else
                s = getStyle().measureString(rpt,g,o,tc,row,width); 
        }
        else
            // call the class static method
            s = getStyle().measureStringDefaults(rpt,g,o,tc,row,width); 
        TextboxRuntime tbr = TextboxRuntime.getTextboxRuntime(rpt,this);
        tbr.RunHeight = RSize.PointsFromPixels(g, s.Height);
        if (getStyle() != null)
            tbr.RunHeight += (getStyle().evalPaddingBottom(rpt,row) + getStyle().evalPaddingTop(rpt,row));
         
        return tbr.RunHeight;
    }

    public Object evaluate(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        Object o = _Value.evaluate(rpt,r);
        return o;
    }

    public Expression getValue() throws Exception {
        return _Value;
    }

    public void setValue(Expression value) throws Exception {
        _Value = value;
    }

    public boolean getCanGrow() throws Exception {
        return _CanGrow;
    }

    public void setCanGrow(boolean value) throws Exception {
        _CanGrow = value;
    }

    public boolean getCanShrink() throws Exception {
        return _CanShrink;
    }

    public void setCanShrink(boolean value) throws Exception {
        _CanShrink = value;
    }

    public String getHideDuplicates() throws Exception {
        return _HideDuplicates;
    }

    public void setHideDuplicates(String value) throws Exception {
        _HideDuplicates = value;
    }

    public boolean isHtml(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (this.getStyle() == null || this.getStyle().getFormat() == null)
            return false;
         
        String format = getStyle().getFormat().evaluateString(rpt,row);
        if (format == null)
            return false;
         
        return StringSupport.equals(format.ToLower(), "html");
    }

    public ToggleImage getToggleImage() throws Exception {
        return _ToggleImage;
    }

    public void setToggleImage(ToggleImage value) throws Exception {
        _ToggleImage = value;
    }

    public boolean getIsToggle() throws Exception {
        return _IsToggle;
    }

    public void setIsToggle(boolean value) throws Exception {
        _IsToggle = value;
    }

    public int runCount(fyiReporting.RDL.Report rpt) throws Exception {
        TextboxRuntime tbr = TextboxRuntime.getTextboxRuntime(rpt,this);
        return tbr.RunCount;
    }

    public DataElementStyleEnum getDataElementStyle() throws Exception {
        if (_DataElementStyle == DataElementStyleEnum.Auto)
            return OwnerReport.getDataElementStyle();
        else
            return _DataElementStyle; 
    }

    // auto means use report
    public void setDataElementStyle(DataElementStyleEnum value) throws Exception {
        _DataElementStyle = value;
    }

}


