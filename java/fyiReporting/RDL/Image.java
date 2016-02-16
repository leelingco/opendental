//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.EmbeddedImage;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ImageSizing;
import fyiReporting.RDL.ImageSizingEnum;
import fyiReporting.RDL.ImageSourceEnum;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.PageImage;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.Parser;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;

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
* Represents an image.  Source of image can from database, external or embedded.
*/
public class Image  extends ReportItem 
{
    ImageSourceEnum _ImageSource = ImageSourceEnum.External;
    // Identifies the source of the image:
    Expression _Value;
    // See Source. Expected datatype is string or
    // binary, depending on Source. If the Value is
    // null, no image is displayed.
    Expression _MIMEType;
    // (string) An expression, the value of which is the
    //	MIMEType for the image.
    //	Valid values are: image/bmp, image/jpeg,
    //	image/gif, image/png, image/x-png
    // Required if Source = Database. Ignored otherwise.
    ImageSizingEnum _Sizing = ImageSizingEnum.AutoSize;
    // Defines the behavior if the image does not fit within the specified size.
    boolean _ConstantImage = new boolean();
    // true if Image is a constant at runtime
    public Image(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p, xNode);
        _ImageSource = ImageSourceEnum.Unknown;
        _Value = null;
        _MIMEType = null;
        _Sizing = ImageSizingEnum.AutoSize;
        _ConstantImage = false;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Source"))
            {
                _ImageSource = fyiReporting.RDL.ImageSource.GetStyle(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("Value"))
            {
                _Value = new Expression(r,this,xNodeLoop,ExpressionType.Variant);
            }
            else if (__dummyScrutVar0.equals("MIMEType"))
            {
                _MIMEType = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar0.equals("Sizing"))
            {
                _Sizing = ImageSizing.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                if (reportItemElement(xNodeLoop))
                    break;
                 
                // try at ReportItem level
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Image element " + xNodeLoop.Name + " ignored.");
            }    
        }
        if (_ImageSource == ImageSourceEnum.Unknown)
            OwnerReport.rl.logError(8,"Image requires a Source element.");
         
        if (_Value == null)
            OwnerReport.rl.logError(8,"Image requires the Value element.");
         
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        super.finalPass();
        _Value.finalPass();
        if (_MIMEType != null)
            _MIMEType.finalPass();
         
        _ConstantImage = this.isConstant();
        return ;
    }

    // Returns true if the image and style remain constant at runtime
    boolean isConstant() throws Exception {
        if (_Value.isConstant())
        {
            if (_MIMEType == null || _MIMEType.isConstant())
            {
                return true;
            }
             
        }
         
        return false;
    }

    //					if (this.Style == null || this.Style.ConstantStyle)
    //						return true;
    // ok if style changes
    public void run(IPresent ip, Row row) throws Exception {
        super.run(ip,row);
        String mtype = null;
        Stream strm = null;
        try
        {
            RefSupport<String> refVar___0 = new RefSupport<String>();
            strm = getImageStream(ip.report(),row,refVar___0);
            mtype = refVar___0.getValue();
            ip.image(this,row,mtype,strm);
        }
        catch (Exception __dummyCatchVar0)
        {
        }
        finally
        {
            // image failed to load;  continue processing
            if (strm != null)
                strm.Close();
             
        }
        return ;
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        fyiReporting.RDL.Report r = pgs.getReport();
        if (isHidden(r,row))
            return ;
         
        WorkClass wc = getWC(r);
        String mtype = null;
        Stream strm = null;
        System.Drawing.Image im = null;
        setPagePositionBegin(pgs);
        if (wc.PgImage != null)
        {
            // have we already generated this one
            // reuse most of the work; only position will likely change
            PageImage pi = new PageImage(wc.PgImage.getImgFormat(),wc.PgImage.getImageData(),wc.PgImage.getSamplesW(),wc.PgImage.getSamplesH());
            pi.setName(wc.PgImage.getName());
            // this is name it will be shared under
            pi.setSizing(this._Sizing);
            this.setPagePositionAndStyle(r,pi,row);
            pgs.getCurrentPage().addObject(pi);
            setPagePositionEnd(pgs,pi.getY() + pi.getH());
            return ;
        }
         
        try
        {
            RefSupport<String> refVar___1 = new RefSupport<String>();
            strm = getImageStream(r,row,refVar___1);
            mtype = refVar___1.getValue();
            im = System.Drawing.Image.FromStream(strm);
            int height = im.Height;
            int width = im.Width;
            MemoryStream ostrm = new MemoryStream();
            ImageFormat imf = new ImageFormat();
            //				if (mtype.ToLower() == "image/jpeg")    //TODO: how do we get png to work
            //					imf = ImageFormat.Jpeg;
            //				else
            imf = ImageFormat.Jpeg;
            im.Save(ostrm, imf);
            byte[] ba = ostrm.ToArray();
            ostrm.Close();
            PageImage pi = new PageImage(imf, ba, width, height);
            pi.setSizing(this._Sizing);
            this.setPagePositionAndStyle(r,pi,row);
            pgs.getCurrentPage().addObject(pi);
            if (_ConstantImage)
            {
                wc.PgImage = pi;
                // create unique name; PDF generation uses this to optimize the saving of the image only once
                RefSupport refVar___2 = new RefSupport(Parser.Counter);
                pi.setName("pi" + Interlocked.Increment(refVar___2).ToString());
                Parser.Counter = refVar___2.getValue();
            }
             
            // create unique name
            setPagePositionEnd(pgs,pi.getY() + pi.getH());
        }
        catch (Exception e)
        {
            // image failed to load, continue processing
            r.rl.logError(4,"Image load failed.  " + e.Message);
        }
        finally
        {
            if (strm != null)
                strm.Close();
             
            if (im != null)
                im.Dispose();
             
        }
        return ;
    }

    Stream getImageStream(fyiReporting.RDL.Report rpt, Row row, RefSupport<String> mtype) throws Exception {
        mtype.setValue(null);
        Stream strm = null;
        try
        {
            switch(this.getImageSource())
            {
                case Database: 
                    if (_MIMEType == null)
                        return null;
                     
                    mtype.setValue(_MIMEType.evaluateString(rpt,row));
                    Object o = _Value.evaluate(rpt,row);
                    strm = new MemoryStream((byte[])o);
                    break;
                case Embedded: 
                    String name = _Value.evaluateString(rpt,row);
                    EmbeddedImage ei = (EmbeddedImage)OwnerReport.getLUEmbeddedImages()[name];
                    mtype.setValue(ei.getMIMEType());
                    byte[] ba = Convert.FromBase64String(ei.getImageData());
                    strm = new MemoryStream(ba);
                    break;
                case External: 
                    String fname = _Value.evaluateString(rpt,row);
                    mtype.setValue(getMimeType(fname));
                    if (fname.StartsWith("http:") || fname.StartsWith("file:") || fname.StartsWith("https:"))
                    {
                        WebRequest wreq = WebRequest.Create(fname);
                        WebResponse wres = wreq.GetResponse();
                        strm = wres.GetResponseStream();
                    }
                    else
                        strm = new FileStream(fname, System.IO.FileMode.Open, FileAccess.Read); 
                    break;
                default: 
                    return null;
            
            }
        }
        catch (Exception e)
        {
            if (strm != null)
            {
                strm.Close();
                strm = null;
            }
             
            rpt.rl.LogError(4, String.Format("Unable to load image. {0}", e.Message));
        }

        return strm;
    }

    public ImageSourceEnum getImageSource() throws Exception {
        return _ImageSource;
    }

    public void setImageSource(ImageSourceEnum value) throws Exception {
        _ImageSource = value;
    }

    public Expression getValue() throws Exception {
        return _Value;
    }

    public void setValue(Expression value) throws Exception {
        _Value = value;
    }

    public Expression getMIMEType() throws Exception {
        return _MIMEType;
    }

    public void setMIMEType(Expression value) throws Exception {
        _MIMEType = value;
    }

    public ImageSizingEnum getSizing() throws Exception {
        return _Sizing;
    }

    public void setSizing(ImageSizingEnum value) throws Exception {
        _Sizing = value;
    }

    public boolean getConstantImage() throws Exception {
        return _ConstantImage;
    }

    private String getMimeType(String file) throws Exception {
        String fileExt = new String();
        int startPos = file.LastIndexOf(".") + 1;
        fileExt = file.Substring(startPos).ToLower();
        System.String __dummyScrutVar2 = fileExt;
        if (__dummyScrutVar2.equals("bmp"))
        {
            return "image/bmp";
        }
        else if (__dummyScrutVar2.equals("jpeg") || __dummyScrutVar2.equals("jpe") || __dummyScrutVar2.equals("jpg") || __dummyScrutVar2.equals("jfif"))
        {
            return "image/jpeg";
        }
        else if (__dummyScrutVar2.equals("gif"))
        {
            return "image/gif";
        }
        else if (__dummyScrutVar2.equals("png"))
        {
            return "image/png";
        }
        else if (__dummyScrutVar2.equals("tif") || __dummyScrutVar2.equals("tiff"))
        {
            return "image/tiff";
        }
        else
        {
            return null;
        }     
    }

    private WorkClass getWC(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = rpt.getCache().get(this,"wc") instanceof WorkClass ? (WorkClass)rpt.getCache().get(this,"wc") : (WorkClass)null;
        if (wc == null)
        {
            wc = new WorkClass();
            rpt.getCache().add(this,"wc",wc);
        }
         
        return wc;
    }

    private void removeWC(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(this,"wc");
    }

    static class WorkClass   
    {
        public PageImage PgImage;
        // When ConstantImage is true this will save the PageImage for reuse
        public WorkClass() throws Exception {
            PgImage = null;
        }
    
    }

}


