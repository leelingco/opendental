//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.X12Parse;
import OpenDentBusiness.X12Segment;

public class DTP271   
{
    public X12Segment Segment;
    private static Dictionary<String, String> DTP01 = new Dictionary<String, String>();
    public DTP271(X12Segment segment) throws Exception {
        Segment = segment;
    }

    public static String getDateStr(String qualifier, String date) throws Exception {
        if (StringSupport.equals(qualifier, "D8"))
        {
            //Segment.Get(2)=="D8") {//single date
            DateTime dt = X12Parse.toDate(date);
            return dt.ToShortDateString();
        }
        else
        {
            //Segment.Get(3));
            String[] strArray = date.Split('-');
            //Segment.Get(3).Split('-');
            DateTime dt1 = X12Parse.ToDate(strArray[0]);
            DateTime dt2 = X12Parse.ToDate(strArray[1]);
            return dt1.ToShortDateString() + "-" + dt2.ToShortDateString();
        } 
    }

    public static String getQualifierDescript(String code) throws Exception {
        if (DTP01 == null)
        {
            fillDictionaries();
        }
         
        if (!DTP01.ContainsKey(code))
        {
            return "";
        }
         
        return DTP01[code];
    }

    //Segment.Get(1)];
    private static void fillDictionaries() throws Exception {
        DTP01 = new Dictionary<String, String>();
        DTP01.Add("102", "Issue");
        DTP01.Add("152", "Effective Date of Change");
        DTP01.Add("193", "Period Start");
        DTP01.Add("194", "Period End");
        DTP01.Add("198", "Completion");
        DTP01.Add("290", "Coordination of Benefits");
        DTP01.Add("291", "Plan");
        DTP01.Add("292", "Benefit");
        DTP01.Add("295", "Primary Care Provider");
        DTP01.Add("304", "Latest Visit or Consultation");
        DTP01.Add("307", "Eligibility");
        DTP01.Add("318", "Added");
        DTP01.Add("340", "Consolidated Omnibus Budget Reconciliation Act (COBRA) Begin");
        DTP01.Add("341", "Consolidated Omnibus Budget Reconciliation Act (COBRA) End");
        DTP01.Add("342", "Premium Paid to Date Begin");
        DTP01.Add("343", "Premium Paid to Date End");
        DTP01.Add("346", "Plan Begin");
        DTP01.Add("347", "Plan End");
        DTP01.Add("348", "Benefit Begin");
        DTP01.Add("349", "Benefit End");
        DTP01.Add("356", "Eligibility Begin");
        DTP01.Add("357", "Eligibility End");
        DTP01.Add("382", "Enrollment");
        DTP01.Add("435", "Admission");
        DTP01.Add("442", "Date of Death");
        DTP01.Add("458", "Certification");
        DTP01.Add("472", "Service");
        DTP01.Add("539", "Policy Effective");
        DTP01.Add("540", "Policy Expiration");
        DTP01.Add("636", "Date of Last Update");
        DTP01.Add("771", "Status");
    }

}


