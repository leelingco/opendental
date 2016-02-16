//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Benefits;
import OpenDentBusiness.Db;
import OpenDentBusiness.Employers;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.TrojanObject;

public class TrojanQueries   
{
    public static DataTable getMaxProcedureDate(long PatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), PatNum);
        }
         
        String command = "SELECT MAX(ProcDate) FROM procedurelog,patient\r\n" + 
        "\t\t\t\tWHERE patient.PatNum=procedurelog.PatNum\r\n" + 
        "\t\t\t\tAND patient.Guarantor=" + POut.long(PatNum);
        return Db.getTable(command);
    }

    public static DataTable getMaxPaymentDate(long PatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), PatNum);
        }
         
        String command = "SELECT MAX(DatePay) FROM paysplit,patient\r\n" + 
        "\t\t\t\tWHERE patient.PatNum=paysplit.PatNum\r\n" + 
        "\t\t\t\tAND patient.Guarantor=" + POut.long(PatNum);
        return Db.getTable(command);
    }

    /**
    * returns int32
    */
    public static int getUniqueFileNum() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT ValueString FROM preference WHERE PrefName='TrojanExpressCollectPreviousFileNumber'";
        DataTable table = Db.getTable(command);
        int previousNum = PIn.Int(table.Rows[0][0].ToString());
        int thisNum = previousNum + 1;
        command = "UPDATE preference SET ValueString='" + POut.Long(thisNum) + "' WHERE PrefName='TrojanExpressCollectPreviousFileNumber'" + " AND ValueString='" + POut.Long(previousNum) + "'";
        int result = Db.nonQ32(command);
        while (result != 1)
        {
            //someone else sent one at the same time
            previousNum++;
            thisNum++;
            command = "UPDATE preference SET ValueString='" + POut.Long(thisNum) + "' WHERE PrefName='TrojanExpressCollectPreviousFileNumber'" + " AND ValueString='" + POut.Long(previousNum) + "'";
            result = Db.nonQ32(command);
        }
        return thisNum;
    }

    /**
    * Get the list of records for the pending plan deletion report for plans that need to be brought to the patient's attention.
    */
    public static DataTable getPendingDeletionTable(Collection<String[]> deletePatientRecords) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), deletePatientRecords);
        }
         
        String whereTrojanID = "";
        for (int i = 0;i < deletePatientRecords.Count;i++)
        {
            if (i > 0)
            {
                whereTrojanID += "OR ";
            }
             
            whereTrojanID += "i.TrojanID='" + deletePatientRecords[i][0] + "' ";
        }
        String command = "SELECT DISTINCT " + "p.FName," + "p.LName," + "p.FName," + "p.LName," + "p.SSN," + "p.Birthdate," + "i.GroupNum," + "s.SubscriberID," + "i.TrojanID," + "CASE i.EmployerNum WHEN 0 THEN '' ELSE e.EmpName END," + "CASE i.EmployerNum WHEN 0 THEN '' ELSE e.Phone END," + "c.CarrierName," + "c.Phone " + "FROM patient p,insplan i,employer e,carrier c,inssub s " + "WHERE p.PatNum=s.Subscriber AND " + "(" + whereTrojanID + ") AND " + "i.CarrierNum=c.CarrierNum AND " + "s.PlanNum=i.PlanNum AND " + "(i.EmployerNum=e.EmployerNum OR i.EmployerNum=0) AND " + "(SELECT COUNT(*) FROM patplan a WHERE a.PatNum=p.PatNum AND a.InsSubNum=s.InsSubNum) > 0 " + "ORDER BY i.TrojanID,p.LName,p.FName";
        return Db.getTable(command);
    }

    /**
    * Get the list of records for the pending plan deletion report for plans which need to be bought to Trojan's attention.
    */
    public static DataTable getPendingDeletionTableTrojan(Collection<String[]> deleteTrojanRecords) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), deleteTrojanRecords);
        }
         
        String whereTrojanID = "";
        for (int i = 0;i < deleteTrojanRecords.Count;i++)
        {
            if (i > 0)
            {
                whereTrojanID += "OR ";
            }
             
            whereTrojanID += "i.TrojanID='" + deleteTrojanRecords[i][0] + "' ";
        }
        String command = "SELECT DISTINCT " + "p.FName," + "p.LName," + "p.FName," + "p.LName," + "p.SSN," + "p.Birthdate," + "i.GroupNum," + "s.SubscriberID," + "i.TrojanID," + "CASE i.EmployerNum WHEN 0 THEN '' ELSE e.EmpName END," + "CASE i.EmployerNum WHEN 0 THEN '' ELSE e.Phone END," + "c.CarrierName," + "c.Phone " + "FROM patient p,insplan i,employer e,carrier c,inssub s " + "WHERE p.PatNum=s.Subscriber AND " + "(" + whereTrojanID + ") AND " + "i.CarrierNum=c.CarrierNum AND " + "s.PlanNum=i.PlanNum AND " + "(i.EmployerNum=e.EmployerNum OR i.EmployerNum=0) AND " + "(SELECT COUNT(*) FROM patplan a WHERE a.PatNum=p.PatNum AND a.InsSubNum=s.InsSubNum) > 0 " + "ORDER BY i.TrojanID,p.LName,p.FName";
        return Db.getTable(command);
    }

    public static InsPlan getPlanWithTrojanID(String trojanID) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<InsPlan>GetObject(MethodBase.GetCurrentMethod(), trojanID);
        }
         
        String command = "SELECT * FROM insplan WHERE TrojanID = '" + POut.string(trojanID) + "'";
        return Crud.InsPlanCrud.SelectOne(command);
    }

    /**
    * This returns the number of plans affected.
    */
    public static void updatePlan(TrojanObject troj, long planNum, boolean updateBenefits) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), troj, planNum, updateBenefits);
            return ;
        }
         
        long employerNum = Employers.getEmployerNum(troj.ENAME);
        String command = new String();
        //for(int i=0;i<planNums.Count;i++) {
        command = "UPDATE insplan SET " + "EmployerNum=" + POut.long(employerNum) + ", " + "GroupName='" + POut.string(troj.PLANDESC) + "', " + "GroupNum='" + POut.string(troj.POLICYNO) + "', " + "CarrierNum= " + POut.long(troj.CarrierNum) + " " + "WHERE PlanNum=" + POut.long(planNum);
        Db.nonQ(command);
        command = "UPDATE inssub SET " + "BenefitNotes='" + POut.string(troj.BenefitNotes) + "' " + "WHERE PlanNum=" + POut.long(planNum);
        Db.nonQ(command);
        if (updateBenefits)
        {
            //clear benefits
            command = "DELETE FROM benefit WHERE PlanNum=" + POut.long(planNum);
            Db.nonQ(command);
            for (int j = 0;j < troj.BenefitList.Count;j++)
            {
                //benefitList
                troj.BenefitList[j].PlanNum = planNum;
                Benefits.Insert(troj.BenefitList[j]);
            }
            InsPlans.computeEstimatesForTrojanPlan(planNum);
        }
         
    }

}


