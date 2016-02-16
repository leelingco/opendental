//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PerioExam;
import OpenDentBusiness.PerioExams;
import OpenDentBusiness.PerioMeasure;
import OpenDentBusiness.PerioSequenceType;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PerioMeasures   
{
    /**
    * List of all perio measures for the current patient. Dim 1 is exams. Dim 2 is Sequences. Dim 3 is Measurements, always 33 per sequence(0 is not used).  This public static variable is only used by the UI.  It's here because it would be complicated to put it in ContrPerio.
    */
    public static PerioMeasure[][][] List = new PerioMeasure[][][]();
    /**
    * 
    */
    public static void update(PerioMeasure Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.PerioMeasureCrud.Update(Cur);
        //3-10-10 A bug that only lasted for a few weeks has resulted in a number of duplicate entries for each tooth.
        //So we need to clean up duplicates as we go.  Might put in db maint later.
        String command = "DELETE FROM periomeasure WHERE " + "PerioExamNum = " + POut.long(Cur.PerioExamNum) + " AND SequenceType = " + POut.Long(((Enum)Cur.SequenceType).ordinal()) + " AND IntTooth = " + POut.Long(Cur.IntTooth) + " AND PerioMeasureNum != " + POut.long(Cur.PerioMeasureNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long insert(PerioMeasure Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.PerioMeasureNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.PerioMeasureNum;
        }
         
        return Crud.PerioMeasureCrud.Insert(Cur);
    }

    /**
    * 
    */
    public static void delete(PerioMeasure Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from periomeasure WHERE PerioMeasureNum = '" + Cur.PerioMeasureNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * For the current exam, clears existing skipped teeth and resets them to the specified skipped teeth. The ArrayList valid values are 1-32 int.
    */
    public static void setSkipped(long perioExamNum, List<int> skippedTeeth) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), perioExamNum, skippedTeeth);
            return ;
        }
         
        //for(int i=0;i<skippedTeeth.Count;i++){
        //MessageBox.Show(skippedTeeth[i].ToString());
        //}
        //first, delete all skipped teeth for this exam
        String command = "DELETE from periomeasure WHERE " + "PerioExamNum = '" + perioExamNum.ToString() + "' " + "AND SequenceType = '" + POut.Long(((Enum)PerioSequenceType.SkipTooth).ordinal()) + "'";
        Db.nonQ(command);
        //then add the new ones in one at a time.
        PerioMeasure Cur;
        for (int i = 0;i < skippedTeeth.Count;i++)
        {
            Cur = new PerioMeasure();
            Cur.PerioExamNum = perioExamNum;
            Cur.SequenceType = PerioSequenceType.SkipTooth;
            Cur.IntTooth = skippedTeeth[i];
            Cur.ToothValue = 1;
            Cur.MBvalue = -1;
            Cur.Bvalue = -1;
            Cur.DBvalue = -1;
            Cur.MLvalue = -1;
            Cur.Lvalue = -1;
            Cur.DLvalue = -1;
            insert(Cur);
        }
    }

    /**
    * Used in FormPerio.Add_Click. For the specified exam, gets a list of all skipped teeth. The ArrayList valid values are 1-32 int.
    */
    public static List<int> getSkipped(long perioExamNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<int>>GetObject(MethodBase.GetCurrentMethod(), perioExamNum);
        }
         
        String command = "SELECT IntTooth FROM periomeasure WHERE " + "SequenceType = '" + POut.int(((Enum)PerioSequenceType.SkipTooth).ordinal()) + "' " + "AND PerioExamNum = '" + perioExamNum.ToString() + "' " + "AND ToothValue = '1'";
        DataTable table = Db.getTable(command);
        List<int> retVal = new List<int>();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(PIn.Int(table.Rows[i][0].ToString()));
        }
        return retVal;
    }

    /**
    * Gets all measurements for the current patient, then organizes them by exam and sequence.
    */
    public static void refresh(long patNum, List<PerioExam> listPerioExams) throws Exception {
        //No need to check RemotingRole; no call to db.
        DataTable table = GetMeasurementTable(patNum, listPerioExams);
        List = new PerioMeasure[listPerioExams.Count, Enum.GetNames(PerioSequenceType.class).Length, 33];
        int examIdx = 0;
        //PerioMeasure pm;
        List<PerioMeasure> list = Crud.PerioMeasureCrud.TableToList(table);
        for (int i = 0;i < list.Count;i++)
        {
            //the next statement can also handle exams with no measurements:
            //if this is the first row
            if (i == 0 || list[i].PerioExamNum != list[i - 1].PerioExamNum)
            {
                //or examNum has changed
                examIdx = PerioExams.GetExamIndex(listPerioExams, list[i].PerioExamNum);
            }
             
            List[examIdx, (int)list[i].SequenceType, list[i].IntTooth] = list[i];
        }
    }

    public static DataTable getMeasurementTable(long patNum, List<PerioExam> listPerioExams) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), patNum, listPerioExams);
        }
         
        String command = "SELECT periomeasure.*,perioexam.ExamDate" + " FROM periomeasure,perioexam" + " WHERE periomeasure.PerioExamNum = perioexam.PerioExamNum" + " AND perioexam.PatNum = '" + patNum.ToString() + "'" + " ORDER BY perioexam.ExamDate";
        DataTable table = Db.getTable(command);
        return table;
    }

    public static List<PerioMeasure> getAllForExam(long perioExamNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PerioMeasure>>GetObject(MethodBase.GetCurrentMethod(), perioExamNum);
        }
         
        String command = "SELECT * FROM periomeasure " + "WHERE PerioExamNum = " + POut.long(perioExamNum);
        return Crud.PerioMeasureCrud.SelectMany(command);
    }

    /**
    * A -1 will be changed to a 0. Measures over 100 are changed to 100-measure. i.e. 100-104=-4 for hyperplasiac GM.
    */
    public static int adjustGMVal(int measure) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (measure == -1)
        {
            return 0;
        }
        else //-1 means no measurement, null.  In the places where this method is used, we have designed it to expect a 0 in those cases.
        if (measure > 100)
        {
            return 100 - measure;
        }
          
        return measure;
    }

}


//no adjustments needed.