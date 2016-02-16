//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Def;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Defs;
import OpenDentBusiness.ImageCategorySpecial;

public class DefC   
{
    private static Def[][] shortt = new Def[][]();
    private static Def[][] longg = new Def[][]();
    /**
    * Stores all defs in a 2D array.
    */
    public static Def[][] getLong() throws Exception {
        if (longg == null)
        {
            Defs.refreshCache();
        }
         
        return longg;
    }

    public static void setLong(Def[][] value) throws Exception {
        longg = value;
    }

    /**
    * Stores all defs in a 2D array except the hidden ones.  The first dimension is the category, in int format.  The second dimension is the index of the definition in this category.  This is dependent on how it was refreshed, and not on what is in the database.  If you need to reference a specific def, then the DefNum is more effective.
    */
    public static Def[][] getShort() throws Exception {
        if (shortt == null)
        {
            Defs.refreshCache();
        }
         
        return shortt;
    }

    public static void setShort(Def[][] value) throws Exception {
        shortt = value;
    }

    public static boolean getDefShortIsNull() throws Exception {
        if (shortt == null)
        {
            return true;
        }
         
        return false;
    }

    /**
    * Gets a list of defs for one category.
    */
    public static Def[] getList(DefCat defCat) throws Exception {
        return getShort()[((Enum)defCat).ordinal()];
    }

    /**
    * Get one def from Long.  Returns null if not found.  Only used for very limited situations.  Other Get functions tend to be much more useful since they don't return null.  There is also BIG potential for silent bugs if you use this.ItemOrder instead of GetOrder().
    */
    public static Def getDef(DefCat myCat, long myDefNum) throws Exception {
        for (int i = 0;i < DefC.getLong()[((Enum)myCat).ordinal()].GetLength(0);i++)
        {
            if (DefC.getLong()[((Enum)myCat).ordinal()][i].DefNum == myDefNum)
            {
                return DefC.getLong()[((Enum)myCat).ordinal()][i].Copy();
            }
             
        }
        return null;
    }

    /**
    * 
    */
    public static String getName(DefCat myCat, long myDefNum) throws Exception {
        if (myDefNum == 0)
        {
            return "";
        }
         
        for (int i = 0;i < DefC.getLong()[((Enum)myCat).ordinal()].GetLength(0);i++)
        {
            if (DefC.getLong()[((Enum)myCat).ordinal()][i].DefNum == myDefNum)
            {
                return DefC.getLong()[((Enum)myCat).ordinal()][i].ItemName;
            }
             
        }
        return "";
    }

    /**
    * Returns 0 if it can't find the named def.  If the name is blank, then it returns the first def in the category.
    */
    public static long getByExactName(DefCat myCat, String itemName) throws Exception {
        if (StringSupport.equals(itemName, ""))
        {
            return DefC.getLong()[((Enum)myCat).ordinal()][0].DefNum;
        }
         
        for (int i = 0;i < DefC.getLong()[((Enum)myCat).ordinal()].GetLength(0);i++)
        {
            //return the first one in the list
            if (StringSupport.equals(DefC.getLong()[((Enum)myCat).ordinal()][i].ItemName, itemName))
            {
                return DefC.getLong()[((Enum)myCat).ordinal()][i].DefNum;
            }
             
        }
        return 0;
    }

    /**
    * Returns the named def.  If it can't find the name, then it returns the first def in the category.
    */
    public static long getByExactNameNeverZero(DefCat myCat, String itemName) throws Exception {
        if (StringSupport.equals(itemName, ""))
        {
            return DefC.getLong()[((Enum)myCat).ordinal()][0].DefNum;
        }
         
        for (int i = 0;i < DefC.getLong()[((Enum)myCat).ordinal()].GetLength(0);i++)
        {
            //return the first one in the list
            if (StringSupport.equals(DefC.getLong()[((Enum)myCat).ordinal()][i].ItemName, itemName))
            {
                return DefC.getLong()[((Enum)myCat).ordinal()][i].DefNum;
            }
             
        }
        if (DefC.getLong()[((Enum)myCat).ordinal()].Length == 0)
        {
            Def def = new Def();
            def.Category = myCat;
            def.ItemOrder = 0;
            def.ItemName = itemName;
            Defs.insert(def);
            Defs.refreshCache();
        }
         
        return DefC.getLong()[((Enum)myCat).ordinal()][0].DefNum;
    }

    //return the first one in the list
    /**
    * Gets the order of the def within Short or -1 if not found.
    */
    public static int getOrder(DefCat myCat, long myDefNum) throws Exception {
        for (int i = 0;i < DefC.getShort()[((Enum)myCat).ordinal()].GetLength(0);i++)
        {
            //gets the index in the list of unhidden (the Short list).
            if (DefC.getShort()[((Enum)myCat).ordinal()][i].DefNum == myDefNum)
            {
                return i;
            }
             
        }
        return -1;
    }

    /**
    * 
    */
    public static String getValue(DefCat myCat, long myDefNum) throws Exception {
        String retStr = "";
        for (int i = 0;i < DefC.getLong()[((Enum)myCat).ordinal()].GetLength(0);i++)
        {
            if (DefC.getLong()[((Enum)myCat).ordinal()][i].DefNum == myDefNum)
            {
                retStr = DefC.getLong()[((Enum)myCat).ordinal()][i].ItemValue;
            }
             
        }
        return retStr;
    }

    /**
    * 
    */
    public static Color getColor(DefCat myCat, long myDefNum) throws Exception {
        Color retCol = Color.White;
        for (int i = 0;i < DefC.getLong()[((Enum)myCat).ordinal()].GetLength(0);i++)
        {
            if (DefC.getLong()[((Enum)myCat).ordinal()][i].DefNum == myDefNum)
            {
                retCol = DefC.getLong()[((Enum)myCat).ordinal()][i].ItemColor;
            }
             
        }
        return retCol;
    }

    /**
    * 
    */
    public static boolean getHidden(DefCat myCat, long myDefNum) throws Exception {
        for (int i = 0;i < DefC.getLong()[((Enum)myCat).ordinal()].GetLength(0);i++)
        {
            if (DefC.getLong()[((Enum)myCat).ordinal()][i].DefNum == myDefNum)
            {
                return DefC.getLong()[((Enum)myCat).ordinal()][i].IsHidden;
            }
             
        }
        return false;
    }

    /*//<summary>Allowed types are blank, C, or A.  Only used in FormInsPlan.</summary>
    		public static Def[] GetFeeSchedList(string type) {
    			ArrayList AL=new ArrayList();
    			for(int i=0;i<DefC.Short[(int)DefCat.FeeSchedNames].Length;i++) {
    				if(DefC.Short[(int)DefCat.FeeSchedNames][i].ItemValue==type) {
    					AL.Add(DefC.Short[(int)DefCat.FeeSchedNames][i]);
    				}
    			}
    			Def[] retVal=new Def[AL.Count];
    			AL.CopyTo(retVal);
    			return retVal;
    		}*/
    /**
    * 
    */
    public static List<Def> getPositiveAdjTypes() throws Exception {
        List<Def> retVal = new List<Def>();
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.AdjTypes).ordinal()].Length;i++)
        {
            if (StringSupport.equals(DefC.getShort()[((Enum)DefCat.AdjTypes).ordinal()][i].ItemValue, "+"))
            {
                retVal.Add(DefC.getShort()[((Enum)DefCat.AdjTypes).ordinal()][i]);
            }
             
        }
        return retVal;
    }

    /**
    * Returns a DefNum for the special image category specified.  Returns 0 if no match found.
    */
    public static long getImageCat(ImageCategorySpecial specialCat) throws Exception {
        Def[] defs = DefC.getList(DefCat.ImageCats);
        for (int i = 0;i < defs.Length;i++)
        {
            if (defs[i].ItemValue.Contains(specialCat.ToString()))
            {
                return defs[i].DefNum;
            }
             
        }
        return 0;
    }

}


