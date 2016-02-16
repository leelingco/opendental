//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.SheetTypeEnum;

/**
* 
*/
public class SheetParameter   
{
    /**
    * 
    */
    public boolean IsRequired = new boolean();
    /**
    * Usually, a columnName.
    */
    public String ParamName = new String();
    /**
    * This is the value which must be set in order to obtain data from the database. It is usually an int primary key.  If running a batch, this may be an array of int.
    */
    public Object ParamValue = new Object();
    /**
    * Do not directly use this constructor.
    */
    public SheetParameter() throws Exception {
        IsRequired = false;
        ParamName = "";
    }

    public SheetParameter(boolean isRequired, String paramName) throws Exception {
        IsRequired = isRequired;
        ParamName = paramName;
    }

    public SheetParameter(boolean isRequired, String paramName, String paramValue) throws Exception {
        IsRequired = isRequired;
        ParamName = paramName;
        ParamValue = paramValue;
    }

    /**
    * Every sheet has at least one required parameter, usually the primary key of an imporant table.
    */
    public static List<SheetParameter> getForType(SheetTypeEnum sheetType) throws Exception {
        List<SheetParameter> list = new List<SheetParameter>();
        if (sheetType == SheetTypeEnum.LabelPatient)
        {
            list.Add(new SheetParameter(true,"PatNum"));
        }
         
        if (sheetType == SheetTypeEnum.LabelCarrier)
        {
            list.Add(new SheetParameter(true,"CarrierNum"));
        }
         
        if (sheetType == SheetTypeEnum.LabelReferral)
        {
            list.Add(new SheetParameter(true,"ReferralNum"));
        }
         
        if (sheetType == SheetTypeEnum.ReferralSlip)
        {
            list.Add(new SheetParameter(true,"PatNum"));
            list.Add(new SheetParameter(true,"ReferralNum"));
        }
         
        if (sheetType == SheetTypeEnum.LabelAppointment)
        {
            list.Add(new SheetParameter(true,"AptNum"));
        }
         
        if (sheetType == SheetTypeEnum.Rx)
        {
            list.Add(new SheetParameter(true,"RxNum"));
        }
         
        if (sheetType == SheetTypeEnum.Consent)
        {
            list.Add(new SheetParameter(true,"PatNum"));
        }
         
        if (sheetType == SheetTypeEnum.PatientLetter)
        {
            list.Add(new SheetParameter(true,"PatNum"));
        }
         
        if (sheetType == SheetTypeEnum.ReferralLetter)
        {
            list.Add(new SheetParameter(true,"PatNum"));
            list.Add(new SheetParameter(true,"ReferralNum"));
        }
         
        if (sheetType == SheetTypeEnum.PatientForm)
        {
            list.Add(new SheetParameter(true,"PatNum"));
        }
         
        if (sheetType == SheetTypeEnum.RoutingSlip)
        {
            list.Add(new SheetParameter(true,"AptNum"));
        }
         
        if (sheetType == SheetTypeEnum.MedicalHistory)
        {
            list.Add(new SheetParameter(true,"PatNum"));
        }
         
        if (sheetType == SheetTypeEnum.LabSlip)
        {
            list.Add(new SheetParameter(true,"PatNum"));
            list.Add(new SheetParameter(true,"LabCaseNum"));
        }
         
        if (sheetType == SheetTypeEnum.ExamSheet)
        {
            list.Add(new SheetParameter(true,"PatNum"));
        }
         
        if (sheetType == SheetTypeEnum.DepositSlip)
        {
            list.Add(new SheetParameter(true,"DepositNum"));
        }
         
        return list;
    }

    public static void setParameter(Sheet sheet, String paramName, Object paramValue) throws Exception {
        SheetParameter param = getParamByName(sheet.Parameters,paramName);
        if (param == null)
        {
            throw new ApplicationException(Lans.g("Sheet","Parameter not found: ") + paramName);
        }
         
        param.ParamValue = paramValue;
    }

    public static SheetParameter getParamByName(List<SheetParameter> parameters, String paramName) throws Exception {
        for (Object __dummyForeachVar0 : parameters)
        {
            SheetParameter param = (SheetParameter)__dummyForeachVar0;
            if (StringSupport.equals(param.ParamName, paramName))
            {
                return param;
            }
             
        }
        return null;
    }

}


