//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:27 PM
//

package OpenDental;


public class MagstripCardParseException  extends Exception 
{
    public MagstripCardParseException(Exception cause) throws Exception {
        super(cause.Message, cause);
    }

    public MagstripCardParseException(String msg) throws Exception {
        super(msg);
    }

    public MagstripCardParseException(String msg, Exception cause) throws Exception {
        super(msg, cause);
    }

}


