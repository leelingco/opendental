//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.LetterMerge;
import OpenDentBusiness.LetterMergeFields;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class LetterMerges   
{
    /**
    * List of all lettermerges.
    */
    private static LetterMerge[] list = new LetterMerge[]();
    //No need to check RemotingRole; no call to db.
    public static LetterMerge[] getListt() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setListt(LetterMerge[] value) throws Exception {
        list = value;
    }

    private static Word.Application wordApp = new Word.Application();
    /**
    * This is a static reference to a word application.  That way, we can reuse it instead of having to reopen Word each time.
    */
    //No need to check RemotingRole; no call to db.
    public static Word.Application getWordApp() throws Exception {
        if (wordApp == null)
        {
            wordApp = new Word.Application();
            wordApp.Visible = true;
        }
         
        try
        {
            wordApp.Activate();
        }
        catch (Exception __dummyCatchVar0)
        {
            wordApp = new Word.Application();
            wordApp.Visible = true;
            wordApp.Activate();
        }

        return wordApp;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM lettermerge ORDER BY Description";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "LetterMerge";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        setListt(Crud.LetterMergeCrud.TableToList(table).ToArray());
        for (int i = 0;i < getListt().Length;i++)
        {
            getListt()[i].Fields = LetterMergeFields.GetForLetter(getListt()[i].LetterMergeNum);
        }
    }

    /**
    * Inserts this lettermerge into database.
    */
    public static long insert(LetterMerge merge) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            merge.LetterMergeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), merge);
            return merge.LetterMergeNum;
        }
         
        return Crud.LetterMergeCrud.Insert(merge);
    }

    /**
    * 
    */
    public static void update(LetterMerge merge) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), merge);
            return ;
        }
         
        Crud.LetterMergeCrud.Update(merge);
    }

    /**
    * 
    */
    public static void delete(LetterMerge merge) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), merge);
            return ;
        }
         
        String command = "DELETE FROM lettermerge " + "WHERE LetterMergeNum = " + POut.long(merge.LetterMergeNum);
        Db.nonQ(command);
    }

    /**
    * Supply the index of the cat within DefC.Short.
    */
    public static List<LetterMerge> getListForCat(int catIndex) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<LetterMerge> retVal = new List<LetterMerge>();
        for (int i = 0;i < getListt().Length;i++)
        {
            if (getListt()[i].Category == DefC.getShort()[((Enum)DefCat.LetterMergeCats).ordinal()][catIndex].DefNum)
            {
                retVal.Add(getListt()[i]);
            }
             
        }
        return retVal;
    }

}


