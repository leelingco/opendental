//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package SparksToothChart;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.PerioSequenceType;
import OpenDentBusiness.PerioSurf;
import OpenDentBusiness.PIn;
import OpenDentBusiness.Tooth;
import SparksToothChart.LineSimple;
import SparksToothChart.ToothChartDirectX;
import SparksToothChart.ToothChartDrawEventArgs;
import SparksToothChart.ToothGraphic;
import SparksToothChart.ToothGroup;
import SparksToothChart.ToothGroupType;


//using System.Linq;
public class ToothChartDirectX  extends Control 
{

    /**
    * DirectX handle to this control.
    */
    public Device device = null;
    /**
    * This is a reference to the TcData object that's at the wrapper level.
    */
    public SparksToothChart.ToothChartData TcData;
    public SparksToothChart.ToothChartDirectX.DirectXDeviceFormat deviceFormat = null;
    //public bool showPerioLegend=false;
    private Color specular_color_normal = new Color();
    private Color specular_color_cementum = new Color();
    private float specularSharpness = new float();
    private Microsoft.DirectX.Direct3D.Font xfont = new Microsoft.DirectX.Direct3D.Font();
    private Microsoft.DirectX.Direct3D.Font xSealantFont = new Microsoft.DirectX.Direct3D.Font();
    private Microsoft.DirectX.Direct3D.Font xWatchFont = new Microsoft.DirectX.Direct3D.Font();
    private Microsoft.DirectX.Direct3D.Font xWatchFontBig = new Microsoft.DirectX.Direct3D.Font();
    private boolean MouseIsDown = false;
    public SparksToothChart.ToothChartDrawEventHandler SegmentDrawn = null;
    /**
    * Mouse move causes this variable to be updated with the current tooth that the mouse is hovering over.
    */
    private String hotTooth = new String();
    /**
    * The previous hotTooth.  If this is different than hotTooth, then mouse has just now moved to a new tooth.  Can be 0 to represent no previous.
    */
    private String hotToothOld = new String();
    private boolean deviceLost = true;
    //private DateTime frameBeginTime=DateTime.MinValue;
    //private DateTime frameEndTime=DateTime.MinValue;
    public static class DirectXDeviceFormat   
    {
        public AdapterInformation adapter = null;
        public DeviceType deviceType = DeviceType.NullReference;
        public CreateFlags createFlags = CreateFlags.SoftwareVertexProcessing;
        public DepthFormat depthStencilFormat = DepthFormat.D16;
        public Format backBufferFormat = Format.R5G6B5;
        public MultiSampleType maxMultiSampleType = MultiSampleType.None;
        public DirectXDeviceFormat() throws Exception {
        }

        /**
        * Inverse of ToString(). String to DirectXDeviceFormat.
        */
        public DirectXDeviceFormat(String directXFormat) throws Exception {
            if (directXFormat.IndexOf(';') < 0)
            {
                return ;
            }
             
            //Invalid format.
            String[] settings = directXFormat.Split(new char[]{ ';' });
            adapter = Manager.Adapters[PIn.Int(settings[0])];
            deviceType = (DeviceType)Enum.Parse(DeviceType.class, settings[1]);
            createFlags = (CreateFlags)PIn.Int(settings[2]);
            depthStencilFormat = (DepthFormat)Enum.Parse(DepthFormat.class, settings[3]);
            backBufferFormat = (Format)Enum.Parse(Format.class, settings[4]);
            maxMultiSampleType = (MultiSampleType)Enum.Parse(MultiSampleType.class, settings[5]);
        }

        public String toString() {
            try
            {
                return "" + adapter.Adapter + ";" + Enum.GetName(DeviceType.class, deviceType) + ";" + ((int)createFlags) + ";" + Enum.GetName(DepthFormat.class, depthStencilFormat) + ";" + Enum.GetName(Format.class, backBufferFormat) + ";" + Enum.GetName(MultiSampleType.class, maxMultiSampleType);
            }
            catch (RuntimeException __dummyCatchVar0)
            {
                throw __dummyCatchVar0;
            }
            catch (Exception __dummyCatchVar0)
            {
                throw new RuntimeException(__dummyCatchVar0);
            }
        
        }

        public boolean equals(Object obj) {
            try
            {
                if (obj.GetType() != SparksToothChart.ToothChartDirectX.DirectXDeviceFormat.class)
                {
                    return false;
                }
                 
                SparksToothChart.ToothChartDirectX.DirectXDeviceFormat xformat = (SparksToothChart.ToothChartDirectX.DirectXDeviceFormat)obj;
                return (StringSupport.equals(xformat.toString(), this.toString()));
            }
            catch (RuntimeException __dummyCatchVar1)
            {
                throw __dummyCatchVar1;
            }
            catch (Exception __dummyCatchVar1)
            {
                throw new RuntimeException(__dummyCatchVar1);
            }
        
        }

        public int hashCode() {
            try
            {
                return super.GetHashCode();
            }
            catch (RuntimeException __dummyCatchVar2)
            {
                throw __dummyCatchVar2;
            }
            catch (Exception __dummyCatchVar2)
            {
                throw new RuntimeException(__dummyCatchVar2);
            }
        
        }

        public Device createDevice(Control control) throws Exception {
            try
            {
                PresentParameters pp = new PresentParameters();
                pp.AutoDepthStencilFormat = depthStencilFormat;
                pp.BackBufferCount = 1;
                pp.BackBufferFormat = backBufferFormat;
                pp.BackBufferHeight = control.Height;
                pp.BackBufferWidth = control.Width;
                pp.FullScreenRefreshRateInHz = 0;
                //Must be 0 in windowed mode.
                pp.DeviceWindow = control;
                pp.DeviceWindowHandle = control.Handle;
                pp.EnableAutoDepthStencil = true;
                pp.ForceNoMultiThreadedFlag = false;
                pp.MultiSample = maxMultiSampleType;
                pp.MultiSampleQuality = 0;
                pp.PresentationInterval = PresentInterval.Default;
                pp.PresentFlag = PresentFlag.None;
                pp.SwapEffect = SwapEffect.Discard;
                //Required to be set to discard for anti-aliasing.
                pp.Windowed = true;
                //Must be set to true for controls.
                //When a Device object is created, the common language runtime will change the floating-point unit (FPU) to single precision to maintain better performance.
                //This was causing extensive rounding errors throughout OD.
                //To maintain the default double precision FPU, which is default for the common language runtime, we use the CreateFlags.FpuPreserve flag in the constructor.
                //HardwareVertexProcessing, MixedVertexProcessing, and SoftwareVertexProcessing constants (CreateFlags) are mutually exclusive.
                //One of them must be specified during creation of a "device".
                //Therefore, we pass in our variable createFlags (typically SoftwareVertexProcessing) along with FpuPreserve to maintain double precision.
                Device dev = new Device(adapter.Adapter, deviceType, control, createFlags | CreateFlags.FpuPreserve, pp);
                return dev;
            }
            catch (Exception __dummyCatchVar3)
            {
            }

            return null;
        }
    
    }

    public ToothChartDirectX() throws Exception {
        initializeComponent();
    }

    public static SparksToothChart.ToothChartDirectX.DirectXDeviceFormat[] getStandardDeviceFormats() throws Exception {
        DeviceType[] deviceTypes = new DeviceType[]{ DeviceType.Hardware, DeviceType.Reference };
        //16bit formats
        //24bit formats
        //32bit formats
        //64bit formats
        Format[] backBufferFormats = new Format[]{ Format.R5G6B5, Format.A1R5G5B5, Format.X1R5G5B5, Format.R8G8B8, Format.A8R8G8B8, Format.X8R8G8B8, Format.A2R10G10B10, Format.A2B10G10R10, Format.A8B8G8R8, Format.G8R8G8B8, Format.R8G8B8G8, Format.X8B8G8R8, Format.A16B16G16R16 };
        DepthFormat[] depthFormats = new DepthFormat[]{ DepthFormat.D16, DepthFormat.D32, DepthFormat.D24X8, DepthFormat.D15S1, DepthFormat.D24S8, DepthFormat.D24SingleS8, DepthFormat.D24X4S4, DepthFormat.L16 };
        return ToothChartDirectX.GetPossibleDeviceFormats(deviceTypes, backBufferFormats, true, depthFormats);
    }

    private static boolean isDeviceMultiSampleOK(AdapterInformation adapter, DeviceType deviceType, DepthFormat depthFormat, Format backbufferFormat, MultiSampleType multisampleType, boolean windowed) throws Exception {
        int qualityLevels = 0;
        int result = 0;
        //Verify that the render target surface supports the given multisample type
        RefSupport<int> refVar___0 = new RefSupport<int>();
        RefSupport<int> refVar___1 = new RefSupport<int>();
        boolean boolVar___0 = Manager.CheckDeviceMultiSampleType(adapter.Adapter, deviceType, backbufferFormat, windowed, multisampleType, refVar___0, refVar___1);
        result = refVar___0.getValue();
        qualityLevels = refVar___1.getValue();
        if (boolVar___0)
        {
            //Verify that the depth stencil surface supports the given multisample type
            RefSupport<int> refVar___2 = new RefSupport<int>();
            RefSupport<int> refVar___3 = new RefSupport<int>();
            boolean boolVar___1 = Manager.CheckDeviceMultiSampleType(adapter.Adapter, deviceType, (Format)depthFormat, windowed, multisampleType, refVar___2, refVar___3);
            result = refVar___2.getValue();
            qualityLevels = refVar___3.getValue();
            if (boolVar___1)
            {
                return (result == ((int)ResultCode.Success));
            }
             
        }
         
        return false;
    }

    private static SparksToothChart.ToothChartDirectX.DirectXDeviceFormat[] getPossibleDeviceFormats(DeviceType[] deviceTypes, Format[] backBufferFormats, boolean windowed, DepthFormat[] depthStencilFormats) throws Exception {
        List<SparksToothChart.ToothChartDirectX.DirectXDeviceFormat> possibleFormats = new List<SparksToothChart.ToothChartDirectX.DirectXDeviceFormat>();
        for (int a = 0;a < Manager.Adapters.Count;a++)
        {
            AdapterInformation adapter = Manager.Adapters[a];
            for (int t = 0;t < deviceTypes.Length;t++)
            {
                DeviceType deviceType = deviceTypes[t];
                for (int b = 0;b < backBufferFormats.Length;b++)
                {
                    Format backBufferFormat = backBufferFormats[b];
                    for (Object __dummyForeachVar1 : adapter.SupportedDisplayModes)
                    {
                        DisplayMode displayMode = (DisplayMode)__dummyForeachVar1;
                        if (displayMode.Format != backBufferFormat)
                        {
                            continue;
                        }
                         
                        //We require the display buffer to have the same format as the back buffer,
                        //so that we know that a back buffer flip will work.
                        if (Manager.CheckDeviceType(adapter.Adapter, deviceType, displayMode.Format, displayMode.Format, windowed))
                        {
                            for (Object __dummyForeachVar0 : depthStencilFormats)
                            {
                                //Now make sure the depth buffer meets one of the required formats.
                                DepthFormat depthStencilFormat = (DepthFormat)__dummyForeachVar0;
                                if (Manager.CheckDeviceFormat(adapter.Adapter, deviceType, displayMode.Format, Usage.DepthStencil, ResourceType.Surface, depthStencilFormat))
                                {
                                    if (Manager.CheckDepthStencilMatch(adapter.Adapter, deviceType, displayMode.Format, displayMode.Format, depthStencilFormat))
                                    {
                                        SparksToothChart.ToothChartDirectX.DirectXDeviceFormat format = new SparksToothChart.ToothChartDirectX.DirectXDeviceFormat();
                                        format.adapter = adapter;
                                        format.deviceType = deviceType;
                                        format.createFlags = CreateFlags.SoftwareVertexProcessing;
                                        //format.createFlags=CreateFlags.FpuPreserve;
                                        format.depthStencilFormat = depthStencilFormat;
                                        format.backBufferFormat = backBufferFormat;
                                        format.maxMultiSampleType = MultiSampleType.None;
                                        //Anti-aliasing/multi-sampling appears to only work on hardware devices.
                                        if (deviceType == DeviceType.Hardware)
                                        {
                                            Caps caps = Manager.GetDeviceCaps(adapter.Adapter, deviceType);
                                            //An multisampling/anti-aliasing method must be supported both on the back buffer and with the depth buffer in order for it to be usable.
                                            if (IsDeviceMultiSampleOK(adapter, deviceType, depthStencilFormat, backBufferFormat, MultiSampleType.FourSamples, windowed))
                                            {
                                                format.maxMultiSampleType = MultiSampleType.FourSamples;
                                            }
                                            else if (IsDeviceMultiSampleOK(adapter, deviceType, depthStencilFormat, backBufferFormat, MultiSampleType.ThreeSamples, windowed))
                                            {
                                                format.maxMultiSampleType = MultiSampleType.ThreeSamples;
                                            }
                                            else if (IsDeviceMultiSampleOK(adapter, deviceType, depthStencilFormat, backBufferFormat, MultiSampleType.TwoSamples, windowed))
                                            {
                                                format.maxMultiSampleType = MultiSampleType.TwoSamples;
                                            }
                                               
                                        }
                                         
                                        if (possibleFormats.IndexOf(format) > -1)
                                        {
                                            continue;
                                        }
                                         
                                        //Skip duplicate formats.
                                        possibleFormats.Add(format);
                                    }
                                     
                                }
                                 
                            }
                        }
                         
                    }
                }
            }
        }
        return possibleFormats.ToArray();
    }

    /**
    * Returns the number of bits corresponding to the given format. Returns 0 if the format has not yet been accounted for.
    */
    public static int getFormatBitCount(Format format) throws Exception {
        Format[] eightBitFormats = new Format[]{ Format.A4L4, Format.A8, Format.L8, Format.P8, Format.R3G3B2 };
        Format[] sixteenBitFormats = new Format[]{ Format.A1R5G5B5, Format.A4R4G4B4, Format.A8L8, Format.A8P8, Format.A8R3G3B2, Format.D15S1, Format.D16, Format.D16Lockable, Format.L16, Format.L6V5U5, Format.R16F, Format.R5G6B5, Format.V8U8, Format.X1R5G5B5, Format.X4R4G4B4 };
        Format[] twentyFourBitFormats = new Format[]{ Format.R8G8B8 };
        Format[] thirtyTwoBitFormats = new Format[]{ Format.A2B10G10R10, Format.A2R10G10B10, Format.A2W10V10U10, Format.A8B8G8R8, Format.A8R8G8B8, Format.D24S8, Format.D24SingleS8, Format.D24X4S4, Format.D24X8, Format.D32, Format.D32SingleLockable, Format.G16R16, Format.G16R16F, Format.G8R8G8B8, Format.Q8W8V8U8, Format.R32F, Format.R8G8B8G8, Format.V16U16, Format.X8B8G8R8, Format.X8L8V8U8, Format.X8R8G8B8 };
        Format[] sixtyFourBitFormats = new Format[]{ Format.A16B16G16R16, Format.A16B16G16R16F, Format.G32R32F, Format.Q16W16V16U16 };
        Format[] oneHundredTwentyEightBitFormats = new Format[]{ Format.A32B32G32R32F };
        for (int i = 0;i < eightBitFormats.Length;i++)
        {
            if (format == eightBitFormats[i])
            {
                return 8;
            }
             
        }
        for (int i = 0;i < sixteenBitFormats.Length;i++)
        {
            if (format == sixteenBitFormats[i])
            {
                return 16;
            }
             
        }
        for (int i = 0;i < twentyFourBitFormats.Length;i++)
        {
            if (format == twentyFourBitFormats[i])
            {
                return 24;
            }
             
        }
        for (int i = 0;i < thirtyTwoBitFormats.Length;i++)
        {
            if (format == thirtyTwoBitFormats[i])
            {
                return 32;
            }
             
        }
        for (int i = 0;i < sixtyFourBitFormats.Length;i++)
        {
            if (format == sixtyFourBitFormats[i])
            {
                return 64;
            }
             
        }
        for (int i = 0;i < oneHundredTwentyEightBitFormats.Length;i++)
        {
            if (format == oneHundredTwentyEightBitFormats[i])
            {
                return 128;
            }
             
        }
        return 0;
    }

    //Format has not yet been accounted for.
    //Format.CxV8U8,Format.Dxt1,Format.Dxt2,Format.Dxt3,Format.Dxt4,Format.Dxt5,Format.Multi2Argb8,
    //Format.Unknown,Format.Uyvy,Format.VertexData,Format.Yuy2
    /**
    * Returns the number of bits represented by the given depth format. Returns 0 if the given depth format is not yet accounted for.
    */
    public static int getDepthFormatBitCount(DepthFormat depthFormat) throws Exception {
        DepthFormat[] sixteenBitFormats = new DepthFormat[]{ DepthFormat.D15S1, DepthFormat.D16, DepthFormat.D16Lockable, DepthFormat.L16 };
        DepthFormat[] thirtyTwoBitFormats = new DepthFormat[]{ DepthFormat.D24S8, DepthFormat.D24SingleS8, DepthFormat.D24X4S4, DepthFormat.D24X8, DepthFormat.D32, DepthFormat.D32SingleLockable };
        for (int i = 0;i < sixteenBitFormats.Length;i++)
        {
            if (depthFormat == sixteenBitFormats[i])
            {
                return 16;
            }
             
        }
        for (int i = 0;i < thirtyTwoBitFormats.Length;i++)
        {
            if (depthFormat == thirtyTwoBitFormats[i])
            {
                return 32;
            }
             
        }
        return 0;
    }

    //The specified format it not yet accounted for.
    //DepthFormat.Unknown
    public static int getMultiSampleNumberForType(MultiSampleType multiSampleType) throws Exception {
        if (multiSampleType == MultiSampleType.TwoSamples)
        {
            return 2;
        }
        else if (multiSampleType == MultiSampleType.ThreeSamples)
        {
            return 3;
        }
        else if (multiSampleType == MultiSampleType.FourSamples)
        {
            return 4;
        }
        else if (multiSampleType == MultiSampleType.FiveSamples)
        {
            return 5;
        }
        else if (multiSampleType == MultiSampleType.SixSamples)
        {
            return 6;
        }
        else if (multiSampleType == MultiSampleType.SevenSamples)
        {
            return 7;
        }
        else if (multiSampleType == MultiSampleType.EightSamples)
        {
            return 8;
        }
        else if (multiSampleType == MultiSampleType.NineSamples)
        {
            return 9;
        }
        else if (multiSampleType == MultiSampleType.TenSamples)
        {
            return 10;
        }
        else if (multiSampleType == MultiSampleType.ElevenSamples)
        {
            return 11;
        }
        else if (multiSampleType == MultiSampleType.TwelveSamples)
        {
            return 12;
        }
        else if (multiSampleType == MultiSampleType.ThirteenSamples)
        {
            return 13;
        }
        else if (multiSampleType == MultiSampleType.FourteenSamples)
        {
            return 14;
        }
        else if (multiSampleType == MultiSampleType.FifteenSamples)
        {
            return 15;
        }
        else if (multiSampleType == MultiSampleType.SixteenSamples)
        {
            return 16;
        }
                       
        return 0;
    }

    /**
    * Must be called after the ToothChartDirectX control has been added to a form and should be called before it is drawn the first time.
    */
    public void initializeGraphics() throws Exception {
        if (deviceFormat != null)
        {
            device = deviceFormat.createDevice(this);
            if (device == null)
            {
                throw new Exception("Failed to create DirectX device.");
            }
             
            device.DeviceReset += new EventHandler(this.OnDeviceReset);
            device.DeviceLost += new EventHandler(this.OnDeviceLost);
            device.DeviceResizing += new CancelEventHandler(this.OnDeviceResizing);
            onDeviceReset(device,null);
        }
         
    }

    public void setSize(Size size) throws Exception {
        this.Size = size;
        reinitialize();
    }

    public void reinitialize() throws Exception {
        cleanupDirectX();
        if (device != null)
        {
            device.Dispose();
            device = null;
        }
         
        initializeGraphics();
    }

    public void cleanupDirectX() throws Exception {
        if (xSealantFont != null)
        {
            xSealantFont.Dispose();
            xSealantFont = null;
        }
         
        if (xWatchFont != null)
        {
            xWatchFont.Dispose();
            xWatchFont = null;
        }
         
        if (xWatchFontBig != null)
        {
            xWatchFontBig.Dispose();
            xWatchFontBig = null;
        }
         
        if (xfont != null)
        {
            xfont.Dispose();
            xfont = null;
        }
         
        TcData.cleanupDirectX();
    }

    /**
    * 
    */
    public void onDeviceReset(Object sender, EventArgs e) throws Exception {
        deviceLost = false;
        cleanupDirectX();
        device = sender instanceof Device ? (Device)sender : (Device)null;
        //Required for calculating font background rectangle size in ToothChartData.
        TcData.Font = new System.Drawing.Font("Arial", 9f);
        xfont = new Microsoft.DirectX.Direct3D.Font(device, (int)Math.Round((float)(14 * Math.Sqrt(TcData.PixelScaleRatio))), (int)Math.Round((float)(5 * Math.Sqrt(TcData.PixelScaleRatio))), FontWeight.Normal, 1, false, CharacterSet.Ansi, Precision.Device, FontQuality.ClearType, PitchAndFamily.DefaultPitch, "Arial");
        xSealantFont = new Microsoft.DirectX.Direct3D.Font(device, (int)Math.Round(25 * TcData.PixelScaleRatio), (int)Math.Round(9 * TcData.PixelScaleRatio), FontWeight.Regular, 1, false, CharacterSet.Ansi, Precision.Device, FontQuality.ClearType, PitchAndFamily.DefaultPitch, "Arial");
        xWatchFont = new Microsoft.DirectX.Direct3D.Font(device, (int)Math.Round(15 * TcData.PixelScaleRatio), (int)Math.Round(6 * TcData.PixelScaleRatio), FontWeight.Regular, 1, false, CharacterSet.Ansi, Precision.Device, FontQuality.ClearType, PitchAndFamily.DefaultPitch, "Arial");
        xWatchFontBig = new Microsoft.DirectX.Direct3D.Font(device, (int)Math.Round(19 * TcData.PixelScaleRatio), (int)Math.Round(7.4 * TcData.PixelScaleRatio), FontWeight.UltraBold, 1, false, CharacterSet.Ansi, Precision.Device, FontQuality.ClearType, PitchAndFamily.DefaultPitch, "Times New Roman");
        TcData.prepareForDirectX(device);
    }

    public void onDeviceLost(Object sender, EventArgs e) throws Exception {
        deviceLost = true;
    }

    public void onDeviceResizing(Object sender, EventArgs e) throws Exception {
    }

    //Hmm, is this function ever called? I couldn't make it fire with initial testing.
    protected void onPaintBackground(PaintEventArgs e) throws Exception {
    }

    //Do nothing to eliminate flicker.
    protected void onSizeChanged(EventArgs e) throws Exception {
        Invalidate();
    }

    //Force the control to redraw.
    private Matrix screenSpaceMatrix() throws Exception {
        return device.Transform.World * device.Transform.View * device.Transform.Projection;
    }

    protected void onPaint(PaintEventArgs pe) throws Exception {
        //Color backColor=Color.FromArgb(150,145,152);
        if (device == null || deviceLost)
        {
            //When no rendering context has been set, simply display the control
            //as a black rectangle. This will make the control draw as a blank
            //rectangle when in the designer.
            pe.Graphics.FillRectangle(new SolidBrush(TcData.ColorBackground), new Rectangle(0, 0, Width, Height));
            return ;
        }
         
        try
        {
            //When the OS user is switched then switched back or when coming back from stand by mode, the OS calls the OnPaint function
            //even before it calls the OnDeviceLost() function. When this happens, the render will fail
            //because the DirectX device is not in a valid state to be rendered to. Before the exception is returned from Render(),
            //a call is made by the OS to OnDeviceLost(), which sets deviceLost=true (when the OnPaint() function begins, deviceLost=false)
            //so that further rendering will not occur with the device in its invalid state.elec
            render();
        }
        catch (Exception __dummyCatchVar4)
        {
            if (deviceLost)
            {
                //Rendering failed because our device is invalid. Reinitialize the device and cached objects and force
                //the control to be rerendered.
                reinitialize();
                Invalidate();
            }
             
        }
    
    }

    protected void render() throws Exception {
        //Set the view and projection matricies for the camera.
        float heightProj = TcData.SizeOriginalProjection.Height;
        float widthProj = TcData.SizeOriginalProjection.Width;
        if (TcData.IsWide)
        {
            widthProj = heightProj * Width / Height;
        }
        else
        {
            //tall
            heightProj = widthProj * Height / Width;
        } 
        device.Transform.Projection = Matrix.OrthoLH(widthProj, heightProj, 0, 1000.0f);
        device.Transform.World = Matrix.Identity;
        //viewport transformation not used. Default is to fill entire control.
        device.RenderState.CullMode = Cull.None;
        //Do not cull triangles. Our triangles are too small for this feature to work reliably.
        device.RenderState.ZBufferEnable = true;
        device.RenderState.ZBufferFunction = Compare.Less;
        //Blend mode settings.
        device.RenderState.AlphaBlendEnable = false;
        //Disabled
        //device.RenderState.AlphaFunction=Compare.Always;
        //device.RenderState.AlphaTestEnable=true;
        //device.RenderState.SourceBlend=Blend.SourceAlpha;
        //device.RenderState.DestinationBlend=Blend.InvSourceAlpha;
        //device.RenderState.AlphaBlendOperation=BlendOperation.Add;
        //Lighting settings
        device.RenderState.Lighting = true;
        device.RenderState.SpecularEnable = true;
        device.RenderState.SpecularMaterialSource = ColorSource.Material;
        float ambI = .4f;
        //.2f;//Darker for testing
        float difI = .6f;
        //.4f;//Darker for testing
        float specI = .8f;
        //.2f;//Had to turn specular down to avoid bleedout.
        if (TcData.PerioMode)
        {
            //Want perio teeth to be more washed out so that other graphics are more visible.
            ambI = .6f;
            difI = .4f;
        }
         
        //I think we're going to need to eventually use shaders to get our pinpoint reflections.
        //Set properties for light 0. Diffuse light.
        device.Lights[0].Type = LightType.Directional;
        device.Lights[0].Ambient = Color.FromArgb(255, (int)(255 * ambI), (int)(255 * ambI), (int)(255 * ambI));
        device.Lights[0].Diffuse = Color.FromArgb(255, (int)(255 * difI), (int)(255 * difI), (int)(255 * difI));
        device.Lights[0].Specular = Color.FromArgb(255, (int)(255 * specI), (int)(255 * specI), (int)(255 * specI));
        device.Lights[0].Direction = new Vector3(0.5f, -0.2f, 1f);
        //(0.5f,0.1f,1f);
        device.Lights[0].Enabled = true;
        //Material settings
        float specNorm = 1f;
        float specCem = .1f;
        //Also, see DrawTooth for the specular color used for enamel.
        specular_color_normal = Color.FromArgb(255, (int)(255 * specNorm), (int)(255 * specNorm), (int)(255 * specNorm));
        specular_color_cementum = Color.FromArgb(255, (int)(255 * specCem), (int)(255 * specCem), (int)(255 * specCem));
        specularSharpness = 70f;
        //70f;//Not the same as in OpenGL. No maximum value. Smaller number means light is more spread out.
        //Draw
        if (TcData.PerioMode)
        {
            drawScenePerio();
        }
        else
        {
            drawScene();
        } 
    }

    private void drawScene() throws Exception {
        device.Clear(ClearFlags.Target | ClearFlags.ZBuffer, TcData.ColorBackground, 1.0f, 0);
        device.BeginScene();
        //The Z values between OpenGL and DirectX are negated (the axis runs in the opposite direction).
        //We reflect that difference here by negating the z values for all coordinates.
        Matrix defOrient = Matrix.Identity;
        defOrient.Scale(1f, 1f, -1f);
        //We make sure to move all teeth forward a large step so that specular lighting will calculate properly.
        //This step does not affect the tooth locations on the screen because changes in z position for a tooth
        //does not affect position in orthographic projections.
        Matrix trans = Matrix.Identity;
        trans.Translate(0f, 0f, 400f);
        defOrient = defOrient * trans;
        for (int t = 0;t < TcData.ListToothGraphics.Count;t++)
        {
            //frameBeginTime=DateTime.Now;
            //loop through each tooth
            if (StringSupport.equals(TcData.ListToothGraphics.get___idx(t).getToothID(), "implant"))
            {
                continue;
            }
             
            //this is not an actual tooth.
            drawFacialView(TcData.ListToothGraphics.get___idx(t),defOrient);
            drawOcclusalView(TcData.ListToothGraphics.get___idx(t),defOrient);
        }
        //frameEndTime=DateTime.Now;
        drawWatches(defOrient);
        drawDrawingSegments();
        drawNumbersAndLines();
        //Numbers and center lines are top-most.
        device.EndScene();
        device.Present();
    }

    private void drawScenePerio() throws Exception {
        device.Clear(ClearFlags.Target | ClearFlags.ZBuffer, TcData.ColorBackground, 1.0f, 0);
        device.BeginScene();
        //The distance from y=0 to the tip of the highest root in the upper-most maxillary row.
        ;
        //The baseY is used to force the perio chart to start as near to the top of the control as possible.
        //This is helpful with the perio legend, because it allows one to increase the height of this control
        //in order to give space for the perio legend to display at the bottom.
        float baseY = Height / (2f * TcData.ScaleMmToPix) - maxYmm;
        Matrix defOrient = Matrix.Scaling(1f, 1f, -1f) * Matrix.Translation(0, baseY, 400f);
        List<ToothGraphic> maxillaryTeeth = new List<ToothGraphic>();
        List<ToothGraphic> mandibleTeeth = new List<ToothGraphic>();
        for (int t = 0;t < TcData.ListToothGraphics.Count;t++)
        {
            //loop through each tooth
            if (StringSupport.equals(TcData.ListToothGraphics.get___idx(t).getToothID(), "implant"))
            {
                continue;
            }
             
            //this is not an actual tooth.
            if (ToothGraphic.isMaxillary(TcData.ListToothGraphics.get___idx(t).getToothID()))
            {
                maxillaryTeeth.Add(TcData.ListToothGraphics.get___idx(t));
            }
            else
            {
                mandibleTeeth.Add(TcData.ListToothGraphics.get___idx(t));
            } 
        }
        //Draw the maxillary upper-most row.
        Matrix maxillaryUpperRowMat = Matrix.Translation(0, 45f, 0) * defOrient;
        DrawPerioRow(maxillaryTeeth, maxillaryUpperRowMat, true, false);
        //Draw the maxillary lower-most row with teeth which have negated z values.
        Matrix maxillaryLowerRowMat = Matrix.Scaling(1f, 1f, -1f) * Matrix.Translation(0, 12f, 0) * defOrient;
        DrawPerioRow(maxillaryTeeth, maxillaryLowerRowMat, true, true);
        //Draw the mandible upper-most row with teeth which have negated z values.
        Matrix mandibleUpperRowMat = Matrix.Scaling(1f, 1f, -1f) * Matrix.Translation(0, -12f, 0) * defOrient;
        DrawPerioRow(mandibleTeeth, mandibleUpperRowMat, false, true);
        //Draw the mandible lower-most row.
        Matrix mandibleLowerRowMat = Matrix.Translation(0, -45f, 0) * defOrient;
        DrawPerioRow(mandibleTeeth, mandibleLowerRowMat, false, false);
        drawNumbersAndLinesPerio(baseY);
        //Numbers and center lines are top-most.
        DrawPerioLegend(-60f, baseY - 63f);
        device.EndScene();
        device.Present();
    }

    private void drawPerioRow(List<ToothGraphic> toothGraphics, Matrix orientation, boolean maxillary, boolean lingual) throws Exception {
        for (int t = 0;t < toothGraphics.Count;t++)
        {
            device.RenderState.ZBufferEnable = true;
            device.RenderState.Lighting = true;
            device.Transform.World = Matrix.Translation(GetTransX(toothGraphics[t].ToothID), 0, 0) * orientation;
            if (toothGraphics[t].Visible || (toothGraphics[t].IsCrown && toothGraphics[t].IsImplant) || toothGraphics[t].IsPontic)
            {
                DrawTooth(toothGraphics[t]);
            }
             
            if (toothGraphics[t].IsImplant)
            {
                DrawImplant(toothGraphics[t]);
            }
             
        }
        device.RenderState.ZBufferEnable = false;
        device.RenderState.Lighting = false;
        Line line = new Line(device);
        float sign = maxillary ? 1 : -1;
        //The device.Transform.World matrix must be set before calling Line.Begin()
        //or else your lines end up in the wrong location! This is odd behavior, since you *MUST*
        //pass in your screen matrix when you call Line.DrawTransform(). This must be a DirectX bug.
        device.Transform.World = orientation;
        Matrix lineMat = screenSpaceMatrix();
        ;
        ;
        ;
        //Draw the horizontal line at 0
        DrawExtended3dLine(new Vector3[]{ new Vector3(leftX, 0, 0), new Vector3(rightX, 0, 0) }, 0, false, TcData.ColorText, 1.5f, lineMat);
        for (int mm = 3;mm <= mmMax;mm += 3)
        {
            //Draw the other horizontal lines
            DrawExtended3dLine(new Vector3[]{ new Vector3(leftX, sign * mm, 0), new Vector3(rightX, sign * mm, 0) }, 0, false, Color.Gray, .8f, lineMat);
        }
        for (int t = 0;t < toothGraphics.Count;t++)
        {
            //Separate loop than drawing the teeth, so that we don't need to change
            //the device.RenderState.ZBufferEnable and device.RenderState.Lighting state variables for every
            //tooth. This will help the speed a little.
            SizeF mobTextSize = MeasureStringMm(toothGraphics[t].Mobility);
            if (!lingual)
            {
                //Draw mobility numbers.
                device.Transform.World = Matrix.Translation(GetTransX(toothGraphics[t].ToothID) - mobTextSize.Width / 2f, 0, 0) * orientation;
                PrintString(toothGraphics[t].Mobility, 0, maxillary ? -2.5f : 5.5f, 0, toothGraphics[t].colorMobility, xfont);
            }
             
            device.Transform.World = Matrix.Translation(GetTransX(toothGraphics[t].ToothID), 0, 0) * orientation;
            Matrix toothLineMat = screenSpaceMatrix();
            int intTooth = ToothGraphic.IdToInt(toothGraphics[t].ToothID);
            if (lingual)
            {
                //Draw furcations at each tooth site if furcation present.
                drawFurcationTriangle(intTooth,PerioSurf.DL,maxillary,toothLineMat);
                drawFurcationTriangle(intTooth,PerioSurf.L,maxillary,toothLineMat);
                drawFurcationTriangle(intTooth,PerioSurf.ML,maxillary,toothLineMat);
                //Draw probing bars.
                drawProbingBar(intTooth,PerioSurf.DL);
                drawProbingBar(intTooth,PerioSurf.L);
                drawProbingBar(intTooth,PerioSurf.ML);
                //Draw bleeding droplets.
                drawDroplet(intTooth,PerioSurf.DL,true,maxillary);
                drawDroplet(intTooth,PerioSurf.L,true,maxillary);
                drawDroplet(intTooth,PerioSurf.ML,true,maxillary);
                //Draw suppuration droplets.
                drawDroplet(intTooth,PerioSurf.DL,false,maxillary);
                drawDroplet(intTooth,PerioSurf.L,false,maxillary);
                drawDroplet(intTooth,PerioSurf.ML,false,maxillary);
            }
            else
            {
                //buccal
                //Draw furcations at each tooth site if furcation present.
                drawFurcationTriangle(intTooth,PerioSurf.DB,maxillary,toothLineMat);
                drawFurcationTriangle(intTooth,PerioSurf.B,maxillary,toothLineMat);
                drawFurcationTriangle(intTooth,PerioSurf.MB,maxillary,toothLineMat);
                //Draw probing bars.
                drawProbingBar(intTooth,PerioSurf.DB);
                drawProbingBar(intTooth,PerioSurf.B);
                drawProbingBar(intTooth,PerioSurf.MB);
                //Draw bleeding droplets.
                drawDroplet(intTooth,PerioSurf.DB,true,maxillary);
                drawDroplet(intTooth,PerioSurf.B,true,maxillary);
                drawDroplet(intTooth,PerioSurf.MB,true,maxillary);
                //Draw suppuration droplets.
                drawDroplet(intTooth,PerioSurf.DB,false,maxillary);
                drawDroplet(intTooth,PerioSurf.B,false,maxillary);
                drawDroplet(intTooth,PerioSurf.MB,false,maxillary);
            } 
        }
        device.Transform.World = orientation;
        Matrix measureLineMat = screenSpaceMatrix();
        //Draw GM lines.
        List<LineSimple> gmLines = TcData.getHorizontalLines(PerioSequenceType.GingMargin,maxillary,!lingual);
        for (int i = 0;i < gmLines.Count;i++)
        {
            List<Vector3> gmLineV = LineSimpleToVector3List(gmLines[i]);
            Vector3[] gmLineA = gmLineV.ToArray();
            DrawExtended3dLine(gmLineA, 0.2f, true, TcData.ColorGingivalMargin, 2f, measureLineMat);
        }
        //Draw CAL lines.
        List<LineSimple> calLines = TcData.getHorizontalLines(PerioSequenceType.CAL,maxillary,!lingual);
        for (int i = 0;i < calLines.Count;i++)
        {
            List<Vector3> calLineV = LineSimpleToVector3List(calLines[i]);
            Vector3[] calLineA = calLineV.ToArray();
            DrawExtended3dLine(calLineA, 0.2f, true, TcData.ColorCAL, 2f, measureLineMat);
        }
        //Draw MGJ lines.
        List<LineSimple> mgjLines = TcData.getHorizontalLines(PerioSequenceType.MGJ,maxillary,!lingual);
        for (int i = 0;i < mgjLines.Count;i++)
        {
            List<Vector3> mgjLineV = LineSimpleToVector3List(mgjLines[i]);
            Vector3[] mgjLineA = mgjLineV.ToArray();
            DrawExtended3dLine(mgjLineA, 0.2f, true, TcData.ColorMGJ, 2f, measureLineMat);
        }
    }

    private void drawDroplet(int intTooth, PerioSurf surf, boolean isBleeding, boolean maxillary) throws Exception {
        PointF dropletPos = TcData.getBleedingOrSuppuration(intTooth,surf,isBleeding);
        if (dropletPos.X == 0 && dropletPos.Y == 0)
        {
            return ;
        }
         
        //No droplet to draw at this site.
        Matrix saveWorldMat = device.Transform.World;
        device.Transform.World = Matrix.Translation(dropletPos.X, dropletPos.Y, 0) * device.Transform.World;
        if (!maxillary)
        {
            //When the droplet is for a mandibular tooth, flip the droplet about the x-axis (negate y values).
            device.Transform.World = Matrix.Scaling(1f, -1f, 1f) * device.Transform.World;
        }
         
        Color dropletColor = TcData.ColorSuppuration;
        if (isBleeding)
        {
            dropletColor = TcData.ColorBleeding;
        }
         
        DrawDroplet(0, 0, dropletColor);
        device.Transform.World = saveWorldMat;
    }

    private void drawDroplet(float x, float y, Color dropletColor) throws Exception {
        int dropletColorArgb = dropletColor.ToArgb();
        List<PointF> dropletVertsP = TcData.getDropletVertices();
        List<CustomVertex.PositionColored> dropletVertsV = new List<CustomVertex.PositionColored>();
        for (int p = 0;p < dropletVertsP.Count;p++)
        {
            dropletVertsV.Add(new CustomVertex.PositionColored(x + dropletVertsP[p].X, y + dropletVertsP[p].Y, 0, dropletColorArgb));
        }
        //This point is implied and is the last point.
        dropletVertsV.Add(new CustomVertex.PositionColored(x, y, 0, dropletColorArgb));
        VertexBuffer vb = new VertexBuffer(CustomVertex.PositionColored.class, CustomVertex.PositionColored.StrideSize * dropletVertsV.Count, device, Usage.WriteOnly, CustomVertex.PositionColored.Format, Pool.Managed);
        vb.SetData(dropletVertsV.ToArray(), 0, LockFlags.None);
        List<int> indiciesL = new List<int>();
        for (int v = 0;v < dropletVertsV.Count - 2;v++)
        {
            indiciesL.Add(0);
            indiciesL.Add(v + 1);
            indiciesL.Add(v + 2);
        }
        int[] indicies = indiciesL.ToArray();
        IndexBuffer ib = new IndexBuffer(int.class, indicies.Length, device, Usage.None, Pool.Managed);
        ib.SetData(indicies, 0, LockFlags.None);
        device.VertexFormat = CustomVertex.PositionColored.Format;
        device.SetStreamSource(0, vb, 0);
        device.Indices = ib;
        device.DrawIndexedPrimitives(PrimitiveType.TriangleList, 0, 0, dropletVertsV.Count, 0, indicies.Length / 3);
        ib.Dispose();
        vb.Dispose();
    }

    private void drawProbingBar(int intTooth, PerioSurf perioSurf) throws Exception {
        ;
        Color colorBar = new Color();
        RefSupport<Color> refVar___4 = new RefSupport<Color>();
        LineSimple barPoints = TcData.getProbingLine(intTooth,perioSurf,refVar___4);
        colorBar = refVar___4.getValue();
        if (barPoints == null)
        {
            return ;
        }
         
        CustomVertex.PositionColored[] quadVerts = new CustomVertex.PositionColored[]{ new CustomVertex.PositionColored(barPoints.Vertices[0].X - barWidthMM / 2f, barPoints.Vertices[0].Y, 0, colorBar.ToArgb()), new CustomVertex.PositionColored(barPoints.Vertices[0].X - barWidthMM / 2f, barPoints.Vertices[1].Y, 0, colorBar.ToArgb()), new CustomVertex.PositionColored(barPoints.Vertices[0].X + barWidthMM / 2f, barPoints.Vertices[1].Y, 0, colorBar.ToArgb()), new CustomVertex.PositionColored(barPoints.Vertices[0].X + barWidthMM / 2f, barPoints.Vertices[0].Y, 0, colorBar.ToArgb()) };
        VertexBuffer vb = new VertexBuffer(CustomVertex.PositionColored.class, CustomVertex.PositionColored.StrideSize * quadVerts.Length, device, Usage.WriteOnly, CustomVertex.PositionColored.Format, Pool.Managed);
        vb.SetData(quadVerts, 0, LockFlags.None);
        int[] indicies = new int[]{ 0, 1, 2, 0, 2, 3 };
        IndexBuffer ib = new IndexBuffer(int.class, indicies.Length, device, Usage.None, Pool.Managed);
        ib.SetData(indicies, 0, LockFlags.None);
        device.VertexFormat = CustomVertex.PositionColored.Format;
        device.SetStreamSource(0, vb, 0);
        device.Indices = ib;
        device.DrawIndexedPrimitives(PrimitiveType.TriangleList, 0, 0, quadVerts.Length, 0, indicies.Length / 3);
        ib.Dispose();
        vb.Dispose();
    }

    private void drawFurcationTriangle(int intTooth, PerioSurf perioSurf, boolean maxillary, Matrix toothLineMat) throws Exception {
        int furcationValue = TcData.getFurcationValue(intTooth,perioSurf);
        if (furcationValue < 1 || furcationValue > 3)
        {
            return ;
        }
         
        PointF sitePos = TcData.getFurcationPos(intTooth,perioSurf);
        DrawFurcationTriangle(sitePos.X, sitePos.Y, maxillary, toothLineMat, furcationValue);
    }

    private Color getFurcationColor(int furcationValue) throws Exception {
        Color color = TcData.ColorFurcations;
        if (furcationValue >= TcData.RedLimitFurcations)
        {
            color = TcData.ColorFurcationsRed;
        }
         
        return color;
    }

    private void drawFurcationTriangle(float tipx, float tipy, boolean pointUp, Matrix lineMat, int furcationValue) throws Exception {
        ;
        float sign = pointUp ? 1 : -1;
        Color color = getFurcationColor(furcationValue);
        List<Vector3> triPoints = new List<Vector3>();
        //We form an equilateral triangle.
        triPoints.Add(new Vector3(tipx + triSideLenMM / 2f, tipy + sign * ((float)(triSideLenMM * Math.Sqrt(3) / 2f)), 0));
        triPoints.Add(new Vector3(tipx, tipy, 0));
        triPoints.Add(new Vector3(tipx - triSideLenMM / 2f, tipy + sign * ((float)(triSideLenMM * Math.Sqrt(3) / 2f)), 0));
        if (furcationValue == 1)
        {
            DrawExtended3dLine(new Vector3[]{ triPoints[0], triPoints[1], triPoints[2] }, 0.1f, false, color, 2f, lineMat);
        }
        else if (furcationValue == 2)
        {
            DrawExtended3dLine(new Vector3[]{ triPoints[0], triPoints[1], triPoints[2], triPoints[0] }, 0.1f, true, color, 2f, lineMat);
        }
        else if (furcationValue == 3)
        {
            DrawExtended3dLine(new Vector3[]{ triPoints[0], triPoints[1], triPoints[2], triPoints[0] }, 0.1f, true, color, 2f, lineMat);
            VertexBuffer triVb = null;
            IndexBuffer triIb = null;
            try
            {
                CustomVertex.PositionColored[] solidTriVerts = new CustomVertex.PositionColored[]{ new CustomVertex.PositionColored(triPoints[0], color.ToArgb()), new CustomVertex.PositionColored(triPoints[1], color.ToArgb()), new CustomVertex.PositionColored(triPoints[2], color.ToArgb()) };
                triVb = new VertexBuffer(CustomVertex.PositionColored.class, CustomVertex.PositionColored.StrideSize * solidTriVerts.Length, device, Usage.WriteOnly, CustomVertex.PositionColored.Format, Pool.Managed);
                triVb.SetData(solidTriVerts, 0, LockFlags.None);
                int[] triIndicies = new int[]{ 0, 1, 2 };
                triIb = new IndexBuffer(int.class, triIndicies.Length, device, Usage.None, Pool.Managed);
                triIb.SetData(triIndicies, 0, LockFlags.None);
                device.VertexFormat = CustomVertex.PositionColored.Format;
                device.SetStreamSource(0, triVb, 0);
                device.Indices = triIb;
                device.DrawIndexedPrimitives(PrimitiveType.TriangleList, 0, 0, solidTriVerts.Length, 0, triIndicies.Length / 3);
            }
            finally
            {
                if (triVb != null)
                {
                    triVb.Dispose();
                    triVb = null;
                }
                 
                if (triIb != null)
                {
                    triIb.Dispose();
                }
                 
            }
        }
        else
        {
        }   
    }

    //invalid value. assume no furcation.
    private List<Vector3> lineSimpleToVector3List(LineSimple lineSimple) throws Exception {
        List<Vector3> vectorList = new List<Vector3>();
        for (int p = 0;p < lineSimple.Vertices.Count;p++)
        {
            vectorList.Add(new Vector3(lineSimple.Vertices[p].X, lineSimple.Vertices[p].Y, lineSimple.Vertices[p].Z));
        }
        return vectorList;
    }

    /**
    * Draws a line strip extending the two point lines which to not include endpoints.
    * Set extendEndPoints to true to extend the endpoints of the line.
    */
    private void drawExtended3dLine(Vector3[] points, float extendDist, boolean extendEndPoints, Color color, float lineWidth, Matrix transform) throws Exception {
        //Convert each line strip into very simple two point lines so that line extensions can be calculated more easily below.
        //Items in the array are tuples of (2D point,bool indicating end point).
        List<Object> twoPointLines = new List<Object>();
        for (int p = 0;p < points.Length - 1;p++)
        {
            twoPointLines.Add(points[p]);
            twoPointLines.Add(p == 0);
            twoPointLines.Add(points[p + 1]);
            twoPointLines.Add(p == points.Length - 2);
        }
        Line line = new Line(device);
        line.Antialias = false;
        line.Width = lineWidth;
        line.Begin();
        for (int j = 0;j < twoPointLines.Count;j += 4)
        {
            //Draw each individual two point line. The lines must be broken down from line strips so that when individual two point
            //line locations are modified they do not affect any other two point lines within the same line strip.
            Vector3 p1 = (Vector3)twoPointLines[j];
            boolean p1IsEndPoint = (boolean)twoPointLines[j + 1];
            Vector3 p2 = (Vector3)twoPointLines[j + 2];
            boolean p2IsEndPoint = (boolean)twoPointLines[j + 3];
            Vector3 lineDir = p2 - p1;
            lineDir.Normalize();
            //Gives the line direction a single unit length.
            //Do not extend the endpoints for the ends of the line strips unless extendEndPoints=true.
            if (!p1IsEndPoint || extendEndPoints)
            {
                p1 = p1 - extendDist * lineDir;
            }
             
            //Do not extend the endpoints for the ends of the line strips unless extendEndPoints=true.
            if (!p2IsEndPoint || extendEndPoints)
            {
                p2 = p2 + extendDist * lineDir;
            }
             
            Vector3[] lineVerts = new Vector3[]{ p1, p2 };
            line.DrawTransform(lineVerts, transform, color);
        }
        line.End();
        line.Dispose();
    }

    private void drawFacialView(ToothGraphic toothGraphic, Matrix defOrient) throws Exception {
        Matrix toothTrans = Matrix.Identity;
        toothTrans.Translate(getTransX(toothGraphic.getToothID()), getTransYfacial(toothGraphic.getToothID()), 0);
        Matrix rotAndTranUser = toothRotationAndTranslationMatrix(toothGraphic);
        device.Transform.World = rotAndTranUser * toothTrans * defOrient;
        if (toothGraphic.getVisible() || (toothGraphic.IsCrown && toothGraphic.IsImplant) || toothGraphic.IsPontic)
        {
            drawTooth(toothGraphic);
        }
         
        device.RenderState.ZBufferEnable = false;
        device.RenderState.Lighting = false;
        Matrix lineMatrix = screenSpaceMatrix();
        Line line = new Line(device);
        line.GlLines = true;
        if (toothGraphic.DrawBigX)
        {
            //Thickness of line depends on size of window.
            //The line size needs to be slightly larger than in OpenGL because
            //lines are drawn with polygons in DirectX and they are anti-aliased,
            //even when the line.Antialias flag is set.
            line.Width = 2.2f * TcData.PixelScaleRatio;
            line.Begin();
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                line.DrawTransform(new Vector3[]{ new Vector3(-2f, 12f, 0f), new Vector3(2f, -6f, 0f) }, lineMatrix, toothGraphic.colorX);
                line.DrawTransform(new Vector3[]{ new Vector3(2f, 12f, 0f), new Vector3(-2f, -6f, 0f) }, lineMatrix, toothGraphic.colorX);
            }
            else
            {
                line.DrawTransform(new Vector3[]{ new Vector3(-2f, 6f, 0f), new Vector3(2f, -12f, 0f) }, lineMatrix, toothGraphic.colorX);
                line.DrawTransform(new Vector3[]{ new Vector3(2f, 6f, 0f), new Vector3(-2f, -12f, 0f) }, lineMatrix, toothGraphic.colorX);
            } 
            line.End();
        }
         
        if (toothGraphic.getVisible() && toothGraphic.IsRCT)
        {
            //draw RCT
            //Thickness of lines depend on size of window.
            //The line size needs to be slightly larger than in OpenGL because
            //lines are drawn with polygons in DirectX and they are anti-aliased,
            //even when the line.Antialias flag is set.
            line.Width = 2.5f * TcData.PixelScaleRatio;
            line.Begin();
            List<LineSimple> linesSimple = toothGraphic.getRctLines();
            for (int i = 0;i < linesSimple.Count;i++)
            {
                if (linesSimple[i].Vertices.Count < 2)
                {
                    continue;
                }
                 
                //Just to avoid internal errors, even though not likely.
                //Convert each line strip into very simple two point lines so that line extensions can be calculated more easily below.
                //Items in the array are tuples of (2D point,bool indicating end point).
                List<Object> twoPointLines = new List<Object>();
                for (int j = 0;j < linesSimple[i].Vertices.Count - 1;j++)
                {
                    twoPointLines.Add(new Vector3(linesSimple[i].Vertices[j].X, linesSimple[i].Vertices[j].Y, linesSimple[i].Vertices[j].Z));
                    twoPointLines.Add(j == 0);
                    twoPointLines.Add(new Vector3(linesSimple[i].Vertices[j + 1].X, linesSimple[i].Vertices[j + 1].Y, linesSimple[i].Vertices[j + 1].Z));
                    twoPointLines.Add(j == linesSimple[i].Vertices.Count - 2);
                }
                for (int j = 0;j < twoPointLines.Count;j += 4)
                {
                    //Draw each individual two point line. The lines must be broken down from line strips so that when individual two point
                    //line locations are modified they do not affect any other two point lines within the same line strip.
                    Vector3 p1 = (Vector3)twoPointLines[j];
                    boolean p1IsEndPoint = (boolean)twoPointLines[j + 1];
                    Vector3 p2 = (Vector3)twoPointLines[j + 2];
                    boolean p2IsEndPoint = (boolean)twoPointLines[j + 3];
                    Vector3 lineDir = p2 - p1;
                    lineDir.Normalize();
                    //Gives the line direction a single unit length.
                    float extSize = 0.25f;
                    //The number of units to extend each end of the two point line.
                    if (!p1IsEndPoint)
                    {
                        //Do not extend the endpoints for the ends of the line strips.
                        p1 = p1 - extSize * lineDir;
                    }
                     
                    if (!p2IsEndPoint)
                    {
                        //Do not extend the endpoints for the ends of the line strips.
                        p2 = p2 + extSize * lineDir;
                    }
                     
                    Vector3[] lineVerts = new Vector3[]{ p1, p2 };
                    line.DrawTransform(lineVerts, lineMatrix, toothGraphic.colorRCT);
                }
            }
            line.End();
        }
         
        ToothGroup groupBU = toothGraphic.getGroup(ToothGroupType.Buildup);
        //during debugging, not all teeth have a BU group yet.
        if (toothGraphic.getVisible() && groupBU != null && groupBU.Visible)
        {
            //BU or Post
            device.RenderState.ZBufferEnable = false;
            device.RenderState.Lighting = true;
            device.Lights[0].Enabled = false;
            //Disable the scene light.
            device.Lights[1].Ambient = Color.White;
            device.Lights[1].Enabled = true;
            Color colorBU = toothGraphic.getGroup(ToothGroupType.Buildup).PaintColor;
            device.VertexFormat = CustomVertex.PositionNormal.Format;
            device.SetStreamSource(0, toothGraphic.vb, 0);
            Material material = new Material();
            material.Ambient = colorBU;
            device.Material = material;
            device.Indices = groupBU.facesDirectX;
            device.DrawIndexedPrimitives(PrimitiveType.TriangleList, 0, 0, toothGraphic.VertexNormals.Count, 0, groupBU.NumIndicies / 3);
            device.Lights[0].Enabled = true;
            device.Lights[1].Enabled = false;
        }
         
        if (toothGraphic.IsImplant)
        {
            drawImplant(toothGraphic);
        }
         
        line.Dispose();
        device.RenderState.ZBufferEnable = true;
        device.RenderState.Lighting = true;
    }

    private void drawImplant(ToothGraphic toothGraphic) throws Exception {
        device.RenderState.ZBufferEnable = true;
        device.RenderState.Lighting = true;
        if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
        {
            //flip the implant upside down
            device.Transform.World = Matrix.RotationZ((float)Math.PI) * device.Transform.World;
        }
         
        Material material = new Material();
        material.Ambient = toothGraphic.colorImplant;
        material.Diffuse = toothGraphic.colorImplant;
        material.Specular = specular_color_normal;
        material.SpecularSharpness = specularSharpness;
        device.Material = material;
        ToothGraphic implantGraphic = TcData.ListToothGraphics.get___idx("implant");
        device.VertexFormat = CustomVertex.PositionNormal.Format;
        device.SetStreamSource(0, implantGraphic.vb, 0);
        for (int g = 0;g < implantGraphic.Groups.Count;g++)
        {
            ToothGroup group = (ToothGroup)implantGraphic.Groups[g];
            if (!group.Visible || group.GroupType == ToothGroupType.Buildup)
            {
                continue;
            }
             
            device.Indices = group.facesDirectX;
            device.DrawIndexedPrimitives(PrimitiveType.TriangleList, 0, 0, implantGraphic.VertexNormals.Count, 0, group.NumIndicies / 3);
        }
    }

    private void drawOcclusalView(ToothGraphic toothGraphic, Matrix defOrient) throws Exception {
        //now the occlusal surface. Notice that it's relative to origin again
        Matrix toothTrans = Matrix.Identity;
        //Start with world transform defined by calling function.
        toothTrans.Translate(getTransX(toothGraphic.getToothID()), getTransYocclusal(toothGraphic.getToothID()), 0);
        Matrix toothRot = Matrix.Identity;
        if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
        {
            toothRot.RotateX((float)((-110f * Math.PI) / 180f));
        }
        else
        {
            //rotate angle about line from origin to x,y,z
            //mandibular
            if (ToothGraphic.isAnterior(toothGraphic.getToothID()))
            {
                toothRot.RotateX((float)((110f * Math.PI) / 180f));
            }
            else
            {
                toothRot.RotateX((float)((120f * Math.PI) / 180f));
            } 
        } 
        Matrix rotAndTranUser = toothRotationAndTranslationMatrix(toothGraphic);
        device.Transform.World = rotAndTranUser * toothRot * toothTrans * defOrient;
        //if perm tooth
        if (!Tooth.isPrimary(toothGraphic.getToothID()) && Tooth.isValidDB(Tooth.permToPri(toothGraphic.getToothID())) && TcData.ListToothGraphics.get___idx(Tooth.permToPri(toothGraphic.getToothID())).getVisible())
        {
        }
        else //and the primary tooth is visible
        //do not paint
        //might not be visible if an implant
        if (toothGraphic.getVisible() || (toothGraphic.IsCrown && toothGraphic.IsImplant))
        {
            //a crown on an implant will paint
            //pontics won't paint, because tooth is invisible
            drawTooth(toothGraphic);
        }
          
        device.RenderState.ZBufferEnable = false;
        device.RenderState.Lighting = false;
        float toMm = 1f / TcData.ScaleMmToPix;
        if (toothGraphic.getVisible() && toothGraphic.IsSealant)
        {
            //draw sealant
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                PrintString("S", TcData.PixelScaleRatio * (-6f * toMm), TcData.PixelScaleRatio * (-100f * toMm), -6f, toothGraphic.colorSealant, xSealantFont);
            }
            else
            {
                PrintString("S", TcData.PixelScaleRatio * (-6f * toMm), TcData.PixelScaleRatio * (22f * toMm), -6f, toothGraphic.colorSealant, xSealantFont);
            } 
        }
         
        device.RenderState.ZBufferEnable = true;
        device.RenderState.Lighting = true;
    }

    private Material getGroupMaterial(ToothGraphic toothGraphic, ToothGroup group) throws Exception {
        Material material = new Material();
        Color materialColor = new Color();
        if (toothGraphic.ShiftO < -10)
        {
            //if unerupted
            materialColor = Color.FromArgb(group.PaintColor.A / 2, group.PaintColor.R / 2, group.PaintColor.G / 2, group.PaintColor.B / 2);
        }
        else
        {
            materialColor = group.PaintColor;
        } 
        material.Ambient = materialColor;
        material.Diffuse = materialColor;
        if (group.GroupType == ToothGroupType.Cementum)
        {
            material.Specular = specular_color_cementum;
        }
        else if (group.PaintColor.R > 245 && group.PaintColor.G > 245 && group.PaintColor.B > 235)
        {
            //because DirectX washes out the specular on the enamel, we have to turn it down only for the enamel color
            //for reference, this is the current enamel color: Color.FromArgb(255,250,250,240)
            float specEnamel = .4f;
            material.Specular = Color.FromArgb(255, (int)(255 * specEnamel), (int)(255 * specEnamel), (int)(255 * specEnamel));
        }
        else
        {
            material.Specular = specular_color_normal;
        }  
        material.SpecularSharpness = specularSharpness;
        return material;
    }

    private void drawTooth(ToothGraphic toothGraphic) throws Exception {
        ToothGroup group;
        device.VertexFormat = CustomVertex.PositionNormal.Format;
        device.SetStreamSource(0, toothGraphic.vb, 0);
        for (int g = 0;g < toothGraphic.Groups.Count;g++)
        {
            group = (ToothGroup)toothGraphic.Groups[g];
            if (!group.Visible || group.facesDirectX == null || group.GroupType == ToothGroupType.Buildup || group.GroupType == ToothGroupType.None)
            {
                continue;
            }
             
            device.Material = getGroupMaterial(toothGraphic,group);
            //draw the group
            device.Indices = group.facesDirectX;
            device.DrawIndexedPrimitives(PrimitiveType.TriangleList, 0, 0, toothGraphic.VertexNormals.Count, 0, group.NumIndicies / 3);
        }
    }

    /**
    * The advantage of drawing the perio legend within the DirectX perio control is that we can render the
    * furcation triangles and blood and supperation droplets to scale. We also have the ability to change the apperance
    * of the legend if a user defined perio color changes.
    */
    private void drawPerioLegend(float xLeft, float yTop) throws Exception {
        device.RenderState.Lighting = false;
        device.RenderState.ZBufferEnable = false;
        float ySpace = 4f;
        Color textColor = Color.Black;
        device.Transform.World = Matrix.Translation(xLeft, yTop, 0);
        PrintString("Bleeding", 0, 0, 0, textColor, xfont);
        DrawDroplet(18f, -1.5f, TcData.ColorBleeding);
        PrintString("Suppuration", 0, -ySpace, 0, textColor, xfont);
        DrawDroplet(18f, -ySpace - 1.5f, TcData.ColorSuppuration);
        PrintString("Probing", 0, -2 * ySpace, 0, textColor, xfont);
        drawColoredRectangle(device,new RectangleF(18f, -2 * ySpace - 3f, 0.6f, 3f),TcData.ColorProbing);
        drawColoredRectangle(device,new RectangleF(19f, -2 * ySpace - 3f, 0.6f, 3f),TcData.ColorProbingRed);
        device.Transform.World = device.Transform.World * Matrix.Translation(35f, 0, 0);
        PrintString("Gingival Margin", 0, 0, 0, textColor, xfont);
        drawColoredRectangle(device,new RectangleF(35f, -1.5f, 15f, 2f / TcData.ScaleMmToPix),TcData.ColorGingivalMargin);
        PrintString("Clinical Attachment Level", 0, -ySpace, 0, textColor, xfont);
        drawColoredRectangle(device,new RectangleF(35f, -ySpace - 1.5f, 15f, 2f / TcData.ScaleMmToPix),TcData.ColorCAL);
        PrintString("Mucogingival Junction", 0, -2 * ySpace, 0, textColor, xfont);
        drawColoredRectangle(device,new RectangleF(35f, -2 * ySpace - 1.5f, 15f, 2f / TcData.ScaleMmToPix),TcData.ColorMGJ);
        device.Transform.World = device.Transform.World * Matrix.Translation(65f, 0, 0);
        Matrix lineMat = screenSpaceMatrix();
        PrintString("Furcation 1", 0, 0, 0, textColor, xfont);
        DrawFurcationTriangle(17f, -0.5f, false, lineMat, 1);
        PrintString("Furcation 2", 0, -ySpace, 0, textColor, xfont);
        DrawFurcationTriangle(17f, -ySpace - 0.5f, false, lineMat, 2);
        PrintString("Furcation 3", 0, -2 * ySpace, 0, textColor, xfont);
        DrawFurcationTriangle(17f, -2 * ySpace - 0.5f, false, lineMat, 3);
    }

    public static void drawColoredRectangle(Device dev, RectangleF rect, Color color) throws Exception {
        VertexBuffer vb = null;
        IndexBuffer ib = null;
        try
        {
            int colorArgb = color.ToArgb();
            CustomVertex.PositionColored[] quadVerts = new CustomVertex.PositionColored[]{ new CustomVertex.PositionColored(rect.Left, rect.Bottom, 0, colorArgb), new CustomVertex.PositionColored(rect.Left, rect.Top, 0, colorArgb), new CustomVertex.PositionColored(rect.Right, rect.Top, 0, colorArgb), new CustomVertex.PositionColored(rect.Right, rect.Bottom, 0, colorArgb) };
            vb = new VertexBuffer(CustomVertex.PositionColored.class, CustomVertex.PositionColored.StrideSize * quadVerts.Length, dev, Usage.WriteOnly, CustomVertex.PositionColored.Format, Pool.Managed);
            vb.SetData(quadVerts, 0, LockFlags.None);
            int[] indicies = new int[]{ 0, 1, 2, 0, 2, 3 };
            ib = new IndexBuffer(int.class, indicies.Length, dev, Usage.None, Pool.Managed);
            ib.SetData(indicies, 0, LockFlags.None);
            dev.VertexFormat = CustomVertex.PositionColored.Format;
            dev.SetStreamSource(0, vb, 0);
            dev.Indices = ib;
            dev.DrawIndexedPrimitives(PrimitiveType.TriangleList, 0, 0, quadVerts.Length, 0, indicies.Length / 3);
        }
        finally
        {
            if (vb != null)
            {
                vb.Dispose();
                vb = null;
            }
             
            if (ib != null)
            {
                ib.Dispose();
                ib = null;
            }
             
        }
    }

    private Matrix toothTranslationMatrix(ToothGraphic toothGraphic) throws Exception {
        Matrix tran = Matrix.Identity;
        if (ToothGraphic.isRight(toothGraphic.getToothID()))
        {
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                //UR
                tran.Translate(toothGraphic.ShiftM, -toothGraphic.ShiftO, toothGraphic.ShiftB);
            }
            else
            {
                //LR
                tran.Translate(toothGraphic.ShiftM, toothGraphic.ShiftO, toothGraphic.ShiftB);
            } 
        }
        else
        {
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                //UL
                tran.Translate(-toothGraphic.ShiftM, -toothGraphic.ShiftO, toothGraphic.ShiftB);
            }
            else
            {
                //LL
                tran.Translate(-toothGraphic.ShiftM, toothGraphic.ShiftO, toothGraphic.ShiftB);
            } 
        } 
        return tran;
    }

    private Matrix toothRotationMatrix(ToothGraphic toothGraphic) throws Exception {
        //1: tipM last
        //2: tipB second
        //3: rotate first
        Matrix rotM = Matrix.Identity;
        Matrix rotB = Matrix.Identity;
        Matrix rot = Matrix.Identity;
        if (ToothGraphic.isRight(toothGraphic.getToothID()))
        {
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                //UR
                rotM.RotateZ(((float)(toothGraphic.TipM * Math.PI) / 180));
                //Converts angle to radians as required.
                rotB.RotateX(((float)(-toothGraphic.TipB * Math.PI) / 180));
                //Converts angle to radians as required.
                rot.RotateY(((float)(toothGraphic.Rotate * Math.PI) / 180));
            }
            else
            {
                //Converts angle to radians as required.
                //LR
                rotM.RotateZ(((float)(-toothGraphic.TipM * Math.PI) / 180));
                //Converts angle to radians as required.
                rotB.RotateX(((float)(toothGraphic.TipB * Math.PI) / 180));
                //Converts angle to radians as required.
                rot.RotateY(((float)(-toothGraphic.Rotate * Math.PI) / 180));
            } 
        }
        else
        {
            //Converts angle to radians as required.
            if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
            {
                //UL
                rotM.RotateZ(((float)(-toothGraphic.TipM * Math.PI) / 180));
                //Converts angle to radians as required.
                rotB.RotateX(((float)(-toothGraphic.TipB * Math.PI) / 180));
                //Converts angle to radians as required.
                rot.RotateY(((float)(toothGraphic.Rotate * Math.PI) / 180));
            }
            else
            {
                //Converts angle to radians as required.
                //LL
                rotM.RotateZ(((float)(toothGraphic.TipM * Math.PI) / 180));
                //Converts angle to radians as required.
                rotB.RotateX(((float)(toothGraphic.TipB * Math.PI) / 180));
                //Converts angle to radians as required.
                rot.RotateY(((float)(-toothGraphic.Rotate * Math.PI) / 180));
            } 
        } 
        return rot * rotB * rotM;
    }

    //Converts angle to radians as required.
    /**
    * Performs the rotations and translations entered by user for this tooth.  Usually, all numbers are just 0, resulting in no movement here. Returns the result as a Matrix that will need to be applied to any other movement and rotation matricies being applied to the tooth.
    */
    private Matrix toothRotationAndTranslationMatrix(ToothGraphic toothGraphic) throws Exception {
        return toothRotationMatrix(toothGraphic) * toothTranslationMatrix(toothGraphic);
    }

    //remembering that they actually show in the opposite order, so:
    //1: translate
    //2: tipM last
    //3: tipB second
    //4: rotate first
    /**
    * Pri or perm tooth numbers are valid.  Only locations of perm teeth are stored.
    */
    private float getTransX(String tooth_id) throws Exception {
        int toothInt = ToothGraphic.idToInt(tooth_id);
        if (toothInt == -1)
        {
            throw new ApplicationException("Invalid tooth number: " + tooth_id);
        }
         
        return ToothGraphic.getDefaultOrthoXpos(toothInt);
    }

    //only for debugging
    private float getTransYfacial(String tooth_id) throws Exception {
        float basic = 29f;
        if (StringSupport.equals(tooth_id, "6") || StringSupport.equals(tooth_id, "11"))
        {
            return basic + 1f;
        }
         
        if (StringSupport.equals(tooth_id, "7") || StringSupport.equals(tooth_id, "10"))
        {
            return basic + 1f;
        }
        else if (StringSupport.equals(tooth_id, "8") || StringSupport.equals(tooth_id, "9"))
        {
            return basic + 2f;
        }
        else if (StringSupport.equals(tooth_id, "22") || StringSupport.equals(tooth_id, "27"))
        {
            return -basic - 2f;
        }
        else if (StringSupport.equals(tooth_id, "23") || StringSupport.equals(tooth_id, "24") || StringSupport.equals(tooth_id, "25") || StringSupport.equals(tooth_id, "26"))
        {
            return -basic - 2f;
        }
        else if (ToothGraphic.isMaxillary(tooth_id))
        {
            return basic;
        }
             
        return -basic;
    }

    private float getTransYocclusal(String tooth_id) throws Exception {
        if (ToothGraphic.isMaxillary(tooth_id))
        {
            return 13f;
        }
         
        return -13f;
    }

    private void drawNumbersAndLines() throws Exception {
        //Draw the center line.
        Line centerLine = new Line(device);
        centerLine.Width = 1f * TcData.PixelScaleRatio;
        centerLine.Antialias = false;
        centerLine.Begin();
        //Must call Line.Begin() in order for Antialias=false to take effect.
        centerLine.Draw(new Vector2[]{ new Vector2(-1, this.Height / 2), new Vector2(this.Width, this.Height / 2) }, Color.White);
        centerLine.End();
        //Draw the tooth numbers.
        String tooth_id = new String();
        for (int i = 1;i <= 52;i++)
        {
            tooth_id = Tooth.fromOrdinal(i);
            if (TcData.getSelectedTeeth().Contains(tooth_id))
            {
                DrawNumber(tooth_id, true, 0);
            }
            else
            {
                DrawNumber(tooth_id, false, 0);
            } 
        }
    }

    //TimeSpan displayTime=(frameEndTime-frameBeginTime);
    //float fps=1000f/displayTime.Milliseconds;
    //this.PrintString(fps.ToString(),0,0,0,Color.Blue,xfont);
    private void drawNumbersAndLinesPerio(float baseY) throws Exception {
        device.RenderState.Lighting = false;
        device.RenderState.ZBufferEnable = false;
        //Draw the center line.
        device.Transform.World = Matrix.Identity;
        Matrix lineMatrix = screenSpaceMatrix();
        Line centerLine = new Line(device);
        centerLine.Width = 2.5f;
        centerLine.Antialias = false;
        centerLine.Begin();
        //Must call Line.Begin() in order for Antialias=false to take effect.
        centerLine.DrawTransform(new Vector3[]{ new Vector3(-65f, baseY, 0), new Vector3(65f, baseY, 0) }, lineMatrix, Color.Black);
        centerLine.End();
        //Draw the tooth numbers.
        String tooth_id = new String();
        for (int i = 1;i <= 32;i++)
        {
            tooth_id = Tooth.fromOrdinal(i);
            //bool isSelected=TcData.SelectedTeeth.Contains(tooth_id);
            float yOffset = ToothGraphic.isMaxillary(tooth_id) ? 30 : -29;
            drawNumber(tooth_id,false,baseY + yOffset);
        }
    }

    /**
    * Draws the number and the small rectangle behind it.  Draws in the appropriate color.  isFullRedraw means that all of the toothnumbers are being redrawn.  This helps with a few optimizations and with not painting blank rectangles when not needed.
    */
    private void drawNumber(String tooth_id, boolean isSelected, float offsetY) throws Exception {
        if (!Tooth.isValidDB(tooth_id))
        {
            return ;
        }
         
        if (TcData.ListToothGraphics.get___idx(tooth_id).getHideNumber())
        {
            return ;
        }
         
        //if this is a "hidden" number
        //skip
        //primary, but not set to show primary letters
        if (Tooth.isPrimary(tooth_id) && !TcData.ListToothGraphics.get___idx(Tooth.priToPerm(tooth_id)).ShowPrimaryLetter)
        {
            return ;
        }
         
        device.RenderState.Lighting = false;
        device.RenderState.ZBufferEnable = false;
        device.Transform.World = Matrix.Identity;
        String displayNum = Tooth.getToothLabelGraphic(tooth_id,TcData.ToothNumberingNomenclature);
        SizeF labelSize = measureStringMm(displayNum);
        RectangleF recMm = TcData.getNumberRecMm(tooth_id,labelSize);
        recMm.Y += offsetY;
        Color foreColor = TcData.ColorText;
        if (isSelected)
        {
            foreColor = TcData.ColorTextHighlight;
            //Draw the background rectangle only if the text is selected.
            int backColorARGB = TcData.ColorBackHighlight.ToArgb();
            //LL
            //UL
            //UR
            CustomVertex.PositionColored[] quadVerts = new CustomVertex.PositionColored[]{ new CustomVertex.PositionColored(recMm.X, recMm.Y, 0, backColorARGB), new CustomVertex.PositionColored(recMm.X, recMm.Y + recMm.Height, 0, backColorARGB), new CustomVertex.PositionColored(recMm.X + recMm.Width, recMm.Y + recMm.Height, 0, backColorARGB), new CustomVertex.PositionColored(recMm.X + recMm.Width, recMm.Y, 0, backColorARGB) };
            //LR
            VertexBuffer vb = new VertexBuffer(CustomVertex.PositionColored.class, CustomVertex.PositionColored.StrideSize * quadVerts.Length, device, Usage.WriteOnly, CustomVertex.PositionColored.Format, Pool.Managed);
            vb.SetData(quadVerts, 0, LockFlags.None);
            int[] indicies = new int[]{ 0, 1, 2, 0, 2, 3 };
            IndexBuffer ib = new IndexBuffer(int.class, indicies.Length, device, Usage.None, Pool.Managed);
            ib.SetData(indicies, 0, LockFlags.None);
            device.VertexFormat = CustomVertex.PositionColored.Format;
            device.SetStreamSource(0, vb, 0);
            device.Indices = ib;
            device.DrawIndexedPrimitives(PrimitiveType.TriangleList, 0, 0, quadVerts.Length, 0, indicies.Length / 3);
            ib.Dispose();
            vb.Dispose();
        }
         
        //Offsets the text by 10% of the rectangle width to ensure that there is at least on pixel of space on
        //the left for the background rectangle.
        PrintString(displayNum, recMm.X + recMm.Width * 0.1f, recMm.Y + recMm.Height, 0, foreColor, xfont);
    }

    private SizeF measureStringMm(String text) throws Exception {
        Rectangle rectSize = xfont.MeasureString(null, text, DrawTextFormat.VerticalCenter, Color.White);
        return new SizeF((rectSize.Width * 1.25f) / TcData.ScaleMmToPix, (rectSize.Height * 0.8f) / TcData.ScaleMmToPix);
    }

    //DirectX appears to add more vertical spacing than GDI+. We scale the height here to 80% as a result.
    //DirectX does not appear to add horizontal spacing into the width consideration. As a result, we widen
    //the output by 25% to ensure that the highlight border around the text has a border all the way around.
    private void printString(String text, float x, float y, float z, Color color, Microsoft.DirectX.Direct3D.Font printFont) throws Exception {
        Vector3 screenPoint = new Vector3(x, y, z);
        screenPoint.Project(device.Viewport, device.Transform.Projection, device.Transform.View, device.Transform.World);
        printFont.DrawText(null, text, new Point((int)Math.Ceiling(screenPoint.X), (int)Math.Floor(screenPoint.Y)), color);
    }

    private void drawWatches(Matrix defOrient) throws Exception {
        device.RenderState.ZBufferEnable = false;
        device.RenderState.Lighting = false;
        Hashtable watchTeeth = new Hashtable(TcData.ListToothGraphics.Count);
        for (int t = 0;t < TcData.ListToothGraphics.Count;t++)
        {
            //loop through each adult tooth
            ToothGraphic toothGraphic = TcData.ListToothGraphics.get___idx(t);
            //If a tooth is marked to be watched then it is always visible, even if the tooth is missing/hidden.
            if (StringSupport.equals(toothGraphic.getToothID(), "implant") || !toothGraphic.Watch || Tooth.isPrimary(toothGraphic.getToothID()))
            {
                continue;
            }
             
            watchTeeth[toothGraphic.getToothID()] = toothGraphic;
        }
        for (int t = 0;t < TcData.ListToothGraphics.Count;t++)
        {
            //loop through each primary tooth
            ToothGraphic toothGraphic = TcData.ListToothGraphics.get___idx(t);
            //If a tooth is marked to be watched then it is always visible, even if the tooth is missing/hidden.
            if (StringSupport.equals(toothGraphic.getToothID(), "implant") || !toothGraphic.Watch || !Tooth.isPrimary(toothGraphic.getToothID()) || !toothGraphic.getVisible())
            {
                continue;
            }
             
            watchTeeth[Tooth.priToPerm(toothGraphic.getToothID())] = toothGraphic;
        }
        for (Object __dummyForeachVar2 : watchTeeth)
        {
            DictionaryEntry toothGraphic = (DictionaryEntry)__dummyForeachVar2;
            renderToothWatch((ToothGraphic)toothGraphic.Value,defOrient);
        }
    }

    private void renderToothWatch(ToothGraphic toothGraphic, Matrix defOrient) throws Exception {
        Matrix toothTrans = Matrix.Identity;
        //Start with world transform defined by calling function.
        if (ToothGraphic.isRight(toothGraphic.getToothID()))
        {
            toothTrans.Translate(getTransX(toothGraphic.getToothID()) + toothGraphic.ShiftM, 0, 0);
        }
        else
        {
            toothTrans.Translate(getTransX(toothGraphic.getToothID()) - toothGraphic.ShiftM, 0, 0);
        } 
        device.Transform.World = toothTrans * defOrient;
        float toMm = 1f / TcData.ScaleMmToPix;
        if (ToothGraphic.isMaxillary(toothGraphic.getToothID()))
        {
            PrintString("W", TcData.PixelScaleRatio * (-8f * toMm), TcData.PixelScaleRatio * (155f * toMm), -6f, Color.White, xWatchFontBig);
            //Just white for now.
            PrintString("W", TcData.PixelScaleRatio * (-6f * toMm), TcData.PixelScaleRatio * (153f * toMm), -6f, toothGraphic.colorWatch, xWatchFont);
        }
        else
        {
            PrintString("W", TcData.PixelScaleRatio * (-8f * toMm), TcData.PixelScaleRatio * (-136f * toMm), -6f, Color.White, xWatchFontBig);
            //Just white for now.
            PrintString("W", TcData.PixelScaleRatio * (-6f * toMm), TcData.PixelScaleRatio * (-138f * toMm), -6f, toothGraphic.colorWatch, xWatchFont);
        } 
    }

    private void drawDrawingSegments() throws Exception {
        device.RenderState.Lighting = false;
        device.RenderState.ZBufferEnable = false;
        device.Transform.World = Matrix.Identity;
        Matrix lineMatrix = screenSpaceMatrix();
        Line line = new Line(device);
        line.Width = 2.2f * TcData.PixelScaleRatio;
        line.Begin();
        for (int s = 0;s < TcData.DrawingSegmentList.Count;s++)
        {
            String[] pointStr = TcData.DrawingSegmentList[s].DrawingSegment.Split(';');
            List<Vector3> points = new List<Vector3>();
            for (int p = 0;p < pointStr.Length;p++)
            {
                String[] xy = pointStr[p].Split(',');
                if (xy.Length == 2)
                {
                    Point point = new Point((int)(float.Parse(xy[0])), (int)(float.Parse(xy[1])));
                    //if we set 0,0 to center, then this is where we would convert it back.
                    PointF pointMm = TcData.pointDrawingPixToMm(point);
                    points.Add(new Vector3(pointMm.X, pointMm.Y, 0f));
                }
                 
            }
            //Convert each line strip into very simple two point lines so that line extensions can be calculated more easily below.
            List<Vector3> twoPointLines = new List<Vector3>();
            for (int j = 0;j < points.Count - 1;j++)
            {
                twoPointLines.Add(new Vector3(points[j].X, points[j].Y, points[j].Z));
                twoPointLines.Add(new Vector3(points[j + 1].X, points[j + 1].Y, points[j + 1].Z));
            }
            for (int j = 0;j < twoPointLines.Count;j += 2)
            {
                //Draw each individual two point line. The lines must be broken down from line strips so that when individual two point
                //line locations are modified they do not affect any other two point lines within the same line strip.
                //All lines are expanded on both sides here, because the drawing could end with a loop and the loop must be closed.
                Vector3 p1 = (Vector3)twoPointLines[j];
                Vector3 p2 = (Vector3)twoPointLines[j + 1];
                Vector3 lineDir = p2 - p1;
                lineDir.Normalize();
                //Gives the line direction a single unit length.
                float extSize = 0.25f;
                //The number of units to extend each end of the two point line.
                p1 = p1 - extSize * lineDir;
                p2 = p2 + extSize * lineDir;
                Vector3[] lineVerts = new Vector3[]{ p1, p2 };
                line.DrawTransform(lineVerts, lineMatrix, TcData.DrawingSegmentList[s].ColorDraw);
            }
        }
        for (int p = 1;p < TcData.PointList.Count;p++)
        {
            //no filled circle at intersections
            //Draw the points that make up the segment which is currently being drawn
            //but which has not yet been sent to the database.
            PointF pMm1 = TcData.PointPixToMm(TcData.PointList[p]);
            PointF pMm2 = TcData.PointPixToMm(TcData.PointList[p - 1]);
            line.DrawTransform(new Vector3[]{ new Vector3(pMm1.X, pMm1.Y, 0f), new Vector3(pMm2.X, pMm2.Y, 0f) }, lineMatrix, TcData.ColorDrawing);
        }
        line.End();
        line.Dispose();
    }

    private void toothChartDirectX_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        MouseIsDown = true;
        if (TcData.CursorTool == SparksToothChart.CursorTool.Pointer)
        {
            String toothClicked = TcData.GetToothAtPoint(e.Location);
            if (TcData.getSelectedTeeth().Contains(toothClicked))
            {
                setSelected(toothClicked,false);
            }
            else
            {
                setSelected(toothClicked,true);
            } 
            Invalidate();
            Application.DoEvents();
        }
        else //Force redraw.
        if (TcData.CursorTool == SparksToothChart.CursorTool.Pen)
        {
            TcData.PointList.Add(new Point(e.X, e.Y));
        }
        else if (TcData.CursorTool == SparksToothChart.CursorTool.Eraser)
        {
        }
        else //do nothing
        if (TcData.CursorTool == SparksToothChart.CursorTool.ColorChanger)
        {
            //look for any lines near the "wand".
            //since the line segments are so short, it's sufficient to check end points.
            String[] xy = new String[]();
            String[] pointStr = new String[]();
            float x = new float();
            float y = new float();
            float dist = new float();
            //the distance between the point being tested and the center of the eraser circle.
            float radius = 2f;
            for (int i = 0;i < TcData.DrawingSegmentList.Count;i++)
            {
                //by trial and error to achieve best feel.
                pointStr = TcData.DrawingSegmentList[i].DrawingSegment.Split(';');
                for (int p = 0;p < pointStr.Length;p++)
                {
                    xy = pointStr[p].Split(',');
                    if (xy.Length == 2)
                    {
                        x = float.Parse(xy[0]);
                        y = float.Parse(xy[1]);
                        dist = (float)Math.Sqrt(Math.Pow(Math.Abs(x - e.X), 2) + Math.Pow(Math.Abs(y - e.Y), 2));
                        if (dist <= radius)
                        {
                            //testing circle intersection here
                            OnSegmentDrawn(TcData.DrawingSegmentList[i].DrawingSegment);
                            TcData.DrawingSegmentList[i].ColorDraw = TcData.ColorDrawing;
                            Invalidate();
                            return ;
                                ;
                        }
                         
                    }
                     
                }
            }
        }
            
    }

    private void toothChartDirectX_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        if (TcData.CursorTool == SparksToothChart.CursorTool.Pointer)
        {
            hotTooth = TcData.GetToothAtPoint(e.Location);
            if (StringSupport.equals(hotTooth, hotToothOld))
            {
                return ;
            }
             
            //mouse has not moved to another tooth
            if (MouseIsDown)
            {
                //drag action
                List<String> affectedTeeth = TcData.GetAffectedTeeth(hotToothOld, hotTooth, TcData.PointPixToMm(e.Location).Y);
                for (int i = 0;i < affectedTeeth.Count;i++)
                {
                    if (TcData.getSelectedTeeth().Contains(affectedTeeth[i]))
                    {
                        SetSelected(affectedTeeth[i], false);
                    }
                    else
                    {
                        SetSelected(affectedTeeth[i], true);
                    } 
                }
                hotToothOld = hotTooth;
                Invalidate();
                Application.DoEvents();
            }
            else
            {
                hotToothOld = hotTooth;
            } 
        }
        else if (TcData.CursorTool == SparksToothChart.CursorTool.Pen)
        {
            if (!MouseIsDown)
            {
                return ;
            }
             
            TcData.PointList.Add(new Point(e.X, e.Y));
            Invalidate();
            Application.DoEvents();
        }
        else if (TcData.CursorTool == SparksToothChart.CursorTool.Eraser)
        {
            if (!MouseIsDown)
            {
                return ;
            }
             
            //look for any lines that intersect the "eraser".
            //since the line segments are so short, it's sufficient to check end points.
            String[] xy = new String[]();
            String[] pointStr = new String[]();
            float x = new float();
            float y = new float();
            float dist = new float();
            //the distance between the point being tested and the center of the eraser circle.
            float radius = 8f;
            //by trial and error to achieve best feel.
            PointF eraserPt = new PointF(e.X + 8.49f, e.Y + 8.49f);
            for (int i = 0;i < TcData.DrawingSegmentList.Count;i++)
            {
                pointStr = TcData.DrawingSegmentList[i].DrawingSegment.Split(';');
                for (int p = 0;p < pointStr.Length;p++)
                {
                    xy = pointStr[p].Split(',');
                    if (xy.Length == 2)
                    {
                        x = float.Parse(xy[0]);
                        y = float.Parse(xy[1]);
                        dist = (float)Math.Sqrt(Math.Pow(Math.Abs(x - eraserPt.X), 2) + Math.Pow(Math.Abs(y - eraserPt.Y), 2));
                        if (dist <= radius)
                        {
                            //testing circle intersection here
                            OnSegmentDrawn(TcData.DrawingSegmentList[i].DrawingSegment);
                            //triggers a deletion from db.
                            TcData.DrawingSegmentList.RemoveAt(i);
                            Invalidate();
                            Application.DoEvents();
                            return ;
                                ;
                        }
                         
                    }
                     
                }
            }
        }
        else if (TcData.CursorTool == SparksToothChart.CursorTool.ColorChanger)
        {
        }
            
    }

    //do nothing
    private void toothChartDirectX_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        MouseIsDown = false;
        if (TcData.CursorTool == SparksToothChart.CursorTool.Pen)
        {
            String drawingSegment = "";
            for (int i = 0;i < TcData.PointList.Count;i++)
            {
                if (i > 0)
                {
                    drawingSegment += ";";
                }
                 
                //I could compensate to center point here:
                drawingSegment += TcData.PointList[i].X + "," + TcData.PointList[i].Y;
            }
            onSegmentDrawn(drawingSegment);
            TcData.PointList = new List<Point>();
        }
        else //Invalidate();
        if (TcData.CursorTool == SparksToothChart.CursorTool.Eraser)
        {
        }
        else //do nothing
        if (TcData.CursorTool == SparksToothChart.CursorTool.ColorChanger)
        {
        }
           
    }

    //do nothing
    /**
    * 
    */
    protected void onSegmentDrawn(String drawingSegment) throws Exception {
        ToothChartDrawEventArgs tArgs = new ToothChartDrawEventArgs(drawingSegment);
        if (SegmentDrawn != null)
        {
            SegmentDrawn.invoke(this,tArgs);
        }
         
    }

    /**
    * Used by mousedown and mouse move to set teeth selected or unselected.  A similar method is used externally in the wrapper to set teeth selected.  This private method might be faster since it only draws the changes.
    */
    private void setSelected(String tooth_id, boolean setValue) throws Exception {
        TcData.setSelected(tooth_id,setValue);
        if (setValue)
        {
            DrawNumber(tooth_id, true, 0);
        }
        else
        {
            DrawNumber(tooth_id, false, 0);
        } 
    }

    /**
    * Returns a bitmap of what is showing in the control.  Used for printing.
    */
    public Bitmap getBitmap() throws Exception {
        render();
        //Redraw the scene to make sure the back buffer is up to date before copying it to a bitmap.
        Surface backBuffer = device.GetBackBuffer(0, 0, BackBufferType.Mono);
        GraphicsStream gs = SurfaceLoader.SaveToStream(ImageFileFormat.Png, backBuffer);
        Bitmap bitmap = new Bitmap(gs);
        backBuffer.Dispose();
        return bitmap;
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        if (disposing)
        {
            //if(g!=null) {
            //	g.Dispose();
            //}
            cleanupDirectX();
            if (device != null)
            {
                device.Dispose();
                device = null;
            }
             
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.SuspendLayout();
        //
        // ToothChartDirectX
        //
        this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.ToothChartDirectX_MouseMove);
        this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.ToothChartDirectX_MouseDown);
        this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.ToothChartDirectX_MouseUp);
        this.ResumeLayout(false);
    }

}


