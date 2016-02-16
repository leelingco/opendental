//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;

//  EZTwain for C#.
//  XDefs translation of \EZTwain4\VC\Eztwain.h
public abstract class EZTwain   
{
    /**
    * Gets the EZImage as .Net image object.
    * 
    *  @return .Net image object.
    */
    public static System.Drawing.Image dIB_ToImage(System.IntPtr hdib) throws Exception {
        System.Drawing.Image convertedImage = null;
        if (hdib != System.IntPtr.Zero)
        {
            byte[] buffer = new byte[dIB_Size(hdib) + 100];
            DIB_WriteToBuffer(hdib, EZT_FF_BMP, buffer, buffer.Length);
            System.IO.MemoryStream memoryStream = new System.IO.MemoryStream(buffer, false);
            System.Drawing.Image temporaryBitmap = System.Drawing.Bitmap.FromStream(((System.IO.Stream)memoryStream));
            convertedImage = (System.Drawing.Image)temporaryBitmap.Clone();
            temporaryBitmap.Dispose();
        }
         
        return convertedImage;
    }

    public static System.IntPtr dIB_FromStream(System.IO.Stream stream) throws Exception {
        System.IntPtr hdib = System.IntPtr.Zero;
        byte[] buffer = new byte[stream.Length];
        stream.Read(buffer, 0, buffer.Length);
        hdib = DIB_LoadFromBuffer(buffer, buffer.Length);
        return hdib;
    }

    public static System.IntPtr dIB_FromImage(System.Drawing.Image image) throws Exception {
        System.IO.MemoryStream imgStream = new System.IO.MemoryStream();
        image.Save(imgStream, System.Drawing.Imaging.ImageFormat.Bmp);
        imgStream.Seek(0, System.IO.SeekOrigin.Begin);
        return DIB_FromStream(imgStream);
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr testing123(String s, int n, System.IntPtr h, double d, int u) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void resetAll() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr acquire(System.IntPtr hwndApp) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean selectImageSource(System.IntPtr hwnd) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int acquireToFilename(System.IntPtr hwndApp, String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int acquireMultipageFile(System.IntPtr hwndApp, String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int acquireToArray(System.IntPtr hwnd, System.IntPtr[] ahdib, int nMax) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int acquireImagesToFiles(System.IntPtr hwndApp, String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int acquirePagesToFiles(System.IntPtr hwnd, int nPPF, String sFile) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int acquireMultipage(System.IntPtr hwnd) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int acquiredFileCount() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr acquireCompressed(System.IntPtr hwndApp) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int acquireCount() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean promptToContinue(System.IntPtr hwnd) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setScanAnotherPagePrompt(String sPrompt) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int setDefaultScanAnotherPagePrompt(int fYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int doSettingsDialog(System.IntPtr hwnd) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean enableSourceUiOnly(System.IntPtr hwnd) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setMultiTransfer(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getMultiTransfer() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setHideUI(boolean bHide) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getHideUI() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setStopOnEmpty(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getStopOnEmpty() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void disableParent(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getDisableParent() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int easyVersion() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int easyBuild() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isAvailable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isMultipageAvailable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int state() throws Exception ;

    public static final int TWAIN_PRESESSION = 1;
    public static final int TWAIN_SM_LOADED = 2;
    public static final int TWAIN_SM_OPEN = 3;
    public static final int TWAIN_SOURCE_OPEN = 4;
    public static final int TWAIN_SOURCE_ENABLED = 5;
    public static final int TWAIN_TRANSFER_READY = 6;
    public static final int TWAIN_TRANSFERRING = 7;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isDone() throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr sourceNamePtr() throws Exception ;

    public static String sourceName() throws Exception {
        return Marshal.PtrToStringAnsi(sourceNamePtr());
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void getSourceName(StringBuilder sName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_IsValid(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_Depth(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_BitsPerPixel(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_PixelType(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_Width(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_Height(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetResolution(System.IntPtr hdib, double xdpi, double ydpi) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetResolutionInt(System.IntPtr hdib, int xdpi, int ydpi) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double dIB_XResolution(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double dIB_YResolution(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_XResolutionInt(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_YResolutionInt(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double dIB_PhysicalWidth(System.IntPtr hdib, int nUnits) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double dIB_PhysicalHeight(System.IntPtr hdib, int nUnits) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_RowBytes(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_ColorCount(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_SamplesPerPixel(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_BitsPerSample(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_IsCompressed(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_Compression(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_Size(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ReadData(System.IntPtr hdib, byte[] pdata, int nbMax) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ReadRow(System.IntPtr hdib, int r, byte[] prow) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ReadRowRGB(System.IntPtr hdib, int r, byte[] prow) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ReadRowGray(System.IntPtr hdib, int r, byte[] prow) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ReadRowChannel(System.IntPtr hdib, int r, byte[] prow, int nChannel) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ReadRowSample(System.IntPtr hdib, int r, byte[] prow, int nSample) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ReadPixelRGB(System.IntPtr hdib, int x, int y, byte[] buffer) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ReadPixelGray(System.IntPtr hdib, int x, int y, byte[] buffer) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ReadPixelChannel(System.IntPtr hdib, int x, int y, byte[] buffer, int nChannel) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_WriteRow(System.IntPtr hdib, int r, byte[] pdata) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_WriteRowChannel(System.IntPtr hdib, int r, byte[] pdata, int nChannel) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_WriteRowSample(System.IntPtr hdib, int r, byte[] psrc, int nSample) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_WriteRowSafe(System.IntPtr hdib, int r, byte[] pdata, int nbMax) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ReadRowSafe(System.IntPtr hdib, int nRow, byte[] prow, int nbMax) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_Allocate(int nDepth, int nWidth, int nHeight) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_Create(int nPixelType, int nWidth, int nHeight, int nDepth) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_Free(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_FreeArray(System.IntPtr[] ahdib, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_InUseCount() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_Copy(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_Equal(System.IntPtr hdib1, System.IntPtr hdib2) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_AlmostEqual(System.IntPtr hdib1, System.IntPtr hdib2, int nMaxErr) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_MaxError(System.IntPtr hdib1, System.IntPtr hdib2) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetGrayColorTable(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetColorTableRGB(System.IntPtr hdib, int i, int R, int G, int B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_IsVanilla(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_IsChocolate(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_ColorTableR(System.IntPtr hdib, int i) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_ColorTableG(System.IntPtr hdib, int i) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_ColorTableB(System.IntPtr hdib, int i) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_FlipVertical(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_FlipHorizontal(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_Rotate180(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_Rotate90(System.IntPtr hOld, int nSteps) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_InPlaceRotate90(System.IntPtr hdib, int nSteps) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_Fill(System.IntPtr hdib, int R, int G, int B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_FillRectWithColor(System.IntPtr hdib, int x, int y, int w, int h, int R, int G, int B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_FillRectWithColorAlpha(System.IntPtr hdib, int x, int y, int w, int h, int R, int G, int B, int A) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_Negate(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_AdjustBC(System.IntPtr hdib, int nB, int nC) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ApplyToneMap8(System.IntPtr hdib, byte[] map) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_AutoContrast(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_Convolve(System.IntPtr hdibDst, System.IntPtr hdibKernel, double dNorm, int nOffset) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_Correlate(System.IntPtr hdibDst, System.IntPtr hdibKernel) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_CrossCorrelate(System.IntPtr hdibDst, System.IntPtr hdibTemplate, double dScale, int nMin) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_HorizontalDifference(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_HorizontalCorrelation(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_VerticalCorrelation(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_MedianFilter(System.IntPtr hdib, int W, int H, int nStyle) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_MeanFilter(System.IntPtr hdib, int W, int H) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_Smooth(System.IntPtr hdib, double sigma, double opacity) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_Sobel(System.IntPtr hdib, int mode, int Thresh) throws Exception ;

    public static final int SOBEL_HORIZONTAL = 0;
    public static final int SOBEL_VERTICAL = 1;
    public static final int SOBEL_SUM = 2;
    public static final int SOBEL_MAX = 3;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_ScaledCopy(System.IntPtr hOld, int w, int h) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_Resize(System.IntPtr hdib, int w, int h) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_ScaleToGray(System.IntPtr hdibOld, int nRatio) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_Thumbnail(System.IntPtr hdibSource, int MaxWidth, int MaxHeight) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_Resample(System.IntPtr hOld, double xdpi, double ydpi) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_RegionCopy(System.IntPtr hOld, int leftx, int topy, int w, int h, int FillByte) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_AutoCrop(System.IntPtr hOld, int nOpts) throws Exception ;

    public static final int AUTOCROP_DARK = 1;
    public static final int AUTOCROP_LIGHT = 2;
    public static final int AUTOCROP_EDGE = 4;
    public static final int AUTOCROP_CHECK = 8;
    public static final int AUTOCROP_CHECK_BACK = 16;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_GetCropRect(System.IntPtr hdib, int nOptions, RefSupport<int> cropx, RefSupport<int> cropy, RefSupport<int> cropw, RefSupport<int> croph) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_AutoDeskew(System.IntPtr hOld, int nOptions) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double dIB_DeskewAngle(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SkewByDegrees(System.IntPtr hdib, double dAngle) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_ConvertToPixelType(System.IntPtr hOld, int nPT) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_ConvertToFormat(System.IntPtr hOld, int nPT, int nBPP) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_SmartThreshold(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_SimpleThreshold(System.IntPtr hdib, int nT) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_SetConversionThreshold(int nT) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_ConversionThreshold() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_FindAdaptiveGlobalThreshold(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_ErrorDiffuse(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetConversionColorCount(int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_ConversionColorCount() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SwapRedBlue(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_CreatePalette(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetColorModel(System.IntPtr hdib, int nCM) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_ColorModel(System.IntPtr hdib) throws Exception ;

    public static final int EZT_CM_RGB = 0;
    public static final int EZT_CM_GRAY = 3;
    public static final int EZT_CM_CMYK = 5;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetColorCount(System.IntPtr hdib, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_Blt(System.IntPtr hdibDst, int dx, int dy, System.IntPtr hdibSrc, int sx, int sy, int w, int h, int uRop) throws Exception ;

    public static final int EZT_ROP_COPY = 0;
    public static final int EZT_ROP_OR = 1;
    public static final int EZT_ROP_AND = 2;
    public static final int EZT_ROP_XOR = 3;
    public static final int EZT_ROP_ANDNOT = 0x12;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_BltMask(System.IntPtr hdibDst, int dx, int dy, System.IntPtr hdibSrc, int sx, int sy, int w, int h, int uRop, System.IntPtr hdibMask) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_PaintMask(System.IntPtr hdibDst, int dx, int dy, int R, int G, int B, int sx, int sy, int w, int h, int uRop, System.IntPtr hdibMask) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_DrawLine(System.IntPtr hdibDst, int x1, int y1, int x2, int y2, int R, int G, int B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_DrawText(System.IntPtr hdibDst, String sText, int leftx, int topy, int w, int h) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_ResetTextDrawing() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetTextColor(int R, int G, int B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_TextColor() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_GetTextColor(RefSupport<int> pR, RefSupport<int> pG, RefSupport<int> pB) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetTextAngle(int nDegrees) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetTextHeight(int nH) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetTextFace(String sTypeface) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetTextFormat(int nFlags) throws Exception ;

    public static final int EZT_TEXT_NORMAL = 0x0;
    public static final int EZT_TEXT_BOLD = 0x1;
    public static final int EZT_TEXT_ITALIC = 0x2;
    public static final int EZT_TEXT_UNDERLINE = 0x4;
    public static final int EZT_TEXT_STRIKEOUT = 0x8;
    public static final int EZT_TEXT_BOTTOM = 0x100;
    public static final int EZT_TEXT_VCENTER = 0x200;
    public static final int EZT_TEXT_TOP = 0x0;
    public static final int EZT_TEXT_LEFT = 0x0;
    public static final int EZT_TEXT_CENTER = 0x1000;
    public static final int EZT_TEXT_RIGHT = 0x2000;
    public static final int EZT_TEXT_WRAP = 0x4000;
    public static final int EZT_TEXT_JUSTIFY = 0x800;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetTextBackgroundColor(int R, int G, int B, int A) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_View(System.IntPtr hdib, String sTitle, System.IntPtr hwndParent) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_SetViewOption(String sOption, String sValue) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_SetViewImage(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_IsViewOpen() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_ViewClose() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_DrawOnWindow(System.IntPtr hdib, System.IntPtr hwnd) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_DrawToDC(System.IntPtr hdib, System.IntPtr hDC, int dx, int dy, int w, int h, int sx, int sy) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_SpecifyPrinter(String sPrinterName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_EnumeratePrinters() throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_PrinterNamePtr(int i) throws Exception ;

    public static String dIB_PrinterName(int i) throws Exception {
        return Marshal.PtrToStringAnsi(dIB_PrinterNamePtr(i));
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_GetPrinterName(int i, StringBuilder PrinterName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetPrintToFit(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_GetPrintToFit() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_Print(System.IntPtr hdib, String sJobname) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_PrintNoPrompt(System.IntPtr hdib, String sJobname) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int printFile(String sFilename, String sJobname, boolean bNoPrompt) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_PrintFile(String sFilename, String sJobname, boolean bNoPrompt) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_PrintArray(System.IntPtr[] ahdib, int nCount, String sJobname, boolean bNoPrompt) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SetNextPrintJobPageCount(int nPages) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_PrintJobBegin(String sJobname, boolean bUseDefaultPrinter) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_PrintPage(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_PrintJobEnd() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_PutOnClipboard(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_CanGetFromClipboard() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_GetFromClipboard() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_FromClipboard() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_OpenInDC(System.IntPtr hdib, System.IntPtr hdc) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_CloseInDC(System.IntPtr hdib, System.IntPtr hdc) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_WriteToFilename(System.IntPtr hdib, String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_LoadFromFilename(String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_FormatOfFile(String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_SelectPageToLoad(int nPage) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_GetFilePageCount(String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_FilePageCount(String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_LoadPage(String sFileName, int nPage) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_LoadArrayFromFilename(System.IntPtr[] ahdib, int nMax, String sFilename) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_LoadPagesFromFilename(System.IntPtr[] ahdib, int index0, int nMax, String sFilename) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_FormatOfBuffer(byte[] pBuffer, int nBytes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_PageCountOfBuffer(byte[] pBuffer, int nBytes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_BufferPageCount(byte[] pBuffer, int nBytes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_LoadFromBuffer(byte[] pBuffer, int nBytes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_LoadPageFromBuffer(byte[] pBuffer, int nBytes, int nPage) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_LoadArrayFromBuffer(System.IntPtr[] ahdib, int nMax, byte[] pBuffer, int nBytes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_LoadFaxData(System.IntPtr hdib, byte[] pBuffer, int nBytes, int nFlags) throws Exception ;

    public static final int FAX_GROUP3_2D = 0x20;
    public static final int FAX_GROUP4 = 0x40;
    public static final int FAX_BYTE_ALIGNED = 0x80;
    public static final int FAX_REQUIRE_EOLS = 0x100;
    public static final int FAX_EXPECT_EOB = 0x200;
    public static final int FAX_VANILLA = 0x400;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_WriteToBuffer(System.IntPtr hdib, int nFormat, byte[] pBuffer, int nbMax) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_WriteArrayToBuffer(System.IntPtr[] ahdib, int n, int nFormat, byte[] pBuffer, int nbMax) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_ToDibSection(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_FromBitmap(System.IntPtr hbm, System.IntPtr hdc) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dIB_IsBlank(System.IntPtr hdib, double dDarkness) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double dIB_Darkness(System.IntPtr hdibFull) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dIB_GetHistogram(System.IntPtr hdib, int nComponent, int[] histo) throws Exception ;

    public static final int COMPONENT_GRAY = 0;
    public static final int COMPONENT_RED = 1;
    public static final int COMPONENT_GREEN = 2;
    public static final int COMPONENT_BLUE = 3;
    public static final int COMPONENT_LUMINANCE = 0;
    public static final int COMPONENT_SAT = 4;
    public static final int COMPONENT_HUE = 5;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_ComponentCopy(System.IntPtr hdib, int nComponent) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double dIB_Avg(System.IntPtr hdib, int nComp) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double dIB_AvgRegion(System.IntPtr hdib, int nComp, int leftx, int topy, int w, int h) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double dIB_AvgRow(System.IntPtr hdib, int nComp, int rowy) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double dIB_AvgColumn(System.IntPtr hdib, int nComp, int colx) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_GetBrightRects(System.IntPtr hdib, int w, int h, int t, int[] xBlob, int[] yBlob, int[] wBlob, int[] hBlob, int nMax) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_ProjectRows(System.IntPtr hdib, int nComp) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_ProjectColumns(System.IntPtr hdib, int leftx, int topy, int w, int h, int nComp) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_Posterize(System.IntPtr hdib, int nLevels) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dIB_ForwardDCT(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dOC_CreateEmpty() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dOC_Destroy(System.IntPtr hdoc) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dOC_ImageCount(System.IntPtr hdoc) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_IsModified(System.IntPtr hdoc) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dOC_SetModified(System.IntPtr hdoc, boolean bIsMod) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dOC_FilenamePtr(System.IntPtr hdoc) throws Exception ;

    public static String dOC_Filename(System.IntPtr hdoc) throws Exception {
        return Marshal.PtrToStringAnsi(dOC_FilenamePtr(hdoc));
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_SetCurPos(System.IntPtr hdoc, int i) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dOC_CurPos(System.IntPtr hdoc) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dOC_OpenReadOnly(String sFilename) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dOC_OpenForUpdate(String sFilename) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void dOC_Reset(System.IntPtr hdoc) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_WriteToFile(System.IntPtr hdoc, String filename) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_Save(System.IntPtr hdoc) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_SaveAs(System.IntPtr hdoc, String filename) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dOC_Image(System.IntPtr hdoc, int i) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_SetImage(System.IntPtr hdoc, int i, System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_AppendImage(System.IntPtr hdoc, System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr dOC_ExtractImages(System.IntPtr hdoc, int i, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_DeleteImage(System.IntPtr hdoc, int i) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_DeleteImages(System.IntPtr hdoc, int i, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_InsertImage(System.IntPtr hdoc, int i, System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_InsertImageArray(System.IntPtr hdoc, int i, System.IntPtr[] ahdib, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dOC_MoveImage(System.IntPtr hdoc, int iOld, int iNew) throws Exception ;

    public static final int EZT_FF_TIFF = 0;
    public static final int EZT_FF_BMP = 2;
    public static final int EZT_FF_JFIF = 4;
    public static final int EZT_FF_PNG = 7;
    public static final int EZT_FF_PDFA = 15;
    public static final int EZT_FF_DCX = 97;
    public static final int EZT_FF_GIF = 98;
    public static final int EZT_FF_PDF = 99;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setFileAppendFlag(boolean bAppend) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getFileAppendFlag() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isJpegAvailable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isPngAvailable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isTiffAvailable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isPdfAvailable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isGifAvailable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isDcxAvailable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isFormatAvailable(int nFF) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int formatVersion(int nFF) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isFileExtensionAvailable(String sExt) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int formatFromExtension(String sExt, int nFF) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr extensionFromFormatPtr(int nFF, String sDefExt) throws Exception ;

    public static String extensionFromFormat(int nFF, String sDefExt) throws Exception {
        return Marshal.PtrToStringAnsi(extensionFromFormatPtr(nFF,sDefExt));
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void getExtensionFromFormat(int nFF, String sDefExt, StringBuilder szExtension) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setSaveFormat(int nFF) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getSaveFormat() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setJpegQuality(int nQ) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getJpegQuality() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setTiffStripSize(int nBytes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getTiffStripSize() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiffImageDescription(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiffDocumentName(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiffCompression(int nPT, int nComp) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getTiffCompression(int nPT) throws Exception ;

    public static final int TIFF_COMP_NONE = 1;
    public static final int TIFF_COMP_CCITTRLE = 2;
    public static final int TIFF_COMP_CCITTFAX3 = 3;
    public static final int TIFF_COMP_CCITTFAX4 = 4;
    public static final int TIFF_COMP_LZW = 5;
    public static final int TIFF_COMP_JPEG = 7;
    public static final int TIFF_COMP_PACKBITS = 32773;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiffTagShort(int nTagId, int sValue) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiffTagLong(int nTagId, int nValue) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiffTagString(int nTagId, String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiffTagDouble(int nTagId, double dValue) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiffTagRational(int nTagId, double dValue) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiffTagRationalArray(int nTagId, double[] dValues, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiffTagBytes(int nTagId, byte[] pdata, int nBytes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiffTagUndefined(int nTagId, byte[] pdata, int nBytes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void resetTiffTags() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getTiffTagAscii(String sFilename, int nPage, int nTag, int nLen, StringBuilder buffer) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr tiffTagAsciiPtr(String sFilename, int nPage, int nTag) throws Exception ;

    public static String tiffTagAscii(String sFilename, int nPage, int nTag) throws Exception {
        return Marshal.PtrToStringAnsi(tiffTagAsciiPtr(sFilename,nPage,nTag));
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_IsOneOfOurs(String sFileName) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr pDF_DocumentPropertyPtr(String sFilename, String sProperty) throws Exception ;

    public static String pDF_DocumentProperty(String sFilename, String sProperty) throws Exception {
        return Marshal.PtrToStringAnsi(pDF_DocumentPropertyPtr(sFilename,sProperty));
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int pDF_GetDocumentProperty(String sFilename, String sProperty, StringBuilder buffer, int buflen) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setPdfTitle(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setPdfAuthor(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setPdfSubject(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setPdfKeywords(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setPdfCreator(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_SetTitle(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_SetAuthor(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_SetSubject(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_SetKeywords(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_SetCreator(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_SetCompression(int nPT, int nComp) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int pDF_GetCompression(int nPT) throws Exception ;

    public static final int COMPRESSION_DEFAULT = -1;
    public static final int COMPRESSION_NONE = 1;
    public static final int COMPRESSION_FLATE = 5;
    public static final int COMPRESSION_JPEG = 7;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_SelectPageSize(int nPaper) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int pDF_SelectedPageSize() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_SetPDFACompliance(int nLevel) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int pDF_GetPDFACompliance() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_SetAutoSearchable(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_GetAutoSearchable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_IsEncrypted(String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void pDF_SetOpenPassword(String sPassword) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void pDF_SetUserPassword(String sPassword) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void pDF_SetOwnerPassword(String sPassword) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void pDF_SetPermissions(int nPermission) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int pDF_GetPermissions() throws Exception ;

    public static final int PDF_PERMIT_PRINT = 4;
    public static final int PDF_PERMIT_MODIFY = 8;
    public static final int PDF_PERMIT_COPY = 16;
    public static final int PDF_PERMIT_ANNOTS = 32;
    public static final int PDF_PERMIT_ALL = -1;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void pDF_DrawText(double leftx, double topy, String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void pDF_DrawInvisibleText(double leftx, double topy, String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void pDF_SetTextVisible(boolean bVisible) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_GetTextVisible() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void pDF_SetTextSize(double dfs) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void pDF_SetTextHorizontalScaling(double dhs) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean pDF_WriteOcrText(String text, int[] ax, int[] ay, int[] aw, int[] ah, double xdpi, double ydpi) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int formatOfFile(String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int pagesInFile(String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean promptForOpenFilename(StringBuilder sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int viewFile(String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setViewOption(String sOption, String sValue) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isViewOpen() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean viewClose() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getLastViewPosition(RefSupport<int> pleft, RefSupport<int> ptop, RefSupport<int> pwidth, RefSupport<int> pheight) throws Exception ;

    public static final int MULTIPAGE_TIFF = 0;
    public static final int MULTIPAGE_PDF = 1;
    public static final int MULTIPAGE_DCX = 2;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int setMultipageFormat(int nFF) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getMultipageFormat() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setLazyWriting(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getLazyWriting() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dIB_WriteArrayToFilename(System.IntPtr[] ahdib, int n, String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int beginMultipageFile(String sFileName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int dibWritePage(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int writePageAndFree(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int endMultipageFile() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int multipageCount() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isMultipageFileOpen() throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr lastOutputFilePtr() throws Exception ;

    public static String lastOutputFile() throws Exception {
        return Marshal.PtrToStringAnsi(lastOutputFilePtr());
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setOutputPageCount(int nPages) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int fileCopy(String sInFile, String sOutFile, int nOptions) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean uPLOAD_IsAvailable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int uPLOAD_Version() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int uPLOAD_MaxFiles() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean uPLOAD_AddFormField(String fieldName, String fieldValue) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean uPLOAD_AddHeader(String header) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean uPLOAD_AddCookie(String cookie) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void uPLOAD_EnableProgressBar(boolean bEnable) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean uPLOAD_IsEnabledProgressBar() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int uPLOAD_DibToURL(System.IntPtr hdib, String URL, String fileName, String fieldName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int uPLOAD_DibsToURL(System.IntPtr[] ahdib, int n, String URL, String fileName, String fieldName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int uPLOAD_DibsSeparatelyToURL(System.IntPtr[] ahdib, int n, String URL, String fileName, String fieldName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int uPLOAD_FilesToURL(String files, String URL, String fieldName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void uPLOAD_SetProxy(String hostport, String userpwd, boolean bTunnel) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr uPLOAD_ResponsePtr() throws Exception ;

    public static String uPLOAD_Response() throws Exception {
        return Marshal.PtrToStringAnsi(uPLOAD_ResponsePtr());
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int uPLOAD_ResponseLength() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void uPLOAD_GetResponse(StringBuilder ResponseText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void uPLOAD_ClearResponse() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setAppTitle(String sAppTitle) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setApplicationKey(int nKey) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void applicationLicense(String sAppTitle, int nAppKey) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void universalLicense(String sLicensee, int nKey) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void inHouseApplicationLicense(String sLicensee, int nKey) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean renewTrialLicense(int uKey) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean singleMachineLicense(String sMsg) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void registerApp(int nMajorNum, int nMinorNum, int nLanguage, int nCountry, String sVersion, String sMfg, String sFamily, String sAppTitle) throws Exception ;

    public static final int EZTEC_NONE = 0;
    public static final int EZTEC_START_TRIPLET_ERRS = 1;
    public static final int EZTEC_CAP_GET = 2;
    public static final int EZTEC_CAP_SET = 3;
    public static final int EZTEC_DSM_FAILURE = 4;
    public static final int EZTEC_DS_FAILURE = 5;
    public static final int EZTEC_XFER_FAILURE = 6;
    public static final int EZTEC_END_TRIPLET_ERRS = 7;
    public static final int EZTEC_OPEN_DSM = 8;
    public static final int EZTEC_OPEN_DEFAULT_DS = 9;
    public static final int EZTEC_NOT_STATE_4 = 10;
    public static final int EZTEC_NULL_HCON = 11;
    public static final int EZTEC_BAD_HCON = 12;
    public static final int EZTEC_BAD_CONTYPE = 13;
    public static final int EZTEC_BAD_ITEMTYPE = 14;
    public static final int EZTEC_CAP_GET_EMPTY = 15;
    public static final int EZTEC_CAP_SET_EMPTY = 16;
    public static final int EZTEC_INVALID_HWND = 17;
    public static final int EZTEC_PROXY_WINDOW = 18;
    public static final int EZTEC_USER_CANCEL = 19;
    public static final int EZTEC_RESOLUTION = 20;
    public static final int EZTEC_LICENSE = 21;
    public static final int EZTEC_JPEG_DLL = 22;
    public static final int EZTEC_SOURCE_EXCEPTION = 23;
    public static final int EZTEC_LOAD_DSM = 24;
    public static final int EZTEC_NO_SUCH_DS = 25;
    public static final int EZTEC_OPEN_DS = 26;
    public static final int EZTEC_ENABLE_FAILED = 27;
    public static final int EZTEC_BAD_MEMXFER = 28;
    public static final int EZTEC_JPEG_GRAY_OR_RGB = 29;
    public static final int EZTEC_JPEG_BAD_Q = 30;
    public static final int EZTEC_BAD_DIB = 31;
    public static final int EZTEC_BAD_FILENAME = 32;
    public static final int EZTEC_FILE_NOT_FOUND = 33;
    public static final int EZTEC_FILE_ACCESS = 34;
    public static final int EZTEC_MEMORY = 35;
    public static final int EZTEC_JPEG_ERR = 36;
    public static final int EZTEC_JPEG_ERR_REPORTED = 37;
    public static final int EZTEC_0_PAGES = 38;
    public static final int EZTEC_UNK_WRITE_FF = 39;
    public static final int EZTEC_NO_TIFF = 40;
    public static final int EZTEC_TIFF_ERR = 41;
    public static final int EZTEC_PDF_WRITE_ERR = 42;
    public static final int EZTEC_NO_PDF = 43;
    public static final int EZTEC_GIFCON = 44;
    public static final int EZTEC_FILE_READ_ERR = 45;
    public static final int EZTEC_BAD_REGION = 46;
    public static final int EZTEC_FILE_WRITE = 47;
    public static final int EZTEC_NO_DS_OPEN = 48;
    public static final int EZTEC_DCXCON = 49;
    public static final int EZTEC_NO_BARCODE = 50;
    public static final int EZTEC_UNK_READ_FF = 51;
    public static final int EZTEC_DIB_FORMAT = 52;
    public static final int EZTEC_PRINT_ERR = 53;
    public static final int EZTEC_NO_DCX = 54;
    public static final int EZTEC_APP_BAD_CON = 55;
    public static final int EZTEC_LIC_KEY = 56;
    public static final int EZTEC_INVALID_PARAM = 57;
    public static final int EZTEC_INTERNAL = 58;
    public static final int EZTEC_LOAD_DLL = 59;
    public static final int EZTEC_CURL = 60;
    public static final int EZTEC_MULTIPAGE_OPEN = 61;
    public static final int EZTEC_BAD_SHUTDOWN = 62;
    public static final int EZTEC_DLL_VERSION = 63;
    public static final int EZTEC_OCR_ERR = 64;
    public static final int EZTEC_ONLY_TO_PDF = 65;
    public static final int EZTEC_APP_TITLE = 66;
    public static final int EZTEC_PATH_CREATE = 67;
    public static final int EZTEC_LATE_LIC = 68;
    public static final int EZTEC_PDF_PASSWORD = 69;
    public static final int EZTEC_PDF_UNSUPPORTED = 70;
    public static final int EZTEC_PDF_BAFFLED = 71;
    public static final int EZTEC_PDF_INVALID = 72;
    public static final int EZTEC_PDF_COMPRESSION = 73;
    public static final int EZTEC_NOT_ENOUGH_PAGES = 74;
    public static final int EZTEC_DIB_ARRAY_OVERFLOW = 75;
    public static final int EZTEC_DEVICE_PAPERJAM = 76;
    public static final int EZTEC_DEVICE_DOUBLEFEED = 77;
    public static final int EZTEC_DEVICE_COMM = 78;
    public static final int EZTEC_DEVICE_INTERLOCK = 79;
    public static final int EZTEC_BAD_DOC = 80;
    public static final int EZTEC_OTHER_DS_OPEN = 81;
    public static final int EZTEC_LIC_NO_LICENSEE = 82;
    public static final int EZTEC_LIC_NO_UKEY = 83;
    public static final int EZTEC_LIC_NO_APPNAME = 84;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getResultCode() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getConditionCode() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean userClosedSource() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void errorBox(String sMsg) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean suppressErrorMessages(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void reportLastError(String msg) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void getLastErrorText(StringBuilder sMsg) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr lastErrorTextPtr() throws Exception ;

    public static String lastErrorText() throws Exception {
        return Marshal.PtrToStringAnsi(lastErrorTextPtr());
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int lastErrorCode() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void clearError() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void recordError(int code, String note) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean reportLeaks() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void shutdown() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean loadSourceManager() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean openSourceManager(System.IntPtr hwnd) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean openDefaultSource() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getSourceList() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getNextSourceName(StringBuilder sName) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr nextSourceNamePtr() throws Exception ;

    public static String nextSourceName() throws Exception {
        return Marshal.PtrToStringAnsi(nextSourceNamePtr());
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getDefaultSourceName(StringBuilder sName) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr defaultSourceNamePtr() throws Exception ;

    public static String defaultSourceName() throws Exception {
        return Marshal.PtrToStringAnsi(defaultSourceNamePtr());
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean openSource(String sName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean enableSource(System.IntPtr hwnd) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean disableSource() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean closeSource() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean closeSourceManager(System.IntPtr hwnd) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean unloadSourceManager() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean endXfer() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean abortAllPendingXfers() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setXferCount(int nXfers) throws Exception ;

    public static final int TWUN_INCHES = 0;
    public static final int TWUN_CENTIMETERS = 1;
    public static final int TWUN_PICAS = 2;
    public static final int TWUN_POINTS = 3;
    public static final int TWUN_TWIPS = 4;
    public static final int TWUN_PIXELS = 5;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getCurrentUnits() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setUnits(int nUnits) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCurrentUnits(int nUnits) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getBitDepth() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setBitDepth(int nBits) throws Exception ;

    public static final int TWPT_BW = 0;
    public static final int TWPT_GRAY = 1;
    public static final int TWPT_RGB = 2;
    public static final int TWPT_PALETTE = 3;
    public static final int TWPT_CMY = 4;
    public static final int TWPT_CMYK = 5;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getPixelType() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setPixelType(int nPixType) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCurrentPixelType(int nPixType) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double getCurrentResolution() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double getXResolution() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double getYResolution() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setResolution(double dRes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setResolutionInt(int nRes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCurrentResolution(double dRes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setXResolution(double dxRes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setYResolution(double dyRes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setContrast(double dCon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setBrightness(double dBri) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setThreshold(double dThresh) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double getCurrentThreshold() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setAutoDeskew(int nMode) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getAutoDeskew() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setBlankPageMode(int nMode) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getBlankPageMode() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setBlankPageThreshold(double dDarkness) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double getBlankPageThreshold() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int blankDiscardCount() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setAutoCrop(int nMode) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getAutoCrop() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setAutoCropOptions(int nOpts) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getAutoCropOptions() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setAutoCropSize(double w, double h, int nUnits) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void clearAutoCropSize() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setAutoCropSizeRange(double minW, double maxW, double minH, double maxH, int nUnits) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void clearAutoCropSizeRange() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setAutoContrast(int nMode) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getAutoContrast() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setAutoOCR(int nMode) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getAutoOCR() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setAutoNegate(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getAutoNegate() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setXferMech(int mech) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int xferMech() throws Exception ;

    public static final int XFERMECH_NATIVE = 0;
    public static final int XFERMECH_FILE = 1;
    public static final int XFERMECH_MEMORY = 2;
    public static final int XFERMECH_FILE2 = 3;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean supportsFileXfer() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setPaperSize(int nPaper) throws Exception ;

    public static final int PAPER_NONE = 0;
    public static final int PAPER_A4LETTER = 1;
    public static final int PAPER_A4 = 1;
    public static final int PAPER_B5LETTER = 2;
    public static final int PAPER_JISB5 = 2;
    public static final int PAPER_USLETTER = 3;
    public static final int PAPER_USLEGAL = 4;
    public static final int PAPER_A5 = 5;
    public static final int PAPER_B4 = 6;
    public static final int PAPER_ISOB4 = 6;
    public static final int PAPER_B6 = 7;
    public static final int PAPER_ISOB6 = 7;
    public static final int PAPER_USLEDGER = 9;
    public static final int PAPER_USEXECUTIVE = 10;
    public static final int PAPER_A3 = 11;
    public static final int PAPER_B3 = 12;
    public static final int PAPER_ISOB3 = 12;
    public static final int PAPER_A6 = 13;
    public static final int PAPER_C4 = 14;
    public static final int PAPER_C5 = 15;
    public static final int PAPER_C6 = 16;
    public static final int PAPER_4A0 = 17;
    public static final int PAPER_2A0 = 18;
    public static final int PAPER_A0 = 19;
    public static final int PAPER_A1 = 20;
    public static final int PAPER_A2 = 21;
    public static final int PAPER_A7 = 22;
    public static final int PAPER_A8 = 23;
    public static final int PAPER_A9 = 24;
    public static final int PAPER_A10 = 25;
    public static final int PAPER_ISOB0 = 26;
    public static final int PAPER_ISOB1 = 27;
    public static final int PAPER_ISOB2 = 28;
    public static final int PAPER_ISOB5 = 29;
    public static final int PAPER_ISOB7 = 30;
    public static final int PAPER_ISOB8 = 31;
    public static final int PAPER_ISOB9 = 32;
    public static final int PAPER_ISOB10 = 33;
    public static final int PAPER_JISB0 = 34;
    public static final int PAPER_JISB1 = 35;
    public static final int PAPER_JISB2 = 36;
    public static final int PAPER_JISB3 = 37;
    public static final int PAPER_JISB4 = 38;
    public static final int PAPER_JISB6 = 39;
    public static final int PAPER_JISB7 = 40;
    public static final int PAPER_JISB8 = 41;
    public static final int PAPER_JISB9 = 42;
    public static final int PAPER_JISB10 = 43;
    public static final int PAPER_C0 = 44;
    public static final int PAPER_C1 = 45;
    public static final int PAPER_C2 = 46;
    public static final int PAPER_C3 = 47;
    public static final int PAPER_C7 = 48;
    public static final int PAPER_C8 = 49;
    public static final int PAPER_C9 = 50;
    public static final int PAPER_C10 = 51;
    public static final int PAPER_USSTATEMENT = 52;
    public static final int PAPER_BUSINESSCARD = 53;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getPaperDimensions(int nPaper, int nUnits, RefSupport<double> pdW, RefSupport<double> pdH) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean hasFeeder() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean probablyHasFlatbed() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isFeederSelected() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean selectFeeder(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isAutoFeedOn() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setAutoFeed(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isFeederLoaded() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isPaperDetectable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setAutoScan(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean canDoDuplex() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getDuplexSupport() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean enableDuplex(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isDuplexEnabled() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int hasControllableUI() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setIndicators(boolean bVisible) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getIndicators() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int compression() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCompression(int compression) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean tiled() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setTiled(boolean bTiled) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int planarChunky() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setPlanarChunky(int shape) throws Exception ;

    public static final int CHUNKY_PIXELS = 0;
    public static final int PLANAR_PIXELS = 1;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int pixelFlavor() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setPixelFlavor(int flavor) throws Exception ;

    public static final int CHOCOLATE_PIXELS = 0;
    public static final int VANILLA_PIXELS = 1;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setLightPath(boolean bTransmissive) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setAutoBright(boolean bOn) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setGamma(double dGamma) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setShadow(double d) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setHighlight(double d) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setRegion(double L, double T, double R, double B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void resetRegion() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setImageLayout(double L, double T, double R, double B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getImageLayout(RefSupport<double> L, RefSupport<double> T, RefSupport<double> R, RefSupport<double> B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getDefaultImageLayout(RefSupport<double> L, RefSupport<double> T, RefSupport<double> R, RefSupport<double> B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean resetImageLayout() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setFrame(double L, double T, double R, double B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setGrayResponse(int[] pcurve) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setColorResponse(int[] pred, int[] pgreen, int[] pblue) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean resetGrayResponse() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean resetColorResponse() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean bARCODE_IsAvailable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int bARCODE_Version() throws Exception ;

    public static final int EZBAR_ENGINE_NONE = 0;
    public static final int EZBAR_ENGINE_NATIVE = 1;
    public static final int EZBAR_ENGINE_AXTEL = 2;
    public static final int EZBAR_ENGINE_LEADTOOLS15 = 3;
    public static final int EZBAR_ENGINE_BLACKICE = 4;
    public static final int EZBAR_ENGINE_LEADTOOLS16 = 5;
    public static final int EZBAR_ENGINE_INBARCODE = 6;
    public static final int EZBAR_ENGINE_LEADTOOLS = 3;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean bARCODE_IsEngineAvailable(int nEngine) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean bARCODE_SelectEngine(int nEngine) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int bARCODE_SelectedEngine() throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr bARCODE_EngineNamePtr(int nEngine) throws Exception ;

    public static String bARCODE_EngineName(int nEngine) throws Exception {
        return Marshal.PtrToStringAnsi(bARCODE_EngineNamePtr(nEngine));
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void bARCODE_SetLicenseKey(String sKey) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int bARCODE_ReadableCodes() throws Exception ;

    public static final int EZBAR_EAN_13 = 0x1;
    public static final int EZBAR_EAN_8 = 0x2;
    public static final int EZBAR_UPCA = 0x4;
    public static final int EZBAR_UPCE = 0x8;
    public static final int EZBAR_CODE_39 = 0x10;
    public static final int EZBAR_CODE_39FA = 0x20;
    public static final int EZBAR_CODE_128 = 0x40;
    public static final int EZBAR_CODE_I25 = 0x80;
    public static final int EZBAR_CODA_BAR = 0x100;
    public static final int EZBAR_UCCEAN_128 = 0x200;
    public static final int EZBAR_CODE_93 = 0x400;
    public static final int EZBAR_ANY = -1;
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr bARCODE_TypeNamePtr(int nType) throws Exception ;

    public static String bARCODE_TypeName(int nType) throws Exception {
        return Marshal.PtrToStringAnsi(bARCODE_TypeNamePtr(nType));
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean bARCODE_SetDirectionFlags(int nDirFlags) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int bARCODE_GetDirectionFlags() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int bARCODE_AvailableDirectionFlags() throws Exception ;

    public static final int EZBAR_LEFT_TO_RIGHT = 0x1;
    public static final int EZBAR_RIGHT_TO_LEFT = 0x2;
    public static final int EZBAR_TOP_TO_BOTTOM = 0x4;
    public static final int EZBAR_BOTTOM_TO_TOP = 0x8;
    public static final int EZBAR_DIAGONAL = 0x10;
    public static final int EZBAR_HORIZONTAL = 0x3;
    public static final int EZBAR_VERTICAL = 0xC;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void bARCODE_SetZone(int x, int y, int w, int h) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void bARCODE_NoZone() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int bARCODE_Recognize(System.IntPtr hdib, int nMaxCount, int nType) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr bARCODE_TextPtr(int n) throws Exception ;

    public static String bARCODE_Text(int n) throws Exception {
        return Marshal.PtrToStringAnsi(bARCODE_TextPtr(n));
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean bARCODE_GetText(int n, StringBuilder Text) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int bARCODE_Type(int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean bARCODE_GetRect(int n, RefSupport<double> L, RefSupport<double> T, RefSupport<double> R, RefSupport<double> B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean oCR_IsAvailable() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int oCR_Version() throws Exception ;

    public static final int EZOCR_ENGINE_NONE = 0;
    public static final int EZOCR_ENGINE_TRANSYM = 1;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean oCR_IsEngineAvailable(int nEngine) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean oCR_SelectEngine(int nEngine) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int oCR_SelectedEngine() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean oCR_SelectDefaultEngine() throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr oCR_EngineNamePtr(int nEngine) throws Exception ;

    public static String oCR_EngineName(int nEngine) throws Exception {
        return Marshal.PtrToStringAnsi(oCR_EngineNamePtr(nEngine));
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void oCR_SetEngineKey(String sKey) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void oCR_SetEnginePath(String sPath) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void oCR_SetLineBreak(String sEOL) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int oCR_RecognizeDib(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int oCR_RecognizeDibZone(System.IntPtr hdib, int x, int y, int w, int h) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr oCR_TextPtr() throws Exception ;

    public static String oCR_Text() throws Exception {
        return Marshal.PtrToStringAnsi(oCR_TextPtr());
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int oCR_GetText(StringBuilder TextBuffer, int nBufLen) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int oCR_TextLength() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int oCR_TextOrientation() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean oCR_GetCharPositions(int[] charx, int[] chary) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean oCR_GetCharSizes(int[] charw, int[] charh) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void oCR_GetResolution(RefSupport<double> xdpi, RefSupport<double> ydpi) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void oCR_ClearText() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean oCR_WritePage(System.IntPtr hdib) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean oCR_WriteTextToPDF() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void oCR_SetAutoRotatePagesToPDF(boolean bYes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean oCR_GetAutoRotatePagesToPDF() throws Exception ;

    public static final int TWON_ARRAY = 3;
    public static final int TWON_ENUMERATION = 4;
    public static final int TWON_ONEVALUE = 5;
    public static final int TWON_RANGE = 6;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void cONTAINER_Free(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_Copy(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_Equal(int hconA, int hconB) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_Format(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_ItemCount(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_ItemType(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_TypeSize(int nItemType) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void cONTAINER_GetStringValue(int hcon, int n, StringBuilder sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double cONTAINER_FloatValue(int hcon, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_IntValue(int hcon, int n) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr cONTAINER_StringValuePtr(int hcon, int n) throws Exception ;

    public static String cONTAINER_StringValue(int hcon, int n) throws Exception {
        return Marshal.PtrToStringAnsi(cONTAINER_StringValuePtr(hcon,n));
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_ContainsValue(int hcon, double d) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_ContainsValueInt(int hcon, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_FindValue(int hcon, double d) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_FindValueInt(int hcon, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double cONTAINER_CurrentValue(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double cONTAINER_DefaultValue(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_CurrentValueInt(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_DefaultValueInt(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_DefaultIndex(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_CurrentIndex(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double cONTAINER_MinValue(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double cONTAINER_MaxValue(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_MinValueInt(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_MaxValueInt(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double cONTAINER_StepSize(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_StepSizeInt(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_OneValue(int nItemType, double dVal) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_Range(int nItemType, double dMin, double dMax, double dStep) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_Array(int nItemType, int nItems) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_Enumeration(int nItemType, int nItems) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_SetItem(int hcon, int n, double dVal) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_SetItemInt(int hcon, int n, int nVal) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_SetItemString(int hcon, int n, String sVal) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_SetItemFrame(int hcon, int n, double l, double t, double r, double b) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_GetItemFrame(int hcon, int n, RefSupport<double> L, RefSupport<double> T, RefSupport<double> R, RefSupport<double> B) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_SelectCurrentValue(int hcon, double dVal) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_SelectCurrentItem(int hcon, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_SelectDefaultValue(int hcon, double dVal) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_SelectDefaultItem(int hcon, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_DeleteItem(int hcon, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_InsertItem(int hcon, int n, double dVal) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int cONTAINER_Wrap(int nFormat, System.IntPtr hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr cONTAINER_Unwrap(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr cONTAINER_Handle(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean cONTAINER_IsValid(int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isCapAvailable(int uCap) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int get(int uCap) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getDefault(int uCap) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getCurrent(int uCap) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean set(int uCap, int hcon) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean reset(int uCap) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int querySupport(int uCap) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCapability(int cap, double dValue) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCapString(int cap, String sValue) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCapBool(int cap, boolean bValue) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getCapBool(int cap, boolean bDefault) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double getCapFix32(int cap, double dDefault) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getCapUint16(int cap, int nDefault) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getCapUint32(int cap, int lDefault) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCapFix32(int Cap, double dVal) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCapOneValue(int Cap, int ItemType, int ItemVal) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCapFix32R(int Cap, int Numerator, int Denominator) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getCapCurrent(int Cap, int ItemType, System.IntPtr pVal) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int toFix32(double d) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int toFix32R(int Numerator, int Denominator) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double fix32ToFloat(int nfix) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getCustomDataToFile(String sFilename) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCustomDataFromFile(String sFilename) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setCustomData(byte[] data, int nbytes) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getCustomData(byte[] buffer, int bufsize) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr customDataPtr() throws Exception ;

    public static String customData() throws Exception {
        return Marshal.PtrToStringAnsi(customDataPtr());
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isExtendedInfoSupported() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean enableExtendedInfo(int eiCode, boolean enabled) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean isExtendedInfoEnabled(int eiCode) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void disableExtendedInfo() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int extendedInfoItemCount(int eiCode) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int extendedInfoItemType(int eiCode) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int extendedInfoInt(int eiCode, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ double extendedInfoFloat(int eiCode, int n) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getExtendedInfoString(int eiCode, int n, StringBuilder Buffer, int Bufsize) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr extendedInfoStringPtr(int eiCode, int n) throws Exception ;

    public static String extendedInfoString(int eiCode, int n) throws Exception {
        return Marshal.PtrToStringAnsi(extendedInfoStringPtr(eiCode,n));
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean getExtendedInfoFrame(int eiCode, int n, RefSupport<double> L, RefSupport<double> T, RefSupport<double> R, RefSupport<double> B) throws Exception ;

    public static final int TWEI_MIN = 0x1200;
    public static final int TWEI_BARCODEX = 0x1200;
    public static final int TWEI_BARCODEY = 0x1201;
    public static final int TWEI_BARCODETEXT = 0x1202;
    public static final int TWEI_BARCODETYPE = 0x1203;
    public static final int TWEI_DESHADETOP = 0x1204;
    public static final int TWEI_DESHADELEFT = 0x1205;
    public static final int TWEI_DESHADEHEIGHT = 0x1206;
    public static final int TWEI_DESHADEWIDTH = 0x1207;
    public static final int TWEI_DESHADESIZE = 0x1208;
    public static final int TWEI_SPECKLESREMOVED = 0x1209;
    public static final int TWEI_HORZLINEXCOORD = 0x120A;
    public static final int TWEI_HORZLINEYCOORD = 0x120B;
    public static final int TWEI_HORZLINELENGTH = 0x120C;
    public static final int TWEI_HORZLINETHICKNESS = 0x120D;
    public static final int TWEI_VERTLINEXCOORD = 0x120E;
    public static final int TWEI_VERTLINEYCOORD = 0x120F;
    public static final int TWEI_VERTLINELENGTH = 0x1210;
    public static final int TWEI_VERTLINETHICKNESS = 0x1211;
    public static final int TWEI_PATCHCODE = 0x1212;
    public static final int TWEI_ENDORSEDTEXT = 0x1213;
    public static final int TWEI_FORMCONFIDENCE = 0x1214;
    public static final int TWEI_FORMTEMPLATEMATCH = 0x1215;
    public static final int TWEI_FORMTEMPLATEPAGEMATCH = 0x1216;
    public static final int TWEI_FORMHORZDOCOFFSET = 0x1217;
    public static final int TWEI_FORMVERTDOCOFFSET = 0x1218;
    public static final int TWEI_BARCODECOUNT = 0x1219;
    public static final int TWEI_BARCODECONFIDENCE = 0x121A;
    public static final int TWEI_BARCODEROTATION = 0x121B;
    public static final int TWEI_BARCODETEXTLENGTH = 0x121C;
    public static final int TWEI_DESHADECOUNT = 0x121D;
    public static final int TWEI_DESHADEBLACKCOUNTOLD = 0x121E;
    public static final int TWEI_DESHADEBLACKCOUNTNEW = 0x121F;
    public static final int TWEI_DESHADEBLACKRLMIN = 0x1220;
    public static final int TWEI_DESHADEBLACKRLMAX = 0x1221;
    public static final int TWEI_DESHADEWHITECOUNTOLD = 0x1222;
    public static final int TWEI_DESHADEWHITECOUNTNEW = 0x1223;
    public static final int TWEI_DESHADEWHITERLMIN = 0x1224;
    public static final int TWEI_DESHADEWHITERLAVE = 0x1225;
    public static final int TWEI_DESHADEWHITERLMAX = 0x1226;
    public static final int TWEI_BLACKSPECKLESREMOVED = 0x1227;
    public static final int TWEI_WHITESPECKLESREMOVED = 0x1228;
    public static final int TWEI_HORZLINECOUNT = 0x1229;
    public static final int TWEI_VERTLINECOUNT = 0x122A;
    public static final int TWEI_DESKEWSTATUS = 0x122B;
    public static final int TWEI_SKEWORIGINALANGLE = 0x122C;
    public static final int TWEI_SKEWFINALANGLE = 0x122D;
    public static final int TWEI_SKEWCONFIDENCE = 0x122E;
    public static final int TWEI_SKEWWINDOWX1 = 0x122F;
    public static final int TWEI_SKEWWINDOWY1 = 0x1230;
    public static final int TWEI_SKEWWINDOWX2 = 0x1231;
    public static final int TWEI_SKEWWINDOWY2 = 0x1232;
    public static final int TWEI_SKEWWINDOWX3 = 0x1233;
    public static final int TWEI_SKEWWINDOWY3 = 0x1234;
    public static final int TWEI_SKEWWINDOWX4 = 0x1235;
    public static final int TWEI_SKEWWINDOWY4 = 0x1236;
    public static final int TWEI_BOOKNAME = 0x1238;
    public static final int TWEI_CHAPTERNUMBER = 0x1239;
    public static final int TWEI_DOCUMENTNUMBER = 0x123A;
    public static final int TWEI_PAGENUMBER = 0x123B;
    public static final int TWEI_CAMERA = 0x123C;
    public static final int TWEI_FRAMENUMBER = 0x123D;
    public static final int TWEI_FRAME = 0x123E;
    public static final int TWEI_PIXELFLAVOR = 0x123F;
    public static final int TWEI_ICCPROFILE = 0x1240;
    public static final int TWEI_LASTSEGMENT = 0x1241;
    public static final int TWEI_SEGMENTNUMBER = 0x1242;
    public static final int TWEI_MAGDATA = 0x1243;
    public static final int TWEI_MAGTYPE = 0x1244;
    public static final int TWEI_PAGESIDE = 0x1245;
    public static final int TWEI_FILESYSTEMSOURCE = 0x1246;
    public static final int TWEI_MAX = 0x1246;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean dS(int DG, int DAT, int MSG, System.IntPtr pData) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean mgr(int DG, int DAT, int MSG, System.IntPtr pData) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void logFile(int fLog) throws Exception ;

    public static final int EZT_LOG_ON = 1;
    public static final int EZT_LOG_FLUSH = 2;
    public static final int EZT_LOG_DETAIL = 4;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setLogFolder(String sFolder) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean setLogName(String sName) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr logFileNamePtr() throws Exception ;

    public static String logFileName() throws Exception {
        return Marshal.PtrToStringAnsi(logFileNamePtr());
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void writeToLog(String sText) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean beginAcquireMemory(System.IntPtr hwnd, int nRows) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr acquireMemoryBlock() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean endAcquireMemory() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean acquireFile(System.IntPtr hwndApp, int nFF, String sFileName) throws Exception ;

    public static final int TWAIN_FF_TIFF = 0;
    public static final int TWAIN_FF_PICT = 1;
    public static final int TWAIN_FF_BMP = 2;
    public static final int TWAIN_FF_XBM = 3;
    public static final int TWAIN_FF_JFIF = 4;
    public static final int TWAIN_FF_FPX = 5;
    public static final int TWAIN_FF_TIFFMULTI = 6;
    public static final int TWAIN_FF_PNG = 7;
    public static final int TWAIN_FF_SPIFF = 8;
    public static final int TWAIN_FF_EXIF = 9;
    public static final int TWAIN_FF_PDF = 10;
    public static final int TWAIN_FF_JP2 = 11;
    public static final int TWAIN_FF_JPN = 12;
    public static final int TWAIN_FF_JPX = 13;
    public static final int TWAIN_FF_DEJAVU = 14;
    public static final int TWAIN_FF_PDFA = 15;
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int setImageReadyTimeout(int nSec) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getImageReadyTimeout() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void autoClickButton(String sButtonName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void breakModalLoop() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void emptyMessageQueue() throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ System.IntPtr buildNamePtr() throws Exception ;

    public static String buildName() throws Exception {
        return Marshal.PtrToStringAnsi(buildNamePtr());
    }

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void getBuildName(StringBuilder sName) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getSourceIdentity(System.IntPtr ptwid) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getImageInfo(System.IntPtr ptwinfo) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int selfTest(int f) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ void setQAMode(int nMode) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int getQAMode() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean blocked() throws Exception ;

    public static final int CAP_CUSTOMBASE = 0x8000;
    public static final int CAP_XFERCOUNT = 0x1;
    public static final int ICAP_COMPRESSION = 0x100;
    public static final int ICAP_PIXELTYPE = 0x101;
    public static final int ICAP_UNITS = 0x102;
    public static final int ICAP_XFERMECH = 0x103;
    public static final int CAP_AUTHOR = 0x1000;
    public static final int CAP_CAPTION = 0x1001;
    public static final int CAP_FEEDERENABLED = 0x1002;
    public static final int CAP_FEEDERLOADED = 0x1003;
    public static final int CAP_TIMEDATE = 0x1004;
    public static final int CAP_SUPPORTEDCAPS = 0x1005;
    public static final int CAP_EXTENDEDCAPS = 0x1006;
    public static final int CAP_AUTOFEED = 0x1007;
    public static final int CAP_CLEARPAGE = 0x1008;
    public static final int CAP_FEEDPAGE = 0x1009;
    public static final int CAP_REWINDPAGE = 0x100A;
    public static final int CAP_INDICATORS = 0x100B;
    public static final int CAP_SUPPORTEDCAPSEXT = 0x100C;
    public static final int CAP_PAPERDETECTABLE = 0x100D;
    public static final int CAP_UICONTROLLABLE = 0x100E;
    public static final int CAP_DEVICEONLINE = 0x100F;
    public static final int CAP_AUTOSCAN = 0x1010;
    public static final int CAP_THUMBNAILSENABLED = 0x1011;
    public static final int CAP_DUPLEX = 0x1012;
    public static final int CAP_DUPLEXENABLED = 0x1013;
    public static final int CAP_ENABLEDSUIONLY = 0x1014;
    public static final int CAP_CUSTOMDSDATA = 0x1015;
    public static final int CAP_ENDORSER = 0x1016;
    public static final int CAP_JOBCONTROL = 0x1017;
    public static final int CAP_ALARMS = 0x1018;
    public static final int CAP_ALARMVOLUME = 0x1019;
    public static final int CAP_AUTOMATICCAPTURE = 0x101A;
    public static final int CAP_TIMEBEFOREFIRSTCAPTURE = 0x101B;
    public static final int CAP_TIMEBETWEENCAPTURES = 0x101C;
    public static final int CAP_CLEARBUFFERS = 0x101D;
    public static final int CAP_MAXBATCHBUFFERS = 0x101E;
    public static final int CAP_DEVICETIMEDATE = 0x101F;
    public static final int CAP_POWERSUPPLY = 0x1020;
    public static final int CAP_CAMERAPREVIEWUI = 0x1021;
    public static final int CAP_DEVICEEVENT = 0x1022;
    public static final int CAP_SERIALNUMBER = 0x1024;
    public static final int CAP_PRINTER = 0x1026;
    public static final int CAP_PRINTERENABLED = 0x1027;
    public static final int CAP_PRINTERINDEX = 0x1028;
    public static final int CAP_PRINTERMODE = 0x1029;
    public static final int CAP_PRINTERSTRING = 0x102A;
    public static final int CAP_PRINTERSUFFIX = 0x102B;
    public static final int CAP_LANGUAGE = 0x102C;
    public static final int CAP_FEEDERALIGNMENT = 0x102D;
    public static final int CAP_FEEDERORDER = 0x102E;
    public static final int CAP_REACQUIREALLOWED = 0x1030;
    public static final int CAP_BATTERYMINUTES = 0x1032;
    public static final int CAP_BATTERYPERCENTAGE = 0x1033;
    public static final int ICAP_AUTOBRIGHT = 0x1100;
    public static final int ICAP_BRIGHTNESS = 0x1101;
    public static final int ICAP_CONTRAST = 0x1103;
    public static final int ICAP_CUSTHALFTONE = 0x1104;
    public static final int ICAP_EXPOSURETIME = 0x1105;
    public static final int ICAP_FILTER = 0x1106;
    public static final int ICAP_FLASHUSED = 0x1107;
    public static final int ICAP_GAMMA = 0x1108;
    public static final int ICAP_HALFTONES = 0x1109;
    public static final int ICAP_HIGHLIGHT = 0x110A;
    public static final int ICAP_IMAGEFILEFORMAT = 0x110C;
    public static final int ICAP_LAMPSTATE = 0x110D;
    public static final int ICAP_LIGHTSOURCE = 0x110E;
    public static final int ICAP_ORIENTATION = 0x1110;
    public static final int ICAP_PHYSICALWIDTH = 0x1111;
    public static final int ICAP_PHYSICALHEIGHT = 0x1112;
    public static final int ICAP_SHADOW = 0x1113;
    public static final int ICAP_FRAMES = 0x1114;
    public static final int ICAP_XNATIVERESOLUTION = 0x1116;
    public static final int ICAP_YNATIVERESOLUTION = 0x1117;
    public static final int ICAP_XRESOLUTION = 0x1118;
    public static final int ICAP_YRESOLUTION = 0x1119;
    public static final int ICAP_MAXFRAMES = 0x111A;
    public static final int ICAP_TILES = 0x111B;
    public static final int ICAP_BITORDER = 0x111C;
    public static final int ICAP_CCITTKFACTOR = 0x111D;
    public static final int ICAP_LIGHTPATH = 0x111E;
    public static final int ICAP_PIXELFLAVOR = 0x111F;
    public static final int ICAP_PLANARCHUNKY = 0x1120;
    public static final int ICAP_ROTATION = 0x1121;
    public static final int ICAP_SUPPORTEDSIZES = 0x1122;
    public static final int ICAP_THRESHOLD = 0x1123;
    public static final int ICAP_XSCALING = 0x1124;
    public static final int ICAP_YSCALING = 0x1125;
    public static final int ICAP_BITORDERCODES = 0x1126;
    public static final int ICAP_PIXELFLAVORCODES = 0x1127;
    public static final int ICAP_JPEGPIXELTYPE = 0x1128;
    public static final int ICAP_TIMEFILL = 0x112A;
    public static final int ICAP_BITDEPTH = 0x112B;
    public static final int ICAP_BITDEPTHREDUCTION = 0x112C;
    public static final int ICAP_UNDEFINEDIMAGESIZE = 0x112D;
    public static final int ICAP_IMAGEDATASET = 0x112E;
    public static final int ICAP_EXTIMAGEINFO = 0x112F;
    public static final int ICAP_MINIMUMHEIGHT = 0x1130;
    public static final int ICAP_MINIMUMWIDTH = 0x1131;
    public static final int ICAP_FLIPROTATION = 0x1136;
    public static final int ICAP_BARCODEDETECTIONENABLED = 0x1137;
    public static final int ICAP_SUPPORTEDBARCODETYPES = 0x1138;
    public static final int ICAP_BARCODEMAXSEARCHPRIORITIES = 0x1139;
    public static final int ICAP_BARCODESEARCHPRIORITIES = 0x113A;
    public static final int ICAP_BARCODESEARCHMODE = 0x113B;
    public static final int ICAP_BARCODEMAXRETRIES = 0x113C;
    public static final int ICAP_BARCODETIMEOUT = 0x113D;
    public static final int ICAP_ZOOMFACTOR = 0x113E;
    public static final int ICAP_PATCHCODEDETECTIONENABLED = 0x113F;
    public static final int ICAP_SUPPORTEDPATCHCODETYPES = 0x1140;
    public static final int ICAP_PATCHCODEMAXSEARCHPRIORITIES = 0x1141;
    public static final int ICAP_PATCHCODESEARCHPRIORITIES = 0x1142;
    public static final int ICAP_PATCHCODESEARCHMODE = 0x1143;
    public static final int ICAP_PATCHCODEMAXRETRIES = 0x1144;
    public static final int ICAP_PATCHCODETIMEOUT = 0x1145;
    public static final int ICAP_FLASHUSED2 = 0x1146;
    public static final int ICAP_IMAGEFILTER = 0x1147;
    public static final int ICAP_NOISEFILTER = 0x1148;
    public static final int ICAP_OVERSCAN = 0x1149;
    public static final int ICAP_AUTOMATICBORDERDETECTION = 0x1150;
    public static final int ICAP_AUTOMATICDESKEW = 0x1151;
    public static final int ICAP_AUTOMATICROTATE = 0x1152;
    public static final int ICAP_JPEGQUALITY = 0x1153;
    public static final int TWTY_INT8 = 0x0;
    public static final int TWTY_INT16 = 0x1;
    public static final int TWTY_INT32 = 0x2;
    public static final int TWTY_UINT8 = 0x3;
    public static final int TWTY_UINT16 = 0x4;
    public static final int TWTY_UINT32 = 0x5;
    public static final int TWTY_BOOL = 0x6;
    public static final int TWTY_FIX32 = 0x7;
    public static final int TWTY_FRAME = 0x8;
    public static final int TWTY_STR32 = 0x9;
    public static final int TWTY_STR64 = 0xA;
    public static final int TWTY_STR128 = 0xB;
    public static final int TWTY_STR255 = 0xC;
    public static final int TWTY_STR1024 = 0xD;
    public static final int TWTY_UNI512 = 0xE;
    public static final int TWOR_ROT0 = 0;
    public static final int TWOR_ROT90 = 1;
    public static final int TWOR_ROT180 = 2;
    public static final int TWOR_ROT270 = 3;
}


// namespace