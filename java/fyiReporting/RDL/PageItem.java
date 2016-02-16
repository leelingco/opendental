//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.PageItem;
import fyiReporting.RDL.StyleInfo;

public class PageItem  extends ICloneable implements IComparable
{
    float x = new float();
    // x coordinate
    float y = new float();
    // y coordinate
    float h = new float();
    // height  --- line redefines as Y2
    float w = new float();
    // width   --- line redefines as X2
    String hyperlink = new String();
    //  a hyperlink the object should link to
    String bookmarklink = new String();
    //  a hyperlink within the report object should link to
    String tooltip = new String();
    //  a message to display when user hovers with mouse
    int zindex = new int();
    // zindex; items will be sorted by this
    int itemNumber = new int();
    //  original number of item
    StyleInfo si;
    // all the style information evaluated
    public float getX() throws Exception {
        return x;
    }

    public void setX(float value) throws Exception {
        x = value;
    }

    public float getY() throws Exception {
        return y;
    }

    public void setY(float value) throws Exception {
        y = value;
    }

    public int getZIndex() throws Exception {
        return zindex;
    }

    public void setZIndex(int value) throws Exception {
        zindex = value;
    }

    public int getItemNumber() throws Exception {
        return itemNumber;
    }

    public void setItemNumber(int value) throws Exception {
        itemNumber = value;
    }

    public float getH() throws Exception {
        return h;
    }

    public void setH(float value) throws Exception {
        h = value;
    }

    public float getW() throws Exception {
        return w;
    }

    public void setW(float value) throws Exception {
        w = value;
    }

    public String getHyperLink() throws Exception {
        return hyperlink;
    }

    public void setHyperLink(String value) throws Exception {
        hyperlink = value;
    }

    public String getBookmarkLink() throws Exception {
        return bookmarklink;
    }

    public void setBookmarkLink(String value) throws Exception {
        bookmarklink = value;
    }

    public String getTooltip() throws Exception {
        return tooltip;
    }

    public void setTooltip(String value) throws Exception {
        tooltip = value;
    }

    public StyleInfo getSI() throws Exception {
        return si;
    }

    public void setSI(StyleInfo value) throws Exception {
        si = value;
    }

    public Object clone() {
        try
        {
            return this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    // Sort items based on zindex, then on order items were added to array
    public int compareTo(Object obj) throws Exception {
        PageItem pi = obj instanceof PageItem ? (PageItem)obj : (PageItem)null;
        int rc = this.zindex - pi.zindex;
        if (rc == 0)
            rc = this.itemNumber - pi.itemNumber;
         
        return rc;
    }

}


