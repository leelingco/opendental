//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.GrowthBehaviorEnum;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.TableBase;

/**
* One field on a sheetDef.
*/
public class SheetFieldDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SheetFieldDefNum = new long();
    /**
    * FK to sheetdef.SheetDefNum.
    */
    public long SheetDefNum = new long();
    /**
    * Enum:SheetFieldType  OutputText, InputField, StaticText,Parameter(only used for SheetField, not SheetFieldDef),Image,Drawing,Line,Rectangle,CheckBox,SigBox,PatImage.
    */
    public SheetFieldType FieldType = SheetFieldType.OutputText;
    /**
    * Mostly for OutputText, InputField, and CheckBox types.  Each sheet typically has a main datatable type.  For OutputText types, FieldName is usually the string representation of the database column for the main table.  For other tables, it can be of the form table.Column.  There may also be extra fields available that are not strictly pulled from the database.  Extra fields will start with lowercase to indicate that they are not pure database fields.  The list of available fields for each type in SheetFieldsAvailable.  Users can pick from that list.  Likewise, InputField types are internally tied to actions to persist the data.  So they are also hard coded and are available in SheetFieldsAvailable.  For static images, this is the full file name including extension, but without path.  Static images paths are reconstructed by looking in the AtoZ folder, SheetImages folder.  For Pat Images, this is an long FK/DefNum to the default folder for the image.  The filename of a PatImage will later be stored in FieldValue.
    */
    public String FieldName = new String();
    /**
    * For StaticText, this text can include bracketed fields, like [nameLF].  For OutputText and InputField, this will be blank.  For CheckBoxes, either X or blank.  Even if the checkbox is set to behave like a radio button.
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
    * The Bitmap should be converted to Base64 using POut.Bitmap() before placing in this field.  Not stored in the database.  Only used when uploading SheetDefs to the web server.
    */
    public String ImageData = new String();
    /**
    * Tab stop order for all fields. One-based.  Only checkboxes and input fields can have values other than 0.
    */
    public int TabOrder = new int();
    /**
    * Allows reporting on misc fields.
    */
    public String ReportableName = new String();
    public SheetFieldDef() throws Exception {
        //required for use as a generic.
        RadioButtonGroup = "";
        ImageData = "";
    }

    public SheetFieldDef(SheetFieldType fieldType, String fieldName, String fieldValue, float fontSize, String fontName, boolean fontIsBold, int xPos, int yPos, int width, int height, GrowthBehaviorEnum growthBehavior, String radioButtonValue) throws Exception {
        FieldType = fieldType;
        FieldName = fieldName;
        FieldValue = fieldValue;
        FontSize = fontSize;
        FontName = fontName;
        FontIsBold = fontIsBold;
        XPos = xPos;
        YPos = yPos;
        Width = width;
        Height = height;
        GrowthBehavior = growthBehavior;
        RadioButtonValue = radioButtonValue;
    }

    public SheetFieldDef copy() throws Exception {
        return (SheetFieldDef)this.MemberwiseClone();
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
    * 
    */
    public Font getFont() throws Exception {
        FontStyle style = FontStyle.Regular;
        if (FontIsBold)
        {
            style = FontStyle.Bold;
        }
         
        return new Font(FontName, FontSize, style);
    }

    public static SheetFieldDef newOutput(String fieldName, float fontSize, String fontName, boolean fontIsBold, int xPos, int yPos, int width, int height) throws Exception {
        return new SheetFieldDef(SheetFieldType.OutputText,fieldName,"",fontSize,fontName,fontIsBold,xPos,yPos,width,height,GrowthBehaviorEnum.None,"");
    }

    public static SheetFieldDef newOutput(String fieldName, float fontSize, String fontName, boolean fontIsBold, int xPos, int yPos, int width, int height, GrowthBehaviorEnum growthBehavior) throws Exception {
        return new SheetFieldDef(SheetFieldType.OutputText,fieldName,"",fontSize,fontName,fontIsBold,xPos,yPos,width,height,growthBehavior,"");
    }

    public static SheetFieldDef newStaticText(String fieldValue, float fontSize, String fontName, boolean fontIsBold, int xPos, int yPos, int width, int height) throws Exception {
        return new SheetFieldDef(SheetFieldType.StaticText,"",fieldValue,fontSize,fontName,fontIsBold,xPos,yPos,width,height,GrowthBehaviorEnum.None,"");
    }

    public static SheetFieldDef newStaticText(String fieldValue, float fontSize, String fontName, boolean fontIsBold, int xPos, int yPos, int width, int height, GrowthBehaviorEnum growthBehavior) throws Exception {
        return new SheetFieldDef(SheetFieldType.StaticText,"",fieldValue,fontSize,fontName,fontIsBold,xPos,yPos,width,height,growthBehavior,"");
    }

    public static SheetFieldDef newInput(String fieldName, float fontSize, String fontName, boolean fontIsBold, int xPos, int yPos, int width, int height) throws Exception {
        return new SheetFieldDef(SheetFieldType.InputField,fieldName,"",fontSize,fontName,fontIsBold,xPos,yPos,width,height,GrowthBehaviorEnum.None,"");
    }

    public static SheetFieldDef newImage(String fileName, int xPos, int yPos, int width, int height) throws Exception {
        return new SheetFieldDef(SheetFieldType.Image, fileName, "", 0, "", false, xPos, yPos, width, height, GrowthBehaviorEnum.None, "");
    }

    public static SheetFieldDef newLine(int xPos, int yPos, int width, int height) throws Exception {
        return new SheetFieldDef(SheetFieldType.Line, "", "", 0, "", false, xPos, yPos, width, height, GrowthBehaviorEnum.None, "");
    }

    public static SheetFieldDef newRect(int xPos, int yPos, int width, int height) throws Exception {
        return new SheetFieldDef(SheetFieldType.Rectangle, "", "", 0, "", false, xPos, yPos, width, height, GrowthBehaviorEnum.None, "");
    }

    public static SheetFieldDef newCheckBox(String fieldName, int xPos, int yPos, int width, int height) throws Exception {
        return new SheetFieldDef(SheetFieldType.CheckBox, fieldName, "", 0, "", false, xPos, yPos, width, height, GrowthBehaviorEnum.None, "");
    }

    public static SheetFieldDef newRadioButton(String fieldName, String radioButtonValue, int xPos, int yPos, int width, int height) throws Exception {
        return new SheetFieldDef(SheetFieldType.CheckBox, fieldName, "", 0, "", false, xPos, yPos, width, height, GrowthBehaviorEnum.None, radioButtonValue);
    }

    public static SheetFieldDef newSigBox(int xPos, int yPos, int width, int height) throws Exception {
        return new SheetFieldDef(SheetFieldType.SigBox, "", "", 0, "", false, xPos, yPos, width, height, GrowthBehaviorEnum.None, "");
    }

    public static SheetFieldDef newSpecial(int xPos, int yPos, int width, int height) throws Exception {
        return new SheetFieldDef(SheetFieldType.Special, "", "", 0, "", false, xPos, yPos, width, height, GrowthBehaviorEnum.None, "");
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


