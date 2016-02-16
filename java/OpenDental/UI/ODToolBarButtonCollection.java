//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.UI;

import CS2JNet.System.StringSupport;

/**
* A strongly typed collection of ODToolBarButtons
*/
public class ODToolBarButtonCollection  extends CollectionBase 
{
    /**
    * Returns the Button with the given index.
    */
    public OpenDental.UI.ODToolBarButton get___idx(int index) throws Exception {
        return ((OpenDental.UI.ODToolBarButton)List[index]);
    }

    public void set___idx(int index, OpenDental.UI.ODToolBarButton value) throws Exception {
        List[index] = value;
    }

    /**
    * Returns the Button with the given string tag.
    */
    public OpenDental.UI.ODToolBarButton get___idx(String buttonTag) throws Exception {
        for (int i = 0;i < List.Count;i++)
        {
            if (StringSupport.equals(((OpenDental.UI.ODToolBarButton)List[i]).getTag().ToString(), buttonTag))
            {
                return ((OpenDental.UI.ODToolBarButton)List[i]);
            }
             
        }
        return null;
    }

    /**
    * 
    */
    public void add(OpenDental.UI.ODToolBarButton button) throws Exception {
        List.Add(button);
    }

    /**
    * 
    */
    public void remove(int index) throws Exception {
        if ((index > Count - 1) || (index < 0))
        {
            throw new System.IndexOutOfRangeException();
        }
        else
        {
            List.RemoveAt(index);
        } 
    }

    /*
    		///<summary>The button is retrieved from List and explicitly cast to the button type.</summary>
    		public ODToolBarButton Item(int index){
    			return (ODToolBarButton)List[index];
    		}*/
    /**
    * 
    */
    public int indexOf(OpenDental.UI.ODToolBarButton value) throws Exception {
        return (List.IndexOf(value));
    }

}


