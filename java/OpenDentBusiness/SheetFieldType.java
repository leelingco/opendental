//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;


public enum SheetFieldType
{
    /**
    * 0-Pulled from the database to be printed on the sheet.  Or also possibly just generated at runtime even though not pulled from the database.   User still allowed to change the output text as they are filling out the sheet so that it can different from what was initially generated.
    */
    OutputText,
    /**
    * 1-A blank box that the user is supposed to fill in.
    */
    InputField,
    /**
    * 2-This is text that is defined as part of the sheet and will never change from sheet to sheet.
    */
    StaticText,
    /**
    * 3-Stores a parameter other than the PatNum.  Not meant to be seen on the sheet.  Only used for SheetField, not SheetFieldDef.
    */
    Parameter,
    /**
    * 4-Any image of any size, typically a background image for a form.
    */
    Image,
    /**
    * 5-One sequence of dots that makes a line.  Continuous without any breaks.  Each time the pen is picked up, it creates a new field row in the database.
    */
    Drawing,
    /**
    * 6-A simple line drawn from x,y to x+width,y+height.  So for these types, we must allow width and height to be negative or zero.
    */
    Line,
    /**
    * 7-A simple rectangle outline.
    */
    Rectangle,
    /**
    * 8-A clickable area on the screen.  It's a form of input, so treated similarly to an InputField.  The X will go from corner to corner of the rectangle specified.  It can also behave like a radio button
    */
    CheckBox,
    /**
    * 9-A signature box, either Topaz pad or directly on the screen with stylus/mouse.  The signature is encrypted based an a hash of all other field values in the entire sheet, excluding other SigBoxes.  The order is critical.
    */
    SigBox,
    /**
    * 10-An image specific to one patient.
    */
    PatImage,
    /**
    * 11-Special: Currently only used for Toothgrid
    */
    Special
}

