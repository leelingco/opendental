//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.OrionProc;
import OpenDentBusiness.OrionStatus;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Sites;
import OpenDental.FormProcEditExplain;

public class FormProcEditExplain  extends Form 
{

    public static String Changes = new String();
    public static String Explanation = new String();
    public boolean dpcChange = new boolean();
    private boolean radioChange = new boolean();
    private String radioText = new String();
    public FormProcEditExplain() throws Exception {
        initializeComponent();
        Lan.F(this);
        textSummary.Text = Changes;
    }

    private void formProcEditExplain_Load(Object sender, EventArgs e) throws Exception {
        textSummary.Text = Changes;
        if (dpcChange)
        {
            groupBoxDPC.Enabled = true;
        }
         
    }

    public static String getChanges(Procedure procCur, Procedure procOld, OrionProc orionProcCur, OrionProc orionProcOld) throws Exception {
        Changes = "";
        if (orionProcOld.DPC != orionProcCur.DPC)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "DPC changed from " + POut.String(orionProcOld.DPC.ToString()) + " to " + POut.String(orionProcCur.DPC.ToString()) + ".";
        }
         
        if (orionProcOld.DPCpost != orionProcCur.DPCpost)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "DPC Post Visit changed from " + POut.String(orionProcOld.DPCpost.ToString()) + " to " + POut.String(orionProcCur.DPCpost.ToString()) + ".";
        }
         
        //PatNum, AptNum, PlannedAptNum should never change---------------------------------------------------------------------------------------------
        if (procOld.PatNum != procCur.PatNum)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Patient Num changed from " + procOld.PatNum + " to " + procCur.PatNum + ".";
        }
         
        if (procOld.AptNum != procCur.AptNum)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Apt Num changed from " + procOld.AptNum + " to " + procCur.AptNum + ".";
        }
         
        if (procOld.PlannedAptNum != procCur.PlannedAptNum)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Planned Apt Num changed from " + procOld.PlannedAptNum + " to " + procCur.PlannedAptNum + ".";
        }
         
        //Date and time related fields------------------------------------------------------------------------------------------------------------------
        if (procOld.DateEntryC.Date != procCur.DateEntryC.Date)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Date Entry changed from " + procOld.DateEntryC.ToShortDateString() + " to " + procCur.DateEntryC.ToShortDateString() + ".";
        }
         
        if (procOld.ProcDate.Date != procCur.ProcDate.Date)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Proc Date changed from " + procOld.ProcDate.ToShortDateString() + " to " + procCur.ProcDate.ToShortDateString() + ".";
        }
         
        //if(procOld.StartTime != procCur.StartTime) {
        //  if(Changes!=""){ Changes+="\r\n";}
        //  Changes+="Start Time changed from "+procOld.StartTime+" to "+procCur.StartTime+".";
        //}
        //if(procOld.StopTime != procCur.StopTime) {
        //  if(Changes!=""){ Changes+="\r\n";}
        //  Changes+="Stop Time changed from "+procOld.StopTime+" to "+procCur.StopTime+".";
        //}
        if (procOld.ProcTime != procCur.ProcTime)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Procedure Time changed from " + (StringSupport.equals(PIn.DateT(procOld.ProcTime.ToString()).ToShortTimeString(), "12:00 AM") ? "none" : PIn.DateT(procOld.ProcTime.ToString()).ToShortTimeString()) + " to " + (StringSupport.equals(PIn.DateT(procCur.ProcTime.ToString()).ToShortTimeString(), "12:00 AM") ? "none" : PIn.DateT(procCur.ProcTime.ToString()).ToShortTimeString()) + ".";
        }
         
        if (procOld.ProcTimeEnd != procCur.ProcTimeEnd)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Procedure End Time changed from " + (StringSupport.equals(PIn.DateT(procOld.ProcTimeEnd.ToString()).ToShortTimeString(), "12:00 AM") ? "none" : PIn.DateT(procOld.ProcTimeEnd.ToString()).ToShortTimeString()) + " to " + (StringSupport.equals(PIn.DateT(procCur.ProcTimeEnd.ToString()).ToShortTimeString(), "12:00 AM") ? "none" : PIn.DateT(procCur.ProcTimeEnd.ToString()).ToShortTimeString()) + ".";
        }
         
        //Procedure, related areas, amount, hide graphics, etc.-----------------------------------------------------------------------------------------
        if (procOld.CodeNum != procCur.CodeNum)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Procedure changed from " + ProcedureCodes.getLaymanTerm(procOld.CodeNum) + " to " + ProcedureCodes.getLaymanTerm(procCur.CodeNum) + ".";
        }
         
        if (procOld.ProcFee != procCur.ProcFee)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Proc Fee changed from $" + procOld.ProcFee.ToString("F") + " to $" + procCur.ProcFee.ToString("F") + ".";
        }
         
        if (!StringSupport.equals(procOld.ToothNum, procCur.ToothNum))
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Tooth Num changed from " + procOld.ToothNum + " to " + procCur.ToothNum + ".";
        }
         
        if (!StringSupport.equals(procOld.Surf, procCur.Surf))
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Surface changed from " + procOld.Surf + " to " + procCur.Surf + ".";
        }
         
        if (!StringSupport.equals(procOld.ToothRange, procCur.ToothRange))
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Tooth Range changed from " + procOld.ToothRange + " to " + procCur.ToothRange + ".";
        }
         
        if (procOld.HideGraphics != procCur.HideGraphics)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Hide Graphics changed from " + (procOld.HideGraphics ? "Hide Graphics" : "Do Not Hide Graphics") + " to " + (procCur.HideGraphics ? "Hide Graphics" : "Do Not Hide Graphics") + ".";
        }
         
        //Provider, Diagnosis, Priority, Place of Service, Clinic, Site---------------------------------------------------------------------------------
        if (procOld.ProvNum != procCur.ProvNum)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Provider changed from " + Providers.getAbbr(procOld.ProvNum) + " to " + Providers.getAbbr(procCur.ProvNum) + ".";
        }
         
        if (procOld.Dx != procCur.Dx)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Diagnosis changed from " + DefC.getDef(DefCat.Diagnosis,procOld.Dx).ItemName + " to " + DefC.getDef(DefCat.Diagnosis,procCur.Dx).ItemName + ".";
        }
         
        if (procOld.Priority != procCur.Priority)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Priority changed from " + ((procOld.Priority != 0) ? DefC.getDef(DefCat.TxPriorities,procOld.Priority).ItemName : "no priority") + " to " + ((procCur.Priority != 0) ? DefC.getDef(DefCat.TxPriorities,procCur.Priority).ItemName : "no priority") + ".";
        }
         
        if (procOld.PlaceService != procCur.PlaceService)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Place of Service changed from " + procOld.PlaceService.ToString() + " to " + procCur.PlaceService.ToString() + ".";
        }
         
        if (procOld.ClinicNum != procCur.ClinicNum)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Clinic changed from " + Clinics.getDesc(procOld.ClinicNum) + " to " + Clinics.getDesc(procCur.ClinicNum) + ".";
        }
         
        if (procOld.SiteNum != procCur.SiteNum)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Site changed from " + (procOld.SiteNum == 0 ? "none" : Sites.getDescription(procOld.SiteNum)) + " to " + (procCur.SiteNum == 0 ? "none" : Sites.getDescription(procCur.SiteNum)) + ".";
        }
         
        //Prosthesis reverse lookup---------------------------------------------------------------------------------------------------------------------
        if (!StringSupport.equals(procOld.Prosthesis, procCur.Prosthesis))
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            String prosthesisOld = new String();
            System.String.APPLY __dummyScrutVar0 = procOld.Prosthesis.ToString();
            if (__dummyScrutVar0.equals(""))
            {
                prosthesisOld = "no";
            }
            else if (__dummyScrutVar0.equals("I"))
            {
                prosthesisOld = "Initial";
            }
            else if (__dummyScrutVar0.equals("R"))
            {
                prosthesisOld = "Replacement";
            }
            else
            {
                prosthesisOld = "error";
            }   
            String prosthesisCur = new String();
            System.String.APPLY __dummyScrutVar1 = procCur.Prosthesis.ToString();
            if (__dummyScrutVar1.equals(""))
            {
                prosthesisCur = "no";
            }
            else if (__dummyScrutVar1.equals("I"))
            {
                prosthesisCur = "Initial";
            }
            else if (__dummyScrutVar1.equals("R"))
            {
                prosthesisCur = "Replacement";
            }
            else
            {
                prosthesisCur = "error";
            }   
            Changes += "Prosthesis changed from " + prosthesisOld + " to " + prosthesisCur + ".";
        }
         
        if (procOld.DateOriginalProsth.Date != procCur.DateOriginalProsth.Date)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Date of Original Prosthesis changed from " + procOld.DateOriginalProsth.ToShortDateString() + " to " + procCur.DateOriginalProsth.ToShortDateString() + ".";
        }
         
        //Claim Note & Orion Proc Fields----------------------------------------------------------------------------------------------------------------
        if (!StringSupport.equals(procOld.ClaimNote, procCur.ClaimNote))
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Claim Note changed from " + (StringSupport.equals(procOld.ClaimNote, "") ? "none" : "'" + procOld.ClaimNote + "'") + " to " + (StringSupport.equals(procCur.ClaimNote, "") ? "none" : "'" + procCur.ClaimNote + "'");
        }
         
        if (orionProcOld.OrionProcNum != orionProcCur.OrionProcNum)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Orion Proc Num changed from " + POut.Long(orionProcOld.OrionProcNum) + " to " + POut.Long(orionProcCur.OrionProcNum) + ".";
        }
         
        if (orionProcOld.ProcNum != orionProcCur.ProcNum)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Proc Num changed from " + POut.Long(orionProcOld.ProcNum) + " to " + POut.Long(orionProcCur.ProcNum) + ".";
        }
         
        //Orion Status Reverse Lookup for Description----------------------------------//None is equivalent to TP---------------------------------------
        if (orionProcOld.Status2 != orionProcCur.Status2 && !(orionProcOld.Status2 == OrionStatus.None && orionProcCur.Status2 == OrionStatus.TP))
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            String[] status2 = new String[2];
            String[] status2Desc = new String[2];
            status2[0] = orionProcOld.Status2.ToString();
            status2[1] = orionProcCur.Status2.ToString();
            for (int i = 0;i < 2;i++)
            {
                System.Array<System.String>.INDEXER __dummyScrutVar2 = status2[i];
                if (__dummyScrutVar2.equals("None"))
                {
                    status2Desc[i] = "TP-treatment planned";
                }
                else if (__dummyScrutVar2.equals("TP"))
                {
                    status2Desc[i] = "TP-treatment planned";
                }
                else if (__dummyScrutVar2.equals("C"))
                {
                    status2Desc[i] = "C-completed";
                }
                else if (__dummyScrutVar2.equals("E"))
                {
                    status2Desc[i] = "E-existing prior to incarceration";
                }
                else if (__dummyScrutVar2.equals("R"))
                {
                    status2Desc[i] = "R-refused treatment";
                }
                else if (__dummyScrutVar2.equals("RO"))
                {
                    status2Desc[i] = "RO-referred out to specialist";
                }
                else if (__dummyScrutVar2.equals("CS"))
                {
                    status2Desc[i] = "CS-completed by specialist";
                }
                else if (__dummyScrutVar2.equals("CR"))
                {
                    status2Desc[i] = "CR-completed by registry";
                }
                else if (__dummyScrutVar2.equals("CA_Tx"))
                {
                    status2Desc[i] = "CA_Tx-cancelled, tx plan changed";
                }
                else if (__dummyScrutVar2.equals("CA_EPRD"))
                {
                    status2Desc[i] = "CA_EPRD-cancelled, eligible parole";
                }
                else if (__dummyScrutVar2.equals("CA_P/D"))
                {
                    status2Desc[i] = "CA_P/D--cancelled, parole/discharge";
                }
                else if (__dummyScrutVar2.equals("S"))
                {
                    status2Desc[i] = "S-suspended, unacceptable plaque";
                }
                else if (__dummyScrutVar2.equals("ST"))
                {
                    status2Desc[i] = "ST-stop clock, multi visit";
                }
                else if (__dummyScrutVar2.equals("W"))
                {
                    status2Desc[i] = "W-watch";
                }
                else if (__dummyScrutVar2.equals("A"))
                {
                    status2Desc[i] = "A-alternative";
                }
                else
                {
                    status2Desc[i] = "error";
                }               
            }
            Changes += "Orion Procedure Status changed from " + status2Desc[0] + " to " + status2Desc[1] + ".";
        }
         
        //Other orion fields----------------------------------------------------------------------------------------------------------------------------
        if (orionProcOld.DateScheduleBy.Date != orionProcCur.DateScheduleBy.Date)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Date Schedule By changed from " + orionProcOld.DateScheduleBy.ToShortDateString() + " to " + orionProcCur.DateScheduleBy.ToShortDateString() + ".";
        }
         
        if (orionProcOld.DateStopClock.Date != orionProcCur.DateStopClock.Date)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Date Stop Clock changed from " + orionProcOld.DateStopClock.ToShortDateString() + " to " + orionProcCur.DateStopClock.ToShortDateString() + ".";
        }
         
        if (orionProcOld.IsOnCall != orionProcCur.IsOnCall)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Is On Call changed from " + (orionProcOld.IsOnCall ? "Is On Call" : "Is Not On Call") + " to " + (orionProcCur.IsOnCall ? "Is On Call" : "Is Not On Call") + ".";
        }
         
        if (orionProcOld.IsEffectiveComm != orionProcCur.IsEffectiveComm)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Is Effective Comm changed from " + (orionProcOld.IsEffectiveComm ? "Is an Effective Communicator" : "Is Not an Effective Communicator") + " to " + (orionProcCur.IsEffectiveComm ? "Is an Effective Communicator" : "Is Not an Effective Communicator") + ".";
        }
         
        if (orionProcOld.IsRepair != orionProcCur.IsRepair)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Is Repair changed from " + (orionProcOld.IsRepair ? "Is a Repair" : "Is Not a Repair") + " to " + (orionProcCur.IsRepair ? "Is a Repair" : "Is Not a Repair") + ".";
        }
         
        //Medical fields--------------------------------------------------------------------------------------------------------------------------------
        if (!StringSupport.equals(procOld.MedicalCode, procCur.MedicalCode))
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Medical Code changed from " + (StringSupport.equals(procOld.MedicalCode, "") ? "none" : procOld.MedicalCode) + " to " + (StringSupport.equals(procCur.MedicalCode, "") ? "none" : procCur.MedicalCode) + ".";
        }
         
        if (!StringSupport.equals(procOld.DiagnosticCode, procCur.DiagnosticCode))
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Diagnostic Code changed from " + (StringSupport.equals(procOld.DiagnosticCode, "") ? "none" : procOld.DiagnosticCode) + " to " + (StringSupport.equals(procCur.DiagnosticCode, "") ? "none" : procCur.DiagnosticCode) + ".";
        }
         
        if (procOld.IsPrincDiag != procCur.IsPrincDiag)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Is Princ Diag changed from " + (procOld.IsPrincDiag ? "Principal Diagnosis" : "Not Principal Diagnosis") + " to " + (procCur.IsPrincDiag ? "Principal Diagnosis" : "Not Principal Diagnosis") + ".";
        }
         
        //if(procOld.RevCode != procCur.RevCode) {
        //  if(Changes!=""){ Changes+="\r\n";}
        //  Changes+="Rev Code changed from "+POut.String(procOld.RevCode)+"' to '"+POut.String(procCur.RevCode)+".";
        //}
        //Proc status and billing fields----------------------------------------------------------------------------------------------------------------
        if (procOld.ProcStatus != procCur.ProcStatus)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Procedure Status changed from " + procOld.ProcStatus.ToString() + " to " + procCur.ProcStatus.ToString() + ".";
        }
         
        if (procOld.DateTP.Date != procCur.DateTP.Date && procOld.DateTP.Date != DateTime.MinValue.Date)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Date TP changed from " + procOld.DateTP.ToShortDateString() + " to " + procCur.DateTP.ToShortDateString() + ".";
        }
         
        //if(procOld.BillingTypeOne != procCur.BillingTypeOne) {
        //  if(Changes!=""){ Changes+="\r\n";}
        //  Changes+="Billing Type One changed from "+(procOld.BillingTypeOne!=0?DefC.GetDef(DefCat.BillingTypes,procOld.BillingTypeOne).ItemName:"none")
        //		+" to "+(procCur.BillingTypeOne!=0?DefC.GetDef(DefCat.BillingTypes,procCur.BillingTypeOne).ItemName:"none")+".";
        //}
        //if(procOld.BillingTypeTwo != procCur.BillingTypeTwo) {
        //  if(Changes!=""){ Changes+="\r\n";}
        //  Changes+="Billing Type Two changed from "+(procOld.BillingTypeTwo!=0?DefC.GetDef(DefCat.BillingTypes,procOld.BillingTypeTwo).ItemName:"none")
        //		+" to "+(procCur.BillingTypeTwo!=0?DefC.GetDef(DefCat.BillingTypes,procCur.BillingTypeTwo).ItemName:"none")+".";
        //}
        if (procOld.ProcNumLab != procCur.ProcNumLab)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Proc Num Lab changed from " + POut.Long(procOld.ProcNumLab) + " to " + POut.Long(procCur.ProcNumLab) + ".";
        }
         
        //if(procOld.UnitCode != procCur.UnitCode) {
        //  if(Changes!=""){ Changes+="\r\n";}
        //  Changes+="Unit Code changed from "+POut.String(procOld.UnitCode)+" to "+POut.String(procCur.UnitCode)+".";
        //}
        //UnitQty, Canadian Type Codes, and Note--------------------------------------------------------------------------------------------------------
        if (procOld.UnitQty != procCur.UnitQty)
        {
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Unit Quantity changed from " + POut.Int(procOld.UnitQty) + " to " + POut.Int(procCur.UnitQty) + ".";
        }
         
        //if(procOld.CanadianTypeCodes != procCur.CanadianTypeCodes) {
        //  if(Changes!=""){ Changes+="\r\n";}
        //  Changes+="Canadian Code Type changed from "+POut.String(procOld.CanadianTypeCodes)+" to "+POut.String(procCur.CanadianTypeCodes)+".";
        // }
        if (!StringSupport.equals(procOld.Note, procCur.Note) && !(procOld.Note == null && StringSupport.equals(procCur.Note, "")))
        {
            //Null note is equivalent to an empty note string.
            if (!StringSupport.equals(Changes, ""))
            {
                Changes += "\r\n";
            }
             
            Changes += "Note changed from " + (StringSupport.equals(procOld.Note, "") ? "none" : "'" + procOld.Note + "'") + " to " + (StringSupport.equals(procCur.Note, "") ? "none" : "'" + procCur.Note + "'");
        }
         
        return Changes;
    }

    private void radioButtonError_CheckedChanged(Object sender, EventArgs e) throws Exception {
        radioChange = true;
        radioText = "Entry error";
    }

    private void radioButtonNewProv_CheckedChanged(Object sender, EventArgs e) throws Exception {
        radioChange = true;
        radioText = "New provider";
    }

    private void radioButtonReAssign_CheckedChanged(Object sender, EventArgs e) throws Exception {
        radioChange = true;
        radioText = "Re-assignment";
    }

    private void radioButtonOther_CheckedChanged(Object sender, EventArgs e) throws Exception {
        radioChange = true;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (groupBoxDPC.Enabled)
        {
            if (radioButtonOther.Checked && StringSupport.equals(textExplanation.Text.Trim(), ""))
            {
                MsgBox.show(this,"Please explain why the DPC was changed.");
                return ;
            }
             
        }
        else if (StringSupport.equals(textExplanation.Text.Trim(), ""))
        {
            MsgBox.show(this,"Please explain why the above changes were made.");
            return ;
        }
          
        if (groupBoxDPC.Enabled && !radioChange)
        {
            MsgBox.show(this,"Please select a reason for DPC change.");
            return ;
        }
         
        Explanation = "Summary of Changes Made:\r\n" + Changes + "\r\nExplanation:\r\n" + textExplanation.Text;
        if (radioChange)
        {
            if (!radioButtonOther.Checked)
            {
                if (!StringSupport.equals(textExplanation.Text.Trim(), ""))
                {
                    Explanation += "\r\n";
                }
                 
                //New line if user typed explanation for other things changed.
                Explanation += "DPC change due to: " + radioText;
            }
             
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProcEditExplain.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textSummary = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textExplanation = new System.Windows.Forms.TextBox();
        this.radioButtonNewProv = new System.Windows.Forms.RadioButton();
        this.radioButtonReAssign = new System.Windows.Forms.RadioButton();
        this.radioButtonError = new System.Windows.Forms.RadioButton();
        this.radioButtonOther = new System.Windows.Forms.RadioButton();
        this.groupBoxDPC = new System.Windows.Forms.GroupBox();
        this.groupBoxDPC.SuspendLayout();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(625, 485);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(625, 526);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(159, 16);
        this.label1.TabIndex = 4;
        this.label1.Text = "Summary of Changes Made";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textSummary
        //
        this.textSummary.Location = new System.Drawing.Point(15, 28);
        this.textSummary.Multiline = true;
        this.textSummary.Name = "textSummary";
        this.textSummary.ReadOnly = true;
        this.textSummary.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textSummary.Size = new System.Drawing.Size(685, 200);
        this.textSummary.TabIndex = 5;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 236);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(159, 16);
        this.label2.TabIndex = 6;
        this.label2.Text = "Explanation";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textExplanation
        //
        this.textExplanation.Location = new System.Drawing.Point(15, 255);
        this.textExplanation.Multiline = true;
        this.textExplanation.Name = "textExplanation";
        this.textExplanation.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textExplanation.Size = new System.Drawing.Size(685, 200);
        this.textExplanation.TabIndex = 7;
        //
        // radioButtonNewProv
        //
        this.radioButtonNewProv.AutoSize = true;
        this.radioButtonNewProv.Location = new System.Drawing.Point(89, 29);
        this.radioButtonNewProv.Name = "radioButtonNewProv";
        this.radioButtonNewProv.Size = new System.Drawing.Size(88, 17);
        this.radioButtonNewProv.TabIndex = 11;
        this.radioButtonNewProv.Text = "New provider";
        this.radioButtonNewProv.UseVisualStyleBackColor = true;
        this.radioButtonNewProv.CheckedChanged += new System.EventHandler(this.radioButtonNewProv_CheckedChanged);
        //
        // radioButtonReAssign
        //
        this.radioButtonReAssign.AutoSize = true;
        this.radioButtonReAssign.Location = new System.Drawing.Point(183, 29);
        this.radioButtonReAssign.Name = "radioButtonReAssign";
        this.radioButtonReAssign.Size = new System.Drawing.Size(95, 17);
        this.radioButtonReAssign.TabIndex = 10;
        this.radioButtonReAssign.Text = "Re-assignment";
        this.radioButtonReAssign.UseVisualStyleBackColor = true;
        this.radioButtonReAssign.CheckedChanged += new System.EventHandler(this.radioButtonReAssign_CheckedChanged);
        //
        // radioButtonError
        //
        this.radioButtonError.AutoSize = true;
        this.radioButtonError.Location = new System.Drawing.Point(10, 29);
        this.radioButtonError.Name = "radioButtonError";
        this.radioButtonError.Size = new System.Drawing.Size(73, 17);
        this.radioButtonError.TabIndex = 9;
        this.radioButtonError.Text = "Entry error";
        this.radioButtonError.UseVisualStyleBackColor = true;
        this.radioButtonError.CheckedChanged += new System.EventHandler(this.radioButtonError_CheckedChanged);
        //
        // radioButtonOther
        //
        this.radioButtonOther.AutoSize = true;
        this.radioButtonOther.Location = new System.Drawing.Point(284, 29);
        this.radioButtonOther.Name = "radioButtonOther";
        this.radioButtonOther.Size = new System.Drawing.Size(51, 17);
        this.radioButtonOther.TabIndex = 12;
        this.radioButtonOther.Text = "Other";
        this.radioButtonOther.UseVisualStyleBackColor = true;
        this.radioButtonOther.CheckedChanged += new System.EventHandler(this.radioButtonOther_CheckedChanged);
        //
        // groupBoxDPC
        //
        this.groupBoxDPC.Controls.Add(this.radioButtonOther);
        this.groupBoxDPC.Controls.Add(this.radioButtonError);
        this.groupBoxDPC.Controls.Add(this.radioButtonNewProv);
        this.groupBoxDPC.Controls.Add(this.radioButtonReAssign);
        this.groupBoxDPC.Enabled = false;
        this.groupBoxDPC.Location = new System.Drawing.Point(15, 484);
        this.groupBoxDPC.Name = "groupBoxDPC";
        this.groupBoxDPC.Size = new System.Drawing.Size(355, 66);
        this.groupBoxDPC.TabIndex = 13;
        this.groupBoxDPC.TabStop = false;
        this.groupBoxDPC.Text = "Reason for DPC change:";
        //
        // FormProcEditExplain
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(725, 577);
        this.Controls.Add(this.groupBoxDPC);
        this.Controls.Add(this.textExplanation);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textSummary);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormProcEditExplain";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Procedure Edit Explanation";
        this.Load += new System.EventHandler(this.FormProcEditExplain_Load);
        this.groupBoxDPC.ResumeLayout(false);
        this.groupBoxDPC.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSummary = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textExplanation = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.RadioButton radioButtonNewProv = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioButtonReAssign = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioButtonError = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioButtonOther = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.GroupBox groupBoxDPC = new System.Windows.Forms.GroupBox();
}


