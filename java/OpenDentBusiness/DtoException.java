//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DataTransferObject;

//<summary>IDorRows will be the InsertID for insert type commands.  For some other commands, it will be the rows changed, and for some commands, it will just be 0.</summary>
//public class DtoServerAck:DataTransferObject {
//	public int IDorRows;
//}
/**
* OpenDentBusiness and all the DA classes are designed to throw an exception if something goes wrong.  If using OpenDentBusiness through the remote server, then the server catches the exception and passes it back to the main program using this DTO.  The client then turns it back into an exception so that it behaves just as if OpenDentBusiness was getting called locally.
*/
public class DtoException  extends DataTransferObject 
{
    public String Message = new String();
}


