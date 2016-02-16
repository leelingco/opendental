//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;


public enum PhoneEmpStatusOverride
{
    /**
    * 0 - None.
    */
    None,
    /**
    * 1
    */
    Unavailable,
    /**
    * 2
    */
    OfflineAssist
}

/*CREATE TABLE phoneempdefault (  
		EmployeeNum BIGINT NOT NULL,      
		IsGraphed TINYINT NOT NULL,      
		HasColor TINYINT NOT NULL,      
		RingGroups INT NOT NULL,      
		EmpName VARCHAR(255) NOT NULL,      
		PRIMARY KEY (EmployeeNum)      
		) DEFAULT CHARSET=utf8; */