//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;


public enum ClaimCorrectionType
{
    /**
    * 0 - X12 1. Use for claims that are not ongoing.
    */
    Original,
    /**
    * 1 - X12 7. Use to entirely replace an original claim. A claim reference number will be required.
    */
    Replacement,
    /**
    * 2 - X12 8. Use to undo an original claim. A claim reference number will be required.
    */
    Void
}

/**
* X12 2. Use for first claim in a series of expected claims.
*/
//FirstClaim,
/**
* X12 3. Use when one or more claims for the same ongoing care have already been submitted.
*/
//InterimContinuingClaim,
/**
* X12 4. Use for a claim which is the last of a series of claims.
*/
//InterimLastClaim,
/**
* X12 5.
*/
//LateCharge,
/**
* X12 6. Nobody seems to use this.  What is it?  Use to make a correction to the original claim. A claim reference number will be required.
*/
//Corrected,