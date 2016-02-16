//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package OpenDental;


/**
* Zipcodes are also known as postal codes.  Zipcodes are always copied to patient records rather than linked.  So items in this list can be freely altered or deleted without harming patient data.
*/
public class ZipCode   
{
    public ZipCode() {
    }

    /**
    * Primary key.
    */
    public int ZipCodeNum = new int();
    /**
    * The actual zipcode.
    */
    public String ZipCodeDigits = new String();
    /**
    * .
    */
    public String City = new String();
    /**
    * .
    */
    public String State = new String();
    /**
    * If true, then it will show in the dropdown list in the patient edit window.
    */
    public boolean IsFrequent = new boolean();
}


