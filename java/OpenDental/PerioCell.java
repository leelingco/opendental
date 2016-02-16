//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:28 PM
//

package OpenDental;

import OpenDentBusiness.BleedingFlags;

/**
* 
*/
public class PerioCell   
{
    public PerioCell() {
    }

    /**
    * The value to display for this exam. Overwrites any oldText from previous exams.
    */
    public String Text = new String();
    /**
    * None, blood, pus, or both
    */
    public BleedingFlags Bleeding = BleedingFlags.None;
    /**
    * Values from previous exams. Slightly greyed out.
    */
    public String OldText = new String();
}


//<summary></summary>
//public Rectangle Bounds;
//<summary></summary>
//public Color BackColor;
//<summary></summary>
//public Color ForeColor;