//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PopupEvent;

public class PopupEvent  extends IComparable 
{
    public long PopupNum = new long();
    /**
    * Disable this popup until this time.
    */
    public DateTime DisableUntil = new DateTime();
    public int compareTo(Object obj) throws Exception {
        PopupEvent pop = (PopupEvent)obj;
        return DisableUntil.CompareTo(pop.DisableUntil);
    }

    public String toString() {
        try
        {
            return PopupNum.ToString() + ", " + DisableUntil.ToString();
        }
        catch (RuntimeException __dummyCatchVar21)
        {
            throw __dummyCatchVar21;
        }
        catch (Exception __dummyCatchVar21)
        {
            throw new RuntimeException(__dummyCatchVar21);
        }
    
    }

}


