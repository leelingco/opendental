//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.TableBase;

/**
* This table is not part of the general release.  User would have to add it manually.
*/
public class PhoneMetric  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PhoneMetricNum = new long();
    /**
    * 
    */
    public DateTime DateTimeEntry = new DateTime();
    /**
    * Smallint -32768 to 32767. -1 means was unable to reach the server.
    */
    public int VoiceMails = new int();
    /**
    * Smallint -32768 to 32767
    */
    public int Triages = new int();
    /**
    * Smallint -32768 to 32767
    */
    public int MinutesBehind = new int();
    /**
    * 
    */
    public PhoneEmpDefault clone() {
        try
        {
            return (PhoneEmpDefault)this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


/*
						CREATE TABLE phonemetric (
						PhoneMetricNum bigint NOT NULL auto_increment PRIMARY KEY,
						DateTimeEntry datetime NOT NULL,
						VoiceMails smallint NOT NULL,
						Triages smallint NOT NULL,
						MinutesBehind smallint NOT NULL
						) DEFAULT CHARSET=utf8;
*/