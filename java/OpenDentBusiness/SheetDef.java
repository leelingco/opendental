//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.TableBase;

/**
* A definition (template) for a sheet.  Can be pulled from the database, or it can be internally defined.
*/
public class SheetDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SheetDefNum = new long();
    /**
    * The description of this sheetdef.
    */
    public String Description = new String();
    /**
    * Enum:SheetTypeEnum
    */
    public SheetTypeEnum SheetType = SheetTypeEnum.LabelPatient;
    /**
    * The default fontSize for the sheet.  The actual font must still be saved with each sheetField.
    */
    public float FontSize = new float();
    /**
    * The default fontName for the sheet.  The actual font must still be saved with each sheetField.
    */
    public String FontName = new String();
    /**
    * Width of the sheet in pixels, 100 pixels per inch.
    */
    public int Width = new int();
    /**
    * Height of the sheet in pixels, 100 pixels per inch.
    */
    public int Height = new int();
    /**
    * Set to true to print landscape.
    */
    public boolean IsLandscape = new boolean();
    /**
    * A collection of all parameters for this sheetdef.  There's usually only one parameter.  The first parameter will be a List long if it's a batch.  If a sheet has already been filled, saved to the database, and printed, then there is no longer any need for the parameters in order to fill the data.  So a retrieved sheet will have no parameters, signalling a skip in the fill phase.  There will still be parameters tucked away in the Field data in the database, but they won't become part of the sheet.
    */
    public List<SheetParameter> Parameters = new List<SheetParameter>();
    /**
    * 
    */
    public List<SheetFieldDef> SheetFieldDefs = new List<SheetFieldDef>();
    /**
    * 
    */
    public Font getFont() throws Exception {
        return new Font(FontName, FontSize);
    }

    public SheetDef() throws Exception {
    }

    //required for use as a generic.
    public SheetDef(SheetTypeEnum sheetType) throws Exception {
        SheetType = sheetType;
        Parameters = SheetParameter.getForType(sheetType);
        SheetFieldDefs = new List<SheetFieldDef>();
    }

    public SheetDef copy() throws Exception {
        SheetDef sheetdef = (SheetDef)this.MemberwiseClone();
        return sheetdef;
    }

    //do I need to copy the lists?
    /**
    * Used only for serialization purposes
    */
    public SheetFieldDef[] getSheetFieldDefsXml() throws Exception {
        if (SheetFieldDefs == null)
        {
            return new SheetFieldDef[0];
        }
         
        return SheetFieldDefs.ToArray();
    }

    public void setSheetFieldDefsXml(SheetFieldDef[] value) throws Exception {
        SheetFieldDefs = new List<SheetFieldDef>();
        for (int i = 0;i < value.Length;i++)
        {
            SheetFieldDefs.Add(value[i]);
        }
    }

}


