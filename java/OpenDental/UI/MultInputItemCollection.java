//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental.UI;

import OpenDental.UI.MultInputItem;

/**
* Strongly typed collection of type MultInputItems.
*/
public class MultInputItemCollection  extends CollectionBase 
{
    /**
    * Returns the MenuInputItem with the given index.
    */
    public MultInputItem get___idx(int index) throws Exception {
        return ((MultInputItem)List[index]);
    }

    public void set___idx(int index, MultInputItem value) throws Exception {
        List[index] = value;
    }

    /**
    * 
    */
    public int add(MultInputItem value) throws Exception {
        return (List.Add(value));
    }

    /**
    * 
    */
    public int indexOf(MultInputItem value) throws Exception {
        return (List.IndexOf(value));
    }

    /**
    * 
    */
    public void insert(int index, MultInputItem value) throws Exception {
        List.Insert(index, value);
    }

}


