//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Def;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.TableBase;

/**
* The info in the definition table is used by other tables extensively.  Almost every table in the database links to definition.  Almost all links to this table will be to a DefNum.  Using the DefNum, you can find any of the other fields of interest, usually the ItemName.  Make sure to look at the Defs class to see how the definitions are used.  Loaded into memory ahead of time for speed.  Some types of info such as operatories started out life in this table, but then got moved to their own table when more complexity was needed.
*/
public class Def  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DefNum = new long();
    /**
    * Enum:DefCat
    */
    public DefCat Category = DefCat.AccountColors;
    /**
    * Order that each item shows on various lists. 0-indexed.
    */
    public int ItemOrder = new int();
    /**
    * Each category is a little different.  This field is usually the common name of the item.
    */
    public String ItemName = new String();
    /**
    * This field can be used to store extra info about the item.
    */
    public String ItemValue = new String();
    /**
    * Some categories include a color option.
    */
    public Color ItemColor = new Color();
    /**
    * If hidden, the item will not show on any list, but can still be referenced.
    */
    public boolean IsHidden = new boolean();
    /**
    * Used only for serialization purposes
    */
    public int getItemColorXml() throws Exception {
        return ItemColor.ToArgb();
    }

    public void setItemColorXml(int value) throws Exception {
        ItemColor = Color.FromArgb(value);
    }

    /**
    * Returns a copy of the def.
    */
    public Def copy() throws Exception {
        return (Def)MemberwiseClone();
    }

}


