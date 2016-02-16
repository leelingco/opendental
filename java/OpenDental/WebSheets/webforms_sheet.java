//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.EntityObject;
import OpenDental.WebSheets.EntityReferenceOfwebforms_preference;


/**
* 
*/
public class webforms_sheet  extends EntityObject 
{

    private System.DateTime dateTimeSheetField = new System.DateTime();
    private long dentalOfficeIDField = new long();
    private String fontNameField = new String();
    private float fontSizeField = new float();
    private int heightField = new int();
    private sbyte isLandscapeField = new sbyte();
    private long sheetIDField = new long();
    private int sheetTypeField = new int();
    private int widthField = new int();
    private String descriptionField = new String();
    private EntityReferenceOfwebforms_preference webforms_preferenceReferenceField;
    /**
    * 
    */
    public System.DateTime getDateTimeSheet() throws Exception {
        return this.dateTimeSheetField;
    }

    public void setDateTimeSheet(System.DateTime value) throws Exception {
        this.dateTimeSheetField = value;
    }

    /**
    * 
    */
    public long getDentalOfficeID() throws Exception {
        return this.dentalOfficeIDField;
    }

    public void setDentalOfficeID(long value) throws Exception {
        this.dentalOfficeIDField = value;
    }

    /**
    * 
    */
    public String getFontName() throws Exception {
        return this.fontNameField;
    }

    public void setFontName(String value) throws Exception {
        this.fontNameField = value;
    }

    /**
    * 
    */
    public float getFontSize() throws Exception {
        return this.fontSizeField;
    }

    public void setFontSize(float value) throws Exception {
        this.fontSizeField = value;
    }

    /**
    * 
    */
    public int getHeight() throws Exception {
        return this.heightField;
    }

    public void setHeight(int value) throws Exception {
        this.heightField = value;
    }

    /**
    * 
    */
    public sbyte getIsLandscape() throws Exception {
        return this.isLandscapeField;
    }

    public void setIsLandscape(sbyte value) throws Exception {
        this.isLandscapeField = value;
    }

    /**
    * 
    */
    public long getSheetID() throws Exception {
        return this.sheetIDField;
    }

    public void setSheetID(long value) throws Exception {
        this.sheetIDField = value;
    }

    /**
    * 
    */
    public int getSheetType() throws Exception {
        return this.sheetTypeField;
    }

    public void setSheetType(int value) throws Exception {
        this.sheetTypeField = value;
    }

    /**
    * 
    */
    public int getWidth() throws Exception {
        return this.widthField;
    }

    public void setWidth(int value) throws Exception {
        this.widthField = value;
    }

    /**
    * 
    */
    public String getDescription() throws Exception {
        return this.descriptionField;
    }

    public void setDescription(String value) throws Exception {
        this.descriptionField = value;
    }

    /**
    * 
    */
    public EntityReferenceOfwebforms_preference getwebforms_preferenceReference() throws Exception {
        return this.webforms_preferenceReferenceField;
    }

    public void setwebforms_preferenceReference(EntityReferenceOfwebforms_preference value) throws Exception {
        this.webforms_preferenceReferenceField = value;
    }

}


