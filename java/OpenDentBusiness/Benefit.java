//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.BenefitQuantity;
import OpenDentBusiness.BenefitTimePeriod;
import OpenDentBusiness.CovCatC;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.InsBenefitType;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.TableBase;

/**
* Corresponds to the benefit table in the database which replaces the old covpat table.  A benefit is usually a percentage, deductible, limitation, max, or similar. Each row represents a single benefit.  A benefit can have a value in EITHER PlanNum OR PatPlanNum.  If it is for a PlanNum, the most common, then the benefit is attached to an insurance plan.  If it is for a PatPlanNum, then it overrides the plan benefit, usually a percentage, for a single patient.  Benefits we can't handle yet include posterior composites, COB duplication, amounts used, in/out of plan network, authorization required, missing tooth exclusion, and any date related limitations like waiting periods.
* Here are examples of typical usage which parallel X12 usage.
* Example fields shown in this order:
* CovCat, ProcCode(- indicates blank), BenefitType, Percent, MonetaryAmt, TimePeriod, QuantityQualifier, Quantity, CoverageLevel
* Annual Max Indiv $1000: None/General,-,Limitations,-1,1000,CalendarYear,None,0,Individual
* Restorative 80%: Restorative,-,CoInsurance,80,-1,CalendarYear,None,0,None
* $50 deductible: None/General,-,Deductible,-1,50,CalendarYear,None,0,Individual
* Deductible waived on preventive: Preventive,-,Deductible,-1,0,CalendarYear,None,0,Individual
* 1 pano every 5 years: None,D0330,Limitations,-1,-1,Years?,Years,5,None
* 2 exams per year: Preventive(or Diagnostic),-,Limitations,-1,-1,BenefitYear,NumberOfServices,2,None
* Fluoride limit 18yo: None, D1204, Limitations, -1, -1, CalendarYear/None, AgeLimit, 18,None (might require a second identical entry for D1205)
* 4BW every 6 months: None, D0274, Limitations, -1, -1, None, Months, 6,None.
* The text above might be difficult to read.  We are trying to improve the white spacing.
*/
public class Benefit  extends TableBase implements IComparable
{
    /**
    * Primary key.
    */
    public long BenefitNum = new long();
    /**
    * FK to insplan.PlanNum.  Most benefits should be attached using PlanNum.  The exception would be if each patient has a different percentage.  If PlanNum is used, then PatPlanNum should be 0.
    */
    public long PlanNum = new long();
    /**
    * FK to patplan.PatPlanNum.  It is rare to attach benefits this way.  Usually only used to override percentages for patients.   In this case, PlanNum should be 0.
    */
    public long PatPlanNum = new long();
    /**
    * FK to covcat.CovCatNum.  Corresponds to X12 EB03- Service Type code.  Situational, so it can be 0.  Will probably be 0 for general deductible and annual max.  There are very specific categories covered by X12. Users should set their InsCovCats to the defaults we provide.
    */
    public long CovCatNum = new long();
    /**
    * Enum:InsBenefitType Corresponds to X12 EB01. Examples: 0=ActiveCoverage, 1=CoInsurance, 2=Deductible, 3=CoPayment, 4=Exclusions, 5=Limitations. ActiveCoverage doesn't really provide meaningful information.
    */
    public InsBenefitType BenefitType = InsBenefitType.ActiveCoverage;
    /**
    * Only used if BenefitType=CoInsurance.  Valid values are 0 to 100.  -1 indicates empty, which is almost always true if not CoInsurance.  The percentage that insurance will pay on the procedure.  Note that benefits coming from carriers are usually backwards, indicating the percetage that the patient is responsible for.
    */
    public int Percent = new int();
    /**
    * Used for CoPayment, Limitations, and Deductible.  -1 indicates empty
    */
    public double MonetaryAmt = new double();
    /**
    * Enum:BenefitTimePeriod Corresponds to X12 EB06, Time Period Qualifier.  Examples: 0=None,1=ServiceYear,2=CalendarYear,3=Lifetime,4=Years. Might add Visit and Remaining.
    */
    public BenefitTimePeriod TimePeriod = BenefitTimePeriod.None;
    /**
    * Enum:BenefitQuantity Corresponds to X12 EB09. Not used very much. Examples: 0=None,1=NumberOfServices,2=AgeLimit,3=Visits,4=Years,5=Months
    */
    public BenefitQuantity QuantityQualifier = BenefitQuantity.None;
    /**
    * Corresponds to X12 EB10. Qualify the quantity using QuantityQualifier.
    */
    public byte Quantity = new byte();
    /**
    * FK to procedurecode.CodeNum.  Typical uses include fluoride, sealants, etc.  If a specific code is used here, then the CovCat should be None.
    */
    public long CodeNum = new long();
    /**
    * Enum:BenefitCoverageLevel Corresponds to X12 EB02.  None, Individual, or Family.  Individual and Family are commonly used for deductibles and maximums.  None is commonly used for percentages and copays.
    */
    public BenefitCoverageLevel CoverageLevel = BenefitCoverageLevel.None;
    public Benefit() throws Exception {
        Percent = -1;
        MonetaryAmt = -1;
    }

    public String toString() {
        try
        {
            //return base.ToString();
            String retVal = "";
            retVal += BenefitType.ToString();
            //EB01: Eligibility or benefit information. Required
            if (CoverageLevel != BenefitCoverageLevel.None)
            {
                retVal += ", " + CoverageLevel.ToString();
            }
             
            //EB02: Coverage level code. Situational
            EbenefitCategory ebenCat = CovCats.getEbenCat(CovCatNum);
            if (ebenCat != EbenefitCategory.None)
            {
                retVal += ", " + ebenCat.ToString();
            }
             
            //EB03: Service type code. Situational
            //EB04: Insurance type code. Situational.  Not a Benefit field.  We treat it as plan level.
            //EB05: Plan coverage description. Situational.  Not a Benefit field.  We treat it as plan level.
            if (TimePeriod != BenefitTimePeriod.None)
            {
                retVal += ", " + TimePeriod.ToString();
            }
             
            //EB06: Time period qualifier. Situational
            if (MonetaryAmt != -1)
            {
                retVal += ", " + MonetaryAmt.ToString("c");
            }
             
            //EB07: Monetary amount. Situational
            if (Percent != -1)
            {
                retVal += ", " + Percent.ToString() + "%";
            }
             
            //EB08: Percent. Situational
            if (QuantityQualifier != BenefitQuantity.None)
            {
                retVal += ", " + QuantityQualifier.ToString();
            }
             
            //EB09: Quantity qualifier. Situational
            if (Quantity != 0)
            {
                retVal += ", " + Quantity.ToString();
            }
             
            //EB10: Quantity. Situational
            //EB11: Authorization Required. Situational.  Not a Benefit field.
            //EB12: In plan network.  Situational.  Not a Benefit field.
            //EB13:  Procedure identifier. Situational.  We don't import this from EB, but we can show it anyway.
            if (CodeNum != 0)
            {
                ProcedureCode proccode = ProcedureCodes.getProcCode(CodeNum);
                retVal += ", " + proccode.ProcCode + " - " + proccode.AbbrDesc;
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

    /**
    * IComparable.CompareTo implementation.  This is used to order benefit lists as well as to group benefits if the type is essentially equal.  It doesn't compare values such as percentages or amounts.  It only compares types.
    */
    public int compareTo(Object obj) throws Exception {
        if (!(obj instanceof Benefit))
        {
            throw new ArgumentException("object is not a Benefit");
        }
         
        Benefit ben = (Benefit)obj;
        //first by fam
        if (CoverageLevel != ben.CoverageLevel)
        {
            return CoverageLevel.CompareTo(ben.CoverageLevel);
        }
         
        //then by type
        if (BenefitType != ben.BenefitType)
        {
            return BenefitType.CompareTo(ben.BenefitType);
        }
         
        //if types are different
        //types are the same, so check covCat. This is a loose comparison, ignored if either is 0.
        //if both covcats have values
        if (CovCatNum != 0 && ben.CovCatNum != 0 && CovCatNum != ben.CovCatNum)
        {
            return CovCatC.getOrderLong(CovCatNum).CompareTo(CovCatC.getOrderLong(ben.CovCatNum));
        }
         
        //and they are different
        //return CovCats.GetOrderShort(CovCatNum).CompareTo(CovCats.GetOrderShort(ben.CovCatNum));
        //this line was changed because we really do need to know if they have different covcats.
        //ProcCode
        //THIS IS WRONG! NEED TO COMPARE THE PROCCODES, NOT THE CODENUMS.
        if (CodeNum != ben.CodeNum)
        {
            return CodeNum.CompareTo(ben.CodeNum);
        }
         
        //TimePeriod-ServiceYear and CalendarYear are treated as the same.
        //if either are not serviceYear or CalendarYear
        if ((TimePeriod != BenefitTimePeriod.CalendarYear && TimePeriod != BenefitTimePeriod.ServiceYear) || (ben.TimePeriod != BenefitTimePeriod.CalendarYear && ben.TimePeriod != BenefitTimePeriod.ServiceYear))
        {
            return TimePeriod.CompareTo(ben.TimePeriod);
        }
         
        //QuantityQualifier
        if (QuantityQualifier != ben.QuantityQualifier)
        {
            return QuantityQualifier.CompareTo(ben.QuantityQualifier);
        }
         
        //if different
        //always different if plan vs. pat override
        if (PatPlanNum == 0 && ben.PatPlanNum != 0)
        {
            return -1;
        }
         
        if (PlanNum == 0 && ben.PlanNum != 0)
        {
            return 1;
        }
         
        return 0;
    }

    //Last resort.  Can't find any significant differences in the type, so:
    //then values are the same.
    /**
    * 
    */
    public Benefit copy() throws Exception {
        return (Benefit)MemberwiseClone();
    }

    /**
    * Returns true if most of the fields match except BenefitNum
    */
    public boolean isSimilar(Benefit ben) throws Exception {
        //PlanNum             != oldBenefitList[i].PlanNum
        //|| PatPlanNum        != oldBenefitList[i].PatPlanNum
        if (CovCatNum != ben.CovCatNum || BenefitType != ben.BenefitType || Percent != ben.Percent || MonetaryAmt != ben.MonetaryAmt || TimePeriod != ben.TimePeriod || QuantityQualifier != ben.QuantityQualifier || Quantity != ben.Quantity || CodeNum != ben.CodeNum || CoverageLevel != ben.CoverageLevel)
        {
            return false;
        }
         
        return true;
    }

}


