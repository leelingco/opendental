//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.LanguageForeign;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class LanguageForeigns   
{
    /**
    * just translations for the culture currently being used.  If a translation is missing, it tries to use a translation from another culture with the same language. Key=ClassType+English. Value =LanguageForeign object.  When support for multiple simultaneous languages is added, there will still be a current culture, but then we will add a supplemental way to extract translations for alternate cultures.
    */
    private static Hashtable hList = new Hashtable();
    //No need to check RemotingRole; no call to db.
    public static Hashtable getHList() throws Exception {
        if (hList == null)
        {
            Refresh(CultureInfo.CurrentCulture.Name, CultureInfo.CurrentCulture.TwoLetterISOLanguageName);
        }
         
        return hList;
    }

    public static void setHList(Hashtable value) throws Exception {
        hList = value;
    }

    /**
    * Haven't moved this over to the cache pattern because of the parameters.  But when called, it behaves exactly like the cache pattern, refreshing on both client and server.
    */
    public static DataTable refresh(String cultureInfoName, String cultureInfoTwoLetterISOLanguageName) throws Exception {
        //Very unusual.  RemotingRole checked a little further down due to complex situation.
        //culture info won't serialize
        if (StringSupport.equals(cultureInfoName, "en-US"))
        {
            return null;
        }
         
        //since DataTable is ignored anyway if on the client, this won't crash.
        //load all translations for the current culture, using other culture of same language if no trans avail.
        String command = "SELECT * FROM languageforeign " + "WHERE Culture LIKE '" + cultureInfoTwoLetterISOLanguageName + "%' " + "ORDER BY Culture";
        DataTable table = new DataTable();
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            table = Meth.GetTable(MethodBase.GetCurrentMethod(), cultureInfoName, cultureInfoTwoLetterISOLanguageName);
        }
        else
        {
            table = Db.getTable(command);
        } 
        hList = new Hashtable();
        //LanguageForeign lanf;
        List<LanguageForeign> list = Crud.LanguageForeignCrud.TableToList(table);
        for (int i = 0;i < list.Count;i++)
        {
            if (StringSupport.equals(list[i].Culture, cultureInfoName))
            {
                //if exact culture match
                if (hList.ContainsKey(list[i].ClassType + list[i].English))
                {
                    hList.Remove(list[i].ClassType + list[i].English);
                }
                 
                //remove any existing entry
                hList.Add(list[i].ClassType + list[i].English, list[i]);
            }
            else
            {
                //or if any other culture of same language
                if (!hList.ContainsKey(list[i].ClassType + list[i].English))
                {
                    //only add if not already in HList
                    hList.Add(list[i].ClassType + list[i].English, list[i]);
                }
                 
            } 
        }
        return table;
    }

    /**
    * 
    */
    public static long insert(LanguageForeign lanf) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), lanf);
        }
         
        return Crud.LanguageForeignCrud.Insert(lanf);
    }

    /**
    * 
    */
    public static void update(LanguageForeign lanf) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), lanf);
            return ;
        }
         
        Crud.LanguageForeignCrud.Update(lanf);
    }

    /**
    * 
    */
    public static void delete(LanguageForeign lanf) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), lanf);
            return ;
        }
         
        Crud.LanguageForeignCrud.Delete(lanf.LanguageForeignNum);
    }

    /**
    * Only used during export to get a list of all translations for specified culture only.
    */
    public static LanguageForeign[] getListForCulture(CultureInfo cultureInfo) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<LanguageForeign[]>GetObject(MethodBase.GetCurrentMethod(), cultureInfo);
        }
         
        String command = "SELECT * FROM languageforeign " + "WHERE Culture='" + CultureInfo.CurrentCulture.Name + "'";
        return Crud.LanguageForeignCrud.SelectMany(command).ToArray();
    }

    /**
    * Used in FormTranslation to get all translations for all cultures for one classtype
    */
    public static LanguageForeign[] getListForType(String classType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<LanguageForeign[]>GetObject(MethodBase.GetCurrentMethod(), classType);
        }
         
        String command = "SELECT * FROM languageforeign " + "WHERE ClassType='" + POut.string(classType) + "'";
        return Crud.LanguageForeignCrud.SelectMany(command).ToArray();
    }

    /**
    * Used in FormTranslation to get a single entry for the specified culture.  The culture match must be extact.  If no translation entries, then it returns null.
    */
    public static LanguageForeign getForCulture(LanguageForeign[] listForType, String english, String cultureName) throws Exception {
        for (int i = 0;i < listForType.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (!StringSupport.equals(english, listForType[i].English))
            {
                continue;
            }
             
            if (!StringSupport.equals(cultureName, listForType[i].Culture))
            {
                continue;
            }
             
            return listForType[i];
        }
        return null;
    }

    /**
    * Used in FormTranslation to get a single entry with the same language as the specified culture, but only for a different culture.  For instance, if culture is es-PR (Spanish-PuertoRico), then it will return any spanish translation that is NOT from Puerto Rico.  If no other translation entries, then it returns null.
    */
    public static LanguageForeign getOther(LanguageForeign[] listForType, String english, String cultureName) throws Exception {
        for (int i = 0;i < listForType.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (!StringSupport.equals(english, listForType[i].English))
            {
                continue;
            }
             
            if (StringSupport.equals(cultureName, listForType[i].Culture))
            {
                continue;
            }
             
            if (cultureName.Substring(0, 2) != listForType[i].Culture.Substring(0, 2))
            {
                continue;
            }
             
            return listForType[i];
        }
        return null;
    }

}


