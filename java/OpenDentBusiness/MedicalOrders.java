//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.LabPanel;
import OpenDentBusiness.LabPanels;
import OpenDentBusiness.LabResult;
import OpenDentBusiness.LabResults;
import OpenDentBusiness.Lans;
import OpenDentBusiness.MedicalOrder;
import OpenDentBusiness.MedicalOrderLineComparer;
import OpenDentBusiness.MedicalOrderType;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class MedicalOrders   
{
    /**
    * 
    */
    public static DataTable getOrderTable(long patNum, boolean includeDiscontinued) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), patNum, includeDiscontinued);
        }
         
        DataTable table = new DataTable("orders");
        DataRow row = new DataRow();
        table.Columns.Add("date");
        table.Columns.Add("DateTime", DateTime.class);
        table.Columns.Add("description");
        table.Columns.Add("MedicalOrderNum");
        table.Columns.Add("MedicationPatNum");
        table.Columns.Add("prov");
        table.Columns.Add("status");
        table.Columns.Add("type");
        List<DataRow> rows = new List<DataRow>();
        String command = "SELECT DateTimeOrder,Description,IsDiscontinued,MedicalOrderNum,MedOrderType,ProvNum " + "FROM medicalorder WHERE PatNum = " + POut.long(patNum);
        if (!includeDiscontinued)
        {
            //only include current orders
            command += " AND IsDiscontinued=0";
        }
         
        //false
        DataTable rawOrder = Db.getTable(command);
        DateTime dateT = new DateTime();
        MedicalOrderType medOrderType = MedicalOrderType.Laboratory;
        long medicalOrderNum = new long();
        boolean isDiscontinued = new boolean();
        for (int i = 0;i < rawOrder.Rows.Count;i++)
        {
            row = table.NewRow();
            dateT = PIn.DateT(rawOrder.Rows[i]["DateTimeOrder"].ToString());
            medOrderType = (MedicalOrderType)PIn.Int(rawOrder.Rows[i]["MedOrderType"].ToString());
            medicalOrderNum = PIn.Long(rawOrder.Rows[i]["MedicalOrderNum"].ToString());
            row["DateTime"] = dateT;
            row["date"] = dateT.ToShortDateString();
            row["description"] = PIn.String(rawOrder.Rows[i]["Description"].ToString());
            if (medOrderType == MedicalOrderType.Laboratory)
            {
                List<LabPanel> listPanelsForOrder = LabPanels.getPanelsForOrder(medicalOrderNum);
                for (int p = 0;p < listPanelsForOrder.Count;p++)
                {
                    row["description"] += "\r\n     ";
                    //new row for each panel
                    List<LabResult> listResults = LabResults.GetForPanel(listPanelsForOrder[p].LabPanelNum);
                    if (listResults.Count > 0)
                    {
                        row["description"] += listResults[0].DateTimeTest.ToShortDateString() + " - ";
                    }
                     
                    row["description"] += listPanelsForOrder[p].ServiceName;
                }
            }
             
            row["MedicalOrderNum"] = medicalOrderNum.ToString();
            row["MedicationPatNum"] = "0";
            row["prov"] = Providers.GetAbbr(PIn.Long(rawOrder.Rows[i]["ProvNum"].ToString()));
            isDiscontinued = PIn.Bool(rawOrder.Rows[i]["IsDiscontinued"].ToString());
            if (isDiscontinued)
            {
                row["status"] = "Discontinued";
            }
            else
            {
                row["status"] = "Active";
            } 
            row["type"] = medOrderType.ToString();
            rows.Add(row);
        }
        //Medications are deprecated for 2014 edition
        //MedicationPats
        //command="SELECT DateStart,DateStop,MedicationPatNum,CASE WHEN medication.MedName IS NULL THEN medicationpat.MedDescript ELSE medication.MedName END MedName,PatNote,ProvNum "
        //	+"FROM medicationpat "
        //	+"LEFT OUTER JOIN medication ON medication.MedicationNum=medicationpat.MedicationNum "
        //	+"WHERE PatNum = "+POut.Long(patNum);
        //if(!includeDiscontinued) {//exclude invalid orders
        //	command+=" AND DateStart > "+POut.Date(new DateTime(1880,1,1))+" AND PatNote !='' "
        //		+"AND (DateStop < "+POut.Date(new DateTime(1880,1,1))+" "//no date stop
        //		+"OR DateStop >= "+POut.Date(DateTime.Today)+")";//date stop hasn't happened yet
        //}
        //DataTable rawMed=Db.GetTable(command);
        //DateTime dateStop;
        //for(int i=0;i<rawMed.Rows.Count;i++) {
        //	row=table.NewRow();
        //	dateT=PIn.DateT(rawMed.Rows[i]["DateStart"].ToString());
        //	row["DateTime"]=dateT;
        //	if(dateT.Year<1880) {
        //		row["date"]="";
        //	}
        //	else {
        //		row["date"]=dateT.ToShortDateString();
        //	}
        //	row["description"]=PIn.String(rawMed.Rows[i]["MedName"].ToString())+", "
        //		+PIn.String(rawMed.Rows[i]["PatNote"].ToString());
        //	row["MedicalOrderNum"]="0";
        //	row["MedicationPatNum"]=rawMed.Rows[i]["MedicationPatNum"].ToString();
        //	row["prov"]=Providers.GetAbbr(PIn.Long(rawMed.Rows[i]["ProvNum"].ToString()));
        //	dateStop=PIn.Date(rawMed.Rows[i]["DateStop"].ToString());
        //	if(dateStop.Year<1880 || dateStop>DateTime.Today) {//not stopped or in the future
        //		row["status"]="Active";
        //	}
        //	else {
        //		row["status"]="Discontinued";
        //	}
        //	row["type"]="Medication";
        //	rows.Add(row);
        //}
        //Sorting-----------------------------------------------------------------------------------------
        rows.Sort(new MedicalOrderLineComparer());
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /*
    		///<summary></summary>
    		public static int GetCountMedical(long patNum){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
    				return Meth.GetInt(MethodBase.GetCurrentMethod(),patNum);
    			}
    			string command="SELECT COUNT(*) FROM medicalorder WHERE MedOrderType="+POut.Int((int)MedicalOrderType.Medication)+" "
    				+"AND PatNUm="+POut.Long(patNum);
    			return PIn.Int(Db.GetCount(command));
    		}*/
    /**
    * 
    */
    public static List<MedicalOrder> getAllLabs(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<MedicalOrder>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM medicalorder WHERE MedOrderType=" + POut.int(((Enum)MedicalOrderType.Laboratory).ordinal()) + " " + "AND PatNum=" + POut.long(patNum);
        return Crud.MedicalOrderCrud.SelectMany(command);
    }

    //NOT EXISTS(SELECT * FROM labpanel WHERE labpanel.MedicalOrderNum=medicalorder.MedicalOrderNum)";
    /**
    * 
    */
    public static List<MedicalOrder> getLabsByDate(long patNum, DateTime dateStart, DateTime dateStop) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<MedicalOrder>>GetObject(MethodBase.GetCurrentMethod(), patNum, dateStart, dateStop);
        }
         
        String command = "SELECT * FROM medicalorder WHERE MedOrderType=" + POut.int(((Enum)MedicalOrderType.Laboratory).ordinal()) + " " + "AND PatNum=" + POut.long(patNum) + " " + "AND DATE(DateTimeOrder) >= " + POut.date(dateStart) + " " + "AND DATE(DateTimeOrder) <= " + POut.date(dateStop);
        return Crud.MedicalOrderCrud.SelectMany(command);
    }

    //NOT EXISTS(SELECT * FROM labpanel WHERE labpanel.MedicalOrderNum=medicalorder.MedicalOrderNum)";
    /**
    * 
    */
    public static boolean labHasResultsAttached(long medicalOrderNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), medicalOrderNum);
        }
         
        String command = "SELECT COUNT(*) FROM labpanel WHERE MedicalOrderNum = " + POut.long(medicalOrderNum);
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
        else
        {
            return true;
        } 
    }

    /**
    * Gets one MedicalOrder from the db.
    */
    public static MedicalOrder getOne(long medicalOrderNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<MedicalOrder>GetObject(MethodBase.GetCurrentMethod(), medicalOrderNum);
        }
         
        return Crud.MedicalOrderCrud.SelectOne(medicalOrderNum);
    }

    /**
    * 
    */
    public static long insert(MedicalOrder medicalOrder) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            medicalOrder.MedicalOrderNum = Meth.GetLong(MethodBase.GetCurrentMethod(), medicalOrder);
            return medicalOrder.MedicalOrderNum;
        }
         
        return Crud.MedicalOrderCrud.Insert(medicalOrder);
    }

    /**
    * 
    */
    public static void update(MedicalOrder medicalOrder) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), medicalOrder);
            return ;
        }
         
        Crud.MedicalOrderCrud.Update(medicalOrder);
    }

    /**
    * 
    */
    public static void delete(long medicalOrderNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), medicalOrderNum);
            return ;
        }
         
        String command = new String();
        //validation
        command = "SELECT COUNT(*) FROM labpanel WHERE MedicalOrderNum=" + POut.long(medicalOrderNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("MedicalOrders","Not allowed to delete a lab order that has attached lab panels."));
        }
         
        //end of validation
        command = "DELETE FROM medicalorder WHERE MedicalOrderNum = " + POut.long(medicalOrderNum);
        Db.nonQ(command);
    }

}


