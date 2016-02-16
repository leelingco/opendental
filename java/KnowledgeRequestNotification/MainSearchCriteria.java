//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:13 PM
//

package KnowledgeRequestNotification;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import KnowledgeRequestNotification.Code;
import KnowledgeRequestNotification.Subject4;
import KnowledgeRequestNotification.Value;

public class MainSearchCriteria   
{
    /**
    * Static field "OBS".  Observation.  Cardinality [1..1]
    */
    public static String classCode = new String();
    /**
    * Static field "DEF".  A definition of a service (master).  Cardinality [1..1]
    */
    public static String moodCode = new String();
    /**
    * Static field.  This defines the value as being a knowledge subject.  Cardinality [1..1]
    */
    public Code code;
    /**
    * Contains information on the snomed in question, icd9, icd10 ... etc code.  The "value" of the "code".  Cardinality [1..1]
    */
    public Value value;
    /**
    * Represents the human readable representation of the code as displayed to the user in the CIS and SHOULD be used only if different than the displayName
    */
    public String originalText = new String();
    /**
    * Contains SeverityObservation:specifies the interpretation of a laboratory test result (e.g., 'high', 'low', 'abnormal', 'normal'). This class MAY be used to support implementations where the MainSearchCriteria consists of a laboratory test result. Supports questions such as "what are the causes of high serum potassium?
    */
    public Subject4 subject;
    public MainSearchCriteria() throws Exception {
        classCode = "OBS";
        moodCode = "DEF";
        code = new Code("KSUBJ","2.16.840.1.113883.6.96","SNOMEDCT","knowledge subject");
        value = new Value();
        subject = new Subject4();
    }

    public MainSearchCriteria(Value val) throws Exception {
        classCode = "OBS";
        moodCode = "DEF";
        code = new Code("KSUBJ","2.16.840.1.113883.6.96","SNOMEDCT","knowledge subject");
        value = val;
        subject = new Subject4();
    }

    public String toXml() throws Exception {
        XmlWriterSettings xmlSettings = new XmlWriterSettings();
        xmlSettings.Encoding = Encoding.UTF8;
        xmlSettings.OmitXmlDeclaration = true;
        xmlSettings.Indent = true;
        xmlSettings.IndentChars = "  ";
        StringBuilder strBuilder = new StringBuilder();
        XmlWriter w = XmlWriter.Create(strBuilder, xmlSettings);
        try
        {
            {
                w.WriteStartElement("mainSearchCriteria");
                w.WriteAttributeString("classCode", classCode);
                w.WriteAttributeString("moodCode", moodCode);
                w.WriteRaw(code.toXml());
                w.WriteRaw(value.toXml());
                if (!StringSupport.equals(originalText, "") && originalText != null && !StringSupport.equals(originalText, value.displayName))
                {
                    w.WriteStartElement("originalText");
                    w.WriteString(originalText);
                    w.WriteEndElement();
                }
                 
                //originalText
                w.WriteEndElement();
            }
        }
        finally
        {
            if (w != null)
                Disposable.mkDisposable(w).dispose();
             
        }
        return strBuilder.ToString();
    }

}


//mainSearchCriteria