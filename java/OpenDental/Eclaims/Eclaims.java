//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:32 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.StringSupport;
import OpenDental.ClearinghouseL;
import OpenDental.Eclaims.AOS;
import OpenDental.Eclaims.BCBSGA;
import OpenDental.Eclaims.Canadian;
import OpenDental.Eclaims.ClaimConnect;
import OpenDental.Eclaims.ClaimX;
import OpenDental.Eclaims.DentiCal;
import OpenDental.Eclaims.Dutch;
import OpenDental.Eclaims.EmdeonMedical;
import OpenDental.Eclaims.Inmediata;
import OpenDental.Eclaims.MercuryDE;
import OpenDental.Eclaims.NHS;
import OpenDental.Eclaims.RECS;
import OpenDental.Eclaims.Renaissance;
import OpenDental.Eclaims.WebMD;
import OpenDental.Eclaims.x837Controller;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.ClaimSendQueueItem;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clearinghouses;
import OpenDentBusiness.EclaimsCommBridge;
import OpenDentBusiness.ElectronicClaimFormat;
import OpenDentBusiness.EnumClaimMedType;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.EtransType;
import OpenDentBusiness.X837_4010;
import OpenDentBusiness.X837_5010;

/**
* Summary description for Eclaims.
*/
public class Eclaims   
{
    /**
    * 
    */
    public Eclaims() throws Exception {
    }

    /**
    * Supply a list of ClaimSendQueueItems. Called from FormClaimSend.  Can only send to one clearinghouse at a time.  Able to send just send one claim.  Cannot include Canadian.
    */
    public static void sendBatch(List<ClaimSendQueueItem> queueItems, Clearinghouse clearhouse, EnumClaimMedType medType) throws Exception {
        String messageText = "";
        if (clearhouse.Eformat == ElectronicClaimFormat.Canadian)
        {
            MsgBox.show("Eclaims","Cannot send Canadian claims as part of Eclaims.SendBatch.");
            return ;
        }
         
        //get next batch number for this clearinghouse
        int batchNum = Clearinghouses.getNextBatchNumber(clearhouse);
        //---------------------------------------------------------------------------------------
        //Create the claim file for this clearinghouse
        if (clearhouse.Eformat == ElectronicClaimFormat.x837D_4010 || clearhouse.Eformat == ElectronicClaimFormat.x837D_5010_dental || clearhouse.Eformat == ElectronicClaimFormat.x837_5010_med_inst)
        {
            messageText = x837Controller.SendBatch(queueItems, batchNum, clearhouse, medType);
        }
        else if (clearhouse.Eformat == ElectronicClaimFormat.Renaissance)
        {
            messageText = Renaissance.SendBatch(queueItems, batchNum);
        }
        else if (clearhouse.Eformat == ElectronicClaimFormat.Dutch)
        {
            messageText = Dutch.SendBatch(queueItems, batchNum);
        }
        else
        {
            messageText = "";
        }   
        //(ElectronicClaimFormat.None does not get sent)
        if (StringSupport.equals(messageText, ""))
        {
            return ;
        }
         
        //if failed to create claim file properly,
        //don't launch program or change claim status
        //----------------------------------------------------------------------------------------
        //Launch Client Program for this clearinghouse if applicable
        if (clearhouse.CommBridge == EclaimsCommBridge.None)
        {
            attemptLaunch(clearhouse,batchNum);
        }
        else if (clearhouse.CommBridge == EclaimsCommBridge.WebMD)
        {
            if (!WebMD.launch(clearhouse,batchNum))
            {
                MessageBox.Show(Lan.g("Eclaims","Error sending."));
                return ;
            }
             
        }
        else if (clearhouse.CommBridge == EclaimsCommBridge.BCBSGA)
        {
            if (!BCBSGA.launch(clearhouse,batchNum))
            {
                MessageBox.Show(Lan.g("Eclaims","Error sending."));
                return ;
            }
             
        }
        else if (clearhouse.CommBridge == EclaimsCommBridge.Renaissance)
        {
            attemptLaunch(clearhouse,batchNum);
        }
        else if (clearhouse.CommBridge == EclaimsCommBridge.ClaimConnect)
        {
            if (!ClaimConnect.launch(clearhouse,batchNum))
            {
                MessageBox.Show(Lan.g("Eclaims","Error sending."));
                return ;
            }
             
        }
        else if (clearhouse.CommBridge == EclaimsCommBridge.RECS)
        {
            if (!RECS.launch(clearhouse,batchNum))
            {
                MessageBox.Show("Claim file created, but could not launch RECS client.");
            }
             
        }
        else //continue;
        if (clearhouse.CommBridge == EclaimsCommBridge.Inmediata)
        {
            if (!Inmediata.launch(clearhouse,batchNum))
            {
                MessageBox.Show("Claim file created, but could not launch Inmediata client.");
            }
             
        }
        else //continue;
        if (clearhouse.CommBridge == EclaimsCommBridge.AOS)
        {
            // added by SPK 7/13/05
            if (!AOS.launch(clearhouse,batchNum))
            {
                MessageBox.Show("Claim file created, but could not launch AOS Communicator.");
            }
             
        }
        else //continue;
        if (clearhouse.CommBridge == EclaimsCommBridge.PostnTrack)
        {
            attemptLaunch(clearhouse,batchNum);
        }
        else if (clearhouse.CommBridge == EclaimsCommBridge.MercuryDE)
        {
            if (!MercuryDE.launch(clearhouse,batchNum))
            {
                MsgBox.show("Eclaims","Error sending.");
                return ;
            }
             
        }
        else if (clearhouse.CommBridge == EclaimsCommBridge.ClaimX)
        {
            if (!ClaimX.launch(clearhouse,batchNum))
            {
                MessageBox.Show("Claim file created, but encountered an error while launching ClaimX Client.");
            }
             
        }
        else if (clearhouse.CommBridge == EclaimsCommBridge.EmdeonMedical)
        {
            if (!EmdeonMedical.launch(clearhouse,batchNum,medType))
            {
                MessageBox.Show(Lan.g("Eclaims","Error sending."));
                return ;
            }
             
        }
        else if (clearhouse.CommBridge == EclaimsCommBridge.DentiCal)
        {
            if (!DentiCal.launch(clearhouse,batchNum))
            {
                return ;
            }
             
        }
        else //Error message was already shown inside of the DentiCal class.
        if (clearhouse.CommBridge == EclaimsCommBridge.NHS)
        {
            if (!NHS.launch(clearhouse,batchNum))
            {
                MessageBox.Show(Lan.g("Eclaims","Error sending."));
                return ;
            }
             
        }
                      
        //----------------------------------------------------------------------------------------
        //finally, mark the claims sent. (only if not Canadian)
        EtransType etype = EtransType.ClaimSent;
        if (clearhouse.Eformat == ElectronicClaimFormat.Renaissance)
        {
            etype = EtransType.Claim_Ren;
        }
         
        if (clearhouse.Eformat != ElectronicClaimFormat.Canadian)
        {
            for (int j = 0;j < queueItems.Count;j++)
            {
                Etrans etrans = Etranss.SetClaimSentOrPrinted(queueItems[j].ClaimNum, queueItems[j].PatNum, clearhouse.ClearinghouseNum, etype, batchNum);
                Etranss.setMessage(etrans.EtransNum,messageText);
            }
        }
         
    }

    /**
    * If no comm bridge is selected for a clearinghouse, this launches any client program the user has entered.  We do not want to cause a rollback, so no return value.
    */
    private static void attemptLaunch(Clearinghouse clearhouse, int batchNum) throws Exception {
        if (StringSupport.equals(clearhouse.ClientProgram, ""))
        {
            return ;
        }
         
        if (!File.Exists(clearhouse.ClientProgram))
        {
            MessageBox.Show(clearhouse.ClientProgram + " " + Lan.g("Eclaims","does not exist."));
            return ;
        }
         
        try
        {
            Process.Start(clearhouse.ClientProgram);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g("Eclaims","Client program could not be started.  It may already be running. You must open your client program to finish sending claims."));
        }
    
    }

    /**
    * Fills the missing data field on the queueItem that was passed in.  This contains all missing data on this claim.  Claim will not be allowed to be sent electronically unless this string comes back empty.
    */
    public static void getMissingData(ClaimSendQueueItem queueItem) throws Exception {
        //, out string warnings){
        queueItem.Warnings = "";
        queueItem.MissingData = "";
        Clearinghouse clearhouse = ClearinghouseL.getClearinghouse(queueItem.ClearinghouseNum,true);
        //Suppress error message in case no default medical clearinghouse set.
        //this is usually just the default clearinghouse or the clearinghouse for the PayorID.
        if (clearhouse == null)
        {
            if (queueItem.MedType == EnumClaimMedType.Dental)
            {
                queueItem.MissingData += "No default dental clearinghouse set.";
            }
            else
            {
                queueItem.MissingData += "No default medical/institutional clearinghouse set.";
            } 
            return ;
        }
         
        if (clearhouse.Eformat == ElectronicClaimFormat.x837D_4010)
        {
            X837_4010.validate(queueItem);
        }
        else //,out warnings);
        //return;
        if (clearhouse.Eformat == ElectronicClaimFormat.x837D_5010_dental || clearhouse.Eformat == ElectronicClaimFormat.x837_5010_med_inst)
        {
            X837_5010.validate(queueItem);
        }
        else //,out warnings);
        //return;
        if (clearhouse.Eformat == ElectronicClaimFormat.Renaissance)
        {
            queueItem.MissingData = Renaissance.getMissingData(queueItem);
        }
        else //return;
        if (clearhouse.Eformat == ElectronicClaimFormat.Canadian)
        {
            queueItem.MissingData = Canadian.getMissingData(queueItem);
        }
        else //return;
        if (clearhouse.Eformat == ElectronicClaimFormat.Dutch)
        {
            Dutch.getMissingData(queueItem);
        }
             
    }

}


//,out warnings);
//return;
//return "";