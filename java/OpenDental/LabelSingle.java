//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental;

import OpenDental.SheetFiller;
import OpenDental.SheetPrinting;
import OpenDental.SheetUtil;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetInternalType;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.SheetsInternal;
import OpenDentBusiness.SheetTypeEnum;

/**
* 
*/
public class LabelSingle   
{
    //private PrintDocument pd;
    //private Patient Pat;
    //private Carrier CarrierCur;
    //private Referral ReferralCur;
    /**
    * 
    */
    public LabelSingle() throws Exception {
    }

    /**
    * 
    */
    public static void printPat(long patNum) throws Exception {
        SheetDef sheetDef = SheetsInternal.getSheetDef(SheetInternalType.LabelPatientMail);
        if (PrefC.getLong(PrefName.LabelPatientDefaultSheetDefNum) != 0)
        {
            try
            {
                //Try to use custom label sheet.
                sheetDef = SheetDefs.getSheetDef(PrefC.getLong(PrefName.LabelPatientDefaultSheetDefNum));
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
         
        //The default label could not be retrieved so just use the internal sheet.
        Sheet sheet = SheetUtil.createSheet(sheetDef);
        SheetParameter.setParameter(sheet,"PatNum",patNum);
        SheetFiller.fillFields(sheet);
        try
        {
            SheetPrinting.print(sheet);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    public static void printCustomPatient(long patNum, SheetDef sheetDef) throws Exception {
        SheetDefs.getFieldsAndParameters(sheetDef);
        Sheet sheet = SheetUtil.createSheet(sheetDef);
        SheetParameter.setParameter(sheet,"PatNum",patNum);
        SheetFiller.fillFields(sheet);
        try
        {
            SheetPrinting.print(sheet);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    public static void printPatientLFAddress(long patNum) throws Exception {
        SheetDef sheetDef = SheetsInternal.getSheetDef(SheetInternalType.LabelPatientLFAddress);
        Sheet sheet = SheetUtil.createSheet(sheetDef);
        SheetParameter.setParameter(sheet,"PatNum",patNum);
        SheetFiller.fillFields(sheet);
        try
        {
            SheetPrinting.print(sheet);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    public static void printPatientLFChartNumber(long patNum) throws Exception {
        SheetDef sheetDef = SheetsInternal.getSheetDef(SheetInternalType.LabelPatientLFChartNumber);
        Sheet sheet = SheetUtil.createSheet(sheetDef);
        SheetParameter.setParameter(sheet,"PatNum",patNum);
        SheetFiller.fillFields(sheet);
        try
        {
            SheetPrinting.print(sheet);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    public static void printPatientLFPatNum(long patNum) throws Exception {
        SheetDef sheetDef = SheetsInternal.getSheetDef(SheetInternalType.LabelPatientLFPatNum);
        Sheet sheet = SheetUtil.createSheet(sheetDef);
        SheetParameter.setParameter(sheet,"PatNum",patNum);
        SheetFiller.fillFields(sheet);
        try
        {
            SheetPrinting.print(sheet);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    public static void printPatRadiograph(long patNum) throws Exception {
        SheetDef sheetDef = SheetsInternal.getSheetDef(SheetInternalType.LabelPatientRadiograph);
        Sheet sheet = SheetUtil.createSheet(sheetDef);
        SheetParameter.setParameter(sheet,"PatNum",patNum);
        SheetFiller.fillFields(sheet);
        try
        {
            SheetPrinting.print(sheet);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    public static void printText(long patNum, String text) throws Exception {
        SheetDef sheetDef = SheetsInternal.getSheetDef(SheetInternalType.LabelText);
        Sheet sheet = SheetUtil.createSheet(sheetDef);
        SheetParameter.setParameter(sheet,"PatNum",patNum);
        sheet.Parameters.Add(new SheetParameter(false,"text"));
        SheetParameter.setParameter(sheet,"text",text);
        SheetFiller.fillFields(sheet);
        try
        {
            SheetPrinting.print(sheet);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * 
    */
    public static void printCarriers(List<long> carrierNums) throws Exception {
        SheetDef sheetDef = SheetsInternal.getSheetDef(SheetInternalType.LabelCarrier);
        List<Sheet> sheetBatch = SheetUtil.CreateBatch(sheetDef, carrierNums);
        try
        {
            SheetPrinting.PrintBatch(sheetBatch);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * 
    */
    public static void printCarrier(long carrierNum) throws Exception {
        SheetDef sheetDef;
        List<SheetDef> customSheetDefs = SheetDefs.getCustomForType(SheetTypeEnum.LabelCarrier);
        if (customSheetDefs.Count == 0)
        {
            sheetDef = SheetsInternal.getSheetDef(SheetInternalType.LabelCarrier);
        }
        else
        {
            sheetDef = customSheetDefs[0];
            SheetDefs.getFieldsAndParameters(sheetDef);
        } 
        Sheet sheet = SheetUtil.createSheet(sheetDef);
        SheetParameter.setParameter(sheet,"CarrierNum",carrierNum);
        SheetFiller.fillFields(sheet);
        try
        {
            SheetPrinting.print(sheet);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * 
    */
    public static void printReferral(long referralNum) throws Exception {
        SheetDef sheetDef;
        List<SheetDef> customSheetDefs = SheetDefs.getCustomForType(SheetTypeEnum.LabelReferral);
        if (customSheetDefs.Count == 0)
        {
            sheetDef = SheetsInternal.getSheetDef(SheetInternalType.LabelReferral);
        }
        else
        {
            sheetDef = customSheetDefs[0];
            SheetDefs.getFieldsAndParameters(sheetDef);
        } 
        Sheet sheet = SheetUtil.createSheet(sheetDef);
        SheetParameter.setParameter(sheet,"ReferralNum",referralNum);
        SheetFiller.fillFields(sheet);
        try
        {
            SheetPrinting.print(sheet);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * 
    */
    public static void printAppointment(long aptNum) throws Exception {
        SheetDef sheetDef;
        List<SheetDef> customSheetDefs = SheetDefs.getCustomForType(SheetTypeEnum.LabelAppointment);
        if (customSheetDefs.Count == 0)
        {
            sheetDef = SheetsInternal.getSheetDef(SheetInternalType.LabelAppointment);
        }
        else
        {
            sheetDef = customSheetDefs[0];
            SheetDefs.getFieldsAndParameters(sheetDef);
        } 
        Sheet sheet = SheetUtil.createSheet(sheetDef);
        SheetParameter.setParameter(sheet,"AptNum",aptNum);
        SheetFiller.fillFields(sheet);
        try
        {
            SheetPrinting.print(sheet);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

}


