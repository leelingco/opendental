//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataElementStyleEnum;
import fyiReporting.RDL.ReportLog;

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
* Indicates whether textboxes should render as elements or attributes.
*/
public class DataElementStyle   
{
    static public DataElementStyleEnum getStyle(String s, ReportLog rl) throws Exception {
        DataElementStyleEnum rs = DataElementStyleEnum.Auto;
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Auto"))
        {
            rs = DataElementStyleEnum.Auto;
        }
        else if (__dummyScrutVar0.equals("AttributeNormal"))
        {
            rs = DataElementStyleEnum.AttributeNormal;
        }
        else if (__dummyScrutVar0.equals("ElementNormal"))
        {
            rs = DataElementStyleEnum.ElementNormal;
        }
        else
        {
            rl.logError(4,"Unknown DataElementStyle '" + s + "'.  AttributeNormal assumed.");
            rs = DataElementStyleEnum.AttributeNormal;
        }   
        return rs;
    }

}


