//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;


public class EhrCqmEncounter   
{
    public long EhrCqmEncounterNum = new long();
    public long PatNum = new long();
    public long ProvNum = new long();
    public String CodeValue = new String();
    public String CodeSystemName = new String();
    public String CodeSystemOID = new String();
    public String Description = new String();
    public String ValueSetName = new String();
    public String ValueSetOID = new String();
    public DateTime DateEncounter = new DateTime();
    //these fields are used by measure 68, meds documented, which is encounter based, not patient based
    public boolean IsNumerator = new boolean();
    public boolean IsException = new boolean();
    public String Explanation = new String();
}


