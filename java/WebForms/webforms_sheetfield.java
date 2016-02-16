//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:44 PM
//

package WebForms;

import WebForms.webforms_sheet;
import WebForms.webforms_sheetfield;


/**
* No Metadata Documentation available.
*/
public class webforms_sheetfield  extends EntityObject 
{

    /**
    * Create a new webforms_sheetfield object.
    * 
    *  @param fieldType Initial value of the FieldType property.
    *  @param fieldValue Initial value of the FieldValue property.
    *  @param fontIsBold Initial value of the FontIsBold property.
    *  @param fontSize Initial value of the FontSize property.
    *  @param growthBehavior Initial value of the GrowthBehavior property.
    *  @param height Initial value of the Height property.
    *  @param isRequired Initial value of the IsRequired property.
    *  @param radioButtonGroup Initial value of the RadioButtonGroup property.
    *  @param radioButtonValue Initial value of the RadioButtonValue property.
    *  @param sheetFieldID Initial value of the SheetFieldID property.
    *  @param sheetID Initial value of the SheetID property.
    *  @param width Initial value of the Width property.
    *  @param xPos Initial value of the XPos property.
    *  @param yPos Initial value of the YPos property.
    *  @param tabOrder Initial value of the TabOrder property.
    */
    public static webforms_sheetfield createwebforms_sheetfield(System.Int32 fieldType, System.String fieldValue, System.SByte fontIsBold, System.Single fontSize, System.Int32 growthBehavior, System.Int32 height, System.SByte isRequired, System.String radioButtonGroup, System.String radioButtonValue, System.Int64 sheetFieldID, System.Int64 sheetID, System.Int32 width, System.Int32 xPos, System.Int32 yPos, System.Int32 tabOrder) throws Exception {
        webforms_sheetfield webforms_sheetfield = new webforms_sheetfield();
        webforms_sheetfield.setFieldType(fieldType);
        webforms_sheetfield.setFieldValue(fieldValue);
        webforms_sheetfield.setFontIsBold(fontIsBold);
        webforms_sheetfield.setFontSize(fontSize);
        webforms_sheetfield.setGrowthBehavior(growthBehavior);
        webforms_sheetfield.setHeight(height);
        webforms_sheetfield.setIsRequired(isRequired);
        webforms_sheetfield.setRadioButtonGroup(radioButtonGroup);
        webforms_sheetfield.setRadioButtonValue(radioButtonValue);
        webforms_sheetfield.setSheetFieldID(sheetFieldID);
        webforms_sheetfield.setSheetID(sheetID);
        webforms_sheetfield.setWidth(width);
        webforms_sheetfield.setXPos(xPos);
        webforms_sheetfield.setYPos(yPos);
        webforms_sheetfield.setTabOrder(tabOrder);
        return webforms_sheetfield;
    }

    /**
    * No Metadata Documentation available.
    */
    public System.String getFieldName() throws Exception {
        return _FieldName;
    }

    public void setFieldName(System.String value) throws Exception {

        ReportPropertyChanging("FieldName");
        _FieldName = StructuralObject.SetValidValue(value, true);
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
    public System.Int64 getSheetFieldID() throws Exception {
        return _SheetFieldID;
    }

    public void setSheetFieldID(System.Int64 value) throws Exception {
        if (_SheetFieldID != value)
        {

            ReportPropertyChanging("SheetFieldID");
            _SheetFieldID = StructuralObject.SetValidValue(value);
            ReportPropertyChanged("SheetFieldID");
        
        }
         
    }

    private System.Int64 _SheetFieldID = new System.Int64();
    /**
    * No Metadata Documentation available.
    */
    public System.Int64 getSheetID() throws Exception {
        return _SheetID;
    }

    public void setSheetID(System.Int64 value) throws Exception {

        ReportPropertyChanging("SheetID");
        _SheetID = StructuralObject.SetValidValue(value);
        ReportPropertyChanged("SheetID");
    
    }

    private System.Int64 _SheetID = new System.Int64();
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
    public webforms_sheet getwebforms_sheet() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheet>GetRelatedReference("odwebserviceModel.FK_webforms_sheetfield_SheetID", "webforms_sheet").Value;
    }

    public void setwebforms_sheet(webforms_sheet value) throws Exception {
        ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheet>GetRelatedReference("odwebserviceModel.FK_webforms_sheetfield_SheetID", "webforms_sheet").Value = value;
    }

    /**
    * No Metadata Documentation available.
    */
    public EntityReference<webforms_sheet> getwebforms_sheetReference() throws Exception {
        return ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheet>GetRelatedReference("odwebserviceModel.FK_webforms_sheetfield_SheetID", "webforms_sheet");
    }

    public void setwebforms_sheetReference(EntityReference<webforms_sheet> value) throws Exception {
        if ((value != null))
        {
            ((IEntityWithRelationships)this).RelationshipManager.<webforms_sheet>InitializeRelatedReference("odwebserviceModel.FK_webforms_sheetfield_SheetID", "webforms_sheet", value);
        }
         
    }

}


