//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Language;
import OpenDentBusiness.LanguageForeign;
import OpenDentBusiness.LanguageForeigns;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Handles database commands for the language table in the database.
*/
public class Lans   
{
    private static Dictionary<String, Language> hList = new Dictionary<String, Language>();
    //<summary>Used by g to keep track of whether any language items were inserted into db. If so, a refresh gets done.</summary>
    //public static bool ItemInserted;
    /**
    * key=ClassType+English.  Value =Language object.
    */
    //No need to check RemotingRole; no call to db.
    public static Dictionary<String, Language> getHList() throws Exception {
        if (hList == null)
        {
            if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
            {
                hList = new Dictionary<String, Language>();
                return hList;
            }
             
            refreshCache();
        }
         
        return hList;
    }

    public static void setHList(Dictionary<String, Language> value) throws Exception {
        hList = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM language";
        DataTable table = null;
        if (!StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
        {
            table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
            table.TableName = "Language";
        }
         
        fillCache(table);
        return table;
    }

    /**
    * Refreshed automatically to always be kept current with all phrases, regardless of whether there are any entries in LanguageForeign table.
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        hList = new Dictionary<String, Language>();
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
        {
            return ;
        }
         
        List<Language> list = Crud.LanguageCrud.TableToList(table);
        for (int i = 0;i < list.Count;i++)
        {
            if (!hList.ContainsKey(list[i].ClassType + list[i].English))
            {
                hList.Add(list[i].ClassType + list[i].English, list[i]);
            }
             
        }
    }

    /**
    * Converts a string to the current language.
    */
    public static String g(String classType, String text) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retVal = Lans.convertString(classType,text);
        return retVal;
    }

    //if(ItemInserted) {
    //	RefreshCache();
    //}
    /**
    * Converts a string to the current language.
    */
    public static String g(System.Object sender, String text) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retVal = Lans.ConvertString(sender.GetType().Name, text);
        return retVal;
    }

    //if(ItemInserted) {
    //	RefreshCache();
    //}
    /**
    * This is where all the action happens.  This method is used by all the others.  This is always run on the client rather than the server, unless, of course, it's being called from the server.  If it inserts an item into the db table, it will also add it to the local cache, but will not trigger a refresh on both ends.
    */
    public static String convertString(String classType, String text) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
        {
            return text;
        }
         
        if (StringSupport.equals(text, ""))
        {
            return "";
        }
         
        if (hList == null)
        {
            return text;
        }
         
        //ItemInserted=false;
        if (!hList.ContainsKey(classType + text))
        {
            Language mylan = new Language();
            mylan.ClassType = classType;
            mylan.English = text;
            insert(mylan);
            getHList().Add(classType + text, mylan);
            return text;
        }
         
        //ItemInserted=true;
        if (LanguageForeigns.getHList().Contains(classType + text))
        {
            if (StringSupport.equals(((LanguageForeign)LanguageForeigns.getHList()[classType + text]).Translation, ""))
            {
                return text;
            }
             
            return ((LanguageForeign)LanguageForeigns.getHList()[classType + text]).Translation;
        }
        else
        {
            return text;
        } 
    }

    //if translation is empty
    //return the English version
    /**
    * 
    */
    public static long insert(Language language) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), language);
        }
         
        return Crud.LanguageCrud.Insert(language);
    }

    /*
    		///<summary>not used to update the english version of text.  Create new instead.</summary>
    		public static void UpdateCur(){
    			string command="UPDATE language SET "
    				+"EnglishComments = '" +POut.PString(Cur.EnglishComments)+"'"
    				+",IsObsolete = '"     +POut.PBool  (Cur.IsObsolete)+"'"
    				+" WHERE ClassType = BINARY '"+POut.PString(Cur.ClassType)+"'"
    				+" AND English = BINARY '"     +POut.PString(Cur.English)+"'";
    			NonQ(false);
    		}*/
    /**
    * No need to refresh after this.
    */
    public static void deleteItems(String classType, List<String> englishList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), classType, englishList);
            return ;
        }
         
        String command = "DELETE FROM language WHERE ClassType='" + POut.string(classType) + "' AND (";
        for (int i = 0;i < englishList.Count;i++)
        {
            if (i > 0)
            {
                command += "OR ";
            }
             
            command += "English='" + POut.String(englishList[i]) + "' ";
            if (getHList().ContainsKey(classType + englishList[i]))
            {
                getHList().Remove(classType + englishList[i]);
            }
             
        }
        command += ")";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static String[] getListCat() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<String[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT Distinct ClassType FROM language ORDER BY ClassType ";
        DataTable table = Db.getTable(command);
        String[] ListCat = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ListCat[i] = PIn.String(table.Rows[i][0].ToString());
        }
        return ListCat;
    }

    /**
    * Only used in translation tool to get list for one category
    */
    public static Language[] getListForCat(String classType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Language[]>GetObject(MethodBase.GetCurrentMethod(), classType);
        }
         
        String command = "SELECT * FROM language " + "WHERE ClassType = BINARY '" + POut.string(classType) + "' ORDER BY English";
        return Crud.LanguageCrud.SelectMany(command).ToArray();
    }

    /**
    * This had to be added because SilverLight does not allow globally setting the current culture format.
    */
    public static String getShortDateTimeFormat() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
        {
            return "MM/dd/yyyy";
        }
        else
        {
            return CultureInfo.CurrentCulture.DateTimeFormat.ShortDatePattern;
        } 
    }

    //DateTimeFormatInfo formatinfo=(DateTimeFormatInfo)CultureInfo.CurrentCulture.DateTimeFormat.Clone();
    //formatinfo.ShortDatePattern="MM/dd/yyyy";
    //return formatinfo;
    /**
    * Gets a short time format for displaying in appt and schedule along the sides. Pass in a clone of the current culture; it will get altered. Returns a string format.
    */
    public static String getShortTimeFormat(CultureInfo ci) throws Exception {
        //No need to check RemotingRole; no call to db.
        String hFormat = "";
        ci.DateTimeFormat.AMDesignator = ci.DateTimeFormat.AMDesignator.ToLower();
        ci.DateTimeFormat.PMDesignator = ci.DateTimeFormat.PMDesignator.ToLower();
        String shortTimePattern = ci.DateTimeFormat.ShortTimePattern;
        if (shortTimePattern.IndexOf("hh") != -1)
        {
            //if hour is 01-12
            hFormat += "hh";
        }
        else if (shortTimePattern.IndexOf("h") != -1)
        {
            //or if hour is 1-12
            hFormat += "h";
        }
        else if (shortTimePattern.IndexOf("HH") != -1)
        {
            //or if hour is 00-23
            hFormat += "HH";
        }
        else
        {
            //hour is 0-23
            hFormat += "H";
        }   
        if (shortTimePattern.IndexOf("t") != -1)
        {
            //if there is an am/pm designator
            hFormat += "tt";
        }
        else
        {
            //if no am/pm designator, then use :00
            hFormat += ":00";
        } 
        return hFormat;
    }

    //time separator will actually change according to region
    /**
    * This is one rare situation where queries can be passed.  But it will always fail for client web and server web.
    */
    public static void loadTranslationsFromTextFile(String content) throws Exception {
        if (RemotingClient.RemotingRole != RemotingRole.ClientDirect)
        {
            throw new ApplicationException("Not allowed.");
        }
         
        Db.nonQ(content);
    }

}


