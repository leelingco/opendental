//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* There is no cache for clinics.  We assume they will almost never change.
*/
public class Clinics   
{
    /**
    * Clinics cannot be hidden or deleted, so there is only one list.
    */
    private static Clinic[] list = new Clinic[]();
    //No need to check RemotingRole; no call to db.
    public static Clinic[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(Clinic[] value) throws Exception {
        list = value;
    }

    /**
    * Refresh all clinics.  Not actually part of official cache.
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM clinic";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "clinic";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.ClinicCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    public static long insert(Clinic clinic) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            clinic.ClinicNum = Meth.GetLong(MethodBase.GetCurrentMethod(), clinic);
            return clinic.ClinicNum;
        }
         
        return Crud.ClinicCrud.Insert(clinic);
    }

    /**
    * 
    */
    public static void update(Clinic clinic) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), clinic);
            return ;
        }
         
        Crud.ClinicCrud.Update(clinic);
    }

    /**
    * Checks dependencies first.  Throws exception if can't delete.
    */
    public static void delete(Clinic clinic) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), clinic);
            return ;
        }
         
        //Check FK dependencies.
        String command = "SELECT LName,FName FROM patient WHERE ClinicNum =" + POut.long(clinic.ClinicNum);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String pats = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                pats += "\r";
                pats += table.Rows[i][0].ToString() + ", " + table.Rows[i][1].ToString();
            }
            throw new Exception(Lans.g("Clinics","Cannot delete clinic because it is in use by the following patients:") + pats);
        }
         
        command = "SELECT patient.LName,patient.FName FROM patient,payment " + "WHERE payment.ClinicNum =" + POut.long(clinic.ClinicNum) + " AND patient.PatNum=payment.PatNum";
        table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String pats = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                pats += "\r";
                pats += table.Rows[i][0].ToString() + ", " + table.Rows[i][1].ToString();
            }
            throw new Exception(Lans.g("Clinics","Cannot delete clinic because the following patients have payments using it:") + pats);
        }
         
        command = "SELECT patient.LName,patient.FName FROM patient,claimproc,claimpayment " + "WHERE claimpayment.ClinicNum =" + POut.long(clinic.ClinicNum) + " AND patient.PatNum=claimproc.PatNum" + " AND claimproc.ClaimPaymentNum=claimpayment.ClaimPaymentNum " + "GROUP BY patient.LName,patient.FName,claimpayment.ClaimPaymentNum";
        table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String pats = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                pats += "\r";
                pats += table.Rows[i][0].ToString() + ", " + table.Rows[i][1].ToString();
            }
            throw new Exception(Lans.g("Clinics","Cannot delete clinic because the following patients have claim payments using it:") + pats);
        }
         
        command = "SELECT patient.LName,patient.FName FROM patient,appointment " + "WHERE appointment.ClinicNum =" + POut.long(clinic.ClinicNum) + " AND patient.PatNum=appointment.PatNum";
        table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String pats = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                pats += "\r";
                pats += table.Rows[i][0].ToString() + ", " + table.Rows[i][1].ToString();
            }
            throw new Exception(Lans.g("Clinics","Cannot delete clinic because the following patients have appointments using it:") + pats);
        }
         
        //reassign procedure.ClinicNum=0 if the procs are status D.
        command = "UPDATE procedurelog SET ClinicNum=0 WHERE ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.D).ordinal());
        Db.nonQ(command);
        command = "SELECT patient.LName,patient.FName FROM patient,procedurelog " + "WHERE procedurelog.ClinicNum =" + POut.long(clinic.ClinicNum) + " AND patient.PatNum=procedurelog.PatNum";
        table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String pats = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                pats += "\r";
                pats += table.Rows[i][0].ToString() + ", " + table.Rows[i][1].ToString();
            }
            throw new Exception(Lans.g("Clinics","Cannot delete clinic because the following patients have procedures using it:") + pats);
        }
         
        command = "SELECT OpName FROM operatory " + "WHERE ClinicNum =" + POut.long(clinic.ClinicNum);
        table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String ops = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                ops += "\r";
                ops += table.Rows[i][0].ToString();
            }
            throw new Exception(Lans.g("Clinics","Cannot delete clinic because the following operatories are using it:") + ops);
        }
         
        command = "SELECT UserName FROM userod " + "WHERE ClinicNum =" + POut.long(clinic.ClinicNum);
        table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            String userNames = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                userNames += "\r";
                userNames += table.Rows[i][0].ToString();
            }
            throw new Exception(Lans.g("Clinics","Cannot delete clinic because the following Open Dental users are using it:") + userNames);
        }
         
        //End checking for dependencies.
        //Clinic is not being used, OK to delete.
        command = "DELETE FROM clinic" + " WHERE ClinicNum = " + POut.long(clinic.ClinicNum);
        Db.nonQ(command);
    }

    /**
    * Returns null if clinic not found.  Pulls from cache.
    */
    public static Clinic getClinic(long clinicNum) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getList()[i].ClinicNum == clinicNum)
            {
                return getList()[i].Copy();
            }
             
        }
        return null;
    }

    /**
    * Returns an empty string for invalid clinicNums.
    */
    public static String getDesc(long clinicNum) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getList()[i].ClinicNum == clinicNum)
            {
                return getList()[i].Description;
            }
             
        }
        return "";
    }

    /**
    * Returns practice default for invalid clinicNums.
    */
    public static PlaceOfService getPlaceService(long clinicNum) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getList()[i].ClinicNum == clinicNum)
            {
                return getList()[i].DefaultPlaceService;
            }
             
        }
        return (PlaceOfService)PrefC.getLong(PrefName.DefaultProcedurePlaceService);
    }

    //return PlaceOfService.Office;
    /**
    * Clinics cannot be hidden, so if clinicNum=0, this will return -1.
    */
    public static int getIndex(long clinicNum) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getList()[i].ClinicNum == clinicNum)
            {
                return i;
            }
             
        }
        return -1;
    }

}


