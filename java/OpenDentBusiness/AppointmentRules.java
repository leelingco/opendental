//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AppointmentRule;
import OpenDentBusiness.AppointmentRuleC;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class AppointmentRules   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM appointmentrule";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "AppointmentRule";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        AppointmentRuleC.setList(Crud.AppointmentRuleCrud.TableToList(table).ToArray());
    }

    /**
    * 
    */
    public static long insert(AppointmentRule appointmentRule) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            appointmentRule.AppointmentRuleNum = Meth.GetLong(MethodBase.GetCurrentMethod(), appointmentRule);
            return appointmentRule.AppointmentRuleNum;
        }
         
        return Crud.AppointmentRuleCrud.Insert(appointmentRule);
    }

    /**
    * 
    */
    public static void update(AppointmentRule appointmentRule) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), appointmentRule);
            return ;
        }
         
        Crud.AppointmentRuleCrud.Update(appointmentRule);
    }

    /**
    * 
    */
    public static void delete(AppointmentRule rule) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), rule);
            return ;
        }
         
        String command = "DELETE FROM appointmentrule" + " WHERE AppointmentRuleNum = " + POut.long(rule.AppointmentRuleNum);
        Db.nonQ(command);
    }

    /**
    * Whenever an appointment is scheduled, the procedures which would be double booked are calculated.  In this method, those procedures are checked to see if the double booking should be blocked.  If double booking is indeed blocked, then a separate function will tell the user which category.
    */
    public static boolean isBlocked(ArrayList codes) throws Exception {
        for (int j = 0;j < codes.Count;j++)
        {
            for (int i = 0;i < AppointmentRuleC.getList().Length;i++)
            {
                //No need to check RemotingRole; no call to db.
                if (!AppointmentRuleC.getList()[i].IsEnabled)
                {
                    continue;
                }
                 
                if (String.Compare((String)codes[j], AppointmentRuleC.getList()[i].CodeStart) < 0)
                {
                    continue;
                }
                 
                if (String.Compare((String)codes[j], AppointmentRuleC.getList()[i].CodeEnd) > 0)
                {
                    continue;
                }
                 
                return true;
            }
        }
        return false;
    }

    /**
    * Whenever an appointment is blocked from being double booked, this method will tell the user which category.
    */
    public static String getBlockedDescription(ArrayList codes) throws Exception {
        for (int j = 0;j < codes.Count;j++)
        {
            for (int i = 0;i < AppointmentRuleC.getList().Length;i++)
            {
                //No need to check RemotingRole; no call to db.
                if (!AppointmentRuleC.getList()[i].IsEnabled)
                {
                    continue;
                }
                 
                if (String.Compare((String)codes[j], AppointmentRuleC.getList()[i].CodeStart) < 0)
                {
                    continue;
                }
                 
                if (String.Compare((String)codes[j], AppointmentRuleC.getList()[i].CodeEnd) > 0)
                {
                    continue;
                }
                 
                return AppointmentRuleC.getList()[i].RuleDesc;
            }
        }
        return "";
    }

}


