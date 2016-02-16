//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards.Belgium;

import OpenDental.SmartCards.Belgium.ErrorCodeOptions;

public class Status   
{
    public Status() {
    }

    /**
    * Db Error Code
    */
    public ErrorCodeOptions General = ErrorCodeOptions.OK;
    /**
    * System Error Code
    */
    public int System = new int();
    /**
    * PC/SC Error Code
    */
    public int PCSC = new int();
    /**
    * Card Status Word
    */
    public short CSW = new short();
    /**
    * Reserved for future use
    */
    public short RFU1 = new short();
    public short RFU2 = new short();
    public short RFU3 = new short();
}


