//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:35 PM
//

package SparksToothChart;

import CS2JNet.System.StringSupport;
import SparksToothChart.ToothGraphic;
import SparksToothChart.ToothGraphicCollection;

/**
* A strongly typed collection of type ToothGraphic
*/
public class ToothGraphicCollection  extends CollectionBase 
{
    /**
    * Returns the ToothGraphic with the given index.
    */
    public ToothGraphic get___idx(int index) throws Exception {
        return (ToothGraphic)List[index];
    }

    public void set___idx(int index, ToothGraphic value) throws Exception {
        List[index] = value;
    }

    /**
    * Returns the ToothGraphic with the given toothID.
    */
    public ToothGraphic get___idx(String toothID) throws Exception {
        if (!StringSupport.equals(toothID, "implant") && !ToothGraphic.isValidToothID(toothID))
        {
            throw new ArgumentException("Tooth ID not valid: " + toothID);
        }
         
        for (int i = 0;i < List.Count;i++)
        {
            if (StringSupport.equals(((ToothGraphic)List[i]).getToothID(), toothID))
            {
                return (ToothGraphic)List[i];
            }
             
        }
        return null;
    }

    public void set___idx(String toothID, ToothGraphic value) throws Exception {
    }

    //List[index]=value;
    /**
    * 
    */
    public int add(ToothGraphic value) throws Exception {
        return (List.Add(value));
    }

    /**
    * 
    */
    public int indexOf(ToothGraphic value) throws Exception {
        return (List.IndexOf(value));
    }

    /**
    * 
    */
    public void insert(int index, ToothGraphic value) throws Exception {
        List.Insert(index, value);
    }

    /**
    * 
    */
    public void remove(ToothGraphic value) throws Exception {
        List.Remove(value);
    }

    /**
    * 
    */
    public boolean contains(ToothGraphic value) throws Exception {
        return (List.Contains(value));
    }

    //If value is not of type ToothGraphic, this will return false.
    /*
    		///<summary></summary>
    		public bool Contains(string toothID) {
    			//If value is not of type ToothGraphic, this will return false.
    			return (List.Contains(value));
    		}*/
    /**
    * 
    */
    protected void onInsert(int index, Object value) throws Exception {
        if (value.GetType() != ToothGraphic.class)
        {
            throw new ArgumentException("value must be of type ToothGraphic.", "value");
        }
         
    }

    /**
    * 
    */
    protected void onRemove(int index, Object value) throws Exception {
        if (value.GetType() != ToothGraphic.class)
        {
            throw new ArgumentException("value must be of type ToothGraphic.", "value");
        }
         
    }

    /**
    * 
    */
    protected void onSet(int index, Object oldValue, Object newValue) throws Exception {
        if (newValue.GetType() != ToothGraphic.class)
        {
            throw new ArgumentException("newValue must be of type ToothGraphic.", "newValue");
        }
         
    }

    /**
    * 
    */
    protected void onValidate(Object value) throws Exception {
        if (value.GetType() != ToothGraphic.class)
        {
            throw new ArgumentException("value must be of type ToothGraphic.");
        }
         
    }

    /**
    * 
    */
    public ToothGraphicCollection copy() throws Exception {
        ToothGraphicCollection collect = new ToothGraphicCollection();
        for (int i = 0;i < this.Count;i++)
        {
            collect.add(this.get___idx(i).copy());
        }
        return collect;
    }

}


