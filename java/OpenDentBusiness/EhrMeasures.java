//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:42 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Allergies;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.EhrLab;
import OpenDentBusiness.EhrLabImages;
import OpenDentBusiness.EhrLabResult;
import OpenDentBusiness.EhrLabResults;
import OpenDentBusiness.EhrLabs;
import OpenDentBusiness.EhrMeasure;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.EhrMeasureType;
import OpenDentBusiness.EhrMu;
import OpenDentBusiness.FamilyHealth;
import OpenDentBusiness.FamilyHealths;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.MedicalOrderType;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Meth;
import OpenDentBusiness.MuMet;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientRaces;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PatRace;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcNote;
import OpenDentBusiness.ProcNotes;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RefAttach;
import OpenDentBusiness.RefAttaches;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.RxPat;
import OpenDentBusiness.RxPats;
import OpenDentBusiness.RxSendStatus;
import OpenDentBusiness.Vitalsign;
import OpenDentBusiness.Vitalsigns;

/**
* 
*/
public class EhrMeasures   
{
    /**
    * Positive/Negative Snomed indicators for lab results.
    */
    private static String _snomedLabResult = "\'393474000\',\'394424008\',\'10828004\',\'393476003\',\'394426005\',\'260385009\',\'61620004\',\'90213003\',\'260408008\',\'272069004\',\'4173002\',\'61707005\',\'272068007\',\'85696000\'";
    /**
    * Select All EHRMeasures from combination of db, static data, and complex calculations.
    */
    public static List<EhrMeasure> selectAll(DateTime dateStart, DateTime dateEnd, long provNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrMeasure>>GetObject(MethodBase.GetCurrentMethod(), dateStart, dateEnd, provNum);
        }
         
        String command = "SELECT * FROM ehrmeasure " + "WHERE MeasureType IN (" + POut.int(((Enum)EhrMeasureType.ProblemList).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.MedicationList).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.AllergyList).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Demographics).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Education).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.TimelyAccess).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ProvOrderEntry).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.CPOE_MedOrdersOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.CPOE_PreviouslyOrdered).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Rx).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSigns).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSigns2014).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSignsBMIOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSignsBPOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Smoking).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Lab).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ElectronicCopy).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ClinicalSummaries).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Reminders).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.MedReconcile).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.SummaryOfCare).ordinal()) + ", " + POut.int(((Enum)EhrMeasureType.VitalSigns2014).ordinal()) + ") " + "ORDER BY MeasureType";
        List<EhrMeasure> retVal = Crud.EhrMeasureCrud.SelectMany(command);
        Stopwatch s = new Stopwatch();
        for (int i = 0;i < retVal.Count;i++)
        {
            s.Restart();
            retVal[i].Objective = GetObjective(retVal[i].MeasureType);
            retVal[i].Measure = GetMeasure(retVal[i].MeasureType);
            retVal[i].PercentThreshold = GetThreshold(retVal[i].MeasureType);
            DataTable table = GetTable(retVal[i].MeasureType, dateStart, dateEnd, provNum);
            if (table == null)
            {
                retVal[i].Numerator = -1;
                retVal[i].Denominator = -1;
            }
            else
            {
                retVal[i].Numerator = calcNumerator(table);
                retVal[i].Denominator = table.Rows.Count;
            } 
            retVal[i].NumeratorExplain = GetNumeratorExplain(retVal[i].MeasureType);
            retVal[i].DenominatorExplain = GetDenominatorExplain(retVal[i].MeasureType);
            retVal[i].ExclusionExplain = GetExclusionExplain(retVal[i].MeasureType);
            retVal[i].ExclusionCount = GetExclusionCount(retVal[i].MeasureType, dateStart, dateEnd, provNum);
            retVal[i].ExclusionCountDescript = GetExclusionCountDescript(retVal[i].MeasureType);
            s.Stop();
            retVal[i].ElapsedTime = s.Elapsed;
        }
        return retVal;
    }

    /**
    * Select All EHRMeasures from combination of db, static data, and complex calculations.
    */
    public static List<EhrMeasure> selectAllMu2(DateTime dateStart, DateTime dateEnd, long provNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrMeasure>>GetObject(MethodBase.GetCurrentMethod(), dateStart, dateEnd, provNum);
        }
         
        List<EhrMeasure> retVal = getMU2List();
        Stopwatch s = new Stopwatch();
        for (int i = 0;i < retVal.Count;i++)
        {
            s.Restart();
            retVal[i].Objective = GetObjectiveMu2(retVal[i].MeasureType);
            retVal[i].Measure = GetMeasureMu2(retVal[i].MeasureType);
            retVal[i].PercentThreshold = GetThresholdMu2(retVal[i].MeasureType);
            DataTable table = GetTableMu2(retVal[i].MeasureType, dateStart, dateEnd, provNum);
            if (table == null)
            {
                retVal[i].Numerator = -1;
                retVal[i].Denominator = -1;
            }
            else
            {
                retVal[i].Numerator = calcNumerator(table);
                retVal[i].Denominator = table.Rows.Count;
            } 
            retVal[i].NumeratorExplain = GetNumeratorExplainMu2(retVal[i].MeasureType);
            retVal[i].DenominatorExplain = GetDenominatorExplainMu2(retVal[i].MeasureType);
            retVal[i].ExclusionExplain = GetExclusionExplainMu2(retVal[i].MeasureType);
            retVal[i].ExclusionCount = GetExclusionCountMu2(retVal[i].MeasureType, dateStart, dateEnd, provNum);
            retVal[i].ExclusionCountDescript = GetExclusionCountDescriptMu2(retVal[i].MeasureType);
            s.Stop();
            retVal[i].ElapsedTime = s.Elapsed;
        }
        return retVal;
    }

    /**
    * 
    */
    public static void update(EhrMeasure ehrMeasure) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrMeasure);
            return ;
        }
         
        Crud.EhrMeasureCrud.Update(ehrMeasure);
    }

    /**
    * Returns the Objective text based on the EHR certification documents.
    */
    private static String getObjective(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case ProblemList: 
                return "Maintain an up-to-date problem list of current and active diagnoses.";
            case MedicationList: 
                return "Maintain active medication list.";
            case AllergyList: 
                return "Maintain active medication allergy list";
            case Demographics: 
                return "Record demographics: Preferred language, Gender, Race, Ethnicity, Date of Birth";
            case Education: 
                return "Use certified EHR technology to identify patient-specific education resources and provide those resources to the patient if appropriate.";
            case TimelyAccess: 
                return "Provide patients with timely electronic access to their health information (including lab results, problem list, medication lists, medication allergies) within four business days of the information being available to the EP";
            case ProvOrderEntry: 
                return "Use CPOE for medication orders directly entered by any licensed healthcare professional who can enter orders into the medical record per state, local and professional guidelines.";
            case CPOE_MedOrdersOnly: 
                return "Use CPOE for medication orders directly entered by any licensed healthcare professional who can enter orders into the medical record per state, local and professional guidelines.";
            case CPOE_PreviouslyOrdered: 
                return "Use CPOE for medication orders directly entered by any licensed healthcare professional who can enter orders into the medical record per state, local and professional guidelines.";
            case Rx: 
                return "Generate and transmit permissible prescriptions electronically (eRx).";
            case VitalSigns: 
                return "Record and chart changes in vital signs: Height, Weight, Blood pressure for age 3 and over, Calculate and display BMI, Plot and display growth charts for children 2-20 years, including BMI";
            case VitalSigns2014: 
                return "Record and chart changes in vital signs: Height, Weight, Blood pressure for age 3 and over, Calculate and display BMI, Plot and display growth charts for children 2-20 years, including BMI";
            case VitalSignsBMIOnly: 
                return "Record and chart changes in vital signs: Height, Weight, Calculate and display BMI, Plot and display growth charts for children 2-20 years, including BMI";
            case VitalSignsBPOnly: 
                return "Record changes in blood pressure for age 3 and over";
            case Smoking: 
                return "Record smoking status for patients 13 years old or older.";
            case Lab: 
                return "Incorporate clinical lab-test results into certified EHR technology as structured data.";
            case ElectronicCopy: 
                return "Provide patients with an electronic copy of their health information (including diagnostic test results, problem list, medication lists, medication allergies), upon request.";
            case ClinicalSummaries: 
                return "Provide clinical summaries for patients for each office visit.";
            case Reminders: 
                return "Send reminders to patients per patient preference for preventive/ follow up care.";
            case MedReconcile: 
                return "The EP, eligible hospital or CAH who receives a patient from another setting of care or provider of care or believes an encounter is relevant should perform medication reconciliation.";
            case SummaryOfCare: 
                return "The EP, eligible hospital or CAH who transitions their patient to another setting of care or provider of care or refers their patient to another provider of care should provide summary of care record for each transition of care or referral.";
        
        }
        throw new ApplicationException("Type not found: " + mtype.ToString());
    }

    /**
    * Returns the Measures text based on the EHR certification documents.
    */
    private static String getMeasure(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case ProblemList: 
                return "More than 80% of all unique patients seen by the Provider have at least one entry or an indication that no problems are known for the patient recorded as structured data.";
            case MedicationList: 
                return "More than 80% of all unique patients seen by the Provider have at least one entry (or an indication that the patient is not currently prescribed any medication) recorded as structured data.";
            case AllergyList: 
                return "More than 80% of all unique patients seen by the Provider have at least one entry (or an indication that the patient has no known medication allergies) recorded as structured data.";
            case Demographics: 
                return "More than 50% of all unique patients seen by the Provider have demographics recorded as structured data.";
            case Education: 
                return "More than 10% of all unique patients seen by the Provider during the EHR reporting period are provided patient-specific education resources.";
            case TimelyAccess: 
                return "More than 10% of all unique patients seen by the Provider are provided timely (available to the patient within four business days of being updated in the certified EHR technology) electronic access to their health information subject to the Provider’s discretion to withhold certain information.";
            case ProvOrderEntry: 
                return "More than 30% of unique patients with at least one medication in their medication list seen by the Provider have at least one medication order entered using CPOE.";
            case CPOE_MedOrdersOnly: 
                return "More than 30% of medication orders created by the Provider during the reporting period are entered using CPOE.";
            case CPOE_PreviouslyOrdered: 
                return "More than 30% of unique patients with at least one medication in their medication list seen by the Provider for whom the Provider has previously ordered medication have at least one medication order entered using CPOE.";
            case Rx: 
                return "More than 40% of all permissible prescriptions written by the Provider are transmitted electronically using certified EHR technology.";
            case VitalSigns: 
                return "More than 50% of all unique patients (age 3 and over for blood pressure) seen by the Provider, height, weight and blood pressure are recorded as structured data.";
            case VitalSigns2014: 
                return "More than 50% of all unique patients (age 3 and over for blood pressure) seen by the Provider, height, weight and blood pressure are recorded as structured data.";
            case VitalSignsBMIOnly: 
                return "More than 50% of all unique patients seen by the Provider, height and weight are recorded as structured data.";
            case VitalSignsBPOnly: 
                return "More than 50% of all unique patients age 3 and over seen by the Provider have blood pressure recorded as structured data.";
            case Smoking: 
                return "More than 50% of all unique patients 13 years old or older seen by the Provider have smoking status recorded as structured data.";
            case Lab: 
                return "More than 40% of all clinical lab tests results ordered by the Provider during the EHR reporting period whose results are either in a positive/negative or numerical format are incorporated in certified EHR technology as structured data.";
            case ElectronicCopy: 
                return "More than 50% of patients who request an electronic copy of their health information are provided it within 3 business days";
            case ClinicalSummaries: 
                return "Clinical summaries provided to patients for more than 50% of all office visits within 3 business days.";
            case Reminders: 
                return "More than 20% of all unique patients 65 years or older or 5 years old or younger were sent an appropriate reminder during the EHR reporting period.";
            case MedReconcile: 
                return "The Provider performs medication reconciliation for more than 50% of transitions of care in which the patient is transitioned into the care of the Provider.";
            case SummaryOfCare: 
                return "The Provider who transitions or refers their patient to another setting of care or provider of care provides a summary of care record for more than 50% of transitions of care and referrals.";
        
        }
        throw new ApplicationException("Type not found: " + mtype.ToString());
    }

    //Leaving original wording so change will not require re-testing to meet 2011 certification.  The wording below may be used in 2014 MU 1 as it is more accurate.
    //return "More than 80% of all unique patients seen by the Provider have at least one problem entered with an ICD-9 code or SNOMED code attached or an indication that no problems are known for the patient recorded as structured data.";
    /**
    * Returns the Measures text based on the EHR certification documents.
    */
    private static int getThreshold(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case ProblemList: 
                return 80;
            case MedicationList: 
                return 80;
            case AllergyList: 
                return 80;
            case Demographics: 
                return 50;
            case Education: 
                return 10;
            case TimelyAccess: 
                return 10;
            case ProvOrderEntry: 
                return 30;
            case CPOE_MedOrdersOnly: 
                return 30;
            case CPOE_PreviouslyOrdered: 
                return 30;
            case Rx: 
                return 40;
            case VitalSigns: 
                return 50;
            case VitalSigns2014: 
                return 50;
            case VitalSignsBMIOnly: 
                return 50;
            case VitalSignsBPOnly: 
                return 50;
            case Smoking: 
                return 50;
            case Lab: 
                return 40;
            case ElectronicCopy: 
                return 50;
            case ClinicalSummaries: 
                return 50;
            case Reminders: 
                return 20;
            case MedReconcile: 
                return 50;
            case SummaryOfCare: 
                return 50;
        
        }
        throw new ApplicationException("Type not found: " + mtype.ToString());
    }

    public static DataTable getTable(EhrMeasureType mtype, DateTime dateStart, DateTime dateEnd, long provNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), mtype, dateStart, dateEnd, provNum);
        }
         
        String command = "";
        DataTable tableRaw = new DataTable();
        command = "SELECT GROUP_CONCAT(provider.ProvNum) FROM provider WHERE provider.EhrKey=" + "(SELECT pv.EhrKey FROM provider pv WHERE pv.ProvNum=" + POut.long(provNum) + ")";
        String provs = Db.getScalar(command);
        String[] tempProv = provs.Split(',');
        String provOID = "";
        for (int oi = 0;oi < tempProv.Length;oi++)
        {
            provOID = provOID + tempProv[oi];
            if (oi < tempProv.Length - 1)
            {
                provOID += ",";
            }
             
        }
        command = "SELECT GROUP_CONCAT(provider.NationalProvID) FROM provider WHERE provider.EhrKey=" + "(SELECT pv.EhrKey FROM provider pv WHERE pv.ProvNum=" + POut.long(provNum) + ")";
        String provNPIs = Db.getScalar(command);
        //Some measures use a temp table.  Create a random number to tack onto the end of the temp table name to avoid possible table collisions.
        Random rnd = new Random();
        String rndStr = rnd.Next(1000000).ToString();
        switch(mtype)
        {
            case ProblemList: 
                //Jordan's original query
                //command="SELECT PatNum,LName,FName, "
                //  +"(SELECT COUNT(*) FROM disease WHERE PatNum=patient.PatNum AND DiseaseDefNum="
                //    +POut.Long(PrefC.GetLong(PrefName.ProblemsIndicateNone))+") AS problemsNone, "
                //  +"(SELECT COUNT(*) FROM disease WHERE PatNum=patient.PatNum) AS problemsAll "
                //  +"FROM patient "
                //  +"WHERE EXISTS(SELECT * FROM procedurelog WHERE patient.PatNum=procedurelog.PatNum "
                //  +"AND procedurelog.ProcStatus=2 "//complete
                //  +"AND procedurelog.ProvNum IN("+POut.String(provs)+") "
                //  +"AND procedurelog.ProcDate >= "+POut.Date(dateStart)+" "
                //  +"AND procedurelog.ProcDate <= "+POut.Date(dateEnd)+")";
                //Query optimized to be faster by Cameron
                //command="SELECT A.*,COALESCE(problemsNone.Count,0) AS problemsNone,COALESCE(problemsAll.Count,0) AS problemsAll "
                //	+"FROM (SELECT patient.PatNum,LName,FName FROM patient "
                //	+"INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 "
                //	+"AND procedurelog.ProvNum IN("+POut.String(provs)+") "
                //	+"AND procedurelog.ProcDate BETWEEN "+POut.Date(dateStart)+" AND "+POut.Date(dateEnd)+" "
                //	+"GROUP BY patient.PatNum) A "
                //	+"LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM disease WHERE DiseaseDefNum="+POut.Long(PrefC.GetLong(PrefName.ProblemsIndicateNone))+" "
                //	+"GROUP BY PatNum) problemsNone ON problemsNone.PatNum=A.PatNum "
                //	+"LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM disease GROUP BY PatNum) problemsAll ON problemsAll.PatNum=A.PatNum";
                //Query modified to count only problems with ICD9 or SNOMED code attached
                command = "SELECT A.*,COALESCE(problemsNone.Count,0) AS problemsNone,COALESCE(problemsAll.Count,0) AS problemsAll " + "FROM (SELECT patient.PatNum,LName,FName FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ") " + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM disease WHERE DiseaseDefNum=" + POut.long(PrefC.getLong(PrefName.ProblemsIndicateNone)) + " " + "AND ProbStatus=0 GROUP BY PatNum) problemsNone ON problemsNone.PatNum=A.PatNum " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM disease " + "INNER JOIN diseasedef ON disease.DiseaseDefNum=diseasedef.DiseaseDefNum " + "AND disease.DiseaseDefNum!=" + POut.long(PrefC.getLong(PrefName.ProblemsIndicateNone)) + " " + "WHERE (diseasedef.SnomedCode!='' OR diseasedef.ICD9Code!='') " + "GROUP BY PatNum) problemsAll ON problemsAll.PatNum=A.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case MedicationList: 
                command = "SELECT A.*,COALESCE(medsNone.Count,0) AS medsNone,COALESCE(medsAll.Count,0) AS medsAll " + "FROM (SELECT patient.PatNum,LName,FName	FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum	AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM medicationpat " + "WHERE MedicationNum=" + POut.long(PrefC.getLong(PrefName.MedicationsIndicateNone)) + " " + "AND (YEAR(DateStop)<1880 OR DateStop>" + POut.date(dateEnd) + ") GROUP BY PatNum) medsNone ON medsNone.PatNum=A.PatNum " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM medicationpat " + "WHERE MedicationNum!=" + POut.long(PrefC.getLong(PrefName.MedicationsIndicateNone)) + " " + "GROUP BY PatNum) medsAll ON medsAll.PatNum=A.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case AllergyList: 
                //Jordan's original query
                //command="SELECT PatNum,LName,FName, "
                //  +"(SELECT COUNT(*) FROM allergy WHERE PatNum=patient.PatNum AND AllergyDefNum="
                //    +POut.Long(PrefC.GetLong(PrefName.AllergiesIndicateNone))+") AS allergiesNone, "
                //  +"(SELECT COUNT(*) FROM allergy WHERE PatNum=patient.PatNum) AS allergiesAll "
                //  +"FROM patient "
                //  +"WHERE EXISTS(SELECT * FROM procedurelog WHERE patient.PatNum=procedurelog.PatNum "
                //  +"AND procedurelog.ProcStatus=2 "//complete
                //  +"AND procedurelog.ProvNum="+POut.Long(provNum)+" "
                //  +"AND procedurelog.ProcDate >= "+POut.Date(dateStart)+" "
                //  +"AND procedurelog.ProcDate <= "+POut.Date(dateEnd)+")";
                //Query optimized to be faster by Cameron
                command = "SELECT A.*,COALESCE(allergiesNone.Count,0) AS allergiesNone,COALESCE(allergiesAll.Count,0) AS allergiesAll " + "FROM (SELECT patient.PatNum,LName,FName	FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum	AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM allergy	" + "WHERE AllergyDefNum=" + POut.long(PrefC.getLong(PrefName.AllergiesIndicateNone)) + " AND StatusIsActive=1 " + "GROUP BY PatNum) allergiesNone ON allergiesNone.PatNum=A.PatNum " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM allergy	" + "WHERE AllergyDefNum!=" + POut.long(PrefC.getLong(PrefName.AllergiesIndicateNone)) + " " + "GROUP BY PatNum) allergiesAll ON allergiesAll.PatNum=A.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case Demographics: 
                //language, gender, race, ethnicity, and birthdate
                //Jordan's original query
                //command="SELECT PatNum,LName,FName,Birthdate,Gender,Race,Language "
                //  +"FROM patient "
                //  +"WHERE EXISTS(SELECT * FROM procedurelog WHERE patient.PatNum=procedurelog.PatNum "
                //  +"AND procedurelog.ProcStatus=2 "//complete
                //  +"AND procedurelog.ProvNum="+POut.Long(provNum)+" "
                //  +"AND procedurelog.ProcDate >= "+POut.Date(dateStart)+" "
                //  +"AND procedurelog.ProcDate <= "+POut.Date(dateEnd)+")";
                //Query optimized to be faster by Cameron
                //command="SELECT patient.PatNum,LName,FName,Birthdate,Gender,Race,Language "
                //	+"FROM patient "
                //	+"INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 "
                //	+"AND procedurelog.ProvNum IN("+POut.String(provs)+")	"
                //	+"AND procedurelog.ProcDate BETWEEN "+POut.Date(dateStart)+" AND "+POut.Date(dateEnd)+" "
                //	+"GROUP BY patient.PatNum";
                command = "SELECT patient.PatNum,LName,FName,Birthdate,Gender,Language,COALESCE(race.HasRace,0) AS HasRace,COALESCE(ethnicity.HasEthnicity,0) AS HasEthnicity " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "LEFT JOIN(SELECT PatNum, 1 AS HasRace FROM patientrace " + "WHERE patientrace.Race IN( " + POut.int(((Enum)PatRace.AfricanAmerican).ordinal()) + "," + POut.int(((Enum)PatRace.AmericanIndian).ordinal()) + "," + POut.int(((Enum)PatRace.Asian).ordinal()) + "," + POut.int(((Enum)PatRace.DeclinedToSpecifyRace).ordinal()) + "," + POut.int(((Enum)PatRace.HawaiiOrPacIsland).ordinal()) + "," + POut.int(((Enum)PatRace.Other).ordinal()) + "," + POut.int(((Enum)PatRace.White).ordinal()) + " " + ") GROUP BY PatNum " + ") AS race ON race.PatNum=patient.PatNum " + "LEFT JOIN(SELECT PatNum, 1 AS HasEthnicity FROM patientrace " + "WHERE patientrace.Race IN( " + POut.int(((Enum)PatRace.Hispanic).ordinal()) + "," + POut.int(((Enum)PatRace.NotHispanic).ordinal()) + "," + POut.int(((Enum)PatRace.DeclinedToSpecifyEthnicity).ordinal()) + " " + ") GROUP BY PatNum " + ") AS ethnicity ON ethnicity.PatNum=patient.PatNum " + "GROUP BY patient.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case Education: 
                //Jordan's original query
                //command="SELECT PatNum,LName,FName, "
                //  +"(SELECT COUNT(*) FROM ehrmeasureevent WHERE PatNum=patient.PatNum AND EventType="+POut.Int((int)EhrMeasureEventType.EducationProvided)+") AS edCount "
                //  +"FROM patient "
                //  +"WHERE EXISTS(SELECT * FROM procedurelog WHERE patient.PatNum=procedurelog.PatNum "
                //  +"AND procedurelog.ProcStatus=2 "//complete
                //  +"AND procedurelog.ProvNum="+POut.Long(provNum)+" "
                //  +"AND procedurelog.ProcDate >= "+POut.Date(dateStart)+" "
                //  +"AND procedurelog.ProcDate <= "+POut.Date(dateEnd)+")";
                //Query optimized to be faster by Cameron
                command = "SELECT A.*,COALESCE(edCount.Count,0) AS edCount " + "FROM (SELECT patient.PatNum,LName,FName	FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.EducationProvided).ordinal()) + " " + "GROUP BY PatNum) edCount ON edCount.PatNum=A.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case TimelyAccess: 
                //denominator is patients
                command = "DROP TABLE IF EXISTS tempehrmeasure" + rndStr;
                Db.nonQ(command);
                command = "CREATE TABLE tempehrmeasure" + rndStr + " (\r\n" + 
                "\t\t\t\t\t\tPatNum bigint NOT NULL auto_increment PRIMARY KEY,\r\n" + 
                "\t\t\t\t\t\tLName varchar(255) NOT NULL,\r\n" + 
                "\t\t\t\t\t\tFName varchar(255) NOT NULL,\r\n" + 
                "\t\t\t\t\t\tlastVisitDate date NOT NULL,\r\n" + 
                "\t\t\t\t\t\tdeadlineDate date NOT NULL,\r\n" + 
                "\t\t\t\t\t\taccessProvided tinyint NOT NULL\r\n" + 
                "\t\t\t\t\t\t) DEFAULT CHARSET=utf8";
                Db.nonQ(command);
                //get all patients who have been seen during the period, along with the most recent visit date during the period
                //complete
                //+"AND procedurelog.ProvNum="+POut.Long(provNum)+" "
                command = "INSERT INTO tempehrmeasure" + rndStr + " (PatNum,LName,FName,lastVisitDate) SELECT patient.PatNum,LName,FName, " + "MAX(procedurelog.ProcDate) " + "FROM patient,procedurelog " + "WHERE patient.PatNum=procedurelog.PatNum " + "AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate >= " + POut.date(dateStart) + " " + "AND procedurelog.ProcDate <= " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum";
                tableRaw = Db.getTable(command);
                //calculate the deadlineDate
                command = "UPDATE tempehrmeasure" + rndStr + " " + "SET deadlineDate = ADDDATE(lastVisitDate, INTERVAL 4 DAY)";
                Db.nonQ(command);
                command = "UPDATE tempehrmeasure" + rndStr + " " + "SET deadlineDate = ADDDate(lastVisitDate, INTERVAL 2 DAY) " + "WHERE DAYOFWEEK(lastVisitDate) IN(3,4,5,6)";
                //add 2 more days for weekend
                //tues, wed, thur, fri
                Db.nonQ(command);
                //date provided could be any date before deadline date if there was more than one visit
                command = "UPDATE tempehrmeasure" + rndStr + ",ehrmeasureevent SET accessProvided = 1 " + "WHERE ehrmeasureevent.PatNum=tempehrmeasure" + rndStr + ".PatNum " + "AND EventType=" + POut.int(((Enum)EhrMeasureEventType.OnlineAccessProvided).ordinal()) + " " + "AND DATE(ehrmeasureevent.DateTEvent) <= deadlineDate";
                Db.nonQ(command);
                command = "SELECT * FROM tempehrmeasure" + rndStr;
                tableRaw = Db.getTable(command);
                command = "DROP TABLE IF EXISTS tempehrmeasure" + rndStr;
                Db.nonQ(command);
                break;
            case ProvOrderEntry: 
                //Jordan's original query
                //command="SELECT PatNum,LName,FName, "
                //  +"(SELECT COUNT(*) FROM medicationpat mp2 WHERE mp2.PatNum=patient.PatNum "
                //  +"AND mp2.PatNote != '' AND mp2.DateStart > "+POut.Date(new DateTime(1880,1,1))+") AS countOrders "
                //  +"FROM patient "
                //  +"WHERE EXISTS(SELECT * FROM procedurelog WHERE patient.PatNum=procedurelog.PatNum "//at least one procedure in the period
                //  +"AND procedurelog.ProcStatus=2 "//complete
                //  +"AND procedurelog.ProvNum="+POut.Long(provNum)+" "
                //  +"AND procedurelog.ProcDate >= "+POut.Date(dateStart)+" "
                //  +"AND procedurelog.ProcDate <= "+POut.Date(dateEnd)+") "
                //  +"AND EXISTS(SELECT * FROM medicationpat WHERE medicationpat.PatNum=patient.PatNum)";//at least one medication
                //Query optimized to be faster by Cameron
                //command="SELECT A.*,COALESCE(countOrders.Count,0) AS countOrders "
                //	+"FROM (SELECT patient.PatNum,LName,FName FROM patient "
                //	+"INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 "
                //	+"AND procedurelog.ProvNum IN("+POut.String(provs)+")	"
                //	+"AND procedurelog.ProcDate BETWEEN "+POut.Date(dateStart)+" AND "+POut.Date(dateEnd)+" "
                //	+"INNER JOIN medicationpat ON medicationpat.PatNum=patient.PatNum "
                //	+"GROUP BY patient.PatNum) A "
                //	+"LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM medicationpat mp2 "
                //	+"WHERE mp2.PatNote!='' AND mp2.DateStart > "+POut.Date(new DateTime(1880,1,1))+" "
                //	+"GROUP BY PatNum) countOrders ON countOrders.PatNum=A.PatNum";
                //Now using IsCpoe flag instead of PatNote and DateStart to mark as an order
                command = "SELECT allpats.*,COALESCE(CountCpoe.Count,0) AS CountCpoe " + "FROM (SELECT patient.PatNum,patient.LName,patient.FName FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "INNER JOIN medicationpat ON medicationpat.PatNum=patient.PatNum " + "AND MedicationNum!=" + POut.long(PrefC.getLong(PrefName.MedicationsIndicateNone)) + " " + "GROUP BY patient.PatNum) allpats " + "LEFT JOIN (SELECT medicationpat.PatNum,COUNT(*) AS 'Count' FROM medicationpat " + "WHERE medicationpat.IsCpoe=1 GROUP BY medicationpat.PatNum) CountCpoe ON CountCpoe.PatNum=allpats.PatNum";
                //allpats seen by provider in date range with medication in med list that is not the 'None' medication
                tableRaw = Db.getTable(command);
                break;
            case CPOE_MedOrdersOnly: 
                //This optional alternate no longer counts patients with meds in med list, instead we will count the orders created by the Provider during the reporting period and what percentage are CPOE (meaning they were entered through NewCrop)
                command = "SELECT patient.PatNum,patient.LName,patient.FName,medicationpat.MedicationPatNum," + "COALESCE(medication.MedName,medicationpat.MedDescript) AS MedName,medicationpat.DateStart," + "medicationpat.IsCpoe FROM patient " + "INNER JOIN medicationpat ON medicationpat.PatNum=patient.PatNum " + "AND medicationpat.ProvNum IN(" + POut.string(provs) + ")	" + "AND medicationpat.PatNote!='' " + "AND medicationpat.DateStart BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "LEFT JOIN medication ON medication.MedicationNum=medicationpat.MedicationNum";
                tableRaw = Db.getTable(command);
                break;
            case CPOE_PreviouslyOrdered: 
                //For details regarding this optional alternate see: https://questions.cms.gov/faq.php?id=5005&faqId=3257, summmary: If you prescribe more than 100 meds during the reporting period, maintain medication lists that include meds the Provider did not order, and orders meds for less than 30% of patients with meds in med list during the reporting period, then the denominator can be limited to only those patients for whom the Provider has previously ordered meds.
                command = "SELECT allpatsprevordered.*,COALESCE(CountCpoe.Count,0) AS CountCpoe " + "FROM (SELECT patient.PatNum,patient.LName,patient.FName FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ") " + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "INNER JOIN medicationpat ON medicationpat.PatNum=patient.PatNum " + "AND medicationpat.MedicationNum!=" + POut.long(PrefC.getLong(PrefName.MedicationsIndicateNone)) + " ";
                //this next join limits to only patients for whom the provider has previously ordered medications
                command += "INNER JOIN (SELECT PatNum FROM medicationpat " + "WHERE PatNote!='' AND DateStart > " + POut.date(new DateTime(1880, 1, 1)) + " " + "AND ProvNum IN(" + POut.string(provs) + ") GROUP BY PatNum) prevordered ON prevordered.PatNum=patient.PatNum " + "GROUP BY patient.PatNum) allpatsprevordered " + "LEFT JOIN (SELECT medicationpat.PatNum,COUNT(*) AS 'Count' FROM medicationpat " + "WHERE medicationpat.IsCpoe=1 GROUP BY PatNum) CountCpoe ON CountCpoe.PatNum=allpatsprevordered.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case Rx: 
                //+"AND rxpat.ProvNum="+POut.Long(provNum)+" "
                command = "SELECT patient.PatNum,LName,FName,SendStatus,RxDate " + "FROM rxpat,patient " + "WHERE rxpat.PatNum=patient.PatNum " + "AND IsControlled = 0 " + "AND rxpat.ProvNum IN(" + POut.string(provs) + ")	" + "AND RxDate >= " + POut.date(dateStart) + " " + "AND RxDate <= " + POut.date(dateEnd);
                tableRaw = Db.getTable(command);
                break;
            case VitalSigns: 
                //Jordan's original query
                //command="SELECT PatNum,LName,FName, "
                //  +"(SELECT COUNT(*) FROM vitalsign WHERE vitalsign.PatNum=patient.PatNum AND Height>0 AND Weight>0) AS hwCount, "
                //  +"(SELECT COUNT(*) FROM vitalsign WHERE vitalsign.PatNum=patient.PatNum AND BpSystolic>0 AND BpDiastolic>0) AS bpCount "
                //  +"FROM patient "
                //  +"WHERE EXISTS(SELECT * FROM procedurelog WHERE patient.PatNum=procedurelog.PatNum "
                //  +"AND procedurelog.ProcStatus=2 "//complete
                //  +"AND procedurelog.ProvNum="+POut.Long(provNum)+" "
                //  +"AND procedurelog.ProcDate >= "+POut.Date(dateStart)+" "
                //  +"AND procedurelog.ProcDate <= "+POut.Date(dateEnd)+") "
                //  +"AND patient.Birthdate <= "+POut.Date(DateTime.Today.AddYears(-2));//2 and older
                //Query optimized to be faster by Cameron
                //command="SELECT A.*,COALESCE(hwCount.Count,0) AS hwCount,COALESCE(bpCount.Count,0) AS bpCount "
                //	+"FROM (SELECT patient.PatNum,LName,FName FROM patient "
                //	+"INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 "
                //	+"AND procedurelog.ProvNum IN("+POut.String(provs)+")	"
                //	+"AND procedurelog.ProcDate BETWEEN "+POut.Date(dateStart)+" AND "+POut.Date(dateEnd)+" "
                //	+"WHERE patient.Birthdate <= "+POut.Date(DateTime.Today.AddYears(-2))+" "//2 and older
                //	+"GROUP BY patient.PatNum) A "
                //	+"LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM vitalsign	WHERE Height>0 AND Weight>0 GROUP BY PatNum) hwCount ON hwCount.PatNum=A.PatNum "
                //	+"LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM vitalsign WHERE BpSystolic>0 AND BpDiastolic>0 GROUP BY PatNum) bpCount ON bpCount.PatNum=A.PatNum";
                //Query modified for new requirements (Optional 2013, Required 2014 and beyond).  BP 3 and older only, Height/Weight all ages
                command = "SELECT A.*,COALESCE(hwCount.Count,0) AS hwCount,COALESCE(bpCount.Count,0) AS bpCount " + "FROM (SELECT patient.PatNum,LName,FName FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "WHERE patient.Birthdate <= " + POut.Date(DateTime.Today.AddYears(-2)) + " " + "GROUP BY patient.PatNum) A " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM vitalsign	WHERE Height>0 AND Weight>0 GROUP BY PatNum) hwCount ON hwCount.PatNum=A.PatNum " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM vitalsign WHERE BpSystolic>0 AND BpDiastolic>0 GROUP BY PatNum) bpCount ON bpCount.PatNum=A.PatNum";
                //2 and older
                tableRaw = Db.getTable(command);
                break;
            case VitalSigns2014: 
                command = "SELECT A.*,COALESCE(hwCount.Count,0) AS hwCount," + "(CASE WHEN A.Birthdate <= (A.LastVisitInDateRange-INTERVAL 3 YEAR) ";
                //BP count only if 3 and older at time of last visit in date range
                command += "THEN COALESCE(bpCount.Count,0) ELSE 1 END) AS bpCount " + "FROM (SELECT patient.PatNum,LName,FName,Birthdate,MAX(procedurelog.ProcDate) AS LastVisitInDateRange " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM vitalsign	WHERE Height>0 AND Weight>0 GROUP BY PatNum) hwCount ON hwCount.PatNum=A.PatNum " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM vitalsign WHERE BpSystolic>0 AND BpDiastolic>0 GROUP BY PatNum) bpCount ON bpCount.PatNum=A.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case VitalSignsBMIOnly: 
                command = "SELECT A.*,COALESCE(hwCount.Count,0) AS hwCount " + "FROM (SELECT patient.PatNum,LName,FName FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM vitalsign	WHERE Height>0 AND Weight>0 GROUP BY PatNum) hwCount ON hwCount.PatNum=A.PatNum ";
                tableRaw = Db.getTable(command);
                break;
            case VitalSignsBPOnly: 
                command = "SELECT patient.PatNum,LName,FName,Birthdate,COUNT(DISTINCT VitalsignNum) AS bpcount " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum " + "AND procedurelog.ProcStatus=2	AND procedurelog.ProvNum IN(" + POut.string(provs) + ") " + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "LEFT JOIN vitalsign ON vitalsign.PatNum=patient.PatNum AND BpSystolic!=0 AND BpDiastolic!=0 " + "GROUP BY patient.PatNum " + "HAVING Birthdate<=MAX(ProcDate)-INTERVAL 3 YEAR ";
                //only include in results if over 3 yrs old at date of last visit
                tableRaw = Db.getTable(command);
                break;
            case Smoking: 
                //Jordan's original query
                //command="SELECT PatNum,LName,FName,SmokeStatus "
                //  +"FROM patient "
                //  +"WHERE EXISTS(SELECT * FROM procedurelog WHERE patient.PatNum=procedurelog.PatNum "
                //  +"AND procedurelog.ProcStatus=2 "//complete
                //  +"AND procedurelog.ProvNum="+POut.Long(provNum)+" "
                //  +"AND procedurelog.ProcDate >= "+POut.Date(dateStart)+" "
                //  +"AND procedurelog.ProcDate <= "+POut.Date(dateEnd)+") "
                //  +"AND patient.Birthdate <= "+POut.Date(DateTime.Today.AddYears(-13));//13 and older
                //Query optimized to be faster by Cameron
                command = "SELECT patient.PatNum,LName,FName,SmokingSnoMed FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ") " + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "WHERE patient.Birthdate <= " + POut.Date(DateTime.Today.AddYears(-13)) + " " + "GROUP BY patient.PatNum";
                //13 and older
                tableRaw = Db.getTable(command);
                break;
            case Lab: 
                //Jordan's original query
                //command="SELECT patient.PatNum,LName,FName,DateTimeOrder, "
                //  +"(SELECT COUNT(*) FROM labpanel WHERE labpanel.MedicalOrderNum=medicalorder.MedicalOrderNum) AS panelCount "
                //  +"FROM medicalorder,patient "
                //  +"WHERE medicalorder.PatNum=patient.PatNum "
                //  +"AND MedOrderType="+POut.Int((int)MedicalOrderType.Laboratory)+" "
                //  +"AND medicalorder.ProvNum="+POut.Long(provNum)+" "
                //  +"AND DATE(DateTimeOrder) >= "+POut.Date(dateStart)+" "
                //  +"AND DATE(DateTimeOrder) <= "+POut.Date(dateEnd);
                //Query optimized to be faster by Cameron
                //TODO: Combine these queries to get old and new lab data
                //When the lab is using a NPI number to determine provider.
                //When the lab is using provider number to determine provider.
                //If the AssigningAuthority is OpenDental.
                //Use the ProvNum to determine provider.
                //If the AssigningAuthority is not OpenDental, we have no way to tell who the provider is.
                command = "SELECT 1 AS IsOldLab,patient.PatNum,LName,FName,DateTimeOrder,COALESCE(panels.Count,0) AS ResultCount FROM patient " + "INNER JOIN medicalorder ON patient.PatNum=medicalorder.PatNum " + "AND MedOrderType=" + POut.int(((Enum)MedicalOrderType.Laboratory).ordinal()) + " " + "AND medicalorder.ProvNum IN(" + POut.string(provs) + ") " + "AND DATE(DateTimeOrder) BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "LEFT JOIN (SELECT MedicalOrderNum,COUNT(*) AS 'Count' FROM labpanel GROUP BY MedicalOrderNum " + ") panels ON panels.MedicalOrderNum=medicalorder.MedicalOrderNum " + "UNION ALL " + "SELECT 0 AS IsOldLab,patient.PatNum,LName,FName,STR_TO_DATE(ObservationDateTimeStart,'%Y%m%d') AS DateTimeOrder,COALESCE(ehrlabs.Count,0) AS ResultCount FROM patient " + "INNER JOIN ehrlab ON patient.PatNum=ehrlab.PatNum " + "LEFT JOIN (SELECT EhrLabNum, COUNT(*) AS 'Count' FROM ehrlabresult " + "WHERE ehrlabresult.ValueType='NM' OR ehrlabresult.ValueType='SN' " + "OR ehrlabresult.ObservationValueCodedElementID IN (" + _snomedLabResult + ") " + "OR ehrlabresult.ObservationValueCodedElementIDAlt IN (" + _snomedLabResult + ") " + "GROUP BY EhrLabNum " + ") ehrlabs ON ehrlab.EhrLabNum=ehrlabs.EhrLabNum " + "WHERE (CASE WHEN ehrlab.OrderingProviderIdentifierTypeCode='NPI' THEN ehrlab.OrderingProviderID IN(" + POut.string(provNPIs) + ") " + "WHEN ehrlab.OrderingProviderIdentifierTypeCode='PRN' THEN ( " + "CASE WHEN ehrlab.OrderingProviderAssigningAuthorityUniversalID=( " + "SELECT IDRoot FROM oidinternal WHERE IDType='Provider' GROUP BY IDType " + ") THEN ehrlab.OrderingProviderID IN('" + POut.string(provOID) + "') END) " + "ELSE FALSE END) " + "AND ehrlab.ObservationDateTimeStart BETWEEN DATE_FORMAT(" + POut.date(dateStart) + ",'%Y%m%d') AND DATE_FORMAT(" + POut.date(dateEnd) + ",'%Y%m%d') " + "AND (CASE WHEN ehrlab.UsiCodeSystemName='LN' THEN ehrlab.UsiID WHEN ehrlab.UsiCodeSystemNameAlt='LN' THEN ehrlab.UsiIDAlt ELSE '' END) " + "NOT IN (SELECT LoincCode FROM loinc WHERE loinc.ClassType LIKE '%rad%')";
                tableRaw = Db.getTable(command);
                break;
            case ElectronicCopy: 
                command = "DROP TABLE IF EXISTS tempehrmeasure" + rndStr;
                Db.nonQ(command);
                command = "CREATE TABLE tempehrmeasure" + rndStr + " (\r\n" + 
                "\t\t\t\t\t\tTempEhrMeasureNum bigint NOT NULL auto_increment PRIMARY KEY,\r\n" + 
                "\t\t\t\t\t\tPatNum bigint NOT NULL,\r\n" + 
                "\t\t\t\t\t\tLName varchar(255) NOT NULL,\r\n" + 
                "\t\t\t\t\t\tFName varchar(255) NOT NULL,\r\n" + 
                "\t\t\t\t\t\tdateRequested date NOT NULL,\r\n" + 
                "\t\t\t\t\t\tdateDeadline date NOT NULL,\r\n" + 
                "\t\t\t\t\t\tcopyProvided tinyint NOT NULL,\r\n" + 
                "\t\t\t\t\t\tINDEX(PatNum)\r\n" + 
                "\t\t\t\t\t\t) DEFAULT CHARSET=utf8";
                Db.nonQ(command);
                //+"AND patient.PriProv="+POut.Long(provNum);
                command = "INSERT INTO tempehrmeasure" + rndStr + " (PatNum,LName,FName,dateRequested) SELECT patient.PatNum,LName,FName,DATE(DateTEvent) " + "FROM ehrmeasureevent,patient " + "WHERE patient.PatNum=ehrmeasureevent.PatNum " + "AND EventType=" + POut.int(((Enum)EhrMeasureEventType.ElectronicCopyRequested).ordinal()) + " " + "AND DATE(DateTEvent) >= " + POut.date(dateStart) + " " + "AND DATE(DateTEvent) <= " + POut.date(dateEnd) + " " + "AND patient.PriProv IN(" + POut.string(provs) + ")";
                Db.nonQ(command);
                command = "UPDATE tempehrmeasure" + rndStr + " " + "SET dateDeadline = ADDDATE(dateRequested, INTERVAL 3 DAY)";
                Db.nonQ(command);
                command = "UPDATE tempehrmeasure" + rndStr + " " + "SET dateDeadline = ADDDate(dateDeadline, INTERVAL 2 DAY) " + "WHERE DAYOFWEEK(dateRequested) IN(4,5,6)";
                //add 2 more days for weekend
                //wed, thur, fri
                Db.nonQ(command);
                command = "UPDATE tempehrmeasure" + rndStr + ",ehrmeasureevent SET copyProvided = 1 " + "WHERE ehrmeasureevent.PatNum=tempehrmeasure" + rndStr + ".PatNum AND EventType=" + POut.int(((Enum)EhrMeasureEventType.ElectronicCopyProvidedToPt).ordinal()) + " " + "AND DATE(ehrmeasureevent.DateTEvent) >= dateRequested " + "AND DATE(ehrmeasureevent.DateTEvent) <= dateDeadline";
                Db.nonQ(command);
                command = "SELECT * FROM tempehrmeasure" + rndStr;
                tableRaw = Db.getTable(command);
                command = "DROP TABLE IF EXISTS tempehrmeasure" + rndStr;
                Db.nonQ(command);
                break;
            case ClinicalSummaries: 
                command = "DROP TABLE IF EXISTS tempehrmeasure" + rndStr;
                Db.nonQ(command);
                command = "CREATE TABLE tempehrmeasure" + rndStr + " (\r\n" + 
                "\t\t\t\t\t\tTempEhrMeasureNum bigint NOT NULL auto_increment PRIMARY KEY,\r\n" + 
                "\t\t\t\t\t\tPatNum bigint NOT NULL,\r\n" + 
                "\t\t\t\t\t\tLName varchar(255) NOT NULL,\r\n" + 
                "\t\t\t\t\t\tFName varchar(255) NOT NULL,\r\n" + 
                "\t\t\t\t\t\tvisitDate date NOT NULL,\r\n" + 
                "\t\t\t\t\t\tdeadlineDate date NOT NULL,\r\n" + 
                "\t\t\t\t\t\tsummaryProvided tinyint NOT NULL,\r\n" + 
                "\t\t\t\t\t\tINDEX(PatNum)\r\n" + 
                "\t\t\t\t\t\t) DEFAULT CHARSET=utf8";
                Db.nonQ(command);
                //+"AND procedurelog.ProvNum="+POut.Long(provNum)+" "
                command = "INSERT INTO tempehrmeasure" + rndStr + " (PatNum,LName,FName,visitDate) SELECT patient.PatNum,LName,FName,ProcDate " + "FROM procedurelog " + "LEFT JOIN patient ON patient.PatNum=procedurelog.PatNum " + "WHERE ProcDate >= " + POut.date(dateStart) + " " + "AND ProcDate <= " + POut.date(dateEnd) + " " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ") " + "AND procedurelog.ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "GROUP BY procedurelog.PatNum,ProcDate";
                Db.nonQ(command);
                command = "UPDATE tempehrmeasure" + rndStr + " " + "SET deadlineDate = ADDDATE(visitDate, INTERVAL 3 DAY)";
                Db.nonQ(command);
                command = "UPDATE tempehrmeasure" + rndStr + " " + "SET DeadlineDate = ADDDate(deadlineDate, INTERVAL 2 DAY) " + "WHERE DAYOFWEEK(visitDate) IN(4,5,6)";
                //add 2 more days for weekend
                //wed, thur, fri
                Db.nonQ(command);
                command = "UPDATE tempehrmeasure" + rndStr + ",ehrmeasureevent SET summaryProvided = 1 " + "WHERE ehrmeasureevent.PatNum=tempehrmeasure" + rndStr + ".PatNum AND EventType=" + POut.int(((Enum)EhrMeasureEventType.ClinicalSummaryProvidedToPt).ordinal()) + " " + "AND DATE(ehrmeasureevent.DateTEvent) >= visitDate " + "AND DATE(ehrmeasureevent.DateTEvent) <= deadlineDate";
                Db.nonQ(command);
                command = "SELECT * FROM tempehrmeasure" + rndStr;
                tableRaw = Db.getTable(command);
                command = "DROP TABLE IF EXISTS tempehrmeasure" + rndStr;
                Db.nonQ(command);
                break;
            case Reminders: 
                //Jordan's original query
                //command="SELECT PatNum,LName,FName, "
                //  +"(SELECT COUNT(*) FROM ehrmeasureevent WHERE PatNum=patient.PatNum AND EventType="+POut.Int((int)EhrMeasureEventType.ReminderSent)+" "
                //  +"AND DATE(ehrmeasureevent.DateTEvent) >= "+POut.Date(dateStart)+" "
                //  +"AND DATE(ehrmeasureevent.DateTEvent) <= "+POut.Date(dateEnd)+" "
                //  +") AS reminderCount "
                //  +"FROM patient "
                //  +"WHERE patient.Birthdate > '1880-01-01' "//a birthdate is entered
                //  +"AND (patient.Birthdate > "+POut.Date(DateTime.Today.AddYears(-6))+" "//5 years or younger
                //  +"OR patient.Birthdate <= "+POut.Date(DateTime.Today.AddYears(-65))+") "//65+
                //  +"AND patient.PatStatus="+POut.Int((int)PatientStatus.Patient)+" "
                //  +"AND patient.PriProv="+POut.Long(provNum);
                //Query optimized to be faster by Cameron
                //command="SELECT patient.PatNum,LName,FName,COALESCE(reminderCount.Count,0) AS reminderCount FROM patient "
                //	+"LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM ehrmeasureevent "
                //	+"WHERE EventType="+POut.Int((int)EhrMeasureEventType.ReminderSent)+" "
                //	+"AND DATE(ehrmeasureevent.DateTEvent) BETWEEN "+POut.Date(dateStart)+" AND "+POut.Date(dateEnd)+" "
                //	+"GROUP BY PatNum) reminderCount ON reminderCount.PatNum=patient.PatNum "
                //	+"WHERE patient.Birthdate > '1880-01-01' "//a birthdate is entered
                //	+"AND (patient.Birthdate > "+POut.Date(DateTime.Today.AddYears(-6))+" "//5 years or younger
                //	+"OR patient.Birthdate <= "+POut.Date(DateTime.Today.AddYears(-65))+") "//65+
                //	+"AND patient.PatStatus="+POut.Int((int)PatientStatus.Patient)+" "
                //	+"AND patient.PriProv IN("+POut.String(provs)+")";
                //Query modified to only return patients that have been seen by any provider in the last 3 years based on dateStart of measurement period
                //a birthdate is entered
                //5 years or younger as of start of measurement period
                //65+ as of start of measurement period
                command = "SELECT patient.PatNum,LName,FName,COALESCE(reminderCount.Count,0) AS reminderCount FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum " + "AND ProcStatus=2 AND ProcDate>" + POut.date(dateStart) + "-INTERVAL 3 YEAR " + "LEFT JOIN (SELECT ehrmeasureevent.PatNum,COUNT(*) AS 'Count' FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.ReminderSent).ordinal()) + " " + "AND DATE(ehrmeasureevent.DateTEvent) BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY ehrmeasureevent.PatNum) reminderCount ON reminderCount.PatNum=patient.PatNum " + "WHERE patient.Birthdate > '1880-01-01' " + "AND (patient.Birthdate > " + POut.date(dateStart) + "-INTERVAL 5 YEAR " + "OR patient.Birthdate <= " + POut.date(dateStart) + "-INTERVAL 65 YEAR) " + "AND patient.PatStatus=" + POut.int(((Enum)PatientStatus.Patient).ordinal()) + " " + "AND patient.PriProv IN(" + POut.string(provs) + ") " + "GROUP BY patient.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case MedReconcile: 
                //command="DROP TABLE IF EXISTS tempehrmeasure"+rndStr;
                //Db.NonQ(command);
                //command="CREATE TABLE tempehrmeasure"+rndStr+@" (
                //	PatNum bigint NOT NULL PRIMARY KEY,
                //	LName varchar(255) NOT NULL,
                //	FName varchar(255) NOT NULL,
                //	RefCount int NOT NULL,
                //	ReconcileCount int NOT NULL
                //	) DEFAULT CHARSET=utf8";
                //Db.NonQ(command);
                //command="INSERT INTO tempehrmeasure"+rndStr+" (PatNum,LName,FName,RefCount) SELECT patient.PatNum,LName,FName,COUNT(*) "
                //	+"FROM refattach,patient "
                //	+"WHERE patient.PatNum=refattach.PatNum "
                //	//+"AND patient.PriProv="+POut.Long(provNum)+" "
                //	+"AND patient.PriProv IN("+POut.String(provs)+") "
                //	+"AND RefDate >= "+POut.Date(dateStart)+" "
                //	+"AND RefDate <= "+POut.Date(dateEnd)+" "
                //	+"AND IsFrom=1 AND IsTransitionOfCare=1 "
                //	+"GROUP BY refattach.PatNum";
                //Db.NonQ(command);
                //command="UPDATE tempehrmeasure"+rndStr+" "
                //	+"SET ReconcileCount = (SELECT COUNT(*) FROM ehrmeasureevent "
                //	+"WHERE ehrmeasureevent.PatNum=tempehrmeasure"+rndStr+".PatNum AND EventType="+POut.Int((int)EhrMeasureEventType.MedicationReconcile)+" "
                //	+"AND DATE(ehrmeasureevent.DateTEvent) >= "+POut.Date(dateStart)+" "
                //	+"AND DATE(ehrmeasureevent.DateTEvent) <= "+POut.Date(dateEnd)+")";
                //Db.NonQ(command);
                //command="SELECT * FROM tempehrmeasure"+rndStr;
                //tableRaw=Db.GetTable(command);
                //command="DROP TABLE IF EXISTS tempehrmeasure"+rndStr;
                //Db.NonQ(command);
                //Reworked to only count patients seen by this provider in the date range
                command = "SELECT ptsRefCnt.*,COALESCE(RecCount,0) AS ReconcileCount " + "FROM (SELECT ptsSeen.*,COUNT(DISTINCT refattach.RefAttachNum) AS RefCount " + "FROM (SELECT patient.PatNum,LName,FName FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum " + "AND ProcStatus=2 AND ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "GROUP BY patient.PatNum) ptsSeen " + "INNER JOIN refattach ON ptsSeen.PatNum=refattach.PatNum " + "AND RefDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "AND IsFrom=1 AND IsTransitionOfCare=1 " + "GROUP BY ptsSeen.PatNum) ptsRefCnt " + "LEFT JOIN (SELECT ehrmeasureevent.PatNum,COUNT(*) AS RecCount FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.MedicationReconcile).ordinal()) + " " + "AND DATE(ehrmeasureevent.DateTEvent) BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY ehrmeasureevent.PatNum) ptsRecCount ON ptsRefCnt.PatNum=ptsRecCount.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case SummaryOfCare: 
                //command="DROP TABLE IF EXISTS tempehrmeasure"+rndStr;
                //Db.NonQ(command);
                //command="CREATE TABLE tempehrmeasure"+rndStr+@" (
                //	PatNum bigint NOT NULL PRIMARY KEY,
                //	LName varchar(255) NOT NULL,
                //	FName varchar(255) NOT NULL,
                //	RefCount int NOT NULL,
                //	CcdCount int NOT NULL
                //	) DEFAULT CHARSET=utf8";
                //Db.NonQ(command);
                //command="INSERT INTO tempehrmeasure"+rndStr+" (PatNum,LName,FName,RefCount) SELECT patient.PatNum,LName,FName,COUNT(*) "
                //	+"FROM refattach,patient "
                //	+"WHERE patient.PatNum=refattach.PatNum "
                //	//+"AND patient.PriProv="+POut.Long(provNum)+" "
                //	+"AND patient.PriProv IN("+POut.String(provs)+") "
                //	+"AND RefDate >= "+POut.Date(dateStart)+" "
                //	+"AND RefDate <= "+POut.Date(dateEnd)+" "
                //	+"AND IsFrom=0 AND IsTransitionOfCare=1 "
                //	+"GROUP BY refattach.PatNum";
                //Db.NonQ(command);
                //command="UPDATE tempehrmeasure"+rndStr+" "
                //	+"SET CcdCount = (SELECT COUNT(*) FROM ehrmeasureevent "
                //	+"WHERE ehrmeasureevent.PatNum=tempehrmeasure"+rndStr+".PatNum AND EventType="+POut.Int((int)EhrMeasureEventType.SummaryOfCareProvidedToDr)+" "
                //	+"AND DATE(ehrmeasureevent.DateTEvent) >= "+POut.Date(dateStart)+" "
                //	+"AND DATE(ehrmeasureevent.DateTEvent) <= "+POut.Date(dateEnd)+")";
                //Db.NonQ(command);
                //command="SELECT * FROM tempehrmeasure"+rndStr;
                //tableRaw=Db.GetTable(command);
                //command="DROP TABLE IF EXISTS tempehrmeasure"+rndStr;
                //Db.NonQ(command);
                //Reworked to only count patients seen by this provider in the date range
                command = "SELECT patient.PatNum,patient.LName,patient.FName,refattach.RefDate, " + "referral.FName AS RefFName,referral.LName AS RefLName,SUM(CASE WHEN ISNULL(socevent.FKey) THEN 0 ELSE 1 END) AS SOCSent " + "FROM refattach " + "INNER JOIN referral ON referral.ReferralNum=refattach.ReferralNum " + "INNER JOIN patient ON patient.PatNum=refattach.PatNum " + "LEFT JOIN ( " + "SELECT ehrmeasureevent.FKey " + "FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.SummaryOfCareProvidedToDr).ordinal()) + " " + "AND DATE(ehrmeasureevent.DateTEvent) BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + ") socevent ON socevent.FKey=refattach.RefAttachNum " + "WHERE RefDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "AND IsFrom=0 AND IsTransitionOfCare=1 " + "AND refattach.ProvNum IN(" + POut.string(provs) + ") " + "GROUP BY refattach.RefAttachNum";
                tableRaw = Db.getTable(command);
                break;
            default: 
                throw new ApplicationException("Type not found: " + mtype.ToString());
        
        }
        //PatNum, PatientName, Explanation, and Met (X).
        DataTable table = new DataTable("audit");
        DataRow row = new DataRow();
        table.Columns.Add("PatNum");
        table.Columns.Add("patientName");
        table.Columns.Add("explanation");
        table.Columns.Add("met");
        //X or empty
        List<DataRow> rows = new List<DataRow>();
        Patient pat;
        String explanation = new String();
        for (int i = 0;i < tableRaw.Rows.Count;i++)
        {
            row = table.NewRow();
            row["PatNum"] = tableRaw.Rows[i]["PatNum"].ToString();
            pat = new Patient();
            pat.LName = tableRaw.Rows[i]["LName"].ToString();
            pat.FName = tableRaw.Rows[i]["FName"].ToString();
            pat.Preferred = "";
            row["patientName"] = pat.getNameLF();
            row["met"] = "";
            explanation = "";
            switch(mtype)
            {
                case ProblemList: 
                    if (!StringSupport.equals(tableRaw.Rows[i]["problemsNone"].ToString(), "0"))
                    {
                        explanation = "Problems indicated 'None'.";
                        row["met"] = "X";
                    }
                    else if (!StringSupport.equals(tableRaw.Rows[i]["problemsAll"].ToString(), "0"))
                    {
                        explanation = "Problems entered: " + tableRaw.Rows[i]["problemsAll"].ToString();
                        row["met"] = "X";
                    }
                    else
                    {
                        //explanation="No Problems entered";
                        explanation = "No Problems entered with ICD-9 code or SNOMED code attached.";
                    }  
                    break;
                case MedicationList: 
                    if (!StringSupport.equals(tableRaw.Rows[i]["medsNone"].ToString(), "0"))
                    {
                        explanation = "Medications indicated 'None'";
                        row["met"] = "X";
                    }
                    else if (!StringSupport.equals(tableRaw.Rows[i]["medsAll"].ToString(), "0"))
                    {
                        explanation = "Medications entered: " + tableRaw.Rows[i]["medsAll"].ToString();
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = "No Medications entered";
                    }  
                    break;
                case AllergyList: 
                    if (!StringSupport.equals(tableRaw.Rows[i]["allergiesNone"].ToString(), "0"))
                    {
                        explanation = "Allergies indicated 'None'";
                        row["met"] = "X";
                    }
                    else if (!StringSupport.equals(tableRaw.Rows[i]["allergiesAll"].ToString(), "0"))
                    {
                        explanation = "Allergies entered: " + tableRaw.Rows[i]["allergiesAll"].ToString();
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = "No Allergies entered";
                    }  
                    break;
                case Demographics: 
                    if (PIn.Date(tableRaw.Rows[i]["Birthdate"].ToString()).Year < 1880)
                    {
                        explanation += "birthdate";
                    }
                     
                    //missing
                    if (StringSupport.equals(tableRaw.Rows[i]["Language"].ToString(), ""))
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "language";
                    }
                     
                    if (PIn.Int(tableRaw.Rows[i]["Gender"].ToString()) == ((Enum)PatientGender.Unknown).ordinal())
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "gender";
                    }
                     
                    //if(PatientRaces.GetForPatient(PIn.Long(row["PatNum"].ToString())).Count==0) {
                    //	if(explanation!="") {
                    //		explanation+=", ";
                    //	}
                    //	explanation+="race, ethnicity";
                    //}
                    if (PIn.Int(tableRaw.Rows[i]["HasRace"].ToString()) == 0)
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "race";
                    }
                     
                    if (PIn.Int(tableRaw.Rows[i]["HasEthnicity"].ToString()) == 0)
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "ethnicity";
                    }
                     
                    if (StringSupport.equals(explanation, ""))
                    {
                        explanation = "All demographic elements recorded";
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = "Missing: " + explanation;
                    } 
                    break;
                case Education: 
                    if (StringSupport.equals(tableRaw.Rows[i]["edCount"].ToString(), "0"))
                    {
                        explanation = "No education resources";
                    }
                    else
                    {
                        explanation = "Education resources provided";
                        row["met"] = "X";
                    } 
                    break;
                case TimelyAccess: 
                    DateTime lastVisitDate = PIn.Date(tableRaw.Rows[i]["lastVisitDate"].ToString());
                    DateTime deadlineDate = PIn.Date(tableRaw.Rows[i]["deadlineDate"].ToString());
                    if (StringSupport.equals(tableRaw.Rows[i]["accessProvided"].ToString(), "0"))
                    {
                        explanation = lastVisitDate.ToShortDateString() + " no online access provided";
                    }
                    else
                    {
                        explanation = "Online access provided before " + deadlineDate.ToShortDateString();
                        row["met"] = "X";
                    } 
                    break;
                case ProvOrderEntry: 
                case CPOE_PreviouslyOrdered: 
                    if (StringSupport.equals(tableRaw.Rows[i]["countCpoe"].ToString(), "0"))
                    {
                        explanation = "No medication order through CPOE";
                    }
                    else
                    {
                        explanation = "Medication order in CPOE";
                        row["met"] = "X";
                    } 
                    break;
                case CPOE_MedOrdersOnly: 
                    DateTime medOrderStartDate = PIn.Date(tableRaw.Rows[i]["DateStart"].ToString());
                    explanation = "Medication order: " + tableRaw.Rows[i]["MedName"].ToString() + ", start date: " + medOrderStartDate.ToShortDateString() + ".";
                    if (StringSupport.equals(tableRaw.Rows[i]["IsCpoe"].ToString(), "1"))
                    {
                        row["met"] = "X";
                    }
                     
                    break;
                case Rx: 
                    RxSendStatus sendStatus = (RxSendStatus)PIn.Int(tableRaw.Rows[i]["SendStatus"].ToString());
                    DateTime rxDate = PIn.Date(tableRaw.Rows[i]["rxDate"].ToString());
                    if (sendStatus == RxSendStatus.SentElect)
                    {
                        explanation = rxDate.ToShortDateString() + " Rx sent electronically.";
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = rxDate.ToShortDateString() + " Rx not sent electronically.";
                    } 
                    break;
                case VitalSigns: 
                    if (StringSupport.equals(tableRaw.Rows[i]["hwCount"].ToString(), "0"))
                    {
                        explanation += "height, weight";
                    }
                     
                    if (StringSupport.equals(tableRaw.Rows[i]["bpCount"].ToString(), "0"))
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "blood pressure";
                    }
                     
                    if (StringSupport.equals(explanation, ""))
                    {
                        explanation = "Vital signs entered";
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = "Missing: " + explanation;
                    } 
                    break;
                case VitalSigns2014: 
                    if (StringSupport.equals(tableRaw.Rows[i]["hwCount"].ToString(), "0"))
                    {
                        explanation += "height, weight";
                    }
                     
                    if (StringSupport.equals(tableRaw.Rows[i]["bpCount"].ToString(), "0"))
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "blood pressure";
                    }
                     
                    if (StringSupport.equals(explanation, ""))
                    {
                        explanation = "Vital signs entered";
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = "Missing: " + explanation;
                    } 
                    break;
                case VitalSignsBMIOnly: 
                    if (StringSupport.equals(tableRaw.Rows[i]["hwCount"].ToString(), "0"))
                    {
                        explanation += "height, weight";
                    }
                     
                    if (StringSupport.equals(explanation, ""))
                    {
                        explanation = "Vital signs entered";
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = "Missing: " + explanation;
                    } 
                    break;
                case VitalSignsBPOnly: 
                    if (StringSupport.equals(tableRaw.Rows[i]["bpCount"].ToString(), "0"))
                    {
                        explanation = "Missing: blood pressure";
                    }
                    else
                    {
                        explanation = "Vital signs entered";
                        row["met"] = "X";
                    } 
                    break;
                case Smoking: 
                    String smokeSnoMed = tableRaw.Rows[i]["SmokingSnoMed"].ToString();
                    if (StringSupport.equals(smokeSnoMed, ""))
                    {
                        //None
                        explanation += "Smoking status not entered.";
                    }
                    else
                    {
                        explanation = "Smoking status entered.";
                        row["met"] = "X";
                    } 
                    break;
                case Lab: 
                    int resultCount = PIn.Int(tableRaw.Rows[i]["ResultCount"].ToString());
                    boolean isOldLab = PIn.Bool(tableRaw.Rows[i]["IsOldLab"].ToString());
                    DateTime dateOrder = PIn.Date(tableRaw.Rows[i]["DateTimeOrder"].ToString());
                    if (resultCount == 0)
                    {
                        explanation += dateOrder.ToShortDateString() + " results not attached.";
                        explanation += isOldLab ? " (2011 edition)" : "";
                    }
                    else
                    {
                        explanation = dateOrder.ToShortDateString() + " results attached.";
                        explanation += isOldLab ? " (2011 edition)" : "";
                        row["met"] = "X";
                    } 
                    break;
                case ElectronicCopy: 
                    DateTime dateRequested = PIn.Date(tableRaw.Rows[i]["dateRequested"].ToString());
                    if (StringSupport.equals(tableRaw.Rows[i]["copyProvided"].ToString(), "0"))
                    {
                        explanation = dateRequested.ToShortDateString() + " no copy provided to patient";
                    }
                    else
                    {
                        explanation = dateRequested.ToShortDateString() + " copy provided to patient";
                        row["met"] = "X";
                    } 
                    break;
                case ClinicalSummaries: 
                    DateTime visitDate = PIn.Date(tableRaw.Rows[i]["visitDate"].ToString());
                    if (StringSupport.equals(tableRaw.Rows[i]["summaryProvided"].ToString(), "0"))
                    {
                        explanation = visitDate.ToShortDateString() + " no summary provided to patient";
                    }
                    else
                    {
                        explanation = visitDate.ToShortDateString() + " summary provided to patient";
                        row["met"] = "X";
                    } 
                    break;
                case Reminders: 
                    if (StringSupport.equals(tableRaw.Rows[i]["reminderCount"].ToString(), "0"))
                    {
                        explanation = "No reminders sent";
                    }
                    else
                    {
                        explanation = "Reminders sent";
                        row["met"] = "X";
                    } 
                    break;
                case MedReconcile: 
                    int refCount = PIn.Int(tableRaw.Rows[i]["RefCount"].ToString());
                    //this will always be greater than zero
                    int reconcileCount = PIn.Int(tableRaw.Rows[i]["ReconcileCount"].ToString());
                    if (reconcileCount < refCount)
                    {
                        explanation = "Transitions of Care:" + refCount.ToString() + ", Reconciles:" + reconcileCount.ToString();
                    }
                    else
                    {
                        explanation = "Reconciles performed for each transition of care.";
                        row["met"] = "X";
                    } 
                    break;
                case SummaryOfCare: 
                    int socSent = PIn.Int(tableRaw.Rows[i]["SOCSent"].ToString());
                    DateTime refDate = PIn.DateT(tableRaw.Rows[i]["RefDate"].ToString());
                    String refLName = PIn.String(tableRaw.Rows[i]["RefLName"].ToString());
                    String refFName = PIn.String(tableRaw.Rows[i]["RefFName"].ToString());
                    if (socSent < 1)
                    {
                        explanation = "Referral on: " + refDate.ToShortDateString() + " to " + refLName + ", " + refFName + " not sent summary of care.";
                    }
                    else
                    {
                        explanation = "Referral on: " + refDate.ToShortDateString() + " to " + refLName + ", " + refFName + " sent summary of care.";
                        row["met"] = "X";
                    } 
                    break;
                default: 
                    throw new ApplicationException("Type not found: " + mtype.ToString());
            
            }
            row["explanation"] = explanation;
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * Just counts up the number of rows with an X in the met column.  Very simple.
    */
    public static int calcNumerator(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        int retVal = 0;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (StringSupport.equals(table.Rows[i]["met"].ToString(), "X"))
            {
                retVal++;
            }
             
        }
        return retVal;
    }

    /**
    * Returns the explanation of the numerator based on the EHR certification documents.
    */
    private static String getNumeratorExplain(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case ProblemList: 
                return "Patients with at least one problem list entry or an indication of 'None' on problem list.";
            case MedicationList: 
                return "Patients with at least one medication list entry or an indication of 'None' on medication list.";
            case AllergyList: 
                return "Patients with at least one allergy list entry or an indication of 'None' on allergy list.";
            case Demographics: 
                return "Patients with all required demographic elements recorded as structured data: language, gender, race, ethnicity, and birthdate.";
            case Education: 
                return "Patients provided patient-specific education resources, not dependent on requests.";
            case TimelyAccess: 
                return "Electronic access of health information provided to seen patients within 4 business days of being entered into their EHR, not dependent on requests.";
            case ProvOrderEntry: 
                return "Patients with a medication order entered using CPOE.";
            case CPOE_MedOrdersOnly: 
                return "The number of medication orders entered by the Provider during the reporting period using CPOE.";
            case CPOE_PreviouslyOrdered: 
                return "Patients with a medication order entered using CPOE.";
            case Rx: 
                return "Permissible prescriptions transmitted electronically.";
            case VitalSigns: 
                return "Patients with height, weight, and blood pressure recorded.";
            case VitalSigns2014: 
                return "Patients with height, weight, and blood pressure recorded.";
            case VitalSignsBMIOnly: 
                return "Patients with height and weight recorded.";
            case VitalSignsBPOnly: 
                return "Patients with blood pressure recorded.";
            case Smoking: 
                return "Patients with smoking status recorded.";
            case Lab: 
                return "Lab results entered.";
            case ElectronicCopy: 
                return "Electronic copy received within 3 business days.";
            case ClinicalSummaries: 
                return "Clinical summaries of office visits provided to patients within 3 business days, not dependent on requests.";
            case Reminders: 
                return "Appropriate reminders sent during the reporting period.";
            case MedReconcile: 
                return "Medication reconciliation was performed for each transition of care.";
            case SummaryOfCare: 
                return "Summary of care record was provided for each transition or referral.";
        
        }
        throw new ApplicationException("Type not found: " + mtype.ToString());
    }

    //Leaving original wording so change will not require re-testing to meet 2011 certification.  The wording below may be used in 2014 MU 1 as it is more accurate.
    //return "Patients with at least one problem entered with an ICD-9 code or SNOMED code attached or an indication of 'None' in their problem list.";
    /**
    * Returns the explanation of the denominator based on the EHR certification documents.
    */
    private static String getDenominatorExplain(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case ProblemList: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case MedicationList: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case AllergyList: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case Demographics: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case Education: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case TimelyAccess: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case ProvOrderEntry: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period and with at least one medication in their medication list.";
            case CPOE_MedOrdersOnly: 
                return "The number of medication orders created by the Provider during the reporting period.";
            case CPOE_PreviouslyOrdered: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period, with at least one medication in their medication list, and for whom the Provider has previously ordered medications.";
            case Rx: 
                return "All permissible prescriptions by the Provider during the reporting period.";
            case VitalSigns: 
                return "All unique patients (age 3 and over for blood pressure) with at least one completed procedure by the Provider during the reporting period.";
            case VitalSigns2014: 
                return "All unique patients (age 3 and over for blood pressure) with at least one completed procedure by the Provider during the reporting period.";
            case VitalSignsBMIOnly: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case VitalSignsBPOnly: 
                return "All unique patients age 3 and over with at least one completed procedure by the Provider during the reporting period.";
            case Smoking: 
                return "All unique patients 13 years or older with at least one completed procedure by the Provider during the reporting period.";
            case Lab: 
                return "All lab orders by the Provider during the reporting period.";
            case ElectronicCopy: 
                return "All requests for electronic copies of health information during the reporting period.";
            case ClinicalSummaries: 
                return "All office visits during the reporting period.  An office visit is calculated as any number of completed procedures by the Provider for a given date.";
            case Reminders: 
                return "All unique patients of the Provider 65+ or 5-.  Must have status of Patient rather than Inactive, Nonpatient, Deceased, etc.";
            case MedReconcile: 
                return "Number of incoming transitions of care from another provider during the reporting period.";
            case SummaryOfCare: 
                return "Number of outgoing transitions of care and referrals during the reporting period.";
        
        }
        throw new ApplicationException("Type not found: " + mtype.ToString());
    }

    //return "All unique patients of the Provider 65+ or 5-.  Not restricted to those seen during the reporting period.  Must have status of Patient rather than Inactive, Nonpatient, Deceased, etc.";
    /**
    * Returns the explanation of the exclusion if there is one, if none returns 'No exclusions.'.
    */
    private static String getExclusionExplain(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case ProblemList: 
            case MedicationList: 
            case AllergyList: 
            case Demographics: 
            case Education: 
                return "No exclusions.";
            case TimelyAccess: 
                return "Any Provider that neither orders nor creates lab tests or information that would be contained in the problem list, medication list, or medication allergy list during the reporting period.";
            case ProvOrderEntry: 
            case CPOE_MedOrdersOnly: 
            case CPOE_PreviouslyOrdered: 
                return "Any Provider who writes fewer than 100 prescriptions during the reporting period.";
            case Rx: 
                return "1. Any Provider who writes fewer than 100 prescriptions during the reporting period.\r\n" + 
                "2. Any Provider who does not have a pharmacy within their organization and there are no pharmacies that accept electronic prescriptions within 10 miles of the practice at the start of the reporting period.";
            case VitalSigns: 
                return "1. Any Provider who sees no patients 3 years or older is excluded from recording blood pressure.\r\n" + 
                "2. Any Provider who believes that all three vital signs of height, weight, and blood pressure have no relevance to their scope of practice is excluded from recording them.\r\n" + 
                "3. Any Provider who believes that height and weight are relevant to their scope of practice, but blood pressure is not, is excluded from recording blood pressure.\r\n" + 
                "4. Any Provider who believes that blood pressure is relevant to their scope of practice, but height and weight are not, is excluded from recording height and weight.";
            case VitalSigns2014: 
                return "1. Any Provider who sees no patients 3 years or older is excluded from recording blood pressure.\r\n" + 
                "2. Any Provider who believes that all three vital signs of height, weight, and blood pressure have no relevance to their scope of practice is excluded from recording them.\r\n" + 
                "3. Any Provider who believes that height and weight are relevant to their scope of practice, but blood pressure is not, is excluded from recording blood pressure.\r\n" + 
                "4. Any Provider who believes that blood pressure is relevant to their scope of practice, but height and weight are not, is excluded from recording height and weight.";
            case VitalSignsBMIOnly: 
                return "Any Provider who believes that height and weight are not relevant to their scope of practice is excluded from recording them.";
            case VitalSignsBPOnly: 
                return "1. Any Provider who sees no patients 3 years or older is excluded from recording blood pressure.\r\n" + 
                "2. Any Provider who believes that blood pressure is not relevant to their scope of practice is excluded from recording it.";
            case Smoking: 
                return "Any Provider who sees no patients 13 years or older during the reporting period.";
            case Lab: 
                return "Any Provider who orders no lab tests whose results are either in a positive/negative or numeric format during the reporting period.";
            case ElectronicCopy: 
                return "Any Provider who has no requests from patients or their agents for an electronic copy of patient health information during the reporting period.";
            case ClinicalSummaries: 
                return "Any Provider who has no completed procedures during the reporting period.";
            case Reminders: 
                return "Any Provider who has no patients 65 years or older or 5 years or younger.";
            case MedReconcile: 
                return "Any Provider who was not the recipient of any transitions of care during the reporting period.";
            case SummaryOfCare: 
                return "Any Provider who neither transfers a patient to another setting nor refers a patient to another provider during the reporting period.";
        
        }
        throw new ApplicationException("Type not found: " + mtype.ToString());
    }

    /**
    * Returns the count the office will need to report in order to attest to being excluded from this measure.  Will return -1 if there is no applicable count for this measure.
    */
    private static int getExclusionCount(EhrMeasureType mtype, DateTime dateStart, DateTime dateEnd, long provNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), mtype);
        }
         
        int retval = 0;
        String command = "";
        DataTable tableRaw = new DataTable();
        command = "SELECT GROUP_CONCAT(provider.ProvNum) FROM provider WHERE provider.EhrKey=" + "(SELECT pv.EhrKey FROM provider pv WHERE pv.ProvNum=" + POut.long(provNum) + ")";
        String provs = Db.getScalar(command);
        switch(mtype)
        {
            case ProblemList: 
            case MedicationList: 
            case AllergyList: 
            case Demographics: 
            case Education: 
            case VitalSignsBMIOnly: 
            case ElectronicCopy: 
            case Lab: 
            case MedReconcile: 
            case SummaryOfCare: 
                return retval = -1;
            case TimelyAccess: 
                //Exlcuded if no lab tests are ordered or created for patients seen in reporting period
                command = "SELECT COUNT(*) AS 'Count' " + "FROM (SELECT patient.PatNum	FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum	AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A " + "INNER JOIN medicalorder ON A.PatNum=medicalorder.PatNum " + "AND MedOrderType=" + POut.int(((Enum)MedicalOrderType.Laboratory).ordinal()) + " " + "AND medicalorder.ProvNum IN(" + POut.string(provs) + ") " + "AND DATE(DateTimeOrder) BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " ";
                retval += PIn.int(Db.getCount(command));
                //Excluded if problems, medications, or medication allergy information is not ordered or created for patients seen in the reporting period
                command = "SELECT SUM(COALESCE(allergies.Count,0)+COALESCE(problems.Count,0)+COALESCE(meds.Count,0)) AS 'Count' " + "FROM (SELECT patient.PatNum	FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum	AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A ";
                //left join allergies with DateTStamp within reporting period
                command += "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM allergy " + "WHERE " + DbHelper.dateColumn("DateTStamp") + " BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY PatNum) allergies ON allergies.PatNum=A.PatNum ";
                //left join problems with DateTStamp within reporting period
                command += "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM disease " + "WHERE " + DbHelper.dateColumn("DateTStamp") + " BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY PatNum) problems ON problems.PatNum=A.PatNum ";
                //left join medications with DateStart or DateTStamp within reporting period
                command += "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM medicationpat " + "WHERE DateStart BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "OR " + DbHelper.dateColumn("DateTStamp") + " BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY PatNum) meds ON meds.PatNum=A.PatNum";
                return retval += PIn.int(Db.getScalar(command));
            case ProvOrderEntry: 
            case CPOE_MedOrdersOnly: 
            case CPOE_PreviouslyOrdered: 
            case Rx: 
                //Excluded if Provider writes fewer than 100 Tx's during the reporting period
                command = "SELECT COUNT(DISTINCT rxpat.RxNum) AS 'Count' " + "FROM patient " + "INNER JOIN rxpat ON rxpat.PatNum=patient.PatNum " + "AND rxpat.ProvNum IN(" + POut.string(provs) + ")	" + "AND RxDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd);
                return retval = PIn.int(Db.getScalar(command));
            case VitalSigns: 
            case VitalSigns2014: 
            case VitalSignsBPOnly: 
                //Excluded if Provider sees no patients 3 years or older at the time of their last visit in reporting period.
                command = "SELECT SUM((CASE WHEN A.Birthdate <= (A.LastVisitInDateRange-INTERVAL 3 YEAR) THEN 1 ELSE 0 END)) AS 'Count' " + "FROM (SELECT Birthdate,MAX(procedurelog.ProcDate) AS LastVisitInDateRange " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A ";
                return retval = PIn.int(Db.getScalar(command));
            case Smoking: 
                //Excluded if Provider sees no patients 13 years or older at the time of their last visit in reporting period.
                command = "SELECT SUM((CASE WHEN A.Birthdate <= (A.LastVisitInDateRange-INTERVAL 13 YEAR) THEN 1 ELSE 0 END)) AS 'Count' " + "FROM (SELECT Birthdate,MAX(procedurelog.ProcDate) AS LastVisitInDateRange " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A ";
                return retval = PIn.int(Db.getScalar(command));
            case ClinicalSummaries: 
                //Excluded if no completed procedures during the reporting period
                command = "SELECT COUNT(DISTINCT ProcNum) FROM procedurelog " + "WHERE ProcStatus=2 AND ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " ";
                return retval = PIn.int(Db.getScalar(command));
            case Reminders: 
                //Excluded if Provider sees no patients 65 years or older or 5 years or younger at the time of their last visit in reporting period.
                command = "SELECT SUM((CASE WHEN (A.Birthdate > (A.LastVisitInDateRange-INTERVAL 6 YEAR) ";
                //6th birthday had not happened by date of last visit, 5 years or younger
                command += "OR A.Birthdate <= (A.LastVisitInDateRange-INTERVAL 65 YEAR)) ";
                //had 65th birthday by date of last vist, 65 or older
                command += "THEN 1 ELSE 0 END)) AS 'Count' " + "FROM (SELECT Birthdate,MAX(procedurelog.ProcDate) AS LastVisitInDateRange " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A ";
                return retval = PIn.int(Db.getScalar(command));
        
        }
        throw new ApplicationException("Type not found: " + mtype.ToString());
    }

    /**
    * Returns the description of what the count displayed is.  May be count of patients under a certain age or number of Rx's written, this will be the label that describes the number.
    */
    private static String getExclusionCountDescript(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case ProblemList: 
            case MedicationList: 
            case AllergyList: 
            case Demographics: 
            case Education: 
            case VitalSignsBMIOnly: 
            case ElectronicCopy: 
            case Lab: 
            case MedReconcile: 
            case SummaryOfCare: 
                return "";
            case TimelyAccess: 
                return "Count of lab orders, problems, medications, and medication allergies entered during the reporting period.";
            case ProvOrderEntry: 
            case CPOE_MedOrdersOnly: 
            case CPOE_PreviouslyOrdered: 
            case Rx: 
                return "Count of prescriptions entered during the reporting period.";
            case VitalSigns: 
            case VitalSigns2014: 
            case VitalSignsBPOnly: 
                return "Count of patients seen who were 3 years or older at the time of their last visit during the reporting period.";
            case Smoking: 
                return "Count of patients seen who were 13 years or older at the time of their last visit during the reporting period.";
            case ClinicalSummaries: 
                return "Count of procedures completed during the reporting period.";
            case Reminders: 
                return "Count of patients 65 years or older or 5 years or younger at the time of their last visit during the reporting period.";
        
        }
        throw new ApplicationException("Type not found: " + mtype.ToString());
    }

    /**
    * Only called from FormEHR to load the patient specific MU data and tell the user what action to take to get closer to meeting MU.
    */
    public static List<EhrMu> getMu(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrMu>>GetObject(MethodBase.GetCurrentMethod(), pat);
        }
         
        List<EhrMu> list = new List<EhrMu>();
        //add one of each type
        EhrMu mu;
        String explanation = new String();
        List<EhrMeasure> retVal = getMUList();
        List<MedicationPat> medList = MedicationPats.refresh(pat.PatNum,true);
        List<EhrMeasureEvent> listMeasureEvents = EhrMeasureEvents.refresh(pat.PatNum);
        List<RefAttach> listRefAttach = RefAttaches.refresh(pat.PatNum);
        for (int i = 0;i < retVal.Count;i++)
        {
            mu = new EhrMu();
            mu.Met = MuMet.False;
            mu.MeasureType = retVal[i].MeasureType;
            switch(mu.MeasureType)
            {
                case ProblemList: 
                    List<Disease> listDisease = Diseases.refresh(pat.PatNum);
                    int validDiseaseCount = 0;
                    if (listDisease.Count == 0)
                    {
                        mu.Details = "No problems entered.";
                    }
                    else
                    {
                        boolean diseasesNone = false;
                        if (listDisease.Count == 1 && listDisease[0].DiseaseDefNum == PrefC.getLong(PrefName.ProblemsIndicateNone))
                        {
                            diseasesNone = true;
                        }
                         
                        if (diseasesNone)
                        {
                            mu.Met = MuMet.True;
                            mu.Details = "Problems marked 'none'.";
                        }
                        else
                        {
                            for (int m = 0;m < listDisease.Count;m++)
                            {
                                DiseaseDef diseaseCur = DiseaseDefs.GetItem(listDisease[m].DiseaseDefNum);
                                if (StringSupport.equals(diseaseCur.ICD9Code, "") && StringSupport.equals(diseaseCur.SnomedCode, ""))
                                {
                                    continue;
                                }
                                 
                                validDiseaseCount++;
                            }
                            if (validDiseaseCount == 0)
                            {
                                mu.Details = "No problems with ICD-9 or Snomed code entered.";
                            }
                            else
                            {
                                mu.Met = MuMet.True;
                                mu.Details = "Problems with ICD-9 or Snomed code entered: " + validDiseaseCount.ToString();
                            } 
                        } 
                    } 
                    mu.Action = "Enter problems";
                    break;
                case MedicationList: 
                    if (medList.Count == 0)
                    {
                        mu.Details = "No medications entered.";
                    }
                    else
                    {
                        mu.Met = MuMet.True;
                        boolean medsNone = false;
                        if (medList.Count == 1 && medList[0].MedicationNum == PrefC.getLong(PrefName.MedicationsIndicateNone))
                        {
                            medsNone = true;
                        }
                         
                        if (medsNone)
                        {
                            mu.Details = "Medications marked 'none'.";
                        }
                        else
                        {
                            mu.Details = "Medications entered: " + medList.Count.ToString();
                        } 
                    } 
                    mu.Action = "Enter medications";
                    break;
                case AllergyList: 
                    List<Allergy> listAllergies = Allergies.refresh(pat.PatNum);
                    if (listAllergies.Count == 0)
                    {
                        mu.Details = "No allergies entered.";
                    }
                    else
                    {
                        mu.Met = MuMet.True;
                        boolean allergiesNone = false;
                        if (listAllergies.Count == 1 && listAllergies[0].AllergyDefNum == PrefC.getLong(PrefName.AllergiesIndicateNone))
                        {
                            allergiesNone = true;
                        }
                         
                        if (allergiesNone)
                        {
                            mu.Details = "Allergies marked 'none'.";
                        }
                        else
                        {
                            mu.Details = "Allergies entered: " + listAllergies.Count.ToString();
                        } 
                    } 
                    mu.Action = "Enter allergies";
                    break;
                case Demographics: 
                    explanation = "";
                    if (pat.Birthdate.Year < 1880)
                    {
                        explanation += "birthdate";
                    }
                     
                    //missing
                    if (StringSupport.equals(pat.Language, ""))
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "language";
                    }
                     
                    if (pat.Gender == PatientGender.Unknown)
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "gender";
                    }
                     
                    if (PatientRaces.getForPatient(pat.PatNum).Count == 0)
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "race, ethnicity";
                    }
                     
                    if (StringSupport.equals(explanation, ""))
                    {
                        mu.Details = "All demographic elements recorded";
                        mu.Met = MuMet.True;
                    }
                    else
                    {
                        mu.Details = "Missing: " + explanation;
                    } 
                    mu.Action = "Enter demographics";
                    break;
                case Education: 
                    List<EhrMeasureEvent> listEd = EhrMeasureEvents.refreshByType(pat.PatNum,EhrMeasureEventType.EducationProvided);
                    if (listEd.Count == 0)
                    {
                        mu.Details = "No education resources provided.";
                    }
                    else
                    {
                        mu.Details = "Education resources provided: " + listEd.Count.ToString();
                        mu.Met = MuMet.True;
                    } 
                    mu.Action = "Provide education resources";
                    break;
                case TimelyAccess: 
                    List<EhrMeasureEvent> listOnline = EhrMeasureEvents.refreshByType(pat.PatNum,EhrMeasureEventType.OnlineAccessProvided);
                    if (listOnline.Count == 0)
                    {
                        mu.Details = "No online access provided.";
                    }
                    else
                    {
                        mu.Details = "Online access provided: " + listOnline[listOnline.Count - 1].DateTEvent.ToShortDateString();
                        //most recent
                        mu.Met = MuMet.True;
                    } 
                    mu.Action = "Provide online Access";
                    break;
                case ProvOrderEntry: 
                    //int medOrderCount=0;
                    int medOrderCpoeCount = 0;
                    for (int mo = 0;mo < medList.Count;mo++)
                    {
                        //if(medList[mo].DateStart.Year>1880 && medList[mo].PatNote!=""){
                        if (medList[mo].IsCpoe)
                        {
                            medOrderCpoeCount++;
                        }
                         
                    }
                    if (medList.Count == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No meds.";
                    }
                    else if (medOrderCpoeCount == 0)
                    {
                        mu.Details = "No medication order in CPOE.";
                    }
                    else
                    {
                        mu.Details = "Medications entered in CPOE: " + medOrderCpoeCount.ToString();
                        mu.Met = MuMet.True;
                    }  
                    mu.Action = "(edit Rxs from Chart)";
                    break;
                case CPOE_MedOrdersOnly: 
                    int medOrderCount = 0;
                    medOrderCpoeCount = 0;
                    for (int m = 0;m < medList.Count;m++)
                    {
                        //Using the last year as the reporting period, following pattern in ElectronicCopy, ClinicalSummaries, Reminders...
                        if (medList[m].DateStart < DateTime.Now.AddYears(-1))
                        {
                            continue;
                        }
                        else //either no start date so not an order, or not within the last year so not during the reporting period
                        if (!StringSupport.equals(medList[m].PatNote, "") && medList[m].ProvNum == pat.PriProv)
                        {
                            //if there's a note and it was created by the patient's PriProv, then count as order created by this provider and would count toward the denominator for MU
                            medOrderCount++;
                            if (medList[m].IsCpoe)
                            {
                                //if also marked as CPOE, then this would count in the numerator of the calculation MU
                                medOrderCpoeCount++;
                            }
                             
                        }
                          
                    }
                    if (medOrderCount == 0)
                    {
                        mu.Details = "No medication order in CPOE.";
                    }
                    else
                    {
                        mu.Details = "Medications entered in CPOE: " + medOrderCount.ToString();
                        mu.Met = MuMet.True;
                    } 
                    mu.Action = "(edit Rxs from Chart)";
                    break;
                case CPOE_PreviouslyOrdered: 
                    //first determine if this patient has ever had a medication ordered by this Provider
                    boolean prevOrderExists = false;
                    for (int m = 0;m < medList.Count;m++)
                    {
                        //if this is an order (defined as having instructions and a start date) and was entered by this provider, then this pat will be counted in the denominator
                        if (!StringSupport.equals(medList[m].PatNote, "") && medList[m].DateStart.Year > 1880 && medList[m].ProvNum == pat.PriProv)
                        {
                            prevOrderExists = true;
                            break;
                        }
                         
                    }
                    medOrderCpoeCount = 0;
                    for (int mo = 0;mo < medList.Count;mo++)
                    {
                        if (medList[mo].IsCpoe)
                        {
                            medOrderCpoeCount++;
                        }
                         
                    }
                    if (medList.Count == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No meds.";
                    }
                    else if (!prevOrderExists)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No previous medication orders by this Provider.";
                    }
                    else if (medOrderCpoeCount == 0)
                    {
                        mu.Details = "No medication order in CPOE.";
                    }
                    else
                    {
                        mu.Details = "Medications entered in CPOE: " + medOrderCpoeCount.ToString();
                        mu.Met = MuMet.True;
                    }   
                    mu.Action = "(edit Rxs from Chart)";
                    break;
                case Rx: 
                    List<RxPat> listRx = RxPats.GetPermissableForDateRange(pat.PatNum, DateTime.Today.AddYears(-1), DateTime.Today);
                    if (listRx.Count == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No Rxs entered.";
                    }
                    else
                    {
                        explanation = "";
                        for (int rx = 0;rx < listRx.Count;rx++)
                        {
                            if (listRx[rx].SendStatus == RxSendStatus.SentElect)
                            {
                                continue;
                            }
                             
                            if (!StringSupport.equals(explanation, ""))
                            {
                                explanation += ", ";
                            }
                             
                            explanation += listRx[rx].RxDate.ToShortDateString();
                        }
                        if (StringSupport.equals(explanation, ""))
                        {
                            mu.Met = MuMet.True;
                            mu.Details = "All Rxs sent electronically.";
                        }
                        else
                        {
                            mu.Met = MuMet.False;
                            mu.Details = "Rxs not sent electronically: " + explanation;
                        } 
                    } 
                    mu.Action = "(edit Rxs from Chart)";
                    break;
                case VitalSigns: 
                    //no action
                    List<Vitalsign> vitalsignList = Vitalsigns.refresh(pat.PatNum);
                    if (vitalsignList.Count == 0)
                    {
                        mu.Details = "No vital signs entered.";
                    }
                    else
                    {
                        boolean hFound = false;
                        boolean wFound = false;
                        boolean bpFound = false;
                        for (int v = 0;v < vitalsignList.Count;v++)
                        {
                            if (vitalsignList[v].Height > 0)
                            {
                                hFound = true;
                            }
                             
                            if (vitalsignList[v].Weight > 0)
                            {
                                wFound = true;
                            }
                             
                            //3 and older for BP
                            if (pat.Birthdate > DateTime.Today.AddYears(-3) || (vitalsignList[v].BpDiastolic > 0 && vitalsignList[v].BpSystolic > 0))
                            {
                                bpFound = true;
                            }
                             
                        }
                        explanation = "";
                        if (!hFound)
                        {
                            explanation += "height";
                        }
                         
                        //missing
                        if (!wFound)
                        {
                            if (!StringSupport.equals(explanation, ""))
                            {
                                explanation += ", ";
                            }
                             
                            explanation += "weight";
                        }
                         
                        if (!bpFound)
                        {
                            if (!StringSupport.equals(explanation, ""))
                            {
                                explanation += ", ";
                            }
                             
                            explanation += "blood pressure";
                        }
                         
                        if (StringSupport.equals(explanation, ""))
                        {
                            mu.Details = "Vital signs entered";
                            mu.Met = MuMet.True;
                        }
                        else
                        {
                            mu.Details = "Missing: " + explanation;
                        } 
                    } 
                    mu.Action = "Enter vital signs";
                    break;
                case VitalSigns2014: 
                    List<Vitalsign> vitalsignList2014 = Vitalsigns.refresh(pat.PatNum);
                    if (vitalsignList2014.Count == 0)
                    {
                        mu.Details = "No vital signs entered.";
                    }
                    else
                    {
                        boolean hFound = false;
                        boolean wFound = false;
                        boolean bpFound = false;
                        for (int v = 0;v < vitalsignList2014.Count;v++)
                        {
                            if (vitalsignList2014[v].Height > 0)
                            {
                                hFound = true;
                            }
                             
                            if (vitalsignList2014[v].Weight > 0)
                            {
                                wFound = true;
                            }
                             
                            //3 and older for BP
                            if (pat.Birthdate > DateTime.Today.AddYears(-3) || (vitalsignList2014[v].BpDiastolic > 0 && vitalsignList2014[v].BpSystolic > 0))
                            {
                                bpFound = true;
                            }
                             
                        }
                        explanation = "";
                        if (!hFound)
                        {
                            explanation += "height";
                        }
                         
                        //missing
                        if (!wFound)
                        {
                            if (!StringSupport.equals(explanation, ""))
                            {
                                explanation += ", ";
                            }
                             
                            explanation += "weight";
                        }
                         
                        if (!bpFound)
                        {
                            if (!StringSupport.equals(explanation, ""))
                            {
                                explanation += ", ";
                            }
                             
                            explanation += "blood pressure";
                        }
                         
                        if (StringSupport.equals(explanation, ""))
                        {
                            mu.Details = "Vital signs entered";
                            mu.Met = MuMet.True;
                        }
                        else
                        {
                            mu.Details = "Missing: " + explanation;
                        } 
                    } 
                    mu.Action = "Enter vital signs";
                    break;
                case VitalSignsBMIOnly: 
                    vitalsignList = Vitalsigns.refresh(pat.PatNum);
                    if (vitalsignList.Count == 0)
                    {
                        mu.Details = "No vital signs entered.";
                    }
                    else
                    {
                        boolean hFound = false;
                        boolean wFound = false;
                        for (int v = 0;v < vitalsignList.Count;v++)
                        {
                            if (vitalsignList[v].Height > 0)
                            {
                                hFound = true;
                            }
                             
                            if (vitalsignList[v].Weight > 0)
                            {
                                wFound = true;
                            }
                             
                        }
                        explanation = "";
                        if (!hFound)
                        {
                            explanation += "height";
                        }
                         
                        //missing
                        if (!wFound)
                        {
                            if (!StringSupport.equals(explanation, ""))
                            {
                                explanation += ", ";
                            }
                             
                            explanation += "weight";
                        }
                         
                        if (StringSupport.equals(explanation, ""))
                        {
                            mu.Details = "Vital signs entered";
                            mu.Met = MuMet.True;
                        }
                        else
                        {
                            mu.Details = "Missing: " + explanation;
                        } 
                    } 
                    mu.Action = "Enter vital signs";
                    break;
                case VitalSignsBPOnly: 
                    vitalsignList = Vitalsigns.refresh(pat.PatNum);
                    if (pat.Birthdate > DateTime.Today.AddYears(-3))
                    {
                        //3 and older for BP
                        mu.Details = "Age 3 and older for BP.";
                        mu.Met = MuMet.NA;
                    }
                    else if (vitalsignList.Count == 0)
                    {
                        mu.Details = "No vital signs entered.";
                    }
                    else
                    {
                        for (int v = 0;v < vitalsignList.Count;v++)
                        {
                            if (vitalsignList[v].BpDiastolic > 0 && vitalsignList[v].BpSystolic > 0)
                            {
                                mu.Details = "Vital signs entered";
                                mu.Met = MuMet.True;
                            }
                            else
                            {
                                mu.Details = "Missing: blood pressure";
                            } 
                        }
                    }  
                    mu.Action = "Enter vital signs";
                    break;
                case Smoking: 
                    if (StringSupport.equals(pat.SmokingSnoMed, ""))
                    {
                        //None
                        mu.Details = "Smoking status not entered";
                    }
                    else
                    {
                        mu.Details = "Smoking status entered";
                        mu.Met = MuMet.True;
                    } 
                    mu.Action = "Edit smoking status";
                    break;
                case Lab: 
                    List<EhrLab> listLabOrders = EhrLabs.GetAllForPatInDateRange(pat.PatNum, DateTime.Today.AddYears(-1), DateTime.Today);
                    if (listLabOrders.Count == 0)
                    {
                        mu.Details = "No lab orders";
                        mu.Met = MuMet.NA;
                    }
                    else
                    {
                        int labResultCount = 0;
                        for (int lo = 0;lo < listLabOrders.Count;lo++)
                        {
                            List<EhrLabResult> listLabResults = EhrLabResults.GetForLab(listLabOrders[lo].EhrLabNum);
                            if (listLabResults.Count > 0)
                            {
                                labResultCount++;
                                continue;
                            }
                             
                        }
                        //Only need one per lab order
                        if (labResultCount < listLabOrders.Count)
                        {
                            mu.Met = MuMet.False;
                            mu.Details = "Lab orders missing results: " + (listLabOrders.Count - labResultCount).ToString();
                        }
                        else
                        {
                            mu.Details = "Lab results entered for each lab order.";
                            mu.Met = MuMet.True;
                        } 
                    } 
                    mu.Action = "Edit labs";
                    break;
                case ElectronicCopy: 
                    List<EhrMeasureEvent> listRequests = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.ElectronicCopyRequested);
                    List<EhrMeasureEvent> listRequestsPeriod = new List<EhrMeasureEvent>();
                    for (int r = 0;r < listRequests.Count;r++)
                    {
                        if (listRequests[r].DateTEvent < DateTime.Now.AddYears(-1))
                        {
                            continue;
                        }
                         
                        //not within the last year
                        listRequestsPeriod.Add(listRequests[r]);
                    }
                    if (listRequestsPeriod.Count == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No requests within the last year.";
                    }
                    else
                    {
                        int countMissingCopies = 0;
                        boolean copyProvidedinTime = new boolean();
                        List<EhrMeasureEvent> listCopiesProvided = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.ElectronicCopyProvidedToPt);
                        for (int rp = 0;rp < listRequestsPeriod.Count;rp++)
                        {
                            copyProvidedinTime = false;
                            DateTime deadlineDateCopy = listRequestsPeriod[rp].DateTEvent.Date.AddDays(3);
                            if (listRequestsPeriod[rp].DateTEvent.DayOfWeek == DayOfWeek.Wednesday || listRequestsPeriod[rp].DateTEvent.DayOfWeek == DayOfWeek.Thursday || listRequestsPeriod[rp].DateTEvent.DayOfWeek == DayOfWeek.Friday)
                            {
                                deadlineDateCopy.AddDays(2);
                            }
                             
                            for (int cp = 0;cp < listCopiesProvided.Count;cp++)
                            {
                                //add two days for the weekend
                                if (listCopiesProvided[cp].DateTEvent.Date > deadlineDateCopy)
                                {
                                    continue;
                                }
                                 
                                if (listCopiesProvided[cp].DateTEvent.Date < listRequestsPeriod[rp].DateTEvent.Date)
                                {
                                    continue;
                                }
                                 
                                copyProvidedinTime = true;
                            }
                            if (!copyProvidedinTime)
                            {
                                countMissingCopies++;
                            }
                             
                        }
                        if (countMissingCopies == 0)
                        {
                            mu.Met = MuMet.True;
                            mu.Details = "Electronic copy provided to Pt within 3 business days of each request.";
                        }
                        else
                        {
                            mu.Met = MuMet.False;
                            mu.Details = "Electronic copies not provided to Pt within 3 business days of a request:" + countMissingCopies.ToString();
                        } 
                    } 
                    mu.Action = "Provide elect copy to Pt";
                    break;
                case ClinicalSummaries: 
                    //If this text ever changes then FormEHR grid will need to change for MU1
                    List<DateTime> listVisits = new List<DateTime>();
                    //for this year
                    List<Procedure> listProcs = Procedures.refresh(pat.PatNum);
                    for (int p = 0;p < listProcs.Count;p++)
                    {
                        if (listProcs[p].ProcDate < DateTime.Now.AddYears(-1) || listProcs[p].ProcStatus != OpenDentBusiness.ProcStat.C)
                        {
                            continue;
                        }
                         
                        //not within the last year or not a completed procedure
                        if (!listVisits.Contains(listProcs[p].ProcDate))
                        {
                            listVisits.Add(listProcs[p].ProcDate);
                        }
                         
                    }
                    if (listVisits.Count == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No visits within the last year.";
                    }
                    else
                    {
                        int countMissing = 0;
                        boolean summaryProvidedinTime = new boolean();
                        List<EhrMeasureEvent> listClinSum = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.ClinicalSummaryProvidedToPt);
                        for (int p = 0;p < listVisits.Count;p++)
                        {
                            summaryProvidedinTime = false;
                            DateTime deadlineDate = listVisits[p].AddDays(3);
                            if (listVisits[p].DayOfWeek == DayOfWeek.Wednesday || listVisits[p].DayOfWeek == DayOfWeek.Thursday || listVisits[p].DayOfWeek == DayOfWeek.Friday)
                            {
                                deadlineDate = deadlineDate.AddDays(2);
                            }
                             
                            for (int r = 0;r < listClinSum.Count;r++)
                            {
                                //add two days for the weekend
                                if (listClinSum[r].DateTEvent.Date > deadlineDate)
                                {
                                    continue;
                                }
                                 
                                if (listClinSum[r].DateTEvent.Date < listVisits[p])
                                {
                                    continue;
                                }
                                 
                                summaryProvidedinTime = true;
                            }
                            if (!summaryProvidedinTime)
                            {
                                countMissing++;
                            }
                             
                        }
                        if (countMissing == 0)
                        {
                            mu.Met = MuMet.True;
                            mu.Details = "Clinical summary provided to Pt within 3 business days of each visit.";
                        }
                        else
                        {
                            mu.Met = MuMet.False;
                            mu.Details = "Clinical summaries not provided to Pt within 3 business days of a visit:" + countMissing.ToString();
                        } 
                    } 
                    mu.Action = "Send clinical summary to Pt";
                    break;
                case Reminders: 
                    if (pat.PatStatus != PatientStatus.Patient)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "Status not patient.";
                    }
                    else if (pat.getAge() == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "Age not entered.";
                    }
                    else if (pat.getAge() > 5 && pat.getAge() < 65)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "Patient age not 65+ or 5-.";
                    }
                    else
                    {
                        List<EhrMeasureEvent> listReminders = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.ReminderSent);
                        //during reporting period.
                        boolean withinLastYear = false;
                        for (int r = 0;r < listReminders.Count;r++)
                        {
                            if (listReminders[r].DateTEvent > DateTime.Now.AddYears(-1))
                            {
                                withinLastYear = true;
                            }
                             
                        }
                        if (withinLastYear)
                        {
                            mu.Details = "Reminder sent within the last year.";
                            mu.Met = MuMet.True;
                        }
                        else
                        {
                            mu.Details = "No reminders sent within the last year for patient age 65+ or 5-.";
                        } 
                    }   
                    mu.Action = "Send reminders";
                    break;
                case MedReconcile: 
                    int countFromRef = 0;
                    int countFromRefPeriod = 0;
                    for (int c = 0;c < listRefAttach.Count;c++)
                    {
                        if (listRefAttach[c].IsFrom && listRefAttach[c].IsTransitionOfCare)
                        {
                            countFromRef++;
                            if (listRefAttach[c].RefDate > DateTime.Now.AddYears(-1))
                            {
                                //within the last year
                                countFromRefPeriod++;
                            }
                             
                        }
                         
                    }
                    if (countFromRef == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "Referral 'from' not entered.";
                    }
                    else if (countFromRefPeriod == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "Referral 'from' not entered within the last year.";
                    }
                    else if (countFromRefPeriod > 0)
                    {
                        List<EhrMeasureEvent> listReconciles = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.MedicationReconcile);
                        int countReconciles = 0;
                        for (int r = 0;r < listReconciles.Count;r++)
                        {
                            //during reporting period.
                            if (listReconciles[r].DateTEvent > DateTime.Now.AddYears(-1))
                            {
                                //within the same period as the count for referrals.
                                countReconciles++;
                            }
                             
                        }
                        mu.Details = "Referrals:" + countFromRefPeriod.ToString() + ", Reconciles:" + countReconciles.ToString();
                        if (countReconciles >= countFromRefPeriod)
                        {
                            mu.Met = MuMet.True;
                        }
                         
                    }
                       
                    mu.Action = "Reconcile from received CCD";
                    mu.Action2 = "Enter Referrals";
                    break;
                case SummaryOfCare: 
                    int countToRefPeriod = 0;
                    for (int c = 0;c < listRefAttach.Count;c++)
                    {
                        if (!listRefAttach[c].IsFrom && listRefAttach[c].IsTransitionOfCare)
                        {
                            if (listRefAttach[c].RefDate > DateTime.Now.AddYears(-1))
                            {
                                //within the last year
                                countToRefPeriod++;
                            }
                             
                        }
                         
                    }
                    if (countToRefPeriod == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No outgoing transitions of care within the last year.";
                    }
                    else
                    {
                        // > 0
                        List<EhrMeasureEvent> listCcds = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.SummaryOfCareProvidedToDr);
                        int countCcds = 0;
                        for (int r = 0;r < listCcds.Count;r++)
                        {
                            //during reporting period.
                            if (listCcds[r].DateTEvent > DateTime.Now.AddYears(-1))
                            {
                                //within the same period as the count for referrals.
                                countCcds++;
                            }
                             
                        }
                        mu.Details = "Referrals:" + countToRefPeriod.ToString() + ", Summaries:" + countCcds.ToString();
                        if (countCcds >= countToRefPeriod)
                        {
                            mu.Met = MuMet.True;
                        }
                         
                    } 
                    mu.Action = "Send/Receive summary of care";
                    mu.Action2 = "Enter Referrals";
                    break;
            
            }
            list.Add(mu);
        }
        return list;
    }

    private static List<EhrMeasure> getMUList() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrMeasure>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM ehrmeasure " + "WHERE MeasureType IN (" + POut.int(((Enum)EhrMeasureType.ProblemList).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.MedicationList).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.AllergyList).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Demographics).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Education).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.TimelyAccess).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ProvOrderEntry).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.CPOE_MedOrdersOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.CPOE_PreviouslyOrdered).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Rx).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSigns).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSigns2014).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSignsBMIOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSignsBPOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Smoking).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Lab).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ElectronicCopy).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ClinicalSummaries).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Reminders).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.MedReconcile).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.SummaryOfCare).ordinal()) + ") " + "ORDER BY FIELD(MeasureType," + POut.int(((Enum)EhrMeasureType.ProblemList).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.MedicationList).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.AllergyList).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Demographics).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Education).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.TimelyAccess).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ProvOrderEntry).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.CPOE_MedOrdersOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.CPOE_PreviouslyOrdered).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Rx).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSigns).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSigns2014).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSignsBMIOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSignsBPOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Smoking).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Lab).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ElectronicCopy).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ClinicalSummaries).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Reminders).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.MedReconcile).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.SummaryOfCare).ordinal()) + ") ";
        List<EhrMeasure> retVal = Crud.EhrMeasureCrud.SelectMany(command);
        return retVal;
    }

    /**
    * Returns the Objective text based on the EHR certification documents.
    */
    private static String getObjectiveMu2(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case CPOE_MedOrdersOnly: 
                return "Use computerized provider order entry (CPOE) for medication orders directly entered by any licensed healthcare professional who can enter orders into the medical record per state, local and professional guidelines.";
            case CPOE_LabOrdersOnly: 
                return "Use computerized provider order entry (CPOE) for laboratory orders directly entered by any licensed healthcare professional who can enter orders into the medical record per state, local and professional guidelines.";
            case CPOE_RadiologyOrdersOnly: 
                return "Use computerized provider order entry (CPOE) for radiology orders directly entered by any licensed healthcare professional who can enter orders into the medical record per state, local and professional guidelines.";
            case Rx: 
                return "Generate and transmit permissible prescriptions electronically (eRx).";
            case Demographics: 
                return "Record the following demographics: preferred language, sex, race, ethnicity, date of birth.";
            case VitalSigns: 
                return "Record and chart changes in the following vital signs: height/length and weight (no age limit); blood pressure (ages 3 and over); calculate and display body mass index (BMI); and plot and display growth charts for patients 0-20 years, including BMI.";
            case VitalSignsBMIOnly: 
                return "Record and chart changes in the following vital signs: height/length and weight (no age limit); calculate and display body mass index (BMI); and plot and display growth charts for patients 0-20 years, including BMI.";
            case VitalSignsBPOnly: 
                return "Record and chart changes in the following vital signs: blood pressure (ages 3 and over).";
            case Smoking: 
                return "Record smoking status for patients 13 years old or older.";
            case ElectronicCopyAccess: 
                return "Provide patients the ability to view online, download and transmit their health information within four business days of the information being available to the EP.";
            case ElectronicCopy: 
                return "Patient's will view online, download or transmit their health information within four business days of the information being available to the EP.";
            case ClinicalSummaries: 
                return "Provide clinical summaries for patients for each office visit.";
            case Lab: 
                return "Incorporate clinical lab-test results into Certified EHR Technology (CEHRT) as structured data.";
            case Reminders: 
                return "Use clinically relevant information to identify patients who should receive reminders for preventive/follow-up care and send these patients the reminders, per patient preference.";
            case Education: 
                return "Use clinically relevant information from Certified EHR Technology to identify patient-specific education resources and provide those resources to the patient.";
            case MedReconcile: 
                return "The EP who receives a patient from another setting of care or provider of care or believes an encounter is relevant should perform medication reconciliation.";
            case SummaryOfCare: 
                return "The EP who transitions their patient to another setting of care or provider of care or refers their patient to another provider of care should provide summary care record for each transition of care or referral.";
            case SummaryOfCareElectronic: 
                return "The EP who transitions their patient to another setting of care or provider of care or refers their patient to another provider of care should provide summary care record electronically for each transition of care or referral.";
            case SecureMessaging: 
                return "Use secure electronic messaging to communicate with patients on relevant health information.";
            case FamilyHistory: 
                return "Record patient family health history as structured data.";
            case ElectronicNote: 
                return "Record electronic notes in patient records.";
            case LabImages: 
                return "Imaging results consisting of the image itself and any explanation or other accompanying information are accessible through CEHRT.";
        
        }
        return "";
    }

    //throw new ApplicationException("Type not in use for MU2: "+mtype.ToString());
    /**
    * Returns the Measures text based on the EHR certification documents.
    */
    private static String getMeasureMu2(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        int thresh = getThresholdMu2(mtype);
        switch(mtype)
        {
            case CPOE_MedOrdersOnly: 
                return "More than " + thresh + "% of medication orders created by the EP during the EHR reporting period are recorded using CPOE.";
            case CPOE_LabOrdersOnly: 
                return "More than " + thresh + "% of lab orders created by the EP during the EHR reporting period are recorded using CPOE.";
            case CPOE_RadiologyOrdersOnly: 
                return "More than " + thresh + "% of radiology orders created by the EP during the EHR reporting period are recorded using CPOE.";
            case Rx: 
                return "More than " + thresh + "% of all permissible prescriptions, or all prescriptions, written by the EP are queried for a drug formulary and transmitted electronically using CEHRT.";
            case Demographics: 
                return "More than " + thresh + "% of all unique patients seen by the EP have demographics recorded as structured data.";
            case VitalSigns: 
                return "More than " + thresh + "% of all unique patients seen by the EP have blood pressure (for patients age 3 and over only) and/or height and weight (for all ages) recorded as structured data.";
            case VitalSignsBMIOnly: 
                return "More than " + thresh + "% of all unique patients seen by the EP have blood pressure (for patients age 3 and over only) and/or height and weight (for all ages) recorded as structured data.";
            case VitalSignsBPOnly: 
                return "More than " + thresh + "% of all unique patients seen by the EP have blood pressure (for patients age 3 and over only) and/or height and weight (for all ages) recorded as structured data.";
            case Smoking: 
                return "More than " + thresh + "% of all unique patients 13 years old or older seen by the EP have smoking status recorded as structured data.";
            case ElectronicCopyAccess: 
                return "More than " + thresh + "% of all unique patients seen by the EP during the EHR reporting period are provided timely (available to the patient within 4 business days after the information is available to the EP) online access to their health information.";
            case ElectronicCopy: 
                return "More than " + thresh + "% of all unique patients seen by the EP during the EHR reporting period (or their authorized representatives) view, download, or transmit to a third party their health information.";
            case ClinicalSummaries: 
                return "Clinical summaries provided to patients or patient-authorized representatives within one business day for more than " + thresh + "% of office visits.";
            case Lab: 
                return "More than " + thresh + "% of all clinical lab tests results ordered by the EP during the EHR reporting period whose results are either in a positive/negative or numerical format are incorporated in Certified EHR Technology as structured data.";
            case Reminders: 
                return "More than " + thresh + "% of all unique patients who have had 2 or more office visits with the EP within the 24 months before the beginning of the EHR reporting period were sent a reminder, per patient preference when available.";
            case Education: 
                return "Patient-specific education resources identified by Certified EHR Technology are provided to patients for more than " + thresh + "% of all unique patients with office visits seen by the EP during the EHR reporting period.";
            case MedReconcile: 
                return "The EP who performs medication reconciliation for more than " + thresh + "% of transitions of care in which the patient is transitioned into the care of the EP.";
            case SummaryOfCare: 
                return "The EP who transitions or refers their patient to another setting of care or provider of care provides a summary of care record for more than " + thresh + "% of transitions of care and referrals.";
            case SummaryOfCareElectronic: 
                return "The EP who transitions or refers their patient to another setting of care or provider of care provides a summary of care record for more than " + thresh + "% of such transitions, and referrals, electronically transmitted using CEHRT to a recipient";
            case SecureMessaging: 
                return "A secure message was sent using the electronic messaging function of CEHRT by more than " + thresh + "% of unique patients (or their authorized representatives) seen by the EP during the EHR reporting period.";
            case FamilyHistory: 
                return "More than " + thresh + "% of all unique patients seen by the EP during the EHR reporting period have a structured data entry for one or more first-degree relatives.";
            case ElectronicNote: 
                return "Enter at least one electronic progress note created, edited and signed by an EP for more than " + thresh + "% of unique patients with at least one office visit during the EHR Measure reporting period. The text of the electronic note must be text searchable and may contain drawings and other content";
            case LabImages: 
                return "More than " + thresh + "% of all tests whose result is one or more images ordered by the EP during the EHR reporting period are accessible through CEHRT.";
        
        }
        return "";
    }

    //throw new ApplicationException("Type not in use for MU2: "+mtype.ToString());
    /**
    * Returns the Measures text based on the EHR certification documents.
    */
    private static int getThresholdMu2(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case CPOE_MedOrdersOnly: 
                return 60;
            case CPOE_LabOrdersOnly: 
                return 30;
            case CPOE_RadiologyOrdersOnly: 
                return 30;
            case Rx: 
                return 50;
            case Demographics: 
                return 80;
            case VitalSigns: 
                return 80;
            case VitalSignsBMIOnly: 
                return 80;
            case VitalSignsBPOnly: 
                return 80;
            case Smoking: 
                return 80;
            case ElectronicCopyAccess: 
                return 50;
            case ElectronicCopy: 
                return 5;
            case ClinicalSummaries: 
                return 50;
            case Lab: 
                return 55;
            case Reminders: 
                return 10;
            case Education: 
                return 10;
            case MedReconcile: 
                return 50;
            case SummaryOfCare: 
                return 50;
            case SummaryOfCareElectronic: 
                return 10;
            case SecureMessaging: 
                return 5;
            case FamilyHistory: 
                return 20;
            case ElectronicNote: 
                return 30;
            case LabImages: 
                return 10;
        
        }
        return 0;
    }

    //throw new ApplicationException("Type not found: "+mtype.ToString());
    public static DataTable getTableMu2(EhrMeasureType mtype, DateTime dateStart, DateTime dateEnd, long provNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), mtype, dateStart, dateEnd, provNum);
        }
         
        String command = "";
        DataTable tableRaw = new DataTable();
        command = "SELECT GROUP_CONCAT(provider.ProvNum) FROM provider WHERE provider.EhrKey=" + "(SELECT pv.EhrKey FROM provider pv WHERE pv.ProvNum=" + POut.long(provNum) + ")";
        String provs = Db.getScalar(command);
        String[] tempProv = provs.Split(',');
        String provOID = "";
        for (int oi = 0;oi < tempProv.Length;oi++)
        {
            provOID = provOID + tempProv[oi];
            if (oi < tempProv.Length - 1)
            {
                provOID += ",";
            }
             
        }
        command = "SELECT GROUP_CONCAT(provider.NationalProvID) FROM provider WHERE provider.EhrKey=" + "(SELECT pv.EhrKey FROM provider pv WHERE pv.ProvNum=" + POut.long(provNum) + ")";
        String provNPIs = Db.getScalar(command);
        //Some measures use a temp table.  Create a random number to tack onto the end of the temp table name to avoid possible table collisions.
        Random rnd = new Random();
        String rndStr = rnd.Next(1000000).ToString();
        switch(mtype)
        {
            case CPOE_MedOrdersOnly: 
                command = "SELECT patient.LName, patient.FName, medPat.* " + "FROM medicationpat as medPat " + "INNER JOIN patient ON patient.PatNum=medPat.PatNum " + "LEFT JOIN ehrmeasureevent as eme ON medPat.MedicationPatNum=eme.FKey " + "AND eme.EventType=" + POut.int(((Enum)EhrMeasureEventType.CPOE_MedOrdered).ordinal()) + " " + "WHERE medPat.ProvNum IN(" + POut.string(provs) + ") " + "AND medPat.DateStart BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd);
                tableRaw = Db.getTable(command);
                break;
            case CPOE_LabOrdersOnly: 
                //When the lab is using a NPI number to determine provider.
                //When the lab is using provider number to determine provider.
                //If the AssigningAuthority is OpenDental.
                //Use the ProvNum to determine provider.
                //If the AssigningAuthority is not OpenDental, we have no way to tell who the provider is.
                command = "SELECT patient.PatNum,patient.LName,patient.FName,ehrlab.IsCpoe,STR_TO_DATE(ehrlab.ObservationDateTimeStart,'%Y%m%d') AS ObservationDateTimeStart " + "FROM ehrlab " + "INNER JOIN patient ON ehrlab.PatNum=patient.PatNum " + "WHERE (CASE WHEN ehrlab.OrderingProviderIdentifierTypeCode='NPI' THEN ehrlab.OrderingProviderID IN(" + POut.string(provNPIs) + ") " + "WHEN ehrlab.OrderingProviderIdentifierTypeCode='PRN' THEN ( " + "CASE WHEN ehrlab.OrderingProviderAssigningAuthorityUniversalID=( " + "SELECT IDRoot FROM oidinternal WHERE IDType='Provider' GROUP BY IDType " + ") THEN ehrlab.OrderingProviderID IN('" + POut.string(provOID) + "') END) " + "ELSE FALSE END) " + "AND ehrlab.ObservationDateTimeStart BETWEEN DATE_FORMAT(" + POut.date(dateStart) + ",'%Y%m%d') AND DATE_FORMAT(" + POut.date(dateEnd) + ",'%Y%m%d') " + "AND (CASE WHEN ehrlab.UsiCodeSystemName='LN' THEN ehrlab.UsiID WHEN ehrlab.UsiCodeSystemNameAlt='LN' THEN ehrlab.UsiIDAlt ELSE '' END) " + "NOT IN (SELECT LoincCode FROM loinc WHERE loinc.ClassType LIKE '%rad%')";
                tableRaw = Db.getTable(command);
                break;
            case CPOE_RadiologyOrdersOnly: 
                //When the lab is using a NPI number to determine provider.
                //When the lab is using provider number to determine provider.
                //If the AssigningAuthority is OpenDental.
                //Use the ProvNum to determine provider.
                //If the AssigningAuthority is not OpenDental, we have no way to tell who the provider is.
                command = "SELECT patient.PatNum,patient.LName,patient.FName,ehrlab.IsCpoe,STR_TO_DATE(ehrlab.ObservationDateTimeStart,'%Y%m%d') AS ObservationDateTimeStart, " + "(CASE WHEN ehrlab.UsiCodeSystemName='LN' THEN ehrlab.UsiID WHEN ehrlab.UsiCodeSystemNameAlt='LN' THEN ehrlab.UsiIDAlt ELSE '' END) AS LoincCode " + "FROM ehrlab " + "INNER JOIN patient ON ehrlab.PatNum=patient.PatNum " + "WHERE (CASE WHEN ehrlab.OrderingProviderIdentifierTypeCode='NPI' THEN ehrlab.OrderingProviderID IN(" + POut.string(provNPIs) + ") " + "WHEN ehrlab.OrderingProviderIdentifierTypeCode='PRN' THEN ( " + "CASE WHEN ehrlab.OrderingProviderAssigningAuthorityUniversalID=( " + "SELECT IDRoot FROM oidinternal WHERE IDType='Provider' GROUP BY IDType " + ") THEN ehrlab.OrderingProviderID IN('" + POut.string(provOID) + "') END) " + "ELSE FALSE END) " + "AND ehrlab.ObservationDateTimeStart BETWEEN DATE_FORMAT(" + POut.date(dateStart) + ",'%Y%m%d') AND DATE_FORMAT(" + POut.date(dateEnd) + ",'%Y%m%d') " + "AND (CASE WHEN ehrlab.UsiCodeSystemName='LN' THEN ehrlab.UsiID WHEN ehrlab.UsiCodeSystemNameAlt='LN' THEN ehrlab.UsiIDAlt ELSE '' END) " + "IN (SELECT LoincCode FROM loinc WHERE loinc.ClassType LIKE '%rad%')";
                tableRaw = Db.getTable(command);
                break;
            case Rx: 
                //+"AND rxpat.ProvNum="+POut.Long(provNum)+" "
                command = "SELECT patient.PatNum,LName,FName,SendStatus,RxDate " + "FROM rxpat,patient " + "WHERE rxpat.PatNum=patient.PatNum " + "AND IsControlled = 0 " + "AND rxpat.ProvNum IN(" + POut.string(provs) + ")	" + "AND RxDate >= " + POut.date(dateStart) + " " + "AND RxDate <= " + POut.date(dateEnd);
                tableRaw = Db.getTable(command);
                break;
            case Demographics: 
                //command="SELECT patient.PatNum,LName,FName,Birthdate,Gender,Race,Language "
                //	+"FROM patient "
                //	+"INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 "
                //	+"AND procedurelog.ProvNum IN("+POut.String(provs)+")	"
                //	+"AND procedurelog.ProcDate BETWEEN "+POut.Date(dateStart)+" AND "+POut.Date(dateEnd)+" "
                //	+"GROUP BY patient.PatNum";
                //tableRaw=Db.GetTable(command);
                command = "SELECT patient.PatNum,LName,FName,Birthdate,Gender,Language,COALESCE(race.HasRace,0) AS HasRace,COALESCE(ethnicity.HasEthnicity,0) AS HasEthnicity " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "LEFT JOIN(SELECT PatNum, 1 AS HasRace FROM patientrace " + "WHERE patientrace.Race IN( " + POut.int(((Enum)PatRace.AfricanAmerican).ordinal()) + "," + POut.int(((Enum)PatRace.AmericanIndian).ordinal()) + "," + POut.int(((Enum)PatRace.Asian).ordinal()) + "," + POut.int(((Enum)PatRace.DeclinedToSpecifyRace).ordinal()) + "," + POut.int(((Enum)PatRace.HawaiiOrPacIsland).ordinal()) + "," + POut.int(((Enum)PatRace.Other).ordinal()) + "," + POut.int(((Enum)PatRace.White).ordinal()) + " " + ") GROUP BY PatNum " + ") AS race ON race.PatNum=patient.PatNum " + "LEFT JOIN(SELECT PatNum, 1 AS HasEthnicity FROM patientrace " + "WHERE patientrace.Race IN( " + POut.int(((Enum)PatRace.Hispanic).ordinal()) + "," + POut.int(((Enum)PatRace.NotHispanic).ordinal()) + "," + POut.int(((Enum)PatRace.DeclinedToSpecifyEthnicity).ordinal()) + " " + ") GROUP BY PatNum " + ") AS ethnicity ON ethnicity.PatNum=patient.PatNum " + "GROUP BY patient.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case VitalSigns: 
                command = "SELECT A.*,COALESCE(hwCount.Count,0) AS hwCount," + "(CASE WHEN A.Birthdate <= (A.LastVisitInDateRange-INTERVAL 3 YEAR) ";
                //BP count only if 3 and older at time of last visit in date range
                command += "THEN COALESCE(bpCount.Count,0) ELSE 1 END) AS bpCount " + "FROM (SELECT patient.PatNum,LName,FName,Birthdate,MAX(procedurelog.ProcDate) AS LastVisitInDateRange " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM vitalsign	WHERE Height>0 AND Weight>0 GROUP BY PatNum) hwCount ON hwCount.PatNum=A.PatNum " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM vitalsign WHERE BpSystolic>0 AND BpDiastolic>0 GROUP BY PatNum) bpCount ON bpCount.PatNum=A.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case VitalSignsBMIOnly: 
                command = "SELECT A.*,COALESCE(hwCount.Count,0) AS hwCount " + "FROM (SELECT patient.PatNum,LName,FName FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM vitalsign	WHERE Height>0 AND Weight>0 GROUP BY PatNum) hwCount ON hwCount.PatNum=A.PatNum ";
                tableRaw = Db.getTable(command);
                break;
            case VitalSignsBPOnly: 
                command = "SELECT patient.PatNum,LName,FName,Birthdate,COUNT(DISTINCT VitalsignNum) AS bpcount " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum " + "AND procedurelog.ProcStatus=2	AND procedurelog.ProvNum IN(" + POut.string(provs) + ") " + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "LEFT JOIN vitalsign ON vitalsign.PatNum=patient.PatNum AND BpSystolic!=0 AND BpDiastolic!=0 " + "GROUP BY patient.PatNum " + "HAVING Birthdate<=MAX(ProcDate)-INTERVAL 3 YEAR ";
                //only include in results if over 3 yrs old at date of last visit
                tableRaw = Db.getTable(command);
                break;
            case Smoking: 
                command = "SELECT patient.PatNum,LName,FName,SmokingSnoMed FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ") " + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "AND patient.Birthdate <= " + POut.Date(DateTime.Today.AddYears(-13)) + " " + "GROUP BY patient.PatNum";
                //13 and older
                tableRaw = Db.getTable(command);
                break;
            case ElectronicCopyAccess: 
                command = "SELECT patient.PatNum,patient.LName,patient.FName,OnlineAccess.dateProvided,MIN(procedurelog.ProcDate) as leastRecentDate " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "LEFT JOIN (SELECT ehrmeasureevent.PatNum, ehrmeasureevent.DateTEvent as dateProvided FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.OnlineAccessProvided).ordinal()) + ") " + "OnlineAccess ON patient.PatNum=OnlineAccess.PatNum " + "GROUP BY patient.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case ElectronicCopy: 
                command = "SELECT patient.PatNum,patient.LName,patient.FName,OnlineAccess.dateRequested,MIN(procedurelog.ProcDate) as leastRecentDate " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "LEFT JOIN (SELECT ehrmeasureevent.PatNum, MIN(ehrmeasureevent.DateTEvent) as dateRequested FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.ElectronicCopyRequested).ordinal()) + " " + "GROUP BY patnum) " + "OnlineAccess ON patient.PatNum=OnlineAccess.PatNum " + "GROUP BY patient.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case ClinicalSummaries: 
                command = "SELECT patient.PatNum,LName,FName,MIN(ClinSum.summaryProvided) as summaryProvided,procedurelog.ProcDate as procDate " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "LEFT JOIN (SELECT ehrmeasureevent.PatNum, ehrmeasureevent.DateTEvent as summaryProvided FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.ClinicalSummaryProvidedToPt).ordinal()) + ") " + "ClinSum ON patient.PatNum=ClinSum.PatNum " + "GROUP BY procedurelog.ProcNum";
                tableRaw = Db.getTable(command);
                break;
            case Lab: 
                //When the lab is using a NPI number to determine provider.
                //When the lab is using provider number to determine provider.
                //If the AssigningAuthority is OpenDental.
                //Use the ProvNum to determine provider.
                //If the AssigningAuthority is not OpenDental, we have no way to tell who the provider is.
                command = "SELECT 1 AS IsOldLab,patient.PatNum,LName,FName,DateTimeOrder,COALESCE(panels.Count,0) AS ResultCount FROM patient " + "INNER JOIN medicalorder ON patient.PatNum=medicalorder.PatNum " + "AND MedOrderType=" + POut.int(((Enum)MedicalOrderType.Laboratory).ordinal()) + " " + "AND medicalorder.ProvNum IN(" + POut.string(provs) + ") " + "AND DATE(DateTimeOrder) BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "LEFT JOIN (SELECT MedicalOrderNum,COUNT(*) AS 'Count' FROM labpanel GROUP BY MedicalOrderNum " + ") panels ON panels.MedicalOrderNum=medicalorder.MedicalOrderNum " + "UNION ALL " + "SELECT 0 AS IsOldLab,patient.PatNum,LName,FName,STR_TO_DATE(ObservationDateTimeStart,'%Y%m%d') AS DateTimeOrder,COALESCE(ehrlabs.Count,0) AS ResultCount FROM patient " + "INNER JOIN ehrlab ON patient.PatNum=ehrlab.PatNum " + "LEFT JOIN (SELECT EhrLabNum, COUNT(*) AS 'Count' FROM ehrlabresult " + "WHERE ehrlabresult.ValueType='NM' OR ehrlabresult.ValueType='SN' " + "OR ehrlabresult.ObservationValueCodedElementID IN (" + _snomedLabResult + ") " + "OR ehrlabresult.ObservationValueCodedElementIDAlt IN (" + _snomedLabResult + ") " + "GROUP BY EhrLabNum " + ") ehrlabs ON ehrlab.EhrLabNum=ehrlabs.EhrLabNum " + "WHERE (CASE WHEN ehrlab.OrderingProviderIdentifierTypeCode='NPI' THEN ehrlab.OrderingProviderID IN(" + POut.string(provNPIs) + ") " + "WHEN ehrlab.OrderingProviderIdentifierTypeCode='PRN' THEN ( " + "CASE WHEN ehrlab.OrderingProviderAssigningAuthorityUniversalID=( " + "SELECT IDRoot FROM oidinternal WHERE IDType='Provider' GROUP BY IDType " + ") THEN ehrlab.OrderingProviderID IN('" + POut.string(provOID) + "') END) " + "ELSE FALSE END) " + "AND ehrlab.ObservationDateTimeStart BETWEEN DATE_FORMAT(" + POut.date(dateStart) + ",'%Y%m%d') AND DATE_FORMAT(" + POut.date(dateEnd) + ",'%Y%m%d') " + "AND (CASE WHEN ehrlab.UsiCodeSystemName='LN' THEN ehrlab.UsiID WHEN ehrlab.UsiCodeSystemNameAlt='LN' THEN ehrlab.UsiIDAlt ELSE '' END) " + "NOT IN (SELECT LoincCode FROM loinc WHERE loinc.ClassType LIKE '%rad%')";
                //Not sure if we need this since rad labs shouldnt be set to numeric results
                tableRaw = Db.getTable(command);
                break;
            case Reminders: 
                //a birthdate is entered
                command = "SELECT patient.PatNum,LName,FName,COALESCE(reminderCount.Count,0) AS reminderCount FROM patient " + "INNER JOIN(SELECT PatNum FROM ( " + "SELECT PatNum, ProcDate FROM procedurelog WHERE ProcStatus=2 " + "AND ProcDate>" + POut.date(dateStart) + "-INTERVAL 2 YEAR " + "AND ProcDate<" + POut.date(dateStart) + " GROUP BY PatNum,ProcDate) uniqueprocdates " + "GROUP BY uniqueprocdates.PatNum HAVING COUNT(*)>1) procscomplete ON procscomplete.PatNum=patient.PatNum " + "LEFT JOIN (SELECT ehrmeasureevent.PatNum,COUNT(*) AS 'Count' FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.ReminderSent).ordinal()) + " " + "AND DATE(ehrmeasureevent.DateTEvent) BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY ehrmeasureevent.PatNum) reminderCount ON reminderCount.PatNum=patient.PatNum " + "WHERE patient.Birthdate > '1880-01-01' " + "AND patient.PatStatus=" + POut.int(((Enum)PatientStatus.Patient).ordinal()) + " " + "AND patient.PriProv IN(" + POut.string(provs) + ") " + "GROUP BY patient.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case Education: 
                command = "SELECT A.*,COALESCE(edCount.Count,0) AS edCount " + "FROM (SELECT patient.PatNum,LName,FName	FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A " + "LEFT JOIN (SELECT PatNum,COUNT(*) AS 'Count' FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.EducationProvided).ordinal()) + " " + "GROUP BY PatNum) edCount ON edCount.PatNum=A.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case MedReconcile: 
                command = "SELECT ptsRefCnt.*,COALESCE(RecCount,0) AS ReconcileCount " + "FROM (SELECT ptsSeen.*,COUNT(DISTINCT refattach.RefAttachNum) AS RefCount " + "FROM (SELECT patient.PatNum,LName,FName FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum " + "AND ProcStatus=2 AND ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "GROUP BY patient.PatNum) ptsSeen " + "INNER JOIN refattach ON ptsSeen.PatNum=refattach.PatNum " + "AND RefDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "AND IsFrom=1 AND IsTransitionOfCare=1 " + "GROUP BY ptsSeen.PatNum) ptsRefCnt " + "LEFT JOIN (SELECT ehrmeasureevent.PatNum,COUNT(*) AS RecCount FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.MedicationReconcile).ordinal()) + " " + "AND DATE(ehrmeasureevent.DateTEvent) BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY ehrmeasureevent.PatNum) ptsRecCount ON ptsRefCnt.PatNum=ptsRecCount.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case SummaryOfCare: 
                command = "SELECT patient.PatNum,patient.LName,patient.FName,refattach.RefDate, " + "referral.FName AS RefFName,referral.LName AS RefLName,SUM(CASE WHEN ISNULL(socevent.FKey) THEN 0 ELSE 1 END) AS SOCSent " + "FROM refattach " + "INNER JOIN referral ON referral.ReferralNum=refattach.ReferralNum " + "INNER JOIN patient ON patient.PatNum=refattach.PatNum " + "LEFT JOIN ( " + "SELECT ehrmeasureevent.FKey " + "FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.SummaryOfCareProvidedToDr).ordinal()) + " " + "AND DATE(ehrmeasureevent.DateTEvent) BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + ") socevent ON socevent.FKey=refattach.RefAttachNum " + "WHERE RefDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "AND IsFrom=0 AND IsTransitionOfCare=1 " + "AND refattach.ProvNum IN(" + POut.string(provs) + ") " + "GROUP BY refattach.RefAttachNum";
                tableRaw = Db.getTable(command);
                break;
            case SummaryOfCareElectronic: 
                command = "SELECT patient.PatNum,patient.LName,patient.FName,refattach.RefDate, " + "referral.FName AS RefFName,referral.LName AS RefLName,SUM(CASE WHEN ISNULL(socevent.FKey) THEN 0 ELSE 1 END) AS ElecSOCSent " + "FROM refattach " + "INNER JOIN referral ON referral.ReferralNum=refattach.ReferralNum " + "INNER JOIN patient ON patient.PatNum=refattach.PatNum " + "LEFT JOIN ( " + "SELECT ehrmeasureevent.FKey " + "FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.SummaryOfCareProvidedToDrElectronic).ordinal()) + " " + "AND DATE(ehrmeasureevent.DateTEvent) BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + ") socevent ON socevent.FKey=refattach.RefAttachNum " + "WHERE RefDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "AND IsFrom=0 AND IsTransitionOfCare=1 " + "AND refattach.ProvNum IN(" + POut.string(provs) + ") " + "GROUP BY refattach.RefAttachNum";
                tableRaw = Db.getTable(command);
                break;
            case SecureMessaging: 
                command = "SELECT A.*,secureMessageRead " + "FROM (SELECT patient.PatNum,LName,FName, procedurelog.ProcDate as procDate " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " GROUP BY procedurelog.PatNum) A " + "LEFT JOIN (SELECT ehrmeasureevent.PatNum, ehrmeasureevent.DateTEvent as secureMessageRead FROM ehrmeasureevent " + "WHERE EventType=" + POut.int(((Enum)EhrMeasureEventType.SecureMessageFromPat).ordinal()) + " GROUP BY ehrmeasureevent.PatNum) " + "SecureMessage ON a.PatNum=SecureMessage.PatNum " + "";
                tableRaw = Db.getTable(command);
                break;
            case FamilyHistory: 
                command = "SELECT * FROM (SELECT patient.PatNum,LName,FName, procedurelog.ProcDate as procDate " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) AS UniquePatsAndProcs " + "LEFT JOIN familyhealth ON UniquePatsAndProcs.PatNum=familyhealth.PatNum " + "GROUP BY UniquePatsAndProcs.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case ElectronicNote: 
                command = "SELECT uniquepatseen.*,notes.NumNotes " + "FROM ( " + "SELECT patient.PatNum,LName,FName " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum " + "AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum " + ") AS uniquepatseen " + "LEFT JOIN ( " + "SELECT procedurelog.PatNum, SUM((CASE WHEN ISNULL(procnotesigned.ProcNoteNum) THEN 0 ELSE 1 END)) AS NumNotes " + "FROM procedurelog " + "LEFT JOIN ( " + "SELECT procnote.PatNum,procnote.ProcNum, procnote.ProcNoteNum " + "FROM procnote " + "INNER JOIN ( " + "SELECT ProcNum,MAX(EntryDateTime) AS NewestNoteDateTime " + "FROM procnote " + "GROUP BY ProcNum " + ") newestnote ON newestnote.ProcNum=procNote.ProcNum AND newestnote.NewestNoteDateTime=procnote.EntryDateTime " + "WHERE Signature!='' AND Note!='' " + "GROUP BY PatNum,ProcNum,EntryDateTime " + ") procnotesigned ON procedurelog.PatNum=procnotesigned.PatNum " + "WHERE procedurelog.ProcStatus!=" + POut.int(((Enum)OpenDentBusiness.ProcStat.D).ordinal()) + " " + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY procedurelog.PatNum " + ") notes ON notes.PatNum=uniquepatseen.PatNum";
                tableRaw = Db.getTable(command);
                break;
            case LabImages: 
                //When the lab is using a NPI number to determine provider.
                //When the lab is using provider number to determine provider.
                //If the AssigningAuthority is OpenDental.
                //Use the ProvNum to determine provider.
                //If the AssigningAuthority is not OpenDental, we have no way to tell who the provider is.
                command = "SELECT labsTable.PatNum,labsTable.LName,labsTable.FName,labImage.DocNum,STR_TO_DATE(ObservationDateTimeStart,'%Y%m%d') AS ObservationDateTimeStart FROM ( " + "SELECT patient.PatNum,patient.LName,patient.FName,ehrlab.ObservationDateTimeStart,ehrlab.EhrLabNum " + "FROM ehrlab " + "INNER JOIN patient ON ehrlab.PatNum=Patient.PatNum " + "WHERE (CASE WHEN ehrlab.OrderingProviderIdentifierTypeCode='NPI' THEN ehrlab.OrderingProviderID IN(" + POut.string(provNPIs) + ") " + "WHEN ehrlab.OrderingProviderIdentifierTypeCode='PRN' THEN ( " + "CASE WHEN ehrlab.OrderingProviderAssigningAuthorityUniversalID=( " + "SELECT IDRoot FROM oidinternal WHERE IDType='Provider' GROUP BY IDType " + ") THEN ehrlab.OrderingProviderID IN('" + POut.string(provOID) + "') END) " + "ELSE FALSE END) " + "AND ehrlab.ObservationDateTimeStart BETWEEN DATE_FORMAT(" + POut.date(dateStart) + ",'%Y%m%d') AND DATE_FORMAT(" + POut.date(dateEnd) + ",'%Y%m%d') " + ") as labsTable " + "INNER JOIN (SELECT DISTINCT EhrLabNum,DocNum FROM ehrlabimage) AS labImage ON labsTable.EhrLabNum=labImage.EhrLabNum";
                tableRaw = Db.getTable(command);
                break;
        
        }
        //default:
        //throw new ApplicationException("Type not found: "+mtype.ToString());
        //PatNum, PatientName, Explanation, and Met (X).
        DataTable table = new DataTable("audit");
        DataRow row = new DataRow();
        table.Columns.Add("PatNum");
        table.Columns.Add("patientName");
        table.Columns.Add("explanation");
        table.Columns.Add("met");
        //X or empty
        List<DataRow> rows = new List<DataRow>();
        Patient pat;
        String explanation = new String();
        for (int i = 0;i < tableRaw.Rows.Count;i++)
        {
            row = table.NewRow();
            row["PatNum"] = tableRaw.Rows[i]["PatNum"].ToString();
            pat = new Patient();
            pat.LName = tableRaw.Rows[i]["LName"].ToString();
            pat.FName = tableRaw.Rows[i]["FName"].ToString();
            pat.Preferred = "";
            row["patientName"] = pat.getNameLF();
            row["met"] = "";
            explanation = "";
            switch(mtype)
            {
                case CPOE_MedOrdersOnly: 
                    DateTime medOrderStartDate = PIn.Date(tableRaw.Rows[i]["DateStart"].ToString());
                    explanation = "Medication order: " + tableRaw.Rows[i]["MedDescript"].ToString() + ", start date: " + medOrderStartDate.ToShortDateString() + ".";
                    if (StringSupport.equals(tableRaw.Rows[i]["IsCpoe"].ToString(), "1"))
                    {
                        row["met"] = "X";
                    }
                     
                    break;
                case CPOE_LabOrdersOnly: 
                    DateTime labOrderStartDate = PIn.DateT(tableRaw.Rows[i]["ObservationDateTimeStart"].ToString());
                    boolean labIsCpoe = PIn.Bool(tableRaw.Rows[i]["IsCpoe"].ToString());
                    explanation = "Laboratory order: " + labOrderStartDate.ToShortDateString() + " ";
                    if (labIsCpoe)
                    {
                        row["met"] = "X";
                        explanation += " is Cpoe";
                    }
                    else
                    {
                        explanation += " is not Cpoe";
                    } 
                    break;
                case CPOE_RadiologyOrdersOnly: 
                    DateTime radOrderStartDate = PIn.DateT(tableRaw.Rows[i]["ObservationDateTimeStart"].ToString());
                    boolean radIsCpoe = PIn.Bool(tableRaw.Rows[i]["IsCpoe"].ToString());
                    explanation = "Radiology order: " + radOrderStartDate.ToShortDateString() + " ";
                    if (radIsCpoe)
                    {
                        row["met"] = "X";
                        explanation += " is Cpoe";
                    }
                    else
                    {
                        explanation += " is not Cpoe";
                    } 
                    break;
                case Rx: 
                    RxSendStatus sendStatus = (RxSendStatus)PIn.Int(tableRaw.Rows[i]["SendStatus"].ToString());
                    DateTime rxDate = PIn.Date(tableRaw.Rows[i]["rxDate"].ToString());
                    if (sendStatus == RxSendStatus.SentElect)
                    {
                        explanation = rxDate.ToShortDateString() + " Rx sent electronically.";
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = rxDate.ToShortDateString() + " Rx not sent electronically.";
                    } 
                    break;
                case Demographics: 
                    if (PIn.Date(tableRaw.Rows[i]["Birthdate"].ToString()).Year < 1880)
                    {
                        explanation += "birthdate";
                    }
                     
                    //missing
                    if (StringSupport.equals(tableRaw.Rows[i]["Language"].ToString(), ""))
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "language";
                    }
                     
                    if (PIn.Int(tableRaw.Rows[i]["Gender"].ToString()) == ((Enum)PatientGender.Unknown).ordinal())
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "gender";
                    }
                     
                    //if(PatientRaces.GetForPatient(PIn.Long(row["PatNum"].ToString())).Count==0) {
                    //	if(explanation!="") {
                    //		explanation+=", ";
                    //	}
                    //	explanation+="race, ethnicity";
                    //}
                    if (PIn.Int(tableRaw.Rows[i]["HasRace"].ToString()) == 0)
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "race";
                    }
                     
                    if (PIn.Int(tableRaw.Rows[i]["HasEthnicity"].ToString()) == 0)
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "ethnicity";
                    }
                     
                    if (StringSupport.equals(explanation, ""))
                    {
                        explanation = "All demographic elements recorded";
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = "Missing: " + explanation;
                    } 
                    break;
                case VitalSigns: 
                    if (StringSupport.equals(tableRaw.Rows[i]["hwCount"].ToString(), "0"))
                    {
                        explanation += "height, weight";
                    }
                     
                    if (StringSupport.equals(tableRaw.Rows[i]["bpCount"].ToString(), "0"))
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "blood pressure";
                    }
                     
                    if (StringSupport.equals(explanation, ""))
                    {
                        explanation = "Vital signs entered";
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = "Missing: " + explanation;
                    } 
                    break;
                case VitalSignsBMIOnly: 
                    if (StringSupport.equals(tableRaw.Rows[i]["hwCount"].ToString(), "0"))
                    {
                        explanation += "height, weight";
                    }
                     
                    if (StringSupport.equals(explanation, ""))
                    {
                        explanation = "Vital signs entered";
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = "Missing: " + explanation;
                    } 
                    break;
                case VitalSignsBPOnly: 
                    if (StringSupport.equals(tableRaw.Rows[i]["bpCount"].ToString(), "0"))
                    {
                        explanation = "Missing: blood pressure";
                    }
                    else
                    {
                        explanation = "Vital signs entered";
                        row["met"] = "X";
                    } 
                    break;
                case Smoking: 
                    String smokeSnoMed = tableRaw.Rows[i]["SmokingSnoMed"].ToString();
                    if (StringSupport.equals(smokeSnoMed, ""))
                    {
                        //None
                        explanation += "Smoking status not entered.";
                    }
                    else
                    {
                        explanation = "Smoking status entered.";
                        row["met"] = "X";
                    } 
                    break;
                case ElectronicCopyAccess: 
                    DateTime visitDate = PIn.Date(tableRaw.Rows[i]["leastRecentDate"].ToString());
                    DateTime deadlineDate = PIn.Date(tableRaw.Rows[i]["leastRecentDate"].ToString());
                    DateTime providedDate = PIn.Date(tableRaw.Rows[i]["dateProvided"].ToString());
                    deadlineDate = deadlineDate.AddDays(4);
                    if (visitDate.DayOfWeek > DayOfWeek.Tuesday)
                    {
                        deadlineDate = deadlineDate.AddDays(2);
                    }
                     
                    if (providedDate <= deadlineDate && providedDate.Year > 1880)
                    {
                        explanation = "Online access provided before " + deadlineDate.ToShortDateString();
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = visitDate.ToShortDateString() + " no online access provided";
                    } 
                    break;
                case ElectronicCopy: 
                    DateTime visitDate2 = PIn.Date(tableRaw.Rows[i]["leastRecentDate"].ToString());
                    DateTime dateRequested = PIn.Date(tableRaw.Rows[i]["dateRequested"].ToString());
                    if (dateRequested < visitDate2)
                    {
                        explanation = visitDate2.ToShortDateString() + " no requests after this date.";
                    }
                    else
                    {
                        explanation = visitDate2.ToShortDateString() + " requests after this date";
                        row["met"] = "X";
                    } 
                    break;
                case ClinicalSummaries: 
                    DateTime procDate = PIn.Date(tableRaw.Rows[i]["procDate"].ToString());
                    DateTime deadlineDateClinSum = procDate.AddDays(1);
                    if (procDate.DayOfWeek == DayOfWeek.Friday)
                    {
                        deadlineDateClinSum = deadlineDateClinSum.AddDays(2);
                    }
                     
                    DateTime summaryProvidedDate = PIn.Date(tableRaw.Rows[i]["summaryProvided"].ToString());
                    if (summaryProvidedDate == DateTime.MinValue)
                    {
                        explanation = procDate.ToShortDateString() + " no summary provided to patient";
                    }
                    else if (summaryProvidedDate <= deadlineDateClinSum)
                    {
                        explanation = procDate.ToShortDateString() + " summary provided to patient";
                        row["met"] = "X";
                    }
                    else
                    {
                        explanation = procDate.ToShortDateString() + " summary provided to patient after more than one buisness day";
                    }  
                    break;
                case Lab: 
                    int resultCount = PIn.Int(tableRaw.Rows[i]["ResultCount"].ToString());
                    boolean isOldLab = PIn.Bool(tableRaw.Rows[i]["IsOldLab"].ToString());
                    DateTime dateOrder = PIn.Date(tableRaw.Rows[i]["DateTimeOrder"].ToString());
                    if (resultCount == 0)
                    {
                        explanation += dateOrder.ToShortDateString() + " results not attached.";
                        explanation += isOldLab ? " (2011 edition)" : "";
                    }
                    else
                    {
                        explanation = dateOrder.ToShortDateString() + " results attached.";
                        explanation += isOldLab ? " (2011 edition)" : "";
                        row["met"] = "X";
                    } 
                    break;
                case Reminders: 
                    if (StringSupport.equals(tableRaw.Rows[i]["reminderCount"].ToString(), "0"))
                    {
                        explanation = "No reminders sent";
                    }
                    else
                    {
                        explanation = "Reminders sent";
                        row["met"] = "X";
                    } 
                    break;
                case Education: 
                    if (StringSupport.equals(tableRaw.Rows[i]["edCount"].ToString(), "0"))
                    {
                        explanation = "No education resources";
                    }
                    else
                    {
                        explanation = "Education resources provided";
                        row["met"] = "X";
                    } 
                    break;
                case MedReconcile: 
                    int refCount = PIn.Int(tableRaw.Rows[i]["RefCount"].ToString());
                    //this will always be greater than zero
                    int reconcileCount = PIn.Int(tableRaw.Rows[i]["ReconcileCount"].ToString());
                    if (reconcileCount < refCount)
                    {
                        explanation = "Transitions of Care:" + refCount.ToString() + ", Reconciles:" + reconcileCount.ToString();
                    }
                    else
                    {
                        explanation = "Reconciles performed for each transition of care.";
                        row["met"] = "X";
                    } 
                    break;
                case SummaryOfCare: 
                    int socSent = PIn.Int(tableRaw.Rows[i]["SOCSent"].ToString());
                    DateTime refDate = PIn.DateT(tableRaw.Rows[i]["RefDate"].ToString());
                    String refLName = PIn.String(tableRaw.Rows[i]["RefLName"].ToString());
                    String refFName = PIn.String(tableRaw.Rows[i]["RefFName"].ToString());
                    if (socSent < 1)
                    {
                        explanation = "Referral on: " + refDate.ToShortDateString() + " to " + refLName + ", " + refFName + " not sent summary of care.";
                    }
                    else
                    {
                        explanation = "Referral on: " + refDate.ToShortDateString() + " to " + refLName + ", " + refFName + " sent summary of care.";
                        row["met"] = "X";
                    } 
                    break;
                case SummaryOfCareElectronic: 
                    int elecSOCSent = PIn.Int(tableRaw.Rows[i]["ElecSOCSent"].ToString());
                    DateTime elecRefDate = PIn.DateT(tableRaw.Rows[i]["RefDate"].ToString());
                    String elecRefLName = PIn.String(tableRaw.Rows[i]["RefLName"].ToString());
                    String elecRefFName = PIn.String(tableRaw.Rows[i]["RefFName"].ToString());
                    if (elecSOCSent < 1)
                    {
                        explanation = "Referral on: " + elecRefDate.ToShortDateString() + " to " + elecRefLName + ", " + elecRefFName + " not sent electronic summary of care.";
                    }
                    else
                    {
                        explanation = "Referral on: " + elecRefDate.ToShortDateString() + " to " + elecRefLName + ", " + elecRefFName + " sent electronic summary of care.";
                        row["met"] = "X";
                    } 
                    break;
                case SecureMessaging: 
                    if (PIn.DateT(tableRaw.Rows[i]["secureMessageRead"].ToString()).Year > 1880)
                    {
                        row["met"] = "X";
                    }
                     
                    break;
                case FamilyHistory: 
                    if (PIn.Long(tableRaw.Rows[i]["FamilyHealthNum"].ToString()) > 0)
                    {
                        row["met"] = "X";
                    }
                     
                    break;
                case ElectronicNote: 
                    if (PIn.Long(tableRaw.Rows[i]["NumNotes"].ToString()) > 0)
                    {
                        row["met"] = "X";
                    }
                     
                    break;
                case LabImages: 
                    DateTime labImageStartDate = PIn.Date(tableRaw.Rows[i]["ObservationDateTimeStart"].ToString());
                    long docNum = PIn.Long(tableRaw.Rows[i]["DocNum"].ToString());
                    explanation = "Laboratory order: " + labImageStartDate.ToShortDateString() + " ";
                    if (docNum > 0)
                    {
                        row["met"] = "X";
                        explanation += " image attached.";
                    }
                    else
                    {
                        explanation += " image not attached.";
                    } 
                    break;
            
            }
            //default:
            //throw new ApplicationException("Type not found: "+mtype.ToString());
            row["explanation"] = explanation;
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * Returns the explanation of the numerator based on the EHR certification documents.
    */
    private static String getNumeratorExplainMu2(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case CPOE_MedOrdersOnly: 
                return "The number of medication orders entered by the Provider during the reporting period using CPOE.";
            case CPOE_LabOrdersOnly: 
                return "The number of lab orders entered by the Provider during the reporting period using CPOE.";
            case CPOE_RadiologyOrdersOnly: 
                return "The number of radiology orders entered by the Provider during the reporting period using CPOE.";
            case Rx: 
                return "Permissible prescriptions transmitted electronically.";
            case Demographics: 
                return "Patients with all required demographic elements recorded as structured data: language, gender, race, ethnicity, and birthdate.";
            case VitalSigns: 
                return "Patients with height, weight, and blood pressure recorded.";
            case VitalSignsBMIOnly: 
                return "Patients with height and weight recorded.";
            case VitalSignsBPOnly: 
                return "Patients with blood pressure recorded.";
            case Smoking: 
                return "Patients with smoking status recorded.";
            case ElectronicCopyAccess: 
                return "Electronic copy received within 4 business days.";
            case ElectronicCopy: 
                return "The number of unique patients in the denominator who have viewed online, downloaded, or transmitted to a third party the patient's health information.";
            case ClinicalSummaries: 
                return "Number of office visits in the denominator where the patient or a patient-authorized representative is provided a clinical summary of their visit within one business day.";
            case Lab: 
                return "Lab results entered.";
            case Reminders: 
                return "Number of patients in the denominator who were sent a reminder per patient preference when available during the EHR reporting period.";
            case Education: 
                return "Patients provided patient-specific education resources, not dependent on requests.";
            case MedReconcile: 
                return "Number of transitions of care in the denominator where medication reconciliation was performed.";
            case SummaryOfCare: 
                return "Number of transitions of care and referrals in the denominator where a summary of care record was provided.";
            case SummaryOfCareElectronic: 
                return "Number of transitions of care and referrals in the denominator where a summary of care record was electronically transmitted";
            case SecureMessaging: 
                return "The number of patients in the denominator who send a secure electronic message to the EP that is received using the electronic messaging function of CEHRT during the EHR reporting period.";
            case FamilyHistory: 
                return "The number of patients in the denominator with a structured data entry for one or more first-degree relatives.";
            case ElectronicNote: 
                return "The number of unique patients in the denominator who have at least one electronic progress note from an eligible professional recorded as text searchable data.";
            case LabImages: 
                return "The number of results in the denominator that are accessible through CEHRT.";
        
        }
        return "";
    }

    //throw new ApplicationException("Type not found: "+mtype.ToString());
    /**
    * Returns the explanation of the denominator based on the EHR certification documents.
    */
    private static String getDenominatorExplainMu2(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case CPOE_MedOrdersOnly: 
                return "The number of medication orders created by the Provider during the reporting period.";
            case CPOE_LabOrdersOnly: 
                return "The number of lab orders created by the Provider during the reporting period.";
            case CPOE_RadiologyOrdersOnly: 
                return "The number of radiology orders created by the Provider during the reporting period.";
            case Rx: 
                return "All permissible prescriptions by the Provider during the reporting period.";
            case Demographics: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case VitalSigns: 
                return "All unique patients (age 3 and over for blood pressure) with at least one completed procedure by the Provider during the reporting period.";
            case VitalSignsBMIOnly: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case VitalSignsBPOnly: 
                return "All unique patients age 3 and over with at least one completed procedure by the Provider during the reporting period.";
            case Smoking: 
                return "All unique patients 13 years or older with at least one completed procedure by the Provider during the reporting period.";
            case ElectronicCopyAccess: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case ElectronicCopy: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case ClinicalSummaries: 
                return "All office visits during the reporting period.  An office visit is calculated as any number of completed procedures by the Provider for a given date.";
            case Lab: 
                return "All lab orders by the Provider during the reporting period.";
            case Reminders: 
                return "Number of unique patients who have had two or more office visits with the EP in the 24 months prior to the beginning of the EHR reporting period.";
            case Education: 
                return "All unique patients with at least one completed procedure by the Provider during the reporting period.";
            case MedReconcile: 
                return "Number of incoming transitions of care from another provider during the reporting period.";
            case SummaryOfCare: 
                return "Number of outgoing transitions of care and referrals during the reporting period.";
            case SummaryOfCareElectronic: 
                return "Number of outgoing transitions of care and referrals during the reporting period.";
            case SecureMessaging: 
                return "Number of unique patients seen by the EP during the EHR reporting period.";
            case FamilyHistory: 
                return "Number of unique patients seen by the EP during the EHR reporting period.";
            case ElectronicNote: 
                return "Number of unique patients with at least one office visit during the EHR reporting period for EPs during the EHR reporting period.";
            case LabImages: 
                return "Number of tests whose result is one or more images ordered by the EP during the EHR reporting period.";
        
        }
        return "";
    }

    //throw new ApplicationException("Type not found: "+mtype.ToString());
    /**
    * Returns the explanation of the exclusion if there is one, if none returns 'No exclusions.'.
    */
    private static String getExclusionExplainMu2(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(mtype)
        {
            case CPOE_MedOrdersOnly: 
            case CPOE_LabOrdersOnly: 
            case CPOE_RadiologyOrdersOnly: 
                return "Any Provider who writes fewer than 100 medication, radiology, or laboratory orders during the EHR reporting period.";
            case Rx: 
                return "1. Any Provider who writes fewer than 100 prescriptions during the reporting period.\r\n" + 
                "2. Any Provider who does not have a pharmacy within their organization and there are no pharmacies that accept electronic prescriptions within 10 miles of the practice at the start of the reporting period.";
            case Demographics: 
                return "No exclusions.";
            case VitalSigns: 
                return "1. Any Provider who sees no patients 3 years or older is excluded from recording blood pressure.\r\n" + 
                "2. Any Provider who believes that all three vital signs of height, weight, and blood pressure have no relevance to their scope of practice is excluded from recording them.\r\n" + 
                "3. Any Provider who believes that height and weight are relevant to their scope of practice, but blood pressure is not, is excluded from recording blood pressure.\r\n" + 
                "4. Any Provider who believes that blood pressure is relevant to their scope of practice, but height and weight are not, is excluded from recording height and weight.";
            case VitalSignsBMIOnly: 
                return "Any Provider who believes that height and weight are not relevant to their scope of practice is excluded from recording them.";
            case VitalSignsBPOnly: 
                return "1. Any Provider who sees no patients 3 years or older is excluded from recording blood pressure.\r\n" + 
                "2. Any Provider who believes that blood pressure is not relevant to their scope of practice is excluded from recording it.";
            case Smoking: 
                return "Any Provider who sees no patients 13 years or older during the reporting period.";
            case ElectronicCopyAccess: 
                return "Any Provider who neither orders nor creates any of the information listed for inclusion as part of both measures, except for Patient name and Provider's name and office contact information.";
            case ElectronicCopy: 
                return "1. Any Provider who neither orders nor creates any of the information listed for inclusion as part of both measures, except for Patient name and Provider\'s name and office contact information.\r\n" + 
                "2. Any Provider who conducts 50% or more of his or her patient encounters in a county that does not have 50% or more of its housing units with 3Mbps broadband availability according to the latest information available from the FCC on the first day of the EHR reporting period.";
            case ClinicalSummaries: 
                return "Any Provider who has no completed procedures during the reporting period.";
            case Lab: 
                return "Any Provider who orders no lab tests whose results are either in a positive/negative or numeric format during the reporting period.";
            case Reminders: 
                return "Any Provider who has had no office visits in the 24 months before the EHR reporting period.";
            case Education: 
                return "Any Provider who has no office visits during the EHR reporting period.";
            case MedReconcile: 
                return "Any Provider who was not the recipient of any transitions of care during the EHR reporting period.";
            case SummaryOfCare: 
                return "Any Provider who transfers a patient to another setting or refers a patient to another provider less than 100 times during the EHR reporting period is excluded from all three measures.";
            case SummaryOfCareElectronic: 
                return "Any Provider who transfers a patient to another setting or refers a patient to another provider less than 100 times during the EHR reporting period is excluded from all three measures.";
            case SecureMessaging: 
                return "Any EP who has no office visits during the EHR reporting period, or any EP who conducts 50% or more of his or her patient encounters in a county that does not have 50% or more of its housing units with 3Mbps broadband availability according to the latest information available from the FCC on the first day of the EHR reporting period.";
            case FamilyHistory: 
                return "Any EP who has no office visits during the EHR reporting period.";
            case ElectronicNote: 
                return "No exclusion.";
            case LabImages: 
                return "Any EP who orders less than 100 tests whose result is an image during the EHR reporting period; or any EP who has no access to electronic imaging results at the start of the EHR reporting period.";
        
        }
        return "";
    }

    //throw new ApplicationException("Type not found: "+mtype.ToString());
    /**
    * Returns the count the office will need to report in order to attest to being excluded from this measure.  Will return -1 if there is no applicable count for this measure.
    */
    private static int getExclusionCountMu2(EhrMeasureType mtype, DateTime dateStart, DateTime dateEnd, long provNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), mtype);
        }
         
        int retval = 0;
        String command = "";
        DataTable tableRaw = new DataTable();
        command = "SELECT GROUP_CONCAT(provider.ProvNum) FROM provider WHERE provider.EhrKey=" + "(SELECT pv.EhrKey FROM provider pv WHERE pv.ProvNum=" + POut.long(provNum) + ")";
        String provs = Db.getScalar(command);
        switch(mtype)
        {
            case CPOE_MedOrdersOnly: 
                command = "SELECT COUNT(DISTINCT rxpat.RxNum) AS 'Count' " + "FROM patient " + "INNER JOIN rxpat ON rxpat.PatNum=patient.PatNum " + "AND rxpat.ProvNum IN(" + POut.string(provs) + ")	" + "AND RxDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd);
                return retval = PIn.int(Db.getScalar(command));
            case CPOE_LabOrdersOnly: 
                command = "SELECT COUNT(DISTINCT ehrlab.EhrLabNum) AS 'Count' " + "FROM patient " + "INNER JOIN ehrlab ON ehrlab.PatNum=patient.PatNum " + "AND ehrlab.OrderingProviderID IN(" + POut.string(provs) + ")	" + "AND ObservationDateTimeStart BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " INNER JOIN loinc on ehrlab.UsiID=loinc.LoincCode" + " AND loinc.ClassType NOT LIKE '%rad%'";
                return retval = PIn.int(Db.getScalar(command));
            case CPOE_RadiologyOrdersOnly: 
                command = "SELECT COUNT(DISTINCT ehrlab.EhrLabNum) AS 'Count' " + "FROM patient " + "INNER JOIN ehrlab ON ehrlab.PatNum=patient.PatNum " + "AND ehrlab.OrderingProviderID IN(" + POut.string(provs) + ")	" + "AND ObservationDateTimeStart BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " INNER JOIN loinc on ehrlab.UsiID=loinc.LoincCode" + " AND loinc.ClassType LIKE '%rad%'";
                return retval = PIn.int(Db.getScalar(command));
            case Rx: 
                command = "SELECT COUNT(DISTINCT rxpat.RxNum) AS 'Count' " + "FROM patient " + "INNER JOIN rxpat ON rxpat.PatNum=patient.PatNum " + "AND rxpat.ProvNum IN(" + POut.string(provs) + ")	" + "AND RxDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd);
                return retval = PIn.int(Db.getScalar(command));
            case Demographics: 
                return retval = -1;
            case VitalSigns: 
                command = "SELECT SUM((CASE WHEN A.Birthdate <= (A.LastVisitInDateRange-INTERVAL 3 YEAR) THEN 1 ELSE 0 END)) AS 'Count' " + "FROM (SELECT Birthdate,MAX(procedurelog.ProcDate) AS LastVisitInDateRange " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A ";
                return retval = PIn.int(Db.getScalar(command));
            case VitalSignsBMIOnly: 
                return retval = -1;
            case VitalSignsBPOnly: 
                command = "SELECT SUM((CASE WHEN A.Birthdate <= (A.LastVisitInDateRange-INTERVAL 3 YEAR) THEN 1 ELSE 0 END)) AS 'Count' " + "FROM (SELECT Birthdate,MAX(procedurelog.ProcDate) AS LastVisitInDateRange " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A ";
                return retval = PIn.int(Db.getScalar(command));
            case Smoking: 
                command = "SELECT SUM((CASE WHEN A.Birthdate <= (A.LastVisitInDateRange-INTERVAL 13 YEAR) THEN 1 ELSE 0 END)) AS 'Count' " + "FROM (SELECT Birthdate,MAX(procedurelog.ProcDate) AS LastVisitInDateRange " + "FROM patient " + "INNER JOIN procedurelog ON procedurelog.PatNum=patient.PatNum AND procedurelog.ProcStatus=2 " + "AND procedurelog.ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " " + "GROUP BY patient.PatNum) A ";
                return retval = PIn.int(Db.getScalar(command));
            case ElectronicCopyAccess: 
                return retval = -1;
            case ElectronicCopy: 
                return retval = -1;
            case ClinicalSummaries: 
                //Excluded if no completed procedures during the reporting period
                command = "SELECT COUNT(DISTINCT ProcNum) FROM procedurelog " + "WHERE ProcStatus=2 AND ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " ";
                return retval = PIn.int(Db.getScalar(command));
            case Lab: 
                command = "SELECT COUNT(DISTINCT ehrlab.EhrLabNum) AS 'Count' " + "FROM patient " + "INNER JOIN ehrlab ON ehrlab.PatNum=patient.PatNum " + "AND ehrlab.OrderingProviderID IN(" + POut.string(provs) + ")	" + "AND ObservationDateTimeStart BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " INNER JOIN loinc on ehrlab.UsiID=loinc.LoincCode" + " AND loinc.ClassType NOT LIKE '%rad%'";
                return retval = PIn.int(Db.getScalar(command));
            case Reminders: 
                //Excluded if Provider has had no office visits in the 24 months before the EHR reporting period.
                command = "SELECT COUNT(DISTINCT ProcNum) FROM procedurelog " + "WHERE ProcStatus=2 AND ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.Date(dateStart.AddMonths(-24)) + " AND " + POut.date(dateStart) + " ";
                return retval = PIn.int(Db.getScalar(command));
            case Education: 
                //Excluded if no completed procedures during the reporting period
                command = "SELECT COUNT(DISTINCT ProcNum) FROM procedurelog " + "WHERE ProcStatus=2 AND ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " ";
                return retval = PIn.int(Db.getScalar(command));
            case MedReconcile: 
                return retval = -1;
            case SummaryOfCare: 
                command = "SELECT COUNT(referral.ReferralNum) FROM referral " + "INNER JOIN provider ON provider.NationalProvID=referral.NationalProvID " + "AND provider.ProvNum=" + POut.long(provNum) + " " + "LEFT JOIN refattach ON referral.referralNum=refattach.referralNum " + "AND RefDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd);
                return retval = PIn.int(Db.getScalar(command));
            case SummaryOfCareElectronic: 
                command = "SELECT COUNT(referral.ReferralNum) FROM referral " + "INNER JOIN provider ON provider.NationalProvID=referral.NationalProvID " + "AND provider.ProvNum=" + POut.long(provNum) + " " + "LEFT JOIN refattach ON referral.referralNum=refattach.referralNum " + "AND RefDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd);
                return retval = PIn.int(Db.getScalar(command));
            case SecureMessaging: 
                //Excluded if no completed procedures during the reporting period
                command = "SELECT COUNT(DISTINCT ProcNum) FROM procedurelog " + "WHERE ProcStatus=2 AND ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " ";
                return retval = PIn.int(Db.getScalar(command));
            case FamilyHistory: 
                //Excluded if no completed procedures during the reporting period
                command = "SELECT COUNT(DISTINCT ProcNum) FROM procedurelog " + "WHERE ProcStatus=2 AND ProvNum IN(" + POut.string(provs) + ")	" + "AND procedurelog.ProcDate BETWEEN " + POut.date(dateStart) + " AND " + POut.date(dateEnd) + " ";
                return retval = PIn.int(Db.getScalar(command));
            case ElectronicNote: 
                return retval = -1;
            case LabImages: 
                return retval = -1;
        
        }
        return -1;
    }

    //This is currently not possible in OD and is always excluded
    //throw new ApplicationException("Type not found: "+mtype.ToString());
    /**
    * Returns the description of what the count displayed is.  May be count of patients under a certain age or number of Rx's written, this will be the label that describes the number.
    */
    private static String getExclusionCountDescriptMu2(EhrMeasureType mtype) throws Exception {
        //No need to check RemotingRole; no call to db.
        //switch(mtype) {
        //	case EhrMeasureType.Demographics:
        //	case EhrMeasureType.Education:
        //	case EhrMeasureType.VitalSignsBMIOnly:
        //	case EhrMeasureType.ElectronicCopy:
        //	case EhrMeasureType.Lab:
        //	case EhrMeasureType.MedReconcile:
        //	case EhrMeasureType.SummaryOfCare:
        //		return "";
        //	case EhrMeasureType.CPOE_MedOrdersOnly:
        //	case EhrMeasureType.Rx:
        //		return "Count of prescriptions entered during the reporting period.";
        //	case EhrMeasureType.CPOE_LabOrdersOnly:
        //		return "Count of labs entered during the reporting period.";
        //	case EhrMeasureType.CPOE_RadiologyOrdersOnly:
        //		return "Count of radiology labs entered during the reporting period.";
        //	case EhrMeasureType.VitalSigns:
        //	case EhrMeasureType.VitalSignsBPOnly:
        //		return "Count of patients seen who were 3 years or older at the time of their last visit during the reporting period.";
        //	case EhrMeasureType.Smoking:
        //		return "Count of patients seen who were 13 years or older at the time of their last visit during the reporting period.";
        //	case EhrMeasureType.ClinicalSummaries:
        //		return "Count of procedures completed during the reporting period.";
        //	case EhrMeasureType.Reminders:
        //		return "Count of procedures completed during the 24 months prior to the reporting period.";
        //}
        //return "";
        switch(mtype)
        {
            case CPOE_MedOrdersOnly: 
                return "Count of prescriptions entered during the reporting period.";
            case CPOE_LabOrdersOnly: 
                return "Count of non-radiology labs entered during the reporting period.";
            case CPOE_RadiologyOrdersOnly: 
                return "Count of radiology labs entered during the reporting period.";
            case Rx: 
                return "Count of prescriptions entered during the reporting period.";
            case Demographics: 
                return "";
            case VitalSigns: 
                return "Count of patients seen who were 3 years or older at the time of their last visit during the reporting period.";
            case VitalSignsBMIOnly: 
                return "";
            case VitalSignsBPOnly: 
                return "Count of patients seen who were 3 years or older at the time of their last visit during the reporting period.";
            case Smoking: 
                return "Count of patients seen who were 13 years or older at the time of their last visit during the reporting period.";
            case ElectronicCopyAccess: 
                return "";
            case ElectronicCopy: 
                return "";
            case ClinicalSummaries: 
                return "Count of procedures completed during the reporting period.";
            case Lab: 
                return "Count of labs entered during the reporting period.";
            case Reminders: 
                return "Count of procedures completed during the 24 months prior to the reporting period.";
            case Education: 
                return "Count of procedures completed during the reporting period.";
            case MedReconcile: 
                return "";
            case SummaryOfCare: 
                return "Count of transitions of care completed during the reporting period.";
            case SummaryOfCareElectronic: 
                return "Count of transitions of care completed during the reporting period.";
            case SecureMessaging: 
                return "Count of procedures completed during the reporting period.";
            case FamilyHistory: 
                return "Count of procedures completed during the reporting period.";
            case ElectronicNote: 
                return "";
            case LabImages: 
                return "";
        
        }
        return "";
    }

    //throw new ApplicationException("Type not found: "+mtype.ToString());
    private static List<EhrMeasure> getMU2List() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrMeasure>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM ehrmeasure " + "WHERE MeasureType IN (" + POut.int(((Enum)EhrMeasureType.CPOE_MedOrdersOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.CPOE_LabOrdersOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.CPOE_RadiologyOrdersOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Rx).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Demographics).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSigns).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSignsBMIOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSignsBPOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Smoking).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ElectronicCopyAccess).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ElectronicCopy).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ClinicalSummaries).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Lab).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Reminders).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Education).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.MedReconcile).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.SummaryOfCare).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.SummaryOfCareElectronic).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.SecureMessaging).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.FamilyHistory).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ElectronicNote).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.LabImages).ordinal()) + ") " + "ORDER BY FIELD(MeasureType," + POut.int(((Enum)EhrMeasureType.CPOE_MedOrdersOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.CPOE_LabOrdersOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.CPOE_RadiologyOrdersOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Rx).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Demographics).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSigns).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSignsBMIOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.VitalSignsBPOnly).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Smoking).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ElectronicCopyAccess).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ElectronicCopy).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ClinicalSummaries).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Lab).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Reminders).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.Education).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.MedReconcile).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.SummaryOfCare).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.SummaryOfCareElectronic).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.SecureMessaging).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.FamilyHistory).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.ElectronicNote).ordinal()) + "," + POut.int(((Enum)EhrMeasureType.LabImages).ordinal()) + ") ";
        //Is always going to be excluded
        List<EhrMeasure> retVal = Crud.EhrMeasureCrud.SelectMany(command);
        return retVal;
    }

    /**
    * Only called from FormEHR to load the patient specific MU data and tell the user what action to take to get closer to meeting MU.
    */
    public static List<EhrMu> getMu2(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrMu>>GetObject(MethodBase.GetCurrentMethod(), pat);
        }
         
        List<EhrMu> list = new List<EhrMu>();
        //add one of each type
        EhrMu mu;
        String explanation = new String();
        List<EhrMeasure> retVal = getMU2List();
        List<MedicationPat> medList = MedicationPats.refresh(pat.PatNum,true);
        List<EhrLab> ehrLabList = EhrLabs.getAllForPat(pat.PatNum);
        List<EhrMeasureEvent> listMeasureEvents = EhrMeasureEvents.refresh(pat.PatNum);
        List<RefAttach> listRefAttach = RefAttaches.refresh(pat.PatNum);
        for (int i = 0;i < retVal.Count;i++)
        {
            mu = new EhrMu();
            mu.Met = MuMet.False;
            mu.MeasureType = retVal[i].MeasureType;
            switch(mu.MeasureType)
            {
                case Demographics: 
                    explanation = "";
                    if (pat.Birthdate.Year < 1880)
                    {
                        explanation += "birthdate";
                    }
                     
                    //missing
                    if (StringSupport.equals(pat.Language, ""))
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "language";
                    }
                     
                    if (pat.Gender == PatientGender.Unknown)
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "gender";
                    }
                     
                    if (PatientRaces.getForPatient(pat.PatNum).Count == 0)
                    {
                        if (!StringSupport.equals(explanation, ""))
                        {
                            explanation += ", ";
                        }
                         
                        explanation += "race, ethnicity";
                    }
                     
                    if (StringSupport.equals(explanation, ""))
                    {
                        mu.Details = "All demographic elements recorded";
                        mu.Met = MuMet.True;
                    }
                    else
                    {
                        mu.Details = "Missing: " + explanation;
                    } 
                    mu.Action = "Enter demographics";
                    break;
                case Education: 
                    List<EhrMeasureEvent> listEd = EhrMeasureEvents.refreshByType(pat.PatNum,EhrMeasureEventType.EducationProvided);
                    if (listEd.Count == 0)
                    {
                        mu.Details = "No education resources provided.";
                    }
                    else
                    {
                        mu.Details = "Education resources provided: " + listEd.Count.ToString();
                        mu.Met = MuMet.True;
                    } 
                    mu.Action = "Provide education resources";
                    break;
                case ElectronicCopyAccess: 
                    List<EhrMeasureEvent> listOnline = EhrMeasureEvents.refreshByType(pat.PatNum,EhrMeasureEventType.OnlineAccessProvided);
                    if (listOnline.Count == 0)
                    {
                        mu.Details = "No online access provided.";
                    }
                    else
                    {
                        mu.Details = "Online access provided: " + listOnline[listOnline.Count - 1].DateTEvent.ToShortDateString();
                        //most recent
                        mu.Met = MuMet.True;
                    } 
                    mu.Action = "Provide online Access";
                    break;
                case CPOE_MedOrdersOnly: 
                    int medOrderCount = 0;
                    int medOrderCpoeCount = 0;
                    for (int m = 0;m < medList.Count;m++)
                    {
                        //Using the last year as the reporting period, following pattern in ElectronicCopy, ClinicalSummaries, Reminders...
                        if (medList[m].DateStart < DateTime.Now.AddYears(-1))
                        {
                            continue;
                        }
                        else //either no start date so not an order, or not within the last year so not during the reporting period
                        if (!StringSupport.equals(medList[m].PatNote, "") && medList[m].ProvNum == pat.PriProv)
                        {
                            //if there's a note and it was created by the patient's PriProv, then count as order created by this provider and would count toward the denominator for MU
                            medOrderCount++;
                            if (medList[m].IsCpoe)
                            {
                                //if also marked as CPOE, then this would count in the numerator of the calculation MU
                                medOrderCpoeCount++;
                            }
                             
                        }
                          
                    }
                    if (medOrderCount == 0)
                    {
                        mu.Details = "No medication order in CPOE.";
                    }
                    else
                    {
                        mu.Details = "Medications entered in CPOE: " + medOrderCount.ToString();
                        mu.Met = MuMet.True;
                    } 
                    mu.Action = "(edit Rxs from Chart)";
                    break;
                case CPOE_LabOrdersOnly: 
                    int labOrderCount = 0;
                    int labOrderCpoeCount = 0;
                    for (int m = 0;m < ehrLabList.Count;m++)
                    {
                        //Using the last year as the reporting period, following pattern in ElectronicCopy, ClinicalSummaries, Reminders...
                        Loinc loinc = Loincs.GetByCode(ehrLabList[m].UsiID);
                        String dateSt = ehrLabList[m].ObservationDateTimeStart.PadRight(8, '0').Substring(0, 8);
                        //stored in DB as yyyyMMddhhmmss-zzzz
                        DateTime dateT = PIn.Date(dateSt.Substring(4, 2) + "/" + dateSt.Substring(6, 2) + "/" + dateSt.Substring(0, 4));
                        if (dateT < DateTime.Now.AddYears(-1))
                        {
                            continue;
                        }
                        else //either no start date so not an order, or not within the last year so not during the reporting period
                        if ((ehrLabList[m].OrderingProviderID == pat.PriProv.ToString() || StringSupport.equals(ehrLabList[m].OrderingProviderID, Providers.getProv(pat.PriProv).NationalProvID)) && (loinc == null || !loinc.ClassType.Contains("%rad%")))
                        {
                            //if there's a note and it was created by the patient's PriProv, then count as order created by this provider and would count toward the denominator for MU
                            labOrderCount++;
                            labOrderCpoeCount++;
                        }
                          
                    }
                    if (labOrderCount == 0)
                    {
                        mu.Details = "No Lab order in CPOE.";
                    }
                    else
                    {
                        mu.Details = "Labs entered in CPOE: " + labOrderCount.ToString();
                        mu.Met = MuMet.True;
                    } 
                    mu.Action = "Edit labs";
                    break;
                case CPOE_RadiologyOrdersOnly: 
                    int radOrderCount = 0;
                    int radOrderCpoeCount = 0;
                    for (int m = 0;m < ehrLabList.Count;m++)
                    {
                        //Using the last year as the reporting period, following pattern in ElectronicCopy, ClinicalSummaries, Reminders...
                        Loinc loinc = Loincs.GetByCode(ehrLabList[m].UsiID);
                        if (loinc == null)
                        {
                            continue;
                        }
                         
                        String dateSt = ehrLabList[m].ObservationDateTimeStart.PadRight(8, '0').Substring(0, 8);
                        //stored in DB as yyyyMMddhhmmss-zzzz
                        DateTime dateT = PIn.Date(dateSt.Substring(4, 2) + "/" + dateSt.Substring(6, 2) + "/" + dateSt.Substring(0, 4));
                        if (dateT < DateTime.Now.AddYears(-1))
                        {
                            continue;
                        }
                        else //either no start date so not an order, or not within the last year so not during the reporting period
                        if ((ehrLabList[m].OrderingProviderID == pat.PriProv.ToString() || StringSupport.equals(ehrLabList[m].OrderingProviderID, Providers.getProv(pat.PriProv).NationalProvID)) && loinc.ClassType.Contains("%rad%"))
                        {
                            //if there's a note and it was created by the patient's PriProv, then count as order created by this provider and would count toward the denominator for MU
                            radOrderCount++;
                            radOrderCpoeCount++;
                        }
                          
                    }
                    if (radOrderCount == 0)
                    {
                        mu.Details = "No Rad order in CPOE.";
                    }
                    else
                    {
                        mu.Details = "Rads entered in CPOE: " + radOrderCount.ToString();
                        mu.Met = MuMet.True;
                    } 
                    mu.Action = "Edit labs";
                    break;
                case Rx: 
                    List<RxPat> listRx = RxPats.GetPermissableForDateRange(pat.PatNum, DateTime.Today.AddYears(-1), DateTime.Today);
                    if (listRx.Count == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No Rxs entered.";
                    }
                    else
                    {
                        explanation = "";
                        for (int rx = 0;rx < listRx.Count;rx++)
                        {
                            if (listRx[rx].SendStatus == RxSendStatus.SentElect)
                            {
                                continue;
                            }
                             
                            if (!StringSupport.equals(explanation, ""))
                            {
                                explanation += ", ";
                            }
                             
                            explanation += listRx[rx].RxDate.ToShortDateString();
                        }
                        if (StringSupport.equals(explanation, ""))
                        {
                            mu.Met = MuMet.True;
                            mu.Details = "All Rxs sent electronically.";
                        }
                        else
                        {
                            mu.Met = MuMet.False;
                            mu.Details = "Rxs not sent electronically: " + explanation;
                        } 
                    } 
                    mu.Action = "(edit Rxs from Chart)";
                    break;
                case VitalSigns: 
                    //no action
                    List<Vitalsign> vitalsignList = Vitalsigns.refresh(pat.PatNum);
                    if (vitalsignList.Count == 0)
                    {
                        mu.Details = "No vital signs entered.";
                    }
                    else
                    {
                        boolean hFound = false;
                        boolean wFound = false;
                        boolean bpFound = false;
                        for (int v = 0;v < vitalsignList.Count;v++)
                        {
                            if (vitalsignList[v].Height > 0)
                            {
                                hFound = true;
                            }
                             
                            if (vitalsignList[v].Weight > 0)
                            {
                                wFound = true;
                            }
                             
                            //3 and older for BP
                            if (pat.Birthdate > DateTime.Today.AddYears(-3) || (vitalsignList[v].BpDiastolic > 0 && vitalsignList[v].BpSystolic > 0))
                            {
                                bpFound = true;
                            }
                             
                        }
                        explanation = "";
                        if (!hFound)
                        {
                            explanation += "height";
                        }
                         
                        //missing
                        if (!wFound)
                        {
                            if (!StringSupport.equals(explanation, ""))
                            {
                                explanation += ", ";
                            }
                             
                            explanation += "weight";
                        }
                         
                        if (!bpFound)
                        {
                            if (!StringSupport.equals(explanation, ""))
                            {
                                explanation += ", ";
                            }
                             
                            explanation += "blood pressure";
                        }
                         
                        if (StringSupport.equals(explanation, ""))
                        {
                            mu.Details = "Vital signs entered";
                            mu.Met = MuMet.True;
                        }
                        else
                        {
                            mu.Details = "Missing: " + explanation;
                        } 
                    } 
                    mu.Action = "Enter vital signs";
                    break;
                case VitalSignsBMIOnly: 
                    vitalsignList = Vitalsigns.refresh(pat.PatNum);
                    if (vitalsignList.Count == 0)
                    {
                        mu.Details = "No vital signs entered.";
                    }
                    else
                    {
                        boolean hFound = false;
                        boolean wFound = false;
                        for (int v = 0;v < vitalsignList.Count;v++)
                        {
                            if (vitalsignList[v].Height > 0)
                            {
                                hFound = true;
                            }
                             
                            if (vitalsignList[v].Weight > 0)
                            {
                                wFound = true;
                            }
                             
                        }
                        explanation = "";
                        if (!hFound)
                        {
                            explanation += "height";
                        }
                         
                        //missing
                        if (!wFound)
                        {
                            if (!StringSupport.equals(explanation, ""))
                            {
                                explanation += ", ";
                            }
                             
                            explanation += "weight";
                        }
                         
                        if (StringSupport.equals(explanation, ""))
                        {
                            mu.Details = "Vital signs entered";
                            mu.Met = MuMet.True;
                        }
                        else
                        {
                            mu.Details = "Missing: " + explanation;
                        } 
                    } 
                    mu.Action = "Enter vital signs";
                    break;
                case VitalSignsBPOnly: 
                    vitalsignList = Vitalsigns.refresh(pat.PatNum);
                    if (pat.Birthdate > DateTime.Today.AddYears(-3))
                    {
                        //3 and older for BP
                        mu.Details = "Age 3 and older for BP.";
                        mu.Met = MuMet.NA;
                    }
                    else if (vitalsignList.Count == 0)
                    {
                        mu.Details = "No vital signs entered.";
                    }
                    else
                    {
                        for (int v = 0;v < vitalsignList.Count;v++)
                        {
                            if (vitalsignList[v].BpDiastolic > 0 && vitalsignList[v].BpSystolic > 0)
                            {
                                mu.Details = "Vital signs entered";
                                mu.Met = MuMet.True;
                            }
                            else
                            {
                                mu.Details = "Missing: blood pressure";
                            } 
                        }
                    }  
                    mu.Action = "Enter vital signs";
                    break;
                case Smoking: 
                    if (StringSupport.equals(pat.SmokingSnoMed, ""))
                    {
                        //None
                        mu.Details = "Smoking status not entered";
                    }
                    else
                    {
                        mu.Details = "Smoking status entered";
                        mu.Met = MuMet.True;
                    } 
                    mu.Action = "Edit smoking status";
                    break;
                case Lab: 
                    if (ehrLabList.Count == 0)
                    {
                        mu.Details = "No lab orders";
                        mu.Met = MuMet.NA;
                    }
                    else
                    {
                        int labResultCount = 0;
                        for (int lo = 0;lo < ehrLabList.Count;lo++)
                        {
                            List<EhrLabResult> ehrLabResults = EhrLabResults.GetForLab(ehrLabList[lo].EhrLabNum);
                            if (ehrLabResults.Count > 0)
                            {
                                labResultCount++;
                            }
                             
                        }
                        if (labResultCount < ehrLabList.Count)
                        {
                            mu.Details = "Lab orders missing results: " + (ehrLabList.Count - labResultCount).ToString();
                        }
                        else
                        {
                            mu.Details = "Lab results entered for each lab order.";
                            mu.Met = MuMet.True;
                        } 
                    } 
                    mu.Action = "Edit labs";
                    break;
                case ElectronicCopy: 
                    List<EhrMeasureEvent> listRequests = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.ElectronicCopyRequested);
                    List<EhrMeasureEvent> listRequestsPeriod = new List<EhrMeasureEvent>();
                    for (int r = 0;r < listRequests.Count;r++)
                    {
                        if (listRequests[r].DateTEvent < DateTime.Now.AddYears(-1) || listRequests[r].PatNum != pat.PatNum)
                        {
                            continue;
                        }
                         
                        //not within the last year
                        listRequestsPeriod.Add(listRequests[r]);
                    }
                    if (listRequestsPeriod.Count == 0)
                    {
                        mu.Met = MuMet.False;
                        mu.Details = "Patient has not viewed/downloaded/transmitted their health info";
                    }
                    else
                    {
                        mu.Met = MuMet.True;
                        mu.Details = "Patient has viewed/downloaded/transmitted their health info";
                    } 
                    mu.Action = "Patient must use the Patient Portal";
                    break;
                case ClinicalSummaries: 
                    List<DateTime> listVisits = new List<DateTime>();
                    //for this year
                    List<Procedure> listProcs = Procedures.refresh(pat.PatNum);
                    for (int p = 0;p < listProcs.Count;p++)
                    {
                        if (listProcs[p].ProcDate < DateTime.Now.AddYears(-1) || listProcs[p].ProcStatus != OpenDentBusiness.ProcStat.C)
                        {
                            continue;
                        }
                         
                        //not within the last year or not a completed procedure
                        if (!listVisits.Contains(listProcs[p].ProcDate))
                        {
                            listVisits.Add(listProcs[p].ProcDate);
                        }
                         
                    }
                    if (listVisits.Count == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No visits within the last year.";
                    }
                    else
                    {
                        int countMissing = 0;
                        boolean summaryProvidedinTime = new boolean();
                        List<EhrMeasureEvent> listClinSum = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.ClinicalSummaryProvidedToPt);
                        for (int p = 0;p < listVisits.Count;p++)
                        {
                            summaryProvidedinTime = false;
                            DateTime deadlineDate = listVisits[p].AddDays(1);
                            if (listVisits[p].DayOfWeek == DayOfWeek.Friday)
                            {
                                deadlineDate = deadlineDate.AddDays(2);
                            }
                             
                            for (int r = 0;r < listClinSum.Count;r++)
                            {
                                //add two days for the weekend
                                if (listClinSum[r].DateTEvent.Date > deadlineDate)
                                {
                                    continue;
                                }
                                 
                                if (listClinSum[r].DateTEvent.Date < listVisits[p])
                                {
                                    continue;
                                }
                                 
                                summaryProvidedinTime = true;
                            }
                            if (!summaryProvidedinTime)
                            {
                                countMissing++;
                            }
                             
                        }
                        if (countMissing == 0)
                        {
                            mu.Met = MuMet.True;
                            mu.Details = "Clinical summary provided to Pt within 1 business day of each visit.";
                        }
                        else
                        {
                            mu.Met = MuMet.False;
                            mu.Details = "Clinical summaries not provided to Pt within 1 business day of a visit:" + countMissing.ToString();
                        } 
                    } 
                    mu.Action = "Send clinical summary to Pt";
                    break;
                case Reminders: 
                    List<DateTime> listVisitsRem = new List<DateTime>();
                    //for this year
                    List<Procedure> listProcsRem = Procedures.refresh(pat.PatNum);
                    for (int p = 0;p < listProcsRem.Count;p++)
                    {
                        if (listProcsRem[p].ProcDate < DateTime.Now.AddYears(-2) || listProcsRem[p].ProcStatus != OpenDentBusiness.ProcStat.C)
                        {
                            continue;
                        }
                         
                        //not within the last year or not a completed procedure
                        if (!listVisitsRem.Contains(listProcsRem[p].ProcDate))
                        {
                            listVisitsRem.Add(listProcsRem[p].ProcDate);
                        }
                         
                    }
                    if (listVisitsRem.Count <= 1)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No two visits within the last two years.";
                    }
                    else if (pat.PatStatus != PatientStatus.Patient)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "Status not patient.";
                    }
                    else
                    {
                        List<EhrMeasureEvent> listReminders = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.ReminderSent);
                        //during reporting period.
                        boolean withinLastYear = false;
                        for (int r = 0;r < listReminders.Count;r++)
                        {
                            if (listReminders[r].DateTEvent > DateTime.Now.AddYears(-1))
                            {
                                withinLastYear = true;
                            }
                             
                        }
                        if (withinLastYear)
                        {
                            mu.Details = "Reminder sent within the last year.";
                            mu.Met = MuMet.True;
                        }
                        else
                        {
                            mu.Details = "No reminders sent within the last year.";
                        } 
                    }  
                    mu.Action = "Send reminders";
                    break;
                case MedReconcile: 
                    int countFromRef = 0;
                    int countFromRefPeriod = 0;
                    for (int c = 0;c < listRefAttach.Count;c++)
                    {
                        if (listRefAttach[c].IsFrom && listRefAttach[c].IsTransitionOfCare)
                        {
                            countFromRef++;
                            if (listRefAttach[c].RefDate > DateTime.Now.AddYears(-1))
                            {
                                //within the last year
                                countFromRefPeriod++;
                            }
                             
                        }
                         
                    }
                    if (countFromRef == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "Referral 'from' not entered.";
                    }
                    else if (countFromRefPeriod == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "Referral 'from' not entered within the last year.";
                    }
                    else if (countFromRefPeriod > 0)
                    {
                        List<EhrMeasureEvent> listReconciles = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.MedicationReconcile);
                        int countReconciles = 0;
                        for (int r = 0;r < listReconciles.Count;r++)
                        {
                            //during reporting period.
                            if (listReconciles[r].DateTEvent > DateTime.Now.AddYears(-1))
                            {
                                //within the same period as the count for referrals.
                                countReconciles++;
                            }
                             
                        }
                        mu.Details = "Referrals:" + countFromRefPeriod.ToString() + ", Reconciles:" + countReconciles.ToString();
                        if (countReconciles >= countFromRefPeriod)
                        {
                            mu.Met = MuMet.True;
                        }
                         
                    }
                       
                    mu.Action = "Reconcile from received CCD";
                    mu.Action2 = "Enter Referrals";
                    break;
                case SummaryOfCare: 
                    int countToRefPeriod = 0;
                    for (int c = 0;c < listRefAttach.Count;c++)
                    {
                        if (!listRefAttach[c].IsFrom && listRefAttach[c].IsTransitionOfCare)
                        {
                            if (listRefAttach[c].RefDate > DateTime.Now.AddYears(-1))
                            {
                                //within the last year
                                countToRefPeriod++;
                            }
                             
                        }
                         
                    }
                    if (countToRefPeriod == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No outgoing transitions of care within the last year.";
                    }
                    else
                    {
                        // > 0
                        List<EhrMeasureEvent> listCcds = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.SummaryOfCareProvidedToDr);
                        int countCcds = 0;
                        for (int r = 0;r < listCcds.Count;r++)
                        {
                            //during reporting period.
                            if (listCcds[r].DateTEvent > DateTime.Now.AddYears(-1))
                            {
                                //within the same period as the count for referrals.
                                countCcds++;
                            }
                             
                        }
                        mu.Details = "Referrals:" + countToRefPeriod.ToString() + ", Summaries:" + countCcds.ToString();
                        if (countCcds >= countToRefPeriod)
                        {
                            mu.Met = MuMet.True;
                        }
                         
                    } 
                    mu.Action = "Send/Receive summary of care";
                    mu.Action2 = "Enter Referrals";
                    break;
                case SummaryOfCareElectronic: 
                    countToRefPeriod = 0;
                    for (int c = 0;c < listRefAttach.Count;c++)
                    {
                        if (!listRefAttach[c].IsFrom && listRefAttach[c].IsTransitionOfCare)
                        {
                            if (listRefAttach[c].RefDate > DateTime.Now.AddYears(-1))
                            {
                                //within the last year
                                countToRefPeriod++;
                            }
                             
                        }
                         
                    }
                    if (countToRefPeriod == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No outgoing transitions of care within the last year.";
                    }
                    else
                    {
                        // > 0
                        List<EhrMeasureEvent> listCcds = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.SummaryOfCareProvidedToDrElectronic);
                        int countCcds = 0;
                        for (int r = 0;r < listCcds.Count;r++)
                        {
                            //during reporting period.
                            if (listCcds[r].DateTEvent > DateTime.Now.AddYears(-1))
                            {
                                //within the same period as the count for referrals.
                                countCcds++;
                            }
                             
                        }
                        mu.Details = "Referrals:" + countToRefPeriod.ToString() + ", Summaries:" + countCcds.ToString();
                        if (countCcds >= countToRefPeriod)
                        {
                            mu.Met = MuMet.True;
                        }
                         
                    } 
                    mu.Action = "Send/Receive summary of care via email";
                    mu.Action2 = "Enter Referrals";
                    break;
                case SecureMessaging: 
                    List<EhrMeasureEvent> listMsg = EhrMeasureEvents.GetByType(listMeasureEvents, EhrMeasureEventType.SecureMessageFromPat);
                    List<DateTime> listVisitsMsg = new List<DateTime>();
                    //for this year
                    List<Procedure> listProcsMsg = Procedures.refresh(pat.PatNum);
                    int msgCount = 0;
                    for (int p = 0;p < listProcsMsg.Count;p++)
                    {
                        if (listProcsMsg[p].ProcDate < DateTime.Now.AddYears(-1) || listProcsMsg[p].ProcStatus != OpenDentBusiness.ProcStat.C)
                        {
                            continue;
                        }
                         
                        //not within the last year or not a completed procedure
                        if (!listVisitsMsg.Contains(listProcsMsg[p].ProcDate))
                        {
                            listVisitsMsg.Add(listProcsMsg[p].ProcDate);
                        }
                         
                    }
                    for (int p = 0;p < listMsg.Count;p++)
                    {
                        if (listMsg[p].PatNum == pat.PatNum)
                        {
                            msgCount++;
                        }
                         
                    }
                    if (listVisitsMsg.Count == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No visits within the last year.";
                    }
                    else if (msgCount == 0)
                    {
                        mu.Met = MuMet.False;
                        mu.Details = "No patient web mail messages.";
                    }
                    else
                    {
                        // > 0
                        mu.Met = MuMet.True;
                        mu.Details = "Web mail message has been received.";
                    }  
                    mu.Action = "Patient must send a web mail message via the Patient Portal.";
                    break;
                case FamilyHistory: 
                    List<FamilyHealth> listFamilyHealth = FamilyHealths.getFamilyHealthForPat(pat.PatNum);
                    if (listFamilyHealth.Count == 0)
                    {
                        mu.Met = MuMet.False;
                        mu.Details = "No family members entered";
                    }
                    else
                    {
                        // > 0
                        mu.Met = MuMet.True;
                        mu.Details = "Family Members: " + listFamilyHealth.Count;
                    } 
                    mu.Action = "Enter family history";
                    break;
                case ElectronicNote: 
                    ProcNote procNote = ProcNotes.GetProcNotesForPat(pat.PatNum, DateTime.Now.AddYears(-1), DateTime.Now.AddDays(1));
                    int notesSigned = 0;
                    if (procNote == null)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No procedure notes";
                    }
                    else if (!StringSupport.equals(procNote.Signature, "") && !StringSupport.equals(procNote.Note, ""))
                    {
                        notesSigned++;
                    }
                      
                    if (notesSigned == 0)
                    {
                        mu.Met = MuMet.False;
                        mu.Details = "Unsigned procedure notes";
                    }
                    else
                    {
                        // > 0
                        mu.Met = MuMet.True;
                        mu.Details = "Signed procedure note is present";
                    } 
                    mu.Action = "Sign all procedure notes";
                    break;
                case LabImages: 
                    int labCount = 0;
                    int labCountImages = 0;
                    List<EhrLab> listEhrLabs = EhrLabs.GetAllForPatInDateRange(pat.PatNum, DateTime.Now.AddYears(-1), DateTime.Now.AddDays(1));
                    for (int img = 0;img < listEhrLabs.Count;img++)
                    {
                        if (EhrLabImages.IsWaitingForImages(listEhrLabs[img].EhrLabNum))
                        {
                            labCount++;
                        }
                        else if (EhrLabImages.Refresh(listEhrLabs[img].EhrLabNum).Count > 0)
                        {
                            labCount++;
                            labCountImages++;
                        }
                          
                    }
                    if (labCount == 0)
                    {
                        mu.Met = MuMet.NA;
                        mu.Details = "No labs are waiting for images";
                    }
                    else if (labCount > labCountImages)
                    {
                        mu.Met = MuMet.False;
                        mu.Details = "Labs currently waiting on images";
                    }
                    else if (labCount == labCountImages)
                    {
                        mu.Met = MuMet.True;
                        mu.Details = "All labs have images attached";
                    }
                       
                    mu.Action = "Manage Lab Images";
                    break;
            
            }
            list.Add(mu);
        }
        return list;
    }

}


