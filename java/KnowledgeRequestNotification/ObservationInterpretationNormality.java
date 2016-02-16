//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:13 PM
//

package KnowledgeRequestNotification;

import CS2JNet.System.LCC.Disposable;
import OpenDentBusiness.LabAbnormalFlag;

/**
* Normality, Abnormality, Alert. Concepts in this category are mutually exclusive, i.e., at most one is allowed. Enum generated from HL7 _ObservationInterpretationNormality [2.16.840.1.113883.1.11.10206] which is a subset of ObservationInterpretation [OID=2.16.840.1.113883.5.83] documentation published 20120831 10:21 AM.
*/
public class ObservationInterpretationNormality   
{
    public String code = new String();
    public static String codeSystem = new String();
    public static String codeSystemName = new String();
    public String displayName = new String();
    public ObservationInterpretationNormality() throws Exception {
        code = "";
        codeSystem = "2.16.840.1.113883.5.83";
        codeSystemName = "ObservationInterpretation";
        displayName = "";
    }

    public void setInterpretation(LabAbnormalFlag laf) throws Exception {
        switch(laf)
        {
            case Above: 
                setInterpretation("H");
                return ;
            case Below: 
                setInterpretation("L");
                return ;
            case Normal: 
                setInterpretation("N");
                return ;
            case None: 
            default: 
                setInterpretation("");
                return ;
        
        }
    }

    /**
    * Set InterpretaionCodes Normal, Abnormal, High, Low, Alert.
    *  @param code Allowed values: A, AA, HH, LL, H, L, N.
    */
    public void setInterpretation(String interpretationCode) throws Exception {
        if (interpretationCode == null)
        {
            code = "";
            displayName = "";
            return ;
        }
         
        System.String __dummyScrutVar1 = interpretationCode;
        if (__dummyScrutVar1.equals("A"))
        {
            /**
            * 0 - Abnormal - Abnormal (for nominal observations, all service types)
            */
            displayName = "Abnormal";
        }
        else if (__dummyScrutVar1.equals("AA"))
        {
            /**
            * 1 - Abnormal alert - Abnormal alert (for nominal observations and all service types)
            */
            displayName = "Abnormal alert";
        }
        else if (__dummyScrutVar1.equals("HH"))
        {
            /**
            * 2 - High alert - Above upper alert threshold (for quantitative observations)
            */
            displayName = "High alert";
        }
        else if (__dummyScrutVar1.equals("LL"))
        {
            /**
            * 3 - Low alert - Below lower alert threshold (for quantitative observations)
            */
            displayName = "Low alert";
        }
        else if (__dummyScrutVar1.equals("H"))
        {
            /**
            * 4 - High - Above high normal (for quantitative observations)
            */
            displayName = "High";
        }
        else if (__dummyScrutVar1.equals("L"))
        {
            /**
            * 5 - Low - Below low normal (for quantitative observations)
            */
            displayName = "Low";
        }
        else if (__dummyScrutVar1.equals("N"))
        {
            /**
            * 6 - Normal - Normal (for all service types)
            */
            displayName = "Normal";
        }
        else
        {
            code = "";
            displayName = "";
            return ;
        }       
        code = interpretationCode;
    }

}


