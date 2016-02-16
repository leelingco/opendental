//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothInitialType;

/**
* Used to track missing teeth, primary teeth, movements, and drawings.
*/
public class ToothInitial  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ToothInitialNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * 1-32 or A-Z. Supernumeraries not supported here yet.
    */
    public String ToothNum = new String();
    /**
    * Enum:ToothInitialType
    */
    public ToothInitialType InitialType = ToothInitialType.Missing;
    /**
    * Shift in mm, or rotation / tipping in degrees.
    */
    public float Movement = new float();
    /**
    * Point data for a drawing segment.  The format would look similar to this: 45,68;48,70;49,72;0,0;55,88;etc.  It's simply a sequence of points, separated by semicolons.  Only positive numbers are used.  0,0 is the upper left of the tooth chart at original size.  Stored in pixels as originally drawn.  If we ever change the tooth chart, we will have to also keep an old version as an alternate to display old drawings.
    */
    public String DrawingSegment = new String();
    /**
    * .
    */
    public Color ColorDraw = new Color();
    /**
    * Used only for serialization purposes
    */
    public int getColorDrawXml() throws Exception {
        return ColorDraw.ToArgb();
    }

    public void setColorDrawXml(int value) throws Exception {
        ColorDraw = Color.FromArgb(value);
    }

    /**
    * 
    */
    public ToothInitial copy() throws Exception {
        return (ToothInitial)this.MemberwiseClone();
    }

}


