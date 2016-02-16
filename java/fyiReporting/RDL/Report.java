//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataSets;
import fyiReporting.RDL.DataSources;
import fyiReporting.RDL.DataSourcesDefn;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.IStreamGen;
import fyiReporting.RDL.MemoryStreamGen;
import fyiReporting.RDL.MhtBuilder;
import fyiReporting.RDL.NeedPassword;
import fyiReporting.RDL.OneFileStreamGen;
import fyiReporting.RDL.OutputPresentationType;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.RCache;
import fyiReporting.RDL.RenderHtml;
import fyiReporting.RDL.RenderPdf;
import fyiReporting.RDL.RenderXml;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLog;
import fyiReporting.RDL.ReportParameter;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.UserReportParameter;
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
* Main Report definition; this is the top of the tree that contains the complete
* definition of a instance of a report.
*/
public class Report   
{
    ReportDefn _Report;
    DataSources _DataSources;
    DataSets _DataSets;
    int _RuntimeName = 0;
    // used for the generation of unique runtime names
    IDictionary _LURuntimeName = new IDictionary();
    // Runtime names
    ICollection _UserParameters = new ICollection();
    // User parameters
    public ReportLog rl;
    // report log
    RCache _Cache;
    // Some report runtime variables
    private String _Folder = new String();
    // folder name
    private String _ReportName = new String();
    // report name
    private String _CSS = new String();
    // after rendering ASPHTML; this is separate
    private String _JavaScript = new String();
    // after rendering ASPHTML; this is separate
    private Object _CodeInstance = new Object();
    // Instance of the class generated for the Code element
    private Page _CurrentPage;
    // needed for page header/footer references
    private String _UserID = new String();
    // UserID of client executing the report
    private String _ClientLanguage = new String();
    // Language code of the client executing the report.
    private DataSourcesDefn _ParentConnections;
    // When running subreport with merge transactions this is parent report connections
    public int PageNumber = 1;
    // current page number
    public int TotalPages = 1;
    // total number of pages in report
    public DateTime ExecutionTime = new DateTime();
    // start time of report execution
    /**
    * Construct a runtime Report object using the compiled report definition.
    * 
    *  @param r
    */
    public Report(ReportDefn r) throws Exception {
        _Report = r;
        _Cache = new RCache();
        rl = new ReportLog(r.rl);
        _ReportName = r.getName();
        _UserParameters = null;
        _LURuntimeName = new ListDictionary();
        // shouldn't be very many of these
        if (r.getCode() != null)
            _CodeInstance = r.getCode().load(this);
         
        if (r.getClasses() != null)
            r.getClasses().load(this);
         
    }

    public Page getCurrentPage() throws Exception {
        return _CurrentPage;
    }

    public void setCurrentPage(Page value) throws Exception {
        _CurrentPage = value;
    }

    public Rows getPageExpressionRows(String exprname) throws Exception {
        if (_CurrentPage == null)
            return null;
         
        return _CurrentPage.getPageExpressionRows(exprname);
    }

    /**
    * Read all the DataSets in the report
    * 
    *  @param parms
    */
    public void runGetData(IDictionary parms) throws Exception {
        ExecutionTime = DateTime.Now;
        _Report.runGetData(this,parms);
        return ;
    }

    /**
    * Renders the report using the requested presentation type.
    * 
    *  @param sg IStreamGen for generating result stream
    *  @param type Presentation type: HTML, XML, PDF, or ASP compatible HTML
    */
    public void runRender(IStreamGen sg, OutputPresentationType type) throws Exception {
        runRender(sg,type,"");
    }

    /**
    * Renders the report using the requested presentation type.
    * 
    *  @param sg IStreamGen for generating result stream
    *  @param type Presentation type: HTML, XML, PDF, MHT, or ASP compatible HTML
    *  @param prefix For HTML puts prefix allowing unique name generation
    */
    public void runRender(IStreamGen sg, OutputPresentationType type, String prefix) throws Exception {
        if (sg == null)
            throw new ArgumentException("IStreamGen argument cannot be null.", "sg");
         
        RenderHtml rh = null;
        PageNumber = 1;
        // reset page numbers
        TotalPages = 1;
        IPresent ip;
        MemoryStreamGen msg = null;
        switch(type)
        {
            case PDF: 
                ip = new RenderPdf(this,sg);
                _Report.run(ip);
                break;
            case XML: 
                if (_Report.getDataTransform() != null && _Report.getDataTransform().Length > 0)
                {
                    msg = new MemoryStreamGen();
                    ip = new RenderXml(this,msg);
                    _Report.run(ip);
                    runRenderXmlTransform(sg,msg);
                }
                else
                {
                    ip = new RenderXml(this,sg);
                    _Report.run(ip);
                } 
                break;
            case MHTML: 
                this.runRenderMht(sg);
                break;
            case ASPHTML: 
            case HTML: 
            default: 
                ip = rh = new RenderHtml(this,sg);
                rh.setAsp((type == OutputPresentationType.ASPHTML));
                rh.setPrefix(prefix);
                _Report.run(ip);
                // Retain the CSS and JavaScript
                if (rh != null)
                {
                    _CSS = rh.getCSS();
                    _JavaScript = rh.getJavaScript();
                }
                 
                break;
        
        }
        sg.closeMainStream();
        _Cache = new RCache();
        return ;
    }

    private void runRenderMht(IStreamGen sg) throws Exception {
        OneFileStreamGen temp = null;
        FileStream fs = null;
        try
        {
            String tempHtmlReportFileName = Path.ChangeExtension(Path.GetTempFileName(), "htm");
            temp = new OneFileStreamGen(tempHtmlReportFileName,true);
            runRender(temp,OutputPresentationType.HTML);
            temp.closeMainStream();
            // Create the mht file (into a temporary file position)
            MhtBuilder mhtConverter = new MhtBuilder();
            String fileName = Path.ChangeExtension(Path.GetTempFileName(), "mht");
            mhtConverter.savePageArchive(fileName,"file://" + tempHtmlReportFileName);
            for (Object __dummyForeachVar0 : temp.getFileList())
            {
                // clean up the temporary files
                String tempFileName = (String)__dummyForeachVar0;
                try
                {
                    File.Delete(tempFileName);
                }
                catch (Exception __dummyCatchVar0)
                {
                }
            
            }
            // Copy the mht file to the requested stream
            Stream os = sg.getStream();
            fs = File.OpenRead(fileName);
            byte[] ba = new byte[4096];
            int rb = 0;
            while ((rb = fs.Read(ba, 0, ba.Length)) > 0)
            {
                os.Write(ba, 0, rb);
            }
        }
        catch (Exception ex)
        {
            rl.logError(8,"Error converting HTML to MHTML " + ex.Message + Environment.NewLine + ex.StackTrace);
        }
        finally
        {
            if (temp != null)
                temp.closeMainStream();
             
            if (fs != null)
                fs.Close();
             
            _Cache = new RCache();
        }
    }

    /**
    * RunRenderPdf will render a Pdf given the page structure
    * 
    *  @param sg 
    *  @param pgs
    */
    public void runRenderPdf(IStreamGen sg, Pages pgs) throws Exception {
        PageNumber = 1;
        // reset page numbers
        TotalPages = 1;
        IPresent ip = new RenderPdf(this,sg);
        try
        {
            ip.start();
            ip.runPages(pgs);
            ip.end();
        }
        finally
        {
            pgs.cleanUp();
            // always want to make sure we cleanup to reduce resource usage
            _Cache = new RCache();
        }
        return ;
    }

    private void runRenderXmlTransform(IStreamGen sg, MemoryStreamGen msg) throws Exception {
        try
        {
            String file = new String();
            if (_Report.getDataTransform()[0] != Path.DirectorySeparatorChar)
                file = this.getFolder() + Path.DirectorySeparatorChar + _Report.getDataTransform();
            else
                file = this.getFolder() + _Report.getDataTransform(); 
            XmlUtil.xslTrans(file,msg.getText(),sg.getStream());
        }
        catch (Exception ex)
        {
            rl.logError(8,"Error processing DataTransform " + ex.Message + "\r\n" + ex.StackTrace);
        }
        finally
        {
            msg.dispose();
        }
        return ;
    }

    /**
    * Build the Pages for this report.
    * 
    *  @return
    */
    public Pages buildPages() throws Exception {
        PageNumber = 1;
        // reset page numbers
        TotalPages = 1;
        Pages pgs = new Pages(this);
        pgs.setPageHeight(_Report.getPageHeight().getPoints());
        pgs.setPageWidth(_Report.getPageWidth().getPoints());
        try
        {
            Page p = new Page(1);
            // kick it off with a new page
            pgs.addPage(p);
            // Create all the pages
            _Report.getBody().runPage(pgs);
            if (pgs.getLastPage().isEmpty() && pgs.getPageCount() > 1)
                // get rid of extraneous pages which
                pgs.removeLastPage();
             
            //   can be caused by region page break at end
            // Now create the headers and footers for all the pages (as needed)
            if (_Report.getPageHeader() != null)
                _Report.getPageHeader().runPage(pgs);
             
            if (_Report.getPageFooter() != null)
                _Report.getPageFooter().runPage(pgs);
             
            for (Object __dummyForeachVar1 : pgs)
            {
                // clear out any runtime clutter
                Page pg = (Page)__dummyForeachVar1;
                pg.resetPageExpressions();
            }
            pgs.sortPageItems();
        }
        catch (Exception e)
        {
            // Handle ZIndex ordering of pages
            rl.logError(8,"Exception running report\r\n" + e.Message + "\r\n" + e.StackTrace);
        }
        finally
        {
            pgs.cleanUp();
            // always want to make sure we clean this up since
            _Cache = new RCache();
        }
        return pgs;
    }

    public NeedPassword getGetDataSourceReferencePassword() throws Exception {
        return _Report.GetDataSourceReferencePassword;
    }

    public void setGetDataSourceReferencePassword(NeedPassword value) throws Exception {
        _Report.GetDataSourceReferencePassword = value;
    }

    public ReportDefn getReportDefinition() throws Exception {
        return this._Report;
    }

    public void setReportDefinition(ReportDefn r) throws Exception {
        _Report = r;
    }

    public String getDescription() throws Exception {
        return _Report.getDescription();
    }

    public String getAuthor() throws Exception {
        return _Report.getAuthor();
    }

    public String getCSS() throws Exception {
        return _CSS;
    }

    public String getJavaScript() throws Exception {
        return _JavaScript;
    }

    public Object getCodeInstance() throws Exception {
        return this._CodeInstance;
    }

    public String createRuntimeName(Object ro) throws Exception {
        _RuntimeName++;
        // increment the name generator
        String name = "o" + _RuntimeName.ToString();
        _LURuntimeName.Add(name, ro);
        return name;
    }

    public DataSources getDataSources() throws Exception {
        if (_Report.getDataSourcesDefn() == null)
            return null;
         
        if (_DataSources == null)
            _DataSources = new DataSources(this,_Report.getDataSourcesDefn());
         
        return _DataSources;
    }

    public DataSets getDataSets() throws Exception {
        if (_Report.getDataSetsDefn() == null)
            return null;
         
        if (_DataSets == null)
            _DataSets = new DataSets(this,_Report.getDataSetsDefn());
         
        return _DataSets;
    }

    /**
    * User provided parameters to the report.  IEnumerable is a list of UserReportParameter.
    */
    public ICollection getUserReportParameters() throws Exception {
        if (_UserParameters != null)
            return _UserParameters;
         
        // only create this once
        //  since it can be expensive to build
        if (getReportDefinition().getReportParameters() == null || getReportDefinition().getReportParameters().getCount() <= 0)
        {
            List<UserReportParameter> parms = new List<UserReportParameter>(1);
            _UserParameters = parms;
        }
        else
        {
            List<UserReportParameter> parms = new List<UserReportParameter>(getReportDefinition().getReportParameters().getCount());
            for (Object __dummyForeachVar2 : getReportDefinition().getReportParameters())
            {
                ReportParameter p = (ReportParameter)__dummyForeachVar2;
                UserReportParameter urp = new UserReportParameter(this,p);
                parms.Add(urp);
            }
            parms.TrimExcess();
            _UserParameters = parms;
        } 
        return _UserParameters;
    }

    /**
    * Get/Set the folder containing the report.
    */
    public String getFolder() throws Exception {
        return _Folder == null ? _Report.getParseFolder() : _Folder;
    }

    public void setFolder(String value) throws Exception {
        _Folder = value;
    }

    /**
    * Get/Set the report name.  Usually this is the file name of the report sans extension.
    */
    public String getName() throws Exception {
        return _ReportName;
    }

    public void setName(String value) throws Exception {
        _ReportName = value;
    }

    /**
    * Returns the height of the page in points.
    */
    public float getPageHeightPoints() throws Exception {
        return _Report.getPageHeight().getPoints();
    }

    /**
    * Returns the width of the page in points.
    */
    public float getPageWidthPoints() throws Exception {
        return _Report.getPageWidthPoints();
    }

    /**
    * Returns the maximum severity of any error.  4 or less indicating report continues running.
    */
    public int getErrorMaxSeverity() throws Exception {
        if (this.rl == null)
            return 0;
        else
            return rl.getMaxSeverity(); 
    }

    /**
    * List of errors encountered so far.
    */
    public IList getErrorItems() throws Exception {
        if (this.rl == null)
            return null;
        else
            return rl.getErrorItems(); 
    }

    /**
    * Clear all errors generated up to now.
    */
    public void errorReset() throws Exception {
        if (this.rl == null)
            return ;
         
        rl.reset();
        return ;
    }

    /**
    * Get/Set the UserID, that is the running user.
    */
    public String getUserID() throws Exception {
        return _UserID == null ? Environment.UserName : _UserID;
    }

    public void setUserID(String value) throws Exception {
        _UserID = value;
    }

    /**
    * Get/Set the three letter ISO language of the client of the report.
    */
    public String getClientLanguage() throws Exception {
        return _ClientLanguage == null ? CultureInfo.CurrentCulture.ThreeLetterISOLanguageName : _ClientLanguage;
    }

    public void setClientLanguage(String value) throws Exception {
        _ClientLanguage = value;
    }

    public DataSourcesDefn getParentConnections() throws Exception {
        return _ParentConnections;
    }

    public void setParentConnections(DataSourcesDefn value) throws Exception {
        _ParentConnections = value;
    }

    public RCache getCache() throws Exception {
        return _Cache;
    }

}


