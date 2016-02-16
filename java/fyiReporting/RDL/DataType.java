//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportDefn;

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
* Data types
*/
public class DataType   
{
    static public TypeCode getStyle(String s, ReportDefn r) throws Exception {
        TypeCode rs = new TypeCode();
        if (s.StartsWith("System."))
            s = s.Substring(7);
         
        System.String __dummyScrutVar0 = s;
        if (__dummyScrutVar0.equals("Boolean"))
        {
            rs = TypeCode.Boolean;
        }
        else if (__dummyScrutVar0.equals("DateTime"))
        {
            rs = TypeCode.DateTime;
        }
        else if (__dummyScrutVar0.equals("Decimal"))
        {
            rs = TypeCode.Decimal;
        }
        else if (__dummyScrutVar0.equals("Integer") || __dummyScrutVar0.equals("Int16") || __dummyScrutVar0.equals("Int32"))
        {
            rs = TypeCode.Int32;
        }
        else if (__dummyScrutVar0.equals("Int64"))
        {
            rs = TypeCode.Int64;
        }
        else if (__dummyScrutVar0.equals("Float") || __dummyScrutVar0.equals("Single") || __dummyScrutVar0.equals("Double"))
        {
            rs = TypeCode.Double;
        }
        else if (__dummyScrutVar0.equals("String") || __dummyScrutVar0.equals("Char"))
        {
            rs = TypeCode.String;
        }
        else
        {
            // user error
            rs = TypeCode.Object;
            r.rl.LogError(4, String.Format("'{0}' is not a recognized type, assuming System.Object.", s));
        }       
        return rs;
    }

    static public boolean isNumeric(TypeCode tc) throws Exception {
        TypeCode __dummyScrutVar1 = tc;
        if (__dummyScrutVar1.equals(TypeCode.Int64) || __dummyScrutVar1.equals(TypeCode.Int32) || __dummyScrutVar1.equals(TypeCode.Double) || __dummyScrutVar1.equals(TypeCode.Decimal))
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

}


// user error