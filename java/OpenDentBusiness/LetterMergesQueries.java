//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.LetterMerge;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class LetterMergesQueries   
{
    public static DataTable getLetterMergeInfo(Patient PatCur, LetterMerge letter) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), PatCur, letter);
        }
         
        //jsparks- This is messy and prone to bugs.  It needs to be reworked to work just like
        //in SheetFiller.FillFieldsInStaticText.  Just grab a bunch of separate objects
        //instead of one result row.
        String command = new String();
        //We need a very small table that tells us which tp is the most recent.
        //command="DROP TABLE IF EXISTS temptp;";
        //Db.NonQ(command);
        //command=@"CREATE TABLE temptp(
        //	DateTP date NOT NULL default '0001-01-01')";
        //Db.NonQ(command);
        //command+=@"CREATE TABLE temptp
        //	SELECT MAX(treatplan.DateTP) DateTP
        //	FROM treatplan
        //	WHERE PatNum="+POut.PInt(PatCur.PatNum)+";";
        //Db.NonQ(command);
        command = "SET @maxTpDate=(SELECT MAX(treatplan.DateTP) FROM treatplan WHERE PatNum=" + POut.long(PatCur.PatNum) + ");";
        command += "SELECT ";
        for (int i = 0;i < letter.Fields.Count;i++)
        {
            if (i > 0)
            {
                command += ",";
            }
             
            if (StringSupport.equals(letter.Fields[i], "NextAptNum"))
            {
                command += "MAX(plannedappt.AptNum) NextAptNum";
            }
            else //other:
            if (StringSupport.equals(letter.Fields[i], "TPResponsPartyNameFL"))
            {
                command += DbHelper.concat("MAX(patResp.FName)","' '","MAX(patResp.LName)") + " TPResponsPartyNameFL";
            }
            else if (StringSupport.equals(letter.Fields[i], "TPResponsPartyAddress"))
            {
                command += "MAX(patResp.Address) TPResponsPartyAddress";
            }
            else if (StringSupport.equals(letter.Fields[i], "TPResponsPartyCityStZip"))
            {
                command += DbHelper.concat("MAX(patResp.City)","', '","MAX(patResp.State)","' '","MAX(patResp.Zip)") + " TPResponsPartyCityStZip";
            }
            else if (StringSupport.equals(letter.Fields[i], "SiteDescription"))
            {
                command += "MAX(site.Description) SiteDescription";
            }
            else if (StringSupport.equals(letter.Fields[i], "DateOfLastSavedTP"))
            {
                command += DbHelper.dateColumn("MAX(treatplan.DateTP)") + " DateOfLastSavedTP";
            }
            else if (StringSupport.equals(letter.Fields[i], "DateRecallDue"))
            {
                command += "MAX(recall.DateDue)  DateRecallDue";
            }
            else if (StringSupport.equals(letter.Fields[i], "CarrierName"))
            {
                command += "MAX(CarrierName) CarrierName";
            }
            else if (StringSupport.equals(letter.Fields[i], "CarrierAddress"))
            {
                command += "MAX(carrier.Address) CarrierAddress";
            }
            else if (StringSupport.equals(letter.Fields[i], "CarrierCityStZip"))
            {
                command += DbHelper.concat("MAX(carrier.City)","', '","MAX(carrier.State)","' '","MAX(carrier.Zip)") + " CarrierCityStZip";
            }
            else if (StringSupport.equals(letter.Fields[i], "SubscriberNameFL"))
            {
                command += DbHelper.concat("MAX(patSubsc.FName)","' '","MAX(patSubsc.LName)") + " SubscriberNameFL";
            }
            else if (StringSupport.equals(letter.Fields[i], "SubscriberID"))
            {
                command += "MAX(inssub.SubscriberID) SubscriberID";
            }
            else if (StringSupport.equals(letter.Fields[i], "NextSchedAppt"))
            {
                command += "MIN(appointment.AptDateTime) NextSchedAppt";
            }
            else if (StringSupport.equals(letter.Fields[i], "Age"))
            {
                command += "MAX(patient.Birthdate) BirthdateForAge";
            }
            else if (StringSupport.equals(letter.Fields[i], "Guarantor"))
            {
                command += DbHelper.concat("MAX(patGuar.FName)","' '","MAX(patGuar.LName)") + " Guarantor";
            }
            else if (StringSupport.equals(letter.Fields[i], "GradeSchool"))
            {
                command += "MAX(site.Description) GradeSchool";
            }
            else if (letter.Fields[i].StartsWith("referral."))
            {
                command += "MAX(referral." + letter.Fields[i].Substring(9) + ") " + letter.Fields[i].Substring(9);
            }
            else
            {
                command += "MAX(patient." + letter.Fields[i] + ") " + letter.Fields[i];
            }                 
        }
        command += " FROM patient " + "LEFT JOIN refattach ON patient.PatNum=refattach.PatNum AND refattach.IsFrom=1 " + "LEFT JOIN referral ON refattach.ReferralNum=referral.ReferralNum " + "LEFT JOIN plannedappt ON plannedappt.PatNum=patient.PatNum AND plannedappt.ItemOrder=1 " + "LEFT JOIN site ON patient.SiteNum=site.SiteNum " + "LEFT JOIN treatplan ON patient.PatNum=treatplan.PatNum AND DateTP=@maxTpDate " + "LEFT JOIN patient patResp ON treatplan.ResponsParty=patResp.PatNum " + "LEFT JOIN recall ON recall.PatNum=patient.PatNum " + "AND (recall.RecallTypeNum=" + POut.long(PrefC.getLong(PrefName.RecallTypeSpecialProphy)) + " OR recall.RecallTypeNum=" + POut.long(PrefC.getLong(PrefName.RecallTypeSpecialPerio)) + ") " + "LEFT JOIN patplan ON patplan.PatNum=patient.PatNum AND Ordinal=1 " + "LEFT JOIN inssub ON patplan.InsSubNum=inssub.InsSubNum " + "LEFT JOIN insplan ON inssub.PlanNum=insplan.PlanNum " + "LEFT JOIN carrier ON carrier.CarrierNum=insplan.CarrierNum " + "LEFT JOIN patient patSubsc ON patSubsc.PatNum=inssub.Subscriber " + "LEFT JOIN appointment ON appointment.PatNum=patient.PatNum " + "AND AptStatus=" + POut.Long(((Enum)ApptStatus.Scheduled).ordinal()) + " " + "AND AptDateTime > " + DbHelper.now() + " " + "LEFT JOIN patient patGuar ON patGuar.PatNum=patient.Guarantor " + "WHERE patient.PatNum=" + POut.long(PatCur.PatNum) + " GROUP BY patient.PatNum " + "ORDER BY refattach.ItemOrder";
        return Db.getTable(command);
    }

}


