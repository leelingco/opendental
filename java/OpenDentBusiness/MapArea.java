//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.MapArea;
import OpenDentBusiness.MapItemType;
import OpenDentBusiness.TableBase;

/**
* MapArea object will be placed on a MapAreaPanel and shown to give a physical layout of a location.
*/
public class MapArea  extends TableBase 
{
    /**
    * Primary key.
    */
    public long MapAreaNum = new long();
    /**
    * FK to Phone.Extension.  Typically 0.  Only used by HQ and when ItemType is set to Room.
    */
    public int Extension = new int();
    /**
    * X-position in the clinical map layout.  Indicates how many feet the MapArea should be placed from the left edge.
    */
    public double XPos = new double();
    /**
    * Y-position in the clinical map layout.  Indicates how many feet the MapArea should be placed from the top edge.
    */
    public double YPos = new double();
    /**
    * MapArea width measured in feet.  Not allowed to be zero.
    */
    public double Width = new double();
    /**
    * MapArea height measured in feet.
    */
    public double Height = new double();
    /**
    * Any text that the user types in.  Only used when ItemType is set to DisplayLabel.  Limit 255 char.
    */
    public String Description = new String();
    /**
    * Enum:MapItemType 0-Room,1-DisplayLabel
    */
    public MapItemType ItemType = MapItemType.Room;
    public MapArea copy() throws Exception {
        return (MapArea)this.MemberwiseClone();
    }

}


