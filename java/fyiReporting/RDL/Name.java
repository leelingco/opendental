//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
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
* A report object name.   CLS comliant identifier.
*/
public class Name   
{
    String _Name = new String();
    // name CLS compliant identifier; www.unicode.org/unicode/reports/tr15/tr15-18.html
    public Name(String name) throws Exception {
        _Name = name;
    }

    public String getNm() throws Exception {
        return _Name;
    }

    public void setNm(String value) throws Exception {
        _Name = value;
    }

    public String toString() {
        try
        {
            return _Name;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


