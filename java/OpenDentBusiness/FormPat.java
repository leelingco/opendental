//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.FormPat;
import OpenDentBusiness.Question;
import OpenDentBusiness.TableBase;

/**
* One form or questionnaire filled out by a patient.  Each patient can have multiple forms.
*/
public class FormPat  extends TableBase 
{
    /**
    * Primary key.
    */
    public long FormPatNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * The date and time that this questionnaire was filled out.
    */
    public DateTime FormDateTime = new DateTime();
    /**
    * Not a database field.
    */
    public List<Question> QuestionList = new List<Question>();
    /**
    * Constructor
    */
    public FormPat() throws Exception {
        QuestionList = new List<Question>();
    }

    /**
    * 
    */
    public FormPat copy() throws Exception {
        FormPat f = new FormPat();
        f = (FormPat)this.MemberwiseClone();
        f.QuestionList = new List<Question>(QuestionList);
        return f;
    }

}


