//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ClaimFormItem;
import OpenDentBusiness.TableBase;

/**
* One item is needed for each field on a claimform.
*/
public class ClaimFormItem  extends TableBase 
{
    /**
    * Primary key.
    */
    //[XmlIgnore]
    public long ClaimFormItemNum = new long();
    /**
    * FK to claimform.ClaimFormNum
    */
    //[XmlIgnore]
    public long ClaimFormNum = new long();
    /**
    * If this item is an image.  Usually only one per claimform.  eg ADA2002.emf.  Otherwise it MUST be left blank, or it will trigger an error that the image cannot be found.
    */
    public String ImageFileName = new String();
    /**
    * Must be one of the hardcoded available fieldnames for claims.
    */
    public String FieldName = new String();
    /**
    * For dates, the format string. ie MM/dd/yyyy or M d y among many other possibilities.
    */
    public String FormatString = new String();
    /**
    * The x position of the item on the claim form.  In pixels. 100 pixels per inch.
    */
    public float XPos = new float();
    /**
    * The y position of the item.
    */
    public float YPos = new float();
    /**
    * Limits the printable area of the item. Set to zero to not limit.
    */
    public float Width = new float();
    /**
    * Limits the printable area of the item. Set to zero to not limit.
    */
    public float Height = new float();
    /**
    * Returns a copy of the claimformitem.
    */
    public ClaimFormItem copy() throws Exception {
        return (ClaimFormItem)this.MemberwiseClone();
    }

}


