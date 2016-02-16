//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:38 PM
//

package UnitTests;

import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;

public class CarrierT   
{
    public static Carrier createCarrier(String suffix) throws Exception {
        Carrier carrier = new Carrier();
        carrier.CarrierName = "Carrier" + suffix;
        Carriers.insert(carrier);
        return carrier;
    }

}


