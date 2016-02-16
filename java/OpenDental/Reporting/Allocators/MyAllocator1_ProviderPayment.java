//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:23 PM
//

package OpenDental.Reporting.Allocators;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.Reporting.Allocators.Allocator;
import OpenDental.Reporting.Allocators.IAllocate;
import OpenDental.Reporting.Allocators.MyAllocator1.FormProgressViewer;
import OpenDental.Reporting.Allocators.MyAllocator1.PU;
import OpenDentBusiness.Db;
import OpenDentBusiness.PrefC;

public class MyAllocator1_ProviderPayment  extends Allocator implements IAllocate
{
    public static final String TABLENAME = "allocation_provider";
    /**
    * The id of this allocator.  Programmer assigned.
    */
    public static int AllocationTypeID = 1;
    /**
    * These are the columns used in the table defined in TABLENAME
    */
    public static final String[] TABLE_COLUMNS = { "AllocNum", "AllocType", "Guarantor", "ProvNum", "PayTableSource", "PaySourceNum", "AllocToTableSource", "AllocToSourceNum", "Amount", "IsFullyAllocated" };
    public static final String Pref_AllocatorProvider1_Use = "AllocatorProvider1_Use";
    public static final String Pref_AllocatorProvider1_ToolHasRun = "AllocatorProvider1_ToolHasRun";
    public MyAllocator1_ProviderPayment() throws Exception {
        super(TABLENAME, TABLE_COLUMNS);
    }

    //SetDbaseTable_and_Columns(TABLENAME, TABLE_COLUMNS);
    /**
    * Where it All Happens.  The Ledger is Filled.  The EqualizePaymentsV2 runs the
    * algorithim that allocates the Ledger AND writes the values to
    * the table allocation_provider.  No Table is written to other than allocation_provider
    * Fill uses only logic from the data in allocation_provider and current status of
    * various opendental tables to determine what needs to be changed.
    */
    public boolean allocate(int iGuarantor) throws Exception {
        return AllocateWithToolCheck(iGuarantor);
    }

    /**
    * Does not check to see if Preferences are set in OD to handle this allocator.  This is only used in the Batch
    * processing of the Allocator Tool.  Do not want the overhead of checking the preferences everytime the tool is run.
    * Probably not a big deal unless it tries to get the preference from the Dbase every time.
    */
    private boolean allocateWithOutToolCheck(int iGuarantor) throws Exception {
        boolean AllocatedNormally = true;
        try
        {
            _AllocateExecute(iGuarantor);
        }
        catch (Exception __dummyCatchVar0)
        {
            AllocatedNormally = false;
        }

        return AllocatedNormally;
    }

    /**
    * Where the actual allocation occurs.  Put it in this method so that we could run the allocator in different
    * circumstances.
    * 
    *  @param iGuarantor
    */
    private static void _AllocateExecute(long iGuarantor) throws Exception {
        OpenDental.Reporting.Allocators.MyAllocator1.GuarantorLedgerItemsCollection Ledger = new OpenDental.Reporting.Allocators.MyAllocator1.GuarantorLedgerItemsCollection(iGuarantor);
        Ledger.fill(false);
        Ledger.equalizePaymentsV2();
    }

    /// <summary>
    /// Calls Allocate but first checks for existance of prefences and an indication that The tool has run.
    /// The reason for the overlaid method is becuase the tool when it runs
    /// <href = "AllocatorCollection.CallAll_Allocators>  See the AllocatorCollection.CallAll_Allocators </href
    /// </summary>
    /// <param name="iGuarantor"></param>
    /// <returns></returns>
    public static boolean allocateWithToolCheck(long iGuarantor) throws Exception {
        boolean AllocatedNomally = false;
        try
        {
            boolean toolRan = false;
            boolean isUsing = false;
            if (PrefC.containsKey(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_ToolHasRun))
                toolRan = StringSupport.equals(PrefC.getRaw(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_ToolHasRun), "1");
             
            if (PrefC.containsKey(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_Use))
                isUsing = StringSupport.equals(PrefC.getRaw(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.Pref_AllocatorProvider1_Use), "1");
             
            if (toolRan & isUsing)
            {
                _AllocateExecute(iGuarantor);
                AllocatedNomally = true;
            }
             
        }
        catch (Exception __dummyCatchVar1)
        {
            AllocatedNomally = false;
        }

        return AllocatedNomally;
    }

    public boolean deAllocate(int iGuarantor) throws Exception {
        throw new Exception("The method or operation is not implemented.");
    }

    /**
    * // 
    * // Sets the inherited properties.
    * // Really did not wan't this member public
    * // 
    * // 
    *  @param tableName 
    * // 
    *  @param Columns
    */
    //public override void SetDbaseTable_and_Columns(string tableName, string[] Columns)
    //{
    //    this.DbaseStorageTable = tableName;
    //    this.DbaseTableColumns = Columns;
    //}
    public static String creatTableString(OpenDentBusiness.DatabaseType type1) throws Exception {
        // Note command to create table
        // //Db.NonQ(MyAllocator1_ProviderPayment.CreatTableString());
        // Put here for reference not for implementation of code. CreatTableString does not check for existance of table.
        String command = "";
        if (type1 == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "CREATE TABLE " + TABLENAME + " (" + "AllocNum INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n" + 
            "\t\t\t\t\tAllocType TINYINT,\r\n" + 
            "\t\t\t\t\tGuarantor BIGINT NOT NULL,\r\n" + 
            "\t\t\t\t\tProvNum bigint DEFAULT 0,\r\n" + 
            "\t\t\t\t\tPayTableSource TINYINT,\r\n" + 
            "\t\t\t\t\tPaySourceNum bigint,\r\n" + 
            "\t\t\t\t\tAllocToTableSource TINYINT,\r\n" + 
            "\t\t\t\t\tAllocToSourceNum int,\r\n" + 
            "\t\t\t\t\tAmount DECIMAL (10,2) NOT NULL,\r\n" + 
            "\t\t\t\t\tIsFullyAllocated TINYINT\r\n" + 
            "\t\t\t\t\t)\r\n" + 
            "\t\t\t\t\tDEFAULT CHARSET=utf8";
        }
         
        return command;
    }

    //else if (type1 == OpenDentBusiness.DatabaseType.Oracle)
    //;// not implemented yet
    /**
    * Generates a string in form of '(#,#,#,#,#,#, ##.##, 0)' just so things match up.
    * Designed to be used with a statment such as INSERT INTO `account` VALUES (1,'Checking Account',0,'',0,-1),(2,'Ca ....
    */
    public static String valueString(int iAllocType, uint uiGuarantor, int iProvNum, int iTableSource, ulong ulSourceNum, int iAllocatedTo_TableSource, ulong ulAllocatedTo_SourceNum, double dAmmount, boolean IsFullyAlloc1) throws Exception {
        // Example of query
        // LOCK TABLES `account` WRITE;
        //INSERT INTO `account` VALUES (1,'Checking Account',0,'',0,-1),(2,'Cash Box',0,'',0,-
        //,(4,'Equipment',0,'',0,-1),(5,'Accumulated Depreciation, Equipment',0,'',0,-1),(6,'B
        //ated Capital',2,'',0,-1),(8,'Retained Earnings',2,'',0,-1),(9,'Patient Fee Income',3
        //'',0,-1),(11,'Supplies',4,'',0,-1),(12,'Services',4,'',0,-1),(13,'Wages',4,'',0,-1);
        //UNLOCK TABLES;
        String rVal = "(" + iAllocType + " , " + uiGuarantor + " , " + iProvNum + " , " + iTableSource + " , " + ulSourceNum + " , " + iAllocatedTo_TableSource + " , " + ulAllocatedTo_SourceNum + " , " + dAmmount + " , " + (IsFullyAlloc1 ? 1 : 0).ToString() + ")";
        return rVal;
    }

    /**
    * The Header of the insert statement.  'INSERT INTO [tablename] (field1, field2...) VALUES '
    * use with ValueString(int ...)
    */
    public static String valueStringHeader() throws Exception {
        return "INSERT INTO " + OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.TABLENAME + "(AllocType, Guarantor, ProvNum, " + "PayTableSource, PaySourceNum, AllocToTableSource, AllocToSourceNum, Amount, IsFullyAllocated) VALUES ";
    }

    public static String create_AP_temp_table_string(OpenDentBusiness.DatabaseType type1) throws Exception {
        String command = "";
        if (type1 == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "CREATE TABLE " + TABLENAME + "_temp \n(\n" + "   tempIndex INT NOT NULL AUTO_INCREMENT PRIMARY KEY, \n" + "   Guarantor bigint NOT NULL, \n" + "   AllocStatus TINYINT\n)\n" + "DEFAULT CHARSET=utf8";
        }
         
        return command;
    }

    //else if(type1 == OpenDentBusiness.DatabaseType.Oracle)
    //	;// not implemented yet
    public enum ProcessingState
    {
        // When the allocator is first used it will take a lot of time to update.  It should be written
        // in its own thread so it can be cancled and completed in a timely manner.
        //
        //  create new temporary table  allocation_provider_temp
        //  3 fields
        //	Index, Guarantor, ProcessingState
        //
        //  enum ProcessingState {NeverStarted, Started, Complete}
        // This way we can look for any Started Objects that were not Completed incase of a Dbase Connection Failure
        //  and fix the problem.
        //
        //  Fill table first with all Guantors,  Markethem as NeverStarted
        //  Run the Equalizer in batches of 50;
        // Match Signature of DoWorkEventHandler which is void bw_DoWork(object sender, DoWorkEventArgs e)
        NeverStarted,
        Started_and_Incomplete,
        Complete
    }
    public void startBatchAllocation() throws Exception {
        FormProgressViewer fpv = new FormProgressViewer();
        fpv.setSET_WORKER_METHOD(new System.ComponentModel.DoWorkEventHandler(StartBatch_DoWork_Handler));
        fpv.sTART_WORK();
        fpv.ShowDialog();
    }

    private void startBatch_DoWork_Handler(Object sender, System.ComponentModel.DoWorkEventArgs e) throws Exception {
        System.ComponentModel.BackgroundWorker bw = (System.ComponentModel.BackgroundWorker)sender;
        if (bw == null)
        {
            PU.setMB("This method is to be called by a a background worker.\n" + PU.getMethod());
            return ;
        }
         
        DateTime StartTime = DateTime.Now;
        bw.ReportProgress(0, "Batch Processing Started at: " + String.Format("{0}h:{1}m.{2}", StartTime.Hour, StartTime.Minute, StartTime.Second));
        // Generate list of Guarantors to allocate
        int[] GuarantorsToAllocate = generate_GuarantorList_ToAllocate();
        if (GuarantorsToAllocate == null || GuarantorsToAllocate.Length == 0)
        {
            MessageBox.Show(Lan.g(this,"No Guarantors to Allocate. Exiting."));
            return ;
        }
         
        int BatchSize = 50;
        int iProgress = 0;
        OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment allocator = new OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment();
        for (int i = 0;i < GuarantorsToAllocate.Length + BatchSize;i = i + BatchSize)
        {
            int from = i;
            int to = (i + BatchSize > GuarantorsToAllocate.Length ? GuarantorsToAllocate.Length : i + BatchSize);
            iProgress = (to * 100) / (GuarantorsToAllocate.Length - 1);
            bw.ReportProgress(iProgress, "Beginning Batch " + from + "-" + to + " of " + GuarantorsToAllocate.Length + " Guarantors.");
            for (int j = from;j < to;j++)
            {
                String UpdateTempTableCommand = "INSERT INTO " + TABLENAME + "_temp (Guarantor,AllocStatus) VALUES " + "( " + GuarantorsToAllocate[j].ToString() + ", " + (((Enum)ProcessingState.Started_and_Incomplete).ordinal()).ToString() + " ) ";
                Db.nonQOld(UpdateTempTableCommand);
                allocator.AllocateWithOutToolCheck(GuarantorsToAllocate[j]);
                iProgress = (j * 100) / (GuarantorsToAllocate.Length - 1);
                if (bw.CancellationPending)
                {
                    // Try to cancel between allocations which does a lot of writting
                    i = GuarantorsToAllocate.Length;
                    // end loop
                    j = to;
                    iProgress = (j * 100) / (GuarantorsToAllocate.Length - 1);
                    // recalculate progrss.
                    bw.ReportProgress(iProgress, "User Cancelled Requested");
                    e.Cancel = true;
                }
                 
                UpdateTempTableCommand = "UPDATE " + TABLENAME + "_temp SET AllocStatus =" + (((Enum)ProcessingState.Complete).ordinal()).ToString() + "  WHERE Guarantor = " + GuarantorsToAllocate[j].ToString();
                Db.nonQOld(UpdateTempTableCommand);
            }
            iProgress = (to * 100) / (GuarantorsToAllocate.Length - 1);
            bw.ReportProgress(iProgress, "Finished Batch " + from + "-" + to + " of " + GuarantorsToAllocate.Length + " Guarantors.");
        }
        DateTime EndTime = DateTime.Now;
        bw.ReportProgress(iProgress, "Batch Processing Ended at: " + String.Format("{0}h:{1}m.{2}", EndTime.Hour, EndTime.Minute, EndTime.Second));
        TimeSpan timeSpan1 = new TimeSpan(EndTime.Ticks - StartTime.Ticks);
        bw.ReportProgress(iProgress, "Batch Processing Total Time is: " + String.Format("{0}h:{1}m.{2}", timeSpan1.Hours, timeSpan1.Minutes, timeSpan1.Seconds));
        e.Result = "Completed Processing of all " + GuarantorsToAllocate.Length + " Guarantors.";
    }

    private boolean checkTableStatus() throws Exception {
        boolean rValOK_TO_RUN = false;
        if (!tableExists(TABLENAME))
        {
            DialogResult dr = MessageBox.Show(Lan.g(this,"The Table for holding provider split infomation generated\n" + "by MyAllocator1 does not exist." + "Creation of this table is required create set up allocation by provider\n" + "according to the rules in MyAllocator1.\n\n" + "Do you want to create this table?"), Lan.g(this,"Create Table"), MessageBoxButtons.YesNoCancel);
            if (dr == DialogResult.Yes)
            {
                Db.nonQOld(OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment.creatTableString(OpenDentBusiness.DatabaseType.MySql));
            }
             
        }
         
        rValOK_TO_RUN = tableExists(TABLENAME);
        return rValOK_TO_RUN;
    }

    /**
    * Simple utility method to tell you if a table exits or not.
    */
    private boolean tableExists(String tblName) throws Exception {
        boolean rvalExists = false;
        String cmd = "SHOW TABLES LIKE '" + tblName + "'";
        DataTable dt = Db.getTableOld(cmd);
        if (dt != null && dt.Rows.Count != 0 && dt.Columns.Count != 0)
        {
            if (StringSupport.equals(dt.Rows[0][0].ToString(), tblName))
                rvalExists = true;
             
        }
         
        return rvalExists;
    }

    /**
    * Returns a list of guarantors that require allocation.
    * Will look for a half finished job from previous.
    * Returns null if user does not want to continue a suspended job.
    */
    private int[] generate_GuarantorList_ToAllocate() throws Exception {
        int[] rValue = null;
        // Check status of an incomplete last run.  Did it complete well?
        String TempTableName = TABLENAME + "_temp";
        boolean TempTableExists = tableExists(TempTableName);
        int[] FullyProcessedGuarantors = null;
        int[] PartiallyProcessedGuarantors = null;
        List<int> OD_Guarantors = new List<int>();
        List<int> OD_Guarantors_NeedProcessing = null;
        // new List<int>();
        // Find fully processed Guarantors
        if (TempTableExists)
        {
            DialogResult dr = MessageBox.Show(Lan.g(this,"Processing was incomplete during last\n" + "run of the batch allocation process. Do you want to start over?\n" + "This will likely result in a loss of data but the data will\n" + "be rebuilt\n\nDO YOU WANT TO START OVER?"), "Warning", MessageBoxButtons.YesNo);
            if (dr == DialogResult.Yes)
            {
                // THEY Want to START OVER --DROP TABLE AND RECREATE
                String dropCommand = "DROP TABLE " + TABLENAME + "_temp";
                Db.nonQOld(dropCommand);
                dropCommand = "DROP TABLE " + TABLENAME;
                Db.nonQOld(dropCommand);
                Db.nonQOld(creatTableString(OpenDentBusiness.DatabaseType.MySql));
                Db.nonQOld(create_AP_temp_table_string(OpenDentBusiness.DatabaseType.MySql));
            }
             
            if (dr == DialogResult.No)
            {
                // Don't want to start over
                DialogResult dr2 = MessageBox.Show(Lan.g(this,"Do you want to continue from where you left of?\n" + "If you state no the allocation process will be aborted.\n\nContinue?"), Lan.g(this,"Continue?"), MessageBoxButtons.YesNo);
                if (dr2 == DialogResult.No)
                    return null;
                 
            }
             
        }
        else
        {
            // don't want to continue
            // Temp table does not exists so create it!
            Db.nonQOld(create_AP_temp_table_string(OpenDentBusiness.DatabaseType.MySql));
        } 
        //  Here is what needs to be done:
        //		1) Find Guarantors with incomplete processing
        //		2) Drop the entries in the allocation_provier table associated with this guarantor
        //		3) Set _temp table Status for this guarantor
        //		4) Generate a list of unprocessed guarantors.
        // continuing
        String cmd2 = "SELECT tempIndex, Guarantor, AllocStatus FROM " + TempTableName + " WHERE AllocStatus = " + (((Enum)ProcessingState.Started_and_Incomplete).ordinal()).ToString();
        DataTable dt = Db.getTableOld(cmd2);
        if (dt.Rows.Count != 0)
        {
            String deleteCommand1 = "DELETE FROM " + TABLENAME + " WHERE ";
            String updateCommand1 = "UPDATE " + TABLENAME + "_temp SET AllocStatus = " + (((Enum)ProcessingState.NeverStarted).ordinal()).ToString() + "\n WHERE ";
            PartiallyProcessedGuarantors = new int[dt.Rows.Count];
            for (int i = 0;i < dt.Rows.Count;i++)
            {
                PartiallyProcessedGuarantors[i] = Int32.Parse(dt.Rows[i][1].ToString());
                deleteCommand1 += "\nGuarantor = " + PartiallyProcessedGuarantors[i].ToString() + "\n";
                updateCommand1 += "\nGuarantor = " + PartiallyProcessedGuarantors[i].ToString() + "\n";
                if (i < dt.Rows.Count - 1)
                {
                    deleteCommand1 += "OR ";
                    updateCommand1 += "OR ";
                }
                 
            }
            Db.nonQOld(deleteCommand1);
            // deletes entries from allocation_provider
            Db.nonQOld(updateCommand1);
        }
         
        // updates status in allocation_provider_temp
        String ProccessedGuarantors_command = "SELECT Guarantor FROM " + TABLENAME + "_temp WHERE AllocStatus = " + (((Enum)ProcessingState.Complete).ordinal());
        DataTable dt3 = Db.getTableOld(ProccessedGuarantors_command);
        DataTable dtODGuarantors = Db.getTableOld("SELECT DISTINCT(Guarantor) FROM Patient");
        for (int i = 0;i < dtODGuarantors.Rows.Count;i++)
            OD_Guarantors.Add(Int32.Parse(dtODGuarantors.Rows[i][0].ToString()));
        if (dt3.Rows.Count != 0)
        {
            FullyProcessedGuarantors = new int[dt3.Rows.Count];
            OD_Guarantors_NeedProcessing = new List<int>();
            OD_Guarantors_NeedProcessing.AddRange(OD_Guarantors);
            for (int i = 0;i < dt3.Rows.Count;i++)
            {
                FullyProcessedGuarantors[i] = Int32.Parse(dt3.Rows[i][0].ToString());
                if (OD_Guarantors_NeedProcessing.Contains(FullyProcessedGuarantors[i]))
                    OD_Guarantors_NeedProcessing.Remove(FullyProcessedGuarantors[i]);
                 
            }
        }
        else
            OD_Guarantors_NeedProcessing = OD_Guarantors; 
        if (OD_Guarantors_NeedProcessing != null)
            rValue = OD_Guarantors_NeedProcessing.ToArray();
         
        return rValue;
    }

}


