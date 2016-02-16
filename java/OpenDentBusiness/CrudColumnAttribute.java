//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;

public class CrudColumnAttribute  extends Attribute 
{
    public CrudColumnAttribute() throws Exception {
        this.isPriKey = false;
        this.specialType = CrudSpecialColType.None;
        this.isNotDbColumn = false;
        this.isPriKeyMobile1 = false;
        this.isPriKeyMobile2 = false;
    }

    private boolean isPriKey = new boolean();
    public boolean getIsPriKey() throws Exception {
        return isPriKey;
    }

    public void setIsPriKey(boolean value) throws Exception {
        isPriKey = value;
    }

    private CrudSpecialColType specialType = CrudSpecialColType.None;
    public CrudSpecialColType getSpecialType() throws Exception {
        return specialType;
    }

    public void setSpecialType(CrudSpecialColType value) throws Exception {
        specialType = value;
    }

    private boolean isNotDbColumn = new boolean();
    public boolean getIsNotDbColumn() throws Exception {
        return isNotDbColumn;
    }

    public void setIsNotDbColumn(boolean value) throws Exception {
        isNotDbColumn = value;
    }

    private boolean isPriKeyMobile1 = new boolean();
    /**
    * Always present in a mobile table.  Always CustomerNum, FK to PatNum.
    */
    public boolean getIsPriKeyMobile1() throws Exception {
        return isPriKeyMobile1;
    }

    public void setIsPriKeyMobile1(boolean value) throws Exception {
        isPriKeyMobile1 = value;
    }

    private boolean isPriKeyMobile2 = new boolean();
    /**
    * Always present in a mobile table.  Always the ordinary priKey of the table, used together with CustomerNum.
    */
    public boolean getIsPriKeyMobile2() throws Exception {
        return isPriKeyMobile2;
    }

    public void setIsPriKeyMobile2(boolean value) throws Exception {
        isPriKeyMobile2 = value;
    }

}


