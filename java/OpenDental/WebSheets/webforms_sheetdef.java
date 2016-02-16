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
public class webforms_sheetdef  extends EntityObject 
{

    private long dentalOfficeIDField = new long();
    private String descriptionField = new String();
    private String fontNameField = new String();
    private float fontSizeField = new float();
    private int heightField = new int();
    private sbyte isLandscapeField = new sbyte();
    private int sheetTypeField = new int();
    private long webSheetDefIDField = new long();
    private int widthField = new int();
    private EntityReferenceOfwebforms_preference webforms_preferenceReferenceField;
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
    public String getDescription() throws Exception {
        return this.descriptionField;
    }

    public void setDescription(String value) throws Exception {
        this.descriptionField = value;
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
    public int getSheetType() throws Exception {
        return this.sheetTypeField;
    }

    public void setSheetType(int value) throws Exception {
        this.sheetTypeField = value;
    }

    /**
    * 
    */
    public long getWebSheetDefID() throws Exception {
        return this.webSheetDefIDField;
    }

    public void setWebSheetDefID(long value) throws Exception {
        this.webSheetDefIDField = value;
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
    public EntityReferenceOfwebforms_preference getwebforms_preferenceReference() throws Exception {
        return this.webforms_preferenceReferenceField;
    }

    public void setwebforms_preferenceReference(EntityReferenceOfwebforms_preference value) throws Exception {
        this.webforms_preferenceReferenceField = value;
    }

}


