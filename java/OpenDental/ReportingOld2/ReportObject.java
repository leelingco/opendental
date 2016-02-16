//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.ReportingOld2;

import OpenDental.PIn;
import OpenDental.ReportingOld2.FieldDefKind;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.ReportingOld2.ReportObjectKind;
import OpenDental.ReportingOld2.SpecialFieldType;
import OpenDental.ReportingOld2.SummaryOperation;

/**
* There is one ReportObject for each element of an ODReport that gets printed on the page.  There are many different kinds of reportObjects
*/
public class ReportObject   
{
    private String sectionName = new String();
    private Point location = new Point();
    private Size size = new Size();
    private String name = new String();
    private ReportObjectKind objectKind = ReportObjectKind.BoxObject;
    private Font font = new Font();
    private ContentAlignment textAlign = new ContentAlignment();
    private Color foreColor = new Color();
    private String staticText = new String();
    private String formatString = new String();
    private boolean suppressIfDuplicate = new boolean();
    private String endSectionName = new String();
    private Point locationLowerRight = new Point();
    private float lineThickness = new float();
    private FieldDefKind fieldKind = FieldDefKind.DataTableField;
    private FieldValueType valueType = FieldValueType.Date;
    private SpecialFieldType specialType = SpecialFieldType.PageNofM;
    private SummaryOperation operation = SummaryOperation.Count;
    private String summarizedField = new String();
    private String dataField = new String();
    /**
    * The name of the section to which this object is attached.  For lines and boxes that span multiple sections, this is the section in which the upper part of the object resides.
    */
    public String getSectionName() throws Exception {
        return sectionName;
    }

    public void setSectionName(String value) throws Exception {
        sectionName = value;
    }

    /**
    * Location within the section. Frequently, y=0
    */
    public Point getLocation() throws Exception {
        return location;
    }

    public void setLocation(Point value) throws Exception {
        location = value;
    }

    /**
    * 
    */
    public Size getSize() throws Exception {
        return size;
    }

    public void setSize(Size value) throws Exception {
        size = value;
    }

    /**
    * The unique name of the ReportObject.
    */
    public String getName() throws Exception {
        return name;
    }

    public void setName(String value) throws Exception {
        name = value;
    }

    /**
    * For instance, FieldObject, or TextObject.
    */
    public ReportObjectKind getObjectKind() throws Exception {
        return objectKind;
    }

    public void setObjectKind(ReportObjectKind value) throws Exception {
        objectKind = value;
    }

    /**
    * 
    */
    public Font getFont() throws Exception {
        return font;
    }

    public void setFont(Font value) throws Exception {
        font = value;
    }

    /**
    * Horizontal alignment of the text.
    */
    public ContentAlignment getTextAlign() throws Exception {
        return textAlign;
    }

    public void setTextAlign(ContentAlignment value) throws Exception {
        textAlign = value;
    }

    /**
    * Can be used for text color or for line color.
    */
    public Color getForeColor() throws Exception {
        return foreColor;
    }

    public void setForeColor(Color value) throws Exception {
        foreColor = value;
    }

    /**
    * The text to display for a TextObject.  Will later include XML formatting markup.
    */
    public String getStaticText() throws Exception {
        return staticText;
    }

    public void setStaticText(String value) throws Exception {
        staticText = value;
    }

    /**
    * For a FieldObject, a C# format string that specifies how to print dates, times, numbers, and currency based on the country or on a custom format.There are a LOT of options for this string.  Look in C# help under Standard Numeric Format Strings, Custom Numeric Format Strings, Standard DateTime Format Strings, Custom DateTime Format Strings, and Enumeration Format Strings.  Once users are allowed to edit reports, we will assemble a help page with all of the common options. The best options are "n" for number, and "d" for date.
    */
    public String getFormatString() throws Exception {
        return formatString;
    }

    public void setFormatString(String value) throws Exception {
        formatString = value;
    }

    /**
    * Suppresses this field if the field for the previous record was the same.
    */
    public boolean getSuppressIfDuplicate() throws Exception {
        return suppressIfDuplicate;
    }

    public void setSuppressIfDuplicate(boolean value) throws Exception {
        suppressIfDuplicate = value;
    }

    /**
    * For graphics, the name of the Section to which the lower part of the object extends.  This will normally be the same as the sectionName unless the object spans multiple sections.  The object will then be drawn across all sections in between.
    */
    public String getEndSectionName() throws Exception {
        return endSectionName;
    }

    public void setEndSectionName(String value) throws Exception {
        endSectionName = value;
    }

    /**
    * The position of the lower right corner of the box or line in the coordinates of the endSection.
    */
    public Point getLocationLowerRight() throws Exception {
        return locationLowerRight;
    }

    public void setLocationLowerRight(Point value) throws Exception {
        locationLowerRight = value;
    }

    /**
    * 
    */
    public float getLineThickness() throws Exception {
        return lineThickness;
    }

    public void setLineThickness(float value) throws Exception {
        lineThickness = value;
    }

    /**
    * The kind of field, like FormulaField, SummaryField, or DataTableField.
    */
    public FieldDefKind getFieldKind() throws Exception {
        return fieldKind;
    }

    public void setFieldKind(FieldDefKind value) throws Exception {
        fieldKind = value;
    }

    /**
    * The value type of field, like string or datetime.
    */
    public FieldValueType getValueType() throws Exception {
        return valueType;
    }

    public void setValueType(FieldValueType value) throws Exception {
        valueType = value;
    }

    /**
    * For FieldKind=FieldDefKind.SpecialField, this is the type.  eg. pagenumber
    */
    public SpecialFieldType getSpecialType() throws Exception {
        return specialType;
    }

    public void setSpecialType(SpecialFieldType value) throws Exception {
        specialType = value;
    }

    /**
    * For FieldKind=FieldDefKind.SummaryField, the summary operation type.
    */
    public SummaryOperation getOperation() throws Exception {
        return operation;
    }

    public void setOperation(SummaryOperation value) throws Exception {
        operation = value;
    }

    /**
    * For FieldKind=FieldDefKind.SummaryField, the name of the dataField that is being summarized.  This might later be changed to refer to a ReportObject name instead (or maybe not).
    */
    public String getSummarizedField() throws Exception {
        return summarizedField;
    }

    public void setSummarizedField(String value) throws Exception {
        summarizedField = value;
    }

    /**
    * For objectKind=ReportObjectKind.FieldObject, the name of the dataField column.
    */
    public String getDataField() throws Exception {
        return dataField;
    }

    public void setDataField(String value) throws Exception {
        dataField = value;
    }

    /**
    * Default constructor.
    */
    public ReportObject() throws Exception {
    }

    /**
    * Overload for TextObject.
    */
    public ReportObject(String thisSectionName, Point thisLocation, Size thisSize, String thisStaticText, Font thisFont, ContentAlignment thisTextAlign) throws Exception {
        sectionName = thisSectionName;
        location = thisLocation;
        size = thisSize;
        staticText = thisStaticText;
        font = thisFont;
        textAlign = thisTextAlign;
        foreColor = Color.Black;
        objectKind = ReportObjectKind.TextObject;
    }

    /**
    * Overload for DataTableField ReportObject
    */
    public ReportObject(String thisSectionName, Point thisLocation, Size thisSize, String thisDataField, FieldValueType thisValueType, Font thisFont, ContentAlignment thisTextAlign, String thisFormatString) throws Exception {
        sectionName = thisSectionName;
        location = thisLocation;
        size = thisSize;
        font = thisFont;
        textAlign = thisTextAlign;
        formatString = thisFormatString;
        fieldKind = FieldDefKind.DataTableField;
        dataField = thisDataField;
        valueType = thisValueType;
        //defaults:
        foreColor = Color.Black;
        objectKind = ReportObjectKind.FieldObject;
    }

    /**
    * Overload for SummaryField ReportObject
    */
    public ReportObject(String thisSectionName, Point thisLocation, Size thisSize, SummaryOperation thisOperation, String thisSummarizedField, Font thisFont, ContentAlignment thisTextAlign, String thisFormatString) throws Exception {
        sectionName = thisSectionName;
        location = thisLocation;
        size = thisSize;
        font = thisFont;
        textAlign = thisTextAlign;
        formatString = thisFormatString;
        fieldKind = FieldDefKind.SummaryField;
        valueType = FieldValueType.Number;
        operation = thisOperation;
        summarizedField = thisSummarizedField;
        //defaults:
        foreColor = Color.Black;
        objectKind = ReportObjectKind.FieldObject;
    }

    /**
    * Overload for SpecialField ReportObject
    */
    public ReportObject(String thisSectionName, Point thisLocation, Size thisSize, FieldValueType thisValueType, SpecialFieldType thisSpecialType, Font thisFont, ContentAlignment thisTextAlign, String thisFormatString) throws Exception {
        sectionName = thisSectionName;
        location = thisLocation;
        size = thisSize;
        font = thisFont;
        textAlign = thisTextAlign;
        formatString = thisFormatString;
        fieldKind = FieldDefKind.SpecialField;
        valueType = thisValueType;
        specialType = thisSpecialType;
        //defaults:
        foreColor = Color.Black;
        objectKind = ReportObjectKind.FieldObject;
    }

    /**
    * Converts contentAlignment into a combination of StringAlignments.  More arguments will later be added for other formatting options.  This method is called by FormReport when drawing text for a reportObject.
    *  @param contentAlignment 
    *  @return
    */
    public static StringFormat getStringFormat(ContentAlignment contentAlignment) throws Exception {
        if (!Enum.IsDefined(ContentAlignment.class, (int)contentAlignment))
            throw new System.ComponentModel.InvalidEnumArgumentException("contentAlignment", (int)contentAlignment, ContentAlignment.class);
         
        StringFormat stringFormat = new StringFormat();
        ContentAlignment __dummyScrutVar0 = contentAlignment;
        if (__dummyScrutVar0.equals(ContentAlignment.MiddleCenter))
        {
            stringFormat.LineAlignment = StringAlignment.Center;
            stringFormat.Alignment = StringAlignment.Center;
        }
        else if (__dummyScrutVar0.equals(ContentAlignment.MiddleLeft))
        {
            stringFormat.LineAlignment = StringAlignment.Center;
            stringFormat.Alignment = StringAlignment.Near;
        }
        else if (__dummyScrutVar0.equals(ContentAlignment.MiddleRight))
        {
            stringFormat.LineAlignment = StringAlignment.Center;
            stringFormat.Alignment = StringAlignment.Far;
        }
        else if (__dummyScrutVar0.equals(ContentAlignment.TopCenter))
        {
            stringFormat.LineAlignment = StringAlignment.Near;
            stringFormat.Alignment = StringAlignment.Center;
        }
        else if (__dummyScrutVar0.equals(ContentAlignment.TopLeft))
        {
            stringFormat.LineAlignment = StringAlignment.Near;
            stringFormat.Alignment = StringAlignment.Near;
        }
        else if (__dummyScrutVar0.equals(ContentAlignment.TopRight))
        {
            stringFormat.LineAlignment = StringAlignment.Near;
            stringFormat.Alignment = StringAlignment.Far;
        }
        else if (__dummyScrutVar0.equals(ContentAlignment.BottomCenter))
        {
            stringFormat.LineAlignment = StringAlignment.Far;
            stringFormat.Alignment = StringAlignment.Center;
        }
        else if (__dummyScrutVar0.equals(ContentAlignment.BottomLeft))
        {
            stringFormat.LineAlignment = StringAlignment.Far;
            stringFormat.Alignment = StringAlignment.Near;
        }
        else if (__dummyScrutVar0.equals(ContentAlignment.BottomRight))
        {
            stringFormat.LineAlignment = StringAlignment.Far;
            stringFormat.Alignment = StringAlignment.Far;
        }
                 
        return stringFormat;
    }

    /**
    * Once a dataTable has been set, this method can be run to get the summary value of this field.  It will still need to be formatted. It loops through all records to get this value.  This will be changed soon to refer to the ReportObject rather than the dataTable field when summarizing.
    */
    public double getSummaryValue(DataTable dataTable, int col) throws Exception {
        //if(SummarizedField.FieldKind!=FieldDefKind.DataTableField){
        //	return 0;
        //}
        double retVal = 0;
        for (int i = 0;i < dataTable.Rows.Count;i++)
        {
            if (getOperation() == SummaryOperation.Sum)
            {
                retVal += PIn.Double(dataTable.Rows[i][col].ToString());
            }
            else //PIn.PDouble(Report.ReportTable.Rows[i][Report.DataFields.IndexOf(fieldObject.DataSource.Name)].ToString())
            if (getOperation() == SummaryOperation.Count)
            {
                retVal++;
            }
              
        }
        return retVal;
    }

}


