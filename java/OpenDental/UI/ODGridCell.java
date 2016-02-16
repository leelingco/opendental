//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDental.UI;

import OpenDentBusiness.YN;

/**
* 
*/
public class ODGridCell   
{
    private String text = new String();
    private Color colorText = new Color();
    private YN bold = YN.Unknown;
    //private Color colorBackG;
    /**
    * Creates a new ODGridCell.
    */
    public ODGridCell() throws Exception {
        text = "";
        colorText = Color.Empty;
        bold = YN.Unknown;
    }

    //colorBackG=Color.Empty;
    /**
    * Creates a new ODGridCell.
    */
    public ODGridCell(String myText) throws Exception {
        text = myText;
        colorText = Color.Empty;
        bold = YN.Unknown;
    }

    /**
    * 
    */
    public String getText() throws Exception {
        return text;
    }

    public void setText(String value) throws Exception {
        text = value;
    }

    /**
    * Default is Color.Empty.  If any color is set, it will override the row color.
    */
    public Color getColorText() throws Exception {
        return colorText;
    }

    public void setColorText(Color value) throws Exception {
        colorText = value;
    }

    /**
    * If YN.Unknown, then the row state is used for bold.  Otherwise, this overrides the row.
    */
    public YN getBold() throws Exception {
        return bold;
    }

    public void setBold(YN value) throws Exception {
        bold = value;
    }

}


