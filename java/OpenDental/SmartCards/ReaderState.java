//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;

import OpenDental.SmartCards.CardState;

public class ReaderState   
{
    public ReaderState() {
    }

    public String Reader = new String();
    public IntPtr UserData = new IntPtr();
    public CardState CurrentState = CardState.Unaware;
    public CardState EventState = CardState.Unaware;
    public uint cbAtr = new uint();
    public byte[] rgbAtr = new byte[]();
}


