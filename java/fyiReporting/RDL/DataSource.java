//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataSourceDefn;

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
* Information about the data source (e.g. a database connection string).
*/
public class DataSource   
{
    fyiReporting.RDL.Report _rpt;
    // Runtime report
    DataSourceDefn _dsd;
    // DataSource definition
    public DataSource(fyiReporting.RDL.Report rpt, DataSourceDefn dsd) throws Exception {
        _rpt = rpt;
        _dsd = dsd;
    }

    /**
    * Get/Set the database connection.  User must handle closing of connection.
    */
    public IDbConnection getUserConnection() throws Exception {
        return _dsd.isUserConnection(_rpt) ? _dsd.getConnection(_rpt) : null;
    }

    // never reveal connection internally connected
    public void setUserConnection(IDbConnection value) throws Exception {
        _dsd.cleanUp(_rpt);
        // clean up prior connection if necessary
        _dsd.setUserConnection(_rpt,value);
    }

}


