//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum TextSizeMySqlOracle
{
    /**
    * 255-4k, MySql: text, Oracle: varchar2
    */
    Small,
    /**
    * 4k-65k, MySql: text, Oracle: clob
    */
    Medium,
    /**
    * 65k+, MySql: mediumtext, Oracle: clob
    */
    Large
}

