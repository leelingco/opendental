//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.ImageType;
import OpenDentBusiness.TableBase;

/**
* Represents a single document in the images module.
*/
public class Document  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DocNum = new long();
    /**
    * Description of the document.
    */
    public String Description = new String();
    /**
    * Date created.
    */
    public DateTime DateCreated = new DateTime();
    /**
    * FK to definition.DefNum. Categories for documents.
    */
    public long DocCategory = new long();
    /**
    * FK to patient.PatNum.  The document will be located in the patient folder of this patient.
    */
    public long PatNum = new long();
    /**
    * The name of the file. Does not include any directory info.
    */
    public String FileName = new String();
    /**
    * Enum:ImageType eg. document, radiograph, photo, file
    */
    public ImageType ImgType = ImageType.Document;
    /**
    * True if flipped horizontally. A vertical flip would be stored as a horizontal flip plus a 180 rotation.
    */
    public boolean IsFlipped = new boolean();
    /**
    * Only allowed 0,90,180, and 270.
    */
    public int DegreesRotated = new int();
    /**
    * Incomplete.  An optional list of tooth numbers separated by commas.  The tooth numbers will be in American format and must be processed for display.  When displayed, dashes will be used for sequences of 3 or more tooth numbers.
    */
    public String ToothNumbers = new String();
    /**
    * .
    */
    public String Note = new String();
    /**
    * True if the signature is in Topaz format rather than OD format.
    */
    public boolean SigIsTopaz = new boolean();
    /**
    * The encrypted and bound signature in base64 format.  The signature is bound to the byte sequence of the original image.
    */
    public String Signature = new String();
    /**
    * Crop rectangle X in original image pixel coordinates.  May be negative.
    */
    public int CropX = new int();
    /**
    * Crop rectangle Y in original image pixel coordinates.  May be negative.
    */
    public int CropY = new int();
    /**
    * Crop rectangle Width in original image pixel coordinates.  May be zero if no cropping.  May be greater than original image width.
    */
    public int CropW = new int();
    /**
    * Crop rectangle Height in original image pixel coordinates.  May be zero if no cropping.  May be greater than original image height.
    */
    public int CropH = new int();
    /**
    * The lower value of the "windowing" (contrast/brightness) for radiographs.  Default is 0.  Max is 255.
    */
    public int WindowingMin = new int();
    /**
    * The upper value of the "windowing" (contrast/brightness) for radiographs.  Default is 0(no windowing).  Max is 255.
    */
    public int WindowingMax = new int();
    /**
    * FK to mountitem.MountItemNum. If set, then this image will only show on a mount, not in the main tree. If set to 0, then no mount item is associated with this document.
    */
    public long MountItemNum = new long();
    /**
    * Date/time last altered.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * The raw file data encoded as base64.  Only used if there is no AtoZ folder.
    */
    public String RawBase64 = new String();
    /**
    * Thumbnail encoded as base64.  Only present if not using AtoZ folder. 100x100 pixels, jpg, takes around 5.5k.
    */
    public String Thumbnail = new String();
    /**
    * Returns a copy of this Document.
    */
    public OpenDentBusiness.Document copy() throws Exception {
        return (OpenDentBusiness.Document)this.MemberwiseClone();
    }

}


