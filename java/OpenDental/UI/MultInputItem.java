//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental.UI;

import OpenDental.ReportingOld2.FieldValueType;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.EnumType;
import OpenDentBusiness.ReportFKType;

/**
* A single input item in the ContrMultInput control.
*/
public class MultInputItem   
{
    public MultInputItem() {
    }

    /**
    * 
    */
    public MultInputItem(String promptingText, FieldValueType valueType, ArrayList currentValues, EnumType enumerationType, DefCat defCategory, ReportFKType fKType) throws Exception {
        PromptingText = promptingText;
        ValueType = valueType;
        CurrentValues = currentValues;
        EnumerationType = enumerationType;
        DefCategory = defCategory;
        FKType = fKType;
    }

    /**
    * The text that prompts the user what information to enter.
    */
    public String PromptingText = new String();
    /**
    * The type of input that this item accepts.  Each input type displays a different input control for this item.
    */
    public FieldValueType ValueType = FieldValueType.Date;
    /**
    * A collection of the actual values of this item, not just the displayed text.  Any supported type is allowed including string, int, double, bool, datetime, etc. The length of the ArrayList can be set to 0 ahead of time if there are no default values to fill in the input field with.  The result is that the field will initially be blank.  After the user input, if the field is still blank, then the count will still be 0.  If the count is 0, then this parameter will not be included as a filter in the query.
    */
    public ArrayList CurrentValues = new ArrayList();
    /**
    * If the ValueKind is EnumField, then this specifies which type of enum.
    */
    public EnumType EnumerationType = EnumType.YN;
    /**
    * If ValueKind is DefParameter, then this specifies which DefCat.
    */
    public DefCat DefCategory = DefCat.AccountColors;
    /**
    * If ValueKind is Foreign key, then this specifies which table.
    */
    public ReportFKType FKType = ReportFKType.None;
}


