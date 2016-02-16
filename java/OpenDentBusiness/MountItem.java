//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.MountItem;
import OpenDentBusiness.TableBase;

/**
* These are always attached to a mount and are constant. Should not be deleted, but rather updated if geometry changes.  Documents are then attached to MountItems using Document.MountItemNum field.
*/
public class MountItem  extends TableBase 
{
    /**
    * Primary key.
    */
    public long MountItemNum = new long();
    /**
    * FK to mount.MountNum.
    */
    public long MountNum = new long();
    /**
    * The x position, in pixels, of the item on the mount.
    */
    public int Xpos = new int();
    /**
    * The y position, in pixels, of the item on the mount.
    */
    public int Ypos = new int();
    /**
    * The ordinal position of the item on the mount.
    */
    public int OrdinalPos = new int();
    /**
    * The scaled or unscaled width of the mount item in pixels.
    */
    public int Width = new int();
    /**
    * The scaled or unscaled height of the mount item in pixels.
    */
    public int Height = new int();
    /**
    * 
    */
    public MountItem copy() throws Exception {
        return (MountItem)this.MemberwiseClone();
    }

}


