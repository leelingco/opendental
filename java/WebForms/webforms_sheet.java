//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:44 PM
//

package WebForms;

import WebForms.webforms_preference;
import WebForms.webforms_sheet;
import WebForms.webforms_sheetfield;


/**
* No Metadata Documentation available.
*/
public class webforms_sheet  extends EntityObject 
{

    /**
    * Create a new webforms_sheet object.
    * 
    *  @param dateTimeSheet Initial value of the DateTimeSheet property.
    *  @param dentalOfficeID Initial value of the DentalOfficeID property.
    *  @param fontSize Initial value of the FontSize property.
    *  @param height Initial value of the Height property.
    *  @param isLandscape Initial value of the IsLandscape property.
    *  @param sheetID Initial value of the SheetID property.
    *  @param sheetType Initial value of the SheetType property.
    *  @param width Initial value of the Width property.
    *  @param description Initial value of the Description property.
    */
    public static webforms_sheet createwebforms_sheet(System.DateTime dateTimeSheet, System.Int64 dentalOfficeID, System.Single fontSize, System.Int32 height, System.SByte isLandscape, System.Int64 sheetID, System.Int32 sheetType, System.Int32 width, System.String description) throws Exception {
        webforms_sheet webforms_sheet = new webforms_sheet();
        webforms_sheet.setDateTimeSheet(dateTimeSheet);
        webforms_sheet.setDentalOfficeID(dentalOfficeID);
        webforms_sheet.setFontSize(fontSize);
        webforms_sheet.setHeight(height);
        webforms_sheet.setIsLandscape(isLandscape);
        webforms_sheet.setSheetID(sheetID);
        webforms_sheet.setSheetType(sheetType);
        webforms_sheet.setWidth(width);
        webforms_sheet.setDescription(description);
        return webforms_sheet;
    }

    /**
    * No Metadata Documentation available.
    */
    public System.DateTime getDateTimeSheet() throws Exception {
        return _DateTimeSheet;
    }

    public void setDateTimeSheet(System.DateTime value) throws Exception {

        ReportPropertyChanging("DateTimeSheet");
        _DateTimeSheet = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("DateTimeSheet");
    
    }

    private System.DateTime _DateTimeSheet = new System.DateTime();
    /**
    * No Metadata Documentation available.
    */
    public System.Int64 getDentalOfficeID() throws Exception {
        return _DentalOfficeID;
    }

    public void setDentalOfficeID(System.Int64 value) throws Exception {

        ReportPropertyChanging("DentalOfficeID");
        _DentalOfficeID = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("DentalOfficeID");
    
    }

    private System.Int64 _DentalOfficeID = new System.Int64();
    /**
    * No Metadata Documentation available.
    */
    public System.String getFontName() throws Exception {
        return _FontName;
    }

    public void setFontName(System.String value) throws Exception {

        ReportPropertyChanging("FontName");
        _FontName = StructuralObject.SetValidValue(value, true);
        ReportPropertyChanged("FontName");
    
    }

    private System.String _FontName = new System.String();
    /**
    * No Metadata Documentation available.
    */
    public System.Single getFontSize() throws Exception {
        return _FontSize;
    }

    public void setFontSize(System.Single value) throws Exception {

        ReportPropertyChanging("FontSize");
        _FontSize = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("FontSize");
    
    }

    private System.Single _FontSize = new System.Single();
    /**
    * No Metadata Documentation available.
    */
    public System.Int32 getHeight() throws Exception {
        return _Height;
    }

    public void setHeight(System.Int32 value) throws Exception {

        ReportPropertyChanging("Height");
        _Height = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("Height");
    
    }

    private System.Int32 _Height = new System.Int32();
    /**
    * No Metadata Documentation available.
    */
    public System.SByte getIsLandscape() throws Exception {
        return _IsLandscape;
    }

    public void setIsLandscape(System.SByte value) throws Exception {

        ReportPropertyChanging("IsLandscape");
        _IsLandscape = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("IsLandscape");
    
    }

    private System.SByte _IsLandscape = new System.SByte();
    /**
    * No Metadata Documentation available.
    */
    public System.Int64 getSheetID() throws Exception {
        return _SheetID;
    }

    public void setSheetID(System.Int64 value) throws Exception {
        if (_SheetID != value)
        {

            ReportPropertyChanging("SheetID");
            _SheetID = StructuralObject.SetValidValue(value);
            ReportPropertyChanged("SheetID");
        
        }
         
    }

    private System.Int64 _SheetID = new System.Int64();
    /**
    * No Metadata Documentation available.
    */
    public System.Int32 getSheetType() throws Exception {
        return _SheetType;
    }

    public void setSheetType(System.Int32 value) throws Exception {

        ReportPropertyChanging("SheetType");
        _SheetType = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("SheetType");
    
    }

    private System.Int32 _SheetType = new System.Int32();
    /**
    * No Metadata Documentation available.
    */
    public System.Int32 getWidth() throws Exception {
        return _Width;
    }

    public void setWidth(System.Int32 value) throws Exception {

        ReportPropertyChanging("Width");
        _Width = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("Width");
    
    }

    private System.Int32 _Width = new System.Int32();
    /**
    * No Metadata Documentation available.
    */
    public System.String getDescription() throws Exception {
        return _Description;
    }

    public void setDescription(System.String value) throws Exception {

        ReportPropertyChanging("Description");
        _Description = StructuralObject.SetValidValue(value, false);
        ReportPropertyChanged("Description");
    
    }

    private System.String _Description = new System.String();
    /**
    * No Metadata Documentation available.
    */
    public webforms_preference getwebforms_preference() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_preference>GetRelatedReference("odwebserviceModel.FK_webforms_sheet_DentalOfficeID", "webforms_preference").Value;
    }

    public void setwebforms_preference(webforms_preference value) throws Exception {
        ((IEntityWithRelationships)this).RelationshipManager.<webforms_preference>GetRelatedReference("odwebserviceModel.FK_webforms_sheet_DentalOfficeID", "webforms_preference").Value = value;
    }

    /**
    * No Metadata Documentation available.
    */
    public EntityReference<webforms_preference> getwebforms_preferenceReference() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_preference>GetRelatedReference("odwebserviceModel.FK_webforms_sheet_DentalOfficeID", "webforms_preference");
    }

    public void setwebforms_preferenceReference(EntityReference<webforms_preference> value) throws Exception {
        if ((value != null))
        {
            ((IEntityWithRelationships)this).RelationshipManager.<webforms_preference>InitializeRelatedReference("odwebserviceModel.FK_webforms_sheet_DentalOfficeID", "webforms_preference", value);
        }
         
    }

    /**
    * No Metadata Documentation available.
    */
    public EntityCollection<webforms_sheetfield> getwebforms_sheetfield() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheetfield>GetRelatedCollection("odwebserviceModel.FK_webforms_sheetfield_SheetID", "webforms_sheetfield");
    }

    public void setwebforms_sheetfield(EntityCollection<webforms_sheetfield> value) throws Exception {
        if ((value != null))
        {
            ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheetfield>InitializeRelatedCollection("odwebserviceModel.FK_webforms_sheetfield_SheetID", "webforms_sheetfield", value);
        }
         
    }

}


