//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ObjectList;

/**
* For Adding the Object number and file offset
*/
public class ObjectList  extends IComparable 
{
    public long offset = new long();
    public int objNum = new int();
    public ObjectList(int objectNum, long fileOffset) throws Exception {
        offset = fileOffset;
        objNum = objectNum;
    }

    public int compareTo(Object obj) throws Exception {
        int result = 0;
        result = (this.objNum.CompareTo(((ObjectList)obj).objNum));
        return result;
    }

}


