//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Row;
import fyiReporting.RDL.RowEnumerator;

public class RowEnumerable  extends IEnumerable 
{
    int startRow = new int();
    int endRow = new int();
    List<Row> data = new List<Row>();
    boolean _LevelCheck = new boolean();
    public RowEnumerable(int start, int end, List<Row> d, boolean levelCheck) throws Exception {
        startRow = start;
        endRow = end;
        data = d;
        _LevelCheck = levelCheck;
    }

    public List<Row> getData() throws Exception {
        return data;
    }

    public int getFirstRow() throws Exception {
        return startRow;
    }

    public int getLastRow() throws Exception {
        return endRow;
    }

    public boolean getLevelCheck() throws Exception {
        return _LevelCheck;
    }

    // Methods
    public IEnumerator getEnumerator() throws Exception {
        return new RowEnumerator(this);
    }

}


