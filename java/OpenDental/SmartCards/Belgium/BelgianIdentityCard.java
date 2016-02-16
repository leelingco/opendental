//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards.Belgium;

import CS2JNet.JavaSupport.language.RefSupport;
import OpenDental.SmartCards.Belgium.CertifCheck;
import OpenDental.SmartCards.Belgium.CRLPolicyOptions;
import OpenDental.SmartCards.Belgium.IDData;
import OpenDental.SmartCards.Belgium.OCSPPolicyOptions;
import OpenDental.SmartCards.Belgium.Status;
import OpenDental.SmartCards.ISmartCardManager;
import OpenDental.SmartCards.SmartCardService;
import OpenDentBusiness.Patient;

public class BelgianIdentityCard  extends SmartCardService 
{
    public BelgianIdentityCard(ISmartCardManager manager) throws Exception {
        super(manager);
        SupportedAtrs.Add(new byte[]{ 0x3B, 0x98, 0x94, 0x40, 0x0A, 0xA5, 0x03, 0x01, 0x01, 0x01, 0xAD, 0x13, 0x10 });
        SupportedAtrs.Add(new byte[]{ 0x3B, 0x98, 0x13, 0x40, 0x0A, 0xA5, 0x03, 0x01, 0x01, 0x01, 0xAD, 0x13, 0x11 });
    }

    //
    // version info
    //
    private static final int INTERFACE_VERSION = 1;
    // Changes each time the interface is modified
    private static final int INTERFACE_COMPAT_VERSION = 1;
    // Stays until incompatible changes in existing functions
    public static final int MAX_CARD_NUMBER_LEN = 12;
    public static final int MAX_CHIP_NUMBER_LEN = 32;
    public static final int MAX_DATE_BEGIN_LEN = 10;
    public static final int MAX_DATE_END_LEN = 10;
    public static final int MAX_DELIVERY_MUNICIPALITY_LEN = 80;
    public static final int MAX_NATIONAL_NUMBER_LEN = 11;
    public static final int MAX_NAME_LEN = 110;
    public static final int MAX_FIRST_NAME1_LEN = 95;
    public static final int MAX_FIRST_NAME2_LEN = 50;
    public static final int MAX_FIRST_NAME3_LEN = 3;
    public static final int MAX_NATIONALITY_LEN = 3;
    public static final int MAX_BIRTHPLACE_LEN = 80;
    public static final int MAX_BIRTHDATE_LEN = 10;
    public static final int MAX_SEX_LEN = 1;
    public static final int MAX_NOBLE_CONDITION_LEN = 50;
    public static final int MAX_DOCUMENT_TYPE_LEN = 2;
    public static final int MAX_SPECIAL_STATUS_LEN = 2;
    public static final int MAX_HASH_PICTURE_LEN = 20;
    public static final int MAX_STREET_LEN = 80;
    public static final int MAX_STREET_NR = 10;
    public static final int MAX_STREET_BOX_NR = 6;
    public static final int MAX_ZIP_LEN = 4;
    public static final int MAX_MUNICIPALITY_LEN = 67;
    public static final int MAX_COUNTRY_LEN = 4;
    public static final int MAX_RAW_ADDRESS_LEN = 512;
    public static final int MAX_RAW_ID_LEN = 1024;
    public static final int MAX_PICTURE_LEN = 4096;
    public static final int MAX_CERT_LEN = 2048;
    public static final int MAX_CERT_NUMBER = 10;
    public static final int MAX_CERT_LABEL_LEN = 256;
    public static final int MAX_SIGNATURE_LEN = 256;
    public static final int MAX_CARD_DATA_LEN = 28;
    public static final int MAX_CARD_DATA_SIG_LEN = MAX_SIGNATURE_LEN + MAX_CARD_DATA_LEN;
    public static final int MAX_CHALLENGE_LEN = 20;
    public static final int MAX_RESPONSE_LEN = 128;
    private static final String EidLibDLL = "eidlib.dll";
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ Status initEx(String readerName, OCSPPolicyOptions ocsp, CRLPolicyOptions crl, RefSupport<IntPtr> cardHandle, int interfaceVersion, int interfaceCompVersion) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ Status exit() throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ Status getID(RefSupport<IDData> data, RefSupport<CertifCheck> check) throws Exception ;

    public Patient getPatientInfo(String reader) throws Exception {
        //
        // Define handle
        //
        IntPtr cardHandle = new IntPtr();
        //
        // Initialize Library
        //
        RefSupport<IntPtr> refVar___0 = new RefSupport<IntPtr>();
        Status returnStatus = initEx(reader,OCSPPolicyOptions.NotUsed,CRLPolicyOptions.NotUsed,refVar___0,1,1);
        cardHandle = refVar___0.getValue();
        // Read the ID from the card.
        IDData data = new IDData();
        CertifCheck check = new CertifCheck();
        RefSupport<IDData> refVar___1 = new RefSupport<IDData>();
        RefSupport<CertifCheck> refVar___2 = new RefSupport<CertifCheck>();
        returnStatus = getID(refVar___1,refVar___2);
        data = refVar___1.getValue();
        check = refVar___2.getValue();
        Patient patient = new Patient();
        String firstName1 = getString(data.FirstName1);
        String firstName2 = getString(data.FirstName2);
        String firstName3 = getString(data.FirstName3);
        String name = getString(data.Name);
        // Populate first name field.
        patient.FName = String.Empty;
        if (!String.IsNullOrEmpty(firstName1))
        {
            if (patient.FName.Length > 0)
                patient.FName += " ";
             
            patient.FName += firstName1;
        }
         
        if (!String.IsNullOrEmpty(firstName2))
        {
            if (patient.FName.Length > 0)
                patient.FName += " ";
             
            patient.FName += firstName2;
        }
         
        if (!String.IsNullOrEmpty(firstName3))
        {
            if (patient.FName.Length > 0)
                patient.FName += " ";
             
            patient.FName += firstName3;
        }
         
        // Populate last name field.
        patient.LName = name;
        exit();
        return patient;
    }

    private String getString(byte[] bytes) throws Exception {
        if (bytes == null)
            return null;
         
        if (bytes.Length == 0)
            return String.Empty;
         
        int length = bytes.Length;
        int count = 0;
        for (int i = 0;i < length;i++)
        {
            if (bytes[i] == '\0')
            {
                count = i;
                break;
            }
             
        }
        return Encoding.ASCII.GetString(bytes, 0, count);
    }

}


