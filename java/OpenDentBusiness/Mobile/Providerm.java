//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.Providerm;
import OpenDentBusiness.TableBase;

/**
* One username/password for one customer.
*/
public class Providerm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long ProvNum = new long();
    /**
    * Abbreviation.
    */
    public String Abbr = new String();
    /**
    * True if hygienist.
    */
    public boolean IsSecondary = new boolean();
    /**
    * Color that shows in appointments
    */
    public Color ProvColor = new Color();
    /**
    * 
    */
    public Providerm copy() throws Exception {
        return (Providerm)this.MemberwiseClone();
    }

    /**
    * Used only for serialization purposes
    */
    public int getProvColorXml() throws Exception {
        return ProvColor.ToArgb();
    }

    public void setProvColorXml(int value) throws Exception {
        ProvColor = Color.FromArgb(value);
    }

}


