//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;

import OpenDental.SmartCards.SmartCardState;

public class SmartCardStateChangedEventArgs  extends EventArgs 
{
    public SmartCardStateChangedEventArgs(String reader, SmartCardState state, byte[] atr) throws Exception {
        this.reader = reader;
        this.state = state;
        this.atr = atr;
    }

    private String reader = new String();
    public String getReader() throws Exception {
        return reader;
    }

    private SmartCardState state = SmartCardState.None;
    public SmartCardState getState() throws Exception {
        return state;
    }

    private byte[] atr = new byte[]();
    public byte[] getAtr() throws Exception {
        return atr;
    }

}


