//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:31 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.StringSupport;
import OpenDental.Eclaims.CCDField;
import OpenDentBusiness.EtransType;

/**
* The idea is to make reading a CCD message in each of the different forms a smaller amount of overall typing, to save time and reduce bugs.
*/
public class CCDFieldInputter   
{
    private List<CCDField> fieldList = new List<CCDField>();
    //List of fields that make up the message
    public boolean isVersion2 = new boolean();
    public String messageText = "";
    public CCDFieldInputter() throws Exception {
    }

    public CCDFieldInputter(String message) throws Exception {
        messageText = message;
        String version = message.Substring(18, 2);
        String msgType = message.Substring(20, 2);
        if (StringSupport.equals(version, "04"))
        {
            System.String __dummyScrutVar0 = msgType;
            if (__dummyScrutVar0.equals("01"))
            {
                parseClaimRequest_01(message);
            }
            else if (__dummyScrutVar0.equals("11"))
            {
                parseClaimAck_11(message);
            }
            else if (__dummyScrutVar0.equals("21"))
            {
                parseEOB_21(message);
            }
            else if (__dummyScrutVar0.equals("12"))
            {
                parseClaimReversalResponse_12(message);
            }
            else if (__dummyScrutVar0.equals("18"))
            {
                parseResponseToElegibility_18(message);
            }
            else if (__dummyScrutVar0.equals("24"))
            {
                parseEmailResponse_24(message);
            }
            else if (__dummyScrutVar0.equals("14"))
            {
                parseOutstandingTransactionsAck_14(message);
            }
            else if (__dummyScrutVar0.equals("23"))
            {
                parsePredeterminationEOB_23(message);
            }
            else if (__dummyScrutVar0.equals("13"))
            {
                parsePredeterminationAck_13(message);
            }
            else if (__dummyScrutVar0.equals("16"))
            {
                parsePaymentReconciliation_16(message);
            }
            else if (__dummyScrutVar0.equals("15"))
            {
                parseReconciliaiton_15(message);
            }
            else
            {
                throw new ApplicationException("Invalid version 04 message type: " + msgType + Environment.NewLine + "Entire raw message:" + Environment.NewLine + message);
            }           
        }
        else
        {
            //version 02
            isVersion2 = true;
            System.String __dummyScrutVar1 = msgType;
            if (__dummyScrutVar1.equals("01"))
            {
                //claim transaction
                parseClaimRequest_v2_01(message);
            }
            else if (__dummyScrutVar1.equals("10"))
            {
                //eligibility response
                parseElegibilityResponse_v2_10(message);
            }
            else if (__dummyScrutVar1.equals("11"))
            {
                //claim response
                parseClaimResponse_v2_11(message);
            }
            else if (__dummyScrutVar1.equals("21"))
            {
                //eob
                parseEOB_v2_21(message);
            }
            else if (__dummyScrutVar1.equals("12"))
            {
                //reversal response
                parseClaimReversalResponse_v2_12(message);
            }
            else if (__dummyScrutVar1.equals("13"))
            {
                //response to predetermination
                parsePredeterminationAck_v2_13(message);
            }
            else
            {
                throw new ApplicationException("Invalid version 02 message type: " + msgType + Environment.NewLine + "Entire raw message:" + Environment.NewLine + message);
            }      
        } 
    }

    public CCDField[] getLoadedFields() throws Exception {
        CCDField[] loadedFields = new CCDField[fieldList.Count];
        fieldList.CopyTo(loadedFields);
        return loadedFields;
    }

    /// <summary>Input a single field.<summary>
    public String inputField(String message, String fieldId) throws Exception {
        CCDField field = new CCDField(fieldId,isVersion2);
        int len = field.getRequiredLength(this);
        if (len < 0 || message == null || message.Length < len)
        {
            return null;
        }
         
        if (len == 0)
        {
            return message;
        }
         
        String substr = message.Substring(0, len);
        //if(!field.CheckValue(this,substr)){
        //  throw new ApplicationException("Invalid value for CCD message field '"+field.fieldName+"'"+((substr==null)?"":(": "+substr)));
        //}
        field.valuestr = substr;
        fieldList.Add(field);
        return message.Substring(substr.Length, message.Length - substr.Length);
    }

    //Skip text that has already been read in.
    /**
    * Inputs fields based on a pseudo-script input string. This is possible, since each field id identifies a unique
    * input pattern. If a field Id is seen, then that field is inputted, but if the ### sequence is encountered, then a field
    * list is inputted based on the number in the next specified field, which has the format of the field after that. If the
    * input string leads with a "#" followed by a 2 digit number, then the string is input that many times.
    */
    public String inputFields(String message, String fieldOrderStr) throws Exception {
        fieldOrderStr = fieldOrderStr.ToUpper();
        if (fieldOrderStr.Length % 3 != 0)
        {
            throw new ApplicationException("Internal error, invalid field order string (not divisible by 3): " + fieldOrderStr);
        }
         
        if (fieldOrderStr.Length < 1)
        {
            return message;
        }
         
        for (int i = 0;i < fieldOrderStr.Length;i += 3)
        {
            String token = fieldOrderStr.Substring(i, 3);
            if (StringSupport.equals(token, "###"))
            {
                //Input field value by field id, then input the value number of fields with the next template.
                String valueFieldId = fieldOrderStr.Substring(i + 3, 3);
                if (valueFieldId == null || valueFieldId.Length != 3)
                {
                    throw new ApplicationException("Internal error, invalid value field id in: " + fieldOrderStr);
                }
                 
                CCDField valueField = getFieldById(valueFieldId);
                if (valueField == null)
                {
                    throw new ApplicationException(this.ToString() + ".InputCCDFields: Internal error, could not locate value field '" + valueFieldId + "'");
                }
                 
                if (!StringSupport.equals(valueField.format, "N"))
                {
                    throw new ApplicationException(this.ToString() + ".InputCCDFields: Internal error, value field '" + valueFieldId + "' is not an integer");
                }
                 
                String listFieldId = fieldOrderStr.Substring(i + 6, 3);
                if (listFieldId == null || listFieldId.Length != 3)
                {
                    throw new ApplicationException("Internal error, invalid field list id in: " + fieldOrderStr);
                }
                 
                i += 6;
                for (int p = 0;p < Convert.ToInt32(valueField.valuestr);p++)
                {
                    message = inputField(message,listFieldId);
                }
            }
            else
            {
                //Input a single field.
                message = inputField(message,token);
            } 
        }
        return message;
    }

    ///<summary>Get a list of loaded fields by a common field id.<summary>
    public CCDField[] getFieldsById(String fieldId) throws Exception {
        //lists are short, so just use a simple list search.
        List<CCDField> fields = new List<CCDField>();
        for (Object __dummyForeachVar0 : fieldList)
        {
            CCDField field = (CCDField)__dummyForeachVar0;
            if (StringSupport.equals(field.fieldId, fieldId))
            {
                fields.Add(field);
            }
             
        }
        return fields.ToArray();
    }

    //(new CCDField(field,isVersion2));
    /**
    * Same as GetFieldsById, but gets only a single field, or returns field with empty value if there are multiple.
    */
    public CCDField getFieldById(String fieldId) throws Exception {
        CCDField[] fields = getFieldsById(fieldId);
        if (fields == null || fields.Length == 0)
        {
            return null;
        }
         
        if (fields.Length > 1)
        {
            throw new ApplicationException("Internal error, invalid use of ambiguous CCD field id" + ((fieldId == null) ? "" : (": " + fieldId)));
        }
         
        return fields[0];
    }

    public String getValue(String fieldId) throws Exception {
        CCDField[] fields = getFieldsById(fieldId);
        if (fields == null || fields.Length == 0)
        {
            return "";
        }
         
        //Doesn't exist, return with empty value, so at least some information can be used.
        if (fields.Length > 1)
        {
            throw new ApplicationException("Internal error, invalid use of ambiguous CCD field id" + ((fieldId == null) ? "" : (": " + fieldId)));
        }
         
        return fields[0].valuestr;
    }

    /**
    * Used to read data that has already been sent out to CDAnet for processing.
    */
    private void parseClaimRequest_01(String message) throws Exception {
        message = inputFields(message,"A01A02A03A04A05A06A10A07A08A09B01B02B03B04B05B06C01C11C02C17C03C04" + "C05C06C07C08C09C10C12C18D01D02D03D04D05D06D07D08D09D10D11E18E20F06F22");
        if (message == null)
        {
            return ;
        }
         
        //error, but print what we have.
        CCDField fieldE20 = getFieldById("E20");
        int e20Val = 0;
        if (fieldE20 != null)
        {
            e20Val = Convert.ToInt32(fieldE20.valuestr);
        }
         
        if (e20Val == 1)
        {
            message = inputFields(message,"E19E01E02E05E03E17E06E04E08E09E10E11E12E13E14E15E16E07");
        }
         
        message = inputFields(message,"F01F02F03F15F04F18F19F05F20F21");
        CCDField fieldF22 = getFieldById("F22");
        int f22Val = 0;
        if (fieldF22 != null)
        {
            f22Val = Convert.ToInt32(fieldF22.valuestr);
        }
         
        for (int i = 0;i < f22Val;i++)
        {
            message = inputFields(message,"F23F24");
        }
        CCDField fieldF06 = getFieldById("F06");
        int f06Val = 0;
        if (fieldF06 != null)
        {
            f06Val = Convert.ToInt32(fieldF06.valuestr);
        }
         
        for (int i = 0;i < f06Val;i++)
        {
            message = inputFields(message,"F07F08F09F10F11F12F34F13F35F36F16F17");
        }
        CCDField fieldC18 = getFieldById("C18");
        int c18Val = 0;
        if (fieldC18 != null)
        {
            c18Val = Convert.ToInt32(fieldC18.valuestr);
        }
         
        if (c18Val == 1)
        {
            message = inputFields(message,"C19");
        }
         
    }

    private void parseClaimAck_11(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07A11B01B02G01G05G06G07G04G27G31G39" + "###G31G32" + "G42");
        //Read field G32 the number of times equal to the value in G31.
        if (message == null)
        {
            return ;
        }
         
        //error, but print what we have.
        CCDField fieldG06 = this.getFieldById("G06");
        if (fieldG06 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG06.format, "N"))
        {
            MessageBox.Show(this.ToString() + "PrintClaimAck_11: Internal error, field G06 is not an integer");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG06.valuestr);i++)
        {
            //error
            //Input a list of sub-records.
            message = this.inputFields(message,"F07G08");
            if (message == null)
            {
                return ;
            }
             
        }
        //error
        message = this.inputField(message,"G40");
    }

    private void parseEOB_21(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07A11B01B02G01G03G04G27F06G10G11G28G29G30F01G33G55G39G42");
        CCDField fieldF06 = this.getFieldById("F06");
        if (fieldF06 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldF06.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".PrintEOB_21: Internal error, field F06 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldF06.valuestr);i++)
        {
            message = this.inputFields(message,"F07G12G13G14G15G43G56G57G58G02G59G60G61G16G17");
        }
        CCDField fieldG10 = this.getFieldById("G10");
        if (fieldG10 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG10.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".PrintEOB_21: Internal error, field G10 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG10.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"G18G19G20G44G21G22G23G24G25");
        }
        CCDField fieldG11 = this.getFieldById("G11");
        if (fieldG11 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG11.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".PrintEOB_21: Internal error, field G11 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG11.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"G41G45G26");
        }
        message = this.inputFields(message,"G40");
    }

    private void parseClaimReversalResponse_12(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07A11B01B02E19G01G05G06G07G04G31" + "###G31G32" + "###G06G08");
        return ;
    }

    private void parseResponseToElegibility_18(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07A11B01B02G01G05G06G07G31G42" + "###G06G08" + "###G31G32");
        return ;
    }

    private void parseEmailResponse_24(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A07A11G48G54G49G50G51G52" + "###G52G53");
        return ;
    }

    private void parseOutstandingTransactionsAck_14(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07A11B01B02B03G05G06G07" + "###G06G08");
        return ;
    }

    private void parsePredeterminationEOB_23(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07A11B01B02G01G04G27F06G10G11G28G29G30G39G42G46G47");
        CCDField fieldF06 = this.getFieldById("F06");
        if (fieldF06 == null)
        {
            return ;
        }
         
        //error, but return as much of the form as we were able to understand.
        if (!StringSupport.equals(fieldF06.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParsePredeterminationEOB_23: Internal error, field F06 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldF06.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"F07G12G13G14G15G43G56G57G58G02G59G60G61G16G17");
        }
        CCDField fieldG10 = this.getFieldById("G10");
        if (fieldG10 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG10.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParsePredeterminationEOB_23: Internal error, field G10 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG10.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"G18G19G20G44G21G22G23G24G25");
        }
        CCDField fieldG11 = this.getFieldById("G11");
        if (fieldG11 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG11.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParsePredeterminationEOB_23: Internal error, field G11 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG11.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"G41G45G26");
        }
        message = this.inputFields(message,"G40");
        return ;
    }

    private void parsePredeterminationAck_13(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07A11B01B02G01G05G06G07G04G27G31G39" + "###G31G32" + "G42G46G47");
        CCDField fieldG06 = this.getFieldById("G06");
        if (fieldG06 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG06.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParsePredeterminationAck_13: Internal error, field G06 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG06.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"F07G08");
        }
        message = this.inputFields(message,"G40");
        return ;
    }

    private void parsePaymentReconciliation_16(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A07A11B04G01G05G06G11G34G35G36G37G33F38G62");
        CCDField fieldG37 = this.getFieldById("G37");
        if (fieldG37 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG37.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParsePaymentReconciliation_16: Internal error, field G37 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG37.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"B01B02B03A05A02G01G38");
        }
        CCDField fieldG11 = this.getFieldById("G11");
        if (fieldG11 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG11.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParsePaymentReconciliation_16: Internal error, field G11 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG11.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"G41G26");
        }
        message = this.inputFields(message,"###G06G08");
        return ;
    }

    private void parseReconciliaiton_15(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A07A11B02G01G05G06G11G34G35G36G37G33");
        CCDField fieldG37 = this.getFieldById("G37");
        if (fieldG37 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG37.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParseReconciliaiton_15: Internal error, field G37 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG37.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"B01A05A02G01G38");
        }
        CCDField fieldG11 = this.getFieldById("G11");
        if (fieldG11 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG11.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParseReconciliaiton_15: Internal error, field G11 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG11.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"G41G26");
        }
        message = this.inputFields(message,"###G06G08");
        return ;
    }

    private void parseClaimRequest_v2_01(String message) throws Exception {
        message = inputFields(message,"A01A02A03A04A05A06A07A08B01B02C01C11C02C03C04C05C06C07C08C09C10D01D02D03D04D05D06D07D08D09D10E01E02E05E03E04" + "F01F02F03F15F04F05F06");
        if (message == null)
        {
            return ;
        }
         
        //error, but print what we have.
        CCDField fieldF06 = getFieldById("F06");
        int f06Val = 0;
        if (fieldF06 != null)
        {
            f06Val = Convert.ToInt32(fieldF06.valuestr);
        }
         
        for (int i = 0;i < f06Val;i++)
        {
            message = inputFields(message,"F07F08F09F10F11F12F13F14");
        }
    }

    private void parseElegibilityResponse_v2_10(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07B01B02G01G05G06G07G02" + "###G06G08");
        return ;
    }

    private void parseClaimResponse_v2_11(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07B01B02G01G05G06G07G02G04G27");
        CCDField fieldG06 = this.getFieldById("G06");
        if (fieldG06 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG06.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParseClaimResponse_v2_11: Internal error, field G06 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG06.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"F07G08");
        }
    }

    private void parseEOB_v2_21(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07B01B02G01G03G04G27G09F06G10G11G28G29G30");
        CCDField fieldF06 = this.getFieldById("F06");
        if (fieldF06 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldF06.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParseEOB_v2_21: Internal error, field F06 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldF06.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"F07G12G13G14G15G16G17");
        }
        CCDField fieldG10 = this.getFieldById("G10");
        if (fieldG10 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG10.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParseEOB_v2_21: Internal error, field G10 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG10.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"G18G19G20G21G22G23G24G25");
        }
        message = this.inputFields(message,"###G11G26");
    }

    private void parseClaimReversalResponse_v2_12(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07B01B02G01G05G06G07G04###G06G08");
    }

    private void parsePredeterminationAck_v2_13(String message) throws Exception {
        message = this.inputFields(message,"A01A02A03A04A05A07B01B02G01G05G06G07G02G04");
        CCDField fieldG06 = this.getFieldById("G06");
        if (fieldG06 == null)
        {
            return ;
        }
         
        //error
        if (!StringSupport.equals(fieldG06.format, "N"))
        {
            MessageBox.Show(this.ToString() + ".ParsePredeterminationAck_v2_13: Internal error, field G06 is not of integer type!");
            return ;
        }
         
        for (int i = 0;i < Convert.ToInt32(fieldG06.valuestr);i++)
        {
            //error
            message = this.inputFields(message,"F07G08");
        }
    }

    /**
    * Probably some missing types.  Mostly focussed on response types.
    */
    public EtransType getEtransType() throws Exception {
        String msgType = getValue("A04");
        if (!isVersion2)
        {
            //version 4
            System.String __dummyScrutVar2 = msgType;
            if (__dummyScrutVar2.equals("01"))
            {
                return EtransType.Claim_CA;
            }
            else if (__dummyScrutVar2.equals("11"))
            {
                return EtransType.ClaimAck_CA;
            }
            else if (__dummyScrutVar2.equals("21"))
            {
                return EtransType.ClaimEOB_CA;
            }
            else if (__dummyScrutVar2.equals("12"))
            {
                return EtransType.ReverseResponse_CA;
            }
            else if (__dummyScrutVar2.equals("18"))
            {
                return EtransType.EligResponse_CA;
            }
            else if (__dummyScrutVar2.equals("24"))
            {
                return EtransType.EmailResponse_CA;
            }
            else if (__dummyScrutVar2.equals("14"))
            {
                return EtransType.OutstandingAck_CA;
            }
            else if (__dummyScrutVar2.equals("23"))
            {
                return EtransType.PredetermEOB_CA;
            }
            else if (__dummyScrutVar2.equals("13"))
            {
                return EtransType.PredetermAck_CA;
            }
            else if (__dummyScrutVar2.equals("16"))
            {
                return EtransType.PaymentResponse_CA;
            }
            else if (__dummyScrutVar2.equals("15"))
            {
                return EtransType.SummaryResponse_CA;
            }
            else
            {
                throw new ApplicationException("Version 4 message type not recognized: " + msgType);
            }           
        }
        else
        {
            //version 02
            System.String __dummyScrutVar3 = msgType;
            if (__dummyScrutVar3.equals("01"))
            {
                return EtransType.Claim_CA;
            }
            else if (__dummyScrutVar3.equals("10"))
            {
                return EtransType.EligResponse_CA;
            }
            else //eligibility response
            if (__dummyScrutVar3.equals("11"))
            {
                return EtransType.ClaimAck_CA;
            }
            else //claim response
            if (__dummyScrutVar3.equals("21"))
            {
                return EtransType.ClaimEOB_CA;
            }
            else //eob
            if (__dummyScrutVar3.equals("12"))
            {
                return EtransType.ReverseResponse_CA;
            }
            else //reversal response
            if (__dummyScrutVar3.equals("13"))
            {
                return EtransType.PredetermAck_CA;
            }
            else
            {
                throw new ApplicationException("Version 2 message type not recognized: " + msgType);
            }      
        } 
    }

}


//response to predetermination