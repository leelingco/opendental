//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataRegion;
import fyiReporting.RDL.DataSetDefn;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.Filters;
import fyiReporting.RDL.GroupEntry;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.List;
import fyiReporting.RDL.MatrixCell;
import fyiReporting.RDL.MatrixCellEntry;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.PageText;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.TableGroup;
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
* DataRegion base class definition and processing.
* Warning if you inherit from DataRegion look at Expression.cs first.
*/
public class DataRegion  extends ReportItem 
{
    boolean _KeepTogether = new boolean();
    // Indicates the entire data region (all
    // repeated sections) should be kept
    // together on one page if possible.
    Expression _NoRows;
    // (string) Message to display in the DataRegion
    // (instead of the region layout) when
    // no rows of data are available.
    // Note: Style information on the data region applies to this text
    String _DataSetName = new String();
    // Indicates which data set to use for this data region.
    //Mandatory for top level DataRegions
    //(not contained within another
    //DataRegion) if there is not exactly
    //one data set in the report. If there is
    //exactly one data set in the report, the
    //data region uses that data set. (Note:
    //If there are zero data sets in the
    //report, data regions can not be used,
    //as there is no valid DataSetName to
    //use) Ignored for DataRegions that are
    //not top level.
    DataSetDefn _DataSetDefn;
    //  resolved data set name;
    boolean _PageBreakAtStart = new boolean();
    // Indicates the report should page break
    //  at the start of the data region.
    boolean _PageBreakAtEnd = new boolean();
    // Indicates the report should page break
    // at the end of the data region.
    Filters _Filters;
    // Filters to apply to each row of data in the data region.
    DataRegion _ParentDataRegion;
    // when DataRegions are nested; the nested regions have the parent set
    public DataRegion(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p, xNode);
        _KeepTogether = false;
        _NoRows = null;
        _DataSetName = null;
        _DataSetDefn = null;
        _PageBreakAtStart = false;
        _PageBreakAtEnd = false;
        _Filters = null;
    }

    public boolean dataRegionElement(XmlNode xNodeLoop) throws Exception {
        Name __dummyScrutVar0 = xNodeLoop.Name;
        if (__dummyScrutVar0.equals("KeepTogether"))
        {
            _KeepTogether = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
        }
        else if (__dummyScrutVar0.equals("NoRows"))
        {
            _NoRows = new Expression(OwnerReport,this,xNodeLoop,ExpressionType.String);
        }
        else if (__dummyScrutVar0.equals("DataSetName"))
        {
            _DataSetName = xNodeLoop.InnerText;
        }
        else if (__dummyScrutVar0.equals("PageBreakAtStart"))
        {
            _PageBreakAtStart = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
        }
        else if (__dummyScrutVar0.equals("PageBreakAtEnd"))
        {
            _PageBreakAtEnd = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
        }
        else if (__dummyScrutVar0.equals("Filters"))
        {
            _Filters = new Filters(OwnerReport,this,xNodeLoop);
        }
        else
        {
            // Will get many that are handled by the specific
            //  type of data region: ie  list,chart,matrix,table
            if (reportItemElement(xNodeLoop))
                break;
             
            return false;
        }      
        return true;
    }

    // try at ReportItem level
    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        super.finalPass();
        // DataRegions aren't allowed in PageHeader or PageFooter;
        if (this.inPageHeaderOrFooter())
            OwnerReport.rl.LogError(8, String.Format("The DataRegion '{0}' is not allowed in a PageHeader or PageFooter", this.getName() == null ? "unknown" : getName().getNm()));
         
        resolveNestedDataRegions();
        if (_ParentDataRegion != null)
        {
            // when nested we use the dataset of the parent
            _DataSetDefn = _ParentDataRegion.getDataSetDefn();
        }
        else if (_DataSetName != null)
        {
            if (OwnerReport.getDataSetsDefn() != null)
                _DataSetDefn = (DataSetDefn)OwnerReport.getDataSetsDefn().getItems()[_DataSetName];
             
            if (_DataSetDefn == null)
            {
                OwnerReport.rl.LogError(8, String.Format("DataSetName '{0}' not specified in DataSets list.", _DataSetName));
            }
             
        }
        else
        {
            // No name but maybe we can default to a single Dataset
            if (_DataSetDefn == null && OwnerReport.getDataSetsDefn() != null && OwnerReport.getDataSetsDefn().getItems().Count == 1)
            {
                for (Object __dummyForeachVar0 : OwnerReport.getDataSetsDefn().getItems().Values)
                {
                    DataSetDefn d = (DataSetDefn)__dummyForeachVar0;
                    _DataSetDefn = d;
                    break;
                }
            }
             
            // since there is only 1 this will obtain it
            if (_DataSetDefn == null)
                OwnerReport.rl.LogError(8, String.Format("{0} must specify a DataSetName.", this.getName() == null ? "DataRegions" : this.getName().getNm()));
             
        }  
        if (_NoRows != null)
            _NoRows.finalPass();
         
        if (_Filters != null)
            _Filters.finalPass();
         
        return ;
    }

    void resolveNestedDataRegions() throws Exception {
        ReportLink rl = this.Parent;
        while (rl != null)
        {
            if (rl instanceof DataRegion)
            {
                this._ParentDataRegion = rl instanceof DataRegion ? (DataRegion)rl : (DataRegion)null;
                break;
            }
             
            rl = rl.Parent;
        }
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        super.run(ip,row);
    }

    public void runPageRegionBegin(Pages pgs) throws Exception {
        if (this.getTC() == null && this.getPageBreakAtStart() && !pgs.getCurrentPage().isEmpty())
        {
            // force page break at beginning of dataregion
            pgs.nextOrNew();
            pgs.getCurrentPage().setYOffset(OwnerReport.getTopOfPage());
        }
         
    }

    public void runPageRegionEnd(Pages pgs) throws Exception {
        if (this.getTC() == null && this.getPageBreakAtEnd() && !pgs.getCurrentPage().isEmpty())
        {
            // force page break at beginning of dataregion
            pgs.nextOrNew();
            pgs.getCurrentPage().setYOffset(OwnerReport.getTopOfPage());
        }
         
    }

    public boolean anyRows(IPresent ip, Rows data) throws Exception {
        if (data == null || data.getData() == null || data.getData().Count <= 0)
        {
            String msg = new String();
            if (this.getNoRows() != null)
                msg = this.getNoRows().evaluateString(ip.report(),null);
            else
                msg = null; 
            ip.dataRegionNoRows(this,msg);
            return false;
        }
         
        return true;
    }

    public boolean anyRowsPage(Pages pgs, Rows data) throws Exception {
        if (data != null && data.getData() != null && data.getData().Count > 0)
            return true;
         
        String msg = new String();
        if (this.getNoRows() != null)
            msg = this.getNoRows().evaluateString(pgs.getReport(),null);
        else
            msg = null; 
        if (msg == null)
            return false;
         
        // OK we have a message we need to put out
        runPageRegionBegin(pgs);
        // still perform page break if needed
        PageText pt = new PageText(msg);
        setPagePositionAndStyle(pgs.getReport(),pt,null);
        if (pt.getSI().BackgroundImage != null)
            pt.getSI().BackgroundImage.setH(pt.getH());
         
        //   and in the background image
        pgs.getCurrentPage().addObject(pt);
        runPageRegionEnd(pgs);
        return false;
    }

    // perform end page break if needed
    public Rows getFilteredData(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        try
        {
            Rows data;
            if (this._Filters == null)
            {
                if (this._ParentDataRegion == null)
                {
                    data = getDataSetDefn().getQuery().getMyData(rpt);
                    return data == null ? null : new Rows(rpt,data);
                }
                else
                    return getNestedData(rpt,row); 
            }
             
            // We need to copy in case DataSet is shared by multiple DataRegions
            if (this._ParentDataRegion == null)
            {
                data = getDataSetDefn().getQuery().getMyData(rpt);
                if (data != null)
                    data = new Rows(rpt,data);
                 
            }
            else
                data = getNestedData(rpt,row); 
            if (data == null)
                return null;
             
            List<Row> ar = new List<Row>();
            for (Object __dummyForeachVar1 : data.getData())
            {
                Row r = (Row)__dummyForeachVar1;
                if (_Filters.apply(rpt,r))
                    ar.Add(r);
                 
            }
            ar.TrimExcess();
            data.setData(ar);
            _Filters.applyFinalFilters(rpt,data,true);
            // Adjust the rowcount
            int rCount = 0;
            for (Object __dummyForeachVar2 : ar)
            {
                Row r = (Row)__dummyForeachVar2;
                r.setRowNumber(rCount++);
            }
            return data;
        }
        catch (Exception e)
        {
            this.OwnerReport.rl.LogError(8, e.Message);
            return null;
        }
    
    }

    Rows getNestedData(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return null;
         
        ReportLink rl = this.Parent;
        while (rl != null)
        {
            if (rl instanceof TableGroup || rl instanceof List || rl instanceof MatrixCell)
                break;
             
            rl = rl.Parent;
        }
        if (rl == null)
            return null;
         
        // should have been caught as an error
        Grouping g = null;
        if (rl instanceof TableGroup)
        {
            TableGroup tg = rl instanceof TableGroup ? (TableGroup)rl : (TableGroup)null;
            g = tg.getGrouping();
        }
        else if (rl instanceof List)
        {
            List l = rl instanceof List ? (List)rl : (List)null;
            g = l.getGrouping();
        }
        else if (rl instanceof MatrixCell)
        {
            MatrixCellEntry mce = this.getMC(rpt);
            return new Rows(rpt,mce.getData());
        }
           
        if (g == null)
            return null;
         
        GroupEntry ge = row.getR().getCurrentGroups()[g.getIndex(rpt)];
        return new Rows(rpt,row.getR(),ge.getStartRow(),ge.getEndRow(),null);
    }

    public void dataRegionFinish() throws Exception {
        // All dataregion names need to be saved!
        if (this.getName() != null)
        {
            try
            {
                OwnerReport.getLUAggrScope().Add(this.getName().getNm(), this);
            }
            catch (Exception __dummyCatchVar0)
            {
                // add to referenceable regions
                // wish duplicate had its own exception
                OwnerReport.rl.logError(8,"Duplicate name '" + this.getName().getNm() + "'.");
            }
        
        }
         
        return ;
    }

    public boolean getKeepTogether() throws Exception {
        return _KeepTogether;
    }

    public void setKeepTogether(boolean value) throws Exception {
        _KeepTogether = value;
    }

    public Expression getNoRows() throws Exception {
        return _NoRows;
    }

    public void setNoRows(Expression value) throws Exception {
        _NoRows = value;
    }

    public String getDataSetName() throws Exception {
        return _DataSetName;
    }

    public void setDataSetName(String value) throws Exception {
        _DataSetName = value;
    }

    public DataSetDefn getDataSetDefn() throws Exception {
        return _DataSetDefn;
    }

    public void setDataSetDefn(DataSetDefn value) throws Exception {
        _DataSetDefn = value;
    }

    public boolean getPageBreakAtStart() throws Exception {
        return _PageBreakAtStart;
    }

    public void setPageBreakAtStart(boolean value) throws Exception {
        _PageBreakAtStart = value;
    }

    public boolean getPageBreakAtEnd() throws Exception {
        return _PageBreakAtEnd;
    }

    public void setPageBreakAtEnd(boolean value) throws Exception {
        _PageBreakAtEnd = value;
    }

    public Filters getFilters() throws Exception {
        return _Filters;
    }

    public void setFilters(Filters value) throws Exception {
        _Filters = value;
    }

}


