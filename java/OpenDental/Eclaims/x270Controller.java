//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:33 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.StringSupport;
import OpenDental.Eclaims.ClaimConnect;
import OpenDental.FormEtrans270Edit;
import OpenDental.Lan;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.EclaimsCommBridge;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransMessageText;
import OpenDentBusiness.EtransMessageTexts;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.EtransType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.X12object;
import OpenDentBusiness.X270;
import OpenDentBusiness.X271;
import OpenDentBusiness.X277;
import OpenDentBusiness.X997;
import OpenDentBusiness.X999;

/**
* Handles all 270/270 logic.  Contains UI elements.  Passes off the 270 to the correct clearinghouse.
*/
public class x270Controller   
{
    /**
    * The insplan that's passed in need not be properly updated to the database first.
    */
    public static void requestBenefits(Clearinghouse clearhouse, InsPlan plan, long patNum, Carrier carrier, List<Benefit> benList, long patPlanNum, InsSub insSub) throws Exception {
        Patient pat = Patients.getPat(patNum);
        Patient subsc = Patients.getPat(insSub.Subscriber);
        Clinic clinic = Clinics.getClinic(pat.ClinicNum);
        Provider billProv = Providers.getProv(Providers.getBillingProvNum(pat.PriProv,pat.ClinicNum));
        //validation.  Throw exception if missing info----------------------------------------
        String validationResult = X270.validate(clearhouse,carrier,billProv,clinic,plan,subsc,insSub);
        if (!StringSupport.equals(validationResult, ""))
        {
            throw new Exception(Lan.g("FormInsPlan","Please fix the following errors first:") + "\r\n" + validationResult);
        }
         
        //create a 270 message---------------------------------------------------------------
        String x12message = X270.generateMessageText(clearhouse,carrier,billProv,clinic,plan,subsc,insSub);
        EtransMessageText etransMessageText = new EtransMessageText();
        etransMessageText.MessageText = x12message;
        EtransMessageTexts.insert(etransMessageText);
        //attach it to an etrans-------------------------------------------------------------
        Etrans etrans = new Etrans();
        etrans.DateTimeTrans = DateTime.Now;
        etrans.ClearingHouseNum = clearhouse.ClearinghouseNum;
        etrans.Etype = EtransType.BenefitInquiry270;
        etrans.PlanNum = plan.PlanNum;
        etrans.InsSubNum = insSub.InsSubNum;
        etrans.EtransMessageTextNum = etransMessageText.EtransMessageTextNum;
        Etranss.insert(etrans);
        //send the 270----------------------------------------------------------------------
        String x12response = "";
        try
        {
            //a connection error here needs to bubble up
            if (clearhouse.CommBridge == EclaimsCommBridge.ClaimConnect)
            {
                x12response = ClaimConnect.benefits270(clearhouse,x12message);
            }
             
        }
        catch (Exception ex)
        {
            EtransMessageTexts.delete(etrans.EtransMessageTextNum);
            Etranss.delete(etrans.EtransNum);
            throw new ApplicationException(Lan.g("FormInsPlan","Connection Error:") + "\r\n" + ex.GetType().Name + "\r\n" + ex.Message);
        }

        //start to process the 271----------------------------------------------------------
        X271 x271 = null;
        if (X12object.isX12(x12response))
        {
            X12object x12obj = new X12object(x12response);
            if (x12obj.is271())
            {
                x271 = new X271(x12response);
            }
             
        }
        else
        {
            //not a 997, 999, 277 or 271
            EtransMessageTexts.delete(etrans.EtransMessageTextNum);
            Etranss.delete(etrans.EtransNum);
            throw new ApplicationException(Lan.g("FormInsPlan","Error:") + "\r\n" + x12response);
        } 
        /*
        			//In realtime mode, X12 limits the request to one patient.
        			//We will always use the subscriber.
        			//So all EB segments are for the subscriber.
        			List<EB271> listEB=new List<EB271>();
        			EB271 eb;
        			if(x271 != null) {
        				for(int i=0;i<x271.Segments.Count;i++) {
        					if(x271.Segments[i].SegmentID != "EB") {
        						continue;
        					}
        					eb=new EB271(x271.Segments[i]);
        					listEB.Add(eb);
        				}
        			}*/
        //create an etrans for the 271------------------------------------------------------
        etransMessageText = new EtransMessageText();
        etransMessageText.MessageText = x12response;
        EtransMessageTexts.insert(etransMessageText);
        Etrans etrans271 = new Etrans();
        etrans271.DateTimeTrans = DateTime.Now;
        etrans271.ClearingHouseNum = clearhouse.ClearinghouseNum;
        etrans271.Etype = EtransType.TextReport;
        if (X12object.isX12(x12response))
        {
            //this shouldn't need to be tested because it was tested above.
            if (x271 == null)
            {
                X12object Xobj = new X12object(x12response);
                if (Xobj.is997())
                {
                    etrans271.Etype = EtransType.Acknowledge_997;
                }
                else if (Xobj.is999())
                {
                    etrans271.Etype = EtransType.Acknowledge_999;
                }
                else if (X277.is277(Xobj))
                {
                    etrans271.Etype = EtransType.StatusNotify_277;
                }
                   
            }
            else
            {
                etrans271.Etype = EtransType.BenefitResponse271;
            } 
        }
         
        etrans271.PlanNum = plan.PlanNum;
        etrans271.InsSubNum = insSub.InsSubNum;
        etrans271.EtransMessageTextNum = etransMessageText.EtransMessageTextNum;
        Etranss.insert(etrans271);
        etrans.AckEtransNum = etrans271.EtransNum;
        if (etrans271.Etype == EtransType.Acknowledge_997)
        {
            X997 x997 = new X997(x12response);
            String error997 = x997.getHumanReadable();
            etrans.Note = "Error: " + error997;
            //"Malformed document sent.  997 error returned.";
            Etranss.update(etrans);
            MessageBox.Show(etrans.Note);
            return ;
        }
        else //CodeBase.MsgBoxCopyPaste msgbox=new CodeBase.MsgBoxCopyPaste(etrans.Note);
        //msgbox.ShowDialog();
        //don't show the 270 interface.
        if (etrans271.Etype == EtransType.Acknowledge_999)
        {
            X999 x999 = new X999(x12response);
            String error999 = x999.getHumanReadable();
            etrans.Note = "Error: " + error999;
            //"Malformed document sent.  999 error returned.";
            Etranss.update(etrans);
            MessageBox.Show(etrans.Note);
            return ;
        }
        else //CodeBase.MsgBoxCopyPaste msgbox=new CodeBase.MsgBoxCopyPaste(etrans.Note);
        //msgbox.ShowDialog();
        //don't show the 270 interface.
        if (etrans271.Etype == EtransType.StatusNotify_277)
        {
            X277 x277 = new X277(x12response);
            String error277 = x277.getHumanReadable();
            etrans.Note = "Error: " + error277;
            //"Malformed document sent.  277 error returned.";
            Etranss.update(etrans);
            MessageBox.Show(etrans.Note);
            return ;
        }
        else
        {
            //CodeBase.MsgBoxCopyPaste msgbox=new CodeBase.MsgBoxCopyPaste(etrans.Note);
            //msgbox.ShowDialog();
            //don't show the 270 interface.
            //271
            String processingerror = x271.getProcessingError();
            if (!StringSupport.equals(processingerror, ""))
            {
                etrans.Note = processingerror;
                Etranss.update(etrans);
                MessageBox.Show(etrans.Note);
                return ;
            }
            else
            {
                //CodeBase.MsgBoxCopyPaste msgbox=new CodeBase.MsgBoxCopyPaste(etrans.Note);
                //msgbox.ShowDialog();
                //don't show the 270 interface.
                etrans.Note = "Normal 271 response.";
            } 
        }   
        //change this later to be explanatory of content.
        Etranss.update(etrans);
        //show the user a list of benefits to pick from for import--------------------------
        FormEtrans270Edit formE = new FormEtrans270Edit(patPlanNum,plan.PlanNum,insSub.InsSubNum);
        formE.EtransCur = etrans;
        formE.IsInitialResponse = true;
        formE.benList = benList;
        formE.ShowDialog();
    }

}


