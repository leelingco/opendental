//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDental.UI;


/**
* A strongly typed collection of ODGridRows
*/
public class ODGridRowCollection  extends CollectionBase 
{
    /**
    * Returns the GridRow with the given index.
    */
    public OpenDental.UI.ODGridRow get___idx(int index) throws Exception {
        return (OpenDental.UI.ODGridRow)List[index];
    }

    public void set___idx(int index, OpenDental.UI.ODGridRow value) throws Exception {
        List[index] = value;
    }

    /**
    * 
    */
    public int add(OpenDental.UI.ODGridRow value) throws Exception {
        return (List.Add(value));
    }

    /**
    * 
    */
    public int indexOf(OpenDental.UI.ODGridRow value) throws Exception {
        return (List.IndexOf(value));
    }

    /**
    * 
    */
    public void insert(int index, OpenDental.UI.ODGridRow value) throws Exception {
        List.Insert(index, value);
    }

    /**
    * 
    */
    public void remove(OpenDental.UI.ODGridRow value) throws Exception {
        List.Remove(value);
    }

    /**
    * 
    */
    public boolean contains(OpenDental.UI.ODGridRow value) throws Exception {
        return (List.Contains(value));
    }

    //If value is not of type ODGridRow, this will return false.
    /**
    * 
    */
    protected void onInsert(int index, Object value) throws Exception {
        if (value.GetType() != OpenDental.UI.ODGridRow.class)
            throw new ArgumentException("value must be of type ODGridRow.", "value");
         
    }

    /**
    * 
    */
    protected void onRemove(int index, Object value) throws Exception {
        if (value.GetType() != OpenDental.UI.ODGridRow.class)
            throw new ArgumentException("value must be of type ODGridRow.", "value");
         
    }

    /**
    * 
    */
    protected void onSet(int index, Object oldValue, Object newValue) throws Exception {
        if (newValue.GetType() != OpenDental.UI.ODGridRow.class)
            throw new ArgumentException("newValue must be of type ODGridRow.", "newValue");
         
    }

    /**
    * 
    */
    protected void onValidate(Object value) throws Exception {
        if (value.GetType() != OpenDental.UI.ODGridRow.class)
            throw new ArgumentException("value must be of type ODGridRow.");
         
    }

}


/*
		///<summary></summary>
		public void Remove(int index){
			if((index>Count-1) || (index<0)){
				throw new System.IndexOutOfRangeException();
			}
			else{
				List.RemoveAt(index);
			}
		}*/
/*
		///<summary>The button is retrieved from List and explicitly cast to the button type.</summary>
		public ODToolBarButton Item(int index){
			return (ODToolBarButton)List[index];
		}*/
/*
		///<summary></summary>
		public int IndexOf(ODGridColumn value){
			return(List.IndexOf(value));
		}*/