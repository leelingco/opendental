//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrMeasureEventType;

public class EhrCqmMeasEvent   
{
    public long EhrCqmMeasEventNum = new long();
    public EhrMeasureEventType EventType = EhrMeasureEventType.EducationProvided;
    public long PatNum = new long();
    public String CodeValue = new String();
    public String CodeSystemName = new String();
    public String CodeSystemOID = new String();
    public String Description = new String();
    public String ValueSetName = new String();
    public String ValueSetOID = new String();
    public DateTime DateTEvent = new DateTime();
}


