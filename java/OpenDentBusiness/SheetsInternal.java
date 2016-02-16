//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.GrowthBehaviorEnum;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetInternalType;
import OpenDentBusiness.SheetTypeEnum;

public class SheetsInternal   
{
    public static SheetDef getSheetDef(SheetInternalType internalType) throws Exception {
        switch(internalType)
        {
            case LabelPatientMail: 
                return labelPatientMail();
            case LabelPatientLFAddress: 
                return labelPatientLFAddress();
            case LabelPatientLFChartNumber: 
                return labelPatientLFChartNumber();
            case LabelPatientLFPatNum: 
                return labelPatientLFPatNum();
            case LabelPatientRadiograph: 
                return labelPatientRadiograph();
            case LabelText: 
                return labelText();
            case LabelCarrier: 
                return labelCarrier();
            case LabelReferral: 
                return labelReferral();
            case ReferralSlip: 
                return referralSlip();
            case LabelAppointment: 
                return labelAppointment();
            case Rx: 
                return rx();
            case Consent: 
                return consent();
            case PatientLetter: 
                return patientLetter();
            case PatientLetterTxFinder: 
                return patientLetterTxFinder();
            case ReferralLetter: 
                return referralLetter();
            case PatientRegistration: 
                return patientRegistration();
            case RoutingSlip: 
                return routingSlip();
            case FinancialAgreement: 
                return financialAgreement();
            case HIPAA: 
                return hIPAA();
            case MedicalHistSimple: 
                return medicalHistSimple();
            case MedicalHistNewPat: 
                return medicalHistNewPat();
            case MedicalHistUpdate: 
                return medicalHistUpdate();
            case LabSlip: 
                return labSlip();
            case ExamSheet: 
                return examSheet();
            case DepositSlip: 
                return depositSlip();
            default: 
                throw new ApplicationException("Invalid SheetInternalType.");
        
        }
    }

    public static List<SheetDef> getAllInternal() throws Exception {
        List<SheetDef> list = new List<SheetDef>();
        for (int i = 0;i < Enum.GetValues(SheetInternalType.class).Length;i++)
        {
            list.Add(getSheetDef(SheetInternalType.values()[i]));
        }
        return list;
    }

    private static SheetDef labelPatientMail() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.LabelPatient);
        sheet.Description = "Label Patient Mail";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 12f;
        sheet.Width = 108;
        sheet.Height = 346;
        sheet.IsLandscape = true;
        int rowH = 19;
        int yPos = 10;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("nameFL",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("address",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH,GrowthBehaviorEnum.DownLocal));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("cityStateZip",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        return sheet;
    }

    private static SheetDef labelPatientLFAddress() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.LabelPatient);
        sheet.Description = "Label PatientLFAddress";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 12f;
        sheet.Width = 108;
        sheet.Height = 346;
        sheet.IsLandscape = true;
        int rowH = 19;
        int yPos = 10;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("nameLF",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("address",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH,GrowthBehaviorEnum.DownLocal));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("cityStateZip",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        return sheet;
    }

    private static SheetDef labelPatientLFChartNumber() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.LabelPatient);
        sheet.Description = "Label PatientLFChartNumber";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 12f;
        sheet.Width = 108;
        sheet.Height = 346;
        sheet.IsLandscape = true;
        int rowH = 19;
        int yPos = 30;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("nameLF",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("ChartNumber",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        return sheet;
    }

    private static SheetDef labelPatientLFPatNum() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.LabelPatient);
        sheet.Description = "Label PatientLFPatNum";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 12f;
        sheet.Width = 108;
        sheet.Height = 346;
        sheet.IsLandscape = true;
        int rowH = 19;
        int yPos = 30;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("nameLF",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("PatNum",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        return sheet;
    }

    private static SheetDef labelPatientRadiograph() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.LabelPatient);
        sheet.Description = "Label Patient Radiograph";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 12f;
        sheet.Width = 108;
        sheet.Height = 346;
        sheet.IsLandscape = true;
        int rowH = 19;
        int yPos = 30;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("nameLF",sheet.FontSize,sheet.FontName,false,25,yPos,150,rowH,GrowthBehaviorEnum.None));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("dateTime.Today",sheet.FontSize,sheet.FontName,false,180,yPos,100,rowH,GrowthBehaviorEnum.None));
        yPos += rowH;
        //smallfont:
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("birthdate", 9, sheet.FontName, false, 25, yPos, 105, rowH, GrowthBehaviorEnum.None));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("priProvName", 9, sheet.FontName, false, 130, yPos, 150, rowH, GrowthBehaviorEnum.None));
        return sheet;
    }

    private static SheetDef labelText() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.LabelPatient);
        sheet.Description = "Label Text";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 12f;
        sheet.Width = 108;
        sheet.Height = 346;
        sheet.IsLandscape = true;
        //int rowH=19;
        int yPos = 30;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("text",sheet.FontSize,sheet.FontName,false,25,yPos,300,315,GrowthBehaviorEnum.None));
        return sheet;
    }

    private static SheetDef labelCarrier() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.LabelCarrier);
        sheet.Description = "Label Carrier";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 12f;
        sheet.Width = 108;
        sheet.Height = 346;
        sheet.IsLandscape = true;
        int rowH = 19;
        int yPos = 10;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("CarrierName",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("address",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH,GrowthBehaviorEnum.DownLocal));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("cityStateZip",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        return sheet;
    }

    private static SheetDef labelReferral() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.LabelReferral);
        sheet.Description = "Label Referral";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 12f;
        sheet.Width = 108;
        sheet.Height = 346;
        sheet.IsLandscape = true;
        int rowH = 19;
        int yPos = 10;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("nameFL",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("address",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH,GrowthBehaviorEnum.DownLocal));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("cityStateZip",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        return sheet;
    }

    private static SheetDef referralSlip() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.ReferralSlip);
        sheet.Description = "Referral Slip";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 9f;
        sheet.Width = 450;
        sheet.Height = 650;
        int rowH = 17;
        int yPos = 50;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Referral to", 10, sheet.FontName, true, 170, yPos, 200, 19));
        yPos += rowH + 5;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("referral.nameFL",sheet.FontSize,sheet.FontName,false,150,yPos,200,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("referral.address",sheet.FontSize,sheet.FontName,false,150,yPos,200,rowH,GrowthBehaviorEnum.DownLocal));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("referral.cityStateZip",sheet.FontSize,sheet.FontName,false,150,yPos,200,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("referral.phone",sheet.FontSize,sheet.FontName,false,150,yPos,200,rowH));
        yPos += rowH + 30;
        //Patient--------------------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Patient", 9, sheet.FontName, true, 25, yPos, 100, rowH));
        yPos += rowH + 5;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.nameFL",sheet.FontSize,sheet.FontName,false,25,yPos,200,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("dateTime.Today",sheet.FontSize,sheet.FontName,false,300,yPos,100,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Work:",sheet.FontSize,sheet.FontName,false,25,yPos,38,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.WkPhone",sheet.FontSize,sheet.FontName,false,63,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Home:",sheet.FontSize,sheet.FontName,false,25,yPos,42,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.HmPhone",sheet.FontSize,sheet.FontName,false,67,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Wireless:",sheet.FontSize,sheet.FontName,false,25,yPos,58,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.WirelessPhone",sheet.FontSize,sheet.FontName,false,83,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.address",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH,GrowthBehaviorEnum.DownLocal));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.cityStateZip",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH + 30;
        //Provider--------------------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Referred by", 9, sheet.FontName, true, 25, yPos, 300, rowH));
        yPos += rowH + 5;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.provider",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH + 20;
        //Notes--------------------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Notes", 9, sheet.FontName, true, 25, yPos, 300, rowH));
        yPos += rowH + 5;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("notes",sheet.FontSize,sheet.FontName,false,25,yPos,400,240));
        return sheet;
    }

    private static SheetDef labelAppointment() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.LabelAppointment);
        sheet.Description = "Label Appointment";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 10f;
        sheet.Width = 108;
        sheet.Height = 346;
        sheet.IsLandscape = true;
        int rowH = 19;
        int yPos = 15;
        //if(PrefC.GetBool(PrefName.FuchsOptionsOn")) yPos = 50;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("nameFL",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Your appointment is scheduled for:",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("weekdayDateTime",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Appointment length:",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        yPos += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("length",sheet.FontSize,sheet.FontName,false,25,yPos,300,rowH));
        return sheet;
    }

    private static SheetDef rx() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.Rx);
        sheet.Description = "Rx";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 8f;
        sheet.Width = 425;
        sheet.Height = 550;
        sheet.IsLandscape = true;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(0,0,550,0));
        //top
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(0,0,0,425));
        //left
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(549,0,0,425));
        //right
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(0,424,550,0));
        //bottom
        int x = new int();
        int y = new int();
        int rowH = 14;
        //for font of 8.
        //Dr--------------------------------------------------------------------------------------------------
        //Left Side
        x = 50;
        y = 37;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("prov.nameFL",sheet.FontSize,sheet.FontName,true,x,y,170,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("prov.address",sheet.FontSize,sheet.FontName,false,x,y,170,rowH,GrowthBehaviorEnum.DownLocal));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("prov.cityStateZip",sheet.FontSize,sheet.FontName,false,x,y,170,rowH));
        y = 100;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(25,y,500,0));
        //Right Side
        x = 280;
        y = 38;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("prov.phone",sheet.FontSize,sheet.FontName,false,x,y,170,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("RxDate",sheet.FontSize,sheet.FontName,false,x,y,170,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("DEA#:",sheet.FontSize,sheet.FontName,true,x,y,40,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("prov.dEANum",sheet.FontSize,sheet.FontName,false,x + 40,y,130,rowH));
        //Patient---------------------------------------------------------------------------------------------------
        //Upper Left
        x = 90;
        y = 105;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("pat.nameFL",sheet.FontSize,sheet.FontName,true,x,y,150,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("DOB:",sheet.FontSize,sheet.FontName,true,x,y,40,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("pat.Birthdate",sheet.FontSize,sheet.FontName,true,x + 40,y,110,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("pat.HmPhone",sheet.FontSize,sheet.FontName,false,x,y,150,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("pat.address",sheet.FontSize,sheet.FontName,false,x,y,170,rowH,GrowthBehaviorEnum.DownLocal));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("pat.cityStateZip",sheet.FontSize,sheet.FontName,false,x,y,170,rowH));
        //RX-----------------------------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Rx", 24, "Times New Roman", true, 35, 190, 55, 33));
        y = 205;
        x = 90;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("Drug",sheet.FontSize,sheet.FontName,true,x,y,300,rowH));
        y += (int)(rowH * 1.5);
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Disp:",sheet.FontSize,sheet.FontName,false,x,y,35,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("Disp",sheet.FontSize,sheet.FontName,false,x + 35,y,300,rowH));
        y += (int)(rowH * 1.5);
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Sig:",sheet.FontSize,sheet.FontName,false,x,y,30,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("Sig",sheet.FontSize,sheet.FontName,false,x + 30,y,325,rowH * 2));
        y += (int)(rowH * 2.5);
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Refills:",sheet.FontSize,sheet.FontName,false,x,y,45,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("Refills",sheet.FontSize,sheet.FontName,false,x + 45,y,110,rowH));
        //Generic Subst----------------------------------------------------------------------------------------------
        x = 50;
        y = 343;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y,12,12));
        x += 17;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Dispense as Written",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        x -= 17;
        y += 25;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y,12,12));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y,12,12));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x + 12,y,-12,12));
        x += 17;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Generic Substitution Permitted",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        //Signature Line--------------------------------------------------------------------------------------------
        y = 360;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(295,y,230,0));
        y += 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Signature of Prescriber",sheet.FontSize,sheet.FontName,false,340,y,150,rowH));
        return sheet;
    }

    private static SheetDef consent() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.Consent);
        sheet.Description = "Extraction Consent";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 11f;
        sheet.Width = 850;
        sheet.Height = 1100;
        int rowH = 19;
        int x = 200;
        int y = 40;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Extraction Consent", 12, sheet.FontName, true, x, y, 170, 20));
        y += 35;
        x = 50;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("dateTime.Today",sheet.FontSize,sheet.FontName,false,x,y,120,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.nameFL",sheet.FontSize,sheet.FontName,false,x,y,220,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Tooth number(s): ",sheet.FontSize,sheet.FontName,false,x,y,120,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("toothNum",sheet.FontSize,sheet.FontName,false,x + 120,y,100,rowH));
        y += rowH;
        y += 20;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Extraction(s) are to be performed on the tooth/teeth listed above.  While we expect no complications, there are some risks involved with this procedure.  The more common complications are:\r\n" + 
        "\r\n" + 
        "Pain, infection, swelling, bruising, and discoloration.  Adjacent teeth may be chipped or damaged during the extraction.\r\n" + 
        "\r\n" + 
        "Nerves that run near the area of extraction may be bruised or damaged.  You may experience some temporary numbness and tingling of the lip and chin, or in rare cases, the tongue.  In some extremely rare instances, the lack of sensation could be permanent.\r\n" + 
        "\r\n" + 
        "In the upper arch, sinus complications can occur because the roots of some upper teeth extend near or into the sinuses.  After extraction, a hole may be present between the sinus and the mouth.  If this happens, you will be informed and the area repaired.\r\n" + 
        "\r\n" + 
        "By signing below you acknowledge that you understand the information presented, have had all your questions answered satisfactorily, and give consent to perform this procedure.",sheet.FontSize,sheet.FontName,false,x,y,500,360));
        y += 360;
        y += 20;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newSigBox(x,y,364,81));
        y += 82;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Signature",sheet.FontSize,sheet.FontName,false,x,y,90,rowH));
        return sheet;
    }

    private static SheetDef patientLetter() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.PatientLetter);
        sheet.Description = "Patient Letter";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 11f;
        sheet.Width = 850;
        sheet.Height = 1100;
        int rowH = 19;
        int x = 100;
        int y = 100;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("PracticeTitle",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("PracticeAddress",sheet.FontSize,sheet.FontName,false,x,y,200,rowH,GrowthBehaviorEnum.DownLocal));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("practiceCityStateZip",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.nameFL",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.address",sheet.FontSize,sheet.FontName,false,x,y,200,rowH,GrowthBehaviorEnum.DownLocal));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.cityStateZip",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        y += rowH;
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("today.DayDate",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.salutation",sheet.FontSize,sheet.FontName,false,x,y,280,rowH));
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("letter text",sheet.FontSize,sheet.FontName,false,x,y,650,200,GrowthBehaviorEnum.DownGlobal));
        y += 200;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Sincerely,",sheet.FontSize,sheet.FontName,false,x,y,100,rowH));
        y += rowH;
        y += rowH;
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.priProvNameFL",sheet.FontSize,sheet.FontName,false,x,y,240,rowH));
        return sheet;
    }

    private static SheetDef patientLetterTxFinder() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.PatientLetter);
        sheet.Description = "Patient Letter Tx Finder";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 11f;
        sheet.Width = 850;
        sheet.Height = 1100;
        int rowH = 19;
        int x = 100;
        int y = 100;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("PracticeTitle",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("PracticeAddress",sheet.FontSize,sheet.FontName,false,x,y,200,rowH,GrowthBehaviorEnum.DownLocal));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("practiceCityStateZip",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.nameFL",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.address",sheet.FontSize,sheet.FontName,false,x,y,200,rowH,GrowthBehaviorEnum.DownLocal));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.cityStateZip",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        y += rowH;
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("today.DayDate",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        y += rowH;
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Your insurance benefits will renew soon.  You have [insRemaining] remaining that can be applied towards important dental treatment." + "  Our records show that the following treatment has not yet been completed." + "\r\n\r\n[treatmentPlanProcs]\r\n\r\n" + "Please call our office at your earliest convenience to schedule an appointment.",sheet.FontSize,sheet.FontName,false,x,y,650,200,GrowthBehaviorEnum.DownGlobal));
        y += 200;
        y += rowH;
        y += rowH;
        y += rowH;
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.priProvNameFL",sheet.FontSize,sheet.FontName,false,x,y,240,rowH));
        return sheet;
    }

    private static SheetDef referralLetter() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.ReferralLetter);
        sheet.Description = "Referral Letter";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 11f;
        sheet.Width = 850;
        sheet.Height = 1100;
        int rowH = 19;
        int x = 100;
        int y = 100;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("PracticeTitle",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("PracticeAddress",sheet.FontSize,sheet.FontName,false,x,y,200,rowH,GrowthBehaviorEnum.DownLocal));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("practiceCityStateZip",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("referral.nameFL",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("referral.address",sheet.FontSize,sheet.FontName,false,x,y,200,rowH,GrowthBehaviorEnum.DownLocal));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("referral.cityStateZip",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        y += rowH;
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("today.DayDate",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("RE patient:",sheet.FontSize,sheet.FontName,false,x,y,90,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.nameFL",sheet.FontSize,sheet.FontName,false,x + 90,y,200,rowH));
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("referral.salutation",sheet.FontSize,sheet.FontName,false,x,y,280,rowH));
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("letter text",sheet.FontSize,sheet.FontName,false,x,y,650,rowH,GrowthBehaviorEnum.DownGlobal));
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Sincerely,",sheet.FontSize,sheet.FontName,false,x,y,100,rowH));
        y += rowH;
        y += rowH;
        y += rowH;
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("patient.priProvNameFL",sheet.FontSize,sheet.FontName,false,x,y,250,rowH));
        return sheet;
    }

    private static SheetDef patientRegistration() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.PatientForm);
        sheet.Description = "Registration Form";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 10f;
        sheet.Width = 850;
        sheet.Height = 1100;
        int rowH = 16;
        int y = 31;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newImage("Patient Info.gif",39,y,761,988));
        y = 204;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("LName",sheet.FontSize,sheet.FontName,false,126,y,150,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("FName",sheet.FontSize,sheet.FontName,false,293,y,145,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("MiddleI",sheet.FontSize,sheet.FontName,false,447,y,50,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("Preferred",sheet.FontSize,sheet.FontName,false,507,y,150,rowH));
        y = 236;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("Birthdate",sheet.FontSize,sheet.FontName,false,133,y,105,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("SSN",sheet.FontSize,sheet.FontName,false,292,y,140,rowH));
        y = 241;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("Gender","Male",499,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("Gender","Female",536,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("Position","Married",649,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("Position","Single",683,y,10,10));
        y = 255;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("WkPhone",sheet.FontSize,sheet.FontName,false,152,y,120,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("WirelessPhone",sheet.FontSize,sheet.FontName,false,381,y,120,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("wirelessCarrier",sheet.FontSize,sheet.FontName,false,631,y,130,rowH));
        y = 274;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("Email",sheet.FontSize,sheet.FontName,false,114,y,575,rowH));
        y = 299;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferContactMethod","HmPhone",345,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferContactMethod","WkPhone",429,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferContactMethod","WirelessPh",513,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferContactMethod","Email",607,y,10,10));
        //sheet.SheetFieldDefs.Add(SheetFieldDef.NewCheckBox("PreferContactMethodIsTextMessage",666,y,10,10));
        y = 318;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferConfirmMethod","HmPhone",343,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferConfirmMethod","WkPhone",428,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferConfirmMethod","WirelessPh",511,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferConfirmMethod","Email",605,y,10,10));
        //sheet.SheetFieldDefs.Add(SheetFieldDef.NewCheckBox("PreferConfirmMethodIsTextMessage",664,y,10,10));
        y = 337;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferRecallMethod","HmPhone",343,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferRecallMethod","WkPhone",428,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferRecallMethod","WirelessPh",512,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("PreferRecallMethod","Email",605,y,10,10));
        //sheet.SheetFieldDefs.Add(SheetFieldDef.NewCheckBox("PreferRecallMethodIsTextMessage",665,y,10,10));
        //cover up the options for text messages since we don't support that yet
        //sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText(".",sheet.FontSize,sheet.FontName,false,660,293,100,70));
        y = 356;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("StudentStatus","Nonstudent",346,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("StudentStatus","Fulltime",443,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("StudentStatus","Parttime",520,y,10,10));
        y += 33;
        //375;
        //sheet.SheetFieldDefs.Add(SheetFieldDef.NewCheckBox("guarantorIsSelf",270,y,10,10));
        //sheet.SheetFieldDefs.Add(SheetFieldDef.NewCheckBox("guarantorIsOther",320,y,10,10));
        //sheet.SheetFieldDefs.Add(SheetFieldDef.NewInput("guarantorNameF",sheet.FontSize,sheet.FontName,false,378,370,150,rowH));
        //y=409;-34
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("referredFrom",sheet.FontSize,sheet.FontName,false,76,y,600,rowH));
        y += 64;
        //439;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newCheckBox("addressAndHmPhoneIsSameEntireFamily",283,y,10,10));
        y += 15;
        //453;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("Address",sheet.FontSize,sheet.FontName,false,128,y,425,rowH));
        y += 19;
        //472;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("Address2",sheet.FontSize,sheet.FontName,false,141,y,425,rowH));
        y += 19;
        //491;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("City",sheet.FontSize,sheet.FontName,false,103,y,200,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("State",sheet.FontSize,sheet.FontName,false,359,y,45,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("Zip",sheet.FontSize,sheet.FontName,false,439,y,100,rowH));
        y += 19;
        //510;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("HmPhone",sheet.FontSize,sheet.FontName,false,156,y,120,rowH));
        //Ins 1--------------------------------------------------------------------------------------------------------------
        y += 58;
        //569;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("ins1Relat","Self",267,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("ins1Relat","Spouse",320,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("ins1Relat","Child",394,y,10,10));
        //sheet.SheetFieldDefs.Add(SheetFieldDef.NewRadioButton("ins1RelatIsNotSelfSpouseChild",457,y,10,10));
        //sheet.SheetFieldDefs.Add(SheetFieldDef.NewInput("ins1RelatDescript",sheet.FontSize,sheet.FontName,false,515,598,200,rowH));
        y += 16;
        //585;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins1SubscriberNameF",sheet.FontSize,sheet.FontName,false,184,y,250,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins1SubscriberID",sheet.FontSize,sheet.FontName,false,565,y,140,rowH));
        y += 20;
        //604;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins1CarrierName",sheet.FontSize,sheet.FontName,false,201,y,290,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins1CarrierPhone",sheet.FontSize,sheet.FontName,false,552,y,170,rowH));
        y += 19;
        //623;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins1EmployerName",sheet.FontSize,sheet.FontName,false,136,y,190,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins1GroupName",sheet.FontSize,sheet.FontName,false,419,y,160,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins1GroupNum",sheet.FontSize,sheet.FontName,false,638,y,120,rowH));
        //Ins 2-------------------------------------------------------------------------------------------------------------
        y += 72;
        //695;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("ins2Relat","Self",267,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("ins2Relat","Spouse",320,y,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("ins2Relat","Child",394,y,10,10));
        //sheet.SheetFieldDefs.Add(SheetFieldDef.NewRadioButton("ins2RelatIsNotSelfSpouseChild",457,y,10,10));
        //sheet.SheetFieldDefs.Add(SheetFieldDef.NewInput("ins2RelatDescript",sheet.FontSize,sheet.FontName,false,515,598+126,200,rowH));
        y += 16;
        //585+126;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins2SubscriberNameF",sheet.FontSize,sheet.FontName,false,184,y,250,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins2SubscriberID",sheet.FontSize,sheet.FontName,false,565,y,140,rowH));
        y += 19;
        //604+126;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins2CarrierName",sheet.FontSize,sheet.FontName,false,201,y,290,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins2CarrierPhone",sheet.FontSize,sheet.FontName,false,552,y,170,rowH));
        y += 19;
        //623+126;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins2EmployerName",sheet.FontSize,sheet.FontName,false,136,y,190,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins2GroupName",sheet.FontSize,sheet.FontName,false,419,y,160,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("ins2GroupNum",sheet.FontSize,sheet.FontName,false,638,y,120,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,136,821,600,200));
        return sheet;
    }

    private static SheetDef routingSlip() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.RoutingSlip);
        sheet.Description = "Routing Slip";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 10f;
        sheet.Width = 850;
        sheet.Height = 1100;
        int rowH = 18;
        int x = 75;
        int y = 50;
        //Title----------------------------------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Routing Slip", 12f, sheet.FontName, true, 373, y, 200, 22));
        y += 35;
        //Today's appointment, including procedures-----------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("[nameFL]",sheet.FontSize,sheet.FontName,true,x,y,500,19));
        y += 19;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("appt.timeDate",sheet.FontSize,sheet.FontName,false,x,y,500,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("appt.length",sheet.FontSize,sheet.FontName,false,x,y,500,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("appt.providers",sheet.FontSize,sheet.FontName,false,x,y,500,rowH,GrowthBehaviorEnum.DownGlobal));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Procedures:",sheet.FontSize,sheet.FontName,false,x,y,500,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("appt.procedures",sheet.FontSize,sheet.FontName,false,x + 10,y,490,rowH,GrowthBehaviorEnum.DownGlobal));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Note:",sheet.FontSize,sheet.FontName,false,x,y,40,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("appt.Note",sheet.FontSize,sheet.FontName,false,x + 40,y,460,rowH,GrowthBehaviorEnum.DownGlobal));
        y += rowH;
        y += 3;
        //Patient/Family Info---------------------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y,725,0));
        y += 3;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Patient Info",sheet.FontSize,sheet.FontName,true,x,y,500,19));
        y += 19;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("PatNum: [PatNum]",sheet.FontSize,sheet.FontName,false,x,y,500,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Age: [age]",sheet.FontSize,sheet.FontName,false,x,y,500,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Date of First Visit: [DateFirstVisit]",sheet.FontSize,sheet.FontName,false,x,y,500,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Billing Type: [BillingType]",sheet.FontSize,sheet.FontName,false,x,y,500,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Recall Due Date: [dateRecallDue]",sheet.FontSize,sheet.FontName,false,x,y,500,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Medical notes: [MedUrgNote]",sheet.FontSize,sheet.FontName,false,x,y,725,rowH,GrowthBehaviorEnum.DownGlobal));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Other Family Members",sheet.FontSize,sheet.FontName,true,x,y,500,19));
        y += 19;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("otherFamilyMembers",sheet.FontSize,sheet.FontName,false,x,y,500,rowH,GrowthBehaviorEnum.DownGlobal));
        y += rowH;
        y += 3;
        //Insurance Info---------------------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y,725,0));
        y += 3;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Primary Insurance",sheet.FontSize,sheet.FontName,true,x,y,360,19));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Secondary Insurance",sheet.FontSize,sheet.FontName,true,x + 365,y,360,19));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x + 362,y,0,133));
        y += 19;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("[carrierName]\r\n" + 
        "Subscriber: [subscriberNameFL]\r\n" + 
        "Annual Max: [insAnnualMax], Pending: [insPending], Used: [insUsed]\r\n" + 
        "Deductible: [insDeductible], Ded Used: [insDeductibleUsed]\r\n" + 
        "[insPercentages]",sheet.FontSize,sheet.FontName,false,x,y,360,114,GrowthBehaviorEnum.DownGlobal));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("[carrier2Name]\r\n" + 
        "Subscriber: [subscriber2NameFL]\r\n" + 
        "Annual Max: [ins2AnnualMax], Pending: [ins2Pending], Used: [ins2Used]\r\n" + 
        "Deductible: [ins2Deductible], Ded Used: [ins2DeductibleUsed]\r\n" + 
        "[ins2Percentages]",sheet.FontSize,sheet.FontName,false,x + 365,y,360,114,GrowthBehaviorEnum.DownGlobal));
        y += 114;
        y += 3;
        //Account Info---------------------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y,725,0));
        y += 3;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Account Info",sheet.FontSize,sheet.FontName,true,x,y,500,19));
        y += 19;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Guarantor: [guarantorNameFL]\r\n" + 
        "Balance: [balTotal]\r\n" + 
        "-Ins Est: [balInsEst]\r\n" + 
        "=Total: [balTotalMinusInsEst]\r\n" + 
        "Aging: 0-30:[bal_0_30]  31-60:[bal_31_60]  61-90:[bal_61_90]  90+:[balOver90]\r\n" + 
        "Fam Urgent Fin Note: [famFinUrgNote]",sheet.FontSize,sheet.FontName,false,x,y,725,98,GrowthBehaviorEnum.DownGlobal));
        y += 98;
        y += 3;
        //Insurance Info---------------------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y,725,0));
        y += 3;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Treatment Plan",sheet.FontSize,sheet.FontName,true,x,y,500,19,GrowthBehaviorEnum.DownGlobal));
        y += 19;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("[treatmentPlanProcs]",sheet.FontSize,sheet.FontName,false,x,y,500,19,GrowthBehaviorEnum.DownGlobal));
        y += rowH;
        return sheet;
    }

    private static SheetDef financialAgreement() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.PatientForm);
        sheet.Description = "Financial Agreement";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 10f;
        sheet.Width = 850;
        sheet.Height = 575;
        int rowH = 18;
        int yOffset = 25;
        int y = 135;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Financial Agreement", 12f, sheet.FontName, true, 332, 65, 200, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Last Name:",sheet.FontSize,sheet.FontName,false,91,y,75,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("LName",sheet.FontSize,sheet.FontName,false,166,y,150,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("First Name:",sheet.FontSize,sheet.FontName,false,321,y,75,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("FName",sheet.FontSize,sheet.FontName,false,396,y,150,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Birthdate:",sheet.FontSize,sheet.FontName,false,551,y,65,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("Birthdate",sheet.FontSize,sheet.FontName,false,616,y,145,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Date: [dateToday]",sheet.FontSize,sheet.FontName,false,92,135 + yOffset,120,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("* For my convenience, this office may release my information to my insurance company, and receive payment directly from them.\r\n" + 
        "* I understand that if I begin major treatment that involves lab work, I will be responsible for the fee at that time.\r\n" + 
        "* If sent to collections, I agree to pay all related fees and court costs.\r\n" + 
        "* Every effort will be made to help me with my insurance, but if they do not pay as expected, I will still be responsible.\r\n" + 
        "* I agree to pay finance charges of 1.5% per month (18% APR) on any balance 90 days past due.\r\n" + 
        "* I will pay a fee for appointments broken without 24 hours notice. \r\n" + 
        "* Treatment plans may change, and I will be responsible for the work actually done.",sheet.FontSize,sheet.FontName,false,92,167 + yOffset,670,155));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("I agree to let this office run a credit report.  If no, then all fees are due at time of service.",sheet.FontSize,sheet.FontName,false,92,337 + yOffset,550,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(93,360 + yOffset,11,11));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(93,378 + yOffset,11,11));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newCheckBox("misc",94,361 + yOffset,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newCheckBox("misc",94,379 + yOffset,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Yes",sheet.FontSize,sheet.FontName,false,108,358 + yOffset,40,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("No",sheet.FontSize,sheet.FontName,false,108,376 + yOffset,40,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newSigBox(258,416 + yOffset,364,81));
        return sheet;
    }

    private static SheetDef hIPAA() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.PatientForm);
        sheet.Description = "HIPAA";
        sheet.FontName = "Microsoft Sans Serif";
        int rowH = 18;
        int yOffset = 25;
        int y = 127;
        sheet.FontSize = 10f;
        sheet.Width = 850;
        sheet.Height = 575;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Notice of Privacy Policies", 12f, sheet.FontName, true, 332, 65, 220, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Last Name:",sheet.FontSize,sheet.FontName,false,91,y,75,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("LName",sheet.FontSize,sheet.FontName,false,166,y,150,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("First Name:",sheet.FontSize,sheet.FontName,false,321,y,75,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("FName",sheet.FontSize,sheet.FontName,false,396,y,150,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Birthdate:",sheet.FontSize,sheet.FontName,false,551,y,65,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("Birthdate",sheet.FontSize,sheet.FontName,false,616,y,145,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Date: [dateToday]",sheet.FontSize,sheet.FontName,false,92,135 + yOffset,120,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("I have had full opportunity to read and consider the contents of  the Notice of Privacy Practices.  I understand that I am giving my permission to your use and disclosure of my protected health information in order to carry out treatment, payment activities, and healthcare operations.  I also understand that I have the right to revoke permission.",sheet.FontSize,sheet.FontName,false,92,167 + yOffset,670,80));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newSigBox(261,295 + yOffset,364,81));
        return sheet;
    }

    private static SheetDef medicalHistSimple() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.MedicalHistory);
        sheet.Description = "Medical History Simple";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 10f;
        sheet.Width = 850;
        sheet.Height = 1100;
        int rowH = 18;
        int y = 60;
        int x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Medical History", 12f, sheet.FontName, true, 345, y, 180, 20));
        y = 105;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Last Name:",sheet.FontSize,sheet.FontName,false,76,y,75,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("LName",sheet.FontSize,sheet.FontName,false,151,y,155,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("First Name:",sheet.FontSize,sheet.FontName,false,311,y,76,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("FName",sheet.FontSize,sheet.FontName,false,387,y,155,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Birthdate:",sheet.FontSize,sheet.FontName,false,547,y,65,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("Birthdate",sheet.FontSize,sheet.FontName,false,612,y,145,rowH));
        y += rowH + 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Name of Medical Doctor:",sheet.FontSize,sheet.FontName,false,x,y,155,rowH));
        x = 230;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,265,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,265,rowH));
        x = 500;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("City/State:",sheet.FontSize,sheet.FontName,false,x,y,67,rowH));
        x = 567;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,190,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,190,rowH));
        x = 75;
        y += rowH + 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Emergency Contact",sheet.FontSize,sheet.FontName,false,x,y,124,rowH));
        x = 199;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,138,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,138,rowH));
        x = 342;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Phone",sheet.FontSize,sheet.FontName,false,x,y,44,rowH));
        x = 386;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,99,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,99,rowH));
        x = 490;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Relationship",sheet.FontSize,sheet.FontName,false,x,y,80,rowH));
        x = 570;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,187,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,187,rowH));
        x = 75;
        y += rowH + 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("List all medications or drugs you are now taking:",sheet.FontSize,sheet.FontName,false,x,y,292,rowH));
        y += rowH + 1;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 2,11,11));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newCheckBox("misc",x + 1,y + 3,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("None",sheet.FontSize,sheet.FontName,false,x + 13,y,37,rowH));
        y += rowH + 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,682,140));
        y += 142;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("List all medications or drugs you are allergic to:",sheet.FontSize,sheet.FontName,false,x,y,286,rowH));
        y += rowH + 1;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 2,11,11));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newCheckBox("misc",x + 1,y + 3,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("None",sheet.FontSize,sheet.FontName,false,x + 13,y,37,rowH));
        y += rowH + 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,682,140));
        y += 142;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("List any medical conditions you may have including: asthma, bleeding problems, cancer, diabetes, heart murmur, heart trouble, high blood pressure, joint replacement, kidney disease, liver disease, pregnancy, psychiatric treatment, sinus trouble, stroke, ulcers, or history of  rheumatic fever or of taking fen-phen:",sheet.FontSize,sheet.FontName,false,x,y,682,55));
        y += 56;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 2,11,11));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newCheckBox("misc",x + 1,y + 3,10,10));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("None",sheet.FontSize,sheet.FontName,false,x + 13,y,37,rowH));
        y += rowH + 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,682,140));
        y += 142;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Tobacco use?  If so, what kind and how much?",sheet.FontSize,sheet.FontName,false,x,y,289,rowH));
        x = 364;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,393,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,393,rowH));
        y += rowH + 2;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Unusual reaction to dental injections?",sheet.FontSize,sheet.FontName,false,x,y,232,rowH));
        x = 307;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,450,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,450,rowH));
        y += rowH + 2;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Reason for today's visit",sheet.FontSize,sheet.FontName,false,x,y,145,rowH));
        x = 220;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,275,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,275,rowH));
        x = 500;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Are you in pain?",sheet.FontSize,sheet.FontName,false,x,y,103,rowH));
        x = 603;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,154,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,154,rowH));
        y += rowH + 2;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("New patients:",sheet.FontSize,sheet.FontName,false,x,y,87,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Do you have a Panoramic x-ray or Full Mouth x-rays that are less than 5 years old?",sheet.FontSize,sheet.FontName,false,x,y,507,rowH));
        x = 602;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,155,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,155,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Do you have BiteWing x-rays that are less than 1 year old?",sheet.FontSize,sheet.FontName,false,x,y,360,rowH));
        x = 455;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,302,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,302,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Name of former dentist",sheet.FontSize,sheet.FontName,false,x,y,143,rowH));
        x = 238;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,275,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,275,rowH));
        x = 518;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("City/State",sheet.FontSize,sheet.FontName,false,x,y,64,rowH));
        x = 582;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,175,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,175,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Date of last cleaning and exam",sheet.FontSize,sheet.FontName,false,x,y,192,rowH));
        x = 287;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,470,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,470,rowH));
        y += 40;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Date: [dateToday]",sheet.FontSize,sheet.FontName,false,x,y,120,rowH));
        y += 40;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newSigBox(261,y,364,81));
        return sheet;
    }

    private static SheetDef medicalHistNewPat() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.MedicalHistory);
        sheet.Description = "Medical History New Patient";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 10f;
        sheet.Width = 850;
        sheet.Height = 1100;
        int rowH = 18;
        int y = 60;
        int x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Medical History for New Patient", 12f, sheet.FontName, true, 312, y, 275, 20));
        y = 105;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Last Name:",sheet.FontSize,sheet.FontName,false,76,y,75,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("LName",sheet.FontSize,sheet.FontName,false,151,y,155,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("First Name:",sheet.FontSize,sheet.FontName,false,311,y,76,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("FName",sheet.FontSize,sheet.FontName,false,387,y,155,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Birthdate:",sheet.FontSize,sheet.FontName,false,547,y,65,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("Birthdate",sheet.FontSize,sheet.FontName,false,612,y,145,rowH));
        y += rowH + 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Name of Medical Doctor:",sheet.FontSize,sheet.FontName,false,x,y,155,rowH));
        x = 230;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,265,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,265,rowH));
        x = 500;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("City/State:",sheet.FontSize,sheet.FontName,false,x,y,67,rowH));
        x = 567;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,190,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,190,rowH));
        x = 75;
        y += rowH + 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Emergency Contact",sheet.FontSize,sheet.FontName,false,x,y,124,rowH));
        x = 199;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,138,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,138,rowH));
        x = 342;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Phone",sheet.FontSize,sheet.FontName,false,x,y,44,rowH));
        x = 386;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,99,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,99,rowH));
        x = 490;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Relationship",sheet.FontSize,sheet.FontName,false,x,y,80,rowH));
        x = 570;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,187,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,187,rowH));
        x = 75;
        y += rowH + 15;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("List all medications that you are now taking:",sheet.FontSize,sheet.FontName,false,x,y,400,rowH));
        y += rowH + 8;
        int inputW = 327;
        int inputGap = 355;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed1",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x += inputGap;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed6",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x = 75;
        y += rowH + 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed2",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x += inputGap;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed7",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x = 75;
        y += rowH + 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed3",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x += inputGap;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed8",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x = 75;
        y += rowH + 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed4",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x += inputGap;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed9",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x = 75;
        y += rowH + 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed5",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x += inputGap;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed10",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x = 75;
        y += rowH + 15;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Are you allergic to any of the following?",sheet.FontSize,sheet.FontName,false,x,y,400,rowH));
        y += rowH + 3;
        //x+=3;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Y",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 21;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("N",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 316;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Y",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 21;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("N",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x = 75;
        y += rowH;
        medicalCheckboxRowYN(sheet,"allergy:","Anesthetic","Iodine",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"allergy:","Aspirin","Latex",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"allergy:","Codeine","Penicillin",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"allergy:","Ibuprofen","Sulfa",x,y);
        y += rowH + 15;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Do you have any of the following medical conditions?",sheet.FontSize,sheet.FontName,false,x,y,400,rowH));
        y += rowH + 3;
        //x+=3;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Y",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 21;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("N",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 316;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Y",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 21;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("N",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x = 75;
        y += rowH;
        medicalCheckboxRowYN(sheet,"problem:","Asthma","Kidney Disease",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Bleeding Problems","Liver Disease",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Cancer","Pregnancy",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Diabetes","Psychiatric Treatment",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Heart Murmur","Sinus Trouble",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Heart Trouble","Stroke",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","High Blood Pressure","Ulcers",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Joint Replacement","Rheumatic Fever",x,y);
        y += rowH + 18;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Tobacco use?  If so, what kind and how much?",sheet.FontSize,sheet.FontName,false,x,y,289,rowH));
        x = 364;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,393,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,393,rowH));
        y += rowH + 2;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Unusual reaction to dental injections?",sheet.FontSize,sheet.FontName,false,x,y,232,rowH));
        x = 307;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,450,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,450,rowH));
        y += rowH + 2;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Reason for today's visit",sheet.FontSize,sheet.FontName,false,x,y,145,rowH));
        x = 220;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,275,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,275,rowH));
        x = 500;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Are you in pain?",sheet.FontSize,sheet.FontName,false,x,y,103,rowH));
        x = 603;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,154,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,154,rowH));
        y += rowH + 2;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("New patients:",sheet.FontSize,sheet.FontName,false,x,y,87,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Do you have a Panoramic x-ray or Full Mouth x-rays that are less than 5 years old?",sheet.FontSize,sheet.FontName,false,x,y,507,rowH));
        x = 602;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,155,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,155,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Do you have BiteWing x-rays that are less than 1 year old?",sheet.FontSize,sheet.FontName,false,x,y,360,rowH));
        x = 455;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,302,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,302,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Name of former dentist",sheet.FontSize,sheet.FontName,false,x,y,143,rowH));
        x = 238;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,275,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,275,rowH));
        x = 518;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("City/State",sheet.FontSize,sheet.FontName,false,x,y,64,rowH));
        x = 582;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,175,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,175,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Date of last cleaning and exam",sheet.FontSize,sheet.FontName,false,x,y,192,rowH));
        x = 287;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,470,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,470,rowH));
        y += 40;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Date: [dateToday]",sheet.FontSize,sheet.FontName,false,x,y,120,rowH));
        y += 40;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newSigBox(261,y,364,81));
        return sheet;
    }

    private static SheetDef medicalHistUpdate() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.MedicalHistory);
        sheet.Description = "Medical History Update";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 10f;
        sheet.Width = 850;
        sheet.Height = 1100;
        int rowH = 18;
        int y = 60;
        int x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Medical History Update", 12f, sheet.FontName, true, 325, y, 220, 20));
        y = 105;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Last Name:",sheet.FontSize,sheet.FontName,false,76,y,75,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("LName",sheet.FontSize,sheet.FontName,false,151,y,155,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("First Name:",sheet.FontSize,sheet.FontName,false,311,y,76,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("FName",sheet.FontSize,sheet.FontName,false,387,y,155,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Birthdate:",sheet.FontSize,sheet.FontName,false,547,y,65,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("Birthdate",sheet.FontSize,sheet.FontName,false,612,y,145,rowH));
        y += rowH + 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Name of Medical Doctor:",sheet.FontSize,sheet.FontName,false,x,y,155,rowH));
        x = 230;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,265,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,265,rowH));
        x = 500;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("City/State:",sheet.FontSize,sheet.FontName,false,x,y,67,rowH));
        x = 567;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,190,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,190,rowH));
        x = 75;
        y += rowH + 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Emergency Contact",sheet.FontSize,sheet.FontName,false,x,y,124,rowH));
        x = 199;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,138,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,138,rowH));
        x = 342;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Phone",sheet.FontSize,sheet.FontName,false,x,y,44,rowH));
        x = 386;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,99,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,99,rowH));
        x = 490;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Relationship",sheet.FontSize,sheet.FontName,false,x,y,80,rowH));
        x = 570;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,187,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,187,rowH));
        x = 75;
        y += rowH + 15;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Mark any medications that you are no longer taking and add any new ones:",sheet.FontSize,sheet.FontName,false,x,y,500,rowH));
        y += rowH + 8;
        int inputW = 300;
        int inputGap = 329;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 3,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("checkMed1","N",x,y + 5,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed1",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x += inputGap;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 3,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("checkMed6","N",x,y + 5,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed6",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x = 75;
        y += rowH + 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 3,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("checkMed2","N",x,y + 5,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed2",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x += inputGap;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 3,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("checkMed7","N",x,y + 5,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed7",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x = 75;
        y += rowH + 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 3,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("checkMed3","N",x,y + 5,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed3",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x += inputGap;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 3,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("checkMed8","N",x,y + 5,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed8",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x = 75;
        y += rowH + 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 3,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("checkMed4","N",x,y + 5,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed4",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x += inputGap;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 3,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("checkMed9","N",x,y + 5,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed9",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x = 75;
        y += rowH + 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 3,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("checkMed5","N",x,y + 5,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed5",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x += inputGap;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,y + 3,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton("checkMed10","N",x,y + 5,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("inputMed10",sheet.FontSize,sheet.FontName,false,x,y,inputW,18));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + 18,inputW,0));
        x = 75;
        x = 75;
        y += rowH + 15;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Are you allergic to any of the following?",sheet.FontSize,sheet.FontName,false,x,y,500,rowH));
        y += rowH + 3;
        //x+=3;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Y",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 21;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("N",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 316;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Y",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 21;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("N",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x = 75;
        y += rowH;
        medicalCheckboxRowYN(sheet,"allergy:","Anesthetic","Iodine",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"allergy:","Aspirin","Latex",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"allergy:","Codeine","Penicillin",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"allergy:","Ibuprofen","Sulfa",x,y);
        y += rowH + 15;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Do you have any of the following medical conditions?",sheet.FontSize,sheet.FontName,false,x,y,500,rowH));
        y += rowH + 3;
        //x+=3;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Y",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 21;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("N",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 316;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Y",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x += 21;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("N",sheet.FontSize,sheet.FontName,false,x,y,16,14));
        x = 75;
        y += rowH;
        medicalCheckboxRowYN(sheet,"problem:","Asthma","Kidney Disease",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Bleeding Problems","Liver Disease",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Cancer","Pregnancy",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Diabetes","Psychiatric Treatment",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Heart Murmur","Sinus Trouble",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Heart Trouble","Stroke",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","High Blood Pressure","Ulcers",x,y);
        y += rowH + 4;
        medicalCheckboxRowYN(sheet,"problem:","Joint Replacement","Rheumatic Fever",x,y);
        y += rowH + 18;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Tobacco use?  If so, what kind and how much?",sheet.FontSize,sheet.FontName,false,x,y,289,rowH));
        x = 364;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,393,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,393,rowH));
        y += rowH + 2;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Unusual reaction to dental injections?",sheet.FontSize,sheet.FontName,false,x,y,232,rowH));
        x = 307;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,450,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,450,rowH));
        y += rowH + 2;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Reason for today's visit",sheet.FontSize,sheet.FontName,false,x,y,145,rowH));
        x = 220;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,275,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,275,rowH));
        x = 500;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Are you in pain?",sheet.FontSize,sheet.FontName,false,x,y,103,rowH));
        x = 603;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,154,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,154,rowH));
        y += rowH + 2;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("New patients:",sheet.FontSize,sheet.FontName,false,x,y,87,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Do you have a Panoramic x-ray or Full Mouth x-rays that are less than 5 years old?",sheet.FontSize,sheet.FontName,false,x,y,507,rowH));
        x = 602;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,155,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,155,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Do you have BiteWing x-rays that are less than 1 year old?",sheet.FontSize,sheet.FontName,false,x,y,360,rowH));
        x = 455;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,302,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,302,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Name of former dentist",sheet.FontSize,sheet.FontName,false,x,y,143,rowH));
        x = 238;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,275,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,275,rowH));
        x = 518;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("City/State",sheet.FontSize,sheet.FontName,false,x,y,64,rowH));
        x = 582;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,175,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,175,rowH));
        y += rowH + 2;
        x = 95;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Date of last cleaning and exam",sheet.FontSize,sheet.FontName,false,x,y,192,rowH));
        x = 287;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(x,y + rowH,470,0));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,470,rowH));
        y += 40;
        x = 75;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Date: [dateToday]",sheet.FontSize,sheet.FontName,false,x,y,120,rowH));
        y += 40;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newSigBox(261,y,364,81));
        return sheet;
    }

    public static SheetDef labSlip() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.LabSlip);
        sheet.Description = "Lab Slip";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 10f;
        sheet.Width = 850;
        sheet.Height = 1100;
        int rowH = 18;
        int x = 75;
        int y = 50;
        //Title----------------------------------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Lab Slip", 12f, sheet.FontName, true, 270, y, 200, 22));
        y += 35;
        //Lab-----------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("lab.Description",sheet.FontSize,sheet.FontName,true,x,y,300,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("lab.Address",sheet.FontSize,sheet.FontName,false,x,y,300,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("lab.CityStZip",sheet.FontSize,sheet.FontName,false,x,y,300,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("lab.Phone",sheet.FontSize,sheet.FontName,false,x,y,300,rowH));
        y += rowH;
        y += 15;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Date:  [dateToday]",sheet.FontSize,sheet.FontName,false,x,y,140,rowH));
        y += rowH;
        //Prov-----------------------------------------------------------------------
        y += 15;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Doctor:",sheet.FontSize,sheet.FontName,false,x,y,50,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("prov.nameFormal",sheet.FontSize,sheet.FontName,false,x + 50,y,300,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("License No:",sheet.FontSize,sheet.FontName,false,x,y,78,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("prov.stateLicence",sheet.FontSize,sheet.FontName,false,x + 78,y,200,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Address:  [clinicAddress],  [clinicCityStZip]",sheet.FontSize,sheet.FontName,false,x,y,600,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Phone:  [clinicPhone]",sheet.FontSize,sheet.FontName,false,x,y,300,rowH));
        y += rowH;
        //Patient-----------------------------------------------------------------------
        y += 15;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Patient:  [nameFL]",sheet.FontSize,sheet.FontName,false,x,y,300,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Age: [age]      Gender: [gender]",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Due Date/Time:",sheet.FontSize,sheet.FontName,false,x,y,100,rowH));
        sheet.SheetFieldDefs.Add(SheetFieldDef.newOutput("labcase.DateTimeDue",sheet.FontSize,sheet.FontName,false,x + 100,y,200,rowH));
        y += rowH;
        //Instructions-----------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Instructions:",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("labcase.Instructions",sheet.FontSize,sheet.FontName,false,x,y,600,200));
        y += 220;
        //sig-------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Dr. Signature:",sheet.FontSize,sheet.FontName,false,x,y,200,rowH));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newSigBox(x,y,364,81));
        return sheet;
    }

    public static SheetDef examSheet() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.ExamSheet);
        sheet.Description = "Exam";
        sheet.FontName = "Microsoft Sans Serif";
        sheet.FontSize = 11f;
        sheet.Width = 850;
        sheet.Height = 1100;
        int rowH = 25;
        int y = 100;
        //Title----------------------------------------------------------------------------------------------------------
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Exam for [nameFL]", 12f, sheet.FontName, true, 275, y, 325, 20));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("sheet.DateTimeSheet", 12f, sheet.FontName, false, 350, y, 100, 20));
        y += rowH;
        int x = 100;
        y += 30;
        String[] fieldText = new String[]{ "TMJ", "Neck", "Tongue", "Palate", "Floor of Mouth" };
        for (int i = 0;i < fieldText.Length;i++)
        {
            sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText(fieldText[i], sheet.FontSize, sheet.FontName, false, x, y, 100, 20));
            sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x + 120,y + 2,15,15));
            sheet.SheetFieldDefs.Add(SheetFieldDef.newCheckBox("misc",x + 121,y + 4,13,13));
            sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("WNL",sheet.FontSize,sheet.FontName,false,x + 140,y,40,20));
            //sheet.SheetFieldDefs.Add(SheetFieldDef.NewLine(x+200,y+22,450,0));
            sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x + 200,y,450,40));
            y += 45;
        }
        //rowH;
        y += 25;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText("Additional Notes",sheet.FontSize,sheet.FontName,false,x,y,140,20));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newInput("misc",sheet.FontSize,sheet.FontName,false,x,y,600,100));
        return sheet;
    }

    public static SheetDef depositSlip() throws Exception {
        SheetDef sheet = new SheetDef(SheetTypeEnum.DepositSlip);
        sheet.Description = "Deposit Slip";
        sheet.FontName = FontFamily.GenericMonospace.Name;
        sheet.FontSize = 9f;
        sheet.Width = 850;
        sheet.Height = 1100;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("deposit.DateDeposit", 11f, sheet.FontName, false, 89, 156, 120, 17));
        //col 1, 6 boxes
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem01", 8f, sheet.FontName, false, 338, 62, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem02", 8f, sheet.FontName, false, 338, 90, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem03", 8f, sheet.FontName, false, 338, 118, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem04", 8f, sheet.FontName, false, 338, 146, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem05", 8f, sheet.FontName, false, 338, 173, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem06", 8f, sheet.FontName, false, 338, 201, 100, 20));
        //col 2, 7 boxes
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem07", 8f, sheet.FontName, false, 530, 34, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem08", 8f, sheet.FontName, false, 530, 62, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem09", 8f, sheet.FontName, false, 530, 90, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem10", 8f, sheet.FontName, false, 530, 118, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem11", 8f, sheet.FontName, false, 530, 146, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem12", 8f, sheet.FontName, false, 530, 173, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem13", 8f, sheet.FontName, false, 530, 201, 100, 20));
        //col 3, 5 boxes
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem14", 8f, sheet.FontName, false, 720, 34, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem15", 8f, sheet.FontName, false, 720, 62, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem16", 8f, sheet.FontName, false, 720, 90, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem17", 8f, sheet.FontName, false, 720, 118, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItem18", 8f, sheet.FontName, false, 720, 146, 100, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositTotal", 8f, sheet.FontName, false, 720, 173, 100, 20));
        //
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositItemCount", 8f, sheet.FontName, false, 556, 275, 50, 20));
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositTotal", 8f, sheet.FontName, false, 720, 275, 130, 20));
        int rowH = 20;
        int y = 399;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Deposit Slip", 16f, sheet.FontName, true, 323, y, 300, 30));
        y += 30;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("[practiceTitle]", 10f, sheet.FontName, true, 323, y, 300, 20));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("deposit.DateDeposit", 10f, sheet.FontName, false, 352, y, 200, 20));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewStaticText("Date: [dateToday]", 8f, sheet.FontName, false, 50, y, 160, 20));
        y += rowH;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(50,y,750,0));
        y += 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositList", 8f, sheet.FontName, false, 75, y, 700, 20, GrowthBehaviorEnum.DownGlobal));
        y += 26;
        //The actual y-value of the proceeding elements will be changed depending on the size of the depositList, since we are using DownGlobal growth.
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(50,y,750,0));
        y += 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("depositTotal", 8f, sheet.FontName, true, 562, y, 200, 20));
        y += rowH + 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newLine(50,y,750,0));
        y += 4;
        sheet.SheetFieldDefs.Add(SheetFieldDef.NewOutput("deposit.BankAccountInfo", 8f, sheet.FontName, true, 50, y, 700, 0, GrowthBehaviorEnum.DownGlobal));
        y += 1;
        return sheet;
    }

    //The actual y-value of the proceeding elements will be changed depending on the size of the deposit.BankAccountInfo, since we are using DownGlobal growth.
    /**
    * This will add two check box rows.  Set the prefix string to "allergy:" or "problem:", item1 and item2 should be set to the desired allergy/med in the row. Leave item2 blank for just one check box in the row.
    */
    private static void medicalCheckboxRowYN(SheetDef sheet, String prefix, String item1, String item2, int x, int y) throws Exception {
        int rectH = y + 3;
        int checkH = y + 5;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,rectH,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton(prefix + item1,"Y",x,checkH,11,11));
        x += 19;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,rectH,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton(prefix + item1,"N",x,checkH,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText(item1,sheet.FontSize,sheet.FontName,false,x,y,280,18));
        x += 290;
        if (StringSupport.equals(item2, ""))
        {
            return ;
        }
         
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,rectH,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton(prefix + item2,"Y",x,checkH,11,11));
        x += 19;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRect(x,rectH,14,14));
        x += 2;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newRadioButton(prefix + item2,"N",x,checkH,11,11));
        x += 24;
        sheet.SheetFieldDefs.Add(SheetFieldDef.newStaticText(item2,sheet.FontSize,sheet.FontName,false,x,y,280,18));
    }

}


