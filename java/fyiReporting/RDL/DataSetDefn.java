//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.Fields;
import fyiReporting.RDL.Filters;
import fyiReporting.RDL.Name;
import fyiReporting.RDL.Query;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Textbox;
import fyiReporting.RDL.TrueFalseAuto;
import fyiReporting.RDL.TrueFalseAutoEnum;

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
* Information about a set of data that will be retrieved when report data is requested.
*/
public class DataSetDefn  extends ReportLink 
{
    Name _Name;
    // Name of the data set
    // Cannot be the same name as any data region or grouping
    Fields _Fields;
    // The fields in the data set
    Query _Query;
    // Information about the data source, including
    //  connection information, query, etc. required to
    //  get the data from the data source.
    String _XmlRowData = new String();
    // User specified data; instead of invoking query we use inline XML data
    //   This is particularlly useful for testing and reporting bugs when
    //   you don't have access to the datasource.
    String _XmlRowFile = new String();
    //   - File should be loaded for user data; if not found use XmlRowData
    TrueFalseAutoEnum _CaseSensitivity = TrueFalseAutoEnum.True;
    // Indicates if the data is case sensitive; true/false/auto
    // if auto; should query data provider; Default false if data provider doesn't support.
    String _Collation = new String();
    // The locale to use for the collation sequence for sorting data.
    //  See Microsoft SQL Server collation codes (http://msdn.microsoft.com/library/enus/tsqlref/ts_ca-co_2e95.asp).
    // If no Collation is specified, the application
    // should attempt to derive the collation setting by
    // querying the data provider.
    // Defaults to the application’s locale settings if
    // the data provider does not support that method
    // or returns an unsupported or invalid value
    TrueFalseAutoEnum _AccentSensitivity = TrueFalseAutoEnum.True;
    // Indicates whether the data is accent sensitive
    // True | False | Auto (Default)
    // If Auto is specified, the application should
    // attempt to derive the accent sensitivity setting
    // by querying the data provider. Defaults to False
    // if the data provider does not support that method.
    TrueFalseAutoEnum _KanatypeSensitivity = TrueFalseAutoEnum.True;
    // Indicates if the data is kanatype sensitive
    // True | False | Auto (Default)
    // If Auto is specified, the Application should
    // attempt to derive the kanatype sensitivity
    // setting by querying the data provider. Defaults
    // to False if the data provider does not support
    // that method.
    TrueFalseAutoEnum _WidthSensitivity = TrueFalseAutoEnum.True;
    // Indicates if the data is width sensitive
    // True | False | Auto (Default)
    // If Auto is specified, the Application should
    // attempt to derive the width sensitivity setting by
    // querying the data provider. Defaults to False if
    // the data provider does not support that method.
    Filters _Filters;
    // Filters to apply to each row of data in the data set
    List<Textbox> _HideDuplicates = new List<Textbox>();
    // holds any textboxes that use this as a hideduplicate scope
    public DataSetDefn(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Name = null;
        _Fields = null;
        _Query = null;
        _CaseSensitivity = TrueFalseAutoEnum.True;
        _Collation = null;
        _AccentSensitivity = TrueFalseAutoEnum.False;
        _KanatypeSensitivity = TrueFalseAutoEnum.False;
        _WidthSensitivity = TrueFalseAutoEnum.False;
        _Filters = null;
        _HideDuplicates = null;
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
        for (Object __dummyForeachVar2 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar2;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar1 = xNodeLoop.Name;
            if (__dummyScrutVar1.equals("Fields"))
            {
                _Fields = new Fields(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("Query"))
            {
                _Query = new Query(r,this,xNodeLoop);
            }
            else // Extension !!!!!!!!!!!!!!!!!!!!!!!
            if (__dummyScrutVar1.equals("Rows") || __dummyScrutVar1.equals("fyi:Rows"))
            {
                _XmlRowData = "<?xml version='1.0' encoding='UTF-8'?><Rows>" + xNodeLoop.InnerXml + "</Rows>";
                for (Object __dummyForeachVar1 : xNodeLoop.Attributes)
                {
                    XmlAttribute xA = (XmlAttribute)__dummyForeachVar1;
                    if (StringSupport.equals(xA.Name, "File"))
                        _XmlRowFile = xA.Value;
                     
                }
            }
            else if (__dummyScrutVar1.equals("CaseSensitivity"))
            {
                _CaseSensitivity = TrueFalseAuto.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar1.equals("Collation"))
            {
                _Collation = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar1.equals("AccentSensitivity"))
            {
                _AccentSensitivity = TrueFalseAuto.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar1.equals("KanatypeSensitivity"))
            {
                _KanatypeSensitivity = TrueFalseAuto.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar1.equals("WidthSensitivity"))
            {
                _WidthSensitivity = TrueFalseAuto.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar1.equals("Filters"))
            {
                _Filters = new Filters(r,this,xNodeLoop);
            }
            else
            {
                OwnerReport.rl.logError(4,"Unknown DataSet element '" + xNodeLoop.Name + "' ignored.");
            }         
        }
        if (this.getName() != null)
            OwnerReport.getLUAggrScope().Add(this.getName().getNm(), this);
        else
            // add to referenceable TextBoxes
            OwnerReport.rl.logError(4,"Name attribute must be specified in a DataSet."); 
        if (_Query == null)
            OwnerReport.rl.logError(8,"Query element must be specified in a DataSet.");
         
    }

    public void finalPass() throws Exception {
        if (_Query != null)
            // query must be resolved before fields
            _Query.finalPass();
         
        if (_Fields != null)
            _Fields.finalPass();
         
        if (_Filters != null)
            _Filters.finalPass();
         
        return ;
    }

    public void addHideDuplicates(Textbox tb) throws Exception {
        if (_HideDuplicates == null)
            _HideDuplicates = new List<Textbox>();
         
        _HideDuplicates.Add(tb);
    }

    public void getData(fyiReporting.RDL.Report rpt) throws Exception {
        resetHideDuplicates(rpt);
        if (_XmlRowData != null)
        {
            // Override the query and provide data from XML
            String xdata = getDataFile(rpt,_XmlRowFile);
            if (xdata == null)
                xdata = _XmlRowData;
             
            // didn't find any data
            _Query.getData(rpt,xdata,_Fields,_Filters);
            return ;
        }
         
        // get the data (and apply the filters
        if (_Query == null)
            return ;
         
        _Query.getData(rpt,this._Fields,_Filters);
    }

    // get the data (and apply the filters
    private String getDataFile(fyiReporting.RDL.Report rpt, String file) throws Exception {
        if (file == null)
            return null;
         
        // no file no data
        StreamReader fs = null;
        String d = null;
        String fullpath = new String();
        String folder = rpt.getFolder();
        if (folder == null || folder.Length == 0)
            fullpath = file;
        else
            fullpath = folder + Path.DirectorySeparatorChar + file; 
        try
        {
            fs = new StreamReader(fullpath);
            d = fs.ReadToEnd();
        }
        catch (FileNotFoundException fe)
        {
            rpt.rl.LogError(4, String.Format("XML data file {0} not found.\n{1}", fullpath, fe.StackTrace));
            d = null;
        }
        catch (Exception ge)
        {
            rpt.rl.LogError(4, String.Format("XML data file error {0}\n{1}\n{2}", fullpath, ge.Message, ge.StackTrace));
            d = null;
        }
        finally
        {
            if (fs != null)
                fs.Close();
             
        }
        return d;
    }

    public void setData(fyiReporting.RDL.Report rpt, IDataReader dr) throws Exception {
        getQuery().setData(rpt,dr,_Fields,_Filters);
    }

    // get the data (and apply the filters
    public void setData(fyiReporting.RDL.Report rpt, DataTable dt) throws Exception {
        getQuery().setData(rpt,dt,_Fields,_Filters);
    }

    public void setData(fyiReporting.RDL.Report rpt, XmlDocument xmlDoc) throws Exception {
        getQuery().setData(rpt,xmlDoc,_Fields,_Filters);
    }

    public void setData(fyiReporting.RDL.Report rpt, IEnumerable ie) throws Exception {
        getQuery().setData(rpt,ie,_Fields,_Filters);
    }

    public void resetHideDuplicates(fyiReporting.RDL.Report rpt) throws Exception {
        if (_HideDuplicates == null)
            return ;
         
        for (Object __dummyForeachVar3 : _HideDuplicates)
        {
            Textbox tb = (Textbox)__dummyForeachVar3;
            tb.resetPrevious(rpt);
        }
    }

    public Name getName() throws Exception {
        return _Name;
    }

    public void setName(Name value) throws Exception {
        _Name = value;
    }

    public Fields getFields() throws Exception {
        return _Fields;
    }

    public void setFields(Fields value) throws Exception {
        _Fields = value;
    }

    public Query getQuery() throws Exception {
        return _Query;
    }

    public void setQuery(Query value) throws Exception {
        _Query = value;
    }

    public TrueFalseAutoEnum getCaseSensitivity() throws Exception {
        return _CaseSensitivity;
    }

    public void setCaseSensitivity(TrueFalseAutoEnum value) throws Exception {
        _CaseSensitivity = value;
    }

    public String getCollation() throws Exception {
        return _Collation;
    }

    public void setCollation(String value) throws Exception {
        _Collation = value;
    }

    public TrueFalseAutoEnum getAccentSensitivity() throws Exception {
        return _AccentSensitivity;
    }

    public void setAccentSensitivity(TrueFalseAutoEnum value) throws Exception {
        _AccentSensitivity = value;
    }

    public TrueFalseAutoEnum getKanatypeSensitivity() throws Exception {
        return _KanatypeSensitivity;
    }

    public void setKanatypeSensitivity(TrueFalseAutoEnum value) throws Exception {
        _KanatypeSensitivity = value;
    }

    public TrueFalseAutoEnum getWidthSensitivity() throws Exception {
        return _WidthSensitivity;
    }

    public void setWidthSensitivity(TrueFalseAutoEnum value) throws Exception {
        _WidthSensitivity = value;
    }

    public Filters getFilters() throws Exception {
        return _Filters;
    }

    public void setFilters(Filters value) throws Exception {
        _Filters = value;
    }

}


