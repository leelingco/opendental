//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.QuestionDef;
import OpenDentBusiness.QuestionType;
import OpenDentBusiness.TableBase;

/**
* Each row represents one question on the medical history questionnaire.  Later, other questionnaires will be allowed, but for now, all questions are on one questionnaire for the patient.  This table has no dependencies, since the question is copied when added to a patient record.  Any row can be freely deleted or altered without any problems.
*/
public class QuestionDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long QuestionDefNum = new long();
    /**
    * The question as presented to the patient.
    */
    public String Description = new String();
    /**
    * The order that the Questions will show.
    */
    public int ItemOrder = new int();
    /**
    * Enum:QuestionType
    */
    public QuestionType QuestType = QuestionType.FreeformText;
    /**
    * 
    */
    public QuestionDef copy() throws Exception {
        QuestionDef q = new QuestionDef();
        q.QuestionDefNum = QuestionDefNum;
        q.Description = Description;
        q.ItemOrder = ItemOrder;
        q.QuestType = QuestType;
        return q;
    }

}


