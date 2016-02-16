//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CovCat;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.TableBase;

/**
* Insurance coverage categories.  They need to look like in the manual for the American calculations to work properly.
*/
public class CovCat  extends TableBase 
{
    /**
    * Primary key.  Only used in Benefit and CovSpan tables.
    */
    public long CovCatNum = new long();
    /**
    * Description of this category.
    */
    public String Description = new String();
    /**
    * Default percent for this category. -1 to skip this category and not apply a percentage.
    */
    public int DefaultPercent = new int();
    /**
    * The order in which the categories are displayed.  Includes hidden categories. 0-based.
    */
    public byte CovOrder = new byte();
    /**
    * If true, this category will be hidden.
    */
    public boolean IsHidden = new boolean();
    /**
    * Enum:EbenefitCategory  The X12 benefit categories.  Each CovCat can link to one X12 category.  Default is 0 (unlinked).
    */
    public EbenefitCategory EbenefitCat = EbenefitCategory.None;
    /**
    * 
    */
    public CovCat copy() throws Exception {
        CovCat c = new CovCat();
        c.CovCatNum = CovCatNum;
        c.Description = Description;
        c.DefaultPercent = DefaultPercent;
        c.CovOrder = CovOrder;
        c.IsHidden = IsHidden;
        c.EbenefitCat = EbenefitCat;
        return c;
    }

}


//public class DtoCovCatRefresh:DtoQueryBase {
//}