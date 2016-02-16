//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.util.ListSupport;
import fyiReporting.RDL.Body;
import fyiReporting.RDL.Classes;
import fyiReporting.RDL.Code;
import fyiReporting.RDL.CodeModules;
import fyiReporting.RDL.Custom;
import fyiReporting.RDL.DataElementStyleEnum;
import fyiReporting.RDL.DataSetsDefn;
import fyiReporting.RDL.DataSourcesDefn;
import fyiReporting.RDL.EmbeddedImages;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.FunctionExecutionTime;
import fyiReporting.RDL.FunctionPageNumber;
import fyiReporting.RDL.FunctionReportFolder;
import fyiReporting.RDL.FunctionReportName;
import fyiReporting.RDL.FunctionTotalPages;
import fyiReporting.RDL.FunctionUserID;
import fyiReporting.RDL.FunctionUserLanguage;
import fyiReporting.RDL.ICacheData;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Name;
import fyiReporting.RDL.NeedPassword;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.PageFooter;
import fyiReporting.RDL.PageHeader;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.RenderXml;
import fyiReporting.RDL.ReportLog;
import fyiReporting.RDL.ReportParameters;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.Subreport;
import fyiReporting.RDL.XmlUtil;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReportDefn   
{
    public int _ObjectCount = 0;
    public ReportLog rl;
    Name _Name;
    String _Description = new String();
    String _Author = new String();
    int _AutoRefresh = new int();
    DataSourcesDefn _DataSourcesDefn;
    public NeedPassword GetDataSourceReferencePassword = null;
    DataSetsDefn _DataSetsDefn;
    Body _Body;
    ReportParameters _ReportParameters;
    Custom _Customer;
    RSize _Width;
    PageHeader _PageHeader;
    PageFooter _PageFooter;
    RSize _PageHeight;
    RSize _PageWidth;
    RSize _LeftMargin;
    RSize _RightMargin;
    RSize _TopMargin;
    RSize _BottomMargin;
    EmbeddedImages _EmbeddedImages;
    Expression _Language;
    Code _Code;
    CodeModules _CodeModules;
    Classes _Classes;
    String _DataTransform = new String();
    String _DataSchema = new String();
    String _DataElementName = new String();
    // Name of a top level element that
    //		represents the report data. Default: Report.
    DataElementStyleEnum _DataElementStyle = DataElementStyleEnum.Auto;
    //Indicates whether textboxes should
    //		render as elements or attributes.
    Subreport _Subreport;
    // null if top level report; otherwise the subreport that loaded the report
    boolean _ContainsSubreport = new boolean();
    // true if report contains a subreport
    int _DynamicNames = 0;
    // used for creating names on the fly during parsing
    // Following variables used for parsing/evaluating expressions
    List<ICacheData> _DataCache = new List<ICacheData>();
    // contains all function that implement ICacheData
    IDictionary _LUGlobals = new IDictionary();
    // contains global and user properties
    IDictionary _LUUser = new IDictionary();
    // contains global and user properties
    IDictionary _LUReportItems = new IDictionary();
    // all TextBoxes in the report	IDictionary _LUGlobalsUser;		// contains global and user properties
    IDictionary _LUDynamicNames = new IDictionary();
    // for dynamic names
    IDictionary _LUAggrScope = new IDictionary();
    // Datasets, Dataregions, grouping names
    IDictionary _LUEmbeddedImages = new IDictionary();
    // Embedded images
    String _ParseFolder = new String();
    // temporary folder for looking up things during parse/finalpass
    Type _CodeType = new Type();
    // used for parsing of expressions; DONT USE AT RUNTIME
    // Constructor
    public ReportDefn(XmlNode xNode, ReportLog replog, String folder, NeedPassword getpswd, int objcount) throws Exception {
        // report has no parents
        rl = replog;
        // used for error reporting
        _ObjectCount = objcount;
        // starting number for objects in this report; 0 other than for subreports
        GetDataSourceReferencePassword = getpswd;
        _ParseFolder = folder;
        _Description = null;
        _Author = null;
        _AutoRefresh = -1;
        _DataSourcesDefn = null;
        _DataSetsDefn = null;
        _Body = null;
        _Width = null;
        _PageHeader = null;
        _PageFooter = null;
        _PageHeight = null;
        _PageWidth = null;
        _LeftMargin = null;
        _RightMargin = null;
        _TopMargin = null;
        _BottomMargin = null;
        _EmbeddedImages = null;
        _Language = null;
        _CodeModules = null;
        _Code = null;
        _Classes = null;
        _DataTransform = null;
        _DataSchema = null;
        _DataElementName = null;
        _DataElementStyle = DataElementStyleEnum.AttributeNormal;
        _LUReportItems = new Hashtable();
        // to hold all the textBoxes
        _LUAggrScope = new ListDictionary();
        // to hold all dataset, dataregion, grouping names
        _LUEmbeddedImages = new ListDictionary();
        // probably not very many
        _LUDynamicNames = new Hashtable();
        _DataCache = new List<ICacheData>();
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
        for (Object __dummyForeachVar1 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar1;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar1 = xNodeLoop.Name;
            if (__dummyScrutVar1.equals("Description"))
            {
                _Description = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar1.equals("Author"))
            {
                _Author = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar1.equals("AutoRefresh"))
            {
                _AutoRefresh = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar1.equals("DataSources"))
            {
                _DataSourcesDefn = new DataSourcesDefn(this,null,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("DataSets"))
            {
                _DataSetsDefn = new DataSetsDefn(this,null,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("Body"))
            {
                _Body = new Body(this,null,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("ReportParameters"))
            {
                _ReportParameters = new ReportParameters(this,null,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("Width"))
            {
                _Width = new RSize(this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("PageHeader"))
            {
                _PageHeader = new PageHeader(this,null,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("PageFooter"))
            {
                _PageFooter = new PageFooter(this,null,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("PageHeight"))
            {
                _PageHeight = new RSize(this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("PageWidth"))
            {
                _PageWidth = new RSize(this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("LeftMargin"))
            {
                _LeftMargin = new RSize(this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("RightMargin"))
            {
                _RightMargin = new RSize(this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("TopMargin"))
            {
                _TopMargin = new RSize(this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("BottomMargin"))
            {
                _BottomMargin = new RSize(this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("EmbeddedImages"))
            {
                _EmbeddedImages = new EmbeddedImages(this,null,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("Language"))
            {
                _Language = new Expression(this,null,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar1.equals("Code"))
            {
                _Code = new Code(this,null,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("CodeModules"))
            {
                _CodeModules = new CodeModules(this,null,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("Classes"))
            {
                _Classes = new Classes(this,null,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("DataTransform"))
            {
                _DataTransform = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar1.equals("DataSchema"))
            {
                _DataSchema = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar1.equals("DataElementName"))
            {
                _DataElementName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar1.equals("DataElementStyle"))
            {
                _DataElementStyle = fyiReporting.RDL.DataElementStyle.GetStyle(xNodeLoop.InnerText, this.rl);
            }
            else
            {
                // don't know this element - log it
                this.rl.logError(4,"Unknown Report element '" + xNodeLoop.Name + "' ignored.");
            }                         
        }
        if (_Body == null)
            rl.logError(8,"Body not specified for report.");
         
        if (_Width == null)
            rl.logError(4,"Width not specified for report.  Assuming page width.");
         
        if (rl.getMaxSeverity() <= 4)
        {
            // don't do final pass if already have serious errors
            finalPass(folder);
        }
         
        // call final parser pass for expression resolution
        // Cleanup any dangling resources
        if (_DataSourcesDefn != null)
            _DataSourcesDefn.cleanUp(null);
         
    }

    //
    void finalPass(String folder) throws Exception {
        // Now do some addition validation and final preparation
        // Create the Globals and User lookup dictionaries
        _LUGlobals = new ListDictionary();
        // if entries grow beyond 10; make hashtable
        _LUGlobals.Add("PageNumber", new FunctionPageNumber());
        _LUGlobals.Add("TotalPages", new FunctionTotalPages());
        _LUGlobals.Add("ExecutionTime", new FunctionExecutionTime());
        _LUGlobals.Add("ReportFolder", new FunctionReportFolder());
        _LUGlobals.Add("ReportName", new FunctionReportName());
        _LUUser = new ListDictionary();
        // if entries grow beyond 10; make hashtable
        _LUUser.Add("UserID", new FunctionUserID());
        _LUUser.Add("Language", new FunctionUserLanguage());
        if (_CodeModules != null)
        {
            _CodeModules.finalPass();
            _CodeModules.loadModules();
        }
         
        if (_Classes != null)
        {
            _Classes.finalPass();
        }
         
        // _Classes.Load();
        if (_Code != null)
        {
            _Code.finalPass();
            _CodeType = _Code.codeType();
        }
         
        if (_ReportParameters != null)
            // report parameters might be used in data source connection strings
            _ReportParameters.finalPass();
         
        if (_DataSourcesDefn != null)
            _DataSourcesDefn.finalPass();
         
        if (_DataSetsDefn != null)
            _DataSetsDefn.finalPass();
         
        _Body.finalPass();
        if (_PageHeader != null)
            _PageHeader.finalPass();
         
        if (_PageFooter != null)
            _PageFooter.finalPass();
         
        if (_EmbeddedImages != null)
            _EmbeddedImages.finalPass();
         
        if (_Language != null)
            _Language.finalPass();
         
        _DataCache.TrimExcess();
        return ;
    }

    // reduce size of array of expressions that cache data
    public Type getCodeType() throws Exception {
        return _CodeType;
    }

    public String getParseFolder() throws Exception {
        return _ParseFolder;
    }

    public int getObjectNumber() throws Exception {
        _ObjectCount++;
        return _ObjectCount;
    }

    public void setObjectNumber(int oc) throws Exception {
        _ObjectCount = oc;
    }

    // Obtain the data for the report
    public void runGetData(fyiReporting.RDL.Report rpt, IDictionary parms) throws Exception {
        // Step 1- set the parameter values for the runtime
        if (parms != null && getReportParameters() != null)
            getReportParameters().setRuntimeValues(rpt,parms);
         
        // set the parameters
        // Step 2- prep the datasources (ie connect and execute the queries)
        if (this._DataSourcesDefn != null)
            _DataSourcesDefn.connectDataSources(rpt);
         
        // Step 3- obtain the data; applying filters
        if (_DataSetsDefn != null)
        {
            resetCachedData(rpt);
            _DataSetsDefn.getData(rpt);
        }
         
        // Step 4- cleanup any DB connections
        if (_DataSourcesDefn != null)
        {
            if (!this.getContainsSubreport())
                _DataSourcesDefn.cleanUp(rpt);
             
        }
         
        return ;
    }

    // no subreports means that nothing will use this transaction
    public String createDynamicName(Object ro) throws Exception {
        _DynamicNames++;
        // increment the name generator
        String name = "o" + _DynamicNames.ToString();
        _LUDynamicNames.Add(name, ro);
        return name;
    }

    public IDictionary getLUDynamicNames() throws Exception {
        return _LUDynamicNames;
    }

    private void resetCachedData(fyiReporting.RDL.Report rpt) throws Exception {
        for (Object __dummyForeachVar2 : this._DataCache)
        {
            ICacheData icd = (ICacheData)__dummyForeachVar2;
            icd.clearCache(rpt);
        }
    }

    public void run(IPresent ip) throws Exception {
        if (_Subreport == null)
        {
            // do true intialization
            ip.start();
        }
         
        if (ip.isPagingNeeded())
        {
            runPage(ip);
        }
        else
        {
            if (_PageHeader != null && !(ip instanceof RenderXml))
                _PageHeader.run(ip,null);
             
            _Body.run(ip,null);
            if (_PageFooter != null && !(ip instanceof RenderXml))
                _PageFooter.run(ip,null);
             
        } 
        if (_Subreport == null)
            ip.end();
         
        if (_DataSourcesDefn != null)
            _DataSourcesDefn.cleanUp(ip.report());
         
    }

    // datasets may not have been cleaned up
    public void runPage(IPresent ip) throws Exception {
        Pages pgs = new Pages(ip.report());
        try
        {
            Page p = new Page(1);
            // kick it off with a new page
            pgs.addPage(p);
            // Create all the pages
            _Body.runPage(pgs);
            if (pgs.getLastPage().isEmpty())
                // get rid of extraneous pages which
                pgs.removeLastPage();
             
            //   can be caused by region page break at end
            // Now create the headers and footers for all the pages (as needed)
            if (_PageHeader != null)
                _PageHeader.runPage(pgs);
             
            if (_PageFooter != null)
                _PageFooter.runPage(pgs);
             
            pgs.sortPageItems();
            // Handle ZIndex ordering of pages
            ip.runPages(pgs);
        }
        finally
        {
            pgs.cleanUp();
            // always want to make sure we clean this up since
            if (_DataSourcesDefn != null)
                _DataSourcesDefn.cleanUp(pgs.getReport());
             
        }
        return ;
    }

    // ensure datasets are cleaned up
    public String getDescription() throws Exception {
        return _Description;
    }

    public void setDescription(String value) throws Exception {
        _Description = value;
    }

    public String getAuthor() throws Exception {
        return _Author;
    }

    public void setAuthor(String value) throws Exception {
        _Author = value;
    }

    public int getAutoRefresh() throws Exception {
        return _AutoRefresh;
    }

    public void setAutoRefresh(int value) throws Exception {
        _AutoRefresh = value;
    }

    public List<ICacheData> getDataCache() throws Exception {
        return _DataCache;
    }

    public DataSourcesDefn getDataSourcesDefn() throws Exception {
        return _DataSourcesDefn;
    }

    public DataSetsDefn getDataSetsDefn() throws Exception {
        return _DataSetsDefn;
    }

    public Body getBody() throws Exception {
        return _Body;
    }

    public void setBody(Body value) throws Exception {
        _Body = value;
    }

    public Code getCode() throws Exception {
        return _Code;
    }

    public ReportParameters getReportParameters() throws Exception {
        return _ReportParameters;
    }

    public void setReportParameters(ReportParameters value) throws Exception {
        _ReportParameters = value;
    }

    public Custom getCustomer() throws Exception {
        return _Customer;
    }

    public void setCustomer(Custom value) throws Exception {
        _Customer = value;
    }

    public String getName() throws Exception {
        return _Name == null ? null : _Name.getNm();
    }

    public void setName(String value) throws Exception {
        _Name = new Name(value);
    }

    public RSize getWidth() throws Exception {
        if (_Width == null)
            // Shouldn't be need since technically Width is required (I let it slip)
            _Width = getPageWidth();
         
        return _Width;
    }

    // Not specified; assume page width
    public void setWidth(RSize value) throws Exception {
        _Width = value;
    }

    public PageHeader getPageHeader() throws Exception {
        return _PageHeader;
    }

    public void setPageHeader(PageHeader value) throws Exception {
        _PageHeader = value;
    }

    public PageFooter getPageFooter() throws Exception {
        return _PageFooter;
    }

    public void setPageFooter(PageFooter value) throws Exception {
        _PageFooter = value;
    }

    public RSize getPageHeight() throws Exception {
        if (this.getSubreport() != null)
            return getSubreport().OwnerReport.getPageHeight();
         
        if (_PageHeight == null)
            // default height is 11 inches
            _PageHeight = new RSize(this,"11 in");
         
        return _PageHeight;
    }

    public void setPageHeight(RSize value) throws Exception {
        _PageHeight = value;
    }

    public float getPageHeightPoints() throws Exception {
        return getPageHeight().getPoints();
    }

    public RSize getPageWidth() throws Exception {
        if (this.getSubreport() != null)
            return getSubreport().OwnerReport.getPageWidth();
         
        if (_PageWidth == null)
            // default width is 8.5 inches
            _PageWidth = new RSize(this,"8.5 in");
         
        return _PageWidth;
    }

    public void setPageWidth(RSize value) throws Exception {
        _PageWidth = value;
    }

    public float getPageWidthPoints() throws Exception {
        return getPageWidth().getPoints();
    }

    public RSize getLeftMargin() throws Exception {
        if (getSubreport() != null)
            return getSubreport().getLeft();
         
        if (_LeftMargin == null)
            _LeftMargin = new RSize(this,"0 in");
         
        return _LeftMargin;
    }

    public void setLeftMargin(RSize value) throws Exception {
        _LeftMargin = value;
    }

    public RSize getRightMargin() throws Exception {
        if (getSubreport() != null)
            return getSubreport().OwnerReport.getRightMargin();
         
        if (_RightMargin == null)
            _RightMargin = new RSize(this,"0 in");
         
        return _RightMargin;
    }

    public void setRightMargin(RSize value) throws Exception {
        _RightMargin = value;
    }

    public RSize getTopMargin() throws Exception {
        if (getSubreport() != null)
            return getSubreport().OwnerReport.getTopMargin();
         
        if (_TopMargin == null)
            _TopMargin = new RSize(this,"0 in");
         
        return _TopMargin;
    }

    public void setTopMargin(RSize value) throws Exception {
        _TopMargin = value;
    }

    public float getTopOfPage() throws Exception {
        if (this.getSubreport() != null)
            return getSubreport().OwnerReport.getTopOfPage();
         
        float y = getTopMargin().getPoints();
        if (this._PageHeader != null)
            y += _PageHeader.getHeight().getPoints();
         
        return y;
    }

    public RSize getBottomMargin() throws Exception {
        if (getSubreport() != null)
            return getSubreport().OwnerReport.getBottomMargin();
         
        if (_BottomMargin == null)
            _BottomMargin = new RSize(this,"0 in");
         
        return _BottomMargin;
    }

    public void setBottomMargin(RSize value) throws Exception {
        _BottomMargin = value;
    }

    // this is the y coordinate just above the page footer
    public float getBottomOfPage() throws Exception {
        if (this.getSubreport() != null)
            return getSubreport().OwnerReport.getBottomOfPage();
         
        // calc size of bottom margin + footer
        float y = getBottomMargin().getPoints();
        if (this._PageFooter != null)
            y += _PageFooter.getHeight().getPoints();
         
        // now get the absolute coordinate
        y = getPageHeight().getPoints() - y;
        return y;
    }

    public EmbeddedImages getEmbeddedImages() throws Exception {
        return _EmbeddedImages;
    }

    public void setEmbeddedImages(EmbeddedImages value) throws Exception {
        _EmbeddedImages = value;
    }

    public Expression getLanguage() throws Exception {
        return _Language;
    }

    public void setLanguage(Expression value) throws Exception {
        _Language = value;
    }

    public String evalLanguage(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Language == null)
        {
            CultureInfo ci = CultureInfo.CurrentCulture;
            return ci.Name;
        }
         
        return _Language.evaluateString(rpt,r);
    }

    public CodeModules getCodeModules() throws Exception {
        return _CodeModules;
    }

    public void setCodeModules(CodeModules value) throws Exception {
        _CodeModules = value;
    }

    public Classes getClasses() throws Exception {
        return _Classes;
    }

    public void setClasses(Classes value) throws Exception {
        _Classes = value;
    }

    public String getDataTransform() throws Exception {
        return _DataTransform;
    }

    public void setDataTransform(String value) throws Exception {
        _DataTransform = value;
    }

    public String getDataSchema() throws Exception {
        return _DataSchema;
    }

    public void setDataSchema(String value) throws Exception {
        _DataSchema = value;
    }

    public String getDataElementName() throws Exception {
        return _DataElementName == null ? "Report" : _DataElementName;
    }

    public void setDataElementName(String value) throws Exception {
        _DataElementName = value;
    }

    public DataElementStyleEnum getDataElementStyle() throws Exception {
        return _DataElementStyle;
    }

    public void setDataElementStyle(DataElementStyleEnum value) throws Exception {
        _DataElementStyle = value;
    }

    public IDictionary getLUGlobals() throws Exception {
        return _LUGlobals;
    }

    public IDictionary getLUUser() throws Exception {
        return _LUUser;
    }

    public IDictionary getLUReportItems() throws Exception {
        return _LUReportItems;
    }

    public IDictionary getLUAggrScope() throws Exception {
        return _LUAggrScope;
    }

    public IDictionary getLUReportParameters() throws Exception {
        if (_ReportParameters != null && _ReportParameters.getItems() != null)
            return _ReportParameters.getItems();
        else
            return null; 
    }

    public IDictionary getLUEmbeddedImages() throws Exception {
        return _LUEmbeddedImages;
    }

    public Subreport getSubreport() throws Exception {
        return _Subreport;
    }

    public void setSubreport(Subreport value) throws Exception {
        _Subreport = value;
    }

    public boolean getContainsSubreport() throws Exception {
        return _ContainsSubreport;
    }

    public void setContainsSubreport(boolean value) throws Exception {
        _ContainsSubreport = value;
    }

    public int getErrorMaxSeverity() throws Exception {
        if (this.rl == null)
            return 0;
        else
            return rl.getMaxSeverity(); 
    }

    public IList getErrorItems() throws Exception {
        if (this.rl == null)
            return null;
        else
            return rl.getErrorItems(); 
    }

    public void errorReset() throws Exception {
        if (this.rl == null)
            return ;
         
        rl.reset();
        return ;
    }

}


