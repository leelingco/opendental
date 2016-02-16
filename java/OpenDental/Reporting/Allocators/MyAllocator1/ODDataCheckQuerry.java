//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.ODDataCheckQuerry;

public class ODDataCheckQuerry   
{
    /**
    * Selects all payments, Adjustments, and charges
    */
    private static final String RawData = "SELECT \r\n" + 
    " \'2\' as Type, \r\n" + 
    "      patient.Guarantor, \r\n" + 
    "      patient.PatNum as Patient, \r\n" + 
    "      adjustment.ProvNum, \r\n" + 
    "      adjustment.ADjDate as Date, \r\n" + 
    "      adjustment.AdjAmt as Ammount, \r\n" + 
    "       Adjustment.AdjNum,\r\n" + 
    "       1 as ODTable\r\n" + 
    " FROM adjustment, patient \r\n" + 
    " WHERE \r\n" + 
    "      adjustment.PatNum = Patient.PatNum \r\n" + 
    "      && adjustment.AdjAmt <= 0\r\n" + 
    "\r\n" + 
    "    && adjustment.IsPulledToDansLedger = 0\r\n" + 
    " UNION ALL\r\n" + 
    "SELECT \r\n" + 
    " \'3\', \r\n" + 
    "      patient.Guarantor, \r\n" + 
    "      patient.PatNum, \r\n" + 
    "      adjustment.ProvNum, \r\n" + 
    "      adjustment.ADjDate, \r\n" + 
    "      adjustment.AdjAmt, \r\n" + 
    "       adjustment.AdjNum,\r\n" + 
    "       1\r\n" + 
    " FROM adjustment, patient \r\n" + 
    " WHERE \r\n" + 
    "      adjustment.PatNum = Patient.PatNum \r\n" + 
    "      && adjustment.AdjAmt > 0\r\n" + 
    "\r\n" + 
    "    && adjustment.IsPulledToDansLedger = 0\r\n" + 
    " UNION ALL\r\n" + 
    "SELECT \r\n" + 
    "      \'2\', \r\n" + 
    "      patient.Guarantor, \r\n" + 
    "      patient.PatNum, \r\n" + 
    "      claimproc.ProvNum, \r\n" + 
    "      claimpayment.CheckDate, \r\n" + 
    "      -(claimproc.WriteOff), \r\n" + 
    "       claimproc.ClaimProcNum,\r\n" + 
    "       2\r\n" + 
    " FROM claimpayment,claimproc, patient \r\n" + 
    " WHERE \r\n" + 
    "      claimproc.PatNum = Patient.PatNum \r\n" + 
    "      && claimproc.ClaimPaymentNum = claimpayment.ClaimPaymentNum \r\n" + 
    "      && (claimproc.Status = 1 OR Claimproc.Status = 4)\r\n" + 
    "\r\n" + 
    "    && claimproc.IsPulledToDansLedger = 0\r\n" + 
    " UNION ALL\r\n" + 
    "SELECT \r\n" + 
    "      \'1\', \r\n" + 
    "      patient.Guarantor, \r\n" + 
    "      patient.PatNum, \r\n" + 
    "      0, \r\n" + 
    "      paysplit.DatePay, \r\n" + 
    "      -paysplit.SplitAmt, \r\n" + 
    "       paysplit.SplitNum,\r\n" + 
    "       3\r\n" + 
    " FROM paysplit,patient \r\n" + 
    " WHERE \r\n" + 
    "      paysplit.PatNum = Patient.PatNum \r\n" + 
    "\r\n" + 
    "    && paysplit.IsPulledToDansLedger = 0\r\n" + 
    " UNION ALL\r\n" + 
    "SELECT \r\n" + 
    "      \'1\', \r\n" + 
    "      patient.Guarantor, \r\n" + 
    "      patient.PatNum, \r\n" + 
    "      0, \r\n" + 
    "      claimpayment.CheckDate, \r\n" + 
    "      -claimproc.InsPayAmt, \r\n" + 
    "       claimproc.ClaimProcNum,\r\n" + 
    "       2\r\n" + 
    " FROM claimproc,patient,claimpayment \r\n" + 
    " WHERE \r\n" + 
    "      claimproc.PatNum = Patient.PatNum \r\n" + 
    "      && claimpayment.ClaimPaymentNum = claimproc.ClaimPaymentNum \r\n" + 
    "\r\n" + 
    "    && claimproc.IsPulledToDansLedger = 0\r\n" + 
    " UNION ALL\r\n" + 
    "SELECT \r\n" + 
    "    0,\r\n" + 
    "    Patient.Guarantor,\r\n" + 
    "    Patient.PatNum,\r\n" + 
    "    Procedurelog.ProvNum,  \r\n" + 
    "    Procedurelog.ProcDate ,  \r\n" + 
    "    Procedurelog.ProcFee, \r\n" + 
    "    Procedurelog.ProcNum ,\r\n" + 
    "       0\r\n" + 
    "FROM \r\n" + 
    "     Procedurelog, Patient\r\n" + 
    "WHERE \r\n" + 
    "     Patient.PatNum = Procedurelog.Patnum \r\n" + 
    "     && Procedurelog.ProcStatus = 2\r\n" + 
    "\r\n" + 
    "    && Procedurelog.IsPulledToDansLedger = 0\r\n";
    /**
    * Fields Selected Include:
    * Patient.LName,
    * Patient.FName,
    * Patient.PatNum,
    * tbl1.Date,
    * tbl1.Ammount
    */
    public static final String CheckForNegPayments = "" + "SELECT Patient.LName, Patient.FName, Patient.PatNum, " + " tbl1.Date,tbl1.Ammount \nFROM ( " + ODDataCheckQuerry.RawData + " ) as tbl1, Patient \nWHERE Patient.PatNum = tbl1.Patient " + "\n&& tbl1.Type = '1' " + "\n&& tbl1.Ammount > 0";
    /**
    * Fields Selected Include:
    * Patient.LName,
    * Patient.FName,
    * Patient.PatNum,
    * tbl1.Date,
    * tbl1.Ammount
    */
    public static final String CheckForNegCharges = "" + "SELECT Patient.LName, Patient.FName, Patient.PatNum, " + " tbl1.Date,tbl1.Ammount\n FROM( " + ODDataCheckQuerry.RawData + " ) as tbl1, Patient\nWHERE Patient.PatNum = tbl1.Patient\n" + "\n&& tbl1.Type = '0' " + "\n&& tbl1.Ammount < 0";
    /**
    * Finds all the guarantors associated with Patients
    */
    public static String guarantorsOfPatient(uint[] Patients) throws Exception {
        String command = "SELECT DISTINCT(Patient.Guarantor) \nFROM Patient \nWHERE ";
        for (int i = 0;i < Patients.Length;i++)
        {
            command += "Patient.PatNum = " + Patients[i].ToString() + " ";
            if (i < Patients.Length - 1)
                command += " \n&& ";
             
        }
        return command;
    }

}


