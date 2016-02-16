//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
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
* The Size definition.  Held in a normalized format but convertible to multiple measurements.
*/
public class RSize   
{
    int _Size = new int();
    // Normalized size in 1/100,000 meters
    String _Original = new String();
    // save original string for recreation of syntax
    public RSize(ReportDefn r, String t) throws Exception {
        // Size is specified in CSS Length Units
        // format is <decimal number nnn.nnn><optional space><unit>
        // in -> inches (1 inch = 2.54 cm)
        // cm -> centimeters (.01 meters)
        // mm -> millimeters (.001 meters)
        // pt -> points (1 point = 1/72.27 inches)
        // pc -> Picas (1 pica = 12 points)
        _Original = t;
        // Save original string for recreation
        t = t.Trim();
        int space = t.LastIndexOf(' ');
        String n = new String();
        // number string
        String u = new String();
        // unit string
        double d = new double();
        try
        {
            // initial number
            // Convert.ToDecimal can be very picky
            if (space != -1)
            {
                // any spaces
                n = t.Substring(0, space).Trim();
                // number string
                u = t.Substring(space).Trim();
            }
            else // unit string
            if (t.Length >= 3)
            {
                n = t.Substring(0, t.Length - 2).Trim();
                u = t.Substring(t.Length - 2).Trim();
            }
            else
            {
                // Illegal unit
                r.rl.LogError(4, String.Format("Illegal size '{0}' specified, assuming 0 length.", t));
                _Size = 0;
                return ;
            }  
            if (!Regex.IsMatch(n, "\\A[ ]*[-]?[0-9]*[.]?[0-9]*[ ]*\\Z"))
            {
                r.rl.LogError(4, String.Format("Unknown characters in '{0}' specified.  Number must be of form '###.##'.  Local conversion will be attempted.", t));
                d = Convert.ToDecimal(n, NumberFormatInfo.InvariantInfo);
            }
            else
                // initial number
                d = Convert.ToDecimal(n, NumberFormatInfo.InvariantInfo); 
        }
        catch (Exception ex)
        {
            // initial number
            // Illegal unit
            r.rl.logError(4,"Illegal size '" + t + "' specified, assuming 0 length.\r\n" + ex.Message);
            _Size = 0;
            return ;
        }

        System.String __dummyScrutVar0 = u;
        // convert to millimeters
        if (__dummyScrutVar0.equals("in"))
        {
            _Size = (int)(d * 2540m);
        }
        else if (__dummyScrutVar0.equals("cm"))
        {
            _Size = (int)(d * 1000m);
        }
        else if (__dummyScrutVar0.equals("mm"))
        {
            _Size = (int)(d * 100m);
        }
        else if (__dummyScrutVar0.equals("pt"))
        {
            _Size = (int)(d * (2540m / POINTSIZEM));
        }
        else if (__dummyScrutVar0.equals("pc"))
        {
            _Size = (int)(d * (2540m / POINTSIZEM * 12m));
        }
        else
        {
            // Illegal unit
            r.rl.logError(4,"Unknown sizing unit '" + u + "' specified, assuming inches.");
            _Size = (int)(d * 2540m);
        }     
        if (_Size > 160 * 2540)
        {
            // Size can't be greater than 160 inches according to spec
            // but RdlEngine supports higher values so just do a warning
            r.rl.logError(4,"Size '" + this._Original + "' is larger than the RDL specification maximum of 160 inches.");
        }
         
    }

    //				_Size = 160 * 2540;     // this would force maximum to spec max of 160
    public RSize(ReportDefn r, XmlNode xNode) throws Exception {
        this(r, xNode.InnerText);
    }

    public int getSize() throws Exception {
        return _Size;
    }

    public void setSize(int value) throws Exception {
        _Size = value;
    }

    // Return value as if specified as px
    public int getPixelsX() throws Exception {
        // For now assume 96 dpi;  TODO: what would be better way; shouldn't use server display pixels
        double p = _Size;
        p = p / 2540m;
        // get it in inches
        p = p * 96;
        return (int)p;
    }

    //
    static public final float POINTSIZED = 72.27f;
    static public final double POINTSIZEM = 72.27m;
    static public int pixelsFromPoints(float x) throws Exception {
        int result = (int)(x * 96 / POINTSIZED);
        return result;
    }

    // convert to pixels
    static public int pixelsFromPoints(Graphics g, float x) throws Exception {
        int result = (int)(x * g.DpiX / POINTSIZED);
        return result;
    }

    // convert to pixels
    public int getPixelsY() throws Exception {
        // For now assume 96 dpi
        double p = _Size;
        p = p / 2540m;
        // get it in inches
        p = p * 96;
        return (int)p;
    }

    //
    public float getPoints() throws Exception {
        return (float)((double)_Size / 2540.0 * POINTSIZED);
    }

    static public float pointsFromPixels(Graphics g, int x) throws Exception {
        float result = (float)((x * POINTSIZED) / g.DpiX);
        return result;
    }

    // convert to points from pixels
    static public float pointsFromPixels(Graphics g, float x) throws Exception {
        float result = (float)((x * POINTSIZED) / g.DpiX);
        return result;
    }

    // convert to points from pixels
    public String getOriginal() throws Exception {
        return _Original;
    }

    public void setOriginal(String value) throws Exception {
        _Original = value;
    }

}


