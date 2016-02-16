//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:13 PM
//

package KnowledgeRequestNotification;

import CS2JNet.System.LCC.Disposable;

public class Value   
{
    /**
    * The actual code snomed, icd-9, or incd10, etc code. Example: 95521008
    */
    public String code = new String();
    /**
    * The HL7-OID of the code system used. Example: 2.16.840.1.113883.6.96 if using SNOMEDCT
    */
    public String codeSystem = new String();
    /**
    * The human readable name of the code system used. Example: SNOMEDCT
    */
    public String codeSystemName = new String();
    /**
    * The human readable name of the code.  Example: "Abnormal jaw movement (disorder)"
    */
    public String displayName = new String();
    public Value(String c, String cs, String csn, String dn) throws Exception {
        code = c;
        codeSystem = cs;
        codeSystemName = csn;
        displayName = dn;
    }

    public Value() throws Exception {
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
                w.WriteStartElement("value");
                w.WriteAttributeString("code", code);
                w.WriteAttributeString("codeSystem", codeSystem);
                w.WriteAttributeString("codeSystemName", codeSystemName);
                w.WriteAttributeString("displayName", displayName);
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


//code