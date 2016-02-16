//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:13 PM
//

package KnowledgeRequestNotification;

import CS2JNet.System.LCC.Disposable;
import KnowledgeRequestNotification.Code;
import KnowledgeRequestNotification.ObservationInterpretationNormality;

public class SeverityObservation   
{
    public static String classCode = new String();
    public static String moodCode = new String();
    public Code code;
    public ObservationInterpretationNormality observationInterpretationNormality;
    public SeverityObservation() throws Exception {
        classCode = "OBS";
        moodCode = "DEF";
        code = new Code("SeverityObservationType","2.16.840.1.113883.5.6","ActClass","SeverityObservationType");
        observationInterpretationNormality = null;
    }

}


