//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:13 PM
//

package KnowledgeRequestNotification;

import CS2JNet.System.LCC.Disposable;
import KnowledgeRequestNotification.MainSearchCriteria;
import KnowledgeRequestNotification.Subject3;
import KnowledgeRequestNotification.Value;

/**
* Mostly just a main search criteria.
*/
public class Subject3   
{
    public String typeCode = new String();
    public MainSearchCriteria mainSearchCriteria;
    public Subject3() throws Exception {
        typeCode = "SUBJ";
        mainSearchCriteria = new MainSearchCriteria();
    }

    public Subject3(Value value) throws Exception {
        typeCode = "SUBJ";
        mainSearchCriteria = new MainSearchCriteria(value);
    }

    public static String toXml(List<Subject3> subject4List) throws Exception {
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
                for (int i = 0;i < subject4List.Count;i++)
                {
                    w.WriteStartElement("subject4");
                    w.WriteAttributeString("typeCode", subject4List[i].typeCode);
                    w.WriteRaw(subject4List[i].mainSearchCriteria.ToXml());
                    w.WriteEndElement();
                }
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


//end subject4List
//end using