//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:13 PM
//

package KnowledgeRequestNotification;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import KnowledgeRequestNotification.Component1;
import KnowledgeRequestNotification.Id;
import KnowledgeRequestNotification.Subject;
import KnowledgeRequestNotification.Subject1;
import KnowledgeRequestNotification.Subject2;
import KnowledgeRequestNotification.Subject3;
import KnowledgeRequestNotification.Value;
import OpenDentBusiness.EhrLabResult;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.RxNorm;
import OpenDentBusiness.Snomed;

/**
* This class represents the root of the KnowledgeRequestNotificatio.
*/
public class KnowledgeRequestNotification   
{
    /**
    * Classification code.  Static field "ACT".  A record of something that is being done, has been done, can be done, or is intended or requested to be done.  Cardinality [1..1]
    */
    public static String classCode = "ACT";
    //1..1
    /**
    * Static field "DEF".  A definition of a service (master).  Cardinality [1..1]
    */
    public static String moodCode = "DEF";
    //1..1
    /**
    * List of globally unique identifiers of this knowledge request.  Cardinality [0..*]
    */
    public List<Id> IdList = new List<Id>();
    //0..*
    /**
    * Creation time of the knowledge request.  Must be formatted "yyyyMMddhhmmss" when used.  Cardinality [0..1]
    */
    public DateTime effectiveTime = new DateTime();
    //0..1 "yyyyMMddhhmmss"
    /**
    * Patient context information. Cardinality [0..1]
    */
    public Subject subject1;
    //0..1
    /**
    * Not fully implemented. Implemented enough to work.
    */
    public boolean performerIsPatient = new boolean();
    /**
    * Not fully implemented. Implemented enough to work.
    */
    public boolean recipientIsPatient = new boolean();
    //public Performer performer;
    //public InformationRecipient informationRecipient;
    /**
    * Task context information. Cardinality [0..1]
    */
    public Subject1 subject2;
    //0..1
    /**
    * Sub-topic information. Cardinality [0..1]
    */
    public Subject2 subject3;
    //0..1
    /**
    * Conatins a list of MainSearchCriteria: represents the main subject of interest in a knowledge request (e.g., a medication, a lab test result, a disease in the patient's problem list). When multiple multiple search criteria are present, knowledge resources MAY determine whether to join the multiple instances using the AND vs. OR Boolean operator. Cardinality[1..*]
    */
    public List<Subject3> subject4List = new List<Subject3>();
    //1..*
    /**
    * Contains encounter information, type and location.  Cardinality[0..1]
    */
    public Component1 componentOf;
    //0..1
    public KnowledgeRequestNotification() throws Exception {
        classCode = "ACT";
        moodCode = "DEF";
        IdList = new List<Id>();
        effectiveTime = DateTime.Now;
        subject1 = new Subject();
        subject2 = new Subject1();
        subject3 = new Subject2();
        subject4List = new List<Subject3>();
        componentOf = new Component1();
    }

    public void addObject(Object obj) throws Exception {
        Name __dummyScrutVar0 = obj.GetType().Name;
        if (__dummyScrutVar0.equals("Snomed"))
        {
            addCode((Snomed)obj);
        }
        else if (__dummyScrutVar0.equals("ICD9"))
        {
            addCode((ICD9)obj);
        }
        else if (__dummyScrutVar0.equals("Icd10"))
        {
            addCode((Icd10)obj);
        }
        else if (__dummyScrutVar0.equals("RxNorm"))
        {
            addCode((RxNorm)obj);
        }
        else if (__dummyScrutVar0.equals("Loinc"))
        {
            addCode((Loinc)obj);
        }
        else //case "LabResult"://Deprecated
        //	AddLabResult((LabResult)obj);
        //	break;
        if (__dummyScrutVar0.equals("EhrLabResult"))
        {
            addLabResult((EhrLabResult)obj);
        }
              
    }

    public void addCode(Snomed snomed) throws Exception {
        subject4List.Add(new Subject3(new Value(snomed.SnomedCode,"2.16.840.1.113883.6.96","SNOMEDCT",snomed.Description)));
        subject4List[subject4List.Count - 1].mainSearchCriteria.originalText = snomed.Description;
    }

    public void addCode(ICD9 icd9) throws Exception {
        subject4List.Add(new Subject3(new Value(icd9.ICD9Code,"2.16.840.1.113883.6.103","ICD9CM",icd9.Description)));
        subject4List[subject4List.Count - 1].mainSearchCriteria.originalText = icd9.Description;
    }

    public void addCode(Icd10 icd10) throws Exception {
        subject4List.Add(new Subject3(new Value(icd10.Icd10Code,"2.16.840.1.113883.6.90","ICD10CM",icd10.Description)));
        subject4List[subject4List.Count - 1].mainSearchCriteria.originalText = icd10.Description;
    }

    public void addCode(RxNorm rxNorm) throws Exception {
        subject4List.Add(new Subject3(new Value(rxNorm.RxCui,"2.16.840.1.113883.6.88","RxNorm",rxNorm.Description)));
        subject4List[subject4List.Count - 1].mainSearchCriteria.originalText = rxNorm.Description;
    }

    public void addCode(Loinc loinc) throws Exception {
        //TODO: lab values? no, add LabResult Instead
        subject4List.Add(new Subject3(new Value(loinc.LoincCode,"2.16.840.1.113883.6.1","LOINC",loinc.NameShort)));
        subject4List[subject4List.Count - 1].mainSearchCriteria.originalText = loinc.NameShort;
    }

    //public void AddLabResult(LabResult labResult) {
    //	if(labResult.TestID==null || labResult.TestID=="") {
    //		return;
    //	}
    //	Loinc loinc=Loincs.GetByCode(labResult.TestID);
    //	subject4List.Add(new Subject3(new Value(loinc.LoincCode,"2.16.840.1.113883.6.1","LOINC",loinc.NameShort)));
    //	subject4List[subject4List.Count-1].mainSearchCriteria.originalText=loinc.NameShort;
    //	subject4List[subject4List.Count-1].mainSearchCriteria.subject.severityObservation.observationInterpretationNormality.SetInterpretation(labResult.AbnormalFlag);
    //}
    public void addLabResult(EhrLabResult ehrLabResult) throws Exception {
        if (!StringSupport.equals(ehrLabResult.ObservationIdentifierID, ""))
        {
            //1st triplet
            subject4List.Add(new Subject3(new Value(ehrLabResult.ObservationIdentifierID,"2.16.840.1.113883.6.1","LOINC",ehrLabResult.ObservationIdentifierText)));
            subject4List[subject4List.Count - 1].mainSearchCriteria.originalText = ehrLabResult.ObservationIdentifierTextOriginal;
        }
         
        if (!StringSupport.equals(ehrLabResult.ObservationIdentifierIDAlt, ""))
        {
            //2nd triplet
            subject4List.Add(new Subject3(new Value(ehrLabResult.ObservationIdentifierIDAlt,"2.16.840.1.113883.6.1","LOINC",ehrLabResult.ObservationIdentifierTextAlt)));
            subject4List[subject4List.Count - 1].mainSearchCriteria.originalText = ehrLabResult.ObservationIdentifierTextOriginal;
        }
         
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
                w.WriteRaw("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
                w.WriteWhitespace("\r\n");
                //TODO:Implement more fields, this is just
                w.WriteStartElement("knowledgeRequestNotification");
                w.WriteAttributeString("classCode", "ACT");
                w.WriteAttributeString("moodCode", "DEF");
                //id
                //effectiveTime
                //subject1
                //holder
                //performer
                //informationRecipient
                //subject2
                //subject3
                w.WriteRaw(Subject3.toXml(subject4List));
                //componentOf
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

    //knowledgeRequestNotification
    public String toUrl() throws Exception {
        StringBuilder strB = new StringBuilder();
        strB.Append((effectiveTime.Year > 1880 ? "knowledgeRequestNotification.effectiveTime.v=" + effectiveTime.ToString("yyyyMMddhhmmss") + "&" : ""));
        for (int i = 0;i < subject4List.Count;i++)
        {
            //holder
            //assignedEntity
            //patientPerson
            //age
            //ageGroup
            //taskContext
            //subTopic
            strB.Append("mainSearchCriteria.v.c" + (i == 0 ? "" : "" + i) + "=" + subject4List[i].mainSearchCriteria.value.code + "&");
            strB.Append("mainSearchCriteria.v.cs" + (i == 0 ? "" : "" + i) + "=" + subject4List[i].mainSearchCriteria.value.codeSystem + "&");
            strB.Append("mainSearchCriteria.v.dn" + (i == 0 ? "" : "" + i) + "=" + subject4List[i].mainSearchCriteria.value.displayName + "&");
            if (subject4List[i].mainSearchCriteria.originalText != subject4List[i].mainSearchCriteria.value.displayName)
            {
                //original text only if different than display name.
                strB.Append("mainSearchCriteria.v.ot" + (i == 0 ? "" : "" + i) + "=" + subject4List[i].mainSearchCriteria.originalText + "&");
            }
             
        }
        //severityObservation
        //informationRecipient
        strB.Append("informationRecipient=" + (recipientIsPatient ? "PAT&" : "PROV&"));
        //performer
        strB.Append("performer=" + (performerIsPatient ? "PAT&" : "PROV&"));
        return strB.ToString().Replace(" ", "%20");
    }

    //encounter
    //serviceDeliveryLocation
    public String toUrl(String xml) throws Exception {
        StringBuilder strBuilder = new StringBuilder();
        return strBuilder.ToString();
    }

}


//TODO later, maybe
//XmlDocument doc=new XmlDocument();
//doc.LoadXml(xml);
//XmlNode node=doc.SelectSingleNode("//Error");
//if(node!=null) {
//	throw new Exception(node.InnerText);
//}