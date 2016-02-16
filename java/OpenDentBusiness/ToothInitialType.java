//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum ToothInitialType
{
    /**
    * 0
    */
    Missing,
    /**
    * 1 - Also hides the number.  This is now also allowed for primary teeth.
    */
    Hidden,
    /**
    * 2 - Only used with 1-32.  "sets" this tooth as a primary tooth.  The result is that the primary tooth shows in addition to the perm, and that the letter shows in addition to the number.  It also does a Shift0 -12 and some other handy movements.  Even if this is set to true, there can be a separate entry for a missing primary tooth; this would be almost equivalent to not even setting the tooth as primary, but would also allow user to select the letter.
    */
    Primary,
    /**
    * 3
    */
    ShiftM,
    /**
    * 4
    */
    ShiftO,
    /**
    * 5
    */
    ShiftB,
    /**
    * 6
    */
    Rotate,
    /**
    * 7
    */
    TipM,
    /**
    * 8
    */
    TipB,
    /**
    * 9 One segment of a drawing.
    */
    Drawing
}

