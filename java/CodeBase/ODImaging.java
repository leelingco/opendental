//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package CodeBase;

import CS2JNet.JavaSupport.language.RefSupport;

public class ODImaging   
{
    /**
    * Returns the bits per channel, channels per pixel and bytes per pixel of the given pixel format. However, the pixel format must contain the same number of bits per channel. Additionally, future pixel formats may not be supported by this function.
    */
    public static void getFormatInfo(PixelFormat pf, RefSupport<int> bitsPerChannel, RefSupport<int> channelsPerPixel, RefSupport<int> bytesPerPixel) throws Exception {
        //	PixelFormat																#bits/channel		#channels/pixel		#bytes/pixel
        int[] formatDataTable = new int[]{ (int)PixelFormat.Format16bppGrayScale, 16, 1, 2, (int)PixelFormat.Format16bppRgb555, 5, 3, 2, (int)PixelFormat.Format1bppIndexed, 1, 1, 1, (int)PixelFormat.Format24bppRgb, 8, 3, 3, (int)PixelFormat.Format32bppArgb, 8, 4, 4, (int)PixelFormat.Format32bppPArgb, 8, 4, 4, (int)PixelFormat.Format32bppRgb, 8, 3, 4, (int)PixelFormat.Format48bppRgb, 16, 3, 6, (int)PixelFormat.Format4bppIndexed, 4, 1, 1, (int)PixelFormat.Format64bppArgb, 16, 4, 8, (int)PixelFormat.Format64bppPArgb, 16, 4, 8, (int)PixelFormat.Format8bppIndexed, 8, 1, 1 };
        for (int i = 0;i < formatDataTable.Length;i += 4)
        {
            if (formatDataTable[i] == (int)pf)
            {
                bitsPerChannel.setValue(formatDataTable[i + 1]);
                channelsPerPixel.setValue(formatDataTable[i + 2]);
                bytesPerPixel.setValue(formatDataTable[i + 3]);
                return ;
            }
             
        }
        throw new Exception("Cannot get bits per channel, channels per pixel and bytes per pixel for unsupported pixel format (" + ((int)pf) + ")");
    }

    /**
    * Performs anti-alias correction on the input image and returns the anti-aliased image in a new memory location (the  input image is unchanged).
    */
    public static Bitmap antiAlias(Bitmap image, int numPasses) throws Exception {
        if (numPasses < 1)
        {
            return image;
        }
         
        BitmapData inData = image.LockBits(new Rectangle(0, 0, image.Width, image.Height), ImageLockMode.ReadWrite, image.PixelFormat);
        byte[] imageBytes = new byte[inData.Stride * inData.Height];
        //For reading the data quickly and efficiently.
        byte[] resultBytes = new byte[inData.Stride * inData.Height];
        //For writing the new data quickly and efficiently.
        Marshal.Copy(inData.Scan0, imageBytes, 0, imageBytes.Length);
        //Make new copy of image as bytes.
        Marshal.Copy(inData.Scan0, resultBytes, 0, resultBytes.Length);
        //Make new copy of image as bytes.
        image.UnlockBits(inData);
        int maskSize = 3;
        double[] mask = new double[]{ 1 / 24.0, 1 / 12.0, 1 / 24.0, 1 / 12.0, 0.5, 1 / 12.0, 1 / 24.0, 1 / 12.0, 1 / 24.0 };
        int bitsPerChannel = 0;
        int channelsPerPixel = 0;
        int bytesPerPixel = 0;
        RefSupport<int> refVar___0 = new RefSupport<int>();
        RefSupport<int> refVar___1 = new RefSupport<int>();
        RefSupport<int> refVar___2 = new RefSupport<int>();
        getFormatInfo(image.PixelFormat,refVar___0,refVar___1,refVar___2);
        bitsPerChannel = refVar___0.getValue();
        channelsPerPixel = refVar___1.getValue();
        bytesPerPixel = refVar___2.getValue();
        for (int x = 1;x < image.Width - 1;x++)
        {
            for (int y = 1;y < image.Height - 1;y++)
            {
                //border pixels unaffected
                //border pixels unaffected
                double[] components = new double[channelsPerPixel];
                for (int i = 0;i < maskSize;i++)
                {
                    for (int j = 0;j < maskSize;j++)
                    {
                        //Sets entire array to zeros.
                        int pixelOffset = inData.Stride * (y - maskSize / 2 + j) + (x - maskSize / 2 + i) * channelsPerPixel;
                        double weight = mask[j * maskSize + i];
                        for (int c = 0;c < channelsPerPixel;c++)
                        {
                            components[c] += imageBytes[pixelOffset + c] * weight;
                        }
                    }
                }
                int resultPixelOffset = inData.Stride * y + x * channelsPerPixel;
                for (int c = 0;c < channelsPerPixel;c++)
                {
                    resultBytes[resultPixelOffset + c] = (byte)Math.Round(components[c]);
                }
            }
        }
        return antiAlias(new Bitmap(image.Width, image.Height, inData.Stride, image.PixelFormat, GCHandle.Alloc(resultBytes, GCHandleType.Pinned).AddrOfPinnedObject()),numPasses - 1);
    }

}


//dummy comment.