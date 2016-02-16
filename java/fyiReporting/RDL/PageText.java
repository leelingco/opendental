//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.PageItem;

public class PageText  extends PageItem implements ICloneable
{
    String text = new String();
    // the text
    float descent = new float();
    // in some cases the Font descent will be recorded; 0 otherwise
    boolean bGrow = new boolean();
    boolean _NoClip = false;
    // on drawing disallow clipping
    public PageText(String t) throws Exception {
        text = t;
        descent = 0;
        bGrow = false;
    }

    public String getText() throws Exception {
        return text;
    }

    public void setText(String value) throws Exception {
        text = value;
    }

    public float getDescent() throws Exception {
        return descent;
    }

    public void setDescent(float value) throws Exception {
        descent = value;
    }

    public boolean getNoClip() throws Exception {
        return _NoClip;
    }

    public void setNoClip(boolean value) throws Exception {
        _NoClip = value;
    }

    public boolean getCanGrow() throws Exception {
        return bGrow;
    }

    public void setCanGrow(boolean value) throws Exception {
        bGrow = value;
    }

    public Object clone() {
        try
        {
            return this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar4)
        {
            throw __dummyCatchVar4;
        }
        catch (Exception __dummyCatchVar4)
        {
            throw new RuntimeException(__dummyCatchVar4);
        }
    
    }

}


