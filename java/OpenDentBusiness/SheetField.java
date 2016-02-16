//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.GrowthBehaviorEnum;
import OpenDentBusiness.SheetField;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.TableBase;

/**
* One field on a sheet.
*/
public class SheetField  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SheetFieldNum = new long();
    /**
    * FK to sheet.SheetNum.
    */
    public long SheetNum = new long();
    /**
    * Enum:SheetFieldType  OutputText, InputField, StaticText,Parameter(only used for SheetField, not SheetFieldDef),Image,Drawing,Line,Rectangle,CheckBox,SigBox,PatImage.
    */
    public SheetFieldType FieldType = SheetFieldType.OutputText;
    /**
    * Mostly for OutputText and InputField types.  Each sheet typically has a main datatable type.  For OutputText types, FieldName is usually the string representation of the database column for the main table.  For other tables, it can be of the form table.Column.  There may also be extra fields available that are not strictly pulled from the database.  Extra fields will start with lowercase to indicate that they are not pure database fields.  The list of available fields for each type in SheetFieldsAvailable.  Users can pick from that list.  Likewise, InputField types are internally tied to actions to persist the data.  So they are also hard coded and are available in SheetFieldsAvailable.  For static images, this is the full file name including extension, but without path.  Static images paths are reconstructed by looking in the AtoZ folder, SheetImages folder.  For a PatImage, this now contains the long FK to the document.  A join must be done to that table to find the filename.   When a SheetField has a fieldType of Parameter, then the FieldName stores the name of the parameter.
    */
    public String FieldName = new String();
    /**
    * For OutputText, this value is set before printing.  This is the data obtained from the database and ready to print.  For StaticText, this is copied from the sheetFieldDef, but in-line fields like [this] will have been filled.  For an archived sheet retrieved from the database (all SheetField rows), this value will have been saved and will not be filled again automatically.  For a parameter fieldtype, this will store the value of the parameter. For a Drawing fieldtype, this will be the point data for the lines.  The format would look similar to this: 45,68;48,70;49,72;0,0;55,88;etc.  It's simply a sequence of points, separated by semicolons.  For CheckBox, it will either be an X or empty.  For SigBox, the first char will be 0 or 1 to indicate SigIsTopaz, and all subsequent chars will be the Signature itself.   For Pat Image, this contains image size and image position info.  Like this: "X=0,Y=20,W=100,H=60".  This is initially generated automatically to fit the object.  It can later be changed by the user to "zoom and pan" within the confines of the object.
    */
    public String FieldValue = new String();
    /**
    * The fontSize for this field regardless of the default for the sheet.  The actual font must be saved with each sheetField.
    */
    public float FontSize = new float();
    /**
    * The fontName for this field regardless of the default for the sheet.  The actual font must be saved with each sheetField.
    */
    public String FontName = new String();
    /**
    * .
    */
    public boolean FontIsBold = new boolean();
    /**
    * In pixels.
    */
    public int XPos = new int();
    /**
    * In pixels.
    */
    public int YPos = new int();
    /**
    * The field will be constrained horizontally to this size.  Not allowed to be zero.
    */
    public int Width = new int();
    /**
    * The field will be constrained vertically to this size.  Not allowed to be 0.  It's not allowed to be zero so that it will be visible on the designer.
    */
    public int Height = new int();
    /**
    * Enum:GrowthBehaviorEnum
    */
    public GrowthBehaviorEnum GrowthBehavior = GrowthBehaviorEnum.None;
    /**
    * This is only used for checkboxes that you want to behave like radiobuttons.  Set the FieldName the same for each Checkbox in the group.  The FieldValue will likely be X for one of them and empty string for the others.  Each of them will have a different RadioButtonValue.  Whichever box has X, the RadioButtonValue for that box will be used when importing.  This field is not used for "misc" radiobutton groups.
    */
    public String RadioButtonValue = new String();
    /**
    * Name which identifies the group within which the radio button belongs. FieldName must be set to "misc" in order for the group to take effect.
    */
    public String RadioButtonGroup = new String();
    /**
    * Set to true if this field is required to have a value before the sheet is closed.
    */
    public boolean IsRequired = new boolean();
    /**
    * Tab stop order for all fields. Only checkboxes and input fields can have values other than 0.
    */
    public int TabOrder = new int();
    /**
    * Allows reporting on misc fields.
    */
    public String ReportableName = new String();
    public SheetField copy() throws Exception {
        return (SheetField)this.MemberwiseClone();
    }

    public String toString() {
        try
        {
            return FieldName + " " + FieldValue;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    /**
    * Should only be called after FieldValue has been set, due to GrowthBehavior.
    */
    public Rectangle getBounds() throws Exception {
        return new Rectangle(XPos, YPos, Width, Height);
    }

    /**
    * Should only be called after FieldValue has been set, due to GrowthBehavior.
    */
    public RectangleF getBoundsF() throws Exception {
        return new RectangleF(XPos, YPos, Width, Height);
    }

}


