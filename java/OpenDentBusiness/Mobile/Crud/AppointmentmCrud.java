//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//

package OpenDentBusiness.Mobile.Crud;

import OpenDentBusiness.Appointment;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Appointmentm;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class AppointmentmCrud   
{
    /**
    * Gets one Appointmentm object from the database using primaryKey1(CustomerNum) and primaryKey2.  Returns null if not found.
    */
    public static Appointmentm selectOne(long customerNum, long aptNum) throws Exception {
        String command = "SELECT * FROM appointmentm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND AptNum = " + POut.long(aptNum);
        List<Appointmentm> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Appointmentm object from the database using a query.
    */
    public static Appointmentm selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Appointmentm> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Appointmentm objects from the database using a query.
    */
    public static List<Appointmentm> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Appointmentm> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Appointmentm> tableToList(DataTable table) throws Exception {
        List<Appointmentm> retVal = new List<Appointmentm>();
        Appointmentm appointmentm;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            appointmentm = new Appointmentm();
            appointmentm.CustomerNum = PIn.Long(table.Rows[i]["CustomerNum"].ToString());
            appointmentm.AptNum = PIn.Long(table.Rows[i]["AptNum"].ToString());
            appointmentm.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            appointmentm.AptStatus = (ApptStatus)PIn.Int(table.Rows[i]["AptStatus"].ToString());
            appointmentm.Pattern = PIn.String(table.Rows[i]["Pattern"].ToString());
            appointmentm.Confirmed = PIn.Long(table.Rows[i]["Confirmed"].ToString());
            appointmentm.Op = PIn.Long(table.Rows[i]["Op"].ToString());
            appointmentm.Note = PIn.String(table.Rows[i]["Note"].ToString());
            appointmentm.ProvNum = PIn.Long(table.Rows[i]["ProvNum"].ToString());
            appointmentm.ProvHyg = PIn.Long(table.Rows[i]["ProvHyg"].ToString());
            appointmentm.AptDateTime = PIn.DateT(table.Rows[i]["AptDateTime"].ToString());
            appointmentm.IsNewPatient = PIn.Bool(table.Rows[i]["IsNewPatient"].ToString());
            appointmentm.ProcDescript = PIn.String(table.Rows[i]["ProcDescript"].ToString());
            appointmentm.ClinicNum = PIn.Long(table.Rows[i]["ClinicNum"].ToString());
            appointmentm.IsHygiene = PIn.Bool(table.Rows[i]["IsHygiene"].ToString());
            retVal.Add(appointmentm);
        }
        return retVal;
    }

    /**
    * Usually set useExistingPK=true.  Inserts one Appointmentm into the database.
    */
    public static long insert(Appointmentm appointmentm, boolean useExistingPK) throws Exception {
        if (!useExistingPK)
        {
            appointmentm.AptNum = ReplicationServers.getKey("appointmentm","AptNum");
        }
         
        String command = "INSERT INTO appointmentm (";
        command += "AptNum,";
        command += "CustomerNum,PatNum,AptStatus,Pattern,Confirmed,Op,Note,ProvNum,ProvHyg,AptDateTime,IsNewPatient,ProcDescript,ClinicNum,IsHygiene) VALUES(";
        command += POut.long(appointmentm.AptNum) + ",";
        command += POut.long(appointmentm.CustomerNum) + "," + POut.long(appointmentm.PatNum) + "," + POut.int((int)appointmentm.AptStatus) + "," + "'" + POut.string(appointmentm.Pattern) + "'," + POut.long(appointmentm.Confirmed) + "," + POut.long(appointmentm.Op) + "," + "'" + POut.string(appointmentm.Note) + "'," + POut.long(appointmentm.ProvNum) + "," + POut.long(appointmentm.ProvHyg) + "," + POut.dateT(appointmentm.AptDateTime) + "," + POut.bool(appointmentm.IsNewPatient) + "," + "'" + POut.string(appointmentm.ProcDescript) + "'," + POut.long(appointmentm.ClinicNum) + "," + POut.bool(appointmentm.IsHygiene) + ")";
        Db.nonQ(command);
        return appointmentm.AptNum;
    }

    //There is no autoincrement in the mobile server.
    /**
    * Updates one Appointmentm in the database.
    */
    public static void update(Appointmentm appointmentm) throws Exception {
        String command = "UPDATE appointmentm SET " + "PatNum      =  " + POut.long(appointmentm.PatNum) + ", " + "AptStatus   =  " + POut.int((int)appointmentm.AptStatus) + ", " + "Pattern     = '" + POut.string(appointmentm.Pattern) + "', " + "Confirmed   =  " + POut.long(appointmentm.Confirmed) + ", " + "Op          =  " + POut.long(appointmentm.Op) + ", " + "Note        = '" + POut.string(appointmentm.Note) + "', " + "ProvNum     =  " + POut.long(appointmentm.ProvNum) + ", " + "ProvHyg     =  " + POut.long(appointmentm.ProvHyg) + ", " + "AptDateTime =  " + POut.dateT(appointmentm.AptDateTime) + ", " + "IsNewPatient=  " + POut.bool(appointmentm.IsNewPatient) + ", " + "ProcDescript= '" + POut.string(appointmentm.ProcDescript) + "', " + "ClinicNum   =  " + POut.long(appointmentm.ClinicNum) + ", " + "IsHygiene   =  " + POut.bool(appointmentm.IsHygiene) + " " + "WHERE CustomerNum = " + POut.long(appointmentm.CustomerNum) + " AND AptNum = " + POut.long(appointmentm.AptNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Appointmentm from the database.
    */
    public static void delete(long customerNum, long aptNum) throws Exception {
        String command = "DELETE FROM appointmentm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND AptNum = " + POut.long(aptNum);
        Db.nonQ(command);
    }

    /**
    * Converts one Appointment object to its mobile equivalent.  Warning! CustomerNum will always be 0.
    */
    public static Appointmentm convertToM(Appointment appointment) throws Exception {
        Appointmentm appointmentm = new Appointmentm();
        //CustomerNum cannot be set.  Remains 0.
        appointmentm.AptNum = appointment.AptNum;
        appointmentm.PatNum = appointment.PatNum;
        appointmentm.AptStatus = appointment.AptStatus;
        appointmentm.Pattern = appointment.Pattern;
        appointmentm.Confirmed = appointment.Confirmed;
        appointmentm.Op = appointment.Op;
        appointmentm.Note = appointment.Note;
        appointmentm.ProvNum = appointment.ProvNum;
        appointmentm.ProvHyg = appointment.ProvHyg;
        appointmentm.AptDateTime = appointment.AptDateTime;
        appointmentm.IsNewPatient = appointment.IsNewPatient;
        appointmentm.ProcDescript = appointment.ProcDescript;
        appointmentm.ClinicNum = appointment.ClinicNum;
        appointmentm.IsHygiene = appointment.IsHygiene;
        return appointmentm;
    }

}

