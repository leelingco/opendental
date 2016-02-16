//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:38 PM
//

package UnitTests;

import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.BenefitLogic;
import OpenDentBusiness.BenefitQuantity;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.BenefitTimePeriod;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.InsBenefitType;
import OpenDentBusiness.ProcedureCodes;

public class BenefitT   
{
    public static void createAnnualMax(long planNum, double amt) throws Exception {
        Benefit ben = new Benefit();
        ben.PlanNum = planNum;
        ben.BenefitType = InsBenefitType.Limitations;
        ben.CovCatNum = 0;
        ben.CoverageLevel = BenefitCoverageLevel.Individual;
        ben.MonetaryAmt = amt;
        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
        Benefits.insert(ben);
    }

    public static void createDeductibleGeneral(long planNum, BenefitCoverageLevel coverageLevel, double amt) throws Exception {
        Benefit ben = new Benefit();
        ben.PlanNum = planNum;
        ben.BenefitType = InsBenefitType.Deductible;
        ben.CovCatNum = 0;
        ben.CoverageLevel = coverageLevel;
        ben.MonetaryAmt = amt;
        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
        Benefits.insert(ben);
    }

    public static void createDeductible(long planNum, EbenefitCategory category, double amt) throws Exception {
        Benefit ben = new Benefit();
        ben.PlanNum = planNum;
        ben.BenefitType = InsBenefitType.Deductible;
        ben.CovCatNum = CovCats.getForEbenCat(category).CovCatNum;
        ben.CoverageLevel = BenefitCoverageLevel.Individual;
        ben.MonetaryAmt = amt;
        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
        Benefits.insert(ben);
    }

    /**
    * Takes an individual codeNum instead of a category.
    */
    public static void createDeductible(long planNum, String procCodeStr, double amt) throws Exception {
        Benefit ben = new Benefit();
        ben.PlanNum = planNum;
        ben.BenefitType = InsBenefitType.Deductible;
        ben.CovCatNum = 0;
        ben.CodeNum = ProcedureCodes.getCodeNum(procCodeStr);
        ben.CoverageLevel = BenefitCoverageLevel.Individual;
        ben.MonetaryAmt = amt;
        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
        Benefits.insert(ben);
    }

    public static void createLimitation(long planNum, EbenefitCategory category, double amt) throws Exception {
        Benefit ben = new Benefit();
        ben.PlanNum = planNum;
        ben.BenefitType = InsBenefitType.Limitations;
        ben.CovCatNum = CovCats.getForEbenCat(category).CovCatNum;
        ben.CoverageLevel = BenefitCoverageLevel.Individual;
        ben.MonetaryAmt = amt;
        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
        Benefits.insert(ben);
    }

    public static void createLimitationProc(long planNum, String procCodeStr, double amt) throws Exception {
        Benefit ben = new Benefit();
        ben.PlanNum = planNum;
        ben.BenefitType = InsBenefitType.Limitations;
        ben.CodeNum = ProcedureCodes.getCodeNum(procCodeStr);
        ben.CoverageLevel = BenefitCoverageLevel.Individual;
        ben.MonetaryAmt = amt;
        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
        Benefits.insert(ben);
    }

    public static void createAnnualMaxFamily(long planNum, double amt) throws Exception {
        Benefit ben = new Benefit();
        ben.PlanNum = planNum;
        ben.BenefitType = InsBenefitType.Limitations;
        ben.CovCatNum = 0;
        ben.CoverageLevel = BenefitCoverageLevel.Family;
        ben.MonetaryAmt = amt;
        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
        Benefits.insert(ben);
    }

    public static void createCategoryPercent(long planNum, EbenefitCategory category, int percent) throws Exception {
        Benefit ben = new Benefit();
        ben.PlanNum = planNum;
        ben.BenefitType = InsBenefitType.CoInsurance;
        ben.CovCatNum = CovCats.getForEbenCat(category).CovCatNum;
        ben.CoverageLevel = BenefitCoverageLevel.None;
        ben.Percent = percent;
        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
        Benefits.insert(ben);
    }

    public static void createFrequencyProc(long planNum, String procCodeStr, BenefitQuantity quantityQualifier, Byte quantity) throws Exception {
        Benefit ben = new Benefit();
        ben.PlanNum = planNum;
        ben.BenefitType = InsBenefitType.Limitations;
        ben.CovCatNum = 0;
        ben.CodeNum = ProcedureCodes.getCodeNum(procCodeStr);
        ben.CoverageLevel = BenefitCoverageLevel.None;
        ben.TimePeriod = BenefitTimePeriod.None;
        ben.Quantity = quantity;
        ben.QuantityQualifier = quantityQualifier;
        Benefits.insert(ben);
    }

    public static void createFrequencyCategory(long planNum, EbenefitCategory category, BenefitQuantity quantityQualifier, Byte quantity) throws Exception {
        Benefit ben = new Benefit();
        ben.PlanNum = planNum;
        ben.BenefitType = InsBenefitType.Limitations;
        ben.CovCatNum = CovCats.getForEbenCat(category).CovCatNum;
        ben.CoverageLevel = BenefitCoverageLevel.None;
        ben.TimePeriod = BenefitTimePeriod.None;
        ben.Quantity = quantity;
        ben.QuantityQualifier = quantityQualifier;
        Benefits.insert(ben);
    }

    public static void createOrthoMax(long planNum, double amt) throws Exception {
        Benefit ben = new Benefit();
        ben.PlanNum = planNum;
        ben.BenefitType = InsBenefitType.Limitations;
        ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Orthodontics).CovCatNum;
        ben.CoverageLevel = BenefitCoverageLevel.Individual;
        ben.MonetaryAmt = amt;
        ben.TimePeriod = BenefitTimePeriod.Lifetime;
        Benefits.insert(ben);
    }

    public static String benefitComputeRenewDate() throws Exception {
        DateTime asofDate = new DateTime(2006, 3, 19);
        //bool isCalendarYear=true;
        //DateTime insStartDate=new DateTime(2003,3,1);
        DateTime result = BenefitLogic.computeRenewDate(asofDate,0);
        if (result != new DateTime(2006, 1, 1))
        {
            throw new ApplicationException("BenefitComputeRenewDate 1 failed.\r\n");
        }
         
        //isCalendarYear=false;//for the remaining tests
        //earlier in same month
        result = BenefitLogic.computeRenewDate(asofDate,3);
        if (result != new DateTime(2006, 3, 1))
        {
            throw new ApplicationException("BenefitComputeRenewDate 2 failed.\r\n");
        }
         
        //earlier month in year
        asofDate = new DateTime(2006, 5, 1);
        result = BenefitLogic.computeRenewDate(asofDate,3);
        if (result != new DateTime(2006, 3, 1))
        {
            throw new ApplicationException("BenefitComputeRenewDate 3 failed.\r\n");
        }
         
        asofDate = new DateTime(2006, 12, 1);
        result = BenefitLogic.computeRenewDate(asofDate,3);
        if (result != new DateTime(2006, 3, 1))
        {
            throw new ApplicationException("BenefitComputeRenewDate 4 failed.\r\n");
        }
         
        //later month in year
        asofDate = new DateTime(2006, 2, 1);
        result = BenefitLogic.computeRenewDate(asofDate,3);
        if (result != new DateTime(2005, 3, 1))
        {
            throw new ApplicationException("BenefitComputeRenewDate 5 failed.\r\n");
        }
         
        asofDate = new DateTime(2006, 2, 12);
        result = BenefitLogic.computeRenewDate(asofDate,3);
        if (result != new DateTime(2005, 3, 1))
        {
            throw new ApplicationException("BenefitComputeRenewDate 6 failed.\r\n");
        }
         
        return "BenefitComputeRenewDates passed.\r\n";
    }

}


//Insurance start date not on the 1st.//no longer possible
//asofDate=new DateTime(2008,5,10);
//insStartDate=new DateTime(2007,1,12);
//result=BenefitLogic.ComputeRenewDate(asofDate,isCalendarYear,insStartDate);
//if(result!=new DateTime(2008,1,1)) {
//	textResults.Text+="BenefitComputeRenewDate 7 failed.\r\n";
//}