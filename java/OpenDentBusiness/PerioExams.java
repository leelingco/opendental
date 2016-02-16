//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PerioExam;
import OpenDentBusiness.PIn;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PerioExams   
{
    /**
    * This is public static because it would be hard to pass it into ContrPerio.  Only used by UI.
    */
    public static List<PerioExam> ListExams = new List<PerioExam>();
    /**
    * Most recent date last.  All exams loaded, even if not displayed.
    */
    public static void refresh(long patNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        DataTable table = getExamsTable(patNum);
        ListExams = new List<PerioExam>();
        PerioExam exam;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            exam = new PerioExam();
            exam.PerioExamNum = PIn.Long(table.Rows[i][0].ToString());
            exam.PatNum = PIn.Long(table.Rows[i][1].ToString());
            exam.ExamDate = PIn.Date(table.Rows[i][2].ToString());
            exam.ProvNum = PIn.Long(table.Rows[i][3].ToString());
            ListExams.Add(exam);
        }
    }

    //return list;
    //PerioMeasures.Refresh(patNum);
    public static DataTable getExamsTable(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * from perioexam" + " WHERE PatNum = '" + patNum.ToString() + "'" + " ORDER BY perioexam.ExamDate";
        DataTable table = Db.getTable(command);
        return table;
    }

    /**
    * 
    */
    public static void update(PerioExam Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.PerioExamCrud.Update(Cur);
    }

    /**
    * 
    */
    public static long insert(PerioExam Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.PerioExamNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.PerioExamNum;
        }
         
        return Crud.PerioExamCrud.Insert(Cur);
    }

    /**
    * 
    */
    public static void delete(PerioExam Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from perioexam WHERE PerioExamNum = '" + Cur.PerioExamNum.ToString() + "'";
        Db.nonQ(command);
        command = "DELETE from periomeasure WHERE PerioExamNum = '" + Cur.PerioExamNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * Used by PerioMeasures when refreshing to organize array.
    */
    public static int getExamIndex(List<PerioExam> list, long perioExamNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (list[i].PerioExamNum == perioExamNum)
            {
                return i;
            }
             
        }
        return 0;
    }

}


//MessageBox.Show("Error. PerioExamNum not in list: "+perioExamNum.ToString());