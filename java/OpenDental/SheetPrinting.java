//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.GraphicsHelper;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.SheetUtil;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetField;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.SheetTypeEnum;

public class SheetPrinting   
{
    ///<summary>If there is only one sheet, then this will stay 0.</Summary>
    private static int sheetsPrinted = new int();
    /**
    * If not a batch, then there will just be one sheet in the list.
    */
    private static List<Sheet> SheetList = new List<Sheet>();
    /**
    * Surround with try/catch.
    */
    public static void printBatch(List<Sheet> sheetBatch) throws Exception {
        //currently no validation for parameters in a batch because of the way it was created.
        //could validate field names here later.
        SheetList = sheetBatch;
        sheetsPrinted = 0;
        PrintDocument pd = new PrintDocument();
        pd.OriginAtMargins = true;
        pd.PrintPage += new PrintPageEventHandler(pd_PrintPage);
        if (sheetBatch[0].Width > 0 && sheetBatch[0].Height > 0)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("Default", sheetBatch[0].Width, sheetBatch[0].Height);
        }
         
        PrintSituation sit = PrintSituation.Default;
        pd.DefaultPageSettings.Landscape = sheetBatch[0].IsLandscape;
        SheetType __dummyScrutVar0 = sheetBatch[0].SheetType;
        if (__dummyScrutVar0.equals(SheetTypeEnum.LabelPatient) || __dummyScrutVar0.equals(SheetTypeEnum.LabelCarrier) || __dummyScrutVar0.equals(SheetTypeEnum.LabelReferral))
        {
            sit = PrintSituation.LabelSingle;
        }
        else if (__dummyScrutVar0.equals(SheetTypeEnum.ReferralSlip))
        {
            sit = PrintSituation.Default;
        }
          
        try
        {
            //later: add a check here for print preview.
            if (!PrinterL.SetPrinter(pd, sit, 0, "Batch of " + sheetBatch[0].Description + " printed"))
            {
                return ;
            }
             
            pd.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
            pd.Print();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    
    }

    //MessageBox.Show(Lan.g("Sheet","Printer not available"));
    public static void printRx(Sheet sheet, boolean isControlled) throws Exception {
        print(sheet,1,isControlled);
    }

    /**
    * Surround with try/catch.
    */
    public static void print(Sheet sheet) throws Exception {
        print(sheet,1,false);
    }

    /**
    * Surround with try/catch.
    */
    public static void print(Sheet sheet, int copies) throws Exception {
        print(sheet,copies,false);
    }

    /**
    * 
    */
    public static void print(Sheet sheet, int copies, boolean isRxControlled) throws Exception {
        //parameter null check moved to SheetFiller.
        //could validate field names here later.
        SheetList = new List<Sheet>();
        for (int i = 0;i < copies;i++)
        {
            SheetList.Add(sheet.copy());
        }
        sheetsPrinted = 0;
        PrintDocument pd = new PrintDocument();
        pd.OriginAtMargins = true;
        pd.PrintPage += new PrintPageEventHandler(pd_PrintPage);
        if (pd.DefaultPageSettings.PrintableArea.Width == 0)
        {
            //prevents bug in some printers that do not specify paper size
            pd.DefaultPageSettings.PaperSize = new PaperSize("paper", 850, 1100);
        }
         
        if (sheet.SheetType == SheetTypeEnum.LabelPatient || sheet.SheetType == SheetTypeEnum.LabelCarrier || sheet.SheetType == SheetTypeEnum.LabelAppointment || sheet.SheetType == SheetTypeEnum.LabelReferral)
        {
            //I think this causes problems for non-label sheet types.
            if (sheet.Width > 0 && sheet.Height > 0)
            {
                pd.DefaultPageSettings.PaperSize = new PaperSize("Default", sheet.Width, sheet.Height);
            }
             
        }
         
        PrintSituation sit = PrintSituation.Default;
        pd.DefaultPageSettings.Landscape = sheet.IsLandscape;
        switch(sheet.SheetType)
        {
            case LabelPatient: 
            case LabelCarrier: 
            case LabelReferral: 
            case LabelAppointment: 
                sit = PrintSituation.LabelSingle;
                break;
            case ReferralSlip: 
                sit = PrintSituation.Default;
                break;
            case Rx: 
                if (isRxControlled)
                {
                    sit = PrintSituation.RxControlled;
                }
                else
                {
                    sit = PrintSituation.Rx;
                } 
                break;
        
        }
        try
        {
            //later: add a check here for print preview.
            if (sheet.PatNum != null)
            {
                if (!PrinterL.setPrinter(pd,sit,sheet.PatNum,sheet.Description + " sheet from " + sheet.DateTimeSheet.ToShortDateString() + " printed"))
                {
                    return ;
                }
                 
            }
            else
            {
                if (!PrinterL.SetPrinter(pd, sit, 0, sheet.Description + " sheet from " + sheet.DateTimeSheet.ToShortDateString() + " printed"))
                {
                    return ;
                }
                 
            } 
            pd.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
            pd.Print();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    
    }

    //MessageBox.Show(Lan.g("Sheet","Printer not available"));
    private static void pd_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Graphics g = e.Graphics;
        g.SmoothingMode = SmoothingMode.HighQuality;
        Sheet sheet = SheetList[sheetsPrinted];
        SheetUtil.calculateHeights(sheet,g);
        //this is here because of easy access to g.
        Font font = new Font();
        FontStyle fontstyle = new FontStyle();
        for (Object __dummyForeachVar0 : sheet.SheetFields)
        {
            //first, draw images------------------------------------------------------------------------------------
            SheetField field = (SheetField)__dummyForeachVar0;
            if (field.FieldType != SheetFieldType.Image)
            {
                continue;
            }
             
            String filePathAndName = CodeBase.ODFileUtils.combinePaths(SheetUtil.getImagePath(),field.FieldName);
            Image img = null;
            //js consider switching this from an image to a bitmap
            if (StringSupport.equals(field.FieldName, "Patient Info.gif"))
            {
                img = Resources.getPatient_Info();
            }
            else if (File.Exists(filePathAndName))
            {
                img = Image.FromFile(filePathAndName);
            }
            else
            {
                continue;
            }  
            g.DrawImage(img, field.XPos, field.YPos, field.Width, field.Height);
            img.Dispose();
            img = null;
        }
        //then, drawings--------------------------------------------------------------------------------------------
        Pen pen = new Pen(Brushes.Black, 2f);
        String[] pointStr = new String[]();
        List<Point> points = new List<Point>();
        Point point = new Point();
        String[] xy = new String[]();
        for (Object __dummyForeachVar1 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar1;
            if (field.FieldType != SheetFieldType.Drawing)
            {
                continue;
            }
             
            pointStr = field.FieldValue.Split(';');
            points = new List<Point>();
            for (int p = 0;p < pointStr.Length;p++)
            {
                xy = pointStr[p].Split(',');
                if (xy.Length == 2)
                {
                    point = new Point(PIn.Int(xy[0]), PIn.Int(xy[1]));
                    points.Add(point);
                }
                 
            }
            for (int i = 1;i < points.Count;i++)
            {
                g.DrawLine(pen, points[i - 1].X, points[i - 1].Y, points[i].X, points[i].Y);
            }
        }
        //then, rectangles and lines----------------------------------------------------------------------------------
        Pen pen2 = new Pen(Brushes.Black, 1f);
        for (Object __dummyForeachVar2 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar2;
            if (field.FieldType == SheetFieldType.Rectangle)
            {
                g.DrawRectangle(pen2, field.XPos, field.YPos, field.Width, field.Height);
            }
             
            if (field.FieldType == SheetFieldType.Line)
            {
                g.DrawLine(pen2, field.XPos, field.YPos, field.XPos + field.Width, field.YPos + field.Height);
            }
             
        }
        //then, draw text-----------------------------------------------------------------------------------------------
        Bitmap doubleBuffer = new Bitmap(sheet.Width, sheet.Height);
        Graphics gfx = Graphics.FromImage(doubleBuffer);
        for (Object __dummyForeachVar3 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar3;
            if (field.FieldType != SheetFieldType.InputField && field.FieldType != SheetFieldType.OutputText && field.FieldType != SheetFieldType.StaticText)
            {
                continue;
            }
             
            fontstyle = FontStyle.Regular;
            if (field.FontIsBold)
            {
                fontstyle = FontStyle.Bold;
            }
             
            font = new Font(field.FontName, field.FontSize, fontstyle);
            Plugins.hookAddCode(null,"SheetPrinting.pd_PrintPage_drawFieldLoop",field);
            GraphicsHelper.DrawString(g, gfx, field.FieldValue, font, Brushes.Black, field.getBounds());
        }
        //g.DrawString(field.FieldValue,font,Brushes.Black,field.BoundsF);
        gfx.Dispose();
        //then, checkboxes----------------------------------------------------------------------------------
        Pen pen3 = new Pen(Brushes.Black, 1.6f);
        for (Object __dummyForeachVar4 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar4;
            if (field.FieldType != SheetFieldType.CheckBox)
            {
                continue;
            }
             
            if (StringSupport.equals(field.FieldValue, "X"))
            {
                g.DrawLine(pen3, field.XPos, field.YPos, field.XPos + field.Width, field.YPos + field.Height);
                g.DrawLine(pen3, field.XPos + field.Width, field.YPos, field.XPos, field.YPos + field.Height);
            }
             
        }
        for (Object __dummyForeachVar5 : sheet.SheetFields)
        {
            //then signature boxes----------------------------------------------------------------------
            SheetField field = (SheetField)__dummyForeachVar5;
            if (field.FieldType != SheetFieldType.SigBox)
            {
                continue;
            }
             
            OpenDental.UI.SignatureBoxWrapper wrapper = new OpenDental.UI.SignatureBoxWrapper();
            wrapper.Width = field.Width;
            wrapper.Height = field.Height;
            if (field.FieldValue.Length > 0)
            {
                //a signature is present
                boolean sigIsTopaz = false;
                if (field.FieldValue[0] == '1')
                {
                    sigIsTopaz = true;
                }
                 
                String signature = "";
                if (field.FieldValue.Length > 1)
                {
                    signature = field.FieldValue.Substring(1);
                }
                 
                String keyData = Sheets.getSignatureKey(sheet);
                wrapper.fillSignature(sigIsTopaz,keyData,signature);
            }
             
            Bitmap sigBitmap = wrapper.getSigImage();
            g.DrawImage(sigBitmap, field.XPos, field.YPos, field.Width - 2, field.Height - 2);
        }
        g.Dispose();
        //no logic yet for multiple pages on one sheet.
        sheetsPrinted++;
        //heightsCalculated=false;
        if (sheetsPrinted < SheetList.Count)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
            sheetsPrinted = 0;
        } 
    }

    public static void createPdf(Sheet sheet, String fullFileName) throws Exception {
        PdfDocument document = new PdfDocument();
        PdfPage page = document.AddPage();
        createPdfPage(sheet,page);
        document.Save(fullFileName);
    }

    public static void createPdfPage(Sheet sheet, PdfPage page) throws Exception {
        page.Width = p(sheet.Width);
        //XUnit.FromInch((double)sheet.Width/100);  //new XUnit((double)sheet.Width/100,XGraphicsUnit.Inch);
        page.Height = p(sheet.Height);
        //new XUnit((double)sheet.Height/100,XGraphicsUnit.Inch);
        if (sheet.IsLandscape)
        {
            page.Orientation = PageOrientation.Landscape;
        }
         
        XGraphics g = XGraphics.FromPdfPage(page);
        g.SmoothingMode = XSmoothingMode.HighQuality;
        //g.PageUnit=XGraphicsUnit. //wish they had pixel
        //XTextFormatter tf = new XTextFormatter(g);//needed for text wrap
        //tf.Alignment=XParagraphAlignment.Left;
        //pd.DefaultPageSettings.Landscape=
        //already done?:SheetUtil.CalculateHeights(sheet,g);//this is here because of easy access to g.
        XFont xfont = new XFont();
        XFontStyle xfontstyle = new XFontStyle();
        for (Object __dummyForeachVar6 : sheet.SheetFields)
        {
            //first, draw images--------------------------------------------------------------------------------------
            SheetField field = (SheetField)__dummyForeachVar6;
            if (field.FieldType != SheetFieldType.Image)
            {
                continue;
            }
             
            String filePathAndName = CodeBase.ODFileUtils.combinePaths(SheetUtil.getImagePath(),field.FieldName);
            Bitmap bitmapOriginal = null;
            if (StringSupport.equals(field.FieldName, "Patient Info.gif"))
            {
                bitmapOriginal = Resources.getPatient_Info();
            }
            else if (File.Exists(filePathAndName))
            {
                bitmapOriginal = new Bitmap(filePathAndName);
            }
            else
            {
                continue;
            }  
            Bitmap bitmapResampled = (Bitmap)bitmapOriginal.Clone();
            if (bitmapOriginal.HorizontalResolution != 96 || bitmapOriginal.VerticalResolution != 96)
            {
                //to avoid slowdown for other pdfs
                //The scaling on the XGraphics.DrawImage() function causes unreadable output unless the image is in 96 DPI native format.
                //We use GDI here first to convert the image to the correct size and DPI, then pass the second image to XGraphics.DrawImage().
                bitmapResampled.Dispose();
                bitmapResampled = null;
                bitmapResampled = new Bitmap(field.Width, field.Height);
                Graphics gr = Graphics.FromImage(bitmapResampled);
                gr.DrawImage(bitmapOriginal, 0, 0, field.Width, field.Height);
                gr.Dispose();
            }
             
            g.DrawImage(bitmapResampled, p(field.XPos), p(field.YPos), p(field.Width), p(field.Height));
            bitmapResampled.Dispose();
            bitmapResampled = null;
            bitmapOriginal.Dispose();
            bitmapOriginal = null;
        }
        //then, drawings--------------------------------------------------------------------------------------------
        XPen pen = new XPen(XColors.Black, p(2));
        String[] pointStr = new String[]();
        List<Point> points = new List<Point>();
        Point point = new Point();
        String[] xy = new String[]();
        for (Object __dummyForeachVar7 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar7;
            if (field.FieldType != SheetFieldType.Drawing)
            {
                continue;
            }
             
            pointStr = field.FieldValue.Split(';');
            points = new List<Point>();
            for (int j = 0;j < pointStr.Length;j++)
            {
                xy = pointStr[j].Split(',');
                if (xy.Length == 2)
                {
                    point = new Point(PIn.Int(xy[0]), PIn.Int(xy[1]));
                    points.Add(point);
                }
                 
            }
            for (int i = 1;i < points.Count;i++)
            {
                g.DrawLine(pen, p(points[i - 1].X), p(points[i - 1].Y), p(points[i].X), p(points[i].Y));
            }
        }
        //then, rectangles and lines----------------------------------------------------------------------------------
        XPen pen2 = new XPen(XColors.Black, p(1));
        for (Object __dummyForeachVar8 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar8;
            if (field.FieldType == SheetFieldType.Rectangle)
            {
                g.DrawRectangle(pen2, p(field.XPos), p(field.YPos), p(field.Width), p(field.Height));
            }
             
            if (field.FieldType == SheetFieldType.Line)
            {
                g.DrawLine(pen2, p(field.XPos), p(field.YPos), p(field.XPos + field.Width), p(field.YPos + field.Height));
            }
             
        }
        //then, draw text--------------------------------------------------------------------------------------------
        Bitmap doubleBuffer = new Bitmap(sheet.Width, sheet.Height);
        Graphics gfx = Graphics.FromImage(doubleBuffer);
        for (Object __dummyForeachVar9 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar9;
            if (field.FieldType != SheetFieldType.InputField && field.FieldType != SheetFieldType.OutputText && field.FieldType != SheetFieldType.StaticText)
            {
                continue;
            }
             
            xfontstyle = XFontStyle.Regular;
            if (field.FontIsBold)
            {
                xfontstyle = XFontStyle.Bold;
            }
             
            xfont = new XFont(field.FontName, field.FontSize, xfontstyle);
            //xfont=new XFont(field.FontName,field.FontSize,xfontstyle);
            //Rectangle rect=new Rectangle((int)p(field.XPos),(int)p(field.YPos),(int)p(field.Width),(int)p(field.Height));
            XRect xrect = new XRect(p(field.XPos), p(field.YPos), p(field.Width), p(field.Height));
            //XStringFormat format=new XStringFormat();
            //tf.DrawString(field.FieldValue,font,XBrushes.Black,xrect,XStringFormats.TopLeft);
            GraphicsHelper.DrawStringX(g, gfx, 1d / p(1), field.FieldValue, xfont, XBrushes.Black, xrect);
        }
        gfx.Dispose();
        //then, checkboxes----------------------------------------------------------------------------------
        XPen pen3 = new XPen(XColors.Black, p(1.6f));
        for (Object __dummyForeachVar10 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar10;
            if (field.FieldType != SheetFieldType.CheckBox)
            {
                continue;
            }
             
            if (StringSupport.equals(field.FieldValue, "X"))
            {
                g.DrawLine(pen3, p(field.XPos), p(field.YPos), p(field.XPos + field.Width), p(field.YPos + field.Height));
                g.DrawLine(pen3, p(field.XPos + field.Width), p(field.YPos), p(field.XPos), p(field.YPos + field.Height));
            }
             
        }
        for (Object __dummyForeachVar11 : sheet.SheetFields)
        {
            //then signature boxes----------------------------------------------------------------------
            SheetField field = (SheetField)__dummyForeachVar11;
            if (field.FieldType != SheetFieldType.SigBox)
            {
                continue;
            }
             
            OpenDental.UI.SignatureBoxWrapper wrapper = new OpenDental.UI.SignatureBoxWrapper();
            wrapper.Width = field.Width;
            wrapper.Height = field.Height;
            if (field.FieldValue.Length > 0)
            {
                //a signature is present
                boolean sigIsTopaz = false;
                if (field.FieldValue[0] == '1')
                {
                    sigIsTopaz = true;
                }
                 
                String signature = "";
                if (field.FieldValue.Length > 1)
                {
                    signature = field.FieldValue.Substring(1);
                }
                 
                String keyData = Sheets.getSignatureKey(sheet);
                wrapper.fillSignature(sigIsTopaz,keyData,signature);
            }
             
            XImage sigBitmap = XImage.FromGdiPlusImage(wrapper.getSigImage());
            g.DrawImage(sigBitmap, p(field.XPos), p(field.YPos), p(field.Width - 2), p(field.Height - 2));
        }
    }

    /*//<summary>Converts pixels used by us to points used by PdfSharp.</summary>
    		private double P(double pixels){
    			return (double)pixels/100;
    		}*/
    /**
    * Converts pixels used by us to points used by PdfSharp.
    */
    private static double p(int pixels) throws Exception {
        XUnit xunit = XUnit.FromInch((double)pixels / 100d);
        return xunit.Point;
    }

    //100 ppi
    //XUnit.FromInch((double)pixels/100);
    /**
    * Converts pixels used by us to points used by PdfSharp.
    */
    private static double p(float pixels) throws Exception {
        XUnit xunit = XUnit.FromInch((double)pixels / 100d);
        return xunit.Point;
    }

}


//100 ppi