//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:06 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class ReqStudentCrud   
{
    /**
    * Gets one ReqStudent object from the database using the primary key.  Returns null if not found.
    */
    public static ReqStudent selectOne(long reqStudentNum) throws Exception {
        String command = "SELECT * FROM reqstudent " + "WHERE ReqStudentNum = " + POut.Long(reqStudentNum);
        List<ReqStudent> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one ReqStudent object from the database using a query.
    */
    public static ReqStudent selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ReqStudent> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of ReqStudent objects from the database using a query.
    */
    public static List<ReqStudent> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ReqStudent> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ReqStudent> tableToList(DataTable table) throws Exception {
        List<ReqStudent> retVal = new List<ReqStudent>();
        ReqStudent reqStudent = new ReqStudent();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            reqStudent = new ReqStudent();
            reqStudent.ReqStudentNum = PIn.Long(table.Rows[i]["ReqStudentNum"].ToString());
            reqStudent.ReqNeededNum = PIn.Long(table.Rows[i]["ReqNeededNum"].ToString());
            reqStudent.Descript = PIn.String(table.Rows[i]["Descript"].ToString());
            reqStudent.SchoolCourseNum = PIn.Long(table.Rows[i]["SchoolCourseNum"].ToString());
            reqStudent.ProvNum = PIn.Long(table.Rows[i]["ProvNum"].ToString());
            reqStudent.AptNum = PIn.Long(table.Rows[i]["AptNum"].ToString());
            reqStudent.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            reqStudent.InstructorNum = PIn.Long(table.Rows[i]["InstructorNum"].ToString());
            reqStudent.DateCompleted = PIn.Date(table.Rows[i]["DateCompleted"].ToString());
            retVal.Add(reqStudent);
        }
        return retVal;
    }

    /**
    * Inserts one ReqStudent into the database.  Returns the new priKey.
    */
    public static long insert(ReqStudent reqStudent) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            reqStudent.ReqStudentNum = DbHelper.GetNextOracleKey("reqstudent", "ReqStudentNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(reqStudent,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        reqStudent.ReqStudentNum++;
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
            return insert(reqStudent,false);
        } 
    }

    /**
    * Inserts one ReqStudent into the database.  Provides option to use the existing priKey.
    */
    public static long insert(ReqStudent reqStudent, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            reqStudent.ReqStudentNum = ReplicationServers.GetKey("reqstudent", "ReqStudentNum");
        }
         
        String command = "INSERT INTO reqstudent (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "ReqStudentNum,";
        }
         
        command += "ReqNeededNum,Descript,SchoolCourseNum,ProvNum,AptNum,PatNum,InstructorNum,DateCompleted) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(reqStudent.ReqStudentNum) + ",";
        }
         
        command += POut.Long(reqStudent.ReqNeededNum) + "," + "'" + POut.String(reqStudent.Descript) + "'," + POut.Long(reqStudent.SchoolCourseNum) + "," + POut.Long(reqStudent.ProvNum) + "," + POut.Long(reqStudent.AptNum) + "," + POut.Long(reqStudent.PatNum) + "," + POut.Long(reqStudent.InstructorNum) + "," + POut.Date(reqStudent.DateCompleted) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            reqStudent.ReqStudentNum = Db.NonQ(command, true);
        } 
        return reqStudent.ReqStudentNum;
    }

    /**
    * Updates one ReqStudent in the database.
    */
    public static void update(ReqStudent reqStudent) throws Exception {
        String command = "UPDATE reqstudent SET " + "ReqNeededNum   =  " + POut.Long(reqStudent.ReqNeededNum) + ", " + "Descript       = '" + POut.String(reqStudent.Descript) + "', " + "SchoolCourseNum=  " + POut.Long(reqStudent.SchoolCourseNum) + ", " + "ProvNum        =  " + POut.Long(reqStudent.ProvNum) + ", " + "AptNum         =  " + POut.Long(reqStudent.AptNum) + ", " + "PatNum         =  " + POut.Long(reqStudent.PatNum) + ", " + "InstructorNum  =  " + POut.Long(reqStudent.InstructorNum) + ", " + "DateCompleted  =  " + POut.Date(reqStudent.DateCompleted) + " " + "WHERE ReqStudentNum = " + POut.Long(reqStudent.ReqStudentNum);
        Db.NonQ(command);
    }

    /**
    * Updates one ReqStudent in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(ReqStudent reqStudent, ReqStudent oldReqStudent) throws Exception {
        String command = "";
        if (reqStudent.ReqNeededNum != oldReqStudent.ReqNeededNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ReqNeededNum = " + POut.Long(reqStudent.ReqNeededNum) + "";
        }
         
        if (reqStudent.Descript != oldReqStudent.Descript)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Descript = '" + POut.String(reqStudent.Descript) + "'";
        }
         
        if (reqStudent.SchoolCourseNum != oldReqStudent.SchoolCourseNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SchoolCourseNum = " + POut.Long(reqStudent.SchoolCourseNum) + "";
        }
         
        if (reqStudent.ProvNum != oldReqStudent.ProvNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProvNum = " + POut.Long(reqStudent.ProvNum) + "";
        }
         
        if (reqStudent.AptNum != oldReqStudent.AptNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AptNum = " + POut.Long(reqStudent.AptNum) + "";
        }
         
        if (reqStudent.PatNum != oldReqStudent.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(reqStudent.PatNum) + "";
        }
         
        if (reqStudent.InstructorNum != oldReqStudent.InstructorNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "InstructorNum = " + POut.Long(reqStudent.InstructorNum) + "";
        }
         
        if (reqStudent.DateCompleted != oldReqStudent.DateCompleted)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateCompleted = " + POut.Date(reqStudent.DateCompleted) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE reqstudent SET " + command + " WHERE ReqStudentNum = " + POut.Long(reqStudent.ReqStudentNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one ReqStudent from the database.
    */
    public static void delete(long reqStudentNum) throws Exception {
        String command = "DELETE FROM reqstudent " + "WHERE ReqStudentNum = " + POut.Long(reqStudentNum);
        Db.NonQ(command);
    }

}


