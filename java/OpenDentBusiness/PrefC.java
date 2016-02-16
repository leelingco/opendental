//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PIn;
import OpenDentBusiness.Pref;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

public class PrefC   
{
    /**
    * Key is prefName.  Can't use the enum, because prefs are allowed to be added by outside programmers, and this framework will support those prefs, too.
    */
    public static Dictionary<String, Pref> Dict = new Dictionary<String, Pref>();
    /**
    * This property is just a shortcut to this pref to make typing faster.  This pref is used a lot.
    */
    public static boolean getRandomKeys() throws Exception {
        return getBool(PrefName.RandomPrimaryKeys);
    }

    /**
    * This property is just a shortcut to this pref to make typing faster.  This pref is used a lot.
    */
    public static boolean getAtoZfolderUsed() throws Exception {
        return getBool(PrefName.AtoZfolderUsed);
    }

    /**
    * Gets a pref of type long.
    */
    public static long getLong(PrefName prefName) throws Exception {
        if (Dict == null)
        {
            Prefs.refreshCache();
        }
         
        if (!Dict.ContainsKey(prefName.ToString()))
        {
            throw new Exception(prefName + " is an invalid pref name.");
        }
         
        return PIn.Long(Dict[prefName.ToString()].ValueString);
    }

    /**
    * Gets a pref of type int32.  Used when the pref is an enumeration, itemorder, etc.  Also used for historical queries in ConvertDatabase.
    */
    public static int getInt(PrefName prefName) throws Exception {
        if (Dict == null)
        {
            Prefs.refreshCache();
        }
         
        if (!Dict.ContainsKey(prefName.ToString()))
        {
            throw new Exception(prefName + " is an invalid pref name.");
        }
         
        return PIn.Int(Dict[prefName.ToString()].ValueString);
    }

    /**
    * Gets a pref of type double.
    */
    public static double getDouble(PrefName prefName) throws Exception {
        if (Dict == null)
        {
            Prefs.refreshCache();
        }
         
        if (!Dict.ContainsKey(prefName.ToString()))
        {
            throw new Exception(prefName + " is an invalid pref name.");
        }
         
        return PIn.Double(Dict[prefName.ToString()].ValueString);
    }

    /**
    * Gets a pref of type bool.
    */
    public static boolean getBool(PrefName prefName) throws Exception {
        if (Dict == null)
        {
            Prefs.refreshCache();
        }
         
        if (!Dict.ContainsKey(prefName.ToString()))
        {
            throw new Exception(prefName + " is an invalid pref name.");
        }
         
        return PIn.Bool(Dict[prefName.ToString()].ValueString);
    }

    /**
    * Gets a pref of type bool, but will not throw an exception if null or not found.  Indicate whether the silent default is true or false.
    */
    public static boolean getBoolSilent(PrefName prefName, boolean silentDefault) throws Exception {
        if (Dict == null)
        {
            return silentDefault;
        }
         
        if (!Dict.ContainsKey(prefName.ToString()))
        {
            return silentDefault;
        }
         
        return PIn.Bool(Dict[prefName.ToString()].ValueString);
    }

    /**
    * Gets a pref of type string.
    */
    public static String getString(PrefName prefName) throws Exception {
        if (Dict == null)
        {
            Prefs.refreshCache();
        }
         
        if (!Dict.ContainsKey(prefName.ToString()))
        {
            throw new Exception(prefName + " is an invalid pref name.");
        }
         
        return Dict[prefName.ToString()].ValueString;
    }

    /**
    * Gets a pref of type string.  Will not throw an exception if null or not found.
    */
    public static String getStringSilent(PrefName prefName) throws Exception {
        if (Dict == null)
        {
            return "";
        }
         
        if (!Dict.ContainsKey(prefName.ToString()))
        {
            return "";
        }
         
        return Dict[prefName.ToString()].ValueString;
    }

    /**
    * Gets a pref of type date.
    */
    public static DateTime getDate(PrefName prefName) throws Exception {
        if (Dict == null)
        {
            Prefs.refreshCache();
        }
         
        if (!Dict.ContainsKey(prefName.ToString()))
        {
            throw new Exception(prefName + " is an invalid pref name.");
        }
         
        return PIn.Date(Dict[prefName.ToString()].ValueString);
    }

    /**
    * Gets a pref of type datetime.
    */
    public static DateTime getDateT(PrefName prefName) throws Exception {
        if (Dict == null)
        {
            Prefs.refreshCache();
        }
         
        if (!Dict.ContainsKey(prefName.ToString()))
        {
            throw new Exception(prefName + " is an invalid pref name.");
        }
         
        return PIn.DateT(Dict[prefName.ToString()].ValueString);
    }

    /**
    * Gets a color from an int32 pref.
    */
    public static Color getColor(PrefName prefName) throws Exception {
        if (Dict == null)
        {
            Prefs.refreshCache();
        }
         
        if (!Dict.ContainsKey(prefName.ToString()))
        {
            throw new Exception(prefName + " is an invalid pref name.");
        }
         
        return Color.FromArgb(PIn.Int(Dict[prefName.ToString()].ValueString));
    }

    /**
    * Used sometimes for prefs that are not part of the enum, especially for outside developers.
    */
    public static String getRaw(String prefName) throws Exception {
        if (Dict == null)
        {
            Prefs.refreshCache();
        }
         
        if (!Dict.ContainsKey(prefName))
        {
            throw new Exception(prefName + " is an invalid pref name.");
        }
         
        return Dict[prefName].ValueString;
    }

    /**
    * Used by an outside developer.
    */
    public static boolean containsKey(String prefName) throws Exception {
        return Dict.ContainsKey(prefName);
    }

    /**
    * Used by an outside developer.
    */
    public static boolean hListIsNull() throws Exception {
        return Dict == null;
    }

    /**
    * Only used in the unit tests.  This quick hack has not been tested.
    */
    public static Dictionary<String, Pref> getDictRef() throws Exception {
        return Dict;
    }

    public static void setDictRef(Dictionary<String, Pref> value) throws Exception {
        Dict = value;
    }

}


