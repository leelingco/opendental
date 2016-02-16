//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Body;
import fyiReporting.RDL.Chart;
import fyiReporting.RDL.ChartBase;
import fyiReporting.RDL.DataRegion;
import fyiReporting.RDL.Footer;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.Header;
import fyiReporting.RDL.Image;
import fyiReporting.RDL.Line;
import fyiReporting.RDL.List;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.MatrixColumns;
import fyiReporting.RDL.PageFooter;
import fyiReporting.RDL.PageHeader;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.Rectangle;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Subreport;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.TableCell;
import fyiReporting.RDL.TableRow;
import fyiReporting.RDL.Textbox;

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
* Presentation: generation of presentation; e.g. html, pdf, xml, ...
*/
public interface IPresent   
{
    // Meta Information: can be called at any time
    boolean isPagingNeeded() throws Exception ;

    // should report engine perform paging
    // General
    void start() throws Exception ;

    // called first
    void end() throws Exception ;

    // called last
    //
    void runPages(Pages pgs) throws Exception ;

    // only called if IsPagingNeeded -
    // Body: main container for the report
    void bodyStart(Body b) throws Exception ;

    // called right before body processing
    void bodyEnd(Body b) throws Exception ;

    // called
    // PageHeader:
    void pageHeaderStart(PageHeader ph) throws Exception ;

    void pageHeaderEnd(PageHeader ph) throws Exception ;

    // PageFooter:
    void pageFooterStart(PageFooter pf) throws Exception ;

    void pageFooterEnd(PageFooter pf) throws Exception ;

    // ReportItems
    void textbox(Textbox tb, String t, Row r) throws Exception ;

    // encountered a textbox
    void dataRegionNoRows(DataRegion d, String noRowsMsg) throws Exception ;

    // no rows in DataRegion
    // Lists
    boolean listStart(List l, Row r) throws Exception ;

    // called first in list
    void listEnd(List l, Row r) throws Exception ;

    // called last in list
    void listEntryBegin(List l, Row r) throws Exception ;

    // called to begin each list entry
    void listEntryEnd(List l, Row r) throws Exception ;

    // called to end each list entry
    // Tables					// Report item table
    boolean tableStart(Table t, Row r) throws Exception ;

    // called first in table
    void tableEnd(Table t, Row r) throws Exception ;

    // called last in table
    void tableBodyStart(Table t, Row r) throws Exception ;

    // table body
    void tableBodyEnd(Table t, Row r) throws Exception ;

    //
    void tableFooterStart(Footer f, Row r) throws Exception ;

    // footer row(s)
    void tableFooterEnd(Footer f, Row r) throws Exception ;

    //
    void tableHeaderStart(Header h, Row r) throws Exception ;

    // header row(s)
    void tableHeaderEnd(Header h, Row r) throws Exception ;

    //
    void tableRowStart(TableRow tr, Row r) throws Exception ;

    // row
    void tableRowEnd(TableRow tr, Row r) throws Exception ;

    //
    void tableCellStart(TableCell t, Row r) throws Exception ;

    // report item will be called after
    void tableCellEnd(TableCell t, Row r) throws Exception ;

    // report item will be called before
    // Matrix					// Report item matrix
    boolean matrixStart(Matrix m, Row r) throws Exception ;

    // called first
    void matrixColumns(Matrix m, MatrixColumns mc) throws Exception ;

    // called just after MatrixStart
    void matrixRowStart(Matrix m, int row, Row r) throws Exception ;

    // row
    void matrixRowEnd(Matrix m, int row, Row r) throws Exception ;

    //
    void matrixCellStart(Matrix m, ReportItem ri, int row, int column, Row r, float h, float w, int colSpan) throws Exception ;

    void matrixCellEnd(Matrix m, ReportItem ri, int row, int column, Row r) throws Exception ;

    void matrixEnd(Matrix m, Row r) throws Exception ;

    // called last
    // Chart
    void chart(Chart c, Row r, ChartBase cb) throws Exception ;

    // Image
    void image(Image i, Row r, String mimeType, Stream io) throws Exception ;

    // Line
    void line(Line l, Row r) throws Exception ;

    // Rectangle
    boolean rectangleStart(Rectangle rect, Row r) throws Exception ;

    // called before any reportitems
    void rectangleEnd(Rectangle rect, Row r) throws Exception ;

    // called after any reportitems
    // Subreport
    void subreport(Subreport s, Row r) throws Exception ;

    // Grouping
    void groupingStart(Grouping g) throws Exception ;

    // called at start of grouping
    void groupingInstanceStart(Grouping g) throws Exception ;

    // called at start for each grouping instance
    void groupingInstanceEnd(Grouping g) throws Exception ;

    // called at start for each grouping instance
    void groupingEnd(Grouping g) throws Exception ;

    // called at end of grouping
    fyiReporting.RDL.Report report() throws Exception ;

}


