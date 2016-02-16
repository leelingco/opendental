//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.CategoryAxis;
import fyiReporting.RDL.CategoryGrouping;
import fyiReporting.RDL.CategoryGroupings;
import fyiReporting.RDL.ChartBar;
import fyiReporting.RDL.ChartBase;
import fyiReporting.RDL.ChartColumn;
import fyiReporting.RDL.ChartData;
import fyiReporting.RDL.ChartElementOutputEnum;
import fyiReporting.RDL.ChartLine;
import fyiReporting.RDL.ChartPalette;
import fyiReporting.RDL.ChartPaletteEnum;
import fyiReporting.RDL.ChartPie;
import fyiReporting.RDL.ChartSeries;
import fyiReporting.RDL.ChartSubType;
import fyiReporting.RDL.ChartSubTypeEnum;
import fyiReporting.RDL.ChartType;
import fyiReporting.RDL.ChartTypeEnum;
import fyiReporting.RDL.DataPoint;
import fyiReporting.RDL.DataRegion;
import fyiReporting.RDL.DataValue;
import fyiReporting.RDL.GroupExpression;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Legend;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.MatrixCellEntry;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.PageImage;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.PlotArea;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.SeriesGrouping;
import fyiReporting.RDL.SeriesGroupings;
import fyiReporting.RDL.ThreeDProperties;
import fyiReporting.RDL.Title;
import fyiReporting.RDL.ValueAxis;
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
* Defines the Chart.  A DataRegion and ReportItem
*/
public class Chart  extends DataRegion 
{
    static final ImageFormat IMAGEFORMAT = ImageFormat.Jpeg;
    ChartTypeEnum _Type = ChartTypeEnum.Column;
    // Generic Type of the chart Default: Column
    ChartSubTypeEnum _Subtype = ChartSubTypeEnum.Plain;
    // Available subtypes (and default subtype) depends on Type
    SeriesGroupings _SeriesGroupings;
    // Set of series groupings for the chart
    CategoryGroupings _CategoryGroupings;
    // Set of category (X) groupings for the chart
    ChartData _ChartData;
    // Defines the data values for the chart
    Legend _Legend;
    // Defines the chart legend
    CategoryAxis _CategoryAxis;
    // Defines the category axis
    ValueAxis _ValueAxis;
    // Defines the value axis
    Title _Title;
    // Defines a title for the chart
    int _PointWidth = new int();
    //Non-zero Percent width for bars and
    //	columns. A value of 100 represents 100%
    //	of the distance between points (i.e. a
    //	value greater than 100 will cause columns
    //	to overlap each other).
    ChartPaletteEnum _Palette = ChartPaletteEnum.Default;
    // Determines the color palette for the chart items.
    ThreeDProperties _ThreeDProperties;
    //Properties for a 3D chart layout.
    PlotArea _PlotArea;
    //Properties for the plot area
    ChartElementOutputEnum _ChartElementOutput = ChartElementOutputEnum.Output;
    // Indicates whether a DataPoints element
    // containing the chart data points should
    // appear in a data rendering.  Default: Output
    Matrix _ChartMatrix;
    // Pseudo matrix to calculate chart data
    public Chart(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p, xNode);
        _Type = ChartTypeEnum.Column;
        _Subtype = ChartSubTypeEnum.Plain;
        _SeriesGroupings = null;
        _CategoryGroupings = null;
        _ChartData = null;
        _Legend = null;
        _CategoryAxis = null;
        _ValueAxis = null;
        _Title = null;
        _PointWidth = 0;
        _Palette = ChartPaletteEnum.Default;
        _ThreeDProperties = null;
        _PlotArea = null;
        _ChartElementOutput = ChartElementOutputEnum.Output;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Type"))
            {
                _Type = ChartType.GetStyle(xNodeLoop.InnerText);
                if (_Type == ChartTypeEnum.Scatter || _Type == ChartTypeEnum.Bubble || _Type == ChartTypeEnum.Stock || _Type == ChartTypeEnum.Unknown)
                {
                    OwnerReport.rl.logError(8,"Chart type '" + xNodeLoop.InnerText + "' is not currently supported.");
                }
                 
            }
            else if (__dummyScrutVar0.equals("Subtype"))
            {
                _Subtype = ChartSubType.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("SeriesGroupings"))
            {
                _SeriesGroupings = new SeriesGroupings(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("CategoryGroupings"))
            {
                _CategoryGroupings = new CategoryGroupings(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("ChartData"))
            {
                _ChartData = new ChartData(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Legend"))
            {
                _Legend = new Legend(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("CategoryAxis"))
            {
                _CategoryAxis = new CategoryAxis(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("ValueAxis"))
            {
                _ValueAxis = new ValueAxis(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Title"))
            {
                _Title = new Title(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("PointWidth"))
            {
                _PointWidth = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("Palette"))
            {
                _Palette = ChartPalette.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("ThreeDProperties"))
            {
                _ThreeDProperties = new ThreeDProperties(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("PlotArea"))
            {
                _PlotArea = new PlotArea(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("ChartElementOutput"))
            {
                _ChartElementOutput = fyiReporting.RDL.ChartElementOutput.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                if (dataRegionElement(xNodeLoop))
                    break;
                 
                // try at DataRegion level
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Chart element '" + xNodeLoop.Name + "' ignored.");
            }              
        }
        dataRegionFinish();
        // Tidy up the DataRegion
        if (_SeriesGroupings == null && _CategoryGroupings == null)
            OwnerReport.rl.logError(8,"Chart requires either the SeriesGroupings element or CategoryGroupings element or both.");
         
    }

    public void finalPass() throws Exception {
        super.finalPass();
        if (_SeriesGroupings != null)
            _SeriesGroupings.finalPass();
         
        if (_CategoryGroupings != null)
            _CategoryGroupings.finalPass();
         
        if (_ChartData != null)
            _ChartData.finalPass();
         
        if (_Legend != null)
            _Legend.finalPass();
         
        if (_CategoryAxis != null)
            _CategoryAxis.finalPass();
         
        if (_ValueAxis != null)
            _ValueAxis.finalPass();
         
        if (_Title != null)
            _Title.finalPass();
         
        if (_ThreeDProperties != null)
            _ThreeDProperties.finalPass();
         
        if (_PlotArea != null)
            _PlotArea.finalPass();
         
        if (this.OwnerReport.rl.getMaxSeverity() < 8)
        {
            // Don't take this step if already have errors
            _ChartMatrix = generateMatrix();
            //   GenerateMatrix() needs no error in defn to date
            _ChartMatrix.finalPass();
        }
         
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        fyiReporting.RDL.Report rpt = ip.report();
        _ChartMatrix.runReset(rpt);
        Rows _Data = getFilteredData(ip.report(),row);
        setMyData(ip.report(),_Data);
        if (!anyRows(ip,_Data))
            return ;
         
        // if no rows, return
        // Build the Chart bitmap, along with data regions
        ChartBase cb = null;
        try
        {
            cb = runChartBuild(rpt,row);
            ip.chart(this,row,cb);
        }
        catch (Exception ex)
        {
            rpt.rl.LogError(8, String.Format("Exception in Chart handling.\n{0}\n{1}", ex.Message, ex.StackTrace));
        }
        finally
        {
            if (cb != null)
                cb.dispose();
             
        }
        return ;
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        fyiReporting.RDL.Report rpt = pgs.getReport();
        if (isHidden(pgs.getReport(),row))
            return ;
         
        _ChartMatrix.runReset(rpt);
        Rows _Data = getFilteredData(rpt,row);
        setMyData(rpt,_Data);
        setPagePositionBegin(pgs);
        if (!anyRowsPage(pgs,_Data))
            return ;
         
        // if no rows return
        //   nothing left to do
        // Build the Chart bitmap, along with data regions
        Page p = pgs.getCurrentPage();
        ChartBase cb = null;
        try
        {
            cb = runChartBuild(rpt,row);
            // Build the chart
            System.Drawing.Image im = cb.image(rpt);
            // Grab the image
            int height = im.Height;
            // save height and width
            int width = im.Width;
            MemoryStream ostrm = new MemoryStream();
            im.Save(ostrm, IMAGEFORMAT);
            // generate a jpeg   TODO: get png to work with pdf
            byte[] ba = ostrm.ToArray();
            ostrm.Close();
            PageImage pi = new PageImage(IMAGEFORMAT, ba, width, height);
            // Create an image
            runPageRegionBegin(pgs);
            setPagePositionAndStyle(rpt,pi,row);
            pi.getSI().BackgroundImage = null;
            // chart already has the background image
            if (pgs.getCurrentPage().getYOffset() + pi.getY() + pi.getH() >= pgs.getBottomOfPage() && !pgs.getCurrentPage().isEmpty())
            {
                // force page break if it doesn't fit on the page
                pgs.nextOrNew();
                pgs.getCurrentPage().setYOffset(OwnerReport.getTopOfPage());
                if (this.getYParents() != null)
                    pi.setY(0);
                 
            }
             
            p = pgs.getCurrentPage();
            p.addObject(pi);
            // Put image onto the current page
            runPageRegionEnd(pgs);
            if (!this.getPageBreakAtEnd() && !isTableOrMatrixCell(rpt))
            {
                float newY = pi.getY() + pi.getH();
                p.setYOffset(p.getYOffset() + newY);
            }
             
            // bump the y location
            setPagePositionEnd(pgs,pi.getY() + pi.getH());
        }
        catch (Exception ex)
        {
            rpt.rl.LogError(8, String.Format("Exception in Chart handling.\n{0}\n{1}", ex.Message, ex.StackTrace));
        }
        finally
        {
            if (cb != null)
                cb.dispose();
             
        }
        return ;
    }

    ChartBase runChartBuild(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        // Get the matrix that defines the data;
        //   some graph types don't require this (XY(scatter), Bubble, Stock
        _ChartMatrix.setMyData(rpt,getMyData(rpt));
        // set the data in the matrix
        int maxColumns = new int();
        int maxRows = new int();
        RefSupport<int> refVar___0 = new RefSupport<int>();
        RefSupport<int> refVar___1 = new RefSupport<int>();
        MatrixCellEntry[][] matrix = _ChartMatrix.runBuild(rpt,refVar___0,refVar___1);
        maxRows = refVar___0.getValue();
        maxColumns = refVar___1.getValue();
        // Build the Chart bitmap, along with data regions
        ChartBase cb = null;
        switch(_Type)
        {
            case Column: 
                cb = new ChartColumn(rpt, row, this, matrix);
                break;
            case Line: 
            case Area: 
                // handled by line
                cb = new ChartLine(rpt, row, this, matrix);
                break;
            case Bar: 
                cb = new ChartBar(rpt, row, this, matrix);
                break;
            case Pie: 
            case Doughnut: 
                // handled by pie
                cb = new ChartPie(rpt, row, this, matrix);
                break;
            case Scatter: 
            case Bubble: 
            case Stock: 
            default: 
                cb = new ChartColumn(rpt, row, this, matrix);
                break;
        
        }
        return cb;
    }

    // We generate a matrix so at runtime the matrix data engine
    //  will create the necessary summary data for the chart
    private Matrix generateMatrix() throws Exception {
        XmlDocument mDoc = new XmlDocument();
        XmlElement m = mDoc.CreateElement("Matrix");
        mDoc.AppendChild(m);
        // Add in DataSetName if provided in Chart
        if (this.getDataSetName() != null)
        {
            XmlElement dsn = mDoc.CreateElement("DataSetName");
            dsn.InnerText = this.getDataSetName();
            m.AppendChild(dsn);
        }
         
        // MatrixColumns -- required but not really used for this case
        XmlElement mcols = mDoc.CreateElement("MatrixColumns");
        m.AppendChild(mcols);
        XmlElement mcol = mDoc.CreateElement("MatrixColumn");
        mcols.AppendChild(mcol);
        XmlElement w = mDoc.CreateElement("Width");
        w.InnerText = "1in";
        mcol.AppendChild(w);
        // ColumnGroupings -- cooresponds to SeriesGroupings
        GenerateMatrixSeries(mDoc, m);
        // RowGroupings -- corresponds to CategoryGroupings
        GenerateMatrixCategories(mDoc, m);
        // MatrixRows -- corresponds to ChartData
        XmlElement mrs = mDoc.CreateElement("MatrixRows");
        // DataPoints
        m.AppendChild(mrs);
        for (Object __dummyForeachVar3 : this.getChartData().getItems())
        {
            ChartSeries cs = (ChartSeries)__dummyForeachVar3;
            XmlElement mr = mDoc.CreateElement("MatrixRow");
            //DataPoint
            mrs.AppendChild(mr);
            XmlElement h = mDoc.CreateElement("Height");
            h.InnerText = "1in";
            mr.AppendChild(h);
            for (Object __dummyForeachVar2 : cs.getDatapoints().getItems())
            {
                DataPoint dp = (DataPoint)__dummyForeachVar2;
                XmlElement mcs = mDoc.CreateElement("MatrixCells");
                //DataValues
                mr.AppendChild(mcs);
                for (Object __dummyForeachVar1 : dp.getDataValues().getItems())
                {
                    DataValue dv = (DataValue)__dummyForeachVar1;
                    XmlElement mc = mDoc.CreateElement("MatrixCell");
                    //DataValue
                    mcs.AppendChild(mc);
                    XmlElement mcris = mDoc.CreateElement("ReportItems");
                    mc.AppendChild(mcris);
                    XmlElement mcri = mDoc.CreateElement("ChartExpression");
                    mcris.AppendChild(mcri);
                    XmlElement dvv = mDoc.CreateElement("Value");
                    dvv.InnerText = dv.getValue().getSource();
                    mcri.AppendChild(dvv);
                    XmlElement dvl = mDoc.CreateElement("DataPoint");
                    dvl.InnerText = this.OwnerReport.createDynamicName(dp);
                    mcri.AppendChild(dvl);
                }
            }
        }
        Matrix mt = new Matrix(this.OwnerReport, this, m);
        return mt;
    }

    void generateMatrixCategories(XmlDocument mDoc, XmlNode m) throws Exception {
        XmlElement rgs = mDoc.CreateElement("RowGroupings");
        m.AppendChild(rgs);
        if (this.getCategoryGroupings() == null)
        {
            XmlElement rg = mDoc.CreateElement("RowGrouping");
            rgs.AppendChild(rg);
            XmlElement width = mDoc.CreateElement("Width");
            width.InnerText = "1in";
            rg.AppendChild(width);
            XmlElement dr = mDoc.CreateElement("DynamicRows");
            rg.AppendChild(dr);
            XmlElement drgrp = mDoc.CreateElement("Grouping");
            dr.AppendChild(drgrp);
            XmlElement drges = mDoc.CreateElement("GroupExpressions");
            drgrp.AppendChild(drges);
            XmlElement drris = mDoc.CreateElement("ReportItems");
            dr.AppendChild(drris);
            XmlElement drge = mDoc.CreateElement("GroupExpression");
            drges.AppendChild(drge);
            drge.InnerText = "";
            XmlElement drri = mDoc.CreateElement("ChartExpression");
            drris.AppendChild(drri);
            XmlElement drv = mDoc.CreateElement("Value");
            drv.InnerText = "";
            drri.AppendChild(drv);
            return ;
        }
         
        for (Object __dummyForeachVar5 : this.getCategoryGroupings().getItems())
        {
            CategoryGrouping catGrp = (CategoryGrouping)__dummyForeachVar5;
            XmlElement rg = mDoc.CreateElement("RowGrouping");
            rgs.AppendChild(rg);
            XmlElement width = mDoc.CreateElement("Width");
            width.InnerText = "1in";
            rg.AppendChild(width);
            XmlElement dr = mDoc.CreateElement("DynamicRows");
            rg.AppendChild(dr);
            XmlElement drgrp = mDoc.CreateElement("Grouping");
            dr.AppendChild(drgrp);
            XmlElement drges = mDoc.CreateElement("GroupExpressions");
            drgrp.AppendChild(drges);
            XmlElement drris = mDoc.CreateElement("ReportItems");
            dr.AppendChild(drris);
            for (Object __dummyForeachVar4 : catGrp.getDynamicCategories().getGrouping().getGroupExpressions().getItems())
            {
                GroupExpression ge = (GroupExpression)__dummyForeachVar4;
                XmlElement drge = mDoc.CreateElement("GroupExpression");
                drges.AppendChild(drge);
                drge.InnerText = ge.getExpression().getSource();
                XmlElement drri = mDoc.CreateElement("ChartExpression");
                drris.AppendChild(drri);
                XmlElement drv = mDoc.CreateElement("Value");
                drv.InnerText = ge.getExpression().getSource();
                drri.AppendChild(drv);
            }
        }
    }

    void generateMatrixSeries(XmlDocument mDoc, XmlNode m) throws Exception {
        XmlElement cgs = mDoc.CreateElement("ColumnGroupings");
        m.AppendChild(cgs);
        if (this.getSeriesGroupings() == null)
        {
            XmlElement cg = mDoc.CreateElement("ColumnGrouping");
            cgs.AppendChild(cg);
            XmlElement h = mDoc.CreateElement("Height");
            h.InnerText = "1in";
            cg.AppendChild(h);
            XmlElement dc = mDoc.CreateElement("DynamicColumns");
            cg.AppendChild(dc);
            XmlElement dcgrp = mDoc.CreateElement("Grouping");
            dc.AppendChild(dcgrp);
            XmlElement dcges = mDoc.CreateElement("GroupExpressions");
            dcgrp.AppendChild(dcges);
            XmlElement dcris = mDoc.CreateElement("ReportItems");
            dc.AppendChild(dcris);
            XmlElement dcge = mDoc.CreateElement("GroupExpression");
            dcges.AppendChild(dcge);
            dcge.InnerText = "";
            XmlElement dcri = mDoc.CreateElement("ChartExpression");
            dcris.AppendChild(dcri);
            XmlElement dcv = mDoc.CreateElement("Value");
            dcv.InnerText = "";
            dcri.AppendChild(dcv);
            return ;
        }
         
        for (Object __dummyForeachVar7 : this.getSeriesGroupings().getItems())
        {
            SeriesGrouping serGrp = (SeriesGrouping)__dummyForeachVar7;
            XmlElement cg = mDoc.CreateElement("ColumnGrouping");
            cgs.AppendChild(cg);
            XmlElement h = mDoc.CreateElement("Height");
            h.InnerText = "1in";
            cg.AppendChild(h);
            XmlElement dc = mDoc.CreateElement("DynamicColumns");
            cg.AppendChild(dc);
            XmlElement dcgrp = mDoc.CreateElement("Grouping");
            dc.AppendChild(dcgrp);
            XmlElement dcges = mDoc.CreateElement("GroupExpressions");
            dcgrp.AppendChild(dcges);
            XmlElement dcris = mDoc.CreateElement("ReportItems");
            dc.AppendChild(dcris);
            for (Object __dummyForeachVar6 : serGrp.getDynamicSeries().getGrouping().getGroupExpressions().getItems())
            {
                GroupExpression ge = (GroupExpression)__dummyForeachVar6;
                XmlElement dcge = mDoc.CreateElement("GroupExpression");
                dcges.AppendChild(dcge);
                dcge.InnerText = ge.getExpression().getSource();
                XmlElement dcri = mDoc.CreateElement("ChartExpression");
                dcris.AppendChild(dcri);
                XmlElement dcv = mDoc.CreateElement("Value");
                dcv.InnerText = ge.getExpression().getSource();
                dcri.AppendChild(dcv);
            }
        }
    }

    public ChartTypeEnum getType() throws Exception {
        return _Type;
    }

    public void setType(ChartTypeEnum value) throws Exception {
        _Type = value;
    }

    public ChartSubTypeEnum getSubtype() throws Exception {
        return _Subtype;
    }

    public void setSubtype(ChartSubTypeEnum value) throws Exception {
        _Subtype = value;
    }

    public SeriesGroupings getSeriesGroupings() throws Exception {
        return _SeriesGroupings;
    }

    public void setSeriesGroupings(SeriesGroupings value) throws Exception {
        _SeriesGroupings = value;
    }

    public CategoryGroupings getCategoryGroupings() throws Exception {
        return _CategoryGroupings;
    }

    public void setCategoryGroupings(CategoryGroupings value) throws Exception {
        _CategoryGroupings = value;
    }

    public ChartData getChartData() throws Exception {
        return _ChartData;
    }

    public void setChartData(ChartData value) throws Exception {
        _ChartData = value;
    }

    public Legend getLegend() throws Exception {
        return _Legend;
    }

    public void setLegend(Legend value) throws Exception {
        _Legend = value;
    }

    public CategoryAxis getCategoryAxis() throws Exception {
        return _CategoryAxis;
    }

    public void setCategoryAxis(CategoryAxis value) throws Exception {
        _CategoryAxis = value;
    }

    public ValueAxis getValueAxis() throws Exception {
        return _ValueAxis;
    }

    public void setValueAxis(ValueAxis value) throws Exception {
        _ValueAxis = value;
    }

    public Title getTitle() throws Exception {
        return _Title;
    }

    public void setTitle(Title value) throws Exception {
        _Title = value;
    }

    public int getPointWidth() throws Exception {
        return _PointWidth;
    }

    public void setPointWidth(int value) throws Exception {
        _PointWidth = value;
    }

    public ChartPaletteEnum getPalette() throws Exception {
        return _Palette;
    }

    public void setPalette(ChartPaletteEnum value) throws Exception {
        _Palette = value;
    }

    public ThreeDProperties getThreeDProperties() throws Exception {
        return _ThreeDProperties;
    }

    public void setThreeDProperties(ThreeDProperties value) throws Exception {
        _ThreeDProperties = value;
    }

    public PlotArea getPlotArea() throws Exception {
        return _PlotArea;
    }

    public void setPlotArea(PlotArea value) throws Exception {
        _PlotArea = value;
    }

    public ChartElementOutputEnum getChartElementOutput() throws Exception {
        return _ChartElementOutput;
    }

    public void setChartElementOutput(ChartElementOutputEnum value) throws Exception {
        _ChartElementOutput = value;
    }

    public Matrix getChartMatrix() throws Exception {
        return _ChartMatrix;
    }

    // Runtime data; either original query if no groups
    // or sorting or a copied version that is grouped/sorted
    private Rows getMyData(fyiReporting.RDL.Report rpt) throws Exception {
        return rpt.getCache().get(this,"data") instanceof Rows ? (Rows)rpt.getCache().get(this,"data") : (Rows)null;
    }

    private void setMyData(fyiReporting.RDL.Report rpt, Rows data) throws Exception {
        if (data == null)
            rpt.getCache().remove(this,"data");
        else
            rpt.getCache().addReplace(this,"data",data); 
    }

}


