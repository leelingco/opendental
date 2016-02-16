//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:44 PM
//

package WebForms;

import WebForms.webforms_preference;
import WebForms.webforms_sheetdef;
import WebForms.webforms_sheetfielddef;


/**
* No Metadata Documentation available.
*/
public class webforms_sheetdef  extends EntityObject 
{

    /**
    * Create a new webforms_sheetdef object.
    * 
    *  @param dentalOfficeID Initial value of the DentalOfficeID property.
    *  @param description Initial value of the Description property.
    *  @param fontName Initial value of the FontName property.
    *  @param fontSize Initial value of the FontSize property.
    *  @param height Initial value of the Height property.
    *  @param isLandscape Initial value of the IsLandscape property.
    *  @param sheetType Initial value of the SheetType property.
    *  @param webSheetDefID Initial value of the WebSheetDefID property.
    *  @param width Initial value of the Width property.
    */
    public static webforms_sheetdef createwebforms_sheetdef(System.Int64 dentalOfficeID, System.String description, System.String fontName, System.Single fontSize, System.Int32 height, System.SByte isLandscape, System.Int32 sheetType, System.Int64 webSheetDefID, System.Int32 width) throws Exception {
        webforms_sheetdef webforms_sheetdef = new webforms_sheetdef();
        webforms_sheetdef.setDentalOfficeID(dentalOfficeID);
        webforms_sheetdef.setDescription(description);
        webforms_sheetdef.setFontName(fontName);
        webforms_sheetdef.setFontSize(fontSize);
        webforms_sheetdef.setHeight(height);
        webforms_sheetdef.setIsLandscape(isLandscape);
        webforms_sheetdef.setSheetType(sheetType);
        webforms_sheetdef.setWebSheetDefID(webSheetDefID);
        webforms_sheetdef.setWidth(width);
        return webforms_sheetdef;
    }

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
    public System.String getFontName() throws Exception {
        return _FontName;
    }

    public void setFontName(System.String value) throws Exception {

        ReportPropertyChanging("FontName");
        _FontName = StructuralObject.SetValidValue(value, false);
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
    public System.Int64 getWebSheetDefID() throws Exception {
        return _WebSheetDefID;
    }

    public void setWebSheetDefID(System.Int64 value) throws Exception {
        if (_WebSheetDefID != value)
        {

            ReportPropertyChanging("WebSheetDefID");
            _WebSheetDefID = StructuralObject.SetValidValue(value);
            ReportPropertyChanged("WebSheetDefID");
        
        }
         
    }

    private System.Int64 _WebSheetDefID = new System.Int64();
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
    public webforms_preference getwebforms_preference() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_preference>GetRelatedReference("odwebserviceModel.FK_webforms_sheetdef_DentalOfficeID", "webforms_preference").Value;
    }

    public void setwebforms_preference(webforms_preference value) throws Exception {
        ((IEntityWithRelationships)this).RelationshipManager.<webforms_preference>GetRelatedReference("odwebserviceModel.FK_webforms_sheetdef_DentalOfficeID", "webforms_preference").Value = value;
    }

    /**
    * No Metadata Documentation available.
    */
    public EntityReference<webforms_preference> getwebforms_preferenceReference() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_preference>GetRelatedReference("odwebserviceModel.FK_webforms_sheetdef_DentalOfficeID", "webforms_preference");
    }

    public void setwebforms_preferenceReference(EntityReference<webforms_preference> value) throws Exception {
        if ((value != null))
        {
            ((IEntityWithRelationships)this).RelationshipManager.<webforms_preference>InitializeRelatedReference("odwebserviceModel.FK_webforms_sheetdef_DentalOfficeID", "webforms_preference", value);
        }
         
    }

    /**
    * No Metadata Documentation available.
    */
    public EntityCollection<webforms_sheetfielddef> getwebforms_sheetfielddef() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheetfielddef>GetRelatedCollection("odwebserviceModel.FK_webforms_sheetfielddef_WebSheetDefID", "webforms_sheetfielddef");
    }

    public void setwebforms_sheetfielddef(EntityCollection<webforms_sheetfielddef> value) throws Exception {
        if ((value != null))
        {
            ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheetfielddef>InitializeRelatedCollection("odwebserviceModel.FK_webforms_sheetfielddef_WebSheetDefID", "webforms_sheetfielddef", value);
        }
         
    }

}


