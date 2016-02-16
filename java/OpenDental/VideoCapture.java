//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:11 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;

/****************************************************************************
While the underlying libraries are covered by LGPL, this sample is released 
as public domain.  It is distributed in the hope that it will be useful, but 
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE.  
*****************************************************************************/
/**
* 
*/
public class VideoCapture  extends ISampleGrabberCB implements IDisposable
{
    /**
    * graph builder interface.
    */
    private IFilterGraph2 m_FilterGraph = null;
    // Used to snap picture on Still pin
    private IAMVideoControl m_VidControl = null;
    private IPin m_pinStill = null;
    /**
    * so we can wait for the async job to finish
    */
    private ManualResetEvent m_PictureReady = null;
    private boolean m_WantOne = false;
    /**
    * Dimensions of the image, calculated once in constructor for perf.
    */
    private int m_videoWidth = new int();
    private int m_videoHeight = new int();
    private int m_stride = new int();
    /**
    * buffer for bitmap data.  Always release by caller
    */
    private IntPtr m_ipBuffer = IntPtr.Zero;
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ void copyMemory(IntPtr Destination, IntPtr Source, int Length) throws Exception ;

    public static int getDeviceCount() throws Exception {
        return DsDevice.GetDevicesOfCat(FilterCategory.VideoInputDevice).Length;
    }

    // Zero based device index and device params and output window
    public VideoCapture(int iDeviceNum, int iWidth, int iHeight, short iBPP, Control hControl) throws Exception {
        DsDevice[] capDevices = new DsDevice[]();
        // Get the collection of video devices
        capDevices = DsDevice.GetDevicesOfCat(FilterCategory.VideoInputDevice);
        if (iDeviceNum + 1 > capDevices.Length)
        {
            throw new Exception("No video capture devices found at that index!");
        }
         
        try
        {
            // Set up the capture graph
            SetupGraph(capDevices[iDeviceNum], iWidth, iHeight, iBPP, hControl);
            // tell the callback to ignore new images
            m_PictureReady = new ManualResetEvent(false);
        }
        catch (Exception __dummyCatchVar0)
        {
            dispose();
            throw __dummyCatchVar0;
        }
    
    }

    /**
    * release everything.
    */
    public void dispose() throws Exception {
        closeInterfaces();
        if (m_PictureReady != null)
        {
            m_PictureReady.Close();
        }
         
    }

    // Destructor
    protected void finalize() throws Throwable {
        try
        {
            dispose();
        }
        finally
        {
            super.finalize();
        }
    }

    /**
    * Get the image from the Still pin.  The returned image can turned into a bitmap with
    * Bitmap b = new Bitmap(cam.Width, cam.Height, cam.Stride, PixelFormat.Format24bppRgb, m_ip);
    * If the image is upside down, you can fix it with
    * b.RotateFlip(RotateFlipType.RotateNoneFlipY);
    * 
    *  @return Returned pointer to be freed by caller with Marshal.FreeCoTaskMem
    */
    public IntPtr click() throws Exception {
        int hr = new int();
        // get ready to wait for new image
        m_PictureReady.Reset();
        m_ipBuffer = Marshal.AllocCoTaskMem(Math.Abs(m_stride) * m_videoHeight);
        try
        {
            m_WantOne = true;
            // If we are using a still pin, ask for a picture
            if (m_VidControl != null)
            {
                // Tell the camera to send an image
                hr = m_VidControl.SetMode(m_pinStill, VideoControlFlags.Trigger);
                DsError.ThrowExceptionForHR(hr);
            }
             
            // Start waiting
            if (!m_PictureReady.WaitOne(9000, false))
            {
                throw new Exception("Timeout waiting to get picture");
            }
             
        }
        catch (Exception __dummyCatchVar1)
        {
            Marshal.FreeCoTaskMem(m_ipBuffer);
            m_ipBuffer = IntPtr.Zero;
            throw __dummyCatchVar1;
        }

        return m_ipBuffer;
    }

    // Got one
    public int getWidth() throws Exception {
        return m_videoWidth;
    }

    public int getHeight() throws Exception {
        return m_videoHeight;
    }

    public int getStride() throws Exception {
        return m_stride;
    }

    /**
    * build the capture graph for grabber.
    */
    private void setupGraph(DsDevice dev, int iWidth, int iHeight, short iBPP, Control hControl) throws Exception {
        int hr = new int();
        ISampleGrabber sampGrabber = null;
        IBaseFilter capFilter = null;
        IPin pCaptureOut = null;
        IPin pSampleIn = null;
        IPin pRenderIn = null;
        // Get the graphbuilder object
        m_FilterGraph = new FilterGraph() instanceof IFilterGraph2 ? (IFilterGraph2)new FilterGraph() : (IFilterGraph2)null;
        try
        {
            // add the video input device
            RefSupport<IBaseFilter> refVar___0 = new RefSupport<IBaseFilter>();
            hr = m_FilterGraph.AddSourceFilterForMoniker(dev.Mon, null, dev.Name, refVar___0);
            capFilter = refVar___0.getValue();
            DsError.ThrowExceptionForHR(hr);
            // Find the still pin
            m_pinStill = DsFindPin.ByCategory(capFilter, PinCategory.Still, 0);
            // Didn't find one.  Is there a preview pin?
            if (m_pinStill == null)
            {
                m_pinStill = DsFindPin.ByCategory(capFilter, PinCategory.Preview, 0);
            }
             
            // Still haven't found one.  Need to put a splitter in so we have
            // one stream to capture the bitmap from, and one to display.  Ok, we
            // don't *have* to do it that way, but we are going to anyway.
            if (m_pinStill == null)
            {
                IPin pRaw = null;
                IPin pSmart = null;
                // There is no still pin
                m_VidControl = null;
                // Add a splitter
                IBaseFilter iSmartTee = (IBaseFilter)new SmartTee();
                try
                {
                    hr = m_FilterGraph.AddFilter(iSmartTee, "SmartTee");
                    DsError.ThrowExceptionForHR(hr);
                    // Find the find the capture pin from the video device and the
                    // input pin for the splitter, and connnect them
                    pRaw = DsFindPin.ByCategory(capFilter, PinCategory.Capture, 0);
                    pSmart = DsFindPin.ByDirection(iSmartTee, PinDirection.Input, 0);
                    hr = m_FilterGraph.Connect(pRaw, pSmart);
                    DsError.ThrowExceptionForHR(hr);
                    // Now set the capture and still pins (from the splitter)
                    m_pinStill = DsFindPin.ByName(iSmartTee, "Preview");
                    pCaptureOut = DsFindPin.ByName(iSmartTee, "Capture");
                    // If any of the default config items are set, perform the config
                    // on the actual video device (rather than the splitter)
                    if (iHeight + iWidth + iBPP > 0)
                    {
                        setConfigParms(pRaw,iWidth,iHeight,iBPP);
                    }
                     
                }
                finally
                {
                    if (pRaw != null)
                    {
                        Marshal.ReleaseComObject(pRaw);
                    }
                     
                    if (pRaw != pSmart)
                    {
                        Marshal.ReleaseComObject(pSmart);
                    }
                     
                    if (pRaw != iSmartTee)
                    {
                        Marshal.ReleaseComObject(iSmartTee);
                    }
                     
                }
            }
            else
            {
                // Get a control pointer (used in Click())
                m_VidControl = capFilter instanceof IAMVideoControl ? (IAMVideoControl)capFilter : (IAMVideoControl)null;
                pCaptureOut = DsFindPin.ByCategory(capFilter, PinCategory.Capture, 0);
                // If any of the default config items are set
                if (iHeight + iWidth + iBPP > 0)
                {
                    setConfigParms(m_pinStill,iWidth,iHeight,iBPP);
                }
                 
            } 
            // Get the SampleGrabber interface
            sampGrabber = new SampleGrabber() instanceof ISampleGrabber ? (ISampleGrabber)new SampleGrabber() : (ISampleGrabber)null;
            // Configure the sample grabber
            IBaseFilter baseGrabFlt = sampGrabber instanceof IBaseFilter ? (IBaseFilter)sampGrabber : (IBaseFilter)null;
            configureSampleGrabber(sampGrabber);
            pSampleIn = DsFindPin.ByDirection(baseGrabFlt, PinDirection.Input, 0);
            // Get the default video renderer
            IBaseFilter pRenderer = new VideoRendererDefault() instanceof IBaseFilter ? (IBaseFilter)new VideoRendererDefault() : (IBaseFilter)null;
            hr = m_FilterGraph.AddFilter(pRenderer, "Renderer");
            DsError.ThrowExceptionForHR(hr);
            pRenderIn = DsFindPin.ByDirection(pRenderer, PinDirection.Input, 0);
            // Add the sample grabber to the graph
            hr = m_FilterGraph.AddFilter(baseGrabFlt, "Ds.NET Grabber");
            DsError.ThrowExceptionForHR(hr);
            if (m_VidControl == null)
            {
                // Connect the Still pin to the sample grabber
                hr = m_FilterGraph.Connect(m_pinStill, pSampleIn);
                DsError.ThrowExceptionForHR(hr);
                // Connect the capture pin to the renderer
                hr = m_FilterGraph.Connect(pCaptureOut, pRenderIn);
                DsError.ThrowExceptionForHR(hr);
            }
            else
            {
                // Connect the capture pin to the renderer
                hr = m_FilterGraph.Connect(pCaptureOut, pRenderIn);
                DsError.ThrowExceptionForHR(hr);
                // Connect the Still pin to the sample grabber
                hr = m_FilterGraph.Connect(m_pinStill, pSampleIn);
                DsError.ThrowExceptionForHR(hr);
            } 
            // Learn the video properties
            saveSizeInfo(sampGrabber);
            configVideoWindow(hControl);
            // Start the graph
            IMediaControl mediaCtrl = m_FilterGraph instanceof IMediaControl ? (IMediaControl)m_FilterGraph : (IMediaControl)null;
            hr = mediaCtrl.Run();
            DsError.ThrowExceptionForHR(hr);
        }
        finally
        {
            if (sampGrabber != null)
            {
                Marshal.ReleaseComObject(sampGrabber);
                sampGrabber = null;
            }
             
            if (pCaptureOut != null)
            {
                Marshal.ReleaseComObject(pCaptureOut);
                pCaptureOut = null;
            }
             
            if (pRenderIn != null)
            {
                Marshal.ReleaseComObject(pRenderIn);
                pRenderIn = null;
            }
             
            if (pSampleIn != null)
            {
                Marshal.ReleaseComObject(pSampleIn);
                pSampleIn = null;
            }
             
        }
    }

    private void saveSizeInfo(ISampleGrabber sampGrabber) throws Exception {
        int hr = new int();
        // Get the media type from the SampleGrabber
        AMMediaType media = new AMMediaType();
        hr = sampGrabber.GetConnectedMediaType(media);
        DsError.ThrowExceptionForHR(hr);
        if ((media.formatType != FormatType.VideoInfo) || (media.formatPtr == IntPtr.Zero))
        {
            throw new NotSupportedException("Unknown Grabber Media Format");
        }
         
        // Grab the size info
        VideoInfoHeader videoInfoHeader = (VideoInfoHeader)Marshal.PtrToStructure(media.formatPtr, VideoInfoHeader.class);
        m_videoWidth = videoInfoHeader.BmiHeader.Width;
        m_videoHeight = videoInfoHeader.BmiHeader.Height;
        m_stride = m_videoWidth * (videoInfoHeader.BmiHeader.BitCount / 8);
        DsUtils.FreeAMMediaType(media);
        media = null;
    }

    // Set the video window within the control specified by hControl
    private void configVideoWindow(Control hControl) throws Exception {
        int hr = new int();
        IVideoWindow ivw = m_FilterGraph instanceof IVideoWindow ? (IVideoWindow)m_FilterGraph : (IVideoWindow)null;
        // Set the parent
        hr = ivw.put_Owner(hControl.Handle);
        DsError.ThrowExceptionForHR(hr);
        // Turn off captions, etc
        hr = ivw.put_WindowStyle(WindowStyle.Child | WindowStyle.ClipChildren | WindowStyle.ClipSiblings);
        DsError.ThrowExceptionForHR(hr);
        // Yes, make it visible
        hr = ivw.put_Visible(OABool.True);
        DsError.ThrowExceptionForHR(hr);
        // Move to upper left corner
        Rectangle rc = hControl.ClientRectangle;
        hr = ivw.SetWindowPosition(0, 0, rc.Right, rc.Bottom);
        DsError.ThrowExceptionForHR(hr);
    }

    private void configureSampleGrabber(ISampleGrabber sampGrabber) throws Exception {
        int hr = new int();
        AMMediaType media = new AMMediaType();
        // Set the media type to Video/RBG24
        media.majorType = MediaType.Video;
        media.subType = MediaSubType.RGB24;
        media.formatType = FormatType.VideoInfo;
        hr = sampGrabber.SetMediaType(media);
        DsError.ThrowExceptionForHR(hr);
        DsUtils.FreeAMMediaType(media);
        media = null;
        // Configure the samplegrabber
        hr = sampGrabber.SetCallback(this, 1);
        DsError.ThrowExceptionForHR(hr);
    }

    // Set the Framerate, and video size
    private void setConfigParms(IPin pStill, int iWidth, int iHeight, short iBPP) throws Exception {
        int hr = new int();
        AMMediaType media = new AMMediaType();
        VideoInfoHeader v = new VideoInfoHeader();
        IAMStreamConfig videoStreamConfig = pStill instanceof IAMStreamConfig ? (IAMStreamConfig)pStill : (IAMStreamConfig)null;
        // Get the existing format block
        RefSupport<AMMediaType> refVar___1 = new RefSupport<AMMediaType>();
        hr = videoStreamConfig.GetFormat(refVar___1);
        media = refVar___1.getValue();
        DsError.ThrowExceptionForHR(hr);
        try
        {
            // copy out the videoinfoheader
            v = new VideoInfoHeader();
            Marshal.PtrToStructure(media.formatPtr, v);
            // if overriding the width, set the width
            if (iWidth > 0)
            {
                v.BmiHeader.Width = iWidth;
            }
             
            // if overriding the Height, set the Height
            if (iHeight > 0)
            {
                v.BmiHeader.Height = iHeight;
            }
             
            // if overriding the bits per pixel
            if (iBPP > 0)
            {
                v.BmiHeader.BitCount = iBPP;
            }
             
            // Copy the media structure back
            Marshal.StructureToPtr(v, media.formatPtr, false);
            // Set the new format
            hr = videoStreamConfig.SetFormat(media);
            DsError.ThrowExceptionForHR(hr);
        }
        finally
        {
            DsUtils.FreeAMMediaType(media);
            media = null;
        }
    }

    /**
    * Shut down capture
    */
    private void closeInterfaces() throws Exception {
        int hr = new int();
        try
        {
            if (m_FilterGraph != null)
            {
                IMediaControl mediaCtrl = m_FilterGraph instanceof IMediaControl ? (IMediaControl)m_FilterGraph : (IMediaControl)null;
                // Stop the graph
                hr = mediaCtrl.Stop();
            }
             
        }
        catch (Exception ex)
        {
            Debug.WriteLine(ex);
        }

        if (m_FilterGraph != null)
        {
            Marshal.ReleaseComObject(m_FilterGraph);
            m_FilterGraph = null;
        }
         
        if (m_VidControl != null)
        {
            Marshal.ReleaseComObject(m_VidControl);
            m_VidControl = null;
        }
         
        if (m_pinStill != null)
        {
            Marshal.ReleaseComObject(m_pinStill);
            m_pinStill = null;
        }
         
    }

    /**
    * sample callback, NOT USED.
    */
    int iSampleGrabberCB___SampleCB(double SampleTime, IMediaSample pSample) throws Exception {
        Marshal.ReleaseComObject(pSample);
        return 0;
    }

    /**
    * buffer callback, COULD BE FROM FOREIGN THREAD.
    */
    int iSampleGrabberCB___BufferCB(double SampleTime, IntPtr pBuffer, int BufferLen) throws Exception {
        // Note that we depend on only being called once per call to Click.  Otherwise
        // a second call can overwrite the previous image.
        Debug.Assert(BufferLen == Math.Abs(m_stride) * m_videoHeight, "Incorrect buffer length");
        if (m_WantOne)
        {
            m_WantOne = false;
            Debug.Assert(m_ipBuffer != IntPtr.Zero, "Unitialized buffer");
            // Save the buffer
            copyMemory(m_ipBuffer,pBuffer,BufferLen);
            // Picture is ready.
            m_PictureReady.Set();
        }
         
        return 0;
    }

}


