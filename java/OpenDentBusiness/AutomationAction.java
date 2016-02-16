//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;


public enum AutomationAction
{
    //<summary>Either a single statement or as part of the billing process.  Either print or </summary>
    //CreateStatement
    /**
    * 
    */
    PrintPatientLetter,
    /**
    * 
    */
    CreateCommlog,
    /**
    * If a referral does not exist for this patient, then notify user instead.
    */
    PrintReferralLetter,
    /**
    * 
    */
    ShowExamSheet,
    /**
    * 
    */
    PopUp
}

//<summary></summary>
//AddStatementNoteBold