//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:57:58 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class AppointmentCrud   
{
    /**
    * Gets one Appointment object from the database using the primary key.  Returns null if not found.
    */
    public static Appointment selectOne(long aptNum) throws Exception {
        String command = "SELECT * FROM appointment " + "WHERE AptNum = " + POut.long(aptNum);
        List<Appointment> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Appointment object from the database using a query.
    */
    public static Appointment selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Appointment> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Appointment objects from the database using a query.
    */
    public static List<Appointment> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Appointment> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Appointment> tableToList(DataTable table) throws Exception {
        List<Appointment> retVal = new List<Appointment>();
        Appointment appointment;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            appointment = new Appointment();
            appointment.AptNum = PIn.Long(table.Rows[i]["AptNum"].ToString());
            appointment.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            appointment.AptStatus = (ApptStatus)PIn.Int(table.Rows[i]["AptStatus"].ToString());
            appointment.Pattern = PIn.String(table.Rows[i]["Pattern"].ToString());
            appointment.Confirmed = PIn.Long(table.Rows[i]["Confirmed"].ToString());
            appointment.TimeLocked = PIn.Bool(table.Rows[i]["TimeLocked"].ToString());
            appointment.Op = PIn.Long(table.Rows[i]["Op"].ToString());
            appointment.Note = PIn.String(table.Rows[i]["Note"].ToString());
            appointment.ProvNum = PIn.Long(table.Rows[i]["ProvNum"].ToString());
            appointment.ProvHyg = PIn.Long(table.Rows[i]["ProvHyg"].ToString());
            appointment.AptDateTime = PIn.DateT(table.Rows[i]["AptDateTime"].ToString());
            appointment.NextAptNum = PIn.Long(table.Rows[i]["NextAptNum"].ToString());
            appointment.UnschedStatus = PIn.Long(table.Rows[i]["UnschedStatus"].ToString());
            appointment.IsNewPatient = PIn.Bool(table.Rows[i]["IsNewPatient"].ToString());
            appointment.ProcDescript = PIn.String(table.Rows[i]["ProcDescript"].ToString());
            appointment.Assistant = PIn.Long(table.Rows[i]["Assistant"].ToString());
            appointment.ClinicNum = PIn.Long(table.Rows[i]["ClinicNum"].ToString());
            appointment.IsHygiene = PIn.Bool(table.Rows[i]["IsHygiene"].ToString());
            appointment.DateTStamp = PIn.DateT(table.Rows[i]["DateTStamp"].ToString());
            appointment.DateTimeArrived = PIn.DateT(table.Rows[i]["DateTimeArrived"].ToString());
            appointment.DateTimeSeated = PIn.DateT(table.Rows[i]["DateTimeSeated"].ToString());
            appointment.DateTimeDismissed = PIn.DateT(table.Rows[i]["DateTimeDismissed"].ToString());
            appointment.InsPlan1 = PIn.Long(table.Rows[i]["InsPlan1"].ToString());
            appointment.InsPlan2 = PIn.Long(table.Rows[i]["InsPlan2"].ToString());
            appointment.DateTimeAskedToArrive = PIn.DateT(table.Rows[i]["DateTimeAskedToArrive"].ToString());
            appointment.ProcsColored = PIn.String(table.Rows[i]["ProcsColored"].ToString());
            appointment.ColorOverride = Color.FromArgb(PIn.Int(table.Rows[i]["ColorOverride"].ToString()));
            retVal.Add(appointment);
        }
        return retVal;
    }

    /**
    * Inserts one Appointment into the database.  Returns the new priKey.
    */
    public static long insert(Appointment appointment) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            appointment.AptNum = DbHelper.getNextOracleKey("appointment","AptNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(appointment, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        appointment.AptNum++;
                        loopcount++;
                    }
                    else
                    {
                        throw ex;
                    } 
                }
            
            }
            throw new ApplicationException("Insert failed.  Could not generate primary key.");
        }
        else
        {
            return Insert(appointment, false);
        } 
    }

    /**
    * Inserts one Appointment into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Appointment appointment, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            appointment.AptNum = ReplicationServers.getKey("appointment","AptNum");
        }
         
        String command = "INSERT INTO appointment (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "AptNum,";
        }
         
        command += "PatNum,AptStatus,Pattern,Confirmed,TimeLocked,Op,Note,ProvNum,ProvHyg,AptDateTime,NextAptNum,UnschedStatus,IsNewPatient,ProcDescript,Assistant,ClinicNum,IsHygiene,DateTimeArrived,DateTimeSeated,DateTimeDismissed,InsPlan1,InsPlan2,DateTimeAskedToArrive,ProcsColored,ColorOverride) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(appointment.AptNum) + ",";
        }
         
        //DateTStamp can only be set by MySQL
        command += POut.long(appointment.PatNum) + "," + POut.int(((Enum)appointment.AptStatus).ordinal()) + "," + "'" + POut.string(appointment.Pattern) + "'," + POut.long(appointment.Confirmed) + "," + POut.bool(appointment.TimeLocked) + "," + POut.long(appointment.Op) + "," + "'" + POut.string(appointment.Note) + "'," + POut.long(appointment.ProvNum) + "," + POut.long(appointment.ProvHyg) + "," + POut.dateT(appointment.AptDateTime) + "," + POut.long(appointment.NextAptNum) + "," + POut.long(appointment.UnschedStatus) + "," + POut.bool(appointment.IsNewPatient) + "," + "'" + POut.string(appointment.ProcDescript) + "'," + POut.long(appointment.Assistant) + "," + POut.long(appointment.ClinicNum) + "," + POut.bool(appointment.IsHygiene) + "," + POut.dateT(appointment.DateTimeArrived) + "," + POut.dateT(appointment.DateTimeSeated) + "," + POut.dateT(appointment.DateTimeDismissed) + "," + POut.long(appointment.InsPlan1) + "," + POut.long(appointment.InsPlan2) + "," + POut.dateT(appointment.DateTimeAskedToArrive) + "," + "'" + POut.String(appointment.ProcsColored) + "'," + POut.Int(appointment.ColorOverride.ToArgb()) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            appointment.AptNum = Db.nonQ(command,true);
        } 
        return appointment.AptNum;
    }

    /**
    * Updates one Appointment in the database.
    */
    public static void update(Appointment appointment) throws Exception {
        //DateTStamp can only be set by MySQL
        String command = "UPDATE appointment SET " + "PatNum               =  " + POut.long(appointment.PatNum) + ", " + "AptStatus            =  " + POut.int(((Enum)appointment.AptStatus).ordinal()) + ", " + "Pattern              = '" + POut.string(appointment.Pattern) + "', " + "Confirmed            =  " + POut.long(appointment.Confirmed) + ", " + "TimeLocked           =  " + POut.bool(appointment.TimeLocked) + ", " + "Op                   =  " + POut.long(appointment.Op) + ", " + "Note                 = '" + POut.string(appointment.Note) + "', " + "ProvNum              =  " + POut.long(appointment.ProvNum) + ", " + "ProvHyg              =  " + POut.long(appointment.ProvHyg) + ", " + "AptDateTime          =  " + POut.dateT(appointment.AptDateTime) + ", " + "NextAptNum           =  " + POut.long(appointment.NextAptNum) + ", " + "UnschedStatus        =  " + POut.long(appointment.UnschedStatus) + ", " + "IsNewPatient         =  " + POut.bool(appointment.IsNewPatient) + ", " + "ProcDescript         = '" + POut.string(appointment.ProcDescript) + "', " + "Assistant            =  " + POut.long(appointment.Assistant) + ", " + "ClinicNum            =  " + POut.long(appointment.ClinicNum) + ", " + "IsHygiene            =  " + POut.bool(appointment.IsHygiene) + ", " + "DateTimeArrived      =  " + POut.dateT(appointment.DateTimeArrived) + ", " + "DateTimeSeated       =  " + POut.dateT(appointment.DateTimeSeated) + ", " + "DateTimeDismissed    =  " + POut.dateT(appointment.DateTimeDismissed) + ", " + "InsPlan1             =  " + POut.long(appointment.InsPlan1) + ", " + "InsPlan2             =  " + POut.long(appointment.InsPlan2) + ", " + "DateTimeAskedToArrive=  " + POut.dateT(appointment.DateTimeAskedToArrive) + ", " + "ProcsColored         = '" + POut.String(appointment.ProcsColored) + "', " + "ColorOverride        =  " + POut.Int(appointment.ColorOverride.ToArgb()) + " " + "WHERE AptNum = " + POut.long(appointment.AptNum);
        Db.nonQ(command);
    }

    /**
    * Updates one Appointment in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Appointment appointment, Appointment oldAppointment) throws Exception {
        String command = "";
        if (appointment.PatNum != oldAppointment.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.long(appointment.PatNum) + "";
        }
         
        if (appointment.AptStatus != oldAppointment.AptStatus)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AptStatus = " + POut.int(((Enum)appointment.AptStatus).ordinal()) + "";
        }
         
        if (!StringSupport.equals(appointment.Pattern, oldAppointment.Pattern))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Pattern = '" + POut.string(appointment.Pattern) + "'";
        }
         
        if (appointment.Confirmed != oldAppointment.Confirmed)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Confirmed = " + POut.long(appointment.Confirmed) + "";
        }
         
        if (appointment.TimeLocked != oldAppointment.TimeLocked)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TimeLocked = " + POut.bool(appointment.TimeLocked) + "";
        }
         
        if (appointment.Op != oldAppointment.Op)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Op = " + POut.long(appointment.Op) + "";
        }
         
        if (!StringSupport.equals(appointment.Note, oldAppointment.Note))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Note = '" + POut.string(appointment.Note) + "'";
        }
         
        if (appointment.ProvNum != oldAppointment.ProvNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProvNum = " + POut.long(appointment.ProvNum) + "";
        }
         
        if (appointment.ProvHyg != oldAppointment.ProvHyg)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProvHyg = " + POut.long(appointment.ProvHyg) + "";
        }
         
        if (appointment.AptDateTime != oldAppointment.AptDateTime)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AptDateTime = " + POut.dateT(appointment.AptDateTime) + "";
        }
         
        if (appointment.NextAptNum != oldAppointment.NextAptNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "NextAptNum = " + POut.long(appointment.NextAptNum) + "";
        }
         
        if (appointment.UnschedStatus != oldAppointment.UnschedStatus)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UnschedStatus = " + POut.long(appointment.UnschedStatus) + "";
        }
         
        if (appointment.IsNewPatient != oldAppointment.IsNewPatient)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsNewPatient = " + POut.bool(appointment.IsNewPatient) + "";
        }
         
        if (!StringSupport.equals(appointment.ProcDescript, oldAppointment.ProcDescript))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProcDescript = '" + POut.string(appointment.ProcDescript) + "'";
        }
         
        if (appointment.Assistant != oldAppointment.Assistant)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Assistant = " + POut.long(appointment.Assistant) + "";
        }
         
        if (appointment.ClinicNum != oldAppointment.ClinicNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClinicNum = " + POut.long(appointment.ClinicNum) + "";
        }
         
        if (appointment.IsHygiene != oldAppointment.IsHygiene)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsHygiene = " + POut.bool(appointment.IsHygiene) + "";
        }
         
        //DateTStamp can only be set by MySQL
        if (appointment.DateTimeArrived != oldAppointment.DateTimeArrived)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateTimeArrived = " + POut.dateT(appointment.DateTimeArrived) + "";
        }
         
        if (appointment.DateTimeSeated != oldAppointment.DateTimeSeated)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateTimeSeated = " + POut.dateT(appointment.DateTimeSeated) + "";
        }
         
        if (appointment.DateTimeDismissed != oldAppointment.DateTimeDismissed)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateTimeDismissed = " + POut.dateT(appointment.DateTimeDismissed) + "";
        }
         
        if (appointment.InsPlan1 != oldAppointment.InsPlan1)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "InsPlan1 = " + POut.long(appointment.InsPlan1) + "";
        }
         
        if (appointment.InsPlan2 != oldAppointment.InsPlan2)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "InsPlan2 = " + POut.long(appointment.InsPlan2) + "";
        }
         
        if (appointment.DateTimeAskedToArrive != oldAppointment.DateTimeAskedToArrive)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateTimeAskedToArrive = " + POut.dateT(appointment.DateTimeAskedToArrive) + "";
        }
         
        if (appointment.ProcsColored != oldAppointment.ProcsColored)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProcsColored = '" + POut.String(appointment.ProcsColored) + "'";
        }
         
        if (appointment.ColorOverride != oldAppointment.ColorOverride)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ColorOverride = " + POut.Int(appointment.ColorOverride.ToArgb()) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE appointment SET " + command + " WHERE AptNum = " + POut.long(appointment.AptNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Appointment from the database.
    */
    public static void delete(long aptNum) throws Exception {
        String command = "DELETE FROM appointment " + "WHERE AptNum = " + POut.long(aptNum);
        Db.nonQ(command);
    }

}


