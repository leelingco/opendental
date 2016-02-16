//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Row;
import fyiReporting.RDL.RowEnumerable;

public class RowEnumerator  extends IEnumerator 
{
    private RowEnumerable re;
    private int index = -1;
    public RowEnumerator(RowEnumerable rea) throws Exception {
        re = rea;
    }

    //Methods
    public boolean moveNext() throws Exception {
        index++;
        while (true)
        {
            if (index + re.getFirstRow() > re.getLastRow())
                return false;
            else
            {
                if (re.getLevelCheck())
                {
                    //
                    Row r1 = re.getData()[re.getFirstRow()] instanceof Row ? (Row)re.getData()[re.getFirstRow()] : (Row)null;
                    Row r2 = re.getData()[index + re.getFirstRow()] instanceof Row ? (Row)re.getData()[index + re.getFirstRow()] : (Row)null;
                    if (r1.getLevel() == r1.getLevel())
                        return true;
                     
                    index++;
                }
                else
                    return true; 
            } 
        }
    }

    public void reset() throws Exception {
        index = -1;
    }

    public Object getCurrent() throws Exception {
        return (re.getData()[index + re.getFirstRow()]);
    }

}


