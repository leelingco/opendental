//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Body;
import fyiReporting.RDL.Chart;
import fyiReporting.RDL.ChartBase;
import fyiReporting.RDL.DataElementOutputEnum;
import fyiReporting.RDL.DataElementStyleEnum;
import fyiReporting.RDL.DataRegion;
import fyiReporting.RDL.Details;
import fyiReporting.RDL.Footer;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.Header;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.IStreamGen;
import fyiReporting.RDL.Line;
import fyiReporting.RDL.List;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.MatrixColumns;
import fyiReporting.RDL.PageFooter;
import fyiReporting.RDL.PageHeader;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.Rectangle;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Subreport;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.TableCell;
import fyiReporting.RDL.TableGroup;
import fyiReporting.RDL.TableRow;
import fyiReporting.RDL.Textbox;
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
* The primary class to "run" a report to XML
*/
public class RenderXml   implements IPresent
{
    fyiReporting.RDL.Report r;
    // report
    TextWriter tw = new TextWriter();
    // where the output is going
    Stack stkReportItem = new Stack();
    // stack of nested report items
    Stack stkContainers = new Stack();
    // stack to hold container elements
    String rowstart = null;
    public RenderXml(fyiReporting.RDL.Report rep, IStreamGen sg) throws Exception {
        r = rep;
        tw = sg.getTextWriter();
        stkReportItem = new Stack();
        stkContainers = new Stack();
    }

    public fyiReporting.RDL.Report report() throws Exception {
        return r;
    }

    public boolean isPagingNeeded() throws Exception {
        return false;
    }

    public void start() throws Exception {
        tw.WriteLine("<?xml version='1.0' encoding='UTF-8'?>");
        pushContainer(r.getReportDefinition().getDataElementName());
        return ;
    }

    public void end() throws Exception {
        ContainerIO cio = (ContainerIO)stkContainers.Pop();
        // this pop should empty the stack
        cio.writeAttribute(">");
        tw.WriteLine(cio.attribute_sb);
        tw.WriteLine(cio.subelement_sb);
        tw.WriteLine("</" + r.getReportDefinition().getDataElementName() + ">");
        return ;
    }

    // Body: main container for the report
    public void bodyStart(Body b) throws Exception {
    }

    public void bodyEnd(Body b) throws Exception {
    }

    public void pageHeaderStart(PageHeader ph) throws Exception {
    }

    public void pageHeaderEnd(PageHeader ph) throws Exception {
    }

    public void pageFooterStart(PageFooter pf) throws Exception {
    }

    public void pageFooterEnd(PageFooter pf) throws Exception {
    }

    public void textbox(Textbox tb, String t, Row row) throws Exception {
        if (tb.getDataElementOutput() != DataElementOutputEnum.Output || tb.getDataElementName() == null)
            return ;
         
        if (rowstart != null)
        {
            // In case no items in row are visible
            //   we delay until we get one.
            //				WriteElement(rowstart);
            rowstart = null;
        }
         
        t = XmlUtil.xmlAnsi(t);
        if (tb.getDataElementStyle() == DataElementStyleEnum.AttributeNormal)
        {
            // write out as attribute
            writeAttribute(" {0}='{1}'",tb.getDataElementName(),XmlUtil.escapeXmlAttribute(t));
        }
        else
        {
            // write out as element
            writeElement("<{0}>{1}</{0}>",tb.getDataElementName(),t);
        } 
    }

    public void dataRegionNoRows(DataRegion t, String noRowMsg) throws Exception {
    }

    // Lists
    public boolean listStart(List l, Row r) throws Exception {
        if (l.getDataElementOutput() == DataElementOutputEnum.NoOutput)
            return false;
         
        if (l.getDataElementOutput() == DataElementOutputEnum.ContentsOnly)
            return true;
         
        writeElementLine("<{0}>",l.getDataElementName());
        return true;
    }

    //want to continue
    public void listEnd(List l, Row r) throws Exception {
        if (l.getDataElementOutput() == DataElementOutputEnum.NoOutput || l.getDataElementOutput() == DataElementOutputEnum.ContentsOnly)
            return ;
         
        writeElementLine("</{0}>",l.getDataElementName());
        return ;
    }

    public void listEntryBegin(List l, Row r) throws Exception {
        String d = new String();
        if (l.getGrouping() == null)
        {
            if (l.getDataElementOutput() != DataElementOutputEnum.Output)
                return ;
             
            d = String.Format("<{0}", l.getDataInstanceName());
        }
        else
        {
            Grouping g = l.getGrouping();
            if (g.getDataElementOutput() != DataElementOutputEnum.Output)
                return ;
             
            d = String.Format("<{0}", l.getDataInstanceName());
        } 
        pushContainer(l.getDataInstanceName());
        return ;
    }

    public void listEntryEnd(List l, Row r) throws Exception {
        if (l.getDataElementOutput() != DataElementOutputEnum.Output)
            return ;
         
        popContainer(l.getDataInstanceName());
    }

    // Tables					// Report item table
    public boolean tableStart(Table t, Row row) throws Exception {
        if (t.getDataElementOutput() == DataElementOutputEnum.NoOutput)
            return false;
         
        pushContainer(t.getDataElementName());
        stkReportItem.Push(t);
        String cName = tableGetCollectionName(t);
        if (cName != null)
            writeAttributeLine("><{0}",cName);
         
        return true;
    }

    public void tableEnd(Table t, Row row) throws Exception {
        if (t.getDataElementOutput() == DataElementOutputEnum.NoOutput)
            return ;
         
        String cName = tableGetCollectionName(t);
        popContainer(cName);
        writeElementLine("</{0}>",t.getDataElementName());
        stkReportItem.Pop();
        return ;
    }

    String tableGetCollectionName(Table t) throws Exception {
        String cName = new String();
        if (t.getTableGroups() == null)
        {
            if (t.getDetails() != null && t.getDetails().getGrouping() != null)
                cName = t.getDetails().getGrouping().getDataCollectionName();
            else
                cName = t.getDetailDataCollectionName(); 
        }
        else
            cName = null; 
        return cName;
    }

    public void tableBodyStart(Table t, Row row) throws Exception {
    }

    public void tableBodyEnd(Table t, Row row) throws Exception {
    }

    public void tableFooterStart(Footer f, Row row) throws Exception {
    }

    public void tableFooterEnd(Footer f, Row row) throws Exception {
    }

    public void tableHeaderStart(Header h, Row row) throws Exception {
    }

    public void tableHeaderEnd(Header h, Row row) throws Exception {
    }

    public void tableRowStart(TableRow tr, Row row) throws Exception {
        String n = tableGetRowElementName(tr);
        if (n == null)
            return ;
         
        pushContainer(n);
    }

    public void tableRowEnd(TableRow tr, Row row) throws Exception {
        String n = tableGetRowElementName(tr);
        if (n == null)
            return ;
         
        this.popContainer(n);
    }

    String tableGetRowElementName(TableRow tr) throws Exception {
        for (ReportLink rl = tr.Parent;!(rl instanceof Table);rl = rl.Parent)
        {
            if (rl instanceof Header || rl instanceof Footer)
                return null;
             
            if (rl instanceof TableGroup)
            {
                TableGroup tg = rl instanceof TableGroup ? (TableGroup)rl : (TableGroup)null;
                Grouping g = tg.getGrouping();
                return g.getDataElementName();
            }
             
            if (rl instanceof Details)
            {
                Table t = (Table)stkReportItem.Peek();
                return t.getDetailDataElementName();
            }
             
        }
        return null;
    }

    public void tableCellStart(TableCell t, Row row) throws Exception {
        return ;
    }

    public void tableCellEnd(TableCell t, Row row) throws Exception {
        return ;
    }

    public boolean matrixStart(Matrix m, Row r) throws Exception {
        // called first
        if (m.getDataElementOutput() != DataElementOutputEnum.Output)
            return false;
         
        tw.WriteLine("<" + (m.getDataElementName() == null ? "Matrix" : m.getDataElementName()) + ">");
        return true;
    }

    public void matrixColumns(Matrix m, MatrixColumns mc) throws Exception {
    }

    // called just after MatrixStart
    public void matrixCellStart(Matrix m, ReportItem ri, int row, int column, Row r, float h, float w, int colSpan) throws Exception {
    }

    public void matrixCellEnd(Matrix m, ReportItem ri, int row, int column, Row r) throws Exception {
    }

    public void matrixRowStart(Matrix m, int row, Row r) throws Exception {
    }

    public void matrixRowEnd(Matrix m, int row, Row r) throws Exception {
    }

    public void matrixEnd(Matrix m, Row r) throws Exception {
        // called last
        tw.WriteLine("</" + (m.getDataElementName() == null ? "Matrix" : m.getDataElementName()) + ">");
    }

    public void chart(Chart c, Row r, ChartBase cb) throws Exception {
    }

    public void image(fyiReporting.RDL.Image i, Row r, String mimeType, Stream io) throws Exception {
    }

    public void line(Line l, Row r) throws Exception {
    }

    public boolean rectangleStart(Rectangle rect, Row r) throws Exception {
        boolean rc = true;
        switch(rect.getDataElementOutput())
        {
            case NoOutput: 
                rc = false;
                break;
            case Output: 
                if (rowstart != null)
                {
                    // In case no items in row are visible
                    //   we delay until we get one.
                    tw.Write(rowstart);
                    rowstart = null;
                }
                 
                pushContainer(rect.getDataElementName());
                break;
            case Auto: 
            case ContentsOnly: 
            default: 
                break;
        
        }
        return rc;
    }

    public void rectangleEnd(Rectangle rect, Row r) throws Exception {
        if (rect.getDataElementOutput() != DataElementOutputEnum.Output)
            return ;
         
        popContainer(rect.getDataElementName());
    }

    public void subreport(Subreport s, Row r) throws Exception {
        if (s.getDataElementOutput() != DataElementOutputEnum.Output)
            return ;
         
        pushContainer(s.getDataElementName());
        s.getReportDefn().run(this);
        popContainer(s.getDataElementName());
        return ;
    }

    public void groupingStart(Grouping g) throws Exception {
        // called at start of grouping
        if (g.getDataElementOutput() != DataElementOutputEnum.Output)
            return ;
         
        pushContainer(g.getDataCollectionName());
    }

    public void groupingInstanceStart(Grouping g) throws Exception {
        // called at start for each grouping instance
        if (g.getDataElementOutput() != DataElementOutputEnum.Output)
            return ;
         
        pushContainer(g.getDataElementName());
    }

    public void groupingInstanceEnd(Grouping g) throws Exception {
        // called at start for each grouping instance
        if (g.getDataElementOutput() != DataElementOutputEnum.Output)
            return ;
         
        popContainer(g.getDataElementName());
    }

    public void groupingEnd(Grouping g) throws Exception {
        // called at end of grouping
        if (g.getDataElementOutput() != DataElementOutputEnum.Output)
            return ;
         
        popContainer(g.getDataCollectionName());
    }

    public void runPages(Pages pgs) throws Exception {
        return ;
    }

    // we don't have paging turned on for xml
    void popContainer(String name) throws Exception {
        ContainerIO cio = (ContainerIO)this.stkContainers.Pop();
        if (cio.bEmpty)
            return ;
         
        cio.writeAttribute(">");
        WriteElementLine(cio.attribute_sb.ToString());
        WriteElementLine(cio.subelement_sb.ToString());
        if (name != null)
            writeElementLine("</{0}>",name);
         
    }

    void pushContainer(String name) throws Exception {
        ContainerIO cio = new ContainerIO("<" + name);
        stkContainers.Push(cio);
    }

    void writeElement(String format) throws Exception {
        ContainerIO cio = (ContainerIO)this.stkContainers.Peek();
        cio.writeElement(format);
    }

    void writeElement(String format, Object... arg) throws Exception {
        ContainerIO cio = (ContainerIO)this.stkContainers.Peek();
        cio.writeElement(format,arg);
    }

    void writeElementLine(String format) throws Exception {
        ContainerIO cio = (ContainerIO)this.stkContainers.Peek();
        cio.writeElementLine(format);
    }

    void writeElementLine(String format, Object... arg) throws Exception {
        ContainerIO cio = (ContainerIO)this.stkContainers.Peek();
        cio.writeElementLine(format,arg);
    }

    void writeAttribute(String format) throws Exception {
        ContainerIO cio = (ContainerIO)this.stkContainers.Peek();
        cio.writeAttribute(format);
    }

    void writeAttribute(String format, Object... arg) throws Exception {
        ContainerIO cio = (ContainerIO)this.stkContainers.Peek();
        cio.writeAttribute(format,arg);
    }

    void writeAttributeLine(String format) throws Exception {
        ContainerIO cio = (ContainerIO)this.stkContainers.Peek();
        cio.writeAttributeLine(format);
    }

    void writeAttributeLine(String format, Object... arg) throws Exception {
        ContainerIO cio = (ContainerIO)this.stkContainers.Peek();
        cio.writeAttributeLine(format,arg);
    }

    static class ContainerIO   
    {
        public StringBuilder attribute_sb = new StringBuilder();
        public StringWriter attribute_sw = new StringWriter();
        public StringBuilder subelement_sb = new StringBuilder();
        public StringWriter subelement_sw = new StringWriter();
        public boolean bEmpty = true;
        public ContainerIO(String begin) throws Exception {
            subelement_sb = new StringBuilder();
            subelement_sw = new StringWriter(subelement_sb);
            attribute_sb = new StringBuilder(begin);
            attribute_sw = new StringWriter(attribute_sb);
        }

        public void writeElement(String format) throws Exception {
            bEmpty = false;
            subelement_sw.Write(format);
        }

        public void writeElement(String format, Object... arg) throws Exception {
            bEmpty = false;
            subelement_sw.Write(format, arg);
        }

        public void writeElementLine(String format) throws Exception {
            bEmpty = false;
            subelement_sw.WriteLine(format);
        }

        public void writeElementLine(String format, Object... arg) throws Exception {
            bEmpty = false;
            subelement_sw.WriteLine(format, arg);
        }

        public void writeAttribute(String format) throws Exception {
            bEmpty = false;
            attribute_sw.Write(format);
        }

        public void writeAttribute(String format, Object... arg) throws Exception {
            bEmpty = false;
            attribute_sw.Write(format, arg);
        }

        public void writeAttributeLine(String format) throws Exception {
            bEmpty = false;
            attribute_sw.WriteLine(format);
        }

        public void writeAttributeLine(String format, Object... arg) throws Exception {
            bEmpty = false;
            attribute_sw.WriteLine(format, arg);
        }
    
    }

}


