//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.PageItem;

public class PageRectangle  extends PageItem implements ICloneable
{
    public PageRectangle() throws Exception {
    }

    public Object clone() {
        try
        {
            return this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar3)
        {
            throw __dummyCatchVar3;
        }
        catch (Exception __dummyCatchVar3)
        {
            throw new RuntimeException(__dummyCatchVar3);
        }
    
    }

}


