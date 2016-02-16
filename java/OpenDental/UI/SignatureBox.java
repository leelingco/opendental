//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.UI;

import CS2JNet.System.StringSupport;


/**
* This class is specifically designed to duplicate the functionality of the Topaz SigPlusNET control.  So even if I would have done it differenly, I didn't have a choice.  Size of box will always be 362,79, although that seems to be arbitrary.  We just don't want to be changing it.
*/
public class SignatureBox  extends Control 
{

    /**
    * 0=not accepting input. 1=accepting input.
    */
    private int tabletState = new int();
    /**
    * Collection of points that will be connected to draw the signature.  0,0 represents pen up.
    */
    private List<Point> pointList = new List<Point>();
    //<summary>0=none, 1=lossless.  Always use 1.</summary>
    //private int compressionMode;
    //<summary>0=clear text. 1=40 bit DES.  2=higher security.</summary>
    //private int encryptionMode;
    /**
    * The hash of the document which will be used to encrypt the signature.
    */
    private byte[] hash = new byte[]();
    private boolean mouseIsDown = new boolean();
    public SignatureBox() throws Exception {
        initializeComponent();
        pointList = new List<Point>();
    }

    /**
    * Set to 1 to activate it to start accepting signatures.  Set to 0 to no longer accept input.
    */
    public void setTabletState(int state) throws Exception {
        tabletState = state;
    }

    /**
    * 1 if the control is accepting signature input. 0 if not.
    */
    public int getTabletState() throws Exception {
        return tabletState;
    }

    /**
    * Clears the display and the stored signature.
    */
    public void clearTablet() throws Exception {
        pointList = new List<Point>();
        Invalidate();
    }

    public int numberOfTabletPoints() throws Exception {
        return pointList.Count;
    }

    /*
    		///<summary>0=none, 1=lossless.  2-8 not used?</summary>
    		public void SetSigCompressionMode(int compressMode){
    			compressionMode=compressMode;
    		}
    		///<summary>0=clear text. 1=low DES (do not use).  2=high Rijndael.</summary>
    		public void SetEncryptionMode(int encryptMode){
    			encryptionMode=encryptMode;
    		}*/
    /**
    * Set it to "0000000000000000" (16 zeros) to indicate no key string to be used for encryption.  Use this OR SetAutoKeyData.
    */
    public void setKeyString(String keyStr) throws Exception {
        UTF8Encoding enc = new UTF8Encoding();
        hash = enc.GetBytes(keyStr);
    }

    /**
    * The data that's begin signed.  A 16 byte hash will be created off this data, and used to encrypt the signature.  Use this OR SetKeyString.  But once the choice is made for a particular data type, it may never be changed.
    */
    public void setAutoKeyData(String keyData) throws Exception {
        byte[] data = Encoding.UTF8.GetBytes(keyData);
        HashAlgorithm algorithm = MD5.Create();
        hash = algorithm.ComputeHash(data);
    }

    //always results in length of 16.
    /**
    * Encrypts signature text and returns a base 64 string so that it can go directly into the database.
    */
    public String getSigString() throws Exception {
        if (pointList.Count == 0)
        {
            return "";
        }
         
        String rawString = "";
        for (int i = 0;i < pointList.Count;i++)
        {
            if (i > 0)
            {
                rawString += ";";
            }
             
            rawString += pointList[i].X.ToString() + "," + pointList[i].Y.ToString();
        }
        byte[] sigBytes = Encoding.UTF8.GetBytes(rawString);
        MemoryStream ms = new MemoryStream();
        //Compression could have been done here, using DeflateStream
        //A decision was made not to use compression because it would have taken more time and not saved much space.
        //DeflateStream compressedzipStream = new DeflateStream(ms , CompressionMode.Compress, true);
        //Now, we have the compressed bytes.  Need to encrypt them.
        Rijndael crypt = Rijndael.Create();
        crypt.KeySize = 128;
        //16 bytes;  Because this is 128 bits, it is the same as AES.
        crypt.Key = hash;
        crypt.IV = new byte[16];
        CryptoStream cs = new CryptoStream(ms, crypt.CreateEncryptor(), CryptoStreamMode.Write);
        cs.Write(sigBytes, 0, sigBytes.Length);
        cs.FlushFinalBlock();
        byte[] encryptedBytes = new byte[ms.Length];
        ms.Position = 0;
        ms.Read(encryptedBytes, 0, (int)ms.Length);
        cs.Dispose();
        ms.Dispose();
        return Convert.ToBase64String(encryptedBytes);
    }

    /**
    * Unencrypts the signature coming in from the database.  The key data etc needs to be set first before calling this function.
    */
    public void setSigString(String sigString) throws Exception {
        pointList = new List<Point>();
        if (StringSupport.equals(sigString, ""))
        {
            return ;
        }
         
        try
        {
            //convert base64 string to bytes
            byte[] encryptedBytes = Convert.FromBase64String(sigString);
            //create the streams
            MemoryStream ms = new MemoryStream();
            //ms.Write(encryptedBytes,0,(int)encryptedBytes.Length);
            //create a crypto stream
            Rijndael crypt = Rijndael.Create();
            crypt.KeySize = 128;
            //16 bytes;
            crypt.Key = hash;
            crypt.IV = new byte[16];
            CryptoStream cs = new CryptoStream(ms, crypt.CreateDecryptor(), CryptoStreamMode.Write);
            cs.Write(encryptedBytes, 0, encryptedBytes.Length);
            cs.FlushFinalBlock();
            byte[] sigBytes = new byte[ms.Length];
            ms.Position = 0;
            ms.Read(sigBytes, 0, (int)ms.Length);
            cs.Dispose();
            ms.Dispose();
            //now convert the bytes into a string.
            String rawString = Encoding.UTF8.GetString(sigBytes);
            //convert the raw string into a series of points
            String[] pointArray = rawString.Split(new char[]{ ';' });
            Point pt = new Point();
            String[] coords = new String[]();
            for (int i = 0;i < pointArray.Length;i++)
            {
                coords = pointArray[i].Split(new char[]{ ',' });
                pt = new Point(Convert.ToInt32(coords[0]), Convert.ToInt32(coords[1]));
                pointList.Add(pt);
            }
            Invalidate();
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

    //this will leave the list with zero points
    /**
    * Also includes a surrounding box.
    */
    public Image getSigImage(boolean includeBorder) throws Exception {
        Image img = new Bitmap(Width, Height);
        Graphics g = Graphics.FromImage(img);
        g.FillRectangle(Brushes.White, 0, 0, this.Width, this.Height);
        Pen pen = new Pen(Color.Black, 2f);
        g.SmoothingMode = SmoothingMode.HighQuality;
        for (int i = 1;i < pointList.Count;i++)
        {
            //skip the first point
            if (pointList[i - 1].X == 0 && pointList[i - 1].Y == 0)
            {
                continue;
            }
             
            if (pointList[i].X == 0 && pointList[i].Y == 0)
            {
                continue;
            }
             
            g.DrawLine(pen, pointList[i - 1], pointList[i]);
        }
        if (includeBorder)
        {
            g.DrawRectangle(Pens.Black, 0, 0, Width - 1, Height - 1);
        }
         
        g.Dispose();
        return img;
    }

    /**
    * 
    */
    protected void onPaintBackground(PaintEventArgs pea) throws Exception {
    }

    //base.OnPaintBackground (pea);
    //don't paint background.  This reduces flickering when using double buffering.
    protected void onPaint(PaintEventArgs e) throws Exception {
        Bitmap doubleBuffer = new Bitmap(Width, Height, e.Graphics);
        Graphics g = Graphics.FromImage(doubleBuffer);
        g.FillRectangle(Brushes.White, 0, 0, this.Width, this.Height);
        Pen pen = new Pen(Color.Black, 2f);
        g.SmoothingMode = SmoothingMode.HighQuality;
        for (int i = 1;i < pointList.Count;i++)
        {
            //skip the first point
            if (pointList[i - 1].X == 0 && pointList[i - 1].Y == 0)
            {
                continue;
            }
             
            if (pointList[i].X == 0 && pointList[i].Y == 0)
            {
                continue;
            }
             
            g.DrawLine(pen, pointList[i - 1], pointList[i]);
        }
        e.Graphics.DrawImageUnscaled(doubleBuffer, 0, 0);
        g.Dispose();
        g = null;
        doubleBuffer.Dispose();
        doubleBuffer = null;
        super.OnPaint(e);
    }

    protected void onMouseDown(MouseEventArgs e) throws Exception {
        super.OnMouseDown(e);
        if (tabletState == 0)
        {
            return ;
        }
         
        mouseIsDown = true;
        pointList.Add(new Point(e.X, e.Y));
    }

    //Invalidate();
    protected void onMouseMove(MouseEventArgs e) throws Exception {
        super.OnMouseMove(e);
        if (tabletState == 0)
        {
            return ;
        }
         
        if (!mouseIsDown)
        {
            return ;
        }
         
        pointList.Add(new Point(e.X, e.Y));
        Invalidate();
    }

    protected void onMouseUp(MouseEventArgs e) throws Exception {
        super.OnMouseUp(e);
        if (tabletState == 0)
        {
            return ;
        }
         
        mouseIsDown = false;
        pointList.Add(new Point(0, 0));
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
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        components = new System.ComponentModel.Container();
    }

}


