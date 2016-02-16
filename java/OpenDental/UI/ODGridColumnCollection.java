//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDental.UI;

import CS2JNet.System.StringSupport;

/**
* A strongly typed collection of ODGridColumns
*/
public class ODGridColumnCollection  extends CollectionBase 
{
    /**
    * Returns the GridColumn with the given index.
    */
    public OpenDental.UI.ODGridColumn get___idx(int index) throws Exception {
        return (OpenDental.UI.ODGridColumn)List[index];
    }

    public void set___idx(int index, OpenDental.UI.ODGridColumn value) throws Exception {
        List[index] = value;
    }

    //public ODGridColumn Item(int index){
    //	return (ODGridColumn)List[index];
    //}
    /**
    * 
    */
    public int add(OpenDental.UI.ODGridColumn value) throws Exception {
        return (List.Add(value));
    }

    /**
    * 
    */
    public int indexOf(OpenDental.UI.ODGridColumn value) throws Exception {
        return (List.IndexOf(value));
    }

    /**
    * 
    */
    public void insert(int index, OpenDental.UI.ODGridColumn value) throws Exception {
        List.Insert(index, value);
    }

    /**
    * 
    */
    public void remove(OpenDental.UI.ODGridColumn value) throws Exception {
        List.Remove(value);
    }

    /**
    * 
    */
    public boolean contains(OpenDental.UI.ODGridColumn value) throws Exception {
        return (List.Contains(value));
    }

    // If value is not of type ODGridColumn, this will return false.
    /**
    * 
    */
    protected void onInsert(int index, Object value) throws Exception {
        if (value.GetType() != OpenDental.UI.ODGridColumn.class)
            throw new ArgumentException("value must be of type ODGridColumn.", "value");
         
    }

    /**
    * 
    */
    protected void onRemove(int index, Object value) throws Exception {
        if (value.GetType() != OpenDental.UI.ODGridColumn.class)
            throw new ArgumentException("value must be of type ODGridColumn.", "value");
         
    }

    /**
    * 
    */
    protected void onSet(int index, Object oldValue, Object newValue) throws Exception {
        if (newValue.GetType() != OpenDental.UI.ODGridColumn.class)
            throw new ArgumentException("newValue must be of type ODGridColumn.", "newValue");
         
    }

    /**
    * 
    */
    protected void onValidate(Object value) throws Exception {
        if (value.GetType() != OpenDental.UI.ODGridColumn.class)
            throw new ArgumentException("value must be of type ODGridColumn.");
         
    }

    /**
    * Gets the index of the column with the specified heading.
    */
    public int getIndex(String heading) throws Exception {
        for (int i = 0;i < List.Count;i++)
        {
            if (StringSupport.equals(((OpenDental.UI.ODGridColumn)List[i]).getHeading(), heading))
            {
                return i;
            }
             
        }
        return -1;
    }

}


//not found
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