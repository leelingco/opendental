//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:13 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;

public enum ActTaskCode
{
    /**
    * Only enumerating the highest level task codes, OE, PATDOC, and PATINFO., Enum generated from HL7 ActTaskCode [2.16.840.1.113883.1.11.19846] which is a subset of ActCode [OID=2.16.840.1.113883.5.4] documentation published 20120831 10:21 AM.0 - order entry task
    */
    OE,
    /**
    * //1 - laboratory test order entry task
    */
    //LABOE,
    /**
    * //2 - medication order entry task
    */
    //MEDOE,
    /**
    * 1 - patient documentation task
    */
    PATDOC,
    /**
    * //4 - allergy list review
    */
    //ALLERLREV,
    /**
    * //5 - clinical note entry task
    */
    //CLINNOTEE,
    /**
    * //6 - diagnosis list entry task
    */
    //DIAGLISTE,
    /**
    * //7 - discharge summary entry task
    */
    //DISCHSUME,
    /**
    * //8 - pathology report entry task
    */
    //PATREPE,
    /**
    * //9 - problem list entry task
    */
    //PROBLISTE,
    /**
    * //10 - radiology report entry task
    */
    //RADREPE,
    /**
    * //11 - immunization list review
    */
    //IMMLREV,
    /**
    * //12 - reminder list review
    */
    //REMLREV,
    /**
    * //13 - wellness reminder list review
    */
    //WELLREMLREV,
    /**
    * 2 - patient information review task
    */
    PATINFO
}

