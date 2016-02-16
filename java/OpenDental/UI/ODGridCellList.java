//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDental.UI;


public class ODGridCellList  extends List<OpenDental.UI.ODGridCell> 
{
    /**
    * 
    */
    public void add(String value) throws Exception {
        this.Add(new OpenDental.UI.ODGridCell(value));
    }

}


