//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.MountItemDef;
import OpenDentBusiness.TableBase;

/**
* THIS TABLE IS NOT BEING USED.  These are always attached to mountdefs.  Can be deleted without any problems.
*/
public class MountItemDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long MountItemDefNum = new long();
    /**
    * FK to mountdef.MountDefNum.
    */
    public long MountDefNum = new long();
    /**
    * The x position, in pixels, of the item on the mount.
    */
    public int Xpos = new int();
    /**
    * The y position, in pixels, of the item on the mount.
    */
    public int Ypos = new int();
    /**
    * Ignored if mount IsRadiograph.  For other mounts, the image will be scaled to fit within this space.  Any cropping, rotating, etc, will all be defined in the original image itself.
    */
    public int Width = new int();
    /**
    * Ignored if mount IsRadiograph.  For other mounts, the image will be scaled to fit within this space.  Any cropping, rotating, etc, will all be defined in the original image itself.
    */
    public int Height = new int();
    /**
    * 
    */
    public MountItemDef copy() throws Exception {
        return (MountItemDef)this.MemberwiseClone();
    }

}


