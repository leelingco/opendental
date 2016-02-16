//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.CovSpan;
import OpenDentBusiness.CovSpanC;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class CovSpans   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM covspan" + " ORDER BY FromCode";
        //+" ORDER BY CovCatNum";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "CovSpan";
        fillCache(table);
        return table;
    }

    //private static void FillCache(DataTable table){//js 3/12/13  Not sure why it was this way
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        CovSpanC.setList(Crud.CovSpanCrud.TableToList(table).ToArray());
    }

    /**
    * 
    */
    public static void update(CovSpan span) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), span);
            return ;
        }
         
        validate(span);
        Crud.CovSpanCrud.Update(span);
        return ;
    }

    /**
    * 
    */
    public static long insert(CovSpan span) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            span.CovSpanNum = Meth.GetLong(MethodBase.GetCurrentMethod(), span);
            return span.CovSpanNum;
        }
         
        validate(span);
        return Crud.CovSpanCrud.Insert(span);
    }

    /**
    * 
    */
    private static void validate(CovSpan span) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(span.FromCode, "") || StringSupport.equals(span.ToCode, ""))
        {
            throw new ApplicationException(Lans.g("FormInsSpanEdit","Codes not allowed to be blank."));
        }
         
        if (String.Compare(span.ToCode, span.FromCode) < 0)
        {
            throw new ApplicationException(Lans.g("FormInsSpanEdit","From Code must be less than To Code.  Remember that the comparison is alphabetical, not numeric.  For instance, 100 would come before 2, but after 02."));
        }
         
    }

    /**
    * 
    */
    public static void delete(CovSpan span) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), span);
            return ;
        }
         
        String command = "DELETE FROM covspan" + " WHERE CovSpanNum = '" + POut.long(span.CovSpanNum) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void deleteForCat(long covCatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), covCatNum);
            return ;
        }
         
        String command = "DELETE FROM covspan WHERE CovCatNum = " + POut.long(covCatNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long getCat(String myCode) throws Exception {
        //No need to check RemotingRole; no call to db.
        long retVal = 0;
        for (int i = 0;i < CovSpanC.getList().Length;i++)
        {
            if (String.Compare(myCode, CovSpanC.getList()[i].FromCode) >= 0 && String.Compare(myCode, CovSpanC.getList()[i].ToCode) <= 0)
            {
                retVal = CovSpanC.getList()[i].CovCatNum;
            }
             
        }
        return retVal;
    }

    /**
    * 
    */
    public static CovSpan[] getForCat(long catNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList AL = new ArrayList();
        for (int i = 0;i < CovSpanC.getList().Length;i++)
        {
            if (CovSpanC.getList()[i].CovCatNum == catNum)
            {
                AL.Add(CovSpanC.getList()[i].Copy());
            }
             
        }
        CovSpan[] retVal = new CovSpan[AL.Count];
        AL.CopyTo(retVal);
        return retVal;
    }

    /**
    * If the supplied code falls within any of the supplied spans, then returns true.
    */
    public static boolean isCodeInSpans(String strProcCode, CovSpan[] covSpanArray) throws Exception {
        for (int i = 0;i < covSpanArray.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (String.Compare(strProcCode, covSpanArray[i].FromCode) >= 0 && String.Compare(strProcCode, covSpanArray[i].ToCode) <= 0)
            {
                return true;
            }
             
        }
        return false;
    }

}


