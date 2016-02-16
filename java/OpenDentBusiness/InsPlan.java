//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EnumCobRule;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.TableBase;

//Any changes made to this tabletype needs to be documented on the Online Manual
/**
* Subscribers can share insplans by using the InsSub table.  The patplan table determines coverage for individual patients.  InsPlans can also exist without any subscriber.
*/
public class InsPlan  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PlanNum = new long();
    /**
    * Optional
    */
    public String GroupName = new String();
    /**
    * Optional.  In Canada, this is called the Plan Number.
    */
    public String GroupNum = new String();
    /**
    * Note for this plan.  Same for all subscribers.
    */
    public String PlanNote = new String();
    /**
    * FK to feesched.FeeSchedNum.
    */
    public long FeeSched = new long();
    /**
    * ""=percentage(the default),"p"=ppo_percentage,"f"=flatCopay,"c"=capitation.
    */
    public String PlanType = new String();
    /**
    * FK to claimform.ClaimFormNum. eg. "1" for ADA2002.  For ADA2006, it varies by office.
    */
    public long ClaimFormNum = new long();
    /**
    * 0=no,1=yes.  could later be extended if more alternates required
    */
    public boolean UseAltCode = new boolean();
    /**
    * Fee billed on claim should be the UCR fee for the patient's provider.
    */
    public boolean ClaimsUseUCR = new boolean();
    /**
    * FK to feesched.FeeSchedNum. Not usually used. This fee schedule holds only co-pays(patient portions).  Only used for Capitation or for fixed copay plans.
    */
    public long CopayFeeSched = new long();
    /**
    * FK to employer.EmployerNum.
    */
    public long EmployerNum = new long();
    /**
    * FK to carrier.CarrierNum.
    */
    public long CarrierNum = new long();
    /**
    * FK to feesched.FeeSchedNum. Not usually used.  This fee schedule holds amounts allowed by carriers.
    */
    public long AllowedFeeSched = new long();
    /**
    * .
    */
    public String TrojanID = new String();
    /**
    * Only used in Canada. It's a suffix to the plan number (group number).
    */
    public String DivisionNo = new String();
    /**
    * True if this is medical insurance rather than dental insurance.  When creating a claim, this, along with pref.
    */
    public boolean IsMedical = new boolean();
    /**
    * FK to insfilingcode.InsFilingCodeNum.  Used for e-claims.  Also used for some complex reports in public health.  The e-claim usage might become obsolete when PlanID implemented by HIPAA.  Can be 0 to indicate none.  Then 'CI' will go out on claims.
    */
    public long FilingCode = new long();
    /**
    * Canadian e-claim field. D11 and E07.  Zero indicates empty.  Mandatory value for Dentaide.  Not used for all others.  2 digit.
    */
    public byte DentaideCardSequence = new byte();
    /**
    * If checked, the units Qty will show the base units assigned to a procedure on the claim form.
    */
    public boolean ShowBaseUnits = new boolean();
    /**
    * Set to true to not allow procedure code downgrade substitution on this insurance plan.
    */
    public boolean CodeSubstNone = new boolean();
    /**
    * Set to true to hide it from the pick list and from the main list.
    */
    public boolean IsHidden = new boolean();
    /**
    * The month, 1 through 12 when the insurance plan renews.  It will renew on the first of the month.  To indicate calendar year, set renew month to 0.
    */
    public byte MonthRenew = new byte();
    /**
    * FK to insfilingcodesubtype.InsFilingCodeSubtypeNum
    */
    public long FilingCodeSubtype = new long();
    /**
    * Canadian C12.  Single char, usually blank.  If non-blank, then it's one of three kinds of Provincial Medical Plans.  A=Newfoundland MCP Plan.  V=Veteran's Affairs Plan.  N=NIHB.  N and V are not yet in use, so they will result in blank being sent instead.  See Elig5.
    */
    public String CanadianPlanFlag = new String();
    /**
    * Canadian C39. Required when CanadianPlanFlag is 'A'.
    */
    public String CanadianDiagnosticCode = new String();
    /**
    * Canadian C40. Required when CanadianPlanFlag is 'A'.
    */
    public String CanadianInstitutionCode = new String();
    /**
    * BIN location number.  Only used with EHR.
    */
    public String RxBIN = new String();
    /**
    * Enum:EnumCobRule 0=Basic, 1=Standard, 2=CarveOut.
    */
    public EnumCobRule CobRule = EnumCobRule.Basic;
    /**
    * FK to sop.SopCode. Examples: 121, 3115, etc.  Acts as default for all patients using this insurance.  When code is changed for an insplan, it should change automatically for patients having that primary insurance.
    */
    public String SopCode = new String();
    /**
    * This is not a database column.  It is just used to display the number of plans with the same info.
    */
    public int NumberSubscribers = new int();
    /*
    		///<summary>IComparable.CompareTo implementation.  This is used to determine if plans are identical.  The criteria is that they have 6 fields in common: Employer, Carrier, GroupName, GroupNum, DivisionNo, and IsMedical.  There is no less than or greater than; just not equal.</summary>
    		public long CompareTo(object obj) {
    			if(!(obj is InsPlan)) {
    				throw new ArgumentException("object is not an InsPlan");
    			}
    			InsPlan plan=(InsPlan)obj;
    			if(plan.EmployerNum==EmployerNum
    				&& plan.CarrierNum==CarrierNum
    				&& plan.GroupName==GroupName
    				&& plan.GroupNum==GroupNum
    				&& plan.DivisionNo==DivisionNo
    				&& plan.IsMedical==IsMedical)
    			{
    				return 0;//they are the same
    			}
    			return -1;
    		}*/
    /**
    * Returns a copy of this InsPlan.
    */
    public InsPlan copy() throws Exception {
        return (InsPlan)this.MemberwiseClone();
    }

}


