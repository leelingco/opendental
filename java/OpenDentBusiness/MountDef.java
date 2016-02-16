//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.MountDef;
import OpenDentBusiness.TableBase;

/**
* THIS TABLE IS NOT BEING USED.  These can be freely deleted, renamed, moved, etc. without affecting any patient info.  mountitemdef
*/
public class MountDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long MountDefNum = new long();
    /**
    * .
    */
    public String Description = new String();
    /**
    * The order that the mount defs will show in various lists.
    */
    public int ItemOrder = new int();
    /**
    * Set to true if this is just xrays.  If true, this prevents image from being scaled to fit inside the mount.  If false (composite photographs for example) then the images will be scaled to fit inside the mount. Later, the basic appearance or background color might be set based on this flag as well.
    */
    public boolean IsRadiograph = new boolean();
    /**
    * The width of the mount, in pixels.  For radiograph mounts, this could be very large.  It must be large enough for the radiographs to fit in the mount without scaling.  For photos, it should also be large so that the scaling won't be too noticeable.  Shrinking to view or print will always produce nicer results than enlarging to view or print.
    */
    public int Width = new int();
    /**
    * Height of the mount in pixels.
    */
    public int Height = new int();
    /**
    * 
    */
    public MountDef copy() throws Exception {
        return (MountDef)this.MemberwiseClone();
    }

}


