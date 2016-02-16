//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:13 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;

public enum ObservationInterpretationNormality
{
    /**
    * Normality, Abnormality, Alert. Concepts in this category are mutually exclusive, i.e., at most one is allowed. Enum generated from HL7 _ObservationInterpretationNormality [2.16.840.1.113883.1.11.10206] which is a subset of ObservationInterpretation [OID=2.16.840.1.113883.5.83] documentation published 20120831 10:21 AM.0 - Abnormal - Abnormal (for nominal observations, all service types)
    */
    A,
    /**
    * 1 - Abnormal alert - Abnormal alert (for nominal observations and all service types)
    */
    AA,
    /**
    * 2 - High alert - Above upper alert threshold (for quantitative observations)
    */
    HH,
    /**
    * 3 - Low alert - Below lower alert threshold (for quantitative observations)
    */
    LL,
    /**
    * 4 - High - Above high normal (for quantitative observations)
    */
    H,
    /**
    * 5 - Low - Below low normal (for quantitative observations)
    */
    L,
    /**
    * 6 - Normal - Normal (for all service types)
    */
    N
}

