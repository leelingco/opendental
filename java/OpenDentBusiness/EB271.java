//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.BenefitQuantity;
import OpenDentBusiness.BenefitTimePeriod;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.DTP271;
import OpenDentBusiness.EB01;
import OpenDentBusiness.EB02;
import OpenDentBusiness.EB03;
import OpenDentBusiness.EB06;
import OpenDentBusiness.EB09;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.InsBenefitType;
import OpenDentBusiness.PIn;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.X12Segment;

/**
* One EB segment from a 271.  Helps to organize a 271 for presentation to the user.
*/
public class EB271   
{
    public X12Segment Segment;
    /**
    * Can be null if the segment can't be translated to an appropriate benefit.  Many fields of the Benefit won't be used.  Just ones needed for display.
    */
    public Benefit Benefitt;
    private static List<EB01> eb01 = new List<EB01>();
    private static List<EB02> eb02 = new List<EB02>();
    private static List<EB03> eb03 = new List<EB03>();
    /**
    * Since element 4 is descriptive rather than useful for import, we will leave it like this
    */
    private static Dictionary<String, String> EB04 = new Dictionary<String, String>();
    private static List<EB06> eb06 = new List<EB06>();
    private static List<EB09> eb09 = new List<EB09>();
    /**
    * Some EB segments have a few segments that follow them which should all be considered together as one "benefit".  Eg dates, addresses.
    */
    public List<X12Segment> SupplementalSegments = new List<X12Segment>();
    public EB271(X12Segment segment, boolean isInNetwork) throws Exception {
        if (eb01 == null)
        {
            fillDictionaries();
        }
         
        Segment = segment;
        SupplementalSegments = new List<X12Segment>();
        //start pattern matching to generate closest Benefit
        EB01 eb01val = eb01.Find(EB01MatchesCode);
        EB02 eb02val = eb02.Find(EB02MatchesCode);
        EB03 eb03val = eb03.Find(EB03MatchesCode);
        EB06 eb06val = eb06.Find(EB06MatchesCode);
        EB09 eb09val = eb09.Find(EB09MatchesCode);
        ProcedureCode proccode = null;
        if (ProcedureCodes.isValidCode(Segment.get(13,2)))
        {
            proccode = ProcedureCodes.getProcCode(Segment.get(13,2));
        }
         
        if (!eb01val.getIsSupported() || (eb02val != null && !eb02val.getIsSupported()) || (eb03val != null && !eb03val.getIsSupported()) || (eb06val != null && !eb06val.getIsSupported()) || (eb09val != null && !eb09val.getIsSupported()))
        {
            Benefitt = null;
            return ;
        }
         
        if (eb01val.getBenefitType() == InsBenefitType.ActiveCoverage && StringSupport.equals(Segment.get(3), "30"))
        {
            Benefitt = null;
            return ;
        }
         
        if (eb01val.getBenefitType() == InsBenefitType.ActiveCoverage && proccode != null)
        {
            //A code is covered.  Informational only.
            Benefitt = null;
            return ;
        }
         
        if (!StringSupport.equals(Segment.get(8), ""))
        {
            //if percentage
            //must have either a category or a proc code
            if (proccode == null)
            {
                //if no proc code is specified
                if (eb03val == null || eb03val.getServiceType() == EbenefitCategory.None || eb03val.getServiceType() == EbenefitCategory.General)
                {
                    //and no category specified
                    Benefitt = null;
                    return ;
                }
                 
            }
             
        }
         
        //coinsurance amounts are handled with fee schedules rather than benefits
        if (eb01val.getBenefitType() == InsBenefitType.CoPayment || eb01val.getBenefitType() == InsBenefitType.CoInsurance)
        {
            if (!StringSupport.equals(Segment.get(7), ""))
            {
                //and a monetary amount specified
                Benefitt = null;
                return ;
            }
             
        }
         
        //a limitation without an amount is meaningless
        if (eb01val.getBenefitType() == InsBenefitType.Limitations)
        {
            if (StringSupport.equals(Segment.get(7), ""))
            {
                //no monetary amount specified
                Benefitt = null;
                return ;
            }
             
        }
         
        if (isInNetwork && StringSupport.equals(Segment.get(12), "N"))
        {
            Benefitt = null;
            return ;
        }
         
        if (!isInNetwork && StringSupport.equals(Segment.get(12), "Y"))
        {
            Benefitt = null;
            return ;
        }
         
        //if only a quantity is specified with no qualifier, it's meaningless
        if (!StringSupport.equals(Segment.get(10), "") && eb09val == null)
        {
            Benefitt = null;
            return ;
        }
         
        //if only a qualifier is specified with no quantity, it's meaningless
        if (eb09val != null && StringSupport.equals(Segment.get(10), ""))
        {
            Benefitt = null;
            return ;
        }
         
        Benefitt = new Benefit();
        //1
        Benefitt.BenefitType = eb01val.getBenefitType();
        //2
        if (eb02val != null)
        {
            Benefitt.CoverageLevel = eb02val.getCoverageLevel();
        }
         
        //3
        if (eb03val != null)
        {
            Benefitt.CovCatNum = CovCats.getForEbenCat(eb03val.getServiceType()).CovCatNum;
        }
         
        //4-Insurance type - we ignore.
        //5-Plan description - we ignore.
        //6
        if (eb06val != null)
        {
            Benefitt.TimePeriod = eb06val.getTimePeriod();
        }
         
        //7
        if (!StringSupport.equals(Segment.get(7), ""))
        {
            Benefitt.MonetaryAmt = PIn.double(Segment.get(7));
        }
         
        //Monetary amount. Situational
        //8
        if (!StringSupport.equals(Segment.get(8), ""))
        {
            Benefitt.Percent = 100 - (int)(PIn.double(Segment.get(8)) * 100);
            //Percent. Situational
            Benefitt.CoverageLevel = BenefitCoverageLevel.None;
        }
         
        //9-Quantity qualifier
        if (eb09val != null)
        {
            Benefitt.QuantityQualifier = eb09val.getQuantityQualifier();
        }
         
        //10-Quantity
        if (!StringSupport.equals(Segment.get(10), ""))
        {
            Benefitt.Quantity = (byte)PIn.double(Segment.get(10));
        }
         
        //Example: "19.0" with Quantity qualifier "S7" (age).
        //11-Authorization. Ignored.
        //12-In network. Ignored.
        //13-proc
        if (proccode != null)
        {
            Benefitt.CodeNum = proccode.CodeNum;
        }
         
    }

    //element 13,2
    /**
    * The most human-readable description possible.  This is only used in one place, the 270/271 window.
    */
    public String getDescription(boolean isMessageMode) throws Exception {
        boolean containsAddress = false;
        boolean containsDate = false;
        for (int i = 0;i < SupplementalSegments.Count;i++)
        {
            if (StringSupport.equals(SupplementalSegments[i].SegmentID, "LS"))
            {
                containsAddress = true;
            }
             
            if (StringSupport.equals(SupplementalSegments[i].SegmentID, "DTP"))
            {
                containsDate = true;
            }
             
        }
        if (containsAddress)
        {
            return getDescriptionForAddress();
        }
         
        if (containsDate)
        {
            return getDescriptionForDate();
        }
         
        if (StringSupport.equals(Segment.get(1), "1") && !StringSupport.equals(Segment.get(13), ""))
        {
            return getDescriptionForCodeCovered();
        }
         
        //active coverage and a proc code indicated
        //informational only
        //if a co-insurance, and a percentage, and a proc code, then special display
        if (StringSupport.equals(Segment.get(1), "A") && !StringSupport.equals(Segment.get(8), "") && !StringSupport.equals(Segment.get(13), ""))
        {
            return getDescriptionForPercentCode();
        }
         
        String retVal = "";
        String txt = new String();
        txt = getDescript(1,isMessageMode);
        //Eligibility or benefit information. Required
        if (!StringSupport.equals(txt, ""))
        {
            retVal += txt;
        }
         
        /*//only show coverage level for things like deductible and annual max
        			txt=GetDescript(2);//Coverage level code. Situational
        			if(txt!="") {
        				retVal+=", "+txt;
        			}*/
        if (!StringSupport.equals(Segment.get(3), "30"))
        {
            //we don't want to show the generic 30 because of clutter.
            txt = getDescript(3);
            //Service type code. Situational
            if (!StringSupport.equals(txt, ""))
            {
                retVal += ", " + txt;
            }
             
        }
         
        txt = getDescript(4);
        //Insurance type code. Situational
        if (!StringSupport.equals(txt, ""))
        {
            retVal += ", " + txt;
        }
         
        txt = getDescript(5);
        //Plan coverage description. Situational
        if (isMessageMode && !StringSupport.equals(txt, ""))
        {
            //Causes clutter.
            if (!StringSupport.equals(retVal, ""))
            {
                retVal += ", ";
            }
             
            retVal += txt;
        }
         
        txt = getDescript(6);
        //Time period qualifier. Situational
        if (!StringSupport.equals(txt, ""))
        {
            retVal += ", " + txt;
        }
         
        txt = getDescript(7);
        //Monetary amount. Situational
        if (!StringSupport.equals(txt, ""))
        {
            retVal += ", " + txt;
        }
         
        txt = getDescript(8,isMessageMode);
        //Percent. Situational
        if (!StringSupport.equals(txt, ""))
        {
            retVal += ", " + txt;
        }
         
        txt = getDescript(9);
        //Quantity qualifier. Situational
        if (!StringSupport.equals(txt, ""))
        {
            retVal += ", " + txt;
        }
         
        txt = getDescript(10);
        //Quantity. Situational
        if (!StringSupport.equals(txt, ""))
        {
            retVal += ", " + txt;
        }
         
        txt = getDescript(11);
        //Situational.
        if (!StringSupport.equals(txt, ""))
        {
            retVal += ", " + txt;
        }
         
        txt = getDescript(12);
        //Situational.
        if (!StringSupport.equals(txt, ""))
        {
            retVal += ", " + txt;
        }
         
        txt = getDescript(13);
        //Procedure identifier. Situational
        if (!StringSupport.equals(txt, ""))
        {
            retVal += ", " + txt;
        }
         
        for (int i = 0;i < SupplementalSegments.Count;i++)
        {
            //addresses already handled
            //messages are just annoying and cluttery
            if (!isMessageMode)
            {
                continue;
            }
             
            if (StringSupport.equals(SupplementalSegments[i].SegmentID, "MSG"))
            {
                retVal += ", " + SupplementalSegments[i].Get(1);
            }
             
        }
        return retVal;
    }

    private String getDescriptionForAddress() throws Exception {
        String retVal = getDescript(1) + "\r\n";
        for (int i = 0;i < SupplementalSegments.Count;i++)
        {
            //tells us what kind of address
            if (StringSupport.equals(SupplementalSegments[i].SegmentID, "NM1"))
            {
                retVal += SupplementalSegments[i].Get(3) + " " + SupplementalSegments[i].Get(4) + "\r\n";
            }
             
            //LName and optional FName
            if (StringSupport.equals(SupplementalSegments[i].SegmentID, "N3"))
            {
                retVal += SupplementalSegments[i].Get(1) + " " + SupplementalSegments[i].Get(2) + "\r\n";
            }
             
            //Address 1 and 2
            if (StringSupport.equals(SupplementalSegments[i].SegmentID, "N4"))
            {
                retVal += SupplementalSegments[i].Get(1) + ", " + SupplementalSegments[i].Get(2) + SupplementalSegments[i].Get(3);
            }
             
        }
        return retVal;
    }

    //City, State Zip
    private String getDescriptionForDate() throws Exception {
        String retVal = "";
        for (int i = 0;i < SupplementalSegments.Count;i++)
        {
            if (StringSupport.equals(SupplementalSegments[i].SegmentID, "DTP"))
            {
                retVal += DTP271.GetQualifierDescript(SupplementalSegments[i].Get(1)) + ": " + DTP271.GetDateStr(SupplementalSegments[i].Get(2), SupplementalSegments[i].Get(3));
            }
             
        }
        return retVal;
    }

    /**
    * Informational only
    */
    private String getDescriptionForCodeCovered() throws Exception {
        String descript = getDescript(13);
        if (StringSupport.equals(descript, ""))
        {
            return "";
        }
         
        return "Covered: " + descript;
    }

    /**
    * 
    */
    private String getDescriptionForPercentCode() throws Exception {
        String descript = getDescript(8);
        //patient pays 0%
        String txt = getDescript(12);
        //in or out of network
        if (!StringSupport.equals(txt, ""))
        {
            descript += ", " + txt;
        }
         
        descript += ", " + getDescript(13);
        return descript;
    }

    //proc
    /**
    * The most human-readable description possible for a single element.
    */
    public String getDescript(int elementPos) throws Exception {
        return getDescript(elementPos,false);
    }

    /**
    * The most human-readable description possible for a single element.
    */
    public String getDescript(int elementPos, boolean isMessageMode) throws Exception {
        String elementCode = Segment.get(elementPos);
        if (StringSupport.equals(elementCode, ""))
        {
            return "";
        }
         
        switch(elementPos)
        {
            case 1: 
                //This is a required element, but we still won't assume it's found
                EB01 eb01val = eb01.Find(EB01MatchesCode);
                if (eb01val == null)
                {
                    return "";
                }
                 
                if (StringSupport.equals(eb01val.getCode(), "D") && isMessageMode)
                {
                    return "";
                }
                 
                return eb01val.getDescript();
            case 2: 
                //D is for benefit description, which is already obvious
                EB02 eb02val = eb02.Find(EB02MatchesCode);
                if (eb02val == null)
                {
                    return "";
                }
                 
                return eb02val.getDescript();
            case 3: 
                EB03 eb03val = eb03.Find(EB03MatchesCode);
                if (eb03val == null)
                {
                    return "";
                }
                 
                return eb03val.getDescript();
            case 4: 
                if (!EB04.ContainsKey(elementCode))
                {
                    return "";
                }
                 
                return EB04[elementCode];
            case 5: 
                return Segment.get(5);
            case 6: 
                EB06 eb06val = eb06.Find(EB06MatchesCode);
                if (eb06val == null)
                {
                    return "";
                }
                 
                return eb06val.getDescript();
            case 7: 
                return PIn.double(elementCode).ToString("c");
            case 8: 
                //Monetary amount. Situational
                if (isMessageMode)
                {
                    return (PIn.double(elementCode) * 100).ToString() + "%";
                }
                else
                {
                    return "Patient pays " + (PIn.double(elementCode) * 100).ToString() + "%";
                } 
            case 9: 
                //delta sends 80% instead of 20% like they should
                //Percent.
                //Percent. Situational
                //Quantity qualifier. Situational
                EB09 eb09val = eb09.Find(EB09MatchesCode);
                if (eb09val == null)
                {
                    return "";
                }
                 
                return eb09val.getDescript();
            case 10: 
                return elementCode;
            case 11: 
                return "Authorization Required-" + elementCode;
            case 12: 
                //Quantity. Situational
                //Situational.
                //Situational.
                if (StringSupport.equals(elementCode, "Y"))
                {
                    return "In network";
                }
                else
                {
                    return "Out of network";
                } 
            case 13: 
                String procStr = Segment.get(13,2);
                if (StringSupport.equals(procStr, ""))
                {
                    return "";
                }
                 
                ProcedureCode procCode = ProcedureCodes.getProcCode(procStr);
                return procStr + " - " + procCode.AbbrDesc;
            default: 
                return "";
        
        }
    }

    //ProcedureCodes.GetLaymanTerm(procCode.CodeNum);
    //Even though we don't make requests about specific procedure codes, some ins co's will send back codes.
    /**
    * Search predicate returns true if code matches.
    */
    private boolean eB01MatchesCode(EB01 eb01val) throws Exception {
        if (StringSupport.equals(Segment.get(1), eb01val.getCode()))
        {
            return true;
        }
         
        return false;
    }

    /**
    * Search predicate returns true if code matches.
    */
    private boolean eB02MatchesCode(EB02 eb02val) throws Exception {
        if (StringSupport.equals(Segment.get(2), eb02val.getCode()))
        {
            return true;
        }
         
        return false;
    }

    /**
    * Search predicate returns true if code matches.
    */
    private boolean eB03MatchesCode(EB03 eb03val) throws Exception {
        if (StringSupport.equals(Segment.get(3), eb03val.getCode()))
        {
            return true;
        }
         
        return false;
    }

    /**
    * Search predicate returns true if code matches.
    */
    private boolean eB06MatchesCode(EB06 eb06val) throws Exception {
        if (StringSupport.equals(Segment.get(6), eb06val.getCode()))
        {
            return true;
        }
         
        return false;
    }

    /**
    * Search predicate returns true if code matches.
    */
    private boolean eB09MatchesCode(EB09 eb09val) throws Exception {
        if (StringSupport.equals(Segment.get(9), eb09val.getCode()))
        {
            return true;
        }
         
        return false;
    }

    /**
    * Gives us a raw string of the original EB segment as well as all supplemental segments.  It's somewhat reconstructed rather than strictly the original.
    */
    public String toString() {
        try
        {
            String retVal = Segment.toString() + "~";
            for (int i = 0;i < SupplementalSegments.Count;i++)
            {
                retVal += "\r\n" + SupplementalSegments[i].ToString() + "~";
            }
            return retVal;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    private static void fillDictionaries() throws Exception {
        eb01 = new List<EB01>();
        eb01.Add(new EB01("1","Active Coverage",InsBenefitType.ActiveCoverage));
        eb01.Add(new EB01("2","Active - Full Risk Capitation",InsBenefitType.ActiveCoverage));
        eb01.Add(new EB01("3","Active - Services Capitated",InsBenefitType.ActiveCoverage));
        eb01.Add(new EB01("4","Active - Services Capitated to Primary Care Physician"));
        eb01.Add(new EB01("5","Active - Pending Investigation"));
        eb01.Add(new EB01("6","Inactive"));
        eb01.Add(new EB01("7","Inactive - Pending Eligibility Update"));
        eb01.Add(new EB01("8","Inactive - Pending Investigation"));
        eb01.Add(new EB01("A","Co-Insurance",InsBenefitType.CoInsurance));
        eb01.Add(new EB01("B","Co-Payment",InsBenefitType.CoPayment));
        eb01.Add(new EB01("C","Deductible",InsBenefitType.Deductible));
        eb01.Add(new EB01("CB","Coverage Basis"));
        eb01.Add(new EB01("D","Benefit Description"));
        eb01.Add(new EB01("E","Exclusions",InsBenefitType.Exclusions));
        eb01.Add(new EB01("F","Limitations",InsBenefitType.Limitations));
        eb01.Add(new EB01("G","Out of Pocket (Stop Loss)"));
        eb01.Add(new EB01("H","Unlimited"));
        eb01.Add(new EB01("I","Non-Covered",InsBenefitType.Exclusions));
        eb01.Add(new EB01("J","Cost Containment"));
        eb01.Add(new EB01("K","Reserve"));
        eb01.Add(new EB01("L","Primary Care Provider"));
        eb01.Add(new EB01("M","Pre-existing Condition"));
        eb01.Add(new EB01("MC","Managed Care Coordinator"));
        eb01.Add(new EB01("N","Services Restricted to Following Provider"));
        eb01.Add(new EB01("O","Not Deemed a Medical Necessity"));
        eb01.Add(new EB01("P","Benefit Disclaimer"));
        eb01.Add(new EB01("Q","Second Surgical Opinion Required"));
        eb01.Add(new EB01("R","Other or Additional Payor"));
        eb01.Add(new EB01("S","Prior Year(s) History"));
        eb01.Add(new EB01("T","Card(s) Reported Lost/Stolen"));
        eb01.Add(new EB01("U","Contact Following Entity for Information"));
        //Too long: ...for Eligibility or Benefit Information"));
        eb01.Add(new EB01("V","Cannot Process"));
        eb01.Add(new EB01("W","Other Source of Data"));
        eb01.Add(new EB01("X","Health Care Facility"));
        eb01.Add(new EB01("Y","Spend Down"));
        //------------------------------------------------------------------------------------------------------
        eb02 = new List<EB02>();
        eb02.Add(new EB02("CHD","Children Only"));
        eb02.Add(new EB02("DEP","Dependents Only"));
        eb02.Add(new EB02("ECH","Employee and Children",BenefitCoverageLevel.Family));
        eb02.Add(new EB02("EMP","Employee Only"));
        eb02.Add(new EB02("ESP","Employee and Spouse",BenefitCoverageLevel.Family));
        eb02.Add(new EB02("FAM","Family",BenefitCoverageLevel.Family));
        eb02.Add(new EB02("IND","Individual",BenefitCoverageLevel.Individual));
        eb02.Add(new EB02("SPC","Spouse and Children"));
        eb02.Add(new EB02("SPO","Spouse Only"));
        //------------------------------------------------------------------------------------------------------
        eb03 = new List<EB03>();
        eb03.Add(new EB03("1","Medical Care"));
        eb03.Add(new EB03("2","Surgical"));
        eb03.Add(new EB03("3","Consultation"));
        eb03.Add(new EB03("4","Diagnostic X-Ray",EbenefitCategory.DiagnosticXRay));
        eb03.Add(new EB03("5","Diagnostic Lab"));
        eb03.Add(new EB03("6","Radiation Therapy"));
        eb03.Add(new EB03("7","Anesthesia"));
        eb03.Add(new EB03("8","Surgical Assistance"));
        eb03.Add(new EB03("9","Other Medical"));
        eb03.Add(new EB03("10","Blood Charges"));
        eb03.Add(new EB03("11","Used Durable Medical Equipment"));
        eb03.Add(new EB03("12","Durable Medical Equipment Purchase"));
        eb03.Add(new EB03("13","Ambulatory Service Center Facility"));
        eb03.Add(new EB03("14","Renal Supplies in the Home"));
        eb03.Add(new EB03("15","Alternate Method Dialysis"));
        eb03.Add(new EB03("16","Chronic Renal Disease (CRD) Equipment"));
        eb03.Add(new EB03("17","Pre-Admission Testing"));
        eb03.Add(new EB03("18","Durable Medical Equipment Rental"));
        eb03.Add(new EB03("19","Pneumonia Vaccine"));
        eb03.Add(new EB03("20","Second Surgical Opinion"));
        eb03.Add(new EB03("21","Third Surgical Opinion"));
        eb03.Add(new EB03("22","Social Work"));
        eb03.Add(new EB03("23","Diagnostic Dental",EbenefitCategory.Diagnostic));
        eb03.Add(new EB03("24","Periodontics",EbenefitCategory.Periodontics));
        eb03.Add(new EB03("25","Restorative",EbenefitCategory.Restorative));
        eb03.Add(new EB03("26","Endodontics",EbenefitCategory.Endodontics));
        eb03.Add(new EB03("27","Maxillofacial Prosthetics",EbenefitCategory.MaxillofacialProsth));
        eb03.Add(new EB03("28","Adjunctive Dental Services",EbenefitCategory.Adjunctive));
        eb03.Add(new EB03("30","Health Benefit Plan Coverage",EbenefitCategory.General));
        eb03.Add(new EB03("32","Plan Waiting Period"));
        eb03.Add(new EB03("33","Chiropractic"));
        eb03.Add(new EB03("34","Chiropractic Office Visits"));
        eb03.Add(new EB03("35","Dental Care",EbenefitCategory.General));
        eb03.Add(new EB03("36","Dental Crowns",EbenefitCategory.Crowns));
        eb03.Add(new EB03("37","Dental Accident",EbenefitCategory.Accident));
        eb03.Add(new EB03("38","Orthodontics",EbenefitCategory.Orthodontics));
        eb03.Add(new EB03("39","Prosthodontics",EbenefitCategory.Prosthodontics));
        eb03.Add(new EB03("40","Oral Surgery",EbenefitCategory.OralSurgery));
        eb03.Add(new EB03("41","Routine (Preventive) Dental",EbenefitCategory.RoutinePreventive));
        eb03.Add(new EB03("42","Home Health Care"));
        eb03.Add(new EB03("43","Home Health Prescriptions"));
        eb03.Add(new EB03("44","Home Health Visits"));
        eb03.Add(new EB03("45","Hospice"));
        eb03.Add(new EB03("46","Respite Care"));
        eb03.Add(new EB03("47","Hospital"));
        eb03.Add(new EB03("48","Hospital - Inpatient"));
        eb03.Add(new EB03("49","Hospital - Room and Board"));
        eb03.Add(new EB03("50","Hospital - Outpatient"));
        eb03.Add(new EB03("51","Hospital - Emergency Accident"));
        eb03.Add(new EB03("52","Hospital - Emergency Medical"));
        eb03.Add(new EB03("53","Hospital - Ambulatory Surgical"));
        eb03.Add(new EB03("54","Long Term Care"));
        eb03.Add(new EB03("55","Major Medical"));
        eb03.Add(new EB03("56","Medically Related Transportation"));
        eb03.Add(new EB03("57","Air Transportation"));
        eb03.Add(new EB03("58","Cabulance"));
        eb03.Add(new EB03("59","Licensed Ambulance"));
        eb03.Add(new EB03("60","General Benefits"));
        eb03.Add(new EB03("61","In-vitro Fertilization"));
        eb03.Add(new EB03("62","MRI/CAT Scan"));
        eb03.Add(new EB03("63","Donor Procedures"));
        eb03.Add(new EB03("64","Acupuncture"));
        eb03.Add(new EB03("65","Newborn Care"));
        eb03.Add(new EB03("66","Pathology"));
        eb03.Add(new EB03("67","Smoking Cessation"));
        eb03.Add(new EB03("68","Well Baby Care"));
        eb03.Add(new EB03("69","Maternity"));
        eb03.Add(new EB03("70","Transplants"));
        eb03.Add(new EB03("71","Audiology Exam"));
        eb03.Add(new EB03("72","Inhalation Therapy"));
        eb03.Add(new EB03("73","Diagnostic Medical"));
        eb03.Add(new EB03("74","Private Duty Nursing"));
        eb03.Add(new EB03("75","Prosthetic Device"));
        eb03.Add(new EB03("76","Dialysis"));
        eb03.Add(new EB03("77","Otological Exam"));
        eb03.Add(new EB03("78","Chemotherapy"));
        eb03.Add(new EB03("79","Allergy Testing"));
        eb03.Add(new EB03("80","Immunizations"));
        eb03.Add(new EB03("81","Routine Physical"));
        eb03.Add(new EB03("82","Family Planning"));
        eb03.Add(new EB03("83","Infertility"));
        eb03.Add(new EB03("84","Abortion"));
        eb03.Add(new EB03("85","AIDS"));
        eb03.Add(new EB03("86","Emergency Services"));
        eb03.Add(new EB03("87","Cancer"));
        eb03.Add(new EB03("88","Pharmacy"));
        eb03.Add(new EB03("89","Free Standing Prescription Drug"));
        eb03.Add(new EB03("90","Mail Order Prescription Drug"));
        eb03.Add(new EB03("91","Brand Name Prescription Drug"));
        eb03.Add(new EB03("92","Generic Prescription Drug"));
        eb03.Add(new EB03("93","Podiatry"));
        eb03.Add(new EB03("94","Podiatry - Office Visits"));
        eb03.Add(new EB03("95","Podiatry - Nursing Home Visits"));
        eb03.Add(new EB03("96","Professional (Physician)"));
        eb03.Add(new EB03("97","Anesthesiologist"));
        eb03.Add(new EB03("98","Professional (Physician) Visit - Office"));
        eb03.Add(new EB03("99","Professional (Physician) Visit - Inpatient"));
        eb03.Add(new EB03("A0","Professional (Physician) Visit - Outpatient"));
        eb03.Add(new EB03("A1","Professional (Physician) Visit - Nursing Home"));
        eb03.Add(new EB03("A2","Professional (Physician) Visit - Skilled Nursing Facility"));
        eb03.Add(new EB03("A3","Professional (Physician) Visit - Home"));
        eb03.Add(new EB03("A4","Psychiatric"));
        eb03.Add(new EB03("A5","Psychiatric - Room and Board"));
        eb03.Add(new EB03("A6","Psychotherapy"));
        eb03.Add(new EB03("A7","Psychiatric - Inpatient"));
        eb03.Add(new EB03("A8","Psychiatric - Outpatient"));
        eb03.Add(new EB03("A9","Rehabilitation"));
        eb03.Add(new EB03("AA","Rehabilitation - Room and Board"));
        eb03.Add(new EB03("AB","Rehabilitation - Inpatient"));
        eb03.Add(new EB03("AC","Rehabilitation - Outpatient"));
        eb03.Add(new EB03("AD","Occupational Therapy"));
        eb03.Add(new EB03("AE","Physical Medicine"));
        eb03.Add(new EB03("AF","Speech Therapy"));
        eb03.Add(new EB03("AG","Skilled Nursing Care"));
        eb03.Add(new EB03("AH","Skilled Nursing Care - Room and Board"));
        eb03.Add(new EB03("AI","Substance Abuse"));
        eb03.Add(new EB03("AJ","Alcoholism"));
        eb03.Add(new EB03("AK","Drug Addiction"));
        eb03.Add(new EB03("AL","Vision (Optometry)"));
        eb03.Add(new EB03("AM","Frames"));
        eb03.Add(new EB03("AN","Routine Exam"));
        eb03.Add(new EB03("AO","Lenses"));
        eb03.Add(new EB03("AQ","Nonmedically Necessary Physical"));
        eb03.Add(new EB03("AR","Experimental Drug Therapy"));
        eb03.Add(new EB03("BA","Independent Medical Evaluation"));
        eb03.Add(new EB03("BB","Partial Hospitalization (Psychiatric)"));
        eb03.Add(new EB03("BC","Day Care (Psychiatric)"));
        eb03.Add(new EB03("BD","Cognitive Therapy"));
        eb03.Add(new EB03("BE","Massage Therapy"));
        eb03.Add(new EB03("BF","Pulmonary Rehabilitation"));
        eb03.Add(new EB03("BG","Cardiac Rehabilitation"));
        eb03.Add(new EB03("BH","Pediatric"));
        eb03.Add(new EB03("BI","Nursery"));
        eb03.Add(new EB03("BJ","Skin"));
        eb03.Add(new EB03("BK","Orthopedic"));
        eb03.Add(new EB03("BL","Cardiac"));
        eb03.Add(new EB03("BM","Lymphatic"));
        eb03.Add(new EB03("BN","Gastrointestinal"));
        eb03.Add(new EB03("BP","Endocrine"));
        eb03.Add(new EB03("BQ","Neurology"));
        eb03.Add(new EB03("BR","Eye"));
        eb03.Add(new EB03("BS","Invasive Procedures"));
        //------------------------------------------------------------------------------------------------------
        EB04 = new Dictionary<String, String>();
        EB04.Add("12", "Medicare Secondary Working Aged Beneficiary or Spouse with Employer Group Health Plan");
        EB04.Add("13", "Medicare Secondary End-Stage Renal Disease Beneficiary in the 12 month coordination period with an employer’s group health plan");
        EB04.Add("14", "Medicare Secondary, No-fault Insurance including Auto is Primary");
        EB04.Add("15", "Medicare Secondary Worker’s Compensation");
        EB04.Add("16", "Medicare Secondary Public Health Service (PHS)or Other Federal Agency");
        EB04.Add("41", "Medicare Secondary Black Lung");
        EB04.Add("42", "Medicare Secondary Veteran’s Administration");
        EB04.Add("43", "Medicare Secondary Disabled Beneficiary Under Age 65 with Large Group Health Plan (LGHP)");
        EB04.Add("47", "Medicare Secondary, Other Liability Insurance is Primary");
        EB04.Add("AP", "Auto Insurance Policy");
        EB04.Add("C1", "Commercial");
        EB04.Add("CO", "Consolidated Omnibus Budget Reconciliation Act (COBRA)");
        EB04.Add("CP", "Medicare Conditionally Primary");
        EB04.Add("D", "Disability");
        EB04.Add("DB", "Disability Benefits");
        EB04.Add("EP", "Exclusive Provider Organization");
        EB04.Add("FF", "Family or Friends");
        EB04.Add("GP", "Group Policy");
        EB04.Add("HM", "Health Maintenance Organization (HMO)");
        EB04.Add("HN", "Health Maintenance Organization (HMO) - Medicare Risk");
        EB04.Add("HS", "Special Low Income Medicare Beneficiary");
        EB04.Add("IN", "Indemnity");
        EB04.Add("IP", "Individual Policy");
        EB04.Add("LC", "Long Term Care");
        EB04.Add("LD", "Long Term Policy");
        EB04.Add("LI", "Life Insurance");
        EB04.Add("LT", "Litigation");
        EB04.Add("MA", "Medicare Part A");
        EB04.Add("MB", "Medicare Part B");
        EB04.Add("MC", "Medicaid");
        EB04.Add("MH", "Medigap Part A");
        EB04.Add("MI", "Medigap Part B");
        EB04.Add("MP", "Medicare Primary");
        EB04.Add("OT", "Other");
        EB04.Add("PE", "Property Insurance - Personal");
        EB04.Add("PL", "Personal");
        EB04.Add("PP", "Personal Payment (Cash - No Insurance)");
        EB04.Add("PR", "Preferred Provider Organization (PPO)");
        EB04.Add("PS", "Point of Service (POS)");
        EB04.Add("QM", "Qualified Medicare Beneficiary");
        EB04.Add("RP", "Property Insurance - Real");
        EB04.Add("SP", "Supplemental Policy");
        EB04.Add("TF", "Tax Equity Fiscal Responsibility Act (TEFRA)");
        EB04.Add("WC", "Workers Compensation");
        EB04.Add("WU", "Wrap Up Policy");
        //------------------------------------------------------------------------------------------------------
        eb06 = new List<EB06>();
        eb06.Add(new EB06("6","Hour"));
        eb06.Add(new EB06("7","Day"));
        eb06.Add(new EB06("13","24 Hours"));
        eb06.Add(new EB06("21","Years",BenefitTimePeriod.Years));
        eb06.Add(new EB06("22","Service Year",BenefitTimePeriod.ServiceYear));
        eb06.Add(new EB06("23","Calendar Year",BenefitTimePeriod.CalendarYear));
        eb06.Add(new EB06("24","Year to Date"));
        eb06.Add(new EB06("25","Contract"));
        eb06.Add(new EB06("26","Episode"));
        eb06.Add(new EB06("27","Visit"));
        eb06.Add(new EB06("28","Outlier"));
        eb06.Add(new EB06("29","Remaining"));
        eb06.Add(new EB06("30","Exceeded"));
        eb06.Add(new EB06("31","Not Exceeded"));
        eb06.Add(new EB06("32","Lifetime",BenefitTimePeriod.Lifetime));
        eb06.Add(new EB06("33","Lifetime Remaining"));
        eb06.Add(new EB06("34","Month"));
        eb06.Add(new EB06("35","Week"));
        eb06.Add(new EB06("36","Admisson"));
        //------------------------------------------------------------------------------------------------------
        eb09 = new List<EB09>();
        eb09.Add(new EB09("99","Quantity Used"));
        eb09.Add(new EB09("CA","Covered - Actual"));
        eb09.Add(new EB09("CE","Covered - Estimated"));
        eb09.Add(new EB09("DB","Deductible Blood Units"));
        eb09.Add(new EB09("DY","Days"));
        eb09.Add(new EB09("HS","Hours"));
        eb09.Add(new EB09("LA","Life-time Reserve - Actual"));
        eb09.Add(new EB09("LE","Life-time Reserve - Estimated"));
        eb09.Add(new EB09("MN","Month",BenefitQuantity.Months));
        eb09.Add(new EB09("P6","Number of Services or Procedures",BenefitQuantity.NumberOfServices));
        eb09.Add(new EB09("QA","Quantity Approved"));
        eb09.Add(new EB09("S7","Age, High Value",BenefitQuantity.AgeLimit));
        eb09.Add(new EB09("S8","Age, Low Value"));
        eb09.Add(new EB09("VS","Visits",BenefitQuantity.Visits));
        eb09.Add(new EB09("YY","Years",BenefitQuantity.Years));
    }

}


