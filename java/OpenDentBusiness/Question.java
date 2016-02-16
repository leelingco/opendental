//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Question;
import OpenDentBusiness.TableBase;

/**
* Each row is one Question for one patient.  If a patient has never filled out a questionnaire, then they will have no rows in this table.
*/
public class Question  extends TableBase 
{
    /**
    * Primary key.
    */
    public long QuestionNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * The order that this question shows in the list.
    */
    public int ItemOrder = new int();
    /**
    * The original question.
    */
    public String Description = new String();
    /**
    * The answer to the question in text form.
    */
    public String Answer = new String();
    /**
    * FK to formpat.FormPatNum
    */
    public long FormPatNum = new long();
    /**
    * 
    */
    public Question copy() throws Exception {
        Question q = new Question();
        q.QuestionNum = QuestionNum;
        q.PatNum = PatNum;
        q.ItemOrder = ItemOrder;
        q.Description = Description;
        q.Answer = Answer;
        q.FormPatNum = FormPatNum;
        return q;
    }

}


