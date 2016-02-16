//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.ThreeDPropertiesDrawingStyle;
import fyiReporting.RDL.ThreeDPropertiesDrawingStyleEnum;
import fyiReporting.RDL.ThreeDPropertiesProjectionMode;
import fyiReporting.RDL.ThreeDPropertiesProjectionModeEnum;
import fyiReporting.RDL.ThreeDPropertiesShading;
import fyiReporting.RDL.ThreeDPropertiesShadingEnum;
import fyiReporting.RDL.XmlUtil;

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
* ThreeDProperties definition and processing.
*/
public class ThreeDProperties  extends ReportLink 
{
    boolean _Enabled = new boolean();
    // Whether or not a chart is displayed in 3D. Default is False (2D).
    ThreeDPropertiesProjectionModeEnum _ProjectionMode = ThreeDPropertiesProjectionModeEnum.Perspective;
    // The projection mode used for the 3D rendering
    int _Rotation = new int();
    // Rotation angle
    //  Applies only for Perspective projection
    int _Inclination = new int();
    // Inclination angle
    //  Applies only for Perspective projection
    int _Perspective = new int();
    // Represents the percent of perspective
    //  Applies only for Perspective projection
    int _HeightRatio = new int();
    // Ratio between height and width
    int _DepthRatio = new int();
    // Ration between depth and width
    ThreeDPropertiesShadingEnum _Shading = ThreeDPropertiesShadingEnum.None;
    // Default: None
    int _GapDepth = new int();
    // Percent depth gap between 3D bars and columns
    int _WallThickness = new int();
    // Percent thickness of outer walls
    ThreeDPropertiesDrawingStyleEnum _DrawingStyle = ThreeDPropertiesDrawingStyleEnum.Cylinder;
    // Determines shape of chart data displayed Default: cube
    //		Only applies to bar and column chart types.
    boolean _Clustered = new boolean();
    // Determines if data series are clustered
    // (displayed along distinct rows). Only
    // applies to bar and column chart types.  Defaults to false.
    public ThreeDProperties(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Enabled = false;
        _ProjectionMode = ThreeDPropertiesProjectionModeEnum.Perspective;
        _Rotation = 0;
        _Inclination = 0;
        _Perspective = 0;
        _HeightRatio = 0;
        _DepthRatio = 0;
        _Shading = ThreeDPropertiesShadingEnum.None;
        _GapDepth = 0;
        _WallThickness = 0;
        _DrawingStyle = ThreeDPropertiesDrawingStyleEnum.Cube;
        _Clustered = false;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Enabled"))
            {
                _Enabled = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("ProjectionMode"))
            {
                _ProjectionMode = ThreeDPropertiesProjectionMode.GetStyle(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("Rotation"))
            {
                _Rotation = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("Inclination"))
            {
                _Inclination = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("Perspective"))
            {
                _Perspective = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("HeightRatio"))
            {
                _HeightRatio = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("DepthRatio"))
            {
                _DepthRatio = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("Shading"))
            {
                _Shading = ThreeDPropertiesShading.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("GapDepth"))
            {
                _GapDepth = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("WallThickness"))
            {
                _WallThickness = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("DrawingStyle"))
            {
                _DrawingStyle = ThreeDPropertiesDrawingStyle.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("Clustered"))
            {
                _Clustered = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
            }            
        }
    }

    public void finalPass() throws Exception {
        return ;
    }

    public boolean getEnabled() throws Exception {
        return _Enabled;
    }

    public void setEnabled(boolean value) throws Exception {
        _Enabled = value;
    }

    public ThreeDPropertiesProjectionModeEnum getProjectionMode() throws Exception {
        return _ProjectionMode;
    }

    public void setProjectionMode(ThreeDPropertiesProjectionModeEnum value) throws Exception {
        _ProjectionMode = value;
    }

    public int getRotation() throws Exception {
        return _Rotation;
    }

    public void setRotation(int value) throws Exception {
        _Rotation = value;
    }

    public int getInclination() throws Exception {
        return _Inclination;
    }

    public void setInclination(int value) throws Exception {
        _Inclination = value;
    }

    public int getPerspective() throws Exception {
        return _Perspective;
    }

    public void setPerspective(int value) throws Exception {
        _Perspective = value;
    }

    public int getHeightRatio() throws Exception {
        return _HeightRatio;
    }

    public void setHeightRatio(int value) throws Exception {
        _HeightRatio = value;
    }

    public int getDepthRatio() throws Exception {
        return _DepthRatio;
    }

    public void setDepthRatio(int value) throws Exception {
        _DepthRatio = value;
    }

    public ThreeDPropertiesShadingEnum getShading() throws Exception {
        return _Shading;
    }

    public void setShading(ThreeDPropertiesShadingEnum value) throws Exception {
        _Shading = value;
    }

    public int getGapDepth() throws Exception {
        return _GapDepth;
    }

    public void setGapDepth(int value) throws Exception {
        _GapDepth = value;
    }

    public int getWallThickness() throws Exception {
        return _WallThickness;
    }

    public void setWallThickness(int value) throws Exception {
        _WallThickness = value;
    }

    public ThreeDPropertiesDrawingStyleEnum getDrawingStyle() throws Exception {
        return _DrawingStyle;
    }

    public void setDrawingStyle(ThreeDPropertiesDrawingStyleEnum value) throws Exception {
        _DrawingStyle = value;
    }

    public boolean getClustered() throws Exception {
        return _Clustered;
    }

    public void setClustered(boolean value) throws Exception {
        _Clustered = value;
    }

}


