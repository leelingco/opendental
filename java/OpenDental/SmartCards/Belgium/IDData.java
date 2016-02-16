//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards.Belgium;

import OpenDental.SmartCards.Belgium.BelgianIdentityCard;

public class IDData   
{
    public IDData() {
    }

    public short Version = new short();
    public char[] CardNumber = new char[]();
    public char[] ChipNumber = new char[]();
    public char[] ValidityDateBegin = new char[]();
    public char[] ValidityDateEnd = new char[]();
    public byte[] Municipality = new byte[]();
    public char[] NationalNumber = new char[]();
    public byte[] Name = new byte[]();
    public byte[] FirstName1 = new byte[]();
    public byte[] FirstName2 = new byte[]();
    public byte[] FirstName3 = new byte[]();
    public char[] Nationality = new char[]();
    public byte[] BirthLocation = new byte[]();
    public char[] BirthDate = new char[]();
    public char[] Sex = new char[]();
    public byte[] NobleCondition = new byte[]();
    public int DocumentType = new int();
    public boolean WhiteCane = new boolean();
    public boolean YellowCane = new boolean();
    public boolean ExtendedMinority = new boolean();
    public byte[] HashPhoto = new byte[]();
    public byte[] RFU = new byte[]();
}


