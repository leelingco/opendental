//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataSetDefn;

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
* Runtime Information about a set of data; public interface to the definition
*/
public class DataSet   
{
    fyiReporting.RDL.Report _rpt;
    //	the runtime report
    DataSetDefn _dsd;
    //  the true definition of the DataSet
    public DataSet(fyiReporting.RDL.Report rpt, DataSetDefn dsd) throws Exception {
        _rpt = rpt;
        _dsd = dsd;
    }

    public void setData(IDataReader dr) throws Exception {
        _dsd.getQuery().setData(_rpt,dr,_dsd.getFields(),_dsd.getFilters());
    }

    // get the data (and apply the filters
    public void setData(DataTable dt) throws Exception {
        _dsd.getQuery().setData(_rpt,dt,_dsd.getFields(),_dsd.getFilters());
    }

    public void setData(XmlDocument xmlDoc) throws Exception {
        _dsd.getQuery().setData(_rpt,xmlDoc,_dsd.getFields(),_dsd.getFilters());
    }

    public void setData(IEnumerable ie) throws Exception {
        _dsd.getQuery().setData(_rpt,ie,_dsd.getFields(),_dsd.getFilters());
    }

}


