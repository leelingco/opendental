//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.Action;
import fyiReporting.RDL.Body;
import fyiReporting.RDL.Chart;
import fyiReporting.RDL.ChartBase;
import fyiReporting.RDL.ColumnGrouping;
import fyiReporting.RDL.Corner;
import fyiReporting.RDL.CssCacheEntry2;
import fyiReporting.RDL.DataRegion;
import fyiReporting.RDL.Details;
import fyiReporting.RDL.DrillthroughParameter;
import fyiReporting.RDL.DynamicColumns;
import fyiReporting.RDL.DynamicRows;
import fyiReporting.RDL.Footer;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.Header;
import fyiReporting.RDL.Image;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.IStreamGen;
import fyiReporting.RDL.Line;
import fyiReporting.RDL.List;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.MatrixCell;
import fyiReporting.RDL.MatrixColumns;
import fyiReporting.RDL.OneFileStreamGen;
import fyiReporting.RDL.PageFooter;
import fyiReporting.RDL.PageHeader;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.Rectangle;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RowGrouping;
import fyiReporting.RDL.StaticColumn;
import fyiReporting.RDL.StaticRow;
import fyiReporting.RDL.Style;
import fyiReporting.RDL.Subreport;
import fyiReporting.RDL.Subtotal;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.TableCell;
import fyiReporting.RDL.TableColumn;
import fyiReporting.RDL.TableGroup;
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
* Renders a report to HTML.  All positioning is handled via tables.
*/
public class RenderHtmlTable   implements IPresent
{
    fyiReporting.RDL.Report r;
    // report
    StringWriter tw = new StringWriter();
    // temporary location where the output is going
    IStreamGen _sg;
    // stream generater
    Hashtable _styles = new Hashtable();
    // hash table of styles we've generated
    int cssId = 1;
    // ID for css when names are usable or available
    boolean bScriptToggle = false;
    // need to generate toggle javascript in header
    boolean bScriptTableSort = false;
    // need to generate table sort javascript in header
    Bitmap _bm = null;
    // bm and
    Graphics _g = null;
    //		  g are needed when calculating string heights
    boolean _Asp = false;
    // denotes ASP.NET compatible HTML; e.g. no <html>, <body>
    //    separate JavaScript and CSS
    String _Prefix = "";
    // prefix to generating all HTML names (e.g. css, ...
    String _CSS = new String();
    // when ASP we put the CSS into a string
    String _JavaScript = new String();
    //     as well as any required javascript
    int _SkipMatrixCols = 0;
    // # of matrix columns to skip
    public RenderHtmlTable(fyiReporting.RDL.Report rep, IStreamGen sg) throws Exception {
        r = rep;
        _sg = sg;
        // We need this in future
        tw = new StringWriter();
        // will hold the bulk of the HTML until we generate
        // final file
        _styles = new Hashtable();
    }

    protected void finalize() throws Throwable {
        try
        {
            // These should already be cleaned up; but in case of an unexpected error
            //   these still need to be disposed of
            if (_bm != null)
                _bm.Dispose();
             
            if (_g != null)
                _g.Dispose();
             
        }
        finally
        {
            super.finalize();
        }
    }

    public fyiReporting.RDL.Report report() throws Exception {
        return r;
    }

    public boolean getAsp() throws Exception {
        return _Asp;
    }

    public void setAsp(boolean value) throws Exception {
        _Asp = value;
    }

    public String getJavaScript() throws Exception {
        return _JavaScript;
    }

    public String getCSS() throws Exception {
        return _CSS;
    }

    public String getPrefix() throws Exception {
        return _Prefix;
    }

    public void setPrefix(String value) throws Exception {
        _Prefix = value == null ? "" : value;
        if (_Prefix.Length > 0 && _Prefix[0] == '_')
            _Prefix = "a" + _Prefix;
         
    }

    // not perfect but underscores as first letter don't work
    public boolean isPagingNeeded() throws Exception {
        return false;
    }

    public void start() throws Exception {
        return ;
    }

    // Create three tables that represent how each top level report item (e.g. those not in a table
    // or matrix) will be positioned.
    String fixupRelativeName(String relativeName) throws Exception {
        if (_sg instanceof OneFileStreamGen)
        {
            if (relativeName[0] == Path.DirectorySeparatorChar || relativeName[0] == Path.AltDirectorySeparatorChar)
                relativeName = relativeName.Substring(1);
             
        }
        else if (relativeName[0] != Path.DirectorySeparatorChar)
            relativeName = Path.DirectorySeparatorChar + relativeName;
          
        return relativeName;
    }

    // puts the JavaScript into the header
    private void scriptGenerate(TextWriter ftw) throws Exception {
        if (bScriptToggle || bScriptTableSort)
        {
            ftw.WriteLine("<script language=\"javascript\">");
        }
         
        if (bScriptToggle)
        {
            ftw.WriteLine("var dname='';");
            ftw.WriteLine("function hideShow(node, hideCount, showID) {\r\n" + 
            "if (navigator.appName.toLowerCase().indexOf(\'netscape\') > -1) \r\n" + 
            "\tdname = \'table-row\';\r\n" + 
            "else\r\n" + 
            "\tdname = \'block\';\r\n" + 
            "\r\n" + 
            " var tNode;\r\n" + 
            " for (var ci=0;ci<node.childNodes.length;ci++) {\r\n" + 
            "     if (node.childNodes[ci].tagName && node.childNodes[ci].tagName.toLowerCase() == \'img\') tNode = node.childNodes[ci];\r\n" + 
            " }\r\n" + 
            "\tvar rows = findObject(showID);\r\n" + 
            "\tif (rows[0].style.display == dname) {hideRows(rows, hideCount); tNode.src=\'plus.gif\';}\r\n" + 
            "\telse {\r\n" + 
            "\t   tNode.src=\'minus.gif\';\r\n" + 
            "\t   for (var i = 0; i < rows.length; i++) {\r\n" + 
            "\t\t  rows[i].style.display = dname;\r\n" + 
            "\t   }\r\n" + 
            "\t}\r\n" + 
            "}\r\n" + 
            "function hideRows(rows, count) {\r\n" + 
            "    var row;\r\n" + 
            "\tif (navigator.appName.toLowerCase().indexOf(\'netscape\') > -1) \r\n" + 
            "\t{\r\n" + 
            "\t\tfor (var r=0; r < rows.length; r++) {\r\n" + 
            "\t\t\trow = rows[r];\r\n" + 
            "\t\t\trow.style.display = \'none\';\r\n" + 
            "\t\t\tvar imgs = row.getElementsByTagName(\'img\');\r\n" + 
            "\t\t\tfor (var ci=0;ci<imgs.length;ci++) {\r\n" + 
            "\t\t\t\tif (imgs[ci].className == \'toggle\') {\r\n" + 
            "\t\t\t\t\timgs[ci].src=\'plus.gif\';\r\n" + 
            "\t\t\t\t}\r\n" + 
            "\t\t\t}\r\n" + 
            "\t\t}\r\n" + 
            "\treturn;\r\n" + 
            "\t}\r\n" + 
            " if (rows.tagName == \'TR\')\r\n" + 
            "    row = rows;\r\n" + 
            " else\r\n" + 
            "    row = rows[0];   \t\r\n" + 
            " while (count > 0) {\r\n" + 
            "   row.style.display = \'none\';\r\n" + 
            "   var imgs = row.getElementsByTagName(\'img\');\r\n" + 
            "   for (var ci=0;ci<imgs.length;ci++) {\r\n" + 
            "      if (imgs[ci].className == \'toggle\') {\r\n" + 
            "\t\t     imgs[ci].src=\'plus.gif\';\r\n" + 
            "      }\r\n" + 
            "   }\r\n" + 
            "   row = row.nextSibling;\r\n" + 
            "   count--;\r\n" + 
            " }\r\n" + 
            "}\r\n" + 
            "function findObject(id) {\r\n" + 
            "\tif (navigator.appName.toLowerCase().indexOf(\'netscape\') > -1) \r\n" + 
            "\t{\r\n" + 
            "\t   var a = new Array();\r\n" + 
            "\t   var count=0;\r\n" + 
            "\t   for (var i=0; i < document.all.length; i++)\r\n" + 
            "\t   {\r\n" + 
            "\t      if (document.all[i].id == id)\r\n" + 
            "\t\t\ta[count++] = document.all[i];\r\n" + 
            "\t   }\r\n" + 
            "\t\treturn a;\r\n" + 
            "\t} \r\n" + 
            "\telse \r\n" + 
            "\t{\r\n" + 
            "\t    var o = document.all[id];\r\n" + 
            "\t\tif (o.tagName == \'TR\')\r\n" + 
            "\t\t{\r\n" + 
            "\t\t   var a = new Array();\r\n" + 
            "\t\t   a[0] = o;\r\n" + 
            "\t\t   return a;\r\n" + 
            "\t\t}\r\n" + 
            "\t\treturn o;\r\n" + 
            "\t} \r\n" + 
            "}\r\n");
        }
         
        if (bScriptTableSort)
        {
            ftw.WriteLine("var SORT_INDEX;");
            ftw.WriteLine("var SORT_DIR;");
            ftw.WriteLine("function sort_getInnerText(element) {");
            ftw.WriteLine("	if (typeof element == 'string') return element;");
            ftw.WriteLine("	if (typeof element == 'undefined') return element;");
            ftw.WriteLine("	if (element.innerText) return element.innerText;");
            ftw.WriteLine("	var s = '';");
            ftw.WriteLine("	var cn = element.childNodes;");
            ftw.WriteLine("	for (var i = 0; i < cn.length; i++) {");
            ftw.WriteLine("		switch (cn[i].nodeType) {");
            ftw.WriteLine("			case 1:");
            // element node
            ftw.WriteLine("				s += sort_getInnerText(cn[i]);");
            ftw.WriteLine("				break;");
            ftw.WriteLine("			case 3:");
            // text node
            ftw.WriteLine("				s += cn[i].nodeValue;");
            ftw.WriteLine("				break;");
            ftw.WriteLine("		}");
            ftw.WriteLine("	}");
            ftw.WriteLine("	return s;");
            ftw.WriteLine("}");
            ftw.WriteLine("function sort_table(node, sortfn, header_rows, footer_rows) {");
            ftw.WriteLine("    var arrowNode;");
            // arrow node
            ftw.WriteLine("    for (var ci=0;ci<node.childNodes.length;ci++) {");
            ftw.WriteLine("        if (node.childNodes[ci].tagName && node.childNodes[ci].tagName.toLowerCase() == 'span') arrowNode = node.childNodes[ci];");
            ftw.WriteLine("    }");
            ftw.WriteLine("    var td = node.parentNode;");
            ftw.WriteLine("    SORT_INDEX = td.cellIndex;");
            // need to remember SORT_INDEX in compare function
            ftw.WriteLine("    var table = sort_getTable(td);");
            ftw.WriteLine("    var sortnext;");
            ftw.WriteLine("    if (arrowNode.getAttribute('sortdir') == 'down') {");
            ftw.WriteLine("        arrow = '&nbsp;&nbsp;&uarr;';");
            ftw.WriteLine("        SORT_DIR = -1;");
            // descending SORT_DIR in compare function
            ftw.WriteLine("        sortnext = 'up';");
            ftw.WriteLine("    } else {");
            ftw.WriteLine("        arrow = '&nbsp;&nbsp;&darr;';");
            ftw.WriteLine("        SORT_DIR = 1;");
            // ascending SORT_DIR in compare function
            ftw.WriteLine("        sortnext = 'down';");
            ftw.WriteLine("    }");
            ftw.WriteLine("    var newRows = new Array();");
            ftw.WriteLine("    for (j=header_rows;j<table.rows.length-footer_rows;j++) { newRows[j-header_rows] = table.rows[j]; }");
            ftw.WriteLine("    newRows.sort(sortfn);");
            // We appendChild rows that already exist to the tbody, so it moves them rather than creating new ones
            ftw.WriteLine("    for (i=0;i<newRows.length;i++) {table.tBodies[0].appendChild(newRows[i]);}");
            // Reset all arrows and directions for next time
            ftw.WriteLine("    var spans = document.getElementsByTagName('span');");
            ftw.WriteLine("    for (var ci=0;ci<spans.length;ci++) {");
            ftw.WriteLine("        if (spans[ci].className == 'sortarrow') {");
            // in the same table as us?
            ftw.WriteLine("            if (sort_getTable(spans[ci]) == sort_getTable(node)) {");
            ftw.WriteLine("                spans[ci].innerHTML = '&nbsp;&nbsp;&nbsp;';");
            ftw.WriteLine("                spans[ci].setAttribute('sortdir','up');");
            ftw.WriteLine("            }");
            ftw.WriteLine("        }");
            ftw.WriteLine("    }");
            ftw.WriteLine("    arrowNode.innerHTML = arrow;");
            ftw.WriteLine("    arrowNode.setAttribute('sortdir',sortnext);");
            ftw.WriteLine("}");
            ftw.WriteLine("function sort_getTable(el) {");
            ftw.WriteLine("	if (el == null) return null;");
            ftw.WriteLine("	else if (el.nodeType == 1 && el.tagName.toLowerCase() == 'table')");
            ftw.WriteLine("		return el;");
            ftw.WriteLine("	else");
            ftw.WriteLine("		return sort_getTable(el.parentNode);");
            ftw.WriteLine("}");
            ftw.WriteLine("function sort_cmp_date(c1,c2) {");
            ftw.WriteLine("    t1 = sort_getInnerText(c1.cells[SORT_INDEX]);");
            ftw.WriteLine("    t2 = sort_getInnerText(c2.cells[SORT_INDEX]);");
            ftw.WriteLine("    dt1 = new Date(t1);");
            ftw.WriteLine("    dt2 = new Date(t2);");
            ftw.WriteLine("    if (dt1==dt2) return 0;");
            ftw.WriteLine("    if (dt1<dt2) return -SORT_DIR;");
            ftw.WriteLine("    return SORT_DIR;");
            ftw.WriteLine("}");
            // numeric - removes any extraneous formating characters before parsing
            ftw.WriteLine("function sort_cmp_number(c1,c2) {");
            ftw.WriteLine("    t1 = sort_getInnerText(c1.cells[SORT_INDEX]).replace(/[^0-9.]/g,'');");
            ftw.WriteLine("    t2 = sort_getInnerText(c2.cells[SORT_INDEX]).replace(/[^0-9.]/g,'');");
            ftw.WriteLine("    n1 = parseFloat(t1);");
            ftw.WriteLine("    n2 = parseFloat(t2);");
            ftw.WriteLine("    if (isNaN(n1)) n1 = Number.MAX_VALUE");
            ftw.WriteLine("    if (isNaN(n2)) n2 = Number.MAX_VALUE");
            ftw.WriteLine("    return (n1 - n2)*SORT_DIR;");
            ftw.WriteLine("}");
            // For string we first do a case insensitive comparison;
            //   when equal we then do a case sensitive comparison
            ftw.WriteLine("function sort_cmp_string(c1,c2) {");
            ftw.WriteLine("    t1 = sort_getInnerText(c1.cells[SORT_INDEX]).toLowerCase();");
            ftw.WriteLine("    t2 = sort_getInnerText(c2.cells[SORT_INDEX]).toLowerCase();");
            ftw.WriteLine("    if (t1==t2) return sort_cmp_casesensitive(c1,c2);");
            ftw.WriteLine("    if (t1<t2) return -SORT_DIR;");
            ftw.WriteLine("    return SORT_DIR;");
            ftw.WriteLine("}");
            ftw.WriteLine("function sort_cmp_casesensitive(c1,c2) {");
            ftw.WriteLine("    t1 = sort_getInnerText(c1.cells[SORT_INDEX]);");
            ftw.WriteLine("    t2 = sort_getInnerText(c2.cells[SORT_INDEX]);");
            ftw.WriteLine("    if (t1==t2) return 0;");
            ftw.WriteLine("    if (t2<t2) return -SORT_DIR;");
            ftw.WriteLine("    return SORT_DIR;");
            ftw.WriteLine("}");
        }
         
        if (bScriptToggle || bScriptTableSort)
        {
            ftw.WriteLine("</script>");
        }
         
        return ;
    }

    // handle the Action tag
    private String action(Action a, Row r, String t, String tooltip) throws Exception {
        if (a == null)
            return t;
         
        String result = t;
        if (a.getHyperlink() != null)
        {
            // Handle a hyperlink
            String url = a.hyperLinkValue(this.r,r);
            if (tooltip == null)
                result = String.Format("<a target=\"_top\" href=\"{0}\">{1}</a>", url, t);
            else
                result = String.Format("<a target=\"_top\" href=\"{0}\" title=\"{1}\">{2}</a>", url, tooltip, t); 
        }
        else if (a.getDrill() != null)
        {
            // Handle a drill through
            StringBuilder args = new StringBuilder("<a target=\"_top\" href=\"");
            if (_Asp)
                // for ASP we go thru the default page and pass it as an argument
                args.Append("Default.aspx?rs:url=");
             
            args.Append(a.getDrill().getReportName());
            args.Append(".rdl");
            if (a.getDrill().getDrillthroughParameters() != null)
            {
                boolean bFirst = !_Asp;
                for (Object __dummyForeachVar0 : a.getDrill().getDrillthroughParameters().getItems())
                {
                    // ASP already have an argument
                    DrillthroughParameter dtp = (DrillthroughParameter)__dummyForeachVar0;
                    if (!dtp.omitValue(this.r,r))
                    {
                        if (bFirst)
                        {
                            // First parameter - prefixed by '?'
                            args.Append('?');
                            bFirst = false;
                        }
                        else
                        {
                            // Subsequant parameters - prefixed by '&'
                            args.Append('&');
                        } 
                        args.Append(dtp.getName().getNm());
                        args.Append('=');
                        args.Append(dtp.valueValue(this.r,r));
                    }
                     
                }
            }
             
            args.Append('"');
            if (tooltip != null)
                args.Append(String.Format(" title=\"{0}\"", tooltip));
             
            args.Append(">");
            args.Append(t);
            args.Append("</a>");
            result = args.ToString();
        }
        else if (a.getBookmarkLink() != null)
        {
            // Handle a bookmark
            String bm = a.bookmarkLinkValue(this.r,r);
            if (tooltip == null)
                result = String.Format("<a href=\"#{0}\">{1}</a>", bm, t);
            else
                result = String.Format("<a href=\"#{0}\" title=\"{1}\">{2}</a>", bm, tooltip, t); 
        }
           
        return result;
    }

    private String bookmark(String bm, String t) throws Exception {
        if (bm == null)
            return t;
         
        return String.Format("<div id=\"{0}\">{1}</div>", bm, t);
    }

    // Generate the CSS styles and put them in the header
    private void cssGenerate(TextWriter ftw) throws Exception {
        if (_styles.Count <= 0)
            return ;
         
        if (!_Asp)
            ftw.WriteLine("<style type='text/css'>");
         
        for (Object __dummyForeachVar1 : _styles.Values)
        {
            CssCacheEntry2 cce = (CssCacheEntry2)__dummyForeachVar1;
            int i = cce.getCss().IndexOf('{');
            if (cce.getName().IndexOf('#') >= 0)
                ftw.WriteLine("{0} {1}", cce.getName(), cce.getCss().Substring(i));
            else
                ftw.WriteLine(".{0} {1}", cce.getName(), cce.getCss().Substring(i)); 
        }
        if (!_Asp)
            ftw.WriteLine("</style>");
         
    }

    private String cssAdd(Style s, ReportLink rl, Row row) throws Exception {
        return CssAdd(s, rl, row, false, float.MinValue, float.MinValue);
    }

    private String cssAdd(Style s, ReportLink rl, Row row, boolean bForceRelative) throws Exception {
        return CssAdd(s, rl, row, bForceRelative, float.MinValue, float.MinValue);
    }

    private String cssAdd(Style s, ReportLink rl, Row row, boolean bForceRelative, float h, float w) throws Exception {
        String css = new String();
        String prefix = cssPrefix(s,rl);
        if (_Asp && StringSupport.equals(prefix, "table#"))
            bForceRelative = true;
         
        if (s != null)
            css = prefix + "{" + cssPosition(rl,row,bForceRelative,h,w) + s.getCSS(this.r,row,true) + "}";
        else if (rl instanceof Table || rl instanceof Matrix)
            css = prefix + "{" + cssPosition(rl,row,bForceRelative,h,w) + "border-collapse:collapse;)";
        else
            css = prefix + "{" + cssPosition(rl,row,bForceRelative,h,w) + "}";  
        CssCacheEntry2 cce = (CssCacheEntry2)_styles[css];
        if (cce == null)
        {
            String name = prefix + this.getPrefix() + "css" + (cssId++).ToString();
            cce = new CssCacheEntry2(css,name);
            _styles.Add(cce.getCss(), cce);
        }
         
        int i = cce.getName().IndexOf('#');
        if (i > 0)
            return cce.getName().Substring(i + 1);
        else
            return cce.getName(); 
    }

    private String cssPosition(ReportLink rl, Row row, boolean bForceRelative, float h, float w) throws Exception {
        if (!(rl instanceof ReportItem))
            return "";
         
        for (ReportLink p = rl.Parent;p != null;p = p.Parent)
        {
            // if not a report item then no position
            // no positioning within a table
            if (p instanceof TableCell)
                return "";
             
            if (p instanceof RowGrouping || p instanceof MatrixCell || p instanceof ColumnGrouping || p instanceof Corner)
            {
                StringBuilder sb2 = new StringBuilder();
                if (h != float.MinValue)
                    sb2.AppendFormat(NumberFormatInfo.InvariantInfo, "height: {0}pt; ", h);
                 
                if (w != float.MinValue)
                    sb2.AppendFormat(NumberFormatInfo.InvariantInfo, "width: {0}pt; ", w);
                 
                return sb2.ToString();
            }
             
        }
        // TODO: optimize by putting this into ReportItem and caching result???
        ReportItem ri = (ReportItem)rl;
        StringBuilder sb = new StringBuilder();
        if (ri.getLeft() != null)
        {
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "left: {0}; ", ri.getLeft().getOriginal());
        }
         
        if (!(ri instanceof Matrix))
        {
            if (ri.getWidth() != null)
                sb.AppendFormat(NumberFormatInfo.InvariantInfo, "width: {0}; ", ri.getWidth().getOriginal());
             
        }
         
        if (ri.getTop() != null)
        {
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "top: {0}pt; ", ri.gap(this.r));
        }
         
        if (ri instanceof List)
        {
            List l = ri instanceof List ? (List)ri : (List)null;
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "height: {0}pt; ", l.heightOfList(this.r,getGetGraphics(),row));
        }
        else if (ri instanceof Matrix || ri instanceof Table)
        {
        }
        else if (ri.getHeight() != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "height: {0}; ", ri.getHeight().getOriginal());
           
        if (sb.Length > 0)
        {
            if (bForceRelative || ri.getYParents() != null)
                sb.Insert(0, "position: relative; ");
            else
                sb.Insert(0, "position: absolute; "); 
        }
         
        return sb.ToString();
    }

    private Graphics getGetGraphics() throws Exception {
        if (_g == null)
        {
            _bm = new Bitmap(10, 10);
            _g = Graphics.FromImage(_bm);
        }
         
        return _g;
    }

    private String cssPrefix(Style s, ReportLink rl) throws Exception {
        String cssPrefix = null;
        ReportLink p;
        if (rl instanceof Table || rl instanceof Matrix || rl instanceof Rectangle)
        {
            cssPrefix = "table#";
        }
        else if (rl instanceof Body)
        {
            cssPrefix = "body#";
        }
        else if (rl instanceof Line)
        {
            cssPrefix = "table#";
        }
        else if (rl instanceof List)
        {
            cssPrefix = "";
        }
        else if (rl instanceof Subreport)
        {
            cssPrefix = "";
        }
        else if (rl instanceof Chart)
        {
            cssPrefix = "";
        }
              
        if (cssPrefix != null)
            return cssPrefix;
         
        for (p = rl.Parent;p != null;p = p.Parent)
        {
            // now find what the style applies to
            if (p instanceof TableCell)
            {
                boolean bHead = false;
                ReportLink p2;
                for (p2 = p.Parent;p2 != null;p2 = p2.Parent)
                {
                    Type t2 = p2.GetType();
                    if (t2 == Header.class)
                    {
                        if (p2.Parent instanceof Table)
                            bHead = true;
                         
                        break;
                    }
                     
                }
                if (bHead)
                    cssPrefix = "th#";
                else
                    cssPrefix = "td#"; 
                break;
            }
            else if (p instanceof RowGrouping || p instanceof MatrixCell || p instanceof ColumnGrouping || p instanceof Corner)
            {
                cssPrefix = "td#";
                break;
            }
              
        }
        return cssPrefix == null ? "" : cssPrefix;
    }

    public void end() throws Exception {
        String bodyCssId = new String();
        if (r.getReportDefinition().getBody() != null)
            bodyCssId = cssAdd(r.getReportDefinition().getBody().getStyle(),r.getReportDefinition().getBody(),null);
        else
            // add the style for the body
            bodyCssId = null; 
        TextWriter ftw = _sg.getTextWriter();
        // the final text writer location
        if (_Asp)
        {
            // do any required JavaScript
            StringWriter sw = new StringWriter();
            ScriptGenerate(sw);
            _JavaScript = sw.ToString();
            sw.Close();
            // do any required CSS
            sw = new StringWriter();
            CssGenerate(sw);
            _CSS = sw.ToString();
            sw.Close();
        }
        else
        {
            ftw.WriteLine("<html>");
            // handle the <head>: description, javascript and CSS goes here
            ftw.WriteLine("<head>");
            scriptGenerate(ftw);
            cssGenerate(ftw);
            if (r.getDescription() != null)
                // Use description as title if provided
                ftw.WriteLine(String.Format("<title>{0}</title>", XmlUtil.xmlAnsi(r.getDescription())));
             
            ftw.WriteLine("</head>");
        } 
        // Always want an HTML body - even if report doesn't have a body stmt
        if (this._Asp)
        {
            ftw.WriteLine("<table style=\"position: relative;\">");
        }
        else if (bodyCssId != null)
            ftw.WriteLine("<body id=\'{0}\'><table>", bodyCssId);
        else
            ftw.WriteLine("<body><table>");  
        ftw.Write(tw.ToString());
        if (this._Asp)
            ftw.WriteLine("</table>");
        else
            ftw.WriteLine("</table></body></html>"); 
        if (_g != null)
        {
            _g.Dispose();
            _g = null;
        }
         
        if (_bm != null)
        {
            _bm.Dispose();
            _bm = null;
        }
         
        return ;
    }

    // Body: main container for the report
    public void bodyStart(Body b) throws Exception {
        if (b.getReportItems() != null && b.getReportItems().getItems().Count > 0)
            tw.WriteLine("<tr><td><div style=\"POSITION: relative; \">");
         
    }

    public void bodyEnd(Body b) throws Exception {
        if (b.getReportItems() != null && b.getReportItems().getItems().Count > 0)
            tw.WriteLine("</div></td></tr>");
         
    }

    public void pageHeaderStart(PageHeader ph) throws Exception {
        if (ph.getReportItems() != null && ph.getReportItems().getItems().Count > 0)
            tw.WriteLine("<tr><td><div style=\"overflow: clip; POSITION: relative; HEIGHT: {0};\">", ph.getHeight().getOriginal());
         
    }

    public void pageHeaderEnd(PageHeader ph) throws Exception {
        if (ph.getReportItems() != null && ph.getReportItems().getItems().Count > 0)
            tw.WriteLine("</div></td></tr>");
         
    }

    public void pageFooterStart(PageFooter pf) throws Exception {
        if (pf.getReportItems() != null && pf.getReportItems().getItems().Count > 0)
            tw.WriteLine("<tr><td><div style=\"overflow: clip; POSITION: relative; HEIGHT: {0};\">", pf.getHeight().getOriginal());
         
    }

    public void pageFooterEnd(PageFooter pf) throws Exception {
        if (pf.getReportItems() != null && pf.getReportItems().getItems().Count > 0)
            tw.WriteLine("</div></td></tr>");
         
    }

    public void textbox(Textbox tb, String t, Row row) throws Exception {
        if (!tb.isHtml(this.r,row))
        {
            // we leave the text as is when request is to treat as html
            //   this can screw up the generated HTML if not properly formed HTML
            // make all the characters browser readable
            t = XmlUtil.xmlAnsi(t);
            // handle any specified bookmark
            t = bookmark(tb.bookmarkValue(this.r,row),t);
            // handle any specified actions
            t = action(tb.getAction(),row,t,tb.toolTipValue(this.r,row));
        }
         
        // determine if we're in a tablecell
        Type tp = tb.Parent.Parent.GetType();
        boolean bCell = new boolean();
        if (tp == TableCell.class || tp == Corner.class || tp == DynamicColumns.class || tp == DynamicRows.class || tp == StaticRow.class || tp == StaticColumn.class || tp == Subtotal.class || tp == MatrixCell.class)
            bCell = true;
        else
            bCell = false; 
        if (tp == Rectangle.class)
            tw.Write("<td>");
         
        if (bCell)
        {
            // The cell has the formatting for this text
            if (StringSupport.equals(t, ""))
                tw.Write("<br />");
            else
                // must have something in cell for formating
                tw.Write(t); 
        }
        else
        {
            // Formatting must be specified
            String cssName = cssAdd(tb.getStyle(),tb,row);
            // get the style name for this item
            tw.Write("<div class='{0}'>{1}</div>", cssName, t);
        } 
        if (tp == Rectangle.class)
            tw.Write("</td>");
         
    }

    public void dataRegionNoRows(DataRegion d, String noRowsMsg) throws Exception {
        // no rows in table
        if (noRowsMsg == null)
            noRowsMsg = "";
         
        boolean bTableCell = d.Parent.Parent.GetType() == TableCell.class;
        if (bTableCell)
        {
            if (StringSupport.equals(noRowsMsg, ""))
                tw.Write("<br />");
            else
                tw.Write(noRowsMsg); 
        }
        else
        {
            String cssName = cssAdd(d.getStyle(),d,null);
            // get the style name for this item
            tw.Write("<div class='{0}'>{1}</div>", cssName, noRowsMsg);
        } 
    }

    // Lists
    public boolean listStart(List l, Row r) throws Exception {
        // identifiy reportitem it if necessary
        String bookmark = l.bookmarkValue(this.r,r);
        if (bookmark != null)
            //
            tw.WriteLine("<div id=\"{0}\">", bookmark);
         
        return true;
    }

    // can't use the table id since we're using for css style
    public void listEnd(List l, Row r) throws Exception {
        String bookmark = l.bookmarkValue(this.r,r);
        if (bookmark != null)
            tw.WriteLine("</div>");
         
    }

    public void listEntryBegin(List l, Row r) throws Exception {
        String cssName = cssAdd(l.getStyle(),l,r,true);
        // get the style name for this item; force to be relative
        tw.WriteLine();
        tw.WriteLine("<div class={0}>", cssName);
    }

    public void listEntryEnd(List l, Row r) throws Exception {
        tw.WriteLine();
        tw.WriteLine("</div>");
    }

    // Tables					// Report item table
    public boolean tableStart(Table t, Row row) throws Exception {
        String cssName = cssAdd(t.getStyle(),t,row);
        // get the style name for this item
        // Determine if report custom defn want this table to be sortable
        if (isTableSortable(t))
        {
            this.bScriptTableSort = true;
        }
         
        String bookmark = t.bookmarkValue(this.r,row);
        if (bookmark != null)
            tw.WriteLine("<div id=\"{0}\">", bookmark);
         
        // can't use the table id since we're using for css style
        // Calculate the width of all the columns
        int width = t.widthInPixels(this.r,row);
        if (width <= 0)
            tw.WriteLine("<table id='{0}'>", cssName);
        else
            tw.WriteLine("<table id='{0}' width={1}>", cssName, width); 
        return true;
    }

    public boolean isTableSortable(Table t) throws Exception {
        if (t.getTableGroups() != null || t.getDetails() == null || t.getDetails().getTableRows() == null || t.getDetails().getTableRows().getItems().Count != 1)
            return false;
         
        // can't have tableGroups; must have 1 detail row
        // Determine if report custom defn want this table to be sortable
        boolean bReturn = false;
        if (t.getCustom() != null)
        {
            for (Object __dummyForeachVar2 : t.getCustom().getCustomXmlNode().ChildNodes)
            {
                // Loop thru all the child nodes
                XmlNode xNodeLoop = (XmlNode)__dummyForeachVar2;
                if (StringSupport.equals(xNodeLoop.Name, "HTML"))
                {
                    if (StringSupport.equals(xNodeLoop.LastChild.InnerText.ToLower(), "true"))
                    {
                        bReturn = true;
                    }
                     
                    break;
                }
                 
            }
        }
         
        return bReturn;
    }

    public void tableEnd(Table t, Row row) throws Exception {
        String bookmark = t.bookmarkValue(this.r,row);
        if (bookmark != null)
            tw.WriteLine("</div>");
         
        tw.WriteLine("</table>");
        return ;
    }

    public void tableBodyStart(Table t, Row row) throws Exception {
        tw.WriteLine("<tbody>");
    }

    public void tableBodyEnd(Table t, Row row) throws Exception {
        tw.WriteLine("</tbody>");
    }

    public void tableFooterStart(Footer f, Row row) throws Exception {
        tw.WriteLine("<tfoot>");
    }

    public void tableFooterEnd(Footer f, Row row) throws Exception {
        tw.WriteLine("</tfoot>");
    }

    public void tableHeaderStart(Header h, Row row) throws Exception {
        tw.WriteLine("<thead>");
    }

    public void tableHeaderEnd(Header h, Row row) throws Exception {
        tw.WriteLine("</thead>");
    }

    public void tableRowStart(TableRow tr, Row row) throws Exception {
        tw.Write("\t<tr");
        ReportLink rl = tr.Parent.Parent;
        Visibility v = null;
        Textbox togText = null;
        // holds the toggle text box if any
        if (rl instanceof Details)
        {
            Details d = (Details)rl;
            v = d.getVisibility();
            togText = d.getToggleTextbox();
        }
        else if (rl.Parent instanceof TableGroup)
        {
            TableGroup tg = (TableGroup)rl.Parent;
            v = tg.getVisibility();
            togText = tg.getToggleTextbox();
        }
          
        if (v != null && v.getHidden() != null)
        {
            boolean bHide = v.getHidden().evaluateBoolean(this.r,row);
            if (bHide)
                tw.Write(" style=\"display:none;\"");
             
        }
         
        if (togText != null && togText.getName() != null)
        {
            String name = togText.getName().getNm() + "_" + togText.runCount(this.r).ToString();
            tw.Write(" id='{0}'", name);
        }
         
        tw.Write(">");
    }

    public void tableRowEnd(TableRow tr, Row row) throws Exception {
        tw.WriteLine("</tr>");
    }

    public void tableCellStart(TableCell t, Row row) throws Exception {
        String cellType = t.getInTableHeader() ? "th" : "td";
        ReportItem r = t.getReportItems().getItems()[0];
        String cssName = cssAdd(r.getStyle(),r,row);
        // get the style name for this item
        tw.Write("<{0} id='{1}'", cellType, cssName);
        // calculate width of column
        if (t.getInTableHeader() && t.getOwnerTable().getTableColumns() != null)
        {
            // Calculate the width across all the spanned columns
            int width = 0;
            for (int ci = t.getColIndex();ci < t.getColIndex() + t.getColSpan();ci++)
            {
                TableColumn tc = t.getOwnerTable().getTableColumns().getItems()[ci] instanceof TableColumn ? (TableColumn)t.getOwnerTable().getTableColumns().getItems()[ci] : (TableColumn)null;
                if (tc != null && tc.getWidth() != null)
                    width += tc.getWidth().getPixelsX();
                 
            }
            if (width > 0)
                tw.Write(" width={0}", width);
             
        }
         
        if (t.getColSpan() > 1)
            tw.Write(" colspan={0}", t.getColSpan());
         
        Textbox tb = r instanceof Textbox ? (Textbox)r : (Textbox)null;
        // have textbox
        //   and its a toggle
        if (tb != null && tb.getIsToggle() && tb.getName() != null)
        {
            //   and need name as well
            int groupNestCount = t.getOwnerTable().getGroupNestCount(this.r);
            if (groupNestCount > 0)
            {
                // anything to toggle?
                String name = tb.getName().getNm() + "_" + (tb.runCount(this.r) + 1).ToString();
                bScriptToggle = true;
                // need both hand and pointer because IE and Firefox use different names
                tw.Write(" onClick=\"hideShow(this, {0}, '{1}')\" onMouseOver=\"style.cursor ='hand';style.cursor ='pointer'\">", groupNestCount, name);
                tw.Write("<img class='toggle' src=\"plus.gif\" align=top/>");
            }
            else
                tw.Write("<img src=\"empty.gif\" align=top/>"); 
        }
        else
            tw.Write(">"); 
        if (t.getInTableHeader())
        {
            // put the second half of the sort tags for the column; if needed
            // first half ---- <a href="#" onclick="sort_table(this,sort_cmp_string,1,0);return false;">
            // next half follows text  ---- <span class="sortarrow">&nbsp;&nbsp;&nbsp;</span></a></th>
            String sortcmp = sortType(t,tb);
            // obtain the sort type
            if (sortcmp != null)
            {
                // null if sort not needed
                int headerRows = new int(), footerRows = new int();
                headerRows = t.getOwnerTable().getHeader().getTableRows().getItems().Count;
                // since we're in header we know we have some rows
                if (t.getOwnerTable().getFooter() != null && t.getOwnerTable().getFooter().getTableRows() != null)
                    footerRows = t.getOwnerTable().getFooter().getTableRows().getItems().Count;
                else
                    footerRows = 0; 
                tw.Write("<a href=\"#\" title='Sort' onclick=\"sort_table(this,{0},{1},{2});return false;\">", sortcmp, headerRows, footerRows);
            }
             
        }
         
        return ;
    }

    private String sortType(TableCell tc, Textbox tb) throws Exception {
        // return of null means don't sort
        if (tb == null || !isTableSortable(tc.getOwnerTable()))
            return null;
         
        // default is true if table is sortable;
        //   but user may place override on Textbox custom tag
        if (tb.getCustom() != null)
        {
            for (Object __dummyForeachVar3 : tb.getCustom().getCustomXmlNode().ChildNodes)
            {
                // Loop thru all the child nodes
                XmlNode xNodeLoop = (XmlNode)__dummyForeachVar3;
                if (StringSupport.equals(xNodeLoop.Name, "HTML"))
                {
                    if (StringSupport.equals(xNodeLoop.LastChild.InnerText.ToLower(), "false"))
                    {
                        return null;
                    }
                     
                    break;
                }
                 
            }
        }
         
        // Must find out the type of the detail column
        Details d = tc.getOwnerTable().getDetails();
        if (d == null)
            return null;
         
        TableRow tr = d.getTableRows().getItems()[0] instanceof TableRow ? (TableRow)d.getTableRows().getItems()[0] : (TableRow)null;
        if (tr == null)
            return null;
         
        TableCell dtc = tr.getTableCells().getItems()[tc.getColIndex()] instanceof TableCell ? (TableCell)tr.getTableCells().getItems()[tc.getColIndex()] : (TableCell)null;
        if (dtc == null)
            return null;
         
        Textbox dtb = dtc.getReportItems().getItems()[0] instanceof Textbox ? (Textbox)dtc.getReportItems().getItems()[0] : (Textbox)null;
        if (dtb == null)
            return null;
         
        String sortcmp = new String();
        TypeCode __dummyScrutVar0 = dtb.getValue().getType();
        if (__dummyScrutVar0.equals(TypeCode.DateTime))
        {
            sortcmp = "sort_cmp_date";
        }
        else if (__dummyScrutVar0.equals(TypeCode.Int16) || __dummyScrutVar0.equals(TypeCode.UInt16) || __dummyScrutVar0.equals(TypeCode.Int32) || __dummyScrutVar0.equals(TypeCode.UInt32) || __dummyScrutVar0.equals(TypeCode.Int64) || __dummyScrutVar0.equals(TypeCode.UInt64) || __dummyScrutVar0.equals(TypeCode.Decimal) || __dummyScrutVar0.equals(TypeCode.Single) || __dummyScrutVar0.equals(TypeCode.Double))
        {
            sortcmp = "sort_cmp_number";
        }
        else if (__dummyScrutVar0.equals(TypeCode.String))
        {
            sortcmp = "sort_cmp_string";
        }
        else
        {
            // Not a type we know how to sort
            sortcmp = null;
        }   
        return sortcmp;
    }

    public void tableCellEnd(TableCell t, Row row) throws Exception {
        String cellType = t.getInTableHeader() ? "th" : "td";
        Textbox tb = t.getReportItems().getItems()[0] instanceof Textbox ? (Textbox)t.getReportItems().getItems()[0] : (Textbox)null;
        if (StringSupport.equals(cellType, "th") && sortType(t,tb) != null)
        {
            // put the second half of the sort tags for the column
            // first half ---- <a href="#" onclick="sort_table(this,sort_cmp_string,1,0);return false;">
            // next half follows text  ---- <span class="sortarrow">&nbsp;&nbsp;&nbsp;</span></a></th>
            tw.Write("<span class=\"sortarrow\">&nbsp;&nbsp;&nbsp;</span></a>");
        }
         
        tw.Write("</{0}>", cellType);
        return ;
    }

    public boolean matrixStart(Matrix m, Row r) throws Exception {
        // called first
        String bookmark = m.bookmarkValue(this.r,r);
        if (bookmark != null)
            tw.WriteLine("<div id=\"{0}\">", bookmark);
         
        // can't use the table id since we're using for css style
        // output some of the table styles
        String cssName = cssAdd(m.getStyle(),m,r);
        // get the style name for this item
        tw.WriteLine("<table id='{0}'>", cssName);
        return true;
    }

    public void matrixColumns(Matrix m, MatrixColumns mc) throws Exception {
    }

    // called just after MatrixStart
    public void matrixCellStart(Matrix m, ReportItem ri, int row, int column, Row r, float h, float w, int colSpan) throws Exception {
        if (ri == null)
        {
            // Empty cell?
            if (_SkipMatrixCols == 0)
                tw.Write("<td>");
             
            return ;
        }
         
        String cssName = cssAdd(ri.getStyle(),ri,r,false,h,w);
        // get the style name for this item
        tw.Write("<td id='{0}'", cssName);
        if (colSpan != 1)
        {
            tw.Write(" colspan={0}", colSpan);
            _SkipMatrixCols = -(colSpan - 1);
        }
        else
            // start it as negative as indicator that we need this </td>
            _SkipMatrixCols = 0; 
        if (ri instanceof Textbox)
        {
            Textbox tb = (Textbox)ri;
            if (tb.getIsToggle() && tb.getName() != null)
            {
                // name is required for this
                String name = tb.getName().getNm() + "_" + (tb.runCount(this.r) + 1).ToString();
                bScriptToggle = true;
                // we need to generate JavaScript in header
                // TODO -- need to calculate the hide count correctly
                tw.Write(" onClick=\"hideShow(this, {0}, '{1}')\" onMouseOver=\"style.cursor ='hand'\"", 0, name);
            }
             
        }
         
        tw.Write(">");
    }

    public void matrixCellEnd(Matrix m, ReportItem ri, int row, int column, Row r) throws Exception {
        if (_SkipMatrixCols == 0)
            tw.Write("</td>");
        else if (_SkipMatrixCols < 0)
        {
            tw.Write("</td>");
            _SkipMatrixCols = -_SkipMatrixCols;
        }
        else
            _SkipMatrixCols--;  
        return ;
    }

    public void matrixRowStart(Matrix m, int row, Row r) throws Exception {
        tw.Write("\t<tr");
        tw.Write(">");
    }

    public void matrixRowEnd(Matrix m, int row, Row r) throws Exception {
        tw.WriteLine("</tr>");
    }

    public void matrixEnd(Matrix m, Row r) throws Exception {
        // called last
        tw.Write("</table>");
        String bookmark = m.bookmarkValue(this.r,r);
        if (bookmark != null)
            tw.WriteLine("</div>");
         
        return ;
    }

    public void chart(Chart c, Row r, ChartBase cb) throws Exception {
        String relativeName = new String();
        RefSupport<String> refVar___0 = new RefSupport<String>();
        Stream io = _sg.getIOStream(refVar___0,"png");
        relativeName = refVar___0.getValue();
        try
        {
            cb.Save(this.r, io, ImageFormat.Png);
        }
        finally
        {
            io.Flush();
            io.Close();
        }
        relativeName = fixupRelativeName(relativeName);
        // Create syntax in a string buffer
        StringWriter sw = new StringWriter();
        String bookmark = c.bookmarkValue(this.r,r);
        if (bookmark != null)
            sw.WriteLine("<div id=\"{0}\">", bookmark);
         
        // can't use the table id since we're using for css style
        String cssName = cssAdd(c.getStyle(),c,null);
        // get the style name for this item
        sw.Write("<img src=\"{0}\" class='{1}'", relativeName, cssName);
        String tooltip = c.toolTipValue(this.r,r);
        if (tooltip != null)
            sw.Write(" alt=\"{0}\"", tooltip);
         
        if (c.getHeight() != null)
            sw.Write(" height=\"{0}\"", c.getHeight().getPixelsY().ToString());
         
        if (c.getWidth() != null)
            sw.Write(" width=\"{0}\"", c.getWidth().getPixelsX().ToString());
         
        sw.Write(">");
        if (bookmark != null)
            sw.Write("</div>");
         
        tw.Write(Action(c.getAction(), r, sw.ToString(), tooltip));
        return ;
    }

    public void image(Image i, Row r, String mimeType, Stream ioin) throws Exception {
        String relativeName = new String();
        String suffix = new String();
        System.String __dummyScrutVar1 = mimeType;
        if (__dummyScrutVar1.equals("image/bmp"))
        {
            suffix = "bmp";
        }
        else if (__dummyScrutVar1.equals("image/jpeg"))
        {
            suffix = "jpeg";
        }
        else if (__dummyScrutVar1.equals("image/gif"))
        {
            suffix = "gif";
        }
        else if (__dummyScrutVar1.equals("image/png") || __dummyScrutVar1.equals("image/x-png"))
        {
            suffix = "png";
        }
        else
        {
            suffix = "unk";
        }    
        RefSupport<String> refVar___1 = new RefSupport<String>();
        Stream io = _sg.getIOStream(refVar___1,suffix);
        relativeName = refVar___1.getValue();
        try
        {
            if (ioin.CanSeek)
            {
                // ioin.Length requires Seek support
                byte[] ba = new byte[ioin.Length];
                ioin.Read(ba, 0, ba.Length);
                io.Write(ba, 0, ba.Length);
            }
            else
            {
                byte[] ba = new byte[1000];
                while (true)
                {
                    // read a 1000 bytes at a time
                    int length = ioin.Read(ba, 0, ba.Length);
                    if (length <= 0)
                        break;
                     
                    io.Write(ba, 0, length);
                }
            } 
        }
        finally
        {
            io.Flush();
            io.Close();
        }
        relativeName = fixupRelativeName(relativeName);
        // Create syntax in a string buffer
        StringWriter sw = new StringWriter();
        String bookmark = i.bookmarkValue(this.r,r);
        if (bookmark != null)
            sw.WriteLine("<div id=\"{0}\">", bookmark);
         
        // we're using for css style
        String cssName = cssAdd(i.getStyle(),i,null);
        // get the style name for this item
        sw.Write("<img src=\"{0}\" class='{1}'", relativeName, cssName);
        String tooltip = i.toolTipValue(this.r,r);
        if (tooltip != null)
            sw.Write(" alt=\"{0}\"", tooltip);
         
        if (i.getHeight() != null)
            sw.Write(" height=\"{0}\"", i.getHeight().getPixelsY().ToString());
         
        if (i.getWidth() != null)
            sw.Write(" width=\"{0}\"", i.getWidth().getPixelsX().ToString());
         
        sw.Write("/>");
        if (bookmark != null)
            sw.Write("</div>");
         
        tw.Write(Action(i.getAction(), r, sw.ToString(), tooltip));
        return ;
    }

    public void line(Line l, Row r) throws Exception {
        boolean bVertical = new boolean();
        String t = new String();
        if (l.getHeight() == null || l.getHeight().getPixelsY() > 0)
        {
            // only handle horizontal rule
            if (l.getWidth() == null || l.getWidth().getPixelsX() > 0)
                return ;
             
            //    and vertical rules
            bVertical = true;
            t = "<TABLE style=\"border-collapse:collapse;BORDER-STYLE: none;WIDTH: {0}; POSITION: absolute; LEFT: {1}; TOP: {2}; HEIGHT: {3}; BACKGROUND-COLOR:{4};\"><TBODY><TR style=\"WIDTH:{0}\"><TD style=\"WIDTH:{0}\"></TD></TR></TBODY></TABLE>";
        }
        else
        {
            bVertical = false;
            t = "<TABLE style=\"border-collapse:collapse;BORDER-STYLE: none;WIDTH: {0}; POSITION: absolute; LEFT: {1}; TOP: {2}; HEIGHT: {3}; BACKGROUND-COLOR:{4};\"><TBODY><TR style=\"HEIGHT:{3}\"><TD style=\"HEIGHT:{3}\"></TD></TR></TBODY></TABLE>";
        } 
        String width = new String(), left = new String(), top = new String(), height = new String(), color = new String();
        Style s = l.getStyle();
        left = l.getLeft() == null ? "0px" : l.getLeft().getOriginal();
        top = l.getTop() == null ? "0px" : l.getTop().getOriginal();
        if (bVertical)
        {
            height = l.getHeight() == null ? "0px" : l.getHeight().getOriginal();
            // width comes from the BorderWidth
            if (s != null && s.getBorderWidth() != null && s.getBorderWidth().getDefault() != null)
                width = s.getBorderWidth().getDefault().evaluateString(this.r,r);
            else
                width = "1px"; 
        }
        else
        {
            width = l.getWidth() == null ? "0px" : l.getWidth().getOriginal();
            // height comes from the BorderWidth
            if (s != null && s.getBorderWidth() != null && s.getBorderWidth().getDefault() != null)
                height = s.getBorderWidth().getDefault().evaluateString(this.r,r);
            else
                height = "1px"; 
        } 
        if (s != null && s.getBorderColor() != null && s.getBorderColor().getDefault() != null)
            color = s.getBorderColor().getDefault().evaluateString(this.r,r);
        else
            color = "black"; 
        tw.WriteLine(t, width, left, top, height, color);
        return ;
    }

    public boolean rectangleStart(Rectangle rect, Row r) throws Exception {
        String cssName = cssAdd(rect.getStyle(),rect,r);
        // get the style name for this item
        String bookmark = rect.bookmarkValue(this.r,r);
        if (bookmark != null)
            tw.WriteLine("<div id=\"{0}\">", bookmark);
         
        // can't use the table id since we're using for css style
        // Calculate the width of all the columns
        int width = rect.getWidth().getPixelsX();
        if (width < 0)
            tw.WriteLine("<table id='{0}'><tr>", cssName);
        else
            tw.WriteLine("<table id='{0}' width={1}><tr>", cssName, width); 
        return true;
    }

    public void rectangleEnd(Rectangle rect, Row r) throws Exception {
        tw.WriteLine("</tr></table>");
        String bookmark = rect.bookmarkValue(this.r,r);
        if (bookmark != null)
            tw.WriteLine("</div>");
         
        return ;
    }

    // Subreport:
    public void subreport(Subreport s, Row r) throws Exception {
        String cssName = cssAdd(s.getStyle(),s,r);
        // get the style name for this item
        tw.WriteLine("<div class='{0}'>", cssName);
        s.getReportDefn().run(this);
        tw.WriteLine("</div>");
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

    // called at end of grouping
    public void runPages(Pages pgs) throws Exception {
    }

}


