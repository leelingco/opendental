//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
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
// for Color class
/**
* Some utility classes consisting entirely of static routines.
*/
public final class XmlUtil   
{
    static public boolean boolean(String tf, ReportLog rl) throws Exception {
        String low_tf = tf.ToLower();
        if (low_tf.CompareTo("true") == 0)
            return true;
         
        if (low_tf.CompareTo("false") == 0)
            return false;
         
        rl.logError(4,"Unknown True/False value '" + tf + "'.  False assumed.");
        return false;
    }

    static public Color colorFromHtml(String sc, Color dc) throws Exception {
        return colorFromHtml(sc,dc,null);
    }

    static public Color colorFromHtml(String sc, Color dc, fyiReporting.RDL.Report rpt) throws Exception {
        Color c = new Color();
        try
        {
            c = ColorTranslator.FromHtml(sc);
        }
        catch (Exception __dummyCatchVar0)
        {
            c = dc;
            if (rpt != null)
                rpt.rl.LogError(4, String.Format("'{0}' is an invalid HTML color.", sc));
             
        }

        return c;
    }

    static public int integer(String i) throws Exception {
        return Convert.ToInt32(i);
    }

    /**
    * Takes an arbritrary string and returns a string that can be embedded in an
    * XML element.  For example, '<' is changed to '&lt;'
    * 
    *  @param s 
    *  @return
    */
    static public String xmlAnsi(String s) throws Exception {
        StringBuilder rs = new StringBuilder(s.Length);
        for (Object __dummyForeachVar0 : s)
        {
            char c = (Character)__dummyForeachVar0;
            if (c == '<')
                rs.Append("&lt;");
            else if (c == '&')
                rs.Append("&amp;");
            else if ((int)c <= 127)
                // in ANSI range
                rs.Append(c);
            else
                rs.Append("&#" + ((int)c).ToString() + ";");   
        }
        return rs.ToString();
    }

    static public void xslTrans(String xslFile, String inXml, Stream outResult) throws Exception {
        XmlDocument xDoc = new XmlDocument();
        xDoc.LoadXml(inXml);
        XslCompiledTransform xslt = new XslCompiledTransform();
        //Load the stylesheet.
        xslt.Load(xslFile);
        xslt.Transform(xDoc, null, outResult);
        return ;
    }

    static public String escapeXmlAttribute(String s) throws Exception {
        String result = new String();
        result = s.Replace("'", "&#39;");
        return result;
    }

    /**
    * Loads assembly from file; tries up to 3 time; load with name, load from BaseDirectory,
    * and load from BaseDirectory concatenated with Relative directory.
    * 
    *  @param s 
    *  @return
    */
    static public Assembly assemblyLoadFrom(String s) throws Exception {
        Assembly ra = null;
        try
        {
            // try 1) loading just from name
            ra = Assembly.LoadFrom(s);
        }
        catch (Exception __dummyCatchVar1)
        {
            // try 2) loading from the base directory name
            String f = AppDomain.CurrentDomain.BaseDirectory + Path.GetFileName(s);
            try
            {
                ra = Assembly.LoadFile(f);
            }
            catch (Exception e)
            {
                // try 3) loading from the relative search path
                String relative = AppDomain.CurrentDomain.RelativeSearchPath;
                if (relative == null || StringSupport.equals(relative, String.Empty))
                    throw e;
                 
                f = relative + Path.DirectorySeparatorChar + Path.GetFileName(s);
                ra = Assembly.LoadFile(f);
            }
        
        }

        return ra;
    }

    static public Type getTypeFromTypeCode(TypeCode tc) throws Exception {
        Type t = null;
        TypeCode __dummyScrutVar0 = tc;
        if (__dummyScrutVar0.equals(TypeCode.Boolean))
        {
            t = Type.GetType("System.Boolean");
        }
        else if (__dummyScrutVar0.equals(TypeCode.Byte))
        {
            t = Type.GetType("System.Byte");
        }
        else if (__dummyScrutVar0.equals(TypeCode.Char))
        {
            t = Type.GetType("System.Char");
        }
        else if (__dummyScrutVar0.equals(TypeCode.DateTime))
        {
            t = Type.GetType("System.DateTime");
        }
        else if (__dummyScrutVar0.equals(TypeCode.Decimal))
        {
            t = Type.GetType("System.Decimal");
        }
        else if (__dummyScrutVar0.equals(TypeCode.Double))
        {
            t = Type.GetType("System.Double");
        }
        else if (__dummyScrutVar0.equals(TypeCode.Int16))
        {
            t = Type.GetType("System.Int16");
        }
        else if (__dummyScrutVar0.equals(TypeCode.Int32))
        {
            t = Type.GetType("System.Int32");
        }
        else if (__dummyScrutVar0.equals(TypeCode.Int64))
        {
            t = Type.GetType("System.Int64");
        }
        else if (__dummyScrutVar0.equals(TypeCode.Object))
        {
            t = Type.GetType("System.Object");
        }
        else if (__dummyScrutVar0.equals(TypeCode.SByte))
        {
            t = Type.GetType("System.SByte");
        }
        else if (__dummyScrutVar0.equals(TypeCode.Single))
        {
            t = Type.GetType("System.Single");
        }
        else if (__dummyScrutVar0.equals(TypeCode.String))
        {
            t = Type.GetType("System.String");
        }
        else if (__dummyScrutVar0.equals(TypeCode.UInt16))
        {
            t = Type.GetType("System.UInt16");
        }
        else if (__dummyScrutVar0.equals(TypeCode.UInt32))
        {
            t = Type.GetType("System.UInt32");
        }
        else if (__dummyScrutVar0.equals(TypeCode.UInt64))
        {
            t = Type.GetType("System.UInt64");
        }
        else
        {
            t = Type.GetType("Object");
        }                
        return t;
    }

}


