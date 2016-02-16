//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:31 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.StringSupport;
import OpenDental.Eclaims.CCDField;
import OpenDental.Eclaims.CCDFieldInputter;

public class CCDField   
{
    /**
    * Returns the length requirement for this field, possibly based on other fields.
    */
    private abstract static class LengthRequirement   
    {
        /**
        * Return the length required to input this field in bytes. Negative value indicates error.
        */
        public abstract int calcLength(CCDFieldInputter formData) throws Exception ;
    
    }

    private static class ConstLengthRequirement  extends LengthRequirement 
    {
        int len = new int();
        public ConstLengthRequirement(int length) throws Exception {
            len = length;
        }

        public int calcLength(CCDFieldInputter formData) throws Exception {
            return len;
        }
    
    }

    private static class LengthFromAnotherField  extends LengthRequirement 
    {
        String otherFieldId = new String();
        public LengthFromAnotherField(String pOtherFieldId) throws Exception {
            otherFieldId = pOtherFieldId;
        }

        public int calcLength(CCDFieldInputter formData) throws Exception {
            CCDField lengthField = formData.getFieldById(otherFieldId);
            if (lengthField == null)
            {
                return -1;
            }
             
            if (!Regex.IsMatch(lengthField.valuestr, "^[0-9]+$"))
            {
                MessageBox.Show(this.ToString() + ".CalcLength: Internal Error, cannot load field length from non-integer field value!");
                return -1;
            }
             
            return Convert.ToInt32(lengthField.valuestr);
        }
    
    }

    //Now the value string of the field is garanteed to be a valid numerical string.
    /**
    * This field does not exist when the other specified field does not exist. If this field does not exist, the returned length is 0, so that no error occurs, but also to avoid input.
    */
    private static class ConstLengthWhenOtherFieldExists  extends LengthRequirement 
    {
        String otherFieldId = new String();
        int valueWhenExists = new int();
        public ConstLengthWhenOtherFieldExists(String pOtherFieldId, int pValueWhenExists) throws Exception {
            otherFieldId = pOtherFieldId;
            valueWhenExists = pValueWhenExists;
        }

        public int calcLength(CCDFieldInputter formData) throws Exception {
            CCDField lengthField = formData.getFieldById(otherFieldId);
            if (lengthField == null)
            {
                return 0;
            }
             
            return valueWhenExists;
        }
    
    }

    private static class ConstLengthWhenOtherFieldHasValue  extends LengthRequirement 
    {
        String otherFieldId = new String();
        String otherFieldValue = new String();
        int valueWhenExists = new int();
        public ConstLengthWhenOtherFieldHasValue(String pOtherFieldId, String pOtherFieldValue, int pValueWhenExists) throws Exception {
            otherFieldId = pOtherFieldId;
            otherFieldValue = pOtherFieldValue;
            valueWhenExists = pValueWhenExists;
        }

        public int calcLength(CCDFieldInputter formData) throws Exception {
            CCDField lengthField = formData.getFieldById(otherFieldId);
            if (lengthField == null)
            {
                return -1;
            }
             
            if (StringSupport.equals(lengthField.valuestr, otherFieldValue))
            {
                return valueWhenExists;
            }
             
            return 0;
        }
    
    }

    /**
    * Ensure that the input value meets a requirement.
    */
    private abstract static class ValueRequirement   
    {
        public abstract boolean meetsRequirement(CCDFieldInputter formData, String value) throws Exception ;
    
    }

    private static class DiscreteValueRequirement  extends ValueRequirement 
    {
        private String[] acceptedValues = new String[]();
        public DiscreteValueRequirement(String[] pAcceptedValues) throws Exception {
            acceptedValues = pAcceptedValues;
        }

        public boolean meetsRequirement(CCDFieldInputter formData, String value) throws Exception {
            for (int i = 0;i < acceptedValues.Length;i++)
            {
                if (StringSupport.equals(value, acceptedValues[i]))
                {
                    return true;
                }
                 
            }
            return false;
        }
    
    }

    private static class DiscreteValueExclusionRequirement  extends ValueRequirement 
    {
        private String[] excludedValues = new String[]();
        public DiscreteValueExclusionRequirement(String[] pExcludedValues) throws Exception {
            excludedValues = pExcludedValues;
        }

        public boolean meetsRequirement(CCDFieldInputter formData, String value) throws Exception {
            for (int i = 0;i < excludedValues.Length;i++)
            {
                if (StringSupport.equals(value, excludedValues[i]))
                {
                    return false;
                }
                 
            }
            return true;
        }
    
    }

    private static class RangeValueRequirement  extends ValueRequirement 
    {
        int minVal = new int();
        int maxVal = new int();
        public RangeValueRequirement(int pMinVal, int pMaxVal) throws Exception {
            minVal = pMinVal;
            maxVal = pMaxVal;
        }

        public boolean meetsRequirement(CCDFieldInputter formData, String value) throws Exception {
            if (!Regex.IsMatch(value, "^[0-9]+$"))
            {
                MessageBox.Show(this.ToString() + ".MeetsRequirements: Internal Error, cannot check range requirement against " + "non-integer value '" + value + "'");
                return false;
            }
             
            int numVal = Convert.ToInt32(value);
            return (numVal >= minVal && numVal <= maxVal);
        }
    
    }

    private static class RegexValueRequirement  extends ValueRequirement 
    {
        String pattern = new String();
        public RegexValueRequirement(String pPattern) throws Exception {
            pattern = pPattern;
        }

        public boolean meetsRequirement(CCDFieldInputter formData, String value) throws Exception {
            return Regex.IsMatch(value, pattern);
        }
    
    }

    /**
    * A many to many mapping of discrete values.
    */
    public static class ValueMap   
    {
        public String[] inputValues = new String[]();
        //The values expected in the other field.
        public String[] discreteValues = new String[]();
        //The discrete values allowed for the inputValue of the other field.
        public ValueMap(String[] pInputValues, String[] pDiscreteValues) throws Exception {
            inputValues = pInputValues;
            discreteValues = pDiscreteValues;
        }
    
    }

    private static class DiscreteValuesBasedOnOtherField  extends ValueRequirement 
    {
        String otherFieldId = new String();
        ValueMap[] valueMaps = new ValueMap[]();
        public DiscreteValuesBasedOnOtherField(String pOtherFieldId, ValueMap[] pValueMaps) throws Exception {
            otherFieldId = pOtherFieldId;
            valueMaps = pValueMaps;
        }

        public boolean meetsRequirement(CCDFieldInputter formData, String value) throws Exception {
            CCDField otherField = formData.getFieldById(otherFieldId);
            if (otherField == null)
            {
                MessageBox.Show(this.ToString() + ".MeetsRequirement: Internal error, field id " + otherFieldId + " does not exist!");
            }
             
            for (Object __dummyForeachVar2 : valueMaps)
            {
                ValueMap valMap = (ValueMap)__dummyForeachVar2;
                for (Object __dummyForeachVar1 : valMap.inputValues)
                {
                    String valstr = (String)__dummyForeachVar1;
                    if (StringSupport.equals(otherField.valuestr, valstr))
                    {
                        for (Object __dummyForeachVar0 : valMap.discreteValues)
                        {
                            String str = (String)__dummyForeachVar0;
                            if (StringSupport.equals(value, str))
                            {
                                return true;
                            }
                             
                        }
                    }
                     
                }
            }
            return false;
        }
    
    }

    private LengthRequirement lengthRequirement;
    //Used to determine the length of the field for input.
    private List<ValueRequirement> valueRequirements = new List<ValueRequirement>();
    //Used to check the value of the field during the input process.
    public String fieldId = new String();
    public String fieldName = new String();
    public String frenchFieldName = new String();
    //The equivalent of the fieldName in French.
    public String format = new String();
    public String valuestr = new String();
    //null indicates unused. Final value set elsewhere.
    public CCDField(CCDField RHS) throws Exception {
        this.fieldId = RHS.fieldId;
        this.fieldName = RHS.fieldName;
        this.frenchFieldName = RHS.frenchFieldName;
        this.format = RHS.format;
        this.valuestr = RHS.valuestr;
    }

    public String getFieldName(boolean useFrench) throws Exception {
        if (useFrench && frenchFieldName != null)
        {
            return frenchFieldName;
        }
        else
        {
            return fieldName;
        } 
    }

    public static boolean isValidId_v2(String str) throws Exception {
        if (str.Length == 3 && str[0] >= 'A' && str[0] <= 'G' && str[1] >= '0' && str[1] <= '9' && str[2] >= '0' && str[2] <= '9')
        {
            int num = Convert.ToInt32(str.Substring(1, 2));
            if (num < 1)
            {
                return false;
            }
             
            System.String.INDEXER __dummyScrutVar0 = str[0];
            if (__dummyScrutVar0.equals(('A')))
            {
                return num <= 8;
            }
            else if (__dummyScrutVar0.equals(('B')))
            {
                return num <= 2;
            }
            else if (__dummyScrutVar0.equals(('C')))
            {
                return num <= 11;
            }
            else if (__dummyScrutVar0.equals(('D')))
            {
                return num <= 10;
            }
            else if (__dummyScrutVar0.equals(('E')))
            {
                return num <= 6;
            }
            else if (__dummyScrutVar0.equals(('F')))
            {
                return num <= 15;
            }
            else if (__dummyScrutVar0.equals(('G')))
            {
                return num <= 30;
            }
                   
        }
         
        return false;
    }

    public static boolean isValidId_v4(String str) throws Exception {
        if (str.Length == 3 && str[0] >= 'A' && str[0] <= 'G' && str[1] >= '0' && str[1] <= '9' && str[2] >= '0' && str[2] <= '9')
        {
            int num = Convert.ToInt32(str.Substring(1, 2));
            if (num < 1)
            {
                return false;
            }
             
            System.String.INDEXER __dummyScrutVar1 = str[0];
            if (__dummyScrutVar1.equals(('A')))
            {
                return num <= 11;
            }
            else if (__dummyScrutVar1.equals(('B')))
            {
                return num <= 8;
            }
            else if (__dummyScrutVar1.equals(('C')))
            {
                return num <= 19;
            }
            else if (__dummyScrutVar1.equals(('D')))
            {
                return num <= 11;
            }
            else if (__dummyScrutVar1.equals(('E')))
            {
                return num <= 20;
            }
            else if (__dummyScrutVar1.equals(('F')))
            {
                return num <= 49;
            }
            else if (__dummyScrutVar1.equals(('G')))
            {
                return num <= 62;
            }
                   
        }
         
        return false;
    }

    /**
    * Returns the length required to input the field. This value may depend on other fields. Negative return value indicates error.
    */
    public int getRequiredLength(CCDFieldInputter formData) throws Exception {
        return lengthRequirement.calcLength(formData);
    }

    /**
    * Checks to be sure that the value string set in this field meets the value requirements. This function can depend on the values of other form fields.
    */
    public boolean checkValue(CCDFieldInputter formData, String value) throws Exception {
        if (value == null)
        {
            return false;
        }
         
        for (Object __dummyForeachVar3 : valueRequirements)
        {
            //Db format is correct. Now check specific values.
            ValueRequirement valReq = (ValueRequirement)__dummyForeachVar3;
            if (!valReq.meetsRequirement(formData,value))
            {
                return false;
            }
             
        }
        return true;
    }

    /**
    * Create a CCD field using the field id. A field id uniquely identifies the field type, contents, etc...
    */
    public CCDField(String pFieldId, boolean isVersion02) throws Exception {
        pFieldId = pFieldId.ToUpper();
        if (isVersion02)
        {
            if (!isValidId_v2(pFieldId))
            {
                if (isValidId_v4(pFieldId))
                {
                    setValuesUsingFieldId_v4(pFieldId);
                }
                else
                {
                    MessageBox.Show("Cannot construct version 2 field with invalid field id: " + pFieldId);
                    return ;
                } 
            }
            else
            {
                setValuesUsingFieldId_v2(pFieldId);
            } 
        }
        else
        {
            if (!isValidId_v4(pFieldId))
            {
                MessageBox.Show("Cannot construct version 4 field with invalid field id: " + pFieldId);
                return ;
            }
             
            setValuesUsingFieldId_v4(pFieldId);
        } 
        fieldId = pFieldId;
        format = format.ToUpper();
        //just in case an error is made above
        valuestr = null;
        //Must be checked and set later.
        System.String __dummyScrutVar2 = format;
        if (__dummyScrutVar2.equals("N"))
        {
            valueRequirements.Add(new RegexValueRequirement("^[0-9]+$"));
        }
        else if (__dummyScrutVar2.equals("A"))
        {
            valueRequirements.Add(new RegexValueRequirement("^[a-zA-Z\'\\-,]+$"));
        }
        else if (__dummyScrutVar2.equals("AE"))
        {
        }
        else //valueRequirements.Add(new RegexValueRequirement(@"^[a-zA-Z'\-,\x80-\xFF]+$"));
        if (__dummyScrutVar2.equals("A/N"))
        {
        }
        else //We consider character values less than 128 printable if they are in the range 32-126 (0x20-0x7E) inclusive.
        //http://msdn2.microsoft.com/en-us/library/60ecse8t(VS.80).aspx
        //valueRequirements.Add(new RegexValueRequirement(@"^[\x20-\x7E]+$"));
        if (__dummyScrutVar2.equals("AE/N"))
        {
        }
        else //valueRequirements.Add(new RegexValueRequirement(@"^[\x20-\x7E\x80-\xFF]+$"));
        if (__dummyScrutVar2.equals("D"))
        {
            valueRequirements.Add(new RegexValueRequirement("^[+-]?[0-9]+$"));
        }
        else
        {
            MessageBox.Show(this.ToString() + ".CCDField: Internal error, unrecognized field format: " + format);
        }      
    }

    private void setValuesUsingFieldId_v2(String pFieldId) throws Exception {
        System.String __dummyScrutVar3 = pFieldId;
        if (__dummyScrutVar3.equals("A01"))
        {
            fieldName = "Transaction Prefix";
            frenchFieldName = "Préfixe de transaction";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(12);
        }
        else if (__dummyScrutVar3.equals("A02"))
        {
            //Provider's Sequence Number
            fieldName = "DENTAL OFFICE CLAIM REFERENCE NO";
            frenchFieldName = "NO DE TRANSACTION DU CABINET";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar3.equals("A03"))
        {
            fieldName = "Format Version Number";
            frenchFieldName = "Nombre de version de format";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "02", "04" }));
        }
        else if (__dummyScrutVar3.equals("A04"))
        {
            fieldName = "Transaction Code";
            frenchFieldName = "Code de transaction";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "00", "10", "01", "11", "21", "31", "02", "12", "03", "13", "23", "04" }));
        }
        else if (__dummyScrutVar3.equals("A05"))
        {
            fieldName = "Carrier Identification Number";
            frenchFieldName = "Numéro d'identification de porteur";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar3.equals("A06"))
        {
            fieldName = "Software System ID";
            frenchFieldName = "Système logiciel identification";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(3);
        }
        else if (__dummyScrutVar3.equals("A07"))
        {
            fieldName = "Message Length";
            frenchFieldName = "Longueur de message";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(4);
        }
        else if (__dummyScrutVar3.equals("A08"))
        {
            fieldName = "E-Mail Flag";
            frenchFieldName = "Drapeau E-Mail";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "0", "1", "2" }));
        }
        else if (__dummyScrutVar3.equals("B01"))
        {
            //CDA Provider Number
            fieldName = "UNIQUE ID NO";
            frenchFieldName = "NO DU DENTISTE";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(9);
        }
        else if (__dummyScrutVar3.equals("B02"))
        {
            //Provider Office Number
            fieldName = "OFFICE NO";
            frenchFieldName = "NO DU CABINET";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(4);
        }
        else if (__dummyScrutVar3.equals("C01"))
        {
            //Primary Policy/Plan Number
            fieldName = "POLICY#";
            frenchFieldName = "NO DE POLICE";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar3.equals("C02"))
        {
            //Subscriber Identification Number
            fieldName = "CERTIFICATE NO";
            frenchFieldName = "NO DE CERTIFICAT";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(11);
        }
        else if (__dummyScrutVar3.equals("C03"))
        {
            fieldName = "Relationship Code";
            frenchFieldName = "Code de rapport";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,5));
        }
        else if (__dummyScrutVar3.equals("C04"))
        {
            fieldName = "Patient's Sex";
            frenchFieldName = "Le sexe du patient";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "M", "F" }));
        }
        else if (__dummyScrutVar3.equals("C05"))
        {
            fieldName = "Patient's Birthday";
            frenchFieldName = "L'anniversaire du patient";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar3.equals("C06"))
        {
            fieldName = "Patient's Last Name";
            frenchFieldName = "Le dernier nom du patient";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(25);
        }
        else if (__dummyScrutVar3.equals("C07"))
        {
            fieldName = "Patient's First Name";
            frenchFieldName = "Le prénom du patient";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(15);
        }
        else if (__dummyScrutVar3.equals("C08"))
        {
            fieldName = "Patient's Middle Initial";
            frenchFieldName = "L'initiale moyenne du patient";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
        }
        else if (__dummyScrutVar3.equals("C09"))
        {
            fieldName = "Eligibility Exception Code";
            frenchFieldName = "Code d'exception d'acceptabilité";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(0,3));
        }
        else if (__dummyScrutVar3.equals("C10"))
        {
            fieldName = "Name of School";
            frenchFieldName = "Nom d'école";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(25);
        }
        else if (__dummyScrutVar3.equals("C11"))
        {
            fieldName = "DIVISION/SECTION NO";
            frenchFieldName = "NO DE DIVISION/SECTION";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(10);
        }
        else if (__dummyScrutVar3.equals("D01"))
        {
            fieldName = "Subscriber's Birthday";
            frenchFieldName = "L'anniversaire de l'abonné";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar3.equals("D02"))
        {
            fieldName = "Subscriber's Last Name";
            frenchFieldName = "Le dernier nom de l'abonné";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(25);
        }
        else if (__dummyScrutVar3.equals("D03"))
        {
            fieldName = "Subscriber's First Name";
            frenchFieldName = "Le prénom de l'abonné";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(15);
        }
        else if (__dummyScrutVar3.equals("D04"))
        {
            fieldName = "Subscriber's Middle Initial";
            frenchFieldName = "L'initiale moyenne de l'abonné";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
        }
        else if (__dummyScrutVar3.equals("D05"))
        {
            fieldName = "Subscriber's Address Line 1";
            frenchFieldName = "Ligne 1 de l'adresse de l'abonné";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(30);
        }
        else if (__dummyScrutVar3.equals("D06"))
        {
            fieldName = "Subscriber's Address Line 2";
            frenchFieldName = "Ligne 2 de l'adresse de l'abonné";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(30);
        }
        else if (__dummyScrutVar3.equals("D07"))
        {
            fieldName = "Subscriber's City";
            frenchFieldName = "La ville de l'abonné";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(20);
        }
        else if (__dummyScrutVar3.equals("D08"))
        {
            fieldName = "Subscriber's Province/State Code";
            frenchFieldName = "Code de la province/état de l'abonné";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(2);
            //Includes US states and Canadian provinces.
            //http://www.nrcan.gc.ca/earth-sciences/geography-boundary/geographical-name/translators/5782
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT", "LB", "NF", "PQ", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY" }));
        }
        else //Canadian province codes.
        //Alberta
        //Britich Columbia
        //Manitoba
        //New Brunswick
        //Newfoundland and Labrador
        //Nova Scotia
        //Northwest Territories
        //Nunavut
        //Ontario
        //Prince Edward Island
        //Quebec
        //Saskatchewan
        //Yukon
        //Traditional Canadian province codes which somehow made it into our application, but we are going to leave them because they are probably harmless.
        //Newfoundland and Labrador - This appeared in Canada Post publications (e.g., The Canadian Postal Code Directory) for the mainland section of the province of Newfoundland and Labrador.
        //Newfoundland and Labrador - Nfld. and later NF (the two-letter abbreviation used before the province's name changed to Newfoundland and Labrador) and T.-N. (French version, for Terre-Neuve)
        //Quebec	- Que. and P.Q. (French version, for Province du Québec); later, PQ evolved from P.Q. as the first two-letter non-punctuated abbreviation.
        //US state codes.
        if (__dummyScrutVar3.equals("D09"))
        {
            fieldName = "Subscriber's Postal/ZIP Code";
            frenchFieldName = "Code du Postal/ZIP de l'abonné";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar3.equals("D10"))
        {
            fieldName = "Language of the Insured";
            frenchFieldName = "Langue des assurés";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "E", "F" }));
        }
        else if (__dummyScrutVar3.equals("E01"))
        {
            fieldName = "Secondary Carrier Unique ID Number";
            frenchFieldName = "Nombre unique d'identification de porteur secondaire";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar3.equals("E02"))
        {
            fieldName = "Secondary Policy/Plan";
            frenchFieldName = "Politique/plan secondaires";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar3.equals("E03"))
        {
            fieldName = "Secondary Plan Subscriber ID";
            frenchFieldName = "Identification secondaire d'abonné de plan";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(11);
        }
        else if (__dummyScrutVar3.equals("E04"))
        {
            //Spouse/Significant Other Birtday
            fieldName = "Secondary Subscriber's Birthday";
            frenchFieldName = "L'anniversaire de l'abonné secondaire";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar3.equals("E05"))
        {
            fieldName = "Secondary Division/Section Number";
            frenchFieldName = "Nombre secondaire de Division/section";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(10);
        }
        else if (__dummyScrutVar3.equals("E06"))
        {
            fieldName = "Secondary Relationship Code";
            frenchFieldName = "Code secondaire de rapport";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,5));
        }
        else if (__dummyScrutVar3.equals("F01"))
        {
            fieldName = "Payee Code";
            frenchFieldName = "Code de bénéficiaire";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(0,2));
        }
        else if (__dummyScrutVar3.equals("F02"))
        {
            fieldName = "Accident Date";
            frenchFieldName = "Date d'accidents";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar3.equals("F03"))
        {
            fieldName = "Predetermination Number";
            frenchFieldName = "Nombre de prédétermination";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(14);
        }
        else if (__dummyScrutVar3.equals("F04"))
        {
            fieldName = "Date of Initial Placement Upper";
            frenchFieldName = "Date de haut initial de placement";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar3.equals("F05"))
        {
            fieldName = "Treatment Required for orthodontic";
            frenchFieldName = "Traitement requis pour orthodontique";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "Y", "N" }));
        }
        else if (__dummyScrutVar3.equals("F06"))
        {
            fieldName = "Number of Procedures Performed";
            frenchFieldName = "Nombre de procédures exécutées";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,7));
        }
        else if (__dummyScrutVar3.equals("F07"))
        {
            fieldName = "Procedure Line Number";
            frenchFieldName = "Ligne nombre de procédé";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(0,7));
        }
        else if (__dummyScrutVar3.equals("F08"))
        {
            fieldName = "Procedure Code";
            frenchFieldName = "Code de procédé";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar3.equals("F09"))
        {
            fieldName = "Date of Service";
            frenchFieldName = "Date de service";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar3.equals("F10"))
        {
            fieldName = "International Tooth,Sextant, Quad or Arch";
            frenchFieldName = "Dent, sextant, quadruple ou voûte international";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar3.equals("F11"))
        {
            fieldName = "Tooth Surface";
            frenchFieldName = "Surface de dent";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(5);
            valueRequirements.Add(new RegexValueRequirement("^[MOIDBLV ]+$"));
        }
        else if (__dummyScrutVar3.equals("F12"))
        {
            fieldName = "Dentist's Fee Claimed";
            frenchFieldName = "Les honoraires du dentiste réclamés";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar3.equals("F13"))
        {
            fieldName = "Lab Procedure Fee # 1";
            frenchFieldName = "Honoraires # 1 de procédé de laboratoire";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar3.equals("F14"))
        {
            fieldName = "Unit of Time";
            frenchFieldName = "Unité de temps";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(4);
        }
        else if (__dummyScrutVar3.equals("F15"))
        {
            fieldName = "Is this an Initial Placement Upper";
            frenchFieldName = "Est c'un premier haut de placement";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "Y", "N", "X" }));
        }
        else if (__dummyScrutVar3.equals("G01"))
        {
            //Transaction Reference Number
            fieldName = "CARRIER CLAIM NO";
            frenchFieldName = "NO DE RÉFÉRENCE DE TRANSACTION";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(14);
        }
        else if (__dummyScrutVar3.equals("G02"))
        {
            fieldName = "Employer Certified Flag";
            frenchFieldName = "";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "Y", "N" }));
        }
        else if (__dummyScrutVar3.equals("G03"))
        {
            fieldName = "Expected Payment Date";
            frenchFieldName = "Date prévue de paiement";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar3.equals("G04"))
        {
            fieldName = "Total Amount of Service";
            frenchFieldName = "Montant total de service";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(7);
        }
        else if (__dummyScrutVar3.equals("G05"))
        {
            fieldName = "Response Status";
            frenchFieldName = "Statut de réponse";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "A", "E", "R", "H", "B", "C", "N", "M", "X" }));
        }
        else if (__dummyScrutVar3.equals("G06"))
        {
            fieldName = "Number of Error Codes";
            frenchFieldName = "Nombre de codes d'erreur";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new RangeValueRequirement(0,10));
        }
        else if (__dummyScrutVar3.equals("G07"))
        {
            //Disposition message
            fieldName = "DISPOSITION";
            frenchFieldName = "SPÉCIFICATIONS";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(75);
        }
        else if (__dummyScrutVar3.equals("G08"))
        {
            fieldName = "Error Code";
            frenchFieldName = "Code d'erreur";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(3);
        }
        else if (__dummyScrutVar3.equals("G09"))
        {
            fieldName = "E-Mail Flag";
            frenchFieldName = "Drapeau E-Mail";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "0", "1", "2" }));
        }
        else if (__dummyScrutVar3.equals("G10"))
        {
            fieldName = "Number of Carrier Issued Procedure Codes";
            frenchFieldName = "Le nombre de porteur a publié des codes de procédé";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(0,6));
        }
        else if (__dummyScrutVar3.equals("G11"))
        {
            fieldName = "Number of Note Lines";
            frenchFieldName = "Nombre de lignes de note";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new RangeValueRequirement(0,12));
        }
        else if (__dummyScrutVar3.equals("G12"))
        {
            fieldName = "Eligible Amount";
            frenchFieldName = "Quantité éligible";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar3.equals("G13"))
        {
            fieldName = "Deductible Amount";
            frenchFieldName = "Quantité déductible";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar3.equals("G14"))
        {
            fieldName = "Eligible Percentage";
            frenchFieldName = "Pourcentage éligible";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(3);
            valueRequirements.Add(new RangeValueRequirement(0,100));
        }
        else if (__dummyScrutVar3.equals("G15"))
        {
            fieldName = "Benefit Amount for the Procedure";
            frenchFieldName = "Quantité d'avantage pour le procédé";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar3.equals("G16"))
        {
            fieldName = "Explanation Note Number 1";
            frenchFieldName = "Note numéro 1 d'explication";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar3.equals("G17"))
        {
            fieldName = "Explanation Note Number 2";
            frenchFieldName = "Note numéro 2 d'explication";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar3.equals("G18"))
        {
            fieldName = "Reference to Line Number of the Submitted Procedure";
            frenchFieldName = "Référence à la ligne nombre du procédé soumis";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(7);
            valueRequirements.Add(new RegexValueRequirement("^[0-7]+$"));
        }
        else if (__dummyScrutVar3.equals("G19"))
        {
            fieldName = "Additional Procedure Code";
            frenchFieldName = "Code additionnel de procédé";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar3.equals("G20"))
        {
            fieldName = "Eligible Amount for the Additional Procedure";
            frenchFieldName = "Quantité éligible pour le procédé additionnel";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar3.equals("G21"))
        {
            fieldName = "Dedutible for the Additional Procedure";
            frenchFieldName = "";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar3.equals("G22"))
        {
            fieldName = "Eligible Percentage";
            frenchFieldName = "Pourcentage éligible";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(3);
            valueRequirements.Add(new RangeValueRequirement(0,100));
        }
        else if (__dummyScrutVar3.equals("G23"))
        {
            fieldName = "Benefit Amount for the Additional Procedure";
            frenchFieldName = "Quantité d'avantage pour le procédé additionnel";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar3.equals("G24"))
        {
            fieldName = "Explanation Note Number 1 for the Additional Procedure";
            frenchFieldName = "Note d'explication numéro 1 pour le procédé additionnel";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar3.equals("G25"))
        {
            fieldName = "Explanation Note Number 2 for the Additional Procedure";
            frenchFieldName = "Note d'explication numéro 2 pour le procédé additionnel";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar3.equals("G26"))
        {
            fieldName = "Note Text";
            frenchFieldName = "Noter le texte";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(75);
        }
        else if (__dummyScrutVar3.equals("G27"))
        {
            fieldName = "Language of the Insured";
            frenchFieldName = "Langue des assurés";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "E", "F" }));
        }
        else if (__dummyScrutVar3.equals("G28"))
        {
            fieldName = "Total Benefit Amount";
            frenchFieldName = "Quantité totale d'avantage";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(7);
        }
        else if (__dummyScrutVar3.equals("G29"))
        {
            fieldName = "Deductible amount unallocated";
            frenchFieldName = "La quantité déductible a désassigné";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar3.equals("G30"))
        {
            //Transaction Validation Code
            fieldName = "VERIFICATION NO";
            frenchFieldName = "CODE DE VALIDATION";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(10);
        }
        else
        {
            MessageBox.Show("Internal Error, unknown version 2 CCD field ID during construction: " + pFieldId);
            return ;
        }                                                                                  
    }

    //error
    private void setValuesUsingFieldId_v4(String pFieldId) throws Exception {
        //Includes US states and Canadian provinces.
        //http://www.nrcan.gc.ca/earth-sciences/geography-boundary/geographical-name/translators/5782
        String[] stateCodes = new String[]{ "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT", "LB", "NF", "PQ", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY" };
        //Canadian province codes.
        //Alberta
        //Britich Columbia
        //Manitoba
        //New Brunswick
        //Newfoundland and Labrador
        //Nova Scotia
        //Northwest Territories
        //Nunavut
        //Ontario
        //Prince Edward Island
        //Quebec
        //Saskatchewan
        //Yukon
        //Traditional Canadian province codes which somehow made it into our application, but we are going to leave them because they are probably harmless.
        //Newfoundland and Labrador - This appeared in Canada Post publications (e.g., The Canadian Postal Code Directory) for the mainland section of the province of Newfoundland and Labrador.
        //Newfoundland and Labrador - Nfld. and later NF (the two-letter abbreviation used before the province's name changed to Newfoundland and Labrador) and T.-N. (French version, for Terre-Neuve)
        //Quebec	- Que. and P.Q. (French version, for Province du Québec); later, PQ evolved from P.Q. as the first two-letter non-punctuated abbreviation.
        //US state codes.
        String[] languageCodes = new String[]{ "A", "E", "F" };
        ValueMap valueMap;
        //Values in the following table were taken from the data dictionary of the CCD doc (about page 70).
        System.String __dummyScrutVar4 = pFieldId;
        if (__dummyScrutVar4.equals("A01"))
        {
            fieldName = "Transaction Prefix";
            frenchFieldName = "Préfixe de transaction";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(12);
        }
        else if (__dummyScrutVar4.equals("A02"))
        {
            //DENTAL OFFICE CLAIM REFERENCE NO
            fieldName = "DENTAL OFFICE CLAIM REFERENCE NO";
            frenchFieldName = "NO DE TRANSACTION DU CABINET";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("A03"))
        {
            fieldName = "Format Version Number";
            frenchFieldName = "Nombre de version de format";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "02", "04" }));
        }
        else if (__dummyScrutVar4.equals("A04"))
        {
            fieldName = "Transaction Code";
            frenchFieldName = "Code de transaction";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "01", "11", "21", "02", "12", "03", "13", "23", "04", "14", "24", "05", "15", "06", "16", "07", "08", "18" }));
        }
        else if (__dummyScrutVar4.equals("A05"))
        {
            fieldName = "Carrier Identification Number";
            frenchFieldName = "Numéro d'identification de porteur";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("A06"))
        {
            fieldName = "Software System ID";
            frenchFieldName = "Système logiciel identification";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(3);
        }
        else if (__dummyScrutVar4.equals("A07"))
        {
            fieldName = "Message Length";
            frenchFieldName = "Longueur de message";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar4.equals("A08"))
        {
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(1);
            fieldName = "Materials Forwarded";
            frenchFieldName = "Les matériaux ont expédié";
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ " ", "E", "C", "M", "X", "I", "A", "B", "D", "F", "G", "H", "J", "K", "L", "N", "O", "P", "Q", "R", "T", "U" }));
        }
        else if (__dummyScrutVar4.equals("A09"))
        {
            //Not in version 2.
            fieldName = "Carrier Transaction Counter";
            frenchFieldName = "Compteur de transaction de porteur";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar4.equals("A10"))
        {
            //Not in version 2.
            fieldName = "Encryption Method";
            frenchFieldName = "Méthode de chiffrage";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,3));
        }
        else if (__dummyScrutVar4.equals("A11"))
        {
            //Not in version 2.
            fieldName = "Mailbox Indicator";
            frenchFieldName = "Indicateur de boîte aux lettres";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "Y", "O", "N" }));
        }
        else if (__dummyScrutVar4.equals("B01"))
        {
            //CDA Provider Number
            fieldName = "UNIQUE ID NO";
            frenchFieldName = "NO DU DENTISTE";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(9);
        }
        else if (__dummyScrutVar4.equals("B02"))
        {
            //Provider Office Number
            fieldName = "OFFICE NO";
            frenchFieldName = "NO DU CABINET";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(4);
        }
        else if (__dummyScrutVar4.equals("B03"))
        {
            //Not in version 2.
            fieldName = "BILLING PROVIDER NUMBER";
            frenchFieldName = "NOMBRE DE FOURNISSEUR DE FACTURATION";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(9);
        }
        else if (__dummyScrutVar4.equals("B04"))
        {
            //Not in version 2.
            fieldName = "BILLING OFFICE NUMBER";
            frenchFieldName = "NOMBRE D'OFFICE DE FACTURATION";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(4);
        }
        else if (__dummyScrutVar4.equals("B05"))
        {
            //Not in version 2.
            fieldName = "Referring Provider Number";
            frenchFieldName = "Référence du nombre de fournisseur";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(10);
        }
        else if (__dummyScrutVar4.equals("B06"))
        {
            //Not in version 2.
            fieldName = "Referral Reason Code";
            frenchFieldName = "Code complémentaire de référence";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new RangeValueRequirement(0,13));
        }
        else if (__dummyScrutVar4.equals("B07"))
        {
            //Not in version 2.
            fieldName = "Receiving Provider Number";
            frenchFieldName = "Réception du nombre de fournisseur";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(9);
        }
        else if (__dummyScrutVar4.equals("B08"))
        {
            //Not in version 2.
            fieldName = "Receiving Office Number";
            frenchFieldName = "Réception du nombre d'Office";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(4);
        }
        else if (__dummyScrutVar4.equals("C01"))
        {
            //Primary Policy/Plan Number
            fieldName = "POLICY#";
            frenchFieldName = "NO DE POLICE";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(12);
        }
        else if (__dummyScrutVar4.equals("C02"))
        {
            //Subscriber Identification Number
            fieldName = "CERTIFICATE NO";
            frenchFieldName = "NO DE CERTIFICAT";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(12);
        }
        else if (__dummyScrutVar4.equals("C03"))
        {
            fieldName = "Relationship Code";
            frenchFieldName = "Code de rapport";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,5));
        }
        else if (__dummyScrutVar4.equals("C04"))
        {
            fieldName = "Patient's Sex";
            frenchFieldName = "Le sexe du patient";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "M", "F" }));
        }
        else if (__dummyScrutVar4.equals("C05"))
        {
            fieldName = "Patient's Birthday";
            frenchFieldName = "L'anniversaire du patient";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("C06"))
        {
            fieldName = "Patient's Last Name";
            frenchFieldName = "Le dernier nom du patient";
            format = "AE";
            lengthRequirement = new ConstLengthRequirement(25);
        }
        else if (__dummyScrutVar4.equals("C07"))
        {
            fieldName = "Patient's First Name";
            frenchFieldName = "Le prénom du patient";
            format = "AE";
            lengthRequirement = new ConstLengthRequirement(15);
        }
        else if (__dummyScrutVar4.equals("C08"))
        {
            fieldName = "Patient's Middle Initial";
            frenchFieldName = "L'initiale moyenne du patient";
            format = "AE";
            lengthRequirement = new ConstLengthRequirement(1);
        }
        else if (__dummyScrutVar4.equals("C09"))
        {
            fieldName = "Eligibility Exception Code";
            frenchFieldName = "Code d'exception d'acceptabilité";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,4));
        }
        else if (__dummyScrutVar4.equals("C10"))
        {
            fieldName = "Name of School";
            frenchFieldName = "Nom d'école";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(25);
        }
        else if (__dummyScrutVar4.equals("C11"))
        {
            fieldName = "DIVISION/SECTION NO";
            frenchFieldName = "NO DE DIVISION/SECTION";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(10);
        }
        else if (__dummyScrutVar4.equals("C12"))
        {
            //Not in version 2.
            fieldName = "Plan Flag";
            frenchFieldName = "Drapeau de plan";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ " ", "A", "V", "N" }));
        }
        else if (__dummyScrutVar4.equals("C13"))
        {
            //Not in version 2.
            fieldName = "Band Number";
            frenchFieldName = "Nombre de bande";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(3);
        }
        else if (__dummyScrutVar4.equals("C14"))
        {
            //Not in version 2.
            fieldName = "Family Number";
            frenchFieldName = "Nombre de famille";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar4.equals("C15"))
        {
            //Not in version 2.
            fieldName = "Missing Teeth";
            frenchFieldName = "Dents absentes";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(11);
        }
        else if (__dummyScrutVar4.equals("C16"))
        {
            //Not in version 2.
            fieldName = "Eligibility Date";
            frenchFieldName = "Date d'acceptabilité";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("C17"))
        {
            //Not in version 2.
            fieldName = "Primary Dependant Code";
            frenchFieldName = "Code dépendant primaire";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar4.equals("C18"))
        {
            //Not in version 2.
            fieldName = "Plan Record Count";
            frenchFieldName = "Plan Coun record";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(0,1));
            valueMap = new ValueMap(new String[]{ "A", "N" }, new String[]{ "1" });
            valueRequirements.Add(new DiscreteValuesBasedOnOtherField("C12", new ValueMap[]{ valueMap }));
        }
        else if (__dummyScrutVar4.equals("C19"))
        {
            //Not in version 2.
            fieldName = "Plan Record";
            frenchFieldName = "Disque de plan";
            format = "AN";
            lengthRequirement = new ConstLengthRequirement(30);
        }
        else if (__dummyScrutVar4.equals("D01"))
        {
            fieldName = "Subscriber's Birthday";
            frenchFieldName = "L'anniversaire de l'abonné";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("D02"))
        {
            fieldName = "Subscriber's Last Name";
            frenchFieldName = "Le dernier nom de l'abonné";
            format = "AE";
            lengthRequirement = new ConstLengthRequirement(25);
        }
        else if (__dummyScrutVar4.equals("D03"))
        {
            fieldName = "Subscriber's First Name";
            frenchFieldName = "Le prénom de l'abonné";
            format = "AE";
            lengthRequirement = new ConstLengthRequirement(15);
        }
        else if (__dummyScrutVar4.equals("D04"))
        {
            fieldName = "Subscriber's Middle Initial";
            frenchFieldName = "L'initiale moyenne de l'abonné";
            format = "AE";
            lengthRequirement = new ConstLengthRequirement(1);
        }
        else if (__dummyScrutVar4.equals("D05"))
        {
            fieldName = "Subscriber's Address Line 1";
            frenchFieldName = "Ligne 1 de l'adresse de l'abonné";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(30);
        }
        else if (__dummyScrutVar4.equals("D06"))
        {
            fieldName = "Subscriber's Address Line 2";
            frenchFieldName = "Ligne 2 de l'adresse de l'abonné";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(30);
        }
        else if (__dummyScrutVar4.equals("D07"))
        {
            fieldName = "Subscriber's City";
            frenchFieldName = "La ville de l'abonné";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(20);
        }
        else if (__dummyScrutVar4.equals("D08"))
        {
            fieldName = "Subscriber's Province/State Code";
            frenchFieldName = "Code de la province/état de l'abonné";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new DiscreteValueRequirement(stateCodes));
        }
        else if (__dummyScrutVar4.equals("D09"))
        {
            fieldName = "Subscriber's Postal/ZIP Code";
            frenchFieldName = "Code du Postal/ZIP de l'abonné";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(9);
        }
        else if (__dummyScrutVar4.equals("D10"))
        {
            fieldName = "Language of the Insured";
            frenchFieldName = "Langue des assurés";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(languageCodes));
        }
        else if (__dummyScrutVar4.equals("D11"))
        {
            //Not in version 2.
            fieldName = "Card Sequence/Version Number";
            frenchFieldName = "Ordre de carte/nombre de version";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new RangeValueRequirement(1,99));
        }
        else //Cannot be 0 and 99 is largest for 2 digits in base 10.
        if (__dummyScrutVar4.equals("E01"))
        {
            fieldName = "Secondary Carrier Unique ID Number";
            frenchFieldName = "Nombre unique d'identification de porteur secondaire";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("E02"))
        {
            fieldName = "Secondary Policy/Plan";
            frenchFieldName = "Politique/plan secondaires";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(12);
        }
        else if (__dummyScrutVar4.equals("E03"))
        {
            fieldName = "Secondary Plan Subscriber ID";
            frenchFieldName = "Identification secondaire d'abonné de plan";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(12);
        }
        else if (__dummyScrutVar4.equals("E04"))
        {
            fieldName = "Secondary Subscriber's Birthday";
            frenchFieldName = "L'anniversaire de l'abonné secondaire";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("E05"))
        {
            fieldName = "Secondary Division/Section Number";
            frenchFieldName = "Nombre secondaire de Division/section";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(10);
        }
        else if (__dummyScrutVar4.equals("E06"))
        {
            fieldName = "Secondary Relationship Code";
            frenchFieldName = "Code secondaire de rapport";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,5));
        }
        else if (__dummyScrutVar4.equals("E07"))
        {
            //Not in version 2.
            fieldName = "Secondary Card Sequence/Version Number";
            frenchFieldName = "Ordre de carte secondaire/nombre de version";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar4.equals("E08"))
        {
            //Not in version 2.
            fieldName = "Secondary Subscriber's Last Name";
            frenchFieldName = "Le dernier nom de l'abonné secondaire";
            format = "AE";
            lengthRequirement = new ConstLengthRequirement(25);
        }
        else if (__dummyScrutVar4.equals("E09"))
        {
            //Not in version 2.
            fieldName = "Secondary Subscriber's First Name";
            frenchFieldName = "Le prénom de l'abonné secondaire";
            format = "AE";
            lengthRequirement = new ConstLengthRequirement(15);
        }
        else if (__dummyScrutVar4.equals("E10"))
        {
            //Not in version 2.
            fieldName = "Secondary Subscriber's Middle Initial";
            frenchFieldName = "L'initiale moyenne de l'abonné secondaire";
            format = "AE";
            lengthRequirement = new ConstLengthRequirement(1);
        }
        else if (__dummyScrutVar4.equals("E11"))
        {
            //Not in version 2.
            fieldName = "Secondary Subscriber's Address Line 1";
            frenchFieldName = "Ligne 1 de l'adresse de l'abonné secondaire";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(30);
        }
        else if (__dummyScrutVar4.equals("E12"))
        {
            //Not in version 2.
            fieldName = "Secondary Subscriber's Address Line 2";
            frenchFieldName = "Ligne 2 de l'adresse de l'abonné secondaire";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(30);
        }
        else if (__dummyScrutVar4.equals("E13"))
        {
            //Not in version 2.
            fieldName = "Secondary Subscriber's City";
            frenchFieldName = "La ville de l'abonné secondaire";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(20);
        }
        else if (__dummyScrutVar4.equals("E14"))
        {
            //Not in version 2.
            fieldName = "Secondary Subscriber's Province/State Code";
            frenchFieldName = "Code de la province/état de l'abonné secondaire";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new DiscreteValueRequirement(stateCodes));
        }
        else if (__dummyScrutVar4.equals("E15"))
        {
            //Not in version 2.
            fieldName = "Secondary Subscriber's Postal/ZIP Code";
            frenchFieldName = "Code du Postal/ZIP de l'abonné secondaire";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(9);
        }
        else if (__dummyScrutVar4.equals("E16"))
        {
            //Not in version 2.
            fieldName = "Secondary Language";
            frenchFieldName = "Langue secondaire";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(languageCodes));
        }
        else if (__dummyScrutVar4.equals("E17"))
        {
            //Not in version 2.
            fieldName = "Secondary Dependant Code";
            frenchFieldName = "Code dépendant secondaire";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar4.equals("E18"))
        {
            //Not in version 2.
            fieldName = "Secondary Coverage";
            frenchFieldName = "Assurance secondaire";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "Y", "O", "N", "X" }));
        }
        else if (__dummyScrutVar4.equals("E19"))
        {
            //Not in version 2.
            fieldName = "Secondary Carrier Transaction Counter";
            frenchFieldName = "Compteur secondaire de transaction de porteur";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("E20"))
        {
            //Not in version 2.
            fieldName = "Secondary Record Count";
            frenchFieldName = "Compte record secondaire";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(0,1));
            valueMap = new ValueMap(new String[]{ "Y", "O" }, new String[]{ "1" });
            valueRequirements.Add(new DiscreteValuesBasedOnOtherField("E18", new ValueMap[]{ valueMap }));
        }
        else if (__dummyScrutVar4.equals("F01"))
        {
            fieldName = "Payee Code";
            frenchFieldName = "Code de bénéficiaire";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,4));
        }
        else if (__dummyScrutVar4.equals("F02"))
        {
            fieldName = "Accident Date";
            frenchFieldName = "Date d'accidents";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("F03"))
        {
            fieldName = "Predetermination Number";
            frenchFieldName = "Nombre de prédétermination";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(14);
        }
        else if (__dummyScrutVar4.equals("F04"))
        {
            fieldName = "Date of Initial Placement Upper";
            frenchFieldName = "Date de haut initial de placement";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("F05"))
        {
            fieldName = "Treatment Required for orthodontic";
            frenchFieldName = "Traitement requis pour orthodontique";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "Y", "O", "N" }));
        }
        else if (__dummyScrutVar4.equals("F06"))
        {
            fieldName = "Number of Procedures Performed";
            frenchFieldName = "Nombre de procédures exécutées";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,7));
        }
        else if (__dummyScrutVar4.equals("F07"))
        {
            fieldName = "Procedure Line Number";
            frenchFieldName = "Ligne nombre de procédé";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(0,7));
        }
        else if (__dummyScrutVar4.equals("F08"))
        {
            fieldName = "Procedure Code";
            frenchFieldName = "Code de procédé";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar4.equals("F09"))
        {
            fieldName = "Date of Service";
            frenchFieldName = "Date de service";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("F10"))
        {
            fieldName = "International Tooth,Sextant, Quad or Arch";
            frenchFieldName = "Dent, sextant, quadruple ou voûte international";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar4.equals("F11"))
        {
            fieldName = "Tooth Surface";
            frenchFieldName = "Surface de dent";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(5);
            valueRequirements.Add(new RegexValueRequirement("^[MOIDBLV ]+$"));
        }
        else if (__dummyScrutVar4.equals("F12"))
        {
            fieldName = "Dentist's Fee Claimed";
            frenchFieldName = "Les honoraires du dentiste réclamés";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("F13"))
        {
            fieldName = "Lab Procedure Fee # 1";
            frenchFieldName = "Honoraires # 1 de procédé de laboratoire";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else //case "F14": // Does not exist in data dictionary!
        //	break;
        if (__dummyScrutVar4.equals("F15"))
        {
            fieldName = "Is this an Initial Placement Upper";
            frenchFieldName = "Est c'un premier haut de placement";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "Y", "O", "N", "X" }));
        }
        else if (__dummyScrutVar4.equals("F16"))
        {
            //Not in version 2.
            fieldName = "Procedure Type Codes";
            frenchFieldName = "Type codes de procédé";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(5);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "A", "B", "C", "E", "L", "S", "X" }));
        }
        else if (__dummyScrutVar4.equals("F17"))
        {
            //Not in version 2.
            fieldName = "Remarks Code";
            frenchFieldName = "Code de remarques";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar4.equals("F18"))
        {
            //Not in version 2.
            fieldName = "Is this an Initial Placement Lower";
            frenchFieldName = "Est c'un premier placement inférieur";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "Y", "O", "N", "X" }));
        }
        else if (__dummyScrutVar4.equals("F19"))
        {
            //Not in version 2.
            fieldName = "Date of Initial Placement Lower";
            frenchFieldName = "La date du placement initial s'abaissent";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("F20"))
        {
            //Not in version 2.
            fieldName = "Maxillary Prosthesis Material";
            frenchFieldName = "Matériel maxillaire de prothèse";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,6));
        }
        else if (__dummyScrutVar4.equals("F21"))
        {
            //Not in version 2.
            fieldName = "Mandibular Prosthesis Material";
            frenchFieldName = "Matériel mandibulaire de prothèse";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,6));
        }
        else if (__dummyScrutVar4.equals("F22"))
        {
            //Not in version 2.
            fieldName = "Extracted Teeth Count";
            frenchFieldName = "Compte extrait de dents";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new RangeValueRequirement(0,50));
        }
        else if (__dummyScrutVar4.equals("F23"))
        {
            //Not in version 2.
            fieldName = "Extracted Tooth Number";
            frenchFieldName = "Nombre extrait de dent";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar4.equals("F24"))
        {
            //Not in version 2.
            fieldName = "Extraction Date";
            frenchFieldName = "Date d'extraction";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("F25"))
        {
            //Not in version 2.
            fieldName = "Orthodontic Record Flag";
            frenchFieldName = "Drapeau record orthodontique";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(0,1));
        }
        else if (__dummyScrutVar4.equals("F26"))
        {
            //Not in version 2.
            fieldName = "First Examination Fee";
            frenchFieldName = "Premiers honoraires d'examen";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("F27"))
        {
            //Not in version 2.
            fieldName = "Diagnostic Phase Fee";
            frenchFieldName = "Honoraires diagnostiques de phase";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("F28"))
        {
            //Not in version 2.
            fieldName = "Initial Payment";
            frenchFieldName = "Paiement initial";
            format = "D";
            lengthRequirement = new ConstLengthWhenOtherFieldHasValue("F25","1",6);
        }
        else if (__dummyScrutVar4.equals("F29"))
        {
            //Not in version 2.
            fieldName = "Payment Mode";
            frenchFieldName = "Mode de paiement";
            format = "N";
            lengthRequirement = new ConstLengthWhenOtherFieldHasValue("F25","1",1);
            valueRequirements.Add(new RangeValueRequirement(1,4));
        }
        else if (__dummyScrutVar4.equals("F30"))
        {
            //Not in version 2.
            fieldName = "Treatment Duration";
            frenchFieldName = "Durée de traitement";
            format = "N";
            lengthRequirement = new ConstLengthWhenOtherFieldHasValue("F25","1",2);
        }
        else if (__dummyScrutVar4.equals("F31"))
        {
            //Not in version 2.
            fieldName = "Number of Anticipated Payments";
            frenchFieldName = "Nombre de paiements prévus";
            format = "N";
            lengthRequirement = new ConstLengthWhenOtherFieldHasValue("F25","1",2);
        }
        else if (__dummyScrutVar4.equals("F32"))
        {
            //Not in version 2.
            fieldName = "Anticipated Payment Amount";
            frenchFieldName = "Quantité prévue de paiement";
            format = "D";
            lengthRequirement = new ConstLengthWhenOtherFieldHasValue("F25","1",6);
        }
        else if (__dummyScrutVar4.equals("F33"))
        {
            //Not in version 2.
            fieldName = "Reconciliation Date";
            frenchFieldName = "Date de réconciliation";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("F34"))
        {
            //Not in version 2.
            fieldName = "Lab Procedure Code # 1";
            frenchFieldName = "Code # 1 de procédé de laboratoire";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar4.equals("F35"))
        {
            //Not in version 2.
            fieldName = "Lab Procedure Code # 2";
            frenchFieldName = "Code # 2 de procédé de laboratoire";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar4.equals("F36"))
        {
            //Not in version 2.
            fieldName = "Lab Procedure Fee # 2";
            frenchFieldName = "Honoraires # 2 de procédé de laboratoire";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("F37"))
        {
            //Not in version 2.
            fieldName = "Estimated Treatment Start Date";
            frenchFieldName = "Date estimée de début de traitement";
            format = "N";
            lengthRequirement = new ConstLengthWhenOtherFieldHasValue("F25","1",8);
        }
        else if (__dummyScrutVar4.equals("F38"))
        {
            //Not in version 2.
            fieldName = "Current Reconciliation Page Number";
            frenchFieldName = "Numéro de page courant de réconciliation";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,9));
        }
        else if (__dummyScrutVar4.equals("F39"))
        {
            //Not in version 2.
            fieldName = "Diagnostic Code";
            frenchFieldName = "Code diagnostique";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("F40"))
        {
            //Not in version 2.
            fieldName = "Institution Code";
            frenchFieldName = "Code d'établissement";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("F41"))
        {
            //Not in version 2.
            fieldName = "Original DENTAL OFFICE CLAIM REFERENCE NO";
            frenchFieldName = "Nombre d'ordre original d'Office";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("F42"))
        {
            //Not in version 2.
            fieldName = "Original Transaction Reference Number";
            frenchFieldName = "Numéro de référence original de transaction";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(14);
        }
        else if (__dummyScrutVar4.equals("F43"))
        {
            //Not in version 2.
            fieldName = "Attachment Source";
            frenchFieldName = "Source d'attachement";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "I", "U" }));
        }
        else if (__dummyScrutVar4.equals("F44"))
        {
            //Not in version 2.
            fieldName = "Attachment Count";
            frenchFieldName = "Compte d'attachement";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new RangeValueRequirement(1,30));
        }
        else if (__dummyScrutVar4.equals("F45"))
        {
            //Not in version 2.
            fieldName = "Attachment Type";
            frenchFieldName = "Type d'attachement";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(3);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "JPG", "DIC", "TXT", "DOC" }));
        }
        else if (__dummyScrutVar4.equals("F46"))
        {
            //Not in version 2.
            fieldName = "Attachment Length";
            frenchFieldName = "Longueur d'attachement";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(7);
        }
        else if (__dummyScrutVar4.equals("F47"))
        {
            //Not in version 2.
            fieldName = "Attachment";
            frenchFieldName = "Attachement";
            format = "N";
            lengthRequirement = new LengthFromAnotherField("F46");
        }
        else if (__dummyScrutVar4.equals("F48"))
        {
            //Not in version 2.
            fieldName = "Attachment File Date";
            frenchFieldName = "Date de dossier d'attachement";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("F49"))
        {
            //Not in version 2.
            fieldName = "Attachment Number";
            frenchFieldName = "Nombre d'attachement";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar4.equals("G01"))
        {
            //Transaction Reference Number
            fieldName = "CARRIER CLAIM NO";
            frenchFieldName = "NO DE RÉFÉRENCE DE TRANSACTION";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(14);
        }
        else if (__dummyScrutVar4.equals("G02"))
        {
            fieldName = "Eligible Amount for Lab Procedure Code #2";
            frenchFieldName = "Quantité éligible pour le code #2 de procédé de laboratoire";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("G03"))
        {
            fieldName = "Expected Payment Date";
            frenchFieldName = "Date prévue de paiement";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("G04"))
        {
            fieldName = "Total Amount of Service";
            frenchFieldName = "Montant total de service";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(7);
        }
        else if (__dummyScrutVar4.equals("G05"))
        {
            fieldName = "Response Status";
            frenchFieldName = "Statut de réponse";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "A", "E", "R", "H", "B", "C", "N", "M", "X" }));
        }
        else if (__dummyScrutVar4.equals("G06"))
        {
            fieldName = "Number of Error Codes";
            frenchFieldName = "Nombre de codes d'erreur";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new RangeValueRequirement(0,10));
        }
        else if (__dummyScrutVar4.equals("G07"))
        {
            //Disposition message
            fieldName = "DISPOSITION";
            frenchFieldName = "SPÉCIFICATIONS";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(75);
        }
        else if (__dummyScrutVar4.equals("G08"))
        {
            fieldName = "Error Code";
            frenchFieldName = "Code d'erreur";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(3);
        }
        else //case "G09": // Does not exist
        //break;
        if (__dummyScrutVar4.equals("G10"))
        {
            fieldName = "Number of Carrier Issued Procedure Codes";
            frenchFieldName = "Le nombre de porteur a publié des codes de procédé";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(0,6));
        }
        else if (__dummyScrutVar4.equals("G11"))
        {
            fieldName = "Number of Note Lines";
            frenchFieldName = "Nombre de lignes de note";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new RangeValueRequirement(0,32));
        }
        else if (__dummyScrutVar4.equals("G12"))
        {
            fieldName = "Eligible Amount";
            frenchFieldName = "Quantité éligible";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("G13"))
        {
            fieldName = "Deductible Amount";
            frenchFieldName = "Quantité déductible";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar4.equals("G14"))
        {
            fieldName = "Eligible Percentage";
            frenchFieldName = "Pourcentage éligible";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(3);
            valueRequirements.Add(new RangeValueRequirement(0,100));
        }
        else if (__dummyScrutVar4.equals("G15"))
        {
            fieldName = "Benefit Amount for the Procedure";
            frenchFieldName = "Quantité d'avantage pour le procédé";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("G16"))
        {
            fieldName = "Explanation Note Number 1";
            frenchFieldName = "Note numéro 1 d'explication";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar4.equals("G17"))
        {
            fieldName = "Explanation Note Number 2";
            frenchFieldName = "Note numéro 2 d'explication";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar4.equals("G18"))
        {
            fieldName = "Reference to Line Number of the Submitted Procedure";
            frenchFieldName = "Référence à la ligne nombre du procédé soumis";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(7);
            valueRequirements.Add(new RegexValueRequirement("^[0-7]+$"));
        }
        else if (__dummyScrutVar4.equals("G19"))
        {
            fieldName = "Additional Procedure Code";
            frenchFieldName = "Code additionnel de procédé";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar4.equals("G20"))
        {
            fieldName = "Eligible Amount for the Additional Procedure";
            frenchFieldName = "Quantité éligible pour le procédé additionnel";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("G21"))
        {
            fieldName = "Dedutible for the Additional Procedure";
            frenchFieldName = "";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar4.equals("G22"))
        {
            fieldName = "Eligible Percentage";
            frenchFieldName = "Pourcentage éligible";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(3);
            valueRequirements.Add(new RangeValueRequirement(0,100));
        }
        else if (__dummyScrutVar4.equals("G23"))
        {
            fieldName = "Benefit Amount for the Additional Procedure";
            frenchFieldName = "Quantité d'avantage pour le procédé additionnel";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("G24"))
        {
            fieldName = "Explanation Note Number 1 for the Additional Procedure";
            frenchFieldName = "Note d'explication numéro 1 pour le procédé additionnel";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar4.equals("G25"))
        {
            fieldName = "Explanation Note Number 2 for the Additional Procedure";
            frenchFieldName = "Note d'explication numéro 2 pour le procédé additionnel";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
        }
        else if (__dummyScrutVar4.equals("G26"))
        {
            fieldName = "Note Text";
            frenchFieldName = "Noter le texte";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(75);
        }
        else if (__dummyScrutVar4.equals("G27"))
        {
            fieldName = "Language of the Insured";
            frenchFieldName = "Langue des assurés";
            format = "A";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new DiscreteValueRequirement(new String[]{ "A", "E", "F" }));
        }
        else if (__dummyScrutVar4.equals("G28"))
        {
            fieldName = "Total Benefit Amount";
            frenchFieldName = "Quantité totale d'avantage";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(7);
        }
        else if (__dummyScrutVar4.equals("G29"))
        {
            fieldName = "Deductible amount unallocated";
            frenchFieldName = "La quantité déductible a désassigné";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("G30"))
        {
            fieldName = "VERIFICATION NO";
            frenchFieldName = "CODE DE VALIDATION";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(10);
        }
        else if (__dummyScrutVar4.equals("G31"))
        {
            //Not in version 2.
            fieldName = "Display Message Count";
            frenchFieldName = "Compte de message d'affichage";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new RangeValueRequirement(0,40));
        }
        else if (__dummyScrutVar4.equals("G32"))
        {
            //Not in version 2.
            fieldName = "Display Message";
            frenchFieldName = "Message d'affichage";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(75);
        }
        else if (__dummyScrutVar4.equals("G33"))
        {
            //Not in version 2.
            fieldName = "PAYMENT ADJUSTMENT AMOUNT";
            frenchFieldName = "MONTANT D'ADAPTATION DE PAIEMENT";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(7);
        }
        else if (__dummyScrutVar4.equals("G34"))
        {
            //Not in version 2.
            fieldName = "PAYMENT REFERENCE";
            frenchFieldName = "RÉFÉRENCE DE PAIEMENT";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(10);
        }
        else if (__dummyScrutVar4.equals("G35"))
        {
            //Not in version 2.
            fieldName = "PAYMENT DATE";
            frenchFieldName = "DATE DE PAIEMENT";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(8);
        }
        else if (__dummyScrutVar4.equals("G36"))
        {
            //Not in version 2.
            fieldName = "PAYMENT AMOUNT";
            frenchFieldName = "QUANTITÉ DE PAIEMENT";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(7);
        }
        else if (__dummyScrutVar4.equals("G37"))
        {
            //Not in version 2.
            fieldName = "Payment Detail Count";
            frenchFieldName = "Compte de détail de paiement";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(3);
            valueRequirements.Add(new RangeValueRequirement(0,250));
        }
        else if (__dummyScrutVar4.equals("G38"))
        {
            //Not in version 2.
            fieldName = "Transaction Payment";
            frenchFieldName = "Paiement de transaction";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(7);
        }
        else if (__dummyScrutVar4.equals("G39"))
        {
            //Not in version 2.
            fieldName = "Embedded Transaction Length";
            frenchFieldName = "Longueur incluse de transaction";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(4);
        }
        else if (__dummyScrutVar4.equals("G40"))
        {
            //Not in version 2.
            fieldName = "Embedded Transaction";
            frenchFieldName = "Transaction incluse";
            format = "AE/N";
            lengthRequirement = new LengthFromAnotherField("G39");
        }
        else if (__dummyScrutVar4.equals("G41"))
        {
            //Not in version 2.
            fieldName = "Message Output Flag";
            frenchFieldName = "Drapeau de rendement de message";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(0,2));
        }
        else if (__dummyScrutVar4.equals("G42"))
        {
            //Not in version 2.
            fieldName = "Form ID";
            frenchFieldName = "Former l'identification";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new RangeValueRequirement(1,8));
        }
        else if (__dummyScrutVar4.equals("G43"))
        {
            //Not in version 2.
            fieldName = "Eligible Amount for Lab Procedure Code # 1";
            frenchFieldName = "Quantité éligible pour le code # 1 de procédé de laboratoire";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("G44"))
        {
            //Not in version 2.
            fieldName = "Eligible Lab Amount for the Additional Procedure";
            frenchFieldName = "Quantité éligible de laboratoire pour le procédé additionnel";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("G45"))
        {
            //Not in version 2.
            fieldName = "Note Number";
            frenchFieldName = "Noter le nombre";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(3);
        }
        else if (__dummyScrutVar4.equals("G46"))
        {
            //Not in version 2.
            fieldName = "Current Predetermination Page Number";
            frenchFieldName = "Numéro de page courant de prédétermination";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
        }
        else if (__dummyScrutVar4.equals("G47"))
        {
            //Not in version 2.
            fieldName = "Last Predetermination Page Number";
            frenchFieldName = "Numéro de page courant de prédétermination";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
        }
        else if (__dummyScrutVar4.equals("G48"))
        {
            //Not in version 2.
            fieldName = "E-Mail Office Number";
            frenchFieldName = "Nombre d'Office d'E-mail";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(4);
        }
        else if (__dummyScrutVar4.equals("G49"))
        {
            //Not in version 2.
            //E-mail to
            fieldName = "TO";
            frenchFieldName = "DESTINATAIRE";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(60);
        }
        else if (__dummyScrutVar4.equals("G50"))
        {
            //Not in version 2.
            //E-mail from
            fieldName = "FROM";
            frenchFieldName = "EXPÉDITEUR";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(60);
        }
        else if (__dummyScrutVar4.equals("G51"))
        {
            //Not in version 2.
            fieldName = "SUBJECT";
            frenchFieldName = "OBJET";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(60);
        }
        else if (__dummyScrutVar4.equals("G52"))
        {
            //Not in version 2.
            fieldName = "Number of E-mail Note Lines";
            frenchFieldName = "Nombre de lignes de note d'E-mail";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(2);
            valueRequirements.Add(new RangeValueRequirement(1,50));
        }
        else if (__dummyScrutVar4.equals("G53"))
        {
            //Not in version 2.
            fieldName = "E-Mail Note Line";
            frenchFieldName = "Ligne de note d'E-mail";
            format = "AE/N";
            lengthRequirement = new ConstLengthRequirement(75);
        }
        else if (__dummyScrutVar4.equals("G54"))
        {
            //Not in version 2.
            //Email reference number
            fieldName = "REFERENCE";
            frenchFieldName = "RÉFÉRENCE";
            format = "A/N";
            lengthRequirement = new ConstLengthRequirement(10);
        }
        else if (__dummyScrutVar4.equals("G55"))
        {
            //Not in version 2.
            fieldName = "Total Payable";
            frenchFieldName = "Payable total";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(7);
        }
        else if (__dummyScrutVar4.equals("G56"))
        {
            //Not in version 2.
            fieldName = "Deductible Amount for Lab Procedure Code # 1";
            frenchFieldName = "Quantité déductible pour le code # 1 de procédé de laboratoire";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar4.equals("G57"))
        {
            //Not in version 2.
            fieldName = "Eligible Percentage for Lab Procedure # 1";
            frenchFieldName = "Pourcentage éligible pour le procédé # 1 de laboratoire";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(3);
        }
        else if (__dummyScrutVar4.equals("G58"))
        {
            //Not in version 2.
            fieldName = "Benefit Amount for Lab Procedure Code #1";
            frenchFieldName = "Quantité d'avantage pour le code #1 de procédé de laboratoire";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("G59"))
        {
            //Not in version 2.
            fieldName = "Deductible Amount for Lab Procedure Code # 2";
            frenchFieldName = "Quantité déductible pour le code # 2 de procédé de laboratoire";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(5);
        }
        else if (__dummyScrutVar4.equals("G60"))
        {
            //Not in version 2.
            fieldName = "Eligible Percentage for Lab Procedure Code # 2";
            frenchFieldName = "Pourcentage éligible pour le code # 2 de procédé de laboratoire";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(3);
        }
        else if (__dummyScrutVar4.equals("G61"))
        {
            //Not in version 2.
            fieldName = "Benefit Amount for Lab Procedure Code # 2";
            frenchFieldName = "Bénéficier la quantité pour le code # 2 de procédé de laboratoire";
            format = "D";
            lengthRequirement = new ConstLengthRequirement(6);
        }
        else if (__dummyScrutVar4.equals("G62"))
        {
            //Not in version 2.
            fieldName = "Last Reconciliation Page Number";
            frenchFieldName = "Dernier numéro de page de réconciliation";
            format = "N";
            lengthRequirement = new ConstLengthRequirement(1);
            valueRequirements.Add(new RangeValueRequirement(1,9));
        }
        else
        {
            MessageBox.Show("Internal Error, unknown version 4 CCD field ID during construction: " + pFieldId);
            return ;
        }                                                                                                                                                                                  
    }

}


//error