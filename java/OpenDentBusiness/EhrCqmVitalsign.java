//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;


public class EhrCqmVitalsign   
{
    public long EhrCqmVitalsignNum = new long();
    public long PatNum = new long();
    public float Height = new float();
    public float Weight = new float();
    /**
    * BMI=-1 if it is a Physical Exam, Finding: Diastolic/Systolic BP
    */
    public double BMI = new double();
    //in kg/m2, if valid BMI value: CodeValue=39156-5, CodeSystemName=LOINC, CodeSystemOID=2.16.840.1.113883.6.1, Description=Body mass index (BMI) [Ratio], ValueSetName=BMI LOINC Value, ValueSetOID=2.16.840.1.113883.3.600.1.681
    /**
    * BpSystolic=-1 if it is a Physical Exam, Finding: BMI
    */
    public int BpSystolic = new int();
    //for BP Systolic exam: CodeValue=8480-6, CodeSystemName=LOINC, CodeSystemOID=2.16.840.1.113883.6.1, Description=Systolic blood pressure, ValueSetName=Systolic Blood Pressure, ValueSetOID=2.16.840.1.113883.3.526.3.1032
    /**
    * BpDiastolic=-1 if it is a Physical Exam, Finding: BMI
    */
    public int BpDiastolic = new int();
    //for BP Diastolic exam: CodeValue=8462-4, CodeSystemName=LOINC, CodeSystemOID=2.16.840.1.113883.6.1, Description=Diastolic blood pressure, ValueSetName=Diastolic Blood Pressure, ValueSetOID=2.16.840.1.113883.3.526.3.1033
    public String HeightExamCode = new String();
    //LOINC codes only
    public String HeightExamDescript = new String();
    //from LOINC table
    public String WeightExamCode = new String();
    //LOINC codes only
    public String WeightExamDescript = new String();
    //from LOINC table
    public String BMIExamCode = new String();
    //Percentile code, LOINC codes only
    public String BMIPercentileDescript = new String();
    //from LOINC table
    public int BMIPercentile = new int();
    //0-99, if -1 then not a valid BMI Percentile
    public DateTime DateTaken = new DateTime();
}


