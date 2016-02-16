//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.PageItem;

// repeat image in y direction
public class PageLine  extends PageItem implements ICloneable
{
    public PageLine() throws Exception {
    }

    public float getX2() throws Exception {
        return getW();
    }

    public void setX2(float value) throws Exception {
        setW(value);
    }

    public float getY2() throws Exception {
        return getH();
    }

    public void setY2(float value) throws Exception {
        setH(value);
    }

    public Object clone() {
        try
        {
            return this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar2)
        {
            throw __dummyCatchVar2;
        }
        catch (Exception __dummyCatchVar2)
        {
            throw new RuntimeException(__dummyCatchVar2);
        }
    
    }

}


