//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ImageRepeat;
import fyiReporting.RDL.ImageSizingEnum;
import fyiReporting.RDL.PageItem;

public class PageImage  extends PageItem implements ICloneable
{
    String name = new String();
    // name of object if constant image
    ImageFormat imf = new ImageFormat();
    // type of image; png, jpeg are supported
    byte[] imageData = new byte[]();
    int samplesW = new int();
    int samplesH = new int();
    ImageRepeat repeat = ImageRepeat.Repeat;
    ImageSizingEnum sizing = ImageSizingEnum.AutoSize;
    public PageImage(ImageFormat im, byte[] image, int w, int h) throws Exception {
        Debug.Assert(im == ImageFormat.Jpeg || im == ImageFormat.Png || im == ImageFormat.Gif, "PageImage only supports Jpeg, Gif and Png image formats.");
        imf = im;
        imageData = image;
        samplesW = w;
        samplesH = h;
        repeat = ImageRepeat.NoRepeat;
        sizing = ImageSizingEnum.AutoSize;
    }

    public byte[] getImageData() throws Exception {
        return imageData;
    }

    public ImageFormat getImgFormat() throws Exception {
        return imf;
    }

    public String getName() throws Exception {
        return name;
    }

    public void setName(String value) throws Exception {
        name = value;
    }

    public ImageRepeat getRepeat() throws Exception {
        return repeat;
    }

    public void setRepeat(ImageRepeat value) throws Exception {
        repeat = value;
    }

    public ImageSizingEnum getSizing() throws Exception {
        return sizing;
    }

    public void setSizing(ImageSizingEnum value) throws Exception {
        sizing = value;
    }

    public int getSamplesW() throws Exception {
        return samplesW;
    }

    public int getSamplesH() throws Exception {
        return samplesH;
    }

    public Object clone() {
        try
        {
            return this.MemberwiseClone();
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

}


