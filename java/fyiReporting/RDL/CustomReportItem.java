//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.CustomProperty;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ICustomReportItem;
import fyiReporting.RDL.ImageSizingEnum;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.PageImage;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.RdlEngineConfig;
import fyiReporting.RDL.Rectangle;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItems;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RSize;

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
* CustomReportItem describes a report item that is not natively defined in RDL.  The
* RdlEngineConfig.xml file (loaded by RdlEngineConfig.cs) contains a list of the
* extensions.   RdlCri.dll is a code module that contains the built-in CustomReportItems.
* However, the runtime dynamically loads this so RdlCrl.dll is not required for the
* report engine to function properly.
*/
public class CustomReportItem  extends Rectangle 
{
    static final ImageFormat IMAGEFORMAT = ImageFormat.Jpeg;
    String _Type = new String();
    // The type of the custom report item. Interpreted by a
    // report design tool or server.
    System.Collections.Generic.List<CustomProperty> _Properties = new System.Collections.Generic.List<CustomProperty>();
    public CustomReportItem(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p, xNode, false);
        _Type = null;
        ReportItems ris = null;
        boolean bVersion2 = true;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Type"))
            {
                _Type = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("ReportItems"))
            {
                // Version 1 of the specification
                ris = new ReportItems(r,this,xNodeLoop);
                bVersion2 = false;
            }
            else if (__dummyScrutVar0.equals("AltReportItem"))
            {
                // Verstion 2 of the specification
                ris = new ReportItems(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("CustomProperties"))
            {
                _Properties = customProperties(xNodeLoop);
            }
            else
            {
                if (reportItemElement(xNodeLoop))
                    break;
                 
                // try at ReportItem level
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown CustomReportItem element " + xNodeLoop.Name + " ignored.");
            }    
        }
        setReportItems(ris);
        if (bVersion2 && ris != null)
        {
            if (ris.getItems().Count != 1)
                OwnerReport.rl.logError(8,"Only one element is allowed within an AltReportItem.");
             
        }
         
        if (_Type == null)
            OwnerReport.rl.logError(8,"CustomReportItem requires the Type element.");
         
    }

    public void finalPass() throws Exception {
        super.finalPass();
        // this handles the finalpass of the AltReportItems
        // Handle the final pass for the Custom Properties
        if (_Properties != null)
        {
            for (Object __dummyForeachVar1 : _Properties)
            {
                CustomProperty cp = (CustomProperty)__dummyForeachVar1;
                cp.getName().finalPass();
                cp.getValue().finalPass();
            }
        }
         
        // Find out whether the type is known
        ICustomReportItem cri = null;
        try
        {
            cri = RdlEngineConfig.createCustomReportItem(_Type);
        }
        catch (Exception ex)
        {
            // Not an error since we'll simply use the ReportItems
            OwnerReport.rl.LogError(4, String.Format("CustomReportItem load of {0} failed: {1}", _Type, ex.Message));
        }
        finally
        {
            if (cri != null)
                cri.Dispose();
             
        }
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        fyiReporting.RDL.Report rpt = ip.report();
        ICustomReportItem cri = null;
        try
        {
            cri = RdlEngineConfig.createCustomReportItem(_Type);
        }
        catch (Exception ex)
        {
            rpt.rl.LogError(8, String.Format("Exception in CustomReportItem handling.\n{0}\n{1}", ex.Message, ex.StackTrace));
        }
        finally
        {
            if (cri != null)
                cri.Dispose();
             
        }
        return ;
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        fyiReporting.RDL.Report rpt = pgs.getReport();
        if (isHidden(pgs.getReport(),row))
            return ;
         
        setPagePositionBegin(pgs);
        // Build the Chart bitmap, along with data regions
        Page p = pgs.getCurrentPage();
        ICustomReportItem cri = null;
        Bitmap bm = null;
        try
        {
            cri = RdlEngineConfig.createCustomReportItem(_Type);
            setProperties(pgs.getReport(),row,cri);
            int width = widthCalc(rpt,pgs.getG()) - (getStyle() == null ? 0 : (getStyle().evalPaddingLeftPx(rpt,row) + getStyle().evalPaddingRightPx(rpt,row)));
            int height = RSize.pixelsFromPoints(this.getHeightOrOwnerHeight()) - (getStyle() == null ? 0 : (getStyle().evalPaddingTopPx(rpt,row) + getStyle().evalPaddingBottomPx(rpt,row)));
            bm = new Bitmap(width, height);
            cri.DrawImage(bm);
            MemoryStream ostrm = new MemoryStream();
            bm.Save(ostrm, IMAGEFORMAT);
            // generate a jpeg   TODO: get png to work with pdf
            byte[] ba = ostrm.ToArray();
            ostrm.Close();
            PageImage pi = new PageImage(IMAGEFORMAT, ba, width, height);
            // Create an image
            pi.setSizing(ImageSizingEnum.Clip);
            //                RunPageRegionBegin(pgs);
            setPagePositionAndStyle(rpt,pi,row);
            if (pgs.getCurrentPage().getYOffset() + pi.getY() + pi.getH() >= pgs.getBottomOfPage() && !pgs.getCurrentPage().isEmpty())
            {
                // force page break if it doesn't fit on the page
                pgs.nextOrNew();
                pgs.getCurrentPage().setYOffset(OwnerReport.getTopOfPage());
                if (this.getYParents() != null)
                    pi.setY(0);
                 
            }
             
            p = pgs.getCurrentPage();
            p.addObject(pi);
            // Put image onto the current page
            //              RunPageRegionEnd(pgs);
            if (!this.getPageBreakAtEnd() && !isTableOrMatrixCell(rpt))
            {
                float newY = pi.getY() + pi.getH();
                p.setYOffset(p.getYOffset() + newY);
            }
             
            // bump the y location
            setPagePositionEnd(pgs,pi.getY() + pi.getH());
        }
        catch (Exception ex)
        {
            rpt.rl.LogError(8, String.Format("Exception in CustomReportItem handling: {0}", ex.Message));
        }
        finally
        {
            if (cri != null)
                cri.Dispose();
             
        }
        return ;
    }

    void setProperties(fyiReporting.RDL.Report rpt, Row row, ICustomReportItem cri) throws Exception {
        if (_Properties == null || _Properties.Count == 0)
            return ;
         
        // Any properties specified?
        System.Collections.Generic.Dictionary<String, Object> dict = new Dictionary<String, Object>(_Properties.Count);
        for (Object __dummyForeachVar2 : _Properties)
        {
            CustomProperty cp = (CustomProperty)__dummyForeachVar2;
            String name = cp.getName().evaluateString(rpt,row);
            Object val = cp.getValue().evaluate(rpt,row);
            try
            {
                dict.Add(name, val);
            }
            catch (Exception __dummyCatchVar0)
            {
                rpt.rl.LogError(4, String.Format("Property {0} has already been set.  New value {1} ignored.", name, val));
            }
        
        }
        cri.SetProperties(dict);
    }

    public String getType() throws Exception {
        return _Type;
    }

    public void setType(String value) throws Exception {
        _Type = value;
    }

    List<CustomProperty> customProperties(XmlNode xNode) throws Exception {
        if (!xNode.HasChildNodes)
            return null;
         
        List<CustomProperty> cps = new List<CustomProperty>(xNode.ChildNodes.Count);
        for (Object __dummyForeachVar3 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar3;
            Name __dummyScrutVar1 = xNodeLoop.Name;
            if (__dummyScrutVar1.equals("CustomProperty"))
            {
                CustomProperty cp = customProperty(xNodeLoop);
                if (cp != null)
                    cps.Add(cp);
                 
            }
            else
            {
                OwnerReport.rl.logError(4,"Unknown CustomProperties element " + xNodeLoop.Name + " ignored.");
            } 
        }
        return cps;
    }

    CustomProperty customProperty(XmlNode xNode) throws Exception {
        Expression name = null;
        Expression val = null;
        CustomProperty cp = null;
        for (Object __dummyForeachVar4 : xNode.ChildNodes)
        {
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar4;
            Name __dummyScrutVar2 = xNodeLoop.Name;
            if (__dummyScrutVar2.equals("Name"))
            {
                name = new Expression(OwnerReport,this,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar2.equals("Value"))
            {
                val = new Expression(OwnerReport,this,xNodeLoop,ExpressionType.Variant);
            }
            else
            {
                OwnerReport.rl.logError(4,"Unknown CustomProperty element " + xNodeLoop.Name + " ignored.");
            }  
        }
        if (name == null || val == null)
            OwnerReport.rl.logError(8,"CustomProperty requires the Name and Value element.");
        else
        {
            cp = new CustomProperty(name,val);
        } 
        return cp;
    }

}


