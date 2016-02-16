//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.TableBase;

/**
* One printer selection for one situation for one computer.
*/
public class Printer  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PrinterNum = new long();
    /**
    * FK to computer.ComputerNum.  This will be changed some day to refer to the computername, because it would make more sense as a key than a cryptic number.
    */
    public long ComputerNum = new long();
    /**
    * Enum:PrintSituation One of about 10 different situations where printing takes place.  If no printer object exists for a situation, then a default is used and a prompt is displayed.
    */
    public PrintSituation PrintSit = PrintSituation.Default;
    /**
    * The name of the printer as set from the specified computer.
    */
    public String PrinterName = new String();
    /**
    * If true, then user will be prompted for printer.  Otherwise, print directly with little user interaction.
    */
    public boolean DisplayPrompt = new boolean();
}


/*//<summary>Returns a copy of the clearinghouse.</summary>
    public ClaimForm Clone(){
			ClaimForm cf=new ClaimForm();
			cf.ClaimFormNum=ClaimFormNum;
			cf.Description=Description;
			return cf;
		}*/