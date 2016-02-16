//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import EhrLaboratories.HL70125;
import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.EhrOperand;
import OpenDentBusiness.EhrPatListElement;
import OpenDentBusiness.EhrPatListElement2014;
import OpenDentBusiness.EhrRestrictionType;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Used in Ehr patient lists.
*/
public class EhrPatListElements   
{
    public static DataTable getListOrderBy2014(List<EhrPatListElement2014> elementList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), elementList);
        }
         
        DataTable table = new DataTable();
        String select = "SELECT patient.PatNum,patient.LName,patient.FName";
        String from = "FROM patient";
        String where = "WHERE TRUE ";
        for (int i = 0;i < elementList.Count;i++)
        {
            //Makes formatting easier when adding additional clauses because they will all be AND clauses.
            Restriction __dummyScrutVar0 = elementList[i].Restriction;
            if (__dummyScrutVar0.equals(EhrRestrictionType.Birthdate))
            {
                select += ",patient.BirthDate, ((YEAR(CURDATE())-YEAR(DATE(patient.Birthdate))) - (RIGHT(CURDATE(),5)<RIGHT(DATE(patient.Birthdate),5))) AS Age";
                from += "";
                //---------------------------------------------------------------------------------------------------------------------------
                //only selecting from patient table
                where += "AND ((YEAR(CURDATE())-YEAR(DATE(patient.Birthdate))) - (RIGHT(CURDATE(),5)<RIGHT(DATE(patient.Birthdate),5)))" + GetOperandText(elementList[i].Operand) + "" + PIn.String(elementList[i].CompareString) + " ";
            }
            else if (__dummyScrutVar0.equals(EhrRestrictionType.Gender))
            {
                select += ",patient.Gender";
            }
            else //------------------------------------------------------------------------------------------------------------------------------
            //will look odd if user adds multiple gender columns, enum needs to be "decoded" when filling grid.
            if (__dummyScrutVar0.equals(EhrRestrictionType.LabResult))
            {
                //---------------------------------------------------------------------------------------------------------------------------
                //TODO Units
                from += ",ehrlab AS ehrlab" + i + ",ehrlabresult AS ehrlabresult" + i + " ";
                where += "AND ehrlab" + i + ".PatNum=patient.PatNum AND ehrlab" + i + ".EhrLabNum=ehrlabresult" + i + ".EhrLabNum ";
                //join
                where += "AND ('" + elementList[i].CompareString + "'=ehrlabresult" + i + ".ObservationIdentifierID OR '" + elementList[i].CompareString + "'=ehrlabresult" + i + ".ObservationIdentifierIDAlt) ";
                //filter, LOINC of lab observation
                if (elementList[i].StartDate != null && elementList[i].StartDate.Year > 1880)
                {
                    where += "AND ehrlabresult" + i + ".ObservationDateTime >=" + POut.Date(elementList[i].StartDate) + " ";
                }
                 
                //on or after this date
                if (elementList[i].EndDate != null && elementList[i].EndDate.Year > 1880)
                {
                    where += "AND ehrlabresult" + i + ".ObservationDateTime <=" + POut.Date(elementList[i].EndDate) + " ";
                }
                 
                //on or before this date
                LabValueType __dummyScrutVar1 = elementList[i].LabValueType;
                //CE and CWE should be SNOMEDCT codes, string compare elementList[i].LabValue to ehrlabresult.ObservationValueCodedElementID or ObservationValueCodedElementIDAlt
                if (__dummyScrutVar1.equals(HL70125.CE) || __dummyScrutVar1.equals(HL70125.CWE))
                {
                    select += ",(CASE WHEN ehrlabresult" + i + ".ObservationValueCodedElementID='' THEN ehrlabresult" + i + ".ObservationValueCodedElementIDAlt ELSE ehrlabresult" + i + ".ObservationValueCodedElementID END) AS LabValue";
                    where += "AND (ehrlabresult" + i + ".ObservationValueCodedElementID='" + elementList[i].LabValue + "' OR " + "ehrlabresult" + i + ".ObservationValueCodedElementIDAlt='" + elementList[i].LabValue + "') " + "AND (ehrlabresult" + i + ".ValueType='CWE' OR ehrlabresult" + i + ".ValueType='CE') ";
                }
                else //DT is stored as a string in ehrlabresult.ObservationValueDateTime as YYYY[MM[DD]]
                if (__dummyScrutVar1.equals(HL70125.DT))
                {
                    select += ",ehrlabresult" + i + ".ObservationValueDateTime ";
                    //+DbHelper.DateFormatColumn("RPAD(ehrlabresult"+i+".ObservationValueDateTime,8,'01')","%m/%d/%Y");
                    where += "AND " + DbHelper.dateColumn("RPAD(ehrlabresult" + i + ".ObservationValueDateTime,8,'01')") + GetOperandText(elementList[i].Operand) + "'" + POut.String(elementList[i].LabValue) + "' " + "AND ehrlabresult" + i + ".ValueType='DT' ";
                }
                else //TS is YYYYMMDDHHMMSS, string compare
                if (__dummyScrutVar1.equals(HL70125.TS))
                {
                    select += ",ehrlabresult" + i + ".ObservationValueDateTime ";
                    //+DbHelper.DateTFormatColumn("ehrlabresult"+i+".ObservationValueDateTime","%m/%d/%Y %H:%i:%s");
                    //+POut.DateT(PIn.DateT(DbHelper.DateTFormatColumn("ehrlabresult"+i+".ObservationValueDateTime","%m/%d/%Y %H:%i:%s")))
                    where += "AND ehrlabresult" + i + ".ObservationValueDateTime " + GetOperandText(elementList[i].Operand) + "'" + POut.String(elementList[i].LabValue) + "' " + "AND ehrlabresult" + i + ".ValueType='TS' ";
                }
                else //00:00:00
                if (__dummyScrutVar1.equals(HL70125.TM))
                {
                    select += ",ehrlabresult" + i + ".ObservationValueTime";
                    where += "AND ehrlabresult" + i + ".ObservationValueTime" + GetOperandText(elementList[i].Operand) + "'" + POut.TSpan(PIn.TSpan(elementList[i].LabValue)) + "' " + "AND ehrlabresult" + i + ".ValueType='TM' ";
                }
                else if (__dummyScrutVar1.equals(HL70125.SN))
                {
                    select += ",CONCAT(CONCAT(CONCAT(ehrlabresult" + i + ".ObservationValueComparator,ehrlabresult" + i + ".ObservationValueNumber1),ehrlabresult" + i + ".ObservationValueSeparatorOrSuffix),ehrlabresult" + i + ".ObservationValueNumber2)";
                    where += "AND ehrlabresult" + i + ".ValueType='SN' ";
                }
                else if (__dummyScrutVar1.equals(HL70125.NM))
                {
                    select += ",ehrlabresult" + i + ".ObservationValueNumeric";
                    where += "AND ehrlabresult" + i + ".ObservationValueNumeric" + GetOperandText(elementList[i].Operand) + POut.Double(PIn.Double(elementList[i].LabValue)) + " " + "AND ehrlabresult" + i + ".ValueType='NM' ";
                }
                else if (__dummyScrutVar1.equals(HL70125.FT) || __dummyScrutVar1.equals(HL70125.ST) || __dummyScrutVar1.equals(HL70125.TX))
                {
                    select += ",ehrlabresult" + i + ".ObservationValueText";
                    //where+="AND ehrlabresult"+i+".ObservationValueText"+GetOperandText(elementList[i].Operand)+POut.String(elementList[i].LabValue)+" "
                    where += "AND (ehrlabresult" + i + ".ValueType='FT' OR ehrlabresult" + i + ".ValueType='ST' OR ehrlabresult" + i + ".ValueType='TX') ";
                }
                       
                select += ",ehrlabresult" + i + ".ObservationDateTime ";
            }
            else //select+=",labresult"+i+".ObsValue,labresult"+i+".DateTimeTest";//format column name when filling grid.
            //from+=",labresult AS labresult"+i+", labpanel AS labpanel"+i;
            //where+="AND labpanel"+i+".LabpanelNum=labresult"+i+".LabpanelNum AND patient.PatNum=labpanel"+i+".PatNum ";//join
            //where+="AND labresult"+i+".TestId='"+elementList[i].CompareString+"' "
            //			+"AND labresult"+i+".ObsValue"+GetOperandText(elementList[i].Operand)+"'"+PIn.String(elementList[i].LabValue)+"' ";//filter
            //if(elementList[i].StartDate!=null && elementList[i].StartDate.Year>1880) {
            //	where+="AND labresult"+i+".DateTimeTest>"+POut.Date(elementList[i].StartDate)+" ";//after this date
            //}
            //if(elementList[i].EndDate!=null && elementList[i].EndDate.Year>1880) {
            //	where+="AND labresult"+i+".DateTimeTest<"+POut.Date(elementList[i].EndDate)+" ";//before this date
            //}
            if (__dummyScrutVar0.equals(EhrRestrictionType.Medication))
            {
                //--------------------------------------------------------------------------------------------------------------------------
                select += ",medicationpat" + i + ".DateStart";
                //Name of medication will be in column title.
                from += ",medication AS medication" + i + ", medicationpat AS medicationpat" + i;
                where += "AND medicationpat" + i + ".PatNum=patient.PatNum ";
                //join
                //This is unusual.  Part of the join logic is in the code below because medicationPat.MedicationNum might be 0 if it came from newcrop.
                where += "AND ((medication" + i + ".MedicationNum=MedicationPat" + i + ".MedicationNum AND medication" + i + ".MedName LIKE '%" + PIn.String(elementList[i].CompareString) + "%') " + "  OR (medication" + i + ".MedicationNum=0 AND medicationpat" + i + ".MedDescript LIKE '%" + PIn.String(elementList[i].CompareString) + "%')) ";
                if (elementList[i].StartDate != null && elementList[i].StartDate.Year > 1880)
                {
                    where += "AND medicationpat" + i + ".DateStart>" + POut.Date(elementList[i].StartDate) + " ";
                }
                 
                //after this date
                if (elementList[i].EndDate != null && elementList[i].EndDate.Year > 1880)
                {
                    where += "AND medicationpat" + i + ".DateStart<" + POut.Date(elementList[i].EndDate) + " ";
                }
                 
            }
            else //before this date
            if (__dummyScrutVar0.equals(EhrRestrictionType.Problem))
            {
                //-----------------------------------------------------------------------------------------------------------------------------
                select += ",disease" + i + ".DateStart";
                //Name of problem will be in column title.
                from += ",disease AS disease" + i + ", diseasedef AS diseasedef" + i;
                where += "AND diseasedef" + i + ".DiseaseDefNum=disease" + i + ".DiseaseDefNum AND disease" + i + ".PatNum=patient.PatNum ";
                //join
                where += "AND (diseasedef" + i + ".ICD9Code='" + PIn.String(elementList[i].CompareString) + "' OR diseasedef" + i + ".SnomedCode='" + PIn.String(elementList[i].CompareString) + "') ";
                //filter
                if (elementList[i].StartDate != null && elementList[i].StartDate.Year > 1880)
                {
                    where += "AND disease" + i + ".DateStart>" + POut.Date(elementList[i].StartDate) + " ";
                }
                 
                //after this date
                if (elementList[i].EndDate != null && elementList[i].EndDate.Year > 1880)
                {
                    where += "AND disease" + i + ".DateStart<" + POut.Date(elementList[i].EndDate) + " ";
                }
                 
            }
            else //before this date
            if (__dummyScrutVar0.equals(EhrRestrictionType.Allergy))
            {
                //-----------------------------------------------------------------------------------------------------------------------------
                select += ",allergy" + i + ".DateAdverseReaction";
                //Name of allergy will be in column title.
                from += ",allergy AS allergy" + i + ", allergydef AS allergydef" + i;
                where += "AND allergydef" + i + ".AllergyDefNum=allergy" + i + ".AllergyDefNum AND allergy" + i + ".PatNum=patient.PatNum ";
                //join
                where += "AND allergydef" + i + ".Description='" + PIn.String(elementList[i].CompareString) + "' ";
                //filter
                if (elementList[i].StartDate != null && elementList[i].StartDate.Year > 1880)
                {
                    where += "AND allergy" + i + ".DateAdverseReaction>" + POut.Date(elementList[i].StartDate) + " ";
                }
                 
                //after this date
                if (elementList[i].EndDate != null && elementList[i].EndDate.Year > 1880)
                {
                    where += "AND allergy" + i + ".DateAdverseReaction<" + POut.Date(elementList[i].EndDate) + " ";
                }
                 
            }
            else //before this date
            if (__dummyScrutVar0.equals(EhrRestrictionType.CommPref))
            {
                select += ",patient.PreferContactConfidential";
                from += "";
                //----------------------------------------------------------------------------------------------------------------------------
                //only selecting from patient table
                where += "AND patient.PreferContactConfidential=" + PIn.Int(contactMethodHelper(elementList[i].CompareString)) + " ";
            }
            else
            {
                continue;
            }       
        }
        //should never happen.
        String command = select + " " + from + " " + where;
        return Db.getTable(command);
    }

    /**
    * This is a potential fix to be backported to 13.2 so that patient lists can be used for MU1 2013. on large databases these queries take way to long to run. (At least several minutes).
    */
    public static DataTable getListOrderBy2014Retro(List<EhrPatListElement> elementList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), elementList);
        }
         
        DataTable table = new DataTable();
        String select = "SELECT patient.PatNum,patient.LName,patient.FName";
        String from = "FROM patient";
        String where = "WHERE TRUE ";
        for (int i = 0;i < elementList.Count;i++)
        {
            //Makes formatting easier when adding additional clauses because they will all be AND clauses.
            Restriction __dummyScrutVar2 = elementList[i].Restriction;
            if (__dummyScrutVar2.equals(EhrRestrictionType.Birthdate))
            {
                select += ",patient.BirthDate ";
                from += "";
                //---------------------------------------------------------------------------------------------------------------------------
                //only selecting from patient table
                where += "AND ((YEAR(CURDATE())-YEAR(DATE(patient.Birthdate))) - (RIGHT(CURDATE(),5)<RIGHT(DATE(patient.Birthdate),5)))" + GetOperandText(elementList[i].Operand) + "" + PIn.String(elementList[i].CompareString) + " ";
            }
            else if (__dummyScrutVar2.equals(EhrRestrictionType.Gender))
            {
                select += ",patient.Gender";
            }
            else //------------------------------------------------------------------------------------------------------------------------------
            //will look odd if user adds multiple gender columns, enum needs to be "decoded" when filling grid.
            if (__dummyScrutVar2.equals(EhrRestrictionType.LabResult))
            {
                //---------------------------------------------------------------------------------------------------------------------------
                select += ",labresult" + i + ".ObsValue";
                //format column name when filling grid.
                from += ",labresult AS labresult" + i + ", labpanel AS labpanel" + i;
                where += "AND labpanel" + i + ".LabpanelNum=labresult" + i + ".LabpanelNum AND patient.PatNum=labpanel" + i + ".PatNum ";
                //join
                where += "AND labresult" + i + ".TestId='" + elementList[i].CompareString + "' " + "AND labresult" + i + ".ObsValue" + GetOperandText(elementList[i].Operand) + "'" + PIn.String(elementList[i].LabValue) + "' ";
            }
            else //filter
            if (__dummyScrutVar2.equals(EhrRestrictionType.Medication))
            {
                select += ",'X'";
                //--------------------------------------------------------------------------------------------------------------------------
                //select+=",medicationpat"+i+".DateStart";
                from += ",medication AS medication" + i + ", medicationpat AS medicationpat" + i;
                where += "AND medicationpat" + i + ".PatNum=patient.PatNum ";
                //join
                //This is unusual.  Part of the join logic is in the code below because medicationPat.MedicationNum might be 0 if it came from newcrop.
                where += "AND ((medication" + i + ".MedicationNum=MedicationPat" + i + ".MedicationNum AND medication" + i + ".MedName LIKE '%" + PIn.String(elementList[i].CompareString) + "%') " + "  OR (medication" + i + ".MedicationNum=0 AND medicationpat" + i + ".MedDescript LIKE '%" + PIn.String(elementList[i].CompareString) + "%')) ";
            }
            else if (__dummyScrutVar2.equals(EhrRestrictionType.Problem))
            {
                //-----------------------------------------------------------------------------------------------------------------------------
                select += ",Concat('(',diseasedef" + i + ".ICD9Code,') - ',diseasedef" + i + ".DiseaseName) as Disease" + i + " ";
                //Name of problem will be in column title.
                from += ",disease AS disease" + i + ", diseasedef AS diseasedef" + i;
                where += "AND diseasedef" + i + ".DiseaseDefNum=disease" + i + ".DiseaseDefNum AND disease" + i + ".PatNum=patient.PatNum ";
                //join
                where += "AND diseasedef" + i + ".ICD9Code='" + PIn.String(elementList[i].CompareString) + "' ";
            }
            else
            {
                continue;
            }     
        }
        //filter
        //Can happen because EhrRestrictionType was expanded for 2014.
        //If we reach this point in the code, we will effectively just ignore the pat list element.
        String command = select + " " + from + " " + where + " ORDER BY Patient.LName, Patient.FName";
        return Db.getTable(command);
    }

    /**
    * Tries to match input string to enum name of ContactMethod and returns an int(as a string). Returns empty string if no match.
    */
    private static String contactMethodHelper(String contactEnumName) throws Exception {
        String[] names = Enum.GetNames(ContactMethod.class);
        for (int i = 0;i < names.Length;i++)
        {
            if (StringSupport.equals(names[i], contactEnumName))
            {
                return i.ToString();
            }
             
        }
        return "";
    }

    /**
    * generate list of PatNums in the format "(#,#,#,#,...)" for use in an "IN" clause. Works with empty list.
    */
    private static String patNumHelper(List<long> listPatNums) throws Exception {
        StringBuilder strb = new StringBuilder();
        strb.Append("(");
        for (int i = 0;i < listPatNums.Count;i++)
        {
            strb.Append((i == 0 ? "" : ",") + listPatNums[i]);
        }
        strb.Append(")");
        return strb.ToString();
    }

    /**
    * //Returns a list of PatNums, all of which meet all of the EhrPatListElemet2014 requirements.
    */
    //private static List<long> getPatientsHelper(List<EhrPatListElement2014> elementList) {
    //  List<long> retval = new List<long>();
    //  //retval.Add(1);
    //  //retval.Add(3);
    //  //retval.Add(5);
    //  //return retval;
    //  string command="SELECT patient.PatNum,patient.LName,patient.FName FROM patient ";
    //  for(int i=0;i<elementList.Count;i++) {
    //    if(i==0) {
    //      command+="WHERE patient.Patnum IN(";
    //    }
    //    else {
    //      command+="AND patient.Patnum IN(";
    //    }
    //    string compStr=elementList[i].CompareString;
    //    switch(elementList[i].Restriction) {
    //      case EhrRestrictionType.Birthdate:
    //        if(DataConnection.DBtype==DatabaseType.MySql) {
    //          command+="SELECT patient.patnum FROM patient WHERE (YEAR(CURDATE())-YEAR(Birthdate)) - (RIGHT(CURDATE(),5)<RIGHT(Birthdate,5))"+GetOperandText(elementList[i].Operand)+PIn.Int(compStr);//works with >, <, and =
    //        }
    //        else {
    //          command+="SELECT patient.patnum FROM patient WHERE TRUNC(MONTHS_BETWEEN(SYSDATE,Birthdate)/12)"+GetOperandText(elementList[i].Operand)+PIn.Int(compStr);//works with >, <, and =
    //        }
    //        break;
    //      case EhrRestrictionType.Problem:
    //        command+="SELECT disease.patnum FROM disease, diseasedef "
    //          +"WHERE disease.diseaseDefNum=diseaseDef.diseaseDefNum "
    //          +"AND (diseasedef.ICD9Code LIKE '"+PIn.String(compStr)+"%' OR diseasedef.SnomedCode LIKE '"+PIn.String(compStr)+"%') ";
    //        if(elementList[i].StartDate!=null && elementList[i].StartDate!=DateTime.MinValue) {//check start date
    //          if(DataConnection.DBtype==DatabaseType.MySql) {
    //            command+="AND disease.DateStart>'"+elementList[i].StartDate.ToString("yyyy-MM-dd")+"' ";
    //          }
    //          else {
    //            command+="TODO";
    //          }
    //        }
    //        if(elementList[i].EndDate!=null && elementList[i].EndDate!=DateTime.MinValue) {//check end date
    //          if(DataConnection.DBtype==DatabaseType.MySql) {
    //            command+="AND disease.DateStart<'"+elementList[i].EndDate.ToString("yyyy-MM-dd")+"' ";
    //            command+="AND disease.DateStart>'1800-01-01' ";//don't grab minDate values, but also do not remove results from above.
    //          }
    //          else {
    //            command+="TODO";
    //          }
    //        }
    //        //disease.DateStart>"elementList[i];
    //        //command+=",(SELECT disease.ICD9Num FROM disease WHERE disease.PatNum=patient.PatNum AND disease.ICD9Num IN (SELECT ICD9Num FROM icd9 WHERE ICD9Code LIKE '"+compStr+"%') "
    //        //  +DbHelper.LimitAnd(1)+") `"+compStr+"` ";
    //        //command+=",(SELECT diseasedef.ICD9Num, diseasedef.SnomedCode FROM diseasedef,disease WHERE disease.PatNum=patient.PatNum AND diseasedef.diseasedefnum=disease.patnum AND diseaseDef.DiseaseDefNum IN (SELECT diseasedef.diseasedefnum WHERE diseasedef.ICD9Num LIKE '"+compStr+"%' OR diseasedef.SnomedCode LIKE '"+compStr+"%') "
    //        //  +DbHelper.LimitAnd(1)+") `"+compStr+"` ";
    //        break;
    //      case EhrRestrictionType.LabResult:
    //        command+=",(SELECT IFNULL(MAX(ObsValue),0) FROM labresult,labpanel WHERE labresult.LabPanelNum=labpanel.LabPanelNum AND labpanel.PatNum=patient.PatNum AND labresult.TestName='"+compStr+"') `"+compStr+"` ";
    //        break;
    //      case EhrRestrictionType.Medication:
    //        command+=",(SELECT COUNT(*) FROM medication,medicationpat WHERE medicationpat.PatNum=patient.PatNum AND medication.MedicationNum=medicationpat.MedicationNum AND medication.MedName LIKE '%"+compStr+"%') `"+compStr+"` ";
    //        break;
    //      case EhrRestrictionType.Gender:
    //        command+=",patient.Gender ";
    //        break;
    //    }
    //    command+=") ";
    //  }
    //  return retval;
    //}
    public static DataTable getListOrderBy(List<EhrPatListElement> elementList, boolean isAsc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), elementList, isAsc);
        }
         
        DataTable table = new DataTable();
        String command = "DROP TABLE IF EXISTS tempehrlist";
        Db.nonQ(command);
        command = "CREATE TABLE tempehrlist SELECT patient.PatNum,patient.LName,patient.FName";
        for (int i = 0;i < elementList.Count;i++)
        {
            String compStr = elementList[i].CompareString;
            Restriction __dummyScrutVar3 = elementList[i].Restriction;
            if (__dummyScrutVar3.equals(EhrRestrictionType.Birthdate))
            {
                command += ",patient.Birthdate ";
            }
            else if (__dummyScrutVar3.equals(EhrRestrictionType.Problem))
            {
                command += ",(SELECT diseasedef.ICD9Code FROM disease,diseasedef " + "WHERE disease.PatNum=patient.PatNum " + "AND disease.DiseaseDefNum=diseasedef.DiseaseDefNum " + "AND diseasedef.Icd9code LIKE '" + compStr + "%') `" + compStr + "` ";
            }
            else if (__dummyScrutVar3.equals(EhrRestrictionType.LabResult))
            {
                command += ",(SELECT IFNULL(MAX(ObsValue),0) FROM labresult,labpanel WHERE labresult.LabPanelNum=labpanel.LabPanelNum AND labpanel.PatNum=patient.PatNum AND labresult.TestName='" + compStr + "') `" + compStr + "` ";
            }
            else if (__dummyScrutVar3.equals(EhrRestrictionType.Medication))
            {
                command += ",(SELECT COUNT(*) FROM medication,medicationpat WHERE medicationpat.PatNum=patient.PatNum AND medication.MedicationNum=medicationpat.MedicationNum AND medication.MedName LIKE '%" + compStr + "%') `" + compStr + "` ";
            }
            else if (__dummyScrutVar3.equals(EhrRestrictionType.Gender))
            {
                command += ",patient.Gender ";
            }
                 
        }
        command += "FROM patient";
        Db.nonQ(command);
        String order = "";
        command = "SELECT * FROM tempehrlist ";
        for (int i = 0;i < elementList.Count;i++)
        {
            if (i < 1)
            {
                command += "WHERE " + GetFilteringText(elementList[i]);
            }
            else
            {
                command += "AND " + GetFilteringText(elementList[i]);
            } 
            if (elementList[i].OrderBy)
            {
                if (elementList[i].Restriction == EhrRestrictionType.Birthdate)
                {
                    order = "ORDER BY Birthdate" + getOrderBy(isAsc);
                }
                else if (elementList[i].Restriction == EhrRestrictionType.Gender)
                {
                    order = "ORDER BY Gender" + getOrderBy(isAsc);
                }
                else
                {
                    order = "ORDER BY `" + POut.String(elementList[i].CompareString) + "`" + getOrderBy(isAsc);
                }  
            }
             
        }
        command += order;
        table = Db.getTable(command);
        command = "DROP TABLE IF EXISTS tempehrlist";
        Db.nonQ(command);
        return table;
    }

    private static String getOrderBy(boolean isAsc) throws Exception {
        if (isAsc)
        {
            return " ASC";
        }
         
        return " DESC";
    }

    /**
    * Returns lt, gt, or equals
    */
    private static String getOperandText(EhrOperand ehrOp) throws Exception {
        String operand = "";
        switch(ehrOp)
        {
            case Equals: 
                operand = "=";
                break;
            case GreaterThan: 
                operand = ">";
                break;
            case LessThan: 
                operand = "<";
                break;
        
        }
        return operand;
    }

    /**
    * Returns text used in WHERE clause of query for tempehrlist.
    */
    private static String getFilteringText(EhrPatListElement element) throws Exception {
        String filter = "";
        String compStr = POut.string(element.CompareString);
        String labStr = POut.string(element.LabValue);
        switch(element.Restriction)
        {
            case Birthdate: 
                filter = "DATE_SUB(CURDATE(),INTERVAL " + compStr + " YEAR)" + getOperandText(element.Operand) + "Birthdate ";
                break;
            case Problem: 
                filter = "`" + compStr + "`" + " IS NOT NULL ";
                break;
            case LabResult: 
                //Has the disease.
                filter = "`" + compStr + "`" + getOperandText(element.Operand) + labStr + " ";
                break;
            case Medication: 
                filter = "`" + compStr + "`" + ">0 ";
                break;
            case Gender: 
                //Count greater than 0 (is taking the med).
                filter = "Gender>-1 ";
                break;
        
        }
        return filter;
    }

}


//Just so WHERE clause won't fail.