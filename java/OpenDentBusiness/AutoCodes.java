//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.AutoCode;
import OpenDentBusiness.AutoCodeC;
import OpenDentBusiness.AutoCodeConds;
import OpenDentBusiness.AutoCodeItem;
import OpenDentBusiness.AutoCodeItems;
import OpenDentBusiness.AutoCondition;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcButtonItems;
import OpenDentBusiness.ProcButtons;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class AutoCodes   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from autocode";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "AutoCode";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        AutoCodeC.setHList(new Hashtable());
        AutoCodeC.setList(Crud.AutoCodeCrud.TableToList(table).ToArray());
        ArrayList ALshort = new ArrayList();
        for (int i = 0;i < AutoCodeC.getList().Length;i++)
        {
            //int of indexes of short list
            AutoCodeC.getHList().Add(AutoCodeC.getList()[i].AutoCodeNum, AutoCodeC.getList()[i]);
            if (!AutoCodeC.getList()[i].IsHidden)
            {
                ALshort.Add(i);
            }
             
        }
        AutoCodeC.setListShort(new AutoCode[ALshort.Count]);
        for (int i = 0;i < ALshort.Count;i++)
        {
            AutoCodeC.getListShort()[i] = AutoCodeC.getList()[(int)ALshort[i]];
        }
    }

    /**
    * 
    */
    public static void clearCache() throws Exception {
        AutoCodeC.setHList(null);
        AutoCodeC.setList(null);
        AutoCodeC.setListShort(null);
    }

    /**
    * 
    */
    public static long insert(AutoCode Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.AutoCodeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.AutoCodeNum;
        }
         
        return Crud.AutoCodeCrud.Insert(Cur);
    }

    /**
    * 
    */
    public static void update(AutoCode Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.AutoCodeCrud.Update(Cur);
    }

    /**
    * Surround with try/catch.  Currently only called from FormAutoCode and FormAutoCodeEdit.
    */
    public static void delete(AutoCode autoCodeCur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), autoCodeCur);
            return ;
        }
         
        //look for dependencies in ProcButton table.
        String strInUse = "";
        for (int i = 0;i < ProcButtons.getList().Length;i++)
        {
            for (int j = 0;j < ProcButtonItems.getList().Length;j++)
            {
                if (ProcButtonItems.getList()[j].ProcButtonNum == ProcButtons.getList()[i].ProcButtonNum && ProcButtonItems.getList()[j].AutoCodeNum == autoCodeCur.AutoCodeNum)
                {
                    if (!StringSupport.equals(strInUse, ""))
                    {
                        strInUse += "; ";
                    }
                     
                    //Add the procedure button description to the list for display.
                    strInUse += ProcButtons.getList()[i].Description;
                    break;
                }
                 
            }
        }
        //Button already added to the description, check the other buttons in the list.
        if (!StringSupport.equals(strInUse, ""))
        {
            throw new ApplicationException(Lans.g("AutoCodes","Not allowed to delete autocode because it is in use.  Procedure buttons using this autocode include ") + strInUse);
        }
         
        List<AutoCodeItem> listAutoCodeItems = AutoCodeItems.getListForCode(autoCodeCur.AutoCodeNum);
        for (int i = 0;i < listAutoCodeItems.Count;i++)
        {
            AutoCodeItem AutoCodeItemCur = listAutoCodeItems[i];
            AutoCodeConds.deleteForItemNum(AutoCodeItemCur.AutoCodeItemNum);
            AutoCodeItems.delete(AutoCodeItemCur);
        }
        Crud.AutoCodeCrud.Delete(autoCodeCur.AutoCodeNum);
    }

    /**
    * Used in ProcButtons.SetToDefault.  Returns 0 if the given autocode does not exist.
    */
    public static long getNumFromDescript(String descript) throws Exception {
        for (int i = 0;i < AutoCodeC.getListShort().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(AutoCodeC.getListShort()[i].Description, descript))
            {
                return AutoCodeC.getListShort()[i].AutoCodeNum;
            }
             
        }
        return 0;
    }

    //------
    /**
    * Deletes all current autocodes and then adds the default autocodes.  Procedure codes must have already been entered or they cannot be added as an autocode.
    */
    public static void setToDefault() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "DELETE FROM autocode";
        Db.nonQ(command);
        command = "DELETE FROM autocodecond";
        Db.nonQ(command);
        command = "DELETE FROM autocodeitem";
        Db.nonQ(command);
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            setToDefaultCanada();
            return ;
        }
         
        long autoCodeNum = new long();
        long autoCodeItemNum = new long();
        //Amalgam-------------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('Amalgam',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //1Surf
        if (ProcedureCodes.isValidCode("D2140"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2140") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.One_Surf).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //2Surf
        if (ProcedureCodes.isValidCode("D2150"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2150") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Two_Surf).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //3Surf
        if (ProcedureCodes.isValidCode("D2160"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2160") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Three_Surf).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //4Surf
        if (ProcedureCodes.isValidCode("D2161"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2161") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Four_Surf).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //5Surf
        if (ProcedureCodes.isValidCode("D2161"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2161") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Five_Surf).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Composite-------------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('Composite',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //1SurfAnt
        if (ProcedureCodes.isValidCode("D2330"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2330") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.One_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //2SurfAnt
        if (ProcedureCodes.isValidCode("D2331"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2331") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Two_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //3SurfAnt
        if (ProcedureCodes.isValidCode("D2332"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2332") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Three_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //4SurfAnt
        if (ProcedureCodes.isValidCode("D2335"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2335") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Four_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //5SurfAnt
        if (ProcedureCodes.isValidCode("D2335"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2335") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Five_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Posterior Composite----------------------------------------------------------------------------------------------
        //1SurfPost
        if (ProcedureCodes.isValidCode("D2391"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2391") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.One_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Posterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //2SurfPost
        if (ProcedureCodes.isValidCode("D2392"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2392") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Two_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Posterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //3SurfPost
        if (ProcedureCodes.isValidCode("D2393"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2393") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Three_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Posterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //4SurfPost
        if (ProcedureCodes.isValidCode("D2394"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2394") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Four_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Posterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //5SurfPost
        if (ProcedureCodes.isValidCode("D2394"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2394") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Five_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Posterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Root Canal-------------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('Root Canal',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //Ant
        if (ProcedureCodes.isValidCode("D3310"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D3310") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Premolar
        if (ProcedureCodes.isValidCode("D3320"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D3320") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Molar
        if (ProcedureCodes.isValidCode("D3330"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D3330") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Bridge-------------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('Bridge',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //Pontic
        if (ProcedureCodes.isValidCode("D6242"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D6242") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Pontic).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Retainer
        if (ProcedureCodes.isValidCode("D6752"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D6752") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Retainer).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Denture-------------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('Denture',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //Max
        if (ProcedureCodes.isValidCode("D5110"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D5110") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Maxillary).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Mand
        if (ProcedureCodes.isValidCode("D5120"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D5120") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Mandibular).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //BU/P&C-------------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('BU/P&C',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //BU
        if (ProcedureCodes.isValidCode("D2950"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2950") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Posterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //P&C
        if (ProcedureCodes.isValidCode("D2954"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D2954") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Root Canal Retreat--------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('RCT Retreat',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //Ant
        if (ProcedureCodes.isValidCode("D3346"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D3346") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Premolar
        if (ProcedureCodes.isValidCode("D3347"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D3347") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Molar
        if (ProcedureCodes.isValidCode("D3348"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("D3348") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
    }

    /**
    * Deletes all current autocodes and then adds the default autocodes.  Procedure codes must have already been entered or they cannot be added as an autocode.
    */
    public static void setToDefaultCanada() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = new String();
        long autoCodeNum = new long();
        long autoCodeItemNum = new long();
        //Amalgam-------------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('Amalgam',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //1SurfPrimary
        if (ProcedureCodes.isValidCode("21111"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21111") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.One_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
        }
         
        //2SurfPrimary
        if (ProcedureCodes.isValidCode("21112"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21112") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Two_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //3SurfPrimary
        if (ProcedureCodes.isValidCode("21113"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21113") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Three_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //4SurfPrimary
        if (ProcedureCodes.isValidCode("21114"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21114") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Four_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //5SurfPrimary
        if (ProcedureCodes.isValidCode("21115"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21115") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Five_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //1SurfPermanentPremolar
        if (ProcedureCodes.isValidCode("21211"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21211") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.One_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //2SurfPermanentPremolar
        if (ProcedureCodes.isValidCode("21212"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21212") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Two_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //3SurfPermanentPremolar
        if (ProcedureCodes.isValidCode("21213"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21213") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Three_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //4SurfPermanentPremolar
        if (ProcedureCodes.isValidCode("21214"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21214") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Four_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //5SurfPermanentPremolar
        if (ProcedureCodes.isValidCode("21215"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21215") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Five_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //1SurfPermanentMolar
        if (ProcedureCodes.isValidCode("21221"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21221") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.One_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //2SurfPermanentMolar
        if (ProcedureCodes.isValidCode("21222"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21222") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Two_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //3SurfPermanentMolar
        if (ProcedureCodes.isValidCode("21223"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21223") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Three_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //4SurfPermanentMolar
        if (ProcedureCodes.isValidCode("21224"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21224") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Four_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //5SurfPermanentMolar
        if (ProcedureCodes.isValidCode("21225"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("21225") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Five_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Composite-------------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('Composite',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //1SurfPermanentAnterior
        if (ProcedureCodes.isValidCode("23111"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23111") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.One_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //2SurfPermanentAnterior
        if (ProcedureCodes.isValidCode("23112"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23112") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Two_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //3SurfPermanentAnterior
        if (ProcedureCodes.isValidCode("23113"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23113") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Three_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //4SurfPermanentAnterior
        if (ProcedureCodes.isValidCode("23114"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23114") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Four_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //5SurfPermanentAnterior
        if (ProcedureCodes.isValidCode("23115"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23115") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Five_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //1SurfPermanentPremolar
        if (ProcedureCodes.isValidCode("23311"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23311") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.One_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //2SurfPermanentPremolar
        if (ProcedureCodes.isValidCode("23312"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23312") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Two_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //3SurfPermanentPremolar
        if (ProcedureCodes.isValidCode("23313"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23313") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Three_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //4SurfPermanentPremolar
        if (ProcedureCodes.isValidCode("23314"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23314") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Four_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //5SurfPermanentPremolar
        if (ProcedureCodes.isValidCode("23315"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23315") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Five_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //1SurfPermanentMolar
        if (ProcedureCodes.isValidCode("23321"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23321") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.One_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //2SurfPermanentMolar
        if (ProcedureCodes.isValidCode("23322"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23322") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Two_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //3SurfPermanentMolar
        if (ProcedureCodes.isValidCode("23323"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23323") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Three_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //4SurfPermanentMolar
        if (ProcedureCodes.isValidCode("23324"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23324") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Four_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //5SurfPermanentMolar
        if (ProcedureCodes.isValidCode("23325"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23325") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Five_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Permanent).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //1SurfPrimaryAnterior
        if (ProcedureCodes.isValidCode("23411"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23411") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.One_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //2SurfPrimaryAnterior
        if (ProcedureCodes.isValidCode("23412"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23412") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Two_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //3SurfPrimaryAnterior
        if (ProcedureCodes.isValidCode("23413"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23413") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Three_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //4SurfPrimaryAnterior
        if (ProcedureCodes.isValidCode("23414"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23414") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Four_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //5SurfPrimaryAnterior
        if (ProcedureCodes.isValidCode("23415"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23415") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Five_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //1SurfPrimaryPosterior
        if (ProcedureCodes.isValidCode("23511"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23511") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.One_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Posterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //2SurfPrimaryPosterior
        if (ProcedureCodes.isValidCode("23512"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23512") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Two_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Posterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //3SurfPrimaryPosterior
        if (ProcedureCodes.isValidCode("23513"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23513") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Three_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Posterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //4SurfPrimaryPosterior
        if (ProcedureCodes.isValidCode("23514"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23514") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Four_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Posterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //5SurfPrimaryPosterior
        if (ProcedureCodes.isValidCode("23515"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("23515") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Five_Surf).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Primary).ordinal()) + ")";
            Db.nonQ(command);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Posterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Root Canal-------------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('Root Canal',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //Ant
        if (ProcedureCodes.isValidCode("33111"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("33111") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Anterior).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Premolar
        if (ProcedureCodes.isValidCode("33121"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("33121") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Premolar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Molar
        if (ProcedureCodes.isValidCode("33131"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("33131") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Molar).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Denture-------------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('Denture',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //Max
        if (ProcedureCodes.isValidCode("51101"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("51101") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Maxillary).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Mand
        if (ProcedureCodes.isValidCode("51302"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("51302") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Mandibular).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Bridge-------------------------------------------------------------------------------------------------------
        command = "INSERT INTO autocode (Description,IsHidden,LessIntrusive) VALUES ('Bridge',0,0)";
        autoCodeNum = Db.nonQ(command,true);
        //Pontic
        if (ProcedureCodes.isValidCode("62501"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("62501") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Pontic).ordinal()) + ")";
            Db.nonQ(command);
        }
         
        //Retainer
        if (ProcedureCodes.isValidCode("67211"))
        {
            command = "INSERT INTO autocodeitem (AutoCodeNum,CodeNum) VALUES (" + POut.long(autoCodeNum) + "," + ProcedureCodes.getCodeNum("67211") + ")";
            autoCodeItemNum = Db.nonQ(command,true);
            command = "INSERT INTO autocodecond (AutoCodeItemNum,Cond) VALUES (" + POut.long(autoCodeItemNum) + "," + POut.Long(((Enum)AutoCondition.Retainer).ordinal()) + ")";
            Db.nonQ(command);
        }
         
    }

}


