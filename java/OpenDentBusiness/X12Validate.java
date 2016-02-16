//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Provider;

public class X12Validate   
{
    /**
    * StringBuilder does not get altered if no invalid data.
    */
    public static void iSA(Clearinghouse clearhouse, StringBuilder strb) throws Exception {
        if (!StringSupport.equals(clearhouse.ISA05, "01") && !StringSupport.equals(clearhouse.ISA05, "14") && !StringSupport.equals(clearhouse.ISA05, "20") && !StringSupport.equals(clearhouse.ISA05, "27") && !StringSupport.equals(clearhouse.ISA05, "28") && !StringSupport.equals(clearhouse.ISA05, "29") && !StringSupport.equals(clearhouse.ISA05, "30") && !StringSupport.equals(clearhouse.ISA05, "33") && !StringSupport.equals(clearhouse.ISA05, "ZZ"))
        {
            comma(strb);
            strb.Append("Clearinghouse ISA05");
        }
         
        if (!StringSupport.equals(clearhouse.SenderTIN, ""))
        {
            //if it IS blank, then we'll be using OD's info as the sender, so no need to validate the rest
            if (clearhouse.SenderTIN.Length < 2)
            {
                comma(strb);
                strb.Append("Clearinghouse SenderTIN");
            }
             
            if (StringSupport.equals(clearhouse.SenderName, ""))
            {
                //1000A NM103 min length=1
                comma(strb);
                strb.Append("Clearinghouse Sender Name");
            }
             
            if (!Regex.IsMatch(clearhouse.SenderTelephone, "^\\d{10}$"))
            {
                //1000A PER04 min length=1
                comma(strb);
                strb.Append("Clearinghouse Sender Phone");
            }
             
        }
         
        if (!StringSupport.equals(clearhouse.ISA07, "01") && !StringSupport.equals(clearhouse.ISA07, "14") && !StringSupport.equals(clearhouse.ISA07, "20") && !StringSupport.equals(clearhouse.ISA07, "27") && !StringSupport.equals(clearhouse.ISA07, "28") && !StringSupport.equals(clearhouse.ISA07, "29") && !StringSupport.equals(clearhouse.ISA07, "30") && !StringSupport.equals(clearhouse.ISA07, "33") && !StringSupport.equals(clearhouse.ISA07, "ZZ"))
        {
            comma(strb);
            strb.Append("Clearinghouse ISA07");
        }
         
        if (clearhouse.ISA08.Length < 2)
        {
            comma(strb);
            strb.Append("Clearinghouse ISA08");
        }
         
        if (!StringSupport.equals(clearhouse.ISA15, "T") && !StringSupport.equals(clearhouse.ISA15, "P"))
        {
            comma(strb);
            strb.Append("Clearinghouse ISA15");
        }
         
    }

    /**
    * StringBuilder does not get altered if no invalid data.
    */
    public static void carrier(Carrier carrier, StringBuilder strb) throws Exception {
        if (StringSupport.equals(carrier.Address.Trim(), ""))
        {
            comma(strb);
            strb.Append("Carrier Address");
        }
         
        if (carrier.City.Trim().Length < 2)
        {
            comma(strb);
            strb.Append("Carrier City");
        }
         
        if (carrier.State.Trim().Length != 2)
        {
            comma(strb);
            strb.Append("Carrier State(2 char)");
        }
         
        if (carrier.Zip.Trim().Length < 3)
        {
            comma(strb);
            strb.Append("Carrier Zip");
        }
         
    }

    /**
    * StringBuilder does not get altered if no invalid data.
    */
    public static void billProv(Provider billProv, StringBuilder strb) throws Exception {
        if (StringSupport.equals(billProv.LName, ""))
        {
            comma(strb);
            strb.Append("Billing Prov LName");
        }
         
        if (!billProv.IsNotPerson && StringSupport.equals(billProv.FName, ""))
        {
            //if is a person, first name cannot be blank.
            comma(strb);
            strb.Append("Billing Prov FName");
        }
         
        if (!Regex.IsMatch(billProv.SSN, "^[0-9]{9}$"))
        {
            //must be exactly 9 in length (no dashes)
            comma(strb);
            strb.Append("Billing Prov SSN/TIN must be a 9 digit number");
        }
         
        if (!Regex.IsMatch(billProv.NationalProvID, "^(80840)?[0-9]{10}$"))
        {
            comma(strb);
            strb.Append("Billing Prov NPI must be a 10 digit number with an optional prefix of 80840");
        }
         
        if (billProv.TaxonomyCodeOverride.Length > 0 && billProv.TaxonomyCodeOverride.Length != 10)
        {
            comma(strb);
            strb.Append("Billing Prov Taxonomy Code must be 10 characters");
        }
         
        //Always check provider name variables regardless of IsNotPerson.
        if (!billProv.IsNotPerson)
        {
            //The first name and middle name are only required if the billing provider is a person. For an organization, these fields are never sent.
            String fNameInvalidChars = getNonAN(billProv.FName);
            if (!StringSupport.equals(fNameInvalidChars, ""))
            {
                comma(strb);
                strb.Append("Billing Prov First Name contains invalid characters: " + fNameInvalidChars);
            }
             
            String middleInvalidChars = getNonAN(billProv.MI);
            if (!StringSupport.equals(middleInvalidChars, ""))
            {
                comma(strb);
                strb.Append("Billing Prov MI contains invalid characters: " + middleInvalidChars);
            }
             
        }
         
        String lNameInvalidChars = getNonAN(billProv.LName);
        if (!StringSupport.equals(lNameInvalidChars, ""))
        {
            comma(strb);
            strb.Append("Billing Prov Last Name contains invalid characters: " + lNameInvalidChars);
        }
         
    }

    /* This was causing problems when dummy providers were used for office and no license number was applicable.
    			 * Delta carriers key off this number and start assigning to wrong provider. Customer: ATD.
    			if(billProv.StateLicense=="") {
    				if(strb.Length!=0) {
    					strb.Append(",");
    				}
    				strb.Append("Billing Prov Lic #");
    			}*/
    public static void practiceAddress(StringBuilder strb) throws Exception {
        if (PrefC.getString(PrefName.PracticePhone).Length != 10)
        {
            //10 digit phone is required by WebMD and is universally assumed
            comma(strb);
            strb.Append("Practice Phone");
        }
         
        if (StringSupport.equals(PrefC.getString(PrefName.PracticeAddress).Trim(), ""))
        {
            comma(strb);
            strb.Append("Practice Address");
        }
         
        if (PrefC.getString(PrefName.PracticeCity).Trim().Length < 2)
        {
            comma(strb);
            strb.Append("Practice City");
        }
         
        if (PrefC.getString(PrefName.PracticeST).Trim().Length != 2)
        {
            comma(strb);
            strb.Append("Practice State(2 char)");
        }
         
        if (PrefC.getString(PrefName.PracticeZip).Trim().Length < 3)
        {
            comma(strb);
            strb.Append("Practice Zip");
        }
         
    }

    public static void billingAddress(StringBuilder strb) throws Exception {
        if (PrefC.getString(PrefName.PracticePhone).Length != 10)
        {
            //There is no billing phone, so the practice phone is sent electronically.
            //10 digit phone is required by WebMD and is universally assumed
            comma(strb);
            strb.Append("Practice Phone");
        }
         
        if (StringSupport.equals(PrefC.getString(PrefName.PracticeBillingAddress).Trim(), ""))
        {
            comma(strb);
            strb.Append("Billing Address");
        }
         
        if (PrefC.getString(PrefName.PracticeBillingCity).Trim().Length < 2)
        {
            comma(strb);
            strb.Append("Billing City");
        }
         
        if (PrefC.getString(PrefName.PracticeBillingST).Trim().Length != 2)
        {
            comma(strb);
            strb.Append("Billing State(2 char)");
        }
         
        if (PrefC.getString(PrefName.PracticeBillingZip).Trim().Length < 3)
        {
            comma(strb);
            strb.Append("Billing Zip");
        }
         
    }

    /**
    * Clinic passed in must not be null.
    */
    public static void clinic(Clinic clinic, StringBuilder strb) throws Exception {
        if (clinic.Phone.Length != 10)
        {
            //1000A PER04 min length=1.
            //But 10 digit phone is required in 2010AA and is universally assumed
            comma(strb);
            strb.Append("Clinic Phone");
        }
         
        if (StringSupport.equals(clinic.Address.Trim(), ""))
        {
            comma(strb);
            strb.Append("Clinic Address");
        }
         
        if (clinic.City.Trim().Length < 2)
        {
            comma(strb);
            strb.Append("Clinic City");
        }
         
        if (clinic.State.Trim().Length != 2)
        {
            comma(strb);
            strb.Append("Clinic State(2 char)");
        }
         
        if (clinic.Zip.Trim().Length < 3)
        {
            comma(strb);
            strb.Append("Clinic Zip");
        }
         
    }

    /**
    * Just subscriber address for now. Other fields (ex subscriber id) are checked elsewhere. We might want to move all subscriber checks here some day.
    */
    public static void subscriber(Patient subscriber, StringBuilder strb) throws Exception {
        if (StringSupport.equals(subscriber.Address.Trim(), ""))
        {
            comma(strb);
            strb.Append("Subscriber Address");
        }
         
        if (StringSupport.equals(subscriber.City.Trim(), ""))
        {
            comma(strb);
            strb.Append("Subscriber City");
        }
         
        if (StringSupport.equals(subscriber.State.Trim(), ""))
        {
            comma(strb);
            strb.Append("Subscriber State");
        }
         
        if (subscriber.Zip.Trim().Length < 3)
        {
            comma(strb);
            strb.Append("Subscriber Zip");
        }
         
    }

    /**
    * Just subscriber address for now. Other fields (ex subscriber id) are checked elsewhere. We might want to move all subscriber checks here some day.
    */
    public static void subscriber2(Patient subscriber2, StringBuilder strb) throws Exception {
        if (StringSupport.equals(subscriber2.Address.Trim(), ""))
        {
            comma(strb);
            strb.Append("Secondary Subscriber Address");
        }
         
        if (StringSupport.equals(subscriber2.City.Trim(), ""))
        {
            comma(strb);
            strb.Append("Secondary Subscriber City");
        }
         
        if (StringSupport.equals(subscriber2.State.Trim(), ""))
        {
            comma(strb);
            strb.Append("Secondary Subscriber State");
        }
         
        if (subscriber2.Zip.Trim().Length < 3)
        {
            comma(strb);
            strb.Append("Secondary Subscriber Zip");
        }
         
    }

    private static void comma(StringBuilder strb) throws Exception {
        if (strb.Length != 0)
        {
            strb.Append(",");
        }
         
    }

    /**
    * Returns a string containing all characters not in the Basic Character Set from the given input.  AN stands for alphanumeric.
    */
    private static String getNonAN(String str) throws Exception {
        String strUpper = str.ToUpper();
        //All strings in our X12s are set to uppercase.
        //Basic Character Set, includes those selected from the uppercase letters, digits, space, and special characters as specified below.
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"&'()*+,-./:;?= ";
        StringBuilder retVal = new StringBuilder();
        for (int i = 0;i < strUpper.Length;i++)
        {
            if (validChars.IndexOf(strUpper[i]) == -1)
            {
                //Not found.
                retVal.Append(strUpper[i]);
            }
             
        }
        return retVal.ToString();
    }

}


