//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ImageType;
import OpenDentBusiness.Mount;
import OpenDentBusiness.TableBase;

/**
* A mount shows in the images module just like other images in the tree.  But it is just a container for images within it rather than an actual image itself.
*/
public class Mount  extends TableBase 
{
    /**
    * Primary key.
    */
    public long MountNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * FK to definition.DefNum. Categories for documents.
    */
    public long DocCategory = new long();
    /**
    * The date at which the mount itself was created. Has no bearing on the creation date of the images the mount houses.
    */
    public DateTime DateCreated = new DateTime();
    /**
    * Used to provide a document description in the image module tree-view.
    */
    public String Description = new String();
    /**
    * To allow the user to enter specific information regarding the exam and tooth numbers, as well as points on interest in the xray images.
    */
    public String Note = new String();
    /**
    * Enum:ImageType This is so that an image can be properly associated with the mount in the image module tree-view.
    */
    public ImageType ImgType = ImageType.Document;
    /**
    * The static width of the mount, in pixels.
    */
    public int Width = new int();
    /**
    * The static height of the mount, in pixels.
    */
    public int Height = new int();
    /**
    * 
    */
    public Mount copy() throws Exception {
        return (Mount)this.MemberwiseClone();
    }

}


