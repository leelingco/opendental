//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDental.UI;

import OpenDental.UI.GridSortingStrategy;

/**
* 
*/
//[DesignTimeVisible(false)]
//[TypeConverter(typeof(GridColumnTypeConverter))]
public class ODGridColumn   
{
    private String heading = new String();
    private int colWidth = new int();
    private HorizontalAlignment textAlign = new HorizontalAlignment();
    private boolean isEditable = new boolean();
    //private System.ComponentModel.Container components = null;
    private ImageList imageList = new ImageList();
    private GridSortingStrategy sortingStrategy = GridSortingStrategy.StringCompare;
    /**
    * Creates a new ODGridcolumn.
    */
    public ODGridColumn() throws Exception {
        heading = "";
        colWidth = 80;
        textAlign = HorizontalAlignment.Left;
        isEditable = false;
        imageList = null;
        sortingStrategy = GridSortingStrategy.StringCompare;
    }

    /**
    * Creates a new ODGridcolumn with the given heading and width. Alignment left
    */
    public ODGridColumn(String heading, int colWidth, HorizontalAlignment textAlign, boolean isEditable) throws Exception {
        this.heading = heading;
        this.colWidth = colWidth;
        this.textAlign = textAlign;
        this.isEditable = isEditable;
        imageList = null;
        sortingStrategy = GridSortingStrategy.StringCompare;
    }

    /**
    * Creates a new ODGridcolumn with the given heading and width. Alignment left
    */
    public ODGridColumn(String heading, int colWidth, boolean isEditable) throws Exception {
        this.heading = heading;
        this.colWidth = colWidth;
        this.isEditable = isEditable;
        imageList = null;
        sortingStrategy = GridSortingStrategy.StringCompare;
    }

    /**
    * Creates a new ODGridcolumn with the given heading and width.
    */
    public ODGridColumn(String heading, int colWidth, HorizontalAlignment textAlign) throws Exception {
        this.heading = heading;
        this.colWidth = colWidth;
        this.textAlign = textAlign;
        imageList = null;
        sortingStrategy = GridSortingStrategy.StringCompare;
    }

    /**
    * Creates a new ODGridcolumn with the given heading and width. Alignment left
    */
    public ODGridColumn(String heading, int colWidth) throws Exception {
        this.heading = heading;
        this.colWidth = colWidth;
        this.textAlign = HorizontalAlignment.Left;
        imageList = null;
        sortingStrategy = GridSortingStrategy.StringCompare;
    }

    /**
    * 
    */
    public String getHeading() throws Exception {
        return heading;
    }

    public void setHeading(String value) throws Exception {
        heading = value;
    }

    /**
    * 
    */
    public int getColWidth() throws Exception {
        return colWidth;
    }

    public void setColWidth(int value) throws Exception {
        colWidth = value;
    }

    /**
    * 
    */
    public HorizontalAlignment getTextAlign() throws Exception {
        return textAlign;
    }

    public void setTextAlign(HorizontalAlignment value) throws Exception {
        textAlign = value;
    }

    /**
    * 
    */
    public boolean getIsEditable() throws Exception {
        return isEditable;
    }

    public void setIsEditable(boolean value) throws Exception {
        isEditable = value;
    }

    /**
    * 
    */
    public ImageList getImageList() throws Exception {
        return imageList;
    }

    public void setImageList(ImageList value) throws Exception {
        imageList = value;
    }

    /**
    * 
    */
    public GridSortingStrategy getSortingStrategy() throws Exception {
        return sortingStrategy;
    }

    public void setSortingStrategy(GridSortingStrategy value) throws Exception {
        sortingStrategy = value;
    }

}


