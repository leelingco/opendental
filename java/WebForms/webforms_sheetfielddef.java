//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:44 PM
//

package WebForms;

import WebForms.webforms_sheetdef;
import WebForms.webforms_sheetfielddef;


/**
* No Metadata Documentation available.
*/
public class webforms_sheetfielddef  extends EntityObject 
{

    /**
    * Create a new webforms_sheetfielddef object.
    * 
    *  @param fieldName Initial value of the FieldName property.
    *  @param fieldType Initial value of the FieldType property.
    *  @param fieldValue Initial value of the FieldValue property.
    *  @param fontIsBold Initial value of the FontIsBold property.
    *  @param fontName Initial value of the FontName property.
    *  @param fontSize Initial value of the FontSize property.
    *  @param growthBehavior Initial value of the GrowthBehavior property.
    *  @param height Initial value of the Height property.
    *  @param imageData Initial value of the ImageData property.
    *  @param isRequired Initial value of the IsRequired property.
    *  @param radioButtonGroup Initial value of the RadioButtonGroup property.
    *  @param radioButtonValue Initial value of the RadioButtonValue property.
    *  @param webSheetDefID Initial value of the WebSheetDefID property.
    *  @param webSheetFieldDefID Initial value of the WebSheetFieldDefID property.
    *  @param width Initial value of the Width property.
    *  @param xPos Initial value of the XPos property.
    *  @param yPos Initial value of the YPos property.
    *  @param tabOrder Initial value of the TabOrder property.
    */
    public static webforms_sheetfielddef createwebforms_sheetfielddef(System.String fieldName, System.Int32 fieldType, System.String fieldValue, System.SByte fontIsBold, System.String fontName, System.Single fontSize, System.Int32 growthBehavior, System.Int32 height, System.String imageData, System.SByte isRequired, System.String radioButtonGroup, System.String radioButtonValue, System.Int64 webSheetDefID, System.Int64 webSheetFieldDefID, System.Int32 width, System.Int32 xPos, System.Int32 yPos, System.Int32 tabOrder) throws Exception {
        webforms_sheetfielddef webforms_sheetfielddef = new webforms_sheetfielddef();
        webforms_sheetfielddef.setFieldName(fieldName);
        webforms_sheetfielddef.setFieldType(fieldType);
        webforms_sheetfielddef.setFieldValue(fieldValue);
        webforms_sheetfielddef.setFontIsBold(fontIsBold);
        webforms_sheetfielddef.setFontName(fontName);
        webforms_sheetfielddef.setFontSize(fontSize);
        webforms_sheetfielddef.setGrowthBehavior(growthBehavior);
        webforms_sheetfielddef.setHeight(height);
        webforms_sheetfielddef.setImageData(imageData);
        webforms_sheetfielddef.setIsRequired(isRequired);
        webforms_sheetfielddef.setRadioButtonGroup(radioButtonGroup);
        webforms_sheetfielddef.setRadioButtonValue(radioButtonValue);
        webforms_sheetfielddef.setWebSheetDefID(webSheetDefID);
        webforms_sheetfielddef.setWebSheetFieldDefID(webSheetFieldDefID);
        webforms_sheetfielddef.setWidth(width);
        webforms_sheetfielddef.setXPos(xPos);
        webforms_sheetfielddef.setYPos(yPos);
        webforms_sheetfielddef.setTabOrder(tabOrder);
        return webforms_sheetfielddef;
    }

    /**
    * No Metadata Documentation available.
    */
    public System.String getFieldName() throws Exception {
        return _FieldName;
    }

    public void setFieldName(System.String value) throws Exception {

        ReportPropertyChanging("FieldName");
        _FieldName = StructuralObject.SetValidValue(value, false);
        ReportPropertyChanged("FieldName");
    
    }

    private System.String _FieldName = new System.String();
    /**
    * No Metadata Documentation available.
    */
    public System.Int32 getFieldType() throws Exception {
        return _FieldType;
    }

    public void setFieldType(System.Int32 value) throws Exception {

        ReportPropertyChanging("FieldType");
        _FieldType = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("FieldType");
    
    }

    private System.Int32 _FieldType = new System.Int32();
    /**
    * No Metadata Documentation available.
    */
    public System.String getFieldValue() throws Exception {
        return _FieldValue;
    }

    public void setFieldValue(System.String value) throws Exception {

        ReportPropertyChanging("FieldValue");
        _FieldValue = StructuralObject.SetValidValue(value, false);
        ReportPropertyChanged("FieldValue");
    
    }

    private System.String _FieldValue = new System.String();
    /**
    * No Metadata Documentation available.
    */
    public System.SByte getFontIsBold() throws Exception {
        return _FontIsBold;
    }

    public void setFontIsBold(System.SByte value) throws Exception {

        ReportPropertyChanging("FontIsBold");
        _FontIsBold = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("FontIsBold");
    
    }

    private System.SByte _FontIsBold = new System.SByte();
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
    public System.Int32 getGrowthBehavior() throws Exception {
        return _GrowthBehavior;
    }

    public void setGrowthBehavior(System.Int32 value) throws Exception {

        ReportPropertyChanging("GrowthBehavior");
        _GrowthBehavior = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("GrowthBehavior");
    
    }

    private System.Int32 _GrowthBehavior = new System.Int32();
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
    public System.String getImageData() throws Exception {
        return _ImageData;
    }

    public void setImageData(System.String value) throws Exception {

        ReportPropertyChanging("ImageData");
        _ImageData = StructuralObject.SetValidValue(value, false);
        ReportPropertyChanged("ImageData");
    
    }

    private System.String _ImageData = new System.String();
    /**
    * No Metadata Documentation available.
    */
    public System.SByte getIsRequired() throws Exception {
        return _IsRequired;
    }

    public void setIsRequired(System.SByte value) throws Exception {

        ReportPropertyChanging("IsRequired");
        _IsRequired = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("IsRequired");
    
    }

    private System.SByte _IsRequired = new System.SByte();
    /**
    * No Metadata Documentation available.
    */
    public System.String getRadioButtonGroup() throws Exception {
        return _RadioButtonGroup;
    }

    public void setRadioButtonGroup(System.String value) throws Exception {

        ReportPropertyChanging("RadioButtonGroup");
        _RadioButtonGroup = StructuralObject.SetValidValue(value, false);
        ReportPropertyChanged("RadioButtonGroup");
    
    }

    private System.String _RadioButtonGroup = new System.String();
    /**
    * No Metadata Documentation available.
    */
    public System.String getRadioButtonValue() throws Exception {
        return _RadioButtonValue;
    }

    public void setRadioButtonValue(System.String value) throws Exception {

        ReportPropertyChanging("RadioButtonValue");
        _RadioButtonValue = StructuralObject.SetValidValue(value, false);
        ReportPropertyChanged("RadioButtonValue");
    
    }

    private System.String _RadioButtonValue = new System.String();
    /**
    * No Metadata Documentation available.
    */
    public System.Int64 getWebSheetDefID() throws Exception {
        return _WebSheetDefID;
    }

    public void setWebSheetDefID(System.Int64 value) throws Exception {

        ReportPropertyChanging("WebSheetDefID");
        _WebSheetDefID = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("WebSheetDefID");
    
    }

    private System.Int64 _WebSheetDefID = new System.Int64();
    /**
    * No Metadata Documentation available.
    */
    public System.Int64 getWebSheetFieldDefID() throws Exception {
        return _WebSheetFieldDefID;
    }

    public void setWebSheetFieldDefID(System.Int64 value) throws Exception {
        if (_WebSheetFieldDefID != value)
        {

            ReportPropertyChanging("WebSheetFieldDefID");
            _WebSheetFieldDefID = StructuralObject.SetValidValue(value);
            ReportPropertyChanged("WebSheetFieldDefID");
        
        }
         
    }

    private System.Int64 _WebSheetFieldDefID = new System.Int64();
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
    public System.Int32 getXPos() throws Exception {
        return _XPos;
    }

    public void setXPos(System.Int32 value) throws Exception {

        ReportPropertyChanging("XPos");
        _XPos = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("XPos");
    
    }

    private System.Int32 _XPos = new System.Int32();
    /**
    * No Metadata Documentation available.
    */
    public System.Int32 getYPos() throws Exception {
        return _YPos;
    }

    public void setYPos(System.Int32 value) throws Exception {

        ReportPropertyChanging("YPos");
        _YPos = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("YPos");
    
    }

    private System.Int32 _YPos = new System.Int32();
    /**
    * No Metadata Documentation available.
    */
    public System.Int32 getTabOrder() throws Exception {
        return _TabOrder;
    }

    public void setTabOrder(System.Int32 value) throws Exception {

        ReportPropertyChanging("TabOrder");
        _TabOrder = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("TabOrder");
    
    }

    private System.Int32 _TabOrder = new System.Int32();
    /**
    * No Metadata Documentation available.
    */
    public webforms_sheetdef getwebforms_sheetdef() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheetdef>GetRelatedReference("odwebserviceModel.FK_webforms_sheetfielddef_WebSheetDefID", "webforms_sheetdef").Value;
    }

    public void setwebforms_sheetdef(webforms_sheetdef value) throws Exception {
        ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheetdef>GetRelatedReference("odwebserviceModel.FK_webforms_sheetfielddef_WebSheetDefID", "webforms_sheetdef").Value = value;
    }

    /**
    * No Metadata Documentation available.
    */
    public EntityReference<webforms_sheetdef> getwebforms_sheetdefReference() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheetdef>GetRelatedReference("odwebserviceModel.FK_webforms_sheetfielddef_WebSheetDefID", "webforms_sheetdef");
    }

    public void setwebforms_sheetdefReference(EntityReference<webforms_sheetdef> value) throws Exception {
        if ((value != null))
        {
            ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheetdef>InitializeRelatedReference("odwebserviceModel.FK_webforms_sheetfielddef_WebSheetDefID", "webforms_sheetdef", value);
        }
         
    }

}


