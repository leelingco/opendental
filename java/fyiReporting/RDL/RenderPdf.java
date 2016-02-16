//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.Body;
import fyiReporting.RDL.Chart;
import fyiReporting.RDL.ChartBase;
import fyiReporting.RDL.CompressionConfig;
import fyiReporting.RDL.DataRegion;
import fyiReporting.RDL.FontWeightEnum;
import fyiReporting.RDL.Footer;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.Header;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.IStreamGen;
import fyiReporting.RDL.Line;
import fyiReporting.RDL.List;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.MatrixColumns;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.PageFooter;
import fyiReporting.RDL.PageHeader;
import fyiReporting.RDL.PageImage;
import fyiReporting.RDL.PageItem;
import fyiReporting.RDL.PageLine;
import fyiReporting.RDL.PageRectangle;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.PageText;
import fyiReporting.RDL.PageTextHtml;
import fyiReporting.RDL.PdfAnchor;
import fyiReporting.RDL.PdfCatalog;
import fyiReporting.RDL.PdfContent;
import fyiReporting.RDL.PdfElements;
import fyiReporting.RDL.PdfFonts;
import fyiReporting.RDL.PdfImages;
import fyiReporting.RDL.PdfInfo;
import fyiReporting.RDL.PdfOutline;
import fyiReporting.RDL.PdfOutlineEntry;
import fyiReporting.RDL.PdfPage;
import fyiReporting.RDL.PdfPageSize;
import fyiReporting.RDL.PdfPageTree;
import fyiReporting.RDL.PdfUtility;
import fyiReporting.RDL.RdlEngineConfig;
import fyiReporting.RDL.Rectangle;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.StyleInfo;
import fyiReporting.RDL.Subreport;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.TableCell;
import fyiReporting.RDL.TableRow;
import fyiReporting.RDL.Textbox;
import fyiReporting.RDL.WritingModeEnum;

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
* Renders a report to PDF.   This is a page oriented formatting renderer.
*/
public class RenderPdf   implements IPresent
{
    fyiReporting.RDL.Report r;
    // report
    Stream tw = new Stream();
    // where the output is going
    PdfAnchor anchor;
    // anchor tieing together all pdf objects
    PdfCatalog catalog;
    PdfPageTree pageTree;
    PdfInfo info;
    PdfFonts fonts;
    PdfImages images;
    PdfOutline outline;
    // holds the bookmarks (if any)
    PdfUtility pdfUtility;
    int filesize = new int();
    PdfPage page;
    PdfContent content;
    PdfElements elements;
    static final char[] lineBreak = new char[]{ '\n' };
    static final char[] wordBreak = new char[]{ ' ' };
    static final int MEASUREMAX = int.MaxValue;
    //  .Net 2 doesn't seem to have a limit; 1.1 limit was 32
    public RenderPdf(fyiReporting.RDL.Report rep, IStreamGen sg) throws Exception {
        r = rep;
        tw = sg.getStream();
    }

    public fyiReporting.RDL.Report report() throws Exception {
        return r;
    }

    public boolean isPagingNeeded() throws Exception {
        return true;
    }

    public void start() throws Exception {
        // Create the anchor for all pdf objects
        CompressionConfig cc = RdlEngineConfig.getCompression();
        anchor = new PdfAnchor(cc != null);
        //Create a PdfCatalog
        String lang = new String();
        if (r.getReportDefinition().getLanguage() != null)
            lang = r.getReportDefinition().getLanguage().evaluateString(this.r,null);
        else
            lang = null; 
        catalog = new PdfCatalog(anchor,lang);
        //Create a Page Tree Dictionary
        pageTree = new PdfPageTree(anchor);
        //Create a Font Dictionary
        fonts = new PdfFonts(anchor);
        //Create an Image Dictionary
        images = new PdfImages(anchor);
        //Create an Outline Dictionary
        outline = new PdfOutline(anchor);
        //Create the info Dictionary
        info = new PdfInfo(anchor);
        //Set the info Dictionary.
        info.setInfo(r.getName(),r.getAuthor(),r.getDescription(),"");
        // title, author, subject, company
        //Create a utility object
        pdfUtility = new PdfUtility(anchor);
        //write out the header
        int size = 0;
        RefSupport<int> refVar___0 = new RefSupport<int>();
        tw.Write(pdfUtility.getHeader("1.5",refVar___0), 0, size);
        size = refVar___0.getValue();
        filesize = size;
    }

    public void end() throws Exception {
        //Write everything
        int size = 0;
        RefSupport<int> refVar___1 = new RefSupport<int>();
        tw.Write(catalog.GetCatalogDict(outline.getObjectNumber(), pageTree.objectNum, filesize, refVar___1), 0, size);
        size = refVar___1.getValue();
        filesize += size;
        RefSupport<int> refVar___2 = new RefSupport<int>();
        tw.Write(pageTree.GetPageTree(filesize, refVar___2), 0, size);
        size = refVar___2.getValue();
        filesize += size;
        RefSupport<int> refVar___3 = new RefSupport<int>();
        tw.Write(fonts.GetFontDict(filesize, refVar___3), 0, size);
        size = refVar___3.getValue();
        filesize += size;
        if (images.getImages().Count > 0)
        {
            RefSupport<int> refVar___4 = new RefSupport<int>();
            tw.Write(images.GetImageDict(filesize, refVar___4), 0, size);
            size = refVar___4.getValue();
            filesize += size;
        }
         
        if (outline.getBookmarks().Count > 0)
        {
            RefSupport<int> refVar___5 = new RefSupport<int>();
            tw.Write(outline.GetOutlineDict(filesize, refVar___5), 0, size);
            size = refVar___5.getValue();
            filesize += size;
        }
         
        RefSupport<int> refVar___6 = new RefSupport<int>();
        tw.Write(info.GetInfoDict(filesize, refVar___6), 0, size);
        size = refVar___6.getValue();
        filesize += size;
        RefSupport<int> refVar___7 = new RefSupport<int>();
        tw.Write(pdfUtility.CreateXrefTable(filesize, refVar___7), 0, size);
        size = refVar___7.getValue();
        filesize += size;
        RefSupport<int> refVar___8 = new RefSupport<int>();
        tw.Write(pdfUtility.getTrailer(catalog.objectNum,info.objectNum,refVar___8), 0, size);
        size = refVar___8.getValue();
        filesize += size;
        return ;
    }

    public void runPages(Pages pgs) throws Exception {
        for (Object __dummyForeachVar0 : pgs)
        {
            // this does all the work
            Page p = (Page)__dummyForeachVar0;
            //Create a Page Dictionary representing a visible page
            page = new PdfPage(anchor);
            content = new PdfContent(anchor);
            PdfPageSize pSize = new PdfPageSize((int)r.getReportDefinition().getPageWidth().getPoints(),(int)r.getReportDefinition().getPageHeight().getPoints());
            page.createPage(pageTree.objectNum,pSize);
            pageTree.addPage(page.objectNum);
            //Create object that presents the elements in the page
            elements = new PdfElements(page,pSize);
            processPage(pgs,p);
            // after a page
            content.setStream(elements.endElements());
            page.addResource(fonts,content.objectNum);
            int size = 0;
            RefSupport<int> refVar___9 = new RefSupport<int>();
            tw.Write(page.GetPageDict(filesize, refVar___9), 0, size);
            size = refVar___9.getValue();
            filesize += size;
            RefSupport<int> refVar___10 = new RefSupport<int>();
            tw.Write(content.GetContentDict(filesize, refVar___10), 0, size);
            size = refVar___10.getValue();
            filesize += size;
        }
        return ;
    }

    // render all the objects in a page in PDF
    private void processPage(Pages pgs, IEnumerable items) throws Exception {
        for (Object __dummyForeachVar1 : items)
        {
            PageItem pi = (PageItem)__dummyForeachVar1;
            if (pi.getSI().BackgroundImage != null)
            {
                // put out any background image
                PageImage i = pi.getSI().BackgroundImage;
                elements.addImage(images,i.getName(),content.objectNum,i.getSI(),i.getImgFormat(),pi.getX(),pi.getY(),pi.getW(),pi.getH(),i.getImageData(),i.getSamplesW(),i.getSamplesH(),null);
            }
             
            if (pi instanceof PageTextHtml)
            {
                PageTextHtml pth = pi instanceof PageTextHtml ? (PageTextHtml)pi : (PageTextHtml)null;
                pth.build(pgs.getG());
                processPage(pgs,pth);
                continue;
            }
             
            if (pi instanceof PageText)
            {
                PageText pt = pi instanceof PageText ? (PageText)pi : (PageText)null;
                float[] textwidth = new float[]();
                RefSupport<float[]> refVar___11 = new RefSupport<float[]>();
                String[] sa = MeasureString(pt, pgs.getG(), refVar___11);
                textwidth = refVar___11.getValue();
                elements.AddText(pt.getX(), pt.getY(), pt.getH(), pt.getW(), sa, pt.getSI(), fonts, textwidth, pt.getCanGrow(), pt.getHyperLink(), pt.getNoClip());
                if (pt.getBookmarkLink() != null)
                {
                    outline.getBookmarks().Add(new PdfOutlineEntry(anchor, page.objectNum, pt.getBookmarkLink(), pt.getX(), elements.getPageSize().yHeight - pt.getY()));
                }
                 
                continue;
            }
             
            if (pi instanceof PageLine)
            {
                PageLine pl = pi instanceof PageLine ? (PageLine)pi : (PageLine)null;
                elements.addLine(pl.getX(),pl.getY(),pl.getX2(),pl.getY2(),pl.getSI());
                continue;
            }
             
            if (pi instanceof PageImage)
            {
                PageImage i = pi instanceof PageImage ? (PageImage)pi : (PageImage)null;
                float x = i.getX() + i.getSI().PaddingLeft;
                float y = i.getY() + i.getSI().PaddingTop;
                float w = i.getW() - i.getSI().PaddingLeft - i.getSI().PaddingRight;
                float h = i.getH() - i.getSI().PaddingTop - i.getSI().PaddingBottom;
                elements.addImage(images,i.getName(),content.objectNum,i.getSI(),i.getImgFormat(),x,y,w,h,i.getImageData(),i.getSamplesW(),i.getSamplesH(),i.getHyperLink());
                continue;
            }
             
            if (pi instanceof PageRectangle)
            {
                PageRectangle pr = pi instanceof PageRectangle ? (PageRectangle)pi : (PageRectangle)null;
                elements.addRectangle(pr.getX(),pr.getY(),pr.getH(),pr.getW(),pi.getSI(),pi.getHyperLink());
                continue;
            }
             
        }
    }

    private String[] measureString(PageText pt, Graphics g, RefSupport<float[]> width) throws Exception {
        StyleInfo si = pt.getSI();
        String s = pt.getText();
        Font drawFont = null;
        StringFormat drawFormat = null;
        SizeF ms = new SizeF();
        String[] sa = null;
        width.setValue(null);
        try
        {
            // STYLE
            System.Drawing.FontStyle fs = 0;
            if (si.FontStyle == fyiReporting.RDL.FontStyleEnum.Italic)
                fs |= System.Drawing.FontStyle.Italic;
             
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
            drawFont = new Font(StyleInfo.getFontFamily(si.getFontFamilyFull()), si.FontSize, fs);
            drawFormat = new StringFormat();
            drawFormat.Alignment = StringAlignment.Near;
            // Measure string
            //  pt.NoClip indicates that this was generated by PageTextHtml Build.  It has already word wrapped.
            if (pt.getNoClip() || pt.getSI().WritingMode == WritingModeEnum.tb_rl)
            {
                // TODO: support multiple lines for vertical text
                ms = measureString(s,g,drawFont,drawFormat);
                width.setValue(new float[1]);
                width.getValue()[0] = RSize.PointsFromPixels(g, ms.Width);
                // convert to points from pixels
                sa = new String[1];
                sa[0] = s;
                return sa;
            }
             
            // handle multiple lines;
            //  1) split the string into the forced line breaks (ie "\n and \r")
            //  2) foreach of the forced line breaks; break these into words and recombine
            s = s.Replace("\r\n", "\n");
            // don't want this to result in double lines
            String[] flines = s.Split(lineBreak);
            List<String> lines = new List<String>();
            List<float> lineWidths = new List<float>();
            // remove the size reserved for left and right padding
            float ptWidth = pt.getW() - pt.getSI().PaddingLeft - pt.getSI().PaddingRight;
            if (ptWidth <= 0)
                ptWidth = 1;
             
            for (Object __dummyForeachVar2 : flines)
            {
                String tfl = (String)__dummyForeachVar2;
                String fl = new String();
                if (tfl.Length > 0 && tfl[tfl.Length - 1] == ' ')
                    fl = tfl.TrimEnd(' ');
                else
                    fl = tfl; 
                // Check if entire string fits into a line
                ms = measureString(fl,g,drawFont,drawFormat);
                float tw = RSize.PointsFromPixels(g, ms.Width);
                if (tw <= ptWidth)
                {
                    // line fits don't need to break it down further
                    lines.Add(fl);
                    lineWidths.Add(tw);
                    continue;
                }
                 
                // Line too long; need to break into multiple lines
                // 1) break line into parts; then build up again keeping track of word positions
                String[] parts = fl.Split(wordBreak);
                // this is the maximum split of lines
                StringBuilder sb = new StringBuilder(fl.Length);
                CharacterRange[] cra = new CharacterRange[parts.Length];
                for (int i = 0;i < parts.Length;i++)
                {
                    int sc = sb.Length;
                    // starting character
                    sb.Append(parts[i]);
                    // endding character
                    int ec = sb.Length;
                    if (i != parts.Length - 1)
                        // last item doesn't need blank
                        sb.Append(" ");
                     
                    CharacterRange cr = new CharacterRange(sc, ec - sc);
                    cra[i] = cr;
                }
                // add to character array
                // 2) Measure the word locations within the line
                String wfl = sb.ToString();
                float[] wordLocations = MeasureString(wfl, g, drawFont, drawFormat, cra);
                if (wordLocations == null)
                    continue;
                 
                // 3) Loop thru creating new lines as needed
                int startLoc = 0;
                CharacterRange crs = cra[startLoc];
                CharacterRange cre = cra[startLoc];
                float cwidth = wordLocations[0];
                // length of the first
                String ts = new String();
                for (int i = 1;i < cra.Length;i++)
                {
                    cwidth = wordLocations[i] - (startLoc == 0 ? 0 : wordLocations[startLoc - 1]);
                    if (cwidth > ptWidth)
                    {
                        // time for a new line
                        cre = cra[i - 1];
                        ts = wfl.Substring(crs.First, cre.First + cre.Length - crs.First);
                        startLoc = i;
                        crs = cre = cra[startLoc];
                        lines.Add(ts);
                        lineWidths.Add(cwidth);
                    }
                    else
                        cre = cra[i]; 
                }
                ts = fl.Substring(crs.First, cre.First + cre.Length - crs.First);
                lines.Add(ts);
                lineWidths.Add(cwidth);
            }
            // create the final array from the Lists
            String[] la = lines.ToArray();
            width.setValue(lineWidths.ToArray());
            return la;
        }
        finally
        {
            if (drawFont != null)
                drawFont.Dispose();
             
            if (drawFormat != null)
                drawFont.Dispose();
             
        }
    }

    /**
    * Measures the location of an arbritrary # of words within a string
    */
    private float[] measureString(String s, Graphics g, Font drawFont, StringFormat drawFormat, CharacterRange[] cra) throws Exception {
        if (cra.Length <= MEASUREMAX)
            return MeasureString32(s, g, drawFont, drawFormat, cra);
         
        // handle the simple case of < MEASUREMAX words
        // Need to compensate for SetMeasurableCharacterRanges limitation of 32 (MEASUREMAX)
        int mcra = (cra.Length / MEASUREMAX);
        // # of full 32 arrays we need
        int ip = cra.Length % MEASUREMAX;
        // # of partial entries needed for last array (if any)
        float[] sz = new float[cra.Length];
        // this is the final result;
        float startPos = 0;
        CharacterRange[] cra32 = new CharacterRange[MEASUREMAX];
        // fill out
        int icra = 0;
        for (int i = 0;i < mcra;i++)
        {
            // index thru the cra
            // fill out the new array
            int ticra = icra;
            for (int j = 0;j < cra32.Length;j++)
            {
                cra32[j] = cra[ticra++];
                cra32[j].First -= cra[icra].First;
            }
            // adjust relative offsets of strings
            // measure the word locations (in the new string)
            // ???? should I put a blank in front of it??
            String ts = s.Substring(cra[icra].First, cra[icra + cra32.Length - 1].First + cra[icra + cra32.Length - 1].Length - cra[icra].First);
            float[] pos = MeasureString32(ts, g, drawFont, drawFormat, cra32);
            for (int j = 0;j < pos.Length;j++)
                // copy the values adding in the new starting positions
                sz[icra++] = pos[j] + startPos;
            startPos = sz[icra - 1];
        }
        // reset the start position for the next line
        // handle the remaining character
        if (ip > 0)
        {
            // resize the range array
            cra32 = new CharacterRange[ip];
            // fill out the new array
            int ticra = icra;
            for (int j = 0;j < cra32.Length;j++)
            {
                cra32[j] = cra[ticra++];
                cra32[j].First -= cra[icra].First;
            }
            // adjust relative offsets of strings
            // measure the word locations (in the new string)
            // ???? should I put a blank in front of it??
            String ts = s.Substring(cra[icra].First, cra[icra + cra32.Length - 1].First + cra[icra + cra32.Length - 1].Length - cra[icra].First);
            float[] pos = MeasureString32(ts, g, drawFont, drawFormat, cra32);
            for (int j = 0;j < pos.Length;j++)
                // copy the values adding in the new starting positions
                sz[icra++] = pos[j] + startPos;
        }
         
        return sz;
    }

    /**
    * Measures the location of words within a string;  limited by .Net 1.1 to 32 words
    * MEASUREMAX is a constant that defines that limit
    * 
    *  @param s 
    *  @param g 
    *  @param drawFont 
    *  @param drawFormat 
    *  @param cra 
    *  @return
    */
    private float[] measureString32(String s, Graphics g, Font drawFont, StringFormat drawFormat, CharacterRange[] cra) throws Exception {
        if (s == null || s.Length == 0)
            return null;
         
        drawFormat.SetMeasurableCharacterRanges(cra);
        Region[] rs = new Region[cra.Length];
        rs = g.MeasureCharacterRanges(s, drawFont, new RectangleF(0, 0, float.MaxValue, float.MaxValue), drawFormat);
        float[] sz = new float[cra.Length];
        int isz = 0;
        for (Object __dummyForeachVar3 : rs)
        {
            Region r = (Region)__dummyForeachVar3;
            RectangleF mr = r.GetBounds(g);
            sz[isz++] = RSize.PointsFromPixels(g, mr.Right);
        }
        return sz;
    }

    private SizeF measureString(String s, Graphics g, Font drawFont, StringFormat drawFormat) throws Exception {
        if (s == null || s.Length == 0)
            return SizeF.Empty;
         
        CharacterRange[] cr;
        drawFormat.SetMeasurableCharacterRanges(cr);
        Region[] rs = new Region[1];
        rs = g.MeasureCharacterRanges(s, drawFont, new RectangleF(0, 0, float.MaxValue, float.MaxValue), drawFormat);
        RectangleF mr = rs[0].GetBounds(g);
        return new SizeF(mr.Width, mr.Height);
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
    }

    public void dataRegionNoRows(DataRegion d, String noRowsMsg) throws Exception {
    }

    // Lists
    public boolean listStart(List l, Row r) throws Exception {
        return true;
    }

    public void listEnd(List l, Row r) throws Exception {
    }

    public void listEntryBegin(List l, Row r) throws Exception {
    }

    public void listEntryEnd(List l, Row r) throws Exception {
    }

    // Tables					// Report item table
    public boolean tableStart(Table t, Row row) throws Exception {
        return true;
    }

    public void tableEnd(Table t, Row row) throws Exception {
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
    }

    public void tableRowEnd(TableRow tr, Row row) throws Exception {
    }

    public void tableCellStart(TableCell t, Row row) throws Exception {
        return ;
    }

    public void tableCellEnd(TableCell t, Row row) throws Exception {
        return ;
    }

    public boolean matrixStart(Matrix m, Row r) throws Exception {
        return true;
    }

    // called first
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
    }

    // called last
    public void chart(Chart c, Row r, ChartBase cb) throws Exception {
    }

    public void image(fyiReporting.RDL.Image i, Row r, String mimeType, Stream ior) throws Exception {
    }

    public void line(Line l, Row r) throws Exception {
        return ;
    }

    public boolean rectangleStart(Rectangle rect, Row r) throws Exception {
        return true;
    }

    public void rectangleEnd(Rectangle rect, Row r) throws Exception {
    }

    public void subreport(Subreport s, Row r) throws Exception {
    }

    public void groupingStart(Grouping g) throws Exception {
    }

    // called at start of grouping
    public void groupingInstanceStart(Grouping g) throws Exception {
    }

    // called at start for each grouping instance
    public void groupingInstanceEnd(Grouping g) throws Exception {
    }

    // called at start for each grouping instance
    public void groupingEnd(Grouping g) throws Exception {
    }

}


// called at end of grouping