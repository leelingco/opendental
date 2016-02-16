//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;

import OpenDental.SmartCards.ISmartCardManager;
import OpenDentBusiness.Patient;

public abstract class SmartCardService   
{
    public SmartCardService(ISmartCardManager manager) throws Exception {
        this.supportedAtrs = new Collection<byte[]>();
        this.manager = manager;
    }

    private ISmartCardManager manager;
    private Collection<byte[]> supportedAtrs = new Collection<byte[]>();
    protected Collection<byte[]> getSupportedAtrs() throws Exception {
        return supportedAtrs;
    }

    public boolean isSupported(byte[] atr) throws Exception {
        // Assume a valid ATR is not found.
        boolean retValue = false;
        // Check incoming data.
        if (atr != null)
        {
            for (Object __dummyForeachVar0 : supportedAtrs)
            {
                // Loop over the supported ATRs.
                byte[] currentAtr = (byte[])__dummyForeachVar0;
                // Always check the length.
                if (currentAtr.Length == atr.Length)
                {
                    for (int i = 0;i < atr.Length;i++)
                    {
                        // Loop over the array to compare the bytes.
                        retValue = (atr[i] == currentAtr[i]);
                        if (!retValue)
                        {
                            break;
                        }
                         
                    }
                }
                 
                // We have a mismatch, break out.
                if (retValue)
                {
                    break;
                }
                 
            }
        }
         
        return retValue;
    }

    // We have a supported ATR, break out.
    public abstract Patient getPatientInfo(String reader) throws Exception ;

}


