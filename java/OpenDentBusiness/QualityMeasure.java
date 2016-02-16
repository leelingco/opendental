//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EhrCqmEncounter;
import OpenDentBusiness.EhrCqmIntervention;
import OpenDentBusiness.EhrCqmMeasEvent;
import OpenDentBusiness.EhrCqmMedicationPat;
import OpenDentBusiness.EhrCqmNotPerf;
import OpenDentBusiness.EhrCqmPatient;
import OpenDentBusiness.EhrCqmProblem;
import OpenDentBusiness.EhrCqmProc;
import OpenDentBusiness.EhrCqmVitalsign;
import OpenDentBusiness.QualityMeasure;
import OpenDentBusiness.QualityType;
import OpenDentBusiness.QualityType2014;

/**
* Used by ehr.
*/
public class QualityMeasure   
{
    public QualityType Type = QualityType.WeightOver65;
    public QualityType2014 Type2014 = QualityType2014.MedicationsEntered;
    public String Id = new String();
    public String Descript = new String();
    public int Denominator = new int();
    public int Numerator = new int();
    public int Exclusions = new int();
    public int Exceptions = new int();
    /**
    * Denominator-Exceptions-Exclusions-Numerator.  Those that do not fall into a sub-population.
    */
    public int NotMet = new int();
    /**
    * This represents the percentage of patients in the denominator who fall into one of the other sub-populations.  The Reporting Rate is calculated as: Rate=(Numerator+Exclusions+Exceptions)/Denominator. See \\SERVERFILES\storage\EHR\Quality Measures\QRDA\CDAR2_QRDA_CATIII_DSTU_R1_2012NOV\CDAR2_QRDAIII_DSTU_R1_2012NOV.pdf page 86.
    */
    public double ReportingRate = new double();
    /**
    * The performance rate is a ratio of patients that meet the numerator criteria divided by patients in the denominator (after accounting for exclusions and exceptions).  Rate = Numerator/(Denominator-Exclusions-Exceptions).
    */
    public double PerformanceRate = new double();
    public String DenominatorExplain = new String();
    public String NumeratorExplain = new String();
    public String ExclusionsExplain = new String();
    public String ExceptionsExplain = new String();
    public String eMeasureTitle = new String();
    public String eMeasureVNeutralId = new String();
    public String eMeasureVSpecificId = new String();
    public String eMeasureVersion = new String();
    public String eMeasureNum = new String();
    public String eMeasureSetId = new String();
    public String eMeasureDenomId = new String();
    public String eMeasureNumerId = new String();
    public String eMeasureDenexId = new String();
    public String eMeasureDenexcepId = new String();
    public String eMeasureIppId = new String();
    public List<EhrCqmPatient> ListEhrPats = new List<EhrCqmPatient>();
    public Dictionary<long, List<EhrCqmEncounter>> DictPatNumListEncounters = new Dictionary<long, List<EhrCqmEncounter>>();
    public Dictionary<long, List<EhrCqmProc>> DictPatNumListProcs = new Dictionary<long, List<EhrCqmProc>>();
    public Dictionary<long, List<EhrCqmIntervention>> DictPatNumListInterventions = new Dictionary<long, List<EhrCqmIntervention>>();
    public Dictionary<long, List<EhrCqmMeasEvent>> DictPatNumListMeasureEvents = new Dictionary<long, List<EhrCqmMeasEvent>>();
    public Dictionary<long, List<EhrCqmMedicationPat>> DictPatNumListMedPats = new Dictionary<long, List<EhrCqmMedicationPat>>();
    public Dictionary<long, List<EhrCqmNotPerf>> DictPatNumListNotPerfs = new Dictionary<long, List<EhrCqmNotPerf>>();
    public Dictionary<long, List<EhrCqmProblem>> DictPatNumListProblems = new Dictionary<long, List<EhrCqmProblem>>();
    public Dictionary<long, List<EhrCqmVitalsign>> DictPatNumListVitalsigns = new Dictionary<long, List<EhrCqmVitalsign>>();
    /**
    * 
    */
    public QualityMeasure copy() throws Exception {
        QualityMeasure qualityMeasure = (QualityMeasure)this.MemberwiseClone();
        qualityMeasure.ListEhrPats = new List<EhrCqmPatient>();
        for (int i = 0;i < ListEhrPats.Count;i++)
        {
            qualityMeasure.ListEhrPats.Add(ListEhrPats[i].Copy());
        }
        return qualityMeasure;
    }

    public QualityMeasure() throws Exception {
        ListEhrPats = new List<EhrCqmPatient>();
        DictPatNumListEncounters = new Dictionary<long, List<EhrCqmEncounter>>();
        DictPatNumListProcs = new Dictionary<long, List<EhrCqmProc>>();
        DictPatNumListInterventions = new Dictionary<long, List<EhrCqmIntervention>>();
        DictPatNumListMeasureEvents = new Dictionary<long, List<EhrCqmMeasEvent>>();
        DictPatNumListMedPats = new Dictionary<long, List<EhrCqmMedicationPat>>();
        DictPatNumListNotPerfs = new Dictionary<long, List<EhrCqmNotPerf>>();
        DictPatNumListProblems = new Dictionary<long, List<EhrCqmProblem>>();
        DictPatNumListVitalsigns = new Dictionary<long, List<EhrCqmVitalsign>>();
    }

}


