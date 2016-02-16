//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:24 PM
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
* Class for defining chart layout.  For example, the plot area of a chart.
*/
public class ChartLayout   
{
    int _Height = new int();
    // total width of layout
    int _Width = new int();
    // total height
    int _LeftMargin = new int();
    // Margins
    int _RightMargin = new int();
    int _TopMargin = new int();
    int _BottomMargin = new int();
    System.Drawing.Rectangle _PlotArea = new System.Drawing.Rectangle();
    public ChartLayout(int width, int height) throws Exception {
        _Width = width;
        _Height = height;
        _LeftMargin = _RightMargin = _TopMargin = _BottomMargin = 0;
        _PlotArea = System.Drawing.Rectangle.Empty;
    }

    public int getWidth() throws Exception {
        return _Width;
    }

    public int getHeight() throws Exception {
        return _Height;
    }

    public int getLeftMargin() throws Exception {
        return _LeftMargin;
    }

    public void setLeftMargin(int value) throws Exception {
        _LeftMargin = value;
    }

    public int getRightMargin() throws Exception {
        return _RightMargin;
    }

    public void setRightMargin(int value) throws Exception {
        _RightMargin = value;
    }

    public int getTopMargin() throws Exception {
        return _TopMargin;
    }

    public void setTopMargin(int value) throws Exception {
        _TopMargin = value;
    }

    public int getBottomMargin() throws Exception {
        return _BottomMargin;
    }

    public void setBottomMargin(int value) throws Exception {
        _BottomMargin = value;
    }

    public System.Drawing.Rectangle getPlotArea() throws Exception {
        if (_PlotArea == System.Drawing.Rectangle.Empty)
        {
            int w = _Width - _LeftMargin - _RightMargin;
            if (w <= 0)
                throw new Exception("Plot area width is less than or equal to 0");
             
            int h = _Height - _TopMargin - _BottomMargin;
            if (h <= 0)
                throw new Exception("Plot area height is less than or equal to 0");
             
            _PlotArea = new System.Drawing.Rectangle(_LeftMargin, _TopMargin, w, h);
        }
         
        return _PlotArea;
    }

}


