//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import OpenDental.Reporting.Allocators.MyAllocator1.ODTable;

public class Patient  extends ODTable 
{
    public final String PatNum = "PatNum";
    public final Type PatNumType = uint.class;
    public final String Guarantor = "Guarantor";
    public final Type GuarantorType = uint.class;
    public final String LName = "LName";
    public final Type LNameType = String.class;
    public final String FName = "FName";
    public final Type FNameType = String.class;
    //public readonly string  = "";
    //public readonly Type Type = typeof(string);
    public Patient() throws Exception {
        String[] cols;
        Type[] types1;
        //string tablename = "Patient";
        checkODTableSetup();
    }

}


