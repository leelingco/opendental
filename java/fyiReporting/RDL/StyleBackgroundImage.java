//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:28 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.EmbeddedImage;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ImageRepeat;
import fyiReporting.RDL.PageImage;
import fyiReporting.RDL.Parser;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.StyleBackgroundImageSource;
import fyiReporting.RDL.StyleBackgroundImageSourceEnum;
import fyiReporting.RDL.StyleInfo;

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
* Represents the background image information in a Style.
*/
public class StyleBackgroundImage  extends ReportLink 
{
    StyleBackgroundImageSourceEnum _Source = StyleBackgroundImageSourceEnum.External;
    // Identifies the source of the image:
    Expression _Value;
    // (string) See Source. Expected datatype is string or
    // binary, depending on Source. If the Value is
    // null, no background image is displayed.
    Expression _MIMEType;
    // (string) The MIMEType for the image.
    // Valid values are: image/bmp, image/jpeg,
    // image/gif, image/png, image/x-png
    // Required if Source = Database. Ignored otherwise.
    Expression _BackgroundRepeat;
    // (Enum BackgroundRepeat) Indicates how the background image should
    // repeat to fill the available space: Default: Repeat
    boolean _ConstantImage = new boolean();
    // true if constant image
    public StyleBackgroundImage(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Source = StyleBackgroundImageSourceEnum.Unknown;
        _Value = null;
        _MIMEType = null;
        _BackgroundRepeat = null;
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
                _Source = StyleBackgroundImageSource.GetStyle(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("Value"))
            {
                _Value = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar0.equals("MIMEType"))
            {
                _MIMEType = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar0.equals("BackgroundRepeat"))
            {
                _BackgroundRepeat = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown BackgroundImage element '" + xNodeLoop.Name + "' ignored.");
            }    
        }
        if (_Source == StyleBackgroundImageSourceEnum.Unknown)
            OwnerReport.rl.logError(8,"BackgroundImage requires the Source element.");
         
        if (_Value == null)
            OwnerReport.rl.logError(8,"BackgroundImage requires the Value element.");
         
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Value != null)
            _Value.finalPass();
         
        if (_MIMEType != null)
            _MIMEType.finalPass();
         
        if (_BackgroundRepeat != null)
            _BackgroundRepeat.finalPass();
         
        _ConstantImage = this.isConstant();
        return ;
    }

    // Generate a CSS string from the specified styles
    public String getCSS(fyiReporting.RDL.Report rpt, Row row, boolean bDefaults) throws Exception {
        StringBuilder sb = new StringBuilder();
        // TODO: need to handle other types of sources
        if (_Value != null && _Source == StyleBackgroundImageSourceEnum.External)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "background-image:url(\"{0}\");", _Value.evaluateString(rpt,row));
        else if (bDefaults)
            return "background-image:none;";
          
        if (_BackgroundRepeat != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "background-repeat:{0};", _BackgroundRepeat.evaluateString(rpt,row));
        else if (bDefaults)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "background-repeat:repeat;");
          
        return sb.ToString();
    }

    public boolean isConstant() throws Exception {
        if (_Source == StyleBackgroundImageSourceEnum.Database)
            return false;
         
        boolean rc = true;
        if (_Value != null)
            rc = _Value.isConstant();
         
        if (!rc)
            return false;
         
        if (_BackgroundRepeat != null)
            rc = _BackgroundRepeat.isConstant();
         
        return rc;
    }

    public PageImage getPageImage(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String mtype = null;
        Stream strm = null;
        System.Drawing.Image im = null;
        PageImage pi = null;
        WorkClass wc = getWC(rpt);
        if (wc.PgImage != null)
        {
            // have we already generated this one
            // reuse most of the work; only position will likely change
            pi = new PageImage(wc.PgImage.getImgFormat(),wc.PgImage.getImageData(),wc.PgImage.getSamplesW(),wc.PgImage.getSamplesH());
            pi.setName(wc.PgImage.getName());
            return pi;
        }
         
        // this is name it will be shared under
        try
        {
            RefSupport<String> refVar___0 = new RefSupport<String>();
            strm = getImageStream(rpt,row,refVar___0);
            mtype = refVar___0.getValue();
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
            pi = new PageImage(imf, ba, width, height);
            pi.setSI(new StyleInfo());
            // this will just default everything
            if (_BackgroundRepeat != null)
            {
                String r = _BackgroundRepeat.evaluateString(rpt,row).ToLower();
                System.String __dummyScrutVar1 = r;
                if (__dummyScrutVar1.equals("repeat"))
                {
                    pi.setRepeat(ImageRepeat.Repeat);
                }
                else if (__dummyScrutVar1.equals("repeatx"))
                {
                    pi.setRepeat(ImageRepeat.RepeatX);
                }
                else if (__dummyScrutVar1.equals("repeaty"))
                {
                    pi.setRepeat(ImageRepeat.RepeatY);
                }
                else
                {
                    pi.setRepeat(ImageRepeat.NoRepeat);
                }   
            }
            else
                pi.setRepeat(ImageRepeat.Repeat); 
            if (_ConstantImage)
            {
                wc.PgImage = pi;
                // create unique name; PDF generation uses this to optimize the saving of the image only once
                RefSupport refVar___1 = new RefSupport(Parser.Counter);
                pi.setName("pi" + Interlocked.Increment(refVar___1).ToString());
                Parser.Counter = refVar___1.getValue();
            }
             
        }
        finally
        {
            // create unique name
            if (strm != null)
                strm.Close();
             
            if (im != null)
                im.Dispose();
             
        }
        return pi;
    }

    Stream getImageStream(fyiReporting.RDL.Report rpt, Row row, RefSupport<String> mtype) throws Exception {
        mtype.setValue(null);
        Stream strm = null;
        try
        {
            switch(this._Source)
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
                    if (_MIMEType == null)
                        return null;
                     
                    mtype.setValue(_MIMEType.evaluateString(rpt,row));
                    String fname = _Value.evaluateString(rpt,row);
                    strm = new FileStream(fname, System.IO.FileMode.Open, FileAccess.Read);
                    break;
                default: 
                    return null;
            
            }
        }
        catch (Exception __dummyCatchVar0)
        {
            if (strm != null)
            {
                strm.Close();
                strm = null;
            }
             
        }

        return strm;
    }

    static public String getCSSDefaults() throws Exception {
        return "background-image:none;";
    }

    public StyleBackgroundImageSourceEnum getSource() throws Exception {
        return _Source;
    }

    public void setSource(StyleBackgroundImageSourceEnum value) throws Exception {
        _Source = value;
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

    public Expression getBackgroundRepeat() throws Exception {
        return _BackgroundRepeat;
    }

    public void setBackgroundRepeat(Expression value) throws Exception {
        _BackgroundRepeat = value;
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


