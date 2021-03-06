//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import OpenDentBusiness.Claim;
import OpenDentBusiness.Clearinghouse;

/**
* Template class for foreign programmers who want to write code to print claims and send eclaims.  If a foreign programmer wants to develop claim printing and sending, then make a copy of this class and name it the same as the country and/or software it is intended for.  Add a call to the new PrintPage() function from FormClaimPrint.PrintImmediate() and FormClaimPrint.FormClaimPrint_Load().  Add an item to the EclaimsCommBridge enumeration for the new class and add a call to the new Launch() function from Eclaims.SendBatch().
*/
public class aTemplateForeignClaims   
{
    public Claim claimToPrint;
    /**
    * Called when a user previews or prints a claim from anywhere in the program.
    * Called for each page of the claim form which needs to be printed.  Set ev.HasMorePages to false on last page to end printing.
    * Read the background claim form image from the OpenDentImages folder.  The claim to be printed is within the claimToPrint variable.
    */
    private void printPage(Object sender, PrintPageEventArgs ev) throws Exception {
    }

    //TODO: Implement.
    /**
    * Returns true if the communications were successful, and false if they failed.  If they failed, a rollback will happen automatically by deleting the previously created X12 file.  The batchNum is supplied for the possible rollback.  If batchNum is 0, then this function can either choose to retreive claim adjudication reports or to do nothing.
    */
    public static boolean launch(Clearinghouse clearhouse, int batchNum) throws Exception {
        return true;
    }

}


//TODO: Implement.