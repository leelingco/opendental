//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EmailTemplate;
import OpenDentBusiness.TableBase;

/**
* A template email which can be used as the basis for a new email.
*/
public class EmailTemplate  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EmailTemplateNum = new long();
    /**
    * Default subject line.
    */
    public String Subject = new String();
    /**
    * Body of the email
    */
    public String BodyText = new String();
    /**
    * Returns a copy of this EmailTemplate.
    */
    public EmailTemplate copy() throws Exception {
        return (EmailTemplate)this.MemberwiseClone();
    }

}


