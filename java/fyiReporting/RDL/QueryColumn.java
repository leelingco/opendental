//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;


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
* When Query is database SQL; QueryColumn represents actual database column.
*/
public class QueryColumn   
{
    public int colNum = new int();
    // Column # in query select
    public String colName = new String();
    // Column name in query
    public TypeCode _colType = new TypeCode();
    // TypeCode in query
    public QueryColumn(int colnum, String name, TypeCode c) throws Exception {
        colNum = colnum;
        colName = name;
        _colType = c;
    }

    // Treat Char as String for queries: <sigh> drivers sometimes confuse char and string types
    //    telling me a type is char but actually returning a string (Mono work around)
    public TypeCode getcolType() throws Exception {
        return _colType == TypeCode.Char ? TypeCode.String : _colType;
    }

}


