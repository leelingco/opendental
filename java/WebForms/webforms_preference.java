//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:44 PM
//

package WebForms;

import WebForms.webforms_preference;
import WebForms.webforms_sheet;
import WebForms.webforms_sheetdef;


/**
* No Metadata Documentation available.
*/
public class webforms_preference  extends EntityObject 
{

    /**
    * Create a new webforms_preference object.
    * 
    *  @param colorBorder Initial value of the ColorBorder property.
    *  @param dentalOfficeID Initial value of the DentalOfficeID property.
    *  @param cultureName Initial value of the CultureName property.
    */
    public static webforms_preference createwebforms_preference(System.Int32 colorBorder, System.Int64 dentalOfficeID, System.String cultureName) throws Exception {
        webforms_preference webforms_preference = new webforms_preference();
        webforms_preference.setColorBorder(colorBorder);
        webforms_preference.setDentalOfficeID(dentalOfficeID);
        webforms_preference.setCultureName(cultureName);
        return webforms_preference;
    }

    /**
    * No Metadata Documentation available.
    */
    public System.Int32 getColorBorder() throws Exception {
        return _ColorBorder;
    }

    public void setColorBorder(System.Int32 value) throws Exception {

        ReportPropertyChanging("ColorBorder");
        _ColorBorder = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("ColorBorder");
    
    }

    private System.Int32 _ColorBorder = new System.Int32();
    /**
    * No Metadata Documentation available.
    */
    public System.Int64 getDentalOfficeID() throws Exception {
        return _DentalOfficeID;
    }

    public void setDentalOfficeID(System.Int64 value) throws Exception {
        if (_DentalOfficeID != value)
        {

            ReportPropertyChanging("DentalOfficeID");
            _DentalOfficeID = StructuralObject.SetValidValue(value);
            ReportPropertyChanged("DentalOfficeID");
        
        }
         
    }

    private System.Int64 _DentalOfficeID = new System.Int64();
    /**
    * No Metadata Documentation available.
    */
    public System.String getCultureName() throws Exception {
        return _CultureName;
    }

    public void setCultureName(System.String value) throws Exception {

        ReportPropertyChanging("CultureName");
        _CultureName = StructuralObject.SetValidValue(value, false);
        ReportPropertyChanged("CultureName");
    
    }

    private System.String _CultureName = new System.String();
    /**
    * No Metadata Documentation available.
    */
    public EntityCollection<webforms_sheetdef> getwebforms_sheetdef() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheetdef>GetRelatedCollection("odwebserviceModel.FK_webforms_sheetdef_DentalOfficeID", "webforms_sheetdef");
    }

    public void setwebforms_sheetdef(EntityCollection<webforms_sheetdef> value) throws Exception {
        if ((value != null))
        {
            ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheetdef>InitializeRelatedCollection("odwebserviceModel.FK_webforms_sheetdef_DentalOfficeID", "webforms_sheetdef", value);
        }
         
    }

    /**
    * No Metadata Documentation available.
    */
    public EntityCollection<webforms_sheet> getwebforms_sheet() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheet>GetRelatedCollection("odwebserviceModel.FK_webforms_sheet_DentalOfficeID", "webforms_sheet");
    }

    public void setwebforms_sheet(EntityCollection<webforms_sheet> value) throws Exception {
        if ((value != null))
        {
            ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheet>InitializeRelatedCollection("odwebserviceModel.FK_webforms_sheet_DentalOfficeID", "webforms_sheet", value);
        }
         
    }

}


