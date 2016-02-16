//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.Guarantors;

//using PaymentDistributor;using ManagedConnection;
/**
* Simple object that holds the Guarantor Patient Relationship
*/
public class Guarantors  extends IComparable 
{
    private int m_GuarantorNum = new int();
    private int[] m_Patients = new int[]();
    /**
    * Used to hold Guarantors that have a problem with compatability.
    * ie Payment is -ve or Charge is -ve
    */
    public static int[] ExcludedGuarantors = null;
    /**
    * See ExcludedGuarantors
    */
    public static int[] ExcludedPatients = null;
    public int[] getPATIENTS() throws Exception {
        return this.m_Patients;
    }

    public void setPATIENTS(int[] value) throws Exception {
        this.m_Patients = value;
    }

    public int getNumber() throws Exception {
        return this.m_GuarantorNum;
    }

    public void setNumber(int value) throws Exception {
        this.m_GuarantorNum = value;
    }

    /**
    * Should add NewPatient to the G1.PATIENTS.
    * if G1.PATIENTS already contains NewPatient then nothing is done.
    */

    /**
    * Should add NewPatient to the G1.PATIENTS.
    * if G1.PATIENTS already contains NewPatient then nothing is done.
    */

    /**
    * Basic method used in operator+  Basically helps generate the Guarantor Object.
    * 
    *  @param G1 
    *  @param NewPatient 
    *  @return
    */
    public static Guarantors add(Guarantors G1, int NewPatient) throws Exception {
        if (G1 == null)
            throw new Exception("Null reference passed to Guarnator Operator -dwk");
         
        if (G1.getPATIENTS() == null)
        {
            G1.setPATIENTS(new int[1]);
            G1.getPATIENTS()[0] = NewPatient;
            return G1;
        }
         
        for (int i = 0;i < G1.getPATIENTS().Length;i++)
            if (G1.getPATIENTS()[i] == NewPatient)
                return G1;
             
        // ignore the attempt to add
        int[] Temp1 = new int[G1.getPATIENTS().Length + 1];
        ArrayCopySmalltoLarge(G1.getPATIENTS(), Temp1);
        Temp1[Temp1.Length - 1] = NewPatient;
        G1.setPATIENTS(Temp1);
        return G1;
    }

    /**
    * Calls the first overload
    */
    public static Guarantors add(int NewPatient, Guarantors G1) throws Exception {
        return Guarantors.add(G1,NewPatient);
    }

    /**
    * Removes NewPatient from G1.PATIENTS if it NewPatient is present in G1.PATIENTS.
    */

    /**
    * Removes NewPatient from G1.PATIENTS if it NewPatient is present in G1.PATIENTS.
    */

    /**
    * Removes NewPatient from G1.PATIENTS if it NewPatient is present in G1.PATIENTS.
    */
    public static Guarantors subtract(int NewPatient, Guarantors G1) throws Exception {
        return subtract(G1,NewPatient);
    }

    /**
    * Removes NewPatient from G1.PATIENTS if it NewPatient is present in G1.PATIENTS.
    */
    public static Guarantors subtract(Guarantors G1, int NewPatient) throws Exception {
        if (G1 == null)
            throw new Exception("Operator - in Guarantors cannot apply to a null reference -dwk");
         
        int IndexToRemove = -1;
        if (G1.getPATIENTS() == null)
            return G1;
         
        for (int i = 0;i < G1.getPATIENTS().Length;i++)
        {
            // ie NewPatient is obviously not present
            if (G1.getPATIENTS()[i] == NewPatient)
                IndexToRemove = i;
             
        }
        if (IndexToRemove == -1)
            return G1;
         
        // NewPatient not present in G1.PATIENTS
        int[] Temp = new int[G1.getPATIENTS().Length - 1];
        int index = 0;
        for (int i = 0;i < G1.getPATIENTS().Length;i++)
        {
            if (i != IndexToRemove)
            {
                Temp[index] = G1.getPATIENTS()[i];
                index++;
            }
             
        }
        G1.setPATIENTS(Temp);
        return G1;
    }

    /**
    * Copies arrays from Smallersize to Larger Size
    * Leaving the last element(s) of the larger array empty
    * 
    *  @param ?
    */
    private static void arrayCopySmalltoLarge(int[] FromSmall, int[] ToLarge) throws Exception {
        if (FromSmall.Length > ToLarge.Length)
            throw new Exception("Smaller array is bigger than Larger array -dwk ");
         
        for (int i = 0;i < FromSmall.Length;i++)
        {
            ToLarge[i] = FromSmall[i];
        }
    }

    /**
    * Copies arrays from Smallersize to Larger Size
    * Dropping the last element(s) of the larger array empty
    * 
    *  @param ?
    */
    private static void arrayCopyLargetoSmall(int[] ToSmall, int[] FromLarge) throws Exception {
        if (ToSmall.Length > FromLarge.Length)
            throw new Exception("Smaller array is bigger than Larger array -dwk ");
         
        for (int i = 0;i < ToSmall.Length;i++)
        {
            ToSmall[i] = FromLarge[i];
        }
    }

    /**
    * Return 0 if Guarantors are identical
    * other wise returns comparison between guarantor numbers.
    * Throws exception if obj is not type Guarantor or
    * if obj == null
    * Note PATIENTS element of Guarantor must be in same order
    * for them to be counted as the same.
    * 
    *  @param obj 
    *  @return
    */
    public int compareTo(Object obj) throws Exception {
        Guarantors G1 = this;
        Guarantors G2 = null;
        if (obj instanceof Guarantors)
            G2 = (Guarantors)obj;
        else
            throw new Exception("Object passed to Guarantors.CompareTo(object) is not Type Guarantor -dwk"); 
        if (G2 == null)
            throw new Exception("Null reference passed to Guarantors.CompareTo(object). -dwk ");
         
        if (G1.getNumber() != G2.getNumber())
            return G1.getNumber().CompareTo(G2.getNumber());
         
        return compareArrays(G1.getPATIENTS(),G2.getPATIENTS());
    }

    // Guarantor Numbers Equal so compare arrays
    /**
    * Note Arrays must be identical not same elements in different order
    * 
    *  @param a1 
    *  @param a2 
    *  @return
    */
    private int compareArrays(int[] a1, int[] a2) throws Exception {
        if (a1 == null && a2 == null)
            return 0;
         
        if (a1 != null && a2 == null || a1 == null && a2 != null)
            return -1;
         
        if (a1.Length != a2.Length)
            return -1;
         
        for (int i = 0;i < a1.Length;i++)
            if (a1[i] != a2[i])
                return -1;
             
        return 0;
    }

    public static boolean guarantorIsInExcludedList(uint Guarantor) throws Exception {
        boolean found = false;
        if (ExcludedGuarantors != null)
        {
            for (int i = 0;i < ExcludedGuarantors.Length;i++)
            {
                if (ExcludedGuarantors[i] == Guarantor)
                {
                    found = true;
                    i = ExcludedGuarantors.Length;
                }
                 
            }
        }
         
        return found;
    }

    public void refresh() throws Exception {
    }

}


/**
* // 
* // How can items not be in OpenDental?  Good Question:
* //
* // What happens is that in OD the Guarantor relationship will change so that
* // the person becomes a guarantor of another person.  (Say a child was on their own
* // and got moved to their mother.)
* //
* // Example:
* //         Patient1  : 10001     Patient2  : 20001
* //         Guarantor1: 10001     Guarantor2: 20001
* //       Guarantor1 changed from its own guarantor to 20001 so now
* //         Patient1  : 10001     Patient2  : 20001
* //         Guarantor1: 20001     Guarantor2: 20001
* //
* // OpenDental:             DansLedger:
* //             100             99
* //             101             100
* //             102             101
* //             103             105
* //
* // returns:  99,105
* //
* // 
* // 
*  @return
*/
//public static uint[] GuarantorsNotInOD()
//{
//    string command =  "SELECT DISCTINCT(Guarantor) FROM Patient";
//    QueryResult r1 = QueryResult.RunQuery(command);
//    string command2 = "SELECT DISCTINCT(Guarantor) FROM " + TableUtility.AvailableTables.dansledgeroriginaldata.ToString();
//    QueryResult r2 = QueryResult.RunQuery(command2);
//    List<uint> GntorNotInOD = new List<uint>();
//    System.Collections.Hashtable htOD = new System.Collections.Hashtable();
//    if (r1.Success && r2.Success)
//    {
//        if (r1.DataTbl.Rows.Count != 0)
//        {
//            for (int i = 0; i < r1.DataTbl.Rows.Count; i++)
//                htOD[(uint)r1.DataTbl.Rows[i][0]] = r1.DataTbl.Rows[i][1];
//        }
//        foreach (System.Data.DataRow dr in r2.DataTbl.Rows)
//            if (!htOD.ContainsKey((uint)dr[0]))
//                GntorNotInOD.Add((uint)dr[0]);
//    }
//    return GntorNotInOD.ToArray();
//}
/**
* // 
* // How does this happen?
* //
* // Example:
* //
* // OpenDental:             DansLedger:
* //             100             99
* //             101             100
* //             102             101
* //             103             105
* //
* // returns:  102,103
* //
* // Happens when ever:
* //     1) New Guarantor is made
* //
* //     can happen when an existing patient changes from patient to guarantor status
* // 
* // 
*  @return
*/
//public static uint[] GuarantorsNotInDL()
//{
//    string command = "SELECT DISCTINCT(Guarantor) FROM Patient";
//    QueryResult r1 = MasterConnectionData.RunQuery(command);
//    string command2 = "SELECT DISCTINCT(Guarantor) FROM " + TableUtility.AvailableTables.dansledgeroriginaldata.ToString();
//    QueryResult r2 = MasterConnectionData.RunQuery(command2);
//    List<uint> GntorNotInDL = new List<uint>();
//    System.Collections.Hashtable htDL = new System.Collections.Hashtable();
//    if (r1.Success && r2.Success)
//    {
//        if (r2.DataTbl.Rows.Count != 0)
//        {
//            for (int i = 0; i < r2.DataTbl.Rows.Count; i++)
//                htDL[(uint)r2.DataTbl.Rows[i][0]] = r2.DataTbl.Rows[i][1];
//        }
//        foreach (System.Data.DataRow dr in r1.DataTbl.Rows)
//            if (!htDL.ContainsKey((uint)dr[0]))
//                GntorNotInDL.Add((uint)dr[0]);
//    }
//    return GntorNotInDL.ToArray();
//}