//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package WebForms;

import CS2JNet.JavaSupport.language.RefSupport;
import OpenDentBusiness.PIn;
import OpenDentBusiness.SheetFieldType;
import WebForms.Logger;
import WebForms.ODWebServiceEntities;

/**
* Dynamically generates the sheet images which are read from the database.
* ToDo: test with various images.formats
*/
public class Handler1  extends IHttpHandler 
{
    private long WebSheetFieldDefID = 0;
    public void processRequest(HttpContext context) throws Exception {
        try
        {
            if (context.Request["WebSheetFieldDefID"] != null)
            {
                RefSupport refVar___0 = new RefSupport();
                Int64.TryParse(context.Request["WebSheetFieldDefID"].ToString().Trim(), refVar___0);
                WebSheetFieldDefID = refVar___0.getValue();
            }
             
            /*png images are used because the background of rectangles/lines can be set to transparent. For gif images the process of making the background transparent is convoluted*/
            context.Response.ContentType = "image/png";
            ODWebServiceEntities db = new ODWebServiceEntities();
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ sfdObj = db.getwebforms_sheetfielddef().Where(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(sfd) => {
                return sfd.WebSheetFieldDefID == WebSheetFieldDefID;
            }" */).First();
            SheetFieldType FieldType = (SheetFieldType)sfdObj.FieldType;
            Bitmap bmp = null;
            Graphics g = null;
            Pen p = new Pen(Color.Black, 2.0f);
            //1.0f does not show, this is a bug in the Drawing namespace.
            if (FieldType == SheetFieldType.Rectangle || FieldType == SheetFieldType.Line)
            {
                int bmpWidth = sfdObj.Width;
                int bmpHeight = sfdObj.Height;
                if (FieldType == SheetFieldType.Line)
                {
                    if (bmpWidth == 0 && bmpHeight != 0)
                    {
                        bmpWidth = 4;
                        //Increases the width of the "canvas" that the line will be drawn on.
                        p.Width += p.Width;
                    }
                     
                    //Double the width of the pen so that it looks like the same thickness of diagonal lines.  Half the line will be drawn on the image, half off.
                    if (bmpWidth != 0 && bmpHeight == 0)
                    {
                        bmpHeight = 4;
                        //Increases the height of the "canvas" that the line will be drawn on.
                        p.Width += p.Width;
                    }
                     
                }
                 
                //Double the width of the pen so that it looks like the same thickness of diagonal lines.  Half the line will be drawn on the image, half off.
                bmp = new Bitmap(bmpWidth, bmpHeight);
                g = Graphics.FromImage(bmp);
            }
             
            //g.Clear(Color.Transparent);
            if (FieldType == SheetFieldType.Rectangle)
            {
                g.DrawRectangle(p, 0, 0, sfdObj.Width, sfdObj.Height);
            }
             
            if (FieldType == SheetFieldType.Line)
            {
                g.DrawLine(p, 0, 0, sfdObj.Width, sfdObj.Height);
            }
             
            if ((SheetFieldType)sfdObj.FieldType == SheetFieldType.Image)
            {
                String ImageData = sfdObj.ImageData;
                bmp = PIn.bitmap(ImageData);
            }
             
            /*  These 3 lines are used in lue of the shorter "bmp.Save(context.Response.OutputStream,ImageFormat.Png);" because it does not work with png images.*/
            MemoryStream MemStream = new MemoryStream();
            bmp.Save(MemStream, System.Drawing.Imaging.ImageFormat.Png);
            MemStream.WriteTo(context.Response.OutputStream);
            if (FieldType == SheetFieldType.Rectangle || FieldType == SheetFieldType.Line)
            {
                g.Dispose();
            }
             
            bmp.Dispose();
        }
        catch (Exception ex)
        {
            Logger.logError("WebSheetFieldDefID=" + WebSheetFieldDefID,ex);
        }
    
    }

    public boolean getIsReusable() throws Exception {
        return false;
    }

}


