//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.CovCat;
import OpenDentBusiness.CovCatC;
import OpenDentBusiness.CovSpan;
import OpenDentBusiness.CovSpans;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.Meth;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class CovCats   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM covcat ORDER BY covorder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "CovCat";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        CovCatC.setListt(Crud.CovCatCrud.TableToList(table));
        CovCatC.setListShort(new List<CovCat>());
        for (int i = 0;i < CovCatC.getListt().Count;i++)
        {
            if (!CovCatC.getListt()[i].IsHidden)
            {
                CovCatC.getListShort().Add(CovCatC.getListt()[i]);
            }
             
        }
    }

    /**
    * 
    */
    public static void update(CovCat covcat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), covcat);
            return ;
        }
         
        Crud.CovCatCrud.Update(covcat);
    }

    /**
    * 
    */
    public static long insert(CovCat covcat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            covcat.CovCatNum = Meth.GetLong(MethodBase.GetCurrentMethod(), covcat);
            return covcat.CovCatNum;
        }
         
        return Crud.CovCatCrud.Insert(covcat);
    }

    /**
    * 
    */
    public static void moveUp(CovCat covcat) throws Exception {
        //No need to check RemotingRole; no call to db.
        refreshCache();
        int oldOrder = CovCatC.getOrderLong(covcat.CovCatNum);
        if (oldOrder == 0 || oldOrder == -1)
        {
            return ;
        }
         
        SetOrder(CovCatC.getListt()[oldOrder], (byte)(oldOrder - 1));
        SetOrder(CovCatC.getListt()[oldOrder - 1], (byte)oldOrder);
    }

    /**
    * 
    */
    public static void moveDown(CovCat covcat) throws Exception {
        //No need to check RemotingRole; no call to db.
        refreshCache();
        int oldOrder = CovCatC.getOrderLong(covcat.CovCatNum);
        if (oldOrder == CovCatC.getListt().Count - 1 || oldOrder == -1)
        {
            return ;
        }
         
        SetOrder(CovCatC.getListt()[oldOrder], (byte)(oldOrder + 1));
        SetOrder(CovCatC.getListt()[oldOrder + 1], (byte)oldOrder);
    }

    /**
    * 
    */
    private static void setOrder(CovCat covcat, byte newOrder) throws Exception {
        //No need to check RemotingRole; no call to db.
        covcat.CovOrder = newOrder;
        update(covcat);
    }

    /**
    * 
    */
    public static CovCat getCovCat(long covCatNum) throws Exception {
        for (int i = 0;i < CovCatC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (covCatNum == CovCatC.getListt()[i].CovCatNum)
            {
                return CovCatC.getListt()[i].Copy();
            }
             
        }
        return null;
    }

    //won't happen
    /**
    * 
    */
    public static double getDefaultPercent(long myCovCatNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < CovCatC.getListt().Count;i++)
        {
            if (myCovCatNum == CovCatC.getListt()[i].CovCatNum)
            {
                retVal = (double)CovCatC.getListt()[i].DefaultPercent;
            }
             
        }
        return retVal;
    }

    /**
    * 
    */
    public static String getDesc(long covCatNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retStr = "";
        for (int i = 0;i < CovCatC.getListt().Count;i++)
        {
            if (covCatNum == CovCatC.getListt()[i].CovCatNum)
            {
                retStr = CovCatC.getListt()[i].Description;
            }
             
        }
        return retStr;
    }

    /**
    * 
    */
    public static long getCovCatNum(int orderShort) throws Exception {
        //No need to check RemotingRole; no call to db.
        //need to check this again:
        long retVal = 0;
        for (int i = 0;i < CovCatC.getListShort().Count;i++)
        {
            if (orderShort == CovCatC.getListShort()[i].CovOrder)
            {
                retVal = CovCatC.getListShort()[i].CovCatNum;
            }
             
        }
        return retVal;
    }

    /**
    * Returns -1 if not in ListShort.
    */
    public static int getOrderShort(long CovCatNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        int retVal = -1;
        for (int i = 0;i < CovCatC.getListShort().Count;i++)
        {
            if (CovCatNum == CovCatC.getListShort()[i].CovCatNum)
            {
                retVal = i;
            }
             
        }
        return retVal;
    }

    /**
    * Gets a matching benefit category from the short list.  Returns null if not found, which should be tested for.
    */
    public static CovCat getForEbenCat(EbenefitCategory eben) throws Exception {
        for (int i = 0;i < CovCatC.getListShort().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (eben == CovCatC.getListShort()[i].EbenefitCat)
            {
                return CovCatC.getListShort()[i];
            }
             
        }
        return null;
    }

    /**
    * If none assigned, it will return None.
    */
    public static EbenefitCategory getEbenCat(long covCatNum) throws Exception {
        for (int i = 0;i < CovCatC.getListShort().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (covCatNum == CovCatC.getListShort()[i].CovCatNum)
            {
                return CovCatC.getListShort()[i].EbenefitCat;
            }
             
        }
        return EbenefitCategory.None;
    }

    public static int countForEbenCat(EbenefitCategory eben) throws Exception {
        //No need to check RemotingRole; no call to db.
        int retVal = 0;
        for (int i = 0;i < CovCatC.getListShort().Count;i++)
        {
            if (CovCatC.getListShort()[i].EbenefitCat == eben)
            {
                retVal++;
            }
             
        }
        return retVal;
    }

    public static void setOrdersToDefault() throws Exception {
        //This can only be run if the validation checks have been run first.
        //No need to check RemotingRole; no call to db.
        SetOrder(getForEbenCat(EbenefitCategory.General), 0);
        SetOrder(getForEbenCat(EbenefitCategory.Diagnostic), 1);
        SetOrder(getForEbenCat(EbenefitCategory.DiagnosticXRay), 2);
        SetOrder(getForEbenCat(EbenefitCategory.RoutinePreventive), 3);
        SetOrder(getForEbenCat(EbenefitCategory.Restorative), 4);
        SetOrder(getForEbenCat(EbenefitCategory.Endodontics), 5);
        SetOrder(getForEbenCat(EbenefitCategory.Periodontics), 6);
        SetOrder(getForEbenCat(EbenefitCategory.OralSurgery), 7);
        SetOrder(getForEbenCat(EbenefitCategory.Crowns), 8);
        SetOrder(getForEbenCat(EbenefitCategory.Prosthodontics), 9);
        SetOrder(getForEbenCat(EbenefitCategory.MaxillofacialProsth), 10);
        SetOrder(getForEbenCat(EbenefitCategory.Accident), 11);
        SetOrder(getForEbenCat(EbenefitCategory.Orthodontics), 12);
        SetOrder(getForEbenCat(EbenefitCategory.Adjunctive), 13);
        //now set the remaining categories to come after the ebens.
        byte idx = 14;
        for (int i = 0;i < CovCatC.getListShort().Count;i++)
        {
            if (CovCatC.getListShort()[i].EbenefitCat != EbenefitCategory.None)
            {
                continue;
            }
             
            SetOrder(CovCatC.getListShort()[i], idx);
            idx++;
        }
        for (int i = 0;i < CovCatC.getListt().Count;i++)
        {
            //finally, the hidden categories
            if (!CovCatC.getListt()[i].IsHidden)
            {
                continue;
            }
             
            SetOrder(CovCatC.getListt()[i], idx);
            idx++;
        }
    }

    public static void setSpansToDefault() throws Exception {
        //This can only be run if the validation checks have been run first.
        //No need to check RemotingRole; no call to db.
        long covCatNum = new long();
        CovSpan span;
        covCatNum = getForEbenCat(EbenefitCategory.General).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D0000";
        span.ToCode = "D7999";
        CovSpans.insert(span);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D9000";
        span.ToCode = "D9999";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.Diagnostic).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D0000";
        span.ToCode = "D0999";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.DiagnosticXRay).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D0200";
        span.ToCode = "D0399";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.RoutinePreventive).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D1000";
        span.ToCode = "D1999";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.Restorative).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D2000";
        span.ToCode = "D2699";
        CovSpans.insert(span);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D2800";
        span.ToCode = "D2999";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.Endodontics).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D3000";
        span.ToCode = "D3999";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.Periodontics).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D4000";
        span.ToCode = "D4999";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.OralSurgery).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D7000";
        span.ToCode = "D7999";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.Crowns).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D2700";
        span.ToCode = "D2799";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.Prosthodontics).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D5000";
        span.ToCode = "D5899";
        CovSpans.insert(span);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D6200";
        span.ToCode = "D6899";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.MaxillofacialProsth).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D5900";
        span.ToCode = "D5999";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.Accident).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        covCatNum = getForEbenCat(EbenefitCategory.Orthodontics).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D8000";
        span.ToCode = "D8999";
        CovSpans.insert(span);
        covCatNum = getForEbenCat(EbenefitCategory.Adjunctive).CovCatNum;
        CovSpans.deleteForCat(covCatNum);
        span = new CovSpan();
        span.CovCatNum = covCatNum;
        span.FromCode = "D9000";
        span.ToCode = "D9999";
        CovSpans.insert(span);
    }

}


