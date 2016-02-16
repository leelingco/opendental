//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:33 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.FormClaimFormItemEdit;
import OpenDental.FormClaimPrint;
import OpenDentBusiness.ClaimForm;
import OpenDentBusiness.ClaimFormItem;
import OpenDentBusiness.ClaimSendQueueItem;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clearinghouses;

/**
* Summary description for Renaissance.
*/
public class Renaissance   
{
    private static ClaimFormItem[] items = new ClaimFormItem[]();
    private static FormClaimFormItemEdit FormCFI;
    private static String[][] DisplayStrings = new String[][]();
    private static Clearinghouse clearinghouse;
    /**
    * Summary description for Renaissance.
    */
    public Renaissance() throws Exception {
    }

    /**
    * Called from Eclaims and includes multiple claims.
    */
    public static String sendBatch(List<ClaimSendQueueItem> queueItems, int batchNum) throws Exception {
        for (int i = 0;i < queueItems.Count;i++)
        {
            //A setting for the clearinghouse could have changed so we need to always refresh the clearinghouse variable before sending any batches.
            if (i == 0)
            {
                clearinghouse = Clearinghouses.GetClearinghouse(queueItems[i].ClearinghouseNum);
            }
             
            if (!CreateClaim(queueItems[i].PatNum, queueItems[i].ClaimNum, batchNum))
            {
                return "";
            }
             
        }
        return "Sent";
    }

    /**
    * Called once for each claim to be created.  For claims with a lot of procedures, this may actually create multiple claims.
    */
    public static boolean createClaim(long patNum, long claimNum, int batchNum) throws Exception {
        //this can be eliminated later after error checking complete:
        FormCFI = new FormClaimFormItemEdit();
        FormCFI.FillFieldNames();
        items = new ClaimFormItem[241];
        //0 is not used
        fill(1,"IsPreAuth");
        fill(2,"IsStandardClaim");
        fill(3,"IsMedicaidClaim");
        fill(4,"");
        //EPSTD
        fill(5,"TreatingProviderSpecialty");
        fill(6,"PreAuthString");
        fill(7,"PriInsCarrierName");
        fill(8,"PriInsAddressComplete");
        fill(9,"PriInsCity");
        fill(10,"PriInsST");
        fill(11,"PriInsZip");
        fill(12,"PatientLastFirst");
        fill(13,"PatientAddressComplete");
        fill(14,"PatientCity");
        fill(15,"PatientST");
        fill(16,"PatientDOB","MM/dd/yyyy");
        fill(17,"PatientID-MedicaidOrSSN");
        fill(18,"PatientIsMale");
        fill(19,"PatientIsFemale");
        fill(20,"PatientPhone");
        fill(21,"PatientZip");
        fill(22,"RelatIsSelf");
        fill(23,"RelatIsSpouse");
        fill(24,"RelatIsChild");
        fill(25,"RelatIsOther");
        fill(26,"");
        //relat other descript
        fill(27,"");
        //pat employer/school
        fill(28,"");
        //pat employer/school address
        fill(29,"SubscrID");
        fill(30,"EmployerName");
        fill(31,"GroupNum");
        fill(32,"OtherInsNotExists");
        fill(33,"OtherInsExists");
        fill(34,"OtherInsExists");
        //dental
        fill(35,"");
        //other ins exists medical
        fill(36,"OtherInsSubscrID");
        fill(37,"SubscrLastFirst");
        fill(38,"OtherInsSubscrLastFirst");
        fill(39,"SubscrAddressComplete");
        fill(40,"SubscrPhone");
        fill(41,"OtherInsSubscrDOB","MM/dd/yyyy");
        fill(42,"OtherInsSubscrIsMale");
        fill(43,"OtherInsSubscrIsFemale");
        fill(44,"OtherInsGroupNum");
        //Other Plan/Program name
        fill(45,"SubscrCity");
        fill(46,"SubscrST");
        fill(47,"SubscrZip");
        fill(48,"");
        //other subscr employer/school
        fill(49,"");
        //other subscr emp/school address
        fill(50,"SubscrDOB","MM/dd/yyyy");
        fill(51,"SubscrIsMarried");
        fill(52,"SubscrIsSingle");
        fill(53,"");
        //subscr marital status other
        fill(54,"SubscrIsMale");
        fill(55,"SubscrIsFemale");
        fill(56,"");
        //subscr is employed
        fill(57,"");
        //subscr is part time
        fill(58,"SubscrIsFTStudent");
        fill(59,"SubscrIsPTStudent");
        fill(60,"");
        //subsc employer/school
        fill(61,"");
        //subsc employer/school address
        fill(62,"PatientRelease");
        fill(63,"PatientReleaseDate","MM/dd/yyyy");
        fill(64,"PatientAssignment");
        fill(65,"PatientAssignmentDate","MM/dd/yyyy");
        fill(66,"BillingDentist");
        fill(67,"BillingDentistPhoneFormatted");
        fill(68,"BillingDentistMedicaidID");
        fill(69,"BillingDentistSSNorTIN");
        fill(70,"PayToDentistAddress");
        fill(71,"PayToDentistAddress2");
        fill(72,"BillingDentistLicenseNum");
        fill(73,"DateService","MM/dd/yyyy");
        fill(74,"PlaceIsOffice");
        fill(75,"PlaceIsHospADA2002");
        fill(76,"PlaceIsExtCareFacilityADA2002");
        fill(77,"PlaceIsOtherADA2002");
        fill(78,"PayToDentistCity");
        fill(79,"PayToDentistST");
        fill(80,"PayToDentistZip");
        fill(81,"IsRadiographsAttached");
        fill(82,"RadiographsNumAttached");
        fill(83,"RadiographsNotAttached");
        fill(84,"IsOrtho");
        fill(85,"IsNotOrtho");
        fill(86,"IsInitialProsth");
        fill(87,"IsReplacementProsth");
        fill(88,"");
        //reason for replacement
        fill(89,"DatePriorProsthPlaced","MM/dd/yyyy");
        fill(90,"DateOrthoPlaced");
        fill(91,"MonthsOrthoRemaining");
        fill(92,"IsNotOccupational");
        fill(93,"IsOccupational");
        fill(94,"");
        //description of occupational injury
        fill(95,"IsAutoAccident");
        fill(96,"IsOtherAccident");
        fill(97,"IsNotAccident");
        fill(98,"");
        //description of accident
        fill(99,"TreatingDentistNPI");
        //Individual NPI
        fill(100,"");
        //''
        fill(101,"BillingDentistNPI");
        //Group NPI
        fill(102,"");
        //''
        fill(103,"");
        //''
        fill(104,"");
        //''
        fill(105,"");
        //''
        fill(106,"");
        //''
        //proc 1
        fill(107,"P1Date","MM/dd/yyyy");
        fill(108,"P1ToothNumber");
        fill(109,"P1Surface");
        fill(110,"");
        //diag index
        fill(111,"P1Code");
        fill(112,"");
        //quantity
        fill(113,"P1Description");
        fill(114,"P1Fee");
        //proc 2
        fill(115,"P2Date","MM/dd/yyyy");
        fill(116,"P2ToothNumber");
        fill(117,"P2Surface");
        fill(118,"");
        fill(119,"P2Code");
        fill(120,"");
        fill(121,"P2Description");
        fill(122,"P2Fee");
        //proc 3
        fill(123,"P3Date","MM/dd/yyyy");
        fill(124,"P3ToothNumber");
        fill(125,"P3Surface");
        fill(126,"");
        fill(127,"P3Code");
        fill(128,"");
        fill(129,"P3Description");
        fill(130,"P3Fee");
        //proc 4
        fill(131,"P4Date","MM/dd/yyyy");
        fill(132,"P4ToothNumber");
        fill(133,"P4Surface");
        fill(134,"");
        fill(135,"P4Code");
        fill(136,"");
        fill(137,"P4Description");
        fill(138,"P4Fee");
        //proc 5
        fill(139,"P5Date","MM/dd/yyyy");
        fill(140,"P5ToothNumber");
        fill(141,"P5Surface");
        fill(142,"");
        fill(143,"P5Code");
        fill(144,"");
        fill(145,"P5Description");
        fill(146,"P5Fee");
        //proc 6
        fill(147,"P6Date","MM/dd/yyyy");
        fill(148,"P6ToothNumber");
        fill(149,"P6Surface");
        fill(150,"");
        fill(151,"P6Code");
        fill(152,"");
        fill(153,"P6Description");
        fill(154,"P6Fee");
        //proc 7
        fill(155,"P7Date","MM/dd/yyyy");
        fill(156,"P7ToothNumber");
        fill(157,"P7Surface");
        fill(158,"");
        fill(159,"P7Code");
        fill(160,"");
        fill(161,"P7Description");
        fill(162,"P7Fee");
        //proc 8
        fill(163,"P8Date","MM/dd/yyyy");
        fill(164,"P8ToothNumber");
        fill(165,"P8Surface");
        fill(166,"");
        fill(167,"P8Code");
        fill(168,"");
        fill(169,"P8Description");
        fill(170,"P8Fee");
        //end of procs
        fill(171,"TotalFee");
        fill(172,"");
        //payment by other plan. Only applicable on secondary insurance.
        fill(173,"");
        //max allowable
        fill(174,"");
        //deductible
        fill(175,"");
        //carrier percent
        fill(176,"");
        //carrier pays
        fill(177,"");
        //patient pays
        fill(178,"");
        //Work injury.  Only x is accepted.
        fill(179,"P1Area");
        fill(180,"");
        fill(181,"P2Area");
        fill(182,"");
        fill(183,"P3Area");
        fill(184,"");
        fill(185,"P4Area");
        fill(186,"");
        fill(187,"P5Area");
        fill(188,"");
        fill(189,"P6Area");
        fill(190,"");
        fill(191,"P7Area");
        fill(192,"");
        fill(193,"P8Area");
        fill(194,"");
        fill(195,"");
        fill(196,"P9Area");
        fill(197,"");
        fill(198,"");
        fill(199,"");
        fill(200,"");
        fill(201,"");
        fill(202,"");
        fill(203,"");
        fill(204,"P10Area");
        fill(205,"");
        fill(206,"");
        fill(207,"");
        fill(208,"");
        fill(209,"");
        fill(210,"");
        fill(211,"");
        fill(212,"");
        fill(213,"");
        fill(214,"");
        fill(215,"");
        fill(216,"OtherInsCarrierName");
        //COB insurance company name
        fill(217,"OtherInsAddress");
        //COB address
        fill(218,"OtherInsCity");
        //COB ins City
        fill(219,"OtherInsST");
        //COB ins State
        fill(220,"OtherInsZip");
        //COB Zip
        fill(221,"");
        fill(222,"");
        fill(223,"");
        fill(224,"");
        fill(225,"");
        fill(226,"");
        fill(227,"");
        fill(228,"");
        fill(229,"");
        fill(230,"Remarks");
        fill(231,"");
        fill(232,"");
        fill(233,"");
        fill(234,"TreatingDentistSignature");
        //Treating Dentist Signature - Will accept any text string
        fill(235,"TreatingDentistLicense");
        //Treating License # - Will accept any text string
        fill(236,"TreatingDentistSigDate");
        //Date Signed - Expecting MM/DD/YYYY
        fill(237,"TreatingDentistAddress");
        //Address Where Procedure Performed - Will accept any text string
        fill(238,"TreatingDentistCity");
        //City Where Procedure Performed - Will accept any text string
        fill(239,"TreatingDentistST");
        //State Where Procedure Performed - Will accept any text string
        fill(240,"TreatingDentistZip");
        //Zip Code Where Procedure Performed - Expecting 5 digit
        ClaimFormItem[] listForForm = new ClaimFormItem[241];
        //items.CopyTo(ClaimFormItems.ListForForm,0);
        FormClaimPrint FormCP = new FormClaimPrint();
        FormCP.ClaimNumCur = claimNum;
        FormCP.PatNumCur = patNum;
        FormCP.ClaimFormCur = new ClaimForm();
        FormCP.ClaimFormCur.Items = new ClaimFormItem[items.Length];
        items.CopyTo(FormCP.ClaimFormCur.Items, 0);
        DisplayStrings = FormCP.fillRenaissance();
        saveFile(batchNum);
        return true;
    }

    private static void fill(int index, String fieldName, String formatString) throws Exception {
        if (!StringSupport.equals(fieldName, ""))
        {
            for (int i = 0;i < FormCFI.FieldNames.Length;i++)
            {
                if (StringSupport.equals(FormCFI.FieldNames[i], fieldName))
                {
                    break;
                }
                 
                if (i == FormCFI.FieldNames.Length - 1)
                {
                    MessageBox.Show(fieldName + " is not valid");
                    return ;
                }
                 
            }
        }
         
        items[index] = new ClaimFormItem();
        items[index].FieldName = fieldName;
        items[index].FormatString = formatString;
    }

    private static void fill(int index, String fieldName) throws Exception {
        fill(index,fieldName,"");
    }

    private static void saveFile(int batchNum) throws Exception {
        for (int i = 0;i < DisplayStrings.GetLength(0);i++)
        {
            //this actually gets the current batch number since it was already incremented
            //int batchNum=PIn.PInt(((Pref)PrefC.HList["RenaissanceLastBatchNumber"]).ValueString);
            //usually 1, but sometimes 2 or 3
            String uploadPath = clearinghouse.ExportPath;
            //@"C:\Program Files\Renaissance\dotr\\upload\";
            if (!Directory.Exists(uploadPath))
            {
                MessageBox.Show("Error. Renaissance not installed.  " + uploadPath + " not valid");
                return ;
            }
             
            int fileEnd = 1;
            String fileName = new String();
            do
            {
                //loop to find the next available filename
                fileName = "C" + batchNum.ToString().PadLeft(3, '0') + "C" + fileEnd.ToString().PadLeft(3, '0') + ".rss";
                fileEnd++;
            }
            while (File.Exists(uploadPath + fileName));
            //Since this is a windows program, no need to use ODFileUtils.CombinePaths()
            StreamWriter sw = new StreamWriter(uploadPath + fileName);
            try
            {
                {
                    for (int ii = 1;ii < DisplayStrings[i].Length;ii++)
                    {
                        sw.WriteLine(ii.ToString().PadLeft(3, '0') + ":" + DisplayStrings[i][ii]);
                    }
                }
            }
            finally
            {
                if (sw != null)
                    Disposable.mkDisposable(sw).dispose();
                 
            }
        }
    }

    /**
    * Returns a string describing all missing data on this claim.  Claim will not be allowed to be sent electronically unless this string comes back empty.
    */
    public static String getMissingData(ClaimSendQueueItem queueItem) throws Exception {
        return "";
    }

}


//Our support for Renaissance is minimal, because they do not use the X12 format and we do not recommend that our customers use it.
//Thus, we do not perform validation.